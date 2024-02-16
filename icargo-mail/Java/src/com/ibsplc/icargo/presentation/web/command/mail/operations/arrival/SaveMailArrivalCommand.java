/*
 * SaveMailArrivalCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;

/**
 * @author A-5991
 *
 */
public class SaveMailArrivalCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "save_success";
   private static final String TARGET_FAILURE ="save_failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";	
   private static final String SAVE_SUCCESS = 
		"mailtracking.defaults.mailsubclassmaster.msg.info.savesuccess";
   private static final String SAME_DESPATCH_PRESENT="mailtracking.defaults.mailarrival.samedespatch";
   private static final String SCREEN_NUMERICAL_ID ="MTK007";
    
	 /**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("SaveMailAcceptanceCommand","execute");
    	  
    	MailArrivalForm mailArrivalForm = (MailArrivalForm)invocationContext.screenModel;
    	MailArrivalSession mailArrivalSession =	getScreenSession(MODULE_NAME,SCREEN_ID);
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//Added by A-4809 for CR ICMN-2345  ---->Starts  
    	String [] oprFlag= mailArrivalForm.getOperationalFlag();
    	log.log(Log.FINE, "<<<---operational Flag----->", oprFlag);
		
    	String[] conDocNo=mailArrivalForm.getCsgDocNum();
    	ArrayList<DSNVO> dSNVOs = null;
    	ArrayList<DespatchDetailsVO> despatchDetails = null;
    	DSNVO dsnvo = new DSNVO();
    	HashMap<String, Collection<DSNVO>> dsnvoMap = mailArrivalSession.getConsignmentMap();
    	MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
    	if(dsnvoMap==null) {
    		dsnvoMap = new HashMap<String,Collection<DSNVO>>();
    	}
    	Collection<MailbagVO> mailBagVOs =null;
    	//mailArrivalVO.setCompanyCode(logonAttributes.getCompanyCode());
    	log.log(Log.FINE, "<<<---mailArrivalVO----->", mailArrivalVO);
    	//mailArrivalVO.setAirportCode(logonAttributes.getAirportCode());
    	//mailArrivalSession.getMailArrivalFilterVO();
    	//mailArrivalSession.getContainerDetailsVO();
    	//mailArrivalSession.getContainerDetailsVOs();
    	
    	//mailArrivalVO.setFlightNumber(mailArrivalSession.getMailArrivalFilterVO().getFlightNumber());
		Collection<ContainerDetailsVO> containerDetailsVOs =  mailArrivalVO.getContainerDetails();
		if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
    		for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
    			containerDtlsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
    		}
    	}
		//Added by A-4809 for CR ICMN-2345  ---->Starts
		
				for(ContainerDetailsVO containerDetailsVO:containerDetailsVOs){
					 //Collection<ErrorVO> errors = new ArrayList<ErrorVO>(); 
					if(dSNVOs==null)
						{
						dSNVOs = new ArrayList<DSNVO>();
						}
					dSNVOs.addAll(containerDetailsVO.getDsnVOs());
					if(mailBagVOs==null)
						{
						mailBagVOs = new ArrayList<MailbagVO>();
						}
					 mailBagVOs.addAll(containerDetailsVO.getMailDetails());
					 if(despatchDetails==null){
						 despatchDetails = new ArrayList<DespatchDetailsVO>();
						 despatchDetails.addAll(containerDetailsVO.getDesptachDetailsVOs());
					 }
					 log.log(Log.FINE, "<<<---mailBagVOs----->", mailBagVOs);
					 log.log(Log.FINE, "<<<---dSNVOs----->", dSNVOs);
					/* for(MailbagVO mailbagVO:mailBagVOs){
							if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getUndoArrivalFlag())){
								if("I".equals(mailbagVO.getMraStatus())){
									errors.add(new ErrorVO("mailtracking.defaults.mailarrival.mailbagsimportedtoMRA"));
									invocationContext.addAllError(errors);
									invocationContext.target = TARGET_FAILURE;
									return;	
								}
							}	 
					 }   */                
					 for(DespatchDetailsVO despatchDetailsVO:despatchDetails){
						 StringBuilder sb = new StringBuilder(despatchDetailsVO.getOriginOfficeOfExchange());
						 sb.append(despatchDetailsVO.getDestinationOfficeOfExchange()).append(despatchDetailsVO.getMailCategoryCode()).append(despatchDetailsVO.getMailSubclass()).
						 append(despatchDetailsVO.getYear()).append(despatchDetailsVO.getDsn());
						 for(MailbagVO mailbagVO:mailBagVOs){
							 String mailBilling = mailbagVO.getMailbagId().substring(0, 20);
							 if(mailBilling.equalsIgnoreCase(sb.toString())){
								 /*errors.add(new ErrorVO(SAME_DESPATCH_PRESENT)); 
								 log.log(Log.FINE, "<<<---loop1 ----->");
								 break;*/
							 }
			//Added by A-5945 for ICRD-111434 starts
			if(mailbagVO.getDamagedMailbags()!=null ){
				Collection< DamagedMailbagVO> damagedVO =  mailbagVO.getDamagedMailbags();
			Collection< DamagedMailbagVO> damagedVO1 =  new ArrayList<DamagedMailbagVO>();
			for(DamagedMailbagVO dmgvo : damagedVO){
			if(dmgvo.getAirportCode()!= logonAttributes.getAirportCode()){
				damagedVO1.add(dmgvo);
			}
			}
			mailbagVO.setDamagedMailbags(damagedVO1);
				} 
			//Added by A-5945 for ICRD-111434 end
						 } 
					 }
				} 
		    	if(errors != null && errors.size()>0){
		    		invocationContext.addAllError(errors);
					invocationContext.target = TARGET_FAILURE;
					return;
				}
				if(oprFlag!=null && oprFlag.length >0){
				for(int i=0; i < oprFlag.length; i++){
					Collection<DSNVO> despatchVOs = null;
					if(ContainerDetailsVO.OPERATION_FLAG_UPDATE.equals(oprFlag[i]) && conDocNo[i] != null && conDocNo[i].length() > 0){
						dSNVOs.get(i).setCsgDocNum(conDocNo[i]);
						dsnvo = dSNVOs.get(i);
						if(dsnvoMap.containsKey(conDocNo[i]))
							{
							despatchVOs = dsnvoMap.get(conDocNo[i]);
							}
						else
							{
							despatchVOs = new ArrayList<DSNVO>();
							}
						despatchVOs.add(dsnvo);     
						dsnvoMap.put(conDocNo[i],despatchVOs);
					}
				}
				}
				//Added by A-4809 for CR ICMN-2345  ----->Ends
				//Added by A-4809 for BUG ICMN-3546  ...Starts
		    	/*added to prevent manually entering 
		    	 * consignment document number in 
		    	 * auto generated format
		    	 */
		    	Collection<ErrorVO> errorVos = new ArrayList<ErrorVO>();
		    	//mailArrivalSession.setConsignmentMap(dsnvoMap);
		    	errorVos =validateSave(mailArrivalForm);
		    	if(errorVos != null && errorVos.size()>0){
		    		invocationContext.addAllError(errorVos);
					invocationContext.target = TARGET_FAILURE;
					return;
		    	}
		  //Added by A-4809 for BUG ICMN-3546  ...Ends
		mailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
		mailArrivalVO.setMailSource(MailConstantsVO.MAIL_STATUS_ARRIVED);//Added for ICRD-156218
		mailArrivalVO.setMailDataSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
		log.log(Log.FINE, "Going To Save ...in command", mailArrivalVO);
		/*
		 * Construct lock vos for implicit locking
		 */
		Collection<LockVO> locks = prepareLocksForSave(mailArrivalVO);
		log.log(Log.FINE, "LockVO for implicit check", locks);
		if (locks == null || locks.size() == 0) {
			locks = null;
		}
		  try {
		    new MailTrackingDefaultsDelegate().saveArrivalDetails(mailArrivalVO,locks);
          }catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    	  }
		  if (errors != null && errors.size() > 0) {
				for(ErrorVO err : errors){
					if("mailtracking.defaults.err.mailAlreadyTransferedOrDelivered".equalsIgnoreCase(err.getErrorCode())){
						ErrorVO error = new ErrorVO("mailtracking.defaults.mailarrival.containertransferredordelivered");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						invocationContext.addError(error);
					}else{
						//Added as part of ICRD-147817 starts
						if("mailtracking.defaults.arrival.err.alreadyarrivedmailbag".equalsIgnoreCase(err.getErrorCode())){
							for(ContainerDetailsVO containerDetailsVO:containerDetailsVOs){
								Collection<MailbagVO>mailbagVOs=new ArrayList<MailbagVO>();
								mailbagVOs=containerDetailsVO.getMailDetails();
								for(MailbagVO mailbagVO:mailbagVOs){
									 if(MailConstantsVO.OPERATION_FLAG_UPDATE.equals(mailbagVO.getOperationalFlag()) ||
											MailConstantsVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())){ 
										mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
										containerDetailsVO.setReceivedBags(containerDetailsVO.getReceivedBags()-1);
										//containerDetailsVO.setReceivedWeight(containerDetailsVO.getReceivedWeight()-mailbagVO.getWeight());
										try {
											containerDetailsVO.setReceivedWeight(Measure.subtractMeasureValues(containerDetailsVO.getReceivedWeight(), mailbagVO.getWeight()));
										} catch (UnitException e) {
											// TODO Auto-generated catch block
											log.log(Log.SEVERE, "UnitException",e.getMessage());
										}
									}
								}
							}
							mailArrivalVO.setContainerDetails(containerDetailsVOs);
						}
						//Added as part of ICRD-147817 ends
						invocationContext.addAllError(errors);
					}
				}
				mailArrivalSession.setMailArrivalVO(mailArrivalVO);
	    		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    		invocationContext.target = TARGET;
	    		return;
    	  }
		//Added by A-4809 for CR ICMN-2345  ----->Starts
          try{
          createConsignmentVO(dsnvoMap,mailBagVOs);
          }catch(SystemException e){
        	  log.log(Log.FINE,  "Sys.Excptn ");
          }catch(BusinessDelegateException e){
        	  log.log(Log.FINE,  "BusinessDelegateException");
          }
        //Added by A-4809 for CR ICMN-2345  ----->Ends
    	  if (errors != null && errors.size() > 0) {
    		invocationContext.addAllError(errors);
    		mailArrivalSession.setMailArrivalVO(mailArrivalVO);
    		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    		invocationContext.target = TARGET;
    		return;
    	  }
    	
    	MailArrivalVO mailArriveVO = new MailArrivalVO(); 
    	//Added for ICRD-134007 starts
    	LocalDate arrivalDate=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
    	arrivalDate.setDate(mailArrivalForm.getArrivalDate());
    	mailArriveVO.setFlightCarrierCode(mailArrivalForm.getFlightCarrierCode());
    	mailArriveVO.setFlightNumber(mailArrivalForm.getFlightNumber());
    	mailArriveVO.setArrivalDate(arrivalDate);
    	//Added for ICRD-134007 starts
    	mailArrivalSession.setMailArrivalVO(mailArriveVO);
    	mailArrivalForm.setSaveSuccessFlag(FLAG_YES);//Added for ICRD-134007
    	//mailArrivalForm.setListFlag("FAILURE");//Commented for ICRD-134007
    	mailArrivalForm.setOperationalStatus("");
    	FlightValidationVO flightValidationVO = new FlightValidationVO();
    	mailArrivalSession.setFlightValidationVO(flightValidationVO);
    	//Added for SAA 403 STARTS
    	mailArrivalForm.setUnsavedDataFlag("");
//    	Added for SAA 403 ENDS
    	mailArrivalForm.setInitialFocus(FLAG_YES);
    	mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	//invocationContext.addError(new ErrorVO(SAVE_SUCCESS));//Commented for ICRD-134007
    	mailArrivalForm.setArrivalPort(logonAttributes.getAirportCode());
		invocationContext.target = TARGET;
    	log.exiting("SaveMailAcceptanceCommand","execute");
    	
    }
    /**
     * @author A-4809
     * Method to prevent manual save of consignment doc no
     * starting with ACM
     * 
     */
    private Collection<ErrorVO> validateSave(MailArrivalForm mailArrivalForm) {
     String [] conDocNo = mailArrivalForm.getCsgDocNum();
     String [] opFlag = mailArrivalForm.getOperationalFlag();
    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	if(conDocNo!=null && conDocNo.length>0){
    	for(int i=0;i< conDocNo.length;i++){
    		if(conDocNo[i] != null && conDocNo[i].length() > 0 && "U".equals(opFlag[i])){
    			if(conDocNo[i].startsWith("ACM")){
    				errors.add(new ErrorVO("mailtracking.defaults.mailarrival.wrongcsgdocformat"));
    			}
    		}
    	}
      }      
    return errors;
	}
	/**
     * @author A-4809
     * Method to save consignment document number for mailbags
     * @param dsnvoMap
     * @param containerDetailsVOForRef
     */
    private void createConsignmentVO(
			HashMap<String, Collection<DSNVO>> dsnvoMap,Collection<MailbagVO> mailBagVOs) 
    throws SystemException,BusinessDelegateException{
		log.entering("SaveMailArrivalCommnad","createConsignmentVO");
		Collection<String> csgDocs = dsnvoMap.keySet();
		LogonAttributes logon = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		Collection<ConsignmentDocumentVO> consignmentDocumentVOs = new ArrayList<ConsignmentDocumentVO>();
		for(String csgDocNum: csgDocs){
			ConsignmentDocumentVO consignmentDocVOForSave = new ConsignmentDocumentVO();
			List<MailInConsignmentVO> mailInConsignments = new ArrayList<MailInConsignmentVO>();
			Page<MailInConsignmentVO> mailVOs = null;
			Collection<DSNVO> dsnVOstoFiler = dsnvoMap.get(csgDocNum);
			Collection<DSNVO> dsnVOstoSave = null;
			log.log(Log.INFO, "----dsnvos taken from map-->>", dsnVOstoFiler);
			if(dsnVOstoFiler != null && dsnVOstoFiler.size() > 0) {
			   dsnVOstoSave = checkforDuplicateDSNsForSameConsignment(dsnVOstoFiler);
			}
			if(dsnVOstoSave != null && dsnVOstoSave.size() > 0){
				ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
				DSNVO dsnVOOne = dsnVOstoSave.iterator().next();
				OfficeOfExchangeVO officeOfExchangeVO= 
					new MailTrackingDefaultsDelegate().validateOfficeOfExchange(logon.getCompanyCode(),dsnVOOne.getOriginExchangeOffice());
				consignmentFilterVO.setCompanyCode(dsnVOOne.getCompanyCode());
				consignmentFilterVO.setPaCode(officeOfExchangeVO.getPoaCode());  
				consignmentFilterVO.setConsignmentNumber(dsnVOOne.getCsgDocNum());
				consignmentFilterVO.setPageNumber(1);  
				ConsignmentDocumentVO consignmentDocumentVO = null;
				try {
					consignmentDocumentVO = new MailTrackingDefaultsDelegate().
					findConsignmentDocumentDetails(consignmentFilterVO);
					if(consignmentDocumentVO == null){
						consignmentDocVOForSave.setOperationFlag(DSNVO.OPERATION_FLAG_INSERT);
						consignmentDocVOForSave.setConsignmentNumber(csgDocNum);
					}
					else{
						consignmentDocVOForSave.setOperationFlag(DSNVO.OPERATION_FLAG_UPDATE);
						consignmentDocVOForSave.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
						consignmentDocVOForSave.setConsignmentSequenceNumber(consignmentDocumentVO.getConsignmentSequenceNumber());
					}
				} catch (BusinessDelegateException businessDelegateException) {
					//businessDelegateException.getMessageVO().printStackTrace();
					handleDelegateException(businessDelegateException);
				}
				consignmentDocVOForSave.setCompanyCode(logon.getCompanyCode());
				consignmentDocVOForSave.setPaCode(officeOfExchangeVO.getPoaCode());
				consignmentDocVOForSave.setAirportCode(logon.getAirportCode());
				consignmentDocVOForSave.setOperation("I");
				consignmentDocVOForSave.setRemarks("From MailArrival");
				//Changed as part of Bug ICRD-116279 by A-5526 starts
				//Modified as part of CRQ ICRD-103713 by A-5526 starts
				if(MailConstantsVO.CN41_CATEGORY.equals(dsnVOOne.getMailCategoryCode()))
				{
				consignmentDocVOForSave.setType(MailConstantsVO.CONSIGNMENT_TYPE_CN41);
				}
				else if ((MailConstantsVO.CN37_CATEGORY_C.equals(
						dsnVOOne.getMailCategoryCode()))  || (MailConstantsVO.CN37_CATEGORY_D.equals(
								dsnVOOne.getMailCategoryCode()))) {
					consignmentDocVOForSave
					.setType(MailConstantsVO.CONSIGNMENT_TYPE_CN37);
				}
				else{
					consignmentDocVOForSave.setType(MailConstantsVO.CONSIGNMENT_TYPE_CN38);
				}
				//Modified as part of CRQ ICRD-103713 by A-5526 ends
				//Changed as part of Bug ICRD-116279 by A-5526 ends
				LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
				
				if(consignmentDocumentVO != null){
					if(consignmentDocumentVO.getConsignmentDate() != null){
						consignmentDocVOForSave.setConsignmentDate(consignmentDocumentVO.getConsignmentDate());
					}
				}
				else
				{
				consignmentDocVOForSave.setConsignmentDate(currentDate);
					
				}
				for(DSNVO dsnVO: dsnVOstoSave){
					if(DSNVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())){
						StringBuilder sb = new StringBuilder();
						sb.append(dsnVO.getOriginExchangeOffice());
						sb.append(dsnVO.getDestinationExchangeOffice());
						sb.append(dsnVO.getMailCategoryCode());
						sb.append(dsnVO.getMailSubclass());
						sb.append(dsnVO.getYear());
						sb.append(dsnVO.getDsn());  
						String mailbagIDToCompare=sb.toString();    
						for(MailbagVO mailBagVO:mailBagVOs){
							StringBuilder sbFromMailBagVO = new StringBuilder();
							sbFromMailBagVO.append(mailBagVO.getOoe());
							sbFromMailBagVO.append(mailBagVO.getDoe());
							sbFromMailBagVO.append(mailBagVO.getMailCategoryCode());
							sbFromMailBagVO.append(mailBagVO.getMailSubclass());
							sbFromMailBagVO.append(mailBagVO.getYear());
							sbFromMailBagVO.append(mailBagVO.getDespatchSerialNumber());  
							String mailbagFromMailBagVO=sbFromMailBagVO.toString();
							if(mailbagFromMailBagVO!=null && mailbagFromMailBagVO.equals(mailbagIDToCompare)){   
							MailInConsignmentVO mailConsignmentVO = new MailInConsignmentVO();
							mailConsignmentVO.setCompanyCode(consignmentDocVOForSave.getCompanyCode());
							mailConsignmentVO.setConsignmentNumber(consignmentDocVOForSave.getConsignmentNumber());
							mailConsignmentVO.setPaCode(consignmentDocVOForSave.getPaCode());
							mailConsignmentVO.setConsignmentSequenceNumber(mailBagVO.getConsignmentSequenceNumber());
							mailConsignmentVO.setDsn(mailBagVO.getDespatchSerialNumber());
							mailConsignmentVO.setOriginExchangeOffice(mailBagVO.getOoe());
							mailConsignmentVO.setDestinationExchangeOffice(mailBagVO.getDoe());
							mailConsignmentVO.setOperationFlag(MailInConsignmentVO.OPERATION_FLAG_INSERT);
							mailConsignmentVO.setMailId(mailBagVO.getMailbagId());
							mailConsignmentVO.setMailCategoryCode(mailBagVO.getMailCategoryCode());
							mailConsignmentVO.setMailSubclass(mailBagVO.getMailSubclass());
							mailConsignmentVO.setMailClass(mailBagVO.getMailClass());
							mailConsignmentVO.setYear(mailBagVO.getYear());
							mailConsignmentVO.setReceptacleSerialNumber(mailBagVO.getReceptacleSerialNumber());
							mailConsignmentVO.setHighestNumberedReceptacle(mailBagVO.getHighestNumberedReceptacle());
							mailConsignmentVO.setRegisteredOrInsuredIndicator(mailBagVO.getRegisteredOrInsuredIndicator());
							mailConsignmentVO.setStatedWeight(mailBagVO.getWeight());
							mailConsignmentVO.setStatedBags(1);
							mailConsignmentVO.setUldNumber(mailBagVO.getUldNumber());
							mailInConsignments.add(mailConsignmentVO);
						}
						}
				}
			}    
			}if(mailVOs == null){
				int _display_Page = 1; 
				int _startIndex = 1;  
				int _endIndex = 25;
				boolean _hasNext_Page = false;   
				int _total_Rec_Count = 0;  
				mailVOs = new Page<MailInConsignmentVO>(
						mailInConsignments,
						_display_Page,  
						MailConstantsVO.MAX_PAGE_LIMIT,
						mailInConsignments.size(),
						_startIndex,
						_endIndex,
						_hasNext_Page,
						_total_Rec_Count);
			}
			consignmentDocVOForSave.setMailInConsignmentVOs(mailVOs);
			consignmentDocumentVOs.add(consignmentDocVOForSave);
		}
		log.log(Log.INFO, "----consignmentDocumentVOs before save-->>",
				consignmentDocumentVOs);
		try{
			new MailTrackingDefaultsDelegate().saveConsignmentDocumentFromManifest(consignmentDocumentVOs);
		}catch(BusinessDelegateException e){
		//	e.getMessageVO().printStackTrace();
			handleDelegateException(e);
		}
		log.exiting("SaveMailArrivalCommnad","createConsignmentVO");
    }
    /**
	 * 	Method		:	SaveMailArrivalCommand.checkforDuplicateDSNsForSameConsignment
	 *	Added by 	:	A-4810 on 16-Sep-2015 for ICRD-126447
	 * 	Used for 	:   Checking Duplicates inDsnVos
	 *	Parameters	:	@param dsnVOstoSave
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<DSNVO>
	 */
	private Collection<DSNVO> checkforDuplicateDSNsForSameConsignment(
			Collection<DSNVO> dsnVOstoFilter) {
		Collection<DSNVO> dSNVOsToSave = new ArrayList<DSNVO>();
		HashMap<String, DSNVO> dsnvoDuplicateFilterMap = new HashMap<String, DSNVO>();
		for(DSNVO dsnvo:dsnVOstoFilter) {
			String key =  new StringBuilder(dsnvo.getOriginExchangeOffice()).append(dsnvo.getDestinationExchangeOffice()).append(dsnvo.getMailCategoryCode()).append(dsnvo.getMailSubclass()).
			 append(dsnvo.getYear()).append(dsnvo.getDsn()).toString();
			if(!dsnvoDuplicateFilterMap.containsKey(key)){
				dsnvoDuplicateFilterMap.put(key, dsnvo);
				dSNVOsToSave.add(dsnvo);
			}
		}
		return dSNVOsToSave;
    }
    /*
     * Added by Indu
     */
    private Collection<LockVO> prepareLocksForSave(
    		MailArrivalVO mailArrivalVO) {
    	log.log(Log.FINE, "\n prepareLocksForSave------->>", mailArrivalVO);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	Collection<LockVO> locks = new ArrayList<LockVO>();
    	Collection<ContainerDetailsVO> containerDetailsVOs =  mailArrivalVO.getContainerDetails();
    	
    	if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
    		for (ContainerDetailsVO conVO : containerDetailsVOs) {
    			if(conVO.getOperationFlag()!=null && conVO.getOperationFlag().trim().length()>0){
    				ArrayList<MailbagVO> mailbagvos=new ArrayList<MailbagVO>(conVO.getMailDetails());
    				if(mailbagvos!=null && mailbagvos.size()>0){
    				for(MailbagVO bagvo:mailbagvos){
    					if(bagvo.getOperationalFlag()!=null && bagvo.getOperationalFlag().trim().length()>0){
    						ULDLockVO lock = new ULDLockVO();
    		    			lock.setAction(LockConstants.ACTION_MAILARRIVAL);
    		    			lock.setClientType(ClientType.WEB);
    		    			lock.setCompanyCode(logonAttributes.getCompanyCode());
    		    			lock.setScreenId(SCREEN_ID);
    		    			lock.setStationCode(logonAttributes.getStationCode());
    		    			if(bagvo.getContainerForInventory() != null){
    		    				lock.setUldNumber(bagvo.getContainerForInventory());
    		    				lock.setDescription(bagvo.getContainerForInventory());
    		    				lock.setRemarks(bagvo.getContainerForInventory());
    		    			log.log(Log.FINE, "\n lock------->>", lock);
							locks.add(lock);
    					    }
    					}
    					   
    					}
    			}
    			
    		}
    	}
    	
    }
    	return locks;
    }
       
}
