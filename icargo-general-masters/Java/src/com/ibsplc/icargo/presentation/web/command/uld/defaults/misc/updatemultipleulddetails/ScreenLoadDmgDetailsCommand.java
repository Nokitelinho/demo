/*
 * ScreenLoadDmgDetailsCommand.java Created on Mar 13, 2018
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.UpdateMultipleULDDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.UpdateMultipleULDDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails.ScreenLoadDmgDetailsCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8176	:	13-Mar-2018	:	Draft
 */
public class ScreenLoadDmgDetailsCommand extends BaseCommand {
	private static final String MODULE = "uld.defaults";
	private Log log = LogFactory.getLogger("Update Multiple ULD damage status");
	private static final String LIST_SUCCESS = "list_success";
	private final String SCREENID = "uld.defaults.updatemultipleulddetails";
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
	//Added by A-8368 as part of user story - IASCB-35533
	private static final String POINTOFNOTICE_ONETIME = "operations.shipment.pointofnotice";
	@Override
	public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
		log.entering("ScreenLoadDmgDetailsCommand", "ScreenLoadDmgDetailsCommand");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		UpdateMultipleULDDetailsForm updateMultipleULDDetailsForm = (UpdateMultipleULDDetailsForm)invocationContext.screenModel;
    	UpdateMultipleULDDetailsSession updateMultipleULDDetailsSession = getScreenSession(MODULE, SCREENID);
		int index = Integer.parseInt(updateMultipleULDDetailsForm.getSelectedRow());
		SharedDefaultsDelegate sharedDefaultsDelegate=new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		LocalDate ldate= new LocalDate(applicationSession.getLogonVO().getAirportCode(),Location.ARP,false);
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		ArrayList<ULDDamageChecklistVO>damageChecklistVOs=new ArrayList <ULDDamageChecklistVO>();
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		try {
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(logonAttributes.getCompanyCode(), getOneTimeParameterTypes());
		} catch (BusinessDelegateException e) {
			e.getMessage();
			exception = handleDelegateException(e);
		}
		 ArrayList<ULDDamageVO> uLDDamageVO = new ArrayList();
		updateMultipleULDDetailsSession.setOneTimeValues(
				(HashMap<String, Collection<OneTimeVO>>)oneTimeValues);
			Collection<ULDDamageRepairDetailsVO> vos =updateMultipleULDDetailsSession.getULDDamageRepairDetailsVOs();
			for (ULDDamageRepairDetailsVO uLDDamageRepairDetailsVO : vos) {
				if(uLDDamageRepairDetailsVO.getDamageIndex()==index) {
					/*This is to default the damage points to zero*/
					if(uLDDamageRepairDetailsVO.getDamagePoints()== null || !(uLDDamageRepairDetailsVO.getDamagePoints().trim().length() >0)) {
						int initialDamage=0;
						uLDDamageRepairDetailsVO.setDamagePoints(String.valueOf(initialDamage));
					}
					updateMultipleULDDetailsSession.setSelectedDamageRepairDetailsVO(uLDDamageRepairDetailsVO);
				}
			}
		 if(updateMultipleULDDetailsSession.getSelectedDamageRepairDetailsVO().getUldDamageVOs()==null) {
				try {	        	
					damageChecklistVOs = (ArrayList<ULDDamageChecklistVO>)delegate
									.listULDDamageChecklistMaster(applicationSession.getLogonVO().getCompanyCode(),"");
					log
							.log(
									Log.FINE,
									"damageChecklistVOs getting from delegate---------#######>",
									damageChecklistVOs);        	
				} catch (BusinessDelegateException e) {
					e.getMessage();
					//errors= handleDelegateException(e);
				} 
				if(damageChecklistVOs!=null && damageChecklistVOs.size()>0){
					updateMultipleULDDetailsSession.setULDDamageChecklistVO(damageChecklistVOs);
				}
				}
			
		
		log.exiting("ScreenLoadDmgDetailsCommand", "EXITING ScreenLoadDmgDetailsCommand");
		updateMultipleULDDetailsForm.setStatusFlag("action_popupload");
		invocationContext.target="popupscreenload_success";
		
	}
	
	private Collection<String> getOneTimeParameterTypes() {
    	log.entering("DamageDetailsScreenLoadCommand",
    									"getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	
    	parameterTypes.add(REPAIRSTATUS_ONETIME);
    	parameterTypes.add(DAMAGECODE_ONETIME);
    	parameterTypes.add(POSITION_ONETIME);
    	parameterTypes.add(SEVERITY_ONETIME);
    	parameterTypes.add(REPAIRHEAD_ONETIME);
    	parameterTypes.add(FACILITYTYPE_ONETIME);    	
    	parameterTypes.add(PARTYTYPE_ONETIME);    
    	parameterTypes.add(SECTION_ONETIME);
    	//Added by A-8368 as part of user story -    IASCB-35533
    	parameterTypes.add(POINTOFNOTICE_ONETIME);
    	log.exiting("DamageDetailsScreenLoadCommand",
    											"getOneTimeParameterTypes");
    	return parameterTypes;    	
    }

}
