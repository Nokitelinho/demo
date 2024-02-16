/*
 * DamageDescriptionListCommand.java Created on Mar 26, 2018
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.UpdateMultipleULDDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.UpdateMultipleULDDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails.DamageDescriptionListCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8176	:	26-Mar-2018	:	Draft
 */
public class DamageDescriptionListCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("Update Multiple ULD damage status");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	private final String SCREENID = "uld.defaults.updatemultipleulddetails";
	

	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		 this.log.entering("DamageDescriptionListCommand", " execute ");
			UpdateMultipleULDDetailsForm updateMultipleULDDetailsForm = (UpdateMultipleULDDetailsForm)invocationContext.screenModel;
	    	UpdateMultipleULDDetailsSession updateMultipleULDDetailsSession = getScreenSession(MODULE, SCREENID);
	    	String[] operationFlags = updateMultipleULDDetailsForm.getRatingUldOperationFlag();
	    	ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
	        log.entering("ListCommand---------------->>>>","Entering");
	        int index=0;
	        String companyCode = logonAttributes.getCompanyCode();
	        //Modified by A-8368 as part of bug - IASCB-47908
	        if(updateMultipleULDDetailsForm.getSelectedDmgRow()!=null && updateMultipleULDDetailsForm.getSelectedDmgRow().trim().length()>0) {
	        	index = Integer.parseInt(updateMultipleULDDetailsForm.getSelectedDmgRow());
	        }
	    	String section= updateMultipleULDDetailsForm.getSection()[index];
	    	String description= updateMultipleULDDetailsForm.getDescription()[index];
	    	String descriptions[]= updateMultipleULDDetailsForm.getDescription();
	    	String sections[]= updateMultipleULDDetailsForm.getSection();
	    	int totalpoints=0;
	    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	        ArrayList<ULDDamageChecklistVO>damageChecklistVOs=new ArrayList <ULDDamageChecklistVO>();
	        ArrayList<ULDDamageChecklistVO>damageChecklistVOsforeach=new ArrayList <ULDDamageChecklistVO>();

	        ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
	        try {
	        	if(section!=null && section.trim().length()>0) {
	        	damageChecklistVOs = (ArrayList<ULDDamageChecklistVO>)delegate.listULDDamageChecklistMaster(companyCode,section);
	        	}
	    	log
					.log(
							Log.FINE,
							"damageChecklistVOs getting from delegate--------->>>>>>>>>>>>>>",
							damageChecklistVOs);
	    	String desc = new String();
	    	if(damageChecklistVOs!=null && damageChecklistVOs.size()>0)
	    		for(ULDDamageChecklistVO damageChecklistVO :damageChecklistVOs){
	    			if(damageChecklistVO.getDescription().contains("\"")){
	    				
	    				desc = damageChecklistVO.getDescription().replace("\"", "&quot;");
	    				damageChecklistVO.setDescription(desc);
	    		}
	    	}
	    	
	    	updateMultipleULDDetailsSession.setULDDamageChecklistVO(damageChecklistVOs);
	    	/*Calculating total points by adding the individual points for all the damages added in the popup*/
	    	  for (int i = 0; i < sections.length; i++) {
	    		  if (!"NOOP".equalsIgnoreCase(operationFlags[i])){
	    		  damageChecklistVOsforeach = (ArrayList<ULDDamageChecklistVO>)delegate.listULDDamageChecklistMaster(companyCode,sections[i]);
					   for(ULDDamageChecklistVO checklistVO: damageChecklistVOsforeach) {
						   if(checklistVO.getDescription().equals(descriptions[i])) {
							   totalpoints= totalpoints+checklistVO.getNoOfPoints();
				    		   updateMultipleULDDetailsForm.setTotalPoints(String.valueOf(totalpoints));
				    		   updateMultipleULDDetailsSession.getSelectedDamageRepairDetailsVO().setDamagePoints(String.valueOf(totalpoints));
						   }
					   }
	    	    } 
	    	  }
	    	  invocationContext.target = "damagelist_success";
		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors= handleDelegateException(e);
		}
		
	}

}
