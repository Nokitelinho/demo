/*
 * RepairDetailsScreenLoadCommand.java Created on Feb 07,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.
										defaults.misc.maintaindamagereport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.currency.vo.CurrencyVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * This command class is invoked on the start up RepairDetailsScreenLoad
 * 
 * @author A-1862
 */
/**
 * @author A-1862
 *
 */
public class RepairDetailsScreenLoadCommand extends BaseCommand {
    
	/**
	 * Logger for Maintain DamageReport
	 */
	private Log log = LogFactory.getLogger("Maintain DamageReport");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintaindamagereport";
	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String DAMAGESTATUS_ONETIME = "uld.defaults.damageStatus";
	private static final String OVERALLSTATUS_ONETIME = "uld.defaults.overallStatus";
	private static final String REPAIRSTATUS_ONETIME = "uld.defaults.repairStatus";
	private static final String DAMAGECODE_ONETIME 
    								= "uld.defaults.damagecode";
	private static final String POSITION_ONETIME = "uld.defaults.position";
	private static final String SEVERITY_ONETIME = "uld.defaults.damageseverity";
	private static final String REPAIRHEAD_ONETIME 
    									= "uld.defaults.repairhead";  
	private static final String FACILITYTYPE_ONETIME = "uld.defaults.facilitytypes";
	private static final String PARTYTYPE_ONETIME = "uld.defaults.PartyType";
    
    private static final String MODULELOV = "uld.defaults";

	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENIDLOV =
		"uld.defaults.damagerefnolov";

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
		String  compCode = logonAttributes.getCompanyCode();

		MaintainDamageReportSession maintainDamageReportSession =
			(MaintainDamageReportSession)getScreenSession(MODULE,
					SCREENID);		
		MaintainDamageReportForm maintainDamageReportForm =
			(MaintainDamageReportForm)invocationContext.screenModel;
		
		//Added by Tarun on 20Mar08 For AirNZ418 
		maintainDamageReportForm.setSupervisor(logonAttributes.getUserId());
		
		
		//for populating details from main screen into the session-START
	    ULDDamageRepairDetailsVO uldDamageRepairDetailsVO =
    		maintainDamageReportSession.getULDDamageVO() != null ?
			maintainDamageReportSession.getULDDamageVO() :
			new ULDDamageRepairDetailsVO();
		if (uldDamageRepairDetailsVO != null ){			
				uldDamageRepairDetailsVO.setDamageStatus
								(maintainDamageReportForm.getDamageStatus());
				uldDamageRepairDetailsVO.setOverallStatus
								(maintainDamageReportForm.getOverallStatus());
				uldDamageRepairDetailsVO.setRepairStatus
								(maintainDamageReportForm.getRepairStatus());
				uldDamageRepairDetailsVO.setSupervisor
									(maintainDamageReportForm.getSupervisor());
				uldDamageRepairDetailsVO.setInvestigationReport
									(maintainDamageReportForm.getInvRep());

		}
//		 for populating details from main screen into the session-END
		
		
		if(maintainDamageReportSession.getULDDamageVO().
										getUldDamageVOs()==null ||
				maintainDamageReportSession.getULDDamageVO().
									getUldDamageVOs().size()==0){
			maintainDamageReportForm.setFlag("NORECORDS");
			 ErrorVO error = null;	    			
 	    	error = new ErrorVO
 	    	("uld.defaults.maintainDmgRep.msg.err.damagedetailsnotpresent");	    	    	
 	    	error.setErrorDisplayType(ErrorDisplayType.ERROR);
 	    	invocationContext.addError(error);  
 	    	invocationContext.target = SCREENLOAD_SUCCESS;
 			return;
			
		}else
		{boolean isPresent=false;
			ArrayList<ULDDamageVO> uLDDamageVO=new ArrayList<ULDDamageVO>
           	(maintainDamageReportSession.getULDDamageVO().getUldDamageVOs());
			for(ULDDamageVO uldDamageVO: uLDDamageVO)
			{
				if(uldDamageVO.getOperationFlag()!=null && 
				!uldDamageVO.getOperationFlag().equals
									(AbstractVO.OPERATION_FLAG_DELETE)
						&& !uldDamageVO.getOperationFlag().equals
										(AbstractVO.OPERATION_FLAG_INSERT)){
					isPresent=true;
					break;
				}
				if(uldDamageVO.getOperationFlag()==null ){
					isPresent=true;
					break;
				}
				
			}
			if(!isPresent)
			{
				maintainDamageReportForm.setFlag("SCREENLOADNORECORDS");
				ErrorVO error = null;	    			
 	    	error = new ErrorVO
 	    	("uld.defaults.maintainDmgRep.msg.err.damagedetailsnotpresent");	    	    	
 	    	error.setErrorDisplayType(ErrorDisplayType.ERROR);
 	    	invocationContext.addError(error);  
 	    	invocationContext.target = SCREENLOAD_SUCCESS;
 			return;
 			}
			
		}
		log.log(Log.FINE,
				"\n\n maintainDamageReportForm.getStatusFlag() ---> ",
				maintainDamageReportForm.getStatusFlag());
		if(("uld_def_mod_rep").equals(maintainDamageReportForm.getStatusFlag())){
			
				
			maintainDamageReportSession.setULDRepairVOs(null);
			String[] selectedRowIds = maintainDamageReportForm.getFlag()
																.split(",");
			
			if(selectedRowIds != null && selectedRowIds.length > 0) {
						
				ArrayList<ULDRepairVO> uLDRepairVO = 
		       		maintainDamageReportSession.getULDDamageVO().
		       										getUldRepairVOs() != null ?
		   			new ArrayList<ULDRepairVO>
		           	(maintainDamageReportSession.getULDDamageVO()
		           										.getUldRepairVOs()) : 
		   			new ArrayList<ULDRepairVO>();
		        
		           	ArrayList<ULDRepairVO> selectedULDRepairVOs =
						new ArrayList<ULDRepairVO>() ;
		           	ArrayList<String> selectedRefNos =
						new ArrayList<String>() ;
				for(int index=0; 
					index < selectedRowIds.length; index++) {
					log.log(Log.FINE, "selectedRowId ---> ", selectedRowIds,
							index);
					ULDRepairVO uldRepairVO = uLDRepairVO.get(
								Integer.parseInt(selectedRowIds[index]));
					
					selectedULDRepairVOs.add(uldRepairVO);
					selectedRefNos.add(String.valueOf(uldRepairVO.getDamageReferenceNumber()));
				}
				log.log(Log.FINE, "\n\n selectedULDRepairVOs ---> ",
						selectedULDRepairVOs);
				maintainDamageReportSession.setULDRepairVOs
													(selectedULDRepairVOs);	
				maintainDamageReportSession.setRefNo(selectedRefNos);
				
				/**
				 * On screen Load,the first selected record
				 * to be populated is set in session
				 */				
				populatePopup(
						selectedULDRepairVOs.get(0), maintainDamageReportForm);
				maintainDamageReportForm.setReplastPageNum(String.valueOf(
						selectedULDRepairVOs.size()));
				maintainDamageReportForm.setReptotalRecords((String.valueOf(
	    				selectedULDRepairVOs.size())));	
	    	}
			
		}else
       	{
       		MaintainDamageReportForm actionForm = (MaintainDamageReportForm)
			invocationContext.screenModel;
       		maintainDamageReportSession.setULDRepairVOs(null);
       		maintainDamageReportSession.setRefNo(null);
			ArrayList<ULDRepairVO> uldRepairVOs =
			(ArrayList<ULDRepairVO>)
			maintainDamageReportSession.getULDRepairVOs();
			ArrayList<String> refNos =
				(ArrayList<String>)
				maintainDamageReportSession.getRefNo();
			log.log(Log.FINE, "\n\nuldDamageVOs before SCREENLOAD ---> ",
					uldRepairVOs);
			log.log(Log.FINE, "\n\nrefNos before SCREENLOAD ---> ", refNos);
			if (uldRepairVOs == null || uldRepairVOs.size() == 0){
    		ULDRepairVO newULDRepairVO = new ULDRepairVO();
    		String newString = null;
    	if(uldRepairVOs == null){
    		uldRepairVOs=new ArrayList<ULDRepairVO>();
    		refNos=new ArrayList<String>();
    	}
    	if(uldRepairVOs != null) {
    		newULDRepairVO.setOperationFlag
			(AbstractVO.OPERATION_FLAG_INSERT);
    		newULDRepairVO.setRepairSequenceNumber(
    								(populateSequence(uldRepairVOs)));
    		log.log(Log.FINE,
					"\n\n logonAttributes.getAirlineIdentifier()--> ",
					logonAttributes.getOwnAirlineIdentifier());
			newULDRepairVO.setAirlineIdentifier(logonAttributes.getOwnAirlineIdentifier());
    		
    		newULDRepairVO.setLastUpdateUser
        	(logonAttributes.getUserId());
    		uldRepairVOs.add(newULDRepairVO);
    		refNos.add(newString);
    	
    	} 
    	
    	log.log(Log.FINE, "\n\nuldDamageVOs after SCREENLOAD ---> ",
				uldRepairVOs);
		
		maintainDamageReportSession.setULDRepairVOs(uldRepairVOs);
    	SelectNextRepCommand command = new SelectNextRepCommand();
    	command.populateRep(newULDRepairVO,newString,actionForm);		

    	int totalRecords = 0;
    	if(refNos != null) {
    		log.log(Log.FINE, "\n\nrefNos after SCREENLOAD ---> ", refNos);
    		maintainDamageReportSession.setRefNo(refNos);
	    	for(String ref : refNos) {
	    		totalRecords++;
	    		}
	    }
     	actionForm.setReptotalRecords(String.valueOf(totalRecords));
    	actionForm.setReplastPageNum(actionForm.getReptotalRecords());
    	actionForm.setRepdisplayPage(actionForm.getReptotalRecords());
    	actionForm.setRepcurrentPageNum(actionForm.getRepdisplayPage());
//    	 Added  for INT_ULD758 starts
    	ArrayList<ULDDamageVO> dmgVos = new ArrayList<ULDDamageVO>(
						maintainDamageReportSession.getULDDamageVO()
								.getUldDamageVOs());
		if (dmgVos != null && dmgVos.size() == 1 ) {	
			String dmgRefNum = String.valueOf(dmgVos.get(0).getDamageReferenceNumber());
			actionForm.setDmgRepairRefNo(dmgRefNum);
		}
		else if(dmgVos !=null && dmgVos.size()>1){
			log.log(Log.FINE, "From else part", dmgVos.size());
				boolean isFound = false;
				String dmgRefNum = String.valueOf(dmgVos.get(0).getDamageReferenceNumber());
				Long damageRefnum= new Long(dmgRefNum);
				for(ULDDamageVO uldDamageVo:dmgVos){
				log.log(Log.FINE,
							"The uldDamageVo.getDamageReferenceNumber() value",
							uldDamageVo.getDamageReferenceNumber());
					log.log(Log.FINE, "The damageRefnum value ", damageRefnum);
					if(!(damageRefnum==uldDamageVo.getDamageReferenceNumber())){
						isFound = true;
						log.log(Log.FINE,"From if");
					}
				}
				if(!isFound){
					actionForm.setDmgRepairRefNo(dmgRefNum);
				}
			}
	
    	// Added for INT_ULD758 ends
       	}
    	}
		
		//Added by Tarun on 24Mar08 For CR_AirNZ_418
		maintainDamageReportSession.setDefaultCurrency(defaultCurrency(logonAttributes));
		
		SharedDefaultsDelegate sharedDefaultsDelegate 
											=new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					compCode, getOneTimeParameterTypes());
		} catch (BusinessDelegateException e) {
			e.getMessage();
			exception = handleDelegateException(e);
		}
		maintainDamageReportSession.setOneTimeValues(
				(HashMap<String, Collection<OneTimeVO>>)oneTimeValues);
		populateCurrency(maintainDamageReportSession);
		invocationContext.target = SCREENLOAD_SUCCESS;
        
    }
    /**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("RepairDetailsScreenLoadCommand",
    									"getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	
    	parameterTypes.add(DAMAGESTATUS_ONETIME);
    	parameterTypes.add(OVERALLSTATUS_ONETIME);
    	parameterTypes.add(REPAIRSTATUS_ONETIME);
    	parameterTypes.add(DAMAGECODE_ONETIME);
    	parameterTypes.add(POSITION_ONETIME);
    	parameterTypes.add(SEVERITY_ONETIME);
    	parameterTypes.add(REPAIRHEAD_ONETIME);
    	parameterTypes.add(FACILITYTYPE_ONETIME);
    	parameterTypes.add(PARTYTYPE_ONETIME);    	
    	
    	log.exiting("RepairDetailsScreenLoadCommand",
    											"getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
    private void populateCurrency
			(MaintainDamageReportSession maintainDamageReportSession) {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		ArrayList<CurrencyVO> currencies = null;
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
			currencies = (ArrayList<CurrencyVO>)new CurrencyDelegate()
			.findAllCurrencyCodes(logonAttributes.getCompanyCode());
		} catch (BusinessDelegateException businessDelegateException) {
			exception = handleDelegateException(businessDelegateException);
		}
		
		maintainDamageReportSession.setCurrencies(currencies);
		
		}
    
    /**
	 * Method to generate sequence
	 * @param uldRepairVOs
     * @return long
     */
    public long populateSequence(ArrayList<ULDRepairVO> 
		uldRepairVOs) {
			log.entering("Screen LoadCommand", "populateSequence");
			long resolutionseq=0;
			for(ULDRepairVO uldRepairVO:uldRepairVOs)
			{
			if(uldRepairVO.getRepairSequenceNumber()>resolutionseq)
			{
			resolutionseq=uldRepairVO.getRepairSequenceNumber();
			}
			}
			resolutionseq=resolutionseq+1;
			log.exiting("Screen Load Command", "populateSequence");
			return resolutionseq;
			
			}
    
    
    /**
     * 
     * @param uldRepairVO
     * @param actionForm
     */
    
	  public void populatePopup(
			  ULDRepairVO uldRepairVO,
				MaintainDamageReportForm actionForm) {
    	log.entering("RepairDetailsScreenLoadCommand", "populatePopup");

    	if (uldRepairVO!=null) {
	    	
    		actionForm.setRepHead(uldRepairVO.getRepairHead());
    		actionForm.setRepairStn(uldRepairVO.getRepairStation());
    		if(uldRepairVO.getRepairDate()!=null){
    		String date = TimeConvertor.toStringFormat
    		(uldRepairVO.getRepairDate().toCalendar(),
    				TimeConvertor.CALENDAR_DATE_FORMAT);
    		actionForm.setRepairDate(date);
    		} else {
				actionForm.setRepairDate("");
			}
    		actionForm.setDmgRepairRefNo(String.valueOf
    				(uldRepairVO.getDamageReferenceNumber()));
    		actionForm.setAmount
    						(String.valueOf(uldRepairVO.getDisplayAmount()));
    		actionForm.setCurrency(uldRepairVO.getCurrency());
    		actionForm.setRepRemarks(uldRepairVO.getRemarks());
    		    		
    	}
    	log.exiting("RepairDetailsScreenLoadCommand", "populatePopup");
    }
	  
	  private String defaultCurrency(LogonAttributes logonAttributes){
		  String currency="";	
		  try {
			
		  		currency =new AreaDelegate().defaultCurrencyForStation
				 (logonAttributes.getCompanyCode(), logonAttributes.getStationCode());
				
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
			}
			
		return currency;
		  
	  }
}
