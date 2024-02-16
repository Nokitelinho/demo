/*
 * DSNokCommand.java Created on AUG 28, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.dsnpopup;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DSNPopUpForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-2391
 *
 */
public class DSNokCommand  extends BaseCommand{
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.dsnselectpopup";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private Log log = LogFactory.getLogger(	"MRA DespatchLOV OK COMMAND");
	private static final String OK_FAILURE = "ok_failure";	
	private static final String FROM_DSNROUTING = "despatchrouting";	
	private static final String KEY_RESULTS_NOT_FOUND = "mailtracking.mra.defaults.dsnselectpopup.nodetailsdatafound";
	private static final String MAINTAINCCA = "MAINTAIN_CCA";	
	/**
	 * Screen ID
	 */
	private static final String MAINTAINCCA_SCREEN = "mailtracking.mra.defaults.maintaincca";

	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {
		log.log(Log.INFO,"OK COMMAND ENTRY");
		 DSNPopUpSession session=getScreenSession(MODULE_NAME,SCREEN_ID);
		 MaintainCCASession maintainCCASession = 
				(MaintainCCASession) getScreenSession(MODULE_NAME, MAINTAINCCA_SCREEN);
		 DSNPopUpForm form=(DSNPopUpForm)invocationContext.screenModel;
		 ArrayList<DSNPopUpVO> dSNPopUpVOs=null;
		 Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		 if(session.getDespatchDetails()!=null && session.getDespatchDetails().size()>0){
			 dSNPopUpVOs=new ArrayList<DSNPopUpVO>(session.getDespatchDetails());
	   		}
		 log.log(Log.INFO,"dSNPopUpVOs obtained");
		 String index[]=null;
		 log.log(Log.INFO, "form.getCheck() ", form.getCheck());
			if(form.getCheck()!=null ){
	   		 log.log(Log.INFO,"form.getCheck() not null");
	   			index=form.getCheck();
	   		}
	   		for(int i=0;i<index.length;i++)
			{
	   			log.log(Log.INFO,"inside loop");
				int ind=Integer.parseInt(index[i]);
				DSNPopUpVO dSNPopUpVO=null;
				dSNPopUpVO=dSNPopUpVOs.get(ind);
				session.setSelectedDespatchDetails(dSNPopUpVO);
				if((dSNPopUpVO.getBillingDetailsCount()==0) && 
						(!(FROM_DSNROUTING.equals(form.getFromPage())))) {
					log.log(Log.INFO," NO ENTRY IN BILLING DETAILS TABLE : ERROR IN IMPORTING DATA");
					errors=new ArrayList<ErrorVO>();
					errors.add(new ErrorVO(KEY_RESULTS_NOT_FOUND));
					invocationContext.addAllError(errors);
					invocationContext.target = OK_FAILURE;
					return;
				}
				log.log(Log.INFO, "inside loop ", session.getSelectedDespatchDetails());
			}
	   		/*
	   		 * To Control the DSN POPUP in MAINTAIN CCA Screen
	   		 */
	   		if(MAINTAINCCA.equals(form.getFromPage())){
	   			maintainCCASession.setPopupFlag("Y");
	   		}
	   		form.setOkFlag("OK");
	   		session.removeDespatchDetails();
	   		session.removeDsnPopUpFilterDetails();
	   	 invocationContext.target=SCREENLOAD_SUCCESS;
	 	log.log(Log.INFO,"OK COMMAND EXIT");
	}

}
