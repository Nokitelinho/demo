/*
 * CarditPreAdviseMultiMapper.java Created on Feb 13, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.CarditPreAdviseReportVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * 
 * @author a-1944
 * 
 */
public class CarditPreAdviseMultiMapper implements
		MultiMapper<CarditPreAdviseReportVO> {
  
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	private String  airportCode =  "";
	private int ownArlIdr = 0;
	public CarditPreAdviseMultiMapper(String airportCode, int ownarlidr) {
		this.airportCode = airportCode;
		this.ownArlIdr = ownarlidr;
	}

	/**
	 * @author a-1944
	 * @param rs
	 * @throws SQLException
	 * @return
	 */

	public List<CarditPreAdviseReportVO> map(ResultSet rs) throws SQLException {
		log.entering("CarditPreAdviseMultiMapper", "map(ResultSet rs");
		List<CarditPreAdviseReportVO> reportVOS = new ArrayList<CarditPreAdviseReportVO>();
		log.log(Log.FINE, airportCode);
		log.log(Log.FINE, ownArlIdr);
		//Collection<String> addedCollection = new ArrayList<String>();
		
		
		CarditPreAdviseReportVO  reportVO = null;
		String currPreAdvKey = "";
		String prevPreAdvKey  = "";
		String prevFlightNumber = "";
		String currFlightNumber = "";
		String prevPort = "";
		String tempPort = "";
		String tempRoute = "";
		String currPort = "";
		int tempCarIdr = 0;
		
		while (rs.next()) {
	/*		currPreAdvKey = new StringBuilder().append(rs.getString("CMPCOD")).append(rs.getString("CDTKEY")).toString();
			if (!currPreAdvKey.equals(prevPreAdvKey)) {
				
				if(airportCode != null && airportCode.equals(rs.getString("DSTCOD"))){
					continue;
				}
				currFlightNumber = new StringBuilder().append(rs.getString("CARCOD")).append(" ").append(rs.getString("FLTNUM")).toString();
				prevFlightNumber = currFlightNumber;
				
				tempRoute = rs.getString("FLTROU");  
				if(tempRoute != null && tempRoute.trim().length() > 7 ){  
					     
					tempPort = tempRoute.substring(tempRoute.indexOf(rs.getString("ORGCOD"))+4, tempRoute.indexOf(rs.getString("DSTCOD"))+3);
					tempPort  = tempPort.replace("-", "/");
					currPort = tempPort;  
				}else{  
					currPort = new StringBuilder().append(rs.getString("DSTCOD")).toString();
				}  
				
				if("Y".equals (rs.getString("ISSPRTARL")) || ownArlIdr == rs.getInt("ARLIDR")){
					tempCarIdr = rs.getInt("ARLIDR");
				}else{
					tempCarIdr = 0;
				}
				
				if (tempCarIdr == 0){
					currPort = "TRANSIT";
				}
				prevPort = currPort;  
				prevPreAdvKey = currPreAdvKey;      
			}else{      
				
				currFlightNumber = new StringBuilder().append(rs.getString("CARCOD")).append(" ").append(rs.getString("FLTNUM")).toString();
				prevFlightNumber = new StringBuilder().append(prevFlightNumber).append("/").append(currFlightNumber).toString();
				tempRoute = rs.getString("FLTROU");
				if(tempRoute != null && tempRoute.trim().length() > 0 ){
					tempPort = tempRoute.substring(tempRoute.indexOf(rs.getString("ORGCOD"))+4, tempRoute.indexOf(rs.getString("DSTCOD"))+3);
					tempPort = tempPort.replace("-", "/");
					currPort = tempPort;
				}else{
					currPort = new StringBuilder().append(rs.getString("DSTCOD")).toString();
				}
				//currPort = new StringBuilder().append(rs.getString("DSTCOD")).toString();
				
				if("Y".equals (rs.getString("ISSPRTARL")) || ownArlIdr == rs.getInt("ARLIDR")){
					tempCarIdr = rs.getInt("ARLIDR");
				}else{
					tempCarIdr = 0;
				}
				if (tempCarIdr == 0){
					currPort = "TRANSIT";
				}
				prevPort = new StringBuilder().append(prevPort).append("/").append(currPort).toString();
			}
			
			String record = new StringBuilder().append(prevPort).append(prevFlightNumber).toString();
			if (!(addedCollection.contains(record))){
				reportVO = new CarditPreAdviseReportVO();
				reportVO.setCompanyCode(rs.getString("CMPCOD"));
				reportVO.setDestinationAirport(prevPort);
				reportVO.setFlightNumber(prevFlightNumber);
				reportVOS.add(reportVO);
				
				addedCollection.add(record);
			}*/			
			// Added By A-2882 for QF bug 102009 starts			
			reportVO = new CarditPreAdviseReportVO();
			reportVO.setCompanyCode(rs.getString("CMPCOD"));
			reportVO.setDestinationAirport(rs.getString("DST"));
			reportVO.setFlightNumber(rs.getString("FLT"));
			reportVO.setNumBags(rs.getInt("BAGCNT"));
			//reportVO.setMailBagWeight(rs.getDouble("BAGWGT"));	
			reportVO.setMailBagWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("BAGWGT")));//added by A-7371
			reportVOS.add(reportVO);
			// Added By A-2882 for QF bug 102009 ends
			
		}
		return reportVOS;
	}
	
}
