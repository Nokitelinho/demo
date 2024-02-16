/*
 * ViewProrateCommand.java Created on Aug 21, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.listinterlinebillingentries;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRAViewProrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 *
 */
public class ViewProrateCommand extends BaseCommand{

	private  Log log = LogFactory.getLogger("MRA AIRLINEBILLING");

	private static final String CLASS_NAME = "ViewProrateCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	private static final String VIEW_MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREENID = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";
	private static final String  VIEW_SCREENID = "mailtracking.mra.defaults.mailproration";
	private static final String VIEW_SUCCESS = "viewprorate_success";
	private static final String SCREENID_VIEW_PRORATION ="mailtracking.mra.defaults.viewproration";
	private static final String MODULE = "mailtracking.mra.defaults";

	

	/**
	 * Method to implement the view operation
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");

    	ListInterlineBillingEntriesForm  listInterlineBillingEntriesForm = 
    		(ListInterlineBillingEntriesForm)invocationContext.screenModel;

    	ListInterlineBillingEntriesSession listInterlineBillingEntriesSession =
    		(ListInterlineBillingEntriesSession)getScreenSession(MODULE_NAME, SCREENID);
    	listInterlineBillingEntriesSession.setFromScreen("fromInterLineBilling");
    	listInterlineBillingEntriesSession.setToScreen("fromInterLineBilling");
    	MRAViewProrationSession session = getScreenSession(MODULE,SCREENID_VIEW_PRORATION);
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
    	ProrationFilterVO prorationFilterVO =new ProrationFilterVO();
		String select= listInterlineBillingEntriesForm.getSelect();
		log.log(Log.FINE, "Select : ", listInterlineBillingEntriesForm.getSelect());
		Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs = listInterlineBillingEntriesSession
		.getDocumentBillingDetailVOs();
    	ArrayList<DocumentBillingDetailsVO> documentBillingDetailsVOArraylist = new ArrayList<DocumentBillingDetailsVO>(
    	documentBillingDetailsVOs);
    	DocumentBillingDetailsVO documentBillingDetailsVO;
    		
    	documentBillingDetailsVO= documentBillingDetailsVOArraylist.get(Integer.parseInt(select));
    	log
				.log(Log.FINE, "Inside viewCommand ... >>",
						documentBillingDetailsVO);
		prorationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
    	if(documentBillingDetailsVO.getCsgDocumentNumber()!=null){
    	prorationFilterVO.setConsigneeDocumentNumber(String.valueOf(documentBillingDetailsVO.getCsgDocumentNumber()));
    	}
    	if(documentBillingDetailsVO.getBillingBasis()!=null){
    	prorationFilterVO.setDespatchSerialNumber(documentBillingDetailsVO.getBillingBasis());
    	prorationFilterVO.setBillingBasis(documentBillingDetailsVO.getBillingBasis());
    	}
    	prorationFilterVO.setPoaCode(documentBillingDetailsVO.getPoaCode());
    	prorationFilterVO.setMailSquenceNumber(documentBillingDetailsVO.getMailSequenceNumber());
    	log.log(Log.FINE, "prorationFilterVO ... >>", prorationFilterVO);
		session.setProrationFilterVO(prorationFilterVO);
    	invocationContext.target = VIEW_SUCCESS;
    }


}
