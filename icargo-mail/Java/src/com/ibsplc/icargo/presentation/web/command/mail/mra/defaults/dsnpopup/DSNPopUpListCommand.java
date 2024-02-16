/*
 * DSNPopUpListCommand.java Created on AUG 28, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.dsnpopup;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DSNPopUpForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 *
 */
public class DSNPopUpListCommand extends BaseCommand{
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private Log log = LogFactory.getLogger(	"MRA DespatchLOV ListCommand");
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.dsnselectpopup";
	private static final String LIST_FAILURE = "list_failure";
	
	private static final String KEY_NO_RESULTS_FOUND = "mailtracking.mra.defaults.dsnselectpopup.nodatafound";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 log.entering("DespatchLOV","execute");
		 ApplicationSessionImpl applicationSession = getApplicationSession();
		 LogonAttributes logonAttributes = applicationSession.getLogonVO();
		 DSNPopUpSession session=getScreenSession(MODULE_NAME,SCREEN_ID);
		 session.removeDespatchDetails();
		String companyCode=logonAttributes.getCompanyCode();
		MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
		Page<DSNPopUpVO> despatchLovVOs=null;	
		DSNPopUpForm form=(DSNPopUpForm)invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String dsnNum="";
		String dsnDate="";
		String fromScreen="";
		if(form.getCode()!=null && form.getCode().trim().length()>0){
			/*
			 * Code Length can be a dynamic value.
			 * It can be a 4 digit DSN number or a 20 character Billing Basis.
			 * In both the case we need to take the dsn number,when billing basis 
			 * is given,then take the last four digits,that represents the dsn.
			 */
			int codeLength = form.getCode().length();
			if(codeLength == 4) {
				dsnNum=form.getCode().toUpperCase();
				form.setCode(dsnNum);
			}else if(codeLength == 20){
				dsnNum=form.getCode().toUpperCase().substring(16, 20);
				form.setCode(dsnNum);
			}else if(codeLength==29){
				dsnNum=form.getCode().toUpperCase().substring(16, 20);//Modified for ICRD-100236
				form.setCode(dsnNum);
			}
		}
		if(form.getDsnFilterDate()!=null && form.getDsnFilterDate().trim().length()>0){
			dsnDate=form.getDsnFilterDate().toUpperCase();
		}		
		if(form.getFromPage() != null && form.getFromPage().trim().length() > 0){
			fromScreen = form.getFromPage();
		}
		int pageno = 1;
		if(form.getDisplayPage() != null && form.getDisplayPage().length() > 0) {
			pageno = Integer.parseInt(form.getDisplayPage());
		}
		
		DSNPopUpFilterVO dsnPopUpFilterVO = session.getDsnPopUpFilterDetails();
		if(dsnPopUpFilterVO != null) {
			companyCode = dsnPopUpFilterVO.getCompanyCode();
			if(dsnNum != null && dsnNum.length() == 0){
				dsnNum = dsnPopUpFilterVO.getDsn();
				if(dsnNum != null && dsnNum.length() != 0){
					form.setCode(dsnNum);
					int codeLength = form.getCode().length();
					if(codeLength == 4) {
						dsnNum=form.getCode().toUpperCase();
						form.setCode(dsnNum);
					}else if(codeLength == 20){
						dsnNum=form.getCode().toUpperCase().substring(16, 20);
						form.setCode(dsnNum);
					}else if(codeLength == 29){
						//dsnNum=form.getCode().toUpperCase().substring(16, 20);
						form.setCode(dsnNum);
					}
				}
			}
			if(dsnDate != null && dsnDate.length() == 0){
				dsnDate = dsnPopUpFilterVO.getDsnDate();
				if(dsnDate !=null && dsnDate.length() != 0){
					form.setDsnFilterDate(dsnDate);				
				}
			}
			if(fromScreen !=null && fromScreen.length() == 0){
				fromScreen = dsnPopUpFilterVO.getFromScreen();
				if(fromScreen !=null && fromScreen.length() != 0){
					form.setFromPage(fromScreen);
				}
			}
			pageno = dsnPopUpFilterVO.getPageNumber();
		}		
		
		try{
			despatchLovVOs=delegate.findDsnSelectLov(companyCode,dsnNum,dsnDate,pageno);
		}
		catch(BusinessDelegateException businessDelegateException){
			errors=handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "despatchLovVOs ", despatchLovVOs);
		if(despatchLovVOs!=null && despatchLovVOs.getActualPageSize()>0){
			session.setDespatchDetails(despatchLovVOs);
			session.removeDsnPopUpFilterDetails();
		}
		else{
			log.log(Log.INFO,"despatchLovVOs null");
			errors=new ArrayList<ErrorVO>();
			errors.add(new ErrorVO(KEY_NO_RESULTS_FOUND));
			invocationContext.addAllError(errors);
			session.removeDespatchDetails();
			invocationContext.target = SCREENLOAD_SUCCESS;
			return;
			
		}
		  if( errors != null && errors.size() > 0 ){
	        	invocationContext.addAllError(errors);
	        }
		 invocationContext.target=SCREENLOAD_SUCCESS;
			log.exiting("DespatchLOV","execute");
			
	}
}

