/*
 * DocumentStatisticsReportMapper.java Created on Sep 02, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentStatisticsDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-3429
 * 
 */
public class DocumentStatisticsReportMapper implements
		Mapper<DocumentStatisticsDetailsVO> {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "DocumentStatisticsReportMapper";

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public DocumentStatisticsDetailsVO map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		DocumentStatisticsDetailsVO detailsVO = new DocumentStatisticsDetailsVO();
		if (rs.getString("FLTCARCOD") != null) {
			detailsVO.setCarrierCode(rs.getString("FLTCARCOD"));
		}
		if (rs.getString("FLTNUM") != null) {
			detailsVO.setFlightNo(rs.getString("FLTNUM"));
		}
		Date date = rs.getDate("FLTDAT");
		if (date != null) {
			detailsVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, date));
		}
		if (rs.getString("NOOFDOCUMENTS") != null) {
			detailsVO.setNoOfDocuments(rs.getString("NOOFDOCUMENTS"));
		}
		if (rs.getString("RATAUD") != null) {
			detailsVO.setRateAudited(rs.getString("RATAUD"));
		}
		if (rs.getString("TOORATAUD") != null) {
			detailsVO.setToBeRateAudited(rs.getString("TOORATAUD"));
		}
		if (rs.getString("ACCNTD") != null) {
			detailsVO.setAccounted(rs.getString("ACCNTD"));
		}
		if (rs.getString("CLSFLG") != null) {
			detailsVO.setFlightStatus(rs.getString("CLSFLG"));
		}

		return detailsVO;
	}

}
