
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailperformance;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.CoTerminusFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.CoTerminusVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailPerformanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


public class ListCommand extends BaseCommand {

	private static final String SUCCESS = "list_success";
	private static final String FAILURE = "list_failure";
	private Log log = LogFactory.getLogger("MailTracking,MailPerformance");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mailperformance";
	
	private static final String GPA_EMPTY = 
		"mailtracking.defaults.mailperformance.msg.err.gpaempty";
	private static final String NO_MATCH = "mailtracking.defaults.noresultsfound";
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the list command----------> \n\n");
    	
    	MailPerformanceForm mailPerformanceForm =
							(MailPerformanceForm)invocationContext.screenModel;
		MailPerformanceSession mailPerformaceSession = 
										getScreenSession(MODULE_NAME,SCREEN_ID);
    	
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    String companyCode = logonAttributes.getCompanyCode();
	   
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    
	    ArrayList<CoTerminusVO> coTerminusVOs = null;
	    
	    MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
	    
	    if(null!=mailPerformanceForm.getPacode()&&("").equals(mailPerformanceForm.getPacode())) {
	    	log.log(Log.FINE, "\n\n GPA CODE EMPTY =============> \n\n");
	    	ErrorVO error = new ErrorVO(GPA_EMPTY);
	    	errors.add(error);
	    	invocationContext.addAllError(errors);
	    	mailPerformaceSession.setCoTerminusVOs(null);
	    	mailPerformanceForm.setScreenFlag("ctRadioBtn");
	    	mailPerformanceForm.setScreenStatusFlag
						(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    	invocationContext.target = FAILURE;
	    	return;
	    }
	    
	    
	    	String airport = mailPerformanceForm.getAirport().toUpperCase().trim();
	    	
	   
	    	//mailPerformanceForm.setDisableSave("N");
	    try {
	    	CoTerminusFilterVO filterVO=new CoTerminusFilterVO();
	    	filterVO.setAirportCodes(airport);
	    	filterVO.setCompanyCode(companyCode);
	    	filterVO.setGpaCode(mailPerformanceForm.getPacode());
	    	filterVO.setResditModes(mailPerformanceForm.getFilterResdit());
	    	if(mailPerformanceForm.getReceivedFromTruck()!=null)
	    		filterVO.setReceivedfromTruck(mailPerformanceForm.getReceivedFromTruck());
	    	else
	    		filterVO.setReceivedfromTruck("N");
	    	log.log(Log.FINE, "\n\n CoTerminusFilterVO=========>",
					filterVO.getAirportCodes()+filterVO.getGpaCode()+filterVO.getResditModes()+filterVO.getReceivedfromTruck());
	    	coTerminusVOs = (ArrayList<CoTerminusVO>)delegate.findAllCoTerminusAirports(filterVO);
	    	log.log(Log.FINE, "\n\n coTerminusVOs after find=========>",
	    			coTerminusVOs);
			if(coTerminusVOs == null || coTerminusVOs.size()==0) {
	    		log.log(Log.SEVERE,"\n\n *******no match found********** \n\n");
				ErrorVO error = new ErrorVO(NO_MATCH);
				errors.add(error);
				invocationContext.addAllError(errors);
				mailPerformaceSession.setCoTerminusVOs(null);
				mailPerformanceForm.setScreenFlag("ctRadioBtn");
				mailPerformanceForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		    	invocationContext.target = FAILURE;
		    	return;
	    	}
	    }catch(BusinessDelegateException businessDelegateException) {
	    	
	    	errors = handleDelegateException(businessDelegateException);
	    }
	    //mailPerformaceSession.setAirport(airport);
	    mailPerformaceSession.setCoTerminusVOs(coTerminusVOs);
	    mailPerformanceForm.setScreenFlag("ctRadioBtn");
	    mailPerformanceForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    invocationContext.target = SUCCESS;

	}

}
