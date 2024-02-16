/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mailtracking.defaults.PostalAdministrationMapper.java
 *
 *	Created by	:	A-4809
 *	Created on	:	08-Jan-2014
 *
 *  Copyright 2014 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mailtracking.defaults.PostalAdministrationMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	08-Jan-2014	:	Draft
 */
public class PostalAdministrationMapper implements Mapper<PostalAdministrationVO>{

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	
	/**
	 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 *	Added by 			: A-4809 on 08-Jan-2014
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SQLException 
	 */
	@Override
	public PostalAdministrationVO map(ResultSet rs) throws SQLException {
		log.entering("MAILTRACKING_DEFAULTS", "PostalAdministrationMapper");
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		postalAdministrationVO.setCompanyCode(rs.getString("CMPCOD"));
		postalAdministrationVO.setPaCode(rs.getString("POACOD"));
		postalAdministrationVO.setPaName(rs.getString("POANAM"));
		postalAdministrationVO.setAddress(rs.getString("POAADR"));
		postalAdministrationVO.setAutoEmailReqd(rs.getString("AUTEMLREQ"));
		postalAdministrationVO.setEmail(rs.getString("EMLADR"));
		postalAdministrationVO.setBillingSource(rs.getString("BLGSRC"));
		postalAdministrationVO.setBillingFrequency(rs.getString("BLGFRQ"));
		postalAdministrationVO.setCountryCode(rs.getString("CNTCOD"));
		postalAdministrationVO.setSecondaryEmail1(rs.getString("SECEMLADRONE"));
		postalAdministrationVO.setSecondaryEmail2(rs.getString("SECEMLADRTWO"));
		//Added by A-8527 for ICRD-324283
		postalAdministrationVO.setSettlementCurrencyCode(rs.getString("STLCURCOD"));
		log.log(Log.FINE, "postalAdministrationVO from mapper++++",postalAdministrationVO);
		if(rs.getString("PARVAL")!=null && rs.getString("PARVAL").trim().length()>0){
			postalAdministrationVO.setPASSPA(true);
		}
		postalAdministrationVO.setProformaInvoiceRequired(rs.getString("PROINVREQ"));
		log.exiting("MAILTRACKING_DEFAULTS", "PostalAdministrationMapper");
		return postalAdministrationVO;
	}

}
