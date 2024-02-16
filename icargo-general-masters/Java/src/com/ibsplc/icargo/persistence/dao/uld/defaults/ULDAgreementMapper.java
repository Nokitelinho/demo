/*
 * ULDAgreementMapper.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class ULDAgreementMapper implements Mapper<ULDAgreementVO>{
	
	 private Log log = LogFactory.getLogger(" ULD_DEFAULTS");
  /**
   * @param rs
   * @return ULDAgreementVO
   * @throws SQLException
   */
    public ULDAgreementVO map(ResultSet rs)
    throws SQLException{
    	log.entering("ULDAgreementMapper","map");
    	ULDAgreementVO uldAgreementVO = new ULDAgreementVO();
    	uldAgreementVO.setCompanyCode(rs.getString("CMPCOD"));
    	uldAgreementVO.setAgreementNumber(rs.getString("AGRMNTNUM"));
    	
    	Date datOne = rs.getDate("AGRMNTDAT");
    	if(datOne != null){
    		uldAgreementVO.setAgreementDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,datOne));
    	}
    	
    	Date datTwo = rs.getDate("AGRMNTFRMDAT");
    	if(datTwo != null){
    		uldAgreementVO.setAgreementFromDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,datTwo));
    	}
    	
    	Date datThree = rs.getDate("AGRMNTTOODAT");
    	if(datThree != null){
    		uldAgreementVO.setAgreementToDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,datThree));
    	}
    	
    	uldAgreementVO.setAgreementStatus(rs.getString("AGRMNTSTA"));
    	uldAgreementVO.setPartyType(rs.getString("PTYTYP"));
    	uldAgreementVO.setPartyCode(rs.getString("PTYCOD"));
    	uldAgreementVO.setPartyName(rs.getString("PTYNAM"));
    	uldAgreementVO.setFromPartyType(rs.getString("FRMPTYTYP"));
    	uldAgreementVO.setFromPartyCode(rs.getString("FRMPTYCOD"));
    	uldAgreementVO.setFromPartyName(rs.getString("FRMPTYNAM"));
    	uldAgreementVO.setTxnType(rs.getString("TXNTYP"));
    	uldAgreementVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
    	uldAgreementVO.setLastUpdatedTime(
    			new LocalDate(LocalDate.NO_STATION , Location.NONE , rs.getTimestamp("LSTUPDTIM")));
    	
    	log.log(Log.INFO, "!!!!!!!uldAgreementVO", uldAgreementVO);
		return uldAgreementVO;
    }

}
