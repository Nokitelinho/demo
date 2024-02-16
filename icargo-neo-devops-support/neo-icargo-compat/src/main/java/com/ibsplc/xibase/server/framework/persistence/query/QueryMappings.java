/*
 * @(#) QueryMappings.java 1.0 Apr 26, 2005
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This Software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to License terms.
 *
 */
package com.ibsplc.xibase.server.framework.persistence.query;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A container for parsed query/procedure definitions.
 */
/*
 * Revision History
 * Revision         Date                Author          Description
 * 1.0          Apr 26, 2005            Binu K          First draft
 */
public class QueryMappings {

    public static final String DOCUMENT_START = "QRY:Definitions";
    public static final String QUERY_DEFINITION = "QRY:Definition";
    public static final String QUERY_ID = "QRY:Id";
    public static final String QUERY_SOURCE = "QRY:Source";
    public static final String NAME_ATTRIBUTE = "Name";
    public static final String TYPE_ATTRIBUTE = "Type";

    private static class AllMappings {
        public HashMap<String, String> queryMappings;

        public HashMap<String, String> procedureMappings;
    }

    private static boolean isBuilt;

    private static ConcurrentHashMap<String, AllMappings> queryMappings = new ConcurrentHashMap<>();

    public static boolean isPresent(String module) {
        return (queryMappings.get(module) != null);
    }

    /**
     * Get all the parsed query definitions for a module
     *
     * @param module
     * @return
     */
    public static final HashMap<String, String> getQueryMappings(String module) {
        AllMappings maps = queryMappings.get(module);
        HashMap<String, String> qryMaps = null;
        if (maps != null) {
            qryMaps = maps.queryMappings;
        }
        return qryMaps;
    }

    /**
     * Get all the parsed procedure definitions for a module
     *
     * @param module
     * @return
     */
    public static final HashMap<String, String> getProcedureMappings(
            String module) {
        AllMappings maps = queryMappings.get(module);
        HashMap<String, String> qryMaps = null;
        if (maps != null) {
            qryMaps = maps.procedureMappings;
        }
        return qryMaps;
    }

}
