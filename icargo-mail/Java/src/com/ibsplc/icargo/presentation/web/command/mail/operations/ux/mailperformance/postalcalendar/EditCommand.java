/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.postalcalendar.EditCommand.java
 *
 *	Created by	:	A-5219
 *	Created on	:	21-Jun-2020
 *
 *  Copyright 2020 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.postalcalendar;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.postalcalendar.EditCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	21-Jun-2020	:	Draft
 */
public class EditCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAIL PERFORMANCE");
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	private static final String SUCCESS = "edit_screenload";
	private static final String FAILURE = "edit_failure";
	private static final String UPDATE = "edit_success";

	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-5219 on 21-Jun-2020
	 * 	Used for 	:
	 *	Parameters	:	@param invocationContext
	 *	Parameters	:	@throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

log.log(Log.FINE, "\n\n in the save command----------> \n\n");

		MailPerformanceForm mailPerformanceForm =
				(MailPerformanceForm)invocationContext.screenModel;
		MailPerformanceSession mailPerformanceSession =
				getScreenSession(MODULE_NAME,SCREEN_ID);

		ArrayList<USPSPostalCalendarVO> uspsPostalCalendarVOs =
				mailPerformanceSession.getUSPSPostalCalendarVOs();
		USPSPostalCalendarVO selectedCalendar = null;

		if(uspsPostalCalendarVOs != null && uspsPostalCalendarVOs.size() > 0){
			if("EDIT".equals(mailPerformanceForm.getPostalCalendarAction())){
				invocationContext.target = UPDATE;
				if(mailPerformanceSession.getCalendarIndex() != null &&
						mailPerformanceSession.getCalendarIndex().trim().length() > 0){
					int index = Integer.parseInt(mailPerformanceSession.getCalendarIndex());
					selectedCalendar = uspsPostalCalendarVOs.get(index);
					String opFlag=selectedCalendar.getCalOperationFlags();
					if(mailPerformanceForm.getPaymEffectiveDates() != null &&
							!mailPerformanceForm.getPostalDiscEftDate().equals(selectedCalendar.getPaymEffectiveDates())){
						selectedCalendar.setPaymEffectiveDates(mailPerformanceForm.getPostalDiscEftDate());
						opFlag="U";
					}
					if(mailPerformanceForm.getIncCalcDates()!=null &&
							!mailPerformanceForm.getPostalIncCalcDate().equals(selectedCalendar.getIncCalcDates())){
						selectedCalendar.setIncCalcDates(mailPerformanceForm.getPostalIncCalcDate());
						opFlag="U";
					}
					if(mailPerformanceForm.getIncEffectiveDates() !=null &&
							!mailPerformanceForm.getPostalIncEftDate().equals(selectedCalendar.getIncEffectiveDates())){
						selectedCalendar.setIncEffectiveDates(mailPerformanceForm.getPostalIncEftDate());
						opFlag="U";
					}
					if(mailPerformanceForm.getIncRecvDates() !=null &&
							!mailPerformanceForm.getPostalIncRecvDate().equals(selectedCalendar.getIncRecvDates())){
						selectedCalendar.setIncRecvDates(mailPerformanceForm.getPostalIncRecvDate());
						opFlag="U";
					}
					if(mailPerformanceForm.getClmGenDate() !=null &&
							!mailPerformanceForm.getPostalClaimGenDate().equals(selectedCalendar.getClmGenDate())){
						selectedCalendar.setClmGenDate(mailPerformanceForm.getPostalClaimGenDate());
						opFlag="U";
					}
					if("U".equals(opFlag)){
						LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
						selectedCalendar.setCalOperationFlags(opFlag);
						selectedCalendar.setLstUpdUsr(logonAttributes.getUserId().toUpperCase());
						selectedCalendar.setLstUpdTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
					}
				}
			}else{
			selectedCalendar = uspsPostalCalendarVOs.get(mailPerformanceForm.getSelectedRow());
				mailPerformanceSession.setCalendarIndex(String.valueOf(mailPerformanceForm.getSelectedRow()));

			mailPerformanceForm.setPostalPeriod(selectedCalendar.getPeriods());
			mailPerformanceForm.setPostalFromDate(selectedCalendar.getFromDates());
			mailPerformanceForm.setPostalToDate(selectedCalendar.getToDates());
			mailPerformanceForm.setPostalDiscEftDate(selectedCalendar.getPaymEffectiveDates());
			mailPerformanceForm.setPostalIncCalcDate(selectedCalendar.getIncCalcDates());
			mailPerformanceForm.setPostalIncEftDate(selectedCalendar.getIncEffectiveDates());
			mailPerformanceForm.setPostalIncRecvDate(selectedCalendar.getIncRecvDates());
			mailPerformanceForm.setPostalClaimGenDate(selectedCalendar.getClmGenDate());

		invocationContext.target = SUCCESS;
			}
		}
    	return;

	}

}
