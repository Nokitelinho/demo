/*
 * FlightRevenueInterfaceMultimapper.java Created on Mar 29, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.FlightRevenueInterfaceVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author a-8061
 *
 */
public class FlightRevenueInterfaceMultimapper implements
		MultiMapper<FlightRevenueInterfaceVO> {
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "FlightRevenueInterfaceMultimapper";
	private static final String FLAG_YES = "Y";
	private static final String FLAG_NO = "N";
	private static final String CARR_TYPE_FLIGHT = "M";
	private static final String CARR_TYPE_TRUCK = "T";
	private static final String FLAG_FAIL = "F";
	//Added as part of ICRD-310185
	private static final double NEGATE = -1; 
	private static final String TRG_PNT_VOID = "VOID";
	
	
	

	/**
	 * @param rs
	 * @return List<FlightRevenueInterfaceVO>
	 * @throws SQLException
	 */
	public List<FlightRevenueInterfaceVO> map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		Collection<FlightRevenueInterfaceVO> flightRevenueInterfaceVOs = new ArrayList<FlightRevenueInterfaceVO>();
		FlightRevenueInterfaceVO flightRevenueInterfaceVO = null;
		
		String accountDateFormated=null;		
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true);

		
		
		while (rs.next()) {

			flightRevenueInterfaceVO = new FlightRevenueInterfaceVO();	
			if(currentDate!=null ){
			accountDateFormated = DateUtilities.format(currentDate, "yyMMdd");
			}	
			flightRevenueInterfaceVO.setCompanyCode(rs.getString("CMPCOD"));
			flightRevenueInterfaceVO.setMailSeqNumber(rs.getLong("MALSEQNUM"));
			flightRevenueInterfaceVO.setSerNumber(rs.getInt("SERNUM"));
			flightRevenueInterfaceVO.setVersionNumber(rs.getInt("VERNUM"));
			flightRevenueInterfaceVO.setAccountDate(accountDateFormated);
			flightRevenueInterfaceVO.setMailNumber(rs.getString("DSN"));
			flightRevenueInterfaceVO.setRegionCode(rs.getString("REGCOD")); 
			flightRevenueInterfaceVO.setBranchCode(rs.getString("STNNAM"));
			flightRevenueInterfaceVO.setMailCategory(rs.getString("MALCTGCOD"));
			flightRevenueInterfaceVO.setSettlement(rs.getString("SETCOD"));  //MODIFIED BY A-7929 AS PART OF ICRD-275010
			flightRevenueInterfaceVO.setSubClassGroup(rs.getString("SUBCLSGRP"));
			flightRevenueInterfaceVO.setMailOrigin(rs.getString("ORGCTYCOD"));
			flightRevenueInterfaceVO.setMailDestination(rs.getString("DSTCTYCOD"));
			
			if(CARR_TYPE_TRUCK.equals(rs.getString("FLTTYP"))){
				if(FLAG_YES.equals(rs.getString("INTFCDFLG"))){
					flightRevenueInterfaceVO.setAdjustCode("TRXO");
					
				}else if(FLAG_YES.equals(rs.getString("ADJFLG"))&& (FLAG_NO.equals(rs.getString("INTFCDFLG"))||FLAG_FAIL.equals(rs.getString("INTFCDFLG")) ) ){
					
					flightRevenueInterfaceVO.setAdjustCode("TRXR");
				}
				
			}else{
				if(FLAG_YES.equals(rs.getString("INTFCDFLG"))){
					flightRevenueInterfaceVO.setAdjustCode("FLTO");
					
				}else if(FLAG_YES.equals(rs.getString("ADJFLG"))&& (FLAG_NO.equals(rs.getString("INTFCDFLG")) ||FLAG_FAIL.equals(rs.getString("INTFCDFLG"))) ){//ADJ flag added for ICRD-274813
					
					flightRevenueInterfaceVO.setAdjustCode("FLTR");
				}
			}

			flightRevenueInterfaceVO.setCurrency(rs.getString("CTRCURCOD"));
			flightRevenueInterfaceVO.setFlightLineCode(rs.getString("FLTLINCOD"));
			flightRevenueInterfaceVO.sethLNumber(rs.getString("TALNUM"));
			
			//Modified by ICRD-310185
			if(FLAG_YES.equals(rs.getString("INTFCDFLG"))){
				flightRevenueInterfaceVO.setRateAmount(Double.toString(NEGATE*Double.parseDouble(rs.getString("TOTAMTCTR"))));	
				flightRevenueInterfaceVO.setMailWeight(Double.toString(NEGATE*Double.parseDouble(rs.getString("WGT"))));
				flightRevenueInterfaceVO.setRateAmountInUSD(Double.toString(NEGATE*Double.parseDouble(rs.getString("TOTAMTUSD"))));	
				
				flightRevenueInterfaceVO.setBlockCheckRateAmount(rs.getString("TOTAMTCTR"));	
				flightRevenueInterfaceVO.setBlockCheckMailWeight(rs.getString("WGT"));
				flightRevenueInterfaceVO.setBlockCheckRateAmountInUSD(rs.getString("TOTAMTUSD"));
				
			}
			else{
				flightRevenueInterfaceVO.setRateAmount(rs.getString("TOTAMTCTR"));	
				flightRevenueInterfaceVO.setMailWeight(rs.getString("WGT"));
				flightRevenueInterfaceVO.setRateAmountInUSD(rs.getString("TOTAMTUSD"));
			}

			flightRevenueInterfaceVO.setrSN(rs.getString("MALIDR"));//ICRD-274819
			flightRevenueInterfaceVO.setBillingBranch(rs.getString("POACOD"));		
			
			if(CARR_TYPE_TRUCK.equals(rs.getString("FLTTYP"))){
				flightRevenueInterfaceVO.setCarrTypeC(CARR_TYPE_TRUCK);
			}else{
				flightRevenueInterfaceVO.setCarrTypeC(CARR_TYPE_FLIGHT);
			}
			
			flightRevenueInterfaceVO.setFlightNumber(rs.getString("FLTCARCOD")+rs.getString("FLTNUM"));
			if(rs.getDate("FLTDAT")!=null){
				flightRevenueInterfaceVO.setFlightDate(DateUtilities.format(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("FLTDAT")), "yyyyMMdd"));
			}
			flightRevenueInterfaceVO.setFlightOrigin(rs.getString("SECFRM"));
			flightRevenueInterfaceVO.setFlightDestination(rs.getString("SECTOO"));
			
			if(rs.getDate("FSTFLTDAT")!=null){
				flightRevenueInterfaceVO.setFirstFlightDate(DateUtilities.format(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("FSTFLTDAT")), "yyyyMMdd"));
			}

			if(currentDate!=null ){
				flightRevenueInterfaceVO.setInterfaceDate(DateUtilities.format(currentDate, "yyMMddHHmm"));//ICRD-274687
			}	
			flightRevenueInterfaceVO.setSerialNumber(rs.getString("ERPSEQ"));
			flightRevenueInterfaceVO.setTriggerPoint(rs.getString("TRGPNT"));
			flightRevenueInterfaceVO.setInterfaceFlag(rs.getString("INTFCDFLG"));
			flightRevenueInterfaceVOs.add(flightRevenueInterfaceVO);
			
			
			
			if(rs.getString("TRGPNT")!=null && TRG_PNT_VOID.equals(rs.getString("TRGPNT"))){
				flightRevenueInterfaceVO.setFlightNumber(rs.getString("VODFLTNUM"));
				flightRevenueInterfaceVO.setFlightDate(rs.getString("VODFLTDAT"));
				flightRevenueInterfaceVO.setFirstFlightDate(rs.getString("VODFSTFLTDAT"));
			}
			
			

		}
		log.exiting(CLASS_NAME, "map");
		return (ArrayList<FlightRevenueInterfaceVO>) flightRevenueInterfaceVOs;
	}
}
