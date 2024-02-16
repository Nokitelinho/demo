/*
 * GPAContractMapper.java Created on Jul 23, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.operations.vo.GPAContractVO;

/**
 * @author A-6986
 *
 */
public class GPAContractMapper implements Mapper<GPAContractVO>{

	private Log log = LogFactory.getLogger("MAIL");


	private static final String CLASS_NAME = "MailHandoverMapper";

	public GPAContractVO map(ResultSet rs) throws SQLException {

		GPAContractVO gpaContractVO = new GPAContractVO();

		gpaContractVO.setCompanyCode(rs.getString("CMPCOD"));
		gpaContractVO.setPaCode(rs.getString("GPACOD"));
		//Added by A-8527 for BUG ICRD-308683
		gpaContractVO.setSernum(rs.getInt("SERNUM"));
		gpaContractVO.setContractIDs(rs.getString("CTRIDR"));
		gpaContractVO.setRegions(rs.getString("REGCOD"));
		gpaContractVO.setOriginAirports(rs.getString("ORGARP"));
		gpaContractVO.setDestinationAirports(rs.getString("DSTARP"));
		gpaContractVO.setAmot(rs.getString("AMOTFLG"));
		Date fromDate = rs.getDate("VLDFRM");
		if(fromDate != null) {
			gpaContractVO.setCidFromDates((new LocalDate(LocalDate.NO_STATION, Location.NONE,
					fromDate)).toDisplayDateOnlyFormat());

		}

		Date toDate = rs.getDate("VDLTOO");
		if(toDate != null) {
			gpaContractVO.setCidToDates((new LocalDate(LocalDate.NO_STATION, Location.NONE,
					toDate)).toDisplayDateOnlyFormat());
		}


		return gpaContractVO;
	}
}
