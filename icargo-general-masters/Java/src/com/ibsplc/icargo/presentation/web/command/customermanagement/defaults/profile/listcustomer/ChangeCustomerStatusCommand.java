/*
 * ChangeCustomerStatusCommand.java Created on Jan 26, 2006
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm;
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
public class ChangeCustomerStatusCommand extends BaseCommand {

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.customerlisting";

	private static final String SCREEN_ID = "customermanagement.defaults.maintainregcustomer";

	private static final String ACTIVATE = "activate";

	private static final String INACTIVATE = "inactivate";

	private static final String BLACKLIST = "blacklist";

	private static final String REVOKE = "revoke";

	private static final String ACTION_SUCCESS = "list_success";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("customerlisting");
		log.entering("customer", "change customer status command");
		ListCustomerForm form = (ListCustomerForm) invocationContext.screenModel;
		ListCustomerSession session = getScreenSession(MODULENAME, SCREENID);
		MaintainCustomerRegistrationSession cusRegSession  = getScreenSession(MODULENAME, SCREEN_ID);
		String selection = form.getFlag();
		log.log(Log.FINE, "\n\n\nselected action---------------->" + selection);
		CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
		String[] checked = form.getCheck();
		Page<CustomerVO> customerVOs = session.getCustomerVOs();
		Collection<CustomerVO> selectedCustomers = new ArrayList<CustomerVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if (customerVOs != null && customerVOs.size() > 0 && checked != null) {
			for (int i = 0; i < checked.length-1; i++) {
				log.log(Log.FINE, "checked------------->" + checked[i]);
				selectedCustomers.add(customerVOs.get(Integer
						.parseInt(checked[i])));
			}
		}

		if (selectedCustomers != null && selectedCustomers.size() > 0) {
			for (CustomerVO selectedCust : selectedCustomers) {
				if (ACTIVATE.equals(form.getFlag())) {
					selectedCust.setStatus("A");
				} else if (INACTIVATE.equals(form.getFlag())) {
					selectedCust.setStatus("I");

				} else if (BLACKLIST.equals(form.getFlag())) {
					selectedCust.setStatus("B");
				}else if(REVOKE.equals(form.getFlag())){
					selectedCust.setCheckRevokeFlag("R");
					selectedCust.setStatus("A");
				}
			}
		}

		try {
			log.log(Log.FINE, "\n beefooredel"+selectedCustomers);
			delegate.changeStatusOfCustomers(selectedCustomers);
		} catch (BusinessDelegateException ex) {
			errors = handleDelegateException(ex);
		}
		if(errors!=null && errors.size()>0){
			log.log(Log.FINE,"\n insideeerrror");
			invocationContext.addAllError(errors);
			invocationContext.target = ACTION_SUCCESS;
		}else{
			invocationContext.target = ACTION_SUCCESS;
		}
		
		//Added for ICRD-67442 by A-5163 starts
		if(cusRegSession.getCustomerDetailVOsInMap() != null){
			cusRegSession.setCustomerDetailVOsInMap(null);
		}
		//Added for ICRD-67442 by A-5163 ends
		
	}
}
