/*
 * PopulateMailTagDetailsCommand.java Created on Oct 10 2019
 *
 * Copyright 2017 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.errorhandling;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingIndexVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitConversionNewVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ErrorHandlingPopUpForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-7540
 *
 */
public class PopulateMailTagDetailsCommand extends BaseCommand{
	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
    
	   private static final String CLASS_NAME = "PopulateMailTagDetailsCommand";
	  private static final String NO_OF_CHAR_ALLOWED_FOR_MAILTAG = "mailtracking.defaults.noofcharsallowedformailtag";
	   private static final String TARGET_SUCCESS = "success";
	   
	   private static final String INT_REGEX = "[0-9]+";
	   private Map<String,String> exchangeOfficeMap;
		 
	   private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	   private static final String SYSTEM_WEIGHT_UNIT="system.defaults.unit.weight";
	   private static final String STNPAR_DEFUNIT_WGT = "station.defaults.unit.weight";
		 
	/**
	 * 
	 */
		 public void execute(InvocationContext invocationContext) throws CommandInvocationException {
			 log.entering(CLASS_NAME,"execute");	
			   ErrorHandlingPopUpForm errorHandlingPopUpForm = (ErrorHandlingPopUpForm)invocationContext.screenModel;
	 	    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		    	String companyCode=logonAttributes.getCompanyCode();
		    	MailbagVO mailbagVO=null;
		    	String mailbagId = errorHandlingPopUpForm.getMailBag().toUpperCase(); 
		    	String weightUnit="";
		    	String mailWgt="";
		    	Measure mailWt=null;
		    	Map stationParameters = null;
		    	 AreaDelegate areaDelegate = new AreaDelegate(); 
					try {
						 stationParameters = areaDelegate.findStationParametersByCode(companyCode, logonAttributes.getAirportCode(), getStationParameterCodes());
						 weightUnit=(String)stationParameters.get(STNPAR_DEFUNIT_WGT);
					} catch ( BusinessDelegateException businessDelegateException) {
						businessDelegateException.getMessage();
		  			}
				/*try {
					weightUnit = findSystemParameterValue(SYSTEM_WEIGHT_UNIT);
				} catch (BusinessDelegateException e) {
					e.getMessage();
				}*/
		    	errorHandlingPopUpForm.setInvalidMailbagId(false);
		    	if(mailbagId!=null && !mailbagId.isEmpty()){
					try{
						mailbagVO =new MailTrackingDefaultsDelegate().findMailDetailsForMailTag(
								companyCode,mailbagId.toUpperCase()) ;
			  		}catch (BusinessDelegateException businessDelegateException) {
		  				handleDelegateException(businessDelegateException);
		  			}
					if(mailbagVO!=null&&mailbagVO.getMailbagId()!=null&&
							!mailbagVO.getMailbagId().isEmpty()){
						errorHandlingPopUpForm.setOoe(mailbagVO.getOoe());
						errorHandlingPopUpForm.setDoe(mailbagVO.getDoe());
						errorHandlingPopUpForm.setCategory(mailbagVO.getMailCategoryCode());
						errorHandlingPopUpForm.setSubclass(mailbagVO.getMailSubclass());
						errorHandlingPopUpForm.setYear(String.valueOf(mailbagVO.getYear()));
						errorHandlingPopUpForm.setDsn(mailbagVO.getDespatchSerialNumber());
						errorHandlingPopUpForm.setRsn(mailbagVO.getReceptacleSerialNumber());
						errorHandlingPopUpForm.setHni(mailbagVO.getHighestNumberedReceptacle());
						errorHandlingPopUpForm.setRi(mailbagVO.getRegisteredOrInsuredIndicator());
						double covertedWeight=0.0;
						if(mailbagVO.getMailbagId().length()==29){
    					 covertedWeight =unitConvertion(UnitConstants.MAIL_WGT,"H",weightUnit,Double.parseDouble(mailbagId.substring(25,29)));
						}
						else if(mailbagVO.getMailbagId().length()==12){
							covertedWeight =unitConvertion(UnitConstants.MAIL_WGT,"L",weightUnit,Double.parseDouble(mailbagId.substring(10,12)));
						}
    					/*double conDisplayWeight=0;
    					if(UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(weightUnit)){
    						 conDisplayWeight=round(covertedWeight,1); 
    					}
    					else {
    						conDisplayWeight=round(covertedWeight,0);
    					} */
    					mailWt=new Measure(UnitConstants.MAIL_WGT,covertedWeight); 
						mailWgt=String.valueOf(mailWt.getSystemValue());
						errorHandlingPopUpForm.setWeightMeasure(mailWt);	
						errorHandlingPopUpForm.setWeight(String.valueOf(mailWgt));
					}
					else if(mailbagId.length()==29 && validateMailTagFormat(mailbagId)){
						errorHandlingPopUpForm.setOoe(mailbagId.substring(0, 6));
						errorHandlingPopUpForm.setDoe(mailbagId.substring(6,12 ));
						errorHandlingPopUpForm.setCategory(mailbagId.substring(12,13 ));
						errorHandlingPopUpForm.setSubclass(mailbagId.substring(13,15));
						errorHandlingPopUpForm.setYear(mailbagId.substring(15,16));
						errorHandlingPopUpForm.setDsn(mailbagId.substring(16,20));
						errorHandlingPopUpForm.setRsn(mailbagId.substring(20,23));
						errorHandlingPopUpForm.setHni(mailbagId.substring(23,24));
						errorHandlingPopUpForm.setRi(mailbagId.substring(24,25));
						double covertedWeight =unitConvertion(UnitConstants.MAIL_WGT,"H",weightUnit,Double.parseDouble(mailbagId.substring(25,29)));
    					/*double conDisplayWeight=0;
    					if(UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(weightUnit)){
    						 conDisplayWeight=round(covertedWeight,1); 
    					}
    					else {
    						conDisplayWeight=round(covertedWeight,0);
    					} */
    					mailWt=new Measure(UnitConstants.MAIL_WGT,covertedWeight); 
						mailWgt=String.valueOf(mailWt.getSystemValue());
						errorHandlingPopUpForm.setWeightMeasure(mailWt);	
						errorHandlingPopUpForm.setWeight(String.valueOf(mailWgt));
					}
					else if(mailbagId.length()==10||mailbagId.length()==12 && isValidMailtag(mailbagId.length())){
						String routIndex=mailbagId.substring(4,8).toUpperCase();
						String org=null;
						String dest=null;
						String mailOrigin="";
						String mailDest="";
						Collection<RoutingIndexVO> routingIndexVOs=new ArrayList<RoutingIndexVO>();
						RoutingIndexVO routingIndexFilterVO=new RoutingIndexVO() ;
						routingIndexFilterVO.setRoutingIndex(routIndex);
						routingIndexFilterVO.setCompanyCode(companyCode);
						try {
							routingIndexVOs=new MailTrackingDefaultsDelegate().findRoutingIndex(routingIndexFilterVO);
						} catch (BusinessDelegateException businessDelegateException) {
							handleDelegateException(businessDelegateException);
						}
						exchangeOfficeMap=new HashMap<String,String>();
						if(routingIndexVOs.size()>0){
						for(RoutingIndexVO routingIndexVO:routingIndexVOs){
						if(routingIndexVO!=null&&routingIndexVO.getRoutingIndex()!=null){
							 org=routingIndexVO.getOrigin();
							 dest=routingIndexVO.getDestination();
							try {
								exchangeOfficeMap=new MailTrackingDefaultsDelegate().findOfficeOfExchangeForPA(companyCode,findSystemParameterValue(USPS_DOMESTIC_PA));
							} catch (BusinessDelegateException businessDelegateException) {
								handleDelegateException(businessDelegateException);
							}
							if(exchangeOfficeMap!=null && !exchangeOfficeMap.isEmpty()){
					    		if(exchangeOfficeMap.containsKey(org)){		
					    			 mailOrigin = exchangeOfficeMap.get(org);
								     errorHandlingPopUpForm.setOoe(mailOrigin.toUpperCase());
					    		}
					    		if(exchangeOfficeMap.containsKey(dest)){
					    			mailDest = exchangeOfficeMap.get(dest);
					    		}
                                    errorHandlingPopUpForm.setDoe(mailDest.toUpperCase());
					    		}
							
							String category="B";
							errorHandlingPopUpForm.setCategory(category.toUpperCase());
							
							String mailClass=mailbagId.substring(3,4);
							String subClass = mailClass+"X";
							errorHandlingPopUpForm.setSubclass(subClass.toUpperCase());
							
							int lastTwoDigits = Calendar.getInstance().get(Calendar.YEAR) % 100;
							String lastDigitOfYear = String.valueOf(lastTwoDigits).substring(1,2);
							String year=lastDigitOfYear;
							errorHandlingPopUpForm.setYear(year);
							String companycode=logonAttributes.getCompanyCode();
							MailbagVO newMailbagVO=new MailbagVO();
							newMailbagVO.setCompanyCode(companycode);
							newMailbagVO.setYear(Integer.parseInt(errorHandlingPopUpForm.getYear()));
							try {
								newMailbagVO =new MailTrackingDefaultsDelegate().findDsnAndRsnForMailbag(newMailbagVO);
							} catch (BusinessDelegateException businessDelegateException) {
								handleDelegateException(businessDelegateException);
							}
							if(newMailbagVO.getDespatchSerialNumber()!=null){
								String dsn=newMailbagVO.getDespatchSerialNumber();	
								errorHandlingPopUpForm.setDsn(dsn.toUpperCase());
								}
								
								if(newMailbagVO.getReceptacleSerialNumber()!=null){
								String rsn=newMailbagVO.getReceptacleSerialNumber();
								errorHandlingPopUpForm.setRsn(rsn.toUpperCase());
								}
								
								String hni="9";
								errorHandlingPopUpForm.setHni(hni.toUpperCase());
								
								String ri="9";
								errorHandlingPopUpForm.setRi(ri.toUpperCase());
								if(mailbagId.length()==12){
									double covertedWeight =unitConvertion(UnitConstants.MAIL_WGT,"L",weightUnit,Double.parseDouble(mailbagId.substring(10,12)));
									/*double conDisplayWeight=0;
			    					if(UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(weightUnit)){
			    						 conDisplayWeight=round(covertedWeight,1); 
			    					}
			    					else {
			    						conDisplayWeight=round(covertedWeight,0);
			    					} */
			    					mailWt=new Measure(UnitConstants.MAIL_WGT,covertedWeight); 
									mailWgt=String.valueOf(mailWt.getRoundedSystemValue());
									errorHandlingPopUpForm.setWeightMeasure(mailWt);	
									errorHandlingPopUpForm.setWeight(String.valueOf(mailWgt));
								}
								
						  }
						  /*else{
							    errorHandlingPopUpForm.setInvalidMailbagId(true);
						     }*/
						  }
						}
						else{
						    errorHandlingPopUpForm.setOoe("");
							errorHandlingPopUpForm.setDoe("");
							errorHandlingPopUpForm.setCategory("");
							errorHandlingPopUpForm.setSubclass("");
							errorHandlingPopUpForm.setYear("");
							errorHandlingPopUpForm.setDsn("");
							errorHandlingPopUpForm.setRsn("");
							errorHandlingPopUpForm.setHni("");
							errorHandlingPopUpForm.setRi("");
							errorHandlingPopUpForm.setWeight("");
							//errorHandlingPopUpForm.setInvalidMailbagId(true);
					     }
					}
					else if(mailbagId.length()!=0 ){
						    errorHandlingPopUpForm.setInvalidMailbagId(true);
						
					}
						
		    	}
		    	else{
					StringBuilder mailTagId=new StringBuilder();
					double weight=errorHandlingPopUpForm.getWeightMeasure().getDisplayValue();			
					double weighttoappend =unitConvertion(UnitConstants.MAIL_WGT,weightUnit,"H",weight);
					mailWt= new Measure(UnitConstants.MAIL_WGT,weighttoappend); 
					errorHandlingPopUpForm.setWeightMeasure(mailWt);
					if(mailWgt!=null){
    					if((weighttoappend<1)||(weighttoappend>9999)){
    						invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.invalidweight"));
							invocationContext.target = TARGET_SUCCESS;
							return;
    					}
    					}
    					//double roundedWeight=round(weighttoappend,0);
    					String wgt=weightFormatter(mailWt.getRoundedSystemValue());
					mailTagId.append(errorHandlingPopUpForm.getOoe().toUpperCase())
							 .append(errorHandlingPopUpForm.getDoe().toUpperCase())
							 .append(errorHandlingPopUpForm.getCategory().toUpperCase())
							 .append(errorHandlingPopUpForm.getSubclass().toUpperCase()).append(errorHandlingPopUpForm.getYear().toUpperCase())
							 .append(errorHandlingPopUpForm.getDsn().toUpperCase()).append(errorHandlingPopUpForm.getRsn().toUpperCase())
							 .append(errorHandlingPopUpForm.getHni().toUpperCase()).append(errorHandlingPopUpForm.getRi().toUpperCase())
							 .append(wgt);
					String mailtag=mailTagId.toString();
					errorHandlingPopUpForm.setMailBag(mailtag);	
				}
		    	invocationContext.target = TARGET_SUCCESS;		 	
		       	
		    	log.exiting("CLASS_NAME","execute");
				
	       
		 }
			/**
			 * @author A-7540
			 * @param mailbagId
			 * @return
			 */
		 private boolean isValidMailtag(int mailtagLength)
			{
				boolean valid=false;
				String systemParameterValue=null;
				try {
					systemParameterValue = findSystemParameterValue(NO_OF_CHAR_ALLOWED_FOR_MAILTAG);//modified by a-7871 for ICRD-218529
				} catch (BusinessDelegateException e) {
					log.log(Log.SEVERE, "System Exception Caught");
				}
				if(systemParameterValue!=null && !systemParameterValue.equals("NA"))
				{
				 String[] systemParameterVal = systemParameterValue.split(","); 
			        for (String a : systemParameterVal) 
			        {
			        	if(Integer.valueOf(a)==mailtagLength)
			        	{
			        		valid=true;
			        		break;
			        	}
			        }
				}
				return valid;
			}
					/**
					 * 
					 * 	Method		:	PopulateMailTagDetailsCommand.findSystemParameterValue
					 *	Added by 	:	A-7540 on 11-Oct-2019
					 * 	Used for 	:
					 *	Parameters	:	@param syspar
					 *	Parameters	:	@return
					 *	Parameters	:	@throws SystemException 
					 *	Return type	: 	String
					 * @throws BusinessDelegateException 
					 */
					private String findSystemParameterValue(String syspar)
							throws BusinessDelegateException {
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
					/**
					 * @author A-7540
					 * @param mailbagId
					 * @return
					 */
			private boolean validateMailTagFormat(String mailbagId){
				boolean isValid=false;
				String mailYr=mailbagId.substring(15,16);
				String mailDSN=mailbagId.substring(16,20);
				String mailRSN=mailbagId.substring(20,23);
				String mailHNI=mailbagId.substring(23,24);
				String mailRI=mailbagId.substring(24,25);
				String mailWt=mailbagId.substring(25,29);
				if(mailYr.matches(INT_REGEX) && mailYr.length()==1&&
						mailDSN.matches(INT_REGEX) && mailDSN.length()==4&&
						mailRSN.matches(INT_REGEX) && mailRSN.length()==3&&
						mailHNI.matches(INT_REGEX) && mailHNI.length()==1&&
						mailRI.matches(INT_REGEX) && mailRI.length()==1&&
						mailWt.matches(INT_REGEX) && mailWt.length()==4){
					isValid=true;
				}
				return isValid;
				
			
							}
			/**
			 * 
			 * @param unitType
			 * @param fromUnit
			 * @param toUnit
			 * @param fromValue
			 * @return
			 */
			private double unitConvertion(String unitType,String fromUnit,String toUnit,double fromValue){
				UnitConversionNewVO unitConversionVO= null;
				try {
					unitConversionVO=UnitFormatter.getUnitConversionForToUnit(unitType,fromUnit,toUnit, fromValue);
				} catch (UnitException e) {
					e.getMessage();
				}
				double convertedValue = unitConversionVO.getToValue();
				return convertedValue;
				} 
			/**
			 * 
			 * @param weight
			 * @return
			 */
			private String weightFormatter(Double weight) {
				String weightString=String.valueOf(weight.intValue());	// added by A-8353 for ICRD-274933
		        String weights[] = weightString.split("[.]");
		        StringBuilder flatWeight = new StringBuilder(weights[0]);
		        /*if(!"0".equals(weights[1])){    
		        	flatWeight.append(weights[1]);//commented by A-8353 for ICRD-274933
		        }*/
		        if (flatWeight.length() >= 4) {
		            return flatWeight.substring(0, 4);
		        }
				StringBuilder zeros = new StringBuilder();
				int zerosRequired = 4 - flatWeight.length();
				for(int i = 0; i < zerosRequired; i++) {
				    zeros.append("0");
				}
				return zeros.append(flatWeight).toString();
							}
			
			private Collection getStationParameterCodes()
		    {
		        Collection stationParameterCodes = new ArrayList();
		        stationParameterCodes.add(STNPAR_DEFUNIT_WGT);
		        return stationParameterCodes;
}
	
}
