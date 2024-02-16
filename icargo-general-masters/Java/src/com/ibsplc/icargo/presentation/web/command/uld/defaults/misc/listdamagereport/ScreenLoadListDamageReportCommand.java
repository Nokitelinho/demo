/*
 * ScreenLoadListDamageReportCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.listdamagereport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.ibase.util.time.TimeConvertor;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up uld
 * 
 * @author A-2052
 */
public class ScreenLoadListDamageReportCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("ScreenLoadListDamageReportCommand");
    
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
    
	private static final String SCREENLOAD_FAILURE = "screenload_failure";

	private static final String DAMAGE_STATUS = "uld.defaults.damageStatus";
    
	private static final String ULD_STATUS = "uld.defaults.overallStatus";
    
	private static final String REPAIR_STATUS = "uld.defaults.repairStatus";
    
	private static final String SCREENID = "uld.defaults.listdamagereport";
	
	private static final String MODULE_NAME = "uld.defaults";
	
	private static final String FACILITYTYPE_ONETIME = "uld.defaults.facilitytypes";
	
	private static final String PARTYTYPE_ONETIME = "uld.defaults.PartyType";
	 
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

		ListDamageReportForm form = (ListDamageReportForm) invocationContext.screenModel;
		ListDamageReportSession session = getScreenSession(MODULE_NAME, SCREENID);
		session.setULDDamagePictureVO(null);
		SharedDefaultsDelegate sharedDefaultsDelegate =
			  new SharedDefaultsDelegate();
		Map hashMap = null;
	    Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(DAMAGE_STATUS);
		oneTimeList.add(ULD_STATUS);
		oneTimeList.add(REPAIR_STATUS);
		oneTimeList.add(FACILITYTYPE_ONETIME);
		oneTimeList.add(PARTYTYPE_ONETIME);
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues
			             							  (companyCode,oneTimeList);
		}catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			exception = handleDelegateException(businessDelegateException);
		}
		Collection<OneTimeVO> uldStatus=(Collection<OneTimeVO>) hashMap.get(ULD_STATUS);
		Collection<OneTimeVO> damageStatus=(Collection<OneTimeVO>) hashMap.get(DAMAGE_STATUS);
		Collection<OneTimeVO> repairStatus=(Collection<OneTimeVO>) hashMap.get(REPAIR_STATUS);
		Collection<OneTimeVO> facilitytype=(Collection<OneTimeVO>) hashMap.get(FACILITYTYPE_ONETIME);
		Collection<OneTimeVO> partyType=(Collection<OneTimeVO>) hashMap.get(PARTYTYPE_ONETIME);
		session.setUldStatus(uldStatus);
		session.setDamageStatus(damageStatus);
		session.setRepairStatus(repairStatus);
		session.setFacilityType(facilitytype);
		session.setPartyType(partyType);
		log.log(Log.INFO, "session.getUldStatus()--------------->", session.getUldStatus());
		log.log(Log.INFO, "session.getDamageStatus()--------------->", session.getDamageStatus());
		log.log(Log.INFO, "session.getRepairStatus--------------->", session.getRepairStatus());
		LocalDate date = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,true);
		String reportedDateFromString = TimeConvertor.toStringFormat(date.toCalendar(),
		"dd-MMM-yyyy");
		form.setRepairedDateFrom(reportedDateFromString);
		
		LocalDate dateTo = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,true);
		String reportedDateToString = TimeConvertor.toStringFormat(dateTo.toCalendar(),
		"dd-MMM-yyyy");
		form.setRepairedDateTo(reportedDateToString);
		form.setCurrentStation(logonAttributes.getStationCode());
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		session.removeULDDamageFilterVO();
		session.removeULDDamageRepairDetailsVOs();
		session.removeScreenId();
		ULDDamageFilterVO filterVO = new ULDDamageFilterVO();
		if(logonAttributes.isAirlineUser()){    		
    		form.setDamageDisableStatus("airline");
    	}
    	else{
    		filterVO.setCurrentStation(logonAttributes.getAirportCode());
    		form.setDamageDisableStatus("GHA");
    	}
		session.setULDDamageFilterVO(filterVO);
		
		invocationContext.target=SCREENLOAD_SUCCESS;
		log.exiting("ScreenLoadCommand", "execute"); 
    }
}
