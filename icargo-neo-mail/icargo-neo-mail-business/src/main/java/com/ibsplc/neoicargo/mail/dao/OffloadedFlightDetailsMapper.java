package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mail.vo.ContainerVO;

/** 
 * This class is used to find the offloded Info for a container
 */
public class OffloadedFlightDetailsMapper implements Mapper<ContainerVO> {
	/** 
	* @author a-3429
	* @param rs
	* @return
	* @throws SQLException
	* @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	*/
	public ContainerVO map(ResultSet rs) throws SQLException {
		ContainerVO containerVO = new ContainerVO();
		containerVO.setOffloadedDescription(rs.getString("OFFLODDTL"));
		containerVO.setOffloadCount(rs.getInt("OFFLODCNT"));
		return containerVO;
	}
}
