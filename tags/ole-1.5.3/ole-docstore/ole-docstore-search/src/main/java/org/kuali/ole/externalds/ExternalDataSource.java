package org.kuali.ole.externalds;

import org.kuali.ole.docstore.OleException;
import org.kuali.ole.docstore.discovery.model.SearchParams;
import org.kuali.ole.docstore.model.xmlpojo.work.bib.marc.WorkBibMarcRecord;
import org.kuali.ole.docstore.model.xmlpojo.work.bib.marc.WorkBibMarcRecords;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ND6967
 * Date: 2/19/13
 * Time: 12:40 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ExternalDataSource {
    public List<String> searchForBibs(SearchParams searchParams, DataSourceConfig dataSourceConfigInfo) throws Exception, OleException;
}
