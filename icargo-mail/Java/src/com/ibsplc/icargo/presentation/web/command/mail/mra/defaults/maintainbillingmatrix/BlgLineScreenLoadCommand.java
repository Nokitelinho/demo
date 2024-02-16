/*
 * BlgLineScreenLoadCommand.java Created on Feb 28, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingmatrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineDetailVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
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
public class BlgLineScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "BlgLineScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.maintainbillingmatrix";

	private static final String SCREEN_SUCCESS = "screenload_success";

	private static final String KEY_RATING_BASIS_ONETIME = "mailtracking.mra.ratingBasis";

	private static final String KEY_BILLED_SECTOR_ONETIME = "mailtracking.mra.billingSector";

	private static final String KEY_BLG_CLASS = "mailtracking.defaults.mailclass";

	private static final String KEY_BLG_CATEGORY = "mailtracking.defaults.mailcategory";
	private static final String KEY_BLG_SURCHARGECHARGEHEAD = "mailtracking.mra.surchargeChargeHead";
	private static final String KEY_BLG_CHARGERATINGBASIS = "mailtracking.mra.chargeRatingBasis";
	private static final String FLATRATE ="FR";

	// private static final String ACNTYP_ADD = "ADD";

	private static final String ACNTYP_MODIFY = "MODIFY";

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

	private static final String CONST_ULDTYP = "ULDGRP";

	private static final String CONST_CLASS = "CLS";

	private static final String CONST_SUBCLS = "SUBCLS";
	
	private static final String CONST_TRASFEREDBY = "TRFBY";
	
	private static final String  CONST_TRANSFEREDPA = "TRFPOA";
	
	private static final String CONST_SUBCLS_GRP ="SUBCLSGRP";

	private static final String CONST_CAT = "CAT";

	private static final String CONST_FLTNUM = "FLTNUM";

	private static final String INC_FLAG = "N";

	private static final String EXC_FLAG = "Y";

	private static final String REV_FLAG = "R";

	private static final String EXP_FLAG = "E";

	private static final String REV_FLAG_VAL = "REVENUE";

	private static final String EXP_FLAG_VAL = "EXPENDITURE";

	private static final String KEY_BILLING_PARTY = "mailtracking.mra.billingparty";

	private static final String BLG_PRTY_POA = "P";

	private static final String BLG_PRTY_AIRLINE = "A";
	
	private static final String  MAIL_COMPANY = "MALCMP";
	
	private static final String CONST_AGENT = "AGT";//Added by a-7531 for icrd-224979 
	
	//Added by A-7540 as part of ICRD-232319
	private static final String CONST_ORGAIR="ORGARPCOD";
	private static final String CONST_DESAIR="DSTARPCOD";
	private static final String CONST_VIAPNT="VIAPNT";
	private static final String CONST_MALSRV="MALSRVLVL";
	private static final String CONST_PABUILT="POAFLG";
	
	private static final String CONST_UPLARP="UPLARPCOD";
	private static final String CONST_DISARP="DISARPCOD";
	private static final String CONST_FLNCAR="FLNCAR";
	
	
	/**
	 * 
	 * @author a-3447 for bug 28390
	 */
	private static final String MAIL_SUBCLS_GRP = "mailtracking.defaults.mailsubclassgroup";
	private static final String KEY_UNTCOD ="mail.mra.gpabilling.untcod";
	private static final String MAIL_SRVC_LEV = "mail.operations.mailservicelevels";
	
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
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Map oneTimeHashMap = null;
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		ArrayList<BillingLineVO> list = null;
		Page<BillingLineVO> tempList = null;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		// BillingLineVO sessionVO = null;
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();

		/** adding attributes to map for passing to SharedDefaultsDelegate */
		oneTimeActiveStatusList.add(KEY_RATING_BASIS_ONETIME);
		oneTimeActiveStatusList.add(KEY_BILLED_SECTOR_ONETIME);
		oneTimeActiveStatusList.add(KEY_BLG_CLASS);
		oneTimeActiveStatusList.add(KEY_BLG_CATEGORY);
		oneTimeActiveStatusList.add(KEY_BILLING_PARTY);
		oneTimeActiveStatusList.add(KEY_UNTCOD);
		oneTimeActiveStatusList.add(KEY_BLG_SURCHARGECHARGEHEAD);
		oneTimeActiveStatusList.add(KEY_BLG_CHARGERATINGBASIS);
		
		/**
		 * 
		 * @author a-3447
		 */
		oneTimeActiveStatusList.add(MAIL_SUBCLS_GRP);
		oneTimeActiveStatusList.add(MAIL_SRVC_LEV);
				
		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, oneTimeActiveStatusList);
		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors = handleDelegateException(e);
		}

		/** setting OneTime hashmap to session */
		// log.log(Log.INFO," the oneTimeHashMap after server call is
		// "+oneTimeHashMap);
		session.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) oneTimeHashMap);
		Collection<OneTimeVO> oneTimeVOs = populateRateBasis((Collection<OneTimeVO>) oneTimeHashMap.get(KEY_BLG_CHARGERATINGBASIS));
		session.setRatingBasis(oneTimeVOs);
		form.setCanClose("N");
		String actionType = form.getActionType();
		int selectedIndex = 0;
		log.log(Log.FINE, "Action type --->", actionType);
		if (ACNTYP_MODIFY.equals(actionType)) {

			if (form.getSelectedIndex() != null) {
				selectedIndex = Integer.parseInt(form.getSelectedIndex());
			}

			BillingLineVO billingLineVO = null;
			if (session.getBillingLineDetails() != null
					&& session.getBillingLineDetails().size() > 0) {
				list = new ArrayList<BillingLineVO>();
				tempList = session.getBillingLineDetails();
				for (BillingLineVO lineVO : tempList) {
					if (!AbstractVO.OPERATION_FLAG_DELETE.equals(lineVO
							.getOperationFlag())) {
						list.add(lineVO);
					}
				}
				billingLineVO = list.get(selectedIndex);
			} else {
				log.log(Log.FINE,
						"No collection of billing line vos in session");
			}
			if (billingLineVO != null) {
				if (REV_FLAG.equals(billingLineVO.getRevenueExpenditureFlag())) {
					if (billingLineVO.getPoaCode() != null) {
						form.setBillTo(billingLineVO.getPoaCode());
						form.setBillingParty(BLG_PRTY_POA);
					} else if (billingLineVO.getAirlineCode() != null) {
						form.setBillTo(billingLineVO.getAirlineCode());
						form.setBillingParty(BLG_PRTY_AIRLINE);
					}
				} else if (EXP_FLAG.equals(billingLineVO
						.getRevenueExpenditureFlag())) {
					form.setBillBy(billingLineVO.getAirlineCode());
				}
				form.setBilledSector(billingLineVO.getBillingSector());
				form.setBillingBasis(billingLineVO.getBillingBasis());
				// form.setBillTo(billingLineVO.get)*********
				if (billingLineVO.getValidityStartDate() != null) {
					form.setBlgLineValidFrom(new LocalDate(
							LocalDate.NO_STATION, Location.NONE, billingLineVO
									.getValidityStartDate(), false)
							.toDisplayDateOnlyFormat());
				}

				if (billingLineVO.getValidityEndDate() != null) {
					form.setBlgLineValidTo(new LocalDate(LocalDate.NO_STATION,
							Location.NONE, billingLineVO.getValidityEndDate(),
							false).toDisplayDateOnlyFormat());
				}
				/*BigDecimal rate = new BigDecimal(
						billingLineVO.getApplicableRate());
				if(rate != null){
				rate = rate.setScale(4, BigDecimal.ROUND_HALF_UP);
				form.setRate(rate.toString());
				}*/
				form.setCurrency(billingLineVO.getCurrencyCode());
				form.setUnitCode(billingLineVO.getUnitCode());
				if(billingLineVO.isTaxIncludedInRateFlag()){
					form.setIsTaxIncludedInRateFlag("Y");
				} else {
					form.setIsTaxIncludedInRateFlag("N");
				}
				
				if (billingLineVO.getRevenueExpenditureFlag() != null) {
					if (billingLineVO.getRevenueExpenditureFlag().trim()
							.equals(REV_FLAG)) {
						form.setRevExp(REV_FLAG_VAL);
					} else if (billingLineVO.getRevenueExpenditureFlag().trim()
							.equals(EXP_FLAG)) {
						form.setRevExp(EXP_FLAG_VAL);
					}
				}
				Collection<BillingLineParameterVO> parameters = billingLineVO
						.getBillingLineParameters();
				if (parameters != null && parameters.size() > 0) {
					populateParameterDetails(form, parameters, session);
				} else {
					log.log(Log.FINE, "#############....No parametrs obtained");
				}

				Collection<BillingLineDetailVO> billingLineDetails = billingLineVO
						.getBillingLineDetails();
				populateBillingLineChargeDetails(session, billingLineDetails,
						form);

			} else {
				log.log(Log.FINE, "No vo obtained");
				session.setBillingLineChargeDetails(null);
			}
		}else if("ADD".equals(actionType)){
			session.setBillingLineChargeDetails(null);
		}
		if (errors.size() > 0) {
			invocationContext.addAllError(errors);
		}
		invocationContext.target = SCREEN_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
/**
 * @author A-4809
 * @param oneTimeVOs
 */
	private Collection<OneTimeVO> populateRateBasis(Collection<OneTimeVO> oneTimeVOs) {
		Collection<OneTimeVO> populatedRateBasis = new ArrayList<OneTimeVO>();
		Collection<OneTimeVO> rateBasis = new ArrayList<OneTimeVO>();
		OneTimeVO flatrate = null;
		if(oneTimeVOs!= null && oneTimeVOs.size()>0){
			for(OneTimeVO onetimeVO :oneTimeVOs){
				if(onetimeVO.getFieldValue().equals(FLATRATE)){
					flatrate = new OneTimeVO();
					flatrate = onetimeVO;
				}else{
					rateBasis.add(onetimeVO);
				}
			}
		}
		populatedRateBasis.add(flatrate);
		populatedRateBasis.addAll(rateBasis);
		log.log(Log.FINE, "Populated rate Basis",populatedRateBasis);
		return populatedRateBasis;
	}

	private String getCatValue(String val, MaintainBillingMatrixSession session) {
		String desc = null;
		//Modified by A-7540 for ICRD-256179	
	    String SEPARATOR = ",";
		Collection<OneTimeVO> oneTimeVOs = session.getOneTimeVOs().get(
				"mailtracking.defaults.mailcategory");
		StringBuilder catBuilder = new StringBuilder();
		String[] category=val.split(",");
		for(String catCode : category){ 
		for (OneTimeVO oneTimeVO : oneTimeVOs) {
		if (oneTimeVO.getFieldValue().equalsIgnoreCase(catCode))
				desc = oneTimeVO.getFieldDescription();
			}
		catBuilder.append(desc).append(","); 
		}
		desc=catBuilder.toString();
		desc=desc.substring(0, desc.length() - SEPARATOR.length());
		return desc;
	}


	private String getClassValue(String val,
			MaintainBillingMatrixSession session) {
		String desc = null;
		String [] valArr=val.split(",");
		StringBuilder clsBuilder = new StringBuilder();
		Collection<OneTimeVO> oneTimeVOs = session.getOneTimeVOs().get(
				"mailtracking.defaults.mailclass");
		for (OneTimeVO oneTimeVO : oneTimeVOs) {
			for (String arrVal:valArr){
			if (oneTimeVO.getFieldValue().equalsIgnoreCase(arrVal)) {
				desc = oneTimeVO.getFieldDescription();
				clsBuilder.append(desc).append(",");
			}
			}
		}
		desc=clsBuilder.toString();
		desc=desc.substring(0, desc.length() -1);
		return desc;

	}

	private void populateParameterDetails(BillingLineForm form,
			Collection<BillingLineParameterVO> parameters,
			MaintainBillingMatrixSession session) {

		log.log(1, "Entering populateParameterDetails.....");

		String orgCountryInc = null;

		String orgCityInc = null;

		String orgRegionInc = null;

		String destCountryInc = null;

		String destRegionInc = null;

		String destCityInc = null;

		String upliftCountryInc = null;

		String upliftCityInc = null;

		String disCountryInc = null;

		String disCityInc = null;

		String categoryInc = null;

		String uldTypeInc = null;

		String billingClassInc = null;

		String subClassInc = null;

		String flightNumInc = null;
		
		String fltNumInc = null;
		String carrierCodeInc = null;	
		String transferedByInc = null;
		
		String transferedPAInc = null;
		
		String subClassgroupInc = null;

		String agentCodeInc =null;//Added by a-7531 for icrd-224979 
		//Added by a-7540
        String orgAirportInc=null;
		
		String desAirportInc=null;
		
		String viaPointInc=null;
		
		String mailServiceInc=null;

		// Excluded parametrs

		String orgCountryExc = null;

		String orgCityExc = null;

		String orgRegionExc = null;

		String destCountryExc = null;

		String destRegionExc = null;

		String destCityExc = null;

		String upliftCountryExc = null;

		String upliftCityExc = null;

		String disCountryExc = null;

		String disCityExc = null;

		String categoryExc = null;

		String uldTypeExc = null;

		String billingClassExc = null;

		String subClassExc = null;

		String flightNumExc = null;
		
		String fltNumExc = null;
		String carrierCodeExc = null;
		String transferedByExc = null;
		
		String transferedPAExc = null;
		
		String subClassgroupExc = null;
		
		String mailCompanyInc=null;
		
		String mailCompanyExc=null;

		String agentCodeExc =null;//Added by a-7531 for icrd-224979 
		//Added by A-7540
        String orgAirportExc=null;
		
		String desAirportExc=null;
		
		String viaPointExc=null;
		
		String mailServiceExc=null;
		
		String paBuilt=null;

		String uplAirportInc=null;			
		String disAirportInc=null;
		String uplAirportExc=null;			
		String disAirportExc=null;
		
		String flownCarrierInc=null;					
		String flownCarrierExc=null;
		
		
		if (parameters == null && parameters.size() == 0) {
			return;
		}
		log.log(Log.INFO, "Parameters oblained are.....", parameters);
		for (BillingLineParameterVO parameterVO : parameters) {
			
			log.log(Log.INFO, "ParameterCode.....",
					parameterVO.getParameterCode());
			if (CONST_ORGREG.equals(parameterVO.getParameterCode())
					&& parameterVO.getParameterValue() != null
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (orgRegionInc == null) {
						orgRegionInc = new StringBuilder(
								parameterVO.getParameterValue()).toString();
					} else {
						orgRegionInc = new StringBuilder(orgRegionInc)
								.append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (orgRegionExc == null) {
						orgRegionExc = new StringBuilder(
								parameterVO.getParameterValue()).toString();
					} else {
						orgRegionExc = new StringBuilder(orgRegionExc)
								.append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				}

			}

			if (CONST_ORGCNT.equals(parameterVO.getParameterCode())
					&& parameterVO.getParameterValue() != null
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (orgCountryInc == null) {
						orgCountryInc = new StringBuilder(
								parameterVO.getParameterValue()).toString();
					} else {
						orgCountryInc = new StringBuilder(orgCountryInc)
								.append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (orgCountryExc == null) {
						orgCountryExc = new StringBuilder(
								parameterVO.getParameterValue()).toString();
					} else {
						orgCountryExc = new StringBuilder(orgCountryExc)
								.append(",").append(
										parameterVO.getParameterValue())
								.toString();
					}
				}

			}
			if (CONST_ORGCTY.equals(parameterVO.getParameterCode())
					&& parameterVO.getParameterValue() != null
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (orgCityInc == null) {
						orgCityInc = new StringBuilder(
								parameterVO.getParameterValue()).toString();
					} else {
						orgCityInc = new StringBuilder(orgCityInc).append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (orgCityExc == null) {
						orgCityExc = new StringBuilder(
								parameterVO.getParameterValue()).toString();
					} else {
						orgCityInc = new StringBuilder(orgCityExc).append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				}

			}
			if (CONST_DSTREG.equals(parameterVO.getParameterCode())
					&& parameterVO.getParameterValue() != null
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (destRegionInc == null) {
						destRegionInc = new StringBuilder(
								parameterVO.getParameterValue()).toString();
					} else {
						destRegionInc = new StringBuilder(destRegionInc)
								.append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (destRegionExc == null) {
						destRegionExc = new StringBuilder(
								parameterVO.getParameterValue()).toString();
					} else {
						destRegionInc = new StringBuilder(destRegionExc)
								.append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				}

			}

			if (CONST_DSTCNT.equals(parameterVO.getParameterCode())&& parameterVO.getParameterValue()!= null
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (destCountryInc == null) {
						destCountryInc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						destCountryInc = new StringBuilder(destCountryInc)
								.append(",").append(
										parameterVO.getParameterValue())
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (destCountryExc == null) {
						destCountryExc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						destCountryInc = new StringBuilder(destCountryExc)
								.append(",").append(
										parameterVO.getParameterValue())
								.toString();
					}
				}

			}
			if (CONST_DSTCTY.equals(parameterVO.getParameterCode())&& parameterVO.getParameterValue()!= null
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (destCityInc == null) {
						destCityInc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						destCityInc = new StringBuilder(destCityInc)
								.append(",").append(
										parameterVO.getParameterValue())
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (destCityExc == null) {
						destCityExc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						destCityExc = new StringBuilder(destCityExc)
								.append(",").append(
										parameterVO.getParameterValue())
								.toString();
					}
				}

			}
			if (CONST_UPLCNT.equals(parameterVO.getParameterCode())&& parameterVO.getParameterValue()!= null
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (upliftCountryInc == null) {
						upliftCountryInc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						upliftCountryInc = new StringBuilder(upliftCountryInc)
								.append(",").append(
										parameterVO.getParameterValue())
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (upliftCountryExc == null) {
						upliftCountryExc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						upliftCountryExc = new StringBuilder(upliftCountryExc)
								.append(",").append(
										parameterVO.getParameterValue())
								.toString();
					}
				}

			}
			if (CONST_UPLCTY.equals(parameterVO.getParameterCode())&& parameterVO.getParameterValue()!= null
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (upliftCityInc == null) {
						upliftCityInc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						upliftCityInc = new StringBuilder(upliftCityInc)
								.append(",").append(
										parameterVO.getParameterValue())
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (upliftCityExc == null) {
						upliftCityExc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						upliftCityExc = new StringBuilder(upliftCityExc)
								.append(",").append(
										parameterVO.getParameterValue())
								.toString();
					}
				}

			}
			//Added by a-7531 for icrd-224979 start
			if (CONST_AGENT.equals(parameterVO.getParameterCode())
					&& parameterVO.getParameterValue() != null
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (agentCodeInc == null) {
						agentCodeInc = new StringBuilder(
								parameterVO.getParameterValue()).toString();
					} else {
						agentCodeInc = new StringBuilder(agentCodeInc)
								.append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (agentCodeExc == null) {
						agentCodeExc = new StringBuilder(
								parameterVO.getParameterValue()).toString();
					} else {
						agentCodeExc = new StringBuilder(agentCodeExc)
								.append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				}

			}
			//Added by a-7531 for icrd-224979 end
			
			//Added by a-7540
			if (CONST_ORGAIR.equals(parameterVO.getParameterCode())
					&& parameterVO.getParameterValue() != null
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (orgAirportInc == null) {
						orgAirportInc = new StringBuilder(
								parameterVO.getParameterValue()).toString();
					} else {
						orgAirportInc = new StringBuilder(orgAirportInc)
								.append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (orgAirportExc == null) {
						orgAirportExc = new StringBuilder(
								parameterVO.getParameterValue()).toString();
					} else {
						orgAirportExc = new StringBuilder(orgAirportExc)
								.append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				}

			}
			if (CONST_DESAIR.equals(parameterVO.getParameterCode())
					&& parameterVO.getParameterValue() != null
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if(desAirportInc == null) {
						desAirportInc = new StringBuilder(
								parameterVO.getParameterValue()).toString();
					} else {
						desAirportInc = new StringBuilder(desAirportInc)
								.append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (desAirportExc == null) {
						desAirportExc = new StringBuilder(
								parameterVO.getParameterValue()).toString();
					} else {
						desAirportExc = new StringBuilder(desAirportExc)
								.append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				}

			}
			if (CONST_VIAPNT.equals(parameterVO.getParameterCode())
					&& parameterVO.getParameterValue() != null
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if(viaPointInc == null) {
						viaPointInc = new StringBuilder(
								parameterVO.getParameterValue()).toString();
					} else {
						viaPointInc = new StringBuilder(viaPointInc)
								.append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (viaPointExc == null) {
						viaPointExc = new StringBuilder(
								parameterVO.getParameterValue()).toString();
					} else {
						viaPointExc = new StringBuilder(viaPointExc)
								.append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				}

			}
			if (CONST_DISCNT.equals(parameterVO.getParameterCode())&& parameterVO.getParameterValue()!= null
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (disCountryInc == null) {
						disCountryInc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						disCountryInc = new StringBuilder(disCountryInc)
								.append(",").append(
										parameterVO.getParameterValue())
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (disCountryExc == null) {
						disCountryExc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						destCountryExc = new StringBuilder(disCountryExc)
								.append(",").append(
										parameterVO.getParameterValue())
								.toString();
					}
				}

			}
			if (CONST_DISCTY.equals(parameterVO.getParameterCode())&& parameterVO.getParameterValue()!= null
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (disCityInc == null) {
						disCityInc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						disCityInc = new StringBuilder(disCityInc).append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (disCityExc == null) {
						disCityExc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						disCityExc = new StringBuilder(disCityExc).append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				}

			}
			if (CONST_FLTNUM.equals(parameterVO.getParameterCode())&& parameterVO.getParameterValue()!= null 
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (fltNumInc == null) {
						fltNumInc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
						carrierCodeInc=fltNumInc.substring(0,2);
						flightNumInc=fltNumInc.substring(2,parameterVO.getParameterValue().length());
					} else {
						disCountryInc = new StringBuilder(fltNumInc).append(
								",").append(parameterVO.getParameterValue())
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (fltNumExc == null) {
						fltNumExc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
						carrierCodeExc=fltNumExc.substring(0,2);
					flightNumExc=fltNumExc.substring(2,parameterVO.getParameterValue().length());
					} else {
						fltNumExc = new StringBuilder(fltNumExc).append(
								",").append(parameterVO.getParameterValue())
								.toString();
						flightNumExc=fltNumExc;
					}
				}

			}
			if (CONST_CLASS.equals(parameterVO.getParameterCode())&& parameterVO.getParameterValue()!= null && 
					parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (billingClassInc == null) {
						// billingClassInc = new
						// StringBuilder(parameterVO.getParameterValue()).toString();
						billingClassInc = new StringBuilder(getClassValue(
								parameterVO.getParameterValue(), session))
								.toString();
					} else {
						// billingClassInc = new
						// StringBuilder(billingClassInc).append(",").
						// append(parameterVO.getParameterValue()).toString();
						billingClassInc = new StringBuilder(billingClassInc)
								.append(",").append(
										getClassValue(parameterVO
												.getParameterValue(), session))
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (billingClassExc == null) {
						// billingClassExc = new
						// StringBuilder(parameterVO.getParameterValue()).toString();
						billingClassExc = new StringBuilder(getClassValue(
								parameterVO.getParameterValue(), session))
								.toString();
					} else {
						// billingClassExc = new
						// StringBuilder(billingClassExc).append(",").
						// append(parameterVO.getParameterValue()).toString();
						billingClassExc = new StringBuilder(billingClassExc)
								.append(",").append(
										getClassValue(parameterVO
												.getParameterValue(), session))
								.toString();
					}
				}

			}
			if (CONST_SUBCLS.equals(parameterVO.getParameterCode())&& parameterVO.getParameterValue()!= null
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (subClassInc == null) {
						subClassInc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						subClassInc = new StringBuilder(subClassInc)
								.append(",").append(
										parameterVO.getParameterValue())
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (subClassExc == null) {
						subClassExc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						subClassExc = new StringBuilder(subClassExc)
								.append(",").append(
										parameterVO.getParameterValue())
								.toString();
					}
				}

			}
			if (CONST_CAT.equals(parameterVO.getParameterCode()) && parameterVO.getParameterValue()!= null
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (categoryInc == null ) {
						// categoryInc = new
						// StringBuilder(parameterVO.getParameterValue()).toString();
						
						categoryInc = new StringBuilder(getCatValue(parameterVO
								.getParameterValue(), session)).toString();
					} else {
						// categoryInc = new
						// StringBuilder(categoryInc).append(",").
						// append(parameterVO.getParameterValue()).toString();
						// categoryInc = new
						// StringBuilder(getCatValue(parameterVO.getParameterValue(),session)).toString();
						categoryInc = new StringBuilder(categoryInc)
								.append(",").append(
										getCatValue(parameterVO
												.getParameterValue(), session))
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (categoryExc == null) {
						// categoryExc = new
						// StringBuilder(parameterVO.getParameterValue()).toString();
						categoryExc = new StringBuilder(getCatValue(parameterVO
								.getParameterValue(), session)).toString();
					} else {
						// categoryExc = new
						// StringBuilder(categoryExc).append(",").
						// append(parameterVO.getParameterValue()).toString();
						categoryExc = new StringBuilder(categoryExc)
								.append(",").append(
										getCatValue(parameterVO
												.getParameterValue(), session))
								.toString();
					}
				}

			}
			if (CONST_ULDTYP.equals(parameterVO.getParameterCode())&& parameterVO.getParameterValue()!= null
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (uldTypeInc == null) {
						uldTypeInc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						uldTypeInc = new StringBuilder(uldTypeInc).append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (uldTypeExc == null) {
						uldTypeExc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						uldTypeExc = new StringBuilder(uldTypeExc).append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				}

			}
			if (CONST_TRASFEREDBY.equals(parameterVO.getParameterCode())&& parameterVO.getParameterValue()!= null
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (transferedByInc == null) {
						transferedByInc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						transferedByInc = new StringBuilder(transferedByInc).append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (transferedByExc == null) {
						transferedByExc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						transferedByExc = new StringBuilder(transferedByExc).append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				}

			}
			if (CONST_TRANSFEREDPA.equals(parameterVO.getParameterCode())&& parameterVO.getParameterValue()!= null
					&& parameterVO.getParameterValue().length()>0){
				if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (transferedPAInc == null) {
						transferedPAInc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						transferedPAInc = new StringBuilder(transferedPAInc).append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
					if (transferedPAExc == null) {
						transferedPAExc = new StringBuilder(parameterVO
								.getParameterValue()).toString();
					} else {
						transferedPAExc = new StringBuilder(transferedPAExc).append(",")
								.append(parameterVO.getParameterValue())
								.toString();
					}
				}

			}
		
		if (CONST_SUBCLS_GRP.equals(parameterVO.getParameterCode())&& parameterVO.getParameterValue()!= null
				&& parameterVO.getParameterValue().length()>0){
			if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
				if (subClassgroupInc == null) {
					subClassgroupInc = new StringBuilder(parameterVO
							.getParameterValue()).toString();
				} else {
					subClassgroupInc = new StringBuilder(subClassgroupInc).append(",")
							.append(parameterVO.getParameterValue())
							.toString();
				}
			} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
				if (subClassgroupExc == null) {
					subClassgroupExc = new StringBuilder(parameterVO
							.getParameterValue()).toString();
				} else {
					subClassgroupExc = new StringBuilder(subClassgroupExc).append(",")
							.append(parameterVO.getParameterValue())
							.toString();
				}
			}

		}
		//added by a7540
		if (CONST_MALSRV.equals(parameterVO.getParameterCode())&& parameterVO.getParameterValue()!= null
		&& parameterVO.getParameterValue().length()>0){
	if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
		if (mailServiceInc == null) {
			mailServiceInc = new StringBuilder(parameterVO
					.getParameterValue()).toString();
		} else {
			mailServiceInc = new StringBuilder(mailServiceInc).append(",")
					.append(parameterVO.getParameterValue())
					.toString();
		}
	} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
		if (mailServiceExc == null) {
			mailServiceExc = new StringBuilder(parameterVO
					.getParameterValue()).toString();
		} else {
			mailServiceExc = new StringBuilder(mailServiceExc).append(",")
					.append(parameterVO.getParameterValue())
					.toString();
		}
	}

}
		if (CONST_PABUILT.equals(parameterVO.getParameterCode())&& parameterVO.getParameterValue()!= null
				&& parameterVO.getParameterValue().length()>0){
			if(paBuilt == null) {
				paBuilt=new StringBuilder(parameterVO
						.getParameterValue()).toString();
			}
			else{
				paBuilt = new StringBuilder(paBuilt).append(",")
				.append(parameterVO.getParameterValue())
				.toString();
		}
		}
		
		if ("MALCMPCOD".equals(parameterVO.getParameterCode())&& parameterVO.getParameterValue()!= null
				&& parameterVO.getParameterValue().length()>0){
			if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
				if (mailCompanyInc == null) {
					mailCompanyInc = new StringBuilder(parameterVO
							.getParameterValue()).toString();
				} else {
					mailCompanyInc = new StringBuilder(mailCompanyInc).append(",")
							.append(parameterVO.getParameterValue())
							.toString();
				}
			} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
				if (mailCompanyExc == null) {
					mailCompanyExc = new StringBuilder(parameterVO
							.getParameterValue()).toString();
				} else {
					mailCompanyExc = new StringBuilder(mailCompanyExc).append(",")
							.append(parameterVO.getParameterValue())
							.toString();
				}
			}

		}
		
		if (CONST_UPLARP.equals(parameterVO.getParameterCode())
				&& parameterVO.getParameterValue() != null
				&& parameterVO.getParameterValue().length()>0){
			if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
				if (uplAirportInc == null) {
					uplAirportInc = new StringBuilder(
							parameterVO.getParameterValue()).toString();
				} else {
					uplAirportInc = new StringBuilder(uplAirportInc)
							.append(",")
							.append(parameterVO.getParameterValue())
							.toString();
				}
			} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
				if (uplAirportExc == null) {
					uplAirportExc = new StringBuilder(
							parameterVO.getParameterValue()).toString();
				} else {
					uplAirportExc = new StringBuilder(uplAirportExc)
							.append(",")
							.append(parameterVO.getParameterValue())
							.toString();
				}
			}

		}
		if (CONST_DISARP.equals(parameterVO.getParameterCode())
				&& parameterVO.getParameterValue() != null
				&& parameterVO.getParameterValue().length()>0){
			if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
				if (disAirportInc == null) {
					disAirportInc = new StringBuilder(
							parameterVO.getParameterValue()).toString();
				} else {
					disAirportInc = new StringBuilder(disAirportInc)
							.append(",")
							.append(parameterVO.getParameterValue())
							.toString();
				}
			} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
				if (disAirportExc == null) {
					disAirportExc = new StringBuilder(
							parameterVO.getParameterValue()).toString();
				} else {
					disAirportExc = new StringBuilder(disAirportExc)
							.append(",")
							.append(parameterVO.getParameterValue())
							.toString();
				}
			}

		}
		
		if (CONST_FLNCAR.equals(parameterVO.getParameterCode())
				&& parameterVO.getParameterValue() != null
				&& parameterVO.getParameterValue().length()>0){
			if (INC_FLAG.equals(parameterVO.getExcludeFlag())) {
				if (flownCarrierInc == null) {
					flownCarrierInc = new StringBuilder(
							parameterVO.getParameterValue()).toString();
				} else {
					flownCarrierInc = new StringBuilder(flownCarrierInc)
							.append(",")
							.append(parameterVO.getParameterValue())
							.toString();
				}
			} else if (EXC_FLAG.equals(parameterVO.getExcludeFlag())) {
				if (flownCarrierExc == null) {
					flownCarrierExc = new StringBuilder(
							parameterVO.getParameterValue()).toString();
				} else {
					flownCarrierExc = new StringBuilder(flownCarrierExc)
							.append(",")
							.append(parameterVO.getParameterValue())
							.toString();
				}
			}

		}
		
		
	}

		log.log(1, "Collection of parameters iteration completed.....");
		
		log.log(Log.INFO, "billingClassInc.....", billingClassInc);
		if (orgRegionInc != null) {
			form.setOrgRegInc(orgRegionInc);
		} else {
			form.setOrgRegInc("-");
		}

		if (orgRegionExc != null) {
			form.setOrgRegExc(orgRegionExc);
		} else {
			form.setOrgRegExc("-");
		}

		if (orgCountryInc != null) {
			form.setOrgCntInc(orgCountryInc);
		} else {
			form.setOrgCntInc("-");
		}

		if (orgCountryExc != null) {
			form.setOrgCntExc(orgCountryExc);
		} else {
			form.setOrgCntExc("-");
		}

		if (orgCityInc != null) {
			form.setOrgCtyInc(orgCityInc);
		} else {
			form.setOrgCtyInc("-");
		}

		if (orgCityExc != null) {
			form.setOrgCtyExc(orgCityExc);
		} else {
			form.setOrgCtyExc("-");
		}

		if (destRegionInc != null) {
			form.setDesRegInc(destRegionInc);
		} else {
			form.setDesRegInc("-");
		}

		if (destRegionExc != null) {
			form.setDesRegExc(destRegionExc);
		} else {
			form.setDesRegExc("-");
		}

		if (destCountryInc != null) {
			form.setDesCntInc(destCountryInc);
		} else {
			form.setDesCntInc("-");
		}

		if (destCountryExc != null) {
			form.setDesCntExc(destCountryExc);
		} else {
			form.setDesCntExc("-");
		}

		if (destCityInc != null) {
			form.setDesCtyInc(destCityInc);
		} else {
			form.setDesCtyInc("-");
		}

		if (destCityExc != null) {
			form.setDesCtyExc(destCityExc);
		} else {
			form.setDesCtyExc("-");
		}

		if (upliftCountryExc != null) {
			form.setUplCntExc(upliftCountryExc);
		} else {
			form.setUplCntExc("-");
		}

		if (upliftCountryInc != null) {
			form.setUplCntInc(upliftCountryInc);
		} else {
			form.setUplCntInc("-");
		}

		if (upliftCityExc != null) {
			form.setUplCtyExc(upliftCityExc);
		} else {
			form.setUplCtyExc("-");
		}

		if (upliftCityInc != null) {
			form.setUplCtyInc(upliftCityInc);
		} else {
			form.setUplCtyInc("-");
		}

		if (disCityInc != null) {
			form.setDisCtyInc(disCityInc);
		} else {
			form.setDisCtyInc("-");
		}

		if (disCityExc != null) {
			form.setDisCtyExc(disCityExc);
		} else {
			form.setDisCtyExc("-");
		}

		if (disCountryInc != null) {
			form.setDisCntInc(disCountryInc);
		} else {
			form.setDisCntInc("-");
		}

		if (disCountryExc != null) {
			form.setDisCntExc(disCountryExc);
		} else {
			form.setDisCntExc("-");
		}

		if (categoryInc != null) {
			form.setCatInc(categoryInc);
		} else {
			form.setCatInc("-");
		}

		if (categoryExc != null) {
			form.setCatExc(categoryExc);
		} else {
			form.setCatExc("-");
		}

		if (billingClassInc != null) {
			form.setClassInc(billingClassInc);
		} else {
			form.setClassInc("-");
		}

		if (billingClassExc != null) {
			form.setClassExc(billingClassExc);
		} else {
			form.setClassExc("-");
		}

		if (subClassInc != null) {
			form.setSubClassInc(subClassInc);
		} else {
			form.setSubClassInc("-");
		}

		if (subClassExc != null) {
			form.setSubClassExc(subClassExc);
		} else {
			form.setSubClassExc("-");
		}

		if (flightNumInc != null) {
			form.setFlightNumberInc(flightNumInc);
		} else {
			form.setFltNumInc("-");
		}
		if (carrierCodeInc != null) {
			form.setCarrierCodeInc(carrierCodeInc);
		} else {
			form.setCarrierCodeInc("-");
		}

		if (flightNumExc != null) {
			form.setFlightNumberExc(flightNumExc);
		} else {
			form.setFltNumExc("-");
		}
		if (carrierCodeExc != null) {
			form.setCarriercodeExc(carrierCodeExc);
		} else {
			form.setCarriercodeExc("-");
		}

		if (uldTypeInc != null) {
			form.setUldTypInc(uldTypeInc);
		} else {
			form.setUldTypInc("-");
		}

		if (uldTypeExc != null) {
			form.setUldTypExc(uldTypeExc);
		} else {
			form.setUldTypExc("-");
		}
		if (transferedByInc != null) {
			form.setTransferedByInc(transferedByInc);
		} else {
			form.setTransferedByInc("-");
		}
		
		if (transferedByExc != null) {
			form.setTransferedByExc(transferedByExc);
		} else {
			form.setTransferedByExc("-");
		}
		if (transferedPAInc != null) {
			form.setTransferedPAInc(transferedPAInc);
		} else {
			form.setTransferedPAInc("-");
		}
		if (transferedPAExc != null) {
			form.setTransferedPAExc(transferedPAExc);
		} else {
			form.setTransferedPAExc("-");
		}
		
		if (subClassgroupInc != null) {
			form.setSubClsInc(subClassgroupInc);
		} else {
			form.setSubClsInc("-");
		}

		if (subClassgroupExc != null) {
			form.setSubClsExc(subClassgroupExc);
		} else {
			form.setSubClsExc("-");
		}
		//Added by a-7531 for icrd-224979 start
		if (agentCodeExc != null) {
			form.setAgentCodeExc(agentCodeExc);
		} else {
			form.setAgentCodeExc("-");
		}
		if (agentCodeInc != null) {
			form.setAgentCodeInc(agentCodeInc);
		} else {
			form.setAgentCodeInc("-");
		}
		//Added by a-7531 for icrd-224979 end
		//added by a7540 starts
		if (orgAirportInc != null) {
			form.setOrgAirportInc(orgAirportInc);
		} else {
			form.setOrgAirportInc("-");
		}
		if (orgAirportExc != null) {
			form.setOrgAirportExc(orgAirportExc);
		} else {
			form.setOrgAirportExc("-");
		}
		if(desAirportInc != null){
			form.setDesAirportInc(desAirportInc);
		}else{
			form.setDesAirportInc("-");
			}
		if(desAirportExc != null){
			form.setDesAirportExc(desAirportExc);
		}else{
			form.setDesAirportExc("-");
			}
		if(viaPointInc !=null){
			form.setViaPointInc(viaPointInc);
		}else{
			form.setViaPointInc("-");
		}
		if(viaPointExc != null){
			form.setViaPointExc(viaPointExc);
		}else{
			form.setViaPointExc("-");
		}
			
		if(mailServiceInc != null){
			form.setMailServiceInc(mailServiceInc);
		}else{
			form.setMailServiceInc("-");
		}
		if(mailServiceExc != null){
			form.setMailServiceExc(mailServiceExc);
		}else{
			form.setMailServiceExc("-");
		}
		if(paBuilt !=null && "Y".equals(paBuilt))
			form.setPaBuilt("Y");  
		else
			form.setPaBuilt("N"); 
		//added by a7540 ends
		if(mailCompanyExc!=null)
			{
			form.setMailCompanyExc(mailCompanyExc);
			}
		else
			{
			form.setMailCompanyExc("-");
			}
		if(mailCompanyInc!=null)
			{
			form.setMailCompanyInc(mailCompanyInc);
			}
		else
			{
			form.setMailCompanyInc("-");
			}
		if (orgAirportInc != null) {
			form.setOrgAirportInc(orgAirportInc);
		} else {
			form.setOrgAirportInc("-");
		}
		if (orgAirportExc != null) {
			form.setOrgAirportExc(orgAirportExc);
		} else {
			form.setOrgAirportExc("-");
		}
		if (uplAirportInc != null) {
			form.setUplArpInc(uplAirportInc);
		} else {
			form.setUplArpInc("-");
		}
		if (uplAirportExc != null) {
			form.setUplArpExc(uplAirportExc);
		} else {
			form.setUplArpExc("-");
		}
		if (disAirportInc != null) {
			form.setDisArpInc(disAirportInc);
		} else {
			form.setDisArpInc("-");
		}
		if (disAirportExc != null) {
			form.setDisArpExc(disAirportExc);
		} else {
			form.setDisArpExc("-");
			}
		
		if (flownCarrierInc != null) {
			form.setFlownCarrierInc(flownCarrierInc);
		} else {
			form.setFlownCarrierInc("-");
		}
		if (flownCarrierExc != null) {
			form.setFlownCarrierExc(flownCarrierExc);
		} else {
			form.setFlownCarrierExc("-");
		}

		log.log(1, "Returning from populate parameters fubction....");
	}// end of private function

	/**
	 * 
	 * @author A-5255
	 * @param session
	 * @param billingLineDetails
	 */
	private void populateBillingLineChargeDetails(
			MaintainBillingMatrixSession session,
			Collection<BillingLineDetailVO> billingLineDetails,
			BillingLineForm form) {
		HashMap<String, BillingLineDetailVO> billingLineDetailVOMap = new HashMap<String, BillingLineDetailVO>();
		BillingLineChargeVO billingLineChargeVO = null;
		if (billingLineDetails != null) {
			for (BillingLineDetailVO billingLineDetailVO : billingLineDetails) {
				if (!"M".equals(billingLineDetailVO.getChargeType())) {
					form.setChargeTypeOther(billingLineDetailVO.getChargeType());
					form.setChargeHead(billingLineDetailVO.getChargeType());
					if ("FR".equals(billingLineDetailVO.getRatingBasis())) {
						if (billingLineDetailVO.getBillingLineCharges() != null
								&& billingLineDetailVO.getBillingLineCharges()
										.size() > 0) {
					billingLineChargeVO = billingLineDetailVO
					.getBillingLineCharges().iterator().next();
							if (billingLineChargeVO != null) {
								form.setRatingBasisOther(billingLineDetailVO
										.getRatingBasis());
					form.setFlatRateOther(Double
							.toString(billingLineChargeVO
									.getApplicableRateCharge()));
							}
						}
					} else if ("FC"
							.equals(billingLineDetailVO.getRatingBasis())) {
						if (billingLineDetailVO.getBillingLineCharges() != null
								&& billingLineDetailVO.getBillingLineCharges()
										.size() > 0) {
							billingLineChargeVO = billingLineDetailVO
									.getBillingLineCharges().iterator().next();
							if (billingLineChargeVO != null) {
								form.setRatingBasisOther(billingLineDetailVO
										.getRatingBasis());
								form.setFlatChargeOther(Double
										.toString(billingLineChargeVO
												.getApplicableRateCharge()));
							}
						}
					} else if ("WB"
							.equals(billingLineDetailVO.getRatingBasis())) {
						if (billingLineDetailVO.getBillingLineCharges() != null
								&& billingLineDetailVO.getBillingLineCharges()
										.size() > 0) {
							ArrayList<BillingLineChargeVO> billingLineChargeVOs = new ArrayList<BillingLineChargeVO>();
							form.setRatingBasisOther(billingLineDetailVO
									.getRatingBasis());
							for (BillingLineChargeVO billingLineChargesVO : billingLineDetailVO
									.getBillingLineCharges()) {
								if (billingLineChargesVO.getFrmWgt() != -1
										&& billingLineChargesVO.getFrmWgt() != 0) {
									billingLineChargeVOs
											.add(billingLineChargesVO);
								} else {
									if (billingLineChargesVO
											.getApplicableRateCharge() != 0) {
										if ("C".equals(billingLineChargesVO
												.getRateType())) {
											form.setMinimumChargeOther(Double
													.toString(billingLineChargesVO
															.getApplicableRateCharge()));
										}
										if ("R".equals(billingLineChargesVO
												.getRateType())) {
											form.setNormalRateOther(Double
													.toString(billingLineChargesVO
															.getApplicableRateCharge()));
										}
									}
								}
							}
							session.setBillingLineSurWeightBreakDetails(billingLineChargeVOs);
						}
					}
					billingLineDetailVOMap.put(
							billingLineDetailVO.getChargeType(),
							billingLineDetailVO);
				} else {
					if ("FR".equals(billingLineDetailVO.getRatingBasis())) {
						if (billingLineDetailVO.getBillingLineCharges() != null
								&& billingLineDetailVO.getBillingLineCharges()
										.size() > 0) {
							billingLineChargeVO = billingLineDetailVO
									.getBillingLineCharges().iterator().next();
							if (billingLineChargeVO != null) {
								form.setRatingBasisMail(billingLineDetailVO
										.getRatingBasis());
								form.setFlatRateMail(Double
										.toString(billingLineChargeVO
												.getApplicableRateCharge()));
							}
						}

					} else if ("FC"
							.equals(billingLineDetailVO.getRatingBasis())) {
						if (billingLineDetailVO.getBillingLineCharges() != null
								&& billingLineDetailVO.getBillingLineCharges()
										.size() > 0) {
							billingLineChargeVO = billingLineDetailVO
									.getBillingLineCharges().iterator().next();
							if (billingLineChargeVO != null) {
								form.setRatingBasisMail(billingLineDetailVO
										.getRatingBasis());
								form.setFlatChargeMail(Double
										.toString(billingLineChargeVO
												.getApplicableRateCharge()));
							}
						}
					} else if ("WB"
							.equals(billingLineDetailVO.getRatingBasis())) {
						if (billingLineDetailVO.getBillingLineCharges() != null
								&& billingLineDetailVO.getBillingLineCharges()
										.size() > 0) {
							ArrayList<BillingLineChargeVO> billingLineChargeVOs = new ArrayList<BillingLineChargeVO>();
							form.setRatingBasisMail(billingLineDetailVO
									.getRatingBasis());
							for (BillingLineChargeVO billingLineChargesVO : billingLineDetailVO
									.getBillingLineCharges()) {
								if (billingLineChargesVO.getFrmWgt() != -1
										&& billingLineChargesVO.getFrmWgt() != 0) {
									billingLineChargeVOs
											.add(billingLineChargesVO);
								} else {
									if (billingLineChargesVO
											.getApplicableRateCharge() != 0) {
										if ("C".equals(billingLineChargesVO
												.getRateType())) {
											form.setMinimumChargeMail(Double
													.toString(billingLineChargesVO
															.getApplicableRateCharge()));
										}
										if ("R".equals(billingLineChargesVO
												.getRateType())) {
											form.setNormalRateMail(Double
													.toString(billingLineChargesVO
															.getApplicableRateCharge()));
										}
									}
								}
							}
							session.setBillingLineMailWeightBreakDetails(billingLineChargeVOs);
						}
					}
					//Added for ICRD-232319 by a-7540
					else if("US"
							.equals(billingLineDetailVO.getRatingBasis())) {
						if (billingLineDetailVO.getBillingLineCharges() != null
								&& billingLineDetailVO.getBillingLineCharges()
										.size() > 0) {
							/*billingLineChargeVO = billingLineDetailVO
									.getBillingLineCharges().iterator().next();*/
							ArrayList<BillingLineChargeVO> billingLineChargeVOs = new ArrayList<BillingLineChargeVO>();
								form.setRatingBasisMail(billingLineDetailVO
										.getRatingBasis());
								for (BillingLineChargeVO billingLineChargesVO : billingLineDetailVO
										.getBillingLineCharges()) {
							  	if(billingLineChargesVO.getFrmWgt()==1)
							  	{
								form.setUspsRateOne(billingLineChargesVO
										.getApplicableRateCharge());
							  	}
							  	else if(billingLineChargesVO.getFrmWgt()==2)
							  	{
									form.setUspsRateTwo(billingLineChargesVO
											.getApplicableRateCharge());
							  	}
								else if(billingLineChargesVO.getFrmWgt()==3)
							  	{
								    form.setUspsRateThr(billingLineChargesVO
												.getApplicableRateCharge());
							  	}
								else if(billingLineChargesVO.getFrmWgt()==4)
							  	{
									form.setUspsRateFour(billingLineChargesVO
													.getApplicableRateCharge());
							  	}
								else if(billingLineChargesVO.getFrmWgt()==5)
							  	{
										form.setUspsTot(billingLineChargesVO
														.getApplicableRateCharge());
							  	}
								else{
										form.setConDiscount(billingLineChargesVO.getApplicableRateCharge());	
								}
							
						}
					}
					
					}
				}
				billingLineDetailVOMap.put(
						billingLineDetailVO.getChargeType(),
						billingLineDetailVO);
			}
		}
		if (billingLineDetailVOMap.size() > 0) {
			session.setBillingLineChargeDetails(billingLineDetailVOMap);
		}
	}
}// End of command class

