/*
 * ViewAccountCommand.java Created on May 25, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.capturegpareport;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.cra.accounting.vo.AccountingFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.cra.accounting.ListAccountingEntriesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.CaptureGPAReportForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2270
 *
 */
public class ViewAccountCommand extends BaseCommand{
	
	
	private  Log log = LogFactory.getLogger("MRA GPAREPORTING");

	private static final String CLASS_NAME = "ViewAccountCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpareporting";

	private static final String SCREENID = "mailtracking.mra.gpareporting.capturegpareport";
	
	/*
	 * Strings for SCREEN_ID and MODULE_NAME
	 */
	private static final String LISTACCOUNTING_SCREENID = "cra.accounting.listaccountingentries";
	private static final String LISTACCOUNTING_MODULENAME = "cra.accounting";
	
		
	private static final String VIEW_SUCCESS = "view_success";
	private static final String VIEW_FAILURE = "view_failure";
	
	private static final String  KEY_NO_ROW_SELECTED = "mailtracking.mra.gpareporting.capturegpareport.norowssel";
	
	private static final String FROM_CAPTUREGPAREPORT = "frm_gpareportscreen";

	
	/**
	 * Method to implement passing of selected vo to details screen
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");
    	
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	
    	//GPAReportingDetailsVO gpaReportingDetailsVO = null;
    	
    	CaptureGPAReportForm form = 
			(CaptureGPAReportForm)invocationContext.screenModel;
    	
    	CaptureGPAReportSession session = 
			(CaptureGPAReportSession)getScreenSession(
													MODULE_NAME, SCREENID);	
    	//Page<GPAReportingDetailsVO> gpaReportingDetailsPage = session.getGPAReportingDetailsPage();
    	String[] rowId = form.getRowCount();
    	//String selectedId = null;
    	AccountingFilterVO accountingFilterVO = null;
    	
    	if(rowId!=null && form.getRowCount().length!=0){
			String selectedRow=form.getRowCount()[0];
			log.log(Log.INFO, "selectedRow========>>", selectedRow);
			int index=0;
			for(GPAReportingDetailsVO gPAReportingDetailsVO:session.getGPAReportingDetailsPage()){
				if(index==Integer.parseInt(selectedRow)){
					accountingFilterVO = new AccountingFilterVO();
					accountingFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
					accountingFilterVO.setMailBillingBasis(gPAReportingDetailsVO.getBillingBasis());
					accountingFilterVO.setFunctionPoint(AccountingFilterVO.FUNCTIONPOINT_GPAREPORTING);
					break;
				}
				index++;
			}
			ListAccountingEntriesSession accountingEntrySession 
			= getScreenSession(LISTACCOUNTING_MODULENAME, LISTACCOUNTING_SCREENID);
		      accountingEntrySession.setAccountingFilterVO(accountingFilterVO);
		          accountingEntrySession.setParentScreenFlag(FROM_CAPTUREGPAREPORT);
		          log.log(Log.FINE, "****AccountingFilterVo Set in Session*",
						accountingFilterVO);
			GPAReportingFilterVO gpaReportingFilterVO = populateFilterVO(form);
	    	
	    	session.setGPAReportingFilterVO(gpaReportingFilterVO);
	    	 log.log(Log.FINE, "****gpaReportingFilterVO Set in Session*",
					gpaReportingFilterVO);
			invocationContext.target = VIEW_SUCCESS;
		}else{
    		errors.add(new ErrorVO(KEY_NO_ROW_SELECTED));
        	invocationContext.addAllError(errors);
        	invocationContext.target = VIEW_FAILURE;
    	}
    		return;
    }

    private GPAReportingFilterVO populateFilterVO(CaptureGPAReportForm form){
    	GPAReportingFilterVO filterVO = new GPAReportingFilterVO();
    	filterVO.setCompanyCode(
    			getApplicationSession().getLogonVO().getCompanyCode());
    	filterVO.setReportingPeriodFrom(convertToDate(form.getFrmDate()));
    	filterVO.setReportingPeriodTo(convertToDate(form.getToDate()));
    	filterVO.setPoaCode(form.getGpaCode());
    	filterVO.setProcessedFlag(form.getAllProcessed());
    	return filterVO;
    	
    }
    
    /**
	 *
	 * @param date
	 * @return LocalDate
	 */
	private LocalDate convertToDate(String date){

		if(date!=null && !("").equals(date)){

			return(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate( date ));
		}
		return null;
	}
	
	

}
