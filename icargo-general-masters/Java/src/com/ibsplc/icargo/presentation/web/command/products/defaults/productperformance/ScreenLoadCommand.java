package com.ibsplc.icargo.presentation.web.command.products.defaults.productperformance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ProductPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ProductPerformanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ScreenLoadCommand extends BaseCommand { 
	private static final String PRIORITY_ONETIME="products.defaults.priority";
	private static final String TRANSMODE_ONETIME="products.defaults.transportmode";
	private String companyCode;
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {		
		
		ProductPerformanceForm maintainProductForm = (ProductPerformanceForm) invocationContext.screenModel;
		ProductPerformanceSession session = getScreenSession(
				"product.defaults", "products.defaults.productperformance");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();		  
	    LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
	    companyCode = logonAttributes.getCompanyCode();  
	    clearForm(maintainProductForm);
		Map<String, Collection<OneTimeVO>> oneTimes = getScreenLoadDetails(companyCode);
		if (oneTimes != null) {
			
			Collection<OneTimeVO> prtyOnetime = oneTimes.get(PRIORITY_ONETIME);
			Collection<OneTimeVO> transmodeOnetimes = oneTimes.get(TRANSMODE_ONETIME);
			session.setPriority(prtyOnetime);
			session.setTransportMode(transmodeOnetimes);
		}		
		invocationContext.target = "screenload_success";
	}
	
	/**
	 * This method will be invoked at the time of screen load
	 * 
	 * @param companyCode
	 * @return
	 */
	public Map<String, Collection<OneTimeVO>> getScreenLoadDetails(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		try {

			Collection<String> fieldTypes = new ArrayList<String>();			
			fieldTypes.add(PRIORITY_ONETIME);	
			fieldTypes.add(TRANSMODE_ONETIME);			
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, fieldTypes);

		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"<-----------------BusinessDelegateException------------->");
		}
		return oneTimes;
	}
	private void clearForm(ProductPerformanceForm form) {
		form.setDestination("");
		form.setFromDate("");
		form.setOrigin("");
		form.setPriority("");
		form.setProductCode("");
		form.setProductName("");
		form.setScc("");
		form.setToDate("");
		form.setTransMode("");	
	}

}
