package com.ibsplc.neoicargo.stubs.runner;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.cloud.contract.stubrunner.HttpServerStubConfiguration;
import org.springframework.cloud.contract.stubrunner.HttpServerStubConfigurer;
import org.springframework.cloud.contract.stubrunner.provider.wiremock.WireMockHttpServerStubConfigurer;

/**
 * Each mock server is a separate jetty server instance, setup the thread budged to limit the thread counts
 */
public class LeanMeanWiremockConfiguration extends WireMockHttpServerStubConfigurer {

    @Override
    public WireMockConfiguration configure(WireMockConfiguration httpStubConfiguration, HttpServerStubConfiguration httpServerStubConfiguration) {
        httpStubConfiguration.asynchronousResponseEnabled(false);
        httpStubConfiguration.containerThreads(5);
        httpStubConfiguration.jettyAcceptors(1);
        httpStubConfiguration.jettyAcceptQueueSize(32);
        httpStubConfiguration.jettyHeaderBufferSize(2048);
        httpStubConfiguration.disableRequestJournal();
        System.out.println("LeanMeanWiremockConfiguration :: WireMockConfiguration : " + httpStubConfiguration + " HttpServerStubConfiguration : " + httpServerStubConfiguration);
        return httpStubConfiguration;
    }
}
