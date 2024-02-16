/*
 * ScreenloadAcceptMailCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.LocationEnquiryFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.commodity.CommodityDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ScreenloadAcceptMailCommand extends BaseCommand  {
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	/**
	 * TARGET
	 */
	private static final String TARGET = "screenload_success";
	
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
	private static final String CONST_FLIGHT = "FLIGHT";
	private static final String CONTAINERTYPE = "mailtracking.defaults.containertype";
	private static final String ULD_TYPE = "U";	//Added for ICRD-128804
	private static final String SYSPAR_DEFUNIT_WEIGHT = "mail.operations.defaultcaptureunit";  
	 private static final String STNPAR_DEFUNIT_WEIGHT = "station.defaults.unit.weight";
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering("ScreenloadAcceptMailCommand","execute");
		
		MailAcceptanceForm mailAcceptanceForm = 
			(MailAcceptanceForm)invocationContext.screenModel;
		MailAcceptanceSession mailAcceptanceSession = 
			getScreenSession(MODULE_NAME,SCREEN_ID);
		mailAcceptanceForm.setOperationalStatus("");
		mailAcceptanceSession.setMessageStatus("");
		mailAcceptanceForm.setDisableFlag("");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;
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
		//added by A_8353 for ICRD-274933 ends
		
		//Added for Unit Component
		UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
		mailAcceptanceSession.setWeightRoundingVO(unitRoundingVO);
		mailAcceptanceSession.setVolumeRoundingVO(unitRoundingVO);		
		setUnitComponent(logonAttributes.getStationCode(),mailAcceptanceSession);	
		
		MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();
		Collection<ContainerDetailsVO> contDetailsVOs = mailAcceptanceVO.getContainerDetails();
		String[] selected = mailAcceptanceForm.getSelectMail();
		String selectedMails = selected[0];
		String[] primaryKey = selectedMails.split(",");
		int cnt=0;
		int count = 1;
		int primaryKeyLen = primaryKey.length;
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		
		//Added by paulson for saving Volume for mails and despatches
		String cmpcod = logonAttributes.getCompanyCode();
		String commodityCode = mailAcceptanceSession.getMailCommidityCode();
		Collection<String> commodites = new ArrayList<String>();
		if(commodityCode!=null && commodityCode.trim().length()>0) {
			commodites.add(commodityCode);
			Map<String,CommodityValidationVO> densityMap = null;
			CommodityDelegate  commodityDelegate = new CommodityDelegate();

			try {
				densityMap = commodityDelegate.validateCommodityCodes(cmpcod, commodites);
			} catch (BusinessDelegateException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}

			if(densityMap !=null && densityMap.size()>0){
				CommodityValidationVO commodityValidationVO = densityMap.get(commodityCode);
				log.log(Log.FINE, "DENSITY-----:", commodityValidationVO.getDensityFactor());
				mailAcceptanceForm.setDensity(String.valueOf(commodityValidationVO.getDensityFactor()));
			}
		}
		try{
			if (contDetailsVOs != null && contDetailsVOs.size() != 0) {
				for (ContainerDetailsVO contDetailsVO : contDetailsVOs) {
					ContainerDetailsVO newContDetailsVO = new ContainerDetailsVO();
					BeanHelper.copyProperties(newContDetailsVO,contDetailsVO);
					String primaryKeyFromVO = newContDetailsVO.getCompanyCode()
					+String.valueOf(count);
					if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
							equalsIgnoreCase(primaryKey[cnt].trim())) {
						if("Y".equals(newContDetailsVO.getAcceptedFlag())){
							newContDetailsVO.setOperationFlag("U");
						}else{
							newContDetailsVO.setOperationFlag("I");
						}
						Collection<MailbagVO> mailbagVOs = newContDetailsVO.getMailDetails();
						if (mailbagVOs != null && mailbagVOs.size() != 0) {
							for (MailbagVO mailbagVO: mailbagVOs) {
								//String mailId = mailbagVO.getMailbagId();Modified for ICRD-212903
								mailbagVO.setStrWeight(mailbagVO.getWeight());
								if(!"AA".equals(logonAttributes.getCompanyCode())){
									mailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT, mailbagVO.getWeight().getDisplayValue()*10));

								}
							}
						}
						containerDetailsVOs.add(newContDetailsVO);
						cnt++;
					}
					count++;
				}
			}
		}catch (SystemException e) {
			e.getMessage();
		}
		
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		log.log(Log.FINE, "*******containerDetailsVOs******",
				containerDetailsVOs.size());
		if(containerDetailsVOs == null || containerDetailsVOs.size() == 0){
			containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setContainerOperationFlag("N");
			containerDetailsVO.setContainerNumber("");
			containerDetailsVO.setContainerType("U");
			//Added for bug ICRD-97361 by A-5526 starts
			containerDetailsVO.setDestination(mailAcceptanceVO.getDestination());      
			//Added for bug ICRD-97361 by A-5526 ends
			containerDetailsVOs.add(containerDetailsVO);
			mailAcceptanceForm.setContainerNo("");
			mailAcceptanceForm.setDisableFlag("Y");
			mailAcceptanceForm.setBarrowCheck(false);//Added for ICRD-128804
			mailAcceptanceSession.setContainerDetailsVO(containerDetailsVO);
		}else{
			for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
				mailAcceptanceForm.setContainerNo(containerDtlsVO.getContainerNumber());
				//Added for ICRD-128804 starts
				if(ULD_TYPE.equalsIgnoreCase(containerDtlsVO.getContainerType())){
					mailAcceptanceForm.setBarrowCheck(false);
		       		}else{
		       		mailAcceptanceForm.setBarrowCheck(true);
		       	}
				//Added for ICRD-128804 ends
				mailAcceptanceSession.setContainerDetailsVO(containerDtlsVO);
				break;
			}
			
			/**
			 * @author A-3227
			 */
			ContainerDetailsVO contDetailsVO=((ArrayList<ContainerDetailsVO>)containerDetailsVOs).get(0);
			Collection<ContainerDetailsVO> contDetailVOs = new ArrayList<ContainerDetailsVO>();
			Collection<ContainerDetailsVO> newContainerDetailsVOs = null;
			int n=0;
			if(contDetailsVO.getMailDetails()==null	&& contDetailsVO.getDesptachDetailsVOs()== null){
				if(contDetailsVO.getOperationFlag()== null || ("U").equals(contDetailsVO.getOperationFlag())){
					if(contDetailsVO.getContainerNumber()!=null && contDetailsVO.getContainerNumber().trim().length()>0){
					contDetailVOs.add(contDetailsVO);
					}         
					try {
						newContainerDetailsVOs =new MailTrackingDefaultsDelegate().findMailbagsInContainer(contDetailVOs);
					} catch (BusinessDelegateException businessDelegateException){
						errors = handleDelegateException(businessDelegateException);
					}
					for( ContainerDetailsVO contnrDtlsVO :containerDetailsVOs){
						for(ContainerDetailsVO newcontrDtlVO :newContainerDetailsVOs ){
							if(contnrDtlsVO.getContainerNumber().equals(newcontrDtlVO.getContainerNumber())){
								contnrDtlsVO.setMailDetails(newcontrDtlVO.getMailDetails());
								contnrDtlsVO.setDesptachDetailsVOs(newcontrDtlVO.getDesptachDetailsVOs());
								
								Collection<MailbagVO> mailbagVOs = contnrDtlsVO.getMailDetails();
								
								if (mailbagVOs != null && mailbagVOs.size() != 0) {
									int mailCount=0;      
									StringBuilder validateDeleteFlag=new StringBuilder();
									for (MailbagVO mailbagVO: mailbagVOs) {
										//String mailId = mailbagVO.getMailbagId();Modified for ICRD-212903
										mailbagVO.setStrWeight(mailbagVO.getWeight());
										
										/*if(!"AA".equals(logonAttributes.getCompanyCode())){
								
											mailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT, mailbagVO.getWeight().getDisplayValue()*10));
										
										}commented by A-8353 for ICRD-274933*/
										// Added as part of CRQ ICRD-118163 by A-5526 starts
										String flagValue="";
										if(!"D".equals(mailbagVO.getOperationalFlag())){
										if(mailbagVO.getScannedPort()!=null && mailbagVO.getScannedPort().trim().length()>0){
										
											if(MailConstantsVO.MAIL_STATUS_RETURNED.equals(mailbagVO.getMailStatus())){
												flagValue=flagValue.concat(String.valueOf(mailCount)).concat("R");
												
											}
											
											else if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagVO.getMailStatus()) ||
													MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbagVO.getMailStatus()) ||
													MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())||
													!logonAttributes.getAirportCode().equals(mailbagVO.getScannedPort())){     
												flagValue=flagValue.concat(String.valueOf(mailCount)).concat("A");
												
												//break;
											}            
											else if(MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbagVO.getMailStatus())){
												flagValue=flagValue.concat(String.valueOf(mailCount)).concat("D");
												
											}
										else if("I".equals(mailbagVO.getOperationalStatus())){      
											flagValue=flagValue.concat(String.valueOf(mailCount)).concat("A");                   
											
											
												  
										}
										   
										else{
											flagValue=flagValue.concat(String.valueOf(mailCount)).concat("Y"); 
												
										}
										       
										}
										}
										// Added as part of CRQ ICRD-118163 by A-5526 ends
										
										/*
				    					 * Added By RENO K ABRAHAM : ANZ BUG : 37646
				    					 * As a part of performance Upgrade
				    					 * START.
				    					 */
				           		       	if(mailbagVO.getOperationalFlag() == null 
				           		       			|| !("I").equals(mailbagVO.getOperationalFlag())){
				           		       		mailbagVO.setDisplayLabel("Y");
				           		       	}      
				           		       	//END
				           		     validateDeleteFlag.append(flagValue);
				           		     mailCount++;
									}
									mailAcceptanceForm.setDeleteAgreeFlag(validateDeleteFlag.toString());         
								}
			        			Collection<DespatchDetailsVO> despatchDetailsVOs = contnrDtlsVO.getDesptachDetailsVOs();
			        			if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0) {
				       		       	for (DespatchDetailsVO despatchDetailsVO: despatchDetailsVOs) {	       		       		
				       		       		/*
				    					 * Added By RENO K ABRAHAM : ANZ BUG : 37646
				    					 * As a part of performance Upgrade
				    					 * START.
				    					 */
				           		       	if(despatchDetailsVO.getOperationalFlag() == null 
				           		       			|| !("I").equals(despatchDetailsVO.getOperationalFlag())){
				           		       		despatchDetailsVO.setDisplayLabel("Y");
				           		       	}      
				           		       	//END
				       		       	}        				
			        			}
								mailAcceptanceSession.setContainerDetailsVO(contnrDtlsVO);
								n++;
								break;
							}
						}
						if(n!=0){
							break;
						}
					}
				}
			}
			else if(contDetailsVO.getMailDetails()!=null && contDetailsVO.getMailDetails().size()>0){
				
				Collection<MailbagVO> mailbagVOs = contDetailsVO.getMailDetails();
				int mailCount=0;                 
				StringBuilder validateDeleteFlag=new StringBuilder();
				for (MailbagVO mailbagVO: mailbagVOs) {
					
					String flagValue="";
					if(!"D".equals(mailbagVO.getOperationalFlag())){
					if(mailbagVO.getScannedPort()!=null && mailbagVO.getScannedPort().trim().length()>0){
						
						if(MailConstantsVO.MAIL_STATUS_RETURNED.equals(mailbagVO.getMailStatus())){
							flagValue=flagValue.concat(String.valueOf(mailCount)).concat("R");
							
						}
						
						else if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagVO.getMailStatus()) ||
								MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbagVO.getMailStatus()) ||
								MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())||
								!logonAttributes.getAirportCode().equals(mailbagVO.getScannedPort())){     
							flagValue=flagValue.concat(String.valueOf(mailCount)).concat("A");      
							
							//break;
						}            
						else if(MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbagVO.getMailStatus())){
							flagValue=flagValue.concat(String.valueOf(mailCount)).concat("D");
							
						}  
					else if("I".equals(mailbagVO.getOperationalStatus())){      
						flagValue=flagValue.concat(String.valueOf(mailCount)).concat("A");                   
						
						
							  
					}
					
					else{
						flagValue=flagValue.concat(String.valueOf(mailCount)).concat("Y"); 
							
					}
					       
					}
					validateDeleteFlag.append(flagValue);
          		     mailCount++;
					}
				}
				mailAcceptanceForm.setDeleteAgreeFlag(validateDeleteFlag.toString());  
				
			}
		}
		
		mailAcceptanceSession.setContainerDetailsVOs(containerDetailsVOs);
		
		
		if(mailAcceptanceVO.isPreassignNeeded()){
			mailAcceptanceForm.setPreassignFlag("Y");
		}else{
			mailAcceptanceForm.setPreassignFlag("N");
		}
		/*
		 * Getting OneTime values
		 */
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		if(oneTimes!=null){
			List<String> sortedOnetimes ;
			Collection<OneTimeVO> catVOs = oneTimes.get("mailtracking.defaults.mailcategory");
			Collection<OneTimeVO> hniVOs = oneTimes.get("mailtracking.defaults.highestnumbermail");
			Collection<OneTimeVO> rsnVOs = oneTimes.get("mailtracking.defaults.registeredorinsuredcode");
			Collection<OneTimeVO> mailClassVOs = oneTimes.get("mailtracking.defaults.mailclass");
			Collection<OneTimeVO> damageCodes = oneTimes.get("mailtracking.defaults.return.reasoncode");
			Collection<OneTimeVO> containerTypeVOs = oneTimes.get(CONTAINERTYPE);
			Collection<OneTimeVO> companyCodesVOs = oneTimes.get("mailtracking.defaults.companycode");
			log.log(Log.FINE, "*******Getting OneTimeVOs***hniVOs***",
					mailClassVOs.size());
			if(hniVOs!=null && !hniVOs.isEmpty()){
				sortedOnetimes= new ArrayList<String>();
				for(OneTimeVO hniVo: hniVOs){
					sortedOnetimes.add(hniVo.getFieldValue());
				}
				Collections.sort(sortedOnetimes);
			
			
			int i=0;
			for(OneTimeVO hniVo: hniVOs){
				hniVo.setFieldValue(sortedOnetimes.get(i++));
			}
			}
			if(rsnVOs!=null && !rsnVOs.isEmpty()){
				sortedOnetimes= new ArrayList<String>();
				for(OneTimeVO riVo: rsnVOs){
					sortedOnetimes.add(riVo.getFieldValue());
				}
				Collections.sort(sortedOnetimes);    
			
			
			int i=0;
			for(OneTimeVO riVo: rsnVOs){
				riVo.setFieldValue(sortedOnetimes.get(i++));
			}
			}
			mailAcceptanceSession.setOneTimeCat(catVOs);
			mailAcceptanceSession.setOneTimeRSN(rsnVOs);
			mailAcceptanceSession.setOneTimeHNI(hniVOs);
			mailAcceptanceSession.setOneTimeMailClass(mailClassVOs);
			mailAcceptanceSession.setOneTimeDamageCodes(damageCodes);
			mailAcceptanceSession.setOneTimeContainerType(containerTypeVOs);
			mailAcceptanceSession.setOneTimeCompanyCode(companyCodesVOs);
		}	
		
//		To set POU
		FlightValidationVO flightValidationVO = mailAcceptanceSession.getFlightValidationVO();
		if(flightValidationVO != null) {
		String route = flightValidationVO.getFlightRoute();
		if(route != null && !"".equals(route)){
			String[] routeArr = route.split("-");
			int flag = 0;
			Collection<String> pous = new ArrayList<String>();
			for(int i=0;i<routeArr.length;i++){
				if(flag == 1){
					pous.add(routeArr[i]);
				}
				if(routeArr[i].equals(logonAttributes.getAirportCode())){
					flag = 1;
				}
			}
			pous.remove(logonAttributes.getAirportCode());
			mailAcceptanceSession.setPous(pous);
			}
		}else{
			mailAcceptanceSession.setPous(null);
			mailAcceptanceForm.setDestn(mailAcceptanceVO.getDestination());
		}
		
		Collection<WarehouseVO> warehouseVOs =  findWarehouses(logonAttributes);
		String warehouseCode = "";
		if(warehouseVOs != null && warehouseVOs.size() > 0){
			for(WarehouseVO warehouseVO:warehouseVOs){
				warehouseCode = warehouseVO.getWarehouseCode();
				break;
			}
		}
		Collection<String> transactionCodes = new ArrayList<String>();
		transactionCodes.add("warehouse.defaults.defaultmaillocation");
		Map<String,Collection<String>> locationsMap = null;
		
		LocationEnquiryFilterVO filterVO=new LocationEnquiryFilterVO();
		filterVO.setCompanyCode(logonAttributes.getCompanyCode());
		filterVO.setAirportCode(logonAttributes.getAirportCode());
		filterVO.setWarehouseCode(warehouseCode);
		filterVO.setTransactionCodes(transactionCodes);
		try{
			locationsMap = new MailTrackingDefaultsDelegate().findWarehouseTransactionLocations(filterVO);
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		String warehouseLocation = "";
		for(String key:locationsMap.keySet()){
			Collection<String> locations = locationsMap.get(key);
			if(locations != null && locations.size() > 0){
				warehouseLocation = ((ArrayList<String>)locations).get(0);
			}
			break;
		}
		mailAcceptanceSession.setWarehouse(warehouseVOs);
		mailAcceptanceForm.setLocation(warehouseLocation);
		
		mailAcceptanceForm.setHiddenScanDate((new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true)).toDisplayDateOnlyFormat());
		mailAcceptanceForm.setHiddenScanTime(
				((new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true))
						.toDisplayTimeOnlyFormat()).substring(0,5));
		
		if ((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT)!=null){
    		mailAcceptanceForm.setDefWeightUnit((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT));//added by A_8353 for ICRD-274933 
    		}
    		else{
    			mailAcceptanceForm.setDefWeightUnit((String)stationParameters.get(STNPAR_DEFUNIT_WEIGHT));
    		}//added by A_8353 for ICRD-274933
		log.log(Log.FINE, "*******Getting locationsMap***", locationsMap);
		invocationContext.target = TARGET;
		log.exiting("ScreenloadAcceptMailCommand","execute");
		
	}
	
	
	/**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add("mailtracking.defaults.registeredorinsuredcode");
			fieldValues.add("mailtracking.defaults.mailcategory");
			fieldValues.add("mailtracking.defaults.highestnumbermail");
			fieldValues.add("mailtracking.defaults.mailclass");
			fieldValues.add("mailtracking.defaults.return.reasoncode");
			fieldValues.add("mailtracking.defaults.companycode");
			
			fieldValues.add(CONTAINERTYPE);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}
	
	/**
	 * This method will be invoked at the time of screen load
	 * @param logonAttributes
	 * @return Collection<WarehouseVO>
	 */
	public Collection<WarehouseVO> findWarehouses(LogonAttributes logonAttributes) {
		Collection<WarehouseVO> warehouseVOs = null;
		Collection<ErrorVO> errors = null;
		try{
			warehouseVOs = new MailTrackingDefaultsDelegate().findAllWarehouses(
					logonAttributes.getCompanyCode(),logonAttributes.getAirportCode());
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return warehouseVOs;
	}
	
	
	/**
	 * A-3251
	 * @param stationCode
	 * @param mailAcceptanceSession
	 * @return 
	 */
	private void setUnitComponent(String stationCode,
			MailAcceptanceSession mailAcceptanceSession){
		UnitRoundingVO unitRoundingVO = null;
		try{
			log.log(Log.FINE, "station code is ----------->>", stationCode);
			unitRoundingVO = UnitFormatter.getStationDefaultUnit(stationCode, UnitConstants.MAIL_WGT);
			log.log(Log.FINE, "unit vo for wt--in session---", unitRoundingVO);
			mailAcceptanceSession.setWeightRoundingVO(unitRoundingVO);
			unitRoundingVO = UnitFormatter.getStationDefaultUnit(
					stationCode, UnitConstants.VOLUME);
			mailAcceptanceSession.setVolumeRoundingVO(unitRoundingVO);
			
		   }catch(UnitException unitException) {
				unitException.getErrorCode();
		   }
		
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
