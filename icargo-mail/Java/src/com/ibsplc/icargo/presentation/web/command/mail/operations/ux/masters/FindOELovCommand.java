package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.masters;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
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
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.OfficeOfExchangeUxLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.masters.FindOELovCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	05-Jul-2018	:	Draft
 */
public class FindOELovCommand extends BaseCommand {
	
	private static final String SUCCESS="findoeLov_Success";
	private static final String FAILURE="findoeLov_Failure";
	private Log log = LogFactory.getLogger("FindOELovCommand");
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-8164 on 05-Jul-2018
	 * 	Used for 	:	 ICRD-263285
	 *	Parameters	:	@param invocationContext
	 *	Parameters	:	@throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.FINE, "\n\n FindOELovCommand----------> \n\n");
		
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
	
		OfficeOfExchangeUxLovForm oeLovForm = 
						(OfficeOfExchangeUxLovForm)invocationContext.screenModel;
		try {
			
			MailTrackingDefaultsDelegate delegate
										= new MailTrackingDefaultsDelegate();
			Collection<ErrorVO> errors = new ArrayList();
			int displayPage=Integer.parseInt(oeLovForm.getDisplayPage());
			int defaultSize=Integer.parseInt(oeLovForm.getDefaultPageSize());
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
	     			String percentage =  MailConstantsVO.PERCENTAGE;
	     			if(airportCode.contains(percentage)){
	     				officeOfExchangeVO.setAirportCode(airportCode);
	     			}else{
	     			airportValidationVO = areaDelegate.validateAirportCode(
	     					logonAttributes.getCompanyCode(),
	     					airportCode.toUpperCase());
	     			}
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
					String percentage =  MailConstantsVO.PERCENTAGE;
	     			if(poaCode.contains(percentage)){
	     				officeOfExchangeVO.setPoaCode(poaCode);
	     			}else{
					postalAdministrationVO  = new MailTrackingDefaultsDelegate().findPACode(
							logonAttributes.getCompanyCode(),poaCode.toUpperCase());
	     			}
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
					officeOfExchangeVO,displayPage,defaultSize);
			oeLovForm.setOeLovPage(page);
		} catch (BusinessDelegateException ex) {
			handleDelegateException(ex);
		  }
		invocationContext.target =SUCCESS;
		
	}
}
