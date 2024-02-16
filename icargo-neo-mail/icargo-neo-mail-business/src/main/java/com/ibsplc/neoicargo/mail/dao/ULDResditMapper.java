package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mail.vo.UldResditVO;

/** 
 * @author This class is used to map the ResultSet in to the UldResditVO
 */
public class ULDResditMapper implements Mapper<UldResditVO> {
	/** 
	* @author A-1936
	* @param rs
	* @return
	* @throws SQLException
	*/
	public UldResditVO map(ResultSet rs) throws SQLException {
		UldResditVO uldResditVO = new UldResditVO();
		uldResditVO.setResditSentFlag(rs.getString("RDTSND"));
		uldResditVO.setResditSequenceNum(rs.getLong("SEQNUM"));
		uldResditVO.setProcessedStatus(rs.getString("PROSTA"));
		return uldResditVO;
	}
}
