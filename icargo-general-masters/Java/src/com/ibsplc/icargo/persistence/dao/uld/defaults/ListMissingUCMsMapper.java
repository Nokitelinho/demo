/*
 * ListMissingUCMsMapper.java Created on Jul 31, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This class implements a Mapper<<ULDFlightMessageReconcileVO>
 * @author A-3459
 *
 */
public class ListMissingUCMsMapper implements Mapper<ULDFlightMessageReconcileVO> {
		private Log log = LogFactory.getLogger("ULD");
	/**
	 * @param resultSet
	 * @return ULDFlightMessageReconcileDetailsVO
	 * @throws SQLException
	 */
	public ULDFlightMessageReconcileVO map(ResultSet resultSet) throws SQLException {	
		log.entering("ListMissingUCMsMapper","map");
		ULDFlightMessageReconcileVO uldFlightMessageReconcileVO = new ULDFlightMessageReconcileVO();
		uldFlightMessageReconcileVO.setCompanyCode(resultSet.getString("CMPCOD"));
		uldFlightMessageReconcileVO.setFlightNumber(resultSet.getString("FLTNUM"));
		uldFlightMessageReconcileVO.setDestination(resultSet.getString("DST"));
		if(resultSet.getDate("FLTDAT") != null) {
			uldFlightMessageReconcileVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultSet.getDate("FLTDAT")));
		}
		uldFlightMessageReconcileVO.setOrigin(resultSet.getString("ORG"));
		uldFlightMessageReconcileVO.setUcmInMissed(resultSet.getString("UCMIN"));
		uldFlightMessageReconcileVO.setUcmOutMissed(resultSet.getString("UCMOUT"));
		log.exiting("ListMissingUCMsMapper","map");
		return uldFlightMessageReconcileVO;
	}
}
