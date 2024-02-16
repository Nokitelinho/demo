package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mail.vo.CarditVO;

/** 
 * @author A-1739
 */
public class CarditDetailsMapper implements Mapper<CarditVO> {
	/** 
	* maps the resultset row to a VO Sep 11, 2006, a-1739
	* @param rs
	* @return carditVO
	* @throws SQLException
	* @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	*/
	public CarditVO map(ResultSet rs) throws SQLException {
		CarditVO carditVO = new CarditVO();
		carditVO.setSenderId(rs.getString("SDRIDR"));
		carditVO.setRecipientId(rs.getString("RCTIDR"));
		carditVO.setTstIndicator(rs.getInt("TSTIND"));
		carditVO.setCarditKey(rs.getString("CDTKEY"));
		return carditVO;
	}
}
