/*
 * OkCN51DetailsCommand.java Created on DEC 05, 2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn51;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN51Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN51Form;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3227 RENO K ABRAHAM
 *
 */
public class OkCN51DetailsCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");
	private static final String CLASS_NAME = "OkCN51DetailsCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn51";
	private static final String OK_SUCCESS="ok_success";
	private static final String OK_FAILURE="ok_failure";
	private static final String UNSAVED_DATA = "unsaveddata";
	
	/**
	 * Execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		CaptureCN51Session captureCN51Session = 
			(CaptureCN51Session) getScreenSession(MODULE_NAME, SCREEN_ID);
		CaptureCN51Form captureCN51Form=(CaptureCN51Form)invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		Collection<ErrorVO> errors = null;
		Page<AirlineCN51DetailsVO> mainCN51VOs = null ;
		ArrayList<AirlineCN51DetailsVO> selectedCN51VOs = new ArrayList<AirlineCN51DetailsVO>();
		ArrayList<AirlineCN51DetailsVO> selectedDummyCN51VOs = new ArrayList<AirlineCN51DetailsVO>();
		AirlineCN51DetailsVO currentCN51VO = null;
		AirlineCN51SummaryVO airlineCN51SummaryVO = new AirlineCN51SummaryVO();
		airlineCN51SummaryVO = captureCN51Session.getCn51Details();
		if(airlineCN51SummaryVO != null) {
			if(airlineCN51SummaryVO.getCn51DetailsPageVOs()!= null && 
					airlineCN51SummaryVO.getCn51DetailsPageVOs().size() > 0) {
				mainCN51VOs = airlineCN51SummaryVO.getCn51DetailsPageVOs();
			}else {
				mainCN51VOs = new Page<AirlineCN51DetailsVO>(
						new ArrayList<AirlineCN51DetailsVO>(), 0, 0, 0, 0, 0,false);
			}
		}
		selectedCN51VOs = captureCN51Session.getSelectedCn51DetailsVOs();
		if(selectedCN51VOs == null) {
			selectedCN51VOs = new ArrayList<AirlineCN51DetailsVO>();
		}
		currentCN51VO = captureCN51Session.getCurrentCn51Detail();
		int currentIndex = Integer.parseInt(captureCN51Form.getCurrentPageNum())-1;
		errors = validateCurrentCN51Detail(currentCN51VO);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			captureCN51Form.setDisplayPage(String.valueOf(currentIndex+1));
			captureCN51Form.setCurrentPageNum(String.valueOf(currentIndex+1));
			captureCN51Form.setTotalRecords(String.valueOf(selectedCN51VOs.size()-1));
			invocationContext.target = OK_FAILURE;
			return;
		}

		//Max Seq Id From selectedCN51VOs & mainCN51VOs
		int maxSeqId = 0;
		int selectedMaxSeqId = 0 ;
		int mainMaxSeqId = 0 ;
		if(selectedCN51VOs.size() > 0) {
			selectedMaxSeqId = selectedCN51VOs.get(0).getSeqIdentifier();
			for(AirlineCN51DetailsVO dummyVO : selectedCN51VOs) {
				int dummySeqId = dummyVO.getSeqIdentifier();
				if(dummySeqId > selectedMaxSeqId) {
					selectedMaxSeqId = dummySeqId;
				}
			}			
		}
		if(mainCN51VOs.size() > 0) {
			mainMaxSeqId = mainCN51VOs.get(0).getSeqIdentifier();
			for(AirlineCN51DetailsVO dummyVO : mainCN51VOs) {
				int dummySeqId = dummyVO.getSeqIdentifier();
				if(dummySeqId > mainMaxSeqId) {
					mainMaxSeqId = dummySeqId;
				}
			}			
		}
		if(selectedMaxSeqId > mainMaxSeqId) {
			maxSeqId = selectedMaxSeqId;
		}else {
			maxSeqId = mainMaxSeqId;
		}

		//Adding Exsisting current VO To Session
		if(currentCN51VO != null) {
			if(currentCN51VO.getSeqIdentifier() == 0 ) {
				currentCN51VO.setSeqIdentifier(maxSeqId+1);
			}			
			boolean alreadyExist = false;
			if(selectedCN51VOs != null && selectedCN51VOs.size() > 0) {
				for(AirlineCN51DetailsVO selectedCN51VO : selectedCN51VOs) {
					if(selectedCN51VO.getSeqIdentifier() == currentCN51VO.getSeqIdentifier()) {
						try {
							BeanHelper.copyProperties(selectedCN51VO, currentCN51VO);
						} catch (SystemException e) {
							ErrorVO error = e.getError();
							errors.add(error);
						}
						alreadyExist = true;
					}
				}
			}
			if(!alreadyExist){
				selectedDummyCN51VOs.add(currentCN51VO);
			}
			if(selectedDummyCN51VOs != null && selectedDummyCN51VOs.size() > 0) {
				selectedCN51VOs.addAll(selectedDummyCN51VOs);
			}
		}
		
//		if(currentCN51VO != null) {
//			if(selectedCN51VOs.size() > currentIndex ) {			
//				selectedCN51VOs.set(currentIndex,currentCN51VO);
//			}else {
//				currentCN51VO.setSeqIdentifier(maxSeqId+1);
//				selectedCN51VOs.add(currentCN51VO);
//			}
//		}

		//Adding Exsisting VOs To Main Session
		if(selectedCN51VOs != null && selectedCN51VOs.size() > 0) {
			if(mainCN51VOs != null && mainCN51VOs.size() > 0) {
				for(AirlineCN51DetailsVO selectedCN51VO : selectedCN51VOs) {
					for(AirlineCN51DetailsVO mainCN51VO : mainCN51VOs) {
						if(selectedCN51VO.getSeqIdentifier() == mainCN51VO.getSeqIdentifier()) {
							try {
								BeanHelper.copyProperties(mainCN51VO, selectedCN51VO);
							} catch (SystemException e) {
								ErrorVO error = e.getError();
								errors.add(error);
							}
							break;
						}else if(selectedCN51VO.getSeqIdentifier() > mainMaxSeqId){
							mainCN51VOs.add(selectedCN51VO);
							break;
						}
					}
				}
			}else {
				mainCN51VOs.addAll(selectedCN51VOs);
			}
		}
		captureCN51Session.getCn51Details().setCn51DetailsPageVOs(mainCN51VOs);
		captureCN51Session.setSelectedCn51DetailsVOs(selectedCN51VOs);
		log.log(Log.FINE, "currentCN51VO-", currentCN51VO);
		log.log(Log.FINE, "selectedCN51VOs.size()-", selectedCN51VOs.size());
		log.log(Log.FINE, "mainCN51VOs.size()-", mainCN51VOs.size());
		captureCN51Form.setPopUpCloseFlag("Y");
		captureCN51Form.setScreenFlg("unsaveddata");
		invocationContext.target=OK_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}

	/**
	 * This method is used for validating the Current AirlineCN51DetailsVO
	 * @param currentAirlineCN51DetailsVO
	 * @return
	 */
	private Collection<ErrorVO> validateCurrentCN51Detail(AirlineCN51DetailsVO currentAirlineCN51DetailsVO) {
		return new ArrayList<ErrorVO>();
	}
}
