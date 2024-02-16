/*
 * DeleteExceptionCommand Created on NOV 26, 2006
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmaildetails;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailDetailsForm;

/**
 * @author a-3817
 *
 */
public class DeleteExceptionCommand extends BaseCommand {
	//private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.batchmailupload";
	private static final String DELETE_EXCEPTION_SUCCESS = "delete_exception_success";
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		UploadMailDetailsForm uploadMailDetailsForm = (UploadMailDetailsForm)invocationContext.screenModel;
		UploadMailDetailsSession uploadMailDetailsSession = getScreenSession(MODULE_NAME, SCREEN_ID) ;
		String [] selectedExceptions = uploadMailDetailsForm.getSelectedExceptions();
		ScannedMailDetailsVO selectedScannedMailDetailsVO = uploadMailDetailsSession.getSelectedScannedMailDetailsVO();
		Collection<ScannedMailDetailsVO> scannedMailDetailsVOs = uploadMailDetailsSession.getScannedMailDetailsVOs();
		if(selectedScannedMailDetailsVO != null){
			String iteratingSessionKey = null;
			String selectedSessionKey = new StringBuilder(selectedScannedMailDetailsVO.getProcessPoint())
			.append(selectedScannedMailDetailsVO.getCarrierCode())
			.append(selectedScannedMailDetailsVO.getFlightNumber())
			.append(selectedScannedMailDetailsVO.getFlightDate())
			.append(selectedScannedMailDetailsVO.getDestination())
			.append(selectedScannedMailDetailsVO.getContainerNumber()).toString();
			if(scannedMailDetailsVOs != null && scannedMailDetailsVOs.size()  >0 ){
				for(ScannedMailDetailsVO detailsVO : scannedMailDetailsVOs){
					iteratingSessionKey = new StringBuilder(detailsVO
							.getProcessPoint()).append(
									detailsVO.getCarrierCode()).append(
											detailsVO.getFlightNumber()).append(
													detailsVO.getFlightDate()).append(
															detailsVO.getDestination()).append(
																	detailsVO.getContainerNumber()).toString();
					if(iteratingSessionKey.equals(selectedSessionKey)){
						deleteMailExceptions(uploadMailDetailsForm, detailsVO, uploadMailDetailsSession);
						break;
					}
				}
			}
		}
		invocationContext.target = DELETE_EXCEPTION_SUCCESS;

	}

	private void deleteMailExceptions(
			UploadMailDetailsForm uploadMailDetailsForm,
			ScannedMailDetailsVO detailsVO,
			UploadMailDetailsSession uploadMailDetailsSession) {
		Collection<MailbagVO> mailExceptionVOs = detailsVO.getErrorMailDetails();
		String [] selectedRows = uploadMailDetailsForm.getSelectedExceptions();
		Collection<MailbagVO> deletedMailBagVOs=new ArrayList<MailbagVO>();
		if(selectedRows != null && selectedRows.length >0){
			for (int i=0;i<selectedRows.length;i++){
				//((ArrayList<MailbagVO>)mailExceptionVOs).remove(Integer.parseInt(selectedRows[i]));
				MailbagVO deletedMailBagVO = ((ArrayList<MailbagVO>)mailExceptionVOs).get(Integer.parseInt(selectedRows[i]));
				deletedMailBagVOs.add(deletedMailBagVO);
			}
			mailExceptionVOs.removeAll(deletedMailBagVOs);
			detailsVO.setDeletedExceptionBagCout(detailsVO.getDeletedExceptionBagCout()+ selectedRows.length);
		}
	}

}
