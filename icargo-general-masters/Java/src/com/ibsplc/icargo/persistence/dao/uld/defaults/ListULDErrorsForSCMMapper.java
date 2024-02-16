/*
 * ListULDErrorsForSCMMapper.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;


import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;



import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 * This class implements a Mapper<<ULDSCMReconcileDetailsVO>
 * @author A-2048
 *
 */
public class ListULDErrorsForSCMMapper implements Mapper<ULDSCMReconcileDetailsVO> {
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
    /**
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
	public ULDSCMReconcileDetailsVO map(ResultSet resultSet) throws SQLException {
		log.entering("ListULDErrorsForSCMMapper","map");
		ULDSCMReconcileDetailsVO detailsVO = new ULDSCMReconcileDetailsVO();
		detailsVO.setCompanyCode(resultSet.getString("CMPCOD"));
		detailsVO.setAirportCode(resultSet.getString("ARPCOD"));
		detailsVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
		detailsVO.setSequenceNumber(resultSet.getString("SEQNUM"));
		detailsVO.setErrorCode(resultSet.getString("ERRCOD"));
		detailsVO.setUldNumber(resultSet.getString("ULDNUM"));
		detailsVO.setFlightCarrierCode(resultSet.getString("ARLCOD"));
		//added by A-6344 for ICRD-114404 starts
		detailsVO.setUldStatus(resultSet.getString("ULDSTKSTA"));
		//added by A-6344 for ICRD-114404 ends
		log.exiting("ListULDErrorsForSCMMapper","map");
		return detailsVO;

	}

}
