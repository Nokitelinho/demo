package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

public class ContainerJourneyIDMapper implements Mapper<ContainerDetailsVO> {

	@Override
	public ContainerDetailsVO map(ResultSet rs) throws SQLException {
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		containerDetailsVO.setContainerJnyId(rs.getString("MALJNRIDR"));
		containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerDetailsVO.setCarrierCode(rs.getString("FLTCARCOD"));
		containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		if (Objects.nonNull(rs.getDate("FLTDAT"))) {
			containerDetailsVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate("FLTDAT")));
		}
		return containerDetailsVO;
	}

}
