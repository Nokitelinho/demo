package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.rdtoffset;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailRdtMasterVO;
import com.ibsplc.icargo.business.mail.operations.vo.RdtMasterFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListCommand extends BaseCommand{

	private static final String SUCCESS = "list_success";
	private static final String FAILURE = "list_failure";
	private static final String INTERNATIONAL = "I";
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	private static final String GPA_EMPTY = 
			"mailtracking.defaults.ux.mailperformance.msg.err.gpaempty";
		private static final String NO_MATCH = "mailtracking.defaults.ux.mailperformance.msg.err.noresultsfound";
		
		
	@Override
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
	    
	    ArrayList<MailRdtMasterVO> mailRdtMasterVOs = null;
	    
	    MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
	    mailPerformanceForm.setStatusFlag("");
	    
	    if(mailPerformanceForm.getRdtPacode()==null  || ("").equals(mailPerformanceForm.getRdtPacode())) {   
	    	log.log(Log.FINE, "\n\n GPA CODE EMPTY =============> \n\n");
	    	ErrorVO error = new ErrorVO(GPA_EMPTY);
	    	/*error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
			error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);*/
	    	errors.add(error);
	    	invocationContext.addAllError(errors);
	    	mailPerformanceForm.setStatusFlag("List_fail");
	    	mailPerformaceSession.setMailRdtMasterVOs(null);
	    	mailPerformanceForm.setScreenFlag("rdtRadioBtn");
	    	mailPerformanceForm.setScreenStatusFlag
						(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    	invocationContext.target = FAILURE;
	    	return;
	    }
	    
	    String airport = mailPerformanceForm.getRdtAirport().toUpperCase().trim();
    	
		   
    	
    try {
    	RdtMasterFilterVO filterVO=new RdtMasterFilterVO();
    	filterVO.setMailType(INTERNATIONAL);
    	filterVO.setAirportCodes(airport);
    	filterVO.setCompanyCode(companyCode);
    	filterVO.setGpaCode(mailPerformanceForm.getRdtPacode());
    	
    	log.log(Log.FINE, "\n\n RdtMasterFilterVO=========>",
				filterVO.getAirportCodes()+filterVO.getGpaCode());
    	mailRdtMasterVOs = (ArrayList<MailRdtMasterVO>)delegate.findRdtMasterDetails(filterVO); 
    	log.log(Log.FINE, "\n\n mailRdtMasterVOs after find=========>",
    			mailRdtMasterVOs);
		if(mailRdtMasterVOs == null || mailRdtMasterVOs.size()==0) {
    		log.log(Log.SEVERE,"\n\n *******no match found********** \n\n");
			ErrorVO error = new ErrorVO(NO_MATCH);
			/*error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
			error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);*/
			errors.add(error);
			invocationContext.addAllError(errors);
			mailPerformaceSession.setMailRdtMasterVOs(null);
			mailPerformanceForm.setScreenFlag("rdtRadioBtn");
			mailPerformanceForm.setScreenStatusFlag
						(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    	invocationContext.target = FAILURE;
	    	return;
    	}
    }catch(BusinessDelegateException businessDelegateException) {
    	
    	errors = handleDelegateException(businessDelegateException);
    }
    //mailPerformaceSession.setAirport(airport);
    mailPerformaceSession.setMailRdtMasterVOs(mailRdtMasterVOs);
    mailPerformanceForm.setScreenFlag("rdtRadioBtn");
    mailPerformanceForm.setStatusFlag("List_success");
    mailPerformanceForm.setScreenStatusFlag
						(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    invocationContext.target = SUCCESS;
	}
	
}