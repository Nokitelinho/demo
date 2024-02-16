/*
 * ViewAccountCommand.java Created on May 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.viewflownmail;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.cra.accounting.vo.AccountingFilterVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.DSNForFlownSegmentVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.MailBagForFlownSegmentVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.cra.accounting.ListAccountingEntriesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.ViewFlownMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.ViewFlownMailForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2270
 *
 */
public class ViewAccountCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA FLOWN");

	private static final String CLASS_NAME = "ViewAccountCommand";

	private static final String MODULE_NAME = "mailtracking.mra.flown";

	private static final String SCREENID = "mra.flown.viewflownmail";

	
	/*
	 * Strings for SCREEN_ID and MODULE_NAME of ListAccountingEntries
	 */
	private static final String LISTACCOUNTING_SCREENID = "cra.accounting.listaccountingentries";
	
	private static final String LISTACCOUNTING_MODULENAME = "cra.accounting";

	private static final String VIEW_SUCCESS = "view_success";

	private static final String VIEW_FAILURE = "view_failure";

	private static final String KEY_NO_ROW_SELECTED = "mailtracking.mra.flown.viewflownmail.norowselected";

	private static final String BLANK = "";
	
	private static final String FROM_VIEWFLOWNMAIL = "frm_viewflownmail";
	
	private static final String FUNCTION_PT_FLOWNMAIL = "FM";
	/**
	 * Method to implement passing of selected vo to details screen
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");
    	
    	ViewFlownMailForm form =
			(ViewFlownMailForm)invocationContext.screenModel;

    	ViewFlownMailSession session = 
			(ViewFlownMailSession)getScreenSession(MODULE_NAME, SCREENID);
    	
    	AccountingFilterVO accountingFilterVO = new AccountingFilterVO();
    	
    	String mailBillingBasis = "";
    	
    	DSNForFlownSegmentVO dSNForFlownSegmentVO = null;
    	MailBagForFlownSegmentVO mailBagForFlownSegmentVO = null;
    	FlownMailSegmentVO flownMailSegmentVO = session.getFlownMailSegmentVO();
    	FlownMailFilterVO filterVO=null;
    	if(session.getListFilterDetails()!=null){
    		filterVO=session.getListFilterDetails();
    	}
    	Collection<DSNForFlownSegmentVO> dSNForFlownSegmentVOs = flownMailSegmentVO.getSegmentDSNs();
    	Collection<MailBagForFlownSegmentVO> mailBagForFlownSegmentVOs =flownMailSegmentVO.getSegmentMailBags(); 
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

    	String[] rowId = form.getRowCount();
    	String selectedId = null;
    	String selectedMailBag = null;
    	String[] selectedRows=form.getSelectedRow();
    	if((dSNForFlownSegmentVOs != null && dSNForFlownSegmentVOs.size()>0
    			||mailBagForFlownSegmentVOs!=null && mailBagForFlownSegmentVOs.size()>0)){
    		if(rowId != null ){
    			for(int i=0; i < rowId.length; i++){
        			selectedId = rowId[i];
        		}
    		}
    		if(rowId != null && dSNForFlownSegmentVOs != null && dSNForFlownSegmentVOs.size()>0){
    			dSNForFlownSegmentVO = ((ArrayList<DSNForFlownSegmentVO>)dSNForFlownSegmentVOs).get(
						Integer.parseInt( selectedId ));
    			log.log(Log.INFO, "selected dSNForFlownSegmentVO-->",
						dSNForFlownSegmentVO);
					//mailBillingBasis = new StringBuilder().append(dSNForFlownSegmentVO.getYear());
    			    mailBillingBasis = new StringBuilder().append(
    					               dSNForFlownSegmentVO.getOriginOfficeOfExchange()).append(
    							       dSNForFlownSegmentVO.getDestinationOfficeOfExchange()).append(
    								   dSNForFlownSegmentVO.getMailCategoryCode()).append(
    								   dSNForFlownSegmentVO.getMailSubclass()).append(
    								   String.valueOf(dSNForFlownSegmentVO.getYear())).append(
    								   dSNForFlownSegmentVO.getDsnNumber()).toString();
    			    log.log(Log.INFO, "mailBillingBasis-->", mailBillingBasis);
				//accountingFilterVO.setMailBillingBasis(mailBillingBasis);
    			accountingFilterVO.setMasterDocumentNumber(dSNForFlownSegmentVO.getDsnNumber());
    			accountingFilterVO.setFunctionPoint(FUNCTION_PT_FLOWNMAIL);
    			accountingFilterVO.setSubSystem("M");
    			accountingFilterVO.setFltnum(dSNForFlownSegmentVO.getFlightNumber());
    			accountingFilterVO.setFltcarIdr(dSNForFlownSegmentVO.getFlightCarrierId());
    			accountingFilterVO.setFltcarCode(filterVO.getFlightCarrierCode());
    			accountingFilterVO.setFltseqnum(dSNForFlownSegmentVO.getFlightSequenceNumber());
    			log.log(Log.INFO, "accountingFilterVO-->", accountingFilterVO);
    		}
    		
    		if(selectedRows != null && mailBagForFlownSegmentVOs!=null && mailBagForFlownSegmentVOs.size()>0){
    			for(int i=0; i < selectedRows.length; i++){
        			selectedMailBag = selectedRows[i];
        		}
    			mailBagForFlownSegmentVO = ((ArrayList<MailBagForFlownSegmentVO>)mailBagForFlownSegmentVOs).get(
						Integer.parseInt( selectedMailBag ));
    			log.log(Log.INFO, "selected mailBagForFlownSegmentVO-->",
						mailBagForFlownSegmentVO);
				String actualMailSubClass = "";
    			if(mailBagForFlownSegmentVO.getMailSubclass().startsWith("C")){
    				actualMailSubClass = "CP";
    			}else{
    				actualMailSubClass = "LC";
    			}
    			mailBillingBasis = new StringBuilder().append(
			              mailBagForFlownSegmentVO.getOriginOfficeOfExchange()).append(
			              mailBagForFlownSegmentVO.getDestinationOfficeOfExchange()).append(
			            		  mailBagForFlownSegmentVO.getMailCategoryCode()).append(
			            				     actualMailSubClass).append(
			            								  mailBagForFlownSegmentVO.getYear()).append(
			            										  mailBagForFlownSegmentVO.getDsnNumber()).toString();
    			log.log(Log.INFO, "mailIdentifier-->", mailBillingBasis);
				/**
    			 * corrected by @author a-3447 for Bug 27215 starts 
    			 */
    			accountingFilterVO.setMasterDocumentNumber( mailBagForFlownSegmentVO.getDsnNumber());
    			
    			/**
    			 * corrected by @author a-3447 for Bug 27215 Ends 
    			 */
    			
    			accountingFilterVO.setFunctionPoint(FUNCTION_PT_FLOWNMAIL);
    			accountingFilterVO.setSubSystem("M");
    			accountingFilterVO.setFltnum(mailBagForFlownSegmentVO.getFlightNumber());
    			accountingFilterVO.setFltcarIdr(mailBagForFlownSegmentVO.getFlightCarrierId());
    			accountingFilterVO.setFltcarCode(filterVO.getFlightCarrierCode());
    			accountingFilterVO.setFltseqnum(mailBagForFlownSegmentVO.getFlightSequenceNumber());
    			//accountingFilterVO.setSegsernum(mailBagForFlownSegmentVO.getSegmentSerialNumber());
    		}
    		
    		FlownMailFilterVO flownMailFilterVO = populateFilterVO(form);
    		session.setFilter(flownMailFilterVO);
    		
    		
    		ListAccountingEntriesSession accountingEntrySession 
    						= getScreenSession(LISTACCOUNTING_MODULENAME, LISTACCOUNTING_SCREENID);
		      accountingEntrySession.setAccountingFilterVO(accountingFilterVO);
		          accountingEntrySession.setParentScreenFlag(FROM_VIEWFLOWNMAIL);
          invocationContext.target = VIEW_SUCCESS;

    	}else{
    		errors.add(new ErrorVO(KEY_NO_ROW_SELECTED));
        	invocationContext.addAllError(errors);
        	invocationContext.target = VIEW_FAILURE;
    	}

    	return;
    }

    /**
     * populates FlownMailFilterVO from selected cn51SummaryVO
     * @param viewFlownMailForm
     * @return FlownMailFilterVO
     */
    private FlownMailFilterVO populateFilterVO(ViewFlownMailForm viewFlownMailForm){

    	FlownMailFilterVO flownMailFilterVO = new FlownMailFilterVO();
    	flownMailFilterVO.setStringFlightDate(viewFlownMailForm.getFlightDate().toString());
    	flownMailFilterVO.setFlightDate(convertToDate(viewFlownMailForm.getFlightDate()));
    	flownMailFilterVO.setFlightNumber(viewFlownMailForm.getFlightNumber());
    	flownMailFilterVO.setFlightCarrierCode(viewFlownMailForm.getCarrierCode());
    	return flownMailFilterVO;
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

}

