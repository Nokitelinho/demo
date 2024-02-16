/*
 * InvoiceDetailsReportMultiMapper.java Created on Mar 02 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceDetailsReportVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Sandeep.T
 * Mapper for InvoiceDetailsReport.
 * 
 * 
 * Revision History
 * 
 * Version     Date 		Author 				Description
 * 
 * 0.1 		Mar 02 2007  Sandeep.T 		Initial draft
 * 
 * 
 */


public class  InvoiceDetailsReportMultiMapper implements MultiMapper<InvoiceDetailsReportVO> {
	private Log log = LogFactory.getLogger("MRA_GPABILLING");	
	
	/**Collection<>
	 * MultiMapper for InvoiceDetailsReportVO
	 * @param rs
	 * @return List<InvoiceDetailsReportVO>
	 * @throws SQLException
	 */
	public List<InvoiceDetailsReportVO> map(ResultSet rs) throws SQLException {
		log.log(Log.FINE,"\n\n\n\n Inside Multi  Mapper Classs InvoiceDetailsReportMultiMapper--->");
		HashMap<String, Collection<InvoiceDetailsReportVO>> invoiceDetailsReportVOMap =
			new HashMap<String, Collection<InvoiceDetailsReportVO>>();
		
		String key = null;
		String finalSector = "";
		String genSector   = null;
		InvoiceDetailsReportVO invoiceDetailsReportVO = null;
		//Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs = new ArrayList<InvoiceDetailsReportVO>();
		while( rs.next() ) {

			key = new StringBuilder()
			.append(rs.getString("CMPCOD"))
			.append(rs.getString("GPACOD"))
			.append(rs.getString("INVNUM"))
			.toString();
			
			
			if(invoiceDetailsReportVOMap.containsKey(key)) {
			
			
			invoiceDetailsReportVO =new InvoiceDetailsReportVO();
			
			if(rs.getDate( "BLDDAT" ) != null) {
				invoiceDetailsReportVO.setBilledDate( new LocalDate(
						LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( "BLDDAT" )));
				invoiceDetailsReportVO.setBilledDateString( new LocalDate(
						LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( "BLDDAT" )).toDisplayDateOnlyFormat());
			  }
			    invoiceDetailsReportVO.setSector(rs.getString("SECTOR"));
			    invoiceDetailsReportVO.setBillingCurrencyCode(rs.getString("BLGCURCOD"));
			    invoiceDetailsReportVO.setContractCurrencyCode(rs.getString("CRTCURCOD"));
				invoiceDetailsReportVO.setInvoiceNumber(rs.getString("INVNUM"));
				invoiceDetailsReportVO.setTotalAmountinBillingCurrency(rs.getDouble("TOTAMTBLGCUR"));
				invoiceDetailsReportVO.setTotalAmountinContractCurrency(rs.getDouble("TOTAMTCRTCUR"));
				if(rs.getDate( "BLGPRDFRM" ) != null) {
					invoiceDetailsReportVO.setFromBillingPeriod( new LocalDate(
							LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("BLGPRDFRM")));
					invoiceDetailsReportVO.setFromDateString( new LocalDate(
							LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( "BLGPRDFRM" )).toDisplayDateOnlyFormat());
					 
					
				    }
				if(rs.getDate( "BLGPRDTOO" ) != null) {
					invoiceDetailsReportVO.setToBillingPeriod( new LocalDate(
							LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( "BLGPRDTOO" )));
					invoiceDetailsReportVO.setToDateString( new LocalDate(
							LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( "BLGPRDTOO" )).toDisplayDateOnlyFormat());
					
					}
				Collection<InvoiceDetailsReportVO> collnExisting = invoiceDetailsReportVOMap.get(key);
				collnExisting.add(invoiceDetailsReportVO);
			}else{
				invoiceDetailsReportVO =new InvoiceDetailsReportVO();
				    if(rs.getDate( "BLDDAT" ) != null) {
					invoiceDetailsReportVO.setBilledDate( new LocalDate(
							LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( "BLDDAT" )));
				    }
				    invoiceDetailsReportVO.setSector(rs.getString("SECTOR"));
				    invoiceDetailsReportVO.setBillingCurrencyCode(rs.getString("BLGCURCOD"));
				    invoiceDetailsReportVO.setContractCurrencyCode(rs.getString("CRTCURCOD"));
					invoiceDetailsReportVO.setInvoiceNumber(rs.getString("INVNUM"));
					invoiceDetailsReportVO.setTotalAmountinBillingCurrency(rs.getDouble("TOTAMTBLGCUR"));
					invoiceDetailsReportVO.setTotalAmountinContractCurrency(rs.getDouble("TOTAMTCRTCUR"));
					if(rs.getDate( "BLGPRDFRM" ) != null) {
						invoiceDetailsReportVO.setFromBillingPeriod( new LocalDate(
								LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( "BLGPRDFRM" )));
						
						invoiceDetailsReportVO.setFromDateString( new LocalDate(
								LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( "BLGPRDFRM" )).toDisplayDateOnlyFormat().toUpperCase());
						 
					    }
					if(rs.getDate( "BLGPRDTOO" ) != null) {
						invoiceDetailsReportVO.setToBillingPeriod( new LocalDate(
								LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( "BLGPRDTOO" )));
						invoiceDetailsReportVO.setToDateString( new LocalDate(
								LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( "BLGPRDTOO" )).toDisplayDateOnlyFormat().toUpperCase());
						
						 }
					Collection<InvoiceDetailsReportVO> collnToAdd = new ArrayList<InvoiceDetailsReportVO>();
					collnToAdd.add(invoiceDetailsReportVO);
   				    invoiceDetailsReportVOMap.put(key,collnToAdd);
   			}
			
			
		}
		// for getting all the sectors for the report
//		for(String mapKey : invoiceDetailsReportVOMap.keySet()){
//			log.log(Log.FINE,"INSIDE FOR LOOP TO GENERATE SECTOR");
//			//log.log(Log.FINE,"NUMBER OF VOS GOT "+invoiceDetailsReportVOMap.get(mapKey).size());
//			log.log(Log.FINE,"invoiceDetailsReportVOMap.get(mapKey)"+invoiceDetailsReportVOMap.get(mapKey));
//			  for(InvoiceDetailsReportVO reportVO : invoiceDetailsReportVOMap.get(mapKey)){
//				 genSector = reportVO.getSector();
//				  
//				  //finalSector = new StringBuilder().append(reportVO.getSector()).toString();
//				  finalSector = new StringBuilder().append(genSector).append(",").append(finalSector).toString();
//				  log.log(Log.FINE,"SECTOR In loop "+finalSector);
//				}
//			  finalSector = finalSector.substring(0,finalSector.length()-1);
//			  log.log(Log.FINE,"THE FINAL SECTOR IS "+finalSector);
//		}
		for(Entry e : invoiceDetailsReportVOMap.entrySet()){
			log.log(Log.FINE,"INSIDE FOR LOOP TO GENERATE SECTOR");
			//log.log(Log.FINE,"NUMBER OF VOS GOT "+invoiceDetailsReportVOMap.get(mapKey).size());
			//log.log(Log.FINE,"invoiceDetailsReportVOMap.get(mapKey)"+invoiceDetailsReportVOMap.get(mapKey));
			    Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs = 
			    	                           (ArrayList<InvoiceDetailsReportVO>)e.getValue();
			  for(InvoiceDetailsReportVO reportVO : invoiceDetailsReportVOs){
				 genSector = reportVO.getSector();
				  
				  //finalSector = new StringBuilder().append(reportVO.getSector()).toString();
				  finalSector = new StringBuilder().append(genSector).append(",").append(finalSector).toString();
				  log.log(Log.FINE, "SECTOR In loop ", finalSector);
				}
			  finalSector = finalSector.substring(0,finalSector.length()-1);
			  log.log(Log.FINE, "THE FINAL SECTOR IS ", finalSector);
		}
		
		
		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVoS = new ArrayList<InvoiceDetailsReportVO>();
		for(InvoiceDetailsReportVO reportVO : invoiceDetailsReportVOMap.get(key)){
			log.log(Log.FINE,"LOOP TO CREATE THE FINAL VO ");
			log.log(Log.FINE, "KEY USED HERE IS ", key);
			reportVO.setSector(finalSector);
			invoiceDetailsReportVoS.add(reportVO);
			break;
		}
		
		
		log.log(Log.FINE,
				"\n\n\n\n The  Collection of Departed Vo is \n\n\n --->",
				invoiceDetailsReportVoS.size());
		log
				.log(
						Log.FINE,
						"\n\n\n\n The  Collection of invoiceDetailsReportVo is \n\n\n --->",
						invoiceDetailsReportVoS);
		return (ArrayList<InvoiceDetailsReportVO>)invoiceDetailsReportVoS;
		
	}
}
