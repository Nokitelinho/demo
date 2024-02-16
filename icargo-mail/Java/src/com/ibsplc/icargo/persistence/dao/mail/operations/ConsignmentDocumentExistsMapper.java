
/*
 * ConsignmentDocumentExistsMapper.java Created on July 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */ 
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-1883
 * 
 */
public class ConsignmentDocumentExistsMapper implements
		Mapper<ConsignmentDocumentVO> {

	private Log log = LogFactory.getLogger("mail.operations");

	/**
	 * @author A-1883
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public ConsignmentDocumentVO map(ResultSet resultSet) throws SQLException {
		log.entering("ConsignmentDocumentExistsMapper", "map");
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setCompanyCode(resultSet.getString("CMPCOD"));
		consignmentDocumentVO.setConsignmentNumber(resultSet
				.getString("CSGDOCNUM"));
		consignmentDocumentVO.setPaCode(resultSet.getString("POACOD"));
		consignmentDocumentVO.setConsignmentSequenceNumber(resultSet
				.getInt("CSGSEQNUM"));
		
		//aded for cardit enquiry
		String senderId = resultSet.getString("SDRIDR");
		if(senderId != null) {
			consignmentDocumentVO.setPaCode(senderId);
		}	
		
		Date csgCmpDat =  resultSet.getDate("CSGCMPDAT");
		if(csgCmpDat != null) {
			consignmentDocumentVO.setConsignmentDate(
					new LocalDate(LocalDate.NO_STATION, Location.NONE,
					csgCmpDat));
		}
		
		log.exiting("ConsignmentDocumentExistsMapper", "map");
		return consignmentDocumentVO;
	}

}
