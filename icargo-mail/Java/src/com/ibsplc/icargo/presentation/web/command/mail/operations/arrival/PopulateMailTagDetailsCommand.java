/*
 * PopulateMailTagDetailsCommand.java Created on Jun 07 2017
 *
 * Copyright 2017 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;



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
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
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
	   
	   private Map<String,String> exchangeOfficeMap;
	   
	   private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	   private static final String STNPAR_DEFUNIT_VOL = "station.defaults.unit.volume";
	   
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
	    	
	    	MailArrivalForm mailArrivalForm = (MailArrivalForm)invocationContext.screenModel;
	    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    	String companyCode=logonAttributes.getCompanyCode();
	    	String[] mailbagId = mailArrivalForm.getMailbagId();
	    	String[] mailOpFlag = mailArrivalForm.getMailOpFlag();
	    	String[] selectedMailTag=mailArrivalForm.getSelectMailTag();
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
	    	Measure[] mailWt=new Measure[length];  // modified by A-8236 for ICRD-250569
	    	String[] mailVol=new String[length];
	    	String[] mailId=new String[length];
	    	String[] weightUnit= mailArrivalForm.getWeightUnit();
	    	Measure[] mailVolume = new Measure[length];
	    	AreaDelegate areaDelegate = new AreaDelegate();
            Map stationParameters = null; 
             String stationCode = logonAttributes.getStationCode();	                
             try {
                          stationParameters = areaDelegate.findStationParametersByCode(companyCode, stationCode, getStationParameterCodes());
                    } catch (BusinessDelegateException e1) {
                          
                          e1.getMessage();
                    }
	    	mailArrivalForm.setInValidId(false);
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
							mailArrivalForm.setMailOOE(mailOOE);	
							mailArrivalForm.setOriginOE(mailOOE[index]);
							
							mailDOE[index]=mailbagVO.getDoe();
							mailArrivalForm.setMailDOE(mailDOE);	
							mailArrivalForm.setDestinationOE(mailDOE[index]);
							
							mailCat[index]=mailbagVO.getMailCategoryCode();
							mailArrivalForm.setMailCat(mailCat);	
							mailArrivalForm.setCategory(mailCat[index]);
							
							mailSC[index]=mailbagVO.getMailSubclass();
							mailArrivalForm.setMailSC(mailSC);
							mailArrivalForm.setSubClass(mailSC[index]);
							
							mailYr[index]=String.valueOf(mailbagVO.getYear());
							mailArrivalForm.setMailYr(mailYr);
							mailArrivalForm.setYear(mailYr[index]);
							
							mailDSN[index]=mailbagVO.getDespatchSerialNumber();
							mailArrivalForm.setMailDSN(mailDSN);	
							mailArrivalForm.setDsn(mailDSN[index]);
							
							mailRSN[index]=mailbagVO.getReceptacleSerialNumber();
							mailArrivalForm.setMailRSN(mailRSN);	
							mailArrivalForm.setRsn(mailRSN[index]);
							
							mailHNI[index]=mailbagVO.getHighestNumberedReceptacle();
							mailArrivalForm.setMailHNI(mailHNI);	
							mailArrivalForm.setHni(mailHNI[index]);
							
							mailRI[index]=mailbagVO.getRegisteredOrInsuredIndicator();
							mailArrivalForm.setMailRI(mailRI);	
							mailArrivalForm.setRi(mailRI[index]);
							
							//Added by a-7540 for ICRD-274933 starts                
                     double convertedWeight = unitConvertion(UnitConstants.MAIL_WGT,"H",weightUnit[index], Double.parseDouble(mailbagId[index].substring(25,29)));

                     double conDisplayWeight=0;
                     if(UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(weightUnit[index])){
                             conDisplayWeight=round(convertedWeight,1); 
                     }
                     else {
                            conDisplayWeight=round(convertedWeight,0);
                     }
                     
                     double weightForVol=unitConvertion(UnitConstants.MAIL_WGT,weightUnit[index],UnitConstants.WEIGHT_UNIT_KILOGRAM,conDisplayWeight);
                     double ActVol=(weightForVol/Double.parseDouble(mailArrivalForm.getDensity()));
                     String stationVolumeUnit = (String)stationParameters.get(STNPAR_DEFUNIT_VOL);
                     double stationVolume=unitConvertion(UnitConstants.VOLUME,UnitConstants.VOLUME_UNIT_CUBIC_METERS,stationVolumeUnit,ActVol);
                     stationVolume = round(stationVolume,3);
                     mailVolume[index]=new Measure(UnitConstants.VOLUME,0,stationVolume,stationVolumeUnit);
                     mailVol[index]=String.valueOf(stationVolume);
                     mailArrivalForm.setMailVolumeMeasure(mailVolume);
                     mailArrivalForm.setMailVolume(mailVol);//Added by a-7540 for ICRD-274933 ends 

							if(mailbagVO.getWeight()!=null){
							//modified by A-8236 for ICRD-250569
							//int wgt=(int)(mailbagVO.getWeight().getSystemValue());//added by A-7371
							mailbagVO.getWeight().setDisplayValue(mailbagVO.getWeight().getRoundedDisplayValue());
							mailWt[index]=mailbagVO.getWeight();
							}		  
							//modified by A-8236 for ICRD-250569
							mailArrivalForm.setMailWtMeasure(mailWt);
							mailArrivalForm.setWgtMeasure(mailWt[index]);
							//mailArrivalForm.setMailWt(mailWt);	
							mailArrivalForm.setWgt(String.valueOf(conDisplayWeight));
							mailArrivalForm.setVol(mailVol[index]);
							
							mailId[index]=mailbagVO.getMailbagId();
							mailArrivalForm.setMailbagId(mailId);	
							mailArrivalForm.setMailId(mailId[index]);
						}else if(mailbagId[index].length()==29 && validateMailTagFormat(mailbagId[index])){//modified by A-7371 as part 0f ICRD-224610
								//if(validateMailTagFormat(mailbagId[index])){
							mailOOE[index]=mailbagId[index].substring(0, 6);
							mailArrivalForm.setMailOOE(mailOOE);	
							mailArrivalForm.setOriginOE(mailOOE[index].toUpperCase());
							
							mailDOE[index]=mailbagId[index].substring(6,12 );
							mailArrivalForm.setMailDOE(mailDOE);	
							mailArrivalForm.setDestinationOE(mailDOE[index].toUpperCase());
							
							mailCat[index]=mailbagId[index].substring(12,13 );
							mailArrivalForm.setMailCat(mailCat);	
							mailArrivalForm.setCategory(mailCat[index].toUpperCase());
							
							mailSC[index]=mailbagId[index].substring(13,15);
							mailArrivalForm.setMailSC(mailSC);
							mailArrivalForm.setSubClass(mailSC[index].toUpperCase());
							
							mailYr[index]=mailbagId[index].substring(15,16);
							mailArrivalForm.setMailYr(mailYr);
							mailArrivalForm.setYear(mailYr[index]);
							
							mailDSN[index]=mailbagId[index].substring(16,20);
							mailArrivalForm.setMailDSN(mailDSN);	
							mailArrivalForm.setDsn(mailDSN[index].toUpperCase());
							
							mailRSN[index]=mailbagId[index].substring(20,23);
							mailArrivalForm.setMailRSN(mailRSN);	
							mailArrivalForm.setRsn(mailRSN[index].toUpperCase());
							
							mailHNI[index]=mailbagId[index].substring(23,24);
							mailArrivalForm.setMailHNI(mailHNI);	
							mailArrivalForm.setHni(mailHNI[index].toUpperCase());
							
							mailRI[index]=mailbagId[index].substring(24,25);
							mailArrivalForm.setMailRI(mailRI);	
							mailArrivalForm.setRi(mailRI[index].toUpperCase());
							
							//Added by a-7540 for ICRD-274933 starts    
                     double convertedWeight = unitConvertion(UnitConstants.MAIL_WGT,"H",weightUnit[index], Double.parseDouble(mailbagId[index].substring(25,29)));
                     double conDisplayWeight=0;
                     if(UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(weightUnit[index])){
                             conDisplayWeight=round(convertedWeight,1); 
                     }
                     else {
                            conDisplayWeight=round(convertedWeight,0);
                     }
                     double weightForVol=unitConvertion(UnitConstants.MAIL_WGT,weightUnit[index],UnitConstants.WEIGHT_UNIT_KILOGRAM,conDisplayWeight);
                     double ActVol=(weightForVol/Double.parseDouble(mailArrivalForm.getDensity()));
                     String stationVolumeUnit = (String)stationParameters.get(STNPAR_DEFUNIT_VOL);
                     double stationVolume=unitConvertion(UnitConstants.VOLUME,UnitConstants.VOLUME_UNIT_CUBIC_METERS,stationVolumeUnit,ActVol);
                     stationVolume = round(stationVolume,3);
                     mailVolume[index]=new Measure(UnitConstants.VOLUME,0,stationVolume,stationVolumeUnit);
                     mailVol[index]=String.valueOf(stationVolume);
                     mailArrivalForm.setMailVolumeMeasure(mailVolume);
                     mailArrivalForm.setMailVolume(mailVol);
                     mailArrivalForm.setVol(mailVol[index]);

                     //Added by a-7540 for ICRD-274933 ends 
                     
							 //modified by A-8236 for ICRD-250569
							mailWt[index]=new Measure(UnitConstants.MAIL_WGT, Double.parseDouble(mailbagId[index].substring(25,29)));
							mailArrivalForm.setMailWtMeasure(mailWt);	
							mailArrivalForm.setWgtMeasure(mailWt[index]);
							mailArrivalForm.setWgt(String.valueOf(conDisplayWeight));
							
							mailId[index]=mailbagId[index];
							mailArrivalForm.setMailbagId(mailId);	
							mailArrivalForm.setMailId(mailId[index].toUpperCase());
								//}
							}
						else if(mailbagId[index].length()==10||mailbagId[index].length()==12){
							String routIndex=mailbagId[index].substring(4,8);
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
						    			mailArrivalForm.setMailOOE(mailOOE);	
						    			mailArrivalForm.setOriginOE(mailOOE[index].toUpperCase());
						    		}
						    				if(exchangeOfficeMap.containsKey(dest)){
								
								mailDOE[index]=exchangeOfficeMap.get(dest);
								mailArrivalForm.setMailDOE(mailDOE);	
								mailArrivalForm.setDestinationOE(mailDOE[index].toUpperCase());
						    		}
								}
								mailCat[index]="B";
								mailArrivalForm.setMailCat(mailCat);	
								mailArrivalForm.setCategory(mailCat[index].toUpperCase());
								
								String mailClass=mailbagId[index].substring(3,4);
								mailSC[index]=mailClass+"X";
								mailArrivalForm.setMailSC(mailSC);
								mailArrivalForm.setSubClass(mailSC[index].toUpperCase());
								
								int lastTwoDigits = Calendar.getInstance().get(Calendar.YEAR) % 100;
								String lastDigitOfYear = String.valueOf(lastTwoDigits).substring(1,2);
								mailYr[index]=lastDigitOfYear;
								mailArrivalForm.setMailYr(mailYr);
								mailArrivalForm.setYear(mailYr[index]);
								String companycode=logonAttributes.getCompanyCode();
							
								MailbagVO newMailbagVO=new MailbagVO();
								newMailbagVO.setCompanyCode(companycode);
								newMailbagVO.setYear(Integer.parseInt(mailArrivalForm.getYear()));
								try {
									newMailbagVO =new MailTrackingDefaultsDelegate().findDsnAndRsnForMailbag(newMailbagVO);
								} catch (BusinessDelegateException businessDelegateException) {
									// TODO Auto-generated catch block
									handleDelegateException(businessDelegateException);
								}
								
								if(newMailbagVO.getDespatchSerialNumber()!=null){
								mailDSN[index]=newMailbagVO.getDespatchSerialNumber();
								mailArrivalForm.setMailDSN(mailDSN);	
								mailArrivalForm.setDsn(mailDSN[index].toUpperCase());
								}
								
								if(newMailbagVO.getReceptacleSerialNumber()!=null){
								mailRSN[index]=newMailbagVO.getReceptacleSerialNumber();
								mailArrivalForm.setMailRSN(mailRSN);	
								mailArrivalForm.setRsn(mailRSN[index].toUpperCase());
								}
								
								mailHNI[index]="9";
								mailArrivalForm.setMailHNI(mailHNI);	
								mailArrivalForm.setHni(mailHNI[index].toUpperCase());
								
								mailRI[index]="9";
								mailArrivalForm.setMailRI(mailRI);	
								mailArrivalForm.setRi(mailRI[index].toUpperCase());
								
								if(mailbagId[index].length()==12){
								mailWt[index]=new Measure(UnitConstants.MAIL_WGT, Double.parseDouble(mailbagId[index].substring(11,12)));
								mailArrivalForm.setMailWtMeasure(mailWt);	
								mailArrivalForm.setWgtMeasure(mailWt[index]);
								mailArrivalForm.setWgt(mailbagId[index].substring(10,12));
								}
								
								
								mailId[index]=mailbagId[index];
								mailArrivalForm.setMailbagId(mailId);	
								mailArrivalForm.setMailId(mailId[index].toUpperCase());	
						}
							
						}
						}	
							else{
								mailArrivalForm.setInValidId(true);
							}
							
							
							}else if(mailbagId[index].length()!=0){//added by A-7371 as part 0f ICRD-224610
						
								mailArrivalForm.setInValidId(true);
								
							}
						
	    			}else{
	    				if(validateForm(mailArrivalForm,index)){
		    				try{
		    					mailbagVO =new MailTrackingDefaultsDelegate().
		    							findMailbagIdForMailTag(constructMailbagVO(mailArrivalForm,
		    									index,companyCode)) ;
		    				}catch (BusinessDelegateException businessDelegateException) {
				  				handleDelegateException(businessDelegateException);
		  				}
		    				updateForm(mailArrivalForm,index);
		    				if(mailbagVO!=null){
			    				if(mailbagVO.getMailbagId()!=null&&
			    						!("").equals(mailbagVO.getMailbagId())){
									mailId[index]=mailbagVO.getMailbagId();
									mailArrivalForm.setMailbagId(mailId);	
									mailArrivalForm.setMailId(mailId[index]);
			    				}
		    				}else{
		    					//Added by A-8236 for ICRD-250569
		    					//Modified by A-7540
		    			double weight=mailArrivalForm.getMailWtMeasure()[index].getDisplayValue();
		    			
		  
		    		    double weighttoappend = unitConvertion(UnitConstants.MAIL_WGT,weightUnit[index],"H",weight);
                        double weightForVol=unitConvertion(UnitConstants.MAIL_WGT,weightUnit[index],UnitConstants.WEIGHT_UNIT_KILOGRAM,weight);
                        double ActVol=(weightForVol/Double.parseDouble(mailArrivalForm.getDensity()));
                        String stationVolumeUnit = (String)stationParameters.get(STNPAR_DEFUNIT_VOL);
                        double stationVolume=unitConvertion(UnitConstants.VOLUME,UnitConstants.VOLUME_UNIT_CUBIC_METERS,stationVolumeUnit,ActVol);
                        stationVolume = round(stationVolume,3);
                        mailVolume[index]=new Measure(UnitConstants.VOLUME,0,stationVolume,stationVolumeUnit);
                        mailVol[index]=String.valueOf(stationVolume);
                        mailArrivalForm.setMailVolumeMeasure(mailVolume);
                        mailArrivalForm.setMailVolume(mailVol);
						mailArrivalForm.setVol(mailVol[index]);

				                  if(mailWt[index]!=null){
		    		    			if((weighttoappend<1)||(weighttoappend>9999)){
		    		    						invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.invalidweight"));
		    									invocationContext.target = TARGET_SUCCESS;
		    									return;
		    		    		  } 		
		    				}			
		    					String wgt=weightFormatter(weighttoappend);
		    					StringBuilder mailTagId=new StringBuilder();
		    					mailTagId.append(mailArrivalForm.getOriginOE())
		    							 .append(mailArrivalForm.getDestinationOE())
		    							 .append(mailArrivalForm.getCategory())
										 .append(mailArrivalForm.getSubClass()).append(mailArrivalForm.getYear())
										 .append(mailArrivalForm.getDsn()).append(mailArrivalForm.getRsn())
										 .append(mailArrivalForm.getHni()).append(mailArrivalForm.getRi())
										 .append(wgt);	 //modified by A-8236 for ICRD-250569
		    					mailId[index]=mailTagId.toString();
		    					mailArrivalForm.setMailbagId(mailId);	
		    					mailArrivalForm.setMailId(mailId[index]);
		    					mailArrivalForm.setWgt(String.valueOf(weight));
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
		 *	Parameters	:	@param mailArrivalForm
		 *	Parameters	:	@param index
		 *	Parameters	:	@return 
		 *	Return type	: 	boolean
		 */
		private boolean validateForm(MailArrivalForm mailArrivalForm,
				int index){
			boolean isValid=false;
			if(mailArrivalForm.getMailOOE()[index]!=null&&
					!mailArrivalForm.getMailOOE()[index].isEmpty()&&
					mailArrivalForm.getMailDOE()[index]!=null&&
					!mailArrivalForm.getMailDOE()[index].isEmpty()&&
					mailArrivalForm.getMailCat()[index]!=null&&
					!mailArrivalForm.getMailCat()[index].isEmpty()&&
					mailArrivalForm.getMailSC()[index]!=null&&
					!mailArrivalForm.getMailSC()[index].isEmpty()&&
					mailArrivalForm.getMailYr()[index]!=null&&
					!mailArrivalForm.getMailYr()[index].isEmpty()&&
					mailArrivalForm.getMailDSN()[index]!=null&&
					!mailArrivalForm.getMailDSN()[index].isEmpty()&&
					mailArrivalForm.getMailRSN()[index]!=null&&
					!mailArrivalForm.getMailRSN()[index].isEmpty()&&
					mailArrivalForm.getMailHNI()[index]!=null&&
					!mailArrivalForm.getMailHNI()[index].isEmpty()&&
					mailArrivalForm.getMailRI()!=null&&
					!mailArrivalForm.getMailRI()[index].isEmpty()&&
					mailArrivalForm.getMailWtMeasure()!=null  //modified by A-8236 for ICRD-250569
					/*mailArrivalForm.getMailWt()!=null&&
					!mailArrivalForm.getMailWt()[index].isEmpty()*/){
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
		 *	Parameters	:	@param mailArrivalForm
		 *	Parameters	:	@param index
		 *	Parameters	:	@param companyCode
		 *	Parameters	:	@return 
		 *	Return type	: 	MailbagVO
		 */
		private MailbagVO constructMailbagVO(MailArrivalForm mailArrivalForm,
				int index,String companyCode){
			MailbagVO mailbagVO=new MailbagVO();
			mailbagVO.setCompanyCode(companyCode);
			mailbagVO.setOoe(mailArrivalForm.getMailOOE()[index].toUpperCase());
			mailbagVO.setDoe(mailArrivalForm.getMailDOE()[index].toUpperCase());
			mailbagVO.setMailCategoryCode(mailArrivalForm.getMailCat()[index].toUpperCase());
			mailbagVO.setMailSubclass(mailArrivalForm.getMailSC()[index].toUpperCase());
			mailbagVO.setYear(Integer.parseInt(mailArrivalForm.getMailYr()[index]));
			mailbagVO.setDespatchSerialNumber(mailArrivalForm.getMailDSN()[index].toUpperCase());
			mailbagVO.setReceptacleSerialNumber(mailArrivalForm.getMailRSN()[index].toUpperCase());
			mailbagVO.setHighestNumberedReceptacle(
					mailArrivalForm.getMailHNI()[index].toUpperCase());
			mailbagVO.setRegisteredOrInsuredIndicator(
					mailArrivalForm.getMailRI()[index].toUpperCase());	
			//modified by A-8236 for ICRD-250569
			Measure[] mailWt=mailArrivalForm.getMailWtMeasure();
			//double systemWt=Double.parseDouble(mailArrivalForm.getMailWt()[index])/10;
			//Measure wgt=new Measure(UnitConstants.MAIL_WGT,systemWt);
			mailbagVO.setWeight(mailWt[index]);//added by A-7371
			return mailbagVO;
			
		}
		/**
		 * 
		 * 	Method		:	PopulateMailTagDetailsCommand.updateForm
		 *	Added by 	:	a-6245 on 28-Jun-2017
		 * 	Used for 	:
		 *	Parameters	:	@param mailArrivalForm
		 *	Parameters	:	@param index 
		 *	Return type	: 	void
		 */
		private void updateForm(MailArrivalForm mailArrivalForm,
				int index){
			mailArrivalForm.setOriginOE(mailArrivalForm.getMailOOE()[index].toUpperCase());
			mailArrivalForm.setDestinationOE(mailArrivalForm.getMailDOE()[index].toUpperCase());
			mailArrivalForm.setCategory(mailArrivalForm.getMailCat()[index].toUpperCase());
			mailArrivalForm.setSubClass(mailArrivalForm.getMailSC()[index].toUpperCase());
			mailArrivalForm.setYear(mailArrivalForm.getMailYr()[index]);
			mailArrivalForm.setDsn(mailArrivalForm.getMailDSN()[index].toUpperCase());
			mailArrivalForm.setRsn(mailArrivalForm.getMailRSN()[index].toUpperCase());	
			mailArrivalForm.setHni(mailArrivalForm.getMailHNI()[index].toUpperCase());	
			mailArrivalForm.setRi(mailArrivalForm.getMailRI()[index].toUpperCase());
			mailArrivalForm.setWgtMeasure(mailArrivalForm.getMailWtMeasure()[index]);
			
		}
		
		/**
		 * 
		 * 	Method		:	PopulateMailTagDetailsCommand.weightFormatter
		 *	Added by 	:	A-8236 on 20-Feb-2018
		 * 	Used for 	:
		 *	Parameters	:	@param weight
		 *	Parameters	:	@return 
		 *	Return type	: 	String
		 */
		private String weightFormatter(Double weight) {
	    	String weightString = String.valueOf(weight.intValue());
	        String weights[] = weightString.split("[.]");
	        StringBuilder flatWeight = new StringBuilder(weights[0]);
	       /* if(!"0".equals(weights[1])){
	        	flatWeight.append(weights[1]);
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
