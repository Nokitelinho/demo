/*
 * UpdateConsignmentCommand.java Created on July 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.consignment;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.area.station.vo.StationFilterVO;
import com.ibsplc.icargo.business.shared.area.station.vo.StationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;

/**
 * @author A-5991
 *
 */
public class UpdateConsignmentCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.consignment";
   private static final String TARGET = "success";
   private static final String SYSPAR_DEFUNIT_WEIGHT = "mail.operations.defaultcaptureunit";
   private static final String STNPAR_DEFUNIT_WEIGHT = "station.defaults.unit.weight";
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */   
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    
    	log.entering("UpdateConsignmentCommand","execute");

    	ConsignmentForm consignmentForm =
    		(ConsignmentForm)invocationContext.screenModel;
    	ConsignmentSession consignmentSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	Page<StationVO> stationPage = null;
    	AreaDelegate delegate = new AreaDelegate();
    	StationFilterVO stationFilterVO = new StationFilterVO();
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		log.log(Log.FINE, "Logon attributes ", logonAttributes);
		//added by A_8353 for ICRD-274933 starts
		Map systemParameters = null;  
		SharedDefaultsDelegate sharedDelegate =new SharedDefaultsDelegate();  
		try {
			systemParameters=sharedDelegate.findSystemParameterByCodes(getSystemParameterCodes());
		} catch (BusinessDelegateException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		AreaDelegate areaDelegate = new AreaDelegate();
		Map stationParameters = null; 
	    	String stationCode = logonAttributes.getStationCode();
    	String companyCode=logonAttributes.getCompanyCode();
    	try {
			stationParameters = areaDelegate.findStationParametersByCode(companyCode, stationCode, getStationParameterCodes());
		} catch (BusinessDelegateException e1) {
			
			e1.getMessage();
		}
    	 if ((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT)!=null){
 			consignmentForm.setDefWeightUnit((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT));//added by A_8353 for ICRD-274933 
     		}
     		else{
     			consignmentForm.setDefWeightUnit((String)stationParameters.get(STNPAR_DEFUNIT_WEIGHT));
     		}
		//added by A_8353 for ICRD-274933 ends
		 stationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			stationFilterVO.setStationCode(logonAttributes.getStationCode().toUpperCase());
			int pageNumber = Integer.parseInt(consignmentForm.getDisplayPage());
	    	log.log(Log.FINE, "PageNumber to server ---> ", pageNumber);
	    	try {
	    		stationPage = delegate.findStations(stationFilterVO,pageNumber);
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			}
			
        ConsignmentDocumentVO consignmentDocumentVO = consignmentSession.getConsignmentDocumentVO();

        String conDocNo = consignmentForm.getConDocNo();
		String paCode = consignmentForm.getPaCode();
        String direction = consignmentForm.getDirection();
        String conDate = consignmentForm.getConDate();
        String type = consignmentForm.getType();
        //Added as part of CRQ-ICRD-103713 by A-5526
        String subType = consignmentForm.getSubType();
        String remarks = consignmentForm.getRemarks();
        if (conDocNo != null && conDocNo.trim().length() > 0) {
			consignmentDocumentVO.setConsignmentNumber(conDocNo.toUpperCase());
		}
        if (paCode != null && paCode.trim().length() > 0) {
			consignmentDocumentVO.setPaCode(paCode.toUpperCase());
		}
		if (direction != null && direction.trim().length() > 0) {
			consignmentDocumentVO.setOperation(direction);
		}
		LocalDate cd = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
		if (conDate != null && conDate.trim().length() > 0) {
			consignmentDocumentVO.setConsignmentDate(cd.setDate(conDate));
		}
		if (type != null && type.trim().length() > 0) {
			consignmentDocumentVO.setType(type);
		}
			consignmentDocumentVO.setSubType(subType);     
		//added by A-6344 for ICRD-90687 start
		if (remarks != null && remarks.trim().length() > 0) {
			consignmentDocumentVO.setRemarks(remarks);
		}else{
			consignmentDocumentVO.setRemarks("");
		}
		//added by A-6344 for ICRD-90687 end
		Page<MailInConsignmentVO> mailVOs =  consignmentDocumentVO.getMailInConsignmentVOs();
    	Collection<RoutingInConsignmentVO> routingVOs =  consignmentDocumentVO.getRoutingInConsignmentVOs();
    	/*
		 * For Mail Consignment Details
		 */
    	String[] originOE = consignmentForm.getOriginOE();
		String[] destinationOE = consignmentForm.getDestinationOE();
		String[] category = consignmentForm.getCategory();
		String[] mailClass = consignmentForm.getMailClass();
		String[] subClass = consignmentForm.getSubClass();
		String[] year = consignmentForm.getYear();
		String[] dsn = consignmentForm.getDsn();
		String[] rsn = consignmentForm.getRsn();
		String[] numBags = consignmentForm.getNumBags();
		String[] mailHI = consignmentForm.getMailHI();
	   	String[] mailRI = consignmentForm.getMailRI();
	   	String[] mailbagId = consignmentForm.getMailbagId();
		String[] weight = consignmentForm.getWeight();
		String[] weightUnit=consignmentForm.getWeightUnit(); //a-8353
		String[] uldNum = consignmentForm.getUldNum();
		String[] declaredValue = consignmentForm.getDeclaredValue();
		String[] currencyCode = consignmentForm.getCurrencyCode();
		log.log(Log.FINE, "Currency code ", currencyCode);
		
		/* 
		 * For Routing Details
		 */
		String[] flightCarrierCode = consignmentForm.getFlightCarrierCode();
		String[] flightNumber = consignmentForm.getFlightNumber();
		String[] depDate = consignmentForm.getDepDate();
		String[] pou = consignmentForm.getPou();
		String[] pol = consignmentForm.getPol();

		String[] routeOpFlag = consignmentForm.getRouteOpFlag();
		String[] mailOpFlag = consignmentForm.getMailOpFlag();

		int size = 0;
    	if(mailVOs != null && mailVOs.size() > 0){
			   size = mailVOs.size();
    	}
    	List<MailInConsignmentVO> newMailVOs = new ArrayList<MailInConsignmentVO>();
		for(int index=0; index<mailOpFlag.length;index++){
			if(index >= size){
				if(!"NOOP".equals(mailOpFlag[index])){
		    	MailInConsignmentVO newMailVO = new MailInConsignmentVO();
					newMailVO.setOperationFlag(mailOpFlag[index]);
	    			if(originOE != null) {
						if(originOE[index] != null && !("".equals(originOE[index]))) {
							newMailVO.setOriginExchangeOffice(originOE[index].toUpperCase());
						}
					}
					if(destinationOE != null) {
						if(destinationOE[index] != null && !("".equals(destinationOE[index]))) {
							newMailVO.setDestinationExchangeOffice(destinationOE[index].toUpperCase());
						}
					}

					if(category != null) {
						if(category[index] != null && !("".equals(category[index]))) {
							newMailVO.setMailCategoryCode(category[index]);
						}
					}
					if(mailClass != null) {
						if(mailClass[index] != null && !("".equals(mailClass[index]))) {
							newMailVO.setMailClass(mailClass[index].toUpperCase());
						}
					}
					if(subClass != null) {
						if(subClass[index] != null && !("".equals(subClass[index]))) {
							newMailVO.setMailSubclass(subClass[index].toUpperCase());
						}
					}
					if(year != null) {
						if(year[index] != null && !("".equals(year[index]))) {
							newMailVO.setYear(Integer.parseInt(year[index]));
						}
			        }
					if(dsn != null) {
						if(dsn[index] != null && !("".equals(dsn[index]))) {
							newMailVO.setDsn(dsn[index].toUpperCase());
						}
					}
					if(rsn != null) {
						if(rsn[index] != null && !("".equals(rsn[index]))) {
							newMailVO.setReceptacleSerialNumber(rsn[index].toUpperCase());
						}
					}

					if(numBags != null) {
						if(numBags[index] != null && !("".equals(numBags[index]))) {
							if(rsn[index] == null || "".equals(rsn[index])) {
								if(!"I".equals(newMailVO.getOperationFlag())){
									if(!numBags[index].equals(String.valueOf(newMailVO.getStatedBags()))){
										newMailVO.setOperationFlag("U");
									}
								}
							}
							newMailVO.setStatedBags(Integer.parseInt(numBags[index]));
						}
			        }
					if(mailHI != null) {
						if(mailHI[index] != null && !("".equals(mailHI[index]))) {
							newMailVO.setHighestNumberedReceptacle(mailHI[index]);
						}
					}
					if(mailRI != null) {
						if(mailRI[index] != null && !("".equals(mailRI[index]))) {
							newMailVO.setRegisteredOrInsuredIndicator(mailRI[index]);
						}
					}
					//Added for ICRD-205027 starts
					if(mailbagId != null) {
						if(mailbagId[index] != null && !("".equals(mailbagId[index]))) {
							newMailVO.setMailId(mailbagId[index]);
						}
					}
					//Added for ICRD-205027 ends
					if(weight != null) {
						if(weight[index] != null && !("".equals(weight[index]))) {
							if(rsn[index] != null && !("".equals(rsn[index]))) {

								String wt = weight[index];//String.valueOf(newMailVO.getStatedWeight());

								int len = wt.indexOf(".");
								String wgt = wt;
								if(len > 0 && len < 5){
									if("0".equals(wt.substring(len+1,wt.length()))){
									    wgt = wt.substring(0,len);
									}else{
										wgt = new StringBuilder(wt.substring(0,len)).append(wt.substring(len+1,wt.length())).toString();
									}
								}
								String stdwgt = wgt;
								if(wgt.length() == 3){
									stdwgt = new StringBuilder("0").append(wgt).toString();
								}
								if(wgt.length() == 2){
									stdwgt = new StringBuilder("00").append(wgt).toString();
								}
								if(wgt.length() == 1){
									stdwgt = new StringBuilder("000").append(wgt).toString();
								}
								//newMailVO.setStrWeight(stdwgt);
								newMailVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT, Double.parseDouble(stdwgt))); //Added by A-7550
							}else{
								if(!"I".equals(newMailVO.getOperationFlag())){
									if(!weight[index].equals(String.valueOf(newMailVO.getStatedWeight()))){
										newMailVO.setOperationFlag("U");
									}
								}
							}

							newMailVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,0.0,Double.parseDouble(weight[index]),weightUnit[index]));  //Added by A-8353  
						}
			        }
					if(uldNum != null) {
						if(uldNum[index] != null && !("".equals(uldNum[index]))) {
							if(!"I".equals(newMailVO.getOperationFlag())){
								if(!uldNum[index].equals(newMailVO.getUldNumber())){
									newMailVO.setOperationFlag("U");
								}
							}
							newMailVO.setUldNumber(uldNum[index].toUpperCase());
						}
					}
					
					
					if(declaredValue != null) {
						if(declaredValue[index] != null && !("".equals(declaredValue[index]))) {
							if(!"I".equals(newMailVO.getOperationFlag())){
								if(!declaredValue[index].equals(String.valueOf(newMailVO.getDeclaredValue()))){
									newMailVO.setOperationFlag("U");
								}
							}
							newMailVO.setDeclaredValue(Double.parseDouble(declaredValue[index]));
						}
					}
					
					stationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
					stationFilterVO.setStationCode(logonAttributes.getStationCode().toUpperCase());
					
			    	log.log(Log.FINE, "PageNumber to server ---> ", pageNumber);
			    	try {
			    		stationPage = delegate.findStations(stationFilterVO,pageNumber);
					} catch (BusinessDelegateException businessDelegateException) {
						handleDelegateException(businessDelegateException);
					}
					
					if(newMailVO.getDeclaredValue()>0)
					{
					if(currencyCode != null) {
						if(currencyCode[index] != null && !("".equals(currencyCode[index].trim()))) {
							if(!"I".equals(newMailVO.getOperationFlag())){
								if(!currencyCode[index].equals(newMailVO.getCurrencyCode())){
									newMailVO.setOperationFlag("U");
								}
							}
							newMailVO.setCurrencyCode(currencyCode[index].toUpperCase());
						}
						else if(currencyCode[index] == null || ("".equals(currencyCode[index].trim())) || currencyCode[index].length()==0)
						{if(stationPage!=null)
						{
							for(StationVO stationDetails:stationPage)
							{  newMailVO.setCurrencyCode(stationDetails.getCurrencyCode())	;
								log.log(Log.FINE, "PageNumber to server ---> ", stationDetails.getCurrencyCode());
							}
							}
								
						}
					}
					
					}
					

					
					
					

					newMailVOs.add(newMailVO);
				}
			}else{
				int count = 0;
				if(mailVOs != null && mailVOs.size() > 0){
				   for(MailInConsignmentVO newMailVO:mailVOs){
					   if(count == index){
						   if(!"NOOP".equals(mailOpFlag[index])){
							   if("N".equals(mailOpFlag[index])){
								   newMailVO.setOperationFlag(null);
							   }else{
								   newMailVO.setOperationFlag(mailOpFlag[index]);
							   }
							   if(originOE != null) {
									if(originOE[index] != null && !("".equals(originOE[index]))) {
										newMailVO.setOriginExchangeOffice(originOE[index].toUpperCase());
									}
								}
								if(destinationOE != null) {
									if(destinationOE[index] != null && !("".equals(destinationOE[index]))) {
										newMailVO.setDestinationExchangeOffice(destinationOE[index].toUpperCase());
									}
								}

								if(category != null) {
									if(category[index] != null && !("".equals(category[index]))) {
										newMailVO.setMailCategoryCode(category[index]);
									}
								}
								if(mailClass != null) {
									if(mailClass[index] != null && !("".equals(mailClass[index]))) {
										newMailVO.setMailClass(mailClass[index].toUpperCase());
									}
								}
								if(subClass != null) {
									if(subClass[index] != null && !("".equals(subClass[index]))) {
										newMailVO.setMailSubclass(subClass[index].toUpperCase());
									}
									else
									{
										newMailVO.setMailSubclass("");
									}

								}
								if(year != null) {
									if(year[index] != null && !("".equals(year[index]))) {
										newMailVO.setYear(Integer.parseInt(year[index]));
									}
						        }
								if(dsn != null) {
									if(dsn[index] != null && !("".equals(dsn[index]))) {
										newMailVO.setDsn(dsn[index].toUpperCase());
									}
								}
								if(rsn != null) {
									if(rsn[index] != null && !("".equals(rsn[index]))) {
										newMailVO.setReceptacleSerialNumber(rsn[index].toUpperCase());
									}
									else
									{
										newMailVO.setReceptacleSerialNumber("");
									}
								}

								if(numBags != null) {
									if(numBags[index] != null && !("".equals(numBags[index]))) {
										if(rsn[index] == null || "".equals(rsn[index])) {
											if(!"I".equals(newMailVO.getOperationFlag())){
												if(Integer.parseInt(numBags[index])!=(newMailVO.getStatedBags())){
													if(!"D".equals(newMailVO.getOperationFlag())){
														newMailVO.setOperationFlag("U");
													}
												}
											}
										}
										newMailVO.setStatedBags(Integer.parseInt(numBags[index]));
									}
						        }
								if(mailHI != null) {
									if(mailHI[index] != null && !("".equals(mailHI[index]))) {
										newMailVO.setHighestNumberedReceptacle(mailHI[index]);
									}
									else{
										newMailVO.setHighestNumberedReceptacle("");
									}
								}
								if(mailRI != null) {
									if(mailRI[index] != null && !("".equals(mailRI[index]))) {
										newMailVO.setRegisteredOrInsuredIndicator(mailRI[index]);
									}
									else{
										newMailVO.setRegisteredOrInsuredIndicator("");
									}

								}
								//Added as part of ICRD-205027 starts
								if(mailbagId != null) {
									if(mailbagId[index] != null && !("".equals(mailbagId[index]))) {
										newMailVO.setMailId(mailbagId[index]);
									}
									else{
										newMailVO.setMailId("");
									}

								}
								//Added as part of ICRD-205027 ends
								if(weight != null) {
									if(weight[index] != null && !("".equals(weight[index]))) {
										if(rsn[index] != null && !("".equals(rsn[index]))) {

											String wt = weight[index];//String.valueOf(newMailVO.getStatedWeight());

											int len = wt.indexOf(".");
											String wgt = wt;
											if(len > 0 && len < 5){
												if("0".equals(wt.substring(len+1,wt.length()))){
												    wgt = wt.substring(0,len);
												}else{
													wgt = new StringBuilder(wt.substring(0,len)).append(wt.substring(len+1,wt.length())).toString();
												}
											}
											String stdwgt = wgt;
											if(wgt.length() == 3){
												stdwgt = new StringBuilder("0").append(wgt).toString();
											}
											if(wgt.length() == 2){
												stdwgt = new StringBuilder("00").append(wgt).toString();
											}
											if(wgt.length() == 1){
												stdwgt = new StringBuilder("000").append(wgt).toString();
											}
											//newMailVO.setStrWeight(stdwgt);
											newMailVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT, Double.parseDouble(stdwgt))); //Added by A-7550
										}else{
											if(!"I".equals(newMailVO.getOperationFlag())){
												//if(Double.parseDouble(weight[index])!=(newMailVO.getStatedWeight())){
												if(Double.parseDouble(weight[index])!=(newMailVO.getStatedWeight().getDisplayValue())){//added by A-7371
													if(!"D".equals(newMailVO.getOperationFlag())){
														newMailVO.setOperationFlag("U");
													}
												}
											}
										}
										//newMailVO.setStatedWeight(Double.parseDouble(weight[index]));
										newMailVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,0.0,Double.parseDouble(weight[index]),weightUnit[index]));  //Added by A-8353;           
									}
						        }
								if(uldNum != null) {
									if(uldNum[index] != null && !("".equals(uldNum[index]))) {
										if(!"I".equals(newMailVO.getOperationFlag())){
											if(!uldNum[index].equals(newMailVO.getUldNumber())){
												if(!"D".equals(newMailVO.getOperationFlag())){
													newMailVO.setOperationFlag("U");
												}
											}
										}
										newMailVO.setUldNumber(uldNum[index].toUpperCase());
									}
									else
									{
										newMailVO.setUldNumber("");
									}

								}
								
								if(declaredValue != null) {
									if(declaredValue[index] != null && declaredValue[index].trim().length()!=0) {
										if(!"I".equals(newMailVO.getOperationFlag())){
											if(!declaredValue[index].equals(String.valueOf(newMailVO.getDeclaredValue()))){
												if(!"D".equals(newMailVO.getOperationFlag())){
													newMailVO.setOperationFlag("U");
												}
											}
										}
										newMailVO.setDeclaredValue(Double.parseDouble(declaredValue[index]));
									}
									else
									{ newMailVO.setOperationFlag("U");
										newMailVO.setDeclaredValue(0);
										
										
									}

								}
								if(newMailVO.getDeclaredValue()>0)
								{
								if(currencyCode != null) {
									if(currencyCode[index] != null && !("".equals(currencyCode[index].trim()))) {
										if(!"I".equals(newMailVO.getOperationFlag())){
											if(!currencyCode[index].equals(newMailVO.getCurrencyCode())){
												if(!"D".equals(newMailVO.getOperationFlag())){
													newMailVO.setOperationFlag("U");
												}
											}
										}
										newMailVO.setCurrencyCode(currencyCode[index].toUpperCase());
									}
									
									else if(declaredValue[index] != null && declaredValue[index].trim().length()!=0)
									{   
										if(stationPage!=null)
										{
											for(StationVO stationDetails:stationPage)
											{  newMailVO.setCurrencyCode(stationDetails.getCurrencyCode())	;
												log.log(Log.FINE, "PageNumber to server ---> ", stationDetails.getCurrencyCode());
											}
											}
										
										
										
									}
										
								}
								
										
									
										

								}
								else
								{
									newMailVO.setCurrencyCode("");	
								}
							   newMailVOs.add(newMailVO);
						   }
					   }
					   count++;
				   }
				}
			}
		}


			size = 0;
	    	if(routingVOs != null && routingVOs.size() > 0){
				   size = routingVOs.size();
	    	}
	    	Collection<RoutingInConsignmentVO> newRoutingVOs = new ArrayList<RoutingInConsignmentVO>();
			for(int index=0; index<routeOpFlag.length;index++){
				if(index >= size){
					if(!"NOOP".equals(routeOpFlag[index])){
				  RoutingInConsignmentVO newRoutingVO = new RoutingInConsignmentVO();
						newRoutingVO.setOperationFlag(routeOpFlag[index]);
						if(flightCarrierCode != null) {
							if(flightCarrierCode[index] != null && !("".equals(flightCarrierCode[index]))) {
								if(!"I".equals(newRoutingVO.getOperationFlag())){
									if(!flightCarrierCode[index].equals(newRoutingVO.getOnwardCarrierCode())){
										newRoutingVO.setOperationFlag("U");
									}
								}
								newRoutingVO.setOnwardCarrierCode(flightCarrierCode[index].toUpperCase());
							}
						}
						if(flightNumber != null) {
							if(flightNumber[index] != null && !("".equals(flightNumber[index]))) {
								if(!"I".equals(newRoutingVO.getOperationFlag())){
									if(!flightNumber[index].equals(newRoutingVO.getOnwardFlightNumber())){
										newRoutingVO.setOperationFlag("U");
									}
								}
								newRoutingVO.setOnwardFlightNumber(flightNumber[index].toUpperCase());
							}
						}
						if(pol != null) {
							if(pol[index] != null && !("".equals(pol[index]))) {
								if(!"I".equals(newRoutingVO.getOperationFlag())){
									if(!pol[index].equals(newRoutingVO.getPol())){
										newRoutingVO.setOperationFlag("U");
									}
								}
								newRoutingVO.setPol(pol[index].toUpperCase());
							}
						}
						if(pou != null) {
							if(pou[index] != null && !("".equals(pou[index]))) {
								if(!"I".equals(newRoutingVO.getOperationFlag())){
									if(!pou[index].equals(newRoutingVO.getPou())){
										newRoutingVO.setOperationFlag("U");
									}
								}
								newRoutingVO.setPou(pou[index].toUpperCase());
							}
						}

						if(depDate != null) {
							if(depDate[index] != null && !("".equals(depDate[index]))) {
								String fltdt = "";
								if(newRoutingVO.getOnwardFlightDate() != null){
								     fltdt = newRoutingVO.getOnwardFlightDate().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT);
								}
								if(!"I".equals(newRoutingVO.getOperationFlag())){
									if(!depDate[index].equals(fltdt)){
										newRoutingVO.setOperationFlag("U");
									}
								}
								LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
								newRoutingVO.setOnwardFlightDate(sd.setDate(depDate[index]));
							}
						}
//Added if check as part of CRQ ICRD-100406 by A-5526 
						if(newRoutingVO.getOnwardCarrierCode()!=null && !newRoutingVO.getOnwardCarrierCode().isEmpty() && newRoutingVO.getOnwardFlightNumber()!=null
								&& !newRoutingVO.getOnwardFlightNumber().isEmpty()){
						newRoutingVOs.add(newRoutingVO);
						}
					}
				}else{
					int count = 0;
					if(routingVOs != null && routingVOs.size() > 0) {
						  for(RoutingInConsignmentVO newRoutingVO:routingVOs) {
						   if(count == index){
							   if(!"NOOP".equals(routeOpFlag[index])){
								   if("N".equals(routeOpFlag[index])){
									   newRoutingVO.setOperationFlag(null);
								   }else{
									   newRoutingVO.setOperationFlag(routeOpFlag[index]);
								   }
								  if(flightCarrierCode != null) {
										if(flightCarrierCode[index] != null && !("".equals(flightCarrierCode[index]))) {
											if(!"I".equals(newRoutingVO.getOperationFlag())){
												if(!flightCarrierCode[index].equals(newRoutingVO.getOnwardCarrierCode())){
													if(!"D".equals(newRoutingVO.getOperationFlag())){
														newRoutingVO.setOperationFlag("U");
													}
												}
											}
											newRoutingVO.setOnwardCarrierCode(flightCarrierCode[index].toUpperCase());
										}
										else
										{
											newRoutingVO.setOnwardCarrierCode("");
										}
									}
									if(flightNumber != null) {
										if(flightNumber[index] != null && !("".equals(flightNumber[index]))) {
											if(!"I".equals(newRoutingVO.getOperationFlag())){
												if(!flightNumber[index].equals(newRoutingVO.getOnwardFlightNumber())){
													if(!"D".equals(newRoutingVO.getOperationFlag())){
														newRoutingVO.setOperationFlag("U");
													}
												}
											}
											newRoutingVO.setOnwardFlightNumber(flightNumber[index].toUpperCase());
										}
										else
										{
											newRoutingVO.setOnwardFlightNumber("");
										}
									}
									if(pol != null) {
										if(pol[index] != null && !("".equals(pol[index]))) {
											if(!"I".equals(newRoutingVO.getOperationFlag())){
												if(!pol[index].equals(newRoutingVO.getPol())){
													if(!"D".equals(newRoutingVO.getOperationFlag())){
														newRoutingVO.setOperationFlag("U");
													}
												}
											}
											newRoutingVO.setPol(pol[index].toUpperCase());
										}
										else
										{
											newRoutingVO.setPol("");
										}
									}
									if(pou != null) {
										if(pou[index] != null && !("".equals(pou[index]))) {
											if(!"I".equals(newRoutingVO.getOperationFlag())){
												if(!pou[index].equals(newRoutingVO.getPou())){
													if(!"D".equals(newRoutingVO.getOperationFlag())){
														newRoutingVO.setOperationFlag("U");
													}
												}
											}
											newRoutingVO.setPou(pou[index].toUpperCase());
										}
										else
										{
											newRoutingVO.setPou("");
										}
									}

									if(depDate != null) {
										if(depDate[index] != null && !("".equals(depDate[index]))) {
											if(!"I".equals(newRoutingVO.getOperationFlag())){
												if(!depDate[index].equals(String.valueOf(newRoutingVO.getOnwardFlightDate()))){
													if(!"D".equals(newRoutingVO.getOperationFlag())){
														newRoutingVO.setOperationFlag("U");
													}
												}
											}
											LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
											newRoutingVO.setOnwardFlightDate(sd.setDate(depDate[index]));
										}
									}
//Added if check as part of CRQ ICRD-100406 by A-5526 
									if(newRoutingVO.getOnwardCarrierCode()!=null && !newRoutingVO.getOnwardCarrierCode().isEmpty() && newRoutingVO.getOnwardFlightNumber()!=null
											&& !newRoutingVO.getOnwardFlightNumber().isEmpty()){
									newRoutingVOs.add(newRoutingVO);
									}
							   }
						   }
						   count++;
					   }
					}
				}
			}
			/*
			 * Added by RENO for BUG 36751 on 16-MAR-2009
			 */
			int _display_Page = 1;
			int _startIndex = 0;
			int _endIndex = 0;
			boolean _hasNext_Page = false;
			int _total_Rec_Count = 0;
	    	Page<MailInConsignmentVO> newMailPage = null;

			if(consignmentForm.getDisplayPage() != null && consignmentForm.getDisplayPage().trim().length() > 0) {
				try {
					_display_Page = Integer.parseInt(consignmentForm.getDisplayPage());
			    	_startIndex = ((_display_Page-1) * MailConstantsVO.MAX_PAGE_LIMIT)+1;
			    	_endIndex = _display_Page *  MailConstantsVO.MAX_PAGE_LIMIT;
				} catch (NumberFormatException e) {
	    			log.log(Log.FINE, "----NumberFormatException*--------",
							consignmentForm.getDisplayPage());
				}
			}
			/*
			 * Finding Number of New Records added to the New Page
			 * and at the same time the number of records that are removed
			 * from the existing Page
			 */
			for(int _flgCnt=0; _flgCnt < mailOpFlag.length; _flgCnt++) {
				if("I".equals(mailOpFlag[_flgCnt])) {
					_total_Rec_Count += 1;
				}
				if("D".equals(mailOpFlag[_flgCnt])) {
					_total_Rec_Count -= 1;
				}
			}
			if(mailVOs != null && mailVOs.size() > 0) {
				/*
				 * This case comes when the page is already existing, with some data
				 * which is not more that 25 records.
				 */
				_hasNext_Page = mailVOs.hasNextPage();
				_display_Page = mailVOs.getPageNumber();
				_startIndex = ((_display_Page-1) * MailConstantsVO.MAX_PAGE_LIMIT)+1;
				_endIndex = _display_Page *  MailConstantsVO.MAX_PAGE_LIMIT;
				if(!_hasNext_Page) {
					/*
					 * Adjusting the total record count with those already present
					 * and those added/removed.
					 */
					_total_Rec_Count += mailVOs.getTotalRecordCount();
				}
				newMailPage = new Page<MailInConsignmentVO>(
						newMailVOs,
						mailVOs.getPageNumber(),
						MailConstantsVO.MAX_PAGE_LIMIT,
						newMailVOs.size(),
						_startIndex,
						_endIndex,
						_hasNext_Page,
						_total_Rec_Count);
			}else {
				/*
				 * New page with the Current Page details
				 */
				newMailPage = new Page<MailInConsignmentVO>(
						newMailVOs,
						_display_Page,
						MailConstantsVO.MAX_PAGE_LIMIT,
						newMailVOs.size(),
						_startIndex,
						_endIndex,
						_hasNext_Page,
						_total_Rec_Count);
			}
			consignmentDocumentVO.setMailInConsignmentVOs(newMailPage);
			consignmentDocumentVO.setRoutingInConsignmentVOs(newRoutingVOs);
		int _mail_Count = 0;
		if(mailVOs != null && mailVOs.size() > 0) {
			_mail_Count = mailVOs.getTotalRecordCount();
		}
		int _total_Rec_Count_In_Lst_Page = _total_Rec_Count - _mail_Count;
	    consignmentSession.setTotalRecords(_total_Rec_Count_In_Lst_Page);
        consignmentSession.setConsignmentDocumentVO(consignmentDocumentVO);
       
        consignmentForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
        invocationContext.target = TARGET;

        log.exiting("UpdateConsignmentCommand","execute");

    }
    /**
	 * added by A-8353
	 * @return systemParameterCodes
	 */
	  private Collection<String> getSystemParameterCodes(){
		  Collection systemParameterCodes = new ArrayList();
		    systemParameterCodes.add("mail.operations.defaultcaptureunit");
		    return systemParameterCodes;
	  } 
	  /**
		 * added by A-8353
		 * @return stationParameterCodes
		 */
	  private Collection<String> getStationParameterCodes()
	  {
	    Collection stationParameterCodes = new ArrayList();
	    stationParameterCodes.add(STNPAR_DEFUNIT_WEIGHT);
	    return stationParameterCodes;
	  }
     

}
