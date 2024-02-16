/*
 * CamelProbeConfig.java Created on 01/12/22
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.camel;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author jens
 */
public class CamelProbeConfig implements CamelProbeMXBean{

    private boolean logBody = true;
    private boolean logHeaders = true;
    private boolean logProperties = true;
    private Pattern[] disabledRoutes;

    @Override
    public boolean isLogBody() {
        return this.logBody;
    }

    @Override
    public void setLogBody(boolean logBody) {
        this.logBody = logBody;
    }

    @Override
    public boolean isLogHeaders() {
        return this.logHeaders;
    }

    @Override
    public void setLogHeaders(boolean logHeaders) {
        this.logHeaders = logHeaders;
    }

    @Override
    public boolean isLogProperties() {
        return this.logProperties;
    }

    @Override
    public void setLogProperties(boolean logProperties) {
        this.logProperties = logProperties;
    }

    @Override
    public String[] getDisabledRoutes() {
        if (this.disabledRoutes == null || this.disabledRoutes.length == 0)
            return null;
        return Arrays.stream(this.disabledRoutes).map(Pattern::toString).toArray(String[]::new);
    }

    @Override
    public void setDisabledRoutes(String[] disabledRoutes) {
        if (disabledRoutes == null || disabledRoutes.length == 0)
            this.disabledRoutes = null;
        this.disabledRoutes = Arrays.stream(disabledRoutes).map(Pattern::compile).toArray(Pattern[]::new);
    }

    /**
     * Check if the topic matches the pattern
     *
     * @param route
     * @return
     */
    public boolean isRouteDisabled(String route) {
        if (this.disabledRoutes == null || this.disabledRoutes.length == 0)
            return false;
        for (int x = 0; x < this.disabledRoutes.length; x++) {
            boolean answer = this.disabledRoutes[x].matcher(route).matches();
            if (answer)
                return true;
        }
        return false;
    }

}
