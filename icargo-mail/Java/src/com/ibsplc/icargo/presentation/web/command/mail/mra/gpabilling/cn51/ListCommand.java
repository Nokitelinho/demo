/*
 * ListCommand.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpabilling.ListCN51SessionImpl;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListCN51Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-1556
 *
 */
public class ListCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA GPABILLING");

	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51s";

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String KEY_NO_RESULTS_FOUND = "mailtracking.mra.gpabilling.noresultsfound";
	
	private static final String ERROR_KEY_DATE = 
		"mailtracking.mra.gpabilling.listcn51.nodatefields";
	
	private static final String ERROR_KEY_NO_INVALID_DATE = 
		"mailtracking.mra.gpabilling.listcn51.notvaliddate";

	private static final String BLANK = "";

	/**
	 * Method to implement the List operation
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");

    	ListCN51Form listCN51Form = (ListCN51Form)invocationContext.screenModel;

    	ListCN51SessionImpl listCN51Session =
    		(ListCN51SessionImpl)getScreenSession(MODULE_NAME, SCREENID);

    	Page<CN51SummaryVO> cn51SummaryVOs = null;
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

    	listCN51Session.removeCN51SummaryVOs();

    	errors = validateForm(listCN51Form, errors);
		
		if(errors != null && errors.size() > 0){
			
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}
    	
    	CN51SummaryFilterVO cn51SummaryFilterVO = new CN51SummaryFilterVO();
    	cn51SummaryFilterVO.setCompanyCode(
    			getApplicationSession().getLogonVO().getCompanyCode());
    	cn51SummaryFilterVO.setFromDate	(convertToDate(listCN51Form.getListcn51frmdat()));
    	cn51SummaryFilterVO.setToDate(convertToDate(listCN51Form.getListcn51todat()));
    	cn51SummaryFilterVO.setGpaCode(listCN51Form.getGpacode());
    	cn51SummaryFilterVO.setPageNumber(Integer.parseInt(listCN51Form.getDisplayPage()));
    	try {
			cn51SummaryVOs = new MailTrackingMRADelegate().findAllInvoices(cn51SummaryFilterVO);

    	} catch (BusinessDelegateException e) {
			errors.addAll(handleDelegateException(e));

		}

    	if(cn51SummaryVOs != null && cn51SummaryVOs.size() > 0){

    		listCN51Form.setViewFlag("Y");
    		listCN51Session.setCN51SummaryVOs(cn51SummaryVOs);
    		invocationContext.target = LIST_SUCCESS;

    	}else{

    		errors.add(new ErrorVO(KEY_NO_RESULTS_FOUND));
    		invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;

    	}


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
	private  Collection<ErrorVO> validateForm(ListCN51Form form,
			Collection<ErrorVO> errors){
		
		
		if(BLANK.equals(form.getListcn51frmdat()) || BLANK.equals(form.getListcn51todat())){
			
			errors.add(new ErrorVO(ERROR_KEY_DATE));
			
		}else if(!validateDate(form.getListcn51frmdat(), form.getListcn51todat())){
			
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
				((fromDate != null) &&(fromDate.trim().length()>0))) {
			
			return DateUtilities.isLessThan( fromDate, toDate, LocalDate.CALENDAR_DATE_FORMAT );
			
		}else{
			
			return true;
		}
	}

}
