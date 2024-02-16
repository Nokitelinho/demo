/*
 * ConsignmentRoutingMapper.java Created on FEB 25, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentRoutingVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-3227 RENO K ABRAHAM
 *
 */
public class ConsignmentRoutingMapper implements Mapper<ConsignmentRoutingVO>{

	public ConsignmentRoutingVO map(ResultSet rs) throws SQLException {
		ConsignmentRoutingVO consignmentRoutingVO = new ConsignmentRoutingVO();

		consignmentRoutingVO.setCompanyCode(rs.getString("CMPCOD"));
		consignmentRoutingVO.setConsignmentDocNumber(rs.getString("CSGDOCNUM"));
		consignmentRoutingVO.setConsignmentSeqNumber(rs.getInt("CSGSEQNUM"));
		consignmentRoutingVO.setPoaCode(rs.getString("POACOD"));
		consignmentRoutingVO.setRoutingSerialNumber(rs.getInt("RTGSERNUM"));
		consignmentRoutingVO.setFlightCarrierCode(rs.getString("FLTCARCOD"));
		consignmentRoutingVO.setFlightCarrierId(rs.getInt("ARLIDR"));
	    if(rs.getDate("FLTDAT") != null) {
			consignmentRoutingVO.setFlightDate(new LocalDate(
                    LocalDate.NO_STATION, Location.NONE,
                    rs.getDate("FLTDAT")));
        }
		consignmentRoutingVO.setFlightNumber(rs.getString("FLTNUM"));
		consignmentRoutingVO.setPol(rs.getString("POL"));
		consignmentRoutingVO.setPou(rs.getString("POU"));
		
		return consignmentRoutingVO;
	}

}
