/*
 * ViewCN51Command.java Created on AUG 02,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice;

/**
 * This command class InvoiceDetails
 * 
 */
/*
 * Revision History Revision Date Author Description
 * ==============================================================================
 * 0.1 01-08-2008 A-3447 For Coding
 * 
 * =============================================================================
 */

/**
 * @author A-3447
 * 
 */
import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
//import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListCN51CN66Session;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListGPABillingInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListGPABillingInvoiceForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author a-3447
 */

public class ViewCN51Command extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA GPABILLING");

	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.listgpabillinginvoice";
	
	private static final String SCREENID_CN51CN66 = "mailtracking.mra.gpabilling.listcn51cn66";

	private static final String VIEW_SUCCESS = "view_success";


	private static final String BLANK = "";
	
	private static final String FROM_CN51_SCREEN = "listinvoice";
	/**
	 * Method to implement passing of selected vo to details screen
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");

    	log.entering(CLASS_NAME,"execute");

   	 ListGPABillingInvoiceForm   listGPABillingInvoiceForm  = ( ListGPABillingInvoiceForm )invocationContext.screenModel;

   	 	ListGPABillingInvoiceSession listGPABillingInvoiceSession =
   		(ListGPABillingInvoiceSession)getScreenSession(MODULE_NAME, SCREENID);  	
       	 String counter = listGPABillingInvoiceForm.getSelectedRow();
		Collection<CN51SummaryVO> cN51SummaryVOs = listGPABillingInvoiceSession
				.getCN51SummaryVOs();
		ArrayList<CN51SummaryVO> cN51SummaryVOArraylist = new ArrayList<CN51SummaryVO>(
				cN51SummaryVOs);
		CN51SummaryVO cn51SummaryVO = null;
		log.log(Log.FINE, "counter--->>", counter);
		cn51SummaryVO = cN51SummaryVOArraylist.get(Integer.parseInt(counter));
		log.log(Log.FINE, "cN51SummaryVO selected-->>", cn51SummaryVO);
			ListCN51CN66Session listCN51CN66Session =
    			(ListCN51CN66Session)getScreenSession(MODULE_NAME, SCREENID_CN51CN66);

    		// getting filter vo to pass to CN51CN66 screen
    		CN51CN66FilterVO cn51CN66FilterVO = populateFilterVO(cn51SummaryVO);
    		log.log(Log.INFO, "selected cn51CN66FilterVO-->", cn51CN66FilterVO);
			// setting cn51CN66FilterVO in ListCN51CN66 screen session
    		listCN51CN66Session.setCN51CN66FilterVO(cn51CN66FilterVO);
    		listCN51CN66Session.setParentScreenFlag(FROM_CN51_SCREEN);
    		listCN51CN66Session.setSystemparametres(listGPABillingInvoiceSession.getSystemparametres());

    		// setting CN51SummaryFilterVO in this screen session for
    		// showing filter values after comming back from ListCN51CN66
    		

    		invocationContext.target = VIEW_SUCCESS;

    	}
    

    /**
     * populates cn51CN66FilterVO from selected cn51SummaryVO
     * @param cn51SummaryVO
     * @return
     */
    private CN51CN66FilterVO populateFilterVO(CN51SummaryVO cn51SummaryVO){

    	CN51CN66FilterVO cn51CN66FilterVO = new CN51CN66FilterVO();

    	cn51CN66FilterVO.setBillingPeriod(cn51SummaryVO.getBillingPeriod());
    	cn51CN66FilterVO.setCompanyCode(cn51SummaryVO.getCompanyCode());
    	cn51CN66FilterVO.setGpaCode(cn51SummaryVO.getGpaCode());
    	cn51CN66FilterVO.setInvoiceNumber(cn51SummaryVO.getInvoiceNumber());

    	return cn51CN66FilterVO;
    }
/*
    *//**
     * populates CN51SummaryFilterVO from form
     * @param listCN51Form
     * @return
     *//*
    private CN51SummaryFilterVO populateCN51FilterVO(ListGPABillingInvoiceForm listGPABillingInvoiceForm){

    	CN51SummaryFilterVO cn51SummaryFilterVO = new CN51SummaryFilterVO();
    	cn51SummaryFilterVO.setCompanyCode(
    			getApplicationSession().getLogonVO().getCompanyCode());
    	cn51SummaryFilterVO.setFromDate	(convertToDate(listGPABillingInvoiceForm.getFromDate()));
    	cn51SummaryFilterVO.setToDate(convertToDate(listGPABillingInvoiceForm.getToDate()));
    	cn51SummaryFilterVO.setGpaCode(listGPABillingInvoiceForm.getGpacode());

    	return cn51SummaryFilterVO;
    }*/

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
