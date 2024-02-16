package com.ibsplc.neoicargo.mailmasters.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mailmasters.vo.RoutingIndexLegVO;
import com.ibsplc.neoicargo.mailmasters.vo.RoutingIndexVO;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.RoutingIndexDetailsMapper.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-7531	:	30-Oct-2018	:	Draft
 */
public class RoutingIndexDetailsMapper implements Mapper<RoutingIndexVO> {
	private static final String CLASS_NAME = "RoutingIndexDetailsMapper";

	/** 
	* Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet) Added by 			: A-7531 on 30-Oct-2018 Used for 	: Parameters	:	@param arg0 Parameters	:	@return Parameters	:	@throws SQLException 
	*/
	@Override
	public RoutingIndexVO map(ResultSet rs) throws SQLException {
		RoutingIndexVO routingIndexVO = new RoutingIndexVO();
		RoutingIndexLegVO routingIndexLegVO = new RoutingIndexLegVO();
		routingIndexVO.setRoutingIndex(rs.getString("RTGIDX"));
		routingIndexVO.setOrigin(rs.getString("ORGCOD"));
		routingIndexVO.setDestination(rs.getString("DSTCOD"));
		routingIndexVO.setRoutingSeqNum(rs.getInt("RTGSEQNUM"));
		if (rs.getDate("PLDEFFDAT") != null) {
			routingIndexVO.setPlannedEffectiveDate(rs.getDate("PLDEFFDAT").toString());
		}
		if (rs.getDate("PLDDISDAT") != null) {
			routingIndexVO.setPlannedDisDate(rs.getDate("PLDDISDAT").toString());
		}
		Collection<RoutingIndexLegVO> routingIndexLegVOs = new ArrayList<>();
		routingIndexLegVO.setLegOrg(rs.getString("LEGORG"));
		routingIndexLegVO.setLegDstn(rs.getString("LEGDST"));
		routingIndexLegVO.setLegRoute(rs.getString("LEGRTG"));
		routingIndexLegVO.setLegFlight(rs.getString("LEGFLT"));
		routingIndexLegVO.setLegDepTime(rs.getString("LEGDEPTIM"));
		routingIndexLegVO.setLegArvTime(rs.getString("LEGARVTIM"));
		routingIndexLegVOs.add(routingIndexLegVO);
		routingIndexVO.setRoutingIndexLegVO(routingIndexLegVOs);
		return routingIndexVO;
	}
}
