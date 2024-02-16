/*
 * ResditConfigurationMapper.java Created on Feb 1, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.ibsplc.icargo.business.mail.operations.vo.ResditTransactionDetailVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 *
 * @author A-1739
 *
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * -------------------------------------------------------------------------
 * 0.1     		  Feb 1, 2007			A-1739		Created
 */
public class ResditConfigurationMapper implements
		Mapper<ResditTransactionDetailVO> {

	/**
	 * TODO Purpose
	 * Feb 1, 2007, A-1739
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 */
	public ResditTransactionDetailVO map(ResultSet rs) throws SQLException {
		ResditTransactionDetailVO resditTransactionDetailVO =
			new ResditTransactionDetailVO();
		resditTransactionDetailVO.setTransaction(rs.getString("TXNCOD"));
		resditTransactionDetailVO.setReceivedResditFlag(rs.getString("RCVRDTFLG"));
		resditTransactionDetailVO.setAssignedResditFlag(rs.getString("ASGRDTFLG"));
		resditTransactionDetailVO.setUpliftedResditFlag(rs.getString("UPLRDTFLG"));
		resditTransactionDetailVO.setLoadedResditFlag(rs.getString("DEPRDTFLG"));
		resditTransactionDetailVO.setHandedOverResditFlag(rs.getString("HNDOVRFLG"));
		resditTransactionDetailVO.setHandedOverReceivedResditFlag(rs.getString("HNDOVRRCVFLG"));
		resditTransactionDetailVO.setDeliveredResditFlag(rs.getString("DLVRDTFLG"));
		resditTransactionDetailVO.setReturnedResditFlag(rs.getString("RETRDTFLG"));
		resditTransactionDetailVO.setReadyForDeliveryFlag(rs.getString("RDYDLVRDTFLG"));
		resditTransactionDetailVO.setTransportationCompletedResditFlag(rs.getString("TRTCPLRDTFLG"));
		resditTransactionDetailVO.setArrivedResditFlag(rs.getString("ARRRDTFLG"));
		Timestamp lstUpdTime = rs.getTimestamp("LSTUPDTIM");
	     if(lstUpdTime != null) {
	    	 resditTransactionDetailVO.setLastUpdateTime(
	    		 new LocalDate(LocalDate.NO_STATION, Location.NONE,lstUpdTime));
	     }
	    resditTransactionDetailVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
		return resditTransactionDetailVO;
	}

}
