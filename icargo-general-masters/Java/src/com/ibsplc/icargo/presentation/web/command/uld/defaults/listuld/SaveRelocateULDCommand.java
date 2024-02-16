/*
 * SaveRelocateULDCommand.java Created on Jun 7, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.listuld;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.RelocateULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up of the RelocateULD popup screen
 * 
 * @author A-2052
 */
public class SaveRelocateULDCommand extends BaseCommand {
	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Relocate ULD");

	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String MODULE = "uld.defaults";
	private static final String SCREENID =
		"uld.defaults.listuld";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("SaveRelocateULDCommand", "execute");

		RelocateULDForm relocateULDForm = (RelocateULDForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		ListULDSession listULDSession = 
			(ListULDSession)getScreenSession(MODULE,SCREENID);
		//Collection<ULDListVO> ULDLsitVOs = listULDSession.getListDisplayPage();
		Collection<ULDVO> vos = new ArrayList<ULDVO>();
		String currentStation = null;
		String fromlocation = null;
		String uldNumber = null;
		String remarks = null;
		String locationType = relocateULDForm.getLocationType();
		String toLocation = relocateULDForm.getToLocation();
		if (relocateULDForm.getCurrentStation() != null
				&& relocateULDForm.getCurrentStation().trim().length() > 0) {
			currentStation = relocateULDForm.getCurrentStation().toUpperCase();
		}
		if (relocateULDForm.getFromLocation() != null
				&& relocateULDForm.getFromLocation().trim().length() > 0) {
			fromlocation = relocateULDForm.getFromLocation().toUpperCase();
		}
		if (relocateULDForm.getUldNumber() != null
				&& relocateULDForm.getUldNumber().trim().length() > 0) {
			uldNumber = relocateULDForm.getUldNumber().toUpperCase();
		}
		if (relocateULDForm.getRemarks() != null
				&& relocateULDForm.getRemarks().trim().length() > 0) {
			remarks = relocateULDForm.getRemarks().toUpperCase();
		}

		String[] strArray = uldNumber.split(",");

		for (int i = 0; i < strArray.length; i++) {
			ULDVO vo = new ULDVO();
			vo.setCompanyCode(logonAttributes.getCompanyCode());
			vo.setCurrentStation(currentStation);
			vo.setLocationType(locationType);
			vo.setFacilityType(locationType);
			vo.setLocation(toLocation);
			vo.setUldNumber(strArray[i]);
			vo.setRemarks(remarks);
			log.log(Log.FINE, "relocateULDForm.getFromScreen()",
					relocateULDForm.getFromScreen());
			if(("maintaindiscrepancy").equals(relocateULDForm.getFromScreen())){
				LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
				vo.setLastUpdateTime(currentDate);
				log.log(Log.FINE, "Current Date", currentDate.toDisplayDateOnlyFormat());
			}
			vo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			if(("U").equals(vo.getOperationalFlag())){
				if(listULDSession.getListDisplayPage()!= null && listULDSession.getListDisplayPage().size() > 0){
					for(ULDListVO uldListVO : listULDSession.getListDisplayPage()){
						log.log(Log.FINE, "uldListVO.getUldNumber()------->>",
								uldListVO.getUldNumber());
						log.log(Log.FINE, "vo.getUldNumber()-------------->>",
								vo.getUldNumber());
						if(uldListVO.getUldNumber().equalsIgnoreCase(vo.getUldNumber())){
							log.log(Log.FINE, "INSIDE LSTUPDTIM setting",
									uldListVO.getLastUpdateTime());
							if(("maintaindiscrepancy").equals(relocateULDForm.getFromScreen())){
								LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
								
								vo.setLastUpdateTime(currentDate);
								log.log(Log.FINE, "Current Date", currentDate.toDisplayDateOnlyFormat());
							}
							vo.setLastUpdateTime(uldListVO.getLastUpdateTime());							
						}
					}
				}
			}
			vos.add(vo);
		}
		// for checking the location with facility type
		boolean isValidFacilityCode = false;
		Collection<ULDAirportLocationVO> uldAirportLocationVOs = null;
		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			uldAirportLocationVOs = uldDefaultsDelegate.listULDAirportLocation(
					logonAttributes.getCompanyCode(), relocateULDForm
							.getCurrentStation().toUpperCase(), relocateULDForm
							.getLocationType().toUpperCase());
		} catch (BusinessDelegateException exception) {
			exception.getMessage();
			errors = handleDelegateException(exception);
		}

		for (ULDAirportLocationVO uldAirportLocationVO : uldAirportLocationVOs) {

			if (uldAirportLocationVO.getAirportCode() != null
					&& uldAirportLocationVO.getAirportCode().equals(
							relocateULDForm.getCurrentStation().toUpperCase())
					&& uldAirportLocationVO.getFacilityType() != null
					&& uldAirportLocationVO.getFacilityType().equals(
							relocateULDForm.getLocationType().toUpperCase())) {

				if (uldAirportLocationVO.getFacilityCode() != null
						&& uldAirportLocationVO.getFacilityCode().equals(
								relocateULDForm.getToLocation().toUpperCase())) {
					isValidFacilityCode = true;
					break;
				}
			}
		}

		if (isValidFacilityCode) {
			// code for finding whether uld is updated or not...
			try {

				delegate.setLocationForULD(vos);
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				errors = handleDelegateException(businessDelegateException);
				log.log(Log.FINE, "\n\n\nAfter Delegate Call");
			}
			if (errors != null && errors.size() > 0) {
				log.log(Log.FINE, "\n\n\nInside Errors");
				log.log(Log.FINE, "Errorss---------------->", errors.size());
				for(ErrorVO err:errors){
					log.log(Log.FINE, "Error type---->", err.getErrorCode());
				}
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_SUCCESS;
				return;
			}

		}

		else {

			ErrorVO error = new ErrorVO(
					"uld.defaults.relocate.msg.err.invalidfacilitycode");

			invocationContext.addError(error);
			invocationContext.target = SCREENLOAD_SUCCESS;
			return;
		}
		relocateULDForm.setCheckFlag("relocateUld");
		relocateULDForm.setSaveStatusPopup("completeSave");
		log.exiting("SaveRelocateULDCommand", "execute");
		invocationContext.target = SCREENLOAD_SUCCESS;
	}
}
