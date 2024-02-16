/*
 * SaveMailArrivalCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class SaveMailManifestCommand extends BaseCommand {
	
	private  Log log = LogFactory.getLogger("MRA MAIL MANIFEST SAVE");	

	private static final String CLASS_NAME = "SaveMailManifestCommand";

	private static final String MODULE_NAME = "mail.operations";

	private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";

	private static final String ACTION_SUCCESS = "save_success";

	private static final String ACTION_FAILURE = "save_failure";

	private static final String INFO_SUCCESS = "mailtracking.defaults.mailmanifest.msg.info.savesuccess";
	
	
	/**
	 * Method to save consignment document number of mailbags
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException{

		log.entering(CLASS_NAME,"execute");

		MailManifestForm mailManifestForm = (MailManifestForm)invocationContext.screenModel;

		MailManifestSession mailManifestSession = (MailManifestSession)getScreenSession(MODULE_NAME, SCREEN_ID);

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		ArrayList<DSNVO> dSNVOs = null;
		String[] oprFlag=mailManifestForm.getOperationalFlag();
		String[] conDocNo=mailManifestForm.getCsgDocNum();
		DSNVO dsnvo = new DSNVO();		
		HashMap<String, Collection<DSNVO>> dsnvoMap = new HashMap<String,Collection<DSNVO>>();
		MailManifestVO mailManifestVO = mailManifestSession.getMailManifestVO();
		ContainerDetailsVO containerDetailsVOForRef = mailManifestVO.getContainerDetails().iterator().next();
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		containerDetailsVOs = mailManifestVO.getContainerDetails();
		log.log(Log.INFO, "containerDetailsVOs.size :::: ", containerDetailsVOs.size());
		for(ContainerDetailsVO containerDetailsVO:containerDetailsVOs){
			if(dSNVOs==null)
				{
				dSNVOs = new ArrayList<DSNVO>();
				}
			dSNVOs.addAll(containerDetailsVO.getDsnVOs());

		}		
		if(oprFlag!=null){
		for(int i=0; i < oprFlag.length; i++){
			Collection<DSNVO> despatchVOs = null;
			if(("U").equals(oprFlag[i]) && conDocNo[i] != null && conDocNo[i].length() > 0){				
				dSNVOs.get(i).setCsgDocNum(conDocNo[i]);				
				dsnvo = dSNVOs.get(i);
				if(dsnvoMap.containsKey(conDocNo[i])) {
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
		Collection<ErrorVO> errorVos = new ArrayList<ErrorVO>();		
    	errorVos =validateSave(mailManifestForm);

    	if(errorVos != null && errorVos.size()>0){
    		invocationContext.addAllError(errorVos);
			invocationContext.target = ACTION_FAILURE;
			return;
    	}
		try{		
			createConsignmentVO(dsnvoMap,containerDetailsVOForRef);
		}catch(SystemException e){
			log.log(Log.FINE,  "Sys.Excptn in findAirlineAirportParameters");
		}catch(BusinessDelegateException e){
		
			errors = handleDelegateException(e);
		}

		if(errors!=null && errors.size()>0){    	
			invocationContext.addAllError(errors);
			invocationContext.target =ACTION_FAILURE;
		}else{
			ErrorVO error = new ErrorVO(INFO_SUCCESS);			
			error.setErrorDisplayType(ErrorDisplayType.INFO);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = ACTION_SUCCESS;
		} 
		log.exiting(CLASS_NAME,"execute");
	}
	/**
	 * @author A-4809
	 * Method to prevent manual save of consignment doc no
     * starting with ACM
	 */
	
	private Collection<ErrorVO> validateSave(MailManifestForm mailManifestForm) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String [] conDocNo = mailManifestForm.getCsgDocNum();		
		String [] opFlag = mailManifestForm.getOperationalFlag();
			if(conDocNo != null && conDocNo.length>0){				
				for(int i=0;i<conDocNo.length;i++){
		    		if(conDocNo[i] != null && conDocNo[i].length() > 0 && "U".equals(opFlag[i])){
		    			if(conDocNo[i].startsWith("ACM")){		    				
		    				errors.add(new ErrorVO("mailtracking.defaults.mailmanifest.err.wrongcsgdocformat"));
		    			}
		    		}
		    	
				}
			}
		return errors;
	}
	/**
	 * @author A-4809
	 * @param dsnvoMap 
	 * @param mailManifestVO
	 */
	private void createConsignmentVO(   
			HashMap<String, Collection<DSNVO>> dsnvoMap,ContainerDetailsVO containerDetailsVOForRef) throws 
			SystemException,BusinessDelegateException{
		log.entering(CLASS_NAME,"createConsignmentVO");
		Collection<String> csgDocs = dsnvoMap.keySet();
		LogonAttributes logon = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		Collection<ConsignmentDocumentVO> consignmentDocumentVOs = new ArrayList<ConsignmentDocumentVO>();				
		for(String csgDocNum: csgDocs){
			
			
			ConsignmentDocumentVO consignmentDocVOForSave = new ConsignmentDocumentVO();
			List<MailInConsignmentVO> mailInConsignments = new ArrayList<MailInConsignmentVO>();
			Page<MailInConsignmentVO> mailVOs = null;			
			Collection<DSNVO> dsnVOstoSave = dsnvoMap.get(csgDocNum);						
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
				consignmentDocVOForSave.setOperation("O");
				consignmentDocVOForSave.setRemarks("From MailManifest");
				//Changed as part of Bug ICRD-140100 by A-5526 starts
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
				//Changed as part of Bug ICRD-140100 by A-5526 ends
				LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
				consignmentDocVOForSave.setConsignmentDate(currentDate);
				for(DSNVO dsnVO: dsnVOstoSave){
					if(DSNVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())){
						dsnVO.setCarrierId(containerDetailsVOForRef.getCarrierId());
						dsnVO.setFlightNumber(containerDetailsVOForRef.getFlightNumber());
						dsnVO.setFlightSequenceNumber(containerDetailsVOForRef.getFlightSequenceNumber());
						dsnVO.setSegmentSerialNumber(containerDetailsVOForRef.getSegmentSerialNumber());
						try{  
							Collection<MailbagVO> mailBagVOs = new ArrayList<MailbagVO>();

							mailBagVOs = new MailTrackingDefaultsDelegate().findDSNMailbags(dsnVO);
							for(MailbagVO mailBagVO:mailBagVOs){
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
						}catch(BusinessDelegateException businessDelegateException) {
							//businessDelegateException.getMessageVO().printStackTrace();
							handleDelegateException(businessDelegateException);
						}
					}   
				} 
			}
			if(mailVOs == null){
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
		try{
			log.log(Log.INFO, " before saveConsignmentDocumentFromManifest");			
			new MailTrackingDefaultsDelegate().saveConsignmentDocumentFromManifest(consignmentDocumentVOs);
			log.log(Log.INFO, " after saveConsignmentDocumentFromManifest");
		}catch(BusinessDelegateException e){
		//	e.getMessageVO().printStackTrace();
			handleDelegateException(e);

		}
		log.exiting(CLASS_NAME,"createConsignmentVO");
		
	}
       
}
