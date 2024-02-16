package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.postalcalendar;
import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplaySubtype;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.postalcalendar.ListCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	04-Jul-2018	:	Draft
 */
public class ListCommand extends BaseCommand{
	private static final String FAILURE = "list_failure";
	private static final String SUCCESS_AJAX= "success_ajax";
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	private static final String BLANK = "";
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	private static final String GPA_EMPTY = 
			"mailtracking.defaults.ux.mailperformance.msg.err.gpaempty";
	private static final String CALTYPE_EMPTY = 
			"mailtracking.defaults.ux.mailperformance.msg.err.caltypeempty";
	private static final String DATE_ERROR =
			"mailtracking.defaults.ux.mailperformance.msg.err.dateerror";
	private static final String NO_MATCH = 
			"mailtracking.defaults.ux.mailperformance.msg.err.noresultsfound";
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-8164 on 04-Jul-2018
	 * 	Used for 	:   ICRD-236925
	 *	Parameters	:	@param invocationContext
	 *	Parameters	:	@throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.FINE, "\n\n in the list command----------> \n\n");
		MailPerformanceForm mailPerformanceForm =
				(MailPerformanceForm)invocationContext.screenModel; 
		MailPerformanceSession mailPerformanceSession = 
							getScreenSession(MODULE_NAME,SCREEN_ID);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    String companyCode = logonAttributes.getCompanyCode();
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    ArrayList<USPSPostalCalendarVO> uSPSPostalCalendarVOs = null;
	    USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO = null;
	    MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
	    mailPerformanceForm.setStatusFlag(BLANK);
	    if(null!=mailPerformanceForm.getCalPacode()&&("").equals(mailPerformanceForm.getCalPacode())) {
	    	log.log(Log.FINE, "\n\n GPA CODE EMPTY =============> \n\n");
	    	ErrorVO error = new ErrorVO(GPA_EMPTY);
	    	error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
			error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
	    	errors.add(error);
	    	invocationContext.addAllError(errors);
	    	mailPerformanceForm.setStatusFlag("List_fail");
	    	mailPerformanceSession.setUSPSPostalCalendarVOs(null);
	    	mailPerformanceForm.setScreenFlag("postCalender");
	    	mailPerformanceForm.setScreenStatusFlag
						(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    	invocationContext.target = FAILURE;
	    	return;
	    }
	    if(null!=mailPerformanceForm.getFilterCalender()&&BLANK.equals(mailPerformanceForm.getFilterCalender())) {
	    	log.log(Log.FINE, "\n\n CALENDAR TYPE EMPTY =============> \n\n");
	    	ErrorVO error = new ErrorVO(CALTYPE_EMPTY);
	    	error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
			error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
	    	errors.add(error);
	    	invocationContext.addAllError(errors);
	    	mailPerformanceForm.setStatusFlag("List_fail");
	    	mailPerformanceSession.setUSPSPostalCalendarVOs(null);
	    	mailPerformanceForm.setScreenFlag("postCalender");
	    	mailPerformanceForm.setScreenStatusFlag
						(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    	invocationContext.target = FAILURE;
	    	return;
	    }
		if(null!=mailPerformanceForm.getCalValidFrom() && 0<mailPerformanceForm.getCalValidFrom().length()
	    		&& null!=mailPerformanceForm.getCalValidTo() && 0<mailPerformanceForm.getCalValidTo().length()){
	    		LocalDate calValidFrom=new LocalDate
	    				(LocalDate.NO_STATION,Location.NONE,false);
	    		LocalDate calValidTo=new LocalDate
	    				(LocalDate.NO_STATION,Location.NONE,false);
	    		calValidFrom.setDate(mailPerformanceForm.getCalValidFrom());
	    		calValidTo.setDate(mailPerformanceForm.getCalValidTo());          
	    			if(calValidTo.isLesserThan(calValidFrom)){
	    				ErrorVO error = new ErrorVO(DATE_ERROR);
	    		    	error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
	    				error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
	    		    	errors.add(error);
	    		    	invocationContext.addAllError(errors);
	    		    	mailPerformanceForm.setStatusFlag("List_fail");
	    		    	mailPerformanceSession.setUSPSPostalCalendarVOs(null);
	    		    	mailPerformanceForm.setScreenFlag("postCalender");
	    		    	mailPerformanceForm.setScreenStatusFlag
	    							(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    		    	invocationContext.target = FAILURE;
	    		    	return;
	    			}
	    		}
		if("EDIT".equals(mailPerformanceForm.getPostalCalendarAction()) ||
				"RELOAD".equals(mailPerformanceForm.getPostalCalendarAction())){
			uSPSPostalCalendarFilterVO = mailPerformanceSession.getFilterVO();
			uSPSPostalCalendarVOs = mailPerformanceSession.getUSPSPostalCalendarVOs();
			if(uSPSPostalCalendarFilterVO.getCalPacode() != null && uSPSPostalCalendarFilterVO.getCalPacode().trim().length() > 0)
				mailPerformanceForm.setCalPacode(uSPSPostalCalendarFilterVO.getCalPacode());
			if(uSPSPostalCalendarFilterVO.getCalValidFrom() != null)
				mailPerformanceForm.setCalValidFrom(uSPSPostalCalendarFilterVO.getCalValidFrom().toDisplayDateOnlyFormat());
			if(uSPSPostalCalendarFilterVO.getCalValidTo() != null)
				mailPerformanceForm.setCalValidTo(uSPSPostalCalendarFilterVO.getCalValidTo().toDisplayDateOnlyFormat());
		}else{
			mailPerformanceForm.setPostalCalendarAction("");
	    String gpacode=mailPerformanceForm.getCalPacode().toUpperCase();
	    try{
	    	uSPSPostalCalendarFilterVO=
	    			new USPSPostalCalendarFilterVO();	    	
	    	if (null!=mailPerformanceForm.getCalValidFrom() && 0<mailPerformanceForm.getCalValidFrom().length()) {
	    		LocalDate calValidFrom=new LocalDate
	    				(LocalDate.NO_STATION,Location.NONE,false);
				uSPSPostalCalendarFilterVO.setCalValidFrom(calValidFrom.setDate(
						mailPerformanceForm.getCalValidFrom()));
			}
	    	if (null!=mailPerformanceForm.getCalValidTo() && 0<mailPerformanceForm.getCalValidTo().length()) {
	    		LocalDate calValidTo=new LocalDate
	    				(LocalDate.NO_STATION,Location.NONE,false);
				uSPSPostalCalendarFilterVO.setCalValidTo(calValidTo.setDate(
						mailPerformanceForm.getCalValidTo())); 
			}
	    	uSPSPostalCalendarFilterVO.setCompanyCode(companyCode);
	    	uSPSPostalCalendarFilterVO.setCalPacode(gpacode);
	    	uSPSPostalCalendarFilterVO.setFilterCalender(mailPerformanceForm.getFilterCalender());
	    	mailPerformanceSession.setFilterVO(uSPSPostalCalendarFilterVO);
	    	uSPSPostalCalendarVOs= (ArrayList<USPSPostalCalendarVO>)delegate.
	    			listPostalCalendarDetails(uSPSPostalCalendarFilterVO);
	    }catch(BusinessDelegateException businessDelegateException) {
	    	errors = handleDelegateException(businessDelegateException);
	    }
	    }
	    	if(uSPSPostalCalendarVOs == null || uSPSPostalCalendarVOs.size()==0) {
	    		log.log(Log.SEVERE,"\n\n *******no match found********** \n\n");
				ErrorVO error = new ErrorVO(NO_MATCH);
				error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
				error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
				errors.add(error);
				invocationContext.addAllError(errors);
				mailPerformanceSession.setUSPSPostalCalendarVOs(null);
				mailPerformanceForm.setScreenFlag("postCalender"); 
				mailPerformanceForm.setStatusFlag("No_data");      
				mailPerformanceForm.setScreenStatusFlag 
							(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		    	invocationContext.target = SUCCESS_AJAX;       
		    	return;
	    	}
	    
	    mailPerformanceSession.setUSPSPostalCalendarVOs(uSPSPostalCalendarVOs);
	    mailPerformanceForm.setScreenFlag("postCalender");
	    mailPerformanceForm.setStatusFlag("List_success");
	    mailPerformanceForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    if("EDIT".equals(mailPerformanceForm.getPostalCalendarAction())){
	    	invocationContext.target = "list_success";
	    }else if("RELOAD".equals(mailPerformanceForm.getPostalCalendarAction())){
	    	invocationContext.target = SUCCESS_AJAX;
	    }
	    else{
	    invocationContext.target = SUCCESS_AJAX;
	    }
	}
}