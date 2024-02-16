/*
 * PopulateMailTagDetailsCommand.java Created on Jun 07 2017
 *
 * Copyright 2017 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
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
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * Populate Mail Tag Details
 * @author a-6245
 *
 */
public class PopulateMailTagDetailsCommand extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	      
	   private static final String CLASS_NAME = "PopulateMailTagDetailsCommand";

	   private static final String TARGET_SUCCESS = "success";
	   
	   private static final String INT_REGEX = "[0-9]+";
	    private static final String STNPAR_DEFUNIT_VOL = "station.defaults.unit.volume";
		 private Map<String,String> exchangeOfficeMap;
		 
		 private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	   
	   /**
	    * 
	    *	Overriding Method	:@see com.ibsplc.icargo.framework.web.command.Command
	    *						#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	    *	Added by 			: A-6245 on 07-Jun-2017
	    * 	Used for 	:
	    *	Parameters	:	@param invocationContext
	    *	Parameters	:	@throws CommandInvocationException
	    */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering(CLASS_NAME,"execute");
	    	
	    	MailAcceptanceForm mailAcceptanceForm = (MailAcceptanceForm)invocationContext.screenModel;
 	    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
 	    	AreaDelegate areaDelegate = new AreaDelegate();
 	    	Map stationParameters = null; 
 	    	String stationCode = logonAttributes.getStationCode();
	    	String companyCode=logonAttributes.getCompanyCode();
	    	try {
				stationParameters = areaDelegate.findStationParametersByCode(companyCode, stationCode, getStationParameterCodes());
			} catch (BusinessDelegateException e1) {
				
				e1.getMessage();
			}
	    	String[] mailbagId = mailAcceptanceForm.getMailbagId();
	    	String[] mailOpFlag = mailAcceptanceForm.getMailOpFlag();
	    	String[] selectedMailTag=mailAcceptanceForm.getSelectMailTag();
	    	int length=mailbagId.length; 
	    	int index=Integer.parseInt(selectedMailTag[0]);
	    	MailbagVO mailbagVO=null;
	    	String[] mailOOE=new String[length];
	    	String[] mailDOE=new String[length];
	    	String[] mailCat=new String[length];
	    	String[] mailSC=new String[length];
	    	String[] mailYr=new String[length];
	    	String[] mailDSN=new String[length];
	    	String[] mailRSN=new String[length];
	    	String[] mailHNI=new String[length];
	    	String[] mailRI=new String[length];
	    	String[] mailWgt=new String[length];
	    	Measure[] mailWt=new Measure[length];//added by A-7371
	    	String[] weightUnit=mailAcceptanceForm.getWeightUnit(); //a-8353
	    	String[] mailVol=new String[length];
	    	Measure[] mailVolume=new Measure[length];//a-8353
	    	String[] mailId=new String[length];
	    	mailAcceptanceForm.setInValidId(false);
	    		if(MailConstantsVO.OPERATION_FLAG_INSERT.equals(mailOpFlag[index])){
	    			if(mailbagId[index]!=null&&!mailbagId[index].isEmpty()){
						try{
							mailbagVO =new MailTrackingDefaultsDelegate().findMailDetailsForMailTag(
									companyCode,mailbagId[index].toUpperCase()) ;
				  		}catch (BusinessDelegateException businessDelegateException) {
			  				handleDelegateException(businessDelegateException);
			  			}
						if(mailbagVO!=null&&mailbagVO.getMailbagId()!=null&&
								!mailbagVO.getMailbagId().isEmpty()){
							mailOOE[index]=mailbagVO.getOoe();
							mailAcceptanceForm.setMailOOE(mailOOE);	
							mailAcceptanceForm.setOriginOE(mailOOE[index]);
							
							mailDOE[index]=mailbagVO.getDoe();
							mailAcceptanceForm.setMailDOE(mailDOE);	
							mailAcceptanceForm.setDestinationOE(mailDOE[index]);
							
							mailCat[index]=mailbagVO.getMailCategoryCode();
							mailAcceptanceForm.setMailCat(mailCat);	
							mailAcceptanceForm.setCategory(mailCat[index]);
							
							mailSC[index]=mailbagVO.getMailSubclass();
							mailAcceptanceForm.setMailSC(mailSC);
							mailAcceptanceForm.setSubClass(mailSC[index]);
							
							mailYr[index]=String.valueOf(mailbagVO.getYear());
							mailAcceptanceForm.setMailYr(mailYr);
							mailAcceptanceForm.setYear(mailYr[index]);
							
							mailDSN[index]=mailbagVO.getDespatchSerialNumber();
							mailAcceptanceForm.setMailDSN(mailDSN);	
							mailAcceptanceForm.setDsn(mailDSN[index]);
							
							mailRSN[index]=mailbagVO.getReceptacleSerialNumber();
							mailAcceptanceForm.setMailRSN(mailRSN);	
							mailAcceptanceForm.setRsn(mailRSN[index]);
							
							mailHNI[index]=mailbagVO.getHighestNumberedReceptacle();
							mailAcceptanceForm.setMailHNI(mailHNI);	
							mailAcceptanceForm.setHni(mailHNI[index]);
							
							mailRI[index]=mailbagVO.getRegisteredOrInsuredIndicator();
							mailAcceptanceForm.setMailRI(mailRI);	
							mailAcceptanceForm.setRi(mailRI[index]);
							//Added by a-8353 for ICRD-274933 starts 
							mailbagId[index]=mailbagVO.getMailbagId();
							if(mailbagId[index].length()==12){  
								mailWt[index]=new Measure(UnitConstants.MAIL_WGT, Double.parseDouble(mailbagVO.getMailbagId().substring(10,12)));
								double convertedWeight = unitConvertion(UnitConstants.MAIL_WGT,"L",weightUnit[index],Double.parseDouble(mailbagVO.getMailbagId().substring(10,12)));
		    					double conDisplayWeight=0;
		    					if(UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(weightUnit[index])){
		    						 conDisplayWeight=round(convertedWeight,1); 
		    					}
		    					else {
		    						conDisplayWeight=round(convertedWeight,0);
		    					} 
								mailWt[index]=new Measure(UnitConstants.MAIL_WGT,0.0,conDisplayWeight,weightUnit[index]); 
								mailWgt[index]=String.valueOf(conDisplayWeight);
								mailAcceptanceForm.setMailWtMeasure(mailWt);	//added by A-7371
								mailAcceptanceForm.setWgtMeasure(mailWt[index]);
								mailAcceptanceForm.setWgt(String.valueOf(conDisplayWeight));
								double weightForVol=unitConvertion(UnitConstants.MAIL_WGT,mailAcceptanceForm.getWgtMeasure().getDisplayUnit(),UnitConstants.WEIGHT_UNIT_KILOGRAM,conDisplayWeight);
								double ActVol=(weightForVol/Double.parseDouble(mailAcceptanceForm.getDensity()));
		    					String stationVolumeUnit = (String)stationParameters.get(STNPAR_DEFUNIT_VOL);
		    					double stationVolume=unitConvertion(UnitConstants.VOLUME,UnitConstants.VOLUME_UNIT_CUBIC_METERS,stationVolumeUnit,ActVol);
		    					mailVolume[index]=new Measure(UnitConstants.VOLUME,0,stationVolume,stationVolumeUnit);
		    					mailVol[index]=String.valueOf(stationVolume);
							    mailAcceptanceForm.setMailVolumeMeasure(mailVolume);
							    mailAcceptanceForm.setMailVolume(mailVol);
							    mailAcceptanceForm.setVol(mailVol[index]);//Added by a-8353 for ICRD-274933 ends 

								}
							
							//Added by a-8353 for ICRD-274933 starts  
							else{
	    					double convertedWeight = unitConvertion(UnitConstants.MAIL_WGT,"H",weightUnit[index],Double.parseDouble(mailbagId[index].substring(25,29)));
	    					double conDisplayWeight=0;
	    					if(UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(weightUnit[index])){
	    						 conDisplayWeight=round(convertedWeight,1); 
	    					}
	    					else {
	    						conDisplayWeight=round(convertedWeight,0);
	    					} 
							mailWt[index]=new Measure(UnitConstants.MAIL_WGT,0.0,conDisplayWeight,weightUnit[index]); 
							mailWgt[index]=String.valueOf(conDisplayWeight);
							mailAcceptanceForm.setMailWtMeasure(mailWt);	//added by A-7371
							mailAcceptanceForm.setWgtMeasure(mailWt[index]);
							mailAcceptanceForm.setWgt(String.valueOf(conDisplayWeight));
							double weightForVol=unitConvertion(UnitConstants.MAIL_WGT,mailAcceptanceForm.getWgtMeasure().getDisplayUnit(),UnitConstants.WEIGHT_UNIT_KILOGRAM,conDisplayWeight);
							double ActVol=(weightForVol/Double.parseDouble(mailAcceptanceForm.getDensity()));
	    					String stationVolumeUnit = (String)stationParameters.get(STNPAR_DEFUNIT_VOL);
	    					double stationVolume=unitConvertion(UnitConstants.VOLUME,UnitConstants.VOLUME_UNIT_CUBIC_METERS,stationVolumeUnit,ActVol);
	    					mailVolume[index]=new Measure(UnitConstants.VOLUME,0,stationVolume,stationVolumeUnit);
	    					mailVol[index]=String.valueOf(stationVolume);
						    mailAcceptanceForm.setMailVolumeMeasure(mailVolume);
						    mailAcceptanceForm.setMailVolume(mailVol);
						    mailAcceptanceForm.setVol(mailVol[index]);//Added by a-8353 for ICRD-274933 ends 
							}
							/*mailVol[index]=String.valueOf(mailbagVO.getVolume());
							mailAcceptanceForm.setMailVolume(mailVol);	
							mailAcceptanceForm.setVol(mailVol[index]);*/
							
							mailId[index]=mailbagVO.getMailbagId();
							mailAcceptanceForm.setMailbagId(mailId);	
							mailAcceptanceForm.setMailId(mailId[index]);
						}else if(mailbagId[index].length()==29 && validateMailTagFormat(mailbagId[index])){//modified by A-7371 as part 0f ICRD-224610
								//if(validateMailTagFormat(mailbagId[index])){
							mailOOE[index]=mailbagId[index].substring(0, 6);
							mailAcceptanceForm.setMailOOE(mailOOE);	
							mailAcceptanceForm.setOriginOE(mailOOE[index].toUpperCase());
							
							mailDOE[index]=mailbagId[index].substring(6,12 );
							mailAcceptanceForm.setMailDOE(mailDOE);	
							mailAcceptanceForm.setDestinationOE(mailDOE[index].toUpperCase());
							
							mailCat[index]=mailbagId[index].substring(12,13 );
							mailAcceptanceForm.setMailCat(mailCat);	
							mailAcceptanceForm.setCategory(mailCat[index].toUpperCase());
							
							mailSC[index]=mailbagId[index].substring(13,15);
							mailAcceptanceForm.setMailSC(mailSC);
							mailAcceptanceForm.setSubClass(mailSC[index].toUpperCase());
							
							mailYr[index]=mailbagId[index].substring(15,16);
							mailAcceptanceForm.setMailYr(mailYr);
							mailAcceptanceForm.setYear(mailYr[index]);
							
							mailDSN[index]=mailbagId[index].substring(16,20);
							mailAcceptanceForm.setMailDSN(mailDSN);	
							mailAcceptanceForm.setDsn(mailDSN[index].toUpperCase());
							
							mailRSN[index]=mailbagId[index].substring(20,23);
							mailAcceptanceForm.setMailRSN(mailRSN);	
							mailAcceptanceForm.setRsn(mailRSN[index].toUpperCase());
							
							mailHNI[index]=mailbagId[index].substring(23,24);
							mailAcceptanceForm.setMailHNI(mailHNI);	
							mailAcceptanceForm.setHni(mailHNI[index].toUpperCase());
							
							mailRI[index]=mailbagId[index].substring(24,25);
							mailAcceptanceForm.setMailRI(mailRI);	
							mailAcceptanceForm.setRi(mailRI[index].toUpperCase());
							//Added by a-8353 for ICRD-274933 starts    
	    					double convertedWeight = unitConvertion(UnitConstants.MAIL_WGT,"H",weightUnit[index],Double.parseDouble(mailbagId[index].substring(25,29)));
	    					double conDisplayWeight=0;
	    					if(UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(weightUnit[index])){
	    						 conDisplayWeight=round(convertedWeight,1); 
	    					}
	    					else {
	    						conDisplayWeight=round(convertedWeight,0);
	    					}
							mailWt[index]=new Measure(UnitConstants.MAIL_WGT,0.0,conDisplayWeight,weightUnit[index]); 
							mailWgt[index]=String.valueOf(conDisplayWeight);
							mailAcceptanceForm.setMailWtMeasure(mailWt);	//added by A-7371
							mailAcceptanceForm.setWgtMeasure(mailWt[index]);
							mailAcceptanceForm.setWgt(String.valueOf(conDisplayWeight));
							double weightForVol=unitConvertion(UnitConstants.MAIL_WGT,mailAcceptanceForm.getWgtMeasure().getDisplayUnit(),UnitConstants.WEIGHT_UNIT_KILOGRAM,conDisplayWeight);
							double ActVol=(weightForVol/Double.parseDouble(mailAcceptanceForm.getDensity()));
	    					String stationVolumeUnit = (String)stationParameters.get(STNPAR_DEFUNIT_VOL);
	    					double stationVolume=unitConvertion(UnitConstants.VOLUME,UnitConstants.VOLUME_UNIT_CUBIC_METERS,stationVolumeUnit,ActVol);
	    					mailVolume[index]=new Measure(UnitConstants.VOLUME,0,stationVolume,stationVolumeUnit);
	    					mailVol[index]=String.valueOf(stationVolume);
						    mailAcceptanceForm.setMailVolumeMeasure(mailVolume);
						    mailAcceptanceForm.setMailVolume(mailVol);
						    mailAcceptanceForm.setVol(mailVol[index]);//Added by a-8353 for ICRD-274933 ends 
							mailId[index]=mailbagId[index];
							mailAcceptanceForm.setMailbagId(mailId);	
							mailAcceptanceForm.setMailId(mailId[index].toUpperCase());
								//}
							}else if(mailbagId[index].length()==10||mailbagId[index].length()==12){
								String routIndex=mailbagId[index].substring(4,8).toUpperCase();
								String org=null;
								String dest=null;
								Collection<RoutingIndexVO> routingIndexVOs=new ArrayList<RoutingIndexVO>();
								RoutingIndexVO routingIndexFilterVO=new RoutingIndexVO() ;
								routingIndexFilterVO.setRoutingIndex(routIndex);
								routingIndexFilterVO.setCompanyCode(companyCode);
								try {
									routingIndexVOs=new MailTrackingDefaultsDelegate().findRoutingIndex(routingIndexFilterVO);
								} catch (BusinessDelegateException businessDelegateException) {
									// TODO Auto-generated catch block
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
										// TODO Auto-generated catch block
										handleDelegateException(businessDelegateException);;
									} catch (SystemException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									if(exchangeOfficeMap!=null && !exchangeOfficeMap.isEmpty()){
							    		if(exchangeOfficeMap.containsKey(org)){
							    			mailOOE[index]=exchangeOfficeMap.get(org);
											mailAcceptanceForm.setMailOOE(mailOOE);	
											mailAcceptanceForm.setOriginOE(mailOOE[index].toUpperCase());
							    		}
							    				if(exchangeOfficeMap.containsKey(dest)){
									
									mailDOE[index]=exchangeOfficeMap.get(dest);
									mailAcceptanceForm.setMailDOE(mailDOE);	
									mailAcceptanceForm.setDestinationOE(mailDOE[index].toUpperCase());
							    		}
									}
									mailCat[index]="B";
									mailAcceptanceForm.setMailCat(mailCat);	
									mailAcceptanceForm.setCategory(mailCat[index].toUpperCase());
									
									String mailClass=mailbagId[index].substring(3,4);
									mailSC[index]=mailClass+"X";
									mailAcceptanceForm.setMailSC(mailSC);
									mailAcceptanceForm.setSubClass(mailSC[index].toUpperCase());
									
									int lastTwoDigits = Calendar.getInstance().get(Calendar.YEAR) % 100;
									String lastDigitOfYear = String.valueOf(lastTwoDigits).substring(1,2);
									mailYr[index]=lastDigitOfYear;
									mailAcceptanceForm.setMailYr(mailYr);
									mailAcceptanceForm.setYear(mailYr[index]);
									String companycode=logonAttributes.getCompanyCode();
									//String despacthNumber=null;
									MailbagVO newMailbagVO=new MailbagVO();
									newMailbagVO.setCompanyCode(companycode);
									newMailbagVO.setYear(Integer.parseInt(mailAcceptanceForm.getYear()));
									try {
										newMailbagVO =new MailTrackingDefaultsDelegate().findDsnAndRsnForMailbag(newMailbagVO);
									} catch (BusinessDelegateException businessDelegateException) {
										// TODO Auto-generated catch block
										handleDelegateException(businessDelegateException);
									}
									/*String rsn=null;
									try {
										rsn = generateReceptacleSerialNumber(despacthNumber,mailAcceptanceForm,companycode);
									} catch (SystemException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							    	if(rsn.length()>3){
									
							    		try {
											generateDespatchSerialNumber(MailConstantsVO.FLAG_NO,mailAcceptanceForm,companycode);
										} catch (SystemException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
							    		try {
											despacthNumber=(generateDespatchSerialNumber(MailConstantsVO.FLAG_YES,mailAcceptanceForm,companycode));
										} catch (SystemException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
							    		try {
											rsn=generateReceptacleSerialNumber(despacthNumber,mailAcceptanceForm,companycode);
										} catch (SystemException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}				
										
							    	}
									*/
									if(newMailbagVO.getDespatchSerialNumber()!=null){
									mailDSN[index]=newMailbagVO.getDespatchSerialNumber();
									mailAcceptanceForm.setMailDSN(mailDSN);	
									mailAcceptanceForm.setDsn(mailDSN[index].toUpperCase());
									}
									
									if(newMailbagVO.getReceptacleSerialNumber()!=null){
									mailRSN[index]=newMailbagVO.getReceptacleSerialNumber();
									mailAcceptanceForm.setMailRSN(mailRSN);	
									mailAcceptanceForm.setRsn(mailRSN[index].toUpperCase());
									}
									
									mailHNI[index]="9";
									mailAcceptanceForm.setMailHNI(mailHNI);	
									mailAcceptanceForm.setHni(mailHNI[index].toUpperCase());
									
									mailRI[index]="9";
									mailAcceptanceForm.setMailRI(mailRI);	
									mailAcceptanceForm.setRi(mailRI[index].toUpperCase());
									
									if(mailbagId[index].length()==12){
									mailWt[index]=new Measure(UnitConstants.MAIL_WGT, Double.parseDouble(mailbagId[index].substring(11,12)));
									double convertedWeight = unitConvertion(UnitConstants.MAIL_WGT,"L",weightUnit[index],Double.parseDouble(mailbagId[index].substring(11,12)));
			    					double conDisplayWeight=0;
			    					if(UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(weightUnit[index])){
			    						 conDisplayWeight=round(convertedWeight,1); 
			    					}
			    					else {
			    						conDisplayWeight=round(convertedWeight,0);
			    					} 
									mailWt[index]=new Measure(UnitConstants.MAIL_WGT,0.0,conDisplayWeight,weightUnit[index]); 
									mailWgt[index]=String.valueOf(conDisplayWeight);
									mailAcceptanceForm.setMailWtMeasure(mailWt);	//added by A-7371
									mailAcceptanceForm.setWgtMeasure(mailWt[index]);
									mailAcceptanceForm.setWgt(String.valueOf(conDisplayWeight));
									double weightForVol=unitConvertion(UnitConstants.MAIL_WGT,mailAcceptanceForm.getWgtMeasure().getDisplayUnit(),UnitConstants.WEIGHT_UNIT_KILOGRAM,conDisplayWeight);
									double ActVol=(weightForVol/Double.parseDouble(mailAcceptanceForm.getDensity()));
			    					String stationVolumeUnit = (String)stationParameters.get(STNPAR_DEFUNIT_VOL);
			    					double stationVolume=unitConvertion(UnitConstants.VOLUME,UnitConstants.VOLUME_UNIT_CUBIC_METERS,stationVolumeUnit,ActVol);
			    					mailVolume[index]=new Measure(UnitConstants.VOLUME,0,stationVolume,stationVolumeUnit);
			    					mailVol[index]=String.valueOf(stationVolume);
								    mailAcceptanceForm.setMailVolumeMeasure(mailVolume);
								    mailAcceptanceForm.setMailVolume(mailVol);
								    mailAcceptanceForm.setVol(mailVol[index]);//Added by a-8353 for ICRD-274933 ends 

									}
									
									
									mailId[index]=mailbagId[index];
									mailAcceptanceForm.setMailbagId(mailId);	
									mailAcceptanceForm.setMailId(mailId[index].toUpperCase());	
							}else{
								mailAcceptanceForm.setInValidId(true);
							}
							}
							}else
							{
								mailAcceptanceForm.setInValidId(true);
							}
							}
						else if(mailbagId[index].length()!=0 ){//added by A-7371 as part 0f ICRD-224610
								mailAcceptanceForm.setInValidId(true);
								
							}
							
						
	    			}else{
	    				if(validateForm(mailAcceptanceForm,index)){
		    				try{
		    					mailbagVO =new MailTrackingDefaultsDelegate().
		    							findMailbagIdForMailTag(constructMailbagVO(
		    							mailAcceptanceForm,index,companyCode)) ;
		    				}catch (BusinessDelegateException businessDelegateException) {
				  				handleDelegateException(businessDelegateException);
		    				}
		    				updateForm(mailAcceptanceForm,index);
		    				if(mailbagVO!=null){
			    				if(mailbagVO.getMailbagId()!=null&&
			    						!("").equals(mailbagVO.getMailbagId())){
									mailId[index]=mailbagVO.getMailbagId();
									mailAcceptanceForm.setMailbagId(mailId);	
									mailAcceptanceForm.setMailId(mailId[index]);
			    				}
		    				}else{
		    					//Added by a-8353 for ICRD-274933 starts
		    					double weight=mailAcceptanceForm.getMailWtMeasure()[index].getDisplayValue();			
		    					double weighttoappend =unitConvertion(UnitConstants.MAIL_WGT,weightUnit[index],"H",weight);
		    					mailWt[index]=new Measure(UnitConstants.MAIL_WGT,0.0,weight,weightUnit[index]); 
								mailAcceptanceForm.setMailWtMeasure(mailWt);	
								mailAcceptanceForm.setWgtMeasure(mailWt[index]);
		    					double weightForVol=unitConvertion(UnitConstants.MAIL_WGT,weightUnit[index],UnitConstants.WEIGHT_UNIT_KILOGRAM,weight);
		    					double ActVol=(weightForVol/Double.parseDouble(mailAcceptanceForm.getDensity()));
		    					String stationVolumeUnit = (String)stationParameters.get(STNPAR_DEFUNIT_VOL);
		    					double stationVol=unitConvertion(UnitConstants.VOLUME,UnitConstants.VOLUME_UNIT_CUBIC_METERS,stationVolumeUnit,ActVol);
		    					double stationVolume=round(stationVol,2);
		    					mailVolume[index]=new Measure(UnitConstants.VOLUME,0,stationVolume,stationVolumeUnit);
		    					if(mailWgt[index]!=null){
		    					if((weighttoappend<1)||(weighttoappend>9999)){
		    						invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.invalidweight"));
									invocationContext.target = TARGET_SUCCESS;
									return;
		    					}
		    					}
		    					double roundedWeight=round(weighttoappend,0);
		    					String wgt=weightFormatter(roundedWeight);
		    					//Added by a-8353 for ICRD-274933 ends
		    					StringBuilder mailTagId=new StringBuilder();
		    					mailTagId.append(mailAcceptanceForm.getOriginOE())
		    							 .append(mailAcceptanceForm.getDestinationOE())
		    							 .append(mailAcceptanceForm.getCategory())
										 .append(mailAcceptanceForm.getSubClass()).append(mailAcceptanceForm.getYear())
										 .append(mailAcceptanceForm.getDsn()).append(mailAcceptanceForm.getRsn())
										 .append(mailAcceptanceForm.getHni()).append(mailAcceptanceForm.getRi())
										 .append(wgt);
		    					mailId[index]=mailTagId.toString();
								mailAcceptanceForm.setMailbagId(mailId);	
								mailAcceptanceForm.setMailId(mailId[index]);
							    String DisplayWgt=String.valueOf(weight);//added by A-8353 for ICRD-274933 starts
							     mailAcceptanceForm.setWgt(DisplayWgt);
							     mailVol[index]=String.valueOf(stationVolume);
							     mailAcceptanceForm.setMailVolumeMeasure(mailVolume);
							     mailAcceptanceForm.setMailVolume(mailVol);
							     mailAcceptanceForm.setVol(mailVol[index]); //added by A-8353 for ICRD-274933 ends   
		    				    
		    				}
	    				}
	    			}
	    		}
	    	
	    		
					
					
			
			invocationContext.target = TARGET_SUCCESS;		 	
	       	
	    	log.exiting("CLASS_NAME","execute");
	    }
	    /**
	     *  Validate Mail tag format
	     * 	Method		:	PopulateMailTagDetailsCommand.validateMailTagFormat
	     *	Added by 	:	A-6245 on 13-Jun-2017
	     * 	Used for 	:
	     *	Parameters	:	@param mailbagId
	     *	Parameters	:	@return 
	     *	Return type	: 	boolean
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
		 * 	Method		:	PopulateMailTagDetailsCommand.validateForm
		 *	Added by 	:	a-6245 on 22-Jun-2017
		 * 	Used for 	:
		 *	Parameters	:	@param mailAcceptanceForm
		 *	Parameters	:	@param index
		 *	Parameters	:	@return 
		 *	Return type	: 	boolean
		 */
		private boolean validateForm(MailAcceptanceForm mailAcceptanceForm,
				int index){
			boolean isValid=false;
			if(mailAcceptanceForm.getMailOOE()[index]!=null&&
					!mailAcceptanceForm.getMailOOE()[index].isEmpty()&&
					mailAcceptanceForm.getMailDOE()[index]!=null&&
					!mailAcceptanceForm.getMailDOE()[index].isEmpty()&&
					mailAcceptanceForm.getMailCat()[index]!=null&&
					!mailAcceptanceForm.getMailCat()[index].isEmpty()&&
					mailAcceptanceForm.getMailSC()[index]!=null&&
					!mailAcceptanceForm.getMailSC()[index].isEmpty()&&
					mailAcceptanceForm.getMailYr()[index]!=null&&
					!mailAcceptanceForm.getMailYr()[index].isEmpty()&&
					mailAcceptanceForm.getMailDSN()[index]!=null&&
					!mailAcceptanceForm.getMailDSN()[index].isEmpty()&&
					mailAcceptanceForm.getMailRSN()[index]!=null&&
					!mailAcceptanceForm.getMailRSN()[index].isEmpty()&&
					mailAcceptanceForm.getMailHNI()[index]!=null&&
					!mailAcceptanceForm.getMailHNI()[index].isEmpty()&&
					mailAcceptanceForm.getMailRI()!=null&&
					!mailAcceptanceForm.getMailRI()[index].isEmpty()&&
					mailAcceptanceForm.getMailWtMeasure()!=null)
					//&&!mailAcceptanceForm.getMailWtMeasure()[index].isEmpty())
					{
				isValid=true;
				
			}

			return isValid;
			
		}
		/**
		 * 
		 * 	Method		:	PopulateMailTagDetailsCommand.constructMailbagVO
		 *	Added by 	:	a-6245 on 22-Jun-2017
		 * 	Used for 	:
		 *	Parameters	:	@param mailbagVO
		 *	Parameters	:	@param mailAcceptanceForm
		 *	Parameters	:	@param index
		 *	Parameters	:	@param companyCode
		 *	Parameters	:	@return 
		 *	Return type	: 	MailbagVO
		 */
		private MailbagVO constructMailbagVO(MailAcceptanceForm mailAcceptanceForm,
				int index,String companyCode){
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setCompanyCode(companyCode);
			mailbagVO.setOoe(mailAcceptanceForm.getMailOOE()[index].toUpperCase());
			mailbagVO.setDoe(mailAcceptanceForm.getMailDOE()[index].toUpperCase());
			mailbagVO.setMailCategoryCode(mailAcceptanceForm.getMailCat()[index].toUpperCase());
			mailbagVO.setMailSubclass(mailAcceptanceForm.getMailSC()[index].toUpperCase());
			mailbagVO.setYear(Integer.parseInt(mailAcceptanceForm.getMailYr()[index]));
			mailbagVO.setDespatchSerialNumber(mailAcceptanceForm.getMailDSN()[index].toUpperCase());
			mailbagVO.setReceptacleSerialNumber(mailAcceptanceForm.getMailRSN()[index].toUpperCase());
			mailbagVO.setHighestNumberedReceptacle(
					mailAcceptanceForm.getMailHNI()[index].toUpperCase());
			mailbagVO.setRegisteredOrInsuredIndicator(
					mailAcceptanceForm.getMailRI()[index].toUpperCase());
			Measure[] mailWt=mailAcceptanceForm.getMailWtMeasure();
			//double systemWt=Double.parseDouble(mailAcceptanceForm.getMailWt()[index])/10;
			//Measure wgt=new Measure(UnitConstants.WEIGHT,systemWt);
			//mailWt[index].setSystemValue(mailWt[index].getSystemValue());//added by A-7371
			mailbagVO.setWeight(mailWt[index]);
			return mailbagVO;
			
		}
		/**
		 * 
		 * 	Method		:	PopulateMailTagDetailsCommand.updateForm
		 *	Added by 	:	a-6245 on 28-Jun-2017
		 * 	Used for 	:
		 *	Parameters	:	@param mailAcceptanceForm
		 *	Parameters	:	@param index 
		 *	Return type	: 	void
		 */
		private void updateForm(MailAcceptanceForm mailAcceptanceForm,
				int index){
			mailAcceptanceForm.setOriginOE(mailAcceptanceForm.getMailOOE()[index].toUpperCase());
			mailAcceptanceForm.setDestinationOE(mailAcceptanceForm.getMailDOE()[index].toUpperCase());
			mailAcceptanceForm.setCategory(mailAcceptanceForm.getMailCat()[index].toUpperCase());
			mailAcceptanceForm.setSubClass(mailAcceptanceForm.getMailSC()[index].toUpperCase());
			mailAcceptanceForm.setYear(mailAcceptanceForm.getMailYr()[index]);
			mailAcceptanceForm.setDsn(mailAcceptanceForm.getMailDSN()[index].toUpperCase());
			mailAcceptanceForm.setRsn(mailAcceptanceForm.getMailRSN()[index].toUpperCase());	
			mailAcceptanceForm.setHni(mailAcceptanceForm.getMailHNI()[index].toUpperCase());	
			mailAcceptanceForm.setRi(mailAcceptanceForm.getMailRI()[index].toUpperCase());
			mailAcceptanceForm.setWgtMeasure(mailAcceptanceForm.getMailWtMeasure()[index]);
			
		}
		/**
		 * 
		 * 	Method		:	PopulateMailTagDetailsCommand.weightFormatter
		 *	Added by 	:	A-7371 on 16-Aug-2017
		 * 	Used for 	:
		 *	Parameters	:	@param weight
		 *	Parameters	:	@return 
		 *	Return type	: 	String
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
		/**
		 * 
		 * 	Method		:	PopulateMailTagDetailsCommand.findSystemParameterValue
		 *	Added by 	:	A-7531 on 30-Oct-2018
		 * 	Used for 	:
		 *	Parameters	:	@param syspar
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException 
		 *	Return type	: 	String
		 * @throws BusinessDelegateException 
		 */
		private String findSystemParameterValue(String syspar)
				throws SystemException, BusinessDelegateException {
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
	 * 	
	 * 	Method		:	PopulateMailTagDetailsCommand.generateDespatchSerialNumber
	 *	Added by 	:	A-7531 on 30-Oct-2018
	 * 	Used for 	:
	 *	Parameters	:	@param currentKey
	 *	Parameters	:	@param maibagVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	String
	 */
		/*private String generateDespatchSerialNumber(String currentKey,MailAcceptanceForm mailAcceptanceForm,String companycode)
				throws SystemException {
				String key=null;
				StringBuilder keyCondition = new StringBuilder();
				keyCondition.append(mailAcceptanceForm.getYear());
				Criterion criterion = KeyUtils.getCriterion(companycode,
						"DOM_USPS_DSN", keyCondition.toString());
				//Code to be modified once framework issue is fixed
				key=KeyUtils.getKey(criterion);
				if(MailConstantsVO.FLAG_YES.equals(currentKey)&&
						!"1".equals(key)){
					key=String.valueOf(Long.parseLong(key)-1);
					KeyUtils.resetKey(criterion, key);
				}
			return checkLength(key, 4);
		}*/
		/**
		 * 
		 * 	Method		:	PopulateMailTagDetailsCommand.generateReceptacleSerialNumber
		 *	Added by 	:	A-7531 on 30-Oct-2018
		 * 	Used for 	:
		 *	Parameters	:	@param dsn
		 *	Parameters	:	@param maibagVO
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException 
		 *	Return type	: 	String
		 */
	/*private String generateReceptacleSerialNumber(String dsn,MailAcceptanceForm mailAcceptanceForm,String companycode)
				throws SystemException {
				StringBuilder keyCondition = new StringBuilder();
				keyCondition.append(mailAcceptanceForm.getYear())
	            		.append(dsn);
				Criterion criterion = KeyUtils.getCriterion(companycode,
						"DOM_USPS_RSN", keyCondition.toString());
				String rsn = checkLength(KeyUtils.getKey(criterion),3);
				log.log(Log.FINEST, "***** rsn Generated ---- " + rsn);
			return rsn;
		}*/
/**
 * 
 * 	Method		:	PopulateMailTagDetailsCommand.checkLength
 *	Added by 	:	A-7531 on 30-Oct-2018
 * 	Used for 	:
 *	Parameters	:	@param key
 *	Parameters	:	@param maxLength
 *	Parameters	:	@return 
 *	Return type	: 	String
 */
	/*private String checkLength(String key,int maxLength){
		String modifiedKey = null;
		StringBuilder buildKey = new StringBuilder();
		modifiedKey = new StringBuilder().append(key).toString();
		int keyLength = modifiedKey.length();
		if(modifiedKey.length() < maxLength){
			int diff = maxLength - keyLength;
			String val = null;
			for(int i=0;i< diff;i++){
				val = buildKey.append("0").toString();
			}
			modifiedKey = 	new StringBuilder().append(val).append(key).toString();
		}
		return modifiedKey;
	}*/
	
		/**
		 * Added by :A-8353 
		 * @param val
		 * @param places
		 * @return
		  */
        	private double round(double val, int places) {
			long factor = (long) Math.pow(10, places);
			val = val * factor;
			long tmp = Math.round(val);
			return (double) tmp / factor;
		} 
		private double unitConvertion(String unitType,String fromUnit,String toUnit,double fromValue){
			UnitConversionNewVO unitConversionVO= null;
			try {
				unitConversionVO=UnitFormatter.getUnitConversionForToUnit(unitType,fromUnit,toUnit, fromValue);
			} catch (UnitException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			double convertedValue = unitConversionVO.getToValue();
			return convertedValue;
			}
	private Collection<String> getStationParameterCodes()
		  {
		    Collection stationParameterCodes = new ArrayList();
		  
		    stationParameterCodes.add(STNPAR_DEFUNIT_VOL);
		    return stationParameterCodes;
        }
}
