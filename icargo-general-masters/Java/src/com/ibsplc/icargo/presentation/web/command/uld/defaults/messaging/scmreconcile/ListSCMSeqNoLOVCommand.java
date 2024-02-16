/* ListSCMSeqNoLOVCommand.java Created on Aug 01,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.scmreconcile;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMReconcileSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMSequenceNoLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class ListSCMSeqNoLOVCommand extends BaseCommand {

	private static final String LIST_SUCCESS = "list_success";

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.scmreconcile";

	private static final String BLANK = "";
	
	private Log log = LogFactory.getLogger("SCMSEQLOV");
	
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		SCMSequenceNoLovForm form = (SCMSequenceNoLovForm) invocationContext.screenModel;
		SCMReconcileSession session = getScreenSession(MODULE, SCREENID);
		String scmSeqNo = BLANK;
		String airlineCode = BLANK;
		
		SCMMessageFilterVO filterVO = new SCMMessageFilterVO();
		if (form.getSequenceNo() != null
				&& form.getSequenceNo().trim().length() > 0) {
			scmSeqNo = form.getSequenceNo().toUpperCase();
			filterVO.setSequenceNumber(scmSeqNo);
		}
		if (form.getAirline() != null && form.getAirline().trim().length() > 0) {
			airlineCode = form.getAirline().toUpperCase();
			filterVO.setAirlineCode(airlineCode);
		}
		filterVO.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());
		filterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));
		Collection<ErrorVO> errors = null;
		// validate carriercode
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		log.log(Log.FINE, "Airline from form--------------->", form.getAirline().toUpperCase());
		if (form.getAirline() != null && form.getAirline().trim().length() > 0) {
			Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						getApplicationSession().getLogonVO().getCompanyCode(),
						form.getAirline().toUpperCase());

			} catch (BusinessDelegateException businessDelegateException) {
				error = handleDelegateException(businessDelegateException);
			}
		}

		if (airlineValidationVO != null) {
			filterVO.setFlightCarrierIdentifier(airlineValidationVO
					.getAirlineIdentifier());
		}
		log.log(Log.FINE, "\n\n\nAirport code------------------>", form.getAirportCode());
		filterVO.setAirportCode(form.getAirportCode().toUpperCase());
		log.log(Log.FINE, "Filter VO to server------------------>", filterVO);
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		Page<ULDSCMReconcileVO> reconcileVOs=null;
		Collection<ErrorVO> err = new ArrayList<ErrorVO>();
		try{
			reconcileVOs = delegate.listSCMMessageLOV(filterVO);
		}catch(BusinessDelegateException exception){
			exception.getMessage();
			err = handleDelegateException(exception);
		}
		
		log.log(Log.FINE, "Reconcile vos from server---------------->",
				reconcileVOs);
		if(reconcileVOs != null && reconcileVOs.size()>0){
			form.setListSuccess(ULDSCMReconcileVO.FLAG_YES);
		}else{
			form.setListSuccess(ULDSCMReconcileVO.FLAG_NO);
		}
		session.setSCMReconcileLovVOs(reconcileVOs);
		

		invocationContext.target = LIST_SUCCESS;

	}
}

