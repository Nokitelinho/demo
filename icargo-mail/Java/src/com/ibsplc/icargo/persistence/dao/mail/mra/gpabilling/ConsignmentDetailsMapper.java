/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.ConsignmentDetailsMapper.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Feb 8, 2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.ConsignmentDetailsMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Feb 8, 2019	:	Draft
 */
public class ConsignmentDetailsMapper implements Mapper<ConsignmentDocumentVO>{
	private static final String CLASS_NAME = "ConsignmentDetailsMapper";

	private Log log = LogFactory.getLogger("MRA:GPABILLING");

	@Override
	public ConsignmentDocumentVO map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME,"Mapper");
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setCompanyCode(rs.getString("CMPCOD"));
		consignmentDocumentVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		consignmentDocumentVO.setCurrency(rs.getString("CTRCURCOD"));
		consignmentDocumentVO.setDsnNumber(rs.getString("DSN"));
		consignmentDocumentVO.setMailSubClass(rs.getString("SUBCLS"));
		consignmentDocumentVO.setMailCategory(rs.getString("CAT"));
		consignmentDocumentVO.setOrgin(rs.getString("ORGCOD"));
		consignmentDocumentVO.setDestination(rs.getString("DSTCOD"));
		consignmentDocumentVO.setOriginOfficeOfExchange(rs.getString("OOE"));
		consignmentDocumentVO.setDestinationOfficeOfExchange(rs.getString("DOE"));
		consignmentDocumentVO.setRate(rs.getDouble("RAT"));
		consignmentDocumentVO.setPaCode(rs.getString("GPA"));
		consignmentDocumentVO.setIsUspsPerformed(rs.getString("PERMET"));
		consignmentDocumentVO.setMcaNumber(rs.getString("CCAREFNUM"));
		return consignmentDocumentVO;
	}

}
