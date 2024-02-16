/*
 * KafkaProbeMXBean.java Created on 07/12/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.kafka;

import javax.management.MXBean;

/**
 * @author jens
 */
@MXBean
public interface KafkaProbeMXBean {

    boolean isLogHeaders();

    void setLogHeaders(boolean flag);

    boolean isLogBody();

    void setLogBody(boolean flag);

    String[] getDisabledTopics();

    void setDisabledTopics(String[] topics);

}
