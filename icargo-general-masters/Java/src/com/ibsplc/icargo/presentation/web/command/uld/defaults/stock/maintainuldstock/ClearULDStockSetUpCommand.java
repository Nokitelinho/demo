/*
 * ClearULDStockSetUpCommand.java Created on Dec 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainuldstock;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.maintainuldstock.ListULDStockSetUpSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainULDStockForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2052
 *
 */
public class ClearULDStockSetUpCommand  extends BaseCommand {

    private static final String MODULE = "uld.defaults";
	private static final String SCREENID ="uld.defaults.maintainuldstock";
	private static final String CLEAR_SUCCESS = "clear_success";
	private static final String CLEAR_FAILURE = "clear_failure";
	private Log log = LogFactory.getLogger("ClearULDStockSetUpCommand");

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

			ListULDStockSetUpSession listULDStockSession = getScreenSession(MODULE, SCREENID);
			MaintainULDStockForm maintainuldstockform = (MaintainULDStockForm) invocationContext.screenModel;
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>(); 
			Collection<ULDStockConfigVO> uldstockvos = new ArrayList<ULDStockConfigVO>();
			
			if(!("canclear").equals(maintainuldstockform.getCloseStatus())) {
				if(listULDStockSession.getULDStockDetails() != null &&
						listULDStockSession.getULDStockDetails().size() > 0) {
					uldstockvos = (Collection<ULDStockConfigVO>)listULDStockSession.getULDStockDetails();
					log.log(Log.INFO, "uldstockvos-------->", uldstockvos);
					if(uldstockvos!=null){
				    	for(ULDStockConfigVO vo : uldstockvos) {
				    		log.log(Log.INFO,
									"vo.getOperationFlag()--------->", vo.getOperationFlag());
							if((ULDStockConfigVO.OPERATION_FLAG_UPDATE.equals(vo.getOperationFlag()))||
				  					(ULDStockConfigVO.OPERATION_FLAG_INSERT.equals(vo.getOperationFlag()))||
				  							(ULDStockConfigVO.OPERATION_FLAG_DELETE.equals(vo.getOperationFlag()))) {
				  				ErrorVO error  = new ErrorVO("uld.defaults.uldstocksetup.msg.err.unsaveddataexistsclear");
				  				error.setErrorDisplayType(ErrorDisplayType.WARNING);
				      			errors.add(error);
				  			}
				  		}
					}
			    	if(errors != null && errors.size() > 0) {
						log.log(Log.FINE, "exception--------------------->");	
						maintainuldstockform.setCloseStatus("whethertoclear");
						invocationContext.addAllError(errors);			
						invocationContext.target = CLEAR_FAILURE;
						return;
					}
				}
			}
			
			listULDStockSession.removeULDStockConfigVOs();
		   	// Added by Sreekumar S as a part of defaulting airline code in page (ANACR - 1471)
	      	Collection<ErrorVO> error = new ArrayList<ErrorVO>();
	    	//removed by nisha on 30Apr08
	      	
				if(logonAttributes.isAirlineUser()){
					maintainuldstockform.setAirlineCode(logonAttributes.getOwnAirlineCode());
		    		listULDStockSession.setAirLineCode(logonAttributes.getOwnAirlineCode());
		    		maintainuldstockform.setStkDisableStatus("airline");
		    		maintainuldstockform.setStationCode("");
		    	}
		    	else{
		    		maintainuldstockform.setStationCode(logonAttributes.getAirportCode());
		    		maintainuldstockform.setStkDisableStatus("GHA");
		    		maintainuldstockform.setAirlineCode("");
		    	} 
			
	    	//Added by Sreekumar S as a part of defaulting airline code in page (ANACR - 1471) ends
			
			
			log.log(Log.FINE, " no exception--------------------->11111");		
			maintainuldstockform.setAirlineIdentifier("");
			//maintainuldstockform.setAirlineCode("");
			maintainuldstockform.setCreateStatus("");
			maintainuldstockform.setValidateStatus("");
			maintainuldstockform.setAirline("");
			//maintainuldstockform.setAirlineCode("");
			maintainuldstockform.setRowContents("");
			maintainuldstockform.setFromScreen("");
			maintainuldstockform.setStation("");
			maintainuldstockform.setUldTypeCode("");
			maintainuldstockform.setUldNature("");
			maintainuldstockform.setMinimumQty("");
			maintainuldstockform.setMaximumQty("");
			maintainuldstockform.setRemarks("");
			listULDStockSession.removeULDStockDetails();
			listULDStockSession.removeAirLineCode();	
			//Added as part of ICRD-3639 by A-3767 on 16Aug11
			maintainuldstockform.setUldGroupCode(""); 
			maintainuldstockform.setUldNature("");
			maintainuldstockform.setUldTypeCode("");
/*			if(logonAttributes.isAirlineUser()){
	    		listULDStockSession.setAirLineCode(logonAttributes.getOwnAirlineCode());
	    		maintainuldstockform.setStkDisableStatus("airline");
	    	}
	    	else{
	    		maintainuldstockform.setStationCode(logonAttributes.getAirportCode());
	    		maintainuldstockform.setStkDisableStatus("GHA");
	    	} */
			
			maintainuldstockform.setScreenStatusFlag(
					ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.addAllError(error);
			invocationContext.target=CLEAR_SUCCESS;
	}
    
}
