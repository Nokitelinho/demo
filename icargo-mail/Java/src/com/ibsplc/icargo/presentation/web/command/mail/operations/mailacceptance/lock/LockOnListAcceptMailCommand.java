/*
 * LockOnListAcceptMailCommand.java Created on Jan 23, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance.lock;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.lock.LockCommand;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 *
 * @author a-2886
 *
 */


public class LockOnListAcceptMailCommand extends LockCommand {

	private Log log = LogFactory.getLogger("MAILOPERATIONS");

	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "mail.operations";
	/*
	 * The ScreenID
	 */
	private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.lock.LockCommand#getLockDetails(com.ibsplc.icargo.framework.model.ScreenModel)
	 */
	@Override
	protected LockVO[] getLockDetails(ScreenModel screenModel) {
		log.entering("AcceptMailCommand","getLockDetails");
		MailAcceptanceForm mailAcceptanceForm =
    		(MailAcceptanceForm)screenModel;

		LockVO[] lockVO = getLock(mailAcceptanceForm);
		log.exiting("AcceptMailCommand","getLockDetails");
		return lockVO;
	}

	private LockVO[] getLock(MailAcceptanceForm mailAcceptanceForm) {

		log.entering("AcceptMailCommand","getUldnumberLock");

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		//Obtaining the Session
		MailAcceptanceSession mailAcceptanceSession =
			getScreenSession(MODULE_NAME,SCREEN_ID);
		LockVO[] lockVOs = null;

		ContainerDetailsVO containerDetailsVO
						= mailAcceptanceSession.getContainerDetailsVO();

		ULDLockVO uldLockVO = new ULDLockVO();
		uldLockVO.setAction(LockConstants.ACTION_ACCEPTMAIL);
		uldLockVO.setClientType(ClientType.WEB);
		uldLockVO.setCompanyCode(logonAttributes.getCompanyCode());
		uldLockVO.setDescription("ACCEPTMAIL_LOCK");
		uldLockVO.setRemarks("ACCEPTMAIL LOCKING");
		uldLockVO.setScreenId(SCREEN_ID);
		uldLockVO.setStationCode(logonAttributes.getStationCode());

		String containerNum = containerDetailsVO.getContainerNumber();

    	boolean isULDType = false;
		if(containerNum.length()>= 3) {
			String containerPart = containerNum.substring(0,3);
			log.log(Log.FINE, "$$$$$$containerPart------->>", containerPart);
			Collection<String> containerParts = new ArrayList<String>();
			containerParts.add(containerPart);
			try {
				new ULDDelegate().validateULDTypeCodes(
					logonAttributes.getCompanyCode(),containerParts);
				isULDType = true;
			}catch (BusinessDelegateException businessDelegateException) {
				isULDType = false;
			}
		}
    	
		//log.log(Log.FINE,"ContainerType------->>"+containerDetailsVO.getContainerType());
		if(isULDType){
			//log.log(Log.FINE,"ContainerNumber------->>"+containerDetailsVO.getContainerNumber());
			uldLockVO.setUldNumber(containerDetailsVO.getContainerNumber());
			lockVOs = new LockVO[1];
			//log.log(Log.FINE,"setting lock vos------->>");
			lockVOs[0] = uldLockVO;
		}
		return lockVOs;
	}
}
