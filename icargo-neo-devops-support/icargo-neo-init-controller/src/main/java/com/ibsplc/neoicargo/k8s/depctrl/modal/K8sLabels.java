/*
 * K8sLabels.java Created on 12/04/21
 *
 * Copyright 2021 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.k8s.depctrl.modal;

/**
 * @author jens
 */
public interface K8sLabels {

    String KEY_APP = "app";
    String KEY_PRCTYP = "prctyp";
    String KEY_SERVICE_TYPE = "srvtyp";
    String KEY_TENANT = "tenant";

    /* Common Process Types */
    String PRCTYP_JAVA = "java";
    String PRCTYP_NODE = "node";

    /* Common Service Types */
    String SRVTYP_DOMAIN_SERVICE = "domain_service";
    String SRVTYP_BFF = "web_bff";
    String SRVTYP_GW = "web_gw";
    String SRVTYP_FE = "web_fe";
    String SRVTYP_INTERNAL = "internal_service";


}
