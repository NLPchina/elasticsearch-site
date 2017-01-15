package org.nlpcn.elasticsearch.site;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;

public class ESClient {

    private static final Logger LOG = LoggerFactory.getLogger(ESClient.class);

    private TransportClient client;

    private final static String COLON = ":";

    public ESClient(String... clusterNodes) {
        try {
            client = TransportClient.builder().build();
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
