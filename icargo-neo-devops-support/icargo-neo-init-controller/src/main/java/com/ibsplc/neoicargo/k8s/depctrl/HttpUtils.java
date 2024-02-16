/*
 * HttpUtils.java Created on 27/05/21
 *
 * Copyright 2021 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.k8s.depctrl;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;

/**
 * @author jens
 */
public class HttpUtils {

    static final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(Duration.ofMillis(3000)).readTimeout(Duration.ofMillis(3000))
            .build();
    static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static boolean healthCheck(String host, int port, String path){
        String url = String.format("http://%s:%s%s",host, port, path);
        Request httpGetRequest = new Request.Builder().get().url(url).build();
        try (Response response = httpClient.newCall(httpGetRequest).execute()){
            return response.isSuccessful();
        } catch (IOException e) {
            logger.debug("Error while connecting to " + url, e);
            return false;
        }
    }
}
