/* ListSCMULDCommand.java Created on Aug 01,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.scmulderrorlog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMULDErrorLogForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1496
 * 
 */
public class ListSCMULDCommand extends BaseCommand {
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of UCM Error logs
	 */
	private static final String SCREENID = "uld.defaults.scmulderrorlog";

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";
	
	private static final String ERROR_DESC = "uld.defaults.scmulderror"; 
	
	private static final String FROM_SCM_RECONCILE = "fromScmReconcile";

	private Log log = LogFactory.getLogger("SCM_ULD_RECONCILE");

	private static final String LIST = "LIST";
	private static final String NAVIGATION = "NAVIGATION";
	/**
	 * execute method
	 * 
	 * @param invocationContext 
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String compCode = logonAttributes.getCompanyCode();

		SCMULDErrorLogForm scmUldReconcileForm = (SCMULDErrorLogForm) invocationContext.screenModel;
		SCMULDErrorLogSession scmUldSession = (SCMULDErrorLogSession) getScreenSession(
				MODULE, SCREENID);
		SCMMessageFilterVO filterVO = new SCMMessageFilterVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		scmUldReconcileForm.setListStatus("");
		if (scmUldSession.getPageUrl() == null
				|| scmUldSession.getPageUrl().trim().length() == 0) {
			errors = validateForm(scmUldReconcileForm, compCode, filterVO);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;
			}
		} else if (scmUldSession.getPageUrl() != null
				&& "fromScmReconcile".equals(scmUldSession.getPageUrl())) {
			scmUldSession.setParentPageUrl("fromScmReconcile");
			scmUldReconcileForm.setPageUrl("fromScmReconcile");
			//Added by A-2408 for bufix on 18Jul08 starts
			Collection<OneTimeVO> errorDescriptions = getOneTimeVOs(logonAttributes.getCompanyCode());
			scmUldSession.setErrorDescriptions(errorDescriptions);
			//ends
		}
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		log.log(Log.FINE, "\n\n\nPage URL----------------->", scmUldSession.getPageUrl());
		if ((scmUldSession.getPageUrl() != null && "frommaintainuld"
				.equals(scmUldSession.getPageUrl()))
				|| (scmUldSession.getPageUrl() != null && "fromMaintainDiscrepancy"
						.equals(scmUldSession.getPageUrl()))
				|| (scmUldSession.getPageUrl() != null && "fromScmReconcile"
						.equals(scmUldSession.getPageUrl()))
				|| "fromrecorduld".equals(scmUldSession.getPageUrl())
				|| "fromreturnuld".equals(scmUldSession.getPageUrl())
				|| "fromloanborrowuld".equals(scmUldSession.getPageUrl())) {
			filterVO = scmUldSession.getSCMULDFilterVO();
			Collection<String> uldNumbers = filterVO.getUldNumbers();
			if(uldNumbers != null && uldNumbers.size() > 0){
				String uldNumber = ((ArrayList<String>)uldNumbers).get(0);
				log.log(Log.FINE, "ULD Number-------------->", uldNumber);
				scmUldReconcileForm.setUldNumber(uldNumber);
			}
		} else {
			populateFilterVO(scmUldReconcileForm, filterVO, compCode);
			scmUldSession.setSCMULDFilterVO(filterVO);
		}
		if( scmUldReconcileForm.getErrorDescription()!=null){
			filterVO.setErrorCode(scmUldReconcileForm.getErrorDescription());
		}
		//Added by Sreekumar S
		HashMap<String, String> indexMap = null;
		HashMap<String, String> finalMap = null;
		if (scmUldSession.getIndexMap() != null) {
			indexMap = scmUldSession.getIndexMap();
		}

		if (indexMap == null) {
			log.log(Log.INFO, "INDEX MAP IS NULL");
			indexMap = new HashMap<String, String>();
			indexMap.put("1", "1");
		}
		int nAbsoluteIndex = 0;
		String displayPage = scmUldReconcileForm.getDisplayPage();
		String strAbsoluteIndex = indexMap.get(displayPage);
		if (strAbsoluteIndex != null) {
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		filterVO.setAbsoluteIndex(nAbsoluteIndex);
		filterVO.setPageNumber(Integer.parseInt(scmUldReconcileForm.getDisplayPage()));
		//Added by Sreekumar S ends.
		
		Page<ULDSCMReconcileDetailsVO> scmUlddetailsVOs = null;
		log.log(Log.FINE, "FilterVO to server------------------>", filterVO);
		Collection<ErrorVO> err = new ArrayList<ErrorVO>();
		try {
			if((LIST.equalsIgnoreCase(scmUldReconcileForm.getNavigationMode()))||(FROM_SCM_RECONCILE.equals(scmUldSession.getPageUrl()))){
				filterVO.setTotalRecordsCount(-1);
			}else if(NAVIGATION.equalsIgnoreCase(scmUldReconcileForm.getNavigationMode())){   
				filterVO.setTotalRecordsCount(scmUldSession.getTotalRecordsCount());
				filterVO.setPageNumber(Integer.parseInt(scmUldReconcileForm.getDisplayPage()));
			}
			scmUlddetailsVOs = delegate.listULDErrorsInSCM(filterVO);
			if (scmUlddetailsVOs != null) {
				scmUldSession.setTotalRecordsCount((scmUlddetailsVOs.getTotalRecordCount()));
				log.log(Log.INFO, "rateLineVosAbsoluteIndex============",
				scmUlddetailsVOs.getAbsoluteIndex());
			}
		} catch (BusinessDelegateException exception) {
			exception.getMessage();
			err = handleDelegateException(exception);
		}
		
		//Added by Sreekumar S
		finalMap = indexMap;
		if (scmUldSession.getSCMReconcileDetailVOs() != null) {
			finalMap = buildIndexMap(indexMap, scmUldSession.getSCMReconcileDetailVOs());
		}
		//Added by Sreekumar S ends
		if (scmUlddetailsVOs != null && scmUlddetailsVOs.size() > 0) {
			
			//Added by Sreekumar S
			scmUldSession.setIndexMap(finalMap);
			
			log
					.log(Log.INFO,
							"GOING TO PUT FINAL MAP IN SESSION---------*****",
							finalMap);
			// Server ends
			scmUldSession.setSCMReconcileDetailVOs(scmUlddetailsVOs);
			log.log(Log.FINE, "scmUldSession page url------------------>",
					scmUldSession.getPageUrl());
			/*
			 * Added by Ashna Anil for ICRD-193723
			 * Clearing the pageUrl value so that while relisting, form values will be picked instead of session filterVo values.
			 */
			scmUldSession.setPageUrl(null);
			scmUldReconcileForm.setListStatus("N");
			invocationContext.target = LIST_SUCCESS;
			return;
		} else {
			scmUldSession.setSCMReconcileDetailVOs(null);
			scmUldSession.setPageUrl(FROM_SCM_RECONCILE); //modified by A-5173 for ICRD-25668
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.messaging.scmreconcile.msg.err.norecords");
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_SUCCESS;
			return;

		}

	}

	/**
	 * 
	 * @param form
	 * @param companyCode
	 * @param filterVO
	 * @return
	 */
	
	private Collection<ErrorVO> validateForm(SCMULDErrorLogForm form,
			String companyCode, SCMMessageFilterVO filterVO) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;

		if (form.getAirline() == null || form.getAirline().trim().length() == 0) {
			error = new ErrorVO(
					"uld.defaults.messaging.scmreconcile.msg.err.enterairline");
			errors.add(error);
		} else if (validateAirlineCodes(form, companyCode, filterVO) != null) {
			error = new ErrorVO(
					"uld.defaults.messaging.scmreconcile.msg.err.invalidairline");
			errors.add(error);
		}
		if (form.getScmSeqNo() == null
				|| form.getScmSeqNo().trim().length() == 0) {
			error = new ErrorVO(
					"uld.defaults.messaging.scmreconcile.msg.err.enterscmseqno");
			errors.add(error);

		}

		if (form.getScmUldAirport() == null
				|| form.getScmUldAirport().trim().length() == 0) {
			error = new ErrorVO(
					"uld.defaults.messaging.scmreconcile.msg.err.enterairport");
			errors.add(error);

		} else if (validateAirportCodes(form.getScmUldAirport().toUpperCase(),
				getApplicationSession().getLogonVO().getCompanyCode()) != null) {
			error = new ErrorVO(
					"uld.defaults.messaging.scmreconcile.msg.err.invalidairport");
			errors.add(error);

		}

		
		return errors;
	}

	/**
	 * 
	 * @param form
	 * @param filterVO
	 * @param compCode
	 * @return
	 */
	private SCMMessageFilterVO populateFilterVO(SCMULDErrorLogForm form,
			SCMMessageFilterVO filterVO, String compCode) {
		filterVO.setCompanyCode(compCode);
		filterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));
		filterVO.setAirportCode(form.getScmUldAirport().toUpperCase());

		LocalDate stockChkDate = new LocalDate(filterVO.getAirportCode(),
				Location.ARP, true);
		
		String stockCheckDate = form.getStockCheckdate();
		
		if(stockCheckDate != null && stockCheckDate.trim().length() > 0){
			StringBuilder dateAndTime = new StringBuilder(stockCheckDate);
			String stockCheckTime = form.getScmStockCheckTime();
			log.log(Log.FINE, "Stock Check Time------------------->",
					stockCheckTime);
			if(stockCheckTime != null && stockCheckTime.trim().length() > 0){
				if (!stockCheckTime.contains(":")) {
					stockCheckTime = stockCheckTime.concat(":00");
				}
				dateAndTime.append(" ").append(stockCheckTime).append(":00");
			}
			log.log(Log.FINE, "Stock Check Time------------------->",
					stockCheckTime);
			stockChkDate.setDateAndTime(dateAndTime.toString());
			
			log.log(Log.FINE, "\n\n\nDate and time -------------------->",
					dateAndTime.toString());
			filterVO.setStockControlDate(stockChkDate);
		}
		// Commented by Preet on 21 Apr for ULD 351
		
		/*if(form.getErrorDescription().equals("M")){
			filterVO.setErrorCode("ERR1");
		}
		if(form.getErrorDescription().equals("F")){
			filterVO.setErrorCode("ERR2");		
		}
		if(form.getErrorDescription().equals("NS")){
			filterVO.setErrorCode("ERR3");
		}
		if(form.getErrorDescription().equals("NA")){
			filterVO.setErrorCode("ERR4");
		}*/
		// Added by Preet for ULD 351 on 21 Apr--starts
		filterVO.setErrorCode(form.getErrorDescription());
		// Preet -- ends
		filterVO.setSequenceNumber(form.getScmSeqNo());
		filterVO.setAirlineCode(form.getAirline().toUpperCase());
		Collection<String> uldNumbers = new ArrayList<String>();
		if (form.getUldNumber() != null
				&& form.getUldNumber().trim().length() > 0) {
			uldNumbers.add(form.getUldNumber().toUpperCase());
			filterVO.setUldNumbers(uldNumbers);
		}

		return filterVO;
	}

	/**
	 * 
	 * @param station
	 * @param companyCode
	 * @return
	 */
	private Collection<ErrorVO> validateAirportCodes(String station,
			String companyCode) {
		Collection<ErrorVO> errors = null;
		try {
			AreaDelegate delegate = new AreaDelegate();
			delegate.validateAirportCode(companyCode, station);

		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors = handleDelegateException(e);
		}
		return errors;
	}

	/**
	 * 
	 * @param form
	 * @param companyCode
	 * @param filterVO
	 * @return
	 */
	
	public Collection<ErrorVO> validateAirlineCodes(SCMULDErrorLogForm form,
			String companyCode, SCMMessageFilterVO filterVO) {
		Collection<ErrorVO> errors = null;
		// validate carriercode
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		if (form.getAirline() != null && form.getAirline().trim().length() > 0) {
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						companyCode, form.getAirline().toUpperCase());

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (airlineValidationVO != null) {
				filterVO.setFlightCarrierIdentifier(airlineValidationVO
						.getAirlineIdentifier());
			}
		}
		return errors;
	}
	
	/**
	 * @author A-2667
	 * @param existingMap
	 * @param page
	 * @return
	 */
		private HashMap<String, String> buildIndexMap(HashMap<String,
				String> existingMap, Page page) {

			HashMap<String, String> finalMap = existingMap;
			String indexPage = String.valueOf((page.getPageNumber() + 1));
			boolean isPageExits = false;
			Set<Map.Entry<String, String>> set = existingMap.entrySet();
			for (Map.Entry<String, String> entry : set) {
				String pageNum = entry.getKey();
				if (pageNum.equals(indexPage)) {
					isPageExits = true;
				}
			}
			if (!isPageExits) {
				finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
			}
			return finalMap;
		}
		/**
		 * @param companyCode
		 * @return
		 */
		public Collection<OneTimeVO> getOneTimeVOs(String companyCode){
			ArrayList<String> parameterList = new ArrayList<String>();
			Map<String,Collection<OneTimeVO>> hashMap = new HashMap<String,Collection<OneTimeVO>>();
			parameterList.add(ERROR_DESC);
			SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
			try {
				hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode, parameterList);
			} catch (BusinessDelegateException e) {
				// To be reviewed Auto-generated catch block
				e.getMessage();
			}
			return hashMap.get(ERROR_DESC);
		}

}
