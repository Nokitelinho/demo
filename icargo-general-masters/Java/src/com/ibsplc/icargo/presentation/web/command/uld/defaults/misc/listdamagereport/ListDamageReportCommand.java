/*
 * ListDamageReportCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.listdamagereport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * This command class is used to list the stock details of the specified accesory  
 * @author A-1347
 */
public class ListDamageReportCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("ListDamageReportCommand");
    
	private static final String LIST_SUCCESS = "list_success";
    
	private static final String LIST_FAILURE = "list_failure";

    private static final String SCREENID = "uld.defaults.listdamagereport";
	
	private static final String MODULE_NAME = "uld.defaults";

	private static final String BLANK = "";

	private static final String ALL= "ALL";
	
	private static final String ULD_SECTION = "uld.defaults.section";
	
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
        
    	ApplicationSessionImpl applicationSessionImpl=getApplicationSession(); 
    	LogonAttributes logonAttributes=applicationSessionImpl.getLogonVO();
    	String companyCode=logonAttributes.getCompanyCode();
    	
    	ListDamageReportForm form = (ListDamageReportForm) invocationContext.screenModel; 
    	ListDamageReportSession session = getScreenSession(MODULE_NAME, SCREENID);
    	ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		ULDDamageFilterVO filterVO = new ULDDamageFilterVO();
		Page<ULDDamageDetailsListVO> pg=null;
		ErrorVO error=null;
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		//HashMap indexMap = null;
		HashMap<String, String> indexMap = getIndexMap(session.getIndexMap(), invocationContext); //Added by A-5201 for ICRD-20902
		HashMap finalMap = null;
		if (session.getIndexMap()!=null){
			indexMap = session.getIndexMap();
		}
		if (indexMap == null) {
			log.log(Log.FINE,"INDEX MAP IS NULL");
			indexMap = new HashMap();
			indexMap.put("1", "1");
		}
		int nAbsoluteIndex = 0;
		String toDisplayPage = form.getDisplayPage();		
		String strAbsoluteIndex = (String)indexMap.get(toDisplayPage);
		if(strAbsoluteIndex != null){
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		//added by A-5223 for ICRD-22824 starts
		if(ListDamageReportForm.PAGINATION_MODE_FROM_NAVIGATION_LINK.equals(form.getPaginationMode()) ){
			log.log(Log.FINE, " <: Page navigating after listing :>  ");
			filterVO.setTotalRecords(session.getTotalRecords());

		} else if(ListDamageReportForm.PAGINATION_MODE_FROM_FILTER.equals(form.getPaginationMode())) {//Added for ICRD-180550 by A-5117
			log.log(Log.FINE, " <: LISTING FROM FILTER :> ");
			form.setDisplayPage("1");
			filterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));
			filterVO.setTotalRecords(-1);

		}
		//added by A-5223 for ICRD-22824 ends
		
		if(session.getScreenId()!=null && ("LISTDAMAGE").equals(session.getScreenId()))
		{
		log.log(Log.INFO, "&&&&&&&&&&&&session.getScreenId()", session.getScreenId());
			log.log(Log.INFO, "&&&&&&&&&&&&session.getULDRepairFilterVO()",
					session.getULDDamageFilterVO());
			filterVO=session.getULDDamageFilterVO();
			session.setScreenId("");

		}
		else
		{
		session.setScreenId("");
		String uldNo ="";
		String damageRefNo = "";
		String uldTypeCode = "";
		String uldStatus = "";
		String damageStatus = "";
		String currentStn = "";
		String reportedStn = "";
		String repairedDateFrom = form.getRepairedDateFrom();
		String repairedDateTo = form.getRepairedDateTo();
		
		if(form.getUldNo()!= null && !BLANK.equals(form.getUldNo())){
			uldNo = form.getUldNo().toUpperCase();
		}
		
		if(form.getDamageRefNo()!= null && !BLANK.equals(form.getDamageRefNo())){
			damageRefNo = form.getDamageRefNo().toUpperCase();
		}
		if(form.getUldTypeCode()!= null && !BLANK.equals(form.getUldTypeCode())){
			uldTypeCode = form.getUldTypeCode().toUpperCase();
		}
		if(form.getUldStatus()!=null && !BLANK.equals(form.getUldStatus()) &&
				!ALL.equals(form.getUldStatus())){
			uldStatus = form.getUldStatus();
		}
		
		if(form.getUldDamageStatus()!=null && !BLANK.equals(form.getUldDamageStatus()) &&
				!ALL.equals(form.getUldDamageStatus())){
			damageStatus = form.getUldDamageStatus();
		}
		
		if(form.getCurrentStn()!= null && !BLANK.equals(form.getCurrentStn())){
			currentStn = form.getCurrentStn().toUpperCase();
		}
		
		if(form.getReportedStn()!= null && !BLANK.equals(form.getReportedStn())){
			reportedStn = form.getReportedStn().toUpperCase();
		}
		
		if(form.getPartyType() != null && !BLANK.equals(form.getPartyType())){
			filterVO.setPartyType(form.getPartyType());
		}
		
		if(form.getParty() != null && !BLANK.equals(form.getParty())){
			filterVO.setParty(form.getParty());
		}
		
		if(form.getFacilityType() != null && !BLANK.equals(form.getFacilityType())){
			filterVO.setFacilityType(form.getFacilityType());
		}
		
		if(form.getLocation() != null && !BLANK.equals(form.getLocation())){
			filterVO.setLocation(form.getLocation());
		}
		//filterVO.setAbsoluteIndex(nAbsoluteIndex);
		filterVO.setCompanyCode(companyCode);
		if(repairedDateFrom!=null && repairedDateFrom.trim().length()>0){
			filterVO.setFromDate(repairedDateFrom);
		}
		
		if(repairedDateTo!=null && repairedDateTo.trim().length()>0){
			filterVO.setToDate(repairedDateTo);
		}
		
		filterVO.setUldNumber(uldNo);
		if(!("").equals(damageRefNo)&& damageRefNo.trim().length()>0) {
		filterVO.setDamageReferenceNumber(Long.parseLong(damageRefNo));
		}
		filterVO.setUldTypeCode(uldTypeCode);
		filterVO.setUldStatus(uldStatus);
		filterVO.setDamageStatus(damageStatus);
		filterVO.setCurrentStation(currentStn);
		filterVO.setReportedStation(reportedStn);
		filterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));

		session.setULDDamageFilterVO(filterVO);
		log.log(Log.FINE, "session.setULDDamageFilterVO(filterVO)", session.getULDDamageFilterVO());
		}
		errors = validateForm(form,session,logonAttributes);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			//	 Added by Manaf for BUG11736
			session.setULDDamageRepairDetailsVOs(null);
			invocationContext.target = LIST_FAILURE;
			return;
		}else {
		
		
			Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
			
            //validation of ULD Number by A-2619 begins
			if (form.getUldNo() != null
					&& form.getUldNo().trim().length() > 0) {
				try {
					new ULDDelegate().validateULD(logonAttributes.getCompanyCode(),
							form.getUldNo());
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}

				if (errors != null && errors.size() > 0) {
					invocationContext.addError(new ErrorVO(
							"uld.defaults.invaliduldnumber",
							new Object[] { form.getUldNo() }));
					// Added by Manaf for BUG11736
					session.setULDDamageRepairDetailsVOs(null);
					invocationContext.target = LIST_FAILURE;
					return;
				}
			}

			 //validation of ULD Number by A-2619 ends

			try{
				log.log(Log.FINE,"&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&inside try block");
				log.log(Log.FINE, "filtervo before setting to delegate",
						filterVO);
				pg =delegate.findULDDamageList(filterVO);	
				log.log(Log.FINE, "page getting from delegate", pg);
				
			}catch(BusinessDelegateException businessDelegateException){
				businessDelegateException.getMessage();
				exception = handleDelegateException(businessDelegateException);
			}
			
		}
		//added by a-3045 for bug22708 starts
		if(pg !=null && pg.size()>0){			
			Collection<String> oneTimeToModes = new ArrayList<String>();
			oneTimeToModes.add(ULD_SECTION);			
			SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
			Map<String, Collection<OneTimeVO>> oneTimeToModeMap = null;
			try {
				oneTimeToModeMap = sharedDefaultsDelegate.findOneTimeValues(
						companyCode, oneTimeToModes);				
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				businessDelegateException.getMessage();
			}			
			Collection<OneTimeVO> uldSection = oneTimeToModeMap.get(ULD_SECTION);
			for(ULDDamageDetailsListVO vo : pg){
				for(OneTimeVO oneTimeVO : uldSection){
					if(oneTimeVO.getFieldValue().equals(vo.getSection())){
						vo.setSection(oneTimeVO.getFieldDescription());
					}
				}			
			}
			log.log(Log.FINE,
					"ULDDamageDetailsListVO page pg after converting onetime ",
					pg);
		}
		//added by a-3045 for bug22708 ends
		//added by A-2883 for enabling/disabling buttons
		form.setUldDamageStatus(filterVO.getDamageStatus());
		form.setUldStatus(filterVO.getUldStatus());
		form.setFacilityType(filterVO.getFacilityType());
		form.setPartyType(filterVO.getPartyType());
		
		form.setListStatus("");
		//form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		if(pg !=null && pg.size()>0){				
			session.setULDDamageRepairDetailsVOs(pg);
			log.log(Log.FINE, " the total records in the    list:>", pg.getTotalRecordCount());
			session.setTotalRecords(pg.getTotalRecordCount());
   		//added by A-5223 for ICRD-22824 starts
			form.setListStatus("N");
			}
			else{
				error = new ErrorVO("uld.defaults.nulllist");
				errors.add(error);
				session.removeULDDamageRepairDetailsVOs();
			}
		if (errors != null && errors.size() > 0) {
 			invocationContext.addAllError(errors);
 			invocationContext.target = LIST_FAILURE;
 			return;
			}
		else 
			{
				finalMap = indexMap;
			if(session.getULDDamageRepairDetailsVOs()!= null){
					finalMap = buildIndexMap(indexMap, session.getULDDamageRepairDetailsVOs());
				//session.setIndexMap(finalMap);
				session.setIndexMap((HashMap<String,String>)super.setIndexMap(finalMap, invocationContext)); //Added by A-5201 for ICRD-20902
			}
			//form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			    //Added by A-6770 for  ICRD-147647 starts 
				if(form.getScreenStatusFlag() != null ){
					form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
				}
				//Added by A-6770 for  ICRD-147647 Ends 
			    invocationContext.target =LIST_SUCCESS;				
			}
    }
    

	/**
	 * 
	 * @param form
	 * @param session
	 * @param logonAttributes
	 * @return
	 */
	private Collection<ErrorVO> validateForm(ListDamageReportForm form,
			ListDamageReportSession session,LogonAttributes logonAttributes) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		boolean isValid = true;
		boolean isValidDate = true;
		LocalDate fromLocalDate = null;
		LocalDate toLocalDate= null;

		if(form.getRepairedDateFrom()!=null && form.getRepairedDateFrom().trim().length()>0){
			if(!DateUtilities.isValidDate(form.getRepairedDateFrom(),"dd-MMM-yyyy")){
				isValid = false;
				isValidDate = false;
				error = new ErrorVO("uld.defaults.notvalidrepairedfromdate");
				errors.add(error);

			}
		}

		if(form.getRepairedDateTo()!=null && form.getRepairedDateTo().trim().length()>0){
			if(!DateUtilities.isValidDate(form.getRepairedDateTo(),"dd-MMM-yyyy")){
				isValid = false;
				isValidDate = false;
				error = new ErrorVO("uld.defaults.notvalidrepairedtodate");
				errors.add(error);

			}
		}
		
		if((form.getRepairedDateFrom()!=null && form.getRepairedDateFrom().trim().length()>0) &&
				(form.getRepairedDateTo()!=null && form.getRepairedDateTo().trim().length()>0)&&
				isValidDate){
			LocalDate localStartDate
					= new LocalDate(logonAttributes.getAirportCode(),Location.ARP, false);
			fromLocalDate = localStartDate.setDate(form.getRepairedDateFrom());
			LocalDate localEndDate
					= new LocalDate(logonAttributes.getAirportCode(),Location.ARP, false);
			toLocalDate = localEndDate.setDate(form.getRepairedDateTo());
			 Calendar calTodate = toLocalDate.toCalendar();
			 Calendar calFromdate = fromLocalDate.toCalendar();
		
			if(calTodate.before(calFromdate)){
				isValid = false;
				error = new ErrorVO("uld.defaults.todatebeforefromdate");
				errors.add(error);
			}
		}
		return errors;
	}
	

/**
 * @param existingMap
 * @param page
 * @return HashMap
 */
private HashMap buildIndexMap(HashMap existingMap, Page page) {
	HashMap finalMap = existingMap;
	//String currentPage = String.valueOf(page.getPageNumber());
	//String currentAbsoluteIndex = String.valueOf(page.getAbsoluteIndex());
	String indexPage = String.valueOf((page.getPageNumber()+1));

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
