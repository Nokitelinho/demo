package com.ibsplc.neoicargo.mailmasters.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mailmasters.vo.GPAContractVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import java.time.format.DateTimeFormatter;

/** 
 * @author A-6986
 */
public class GPAContractMapper implements Mapper<GPAContractVO> {
	private static final String CLASS_NAME = "MailHandoverMapper";

	public GPAContractVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		GPAContractVO gpaContractVO = new GPAContractVO();
		gpaContractVO.setCompanyCode(rs.getString("CMPCOD"));
		gpaContractVO.setPaCode(rs.getString("GPACOD"));
		gpaContractVO.setSernum(rs.getInt("SERNUM"));
		gpaContractVO.setContractIDs(rs.getString("CTRIDR"));
		gpaContractVO.setRegions(rs.getString("REGCOD"));
		gpaContractVO.setOriginAirports(rs.getString("ORGARP"));
		gpaContractVO.setDestinationAirports(rs.getString("DSTARP"));
		gpaContractVO.setAmot(rs.getString("AMOTFLG"));
		Date fromDate = rs.getDate("VLDFRM");
		if (fromDate != null) {
			gpaContractVO.setCidFromDates((localDateUtil.getLocalDate(null, fromDate))
					.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		}
		Date toDate = rs.getDate("VDLTOO");
		if (toDate != null) {
			gpaContractVO.setCidToDates((localDateUtil.getLocalDate(null, toDate))
					.format(DateTimeFormatter.ofPattern(LocalDate.DATE_FORMAT)));
		}
		return gpaContractVO;
	}
}
