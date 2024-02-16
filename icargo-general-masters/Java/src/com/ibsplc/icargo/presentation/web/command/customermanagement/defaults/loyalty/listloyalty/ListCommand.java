/*
 * ListCommand.java Created on Apr 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.listloyalty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.ListLoyaltySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.ListLoyaltyForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * This command class is used to list the  Loyalty
 * @author A-1862
 */
public class ListCommand extends BaseCommand {
	
	
	private Log log = LogFactory.getLogger("LIST Loyalty");
	
	private static final String MODULE = "customermanagement.defaults";
	
	
	private static final String SCREENID =
		"customermanagement.defaults.listloyalty";
    
    private static final String LIST_SUCCESS = "list_success";
    private static final String LIST_FAILURE = "list_failure";
    
    private static final String PERIOD_ONETIME = 
    	"customer.addloyaltyparams.periodforredumption";

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
		String  compCode = logonAttributes.getCompanyCode();
		
		ListLoyaltyForm listLoyaltyForm = 
			(ListLoyaltyForm) invocationContext.screenModel;
		ListLoyaltySession listLoyaltySession = 
			(ListLoyaltySession)getScreenSession(MODULE,SCREENID);
		
		log.log(Log.FINE, "listLoyaltyForm.getFromList() ---> ",
				listLoyaltyForm.getFromList());
		listLoyaltySession.setLoyaltyDetails(null);
		
		SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		
		try {
		oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
		compCode, getOneTimeParameterTypes());
		} catch (BusinessDelegateException e) {
//printStackTrrace()();
		handleDelegateException(e);
		}
		listLoyaltySession.setOneTimeValues(
		(HashMap<String, Collection<OneTimeVO>>)oneTimeValues);
		
		
		
		
		LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO=new LoyaltyProgrammeFilterVO();
		if("fromlist".equals(listLoyaltyForm.getFromList()))
		{
		
		
		loyaltyProgrammeFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		
		if(listLoyaltyForm.getLoyaltyName()!=null &&
				listLoyaltyForm.getLoyaltyName().length()!=0 )
        {
			loyaltyProgrammeFilterVO.setLoyaltyProgrammeCode(listLoyaltyForm.getLoyaltyName().toUpperCase());
        }
		if(listLoyaltyForm.getToDate()!=null &&
				listLoyaltyForm.getToDate().length()!=0 )
        {
    		LocalDate toDate = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
    		loyaltyProgrammeFilterVO.setToDate(toDate.setDate(
    				listLoyaltyForm.getToDate()));
        }
		if(listLoyaltyForm.getFromDate()!=null &&
				listLoyaltyForm.getFromDate().length()!=0 )
        {
    		LocalDate fromDate = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
    		loyaltyProgrammeFilterVO.setFromDate(fromDate.setDate(
    				listLoyaltyForm.getFromDate()));
        }
		
		listLoyaltySession.setFilterVO(loyaltyProgrammeFilterVO);
		log.log(Log.FINE, "loyaltyProgrammeFilterVO  from form ---> ",
				loyaltyProgrammeFilterVO);
		
		}
		
		else
			
		{
			
			loyaltyProgrammeFilterVO=listLoyaltySession.getFilterVO();
			log.log(Log.FINE, "loyaltyProgrammeFilterVO  from session ---> ",
					loyaltyProgrammeFilterVO);
			if(loyaltyProgrammeFilterVO.getFromDate()!=null){
			String fromdate = TimeConvertor.toStringFormat(loyaltyProgrammeFilterVO.getFromDate().toCalendar(),
					TimeConvertor.CALENDAR_DATE_FORMAT);
			listLoyaltyForm.setFromDate(fromdate);
			}
			else{
				listLoyaltyForm.setFromDate("");
			}
			
			if(loyaltyProgrammeFilterVO.getToDate()!=null){
			String todate = TimeConvertor.toStringFormat(loyaltyProgrammeFilterVO.getToDate().toCalendar(),
					TimeConvertor.CALENDAR_DATE_FORMAT);
			listLoyaltyForm.setToDate(todate);
			}
			else{
				listLoyaltyForm.setToDate("");
			}
			
			listLoyaltyForm.setLoyaltyName(loyaltyProgrammeFilterVO.getLoyaltyProgrammeCode());
			
		}
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		String toDisplayPage = listLoyaltyForm.getDisplayPage();
		int displayPage = Integer.parseInt(toDisplayPage);

		Page<LoyaltyProgrammeVO> loyaltyDetails = null;
		 if(!"Y".equals(listLoyaltyForm.getCountTotalFlag()) && listLoyaltySession.getTotalRecords()!=null)
			 {
			 loyaltyProgrammeFilterVO.setTotalRecords(listLoyaltySession.getTotalRecords().intValue());
			 }
	        else
	        	{
	        	loyaltyProgrammeFilterVO.setTotalRecords(-1);
	        	}
	        log.log(3, (new StringBuilder()).append("checked.length-------------------->").append(loyaltyProgrammeFilterVO).toString());
	       

		
		try{
			loyaltyDetails =listLoyaltyDetails(loyaltyProgrammeFilterVO,displayPage);
		}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
		errors = handleDelegateException(businessDelegateException);
		}
		
		
		if(  loyaltyDetails != null &&  loyaltyDetails.size() > 0 ) {
			log.log(Log.FINE, " the total records in the    list:>",
					loyaltyDetails.getTotalRecordCount());
			log.log(Log.FINE, " caching in screen session ");
			listLoyaltySession.setTotalRecords(loyaltyDetails.getTotalRecordCount()); 
		}
		
		
		listLoyaltySession.setLoyaltyDetails(loyaltyDetails);
		log.log(Log.FINE, "\n<--loyaltyDetails-->\n", loyaltyDetails);
		if (loyaltyDetails == null || 
				loyaltyDetails.getActualPageSize() == 0){
		
		errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		error = new ErrorVO( "customermanagement.defaults.listloyalty.nomatchingcriteria",null);
		error.setErrorDisplayType(ErrorDisplayType.ERROR);
		errors.add(error);
		
		if(errors.size()>0){
		invocationContext.addAllError(errors);
		invocationContext.target = 	LIST_FAILURE;
		return;
		}
		}
	
		invocationContext.target = LIST_SUCCESS;
        
    }
 
    private Page<LoyaltyProgrammeVO> 
	listLoyaltyDetails(
			LoyaltyProgrammeFilterVO 
			loyaltyProgrammeFilterVO,int displayPage) throws BusinessDelegateException{
    	CustomerMgmntDefaultsDelegate	customerMgmntDefaultsDelegate =
								new CustomerMgmntDefaultsDelegate();
	
	
	
	return customerMgmntDefaultsDelegate.listAllLoyaltyProgrammes(loyaltyProgrammeFilterVO, displayPage);
	
	}
    
    /*
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	
    	parameterTypes.add(PERIOD_ONETIME);
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }

}
