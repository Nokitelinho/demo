/*
 * SaveULDStockSetUpCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainuldstock;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.maintainuldstock.ListULDStockSetUpSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainULDStockForm;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigVO;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.util.Collection;
import java.util.ArrayList;

/**
 * @author A-1496
 *
 */
public class SaveULDStockSetUpCommand  extends BaseCommand {


	private static final String SAVE_SUCCESS = "save_success";
	private static final String SAVE_FAILURE = "save_failure";
	private static final String MODULE = "uld.defaults";
	private static final String SCREENID ="uld.defaults.maintainuldstock";
	private Log log = LogFactory.getLogger("SaveULDStockSetUpCommand");

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
				throws CommandInvocationException {
    	log.log(log.FINE,"SaveULDStockSetUpCommand----------------------------->");
			ApplicationSessionImpl applicationSession = getApplicationSession();
			
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			ListULDStockSetUpSession listULDStockSession = getScreenSession(MODULE, SCREENID);
			MaintainULDStockForm actionForm = 
				(MaintainULDStockForm) invocationContext.screenModel;
			
			Collection<ULDStockConfigVO> uldstockvos = new ArrayList<ULDStockConfigVO>();
			Collection<ULDStockConfigVO> dupUldstockvos = new ArrayList<ULDStockConfigVO>();
			uldstockvos = (Collection<ULDStockConfigVO>)listULDStockSession.getULDStockDetails();
			AirlineValidationVO airlineValidationVO = null;
			ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
			log.log(Log.INFO, "uldstockvos from session-------------->",
					uldstockvos);
			if(uldstockvos != null){
				
			
			for(ULDStockConfigVO vo:uldstockvos) {
				//if(vo.getOperationFlag()!=null){
				if(!(AbstractVO.OPERATION_FLAG_DELETE).equals(vo.getOperationFlag())){
				log.log(log.FINE,"SaveULDStockSetUpCommand----------inside for loop------------------->");
				String airlineCode = vo.getAirlineCode();
			
					airlineValidationVO = validateAlphaAWBPrefix(logonAttributes.getCompanyCode(),airlineCode);				
							
					if (airlineValidationVO !=  null){
						vo.setAirlineIdentifier(
								airlineValidationVO.getAirlineIdentifier());
				}
					/*if(!dupUldstockvos.contains(vo)) {
						log.log(log.FINE,"SaveULDStockSetUpCommand---------if dupUldstockvos is  not ther-------------------->");
						dupUldstockvos.add(vo);
					}
					else {
						log.log(log.FINE,"SaveULDStockSetUpCommand---------inside else loop-------------------->");
						error = new ErrorVO("uld.defaults.uldstocksetup.msg.err.duplicateexits");
		    			errors.add(error);	
		    			invocationContext.addAllError(errors);
		    			invocationContext.target=SAVE_FAILURE;
						
					}*/
					boolean isDuplicateExists = false;
					log.log(Log.INFO, "dupUldstockvos------->", dupUldstockvos);
					for(ULDStockConfigVO dupUld:dupUldstockvos) {
						if(dupUld.getAirlineCode().equals(vo.getAirlineCode())&&
								dupUld.getStationCode().equals(vo.getStationCode())&&
								dupUld.getUldTypeCode().equals(vo.getUldTypeCode())) {
							log.log(log.FINE,"SaveULDStockSetUpCommand---------inside else loop--error exits------------------>");
							error = new ErrorVO("uld.defaults.uldstocksetup.msg.err.duplicateexits");
			    			errors.add(error);	
			    			
			    			isDuplicateExists = true;
							break;
						}
					}
					if(isDuplicateExists) {
						break;
					}
					else {
						//isDuplicateExists = false;
						log.log(log.FINE,"SaveULDStockSetUpCommand---------if dupUldstockvos is  not ther------no error-------------->");
						dupUldstockvos.add(vo);
					}
			}
			}
    }
			if(errors!=null && errors.size()>0){
				log.log(log.FINE,"SaveULDStockSetUpCommand---------if errors is not null-------------------->");
				invocationContext.addAllError(errors);
    			invocationContext.target=SAVE_FAILURE;
    			
				return;
			}
			if( (errors == null || errors.size() == 0) && uldstockvos != null) {
				for(ULDStockConfigVO vo:uldstockvos) {
					if((AbstractVO.OPERATION_FLAG_DELETE).equals(vo.getOperationFlag())){
						log.log(log.FINE,"if errors is null && flag is delete-------------------->");
						dupUldstockvos.add(vo);
					}
				}
				log.log(log.FINE,"SaveULDStockSetUpCommand---------if errors is  null-------------------->");
				Collection<ErrorVO> err = new ArrayList<ErrorVO>();
			try {
				log.log(Log.INFO, "before setting to delegate---------->",
						dupUldstockvos);
				uldDefaultsDelegate.saveULDStockConfig(dupUldstockvos);
			}catch(BusinessDelegateException ex) 
			{
				ex.getMessage();
				err = handleDelegateException(ex);
			}
			//now added
			listULDStockSession.removeULDStockConfigVOs();
			listULDStockSession.removeULDStockDetails();
			actionForm.setStation("");
			actionForm.setAirline("");
			actionForm.setStationCode("");
			actionForm.setAirlineCode("");
			actionForm.setLinkStatus("");
			listULDStockSession.setAirLineCode("");
			//actionForm.setCreateStatus("success");
			
			if(logonAttributes.isAirlineUser()){
	    		listULDStockSession.setAirLineCode(logonAttributes.getOwnAirlineCode());
	    		actionForm.setStkDisableStatus("airline");
	    	}
	    	else{
	    		actionForm.setStationCode(logonAttributes.getAirportCode());
	    		actionForm.setStkDisableStatus("GHA");
	    	} 
			
			invocationContext.target=SAVE_SUCCESS;
			return;
			}else{
				if(logonAttributes.isAirlineUser()){
		    		listULDStockSession.setAirLineCode(logonAttributes.getOwnAirlineCode());
		    		actionForm.setStkDisableStatus("airline");
		    	}
		    	else{
		    		actionForm.setStationCode(logonAttributes.getAirportCode());
		    		actionForm.setStkDisableStatus("GHA");
		    	} 
				
				invocationContext.target=SAVE_SUCCESS;
			}
    }

    private AirlineValidationVO validateAlphaAWBPrefix(String compCode,String ownerId){
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();  
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
		airlineValidationVO = airlineDelegate.validateAlphaCode(compCode,ownerId);
		}catch(BusinessDelegateException businessDelegateException){
		businessDelegateException.getMessage();
		error = handleDelegateException(businessDelegateException);
		}
		return airlineValidationVO;
    }
}
