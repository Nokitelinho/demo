package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.ContainerAssignmentVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.util.currency.Money;

/** 
 * @author a-1303This class is used to map the Resultset into the Vos
 */
public class ContainerAssignmentMapper implements Mapper<ContainerAssignmentVO> {
	/** 
	* @param rs
	* @return
	* @throws SQLException
	*/
	public ContainerAssignmentVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		ContainerAssignmentVO containerAssignmentVO = new ContainerAssignmentVO();
		String assignedPort = rs.getString("ASGPRT");
		containerAssignmentVO.setCompanyCode(rs.getString("CMPCOD"));
		containerAssignmentVO.setAirportCode(assignedPort);
		containerAssignmentVO.setContainerNumber(rs.getString("CONNUM"));
		containerAssignmentVO.setFlightNumber(rs.getString("FLTNUM"));
		containerAssignmentVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerAssignmentVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		containerAssignmentVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		containerAssignmentVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		containerAssignmentVO.setFlightStatus(rs.getString("CLSFLG"));
		if (rs.getDate("FLTDAT") != null) {
			containerAssignmentVO.setFlightDate(localDateUtil.getLocalDate(assignedPort, rs.getDate("FLTDAT")));
		}
		containerAssignmentVO.setCarrierCode(rs.getString("FLTCARCOD"));
		containerAssignmentVO.setDestination(rs.getString("DSTCOD"));
		containerAssignmentVO.setAcceptanceFlag(rs.getString("ACPFLG"));
		containerAssignmentVO.setPou(rs.getString("POU"));
		containerAssignmentVO.setRemark(rs.getString("RMK"));
		containerAssignmentVO.setContainerType(rs.getString("CONTYP"));
		containerAssignmentVO.setArrivalFlag(rs.getString("ARRSTA"));
		containerAssignmentVO.setTransferFlag(rs.getString("TRAFLG"));
		containerAssignmentVO.setJourneyID(rs.getString("CONJRNIDR"));
		containerAssignmentVO.setShipperBuiltCode(rs.getString("SBCODE"));
		containerAssignmentVO.setTransitFlag(rs.getString("TRNFLG"));
		containerAssignmentVO.setTransactionCode(rs.getString("TXNCOD"));
		containerAssignmentVO.setReleasedFlag(rs.getString("RELFLG"));
		containerAssignmentVO.setOffloadStatus(rs.getString("OFLFLG"));
		containerAssignmentVO.setPoaFlag(rs.getString("POAFLG"));
		if (rs.getDouble("ACTULDWGT") > 0)
			containerAssignmentVO.setActualWeight(
					quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ACTULDWGT"))));
		if (rs.getDate("ASGDAT") != null) {
			containerAssignmentVO.setAssignedDate(localDateUtil.getLocalDate(assignedPort, rs.getTimestamp("ASGDAT")));
		}
		containerAssignmentVO.setUldFulIndFlag(rs.getString("ULDFULIND"));
		containerAssignmentVO.setUldReferenceNo(rs.getLong("ULDREFNUM"));
		return containerAssignmentVO;
	}
}
