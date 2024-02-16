package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.ResditTransactionDetailVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

/** 
 * @author A-1739
 */
public class ResditConfigurationMapper implements Mapper<ResditTransactionDetailVO> {
	/** 
	* TODO Purpose Feb 1, 2007, A-1739
	* @param rs
	* @return
	* @throws SQLException
	* @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	*/
	public ResditTransactionDetailVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ResditTransactionDetailVO resditTransactionDetailVO = new ResditTransactionDetailVO();
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
		if (lstUpdTime != null) {
			resditTransactionDetailVO.setLastUpdateTime(localDateUtil.getLocalDate(null, lstUpdTime));
		}
		resditTransactionDetailVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
		return resditTransactionDetailVO;
	}
}
