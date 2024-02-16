/*
 * DeleteAttachmentCommand created on July 16, 2012 
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.rejectionmemo;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SisSupportingDocumentDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.RejectionMemoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.RejectionMemoForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-8061
 *
 */
public class DeleteAttachmentCommand extends BaseCommand {

	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD REJECTION MEMO");
	private static final String CLASS_NAME = "DeleteAttachmentCommand";
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.rejectionmemo";
	private static final String DELETE_SUCCESS = "deleteattachment_success";
	 private static final String INT_BLGTYPE = "O";
	 private static final String BLANK = "";
	
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.entering(CLASS_NAME, "execute");
		RejectionMemoForm rejectionMemoForm = (RejectionMemoForm) invocationContext.screenModel;

		RejectionMemoSession session = (RejectionMemoSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);

		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
		String[] selectedRows=rejectionMemoForm.getFileNameCheck();  
		
		RejectionMemoVO rejectionMemoVO  = session.getRejectionMemoVO();
		
		
	
		Collection<SisSupportingDocumentDetailsVO> supportingVOs = rejectionMemoVO
																		.getSisSupportingDocumentDetailsVOs();
		Collection<SisSupportingDocumentDetailsVO> supportingVOsToDelete = new ArrayList<SisSupportingDocumentDetailsVO>();
		
		if(supportingVOs!=null && supportingVOs.size() >0 ){
			int index = 0;
			int countToDelete = supportingVOs.size();
			for(String str:selectedRows){
				index = Integer.parseInt(str);
				log.log(Log.INFO, "\n \n \n The index-->", index);
				SisSupportingDocumentDetailsVO  supportingVO = ((ArrayList<SisSupportingDocumentDetailsVO>)supportingVOs).get(index);
				
				if(rejectionMemoVO.getMemoCode()!=null && !BLANK.equals(rejectionMemoVO.getMemoCode())){
					countToDelete--;
					supportingVO.setInterlineBillingType(INT_BLGTYPE);
			
					
					if(countToDelete==0){
						supportingVO.setAttachmentIndicator(SisSupportingDocumentDetailsVO.FLAG_NO);
						supportingVO.setAttachmentStatus(SisSupportingDocumentDetailsVO.FLAG_NO);
						supportingVO.setFromAttachment(true);
						rejectionMemoVO.setAttachmentIndicator(SisSupportingDocumentDetailsVO.FLAG_NO);
					}
					else{
						supportingVO.setAttachmentIndicator(SisSupportingDocumentDetailsVO.FLAG_YES);
						rejectionMemoVO.setAttachmentIndicator(SisSupportingDocumentDetailsVO.FLAG_YES);
					}
					supportingVO.setStatus(rejectionMemoVO.getMemoStatus());
					supportingVO.setOperationFlag(OPERATION_FLAG_DELETE);
					
					supportingVOsToDelete.add(supportingVO);
									
				 }
				else{
					
						if((OPERATION_FLAG_INSERT).equals(supportingVO.getOperationFlag())){
						countToDelete--;
							supportingVOs.remove(supportingVO); 
						}
						else{
						countToDelete--;
							supportingVO.setInterlineBillingType(INT_BLGTYPE);
							/*if(awbInMemoVO!=null){
							supportingVO.setBillingMode(awbInMemoVO.getBillingMode());
							}*/
						if(countToDelete==0){
							supportingVO.setAttachmentIndicator(SisSupportingDocumentDetailsVO.FLAG_NO);
							supportingVO.setAttachmentStatus(SisSupportingDocumentDetailsVO.FLAG_NO);
							supportingVO.setFromAttachment(true);
							rejectionMemoVO.setAttachmentIndicator(SisSupportingDocumentDetailsVO.FLAG_NO);
						}
						else{
							supportingVO.setAttachmentIndicator(SisSupportingDocumentDetailsVO.FLAG_YES);
							rejectionMemoVO.setAttachmentIndicator(SisSupportingDocumentDetailsVO.FLAG_YES);
						}
							supportingVO.setStatus(rejectionMemoVO.getMemoStatus());
							supportingVO.setOperationFlag(OPERATION_FLAG_DELETE);
					}
				}
						
			}

			
		}
		supportingVOs.removeAll(supportingVOsToDelete); 
		rejectionMemoVO.setSisSupportingDocumentDetailsVOs(supportingVOs);
		
		log.log(Log.FINE, "The SupportingDocVOsToDelete",supportingVOsToDelete);
		if(rejectionMemoVO.getMemoCode()!=null && !BLANK.equals(rejectionMemoVO.getMemoCode())){
		try {
					delegate.removeSupportingDocumentDetails(supportingVOsToDelete);
			
		} catch(BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"inside  businessDelegateException");
			
		   	}
	   	}

		invocationContext.target = DELETE_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
		
		
	}

}
