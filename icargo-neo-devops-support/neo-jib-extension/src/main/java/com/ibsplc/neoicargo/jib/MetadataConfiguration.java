/*
 * MetadataConfiguration.java Created on 02/08/22
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.jib;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jens
 */
public class MetadataConfiguration {

    private List<String> filters;

    public MetadataConfiguration(List<String> filters) {
        this.filters = filters;
    }

    public MetadataConfiguration(){
        this.filters = new ArrayList<>();
    }

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }
}
