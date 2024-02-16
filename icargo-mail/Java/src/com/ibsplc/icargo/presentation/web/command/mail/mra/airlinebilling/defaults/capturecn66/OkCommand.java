/*
 * OkCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.BeanUtils;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CN66DetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 * 
 */ 
public class OkCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("AirlineBilling okCommand");

	private static final String CLASS_NAME = "OKCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";

	private static final String SCREEN_SUCCESS = "screenload_success";

	private static final String ACTION_FAILURE = "screenload_failure";

	private static final String SCREENSTATUS_NEXT = "next";

	private static final String SCREENSTATUS_NONEXT = "nonext";

	private static final String SCREENSTATUS_OK = "ok";

	private static final String SCREENSTATUS_ADD = "add";

	private static final String MAILSUBCLASS_CP = "CP";

	private static final String MAILSUBCLASS_LC = "LC";
	
	private static final String MAILSUBCLASS_SAL = "SAL";
	
	private static final String MAILSUBCLASS_ULD = "UL";
	
	private static final String MAILSUBCLASS_SV = "SV";

	private static final String MAILSUBCLASS_EMS = "EMS";////Added as part of ICRD-101397
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		CaptureCN66Session session = (CaptureCN66Session) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		CN66DetailsForm form = (CN66DetailsForm) invocationContext.screenModel;
		String carriageFrom = form.getCarriageFrom();
		String carriageTo = form.getCarriageTo();
		String[] category = form.getMailCategoryCode();
		String[] origin = form.getOrigin();
		String[] destination = form.getDestination();
		String[] flightNumber = form.getFlightNumber();
		String[] carrierCode = form.getCarrierCode();
		String[] despatchDate = form.getDespatchDate();
		String[] despatchNumber = form.getDespatchNumber();
		String[] receptacleSerialNo = form.getReceptacleSerialNo();
		String[] hni = form.getHni();
		String[] regInd = form.getRegInd();
		String[] totalCpWeights = form.getTotalCpWeight();
		String[] totalLcWeights = form.getTotalLcWeight();
		String[] totalEmsWeights=form.getTotalEmsWeight();	//Added as part of ICRD-101397
		String[] operationFlags = form.getOperationFlag();
		String[] sequenceNumber = form.getSequenceNumber();
		double totalCpWeight = 0.0;
		double totalLcWeight = 0.0;
		
		double totalSalWeight = 0.0;
		double totalUldWeight = 0.0;
		double totalSvWeight = 0.0;
		double totalEmsWeight = 0.0;//Added as part of ICRD-101397
		double amount = 0.0;
		String[] totalSalWeights=form.getTotalSalWeight();
    	String[] totalUldWeights=form.getTotalUldWeight();
    	String[] totalSvWeights=form.getTotalSvWeight();
    	String[] rate=form.getRate();
    	String[] amounts=form.getAmount();
    	
    	
		String[] dsnIdr = form.getDsnIdr();
		String[] malSeqNum =  form.getMalSeqNum();
		
		
		Collection<String> origins = Arrays.asList(form.getOrigin());
		Collection<String> destinations = Arrays.asList(form.getDestination());
		Collection<String> checkorigins = new ArrayList<String>();
		Collection<String> checkdests = new ArrayList<String>();
		ArrayList<AirlineCN66DetailsVO> newcn66s = new ArrayList<AirlineCN66DetailsVO>();
		ArrayList<AirlineCN66DetailsVO> presentcn66s = new ArrayList<AirlineCN66DetailsVO>();
		ArrayList<AirlineCN66DetailsVO> finalcn66s = null;
		ArrayList<AirlineCN66DetailsVO> oldcn66s = null;
		ArrayList<AirlineCN66DetailsVO> totalcn66s = new ArrayList<AirlineCN66DetailsVO>();
		HashMap<String, Collection<AirlineCN66DetailsVO>> finalcn66details = new HashMap<String, Collection<AirlineCN66DetailsVO>>();
		HashMap<String, Collection<AirlineCN66DetailsVO>> modifiedcn66map = new HashMap<String, Collection<AirlineCN66DetailsVO>>();
		ArrayList<AirlineCN66DetailsVO> modifiedArray = new ArrayList<AirlineCN66DetailsVO>();
	
		Page<AirlineCN66DetailsVO>airlineCN66DetailsVOs=null;
		
		log.log(Log.INFO, "AirlineCN66DetailsVOs....", session.getAirlineCN66DetailsVOs());
		if(session.getAirlineCN66DetailsVOs()==null){
			airlineCN66DetailsVOs = new Page<AirlineCN66DetailsVO>(
					new ArrayList<AirlineCN66DetailsVO>(), 0, 0, 0, 0, 0,false);
		}
		else{
			airlineCN66DetailsVOs=session.getAirlineCN66DetailsVOs();
		}
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> orgerrors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> desterrors = new ArrayList<ErrorVO>();
		String key = new StringBuilder().append(carriageFrom).append("-")
				.append(carriageTo).toString();
		String screenstatus = form.getScreenStatus();
		Map<String, AirlineValidationVO> airlinevalidationVOs = null;
		// Carriage from - validation against station
		if (carriageFrom != null && carriageFrom.trim().length() > 0) {
			try {
				new AreaDelegate().validateStation(getApplicationSession()
						.getLogonVO().getCompanyCode(), carriageFrom);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		// Carriage to - validation against station
		if (carriageTo != null && carriageTo.trim().length() > 0) {
			try {
				new AreaDelegate().validateStation(getApplicationSession()
						.getLogonVO().getCompanyCode(), carriageTo);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		int index = 0;
		for (String org : origins) {
			if (index != origins.size() - 1) {
				if (!checkorigins.contains(org)) {
					checkorigins.add(org);
				}
			}
			++index;
		}
		index = 0;
		for (String des : destinations) {
			if (index != origins.size() - 1) {
				if (!checkdests.contains(des)) {
					checkdests.add(des);
				}
			}
			++index;
		}
		// origin - validation against station
		try {
			new AreaDelegate().validateStationCodes(getApplicationSession()
					.getLogonVO().getCompanyCode(), checkorigins);
		} catch (BusinessDelegateException e) {
			orgerrors = handleDelegateException(e);
		}
		log.log(Log.INFO, "orgerrors.....", orgerrors);
		log.log(Log.INFO, "size.....", orgerrors.size());
		if (orgerrors != null && orgerrors.size() > 0) {
			StringBuilder codeArray = new StringBuilder();
			String errorString = "";
			for (ErrorVO error : orgerrors) {
				log.log(Log.INFO, "ErrorVO---->>>", error);
				if (("shared.station.invalidstation").equals
						(error.getErrorCode())) {
					Object[] codes = error.getErrorData();

					for (int count = 0; count < codes.length; count++) {
						if (("").equals(errorString)) {
							errorString = String.valueOf(codes[count]);
							codeArray.append(errorString);
						} else {
							errorString = codeArray.append(",").append(
									String.valueOf(codes[count])).toString();
						}
						log.log(Log.FINE, "\n\n\nValue-->", String.valueOf(codes[count]));
					}
					Object[] errorArray = { errorString };
					ErrorVO errorVO = new ErrorVO(
							"mra.airlinebilling.defaults.capturecn66.msg.err.invalidorigin",
							errorArray);
					errors.add(errorVO);
				}
			}
		}
		// destination - validation against station
		try {
			new AreaDelegate().validateStationCodes(getApplicationSession()
					.getLogonVO().getCompanyCode(), checkdests);
		} catch (BusinessDelegateException e) {
			desterrors = handleDelegateException(e);
		}
		if (desterrors != null && desterrors.size() > 0) {
			StringBuilder codeArray = new StringBuilder();
			String errorString = "";
			for (ErrorVO error : desterrors) {
				log.log(Log.INFO, "ErrorVO---->>>", error);
				if (("shared.station.invalidstation").equals
						(error.getErrorCode())) {
					Object[] codes = error.getErrorData();
					for (int count = 0; count < codes.length; count++) {
						if (("").equals(errorString)) {
							errorString = String.valueOf(codes[count]);
							codeArray.append(errorString);
						} else {
							errorString = codeArray.append(",").append(
									String.valueOf(codes[count])).toString();
						}
						log.log(Log.FINE, "\n\n\nValue-->", String.valueOf(codes[count]));
					}
					Object[] errorArray = { errorString };
					ErrorVO errorVO = new ErrorVO(
							"mra.airlinebilling.defaults.capturecn66.msg.err.invaliddest",
							errorArray);
					errors.add(errorVO);
				}
			}
		}
		
		
			Collection<String> carrierCodes =null;
			index = 0;
			for (String code : carrierCode) {
				if (index != carrierCode.length - 1) {
					
					if(carrierCodes==null){
						carrierCodes= new ArrayList<String>();
					}
					
					if (!carrierCodes.contains(code) && !"".equals(code)) {
						log.log(Log.INFO, "code.....", code);
						carrierCodes.add(code);
					}
				}
				++index;
			}
			log.log(Log.INFO, "carrierCodes.....", carrierCodes);
			log.log(Log.INFO, "size.....", carrierCodes.size());
		// carrier code - validation against airline
		if (carrierCodes != null && carrierCodes.size()> 0) {
			log
					.log(Log.INFO, "carrierCodes not null.....", carrierCodes.size());
			try {
				airlinevalidationVOs = new AirlineDelegate()
						.validateAlphaCodes(getApplicationSession()
								.getLogonVO().getCompanyCode(), carrierCodes);
				log.log(Log.INFO, "validation VOs", airlinevalidationVOs);
			} catch (BusinessDelegateException businessDelegateException) {
				/*errors
						.addAll(handleDelegateException(businessDelegateException));*/
			}
		}

		ArrayList<AirlineCN66DetailsVO> cn66detailsForAdding = new ArrayList<AirlineCN66DetailsVO>();
		ArrayList<AirlineCN66DetailsVO> cn66details =session.getCn66Details();
		log.log(Log.INFO, "airlineCN66DetailsVO---in ok....", cn66details);
		log.log(Log.FINEST, "OPERATION FLAGS LENGTH-->", operationFlags.length);
		index = 0;
		// populating VOs from form bean
		try{
		
		//for (String operationFlag : operationFlags) {
		if(cn66details!=null || cn66details.size()!=0){	
		for(AirlineCN66DetailsVO cn66DetailsVo:cn66details)	{
			if (index != operationFlags.length - 1) {
				log.log(Log.FINEST, "operationFlags---->", operationFlags,
						index);
				log.log(Log.FINEST, "ind/////ex---->", index);
				//log.log(Log.FINEST, "OPERATION FLAG-->" + operationFlag);
				//cn66DetailsVo = new AirlineCN66DetailsVO();
				/*if ("I".equals(operationFlags[index])
						|| "D".equals(operationFlags[index])) {
					cn66DetailsVo.setOperationFlag(operationFlags[index]);
				}*//* else if (!"NOOP".equals(operationFlags[index])
						&& !"N".equals(operationFlags[index])) {
					cn66DetailsVo.setOperationFlag("U");
				}*/
				if(!"D".equals(operationFlags[index])){
					
					
				cn66DetailsVo.setDsnIdr(dsnIdr[index]);
				cn66DetailsVo.setMalSeqNum(Long.parseLong(malSeqNum[index]));
					
				cn66DetailsVo.setCurCod(form.getBlgCurCode());
				cn66DetailsVo.setSequenceNumber(Integer
						.parseInt(sequenceNumber[index]));
				cn66DetailsVo.setCarriageFrom(form.getCarriageFrom());
				cn66DetailsVo.setCarriageTo(form.getCarriageTo());
				cn66DetailsVo.setMailCategoryCode(category[index]);
				cn66DetailsVo.setOrigin(origin[index]);
				cn66DetailsVo.setDestination(destination[index]);
				cn66DetailsVo.setFlightNumber(flightNumber[index]);
				cn66DetailsVo.setCarrierCode(carrierCode[index]);
				cn66DetailsVo.setFlightCarrierCode(carrierCode[index]);
				if((despatchDate[index]!=null)&& (despatchDate[index].length()>0)){
					log.log(Log.FINEST, "despatchDate----->");
				cn66DetailsVo.setDespatchDate(new LocalDate(
						LocalDate.NO_STATION, Location.NONE, false)
						.setDate(despatchDate[index]));
				cn66DetailsVo.setDespatchSerialNo(despatchNumber[index]);
				cn66DetailsVo.setReceptacleSerialNo(receptacleSerialNo[index]);
				cn66DetailsVo.setHni(hni[index]);
				cn66DetailsVo.setRegInd(regInd[index]);
				}
				if (totalCpWeights[index] != null
						&& totalCpWeights[index].length() > 0) {
					totalCpWeight = Double.parseDouble(totalCpWeights[index]);
					if (totalCpWeight != 0.0) {
					
						cn66DetailsVo.setTotalWeight(totalCpWeight);
						cn66DetailsVo.setMailSubClass(MAILSUBCLASS_CP);
					}
				}
				if (totalLcWeights[index] != null
						&& totalLcWeights[index].length() > 0) {
					totalLcWeight = Double.parseDouble(totalLcWeights[index]);
					if (totalLcWeight != 0.0) {
						
						cn66DetailsVo.setTotalWeight(totalLcWeight);
						cn66DetailsVo.setMailSubClass(MAILSUBCLASS_LC);
					}
				}
				/*if (totalSalWeights[index] != null
						&& totalSalWeights[index].length() > 0) {
					totalSalWeight = Double.parseDouble(totalSalWeights[index]);
					if (totalSalWeight != 0.0) {
						
						cn66DetailsVo.setTotalWeight(totalSalWeight);
						cn66DetailsVo.setMailSubClass(MAILSUBCLASS_SAL);
					}
				}
				if (totalUldWeights[index] != null
						&& totalUldWeights[index].length() > 0) {
					totalUldWeight = Double.parseDouble(totalUldWeights[index]);
					if (totalUldWeight != 0.0) {
						
						cn66DetailsVo.setTotalWeight(totalUldWeight);
						cn66DetailsVo.setMailSubClass(MAILSUBCLASS_ULD);
					}
				}*/
				if (totalSvWeights[index] != null
						&& totalSvWeights[index].length() > 0) {
					totalSvWeight = Double.parseDouble(totalSvWeights[index]);
					if (totalSvWeight != 0.0) {
						
						cn66DetailsVo.setTotalWeight(totalSvWeight);
						cn66DetailsVo.setMailSubClass(MAILSUBCLASS_SV);
					}
				}
				//Added as part of ICRD-101397
				if (totalEmsWeights[index] != null
						&& totalEmsWeights[index].length() > 0) {
					totalEmsWeight = Double.parseDouble(totalEmsWeights[index]);
					if (totalEmsWeight != 0.0) {
						cn66DetailsVo.setTotalWeight(totalEmsWeight);
						cn66DetailsVo.setMailSubClass(MAILSUBCLASS_EMS);
					}
				}
				double tot=totalCpWeight+totalSvWeight+totalUldWeight+totalSalWeight+totalLcWeight+totalEmsWeight;
				
			
				cn66DetailsVo.setRate(Double.parseDouble(rate[index]));
				if (amounts[index] != null
						&& amounts[index].length() > 0) {
					
					//amount = Double.parseDouble(amounts[index]);
					
					//Double weight=cn66DetailsVo.getTotalWeight();
					//Double rates=cn66DetailsVo.getRate();
					Double weight=tot;
					Double rates=Double.parseDouble(rate[index]);
					amount = weight*rates;
					
					
					if (amount != 0.0) {
						Money amt=CurrencyHelper.getMoney(form.getBlgCurCode());
						amt.setAmount(amount);
						cn66DetailsVo.setAmount(amt);
						
					}
				}
				
				
				log.log(Log.INFO, "amount....", amount);
				if((Double.parseDouble(rate[index])*tot)!=amount){
					
				 	ErrorVO err=new ErrorVO("mailtracking.mra.airlinebilling.defaults.msg.err.invalidamount");
	     			err.setErrorDisplayType(ErrorDisplayType.ERROR);
	     			errors.add(err);
				}
				
				if(airlinevalidationVOs!=null){
					if(airlinevalidationVOs.get(carrierCode[index])!=null){
				cn66DetailsVo.setFlightCarrierIdentifier(airlinevalidationVOs
						.get(carrierCode[index]).getAirlineIdentifier());
				
				}}
				log.log(Log.FINE, "VOS for ADDING===>>>", cn66DetailsVo);
				cn66detailsForAdding.add(cn66DetailsVo);
			}
			++index;
			
		}
		//}
		}
		}
		}
		catch(CurrencyException currencyException){
			log.log(Log.INFO,"CurrencyException found");
		}
		
		session.setCn66Details(cn66detailsForAdding);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = ACTION_FAILURE;
			log.exiting("MAILTRACKING_LIST", "OkCommand exit");
			return;
		}
		//Adding to Main Session
		presentcn66s = session.getCn66Details();
		log.log(Log.INFO, "present values", presentcn66s);
		if (screenstatus != null && screenstatus.trim().length() > 0) {
			if ((SCREENSTATUS_ADD).equals(screenstatus)) {
				log.log(Log.INFO, "inside add status");
				if (carriageFrom != null && carriageFrom.trim().length() > 0
						&& carriageTo != null && carriageTo.trim().length() > 0) {
					if (session.getCn66Details() != null
							&& session.getCn66Details().size() > 0) {
						if (session.getCn66DetailsMap() != null
								&& session.getCn66DetailsMap().size() > 0) {
							if (session.getCn66DetailsMap().containsKey(key)) {
								session.getCn66DetailsMap().get(key).addAll(
										presentcn66s);
							} else {
								log.log(Log.INFO, "inside added status",
										presentcn66s);
								session.getCn66DetailsMap().put(key,
										presentcn66s);
							}
						} else {
							HashMap<String, Collection<AirlineCN66DetailsVO>> adddedcn66details = new HashMap<String, Collection<AirlineCN66DetailsVO>>();
							adddedcn66details.put(key, presentcn66s);
							session.setCn66DetailsMap(adddedcn66details);
						}
					}
				}
				log.log(Log.INFO, "presentcn66s--in add....", presentcn66s);
				if(presentcn66s!=null && presentcn66s.size()>0){
				for(AirlineCN66DetailsVO airlineCN66Detailsvo:presentcn66s){
					airlineCN66DetailsVOs.add(airlineCN66Detailsvo);
				}
				}
			} else if ((SCREENSTATUS_NEXT).equals(screenstatus)
					|| (SCREENSTATUS_NONEXT).equals(screenstatus)) {
				
				String innerrow=form.getInnerRowSelected();
		    	String[] innerrows=new String[innerrow.length()];
		    	StringTokenizer innertok = new StringTokenizer(innerrow,",");
		    	int num=0;
		    	while(innertok.hasMoreTokens()){
		    		innerrows[num]=innertok.nextToken();
		    		num++;
		    	}
		    	int selectedRow =Integer.parseInt(innerrows[0]);
				log.log(Log.INFO, "inside next/nonext status");
				if (session.getCn66Details() != null && session.getCn66Details().size()>0) {
					modifiedArray = session.getCn66Details();
					/*modifiedcn66map.remove(key);
					modifiedcn66map.put(key, presentcn66s);*/
				}
				AirlineCN66DetailsVO modifiedDetailsVO=null;
				if(modifiedArray !=null){
					modifiedDetailsVO= modifiedArray.get(0);
				}
				if(session.getAirlineCN66DetailsVOs() !=null){
					AirlineCN66DetailsVO vo = session.getAirlineCN66DetailsVOs().get(selectedRow);
					try {
						BeanUtils.copyProperties(vo,modifiedDetailsVO);
					} catch (IllegalAccessException e) {
						log.log(Log.FINE,  "IllegalAccessException");
					} catch (InvocationTargetException e) {
						log.log(Log.FINE,  "InvocationTargetException");
					}

				}
				
				/*ArrayList<Collection<AirlineCN66DetailsVO>> vos = new ArrayList<Collection<AirlineCN66DetailsVO>>(
						modifiedcn66map.values());
				Collection<AirlineCN66DetailsVO> airlineCn66DetailsVO =new ArrayList<AirlineCN66DetailsVO>();
				Collection<AirlineCN66DetailsVO> detailVOsToBeRemoved=new ArrayList<AirlineCN66DetailsVO>();
				for(AirlineCN66DetailsVO airlineCN66DetailsVO : airlineCN66DetailsVOs){
					for(Collection<AirlineCN66DetailsVO> newCN66DetailsVOs : vos){
						for(AirlineCN66DetailsVO newCN66DetailsVO : newCN66DetailsVOs){
							log.log(Log.INFO, "newCN66DetailsVO===>>>"+newCN66DetailsVO);
							if(airlineCN66DetailsVO.getCompanyCode().equals(newCN66DetailsVO.getCompanyCode())
									&& airlineCN66DetailsVO.getAirlineIdentifier()==(newCN66DetailsVO.getAirlineIdentifier())
									&& airlineCN66DetailsVO.getInvoiceNumber().equals(newCN66DetailsVO.getInvoiceNumber())
									&& airlineCN66DetailsVO.getInterlineBillingType().equals(newCN66DetailsVO.getInterlineBillingType())
									&& airlineCN66DetailsVO.getSequenceNumber()==(newCN66DetailsVO.getSequenceNumber())
									&& airlineCN66DetailsVO.getClearancePeriod().equals(newCN66DetailsVO.getClearancePeriod())){
								detailVOsToBeRemoved.add(airlineCN66DetailsVO);
							}
						}
					}
				}
				
				airlineCN66DetailsVOs.removeAll(detailVOsToBeRemoved);*/
				/*if(modifiedArray!=null && modifiedArray.size()>0 ){
					airlineCN66DetailsVOs.addAll(modifiedArray);
				}*/
				
				/*log.log(Log.INFO, "presentcn66s--in modify...." + vos);
				int vosize = vos.size();
				for (int i = 0; i < vosize; i++) {
					newcn66s.addAll(vos.get(i));
				}
				if (session.getPreviousCn66Details() != null
						&& session.getPreviousCn66Details().size() > 0) {
					oldcn66s = new ArrayList<AirlineCN66DetailsVO>(session
							.getPreviousCn66Details());
					totalcn66s.addAll(oldcn66s);
				}
				totalcn66s.addAll(newcn66s);
				if (totalcn66s != null && totalcn66s.size() > 0) {
					int cn66size = totalcn66s.size();
					for (int i = 0; i < cn66size; i++) {
						String keyval = "";
						if (totalcn66s.get(i).getCarriageFrom() != null
								&& totalcn66s.get(i).getCarriageTo() != null) {
							keyval = (new StringBuilder().append(
									totalcn66s.get(i).getCarriageFrom())
									.append("-").append(totalcn66s.get(i)
									.getCarriageTo())).toString();
						}
						if (!(finalcn66details.containsKey(keyval))) {
							finalcn66s = new ArrayList<AirlineCN66DetailsVO>();
							finalcn66s.add(totalcn66s.get(i));
							finalcn66details.put(keyval, finalcn66s);
						} else {
							finalcn66details.get(keyval).add(totalcn66s.get(i));
						}
					}
				}*/
				session.setCn66DetailsMap(finalcn66details);
			}
		}
		
		
		session.setAirlineCN66DetailsVOs(airlineCN66DetailsVOs);
		log.log(Log.INFO, "airlineCN66DetailsVOs---in ok..end..",
				airlineCN66DetailsVOs);
		///session.removeCn66Details();
		form.setScreenStatus(SCREENSTATUS_OK);
		log.log(Log.INFO, "final cn66s", session.getCn66DetailsMap());
		invocationContext.target = SCREEN_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}
