/* AirlineCN66DetailsMapper.java Created on Feb 19, 2007
 * 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingPersistenceConstants;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2518
 * 
 */
public class AirlineCN66DetailsMapper implements Mapper<AirlineCN66DetailsVO> {

	private Log log = LogFactory.getLogger("UPUCalenderDetailsMapper");

	private static final String CLASS_NAME = "AirlineCN66DetailsMapper";

	/**
	 * @return AirlineCN66DetailsVO
	 * @param rs
	 * @throws SQLException
	 */
	public AirlineCN66DetailsVO map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		AirlineCN66DetailsVO airlineCn66DetailsVo = new AirlineCN66DetailsVO();
		airlineCn66DetailsVo.setAirlineIdentifier(rs.getInt("ARLIDR"));
		airlineCn66DetailsVo.setCarrierCode(rs.getString("CARCOD"));
		airlineCn66DetailsVo.setCarriageFrom(rs.getString("CARFRM"));
		airlineCn66DetailsVo.setCarriageTo(rs.getString("CARTOO"));
		airlineCn66DetailsVo.setClearancePeriod(rs.getString("CLRPRD"));
		airlineCn66DetailsVo.setCompanyCode(rs.getString("CMPCOD"));
		/*if(airlineCn66DetailsVo.getCarrierCode()!=null){
			if(rs.getString("FLTNUM")!=null){
				airlineCn66DetailsVo.setCarCode(airlineCn66DetailsVo.getCarrierCode().concat(" ").concat(rs.getString("FLTNUM")));
			}
			else{
				
				airlineCn66DetailsVo.setCarCode(airlineCn66DetailsVo.getCarrierCode());
				
			}
		
		}*/
		if (rs.getDate("DSNDAT") != null) {
			airlineCn66DetailsVo.setDespatchDate(new LocalDate(NO_STATION,
					NONE, rs.getDate("DSNDAT")));
			airlineCn66DetailsVo.setDespachDate((new LocalDate(
					LocalDate.NO_STATION,Location.NONE,rs.getTimestamp( "DSNDAT" )).toDisplayDateOnlyFormat().toUpperCase()));
		}
		if (rs.getDate("FLTDAT") != null) {
			airlineCn66DetailsVo.setFlightDate(new LocalDate(NO_STATION, NONE,
					rs.getDate("FLTDAT")));
		}
		airlineCn66DetailsVo.setDespatchSerialNo(rs.getString("DSN"));
		airlineCn66DetailsVo.setDespatchStatus(rs.getString("MALSTA"));   //Modified by A-7929 as part of ICRD-265471
		airlineCn66DetailsVo.setDestination(rs.getString("DSTCOD"));
		airlineCn66DetailsVo.setDestinationExchangeOffice(rs
				.getString("DSTEXGOFC"));
		airlineCn66DetailsVo.setFlightNumber(rs.getString("FLTNUM"));
		airlineCn66DetailsVo.setInterlineBillingType(rs.getString("INTBLGTYP"));
		airlineCn66DetailsVo.setInvoiceNumber(rs.getString("INVNUM"));
		airlineCn66DetailsVo.setMailCategoryCode(rs.getString("MALCTGCOD"));
		/*if(rs.getString("MALSUBCLS").startsWith("C")){
			airlineCn66DetailsVo.setMailSubClass(MRAAirlineBillingPersistenceConstants.MAILSUBCLASS_CP);
		}else{
			airlineCn66DetailsVo.setMailSubClass(MRAAirlineBillingPersistenceConstants.MAILSUBCLASS_LC);
		}*/
		
		airlineCn66DetailsVo.setMailSubClass(rs.getString("SUBCLSGRP"));
		if((MRAAirlineBillingPersistenceConstants.MAILSUBCLASS_LC).equals(airlineCn66DetailsVo.getMailSubClass()))
			{
			airlineCn66DetailsVo.setWeightLC(rs.getDouble("TOTWGT"));
			}
		else if((MRAAirlineBillingPersistenceConstants.MAILSUBCLASS_CP).equals(airlineCn66DetailsVo.getMailSubClass()))
			{
			airlineCn66DetailsVo.setWeightCP(rs.getDouble("TOTWGT"));
			}
		else if(rs.getString("SUBCLSGRP")!=null && rs.getString("SUBCLSGRP").startsWith("C")){
			  airlineCn66DetailsVo.setWeightCP(rs.getDouble("TOTWGT"));
		}
		else if(rs.getString("SUBCLSGRP")!=null && MRAAirlineBillingPersistenceConstants.MAILSUBCLASS_ULD
				.equals(airlineCn66DetailsVo.getMailSubClass())){
			  airlineCn66DetailsVo.setWeightULD(rs.getDouble("TOTWGT"));
		}
		
		else if(rs.getString("SUBCLSGRP")!=null && MRAAirlineBillingPersistenceConstants.MAILSUBCLASS_SV.
				equals(airlineCn66DetailsVo.getMailSubClass())){
			  airlineCn66DetailsVo.setWeightSV(rs.getDouble("TOTWGT"));
		}else if(MRAAirlineBillingPersistenceConstants.MAILSUBCLASS_EMS.equals(airlineCn66DetailsVo.getMailSubClass())){
			airlineCn66DetailsVo.setWeightEMS(rs.getDouble("TOTWGT"));
		}else{
			airlineCn66DetailsVo.setWeightLC(rs.getDouble("TOTWGT"));
		}
		
		
		airlineCn66DetailsVo.setOrigin(rs.getString("ORGCOD"));
		airlineCn66DetailsVo.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		airlineCn66DetailsVo.setReceptacleSerialNo(rs.getString("RSN"));
		airlineCn66DetailsVo.setSequenceNumber(rs.getInt("SEQNUM"));
		airlineCn66DetailsVo.setTotalPieces(rs.getInt("TOTPCS"));
		airlineCn66DetailsVo.setHni(rs.getString("HSN"));//Changed by A-5945 for ICRD-100243
		airlineCn66DetailsVo.setRegInd(rs.getString("REGIND"));
		airlineCn66DetailsVo.setRate(rs.getDouble("APLRAT"));
		airlineCn66DetailsVo.setYear(rs.getInt("YER"));
		airlineCn66DetailsVo.setFlightCarrierIdentifier(rs.getInt("FLTCARIDR"));
		airlineCn66DetailsVo.setCn51Status(rs.getString("C51STA"));
		if(rs.getTimestamp("LSTUPDTIM")!=null){
			 airlineCn66DetailsVo.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("LSTUPDTIM")));
	    }
		airlineCn66DetailsVo.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
		airlineCn66DetailsVo.setCurCod(rs.getString("LSTCURCOD")); //Modified by A-7929 as part of ICRD-265471
		//airlineCn66DetailsVo.setCsgdocnum(rs.getString("CSGDOCNUM"));  //cOMMENTED  as part of ICRD-265471
		//airlineCn66DetailsVo.setCsgseqnum(rs.getInt("CSGSEQNUM"));
		//airlineCn66DetailsVo.setPoaCode(rs.getString("POACOD"));
		//airlineCn66DetailsVo.setBillingBasis(rs.getString("BLGBAS"));
		//airlineCn66DetailsVo.setListingCurrencyCode(rs.getString("LSTCUR"));
		airlineCn66DetailsVo.setListingCurrencyCode(rs.getString("LSTCURCODSMY")); //Modified by A-7929 as part of ICRD-265471
		//Added as part of ICRD-265471
		airlineCn66DetailsVo.setMalSeqNum(rs.getLong("MALSEQNUM"));//Added as part of ICRD-265471
		String malIdr = rs.getString("MALIDR");
		airlineCn66DetailsVo.setDsnIdr(malIdr.substring(0,20));        
		try{
			
			airlineCn66DetailsVo.setTotalWeight(rs.getDouble("TOTWGT"));
			log.log(Log.INFO, "blg cur in mapper ", airlineCn66DetailsVo.getCurCod());
			Money totChg=CurrencyHelper.getMoney(airlineCn66DetailsVo.getCurCod());
			totChg.setAmount(rs.getDouble("BLDAMTLSTUR"));
			airlineCn66DetailsVo.setAmount(totChg);
			airlineCn66DetailsVo.setBldamt(totChg.getAmount());//for report
			
			}
			catch(CurrencyException currencyException){
				log.log(Log.INFO,"CurrencyException found");
			}
		log.exiting(CLASS_NAME, "map");
		return airlineCn66DetailsVo;
	}

}
