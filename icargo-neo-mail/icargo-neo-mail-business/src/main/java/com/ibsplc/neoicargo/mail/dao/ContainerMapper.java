package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.ContainerVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.util.currency.Money;

/** 
 * @author a-1303This class is used to map the Resultset in to the ContainerVOs.
 */
@Slf4j
public class ContainerMapper implements Mapper<ContainerVO> {
	/** 
	* This method is used to map all the  records from the resultset in to the ContainerVO
	* @param rs
	* @return ContainerVO
	* @throws SQLException
	*/
	public ContainerVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("Container Mapper" + " : " + "map(ResultSet rs)" + " Entering");
		String assignedPort = rs.getString("ASGPRT");
		ContainerVO containerVo = new ContainerVO();
		containerVo.setCompanyCode(rs.getString("CMPCOD"));
		containerVo.setContainerNumber(rs.getString("CONNUM"));
		containerVo.setAssignedPort(assignedPort);
		containerVo.setCarrierId(rs.getInt("FLTCARIDR"));
		containerVo.setFlightNumber(rs.getString("FLTNUM"));
		containerVo.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
		containerVo.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		if (rs.getTimestamp("ASGDAT") != null) {
			containerVo.setAssignedDate(localDateUtil.getLocalDate(assignedPort, rs.getTimestamp("ASGDAT")));
		}
		containerVo.setAssignedUser(rs.getString("USRCOD"));
		containerVo.setCarrierCode(rs.getString("FLTCARCOD"));
		containerVo.setPou(rs.getString("POU"));
		containerVo.setRemarks(rs.getString("RMK"));
		containerVo.setType(rs.getString("CONTYP"));
		containerVo.setFinalDestination(rs.getString("DSTCOD"));
		containerVo.setPaBuiltFlag(rs.getString("POAFLG"));
		containerVo.setBags(rs.getInt("ACPBAG"));
		containerVo.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ACPWGT"))));
		containerVo.setAcceptanceFlag(rs.getString("ACPFLG"));
		containerVo.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		containerVo.setOffloadFlag(rs.getString("OFLFLG"));
		containerVo.setArrivedStatus(rs.getString("ARRSTA"));
		containerVo.setContainerJnyID(rs.getString("CONJRNIDR"));
		containerVo.setShipperBuiltCode(rs.getString("SBCODE"));
		containerVo.setWarehouseCode(rs.getString("WHSCOD"));
		containerVo.setIntact(rs.getString("INTFLG"));
		containerVo.setTransitFlag(rs.getString("TRNFLG"));
		Timestamp lstUpdTime = rs.getTimestamp("LSTUPDTIM");
		if (lstUpdTime != null) {
			containerVo.setLastUpdateTime(localDateUtil.getLocalDate(null, lstUpdTime));
		}
		Timestamp uldLstUpdTime = rs.getTimestamp("ULDLSTUPDTIM");
		if (uldLstUpdTime != null) {
			containerVo.setULDLastUpdateTime(localDateUtil.getLocalDate(null, uldLstUpdTime));
		}
		containerVo.setLastUpdateUser(rs.getString("LSTUPDUSR"));
		containerVo.setULDLastUpdateUser(rs.getString("ULDLSTUPDUSR"));
		log.debug("" + "The ContainerVO in ContainerMapper" + " " + containerVo);
		log.debug("Container Mapper" + " : " + "map(ResultSet rs)" + " Exiting");
		return containerVo;
	}
}
