/*
 * FlownExceptionsPrintMapper.java Created on Jan 9, 2007
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
 * @author a-2401
 *
 */
public class FlownExceptionsPrintMapper implements Mapper<FlownMailExceptionVO>{

	 /**
     * @param resultSet
     * @return FlownMailExceptionVO
     * @throws SQLException
     */
	 public FlownMailExceptionVO map(ResultSet resultSet) throws SQLException {

		 FlownMailExceptionVO flownMailExceptionVO = new FlownMailExceptionVO();
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
	    flownMailExceptionVO.setExceptionCode(resultSet.getString("EXPCOD"));
	    flownMailExceptionVO.setAssigneeCode(resultSet.getString("ASGCOD"));
	    flownMailExceptionVO.setTotalNoOfExceptions(resultSet.getInt("COUNT"));
	    return flownMailExceptionVO;
	 }

}
