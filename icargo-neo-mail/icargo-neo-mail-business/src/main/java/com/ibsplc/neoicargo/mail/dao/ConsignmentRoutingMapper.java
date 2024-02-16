package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.ConsignmentRoutingVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

/** 
 * @author A-3227 RENO K ABRAHAM
 */
public class ConsignmentRoutingMapper implements Mapper<ConsignmentRoutingVO> {
	public ConsignmentRoutingVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ConsignmentRoutingVO consignmentRoutingVO = new ConsignmentRoutingVO();
		consignmentRoutingVO.setCompanyCode(rs.getString("CMPCOD"));
		consignmentRoutingVO.setConsignmentDocNumber(rs.getString("CSGDOCNUM"));
		consignmentRoutingVO.setConsignmentSeqNumber(rs.getInt("CSGSEQNUM"));
		consignmentRoutingVO.setPoaCode(rs.getString("POACOD"));
		consignmentRoutingVO.setRoutingSerialNumber(rs.getInt("RTGSERNUM"));
		consignmentRoutingVO.setFlightCarrierCode(rs.getString("FLTCARCOD"));
		consignmentRoutingVO.setFlightCarrierId(rs.getInt("ARLIDR"));

		if (rs.getDate("FLTDAT") != null) {
			consignmentRoutingVO.setFlightDate(localDateUtil.getLocalDate(null, rs.getDate("FLTDAT")));
		}
		consignmentRoutingVO.setFlightNumber(rs.getString("FLTNUM"));
		consignmentRoutingVO.setPol(rs.getString("POL"));
		consignmentRoutingVO.setPou(rs.getString("POU"));
		return consignmentRoutingVO;
	}
}
