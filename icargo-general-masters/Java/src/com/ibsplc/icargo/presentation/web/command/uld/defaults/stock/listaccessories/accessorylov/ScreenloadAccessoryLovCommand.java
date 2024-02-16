/*
 * ScreenloadAccessoryLovCommand.java Created on May 18, 2009
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.listaccessories.accessorylov;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.AccessoryStockLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3278
 * 
 */
public class ScreenloadAccessoryLovCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ScreenLoad AccessoryLov");

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String compCode = logonAttributes.getCompanyCode();
		AccessoryStockLovForm accessoryLovForm = (AccessoryStockLovForm) invocationContext.screenModel;
		AccessoriesStockConfigFilterVO accessoriesStockConfigFilterVO = new AccessoriesStockConfigFilterVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		accessoriesStockConfigFilterVO.setCompanyCode(compCode);
		int pageNumber = Integer.parseInt(accessoryLovForm.getDisplayPage());
		int airlineIdentifier = 0;
		if (accessoryLovForm.getStatusValue() != null
				&& accessoryLovForm.getStatusValue().trim().length() > 0) {
			if ("fromOperations".equals(accessoryLovForm.getStatusValue())) {
				accessoryLovForm.setAirline(accessoryLovForm
						.getAirlineFromOperations());
			}
		}
		if (accessoryLovForm.getAccessoryStkCode() != null
				&& accessoryLovForm.getAccessoryStkCode().trim().length() > 0) {
			accessoriesStockConfigFilterVO.setAccessoryCode(accessoryLovForm
					.getAccessoryStkCode().toUpperCase());
		}
		if (accessoryLovForm.getAirline() != null
				&& accessoryLovForm.getAirline().trim().length() > 0) {
			AirlineValidationVO airlineValidationVO = null;
			AirlineDelegate airlineDelegate = new AirlineDelegate();
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						compCode, accessoryLovForm.getAirline());
				airlineIdentifier = airlineValidationVO.getAirlineIdentifier();
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.FINE, "inside updateAirlinecaught busDelegateExc");
				errors = handleDelegateException(businessDelegateException);
			}
			accessoriesStockConfigFilterVO.setAirlineCode(accessoryLovForm
					.getAirline().toUpperCase());
			accessoriesStockConfigFilterVO
					.setAirlineIdentifier(airlineIdentifier);
		}
		if (accessoryLovForm.getAccessoryStkName() != null
				&& accessoryLovForm.getAccessoryStkName().trim().length() > 0) {
			accessoriesStockConfigFilterVO
					.setAccessoryDescription(accessoryLovForm
							.getAccessoryStkName().toUpperCase());
		}
		try {
			Page<AccessoriesStockConfigVO> page = new ULDDefaultsDelegate()
					.findAccessoryStockList(accessoriesStockConfigFilterVO,
							pageNumber);

			accessoryLovForm.setPageAccessoryLov(page);
			log.log(Log.FINE, "page ---> ", page);

		} catch (BusinessDelegateException businessDelegateException) {
			
			errors = handleDelegateException(businessDelegateException);
		}
		invocationContext.target = SCREENLOAD_SUCCESS;
	}

}
