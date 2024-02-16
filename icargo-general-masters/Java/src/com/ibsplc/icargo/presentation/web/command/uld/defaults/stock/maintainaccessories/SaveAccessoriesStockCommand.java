/*
 * SaveAccessoriesStockCommand.java Created on Jan 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainaccessories;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListAccessoriesStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainAccessoriesStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainAccessoriesStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 * This command class is used to save the details of the specified stock 
 * 
 * @author A-2122
 */
public class SaveAccessoriesStockCommand extends BaseCommand {
    
	
	private Log log = LogFactory.getLogger("MAINTAIN ACCESSORIES");	
	private static final String MODULE_NAME = "uld.defaults";
	private static final String SCREEN_ID =
						"uld.defaults.maintainaccessoriesstock";
	private static final String MODULELIST_NAME = "uld.defaults";
	private static final String SCREENLIST_ID =
								"uld.defaults.stock.listaccessoriesstock";
	private static final String SAVE_SUCCESS = "save_success";
	private static final String LIST_FORWARD = "list_forward";
    
    
    private static final String SAVE = "U";
    private static final String INSERT="I";
    
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("SaveAccessoriesStockCommand", "execute"); 
    	
        ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
    	
	    ApplicationSessionImpl applicationSession = getApplicationSession();
	    LogonAttributes logonAttributes = applicationSession.getLogonVO();

    	String lastupdateuser=logonAttributes.getUserId();
    	String companyCode=logonAttributes.getCompanyCode().toUpperCase();
    	    	
    	MaintainAccessoriesStockSession maintainAccessoriesStockSession = 
    		(MaintainAccessoriesStockSession ) getScreenSession
    												(MODULE_NAME, SCREEN_ID);
    	MaintainAccessoriesStockForm maintainAccessoriesStockForm   = 
    				(MaintainAccessoriesStockForm)invocationContext.screenModel;
    	ListAccessoriesStockSession listAccessoriesStockSession = 
      	  (ListAccessoriesStockSession)getScreenSession
      	  									(MODULELIST_NAME,SCREENLIST_ID);
    	AccessoriesStockConfigVO accessoriesStockConfigVO = 
    									new AccessoriesStockConfigVO();
    	
    	
        accessoriesStockConfigVO =
				maintainAccessoriesStockSession.getAccessoriesStockConfigVO();
         	log.log(Log.FINE, "Status Flag inside save",
					maintainAccessoriesStockForm.getStatusFlag());
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
        if(maintainAccessoriesStockForm.getStatusFlag().equals(SAVE)){
    		accessoriesStockConfigVO.setOperationFlag
    						(accessoriesStockConfigVO.OPERATION_FLAG_UPDATE);
    		log.log(Log.FINE, "OpnFlaginside save", accessoriesStockConfigVO.getOperationFlag());
    	}  
        log.log(Log.FINE, "Status Flag--------->", maintainAccessoriesStockForm.getStatusFlag());
		if(maintainAccessoriesStockForm.getStatusFlag().equals(INSERT)){
        	accessoriesStockConfigVO.setOperationFlag
        					(accessoriesStockConfigVO.OPERATION_FLAG_INSERT);
        	log.log(Log.FINE, "OperationFlag------>inside save",
					accessoriesStockConfigVO.getOperationFlag());
			accessoriesStockConfigVO.setAirlineIdentifier
        				(maintainAccessoriesStockForm.getAirlineIdentifier());
        }
    	if(accessoriesStockConfigVO!= null){
    	accessoriesStockConfigVO.setAccessoryDescription
					  (maintainAccessoriesStockForm.getAccessoryDescription());
    	accessoriesStockConfigVO.setAvailable(maintainAccessoriesStockForm.
    															getAvailable());
    	accessoriesStockConfigVO.setLoaned(maintainAccessoriesStockForm.
    															getLoaned());
    	accessoriesStockConfigVO.setMinimumQuantity(maintainAccessoriesStockForm.
    															getMinimumQuantity());
    	log.log(Log.FINE, "MinimumQuantity..after setting vo",
				accessoriesStockConfigVO.getMinimumQuantity());
		accessoriesStockConfigVO.setLastUpdateUser(lastupdateuser);
    	accessoriesStockConfigVO.setRemarks(maintainAccessoriesStockForm.
    															getRemarks());
    	accessoriesStockConfigVO.setCompanyCode(companyCode);
    	log.log(Log.FINE,"SaveAccessoriesStockCommand..after setting vo");
    	log.log(Log.FINE, "accessoriesStockConfigVO--->>>>",
				accessoriesStockConfigVO);
		//Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	try{
    		uldDefaultsDelegate.saveAccessoriesStock(accessoriesStockConfigVO);
    	}catch(BusinessDelegateException businessDelegateException){
    		errors = handleDelegateException(businessDelegateException);
    	}
    	if(errors == null || errors.size() == 0){
			ErrorVO errorVO = new ErrorVO("uld.defaults.maintainaccessoriessaved");
			errorVO.setErrorDisplayType(ErrorDisplayType.STATUS);
			errors.add(errorVO);
			//commented by a-3045
			//invocationContext.addAllError(errors);
    	}
    	}
    	maintainAccessoriesStockForm.setModeFlag("N");
    	maintainAccessoriesStockForm.setLovFlag("N");
    	maintainAccessoriesStockForm.setScreenStatusFlag
    					(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	maintainAccessoriesStockForm.setAccessoryCode("");
    	maintainAccessoriesStockForm.setAirlineCode("");
    	maintainAccessoriesStockForm.setStationCode("");
    	maintainAccessoriesStockSession.setAccessoriesStockConfigVO(null);
    	
        AccessoriesStockConfigVO filterVO = new AccessoriesStockConfigVO();
        //added by a-3045 for defaulting airline as a part of CR AirNZ450 starts	
        //Commented by Manaf for INT ULD510
       // Collection<ErrorVO> errorvo = new ArrayList<ErrorVO>();
    	//removed by nisha on 29Apr08
          	if(logonAttributes.isAirlineUser()){
	    		filterVO.setAirlineCode(logonAttributes.getOwnAirlineCode());
	    		maintainAccessoriesStockForm.setAccessoryDisableStatus("airline");
	    	}
	    	else{
	    		filterVO.setStationCode(logonAttributes.getAirportCode());
	    		maintainAccessoriesStockForm.setAccessoryDisableStatus("GHA");
	    	}
       
		//added by a-3045 for defaulting airline as a part of CR AirNZ450 ends
    	maintainAccessoriesStockSession.setAccessoriesStockConfigVO(filterVO);
    	
    	if(("From List").equals(maintainAccessoriesStockForm.getDetailsFlag())){
    	listAccessoriesStockSession.setListStatus("noListForm");
    	
    	Collection<AccessoriesStockConfigVO> accessoriesStockConfigVOs=
    				maintainAccessoriesStockSession.
    						getAccessoriesStockConfigVOMap().values();
    	
    	log.log(Log.FINE, "Values inside session --->",
				accessoriesStockConfigVOs);
		if(accessoriesStockConfigVOs != null && 
    						accessoriesStockConfigVOs.size() > 0){
    	for(AccessoriesStockConfigVO accessoryStockVO:accessoriesStockConfigVOs)
    			{
    				accessoryStockVO.setOperationFlag
    				(accessoriesStockConfigVO.OPERATION_FLAG_UPDATE);
    			}
    			
    		}
    		log.log(Log.FINE, "Collection inside command-------->",
					accessoriesStockConfigVOs);
			//Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    		try{
    			uldDefaultsDelegate.saveAccessories(accessoriesStockConfigVOs);
    		}catch(BusinessDelegateException businessDelegateException){
    			errors = handleDelegateException(businessDelegateException);      			
    		}
        	//invocationContext.target = LIST_FORWARD; 
    	}
    	//else{//commented by A-5844 for the bug ICRD-63086 starts
    		ErrorVO error = new ErrorVO("uld.defaults.maintainaccessories.savedsuccessfully");
         	error.setErrorDisplayType(ErrorDisplayType.STATUS);
         	errors = new ArrayList<ErrorVO>();         	
         	errors.add(error);
         	invocationContext.addAllError(errors);
         	invocationContext.target = SAVE_SUCCESS; 
    	//}
    	log.exiting("SaveAccessoriesStockCommand","execute");
    }
    }

