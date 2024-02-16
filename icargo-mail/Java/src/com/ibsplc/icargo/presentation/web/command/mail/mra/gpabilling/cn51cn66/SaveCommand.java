/*
 * SaveCommand.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51cn66;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListCN51CN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListCN51CN66Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class SaveCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA_GPABILLING");

	private static final String CLASS_NAME = "SaveCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51cn66";

	private static final String SAVE_SUCCESS = "savedetails_success";

	private static final String EMPTY_STRING= "";


	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");

		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
		ListCN51CN66Form form = (ListCN51CN66Form)invocationContext.screenModel;
		ListCN51CN66Session session = (ListCN51CN66Session)getScreenSession(MODULE_NAME, SCREENID);
		Collection<CN66DetailsVO> cn66Vos =  null;
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		String userId = getApplicationSession().getLogonVO().getUserId();
		String[] observations = form.getObservations();
		//log.log(Log.FINE,"obs for save---->>"+observations);
		cn66Vos = session.getCN66VOs();
		log.log(Log.FINE, "cn66vos from session before save ---->>", cn66Vos);
		Collection <CN66DetailsVO> cn66VosForSave = new ArrayList<CN66DetailsVO>();

		if(cn66Vos.size()>0){
			int index=0;
		for(CN66DetailsVO cn66Vo : cn66Vos){
			log.log(Log.FINE, "cn66vos for save---->>", observations, index);
		if(!observations[index].equals(cn66Vo.getRemarks())){
			  cn66Vo.setCompanyCode(companyCode);
			  cn66Vo.setRemarks(observations[index]);
			  cn66Vo.setLastUpdatedUser(userId);
			 log.log(Log.FINE, " lstUPdatedTime while saving", cn66Vo.getLastupdatedTime());
			cn66VosForSave.add(cn66Vo);
		  }
		++index;
		}
		}
		log.log(Log.FINE, "cn66vos for save---->>", cn66VosForSave);
		try {
			log.log(Log.FINE,"calling saveCN66Observations");
			delegate.saveCN66Observations(cn66VosForSave) ;
			log.log(Log.FINE, "saved details successfully");
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}
		form.setCategory(EMPTY_STRING);
		form.setDestination(EMPTY_STRING);
		form.setOrigin(EMPTY_STRING);
		form.setInvoiceNumber(EMPTY_STRING);
		form.setGpaCode(EMPTY_STRING);
		form.setGpaName(EMPTY_STRING);


		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		session.removeCN66VOs();
		session.removeCN51DetailsVOs();
		invocationContext.target = SAVE_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	}
