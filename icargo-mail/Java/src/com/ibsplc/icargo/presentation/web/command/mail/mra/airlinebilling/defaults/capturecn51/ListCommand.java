/*
 * ListCommand.java Created on Feb 21, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn51;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.vo.AreaValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.text.TextFormatter;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN51Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN51Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2105
 * 
 */
public class ListCommand extends BaseCommand {

	private Log log = LogFactory
	.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");

	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn51";

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String DISABLESTATUS = "DISABLE";

	private static final String DISABLEALLSTATUS = "DISABLEALL";

	private static final String CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";

	private static final String BILLINGTYPE_ONETIME = "mailtracking.mra.billingtype";

	private static final String STATUS_ONETIME = "mailtracking.mra.despatchstatus";
	private static final String CLRPRD_INVALID = "mra.airlinebilling.defaults.capturecn51.msg.err.clrprdinvalid";
	private static final String ENABLE="ENABLE";
	private static final String SYS_PARA_INVOICESUMMARY_ENABLED = "mailtracking.mra.airlinebilling.isinvoicesummaryenabled";

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
		AirlineCN51SummaryVO airlineCN51SummaryVO = null;
		int flag=0;
		double lc =0.0;
		double cp =0.0;
		double sal=0.0;
		double uld=0.0;
		double sv=0.0;
		double wt=0.0;
		double ems=0.0;
		Money moneyChg=null;
		try{
			moneyChg=CurrencyHelper.getMoney("USD");
			moneyChg.setAmount(0.0D);
		}
		catch(CurrencyException currencyException){
			log.log(Log.INFO,"CurrencyException found");
		}

		AirlineCN66DetailsFilterVO cN66FilterVO = new AirlineCN66DetailsFilterVO();
		CaptureCN51Session captureCN51Session = (CaptureCN51Session) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Map<String, String> systemParameters = null;
		//Added for Unit Components weight,volume
		UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
		captureCN51Session.setWeightRoundingVO(unitRoundingVO);
		captureCN51Session.setVolumeRoundingVO(unitRoundingVO);		
		setUnitComponent(logonAttributes.getStationCode(),captureCN51Session);	

		CaptureCN51Form captureCN51Form = (CaptureCN51Form) invocationContext.screenModel;
		captureCN51Form.setCn66Flag("");
		if (captureCN51Session.getOneTimeValues() == null
				|| captureCN51Session.getOneTimeValues().size() == 0) {

			SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
			Map<String, Collection<OneTimeVO>> oneTimeValues = null;
			try {

				oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
						logonAttributes.getCompanyCode(),
						getOneTimeParameterTypes());
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.FINE, "*****in the exception");
				businessDelegateException.getMessageVO().getErrors();
				handleDelegateException(businessDelegateException);
			}
			captureCN51Session
			.setOneTimeValues((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
		}
		Collection<ErrorVO> errors = null;
		if (("cn66").equals(captureCN51Form.getScreenFlag())
				|| ("viewcn51").equals(captureCN51Form.getScreenFlag())) {
			captureCN51Form.setDisplayPageNum("1");
			captureCN51Form.setLastPageNumber("0");
			populateToForm(captureCN51Form, captureCN51Session);
		}
		if (!("cn66").equals(captureCN51Form.getScreenFlag())
				&& !("viewcn51").equals(captureCN51Form.getScreenFlag())) { 
			log.log(Log.INFO, "Going to check mandatory");
			if(captureCN51Session.getFilterDetails()!=null){
				if(captureCN51Form.getAirlineCode()==null 
						|| captureCN51Form.getAirlineCode().trim().length()<=0){
					captureCN51Form.setAirlineCode(captureCN51Session.getFilterDetails().getAirlineCode());
				}
			}
			errors = checkMandatory(captureCN51Form);
		}
		if (errors != null && errors.size() > 0) {
			log
			.log(Log.INFO,
					"Errors !!!!!!!! after checking mandatory fields");
			captureCN51Form
			.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.addAllError(errors);
			captureCN51Session.setCn51Details(null);
			captureCN51Form.setNetChargeMoney(moneyChg);
			captureCN51Form.setNetSummaryMoney(moneyChg);
			if(moneyChg!=null){
				double amount = Double.parseDouble(TextFormatter.formatDouble(moneyChg.getAmount(),2));
				captureCN51Form.setNetChargeMoneyDisp(String.valueOf(amount));
			}
			invocationContext.target = LIST_FAILURE;
		} else {
			if (!("cn66").equals(captureCN51Form.getScreenFlag())
					&& !("viewcn51").equals(captureCN51Form.getScreenFlag())) {
				populateFilterVo(captureCN51Form, captureCN51Session);
				log.log(Log.INFO, "Going to validate fields");
				errors = validateForm(captureCN51Form, captureCN51Session);
			}
			if (errors != null && errors.size() > 0) {
				log.log(Log.INFO, "Errors !!!!!!!! after Validating fields");
				captureCN51Form
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
				invocationContext.addAllError(errors);
				captureCN51Session.setCn51Details(null);
				captureCN51Form.setNetChargeMoney(moneyChg);
				captureCN51Form.setNetSummaryMoney(moneyChg);
				if(moneyChg!=null){
					double amount = Double.parseDouble(TextFormatter.formatDouble(moneyChg.getAmount(),2));
					captureCN51Form.setNetChargeMoneyDisp(String.valueOf(amount));
				}
				invocationContext.target = LIST_FAILURE;
			} else {
				log.log(Log.INFO, "Going to populate filter");
				AirlineCN51FilterVO airlineCN51FilterVO = captureCN51Session
				.getFilterDetails();
				MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
				airlineCN51SummaryVO = null;
				log.log(Log.INFO, "Filter VO to server--->>>>",
						captureCN51Session.getFilterDetails());
				if ((airlineCN51FilterVO.getCarriageStationFrom() != null && airlineCN51FilterVO
						.getCarriageStationFrom().trim().length() > 0)
						|| (airlineCN51FilterVO.getCarriageStationTo() != null && airlineCN51FilterVO
								.getCarriageStationTo().trim().length() > 0)
								|| (airlineCN51FilterVO.getCategoryCode() != null && airlineCN51FilterVO
										.getCategoryCode().trim().length() > 0)) {
					captureCN51Form.setStatusFlag(DISABLESTATUS);
				} else if (("O").equals(captureCN51Session.getFilterDetails()
						.getInterlineBillingType())) {
					captureCN51Form.setStatusFlag(DISABLESTATUS);
				} else if (("cn66").equals(captureCN51Form.getScreenFlag())
						&& captureCN51Session.getParentId() != null
						&& captureCN51Session.getParentId().trim().length() > 0) {
					captureCN51Form.setStatusFlag(DISABLEALLSTATUS);
				} else if (("viewcn51").equals(captureCN51Form.getScreenFlag())
						&& captureCN51Session.getParentId() != null
						&& captureCN51Session.getParentId().trim().length() > 0) {
					captureCN51Form.setStatusFlag(DISABLEALLSTATUS);
				} else {

					captureCN51Form.setStatusFlag("");
				}
				/**done by indu for cn51smy chk starts**/				
				String lstcurcod=null;
				log.log(Log.INFO,"");
				if(("I").equals(captureCN51Form.getBillingType().toUpperCase())){
					
					try {
						lstcurcod = mailTrackingMRADelegate
						.findInvoiceListingCurrency(captureCN51Session
								.getFilterDetails());
					} catch (BusinessDelegateException e) {
						errors = handleDelegateException(e);
					}
					
					if(lstcurcod==null){
						captureCN51Form
						.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
						captureCN51Form.setLinkStatusFlag("disable");
						ErrorVO err=new ErrorVO("mra.airlinebilling.defaults.capturecn51.msg.err.invnotcaptured");
						errors.add(err);
						invocationContext.addAllError(errors);
						captureCN51Session.setCn51Details(null);
						captureCN51Form.setNetChargeMoney(moneyChg);
						captureCN51Form.setNetSummaryMoney(moneyChg);
						if(moneyChg!=null){
							double amount = Double.parseDouble(TextFormatter.formatDouble(moneyChg.getAmount(),2));
							captureCN51Form.setNetChargeMoneyDisp(String.valueOf(amount));
						}
						invocationContext.target = LIST_FAILURE;
						return;
					}
					
				}
				log.log(Log.FINE, "Filter VO From LIST Command===>>>",
						captureCN51Session
								.getFilterDetails());
				/**done by indu for cn51smy chk ends**/
				try {
					airlineCN51SummaryVO = mailTrackingMRADelegate
					.findCN51Details(captureCN51Session
							.getFilterDetails());
				} catch (BusinessDelegateException e) {
					errors = handleDelegateException(e);
				}
				if(airlineCN51SummaryVO !=null){
					airlineCN51SummaryVO.setCn51FilterVO(captureCN51Session
							.getFilterDetails());
				}
				log.log(Log.INFO, "airlineCN51SummaryVO....... ",
						airlineCN51SummaryVO);
				if(airlineCN51SummaryVO==null)  {
					ErrorVO errorVO = null;
					errorVO = new ErrorVO(
					"mra.airlinebilling.defaults.capturecn51.msg.err.noresults");
					errors.add(errorVO);
				}
				if (errors != null && errors.size() > 0) {
					captureCN51Form
					.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
					captureCN51Form.setLinkStatusFlag("disable");
					invocationContext.addAllError(errors);
					captureCN51Session.setCn51Details(null);
					captureCN51Form.setNetChargeMoney(moneyChg);
					captureCN51Form.setNetSummaryMoney(moneyChg);
					if(moneyChg!=null){
						double amount = Double.parseDouble(TextFormatter.formatDouble(moneyChg.getAmount(),2));
						captureCN51Form.setNetChargeMoneyDisp(String.valueOf(amount));
					}
					invocationContext.target = LIST_FAILURE;
				} else {
					try{

						errors = new ArrayList<ErrorVO>();
						if (airlineCN51SummaryVO!=null && airlineCN51SummaryVO.getCn51DetailsPageVOs().getActualPageSize()<=0) {
							/**change starts**/
							log.log(Log.INFO,"!!!!!Cn51DetailsVOs == null!!!!");
							if(("I").equals(captureCN51Form.getBillingType().toUpperCase())){
								moneyChg=CurrencyHelper.getMoney(lstcurcod);
								moneyChg.setAmount(0.0D);
								captureCN51Form.setBlgCurCode(lstcurcod);
								log.log(Log.INFO, "!!!!!BlgCurCode!!!!",
										captureCN51Form.getBlgCurCode());
								/**change ends**/
								/*captureCN51Session.setFilterDetails(null);
						captureCN51Session.setCn51Details(null);*/
								ErrorVO errorVO = null;
								if (!((DISABLESTATUS).equals(captureCN51Form
										.getStatusFlag()) || (DISABLEALLSTATUS)
										.equals(captureCN51Form.getStatusFlag()))||"cn66".equals(captureCN51Form.getScreenFlag())) {
									log.log(Log.INFO, "flag*****...", flag);
									//if(flag==0){
									errorVO = new ErrorVO(
									"mra.airlinebilling.defaults.capturecn51.msg.err.noresultsfound");
									errors.add(errorVO);
									//}
									/*AirlineCN51SummaryVO dummyvo = new AirlineCN51SummaryVO();
							dummyvo
									.setOperationFlag(AirlineCN51SummaryVO.OPERATION_FLAG_INSERT);
							dummyvo.setCompanycode(getApplicationSession()
									.getLogonVO().getCompanyCode());
							dummyvo.setAirlinecode(airlineCN51FilterVO
									.getAirlineCode());
							dummyvo.setAirlineidr(airlineCN51FilterVO
									.getAirlineIdentifier());
							dummyvo.setClearanceperiod(airlineCN51FilterVO
									.getIataClearancePeriod());
							dummyvo.setCn51status("N");
							dummyvo.setInterlinebillingtype(airlineCN51FilterVO
									.getInterlineBillingType());
							dummyvo.setInvoicenumber(airlineCN51FilterVO
									.getInvoiceReferenceNumber());

							if(flag>0){
								captureCN51Form.setCn66Flag("Y");
							dummyvo.setCn51DetailsVOs(cN51vos);
							dummyvo.setCn51DetailsPageVOs((Page<AirlineCN51DetailsVO>)cN51vos);

							}
							else{*/
									captureCN51Form.setCn66Flag("N");
									//}
									/*captureCN51Form.setNetCP(cp);
							captureCN51Form.setNetLC(lc);
							captureCN51Form.setNetSal(sal);
							captureCN51Form.setNetUld(uld);
							captureCN51Form.setNetSV(sv);
							captureCN51Form.setNetWeight(wt);
							captureCN51Form.setNetChargeMoney(moneyChg);
							captureCN51Form.setBlgCurCode(lstcurcod);
							captureCN51Session.setCn51Details(dummyvo);*/
								} 



								else {


									errorVO = new ErrorVO(
											"mra.airlinebilling.defaults.capturecn51.msg.err.noresults");
									errors.add(errorVO);
								}
								if (errors != null && errors.size() > 0) {
									if(flag!=0){
										log.log(Log.INFO, "flag....", flag);
										captureCN51Session.setCn51Details(null);
									}
									else{
										log
												.log(
														Log.INFO,
														"airlineCN51SummaryVO..if flag..0..",
														flag);
										airlineCN51SummaryVO.setOperationFlag(OPERATION_FLAG_INSERT);
										captureCN51Session.setCn51Details(airlineCN51SummaryVO);
									}
									log.log(Log.INFO, "flag..zero..", flag);
									invocationContext.addAllError(errors);

									captureCN51Form.setNetChargeMoney(moneyChg);
									invocationContext.target = LIST_FAILURE;
								}
								else{
									invocationContext.target = LIST_SUCCESS;
								}
							}
							else {
								ErrorVO errorVO = new ErrorVO(
								"mra.airlinebilling.defaults.capturecn51.msg.err.noresults");
								errors.add(errorVO);
								if (errors != null && errors.size() > 0) {

									invocationContext.addAllError(errors);
									captureCN51Session.setCn51Details(null);
									captureCN51Form.setNetChargeMoney(moneyChg);
									captureCN51Form.setNetSummaryMoney(moneyChg);
									if(moneyChg!=null){
										double amount = Double.parseDouble(TextFormatter.formatDouble(moneyChg.getAmount(),2));
										captureCN51Form.setNetChargeMoneyDisp(String.valueOf(amount));
									}
									invocationContext.target = LIST_FAILURE;
									return;
								}
							}

						} else {
							log.log(Log.INFO,"!!!!!airlineCN51DeatailsVO not null!!!!");
							if(("I").equals(captureCN51Form.getBillingType().toUpperCase())){
								moneyChg=CurrencyHelper.getMoney(lstcurcod);
								log.log(Log.INFO, "lstcurcod", lstcurcod);
								moneyChg.setAmount(0.0D);
								captureCN51Form.setBlgCurCode(lstcurcod);
							}
							else if(airlineCN51SummaryVO.getListingCurrency()!=null){

								moneyChg=CurrencyHelper.getMoney(airlineCN51SummaryVO.getListingCurrency());
								moneyChg.setAmount(0.0D);
								captureCN51Form.setBlgCurCode(airlineCN51SummaryVO.getListingCurrency());
							}

							airlineCN51SummaryVO
							.setOperationFlag(OPERATION_FLAG_UPDATE);
							if(airlineCN51SummaryVO.getCn51DetailsPageVOs()!=null){
								for(AirlineCN51DetailsVO c51vo:airlineCN51SummaryVO.getCn51DetailsPageVOs()){

									if(("I").equals(captureCN51Form.getBillingType().toUpperCase())){
										c51vo.setListingcurrencycode(lstcurcod);
									}
									else{
										c51vo.setListingcurrencycode(airlineCN51SummaryVO.getListingCurrency());
									}
									log.log(Log.INFO,
											"!!!!!ListingCurrencycode !!!!",
											airlineCN51SummaryVO.getListingCurrency());
									/**@author a-3447							
									 * added null check  for preventing sorry page
									 *  even if sub class is not configured in case of out wared billing 
									 */
									if(c51vo.getMailsubclass()!=null){
										if(("CP").equals(c51vo.getMailsubclass())){
											cp=cp+c51vo.getTotalweight();
										}
										if(("LC").equals(c51vo.getMailsubclass())){
											lc=lc+c51vo.getTotalweight();
										}
										if(("SAL").equals(c51vo.getMailsubclass())){
											sal=sal+c51vo.getTotalweight();
										}
										if(("UL").equals(c51vo.getMailsubclass())){
											uld=uld+c51vo.getTotalweight();
										}
										if(("SV").equals(c51vo.getMailsubclass())){
											sv=sv+c51vo.getTotalweight();
										}
										if("EMS".equals(c51vo.getMailsubclass())){
											ems=ems+c51vo.getTotalweight();
										}
										if(c51vo.getTotalweight()!=0.0){
											wt=wt+c51vo.getTotalweight();
										}
										if(c51vo.getTotalamountincontractcurrency()!=null){	
											moneyChg.plusEquals(c51vo.getTotalamountincontractcurrency());
										}		
									}

								}
							}  
							captureCN51Form.setNetCP(cp);
							captureCN51Form.setNetLC(lc);
							captureCN51Form.setNetSal(sal);
							captureCN51Form.setNetUld(uld);
							captureCN51Form.setNetSV(sv);
							captureCN51Form.setNetEMS(ems);
//							Added By A-3434 For Rounding NetWeight	  
							//BigDecimal bigDecimal = new BigDecimal(wt).setScale(2,RoundingMode.HALF_UP);
							captureCN51Form.setNetWeight(airlineCN51SummaryVO.getTotalWeight());
							//captureCN51Form.setNetWeight(String.valueOf(wt));
							captureCN51Form.setNetSummaryMoney(airlineCN51SummaryVO.getTotalCharge());					
							double totChgAmount = Double.parseDouble(TextFormatter.formatDouble(airlineCN51SummaryVO.getTotalCharge().getAmount(),2));
							captureCN51Form.setNetChargeMoneyDisp(String.valueOf(totChgAmount));				
							captureCN51Form.setNetChargeMoney(moneyChg);
							captureCN51Session.setCn51Details(airlineCN51SummaryVO);
							captureCN51Form	.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
							captureCN51Form.setStatusFlag(ENABLE);
							invocationContext.target = LIST_SUCCESS;
						}
						captureCN51Form.setLinkStatusFlag("enable");

					}
					catch(CurrencyException currencyException){
						log.log(Log.INFO,"CurrencyException found");
					}
				}




			}

		}

		captureCN51Form.setScreenFlag("");

		log.exiting(CLASS_NAME, "execute");

	}

	/**
	 * Method to populate filter fields to for variables
	 * 
	 * @param captureCN51Form
	 * @param captureCN51Session
	 */
	private void populateToForm(CaptureCN51Form captureCN51Form,
			CaptureCN51Session captureCN51Session) {
		AirlineCN51FilterVO airlineCN51FilterVO = captureCN51Session
		.getFilterDetails();
		captureCN51Form.setInvoiceRefNo(airlineCN51FilterVO
				.getInvoiceReferenceNumber());
		captureCN51Form.setAirlineCode(airlineCN51FilterVO.getAirlineCode());
		captureCN51Form.setClearancePeriod(airlineCN51FilterVO
				.getIataClearancePeriod());
		captureCN51Form.setBillingType(airlineCN51FilterVO
				.getInterlineBillingType());
		if (airlineCN51FilterVO.getCategoryCode() != null
				&& airlineCN51FilterVO.getCategoryCode().trim().length() > 0) {
			captureCN51Form.setCategory(airlineCN51FilterVO.getCategoryCode());
		}
		if (airlineCN51FilterVO.getCarriageStationFrom() != null
				&& airlineCN51FilterVO.getCarriageStationFrom().trim().length() > 0) {
			captureCN51Form.setCarriageFrom(airlineCN51FilterVO
					.getCarriageStationFrom());
		}
		if (airlineCN51FilterVO.getCarriageStationTo() != null
				&& airlineCN51FilterVO.getCarriageStationTo().trim().length() > 0) {
			captureCN51Form.setCarriageTo(airlineCN51FilterVO
					.getCarriageStationTo());
		}
		airlineCN51FilterVO.setPageNumber(Integer.parseInt(captureCN51Form.getDisplayPageNum()));
		captureCN51Form.setStatusFlag(DISABLESTATUS);
	}

	private Collection<ErrorVO> validateForm(CaptureCN51Form captureCN51Form,
			CaptureCN51Session captureCN51Session) {
		log.entering(CLASS_NAME, "validateForm");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		String companycode = getApplicationSession().getLogonVO()
		.getCompanyCode();
		if (captureCN51Form.getAirlineCode() != null
				&& captureCN51Form.getAirlineCode().trim().length() > 0) {
			AirlineDelegate airlineDelegate = new AirlineDelegate();
			AirlineValidationVO airlineValidationVO = null;

			StringBuffer invalidCodes = new StringBuffer("");
			String error = "";
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						companycode, captureCN51Form.getAirlineCode()
						.toUpperCase());
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
			if (airlineValidationVO == null) {
				invalidCodes.append(captureCN51Form.getAirlineCode());
				log.log(Log.INFO, "Invalid AirlineCode!!!!!!!");
				error = "mra.airlinebilling.defaults.capturecn51.msg.err.invalidairline";
				ErrorVO errorVO = new ErrorVO(error,
						new Object[] { invalidCodes.toString() });
				errors.add(errorVO);
			} else {
				captureCN51Session.getFilterDetails().setAirlineIdentifier(
						airlineValidationVO.getAirlineIdentifier());
			}
		}
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		if (captureCN51Form.getClearancePeriod() != null
				&& captureCN51Form.getClearancePeriod().trim().length() > 0) {
			log.log(log.INFO,"inside ClearancePeriod");
			IATACalendarVO    iatacalendarvo = null;
			IATACalendarFilterVO iatacalendarfiltervo = new IATACalendarFilterVO();
			iatacalendarfiltervo.setCompanyCode(companycode);
			iatacalendarfiltervo.setClearancePeriod(captureCN51Form.getClearancePeriod());
			try{

				iatacalendarvo = mailTrackingMRADelegate.validateClearancePeriod(iatacalendarfiltervo);
				
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
			
			if(iatacalendarvo==null ){  	
			
				log.log(Log.INFO,"iatacalendarvo null");
				ErrorVO err=new ErrorVO(CLRPRD_INVALID);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(err);
			}
		}
		AreaDelegate areaDelegate = new AreaDelegate();
		AreaValidationVO areaValidationVO = null;
		String companyCode = getApplicationSession().getLogonVO()
		.getCompanyCode();
		if (captureCN51Form.getCarriageFrom() != null
				&& captureCN51Form.getCarriageFrom().trim().length() > 0) {
			try {
				areaValidationVO = areaDelegate.validateStation(companyCode,
						captureCN51Form.getCarriageFrom().toUpperCase());
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
			if (areaValidationVO == null) {
				ErrorVO errorVO = new ErrorVO(
						"mra.airlinebilling.defaults.capturecn51.msg.err.invalidcarrfrom",
						new Object[] { captureCN51Form.getCarriageFrom() });
				errors.add(errorVO);
			}
		}
		if (captureCN51Form.getCarriageTo() != null
				&& captureCN51Form.getCarriageTo().trim().length() > 0) {
			try {
				areaValidationVO = areaDelegate.validateStation(companyCode,
						captureCN51Form.getCarriageTo().toUpperCase());
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
			if (areaValidationVO == null) {
				ErrorVO errorVO = new ErrorVO(
						"mra.airlinebilling.defaults.capturecn51.msg.err.invalidcarrto",
						new Object[] { captureCN51Form.getCarriageTo() });
				errors.add(errorVO);
			}
		}
		log.exiting(CLASS_NAME, "validateForm");
		return errors;
	}

	private Collection<ErrorVO> checkMandatory(CaptureCN51Form captureCN51Form) {
		log.entering(CLASS_NAME, "checkMandatory");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if (captureCN51Form.getInvoiceRefNo() == null
				|| captureCN51Form.getInvoiceRefNo().trim().length() == 0) {
			ErrorVO errorVO = new ErrorVO(
			"mra.airlinebilling.defaults.capturecn51.msg.err.invrefnomandatory");
			errors.add(errorVO);
		}
		if (captureCN51Form.getClearancePeriod() == null
				|| captureCN51Form.getClearancePeriod().trim().length() == 0) {
			ErrorVO errorVO = new ErrorVO(
			"mra.airlinebilling.defaults.capturecn51.msg.err.clearanceperiodmandatory");
			errors.add(errorVO);

		}
		if (captureCN51Form.getAirlineCode() == null
				|| captureCN51Form.getAirlineCode().trim().length() == 0) {
			ErrorVO errorVO = new ErrorVO(
			"mra.airlinebilling.defaults.capturecn51.msg.err.airlinecodemandatory");
			errors.add(errorVO);
		}
		log.exiting(CLASS_NAME, "checkMandatory");
		return errors;
	}

	/**
	 * Method to populate filter vo
	 * 
	 * @param captureCN51Form
	 * @param captureCN51Session
	 */
	private void populateFilterVo(CaptureCN51Form captureCN51Form,
			CaptureCN51Session captureCN51Session) {
		log.entering(CLASS_NAME, "populateFilterVo");
		AirlineCN51FilterVO airlineCN51FilterVO = new AirlineCN51FilterVO();
		airlineCN51FilterVO.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());
		airlineCN51FilterVO.setInvoiceReferenceNumber(captureCN51Form
				.getInvoiceRefNo().toUpperCase());
		airlineCN51FilterVO.setIataClearancePeriod(captureCN51Form
				.getClearancePeriod().toUpperCase());
		airlineCN51FilterVO.setAirlineCode(captureCN51Form.getAirlineCode()
				.toUpperCase());
		airlineCN51FilterVO.setCategoryCode(captureCN51Form.getCategory());
		airlineCN51FilterVO.setCarriageStationFrom(captureCN51Form
				.getCarriageFrom().toUpperCase());
		airlineCN51FilterVO.setCarriageStationTo(captureCN51Form
				.getCarriageTo().toUpperCase());
		airlineCN51FilterVO.setInterlineBillingType(captureCN51Form
				.getBillingType().toUpperCase());
		airlineCN51FilterVO.setPageNumber(Integer.parseInt(captureCN51Form
				.getDisplayPageNum()));
		captureCN51Session.setFilterDetails(airlineCN51FilterVO);
		log.exiting(CLASS_NAME, "populateFilterVo");
	}

	/**
	 * 
	 * @return Collection<String>
	 */
	private Collection<String> getOneTimeParameterTypes() {

		ArrayList<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(CATEGORY_ONETIME);
		parameterTypes.add(BILLINGTYPE_ONETIME);
		parameterTypes.add(STATUS_ONETIME);
		return parameterTypes;
	}



	private void setUnitComponent(String stationCode,
			CaptureCN51Session captureCN51Session){
		UnitRoundingVO unitRoundingVO = null;
		try{
			log.log(Log.FINE, "station code is ----------->>", stationCode);
			unitRoundingVO = UnitFormatter.getStationDefaultUnit(
					stationCode, UnitConstants.WEIGHT);			
			log.log(Log.FINE, "unit vo for wt--in session---", unitRoundingVO);
			captureCN51Session.setWeightRoundingVO(unitRoundingVO);
			unitRoundingVO = UnitFormatter.getStationDefaultUnit(
					stationCode, UnitConstants.VOLUME);
			captureCN51Session.setVolumeRoundingVO(unitRoundingVO);

		}catch(UnitException unitException) {
			unitException.getErrorCode();
		}

	}



}
