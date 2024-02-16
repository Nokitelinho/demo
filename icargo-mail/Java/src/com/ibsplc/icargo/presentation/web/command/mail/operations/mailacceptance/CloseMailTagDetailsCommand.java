/*
 * CloseMailTagDetailsCommand.java Created on Jul 1 2016
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
public class CloseMailTagDetailsCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAIL  OPERATIONS");
	private static final String CLASS_NAME = "CloseMailTagDetailsCommand";

	private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
	private static final String CLOSE_SUCCESS="close_success";
	
	
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
	//	ApplicationSessionImpl applicationSession = getApplicationSession();
		

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Page<MailbagVO> mainMailVOs = null ;
		Collection<MailbagVO> selectedMailVOs =null;
		ContainerDetailsVO containerVO = mailAcceptanceSession.getContainerDetailsVO();
		containerVO = mailAcceptanceSession.getContainerDetailsVO();
		if(containerVO != null) {
			if(containerVO.getMailDetails()!= null && 
					containerVO.getMailDetails().size() > 0) {
				//Page<RateLineVO> oldratespage=new Page<RateLineVO>(ratelinevos,1,0,ss,0,0,false);
				mainMailVOs =  new Page<MailbagVO>(
						new ArrayList<MailbagVO>(containerVO.getMailDetails()), 1, 0, containerVO.getMailDetails().size(), 0, 0,false);
				//selectedMailVOsVal= new ArrayList<MailbagVO>(mainMailVOs);
			}else {
				mainMailVOs = new Page<MailbagVO>(
						new ArrayList<MailbagVO>(), 0, 0, 0, 0, 0,false);
			}
		}
		selectedMailVOs = mailAcceptanceSession.getSelectedMailDetailsVOs();
		if(selectedMailVOs == null) {
			selectedMailVOs = new ArrayList<MailbagVO>();
		}
		int currentIndex = Integer.parseInt(mailAcceptanceForm.getCurrentPageNum())-1;
		
		if(selectedMailVOs != null && selectedMailVOs.size() > 0) {
			if(mainMailVOs != null && mainMailVOs.size() > 0) {
				for(MailbagVO selectedmailVO : selectedMailVOs) {
					for(MailbagVO mainmailVO : mainMailVOs) {
						if(selectedmailVO.getSeqIdentifier() == mainmailVO.getSeqIdentifier()) {
							try {
								BeanHelper.copyProperties(mainmailVO, selectedmailVO);
							} catch (SystemException e) {
								ErrorVO error = e.getError(); 
								errors.add(error);
							}
							break;
						}
					}
				}
			}else {
				//  COMMENTED FOR BUG 85658 
			//	mainMailVOs = (Page<MailbagVO>)selectedMailVOs;
			}
		}
		log.log(Log.FINE, "selectedMailVOs.size()-", selectedMailVOs.size());
		log.log(Log.FINE, "mainMailVOs.size()-", mainMailVOs.size());
		mailAcceptanceSession.getContainerDetailsVO().setMailDetails(mainMailVOs);
		mailAcceptanceSession.setSelectedMailDetailsVOs((ArrayList<MailbagVO>)selectedMailVOs);


		mailAcceptanceForm.setPopupCloseFlag("YES");
		invocationContext.target=CLOSE_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}

}
