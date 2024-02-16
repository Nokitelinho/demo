/*
 * ListCommand.java Created on Feb 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN66Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class ListCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("AirLineBillingInward ListCommand");

	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";

	private static final String ACTION_SUCCESS = "screenload_success";

	private static final String ACTION_FAILURE = "screenload_failure";

	private static final String INVREFNUM_MANDATORY = "mailtracking.mra.airlinebilling.msg.err.invrefnomandatory";

	private static final String CLEARANCEPERIOD_MANDATORY = "mailtracking.mra.airlinebilling.msg.err.clearanceperiodmandatory";

	private static final String AIRLINECODE_MANDATORY = "mailtracking.mra.airlinebilling.msg.err.airlinecodemandatory";

	private static final String BILLINGTYPE_MANDATORY = "mailtracking.mra.airlinebilling.msg.err.billingtypemandatory";

	private static final String CLRPRD_INVALID= "mailtracking.mra.airlinebilling.error.invalidClearancePeriod";

	private static final String NO_RESULTS = "mailtracking.mra.airlinebilling.msg.err.noresults";

	private static final String NO_DATA = "mailtracking.mra.airlinebilling.msg.err.nodata";

	private static final String DESP_STATUSNEW = "N";

	private static final String DESP_STATUSUNBAL = "U";

	private static final String DESP_STATUSPROCESSERROR = "W";

	private static final String SCREENSTATUS_LIST = "list";

	private static final String SCREENSTATUS_NOSAVE = "nosave";

	private static final String SCREENSTATUS_DISABLEALL = "disableall";

	private static final String SCREENSTATUS_OUTWARD = "outward";

	private static final String SCREENSTATUS_PROERROR = "errors";

	private static final String MAILSUBCLASS_CP = "CP";

	private static final String MAILSUBCLASS_LC = "LC";

	private static final String MAILSUBCLASS_SAL = "SAL";

	private static final String MAILSUBCLASS_ULD = "UL";

	private static final String MAILSUBCLASS_SV = "SV";

	private static final String MAILSUBCLASS_EMS ="EMS";
	private static final String FROM_CRA ="fromCraPostingDetails";

	private static final String KEY_BILLING_TYPE_ONETIME = "mailtracking.mra.billingtype";
	private static final String SYS_PARA_INVOICESUMMARY_ENABLED = "mailtracking.mra.airlinebilling.isinvoicesummaryenabled";
	private static final String ONE_TIME_FIELDTYPE_BLGTYP = "mailtracking.mra.typebilling";//added by a-7929 as part of ICRD-265471
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		double lc =0.0;
		double cp =0.0;
		double sal=0.0;
		double uld=0.0;
		double sv=0.0;
		double ems=0.0;
		double wt=0.0;
		Money moneyChg=null;
		Money summaryChg=null;

		log.entering(CLASS_NAME, "execute");
		CaptureCN66Session session = (CaptureCN66Session) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		CaptureCN66Form form = (CaptureCN66Form) invocationContext.screenModel;
		AirlineCN66DetailsFilterVO filterVO = new AirlineCN66DetailsFilterVO();
		HashMap<String, Collection<AirlineCN66DetailsVO>> cn66detailsmap = new HashMap<String, Collection<AirlineCN66DetailsVO>>();

		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		boolean isListOnly = false;
		Map<String, String> systemParameters = null;
		if ("cn51screen".equals(form.getFromScreenStatus()) || "processed".equals(form.getFromScreenStatus())
				||("listacc").equals(form.getFromScreenStatus())) {
			if (session.getCn66FilterVO() != null) {
				String companyCode = getApplicationSession().getLogonVO()
				.getCompanyCode();
				form.setDisplayPage("1");
				form.setLastPageNum("0");
				filterVO = session.getCn66FilterVO();

			}
			form.setScreenStatus(SCREENSTATUS_DISABLEALL);
			form.setInvoiceRefNo(filterVO.getInvoiceRefNumber());
			form.setClearancePeriod(filterVO.getClearancePeriod());
			form.setAirlineCode(filterVO.getAirlineCode());
			form.setAirlineIdentifier(filterVO.getAirlineId());		//Added by A-5945 for ICRD-101324
			form.setCategory(filterVO.getCategory());
			form.setCarriageFromFilter(filterVO.getCarriageFrom());
			form.setCarriageToFilter(filterVO.getCarriageTo());
			form.setDespatchStatusFilter(filterVO.getDespatchStatus());
			form.setBillingType(filterVO.getInterlineBillingType());
			form.setTypeBilling(filterVO.getBillingType()); //added by A-7929 as part of ICRD-265471
			session.removeCn66DetailsMap();
		}
		else if(FROM_CRA.equals(form.getFromScreenStatus())) {
			log.log(Log.INFO, "inside LISTCOMMAND==FromScreenStatus==>>>>",
					form.getFromScreenStatus());
			if (session.getCn66FilterVO() != null) {
				String companyCode = getApplicationSession().getLogonVO()
				.getCompanyCode();
				form.setDisplayPage("1");
				form.setLastPageNum("0");
				filterVO = session.getCn66FilterVO();
				session.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>)
						fetchOneTimeDetails(companyCode));
				log
						.log(Log.INFO, "inside LISTCOMMAND filterVO==>>>>",
								filterVO);
				log.log(Log.INFO, "inside LISTCOMMAND OneTimeVOs==>>>>",
						session.getOneTimeVOs());
			}
			//form.setScreenStatus(SCREENSTATUS_DISABLEALL);
			form.setInvoiceRefNo(filterVO.getInvoiceRefNumber());
			form.setClearancePeriod(filterVO.getClearancePeriod());
			form.setAirlineCode(filterVO.getAirlineCode());
			form.setBillingType(filterVO.getInterlineBillingType());
			form.setTypeBilling(filterVO.getBillingType()); //added by A-7929 as part of ICRD-265471
			session.setParentId(FROM_CRA);
		}
		else {
			String invoiceRefNo = form.getInvoiceRefNo();
			String clearancePeriod = form.getClearancePeriod();
			String airlineCode = form.getAirlineCode();
			String category = form.getCategory();
			String carriageFrom = form.getCarriageFromFilter();
			String carriageTo = form.getCarriageToFilter();
			String despatchStatus = form.getDespatchStatusFilter();
			String billingType = form.getBillingType().trim();
            String typeBilling = form.getTypeBilling().trim(); //added by A-7929 as part of ICRD-265471

			String companyCode = getApplicationSession().getLogonVO()
			.getCompanyCode();
			filterVO.setCompanyCode(companyCode);

			if (invoiceRefNo != null && invoiceRefNo.trim().length() > 0) {
				filterVO.setInvoiceRefNumber(invoiceRefNo.toUpperCase());
			} else {
				error = new ErrorVO(INVREFNUM_MANDATORY);
				errors.add(error);
			}
			if (clearancePeriod != null && clearancePeriod.trim().length() > 0) {
				log.log(log.INFO,"inside ClearancePeriod");
				IATACalendarVO   iatacalendarvo = null;
				IATACalendarFilterVO iatacalendarfiltervo = new IATACalendarFilterVO();
				iatacalendarfiltervo.setCompanyCode(logonAttributes.getCompanyCode());
				iatacalendarfiltervo.setClearancePeriod(clearancePeriod);
				try{

					iatacalendarvo = delegate.validateClearancePeriod(iatacalendarfiltervo);
					log
							.log(Log.INFO, "iatacalendarvo obtained",
									iatacalendarvo);
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}

				if(iatacalendarvo!=null ){
					filterVO.setClearancePeriod(clearancePeriod);

				}
				else{
					log.log(log.INFO,"iatacalendarvo null");
					ErrorVO err=new ErrorVO(CLRPRD_INVALID);
					err.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(err);
				}
			} else {
				error = new ErrorVO(CLEARANCEPERIOD_MANDATORY);
				errors.add(error);
			}
			if (airlineCode != null && airlineCode.trim().length() > 0) {
				filterVO.setAirlineCode(airlineCode);
				try {
					AirlineValidationVO airlineValidationVO = new AirlineDelegate()
					.validateAlphaCode(companyCode, airlineCode
							.toUpperCase());
					filterVO.setAirlineId(airlineValidationVO
							.getAirlineIdentifier());
					form.setAirlineIdentifier(airlineValidationVO
							.getAirlineIdentifier());
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
			} else {
				error = new ErrorVO(AIRLINECODE_MANDATORY);
				errors.add(error);
			}
			if (category != null && category.trim().length() > 0) {
				isListOnly = true;
				filterVO.setCategory(category.toUpperCase());
			}
			if (carriageFrom != null && carriageFrom.trim().length() > 0) {
				isListOnly = true;
				filterVO.setCarriageFrom(carriageFrom.toUpperCase().trim());
			}
			if (carriageTo != null && carriageTo.trim().length() > 0) {
				isListOnly = true;
				filterVO.setCarriageTo(carriageTo.trim().toUpperCase().trim());
			}
			if (despatchStatus != null && despatchStatus.trim().length() > 0) {
				isListOnly = true;
				filterVO.setDespatchStatus(despatchStatus.toUpperCase());
			}
			if (billingType != null && billingType.trim().length() > 0) {
				filterVO.setInterlineBillingType(billingType.toUpperCase());
			} else {
				error = new ErrorVO(BILLINGTYPE_MANDATORY);
				errors.add(error);
			}
			if (typeBilling != null && typeBilling.trim().length() > 0) { //Added by A-7929 as part of ICRD-265471
				filterVO.setBillingType(typeBilling.toUpperCase());
			}
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = ACTION_FAILURE;
				log.exiting("MAILTRACKING_LIST", "ListCommand exit");
				return;
			}
			// System.out.println("filtervo"+filterVO);
			session.setCn66FilterVO(filterVO);
		}
		/**done by indu for cn51smy chk starts**/
		AirlineCN51FilterVO filter=new AirlineCN51FilterVO();
		filter.setCompanyCode(filterVO.getCompanyCode());
		filter.setAirlineIdentifier(filterVO.getAirlineId());
		filter.setInterlineBillingType(filterVO.getInterlineBillingType());
		filter.setInvoiceReferenceNumber(filterVO.getInvoiceRefNumber());
		filter.setIataClearancePeriod(filterVO.getClearancePeriod());
		String lstcurcod=null;
		if(("I").equals(form.getBillingType().toUpperCase())){


			try {
				lstcurcod = delegate
				.findInvoiceListingCurrency(filter);
			} catch (BusinessDelegateException e) {
				errors = handleDelegateException(e);
			}

			if(lstcurcod==null){

				ErrorVO err=new ErrorVO("mra.airlinebilling.defaults.capturecn51.msg.err.invnotcaptured");
				errors.add(err);
				invocationContext.addAllError(errors);
				invocationContext.target = ACTION_FAILURE;
				return;
			} else {
				form.setBlgCurCode(lstcurcod);
			}

		}

		//ArrayList<AirlineCN66DetailsVO> vos = new ArrayList<AirlineCN66DetailsVO>();
		Page<AirlineCN66DetailsVO> vos =null;
		filterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));  
		//filterVO.setTotalRecords(-1);
		try {
			vos =  delegate.findCN66Details(filterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}

		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = ACTION_FAILURE;
			log.exiting("MAILTRACKING_LIST", "ListCommand exit");
			return;
		}
		try{
			if (vos != null && vos.size() > 0) {
				if(("I").equals(form.getBillingType().toUpperCase())){
					moneyChg=CurrencyHelper.getMoney(lstcurcod);
					moneyChg.setAmount(0.0D);
				}
				else{
					moneyChg=CurrencyHelper.getMoney(vos.get(0).getCurCod());
					moneyChg.setAmount(0.0D);
					form.setBlgCurCode(vos.get(0).getCurCod());
				}

				if ("cn51screen".equals(form.getFromScreenStatus())) {
					form.setScreenStatus(SCREENSTATUS_DISABLEALL);
				} else {
					form.setScreenStatus(SCREENSTATUS_LIST);
				}
				ArrayList<AirlineCN66DetailsVO> keyValues = null;
				for (AirlineCN66DetailsVO vo : vos) {

					if(MAILSUBCLASS_CP.equals(vo.getMailSubClass())){
						cp=cp+vo.getTotalWeight();
					}
					if(MAILSUBCLASS_LC.equals(vo.getMailSubClass())){
						lc=lc+vo.getTotalWeight();
					}
					if(MAILSUBCLASS_SAL.equals(vo.getMailSubClass())){
						sal=sal+vo.getTotalWeight();
					}
					if(MAILSUBCLASS_ULD.equals(vo.getMailSubClass())){
						uld=uld+vo.getTotalWeight();
					}
					if(MAILSUBCLASS_SV.equals(vo.getMailSubClass())){
						sv=sv+vo.getTotalWeight();
					}
					if(MAILSUBCLASS_EMS.equals(vo.getMailSubClass())){
						ems=ems+vo.getTotalWeight();
					}
					if(vo.getTotalSummaryWeight()!=0.0){
						wt=vo.getTotalSummaryWeight();
					}
					if(vo.getAmount()!=null){
						moneyChg.plusEquals(vo.getAmount());

					}
					if(vo.getSummaryAmount()!=null){
						summaryChg=vo.getSummaryAmount();

					}
					if (!(SCREENSTATUS_DISABLEALL.equals(form.getScreenStatus()))) {

						if (DESP_STATUSNEW.equals(vo.getCn51Status())
								|| DESP_STATUSUNBAL.equals(vo.getDespatchStatus())
								|| vo.getCn51Status() == null) {

							if (isListOnly) {
								form.setScreenStatus(SCREENSTATUS_NOSAVE);
							} else {
								form.setScreenStatus(SCREENSTATUS_LIST);
							}
							if ((("O".equals(vo.getInterlineBillingType())))) {
								form.setScreenStatus(SCREENSTATUS_OUTWARD);
							}
						} else if (DESP_STATUSPROCESSERROR.equals(vo
								.getCn51Status())) {
							form.setScreenStatus(SCREENSTATUS_PROERROR);
						} else {
							form.setScreenStatus(SCREENSTATUS_NOSAVE);
						}
					}
					form.setCn51Status(vo.getCn51Status());
					form.setAirlineIdentifier(vo.getAirlineIdentifier());
					String key = new StringBuilder().append(vo.getCarriageFrom())
					.append("-").append(vo.getCarriageTo()).toString();
					AirlineValidationVO airlineValidationVo = null;
					try {
						airlineValidationVo = new AirlineDelegate().findAirline(vo
								.getCompanyCode(), vo.getFlightCarrierIdentifier());
					} catch (BusinessDelegateException businessDelegateException) {
						handleDelegateException(businessDelegateException);
					}
					if (airlineValidationVo != null) {
						vo.setFlightCarrierCode(airlineValidationVo.getAlphaCode());
					}
					// keyValues.add(key);
					if (!(cn66detailsmap.containsKey(key))) {
						keyValues = new ArrayList<AirlineCN66DetailsVO>();
						keyValues.add(vo);
						cn66detailsmap.put(key, keyValues);
					} else {
						cn66detailsmap.get(key).add(vo);
					}

				}



				session.setCn66DetailsMap(cn66detailsmap);
				session.setAirlineCN66DetailsVOs(vos);
				log.log(Log.INFO, "Page vos", session.getAirlineCN66DetailsVOs());


			} else {
				session.removeCn66DetailsMap();
				if (("cn51screen".equals(form.getFromScreenStatus()))
						|| (isListOnly)||"O".equals(filterVO.getInterlineBillingType())) {
					log.log(log.INFO, "no data---------->");
					error = new ErrorVO(NO_DATA);

				} else {

					log.log(log.INFO, "no results---------->");
					error = new ErrorVO(NO_RESULTS);
				}

				errors.add(error);
			}
		}
		catch(CurrencyException currencyException){
			log.log(Log.INFO,"CurrencyException found");
		}

		// Added by A-5526 as part of ICRD-46939
		try
		{
		form.setNetCPWeight(String.valueOf(UnitFormatter.getRoundedValue(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT, cp)));//Modified as part of ICRD-101112
		}catch(UnitException unitException) {
			unitException.getErrorCode();
		   }
		
		try
		{
		form.setNetLCWeight(String.valueOf(UnitFormatter.getRoundedValue(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT, lc)));
		}catch(UnitException unitException) {
			unitException.getErrorCode();
		   }
		
		try
		{
		form.setNetSalWeight(String.valueOf(UnitFormatter.getRoundedValue(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT, sal)));
		}catch(UnitException unitException) {
			unitException.getErrorCode();
		   }
		
		try
		{
		form.setNetUldWeight(String.valueOf(UnitFormatter.getRoundedValue(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT, uld)));
		}catch(UnitException unitException) {
			unitException.getErrorCode();
		   }
		
		try
		{
		form.setNetSVWeight(String.valueOf(UnitFormatter.getRoundedValue(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT, sv)));
		}catch(UnitException unitException) {
			unitException.getErrorCode();
		   }
		try
		{
		form.setNetEMSWeight(String.valueOf(UnitFormatter.getRoundedValue(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT, ems)));
		}catch(UnitException unitException) {
			unitException.getErrorCode();
		}

		//form.setNetWeight(String.valueOf(wt));
		try
		{
		form.setNetSummaryWeight(String.valueOf(UnitFormatter.getRoundedValue(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT, wt)));//Modified as part of ICRD-101112 ends
		}catch(UnitException unitException) {
			unitException.getErrorCode();
		   }
		
		//Added by A-5526 as part of ICRD-46939 Ends
		form.setNetChargeMoney(moneyChg);
		if(summaryChg!=null){
			form.setNetChargeMoneyDisp(String.valueOf(summaryChg.getAmount()));
		}
		//log.log(log.FINE, "netcharge**------->?>>"+form.getNetChargeMoneyDisp());
		form.setNetSummaryAmount(summaryChg);
		log.log(Log.FINE, "wt..", wt);
		log.log(Log.FINE, "summaryChg..", summaryChg);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = ACTION_FAILURE;
			log.exiting("MAILTRACKING_LIST", "ListCommand exit");
			return;
		}

		// to be reoved
		ArrayList<AirlineCN66DetailsVO> arrayvos = new ArrayList<AirlineCN66DetailsVO>();
		for(AirlineCN66DetailsVO airlineCN66DetailsVO:vos){

			arrayvos.add(airlineCN66DetailsVO);
		}
		log.log(Log.FINE, "arrayvos..", arrayvos);
		session.setCn66Details(arrayvos);
		form.setLinkStatus("Y");
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

	/**
	 * Helper method to get the one time data.
	 * @param companyCode String
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(String companyCode) {

		log.entering(CLASS_NAME, "fetchOneTimeDetails");
		Map<String, Collection<OneTimeVO>> hashMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();

		oneTimeList.add(KEY_BILLING_TYPE_ONETIME);
		oneTimeList.add(ONE_TIME_FIELDTYPE_BLGTYP); //Added by A-7929 as part of ICRD-265471

		try {
			hashMap = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, oneTimeList);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}

		log.exiting(CLASS_NAME, "fetchOneTimeDetails");
		return hashMap;
	}


}
