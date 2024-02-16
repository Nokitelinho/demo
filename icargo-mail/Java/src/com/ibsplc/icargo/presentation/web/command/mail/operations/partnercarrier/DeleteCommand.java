/*
 * DeleteCommand.java Created on August 11, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.partnercarrier;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.PartnerCarrierSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PartnerCarriersForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class DeleteCommand extends BaseCommand {

	private static final String SUCCESS = "delete_success";
	
private Log log = LogFactory.getLogger("MailTracking,PartnerCarrier");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.partnercarriers";
	
	private static final String OPERATION_FLAG_INS_DEL ="I_D";
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the delete command----------> \n\n");
    	
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
	    //String companyCode = logonAttributes.getCompanyCode();
    	
    	PartnerCarriersForm partnerCarriersForm =
							(PartnerCarriersForm)invocationContext.screenModel;
		PartnerCarrierSession partnerCarrierSession = 
										getScreenSession(MODULE_NAME,SCREEN_ID);
		
    	ArrayList<PartnerCarrierVO> partnerCarrierVOs = partnerCarrierSession.getPartnerCarrierVOs();
    	ArrayList<PartnerCarrierVO> updatedVOs = new ArrayList<PartnerCarrierVO>();
    	
    	if (partnerCarrierVOs == null) {
    		partnerCarrierVOs = new ArrayList<PartnerCarrierVO>();
		}else {
			partnerCarrierVOs = updatePartnerCarrierVOs(partnerCarrierVOs,
														partnerCarriersForm,
														logonAttributes);
		}
    	
    	String[] rowIds = partnerCarriersForm.getRowId();
    	
    	int index = 0;
    	
    	for(PartnerCarrierVO partnerCarrierVO:partnerCarrierVOs) {
    		for(int i = 0; i < rowIds.length; i++){
    			if(index == Integer.parseInt(rowIds[i])){
    				if (PartnerCarrierVO.OPERATION_FLAG_INSERT.equals(partnerCarrierVO.getOperationFlag())) {
    					partnerCarrierVO.setOperationFlag(OPERATION_FLAG_INS_DEL);
					}
					else {
						partnerCarrierVO.setOperationFlag(PartnerCarrierVO.OPERATION_FLAG_DELETE);
					}	
    			}
    		}
    		if (!OPERATION_FLAG_INS_DEL.equals(partnerCarrierVO.getOperationFlag())) {
				updatedVOs.add(partnerCarrierVO);
			}
    		index++;
    	}
    	
    	log.log(Log.FINE, "After deleting PartnerCarrierVOs------------> ",
				updatedVOs);
		partnerCarrierSession.setPartnerCarrierVOs(updatedVOs);
		
		
		partnerCarriersForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = SUCCESS;

	}
	
	/**
     * Method to update the partnerCarrierVOs in session
     * @param partnerCarrierVOs
     * @param partnerCarriersForm
     * @param logonAttributes
     * @return
     */
    private ArrayList<PartnerCarrierVO> updatePartnerCarrierVOs(
    		ArrayList<PartnerCarrierVO> partnerCarrierVOs,
    		PartnerCarriersForm partnerCarriersForm,
    		LogonAttributes logonAttributes) {
    	
    	log.entering("DeleteCommand","updatePartnerCarrierVOs");
    	
    	String[] carrierCodes = partnerCarriersForm.getPartnerCarrierCode();
    	String[] carrierNames = partnerCarriersForm.getPartnerCarrierName();
    	
    	if(carrierCodes != null){
    		int index = 0;
    		for(PartnerCarrierVO partnerCarrierVO:partnerCarrierVOs) {
    			partnerCarrierVO.setPartnerCarrierCode(carrierCodes[index].toUpperCase().trim());
    			partnerCarrierVO.setPartnerCarrierName(carrierNames[index]);
    			partnerCarrierVO.setOwnCarrierCode(logonAttributes.getOwnAirlineCode());
    			partnerCarrierVO.setAirportCode(partnerCarriersForm.getAirport().toUpperCase().trim());
    			partnerCarrierVO.setCompanyCode(logonAttributes.getCompanyCode());
				partnerCarrierVO.setOperationFlag(partnerCarriersForm.getOperationFlag()[index]);
    	
    			index++;
    		}
    	}
    	log.log(Log.FINE, "Updated partnerCarrierVOs------------> ",
				partnerCarrierVOs);
		log.exiting("DeleteCommand","updatePartnerCarrierVOs");
    	
    	return partnerCarrierVOs;    	
    }

}
