package com.ibsplc.icargo.presentation.web.command.products.defaults.listproducts;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListProductForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
/**
 * ScreenloadCommand class is for screenload action
 * @author a-1870
 *
 */
public class ScreenLoadCommand extends BaseCommand{ 
	//private static final String COMPANY_CODE = "AV";
	private static final String  PRD_CATG_ONETIME= "products.defaults.category"; //Added for ICRD-166985 by A-5117 
	private static final String SCRRENLOAD_PROD_MODE= "screenloadmode";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		ListProductSessionInterface session = getScreenSession( "product.defaults","products.defaults.listproducts");
		ListProductForm listProductForm = (ListProductForm)invocationContext.screenModel;
		session.setPageProductVO(null);
		listProductForm.setStatus("ALL");
		listProductForm.setTransportMode("ALL");
		listProductForm.setPriority("ALL");
		Map<String, Collection<OneTimeVO>>  oneTimes=getScreenLoadDetails(getApplicationSession().getLogonVO().getCompanyCode());
		if(oneTimes!=null){
			Collection<OneTimeVO> resultStatus=
				oneTimes.get("products.defaults.status");
			Collection<OneTimeVO> resultTransportMode=oneTimes.get("products.defaults.transportmode");
			Collection<OneTimeVO> resultPriority=oneTimes.get("products.defaults.priority");
			Collection<OneTimeVO> productCategories=oneTimes.get(PRD_CATG_ONETIME); //Added for ICRD-166985 by A-5117 
			
			session.setStatus(resultStatus);
			session.setPriority(resultPriority);
			session.setTransportMode(resultTransportMode);
			session.setProductCategories(productCategories);//Added for ICRD-166985 by A-5117 
			
		}
		listProductForm.setScreenMode(SCRRENLOAD_PROD_MODE);
		invocationContext.target = "screenload_success";
	}
	
	/**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return
	 */
	public Map<String, Collection<OneTimeVO>> getScreenLoadDetails(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			
			fieldValues.add("products.defaults.transportmode");
			fieldValues.add("products.defaults.status");
			fieldValues.add("products.defaults.priority");
			fieldValues.add(PRD_CATG_ONETIME); //Added for ICRD-166985 by A-5117 
			
			
			oneTimes =
				new SharedDefaultsDelegate().findOneTimeValues(getApplicationSession().getLogonVO().getCompanyCode(),fieldValues) ;
			
		}catch (BusinessDelegateException businessDelegateException) {
			
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}
	
}


