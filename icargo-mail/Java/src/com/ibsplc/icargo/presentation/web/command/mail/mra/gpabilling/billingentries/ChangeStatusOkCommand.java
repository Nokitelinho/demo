/*
 * ChangeStatusOkCommand.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.billingentries;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingEntriesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingEntriesForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class ChangeStatusOkCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("GPABillingEntries ChangeStatusOk");

	//private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.billingentries.listgpabillingentries";
	private static final String ACTION_SUCCESS = "screenload_success";

	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ChangeStatusOkCommand","execute");
    	GPABillingEntriesForm form=(GPABillingEntriesForm)invocationContext.screenModel;
    	 GPABillingEntriesSession session=null;
	   		session=(GPABillingEntriesSession) getScreenSession(MODULE_NAME,SCREEN_ID);
	   		Page<DocumentBillingDetailsVO> gpaBillingVos=session.getGpaBillingDetails();

    	String[] select=session.getSelectedRows();
    	//System.out.println("no of selected rows"+select.length);
    	form.setScreenStatus("ok");
    	if(select!=null && select.length>0){
    		form.setScreenStatus("ok");
    	for(int i=0;i<select.length;i++){
    		//System.out.println("inside for");
    		if(form.getPopupBillingStatus()!=null && form.getPopupBillingStatus().trim().length()>0){

    			if(select[i]!=null && select[i].trim().length()>0 ){

    				gpaBillingVos.get(Integer.parseInt(select[i])).setBillingStatus(form.getPopupBillingStatus());


    			}

    		}
    		if(form.getPopupRemarks()!=null && form.getPopupRemarks().trim().length()>0){

    			if(select[i]!=null && select[i].trim().length()>0 ) {
					gpaBillingVos.get(Integer.parseInt(select[i])).setRemarks(form.getPopupRemarks());
				}
    		}
    	}
    	}
    	session.setGpaBillingDetails(gpaBillingVos);
    	invocationContext.target = ACTION_SUCCESS;
		log.exiting("ChangeStatusCommandOk", "execute");

    }

}
