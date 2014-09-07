package org.kuali.ole.docstore.common.document;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.kuali.ole.docstore.common.util.ParseXml;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: chenchulakshmig
 * Date: 2/25/14
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class LicenseAttachment_UT {
    private static final Logger LOG = Logger.getLogger(LicenseAttachment_UT.class);

    @Test
    public void deserializeAndSerialize() {
        String input = "";
        File file = null;
        try {
            file = new File(getClass().getResource("/documents/LicenseAttachment1.xml").toURI());
            input = FileUtils.readFileToString(file);
        } catch (Exception e) {
            LOG.error("Exception :", e);
        }
        LicenseAttachment licenseAttachment = new LicenseAttachment();
        licenseAttachment = (LicenseAttachment) licenseAttachment.deserialize(input);
        licenseAttachment.setContent(null);
        licenseAttachment.setFileName("123");
        licenseAttachment.setFormat("pdf");
        String serializeXml = licenseAttachment.serialize(licenseAttachment);
        serializeXml = ParseXml.formatXml(serializeXml);
        System.out.print(serializeXml);
    }
}
