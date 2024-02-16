/*
 * ListCommand.java Created on Aug 19, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewflightsectorrevenue;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.FlightSectorRevenueFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SectorRevenueDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewFlightSectorRevenueSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ViewMailFlightSectorRevenueForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3429
 * 
 */
public class ListCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String ACTION_SUCCESS = "action_success";

	private static final String ACTION_FAILURE = "action_failure";

	private static final String LIST_SUCCESS = "list_success";

	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.viewflightsectorrevenue";

	private static final String SCREENID_DUPLICATE_FLIGHTS = "flight.operation.duplicateflight";

	private static final String NO_RESULTS_FOUND = "mra.defaults.msg.err.noresultsfound";
	
	private static final String PAYFLAG = "T";
	
	private static final String CLOSED = "CLOSED";

	private static final String OPEN = "OPEN";

	private static final String ACCOUNTED = "ACCOUNTED";

	private static final String PARTIALLY_ACCOUNTED = "PARTIALLY ACCOUNTED";
	private static final String CURRENCY = "NZD";
	/**
	 * target action
	 */
	private static final String SCREEN_STATUS = "listproration";
	
	private static final String FROM_ACCOUNTING = "listaccontingentry";
	
	private static final String FROM_POSTINGDTLS = "Posting_Details";

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");

		ViewFlightSectorRevenueSession session = (ViewFlightSectorRevenueSession) getScreenSession(
				MODULE_NAME, SCREENID);
		ViewMailFlightSectorRevenueForm form = (ViewMailFlightSectorRevenueForm) invocationContext.screenModel;
		double totalGrossWeight = 0.0D;
		double totalWeightCharge = 0.0D;
		double totalNetRevenue = 0.0D;
		boolean flag = true;
		log.log(Log.FINE, "liststaus==>>", session.getListStatus());
		if (invocationContext.getErrors() != null
				&& invocationContext.getErrors().size() > 0) {
			log.log(Log.INFO, "errors-------->", invocationContext.getErrors());
			if(FROM_POSTINGDTLS.equals(session.getListStatus())){
				form.setFromScreen(FROM_POSTINGDTLS);
			}
			session.setSectorDetails(null);
			session.setFlightSectorRevenueDetails(null);
			invocationContext.target = ACTION_FAILURE;
			return;
		} else if ("Y".equals(form.getDuplicateFlightFlag())) {
			log.log(Log.INFO, "duplicateFlightFlag---->", form.getDuplicateFlightFlag());
			log.log(1, "duplicateflights exists......");
			invocationContext.target = ACTION_FAILURE;
			return;
		} else {
			log.log(Log.INFO, "duplicateFlightFlag----->", form.getDuplicateFlightFlag());
			FlightValidationVO flightValidationVO = session.getFlightDetails();
			log.log(Log.INFO,
					"flightValidationVO in ListSegment command class---->",
					flightValidationVO);
			ErrorVO error = null;
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ArrayList<SectorRevenueDetailsVO> sectorDetailsVOs = new ArrayList<SectorRevenueDetailsVO>();
			Collection<SectorRevenueDetailsVO> revenueDetailsVOs = new ArrayList<SectorRevenueDetailsVO>();
			FlightSectorRevenueFilterVO filterVO = new FlightSectorRevenueFilterVO();
			FlightSectorRevenueFilterVO revenueFilterVO = new FlightSectorRevenueFilterVO();
			log.log(Log.INFO, "Flight Number from form------>", form.getFlightNo());
			log.log(Log.INFO, "Flight Date from form------->", form.getFlightDate());
			log.log(Log.INFO, "listr status==>>>", session.getListStatus());
			if(FROM_POSTINGDTLS.equals(session.getListStatus())){
				filterVO.setFlightCarrierCode(form.getCarrierCode());
				filterVO.setFlightNumber(form.getFlightNo());

				LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				flightDate.setDate(form.getFlightDate());
				filterVO.setFlightDate(flightDate);

				filterVO
						.setFlightCarrierId(flightValidationVO.getFlightCarrierId());
				filterVO.setFlightSequenceNumber((int) flightValidationVO
						.getFlightSequenceNumber());
				filterVO.setCompanyCode(flightValidationVO.getCompanyCode());
				session.setSectorDetailsFilterVO(filterVO);
				if(session.getRevenueDetailsFilterVO()!=null){
					session.getRevenueDetailsFilterVO().setPayFlag(PAYFLAG);
					session.getRevenueDetailsFilterVO().setFlightCarrierId(flightValidationVO
							.getFlightCarrierId());
					session.getRevenueDetailsFilterVO().setFlightSequenceNumber((int) flightValidationVO
							.getFlightSequenceNumber());
					LocalDate flightDte = new LocalDate(LocalDate.NO_STATION,
							Location.NONE, false);
					flightDte.setDate(session.getRevenueDetailsFilterVO().getFlightDate().toDisplayDateOnlyFormat());
					//filterVO.setFlightDate(flightDte);
					/*LocalDate flightDte = new LocalDate(session.getRevenueDetailsFilterVO().getFlightDate()
							,false);*/
					session.getRevenueDetailsFilterVO().setFlightDate(new LocalDate
							(LocalDate.NO_STATION,Location.NONE,false).setDate( session.getRevenueDetailsFilterVO().getFlightDate().toDisplayDateOnlyFormat()));
					
										
				}
				form.setFromScreen(FROM_POSTINGDTLS);
				form.setSectorCtrlFlg("Y");
			}
			log.log(Log.INFO, "listr status==>>>", session.getListStatus());
			log.log(Log.FINE, "filter Vo==>>>", session.getRevenueDetailsFilterVO());
			if(SCREEN_STATUS.equals(session.getListStatus())||FROM_ACCOUNTING.equals(session.getListStatus()) 
					|| FROM_POSTINGDTLS.equals(session.getListStatus())){
				filterVO=session.getSectorDetailsFilterVO();
			}else{
			
			filterVO.setFlightCarrierCode(form.getCarrierCode());
			filterVO.setFlightNumber(form.getFlightNo());

			LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			flightDate.setDate(form.getFlightDate());
			filterVO.setFlightDate(flightDate);

			filterVO
					.setFlightCarrierId(flightValidationVO.getFlightCarrierId());
			filterVO.setFlightSequenceNumber((int) flightValidationVO
					.getFlightSequenceNumber());
			filterVO.setCompanyCode(flightValidationVO.getCompanyCode());
			session.setSectorDetailsFilterVO(filterVO);
			}
			try {
				sectorDetailsVOs =(ArrayList<SectorRevenueDetailsVO>) new MailTrackingMRADelegate()
						.findSectorDetails(filterVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);

			}

			session.setSectorDetails(sectorDetailsVOs);
			log.log(Log.INFO, "SectorDetailsVOs from session-------->", session.getSectorDetails());
			if (sectorDetailsVOs == null) {
				error = new ErrorVO(NO_RESULTS_FOUND);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			
			if (errors != null && errors.size() > 0) {
				log.log(Log.FINE, "!!!inside errors!= null");
				invocationContext.addAllError(errors);
				invocationContext.target = ACTION_FAILURE;
				return;
			}
			log.log(Log.INFO, "listr status==>>>", session.getListStatus());
			if(sectorDetailsVOs!=null && sectorDetailsVOs.size()>0){
				if(SCREEN_STATUS.equals(session.getListStatus())||FROM_ACCOUNTING.equals(session.getListStatus())
						|| FROM_POSTINGDTLS.equals(session.getListStatus())){
					revenueFilterVO=session.getRevenueDetailsFilterVO();
					log.log(Log.FINE, "FROM PRORATION SCREEN");
					session.setListStatus("");
				}else{
				SectorRevenueDetailsVO sectorRevenueDetailsVo=
					sectorDetailsVOs.get(0);
				revenueFilterVO.setFlightCarrierCode(form.getCarrierCode());
				revenueFilterVO.setFlightNumber(form.getFlightNo());
				log.log(Log.FINE, "sectorRevenueDetailsVo.getSegmentOrigin()",
						sectorRevenueDetailsVo.getSegmentOrigin());
				log.log(Log.FINE,
						"sectorRevenueDetailsVo.getSegmentDestination()",
						sectorRevenueDetailsVo.getSegmentDestination());
				revenueFilterVO.setSegmentOrigin(sectorRevenueDetailsVo.getSegmentOrigin());
				revenueFilterVO.setSegmentDestination(sectorRevenueDetailsVo.getSegmentDestination());
				revenueFilterVO.setPayFlag(PAYFLAG);
				LocalDate fltDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				fltDate.setDate(form.getFlightDate());
				revenueFilterVO.setFlightDate(fltDate);

				revenueFilterVO.setFlightCarrierId(flightValidationVO
						.getFlightCarrierId());
				revenueFilterVO.setFlightSequenceNumber((int) flightValidationVO
						.getFlightSequenceNumber());
				revenueFilterVO.setCompanyCode(flightValidationVO.getCompanyCode());
				session.setRevenueDetailsFilterVO(revenueFilterVO);
				}
				log.log(Log.INFO, "revenueFilterVO=====>>>", revenueFilterVO);
				try {
					revenueDetailsVOs = new MailTrackingMRADelegate()
							.findFlightRevenueForSector(revenueFilterVO);
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);

				}
			}
			 Money totWeightCharge=null;
			 Money totnetreven=null;
			 try {
				 totWeightCharge = CurrencyHelper.getMoney(CURRENCY);			 
				 totnetreven = CurrencyHelper.getMoney(CURRENCY);
			 } catch (CurrencyException e) {
					// TODO Auto-generated catch block
					e.getErrorCode();
				}
		
			if (revenueDetailsVOs != null && revenueDetailsVOs.size() > 0) {
				for (SectorRevenueDetailsVO detailsVO : revenueDetailsVOs) {
					totalGrossWeight += detailsVO.getGrossWeight();
					
						totalWeightCharge += detailsVO.getWeightChargeBase().getAmount();
						totalNetRevenue += detailsVO.getNetRevenue().getAmount();
						totWeightCharge.setAmount(totalWeightCharge);			 
						totnetreven.setAmount(totalNetRevenue);											
			}
				for (SectorRevenueDetailsVO detailsVO : revenueDetailsVOs) {
					log.log(Log.INFO, "detailsVO", detailsVO.getAccStatus());
					if (!("A".equals(detailsVO.getAccStatus()))) {
						flag = false;
						break;
					}
				}
				if (flag) {
					log.log(Log.INFO, "inside falg true===>");
					form.setFlightStatus(CLOSED);
					form.setFlightSectorStatus(ACCOUNTED);
				} else {
					log.log(Log.INFO, "inside falg false===>");
					form.setFlightStatus(OPEN);
					form.setFlightSectorStatus(PARTIALLY_ACCOUNTED);
				}
				log.log(Log.INFO, "weight charge base===>", totalWeightCharge);
				form.setTotGrossWeight(round(totalGrossWeight, 3));
				form.setTotWeightCharge(totWeightCharge.getAmount());
				form.setTotNetRevenue(totnetreven.getAmount());
				log.log(Log.INFO, "weight ccccccccharge", form.getTotWeightCharge());
				session.setFlightSectorRevenueDetails(revenueDetailsVOs);
			
		}else{
			
				error = new ErrorVO(NO_RESULTS_FOUND);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
		}
			
			if (errors != null && errors.size() > 0) {
				form.setListSegmentsFlag("Y");
				log.log(Log.FINE, "!!!inside errors!= null");
				invocationContext.addAllError(errors);
				invocationContext.target = ACTION_FAILURE;
				return;
			}
			if (sectorDetailsVOs.size() == 1) {
				invocationContext.target = LIST_SUCCESS;
				form.setListSegmentsFlag("Y");
				return;
			}

		form.setListSegmentsFlag("Y");
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	}
		/**
		 * Round a double value to a specified number of decimal places.
		 * 
		 * @Author A-3429
		 * @param val
		 *            the value to be rounded.
		 * @param places
		 *            the number of decimal places to round to.
		 * @return val rounded to places decimal places.
		 */
		private double round(double val, int places) {
			long factor = (long) Math.pow(10, places);
			val = val * factor;
			long tmp = Math.round(val);
			return (double) tmp / factor;
		}
}
