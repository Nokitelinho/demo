/*
 * DetachAWBCommand.java Created on Nov 9 2017
 *
 * Copyright 2017 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class DetachAWBCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";
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

		MailManifestForm mailManifestForm = (MailManifestForm) invocationContext.screenModel;
		MailManifestSession mailManifestSession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailManifestVO mailManifestVO = mailManifestSession.getMailManifestVO();

		ContainerDetailsVO containerDetailsVO = ((ArrayList<ContainerDetailsVO>) mailManifestVO
				.getContainerDetails()).get(Integer.parseInt(mailManifestForm
				.getParentContainer()));

		Collection<DSNVO> dsnVOs = containerDetailsVO.getDsnVOs();
		Collection<DSNVO> newDsnVOs = new ArrayList<DSNVO>();
		String[] selectedDsn = mailManifestForm.getSelectDSN();
		String selectedMails = selectedDsn[0];
		String[] primaryKey = selectedMails.split(",");

		int cnt = 0;
		int count = 0;
		int primaryKeyLen = primaryKey.length;
		log.log(Log.INFO, "primaryKeyLen------------>>", primaryKeyLen);
			if (dsnVOs != null && dsnVOs.size() != 0) {
				for (DSNVO dsnVO : dsnVOs) {
					String primaryKeyFromVO = String.valueOf(count);
					if ((cnt < primaryKeyLen)
							&& (primaryKeyFromVO.trim())
									.equalsIgnoreCase(primaryKey[cnt].trim())&&
									dsnVO.getMasterDocumentNumber()!=null&&
									!dsnVO.getMasterDocumentNumber().trim().isEmpty()) {
						newDsnVOs.add(dsnVO);
						containerDetailsVO.setDsnVOs(newDsnVOs);
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
		invocationContext.target = TARGET_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

}
