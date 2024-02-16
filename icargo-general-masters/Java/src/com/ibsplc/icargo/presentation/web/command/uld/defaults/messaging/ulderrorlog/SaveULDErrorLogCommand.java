/*
 * SaveULDErrorLogCommand.java Created on Jun 15, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ulderrorlog;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
//import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.ULDErrorLogForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
/**
 * @author A-2408
 *
 */
public class SaveULDErrorLogCommand extends BaseCommand {
    
	/**
	 * Logger for ULD Error Log
	 */
	private Log log = LogFactory.getLogger("ULD Error Log");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	private static final String SCREENID_ULDERRORLOG =
		"uld.defaults.ulderrorlog";
	
	private static final String SAVE_SUCCESS = "save_success";
    
	private static final String SAVE_FAILURE="save_failure";
    
	private static final String NO_SAVE="uld.defaults.ulderrorlog.nodataforsave";
    
	private static final String SAVE_SUCCESSFULL="uld.defaults.ulderrorlog.savesuccess";
    
	private static final String DUP_ULDNOS="uld.defaults.ulderrorlog.dupuldnos";

	private static final String BLANK_ULD="uld.defaults.ulderrorlog.blankuld";
    
	private static final String INVALID_ULD="uld.defaults.ulderrorlog.invaliduld";
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ULDErrorLogSession uldErrorLogSession = 
			(ULDErrorLogSession)getScreenSession(MODULE,SCREENID_ULDERRORLOG);
		ULDErrorLogForm uldErrorLogForm = 
			(ULDErrorLogForm) invocationContext.screenModel;
		updateSessionWithForm(uldErrorLogForm,uldErrorLogSession);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO errorVO=null;
		//String[] uldNumbers=uldErrorLogForm.getUldNumber();
		ArrayList<String> ulds=new ArrayList<String>();
		ArrayList<String> dupulds=new ArrayList<String>();
		//boolean flag=false;
		boolean blankflag=false;
		
		
		Page<ULDFlightMessageReconcileDetailsVO> uldFlightMessageReconcileDetailsVOs
												=uldErrorLogSession.getULDFlightMessageReconcileDetailsVOs();
		
		if(uldFlightMessageReconcileDetailsVOs!=null && uldFlightMessageReconcileDetailsVOs.size()>0){
			for(ULDFlightMessageReconcileDetailsVO vo:uldFlightMessageReconcileDetailsVOs){
				
					if("".equals(vo.getUldNumber())){
						blankflag=true;
					}
						if(vo.getUldNumber()!=null && vo.getUldNumber().trim().length()>0 &&
								(!(OPERATION_FLAG_DELETE.equals(vo.getOperationFlag())))){
										ulds.add(vo.getUldNumber().trim());
						}
				
			
				
		log.log(Log.INFO, "vos for save", vo);
			}
			log.log(Log.INFO, "ulds", ulds);
			for(String uld:ulds){
				if(!(dupulds.contains(uld))){
					dupulds.add(uld);
				}
			}
			log.log(Log.INFO, "dupulds", dupulds);
			if(ulds.size()!=dupulds.size()){
				errorVO=new ErrorVO(DUP_ULDNOS);
				invocationContext.addError(errorVO);
				invocationContext.target=SAVE_FAILURE;
				return;
			}
			if(blankflag){
				errorVO=new ErrorVO(BLANK_ULD);
				invocationContext.addError(errorVO);
				invocationContext.target=SAVE_FAILURE;
				return;
			}
			StringBuilder codeArray = new StringBuilder();
		String errorString="";
			for(String uld:ulds){
				try{
					boolean isValid=new ULDDefaultsDelegate().validateULDFormat(logonAttributes.getCompanyCode(),uld);
					if(!isValid){
						if(("").equals(errorString)){
							errorString = uld;	
							codeArray.append(errorString);	
						}else{
							errorString = codeArray.append(",").append(uld).toString();
						}
						
					}
					
				}catch(BusinessDelegateException businessDelegateException) {
					businessDelegateException.getMessage();
					errors = handleDelegateException(businessDelegateException);
				}
			}
			if(!("".equals(errorString))){
				Object[] errorArray = {errorString};
				ErrorVO error = new ErrorVO(
						INVALID_ULD,
						errorArray);					
					errors.add(error);
			}
			if(errors!=null && errors.size()>0){
				invocationContext.addAllError(errors);
				invocationContext.target=SAVE_FAILURE;
				return;
			}
			log.log(Log.FINE, "before saving ULD Error Logs",
					uldFlightMessageReconcileDetailsVOs);
		try {
			new ULDDefaultsDelegate().updateUCMULDDetails(uldFlightMessageReconcileDetailsVOs);
		}
		catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		}else{
			errorVO=new ErrorVO(NO_SAVE);
			errors.add(errorVO);
		}
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target=SAVE_FAILURE;
			return;
		}else{
			uldErrorLogForm.setDisplayPage("1");
			uldErrorLogForm.setUlderrorlogAirport("");
			uldErrorLogForm.setUlderrorlogULDNo("");
			//uldErrorLogForm.setCarrierCode("");
			uldErrorLogForm.setFlightDate("");
			uldErrorLogForm.setFlightNo("");
			uldErrorLogForm.setMessageType("OUT");
			uldErrorLogForm.setScreenFlag("screenload");
			
			uldErrorLogSession.setFlightFilterMessageVOSession(null);
			uldErrorLogSession.setULDFlightMessageReconcileDetailsVOs(null);
			uldErrorLogSession.removePouValues();
			uldErrorLogSession.removePouValues();
			
			if(logonAttributes.isAirlineUser()){    		
				uldErrorLogForm.setUldDisableStat("airline");
	    	}
	    	else{
	    		uldErrorLogForm.setUlderrorlogAirport(logonAttributes.getAirportCode());
	    		uldErrorLogForm.setUldDisableStat("GHA");
	    	}
			errorVO=new ErrorVO(SAVE_SUCCESSFULL);
			invocationContext.addError(errorVO);
		}
		invocationContext.target = SAVE_SUCCESS;
    }
    
   /**
 * @param form
 * @param session
 */
private void updateSessionWithForm(ULDErrorLogForm form,ULDErrorLogSession session){
    	
    	String[] pous=form.getPou();
    	String[] uldNo=form.getUldNumber();
    	String[] ucmnos=form.getSequenceNumber();
    	String[] content=form.getContent();
    	Page<ULDFlightMessageReconcileDetailsVO> uldFlightMessageReconcileDetailsVOs
		=session.getULDFlightMessageReconcileDetailsVOs();
    	if(uldFlightMessageReconcileDetailsVOs!=null && uldFlightMessageReconcileDetailsVOs.size()>0){
    		for(int i=0;i<uldFlightMessageReconcileDetailsVOs.size();i++){
    			//log.log(log.INFO,"vo for save"+uldFlightMessageReconcileDetailsVOs.get(i));
    			if(!(OPERATION_FLAG_INSERT.equals(uldFlightMessageReconcileDetailsVOs.get(i).getOperationFlag()))&&
    					!(OPERATION_FLAG_DELETE.equals(uldFlightMessageReconcileDetailsVOs.get(i).getOperationFlag()))){
    				if(!(pous[i].equals(uldFlightMessageReconcileDetailsVOs.get(i).getPou()))){
    					uldFlightMessageReconcileDetailsVOs.get(i).setPou(pous[i]);
    					uldFlightMessageReconcileDetailsVOs.get(i).setOperationFlag(OPERATION_FLAG_UPDATE);
    				}
    				if(!(content[i].equals(uldFlightMessageReconcileDetailsVOs.get(i).getContent()))){
    					uldFlightMessageReconcileDetailsVOs.get(i).setContent(content[i]);
    					uldFlightMessageReconcileDetailsVOs.get(i).setOperationFlag(OPERATION_FLAG_UPDATE);
    				}
    			}
    			if(OPERATION_FLAG_INSERT.equals(uldFlightMessageReconcileDetailsVOs.get(i).getOperationFlag())){
    				uldFlightMessageReconcileDetailsVOs.get(i).setUldNumber(uldNo[i]);
    				uldFlightMessageReconcileDetailsVOs.get(i).setPou(pous[i]);
    				uldFlightMessageReconcileDetailsVOs.get(i).setSequenceNumber(ucmnos[i]);
    				uldFlightMessageReconcileDetailsVOs.get(i).setContent(content[i]);
    			}
    		}
    	}
    	session.setULDFlightMessageReconcileDetailsVOs(uldFlightMessageReconcileDetailsVOs);
    }
}

