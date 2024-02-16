package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listtempcustomer;
import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.customer.vo.TempCustomerVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListtempCustomerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListTempCustomerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2135
 *
 */
public class RegisterCustomerCommand  extends BaseCommand {

    private static final String MODULE = "customermanagement.defaults";
    private static final String SCREENID ="customermanagement.defaults.listtempcustomerform";
    private static final String SCREENID_REGISTER = "customermanagement.defaults.maintainregcustomer";
    private static final String REGISTER_SUCCESS = "register_success";



    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    		Log log = LogFactory.getLogger("customermanagement");
			ListtempCustomerSession listtempCustomerSession = getScreenSession(MODULE, SCREENID);
			MaintainCustomerRegistrationSession maintainCustomerRegistrationSession = 
	    		getScreenSession(MODULE,SCREENID_REGISTER);
			ListTempCustomerForm listTempCustomerForm = (ListTempCustomerForm) invocationContext.screenModel;
			
			Collection<TempCustomerVO> tempCustomerVOs = listtempCustomerSession.getListCustomerRegistration();
			ArrayList<TempCustomerVO> tempCust = new ArrayList<TempCustomerVO>();
			ArrayList<TempCustomerVO> selectedTempCust = new ArrayList<TempCustomerVO>();
	 		
	 		String[] checked =listTempCustomerForm.getRowId();
	 		
	 		for(TempCustomerVO tempCustomerVO:tempCustomerVOs){
	 			
	 			tempCust.add(tempCustomerVO);			
	 		}
	 		
	 		
	 		for(int i=0;i<checked.length;i++){
	        		
	 			selectedTempCust.add(tempCust.get(Integer.parseInt(checked[i])));
	        	   	
	        	
	        }
	 		log.log(Log.FINE,"selectedTempCust-----"+selectedTempCust);
	 		Collection<TempCustomerVO> tempCustColl=selectedTempCust;
	 		
	 		CustomerVO customerVO=new CustomerVO();
	 		
	 			TempCustomerVO tempCustomerVO=selectedTempCust.get(0);
	 			customerVO.setAddress1(tempCustomerVO.getAddress());
	 			//customerVO.setAddress2()
	 			//customerVO.setAirlineIdentifier(tempCustomerVO.get);
	 			//customerVO.setArea()
	 			//customerVO.getCity()
	 			customerVO.setCompanyCode(tempCustomerVO.getCompanyCode());
	 			//customerVO.getCountry()
	 			//customerVO.setCreditAccountNo()
	 			//customerVO.setCreditCurrencyCode()
	 			//customerVO.setCreditLimit()
	 			//customerVO.setCreditOutstanding()
	 			//customerVO.setCreditOutstdCurrencyCode();
	 			//customerVO.setCreditPeriod();
	 			
	 			//customerVO.setCreditPeriodMode()
	 			customerVO.setCustomerCode(tempCustomerVO.getCustomerCode());
	 			//customerVO.setCustomerGroup()
	 			customerVO.setCustomerName(tempCustomerVO.getTempCustName());
	 			customerVO.setEmail(tempCustomerVO.getEmailAddress());
	 			//customerVO.setFax()
	 			//customerVO.setGlobalCustomerFlag()
	 			customerVO.setLastUpdatedTime(tempCustomerVO.getLastUpdatedTime());
	 			customerVO.setLastUpdatedUser(tempCustomerVO.getLastUpdatedUser());
	 			//customerVO.setMobile()
	 			//customerVO.setPointsAccruded()
	 			//customerVO.setSita();
	 			customerVO.setStatus(tempCustomerVO.getActiveStatus());
	 			customerVO.setStationCode(tempCustomerVO.getStation());
	 			//customerVO.setStockHolderCode()
	 			customerVO.setTelephone(tempCustomerVO.getPhoneNumber());
	 			//customerVO.setZipCode();
	 			//customerVO.setZone();
	 	//		customerVO.setTempCustomerVOs(selectedTempCust);
	 			customerVO.setTempCustomerVOs(tempCustColl); 			
	 			
	 			
	 			log.log(Log.FINE,"customerVO----"+customerVO);
	 			
	 			maintainCustomerRegistrationSession.setCustomerVO(customerVO);
	 			maintainCustomerRegistrationSession.setPageURL("FROMLISTTEMP");
			
			invocationContext.target = REGISTER_SUCCESS;
	}
}