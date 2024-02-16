/*
 * AddRowCommand.java Created on Nov-08-2008 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.s
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;

import java.util.ArrayList;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CN66DetailsForm;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  /* Revision History
 * -------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * Nov-7-2008 a-3434 Created
 */

public class AddRowCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Mailtracking MRA");

	private static final String CLASS_NAME = "ShowDsnPopUpCommand";

	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";
	
	private static final String ACTION_SUCCESS = "action_success";
	
	private static final String MAILSUBCLASS_CP = "CP";

	private static final String MAILSUBCLASS_LC = "LC";
	
	private static final String MAILSUBCLASS_SAL = "SAL";
	
	private static final String MAILSUBCLASS_ULD = "UL";
	
	private static final String MAILSUBCLASS_SV = "SV";
	private static final String MAILSUBCLASS_EMS = "EMS";//Added as part of ICRD-101397
	/**
	 * *Nov-08,a-3434
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * Execute Method for updating Session Before Adding row
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, MODULE_NAME);
		
		CaptureCN66Session session = (CaptureCN66Session) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		
		CN66DetailsForm cN66DetailsForm = (CN66DetailsForm) invocationContext.screenModel;
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		//Added for Unit Components weight,volume
		UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
		session.setWeightRoundingVO(unitRoundingVO);
		session.setVolumeRoundingVO(unitRoundingVO);		
		setUnitComponent(logonAttributes.getStationCode(),session);	
		ArrayList<AirlineCN66DetailsVO> airlineCN66DetailsVOs=session.getCn66Details();
		//ArrayList<AirlineCN66DetailsVO> airlineCN66DetailsVOsForadding=new ArrayList<AirlineCN66DetailsVO>();
		AirlineCN66DetailsVO airlineCN66DetailsVO=null;
		
		
		String dsnDate="";
		Double  weight=0.0;
		Double  rates=0.0;
		Integer count=cN66DetailsForm.getCount();
		log.log(Log.INFO, "count --in add-", count);
		if(count==0){
			 airlineCN66DetailsVO=new AirlineCN66DetailsVO();
			ArrayList<AirlineCN66DetailsVO> airlineCN66DetailsVOsForadding=new ArrayList<AirlineCN66DetailsVO>();
			airlineCN66DetailsVO.setOperationFlag("I");
			airlineCN66DetailsVOsForadding.add(airlineCN66DetailsVO);
			session.setCn66Details(airlineCN66DetailsVOsForadding);
		}
		else{
		 airlineCN66DetailsVO=airlineCN66DetailsVOs.get(airlineCN66DetailsVOs.size()-1);
			
		String[] dsnNum=cN66DetailsForm.getDespatchNumber();
		String dsnNumber= dsnNum[count-1];
		log.log(Log.FINEST, "despatchNumber--in add-", dsnNumber);
		log.log(Log.FINEST, "airlineCN66DetailsVOsForadding--in addrow-",
				airlineCN66DetailsVOs);
		String carriageFrom = cN66DetailsForm.getCarriageFrom();
		String carriageTo = cN66DetailsForm.getCarriageTo();
		String[] category = cN66DetailsForm.getMailCategoryCode();
		String[] origin = cN66DetailsForm.getOrigin();
		String[] destination = cN66DetailsForm.getDestination();
		String[] flightNumber = cN66DetailsForm.getFlightNumber();
		String[] carrierCode = cN66DetailsForm.getCarrierCode();
		String[] despatchDate = cN66DetailsForm.getDespatchDate();
		String[]totalCpWeights = cN66DetailsForm.getTotalCpWeight();
		String[] totalLcWeights = cN66DetailsForm.getTotalLcWeight();
		String[] totalSalWeights=cN66DetailsForm.getTotalSalWeight();
		String[] totalUldWeights=cN66DetailsForm.getTotalUldWeight();
		String[] totalSvWeights=cN66DetailsForm.getTotalSvWeight();
		String[] totalEmsWeights=cN66DetailsForm.getTotalEmsWeight();//Added as part of ICRD-101397
		String[] rate=cN66DetailsForm.getRate();
		String[] operationFlags = cN66DetailsForm.getOperationFlag();
		String[] receptacleSerialNo = cN66DetailsForm.getReceptacleSerialNo();
		String[] hni = cN66DetailsForm.getHni();
		String[]regInd = cN66DetailsForm.getRegInd();
		String[] sequenceNumber = cN66DetailsForm.getSequenceNumber();
		if(airlineCN66DetailsVOs!=null && airlineCN66DetailsVOs.size() > 0){
			
		
		log.log(Log.INFO, "latest vo in add---", airlineCN66DetailsVO);
		try{
		airlineCN66DetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
    	airlineCN66DetailsVO.setCarriageFrom(carriageFrom);
    	airlineCN66DetailsVO.setCarriageTo(carriageTo);
    	airlineCN66DetailsVO.setMailCategoryCode(category[count-1]);
    	airlineCN66DetailsVO.setOrigin(origin[count-1]);
    	airlineCN66DetailsVO.setDestination(destination[count-1]);
    	airlineCN66DetailsVO.setFlightNumber(flightNumber[count-1]);
    	airlineCN66DetailsVO.setCarrierCode(carrierCode[count-1]);
    	airlineCN66DetailsVO.setCurCod(cN66DetailsForm.getBlgCurCode());
    	airlineCN66DetailsVO.setSequenceNumber(Integer
				.parseInt(sequenceNumber[count-1]));
    	airlineCN66DetailsVO.setDespatchSerialNo( dsnNumber);
    	airlineCN66DetailsVO.setReceptacleSerialNo(receptacleSerialNo[count-1]);
    	airlineCN66DetailsVO.setHni(hni[count-1]);
    	airlineCN66DetailsVO.setRegInd(regInd[count-1]);
		
    	if((despatchDate[count-1]!=null)&& (despatchDate[count-1].length()>0)){
			log.log(Log.FINEST, "despatchDate--IN ADD-", despatchDate, count);
		airlineCN66DetailsVO.setDespatchDate(new LocalDate(
				LocalDate.NO_STATION, Location.NONE, false)
				.setDate(despatchDate[count-1]));
		airlineCN66DetailsVO.setOperationFlag(operationFlags[count-1]);
		log.log(Log.FINEST, "operationFlags in add---", operationFlags, count);
		}
    	
    	
    		if(totalCpWeights[count-1]!= null && totalCpWeights[count-1].length() > 0){
    			
    		Double totalCpWeight=Double.parseDouble(totalCpWeights[count-1]);
    		if(totalCpWeight!=0.0){	
    		airlineCN66DetailsVO.setTotalWeight(Double.parseDouble(totalCpWeights[count-1]));
    		airlineCN66DetailsVO.setMailSubClass(MAILSUBCLASS_CP);
    		
    		}
        	}
    		if(totalLcWeights[count-1]!= null && totalLcWeights[count-1].length() > 0){
    			Double totalLcWeight=Double.parseDouble(totalLcWeights[count-1]);
    			if(totalLcWeight!=0.0){
    			airlineCN66DetailsVO.setTotalWeight(totalLcWeight);
    			airlineCN66DetailsVO.setMailSubClass(MAILSUBCLASS_LC);
    			}
    			
    		}
    		/*if(totalSalWeights[count-1]!= null && totalSalWeights[count-1].length() > 0){
    			Double totalSalWeight=Double.parseDouble(totalSalWeights[count-1]);
    			if(totalSalWeight!=0.0){
    			airlineCN66DetailsVO.setTotalWeight(totalSalWeight);
    			airlineCN66DetailsVO.setMailSubClass(MAILSUBCLASS_SAL);
    			}
    		}if(totalUldWeights[count-1]!= null && totalUldWeights[count-1].length() > 0){
    			Double totalUldWeight=Double.parseDouble(totalUldWeights[count-1]);
    			if(totalUldWeight!=0.0){
    			airlineCN66DetailsVO.setTotalWeight(totalUldWeight);
    			airlineCN66DetailsVO.setMailSubClass(MAILSUBCLASS_ULD);
    			}
    		}*/
    		if(totalSvWeights[count-1]!= null && totalSvWeights[count-1].length() > 0){
    			Double totalSvWeight=Double.parseDouble(totalSvWeights[count-1]);
    			if(totalSvWeight!=0.0){
    			airlineCN66DetailsVO.setTotalWeight(totalSvWeight);
    			airlineCN66DetailsVO.setMailSubClass(MAILSUBCLASS_SV);
    			}
    		}
    		//Added as part of ICRD-101397
    		if(totalEmsWeights[count-1]!= null && totalEmsWeights[count-1].length() > 0){
    			Double totalEmsWeight=Double.parseDouble(totalEmsWeights[count-1]);
    			if(totalEmsWeight!=0.0){
    			airlineCN66DetailsVO.setTotalWeight(totalEmsWeight);
    			airlineCN66DetailsVO.setMailSubClass(MAILSUBCLASS_EMS);
    			}
    		}
    		
    		 weight=airlineCN66DetailsVO.getTotalWeight();
    		 
    		 log.log(Log.FINEST, "TotalWeight --in -add---", weight);
			log.log(Log.FINEST, "subclass --in -add---", airlineCN66DetailsVO.getMailSubClass());
			if(rate[count-1]!= null && rate[count-1].length() > 0){
    			
    		airlineCN66DetailsVO.setRate(Double.parseDouble(rate[count-1]));
    		}
    		rates=airlineCN66DetailsVO.getRate();
    		Double amount = weight*rates;
    		if (amount != 0.0) {
				Money amt=CurrencyHelper.getMoney(cN66DetailsForm.getBlgCurCode());
				amt.setAmount(amount);
				airlineCN66DetailsVO.setAmount(amt);
				
			}
    	
		}
    		catch(CurrencyException currencyException){
    			log.log(Log.INFO,"CurrencyException found");
    		}
    		
		}
		AirlineCN66DetailsVO airlineCN66DetailVO=new AirlineCN66DetailsVO();
		airlineCN66DetailVO.setOperationFlag("I");
		airlineCN66DetailsVOs.add(airlineCN66DetailVO);
		log.log(Log.FINEST, "airlineCN66DetailsVOsForadding -in -add---",
				airlineCN66DetailsVOs);
		session.setCn66Details(airlineCN66DetailsVOs);
		}
    	invocationContext.target = ACTION_SUCCESS;
}	
	//Added as part of ICRD-101112
	 private void setUnitComponent(String stationCode,
			 CaptureCN66Session session){
		UnitRoundingVO unitRoundingVO = null;
		try{
			log.log(Log.FINE, "station code is ----------->>", stationCode);
			unitRoundingVO=UnitFormatter.getUnitRoundingForUnitCode(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT);		
			log.log(Log.FINE, "unit vo for wt--in session---", unitRoundingVO);
			session.setWeightRoundingVO(unitRoundingVO);
			unitRoundingVO = UnitFormatter.getStationDefaultUnit(
					stationCode, UnitConstants.VOLUME);
			session.setVolumeRoundingVO(unitRoundingVO);
		   }catch(UnitException unitException) {
	//printStackTrrace()();
		   }
}	
}
		
