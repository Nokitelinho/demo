/*
 * InvoiceDetailsForReportMultiMapper.java Created on Mar 16 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineInvoiceReportVO;
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
 * 0.1 		Mar 16 2007  Sandeep.T 		Initial draft
 *
 *
 */


public class  InvoiceDetailsForReportMultiMapper implements MultiMapper<AirlineInvoiceReportVO> {
	private Log log = LogFactory.getLogger("MRA_airlineBILLING");

	/**Collection<>
	 * MultiMapper for InvoiceDetailsReportVO
	 * @param rs
	 * @return List<InvoiceDetailsReportVO>
	 * @throws SQLException
	 */
	public List<AirlineInvoiceReportVO> map(ResultSet rs) throws SQLException {
		log.log(Log.FINE," Inside InvoiceDetailsForReportMultiMapper--->");

		//AirlineInvoiceReportVO

		HashMap<String, Collection<AirlineInvoiceReportVO>> airlineInvoiceReportVoMap =
			                     new HashMap<String, Collection<AirlineInvoiceReportVO>>();

		String key = null;
		String finalSector = "";
		String genSector   = null;
		AirlineInvoiceReportVO airlineInvoiceReportVO = null;

		//Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs = new ArrayList<InvoiceDetailsReportVO>();
		while( rs.next() ) {

			key = new StringBuilder()
			.append(rs.getString("CMPCOD"))
			.append(rs.getString("ARLIDR"))
			.append(rs.getString("INVNUM"))
			.toString();


			if(airlineInvoiceReportVoMap.containsKey(key)) {


				airlineInvoiceReportVO =new AirlineInvoiceReportVO();


			if(rs.getDate( "BLDDAT" ) != null) {
				airlineInvoiceReportVO.setBilledDate( new LocalDate(
						LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( "BLDDAT" )).toDisplayDateOnlyFormat());
			  }
			   airlineInvoiceReportVO.setSector(rs.getString("SECTOR"));
			   airlineInvoiceReportVO.setBillingCurrencyCode(rs.getString("BLGCURCOD"));
			   airlineInvoiceReportVO.setContractCurrencyCode(rs.getString("CRTCURCOD"));
			   airlineInvoiceReportVO.setInvoiceNumber(rs.getString("INVNUM"));
			   //airlineInvoiceReportVO.setTotalAmountinBillingCurrency(rs.getDouble("TOTAMTBLGCUR"));
			   airlineInvoiceReportVO.setTotalAmountinBillingCurrency(rs.getDouble("TOTAMTLSTCUR"));  //Modified by A-7929 as part of ICRD-265471
			   airlineInvoiceReportVO.setTotalAmountinContractCurrency(rs.getDouble("TOTAMTCRTCUR"));
				if(rs.getDate( "FRMDAT" ) != null) {
					airlineInvoiceReportVO.setFromBillingPeriod( new LocalDate(
							LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("FRMDAT")).toDisplayDateOnlyFormat());

				    }
				if(rs.getDate( "TOODAT" ) != null) {
					airlineInvoiceReportVO.setToBillingPeriod( new LocalDate(
							LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( "TOODAT" )).toDisplayDateOnlyFormat());
					}

				Collection<AirlineInvoiceReportVO> collnExisting = airlineInvoiceReportVoMap.get(key);
				collnExisting.add(airlineInvoiceReportVO);
			}else{
				airlineInvoiceReportVO =new AirlineInvoiceReportVO();
				    if(rs.getDate( "BLDDAT" ) != null) {
				    	airlineInvoiceReportVO.setBilledDate( new LocalDate(
							LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( "BLDDAT" )).toDisplayDateOnlyFormat());
				    }
				    airlineInvoiceReportVO.setSector(rs.getString("SECTOR"));
				    airlineInvoiceReportVO.setBillingCurrencyCode(rs.getString("BLGCURCOD"));
				    airlineInvoiceReportVO.setContractCurrencyCode(rs.getString("CRTCURCOD"));
				    
				    airlineInvoiceReportVO.setInvoiceNumber(rs.getString("INVNUM"));
				    //airlineInvoiceReportVO.setTotalAmountinBillingCurrency(rs.getDouble("TOTAMTBLGCUR"));
				    airlineInvoiceReportVO.setTotalAmountinBillingCurrency(rs.getDouble("TOTAMTLSTCUR"));  //Modified by A-7929 as part of ICRD-265471
				    airlineInvoiceReportVO.setTotalAmountinContractCurrency(rs.getDouble("TOTAMTCRTCUR"));
					if(rs.getDate( "FRMDAT" ) != null) {
						airlineInvoiceReportVO.setFromBillingPeriod( new LocalDate(
								LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( "FRMDAT" )).toDisplayDateOnlyFormat().toUpperCase());


					    }
					if(rs.getDate( "TOODAT" ) != null) {
						airlineInvoiceReportVO.setToBillingPeriod( new LocalDate(
								LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( "TOODAT" )).toDisplayDateOnlyFormat().toUpperCase());
						 }
					Collection<AirlineInvoiceReportVO> collnToAdd = new ArrayList<AirlineInvoiceReportVO>();
					collnToAdd.add(airlineInvoiceReportVO);
					airlineInvoiceReportVoMap.put(key,collnToAdd);
   			}


		}
		// for getting all the sectors for the report
		if(airlineInvoiceReportVoMap.entrySet()!=null){
		for(Entry entry : airlineInvoiceReportVoMap.entrySet()){
			log.log(Log.FINE,"INSIDE FOR LOOP TO GENERATE SECTOR");
			//log.log(Log.FINE,"NUMBER OF VOS GOT "+airlineInvoiceReportVoMap.get(mapKey).size());
			  String firstLoop = "X";
			  for(AirlineInvoiceReportVO reportVO : airlineInvoiceReportVoMap.get(entry.getKey())){
				 genSector = reportVO.getSector();
				 if(("X").equals(firstLoop)){
					 finalSector = genSector;
					 firstLoop = "Y";
				 }else{
//					finalSector = new StringBuilder().append(reportVO.getSector()).toString();
					  finalSector = new StringBuilder().append(genSector).append(",").append(finalSector).toString();
				 }

				  
				  log.log(Log.FINE, "SECTOR In loop ", finalSector);
				}
			  log.log(Log.FINE, "THE FINAL SECTOR IS ", finalSector);
		}
		}


		Collection<AirlineInvoiceReportVO> airlineInvoiceReportVos = new ArrayList<AirlineInvoiceReportVO>();
		if(airlineInvoiceReportVoMap.get(key)!=null){
		for(AirlineInvoiceReportVO reportVO : airlineInvoiceReportVoMap.get(key)){
			log.log(Log.FINE,"LOOP TO CREATE THE FINAL VO ");
			log.log(Log.FINE, "KEY USED HERE IS ", key);
			reportVO.setSector(finalSector);
			airlineInvoiceReportVos.add(reportVO);
			break;
		}
		}


		log.log(Log.FINE,
				"\n\n\n\n The  Collection of Departed Vo is \n\n\n --->",
				airlineInvoiceReportVos.size());
		log
				.log(
						Log.FINE,
						"\n\n\n\n The  Collection of invoiceDetailsReportVo is \n\n\n --->",
						airlineInvoiceReportVos);
		return (ArrayList<AirlineInvoiceReportVO>)airlineInvoiceReportVos;

	}
}
