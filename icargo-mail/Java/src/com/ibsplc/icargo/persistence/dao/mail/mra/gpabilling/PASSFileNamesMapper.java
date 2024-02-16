package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.FileNameLovVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

public class PASSFileNamesMapper implements Mapper<FileNameLovVO> {

	@Override
	public FileNameLovVO map(ResultSet rs) throws SQLException {
		FileNameLovVO fileNameLovVO = new FileNameLovVO();
		fileNameLovVO.setCompanyCode(rs.getString("CMPCOD"));
		if (Objects.nonNull(rs.getDate("BLGPRDFRM"))) {
			fileNameLovVO.setFromDate(getDateString(rs, "BLGPRDFRM"));
		}
		if (Objects.nonNull(rs.getDate("BLGPRDTOO"))) {
			fileNameLovVO.setToDate(getDateString(rs, "BLGPRDTOO"));
		}
		fileNameLovVO.setPeriodNumber(rs.getString("PRDNUM"));
		fileNameLovVO.setFileName(rs.getString("INTFCDFILNAM"));
		return fileNameLovVO;
	}

	private String getDateString(ResultSet rs, String column) throws SQLException {
		return new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate(column)).toDisplayDateOnlyFormat();
	}

}
