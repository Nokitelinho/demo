/*
 * NeoReleaseManagerApplication.java Created on 01/10/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.relmgr;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.ibsplc.neoicargo.relmgr"})
public class NeoReleaseManagerApplication {

    /**
     * Main
     * @param args
     */
    public static void main(String[] args) {
        //See https://github.com/vladmihalcea/hibernate-types#how-to-remove-the-hypersistence-optimizer-banner-from-the-log
        System.setProperty("hibernate.types.print.banner","false");
        SpringApplication appln = new SpringApplication(NeoReleaseManagerApplication.class);
        appln.setBannerMode(Banner.Mode.CONSOLE);
        appln.setWebApplicationType(WebApplicationType.SERVLET);
        appln.setMainApplicationClass(NeoReleaseManagerApplication.class);
        appln.run(args);
    }

}
