/*
 * CN66DetailsPrintMapper.java Created on Mar 16, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsPrintVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2408
 *
 */
public class CN66DetailsPrintMapper implements Mapper<CN66DetailsPrintVO> {

	private Log log = LogFactory.getLogger("MRA GPABILLING");


	/**
	 * @param resultSet
	 * @throws SQLException
	 * @return vo
	 */
    public CN66DetailsPrintVO map(ResultSet resultSet) throws SQLException {
    	log.entering("CN66DetailsPrintMapper","map");
    	CN66DetailsPrintVO vo=new CN66DetailsPrintVO();
    	vo.setCompanyCode(resultSet.getString("CMPCOD"));
    	vo.setBillingPeriod(resultSet.getString("BLDPRD"));
    	vo.setGpaCode(resultSet.getString("GPACOD"));
    	vo.setGpaName(resultSet.getString("POANAM"));
    	vo.setOrigin(resultSet.getString("ORGCOD"));
    	vo.setDestination(resultSet.getString("DSTCOD"));
    	vo.setMailCategoryCode(resultSet.getString("MALCTGCOD"));
    	if(("LC").equals(resultSet.getString("SUBCLSGRP"))){
    		vo.setTotalLcWeight(resultSet.getDouble("TOTWGT"));
    		vo.setDisplayWgtUnitLC(resultSet.getString("UNTCOD"));
    	}else if(("CP").equals(resultSet.getString("SUBCLSGRP"))){
    		vo.setTotalCpWeight(resultSet.getDouble("TOTWGT"));
    		vo.setDisplayWgtUnitCP(resultSet.getString("UNTCOD"));
    	}else if(("SV").equals(resultSet.getString("SUBCLSGRP"))){
    		vo.setTotalSvWeight(resultSet.getDouble("TOTWGT"));	
    		vo.setDisplayWgtUnitSV(resultSet.getString("UNTCOD"));
    	}else if(("EMS").equals(resultSet.getString("SUBCLSGRP"))){
    		vo.setTotalEmsWeight(resultSet.getDouble("TOTWGT"));
    		vo.setDisplayWgtUnitEMS(resultSet.getString("UNTCOD"));
    		}
    	if(resultSet.getString("RCVDAT")!=null && resultSet.getString("RCVDAT").trim().length()>0){
    		vo.setReceivedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultSet.getTimestamp("RCVDAT")));
    	}
    	vo.setDsn(resultSet.getString("DSN"));
    	vo.setBilledFrom(resultSet.getString("BILSECFRM"));
    	vo.setBillingTo(resultSet.getString("BILSECTOO"));
    	vo.setFlightCarrierCode(resultSet.getString("FLTCARCOD"));
    	vo.setFlightNumber(resultSet.getString("FLTNUM"));  
    	vo.setMailSubclass(resultSet.getString("SUBCLSGRP"));
    	vo.setWeight(resultSet.getDouble("TOTWGT"));
    	vo.setUnitCode(resultSet.getString("UNTCOD"));
    	    
    	log.exiting("CN51DetailsPrintMapper","map");
        return vo;
    }

}
