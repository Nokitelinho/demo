/*
 * ListCommand.java Created on Aug 7, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.manualproration;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ManualProrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ManualProrationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Command class for listing
 *
 * Revision History
 *
 * Version Date Author Description
 *
 * 0.1 Aug 8, 2008 A-3229 Initial draft
 */
public class ListCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */

	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");

	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.manualproration";

	private static final String SCREENID_DSNPOPUP = "mailtracking.mra.defaults.dsnselectpopup";

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String BASE_CURRENCY = "shared.station.basecurrency";

	private static final String VIEWPRORATION ="viewproration";

	//	Flags
	private static final String AIRLINE="A";
	private static final String GPA="G";
	private static final String RETENTION="T";
	private static final String PAYABLE="P";
	private static final String RECEIVABLE="R";
	private static final String PAYFLAG_ONETIME = "mailtracking.mra.defaults.payflag";
	private static final String SECTORSTATUS_ONETIME = "mailtracking.mra.proration.sectorstatus";

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

		ManualProrationSession manualProrationSession = (ManualProrationSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);

		ManualProrationForm manualProrationForm = (ManualProrationForm) invocationContext.screenModel;


		DSNPopUpSession popUpSession = getScreenSession(MODULE_NAME,SCREENID_DSNPOPUP);

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();


		//Added for Unit Component
		UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
		manualProrationSession.setWeightRoundingVO(unitRoundingVO);		
		setUnitComponent(logonAttributes.getStationCode(),manualProrationSession);



		ProrationFilterVO prorationFilterVO = new ProrationFilterVO();
		log.log(Log.INFO, "FromScreen-------------->", manualProrationForm.getFromScreen());
		if(manualProrationForm.getFromScreen()!=null && VIEWPRORATION.equalsIgnoreCase(manualProrationForm.getFromScreen())){
			prorationFilterVO = manualProrationSession.getProrationFilterVO();
			log.log(Log.INFO, "prorationFilterVO-------------->",
					prorationFilterVO);
			if(prorationFilterVO!=null) {
				manualProrationForm.setDsn(prorationFilterVO.getDespatchSerialNumber());
				manualProrationForm.setDespatchSerialNumber(prorationFilterVO.getBillingBasis());
				manualProrationForm.setBillingBasis(prorationFilterVO.getBillingBasis());
				manualProrationForm.setConsigneeDocumentNumber(prorationFilterVO.getConsigneeDocumentNumber());
				manualProrationForm.setConsigneeSequenceNumber(String.valueOf(prorationFilterVO.getConsigneeSequenceNumber()));
				manualProrationForm.setPoaCode(prorationFilterVO.getPoaCode());
				manualProrationForm.setCompanyCode(prorationFilterVO.getCompanyCode());

				if(prorationFilterVO.getFlightDate()!=null){
					manualProrationForm.setFlightDate(prorationFilterVO.getFlightDate().toDisplayDateOnlyFormat());
				}else{
					manualProrationForm.setFlightDate("");
				}

				if(prorationFilterVO.getCarrierCode()!=null && prorationFilterVO.getCarrierCode().trim().length()>0){
					manualProrationForm.setFlightCarrierIdentifier(prorationFilterVO.getCarrierCode());
				}
				else{
					manualProrationForm.setFlightCarrierIdentifier("");
				}

				if(prorationFilterVO.getFlightNumber()!=null && prorationFilterVO.getFlightNumber().trim().length()>0){
					manualProrationForm.setFlightNumber(prorationFilterVO.getFlightNumber());
				}
				else{
					manualProrationForm.setFlightNumber("");
				}
			}
		}

		if(manualProrationForm.getFromScreen()!=null && "fromlistproexceptions".equals(manualProrationForm.getFromScreen())){
			prorationFilterVO = manualProrationSession.getProrationFilterVO();
			log.log(Log.INFO, "prorationFilterVO-------------->",
					prorationFilterVO);
			if(prorationFilterVO!=null) {
				manualProrationForm.setDsn(prorationFilterVO.getDespatchSerialNumber());
				manualProrationForm.setDespatchSerialNumber(prorationFilterVO.getBillingBasis());
				manualProrationForm.setBillingBasis(prorationFilterVO.getBillingBasis());
				manualProrationForm.setConsigneeDocumentNumber(prorationFilterVO.getConsigneeDocumentNumber());
				manualProrationForm.setConsigneeSequenceNumber(String.valueOf(prorationFilterVO.getConsigneeSequenceNumber()));
				manualProrationForm.setPoaCode(prorationFilterVO.getPoaCode());
				manualProrationForm.setCompanyCode(prorationFilterVO.getCompanyCode());

				if(prorationFilterVO.getFlightDate()!=null){
					manualProrationForm.setFlightDate(prorationFilterVO.getFlightDate().toDisplayDateOnlyFormat());
				}else{
					manualProrationForm.setFlightDate("");
				}

				if(prorationFilterVO.getCarrierCode()!=null && prorationFilterVO.getCarrierCode().trim().length()>0){
					manualProrationForm.setFlightCarrierIdentifier(prorationFilterVO.getCarrierCode());
				}
				else{
					manualProrationForm.setFlightCarrierIdentifier("");
				}

				if(prorationFilterVO.getFlightNumber()!=null && prorationFilterVO.getFlightNumber().trim().length()>0){
					manualProrationForm.setFlightNumber(prorationFilterVO.getFlightNumber());
				}
				else{
					manualProrationForm.setFlightNumber("");
				}
			}
			manualProrationForm.setCloseFlag("fromlistprorationexception");
		}
		else{
			DSNPopUpVO popUpVO = popUpSession.getSelectedDespatchDetails();

			log.log(Log.INFO, "inside list command popupvo ", popUpVO);
			if (popUpVO != null) {
				manualProrationForm.setDsn(popUpVO.getDsn());
				manualProrationForm.setDespatchSerialNumber(popUpVO.getBlgBasis());
				manualProrationForm.setBillingBasis(popUpVO.getBlgBasis());
				manualProrationForm.setConsigneeDocumentNumber(popUpVO
						.getCsgdocnum());
				manualProrationForm.setConsigneeSequenceNumber(String
						.valueOf(popUpVO.getCsgseqnum()));
				manualProrationForm.setPoaCode(popUpVO.getGpaCode());
				manualProrationForm.setCompanyCode(popUpVO.getCompanyCode());

				//creating filter vo
				prorationFilterVO = new ProrationFilterVO();
				prorationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
				prorationFilterVO.setDespatchSerialNumber(popUpVO.getBlgBasis());
				prorationFilterVO.setBillingBasis(popUpVO.getBlgBasis());
				prorationFilterVO.setConsigneeDocumentNumber(popUpVO.getCsgdocnum());
				prorationFilterVO.setConsigneeSequenceNumber(popUpVO.getCsgseqnum());
				prorationFilterVO.setPoaCode(popUpVO.getGpaCode());
			}

		}


		/*
		 * Validate for client errors. The method will check for mandatory fields
		 */
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		errors = validateForm(manualProrationForm);

		if (errors != null && errors.size() > 0) {
			manualProrationForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}

		// MONEY IMPL

		ArrayList<String> stationParameterCodes = new ArrayList<String>();
		HashMap<String, String> stationParameters = new HashMap<String, String>();
		stationParameterCodes.add(BASE_CURRENCY);

		try {
			stationParameters = (HashMap<String, String>) (new AreaDelegate()
			.findStationParametersByCode(logonAttributes
					.getCompanyCode(),
					logonAttributes.getStationCode(),
					stationParameterCodes));
			prorationFilterVO.setBaseCurrency(stationParameters
					.get(BASE_CURRENCY));

		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}

		String baseCurrency = stationParameters.get(BASE_CURRENCY);

		if (errors != null && errors.size() > 0) {
			manualProrationForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
		}			

		//For flight validation
//		FlightValidationVO flightValidationVO=new FlightValidationVO();
//		if(manualProrationForm.getFlightNumber()!=null && !manualProrationForm.getFlightNumber().equals("")) {

//		flightValidationVO=validateFlight(manualProrationForm,invocationContext,logonAttributes);

//		if(flightValidationVO!=null){

//		prorationFilterVO.setFlightNumber(manualProrationForm.getFlightNumber());

//		LocalDate flightdate = new LocalDate(logonAttributes
//		.getStationCode(), Location.STN, true);
//		flightdate = flightdate.setDate(manualProrationForm.getFlightDate());
//		prorationFilterVO.setFlightDate(flightdate);

//		prorationFilterVO.setFlightCarrierIdentifier(flightValidationVO.getFlightCarrierId());
//		prorationFilterVO.setFlightSeqNumber(flightValidationVO.getFlightSequenceNumber());

//		}else{
//		manualProrationForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
//		invocationContext.target = LIST_FAILURE;
//		return;

log.log(Log.FINE, "ProrationFilterVO", prorationFilterVO);
		Collection<ProrationDetailsVO> prorationDetails = null;

		try {
			MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
			prorationDetails = mailTrackingMRADelegate.listProrationDetails(prorationFilterVO);
			log.log(Log.FINE, "PRORATIONVOs from Server is----->",
					prorationDetails);

		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,
					"inside try...caught businessDelegateException");
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}

		//added by A-3229 For bug AirNZ 23617



		Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(logonAttributes.getCompanyCode());
		manualProrationSession.setOneTimeVOs((HashMap<String,Collection<OneTimeVO>>)oneTimeValues);
		Collection<ProrationDetailsVO> primaryDetailsVOs=new ArrayList<ProrationDetailsVO>();
		Collection<ProrationDetailsVO> secondaryDetailsVOs=new ArrayList<ProrationDetailsVO>();
		if(prorationDetails!=null){
			for(ProrationDetailsVO vo:prorationDetails){

				manualProrationForm.setPostalAuthorityCode(vo
						.getPostalAuthorityCode());
				manualProrationSession.setProrationDetailsVO(vo);

				if(AIRLINE.equals(vo.getGpaarlBillingFlag())){
					if(!(RETENTION.equals(vo.getPayableFlag()))){
						primaryDetailsVOs.add(vo);
					}

				}
				if(GPA.equals(vo.getGpaarlBillingFlag())){	
					if(!(RECEIVABLE.equals(vo.getPayableFlag()))){
						primaryDetailsVOs.add(vo);
					}

				}
				if(AIRLINE.equals(vo.getGpaarlBillingFlag())){
					if(RETENTION.equals(vo.getPayableFlag())){
						secondaryDetailsVOs.add(vo);
					}

				}
				if(GPA.equals(vo.getGpaarlBillingFlag())){
					if(RETENTION.equals(vo.getPayableFlag())){
						secondaryDetailsVOs.add(vo);
					}

				}
				if(("".equals(vo.getGpaarlBillingFlag()))||(vo.getGpaarlBillingFlag()==null) ){

					if(RETENTION.equals(vo.getPayableFlag())){
						secondaryDetailsVOs.add(vo);
						primaryDetailsVOs.add(vo);
					}

				}
				if(RETENTION.equals(vo.getPayableFlag())){
					manualProrationForm.setFlightCarrierIdentifier(vo.getCarrierCode());
					manualProrationForm.setFlightNumber(vo.getFlightNumber());
					manualProrationForm.setFlightDate(vo.getFlightDate().toDisplayDateOnlyFormat());
					prorationFilterVO.setCarrierCode(vo.getCarrierCode());
					prorationFilterVO.setFlightNumber(vo.getFlightNumber());
					prorationFilterVO.setFlightDate(vo.getFlightDate());
				}

			}


		}

		ArrayList<ProrationDetailsVO> priTrecs =new ArrayList<ProrationDetailsVO>();
		ArrayList<ProrationDetailsVO> priTrecsToremove =new ArrayList<ProrationDetailsVO>();
		ArrayList<ProrationDetailsVO> newProrationVOs =new ArrayList<ProrationDetailsVO>();


		Money totalInUsd = null;
		Money totalInBase = null;
		Money totalInSdr = null;
		Money totalInCtr = null;		

		try {
			totalInUsd = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
			totalInUsd.setAmount(0.0);
			totalInBase = CurrencyHelper.getMoney(baseCurrency);
			totalInBase.setAmount(0.0);
			totalInSdr=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
			totalInSdr.setAmount(0.0);
			totalInCtr=CurrencyHelper.getMoney(baseCurrency);
			totalInCtr.setAmount(0.0);

		} catch (CurrencyException e) {
			e.getMessage();
		}

		if(primaryDetailsVOs!=null && primaryDetailsVOs.size()>0){
			for (ProrationDetailsVO priDetailsVO : primaryDetailsVOs) {	

				if(RETENTION.equals(priDetailsVO.getPayableFlag())){							
					priTrecs.add(priDetailsVO);							
				}
				totalInUsd = totalInUsd.plusEquals(priDetailsVO
						.getProrationAmtInUsd());
				totalInBase = totalInBase.plusEquals(priDetailsVO
						.getProrationAmtInBaseCurr());
				totalInSdr=totalInSdr.plusEquals(priDetailsVO.getProrationAmtInSdr());
				totalInCtr=totalInCtr.plusEquals(priDetailsVO.getProratedAmtInCtrCur());
			}
			manualProrationForm.setTotalInUsdForPri(String
					.valueOf(totalInUsd.getAmount()));
			manualProrationForm.setTotalInBasForPri(String
					.valueOf(totalInBase.getAmount()));
			manualProrationForm.setTotalInSdrForPri(String.valueOf(totalInSdr.getAmount()));
			manualProrationForm.setTotalInCurForPri(String.valueOf(totalInCtr.getAmount()));
			manualProrationForm.setPrimaryWeight(String.valueOf(0));
		}




		Money totalTrecInUsd = null;
		Money totalTrecInBase = null;
		Money totalTrecInSdr = null;
		Money totalTrecInCtr = null;

		try {
			totalTrecInUsd = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
			totalTrecInUsd.setAmount(0.0);
			totalTrecInBase = CurrencyHelper.getMoney(baseCurrency);
			totalTrecInBase.setAmount(0.0);
			totalTrecInSdr=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
			totalTrecInSdr.setAmount(0.0);
			totalTrecInCtr=CurrencyHelper.getMoney(baseCurrency);
			totalTrecInCtr.setAmount(0.0);

		} catch (CurrencyException e) {
			e.getMessage();
		}

		/*
		 * To group  primary retention records for multi sector flight
		 */
		 String tOrgin ="";
		String tDestn ="";
		int profactor=0;
		int siz=0;
		if(priTrecs!=null && priTrecs.size() >1){
			tOrgin = priTrecs.get(0).getSectorFrom();
			tDestn = priTrecs.get(0).getSectorTo();
			profactor= priTrecs.get(0).getProrationFactor();
			newProrationVOs.add( priTrecs.get(0));
			siz=priTrecs.size()-1;
			for(int i =0;i<siz;i++){
				String secto =  priTrecs.get(i).getSectorTo();
				String nxtsecfrm = priTrecs.get(i+1).getSectorFrom();						
				if(secto.equals(nxtsecfrm)){
					tDestn = priTrecs.get(i+1).getSectorTo();
					profactor=profactor+priTrecs.get(i+1).getProrationFactor();
					priTrecsToremove.add(priTrecs.get(i));
					priTrecsToremove.add(priTrecs.get(i+1));

					totalTrecInUsd.setAmount((priTrecs.get(i).getProrationAmtInUsd().getAmount())+(priTrecs.get(i+1).getProrationAmtInUsd().getAmount()));
					totalTrecInBase.setAmount((priTrecs.get(i).getProrationAmtInBaseCurr().getAmount())+(priTrecs.get(i+1).getProrationAmtInBaseCurr().getAmount()));
					totalTrecInSdr.setAmount((priTrecs.get(i).getProrationAmtInSdr().getAmount())+(priTrecs.get(i+1).getProrationAmtInSdr().getAmount()));
					totalTrecInCtr.setAmount((priTrecs.get(i).getProratedAmtInCtrCur().getAmount())+(priTrecs.get(i+1).getProratedAmtInCtrCur().getAmount()));
				}	
			}
		}

		ProrationDetailsVO priProrationVO = null;

		if(priTrecsToremove!=null && priTrecsToremove.size()>0){

			primaryDetailsVOs.removeAll(priTrecsToremove);

			for(ProrationDetailsVO newProrationVO :newProrationVOs){

				priProrationVO =  new ProrationDetailsVO();						

				priProrationVO.setOperationFlag("N");						
				priProrationVO.setSectorFrom(tOrgin);
				priProrationVO.setSectorTo(tDestn);
				priProrationVO.setProrationFactor(profactor);
				priProrationVO.setPayableFlag("T");
				priProrationVO.setProrationPercentage(100);
				priProrationVO.setProratPercentage("100");
				priProrationVO.setNumberOfPieces(newProrationVO.getNumberOfPieces());
				priProrationVO.setWeight(newProrationVO.getWeight());
				priProrationVO.setProrationType(newProrationVO.getProrationType());
				priProrationVO.setCarrierCode(newProrationVO.getCarrierCode());
				priProrationVO.setProrationAmtInUsd(totalTrecInUsd);
				priProrationVO.setProrationAmtInBaseCurr(totalTrecInBase);
				priProrationVO.setProrationAmtInSdr(totalTrecInSdr);
				priProrationVO.setProratedAmtInCtrCur(totalTrecInCtr);
				priProrationVO.setSectorStatus(newProrationVO.getSectorStatus());
				priProrationVO.setGpaarlBillingFlag("A");
				primaryDetailsVOs.add(priProrationVO);
			}					
		}


		manualProrationSession.setPrimaryProrationVOs(primaryDetailsVOs);



		totalInUsd.setAmount(0.0);
		totalInBase.setAmount(0.0);
		totalInSdr.setAmount(0.0);
		totalInCtr.setAmount(0.0);

		if(secondaryDetailsVOs!=null && secondaryDetailsVOs.size()>0){
			for (ProrationDetailsVO secDetailsVO : secondaryDetailsVOs) {				


				totalInUsd = totalInUsd.plusEquals(secDetailsVO
						.getProrationAmtInUsd());
				totalInBase = totalInBase.plusEquals(secDetailsVO
						.getProrationAmtInBaseCurr());
				totalInSdr=totalInSdr.plusEquals(secDetailsVO.getProrationAmtInSdr());
				totalInCtr=totalInCtr.plusEquals(secDetailsVO.getProratedAmtInCtrCur());
			}
			manualProrationForm.setTotalInUsdForSec(String
					.valueOf(totalInUsd.getAmount()));
			manualProrationForm.setTotalInBasForSec(String
					.valueOf(totalInBase.getAmount()));
			manualProrationForm.setTotalInSdrForSec(String.valueOf(totalInSdr.getAmount()));
			manualProrationForm.setTotalInCurForSec(String.valueOf(totalInCtr.getAmount()));
		}




		manualProrationSession.setSecondaryProrationVOs(secondaryDetailsVOs);




		//added by A-3229 For bug AirNZ 23617 ends

		manualProrationSession.setProrationFilterVO(prorationFilterVO);
		manualProrationSession.setProrationDetailVOs(prorationDetails);
		manualProrationSession.setBaseCurrency(baseCurrency);		


		if (prorationDetails == null || prorationDetails.size() == 0) {
			log.log(Log.FINE, "!!!inside resultList== null");
			ErrorVO errorVO = new ErrorVO(
			"mailtracking.mra.defaults.manualproration.msg.err.noprorationdetails");
			errorVO.setErrorDisplayType(ERROR);
			errors.add(errorVO);
			manualProrationSession.removeProrationDetailVOs();
			manualProrationForm
			.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);

		}

		if (errors != null && errors.size() > 0) {
			log.log(Log.FINE, "!!!inside errors!= null");
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
		} else {
			log.log(Log.FINE, "!!!inside resultList!= null");

			manualProrationForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
			invocationContext.target = LIST_SUCCESS;

		}

		log.exiting(CLASS_NAME, "execute");
	}

	public Collection<ErrorVO> validateForm(
			ManualProrationForm manualProrationForm) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;

		if(manualProrationForm.getFlightDate()!=null && manualProrationForm.getFlightDate().length()>0){
			if("".equals(manualProrationForm.getFlightCarrierIdentifier()) ||"".equals(manualProrationForm.getFlightNumber())){
				errors = new ArrayList<ErrorVO>();
				ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.manualproration.msg.err.enterflightdetails");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
			}
		}

		if(manualProrationForm.getFlightCarrierIdentifier()!=null && manualProrationForm.getFlightCarrierIdentifier().length()>0 &&
				manualProrationForm.getFlightNumber()!=null && 	manualProrationForm.getFlightNumber().length()>0 &&
				(((manualProrationForm.getFlightDate()==null)) || (("".equals(manualProrationForm.getFlightDate().trim()))))){
			errors = new ArrayList<ErrorVO>();
			ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.manualproration.msg.err.enterflightdate");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
		}

		if(manualProrationForm.getFlightCarrierIdentifier()!=null && manualProrationForm.getFlightCarrierIdentifier().length()>0 &&
				(manualProrationForm.getFlightNumber()==null ||"".equals(manualProrationForm.getFlightNumber()))) {
			errors = new ArrayList<ErrorVO>();
			ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.manualproration.msg.err.enterflightno");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
		}

		if(manualProrationForm.getFlightNumber()!=null && manualProrationForm.getFlightNumber().length()>0 &&
				(manualProrationForm.getFlightCarrierIdentifier()==null ||"".equals(manualProrationForm.getFlightCarrierIdentifier()))) {
			errors = new ArrayList<ErrorVO>();
			ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.manualproration.msg.err.entercaridr");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
		}


		return errors;
	}

	/**
	 *
	 * @param form
	 * @param invocationContext
	 * @param logonAttributes
	 * @return
	 */
	private FlightValidationVO validateFlight(
			ManualProrationForm form,	InvocationContext invocationContext,
			LogonAttributes logonAttributes) {
		log.entering("ListMCommand", "validateFlight");
		Collection<ErrorVO> errors = null;
		FlightValidationVO flightValidationVO = null;
		MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();

		/*
		 * Validate the carrier code and obtain the carrierId
		 */
		int carrierId = validateCarrierCode(form, invocationContext,
				logonAttributes);
		if(invocationContext.getErrors() != null &&
				invocationContext.getErrors().size() > 0) {
			log.log(Log.FINE, "\n\n--- Invalid Carrier Code ---\n\n");
			return flightValidationVO;
		}
		/*
		 * Populate the flightFilterVO
		 */
		FlightFilterVO flightFilterVO =	populateFlightFilterVO(form,
				logonAttributes);
		flightFilterVO.setFlightCarrierId(carrierId);
		log.log(Log.INFO, "flightFilterVO ---> ", flightFilterVO);
		Collection<FlightValidationVO> flightValidationVOs = null;
		try {
			flightValidationVOs =
				delegate.validateFlight(flightFilterVO);
		} catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		/*
		 * If the flight validationVO is null
		 */
		if(errors == null && flightValidationVOs.size() == 0){
			StringBuffer flight = new StringBuffer();
			flight.append(form.getFlightCarrierIdentifier().toUpperCase())
			.append(form.getFlightNumber().toUpperCase());

			invocationContext.addError(new ErrorVO(
					"mailtracking.mra.defaults.manualproration.msg.err.flightinvalid",
					new String[]{flight.toString(),
							form.getFlightDate()}));


			return flightValidationVO;
		}
		/*
		 * If no error and flightValidationVOs is not null
		 */
		if(flightValidationVOs != null && flightValidationVOs.size() > 0){

			flightValidationVO = (
					(ArrayList<FlightValidationVO>)flightValidationVOs).get(0);
		}

		log.exiting("ListCommand", "validateFlight");
		return flightValidationVO;

	}




	/**
	 *
	 * @param form
	 * @param logonAttributes
	 * @return
	 */
	private FlightFilterVO populateFlightFilterVO(
			ManualProrationForm form,
			LogonAttributes logonAttributes){
		log.entering("ListCommand", "populateFlightFilterVO");

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setCarrierCode(form.getFlightCarrierIdentifier().toUpperCase());
		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setFlightNumber(form.getFlightNumber().toUpperCase());

		LocalDate flightDate = new LocalDate(
				logonAttributes.getAirportCode(),Location.ARP,false);
		flightFilterVO.setFlightDate(
				flightDate.setDate(form.getFlightDate()));

		flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);

		log.exiting("ListCommand", "populateFlightFilterVO");
		return flightFilterVO;
	}


	/**
	 *
	 * @param form
	 * @param invocationContext
	 * @param logonAttributes
	 * @return
	 */
	private int validateCarrierCode(ManualProrationForm form,
			InvocationContext invocationContext,
			LogonAttributes logonAttributes) {
		log.entering("ListCommand", "validateCarrierCode");
		int carrierId = 0;
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();

		try {
			airlineValidationVO =
				airlineDelegate.validateAlphaCode(
						logonAttributes.getCompanyCode(), form.getFlightCarrierIdentifier().toUpperCase());
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		log.log(Log.FINE, "airlineValidationVO ---> ", airlineValidationVO);
		if(airlineValidationVO == null) {

			invocationContext.addError(
					new ErrorVO("mailtracking.mra.defaults.manualproration.msg.err.invalidcarriercode",
							new String[]{form.getFlightCarrierIdentifier().toUpperCase()}));
		} else {
			carrierId = airlineValidationVO.getAirlineIdentifier();
		}
		log.log(Log.FINE, "carrierId ---> ", carrierId);
		log.exiting("ListCommand", "validateCarrierCode");
		return carrierId;
	}
	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(String companyCode) {
		log.entering(CLASS_NAME,"fetchOneTimeDetails");
		Map<String, Collection<OneTimeVO>> hashMap =new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList=new ArrayList<String>();
		oneTimeList.add(PAYFLAG_ONETIME);
		oneTimeList.add(SECTORSTATUS_ONETIME);

		SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException e) {			
			handleDelegateException(e);
		}
		log.exiting(CLASS_NAME,"fetchOneTimeDetails");
		return hashMap;
	}

	/**
	 * A-3251
	 * @param stationCode
	 * @param mailAcceptanceSession
	 * @return 
	 */
	private void setUnitComponent(String stationCode,
			ManualProrationSession manualProrationSession){
		UnitRoundingVO unitRoundingVO = null;
		try{
			log.log(Log.FINE, "station code is ----------->>", stationCode);
			unitRoundingVO = UnitFormatter.getStationDefaultUnit(
					stationCode, UnitConstants.WEIGHT);			
			log.log(Log.FINE, "unit vo for wt--in session---", unitRoundingVO);
			manualProrationSession.setWeightRoundingVO(unitRoundingVO);	
		}catch(UnitException unitException) {
			unitException.getErrorCode();
		}

	}

}
