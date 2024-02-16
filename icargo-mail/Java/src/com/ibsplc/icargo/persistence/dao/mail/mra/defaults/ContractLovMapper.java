/*
 * ContractLovMapper.java Created on Apr 02 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ContractDetailsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-2521
 *
 */
public class ContractLovMapper implements Mapper<ContractDetailsVO> {
	
	private static final String CLASS_NAME = "ContractLovMapper";

	
    /**
     * @param rs
     * @return contractDetailsVO
     * @throws SQLException
     */
    public ContractDetailsVO map(ResultSet rs) throws SQLException {
    	
    	ContractDetailsVO contractDetailsVO=new ContractDetailsVO();
    	
    	contractDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
    	contractDetailsVO.setContractRefNo(rs.getString("CTRREFNUM"));
    	contractDetailsVO.setPaCode(rs.getString("GPACOD"));
    	contractDetailsVO.setAirlineCode(rs.getString("ARLCOD"));
    	contractDetailsVO.setVersion(rs.getInt("VERNUM"));
    	
        return contractDetailsVO;
    }

}
