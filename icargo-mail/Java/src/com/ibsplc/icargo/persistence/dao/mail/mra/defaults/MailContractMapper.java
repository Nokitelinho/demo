/*
 * MailContractMapper.java created on Mar 06, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-1946
 *
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Mar 6, 2007			  A-1872		Created
 */
public class MailContractMapper implements Mapper<MailContractVO>{
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public MailContractVO map( ResultSet rs ) throws SQLException {
		MailContractVO mailContractVO = new MailContractVO();
		mailContractVO.setContractReferenceNumber( rs.getString( "CTRREFNUM" ) );
		mailContractVO.setVersionNumber(rs.getString( "VERNUM" ) );
		mailContractVO.setGpaCode(rs.getString( "GPACOD" ) );
		mailContractVO.setAirlineCode(rs.getString( "ARLCOD" ) );
		mailContractVO.setAgreementStatus(rs.getString( "AGRSTA" ) );
		mailContractVO.setAgreementType(rs.getString( "AGRTYP" ) );
		if( rs.getDate( "CREDAT" ) != null) {
			mailContractVO.setCreationDate( new LocalDate
					( LocalDate.NO_STATION, Location.NONE, rs.getDate( "CREDAT" ) ) );
		}
		if( rs.getDate( "VLDFRMDAT" ) != null) {
			mailContractVO.setValidFromDate( new LocalDate
					( LocalDate.NO_STATION, Location.NONE, rs.getDate( "VLDFRMDAT" ) ) );
		}
		if( rs.getDate( "VLDTOODAT" ) != null) {
			mailContractVO.setValidToDate( new LocalDate
					( LocalDate.NO_STATION, Location.NONE, rs.getDate( "VLDTOODAT" ) ) );
		}
		return mailContractVO;
	}
}
