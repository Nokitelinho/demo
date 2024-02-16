/*
 * FindOELovCommand.java Created on June 21, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OfficeOfExchangeLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class FindOELovCommand extends BaseCommand {

	private static final String SUCCESS="findoeLov_Success";
	private static final String FAILURE="findoeLov_Failure";
	
	private Log log = LogFactory.getLogger("FindOELovCommand");


	/**
	* Method to execute the command
	* @param invocationContext
	* @exception  CommandInvocationException
	*/
	public void execute(InvocationContext invocationContext)
							throws CommandInvocationException {
		
		log.log(Log.FINE, "\n\n FindOELovCommand----------> \n\n");
	
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
	
		OfficeOfExchangeLovForm oeLovForm = 
						(OfficeOfExchangeLovForm)invocationContext.screenModel;
		try {
			
			MailTrackingDefaultsDelegate delegate
										= new MailTrackingDefaultsDelegate();
			Collection<ErrorVO> errors = new ArrayList();
			int displayPage=Integer.parseInt(oeLovForm.getDisplayPage());
			
			String code = oeLovForm.getCode().toUpperCase();
			String description = oeLovForm.getDescription();
			String airportCode = oeLovForm.getAirportCode();
			String poaCode = oeLovForm.getPoaCode();
	
			if(!(("Y").equals(oeLovForm.getMultiselect()))){
				oeLovForm.setSelectedValues("");
			}
			OfficeOfExchangeVO officeOfExchangeVO = new OfficeOfExchangeVO();
			officeOfExchangeVO.setCompanyCode(companyCode);
			officeOfExchangeVO.setCode(code);
			officeOfExchangeVO.setCodeDescription(description);
			AirportValidationVO airportValidationVO = null;
			AreaDelegate areaDelegate = new AreaDelegate();
	     	if (airportCode != null && !"".equals(airportCode)) {
	     		try {
	     			airportValidationVO = areaDelegate.validateAirportCode(
	     					logonAttributes.getCompanyCode(),
	     					airportCode.toUpperCase());
	     		}catch (BusinessDelegateException businessDelegateException) {
	     			errors = handleDelegateException(businessDelegateException);
	     		}
	     		if (errors != null && !errors.isEmpty()) {
	     			invocationContext.addError(new ErrorVO("mailtracking.defaults.ooe.invalidairport"));
	     			invocationContext.target=FAILURE;
		        	return;
	     		}
	     		if(airportValidationVO != null){
	    			officeOfExchangeVO.setAirportCode(airportCode);
	    			}
	     	}
	
	     	if (poaCode != null && poaCode.trim().length() > 0) {
				PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
				try {
					postalAdministrationVO  = new MailTrackingDefaultsDelegate().findPACode(
							logonAttributes.getCompanyCode(),poaCode.toUpperCase());
				}catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				if (postalAdministrationVO == null || (errors!=null && !errors.isEmpty())) {
					invocationContext.addError(new ErrorVO("mailtracking.defaults.ooe.invalidpacode"));
					invocationContext.target=FAILURE;
					return;
				}
				else{
					officeOfExchangeVO.setPoaCode(poaCode);
				}
			}
			Page<OfficeOfExchangeVO> page=delegate.findOfficeOfExchangeLov(
					officeOfExchangeVO,displayPage,0);
			oeLovForm.setOeLovPage(page);
		} catch (BusinessDelegateException ex) {
			handleDelegateException(ex);
		}
		invocationContext.target =SUCCESS;
	}

}
