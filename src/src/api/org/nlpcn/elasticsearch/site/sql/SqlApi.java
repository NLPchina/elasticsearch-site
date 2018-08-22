package org.nlpcn.elasticsearch.site.sql;

import java.sql.SQLFeatureNotSupportedException;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.nlpcn.elasticsearch.site.ESClient;
import org.nlpcn.es4sql.SearchDao;
import org.nlpcn.es4sql.exception.SqlParseException;
import org.nlpcn.jcoder.run.annotation.Execute;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.annotation.Param;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class SqlApi {

	@Inject
	private ESClient esClient;

	@Inject
	private Logger log;

	@Execute
	public Object execute(String sql) throws SQLFeatureNotSupportedException, SqlParseException {
		ActionResponse rep = new SearchDao(esClient.getClient()).explain(sql).explain().get();
		return rep != null ? JSONObject.parseObject(rep.toString()) : null;
	}

	@Execute
	public Object explain(String sql) throws SQLFeatureNotSupportedException, SqlParseException {
		return new SearchDao(esClient.getClient()).explain(sql).explain().explain();
	}

	@Execute
	public Object scroll(@Param(value = "scroll", df = "1m") String scroll, @Param("scrollId") String scrollId) {
		SearchResponse rep = esClient.getClient().prepareSearchScroll(scrollId).setScroll(scroll).get();
		return rep != null ? JSONObject.parseObject(rep.toString()) : null;
	}
}
