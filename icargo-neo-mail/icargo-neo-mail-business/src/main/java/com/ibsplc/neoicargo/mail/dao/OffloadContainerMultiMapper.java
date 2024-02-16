package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.neoicargo.mail.vo.ContainerVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;


/**
 * @author A-1936
 */
@Slf4j
public class OffloadContainerMultiMapper implements MultiMapper<ContainerVO> {
	/**
	 * This method is used to map the result set in to the Vo
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<ContainerVO> map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("inside the mapper" + " : " + "map(ResultSet rs )" + " Entering");
		List<ContainerVO> containersVos = null;
		ContainerVO containerVo = null;
		while (rs.next()) {
			if (containersVos == null) {
				containersVos = new ArrayList<ContainerVO>();
			}
			if (rs.getString("CONNUM").equals(rs.getString("ULDNUM"))) {

				containerVo = new ContainerVO();
				containerVo.setCompanyCode(rs.getString("CMPCOD"));
				containerVo.setContainerNumber(rs.getString("CONNUM"));
				containerVo.setFlightNumber(rs.getString("FLTNUM"));
				containerVo.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
				containerVo.setAssignedPort(rs.getString("ASGPRT"));
				containerVo.setCarrierId(rs.getInt("FLTCARIDR"));
				containerVo.setLegSerialNumber(rs.getInt("LEGSERNUM"));
				containerVo.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
				containerVo.setPou(rs.getString("POU"));
				containerVo.setFinalDestination(rs.getString("DSTCOD"));
				containerVo.setType(rs.getString("CONTYP"));
				containerVo.setAcceptanceFlag(rs.getString("ACPFLG"));
				containerVo.setPaBuiltFlag(rs.getString("POAFLG"));
				containerVo.setBags(rs.getInt("BAGS"));
				containerVo.setWeight( quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT"))));
				containerVo.setCarrierCode(rs.getString("FLTCARCOD"));
				containerVo.setPaBuiltFlag(rs.getString("POAFLG"));
				containerVo.setContainerJnyID(rs.getString("CONJRNIDR"));
				containerVo.setShipperBuiltCode(rs.getString("SBCODE"));
				containerVo.setUldFulIndFlag(rs.getString("ULDFULIND"));
				containerVo.setUldReferenceNo(rs.getLong("ULDREFNUM"));
				containerVo.setTransactionCode(rs.getString("TXNCOD"));
				String airport = rs.getString("ASGPRT");
				if (rs.getDate("FLTDAT") != null) {
					containerVo.setFlightDate(localDateUtil.getLocalDate(airport, rs.getDate("FLTDAT")));
				}
				containersVos.add(containerVo);

			} else {
				containerVo = new ContainerVO();
				containerVo.setCompanyCode(rs.getString("CMPCOD"));
				containerVo.setContainerNumber(rs.getString("CONNUM"));
				containerVo.setFlightNumber(rs.getString("FLTNUM"));
				containerVo.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
				containerVo.setAssignedPort(rs.getString("ASGPRT"));
				containerVo.setCarrierId(rs.getInt("FLTCARIDR"));
				containerVo.setLegSerialNumber(rs.getInt("LEGSERNUM"));
				containerVo.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
				containerVo.setPou(rs.getString("POU"));
				containerVo.setFinalDestination(rs.getString("DSTCOD"));
				containerVo.setType(rs.getString("CONTYP"));
				containerVo.setAcceptanceFlag(rs.getString("ACPFLG"));
				containerVo.setCarrierCode(rs.getString("FLTCARCOD"));
				containerVo.setContainerJnyID(rs.getString("CONJRNIDR"));
				String airport = rs.getString("ASGPRT");
				if (rs.getDate("FLTDAT") != null) {
					containerVo.setFlightDate(localDateUtil.getLocalDate(airport, rs.getDate("FLTDAT")));
				}
				containerVo.setBags(rs.getInt("BAGS"));
				containerVo.setWeight( quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT"))));
				containersVos.add(containerVo);

			}
		}

		return containersVos;
	}
}
