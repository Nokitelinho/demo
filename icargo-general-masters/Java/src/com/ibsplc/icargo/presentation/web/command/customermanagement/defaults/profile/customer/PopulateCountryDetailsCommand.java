/*
 * PopulateCountryDetailsCommand.java Created on MAR 11, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.area.zipcode.vo.ZipCodeFilterVO;
import com.ibsplc.icargo.business.shared.area.zipcode.vo.ZipCodeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-3353  Mofified by @author a-3351
 * 
 */
public class PopulateCountryDetailsCommand extends BaseCommand {

	private static final String FAILURE = "failure";
	private static final String SUCCESS = "success";
	private static final String MODULENAME = "customermanagement.defaults";
	private static final String SCREENID = "customermanagement.defaults.maintainregcustomer";

	private Log log = LogFactory.getLogger("PopulateCustomerNameCommand");

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.log(Log.FINE, "PopulateCustomerNameCommand");
		MaintainRegCustomerForm form = (MaintainRegCustomerForm) invocationContext.screenModel;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		MaintainCustomerRegistrationSession session = getScreenSession(
				MODULENAME, SCREENID);

		ZipCodeVO zipCodeVO = null;
		ZipCodeFilterVO zipCodeFilterVO = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		zipCodeFilterVO = new ZipCodeFilterVO();
		zipCodeFilterVO.setCompanyCode(logonAttributes.getCompanyCode());

		if (form.getZipCode() != null && form.getZipCode().trim().length() > 0) {
			zipCodeFilterVO.setZipCode(form.getZipCode().trim().toUpperCase());
		}

		if (form.getCountry() != null && form.getCountry().trim().length() > 0) {
			zipCodeFilterVO.setCountryCode(form.getCountry().trim()
					.toUpperCase());
		}

		log.log(Log.FINE, "zipCodeFilterVO-------------->", zipCodeFilterVO);
		try {
			zipCodeVO = new AreaDelegate()
					.validateZipCodeforCountry(zipCodeFilterVO);
		} catch (BusinessDelegateException delegateException) {
			errors = handleDelegateException(delegateException);
			form.setAjaxErrorStatusFlag(ZipCodeVO.FLAG_YES);
		}

		log.log(Log.FINE, "zipCodeVO-------------->", zipCodeVO);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = FAILURE;
			return;
		}

		if (zipCodeVO != null) {
			form.setAjaxErrorStatusFlag(ZipCodeVO.FLAG_NO);
			form.setState(zipCodeVO.getStateDescription());
			form.setCountry(zipCodeVO.getCountryCode());
			form.setCity(zipCodeVO.getCityDescription());

		} else {
			form.setAjaxErrorStatusFlag(ZipCodeVO.FLAG_YES);
			invocationContext.target = FAILURE;
			return;
		}
		invocationContext.target = SUCCESS;

	}

}
