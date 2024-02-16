package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.postalcalendar;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.postalcalendar.ClearCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	04-Jul-2018	:	Draft
 */
public class ClearCommand extends BaseCommand{
	private static final String SUCCESS = "clear_success";
	private static final String BLANK = "";
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-8164 on 04-Jul-2018
	 * 	Used for 	:	ICRD-236925
	 *	Parameters	:	@param invocationContext
	 *	Parameters	:	@throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.FINE, "\n\n in the clear command----------> \n\n");
		MailPerformanceForm mailperformanceForm =
				(MailPerformanceForm)invocationContext.screenModel;
		MailPerformanceSession mailperformanceSession = 
							getScreenSession(MODULE_NAME,SCREEN_ID);
		mailperformanceForm.setCalPacode(BLANK);
		mailperformanceForm.setCalValidFrom(BLANK);
		mailperformanceForm.setCalValidTo(BLANK);
		SharedDefaultsDelegate defaultsDelegate = new SharedDefaultsDelegate();
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	String companyCode = logonAttributes.getCompanyCode();
    	Map<String, Collection<OneTimeVO>> hashMap = 
    		      new HashMap<String, Collection<OneTimeVO>>();
    	 Collection<String> oneTimeList = new ArrayList();
    	 oneTimeList.add("mail.operations.usps.calendartype"); 
    	 try
 	    {
 	      hashMap = defaultsDelegate.findOneTimeValues(companyCode, 
 	        oneTimeList);
 	    }
 	    catch (BusinessDelegateException localBusinessDelegateException3)
 	    {
 	      this.log.log(7, "onetime fetch exception");
 	    }
    	 Collection<OneTimeVO> calendarTypes = (Collection<OneTimeVO>) hashMap
 				.get("mail.operations.usps.calendartype");
 		if (calendarTypes != null) {
 			log.log(Log.INFO, "Sizeee----", calendarTypes.size());
 			for (OneTimeVO list : calendarTypes) {
 				log.log(Log.INFO, "LIST----------", list.getFieldDescription());
 			}
 		}
 		mailperformanceSession.setCalendarTypes((ArrayList<OneTimeVO>)calendarTypes);
		mailperformanceSession.setUSPSPostalCalendarVOs(null);
		mailperformanceSession.setCalendarIndex(null);
		mailperformanceSession.setFilterVO(null);
		mailperformanceForm.setScreenFlag("postCalender");
		mailperformanceForm.setPostalCalendarAction("");
		mailperformanceForm.setScreenStatusFlag
					(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	invocationContext.target = SUCCESS;
    	mailperformanceForm.setStatusFlag("List_fail"); 
	}
}