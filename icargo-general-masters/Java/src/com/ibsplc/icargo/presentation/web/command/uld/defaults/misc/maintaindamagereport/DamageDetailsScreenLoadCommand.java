/*
 * DamageDetailsScreenLoadCommand.java Created on Feb 07,2006
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
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;


/**
 * This command class is invoked on the start up DamageDetailsScreenLoad
 * 
 * @author A-1862
 */
public class DamageDetailsScreenLoadCommand extends BaseCommand {
    
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
	private static final String OVERALLSTATUS_ONETIME = "uld.defaults.operationalStatus";
	private static final String REPAIRSTATUS_ONETIME = "uld.defaults.repairStatus";
	private static final String DAMAGECODE_ONETIME 
    								= "uld.defaults.damagecode";
	private static final String POSITION_ONETIME = "uld.defaults.position";
	private static final String SEVERITY_ONETIME = "uld.defaults.damageseverity";
	private static final String REPAIRHEAD_ONETIME 
    									= "uld.defaults.repairhead";    
	private static final String FACILITYTYPE_ONETIME = "uld.defaults.facilitytypes";
	private static final String PARTYTYPE_ONETIME = "uld.defaults.PartyType";
	private static final String SECTION_ONETIME="uld.defaults.section";

  //  private static final String SCREENID_CHECKLIST = "uld.defaults.damagechecklistmaster";
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
		// DamageChecklistMasterSession damageChecklistMasterSession  = 
			//	getScreenSession(MODULE, SCREENID_CHECKLIST);
		MaintainDamageReportForm maintainDamageReportForm =
					(MaintainDamageReportForm)invocationContext.screenModel;
		
		maintainDamageReportForm.setCurrentStation(logonAttributes.getStationCode());
		
log.log(Log.FINE, "maintainDamageReportForm.getScreenStatusFlag()",
				maintainDamageReportForm.getStatusFlag());
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
		
		if(("uld_def_mod_dmg").equals(maintainDamageReportForm.getStatusFlag())){
			
				
			
			String[] selectedRowIds = maintainDamageReportForm.getFlag()
														.split(",");
			
			if(selectedRowIds != null && selectedRowIds.length > 0) {
				
				ArrayList<ULDDamageVO> uLDDamageVO = 
		       		maintainDamageReportSession.getULDDamageVO().
		       										getUldDamageVOs() != null ?
		   			new ArrayList<ULDDamageVO>
		           	(maintainDamageReportSession.getULDDamageVO()
		           										.getUldDamageVOs()) : 
		   			new ArrayList<ULDDamageVO>();
		        
		           	ArrayList<ULDDamageVO> selectedULDDamageVOs =
						new ArrayList<ULDDamageVO>() ;
				for(int index=0; 
					index < selectedRowIds.length; index++) {
					log.log(Log.FINE, "selectedRowId ---> ", selectedRowIds,
							index);
					ULDDamageVO uldDamageVO = uLDDamageVO.get(
								Integer.parseInt(selectedRowIds[index]));
					
					selectedULDDamageVOs.add(uldDamageVO);
				}
				log.log(Log.FINE, "\n\n selectedULDDamageVOs ---> ",
						selectedULDDamageVOs);
				maintainDamageReportSession.setULDDamageVOs
												(selectedULDDamageVOs);					
				/**
				 * On screen Load,the first selected record
				 * to be populated is set in session
				 */				
			
				populatePopup(
						selectedULDDamageVOs.get(0), maintainDamageReportForm);
				maintainDamageReportForm.setDmglastPageNum(String.valueOf(
						selectedULDDamageVOs.size()));
				maintainDamageReportForm.setDmgtotalRecords((String.valueOf(
	    				selectedULDDamageVOs.size())));	
				
				//populate ur damagechecklist - withvalues from selectedULDDamageVOs.get(0)
				//set that damagechecklist to session
				
				String section=null;
				
				SharedDefaultsDelegate sharedDefaultsDelegate=new SharedDefaultsDelegate();
				Map<String, Collection<OneTimeVO>> oneTimeValues = null;
				Collection<OneTimeVO> oneTimeVOs = null;
				Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
				try {
					oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(logonAttributes.getCompanyCode(), getOneTimeParameterTypes());
					oneTimeVOs=oneTimeValues.get(SECTION_ONETIME);
					log.log(Log.FINE, "One time values frm Screenload ",
							oneTimeVOs);
				} catch (BusinessDelegateException e) {
					e.getMessage();
					exception = handleDelegateException(e);
				}
				if(selectedULDDamageVOs.get(0).getSection()!=null){
				/*	if (oneTimeVOs != null && oneTimeVOs.size() > 0) {
		    	  			for (OneTimeVO oneTimeVo : oneTimeVOs) {
		    	  				if (oneTimeVo.getFieldDescription().equals(selectedULDDamageVOs.get(0).getSection())) {
					//actionForm.setSection(oneTimeVo.getFieldValue());
		    	  					section=oneTimeVo.getFieldValue();
					break;
				}
		    	 }
		    	    	}*/
					section=selectedULDDamageVOs.get(0).getSection();
		    	    }
				
	   			String damageDescription=null;
	   			String[] damageDescriptions =null;
	   			if(selectedULDDamageVOs.get(0).getDamageDescription()!=null){
	   				damageDescription = selectedULDDamageVOs.get(0).getDamageDescription();
	   			}
	   			if(damageDescription != null){
	   				damageDescriptions = damageDescription.split(",");
	   				log.log(Log.FINE, "DamageDescription from modify",
							damageDescriptions.length);
					log
							.log(Log.FINE, "The Damage Section Details are",
									section);
	   			}
	   			
	   			
	   		 ArrayList<ULDDamageChecklistVO>damageChecklistVOs=new ArrayList <ULDDamageChecklistVO>();
	   		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
	   			try {	        	
	   	        	damageChecklistVOs = (ArrayList<ULDDamageChecklistVO>)uldDefaultsDelegate.listULDDamageChecklistMaster(logonAttributes.getCompanyCode(),section);
	   	    	log
						.log(
								Log.FINE,
								"damageChecklistVOs getting from delegate in OK Command --------->>>>>>>>>>>>>>",
								damageChecklistVOs);        	
	   			} catch (BusinessDelegateException e) {
	   			e.getMessage();
	   			//errors= handleDelegateException(e);
	   			}
	   			if(damageChecklistVOs != null && damageChecklistVOs.size()>0){
					for(ULDDamageChecklistVO chkVO : damageChecklistVOs){
						chkVO.setCheckInOut(false);
					}
				}
	   			
	   			if(damageDescriptions !=null){
		   				for(int i=0;i<damageDescriptions.length;i++){
			   				String sectionDescription=section.concat(damageDescriptions[i]);
			   				log.log(Log.FINE,
									"sectionDescription Value frm command",
									sectionDescription);
							if(damageChecklistVOs!=null && damageChecklistVOs.size()>0){
			   					for(ULDDamageChecklistVO damageVO:damageChecklistVOs){
			   					//	if((damageVO.getSection().concat(damageVO.getDescription())).equalsIgnoreCase(sectionDescription)){
			   					//	log.log(Log.FINE,"The Description value"+ (damageVO.getSection().concat(damageVO.getDescription())).trim());
			   					//	log.log(Log.FINE,"sectionDescription "+sectionDescription.trim());
			   						if((damageVO.getSection().concat(damageVO.getDescription()).trim()).equalsIgnoreCase(sectionDescription.trim())){
			   							damageVO.setCheckInOut(true);
			   						}
			   					}
		   				
		   				}
			   				
		   			}
	   			}
	   				log
							.log(
									Log.FINE,
									"The damageChecklistVOs details before setting to session",
									damageChecklistVOs);
					log.log(Log.FINE, "the session values are ",
							maintainDamageReportSession.getULDDamageChecklistVO());
					//if(maintainDamageReportSession.getULDDamageChecklistVO()==null){
	   				maintainDamageReportSession.setULDDamageChecklistVO(damageChecklistVOs);
	   				log.log(Log.FINE,
							"Session details frm the ScreenLoad command ",
							maintainDamageReportSession.getULDDamageChecklistVO());
				//}else{				
				Collection<ULDDamageChecklistVO>damageChecklistVos= selectedULDDamageVOs.get(0).getDamageChecklistVOs();
				
log.log(Log.FINE, "The details before setting to the Session",
						damageChecklistVos);
				
	    	}
			
       	}else
       	{
       		log.log(Log.FINE,"The MaintainDamageReport from else ");
       		MaintainDamageReportForm actionForm = (MaintainDamageReportForm)
			invocationContext.screenModel;
       		maintainDamageReportSession.setULDDamageVOs(null);
			ArrayList<ULDDamageVO> uldDamageVOs =
			(ArrayList<ULDDamageVO>)
			maintainDamageReportSession.getULDDamageVOs();
			
			log.log(Log.FINE, "\n\nuldDamageVOs before SCREENLOAD ---> ",
					uldDamageVOs);
			if (uldDamageVOs == null || uldDamageVOs.size() == 0){
    				
    	
    			ULDDamageVO newULDDamageVO = new ULDDamageVO();
    	if(uldDamageVOs == null){
    		uldDamageVOs=new ArrayList<ULDDamageVO>();
    	}
    	if(uldDamageVOs != null) {
    		LocalDate ldate= new LocalDate(applicationSession.getLogonVO().getAirportCode(),Location.ARP,false);
    		newULDDamageVO.setOperationFlag
			(AbstractVO.OPERATION_FLAG_INSERT);
    		if(uldDamageRepairDetailsVO.getUldDamageVOs()!=null &&
    				uldDamageRepairDetailsVO.getUldDamageVOs().size()>0	){
    		newULDDamageVO.setSequenceNumber(
    								(populateSequence((ArrayList<ULDDamageVO>)uldDamageRepairDetailsVO.getUldDamageVOs())));
    		
    		}
    		else{
    			newULDDamageVO.setSequenceNumber(
						(populateSequence(uldDamageVOs)));
    		}
    		newULDDamageVO.setReportedDate(ldate);
    		newULDDamageVO.setLastUpdateUser
        	(logonAttributes.getUserId());
    		uldDamageVOs.add(newULDDamageVO);
    	
    	} 
    	
    	log.log(Log.FINE, "\n\nuldDamageVOs after SCREENLOAD ---> ",
				uldDamageVOs);
		maintainDamageReportSession.setULDDamageVOs(uldDamageVOs);
    	
    	
    	SelectNextDmgCommand command = new SelectNextDmgCommand();
    	command.populateDmg(newULDDamageVO, actionForm);		

    	int totalRecords = 0;
    	if(uldDamageVOs != null) {
	    	for(ULDDamageVO uldDamageVO : uldDamageVOs) {
	    		totalRecords++;
	    		}
	    }
     	actionForm.setDmgtotalRecords(String.valueOf(totalRecords));
    	actionForm.setDmglastPageNum(actionForm.getDmgtotalRecords());
    	actionForm.setDmgdisplayPage(actionForm.getDmgtotalRecords());
    	actionForm.setDmgcurrentPageNum(actionForm.getDmgdisplayPage());
    	//ADDED BY TARUN FOR INT ULD370
    	LocalDate currentDate = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
    	actionForm.setReportedDate(
				TimeConvertor.toStringFormat(
						currentDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT));
    	actionForm.setRepStn(logonAttributes.getAirportCode());
    	
log.log(Log.FINE, "The actionForm values on Screenload", actionForm.getRepStn());
    	}
    		}
		SharedDefaultsDelegate sharedDefaultsDelegate 
								=new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<OneTimeVO> oneTimeVOs = null;
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
			compCode, getOneTimeParameterTypes());
			oneTimeVOs=oneTimeValues.get(SECTION_ONETIME);
			log.log(Log.FINE, "The onetimevalues are ", oneTimeValues.get(OVERALLSTATUS_ONETIME));
		} catch (BusinessDelegateException e) {
		e.getMessage();
		exception = handleDelegateException(e);
		}
		maintainDamageReportSession.setOneTimeValues(
		(HashMap<String, Collection<OneTimeVO>>)oneTimeValues);
		if("uld_def_add_dmg".equalsIgnoreCase(maintainDamageReportForm.getStatusFlag())){
			//maintainDamageReportSession.removeULDDamageChecklistVO();
			if(oneTimeVOs!=null && oneTimeVOs.size()>0){
				maintainDamageReportForm.setSection(oneTimeVOs.iterator().next().getFieldValue());
			}
			String section= maintainDamageReportForm.getSection();
			log.log(Log.FINE, "The section value#####", section);
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ArrayList<ULDDamageChecklistVO>damageChecklistVOs=new ArrayList <ULDDamageChecklistVO>();
			
			ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
			try {	        	
				damageChecklistVOs = (ArrayList<ULDDamageChecklistVO>)delegate
								.listULDDamageChecklistMaster(applicationSession.getLogonVO().getCompanyCode(),section);
				log
						.log(
								Log.FINE,
								"damageChecklistVOs getting from delegate---------#######>",
								damageChecklistVOs);        	
			} catch (BusinessDelegateException e) {
				e.getMessage();
				errors= handleDelegateException(e);
			} 
			if(damageChecklistVOs!=null && damageChecklistVOs.size()>0){
				maintainDamageReportSession.setULDDamageChecklistVO(damageChecklistVOs);
			}
		}
	/*	if(maintainDamageReportSession.getULDDamageVOs()!=null && maintainDamageReportSession.getULDDamageVOs().size()>0){
			if(maintainDamageReportSession.getULDDamageChecklistVO()!=null && maintainDamageReportSession.getULDDamageChecklistVO().size()>0){
				
		for(ULDDamageVO ulddamageVO:maintainDamageReportSession.getULDDamageVOs()){
			if(ulddamageVO.getDamageChecklistVOs()!=null && ulddamageVO.getDamageChecklistVOs().size()>0){
		maintainDamageReportSession.getULDDamageChecklistVO().addAll(ulddamageVO.getDamageChecklistVOs());
			}
		}
			}
		}*/
		//log.log(Log.FINE, "The ScreenLoad Details of getULDDamageChecklistVO"+maintainDamageReportSession.getULDDamageChecklistVO());
		//damageChecklistMasterSession.removeULDDamageChecklistVO();
	
		//maintainDamageReportForm.setSection()
		populateCurrency(maintainDamageReportSession);
		//Added as part of bug 108276 by A-3767 on 02Mar11
		maintainDamageReportForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SCREENLOAD_SUCCESS;
        
    }
    /**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("DamageDetailsScreenLoadCommand",
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
    	parameterTypes.add(SECTION_ONETIME);
    	log.exiting("DamageDetailsScreenLoadCommand",
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
     * 
     * @param uldDamageVOs
     * @return
     */
    public long populateSequence(ArrayList<ULDDamageVO> 
		uldDamageVOs) {
			log.entering("Screen LoadCommand", "populateDiscDetail");
			long resolutionseq=0;
			for(ULDDamageVO uldDamageVO:uldDamageVOs)
			{
			if(uldDamageVO.getSequenceNumber()>resolutionseq)
			{
			resolutionseq=uldDamageVO.getSequenceNumber();
			}
			}
			resolutionseq=resolutionseq+1;
			log.exiting("Screen Load Command", "populateDiscDetail");
			return resolutionseq;
			
			}
    
    /**
     * 
     * @param uldDamageVO
     * @param actionForm
     */
	  public void populatePopup(
			  ULDDamageVO uldDamageVO,
				MaintainDamageReportForm actionForm) {
		  //ApplicationSessionImpl applicationSession = getApplicationSession();
		//	LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	log.entering("DamageDetailsScreenLoadCommand", "populatePopup");

    	if (uldDamageVO!=null) {
	    	log.log(Log.FINE,
					"The uldDamageVO details frm populatePopup details",
					uldDamageVO);
			actionForm.setDamageCode
			(uldDamageVO.getDamageCode());
    		/*
//    		 for pic
	    	if(uldDamageVO.getPictureVO()==null || uldDamageVO.getPictureVO().getImage()==null){
	    		actionForm.setDmgPic(null);
	    	}else{
	    	
	    	}
	    	
	    	//for pic
	    	*/
	    	
    		actionForm.setSection(uldDamageVO.getSection());
    		/*SharedDefaultsDelegate sharedDefaultsDelegate=new SharedDefaultsDelegate();
			Map<String, Collection<OneTimeVO>> oneTimeValues = null;
			Collection<OneTimeVO> oneTimeVOs = null;
			Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
			try {
				oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(logonAttributes.getCompanyCode(), getOneTimeParameterTypes());
				oneTimeVOs=oneTimeValues.get(SECTION_ONETIME);
				log.log(Log.FINE,"One time values frm update "+oneTimeVOs);
			} catch (BusinessDelegateException e) {
				e.printStackTrace();
				exception = handleDelegateException(e);
			}
			if(uldDamageVO.getSection()!=null){
				if (oneTimeVOs != null && oneTimeVOs.size() > 0) {
	    	  			for (OneTimeVO oneTimeVo : oneTimeVOs) {
	    	  				if (oneTimeVo.getFieldDescription().equals(uldDamageVO.getSection())) {
				actionForm.setSection(oneTimeVo.getFieldValue());
				break;
			}
	    	 }
	    	    	}
				actionForm.setSection(uldDamageVO.getSection());
	    	    }*/
	    	  
    		actionForm.setDmgRefNo
			(String.valueOf
					(uldDamageVO.getDamageReferenceNumber()));
    		actionForm.setPosition
								(uldDamageVO.getPosition());
    		actionForm.setSeverity
							(uldDamageVO.getSeverity());
    		if(uldDamageVO.getReportedStation()!=null){
    		actionForm.setRepStn
							(uldDamageVO.getReportedStation().toUpperCase());
    		}
			if(uldDamageVO.isClosed()){
				actionForm.setClosed("on");
			}
			else
			{
				actionForm.setClosed(null);
			}
			actionForm.setRemarks
								(uldDamageVO.getRemarks());
			actionForm.setFacilityType(uldDamageVO.getFacilityType());
			actionForm.setLocation(uldDamageVO.getLocation());
			actionForm.setParty(uldDamageVO.getParty());
			actionForm.setPartyType(uldDamageVO.getPartyType());
			actionForm.setReportedDate(uldDamageVO.getReportedDate().toDisplayDateOnlyFormat());
			actionForm.setTotalPoints(uldDamageVO.getDamagePoints());
    		log.log(Log.FINE, "Section value", actionForm.getSection());
			log.log(Log.FINE, "Total Value value", actionForm.getTotalPoints());
    	}
    	log.exiting("DamageDetailsScreenLoadCommand", "populatePopup");
    }
}
