/*
 * FlownSegmentDetailsMapper.java Created on Jan 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.flown;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;



/**
 * FlownSegmentDetailsMapper for Flight details
 * @author A-2259
 *
 */
public class FlownSegmentDetailsMapper implements Mapper<FlownMailSegmentVO> {

	 /**
     * @param resultSet
     * @return MailBagForFlownSegmentVO
     * @throws SQLException
     */
   
    public FlownMailSegmentVO map(ResultSet resultSet) throws SQLException {
    	
    	FlownMailSegmentVO flownMailSegmentVO=new FlownMailSegmentVO();
    	flownMailSegmentVO.setCompanyCode(resultSet.getString("CMPCOD"));
    	flownMailSegmentVO.setFlightCarrierId(resultSet.getInt("FLTCARIDR"));
    	flownMailSegmentVO.setFlightNumber(resultSet.getString("FLTNUM"));
    	flownMailSegmentVO.setFlightSequenceNumber(resultSet.getInt("FLTSEQNUM"));
    	flownMailSegmentVO.setSegmentDestination(resultSet.getString("SEGDST"));
    	flownMailSegmentVO.setSegmentOrigin(resultSet.getString("SEGORG"));
    	flownMailSegmentVO.setSegmentSerialNumber(resultSet.getInt("SEGSERNUM"));
    	flownMailSegmentVO.setFlightSegmentStatus(resultSet.getString("SEGSTA"));
    	flownMailSegmentVO.setFlightStatus(resultSet.getString("FLTSTA"));
    	
    	// added for Flown Reports
    	if(resultSet.getDate("FLTDAT") != null) {
			flownMailSegmentVO.setStringFlightDate(
    				new LocalDate(LocalDate.NO_STATION,Location.NONE,resultSet.getDate("FLTDAT")).toDisplayDateOnlyFormat().toUpperCase());
		}
    	
    	if(resultSet.getString("FLTORG") != null) {
			flownMailSegmentVO.setFlightOrigin(resultSet.getString("FLTORG"));
		}
    	
    	if(resultSet.getString("FLTDST") != null) {
			flownMailSegmentVO.setFlightDestination(resultSet.getString("FLTDST"));
		}
    	
    	if(resultSet.getString("SEGSTA") != null) {
			flownMailSegmentVO.setSegmentStatus(resultSet.getString("SEGSTA"));
		}
    	
    	if(resultSet.getString("TWOAPHCOD") != null) {
			flownMailSegmentVO.setFlightCarrierCode(resultSet.getString("TWOAPHCOD"));
		}
    	
    	if(resultSet.getString("DSNORG") != null) {
			flownMailSegmentVO.setMailOrigin(resultSet.getString("DSNORG"));
		}
    	
    	if(resultSet.getString("DSNDST") != null) {
			flownMailSegmentVO.setMailDestination(resultSet.getString("DSNDST"));
		}
    	
    	if(resultSet.getString("DSN") != null) {
			flownMailSegmentVO.setDespatch(resultSet.getString("DSN"));
		}
    	
    	if(resultSet.getString("DSNBAG") != null) {
			flownMailSegmentVO.setBagCount(resultSet.getString("DSNBAG"));
		}
    	
    	if(resultSet.getString("DSNWGT") != null) {
			flownMailSegmentVO.setBagWeight(resultSet.getString("DSNWGT"));
		}
    	if(resultSet.getString("WGTUNT") != null) {
			flownMailSegmentVO.setBagWeightUnit(resultSet.getString("WGTUNT"));
		}
    	
        return flownMailSegmentVO;
    }

}
