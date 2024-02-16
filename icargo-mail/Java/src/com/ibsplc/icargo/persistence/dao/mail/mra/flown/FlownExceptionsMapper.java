/*
 * FlownExceptionsMapper.java Created on Jan 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.flown;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailExceptionVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;


/**
 * This Class is for FlownDSNExceptionsMapper
 * @author A-2338
 *
 *A-2401
 *Added the body of the function FlownExceptionsMapper
 */
public class FlownExceptionsMapper implements Mapper<FlownMailExceptionVO> {

	 /**
     * @param resultSet
     * @return FlownMailExceptionVO
     * @throws SQLException
     */
    public FlownMailExceptionVO map(ResultSet resultSet) throws SQLException {
    	
    	FlownMailExceptionVO flownMailExceptionVO = new FlownMailExceptionVO();
    	flownMailExceptionVO.setCompanyCode(resultSet.getString("CMPCOD"));
    	flownMailExceptionVO.setBillingBasis(resultSet.getString("BLGBAS"));
    	flownMailExceptionVO.setFlightCarrierId(resultSet.getInt("FLTCARIDR"));
    	flownMailExceptionVO.setFlightSequenceNumber(resultSet.getInt("FLTSEQNUM"));
    	flownMailExceptionVO.setSegmentSerialNumber(resultSet.getInt("SEGSERNUM"));
    	flownMailExceptionVO.setSerialNumber(resultSet.getInt("SERNUM"));
    	flownMailExceptionVO.setAirlineCode(resultSet.getString("ARLCOD"));
    	flownMailExceptionVO.setFlightNumber(resultSet.getString("FLTNUM"));
    	if(resultSet.getDate("FLTDAT")!=null){
    		flownMailExceptionVO.setFlightDate(
    				new LocalDate(LocalDate.NO_STATION,Location.NONE,resultSet.getDate("FLTDAT")));
    	}
    	StringBuilder sb = new StringBuilder();
    	sb.append(resultSet.getString("POL"));
    	sb.append(" - ");
    	sb.append(resultSet.getString("POU"));
    	flownMailExceptionVO.setFlightSegment(sb.toString());
    	flownMailExceptionVO.setConsignmentDocNumber(resultSet.getString("CSGDOCNUM"));
    	flownMailExceptionVO.setDsnNumber(resultSet.getString("DSN"));
    	flownMailExceptionVO.setMailBagCount(resultSet.getInt("ACPBAG"));
    	flownMailExceptionVO.setExceptionCode(resultSet.getString("EXPCOD"));
    	flownMailExceptionVO.setAssigneeCode(resultSet.getString("ASGCOD"));
    	if(resultSet.getDate("ASGDAT")!=null){
    		flownMailExceptionVO.setAssignedDate(
    				new LocalDate(LocalDate.NO_STATION,Location.NONE,resultSet.getDate("ASGDAT")));
    	}
    	if(resultSet.getDate("RESDAT")!=null){
    		flownMailExceptionVO.setResolvedDate(
    				new LocalDate(LocalDate.NO_STATION,Location.NONE,resultSet.getDate("RESDAT")));
    	}
    	flownMailExceptionVO.setLastUpdatedUser(resultSet.getString("LSTUPDUSR"));
    	if(resultSet.getDate("LSTUPDTIM")!=null){
    		flownMailExceptionVO.setLastUpdatedTime(
    				new LocalDate(LocalDate.NO_STATION,Location.NONE,resultSet.getTimestamp("LSTUPDTIM")));
    	}
    	return flownMailExceptionVO;

    }
    
}
