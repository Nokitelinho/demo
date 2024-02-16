/*
 * DeleteULDStockSetUpCommand.java Created on Dec 22, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainuldstock;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.maintainuldstock.ListULDStockSetUpSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainULDStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2105
 *
 */
public class DeleteULDStockSetUpCommand  extends BaseCommand {

	private Log log = LogFactory.getLogger("DeleteULDStockSetUpCommand ");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";	
	
	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID ="uld.defaults.maintainuldstock";
	
	private static final String ULD_DEF_UPDATE_DMG = 
		"uld_def_update_dmg";
	private static final String ULD_DEF_ADD_DMG = 
		"uld_def_add_dmg";
	private static final String OPERATION_FLAG_INS_DEL
	= "operation_flg_insert_delete";
	/**
	 * Constants for Status Flag
	 */
	private static final String ACTION_SUCCESS = "action_success";
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

		/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		MaintainULDStockForm actionForm = 
			(MaintainULDStockForm) invocationContext.screenModel;
    	ListULDStockSetUpSession listULDStockSession =
    		getScreenSession(MODULE, SCREENID);
		
    	ArrayList<ULDStockConfigVO> uldStockConfigVOs = 
			listULDStockSession.getULDStockDetails()!= null ?
   			new ArrayList<ULDStockConfigVO>
           	(listULDStockSession.getULDStockDetails()) : 
   			new ArrayList<ULDStockConfigVO>();
           	
			ArrayList<ULDStockConfigVO> uldStockConfigDeletedVOs=new ArrayList<ULDStockConfigVO>();
			log.log(Log.FINE,
					"\n\n uldStockConfigVO from session ----------->",
					uldStockConfigVOs);
			String[] selectedRows=null ;
			selectedRows = actionForm.getRowId();
			log.log(Log.FINE, "\n\n selectedRows----------->", selectedRows);
			if (selectedRows != null) {
               int size = selectedRows.length;
               log.log(Log.FINE, "\n\nsize....", size);
			for(int i = size - 1; i >= 0; i--) {
            	   int selectedIndex = Integer.parseInt(selectedRows[i]);
            	   ULDStockConfigVO uldStockConfigvo = uldStockConfigVOs.get(selectedIndex);
            	   uldStockConfigvo.setOperationFlag
							(AbstractVO.OPERATION_FLAG_DELETE); 
            	   uldStockConfigDeletedVOs.add(uldStockConfigvo);
            	   uldStockConfigVOs.remove(selectedIndex);
            	   
               }
         }
            Collection<ErrorVO> errors = saveUldStock(uldStockConfigDeletedVOs,logonAttributes);
            if(errors != null && errors.size() > 0){
        		invocationContext.addAllError(errors);
        	}
    	 invocationContext.target = ACTION_SUCCESS;
   	}

    	
    private Collection<ErrorVO> saveUldStock(
    		ArrayList<ULDStockConfigVO> uldstockvos,
			LogonAttributes logonAttributes) {	
		
    	Collection<ErrorVO> errors = null;
		AirlineValidationVO airlineValidationVO = null;
		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
		
		log.log(Log.INFO, "uldstockvos from session-------------->",
				uldstockvos);
		log.log(log.FINE,"SaveULDStockSetUpCommand---------if errors is  null-------------------->");
				
		try {
			
			uldDefaultsDelegate.saveULDStockConfig(uldstockvos);
		}catch(BusinessDelegateException ex) 
		{
			errors = handleDelegateException(ex);
		}
		return errors;
		
	}

 }



