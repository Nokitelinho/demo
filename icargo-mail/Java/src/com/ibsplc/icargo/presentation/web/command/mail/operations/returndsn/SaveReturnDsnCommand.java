/*
 * SaveReturnDsnCommand.java Created on July 24, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.returndsn;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DsnEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.EmptyULDsSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReturnDsnSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReturnDsnForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1861
 *
 */
public class SaveReturnDsnCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "save_success";
   private static final String TARGET_FAILURE = "save_failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.returndsn";
   private static final String EMPTYULD_SCREEN_ID = "mailtracking.defaults.emptyulds";
   private static final String DSN_SCREEN_ID = "mailtracking.defaults.dsnEnquiry";
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("SaveReturnDsnCommand","execute");
    	  
    	ReturnDsnForm returnDsnForm = 
    		(ReturnDsnForm)invocationContext.screenModel;
    	ReturnDsnSession returnDsnSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	EmptyULDsSession emptyUldsSession = 
			  getScreenSession(MODULE_NAME,EMPTYULD_SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = null;

    	Collection<DamagedDSNVO> damagedDsnVOs = returnDsnSession.getDamagedDsnVOs();
		
		int currentVoIndex = returnDsnForm.getCurrentPage();
		log.log(Log.INFO, "currentpage:-->", currentVoIndex);
		//	VALIDATING FORM
		errors = validateForm(returnDsnForm);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		// VALIDATING DSN DETAILS		
		errors = validateDsns(returnDsnForm,returnDsnSession);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		
		// VALIDATING WHETHER DAMAGES SPECIFIED FOR ALL VOS IN SESSION
		for (DamagedDSNVO dsnvo : damagedDsnVOs) {
			if (dsnvo.getDamagedDsnDetailVOs() == null 
					|| dsnvo.getDamagedDsnDetailVOs().size() == 0) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.returndsn.msg.err.noFullDetails");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors = new ArrayList<ErrorVO>();
				errors.add(errorVO);
				break;
			}
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
				
		// setting -1 for fltnum, segserialnum & fltseqnum for destnaccepted DSN
		for (DamagedDSNVO dsnvo : damagedDsnVOs) {
			dsnvo.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
			if (dsnvo.getFlightNumber() == null) {
				dsnvo.setFlightNumber("-1");
				dsnvo.setSegmentSerialNumber(-1);
				dsnvo.setFlightSequenceNumber(-1);
			}
		}
		
		// setting whether the dsns are returned or damaged 
		boolean isReturned = false;
		for (DamagedDSNVO dsnvo : damagedDsnVOs) {
			Collection<DamagedDSNDetailVO> damagedDsnDetailVOs = dsnvo.getDamagedDsnDetailVOs();
			if (damagedDsnDetailVOs != null) {
				for (DamagedDSNDetailVO dsndetailvo : damagedDsnDetailVOs) {
					if (dsndetailvo.getReturnedBags() != dsndetailvo.getLatestReturnedBags()
							|| dsndetailvo.getReturnedWeight() != dsndetailvo.getLatestReturnedWeight()) {
						isReturned = true;
						break;
					}
				}
			}
		}
		
		log.log(Log.FINE, "DsnvosForSaving--------> ", damagedDsnVOs);
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		try {
			if (isReturned) {
				log.log(Log.FINE,"GOING FOR RETURN--------> ");
				containerDetailsVOs = 
					mailTrackingDefaultsDelegate.returnDespatches(damagedDsnVOs);
			}
			else {
				log.log(Log.FINE,"GOING FOR DAMAGE--------> ");
				mailTrackingDefaultsDelegate.saveDamageDetailsForDespatches(damagedDsnVOs);
			}			

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		else {
			log.log(Log.FINE, "containerDetailsVOs ------------------>>",
					containerDetailsVOs);
			returnDsnSession.setDamagedDsnVOs(null);
			if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
	    		emptyUldsSession.setContainerDetailsVOs(containerDetailsVOs);
	    		returnDsnForm.setScreenStatusFlag(ComponentAttributeConstants.
	    				SCREEN_STATUS_VIEW);
		    }
	    	else {	    		
		    	returnDsnForm.setScreenStatusFlag(ComponentAttributeConstants.
						SCREEN_STATUS_SCREENLOAD);
	    	}			
		}
		    	
    	invocationContext.target = TARGET_SUCCESS;
    	
       	log.exiting("SaveReturnDsnCommand","execute");
    }
   
    /**
     * Method for validating form for mandatory checks
     * @param returnDsnForm
     * @return
     */
    private Collection<ErrorVO> validateForm(
    		ReturnDsnForm returnDsnForm) {
    	
    	log.entering("SaveReturnDsnCommand","validateForm");
    	
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
		
		log.exiting("SaveReturnDsnCommand","validateForm");
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
    	
    	log.entering("SaveReturnDsnCommand","validateDsns");
    	
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
		
        DsnEnquirySession dsnEnquirySession =
            getScreenSession(MODULE_NAME,DSN_SCREEN_ID);
        
		int currentVoIndex = returnDsnForm.getCurrentPage();
		Collection<DamagedDSNVO> damagedDsnVOs = returnDsnSession.getDamagedDsnVOs();
		String operationType = dsnEnquirySession.getDsnEnquiryFilterVO().
                getOperationType();
        boolean isInbound = operationType.
                equals(MailConstantsVO.OPERATION_INBOUND)? true : false ;
        log.log(Log.FINE, "Operation Type --> ", operationType);
		log.log(Log.FINE, " Is Inbound --> ", isInbound);
		int receivedBags = 0;
        double receivedWt = 0;
		int acceptedBags = 0;
		double acceptedWt = 0;
		int totalBags = 0;
		double totalWt = 0;
		int totalBagsTwo = 0;
		double totalWtTwo = 0;
    	
		int index = 1;
		Collection<DamagedDSNDetailVO> damagedDsnDetailVOs = null;
		for (DamagedDSNVO dsnvo : damagedDsnVOs) {
			
			if (index == currentVoIndex) {
                dsnvo.setOperationType(operationType);
				damagedDsnDetailVOs = dsnvo.getDamagedDsnDetailVOs();
				if (damagedDsnDetailVOs != null) {
					boolean isDamagedBagsLessThanReturned = false;
					boolean isDamagedWtLessThanReturned = false;
					for (DamagedDSNDetailVO dsndetailvo : damagedDsnDetailVOs) {
						totalBags = totalBags + (dsndetailvo.getLatestReturnedBags()-dsndetailvo.getReturnedBags());
						//totalWt = dsndetailvo.getLatestReturnedWeight()-dsndetailvo.getReturnedWeight();
						totalWt = dsndetailvo.getLatestReturnedWeight().getRoundedSystemValue()-dsndetailvo.getReturnedWeight().getRoundedSystemValue();
						totalBagsTwo = dsndetailvo.getLatestDamagedBags()-dsndetailvo.getDamagedBags();
						//totalWtTwo = dsndetailvo.getLatestDamagedWeight()-dsndetailvo.getDamagedWeight();	
						totalWtTwo = dsndetailvo.getLatestDamagedWeight().getRoundedDisplayValue()-dsndetailvo.getDamagedWeight().getRoundedSystemValue();//added by A-7371	
						if (dsndetailvo.getLatestDamagedBags() < dsndetailvo.getLatestReturnedBags()) {
							isDamagedBagsLessThanReturned = true;
						}
						//if (dsndetailvo.getLatestDamagedWeight() < dsndetailvo.getLatestReturnedWeight()) {
						if (dsndetailvo.getLatestDamagedWeight().getRoundedSystemValue() < dsndetailvo.getLatestReturnedWeight().getRoundedSystemValue()) {
							isDamagedWtLessThanReturned = true;
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
				log.log(Log.FINE, "totalBags Damaged--------> ", totalBagsTwo);
				log.log(Log.FINE, "totalWt   Damaged--------> ", totalWtTwo);
				log.log(Log.FINE, "totalBags--------> ", totalBags);
				log.log(Log.FINE, "totalWt--------> ", totalWt);
				if(isInbound){
                    receivedBags = dsnvo.getAcceptedBags();
                   // receivedWt = dsnvo.getAcceptedWeight();
                    receivedWt = dsnvo.getAcceptedWeight().getRoundedSystemValue();//added by A-7371
                    log.log(Log.FINE, "acceptedBags--------> ", receivedBags);
					log.log(Log.FINE, "acceptedWt--------> ", receivedWt);
					if (totalBagsTwo > receivedBags) {
                        ErrorVO errorVO = new ErrorVO(
                                "mailtracking.defaults.returndsn.msg.err.invalidDamagedBagsCount2");
                        errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
                        validationErrors.add(errorVO);
                    }
                    if (totalWtTwo > receivedWt) {
                        ErrorVO errorVO = new ErrorVO(
                                "mailtracking.defaults.returndsn.msg.err.invalidDamagedWeight2");
                        errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
                        validationErrors.add(errorVO);
                    }
                    
                    if (totalBags > receivedBags) {
                        ErrorVO errorVO = new ErrorVO(
                                "mailtracking.defaults.returndsn.msg.err.invalidDamagedBagsCount");
                        errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
                        validationErrors.add(errorVO);
                    }
                    if (totalWt > receivedWt) {
                        ErrorVO errorVO = new ErrorVO(
                                "mailtracking.defaults.returndsn.msg.err.invalidDamagedWeight");
                        errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
                        validationErrors.add(errorVO);
                    }
                }
                else{
                    acceptedBags = dsnvo.getAcceptedBags();
                    //acceptedWt = dsnvo.getAcceptedWeight();
                    acceptedWt = dsnvo.getAcceptedWeight().getRoundedSystemValue();//added by A-7371
                    log.log(Log.FINE, "acceptedBags--------> ", acceptedBags);
					log.log(Log.FINE, "acceptedWt--------> ", acceptedWt);
					if (totalBagsTwo > acceptedBags) {
                        ErrorVO errorVO = new ErrorVO(
                                "mailtracking.defaults.returndsn.msg.err.invalidDamagedBagsCount2");
                        errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
                        validationErrors.add(errorVO);
                    }
                    if (totalWtTwo > acceptedWt) {
                        ErrorVO errorVO = new ErrorVO(
                                "mailtracking.defaults.returndsn.msg.err.invalidDamagedWeight2");
                        errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
                        validationErrors.add(errorVO);
                    }
                    
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
                }
				
				
				
				break;
			}										
			index++;
		}

		log.exiting("SaveReturnDsnCommand","validateDsns");
		return validationErrors;
    }
 }
