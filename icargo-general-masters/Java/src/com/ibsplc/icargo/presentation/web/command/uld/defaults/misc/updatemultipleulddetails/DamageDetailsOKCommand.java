/*
 * DamageDetailsOKCommand.java Created on Mar 13, 2018
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.UpdateMultipleULDDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.UpdateMultipleULDDetailsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails.DamageDetailsOKCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8176	:	13-Mar-2018	:	Draft
 */
public class DamageDetailsOKCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("Update Multiple ULD damage screen");
	private static final String MODULE_NAME = "uld.defaults";
	private final String SCREENID = "uld.defaults.updatemultipleulddetails";
	@Override
	public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
    	LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		
		 this.log.entering("DamageDetailsOKCommand", " execute ");
			UpdateMultipleULDDetailsForm updateMultipleULDDetailsForm = (UpdateMultipleULDDetailsForm)invocationContext.screenModel;
	    	UpdateMultipleULDDetailsSession updateMultipleULDDetailsSession = getScreenSession(MODULE_NAME, SCREENID);
			
			   ArrayList<ULDDamageVO> uldDamageVODetails = new ArrayList();
			   ULDDamageRepairDetailsVO ULDDamageRepairDetailsVO =updateMultipleULDDetailsSession.getSelectedDamageRepairDetailsVO();
			   if (updateMultipleULDDetailsForm.getTotalPoints()!=null || updateMultipleULDDetailsForm.getTotalPoints().trim().length() >0) {
				   ULDDamageRepairDetailsVO.setDamagePoints(updateMultipleULDDetailsForm.getTotalPoints());
			   }
			   /*This is to create the list of damages added in the popup*/
			   uldDamageVODetails = populateULDDamageDetailsforSave(updateMultipleULDDetailsForm, updateMultipleULDDetailsSession,ULDDamageRepairDetailsVO);
			   ULDDamageRepairDetailsVO.setUldDamageVOs(uldDamageVODetails);
			   updateMultipleULDDetailsSession.setSelectedDamageRepairDetailsVO(ULDDamageRepairDetailsVO);
			   
				
			   updateMultipleULDDetailsForm.setStatusFlag("action_damagesuccess");
			   invocationContext.target = "action_uldsuccess";
			   this.log.exiting("DamageDetailsOKCommand", "execute");
			    
	}  
			    
	 private ArrayList<ULDDamageVO> populateULDDamageDetailsforSave(UpdateMultipleULDDetailsForm actionForm, UpdateMultipleULDDetailsSession uldSession, ULDDamageRepairDetailsVO ULDDamageRepairDetailsVO)
			 {     
		          int count=0;
			      ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
			      LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
			      LocalDate repDate  = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);	
			      HashMap<Integer , ULDDamagePictureVO> picMap = uldSession.getSelectedDamageRepairDetailsVO().getDamagePicMap() !=null ? uldSession.getSelectedDamageRepairDetailsVO().getDamagePicMap():null;
			    	  String[] operationFlags = actionForm.getRatingUldOperationFlag();
			    	  String[] sectionNumbers = actionForm.getSection();
					   ArrayList<ULDDamageVO> uldDamageVOlist=new ArrayList<ULDDamageVO>() ;
					   for (int index = 0; index < operationFlags.length; index++) {
						      if ((!"NOOP".equalsIgnoreCase(actionForm.getRatingUldOperationFlag()[index])) && 
						        (sectionNumbers[index] != null) && (sectionNumbers[index].trim().length() > 0))
						      {
						        ULDDamageVO uldDamageVO = new ULDDamageVO();
						        uldDamageVO.setSection(actionForm.getSection()[count]);
						        uldDamageVO.setDamageDescription(actionForm.getDescription()[count]);
						        if (actionForm.getSeverity()[count] !=null || actionForm.getSeverity()[count].trim().length() >0) {
						        uldDamageVO.setSeverity(actionForm.getSeverity()[count]);
						        }
						        if (actionForm.getReportedDate() !=null || actionForm.getReportedDate()[count].trim().length() >0) {
						        	String reportedDate = actionForm.getReportedDate()[count];	
						        	uldDamageVO.setReportedDate(repDate.setDate(reportedDate));
						        }
						        if (actionForm.getRepStn()[count] !=null || actionForm.getRepStn()[count].trim().length() >0) {
						        uldDamageVO.setReportedStation(actionForm.getRepStn()[count]);
						        }
						        if (actionForm.getLocation()[count]!=null || actionForm.getLocation()[count].trim().length() >0) {
						        uldDamageVO.setLocation(actionForm.getLocation()[count]);
						        }
						        if (actionForm.getFacilityType()[count]!=null || actionForm.getFacilityType()[count].trim().length() >0) {
							    uldDamageVO.setFacilityType(actionForm.getFacilityType()[count]);
						        }
						        //Added by A-8368 as part of user story -    IASCB-35533
						        if (actionForm.getDamageNoticePoint()[count]!=null || actionForm.getDamageNoticePoint()[count].trim().length() >0) {
								    uldDamageVO.setDamageNoticePoint(actionForm.getDamageNoticePoint()[count]);
							    }
							    if (actionForm.getPartyType()[count] !=null || actionForm.getPartyType()[count].trim().length() >0) {
						        uldDamageVO.setPartyType(actionForm.getPartyType()[count]);
							    }
						        if (actionForm.getParty()[count] !=null || actionForm.getParty()[count].trim().length() >0) {
						        uldDamageVO.setParty(actionForm.getParty()[count]);
						        }
						        if (actionForm.getRemarks()[count] !=null || actionForm.getRemarks()[count].trim().length() >0) {
                                uldDamageVO.setRemarks(actionForm.getRemarks()[count]);
						        }
						        uldDamageVO.setSequenceNumber(count);
						        ULDDamagePictureVO damagePicVO=null;
						        if(picMap!=null) {
						        damagePicVO=picMap.get(index);
						        if(damagePicVO!= null) {
						        uldDamageVO.setPictureVO(damagePicVO);
				        		uldDamageVO.setPicturePresent(true);
						        }
						        }
						        uldDamageVO.setOperationFlag(operationFlags[count]);
						        uldDamageVO.setLastUpdateUser(logonAttributes.getUserId());
						        uldDamageVOlist.add(uldDamageVO);
						      }
						        count++;
						      }
					    return uldDamageVOlist;
			    }

}
