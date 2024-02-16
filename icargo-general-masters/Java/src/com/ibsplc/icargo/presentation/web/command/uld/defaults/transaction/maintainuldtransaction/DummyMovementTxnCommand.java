/*
 * DummyMovementTxnCommand.java  Created on Feb 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.RecordUldMovementSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.RecordULDMovementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-1876
 *
 */
public class DummyMovementTxnCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("ULD_DEFAULTS");

	private static final String SCREEN_ID_ONE = "uld.defaults.loanborrowuld";

	private static final String SAVE_SUCCESS = "save_success";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 *
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("DummyMovementTxnCommand", "execute");
		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				"uld.defaults", SCREEN_ID_ONE);
		RecordULDMovementForm recordULDMovementForm = (RecordULDMovementForm) invocationContext.screenModel;
		RecordUldMovementSession recordUldMovementSession = (RecordUldMovementSession) getScreenSession(
				"uld.defaults", "uld.defaults.misc.recorduldmovement");

		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		Collection<LockVO> locks = new ArrayList<LockVO>();
		locks = prepareLocksForSave(recordUldMovementSession.getULDNumbers());
		try {
			delegate.saveULDMovement(recordUldMovementSession.getULDNumbers(),
					recordUldMovementSession.getULDMovementVOs(),"ULD_TXN","N",locks);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}

		if (recordULDMovementForm.getPageurl() != null
				&& (("LoanBorrow").equals(recordULDMovementForm.getPageurl())
						|| ("fromulderrorlogforborrowDamage").equals(recordULDMovementForm.getPageurl())
						|| ("fromulderrorlogforloanDamage").equals(recordULDMovementForm.getPageurl()) ||
						("fromulderrorlogforloanDamage").equals(recordULDMovementForm.getPageurl())||
						("fromScmReconcileBorrowDamage").equals(recordULDMovementForm.getPageurl()))) {
			loanBorrowULDSession.setPageURL(recordULDMovementForm.getPageurl());

		}

		invocationContext.target = SAVE_SUCCESS;
		log.exiting("DummyMovementTxnCommand", "execute");
	}
	/*
	 * Added by ayswarya
	 */
	private Collection<LockVO> prepareLocksForSave(
			Collection<String>  uldNumbers) {
		log.entering("prepareLocksForSave","uldNumbers");
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<LockVO> locks = new ArrayList<LockVO>();

		if (uldNumbers != null && uldNumbers.size() > 0) {
		log.log(Log.FINE, "\n uldNumbers------->>", uldNumbers);
		log
				.log(Log.FINE, "\n uldNumbers.length 1b------->>", uldNumbers.size());
			for (int i = 0; i < uldNumbers.size(); i++) {
				ULDLockVO lock = new ULDLockVO();
				lock.setAction(LockConstants.ACTION_RECORDULDMOVEMENT);
				lock.setClientType(ClientType.WEB);
				lock.setCompanyCode(logonAttributes.getCompanyCode());
				lock.setScreenId(SCREEN_ID_ONE);
				lock.setStationCode(logonAttributes.getStationCode());
				lock.setUldNumber(uldNumbers.toString().toUpperCase());
				lock.setDescription(uldNumbers.toString().toUpperCase());
				lock.setRemarks(uldNumbers.toString().toUpperCase());
				log.log(Log.FINE, "\n lock------->>", lock);
				locks.add(lock);
			}
		}
		log.exiting("prepareLocksForSave","locks");
		return locks;
	}

}
