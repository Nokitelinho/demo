/*
 * ULDConfigAuditMapper.java Created on Aug 07, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDConfigAuditVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3045
 *
 */
public class ULDConfigAuditMapper implements Mapper<ULDConfigAuditVO>{
	
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	 /**
    * Method for getting the map
    * @param rs
    * @return AuditDetailsVO
    * @throws SQLException
    */
	public ULDConfigAuditVO map(ResultSet rs) throws SQLException {
		log.entering("ULDConfigAuditMapper", "map");
		ULDConfigAuditVO uldConfigAuditVO = new ULDConfigAuditVO(
				ULDAgreementVO.MODULE, ULDAgreementVO.SUBMODULE,
				ULDAgreementVO.ENTITY);
		uldConfigAuditVO.setCompanyCode(rs.getString("CMPCOD"));
		uldConfigAuditVO.setActionCode(rs.getString("ACTCOD"));
		uldConfigAuditVO.setAdditionalInformation(rs.getString("ADLINF"));
		Timestamp txntime = rs.getTimestamp("UPDTXNTIM");
		if(txntime != null){
			uldConfigAuditVO.setTxnTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,txntime));
		}		
		log.exiting("ULDConfigAuditMapper", "map");
		return uldConfigAuditVO;
	}

}
