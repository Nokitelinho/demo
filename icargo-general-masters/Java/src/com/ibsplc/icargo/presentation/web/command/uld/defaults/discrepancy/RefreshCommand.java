/*
 * RefreshCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.discrepancy;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListUldDiscrepancySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDDiscrepanciesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2052
 *
 */
public class RefreshCommand extends BaseCommand {

	private static final String SCREENID = "uld.defaults.listulddiscrepancies";

	private static final String MODULE = "uld.defaults";

	private Log log = LogFactory.getLogger("RefreshCommand");

	private static final String REFRESH_SUCCESS = "refresh_success";

	
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("RefreshCommand", "ENTRY");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		ListULDDiscrepanciesForm form = 
			(ListULDDiscrepanciesForm) invocationContext.screenModel;
		ListUldDiscrepancySession session = getScreenSession(MODULE, SCREENID);
		String uldnumber = session.getULDDiscrepancyFilterVODetails().getUldNumber();
		form.setUldNoChild(uldnumber);
		log.log(Log.FINE, "form.getUldNoChild()------------>>>", form.getUldNoChild());
		ULDDiscrepancyFilterVO filterVO = new ULDDiscrepancyFilterVO();
    	
    	if(logonAttributes.isAirlineUser()){    		
    		form.setDiscDisableStat("airline");
    	}
    	else{
    		filterVO.setReportingStation(logonAttributes.getAirportCode());
    		form.setDiscDisableStat("GHA");
    	}
       	//Commented and added by a-3045 for bug13872 starts
    	//session.setULDDiscrepancyFilterVODetails(filterVO);
    	filterVO=session.getULDDiscrepancyFilterVODetails();
    	Page<ULDDiscrepancyVO> uldDiscrepancyVOs = null;
    	ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
    	try {
			uldDiscrepancyVOs = delegate
					.listULDDiscrepancyDetails(filterVO);
		} catch (BusinessDelegateException e) {			
			e.getMessage();
			handleDelegateException(e);
		}
		if(uldDiscrepancyVOs!=null && uldDiscrepancyVOs.size()>0){
			form.setUldNoChild(uldDiscrepancyVOs.get(0).getUldNumber());
			form.setDiscrepancyCode(uldDiscrepancyVOs.get(0).getDiscrepencyCode());
			form.setDiscrepancyDate(uldDiscrepancyVOs.get(0).getDiscrepencyDate()
					.toDisplayDateOnlyFormat());
			form.setReportingStationChild(uldDiscrepancyVOs.get(0).getReportingStation());
			form.setLocation(uldDiscrepancyVOs.get(0).getLocation());
			form.setRemarks(uldDiscrepancyVOs.get(0).getRemarks());
		}else if(filterVO.getUldNumber()!=null && filterVO.getUldNumber().length()>0){
			ULDVO uldVO = null;
			try {

				uldVO = new ULDDefaultsDelegate().findULDDetails(logonAttributes.getCompanyCode(), filterVO
						.getUldNumber().toUpperCase());

			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			}
			log.log(Log.FINE, "uldVO is ----------->", uldVO);
			if (uldVO == null) {
				log.log(Log.FINE, "!!!!!uldVO is null ");			
			} else {
				if (uldVO.getUldNumber() != null) {
					form.setUldNoChild(uldVO.getUldNumber());
				}
				form.setDiscrepancyDate(new LocalDate(getApplicationSession()
						.getLogonVO().getAirportCode(), Location.ARP, true)
						.toDisplayDateOnlyFormat());
				if (uldVO.getCurrentStation() != null) {
					form.setReportingStationChild(uldVO.getCurrentStation());
				}
				if (uldVO.getLocation() != null
						&& !("NIL").equals(uldVO.getLocation())) {
					form.setLocation(uldVO.getLocation());
				}
				if (uldVO.getFacilityType() != null
						&& !("NIL").equals(uldVO.getFacilityType())) {
					form.setFacilityType(uldVO.getFacilityType());
				}
				form.setListFlag("Y");
			}		
		}
    	
		form.setFlag("fromlistuld");
		log.exiting("RefreshCommand", "EXIT");
		invocationContext.target = REFRESH_SUCCESS;

	}



}
