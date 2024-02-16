/*
 * ListAcceptMailCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
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
public class ListAcceptMailCommand extends BaseCommand {
	
	@Override
	public boolean breakOnInvocationFailure() {		
		return true;
	}
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	/**
	 * TARGET
	 */
	private static final String TARGET = "success";
	
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";
	private static final String SYSPAR_DEFUNIT_WEIGHT = "mail.operations.defaultcaptureunit";  
	private static final String ULD_TYPE = "U";	
			private static final String STNPAR_DEFUNIT_WEIGHT = "station.defaults.unit.weight";
//Added by A-7540
	private static final String ULD_AS_BARROW = "mail.operations.allowuldasbarrow";
	private static final String FLAG_YES = "Y";
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering("ListAcceptMailCommand","execute");
		
		MailAcceptanceForm mailAcceptanceForm = 
			(MailAcceptanceForm)invocationContext.screenModel;
		MailAcceptanceSession mailAcceptanceSession = 
			getScreenSession(MODULE_NAME,SCREEN_ID);
		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
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
		    	if ((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT)!=null){
					mailAcceptanceForm.setDefWeightUnit((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT));//added by A_8353 for ICRD-274933 
					}
					else{
						mailAcceptanceForm.setDefWeightUnit((String)stationParameters.get(STNPAR_DEFUNIT_WEIGHT));
					}//added by A_8353 for ICRD-274933 
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();
		// Putting selected ContainerNo Details VO into session...
		String containerNum = mailAcceptanceForm.getContainerNo();
		String containerType = mailAcceptanceForm.getContainerType();
		if(containerNum == null || ("".equals(containerNum.trim()))){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.containernum.empty"));
			
			mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);			
			ContainerDetailsVO containerDtlsVO = mailAcceptanceSession.getContainerDetailsVO();
			containerDtlsVO.setContainerType(mailAcceptanceForm.getContainerType());
			containerDtlsVO.setPou(mailAcceptanceForm.getPou());
			containerDtlsVO.setContainerNumber(mailAcceptanceForm.getContainerNo());
			containerDtlsVO.setDestination(mailAcceptanceForm.getDestn());
			containerDtlsVO.setPaBuiltFlag(mailAcceptanceForm.getPaBuilt());
			containerDtlsVO.setContainerJnyId(mailAcceptanceForm.getContainerJnyId());
			containerDtlsVO.setPaCode(mailAcceptanceForm.getPaCode());
			mailAcceptanceSession.setContainerDetailsVO(containerDtlsVO);
			
			invocationContext.target = TARGET;
			return;
		}else if("FLIGHT".equals(mailAcceptanceForm.getAssignToFlight()) 
					&& (mailAcceptanceForm.getPou() == null || mailAcceptanceForm.getPou().trim().length() == 0)){ 
			invocationContext.addError(new ErrorVO("mailtracking.defaults.pou.empty"));
			
			mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);			
			ContainerDetailsVO containerDtlsVO = mailAcceptanceSession.getContainerDetailsVO();
			containerDtlsVO.setContainerType(mailAcceptanceForm.getContainerType());
			containerDtlsVO.setContainerNumber(mailAcceptanceForm.getContainerNo());
			containerDtlsVO.setDestination(mailAcceptanceForm.getDestn());
			containerDtlsVO.setPaBuiltFlag(mailAcceptanceForm.getPaBuilt());
			containerDtlsVO.setContainerJnyId(mailAcceptanceForm.getContainerJnyId());
			containerDtlsVO.setPaCode(mailAcceptanceForm.getPaCode());
			containerDtlsVO.setPou(mailAcceptanceForm.getPou());
			mailAcceptanceSession.setContainerDetailsVO(containerDtlsVO);
			invocationContext.target = TARGET;
			return;
		}else if("U".equals(containerType) && (mailAcceptanceForm.getDestn()==null 
					|| mailAcceptanceForm.getDestn().trim().length()==0)){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.destn.empty"));
			mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);			
			ContainerDetailsVO containerDtlsVO = mailAcceptanceSession.getContainerDetailsVO();
			containerDtlsVO.setContainerType(mailAcceptanceForm.getContainerType());
			containerDtlsVO.setContainerNumber(mailAcceptanceForm.getContainerNo());
			containerDtlsVO.setPou(mailAcceptanceForm.getPou());
			containerDtlsVO.setPaBuiltFlag(mailAcceptanceForm.getPaBuilt());
			containerDtlsVO.setContainerJnyId(mailAcceptanceForm.getContainerJnyId());
			containerDtlsVO.setPaCode(mailAcceptanceForm.getPaCode());
			containerDtlsVO.setDestination(mailAcceptanceForm.getDestn());
			mailAcceptanceSession.setContainerDetailsVO(containerDtlsVO);			
			invocationContext.target = TARGET;
			return;

		}else if(("FLIGHT".equals(mailAcceptanceForm.getAssignToFlight())
				&& ("B".equals(mailAcceptanceForm.getContainerType()))
				&& !(mailAcceptanceForm.getPou().equals(mailAcceptanceForm.getDestn())))){				
					invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.destnandpouisnotsame")); 

					mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);			
					ContainerDetailsVO containerDtlsVO = mailAcceptanceSession.getContainerDetailsVO();
					containerDtlsVO.setContainerType(mailAcceptanceForm.getContainerType());
					containerDtlsVO.setContainerNumber(mailAcceptanceForm.getContainerNo());
					containerDtlsVO.setPou(mailAcceptanceForm.getPou());
					containerDtlsVO.setDestination(mailAcceptanceForm.getDestn());
					mailAcceptanceSession.setContainerDetailsVO(containerDtlsVO);

					invocationContext.target = TARGET;
					return;				
		}else if(mailAcceptanceForm.getDestn().equals(logonAttributes.getAirportCode()))
		{
			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.destn.currentairport"));
			mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);			
			ContainerDetailsVO containerDtlsVO = mailAcceptanceSession.getContainerDetailsVO();
			containerDtlsVO.setContainerType(mailAcceptanceForm.getContainerType());
			containerDtlsVO.setContainerNumber(mailAcceptanceForm.getContainerNo());
			containerDtlsVO.setPaBuiltFlag(mailAcceptanceForm.getPaBuilt());
			containerDtlsVO.setContainerJnyId(mailAcceptanceForm.getContainerJnyId());
			containerDtlsVO.setPaCode(mailAcceptanceForm.getPaCode());
			containerDtlsVO.setPou(mailAcceptanceForm.getPou());
			containerDtlsVO.setDestination(mailAcceptanceForm.getDestn());
			mailAcceptanceSession.setContainerDetailsVO(containerDtlsVO);				
			invocationContext.target = TARGET;
			return;
		}else {
			// Validate Destination
	    	//AreaDelegate areaDelegate = new AreaDelegate();
	    	AirportValidationVO airportValidationVO = null;
	    	String destination = mailAcceptanceForm.getDestn();  
	    	int errorFlag=0;
	    	if (destination != null && !"".equals(destination)) {        		
	    		try {        			
	    			airportValidationVO = areaDelegate.validateAirportCode(
	    					logonAttributes.getCompanyCode(),destination.toUpperCase());
	    		}catch (BusinessDelegateException businessDelegateException) {
	    			errorFlag=1;
	    			errors = handleDelegateException(businessDelegateException);
	    		}
	    		if (errors != null && errors.size() > 0
	    				&& errorFlag==1) {            			
	    			Object[] obj = {destination.toUpperCase()};
					invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidairport",obj));
					mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);			
					ContainerDetailsVO containerDtlsVO = mailAcceptanceSession.getContainerDetailsVO();
					containerDtlsVO.setContainerType(mailAcceptanceForm.getContainerType());
					containerDtlsVO.setContainerNumber(mailAcceptanceForm.getContainerNo());
					containerDtlsVO.setPou(mailAcceptanceForm.getPou());
					containerDtlsVO.setDestination(mailAcceptanceForm.getDestn());
					containerDtlsVO.setPaBuiltFlag(mailAcceptanceForm.getPaBuilt());
					containerDtlsVO.setContainerJnyId(mailAcceptanceForm.getContainerJnyId());
					containerDtlsVO.setPaCode(mailAcceptanceForm.getPaCode());
					mailAcceptanceSession.setContainerDetailsVO(containerDtlsVO);				
					invocationContext.target = TARGET;
					return;
	    		}
	    	}
			// Putting selected ContainerNo Details VO into session...
			int flag = 0;
			containerNum = mailAcceptanceForm.getContainerNo().trim().toUpperCase();
			
			
			
			/* no such validation needed */
//			else {
//			//Validating the container number against the ULD Type
//			if(containerNum.length()>= 3) {
//			String containerPart = containerNum.substring(0,3);
//			log.log(Log.FINE,"$$$$$$containerPart------->>"+containerPart);
//			Collection<String> containerParts = new ArrayList<String>();
//			containerParts.add(containerPart);
//			Map<String, ULDTypeValidationVO> uldTypeValidationVos = null;
//			try {
//			uldTypeValidationVos = uldDelegate.validateULDTypeCodes(
//			logonAttributes.getCompanyCode(),containerParts);
//			}catch (BusinessDelegateException businessDelegateException) {
//			errors = handleDelegateException(businessDelegateException);
//			}
//			log.log(Log.FINE,"uldTypeValidationVos------->>"+uldTypeValidationVos);
//			if (uldTypeValidationVos != null &&
//			uldTypeValidationVos.get(containerPart) != null) {      
//			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.invalidcontainernoforbulk",
//			new Object[]{containerNum}));
//			invocationContext.target = TARGET;
//			ContainerDetailsVO containerDtlsVO = mailAcceptanceSession.getContainerDetailsVO();
//			containerDtlsVO.setContainerType(mailAcceptanceForm.getContainerType());
//			mailAcceptanceSession.setContainerDetailsVO(containerDtlsVO);
//			return;
//			}
//			}
//			}
			
			ContainerDetailsVO selContainerDtlsVO = new ContainerDetailsVO();
			
			//STEP 1
			Collection<ContainerDetailsVO> containerDetailsVOs = mailAcceptanceSession.getContainerDetailsVOs();
			Collection<ContainerDetailsVO> mainContDtlsVOs = mailAcceptanceVO.getContainerDetails();
			log.log(Log.FINE, "*containerDetailsVOs**", containerDetailsVOs);
			try{
				if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
					for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
						if("FLIGHT".equals(mailAcceptanceForm.getAssignToFlight())){
							if(containerNum.equals(containerDtlsVO.getContainerNumber())
									&& mailAcceptanceForm.getPou().equals(containerDtlsVO.getPou())
									&& !"N".equals(containerDtlsVO.getContainerOperationFlag())){
								flag = 1;
								BeanHelper.copyProperties(selContainerDtlsVO,containerDtlsVO);
								mailAcceptanceForm.setContainerNo(selContainerDtlsVO.getContainerNumber());
								//mailAcceptanceForm.setDisableFlag("Y");
								log.log(Log.FINE, "*******STEP 1***");
							}else{
								if(containerNum.equals(containerDtlsVO.getContainerNumber())
										&& !"N".equals(containerDtlsVO.getContainerOperationFlag())){
									if(!mailAcceptanceForm.getPou().equals(containerDtlsVO.getPou())){
										invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.invalidcontainerpou",
												new Object[]{containerDtlsVO.getPou()}));
										invocationContext.target = TARGET;
										return;
									}
								}
							}
						}else{
							if(containerNum.equals(containerDtlsVO.getContainerNumber())
									&& !"N".equals(containerDtlsVO.getContainerOperationFlag())){
								flag = 1;
								BeanHelper.copyProperties(selContainerDtlsVO,containerDtlsVO);
								mailAcceptanceForm.setContainerNo(selContainerDtlsVO.getContainerNumber());
								//mailAcceptanceForm.setDisableFlag("Y");
								log.log(Log.FINE, "*******STEP 1***");
							}
						}
						
					}
				}
				
				if(flag == 0){
					log.log(Log.FINE, "**mainContDtlsVOs**", mainContDtlsVOs);
					if(mainContDtlsVOs != null && mainContDtlsVOs.size() > 0){
						for(ContainerDetailsVO containerDtlsVO:mainContDtlsVOs){
							if("FLIGHT".equals(mailAcceptanceForm.getAssignToFlight())){
								if(containerNum.equals(containerDtlsVO.getContainerNumber())
										&& mailAcceptanceForm.getPou().equals(containerDtlsVO.getPou())){
									flag = 1;
									BeanHelper.copyProperties(selContainerDtlsVO,containerDtlsVO);
								}else{
									if(containerNum.equals(containerDtlsVO.getContainerNumber())){
										if(!mailAcceptanceForm.getPou().equals(containerDtlsVO.getPou())){
											invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.invalidcontainerpou",
													new Object[]{containerDtlsVO.getPou()}));
											invocationContext.target = TARGET;
											return;
										}
									}
								}
							}else{
								if(containerNum.equals(containerDtlsVO.getContainerNumber())){
									flag = 1;
									BeanHelper.copyProperties(selContainerDtlsVO,containerDtlsVO);
								}
							}
						}
					}
					if(flag == 1){
						
						if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
							mailAcceptanceForm.setContainerNo(selContainerDtlsVO.getContainerNumber());
							//mailAcceptanceForm.setDisableFlag("Y");
							if("Y".equals(selContainerDtlsVO.getAcceptedFlag())){
								selContainerDtlsVO.setOperationFlag("U");
							}else{
								selContainerDtlsVO.setOperationFlag("I");
							}
							
							Collection<MailbagVO> mailbagVOs = selContainerDtlsVO.getMailDetails();
							if (mailbagVOs != null && mailbagVOs.size() != 0) {
								for (MailbagVO mailbagVO: mailbagVOs) {
									String mailId = mailbagVO.getMailbagId();
									double diplayWt=Double.parseDouble(mailId.substring(mailId.length()-4,mailId.length()));
									Measure strWt=new Measure(UnitConstants.WEIGHT,diplayWt/10);
									mailbagVO.setStrWeight(strWt);//added by A-7371
								}
							}
							
							Collection<ContainerDetailsVO> containerDtlsVOs = new ArrayList<ContainerDetailsVO>();
							for (ContainerDetailsVO containerDtlsVO: containerDetailsVOs) {
								if(!"N".equals(containerDtlsVO.getContainerOperationFlag())){
									containerDtlsVOs.add(containerDtlsVO);
								}
							}
							containerDtlsVOs.add(selContainerDtlsVO);
							log.log(Log.FINE, "*******containerDtlsVOs***",
									containerDtlsVOs);
							containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
							containerDetailsVOs.addAll(containerDtlsVOs);
						}else{

							if("Y".equals(selContainerDtlsVO.getAcceptedFlag())){
								selContainerDtlsVO.setOperationFlag("U");
							}else{
								selContainerDtlsVO.setOperationFlag("I");
							}
							
							Collection<MailbagVO> mailbagVOs = selContainerDtlsVO.getMailDetails();
							if (mailbagVOs != null && mailbagVOs.size() != 0) {
								for (MailbagVO mailbagVO: mailbagVOs) {
									String mailId = mailbagVO.getMailbagId();
									double displayWt=Double.parseDouble(mailId.substring(mailId.length()-4,mailId.length()));
									Measure strWt=new Measure(UnitConstants.WEIGHT,displayWt/10);
									mailbagVO.setStrWeight(strWt);//added by A-7371
								}
							}
							
							containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
							containerDetailsVOs.add(selContainerDtlsVO);
						}
						log.log(Log.FINE, "*******containerDetailsVOs***",
								containerDetailsVOs);
						mailAcceptanceSession.setContainerDetailsVOs(containerDetailsVOs); 
					}
				}
			}catch (SystemException e) {
				e.getMessage();
			}
			if(flag == 0){
				int nowarn = 0;
				//if(ULD_TYPE.equals(containerType)) { 
				log.log(Log.FINE, "*******New Container***");
				
				selContainerDtlsVO.setOperationFlag("I");
				selContainerDtlsVO.setCompanyCode(logonAttributes.getCompanyCode());
				selContainerDtlsVO.setPol(logonAttributes.getAirportCode());
				selContainerDtlsVO.setPou(mailAcceptanceForm.getPou());
				selContainerDtlsVO.setDestination(mailAcceptanceForm.getDestn());
				selContainerDtlsVO.setPaBuiltFlag(mailAcceptanceForm.getPaBuilt());
				selContainerDtlsVO.setContainerJnyId(mailAcceptanceForm.getContainerJnyId());
				selContainerDtlsVO.setPaCode(mailAcceptanceForm.getPaCode());
				selContainerDtlsVO.setContainerType(mailAcceptanceForm.getContainerType());
				selContainerDtlsVO.setContainerNumber(mailAcceptanceForm.getContainerNo());
				selContainerDtlsVO.setPreassignFlag(mailAcceptanceVO.isPreassignNeeded());
				selContainerDtlsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
				selContainerDtlsVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
				
				
				if(!"Y".equals(mailAcceptanceForm.getWarningOveride())){
					
					
					ULDDelegate uldDelegate = new ULDDelegate();
					
					boolean isULDType = ULD_TYPE.equals(containerType);
//					if(!isULDType && containerNum.length()>= 3) {
//						String containerPart = containerNum.substring(0,3);
//						log.log(Log.FINE,"$$$$$$containerPart------->>"+containerPart);
//						Collection<String> containerParts = new ArrayList<String>();
//						containerParts.add(containerPart);
//						try {
//							uldDelegate.validateULDTypeCodes(
//									logonAttributes.getCompanyCode(),containerParts);
//							isULDType = true;
//						}catch (BusinessDelegateException businessDelegateException) {
//							isULDType = false;
//						}
//					}
					
					if(isULDType){	    	
						try {
							uldDelegate.validateULD(logonAttributes.getCompanyCode(),containerNum);
						}catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
						}
						
						if (errors != null && errors.size() > 0) {      
							invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.invaliduldnumber",
									new Object[]{containerNum}));
							invocationContext.target = TARGET;
							ContainerDetailsVO containerDtlsVO = mailAcceptanceSession.getContainerDetailsVO();
							containerDtlsVO.setContainerType(mailAcceptanceForm.getContainerType());
							containerDtlsVO.setPou(mailAcceptanceForm.getPou());
							containerDtlsVO.setPaBuiltFlag(mailAcceptanceForm.getPaBuilt());
							containerDtlsVO.setContainerJnyId(mailAcceptanceForm.getContainerJnyId());
							containerDtlsVO.setPaCode(mailAcceptanceForm.getPaCode());
							containerDtlsVO.setDestination(mailAcceptanceForm.getDestn());
							mailAcceptanceSession.setContainerDetailsVO(containerDtlsVO);
							return;
						}
					}	
					
					//Added by A-7540 for ICRD-245350
					else{
						Collection<ErrorVO> bulkErrors = null;
						Collection<String> codes = new ArrayList<String>();
				      	codes.add(ULD_AS_BARROW);
				      	Map<String, String> results = null;
				      	try {
				      		results = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
				      	} catch(BusinessDelegateException businessDelegateException) {
				      		handleDelegateException(businessDelegateException);
				      	}
						if (results != null && !FLAG_YES.equals(results.get(ULD_AS_BARROW))){
							try {
								uldDelegate.validateULD(logonAttributes.getCompanyCode(),containerNum);
							}catch (BusinessDelegateException businessDelegateException) {
								bulkErrors = handleDelegateException(businessDelegateException);
							}
						
						
					
					if (bulkErrors == null) {      
						invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.invalidbulkformat"));
						invocationContext.target = TARGET;
						ContainerDetailsVO containerDtlsVO = mailAcceptanceSession.getContainerDetailsVO();
						containerDtlsVO.setContainerType(mailAcceptanceForm.getContainerType());
						containerDtlsVO.setPou(mailAcceptanceForm.getPou());
						containerDtlsVO.setPaBuiltFlag(mailAcceptanceForm.getPaBuilt());
						containerDtlsVO.setContainerJnyId(mailAcceptanceForm.getContainerJnyId());
						containerDtlsVO.setPaCode(mailAcceptanceForm.getPaCode());
						containerDtlsVO.setDestination(mailAcceptanceForm.getDestn());
						mailAcceptanceSession.setContainerDetailsVO(containerDtlsVO);
						return;
					}
				  }
				}
					
					ContainerVO containerVO = new ContainerVO();
					if("Y".equals(mailAcceptanceForm.getOverrideUMSFlag())){
						containerVO.setOverrideUMSFlag(true);
						mailAcceptanceForm.setOverrideUMSFlag("");
					}
					containerVO.setCompanyCode(logonAttributes.getCompanyCode());
					containerVO.setAssignedPort(logonAttributes.getAirportCode());
					containerVO.setContainerNumber(mailAcceptanceForm.getContainerNo());
					containerVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
					containerVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
					containerVO.setCarrierId(mailAcceptanceVO.getCarrierId());
					containerVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
					containerVO.setType(mailAcceptanceForm.getContainerType());
					containerVO.setPou(mailAcceptanceForm.getPou());
					containerVO.setLegSerialNumber(mailAcceptanceVO.getLegSerialNumber());
					containerVO.setFinalDestination(mailAcceptanceForm.getDestn());
					if(mailAcceptanceVO.getFlightSequenceNumber() > 0) {
						containerVO.setAssignmentFlag("F");
					}
					log.log(Log.FINE, "*******containerVO***", containerVO);
					try {
						if(!mailAcceptanceForm.isCanDiscardUldValidation()){   /*added by A-8149 for ICRD-276070*/
						mailAcceptanceForm.setWarningStatus("uldvalidation"); /*added by A-8149 for ICRD-276070*/
						containerVO = new MailTrackingDefaultsDelegate().validateContainer(logonAttributes.getAirportCode(), containerVO);
						}
						log.log(Log.FINE, "Resultvo ------------> ",
								containerVO);
						
					}catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
						if (errors != null && errors.size() > 0) {
							for(ErrorVO vo : errors) {
								log.log(Log.FINE,
										"vo.getErrorCode() ----------> ", vo.getErrorCode());
								if (("mailtracking.defaults.openedflight").equals(vo.getErrorCode())) {
									//assignContainerForm.setWarningCode(CON_REASSIGN_WARN_FLIGHT);
									Object[] obj = vo.getErrorData();
									ContainerAssignmentVO containerAssignmentVO = (ContainerAssignmentVO)obj[2];
									log
											.log(
													Log.FINE,
													"ContainerAssignmentVO (Flight)------------> ",
													containerAssignmentVO);
									selContainerDtlsVO.setReassignFlag(true);
									selContainerDtlsVO.setCarrierId(containerAssignmentVO.getCarrierId());
									selContainerDtlsVO.setCarrierCode(containerAssignmentVO.getCarrierCode());				
									selContainerDtlsVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
									selContainerDtlsVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
									selContainerDtlsVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
									selContainerDtlsVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
									selContainerDtlsVO.setFlightDate(containerAssignmentVO.getFlightDate());
									selContainerDtlsVO.setAcceptedFlag(containerAssignmentVO.getAcceptanceFlag());
									break;
								}
								else if (("mailtracking.defaults.canreassigned").equals(vo.getErrorCode())) {
									//assignContainerForm.setWarningCode(CON_REASSIGN_WARN_DESTN);
									Object[] obj = vo.getErrorData();
									ContainerAssignmentVO containerAssignmentVO = (ContainerAssignmentVO)obj[2];
									log
											.log(
													Log.FINE,
													"ContainerAssignmentVO (Destn)------------> ",
													containerAssignmentVO);
									selContainerDtlsVO.setReassignFlag(true);
									selContainerDtlsVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
									selContainerDtlsVO.setCarrierId(containerAssignmentVO.getCarrierId());
									selContainerDtlsVO.setFlightNumber("-1");
									selContainerDtlsVO.setFlightSequenceNumber(-1);
									selContainerDtlsVO.setLegSerialNumber(-1);
									selContainerDtlsVO.setAcceptedFlag(containerAssignmentVO.getAcceptanceFlag());
									break;
								}
								else{
									selContainerDtlsVO.setCarrierId(mailAcceptanceVO.getCarrierId());
									selContainerDtlsVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
									selContainerDtlsVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
									selContainerDtlsVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());				
									selContainerDtlsVO.setLegSerialNumber(mailAcceptanceVO.getLegSerialNumber());
									selContainerDtlsVO.setFlightDate(mailAcceptanceVO.getFlightDate());
									
									mailAcceptanceForm.setOverrideUMSFlag("Y");
									break;
								}
							}
							mailAcceptanceSession.setContainerDetailsVO(selContainerDtlsVO);
							invocationContext.addAllError(errors);
							invocationContext.target = TARGET;
							return;
						}                        
					}
				}else{
					mailAcceptanceForm.setWarningOveride("");
					selContainerDtlsVO = mailAcceptanceSession.getContainerDetailsVO();
					selContainerDtlsVO.setContainerOperationFlag("I");
					nowarn = 1;
				}
				//}
				
				if(nowarn == 0){
					log.log(Log.FINE,"selContainerDtlsVO in ListAcceptMailCommand----01------> ");
					selContainerDtlsVO.setOperationFlag("I");
					selContainerDtlsVO.setContainerOperationFlag("I");
					selContainerDtlsVO.setCompanyCode(logonAttributes.getCompanyCode());
					selContainerDtlsVO.setPol(logonAttributes.getAirportCode());
					selContainerDtlsVO.setPou(mailAcceptanceForm.getPou());
					selContainerDtlsVO.setDestination(mailAcceptanceForm.getDestn());
					selContainerDtlsVO.setPaBuiltFlag(mailAcceptanceForm.getPaBuilt());
					selContainerDtlsVO.setContainerJnyId(mailAcceptanceForm.getContainerJnyId());
					selContainerDtlsVO.setPaCode(mailAcceptanceForm.getPaCode());
					selContainerDtlsVO.setContainerType(mailAcceptanceForm.getContainerType());
					selContainerDtlsVO.setContainerNumber(mailAcceptanceForm.getContainerNo());
					selContainerDtlsVO.setPreassignFlag(mailAcceptanceVO.isPreassignNeeded());
					selContainerDtlsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
					selContainerDtlsVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
					selContainerDtlsVO.setCarrierId(mailAcceptanceVO.getCarrierId());
					selContainerDtlsVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
					selContainerDtlsVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
				}
				
				Collection<ContainerDetailsVO> containerDtlsVOs = new ArrayList<ContainerDetailsVO>();
				if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
					log.log(Log.FINE,"selContainerDtlsVO in ListAcceptMailCommand----1------> ");
					for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
						log.log(Log.FINE,"selContainerDtlsVO in ListAcceptMailCommand----2------> ");
						if("N".equals(containerDtlsVO.getContainerOperationFlag())){
							log.log(Log.FINE,"selContainerDtlsVO in ListAcceptMailCommand----3------> ");
							containerDtlsVOs.add(selContainerDtlsVO);
						}else{
							log.log(Log.FINE,"selContainerDtlsVO in ListAcceptMailCommand----4------> ");
							containerDtlsVOs.add(containerDtlsVO);
						}
					}
				}
				mailAcceptanceSession.setContainerDetailsVOs(containerDtlsVOs); 
			}
			
			
			Collection<ContainerDetailsVO> finalConDtlsVOs = new ArrayList<ContainerDetailsVO>();
			Collection<ContainerDetailsVO> conDtlsVOs =mailAcceptanceSession.getContainerDetailsVOs(); 
			if(conDtlsVOs != null && conDtlsVOs.size() > 0){
				for(ContainerDetailsVO conDtlsVO:conDtlsVOs){
					if(!"N".equals(conDtlsVO.getContainerOperationFlag())){
						finalConDtlsVOs.add(conDtlsVO);
					}
				}
			}
			mailAcceptanceSession.setContainerDetailsVOs(finalConDtlsVOs);
			//log.log(Log.FINE,"selContainerDtlsVO in ListAcceptMailCommand----------> " + selContainerDtlsVO);
			mailAcceptanceSession.setContainerDetailsVO(selContainerDtlsVO);
			//log.log(Log.FINE,"mailAcceptanceSession.getContainerDetailsVOs() in ListAcceptMailCommand----------> " + mailAcceptanceSession.getContainerDetailsVOs());
			
			/**
			 * @author A-3227
			 * 
			 */		 
			Collection<ContainerDetailsVO> contDetailVOs = new ArrayList<ContainerDetailsVO>();
			Collection<ContainerDetailsVO> newContainerDetailsVOs = null;
			int n=0;
			if(selContainerDtlsVO.getMailDetails()==null	&& selContainerDtlsVO.getDesptachDetailsVOs()== null){
				//if(selContainerDtlsVO.getOperationFlag()== null || ("U").equals(selContainerDtlsVO.getOperationFlag())){
					contDetailVOs.add(selContainerDtlsVO);
					try {					
						newContainerDetailsVOs =new MailTrackingDefaultsDelegate().findMailbagsInContainer(contDetailVOs);
					} catch (BusinessDelegateException businessDelegateException){
						errors = handleDelegateException(businessDelegateException);
					}
					if(newContainerDetailsVOs!=null && newContainerDetailsVOs.size()>0 &&
							newContainerDetailsVOs.iterator().next().getMailDetails().isEmpty() &&
							newContainerDetailsVOs.iterator().next().getMailDetails().size()==0){//added by A-7371 as part of ICRD-271301
				    	//mailAcceptanceForm.setAddRowEnableFlag(MailConstantsVO.FLAG_YES); Commented by A-8527 for ICRD-293590
				    }
					for( ContainerDetailsVO contnrDtlsVO :finalConDtlsVOs){
						for(ContainerDetailsVO newcontrDtlVO :newContainerDetailsVOs ){
							if(contnrDtlsVO.getContainerNumber().equals(newcontrDtlVO.getContainerNumber())){
								contnrDtlsVO.setMailDetails(newcontrDtlVO.getMailDetails());
								contnrDtlsVO.setDesptachDetailsVOs(newcontrDtlVO.getDesptachDetailsVOs());
								//Added by A_7540
								mailAcceptanceForm.setContainerType(contnrDtlsVO.getContainerType());
								if(ULD_TYPE.equalsIgnoreCase(contnrDtlsVO.getContainerType())){
									mailAcceptanceForm.setBarrowCheck(false);
						       		}else{
						       		mailAcceptanceForm.setBarrowCheck(true);
								
						       	}
								Collection<MailbagVO> mailbagVOs = contnrDtlsVO.getMailDetails();
								if (mailbagVOs != null && mailbagVOs.size() != 0) {
									for (MailbagVO mailbagVO: mailbagVOs) {
										String mailId = mailbagVO.getMailbagId();
										double displayWt=Double.parseDouble(mailId.substring(mailId.length()-4,mailId.length()));
										Measure strWt=new Measure(UnitConstants.WEIGHT,displayWt/10);
										mailbagVO.setStrWeight(strWt);//added by A-7371
										/*
				    					 * Added By RENO K ABRAHAM : ANZ BUG : 37646
				    					 * As a part of performance Upgrade
				    					 * START.
				    					 */
				           		       	if(mailbagVO.getOperationalFlag()==null 
				           		       			|| !("I").equals(mailbagVO.getOperationalFlag())){
				           		       		mailbagVO.setDisplayLabel("Y");
				           		       	}      
				           		       	//END
									}
								}
			        			Collection<DespatchDetailsVO> despatchDetailsVOs = contnrDtlsVO.getDesptachDetailsVOs();
			        			if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0) {
				       		       	for (DespatchDetailsVO despatchDetailsVO: despatchDetailsVOs) {	       		       		
				       		       		/*
				    					 * Added By RENO K ABRAHAM : ANZ BUG : 37646
				    					 * As a part of performance Upgrade
				    					 * START.
				    					 */
				           		       	if(despatchDetailsVO.getOperationalFlag()==null 
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
					
				//}
			}
			
			mailAcceptanceForm.setDisableFlag("");
		}
	/*	if ((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT)!=null){
			mailAcceptanceForm.setDefWeightUnit((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT));//added by A_8353 for ICRD-274933 
			}
			else{
				mailAcceptanceForm.setDefWeightUnit((String)stationParameters.get(STNPAR_DEFUNIT_WEIGHT));
			}//added by A_8353 for ICRD-274933 */
		mailAcceptanceForm.setScreenStatusFlag("detail");
		invocationContext.target = TARGET;
		log.exiting("ListAcceptMailCommand","execute");
		
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
