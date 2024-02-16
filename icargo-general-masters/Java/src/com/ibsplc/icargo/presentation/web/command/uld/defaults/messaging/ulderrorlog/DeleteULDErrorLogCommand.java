/*
 * DeleteULDErrorLogCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ulderrorlog;

import java.util.ArrayList;
import java.util.Collection;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
//import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.ULDErrorLogForm;
//import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
//import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up of the 
 * DeleteULDErrorLogCommand screen
 * 
 * @author A-1862
 */

public class DeleteULDErrorLogCommand extends BaseCommand {
    
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
	
	private static final String DELETE_SUCCESS = "delete_success";
    

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    
		
		ULDErrorLogSession uldErrorLogSession = 
			(ULDErrorLogSession)getScreenSession(MODULE,SCREENID_ULDERRORLOG);
		ULDErrorLogForm uldErrorLogForm = 
			(ULDErrorLogForm) invocationContext.screenModel;
		updateSessionWithForm(uldErrorLogForm,uldErrorLogSession);
		Collection<ULDFlightMessageReconcileDetailsVO> uldDetailsVOs = new ArrayList<ULDFlightMessageReconcileDetailsVO>();
		uldDetailsVOs=uldErrorLogSession.getULDFlightMessageReconcileDetailsVOs();
		if(("error15").equals(uldErrorLogForm.getErrorCode())){			
			//uldDetailsVOs.add(uldErrorLogSession.getULDFlightMessageReconcileDetailsVO());
		}
		else{
	   	String[] checked = uldErrorLogForm.getSelectedULDErrorLog();	    	
	   
		Page<ULDFlightMessageReconcileDetailsVO> uldFlightMessageReconcileDetailsVOs=uldErrorLogSession.getULDFlightMessageReconcileDetailsVOs();

		if(uldFlightMessageReconcileDetailsVOs!=null && uldFlightMessageReconcileDetailsVOs.size()>0){
			if(checked!=null && checked.length>0){
				log
				.log(Log.FINE, "checked.length-------------------->",
						checked.length);
				for(int i=checked.length-1;i>-1;i--){
					if(checked[i]!=null && checked[i].length()>0){
						int val=Integer.parseInt(checked[i]);
						if(OPERATION_FLAG_INSERT.equals(uldFlightMessageReconcileDetailsVOs.get(val).getOperationFlag())){
							log.log(log.INFO,"insert flagggggggggg");
							uldFlightMessageReconcileDetailsVOs.remove(uldFlightMessageReconcileDetailsVOs.get(val));
						}else{
							log.log(log.INFO,"not insert flagggggggggg");
							uldFlightMessageReconcileDetailsVOs.get(val).setOperationFlag(OPERATION_FLAG_DELETE);
						}
					}
				}
	    	/*for(int i = 0; i < checked.length; i++) {
	    		int val=Integer.parseInt(checked[i]);	    		
	    		int index=0;
	    		for(ULDFlightMessageReconcileDetailsVO uldMessageReconcileDetailsVO:uldFlightMessageReconcileDetailsVOs){
	    			log.log(Log.FINE, "val-------------------->"
		    				+ val);
	    			log.log(Log.FINE, "index-------------------->"
		    				+ index);
	    			if(index==val){
	    				log.log(Log.FINE, "uldMessageReconcileDetailsVO-------------------->"
			    				+ uldMessageReconcileDetailsVO.getUldNumber());
	    				uldDetailsVOs.add(uldMessageReconcileDetailsVO);
	    			}
	    			index++;
	    		}
	    		
	    	}*/
			}
		}
	    	uldErrorLogSession.setULDFlightMessageReconcileDetailsVOs(null);
	    	uldErrorLogSession.setULDFlightMessageReconcileDetailsVOs(uldFlightMessageReconcileDetailsVOs);
	    	log.log(Log.FINE, "uldDetailsVOs-------------------->",
					uldDetailsVOs);
		}
		//Commented by Manaf for INT ULD510 starts
		//Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		//Commented by Manaf for INT ULD510 ends
		
		/*try {
			new ULDDefaultsDelegate().deleteUCMULDDetails(uldDetailsVOs);
		}
		catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
			errors = handleDelegateException(businessDelegateException);
		}*/
		
		uldErrorLogForm.setErrorCode("");
		uldErrorLogSession.setULDFlightMessageReconcileDetailsVO(null);
		
		invocationContext.target = DELETE_SUCCESS;
        
    }
    /**
     * @param form
     * @param session
     */
    private void updateSessionWithForm(ULDErrorLogForm form,ULDErrorLogSession session){
        	
        	String[] pous=form.getPou();
        	String[] uldNo=form.getUldNumber();
        	String[] ucmnos=form.getSequenceNumber();
        	Page<ULDFlightMessageReconcileDetailsVO> uldFlightMessageReconcileDetailsVOs
    		=session.getULDFlightMessageReconcileDetailsVOs();
        	if(uldFlightMessageReconcileDetailsVOs!=null && uldFlightMessageReconcileDetailsVOs.size()>0){
        		//added by Manaf for INT ULD510 due to code review error when checking in starts
        		int fltSize=uldFlightMessageReconcileDetailsVOs.size();
        		// added by Manaf for INT ULD510 due to code review error when checking in ends
        		for(int i=0;i<fltSize;i++){
        			log.log(Log.INFO, "vo for save",
							uldFlightMessageReconcileDetailsVOs.get(i));
					if(!(OPERATION_FLAG_INSERT.equals(uldFlightMessageReconcileDetailsVOs.get(i).getOperationFlag()))&&
        					!(OPERATION_FLAG_DELETE.equals(uldFlightMessageReconcileDetailsVOs.get(i).getOperationFlag()))){
        				if(!(pous[i].equals(uldFlightMessageReconcileDetailsVOs.get(i).getPou()))){
        					uldFlightMessageReconcileDetailsVOs.get(i).setPou(pous[i]);
        					uldFlightMessageReconcileDetailsVOs.get(i).setOperationFlag(OPERATION_FLAG_UPDATE);
        				}
        			}
        			if(OPERATION_FLAG_INSERT.equals(uldFlightMessageReconcileDetailsVOs.get(i).getOperationFlag())){
        				uldFlightMessageReconcileDetailsVOs.get(i).setUldNumber(uldNo[i]);
        				uldFlightMessageReconcileDetailsVOs.get(i).setPou(pous[i]);
        				uldFlightMessageReconcileDetailsVOs.get(i).setSequenceNumber(ucmnos[i]);
        			}
        		}
        	}
        	session.setULDFlightMessageReconcileDetailsVOs(uldFlightMessageReconcileDetailsVOs);
        }
    
}
