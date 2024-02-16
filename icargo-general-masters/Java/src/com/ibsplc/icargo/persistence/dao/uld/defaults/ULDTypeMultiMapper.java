/*
 * ULDTypeMultiMapper.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.util.List;

import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

/**
 * @author A-1496
 *
 */
public class ULDTypeMultiMapper implements MultiMapper{
    
    /**
     * Method for getting the map
     * @param rs
     * @return List<String>
     */
    public List<String> map(ResultSet rs){
    	return null;
    }

}
