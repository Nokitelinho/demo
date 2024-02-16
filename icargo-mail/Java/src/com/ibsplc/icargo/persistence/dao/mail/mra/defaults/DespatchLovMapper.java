/*
 * DespatchLovMapper.java Created on Apr 25, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DespatchLovVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */


public class DespatchLovMapper implements Mapper<DespatchLovVO> {
	private static final String CLASS_NAME = "DespatchLovMapper";

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	 /**
     * @param rs
     * @return DespatchLovVO
     * @throws SQLException
     */
    
    public DespatchLovVO map(ResultSet rs) throws SQLException {
    	log.entering(CLASS_NAME,"map");
    	DespatchLovVO despatchLovVO=new DespatchLovVO();
    	despatchLovVO.setCompanyCode(rs.getString("CMPCOD"));
    	despatchLovVO.setDespatch(rs.getString("BLGBASNUM"));
    	despatchLovVO.setGpaCode(rs.getString("POACOD"));
    	despatchLovVO.setDsn(rs.getString("DSN"));
    /*	if(rs.getString("BLGBASNUM")!=null && rs.getString("BLGBASNUM").trim().length()>0){
    		System.out.println("basis type"+rs.getString("BLGBASNUM"));
    		System.out.println("basis lenggth"+rs.getString("BLGBASNUM").trim().length());
    		if(rs.getString("BLGBASNUM").trim().length()==20){
    			String dsn=rs.getString("BLGBASNUM").substring(15,20);
    			despatchLovVO.setDsn(dsn);
    		}else{
    			despatchLovVO.setDsn("");
    		}
    	}*/
    	

        return despatchLovVO;
    }

}
