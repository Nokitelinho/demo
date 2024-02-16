/*
 * SaveCommand.java Created on Dec 19, 2007
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.billingentries;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingStatusVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingEntriesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingEntriesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2408
 *
 */
public class SaveCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("GPABillingEntries ScreenloadCommand");

	private static final String CLASS_NAME = "SaveLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.billingentries.listgpabillingentries";
	private static final String NO_DATA_FOR_SAVE="mailtracking.mra.gpabilling.msg.err.nodataforsave";

	private static final String SAVE_SUCCESS = "save_success";

	private static final String SAVE_FAILURE = "save_failure";
	
	private static final String SAVE_SUCCESSMSG="mailtracking.mra.airlinebilling.msg.err.gpabillingsavesuccess";
	
	private static final String SCREEN_LOAD="screenload";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */ 
	public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 log.entering(CLASS_NAME, "execute");
		 GPABillingEntriesSession session=null;
		 ArrayList<GPABillingDetailsVO> gpaBillingDetailsVOs=null;
		 ErrorVO error=null;
			Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
			MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
	   		session=(GPABillingEntriesSession) getScreenSession(MODULE_NAME,SCREEN_ID);
	   		GPABillingEntriesForm form=(GPABillingEntriesForm)invocationContext.screenModel;
	   		String[] updatedRows=session.getSelectedRows();
	   		

	   		if(session.getGpaBillingDetails()!=null){
	   		 //gpaBillingDetailsVOs=new ArrayList<GPABillingDetailsVO>(session.getGpaBillingDetails());
	   		}
	   		Collection<GPABillingStatusVO> gpaBillingStatusVOs=new ArrayList<GPABillingStatusVO>();
	   		if(gpaBillingDetailsVOs!=null){
	   			
	   			//String status = "";
	   			//System.out.println("check inside if");
	   			for(GPABillingDetailsVO gpaBillingDetailsVO:gpaBillingDetailsVOs){
	   				GPABillingStatusVO gpaStatusVO=new GPABillingStatusVO();
	   				if(gpaBillingDetailsVO.getCompanyCode()!=null && gpaBillingDetailsVO.getCompanyCode().trim().length()>0){
	   					gpaStatusVO.setCompanyCode(gpaBillingDetailsVO.getCompanyCode());
	   				}
	   				if(gpaBillingDetailsVO.getGpaCode()!=null && gpaBillingDetailsVO.getGpaCode().trim().length()>0){
	   					gpaStatusVO.setGpaCode(gpaBillingDetailsVO.getGpaCode());
	   				}
	   				if(gpaBillingDetailsVO.getRemarks()!=null && gpaBillingDetailsVO.getRemarks().trim().length()>0){
	   					gpaStatusVO.setRemarks(gpaBillingDetailsVO.getRemarks());
	   				}
	   				if(gpaBillingDetailsVO.getBillingStatus()!=null && gpaBillingDetailsVO.getBillingStatus().trim().length()>0){
	   					//status=gpaBillingDetailsVO.getBillingStatus();
	   					gpaStatusVO.setStatus(gpaBillingDetailsVO.getBillingStatus());

	   				}
	   				if(gpaBillingDetailsVO.getSequenceNumber()!=0){
	   					gpaStatusVO.setSequenceNumber(gpaBillingDetailsVO.getSequenceNumber());
	   				}
	   				if(gpaBillingDetailsVO.getBillingBasis()!=null && gpaBillingDetailsVO.getBillingBasis().trim().length()>0){
	   					gpaStatusVO.setBillingBasis(gpaBillingDetailsVO.getBillingBasis());
	   				}
	   				gpaBillingStatusVOs.add(gpaStatusVO);
	   			}

	 }
	   		if(gpaBillingStatusVOs==null || gpaBillingStatusVOs.size()==0||updatedRows==null || updatedRows.length==0
	   			||("list".equals(form.getScreenStatus()))){

	   			error=new ErrorVO(NO_DATA_FOR_SAVE);
 				//error.setErrorDisplayType(ERROR);
 		   		errors.add(error);
	   		}


	   		if(errors!=null && errors.size()>0){
	   		 log.log(Log.FINE,"!!!inside errors!= null");
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
	   		}
	   		else{
	   			try{
	   				//System.out.println("save vos"+gpaBillingStatusVOs);
	   				delegate.changeBillingStatus(gpaBillingStatusVOs);
	   				
	   			}
	   			catch(BusinessDelegateException businessDelegateException ){
	   				log.log(Log.FINE, "\n \n Exception is -->",
							businessDelegateException);
					errors=handleDelegateException(businessDelegateException);


	   			}
	   		}
	  if(errors!=null && errors.size()>0){
		  invocationContext.addAllError(errors);
		  invocationContext.target = SAVE_FAILURE;
		  return;
	  }else{
		  session.removeGpaBillingDetails();
		   		session.removeSelectedRows();
		   		form.setFromDate("");
		   		form.setToDate("");
		   		form.setGpaCodeFilter("");
		   		form.setGpaName("");
		   		form.setCountry("");
		   		form.setStatus("");
		   		form.setScreenStatus("screenload");
		   		error=new ErrorVO(SAVE_SUCCESSMSG);
		   		errors.add(error);
		   		invocationContext.addAllError(errors);
	  }
	  session.removeSelectedRows();
	   		invocationContext.target = SAVE_SUCCESS;

	 }
}
