package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.RedemptionValidationVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2052
 * 
 */
public class ScreenLoadPointRedemptionCommand extends BaseCommand {

	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	
	private static final String SCREENLOAD_FAILURE = "screenload_failure";
	

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.customerlisting";

	private static final String KEY_SERVICES = "customer.redeempoints.services";

	private Log log = LogFactory.getLogger("ScreenLoadPointRedemptionCommand");

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
public void execute(InvocationContext invocationContext)throws CommandInvocationException{
		log.entering("ScreenLoadPointRedemptionCommand","ENTER");
		ListCustomerForm form = (ListCustomerForm)invocationContext.screenModel;
    	String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	ListCustomerSession session = getScreenSession(MODULENAME,SCREENID);
    	CustomerMgmntDefaultsDelegate delegate  = new CustomerMgmntDefaultsDelegate();
    	
    	
    	SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		
		try {
		oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
				companyCode, getOneTimeParameterTypes());
		} catch (BusinessDelegateException e) {
//printStackTrrace()();
		handleDelegateException(e);
		}
		session.setService((HashMap<String, Collection<OneTimeVO>>)oneTimeValues);
		
		session.setCustomerContactPointsVOs(null);
    	Page<CustomerVO> customerVOs = session.getCustomerVOs();
    	ArrayList<String> custCodes = new ArrayList<String>();
    	ArrayList<String> custNames = new ArrayList<String>();
    	String rows = form.getRows();
    	log.log(Log.FINE, "\n\nRows::", rows);
		String[] checked = rows.split(",");
    	for(CustomerVO vo:customerVOs){
 			String code=vo.getCustomerCode();
 			custCodes.add(code);
 		}
    	for(CustomerVO vo:customerVOs){
 			String name=vo.getCustomerName();
 			custNames.add(name);
 		}
    	 for(int i=0;i<checked.length;i++){    
	    	log.log(Log.FINE,"customerVOs before getting points");
	    	CustomerVO vo = customerVOs.get(Integer.parseInt(checked[i]));
	    	log.log(Log.FINE,
					"customerVOs.get(Integer.parseInt(checked[i]))------>>>>>",
					vo);
			double pointsAccruded = vo.getPointsAccruded();
 			double pointsRedeemed = vo.getPointsRedeemed();
 			double point = pointsAccruded-pointsRedeemed;
 			log.log(Log.FINE, "pointsAccruded setting to form------>>>>>",
					pointsAccruded);
			log.log(Log.FINE, "pointsRedeemed setting to form------>>>>>",
					pointsRedeemed);
			log.log(Log.FINE, "point setting to form------>>>>>", point);
			form.setPointsRedmdTo(String.valueOf(point));
 			// session.setPoints(form.getPointsRedmdTo());
 			session.setPointsAccruded(String.valueOf(pointsAccruded));
 			session.setPointsRedeemed(String.valueOf(point));
 			log.log(Log.FINE, "session.getPointsAccruded())---------->>>",
					session.getPointsAccruded());
			log.log(Log.FINE, "session.getPointsRedeemed())---------->>>",
					session.getPointsRedeemed());
    	 }
    	ArrayList<String> selectedCustomerCodes = new ArrayList<String>();
    	ArrayList<String> selectedCustomerNames = new ArrayList<String>();
        for(int i=0;i<checked.length;i++){        	
        	String selectedCustomerCode = custCodes.get(Integer.parseInt(checked[i]));
        	selectedCustomerCodes.add(selectedCustomerCode);
        }
        for(int i=0;i<checked.length;i++){        	
        	String selectedCustomerName = custNames.get(Integer.parseInt(checked[i]));
        	selectedCustomerNames.add(selectedCustomerName);
        }
        if(!"Y".equals(form.getCanRedeem())){
        RedemptionValidationVO validationVO = new RedemptionValidationVO();
    	validationVO.setCompanyCode(companyCode);
    	validationVO.setCustomerCode(selectedCustomerCodes.get(0));
    	LocalDate currentDate = new LocalDate(getApplicationSession().getLogonVO().getStationCode(),Location.STN , false);
    	validationVO.setCurrentDate(currentDate);
    	log.log(Log.FINE, "validation vo-------------->", validationVO);
		Collection<ErrorVO> errors  = new ArrayList<ErrorVO>();
        
    	try{
        	errors  = delegate.validateCustomerForPointsRedemption(validationVO);
    	}catch(BusinessDelegateException ex){
//printStackTrrace()();
    		errors = handleDelegateException(ex);
    	}
    	log.log(Log.FINE, "\n\n\n\nerrors-------------------------->", errors);
		if(errors != null && errors.size()>0){
    		log.log(Log.FINE,"\n\n\ninside errors************");
    		invocationContext.addAllError(errors);
    		invocationContext.target = SCREENLOAD_FAILURE;
    		form.setCanRedeem("N");
    		return;
    	}
        }
    	
        session.setCustomerCodes(selectedCustomerCodes);
        session.setCustomerNames(selectedCustomerNames);      
        String customerCode = selectedCustomerCodes.get(0);
        String customerName = selectedCustomerNames.get(0);
        form.setCustomerCodePointRdmd(customerCode);
        form.setCustomerNamePointRdmd(customerName);        
        log.exiting("ScreenLoadPointRedemptionCommand","EXIT");
        form.setCanRedeem("Y");
        invocationContext.target=SCREENLOAD_SUCCESS;
	}
	/**
	 * Method to populate the collection of onetime parameters to be obtained
	 * 
	 * @return parameterTypes
	 */
	private Collection<String> getOneTimeParameterTypes() {
		log.entering("ScreenLoadCommand", "getOneTimeParameterTypes");
		ArrayList<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(KEY_SERVICES);
		log.exiting("ScreenLoadCommand", "getOneTimeParameterTypes");
		return parameterTypes;
	}

}
