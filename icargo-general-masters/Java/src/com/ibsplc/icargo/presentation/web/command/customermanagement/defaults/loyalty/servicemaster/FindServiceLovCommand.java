package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.servicemaster;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.CustomerServicesVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.ServiceLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * Class to find the FindServiceLovCommand
 */
public class FindServiceLovCommand extends BaseCommand {

	private static final String
				FINDSERVICELOVSUCCESS="findserviceLov_Success";

	

	/**
	 * Method to execute the command
	 * @param invocationContext
	 * @exception  CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {

		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
	    String companyCode = logonAttributes.getCompanyCode();
	    String customerCode=null;
	    ServiceLovForm serviceLovForm=
					(ServiceLovForm)invocationContext.screenModel;
		try {
			
			 CustomerMgmntDefaultsDelegate customerDelegate = new CustomerMgmntDefaultsDelegate();
			int displayPage=Integer.
							parseInt(serviceLovForm.getDisplayPage());
			if(serviceLovForm.getCode()!= null && serviceLovForm.getCode().trim().length()>0){
				if(serviceLovForm.getCode().contains("&")){
					customerCode=serviceLovForm.getCode().split("&")[0].toUpperCase();
					serviceLovForm.setCode(serviceLovForm.getCode().split("&")[0].toUpperCase());
				}else{
				customerCode = serviceLovForm.getCode().toUpperCase();
				}
			}
			if(!(AbstractVO.FLAG_YES.equals(serviceLovForm.getMultiselect()))){
				serviceLovForm.setSelectedValues("");
			}

			Page<CustomerServicesVO> page=customerDelegate.customerServicesLOV(companyCode,customerCode,displayPage);
					
			serviceLovForm.setServiceTypeLovPage(page);
		} catch (BusinessDelegateException ex) {
//printStackTrrace()();
			 handleDelegateException(ex);
		}
		invocationContext.target =FINDSERVICELOVSUCCESS;
	}
}
