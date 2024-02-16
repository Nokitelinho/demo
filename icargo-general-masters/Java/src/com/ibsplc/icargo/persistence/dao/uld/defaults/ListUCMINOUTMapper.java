/*
 * ListUCMINOUTMapper.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;


import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 * This class implements a Mapper<<ULDFlightMessageReconcileDetailsVO>
 * @author A-2048
 *
 */
public class ListUCMINOUTMapper implements Mapper<ULDFlightMessageReconcileDetailsVO> {
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
 /**
  * @param resultSet
  * @return ULDFlightMessageReconcileDetailsVO
  * @throws SQLException
  */
	public ULDFlightMessageReconcileDetailsVO map(ResultSet resultSet) throws SQLException {
		log.entering("ListULDErrorsMapper","map");
		ULDFlightMessageReconcileDetailsVO detailsVO = new ULDFlightMessageReconcileDetailsVO();
		detailsVO.setCompanyCode(resultSet.getString("CMPCOD"));
		detailsVO.setFlightCarrierIdentifier(resultSet.getInt("FLTCARIDR"));
		detailsVO.setFlightNumber(resultSet.getString("FLTNUM"));
		detailsVO.setFlightSequenceNumber(resultSet.getInt("FLTSEQNUM"));
		if(resultSet.getDate("FLTDAT") != null) {
			detailsVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultSet.getDate("FLTDAT")));
		}
		
	//	detailsVO.setLegSerialNumber(resultSet.getInt("LEGSERNUM"));
		detailsVO.setAirportCode(resultSet.getString("ARPCOD"));
	//	detailsVO.setMessageType(resultSet.getString("MSGTYP"));
		detailsVO.setSequenceNumber(resultSet.getString("SEQNUM"));
		detailsVO.setErrorCode(resultSet.getString("ERRCOD"));
		detailsVO.setDamageStatus(resultSet.getString("DMGSTA"));
		detailsVO.setUldNumber(resultSet.getString("ULDNUM"));
		detailsVO.setPou(resultSet.getString("POU"));
		detailsVO.setContent(resultSet.getString("CNT"));
		detailsVO.setMessageType(resultSet.getString("MSGTYP"));
		detailsVO.setUcmErrorCode(resultSet.getString("UCMERRCOD"));
		
	//	detailsVO.setCarrierCode(resultSet.getString("ARLCOD"));
		
		
		
		log.exiting("ListULDErrorsMapper","map"+detailsVO);
		return detailsVO;

	}

}
