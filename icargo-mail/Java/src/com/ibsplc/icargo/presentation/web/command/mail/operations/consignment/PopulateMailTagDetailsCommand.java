/*
 * PopulateMailTagDetailsCommand.java Created on Jun 21 2017
 *
 * Copyright 2017 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.consignment;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
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
	   //A-8061 added for ICRD-229579 
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.consignment";	
       private Map<String,String> exchangeOfficeMap;
	   
	   private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	   
	   /**
	    * 
	    *	Overriding Method	:@see com.ibsplc.icargo.framework.web.command.Command
	    *						#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	    *	Added by 			: A-6245 on 21-Jun-2017
	    * 	Used for 	:
	    *	Parameters	:	@param invocationContext
	    *	Parameters	:	@throws CommandInvocationException
	    */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering(CLASS_NAME,"execute");
	    	//A-8061 added for ICRD-229579 begin
	    	ConsignmentSession consignmentSession = getScreenSession(MODULE_NAME,SCREEN_ID);  
	    	ConsignmentDocumentVO consignmentDocumentVO=consignmentSession.getConsignmentDocumentVO();
	    	List<MailInConsignmentVO> mailbagVOs=new ArrayList<MailInConsignmentVO>();
	    	//A-8061 added for ICRD-229579 end
	    	ConsignmentForm consignmentForm = (ConsignmentForm)invocationContext.screenModel;
	    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    	String companyCode=logonAttributes.getCompanyCode();
	    	String[] mailbagId = consignmentForm.getMailbagId();
	    	String[] mailOpFlag = consignmentForm.getMailOpFlag();
	    	String[] selectedMailTag=consignmentForm.getSelectMail();
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
	    	String[] mailWt=new String[length];
	    	String[] mailId=new String[length];
	    	String[] mailClass=new String[length];		   
			String[] weightUnit=consignmentForm.getWeightUnit(); //a-8353
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
							consignmentForm.setOriginOE(mailOOE);	
							consignmentForm.setMailOOE(mailOOE[index]);
							
							mailDOE[index]=mailbagVO.getDoe();
							consignmentForm.setDestinationOE(mailDOE);	
							consignmentForm.setMailDOE(mailDOE[index]);
							
							mailCat[index]=mailbagVO.getMailCategoryCode();
							consignmentForm.setCategory(mailCat);	
							consignmentForm.setMailCat(mailCat[index]);
							
							mailSC[index]=mailbagVO.getMailSubclass();
							consignmentForm.setSubClass(mailSC);
							consignmentForm.setMailSC(mailSC[index]);
							
							mailYr[index]=String.valueOf(mailbagVO.getYear());
							consignmentForm.setYear(mailYr);
							consignmentForm.setMailYr(mailYr[index]);
							
							mailDSN[index]=mailbagVO.getDespatchSerialNumber();
							consignmentForm.setDsn(mailDSN);	
							consignmentForm.setMailDSN(mailDSN[index]);
							
							mailRSN[index]=mailbagVO.getReceptacleSerialNumber();
							consignmentForm.setRsn(mailRSN);	
							consignmentForm.setMailRSN(mailRSN[index]);
							
							mailHNI[index]=mailbagVO.getHighestNumberedReceptacle();
							consignmentForm.setMailHI(mailHNI);	
							consignmentForm.setHni(mailHNI[index]);
							
							mailRI[index]=mailbagVO.getRegisteredOrInsuredIndicator();
							consignmentForm.setMailRI(mailRI);	
							consignmentForm.setRi(mailRI[index]);
							if(mailbagId[index].length()==12){
								String wgt=mailbagVO.getMailbagId().substring(10,12);//modified by A-7371
								//Added by a-8353 for ICRD-274933 starts    
								UnitConversionNewVO unitConversionVO= null;
		    					try {
		    						unitConversionVO=UnitFormatter.getUnitConversionForToUnit(UnitConstants.MAIL_WGT,"L",weightUnit[index], Double.parseDouble(wgt));
		    					} catch (UnitException e) {  
		    						// TODO Auto-generated catch block  
		    						e.getMessage();
		    					}
		    					double convertedWeight = unitConversionVO.getToValue();
		    					double conDisplayWeight=0;
		    					if(UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(weightUnit[index])){
		    						 conDisplayWeight=round(convertedWeight,1); 
		    					}
		    					else {
		    						conDisplayWeight=round(convertedWeight,0);
		    					}//Added by a-8353 for ICRD-274933 ends 
								mailWt[index]=String.valueOf(conDisplayWeight);
								consignmentForm.setWeight(mailWt);	
								consignmentForm.setMailWt(String.valueOf(conDisplayWeight));
								}
							
							//Added by a-8353 for ICRD-274933 starts 
							else{
							UnitConversionNewVO unitConversionVO= null;
	    					try {
	    						unitConversionVO=UnitFormatter.getUnitConversionForToUnit(UnitConstants.MAIL_WGT,"H",weightUnit[index], Double.parseDouble(mailbagId[index].substring(25,29)));
	    					} catch (UnitException e) {
	    						// TODO Auto-generated catch block
	    						e.getMessage();
	    					}
	    					double convertedWeight = unitConversionVO.getToValue();
	    					double conDisplayWeight=0;
	    					if(UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(weightUnit[index])){
	    						 conDisplayWeight=round(convertedWeight,1); 
	    					}
	    					else {
	    						conDisplayWeight=round(convertedWeight,0);
	    					}//Added by a-8353 for ICRD-274933 ends 
							mailWt[index]=String.valueOf(conDisplayWeight);
							consignmentForm.setWeight(mailWt);	
							consignmentForm.setMailWt(String.valueOf(conDisplayWeight));
							}
							
							mailId[index]=mailbagVO.getMailbagId();
							consignmentForm.setMailbagId(mailId);	
							consignmentForm.setMailId(mailId[index]);
							
							
						}else
							if(mailbagId[index].length()==29){
								if(validateMailTagFormat(mailbagId[index])){
							mailOOE[index]=mailbagId[index].substring(0, 6);
							consignmentForm.setOriginOE(mailOOE);	
							consignmentForm.setMailOOE(mailOOE[index].toUpperCase());
							
							mailDOE[index]=mailbagId[index].substring(6,12 );
							consignmentForm.setDestinationOE(mailDOE);	
							consignmentForm.setMailDOE(mailDOE[index].toUpperCase());
							
							mailCat[index]=mailbagId[index].substring(12,13 );
							consignmentForm.setCategory(mailCat);	
							consignmentForm.setMailCat(mailCat[index].toUpperCase());
							
							mailSC[index]=mailbagId[index].substring(13,15);
							consignmentForm.setSubClass(mailSC);
							consignmentForm.setMailSC(mailSC[index].toUpperCase());
							
							mailYr[index]=mailbagId[index].substring(15,16);
							consignmentForm.setYear(mailYr);
							consignmentForm.setMailYr(mailYr[index]);
							
							mailDSN[index]=mailbagId[index].substring(16,20);
							consignmentForm.setDsn(mailDSN);	
							consignmentForm.setMailDSN(mailDSN[index].toUpperCase());
							
							mailRSN[index]=mailbagId[index].substring(20,23);
							consignmentForm.setRsn(mailRSN);	
							consignmentForm.setMailRSN(mailRSN[index].toUpperCase());
							
							mailHNI[index]=mailbagId[index].substring(23,24);
							consignmentForm.setMailHI(mailHNI);	
							consignmentForm.setHni(mailHNI[index].toUpperCase());
							
							mailRI[index]=mailbagId[index].substring(24,25);
							consignmentForm.setMailRI(mailRI);	
							consignmentForm.setRi(mailRI[index].toUpperCase());
							//Added by a-8353 for ICRD-274933 starts    
							UnitConversionNewVO unitConversionVO= null;
	    					try {
	    						unitConversionVO=UnitFormatter.getUnitConversionForToUnit(UnitConstants.MAIL_WGT,"H",weightUnit[index], Double.parseDouble(mailbagId[index].substring(25,29)));
	    					} catch (UnitException e) {
	    						// TODO Auto-generated catch block
	    						e.getMessage();
	    					}
	    					double convertedWeight = unitConversionVO.getToValue();
	    					double conDisplayWeight=0;
	    					if(UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(weightUnit[index])){
	    						 conDisplayWeight=round(convertedWeight,1); 
	    					}
	    					else {
	    						conDisplayWeight=round(convertedWeight,0);
	    					}//Added by a-8353 for ICRD-274933 ends 
							mailWt[index]=String.valueOf(conDisplayWeight);
							consignmentForm.setWeight(mailWt);	
							consignmentForm.setMailWt(String.valueOf(conDisplayWeight));
							
							mailId[index]=mailbagId[index];
							consignmentForm.setMailbagId(mailId);	
							consignmentForm.setMailId(mailId[index].toUpperCase());
								}
							}else if(mailbagId[index].length()==10||mailbagId[index].length()==12){
								 
									String routIndex=mailbagId[index].substring(4,8);
									String org=null;
									String dest=null;
									String numBags[] = new String[length];;
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
									}
									
									if(exchangeOfficeMap!=null && !exchangeOfficeMap.isEmpty()){
							    		if(exchangeOfficeMap.containsKey(org)){
								mailOOE[index]=exchangeOfficeMap.get(org);
								consignmentForm.setOriginOE(mailOOE);	
								consignmentForm.setMailOOE(mailOOE[index]);
							    		}
							    		
							    		if(exchangeOfficeMap.containsKey(dest)){
								mailDOE[index]=exchangeOfficeMap.get(dest);
								consignmentForm.setDestinationOE(mailDOE);	
								consignmentForm.setMailDOE(mailDOE[index]);
							    		}
									}
								mailCat[index]="B";
								consignmentForm.setCategory(mailCat);	
								consignmentForm.setMailCat(mailCat[index]);
								
								mailClass[index]=mailbagId[index].substring(3,4);
								consignmentForm.setMailClassType(mailClass[index]);
								consignmentForm.setMailClass(mailClass);
								
								mailSC[index]=mailClass[index]+"X";
								consignmentForm.setSubClass(mailSC);
								consignmentForm.setMailSC(mailSC[index]);
								
								
								int lastTwoDigits = Calendar.getInstance().get(Calendar.YEAR) % 100;
								String lastDigitOfYear = String.valueOf(lastTwoDigits).substring(1,2);
								mailYr[index]=lastDigitOfYear;
								consignmentForm.setYear(mailYr);
								consignmentForm.setMailYr(mailYr[index]);
								String companycode=logonAttributes.getCompanyCode();
								
								MailbagVO newMailbagVO=new MailbagVO();
								newMailbagVO.setCompanyCode(companycode);
								newMailbagVO.setYear(Integer.parseInt(consignmentForm.getMailYr()));
								try {
									newMailbagVO =new MailTrackingDefaultsDelegate().findDsnAndRsnForMailbag(newMailbagVO);
								} catch (BusinessDelegateException businessDelegateException) {
									// TODO Auto-generated catch block
									handleDelegateException(businessDelegateException);
								}
								
								if(newMailbagVO.getDespatchSerialNumber()!=null){
								mailDSN[index]=newMailbagVO.getDespatchSerialNumber();
								consignmentForm.setDsn(mailDSN);	
								consignmentForm.setMailDSN(mailDSN[index]);
								}
								
								if(newMailbagVO.getDespatchSerialNumber()!=null){
								mailRSN[index]=newMailbagVO.getReceptacleSerialNumber();
								consignmentForm.setRsn(mailRSN);	
								consignmentForm.setMailRSN(mailRSN[index]);
								}
								
								mailHNI[index]="9";
								consignmentForm.setMailHI(mailHNI);	
								consignmentForm.setHni(mailHNI[index]);
								
								mailRI[index]="9";
								consignmentForm.setMailRI(mailRI);	
								consignmentForm.setRi(mailRI[index]);
								
								
								numBags[index]="1";
								consignmentForm.setNumBags(numBags);
								consignmentForm.setTotalBags("1");
								
								if(mailbagId[index].length()==12){
								String wgt=mailbagId[index].substring(10,12);//modified by A-7371
								//Added by a-8353 for ICRD-274933 starts    
								UnitConversionNewVO unitConversionVO= null;
		    					try {
		    						unitConversionVO=UnitFormatter.getUnitConversionForToUnit(UnitConstants.MAIL_WGT,"L",weightUnit[index], Double.parseDouble(wgt));
		    					} catch (UnitException e) {  
		    						// TODO Auto-generated catch block  
		    						e.getMessage();
		    					}
		    					double convertedWeight = unitConversionVO.getToValue();
		    					double conDisplayWeight=0;
		    					if(UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(weightUnit[index])){
		    						 conDisplayWeight=round(convertedWeight,1); 
		    					}
		    					else {
		    						conDisplayWeight=round(convertedWeight,0);
		    					}//Added by a-8353 for ICRD-274933 ends 
								mailWt[index]=String.valueOf(conDisplayWeight);
								consignmentForm.setWeight(mailWt);	
								consignmentForm.setMailWt(String.valueOf(conDisplayWeight));
								}
								
								mailId[index]=mailbagId[index];
								consignmentForm.setMailbagId(mailId);	
								consignmentForm.setMailId(mailId[index]);
								
								
								
								
								
							}
					//end
						}
	    			}else{
	    				if(validateForm(consignmentForm,index)){
			    				try{
			    					mailbagVO =new MailTrackingDefaultsDelegate().
			    							findMailbagIdForMailTag(constructMailbagVO(consignmentForm,index,companyCode)) ;
			    				}catch (BusinessDelegateException businessDelegateException) {
					  				handleDelegateException(businessDelegateException);
			  				}
			    		//Added by a-8353 for icrd-ICRD-274933 starts	 
			    		  double weightToAppend=updateForm(consignmentForm,index);  
			    		  if (mailWt[index]!=null){  
			    			if((weightToAppend<1)||(weightToAppend>9999)){
	    						invocationContext.addError(new ErrorVO("mailtracking.defaults.consignment.invalidweight"));
								invocationContext.target = TARGET_SUCCESS;
								return;
	    					}
			    		  }
			    		//Added by a-8353 for icrd-ICRD-274933 ends	 
			    			 
		    				if(mailbagVO!=null){
			    				if(mailbagVO.getMailbagId()!=null&&
			    						!("").equals(mailbagVO.getMailbagId())){
									mailId[index]=mailbagVO.getMailbagId();
									consignmentForm.setMailbagId(mailId);	
									consignmentForm.setMailId(mailId[index]);
									consignmentForm.setMailWt(consignmentForm.getWeight()[index]);//Reset weight after constructing mail id
			    				}
		    				}else{
		    					StringBuilder mailTagId=new StringBuilder();
		    					mailTagId.append(consignmentForm.getMailOOE())
		    							 .append(consignmentForm.getMailDOE())
		    							 .append(consignmentForm.getMailCat())
										 .append(consignmentForm.getMailSC()).append(consignmentForm.getMailYr())
										 .append(consignmentForm.getMailDSN()).append(consignmentForm.getMailRSN())
										 .append(consignmentForm.getHni()).append(consignmentForm.getRi())
										 .append(consignmentForm.getMailWt());
		    					mailId[index]=mailTagId.toString();
		    					consignmentForm.setMailbagId(mailId);	
		    					consignmentForm.setMailId(mailId[index]);
		    					consignmentForm.setMailWt(consignmentForm.getWeight()[index]);//Reset weight after constructing mail id
		    					
		    					consignmentForm.setMailClassType(consignmentForm.getMailClass()[index]);
		    					//A-8061 added for ICRD-229579 begin
		    					if(consignmentDocumentVO.getMailInConsignmentVOs()!=null  )
		    					{		    					
		    					//Added by a-7531 for icrd-252624 starts
		    					mailbagVOs.addAll(consignmentDocumentVO.getMailInConsignmentVOs());
		    					//MailInConsignmentVO newMailInConsignmentVO	 = 	 constructMailInConsignmentVO(consignmentForm,index,companyCode);
		    					//mailbagVOs.add(newMailInConsignmentVO);
		    					//Added by a-7531 for icrd-252624 ends
		    					if(mailbagVOs.size() > index ){
		    					mailbagVOs.get(index).setMailId(mailTagId.toString());
		    					mailbagVOs.get(index).setStatedWeight((new Measure(UnitConstants.MAIL_WGT ,Double.parseDouble(consignmentForm.getWeight()[index])))); 
		    					mailbagVOs.get(index).setStatedBags(Integer.parseInt(consignmentForm.getNumBags()[index]));
		    					}
		    					Page<MailInConsignmentVO> newmailbagVO= new Page<MailInConsignmentVO>(mailbagVOs,0,0,0,0,0,false);
		    					consignmentSession.getConsignmentDocumentVO().setMailInConsignmentVOs(newmailbagVO);
		    				 
		    					}
		    					//A-8061 added for ICRD-229579 end
		    					 
		    					 
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
		 *	Parameters	:	@param consignmentForm
		 *	Parameters	:	@param index
		 *	Parameters	:	@return 
		 *	Return type	: 	boolean
		 */
		private boolean validateForm(ConsignmentForm consignmentForm,
				int index){
			boolean isValid=false;
			if(consignmentForm.getOriginOE()[index]!=null&&
					!consignmentForm.getOriginOE()[index].isEmpty()&&
					consignmentForm.getDestinationOE()!=null&&
					!consignmentForm.getDestinationOE()[index].isEmpty()&&
					consignmentForm.getCategory()!=null&&
					!consignmentForm.getCategory()[index].isEmpty()&&
					consignmentForm.getSubClass()!=null&&
					!consignmentForm.getSubClass()[index].isEmpty()&&
					consignmentForm.getYear()!=null&&
					!consignmentForm.getYear()[index].isEmpty()&&
					consignmentForm.getDsn()!=null&&
					!consignmentForm.getDsn()[index].isEmpty()&&
					consignmentForm.getRsn()!=null&&
					!consignmentForm.getRsn()[index].isEmpty()&&
					consignmentForm.getMailHI()!=null&&
					!consignmentForm.getMailHI()[index].isEmpty()&&
					consignmentForm.getMailRI()!=null&&
					!consignmentForm.getMailRI()[index].isEmpty()&&
					consignmentForm.getWeight()!=null&&
					!consignmentForm.getWeight()[index].isEmpty()){
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
		 *	Parameters	:	@param consignmentForm
		 *	Parameters	:	@param index
		 *	Parameters	:	@param companyCode
		 *	Parameters	:	@return 
		 *	Return type	: 	MailbagVO
		 */
		private MailbagVO constructMailbagVO(ConsignmentForm consignmentForm, int index,String companyCode){
			MailbagVO mailbagVO =new MailbagVO();
			mailbagVO.setCompanyCode(companyCode);
			mailbagVO.setOoe(consignmentForm.getOriginOE()[index].toUpperCase());
			mailbagVO.setDoe(consignmentForm.getDestinationOE()[index].toUpperCase());
			mailbagVO.setMailCategoryCode(consignmentForm.getCategory()[index].toUpperCase());
			mailbagVO.setMailSubclass(consignmentForm.getSubClass()[index].toUpperCase());
			mailbagVO.setYear(Integer.parseInt(consignmentForm.getYear()[index]));
			mailbagVO.setDespatchSerialNumber(consignmentForm.getDsn()[index].toUpperCase());
			mailbagVO.setReceptacleSerialNumber(consignmentForm.getRsn()[index].toUpperCase());
			mailbagVO.setHighestNumberedReceptacle(
					consignmentForm.getMailHI()[index].toUpperCase());
			mailbagVO.setRegisteredOrInsuredIndicator(
					consignmentForm.getMailRI()[index].toUpperCase());
			double systemWt=Double.parseDouble(consignmentForm.getWeight()[index]);
			Measure wgt=new Measure(UnitConstants.MAIL_WGT,systemWt);
			mailbagVO.setWeight(wgt);//added by A-7371
			return mailbagVO;
			
		}
		/**
		 * 
		 * 	Method		:	PopulateMailTagDetailsCommand.updateForm
		 *	Added by 	:	a-6245 on 28-Jun-2017
		 * 	Used for 	:
		 *	Parameters	:	@param consignmentForm
		 *	Parameters	:	@param index 
		 *	Return type	: 	double
		 */
		private double updateForm(ConsignmentForm consignmentForm, int index){	
			consignmentForm.setMailOOE(consignmentForm.getOriginOE()[index].toUpperCase());
			consignmentForm.setMailDOE(consignmentForm.getDestinationOE()[index].toUpperCase());
			consignmentForm.setMailCat(consignmentForm.getCategory()[index].toUpperCase());
			consignmentForm.setMailSC(consignmentForm.getSubClass()[index].toUpperCase());
			consignmentForm.setMailYr(consignmentForm.getYear()[index]);	
			consignmentForm.setMailDSN(consignmentForm.getDsn()[index].toUpperCase());	
			consignmentForm.setMailRSN(consignmentForm.getRsn()[index].toUpperCase());	
			consignmentForm.setHni(consignmentForm.getMailHI()[index].toUpperCase());	
			consignmentForm.setRi(consignmentForm.getMailRI()[index].toUpperCase());
			//Added by a-8353 for icrd-ICRD-274933 starts
			double wgt=Double.parseDouble(consignmentForm.getWeight()[index]);			
			//String mailWgt=String.valueOf(((int)(wgt)));
			UnitConversionNewVO unitConversionVO= null;    
			String wgtStr="";
			try {
				unitConversionVO=UnitFormatter.getUnitConversionForToUnit(UnitConstants.MAIL_WGT,consignmentForm.getWeightUnit()[index],"H",wgt);
			} catch (UnitException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}  
			double weighttoappend = unitConversionVO.getToValue();
			if((weighttoappend<1)||(weighttoappend>9999)){
				
				return weighttoappend;
			}
			double roundedWeight=round(weighttoappend,0);
			
			String mailWgt=String.valueOf(((int)(roundedWeight)));	
			//Added by a-8353 for icrd-ICRD-274933 ends
			if(mailWgt.length() == 3){
				wgtStr = new StringBuilder("0").append(mailWgt).toString();  
			}
			else if(mailWgt.length() == 2){
				wgtStr = new StringBuilder("00").append(mailWgt).toString();
			}
			else if(mailWgt.length() == 1){
				wgtStr = new StringBuilder("000").append(mailWgt).toString();
			}
			else if(mailWgt.length() >4||mailWgt.length() ==0){
				wgtStr = new StringBuilder("0000").toString();//Handle case when weight is incorrect
			}
			else{
				wgtStr = mailWgt;
			}
			consignmentForm.setMailWt(wgtStr);
			
 			return weighttoappend;
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
	   /** Added by :A-8353 
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


}
