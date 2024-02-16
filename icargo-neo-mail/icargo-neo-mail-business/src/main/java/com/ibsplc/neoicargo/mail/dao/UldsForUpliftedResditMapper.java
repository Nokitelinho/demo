package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

/** 
 * @author A-1876
 */
public class UldsForUpliftedResditMapper implements Mapper<ContainerDetailsVO> {
	/** 
	* This class is used to map the ResultSet into the MailBagVo
	* @param rs
	* @return 
	* @throws SQLException
	*/
	public ContainerDetailsVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		String airport = rs.getString("ASGPRT");
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		containerDetailsVO.setPol(rs.getString("ASGPRT"));
		containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		containerDetailsVO.setContainerNumber(rs.getString("CONNUM"));
		containerDetailsVO.setContainerJnyId(rs.getString("CONJRNIDR"));
		containerDetailsVO.setPaCode(rs.getString("SBCODE"));
		containerDetailsVO.setAssignmentDate(localDateUtil.getLocalDate(airport, rs.getTimestamp("ASGDAT")));
		return containerDetailsVO;
	}
}
