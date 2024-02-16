/*
 * DeleteListULDCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.listuld;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for deleting a uld
 *
 * @author A-2001
 */
public class DeleteListULDCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Delete Uld");
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.listuld";

	private static final String DELETE_SUCCESS = "delete_success";
	private static final String DELETE_FAILURE = "delete_failure";
    
	private static final String ULD_IS_OCCUPIED_AT_WAREHOUSE =
		"uld.defaults.uldisoccupiedatwarehouse";	
    
	private static final String ULD_EXISTS_AT_AIRPORT = "operations.flthandling.uldexistsatairport";

	private static final String ULD_EXISTS_IN_LOADPLAN = "operations.flthandling.uldexistsinloadplan";

	private static final String ULD_EXISTS_IN_MANIFEST = "operations.flthandling.uldexistsinmanifest";

    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	invocationContext.target = DELETE_SUCCESS;
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String compCode=logonAttributes.getCompanyCode();
		String airportCode=logonAttributes.getAirportCode();
		boolean isAirlineUser=logonAttributes.isAirlineUser();
		
		ListULDForm listULDForm = (ListULDForm) invocationContext.screenModel;
		ListULDSession listULDSession = 
							(ListULDSession)getScreenSession(MODULE,SCREENID);
		
    	Page<ULDListVO> uldListVO = listULDSession.getListDisplayPage();
    	Collection<ULDVO> uldVOs = new ArrayList<ULDVO>();
    	if(listULDForm.getSelectedRows() != null &&
    			listULDForm.getSelectedRows().length > 0) {
    		String selectedRows[] = listULDForm.getSelectedRows();
    		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    	for(int i = selectedRows.length - 1; i >= 0; i--) {
	    		ULDVO uldVO = new ULDVO();
	    		uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_DELETE);
    			uldVO.setCompanyCode(logonAttributes.getCompanyCode());
    			uldVO.setUldNumber(uldListVO.get(
    					Integer.parseInt(selectedRows[i])).getUldNumber().toUpperCase());
    			uldVOs.add(uldVO);
    			log.log(Log.FINE, "Inside delete uldVO------>>>", uldVO);
	    	}
	    	boolean isError=false;
	    	for(ULDVO uldVO:uldVOs){
	    		ErrorVO err=null;
				err=validateULD(uldVO,compCode,airportCode,isAirlineUser);
				if(err!=null){
					isError=true;
					invocationContext.addError(err);
	    			
				}
	    	}
	    	if(isError)
	    	{
	    		invocationContext.target = DELETE_FAILURE;	
    			return;
	    	}
    		try {
				new ULDDefaultsDelegate().saveULDs(uldVOs);
			}
			catch(BusinessDelegateException businessDelegateException) {
				Collection<ErrorVO> deleteErrors = 
						handleDelegateException(businessDelegateException);
				if(deleteErrors != null &&
						deleteErrors.size() > 0 ) {
					errors.addAll(deleteErrors);
				}
   			}
	    	/*	
    		for(int i = selectedRows.length - 1; i >= 0; i--) {
	    		uldListVO.remove(uldListVO.get(
						Integer.parseInt(selectedRows[i])));
    		}*/
	    	if(errors != null &&
	    			errors.size() > 0 ) {
	    			invocationContext.addAllError(errors);
	    			invocationContext.target = DELETE_FAILURE;
	    			return;
	    	}
	    	//now added
	    	else{
		    	for(int i = selectedRows.length - 1; i >= 0; i--) {
		    		uldListVO.remove(uldListVO.get(
							Integer.parseInt(selectedRows[i])));
	    		}
	    	}
	    	//ends
    	}
    	
       	listULDForm.setScreenStatusFlag(
					ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = DELETE_SUCCESS;	

    }
    
    private ErrorVO validateULD(ULDVO uldVo,String compCode,String airportCode,boolean isAirlineUser) {
//    	find whether airport is a ULD handling GHA
    	
    	ErrorVO err=null;
  //code removed by nisha for bugfix on 11Jul08....GHA check not required    
    		Collection<ULDValidationVO> validationvo = new ArrayList<ULDValidationVO>();
    		ULDValidationVO vo=new ULDValidationVO();
    		vo.setUldNumber(uldVo.getUldNumber());
    		vo.setCompanyCode(compCode);
    		validationvo.add(vo);
//    	to check whether ULD is in warehouse
    		log.log(Log.FINE, "\n\n\n\n ****INSIDE WH VALIDATION FOR ULD******");
    		Collection<ErrorVO> errorsafterwh = new ArrayList<ErrorVO>();
    		try {
    			new ULDDefaultsDelegate().validateULDForWarehouseOccupancy(validationvo);    			
    		} catch (BusinessDelegateException businessDelegateException) {
    			businessDelegateException.getMessage();
    			log.log(Log.FINE, "\n\n\n\n ****caught******");
    			errorsafterwh = handleDelegateException(businessDelegateException);
    		}
    		if (errorsafterwh != null && errorsafterwh.size() > 0) {
    			   			
    			for (ErrorVO error : errorsafterwh) {
    				if (ULD_IS_OCCUPIED_AT_WAREHOUSE.equals(error.getErrorCode())) {
    					Object[] obj = error.getErrorData();
    					String uldN = (String) obj[0];
    					log.log(Log.FINE, "\n\n\n\n ULD_IS_OCCUPIED_AT_WAREHOUSE"); 
    					
						
	    				return new ErrorVO("uld.defaults.uldisoccupiedatwarehouse", new Object[] { uldN });
    				}
    			
    		}}
    		
    	
//    	to check whether ULD is operational anywhere
    	
    		log.log(Log.FINE, "\n\n\n\n ****INSIDE opr VALIDATION FOR ULD******");
    		Collection<ErrorVO> errorsafteropr = new ArrayList<ErrorVO>();
    		try {
    			new ULDDefaultsDelegate().checkForULDInOperation(validationvo);		
    		} catch (BusinessDelegateException businessDelegateException) {
    			businessDelegateException.getMessage();
    			log.log(Log.FINE, "\n\n\n\n ****caught******");
    			errorsafteropr = handleDelegateException(businessDelegateException);
    		}
    		if (errorsafteropr != null && errorsafteropr.size() > 0) {
    			log.log(Log.FINE, "\n\n\n\n ****ERROR PRESENT******");    			
    			for (ErrorVO error : errorsafteropr) {
    				if (ULD_EXISTS_AT_AIRPORT.equals(error.getErrorCode())) {
    					Object[] obj = error.getErrorData();
    					String uldN = (String) obj[0];
    					log.log(Log.FINE, "\n\n\n\n ULD_EXISTS_AT_AIRPORT"); 
						
	    				return new ErrorVO("operations.flthandling.uldexistsatairport", new Object[] { uldN });
    				}
    				if (ULD_EXISTS_IN_LOADPLAN.equals(error.getErrorCode())) {
    					Object[] obj = error.getErrorData();
    					String uldN = (String) obj[0];
    					log.log(Log.FINE, "\n\n\n\n ULD_EXISTS_IN_LOADPLAN");
						
	    				return new ErrorVO("operations.flthandling.uldexistsinloadplan", new Object[] { uldN });
    				}
    				if (ULD_EXISTS_IN_MANIFEST.equals(error.getErrorCode())) {
    					Object[] obj = error.getErrorData();
    					String uldN = (String) obj[0];
    					log.log(Log.FINE, "\n\n\n\n ULD_EXISTS_IN_MANIFEST");
						
	    				return new ErrorVO("operations.flthandling.uldexistsinmanifest", new Object[] { uldN });
    				}
    			
    		}}
    	
    	return err;
    }

}

