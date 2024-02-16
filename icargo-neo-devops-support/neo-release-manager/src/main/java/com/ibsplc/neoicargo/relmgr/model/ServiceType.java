/*
 * ServiceType.java Created on 01/10/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.relmgr.model;

/**
 * @author jens
 */
public enum ServiceType {

    /**
     * The business domain services which hosts the private apis of the module
     */
    DOMAIN_SERVICE,
    /**
     * EBL Services
     */
    EBL,
    /**
     * Integration Services
     */
    EAI,
    /**
     * Custom internal services like DB connectors, monitoring etc
     */
    INTERNAL_SERVICE,
    /**
     * Web BFF
     */
    WEB_BFF,
    /**
     * Front end services
     */
    WEB_FE,
    /**
     * Frontend gateway services
     */
    WEB_GW,

    /**
     * Debezium Kafka Connector
      */
    DBZ_CONNECTOR

}
