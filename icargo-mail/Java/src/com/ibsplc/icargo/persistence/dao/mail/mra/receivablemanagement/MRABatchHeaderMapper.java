/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.MRABatchHeaderMapper.java
 *
 *	Created by	:	A-5219
 *	Created on	:	15-Nov-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.MRABatchHeaderMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	12-Nov-2021	:	Draft
 */
public class MRABatchHeaderMapper implements Mapper<InvoiceSettlementVO> {
	
	public InvoiceSettlementVO map(ResultSet rs) throws SQLException {
		
		InvoiceSettlementVO vo = new InvoiceSettlementVO();
		
		vo.setCompanyCode(rs.getString("CMPCOD"));
		vo.setSettlementId(rs.getString("BTHSTLIDR"));
		vo.setGpaCode(rs.getString("POACOD"));
		vo.setBatchSeqNumber(rs.getLong("BTHSTLSEQNUM")); 
		vo.setMailsequenceNum(rs.getLong("MALSEQNUM"));
		String gpainvCheck = rs.getString("GPAINVMISMATCH");
		if( gpainvCheck != null){
			String[] vals = gpainvCheck.split("-"); 
			if(vals!=null && vals.length==2){
				if(MailConstantsVO.FLAG_YES.equals(vals[0])){
					vo.setInvoiceNotPresent(true);
				}
				if(MailConstantsVO.FLAG_YES.equals(vals[1])){
					vo.setGpaMismatch(true);
				}
			}
		}else{
			vo.setGpaMismatch(true);
			vo.setInvoiceNotPresent(true);
		}
		return  vo;
	}

}
