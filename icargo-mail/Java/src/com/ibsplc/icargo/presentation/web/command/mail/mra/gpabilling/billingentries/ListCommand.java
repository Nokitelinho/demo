/*
 * ListCommand.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.billingentries;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingEntriesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingEntriesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class ListCommand extends BaseCommand  {
	private Log log = LogFactory.getLogger("MRA GPABILLING");
	private static final String DETAILS_SUCCESS= "details_success";
	private static final String DETAILS_FAILURE= "details_failure";
	private static final String CLASS_NAME = "ListCommand";
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREENID = "mailtracking.mra.gpabilling.billingentries.listgpabillingentries";
	private static final String FROMDATE_MUST_NOT_BE_GREATER="mailtracking.mra.gpabilling.msg.err.fromdatenotgreater";
	private static final String FROMDATE_MUST_NOT_BE_NULL="mailtracking.mra.gpabilling.msg.err.fromdatenotnull";
	private static final String TODATE_MUST_NOT_BE_NULL="mailtracking.mra.gpabilling.msg.err.todatenotnull";
	private static final String NO_RESULTS_FOUND="mailtracking.mra.gpabilling.msg.err.noresultsfound";
	private static final String PARENT_PAGE = "fromotherpage";
	private static final String LEVEL_FOR_DATA_IMPORT_TO_MRA="mailtracking.defaults.DsnLevelImportToMRA";
	private static final String CONFRM_MSG="mailtracking.mra.gpabilling.rerateconfirmation";
	private static final String FROM_DSNROUTING_SCREEN = "dsnrouting";//added by A-6991 for CR ICRD-189046
	private static final String INVALID_CITY="mailtracking.mra.gpabilling.msg.err.invalidcity";////Added by A-4809 for CR ICRD-258393
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */

    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering(CLASS_NAME, "execute");

    	GPABillingEntriesSession gpaSession =
			(GPABillingEntriesSession)getScreenSession(
					MODULE_NAME, SCREENID);
    	GPABillingEntriesForm gpaForm =
			(GPABillingEntriesForm)invocationContext.screenModel;
    	ErrorVO error=null;
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		GPABillingEntriesFilterVO filterVO = null;
		Page<DocumentBillingDetailsVO> documentBillingDetailsVOs = null;
		HashMap<String, String> finalMap = null;
		HashMap<String, String> indexMap = getIndexMap(gpaSession.getIndexMap(), invocationContext);
    	LocalDate fromDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
        LocalDate toDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	 String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	 //String[] conDocNum = gpaForm.getConsignmentNumber();
    	 //String[] dsnNum = gpaForm.getDsn();
    	 if(null != gpaSession.getGPABillingEntriesFilterVO()){
    		 setSearchDetailsInForm(gpaForm);
    	 }
    	 if(gpaSession.getSelectedVoidMailbags() != null && !gpaSession.getSelectedVoidMailbags().isEmpty()){
    		 gpaSession.setSelectedRows(null);
    		 gpaSession.setSelectedVoidMailbags(null);
    	 }
    	 String consignmentNumber = gpaForm.getConsignmentNumber();
    	 String dsnNumber = gpaForm.getDsn();
    		 
    	 filterVO=new GPABillingEntriesFilterVO();
    	 filterVO.setCompanyCode(companyCode);
    	 filterVO.setPageNumber(Integer.parseInt(gpaForm.getDisplayPage()));
    	 
    	 //Added by A-6991 for ICRD-137019 starts
    	 if(DocumentBillingDetailsVO.FLAG_YES.equalsIgnoreCase(gpaForm.getContractRate())){
    		 filterVO.setContractRate(MRAConstantsVO.CONTRACT_RATE);
    	 }
    	 if(DocumentBillingDetailsVO.FLAG_YES.equalsIgnoreCase(gpaForm.getUPURate())){
    		 filterVO.setUPURate(MRAConstantsVO.UPU_RATE);
    	 }
    	//Added by A-6991 for ICRD-137019 Ends
         		if(gpaForm.getFromDate()!=null && gpaForm.getFromDate().trim().length()>0){
                  fromDate.setDate(gpaForm.getFromDate());
                  filterVO.setFromDate(fromDate);
         		}
         		else{
         			error=new ErrorVO(FROMDATE_MUST_NOT_BE_NULL);
         			errors.add(error);
         			}
          
         		 if(gpaForm.getToDate()!=null && gpaForm.getToDate().trim().length()>0){
         			 toDate.setDate(gpaForm.getToDate());
         			filterVO.setToDate(toDate);
         		 }
         		 else{
         			error=new ErrorVO(TODATE_MUST_NOT_BE_NULL);
         			errors.add(error);
         		 }
         		  if ((filterVO.getFromDate() != null) && (!("").equals(filterVO.getFromDate().toString()))&&
       					filterVO.getToDate() != null && ((!("").equals(filterVO.getToDate().toString())))){
                 if(filterVO.getFromDate().isGreaterThan(filterVO.getToDate())){
         				error=new ErrorVO(FROMDATE_MUST_NOT_BE_GREATER);
         				//error.setErrorDisplayType(ERROR);
         		   		errors.add(error);
                  }
                  }
         		//Added by A-4809 for CR ICRD-258393 ...Starts
         		if(gpaForm.getOrigin()!=null && gpaForm.getOrigin().trim().length()>0){
     				Collection<String> originColl = new ArrayList<String>();
     				originColl.add(gpaForm.getOrigin());
     				Collection<ErrorVO> errs=null;
     				errs = validateCityCodes(companyCode,originColl);
     					if(errs !=null && errs.size()>0){
     						error= new ErrorVO(INVALID_CITY);
     						error.setErrorDisplayType(ErrorDisplayType.ERROR);
     						errors.add(error);
     					}
         		}
         		if(gpaForm.getDestination()!=null && gpaForm.getDestination().trim().length()>0){
     				Collection<String> destColl = new ArrayList<String>();
     				destColl.add(gpaForm.getDestination());
     				Collection<ErrorVO> errs=null;
     				errs = validateCityCodes(companyCode,destColl);
     					if(errs !=null && errs.size()>0){
     						error= new ErrorVO(INVALID_CITY);
     						error.setErrorDisplayType(ErrorDisplayType.ERROR);
     						errors.add(error);
     					}
         		}
         		//Added by A-4809 for CR ICRD-258393 ...ends
         		 if(gpaForm.getStatus()!=null && gpaForm.getStatus().trim().length()>0) {
					filterVO.setBillingStatus(gpaForm.getStatus().toUpperCase());
				}
         		 if(gpaForm.getOriginOfficeOfExchange()!=null && gpaForm.getOriginOfficeOfExchange().trim().length()>0) {
 					filterVO.setOriginOfficeOfExchange(gpaForm.getOriginOfficeOfExchange().toUpperCase());
 				}
         		if(gpaForm.getDestinationOfficeOfExchange()!=null && gpaForm.getDestinationOfficeOfExchange().trim().length()>0) {
 					filterVO.setDestinationOfficeOfExchange(gpaForm.getDestinationOfficeOfExchange().toUpperCase());
 				}
         		if(gpaForm.getMailCategoryCode()!=null && gpaForm.getMailCategoryCode().trim().length()>0) {
 					filterVO.setMailCategoryCode(gpaForm.getMailCategoryCode().toUpperCase());
 				}
         		if(gpaForm.getMailSubclass()!=null && gpaForm.getMailSubclass().trim().length()>0) {
 					filterVO.setMailSubclass(gpaForm.getMailSubclass().toUpperCase());
 				}
         		if(gpaForm.getYear()!=null && gpaForm.getYear().trim().length()>0) {
 					filterVO.setYear(gpaForm.getYear().toUpperCase());
 				}
         		if(gpaForm.getRecepatableSerialNumber()!=null && gpaForm.getRecepatableSerialNumber().trim().length()>0) {
 					filterVO.setRsn(gpaForm.getRecepatableSerialNumber().toUpperCase());
 				}
         		if(gpaForm.getHighestNumberIndicator()!=null && gpaForm.getHighestNumberIndicator().trim().length()>0) {
 					filterVO.setHni(gpaForm.getHighestNumberIndicator().toUpperCase());
 				}
         		if(gpaForm.getRegisteredIndicator()!=null && gpaForm.getRegisteredIndicator().trim().length()>0) {
 					filterVO.setRegInd(gpaForm.getRegisteredIndicator().toUpperCase());
 				}
         		 if(gpaForm.getGpaCodeFilter()!=null && gpaForm.getGpaCodeFilter().trim().length()>0) {
					filterVO.setGpaCode(gpaForm.getGpaCodeFilter().toUpperCase());
				}
         		 //Added as part of ICRD-205027 starts
         		 if(gpaForm.getMailbagId()!=null && gpaForm.getMailbagId().trim().length()>0) {
					filterVO.setMailbagId(gpaForm.getMailbagId().toUpperCase());
				}
         		//Added as part of ICRD-205027 ends
         		 if(gpaForm.getCountry()!=null && gpaForm.getCountry().trim().length()>0) {
					filterVO.setCountryCode(gpaForm.getCountry().toUpperCase());
				}
         		//Added by A-4809 for BUG ICRD-17509 ...Starts
         		 if(consignmentNumber!=null && consignmentNumber.trim().length()>0){
         			 filterVO.setConDocNumber(consignmentNumber);
         		 }
         		 if(dsnNumber != null && dsnNumber.trim().length()>0){
         			 filterVO.setDsnNumber(dsnNumber);
         		 }
          		//Added by A-4809 for CR ICRD-258393.. Starts
         		 if(gpaForm.getOrigin()!=null && gpaForm.getOrigin().trim().length()>0){
         			 filterVO.setOrigin(gpaForm.getOrigin());
         		 }
         		 if(gpaForm.getDestination()!=null && gpaForm.getDestination().trim().length()>0){
         			 filterVO.setDestination(gpaForm.getDestination());
         		 }
         		//Added by A-4809 for CR ICRD-258393.. Ends
         		 if(gpaForm.getIsUSPSPerformed()!=null && gpaForm.getIsUSPSPerformed().trim().length()>0){//Added by A-7871 for ICRD-232381
         			 filterVO.setIsUSPSPerformed(gpaForm.getIsUSPSPerformed());
         		 }
         		log.log(Log.INFO, "filterVO *************", filterVO);
				if(errors!=null && errors.size()>0){
                	 log.log(Log.FINE,"!!!inside errors!= null");
        				invocationContext.addAllError(errors);
        				gpaSession.removeGpaBillingDetails();

        				invocationContext.target=DETAILS_FAILURE;
        				return;
        			}

        		
        	// Added for handling PageAwareMultiMapper implementation(Absolute index)
         		
         		//Modified by A-5237 for ICRD-20902 starts
    			//Modified by A-5237 for ICRD-20902 ends
        		if(gpaSession.getIndexMap() != null){
        			indexMap = gpaSession.getIndexMap();
        		}
        		if(indexMap == null){
        			log.log(Log.FINE, "INDEX MAP IS NULL");
        			indexMap = new HashMap<String, String>();
        			indexMap.put("1", "1");
        		}
        		int nAbsoluteIndex = 0;
        		String strAbsoluteIndex = indexMap.get(String.valueOf(filterVO.getPageNumber()));
        		if(strAbsoluteIndex != null){
        			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
        		}
        		filterVO.setAbsoluteIndex(nAbsoluteIndex);
     			
        		
     			
     		//Added for Page Aware Multimapper Ends 		 
        
   	 
         //added by A-5175 for QF CR icrd-21098 starts
        	if(GPABillingEntriesForm.PAGINATION_MODE_FROM_FILTER.equals(gpaForm.getNavigationMode()) || gpaForm.getNavigationMode()==null ) {
        		log.log(Log.INFO,">>>>>  Listing From Filter >>>>>>");
        		filterVO.setTotalRecordCount(-1);
        		//filterVO.setPageNumber(1); Modified by A-5237 for ICRD-20902 ends
        		
        	}else if(GPABillingEntriesForm.PAGINATION_MODE_FROM_NAVIGATION.equals(gpaForm.getNavigationMode())) {
        		log.log(Log.INFO,">>>>>>  Listing From  Navigation >>>>>");
        		log.log(Log.INFO, ">>>>>>  Disply Page >>>>>", gpaForm.getDisplayPage());
				filterVO.setTotalRecordCount(gpaSession.getTotalRecords());
        		filterVO.setPageNumber(Integer.parseInt(gpaForm.getDisplayPage()));
        		
        	}
        		
        	//Added by A-7929 as part ICRD-132548
        	/*if("FROMMCA".equals(gpaForm.getNavigationMode())){
        		filterVO = new GPABillingEntriesFilterVO();
        		filterVO=gpaSession.getGPABillingEntriesFilterVO();
        		
        	}*/
        	
        	
        //	gpaSession.setGPABillingEntriesFilterVO(filterVO);
   
         //added by A-5175 for QF CR icrd-21098 ends

		/*
		 * Delegate Call
		 */
  
		try{
			documentBillingDetailsVOs =new MailTrackingMRADelegate().findGPABillingEntries(filterVO);
		}catch (BusinessDelegateException businessDelegateException) {
			errors=handleDelegateException(businessDelegateException);

		}
		if(documentBillingDetailsVOs==null || documentBillingDetailsVOs.size()==0){
			error=new ErrorVO(NO_RESULTS_FOUND);
			errors.add(error);
		}
		if(errors!=null && errors.size()>0){
       	 log.log(Log.FINE,"!!!inside errors!= null");
				invocationContext.addAllError(errors);
				gpaSession.removeGpaBillingDetails();

				invocationContext.target=DETAILS_FAILURE;
				return;
			}
		/*HashMap<String, Collection<OneTimeVO>> oneTimeHashMap=new HashMap<String, Collection<OneTimeVO>>();
		oneTimeHashMap=gpaSession.getOneTimeVOs();

		if(oneTimeHashMap!=null && oneTimeHashMap.size()>0){

			ArrayList<OneTimeVO> billingStatuss=new ArrayList<OneTimeVO>(oneTimeHashMap.get("mailtracking.mra.gpabilling.ratestatus"));
			if(gpaBillingDetailsVOs!=null){
				gpaForm.setScreenStatus("list");
			for(GPABillingDetailsVO gpaDetailsVo:gpaBillingDetailsVOs){
				if(gpaDetailsVo.getBillingStatus()!=null && gpaDetailsVo.getBillingStatus().length()>0){
				for(OneTimeVO onetime:billingStatuss){
					if(onetime.getFieldValue().equals(gpaDetailsVo.getBillingStatus().trim())){
						gpaDetailsVo.setBillingStatus(onetime.getFieldDescription());
					}
				}
			}
			}
			}
		}*/
		//Added by A-4809 for ICRD-67794 Starts
		HashMap<String, String>systemParameterValues = gpaSession.getSystemparametres();
		String value =systemParameterValues.get(LEVEL_FOR_DATA_IMPORT_TO_MRA);
		if(DocumentBillingDetailsVO.FLAG_YES.equals(value)){
			if(documentBillingDetailsVOs!=null && documentBillingDetailsVOs.size()>0){
				for(DocumentBillingDetailsVO documentBillingDetailsVO:documentBillingDetailsVOs){
					documentBillingDetailsVO.setRegInd("");
					documentBillingDetailsVO.setHni("");
					documentBillingDetailsVO.setRsn(""); 
				}
			}
		}   
		//Added by A-4809 for ICRD-67794 Ends
		if(documentBillingDetailsVOs!=null){
			gpaForm.setScreenStatus("list");
		log.log(Log.INFO, ">>>>>> The Total Recors in the list>>>>>",
					documentBillingDetailsVOs.getTotalRecordCount());
			log.log(Log.INFO,">>>>>> Caching in Screen Sesson>>>>>");
			gpaSession.setTotalRecords(documentBillingDetailsVOs.getTotalRecordCount());
			//added by A-5175 for QF CR icrd-21098 ends
			
		}
		gpaSession.getGpaBillingDetails();
		gpaSession.setGpaBillingDetails(documentBillingDetailsVOs);
		finalMap = indexMap;
		if (gpaSession.getGpaBillingDetails() != null) {
			finalMap = buildIndexMap(indexMap, gpaSession
					.getGpaBillingDetails());
		}
		
		//Modified by A-5237 for ICRD-20902 starts
		gpaSession.setIndexMap((HashMap<String,String>)super.setIndexMap(finalMap, invocationContext));
		//Modified by A-5237 for ICRD-20902 ends

		log.log(Log.INFO, "GOING TO PUT FINAL MAP IN SESSION---------*****",
				finalMap);
		log.exiting(CLASS_NAME,"execute method**** **** **** ");
		if(gpaForm.isReRated()){
			ErrorVO rerateError = null; 
			Collection<ErrorVO> saveerrors = new ArrayList<ErrorVO>();
			rerateError = new ErrorVO(CONFRM_MSG);
			rerateError.setErrorDisplayType(ErrorDisplayType.INFO);
	        saveerrors.add(rerateError);
			invocationContext.addAllError(saveerrors);
			gpaForm.setReRated(false);
		}
	//	gpaSession.setGPABillingEntriesFilterVO(null);//Modified by A-8464 for ICRD-277576
		invocationContext.target = DETAILS_SUCCESS;
    }
    
    /**
	 * 	Added by A-3434 for handling PageAwareMultiMapper
	 *   
	 * method to bulid the hashmap to maintain absoluteindex
	 * 
	 * @param existingMap
	 *            HashMap<String, String>
	 * @param page
	 *            Page
	 * @return HashMap<String, String>
	 * 
	 */
   
	private HashMap<String, String> buildIndexMap(
			HashMap<String, String> existingMap, Page page) {
		
		log.entering(CLASS_NAME, "buildIndexMap");
		HashMap<String, String> finalMap = existingMap;
		String indexPage = String.valueOf((page.getPageNumber() + 1));
		boolean isPageExist = false;
		Set<Map.Entry<String, String>> set = existingMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExist = true;
			}
		}
		if (!isPageExist) {
			finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
		}
		log.entering(CLASS_NAME, "buildIndexMap");
		return finalMap;
	}

	private void setSearchDetailsInForm(GPABillingEntriesForm form){
		GPABillingEntriesSession session = (GPABillingEntriesSession) getScreenSession(MODULE_NAME, SCREENID);
		GPABillingEntriesFilterVO gpaBillingEntriesFilterVO = session.getGPABillingEntriesFilterVO();
		if(null != gpaBillingEntriesFilterVO){
			if(gpaBillingEntriesFilterVO.getFromDate()!= null){
				LocalDate fromDateFormat = new LocalDate(LocalDate.NO_STATION, Location.NONE,gpaBillingEntriesFilterVO.getFromDate(),true);
				form.setFromDate(fromDateFormat.toDisplayDateOnlyFormat());
			}
			if(gpaBillingEntriesFilterVO.getToDate()!= null){
				LocalDate toDateFormat = new LocalDate(LocalDate.NO_STATION, Location.NONE,gpaBillingEntriesFilterVO.getToDate(),true);
				form.setToDate(toDateFormat.toDisplayDateOnlyFormat());
			}
			form.setConsignmentNumber(gpaBillingEntriesFilterVO.getConDocNumber());
			form.setStatus(gpaBillingEntriesFilterVO.getBillingStatus());
			form.setGpaCodeFilter(gpaBillingEntriesFilterVO.getGpaCode());
				form.setMailbagId(gpaBillingEntriesFilterVO.getMailbagId());
			form.setOriginOfficeOfExchange(gpaBillingEntriesFilterVO.getOriginOfficeOfExchange());
			form.setDestinationOfficeOfExchange(gpaBillingEntriesFilterVO.getDestinationOfficeOfExchange());
			form.setMailCategoryCode(gpaBillingEntriesFilterVO.getMailCategoryCode());
			form.setMailSubclass(gpaBillingEntriesFilterVO.getMailSubclass());
			form.setYear(gpaBillingEntriesFilterVO.getYear());
			form.setDsn(gpaBillingEntriesFilterVO.getDsnNumber());
			form.setRecepatableSerialNumber(gpaBillingEntriesFilterVO.getRsn());
			form.setHighestNumberIndicator(gpaBillingEntriesFilterVO.getHni());
			form.setRegisteredIndicator(gpaBillingEntriesFilterVO.getRegInd());
			form.setContractRate(gpaBillingEntriesFilterVO.getContractRate());
			form.setUPURate(gpaBillingEntriesFilterVO.getUPURate());
			form.setOrigin(gpaBillingEntriesFilterVO.getOrigin()); //added by A-8149 for ICRD-270427
			form.setDestination(gpaBillingEntriesFilterVO.getDestination()); //added by A-8149 for ICRD-270427
		}
		//if(!"FROMMCA".equals(form.getNavigationMode())){  //modified by A-7929 as part of ICRD-132548
		session.setGPABillingEntriesFilterVO(null);
		//}
		
		
	}
	/**
	 * 	Method		:	ListCommand.validateCityCodes
	 *	Added by 	:	A-4809 on May 9, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param cityCode
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateCityCodes(String companyCode,
			Collection<String> cityCode) {
		Collection<ErrorVO> errorVO = null;
			try {
				AreaDelegate areaDelegate = new AreaDelegate();	
				 areaDelegate.validateCityCodes(companyCode, cityCode);
			} catch (BusinessDelegateException businessDelegateException) {
				errorVO = handleDelegateException(businessDelegateException);
			}  
		return errorVO;
	}

}
