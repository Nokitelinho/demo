/*
 * ListCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.airportfacilitymaster;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.AirportFacilityMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AirportFacilityMasterForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2052
 *
 */
public class ListCommand  extends BaseCommand {

	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_failure";
	private static final String SCREENID = "uld.defaults.airportfacilitymaster";
    private static final String MODULE = "uld.defaults";
	private Log log = LogFactory.getLogger("ListCommand");

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
        log.entering("ListCommand---------------->>>>","Entering");
        String companyCode = logonAttributes.getCompanyCode();
    	AirportFacilityMasterForm form = 
			(AirportFacilityMasterForm) invocationContext.screenModel;
		AirportFacilityMasterSession session = 
			getScreenSession(MODULE, SCREENID);
        String airportCode = form.getAirportCode().toUpperCase();
        String facilityType = form.getFacilityType().toUpperCase();
        session.setAirportCode(airportCode);
        session.setFacilityTypeValue(facilityType);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();		
		errors = validateAirportCode(form,companyCode);
		if(errors!=null && errors.size() > 0) {			
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		
	    Collection<ULDAirportLocationVO> uldAirportLocationVOs =
	    	new ArrayList<ULDAirportLocationVO>();
	    Collection<ULDAirportLocationVO> vos =
	    	new ArrayList<ULDAirportLocationVO>();
	    Collection<WarehouseVO> warehouseVOs =
	    			new ArrayList<WarehouseVO>();
	    log.log(Log.FINE,"before setting to delegate--------->>>>>>>>>>>>>>");
    	log.log(Log.FINE, "companyCode-------->>>", companyCode);
		log.log(Log.FINE, "airportCode-------->>>", airportCode);
		log.log(Log.FINE, "facilityType-------->>>", facilityType);
		//if(("WHS").equals(facilityType)){
    	Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
    		try {	        	
    			warehouseVOs = delegate.findAllWarehousesforULD
        							(companyCode,airportCode);
        	log
					.log(
							Log.FINE,
							"warehouseVOs getting from delegate--------->>>>>>>>>>>>>>",
							warehouseVOs);        	
		} catch (BusinessDelegateException e) {
			e.getMessage();
			exception = handleDelegateException(e);
		}
		if(warehouseVOs !=null && warehouseVOs.size()>0 ){			
			for(WarehouseVO warehouseVO:warehouseVOs){
				ULDAirportLocationVO vo = new ULDAirportLocationVO();
				vo.setFacilityCode(warehouseVO.getWarehouseCode());
				vos.add(vo);				
			}
			session.setFacilityCode((ArrayList<ULDAirportLocationVO>)vos);
		}else{	
			if(("WHS").equals(facilityType)){	
			invocationContext.addError(new ErrorVO(
	            "uld.defaults.airportfacilitymaster.msg.err.nofacilitycodefound",null));
			invocationContext.addAllError(errors);						
			invocationContext.target = LIST_FAILURE;
			return;
			}else{
				log.log(Log.FINE, "facilityType inside else loop------>",
						facilityType);
				session.setFacilityCode(null);
				Collection<ErrorVO> exceptions = new ArrayList<ErrorVO>();
		    	try {
		        	
		        	uldAirportLocationVOs = delegate.
		        	listULDAirportLocation(companyCode,airportCode,facilityType);
		        	log
							.log(
									Log.FINE,
									"uldAirportLocationVOs getting from delegate--------->>>>>>>>>>>>>>",
									uldAirportLocationVOs);
					session.setULDAirportLocationVOs(uldAirportLocationVOs);
				} catch (BusinessDelegateException e) {
					e.getMessage();
					exceptions = handleDelegateException(e);
				}
		    		if(uldAirportLocationVOs == null || uldAirportLocationVOs.size()==0) {
						form.setAfterList("Listed");
					    invocationContext.addError(new ErrorVO(
		                    "uld.defaults.airportfacilitymaster.msg.err.noresults",null));
						invocationContext.addAllError(errors);
						invocationContext.target = LIST_FAILURE;
						// Added by A-2412
						session.setULDAirportLocationVOs(null);
						log.log(Log.ALL, "session.getULDAirportLocationVOs()",
								session.getULDAirportLocationVOs());
						return;
					}else{
						form.setAfterList("Listed");
						//invocationContext.addAllError(errors);
						invocationContext.target = LIST_SUCCESS;
					}
			}
			
		}
    	
    	Collection<ErrorVO> exceptions = new ArrayList<ErrorVO>();
    	try {
        	
        	uldAirportLocationVOs = delegate.
        	listULDAirportLocation(companyCode,airportCode,facilityType);
        	log
					.log(
							Log.FINE,
							"uldAirportLocationVOs getting from delegate--------->>>>>>>>>>>>>>",
							uldAirportLocationVOs);
			session.setULDAirportLocationVOs(uldAirportLocationVOs);
		} catch (BusinessDelegateException e) {
			e.getMessage();
			exceptions = handleDelegateException(e);
		}
    		if(uldAirportLocationVOs == null || uldAirportLocationVOs.size()==0) {
				form.setAfterList("Listed");
			    invocationContext.addError(new ErrorVO(
                    "uld.defaults.airportfacilitymaster.msg.err.noresults",null));
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				// Added by A-2412
				session.setULDAirportLocationVOs(null);
				log.log(Log.ALL, "session.getULDAirportLocationVOs()", session.getULDAirportLocationVOs());
				return;
			}else{
				form.setAfterList("Listed");
				//invocationContext.addAllError(errors);
				invocationContext.target = LIST_SUCCESS;
			}
    }

    private Collection<ErrorVO> validateAirportCode(AirportFacilityMasterForm form,
				String companyCode) {
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();			
			if(validateAirportCodes(form.getAirportCode().toUpperCase(),logonAttributes)!=null){
				errors.add(new ErrorVO("uld.defaults.airportFacilityMaster.msg.err.airportcodeinvalid",null));
			}					
			return errors;
	}
    
    /**
     * 
     * @param station
     * @param logonAttributes
     * @return
     */
    
 	public Collection<ErrorVO> validateAirportCodes(
    		String station,
    		LogonAttributes logonAttributes){
    	log.entering("ListCommand", "validateAirportCodes");	
    	Collection<ErrorVO> errors = null;    	
    	try {
			AreaDelegate delegate = new AreaDelegate();
			delegate.validateAirportCode(
					logonAttributes.getCompanyCode(),station);			

    	} catch (BusinessDelegateException e) {
			e.getMessage();
			errors = handleDelegateException(e);
		}
		log.exiting("ListCommand", "validateAirportCodes");
    	return errors;        
    }
}
