package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentRoutingVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

public class ConsignmentRoutingForMailBagScreeningMapper implements MultiMapper<ConsignmentRoutingVO> {

	public List<ConsignmentRoutingVO> map(ResultSet rs) throws SQLException {
		
		ArrayList<ConsignmentRoutingVO> consignmentRoutingVos = new ArrayList<>();
	
		while (rs.next()) {
			ConsignmentRoutingVO consignmentRoutingVO = new ConsignmentRoutingVO();
			consignmentRoutingVO.setCompanyCode(rs.getString("CMPCOD"));
			consignmentRoutingVO.setPol(rs.getString("POL"));
			consignmentRoutingVO.setPou(rs.getString("POU"));
			consignmentRoutingVO.setFlightNumber(rs.getString("FLTNUM"));
			consignmentRoutingVO.setFlightCarrierId(rs.getInt("FLTCARIDR"));
			consignmentRoutingVO.setConsignmentDocNumber(rs.getString("CSGDOCNUM"));
			consignmentRoutingVO.setConsignmentSeqNumber(rs.getInt("CSGSEQNUM"));
			consignmentRoutingVO.setPoaCode(rs.getString("POACOD"));
			if(rs.getDate("FLTDAT") != null) {
				consignmentRoutingVO.setFlightDate(new LocalDate(
	                    LocalDate.NO_STATION, Location.NONE,
	                    rs.getDate("FLTDAT")));
	        }
			consignmentRoutingVos.add(consignmentRoutingVO);
	}
		return consignmentRoutingVos;

}
}
