/*
 * MandatoryCheckCommand.java Created on Aug 19, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewflightsectorrevenue;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.FlightSectorRevenueFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewFlightSectorRevenueSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ViewMailFlightSectorRevenueForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3429
 * 
 */
public class MandatoryCheckCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA Defaults");

	private static final String CLASS_NAME = "MandatoryCheckCommand";

	private static final String CARRIERCODE_MANDATORY = "mra.defaults.msg.err.carriercodemandatory";

	private static final String FLIGHTNUMBER_MANDATORY = "mra.defaults.msg.err.flightnumbermandatory";

	private static final String FLIGHTDATE_MANDATORY = "mra.defaults.msg.err.flightdatemandatory";
	
	private static final String SCREEN_STATUS = "listproration";
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.viewflightsectorrevenue";
	
	private static final String FROM_ACCOUNTING = "listaccontingentry";
	
	private static final String FROM_POSTINGDTLS = "Posting_Details";

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		ViewMailFlightSectorRevenueForm form = (ViewMailFlightSectorRevenueForm) invocationContext.screenModel;
		ViewFlightSectorRevenueSession session = (ViewFlightSectorRevenueSession) getScreenSession(
				MODULE_NAME, SCREENID);
		FlightSectorRevenueFilterVO filterVO = new FlightSectorRevenueFilterVO();
		if(FROM_ACCOUNTING.equals(session.getListStatus())){
			filterVO=session.getSectorDetailsFilterVO();
			if(filterVO!=null){
				form.setCarrierCode(filterVO.getFlightCarrierCode());
				form.setFlightNo(filterVO.getFlightNumber());
				form.setFlightDate(filterVO.getFlightDate().toDisplayDateOnlyFormat());
			}
		}
		if(FROM_POSTINGDTLS.equals(session.getListStatus()) ){
			filterVO=session.getRevenueDetailsFilterVO();
			if(filterVO!=null){
				form.setCarrierCode(filterVO.getFlightCarrierCode().trim());
				form.setFlightNo(filterVO.getFlightNumber().trim());
				form.setFlightDate(filterVO.getFlightDate().toDisplayDateOnlyFormat());
			}
		}
		log.log(Log.INFO, "Carrier Code from form------>", form.getCarrierCode());
		if (form.getCarrierCode() == null
				|| form.getCarrierCode().trim().length() == 0) {
			log.log(1, "error present");
			ErrorVO errorVO = new ErrorVO(CARRIERCODE_MANDATORY);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(errorVO);
		}
		log.log(Log.INFO, "flight number from form----->", form.getFlightNo());
		if(SCREEN_STATUS.equals(session.getListStatus())){
			form.setFlightNo(session.getSectorDetailsFilterVO().getFlightNumber());
		}else{
		
		if (form.getFlightNo() == null
				|| form.getFlightNo().trim().length() == 0) {
			log.log(1, "error present");
			ErrorVO errorVO = new ErrorVO(FLIGHTNUMBER_MANDATORY);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(errorVO);
		}
		}
		log.log(Log.INFO, "flight date from form------>", form.getFlightDate());
		if (form.getFlightDate() == null
				|| form.getFlightDate().trim().length() == 0) {
			log.log(1, "error present");
			ErrorVO errorVO = new ErrorVO(FLIGHTDATE_MANDATORY);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(errorVO);
		}
		log.exiting(CLASS_NAME, "execute");
		return;
	}
}
