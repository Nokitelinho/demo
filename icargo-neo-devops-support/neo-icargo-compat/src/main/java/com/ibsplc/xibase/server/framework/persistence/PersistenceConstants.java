/*
* @(#) PersistenceConstants.java 1.0 Apr 1, 2005 
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This Software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to License terms.
*
*/
package com.ibsplc.xibase.server.framework.persistence;

/**
 * Hold the common constants used by the persistence module
 */
/*
* Revision History
* Revision      Date                Author          Description
* 1.0           Apr 1, 2005         Binu K          First draft
*/
public interface PersistenceConstants {
	public static final String SET_PREFIX = "set";
    public static final String GET_PREFIX = "get";
    public static final String SPACE = " ";
	
	
    public static final String PERSISTENCE_PROPERTIES = "persistence.properties";
	
    public static final String PERSISTENCE_MAPPING_FILE = "persistence.mappings.xml";

    public static final String QUERY_MAPPING_FILE_XSD = "query.definition.xsd";

    public static final String PERSISTENCE_MAPPING_FILE_XSD = "persistence.mappings.xsd";

	
}
