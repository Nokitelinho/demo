/*
 * ScreenLoadListRepairReportCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.listrepairreport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.ibase.util.time.TimeConvertor;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListRepairReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListRepairReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up of the MaintainAccessoriesStock screen
 *
 * @author A-2046
 */
public class ScreenLoadListRepairReportCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ScreenLoadListDamageReportCommand");

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String SCREENLOAD_FAILURE = "screenload_failure";

	private static final String REPAIR_STATUS = "uld.defaults.repairStatus";

	private static final String REPAIRHEAD_ONETIME = "uld.defaults.repairhead";

	private static final String ULD_STATUS = "uld.defaults.overallStatus";

	private static final String SCREENID = "uld.defaults.listrepairreport";

	private static final String MODULE_NAME = "uld.defaults";
	
	private static final String CURRENCY_VALUE="uld.defaults.uldinvoicingcurrency";
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	ApplicationSessionImpl applicationSessionImpl=getApplicationSession();
		LogonAttributes logonAttributes=applicationSessionImpl.getLogonVO();
		String companyCode=logonAttributes.getCompanyCode();

		ListRepairReportForm form = (ListRepairReportForm) invocationContext.screenModel;
		ListRepairReportSession session = getScreenSession(MODULE_NAME, SCREENID);
		SharedDefaultsDelegate sharedDefaultsDelegate =
			  new SharedDefaultsDelegate();
		Map hashMap = null;
	    Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(REPAIR_STATUS);
		oneTimeList.add(ULD_STATUS);
		oneTimeList.add(REPAIRHEAD_ONETIME);
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues
			             							  (companyCode,oneTimeList);
		}catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			exception = handleDelegateException(businessDelegateException);
		}
		Collection<OneTimeVO> uldStatus=(Collection<OneTimeVO>) hashMap.get(ULD_STATUS);
		Collection<OneTimeVO> repairStatus=(Collection<OneTimeVO>) hashMap.get(REPAIR_STATUS);
		Collection<OneTimeVO> repairHead=(Collection<OneTimeVO>) hashMap.get(REPAIRHEAD_ONETIME);
		log.log(Log.INFO, "uldStatus", uldStatus);
		log.log(Log.INFO, "repairStatus", repairStatus);
		log.log(Log.INFO, "repairHead", repairHead);
		session.setUldStatus(uldStatus);
		session.setRepairStatus(repairStatus);
		session.setRepairHead(repairHead);
		log.log(Log.INFO, "uldStatus", session.getUldStatus());
		log.log(Log.INFO, "repairStatus", session.getRepairStatus());
		log.log(Log.INFO, "repairHead", session.getRepairHead());
		session.setULDDamageRepairDetailsVOs(null);
		
		LocalDate dateFrom = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,true);
		String reportedDateFromString = TimeConvertor.toStringFormat(dateFrom.toCalendar(),
		"dd-MMM-yyyy");
		form.setRepairedDateFrom(reportedDateFromString);
		
		LocalDate dateTo = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,true);
		String reportedDateToString = TimeConvertor.toStringFormat(dateTo.toCalendar(),
		"dd-MMM-yyyy");
		form.setRepairedDateTo(reportedDateToString);
		log.log(Log.INFO, "RepairedDateTo", form.getRepairedDateTo());
		log.log(Log.INFO, "RepairedDateFrom", form.getRepairedDateFrom());
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		//SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, String> systemUnitCodes = new HashMap<String, String>();
		Collection<String> systemUnitValues = new ArrayList<String>();
		

			systemUnitValues.add(CURRENCY_VALUE);
			log.log(Log.INFO,"-------------------Calling findSystemParameterByCodes");
			try {
				systemUnitCodes = sharedDefaultsDelegate
						.findSystemParameterByCodes(systemUnitValues);
			
			String ownCurrencyValue = systemUnitCodes.get(CURRENCY_VALUE);
			log.log(Log.FINE, "The ownCurrencyValue Value is ",
					ownCurrencyValue);
			//ownVolumeUnit = systemUnitCodes.get(SYSTEM_VOLUMECODE);
			form.setCurrencyValue(ownCurrencyValue);
			} catch (BusinessDelegateException e) {
				
				e.getMessage();
			}
			log
					.log(Log.FINE, "The Currency Value is ", form.getCurrencyValue());
		session.removeULDRepairFilterVO();
		
		log.log(Log.INFO, "airline", logonAttributes.getOwnAirlineCode());
		log.log(Log.INFO, "airport", logonAttributes.getAirportCode());
		ULDRepairFilterVO filterVO = new ULDRepairFilterVO();
		if(logonAttributes.isAirlineUser()){    		
    		form.setRepairDisableStatus("airline");
    	}
    	else{
    		filterVO.setCurrentStation(logonAttributes.getAirportCode());
    		form.setRepairDisableStatus("GHA");
    	}
		session.setULDRepairFilterVO(filterVO);
		
		invocationContext.target=SCREENLOAD_SUCCESS;
		log.exiting("ScreenLoadCommand", "execute");
    }
}
