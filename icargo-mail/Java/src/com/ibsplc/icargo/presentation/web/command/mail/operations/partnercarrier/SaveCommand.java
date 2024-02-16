/*
 * SaveCommand.java Created on August 11, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.partnercarrier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.PartnerCarrierSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PartnerCarriersForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class SaveCommand extends BaseCommand {

	private static final String SUCCESS = "save_success";
	private static final String FAILURE = "save_failure";
	private Log log = LogFactory.getLogger("MailTracking,PartnerCarrier");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.partnercarriers";
	
	private static final String CODE_EMPTY = 
					"mailtracking.defaults.partnercarrier.msg.err.codeEmpty";
	private static final String DUPLICATE_CODE = 
									"mailtracking.defaults.duplicatepartner";
	private static final String SAVE_SUCCESS = 
					"mailtracking.defaults.partnercarrier.msg.info.savesuccess";
	private static final String ARP_EMPTY = 
						"mailtracking.defaults.partnercarrier.msg.err.arpEmpty";
	
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
		
    	log.log(Log.FINE, "\n\n in the save command----------> \n\n");

    	
		
		
    	PartnerCarriersForm partnerCarriersForm =
						(PartnerCarriersForm)invocationContext.screenModel;
		PartnerCarrierSession partnerCarrierSession = 
						getScreenSession(MODULE_NAME,SCREEN_ID);

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		ArrayList<PartnerCarrierVO> partnerCarrierVOs = partnerCarrierSession.getPartnerCarrierVOs();
    	if (partnerCarrierVOs == null) {
    		partnerCarrierVOs = new ArrayList<PartnerCarrierVO>();
		}
		
    	partnerCarrierVOs = updatePartnerCarrierVOs(partnerCarrierVOs,
										partnerCarriersForm,logonAttributes);
    	
    	if(("").equals(partnerCarriersForm.getAirport())) {
	    	log.log(Log.FINE, "\n\n ARP_EMPTY =============> \n\n");
	    	ErrorVO error = new ErrorVO(ARP_EMPTY);
	    	errors.add(error);
	    }
    	
		if(partnerCarrierVOs != null && partnerCarrierVOs.size()>0){
			for(PartnerCarrierVO partnerCarrierVO:partnerCarrierVOs){
				if(("").equals(partnerCarrierVO.getPartnerCarrierCode())){
					ErrorVO error = new ErrorVO(CODE_EMPTY);
					errors.add(error);
				}
			}
		}
		
		if(errors != null && errors.size()>0){
			invocationContext.addAllError(errors);
			partnerCarrierSession.setPartnerCarrierVOs(partnerCarrierVOs);
			invocationContext.target = FAILURE;
	    	return;
		}
		
    	try{
    		new AreaDelegate().validateAirportCode
    					(logonAttributes.getCompanyCode(),
    					partnerCarriersForm.getAirport().toUpperCase().trim());
    	}catch(BusinessDelegateException businessDelegateException){
    		errors = handleDelegateException(businessDelegateException);
    		invocationContext.addAllError(errors);
			partnerCarrierSession.setPartnerCarrierVOs(partnerCarrierVOs);
			invocationContext.target = FAILURE;
	    	return;
    	}
		
		errors = chkDuplicate(partnerCarrierVOs);
		if(errors != null && errors.size()>0) {
			invocationContext.addAllError(errors);
			partnerCarrierSession.setPartnerCarrierVOs(partnerCarrierVOs);
			invocationContext.target = FAILURE;
	    	return;
		}
		partnerCarrierSession.setPartnerCarrierVOs(partnerCarrierVOs);
		
		//partnerCarrierVOs = selectVO(partnerCarrierVOs);
		
		try {
			new MailTrackingDefaultsDelegate().savePartnerCarriers(partnerCarrierVOs);
		}catch(BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		if(errors != null && errors.size()>0) {
			invocationContext.addAllError(errors);			
			invocationContext.target = FAILURE;
	    	return;
		}
		
		if(partnerCarrierVOs != null && partnerCarrierVOs.size()>0){
			ErrorVO error = new ErrorVO(SAVE_SUCCESS);
			errors.add(error);
			invocationContext.addAllError(errors);
		}
		partnerCarriersForm.setAirport("");
		partnerCarrierSession.setPartnerCarrierVOs(null);
		partnerCarrierSession.setAirport(null);
		partnerCarriersForm.setScreenStatusFlag
					(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
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
    	
    	log.entering("SaveCommand","updatePartnerCarrierVOs");
   
    	String[] oprflags = partnerCarriersForm.getOperationFlag();
    	
    	int size = 0;
    	if(partnerCarrierVOs != null && partnerCarrierVOs.size() > 0){
			   size = partnerCarrierVOs.size();
    	}
    	Collection<PartnerCarrierVO> newPartnerCarrierVOs = new ArrayList<PartnerCarrierVO>();
		for(int index=0; index<oprflags.length;index++){
			if(index >= size){
				if(!"NOOP".equals(oprflags[index])){
	    			PartnerCarrierVO partnerCarrierVO = new PartnerCarrierVO();
	    			partnerCarrierVO.setCompanyCode(logonAttributes.getCompanyCode());
	    			partnerCarrierVO.setAirportCode(partnerCarriersForm.getAirport().toUpperCase().trim());
	    			partnerCarrierVO.setOwnCarrierCode(logonAttributes.getOwnAirlineCode());
	    			partnerCarrierVO.setPartnerCarrierCode(partnerCarriersForm.getPartnerCarrierCode()[index].toUpperCase().trim());
	    			partnerCarrierVO.setPartnerCarrierName(partnerCarriersForm.getPartnerCarrierName()[index]);
					partnerCarrierVO.setOperationFlag(partnerCarriersForm.getOperationFlag()[index]);
					partnerCarrierVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
					newPartnerCarrierVOs.add(partnerCarrierVO);
				}
			}else{
				int count = 0;
				if(partnerCarrierVOs != null && partnerCarrierVOs.size() > 0){
				   for(PartnerCarrierVO partnerCarrierVO:partnerCarrierVOs){
					   if(count == index){
						   if(!"NOOP".equals(oprflags[index])){
							   partnerCarrierVO.setPartnerCarrierCode(partnerCarriersForm.getPartnerCarrierCode()[index].toUpperCase().trim());
							   partnerCarrierVO.setPartnerCarrierName(partnerCarriersForm.getPartnerCarrierName()[index]);
							   if("N".equals(oprflags[index])){
								   partnerCarrierVO.setOperationFlag(null);
							   }else{
								   partnerCarrierVO.setOperationFlag(oprflags[index]);
							   }
							   newPartnerCarrierVOs.add(partnerCarrierVO);
						   }
					   }
					   count++;
				   }
				}
			}
		}
    	log.log(Log.FINE, "Updated partnerCarrierVOs------------> ",
				newPartnerCarrierVOs);
		log.exiting("SaveCommand","updatePartnerCarrierVOs");
    	
    	return (ArrayList<PartnerCarrierVO>)newPartnerCarrierVOs;    	
    }
    
	/**
     * Method to check duplicate value in partnerCarrierVOs
     * @param partnerCarrierVOs
     * @return
     */
    
    private Collection<ErrorVO> chkDuplicate(
					    		ArrayList<PartnerCarrierVO> partnerCarrierVOs){
    	
    	log.entering("SaveCommand","chkDuplicate");
    	
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		
		if(partnerCarrierVOs != null && partnerCarrierVOs.size()>0){
			int index = 0;
			String flag = "Y";
			HashSet<String> obj = new HashSet<String>();
			for(PartnerCarrierVO partnerCarierVO:partnerCarrierVOs){
				index = 0;
				String carrierCode = "";
				if(!PartnerCarrierVO.OPERATION_FLAG_DELETE.equals(partnerCarierVO.getOperationFlag())){
					carrierCode = partnerCarierVO.getPartnerCarrierCode();
					for(PartnerCarrierVO chkVO:partnerCarrierVOs){
						if(!PartnerCarrierVO.OPERATION_FLAG_DELETE.equals(chkVO.getOperationFlag())){
							String chkCode = chkVO.getPartnerCarrierCode();
							if(chkCode.equals(carrierCode)){
								index++;
							}
						}
					}
				}
				
				if(index>1){
					log.log(Log.FINE, "**duplicate present*** index =", index);
					/*				if(flag.equals("N")) {
						obj = new StringBuffer(obj).append(",").append(carrierCode).toString();
					}else{
						obj = carrierCode;
					}*/
					obj.add(carrierCode);
					flag = "N";
				}
				
			}
			Object[] destinationObject={obj.toString()};
			if(("N").equals(flag)) {	
				error = new ErrorVO(DUPLICATE_CODE,destinationObject);
				errors.add(error);
			}
		}
    	
    	log.exiting("SaveCommand","chkDuplicate");
    	
    	return errors;
    	
    }
    
	/**
     * Method to select VOs which are to be sent to server 
     * for insertion modification or deletion
     * @param partnerCarrierVOs
     * @return
     */
    
    private ArrayList<PartnerCarrierVO> selectVO(
					    		ArrayList<PartnerCarrierVO> partnerCarrierVOs){
    	
    	log.entering("SaveCommand","selectVO");
    	ArrayList<PartnerCarrierVO> selectedVOs = new ArrayList<PartnerCarrierVO>();
    	
    	for(PartnerCarrierVO partnerCarierVO:partnerCarrierVOs){
    		if(!("NA").equals(partnerCarierVO.getOperationFlag())){
    			selectedVOs.add(partnerCarierVO);
    		}
    	}
    	
    	log.log(Log.FINE, "\n\n**selectedVOs---------->", selectedVOs);
		log.exiting("SaveCommand","selectVO");
    	
    	return selectedVOs;
    }

}
