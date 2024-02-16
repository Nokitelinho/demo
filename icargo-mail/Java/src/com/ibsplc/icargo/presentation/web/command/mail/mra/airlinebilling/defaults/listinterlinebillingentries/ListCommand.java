
/*
 * ListCommand.java Created on Aug 7, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.listinterlinebillingentries;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.AirlineBillingFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-3434
 * 
 *
 */
public class ListCommand extends BaseCommand {

	private static final String MODULE = "mailtracking.mra.airlinebilling";

	private static final String SCREENID = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String CLASS_NAME = "ListCommand";

	private static final String KEY_NO_RESULTS_FOUND = 
		"mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.noresultsfound";

	private static final String ERROR_KEY_DATE = 
		"mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.nodatefields";
	
	private static final String ERROR_KEY_NO_INVALID_DATE = 
		"mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.notvaliddate";
	private static final String INVALID_AIRLINE_CODE=
		"mra.airlinebilling.outward.listinterlinebillingentries.msg.err.invalidairlinecode";
	
	private static final String BLANK = "";
	//private static final String BILLING_STATUS="mra.airlinebilling.billingstatus";
	private static final String  FROM_INTERLINE = "fromInterLineBilling";
	private static final String TO_INTERLINE = "toInterLineBilling";
	
	private static final String IS_REVIEW_ENABLED = "mailtracking.mra.airlinebilling.IsReviewEnabledforMailOutward";
	
	private Log log = LogFactory.getLogger("MRA_AIRLINEBILLING");

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		ListInterlineBillingEntriesForm form =(ListInterlineBillingEntriesForm)invocationContext.screenModel;
		ListInterlineBillingEntriesSession session = (ListInterlineBillingEntriesSession)getScreenSession(
				MODULE, SCREENID);
		
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		//session.setFromScreen(FROM_INTERLINE); //Commented by A-4809 for CzechAirline demo issue
		AirlineBillingFilterVO filterVO = new AirlineBillingFilterVO();
		Page<DocumentBillingDetailsVO> documentBillingDetailsVOs = null;
		//Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs	= new ArrayList<DocumentBillingDetailsVO>();
//		filterVO.setPageNumber(Integer.parseInt(form
//				.getDisplayPage()));
		Collection<ErrorVO> errors 	= new ArrayList<ErrorVO>();
		AirlineValidationVO airlineValidationVO = null;
		ErrorVO error = null;
		errors=validateForm(form,errors);
		if(errors != null && errors.size() > 0){
			log.log(Log.FINE, "errors--> ", errors);
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		if(session.getSelectedVoidMailbags() != null){
			session.setSelectedRow(null);
			session.setSelectedVoidMailbags(null);
   	 	}
		
			
			
			String sysparValue = null;
			ArrayList<String> systemParameters = new ArrayList<String>();
			systemParameters.add(IS_REVIEW_ENABLED);
			Map<String, String> systemParameterMap = null;
			try {
				systemParameterMap = new SharedDefaultsDelegate()
						.findSystemParameterByCodes(systemParameters);
			} catch (BusinessDelegateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
			if (systemParameterMap != null) {
				sysparValue = systemParameterMap.get(IS_REVIEW_ENABLED);
				form.setIsReviewEnabledFlag(sysparValue);
			}
			
			
			
			
			
			
		
		log.log(Log.FINE, "CloseFlag--> ", session.getCloseFlag());
		if(TO_INTERLINE.equals(session.getCloseFlag())){
			AirlineBillingFilterVO vo =session.getAirlineBillingFilterVO();
			log.log(Log.FINE, "session value of filter vo",vo);
			form.setFromDate(vo.getFromDate().
					toDisplayDateOnlyFormat());
			form.setToDate(vo.getToDate().
					toDisplayDateOnlyFormat());
			form.setBillingType(vo.getBillingType());
					
			form.setBillingStatus(vo.getBillingStatus());
			form.setAirlineCode(vo.getAirlineCode());
			form.setSectorFrom(vo.getSectorFrom());
			form.setSectorTo(vo.getSectorTo());
			//Added by A-4809 as part of Navigation issue... Starts
			form.setOriginOfficeOfExchange(vo.getOriginOfficeOfExchange());
			form.setDestinationOfficeOfExchange(vo.getDestinationOfficeOfExchange());
			form.setMailCategory(vo.getMailCategory());
			form.setSubClass(vo.getSubClass());
			form.setYear(vo.getYear());
			form.setDsn(vo.getDsn());
			form.setReceptacleSerialNumber(vo.getReceptacleSerialNumber());
			form.setHighestNumberIndicator(vo.getHighestNumberIndicator());
			form.setRegisteredIndicator(vo.getRegisteredIndicator());
			//Added by A-4809 as part of Navigation issue... Ends
			session.setFromScreen(BLANK);
			session.setCloseFlag(BLANK);
			//added by A-5223 for ICRD-21098 starts
			form.setDisplayPage("1");
			filterVO.setTotalRecords(-1);
			//added by A-5223 for ICRD-21098 ends
		}
		
 		filterVO.setCompanyCode(companyCode);
		
		LocalDate fromDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
        LocalDate toDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	
        if(form.getFromDate()!=null && form.getFromDate().trim().length()>0){
             fromDate.setDate(form.getFromDate());
              filterVO.setFromDate(fromDate);
         }
         if(form.getToDate()!=null && form.getToDate().trim().length()>0){
         	toDate.setDate(form.getToDate());
             filterVO.setToDate(toDate);
         }
         		
		if(form.getBillingType()!=null&&form.getBillingType().trim().length()>0){
			
			filterVO.setBillingType(form.getBillingType());
		}
		if(form.getBillingStatus()!=null&&form.getBillingStatus().trim().length()>0){
			
			filterVO.setBillingStatus(form.getBillingStatus());
		}
		if(form.getSectorFrom()!=null&&form.getSectorFrom().trim().length()>0){
			
			filterVO.setSectorFrom(form.getSectorFrom());
		}
		if(form.getSectorTo()!=null&&form.getSectorTo().trim().length()>0){
			
			filterVO.setSectorTo(form.getSectorTo());
		}
		
if(form.getOriginOfficeOfExchange()!=null&&form.getOriginOfficeOfExchange().trim().length()>0){
			
			filterVO.setOriginOfficeOfExchange(form.getOriginOfficeOfExchange());
		}
		
		
if(form.getDestinationOfficeOfExchange()!=null&&form.getDestinationOfficeOfExchange().trim().length()>0){
	
	filterVO.setDestinationOfficeOfExchange(form.getDestinationOfficeOfExchange());
}
if(form.getMailCategory()!=null&&form.getMailCategory().trim().length()>0){
	
	filterVO.setMailCategory(form.getMailCategory());
}
if(form.getSubClass()!=null&&form.getSubClass().trim().length()>0){
	
	filterVO.setSubClass(form.getSubClass());
}
if(form.getYear()!=null&&form.getYear().trim().length()>0){
	
	filterVO.setYear(form.getYear());
}
if(form.getDsn()!=null&&form.getDsn().trim().length()>0){
	
	filterVO.setDsn(form.getDsn());
}
if(form.getReceptacleSerialNumber()!=null&&form.getReceptacleSerialNumber().trim().length()>0){
	
	filterVO.setReceptacleSerialNumber(form.getReceptacleSerialNumber());
}
if(form.getHighestNumberIndicator()!=null&&form.getHighestNumberIndicator().trim().length()>0){
	
	filterVO.setHighestNumberIndicator(form.getHighestNumberIndicator());
}
if(form.getRegisteredIndicator()!=null&&form.getRegisteredIndicator().trim().length()>0){
	
	filterVO.setRegisteredIndicator(form.getRegisteredIndicator());
}
		
		
		
		
		
		
		
		
		
		if(form.getAirlineCode()!= null && form.getAirlineCode().trim().length()>0){
			try {
			airlineValidationVO = new AirlineDelegate().validateAlphaCode(
					companyCode,form.getAirlineCode());
			}
			
			catch (BusinessDelegateException e) {
				
			if(airlineValidationVO == null ){
				log.log(Log.FINE, "errors in airlinecode--> ", errors);
				errors.add(new ErrorVO(INVALID_AIRLINE_CODE));
	    		invocationContext.addAllError(errors);
	    		
				invocationContext.target = LIST_FAILURE;
				return;
				
			}
			}
			
			log.log(Log.FINE, " airlineValidationVO.... ", airlineValidationVO);
				filterVO.setAirlineId
						(airlineValidationVO.getAirlineIdentifier());
				
				filterVO.setAirlineCode(form.getAirlineCode());
		}
		
		//added by A-5223 for ICRD-21098 starts
		if(ListInterlineBillingEntriesForm.PAGINATION_MODE_FROM_NAVIGATION_LINK.equals(form.getPaginationMode()) ) {

			log.log(Log.FINE, " <: Page navigating after listing :> ");
			filterVO.setTotalRecords(session.getTotalRecords());

		} else {
			log.log(Log.FINE, " <: LISTING FROM FILTER :> ");
			log
					.log(
							Log.FINE,
							"VALUE SETTED THROUGH JSP}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}} ",
							form.getPaginationMode());
			if(form.getPaginationMode() != null)//This line added by Manish for IASCB-40012
				form.setDisplayPage("1");
			filterVO.setTotalRecords(-1);
		}
		//added by A-5223 for ICRD-21098 ends
		
		//commented by A-5223 for ICRD-2108
		
		// Added for handling PageAwareMultiMapper implementation(Absolute index)
 		/*
 		HashMap<String, String> indexMap = null;
		HashMap<String, String> finalMap = null;
		if(session.getIndexMap() != null){
			indexMap = session.getIndexMap();
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
			*/
		//Added for Page Aware Multimapper Ends 		 
		
//		session.setAirlineBillingFilterVO(filterVO);
		/*
		 * calling MailTrackingMRADelegate
		 */
		try {
			log.log(Log.FINE,"Inside try : Calling findInterlineBillingEntries");
			filterVO.setPageNumber(Integer.parseInt(form
					.getDisplayPage()));
			session.setAirlineBillingFilterVO(filterVO);
			documentBillingDetailsVOs = new MailTrackingMRADelegate().findInterlineBillingEntries(filterVO);
//			log.log(Log.FINE, "documentBillingDetailsVO from Server:--> "
//					+ documentBillingDetailsVOs);

		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			log.log(Log.FINE, "caught");
		}
		
		if(documentBillingDetailsVOs==null ||documentBillingDetailsVOs.size()==0 ){
			session.removeDocumentBillingDetailVOs();
    		errors.add(new ErrorVO(KEY_NO_RESULTS_FOUND));
    		invocationContext.addAllError(errors);
    		
			invocationContext.target = LIST_FAILURE;
			return;

    	}
		else{
			log.log(Log.FINE, " the total records in the    list:>",
					documentBillingDetailsVOs.getTotalRecordCount());
			log.log(Log.FINE, " caching in screen session ");
   			session.setTotalRecords(documentBillingDetailsVOs.getTotalRecordCount()); 
   		//added by A-5223 for ICRD-21098 ends
			
			session.setDocumentBillingDetailVOs(documentBillingDetailsVOs);
    		form.setShowPopup("Y");
    		form.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
    		//commented by A-5223 for ICRD-2108
    		/*
    		finalMap = indexMap;
    		
   		finalMap = buildIndexMap(indexMap, session.getDocumentBillingDetailVOs());
    		
    		session.setIndexMap(finalMap);


    		log.log(Log.INFO, "GOING TO PUT FINAL MAP IN SESSION---------*****"
   				+ finalMap);*/
    		invocationContext.target = LIST_SUCCESS;
			
			}	

	}
	
	  /**
     * Validating the Filter parameters
     * @param ListInterlineBillingEntriesForm
     * @param companyCode
     * @return
     */
    private Collection<ErrorVO> validateForm(
    		ListInterlineBillingEntriesForm form , Collection<ErrorVO> errors){
    	
				
    	if(BLANK.equals(form.getFromDate()) || BLANK.equals(form.getToDate())){
    		
			errors.add(new ErrorVO(ERROR_KEY_DATE));
			
		}else if(!validateDate(form.getFromDate(), form.getToDate())){
			
			errors.add(new ErrorVO(ERROR_KEY_NO_INVALID_DATE));
		}
		
		return errors;
	}
    
    /**
	 * validating fromdate and todate 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	private boolean validateDate( String fromDate, String toDate ){
		
		if( ((toDate != null)&&(toDate.trim().length()>0)) && 
				((fromDate != null) &&(fromDate.trim().length()>0))&& !(fromDate.equals(toDate))) {
			
			return DateUtilities.isLessThan( fromDate, toDate, LocalDate.CALENDAR_DATE_FORMAT );
			
		}else{
			
			return true;
		}
	}
    

	 /**
	 * 	Added by A-3434 for handling PageAwareMultiMapper
	 *   
	 * method to bulid the hashmap to maintain absoluteindex
	 * 
	 * @param existingMap HashMap<String, String>
	 * @param page Page
	 * @return HashMap<String, String>
	 * 
	 */
	//commented by A-5223 for ICRD-2108
   /*
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
	}*/


}	

