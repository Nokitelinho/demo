/*
 * ListMonitorULDStockCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.monitoruldstock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockListVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MonitorULDStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MonitorULDStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.util.Map;
import java.util.Set;
/**
 * @author A-1496
 *
 */
public class ListMonitorULDStockCommand  extends BaseCommand {
	private Log log = LogFactory.getLogger("MonitorULD Stock");

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.monitoruldstock";
	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_failure";
	private static final String LIST_STATUS = "fromAnotherScreen";
	private static final String BLANK="";
	private static final String LIST = "LIST";
	private static final String NAVIGATION = "NAVIGATION";
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MonitorULDStockSession monitorULDStockSession = (MonitorULDStockSession)getScreenSession(MODULE,SCREENID);

		MonitorULDStockForm monitorULDStockForm = (MonitorULDStockForm) invocationContext.screenModel;

		ULDStockConfigFilterVO uldStockConfigFilterVO = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(!LIST_STATUS.equals(monitorULDStockSession.getListStatus())) {
			uldStockConfigFilterVO = new ULDStockConfigFilterVO();
			errors = loadStockFilterFromForm(monitorULDStockForm,uldStockConfigFilterVO);
			monitorULDStockSession.setULDStockConfigFilterVO(uldStockConfigFilterVO);

			if(errors != null &&
					errors.size() > 0 ) {
					invocationContext.addAllError(errors);
					invocationContext.target = LIST_FAILURE;
					return;
			}
		}
		else {
			log.log(Log.FINE, " \n 2222#$#$##$..$#", monitorULDStockSession.getULDStockConfigFilterVO());
			//Added by Tarun INT_ULD42 on  28-12-2007
			uldStockConfigFilterVO = monitorULDStockSession.getULDStockConfigFilterVO();
			if(uldStockConfigFilterVO!=null){//added by a-5505 for the bug ICRD-101657
				if(monitorULDStockForm!=null){
					monitorULDStockForm.setLevelType(uldStockConfigFilterVO.getLevelType()); 
					monitorULDStockForm.setLevelValue(uldStockConfigFilterVO.getLevelValue());
				}
			}
			monitorULDStockSession.setListStatus("");
			log.log(Log.FINE, "\n  eeeeee2", uldStockConfigFilterVO);
				//if(uldStockConfigFilterVO.getViewByNatureFlag()){
				monitorULDStockForm.setViewByNatureFlag(true);
			//}
			//Added By Deepthi INT_ULD506
			//Commented By manaf for BUG 13993 starts
			/*
			if(logonAttributes.isAirlineUser()){
	    		uldStockConfigFilterVO.setAirlineCode(logonAttributes.getOwnAirlineCode());
	    		uldStockConfigFilterVO.setStationCode(logonAttributes.getAirportCode());
	    		monitorULDStockForm.setStockDisableStatus("airline");
	    	}
	    	else{
	    		uldStockConfigFilterVO.setStationCode(logonAttributes.getAirportCode());
	    		monitorULDStockForm.setStockDisableStatus("GHA");
	    	}
	    	*/
			//Commented By manaf for BUG 13993 ends
		}

		// Added by Ramachandran S for handling PageAwareMultiMapper
		// implementation
		// Modified by A-5266 for ICRD-20902 starts
		// HashMap<String, String> indexMap = null;
		log.log(Log.INFO, "Getting the index map - ListMonitorULDStockCommand");
		HashMap<String, String> indexMap = getIndexMap(
				monitorULDStockSession.getIndexMap(), invocationContext);
		/*
		 * if (monitorULDStockSession.getIndexMap() != null) { indexMap =
		 * monitorULDStockSession.getIndexMap(); }
		 */
		// Modified by A-5266 for ICRD-20902 ends
		HashMap<String, String> finalMap = null;
		if (indexMap == null || (indexMap.keySet() != null && indexMap.keySet().isEmpty())) {

			log.log(Log.INFO, "INDEX MAP IS NULL");
			indexMap = new HashMap<String, String>();
			indexMap.put("1", "1");
		}
		int nAbsoluteIndex = 1;
		String displayPage = monitorULDStockForm.getDisplayPage();
		String strAbsoluteIndex = indexMap.get(displayPage);
		if (strAbsoluteIndex != null) {
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}

		//Added by Tarun INT_ULD42 on  28-12-2007 ends

		uldStockConfigFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		uldStockConfigFilterVO.setAbsoluteIndex(nAbsoluteIndex);
		if(monitorULDStockForm.getStationCode() != null 
					&& monitorULDStockForm.getStationCode().trim().length() > 0){
		uldStockConfigFilterVO.setStationCode(monitorULDStockForm.getStationCode());
		}
		if(LIST.equalsIgnoreCase(monitorULDStockForm.getNavigationMode())){//a-5505 for bug ICRD-123103 
			uldStockConfigFilterVO.setTotalRecordsCount(-1);
		}else if(NAVIGATION.equalsIgnoreCase(monitorULDStockForm.getNavigationMode())){
			uldStockConfigFilterVO.setTotalRecordsCount(monitorULDStockSession.getTotalRecordsCount());
			uldStockConfigFilterVO.setPageNumber(Integer.parseInt(monitorULDStockForm.getDisplayPage()));
		}
		uldStockConfigFilterVO.setPageNumber(Integer.parseInt(monitorULDStockForm.getDisplayPage())); 
		Page<ULDStockListVO> uldStockListVOs = new Page<ULDStockListVO>(
				new ArrayList<ULDStockListVO>(), 0, 0, 0, 0, 0, false);

		// Added by Preet for ULD 343 on 18th April --starts
		// Validate Agent Code
		AgentDelegate agentDelegate = new AgentDelegate();
		AgentVO agentVO = null;
		String agentCode=uldStockConfigFilterVO.getAgentCode();
		if (agentCode != null && !(BLANK.equals(agentCode))) {
			Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			try {
				agentVO = agentDelegate.findAgentDetails(
						logonAttributes.getCompanyCode(),
						agentCode.toUpperCase());
			} catch (BusinessDelegateException exception) {
				log.log(Log.FINE, "*****in the exception");				
			}
			if (agentVO == null) {
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.moniotruldstock.invalidagentcode");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target=LIST_FAILURE;
				return;
			}
		}
		// Added by Preet for QF1012 starts
		//uldStockConfigFilterVO.setAbsoluteIndex(1);

		// Added by Preet for ULD 343 on 18th April --ends

		try {
			uldStockListVOs = new ULDDefaultsDelegate().findULDStockList(
				uldStockConfigFilterVO,Integer.parseInt(monitorULDStockForm.getDisplayPage())) ;
		}
		catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}

		if(errors != null &&
				errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;
		}
		if(uldStockListVOs != null && uldStockListVOs.size()> 0) {
			//if(uldStockConfigFilterVO.getViewByNatureFlag()==true){
				monitorULDStockForm.setViewByNatureFlag(true);
			//}
			monitorULDStockSession.setULDStockListVO(uldStockListVOs);
			monitorULDStockSession.setTotalRecordsCount(uldStockListVOs.getTotalRecordCount());//a-5505 for bug ICRD-123103 
			log
					.log(
							Log.FINE,
							"uldStockListVOs===========================================================================>",
							uldStockListVOs);
		}
		else {
			monitorULDStockSession.setULDStockListVO(null);
			ErrorVO error = new ErrorVO("uld.defaults.monitorstock.norecordsfound");
	     	error.setErrorDisplayType(ErrorDisplayType.ERROR);
	     	errors = new ArrayList<ErrorVO>();
	     	errors.add(error);
	     	invocationContext.addAllError(errors);
		}
//		Added by Tarun INT_ULD42 on  28-12-2007
		finalMap = indexMap;
		if (monitorULDStockSession.getULDStockListVO() != null) {
			finalMap = buildIndexMap(indexMap, monitorULDStockSession.getULDStockListVO());
		}
		// Modified by A-5266 for ICRD-20902 starts
		log.log(Log.INFO, "Setting the index map - ListMonitorULDStockCommand");
		monitorULDStockSession.setIndexMap((HashMap<String, String>) super
				.setIndexMap(finalMap, invocationContext));
		// Modified by A-5266 for ICRD-20902 ends
		// monitorULDStockSession.setIndexMap(finalMap);
		// Added by Tarun INT_ULD42 on 28-12-2007 ends
		monitorULDStockForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);

		invocationContext.target = LIST_SUCCESS;

    }

    /*
     * Modified as part of ICRD-334152
     */
    private Collection<ErrorVO> loadStockFilterFromForm(MonitorULDStockForm monitorULDStockForm,
    		ULDStockConfigFilterVO uldStockConfigFilterVO) {
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	log.log(Log.FINE, "monitorULDStockForm==============>",
				monitorULDStockForm);
    	/*
		 * Modified as part of ICRD-334152
		 */
		if(monitorULDStockForm.getAirlineCode() != null
				&& monitorULDStockForm.getAirlineCode().trim().length() > 0) {
			AirlineValidationVO	airlineVO= validateAirlineCode(logonAttributes.getCompanyCode(),monitorULDStockForm.getAirlineCode(),errors);

		if(airlineVO != null) {
				uldStockConfigFilterVO.setAirlineIdentifier(airlineVO.getAirlineIdentifier());
			}
			uldStockConfigFilterVO.setAirlineCode(monitorULDStockForm.getAirlineCode().toUpperCase());			
		}
		/*
		 * Added as part of ICRD-334152
		 */
		if(monitorULDStockForm.getOwnerAirline() != null
				&& monitorULDStockForm.getOwnerAirline().trim().length() > 0) { 
			if(!monitorULDStockForm.getOwnerAirline().equals(monitorULDStockForm.getAirlineCode())) {
				AirlineValidationVO	ownerAirlineVO = validateAirlineCode(logonAttributes.getCompanyCode(),monitorULDStockForm.getOwnerAirline(),errors);
				if(ownerAirlineVO != null) {
					uldStockConfigFilterVO.setOwnerAirlineIdentifier(ownerAirlineVO.getAirlineIdentifier());
				}
			}else {
				uldStockConfigFilterVO.setOwnerAirlineIdentifier(uldStockConfigFilterVO.getAirlineIdentifier());
			}
			uldStockConfigFilterVO.setOwnerAirline(monitorULDStockForm.getOwnerAirline().toUpperCase());
		}
		/*
		 * Added as part of ICRD-334152 ends
		 */
    	if((monitorULDStockForm.getStationCode() != null &&
    			monitorULDStockForm.getStationCode().trim().length() > 0)) {
    		uldStockConfigFilterVO.setStationCode(monitorULDStockForm.getStationCode().toUpperCase());

    	}
    	if((monitorULDStockForm.getAirlineCode() == null
				|| monitorULDStockForm.getAirlineCode().trim().length()== 0) &&
				(monitorULDStockForm.getStationCode() == null ||
		    			monitorULDStockForm.getStationCode().trim().length() == 0)) {
    		ErrorVO error = new ErrorVO("uld.defaults.eitherairlineorstationmandatory");
    		errors.add(error);
    	}
    	if((monitorULDStockForm.getUldGroupCode() != null &&
				monitorULDStockForm.getUldGroupCode().trim().length() > 0)) {
    		uldStockConfigFilterVO.setUldGroupCode(monitorULDStockForm.getUldGroupCode().toUpperCase());

    	}

    	if((monitorULDStockForm.getUldTypeCode() != null &&
				monitorULDStockForm.getUldTypeCode().trim().length() > 0)) {
    		uldStockConfigFilterVO.setUldTypeCode(monitorULDStockForm.getUldTypeCode().toUpperCase());

    	}
    	// Added by Preet for AirNZ 449 starts
    	if((monitorULDStockForm.getAgentCode() != null &&
				monitorULDStockForm.getAgentCode().trim().length() > 0)) {
    		uldStockConfigFilterVO.setAgentCode(monitorULDStockForm.getAgentCode());
    	}
    	// Added by Preet for AirNZ 449 ends
    	//added by a-3045 for CR QF1012 starts
       	if((monitorULDStockForm.getLevelType() != null &&
				monitorULDStockForm.getLevelType().trim().length() > 0)) {
    		uldStockConfigFilterVO.setLevelType(monitorULDStockForm.getLevelType().toUpperCase());

    	}
       	if((monitorULDStockForm.getLevelValue() != null &&
				monitorULDStockForm.getLevelValue().trim().length() > 0)) {
    		uldStockConfigFilterVO.setLevelValue(monitorULDStockForm.getLevelValue().toUpperCase());

    	}
    	//added by a-3045 for CR QF1012 ends

    	//uldStockConfigFilterVO.setViewByNatureFlag(monitorULDStockForm.getViewByNatureFlag());
    	uldStockConfigFilterVO.setViewByNatureFlag(true);
    	if((monitorULDStockForm.getUldNature() != null &&
				monitorULDStockForm.getUldNature().trim().length() > 0)) {
				if(("ALL").equals(monitorULDStockForm.getUldNature())){
				uldStockConfigFilterVO.setUldNature(null);
				}else{
				uldStockConfigFilterVO.setUldNature(monitorULDStockForm.getUldNature().toUpperCase());
				}

    	}
    	if((monitorULDStockForm.getSort() != null &&
				monitorULDStockForm.getSort().trim().length() > 0)) {
    		uldStockConfigFilterVO.setSort(monitorULDStockForm.getSort());

    	}
    	return errors;



	}
    /**
     * 
     * @param companyCode
     * @param airlineCode
     * @param errors
     * @return
     */
	private AirlineValidationVO validateAirlineCode(String companyCode, String airlineCode,
			Collection<ErrorVO> errors) {
		Collection<ErrorVO> errorsAirline=null;
		AirlineValidationVO	airlineVO=null;
		try {
			airlineVO= new AirlineDelegate().validateAlphaCode(companyCode,
					airlineCode.toUpperCase());
		}
		catch(BusinessDelegateException businessDelegateException) {
			errorsAirline =
				handleDelegateException(businessDelegateException);
			}
		if(errorsAirline != null &&
				errorsAirline.size() > 0) {
			errors.addAll(errorsAirline);
		}
		return airlineVO;
	}

    /**
     * @author A-2883
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

}
