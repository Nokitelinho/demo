/*
 * CloseFlightCommand.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.assignexceptions;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailExceptionVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.AssignExceptionSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.AssignExceptionsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2401
 *
 */
public class CloseFlightCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("MAILTRACKING MRA");
	private static final String CLASS_NAME = "Close Flight Command";
	private static final String MODULE_NAME = "mailtracking.mra.flown";
	private static final String SCREEN_ID = "mailtracking.mra.flown.assignexceptions";
	private static final String SELECT_ERR = "mailtracking.mra.flown.assignexceptios.msg.err.morethanonerowslected";
	private static final String CLOSE_SUCCESS = "close_success";
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		AssignExceptionSession assignExceptionSession = 
			(AssignExceptionSession)
		getScreenSession(MODULE_NAME, SCREEN_ID);
		AssignExceptionsForm assignExceptionsForm = (
				AssignExceptionsForm)invocationContext.screenModel;
		ArrayList<FlownMailExceptionVO> flownMailExceptionVOs = 
			new ArrayList<FlownMailExceptionVO>(assignExceptionSession.getExceptions());
		Collection<ErrorVO> errorvos = null;
		FlownMailFilterVO flownMailFilterVO = new FlownMailFilterVO();
		if(assignExceptionsForm.getSelectedElements()!=null){
			if(assignExceptionsForm.getSelectedElements().length > 1){
				log.log(log.FINE,"****error");
				ErrorVO errorVO = new ErrorVO(
						SELECT_ERR);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errorvos = new ArrayList<ErrorVO>();
				errorvos.add(errorVO);
			}
			else{
				int index = Integer.parseInt(assignExceptionsForm.getSelectedElements()[0]);
				log.log(Log.INFO, "****", assignExceptionsForm.getSelectedElements());
				flownMailFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
				flownMailFilterVO.setFlightCarrierId(
						flownMailExceptionVOs.get(index).getFlightCarrierId());
				flownMailFilterVO.setFlightNumber(
						flownMailExceptionVOs.get(index).getFlightNumber());
				flownMailFilterVO.setFlightSequenceNumber(
						flownMailExceptionVOs.get(index).getFlightSequenceNumber());
				flownMailFilterVO.setSegmentSerialNumber(
						flownMailExceptionVOs.get(index).getSegmentSerialNumber());			
	
				MailTrackingMRADelegate mailTrackingMRADelegate =  new MailTrackingMRADelegate();
				try{
					mailTrackingMRADelegate.closeFlight(flownMailFilterVO);
				}catch(BusinessDelegateException businessDelegateException){
					errorvos = handleDelegateException(businessDelegateException);
				}
			}
		}
		if (errorvos != null && errorvos.size() > 0) {	
	       	 invocationContext.addAllError(errorvos);
		}
		invocationContext.target = CLOSE_SUCCESS;	

		log.exiting(CLASS_NAME, "execute");
	}

}
