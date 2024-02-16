package com.ibsplc.neoicargo.mailmasters.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mailmasters.vo.PostalAdministrationVO;
import lombok.extern.slf4j.Slf4j;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mailtracking.defaults.PostalAdministrationMapper.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-4809	:	08-Jan-2014	:	Draft
 */
@Slf4j
public class PostalAdministrationMapper implements Mapper<PostalAdministrationVO> {
	/** 
	* Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet) Added by 			: A-4809 on 08-Jan-2014 Used for 	: Parameters	:	@param arg0 Parameters	:	@return Parameters	:	@throws SQLException 
	*/
	@Override
	public PostalAdministrationVO map(ResultSet rs) throws SQLException {
		log.debug("MAILTRACKING_DEFAULTS" + " : " + "PostalAdministrationMapper" + " Entering");
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
		postalAdministrationVO.setSettlementCurrencyCode(rs.getString("STLCURCOD"));
		log.debug("" + "postalAdministrationVO from mapper++++" + " " + postalAdministrationVO);
		if (rs.getString("PARVAL") != null && rs.getString("PARVAL").trim().length() > 0) {
			postalAdministrationVO.setPASSPA(true);
		}
		postalAdministrationVO.setProformaInvoiceRequired(rs.getString("PROINVREQ"));
		log.debug("MAILTRACKING_DEFAULTS" + " : " + "PostalAdministrationMapper" + " Exiting");
		return postalAdministrationVO;
	}
}
