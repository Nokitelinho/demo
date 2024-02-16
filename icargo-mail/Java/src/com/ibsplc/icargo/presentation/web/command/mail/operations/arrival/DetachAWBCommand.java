/*
 * DetachAWBCommand.java Created on Nov 9 2017
 *
 * Copyright 2017 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class DetachAWBCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
	private static final String CLASS_NAME = "DetachAWBCommand";
	private static final String TARGET_SUCCESS = "success";
	private static final String CANNOT_BE_DETACHED ="mailtracking.defaults.detachawb.msg.err.cannotbedetached";
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command
	 *							#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: U-1267 on 09-Nov-2017
	 * 	Used for 	:	ICRD-211205
	 *	Parameters	:	@param invocationContext
	 */
	public void execute(InvocationContext invocationContext) {
		log.entering(CLASS_NAME, "execute");

		MailArrivalForm mailArrivalForm = (MailArrivalForm) invocationContext.screenModel;
		MailArrivalSession mailArrivalSession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
		String[] primaryKey =mailArrivalForm.getChildCont().split(",");
		ContainerDetailsVO containerDetailsVO = ((ArrayList<ContainerDetailsVO>) mailArrivalVO
				.getContainerDetails()).get(Integer.parseInt(primaryKey[0].split("~")[0].trim()));
		Collection<DSNVO> dsnVOs = containerDetailsVO.getDsnVOs();
		Collection<DSNVO> selectedDsnVOs=new ArrayList<DSNVO>();
		int cnt = 0;
		int count = 0;
		int primaryKeyLen = primaryKey.length;
		if (primaryKey[0].contains("~")) {
			for(DSNVO dsnVO:dsnVOs){
				String primaryKeyFromVO = String.valueOf(count);
				if ((cnt < primaryKeyLen)
						&& (primaryKeyFromVO.trim())
								.equalsIgnoreCase(primaryKey[cnt].split("~")[1].trim())&&
								dsnVO.getMasterDocumentNumber()!=null&&
								!dsnVO.getMasterDocumentNumber().trim().isEmpty()) {
					selectedDsnVOs.add(dsnVO);
					containerDetailsVO.setDsnVOs(selectedDsnVOs);
					cnt++;
				}
				count++;
			}
		}
		Iterator<DSNVO> iterator=containerDetailsVO.getDsnVOs().iterator();
		while(iterator.hasNext()){
			DSNVO dsnVO=iterator.next();
			String documentNumber=dsnVO.getMasterDocumentNumber();
			if(documentNumber==null||documentNumber.trim().isEmpty()){
				invocationContext.addError(new ErrorVO(
						CANNOT_BE_DETACHED));
				invocationContext.target = TARGET_SUCCESS;
				return;
			}
		}
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		try {
			mailTrackingDefaultsDelegate
					.detachAWBDetails(containerDetailsVO);

		} catch (BusinessDelegateException businessDelegateException) {	
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_SUCCESS;
		}
		mailArrivalForm.setSaveSuccessFlag(MailConstantsVO.FLAG_YES);
		invocationContext.target = TARGET_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

}
