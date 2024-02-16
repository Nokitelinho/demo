/*
 * KafkaProbeConfig.java Created on 07/12/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.kafka;

import lombok.ToString;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author jens
 */
@ToString
public class KafkaProbeConfig implements KafkaProbeMXBean {

    private boolean logHeaders = true;
    private boolean logBody = true;
    private Pattern[] disabledTopics;

    @Override
    public boolean isLogHeaders() {
        return this.logHeaders;
    }

    @Override
    public void setLogHeaders(boolean flag) {
        this.logHeaders = flag;
    }

    @Override
    public boolean isLogBody() {
        return this.logBody;
    }

    @Override
    public void setLogBody(boolean flag) {
        this.logBody = flag;
    }

    @Override
    public String[] getDisabledTopics() {
        if (this.disabledTopics == null || this.disabledTopics.length == 0)
            return null;
        return Arrays.stream(this.disabledTopics).map(Pattern::toString).toArray(String[]::new);
    }

    @Override
    public void setDisabledTopics(String[] topics) {
        if (topics == null || topics.length == 0)
            this.disabledTopics = null;
        this.disabledTopics = Arrays.stream(topics).map(Pattern::compile).toArray(Pattern[]::new);
    }

    /**
     * Check if the topic matches the pattern
     *
     * @param topic
     * @return
     */
    public boolean isTopicDisabled(String topic) {
        if (this.disabledTopics == null || this.disabledTopics.length == 0)
            return false;
        for (int x = 0; x < this.disabledTopics.length; x++) {
            boolean answer = this.disabledTopics[x].matcher(topic).matches();
            if (answer)
                return true;
        }
        return false;
    }
}
