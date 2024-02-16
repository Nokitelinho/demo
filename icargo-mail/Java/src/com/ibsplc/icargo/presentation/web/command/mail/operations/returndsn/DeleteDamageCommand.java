/*
 * DeleteDamageCommand.java Created on July 24, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.returndsn;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReturnDsnSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReturnDsnForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1861
 *
 */
public class DeleteDamageCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "delete_success";
   private static final String TARGET_FAILURE = "delete_failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.returndsn";	
   
   private static final String OPERFLAG_INSERT_DELETE = "ID";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("DeleteDamageCommand","execute");
    	  
    	ReturnDsnForm returnDsnForm = 
    		(ReturnDsnForm)invocationContext.screenModel;
    	ReturnDsnSession returnDsnSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;
		
		Collection<DamagedDSNVO> damagedDsnVOs = returnDsnSession.getDamagedDsnVOs();
		
		int currentVoIndex = returnDsnForm.getCurrentPage();
		log.log(Log.INFO, "currentpage:-->", currentVoIndex);
		// deleting the selected vos 
		int index = 1;
		Collection<DamagedDSNDetailVO> damagedDsnDetailVOs = null;
		for (DamagedDSNVO vo : damagedDsnVOs) {
			
			if (index == currentVoIndex) {
				damagedDsnDetailVOs = vo.getDamagedDsnDetailVOs();
				if (damagedDsnDetailVOs != null) {
					errors = validateDeletingVOs(damagedDsnDetailVOs,returnDsnForm);
					if (errors != null && errors.size() > 0) {
						invocationContext.addAllError(errors);
						invocationContext.target = TARGET_FAILURE;
						return;
					}
					
					damagedDsnDetailVOs = handleDeletedVO(damagedDsnDetailVOs,returnDsnForm,logonAttributes);
					vo.setDamagedDsnDetailVOs(damagedDsnDetailVOs);
				}						
				break;
			}										
			index++;
		}
		
		log.log(Log.FINE, "After deleting DamagedDsnVOs------------> ",
				damagedDsnVOs);
		returnDsnSession.setDamagedDsnVOs(damagedDsnVOs);
    	
    	invocationContext.target = TARGET_SUCCESS;
    	
       	log.exiting("DeleteDamageCommand","execute");
    	
    }
    
    private Collection<ErrorVO> validateDeletingVOs(
    		Collection<DamagedDSNDetailVO> damagedDsnDetailVOs,
    		ReturnDsnForm returnDsnForm) {
    	
    	log.entering("DeleteDamageCommand","validateDeletingVOs");
    	
    	Collection<ErrorVO> validationErrors = new ArrayList<ErrorVO>();
    	
    	String[] selectedRows = returnDsnForm.getReturnSubCheck();    	
    	int size = selectedRows.length;
    	int row = 0;
    	for(DamagedDSNDetailVO selectedvo : damagedDsnDetailVOs) {
    		for (int j = 0; j < size; j++) {
    			if (row == Integer.parseInt(selectedRows[j])) {
    				if (!DamagedMailbagVO.OPERATION_FLAG_INSERT.equals(selectedvo.getOperationFlag())) {
    					if (selectedvo.getReturnedBags() != 0) {
    						ErrorVO errorVO = new ErrorVO(
									"mailtracking.defaults.returndsn.msg.err.cannotDeleteReturnedBags");
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							validationErrors.add(errorVO);
							break;
    					}
    				}
    			}
    		}
    		row++;
    	}
    	
    	log.exiting("DeleteDamageCommand","validateDeletingVOs");
    	return validationErrors;
    }
    
    /**
     * Method to remove the deleted vos
     * @param damagedDsnDetailVOs
     * @param returnDsnForm
     * @param logonAttributes
     * @return
     */
    private Collection<DamagedDSNDetailVO> handleDeletedVO(
    		Collection<DamagedDSNDetailVO> damagedDsnDetailVOs,
    		ReturnDsnForm returnDsnForm,
    		LogonAttributes logonAttributes) {
    	
    	log.entering("DeleteDamageCommand","handleDeletedVO");
    	
    	Collection<DamagedDSNDetailVO> updatedvos = new ArrayList<DamagedDSNDetailVO>();
    	
    	String[] selectedRows = returnDsnForm.getReturnSubCheck();    	
    	int size = selectedRows.length;
    	int row = 0;
    	for(DamagedDSNDetailVO selectedvo : damagedDsnDetailVOs) {
    		for (int j = 0; j < size; j++) {
    			if (row == Integer.parseInt(selectedRows[j])) {
    				if (DamagedDSNDetailVO.OPERATION_FLAG_INSERT.equals(selectedvo.getOperationFlag())) {
    					selectedvo.setOperationFlag(OPERFLAG_INSERT_DELETE);
					}
					else {
						selectedvo.setOperationFlag(DamagedDSNDetailVO.OPERATION_FLAG_DELETE);
					}		    				
    			}	    
    		}
    		if (!OPERFLAG_INSERT_DELETE.equals(selectedvo.getOperationFlag())) {
				updatedvos.add(selectedvo);
			}
    		row++;
    	} 
    	
    	log.exiting("DeleteDamageCommand","handleDeletedVO");
    	return updatedvos;
    }
             
}
