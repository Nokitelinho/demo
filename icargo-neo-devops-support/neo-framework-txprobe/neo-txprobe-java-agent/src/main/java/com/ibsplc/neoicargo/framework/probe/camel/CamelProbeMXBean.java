/* CamelProbeMXBean.java Created on 01/12/22
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.camel;

import javax.management.MXBean;

/**
 * @author jens
 */
@MXBean
public interface CamelProbeMXBean {

    boolean isLogBody();

    void setLogBody(boolean logBody);

    boolean isLogHeaders();

    void setLogHeaders(boolean logHeaders);

    boolean isLogProperties();

    void setLogProperties(boolean logProperties);

    String[] getDisabledRoutes();

    void setDisabledRoutes(String[] disabledRoutes);

}
