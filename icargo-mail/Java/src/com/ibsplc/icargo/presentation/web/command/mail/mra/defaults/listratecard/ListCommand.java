/*
 * ListCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listratecard;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateCardSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListUPURateCardForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2521
 *
 */
public class ListCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.listupuratecard";

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String KEY_NO_RESULTS_FOUND = "mailtracking.mra.defaults.listupuratecard.noresultsfound";
	
	private static final String KEY_DATE_MND = "mailtracking.mra.defaults.listupuratecard.datefieldsmand";
	
	private static final String KEY_DATE_INVALID = "mailtracking.mra.defaults.listupuratecard.invaliddates";
	
	private static final String BLANK = "";

	/**
	 * Method to implement the Listing of rate cards
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");
    	
    	ListUPURateCardForm listUPURateCardForm
    	= (ListUPURateCardForm)invocationContext.screenModel;

    	ListUPURateCardSession listUPURateCardSession =
    		(ListUPURateCardSession)getScreenSession(MODULE_NAME, SCREENID);

    	listUPURateCardSession.removeRateCardVOs();
    	listUPURateCardSession.removeRateCardFilterVO();
    	listUPURateCardSession.removeErrorVOs();
    	listUPURateCardSession.getSelectedRateCardVOs();

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	Page<RateCardVO>  rateCardVOs = null;
    	
    	errors = validateForm(listUPURateCardForm, errors);
    	
    	if(errors.size() > 0){
    		
    		invocationContext.addAllError(errors);
			listUPURateCardForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = LIST_FAILURE;
			return;
    	}
    	
    	RateCardFilterVO rateCardFilterVO = populateFilterVO(listUPURateCardForm);

    	rateCardFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
    	log.log(Log.INFO, "selected rateCardFilterVO-->", rateCardFilterVO);
		listUPURateCardForm.setChangeStatusFlag(BLANK);
    	
    	//Added by : A-5175 on 16-Oct-2012 for icrd-21098 starts
    	if(ListUPURateCardForm.PAGINATION_MODE_FROM_FILTER.equals(listUPURateCardForm.getNavigationMode())) {
    		log.log(Log.INFO,"Listing From Filter");
    		rateCardFilterVO.setTotalRecordCount(-1);
    		rateCardFilterVO.setDisplayPage(1);
    		
    	}else if(ListUPURateCardForm.PAGINATION_MODE_FROM_NAVIGATION.equals(listUPURateCardForm.getNavigationMode())) {
    		log.log(Log.INFO,"Listing From Navigation");
    		log.log(Log.INFO, "Total Recors>>>>", listUPURateCardSession.getTotalRecords());
			log.log(Log.INFO, "Disply Page>>", listUPURateCardForm.getDisplayPage());
			rateCardFilterVO.setTotalRecordCount(listUPURateCardSession.getTotalRecords());
    		rateCardFilterVO.setDisplayPage(Integer.parseInt(listUPURateCardForm.getDisplayPage()));
    	}else {
    		rateCardFilterVO.setTotalRecordCount(-1);
    		rateCardFilterVO.setDisplayPage(1);
    	}
        //ends
    	
    	listUPURateCardSession.setRateCardFilterVO(rateCardFilterVO);
    	
    	try {
			rateCardVOs = new MailTrackingMRADelegate().findAllRateCards(rateCardFilterVO);
			

		} catch (BusinessDelegateException e) {
			errors.addAll(handleDelegateException(e));

		}

		if(rateCardVOs != null && rateCardVOs.size() > 0){
			log.log(Log.FINE, " the total records in the    list:>",
					rateCardVOs.getTotalRecordCount());
			log.log(Log.FINE, " caching in screen session ");
			listUPURateCardSession.setTotalRecords(rateCardVOs.getTotalRecordCount());
			 //ends
			
			
			/****indu changed**********/	
			ArrayList<RateCardVO> ratecards=new ArrayList<RateCardVO>(rateCardVOs);
			int arySize=ratecards.size();
			for(int i=0;i<arySize;i++){
				RateCardVO ratecardvo=(RateCardVO)ratecards.get(i);
				LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
				if(ratecardvo.getValidityEndDate().before(currentDate)){
					ratecardvo.setRateCardStatus("E");
				}
				ratecards.set(i,ratecardvo);
			}
			 int siz=ratecards.size();
			  Page<RateCardVO> ratecardpage=new Page<RateCardVO>(ratecards,1,0,siz,0,0,false);
			/****indu changed**********/
			  ratecardpage.setTotalRecordCount(listUPURateCardSession.getTotalRecords());
			listUPURateCardSession.setRateCardVOs(rateCardVOs);
			listUPURateCardForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = LIST_SUCCESS;

		}else{

			errors.add(new ErrorVO(KEY_NO_RESULTS_FOUND));
			invocationContext.addAllError(errors);
			listUPURateCardForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = LIST_FAILURE;
    	}
    }

    /**
     * populates rateCardFilterVO from listUPURateCardForm
     * @param listUPURateCardForm
     * @return rateCardFilterVO
     */
    private RateCardFilterVO populateFilterVO(ListUPURateCardForm listUPURateCardForm){

    	RateCardFilterVO rateCardFilterVO = new RateCardFilterVO();

    	rateCardFilterVO.setEndDate(convertToDate(listUPURateCardForm.getEndDate()));
    	rateCardFilterVO.setStartDate(convertToDate(listUPURateCardForm.getStartDate()));
    	rateCardFilterVO.setRateCardID(listUPURateCardForm.getRateCardID());
    	rateCardFilterVO.setRateCardStatus(listUPURateCardForm.getRateCardStatus());
    	rateCardFilterVO.setDisplayPage(Integer.parseInt(listUPURateCardForm.getDisplayPage()));
    	return rateCardFilterVO;
    }

    /**
	 *
	 * @param date
	 * @return LocalDate
	 */
	private LocalDate convertToDate(String date){

		if(date!=null && !date.equals(BLANK)){

			return(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate( date ));
		}
		return null;
	}
	
	/**
	 * 
	 * @param form
	 * @param errors
	 * @return
	 */
	private Collection<ErrorVO> validateForm(ListUPURateCardForm form, Collection<ErrorVO> errors){
		
		String startDate = form.getStartDate();
    	String endDate = form.getEndDate();
    	
    	if(BLANK.equals(startDate) || BLANK.equals(endDate)){
    		
    		errors.add(new ErrorVO(KEY_DATE_MND));
    	
    	}else if(!validateDate(startDate, endDate)){
    		
    		errors.add(new ErrorVO(KEY_DATE_INVALID));
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
				((fromDate != null) &&(fromDate.trim().length()>0))) {
			  LocalDate frmDat=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			  LocalDate toDat=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			  frmDat.setDate(fromDate);
			  toDat.setDate(toDate);
			  if(frmDat.isGreaterThan(toDat)){
				  log.log(Log.INFO,"todate lesser");
				  return false;
			  } else {
				return true;
			}
			
		}else{
			
			return false;
		}
	}

}
