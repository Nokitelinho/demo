/*
 * OkMailTagDetailsCommand.java Created on Jul 1 2016
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.BeanHelper;
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
public class OkMailTagDetailsCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	private static final String CLASS_NAME = "OkMailTagDetailsCommand";

	 private static final String MODULE_NAME = "mail.operations";
	  private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";
	private static final String OK_SUCCESS="ok_success";
	private static final String OK_FAILURE="ok_failure";
	//private static final String UNSAVED_DATA = "unsaveddata";

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
		//ApplicationSessionImpl applicationSession = getApplicationSession();
	//	LogonAttributes logonAttributes = applicationSession.getLogonVO();

		Collection<ErrorVO> errors = null;
		Page<MailbagVO> mainMailVOs = null ;
		ArrayList<MailbagVO> selectedMailVOs = new ArrayList<MailbagVO>();
		ArrayList<MailbagVO> selectedDummymailVOs = new ArrayList<MailbagVO>();
		MailbagVO currentMailVO = new MailbagVO();

		ContainerDetailsVO containerVO = new ContainerDetailsVO();
		containerVO = mailAcceptanceSession.getContainerDetailsVO();
		if(containerVO != null) {
			if(containerVO.getMailDetails()!= null &&
					containerVO.getMailDetails().size() > 0) {
				//Page<RateLineVO> oldratespage=new Page<RateLineVO>(ratelinevos,1,0,ss,0,0,false);
				mainMailVOs =  new Page<MailbagVO>(
						new ArrayList<MailbagVO>(containerVO.getMailDetails()), 1, 0, containerVO.getMailDetails().size(), 0, 0,false);

			}else {
				mainMailVOs = new Page<MailbagVO>(
						new ArrayList<MailbagVO>(), 0, 0, 0, 0, 0,false);
			}
		}
		selectedMailVOs = mailAcceptanceSession.getSelectedMailDetailsVOs();
		if(selectedMailVOs == null) {
			selectedMailVOs = new ArrayList<MailbagVO>();
		}
		currentMailVO = mailAcceptanceSession.getCurrentMailDetail();
		int currentIndex = Integer.parseInt(mailAcceptanceForm.getCurrentPageNum())-1;
		errors = validateCurrentMailDetail(currentMailVO);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			mailAcceptanceForm.setDisplayPage(String.valueOf(currentIndex+1));
			mailAcceptanceForm.setCurrentPageNum(String.valueOf(currentIndex+1));
			mailAcceptanceForm.setTotalRecords(String.valueOf(selectedMailVOs.size()-1));
			invocationContext.target = OK_FAILURE;
			return;
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
				selectedDummymailVOs.add(currentMailVO);
			}
			if(selectedDummymailVOs != null && selectedDummymailVOs.size() > 0) {
				selectedMailVOs.addAll(selectedDummymailVOs);
			}
		}




		//Adding Exsisting VOs To Main Session
		if(selectedMailVOs != null && selectedMailVOs.size() > 0) {
			if(mainMailVOs != null && mainMailVOs.size() > 0) {
				for(MailbagVO selectedmailVO : selectedMailVOs) {
					for(MailbagVO mainMailVO : mainMailVOs) {
						mainMailVO.setDisplayLabel("Y");
						if(selectedmailVO.getSeqIdentifier() == mainMailVO.getSeqIdentifier()) {
							try {
								BeanHelper.copyProperties(mainMailVO, selectedmailVO);
							} catch (SystemException e) {
								ErrorVO error = e.getError();
								errors.add(error);
							}
							break;
						}else if(selectedmailVO.getSeqIdentifier() > mainMaxSeqId){
							mainMailVOs.add(selectedmailVO);
							break;
						}
					}
				}
			}else {
				mainMailVOs.addAll(selectedMailVOs);
			}
		}
		mailAcceptanceSession.getContainerDetailsVO().setMailDetails(mainMailVOs);
		mailAcceptanceSession.setSelectedMailDetailsVOs(selectedMailVOs);
		log.log(Log.INFO, "selectedMailVOs ", selectedMailVOs);
		if(currentMailVO.getStrWeight()!=null){
		mailAcceptanceForm.setPopupCloseFlag("YES");   //added by A-8353 for ICRD-274933 
		}
		//mailAcceptanceForm.setScreenFlg("unsaveddata");
		invocationContext.target=OK_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}

	/**
	 * This method is used for validating the Current mailbagvo
	 * @param mailbagvo
	 * @return  Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateCurrentMailDetail(MailbagVO mailbagvo) {
		return new ArrayList<ErrorVO>();
	}
}
