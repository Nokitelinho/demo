/*
 * ElasticSearchUtils.java Created on 27/01/21
 *
 * Copyright 2021 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.txprobe.aggregator.handler.outbound.es;

import org.apache.http.HttpHost;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jens
 */
public class ElasticSearchUtils {

    public static RestHighLevelClient createClient(String serverUrl) {
        String connectProto = serverUrl.substring(0, serverUrl.indexOf(":"));
        String nodePairMap = serverUrl.substring(serverUrl.lastIndexOf("/") + 1);
        if ("http".equals(connectProto) || "https".equals(connectProto)) {
            List<HttpHost> httpHosts = new ArrayList<>(3);
            for (String nodePair : nodePairMap.split(",")) {
                String[] hostPort = nodePair.split(":");
                int port = Integer.parseInt(hostPort[1]);
                HttpHost httpHost = new HttpHost(hostPort[0], port, connectProto);
                httpHosts.add(httpHost);
            }
            RestClientBuilder rbul = RestClient.builder(httpHosts.toArray(HttpHost[]::new)).setHttpClientConfigCallback(
                   httpAsyncClientBuilder -> {
                       final int maxIOThreads = Math.min(4, Runtime.getRuntime().availableProcessors());
                       httpAsyncClientBuilder.setDefaultIOReactorConfig(
                               IOReactorConfig.custom()
                                       .setIoThreadCount(maxIOThreads)
                                       .build());
                       return httpAsyncClientBuilder;
                   }
            );
            rbul.setStrictDeprecationMode(false);
            return new RestHighLevelClient(rbul);
        } else
            throw new IllegalArgumentException("only elasticsearch transport client is supported - [estc, http, https]");
    }

}
