	/*
	 * PrintCommand.java Created on 31 Oct, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.irregularity;


	import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
	import static com.ibsplc.icargo.framework.util.time.Location.NONE;
	import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.HashMap;

	
	import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAIrregularityFilterVO;
	import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
	import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
	import com.ibsplc.icargo.framework.util.time.LocalDate;
	import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
	import com.ibsplc.icargo.framework.web.command.InvocationContext;
	import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
	import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAIrregularityForm;
	
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
	import com.ibsplc.xibase.server.framework.vo.ErrorVO;
	import com.ibsplc.xibase.util.log.Log;
	import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

	/**
	 * @author A-3229
	 *
	 */
	public class PrintCommand extends AbstractPrintCommand{
		
		private Log log = LogFactory.getLogger("MRA_DEFAULTS");
		private static final String REPORT_ID = "RPTLST304";
		private static final String RESOURCE_BUNDLE_KEY = "irregularityresources";
		private static final String ACTION = "printIrregularityReport";
		private static final String CLASS_NAME="PrintCommand";
		private static final String PRINT_FAILURE = "normal-report-error-jsp";
		
	    private static final String ERROR_DATE = "mra.defaults.irregularityReport.msg.err.fromdateearliertodate";
		private static final String ERROR_MANDATORY = "mra.defaults.irregularityReport.msg.err.todatemandatory";
		private static final String BASE_CURRENCY = "shared.station.basecurrency";
		
		/**
		 * Execute method
		 * 
		 * @param invocationContext InvocationContext
		 * @throws CommandInvocationException
		 */
		
		public void execute(InvocationContext invocationContext)
				throws CommandInvocationException {
			
			MRAIrregularityForm form =  (MRAIrregularityForm)invocationContext.screenModel;
			MRAIrregularityFilterVO filterVO = new MRAIrregularityFilterVO();

			ApplicationSessionImpl applicationSession = getApplicationSession();
	        LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
	        String companyCode=logonAttributes.getCompanyCode().toUpperCase();
	      
	        filterVO.setCompanyCode(companyCode);
	  	
	    	//MONEY IMPL

			ArrayList<String> stationParameterCodes = new ArrayList<String>();
			HashMap<String, String> stationParameters = new HashMap<String, String>();
			stationParameterCodes.add(BASE_CURRENCY);

			try {
				stationParameters = (HashMap<String, String>) (new AreaDelegate()
						.findStationParametersByCode(logonAttributes
								.getCompanyCode(),
								logonAttributes.getStationCode(),
								stationParameterCodes));
				filterVO.setBaseCurrency(stationParameters
						.get(BASE_CURRENCY));

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			

			String baseCurrency = stationParameters.get(BASE_CURRENCY);
			
			if(form.getDsn()!=null
					&& form.getDsn().trim().length()>0){
				filterVO.setDsn(filterVO.getDsn());
			}else{
				filterVO.setDsn("");
			}
			
			if(form.getOrigin()!=null
					&& form.getOrigin().trim().length()>0){
				filterVO.setOrigin(form.getOrigin());
			}else{
				filterVO.setOrigin("");
			}
			
			if(form.getDestination()!=null
					&& form.getDestination().trim().length()>0){
				filterVO.setDestination(form.getDestination());
			}else{
				filterVO.setDestination("");
			}
			
			if(form.getOffloadStation()!=null
					&& form.getOffloadStation().trim().length()>0){
				filterVO.setOffloadStation(form.getOffloadStation());
			}else{
				filterVO.setOffloadStation("");
			}
			
			if(form.getIrpStatus()!=null
					&& form.getIrpStatus().trim().length()>0){
				filterVO.setIrpStatus(form.getIrpStatus());
			}else{
				filterVO.setIrpStatus("");
			}
			
			if(form.getFromDate() != null && form.getFromDate().trim().length() > 0){
	    		if(DateUtilities.isValidDate(form.getFromDate(),"dd-MMM-yyyy")) {
						LocalDate fromDate = new LocalDate(NO_STATION,NONE,false);
						fromDate.setDate(form.getFromDate());
						filterVO.setFromDate(fromDate);
	    		}
	    	}
			else {
				filterVO.setFromDate(null);
			}
			
			if(form.getToDate() != null && form.getToDate().trim().length() > 0){
	    		if(DateUtilities.isValidDate(form.getToDate(),"dd-MMM-yyyy")) {
						LocalDate toDate = new LocalDate(NO_STATION,NONE,false);
					    toDate.setDate(form.getToDate());
					    filterVO.setToDate(toDate);
	    		}
	    	}

			else {
				filterVO.setToDate(null);
			}
			
			if(form.getEffectiveDate() != null && form.getEffectiveDate().trim().length() > 0){
	    		if(DateUtilities.isValidDate(form.getEffectiveDate(),"dd-MMM-yyyy")) {
						LocalDate effectiveDate = new LocalDate(NO_STATION,NONE,false);
						effectiveDate.setDate(form.getEffectiveDate());
					    filterVO.setEffectiveDate(effectiveDate);
	    		}
	    	}

			else {
				filterVO.setEffectiveDate(null);
			}
			
			
			if ((filterVO.getFromDate() != null) && (!("").equals(filterVO.getFromDate().toString()))&&
					filterVO.getToDate() != null && ((!("").equals(filterVO.getToDate().toString())))) {
				if (filterVO.getFromDate().isGreaterThan(filterVO.getToDate())) {
					ErrorVO errorVO = new ErrorVO(ERROR_DATE);
					errorVO.setErrorDisplayType(ERROR);
					errors.add(errorVO);
				}
			}
			
			
			String toDate = form.getToDate();
			
			if (toDate == null &&  toDate.trim().length() == 0) {
				log.log(Log.FINE, "!!!inside todate == null");
				ErrorVO errorVO = new ErrorVO(ERROR_MANDATORY);
				errorVO.setErrorDisplayType(ERROR);
				errors.add(errorVO);
			}
			
	    	
	    	
	    	if(getErrors() != null && getErrors().size() > 0){
				
				log.log(Log.FINE, "Errors are returned............",
						getErrors().size());
				invocationContext.addAllError(getErrors());
				invocationContext.target = PRINT_FAILURE;
				log.log(Log.FINE,"Errors are returned............sw");
				return;
			}		


			log.log(Log.INFO, " FilterVO for report fetch ------> ", filterVO);
			getReportSpec().setReportId(REPORT_ID);
			getReportSpec().setProductCode("mailtracking");
			getReportSpec().setSubProductCode("mra");
			getReportSpec().setPreview(true);
			getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
			getReportSpec().addFilterValue(filterVO);
			getReportSpec().setResourceBundle(RESOURCE_BUNDLE_KEY);
			getReportSpec().setAction(ACTION);
			generateReport();
			log.exiting("MRA_DEFAULTS","PrintCommand exit");
			invocationContext.target = getTargetPage();

			
			
		}

	}

