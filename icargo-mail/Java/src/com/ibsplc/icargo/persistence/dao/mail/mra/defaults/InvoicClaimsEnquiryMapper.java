/*
 * InvoicClaimsEnquiryMapper.java Created on Jul 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicClaimsEnquiryVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2270
 *
 */

public class InvoicClaimsEnquiryMapper implements Mapper<MailInvoicClaimsEnquiryVO> {

	private static final String CLASS_NAME = "InvoicEnquiryMapper";

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
    public MailInvoicClaimsEnquiryVO map(ResultSet rs) throws SQLException {
    	log.entering(CLASS_NAME,"Mapper");
    	
    	MailInvoicClaimsEnquiryVO mailInvoicClaimsEnquiryVO = new MailInvoicClaimsEnquiryVO();
    	
    	//mailInvoicClaimsEnquiryVO.setCalimType(rs.getString("CLMCOD"));
    	mailInvoicClaimsEnquiryVO.setClaimStatus(rs.getString("CLMSTA"));
    	mailInvoicClaimsEnquiryVO.setCarrierCode(rs.getString("SECFLT"));
    	mailInvoicClaimsEnquiryVO.setCalimType(rs.getString("CLMCOD"));
    	mailInvoicClaimsEnquiryVO.setPoaCode(rs.getString("POACOD"));
    	mailInvoicClaimsEnquiryVO.setSectorOrigin(rs.getString("SECORG"));
    	mailInvoicClaimsEnquiryVO.setSectorDestination(rs.getString("SECDST"));
    	mailInvoicClaimsEnquiryVO.setReceptacleIdentifier(rs.getString("RCPIDR"));
    	mailInvoicClaimsEnquiryVO.setSector(rs.getString("SECORG").concat("-").concat(rs.getString("SECDST")));
    	if(rs.getDate("CLMDAT") != null){
    		mailInvoicClaimsEnquiryVO.setClaimDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("CLMDAT")));				
		   }
    	if(rs.getDate("SCNDAT") != null){
    		mailInvoicClaimsEnquiryVO.setTransferDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("SCNDAT")));				
		   }
    	return mailInvoicClaimsEnquiryVO;
    }

}
