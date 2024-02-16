package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.neoicargo.mail.vo.FlightLoadPlanContainerVO;
import com.ibsplc.neoicargo.framework.util.currency.Money;

public class LoadPlanDetailsForContainerMapper implements MultiMapper<FlightLoadPlanContainerVO> {
	public List<FlightLoadPlanContainerVO> map(ResultSet rs) throws SQLException {
		List<FlightLoadPlanContainerVO> flightLoadPlanContainerVos = new ArrayList<>();
		while (rs.next()) {
			FlightLoadPlanContainerVO flightLoadPlanContainerVO = new FlightLoadPlanContainerVO();
			flightLoadPlanContainerVO.setCompanyCode(rs.getString("CMPCOD"));
			flightLoadPlanContainerVO.setCarrierId(rs.getInt("FLTCARIDR"));
			flightLoadPlanContainerVO.setFlightNumber(rs.getString("FLTNUM"));
			flightLoadPlanContainerVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
			flightLoadPlanContainerVO.setLoadPlanVersion(rs.getInt("LODPLNVER"));
			flightLoadPlanContainerVO.setSegOrigin(rs.getString("SEGORG"));
			flightLoadPlanContainerVO.setSegDestination(rs.getString("SEGDST"));
			flightLoadPlanContainerVO.setContainerNumber(rs.getString("CONNUM"));
			flightLoadPlanContainerVO.setPlannedPieces(rs.getInt("PLNPCS"));
			flightLoadPlanContainerVO.setPlannedWeight(rs.getDouble("PLNWGT"));
			flightLoadPlanContainerVO.setUldNumber(rs.getString("ULDNUM"));
			flightLoadPlanContainerVO.setContainerType(rs.getString("CONTYP"));
			flightLoadPlanContainerVO.setSegSerialNumber(rs.getInt("SEGSERNUM"));
			flightLoadPlanContainerVO.setUldReferenceNo(rs.getLong("ULDREFNUM"));
			flightLoadPlanContainerVO.setPlanStatus(rs.getString("LODPLNSTA"));
			flightLoadPlanContainerVO.setPlannedPosition(rs.getString("PLNPOS"));
			flightLoadPlanContainerVO.setPlannedVolume(rs.getDouble("PLNVOL"));
			flightLoadPlanContainerVO.setUldFullIndicator(rs.getString("ULDFULIND"));
			flightLoadPlanContainerVO.setSubClassGroup(rs.getString("SUBCLSGRP"));
			flightLoadPlanContainerVos.add(flightLoadPlanContainerVO);
		}
		return flightLoadPlanContainerVos;
	}
}
