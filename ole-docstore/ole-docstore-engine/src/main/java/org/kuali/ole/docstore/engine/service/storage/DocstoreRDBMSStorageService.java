package org.kuali.ole.docstore.engine.service.storage;

import org.kuali.ole.DocumentUniqueIDPrefix;
import org.kuali.ole.docstore.common.document.*;
import org.kuali.ole.docstore.common.document.Item;
import org.kuali.ole.docstore.common.document.content.enums.DocCategory;
import org.kuali.ole.docstore.common.document.content.enums.DocFormat;
import org.kuali.ole.docstore.common.document.content.enums.DocType;
import org.kuali.ole.docstore.engine.factory.DocumentManagerFactory;
import org.kuali.ole.docstore.engine.service.storage.rdbms.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sambasivam
 * Date: 12/13/13
 * Time: 6:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class DocstoreRDBMSStorageService implements DocstoreStorageService {

    @Override
    public void createBib(Bib bib) {
        DocumentManager documentManager = DocumentManagerFactory.getInstance().getDocumentManager(bib.getCategory(), bib.getType(), bib.getFormat());
        documentManager.validate(bib);
        documentManager.create(bib);
    }

    @Override
    public void createHoldings(Holdings holdings) {
        DocumentManager documentManager = DocumentManagerFactory.getInstance().getDocumentManager(DocCategory.WORK.getCode(), DocType.HOLDINGS.getCode(), DocFormat.OLEML.getCode());
        documentManager.validate(holdings);
        documentManager.create(holdings);
    }

    @Override
    public void createItem(Item item) {
        DocumentManager documentManager = DocumentManagerFactory.getInstance().getDocumentManager(DocCategory.WORK.getCode(), DocType.ITEM.getCode(), DocFormat.OLEML.getCode());
        documentManager.validate(item);
        documentManager.create(item);
    }

    @Override
    public void createHoldingsTree(HoldingsTree holdingsTree) {
        DocumentManager holdingsDocumentManager = RdbmsHoldingsDocumentManager.getInstance();
        DocumentManager itemDocumentManager = RdbmsItemDocumentManager.getInstance();
        holdingsDocumentManager.validate(holdingsTree.getHoldings());
        for (Item item : holdingsTree.getItems()) {
            itemDocumentManager.validate(item);
        }
        createHoldings(holdingsTree.getHoldings());
        for (Item item : holdingsTree.getItems()) {
            Holdings holdings = new PHoldings();
            holdings.setId(holdingsTree.getHoldings().getId());
            item.setHolding(holdings);
            createItem(item);
        }
    }

    @Override
    public void createBibTree(BibTree bibTree) {
        DocumentManager holdingsDocumentManager = RdbmsHoldingsDocumentManager.getInstance();
        DocumentManager itemDocumentManager = RdbmsItemDocumentManager.getInstance();
        for (HoldingsTree holdingsTree : bibTree.getHoldingsTrees()) {
            if(holdingsTree.getHoldings() != null && holdingsTree.getHoldings().getContent() != null) {
                holdingsDocumentManager.validate(holdingsTree.getHoldings());
                for (Item item : holdingsTree.getItems()) {
                    itemDocumentManager.validate(item);
                }
            }
        }
        createBib(bibTree.getBib());
        for (HoldingsTree holdingsTree : bibTree.getHoldingsTrees()) {
            Bib bib = new BibMarc();
            bib.setId(bibTree.getBib().getId());
            if (holdingsTree.getHoldings() != null && holdingsTree.getHoldings().getContent() != null) {
                holdingsTree.getHoldings().setBib(bib);
                createHoldingsTree(holdingsTree);
            }
        }
    }

    @Override
    public Bib retrieveBib(String bibId) {
        DocumentManager documentManager =null;
        documentManager = RdbmsBibDocumentManager.getInstance();
        Bib bib = (Bib) documentManager.retrieve(bibId);
        return bib;
    }

    @Override
    public List<Bib> retrieveBibs(List<String> bibIds) {
        DocumentManager documentManager =null;
        List<Bib> bibs = new ArrayList<>();
        if(bibIds!=null && bibIds.size() > 0) {
            documentManager = RdbmsBibDocumentManager.getInstance();
            List<Object> objs = documentManager.retrieve(bibIds);

            for (Object obj : objs) {
                bibs.add((Bib) obj);
            }
        }
        return bibs;
    }

    @Override
    public List<Item> retrieveItems(List<String> itemIds) {
        DocumentManager documentManager = RdbmsItemDocumentManager.getInstance();
        List<Object> objs = documentManager.retrieve(itemIds);
        List<Item> items = new ArrayList<>();
        for (Object obj : objs) {
            items.add((Item) obj);
        }
        return items;
    }

    @Override
    public HashMap<String, Item> retrieveItemMap(List<String> itemIds) {
        DocumentManager documentManager = RdbmsItemDocumentManager.getInstance();
        List<Object> objs = documentManager.retrieve(itemIds);
        HashMap<String,Item> items = new HashMap<>();
        for (Object obj : objs) {
            Item item = (Item) obj;
            items.put(item.getId(),item);
        }
        return items;
    }

    @Override
    public Holdings retrieveHoldings(String holdingsId) {
        RdbmsHoldingsDocumentManager rdbmsHoldingsDocumentManager = RdbmsHoldingsDocumentManager.getInstance();
        Holdings holdings = (Holdings) rdbmsHoldingsDocumentManager.retrieve(holdingsId);
        return holdings;
    }

    @Override
    public Item retrieveItem(String itemId) {
        RdbmsItemDocumentManager rdbmsItemDocumentManager = RdbmsItemDocumentManager.getInstance();
        Item item = (Item) rdbmsItemDocumentManager.retrieve(itemId);
        return item;
    }

    @Override
    public HoldingsTree retrieveHoldingsTree(String holdingsId) {
        DocumentManager documentManager = RdbmsHoldingsDocumentManager.getInstance();
        HoldingsTree holdingsTree = (HoldingsTree) documentManager.retrieveTree(holdingsId);
        return holdingsTree;
    }

    @Override
    public BibTree retrieveBibTree(String bibId) {
        DocumentManager documentManager =null;
        documentManager = RdbmsBibDocumentManager.getInstance();
        BibTree bibTree = (BibTree) documentManager.retrieveTree(bibId);
        return bibTree;
    }

    @Override
    public List<BibTree> retrieveBibTrees(List<String> bibIds) {
        List<BibTree> bibTrees = new ArrayList<>();
        for (String bibId : bibIds) {
            try{
                bibTrees.add(retrieveBibTree(bibId));
            }catch(Exception e){
            }
        }

        return bibTrees;
    }

    @Override
    public void updateBib(Bib bib) {
        DocumentManager documentManager = DocumentManagerFactory.getInstance().getDocumentManager(bib.getCategory(), bib.getType(), bib.getFormat());
        documentManager.update(bib);
    }

    @Override
    public void updateBibs(List<Bib> bibs) {
        for (Bib bib : bibs) {
            DocumentManager documentManager = DocumentManagerFactory.getInstance().getDocumentManager(bib.getCategory(), bib.getType(), bib.getFormat());
            documentManager.update(bib);
        }
    }

    @Override
    public void updateHoldings(Holdings holdings) {
        DocumentManager documentManager = DocumentManagerFactory.getInstance().getDocumentManager(DocCategory.WORK.getCode(), DocType.HOLDINGS.getCode(), DocFormat.OLEML.getCode());
        documentManager.validate(holdings);
        documentManager.update(holdings);
    }

    @Override
    public void updateItem(Item item) {
        DocumentManager documentManager = DocumentManagerFactory.getInstance().getDocumentManager(item.getCategory(), item.getType(), item.getFormat());
        documentManager.validate(item);
        documentManager.update(item);

    }

    @Override
    public void deleteBib(String bibId) {
        DocumentManager documentManager = RdbmsBibMarcDocumentManager.getInstance();
        documentManager.deleteVerify(bibId);
        documentManager.delete(bibId);
    }

    @Override
    public void deleteHoldings(String holdingsId) {
        DocumentManager documentManager = RdbmsHoldingsDocumentManager.getInstance();
        documentManager.deleteVerify(holdingsId);
        documentManager.delete(holdingsId);

    }

    @Override
    public void deleteItem(String itemId) {
        DocumentManager documentManager = RdbmsItemDocumentManager.getInstance();
        documentManager.deleteVerify(itemId);
        documentManager.delete(itemId);

    }

    @Override
    public void rollback() {
        //TODO: implementation is pending for rollback.
    }

    public void boundHoldingsWithBibs(String holdingsId, List<String> bibIds) {
        RdbmsHoldingsDocumentManager rdbmsHoldingsDocumentManager = RdbmsHoldingsDocumentManager.getInstance();
        rdbmsHoldingsDocumentManager.boundHoldingsWithBibs(holdingsId, bibIds);
    }

    public void transferHoldings(List<String> holdingsIds, String bibId) {
        RdbmsHoldingsDocumentManager rdbmsHoldingsDocumentManager = RdbmsHoldingsDocumentManager.getInstance();
        rdbmsHoldingsDocumentManager.transferInstances(holdingsIds, bibId);

    }

    public void transferItems(List<String> itemIds, String bibId) {
        RdbmsItemDocumentManager rdbmsItemDocumentManager = RdbmsItemDocumentManager.getInstance();
        rdbmsItemDocumentManager.transferItems(itemIds, bibId);
    }

    @Override
    public void createBibTrees(BibTrees bibTrees) {
        for (BibTree bibTree : bibTrees.getBibTrees()) {
            createBibTree(bibTree);
        }
    }

    @Override
    public void deleteBibs(List<String> bibIds) {
        for (String bibId : bibIds) {
            deleteBib(bibId);
        }
    }

    @Override
    public void validateInput(Object object) {
        DocumentManager documentManager = null;
        DocstoreDocument docstoreDocument = (DocstoreDocument) object;
        documentManager = DocumentManagerFactory.getInstance().getDocumentManager(docstoreDocument.getCategory(), docstoreDocument.getType(), docstoreDocument.getFormat());
        documentManager.validate(object);
    }

    @Override
    public void createLicense(License license) {
        DocumentManager documentManager = null;
        documentManager = DocumentManagerFactory.getInstance().getDocumentManager(license.getCategory(), license.getType(), license.getFormat());
        documentManager.create(license);
    }

    @Override
    public void createLicenses(Licenses licenses) {
        DocumentManager documentManager = null;
        for (License license : licenses.getLicenses()) {
            documentManager = DocumentManagerFactory.getInstance().getDocumentManager(license.getCategory(), license.getType(), license.getFormat());
            documentManager.create(license);
        }
    }

    @Override
    public License retrieveLicense(String licenseId) {
        DocumentManager documentManager = null;
        if(licenseId.startsWith(DocumentUniqueIDPrefix.PREFIX_WORK_LICENSE_ONIXPL)) {
            documentManager = DocumentManagerFactory.getInstance().getDocumentManager(DocCategory.WORK.getCode(), DocType.LICENSE.getCode(), DocFormat.ONIXPL.getCode());
        }
        else {
            documentManager = RdbmsLicenseAttachmentDocumentManager.getInstance();
        }

        License license = (License) documentManager.retrieve(licenseId);
        return license;
    }

    @Override
    public Licenses retrieveLicenses(List<String> licenseIds) {
        Licenses licenses = new Licenses();
        for(String id : licenseIds) {
            licenses.getLicenses().add(this.retrieveLicense(id));
        }
        return licenses;
    }

    @Override
    public void updateLicense(License license) {
        DocumentManager documentManager = DocumentManagerFactory.getInstance().getDocumentManager(license.getCategory(), license.getType(), license.getFormat());
        documentManager.update(license);
    }

    @Override
    public void updateLicenses(Licenses licenses) {
        DocumentManager documentManager = null;
        for(License license : licenses.getLicenses()) {
            documentManager = DocumentManagerFactory.getInstance().getDocumentManager(license.getCategory(), license.getType(), license.getFormat());
            documentManager.update(license);

        }
    }

    @Override
    public void deleteLicense(String licenseId) {
        DocumentManager documentManager = RdbmsLicenseOnixplDocumentManager.getInstance();
        documentManager.delete(licenseId);
    }

    @Override
    public void createAnalyticsRelation(String seriesHoldingsId, List<String> itemIds) {
        RdbmsHoldingsDocumentManager rdbmsHoldingsDocumentManager = RdbmsHoldingsDocumentManager.getInstance();
        rdbmsHoldingsDocumentManager.createAnalyticsRelation(seriesHoldingsId, itemIds);
    }

    @Override
    public void breakAnalyticsRelation(String seriesHoldingsId, List<String> itemIds) {
        RdbmsHoldingsDocumentManager rdbmsHoldingsDocumentManager = RdbmsHoldingsDocumentManager.getInstance();
        rdbmsHoldingsDocumentManager.breakAnalyticsRelation(seriesHoldingsId, itemIds);
    }

    @Override
    public Item retrieveItemByBarcode(String barcode) {
        RdbmsItemDocumentManager rdbmsItemDocumentManager = RdbmsItemDocumentManager.getInstance();
        Item item = rdbmsItemDocumentManager.retrieveItemByBarcode(barcode);
        return item;
    }

}