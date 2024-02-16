/*
 * PopulateMailTagDetailsCommand.java Created on Jun 07 2017
 *
 * Copyright 2017 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.printmailtag;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PrintMailTagForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
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
	    	
	    	PrintMailTagForm printMailTagForm = (PrintMailTagForm)invocationContext.screenModel;
	    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    	String companyCode=logonAttributes.getCompanyCode();
	    	String[] mailbagId = printMailTagForm.getMailbagId();
	    	String[] mailOpFlag = printMailTagForm.getOpFlag();
	    	String[] selectedMailTag=printMailTagForm.getSelectMail();
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
	    	//String[] mailWt=new String[length];
	    	Measure[] mailWt=new Measure[length];//added by A-7871 for ICRD-263254
	    	String[] mailId=new String[length];
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
							printMailTagForm.setOriginOE(mailOOE);	
							printMailTagForm.setMailOOE(mailOOE[index]);
							
							mailDOE[index]=mailbagVO.getDoe();
							printMailTagForm.setDestnOE(mailDOE);
							printMailTagForm.setMailDOE(mailDOE[index]);
							
							mailCat[index]=mailbagVO.getMailCategoryCode();
							printMailTagForm.setCategory(mailCat);	
							printMailTagForm.setMailCat(mailCat[index]);
							
							mailSC[index]=mailbagVO.getMailSubclass();
							printMailTagForm.setSubClass(mailSC);
							printMailTagForm.setMailSC(mailSC[index]);
							
							mailYr[index]=String.valueOf(mailbagVO.getYear());
							printMailTagForm.setYear(mailYr);
							printMailTagForm.setMailYr(mailYr[index]);
							
							mailDSN[index]=mailbagVO.getDespatchSerialNumber();
							printMailTagForm.setDsn(mailDSN);	
							printMailTagForm.setMailDSN(mailDSN[index]);
							
							mailRSN[index]=mailbagVO.getReceptacleSerialNumber();
							printMailTagForm.setRsn(mailRSN);	
							printMailTagForm.setMailRSN(mailRSN[index]);
							
							mailHNI[index]=mailbagVO.getHighestNumberedReceptacle();
							printMailTagForm.setHni(mailHNI);	
							printMailTagForm.setMailHNI(mailHNI[index]);
							
							mailRI[index]=mailbagVO.getRegisteredOrInsuredIndicator();
							printMailTagForm.setRi(mailRI);	
							printMailTagForm.setMailRI(mailRI[index]);
							
							//mailbagVO.getWeight().setDisplayValue(mailbagVO.getWeight().getRoundedDisplayValue());
				            mailWt[index]=mailbagVO.getWeight();//added by A-7371
				            printMailTagForm.setMailWtMeasure(mailWt);	
							printMailTagForm.setWgtMeasure(mailWt[index]);
							printMailTagForm.setWgt(weightFormatter(printMailTagForm.getWgtMeasure().getRoundedDisplayValue()));//modified by a-7871 for ICRD-263254
							
							mailId[index]=mailbagVO.getMailbagId();
							printMailTagForm.setMailbagId(mailId);	
							printMailTagForm.setMailId(mailId[index]);
							
							
						}else{
							if(mailbagId[index].length()==29){
								if(validateMailTagFormat(mailbagId[index])){
							mailOOE[index]=mailbagId[index].substring(0, 6);
							printMailTagForm.setOriginOE(mailOOE);	
							printMailTagForm.setMailOOE(mailOOE[index].toUpperCase());
							
							mailDOE[index]=mailbagId[index].substring(6,12 );
							printMailTagForm.setDestnOE(mailDOE);	
							printMailTagForm.setMailDOE(mailDOE[index].toUpperCase());
							
							mailCat[index]=mailbagId[index].substring(12,13 );
							printMailTagForm.setCategory(mailCat);	
							printMailTagForm.setMailCat(mailCat[index].toUpperCase());
							
							mailSC[index]=mailbagId[index].substring(13,15);
							printMailTagForm.setSubClass(mailSC);
							printMailTagForm.setMailSC(mailSC[index].toUpperCase());
							
							mailYr[index]=mailbagId[index].substring(15,16);
							printMailTagForm.setYear(mailYr);
							printMailTagForm.setMailYr(mailYr[index]);
							
							mailDSN[index]=mailbagId[index].substring(16,20);
							printMailTagForm.setDsn(mailDSN);	
							printMailTagForm.setMailDSN(mailDSN[index].toUpperCase());
							
							mailRSN[index]=mailbagId[index].substring(20,23);
							printMailTagForm.setRsn(mailRSN);	
							printMailTagForm.setMailRSN(mailRSN[index].toUpperCase());
							
							mailHNI[index]=mailbagId[index].substring(23,24);
							printMailTagForm.setHni(mailHNI);	
							printMailTagForm.setMailHNI(mailHNI[index].toUpperCase());
							
							mailRI[index]=mailbagId[index].substring(24,25);
							printMailTagForm.setRi(mailRI);	
							printMailTagForm.setMailRI(mailRI[index].toUpperCase());
							
							mailWt[index]=new Measure(UnitConstants.MAIL_WGT, Double.parseDouble(mailbagId[index].substring(25,29)));
							printMailTagForm.setMailWtMeasure(mailWt);	//added by A-7871 for ICRD-263254
							printMailTagForm.setWgtMeasure(mailWt[index]);
							printMailTagForm.setWgt(mailbagId[index].substring(25,29));
							
							mailId[index]=mailbagId[index];
							printMailTagForm.setMailbagId(mailId);	
							printMailTagForm.setMailId(mailId[index].toUpperCase());
								}
							}
						}
	    			}else{
	    				if(validateForm(printMailTagForm,index)){
			    				try{
			    					mailbagVO =new MailTrackingDefaultsDelegate().
			    							findMailbagIdForMailTag(constructMailbagVO(printMailTagForm,index,companyCode)) ;
			    				}catch (BusinessDelegateException businessDelegateException) {
					  				handleDelegateException(businessDelegateException);
			  				}
				    			updateForm(printMailTagForm,index);
			    				if(mailbagVO!=null){
				    				if(mailbagVO.getMailbagId()!=null&&
				    						!("").equals(mailbagVO.getMailbagId())){
										mailId[index]=mailbagVO.getMailbagId();
										printMailTagForm.setMailbagId(mailId);	
										printMailTagForm.setMailId(mailId[index]);
										printMailTagForm.setMailWt(printMailTagForm.getWeight()[index]);//Reset weight after constructing mail id
				    				}
			    				}else{
			    					String wgt=weightFormatter(printMailTagForm.getWgtMeasure().getDisplayValue());
			    					StringBuilder mailTagId=new StringBuilder();
			    					mailTagId.append(printMailTagForm.getMailOOE())
			    							 .append(printMailTagForm.getMailDOE())
			    							 .append(printMailTagForm.getMailCat())
											 .append(printMailTagForm.getMailSC()).append(printMailTagForm.getMailYr())
											 .append(printMailTagForm.getMailDSN()).append(printMailTagForm.getMailRSN())
											 .append(printMailTagForm.getMailHNI()).append(printMailTagForm.getMailRI())
											.append(wgt);
			    					mailId[index]=mailTagId.toString();
			    					printMailTagForm.setMailbagId(mailId);	
			    					printMailTagForm.setMailId(mailId[index]);
			    					printMailTagForm.setWgt(wgt);//modified by a-7871 for ICRD-263219
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
		 *	Parameters	:	@param printMailTagForm
		 *	Parameters	:	@param index
		 *	Parameters	:	@return 
		 *	Return type	: 	boolean
		 */
		private boolean validateForm(PrintMailTagForm printMailTagForm,
				int index){
			boolean isValid=false;
			if(printMailTagForm.getOriginOE()[index]!=null&&
					!printMailTagForm.getOriginOE()[index].isEmpty()&&
					printMailTagForm.getDestnOE()!=null&&
					!printMailTagForm.getDestnOE()[index].isEmpty()&&
					printMailTagForm.getCategory()!=null&&
					!printMailTagForm.getCategory()[index].isEmpty()&&
					printMailTagForm.getSubClass()!=null&&
					!printMailTagForm.getSubClass()[index].isEmpty()&&
					printMailTagForm.getYear()!=null&&
					!printMailTagForm.getYear()[index].isEmpty()&&
					printMailTagForm.getDsn()!=null&&
					!printMailTagForm.getDsn()[index].isEmpty()&&
					printMailTagForm.getRsn()!=null&&
					!printMailTagForm.getRsn()[index].isEmpty()&&
					printMailTagForm.getHni()!=null&&
					!printMailTagForm.getHni()[index].isEmpty()&&
					printMailTagForm.getRi()!=null&&
					!printMailTagForm.getRi()[index].isEmpty()&&
					printMailTagForm.getWeight()!=null&&
					!printMailTagForm.getWeight()[index].isEmpty()){
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
		 *	Parameters	:	@param printMailTagForm
		 *	Parameters	:	@param index
		 *	Parameters	:	@param companyCode
		 *	Parameters	:	@return 
		 *	Return type	: 	MailbagVO
		 */
		private MailbagVO constructMailbagVO(PrintMailTagForm printMailTagForm, int index,String companyCode){
			MailbagVO mailbagVO =new MailbagVO();
			mailbagVO.setCompanyCode(companyCode);
			mailbagVO.setOoe(printMailTagForm.getOriginOE()[index].toUpperCase());
			mailbagVO.setDoe(printMailTagForm.getDestnOE()[index].toUpperCase());
			mailbagVO.setMailCategoryCode(printMailTagForm.getCategory()[index].toUpperCase());
			mailbagVO.setMailSubclass(printMailTagForm.getSubClass()[index].toUpperCase());
			mailbagVO.setYear(Integer.parseInt(printMailTagForm.getYear()[index]));
			mailbagVO.setDespatchSerialNumber(printMailTagForm.getDsn()[index].toUpperCase());
			mailbagVO.setReceptacleSerialNumber(printMailTagForm.getRsn()[index].toUpperCase());
			mailbagVO.setHighestNumberedReceptacle(
					printMailTagForm.getHni()[index].toUpperCase());
			mailbagVO.setRegisteredOrInsuredIndicator(
					printMailTagForm.getRi()[index].toUpperCase());
			//mailbagVO.setWeight(Double.parseDouble(printMailTagForm.getWeight()[index])/10);
			mailbagVO.setWeight(printMailTagForm.getMailWtMeasure()[index]);//added by A-7371
			
			return mailbagVO;
			
		}
		private void updateForm(PrintMailTagForm printMailTagForm, int index){
			
			printMailTagForm.setMailOOE(printMailTagForm.getOriginOE()[index].toUpperCase());
			printMailTagForm.setMailDOE(printMailTagForm.getDestnOE()[index].toUpperCase());
			printMailTagForm.setMailCat(printMailTagForm.getCategory()[index].toUpperCase());
			printMailTagForm.setMailSC(printMailTagForm.getSubClass()[index].toUpperCase());
			printMailTagForm.setMailYr(printMailTagForm.getYear()[index]);
			printMailTagForm.setMailDSN(printMailTagForm.getDsn()[index].toUpperCase());
			printMailTagForm.setMailRSN(printMailTagForm.getRsn()[index].toUpperCase());	
			printMailTagForm.setMailHNI(printMailTagForm.getHni()[index].toUpperCase());
			printMailTagForm.setMailRI(printMailTagForm.getRi()[index].toUpperCase());
			printMailTagForm.setWgtMeasure(printMailTagForm.getMailWtMeasure()[index]);//modified by a-7871 for ICRD-263254
		}
		/**
		 * 
		 * 	Method		:	PopulateMailTagDetailsCommand.weightFormatter
		 *	Added by 	:	A-7871 on 22-May-2018
		 * 	Used for 	:
		 *	Parameters	:	@param weight
		 *	Parameters	:	@return 
		 *	Return type	: 	String
		 */
		private String weightFormatter(Double weight) {
	    	String weightString = String.valueOf(weight);
	        String weights[] = weightString.split("[.]");
	        StringBuilder flatWeight = new StringBuilder(weights[0]);
	        if(!"0".equals(weights[1])){
	        	flatWeight.append(weights[1]);
	        }
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

}
