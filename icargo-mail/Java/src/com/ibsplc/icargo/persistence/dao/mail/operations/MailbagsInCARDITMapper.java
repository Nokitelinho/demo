/*
 * MailbagsInCARDITMapper.java Created on June 29, 2006
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
 * @author 
 * This method is used to Map the ResultSet into PreAdviceDetailsVO
 * 
 */
public class MailbagsInCARDITMapper implements Mapper<PreAdviceDetailsVO>{
	 /*
	  *  (non-Javadoc)
	  * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	  */
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public PreAdviceDetailsVO map(ResultSet rs) throws SQLException{
		PreAdviceDetailsVO preAdviceDetailsVO=new PreAdviceDetailsVO();
		preAdviceDetailsVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFF"));
		preAdviceDetailsVO.setMailCategory(rs.getString("MALCTGCOD"));
		preAdviceDetailsVO.setOriginExchangeOffice(rs.getString("ORGEXGOFF"));
		preAdviceDetailsVO.setTotalbags(rs.getInt("COUNT"));
		//preAdviceDetailsVO.setTotalWeight(rs.getDouble("RCPWGT"));
		preAdviceDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("RCPWGT")));//added by A-7371
		preAdviceDetailsVO.setUldNumbr(rs.getString("CONNUM"));		
		return preAdviceDetailsVO;
	}

}
