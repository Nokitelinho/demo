/*
 * AddBillingLineCommand.java Created on Feb 28, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingmatrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineDetailVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingLineForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2398
 * 
 */
public class AddBillingLineCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "BillingLineOKCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.maintainbillingmatrix";

	private static final String SCREEN_SUCCESS = "screenload_success";

	private static final String SCREEN_FAILED = "screen_failure";

	private static final String CONST_ORGCNT = "ORGCNT";

	private static final String CONST_ORGCTY = "ORGCTY";

	private static final String CONST_ORGREG = "ORGREG";

	private static final String CONST_DSTCNT = "DSTCNT";

	private static final String CONST_DSTCTY = "DSTCTY";

	private static final String CONST_DSTREG = "DSTREG";

	private static final String CONST_UPLCNT = "UPLCNT";

	private static final String CONST_UPLCTY = "UPLCTY";

	private static final String CONST_DISCNT = "DISCNT";

	private static final String CONST_DISCTY = "DISCTY";

	private static final String CONST_ULDGRP= "ULDGRP";

	private static final String CONST_CLASS = "CLS";

	private static final String CONST_SUBCLS = "SUBCLS";

	private static final String CONST_SUBCLS_GRP ="SUBCLSGRP";

	private static final String CONST_CAT = "CAT";

	private static final String CONST_FLTNUM = "FLTNUM";

	private static final String CONST_TRASFEREDBY = "TRFBY";

	private static final String  CONST_TRANSFEREDPA = "TRFPOA";

	private static final String  CONST_MAILCOMPANY = "MALCMPCOD";
	
	private static final String INCLUDE_FLAG = "N";

	private static final String EXCLUDE_FLAG = "Y";

	private static final String STATUS_NEW = "N";
	
	private static final String STA_ACT = "A";

	private static final String OPFLAG_INS = "I";

	private static final String OPFLAG_UPD = "U";

	private static final String ACN_ADD = "ADD";

	private static final String ACN_MODIFY = "MODIFY";

	private static final String IS_MODIFIED = "Y";

	private static final String BLGPTY_ARL = "A";

	private static final String BLGPTY_POA = "P";

	private static final String LESSTHAN_CURRENTDATE="mailtracking.mra.defaults.maintainbillingmatrix.invalidstartdate";

	private static final String INVALID_CURCODE = "mailtracking.mra.defaults.maintainbillingmatrix.invalidcurcode";

	private static final String DATE_RANGE_OUT_MTX_RANGE = "mailtracking.mra.defaults.maintainbillingmatrix.outofrange";

	private static final String REVEXP_NULL = "mailtracking.mra.defaults.maintainbillingmatrix.revexpnull";

	private static final String BLDSEC_NULL = "mailtracking.mra.defaults.maintainbillingmatrix.billedsecnull";

	private static final String BLDBASIS_NULL = "mailtracking.mra.defaults.maintainbillingmatrix.billedbasisnull";

	private static final String FROMDATE_NULL = "mailtracking.mra.defaults.maintainbillingmatrix.frmdatnull";

	private static final String TODATE_NULL = "mailtracking.mra.defaults.maintainbillingmatrix.todatnull";

	private static final String CURCODE_NULL = "mailtracking.mra.defaults.maintainbillingmatrix.curcodenull";

	private static final String RATE_NULL = "mailtracking.mra.defaults.maintainbillingmatrix.ratenull";

	private static final String INVALID_DATE_RANGE = "mailtracking.mra.defaults.maintainbillingmatrix.invaliddaterange";

	private static final String NO_PARAMETERS = "mailtracking.mra.defaults.maintainbillingmatrix.noparameters";

	private static final String REVENUE = "R";

	private static final String EXPENDITURE = "E";

	private static final String INVALID_POACODE = "mailtracking.mra.defaults.maintainbillingmatrix.err.invalidpoacode";

	private static final String INVALID_COMPANY = "mailtracking.mra.defaults.maintainbillingmatrix.err.invalidcompany";

	private static final String INVALID_ARL = "mailtracking.mra.defaults.maintainbillingmatrix.invalidairline";

	private static final String REV_FLAG = "R";

	private static final String EXP_FLAG = "E";

	private static final String BILLBY_MANDATORY = "mailtracking.mra.defaults.mandatory.billby";

	private static final String BILLTO_MANDATORY = "mailtracking.mra.defaults.mandatory.billto";

	private static final String BILLINGPARTY_MANDATORY = "mailtracking.mra.defaults.mandatory.billingparty";

	private static final String INVCATEGORY="mailtracking.mra.defaults.maintainbillingmatrix.invalidcategory";
	//Added as part of bug ICRD-129585 by A-5526 starts
	private static final String CHARGEHEAD_MANDATORY="mailtracking.mra.defaults.maintainbillingmatrix.chargeheadmandatory";
	private static final String SURCHARGE_RATE_MANDATORY="mailtracking.mra.defaults.maintainbillingmatrix.surchargeratemandatory";
	//Added as part of bug ICRD-129585 by A-5526 ends
	private static final String EMPTY_STRING="";

	private static final String FLAT_CHARGE = "FC";
	private static final String FLAT_RATE = "FR";
	private static final String WEIGHT_BREAK = "WB";
	private static final String USPS="US";
	private static final String CONST_AGENT = "AGT";//Added by a-7531 for icrd-224979
	//Added by A-7540
	private static final String CONST_ORGAIR="ORGARPCOD";
	private static final String CONST_DESAIR="DSTARPCOD";
	private static final String CONST_VIAPNT="VIAPNT";
	private static final String CONST_MALSRV="MALSRVLVL";
	private static final String CONST_PABUILT="POAFLG";
	private static final String CONST_UPLARP="UPLARPCOD";
	private static final String CONST_DISARP="DISARPCOD";
	private static final String CONST_FLNCAR="FLNCAR";


	/**
	 * *
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");

		BillingLineForm form = (BillingLineForm) invocationContext.screenModel;
		MaintainBillingMatrixSession session = (MaintainBillingMatrixSession) getScreenSession(
				MODULE_NAME, SCREENID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		BillingLineVO sessionVO = null;
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		// Obtain parameters(excluded & included) from popup form
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> errForm = new ArrayList<ErrorVO>();
		Collection<ErrorVO> errPar = new ArrayList<ErrorVO>();
		Collection<BillingLineParameterVO> parameters = populateParameters(
				companyCode, form, invocationContext, session);
		/*
		 * if(parameters != null && parameters.size()>0)
		 * errPar=(validateParameters(parameters));
		 */
		errForm = (validateForm(form, companyCode,session));

		if (errPar.size() > 0) {
			errors.addAll(errPar);
		}
		if (errForm.size() > 0) {
			errors.addAll(errForm);
		}
		BillingLineVO billingLineVO = new BillingLineVO();
		BillingLineParameterVO billingLineParameterVO = new BillingLineParameterVO();
		if (errors.size() == 0) {
			if ((parameters == null || parameters.size() == 0)) {
				ErrorVO err = new ErrorVO(NO_PARAMETERS);
				errors.add(err);
			}
		}

		if (errors != null && errors.size() > 0
				|| invocationContext.getErrors() != null
				&& invocationContext.getErrors().size() > 0) {
			form.setCanClose("N");
			if (form.getRevExp() != null) {
				form.setReFlag(form.getRevExp().toUpperCase());
			}
			invocationContext.addAllError(errors);
			invocationContext.target = SCREEN_FAILED;
			return;
		}
		billingLineVO.setCompanyCode(companyCode);
		/*if (form.getRate() != null && form.getRate().length() > 0) {
			billingLineVO.setApplicableRate(Double.parseDouble(form.getRate()
					.trim()));
		}*/
		if (form.getBillingBasis() != null
				&& form.getBillingBasis().length() > 0) {
			billingLineVO.setBillingBasis(form.getBillingBasis().trim()
					.toUpperCase());
		}
		if (form.getRevExp() != null && form.getRevExp().length() > 0) {
			billingLineVO.setRevenueExpenditureFlag(form.getRevExp()
					.toUpperCase());
		}
		log.log(Log.INFO, "********************", form.getRevExp());
		// If revenue
		if (REVENUE.equals(billingLineVO.getRevenueExpenditureFlag())) {
			if (form.getBillingParty() != null) {
				if (BLGPTY_ARL.equals(form.getBillingParty())) {
					AirlineValidationVO airlineValidationVo = null;
					try {
						AirlineDelegate delegate = new AirlineDelegate();
						airlineValidationVo = delegate.validateAlphaCode(
								companyCode, form.getBillTo().toUpperCase()
								.trim());
					} catch (BusinessDelegateException businessDelegateException) {
						handleDelegateException(businessDelegateException);
						ErrorVO err = new ErrorVO(INVALID_ARL);
						errors.add(err);
						log.log(Log.INFO, "Error", err.getErrorCode());
						invocationContext.addAllError(errors);
						invocationContext.target = SCREEN_FAILED;
						return;
					}
					billingLineVO
					.setAirlineCode(form.getBillTo().toUpperCase());
					billingLineVO.setAirlineIdentifier(airlineValidationVo
							.getAirlineIdentifier());
				} else if (BLGPTY_POA.equals(form.getBillingParty())) {
					try {
						PostalAdministrationVO paVO = new MailTrackingMRADelegate()
						.findPACode(companyCode, form.getBillTo()
								.trim().toUpperCase());
						if (paVO == null) {
							ErrorVO err = new ErrorVO(INVALID_POACODE);
							errors.add(err);
							log.log(Log.INFO, "Error", err.getErrorCode());
							invocationContext.addAllError(errors);
							invocationContext.target = SCREEN_FAILED;
							return;
						} else {
							billingLineVO.setPoaCode(form.getBillTo()
									.toUpperCase());
						}
					} catch (BusinessDelegateException businessDelegateException) {
						errors
						.addAll(handleDelegateException(businessDelegateException));
						invocationContext.addAllError(errors);
						invocationContext.target = SCREEN_FAILED;
						return;
					}
				}
			}
		}
		// If revenue
		else if (EXPENDITURE.equals(billingLineVO.getRevenueExpenditureFlag())) {
			AirlineValidationVO airlineValidationVo = null;
			try {
				AirlineDelegate delegate = new AirlineDelegate();
				airlineValidationVo = delegate.validateAlphaCode(companyCode,
						form.getBillBy().toUpperCase().trim());
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				ErrorVO err = new ErrorVO(INVALID_ARL);
				errors.add(err);
				log.log(Log.INFO, "Error", err.getErrorCode());
				invocationContext.addAllError(errors);
				invocationContext.target = SCREEN_FAILED;
				return;
			}
			billingLineVO.setAirlineCode(form.getBillBy().toUpperCase());
			billingLineVO.setAirlineIdentifier(airlineValidationVo
					.getAirlineIdentifier());
		}
		//Added for CRQ 12578 
		billingLineVO.setUnitCode(form.getUnitCode());
		if("Y".equals(form.getIsTaxIncludedInRateFlag())){
			billingLineVO.setTaxIncludedInRateFlag(true);	
		}
		else{
			billingLineVO.setTaxIncludedInRateFlag(false);	
		}
		if (form.getCurrency() != null && form.getCurrency().length() > 0) {
			billingLineVO.setCurrencyCode(form.getCurrency().trim()
					.toUpperCase());
		}
		billingLineVO.setBillingLineStatus(STATUS_NEW);
		if (form.getBilledSector() != null
				&& form.getBilledSector().length() > 0) {
			billingLineVO.setBillingSector(form.getBilledSector());
		}
		if (form.getBlgLineValidFrom() != null
				&& form.getBlgLineValidFrom().length() > 0) {
			LocalDate startDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			startDate.setDate(form.getBlgLineValidFrom().trim().toUpperCase());
			billingLineVO.setValidityStartDate(startDate);
		}
		if (form.getBlgLineValidTo() != null
				&& form.getBlgLineValidTo().length() > 0) {
			LocalDate endDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			endDate.setDate(form.getBlgLineValidTo().trim().toUpperCase());
			billingLineVO.setValidityEndDate(endDate);
		}
		// set the parameters read from form to billingLineVO...
		billingLineVO.setBillingLineParameters(parameters);
		//Setting the rate
		populateRateDetails(billingLineVO,session,form);
		
		
		
		Page<BillingLineVO> billingLineVOs = session.getBillingLineDetails();
		String matrixId="";
		LocalDate localDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true) ;
		ArrayList<BillingLineVO> tempList = new ArrayList<BillingLineVO>();
		if (billingLineVOs != null && billingLineVOs.size() > 0) {
			for (BillingLineVO lineVO : billingLineVOs) {
				matrixId=lineVO.getBillingMatrixId();
				if (!AbstractVO.OPERATION_FLAG_DELETE.equals(lineVO
						.getOperationFlag())) {
					tempList.add(lineVO);
				}
			}
			if (ACN_ADD.equals(form.getActionType())) {
				billingLineVO.setOperationFlag(OPFLAG_INS);
				billingLineVO.setBillingMatrixId(matrixId);
				billingLineVO.setLastUpdatedUser(logonAttributes.getUserName());//Added as part of ICRD-101506
				billingLineVO.setLastUpdatedTime(localDate);//Added as part of ICRD-101506
				log.log(Log.INFO, "ID ", matrixId);
				////log.log(Log.INFO,"Page Size"+session.getBillingLineDetails().getActualPageSize()+1);
				for(BillingLineVO billingLineVO1 :session.getBillingLineDetails()){
					billingLineVO.setBillingLineSequenceNumber(billingLineVO1.getBillingLineSequenceNumber()+1);
				}
				//billingLineVO.setBillingLineSequenceNumber(session.getBillingLineDetails().iterator().next().getBillingLineSequenceNumber()+1);
				session.getBillingLineDetails().add(billingLineVO);
				log.log(1,"Added for the first time");
				for (BillingLineVO billingLineVo : session
						.getBillingLineDetails()) {
					log.log(Log.FINE, billingLineVo.toString());
				}
			} else if (ACN_MODIFY.equals(form.getActionType())
					&& IS_MODIFIED.contains(form.getIsModified())) {
				int selectedIndex = Integer.parseInt(form.getSelectedIndex());
				sessionVO = tempList.get(selectedIndex);
				log.log(Log.FINE, "opFlag!!", sessionVO.getOperationFlag());
				billingLineVO.setBillingMatrixId(matrixId);
				if (!OPFLAG_INS.equals(sessionVO.getOperationFlag())){
					billingLineVO.setOperationFlag(OPFLAG_UPD);
				}
				else if(OPFLAG_INS.equals(sessionVO.getOperationFlag())){
					billingLineVO.setOperationFlag(OPFLAG_INS);
				}
				billingLineVO.setLastUpdatedUser(logonAttributes.getUserName());//Added as part of ICRD-101506
				billingLineVO.setLastUpdatedTime(localDate);//Added as part of ICRD-101506
				if (sessionVO != null) {
					log.log(Log.INFO, "The vo modified from session is--->",
							sessionVO);
					log.log(Log.INFO, "Selected Index is..", selectedIndex);
					billingLineVO.setBillingLineSequenceNumber(sessionVO
							.getBillingLineSequenceNumber());
					billingLineVOs.remove(sessionVO);
					log.log(Log.INFO, "The modified vo--->", billingLineVO);
					session.getBillingLineDetails().add(billingLineVO);
					log.log(Log.INFO,
							"The modified session collection is...------>>",
							session.getBillingLineDetails());
				}
			}
		} else if (billingLineVOs == null || billingLineVOs.size() == 0
				&& ACN_ADD.equals(form.getActionType())) {
			billingLineVOs = new Page<BillingLineVO>(
					new ArrayList<BillingLineVO>(), 1, 0, 1, 0, 0, 0, false);
			billingLineVO.setOperationFlag(OPFLAG_INS);
			billingLineVO.setBillingLineSequenceNumber(1);
			billingLineVO.setLastUpdatedUser(logonAttributes.getUserName());//Added as part of ICRD-101506
			billingLineVO.setLastUpdatedTime(localDate);//Added as part of ICRD-101506
			billingLineVOs.add(billingLineVO);
			session.setBillingLineDetails(billingLineVOs);
			log.log(1, "Added to the collection.....ADD action...");
			for (BillingLineVO billingLineVo : billingLineVOs) {
				log.log(Log.FINE, billingLineVo.toString());
			}
		}
		log
				.log(
						Log.FINE,
						"Billing line details page as from SESSIONvand new vo added to it..",
						billingLineVOs);
		log.log(Log.FINE, "Billing line details page as from SESSION---->>",
				session.getBillingLineDetails());
		log.exiting(CLASS_NAME, "AddBillingLine Command");
		if (invocationContext.getErrors() != null
				&& invocationContext.getErrors().size() > 0) {
			form.setCanClose("N");
			log.log(1, "Errors detected................");
			invocationContext.target = SCREEN_FAILED;
			return;
		} else {
			form.setCanClose("Y");
		}
		invocationContext.target = SCREEN_SUCCESS;
	}
  

	/**
	 * 
	 * @param companyCode
	 * @param form
	 * @param invocationContext
	 * @param session
	 * @return
	 */
	private Collection<BillingLineParameterVO> populateParameters(
			String companyCode, BillingLineForm form,
			InvocationContext invocationContext,
			MaintainBillingMatrixSession session) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String orgCountryInc = form.getOrgCntInc();

		String orgCityInc = form.getOrgCtyInc();

		String orgRegionInc = form.getOrgRegInc();

		String destCountryInc = form.getDesCntInc();

		String destRegionInc = form.getDesRegInc();

		String destCityInc = form.getDesCtyInc();

		String upliftCountryInc = form.getUplCntInc();

		String upliftCityInc = form.getUplCtyInc();

		String disCountryInc = form.getDisCntInc();

		String disCityInc = form.getDisCtyInc();

		String categoryInc = form.getCatInc();

		String uldTypeInc = form.getUldTypInc();

		String billingClassInc = form.getClassInc();

		String subClassInc = form.getSubClsInc();

		String flightNumInc = form.getCarrierCodeInc().concat(form.getFlightNumberInc());
		String transferedByInc=form.getTransferedByInc();

		String transferredPAInc=form.getTransferedPAInc();

		String mailSubClassInc=form.getSubClassInc();

		String agentCodeInc=form.getAgentCodeInc();//Added by a-7531 for icrd-224979

		//Added as part of ICRD-232319 by A-7540
		String orgAirportInc=form.getOrgAirportInc(); 
		
		String desAirportInc=form.getDesAirportInc();
		
		String viaPointInc=form.getViaPointInc();
		
	    String mailServiceInc=form.getMailServiceInc();
		
		String paBuilt=form.getPaBuilt(); 

		// Excluded parametrs

		String orgCountryExc = form.getOrgCntExc();

		String orgCityExc = form.getOrgCtyExc();

		String orgRegionExc = form.getOrgRegExc();

		String destCountryExc = form.getDesCntExc();

		String destRegionExc = form.getDesRegExc();

		String destCityExc = form.getDesCtyExc();

		String upliftCountryExc = form.getUplCntExc();

		String upliftCityExc = form.getUplCtyExc();

		String disCountryExc = form.getDisCntExc();

		String disCityExc = form.getDisCtyExc();

		String categoryExc = form.getCatExc();

		String uldTypeExc = form.getUldTypExc();

		String billingClassExc = form.getClassExc();

		String subClassExc = form.getSubClsExc();

		String flightNumExc = form.getCarriercodeExc().concat(form.getFlightNumberExc());

		String transferedByExc=form.getTransferedByExc();

		String transferredPAExc=form.getTransferedPAExc();

		String mailSubClassExc=form.getSubClassExc();

		String mailCompanyInc=form.getMailCompanyInc();
		
		String mailCompanyExc=form.getMailCompanyExc();
		
		String agentCodeExc=form.getAgentCodeExc();//Added by a-7531 for icrd-224979
		
		//Added by A-7540
         String orgAirportExc=form.getOrgAirportExc();
		
		String desAirportExc=form.getDesAirportExc();
		
		String viaPointExc=form.getViaPointExc();
	
		 String mailServiceExc=form.getMailServiceExc();
		 
		 String uplAirportInc=form.getUplArpInc();			
		 String disAirportInc=form.getDisArpInc();
		 String uplAirportExc=form.getUplArpExc();			
		 String disAirportExc=form.getDisArpExc();
		 
		 String flownCarrierInc=form.getFlownCarrierInc();					
		 String flownCarrierExc=form.getFlownCarrierExc();
		 
		 
		 
		Collection<BillingLineParameterVO> parameters = new ArrayList<BillingLineParameterVO>();
		String isTransferedBy="";
		String isTransferedPA="";
		if (orgCityInc != null && orgCityInc.length() > 0
				&& !("-").equals(orgCityInc)) {
			String[] bufferOne = orgCityInc.trim().toUpperCase().split(",");
			Collection<String> countryCodes = new ArrayList<String>();
			for (int i = 0; i < bufferOne.length; i++){
				countryCodes.add(bufferOne[i]);
			}
				
			Collection<ErrorVO> errs = validateCityCodes(companyCode,
					countryCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invalidorgcity"));
				log.log(Log.INFO, "Erros in country code", errs.size());
			} else {
				parameters.addAll(createParameters(bufferOne, CONST_ORGCTY,
						INCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "orgCityInc.........", bufferOne.length);
		}
		// System.out.println("in populate parameters...22sss22");
		if (orgCityExc != null && orgCityExc.length() > 0
				&& !("-").equals(orgCityExc)) {
			String[] bufferTwo = orgCityExc.trim().toUpperCase().split(",");
			Collection<String> countryCodes = new ArrayList<String>();
			for (int i = 0; i < bufferTwo.length; i++){
				countryCodes.add(bufferTwo[i]);
			}
				
			Collection<ErrorVO> errs = validateCityCodes(companyCode,
					countryCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invalidorgcity"));
				log.log(Log.INFO, "Erros in country code", errs.size());
			} else {
				parameters.addAll(createParameters(bufferTwo, CONST_ORGCTY,
						EXCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "orgCityExc.........", bufferTwo.length);
		}
		if (orgCountryInc != null && orgCountryInc.length() > 0
				&& !("-").equals(orgCountryInc)) {
			String[] buffer = orgCountryInc.trim().toUpperCase().split(",");
			Collection<String> countryCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				countryCodes.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateCountryCodes(companyCode,
					countryCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invalidorgcntry"));
				log.log(Log.INFO, "Erros in country code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_ORGCNT,
						INCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "orgCntImc.........", buffer.length);
		}
		if (orgCountryExc != null && orgCountryExc.length() > 0
				&& !("-").equals(orgCountryExc)) {
			String[] buffer = orgCountryExc.trim().toUpperCase().split(",");
			Collection<String> countryCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				countryCodes.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateCountryCodes(companyCode,
					countryCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invalidorgcntry"));

			} else {
				parameters.addAll(createParameters(buffer, CONST_ORGCNT,
						EXCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "orgCntExc.........", buffer.length);
		}
		if (orgRegionInc != null && orgRegionInc.length() > 0
				&& !("-").equals(orgRegionInc)) {
			String[] buffer = orgRegionInc.trim().toUpperCase().split(",");

			Collection<String> regCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				regCodes.add(buffer[i]);
			}
				
			ErrorVO errs = validateRegion(companyCode, regCodes);
			if (errs != null) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invalidorgregion"));
				log.log(1, "Erros in org reg code");
			} else{
				parameters.addAll(createParameters(buffer, CONST_ORGREG,
						INCLUDE_FLAG, session));
			}
				
			log.log(Log.FINE, "orgRegionInc.........", buffer.length);
		}
		if (orgRegionExc != null && orgRegionExc.length() > 0
				&& !("-").equals(orgRegionExc)) {
			String[] buffer = orgRegionExc.trim().toUpperCase().split(",");
			Collection<String> regCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				regCodes.add(buffer[i]);
			}
				
			ErrorVO errs = validateRegion(companyCode, regCodes);
			if (errs != null) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invalidorgregion"));
				log.log(1, "Erros in org reg code");
			} else {
				parameters.addAll(createParameters(buffer, CONST_ORGREG,
						EXCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "orgRegionExc.........", buffer.length);
		}
		if (destCityInc != null && destCityInc.length() > 0
				&& !("-").equals(destCityInc)) {
			String[] buffer = destCityInc.trim().toUpperCase().split(",");
			Collection<String> countryCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				countryCodes.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateCityCodes(companyCode,
					countryCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliddestcity"));
				log.log(Log.INFO, "Erros in country code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_DSTCTY,
						INCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "destCityInc.........", buffer.length);
		}
		if (destCityExc != null && destCityExc.length() > 0
				&& !("-").equals(destCityExc)) {
			String[] buffer = destCityExc.trim().toUpperCase().split(",");
			Collection<String> countryCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				countryCodes.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateCityCodes(companyCode,
					countryCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliddestcity"));
				log.log(Log.INFO, "Erros in country code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_DSTCTY,
						EXCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "destCityExc.........", buffer.length);
		}
		if (destCountryInc != null && destCountryInc.length() > 0
				&& !("-").equals(destCountryInc)) {
			String[] buffer = destCountryInc.trim().toUpperCase().split(",");
			Collection<String> countryCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				countryCodes.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateCountryCodes(companyCode,
					countryCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliddestcntry"));

			} else {
				parameters.addAll(createParameters(buffer, CONST_DSTCNT,
						INCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "destCountryInc.........", buffer.length);
		}
		if (destCountryExc != null && destCountryExc.length() > 0
				&& !("-").equals(destCountryExc)) {
			String[] buffer = destCountryExc.trim().toUpperCase().split(",");
			Collection<String> countryCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				countryCodes.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateCountryCodes(companyCode,
					countryCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliddestcntry"));

			} else {
				parameters.addAll(createParameters(buffer, CONST_DSTCNT,
						EXCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "destCountryExc.........", buffer.length);
		}
		if (destRegionInc != null && destRegionInc.length() > 0
				&& !("-").equals(destRegionInc)) {
			String[] buffer = destRegionInc.trim().toUpperCase().split(",");
			Collection<String> regCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				regCodes.add(buffer[i]);
			}
				
			ErrorVO errs = validateRegion(companyCode, regCodes);
			if (errs != null) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliddesregion"));
				log.log(Log.FINE, "error in des reg.........");
			} else {
				parameters.addAll(createParameters(buffer, CONST_DSTREG,
						INCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "destRegionInc.........", buffer.length);
		}
		if (destRegionExc != null && destRegionExc.length() > 0
				&& !("-").equals(destRegionExc)) {
			String[] buffer = destRegionExc.trim().toUpperCase().split(",");
			Collection<String> regCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				regCodes.add(buffer[i]);
			}
				
			ErrorVO errs = validateRegion(companyCode, regCodes);
			if (errs != null) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliddesregion"));
				log.log(Log.FINE, "error in des reg.........");
			} else {
				parameters.addAll(createParameters(buffer, CONST_DSTREG,
						EXCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "destRegionExc.........", buffer.length);
		}
		if (upliftCityInc != null && upliftCityInc.length() > 0
				&& !("-").equals(upliftCityInc)) {
			String[] buffer = upliftCityInc.trim().toUpperCase().split(",");
			Collection<String> countryCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				countryCodes.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateCityCodes(companyCode,
					countryCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliduplcity"));
				log.log(Log.INFO, "Erros in country code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_UPLCTY,
						INCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "upliftCityInc.........", buffer.length);
		}
		if (upliftCityExc != null && upliftCityExc.length() > 0
				&& !("-").equals(upliftCityExc)) {
			String[] buffer = upliftCityExc.trim().toUpperCase().split(",");
			Collection<String> countryCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				countryCodes.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateCityCodes(companyCode,
					countryCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliduplcity"));
				log.log(Log.INFO, "Erros in country code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_UPLCTY,
						EXCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "upliftCityExc.........", buffer.length);
		}
		//Added by a-7531 for icrd-224979 start
		if (agentCodeInc != null && agentCodeInc.length() > 0
				&& !("-").equals(agentCodeInc)) {
			String[] buffer = agentCodeInc.trim().toUpperCase().split(",");
			Collection<String> agentCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				agentCodes.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateAgentCodes(companyCode,
				agentCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invalidagent"));
				log.log(Log.INFO, "Erros in agent code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_AGENT,
						INCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "AgentcodeInc.........", buffer.length);
		}
		if (agentCodeExc != null && agentCodeExc.length() > 0
				&& !("-").equals(agentCodeExc)) {
			String[] buffer = agentCodeExc.trim().toUpperCase().split(",");
			Collection<String> agentCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				agentCodes.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateAgentCodes(companyCode,
					agentCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invalidagent"));
				log.log(Log.INFO, "Erros in agent code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_AGENT,
						EXCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "AgentcodeExc.........", buffer.length);
		}//Added by a-7531 for icrd-224979 ends
		
		//Added by A-7540 for ICRD-232319
		if (orgAirportInc != null && orgAirportInc.length() > 0
				&& !("-").equals(orgAirportInc)) {
			String[] buffer = orgAirportInc.trim().toUpperCase().split(",");
			Collection<String> orgAirport = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				orgAirport.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateAirportCodes(companyCode,
					orgAirport);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invalidorginairport"));//Modified by a-7871 for ICRD-282205
				log.log(Log.INFO, "Erros in airport code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_ORGAIR,
						INCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "OrgAirportInc.........", buffer.length);
		}
		if (orgAirportExc != null && orgAirportExc.length() > 0
				&& !("-").equals(orgAirportExc)) {
			String[] buffer = orgAirportExc.trim().toUpperCase().split(",");
			Collection<String> orgAirport = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				orgAirport.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateAirportCodes(companyCode,
					orgAirport);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invalidorginairport"));//Modified by a-7871 for ICRD-282205
				log.log(Log.INFO, "Erros in airport code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_ORGAIR,
						EXCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "OrgAirportExc.........", buffer.length);
		}
		if (desAirportInc != null && desAirportInc.length() > 0
				&& !("-").equals(desAirportInc)) {
			String[] buffer = desAirportInc.trim().toUpperCase().split(",");
			Collection<String> desAirport = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				desAirport.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateAirportCodes(companyCode,
					desAirport);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliddestairport"));//Modified by a-7871 for ICRD-282205
				log.log(Log.INFO, "Erros in airport code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_DESAIR,
						INCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "DesAirportInc.........", buffer.length);
		}
		if (desAirportExc != null && desAirportExc.length() > 0
				&& !("-").equals(desAirportExc)) {
			String[] buffer = desAirportExc.trim().toUpperCase().split(",");
			Collection<String> desAirport = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				desAirport.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateAirportCodes(companyCode,
					desAirport);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliddestairport"));//Modified by a-7871 for ICRD-282205
				log.log(Log.INFO, "Erros in airport code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_DESAIR,
						EXCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "DesAirportExc.........", buffer.length);
		}
		if (viaPointInc != null && viaPointInc.length() > 0
				&& !("-").equals(viaPointInc)) {
			String[] buffer = viaPointInc.trim().toUpperCase().split(",");
			Collection<String> viaPoint = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				viaPoint.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateAirportCodes(companyCode,
					viaPoint);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invalidviaairport"));//Modified by a-7871 for ICRD-282205
				log.log(Log.INFO, "Erros in airport code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_VIAPNT,
						INCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "ViaPointInc.........", buffer.length);
		}
		if (viaPointExc != null && viaPointExc.length() > 0
				&& !("-").equals(viaPointExc)) {
			String[] buffer = viaPointExc.trim().toUpperCase().split(",");
			Collection<String> viaPoint = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				viaPoint.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateAirportCodes(companyCode,
					viaPoint);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invalidviaairport"));//Modified by a-7871 for ICRD-282205
				log.log(Log.INFO, "Erros in airport code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_VIAPNT,
						EXCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "ViaPointExc.........", buffer.length);
		}
		if (paBuilt != null && paBuilt.length() > 0
				&& !("-").equals(paBuilt)) {
			String[] buffer = paBuilt.trim().toUpperCase().split(",");
			Collection<String> paBuilts = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				paBuilts.add(buffer[i]);
			}
			parameters.addAll(createParameters(buffer, CONST_PABUILT,
					INCLUDE_FLAG, session));
			log.log(Log.FINE, "PaBuilt.........", buffer.length);
		}
		
		if (upliftCountryInc != null && upliftCountryInc.length() > 0
				&& !("-").equals(upliftCountryInc)) {
			String[] buffer = upliftCountryInc.trim().toUpperCase().split(",");
			Collection<String> countryCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				countryCodes.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateCountryCodes(companyCode,
					countryCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliduplcntry"));

			} else {
				parameters.addAll(createParameters(buffer, CONST_UPLCNT,
						INCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "upliftCountryInc.........", buffer.length);
		}
		if (upliftCountryExc != null && upliftCountryExc.length() > 0
				&& !("-").equals(upliftCountryExc)) {
			String[] buffer = upliftCountryExc.trim().toUpperCase().split(",");
			Collection<String> countryCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				countryCodes.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateCountryCodes(companyCode,
					countryCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliduplcntry"));

			} else {
				parameters.addAll(createParameters(buffer, CONST_UPLCNT,
						EXCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "upliftCountryExc.........", buffer.length);
		}
		if (disCityInc != null && disCityInc.length() > 0
				&& !("-").equals(disCityInc)) {
			String[] buffer = disCityInc.trim().toUpperCase().split(",");
			Collection<String> countryCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				countryCodes.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateCityCodes(companyCode,
					countryCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliddiscity"));
				log.log(Log.INFO, "Erros in country code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_DISCTY,
						INCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "upliftCityInc.........", buffer.length);
		}
		if (disCityExc != null && disCityExc.length() > 0
				&& !("-").equals(disCityExc)) {
			String[] buffer = disCityExc.trim().toUpperCase().split(",");
			Collection<String> countryCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				countryCodes.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateCityCodes(companyCode,
					countryCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliddiscity"));
				log.log(Log.INFO, "Erros in country code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_DISCTY,
						EXCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "upliftCityExc.........", buffer.length);
		}
		if (disCountryInc != null && disCountryInc.length() > 0
				&& !("-").equals(disCountryInc)) {
			String[] buffer = disCountryInc.trim().toUpperCase().split(",");
			Collection<String> countryCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				countryCodes.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateCountryCodes(companyCode,
					countryCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliddiscntry"));

			} else {
				parameters.addAll(createParameters(buffer, CONST_DISCNT,
						INCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "upliftCountryInc.........", buffer.length);
		}
		if (disCountryExc != null && disCountryExc.length() > 0
				&& !("-").equals(disCountryExc)) {
			String[] buffer = disCountryExc.trim().toUpperCase().split(",");
			Collection<String> countryCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				countryCodes.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateCountryCodes(companyCode,
					countryCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliddiscntry"));

			} else {
				parameters.addAll(createParameters(buffer, CONST_DISCNT,
						EXCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "upliftCountryExc.........", buffer.length);
		}
		if (categoryInc != null && categoryInc.length() > 0
				&& !("-").equals(categoryInc)) {
			String[] buffer = categoryInc.trim().toUpperCase().split(",");
			parameters.addAll(createParameters(buffer, CONST_CAT, INCLUDE_FLAG,
					session));
			log.log(Log.FINE, "categoryInc.........", buffer.length);
		}
		if (categoryExc != null && categoryExc.length() > 0
				&& !("-").equals(categoryExc)) {
			String[] buffer = categoryExc.trim().toUpperCase().split(",");
			parameters.addAll(createParameters(buffer, CONST_CAT, EXCLUDE_FLAG,
					session));
			log.log(Log.FINE, "categoryExc.........", buffer.length);
		}
		if (uldTypeInc != null && uldTypeInc.length() > 0
				&& !("-").equals(uldTypeInc)) {
			String[] buffer = uldTypeInc.trim().toUpperCase().split(",");
			Collection<String> uldCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				uldCodes.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateUldTypes(companyCode, uldCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliduldtype"));
				log.log(Log.INFO, "Erros in uld code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_ULDGRP,
						INCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "uldTypeInc.........", buffer.length);
		}
		if (uldTypeExc != null && uldTypeExc.length() > 0
				&& !("-").equals(uldTypeExc)) {
			String[] buffer = uldTypeExc.trim().toUpperCase().split(",");
			Collection<String> uldCodes = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				uldCodes.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateUldTypes(companyCode, uldCodes);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliduldtype"));
				log.log(Log.INFO, "Erros in uld code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_ULDGRP,
						EXCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "uldTypeExc.........", buffer.length);
		}
		if (billingClassInc != null && billingClassInc.length() > 0
				&& !("-").equals(billingClassInc)) {
			String[] buffer = billingClassInc.trim().toUpperCase().split(",");
			parameters.addAll(createParameters(buffer, CONST_CLASS,
					INCLUDE_FLAG, session));
			log.log(Log.FINE, "billingClassInc.........", buffer.length);
		}
		if (billingClassExc != null && billingClassExc.length() > 0
				&& !("-").equals(billingClassExc)) {
			String[] buffer = billingClassExc.trim().toUpperCase().split(",");
			parameters.addAll(createParameters(buffer, CONST_CLASS,
					EXCLUDE_FLAG, session));
			log.log(Log.FINE, "billingClassExc.........", buffer.length);
		}
		if (subClassInc != null && subClassInc.length() > 0
				&& !("-").equals(subClassInc)) {
			String[] buffer = subClassInc.trim().toUpperCase().split(",");
			parameters.addAll(createParameters(buffer, CONST_SUBCLS_GRP,
					INCLUDE_FLAG, session));
			log.log(Log.FINE, "subClassInc.........", buffer.length);
		}
		if (subClassExc != null && subClassExc.length() > 0
				&& !("-").equals(subClassExc)) {
			String[] buffer = subClassExc.trim().toUpperCase().split(",");
			parameters.addAll(createParameters(buffer, CONST_SUBCLS_GRP,
					EXCLUDE_FLAG, session));
			log.log(Log.FINE, "subClassExc.........", buffer.length);
		}
		//Added by A-7540 starts
		if (mailServiceInc != null && mailServiceInc.length() > 0
				&& !("-").equals(mailServiceInc)) {
			String[] buffer = mailServiceInc.trim().toUpperCase().split(",");
			parameters.addAll(createParameters(buffer, CONST_MALSRV,
					INCLUDE_FLAG, session));
			log.log(Log.FINE, "mailServiceInc.........", buffer.length);
		}
		if (mailServiceExc != null && mailServiceExc.length() > 0
				&& !("-").equals(mailServiceExc)) {
			String[] buffer = mailServiceExc.trim().toUpperCase().split(",");
			parameters.addAll(createParameters(buffer, CONST_MALSRV,
					EXCLUDE_FLAG, session));
			log.log(Log.FINE, "mailServiceExc.........", buffer.length);
		}
		//Added by a-7540 ends
		if (mailSubClassInc != null && mailSubClassInc.length() > 0
				&& !("-").equals(mailSubClassInc)) {
			String[] buffer = mailSubClassInc.trim().toUpperCase().split(",");
			Collection<String> subClasses = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				subClasses.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs=validateSubClass(companyCode,
					subClasses);
			log.log(Log.FINE, "errs.........", buffer.length);
			if(errs!=null && errs.size()>0 ){
				invocationContext.addAllError(errs);
			}
			parameters.addAll(createParameters(buffer, CONST_SUBCLS,
					INCLUDE_FLAG, session));

			log.log(Log.FINE, "mailSubClassInc.........", buffer.length);
		}
		if (mailSubClassExc != null && mailSubClassExc.length() > 0
				&& !("-").equals(mailSubClassExc)) {
			String[] buffer = mailSubClassExc.trim().toUpperCase().split(",");
			Collection<String> subClasses = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				subClasses.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs= validateSubClass(companyCode,
					subClasses);
			if(errs!=null && errs.size()>0 ){
				invocationContext.addAllError(errs);
			}
			parameters.addAll(createParameters(buffer, CONST_SUBCLS,
					EXCLUDE_FLAG, session));


			log.log(Log.FINE, "mailSubClassExc.........", buffer.length);
		}
		if (flightNumInc != null && flightNumInc.length() > 0
				&& !("-").equals(flightNumInc)) {
			if(flightNumInc.length() > 7){
				errors.add(new ErrorVO(
				"mailtracking.mra.defaults.maintainbillingmatrix.fltnumshouldbeoffive"));
			}

			String[] buffer = flightNumInc.trim().toUpperCase().split(",");
			parameters.addAll(createParameters(buffer, CONST_FLTNUM,
					INCLUDE_FLAG, session));
			log.log(Log.FINE, "flightNumInc.........", buffer.length);

		}
		if (flightNumExc != null && flightNumExc.length() > 0
				&& !("-").equals(flightNumExc)) {
			if(flightNumExc.length() > 7){
				errors.add(new ErrorVO(
				"mailtracking.mra.defaults.maintainbillingmatrix.fltnumshouldbeoffive"));
			}

			String[] buffer = flightNumExc.trim().toUpperCase().split(",");
			parameters.addAll(createParameters(buffer, CONST_FLTNUM,
					EXCLUDE_FLAG, session));
			log.log(Log.FINE, "flightNumExc.........", buffer.length);

		}
		if (transferedByInc != null && transferedByInc.length() > 0
				&& !("-").equals(transferedByInc)) {
			isTransferedBy="Y";
			String[] buffer = transferedByInc.trim().toUpperCase().split(",");
			Collection<String> transferredBys = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				transferredBys.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateAirlineCode(companyCode,transferredBys);
			if(errs!=null ){
				invocationContext.addAllError(errs);
			}
			parameters.addAll(createParameters(buffer, CONST_TRASFEREDBY,
					INCLUDE_FLAG, session));
			log.log(Log.FINE, "transferedByInc.........", buffer.length);
		}
		if (transferedByExc != null && transferedByExc.length() > 0
				&& !("-").equals(transferedByExc)) {
			isTransferedBy="Y";
			String[] buffer = transferedByExc.trim().toUpperCase().split(",");
			Collection<String> transferredBys = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				transferredBys.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateAirlineCode(companyCode,transferredBys);
			if(errs!=null ){
				invocationContext.addAllError(errs);
			}
			parameters.addAll(createParameters(buffer, CONST_TRASFEREDBY,
					EXCLUDE_FLAG, session));
			log.log(Log.FINE, "transferedByExc.........", buffer.length);
		}
		if (transferredPAInc != null && transferredPAInc.length() > 0
				&& !("-").equals(transferredPAInc)) {
			isTransferedPA="Y";
			String[] buffer = transferredPAInc.trim().toUpperCase().split(",");
			Collection<String> transferredPAs = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				transferredPAs.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateGpaCode(companyCode,transferredPAs);
			if(errs!=null ){
				invocationContext.addAllError(errs);
			}
			parameters.addAll(createParameters(buffer, CONST_TRANSFEREDPA,
					INCLUDE_FLAG, session));
			log.log(Log.FINE, "transferredPAInc.........", buffer.length);
		}
		if (transferredPAExc != null && transferredPAExc.length() > 0
				&& !("-").equals(transferredPAExc)) {
			isTransferedPA="Y";
			String[] buffer = transferredPAExc.trim().toUpperCase().split(",");
			Collection<String> transferredPAs = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				transferredPAs.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateGpaCode(companyCode,transferredPAs);
			if(errs!=null ){
				invocationContext.addAllError(errs);
			}
			parameters.addAll(createParameters(buffer, CONST_TRANSFEREDPA,
					EXCLUDE_FLAG, session));
			log.log(Log.FINE, "transferredPAExc.........", buffer.length);
		}
		if("Y".equals(isTransferedBy) && "Y".equals(isTransferedPA)){
			errors.add(new ErrorVO(
			"mailtracking.mra.defaults.maintainbillingmatrix.eithertransferbyorpa"));
		}
		if (mailCompanyInc != null && mailCompanyInc.length() > 0
				&& !("-").equals(mailCompanyInc)) {
			isTransferedPA="Y";
			String[] buffer = mailCompanyInc.trim().toUpperCase().split(",");
			Collection<String> transferredPAs = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				transferredPAs.add(buffer[i]);
			}
			Collection<ErrorVO> errs = validateCompany(companyCode,transferredPAs);
			if(errs!=null ){
				invocationContext.addAllError(errs);
			}
			parameters.addAll(createParameters(buffer, CONST_MAILCOMPANY,
					INCLUDE_FLAG, session));
			log.log(Log.FINE, "mailCompanyInc.........", buffer.length);
		}
		if (mailCompanyExc != null && mailCompanyExc.length() > 0
				&& !("-").equals(mailCompanyExc)) {
			isTransferedPA="Y";
			String[] buffer = mailCompanyExc.trim().toUpperCase().split(",");
			Collection<String> transferredPAs = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				transferredPAs.add(buffer[i]);
			}
			Collection<ErrorVO> errs = validateCompany(companyCode,transferredPAs);
			if(errs!=null ){
				invocationContext.addAllError(errs);
			}
			parameters.addAll(createParameters(buffer, CONST_MAILCOMPANY,
					EXCLUDE_FLAG, session));
			log.log(Log.FINE, "mailCompanyExc.........", buffer.length);
		}
		
		if (uplAirportInc != null && uplAirportInc.length() > 0
				&& !("-").equals(uplAirportInc)) {
			String[] buffer = uplAirportInc.trim().toUpperCase().split(",");
			Collection<String> uplAirport = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				uplAirport.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateAirportCodes(companyCode,
					uplAirport);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invalidupliftairport"));
				log.log(Log.INFO, "Erros in airport code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_UPLARP,
						INCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "uplAirportInc.........", buffer.length);
		}
		if (uplAirportExc != null && uplAirportExc.length() > 0
				&& !("-").equals(uplAirportExc)) {
			String[] buffer = uplAirportExc.trim().toUpperCase().split(",");
			Collection<String> uplAirport = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				uplAirport.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateAirportCodes(companyCode,
					uplAirport);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invalidupliftairport"));
				log.log(Log.INFO, "Erros in airport code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_UPLARP,
						EXCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "UplAirportExc.........", buffer.length);
		}
		
		if (disAirportInc != null && disAirportInc.length() > 0
				&& !("-").equals(disAirportInc)) {
			String[] buffer = disAirportInc.trim().toUpperCase().split(",");
			Collection<String> disAirport = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				disAirport.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateAirportCodes(companyCode,
					disAirport);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliddischargeairport"));
				log.log(Log.INFO, "Erros in airport code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_DISARP,
						INCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "disAirportInc.........", buffer.length);
		}
		if (disAirportExc != null && disAirportExc.length() > 0
				&& !("-").equals(disAirportExc)) {
			String[] buffer = disAirportExc.trim().toUpperCase().split(",");
			Collection<String> disAirport = new ArrayList<String>();
			for (int i = 0; i < buffer.length; i++){
				disAirport.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateAirportCodes(companyCode,
					disAirport);
			if (errs != null && errs.size() > 0) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invaliddischargeairport"));
				log.log(Log.INFO, "Erros in airport code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_DISARP,
						EXCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "DisAirportExc.........", buffer.length);
		}

		if (flownCarrierInc != null && flownCarrierInc.length() > 0
				&& !("-").equals(flownCarrierInc)) {
			String[] buffer = flownCarrierInc.trim().toUpperCase().split(",");
			Collection<String> flownCarrier = new ArrayList<>();
			for (int i = 0; i < buffer.length; i++){
				flownCarrier.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateAirlineCode(companyCode,
					flownCarrier);
			if (errs != null && !errs.isEmpty()) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invalidflowncarrier"));
				log.log(Log.INFO, "Erros in airport code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_FLNCAR,
						INCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "flownCarrierInc.........", buffer.length);
		}
		
		
		if (flownCarrierExc != null && flownCarrierExc.length() > 0
				&& !("-").equals(flownCarrierExc)) {
			String[] buffer = flownCarrierExc.trim().toUpperCase().split(",");
			Collection<String> flownCarrier = new ArrayList<>();
			for (int i = 0; i < buffer.length; i++){
				flownCarrier.add(buffer[i]);
			}
				
			Collection<ErrorVO> errs = validateAirlineCode(companyCode,
					flownCarrier);
			if (errs != null && !errs.isEmpty()) {
				errors
				.add(new ErrorVO(
						"mailtracking.mra.defaults.maintainbillingmatrix.invalidflowncarrier"));
				log.log(Log.INFO, "Erros in airport code", errs.size());
			} else {
				parameters.addAll(createParameters(buffer, CONST_FLNCAR,
						EXCLUDE_FLAG, session));
			}
			log.log(Log.FINE, "flownCarrierExc.........", buffer.length);
		}

		log.log(Log.FINE, "Obtained parameters are", parameters);
		if (errors.size() > 0) {
			invocationContext.addAllError(errors);
		}
		return parameters;
	}

	private Collection<BillingLineParameterVO> createParameters(
			String[] buffer, String parameterType, String flag,
			MaintainBillingMatrixSession session) {
		Collection<BillingLineParameterVO> parameterVOs = null;

		for (int i = 0; i < buffer.length; i++) {
			if (parameterVOs == null) {
				parameterVOs = new ArrayList<BillingLineParameterVO>();
			}
			BillingLineParameterVO parameterVO = new BillingLineParameterVO();
			parameterVO.setExcludeFlag(flag);
			parameterVO.setParameterCode(parameterType);
			if (parameterType.equalsIgnoreCase(BillingLineParameterVO.CATEGORY)) {
				String catCode = getCodeForCategory(buffer[i], session);
				parameterVO.setParameterValue(catCode);
			} else if (parameterType
					.equalsIgnoreCase(BillingLineParameterVO.CLASS)) {
				String classCode = getCodeForClass(buffer[i], session);
				parameterVO.setParameterValue(classCode);

			} else {
				parameterVO.setParameterValue(buffer[i]);
			}
			log.log(Log.FINE, "Obtained parameterVO is", parameterVO);
			parameterVOs.add(parameterVO);
		}
		log.log(Log.FINE, "Obtained parameterVOs are", parameterVOs);
		return parameterVOs;
	}

	// private void setBillingLinePage(MaintainBillingMatrixSession
	// session,BillingLineVO billingLineVO){
	//		   
	// List<BillingLineVO> billingLineList = new ArrayList<BillingLineVO>();
	// if(session.getBillingLineDetails() != null)
	// for(BillingLineVO vo : session.getBillingLineDetails() )
	// billingLineList.add(vo);
	//			   
	// billingLineList.add(billingLineVO);
	// int size = billingLineList.size();
	//			   
	// Page<BillingLineVO> page = new
	// Page<BillingLineVO>(billingLineList,1,size,size,0,0,0,false);
	// session.setBillingLineDetails(page);
	//		   
	//			  
	// }

	private Collection<ErrorVO> validateCountryCodes(String companyCode,
			Collection<String> countryCodes) {

		AreaDelegate areaDelegate = new AreaDelegate();
		log.log(Log.FINE, "***************Chkng country code");

		try {
			areaDelegate.validateCountryCodes(companyCode, countryCodes);
			log.log(Log.FINE, "***************\nOver Chkng country code");
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "inside updateAirlinecaught busDelegateExc");
			businessDelegateException.getMessage();
			return handleDelegateException(businessDelegateException);
		}
		return null;
	}
	//Added by a7531 for icrd-224979
	private Collection<ErrorVO> validateAgentCodes(String companyCode,
			Collection<String> agentCodes) {

		log.log(Log.FINE, "***************Chkng agent code");

		try {
			new AgentDelegate().validateAgents(companyCode, agentCodes);
			log.log(Log.FINE, "***************\nOver Chkng agent code");
		} catch (BusinessDelegateException businessDelegateException) {
			
			businessDelegateException.getMessage();
			return handleDelegateException(businessDelegateException);
		}
		return null;
	}
	
	//Added by A-7540 for icrd-232319
	private Collection<ErrorVO> validateAirportCodes(String companyCode,
			Collection<String> orgAirport) {
		
		AreaDelegate areaDelegate = new AreaDelegate();
		log.log(Log.FINE, "***************Chkng airport code");

		try {
			areaDelegate.validateAirportCodes(companyCode, orgAirport);
			log.log(Log.FINE, "***************\nOver Chkng airport code");
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "inside updateAirlinecaught busDelegateExc");
			businessDelegateException.getMessage();
			return handleDelegateException(businessDelegateException);
		}
		return null;
	}
	private Collection<ErrorVO> validateUldTypes(String companyCode,
			Collection<String> uldTypeCodes) {
		log.log(Log.INFO, "in uld vlidatn....", uldTypeCodes);
		ULDDelegate delegate = new ULDDelegate();
		try {
			if (uldTypeCodes != null) {
				delegate.validateULDGroupCodes(companyCode, uldTypeCodes);
			}	

		} catch (BusinessDelegateException businessDelegateException) {
			log.log(1, "Delegate exception..........");
			return handleDelegateException(businessDelegateException);

		}
		return null;
	}

	private Collection<ErrorVO> validateCityCodes(String companyCode,
			Collection<String> cityCodes) {
		AreaDelegate areaDelegate = new AreaDelegate();
		log.log(Log.FINE, "***************Chkng city code");

		try {
			areaDelegate.validateCityCodes(companyCode, cityCodes);
			log.log(Log.FINE, "***************\nOver Chkng cityCodes code");
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "inside updateAirlinecaught busDelegateExc");
			return handleDelegateException(businessDelegateException);
		}
		return null;

	}

	private ErrorVO validateRegion(String companyCode,
			Collection<String> regCodes) {
		log.log(Log.FINE, "***************Chkng reg code");

		for (String reg : regCodes) {
			try {
				new AreaDelegate().validateLevel(companyCode, "REG", reg);
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.FINE, "inside updateAirlinecaught busDelegateExc");
				businessDelegateException.getMessage();
				handleDelegateException(businessDelegateException);
				return new ErrorVO("invalidregion");

			}
		}
		return null;

	}

	private Collection<ErrorVO> validateForm(BillingLineForm form,
			String companyCode,MaintainBillingMatrixSession session) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(form.getCatInc() != null && form.getCatInc().trim().length()>0){
			String[] buffer = form.getCatInc().trim().toUpperCase().split(",");
			String errorString="";
			StringBuilder codeArray = new StringBuilder();
			if(!("-").equals(buffer[0])){
				for(int i=0;i<buffer.length;i++){
					if(getCodeForCategory(buffer[i],session)==null){
						log.log(1, "category included not present....");
						if (("").equals(errorString)) {
							errorString = buffer[i];
							codeArray.append(errorString);
						} else {
							errorString = codeArray.append(",").append(buffer[i]).toString();
						}
					}
				}
			}
			if(errorString.trim().length()>0){
				Object[] errorArray = { errorString };
				ErrorVO err = new ErrorVO(INVCATEGORY,errorArray);
				errors.add(err);
			}
		}
		//Added for Bug ICRD-129490 by A-5526 starts
		if ((!OPFLAG_UPD.equals(form.getBillingBasis()))) {

			if ((form.getRatingBasisMail() != null && form.getRatingBasisMail()
					.trim().length() > 0)
					|| (form.getRatingBasisOther() != null && form
							.getRatingBasisOther().trim().length() > 0)) {

				String[] weightMail = form.getWbFrmWgtMail();
				String[] rateMail = form.getWbApplicableRateMail();
				String[] weightOther = form.getWbFrmWgtOther();
				String[] rateOther = form.getWbApplicableRateOther();

				String errorMessageMail = "";
				String errorMessageOther = "";
				boolean rateMentionedMail = false;
				boolean rateMentionedOther = false;
				//Added as part of bug ICRD-129585 by A-5526 starts
				boolean surChargeRate= false;
				boolean chargeHeadPresent= false;
				String surChargeError="";
if((FLAT_CHARGE.equals(form.getRatingBasisOther())
		&& form.getFlatChargeOther() != null && form
		.getFlatChargeOther().trim().length() > 0) ||
								(FLAT_RATE.equals(form.getRatingBasisOther())
										&& form.getFlatRateOther() != null && form
										.getFlatRateOther().trim().length() > 0) ||
										(WEIGHT_BREAK.equals(form.getRatingBasisOther()) && ((form
												.getMinimumChargeOther() != null && form
												.getMinimumChargeOther().trim().length() > 0)
												|| (form.getNormalRateOther() != null && form
														.getNormalRateOther().trim().length() > 0) || (weightOther.length > 1 && rateOther.length > 1))

										)){
	surChargeRate=true;	      
								}
if(form.getChargeHead()!=null && form.getChargeHead().trim().length()>0){
	chargeHeadPresent=true;
}

if(!surChargeRate && chargeHeadPresent){
	ErrorVO err = new ErrorVO(SURCHARGE_RATE_MANDATORY);
	errors.add(err);
	surChargeError=SURCHARGE_RATE_MANDATORY;
}

//Added as part of bug ICRD-129585 by A-5526 ends

				if ((FLAT_CHARGE.equals(form.getRatingBasisMail())
						&& form.getFlatChargeMail() != null && form
						.getFlatChargeMail().trim().length() > 0)
						|| (FLAT_RATE.equals(form.getRatingBasisMail())
								&& form.getFlatRateMail() != null && form
								.getFlatRateMail().trim().length() > 0)) {
					rateMentionedMail = true;
				}else{
					if((FLAT_CHARGE.equals(form.getRatingBasisMail()))
							||(FLAT_RATE.equals(form.getRatingBasisMail()))){
					errorMessageMail = "mailtracking.mra.defaults.maintainbillingmatrix.flatchargeorflatrate.mandatory";
					}
				}
				if ((FLAT_CHARGE.equals(form.getRatingBasisOther())
						&& form.getFlatChargeOther() != null && form
						.getFlatChargeOther().trim().length() > 0)
						|| (FLAT_RATE.equals(form.getRatingBasisOther())
								&& form.getFlatRateOther() != null && form
								.getFlatRateOther().trim().length() > 0)) {
					rateMentionedOther = true;

				} else {
					if((FLAT_CHARGE.equals(form.getRatingBasisOther()))
							||(FLAT_RATE.equals(form.getRatingBasisOther()))){
					errorMessageMail = "mailtracking.mra.defaults.maintainbillingmatrix.flatchargeorflatrate.mandatory";
					}
				}

				if ((WEIGHT_BREAK.equals(form.getRatingBasisMail()) && ((form
						.getMinimumChargeMail() != null && form
						.getMinimumChargeMail().trim().length() > 0)
						|| (form.getNormalRateMail() != null && form
								.getNormalRateMail().trim().length() > 0) || (weightMail.length > 1 && rateMail.length > 1))

				))

				{
					rateMentionedMail = true;
					errorMessageMail = "";

				}else{
					if(WEIGHT_BREAK.equals(form.getRatingBasisMail())){
					errorMessageMail = "mailtracking.mra.defaults.maintainbillingmatrix.minchargenormalrate.mandatory";
					}
				}if ((WEIGHT_BREAK.equals(form.getRatingBasisOther()) && ((form
						.getMinimumChargeOther() != null && form
						.getMinimumChargeOther().trim().length() > 0)
						|| (form.getNormalRateOther() != null && form
								.getNormalRateOther().trim().length() > 0) || (weightOther.length > 1 && rateOther.length > 1))

				)) {

					rateMentionedOther = true;
					errorMessageOther = "";
				} else if (WEIGHT_BREAK.equals(form.getRatingBasisOther())) {
					errorMessageOther = "mailtracking.mra.defaults.maintainbillingmatrix.minchargenormalrate.mandatory";
				}
				
				if(USPS.equals(form.getRatingBasisMail()) &&
						(form.getUspsTot() >0 ) ||(form.getUspsRateOne()>0 && form.getUspsRateTwo()>0 && form.getUspsRateThr()>0 && form.getUspsRateFour()>0))//Modified by a-7871 for ICRD-282240
				{
					rateMentionedMail = true;
					errorMessageMail = "";
				}
				else{
					if(USPS.equals(form.getRatingBasisMail())){
						errorMessageMail="mailtracking.mra.defaults.maintainbillingmatrix.totalrate.mandatory";
					}
				}
						
if(surChargeError!=null && surChargeError.trim().length()>0){
	errorMessageMail="";
	errorMessageOther = "";
}
				if (!rateMentionedMail && errorMessageMail != null
						&& errorMessageMail.trim().length() > 0) {
					ErrorVO err = new ErrorVO(errorMessageMail);
					errors.add(err);       
				}
				if (!rateMentionedOther && errorMessageOther != null
						&& errorMessageOther.trim().length() > 0) {
					ErrorVO err = new ErrorVO(errorMessageOther);
					errors.add(err);       
				}
				if(surChargeRate  && !chargeHeadPresent){      
					ErrorVO err = new ErrorVO(CHARGEHEAD_MANDATORY);
					errors.add(err);
				}

			}
		}
		//Added for Bug ICRD-129490 by A-5526 ends
		if(form.getCatExc() != null && form.getCatExc().trim().length()>0){
			String[] buffer = form.getCatExc().trim().toUpperCase().split(",");
			String errorString="";
			StringBuilder codeArray = new StringBuilder();
			if(!("-").equals(buffer[0])){
				for(int i=0;i<buffer.length;i++){
					if(getCodeForCategory(buffer[i],session)==null){
						log.log(1, "category excluded not present....");
						if (("").equals(errorString)) {
							errorString = buffer[i];
							codeArray.append(errorString);
						} else {
							errorString = codeArray.append(",").append(buffer[i]).toString();
						}
					}
				}
			}
			if(errorString.trim().length()>0){
				Object[] errorArray = { errorString };
				ErrorVO err = new ErrorVO(INVCATEGORY,errorArray);
				errors.add(err);
			}
		}
		if(form.getClassExc() != null && form.getClassExc().trim().length()>0){
			String[] buffer = form.getClassExc().trim().toUpperCase().split(",");
			String errorString="";
			StringBuilder codeArray = new StringBuilder();
			if(!("-").equals(buffer[0])){
				for(int i=0;i<buffer.length;i++){
					if(getCodeForClass(buffer[i],session)==null){
						log.log(1, "Class included not present....");
						if (("").equals(errorString)) {
							errorString = buffer[i];
							codeArray.append(errorString);
						} else {
							errorString = codeArray.append(",").append(buffer[i]).toString();
						}
					}
				}
			}
			if(errorString.trim().length()>0){
				Object[] errorArray = { errorString };
				ErrorVO err = new ErrorVO("mailtracking.mra.defaults.maintainbillingmatrix.invalidClass",errorArray);
				errors.add(err);
			}
		}
		if(form.getClassInc() != null && form.getClassInc().trim().length()>0){
			String[] buffer = form.getClassInc().trim().toUpperCase().split(",");
			String errorString="";
			StringBuilder codeArray = new StringBuilder();
			if(!("-").equals(buffer[0])){
				for(int i=0;i<buffer.length;i++){
					if(getCodeForClass(buffer[i],session)==null){
						log.log(1, "class included not present....");
						if (("").equals(errorString)) {
							errorString = buffer[i];
							codeArray.append(errorString);
						} else {
							errorString = codeArray.append(",").append(buffer[i]).toString();
						}
					}
				}
			}
			if(errorString.trim().length()>0){
				Object[] errorArray = { errorString };
				ErrorVO err = new ErrorVO("mailtracking.mra.defaults.maintainbillingmatrix.invalidClass",errorArray);
				errors.add(err);
			}
		}
		if (form.getRevExp() == null) {
			log.log(1, "REVEXP not present....");
			ErrorVO err = new ErrorVO(REVEXP_NULL);
			errors.add(err);

		} else {
			if (REV_FLAG.equals(form.getRevExp())) {
				if (!(form.getBillingParty() != null && !"".equals(form
						.getBillingParty().trim().toUpperCase()))) {
					log.log(1, "Billing Party not present....");
					ErrorVO err = new ErrorVO(BILLINGPARTY_MANDATORY);
					errors.add(err);
				}
				if (!(form.getBillTo() != null && !"".equals(form.getBillTo()
						.trim().toUpperCase()))) {
					log.log(1, "Bill To not present....");
					ErrorVO err = new ErrorVO(BILLTO_MANDATORY);
					errors.add(err);
				}
			} else if (EXP_FLAG.equals(form.getRevExp())) {
				if (!(form.getBillBy() != null && !"".equals(form.getBillBy()
						.trim().toUpperCase()))) {
					log.log(1, "Bill By not present....");
					ErrorVO err = new ErrorVO(BILLBY_MANDATORY);
					errors.add(err);
				}
			}
		}
		if (form.getBilledSector() == null
				|| form.getBilledSector().trim().length() == 0) {
			log.log(1, "getBilledSector not present....");
			ErrorVO err = new ErrorVO(BLDSEC_NULL);
			errors.add(err);
		}

		if (form.getBillingBasis() == null
				|| form.getBillingBasis().trim().length() == 0) {
			log.log(1, "getBillingBasis not present....");
			ErrorVO err = new ErrorVO(BLDBASIS_NULL);
			errors.add(err);
		} else if ("F".equals(form.getBillingBasis())) {
			/*if (form.getRate() == null || form.getRate().trim().length() == 0) {
				log.log(1, "getRate not present....");
				ErrorVO err = new ErrorVO(RATE_NULL);
				errors.add(err);
			}*/
		}
		if (form.getBlgLineValidFrom() == null
				|| form.getBlgLineValidFrom().trim().length() == 0) {
			log.log(1, "getBlgLineValidFrom not present....");
			ErrorVO err = new ErrorVO(FROMDATE_NULL);
			errors.add(err);
		}
		if (form.getBlgLineValidTo() == null
				|| form.getBlgLineValidTo().trim().length() == 0) {
			log.log(1, "getBlgLineValidTo not present....");
			ErrorVO err = new ErrorVO(TODATE_NULL);
			errors.add(err);
		}
		if(!OPFLAG_UPD.equals(form.getBillingBasis())){
		if (form.getCurrency() == null
				|| form.getCurrency().trim().length() == 0) {
			log.log(1, "getCurrency not present....");
			ErrorVO err = new ErrorVO(CURCODE_NULL);
			errors.add(err);
		} else {
			log.log(Log.INFO, "getCurrency  present....", form.getCurrency());
			try {
				new CurrencyDelegate().validateCurrency(companyCode, form
						.getCurrency().trim().toUpperCase());
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				ErrorVO err = new ErrorVO(INVALID_CURCODE);
				errors.add(err);
				log.log(1, "getCurrency not valid....");
			}
		}
		}
		LocalDate startDate = null;
		LocalDate endDate = null;
		if (form.getBlgLineValidFrom() != null
				&& form.getBlgLineValidFrom().length() > 0) {
			startDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
					false);
			startDate.setDate(form.getBlgLineValidFrom().trim().toUpperCase());

		}

		if (form.getBlgLineValidTo() != null
				&& form.getBlgLineValidTo().length() > 0) {
			endDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			endDate.setDate(form.getBlgLineValidTo().trim().toUpperCase());

		}
		if (startDate != null && endDate != null && startDate.after(endDate)) {

			log.log(1, "Date range not correct....");
			ErrorVO err = new ErrorVO(INVALID_DATE_RANGE);
			errors.add(err);
		}
		log.log(Log.INFO, "From Date", form.getFrmDate());
		log.log(Log.INFO, "To Date", form.getToDate());
		int dateerror=0;
		if (form.getBlgLineValidTo() != null
				&& form.getBlgLineValidTo().length() > 0
				&& form.getBlgLineValidFrom() != null
				&& form.getBlgLineValidFrom().length() > 0) {
			LocalDate mtxStartDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			mtxStartDate.setDate(form.getFrmDate());
			LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			LocalDate mtxEndDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			mtxEndDate.setDate(form.getToDate());
			if (startDate.isLesserThan(mtxStartDate)
					|| (endDate.isGreaterThan(mtxEndDate))) {
				log
				.log(1,
						"Date range not matching with date range of mtx....");
				ErrorVO err = new ErrorVO(DATE_RANGE_OUT_MTX_RANGE);
				errors.add(err);
				dateerror++;
			}
			/*else if (startDate.isLesserThan(currentDate)){
				log
				.log(1,
						"line start date less than current date");
				ErrorVO err = new ErrorVO(LESSTHAN_CURRENTDATE);
				errors.add(err);
				dateerror++;

			}*/
			
		}

		return errors;
	}

	/*
	 * ArrayList<ErrorVO> validateParameters(Collection<BillingLineParameterVO>
	 * parameters){ ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();
	 * if(parameters != null && parameters.size()>0){ for(BillingLineParameterVO
	 * parVO : parameters){
	 * if(BillingLineParameterVO.ORIGIN_CITY.equals(parVO.getParameterCode())){
	 * if(!validateParameterValues(BillingLineParameterVO.DESTINATION_CITY,
	 * parVO.getParameterValue(),parameters)){ ErrorVO err = new
	 * ErrorVO("mailtracking.mra.defaults.maintainbillingmatrix.orgctysameasdestcty");
	 * errors.add(err); } }
	 * if(BillingLineParameterVO.ORIGIN_COUNTRY.equals(parVO.getParameterCode())){
	 * if(!validateParameterValues(BillingLineParameterVO.DESTINATION_COUNTRY,
	 * parVO.getParameterValue(),parameters)){ ErrorVO err = new
	 * ErrorVO("mailtracking.mra.defaults.maintainbillingmatrix.orgcntsameasdestcnt");
	 * errors.add(err); } }
	 * if(BillingLineParameterVO.ORIGIN_REGION.equals(parVO.getParameterCode())){
	 * if(!validateParameterValues(BillingLineParameterVO.DESTINATION_REGION,
	 * parVO.getParameterValue(),parameters)){ ErrorVO err = new
	 * ErrorVO("mailtracking.mra.defaults.maintainbillingmatrix.orgregsameasdestreg");
	 * errors.add(err); } }
	 * if(BillingLineParameterVO.UPLIFT_CITY.equals(parVO.getParameterCode())){
	 * if(!validateParameterValues(BillingLineParameterVO.DISCHARGE_CITY,
	 * parVO.getParameterValue(),parameters)){ ErrorVO err = new
	 * ErrorVO("mailtracking.mra.defaults.maintainbillingmatrix.uplctysameasdiscty");
	 * errors.add(err); } }
	 * if(BillingLineParameterVO.UPLIFT_COUNTRY.equals(parVO.getParameterCode())){
	 * if(!validateParameterValues(BillingLineParameterVO.DISCHARGE_COUNTRY,
	 * parVO.getParameterValue(),parameters)){ ErrorVO err = new
	 * ErrorVO("mailtracking.mra.defaults.maintainbillingmatrix.uplcntsameasdiscnt");
	 * errors.add(err); } } } } return errors; }
	 */
	Boolean validateParameterValues(String parameterCode,
			String parameterValue, Collection<BillingLineParameterVO> parameters) {
		if (parameters != null && parameters.size() > 0) {
			for (BillingLineParameterVO parVO : parameters) {
				if (parameterCode.equals(parVO.getParameterCode())
						&& parameterValue.equals(parVO.getParameterValue())) {
					return false;
				}

			}
		}
		return true;
	}

	private String getCodeForCategory(String desc,
			MaintainBillingMatrixSession session) {
		String code = null;
		Collection<OneTimeVO> oneTimeVOs = session.getOneTimeVOs().get(
		"mailtracking.defaults.mailcategory");
		for (OneTimeVO oneTimeVO : oneTimeVOs) {
			if (oneTimeVO.getFieldDescription().equalsIgnoreCase(desc)) {
				code = oneTimeVO.getFieldValue();
			}
		}
		return code;
	}

	private String getCodeForClass(String desc,
			MaintainBillingMatrixSession session) {
		String code = null;
		Collection<OneTimeVO> oneTimeVOs = session.getOneTimeVOs().get(
		"mailtracking.defaults.mailclass");
		for (OneTimeVO oneTimeVO : oneTimeVOs) {
			if (oneTimeVO.getFieldDescription().equalsIgnoreCase(desc)) {
				code = oneTimeVO.getFieldValue();
			}
		}
		return code;
	}


	/** Added By A-3434
	 * Method to validate subclasses
	 * @param companyCode,subClasses
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateSubClass(String companyCode,
			Collection<String> subClasses) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String errorFlag=null;
		for (String subClass : subClasses) {
			try{
				String validFlag=null;
				validFlag =  new MailTrackingMRADelegate().validateMailSubClass(companyCode,subClass);
				log.log(Log.FINE, "validFlag", validFlag);
				if(("N").equals(validFlag)){
					errorFlag="Y";
					errors = new ArrayList<ErrorVO>();
					Object[] obj = {subClass};
					errors.add(new ErrorVO("mailtracking.mra.defaults.billingline.subclass.invalid",obj));

				} 
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		log.log(Log.FINE, "errorFlag", errorFlag);
		if("Y".equals(errorFlag)){
			return errors;
		}
		else{
			return null;
		}


	}

	/**Added By A-3434
	 * Method to validate PA code
	 * @param companyCode,Collection<String>
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateGpaCode(String companyCode,
			Collection<String> transferedPAs) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String errorFlag=null;
		for (String transferedPA : transferedPAs) {
//			validate PA code
			log.log(Log.FINE, "Going To validate GPA code ...in command");
			try {
				PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
				postalAdministrationVO  = new MailTrackingMRADelegate().findPACode(
						companyCode,transferedPA.toUpperCase());
				log.log(Log.FINE, "postalAdministrationVO",
						postalAdministrationVO);
				if(postalAdministrationVO == null) {
					errorFlag="Y";
					Object[] obj = {transferedPA.toUpperCase()};
					errors.add(new ErrorVO(INVALID_POACODE,obj));
				}

			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		log.log(Log.FINE, "errorFlag", errorFlag);
		if("Y".equals(errorFlag)){
			return errors;
		}
		else{
			return null;
		}
	}
	/**
	 * 
	 * @param companyCode
	 * @param transferedPAs
	 * @return
	 */
	private Collection<ErrorVO> validateCompany(String companyCode,
			Collection<String> transferedPAs) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String errorFlag=null;
		for (String transferedPA : transferedPAs) {
//			validate Company code
			log.log(Log.FINE, "Going To validate Company code ...in command");
			try {
				PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
				postalAdministrationVO  = new MailTrackingMRADelegate().findPACode(
						companyCode,transferedPA.toUpperCase());
				log.log(Log.FINE, "postalAdministrationVO",
						postalAdministrationVO);
				if(postalAdministrationVO == null) {
					errorFlag="Y";
					Object[] obj = {transferedPA.toUpperCase()};
					errors.add(new ErrorVO(INVALID_COMPANY,obj));
				}

			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		log.log(Log.FINE, "errorFlag", errorFlag);
		if("Y".equals(errorFlag)){
			return errors;
		}
		else{
			return null;
		}
	}
	/**Added By A-3434
	 * Method to validate Airline code
	 * @param companyCode,Collection<String>
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateAirlineCode(String companyCode,
			Collection<String> transferedbys) {
		AirlineValidationVO airlineValidationVo = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String errorFlag=null;
		for (String transferedby : transferedbys) {
			try {

				AirlineDelegate delegate = new AirlineDelegate();
				airlineValidationVo = delegate.validateAlphaCode(
						companyCode, transferedby.toUpperCase().trim());
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				errorFlag="Y";
				ErrorVO err = new ErrorVO(INVALID_ARL);
				errors.add(err);
			}
		}
		log.log(Log.FINE, "errorFlag", errorFlag);
		if("Y".equals(errorFlag)){
			return errors;
		}
		else{
			return null;
		}
	}
	/**
	 * 
	 * @author A-5255
	 * @param billingLineVO
	 * @param session
	 * @param form
	 */
	private void populateRateDetails(BillingLineVO billingLineVO,
			MaintainBillingMatrixSession session, BillingLineForm form) {
		if (billingLineVO != null) {
			BillingLineDetailVO billingLineDetailVO=null;
		
			Map<String, BillingLineDetailVO> billingLineDetailMap = session
					.getBillingLineChargeDetails();
			List<BillingLineDetailVO> billingLineDetails=null;
			/**
			 * Checking whether Surcharge details exits. if yes setting the SurchargeIndicator as Y
			 */
			if(billingLineDetailMap!=null && billingLineDetailMap.size()>0){
				billingLineDetails= new ArrayList<BillingLineDetailVO>(
					billingLineDetailMap.values());
				if(billingLineDetails.size()>1)//Modified by a-7871 for ICRD-282240
				billingLineVO.setSurchargeIndicator(BillingLineVO.FLAG_YES);
			}
			
			/**
			 * Populating Mail charge 
			 */
			billingLineDetailVO=new BillingLineDetailVO();
			billingLineDetailVO.setChargeType("M");
			billingLineDetailVO.setRatingBasis(form.getRatingBasisMail());
			
			// Updating mail charge data into VO from Form
			populateBillingLineCharge(form, billingLineDetailVO);
			if (billingLineDetailVO.getBillingLineCharges() != null) {
				if (billingLineDetails == null) {
					billingLineDetails = new ArrayList<BillingLineDetailVO>();
				}
				if(!billingLineDetails.isEmpty()){
					List<BillingLineDetailVO> billingDetails = new ArrayList<BillingLineDetailVO>();
					for(BillingLineDetailVO bchgVO: billingLineDetails){
						billingDetails.add(bchgVO);
					}
					for(BillingLineDetailVO detVO: billingDetails){
						if("M".equals(detVO.getChargeType()))
							{
							billingLineDetails.remove(detVO);
							}
					}
				}
				billingLineDetails.add(billingLineDetailVO);
			}
			if(billingLineDetails!=null && billingLineDetails.size()>0){
				billingLineVO.setBillingLineDetails(billingLineDetails);
			}
		}

	}
	/**
	 * Added for ICRD-76551
	 * Updates the session vo with Mail charge data from Form
	 * @author A-5255
	 * @param form
	 * @param billingLineDetailVO
	 */
	private void populateBillingLineCharge(BillingLineForm form,BillingLineDetailVO billingLineDetailVO){
		Collection<BillingLineChargeVO> billingLineCharges=null;
		billingLineCharges=new ArrayList<BillingLineChargeVO>();
		BillingLineChargeVO billingLineChargeVO=null;
		boolean isMinRateRequired=false;
		Money aplRatChg=null;
		if ("FR".equals(form.getRatingBasisMail()) && form.getFlatRateMail() != null
				&& !EMPTY_STRING.equals(form.getFlatRateMail())) {
			billingLineChargeVO = new BillingLineChargeVO();
			billingLineChargeVO.setApplicableRateCharge(Double
					.parseDouble(form.getFlatRateMail()));
			billingLineChargeVO.setRateType(BillingLineChargeVO.RATE);
			billingLineCharges.add(billingLineChargeVO);
			billingLineDetailVO.setRatingBasis("FR");
		} else if ("FC".equals(form.getRatingBasisMail())
				&& form.getFlatChargeMail() != null
				&&!EMPTY_STRING.equals(form.getFlatChargeMail())) {
			billingLineChargeVO = new BillingLineChargeVO();
			billingLineChargeVO.setApplicableRateCharge(Double
					.parseDouble(form.getFlatChargeMail()));
			//Added as part of ICRD-150833 starts
			try {
				aplRatChg = CurrencyHelper
				.getMoney(form.getCurrency());
			} catch (CurrencyException e) {
				log.log(Log.FINE,  "CurrencyException");
			}
			aplRatChg.setAmount(billingLineChargeVO.getApplicableRateCharge());
			billingLineChargeVO.setAplRatChg(aplRatChg);
			//Added as part of ICRD-150833 ends
			billingLineChargeVO.setRateType(BillingLineChargeVO.CHARGE);
			billingLineCharges.add(billingLineChargeVO);
			billingLineDetailVO.setRatingBasis("FC");
		} else if ("WB".equals(form.getRatingBasisMail())) {
			billingLineDetailVO.setRatingBasis("WB");
	
				if (!EMPTY_STRING.equals(form.getMinimumChargeMail()
						.trim())) {
					billingLineChargeVO = new BillingLineChargeVO();
					billingLineChargeVO.setApplicableRateCharge(Double
							.parseDouble(form
									.getMinimumChargeMail()));
					//Added as part of ICRD-150833 starts
					try {
						aplRatChg = CurrencyHelper
						.getMoney(form.getCurrency());
					} catch (CurrencyException e) {
						log.log(Log.FINE,  "CurrencyException");
					}
					aplRatChg.setAmount(billingLineChargeVO.getApplicableRateCharge());
					billingLineChargeVO.setAplRatChg(aplRatChg);
					//Added as part of ICRD-150833 ends
					billingLineChargeVO.setRateType(BillingLineChargeVO.CHARGE);
					billingLineChargeVO.setFrmWgt(-1);
					billingLineCharges.add(billingLineChargeVO);
					//isMinRateRequired=true;
				}
				if (!EMPTY_STRING.equals(form.getNormalRateMail()
						.trim())) {
					billingLineChargeVO = new BillingLineChargeVO();
					billingLineChargeVO
							.setApplicableRateCharge(Double
									.parseDouble(form
											.getNormalRateMail()));
					billingLineChargeVO.setRateType(BillingLineChargeVO.RATE);
					billingLineChargeVO.setFrmWgt(0);
					billingLineCharges.add(billingLineChargeVO);
					//isMinRateRequired=true;
					
				}
				/*if(isMinRateRequired){
					billingLineCharges.add(billingLineChargeVO);
				}*/
			
			if(form.getWbFrmWgtMail()!=null && form.getWbFrmWgtMail().length>0){
				int i=0;
				for(String wbFrmWgtOther:form.getWbFrmWgtMail()){
					if(!EMPTY_STRING.equals(wbFrmWgtOther)){
						billingLineChargeVO = new BillingLineChargeVO();
						billingLineChargeVO.setFrmWgt(Double
								.parseDouble(wbFrmWgtOther));
						billingLineChargeVO.setRateType(BillingLineChargeVO.RATE);
						if(!EMPTY_STRING.equals(form.getWbApplicableRateMail()[i])){
						billingLineChargeVO.setApplicableRateCharge(Double
								.parseDouble(form.getWbApplicableRateMail()[i]));
						}  
						billingLineChargeVO.setOperationalFlag("I");
						billingLineCharges.add(billingLineChargeVO);
					}
					i++;
				}
			}
			
		}
		//Added by a-7540 for ICRD-232319
		else if("US".equals(form.getRatingBasisMail())){
			billingLineDetailVO.setRatingBasis("US");
			if(form.getUspsRateOne()!=null){
					if(!EMPTY_STRING.equals(form.getUspsRateOne())){
						billingLineChargeVO = new BillingLineChargeVO();
						billingLineChargeVO.setApplicableRateCharge(form.getUspsRateOne());
						billingLineChargeVO.setFrmWgt(1);
						//billingLineChargeVO.setOperationalFlag("I");
						billingLineCharges.add(billingLineChargeVO);
					}
					
				}
			if(form.getUspsRateTwo()!=null){
				
				if(!EMPTY_STRING.equals(form.getUspsRateTwo())){
					billingLineChargeVO = new BillingLineChargeVO();
					billingLineChargeVO.setApplicableRateCharge(form.getUspsRateTwo()) ;
					billingLineChargeVO.setFrmWgt(2);
					//billingLineChargeVO.setOperationalFlag("I");
					billingLineCharges.add(billingLineChargeVO);
				}
			}
	      if(form.getUspsRateThr()!=null){
				
				if(!EMPTY_STRING.equals(form.getUspsRateThr())){
					billingLineChargeVO = new BillingLineChargeVO();
					billingLineChargeVO.setApplicableRateCharge(form.getUspsRateThr()) ;
					billingLineChargeVO.setFrmWgt(3);
					//billingLineChargeVO.setOperationalFlag("I");
					billingLineCharges.add(billingLineChargeVO);
				}
			}
	      if(form.getUspsRateFour()!=null){
				
				if(!EMPTY_STRING.equals(form.getUspsRateFour())){
					billingLineChargeVO = new BillingLineChargeVO();
					billingLineChargeVO.setApplicableRateCharge(form.getUspsRateFour()) ;
					billingLineChargeVO.setFrmWgt(4);
					//billingLineChargeVO.setOperationalFlag("I");
					billingLineCharges.add(billingLineChargeVO);
				}
			}
	      if(form.getUspsTot()!=null){
				
				if(!EMPTY_STRING.equals(form.getUspsTot())){
					billingLineChargeVO = new BillingLineChargeVO();
					billingLineChargeVO.setApplicableRateCharge(form.getUspsTot()) ;
					billingLineChargeVO.setFrmWgt(5);
					//billingLineChargeVO.setOperationalFlag("I");
					billingLineCharges.add(billingLineChargeVO);
				}
			}
	      if(form.getConDiscount()!=null){
				
				if(!EMPTY_STRING.equals(form.getConDiscount())){
					billingLineChargeVO = new BillingLineChargeVO();
					billingLineChargeVO.setApplicableRateCharge(form.getConDiscount()) ;
					billingLineChargeVO.setFrmWgt(6);
					//billingLineChargeVO.setOperationalFlag("I");
					billingLineCharges.add(billingLineChargeVO);
				}
			}
		}
		
		if(billingLineCharges!=null && billingLineCharges.size()>0){
			billingLineDetailVO.setBillingLineCharges(billingLineCharges);
		}
	}
}
