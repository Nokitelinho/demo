package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingIndexVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.AddMailbag;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.PopulateMailbagDetailsCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	04-Feb-2019		:	Draft
 */
public class PopulateMailbagDetailsCommand extends AbstractCommand  {
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS PopulateMailbagDetailsCommand");
	private Map<String,String> exchangeOfficeMap;
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	private static final String DEST_FOR_CDT_MISSING_DOM_MAL="mail.operation.destinationforcarditmissingdomesticmailbag";
	private static final String DOMESTIC_WARNING = "mail.operation.domesticmaildoesnotexistwarning";  
	private static final String DOMESTIC_WARNING_ONSAVE = "mail.operation.domesticmaildoesnotexistwarningonsave";
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
this.log.entering("PopulateMailbagDetailsCommand", "execute");
		
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		MailinboundModel mailinboundModel = (MailinboundModel) actionContext.getScreenModel();
		ArrayList<AddMailbag> addMailBags = mailinboundModel.getAddMailbags();
		String companycode = logonAttributes.getCompanyCode();
		AddMailbag bagDetails = null;
		ResponseVO responseVO = new ResponseVO();
		MailbagVO mailbagVO = new MailbagVO();
		ArrayList<AddMailbag> addMailbagsCollection = new ArrayList<AddMailbag>();
		String defDestForCdtMissingMailbag=null;
		try {
			defDestForCdtMissingMailbag=findSystemParameterValue(DEST_FOR_CDT_MISSING_DOM_MAL);
		} catch (SystemException e1) {
			log.log(Log.INFO, e1);
		}
		if(null!=addMailBags&&addMailBags.size()>0){
			if(!mailinboundModel.getAddMailbagMode().equalsIgnoreCase("EXCEL_VIEW")) {
			AddMailbag addMailbag=addMailBags.get(0);
			if (addMailbag.getMailbagId().length() == 29 || addMailbag.getMailbagId().length() == 12) {
			try{
				mailbagVO =new MailTrackingDefaultsDelegate().findMailDetailsForMailTag(
						logonAttributes.getCompanyCode(),addMailbag.getMailbagId().toUpperCase()) ;
	  		}catch (BusinessDelegateException businessDelegateException) {
  				handleDelegateException(businessDelegateException);
  			}
			
			if(null==mailbagVO && addMailbag.getMailbagId().length() == 29){
				actionContext.addError(new ErrorVO("mail.operations.err.mailbagnotfound"));
				return;
			}	
			else if (mailbagVO!=null&&mailbagVO.getMailbagId()!=null&&!mailbagVO.getMailbagId().isEmpty()){
				 bagDetails = MailInboundModelConverter.populateMailDetails(mailbagVO);
			}
			else if (addMailbag.getMailbagId().length() == 12) {
				String routIndex = addMailbag.getMailbagId().substring(4, 8);
				String org = null;
				String dest = null;
				Collection<RoutingIndexVO> routingIndexVOs = new ArrayList<RoutingIndexVO>();
				RoutingIndexVO routingIndexFilterVO = new RoutingIndexVO();
				routingIndexFilterVO.setRoutingIndex(routIndex);
				routingIndexFilterVO.setCompanyCode(companycode);
				LocalDate scannedDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,false);
				if(addMailbag.getScannedDate()!=null && addMailbag.getScannedDate().trim().length()>0) {
					scannedDate.setDate(addMailbag.getScannedDate());
				}
				routingIndexFilterVO.setScannedDate(scannedDate);
				try {
					routingIndexVOs = new MailTrackingDefaultsDelegate().findRoutingIndex(routingIndexFilterVO);
				} catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
				}
				if(routingIndexVOs.isEmpty()&&defDestForCdtMissingMailbag!=null &&!"NA".equals(defDestForCdtMissingMailbag)){
					String domesticWarningStatus =mailinboundModel.getShowWarning();
			    	if("Y".equals(domesticWarningStatus)){     
					List<ErrorVO> warningErrors = new ArrayList<>();
				    ErrorVO warningError = new ErrorVO(DOMESTIC_WARNING);   
		  		    warningError.setErrorDisplayType(ErrorDisplayType.WARNING);
		  		    warningErrors.add(warningError);
		  		    actionContext.addAllError(warningErrors); 
				    return;  
			    	}
					RoutingIndexVO routingIndexVO=new RoutingIndexVO();
					if(mailinboundModel.getMailinboundDetails().getLegOrigin()!=null){ 
					routingIndexVO.setOrigin(mailinboundModel.getMailinboundDetails().getLegOrigin());
					}
					routingIndexVO.setDestination(defDestForCdtMissingMailbag);	
					routingIndexVO.setRoutingIndex("XXXX");	      
					routingIndexVOs.add(routingIndexVO);
				}
				exchangeOfficeMap=new HashMap<String,String>();
				
				if(routingIndexVOs.size()>0){
					for(RoutingIndexVO routingIndexVO:routingIndexVOs){
						if(routingIndexVO!=null&&routingIndexVO.getRoutingIndex()!=null){
							 org=routingIndexVO.getOrigin();
							 dest=routingIndexVO.getDestination();
							 try {
									exchangeOfficeMap=new MailTrackingDefaultsDelegate().findOfficeOfExchangeForPA(companycode,findSystemParameterValue(USPS_DOMESTIC_PA));
								} catch (BusinessDelegateException businessDelegateException) {
									handleDelegateException(businessDelegateException);;
								} catch (SystemException e) {
									e.printStackTrace();
								} 
							 
							 if (exchangeOfficeMap != null && !exchangeOfficeMap.isEmpty()) {
									if (exchangeOfficeMap.containsKey(org)) {
										addMailbag.setOoe(exchangeOfficeMap.get(org));
									}
									if (exchangeOfficeMap.containsKey(dest)) {
										addMailbag.setDoe(exchangeOfficeMap.get(dest));
									}
								}
							 
							    addMailbag.setMailCategoryCode("B");
								String mailClass = addMailbag.getMailbagId().substring(3, 4);
								addMailbag.setMailSubclass(mailClass + "X");
								int lastTwoDigits = Calendar.getInstance().get(Calendar.YEAR) % 100;
								addMailbag.setYear(String.valueOf((lastTwoDigits % 10)));

								/*MailbagVO newMailbagVO = new MailbagVO();
								newMailbagVO.setCompanyCode(companycode);
								newMailbagVO.setYear(lastTwoDigits % 10);
								try {
									newMailbagVO = new MailTrackingDefaultsDelegate().findDsnAndRsnForMailbag(newMailbagVO);
								} catch (BusinessDelegateException businessDelegateException) {
									handleDelegateException(businessDelegateException);
								}
								if (newMailbagVO.getDespatchSerialNumber() != null) {
									addMailbag.setDespatchSerialNumber(newMailbagVO.getDespatchSerialNumber());
								}

								if (newMailbagVO.getReceptacleSerialNumber() != null) {
									addMailbag.setReceptacleSerialNumber(newMailbagVO.getReceptacleSerialNumber());
								}*/
								
								addMailbag.setDespatchSerialNumber(MailConstantsVO.DOM_MAILBAG_DEF_DSNVAL);  
								addMailbag.setReceptacleSerialNumber(MailConstantsVO.DOM_MAILBAG_DEF_RSNVAL);
								addMailbag.setHighestNumberedReceptacle("9");
								addMailbag.setRegisteredOrInsuredIndicator("9");
								
								/*if (addMailbag.getMailbagId().length() == 12) {
									Measure weight = new Measure(UnitConstants.MAIL_WGT,
											Double.parseDouble(addMailbag.getMailbagId().substring(11, 12)));
									double convertedWeight = unitConvertion(UnitConstants.MAIL_WGT, "L", addMailbag.getDisplayUnit(),
											Double.parseDouble(addMailbag.getMailbagId().substring(11, 12)));
									double conDisplayWeight = 0;
									if (UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(addMailbag.getDisplayUnit())) {
										conDisplayWeight = round(convertedWeight, 1);
									} else {
										conDisplayWeight = round(convertedWeight, 0);
									}
									addMailbag.setWeight(String.valueOf(conDisplayWeight));

								}*/
								mailbagVO.setMailbagId(addMailbag.getMailbagId());
								mailbagVO.setOoe(addMailbag.getOoe());
								mailbagVO.setDoe(addMailbag.getDoe());
								mailbagVO.setMailCategoryCode(addMailbag.getMailCategoryCode());
								mailbagVO.setMailSubclass(addMailbag.getMailSubclass());
								mailbagVO.setCompanyCode(addMailbag.getCompanyCode());
								mailbagVO.setYear(Integer.parseInt(addMailbag.getYear()));
								mailbagVO.setDespatchSerialNumber(addMailbag.getDespatchSerialNumber());
								mailbagVO.setReceptacleSerialNumber(addMailbag.getReceptacleSerialNumber());
								mailbagVO.setHighestNumberedReceptacle(addMailbag.getHighestNumberedReceptacle());
								mailbagVO.setRegisteredOrInsuredIndicator(addMailbag.getRegisteredOrInsuredIndicator());
								//mailbagVO.setWeight(addMailbag.getWeight());
								
								if(org!=null){
									mailbagVO.setMailOrigin(org);	
								}
								if(dest!=null){
									mailbagVO.setMailDestination(dest);	
								}
								bagDetails = MailInboundModelConverter.populateMailDetails(mailbagVO);
						}
					}
					
				}
				else{
					actionContext.addError(new ErrorVO("mail.operations.err.invalidmailbag"));
					return;
				}
				

				

			}

				 
			} 
			else{
				actionContext.addError(new ErrorVO("mail.operations.err.invalidmailbag"));
				return;
			}

			addMailbagsCollection.add(bagDetails);
			}
		else {
				for(AddMailbag mailbag:mailinboundModel.getAddMailbags()) {
					if (mailbag.getMailbagId().length() == 12) {
						try{
							mailbagVO =new MailTrackingDefaultsDelegate().findMailDetailsForMailTag(
									logonAttributes.getCompanyCode(),mailbag.getMailbagId().toUpperCase()) ;
				  		}catch (BusinessDelegateException businessDelegateException) {
			  				handleDelegateException(businessDelegateException);
			  			}
						if(null==mailbagVO){
							actionContext.addError(new ErrorVO("mail.operations.err.mailbagnotfound"));
							return;
						}	
						/*else if(mailbag.getMailbagId().length() == 29) {
						}*/
						else if (mailbagVO!=null&&mailbagVO.getMailbagId()!=null&&!mailbagVO.getMailbagId().isEmpty()){
							 bagDetails=	MailInboundModelConverter.populateMailDetails(mailbagVO);
						}
						else if (mailbag.getMailbagId().length() == 12) {
							String routIndex = mailbag.getMailbagId().substring(4, 8);
							String org = null;
							String dest = null;
							Collection<RoutingIndexVO> routingIndexVOs = new ArrayList<RoutingIndexVO>();
							RoutingIndexVO routingIndexFilterVO = new RoutingIndexVO();
							routingIndexFilterVO.setRoutingIndex(routIndex);
							routingIndexFilterVO.setCompanyCode(companycode);
							LocalDate scannedDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,false);
							if(mailbag.getScannedDate()!=null && mailbag.getScannedDate().trim().length()>0) {
								scannedDate.setDate(mailbag.getScannedDate());
							}
							routingIndexFilterVO.setScannedDate(scannedDate);
							try {
								routingIndexVOs = new MailTrackingDefaultsDelegate().findRoutingIndex(routingIndexFilterVO);
							} catch (BusinessDelegateException businessDelegateException) {
								handleDelegateException(businessDelegateException);
							}
							if(routingIndexVOs.isEmpty()&&defDestForCdtMissingMailbag!=null &&!"NA".equals(defDestForCdtMissingMailbag)){
								String domesticWarningStatus =mailinboundModel.getShowWarning();
						    	if("Y".equals(domesticWarningStatus)){     
								List<ErrorVO> warningErrors = new ArrayList<>();
							    ErrorVO warningError = new ErrorVO(DOMESTIC_WARNING_ONSAVE);   
					  		    warningError.setErrorDisplayType(ErrorDisplayType.WARNING);
					  		    warningErrors.add(warningError);
					  		    actionContext.addAllError(warningErrors); 
							    return;  
						    	}   
								RoutingIndexVO routingIndexVO=new RoutingIndexVO();
								if(mailinboundModel.getMailinboundDetails().getLegOrigin()!=null){  
								routingIndexVO.setOrigin(mailinboundModel.getMailinboundDetails().getLegOrigin());
								}
								routingIndexVO.setDestination(defDestForCdtMissingMailbag);	
								routingIndexVO.setRoutingIndex("XXXX");	      
								routingIndexVOs.add(routingIndexVO);
							}
							exchangeOfficeMap=new HashMap<String,String>();
							if(routingIndexVOs.size()>0){
								for(RoutingIndexVO routingIndexVO:routingIndexVOs){
									if(routingIndexVO!=null&&routingIndexVO.getRoutingIndex()!=null){
										 org=routingIndexVO.getOrigin();
										 dest=routingIndexVO.getDestination();
										 try {
												exchangeOfficeMap=new MailTrackingDefaultsDelegate().findOfficeOfExchangeForPA(companycode,findSystemParameterValue(USPS_DOMESTIC_PA));
											} catch (BusinessDelegateException businessDelegateException) {
												handleDelegateException(businessDelegateException);;
											} catch (SystemException e) {
												e.printStackTrace();
											} 
										 if (exchangeOfficeMap != null && !exchangeOfficeMap.isEmpty()) {
												if (exchangeOfficeMap.containsKey(org)) {
													mailbag.setOoe(exchangeOfficeMap.get(org));
												}
												if (exchangeOfficeMap.containsKey(dest)) {
													mailbag.setDoe(exchangeOfficeMap.get(dest));
												}
											}
										    mailbag.setMailCategoryCode("B");
											String mailClass = mailbag.getMailbagId().substring(3, 4);
											mailbag.setMailSubclass(mailClass + "X");
											int lastTwoDigits = Calendar.getInstance().get(Calendar.YEAR) % 100;
											mailbag.setYear(String.valueOf((lastTwoDigits % 10)));
											
											
											/*MailbagVO newMailbagVO = new MailbagVO();
											newMailbagVO.setCompanyCode(companycode);
											newMailbagVO.setYear(lastTwoDigits % 10);
											try {
												newMailbagVO = new MailTrackingDefaultsDelegate().findDsnAndRsnForMailbag(newMailbagVO);
											} catch (BusinessDelegateException businessDelegateException) {
												handleDelegateException(businessDelegateException);
											}
											if (newMailbagVO.getDespatchSerialNumber() != null) {
												mailbag.setDespatchSerialNumber(newMailbagVO.getDespatchSerialNumber());
											}
											if (newMailbagVO.getReceptacleSerialNumber() != null) {
												mailbag.setReceptacleSerialNumber(newMailbagVO.getReceptacleSerialNumber());
											}*/
											
											mailbag.setDespatchSerialNumber(MailConstantsVO.DOM_MAILBAG_DEF_DSNVAL);  
											mailbag.setReceptacleSerialNumber(MailConstantsVO.DOM_MAILBAG_DEF_RSNVAL);

											mailbag.setHighestNumberedReceptacle("9");
											mailbag.setRegisteredOrInsuredIndicator("9");
											mailbagVO.setMailbagId(mailbag.getMailbagId());
											mailbagVO.setOoe(mailbag.getOoe());
											mailbagVO.setDoe(mailbag.getDoe());
											mailbagVO.setMailCategoryCode(mailbag.getMailCategoryCode());
											mailbagVO.setMailSubclass(mailbag.getMailSubclass());
											mailbagVO.setCompanyCode(mailbag.getCompanyCode());
											mailbagVO.setYear(Integer.parseInt(mailbag.getYear()));
											mailbagVO.setDespatchSerialNumber(mailbag.getDespatchSerialNumber());
											mailbagVO.setReceptacleSerialNumber(mailbag.getReceptacleSerialNumber());
											mailbagVO.setHighestNumberedReceptacle(mailbag.getHighestNumberedReceptacle());
											mailbagVO.setRegisteredOrInsuredIndicator(mailbag.getRegisteredOrInsuredIndicator());
											/**mailbagVO.setWeight(addMailbag.getWeight());*/
											if(org!=null){
												mailbagVO.setMailOrigin(org);	
											}
											if(dest!=null){
												mailbagVO.setMailDestination(dest);	
											}
											bagDetails = MailInboundModelConverter.populateMailDetails(mailbagVO);
									}
								}
				}else{
					actionContext.addError(new ErrorVO("mail.operations.err.invalidmailbag"));
					return;
				}
						}
			}
					else if (mailbag.getMailbagId().length() == 29) {
						mailbagVO= new MailbagVO();
						mailbagVO.setMailbagId(mailbag.getMailbagId());
						mailbagVO.setOoe(mailbag.getMailbagId().substring(0, 6));
						mailbagVO.setDoe(mailbag.getMailbagId().substring(6, 12));
						mailbagVO.setMailCategoryCode(mailbag.getMailbagId().substring(12, 13));
						mailbagVO.setMailSubclass(mailbag.getMailbagId().substring(13, 15));
						mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
						mailbagVO.setYear(Integer.parseInt(mailbag.getMailbagId().substring(15, 16)));
						mailbagVO.setDespatchSerialNumber(mailbag.getMailbagId().substring(16, 20));
						mailbagVO.setReceptacleSerialNumber(mailbag.getMailbagId().substring(20, 23));
						mailbagVO.setHighestNumberedReceptacle(mailbag.getMailbagId().substring(23, 24));
						mailbagVO.setRegisteredOrInsuredIndicator(mailbag.getMailbagId().substring(24, 25));
						mailbagVO.setMailRemarks(mailbag.getRemarks());
						bagDetails = MailInboundModelConverter.populateMailDetails(mailbagVO);
					}
					else{
						actionContext.addError(new ErrorVO("mail.operations.err.invalidmailbag"));
						return;
					}
			addMailbagsCollection.add(bagDetails);
		}
		}
		}
		else{
			actionContext.addError(new ErrorVO("Invalid Mail bag"));
				return;
		}
			mailinboundModel.setAddMailbags(addMailbagsCollection);
			ArrayList<MailinboundModel> result=new ArrayList<MailinboundModel>();
			result.add(mailinboundModel); 
			responseVO.setResults(result);
			responseVO.setStatus("success"); 
			actionContext.setResponseVO(responseVO);
			log.exiting("PopulateMailbagDetailsCommand", "execute");
			
						
	}
	

	/**
	 * 
	 * Method : PopulateMailbagDetailsCommand.findSystemParameterValue Added by
	 * : A-7929 on 04-June-2019 Used for : Parameters : @param syspar Parameters
	 * : @return Parameters : @throws SystemException Return type : String
	 * 
	 * @throws BusinessDelegateException
	 */
	private String findSystemParameterValue(String syspar) throws SystemException, BusinessDelegateException {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = new SharedDefaultsDelegate()
				.findSystemParameterByCodes(systemParameters);
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

	

	
}
