/* ListSCMReconcileCommand.java Created on Aug 01,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.scmreconcile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMReconcileSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMReconcileForm;
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
public class ListSCMReconcileCommand extends BaseCommand {

	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of UCM Error logs
	 */
	private static final String SCREENID = "uld.defaults.scmreconcile";

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";
	
	private Log log = LogFactory.getLogger("SCM Reconcile");

    private static final String LIST = "LIST";
	private static final String NAVIGATION = "NAVIGATION";
	/**
	 * execute method
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

		SCMReconcileForm scmReconcileForm = (SCMReconcileForm) invocationContext.screenModel;
		SCMReconcileSession scmReconcileSession = (SCMReconcileSession) getScreenSession(
				MODULE, SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		SCMMessageFilterVO filterVO = new SCMMessageFilterVO();
		scmReconcileForm.setListStatus("");
		if (scmReconcileSession.getPageUrl() == null
				|| !"fromScmUldErrorLog".equals(scmReconcileSession
						.getPageUrl())) {
			errors = validateForm(scmReconcileForm, compCode, filterVO);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;
			}
		}
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();

		HashMap<String, String> indexMap = null;
		HashMap<String, String> finalMap = null;
		log.log(Log.FINE, "Page URl--------------------->", scmReconcileSession.getPageUrl());
		if (scmReconcileSession.getPageUrl() != null
				&& "fromScmUldErrorLog"
						.equals(scmReconcileSession.getPageUrl())) {
			filterVO = scmReconcileSession.getMessageFilterVO();
			log.log(Log.FINE, "\n\n\ntaking filterVO from session");
			scmReconcileForm.setDisplayPage(scmReconcileSession.getDisplayPageNumber());

		}

		if ("Y".equals(scmReconcileForm.getListflag())) {
			scmReconcileSession.setIndexMap(null);
		}		
		
			indexMap = getIndexMap(scmReconcileSession.getIndexMap(), invocationContext); //added by A-5203
		if(indexMap == null || indexMap.size()<=0) {
			scmReconcileForm.setListflag("");
			indexMap = new HashMap<String, String>();
			indexMap.put("1", "1");
		}
		// A-5125 
		if(!"fromScmUldErrorLog".equals(scmReconcileSession
				.getPageUrl()))
		{
			scmReconcileSession.setDisplayPageNumber(scmReconcileForm.getDisplayPage());
		}
		
		int nAbsoluteIndex = 0;
		 String strAbsoluteIndex =null;
		 strAbsoluteIndex = (String) indexMap.get(scmReconcileForm
				 .getDisplayPage());

		scmReconcileForm.setAbsoluteIndex(strAbsoluteIndex);

		if (strAbsoluteIndex != null) {
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
			filterVO.setAbsoluteIndex(nAbsoluteIndex);
		}
		if (!"fromScmUldErrorLog".equals(scmReconcileSession.getPageUrl())) {
			populateScmFilterVO(filterVO, compCode, scmReconcileForm);
			 //scmReconcileSession.setPageUrl("");
		}
		scmReconcileSession.setPageUrl("fromListSCMReconcile");
		log.log(Log.FINE, "\n\n\n **filterVO**", filterVO);
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		Page<ULDSCMReconcileVO> scmdetailsVos = null;
		try {
			if(LIST.equalsIgnoreCase(scmReconcileForm.getNavigationMode())){
				filterVO.setTotalRecordsCount(-1);
			}else if(NAVIGATION.equalsIgnoreCase(scmReconcileForm.getNavigationMode())){   
				filterVO.setTotalRecordsCount(scmReconcileSession.getTotalRecordsCount());
				filterVO.setPageNumber(Integer.parseInt(scmReconcileForm.getDisplayPage()));
			}
			scmdetailsVos = delegate.listSCMMessage(filterVO);
			if (scmdetailsVos != null) {
				scmReconcileSession.setTotalRecordsCount((scmdetailsVos.getTotalRecordCount()));
				log.log(Log.INFO, "rateLineVosAbsoluteIndex============",
						scmdetailsVos.getAbsoluteIndex());
			}
		} catch (BusinessDelegateException exception) {
			exception.getMessage();
			error = handleDelegateException(exception);
		}
		//Added for Bug ID:106788 by Vivek.Perla on 22Feb2011
		ArrayList<ULDSCMReconcileVO> sortedScmdetailsVos =null;		
		if (scmdetailsVos != null && scmdetailsVos.size() > 0) {
//			Added for Bug ID:106788 by Vivek.Perla on 22Feb2011 starts
			
			
			 /*sortedScmdetailsVos =new ArrayList<ULDSCMReconcileVO>(scmdetailsVos);
			 Collections.sort(sortedScmdetailsVos,new ULDSCMReconcileVOComparator());
			 scmdetailsVos = new Page<ULDSCMReconcileVO>(sortedScmdetailsVos,0,0,0,0,0,false);*/
			////Added for Bug ID:106788 by Vivek.Perla on 22Feb2011 ends
			
			
			scmReconcileSession.setSCMReconcileVOs(scmdetailsVos);
			finalMap = indexMap;
			if (scmReconcileSession.getSCMReconcileVOs() != null) {
				finalMap = buildIndexMap(indexMap, scmReconcileSession.getSCMReconcileVOs());
				//scmReconcileSession.setIndexMap(finalMap);
				
				scmReconcileSession.setMessageFilterVO(filterVO);
			}
			
			scmReconcileForm.setListStatus("N");
			invocationContext.target = LIST_SUCCESS;
			
		} else {
			
			scmReconcileSession.setSCMReconcileVOs(null);
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.messaging.scmreconcile.msg.err.norecords");
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_SUCCESS;
			
			

		}
		scmReconcileSession.setIndexMap((HashMap<String,String>)super.setIndexMap(finalMap, invocationContext)); //added by A-5203
	}
	//Added for Bug ID:106788 by Vivek.Perla on 22Feb2011 Starts
	   /*
     * ULDSCMReconcileVOComparator.java Created on 22Feb,2011
     *
     * Copyright 2011 IBS Software Services (P) Ltd. All Rights Reserved.
     *
     * This software is the proprietary information of IBS Software Services (P) Ltd.
     * Use is subject to license terms.
     */
    /**
     * ULDSCMReconcileVOComparator.java
     * @author T-1826
     *
     */
		
	private static class ULDSCMReconcileVOComparator
						implements Comparator<ULDSCMReconcileVO> {
	/**
	 * Method to compare the routingSequenceNumber
	 * @param firstULDSCMReconcileVO
	 * @param secondULDSCMReconcileVO
	 * @return int
	 */
		
	public int compare(ULDSCMReconcileVO firstULDSCMReconcileVO,
			ULDSCMReconcileVO secondULDSCMReconcileVO) {
			

			return Integer.parseInt(firstULDSCMReconcileVO.getSequenceNumber()) 
			> Integer.parseInt(secondULDSCMReconcileVO.getSequenceNumber()) ? 1 : 0;
		}
	}
	
//	Added for Bug ID:106788 by Vivek.Perla on 22Feb2011 Ends

	/**
	 * 
	 * @param filterVO
	 * @param compCode
	 * @param form
	 */
	private SCMMessageFilterVO populateScmFilterVO(SCMMessageFilterVO filterVO,
			String compCode, SCMReconcileForm form) {
		filterVO.setCompanyCode(compCode);
		filterVO.setAirportCode(form.getAirport().toUpperCase());
		filterVO.setAirlineCode(form.getAirline().toUpperCase());
		filterVO.setSequenceNumber(form.getSeqNo());
		if(form.getStockChkdate()!=null && form.getStockChkdate().trim().length()>0 &&
				form.getScmStockCheckTime()!=null && form.getScmStockCheckTime().trim().length()>0	){
		LocalDate stookChkDate = new LocalDate(filterVO.getAirportCode(),
				Location.ARP, true);
		String stockCheckDate = form.getStockChkdate();
		StringBuilder dateAndTime = new StringBuilder(stockCheckDate);
		String stockCheckTime = form.getScmStockCheckTime();
		
		if (stockCheckTime.indexOf(":") == -1) {
			stockCheckTime=stockCheckTime.concat(":00");
		}
		
		dateAndTime.append(" ").append(stockCheckTime).append(":00");
		log.log(Log.FINE, " Date and time-------------------->", dateAndTime.toString());
		stookChkDate.setDateAndTime(dateAndTime.toString());
		filterVO.setStockControlDate(stookChkDate);
		}
		filterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));
		return filterVO;

	}

	/**
	 * 
	 * @param indexMap
	 * @param scmreconcilePage
	 * @return
	 */
	private HashMap buildIndexMap(HashMap indexMap,
			Page<ULDSCMReconcileVO> scmreconcilePage) {
		HashMap existingMap = indexMap;
		String indexPage = String
				.valueOf((scmreconcilePage.getPageNumber() + 1));
		boolean isPageExits = false;
		Set<Map.Entry<String, String>> set = indexMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExits = true;
			}
		}
		if (!isPageExits) {
			existingMap.put(indexPage, String.valueOf(scmreconcilePage
					.getAbsoluteIndex()));
		}

		return existingMap;
	}

	/**
	 * @param form
	 * @param companyCode
	 * @param filterVO
	 * @return
	 */
	private Collection<ErrorVO> validateForm(SCMReconcileForm form,
			String companyCode, SCMMessageFilterVO filterVO) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if (form.getAirport() == null || form.getAirport().trim().length() == 0) {
			error = new ErrorVO(
					"uld.defaults.messaging.scmreconcile.msg.err.enterairport");
			errors.add(error);

		} else if (validateAirportCodes(form.getAirport().toUpperCase(),
				getApplicationSession().getLogonVO().getCompanyCode()) != null) {
			error = new ErrorVO(
					"uld.defaults.messaging.scmreconcile.msg.err.invalidairport");
			errors.add(error);
		}

		if (form.getAirline() != null && form.getAirline().trim().length() == 0) {
			error = new ErrorVO(
					"uld.defaults.messaging.scmreconcile.msg.err.enterairline");
			errors.add(error);

		} else if (validateAirlineCodes(form, companyCode, filterVO) != null) {
			error = new ErrorVO(
					"uld.defaults.messaging.scmreconcile.msg.err.invalidairline");
			errors.add(error);

		}

		
		return errors;
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
	 * @param form
	 * @param companyCode
	 * @param filterVO
	 * @return
	 */
	
	public Collection<ErrorVO> validateAirlineCodes(SCMReconcileForm form,
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

}
