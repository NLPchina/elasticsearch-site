package org.nlpcn.elasticsearch.site.sql;

import org.nlpcn.elasticsearch.site.ESClient;
import org.nlpcn.es4sql.SearchDao;
import org.nlpcn.es4sql.exception.SqlParseException;
import org.nlpcn.jcoder.run.annotation.Execute;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.annotation.Param;
import org.slf4j.Logger;

import java.sql.SQLFeatureNotSupportedException;

public class SqlApi {

    @Inject
    private ESClient esClient;

    @Inject
    private Logger log;

    @Execute
    public Object execute(String sql) throws SQLFeatureNotSupportedException, SqlParseException {
        return new SearchDao(esClient.getClient()).explain(sql).explain().get();
    }

    @Execute
    public Object explain(String sql) throws SQLFeatureNotSupportedException, SqlParseException {
        return new SearchDao(esClient.getClient()).explain(sql).explain().explain();
    }

    @Execute
    public Object scroll(@Param(value = "scroll", df = "1m") String scroll, @Param("scrollId") String scrollId) {
        return esClient.getClient().prepareSearchScroll(scrollId).setScroll(scroll).get();
    }
}
