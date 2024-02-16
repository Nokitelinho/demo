/*
 * AirlineBillingController.java Created on Nov 2, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd.
 * All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import com.ibsplc.icargo.business.cra.accounting.vo.AccountingFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarVO;
import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.mra.MailTrackingMRABusinessException;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.UPUCalendar;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.MRAArlAuditFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineBillingAuditVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineForBillingVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineInvoiceLovVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineInvoiceReportVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ArlInvoiceDetailsReportVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MiscFileFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InterlineFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceInFormOneVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceLovFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoInInvoiceVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoLovVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SisSupportingDocumentDetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SupportingDocumentFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.MRABillingDetails;
import com.ibsplc.icargo.business.mail.mra.defaults.MRABillingMaster;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.SharedProxyException;
import com.ibsplc.icargo.business.mail.mra.proxy.CRAAccountingProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.CRADefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MailTrackingDefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedCurrencyProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.msgbroker.message.vo.sis.SISMessageVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.currency.vo.CurrencyConvertorVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.agent.ReportAgent;
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.report.vo.ReportMetaData;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingSQLDAO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.audit.vo.AuditFieldVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.Advice;
import com.ibsplc.xibase.server.framework.interceptor.Phase;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.keygen.GenerationFailedException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author
 * AirlineBillingController
 * Revision History
 *
 * Version Date Author Description
 *
 */
@Module("mail")
@SubModule("mra")
public class AirlineBillingController {

	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING");

	private static final String CLASS_NAME = "AirlineBillingController";

	private static final String MRA_ARL_REJECTION_MEMO_NO = "MRA_ARL_REJECTION_MEMO_NO";

	/**
	 * errorCode which will be added when invalid clearance period is found
	 * while upu clearance period is validated
	 */
	public static final String INVALID_CLEARANCE_PERIOD = "mailtracking.mra.airlinebilling.error.invalidClearancePeriod";

	/**
	 * stores the value hyphen
	 */
	public static final String STRING_VALUE_HYPHEN = "-";

	private static final String BALANCED = "B";

	private static final String UNBALANCED = "U";
	private static final String REJECTED = "R";
	private static final String DELETE = "D";

	private static final String NEW = "N";

	private static final String EXCEPTION_STATUS = "mailtracking.mra.exceptionstatus";

	private static final String MEMO_STATUS = "mailtracking.mra.memostatus";

	private static final String IS_ACCOUNTING_ENABLED = "cra.accounting.isaccountingenabled";
	private static final String GETTING_CLASS_TYPE_CARGO = "C";
	private static final String GETTING_CLASS_TYPE_MISCELLANEOUS = "M";
	private static final String SETTING_CLASS_TYPE_CARGO = "Cargo";
	private static final String SETTING_CLASS_TYPE_MISCELLANEOUS = "Miscellaneous";
	private static final String ACTUAL = "A";
	private static final String DUMMY = "D";
	private static final String INTERLINE = "I";
	private static final String MAIL_CLASS = "M";
	private static final String YES = "Y";
	private static final String CAPTURE_INVOICE="captureinvoice";
	private static final String CAPTURE_FORM_ONE="captureformone";
	private static final String EXCEPTION_CODES = "mailtracking.mra.airlinebilling.exceptioncodes";
	private static final String CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";

	private static final String BILLINGTYPE_ONETIME = "mailtracking.mra.billingtype";
	private static final String SYS_PARA_ACCOUNTING_ENABLED="cra.accounting.isaccountingenabled";
	private static final String EXCEPTION="E";
	private static final String ACCEPTED="A";
	private static final String PROCESSED="P";
	private static final String EXPCOD_DTL="DTL";
	private static final String KEY_CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
	private static final String KEY_DESPATCH_STATUS_ONETIME = "mailtracking.mra.despatchstatus";
	private static final String PAYFLG="P";
	private static final String UNUTILISED="IU";
	private static final String IBBILLED="IB";
	private static final String DSN_AUDIT_REQD="mailtracking.mra.dsnauditrequired";
	private static final String MRAIMPORTLEVL="mailtracking.defaults.DsnLevelImportToMRA";
	private static final String MISCELLANEOUS="Miscellaneous";
	private static final String MRA_OB_FINALIZED_SUCCESS = "Mail outward Invoice/s finalized successfully for Clearance period ";
	private static final String MRA_OB_FINALIZED_FAILED = "Mail outward Invoice not finalized for Clearance Period ";
	/**
	 *
	 * @author
	 * saveUPUCalendarDetails
	 * @param upuCalendarVOs
	 * @throws SystemException
	 *
	 */
	public void saveUPUCalendarDetails(Collection<UPUCalendarVO> upuCalendarVOs)
			throws SystemException {
		log.entering("AirlineBillingController", "saveUPUCalendarDetails");

		UPUCalendar upuCalendar = null;

		if (upuCalendarVOs != null && upuCalendarVOs.size() > 0) {

			for (UPUCalendarVO upuCalendarVO : upuCalendarVOs) {

				if (UPUCalendarVO.OPERATION_FLAG_INSERT.equals(upuCalendarVO
						.getOperationalFlag())) {

					if (UPUCalendar.checkDuplicates(upuCalendarVO)) {

						new UPUCalendar(upuCalendarVO);
					} else {

						upuCalendarVO
								.setOperationalFlag(UPUCalendarVO.OPERATION_FLAG_UPDATE);
					}

				}

				if (UPUCalendarVO.OPERATION_FLAG_UPDATE.equals(upuCalendarVO
						.getOperationalFlag())) {

					upuCalendar = UPUCalendar
							.find(upuCalendarVO.getCompanyCode(), upuCalendarVO
									.getBillingPeriod());
					/*
					 * log.log(Log.FINE,"\n\n\n****************** upuCalendar " +
					 * upuCalendar+"\t"+
					 * upuCalendarVO.getGenerateAfterToDate());
					 */

					if (upuCalendar != null) {
						upuCalendar.update(upuCalendarVO);
					}
				}
				if (UPUCalendarVO.OPERATION_FLAG_DELETE.equals(upuCalendarVO
						.getOperationalFlag())) {

					upuCalendar = UPUCalendar
							.find(upuCalendarVO.getCompanyCode(), upuCalendarVO
									.getBillingPeriod());

					if (upuCalendar != null) {

						upuCalendar.remove();
					}
				}
			}
		}
		log.exiting("AirlineBillingController", "saveUPUCalendarDetails");
	}

	/**
	 *
	 *
	 * Find the details of UPU Calendar for the specified filter criteria
	 *
	 * @param upuCalendarFilterVO
	 * @return Collection<UPUCalendarVO>
	 * @throws SystemException
	 *
	 */
	public Collection<UPUCalendarVO> displayUPUCalendarDetails(
			UPUCalendarFilterVO upuCalendarFilterVO) throws SystemException {

		return UPUCalendar.displayUPUCalendarDetails(upuCalendarFilterVO);
	}

	/**
	 * Method to list CN66 details
	 *
	 * @param cn66FilterVo
	 * @return
	 * @throws SystemException
	 * @author A-2518
	 */
	public Page<AirlineCN66DetailsVO> findCN66Details(
			AirlineCN66DetailsFilterVO cn66FilterVo) throws SystemException {
		log.entering(CLASS_NAME, "findCN66Details");
		Page<AirlineCN66DetailsVO> airlineCn66DetailsVos = null;
		Collection<AirlineCN66DetailsVO> airlineCn66DetailsVoCol = null;
		Double totalWeight=0.0;
		Money totalamount=null;
		try{
			totalamount=CurrencyHelper.getMoney("USD");
			totalamount.setAmount(0.0D);
		}catch(CurrencyException currencyException){
			log.log(Log.INFO,"CurrencyException found");
		}
		try{
		airlineCn66DetailsVos = AirlineCN66Details
				.findCN66Details(cn66FilterVo);
		airlineCn66DetailsVoCol=AirlineCN66Details.findCN66DetailsVOCollection(cn66FilterVo);
		String currency =null;
		if(airlineCn66DetailsVoCol!=null && airlineCn66DetailsVoCol.size()!=0){
			for(AirlineCN66DetailsVO airlineCN66DetailsVO :airlineCn66DetailsVoCol){
				currency=airlineCN66DetailsVO.getListingCurrencyCode();
			}
			totalamount=CurrencyHelper.getMoney(currency);
		for(AirlineCN66DetailsVO airlineCN66DetailsVO :airlineCn66DetailsVoCol){

			totalWeight+=airlineCN66DetailsVO.getTotalWeight();

			log.log(Log.FINE, "amount", airlineCN66DetailsVO.getAmount());
			totalamount.plusEquals(airlineCN66DetailsVO.getAmount());
		}
		}
		log.log(Log.FINE, "amount...", totalamount);
		}
		catch(CurrencyException currencyException){
			log.log(Log.INFO,"CurrencyException found");
		}

		for(AirlineCN66DetailsVO airlineCN66DetailsVO :airlineCn66DetailsVos){
			airlineCN66DetailsVO.setTotalSummaryWeight(totalWeight);
			airlineCN66DetailsVO.setSummaryAmount(totalamount);
		}
		if (!(airlineCn66DetailsVos != null && airlineCn66DetailsVos.size() > 0)) {
			log.log(Log.FINE, "No CN66 Details found");
			String clearancePrdFilter = cn66FilterVo.getClearancePeriod();
			String clrPrdForMonthEnding = getClrPrdForMonthsEnding(clearancePrdFilter);
			if (!clearancePrdFilter.equals(clrPrdForMonthEnding)) {
				/*
				 * searching for existing CN66 records for the end of the month
				 */
				cn66FilterVo.setClearancePeriod(clrPrdForMonthEnding);
				airlineCn66DetailsVos = AirlineCN66Details
						.findCN66Details(cn66FilterVo);

				if (airlineCn66DetailsVos != null
						&& airlineCn66DetailsVos.size() > 0) {

					CN66DetailsAlreadyCapturedException exception = new CN66DetailsAlreadyCapturedException();
					Object[] errorData = new Object[] { airlineCn66DetailsVos };
					ErrorVO errorVO = new ErrorVO(
							CN66DetailsAlreadyCapturedException.MAILTRACKING_MRA_AIRLINEBILLING_CN66DETAILS_FOUND,
							errorData);
					exception.addError(errorVO);
					throw new SystemException(
							CN66DetailsAlreadyCapturedException.MAILTRACKING_MRA_AIRLINEBILLING_CN66DETAILS_FOUND,
							exception);
				}

			}
		}
		log.entering(CLASS_NAME, "findCN66Details");
		return airlineCn66DetailsVos;
	}


	/**
	 * @author a-2458
	 * @param reportSpec
	 * @return  Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */

	public Map<String, Object> findCN66DetailsPrint(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "findCN66DetailsPrint");

		AirlineCN66DetailsFilterVO airlineCn66DetailsFilterVo = (AirlineCN66DetailsFilterVO) reportSpec
				.getFilterValues().get(0);
		Collection<AirlineCN66DetailsVO> airlineCn66DetailsVos = null;

		airlineCn66DetailsVos = AirlineCN66Details
				.findCN66DetailsVOCollection(airlineCn66DetailsFilterVo);

		for(AirlineCN66DetailsVO airlineCN66DetailsVO:airlineCn66DetailsVos){
			AirlineValidationVO airlineValidationVo = null;
			try {
				airlineValidationVo = new SharedAirlineProxy().findAirline(airlineCN66DetailsVO
						.getCompanyCode(), airlineCN66DetailsVO.getFlightCarrierIdentifier());
			} catch (SharedProxyException sharedProxyException) {
				log.log(Log.SEVERE,"SharedProxyException findCN66DetailsPrint");
			}
			if (airlineValidationVo != null) {
				airlineCN66DetailsVO.setCarCode(airlineValidationVo.getAlphaCode());
				if(airlineCN66DetailsVO.getFlightNumber()!=null){
					airlineCN66DetailsVO.setCarCode(airlineCN66DetailsVO.getCarCode().concat(" ").concat(airlineCN66DetailsVO.getFlightNumber()));
				}
			}
		}

		if (airlineCn66DetailsVos == null || airlineCn66DetailsVos.size() == 0) {
			ErrorVO errorVo = new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_AIRLINEBILLING_NOREPORTDATA);
			errorVo.setErrorDisplayType(ErrorDisplayType.ERROR);
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			mailTrackingMRABusinessException.addError(errorVo);
			throw mailTrackingMRABusinessException;
		}
		AirlineCN51Summary airlineCN51Summary=null;
		try{
			airlineCN51Summary=AirlineCN51Summary.find
			(airlineCn66DetailsFilterVo.getCompanyCode(),
					airlineCn66DetailsFilterVo.getAirlineId(),
					airlineCn66DetailsFilterVo.getInterlineBillingType(),
					airlineCn66DetailsFilterVo.getInvoiceRefNumber(),
					airlineCn66DetailsFilterVo.getClearancePeriod());

		}
		catch (FinderException e) {
			log
			.log(Log.SEVERE,
					"FINDER EXCEPTION OCCURED IN FINDING AirlineCN51Summary Entity");
		}

		Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		// Collection<OneTimeVO> categorycodes=new ArrayList<OneTimeVO>();
		oneTimeActiveStatusList.add(BILLINGTYPE_ONETIME);
		oneTimeActiveStatusList.add(KEY_CATEGORY_ONETIME);
		oneTimeActiveStatusList.add(KEY_DESPATCH_STATUS_ONETIME);
		try {
			oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
					airlineCn66DetailsFilterVo.getCompanyCode(),
					oneTimeActiveStatusList);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		Collection<OneTimeVO> billingType = new ArrayList<OneTimeVO>();
		Collection<OneTimeVO> categoryType = new ArrayList<OneTimeVO>();
		Collection<OneTimeVO> despatchStatus = new ArrayList<OneTimeVO>();
		if (oneTimeHashMap != null) {
			billingType = oneTimeHashMap.get(BILLINGTYPE_ONETIME);
			categoryType = oneTimeHashMap.get(KEY_CATEGORY_ONETIME);
			despatchStatus = oneTimeHashMap.get(KEY_DESPATCH_STATUS_ONETIME);
			log.log(Log.INFO, "billingStatus1234++", billingType);
		}
		if (airlineCn66DetailsFilterVo != null) {
			if (airlineCn66DetailsFilterVo.getInterlineBillingType() != null) {
				for (OneTimeVO oneTimeVO : billingType) {
					if (airlineCn66DetailsFilterVo.getInterlineBillingType()
							.equals(oneTimeVO.getFieldValue())) {
						airlineCn66DetailsFilterVo
								.setInterlineBillingType(oneTimeVO
										.getFieldDescription());
					}
				}
			}
		}
		if (airlineCn66DetailsFilterVo != null) {
			if (airlineCn66DetailsFilterVo.getCategory() != null) {
				for (OneTimeVO oneTimeVO : categoryType) {
					if (airlineCn66DetailsFilterVo.getCategory().equals(
							oneTimeVO.getFieldValue())) {
						airlineCn66DetailsFilterVo.setCategory(oneTimeVO
								.getFieldDescription());
					}
				}
			}
		}
		if (airlineCn66DetailsFilterVo != null) {
			if (airlineCn66DetailsFilterVo.getDespatchStatus() != null) {
				for (OneTimeVO oneTimeVO : despatchStatus) {
					if (airlineCn66DetailsFilterVo.getDespatchStatus().equals(
							oneTimeVO.getFieldValue())) {
						airlineCn66DetailsFilterVo.setDespatchStatus(oneTimeVO
								.getFieldDescription());
					}
				}
			}
		}
		List<String> reportParam = new ArrayList<String>();

		reportParam.add(airlineCn66DetailsFilterVo.getAirlineCode());
		reportParam.add(airlineCn66DetailsFilterVo.getClearancePeriod());
		reportParam.add(airlineCn66DetailsFilterVo.getInvoiceRefNumber());
		if(airlineCN51Summary!=null){
			if(airlineCN51Summary.getBilleddate()!=null){
			log.log(Log.FINE, "Billeddate...", airlineCN51Summary.getBilleddate().getTime());
			LocalDate billedDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,airlineCN51Summary.getBilleddate(),false);
			reportParam.add(billedDate.toDisplayDateOnlyFormat());
			}else{
				reportParam.add("");
			}
		}
		else{
			reportParam.add("");
		}
		if(airlineCn66DetailsFilterVo.getCategory()!=null){
			reportParam.add(airlineCn66DetailsFilterVo.getCategory());
		}else{
			reportParam.add("");
		}
		if(airlineCn66DetailsFilterVo.getCarriageFrom()!=null){
			reportParam.add(airlineCn66DetailsFilterVo.getCarriageFrom());
		}else{
			reportParam.add("");
		}
		if(airlineCn66DetailsFilterVo.getCarriageTo()!=null){
			reportParam.add(airlineCn66DetailsFilterVo.getCarriageTo());
		}else{
			reportParam.add("");
		}
		if(airlineCn66DetailsFilterVo.getDespatchStatus()!=null){
			reportParam.add(airlineCn66DetailsFilterVo.getDespatchStatus());
		}else{
			reportParam.add("");
		}
		if(airlineCn66DetailsFilterVo.getInterlineBillingType()!=null){
			reportParam.add(airlineCn66DetailsFilterVo.getInterlineBillingType());
		}else{
			reportParam.add("");
		}
		reportSpec.addParameter(reportParam);
		ReportMetaData parameterMetaData = new ReportMetaData();
		reportSpec.addParameterMetaData(parameterMetaData);

		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "CARFRM", "CARTOO",
				"MALCTGCOD", "ORGCOD", "DSTCOD", "DSNDAT", "DSN", "FLTNUM",
				"LCWGT", "CPWGT","ULDWGT","SVWGT","DSNSTA","APLRAT","BLDAMT" });

		reportMetaData.setFieldNames(new String[] { "carriageFrom",
				"carriageTo", "mailCategoryCode", "origin", "destination",
				"despachDate", "despatchSerialNo", "carCode", "weightLC",
				"weightCP","weightULD","weightSV","despatchStatus","rate","bldamt" });

		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(airlineCn66DetailsVos);
		return ReportAgent.generateReport(reportSpec);
		// log.entering(CLASS_NAME, "findCN66DetailsPrint");
	}

	/**
	 * @author a-2458
	 * @param reportSpec
	 * @return Map<String,Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	//	added for reports by a-2458
	public Map<String, Object> findInvoicesCollectionByClrPrd(
			ReportSpec reportSpec) throws SystemException, RemoteException,
			SystemException, MailTrackingMRABusinessException {

		log.entering(CLASS_NAME, "findInvoicesCollectionByClrPrd");
		AirlineCN51FilterVO airlineCN51FilterVO = (AirlineCN51FilterVO) reportSpec
				.getFilterValues().get(0);
		Collection<AirlineCN51DetailsVO> airlineCN51DetailsVos = null;

		airlineCN51DetailsVos = AirlineCN51Details
				.findInwardInvoicesCollection(airlineCN51FilterVO);

		if (airlineCN51DetailsVos == null || airlineCN51DetailsVos.size() == 0) {
			ErrorVO errorVo = new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_AIRLINEBILLING_NOREPORTDATA);
			errorVo.setErrorDisplayType(ErrorDisplayType.ERROR);
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			mailTrackingMRABusinessException.addError(errorVo);
			throw mailTrackingMRABusinessException;
		}

		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "iataClearancePeriod" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(airlineCN51FilterVO);
		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "TWOAPHCOD", "THRNUMCOD",
				"ARLNAM", "INVNUM", "TOTAMT", "TOTAMT1", "TOTAMT2" });
		reportMetaData.setFieldNames(new String[] { "airlineCode",
				"airlineNumber", "airlineName", "invoicenumber", "weightLC",
				"weightCP", "totalAmount" });
		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(airlineCN51DetailsVos);
		log.exiting(CLASS_NAME, "findInvoicesCollectionByClrPrd-");
		return ReportAgent.generateReport(reportSpec);

	}

	/**
	 * @author a-2458
	 * @param reportSpec
	 * @return Map<String,Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */

	//	added for reports by a-2458
	public Map<String, Object> findInvoicesCollectionByAirline(
			ReportSpec reportSpec) throws SystemException, RemoteException,
			SystemException, MailTrackingMRABusinessException {

		log.entering(CLASS_NAME, "findInvoicesCollectionByAirline");
		AirlineCN51FilterVO airlineCN51FilterVO = (AirlineCN51FilterVO) reportSpec
				.getFilterValues().get(0);
		Collection<AirlineCN51DetailsVO> airlineCN51DetailsVos = null;
		airlineCN51DetailsVos = AirlineCN51Details
				.findInwardInvoicesCollection(airlineCN51FilterVO);
		if (airlineCN51DetailsVos == null || airlineCN51DetailsVos.size() == 0) {
			ErrorVO errorVo = new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_AIRLINEBILLING_NOREPORTDATA);
			errorVo.setErrorDisplayType(ErrorDisplayType.ERROR);
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			mailTrackingMRABusinessException.addError(errorVo);
			throw mailTrackingMRABusinessException;
		}

		for (AirlineCN51DetailsVO airlineCN51DetailsVo : airlineCN51DetailsVos) {
			airlineCN51FilterVO.setAirlineName(airlineCN51DetailsVo
					.getAirlineName());
			airlineCN51FilterVO.setListingcurrencycode(airlineCN51DetailsVo.getListingcurrencycode());
		}

		ReportMetaData parameterMetaData = new ReportMetaData();
		//Modified by A-7794 as part of ICRD-294064
		parameterMetaData.setFieldNames(new String[] { "airlineCode",
				"airlineNum", "airlineName","listingcurrencycode" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(airlineCN51FilterVO);
		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "CLRPRD", "INVNUM",
				"TOTAMT", "TOTAMT1", "TOTAMT2" ,"LSTCUR"});  //Added by A-5945 for ICRD-100255 
		reportMetaData.setFieldNames(new String[] { "clearanceperiod",
				"invoicenumber", "weightLC", "weightCP", "totalAmount","listingcurrencycode" });

		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(airlineCN51DetailsVos);
		log.exiting(CLASS_NAME, "findInvoicesCollectionByAirline");
		return ReportAgent.generateReport(reportSpec);
	}

	/**
	 * Method to save CN66 details
	 *
	 * @param cn66DetailsVos
	 * @throws SystemException
	 * @author A-2518
	 */
	public void saveCN66Details(Collection<AirlineCN66DetailsVO> cn66DetailsVos)
			throws SystemException {
		log.entering(CLASS_NAME, "saveCN66Details");
		boolean isVoForSavePresent = false;
		int deletedVos = 0;
		Collection<AirlineCN51Details> airlineCN51Detailses=new ArrayList<AirlineCN51Details>();
		AirlineCN51Summary airlineCN51Summary=null;
		AirlineCN51Details airlineCN51Details=null;
		AirlineCN51DetailsVO cn51DetailsVO=new AirlineCN51DetailsVO();
		int seqNum = 0;
		if(cn66DetailsVos != null && cn66DetailsVos.size() > 0) {
			for (AirlineCN66DetailsVO airlineCn66DetailsVo : cn66DetailsVos) {
				try{
					airlineCN51Summary=AirlineCN51Summary.find
					(airlineCn66DetailsVo.getCompanyCode(),
							airlineCn66DetailsVo.getAirlineIdentifier(),
							airlineCn66DetailsVo.getInterlineBillingType(),
							airlineCn66DetailsVo.getInvoiceNumber(),
							airlineCn66DetailsVo.getClearancePeriod());

				}
				catch (FinderException e) {
					log
					.log(Log.SEVERE,
							"FINDER EXCEPTION OCCURED IN FINDING AirlineCN66Details Entity");
				}

				if(airlineCN51Summary !=null){
					airlineCN51Detailses=airlineCN51Summary.getAirlineCN51Details();
					//Assuming the Sequence nunber will be equal to the size of the child entities
					if(airlineCN51Detailses != null && airlineCN51Detailses.size() > 0) {
						seqNum = airlineCN51Detailses.size();
					}
					break;
				}

			}
		}
		/*
		 * MAINTAIN CN66 DETAILS
		 */
		for (AirlineCN66DetailsVO airlineCn66DetailsVo : cn66DetailsVos) {
			if (AirlineCN66DetailsVO.OPERATION_FLAG_INSERT
					.equals(airlineCn66DetailsVo.getOperationFlag())) {
				MailTrackingDefaultsProxy mailTrackingDefaultsProxy = new MailTrackingDefaultsProxy();
				
				isVoForSavePresent = true;
				log.log(Log.INFO, "vo in CONTROLLER ", airlineCn66DetailsVo);
				long mailseqnum =0;
				if(airlineCn66DetailsVo.getBillingBasis()!=null)
				{
				String dsnidr = airlineCn66DetailsVo.getBillingBasis().substring(0, 20);
				
				try {
					mailseqnum= mailTrackingDefaultsProxy.findMailBagSequenceNumberFromMailIdr(airlineCn66DetailsVo.getBillingBasis(),airlineCn66DetailsVo.getCompanyCode());
				} catch (ProxyException e) {
					log.log(Log.SEVERE, "ProxyException Occured!!!!");
				}
				airlineCn66DetailsVo.setDsnIdr(dsnidr);
				airlineCn66DetailsVo.setMalSeqNum(mailseqnum);
				airlineCn66DetailsVo.setOriginExchangeOffice(airlineCn66DetailsVo.getBillingBasis().substring(0, 6));
				airlineCn66DetailsVo.setDestinationExchangeOffice(airlineCn66DetailsVo.getBillingBasis().substring(6, 12));
				airlineCn66DetailsVo.setMailSubClass(airlineCn66DetailsVo.getBillingBasis().substring(13,15));

				AirlineCN66Details airlineCN66Details = new AirlineCN66Details(airlineCn66DetailsVo);
				}
//				log
//						.log(Log.FINEST,
//								"before calling Audit in case of Insert................>>>>>"+airlineCN66Details);
//				/// calling audit
//				// PostalAdministration postalAdministration = new PostalAdministration();
//				StringBuilder auditRemarks = new StringBuilder();
//				AirlineBillingAuditVO airlineBillingAuditVO = new AirlineBillingAuditVO(
//						AirlineBillingAuditVO.AUDIT_MODULENAME,
//						AirlineBillingAuditVO.AUDIT_SUBMODULENAME,
//						AirlineBillingAuditVO.AUDIT_ENTITYCN66);
//				airlineBillingAuditVO
//						.setActionCode(AirlineBillingAuditVO.AUDIT_CN66_CAPTURED);
//				auditRemarks
//						.append("CN66 Captured for Airline->"
//								+ (String.valueOf(airlineCn66DetailsVo
//										.getCarrierCode())));
//				airlineBillingAuditVO.setAdditionalInformation(auditRemarks
//						.toString());
//				airlineBillingAuditVO.setAuditRemarks(auditRemarks.toString());
//				airlineBillingAuditVO = (AirlineBillingAuditVO) AuditUtils
//						.populateAuditDetails(airlineBillingAuditVO,
//								airlineCN66Details, true);
//				LogonAttributes logonAttributes = ContextUtils
//						.getSecurityContext().getLogonAttributesVO();
//				String userId = logonAttributes.getUserId();
//				airlineBillingAuditVO.setUserId(userId);
//				airlineBillingAuditVO = populateAirlineCN66AuditDetails(
//						airlineBillingAuditVO, airlineCN66Details);
//				log.log(Log.INFO, "AuditVo before calling performAudit ->"
//						+ airlineBillingAuditVO);
//				AuditUtils.performAudit(airlineBillingAuditVO);
//				log.log(Log.FINE, "airline audit performed");

			} else if (AirlineCN66DetailsVO.OPERATION_FLAG_UPDATE
					.equals(airlineCn66DetailsVo.getOperationFlag())) {
				/*
				 * Update CN66 Details
				 */
				isVoForSavePresent = true;
				AirlineCN66Details airlineCn66Details = null;
				try {
					airlineCn66Details = AirlineCN66Details.find(
							airlineCn66DetailsVo.getCompanyCode(),
							airlineCn66DetailsVo.getAirlineIdentifier(),
							airlineCn66DetailsVo.getInvoiceNumber(),
							airlineCn66DetailsVo.getInterlineBillingType(),
							airlineCn66DetailsVo.getSequenceNumber(),
							airlineCn66DetailsVo.getClearancePeriod(),
							airlineCn66DetailsVo.getDsnIdr(),
							airlineCn66DetailsVo.getMalSeqNum() )
							;

					log.log(Log.FINE,"--Inside UPDATE--");
					airlineCn66Details.update(airlineCn66DetailsVo);
					log.log(Log.FINE, "--AFTER UPDATE airlineCn66DetailsVo--",
							airlineCn66DetailsVo);
				} catch (FinderException e) {
					log
							.log(Log.SEVERE,
									"FINDER EXCEPTION OCCURED IN FINDING AirlineCN66Details Entity");
				}
//				if (airlineCn66Details != null) {
//					log
//							.log(Log.FINEST,
//									"before calling Audit in case of update................>>>>>");
//					/// calling audit
//					// PostalAdministration postalAdministration = new PostalAdministration();
//					StringBuilder auditRemarks = new StringBuilder();
//					AirlineBillingAuditVO airlineBillingAuditVO = new AirlineBillingAuditVO(
//							AirlineBillingAuditVO.AUDIT_MODULENAME,
//							AirlineBillingAuditVO.AUDIT_SUBMODULENAME,
//							AirlineBillingAuditVO.AUDIT_ENTITYCN66);
//					airlineBillingAuditVO
//							.setActionCode(AirlineBillingAuditVO.AUDIT_CN66_UPDATED);
//					auditRemarks.append("CN66 Captured for Airline->"
//							+ (String.valueOf(airlineCn66DetailsVo
//									.getCarrierCode())));
//					airlineBillingAuditVO.setAdditionalInformation(auditRemarks
//							.toString());
//					airlineBillingAuditVO.setAuditRemarks(auditRemarks
//							.toString());
//					airlineBillingAuditVO = (AirlineBillingAuditVO) AuditUtils
//							.populateAuditDetails(airlineBillingAuditVO,
//									airlineCn66Details, false);
//					LogonAttributes logonAttributes = ContextUtils
//							.getSecurityContext().getLogonAttributesVO();
//					String userId = logonAttributes.getUserId();
//					airlineBillingAuditVO.setUserId(userId);
//					// calling update
//					airlineCn66Details.update(airlineCn66DetailsVo);
//					log.log(Log.FINE, "AirlineCN66Details has been updated");
//
//					airlineBillingAuditVO = (AirlineBillingAuditVO) AuditUtils
//							.populateAuditDetails(airlineBillingAuditVO,
//									airlineCn66Details, false);
//					airlineBillingAuditVO = populateAirlineCN66AuditDetails(
//							airlineBillingAuditVO, airlineCn66Details);
//					log.log(Log.INFO,
//							"AuditVo before calling performAudit during UPDATE case ->"
//									+ airlineBillingAuditVO);
//					AuditUtils.performAudit(airlineBillingAuditVO);
//					log.log(Log.FINE, "airline audit performed in Update case");
//
//				}
			} else if (AirlineCN66DetailsVO.OPERATION_FLAG_DELETE
					.equals(airlineCn66DetailsVo.getOperationFlag())) {
				log.log(Log.FINE,"--Inside Delete--");
				isVoForSavePresent = true;
				AirlineCN66Details airlineCn66Details = null;
				try {
					airlineCn66Details = AirlineCN66Details.find(
							airlineCn66DetailsVo.getCompanyCode(),
							airlineCn66DetailsVo.getAirlineIdentifier(),
							airlineCn66DetailsVo.getInvoiceNumber(),
							airlineCn66DetailsVo.getInterlineBillingType(),
							airlineCn66DetailsVo.getSequenceNumber(),
							airlineCn66DetailsVo.getClearancePeriod(),
							airlineCn66DetailsVo.getDsnIdr(),
							airlineCn66DetailsVo.getMalSeqNum() );
					/**added by
					 * @author a-3447 for MRA174
					 */
					airlineCn66Details.remove();
				} catch (FinderException e) {
					log.log(Log.SEVERE,
									"FINDER EXCEPTION OCCURED IN FINDING AirlineCN66Details Entity");
				}
//				if (airlineCn66Details != null) {
//					// calling audit
//					log
//							.log(Log.FINEST,
//									"before calling Audit in case of Insert................>>>>>");
//					/// calling audit
//					// PostalAdministration postalAdministration = new PostalAdministration();
//					StringBuilder auditRemarks = new StringBuilder();
//					AirlineBillingAuditVO airlineBillingAuditVO = new AirlineBillingAuditVO(
//							AirlineBillingAuditVO.AUDIT_MODULENAME,
//							AirlineBillingAuditVO.AUDIT_SUBMODULENAME,
//							AirlineBillingAuditVO.AUDIT_ENTITYCN66);
//					airlineBillingAuditVO
//							.setActionCode(AirlineBillingAuditVO.AUDIT_CN66_DELETED);
//					auditRemarks.append("CN66 Captured for Airline->"
//							+ (String.valueOf(airlineCn66Details
//									.getCarrierCode())));
//					airlineBillingAuditVO.setAdditionalInformation(auditRemarks
//							.toString());
//					airlineBillingAuditVO.setAuditRemarks(auditRemarks
//							.toString());
//					airlineBillingAuditVO = (AirlineBillingAuditVO) AuditUtils
//							.populateAuditDetails(airlineBillingAuditVO,
//									airlineCn66Details, false);
//					LogonAttributes logonAttributes = ContextUtils
//							.getSecurityContext().getLogonAttributesVO();
//					String userId = logonAttributes.getUserId();
//					airlineBillingAuditVO.setUserId(userId);
//					airlineBillingAuditVO = populateAirlineCN66AuditDetails(
//							airlineBillingAuditVO, airlineCn66Details);
//					log.log(Log.INFO, "AuditVo before calling performAudit ->"
//							+ airlineBillingAuditVO);

//					airlineCn66Details.remove();
//					log.log(Log.FINE, "AirlineCN66Details has been removed");
//					//calling performAudit
//					AuditUtils.performAudit(airlineBillingAuditVO);
//					log.log(Log.FINE, "airline audit performed during Delete");

//					++deletedVos;
//				}
			}
		}

		Collection<AirlineCN66DetailsVO> airlineCN66DetailsVos = null;

		// Getting CN66 Details
		AirlineCN66DetailsFilterVO filterVo = new AirlineCN66DetailsFilterVO();
		if(cn66DetailsVos!=null && cn66DetailsVos.size()>0){
			for(AirlineCN66DetailsVO airlineCN66DetailsVO:cn66DetailsVos){
			filterVo.setCompanyCode(airlineCN66DetailsVO.getCompanyCode());
			filterVo.setInvoiceRefNumber(airlineCN66DetailsVO.getInvoiceNumber());
			filterVo.setAirlineId(airlineCN66DetailsVO.getAirlineIdentifier());
			filterVo.setInterlineBillingType(airlineCN66DetailsVO.getInterlineBillingType());
			filterVo.setClearancePeriod(airlineCN66DetailsVO.getClearancePeriod());
			break;
			}
		}
		// Full set of CN66
		airlineCN66DetailsVos = findCN66DetailsVOCollection(filterVo);
		/*
		 * Grouping CN66 Details to Form a single CN51 Record Accordingly,
		 * 1 Carriage From
		 * 2 Carriage To
		 * 3 Mail Category Code
		 * 4 Mail Sub Class
		 * 5 Rate
		 */
		Collection<AirlineCN51DetailsVO> groupedCN51Details = null;
		if(airlineCN66DetailsVos != null && airlineCN66DetailsVos.size() > 0 ) {
			groupedCN51Details = performCN66ToCN51Grouping(airlineCN66DetailsVos);
		}
		/*
		 * UPDATE/POPULATE CN51 DETAILS
		 */
		maintainCN51Details(airlineCN51Summary,groupedCN51Details);

		// Balancing starts
		for (AirlineCN66DetailsVO airlineCn66DetailsVo : cn66DetailsVos) {
			log.log(Log.INFO, "vo in  Balancing starts CONTROLLER ",
					airlineCn66DetailsVo);
			/* If all the VOs are deleted, revert back CN51 status to <b>New<b> */
			if (deletedVos == cn66DetailsVos.size()) {
				AirlineCN51Summary airlineCn51Summary = null;
				try {
					airlineCn51Summary = AirlineCN51Summary.find(
							airlineCn66DetailsVo.getCompanyCode(),
							airlineCn66DetailsVo.getAirlineIdentifier(),
							airlineCn66DetailsVo.getInterlineBillingType(),
							airlineCn66DetailsVo.getInvoiceNumber(),
							airlineCn66DetailsVo.getClearancePeriod());
				} catch (FinderException e) {
					log
							.log(Log.SEVERE,
									"FinderException occurred in finding AirlineCN51Summary Entity");
				}
				if (airlineCn51Summary != null) {
					airlineCn51Summary.setCn51status(NEW);
					log.log(Log.FINE, "CN51 Status has been set to NEW");
				}
			} else if (isVoForSavePresent) {
				balanceCN51CN66s(airlineCn66DetailsVo.getCompanyCode(),
						airlineCn66DetailsVo.getAirlineIdentifier(),
						airlineCn66DetailsVo.getInvoiceNumber(),
						airlineCn66DetailsVo.getInterlineBillingType(),
						airlineCn66DetailsVo.getClearancePeriod(), null);
			}
			break;
		}
		log.exiting(CLASS_NAME, "saveCN66Details");
	}

	/**
	 * @author A-3227 RENO K ABRAHAM
	 * @param airlineCN51Summary
	 * @param airlineCN51DetailsVOs
	 * @throws SystemException
	 */
	private void maintainCN51Details(AirlineCN51Summary airlineCN51Summary,
			Collection<AirlineCN51DetailsVO> groupedCN51Details) throws SystemException {
		Collection<AirlineCN51Details> airlineCN51Detailses=new ArrayList<AirlineCN51Details>();
		if(airlineCN51Summary != null){
			airlineCN51Detailses=airlineCN51Summary.getAirlineCN51Details();
			if(airlineCN51Detailses != null && airlineCN51Detailses.size() > 0) {
				/*
				 * Removing CN51 details
				 * In order to avoid complexity due to CN66 modification
				 */
				for(AirlineCN51Details airlineCN51Dtls : airlineCN51Detailses){
					try {
						airlineCN51Dtls.remove();
					} catch (RemoveException ex) {
						log.log(Log.FINE, "---------- RemoveException Caught For ----AirlineCN51Details---!!!!!!!!!!!!!----------");
					}
				}
			}
			/*
			 * Inserting New set of CN51, grouped from CN66
			 */
			if(groupedCN51Details != null && groupedCN51Details.size() > 0){
				for (AirlineCN51DetailsVO airlineCN51DtlVO : groupedCN51Details) {
					try{
						new AirlineCN51Details(airlineCN51DtlVO);
					}catch (CreateException e) {
						log.log(Log.FINE, "--------- Create Exception Caught For ----AirlineCN51Details---!!!!!!!!!!!!!----------");
					}
				}
			}
		}
	}
	/**
	 * @author A-3227 RENO K ABRAHAM
	 * @param airlineCN51DetailsVOs
	 * @return
	 */
	private Collection<AirlineCN51DetailsVO> performCN66ToCN51Grouping(Collection<AirlineCN66DetailsVO> airlineCN66DetailsVOs){
		Collection<AirlineCN51DetailsVO>  sortedCN51DtlVOs = new ArrayList<AirlineCN51DetailsVO>();
		String sectorKey = null;
		Collection<AirlineCN66DetailsVO> detailsForStorage = null;
		Map<String, Collection<AirlineCN66DetailsVO>> sectorMap = new HashMap<String, Collection<AirlineCN66DetailsVO>>();
		if(airlineCN66DetailsVOs != null && airlineCN66DetailsVOs.size() > 0) {
			for (AirlineCN66DetailsVO cn66detailVO : airlineCN66DetailsVOs) {
				sectorKey = new StringBuffer(cn66detailVO.getCarriageFrom())
				.append(STRING_VALUE_HYPHEN)
				.append(cn66detailVO.getCarriageTo())
				.append(STRING_VALUE_HYPHEN)
				.append(cn66detailVO.getMailCategoryCode())
				.append(STRING_VALUE_HYPHEN)
				.append(cn66detailVO.getMailSubClass())
				.append(STRING_VALUE_HYPHEN)
				.append(cn66detailVO.getRate()).toString();
				if (sectorMap.get(sectorKey) != null) {
					sectorMap.get(sectorKey).add(cn66detailVO);
				} else {
					detailsForStorage = new ArrayList<AirlineCN66DetailsVO>();
					detailsForStorage.add(cn66detailVO);
					sectorMap.put(sectorKey, detailsForStorage);
				}
			}
			Collection<AirlineCN66DetailsVO> storredDetailsInMap = null;
			AirlineCN51DetailsVO totalDetailsVO = null;
			int seqNum = 0;

			for (Entry<String, Collection<AirlineCN66DetailsVO>> entryInsideMap : sectorMap.entrySet()) {
				storredDetailsInMap = entryInsideMap.getValue();
				double totalWeight = 0.0;
				Money totalAmt=null;
				for (AirlineCN66DetailsVO storredDetail : storredDetailsInMap) {
					if (!AirlineCN66DetailsVO.OPERATION_FLAG_DELETE.equals(storredDetail.getOperationFlag())) {
						totalWeight = totalWeight + storredDetail.getTotalWeight();
					}
				}
				try{
					totalAmt=CurrencyHelper.getMoney(((ArrayList<AirlineCN66DetailsVO>)storredDetailsInMap).get(0).getCurCod());
					totalAmt.setAmount(0.0D);
					for (AirlineCN66DetailsVO storredDetail : storredDetailsInMap) {
						if (!AirlineCN66DetailsVO.OPERATION_FLAG_DELETE.equals(storredDetail.getOperationFlag())) {
							totalAmt.plusEquals(storredDetail.getAmount());
						}
					}
				}
				catch(CurrencyException currencyException){
					log.log(Log.INFO,"CurrencyException found");
				}
				// constrcuting a vo for the storing the total details
				StringTokenizer tokenizer =
					new StringTokenizer(entryInsideMap.getKey(), STRING_VALUE_HYPHEN, false);
				String[] keyArray = new String[tokenizer.countTokens()+1];
				int index = 0;
				while (tokenizer.hasMoreTokens()) {
					keyArray[index] = tokenizer.nextToken();
					index++;
				}
				index = 0;
				totalDetailsVO = new AirlineCN51DetailsVO();
				totalDetailsVO.setCarriagefrom(keyArray[index]);
				totalDetailsVO.setCarriageto(keyArray[++index]);
				totalDetailsVO.setMailcategory(keyArray[++index]);
				totalDetailsVO.setMailsubclass(keyArray[++index]);
				totalDetailsVO.setApplicablerate(Double.parseDouble(keyArray[++index]));

				totalDetailsVO.setCompanycode(((ArrayList<AirlineCN66DetailsVO>)storredDetailsInMap).get(0).getCompanyCode());
				totalDetailsVO.setAirlineidr(((ArrayList<AirlineCN66DetailsVO>)storredDetailsInMap).get(0).getAirlineIdentifier());
				totalDetailsVO.setInvoicenumber(((ArrayList<AirlineCN66DetailsVO>)storredDetailsInMap).get(0).getInvoiceNumber());
				totalDetailsVO.setInterlinebillingtype(((ArrayList<AirlineCN66DetailsVO>)storredDetailsInMap).get(0).getInterlineBillingType());
				totalDetailsVO.setSequenceNumber(++seqNum);
				totalDetailsVO.setClearanceperiod(((ArrayList<AirlineCN66DetailsVO>)storredDetailsInMap).get(0).getClearancePeriod());
				try
				{
					totalDetailsVO.setTotalweight(UnitFormatter.getRoundedValue(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_KILOGRAM, totalWeight));
				}catch(UnitException unitException) {
					unitException.getErrorCode();
			   }
				//totalDetailsVO.setTotalweight(getScaledValue(totalWeight,2));
				totalDetailsVO.setTotalamountincontractcurrency(totalAmt);
				sortedCN51DtlVOs.add(totalDetailsVO);
			}
		}
		return sortedCN51DtlVOs;
	}
	/**
	 * @param airlineBillingAuditVO
	 * @param airlineCN66Details
	 * @return
	 */
	/*private AirlineBillingAuditVO populateAirlineCN66AuditDetails(
			AirlineBillingAuditVO airlineBillingAuditVO,
			AirlineCN66Details airlineCN66Details) {
		StringBuffer additionalInfo = new StringBuffer();
		log.log(Log.INFO, "Inside findDcmAuditDetails -> populating fields");
		airlineBillingAuditVO.setCompanyCode(airlineCN66Details
				.getAirlineCN66DetailsPK().getCompanyCode());
		airlineBillingAuditVO.setAirlineIdentifier(airlineCN66Details
				.getAirlineCN66DetailsPK().getAirlineIdentifier());
		//postalAdministrationAuditVO.setUserId("System");
		if (airlineBillingAuditVO.getAuditFields() != null
				&& airlineBillingAuditVO.getAuditFields().size() > 0) {
			for (AuditFieldVO auditField : airlineBillingAuditVO
					.getAuditFields()) {
				if (auditField != null) {
					log.log(Log.INFO, "Inside AuditField Not Null");
					additionalInfo.append(" Field Name: ").append(
							auditField.getFieldName()).append(
							" Field Description: ").append(
							auditField.getDescription()).append(" Old Value: ")
							.append(auditField.getOldValue()).append(
									" New Value: ").append(
									auditField.getNewValue());
				}
			}
		}
		//airlineBillingAuditVO.setAdditionalInformation(additionalInfo.toString());
		return airlineBillingAuditVO;
	}
*/
	/**
	 * Method displayAirlineExceptions
	 *
	 * @param airlineExceptionsFilterVO
	 * @throws SystemException
	 * @author A-2407
	 */
	public Page<AirlineExceptionsVO> displayAirlineExceptions(
			AirlineExceptionsFilterVO airlineExceptionsFilterVO)
			throws SystemException {
		log.entering(CLASS_NAME, "displayAirlineExceptions");
		return AirlineExceptions
				.displayAirlineExceptions(airlineExceptionsFilterVO);
	}

	/**
	 * Method saveAirlineExceptions
	 *
	 * @param airlineExceptionsVOs
	 * @throws SystemException
	 * @throws FinderException
	 * @return void
	 * @author A-2407
	 */
	@Advice(name = "mailtracking.mra.saveAirlineExceptions" , phase=Phase.POST_INVOKE)
	public void saveAirlineExceptions(
			Collection<AirlineExceptionsVO> airlineExceptionsVOs)
			throws SystemException {
		log.entering(CLASS_NAME, "saveAirlineExceptions");
		log.log(Log.FINE, ",------- Vos To Save------->\n\n",
				airlineExceptionsVOs);
		if (airlineExceptionsVOs != null) {
			try {
				for (AirlineExceptionsVO avo : airlineExceptionsVOs) {
					if (("U").equals(avo.getOperationalFlag())) {
						AirlineExceptionsPK airlineExceptionsPK = new AirlineExceptionsPK();
						airlineExceptionsPK
								.setCompanyCode(avo.getCompanyCode());
						airlineExceptionsPK.setAirlineIdentifier(avo
								.getAirlineIdentifier());
						airlineExceptionsPK.setExceptionCode(avo
								.getExceptionCode());
						airlineExceptionsPK.setSerialNumber(avo
								.getSerialNumber());
						airlineExceptionsPK.setInvoiceNumber(avo
								.getInvoiceNumber());
						airlineExceptionsPK.setClearancePeriod(avo
								.getClearancePeriod());

						AirlineExceptions airlineExceptions = AirlineExceptions
								.find(airlineExceptionsPK);
						airlineExceptions.update(avo);

					}
				}
			} catch (FinderException ex) {
				throw new SystemException(ex.getErrorCode(), ex);
			}
		} else {
			log.log(Log.FINE, "\n\n\n<-----------Vo to be saved is null----->");
		}
	}

	/**
	 * Method findAirlineExceptionInInvoices
	 *
	 * @param exceptionInInvoiceFilterVO
	 * @throws SystemException
	 * @author A-2391
	 */
	public Page<ExceptionInInvoiceVO> findAirlineExceptionInInvoices(
			ExceptionInInvoiceFilterVO exceptionInInvoiceFilterVO)
			throws SystemException {
		log.entering(CLASS_NAME, "findAirlineExceptionInInvoices");
		return ExceptionInInvoice
				.findAirlineExceptionInInvoices(exceptionInInvoiceFilterVO);
	}

	/**
	 * Method acceptAirlineInvoices
	 *
	 * @param exceptionInInvoiceVOs
	 * @throws SystemException
	 *
	 * @author A-2391
	 */
	public void acceptAirlineInvoices(
			Collection<ExceptionInInvoiceVO> exceptionInInvoiceVOs)
			throws SystemException {
		log.entering(CLASS_NAME, "acceptAirlineInvoices");
		ArrayList<ExceptionInInvoiceVO> exceptionVOs = new ArrayList<ExceptionInInvoiceVO>(
				exceptionInInvoiceVOs);
		int size = exceptionVOs.size();
		try {
			for (int i = 0; i < size; i++) {
				String companyCode = exceptionVOs.get(i).getCompanyCode();
				int airlineIdentifier = exceptionVOs.get(i)
						.getAirlineIdentifier();
				String invoiceNumber = exceptionVOs.get(i).getInvoiceNumber();
				String airlineCode = exceptionVOs.get(i).getAirlineCode();
				String clearancePeriod = exceptionVOs.get(i)
						.getClearancePeriod();
				//Added as part of ICRD-265471
				String dsnIdr = exceptionVOs.get(i).getDsnIdr();
				long malSeqnum =exceptionVOs.get(i).getMalseqnum() ;
				ExceptionInInvoice exceptionInInvoice = ExceptionInInvoice
						.find(companyCode, airlineIdentifier, invoiceNumber,
								clearancePeriod);
				exceptionInInvoice.setExceptionStatus("A");
				/***
				 *Added by Sandeep as part of optimistic Locking Mechanism
				 */
				exceptionInInvoice.setLastUpdatedTime(exceptionVOs.get(i)
						.getLastUpdatedTime());
				exceptionInInvoice.setLastUpdatedUser(exceptionVOs.get(i)
						.getLastUpdatedUser());
				/***
				 *Added by Sandeep as part of optimistic Locking Mechanism Ends
				 */
				if (exceptionVOs.get(i).getRemark() != null
						&& exceptionVOs.get(i).getRemark().trim().length() > 0) {
					exceptionInInvoice.setRemark(exceptionVOs.get(i)
							.getRemark());
				}
				AirlineExceptionsFilterVO expfiltervo = new AirlineExceptionsFilterVO();
				expfiltervo
						.setCompanyCode(exceptionVOs.get(i).getCompanyCode());
				expfiltervo.setInvoiceRefNumber(exceptionVOs.get(i)
						.getInvoiceNumber());
				expfiltervo.setAirlineIdentifier(exceptionVOs.get(i)
						.getAirlineIdentifier());
				expfiltervo.setClearancePeriod(exceptionVOs.get(i)
						.getClearancePeriod());
				ArrayList<AirlineExceptionsVO> airlineExceptionVOs = (ArrayList<AirlineExceptionsVO>) AirlineExceptions
						.findAirlineExceptions(expfiltervo);
				int expSize = airlineExceptionVOs.size();
				AirlineExceptions airlineExceptions = null;
				log.log(Log.INFO, "airlineexcep obtained with size!!!!!!!!!",
						airlineExceptionVOs.size());
				for (int eSize = 0; eSize < expSize; eSize++) {
					AirlineExceptionsPK airlineExceptionsPK = new AirlineExceptionsPK();
					airlineExceptionsPK.setCompanyCode(companyCode);
					airlineExceptionsPK.setAirlineIdentifier(airlineIdentifier);
					airlineExceptionsPK.setExceptionCode(airlineExceptionVOs
							.get(eSize).getExceptionCode());
					airlineExceptionsPK.setSerialNumber(airlineExceptionVOs
							.get(eSize).getSerialNumber());
					airlineExceptionsPK.setInvoiceNumber(invoiceNumber);
					airlineExceptionsPK.setClearancePeriod(clearancePeriod);
					airlineExceptions = AirlineExceptions
							.find(airlineExceptionsPK);
					log.log(Log.INFO, "airlineexcep obtained!!!!!!!!!",
							airlineExceptions);
					airlineExceptions.setExceptionStatus("A");
					log.log(Log.INFO, "airlineexcep values set!!!!!!!!!");
				}
				String billingType = exceptionInInvoice
						.getInterlineBillingType();
				AirlineCN66DetailsFilterVO filtervo = new AirlineCN66DetailsFilterVO();
				filtervo.setCompanyCode(companyCode);
				filtervo.setAirlineId(airlineIdentifier);
				filtervo.setInterlineBillingType(billingType);
				filtervo.setPageNumber(1);
				filtervo.setClearancePeriod(clearancePeriod);
				filtervo.setInvoiceRefNumber(invoiceNumber);
				Collection<AirlineCN66DetailsVO> airlineCN66DetailVOs = AirlineCN66Details
						.findCN66Details(filtervo);
				ArrayList<AirlineCN66DetailsVO> cn66VOs = new ArrayList<AirlineCN66DetailsVO>(
						airlineCN66DetailVOs);
				int cn66vosize = cn66VOs.size();
				int flg = 0;
				for (int j = 1; j <= cn66vosize; j++) {

					if (("E").equals(cn66VOs.get(j - 1).getDespatchStatus())) {

						AirlineCN66Details airlineCN66Details = AirlineCN66Details
								.find(companyCode, airlineIdentifier,
										invoiceNumber, billingType,
										cn66VOs.get(j - 1).getSequenceNumber(),

										clearancePeriod,
										dsnIdr,
										malSeqnum);
						log.log(Log.FINE, "entity obtained ", j);
						airlineCN66Details.setDespatchStatus("A");
						flg++;
					}
					if ((("P").equals(cn66VOs.get(j - 1).getDespatchStatus()))
							|| (("A").equals
									(cn66VOs.get(j - 1).getDespatchStatus()))) {
						flg++;
					}
				}
				log.log(Log.INFO, "fl ", flg);
				if (flg == cn66vosize && cn66vosize > 0) {
					log.log(Log.INFO, "flag ", flg);
					AirlineCN51Summary airlineCN51Summary = AirlineCN51Summary
							.find(companyCode, airlineIdentifier, billingType,
									invoiceNumber, clearancePeriod);
					log.log(Log.FINE, "entity obtained ");
					airlineCN51Summary.setCn51status("P");
					log.log(Log.INFO, "before auditing cn51");
					AirlineBillingAuditVO airlineBillingAuditVO = new AirlineBillingAuditVO(
							AirlineBillingAuditVO.AUDIT_MODULENAME,
							AirlineBillingAuditVO.AUDIT_SUBMODULENAME,
							AirlineBillingAuditVO.AUDIT_ENTITYCN51SMY);
					airlineBillingAuditVO = (AirlineBillingAuditVO) AuditUtils
							.populateAuditDetails(airlineBillingAuditVO,
									airlineCN51Summary, false);
					LogonAttributes logonAttributes = ContextUtils
							.getSecurityContext().getLogonAttributesVO();
					String userId = logonAttributes.getUserId();
					airlineBillingAuditVO.setUserId(userId);
					airlineBillingAuditVO.setAirlineCode(airlineCode);
					airlineBillingAuditVO.setClearancePeriod(clearancePeriod);
					airlineBillingAuditVO
							.setActionCode(AirlineBillingAuditVO.AUDIT_INVOICE_ACCEPTED);
					airlineBillingAuditVO = populateAirlineCN51AuditDetails(
							airlineBillingAuditVO, airlineCN51Summary);
					StringBuffer additionalInfo = new StringBuffer();
					additionalInfo.append("Airline ").append(airlineCode)
							.append(" with invoice number ").append(
									invoiceNumber).append(" ").append(
									AirlineBillingAuditVO.AUDIT_ACCEPT_ACTION);
					airlineBillingAuditVO
							.setAdditionalInformation(additionalInfo.toString());
					AuditUtils.performAudit(airlineBillingAuditVO);
					log.log(Log.INFO, "after auditing cn51");
				}
			}
		} catch (FinderException e) {
			throw new SystemException(e.getErrorCode(),
					e);
		}

	}

	/**
	 * Method saveRejectionMemo
	 *
	 * @param exceptionInInvoiceVO
	 * @throws SystemException
	 * @throws FinderException
	 * @author A-2391
	 */
	public RejectionMemoVO saveRejectionMemo(
			ExceptionInInvoiceVO exceptionInInvoiceVO) throws SystemException {
		log.entering(CLASS_NAME, "saveRejectionMemo");
		log.entering("Memo", "generateMemoNUmber");
		Criterion criterion = null;
		String generatedKey = null;
		String key = null;

		try {

			String companyCode = exceptionInInvoiceVO.getCompanyCode();
			String airlineCode = exceptionInInvoiceVO.getAirlineCode();
			String userId = exceptionInInvoiceVO.getLastUpdatedUser();
			String contractCurCod = exceptionInInvoiceVO.getContractCurrency();
			Double bldAmt = exceptionInInvoiceVO.getReportedAmount();
			Double provAmt = exceptionInInvoiceVO.getProvisionalAmount();
			Double difAmt = exceptionInInvoiceVO.getDifferenceAmount();
			int airlineIdentifier = exceptionInInvoiceVO.getAirlineIdentifier();
			String invoiceNumber = exceptionInInvoiceVO.getInvoiceNumber();
			String clearancePeriod = exceptionInInvoiceVO.getClearancePeriod();
           //Added as part of ICRD-265471
			String dsnIdr = exceptionInInvoiceVO.getDsnIdr();
			long malSeqnum = exceptionInInvoiceVO.getMalseqnum();
			/*Memo code generation*/
			StringBuilder keyBuilder = new StringBuilder(5);
			criterion = KeyUtils.getCriterion(exceptionInInvoiceVO
					.getCompanyCode(), MRA_ARL_REJECTION_MEMO_NO);
			log.log(Log.INFO, "criterion");
			generatedKey = KeyUtils.getKey(criterion);
			log.log(Log.INFO, "key generated", generatedKey);
			int keyLength = generatedKey.length();
			for (int count = 0; count < 5 - keyLength; count++) {
				keyBuilder.append("0");
			}
			keyBuilder.append(generatedKey);
			log.log(Log.INFO, "key keyBuilder", keyBuilder);
			key = new StringBuilder().append("RM").append(
					exceptionInInvoiceVO.getAirlineCode()).append(
					clearancePeriod).append("INV").append(keyBuilder)
					.toString();

			log.log(Log.INFO, new StringBuilder().append("Generated Key -->")
					.append(key).toString());
			log.exiting("Memo", "generateMemoNUmber");

			/* changing exception and memo status in ExceptionInInvoice*/

			ExceptionInInvoice exceptionInInvoice = ExceptionInInvoice.find(
					companyCode, airlineIdentifier, invoiceNumber,
					clearancePeriod);
			exceptionInInvoice.setExceptionStatus("R");
			exceptionInInvoice.setMemoStatus("B");
			exceptionInInvoice.setMemoCode(key);

			/*changing the exception and memo status in expdtl table*/

			AirlineExceptionsFilterVO expfiltervo = new AirlineExceptionsFilterVO();
			expfiltervo.setCompanyCode(companyCode);
			expfiltervo.setInvoiceRefNumber(invoiceNumber);
			expfiltervo.setAirlineIdentifier(airlineIdentifier);
			expfiltervo.setClearancePeriod(clearancePeriod);
			ArrayList<AirlineExceptionsVO> airlineExceptionVOs = (ArrayList<AirlineExceptionsVO>) AirlineExceptions
					.findAirlineExceptions(expfiltervo);
			int expSize = airlineExceptionVOs.size();
			AirlineExceptions airlineExceptions = null;
			log.log(Log.INFO, "airlineexcep obtained with size!!!!!!!!!",
					airlineExceptionVOs.size());
			for (int eSize = 0; eSize < expSize; eSize++) {
				AirlineExceptionsPK airlineExceptionsPK = new AirlineExceptionsPK();
				airlineExceptionsPK.setCompanyCode(companyCode);
				airlineExceptionsPK.setAirlineIdentifier(airlineIdentifier);
				airlineExceptionsPK.setExceptionCode(airlineExceptionVOs.get(
						eSize).getExceptionCode());
				airlineExceptionsPK.setSerialNumber(airlineExceptionVOs.get(
						eSize).getSerialNumber());
				airlineExceptionsPK.setInvoiceNumber(invoiceNumber);
				airlineExceptionsPK.setClearancePeriod(clearancePeriod);
				airlineExceptions = AirlineExceptions.find(airlineExceptionsPK);
				log.log(Log.INFO, "airlineexcep obtained!!!!!!!!!",
						airlineExceptions);
				airlineExceptions.setExceptionStatus("R");
				log.log(Log.INFO, "KEY set!!!!!!!!!", key);
				airlineExceptions.setMemoCode(key);
				log.log(Log.INFO, "airlineexcep values set!!!!!!!!!");
			}

			/*changing the despatch status in cn66 table*/

			String billingType = exceptionInInvoice.getInterlineBillingType();
			AirlineCN66DetailsFilterVO filtervo = new AirlineCN66DetailsFilterVO();
			filtervo.setCompanyCode(companyCode);
			filtervo.setAirlineId(airlineIdentifier);
			filtervo.setInterlineBillingType(billingType);
			filtervo.setClearancePeriod(clearancePeriod);
			filtervo.setInvoiceRefNumber(invoiceNumber);
			Collection<AirlineCN66DetailsVO> airlineCN66DetailVOs = AirlineCN66Details
					.findCN66Details(filtervo);
			ArrayList<AirlineCN66DetailsVO> cn66VOs = new ArrayList<AirlineCN66DetailsVO>(
					airlineCN66DetailVOs);
			int cn66vosize = cn66VOs.size();
			log.log(Log.INFO, "vos obtained ", cn66VOs);
			for (int j = 1; j <= cn66vosize; j++) {

				if (("E").equals(cn66VOs.get(j - 1).getDespatchStatus())) {

					AirlineCN66Details airlineCN66Details = AirlineCN66Details
							.find(companyCode, airlineIdentifier,
									invoiceNumber, billingType, cn66VOs.get(
											j - 1).getSequenceNumber(),
									clearancePeriod,dsnIdr,malSeqnum);
					log.log(Log.INFO, "entity obtained");
					airlineCN66Details.setDespatchStatus("R");
					log.log(Log.INFO, "value set");
				}
			}

			RejectionMemoVO rejectionVO = new RejectionMemoVO();
			rejectionVO.setCompanycode(companyCode);
			rejectionVO.setAirlineCode(airlineCode);
			rejectionVO.setAirlineIdentifier(airlineIdentifier);
			rejectionVO.setMemoCode(key);
			rejectionVO.setContractCurrencyCode(contractCurCod);
			rejectionVO.setInwardInvoiceNumber(invoiceNumber);
			rejectionVO.setInwardClearancePeriod(clearancePeriod);
			double rate = 1.0;
			/*server call to find exchange rate and billing currency code*/
			String blgcurcod = RejectionMemo.findBlgCurCode(rejectionVO);
			rejectionVO.setBillingCurrencyCode(blgcurcod);
			if (blgcurcod != null && blgcurcod.trim().length() > 0
					&& contractCurCod != null
					&& contractCurCod.trim().length() > 0) {
				if (!blgcurcod.equals(contractCurCod)) {
					if (clearancePeriod != null
							&& clearancePeriod.trim().length() > 0) {
						try {
							UPUCalendarVO upuVO = validateIataClearancePeriod(
									companyCode, clearancePeriod);
							if (upuVO != null) {
								CurrencyConvertorVO currencyConvertorVO = new CurrencyConvertorVO();
								currencyConvertorVO.setCompanyCode(companyCode);
								LogonAttributes logonAttributes = ContextUtils
										.getSecurityContext()
										.getLogonAttributesVO();
								currencyConvertorVO
										.setAirlineIdentifier(logonAttributes
												.getOwnAirlineIdentifier());
								currencyConvertorVO
										.setFromCurrencyCode(blgcurcod);
								currencyConvertorVO
										.setToCurrencyCode(contractCurCod);
								// Collection<String> systemParameterCodes = new ArrayList<String>();
								//systemParameterCodes.add(IS_ACCOUNTING_ENABLED);
								//Map<String, String> systemParameters = null;
								// systemParameters = new SharedDefaultsProxy()
								//	.findSystemParameterByCodes(systemParameterCodes);
								//	String ratingBasis = (systemParameters.get(IS_ACCOUNTING_ENABLED));
								currencyConvertorVO.setRatingBasisType("F");
								currencyConvertorVO.setValidityStartDate(upuVO
										.getFromDate());
								currencyConvertorVO.setValidityEndDate(upuVO
										.getToDate());
								log.log(Log.INFO, "rate value--->",
										currencyConvertorVO);
								rate = new SharedCurrencyProxy()
										.findConversionRate(currencyConvertorVO);

							}
						} catch (ProxyException e) {
							e.getMessage();
						} catch (MailTrackingMRABusinessException e) {
							e.getMessage();
						}
					}

					//rate=RejectionMemo.findExgRate(rejectionVO);
				} else {
					rate = 1.0;
				}
			}
			log.log(Log.INFO, "rate ", rate);
			rejectionVO.setCompanycode(companyCode);
			rejectionVO.setAirlineCode(airlineCode);
			rejectionVO.setBillingCurrencyCode(blgcurcod);
			rejectionVO.setContractBillingExchangeRate(rate);
			rejectionVO.setAirlineIdentifier(airlineIdentifier);
			rejectionVO.setMemoCode(key);
			rejectionVO.setContractCurrencyCode(contractCurCod);
			rejectionVO.setInwardInvoiceNumber(invoiceNumber);
			rejectionVO.setInwardClearancePeriod(clearancePeriod);
			rejectionVO.setClearanceCurrencyCode(rejectionVO
					.getBillingCurrencyCode());
			rejectionVO.setContractBilledAmount(bldAmt);
			rejectionVO.setContractAcceptedAmount(provAmt);
			rejectionVO.setContractRejectedAmount(difAmt);
			//rejectionVO.setBillingBilledAmount(bldAmt * rate);
			//rejectionVO.setBillingAcceptedAmount(provAmt * rate);
			//rejectionVO.setBillingRejectedAmount(difAmt * rate);
			rejectionVO.setClearanceBilledAmount(bldAmt * rate);
			rejectionVO.setClearanceAcceptedAmount(provAmt * rate);
			rejectionVO.setClearanceRejectedAmount(difAmt * rate);
			rejectionVO.setBillingClearanceExchangeRate(1.0);
			rejectionVO.setMemoStatus("B");

			RejectionMemoFilterVO filterVO = new RejectionMemoFilterVO();
			filterVO.setCompanyCode(companyCode);
			filterVO.setMemoCode(key);
			filterVO.setAirlineIdentifier(airlineIdentifier);

			/*saving details in arlmemo table*/
			new RejectionMemo(rejectionVO);

			/*getting airline identifier from accouting table using procedure*/
			// commented by Sandeep on 30 DEC 2009
			//String accIdr = RejectionMemo.findAcctTxnIdr(filterVO, userId);

			/*setting acctTxnIdr in arlmem table*/
			RejectionMemo rejectionMemo = RejectionMemo.find(companyCode,
					airlineIdentifier, key);
			//rejectionMemo.setAcctTxnIdr(accIdr);

			/*fetching the saved data from arlmemo table*/
			rejectionVO = findRejectionMemo(filterVO);

			/*for auditing
			 Changed by A-4809*/
			log.log(Log.INFO, "before auditing");
/*			AirlineBillingAuditVO airlineBillingAuditVO = new AirlineBillingAuditVO(
					AirlineBillingAuditVO.AUDIT_MODULENAME,
					AirlineBillingAuditVO.AUDIT_SUBMODULENAME,
					AirlineBillingAuditVO.AUDIT_ENTITY);
			airlineBillingAuditVO = (AirlineBillingAuditVO) AuditUtils
					.populateAuditDetails(airlineBillingAuditVO, rejectionMemo,
							true);*/
			RejectionMemoVO rejectionMemoVO = populateRejectionMemoVO(rejectionMemo);
			AirlineBillingController airlineBillingController = (AirlineBillingController)SpringAdapter.getInstance().getBean("airlineBillingcontroller");
			airlineBillingController.performAirlineBillingAudit(rejectionMemoVO,
					AirlineBillingAuditVO.AUDIT_REJECTIONMEMO_CAPTURED);
			log.log(Log.INFO, "after auditing");

			return rejectionVO;
		} catch (FinderException ex) {
			throw new SystemException(ex.getErrorCode(),
					ex);
		}
	}

	/**
	 * Method deleteRejectionMemo
	 *
	 * @param exceptionInInvoiceVOs
	 * @throws SystemException
	 * @throws FinderException
	 * @author A-2391
	 */
	public void deleteRejectionMemo(
			Collection<ExceptionInInvoiceVO> exceptionInInvoiceVOs)
			throws SystemException {
		log.entering(CLASS_NAME, "deleteRejectionMemo");
		ArrayList<ExceptionInInvoiceVO> exceptionVOs = new ArrayList<ExceptionInInvoiceVO>(
				exceptionInInvoiceVOs);
		int size = exceptionVOs.size();
		try {
			for (int i = 0; i < size; i++) {
				String companyCode = exceptionVOs.get(i).getCompanyCode();
				int airlineIdentifier = exceptionVOs.get(i)
						.getAirlineIdentifier();
				String invoiceNumber = exceptionVOs.get(i).getInvoiceNumber();
				String memoCode = exceptionVOs.get(i).getMemoCode();
				String clearancePeriod = exceptionVOs.get(i)
						.getClearancePeriod();
				//Added as part of ICRD-265471
				String dsnIdr = exceptionVOs.get(i).getDsnIdr();
				long malSeqnum = exceptionVOs.get(i).getMalseqnum();
				
				int flag = 0;
				// try {
				ExceptionInInvoice exceptionInInvoice = ExceptionInInvoice
						.find(companyCode, airlineIdentifier, invoiceNumber,
								clearancePeriod);

				exceptionInInvoice.setExceptionStatus("E");
				exceptionInInvoice.setMemoStatus("");
				exceptionInInvoice.setMemoCode("");

				AirlineExceptionsFilterVO expfiltervo = new AirlineExceptionsFilterVO();
				expfiltervo
						.setCompanyCode(exceptionVOs.get(i).getCompanyCode());
				expfiltervo.setInvoiceRefNumber(exceptionVOs.get(i)
						.getInvoiceNumber());
				expfiltervo.setAirlineIdentifier(exceptionVOs.get(i)
						.getAirlineIdentifier());
				expfiltervo.setClearancePeriod(exceptionVOs.get(i)
						.getClearancePeriod());
				ArrayList<AirlineExceptionsVO> airlineExceptionVOs = (ArrayList<AirlineExceptionsVO>) AirlineExceptions
						.findAirlineExceptions(expfiltervo);
				int expSize = airlineExceptionVOs.size();
				AirlineExceptions airlineExceptions = null;
				log.log(Log.INFO, "airlineexcep obtained with size!!!!!!!!!",
						airlineExceptionVOs.size());
				for (int eSize = 0; eSize < expSize; eSize++) {
					AirlineExceptionsPK airlineExceptionsPK = new AirlineExceptionsPK();
					airlineExceptionsPK.setCompanyCode(companyCode);
					airlineExceptionsPK.setAirlineIdentifier(airlineIdentifier);
					airlineExceptionsPK.setExceptionCode(airlineExceptionVOs
							.get(eSize).getExceptionCode());
					airlineExceptionsPK.setSerialNumber(airlineExceptionVOs
							.get(eSize).getSerialNumber());
					airlineExceptionsPK.setInvoiceNumber(invoiceNumber);
					airlineExceptionsPK.setClearancePeriod(clearancePeriod);
					airlineExceptions = AirlineExceptions
							.find(airlineExceptionsPK);
					log.log(Log.INFO, "airlineexcep obtained!!!!!!!!!",
							airlineExceptions);
					airlineExceptions.setExceptionStatus("E");
					airlineExceptions.setMemoCode("");
					log.log(Log.INFO, "airlineexcep values set!!!!!!!!!");
				}

				String billingType = exceptionInInvoice
						.getInterlineBillingType();
				AirlineCN66DetailsFilterVO filtervo = new AirlineCN66DetailsFilterVO();
				filtervo.setCompanyCode(companyCode);
				filtervo.setAirlineId(airlineIdentifier);
				filtervo.setInterlineBillingType(billingType);
				filtervo.setClearancePeriod(clearancePeriod);
				filtervo.setInvoiceRefNumber(invoiceNumber);
				Collection<AirlineCN66DetailsVO> airlineCN66DetailVOs = AirlineCN66Details
						.findCN66Details(filtervo);
				ArrayList<AirlineCN66DetailsVO> cn66VOs = new ArrayList<AirlineCN66DetailsVO>(
						airlineCN66DetailVOs);
				int cn66vosize = cn66VOs.size();

				for (int j = 1; j <= cn66vosize; j++) {
					flag++;

					if (("R").equals(cn66VOs.get(j - 1).getDespatchStatus())) {

						AirlineCN66Details airlineCN66Details = AirlineCN66Details
								.find(companyCode, airlineIdentifier,
										invoiceNumber, billingType,
										cn66VOs.get(j - 1).getSequenceNumber(),

										clearancePeriod,dsnIdr,malSeqnum);

						airlineCN66Details.setDespatchStatus("E");
						log.log(log.INFO, "c66det set");
					}
				}

				if (flag == cn66vosize && cn66vosize > 0) {

					AirlineCN51Summary airlineCN51Summary = AirlineCN51Summary
							.find(companyCode, airlineIdentifier, billingType,
									invoiceNumber, clearancePeriod);
					airlineCN51Summary.setCn51status("W");
					log.log(log.INFO, "c51smy set");
				}
				RejectionMemo rejectionMemo = null;
				rejectionMemo = RejectionMemo.find(companyCode,
						airlineIdentifier, memoCode);
				String acctTxnIdr = rejectionMemo.getAcctTxnIdr();

				/*for auditing*/
				log.log(Log.INFO, "before auditing");
/*				AirlineBillingAuditVO airlineBillingAuditVO = new AirlineBillingAuditVO(
						AirlineBillingAuditVO.AUDIT_MODULENAME,
						AirlineBillingAuditVO.AUDIT_SUBMODULENAME,
						AirlineBillingAuditVO.AUDIT_ENTITY);
				airlineBillingAuditVO = (AirlineBillingAuditVO) AuditUtils
						.populateAuditDetails(airlineBillingAuditVO,
								rejectionMemo, false);*/
				RejectionMemoVO rejectionMemoVO = populateRejectionMemoVO(rejectionMemo);
				AirlineBillingController airlineBillingController = (AirlineBillingController)SpringAdapter.getInstance().getBean("airlineBillingcontroller");
				airlineBillingController.performAirlineBillingAudit(rejectionMemoVO,
						AirlineBillingAuditVO.AUDIT_REJECTIONMEMO_DELETED);
				log.log(Log.INFO, "after auditing");

				rejectionMemo.remove();
				/*for change in cra-accounting*/
				Collection<String> systemParameterCodes = new ArrayList<String>();
				systemParameterCodes.add(IS_ACCOUNTING_ENABLED);
				Map<String, String> systemParameters = null;
				systemParameters = new SharedDefaultsProxy()
						.findSystemParameterByCodes(systemParameterCodes);
				String accountingEnabled = (systemParameters
						.get(IS_ACCOUNTING_ENABLED));
				if (("N").equals(accountingEnabled)) {
					CRAAccountingProxy proxy = new CRAAccountingProxy();
					AccountingFilterVO filterVO = new AccountingFilterVO();

					filterVO.setAccountTransactionIdentifier(acctTxnIdr);
					filterVO.setAirlineIdentifier(airlineIdentifier);
					filterVO.setCompanyCode(companyCode);
					//filterVO.setInvoiceNumber(invoiceNumber);
					filterVO.setFunctionPoint("MR");
					proxy.reverseAccountingDetails(filterVO);
				}
			}

		} catch (FinderException ex) {
			throw new SystemException(ex.getErrorCode(),
					ex);
		} catch (RemoveException e) {
			throw new SystemException(e.getErrorCode(),
					e);
		} catch (ProxyException proxyException) {
			ErrorVO errorVO = null;
			for (ErrorVO error : proxyException.getErrors()) {
				errorVO = error;
				break;
			}
			throw new SystemException(errorVO.getErrorCode(), proxyException);
		}

	}

	/**
	 * Method to balance CN51 and CN66 details
	 *
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param invoiceNumber
	 * @param interlineBillingType
	 * @param clearancePeriod
	 * @param airlineCn51DetailsVos
	 * @throws SystemException
	 * @author a-2518
	 */
	public void balanceCN51CN66s(String companyCode, int airlineIdentifier,
			String invoiceNumber, String interlineBillingType,
			String clearancePeriod,
			Collection<AirlineCN51DetailsVO> airlineCn51DetailsVos)
			throws SystemException {
		log.entering(CLASS_NAME, "balanceCN51CN66s");
		boolean isBalanced = false;
		double cn51TotalWeight = 0.0D;
		double cn51TotalAmount = 0.0D;
		double cn66TotalWeight = 0.0D;
		double cn66TotalAmount = 0.0D;
		double cn51WeightTotal = 0.0D;
		double cn51AmountTotal = 0.0D;
		double cn66WeightTotal = 0.0D;
		double cn66AmountTotal = 0.0D;
		double cn51SmyTotalWeight = 0.0D;
		double cn51SmyTotalAmount = 0.0D;
		String airlineCn51DetailsKey = null;
		String airlineCn66DetailsKey = null;
		AirlineCN51Summary airlineCn51Summary = null;
		HashMap<String, Collection<AirlineCN51DetailsVO>> airlineCn51DetailsMap = new HashMap<String, Collection<AirlineCN51DetailsVO>>();
		HashMap<String, Collection<AirlineCN66DetailsVO>> airlineCn66DetailsMap = new HashMap<String, Collection<AirlineCN66DetailsVO>>();
		Collection<AirlineCN51DetailsVO> airlineCn51Details = null;
		Collection<AirlineCN66DetailsVO> airlineCn66Details = null;
		Collection<AirlineCN66DetailsVO> cn66DetailsVos = null;

		// Getting CN66 Details
		AirlineCN66DetailsFilterVO filterVo = new AirlineCN66DetailsFilterVO();
		filterVo.setCompanyCode(companyCode);
		filterVo.setInvoiceRefNumber(invoiceNumber);
		filterVo.setAirlineId(airlineIdentifier);
		filterVo.setInterlineBillingType(interlineBillingType);
		filterVo.setClearancePeriod(clearancePeriod);

		try{
			airlineCn51Summary=AirlineCN51Summary.find
			(companyCode,airlineIdentifier,
					interlineBillingType,invoiceNumber,clearancePeriod);
			//cn51SmyTotalWeight = getScaledValue(airlineCn51Summary.getTotalWt(),2);
			
			try
			{
				cn51SmyTotalWeight=UnitFormatter.getRoundedValue(UnitConstants.MAIL_WGT, UnitConstants.WEIGHT_MAIL_UNIT, airlineCn51Summary.getTotalWt());
			
			}catch(UnitException unitException) {
				unitException.getErrorCode();
		   }
			cn51SmyTotalAmount = getScaledValue(airlineCn51Summary.getNetAmount(),2);
			log.log(Log.FINE, "<-------- CN51 WT SUMMARY --------->",
					cn51SmyTotalWeight);
			log.log(Log.FINE, "<-------- CN51 AMOUNT SUMMARY --------->",
					cn51SmyTotalAmount);
		}
		catch (FinderException e) {
			log
			.log(Log.SEVERE,
					"FINDER EXCEPTION OCCURED IN FINDING AirlineCN66Details Entity");
		}
		cn66DetailsVos = findCN66DetailsVOCollection(filterVo);
		log.log(Log.INFO, "<------ cn66DetailsVos in balancing------>",
				cn66DetailsVos);
		// Creating CN66 HashMap
		if (cn66DetailsVos != null && cn66DetailsVos.size() > 0) {
			for (AirlineCN66DetailsVO airlineCn66DetailsVo : cn66DetailsVos) {
				log
						.log(
								Log.INFO,
								"<------ airlineCn66DetailsVo IN balanceCN51CN66s ------>",
								airlineCn66DetailsVo);
				airlineCn66DetailsKey = new StringBuilder().append(
						airlineCn66DetailsVo.getCompanyCode()).append(
						airlineCn66DetailsVo.getAirlineIdentifier()).append(
						airlineCn66DetailsVo.getInvoiceNumber()).append(
						airlineCn66DetailsVo.getInterlineBillingType()).append(
						airlineCn66DetailsVo.getClearancePeriod()).append(
						airlineCn66DetailsVo.getCarriageFrom()).append(
						airlineCn66DetailsVo.getCarriageTo()).append(
						airlineCn66DetailsVo.getMailCategoryCode()).append(
						airlineCn66DetailsVo.getMailSubClass()).append(
						airlineCn66DetailsVo.getRate()).toString();

				try
				{
					cn66WeightTotal += UnitFormatter.getRoundedValue(UnitConstants.MAIL_WGT, UnitConstants.WEIGHT_MAIL_UNIT, airlineCn66DetailsVo.getTotalWeight());
				
				}catch(UnitException unitException) {
					unitException.getErrorCode();
			   }
				//cn66WeightTotal += getScaledValue(airlineCn66DetailsVo.getTotalWeight(),2);
				cn66AmountTotal += getScaledValue(airlineCn66DetailsVo.getBldamt(),2);

				if (!airlineCn66DetailsMap.containsKey(airlineCn66DetailsKey)) {
					log.log(Log.FINE, "<------ GENERATED 66 KEY ------->");
					log.log(Log.FINE, airlineCn66DetailsKey);
					airlineCn66Details = new ArrayList<AirlineCN66DetailsVO>();
					airlineCn66Details.add(airlineCn66DetailsVo);
					airlineCn66DetailsMap.put(airlineCn66DetailsKey,airlineCn66Details);
				}else {
					airlineCn66DetailsMap.get(airlineCn66DetailsKey).add(airlineCn66DetailsVo);
				}
			}
			/*
			 * Scaling the double value to TWO Precision
			 */
			try
			{
				cn66WeightTotal = UnitFormatter.getRoundedValue(UnitConstants.MAIL_WGT, UnitConstants.WEIGHT_MAIL_UNIT, cn66WeightTotal);
			
			}catch(UnitException unitException) {
				unitException.getErrorCode();
		   }
			//cn66WeightTotal = getScaledValue(cn66WeightTotal, 2);
			cn66AmountTotal = getScaledValue(cn66AmountTotal, 2);

		}
		/*
		 * Getting Collection of AirlineCN51DetailsVO if this method was called
		 * from saveCN66Details method
		 */
		if (airlineCn51DetailsVos == null) {
			AirlineCN51SummaryVO airlineCn51SummaryVo = null;
			AirlineCN51FilterVO airlineCn51FilterVo = new AirlineCN51FilterVO();
			airlineCn51FilterVo.setCompanyCode(companyCode);
			airlineCn51FilterVo.setAirlineIdentifier(airlineIdentifier);
			airlineCn51FilterVo.setInterlineBillingType(interlineBillingType);
			airlineCn51FilterVo.setInvoiceReferenceNumber(invoiceNumber);
			airlineCn51FilterVo.setIataClearancePeriod(clearancePeriod);
			airlineCn51SummaryVo = AirlineCN51Summary
					.findCN51DetailColection(airlineCn51FilterVo);
			if (airlineCn51SummaryVo != null) {
				airlineCn51DetailsVos = airlineCn51SummaryVo
						.getCn51DetailsVOs();
			}
			/* if there is no CN51 Summary details, no balancing will be done */
			else {
				isBalanced = false;
				updateCN51CN66Status(companyCode, airlineIdentifier,
						invoiceNumber, interlineBillingType, clearancePeriod,
						isBalanced, cn66DetailsVos);
				return;
			}
		}
		// Creating CN51 HashMap
		if (airlineCn51DetailsVos != null) {
			for (AirlineCN51DetailsVO airlineCn51DetailsVo : airlineCn51DetailsVos) {
				airlineCn51DetailsKey = new StringBuilder().append(
						airlineCn51DetailsVo.getCompanycode()).append(
						airlineCn51DetailsVo.getAirlineidr()).append(
						airlineCn51DetailsVo.getInvoicenumber()).append(
						airlineCn51DetailsVo.getInterlinebillingtype()).append(
						airlineCn51DetailsVo.getClearanceperiod()).append(
						airlineCn51DetailsVo.getCarriagefrom()).append(
						airlineCn51DetailsVo.getCarriageto()).append(
						airlineCn51DetailsVo.getMailcategory()).append(
						airlineCn51DetailsVo.getMailsubclass()).append(
						airlineCn51DetailsVo.getApplicablerate()).toString();
				try
				{
					cn51WeightTotal+=UnitFormatter.getRoundedValue(UnitConstants.MAIL_WGT, UnitConstants.WEIGHT_MAIL_UNIT, airlineCn51DetailsVo.getTotalweight());

				}catch(UnitException unitException) {
					unitException.getErrorCode();
			   }
				//cn51WeightTotal += getScaledValue(airlineCn51DetailsVo.getTotalweight(),2);
				if(airlineCn51DetailsVo.getTotalamountincontractcurrency() != null ) {
					cn51AmountTotal += airlineCn51DetailsVo.getTotalamountincontractcurrency().getRoundedAmount();
				}
				if (!airlineCn51DetailsMap.containsKey(airlineCn51DetailsKey)) {
					log.log(Log.FINE, "<------ GENERATED 51 KEY ------>");
					log.log(Log.FINE, airlineCn51DetailsKey);
					airlineCn51Details = new ArrayList<AirlineCN51DetailsVO>();
					airlineCn51Details.add(airlineCn51DetailsVo);
					airlineCn51DetailsMap.put(airlineCn51DetailsKey,airlineCn51Details);
				}else {
					airlineCn51DetailsMap.get(airlineCn66DetailsKey).add(airlineCn51DetailsVo);
				}
			}
			/*
			 * Scaling the double value to TWO Precision
			 */
			try
			{
				cn51WeightTotal=UnitFormatter.getRoundedValue(UnitConstants.MAIL_WGT, UnitConstants.WEIGHT_MAIL_UNIT, cn51WeightTotal);
			
			}catch(UnitException unitException) {
				unitException.getErrorCode();
		   }
			//cn51WeightTotal = getScaledValue(cn51WeightTotal, 2);
			cn51AmountTotal = getScaledValue(cn51AmountTotal, 2);
		} else {
			isBalanced = false;
			updateCN51CN66Status(companyCode, airlineIdentifier, invoiceNumber,
					interlineBillingType, clearancePeriod, isBalanced,
					cn66DetailsVos);
		}
		// Balancing starts
		Set<String> airlineCn51DetailsKeys = airlineCn51DetailsMap.keySet();
		Set<String> airlineCn66DetailsKeys = airlineCn66DetailsMap.keySet();
		// Comparing CN51 with CN66
		for (String key : airlineCn51DetailsKeys) {
			cn51TotalWeight = 0.0D;
			cn66TotalWeight = 0.0D;
			cn51TotalAmount = 0.0D;
			cn66TotalAmount = 0.0D;
			Collection<AirlineCN51DetailsVO> airlineCn51DetailsKeyVos = null;
			Collection<AirlineCN66DetailsVO> airlineCn66DetailsKeyVos = null;
			airlineCn51DetailsKeyVos = airlineCn51DetailsMap.get(key);
			if (airlineCn66DetailsMap.containsKey(key)) {
				airlineCn66DetailsKeyVos = airlineCn66DetailsMap.get(key);
			}
			if (airlineCn66DetailsKeyVos == null) {
				isBalanced = false;
				updateCN51CN66Status(companyCode, airlineIdentifier,
						invoiceNumber, interlineBillingType, clearancePeriod,
						isBalanced, cn66DetailsVos);
				return;
			}
			log.log(Log.FINE,
					"airlineCn51DetailsKeyVos inside controller==>>>",
					airlineCn51DetailsKeyVos);
			if (airlineCn51DetailsKeyVos != null
					&& airlineCn51DetailsKeyVos.size() > 0) {
				for (AirlineCN51DetailsVO airlineCn51DetailsVo : airlineCn51DetailsKeyVos) {
					log.log(Log.FINE, "<-------- 1 CN51 WEIGHT -----*--->",
							airlineCn51DetailsVo.getTotalweight());
					cn51TotalWeight += airlineCn51DetailsVo.getTotalweight();
					if(airlineCn51DetailsVo.getTotalamountincontractcurrency() != null ) {
						cn51TotalAmount += airlineCn51DetailsVo.getTotalamountincontractcurrency().getAmount();
					}
				}
				for (AirlineCN66DetailsVO airlineCn66DetailsVo : airlineCn66DetailsKeyVos) {
					log.log(Log.FINE, "<-------- 1 CN66 WEIGHT --------->",
							airlineCn66DetailsVo.getTotalWeight());
					double cn66vowt=0.0;
					cn66vowt= airlineCn66DetailsVo.getTotalWeight();
					cn66TotalWeight += cn66vowt;
					cn66TotalAmount += airlineCn66DetailsVo.getBldamt();
				}
				/*
				 * Scaling the double value to TWO Precision
				 */
				try
				{
					cn51TotalWeight=UnitFormatter.getRoundedValue(UnitConstants.MAIL_WGT, UnitConstants.WEIGHT_MAIL_UNIT, cn51TotalWeight);
				
				}catch(UnitException unitException) {
					unitException.getErrorCode();
			   }
				//cn51TotalWeight = getScaledValue(cn51TotalWeight, 2);
				cn51TotalAmount = getScaledValue(cn51TotalAmount, 2);
				try
				{
					cn66TotalWeight=UnitFormatter.getRoundedValue(UnitConstants.MAIL_WGT, UnitConstants.WEIGHT_MAIL_UNIT, cn66TotalWeight);
				
				}catch(UnitException unitException) {
					unitException.getErrorCode();
			   }
				//cn66TotalWeight = getScaledValue(cn66TotalWeight, 2);
				cn66TotalAmount = getScaledValue(cn66TotalAmount, 2);
				if ((Double.compare(cn51TotalWeight, cn66TotalWeight) != 0) ||
						(Double.compare(cn51TotalAmount, cn66TotalAmount) != 0)) {
					log.log(Log.FINE, "<-------- CN51 & CN566-- WT OR AMT ------ NOT MATCHING --------->");
					log.log(Log.FINE, "<-------- CN51 WEIGHT --------->",
							cn51TotalWeight);
					log.log(Log.FINE, "<-------- CN66 WEIGHT --------->",
							cn66TotalWeight);
					log.log(Log.FINE, "<-------- CN51 AMOUNT --------->",
							cn51TotalAmount);
					log.log(Log.FINE, "<-------- CN66 AMOUNT --------->",
							cn66TotalAmount);
					isBalanced = false;
					updateCN51CN66Status(companyCode, airlineIdentifier,
							invoiceNumber, interlineBillingType,
							clearancePeriod, isBalanced, cn66DetailsVos);
					return;
				} else if ((Double.compare(cn51WeightTotal,cn51SmyTotalWeight ) != 0) ||
						(Double.compare(cn51AmountTotal,cn51SmyTotalAmount ) != 0)){
					log.log(Log.FINE, "<-------- CN51 & CN51 SMY ---WT OR AMT--------- NOT MATCHING --------->");
					log.log(Log.FINE, "<-------- CN51 WEIGHT --------->",
							cn51WeightTotal);
					log.log(Log.FINE, "<-------- CN51 SMY WEIGHT --------->",
							cn51SmyTotalWeight);
					log.log(Log.FINE, "<-------- CN51 AMOUNT --------->",
							cn51AmountTotal);
					log.log(Log.FINE, "<-------- CN51 SMY AMOUNT --------->",
							cn51SmyTotalAmount);
					isBalanced = false;
					updateCN51CN66Status(companyCode, airlineIdentifier,
							invoiceNumber, interlineBillingType,
							clearancePeriod, isBalanced, cn66DetailsVos);
					return;
				} else if ((Double.compare(cn66WeightTotal, cn51SmyTotalWeight) != 0) ||
						(Double.compare(cn66AmountTotal, cn51SmyTotalAmount) != 0)){
					log.log(Log.FINE, "<-------- CN66 & CN51 SMY -- WT OR AMT --------- NOT MATCHING --------->");
					log.log(Log.FINE, "<-------- CN51 SMY WEIGHT --------->",
							cn51SmyTotalWeight);
					log.log(Log.FINE, "<-------- CN66 WEIGHT --------->",
							cn66WeightTotal);
					log.log(Log.FINE, "<-------- CN51 SMY AMOUNT --------->",
							cn66AmountTotal);
					log.log(Log.FINE, "<-------- CN66 AMOUNT --------->",
							cn51SmyTotalAmount);
					isBalanced = false;
					updateCN51CN66Status(companyCode, airlineIdentifier,
							invoiceNumber, interlineBillingType,
							clearancePeriod, isBalanced, cn66DetailsVos);
					return;
				} else {
					log.log(Log.FINE, "<-------- WEIGHT AND AMOUNT CONDITION MATCHED --------->");
					isBalanced = true;
				}
			}
		}
		// Comparing CN66 with CN51
		for (String key : airlineCn66DetailsKeys) {
			cn66TotalWeight = 0.0D;
			cn51TotalWeight = 0.0D;
			cn51TotalAmount = 0.0D;
			cn66TotalAmount = 0.0D;
			Collection<AirlineCN51DetailsVO> airlineCn51DetailsKeyVos = null;
			Collection<AirlineCN66DetailsVO> airlineCn66DetailsKeyVos = null;
			airlineCn66DetailsKeyVos = airlineCn66DetailsMap.get(key);
			if (airlineCn51DetailsMap.containsKey(key)) {
				airlineCn51DetailsKeyVos = airlineCn51DetailsMap.get(key);
			}
			if (airlineCn51DetailsKeyVos == null) {
				isBalanced = false;
				updateCN51CN66Status(companyCode, airlineIdentifier,
						invoiceNumber, interlineBillingType, clearancePeriod,
						isBalanced, cn66DetailsVos);
				return;
			}
			if (airlineCn66DetailsKeyVos != null
					&& airlineCn66DetailsKeyVos.size() > 0) {
				for (AirlineCN66DetailsVO airlineCn66DetailsVo : airlineCn66DetailsKeyVos) {
					log.log(Log.FINE, "<-------- 2 CN51 WEIGHT --------->",
							airlineCn66DetailsVo.getTotalWeight());
					double cn66vowt=0.0;
					cn66vowt= airlineCn66DetailsVo.getTotalWeight();
					cn66TotalWeight += cn66vowt;
					cn66TotalAmount += airlineCn66DetailsVo.getBldamt();
				}
				for (AirlineCN51DetailsVO airlineCn51DetailsVo : airlineCn51DetailsKeyVos) {
					log.log(Log.FINE, "<-------- 2 CN66 WEIGHT --------->",
							airlineCn51DetailsVo.getTotalweight());
					cn51TotalWeight += airlineCn51DetailsVo.getTotalweight();
					if(airlineCn51DetailsVo.getTotalamountincontractcurrency() != null ) {
						cn51TotalAmount += airlineCn51DetailsVo.getTotalamountincontractcurrency().getRoundedAmount();
					}
				}
				/*
				 * Scaling the double value to TWO Precision
				 */
				try
				{
					cn51TotalWeight=UnitFormatter.getRoundedValue(UnitConstants.MAIL_WGT, UnitConstants.WEIGHT_MAIL_UNIT, cn51TotalWeight);
				
				}catch(UnitException unitException) {
					unitException.getErrorCode();
			   }
				//cn51TotalWeight = getScaledValue(cn51TotalWeight, 2);
				cn51TotalAmount = getScaledValue(cn51TotalAmount, 2);
				try
				{
					cn66TotalWeight=UnitFormatter.getRoundedValue(UnitConstants.MAIL_WGT, UnitConstants.WEIGHT_MAIL_UNIT, cn66TotalWeight);
				
				}catch(UnitException unitException) {
					unitException.getErrorCode();
			   }
				//cn66TotalWeight = getScaledValue(cn66TotalWeight, 2);
				cn66TotalAmount = getScaledValue(cn66TotalAmount, 2);
				if ((Double.compare(cn51TotalWeight, cn66TotalWeight) != 0) ||
						(Double.compare(cn51TotalAmount, cn66TotalAmount) != 0)) {
					log.log(Log.FINE, "<-------- CN51 & CN566-- WT OR AMT ------ NOT MATCHING --------->");
					log.log(Log.FINE, "<-------- CN51 WEIGHT --------->",
							cn51TotalWeight);
					log.log(Log.FINE, "<-------- CN66 WEIGHT --------->",
							cn66TotalWeight);
					log.log(Log.FINE, "<-------- CN51 AMOUNT --------->",
							cn51TotalAmount);
					log.log(Log.FINE, "<-------- CN66 AMOUNT --------->",
							cn66TotalAmount);
					isBalanced = false;
					updateCN51CN66Status(companyCode, airlineIdentifier,
							invoiceNumber, interlineBillingType,
							clearancePeriod, isBalanced, cn66DetailsVos);
					return;
				} else if ((Double.compare(cn51WeightTotal,cn51SmyTotalWeight ) != 0) ||
						(Double.compare(cn51AmountTotal,cn51SmyTotalAmount ) != 0)){
					log.log(Log.FINE, "<-------- CN51 & CN51 SMY ---WT OR AMT--------- NOT MATCHING --------->");
					log.log(Log.FINE, "<-------- CN51 WEIGHT --------->",
							cn51WeightTotal);
					log.log(Log.FINE, "<-------- CN51 SMY WEIGHT --------->",
							cn51SmyTotalWeight);
					log.log(Log.FINE, "<-------- CN51 AMOUNT --------->",
							cn51AmountTotal);
					log.log(Log.FINE, "<-------- CN51 SMY AMOUNT --------->",
							cn51SmyTotalAmount);
					isBalanced = false;
					updateCN51CN66Status(companyCode, airlineIdentifier,
							invoiceNumber, interlineBillingType,
							clearancePeriod, isBalanced, cn66DetailsVos);
					return;
				} else if ((Double.compare(cn66WeightTotal, cn51SmyTotalWeight) != 0) ||
						(Double.compare(cn66AmountTotal, cn51SmyTotalAmount) != 0)){
					log.log(Log.FINE, "<-------- CN66 & CN51 SMY -- WT OR AMT --------- NOT MATCHING --------->");
					log.log(Log.FINE, "<-------- CN51 SMY WEIGHT --------->",
							cn51SmyTotalWeight);
					log.log(Log.FINE, "<-------- CN66 WEIGHT --------->",
							cn66WeightTotal);
					log.log(Log.FINE, "<-------- CN51 SMY AMOUNT --------->",
							cn51SmyTotalAmount);
					log.log(Log.FINE, "<-------- CN66 AMOUNT --------->",
							cn66AmountTotal);
					isBalanced = false;
					updateCN51CN66Status(companyCode, airlineIdentifier,
							invoiceNumber, interlineBillingType,
							clearancePeriod, isBalanced, cn66DetailsVos);
					return;
				} else {
					log.log(Log.FINE, "<-------- WEIGHT AND AMOUNT CONDITION MATCHED --------->");
					isBalanced = true;
				}
			}
		}
		updateCN51CN66Status(companyCode, airlineIdentifier, invoiceNumber,
				interlineBillingType, clearancePeriod, isBalanced,
				cn66DetailsVos);
		log.exiting(CLASS_NAME, "balanceCN51CN66s");
	}
	/**
	 * Method to list CN66 details
	 *
	 * @param cn66FilterVo
	 * @return
	 * @throws SystemException
	 * @author A-3434
	 */
	public Collection<AirlineCN66DetailsVO> findCN66DetailsVOCollection(
			AirlineCN66DetailsFilterVO cn66FilterVo) throws SystemException {
		log.entering(CLASS_NAME, "findCN66Details");
		Collection<AirlineCN66DetailsVO> airlineCn66DetailsVos = null;
		return AirlineCN66Details.findCN66DetailsVOCollection(cn66FilterVo);
	}
	/**
	 *
	 * @param cn51FilterVO
	 * @return
	 * @throws SystemException
	 * @throws CN51DetailsAlreadyCapturedException
	 */
	public AirlineCN51SummaryVO findCN51Details(AirlineCN51FilterVO cn51FilterVO)
			throws SystemException, CN51DetailsAlreadyCapturedException {
		log.entering(CLASS_NAME, "findCN51Details");
		double wt=0.0;
	    Money moneyChg=null;
	    try{
			moneyChg=CurrencyHelper.getMoney("USD");
			moneyChg.setAmount(0.0D);
     }
     catch(CurrencyException currencyException){
			log.log(Log.INFO,"CurrencyException found");
		}
		AirlineCN51SummaryVO summaryVOfetched = AirlineCN51Summary
				.findCN51Details(cn51FilterVO);
		AirlineCN51FilterVO filtervo = new AirlineCN51FilterVO();
		try {
			BeanHelper.copyProperties(filtervo, cn51FilterVO);
		} catch (SystemException e) {
			e.getMessage();
		}
		if (summaryVOfetched == null) {
			log.log(Log.FINE, " ##### no CN51 records found ");
			String clearancePrdFilter = cn51FilterVO.getIataClearancePeriod();
			String clrPrdForMonthEnding = getClrPrdForMonthsEnding(clearancePrdFilter);

			if (!clearancePrdFilter.equals(clrPrdForMonthEnding)) {

				/*
				 * searching for existing CN51 summary records for the end of
				 * the month
				 */
				filtervo.setIataClearancePeriod(clrPrdForMonthEnding);
				summaryVOfetched = AirlineCN51Summary.findCN51Details(filtervo);

				if (summaryVOfetched != null) {

					CN51DetailsAlreadyCapturedException exception = new CN51DetailsAlreadyCapturedException();
					Object[] errorData = new Object[] { summaryVOfetched };
					ErrorVO errorVO = new ErrorVO(
							CN51DetailsAlreadyCapturedException.MAILTRACKING_MRA_AIRLINEBILLING_CN51DETAILS_FOUND,
							errorData);
					exception.addError(errorVO);
					throw exception;
				}

			}
		}
		AirlineCN51SummaryVO summaryVOCollection = AirlineCN51Summary
		.findCN51DetailColection(cn51FilterVO);

		if(summaryVOCollection!=null){
			if(summaryVOCollection.getCn51DetailsVOs()!=null
					&& summaryVOCollection.getCn51DetailsVOs().size()>0){
				try{
				moneyChg=CurrencyHelper.getMoney(summaryVOCollection.getListingCurrency());
				}catch(CurrencyException currencyException){
					log.log(Log.INFO,"CurrencyException found");
				}
				for(AirlineCN51DetailsVO c51vo:summaryVOCollection.getCn51DetailsVOs()){
					if(c51vo.getTotalweight()!=0.0){
						wt=wt+c51vo.getTotalweight();
					}
					if(c51vo.getTotalamountincontractcurrency()!=null){
						moneyChg.plusEquals(c51vo.getTotalamountincontractcurrency());
					}
				}

			}
		}
		if(summaryVOfetched!=null){
			//rounding not required
		summaryVOfetched.setTotalWeight(wt);
			
		//summaryVOfetched.setTotalWeight(getScaledValue(wt,2));
		summaryVOfetched.setTotalCharge(moneyChg);
		}
		return summaryVOfetched;
		/*
		 * String iataclearancePrd = cn51FilterVO.getIataClearancePeriod(); int
		 * length = iataclearancePrd.length();
		 * cn51FilterVO.setIataClearancePeriod(getClearancePrd_For_Months_Ending(iataclearancePrd));
		 */
	}

	// Added for Bug 91970
	/**
	 *@author a-3434
	 * @param cn51FilterVO
	 * @return
	 * @throws SystemException
	 * @throws CN51DetailsAlreadyCapturedException
	 */
	public AirlineCN51SummaryVO findCN51DetailsCollection(AirlineCN51FilterVO cn51FilterVO)
			throws SystemException, CN51DetailsAlreadyCapturedException {
		log.entering(CLASS_NAME, "findCN51Details");
		double wt=0.0;
	    Money moneyChg=null;
	    try{
			moneyChg=CurrencyHelper.getMoney("USD");
			moneyChg.setAmount(0.0D);
    }
    catch(CurrencyException currencyException){
			log.log(Log.INFO,"CurrencyException found");
		}

		AirlineCN51SummaryVO summaryVOCollection = AirlineCN51Summary
		.findCN51DetailColection(cn51FilterVO);

		if(summaryVOCollection!=null){
			if(summaryVOCollection.getCn51DetailsVOs()!=null
					&& summaryVOCollection.getCn51DetailsVOs().size()>0){
				try{
				moneyChg=CurrencyHelper.getMoney(summaryVOCollection.getListingCurrency());
				}catch(CurrencyException currencyException){
					log.log(Log.INFO,"CurrencyException found");
				}
				for(AirlineCN51DetailsVO c51vo:summaryVOCollection.getCn51DetailsVOs()){
					if(c51vo.getTotalweight()!=0.0){
						wt=wt+c51vo.getTotalweight();
					}
					if(c51vo.getTotalamountincontractcurrency()!=null){
						moneyChg.plusEquals(c51vo.getTotalamountincontractcurrency());
					}
				}

			}
		}
		if(summaryVOCollection!=null){
			 try
				{
				 summaryVOCollection.setTotalWeight(UnitFormatter.getRoundedValue(UnitConstants.MAIL_WGT, UnitConstants.WEIGHT_MAIL_UNIT, wt));
				}catch(UnitException unitException) {
					unitException.getErrorCode();
			   }
			//summaryVOCollection.setTotalWeight(getScaledValue(wt,2));
			summaryVOCollection.setTotalCharge(moneyChg);
		}
		return summaryVOCollection;

	}

	/**
	 * @author A-3227 RENO K ABRAHAM
	 * This method rounds the specified double value to a precision specified
	 * @param value
	 * @param precision
	 * @return
	 */
	private double getScaledValue(double value, int precision) {

		java.math.BigDecimal bigDecimal = new java.math.BigDecimal(value);
		return bigDecimal.setScale(precision,
				java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
	}


	/**
	 * @param reportspec
	 * @return
	 * @throws SystemException
	 * @throws ReportGenerationException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findCN51DetailsReport(ReportSpec reportspec)
			throws SystemException, ReportGenerationException,
			MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "findCN51DetailsReport");
		AirlineCN51FilterVO cn51FilterVO = null;
		AirlineCN51SummaryVO summaryVOfetched = null;
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		List<OneTimeVO> mailcategory = new ArrayList<OneTimeVO>();
		List<OneTimeVO> billingType = new ArrayList<OneTimeVO>();
		 Collection<AirlineCN51DetailsVO> cn51DetailsVOs=null;
		if ((reportspec != null) && (reportspec.getFilterValues() != null)) {
			cn51FilterVO = (AirlineCN51FilterVO) reportspec.getFilterValues()
					.get(0);
		}
		try {

			log.log(Log.FINE, "\n Filter VO---cn51FilterVO ", cn51FilterVO);
			summaryVOfetched = findCN51DetailsCollection(cn51FilterVO);// // may be
			// null,not null
			// or exception


			if (summaryVOfetched != null) {

				/*
				 * for bug fix 37974,the data is in page..but is compared for getCn51DetailsVOs()
				 * so setting the data from <Page> to   getCn51DetailsVOs()<Collection>
				 */
				if(summaryVOfetched.getCn51DetailsPageVOs()!=null){
				summaryVOfetched.setCn51DetailsVOs(summaryVOfetched.getCn51DetailsPageVOs());
				}
				log
						.log(
								Log.FINE,
								"\n\n\n<------------------summaryVOfetched----------------->  ",
								summaryVOfetched);
				if (summaryVOfetched.getCn51DetailsVOs() == null) {
					log.log(Log.FINE, "\n Cn51DetailsVOs is null ");
				} else {
					log.log(Log.FINE, "\n Cn51DetailsVOs is not null ",
							summaryVOfetched.getCn51DetailsVOs());
				}

			}
			if ((summaryVOfetched == null)
					|| (summaryVOfetched.getCn51DetailsVOs() == null)
					|| summaryVOfetched.getCn51DetailsVOs().size() <= 0) {
				log.log(Log.FINE, "\n No Data received ");
				ErrorVO error = new ErrorVO(
						MailTrackingMRABusinessException.NO_DATA);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				MailTrackingMRABusinessException exp = new MailTrackingMRABusinessException();
				exp.addError(error);
				throw exp;

			}
		} catch (CN51DetailsAlreadyCapturedException e) {
			log.log(Log.FINE, "\n No Data received ");
			ErrorVO error = new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			MailTrackingMRABusinessException exp = new MailTrackingMRABusinessException();
			exp.addError(error);
			throw exp;
		}
		/***Added by
		 * @author a-3447 for Bug 29992 starts
		 */


		SharedDefaultsProxy defaultsproxy = new SharedDefaultsProxy();
		oneTimeList.add(CATEGORY_ONETIME);
		oneTimeList.add(BILLINGTYPE_ONETIME);
		try {
			oneTimeValues = defaultsproxy.findOneTimeValues(cn51FilterVO
					.getCompanyCode(), oneTimeList);
		} catch (ProxyException e) {
			ErrorVO errorVO = null;
			for (ErrorVO error : e.getErrors()) {
				errorVO = error;
				break;
			}
			throw new SystemException(errorVO.getErrorCode(), e);
		}
		if (oneTimeValues != null) {
			mailcategory = (ArrayList<OneTimeVO>) oneTimeValues
			.get(CATEGORY_ONETIME);

		}
		if (oneTimeValues != null) {
			billingType = (ArrayList<OneTimeVO>) oneTimeValues
			.get(BILLINGTYPE_ONETIME);

		}


		cn51DetailsVOs=summaryVOfetched.getCn51DetailsVOs();

		for (AirlineCN51DetailsVO airlineCN51DetailsVO : cn51DetailsVOs) {
			for (OneTimeVO mailcategoryVO : mailcategory) {
				if (mailcategoryVO.getFieldValue().equalsIgnoreCase(
						airlineCN51DetailsVO.getMailcategory())) {
					airlineCN51DetailsVO
					.setMailcategory(mailcategoryVO.getFieldDescription());
				}
			}

		}



		if(cn51FilterVO.getCategoryCode()!=null){
			for (OneTimeVO mailcategoryVO : mailcategory) {
				if (mailcategoryVO.getFieldValue().equalsIgnoreCase(
						cn51FilterVO.getCategoryCode())) {
					cn51FilterVO.setCategoryCode(mailcategoryVO.getFieldDescription());
				}
			}


		}

		if(cn51FilterVO.getInterlineBillingType()!=null){
			for (OneTimeVO billingTypeVO : billingType) {
				if (billingTypeVO.getFieldValue().equalsIgnoreCase(
						cn51FilterVO.getInterlineBillingType())) {
					cn51FilterVO.setInterlineBillingType(billingTypeVO.getFieldDescription());
				}
			}


		}



		log.log(Log.FINE, "collection after iteration ", cn51DetailsVOs);
		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "invoiceReferenceNumber",
						"iataClearancePeriod", "airlineCode","interlineBillingType","categoryCode" ,"carriageStationFrom","carriageStationTo","interlineBillingType"});
		reportspec.addParameterMetaData(parameterMetaData);
		reportspec.addParameter(cn51FilterVO);



		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "CARFRM", "CARTOO",
				"MALSUBCLS", "TOTWGT", "APLRAT", "TOTAMTCRTCUR","MALCTGCOD" });
		reportMetaData.setFieldNames(new String[] { "carriagefrom",
				"carriageto", "mailsubclass", "totalweight", "applicablerate",
				"totalAmount" ,"mailcategory"});
		reportspec.setReportMetaData(reportMetaData);

		reportspec.setData(cn51DetailsVOs);

		log.exiting(CLASS_NAME, "findCN51DetailsReport");

		return ReportAgent.generateReport(reportspec);

	}

	private String getClrPrdForMonthsEnding(String iataclearancePrd) {

		String endingWeek = "04";
		int length = iataclearancePrd.length();
		String clearanceMonth = null;

		clearanceMonth = iataclearancePrd.substring(0, length - 2);
		return new StringBuffer(clearanceMonth).append(endingWeek).toString();

	}

	/**
	 * Method to Save Rejection Memo added by A-2397
	 *
	 * @param memoInInvoiceVos
	 *
	 * @return
	 * @throws SystemException
	 */
	public void saveMemo(Collection<MemoInInvoiceVO> memoInInvoiceVos)
			throws SystemException {
		log.entering("AirlineBillingController", "saveMemo");
		if (memoInInvoiceVos != null && memoInInvoiceVos.size() > 0) {
			for (MemoInInvoiceVO memoInInvoiceVo : memoInInvoiceVos) {
				if (MemoInInvoiceVO.OPERATION_FLAG_INSERT
						.equals(memoInInvoiceVo.getOperationalFlag())) {
					/*
					 * method to find CN51SMRY. If exist update the
					 * contract Curr Code else insert a new row in CN51SMRY
					 */
					AirlineCN51Summary cn51Summary = findCN51Summary(memoInInvoiceVo);
					if (cn51Summary != null) {
						log
								.log(Log.FINE,
										" inside  cn51Summary not null>>> going to update");
						updateCN51Summary(cn51Summary, memoInInvoiceVo);
					} else {
						log
								.log(Log.FINE,
										" inside  cn51Summary null>>>> going to insert");
						insertCN51Summary(null, memoInInvoiceVo);
					}

					insertMemo(memoInInvoiceVo);
				}
				if (MemoInInvoiceVO.OPERATION_FLAG_UPDATE
						.equals(memoInInvoiceVo.getOperationalFlag())) {
					AirlineCN51Summary cn51Summary = findCN51Summary(memoInInvoiceVo);
					if (cn51Summary != null) {
						log
								.log(Log.FINE,
										" inside  cn51Summary not null>>> going to update");
						updateCN51Summary(cn51Summary, memoInInvoiceVo);
					} else {
						log
								.log(Log.FINE,
										" inside  cn51Summary null>>>> going to insert");
						insertCN51Summary(null, memoInInvoiceVo);
					}
					updateMemo(memoInInvoiceVo);
				}
				if (MemoInInvoiceVO.OPERATION_FLAG_DELETE
						.equals(memoInInvoiceVo.getOperationalFlag())) {
					AirlineCN51Summary cn51Summary = findCN51Summary(memoInInvoiceVo);
					if (cn51Summary != null) {
						if (cn51Summary.getTotalAmountInContractCurrency() == memoInInvoiceVo
								.getPreviousDifferenceAmount()) {
							cn51Summary.remove();
						} else {
							double contCurrTotal = 0.0;
							contCurrTotal = cn51Summary
									.getTotalAmountInContractCurrency();
							contCurrTotal = contCurrTotal
									- memoInInvoiceVo
											.getPreviousDifferenceAmount();
							cn51Summary
									.setTotalAmountInContractCurrency(contCurrTotal);
						}
					}
					removeMemo(memoInInvoiceVo);
				}
			}
		}
		log.exiting("AirlineBillingController", "saveMemo");
	}

	/*
	 * method to find CN51Summary.
	 */
	private AirlineCN51Summary findCN51Summary(MemoInInvoiceVO memoInInvoiceVo)
			throws SystemException {
		AirlineCN51Summary cn51Summary = null;
		try {
			cn51Summary = AirlineCN51Summary.find(memoInInvoiceVo
					.getCompanyCode(), memoInInvoiceVo.getAirlineIdentifier(),
					memoInInvoiceVo.getInterlineBlgType(), memoInInvoiceVo
							.getInvoiceNumber(), memoInInvoiceVo
							.getClearancePeriod());
		} catch (FinderException e) {
			// throw new SystemException(e.getErrorCode(), e);
		}
		return cn51Summary;
	}

	/*
	 * method to update CN51Summary.
	 */
	private void updateCN51Summary(AirlineCN51Summary cn51Summary,
			MemoInInvoiceVO memoInInvoiceVo) throws SystemException {
		log.entering("AirlineBillingController", "updateCN51Summary");
		double contCurrTotal = 0.0;
		contCurrTotal = cn51Summary.getTotalAmountInContractCurrency();
		contCurrTotal = contCurrTotal + memoInInvoiceVo.getDifferenceAmount()
				- memoInInvoiceVo.getPreviousDifferenceAmount();
		cn51Summary.setTotalAmountInContractCurrency(contCurrTotal);
		log.exiting("AirlineBillingController", "updateCN51Summary");

	}

	/*
	 * method to insert CN51Summary
	 */
	private void insertCN51Summary(AirlineCN51Summary cn51Summary,
			MemoInInvoiceVO memoInInvoiceVo) throws SystemException {
		log.entering("AirlineBillingController", "insertCN51Summary");
		AirlineCN51SummaryVO cn51SummaryVo = new AirlineCN51SummaryVO();

		cn51SummaryVo.setCompanycode(memoInInvoiceVo.getCompanyCode());
		cn51SummaryVo.setAirlineidr(memoInInvoiceVo.getAirlineIdentifier());
		cn51SummaryVo.setClearanceperiod(memoInInvoiceVo.getClearancePeriod());
		cn51SummaryVo.setInterlinebillingtype(memoInInvoiceVo
				.getInterlineBlgType());
		cn51SummaryVo.setInvoicenumber(memoInInvoiceVo.getInvoiceNumber());
		cn51SummaryVo.setAirlinecode(memoInInvoiceVo.getAirlineCode());
		cn51SummaryVo.setCn51status("NEW");
		cn51SummaryVo.setTotalAmountInContractCurrency(memoInInvoiceVo
				.getDifferenceAmount());

		log.log(Log.FINE, "cn51SummaryVo --->>>> ", cn51SummaryVo);
		try {
			new AirlineCN51Summary(cn51SummaryVo);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode(),
					createException);
		}
		log.exiting("AirlineBillingController", "insertCN51Summary");
	}

	private void insertMemo(MemoInInvoiceVO memoInInvoiceVo)
			throws SystemException {
		log.entering("AirlineBillingController", "insertMemo");
		new MRAMemoInInvoice(memoInInvoiceVo);
		log.exiting("AirlineBillingController", "insertMemo");
	}

	private void updateMemo(MemoInInvoiceVO memoInInvoiceVO)
			throws SystemException {
		log.entering("AirlineBillingController", "updateMemo");
		MRAMemoInInvoice memo = null;
		try {
			memo = MRAMemoInInvoice.find(memoInInvoiceVO.getCompanyCode(),
					memoInInvoiceVO.getAirlineIdentifier(), memoInInvoiceVO
							.getMemoCode(), memoInInvoiceVO.getInvoiceNumber(),
					memoInInvoiceVO.getInterlineBlgType(), memoInInvoiceVO
							.getClearancePeriod());
		} catch (FinderException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
		memo.update(memoInInvoiceVO);
		log.exiting("AirlineBillingController", "updateMemo");
	}

	private void removeMemo(MemoInInvoiceVO memoInInvoiceVO)
			throws SystemException {
		log.entering("AirlineBillingController", "removeMemo");
		MRAMemoInInvoice memo = null;
		try {
			memo = MRAMemoInInvoice.find(memoInInvoiceVO.getCompanyCode(),
					memoInInvoiceVO.getAirlineIdentifier(), memoInInvoiceVO
							.getMemoCode(), memoInInvoiceVO.getInvoiceNumber(),
					memoInInvoiceVO.getInterlineBlgType(), memoInInvoiceVO
							.getClearancePeriod());
		} catch (FinderException e) {
			throw new SystemException(e.getErrorCode(), e);
		}

		memo.remove();
		log.exiting("AirlineBillingController", "removeMemo");
	}

	/**
	 * Method to find Rejection Memo added by A-2397
	 *
	 * @param memoFilterVo
	 * @return Collection<MemoInInvoiceVO>
	 * @throws SystemException
	 */
	public Collection<MemoInInvoiceVO> findMemoDetails(MemoFilterVO memoFilterVo)
			throws SystemException {
		log.entering("AirlineBillingController", "findMemoDetails");
		return MRAMemoInInvoice.findMemoDetails(memoFilterVo);

	}

	/**
	 *
	 * @param companyCode
	 * @param iataClearancePeriod
	 * @return
	 * @throws MailTrackingMRABusinessException
	 */
	public UPUCalendarVO validateIataClearancePeriod(String companyCode,
			String iataClearancePeriod) throws MailTrackingMRABusinessException {
		log.entering(CLASS_NAME, "validateIataClearancePeriod");
		UPUCalendarVO upuClrPrdvalidationVO = null;
		try {
			upuClrPrdvalidationVO = UPUCalendar.validateIataClearancePeriod(
					companyCode, iataClearancePeriod);
		} catch (SystemException e) {
			log.log(Log.INFO, " ############ ", INVALID_CLEARANCE_PERIOD);
			ErrorVO validationErrorVO = new ErrorVO(INVALID_CLEARANCE_PERIOD);
			validationErrorVO.setErrorDisplayType(ErrorDisplayType.ERROR);

			MailTrackingMRABusinessException bussExcep = new MailTrackingMRABusinessException();
			bussExcep.addError(validationErrorVO);

			throw bussExcep;
		}

		log.log(Log.INFO, " ####### the validation VO from server ",
				upuClrPrdvalidationVO);
		return upuClrPrdvalidationVO;

	}

	/**
	 *
	 * @param invoiceLovFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Page<AirlineInvoiceLovVO> displayInvoiceLOV(
			InvoiceLovFilterVO invoiceLovFilterVO) throws SystemException {

		log.entering(CLASS_NAME, "displayInvoiceLOV");
		return AirlineCN51Summary.displayInvoiceLOV(invoiceLovFilterVO);
	}

	/**
	 *
	 * @param companyCode
	 * @param memoCode
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public Page<MemoLovVO> displayMemoLOV(String companyCode, String memoCode,
			int pageNumber) throws SystemException {

		log.entering(CLASS_NAME, "displayInvoiceLOV");
		return ExceptionInInvoice.displayMemoLOV(companyCode, memoCode,
				pageNumber);

	}

	/**
	 * @author a-2049
	 * @param cn51SummaryVO
	 * @throws SystemException
	 */
	public void saveCN51(AirlineCN51SummaryVO cn51SummaryVO)
			throws SystemException {

		log.entering(CLASS_NAME, "saveCN51");
		if (cn51SummaryVO != null) {
			log.log(Log.INFO, " cn51SummaryVO11 from controller......> ",
					cn51SummaryVO);
			String operationMode = cn51SummaryVO.getOperationFlag();

			String companyCode = cn51SummaryVO.getCompanycode();
			String clearancePeriod = cn51SummaryVO.getClearanceperiod();
			String invoiceNumber = cn51SummaryVO.getInvoicenumber();
			int airlineIdr = cn51SummaryVO.getAirlineidr();
			String interlineBlgType = cn51SummaryVO.getInterlinebillingtype();
			String listingCurrency = cn51SummaryVO.getListingCurrency();
			Double c51Amount = cn51SummaryVO.getC51Amount();
			log.log(Log.INFO, " C51Amount......> ", cn51SummaryVO.getC51Amount());
			//Added By Deepthi as a part of CN51 pagination
			AirlineCN51FilterVO airlineCN51FilterVO = new AirlineCN51FilterVO();
			airlineCN51FilterVO.setCompanyCode(cn51SummaryVO.getCn51FilterVO().getCompanyCode());
			airlineCN51FilterVO.setInvoiceReferenceNumber(cn51SummaryVO.getCn51FilterVO().getInvoiceReferenceNumber());
			airlineCN51FilterVO.setIataClearancePeriod(cn51SummaryVO.getCn51FilterVO().getIataClearancePeriod());
			airlineCN51FilterVO.setAirlineCode(cn51SummaryVO.getCn51FilterVO().getAirlineCode()
					.toUpperCase());
			airlineCN51FilterVO.setAirlineIdentifier(cn51SummaryVO.getCn51FilterVO().getAirlineIdentifier());
			airlineCN51FilterVO.setInterlineBillingType(cn51SummaryVO.getCn51FilterVO().getInterlineBillingType()
					.toUpperCase());
			AirlineCN51SummaryVO summaryVOfetched=findCN51DetailColection(airlineCN51FilterVO);
			log.log(Log.INFO, " summaryVOfetched from controller......> ",
					summaryVOfetched);
			Collection<AirlineCN51DetailsVO> detailVOsToBeRemoved=new ArrayList<AirlineCN51DetailsVO>();
			Collection<AirlineCN51DetailsVO> detailVOsToBeUpdated=new ArrayList<AirlineCN51DetailsVO>();
			if(summaryVOfetched!=null){
				if(summaryVOfetched.getCn51DetailsVOs()!=null
						&& summaryVOfetched.getCn51DetailsVOs().size()>0){
					for(AirlineCN51DetailsVO mainAirlineCN51DetailsVO:summaryVOfetched.getCn51DetailsVOs()){
						for(AirlineCN51DetailsVO airlineCN51DetailVO:cn51SummaryVO.getCn51DetailsPageVOs()){
							if(mainAirlineCN51DetailsVO.getCompanycode().equals(airlineCN51DetailVO.getCompanycode())
									&& mainAirlineCN51DetailsVO.getAirlineidr()==(airlineCN51DetailVO.getAirlineidr())
									&& mainAirlineCN51DetailsVO.getInvoicenumber().equals(airlineCN51DetailVO.getInvoicenumber())
									&& mainAirlineCN51DetailsVO.getInterlinebillingtype().equals(airlineCN51DetailVO.getInterlinebillingtype())
									&& mainAirlineCN51DetailsVO.getSequenceNumber()==(airlineCN51DetailVO.getSequenceNumber())
									&& mainAirlineCN51DetailsVO.getClearanceperiod().equals(airlineCN51DetailVO.getClearanceperiod())){
										detailVOsToBeRemoved.add(mainAirlineCN51DetailsVO)	;
							}
						}
					}
					summaryVOfetched.getCn51DetailsVOs().removeAll(detailVOsToBeRemoved);


				}
			}
			if (cn51SummaryVO != null) {
				for (AirlineCN51DetailsVO airlineCN51DetailVO : cn51SummaryVO
						.getCn51DetailsPageVOs()) {
					AirlineCN51DetailsVO cN51DetailVO = new AirlineCN51DetailsVO();
					try {
						BeanHelper.copyProperties(cN51DetailVO,
								airlineCN51DetailVO);
					} catch (SystemException e) {
						e.getMessage();
					}
					detailVOsToBeUpdated.add(cN51DetailVO);
				}
				summaryVOfetched.getCn51DetailsVOs().addAll(
						detailVOsToBeUpdated);
			}
			/*
			 * Collection<AirlineCN51DetailsVO> unsortedDetailsVOs = null;
			 *
			 * this is collection send from client...this has only those vos
			 * which are for insertion ..ie Operation flag is "I" ... even if
			 * "U" flag is present we will try to insert that detail....this
			 * collection need to be initialized on any operation other than
			 * delete cn51summary operation
			 */
			/*
			 * if(!operationMode.equals(OPERATION_FLAG_DELETE)){
			 * unsortedDetailsVOs = cn51SummaryVO.getCn51DetailsVOs(); }
			 */

			log
					.log(
							Log.INFO,
							" summaryVOfetched from controller before performSectorWiseCategorization......> ",
							summaryVOfetched);
			performSectorWiseCategorization(summaryVOfetched);
			log
					.log(
							Log.INFO,
							" summaryVOfetched from controller after performSectorWiseCategorization......> ",
							summaryVOfetched);
			log.log(Log.INFO, " OPERATION_FLAG-......> ", operationMode);
			if (operationMode != null && operationMode.length() > 0) {

				/*
				 * enters if the CN51 capture is done for the first time for the
				 * airline invoice
				 */

				if (OPERATION_FLAG_INSERT.equals(operationMode)) {

					try {
//						//AirlineCN51Summary airlineCN51Summary = new AirlineCN51Summary(
//								cn51SummaryVO);
						AirlineCN51Summary airlineCN51Summary = AirlineCN51Summary.find(companyCode,airlineIdr,
								interlineBlgType,invoiceNumber,clearancePeriod);
						airlineCN51Summary.setC51Amount(c51Amount);
						AirlineCN51Details cN51Detail = null;
						if(summaryVOfetched !=null &&summaryVOfetched.getCn51DetailsVOs()!=null && summaryVOfetched.getCn51DetailsVOs().size()>0){
						for (AirlineCN51DetailsVO airlineCN51DetailVO : summaryVOfetched.getCn51DetailsVOs())
						{
							log.log(Log.INFO, " <-- insert child ---> ");
							log.log(Log.INFO, " the vo for insertion ",
									airlineCN51DetailVO);
							if("ULD".equals(airlineCN51DetailVO.getMailsubclass())){
								airlineCN51DetailVO.setMailsubclass("UL");
							}
							else if("SAL".equals(airlineCN51DetailVO.getMailsubclass())){
								airlineCN51DetailVO.setMailsubclass("SL");
							}
							cN51Detail = new AirlineCN51Details(airlineCN51DetailVO);
							if (airlineCN51Summary.getAirlineCN51Details() == null
									||airlineCN51Summary.getAirlineCN51Details().size() <= 0) {
								log.log(Log.INFO, " -- adding for first time ---- ");
								airlineCN51Summary
										.setAirlineCN51Details(new HashSet<AirlineCN51Details>());
							}
							log.log(Log.INFO,
									" --ListingCurrency --in summary--- ",
									listingCurrency);
							cN51Detail.setContractcurrencycode(listingCurrency);
							cN51Detail.setBillingcurrencycode("USD");
							airlineCN51Summary.getAirlineCN51Details().add(cN51Detail);

						}}
						
log
								.log(
										Log.INFO,
										" summaryVOfetched from controller after insert loop......> ",
										summaryVOfetched);

					} catch (CreateException createException) {
						log.log(Log.SEVERE, " #### Capture CN51 failed ");
						throw new SystemException(createException.getMessage(),
								createException);
					}
					catch (FinderException finderException) {
						log.log(Log.SEVERE, " #### Capture CN51 failed ");
						throw new SystemException(finderException.getMessage(),
								finderException);
					}

				}

				/*
				 * enters if it is operation is the modification for an existing
				 * airline invoice
				 */
				else if (OPERATION_FLAG_UPDATE.equals(operationMode)) {

					try {
						AirlineCN51Summary entityForUpdate = AirlineCN51Summary
								.find(companyCode, airlineIdr,
										interlineBlgType, invoiceNumber,
										clearancePeriod);

						//						// deletes any previous detail present in db
						//						entityForUpdate.removeAllDetails();
						//						// updation of the existing CN51 record
						//						entityForUpdate.update(cn51SummaryVO);

						log
								.log(Log.FINEST,
										"before calling Audit in case of update................>>>>>");
						/// calling audit
						// PostalAdministration postalAdministration = new PostalAdministration();
						StringBuilder auditRemarks = new StringBuilder();
						AirlineBillingAuditVO airlineBillingAuditVO = new AirlineBillingAuditVO(
								AirlineBillingAuditVO.AUDIT_MODULENAME,
								AirlineBillingAuditVO.AUDIT_SUBMODULENAME,
								AirlineBillingAuditVO.AUDIT_ENTITYCN51SMY);
						airlineBillingAuditVO
								.setActionCode(AirlineBillingAuditVO.AUDIT_CN51_UPDATED);
						auditRemarks.append("CN66 updated for Airline->"
								+ (String.valueOf(summaryVOfetched
										.getAirlinecode())));
						airlineBillingAuditVO
								.setAdditionalInformation(auditRemarks
										.toString());
						airlineBillingAuditVO.setAuditRemarks(auditRemarks
								.toString());
						airlineBillingAuditVO = (AirlineBillingAuditVO) AuditUtils
								.populateAuditDetails(airlineBillingAuditVO,
										entityForUpdate, false);
						LogonAttributes logonAttributes = ContextUtils
								.getSecurityContext().getLogonAttributesVO();
						String userId = logonAttributes.getUserId();
						airlineBillingAuditVO.setUserId(userId);

						/*
						 * The below code first removes the CN51 Details and
						 * then inserts the CN51 Details into the db.
						 *
						 * This is done to reduce the complexity of code and
						 * the bussiness in the CN51 save, where grouping is done.
						 */
						if(summaryVOfetched.getCn51DetailsVOs() != null &&
								summaryVOfetched.getCn51DetailsVOs().size() > 0) {
							entityForUpdate.removeAllDetails();
							entityForUpdate.populateAirlineCN51ChildDetails(summaryVOfetched.getCn51DetailsVOs());
						}
						log.log(Log.FINE, "AirlineCN51Smy has been updated");

						airlineBillingAuditVO = (AirlineBillingAuditVO) AuditUtils
								.populateAuditDetails(airlineBillingAuditVO,
										entityForUpdate, false);
						airlineBillingAuditVO = populateAirlineCN51AuditDetails(
								airlineBillingAuditVO, entityForUpdate);
						log
								.log(
										Log.INFO,
										"AuditVo before calling performAudit during UPDATE case ->",
										airlineBillingAuditVO);
						AuditUtils.performAudit(airlineBillingAuditVO);
						log.log(Log.FINE,
								"airline audit performed in Update case");
						log.log(Log.INFO,
								" --summary vO fetched in update--- ",
								summaryVOfetched);
					} catch (FinderException finderException) {
						log.log(Log.SEVERE, " #### updation failed ");
						throw new SystemException(finderException.getMessage(),
								finderException);
					}
				}
				/*
				 * enters if the action is delete
				 */
				else if (OPERATION_FLAG_DELETE.equals(operationMode)) {

					AirlineCN51Summary entityForRemove = null;
					try {
						entityForRemove = AirlineCN51Summary.find(companyCode,
								airlineIdr, interlineBlgType, invoiceNumber,
								clearancePeriod);
					} catch (FinderException finderException) {
						log.log(Log.SEVERE, " #### deletion failed ");
						throw new SystemException(finderException.getMessage(),
								finderException);
					}

					double totalAmtInCrtCur = entityForRemove
							.getTotalAmountInContractCurrency();
					double totalAmtForC51Dtls = entityForRemove
							.calculateC51DtlsTotalAmtInCrtCurrency();
					double differenceAmount = totalAmtInCrtCur
							- totalAmtForC51Dtls;

					if (differenceAmount != 0) {
						/*
						 * CN51 cannot be deleted only CN51Details can be
						 * deleted
						 */
						entityForRemove.removeAllDetails();
						entityForRemove
								.setTotalAmountInContractCurrency(differenceAmount);
					} else {
						/* CN51 is safe for deletion */
						// calling audit while Delete
						StringBuilder auditRemarks = new StringBuilder();
						AirlineBillingAuditVO airlineBillingAuditVO = new AirlineBillingAuditVO(
								AirlineBillingAuditVO.AUDIT_MODULENAME,
								AirlineBillingAuditVO.AUDIT_SUBMODULENAME,
								AirlineBillingAuditVO.AUDIT_ENTITYCN51SMY);
						airlineBillingAuditVO
								.setActionCode(AirlineBillingAuditVO.AUDIT_CN51_DELETED);
						auditRemarks.append("CN51 Deleted for Airline->"
								+ (String.valueOf(summaryVOfetched
										.getAirlinecode())));
						airlineBillingAuditVO
								.setAdditionalInformation(auditRemarks
										.toString());
						airlineBillingAuditVO.setAuditRemarks(auditRemarks
								.toString());
						airlineBillingAuditVO = (AirlineBillingAuditVO) AuditUtils
								.populateAuditDetails(airlineBillingAuditVO,
										entityForRemove, false);
						LogonAttributes logonAttributes = ContextUtils
								.getSecurityContext().getLogonAttributesVO();
						String userId = logonAttributes.getUserId();
						airlineBillingAuditVO.setUserId(userId);
						airlineBillingAuditVO = populateAirlineCN51AuditDetails(
								airlineBillingAuditVO, entityForRemove);
						log.log(Log.INFO,
								"AuditVo before calling performAudit ->",
								airlineBillingAuditVO);
						entityForRemove.remove();
						log.log(Log.FINE, "AirlineCN51Smy has been removed");
						//calling performAudit
						AuditUtils.performAudit(airlineBillingAuditVO);
						log.log(Log.FINE,
								"airline audit performed during Delete");

					}

				}

			}

			log.log(Log.INFO, " <-- VOS for balancing --> ", summaryVOfetched.getCn51DetailsVOs());
			this.balanceCN51CN66s(companyCode, airlineIdr, invoiceNumber,
					interlineBlgType, clearancePeriod, summaryVOfetched
							.getCn51DetailsVOs());

		} // end of if(cn51smy_client_VO != null ){

		log.exiting(CLASS_NAME, "saveCN51");
	}

	/**
	 * @param airlineBillingAuditVO
	 * @param airlineCN51Summary
	 * @return
	 */
	private AirlineBillingAuditVO populateAirlineCN51AuditDetails(
			AirlineBillingAuditVO airlineBillingAuditVO,
			AirlineCN51Summary airlineCN51Summary) {
		StringBuffer additionalInfo = new StringBuffer();
		log.log(Log.INFO, "Inside findDcmAuditDetails -> populating fields");
		airlineBillingAuditVO.setCompanyCode(airlineCN51Summary
				.getAirlineCN51SummaryPK().getCompanyCode());
		airlineBillingAuditVO.setAirlineIdentifier(airlineCN51Summary
				.getAirlineCN51SummaryPK().getAirlineIdentifier());
		//postalAdministrationAuditVO.setUserId("System");
		if (airlineBillingAuditVO.getAuditFields() != null
				&& airlineBillingAuditVO.getAuditFields().size() > 0) {
			for (AuditFieldVO auditField : airlineBillingAuditVO
					.getAuditFields()) {
				if (auditField != null) {
					log.log(Log.INFO, "Inside AuditField Not Null");
					additionalInfo.append(" Field Name: ").append(
							auditField.getFieldName()).append(
							" Field Description: ").append(
							auditField.getDescription()).append(" Old Value: ")
							.append(auditField.getOldValue()).append(
									" New Value: ").append(
									auditField.getNewValue());
				}
			}
		}
		//airlineBillingAuditVO.setAdditionalInformation(additionalInfo.toString());
		return airlineBillingAuditVO;
	}

	/**
	 * method for categorizing and clubbing a collection of AirlineCN51DetailsVO
	 * to a single AirlineCN51DetailsVO if they belong to a same category
	 * ...this means they got the same origin-destination-category-subclass-rate
	 *
	 * @param cn51summaryVO
	 * @return Collection<AirlineCN51DetailsVO>
	 */
	private Collection<AirlineCN51DetailsVO> performSectorWiseCategorization(
			AirlineCN51SummaryVO cn51summaryVO) {

		log.entering(CLASS_NAME, "performSectorWiseOrdering");
		Collection<AirlineCN51DetailsVO> sortedCN51VOs = null;

		if (cn51summaryVO.getCn51DetailsVOs() != null
				&& cn51summaryVO.getCn51DetailsVOs().size() > 0) {

			sortedCN51VOs = new ArrayList<AirlineCN51DetailsVO>();
			String sectorKey = null;
			Collection<AirlineCN51DetailsVO> detailsForStorage = null;
			Map<String, Collection<AirlineCN51DetailsVO>> sectorMap = new HashMap<String, Collection<AirlineCN51DetailsVO>>();

			for (AirlineCN51DetailsVO detailVO : cn51summaryVO
					.getCn51DetailsVOs()) {

				sectorKey = new StringBuffer(detailVO.getCarriagefrom())
						.append(STRING_VALUE_HYPHEN).append(
								detailVO.getCarriageto()).append(
								STRING_VALUE_HYPHEN).append(
								detailVO.getMailcategory()).append(
								STRING_VALUE_HYPHEN).append(
								detailVO.getMailsubclass()).append(
								STRING_VALUE_HYPHEN).append(
								detailVO.getApplicablerate()).toString();

				if (sectorMap.get(sectorKey) != null) {
					sectorMap.get(sectorKey).add(detailVO);
				} else {
					detailsForStorage = new ArrayList<AirlineCN51DetailsVO>();
					detailsForStorage.add(detailVO);
					sectorMap.put(sectorKey, detailsForStorage);
				}
			}

			Collection<AirlineCN51DetailsVO> storredDetailsInMap = null;
			AirlineCN51DetailsVO totalDetailsVO = null;
			int seqNum = 0;
			for (Entry<String, Collection<AirlineCN51DetailsVO>> entryInsideMap : sectorMap
					.entrySet()) {

				storredDetailsInMap = entryInsideMap.getValue();
				double totalWeight = 0.0;
				Money totalAmt=null;
				for (AirlineCN51DetailsVO storredDetail : storredDetailsInMap) {
					totalWeight = totalWeight + storredDetail.getTotalweight();
				}
				try{
					totalAmt=CurrencyHelper.getMoney(cn51summaryVO.getListingCurrency());
					totalAmt.setAmount(0.0D);

					for (AirlineCN51DetailsVO storredDetail : storredDetailsInMap) {
						if(storredDetail.getTotalamountincontractcurrency()!=null)
						{
						totalAmt.plusEquals(storredDetail.getTotalamountincontractcurrency());
						}
					}
				}
				catch(CurrencyException currencyException){
					log.log(Log.INFO,"CurrencyException found");
				}
				// constrcuting a vo for the storining the summed details
				StringTokenizer tokenizer = new StringTokenizer(entryInsideMap
						.getKey(), STRING_VALUE_HYPHEN, false);
				String[] keyArray = new String[tokenizer.countTokens()+1];
				int index = 0;
				while (tokenizer.hasMoreTokens()) {
					keyArray[index] = tokenizer.nextToken();
					index++;
				}

				totalDetailsVO = new AirlineCN51DetailsVO();

				index = 0;
				totalDetailsVO.setCarriagefrom(keyArray[index]);
				index++;
				totalDetailsVO.setCarriageto(keyArray[index]);
				index++;
				totalDetailsVO.setMailcategory(keyArray[index]);
				index++;
				totalDetailsVO.setMailsubclass(keyArray[index]);
				index++;
				totalDetailsVO.setApplicablerate(Double
						.parseDouble(keyArray[index]));
				index++;

				totalDetailsVO.setCompanycode(cn51summaryVO.getCompanycode());
				totalDetailsVO.setAirlineidr(cn51summaryVO.getAirlineidr());
				totalDetailsVO.setInvoicenumber(cn51summaryVO
						.getInvoicenumber());
				totalDetailsVO.setInterlinebillingtype(cn51summaryVO
						.getInterlinebillingtype());
				totalDetailsVO.setSequenceNumber(++seqNum);
				totalDetailsVO.setClearanceperiod(cn51summaryVO
						.getClearanceperiod());
				totalDetailsVO.setOperationFlag("I");
				totalDetailsVO.setTotalweight(totalWeight);
				totalDetailsVO.setTotalamountincontractcurrency(totalAmt);
				sortedCN51VOs.add(totalDetailsVO);

			}

		}

		cn51summaryVO.setCn51DetailsVOs(sortedCN51VOs);
		return sortedCN51VOs;
	}

	/**
	 * Method to update CN66 and CN51 staus
	 *
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param invoiceNumber
	 * @param interlineBillingType
	 * @param clearancePeriod
	 * @param isBalanced
	 * @param airlineCn66Details
	 * @throws SystemException
	 * @author a-2518
	 */
	private void updateCN51CN66Status(String companyCode,
			int airlineIdentifier, String invoiceNumber,
			String interlineBillingType, String clearancePeriod,
			boolean isBalanced,
			Collection<AirlineCN66DetailsVO> airlineCn66Details)
			throws SystemException {
		log.entering(CLASS_NAME, "updateCN51CN66Status");
		if (isBalanced) {
			log.log(Log.FINE, "&&&&&&& BALANCED &&&&&&&&&");
		} else {
			log.log(Log.FINE, "&&&&&&& UN BALANCED &&&&&&&&&");
		}
		AirlineCN51Summary airlineCn51Summary = null;
		try {
			airlineCn51Summary = AirlineCN51Summary.find(companyCode,
					airlineIdentifier, interlineBillingType, invoiceNumber,
					clearancePeriod);
		} catch (FinderException finderException) {
			log
					.log(Log.SEVERE,
							"FinderException occurred in finding AirlineCN51Summary Entity");
		}
		//if (airlineCn51Summary != null) {
		if (airlineCn51Summary.getAirlineCN51Details()!= null) {
			//airlineCn51Summary
					//.setCn51status(isBalanced ? BALANCED : UNBALANCED);
			airlineCn51Summary.setInvStatus(isBalanced ? BALANCED : UNBALANCED);
			log.log(Log.FINE, "CN51 Status has been updated");
		}
		if (airlineCn66Details != null && airlineCn66Details.size() > 0) {
			for (AirlineCN66DetailsVO airlineCn66DetailsVo : airlineCn66Details) {
				airlineCn66DetailsVo.setOperationFlag(OPERATION_FLAG_UPDATE);
				// if (isBalanced) {
				if(airlineCn66DetailsVo.getDespatchStatus()!=null && "P".equals(airlineCn66DetailsVo.getDespatchStatus())){
					log.log(Log.FINE, "Despatch Status has been Updated******");

				}else if("B".equals(airlineCn66DetailsVo.getDespatchStatus())
				){
					log.log(Log.FINE, "airlineCn66DetailsVo.getDespatchStatus() is B");
				}else{

					airlineCn66DetailsVo.setDespatchStatus(isBalanced ? BALANCED
							: UNBALANCED);
				}
				AirlineCN66Details cn66Details = null;
				try {
					cn66Details = AirlineCN66Details.find(airlineCn66DetailsVo
							.getCompanyCode(), airlineCn66DetailsVo
							.getAirlineIdentifier(), airlineCn66DetailsVo
							.getInvoiceNumber(), airlineCn66DetailsVo
							.getInterlineBillingType(), airlineCn66DetailsVo
							.getSequenceNumber(), airlineCn66DetailsVo
							.getClearancePeriod(),
							airlineCn66DetailsVo.getDsnIdr(),
							airlineCn66DetailsVo.getMalSeqNum()
							);
				} catch (FinderException finderException) {
					log
							.log(Log.SEVERE,
									"FINDER EXCEPTION OCCURED IN FINDING AirlineCN66Details Entity");
				}
				if (cn66Details != null) {
					cn66Details.update(airlineCn66DetailsVo);
					log.log(Log.FINE, "Despatch Status has been Updated");
				}
				// }
			}
		}
		log.exiting(CLASS_NAME, "updateCN51CN66Status");
	}

	/**
	 *
	 * @author A-2399
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 *
	 */
	public Map<String, Object> printExceptionInInvoice(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException {

		log.entering(CLASS_NAME, "printExceptionInInvoice");
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		List<OneTimeVO> statusVOs = new ArrayList<OneTimeVO>();
		List<OneTimeVO> memoStatusVOs = new ArrayList<OneTimeVO>();

		ExceptionInInvoiceFilterVO filterVO = (ExceptionInInvoiceFilterVO) reportSpec
				.getFilterValues().iterator().next();
		log.log(Log.FINE, "ExceptionInInvoiceFilterVO==>>> ", filterVO);
		Collection<ExceptionInInvoiceVO> invoices = null;
		invoices= ExceptionInInvoice.findAirlineExceptionInInvoicesForReport(filterVO);
		log.log(Log.INFO, "invoices", invoices);
		ErrorVO errVO = new ErrorVO(
				MailTrackingMRABusinessException.MAILTACKING_MRA_AIRLINEBILLING_NOREPORTDATA);
		errVO.setErrorDisplayType(ErrorDisplayType.ERROR);
		MailTrackingMRABusinessException mraExcn = new MailTrackingMRABusinessException();
		mraExcn.addError(errVO);
		if (invoices == null || invoices.size() <= 0) {
			throw mraExcn;
		}
		log.log(Log.FINE, " **** Number of records Found ---> ", invoices.size());
		SharedDefaultsProxy defaultsproxy = new SharedDefaultsProxy();
		oneTimeList.add(EXCEPTION_STATUS);
		oneTimeList.add(MEMO_STATUS);

		try {
			oneTimeValues = defaultsproxy.findOneTimeValues(filterVO
					.getCompanyCode(), oneTimeList);
		} catch (ProxyException e) {
			ErrorVO errorVO = null;
			for (ErrorVO error : e.getErrors()) {
				errorVO = error;
				break;
			}
			throw new SystemException(errorVO.getErrorCode(), e);
		}
		if (oneTimeValues != null) {
			statusVOs = (ArrayList<OneTimeVO>) oneTimeValues
					.get(EXCEPTION_STATUS);

		}
		if (oneTimeValues != null) {
			memoStatusVOs = (ArrayList<OneTimeVO>) oneTimeValues
					.get(MEMO_STATUS);

		}

		for (ExceptionInInvoiceVO invoiceVO : invoices) {
			for (OneTimeVO statusVO : statusVOs) {
				if (statusVO.getFieldValue().equalsIgnoreCase(
						invoiceVO.getExceptionStatus())) {
					invoiceVO
							.setExceptionStatus(statusVO.getFieldDescription());
				}
			}

		}
		for (OneTimeVO statusVO : statusVOs) {
			if (statusVO.getFieldValue().equalsIgnoreCase(
					filterVO.getExceptionStatus())) {
				filterVO
						.setExceptionStatus(statusVO.getFieldDescription());
			}
		}
		for (ExceptionInInvoiceVO invoiceVO : invoices) {
			for (OneTimeVO memoStatusVO : memoStatusVOs) {
				if (memoStatusVO.getFieldValue().equalsIgnoreCase(
						invoiceVO.getMemoStatus())) {
					invoiceVO.setMemoStatus(memoStatusVO.getFieldDescription());
				}
			}

		}
		for (OneTimeVO memoStatusVO : memoStatusVOs) {
			if (memoStatusVO.getFieldValue().equalsIgnoreCase(
					filterVO.getMemoStatus())) {
				filterVO.setMemoStatus(memoStatusVO.getFieldDescription());
			}
		}

		ReportMetaData parameterMetaData = new ReportMetaData();
   	 	parameterMetaData.setFieldNames(new String[] {"airlineCode", "invoiceNumber", "clearancePeriod",
   	 												"exceptionStatus", "memoCode", "memoStatus"});
   	 	reportSpec.addParameterMetaData(parameterMetaData);
   	 	reportSpec.addParameter(filterVO);
		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "INVNUM", "CLRPRD",
				"PVNAMT", "RPDAMT", "DIFAMT", "MEMCOD", "EXPSTA", "MEMSTA",
				"RMK","CRTCUR"});
		reportMetaData.setFieldNames(new String[] { "invoiceNumber",
				"clearancePeriod", "provisionalAmount", "reportedAmount",
				"differenceAmount", "memoCode", "exceptionStatus",
				"memoStatus","remark","contractCurrency"});
		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(invoices);
		log.exiting(CLASS_NAME, "printExceptionInInvoice");

		return ReportAgent.generateReport(reportSpec);
	}

	/**
	 * @param cn66FilterVo
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public void processMail(AirlineCN66DetailsFilterVO cn66FilterVo)
			throws SystemException, MailTrackingMRABusinessException {
		String outparameter = AirlineCN66Details.processMail(cn66FilterVo);

		if ("OK".equals(outparameter)) {
			log.log(Log.INFO, "Procedure executed successfully");
		} else if ("Clearance Period not found".equals(outparameter)) {
			log.log(Log.INFO, "Clearance Period not found");
			ErrorVO errorVO = new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_EXCEPTION_PROCESSMAIL_CLRPERIOD);
			errorVO.setErrorDisplayType(ErrorDisplayType.INFO);
			MailTrackingMRABusinessException mraExcn = new MailTrackingMRABusinessException();
			mraExcn.addError(errorVO);
			throw mraExcn;
		} else if ("No valid summary details".equals(outparameter)) {
			log.log(Log.INFO, "No valid summary details");
			ErrorVO errorVO = new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_EXCEPTION_PROCESSMAIL_NOSMYDETAILS);
			errorVO.setErrorDisplayType(ErrorDisplayType.INFO);
			MailTrackingMRABusinessException mraExcn = new MailTrackingMRABusinessException();
			mraExcn.addError(errorVO);
			throw mraExcn;
		} else if ("No records for processing".equals(outparameter)) {
			log.log(Log.INFO, "No records for processing");
			ErrorVO errorVO = new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_EXCEPTION_PROCESSMAIL_NORECORDS);
			errorVO.setErrorDisplayType(ErrorDisplayType.INFO);
			MailTrackingMRABusinessException mraExcn = new MailTrackingMRABusinessException();
			mraExcn.addError(errorVO);
			throw mraExcn;
		} else {
			log.log(Log.INFO, "Procedure failed");
			ErrorVO errorVO = new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_EXCEPTION_PROCESSMAIL_FAILED);
			errorVO.setErrorDisplayType(ErrorDisplayType.INFO);
			MailTrackingMRABusinessException mraExcn = new MailTrackingMRABusinessException();
			mraExcn.addError(errorVO);
			throw mraExcn;
		}

	}

	/**
	 *
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 * @author A-2391
	 */
	public Map<String, Object> printExceptionReportDetail(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException {
		log.entering("AirlineBillingController", "printExceptionReportDetail");


		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		List<OneTimeVO> statusVOs = new ArrayList<OneTimeVO>();

		AirlineExceptionsFilterVO filterVo = (AirlineExceptionsFilterVO) reportSpec
				.getFilterValues().get(0);
		log.log(Log.FINE, "filterVo ", filterVo);
		Collection<AirlineExceptionsVO> airlineExceptionsVOs = null;
		airlineExceptionsVOs = AirlineExceptions
				.printExceptionReportDetail(filterVo);

		log.log(Log.FINE, "airlineExceptionsVOs ", airlineExceptionsVOs);
		log.log(Log.FINE, "----------------");
		SharedDefaultsProxy defaultsproxy = new SharedDefaultsProxy();
		oneTimeList.add(EXCEPTION_CODES);
		try {
			oneTimeValues = defaultsproxy.findOneTimeValues(filterVo
					.getCompanyCode(), oneTimeList);
		} catch (ProxyException e) {
			ErrorVO errorVO = null;
			for (ErrorVO error : e.getErrors()) {
				errorVO = error;
				break;
			}
			throw new SystemException(errorVO.getErrorCode(), e);
		}
		if (oneTimeValues != null) {
			statusVOs = (ArrayList<OneTimeVO>) oneTimeValues
					.get(EXCEPTION_CODES);

		}
		for (AirlineExceptionsVO airlineExceptionsVO : airlineExceptionsVOs) {
			for (OneTimeVO statusVO : statusVOs) {
				if (statusVO.getFieldValue().equalsIgnoreCase(
						airlineExceptionsVO.getExceptionCode())) {
					airlineExceptionsVO.setExceptionCode(statusVO
							.getFieldDescription());
					log.log(Log.FINE, "vo values", statusVO.getFieldDescription());

				}
			}

		}
		for (OneTimeVO statusVO : statusVOs) {
			if (statusVO.getFieldValue().equalsIgnoreCase(
					filterVo.getExceptionCode())) {
				filterVo.setExceptionCode(statusVO
						.getFieldDescription());
				log.log(Log.FINE, "vo values", statusVO.getFieldDescription());

			}
		}
		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "frmDate",
				"toDat", "airlineCode" , "exceptionCode", "invoiceRefNumber", "despatchSerNo"});
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(filterVo);
		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "INVNUM", "CLRPRD", "DSN",
				"EXPCOD", "PVNRAT", "PVNWGT", "RPDWGT", "MEMCOD", "ASGCOD",
				"ASGDAT","PVNAMT","RPDAMT","MEMSTA","RMK" });
		reportMetaData.setFieldNames(new String[] { "invoiceNumber",
				"clearancePeriod", "despatchSerNo", "exceptionCode",
				"provRate", "provWeight", "rptdWeight", "memCode",
				"assigneeCode", "assignedDate","provAmt","reportedAmt",
				"memStaus","remark"});
		reportSpec.setReportMetaData(reportMetaData);
		if (airlineExceptionsVOs != null && airlineExceptionsVOs.size() > 0) {

			reportSpec.setData(airlineExceptionsVOs);
		} else {
			ErrorVO errorVO = new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_EXCEPTION_NOREPORTDATA);
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			mailTrackingMRABusinessException.addError(errorVO);
			throw mailTrackingMRABusinessException;
		}

		// reportSpec.setData(salesReportVOs);
		return ReportAgent.generateReport(reportSpec);
	}

	/**
	 *

	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 * @author A-2391
	 */

	public Map<String, Object> printRejectionMemo(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException {
		log.entering("AirlineBillingController", "printRejectionMemo");
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		List<OneTimeVO> statusVOs = new ArrayList<OneTimeVO>();

		RejectionMemoFilterVO filterVO = (RejectionMemoFilterVO) reportSpec
				.getFilterValues().get(0);
		log.log(Log.FINE, "filterVo ", filterVO);
		RejectionMemoVO rejectionMemoVO = null;
		rejectionMemoVO = RejectionMemo.findRejectionMemo(filterVO);
		reportSpec.addParameter(rejectionMemoVO);
		log.log(Log.FINE, "rejectionMemoVO ", rejectionMemoVO);
		log.log(Log.FINE, "----------------");
		AirlineExceptionsFilterVO exceptionFilterVO = new AirlineExceptionsFilterVO();
		exceptionFilterVO.setCompanyCode(filterVO.getCompanyCode());
		exceptionFilterVO.setInvoiceRefNumber(filterVO.getInvoiceNumber());
		exceptionFilterVO.setClearancePeriod(filterVO.getClearancePeriod());
		exceptionFilterVO.setAirlineIdentifier(filterVO.getAirlineIdentifier());
		log.log(Log.FINE, "filterVo ", exceptionFilterVO);
		Collection<AirlineExceptionsVO> airlineExceptionsVOs = null;
		airlineExceptionsVOs = AirlineExceptions
				.findAirlineExceptions(exceptionFilterVO);

		log.log(Log.FINE, "airlineExceptionsVOs ", airlineExceptionsVOs);
		log.log(Log.FINE, "----------------");
		SharedDefaultsProxy defaultsproxy = new SharedDefaultsProxy();
		oneTimeList.add(EXCEPTION_CODES);
		try {
			oneTimeValues = defaultsproxy.findOneTimeValues(exceptionFilterVO
					.getCompanyCode(), oneTimeList);
		} catch (ProxyException e) {
			ErrorVO errorVO = null;
			for (ErrorVO error : e.getErrors()) {
				errorVO = error;
				break;
			}
			throw new SystemException(errorVO.getErrorCode(), e);
		}
		if (oneTimeValues != null) {
			statusVOs = (ArrayList<OneTimeVO>) oneTimeValues
					.get(EXCEPTION_CODES);

		}
		for (AirlineExceptionsVO airlineExceptionsVO : airlineExceptionsVOs) {
			for (OneTimeVO statusVO : statusVOs) {
				if (statusVO.getFieldValue().equalsIgnoreCase(
						airlineExceptionsVO.getExceptionCode())) {
					airlineExceptionsVO.setExceptionCode(statusVO
							.getFieldDescription());
					log.log(Log.FINE, "vo values", statusVO.getFieldDescription());
				}
			}

		}

		/*ReportMetaData reportMetaData = new ReportMetaData();
		 reportMetaData.setColumnNames(new String[] { "INVNUM", "CLRPRD", "DSN",
		 "EXPCOD", "PVNRAT", "PVNWGT", "RPTWGT"});
		 reportMetaData.setFieldNames(new String[] { "invoiceNumber",
		 "clearancePeriod", "despatchSerNo", "exceptionCode",
		 "provRate", "provWeight", "rptdWeight"
		 });
		 reportSpec.setReportMetaData(reportMetaData);*/

		if (airlineExceptionsVOs != null && airlineExceptionsVOs.size() > 0) {

			reportSpec.setData(airlineExceptionsVOs);
		} else {
			ErrorVO errorVO = new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_EXCEPTION_NOREPORTDATA);
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			mailTrackingMRABusinessException.addError(errorVO);
			throw mailTrackingMRABusinessException;
		}

		// reportSpec.setData(salesReportVOs);
		return ReportAgent.generateReport(reportSpec);
	}

	/**
	 * This method generates invoices for outward billing
	 *
	 * @author a-2521
	 * @param invoiceFilterVO
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public void generateOutwardBillingInvoice(InvoiceLovFilterVO invoiceFilterVO)
			throws SystemException, MailTrackingMRABusinessException {

		String outParameter = MRAAirlineBilling
				.generateOutwardBillingInvoice(invoiceFilterVO);

		String result = "OK";
		if (!result.equalsIgnoreCase(outParameter)) {

			MailTrackingMRABusinessException exception = new MailTrackingMRABusinessException();
			exception
					.addError(new ErrorVO(
							MailTrackingMRABusinessException.MAILTACKING_MRA_EXCEPTION_GENINV_FAILED));
			throw exception;
		}
	}

	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findInvoiceDetailsForReport(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException {
		log.log(Log.INFO, " inside generateInvoiceReport");
		AirlineCN51FilterVO airlineCN51FilterVO = (AirlineCN51FilterVO) reportSpec
				.getFilterValues().iterator().next();

		Collection<AirlineInvoiceReportVO> airlineInvoiceReportVOs = AirlineCN51Summary
				.findInvoiceDetailsForReport(airlineCN51FilterVO);
		if (airlineInvoiceReportVOs == null
				|| airlineInvoiceReportVOs.size() <= 0) {
			MailTrackingMRABusinessException mailTrackingMRABusinessException

			= new MailTrackingMRABusinessException();
			ErrorVO reporterror = new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA);

			mailTrackingMRABusinessException.addError(reporterror);
			throw mailTrackingMRABusinessException;
		}
		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "sector" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(airlineInvoiceReportVOs.iterator().next());

		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "INVNUM", "SECTOR",
				"BLGPRDFRM", "BLGPRDTOO", "TOTAMTBLGCUR", "BLGCURCOD" });
		reportMetaData.setFieldNames(new String[] { "invoiceNumber", "sector",
				"fromBillingPeriod", "toBillingPeriod",
				"totalAmountinContractCurrency", "contractCurrencyCode" });
		for (AirlineInvoiceReportVO vo : airlineInvoiceReportVOs) {
			log.log(Log.INFO, " <<<printing sector>>>>>");
			log.log(Log.INFO, " sector>>", vo.getSector());
		}

		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(airlineInvoiceReportVOs);
		return ReportAgent.generateReport(reportSpec);
	}

	/**
	 * @author A-2521
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> generateInvoiceByClrPrd(ReportSpec reportSpec)
			throws SystemException, MailTrackingMRABusinessException {

		log.log(Log.INFO, " inside generateInvoiceReport");
		AirlineCN51FilterVO airlineCN51FilterVO = (AirlineCN51FilterVO) reportSpec
				.getFilterValues().iterator().next();

		Collection<AirlineCN51SummaryVO> airlineCN51SummaryVOs = AirlineCN51Summary
				.generateInvoiceReports(airlineCN51FilterVO);

		if (airlineCN51SummaryVOs == null || airlineCN51SummaryVOs.size() <= 0) {

			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();

			mailTrackingMRABusinessException.addError(new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA));
			throw mailTrackingMRABusinessException;
		}else{
			for(AirlineCN51SummaryVO airlineCN51SummaryVO :airlineCN51SummaryVOs){
			if(airlineCN51SummaryVO.getInterlinebillingtype().equals("O")){
				airlineCN51SummaryVO.setInterlinebillingtype("Outward");
			}else if(airlineCN51SummaryVO.getInterlinebillingtype().equals("I")){
				airlineCN51SummaryVO.setInterlinebillingtype("Inward");
			}
			}
		}

		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "iataClearancePeriod" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(airlineCN51FilterVO);

		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "ARLCOD", "ARLIDR",
				"ARLNAM", "INTBLGTYP", "INVNUM", "TOTAMTBLGCUR", "CLRPRD" });
		reportMetaData.setFieldNames(new String[] { "airlinecode",
				"strAirlineIdr", "airlineName", "interlinebillingtype",
				"invoicenumber", "totalAmount", "clearanceperiod" });

		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(airlineCN51SummaryVOs);
		return ReportAgent.generateReport(reportSpec);

	}

	/**
	 * @author A-2521
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> generateInvoiceByAirline(ReportSpec reportSpec)
			throws SystemException, MailTrackingMRABusinessException {

		log.log(Log.INFO, " inside generateInvoiceReport");
		AirlineCN51FilterVO airlineCN51FilterVO = (AirlineCN51FilterVO) reportSpec
				.getFilterValues().iterator().next();

		Collection<AirlineCN51SummaryVO> airlineCN51SummaryVOs = AirlineCN51Summary
				.generateInvoiceReports(airlineCN51FilterVO);

		if (airlineCN51SummaryVOs == null || airlineCN51SummaryVOs.size() <= 0) {

			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();

			mailTrackingMRABusinessException.addError(new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA));
			throw mailTrackingMRABusinessException;
		}

		AirlineCN51SummaryVO airlineCN51SummaryVO = airlineCN51SummaryVOs
				.iterator().next();
		String temp = airlineCN51SummaryVO.getAirlineName() == null ? ""
				: airlineCN51SummaryVO.getAirlineName();
		if(airlineCN51SummaryVO.getInterlinebillingtype().equals("O")){
			airlineCN51SummaryVO.setInterlinebillingtype("Outward");
		}else if(airlineCN51SummaryVO.getInterlinebillingtype().equals("I")){
			airlineCN51SummaryVO.setInterlinebillingtype("Inward");
		}
		airlineCN51FilterVO.setAirlineName(temp);
		airlineCN51FilterVO.setStrAirlineIdentifier(String
				.valueOf(airlineCN51FilterVO.getAirlineIdentifier()));

		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "airlineCode",
				"strAirlineIdentifier", "airlineName" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(airlineCN51FilterVO);

		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "INVNUM", "INTBLGTYP",
				"CLRPRD", "TOTAMTBLGCUR" });

		reportMetaData.setFieldNames(new String[] { "invoicenumber",
				"interlinebillingtype", "clearanceperiod", "totalAmount" });

		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(airlineCN51SummaryVOs);
		log.log(Log.INFO, " airlineCN51SummaryVOs", airlineCN51SummaryVOs);
		return ReportAgent.generateReport(reportSpec);

	}

	/**
	 *
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public Page<AirlineCN51SummaryVO> findCN51s(
			AirlineCN51FilterVO filterVO) throws SystemException {
		log.entering("AirlineBillingController", "findCN51s");
		return AirlineCN51Summary.findCN51s(filterVO);
	}

	/**
	 * @author a-2122
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findOutwardRejectionMemo(ReportSpec reportSpec)
			throws SystemException, RemoteException,
			MailTrackingMRABusinessException {

		log.log(Log.INFO, " inside Outward Rejection Memo Report");
		MemoFilterVO memoFilterVO = (MemoFilterVO) reportSpec.getFilterValues()
				.get(0);

		Collection<MemoInInvoiceVO> memoInInvoiceVOs = null;
		memoInInvoiceVOs = MRAMemoInInvoice
				.findOutwardRejectionMemo(memoFilterVO);

		if (memoInInvoiceVOs == null || memoInInvoiceVOs.size() <= 0) {
			ErrorVO errorVo = new ErrorVO(
					MailTrackingMRABusinessException.MAILTACKING_MRA_AIRLINEBILLING_NOREPORTDATA);
			errorVo.setErrorDisplayType(ErrorDisplayType.ERROR);
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			mailTrackingMRABusinessException.addError(errorVo);
			throw mailTrackingMRABusinessException;
		}

		/*if(memoInInvoiceVOs != null && memoInInvoiceVOs.size() >0){

		 MemoInInvoiceVO memoInInvoiceVO = new MemoInInvoiceVO();

		 memoInInvoiceVO =((ArrayList<MemoInInvoiceVO>)memoInInvoiceVOs).get(0);
		 log.log(Log.FINE,"MemoININvoiceVO"+memoInInvoiceVO);

		 if(memoInInvoiceVO.getAirlineName()!= null){
		 memoFilterVO.setAirlineName(memoInInvoiceVO.getAirlineName());
		 }
		 log.log(Log.FINE,"MemoFilterVO"+memoFilterVO);

		 }*/
		// if(memoInInvoiceVOs != null && memoInInvoiceVOs.size() >0){
		for (MemoInInvoiceVO memoInInvoiceVO : memoInInvoiceVOs) {
			log.log(Log.FINE, "MemoInInvoiceVO", memoInInvoiceVO);
			if (memoInInvoiceVO.getAirlineName() != null) {
				memoFilterVO.setAirlineName(memoInInvoiceVO.getAirlineName());
			}
			break;
		}
		log.log(Log.FINE, "MemoFilterVO", memoFilterVO);
		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "airlineCodeFilter",
				"airlineNumber", "airlineName", "clearancePeriod" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(memoFilterVO);

		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "MEMCOD", "INBINVNUM",
				"INVNUM", "RPDAMT", "PVNAMT", "DIFAMT", "CRTCURCOD", "MEMDAT",
				"RMK" });
		reportMetaData.setFieldNames(new String[] { "memoCode",
				"invoiceNumber", "outwardInvNumber", "reportedAmount",
				"provisionalAmount", "differenceAmount", "contractCurrCode",
				"memoDate", "remarks" });

		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(memoInInvoiceVOs);
		return ReportAgent.generateReport(reportSpec);
	}

	/**
	 * @author A-2391
	 * @param filterVO
	 * @return RejectionMemoVO
	 * @throws SystemException
	 */
	public RejectionMemoVO findRejectionMemo(RejectionMemoFilterVO filterVO)
			throws SystemException {
		log.entering("AirlineBillingController", "findCN51s");

		return RejectionMemo.findRejectionMemo(filterVO);

	}

	/**
	 * @author A-2391
	 * @param rejectionVO
	 * @return
	 * @throws SystemException
	 */
	public void updateRejectionMemo(RejectionMemoVO rejectionVO)
			throws SystemException {
		log.entering("AirlineBillingController", "updateRejectionMemo");

		try {
			RejectionMemo rejectionMemo = null;
			rejectionMemo = RejectionMemo.find(rejectionVO.getCompanycode(),
					rejectionVO.getAirlineIdentifier(), rejectionVO
							.getMemoCode());

			rejectionMemo.update(rejectionVO);
			/*for auditing*/
			log.log(Log.INFO, "before auditing");
/*			AirlineBillingAuditVO airlineBillingAuditVO = new AirlineBillingAuditVO(
					AirlineBillingAuditVO.AUDIT_MODULENAME,
					AirlineBillingAuditVO.AUDIT_SUBMODULENAME,
					AirlineBillingAuditVO.AUDIT_ENTITY);
			airlineBillingAuditVO = (AirlineBillingAuditVO) AuditUtils
					.populateAuditDetails(airlineBillingAuditVO, rejectionMemo,
							false);*/
			RejectionMemoVO rejectionMemoVO = populateRejectionMemoVO(rejectionMemo);
			AirlineBillingController airlineBillingController = (AirlineBillingController)SpringAdapter.getInstance().getBean("airlineBillingcontroller");
			airlineBillingController.performAirlineBillingAudit(rejectionMemoVO,
					AirlineBillingAuditVO.AUDIT_REJECTIONMEMO_UPDATED);
/*			((AirlineBillingController)SpringAdapter.getCurrentProxy()).performAirlineBillingAudit(rejectionMemo,
					AirlineBillingAuditVO.AUDIT_REJECTIONMEMO_UPDATED);     
*/
			String dsnReqdValue = null;
			String mraImportLevel = null;
			ArrayList<String> systemParameters = new ArrayList<String>();
			systemParameters.add (DSN_AUDIT_REQD);  
			systemParameters.add(MRAIMPORTLEVL);
			Map<String, String> systemParameterMap = null;
			try {
				systemParameterMap = new SharedDefaultsProxy()
						.findSystemParameterByCodes(systemParameters);
			} catch (ProxyException e) {
				e.getMessage();
			}
			log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
			if (systemParameterMap != null) {

				dsnReqdValue = systemParameterMap.get (DSN_AUDIT_REQD); 
				mraImportLevel = systemParameterMap.get(MRAIMPORTLEVL);
				if(RejectionMemoVO.FLAG_YES.equals(mraImportLevel)){
				if (RejectionMemoVO.FLAG_YES.equals (dsnReqdValue)){
					//((AirlineBillingController)SpringAdapter.getCurrentProxy()).auditDSNForRejectionMemo(rejectionVO);  
					airlineBillingController.auditDSNForRejectionMemo(rejectionVO);
					//AuditHelper.auditDSNForRejectionMemo (rejectionVO);
				}
				}
			}
			log.log(Log.INFO, "after auditing");

		} catch (FinderException finderException) {
			log.log(Log.SEVERE,
					"FINDER EXCEPTION OCCURED IN FINDING rejectionMemo Entity");
		}

	}

	/**
	 * @author A-2391
	 * @param airlineBillingAuditVO
	 * @param rejectionMemo
	 * @param actionCode
	 * @throws SystemException
	 */
	/*@Deprecated
	private void performAirlineBillingAudit(
			AirlineBillingAuditVO airlineBillingAuditVO,
			RejectionMemo rejectionMemo, String actionCode)
			throws SystemException {
		log.entering("DSN", "performAirlineBillingAudit");
		/*		RejectionMemoPK rejectionMemoPK = rejectionMemo.getRejectionMemoPK();
		airlineBillingAuditVO.setCompanyCode(rejectionMemoPK.getCompanyCode());
		airlineBillingAuditVO.setAirlineIdentifier(rejectionMemoPK
				.getAirlineIdentifier());
		airlineBillingAuditVO.setAirlineCode(rejectionMemo.getAirlineCode());
		airlineBillingAuditVO.setClearancePeriod(rejectionMemo
				.getInwardClearancePeriod());
		airlineBillingAuditVO.setMemoCode(rejectionMemoPK.getMemoCode());
		airlineBillingAuditVO.setActionCode(actionCode);*/
/*		StringBuffer additionalInfo = new StringBuffer();
		log.log(Log.INFO, "from pk", rejectionMemoPK.getAirlineIdentifier());
		log.log(Log.INFO, "airlineBillingAuditVO", airlineBillingAuditVO.getEntityName());
		log.log(Log.INFO, "audit entity class name", airlineBillingAuditVO.getAuditEntityclassName());
		log.log(Log.INFO, "actionCOde", airlineBillingAuditVO.getActionCode());
		additionalInfo.append("Airline ")
				.append(rejectionMemo.getAirlineCode()).append(
						" with invoice number ").append(
						rejectionMemo.getInwardInvoiceNumber()).append(
						" and memo code ")
				.append(rejectionMemoPK.getMemoCode()).append(" ");
		if (actionCode
				.equals(AirlineBillingAuditVO.AUDIT_REJECTIONMEMO_CAPTURED)) {
			additionalInfo.append(AirlineBillingAuditVO.AUDIT_CREATE_ACTION);
		} else if (actionCode
				.equals(AirlineBillingAuditVO.AUDIT_REJECTIONMEMO_DELETED)) {
			additionalInfo.append(AirlineBillingAuditVO.AUDIT_DELETE_ACTION);
		} else if (actionCode
				.equals(AirlineBillingAuditVO.AUDIT_REJECTIONMEMO_UPDATED)) {
			additionalInfo.append(AirlineBillingAuditVO.AUDIT_UPDATE_ACTION);
		}*/
/*		airlineBillingAuditVO.setAdditionalInformation(additionalInfo
				.toString());
		log.log(Log.INFO, "inftn ", airlineBillingAuditVO.getAdditionalInformation());
		AuditUtils.performAudit(airlineBillingAuditVO);
		log.exiting("DSN", "performAirlineBillingAudit");
	}*/

	/**
	 * 	Method		:	AirlineBillingController.performAirlineBillingAudit
	 *	Added by 	:	A-4809 on Dec 29, 2014
	 * 	Used for 	:   Audit
	 *	Parameters	:	@param rejectionMemo
	 *	Parameters	:	@param actionCode
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	@Advice(name = "mailtracking.mra.performAirlineBillingAudit" , phase=Phase.PRE_INVOKE)
	public void performAirlineBillingAudit(RejectionMemoVO rejectionMemo, String actionCode)
			throws SystemException {
		log.entering(CLASS_NAME, "performAirlineBillingAudit");
		log.log(Log.FINE, "Common Method for Audit Rejection Memo :-",rejectionMemo);
		log.log(Log.FINE, "Common Method for Audit Action Code :-",actionCode);
		log.exiting(CLASS_NAME, "performAirlineBillingAudit");
	} 
	/**
	 * 	Method		:	AirlineBillingController.auditDSNForRejectionMemo
	 *	Added by 	:	A-4809 on Jan 2, 2015
	 * 	Used for 	:	Audit at DSN level
	 *	Parameters	:	@param rejectionMemoVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	@Advice(name = "mailtracking.mra.auditDSNForRejectionMemo" , phase=Phase.POST_INVOKE)
	public void auditDSNForRejectionMemo(RejectionMemoVO rejectionMemoVO)
			throws SystemException{
		log.entering(CLASS_NAME, "auditDSNForRejectionMemo");
		log.log(Log.FINE, "auditDSNForRejectionMemo RejectionMemoVO:-",rejectionMemoVO);
		log.exiting(CLASS_NAME, "auditDSNForRejectionMemo");
	}
	/**
	 * @author a-2391 This method is used to list the Audit details
	 * @param mailAuditFilterVO
	 * @return Collection<AuditDetailsVO>
	 * @throws SystemException
	 */
	public Collection<AuditDetailsVO> findArlAuditDetails(
			MRAArlAuditFilterVO mailAuditFilterVO) throws SystemException {
		log.entering("audit", "findArlAuditDetails");
		return MRAAirlineBillingAudit.findArlAuditDetails(mailAuditFilterVO);
	}

	/**
	 *
	 *
	 *
	 * @author A-3434
	 *
	 * @param formOneFilterVo
	 *
	 * @return Page<FormOneVO>
	 *
	 * @throws SystemException
	 *
	 *
	 *
	 */

	public Page<FormOneVO> findFormOnes(FormOneFilterVO formOneFilterVo)

	throws SystemException {

		Page<FormOneVO> page = MRAAirlineForBilling.findFormOnes(formOneFilterVo);



		log.log(Log.FINE, "findFormOnes");

		return page;

	}
	/**
	 *
	 *
	 *
	 * @author A-3434
	 *
	 * @param interlineFilterVo
	 *
	 * @return Collection<AirlineForBillingVO>
	 *
	 * @throws SystemException
	 *
	 *
	 *
	 */

	public Collection<AirlineForBillingVO> findFormTwoDetails(

	InterlineFilterVO interlineFilterVo) throws SystemException {

		log.entering("AirlineBillingController", "findFormTwoDetails");

		return MRAAirlineForBilling.findAirlineDetails(interlineFilterVo);

	}
	/**
	 * @param formOneVO
	 * @return
	 * @throws SystemException
	 */
	public FormOneVO listFormOneDetails(FormOneVO formOneVO)throws SystemException {
		log.entering("controller", "listFormOneDetails");
		return MRAFormOne.listFormOneDetails(formOneVO);
	}
	/**
	 *
	 * @author a-3456
	 *
	 * @param interlineFilterVo
	 *
	 * @return
	 *
	 * @throws SystemException
	 *@throws RemoteException
	 */

	public FormOneVO findFormOneDetails(InterlineFilterVO interlineFilterVo)
	throws RemoteException, SystemException{
		log.entering("Airline Billing Controller", "findFormOneDetails");
		return MRAAirlineForBilling.findFormOneDetails(interlineFilterVo);
	}
	/**
	 * @param formOneVO
	 * @return
	 * @throws SystemException
	 */
	public void saveFormOneDetails(FormOneVO formOneVO)throws SystemException {
		log.entering("controller", "saveFormOneDetails");
		boolean flag;
		MRAAirlineForBilling mRAAirlineForBilling=null;
		AirlineCN51Summary c51Sum= null;
		MRAFormOne formOne=null;
		MRAFormOneInv formOneInv=null;
		ArrayList<InvoiceInFormOneVO> formOneInvVOs=new ArrayList<InvoiceInFormOneVO>();
		formOneInvVOs=(ArrayList<InvoiceInFormOneVO>)formOneVO.getInvoiceInFormOneVOs();
		int siz=formOneInvVOs.size();
		int same=0;
		if(formOneVO.getOperationFlag()!=null  && formOneVO.getOperationFlag().equals(FormOneVO.OPERATION_FLAG_INSERT)){
			 log.log(Log.INFO, "inside insert  ", formOneVO.getOperationFlag());
					new MRAFormOne(formOneVO);
					try{
				    	mRAAirlineForBilling=MRAAirlineForBilling.find(formOneVO.getCompanyCode(), formOneVO.getAirlineIdr(), formOneVO.getClearancePeriod());
				    	if(mRAAirlineForBilling!=null) {
							mRAAirlineForBilling.setCapturedFormOneFlag("Y");
						}
				    	mRAAirlineForBilling.setLastUpdateTime(formOneVO.getLastUpdateTimeBlg());
				    	mRAAirlineForBilling.setLastUpdateUser(formOneVO.getLastUpdateUser());
					}
				    	catch (FinderException finderException) {
				    		AirlineForBillingVO airlineForBillingVO=new AirlineForBillingVO();
				    		airlineForBillingVO.setCompanyCode(formOneVO.getCompanyCode());
				    		airlineForBillingVO.setAirlineIdentifier(formOneVO.getAirlineIdr());
				    		airlineForBillingVO.setClearancePeriod(formOneVO.getClearancePeriod());
				    		airlineForBillingVO.setLastUpdateTime(formOneVO.getLastUpdateTime());
				    		airlineForBillingVO.setLastUpdateUser(formOneVO.getLastUpdateUser());
				    		airlineForBillingVO.setAirlineCode(formOneVO.getAirlineCode());
				    		airlineForBillingVO.setAirlineNumber(String.valueOf(formOneVO.getAirlineIdr()));
				    		airlineForBillingVO.setCapturedFormOneFlag(true);
				    		new MRAAirlineForBilling(airlineForBillingVO);
							log.log(Log.SEVERE,"mRAAirlineForBilling finder1111111111!!!");

						}
		}
		try{
			formOne=null;
			formOne=	MRAFormOne.find(formOneVO.getCompanyCode(),formOneVO.getAirlineIdr(),formOneVO.getClearancePeriod(),
					formOneVO.getInterlineBillingType(),formOneVO.getClassType());
		if(formOneVO.getOperationFlag()!=null  && formOneVO.getOperationFlag().equals(FormOneVO.OPERATION_FLAG_UPDATE)){
			 log.log(Log.INFO, "inside update  ", formOneVO.getOperationFlag());
			try{
			    	mRAAirlineForBilling=MRAAirlineForBilling.find(formOneVO.getCompanyCode(), formOneVO.getAirlineIdr(), formOneVO.getClearancePeriod());
			    	if(mRAAirlineForBilling!=null) {
						mRAAirlineForBilling.setCapturedFormOneFlag("Y");
					}
			    	mRAAirlineForBilling.setLastUpdateTime(formOneVO.getLastUpdateTimeBlg());
			    	mRAAirlineForBilling.setLastUpdateUser(formOneVO.getLastUpdateUser());
				}
			    	catch (FinderException finderException) {
			    		AirlineForBillingVO airlineForBillingVO=new AirlineForBillingVO();
			    		airlineForBillingVO.setCompanyCode(formOneVO.getCompanyCode());
			    		airlineForBillingVO.setAirlineIdentifier(formOneVO.getAirlineIdr());
			    		airlineForBillingVO.setClearancePeriod(formOneVO.getClearancePeriod());
			    		airlineForBillingVO.setLastUpdateTime(formOneVO.getLastUpdateTime());
			    		airlineForBillingVO.setLastUpdateUser(formOneVO.getLastUpdateUser());
			    		airlineForBillingVO.setAirlineCode(formOneVO.getAirlineCode());
			    		airlineForBillingVO.setAirlineNumber(String.valueOf(formOneVO.getAirlineIdr()));
			    		airlineForBillingVO.setCapturedFormOneFlag(true);
			    		airlineForBillingVO.setCapturedFormOneFlag(true);
			    		new MRAAirlineForBilling(airlineForBillingVO);
						log.log(Log.SEVERE,"mRAAirlineForBilling finder1111111111!!!");

					}


				//	formOne=null;
				//	formOne=	MRAFormOne.find(formOneVO.getCompanyCode(),formOneVO.getAirlineIdr(),formOneVO.getClearancePeriod(),
				//			formOneVO.getInterlineBillingType(),formOneVO.getClassType());
					formOne.update(formOneVO);

		}
		if(formOneVO.getOperationFlag()!=null  && formOneVO.getOperationFlag().equals(FormOneVO.OPERATION_FLAG_DELETE)){
			 log.log(Log.INFO, "inside delete   ", formOneVO.getOperationFlag());
			for(int i=0;i<siz;i++){
				c51Sum=AirlineCN51Summary.find(formOneInvVOs.get(i).getCompanyCode(),formOneInvVOs.get(i).getAirlineIdentifier(),
						formOneInvVOs.get(i).getIntBlgTyp(),formOneInvVOs.get(i).getInvoiceNumber(),formOneInvVOs.get(i).getClearancePeriod());
				if(c51Sum.getInvSrc()!=null){
					if(c51Sum.getInvSrc().equals(FormOneVO.OPERATION_FLAG_DELETE)) {
						c51Sum.remove();
					}
				}
			}
			//formOne=	MRAFormOne.find(formOneVO.getCompanyCode(),formOneVO.getAirlineIdr(),formOneVO.getClearancePeriod(),
			//		formOneVO.getInterlineBillingType(),formOneVO.getClassType());
			formOne.remove();
			try{
		    	mRAAirlineForBilling=MRAAirlineForBilling.find(formOneVO.getCompanyCode(), formOneVO.getAirlineIdr(), formOneVO.getClearancePeriod());
		    	mRAAirlineForBilling.setCapturedFormOneFlag("N");
		    	mRAAirlineForBilling.setLastUpdateTime(formOneVO.getLastUpdateTimeBlg());
		    	mRAAirlineForBilling.setLastUpdateUser(formOneVO.getLastUpdateUser());
		    	}
		    	catch (FinderException finderException) {
					log.log(Log.SEVERE,"mRAAirlineForBilling finder22222222222222222!!!");

				}
			return;
		}
		}
		catch (FinderException finderException) {
			log.log(Log.SEVERE,
					"FINDER EXCEPTION OCCURED IN update of formOne");

		}
		catch (RemoveException removeException) {
			log.log(Log.SEVERE,
					"RemoveException OCCURED IN delete of formOne");

		}

		try{

			/**
			 * @author A-3447 for form 3 and form 1 balancing starts
			 */
			try{
			mRAAirlineForBilling=MRAAirlineForBilling.find(formOneVO.getCompanyCode(),formOneVO.getAirlineIdr(), formOneVO.getClearancePeriod());


			if(mRAAirlineForBilling!=null){
				log.log(Log.FINE,"mRAAirlineForBilling NOT NULL");
				if(mRAAirlineForBilling.getInwardTotalAmount()==formOneVO.getBillingTotalAmt().getAmount()){
					mRAAirlineForBilling.setFormThreeStatus(BALANCED);
					mRAAirlineForBilling.setLastUpdateTime(formOneVO.getLastUpdateTimeBlg());
			    	mRAAirlineForBilling.setLastUpdateUser(formOneVO.getLastUpdateUser());
					log.log(Log.FINE,"Form 3--- balancing");
				}
				else{

					log.log(Log.FINE,"Form 3 un ---balancing");
				mRAAirlineForBilling.setFormThreeStatus(UNBALANCED);
				mRAAirlineForBilling.setLastUpdateTime(formOneVO.getLastUpdateTimeBlg());
		    	mRAAirlineForBilling.setLastUpdateUser(formOneVO.getLastUpdateUser());

				}
			}
			else{

				log.log(Log.FINE,"Finder*****");
			}
			}
			catch (FinderException finderException) {



				log.log(Log.FINE,"Finder***for arlblg**");

				//formone status=new

			}
		for(int i=0;i<siz;i++){
			c51Sum= null;
			flag=false;
			try{
			c51Sum=AirlineCN51Summary.find(formOneInvVOs.get(i).getCompanyCode(),formOneInvVOs.get(i).getAirlineIdentifier(),
					formOneInvVOs.get(i).getIntBlgTyp(),formOneInvVOs.get(i).getInvoiceNumber(),formOneInvVOs.get(i).getClearancePeriod());
			flag=true;
			}
			catch (FinderException finderException) {
				log.log(Log.SEVERE,
						"FINDER EXCEPTION OCCURED IN FINDING c51Sum Entity");
				//invsta & frmonestatus new.
				AirlineCN51SummaryVO cn51SummaryVO=new AirlineCN51SummaryVO();
				cn51SummaryVO.setCompanycode(formOneInvVOs.get(i).getCompanyCode());
				cn51SummaryVO.setAirlineidr(formOneInvVOs.get(i).getAirlineIdentifier());
				cn51SummaryVO.setInterlinebillingtype(formOneInvVOs.get(i).getIntBlgTyp());
				cn51SummaryVO.setInvoicenumber(formOneInvVOs.get(i).getInvoiceNumber());
				cn51SummaryVO.setClearanceperiod(formOneInvVOs.get(i).getClearancePeriod());
				cn51SummaryVO.setInvSrc(DELETE);
				cn51SummaryVO.setInvStatus(NEW);
				cn51SummaryVO.setInvFormstatus(NEW);
				c51Sum=new AirlineCN51Summary(cn51SummaryVO);
				log.log(Log.INFO,
				"c51Sum OBTAINED");

			}
			if(flag){
							c51Sum=AirlineCN51Summary.find(formOneInvVOs.get(i).getCompanyCode(),formOneInvVOs.get(i).getAirlineIdentifier(),
									formOneInvVOs.get(i).getIntBlgTyp(),formOneInvVOs.get(i).getInvoiceNumber(),formOneInvVOs.get(i).getClearancePeriod());

							if(!("D").equals(formOneInvVOs.get(i).getOperationFlag()) ){
								log.log(Log.INFO,
								"c51Sum!=null ");

								 formOneInv=MRAFormOneInv.find(formOneInvVOs.get(i).getCompanyCode(),formOneInvVOs.get(i).getAirlineIdentifier(),
										 formOneVO.getClearancePeriod(),formOneInvVOs.get(i).getIntBlgTyp(),formOneInvVOs.get(i).getClassType(),formOneInvVOs.get(i).getInvoiceNumber());
								 log.log(Log.INFO,
										"c51Sum.c51Sum.getAmountInusd() ",
										c51Sum.getAmountInusd());
								log.log(Log.INFO, "formOneInv.getTotBlgAmt() ",
										formOneInv.getTotBlgAmt());
								/**
								 * @author A-3447 for form 3 balancing ends
								 */
								if(c51Sum.getAmountInusd()==formOneInv.getTotBlgAmt()){

									log.log(Log.INFO,
									"(blgamt.equals");
									if(formOneInv!=null){
										//formOneInv.setFormOneStatus(BALANCED);
										c51Sum.setInvFormstatus(BALANCED);
										log.log(Log.INFO,
										"(blgamt.equals");
										same++;
									}

								}
								else{
									log.log(Log.INFO,
									"(blgamt.not equals");
									//formOneInv.setFormOneStatus(UNBALANCED);
									c51Sum.setInvFormstatus(UNBALANCED);
									//formOneInv.setLastUpdatedTime(formOneVO.getLastUpdateTime());

								}
							} else {
								same++;
							}
						}
		}

	/*	if(siz==same){
			log.log(Log.INFO,"**invoice status balanced**");
			formOneInv.setInvStatus(BALANCED);
			formOneInv.setLastUpdatedTime(formOneVO.getLastUpdateTime());

		}else{
			log.log(Log.INFO,"**invoice status not  balanced**");
			formOneInv.setInvStatus(UNBALANCED);
			formOneInv.setLastUpdatedTime(formOneVO.getLastUpdateTime());
		}
		*/

		formOne=null;
		formOne=	MRAFormOne.find(formOneVO.getCompanyCode(),formOneVO.getAirlineIdr(),formOneVO.getClearancePeriod(),
							formOneVO.getInterlineBillingType(),formOneVO.getClassType());
		log.log(Log.INFO, "siz ", siz);
		log.log(Log.INFO, "same ", same);
		if(siz==same){

			formOne.setFormOneStatus(BALANCED);

			formOne.setLastUpdatedTime(formOneVO.getLastUpdateTime());
		}
		else{
			log.log(Log.INFO,"*invoice status unbalanced**");
			formOne.setFormOneStatus(UNBALANCED);
			formOne.setLastUpdatedTime(formOneVO.getLastUpdateTime());
		}
		}
		catch (FinderException finderException) {
			log.log(Log.SEVERE,
					"FINDER EXCEPTION OCCURED IN FINDING MRAFormOne Entity");

		}

		catch (CreateException createException) {
			log.log(Log.SEVERE,
					"createException OCCURED IN FINDING c51Sum Entity");
		}

	}
	/**
	 *
	 * @author a-3108
	 *
	 * @param interlineFilterVo
	 *
	 * @return Collection<AirlineForBillingVO>
	 *
	 * @throws SystemException
	 *
	 * */

	public Collection<AirlineForBillingVO> findFormThreeDetails(

	InterlineFilterVO interlineFilterVo) throws SystemException {

		log.entering("AirlineBillingController", "findFormThreeDetails");

		return MRAAirlineForBilling.findFormThreeDetails(interlineFilterVo);

	}
	/** @author a-3108

	 * Method to save Form Three Details
	 *
	 * @param airlineForBillingVOs
	 *
	 * @return void
	 *
	 * @throws SystemException


	 */

	public void saveFormThreeDetails(

	Collection<AirlineForBillingVO> airlineForBillingVOs)

	throws SystemException {

		String lstupdusr = null;

		LocalDate lstupddat = null;

		String companyCode = null;

		log.entering("AirlineBillingController", "saveFormThreeDetails");

		if (airlineForBillingVOs != null && airlineForBillingVOs.size() > 0) {

			int serialNumber = MRAFormThreeTemp.findMaxSerialNumber();

			log.log(Log.FINE, "serialNUmber--->>>", serialNumber);
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext()

			.getLogonAttributesVO();

			lstupdusr = logonAttributes.getUserId();

			lstupddat = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);

			for (AirlineForBillingVO airlineForBillingVO : airlineForBillingVOs) {

				companyCode = airlineForBillingVO.getCompanyCode();

				new MRAFormThreeTemp(airlineForBillingVO, serialNumber);

			}

			log.log(Log.FINE, "companyCode--->>>", companyCode);
			MRAAirlineForBilling.saveFormThree(lstupdusr, lstupddat, serialNumber,

			companyCode);

		}

		log.exiting("AirlineBillingController", "saveFormThreeDetails");

	}


/**
 * @author a-3447
 * @param airlineCN51FilterVO
 * @return
 * @throws RemoteException
 * @throws SystemException
 */

public AirlineCN51SummaryVO findCaptureInvoiceDetails(
	AirlineCN51FilterVO airlineCN51FilterVO) throws RemoteException,
	SystemException {
log.entering("Airline Billing Controller", "findCaptureInvoiceDetails");
return AirlineCN51Summary
		.findCaptureInvoiceDetails(airlineCN51FilterVO);
}




/**
 *  @author a-3447
 * @param airlineCN51SummaryVO
 * @throws SystemException
 * @throws FinderException
 * @throws CreateException
 */

public void updateBillingDetailCommand(
		AirlineCN51SummaryVO airlineCN51SummaryVO) throws SystemException,
		 CreateException {
	log.entering("-inside Controller--->> ", "updateBillingDetailCommand");
	//MRAAirlineForBilling mRAAirlineForBilling;
	String companyCode = airlineCN51SummaryVO.getCompanycode();
	int airlineIdentifier = airlineCN51SummaryVO.getAirlineidr();
	String interlineBillingType = airlineCN51SummaryVO.getInterlinebillingtype();
	String invoiceNumber = airlineCN51SummaryVO.getInvoicenumber();
	String clearancePeriod = airlineCN51SummaryVO.getClearanceperiod();
	log.log(Log.INFO, "updateBillingDetailCommand", airlineCN51SummaryVO);
	if(("I").equals(airlineCN51SummaryVO.getOperationFlag())){

			log.log(log.FINE, "Creating new --- ");
			airlineCN51SummaryVO.setInvFormstatus(NEW);
			airlineCN51SummaryVO.setInvStatus(NEW);
			log.log(Log.INFO, "InvFormstatus --- ", airlineCN51SummaryVO.getInvStatus());
			try{
				MRAAirlineForBilling mRAAirlineForBilling=MRAAirlineForBilling.find(companyCode,airlineIdentifier,clearancePeriod);
			try{
				AirlineCN51Summary airlineCN51Smy = AirlineCN51Summary.find(companyCode, airlineIdentifier, interlineBillingType,invoiceNumber, clearancePeriod);
				//MRAAirlineForBilling mRAAirlineForBilling=MRAAirlineForBilling.find(companyCode,airlineIdentifier,clearancePeriod);
			if(airlineCN51Smy!=null){
				log.log(Log.INFO, "inside  entity Found-- ",
						airlineCN51SummaryVO);
				if(airlineCN51Smy.getInvSrc()!=null){
			if(airlineCN51Smy.getInvSrc().equals(DUMMY)){
				airlineCN51Smy.remove();
			new AirlineCN51Summary(airlineCN51SummaryVO);
			mRAAirlineForBilling.setCapturedInvoiceFlag(YES);
			mRAAirlineForBilling.setLastUpdateTime(airlineCN51SummaryVO.getLastUpdatedTime());
			mRAAirlineForBilling.setLastUpdateUser(airlineCN51SummaryVO.getLastUpdatedUser());
			 log.log(log.FINE, "setting flag in master table ----as Y 1");
			balanceInvoice(airlineCN51SummaryVO);
			AirlineCN51Summary airlineCN51Smys = AirlineCN51Summary.find(companyCode, airlineIdentifier, interlineBillingType,
					invoiceNumber, clearancePeriod);
			if(airlineCN51Smys!=null){
				airlineCN51Smys.setInvSrc((ACTUAL));
			}

			}
				}
			else{


			airlineCN51Smy.remove();
			new AirlineCN51Summary(airlineCN51SummaryVO);
			}
			}
				}	catch(FinderException e){
						log.log(Log.INFO, "inside  entity not  found--- ",
								airlineCN51SummaryVO);
						new AirlineCN51Summary(airlineCN51SummaryVO);
						balanceInvoice(airlineCN51SummaryVO);
						mRAAirlineForBilling.setCapturedInvoiceFlag(YES);
						mRAAirlineForBilling.setLastUpdateTime(airlineCN51SummaryVO.getLastUpdatedTime());
						mRAAirlineForBilling.setLastUpdateUser(airlineCN51SummaryVO.getLastUpdatedUser());
						 log.log(log.FINE, "setting flag in master table ----as Y 2");

			}




		}
		catch(FinderException ex){
			log.log(Log.INFO, "finder --- ", ex);
			try {
				AirlineCN51Summary airlineCN51Smy = AirlineCN51Summary.find(companyCode, airlineIdentifier, interlineBillingType,invoiceNumber, clearancePeriod);
				if(airlineCN51Smy!=null){
					airlineCN51SummaryVO.setInvFormstatus(airlineCN51Smy.getInvFormstatus());
					airlineCN51SummaryVO.setInvStatus(airlineCN51Smy.getInvStatus());
				if((DUMMY).equals(airlineCN51Smy.getInvSrc())){
					airlineCN51Smy.remove();
					airlineCN51SummaryVO.setInvSrc(ACTUAL);
					balanceInvoice(airlineCN51SummaryVO);
				new AirlineCN51Summary(airlineCN51SummaryVO);
				}
				}
				//new AirlineCN51Summary(airlineCN51SummaryVO);
				try{
				MRAAirlineForBilling mRAAirlineForBilling;
				mRAAirlineForBilling = MRAAirlineForBilling.find(companyCode,airlineIdentifier,clearancePeriod);
				if(mRAAirlineForBilling!=null){
				mRAAirlineForBilling.setCapturedInvoiceFlag(YES);
				mRAAirlineForBilling.setLastUpdateTime(airlineCN51SummaryVO.getLastUpdatedTime());
				mRAAirlineForBilling.setLastUpdateUser(airlineCN51SummaryVO.getLastUpdatedUser());
				log.log(log.FINE, "setting flag in master table ----as Y 3");
			}
				}

				catch (FinderException e) {
					// TODO Auto-generated catch block

					log.log(log.FINE, "Exception in Finding mRAAirlineForBilling");
					e.getErrorCode();
				}
			}catch (FinderException e) {
				// TODO Auto-generated catch block
				airlineCN51SummaryVO.setInvFormstatus("N");
				new AirlineCN51Summary(airlineCN51SummaryVO);
				log.log(log.FINE, "Exception in Finding AirlineCN51Summary");
				e.getMessage();
			}


		}

		}
		try{


	if ((("U").equals(airlineCN51SummaryVO.getOperationFlag()))) {


		AirlineCN51Summary airlineCN51Summary = AirlineCN51Summary.find(companyCode, airlineIdentifier, interlineBillingType,
				invoiceNumber, clearancePeriod);

	if (airlineCN51Summary != null) {
		/*
		 * if inv staus :d--update else insert as new
		 *
		 */
		log.log(log.FINE, "Entity not null--- ");
		try{
			MRAFormOneInv mRAFormOneInv = MRAFormOneInv.find(companyCode,airlineIdentifier, clearancePeriod, interlineBillingType, "M",	invoiceNumber);
			MRAFormOne  mRAFormOne= MRAFormOne.find(companyCode, airlineIdentifier, clearancePeriod, INTERLINE, MAIL_CLASS);

			if (mRAFormOneInv != null) {
				if(mRAFormOne!=null){
				/**
				 * Balancing Check
				 */

					log.log(Log.INFO, "amounts  -->>>  ", airlineCN51SummaryVO.getAmountInusd().getAmount());
					log.log(Log.INFO, "amounts frm smy -->>>  ", mRAFormOneInv.getTotBlgAmt());
				if (airlineCN51SummaryVO.getAmountInusd().getAmount() == mRAFormOneInv
						.getTotBlgAmt()) {
					log.log(Log.INFO, "amounts  -->>>  ", airlineCN51Summary.getAmountInusd());
					log.log(Log.INFO, "amounts frm smy -->>>  ",
							airlineCN51Summary.getAmountInusd());
					airlineCN51SummaryVO.setInvFormstatus(BALANCED);
					mRAFormOne.setFormOneStatus(BALANCED);
					mRAFormOne.setLastUpdatedTime(airlineCN51SummaryVO.getLastUpdatedTime());
					mRAFormOne.setLastUpdatedUser(airlineCN51SummaryVO.getLastUpdatedUser());

				} else {
					log.log(log.FINE, "inside Unbalacing  ");
					airlineCN51SummaryVO.setInvFormstatus(UNBALANCED);
					mRAFormOne.setFormOneStatus(UNBALANCED);
					mRAFormOne.setLastUpdatedTime(airlineCN51SummaryVO.getLastUpdatedTime());
					mRAFormOne.setLastUpdatedUser(airlineCN51SummaryVO.getLastUpdatedUser());

				}
			}}else {
				log.log(log.FINE, "inside UNBAL---- ");
				airlineCN51SummaryVO.setInvFormstatus(UNBALANCED);

			}
			}
			catch(FinderException e){
					log.log(log.FINE, "FinderException---->>>  ");
					airlineCN51SummaryVO.setInvFormstatus("");

			}
				if (ACTUAL.equals(airlineCN51SummaryVO.getInvoiceSrcFlag())) {
					log.log(log.FINE, "inside Invoice Flag --ACTUAL");
					airlineCN51Summary
							.setListingCurrency(airlineCN51SummaryVO
									.getListingCurrency());
					if (airlineCN51SummaryVO.getAmountInusd() != null) {
						airlineCN51Summary
								.setAmountInusd(airlineCN51SummaryVO
										.getAmountInusd().getAmount());

					}
					if (airlineCN51SummaryVO.getNetAmount() != null) {
						airlineCN51Summary
								.setNetAmount(airlineCN51SummaryVO
										.getNetAmount().getAmount());
					}
					if (airlineCN51SummaryVO.getExchangeRate() != null) {
						airlineCN51Summary
								.setExchangeRate(airlineCN51SummaryVO
										.getExchangeRate().getAmount());
					}
					airlineCN51Summary.setInvRcvdate(airlineCN51SummaryVO
							.getInvRcvdate());
					airlineCN51Summary
							.setLastUpdatedTime(airlineCN51SummaryVO
									.getLastUpdatedTime());
					airlineCN51Summary
							.setLastUpdatedUser(airlineCN51SummaryVO
									.getLastUpdatedUser());
					airlineCN51Summary.setTotalWt(airlineCN51SummaryVO
							.getTotWt());
					airlineCN51Summary
							.setInvFormstatus(airlineCN51SummaryVO
									.getInvFormstatus());
					airlineCN51Summary.setInvStatus(airlineCN51SummaryVO
							.getInvStatus());
				}

				else {
					log.log(log.FINE, "inside Not D --");
					balanceInvoice(airlineCN51SummaryVO);
					airlineCN51Summary.update(airlineCN51SummaryVO);
				}

	}
	}

			if (("D").equals(airlineCN51SummaryVO.getOperationFlag())) {
				log.log(log.FINE, "inside delete");
				AirlineCN51Summary airlineCN51Sum = AirlineCN51Summary.find(companyCode, airlineIdentifier, interlineBillingType,
						invoiceNumber, clearancePeriod);
				if (ACTUAL.equals(airlineCN51SummaryVO.getInvoiceSrcFlag())) {
					log.log(log.FINE, "inside INVSRC D--- ");
					airlineCN51Sum.remove();
					AirlineCN51SummaryVO arlineCN51SummaryVO = new AirlineCN51SummaryVO();
					arlineCN51SummaryVO.setInvSrc(DUMMY);
					arlineCN51SummaryVO.setCompanycode(companyCode);
					arlineCN51SummaryVO.setAirlineidr(airlineIdentifier);
					arlineCN51SummaryVO
							.setInterlinebillingtype(interlineBillingType);
					arlineCN51SummaryVO.setInvoicenumber(invoiceNumber);
					arlineCN51SummaryVO.setClearanceperiod(clearancePeriod);
					arlineCN51SummaryVO.setInvFormstatus("N");
					arlineCN51SummaryVO.setInvStatus("N");
					new AirlineCN51Summary(arlineCN51SummaryVO);
					MRAAirlineForBilling mRAAirlineForBillings=MRAAirlineForBilling.find(companyCode,airlineIdentifier,clearancePeriod);
					if(mRAAirlineForBillings!=null){
						 log.log(log.FINE, "setting flag in master table ----as N");
						mRAAirlineForBillings.setCapturedInvoiceFlag("N");
						mRAAirlineForBillings.setLastUpdateTime(airlineCN51SummaryVO.getLastUpdatedTime());
						mRAAirlineForBillings.setLastUpdateUser(airlineCN51SummaryVO.getLastUpdatedUser());
						}
				}

				else {
					log.log(log.FINE, "inside not  D--- ");
					 log.log(log.FINE, "setting flag in master table ----as N");
					airlineCN51Sum.remove();
					MRAAirlineForBilling mRAAirlineForBillings=MRAAirlineForBilling.find(companyCode,airlineIdentifier,clearancePeriod);
					mRAAirlineForBillings.setCapturedInvoiceFlag("N");
					mRAAirlineForBillings.setLastUpdateTime(airlineCN51SummaryVO.getLastUpdatedTime());
					mRAAirlineForBillings.setLastUpdateUser(airlineCN51SummaryVO.getLastUpdatedUser());
				}

			}



	}
	catch(FinderException e){
		log.log(Log.FINE, "FinderException---->>>  ");
	}


}


/**
 * @author A-3447
 * @param airlineCN51SummaryVO
 * for balancing checks
 */


private void balanceInvoice(AirlineCN51SummaryVO airlineCN51SummaryVO) {
	log.log(log.FINE, "balanceInvoice --- ");
	String companyCode = airlineCN51SummaryVO.getCompanycode();
	int airlineIdentifier = airlineCN51SummaryVO.getAirlineidr();
	String interlineBillingType = airlineCN51SummaryVO.getInterlinebillingtype();
	String invoiceNumber = airlineCN51SummaryVO.getInvoicenumber();
	String clearancePeriod = airlineCN51SummaryVO.getClearanceperiod();
	try{
	MRAFormOneInv mRAFormOneInvs= MRAFormOneInv.find(companyCode,airlineIdentifier, clearancePeriod, interlineBillingType, "M",	invoiceNumber);
	MRAFormOne  mRAFormOne= MRAFormOne.find(companyCode, airlineIdentifier, clearancePeriod, INTERLINE, MAIL_CLASS);
	AirlineCN51Summary	airlineCN51Smy = AirlineCN51Summary.find(companyCode, airlineIdentifier, interlineBillingType,	invoiceNumber, clearancePeriod);
		if ((mRAFormOneInvs != null) &&mRAFormOne!=null){
	/**
	 * Balancing
	 */
		if (airlineCN51SummaryVO.getAmountInusd().getAmount() == mRAFormOneInvs
				.getTotBlgAmt()) {
			log.log(log.FINE, "Balancing --- ");
			airlineCN51SummaryVO.setInvFormstatus(BALANCED);
			mRAFormOne.setFormOneStatus(BALANCED);
			mRAFormOne.setLastUpdatedTime(airlineCN51SummaryVO.getLastUpdatedTime());
			mRAFormOne.setLastUpdatedUser(airlineCN51SummaryVO.getLastUpdatedUser());



		} else {
			log.log(log.FINE, "inside Unbalacing  ");
			airlineCN51SummaryVO.setInvFormstatus(UNBALANCED);
			mRAFormOne.setFormOneStatus(UNBALANCED);
			mRAFormOne.setLastUpdatedTime(airlineCN51SummaryVO.getLastUpdatedTime());
			mRAFormOne.setLastUpdatedUser(airlineCN51SummaryVO.getLastUpdatedUser());

		}

		airlineCN51Smy.setInvFormstatus(airlineCN51SummaryVO.getInvFormstatus());
		airlineCN51Smy.setLastUpdatedTime(airlineCN51Smy.getLastUpdatedTime());
		airlineCN51Smy.setLastUpdatedUser(airlineCN51SummaryVO.getLastUpdatedUser());

	}


	}catch(FinderException fe){
		if(airlineCN51SummaryVO.getInvStatus()!=null){
		if(airlineCN51SummaryVO.getInvStatus().equals(NEW)){
		airlineCN51SummaryVO.setInvFormstatus(NEW);
		log.log(log.FINE, "finder");
	}
		}
	}catch (SystemException e) {

		e.getMessage();
	}




}





/**
 *
 * @param filterVO
 * @return
 * @throws RemoteException
 * @throws SystemException
 */
public String findInvoiceListingCurrency(AirlineCN51FilterVO filterVO) throws RemoteException,
SystemException{
	String lstcurcod=null;
	AirlineCN51Summary airlineCN51Summary =null;
	try{
	String companyCode = filterVO.getCompanyCode();

	int airlineIdentifier = filterVO.getAirlineIdentifier();

	String interlineBillingType = filterVO
			.getInterlineBillingType();

	String invoiceNumber = filterVO.getInvoiceReferenceNumber();

	String clearancePeriod = filterVO.getIataClearancePeriod();

	 airlineCN51Summary = AirlineCN51Summary.find(
			companyCode, airlineIdentifier, interlineBillingType,
			invoiceNumber, clearancePeriod);
	}
	catch(FinderException finderException){
		return lstcurcod;
	}
	//airlineCN51Summary.get
	if(airlineCN51Summary!=null){
		lstcurcod= airlineCN51Summary.getListingCurrency();
	}
	return lstcurcod;
}





/**@author A-3447
 * for generating memonumber
 * @param companyCode
 * @param clearancePeriod
 * @param airlineCode
 * @return
 * @throws GenerationFailedException
 * @throws SystemException
 */

public String generateMemoCode(String companyCode,String clearancePeriod,String airlineCode) throws GenerationFailedException, SystemException{
	Criterion criterion = null;
	String generatedKey = null;
	String key = null;

/*Memo code generation*/
StringBuilder keyBuilder = new StringBuilder(5);
criterion = KeyUtils.getCriterion(companyCode
		, MRA_ARL_REJECTION_MEMO_NO);
log.log(Log.INFO, "criterion");
generatedKey = KeyUtils.getKey(criterion);
log.log(Log.INFO, "key generated", generatedKey);
int keyLength = generatedKey.length();
/*for (int count = 0; count < 5 - keyLength; count++) {
	keyBuilder.append("0");
}*/

keyBuilder.append(generatedKey);
log.log(Log.INFO, "key keyBuilder", keyBuilder);
key = new StringBuilder().append("RM").append(
		airlineCode).append(
		clearancePeriod).append("INV").append(keyBuilder)
		.toString();

log.log(Log.INFO, new StringBuilder().append("Generated Key -->")
		.append(key).toString());
log.exiting("Memo", "generateMemoNUmber");
return key;
}



/**
 * @author A-3447
 * @param rejectionMemoVO
 * @return
 * @throws GenerationFailedException
 * @throws SystemException
 * @throws FinderException
 */
public String saveRejectionMemos(RejectionMemoVO rejectionMemoVO) throws GenerationFailedException, SystemException, FinderException {
	String memoCode=generateMemoCode(rejectionMemoVO.getCompanycode(),rejectionMemoVO.getClearanceperiod(),rejectionMemoVO.getAirlineCode());
	rejectionMemoVO.setMemoCode(memoCode);

	log.log(Log.INFO, "Memo Code-->", memoCode);
	String companyCode = rejectionMemoVO.getCompanycode();
	int airlineIdentifier = rejectionMemoVO.getAirlineIdentifier();
	String interlineBillingType = rejectionMemoVO.getInterlinebillingtype();
	String invoiceNumber = rejectionMemoVO.getInvoiceNumber();
	String clearancePeriod = rejectionMemoVO.getClearanceperiod();
	String classType=rejectionMemoVO.getClassType();


	if(rejectionMemoVO.getScreenFlag().equals(CAPTURE_INVOICE)){
		log.entering("-inside Controller->> ", "marking status as rejected ");
		try{
		AirlineCN51Summary airlineCN51Summary = AirlineCN51Summary.find(
				companyCode, airlineIdentifier, interlineBillingType,
				invoiceNumber, clearancePeriod);
		if (airlineCN51Summary != null ) {
			new RejectionMemo(rejectionMemoVO);
			airlineCN51Summary.setInvStatus(REJECTED);
			airlineCN51Summary.setLastUpdatedTime(rejectionMemoVO.getLastUpdatedTime());
			airlineCN51Summary.setLastUpdatedUser(rejectionMemoVO.getLastUpdatedUser());
		}
		else{
			log.log(log.FINE, "inside not  found--- ");

		}}
		catch(FinderException e){

			log.log(log.FINE, "inside not  found--- ");
		}
	}

	else{
		if(CAPTURE_FORM_ONE.equals(rejectionMemoVO.getScreenFlag())){

		try{
			AirlineCN51Summary airlineCN51Summary = AirlineCN51Summary.find(
					companyCode, airlineIdentifier, interlineBillingType,
					invoiceNumber, clearancePeriod);

		if(airlineCN51Summary!=null){
			new RejectionMemo(rejectionMemoVO);
			airlineCN51Summary.setInvStatus(REJECTED);
			airlineCN51Summary.setLastUpdatedTime(rejectionMemoVO.getLastUpdatedTime());
			airlineCN51Summary.setLastUpdatedUser(rejectionMemoVO.getLastUpdatedUser());
		}
		}catch(FinderException e){

			log.log(log.FINE, "inside  entity not  found--- ");
		}
	}

	}
	/** For Audit 
	 * Added by A-4809**/

	RejectionMemo rejectionMemo = RejectionMemo.find(companyCode,
			airlineIdentifier, memoCode);
	if(rejectionMemo !=null){
	RejectionMemoVO memoVO = populateRejectionMemoVO(rejectionMemo);
	AirlineBillingController airlineBillingController = (AirlineBillingController)SpringAdapter.getInstance().getBean("airlineBillingcontroller");
	airlineBillingController.performAirlineBillingAudit(memoVO,
			AirlineBillingAuditVO.AUDIT_REJECTIONMEMO_CAPTURED);
	}
	String sysparValue = null;

	ArrayList<String> systemParameters = new ArrayList<String>();
	systemParameters.add (DSN_AUDIT_REQD);
	systemParameters.add(MRAIMPORTLEVL);
	Map<String, String> systemParameterMap = null;
	try {
		systemParameterMap = new SharedDefaultsProxy()
				.findSystemParameterByCodes(systemParameters);
	} catch (ProxyException e) {
		// TODO Auto-generated catch block
		e.getMessage();
	}
	log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
	if (systemParameterMap != null) {
		sysparValue = systemParameterMap.get (DSN_AUDIT_REQD);
		if(RejectionMemoVO.FLAG_YES.equals(MRAIMPORTLEVL)){
		if (RejectionMemoVO.FLAG_YES.equals (sysparValue)){
			AirlineBillingController airlineBillingController = (AirlineBillingController)SpringAdapter.getInstance().getBean("airlineBillingcontroller");
			airlineBillingController.auditDSNForRejectionMemo (rejectionMemoVO);
		}
		}
	}

	return memoCode;


}
/**
 * @author A-3447
 * @param airlineCN51FilterVO
 * @return AirlineCN51SummaryVO
 * @throws SystemException
 * Method for picking all invoice flags from MTKMRAARLBLG table
 */

public AirlineForBillingVO findAllInvoiceFlags(AirlineCN51FilterVO airlineCN51FilterVO) throws SystemException{
	log.entering("Airline Billing Controller", "findAllInvoiceFlags");
	AirlineForBillingVO airlineForBillingVO=new AirlineForBillingVO();
	try{
	MRAAirlineForBilling mRAAirlineForBilling=MRAAirlineForBilling.find(airlineCN51FilterVO.getCompanyCode(), airlineCN51FilterVO.getAirlineIdentifier(), airlineCN51FilterVO.getIataClearancePeriod());

	if(mRAAirlineForBilling!=null){
		if(mRAAirlineForBilling.getIsFormTwoGenerated()!=null){
			if(("Y").equals(mRAAirlineForBilling.getIsFormTwoGenerated())){
				airlineForBillingVO.setFormTwoGenerated(true);
			}else{

				airlineForBillingVO.setFormTwoGenerated(false);
			}
		}
		if(mRAAirlineForBilling.getIsFormThreeCaptured()!=null){
			if(("Y").equals(mRAAirlineForBilling.getIsFormThreeCaptured())){
				airlineForBillingVO.setFormThreeCaptured(true);
			}
			else{

				airlineForBillingVO.setFormThreeCaptured(false);
			}
		}
		if(mRAAirlineForBilling.getCapturedFormOneFlag()!=null){
			if(("Y").equals(mRAAirlineForBilling.getCapturedFormOneFlag())){
				airlineForBillingVO.setCapturedFormOneFlag(true);

			}
			else{ airlineForBillingVO.setCapturedFormOneFlag(false);


			}
		}
		if(mRAAirlineForBilling.getCapturedInvoiceFlag()!=null){
			if(("Y").equals(mRAAirlineForBilling.getCapturedInvoiceFlag())){

				airlineForBillingVO.setCapturedInvoiceFlag(true);
			}else{


				airlineForBillingVO.setCapturedInvoiceFlag(false);
			}
		}
		if(mRAAirlineForBilling.getGeneratedFormOneFlag()!=null){
			if(("Y").equals(mRAAirlineForBilling.getGeneratedFormOneFlag())){

				airlineForBillingVO.setGeneratedFormOneFlag(true);
			}else{

				airlineForBillingVO.setGeneratedFormOneFlag(false);
			}
		}
		if(mRAAirlineForBilling.getGeneratedInvoiceFlag()!=null){
			if(("Y").equals(mRAAirlineForBilling.getGeneratedInvoiceFlag())){
				airlineForBillingVO.setGeneratedInvoiceFlag(true);

			}else{

				airlineForBillingVO.setGeneratedInvoiceFlag(false);
			}

		}

		log.log(Log.INFO, "returning vo--", airlineForBillingVO);


	}
	}
	catch(FinderException  e){
		log.log(Log.SEVERE, "Finder--------");

	}
	return airlineForBillingVO;



}
/**
 * Method saveRejectionMemo
 *
 * @param rejectionMemoVO
 * @throws SystemException
 * @throws FinderException
 * @author A-3429
 */

public String saveRejectionMemoForDsn(
		RejectionMemoVO rejectionMemoVO) throws SystemException{
	log.entering(CLASS_NAME, "saveRejectionMemo");
	log.entering("Memo", "generateMemoNUmber");
	Criterion criterion = null;
	String generatedKey = null;
	String key = null;
	double billedAmt = 0.0;
	double acceptedAmt = 0.0;
	double rejectedAmt = 0.0;
	double exchgRate = 0.0;
	double provAmt = 0.0;
	log.log(Log.INFO, " rejectionMemoVO from Controller", rejectionMemoVO);
		String companyCode = rejectionMemoVO.getCompanycode();
		String airlineCode = rejectionMemoVO.getAirlineCode();
		String userId = rejectionMemoVO.getLastUpdatedUser();
		String serialNumber = rejectionMemoVO.getSerialNumber();
		String invoiceNumber = rejectionMemoVO.getInvoiceNumber();
		String clearancePeriod = rejectionMemoVO.getClearanceperiod();
		int airlineIdentifier = rejectionMemoVO.getAirlineIdentifier();
		String contractCurCod=rejectionMemoVO.getContractCurrencyCode();
		String billingCurCod=rejectionMemoVO.getBillingCurrencyCode();
		String exceptionCode=rejectionMemoVO.getExceptionCode();
		if(rejectionMemoVO.getContractBilledAmount()!=null) {
		 billedAmt=rejectionMemoVO.getContractBilledAmount();
		}
		if(rejectionMemoVO.getContractAcceptedAmount()!=null) {
		 acceptedAmt=rejectionMemoVO.getContractAcceptedAmount();
		}
		if(rejectionMemoVO.getContractRejectedAmount()!=null) {
		 rejectedAmt=rejectionMemoVO.getContractRejectedAmount();
		} 
		//double billingbldAmt = rejectionMemoVO.getBillingBilledAmount();
		//double billingAcceptedAmt=rejectionMemoVO.getBillingAcceptedAmount();
		//double billingRejectedAmt=rejectionMemoVO.getBillingRejectedAmount();
		if(rejectionMemoVO.getContractBillingExchangeRate()!=null) {
		 exchgRate=rejectionMemoVO.getContractBillingExchangeRate();
		}
		if(rejectionMemoVO.getProvisionalAmount()!=null) {
		 provAmt = rejectionMemoVO.getProvisionalAmount();
		}
		//Added by A-4809 for BUG ICRD-167457 .. Starts
		String dsn = null;
		if(rejectionMemoVO.getDsn() != null && !rejectionMemoVO.getDsn().isEmpty()){
			int len = rejectionMemoVO.getDsn().length();
			if(len == 29){
				dsn = rejectionMemoVO.getDsn().substring(16, 20);
			}else if(len == 4){
				dsn=rejectionMemoVO.getDsn(); 
			} 
		}
		//Added by A-4809 for BUG ICRD-167457 .. Ends
		//String dsn=rejectionMemoVO.getDsn();
		String blgbase=rejectionMemoVO.getBillingBasis();
		String csgDocNum =rejectionMemoVO.getCsgDocNum();
		int csgSeqNum = rejectionMemoVO.getCsgSeqNum();
		String poaCode = rejectionMemoVO.getPoaCode();
		String memoCode =rejectionMemoVO.getMemoCode();
		LocalDate rejectedDate=rejectionMemoVO.getRejectedDate();
		LocalDate InwardInvoiceDate=rejectionMemoVO.getInwardInvoiceDate();
		RejectionMemo rejectionmemo = null;
		try{
		rejectionmemo = rejectionmemo.find(companyCode,airlineIdentifier,memoCode);
		}catch (FinderException e) {
			log.log(Log.FINE, "FinderException---->>>  ");
		}

		if(rejectionmemo==null){
	 		log.log(Log.INFO, "Inside if loop");

		/*Memo code generation*/
		StringBuilder keyBuilder = new StringBuilder(5);
		criterion = KeyUtils.getCriterion(rejectionMemoVO
				.getCompanycode(), MRA_ARL_REJECTION_MEMO_NO);
		log.log(Log.INFO, "criterion");
		generatedKey = KeyUtils.getKey(criterion);
		log.log(Log.INFO, "key generated", generatedKey);
		int keyLength = generatedKey.length();
		/*for (int count = 0; count < 5 - keyLength; count++) {
			keyBuilder.append("0");
		}*/
		keyBuilder.append(generatedKey);
		log.log(Log.INFO, "key keyBuilder", keyBuilder);
		key = new StringBuilder().append("RM").append(
				rejectionMemoVO.getAirlineCode()).append(
				clearancePeriod).append("DSN").append(keyBuilder)
				.toString();

		log.exiting("Memo", "generateMemoNUmber");

		/*changing the exception and memo status in expdtl table*/
		//AirlineExceptions airlineExceptions = null;

			AirlineExceptionsPK airlineExceptionsPK = new AirlineExceptionsPK();
			log.log(Log.INFO, "airlineexcep companyCode", companyCode);
			log.log(Log.INFO, "airlineexcep airlineIdentifier",
					airlineIdentifier);
			log.log(Log.INFO, "airlineexcep exceptionCode", exceptionCode);
			log.log(Log.INFO, "airlineexcep serialNumber", serialNumber);
			log.log(Log.INFO, "airlineexcep invoiceNumber", invoiceNumber);
			log.log(Log.INFO, "airlineexcep clearancePeriod", clearancePeriod);
			airlineExceptionsPK.setCompanyCode(companyCode);
			airlineExceptionsPK.setAirlineIdentifier(airlineIdentifier);
			airlineExceptionsPK.setExceptionCode(exceptionCode);
			airlineExceptionsPK.setSerialNumber(Integer.parseInt(serialNumber));
			airlineExceptionsPK.setInvoiceNumber(invoiceNumber);
			airlineExceptionsPK.setClearancePeriod(clearancePeriod);
			log.log(Log.INFO, "airlineexcep obtained before finder",
					airlineExceptionsPK);
			log.log(Log.INFO, "airlineexcep obtained before finder2",
					airlineExceptionsPK);
			try{
				AirlineExceptions airlineExceptions = AirlineExceptions.find(airlineExceptionsPK);

			log.log(Log.INFO, "airlineexcep obtained!!!!!!!!!",
					airlineExceptions);
			airlineExceptions.setExceptionStatus("R");
			log.log(Log.INFO, "KEY set!!!!!!!!!", key);
			airlineExceptions.setMemoCode(key);
			log.log(Log.INFO, "airlineexcep values set!!!!!!!!!");
			airlineExceptions.setLastUpdateTime(rejectionMemoVO.getLastUpdatedTime());
			airlineExceptions.setLastUpdateUser(rejectionMemoVO.getLastUpdatedUser());
			}catch(FinderException e){
				log.log(Log.FINE, "inside catch");
			}
		RejectionMemoVO rejectionVO = new RejectionMemoVO();
		rejectionVO.setCompanycode(companyCode);
		rejectionVO.setAirlineCode(airlineCode);
		rejectionVO.setAirlineIdentifier(airlineIdentifier);
		rejectionVO.setMemoCode(key);
		rejectionVO.setContractCurrencyCode(contractCurCod);
		rejectionVO.setBillingCurrencyCode(billingCurCod);
		rejectionVO.setContractBilledAmount(billedAmt);
		rejectionVO.setContractAcceptedAmount(acceptedAmt);
		rejectionVO.setContractRejectedAmount(rejectedAmt);
		rejectionVO.setBillingBilledAmount(rejectionMemoVO.getBillingBilledAmount());
		rejectionVO.setBillingAcceptedAmount(rejectionMemoVO.getBillingAcceptedAmount());
		rejectionVO.setBillingRejectedAmount(rejectionMemoVO.getBillingRejectedAmount());
		rejectionVO.setProvisionalAmount(provAmt);
		rejectionVO.setInwardClearancePeriod(clearancePeriod);
		rejectionVO.setCompanycode(companyCode);
		rejectionVO.setAirlineCode(airlineCode);
		rejectionVO.setContractBillingExchangeRate(exchgRate);
		rejectionVO.setAirlineIdentifier(airlineIdentifier);
		rejectionVO.setMemoCode(key);
		rejectionVO.setInwardInvoiceNumber(invoiceNumber);
		rejectionVO.setDsn(dsn);
		rejectionVO.setBillingBasis(blgbase);
		rejectionVO.setCsgDocNum(csgDocNum);
		rejectionVO.setCsgSeqNum(csgSeqNum);
		rejectionVO.setPoaCode(poaCode);
		rejectionVO.setRejectedDate(rejectedDate);
		rejectionVO.setChargeNotConvertedToContractIndicator
				(rejectionMemoVO.getChargeNotConvertedToContractIndicator());
		rejectionVO.setChargeNotCoveredByContractIndicator
				(rejectionMemoVO.getChargeNotCoveredByContractIndicator());
		rejectionVO.setIncorrectExchangeRateIndicator
		(rejectionMemoVO.getIncorrectExchangeRateIndicator());
		rejectionVO.setDuplicateBillingIndicator
		(rejectionMemoVO.getDuplicateBillingIndicator());
		rejectionVO.setDuplicateBillingInvoiceNumber
		(rejectionMemoVO.getDuplicateBillingInvoiceNumber());
		rejectionVO.setDuplicateBillingInvoiceDate
		(rejectionMemoVO.getDuplicateBillingInvoiceDate());
		rejectionVO.setNoApprovalIndicator
		(rejectionMemoVO.getNoApprovalIndicator());
		rejectionVO.setNoReceiptIndicator
		(rejectionMemoVO.getNoReceiptIndicator());
		rejectionVO.setRequestAuthorisationIndicator
		(rejectionMemoVO.getRequestAuthorisationIndicator());
		rejectionVO.setRequestAuthorisationReference
		(rejectionMemoVO.getRequestAuthorisationReference());
		rejectionVO.setRequestAuthorisationDate
		(rejectionMemoVO.getRequestAuthorisationDate());
		rejectionVO.setOutTimeLimitsForBillingIndicator
		(rejectionMemoVO.getOutTimeLimitsForBillingIndicator());
		rejectionVO.setOtherIndicator
		(rejectionMemoVO.getOtherIndicator());
		rejectionVO.setRemarks
		(rejectionMemoVO.getRemarks());
		rejectionVO.setMemoStatus("OB");
		rejectionVO.setInwardInvoiceDate(InwardInvoiceDate);
		log.log(Log.INFO, "rejectionVO ", rejectionVO);
		RejectionMemoFilterVO filterVO = new RejectionMemoFilterVO();
		filterVO.setCompanyCode(companyCode);
		filterVO.setMemoCode(key);
		filterVO.setAirlineIdentifier(airlineIdentifier);

		/*saving details in arlmemo table*/
		new RejectionMemo(rejectionVO);
		/*accounting is triggered here added by Meenu A-2565*/
		/**
		 * For Audit
		 * Added by A-4809
		 */
		RejectionMemo rejectionMemo;
		try {
			rejectionMemo = RejectionMemo.find(companyCode,
					airlineIdentifier, key);
			if(rejectionMemo !=null){
				RejectionMemoVO memoVO = populateRejectionMemoVO(rejectionMemo);
				AirlineBillingController airlineBillingController = (AirlineBillingController)SpringAdapter.getInstance().getBean("airlineBillingcontroller");
				airlineBillingController.performAirlineBillingAudit(memoVO,
						AirlineBillingAuditVO.AUDIT_REJECTIONMEMO_CAPTURED);
				}
		} catch (FinderException e) {
			log.log(Log.SEVERE, "FinderExceptionCaught for Rejection Memo");
		}
		//Audit ends
		if(rejectionVO.getCsgDocNum()!=null){
		SharedDefaultsProxy sharedDefaultsProxy=new SharedDefaultsProxy();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		//system parameter for accounting
		systemParameterCodes.add(SYS_PARA_ACCOUNTING_ENABLED);
		Map<String, String> systemParameters = null;
		try {
			systemParameters = sharedDefaultsProxy.findSystemParameterByCodes(systemParameterCodes);
		} catch (ProxyException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		if (systemParameters != null) {
			String accountingEnabled = (systemParameters.get(SYS_PARA_ACCOUNTING_ENABLED));
			log.log(Log.FINE, " accountingEnabled==================> ",
					accountingEnabled);
		//if  accounting enabled
		if( RejectionMemoVO.FLAG_YES.equals(accountingEnabled)){
			//accounting at despatch level
			RejectionMemo.triggerRejectionMemoAccounting(rejectionVO);
			}
		}
		}
		}else{
			rejectionmemo.update(rejectionMemoVO);
 			//RejectionMemoVO memoVO = populateRejectionMemoVO(rejectionMemo);
			AirlineBillingController airlineBillingController = (AirlineBillingController)SpringAdapter.getInstance().getBean("airlineBillingcontroller");
			airlineBillingController.performAirlineBillingAudit(rejectionMemoVO,
					AirlineBillingAuditVO.AUDIT_REJECTIONMEMO_UPDATED);
		}
		return key;

}
/**
 * @param reportSpec
 * @return
 * @throws SystemException
 * @throws RemoteException
 * @throws MailTrackingMRABusinessException
 */
public Map<String, Object> viewFormTwoPrint(ReportSpec reportSpec)
		throws SystemException, RemoteException,
		MailTrackingMRABusinessException {

	InterlineFilterVO interlineFilterVo = (InterlineFilterVO) reportSpec
			.getFilterValues().iterator().next();

	Collection<AirlineForBillingVO>  airlineForBillingVO= MRAAirlineForBilling.findAirlineDetails(interlineFilterVo);

	log.log(Log.INFO, "data airlineForBillingVO34----->", airlineForBillingVO);
	log.log(Log.INFO, "outside null loop---->");

	if (airlineForBillingVO == null || airlineForBillingVO.size() <= 0) {
		log.log(Log.INFO, "inside null loop---->");
		ErrorVO errorVo = new ErrorVO(
				MailTrackingMRABusinessException.MAILTACKING_MRA_AIRLINEBILLING_NOREPORTDATA);
		errorVo.setErrorDisplayType(ErrorDisplayType.ERROR);
		MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
		mailTrackingMRABusinessException.addError(errorVo);
		throw mailTrackingMRABusinessException;
	}

	log.log(Log.INFO, "filter vo!@#&----->", interlineFilterVo);
	reportSpec.addParameter(interlineFilterVo);
	reportSpec.setData(airlineForBillingVO);
	return ReportAgent.generateReport(reportSpec);

}

/**
*
* @param cn51FilterVO
* @return
* @throws SystemException
* @throws CN51DetailsAlreadyCapturedException
*/
public AirlineCN51SummaryVO findCN51DetailColection(AirlineCN51FilterVO cn51FilterVO)
		throws SystemException{
	log.entering(CLASS_NAME, "findCN51DetailColection");
	return AirlineCN51Summary.findCN51DetailColection(cn51FilterVO);
	/*
	 * String iataclearancePrd = cn51FilterVO.getIataClearancePeriod(); int
	 * length = iataclearancePrd.length();
	 * cn51FilterVO.setIataClearancePeriod(getClearancePrd_For_Months_Ending(iataclearancePrd));
	 */
}
/**
*
* @param reportSpec
* @return
* @throws SystemException
* @throws RemoteException
* @throws MailTrackingMRABusinessException
*/
public Map<String,Object> generateCN66InvoiceReport(ReportSpec reportSpec)
throws SystemException,RemoteException,MailTrackingMRABusinessException
{
	log.log(Log.INFO," inside generateCN66InvoiceReport");
	LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
	AirlineVO airlineVO = null;
	AirlineCN66DetailsFilterVO filterVO = (AirlineCN66DetailsFilterVO)reportSpec.
   getFilterValues().iterator().next();
	ArlInvoiceDetailsReportVO 	invoiceDetailsReportVO	=	AirlineCN51Summary.generateCN66InvoiceReport(filterVO);
   if(invoiceDetailsReportVO == null){
		MailTrackingMRABusinessException mailTrackingMRABusinessException
		 = new MailTrackingMRABusinessException();
		ErrorVO reporterror = new ErrorVO(MailTrackingMRABusinessException.NO_DATA_FOUND);
		mailTrackingMRABusinessException.addError(reporterror);
		 throw mailTrackingMRABusinessException;
	}
   Collection <ArlInvoiceDetailsReportVO>invoiceDetailsReportVOs=new ArrayList<ArlInvoiceDetailsReportVO>();
   invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
   log.log(Log.INFO, "vos returned ", invoiceDetailsReportVOs);
log.log(Log.INFO, "Airline Identifier--> ", logon.getOwnAirlineIdentifier());
airlineVO=AirlineCN51Summary.findAirlineAddresss(filterVO.getCompanyCode(),logon.getOwnAirlineIdentifier());
	if(airlineVO!=null){
		 ReportMetaData parameterMetaDatas = new ReportMetaData();
		 if (airlineVO.getAirlineName() == null) {
				airlineVO.setAirlineName("");
			}
			if (airlineVO.getBillingAddress() == null) {
				airlineVO.setBillingAddress("");
			}

			if (airlineVO.getBillingPhone1()== null) {
				airlineVO.setBillingPhone1("");
			}

			if (airlineVO.getBillingPhone2()== null) {
				airlineVO.setBillingPhone2("");
			}

			if (airlineVO.getBillingFax()== null) {
				airlineVO.setBillingFax("");
			}
   	 parameterMetaDatas.setFieldNames(new String[] {"airlineName", "billingAddress", "billingPhone1", "billingPhone2", "billingFax"});
   	 reportSpec.addParameterMetaData(parameterMetaDatas);
   	 reportSpec.addParameter(airlineVO);
	}
	ReportMetaData reportMetaData = new ReportMetaData();
	reportMetaData.setColumnNames(new String[]
	                                         {"INVNUM","TOTAMTBLGCUR",
			"BLGCURCOD","ARLNAM","BLGADR","BLGCTYCOD","BLGSTANAM","BLGCNTCOD","BLGPHNONE",
			"BLGPHNTWO","BLGFAX","BLDDAT","MALCTGCOD","CLRPRD"});
		reportMetaData.setFieldNames(new String[]
	                   {"invoiceNumber",
			"totalAmountinBillingCurrency","billingCurrencyCode",
			"arlName","address","city","state","country","phone1","phone2",
			"fax","billedDate","mailCategoryCode","clrPrd"});
		  reportSpec.setReportMetaData(reportMetaData);
		  reportSpec.setData(invoiceDetailsReportVOs);
		  return ReportAgent.generateReport(reportSpec);
		  /**
		     * @author a-3447 for AirNZ Enhancemet:27970 ends
		     */
}

	/**
	 * Method acceptAirlineInvoices
	 *
	 * @param airlineExceptionsVOs
	 * @throws SystemException
	 *
	 * @author A-3429
	 */
	@Advice(name = "mailtracking.mra.acceptAirlineDsns" , phase=Phase.POST_INVOKE)
	public void acceptAirlineDsns(
			Collection<AirlineExceptionsVO> airlineExceptionsVOs)
			throws SystemException {
		log.entering(CLASS_NAME, "acceptAirlineDsns");
		ArrayList<AirlineExceptionsVO> exceptionVOs = new ArrayList<AirlineExceptionsVO>(
				airlineExceptionsVOs);
		int size = exceptionVOs.size();
		try {
			for (int i = 0; i < size; i++) {
				String companyCode = exceptionVOs.get(i).getCompanyCode();
				int airlineIdentifier = exceptionVOs.get(i)
						.getAirlineIdentifier();
				String invoiceNumber = exceptionVOs.get(i).getInvoiceNumber();
				String airlineCode = exceptionVOs.get(i).getAirlineCode();
				String clearancePeriod = exceptionVOs.get(i)
						.getClearancePeriod();
				int csgseqnumber=exceptionVOs.get(i).getCsgSeqNum();
				String csgdocnum=exceptionVOs.get(i).getCsgDocNum();
				String billingBasis=exceptionVOs.get(i).getBillingBasis();
				long mailSequenceNumber=exceptionVOs.get(i).getMailSequenceNumber();
				String poaCode=exceptionVOs.get(i).getPoaCode();
				ExceptionInInvoice exceptionInInvoice = ExceptionInInvoice
						.find(companyCode, airlineIdentifier, invoiceNumber,
								clearancePeriod);
				AirlineExceptions airlineExceptions = null;
				// log.log(Log.INFO, "airlineexcep obtained with size!!!!!!!!!"
				// + airlineExceptionsVOs.size());
				// for (int eSize = 0; eSize < expSize; eSize++) {
				AirlineExceptionsPK airlineExceptionsPK = new AirlineExceptionsPK();
				airlineExceptionsPK.setCompanyCode(companyCode);
				airlineExceptionsPK.setAirlineIdentifier(airlineIdentifier);
				airlineExceptionsPK.setExceptionCode(exceptionVOs.get(i)
						.getExceptionCode());
				airlineExceptionsPK.setSerialNumber(exceptionVOs.get(i)
						.getSerialNumber());
				airlineExceptionsPK.setInvoiceNumber(invoiceNumber);
				airlineExceptionsPK.setClearancePeriod(clearancePeriod);
				airlineExceptions = AirlineExceptions.find(airlineExceptionsPK);
				log.log(Log.INFO, "airlineexcep obtained!!!!!!!!!",
						airlineExceptions);
				airlineExceptions.setExceptionStatus("A");
				airlineExceptions.setRemark(exceptionVOs.get(i).getRemark());
				airlineExceptions.setLastUpdateTime(exceptionVOs.get(i)
						.getLastUpdatedTime());

				log.log(Log.INFO, "airlineexcep values set!!!!!!!!!");
				// }
				String billingType = exceptionInInvoice
						.getInterlineBillingType();
				AirlineCN51Summary airlineCN51Summary = AirlineCN51Summary
						.find(companyCode, airlineIdentifier, billingType,
								invoiceNumber, clearancePeriod);

				if (airlineCN51Summary.getAirlineCN66Details() != null) {
					for (AirlineExceptionsVO airlineExceptionsVO : exceptionVOs) {
						for (AirlineCN66Details airlineCN66Details : airlineCN51Summary
								.getAirlineCN66Details()) {

							if (!EXPCOD_DTL.equals(airlineExceptionsVO
									.getExceptionCode())) {
								if (airlineExceptionsVO
										.getCompanyCode()
										.equals(
												airlineCN66Details
														.getAirlineCN66DetailsPK()
														.getCompanyCode())
										&& airlineExceptionsVO
												.getAirlineIdentifier() == airlineCN66Details
												.getAirlineCN66DetailsPK()
												.getAirlineIdentifier()
										&& airlineExceptionsVO
												.getInvoiceNumber()
												.equals(
														airlineCN66Details
																.getAirlineCN66DetailsPK()
																.getInvoiceNumber())
										&& airlineExceptionsVO
												.getClearancePeriod()
												.equals(
														airlineCN66Details
																.getAirlineCN66DetailsPK()
																.getClearancePeriod())
										/*&& airlineExceptionsVO
												.getBillingBasis()
												.equals(
														airlineCN66Details
																.getBillingBasis())
										&& airlineExceptionsVO
												.getCsgDocNum()
												.equals(
														airlineCN66Details
																.getConsignmentDocumentNumber())
										&& airlineExceptionsVO.getCsgSeqNum() == airlineCN66Details
												.getConsignmentSequenceNumber()
										&& airlineExceptionsVO.getPoaCode()
												.equals(
														airlineCN66Details
																.getPoaCode())*/
										&& EXCEPTION.equals(airlineCN66Details
												.getDespatchStatus())) {
									airlineCN66Details
											.setDespatchStatus(ACCEPTED);
								}
							}

							else {
								if (airlineCN66Details
										.getAirlineCN66DetailsPK()
										.getCompanyCode().equals(
												airlineExceptionsVO
														.getCompanyCode())
										&& airlineCN66Details
												.getAirlineCN66DetailsPK()
												.getAirlineIdentifier() == airlineExceptionsVO
												.getAirlineIdentifier()
										&& airlineCN66Details
												.getAirlineCN66DetailsPK()
												.getInvoiceNumber()
												.equals(
														airlineExceptionsVO
																.getInvoiceNumber())
										&& airlineCN66Details
												.getDespatchSerialNo()
												.equals(
														airlineExceptionsVO
																.getDespatchSerNo())
										&& EXCEPTION.equals(airlineCN66Details
												.getDespatchStatus())) {
									airlineCN66Details
											.setDespatchStatus(ACCEPTED);
								}
							}
						}
					}
				}

				ExceptionInInvoice exceptionInInvoiceForUpdate = ExceptionInInvoice
						.find(companyCode, airlineIdentifier, invoiceNumber,
								clearancePeriod);
				boolean chkFlg = true;
				for (AirlineExceptions airlineException : exceptionInInvoiceForUpdate
						.getAirlineExceptions()) {
					if (!ACCEPTED.equals(airlineException.getExceptionStatus())) {
						chkFlg = false;
					}
				}
				if (chkFlg) {
					exceptionInInvoiceForUpdate.setExceptionStatus(ACCEPTED);
					exceptionInInvoiceForUpdate
							.setLastUpdatedTime(exceptionInInvoiceForUpdate
									.getLastUpdatedTime());
				}
				boolean smyTestFlg = true;
				AirlineCN51Summary airlineCN51Smry = AirlineCN51Summary.find(
						companyCode, airlineIdentifier, billingType,
						invoiceNumber, clearancePeriod);
				for (AirlineCN66Details airlineCN66Dtls : airlineCN51Smry
						.getAirlineCN66Details()) {
					if ((!ACCEPTED.equals(airlineCN66Dtls.getDespatchStatus()))
							&& !PROCESSED.equals(airlineCN66Dtls
									.getDespatchStatus())) {
						smyTestFlg = false;
					}
				}
				if (smyTestFlg) {
					airlineCN51Summary.setCn51status("P");
				}
				if (csgdocnum != null) {
					MRABillingMaster mRABillingMaster = MRABillingMaster.find(
							 companyCode, mailSequenceNumber);
					
					if (mRABillingMaster.getBillingDetails() != null) {
						for (MRABillingDetails mRABillingDetails : mRABillingMaster
								.getBillingDetails()) {
							if (PAYFLG.equals(mRABillingDetails
									.getPaymentFlag())) {
								mRABillingDetails.setBlgStatus(IBBILLED);
								mRABillingDetails.setSectStatus(PROCESSED);
							}
						}
					}

				}
				AirlineExceptionsVO airlineExceptionVO=exceptionVOs.get(i);
				//AirlineExceptions.triggerAcceptDSNAccounting(airlineExceptionVO);
				/*
				 * if (flg == cn66vosize && cn66vosize > 0) { log.log(Log.INFO,
				 * "flag " + flg); AirlineCN51Summary airlineCN51Summary =
				 * AirlineCN51Summary .find(companyCode, airlineIdentifier,
				 * billingType, invoiceNumber, clearancePeriod);
				 * log.log(Log.FINE, "entity obtained ");
				 * airlineCN51Summary.setCn51status("P"); log.log(Log.INFO,
				 * "before auditing cn51"); AirlineBillingAuditVO
				 * airlineBillingAuditVO = new AirlineBillingAuditVO(
				 * AirlineBillingAuditVO.AUDIT_MODULENAME,
				 * AirlineBillingAuditVO.AUDIT_SUBMODULENAME,
				 * AirlineBillingAuditVO.AUDIT_ENTITYCN51SMY);
				 * airlineBillingAuditVO = (AirlineBillingAuditVO) AuditUtils
				 * .populateAuditDetails(airlineBillingAuditVO,
				 * airlineCN51Summary, false); LogonAttributes logonAttributes =
				 * ContextUtils .getSecurityContext().getLogonAttributesVO();
				 * String userId = logonAttributes.getUserId();
				 * airlineBillingAuditVO.setUserId(userId);
				 * airlineBillingAuditVO.setAirlineCode(airlineCode);
				 * airlineBillingAuditVO.setClearancePeriod(clearancePeriod);
				 * airlineBillingAuditVO
				 * .setActionCode(AirlineBillingAuditVO.AUDIT_INVOICE_ACCEPTED);
				 * airlineBillingAuditVO = populateAirlineCN51AuditDetails(
				 * airlineBillingAuditVO, airlineCN51Summary); StringBuffer
				 * additionalInfo = new StringBuffer();
				 * additionalInfo.append("Airline ").append(airlineCode)
				 * .append(" with invoice number ").append(
				 * invoiceNumber).append(" ").append(
				 * AirlineBillingAuditVO.AUDIT_ACCEPT_ACTION);
				 * airlineBillingAuditVO
				 * .setAdditionalInformation(additionalInfo.toString());
				 * AuditUtils.performAudit(airlineBillingAuditVO);
				 * log.log(Log.INFO, "after auditing cn51"); }
				 */
			}
		} catch (FinderException e) {
			throw new SystemException(e.getErrorCode(), e);
		}

	}

	/**
	 * method to validate clearance period

	 * return of the method is changed to collection of vos
	 * by @author a-3434 for AirNZ reqirement for
	 * providing same clearaneperiod in different clearing houses

	 * @param iataCalenderFilterVO
	 *
	 * @return iataCalendarVOs
	 *
	 * @throws SystemException
	 *
	 * @throws RemoteException

	 *
	 */

	public IATACalendarVO validateClearancePeriod(

	IATACalendarFilterVO iataCalenderFilterVO) throws SystemException {

		log.entering("AirlineBillingController", "validateClearancePeriod");

		IATACalendarVO iataCalendarVO = null;

		try {

			iataCalendarVO = new CRADefaultsProxy()
					.validateClearancePeriod(iataCalenderFilterVO);

		}
		catch(ProxyException proxyException){
			log.log(log.INFO,"proxyException obtained");

		}catch (SystemException systemException) {

			throw new SystemException(systemException.getMessage());

		}

		log.exiting("AirlineBillingController", "validateNumericCode");

		return iataCalendarVO;

	}
	/**
	 * 	Method		:	AirlineBillingController.populateRejectionMemoVO
	 *	Added by 	:	A-4809 on Jan 7, 2015
	 * 	Used for 	:
	 *	Parameters	:	@param memo
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	RejectionMemoVO
	 */
	private RejectionMemoVO populateRejectionMemoVO(RejectionMemo memo)throws SystemException{
		RejectionMemoVO rejectionMemoVO = new RejectionMemoVO();
		RejectionMemoPK memoPK = memo.getRejectionMemoPK();
		rejectionMemoVO.setCompanycode(memoPK.getCompanyCode());
		rejectionMemoVO.setMemoCode(memoPK.getMemoCode());
		rejectionMemoVO.setAirlineIdentifier(memoPK.getAirlineIdentifier());
		rejectionMemoVO.setAirlineCode(memo.getAirlineCode());
		rejectionMemoVO.setInwardInvoiceNumber(memo.getInwardInvoiceNumber());
		//rejectionMemoVO.setCsgDocNum(memo.getConsignmentDocumentNumber()); //Commented as part of ICRD-265471
		//rejectionMemoVO.setCsgSeqNum(memo.getConsignmentSequenceNumber()); 
		//rejectionMemoVO.setPoaCode(memo.getPoaCode()); 
		//rejectionMemoVO.setBillingBasis(memo.getBillingBasis());  
		return rejectionMemoVO;
	}
	
	/**
	 * This method generate file for mail outward billing
	 *
	 * @author A-7794
	 * generateFile
	 * @param invoiceFilterVO
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public SISMessageVO generateFile(MiscFileFilterVO fileFilterVO)
			throws SystemException {

		Collection<SISMessageVO> sisMessageVOs = null;
		SISMessageVO sisMessage = null;
		MiscFileFilterVO isFilterVo = new MiscFileFilterVO();
		isFilterVo.setCompanyCode(fileFilterVO.getCompanyCode());
		isFilterVo.setClearancePeriod(fileFilterVO.getClearancePeriod());
		isFilterVo.setAirlineIdr(fileFilterVO.getAirlineIdr());
		log.log(Log.INFO, "Generating File" );
		sisMessageVOs = MRAAirlineBilling.generateIsFile(
				isFilterVo);
		if (sisMessageVOs != null && sisMessageVOs.size() > 0) {
			sisMessage = (SISMessageVO) sisMessageVOs.toArray(new SISMessageVO[sisMessageVOs.size()])[0];
		}
		if(sisMessage != null){
			sisMessage.setBillingCategory(MiscFileFilterVO.MISCELLANEOUS);
			generateIsFileName(sisMessage,fileFilterVO) ;
		}
		return sisMessage;
		
	}
	/**
	 * This method generate file name
	 *
	 * @author A-7794
	 * generateIsFileName
	 * @param invoiceFilterVO
	 * @return 
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	private void generateIsFileName(SISMessageVO sisMessage,
			MiscFileFilterVO isFileFilterVO) throws SystemException {
		String sisFileName="";
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
			LocalDate currentDate = new LocalDate(
					logonAttributes.getStationCode(), Location.STN, true);
			StringBuilder fileName = new StringBuilder();
			if(MiscFileFilterVO.MISCELLANEOUS.equals(sisMessage.getBillingCategory())){
				fileName.append("MXMLF-");
			}
			if(sisMessage.getIssuingOrganizationID().length() > 3){
				fileName.append(sisMessage.getIssuingOrganizationID().substring(1, sisMessage.getIssuingOrganizationID().length()));
			}else {
				fileName.append(sisMessage.getIssuingOrganizationID());
			}

			fileName.append("20");// prefixing value for year with clearance  period period
			fileName.append(isFileFilterVO.getClearancePeriod());
			Calendar cal = currentDate.toCalendar();
			fileName.append(cal.get(Calendar.YEAR));
			if(cal.get(Calendar.MONTH)<9){
				fileName.append("0");
			}
			fileName.append(cal.get(Calendar.MONTH)+1);

			if((cal.get(Calendar.DAY_OF_MONTH))<10){
				fileName.append("0");
			}
			fileName.append(cal.get(Calendar.DAY_OF_MONTH));
			if(cal.get(Calendar.HOUR_OF_DAY)<10){
				fileName.append("0");
			}
			fileName.append(cal.get(Calendar.HOUR_OF_DAY));
			if(cal.get(Calendar.MINUTE)<10){
				fileName.append("0");
			}
			fileName.append(cal.get(Calendar.MINUTE));
			if(cal.get(Calendar.SECOND)<10){
				fileName.append("0");
			}
			fileName.append(cal.get(Calendar.SECOND));
			fileName.append(".xml");
			log.log(Log.INFO, "IS File Generated File Name ::::", fileName);
			isFileFilterVO.setGeneratedFileName(fileName.toString());
			sisFileName = fileName.toString();
			sisMessage.setFileName(sisFileName);
		log.log(Log.INFO, "IS File Generated File Name ::::", sisMessage.getFileName());
	}  
  /**
	 * 	Method		:	AirlineBillingController.finalizeInvoice
	 *	Added by 	:	A-7929 on Aug 17, 2018
	 * @param selectedairlineCN51SummaryVO
	 * @throws SystemException 
	 * @throws FinderException 
	 */
	public void finalizeInvoice(Collection<AirlineCN51SummaryVO> selectedairlineCN51SummaryVO,String txnlogInfo) throws FinderException, SystemException {
		boolean isTxnSuccess = false;
		try{
		for(AirlineCN51SummaryVO airlineCN51SummaryVO : selectedairlineCN51SummaryVO){
		if(airlineCN51SummaryVO.getFileName() != null && airlineCN51SummaryVO.getFileName().trim().length() > 0){	
		AirlineCN51Summary airlineCN51Summary = AirlineCN51Summary.find(airlineCN51SummaryVO.getCompanycode(), airlineCN51SummaryVO.getAirlineidr(), airlineCN51SummaryVO.getInterlinebillingtype(), airlineCN51SummaryVO.getInvoicenumber(), airlineCN51SummaryVO.getClearanceperiod());
		airlineCN51Summary.setInvStatus("F");
		}
		else
		{
			AirlineCN51Summary airlineCN51Summary = AirlineCN51Summary.find(airlineCN51SummaryVO.getCompanycode(), airlineCN51SummaryVO.getAirlineidr(), airlineCN51SummaryVO.getInterlinebillingtype(), airlineCN51SummaryVO.getInvoicenumber(), airlineCN51SummaryVO.getClearanceperiod());
			airlineCN51Summary.setInvStatus("F");
		}
		}
		isTxnSuccess = true;
		}catch(SystemException exp){
            log.log(Log.INFO,exp);
			if(txnlogInfo != null){
				String[] txnInfo = txnlogInfo.split("-");
				InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
				LogonAttributes logonAttributes = ContextUtils
						.getSecurityContext().getLogonAttributesVO();
			    invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
				invoiceTransactionLogVO.setInvoiceType(MRAConstantsVO.MRA_OB_FINALIZE);
				invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		   		invoiceTransactionLogVO.setInvoiceGenerationStatus(MRAConstantsVO.FAILD);
		   		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
				invoiceTransactionLogVO.setRemarks(new StringBuilder(MRA_OB_FINALIZED_FAILED).append(txnInfo[2]).toString());
				invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
				invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
			    invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
			    invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
			    invoiceTransactionLogVO.setSerialNumber(Integer.parseInt(txnInfo[1]));
			    invoiceTransactionLogVO.setTransactionCode(txnInfo[0]);
			    try {
			     new CRADefaultsProxy().updateTransactionandRemarks(invoiceTransactionLogVO);
				} catch (ProxyException e) {
					log.log(Log.INFO,e);
				}
			}
		}
		if(isTxnSuccess && txnlogInfo != null){
			String[] txnInfo = txnlogInfo.split("-");
			InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
			LogonAttributes logonAttributes = ContextUtils
					.getSecurityContext().getLogonAttributesVO();
		    invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
			invoiceTransactionLogVO.setInvoiceType(MRAConstantsVO.MRA_OB_FINALIZE);
			invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	   		invoiceTransactionLogVO.setInvoiceGenerationStatus(MRAConstantsVO.COMPLETED);
	   		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
			invoiceTransactionLogVO.setRemarks(new StringBuilder(MRA_OB_FINALIZED_SUCCESS).append(txnInfo[2]).toString());
			invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
			invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		    invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		    invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
		    invoiceTransactionLogVO.setSerialNumber(Integer.parseInt(txnInfo[1]));
		    invoiceTransactionLogVO.setTransactionCode(txnInfo[0]);
		    try {
		     new CRADefaultsProxy().updateTransactionandRemarks(invoiceTransactionLogVO);
			} catch (ProxyException e) {
				log.log(Log.INFO,e);
			}
		}
	}


    /**
    * 
	 * 	Method		:	withdrawMailBags
	 *	Added by 	:	A-8061 
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	void
	 * @param airlineCN66DetailsVOs 
	 * @throws BusinessDelegateException 
	 */
public void withdrawMailBags(Collection<AirlineCN66DetailsVO> airlineCN66DetailsVOs)throws  SystemException  {
	
	AirlineCN66Details.withdrawMailBags(airlineCN66DetailsVOs);
	
	}
public Collection<AirlineCN51SummaryVO> getAirlineSummaryDetails(AirlineCN51FilterVO filterVO)throws  SystemException{
	return AirlineCN51Summary.generateInvoiceReports(filterVO);
}

	
	

	/**
	 * 
	 * 	Method		:	AirlineBillingController.saveSupportingDocumentDetails
	 *	Added by 	:	a-8061 on 29-Oct-2018
	 * 	Used for 	:	ICRD-265471
	 *	Parameters	:	@param sisSupportingDocumentDetailsVOs
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<SisSupportingDocumentDetailsVO>
	 */
	public Collection<SisSupportingDocumentDetailsVO> saveSupportingDocumentDetails(
			Collection<SisSupportingDocumentDetailsVO> sisSupportingDocumentDetailsVOs)
			throws SystemException {
		int count = 0;
		int docSerialNumber = 0;
		for (SisSupportingDocumentDetailsVO sisSupportingDocumentDetailsVO : sisSupportingDocumentDetailsVOs) {
			if (SisSupportingDocumentDetailsVO.OPERATION_FLAG_INSERT
					.equals(sisSupportingDocumentDetailsVO.getOperationFlag())) {
			if (count == 0) {
				try {
					docSerialNumber = constructDAO()
							.findSupportingDocumentSerialNumber(
									sisSupportingDocumentDetailsVO);
					
				} catch (PersistenceException persistenceException) {
					throw new SystemException(
							persistenceException.getErrorCode());
				}
			}
			sisSupportingDocumentDetailsVO
					.setDocumentSerialNumber(docSerialNumber);
			saveSupportingDocumentDetails(sisSupportingDocumentDetailsVO);
			docSerialNumber++;
			count++;
		}
		}
		return sisSupportingDocumentDetailsVOs;
	}
	/**
	 * 
	 * 	Method		:	AirlineBillingController.saveSupportingDocumentDetails
	 *	Added by 	:	a-8061 on 29-Oct-2018
	 * 	Used for 	:	ICRD-265471
	 *	Parameters	:	@param sisSupportingDocumentDetailsVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	private void saveSupportingDocumentDetails(
			SisSupportingDocumentDetailsVO sisSupportingDocumentDetailsVO)
			throws SystemException {
		new MRASisSupportingDocument(sisSupportingDocumentDetailsVO).save();
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		int ownAirline = logonAttributes.getOwnAirlineIdentifier();
		updateAttachmentIndicatorInEntities(sisSupportingDocumentDetailsVO);
	}

	/**
	 * 
	 * 	Method		:	AirlineBillingController.downloadAttachment
	 *	Added by 	:	a-8061 on 29-Oct-2018
	 * 	Used for 	:	ICRD-265471
	 *	Parameters	:	@param supportingDocumentFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	SisSupportingDocumentDetailsVO
	 */
	public SisSupportingDocumentDetailsVO downloadAttachment(
			SupportingDocumentFilterVO supportingDocumentFilterVO)
			throws SystemException {
		try {
			return constructDAO().downloadAttachment(
					supportingDocumentFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	
	
	
	/**
	 * 
	 * 	Method		:	AirlineBillingController.constructDAO
	 *	Added by 	:	a-8061 on 29-Oct-2018
	 * 	Used for 	:	ICRD-265471
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	MRAAirlineBillingSQLDAO
	 */
	 private static MRAAirlineBillingSQLDAO constructDAO()throws SystemException {
	    	
			try {
				EntityManager entityManager = PersistenceController.getEntityManager();
				return MRAAirlineBillingSQLDAO.class.cast(entityManager.getQueryDAO("mail.mra.airlinebilling"));
			}
			catch(PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getErrorCode());
			}
		}
	 	/**
	 	 * 
	 	 * 	Method		:	AirlineBillingController.removeSupportingDocumentDetails
	 	 *	Added by 	:	a-8061 on 29-Oct-2018
	 	 * 	Used for 	:	ICRD-265471
	 	 *	Parameters	:	@param sisSupportingDocumentDetailsVOs
	 	 *	Parameters	:	@return
	 	 *	Parameters	:	@throws SystemException 
	 	 *	Return type	: 	Collection<SisSupportingDocumentDetailsVO>
	 	 */
		public Collection<SisSupportingDocumentDetailsVO> removeSupportingDocumentDetails(
				Collection<SisSupportingDocumentDetailsVO> sisSupportingDocumentDetailsVOs)
				throws  SystemException
				 {
			for (SisSupportingDocumentDetailsVO sisSupportingDocumentDetailsVO : sisSupportingDocumentDetailsVOs) {
				if (SisSupportingDocumentDetailsVO.OPERATION_FLAG_DELETE
						.equals(sisSupportingDocumentDetailsVO.getOperationFlag())) {
				removeSupportingDocumentDetails(sisSupportingDocumentDetailsVO);
			}
			}
			return sisSupportingDocumentDetailsVOs;
		}
		
		
		/**
		 * 
		 * 	Method		:	AirlineBillingController.removeSupportingDocumentDetails
		 *	Added by 	:	a-8061 on 29-Oct-2018
		 * 	Used for 	:	ICRD-265471
		 *	Parameters	:	@param sisSupportingDocumentDetailsVO
		 *	Parameters	:	@throws SystemException 
		 *	Return type	: 	void
		 */
		private void removeSupportingDocumentDetails(
				SisSupportingDocumentDetailsVO sisSupportingDocumentDetailsVO)
				throws SystemException {
			MRASisSupportingDocument sisSupportingDocument = null;
			try {
				sisSupportingDocument = new MRASisSupportingDocument()
						.find(sisSupportingDocumentDetailsVO);
				LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();

				int ownAirline = logonAttributes.getOwnAirlineIdentifier();
				updateAttachmentIndicatorInEntities(sisSupportingDocumentDetailsVO);
			} catch (FinderException e) {
				throw new SystemException(e.getErrorCode(), e);
			}
			sisSupportingDocument.remove();
		}
		
		/**
		 * 
		 * 	Method		:	AirlineBillingController.updateAttachmentIndicatorInEntities
		 *	Added by 	:	a-8061 on 29-Oct-2018
		 * 	Used for 	:	ICRD-265471
		 *	Parameters	:	@param sisSupportingDocumentDetailsVO
		 *	Parameters	:	@throws SystemException 
		 *	Return type	: 	void
		 */
		public void updateAttachmentIndicatorInEntities(
				SisSupportingDocumentDetailsVO sisSupportingDocumentDetailsVO
				 ) throws SystemException {
		
			RejectionMemo rejectionMemo = null;
			try {
				rejectionMemo = RejectionMemo.find(sisSupportingDocumentDetailsVO.getCompanyCode(),
						sisSupportingDocumentDetailsVO.getBilledAirline(), sisSupportingDocumentDetailsVO.getMemoNumber());
			} catch (FinderException finderException) {
				log.log(Log.INFO, "FinderException",finderException.getMessage());
			}
			if(rejectionMemo!=null){
				rejectionMemo.setAttachmentIndicator(sisSupportingDocumentDetailsVO.getAttachmentIndicator());
			}

		}
		
		
/**
 * @author A-5526
 * Method to change billing status for interline billing-mail outward case
 * @param documentBillingDetailsVOs
 * @throws SystemException
 */

public void changeStatus(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs) throws SystemException {
	Collection<AirlineCN66DetailsVO> airlineCn66DetailsVoCol = null;
	if(documentBillingDetailsVOs!=null && !documentBillingDetailsVOs.isEmpty()){
		for(DocumentBillingDetailsVO documentBillingDetailsVO:documentBillingDetailsVOs){
			AirlineCN66DetailsFilterVO airlineCN66DetailsFilterVO=new AirlineCN66DetailsFilterVO();
			airlineCN66DetailsFilterVO.setCompanyCode(documentBillingDetailsVO.getCompanyCode());
			airlineCN66DetailsFilterVO.setInterlineBillingType(documentBillingDetailsVO.getIntblgType());
			airlineCN66DetailsFilterVO.setInvoiceRefNumber(documentBillingDetailsVO.getInvoiceNumber());
			
			airlineCn66DetailsVoCol=AirlineCN66Details.findCN66DetailsVOsForStatusChange(airlineCN66DetailsFilterVO,documentBillingDetailsVO.getMailSequenceNumber());
			MRABillingMaster mRABillingMaster=null;
			
			if(airlineCn66DetailsVoCol!=null && !airlineCn66DetailsVoCol.isEmpty()){
				for(AirlineCN66DetailsVO airlineCN66DetailsVO:airlineCn66DetailsVoCol){
					if(airlineCN66DetailsVO.getMalSeqNum()!=0 && airlineCN66DetailsVO.getMalSeqNum()==documentBillingDetailsVO.getMailSequenceNumber()){
						AirlineCN66Details cn66Details = null;
						try {
							cn66Details = AirlineCN66Details.find(airlineCN66DetailsVO
									.getCompanyCode(), airlineCN66DetailsVO
									.getAirlineIdentifier(), airlineCN66DetailsVO
									.getInvoiceNumber(), airlineCN66DetailsVO
									.getInterlineBillingType(), airlineCN66DetailsVO
									.getSequenceNumber(), airlineCN66DetailsVO
									.getClearancePeriod(),
									airlineCN66DetailsVO.getDsnIdr(), 
									airlineCN66DetailsVO.getMalSeqNum()
									);    
							 mRABillingMaster = MRABillingMaster.find(
									documentBillingDetailsVO.getCompanyCode(),documentBillingDetailsVO.getMailSequenceNumber());
						} catch (FinderException finderException) {    
							log
									.log(Log.SEVERE,
											"FINDER EXCEPTION OCCURED IN FINDING AirlineCN66Details Entity");
						}
						if (cn66Details != null) {
							cn66Details.remove();
							
							
							
							if (mRABillingMaster!=null && mRABillingMaster.getBillingDetails() != null) {
								for (MRABillingDetails mRABillingDetails : mRABillingMaster
										.getBillingDetails()) {
									if ("R".equals(mRABillingDetails
											.getPaymentFlag())) {
										mRABillingDetails.setBlgStatus("OB");
										
									}
								}    
							}
							
							log.log(Log.FINE, "Despatch Status has been Updated");
						}
						
						
						
					}
				}
			}
			} 
			
			
		}
	
	
}

}


