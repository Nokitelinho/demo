/*
 * AddNewMailTagDetailsCommand.java Created on Jul 1 2016
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class AddNewMailTagDetailsCommand   extends BaseCommand{

	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	private static final String CLASS_NAME = "AddNewMailTagDetailsCommand";

	private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
	private static final String ADDNEW_SUCCESS="addnew_success";
	private static final String ADDNEW_FAILURE="addnew_failure";
	
	/**
	 * Execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		
		MailAcceptanceForm mailAcceptanceForm = 
    		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ArrayList<MailbagVO> selectedMailVOsVal = new ArrayList<MailbagVO>();
		
		Collection<ErrorVO> errors = null;
		ArrayList<MailbagVO> selectedMailVOs = new ArrayList<MailbagVO>();
		ArrayList<MailbagVO> selectedDummyMailVOs = new ArrayList<MailbagVO>();
		MailbagVO currentMailVO = null;
		Page<MailbagVO> mainMailVOs = null ;
		ContainerDetailsVO containerVO = mailAcceptanceSession.getContainerDetailsVO();
    	containerVO = mailAcceptanceSession.getContainerDetailsVO();
    	if(containerVO != null) {
			if(containerVO.getMailDetails()!= null && 
					containerVO.getMailDetails().size() > 0) {
				//Page<RateLineVO> oldratespage=new Page<RateLineVO>(ratelinevos,1,0,ss,0,0,false);
				mainMailVOs =  new Page<MailbagVO>(
						new ArrayList<MailbagVO>(containerVO.getMailDetails()), 1, 0, containerVO.getMailDetails().size(), 0, 0,false);
				selectedMailVOsVal= new ArrayList<MailbagVO>(mainMailVOs);
			}else {
				mainMailVOs = new Page<MailbagVO>(
						new ArrayList<MailbagVO>(), 0, 0, 0, 0, 0,false);
			}
		}
    	selectedMailVOs = mailAcceptanceSession.getSelectedMailDetailsVOs();
		currentMailVO = mailAcceptanceSession.getCurrentMailDetail();

		int displayPageNum = Integer.parseInt(mailAcceptanceForm.getDisplayPage());
		errors = validateCurrentMailDetail(currentMailVO);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			mailAcceptanceForm.setCurrentPageNum(String.valueOf(displayPageNum));
			mailAcceptanceForm.setDisplayPage(String.valueOf(displayPageNum));
			mailAcceptanceForm.setTotalRecords(String.valueOf(selectedMailVOs.size()));
			invocationContext.target = ADDNEW_FAILURE;
			return;
		}
		if(selectedMailVOs == null) {
			selectedMailVOs = new ArrayList<MailbagVO>();
		}
		//Max Seq Id From selectedMailVOs & mainMailVOs
		int maxSeqId = 0;
		int selectedMaxSeqId = 0 ;
		int mainMaxSeqId = 0 ;
		if(selectedMailVOs.size() > 0) {
			selectedMaxSeqId = selectedMailVOs.get(0).getSeqIdentifier();
			for(MailbagVO dummyVO : selectedMailVOs) {
				int dummySeqId = dummyVO.getSeqIdentifier();
				if(dummySeqId > selectedMaxSeqId) {
					selectedMaxSeqId = dummySeqId;
				}
			}			
		}
		if(mainMailVOs.size() > 0) {
			mainMaxSeqId = mainMailVOs.get(0).getSeqIdentifier();
			for(MailbagVO dummyVO : mainMailVOs) {
				int dummySeqId = dummyVO.getSeqIdentifier();
				if(dummySeqId > mainMaxSeqId) {
					mainMaxSeqId = dummySeqId;
				}
			}			
		}
		if(selectedMaxSeqId > mainMaxSeqId) {
			maxSeqId = selectedMaxSeqId;
		}else {
			maxSeqId = mainMaxSeqId;
		}
		//Adding Exsisting current VO To Session
		if(currentMailVO != null) {
			if(currentMailVO.getSeqIdentifier() == 0 ) {
				currentMailVO.setSeqIdentifier(maxSeqId+1);
			}			
			boolean alreadyExist = false;
			if(selectedMailVOs != null && selectedMailVOs.size() > 0) {
				for(MailbagVO selectedMailVO : selectedMailVOs) {
					if(selectedMailVO.getSeqIdentifier() == currentMailVO.getSeqIdentifier()) {
						try {
							BeanHelper.copyProperties(selectedMailVO, currentMailVO);
						} catch (SystemException e) {
							ErrorVO error = e.getError();
							errors.add(error);
						}
						alreadyExist = true;
					}
				}
			}
			if(!alreadyExist){
				selectedDummyMailVOs.add(currentMailVO);
			}
			if(selectedDummyMailVOs != null && selectedDummyMailVOs.size() > 0) {
				selectedMailVOs.addAll(selectedDummyMailVOs);
			}
		}
		
		
		mailAcceptanceSession.setSelectedMailDetailsVOs(selectedMailVOs);
		log.log(Log.FINE, "selectedMailVOs.size()-", selectedMailVOs.size());
		log.log(Log.FINE, "mainMailVOs.size()-", selectedMailVOs.size());
		// New MailDetailsVO set as Current VO
		currentMailVO = new MailbagVO();
		currentMailVO.setOperationalFlag("I");
		if(selectedMailVOs!=null){
			if(selectedMailVOs.size()>0){
				MailbagVO bagVO=selectedMailVOs.get(selectedMailVOs.size()-1);
				if(bagVO!=null){
					currentMailVO.setOoe(bagVO.getOoe());
					currentMailVO.setDoe(bagVO.getDoe());
					currentMailVO.setMailCategoryCode(bagVO.getMailCategoryCode());
					currentMailVO.setMailSubclass(bagVO.getMailSubclass());
					currentMailVO.setYear(bagVO.getYear());
					currentMailVO.setDespatchSerialNumber(bagVO.getDespatchSerialNumber());
					currentMailVO.setHighestNumberedReceptacle(bagVO.getHighestNumberedReceptacle());
					currentMailVO.setRegisteredOrInsuredIndicator(bagVO.getRegisteredOrInsuredIndicator());
					currentMailVO.setCarrierCode(bagVO.getCarrierCode());
					currentMailVO.setScannedDate(bagVO.getScannedDate());

				}
			}else if(selectedMailVOs.size()==0){
				LocalDate sdate=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
				if(mailAcceptanceForm.getHiddenScanDate()!=null && mailAcceptanceForm.getHiddenScanDate().trim().length()>0) {
					sdate.setDate(mailAcceptanceForm.getHiddenScanDate());
				}
				currentMailVO.setScannedDate(sdate);
				String currentDate = sdate.toDisplayDateOnlyFormat();
				int lastDigitOfYear = Integer.parseInt(currentDate.substring(10,11));
				currentMailVO.setYear(lastDigitOfYear);		
				log.log(Log.INFO, "SELECTED MAILBAGS 0", currentMailVO);
			}
		}
		
		mailAcceptanceSession.setCurrentMailDetail(currentMailVO);
		
		mailAcceptanceForm.setDisplayPage(String.valueOf(displayPageNum+1));
		mailAcceptanceForm.setLastPageNum(String.valueOf(displayPageNum+1));
		mailAcceptanceForm.setCurrentPageNum(String.valueOf(displayPageNum+1));
		//for disablinh the last.....
		mailAcceptanceForm.setTotalRecords(String.valueOf(selectedMailVOs.size()+1));
		
		//mailAcceptanceForm.setPopUpCloseFlag("N");
		invocationContext.target=ADDNEW_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}

	/**
	 * This method is used for validating the Current MailDetailsVO
	 * @param mailbagVO
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateCurrentMailDetail(MailbagVO mailbagVO) {
		return new ArrayList<ErrorVO>();
	}
}
