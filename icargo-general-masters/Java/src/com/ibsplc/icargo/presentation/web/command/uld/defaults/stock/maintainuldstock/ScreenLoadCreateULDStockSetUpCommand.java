/*
 * ScreenLoadCreateULDStockSetUpCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainuldstock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.maintainuldstock.ListULDStockSetUpSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainULDStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1496
 *
 */
public class ScreenLoadCreateULDStockSetUpCommand  extends BaseCommand {

	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String SCREENLOAD_FAILURE = "screenload_failure";
    private static final String MODULE = "uld.defaults";
	private static final String SCREENID ="uld.defaults.maintainuldstock";
	private static final String ULDNATURE_ONETIME = "uld.defaults.uldnature";
	/**
	 * Logger for Maintain DamageReport
	 */
	private Log log = LogFactory.getLogger("ScreenLoadCreateULDStockSetUpCommand");
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String  compCode = logonAttributes.getCompanyCode();
		//String station=logonAttributes.getStationCode();
		AirlineValidationVO airlineValidationVO = null;
    	MaintainULDStockForm maintainuldstockform = (MaintainULDStockForm) invocationContext.screenModel;
    	
    	ListULDStockSetUpSession listULDStockSession = getScreenSession(MODULE, SCREENID);
    	
    	listULDStockSession.setOneTimeValues(getOneTimeValues());
    	
    	String air = "";
    	if(maintainuldstockform.getAirline()!=null &&
    			maintainuldstockform.getAirline().trim().length()>0){
		air = maintainuldstockform.getAirline().toUpperCase();
		maintainuldstockform.setAirlineMain(air);

    	}
		String station = maintainuldstockform.getStation().toUpperCase();
		maintainuldstockform.setStationCode(station);
		maintainuldstockform.setStationMain(station);
		if(maintainuldstockform.getAirline() != null
				&& maintainuldstockform.getAirline().trim().length() > 0) {
			maintainuldstockform.setFilterStatus("both");
		}
		maintainuldstockform.setAirlineCode(air);
		maintainuldstockform.setStationCode(station);
		log.log(Log.ALL, "maintainuldstockform.getStatusFlag()---",
				maintainuldstockform.getStatusFlag());
		//added by ayswarya
		if(("uld_def_mod_dmg").equals(maintainuldstockform.getStatusFlag())){

			String[] selectedRowIds = maintainuldstockform.getFlag().split(",");

			if(selectedRowIds != null && selectedRowIds.length > 0) {
				/*
				ArrayList<ULDStockConfigVO> uldStockConfigVOs =
					new ArrayList<ULDStockConfigVO>();
					*/
				// from list command session
				ArrayList<ULDStockConfigVO> uldStockConfigVO =
					listULDStockSession.getULDStockDetails()!= null ?
		   			new ArrayList<ULDStockConfigVO>
		           	(listULDStockSession.getULDStockDetails()) :
		   			new ArrayList<ULDStockConfigVO>();
		        log.log(Log.INFO, "uldStockConfigVO------------------>",
						uldStockConfigVO);
					ArrayList<ULDStockConfigVO> selectedULDStockConfigVOs =
						new ArrayList<ULDStockConfigVO>() ;
				for(int index=0;index < selectedRowIds.length; index++) {
					log.log(Log.FINE, "selectedRowId ---> ", selectedRowIds,
							index);
					ULDStockConfigVO uldStockVO = uldStockConfigVO.get(
								Integer.parseInt(selectedRowIds[index]));

					selectedULDStockConfigVOs.add(uldStockVO);
				}
				log.log(Log.FINE, "\n\n selectedULDStockConfigVOs ---> ",
						selectedULDStockConfigVOs);
				listULDStockSession.setULDStockConfigVOs(selectedULDStockConfigVOs);
				maintainuldstockform.setDisableUldNature(AirlineValidationVO.FLAG_YES);
				/**
				 * On screen Load,the first selected record
				 * to be populated is set in session
				 */

				populatePopup(
						selectedULDStockConfigVOs.get(0), maintainuldstockform);
				maintainuldstockform.setDmglastPageNum(String.valueOf(
						selectedULDStockConfigVOs.size()));
				maintainuldstockform.setDmgtotalRecords((String.valueOf(
						selectedULDStockConfigVOs.size())));
				}

       	}else  	{
       		MaintainULDStockForm actionForm = (MaintainULDStockForm)
			invocationContext.screenModel;
       		listULDStockSession.setULDStockConfigVOs(null);
			ArrayList<ULDStockConfigVO> uldStockConfigVOs =
			(ArrayList<ULDStockConfigVO>)
			listULDStockSession.getULDStockConfigVOs();

			log.log(Log.FINE, "\n\nuldStockConfigVOs before SCREENLOAD ---> ",
					uldStockConfigVOs);
			if (uldStockConfigVOs == null || uldStockConfigVOs.size() == 0){


    			ULDStockConfigVO newULDStockConfigVO = new ULDStockConfigVO();

    	if(uldStockConfigVOs == null){
    		uldStockConfigVOs=new ArrayList<ULDStockConfigVO>();
    	}
    	if(uldStockConfigVOs != null) {
    		try{
				airlineValidationVO = validateAlphaAWBPrefix(logonAttributes.getCompanyCode(),actionForm.getAirlineCode());
				}catch(BusinessDelegateException businessDelegateException){
					Collection<ErrorVO> errors = handleDelegateException(businessDelegateException);
		        	
				}
				if (airlineValidationVO !=  null){
					newULDStockConfigVO.setAirlineIdentifier(
							airlineValidationVO.getAirlineIdentifier());
			}
    		newULDStockConfigVO.setOperationFlag
			(AbstractVO.OPERATION_FLAG_INSERT);
    		if(actionForm.getAirlineCode()!=null && actionForm.getAirlineCode().trim().length()>0 ){
    			newULDStockConfigVO.setAirlineCode(actionForm.getAirlineCode().toUpperCase());
    		}
    		newULDStockConfigVO.setCompanyCode(logonAttributes.getCompanyCode());
    		newULDStockConfigVO.setSequenceNumber(populateSequence(uldStockConfigVOs));
    		newULDStockConfigVO.setLastUpdatedUser(logonAttributes.getUserId());
    		newULDStockConfigVO.setStationCode(station);
    		// Added by A-2412
    		//Modified as part of ICRD-322756
    		if(!("uld_def_add_dmg").equals(maintainuldstockform.getStatusFlag())
    				&& listULDStockSession.getUldNature() !=null && listULDStockSession.getUldNature().trim().length()>0){
    			newULDStockConfigVO.setUldNature(listULDStockSession.getUldNature());
    			actionForm.setDisableUldNature(AirlineValidationVO.FLAG_YES);
    		}else{
    			actionForm.setDisableUldNature(AirlineValidationVO.FLAG_NO);
    		}
    		
    		//Addition by A-2412 ends
    			
    		uldStockConfigVOs.add(newULDStockConfigVO);

    	}

    	log.log(Log.FINE, "\n\n uldStockConfigVOs after SCREENLOAD ---> ",
				uldStockConfigVOs);
		listULDStockSession.setULDStockConfigVOs(uldStockConfigVOs);


    	SelectNextULDSetUpCommand command = new SelectNextULDSetUpCommand();
    	command.populateDmg(newULDStockConfigVO, actionForm);

    	int totalRecords = 0;
    	if(uldStockConfigVOs != null) {
	    	for(ULDStockConfigVO uldStockConfigVO : uldStockConfigVOs) {
	    		totalRecords++;
	    		}
	    }
     	actionForm.setDmgtotalRecords(String.valueOf(totalRecords));
    	actionForm.setDmglastPageNum(actionForm.getDmgtotalRecords());
    	actionForm.setDmgdisplayPage(actionForm.getDmgtotalRecords());
    	actionForm.setDmgcurrentPageNum(actionForm.getDmgdisplayPage());
       	}
    		}
		invocationContext.target=SCREENLOAD_SUCCESS;
    }
    /**
     *
     * @param uldStockConfigVOs
     * @return
     */
    public long populateSequence(ArrayList<ULDStockConfigVO>
    uldStockConfigVOs) {
		log.entering("Screen LoadCommand", "populateDiscDetail");
		long resolutionseq=0;
		for(ULDStockConfigVO uldStockConfigVO:uldStockConfigVOs)
		{
		if(uldStockConfigVO.getSequenceNumber()>resolutionseq)
		{
		resolutionseq=uldStockConfigVO.getSequenceNumber();
		}
		}
		resolutionseq=resolutionseq+1;
		log.exiting("Screen Load Command", "populateDiscDetail");
		return resolutionseq;

		}
    /**
     *
     * @param uldStockConfigVO
     * @param actionForm
     */
    public void populatePopup(
    		ULDStockConfigVO uldStockConfigVO,
    		MaintainULDStockForm actionForm) {
  	log.entering("SelectNextCommand", "populateShipmentDiscrepancyDetail");

  	if (uldStockConfigVO!=null) {

  		actionForm.setAirlineCode(uldStockConfigVO.getAirlineCode().toUpperCase());
  		actionForm.setStationCode(uldStockConfigVO.getStationCode().toUpperCase());
  		actionForm.setUldTypeCode(uldStockConfigVO.getUldTypeCode().toUpperCase());
  		actionForm.setUldNature(uldStockConfigVO.getUldNature().toUpperCase());
  		ListULDStockSetUpSession listULDStockSession = getScreenSession(MODULE, SCREENID);
  		/*if(listULDStockSession.getUldNature() !=null && listULDStockSession.getUldNature().trim().length()>0){
			actionForm.setDisableUldNature(AirlineValidationVO.FLAG_YES);
		}
		else{
			actionForm.setDisableUldNature(AirlineValidationVO.FLAG_NO);
		}*/
  		actionForm.setMaximumQty(String.valueOf(uldStockConfigVO.getMaxQty()));
  		actionForm.setMinimumQty(String.valueOf(uldStockConfigVO.getMinQty()));
  		// Added by Preet on 1st Apr for AirNZ 448 starts
  		actionForm.setDwellTime(String.valueOf(uldStockConfigVO.getDwellTime()));
  		if(uldStockConfigVO.getUldGroupCode()!=null && uldStockConfigVO.getUldGroupCode().trim().length()>0){
  			actionForm.setUldGroupCode(uldStockConfigVO.getUldGroupCode().toUpperCase());
  		}else{
  			actionForm.setUldGroupCode("");
  		}
  		// Added by Preet on 1st Apr for AirNZ 448 ends
  		actionForm.setRemarks(uldStockConfigVO.getRemarks());

  	}
  	log.exiting("SelectNextCommand", "populateShipmentDiscrepancyDetail");
  }

	private AirlineValidationVO validateAlphaAWBPrefix(String compCode,
			String ownerId) throws BusinessDelegateException {
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		return airlineDelegate.validateAlphaCode(compCode, ownerId);
	}
	   
	   
	   private Collection<String> getOneTimeParameterTypes() {
	    	log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
	    	ArrayList<String> parameterTypes = new ArrayList<String>();    	
	    	parameterTypes.add(ULDNATURE_ONETIME);    	
	    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
	    	return parameterTypes;    	
	    }
	   private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
			log.entering("ScreenLoadCommand","getOneTimeValues");
			/*
			 * Obtain the logonAttributes
			 */
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			/*
			 * the shared defaults delegate
			 */
			SharedDefaultsDelegate sharedDefaultsDelegate = 
				new SharedDefaultsDelegate();
			Map<String, Collection<OneTimeVO>> oneTimeValues = null;
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			try {
				log.log(Log.FINE, "****inside try**************************",
						getOneTimeParameterTypes());
				oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
						logonAttributes.getCompanyCode(), 
						getOneTimeParameterTypes());
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.FINE,"*****in the exception");				
			}
			log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
			log.exiting("ScreenLoadCommand","getOneTimeValues");
			return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
		}
		
}
