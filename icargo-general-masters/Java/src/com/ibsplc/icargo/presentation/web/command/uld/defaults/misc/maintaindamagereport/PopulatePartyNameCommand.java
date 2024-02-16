/*
 * PopulatePartyNameCommand.java Created on Oct 14,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.maintaindamagereport;

import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
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
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up PopulatePartyNameCommand
 * 
 * @author A-3154
 */

public class PopulatePartyNameCommand extends BaseCommand {

	/**
	 * Logger for PopulatePartyNameCommand
	 */
	private Log log = LogFactory.getLogger("PopulatePartyNameCommand");

	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of PopulatePartyNameCommand  screen
	 */
	private static final String SCREENID = "uld.defaults.maintaindamagereport";

	private static final String SUCCESS ="success";
	
	private static final String FAILURE ="failure";
	
	private static final String AGENT    =  "G";
	
	private static final String CUSTOMER =  "C";
	
	private static final String AIRLINE  =  "A";

	private static final String YES = "Y";
	
	private static final String NO = "N";
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("maintaindamagereport", "PopulatePartyNameCommand");
		MaintainDamageReportForm form = (MaintainDamageReportForm) invocationContext.screenModel;
		if(form.getAjaxPartyType() != null &&
				form.getAjaxPartyType().trim().length() > 0 &&
					form.getAjaxPartyCode() != null &&
						form.getAjaxPartyCode().trim().length() > 0){
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			if(CUSTOMER.equals(form.getAjaxPartyType())){
				CustomerDelegate custdel = new CustomerDelegate();
				CustomerFilterVO customerFilterVO = new CustomerFilterVO();
				customerFilterVO.setCustomerCode(form.getAjaxPartyCode().toUpperCase());
				customerFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
				CustomerVO customerVO = null;
				try {
					customerVO = custdel.listCustomer(customerFilterVO);
				} catch (BusinessDelegateException e) {
					log.log(Log.FINE, "\n Exception thrown in listcustomer");
					form.setAjaxErrorStatusFlag(YES);
					invocationContext.target = FAILURE;
					return;
				}
				if(customerVO != null && 
						customerVO.getCustomerName() != null ){
					form.setAjaxErrorStatusFlag(NO);
					form.setAjaxPartyName(customerVO.getCustomerName());
				}else{
					form.setAjaxErrorStatusFlag(YES);
					invocationContext.target = FAILURE;
					return;
				}
			}else if(AGENT.equals(form.getAjaxPartyType())){
				AgentDelegate agentdelegate = new AgentDelegate();
				AgentVO agentVO = null;
				try {
					agentVO = agentdelegate.findAgentDetails(logonAttributes
							.getCompanyCode(), form.getAjaxPartyCode()
							.toUpperCase());
				} catch (BusinessDelegateException e) {
					log.log(Log.FINE, "\n Exception thrown in listagent");
					form.setAjaxErrorStatusFlag(YES);
					invocationContext.target = FAILURE;
					return;
				}
				if(agentVO == null ){
					form.setAjaxErrorStatusFlag(YES);
					invocationContext.target = FAILURE;
					return;
				}else{
					form.setAjaxErrorStatusFlag(NO);
					form.setAjaxPartyName(agentVO.getAgentName());
				}
			}else if(AIRLINE.equals(form.getAjaxPartyType())){
				AirlineDelegate airlinedel = new AirlineDelegate();
				AirlineValidationVO airlineVO = null;
				try {
					airlineVO = airlinedel.validateAlphaCode(logonAttributes
							.getCompanyCode(), form.getAjaxPartyCode()
							.toUpperCase());
				} catch (BusinessDelegateException e) {
					log.log(Log.FINE, "\n Exception thrown in listairline");
					form.setAjaxErrorStatusFlag(YES);
					invocationContext.target = FAILURE;
					return;
				}	
				if(airlineVO == null){
					form.setAjaxErrorStatusFlag(YES);
					invocationContext.target = FAILURE;
					return;
				}else{
					form.setAjaxErrorStatusFlag(NO);
					form.setAjaxPartyName(airlineVO.getAirlineName());
				}
			}
		}else{
			form.setAjaxErrorStatusFlag(YES);
		}
		log.exiting("maintaindamagereport", "PopulatePartyNameCommand");
		invocationContext.target = SUCCESS;

	}

}
