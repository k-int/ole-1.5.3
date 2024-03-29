package org.kuali.ole.docstore.engine.service.storage.rdbms;

import org.kuali.ole.docstore.common.document.Bib;
import org.kuali.ole.docstore.common.document.content.bib.marc.BibMarcRecord;
import org.kuali.ole.docstore.common.document.content.bib.marc.BibMarcRecords;
import org.kuali.ole.docstore.common.document.content.bib.marc.ControlField;
import org.kuali.ole.docstore.common.document.content.bib.marc.xstream.BibMarcRecordProcessor;
import org.kuali.ole.docstore.common.exception.DocstoreResources;
import org.kuali.ole.docstore.common.exception.DocstoreValidationException;
import org.kuali.ole.docstore.engine.service.storage.rdbms.pojo.BibRecord;
import org.kuali.rice.core.api.config.property.ConfigContext;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: sambasivam
 * Date: 12/17/13
 * Time: 1:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class RdbmsBibMarcDocumentManager extends RdbmsBibDocumentManager {

    private static RdbmsBibMarcDocumentManager rdbmsBibMarcDocumentManager = null;

    private static BibMarcRecordProcessor bibMarcRecordProcessor = null;

    public static RdbmsBibMarcDocumentManager getInstance() {
        if (rdbmsBibMarcDocumentManager == null) {
            rdbmsBibMarcDocumentManager = new RdbmsBibMarcDocumentManager();
        }
        return rdbmsBibMarcDocumentManager;
    }

    public static BibMarcRecordProcessor getBibMarcRecordProcessor() {
        if (bibMarcRecordProcessor == null) {
            bibMarcRecordProcessor = new BibMarcRecordProcessor();
        }
        return bibMarcRecordProcessor;
    }

    protected boolean getBibIdFromBibXMLContent(BibRecord bibRecord) {

        boolean isBibIdFlag = true;
        BibMarcRecordProcessor bibMarcRecordProcessor = new BibMarcRecordProcessor();
        BibMarcRecords bibMarcRecords = bibMarcRecordProcessor.fromXML(bibRecord.getContent());
        if (bibMarcRecords != null && bibMarcRecords.getRecords() != null && bibMarcRecords.getRecords().size() > 0) {
            BibMarcRecord bibMarcRecord = bibMarcRecords.getRecords().get(0);
            if (bibMarcRecord.getControlFields() != null) {
                for (ControlField controlField : bibMarcRecord.getControlFields()) {
                    if ("001".equals(controlField.getTag()) && validateIdField(controlField.getValue())) {
                        bibRecord.setBibId(controlField.getValue());
                        Map parentCriteria1 = new HashMap();
                        parentCriteria1.put("bibId", controlField.getValue());
                        List<BibRecord> bibRecords = (List<BibRecord>) getBusinessObjectService().findMatching(BibRecord.class, parentCriteria1);
                        if (bibRecords == null && bibRecords.size() > 0) {
                            throw new DocstoreValidationException(DocstoreResources.BIB_ID_ALREADY_EXISTS, DocstoreResources.BIB_ID_ALREADY_EXISTS);
                        }
                        isBibIdFlag = false;
                        break;
                    }
                }
            }
        }
        return isBibIdFlag;
    }

    protected void modifyDocumentContent(Bib bib, BibRecord bibRecord) {
        String content = bib.getContent();
        if (content != null && content != "" && content.length() > 0) {
            Pattern pattern = Pattern.compile("tag=\"001\">.*?</controlfield");
            Pattern pattern2 = Pattern.compile("<controlfield.*?tag=\"001\"/>");
            Matcher matcher = pattern.matcher(content);
            Matcher matcher2 = pattern2.matcher(content);
            if (matcher.find()) {
                bib.setContent(matcher.replaceAll("tag=\"001\">" + bibRecord.getBibId() + "</controlfield"));
            } else if (matcher2.find()) {
                bib.setContent(matcher2.replaceAll("<controlfield tag=\"001\">" + bibRecord.getBibId() + "</controlfield>"));
            } else {
                int ind = content.indexOf("</leader>") + 9;
                if (ind == 8) {
                    ind = content.indexOf("<leader/>") + 9;
                    if (ind == 8) {
                        ind = content.indexOf("record>") + 7;
                    }
                }
                StringBuilder sb = new StringBuilder();
                sb.append(content.substring(0, ind));
                sb.append("<controlfield tag=\"001\">");
                sb.append(bibRecord.getBibId());
                sb.append("</controlfield>");
                sb.append(content.substring(ind + 1));
                bib.setContent(sb.toString());
            }
            bibRecord.setContent(bib.getContent());
            getBusinessObjectService().save(bibRecord);
        }
    }

    /**
     * Set 003  with Local 003
     * @param bib
     */

    protected void add003Field(Bib bib) {
        String content = bib.getContent();
        bibMarcRecordProcessor = RdbmsBibMarcDocumentManager.getBibMarcRecordProcessor();
        BibMarcRecords bibMarcRecords = bibMarcRecordProcessor.fromXML(content);
        ListIterator<ControlField> iterator = bibMarcRecords.getRecords().get(0).getControlFields().listIterator();
        boolean organizationCodeAvailable = false;
        String organizationCode = ConfigContext.getCurrentContextConfig().getProperty("organization.marc.code");
        String controlField003 = ConfigContext.getCurrentContextConfig().getProperty("controlfield.003");
        while (iterator.hasNext()) {
            ControlField controlField = iterator.next();

            // Check and update to local 003
            if (controlField.getTag().equals(controlField003)) {
                if (!controlField.getValue().equals(organizationCode)) {
                    controlField.setValue(organizationCode);
                }
                organizationCodeAvailable = true;
            }
        }

        // Add local 003
        if (!organizationCodeAvailable) {
            ControlField controlField = new ControlField();
            controlField.setTag(controlField003);
            controlField.setValue(organizationCode);
            iterator.add(controlField);
        }
        content = bibMarcRecordProcessor.generateXML(bibMarcRecords.getRecords());
        bib.setContent(content);
    }

    /**
     *  Modify additional attributes
     * @param bib
     */
    protected void modifyAdditionalAttributes(Bib bib) {
        super.modifyAdditionalAttributes(bib);
        add003Field(bib);
    }

    /**
     *  Update additional attributes
     * @param bib
     * @param bibRecord
     */
    public void updateAdditionalAttributes(Bib bib, BibRecord bibRecord) {
        super.updateAdditionalAttributes(bib, bibRecord);
        add003Field(bib);
    }


}