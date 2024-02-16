/*
 * ViewCN51Command.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpabilling.ListCN51SessionImpl;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListCN51CN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListCN51Form;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class ViewCN51Command extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA GPABILLING");

	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51s";

	private static final String SCREENID_CN51CN66 = "mailtracking.mra.gpabilling.listcn51cn66";

	private static final String VIEW_SUCCESS = "view_success";

	private static final String VIEW_FAILURE = "view_failure";

	private static final String KEY_NO_ROW_SELECTED = "mailtracking.mra.gpabilling.norowselected";

	private static final String BLANK = "";
	
	private static final String FROM_CN51_SCREEN = "ListCN51s_Screen";
	/**
	 * Method to implement passing of selected vo to details screen
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

    	Page<CN51SummaryVO> cn51SummaryVOs = listCN51Session.getCN51SummaryVOs();
    	CN51SummaryVO cn51SummaryVO = null;
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

    	String[] rowId = listCN51Form.getRowCount();
    	String selectedId = null;

    	if(rowId != null && cn51SummaryVOs != null){

    		for(int i=0; i < rowId.length; i++){
    			selectedId = rowId[i];
    		}

    		cn51SummaryVO =	((Page<CN51SummaryVO>)cn51SummaryVOs).get(
    								Integer.parseInt( selectedId ));
    		log.log(Log.INFO, "selected cn51SummaryVO-->", cn51SummaryVO);
			ListCN51CN66Session listCN51CN66Session =
    			(ListCN51CN66Session)getScreenSession(MODULE_NAME, SCREENID_CN51CN66);

    		// getting filter vo to pass to CN51CN66 screen
    		CN51CN66FilterVO cn51CN66FilterVO = populateFilterVO(cn51SummaryVO);
    		log.log(Log.INFO, "selected cn51CN66FilterVO-->", cn51CN66FilterVO);
			// setting cn51CN66FilterVO in ListCN51CN66 screen session
    		listCN51CN66Session.setCN51CN66FilterVO(cn51CN66FilterVO);
    		listCN51CN66Session.setParentScreenFlag(FROM_CN51_SCREEN);

    		// setting CN51SummaryFilterVO in this screen session for
    		// showing filter values after comming back from ListCN51CN66
    		listCN51Session.setCN51SummaryFilterVO(populateCN51FilterVO(listCN51Form));

    		invocationContext.target = VIEW_SUCCESS;

    	}else{

    		errors.add(new ErrorVO(KEY_NO_ROW_SELECTED));
        	invocationContext.addAllError(errors);
        	invocationContext.target = VIEW_FAILURE;
    	}

    	return;
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

    /**
     * populates CN51SummaryFilterVO from form
     * @param listCN51Form
     * @return
     */
    private CN51SummaryFilterVO populateCN51FilterVO(ListCN51Form listCN51Form){

    	CN51SummaryFilterVO cn51SummaryFilterVO = new CN51SummaryFilterVO();
    	cn51SummaryFilterVO.setCompanyCode(
    			getApplicationSession().getLogonVO().getCompanyCode());
    	cn51SummaryFilterVO.setFromDate	(convertToDate(listCN51Form.getListcn51frmdat()));
    	cn51SummaryFilterVO.setToDate(convertToDate(listCN51Form.getListcn51todat()));
    	cn51SummaryFilterVO.setGpaCode(listCN51Form.getGpacode());

    	return cn51SummaryFilterVO;
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
