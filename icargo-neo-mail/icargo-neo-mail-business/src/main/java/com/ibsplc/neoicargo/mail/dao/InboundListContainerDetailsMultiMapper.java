package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.util.currency.Money;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.InboundListContainerDetailsMultiMapper.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-8164	:	28-Dec-2018		:	Draft
 */
@Slf4j
public class InboundListContainerDetailsMultiMapper implements MultiMapper<ContainerDetailsVO> {
	private static final String CLASS_NAME = "InboundListContainerDetailsMultiMapper";

	public List<ContainerDetailsVO> map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug(CLASS_NAME + " : " + "map" + " Entering");
		List<ContainerDetailsVO> containerDetailsVOsList = new ArrayList<ContainerDetailsVO>();
		while (rs.next()) {
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			if (null != rs.getString("CONNUM")) {
				String assignedPort = rs.getString("ASGPRT");
				containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
				containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
				containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
				containerDetailsVO.setContainerNumber(rs.getString("CONNUM"));
				containerDetailsVO.setContainerGroup(rs.getString("ULDNUM"));
				containerDetailsVO.setLegSerialNumber((rs.getInt("LEGSERNUM")));
				containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
				containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
				containerDetailsVO.setPou(rs.getString("POU"));
				containerDetailsVO.setPol(rs.getString("ASGPRT"));
				containerDetailsVO.setDestination(rs.getString("DSTCOD"));
				containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
				containerDetailsVO.setAcceptedFlag(rs.getString("ACPFLG"));
				containerDetailsVO.setOffloadFlag(rs.getString("OFLFLG"));
				containerDetailsVO.setContainerType(rs.getString("CONTYP"));
				containerDetailsVO.setArrivedStatus(rs.getString("ARRSTA"));
				containerDetailsVO.setTransitFlag(rs.getString("TRNFLG"));
				Timestamp lstUpdateTime = rs.getTimestamp("CONLSTUPDTIM");
				if (lstUpdateTime != null) {
					containerDetailsVO.setLastUpdateTime(localDateUtil.getLocalDate(null, lstUpdateTime));
				}
				Timestamp uldLastUpdateTime = rs.getTimestamp("ULDLSTUPDTIM");
				if (uldLastUpdateTime != null) {
					containerDetailsVO.setUldLastUpdateTime(localDateUtil.getLocalDate(null, uldLastUpdateTime));
				}
				containerDetailsVO.setContainerJnyId(rs.getString("CONJRNIDR"));
				containerDetailsVO.setPaCode(rs.getString("POACOD"));
				if (rs.getTimestamp("ASGDATUTC") != null) {
					containerDetailsVO
							.setAssignedDate(localDateUtil.getLocalDate(assignedPort, rs.getTimestamp("ASGDATUTC")));
				}
				containerDetailsVO.setLocation(rs.getString("LOCCOD"));
				containerDetailsVO.setRemarks(rs.getString("RMK"));
				containerDetailsVO.setAssignedUser(rs.getString("USRCOD"));
				containerDetailsVO.setTotalBags(rs.getInt("MALCNT"));
				containerDetailsVO.setReceivedBags(rs.getInt("RCVBAG"));
				containerDetailsVO.setMailbagcount(rs.getInt("ACPBAG"));
				containerDetailsVO.setIntact(rs.getString("INTFLG"));
				containerDetailsVO.setDeliveredStatus(rs.getString("DLVFLG"));
				if (0 != rs.getDouble("WGT"))
					containerDetailsVO.setTotalWeight(
							quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT"))));
				if (0 != rs.getDouble("RCVWGT"))
					containerDetailsVO.setReceivedWeight(
							quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("RCVWGT"))));
				if (0 != rs.getDouble("ACPWGT"))
					containerDetailsVO.setMailbagwt(
							quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ACPWGT"))));
				String onwardFlight = "";
				if (null != rs.getString("ONWFLTCARCOD"))
					onwardFlight = new StringBuffer().append(onwardFlight).append(rs.getString("ONWFLTCARCOD"))
							.toString();
				if (null != rs.getString("ONWFLTNUM"))
					onwardFlight = new StringBuffer().append(onwardFlight).append(rs.getString("ONWFLTNUM")).toString();
				if (null != rs.getString("ONWFLTDAT"))
					onwardFlight = new StringBuffer().append(onwardFlight).append(" ").append(rs.getString("ONWFLTDAT"))
							.toString();
				if (null != rs.getString("RTGPOU"))
					onwardFlight = new StringBuffer().append(onwardFlight).append(" to ").append(rs.getString("RTGPOU"))
							.toString();
				if (!"".equals(onwardFlight))
					containerDetailsVO.setOnwardFlights(onwardFlight);
				if (0 != rs.getDouble("ACTULDWGT"))
					containerDetailsVO.setActualWeight(
							quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ACTULDWGT"))));
				containerDetailsVO.setAssignedPort(rs.getString("ASGPRT"));
				containerDetailsVOsList.add(containerDetailsVO);
				int mailDiffDestCnt = rs.getInt("DESTCOUNT");
				if (mailDiffDestCnt == 1) {
					if (rs.getString("MALDST").equals(rs.getString("POU"))) {
						containerDetailsVO.setContainerPureTransfer("TERMINATING");
					} else {
						containerDetailsVO.setContainerPureTransfer("PURE TRANSFER");
					}
				} else {
					containerDetailsVO.setContainerPureTransfer("MIXED");
				}
				if (rs.getTimestamp("REQDLVTIM") != null) {
					containerDetailsVO
							.setMinReqDelveryTime(localDateUtil.getLocalDate(null, rs.getTimestamp("REQDLVTIM")));
				}
			}
		}
		return containerDetailsVOsList;
	}
}
