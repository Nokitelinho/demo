/*
 * ChangeStatusCommand.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.billingentries;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingEntriesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingEntriesForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class ChangeStatusCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("GPABillingEntries ScreenloadCommand");

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
    	log.entering("ChangeStatusCommand","execute");
    	GPABillingEntriesForm form=(GPABillingEntriesForm)invocationContext.screenModel;
    	GPABillingEntriesSession session=null;
   		session=(GPABillingEntriesSession) getScreenSession(MODULE_NAME,SCREEN_ID);
    	String popDespatchNumber="";

    	String dsns=form.getDespatchNumbers();
    	String select=form.getSelect();
    	String[] selectArray=select.split(",");
    	/*StringTokenizer tok = new StringTokenizer(select,",");

    	String[] selectArray=new String[session.getGpaBillingDetails().size()];
    	int count=0;
    	while(tok.hasMoreTokens()){

    		selectArray[count] = tok.nextToken();
			count++;
		}*/
    	//String[] status=form.getSaveBillingStatus();
    	session.setSelectedRows(selectArray);

    	/*StringTokenizer destok = new StringTokenizer(dsns,",");
    	String[] despatchNumbers=new String[session.getGpaBillingDetails().size()];
    	int num=0;
    	while(destok.hasMoreTokens()){

    		despatchNumbers[num] = destok.nextToken();
    		num++;
		}*/
    	String[] despatchNumbers=dsns.split(",");

    	if(despatchNumbers!=null && despatchNumbers.length>0){
    		StringBuilder sbul=new StringBuilder(despatchNumbers[0]);
    		//System.out.println("length"+despatchNumbers.length);
    		//System.out.println("inside");
    		for(int i=1;i<despatchNumbers.length;i++){
    			if(despatchNumbers[i]!=null && despatchNumbers[i].trim().length()>0){
    				sbul=sbul.append(",").append(despatchNumbers[i]);

    			//popDespatchNumber=popDespatchNumber+despatchNumbers[i]+",";

    			}
    		}
    		popDespatchNumber=sbul.toString();
    		//System.out.println("popdes"+popDespatchNumber);
    		form.setPopupDespatchNumber(popDespatchNumber);
    	}







    	invocationContext.target = ACTION_SUCCESS;
		log.exiting("ChangeStatusCommand", "execute");

    }

}
