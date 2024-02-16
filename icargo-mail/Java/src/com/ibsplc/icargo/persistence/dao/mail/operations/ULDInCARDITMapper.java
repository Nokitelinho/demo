/*
 * ULDInCARDITMapper.java Created on Jun 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.PreAdviceDetailsVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-2037
 *
 */
public class ULDInCARDITMapper implements Mapper<PreAdviceDetailsVO>{
	 /**
	  * @param rs
	  * @return 
	  * @throws SQLException
	  */
	public PreAdviceDetailsVO map(ResultSet rs) throws SQLException{
		
		PreAdviceDetailsVO preAdviceDetailsVO=new PreAdviceDetailsVO();
		preAdviceDetailsVO.setTotalbags(rs.getInt("NETCOUNT"));
		//preAdviceDetailsVO.setTotalWeight(rs.getDouble("NETWGT"));
		preAdviceDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("NETWGT")));//added by A-7371
		preAdviceDetailsVO.setUldNumbr(rs.getString("CONNUM"));
		return preAdviceDetailsVO;
	}

}
