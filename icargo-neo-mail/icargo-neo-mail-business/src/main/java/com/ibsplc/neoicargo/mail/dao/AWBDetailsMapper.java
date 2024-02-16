package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mail.vo.AWBDetailVO;

/** 
 * @author a-1883
 */
public class AWBDetailsMapper implements Mapper<AWBDetailVO> {
	/** 
	* @param rs
	* @return AWBDetailVO
	* @throws SQLException
	*/
	public AWBDetailVO map(ResultSet rs) throws SQLException {
		AWBDetailVO awbDetailVO = new AWBDetailVO();
		awbDetailVO.setCompanyCode(rs.getString("CMPCOD"));
		awbDetailVO.setOwnerId(rs.getInt("DOCOWRIDR"));
		awbDetailVO.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
		awbDetailVO.setDuplicateNumber(rs.getInt("DUPNUM"));
		awbDetailVO.setSequenceNumber(rs.getInt("SEQNUM"));
		awbDetailVO.setOrigin(rs.getString("ORG"));
		awbDetailVO.setDestination(rs.getString("DST"));
		awbDetailVO.setOwnerCode(rs.getString("DOCOWRCOD"));
		return awbDetailVO;
	}
}
