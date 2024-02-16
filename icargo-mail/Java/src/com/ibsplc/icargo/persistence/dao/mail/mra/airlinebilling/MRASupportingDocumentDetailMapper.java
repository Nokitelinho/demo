/*
 * MRASupportingDocumentDetailMapper.java Created on Oct 29, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SisSupportingDocumentDetailsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-8061
 *
 */
public class MRASupportingDocumentDetailMapper implements Mapper<SisSupportingDocumentDetailsVO>{
	private Log log = LogFactory.getLogger("MRASupportingDocumentDetailMapper");

	/**
	 * @param rs
	 * @return RejectionMemoVO
	 * @exception SQLException
	 *
	 */

	public SisSupportingDocumentDetailsVO map(ResultSet rs) throws SQLException {
		
		log.entering("MRASupportingDocumentDetailMapper---------", "Map Method");
	
		
	    SisSupportingDocumentDetailsVO sisSupportingDocumentDetailsVO = new SisSupportingDocumentDetailsVO();
	    
		sisSupportingDocumentDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		sisSupportingDocumentDetailsVO.setBilledAirline(rs.getInt("ARLIDR"));
		sisSupportingDocumentDetailsVO.setInterlineBillingType(rs.getString("INTBLGTYP"));
		sisSupportingDocumentDetailsVO.setClearancePeriod(rs.getString("DOCCLRPRD"));
		sisSupportingDocumentDetailsVO.setBillingType(rs.getString("BLGTYP"));
		sisSupportingDocumentDetailsVO.setInvoiceNumber(rs.getString("DOCINVNUM"));
		sisSupportingDocumentDetailsVO.setInvoiceSerialNumber(rs.getInt("DOCINVSERNUM"));
		//sisSupportingDocumentDetailsVO.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
		sisSupportingDocumentDetailsVO.setDuplicateNumber(rs.getInt("DUPNUM"));
		sisSupportingDocumentDetailsVO.setSequenceNumber(rs.getInt("SEQNUM"));
		//sisSupportingDocumentDetailsVO.setDocOwnerId(rs.getInt("DOCOWRIDR"));
		sisSupportingDocumentDetailsVO.setMemoNumber(rs.getString("MEMNUM"));
		sisSupportingDocumentDetailsVO.setDocumentSerialNumber(rs.getInt("DOCSERNUM"));
		sisSupportingDocumentDetailsVO.setFilename(rs.getString("FILNAM"));
		sisSupportingDocumentDetailsVO.setCarierOrigin(rs.getString("CARORG"));
		sisSupportingDocumentDetailsVO.setCarierDestination(rs.getString("CARDST"));

		
		log.exiting("MRASupportingDocumentDetailMapper", "Map Method");
		return sisSupportingDocumentDetailsVO;
	}


}
