/*
 * ListRepairReportCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.listrepairreport;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.INFO;

import java.util.ArrayList;
import java.util.Collection; 
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListRepairReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListRepairReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
/**
 * This command class is used to list the stock details of the specified accesory
 * @author A-1347
 */
public class ListRepairReportCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ListDamageReportCommand");

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

    private static final String SCREENID = "uld.defaults.listrepairreport";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String BLANK = "";

	private static final String ALL= "ALL";
	
	private static final String CURRENCY_VALUE="uld.defaults.uldinvoicingcurrency";
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

    	ListRepairReportForm form = (ListRepairReportForm) invocationContext.screenModel;
    	ListRepairReportSession session = getScreenSession(MODULE_NAME, SCREENID);
    	ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		ULDRepairFilterVO filterVO = new ULDRepairFilterVO();
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		

		Page<ULDRepairDetailsListVO> repairDetailVOs=null;
		ErrorVO error=null;
		//added by jisha for bug ULD363 starts
		errors = validateForm(form,
				logonAttributes.getCompanyCode());
		
		if (errors != null && errors.size() > 0) {
			session.setULDRepairFilterVO(null);
 			invocationContext.addAllError(errors);
 			invocationContext.target = LIST_FAILURE;
 			return;
			}	
		//added by jisha for bug ULD363 ends
		        
		String toDisplayPage = form.getDisplayPage();
		int displayPage = Integer.parseInt(toDisplayPage);	
		HashMap indexMap = null;
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
		
		String strAbsoluteIndex = (String)indexMap.get(toDisplayPage);
		if(strAbsoluteIndex != null){
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		
		
			
		if(session.getScreenId()!=null && ("LISTREPAIR").equals(session.getScreenId()))
		{
		log.log(Log.INFO, "&&&&&&&&&&&&session.getScreenId()", session.getScreenId());
			log.log(Log.INFO, "&&&&&&&&&&&&session.getULDRepairFilterVO()",
					session.getULDRepairFilterVO());
			filterVO=session.getULDRepairFilterVO();
			session.setScreenId("");

		}else
			if(form.getInvoiceId()!=null && form.getInvoiceId().trim().length()!=0){
				log.log(Log.INFO, "&&&&&&&&&&&&session.getULDRepairFilterVO()",
						session.getULDRepairFilterVO());
				filterVO=session.getULDRepairFilterVO();	
				
			}
		else
		{
		session.setScreenId("");
		
		
		filterVO.setCompanyCode(companyCode);
		if(form.getUldNo()!= null && !BLANK.equals(form.getUldNo())){
		filterVO.setUldNumber(form.getUldNo().toUpperCase());
		}
		if(form.getUldTypeCode()!=null && !BLANK.equals(form.getUldTypeCode())){
		filterVO.setUldTypeCode(form.getUldTypeCode().toUpperCase());
		}
		if(form.getUldStatus()!=null && !BLANK.equals(form.getUldStatus()) &&
				!ALL.equals(form.getUldStatus())){
		filterVO.setUldStatus(form.getUldStatus());
		}
		if(form.getRepairHead()!=null && !BLANK.equals(form.getRepairHead())){
		filterVO.setRepairHead(form.getRepairHead().toUpperCase());
		}
		if(form.getCurrentStn()!=null && !BLANK.equals(form.getCurrentStn())){
		filterVO.setCurrentStation(form.getCurrentStn().toUpperCase());
		}
		if(form.getRepairStatus()!=null && !BLANK.equals(form.getRepairStatus())){
			filterVO.setRepairStatus(form.getRepairStatus());
		}
		if(form.getRepairedStn()!=null && !BLANK.equals(form.getRepairedStn())){
			filterVO.setRepairStation(form.getRepairedStn().toUpperCase());
		}
		if(DateUtilities.isValidDate( form.getRepairedDateFrom(),CALENDAR_DATE_FORMAT)){
			LocalDate repairFromDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
			filterVO.setFromDate(repairFromDate.setDate(form.getRepairedDateFrom()));
		}
		if(DateUtilities.isValidDate( form.getRepairedDateTo(),CALENDAR_DATE_FORMAT)){
			LocalDate repairToDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
			filterVO.setToDate(repairToDate.setDate(form.getRepairedDateTo()));
		}

		filterVO.setPageNumber(displayPage);
		//Added by A-5214 as part from the ICRD-20959 starts
		if(!"YES".equals(form.getCountTotalFlag()) && session.getTotalRecords().intValue() != 0){
			filterVO.setTotalRecords(session.getTotalRecords().intValue());
	    }else{
	    	filterVO.setTotalRecords(-1);
	    }
		//Added by A-5214 as part from the ICRD-20959 end
		
		
				
		
		//filterVO.setAbsoluteIndex(nAbsoluteIndex);
		
		

		session.setULDRepairFilterVO(filterVO);

    }


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
				log.log(log.FINE,"&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&inside try block");
		log.log(Log.INFO, "filtervo before setting to delegate", filterVO);
			repairDetailVOs =delegate.listULDRepairDetails(filterVO);
			
		}catch(BusinessDelegateException businessDelegateException){
			businessDelegateException.getMessage();
			exception = handleDelegateException(businessDelegateException);
		}
		if(repairDetailVOs!=null && repairDetailVOs.size()>0){
			for(ULDRepairDetailsListVO uldDamageRepairDetailsVO:repairDetailVOs){
			log
					.log(
							Log.INFO,
							"&&&&&&&&&**********uldDamageRepairDetailsVO***********&&&&&&&&&&&&&&",
							uldDamageRepairDetailsVO);
			}
		}
			
		form.setListStatus("");	
		
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, String> systemUnitCodes = new HashMap<String, String>();
		Collection<String> systemUnitValues = new ArrayList<String>();
		

			systemUnitValues.add(CURRENCY_VALUE);
			log.log(Log.INFO,"-------------------Calling findSystemParameterByCodes");
			try {
				systemUnitCodes = sharedDefaultsDelegate
						.findSystemParameterByCodes(systemUnitValues);
			
			String ownCurrencyValue = systemUnitCodes.get(CURRENCY_VALUE);
			log.log(Log.FINE, "The ownCurrencyValue Value is ",
					ownCurrencyValue);
			//ownVolumeUnit = systemUnitCodes.get(SYSTEM_VOLUMECODE);
			form.setCurrencyValue(ownCurrencyValue);
			} catch (BusinessDelegateException e) {
				
				e.getMessage();
			}
			log
					.log(Log.FINE, "The Currency Value is ", form.getCurrencyValue());
		if(repairDetailVOs!=null && repairDetailVOs.size()>0 && repairDetailVOs.getActualPageSize() > 0){
			log.log(Log.FINE,
					"*****************ActualPageSize****************",
					repairDetailVOs.getActualPageSize());
			log.log(Log.FINE,
					"*****************DefaultPageSize****************",
					repairDetailVOs.getDefaultPageSize());
			log.log(Log.FINE, "*****************AbsoluteIndex****************",
					repairDetailVOs.getAbsoluteIndex());
			form.setListStatus("N");
			session.setULDDamageRepairDetailsVOs(repairDetailVOs);
			session.setTotalRecords(repairDetailVOs.getTotalRecordCount());
			}
			else{
				error = new ErrorVO("uld.defaults.nulllist");
				errors.add(error);
				session.setULDRepairFilterVO(null);
				session.removeULDDamageRepairDetailsVOs();
			}
		if (errors != null && errors.size() > 0) {
			session.setULDRepairFilterVO(null);
 			invocationContext.addAllError(errors);
 			invocationContext.target = LIST_FAILURE;
 			return;
			}else {
				if(form.getInvoiceId()!=null && form.getInvoiceId().trim().length()!=0){
					ErrorVO errorVO = new ErrorVO("uld.defaults.repair.geninvoice",
				    		 new Object[]{form.getInvoiceId()});
					 errorVO.setErrorDisplayType(INFO);
					 errors.add(errorVO);
					 form.setInvoiceId(null);
				}
				finalMap = indexMap;
				if (session.getULDDamageRepairDetailsVOs() != null){
				finalMap = 
				buildIndexMap(indexMap, session.getULDDamageRepairDetailsVOs());
				}
				session.setIndexMap(finalMap);
				invocationContext.addAllError(errors);
			    invocationContext.target =LIST_SUCCESS;
			}
    }
//  added by jisha for bug ULD363 starts
    private Collection<ErrorVO> validateForm(
    		ListRepairReportForm form,String companyCode) {
    		log.log(Log.FINE,"*****************inside validate form**************");
    			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    			ErrorVO error = null;
    			
    			if (((!("").equals(form.getRepairedDateFrom())) && form.getRepairedDateFrom()!= null)
    					&& ((!("").equals(form.getRepairedDateTo())) && form.getRepairedDateTo()!= null)) {
    				if (!form.getRepairedDateFrom().equals(
    						form.getRepairedDateTo())) {
    					if (!DateUtilities.isLessThan(form
    							.getRepairedDateFrom(), form.getRepairedDateTo(),
    							"dd-MMM-yyyy")) {
    						error= new ErrorVO(
    								"uld.defaults.fromdateearliertodate");
    						error.setErrorDisplayType(ErrorDisplayType.ERROR);
    						errors.add(error);
    					}
    				}
    			}	
    			return errors;
    			
    }
//  added by jisha for bug ULD363 ends

/**
 * @param existingMap
 * @param page
 * @return HashMap
 */
private HashMap buildIndexMap(HashMap existingMap, Page page) {
	HashMap finalMap = existingMap;
	String currentPage = String.valueOf(page.getPageNumber());
	String currentAbsoluteIndex = String.valueOf(page.getAbsoluteIndex());
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

