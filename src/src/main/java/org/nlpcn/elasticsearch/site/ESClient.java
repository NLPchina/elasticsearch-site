package org.nlpcn.elasticsearch.site;

import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class ESClient {

	private static final Logger LOG = LoggerFactory.getLogger(ESClient.class);

	private TransportClient client;

	private final static String COLON = ":";

	public static void main(String[] args) {
		ESClient esClient = new ESClient("127.0.0.1" + "" + "" + ":9300");

		System.out.println(esClient);

		JSONObject job = new JSONObject();

		job.put("user", "kimchy");
		job.put("postDate", new Date());
		job.put("message", "trying out Elastic     Search");

		IndexResponse response = esClient.client.prepareIndex("twitter", "tweet", "1").setSource(job.toJSONString()).execute().actionGet();
		
		System.out.println(response);

		esClient.destroy();

	}

	public ESClient(String... clusterNodes) {
		try {

			Settings settings = Settings.builder().put("client.transport.ignore_cluster_name", true).build();

			client = new PreBuiltTransportClient(settings);

			for (String clusterNode : clusterNodes) {
				String hostName = substringBefore(clusterNode, COLON);
				String port = substringAfter(clusterNode, COLON);

				if (StringUtils.isEmpty(hostName)) {
					throw new IllegalArgumentException("missing host name in 'clusterNodes'");
				}

				if (StringUtils.isEmpty(port)) {
					throw new IllegalArgumentException("missing port in 'clusterNodes'");
				}

				LOG.info("adding transport node : " + clusterNode);
				client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostName), Integer.valueOf(port)));
			}

			client.connectedNodes();
		} catch (UnknownHostException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		}
	}

	public Client getClient() {
		return client;
	}

	public void destroy() {
		try {
			LOG.info("Closing elasticsearch client");
			if (client != null) {
				client.close();
			}
		} catch (Exception e) {
			LOG.error("Error closing elasticsearch client: " + ExceptionUtils.getStackTrace(e));
		}
	}
}
