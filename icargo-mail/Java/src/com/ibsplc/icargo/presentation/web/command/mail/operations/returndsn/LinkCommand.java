/*
 * LinkCommand.java Created on July 24, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.returndsn;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReturnDsnSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReturnDsnForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1861
 *
 */
public class LinkCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "link_success";
   private static final String TARGET_FAILURE = "link_failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.returndsn";	
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("LinkCommand","execute");
    	  
    	ReturnDsnForm returnDsnForm = 
    		(ReturnDsnForm)invocationContext.screenModel;
    	ReturnDsnSession returnDsnSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	Collection<ErrorVO> errors = null;
    	
    	int currentVoIndex = returnDsnForm.getCurrentPage();
		int nextVoIndex = returnDsnForm.getDisplayPage();
		int lastVoIndex = returnDsnForm.getLastPage();
    	
    	log.log(Log.INFO, "currentpage:-->", currentVoIndex);
		log.log(Log.INFO, "displaypage:-->", nextVoIndex);
		log.log(Log.INFO, "lastpage:-->", lastVoIndex);
		// VALIDATING FORM
		errors = validateForm(returnDsnForm);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			returnDsnForm.setDisplayPage(returnDsnForm.getCurrentPage());
			returnDsnForm.setLastPage(returnDsnSession.getDamagedDsnVOs().size());
			return;
		}
		// VALIDATING DSN DETAILS		
		errors = validateDsns(returnDsnForm,returnDsnSession);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			returnDsnForm.setDisplayPage(returnDsnForm.getCurrentPage());
			returnDsnForm.setLastPage(returnDsnSession.getDamagedDsnVOs().size());
			return;
		}
		
		Collection<DamagedDSNVO> damagedDsnVOs = returnDsnSession.getDamagedDsnVOs();
		
		log.log(Log.FINE, "Dsnvos--------> ", damagedDsnVOs);
		returnDsnForm.setCurrentPage(returnDsnForm.getDisplayPage());
		returnDsnForm.setLastPage(returnDsnSession.getDamagedDsnVOs().size());
		
		displayDsndetails(returnDsnForm,returnDsnSession,returnDsnForm.getCurrentPage());
		    	
    	invocationContext.target = TARGET_SUCCESS;
    	
       	log.exiting("LinkCommand","execute");
    	
    }
    /**
     * Method used to display the details of next vo in the form
     * @param returnDsnForm
     * @param returnDsnSession
     * @param currentpage
     */
    private void displayDsndetails(
    		ReturnDsnForm returnDsnForm,
    		ReturnDsnSession returnDsnSession,
    		int currentpage) {
    	
    	log.entering("LinkCommand","displayDsndetails");
    	
    	Collection<DamagedDSNVO> damagedDsnVOs = returnDsnSession.getDamagedDsnVOs();
    	int index = 1;
		for (DamagedDSNVO dsnvo : damagedDsnVOs) {
			if (index == currentpage) {
				returnDsnForm.setDsn(dsnvo.getDsn());
				returnDsnForm.setOriginOE(dsnvo.getOriginExchangeOffice());
				returnDsnForm.setDestnOE(dsnvo.getDestinationExchangeOffice());
				returnDsnForm.setMailClass(dsnvo.getMailClass());
				returnDsnForm.setYear("200"+String.valueOf(dsnvo.getYear()));
				returnDsnForm.setPostalAdmin(dsnvo.getPaCode());
				if(dsnvo.getAcceptedWeight().getRoundedSystemValue()== 0){
					//returnDsnForm.setDmgWeight(dsnvo.getReceivedWeight());
					returnDsnForm.setDmgWeight(dsnvo.getReceivedWeight().getRoundedSystemValue());//added by A-7371
				}else{
				//returnDsnForm.setDmgWeight(dsnvo.getAcceptedWeight());
					returnDsnForm.setDmgWeight(dsnvo.getAcceptedWeight().getRoundedSystemValue());//added by A-7371
				}
				if(dsnvo.getAcceptedBags()== 0){
					returnDsnForm.setDmgNOB(dsnvo.getReceivedBags());
				}else{
					returnDsnForm.setDmgNOB(dsnvo.getAcceptedBags());	
				}
				
				
				break;
			}
			index++;
		}
		
		log.exiting("LinkCommand","displayDsndetails");
    }
        
    /**
     * Method for validating form for mandatory checks
     * @param returnDsnForm
     * @return
     */
    private Collection<ErrorVO> validateForm(
    		ReturnDsnForm returnDsnForm) {
    	
    	log.entering("LinkCommand","validateForm");
    	
    	Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();
    	
    	String[] damagedbags = returnDsnForm.getDamagedBags();
    	String[] damagedweights = returnDsnForm.getDamagedWeight();
    	//String[] returnedbags = returnDsnForm.getReturnedBags();
    	//String[] returnedweights = returnDsnForm.getReturnedWeight();
    	
		if (damagedbags != null) {
			int rows = damagedbags.length;
			for (int i = 0 ; i < rows ; i++) {
				if (("").equals(damagedbags[i])) {
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.returndsn.msg.err.nobag");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					formErrors.add(errorVO);
					break;
				}
				else if (Integer.parseInt(damagedbags[i]) == 0) {
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.returndsn.msg.err.nobag");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					formErrors.add(errorVO);
					break;
				}
			}
			for (int i = 0 ; i < rows ; i++) {
				if (("").equals(damagedweights[i])) {
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.returndsn.msg.err.noweight");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					formErrors.add(errorVO);
					break;
				}
				else if (Double.parseDouble(damagedweights[i]) == 0) {
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.returndsn.msg.err.noweight");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					formErrors.add(errorVO);
					break;
				}
			}
		}
		else {
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.returndsn.msg.err.nodamages");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			formErrors.add(errorVO);
		}
		
		log.exiting("LinkCommand","validateForm");
		return formErrors;
    }
    /**
     * Method for validating duplicate dsns and bagcount and weight
     * @param returnDsnForm
     * @param returnDsnSession
     * @return
     */
    private Collection<ErrorVO> validateDsns(
    		ReturnDsnForm returnDsnForm,
    		ReturnDsnSession returnDsnSession) {
    	
    	log.entering("LinkCommand","validateDsns");
    	
    	Collection<ErrorVO> validationErrors = new ArrayList<ErrorVO>();
    	    	
		String[] damageCodes = returnDsnForm.getDamageCode();
		
		// VALIDATING DUPLICATE DAMAGE CODES
		if (damageCodes != null) {
			int rows = damageCodes.length;
			for (int i = 0; i < rows; i++) {
				int checkFlag = 0;
				for (int j = i + 1; j < rows; j++) {
					if (!("").equals(damageCodes[j])
							&& !("").equals(damageCodes[i])) {

						if (damageCodes[j].equalsIgnoreCase(damageCodes[i])) {
							checkFlag = 1;
							ErrorVO errorVO = new ErrorVO(
									"mailtracking.defaults.returndsn.msg.err.duplicatedamagecodes");
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							validationErrors.add(errorVO);
							break;
						}

					}
				}
				if (checkFlag == 1) {
					break;
				}
			}
		}
		
		// VALIDATING BAGS & WEIGHT
		
		int currentVoIndex = returnDsnForm.getCurrentPage();
		Collection<DamagedDSNVO> damagedDsnVOs = returnDsnSession.getDamagedDsnVOs();
		
		int acceptedBags = 0;
		double acceptedWt = 0;
		int totalBags = 0;
		double totalWt = 0;
    	
		int index = 1;
		Collection<DamagedDSNDetailVO> damagedDsnDetailVOs = null;
		for (DamagedDSNVO dsnvo : damagedDsnVOs) {
			
			if (index == currentVoIndex) {
				acceptedBags = dsnvo.getAcceptedBags();
				//acceptedWt = dsnvo.getAcceptedWeight();
				acceptedWt = dsnvo.getAcceptedWeight().getRoundedSystemValue();//added by A-7371
				damagedDsnDetailVOs = dsnvo.getDamagedDsnDetailVOs();
				if (damagedDsnDetailVOs != null) {
					boolean isDamagedBagsLessThanReturned = false;
					boolean isDamagedWtLessThanReturned = false;
					for (DamagedDSNDetailVO dsndetailvo : damagedDsnDetailVOs) {
						totalBags = totalBags + (dsndetailvo.getLatestReturnedBags()-dsndetailvo.getReturnedBags());
						//totalWt = totalWt + (dsndetailvo.getLatestReturnedWeight()-dsndetailvo.getReturnedWeight());
						try {
							totalWt = totalWt + Measure.subtractMeasureValues(dsndetailvo.getLatestReturnedWeight(), dsndetailvo.getReturnedWeight()).getRoundedSystemValue();
						} catch (UnitException e1) {
							// TODO Auto-generated catch block
							log.log(Log.SEVERE,"UnitException",e1.getMessage());
						}
						if (dsndetailvo.getLatestDamagedBags() < dsndetailvo.getLatestReturnedBags()) {
							isDamagedBagsLessThanReturned = true;
						}
						//if (dsndetailvo.getLatestDamagedWeight() < dsndetailvo.getLatestReturnedWeight()) {
						try {
							if (Measure.compareTo(dsndetailvo.getLatestDamagedWeight(), dsndetailvo.getLatestReturnedWeight())<0) {
								isDamagedWtLessThanReturned = true;
							}
						} catch (UnitException e) {
							// TODO Auto-generated catch block
							log.log(Log.SEVERE,"UnitException",e.getMessage());
						}
					}
					if (isDamagedBagsLessThanReturned) {
						ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.returndsn.msg.err.DamagedBagsLessThanReturned");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						validationErrors.add(errorVO);
					}
					if (isDamagedWtLessThanReturned) {
						ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.returndsn.msg.err.DamagedWtLessThanReturned");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						validationErrors.add(errorVO);
					}
				}
				
				log.log(Log.FINE, "acceptedBags--------> ", acceptedBags);
				log.log(Log.FINE, "acceptedWt--------> ", acceptedWt);
				log.log(Log.FINE, "totalBags--------> ", totalBags);
				log.log(Log.FINE, "totalWt--------> ", totalWt);
				if (totalBags > acceptedBags) {
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.returndsn.msg.err.invalidDamagedBagsCount");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					validationErrors.add(errorVO);
				}
				if (totalWt > acceptedWt) {
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.returndsn.msg.err.invalidDamagedWeight");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					validationErrors.add(errorVO);
				}
				break;
			}										
			index++;
		}

		log.exiting("LinkCommand","validateDsns");
		return validationErrors;
    }
}
