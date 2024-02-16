/*
 * PopulatePartyNameCommand.java  Created on Nov 13, 2008
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldagreement.maintainuldagreement;

import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.customer.CustomerDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainULDAgreementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3278
 * to populate the party name on tabout
 */
public class PopulatePartyNameCommand extends BaseCommand {	

	/**
	 * Logger for PopulateParyNameCommand
	 */
	private Log log = LogFactory.getLogger("Maintain ULD Agreement");

	/**
	 * target String if success
	 */
	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String AGENT = "G";

	private static final String AIRLINE = "A";
	private static final String CUSTOMER = "C";
	private static final String FROM = "FROM";
	private static final String TO = "TO";
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		MaintainULDAgreementForm maintainULDAgreementForm = (MaintainULDAgreementForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		if (maintainULDAgreementForm.getAgrPartyCode() != null
				&& maintainULDAgreementForm.getAgrPartyCode().trim().length() > 0) {
			String partyType=null;
			if (FROM.equals(maintainULDAgreementForm.getTypeOfParty())) {
				partyType = maintainULDAgreementForm.getFromPartyType();
			} else {
				partyType = maintainULDAgreementForm.getPartyType();

			}
			if (partyType.equals(AGENT)) {

				AgentVO agentVO = null;
				try {
					agentVO = new AgentDelegate().findAgentDetails(
							logonAttributes.getCompanyCode(),
							maintainULDAgreementForm.getAgrPartyCode().trim()
									.toUpperCase());
				} catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
				}
				if (agentVO != null) {
					maintainULDAgreementForm.setAgrPartyName(agentVO
							.getAgentName());
					maintainULDAgreementForm.setErrorStatusFlag("N");
				} else {
					maintainULDAgreementForm.setErrorStatusFlag("Y");
					maintainULDAgreementForm.setAgrPartyName("");
				}

			} else if (partyType.equals(AIRLINE)) {
				AirlineLovFilterVO airlineLovFilterVO = new AirlineLovFilterVO();
				airlineLovFilterVO.setCompanyCode(logonAttributes
						.getCompanyCode());
				airlineLovFilterVO.setAirlineCode(maintainULDAgreementForm
						.getAgrPartyCode().trim().toUpperCase());
				airlineLovFilterVO.setDisplayPage(1);
				Page<AirlineLovVO> page = null;
				try {
					page = new AirlineDelegate().findAirlineLov(
							airlineLovFilterVO, 1);

				} catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
				}

				if (page != null && page.size() > 0) {
					maintainULDAgreementForm.setAgrPartyName(page.get(0)
							.getAirlineName());
					maintainULDAgreementForm.setErrorStatusFlag("N");
				} else {
					maintainULDAgreementForm.setErrorStatusFlag("Y");
					maintainULDAgreementForm.setAgrPartyName("");
				}
			}
			//changes for ICRD-34869 begins
			else if (partyType.equals(CUSTOMER)) {
				CustomerFilterVO customerFilterVO = new CustomerFilterVO();
				customerFilterVO.setCompanyCode(getApplicationSession().getLogonVO()
						.getCompanyCode());
				if(FROM.equals(maintainULDAgreementForm.getTypeOfParty())){
					customerFilterVO.setCustomerCode(maintainULDAgreementForm.getFromPartyCode());
				}else{
				customerFilterVO.setCustomerCode(maintainULDAgreementForm.getPartyCode());
				}
				CustomerDelegate customerDelegate = new CustomerDelegate();

				CustomerVO customerVO = null;
				try {
					customerVO = customerDelegate.validateCustomer(customerFilterVO);
				} catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
				}

				if (customerVO != null ) {
					maintainULDAgreementForm.setAgrPartyName(customerVO.getCustomerName());
					maintainULDAgreementForm.setErrorStatusFlag("N");
				} else {
					maintainULDAgreementForm.setErrorStatusFlag("Y");
					maintainULDAgreementForm.setAgrPartyName("");
				}
			}
			//changes for ICRD-34869 ends
		} else {
			maintainULDAgreementForm.setErrorStatusFlag("Y");
			maintainULDAgreementForm.setAgrPartyName("");
		}
		log.log(Log.INFO, "in populate PartyName Command",
				maintainULDAgreementForm.getAgrPartyName());
		invocationContext.target = SCREENLOAD_SUCCESS;

	}

}
