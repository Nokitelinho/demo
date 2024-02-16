package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.report;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingReportsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingReportsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class FindGPANameCommand extends BaseCommand{

	private static final String SUCCESS="findgpa_Success";
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID ="mailtracking.mra.gpabilling.gpabillingreports";
	
	private Log log = LogFactory.getLogger("FindGPANameCommand");
	/**
	* Method to execute the command
	* @param invocationContext
	* @exception  CommandInvocationException
	*/
	public void execute(InvocationContext invocationContext)
							throws CommandInvocationException {
		
		log.log(Log.FINE, "\n\n FindGPANameCommand----------> \n\n");
	
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();  
		LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
		GPABillingReportsSession session=(GPABillingReportsSession)getScreenSession(MODULE_NAME,SCREEN_ID); 
		String companyCode = logonAttributes.getCompanyCode();
		GPABillingReportsForm form = (GPABillingReportsForm)invocationContext.screenModel;
		try {
			
			MailTrackingMRADelegate delegate
										= new MailTrackingMRADelegate();
			
			
			String paCode = "";
			if(form.getGpaCode() != null 
					&& form.getGpaCode().length() > 0){
				paCode = form.getGpaCode().toUpperCase();
			}
			if(form.getGpaCodeGpa51() != null &&
					form.getGpaCodeGpa51().length() > 0){
				paCode	= form.getGpaCodeGpa51().toUpperCase();
			}
			if(form.getGpaCodeGpa66() != null &&
					form.getGpaCodeGpa66().length() > 0){
				paCode = form.getGpaCodeGpa66().toUpperCase();
			}
			if(form.getGpaCodeGpaBillSmy() != null &&
					form.getGpaCodeGpaBillSmy().length() > 0){
				paCode = form.getGpaCodeGpaBillSmy().toUpperCase();
			}
			if(form.getGpaCodePeriod51() != null &&
					form.getGpaCodePeriod51().length() > 0){
				paCode =form.getGpaCodePeriod51().toUpperCase();
			} 
			if(form.getGpaCodePeriod66() != null &&
					form.getGpaCodePeriod66().length() > 0){
				paCode =form.getGpaCodePeriod66().toUpperCase();
			}
			if(form.getGpaCodePeriodBillSmy() != null &&
					form.getGpaCodePeriodBillSmy().length() > 0){
				paCode =form.getGpaCodePeriodBillSmy().toUpperCase();
			}
			
		
			String paName = "";
			//paName = paLovForm.getDescription();
			int displayPage=1;
			int defaultPgeSize=10;
			Page<PostalAdministrationVO> page=delegate.findPALov(
										companyCode,paCode,paName,displayPage,defaultPgeSize);
			String description = null;
			log.log(Log.INFO,"--------------> paCode", paCode );
			if(page != null &&
					page.size() > 0){
			for(PostalAdministrationVO vo : page){
				if(paCode.equals(vo.getPaCode())){
					description = vo.getPaName();
				}
			}
				
			}
			
			if("RPTMRA033".equals(form.getSelectedReport())){
				form.setGpaNamePeriodBillSmy(description);
			}
			if("RPTMRA034".equals(form.getSelectedReport())){
				form.setGpaNameGpaBillSmy(description);
			}
			if("RPTMRA035".equals(form.getSelectedReport())){
				form.setGpaNamePeriod51(description);
			}
			if("RPTMRA036".equals(form.getSelectedReport())){
				form.setGpaNameGpa51(description);
			}
			if("RPTMRA037".equals(form.getSelectedReport())){
				form.setGpaNamePeriod66(description);
			}
			if("RPTMRA038".equals(form.getSelectedReport())){
				form.setGpaNameGpa66(description);
			}
			if("RPTMRA039".equals(form.getSelectedReport())){
				form.setGpaName(description);
			}
			
			log.log(Log.FINE, "\n\n FindGPACode----------> \n\n");
			log.log(Log.INFO, description);
		

		
			
		} catch (BusinessDelegateException ex) {
			handleDelegateException(ex);
		}
		invocationContext.target =SUCCESS;
	}

}
