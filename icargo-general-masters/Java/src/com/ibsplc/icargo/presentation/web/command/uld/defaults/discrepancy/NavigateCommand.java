/*
 * NavigateCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.discrepancy;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainUldDiscrepancySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainUldDiscrepanciesForm;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;

/**
 * 
 * @author A-2052
 * 
 */
public class NavigateCommand extends BaseCommand {

	private static final String SCREENID = "uld.defaults.maintainulddiscrepancies";

	private static final String MODULE = "uld.defaults";

	private static final String NAVIGATE_SUCCESS = "navigate_success";
	
	private Log log = LogFactory.getLogger("NavigateCommand");

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("NavigateCommand", "Navigate Command");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MaintainUldDiscrepanciesForm form = (MaintainUldDiscrepanciesForm) invocationContext.screenModel;
		MaintainUldDiscrepancySession session = getScreenSession(MODULE, SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String companycode = logonAttributes.getCompanyCode();
		
		ArrayList<ULDDiscrepancyVO> uldDiscrepancyVOs = session
				.getULDDiscrepancyVOs();
		ULDDiscrepancyVO vo = new ULDDiscrepancyVO();
		if (uldDiscrepancyVOs != null) {
			ULDDiscrepancyVO uldDiscrepancyVO = uldDiscrepancyVOs.get(Integer
					.parseInt(form.getUldCurrentPageNum()) - 1);
			uldDiscrepancyVO = updateULDDiscrepancyVO(session,
					uldDiscrepancyVO, form, logonAttributes);
			uldDiscrepancyVOs.set(
					Integer.parseInt(form.getUldCurrentPageNum()) - 1,
					uldDiscrepancyVO);
			session.setULDDiscrepancyVOs(uldDiscrepancyVOs);
		}

		form.setCloseFlag(session.getPageURL());
		form.setNavigate(true);
		form.setSaveFlag(false);
		if (form.getDiscrepancyDate() != null
				&& form.getDiscrepancyDate().trim().length() > 0) {
			errors = validateDiscrepancyDate(form, companycode);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				int displayPageNum = Integer.parseInt
				(form.getDisplayPage());	
				form.setDisplayPage(form.getCurrentPageNum());

				invocationContext.target = NAVIGATE_SUCCESS;
				return;
			}
		}
		vo = uldDiscrepancyVOs
				.get(Integer.parseInt(form.getUldDisplayPage()) - 1);
		if (vo != null) {
			form.setUldNoChild(vo.getUldNumber());
			form.setDiscrepancyCode(vo.getDiscrepencyCode());
			form.setDiscrepancyDate(vo.getDiscrepencyDate()
					.toDisplayDateOnlyFormat());
			form.setReportingStationChild(vo.getReportingStation());
			form.setRemarks(vo.getRemarks());
		}
		form.setUldCurrentPageNum(form.getUldDisplayPage());
		
		invocationContext.target = NAVIGATE_SUCCESS;
	}

	/**
	 * 
	 * @param session
	 * @param uldDiscrepancyVO
	 * @param actionForm
	 * @param logonAttributes
	 */
	public ULDDiscrepancyVO updateULDDiscrepancyVO(
			MaintainUldDiscrepancySession session,
			ULDDiscrepancyVO uldDiscrepancyVO,
			MaintainUldDiscrepanciesForm actionForm, LogonAttributes logonAttributes) {
		log.entering("NavigateCommand", "updateULDDiscrepancyVO");
		if (uldDiscrepancyVO != null) {
			uldDiscrepancyVO.setCompanyCode(logonAttributes.getCompanyCode());
			if (actionForm.getUldNoChild() != null
					&& actionForm.getUldNoChild().trim().length() > 0) {
				uldDiscrepancyVO.setUldNumber(actionForm.getUldNoChild()
						.toUpperCase());
			} 
			if (actionForm.getDiscrepancyCode() != null
					&& actionForm.getDiscrepancyCode().trim().length() > 0) {
				uldDiscrepancyVO.setDiscrepencyCode(actionForm
						.getDiscrepancyCode().toUpperCase());
			}
			
			if (actionForm.getReportingStationChild() != null
					&& actionForm.getReportingStationChild().trim().length() > 0) {
				uldDiscrepancyVO.setReportingStation(actionForm
						.getReportingStationChild().toUpperCase());
			}
			LocalDate discDate = new LocalDate(uldDiscrepancyVO.getReportingStation(),Location.ARP, false);
			if (actionForm.getDiscrepancyDate() != null
					&& actionForm.getDiscrepancyDate().trim().length() > 0) {
				uldDiscrepancyVO.setDiscrepencyDate(discDate.setDate(actionForm.getDiscrepancyDate()));
			}
			if (actionForm.getRemarks() != null
					&& actionForm.getRemarks().trim().length() > 0) {
				uldDiscrepancyVO.setRemarks(actionForm.getRemarks()
						.toUpperCase());
			}
			if(actionForm.getDefaultComboValue() != null && 
					actionForm.getDefaultComboValue().trim().length() > 0){
				uldDiscrepancyVO.setFacilityType(actionForm.getDefaultComboValue()
						.toUpperCase());
			}
			if(actionForm.getLocation() != null && 
					actionForm.getLocation().trim().length() > 0){
				uldDiscrepancyVO.setLocation(actionForm.getLocation()
						.toUpperCase());
			}
			uldDiscrepancyVO.setLastUpdatedUser(logonAttributes.getUserId());
			if (uldDiscrepancyVO.getOperationFlag() == null) {
				uldDiscrepancyVO
						.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			}
			log.log(Log.FINE,
					"---uldDiscrepancyVO inside NAvigateCommand-------->>",
					uldDiscrepancyVO);
		}
		log.exiting("NavigateCommand", "updateULDDiscrepancyVO");
		return uldDiscrepancyVO;
	}
	
	private Collection<ErrorVO> validateDiscrepancyDate(MaintainUldDiscrepanciesForm form,
			String companyCode) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		LocalDate localDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
		LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
		log.log(Log.FINE, "\n\n\n\n currentdate", currentdate);
		log.log(Log.FINE, "\n\n\n\n disdate", localDate.setDate(form.getDiscrepancyDate()));
		if(currentdate.isLesserThan(localDate.setDate(form.getDiscrepancyDate()))){
    		errors.add(new ErrorVO(
    			"uld.defaults.listulddiscrepancies.msg.err.discrepancydategreaterthancurrent"));
    	}
		return errors;
	}
}
