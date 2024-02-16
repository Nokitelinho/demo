package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

/**
 * RoutingCarrierDetailsMapper
 * 
 * @author A-4452
 * 
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RoutingCarrierVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


public class RoutingCarrierDetailsMapper implements Mapper<RoutingCarrierVO> {
	  private Log log = LogFactory.getLogger("MAILTRACKING MRA ");
	
	public RoutingCarrierVO map(ResultSet rs) throws SQLException {
		
		log.entering("RoutingCarrierDetailsMapper", "map");
		 // //Modified by A-8236 as part of ICRD-252154 
		RoutingCarrierVO routingCarrierVO = new RoutingCarrierVO();
		routingCarrierVO.setCompanyCode(rs.getString("CMPCOD"));
		routingCarrierVO.setOriginCity(rs.getString("MALORGVAL"));
		routingCarrierVO.setDestCity(rs.getString("MALDSTVAL"));
		routingCarrierVO.setOwnSectorFrm(rs.getString("SECONEORGVAL"));
		routingCarrierVO.setOwnSectorTo(rs.getString("SECONEDSTVAL"));
		routingCarrierVO.setOalSectorFrm(rs.getString("SECTWOORGVAL"));
		routingCarrierVO.setOalSectorTo(rs.getString("SECTWODSTVAL"));
		routingCarrierVO.setCarrier(rs.getString("SECTWOCARCOD"));
		if (rs.getDate("VALFRM") != null) {
			routingCarrierVO.setValidFrom(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getDate("VALFRM")));
		}
		if (rs.getDate("VALTOO") != null) {
			routingCarrierVO.setValidTo(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getDate("VALTOO")));
		}
		routingCarrierVO.setSequenceNumber(rs.getInt("SEQNUM"));		  			
		routingCarrierVO.setOperationFlag(RoutingCarrierVO.OPERATION_FLAG_UPDATE);
		return routingCarrierVO; 
	}

}  
