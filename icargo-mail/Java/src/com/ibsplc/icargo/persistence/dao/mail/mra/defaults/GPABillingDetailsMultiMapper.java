/*
 * GPABillingDetailsMultiMapper.java Created on March 20, 2009
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
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-3434
 *
 */
public class GPABillingDetailsMultiMapper  implements MultiMapper<DocumentBillingDetailsVO> {

	private static final String CLASS_NAME = "GPABillingDetailsMultiMapper";

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	/**
	 *
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */

	public List<DocumentBillingDetailsVO> map(ResultSet rs) throws SQLException {
		log.entering("GPABillingDetailsMultiMapper", "map"); 

		List<DocumentBillingDetailsVO> documentBillingDetailsVOs =
			new ArrayList<DocumentBillingDetailsVO>();

		DocumentBillingDetailsVO   documentBillingDetailsVO =null;


		String dsnKey = null;
		String dsnKeyPrev = null;
		Double weight=0.0D;
		int rate=0;
		Double amount=0.0D;

		while(rs.next()) {

			dsnKey=new StringBuilder().append(rs.getString("CMPCOD"))
			.append(rs.getString("BLGBAS"))
			.append(rs.getString("CSGDOCNUM"))
			.append(rs.getInt("CSGSEQNUM"))
			.append(rs.getString("POACOD"))
			.append(rs.getString("INVNUM"))
			.append(rs.getString("SEQNUM"))
			.append(rs.getString("CCAREFNUM"))
			
			.toString();

			if(!dsnKey.equals(dsnKeyPrev)){ 
				/* 
				 * 
				 * for eliminating duplicates (Parent)
				 */

//				if(documentBillingDetailsVO!=null){
//
//					increment();						
//
//				}

				dsnKeyPrev = dsnKey;
				documentBillingDetailsVO = new DocumentBillingDetailsVO();
				documentBillingDetailsVOs.add(documentBillingDetailsVO);
				documentBillingDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
				documentBillingDetailsVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
				documentBillingDetailsVO.setBillingStatus(rs.getString("BLGSTA"));
				documentBillingDetailsVO.setSerialNumber(rs.getInt("SEQNUM") );
				documentBillingDetailsVO.setBillingBasis(rs.getString("BLGBAS"));
				documentBillingDetailsVO.setGpaCode(rs.getString("COD"));

				if(rs.getDate("DAT") != null) {
					documentBillingDetailsVO.setFlightDate(new LocalDate(
							LocalDate.NO_STATION, Location.NONE, rs.getDate("DAT")));
				}

				documentBillingDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
				documentBillingDetailsVO.setInvoiceNumber(rs.getString("INVNUM"));
				documentBillingDetailsVO.setCsgDocumentNumber(rs.getString("CSGDOCNUM"));
				documentBillingDetailsVO.setCsgSequenceNumber(rs.getInt("CSGSEQNUM"));
				documentBillingDetailsVO.setPoaCode(rs.getString("POACOD"));
				documentBillingDetailsVO.setOrgOfficeOfExchange(rs.getString("ORG"));
				documentBillingDetailsVO.setDestOfficeOfExchange(rs.getString("DST"));
				documentBillingDetailsVO.setOrigin(rs.getString("ORGCOD"));
				documentBillingDetailsVO.setDestination(rs.getString("DSTCOD"));
				documentBillingDetailsVO.setCategory(rs.getString("CTGCOD"));
				documentBillingDetailsVO.setSubClass(rs.getString("SUBCLS"));
				documentBillingDetailsVO.setYear(rs.getString("YER"));
				documentBillingDetailsVO.setDsn(rs.getString("DSN"));
				documentBillingDetailsVO.setNoofMailbags(rs.getString("PCS"));
				if(rs.getString("CCATYP")!=null)
					{
					documentBillingDetailsVO.setWeight(rs.getDouble("REVGRSWGT"));
					}
				else
				{
				documentBillingDetailsVO.setWeight(rs.getDouble("WGT"));
				}
				if(rs.getDouble("WGT")== 0.0 ){
					documentBillingDetailsVO.setWeight(rs.getDouble("REVGRSWGT"));
				}
				
				documentBillingDetailsVO.setRsn(rs.getString("RSN")); 
				documentBillingDetailsVO.setHni(rs.getString("HSN")); 
				documentBillingDetailsVO.setRegInd(rs.getString("REGIND")); 
				
				
				documentBillingDetailsVO.setCcaType(rs.getString("CCATYP"));

				if(rs.getTimestamp("LSTUPDTIM") != null) {
					documentBillingDetailsVO.setLastUpdatedTime(new LocalDate(
							LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("LSTUPDTIM")));
				}
				documentBillingDetailsVO.setCcaRefNumber(rs.getString("CCAREFNUM"));
				//added for cr ICRD 7370
				
				documentBillingDetailsVO.setTds(rs.getDouble("TDS"));
				
				documentBillingDetailsVO.setApplicableRate(rs.getDouble("APLRAT"));
				documentBillingDetailsVO.setRemarks(rs.getString("RMK"));
				if(rs.getString("REVCURCOD") != null){
					documentBillingDetailsVO.setCurrency(rs.getString("REVCURCOD"));
				}else{
					documentBillingDetailsVO.setCurrency(rs.getString("CTRCURCOD"));
				}
				try{
					if(documentBillingDetailsVO.getCurrency()!=null){
					Money valCharges =  CurrencyHelper.getMoney(documentBillingDetailsVO.getCurrency());
					Money netAmount =  CurrencyHelper.getMoney(documentBillingDetailsVO.getCurrency());
					Money serviceTax =  CurrencyHelper.getMoney(documentBillingDetailsVO.getCurrency());
					Money grossAmount =  CurrencyHelper.getMoney(documentBillingDetailsVO.getCurrency());
					Money mailChg =  CurrencyHelper.getMoney(documentBillingDetailsVO.getCurrency());
					Money surChg =  CurrencyHelper.getMoney(documentBillingDetailsVO.getCurrency());
					valCharges.setAmount(rs.getDouble("VALCHGCTR"));
					netAmount.setAmount(rs.getDouble("NETAMT"));
					serviceTax.setAmount(rs.getDouble("SRVTAX"));
					grossAmount.setAmount(rs.getDouble("GRSAMT"));
					mailChg.setAmount(rs.getDouble("MAILCHG"));
					surChg.setAmount(rs.getDouble("SURCHG"));
					documentBillingDetailsVO.setValCharges(valCharges);
					documentBillingDetailsVO.setNetAmount(netAmount); 
					documentBillingDetailsVO.setServiceTax(serviceTax);
					documentBillingDetailsVO.setGrossAmount(grossAmount);
					documentBillingDetailsVO.setMailChg(mailChg);
					documentBillingDetailsVO.setSurChg(surChg);
					}
				}catch(CurrencyException currencyException){
					log.log(Log.FINE, currencyException.getErrorCode());
				}
				
				//Added by A-6991 for ICRD-203474
				documentBillingDetailsVO.setDeclaredValue(rs.getDouble("DCLVAL"));
				//Added by A-6991 for ICRD-137019 Starts
				documentBillingDetailsVO.setRateIdentifier(rs.getString("RATIDF"));
				documentBillingDetailsVO.setPaBuilt(rs.getString("POAFLG"));
				documentBillingDetailsVO.setActualWeight(rs.getDouble("ACTULDWGT"));
				documentBillingDetailsVO.setContainerID(rs.getString("ULDNUM"));
				documentBillingDetailsVO.setActualWeightUnit(rs.getString("ACTULDWGTUNT"));
				documentBillingDetailsVO.setRateLineIdentifier(rs.getString("RATLINIDR"));
				documentBillingDetailsVO.setRateType(rs.getString("RATTYP"));
				if(rs.getDate("DSNDAT")!=null){//modified as part of ICRD-277615
				documentBillingDetailsVO.setDsnDate(new LocalDate(
						LocalDate.NO_STATION, Location.NONE,rs.getDate("DSNDAT")));
				}
				documentBillingDetailsVO.setIsUSPSPerformed(rs.getString("MALPERFLG"));// Added by A-8464 for ICRD-232381 
				documentBillingDetailsVO.setUnitCode(rs.getString("UNTCOD"));// Added by A-8527 for IASCB-22915
			
				//Added by A-6991 for ICRD-137019 Starts
				//Added as a part of ICRD-237777 by A-7540
				
				
				
				//	    		rate=documentBillingDetailsVO.getApplicableRate();	    		
				//	    		weight=documentBillingDetailsVO.getWeight();	    		
				//	    		amount=rate*weight;
				//	    		documentBillingDetailsVO.setGrossAmount(amount);


			}

		}
		//	This part of the code is required to add the last parent
		//The last parent wont be added by the main loop 
//		if(documentBillingDetailsVO!=null){
//
//			increment();						
//
//		}


		log.log(Log.INFO, "---size of documentBillingDetailsVOs--->",
				documentBillingDetailsVOs.size());
		return documentBillingDetailsVOs;
	}					    											    											  


}
