/*
 * AddDamageDetailsCommand.java Created on Feb 6, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults
												.misc.maintaindamagereport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1862
 *
 */
public class AddDamageDetailsCommand extends BaseCommand {

		private Log log = LogFactory.getLogger("Maintain Damage Report");
		/*
		 * The Module Name
		 */
		private static final String MODULE = "uld.defaults";
		/*
		 * Screen Id of maintain damage report screen
		 */
		private static final String SCREENID =
			"uld.defaults.maintaindamagereport";
		private static final String ULD_DEF_ADD_DMG = 
			"uld_def_add_dmg";
		
	//	 private static final String SCREENID_CHECKIN = "uld.defaults.damagechecklistmaster";
		 
		private static final String SECTION_ONETIME="uld.defaults.section";
		
		private static final String TOTALPOINTS ="uld.defaults.totalpointstomakeuldunserviceable";
		/*
		 * Constants for Status Flag
		 */
		private static final String ACTION_SUCCESS = "action_success";
		
		private static final String OPERATION_FLAG_INS_DEL
		= "operation_flg_insert_delete";
		/**
		 * The execute method for AddDamageDetailsCommand
		 * (non-Javadoc)
		 * @see com.ibsplc.icargo.framework.web.command.Command#
		 * execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
		public void execute(InvocationContext invocationContext)
				throws CommandInvocationException {
	
			/*
			 * Obtain the logonAttributes
			 */
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			MaintainDamageReportForm maintainDamageReportForm = 
				(MaintainDamageReportForm) invocationContext.screenModel;
			MaintainDamageReportSession maintainDamageReportSession = 
				(MaintainDamageReportSession)getScreenSession(MODULE,SCREENID);
			/*  DamageChecklistMasterSession damageChecklistMasterSession  = 
					getScreenSession(MODULE, SCREENID_CHECKIN);*/
			/*
			 * Validate for client errors
			 */
			Collection<ErrorVO> errors = null;
			errors = validateForm
					(maintainDamageReportForm,logonAttributes.getCompanyCode());
			
			if(errors!=null && errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				invocationContext.target = ACTION_SUCCESS;
				return;
			}
			
	    	ArrayList<ULDDamageVO> uldDamageVO = 
	    		maintainDamageReportSession.getULDDamageVO()
	    											.getUldDamageVOs() != null ?
				new ArrayList<ULDDamageVO>(maintainDamageReportSession
										.getULDDamageVO().getUldDamageVOs()) : 
				new ArrayList<ULDDamageVO>();
				
				Collection<String> damageCodes = new ArrayList<String>();
	   			if(uldDamageVO != null && uldDamageVO.size() > 0){
	   				for(ULDDamageVO uldDmgVO : uldDamageVO){
	   					if(!ULDDamageVO.OPERATION_FLAG_DELETE.equals(uldDmgVO.getOperationFlag()) && 
	   							!OPERATION_FLAG_INS_DEL.equals(uldDmgVO.getOperationFlag())){
		   					if(!damageCodes.contains(uldDmgVO.getSection())){
		   						damageCodes.add(uldDmgVO.getSection());
		   					}
		   					
		   					
	   					}
	   				}
	   			}
				
				log.log(Log.FINE," \n\n\n\n uldDamageVO ---> ", uldDamageVO);
				log
						.log(
								Log.FINE,
								"The maintainDamageReportSession from AddDaqmageDetails",
								maintainDamageReportSession.getULDDamageChecklistVO());
				ArrayList<ULDDamageChecklistVO> damageCheckVos= null;
		    	  damageCheckVos=(ArrayList<ULDDamageChecklistVO>)maintainDamageReportSession.getULDDamageChecklistVO();
		    	  
				boolean[] checks = maintainDamageReportForm.isCheckbx();
				String[] selectedVal = maintainDamageReportForm.getCheckBxVal();
				
				log.log(Log.FINE, "The Checked Values before are", checks);
				if(damageCheckVos != null && damageCheckVos.size()>0){
					for(ULDDamageChecklistVO chkVO : damageCheckVos){
						chkVO.setCheckInOut(false);
					}
				}
				if(selectedVal != null && selectedVal.length >0){
					for(int i=0; i<selectedVal.length; i++){
						log.log(Log.FINE, "----------------->", selectedVal, i);
						if(damageCheckVos != null && damageCheckVos.size()>0){
							for(ULDDamageChecklistVO chkVO : damageCheckVos){
								if(chkVO.getSection().concat(chkVO.getDescription()).equalsIgnoreCase(selectedVal[i])){
									chkVO.setCheckInOut(true);
								}
							}
						}
					}
				}
				
		    	 /* if(checks!=null && checks.length>0){
		    		  log.log(Log.FINE,"The Checked Values are"+ checks.length);
		        	  log.log(Log.FINE,"The Checked Values are"+ checks.toString());
		        	  for(int j = 0;j < checks.length ; j++){
		        		  log.log(Log.FINE,"The Checked Values are==>"+ checks[j]);
		        	  }
		    	  for(int i = 0;i < checks.length ; i++){
		    		  if(checks[i]){
		    			  damageCheckVos.get(i).setCheckInOut(true);
		    			  log.log(Log.FINE,"the checked value from true "+damageCheckVos.get(i).isCheckInOut());
		    			   }else{
		    				   damageCheckVos.get(i).setCheckInOut(false);
		    				   log.log(Log.FINE,"the checked value from false "+damageCheckVos.get(i).isCheckInOut());
		    			   }
		    		  //damageCheckVos.get(i).setTotalPoints(Integer.parseInt(maintainDamageReportForm.getTotalPoints()));
		    	  }
		    	}*/
				
					/*if(checks!=null && checks.length>0){
						for(int i=0;i<checks.length;i++){
							if(maintainDamageReportSession.getULDDamageChecklistVO()!=null && maintainDamageReportSession.getULDDamageChecklistVO().size()>0){
								for (ULDDamageChecklistVO uldDamageChecklistVo : maintainDamageReportSession.getULDDamageChecklistVO()) {
									
								}
							}
							
						}
					}*/
				if(damageCheckVos!=null && damageCheckVos.size()>0){
		    	  for(ULDDamageChecklistVO damageChecklistVO:damageCheckVos){
	    			  damageChecklistVO.setTotalPoints(Integer.parseInt(maintainDamageReportForm.getTotalPoints()));
	    			//  damageChecklistVO.set
	    		  }
				}
		    	  log.log(Log.FINE,
						"damageCheckVos Details after coming frm Session",
						damageCheckVos);
		    	  maintainDamageReportSession.setULDDamageChecklistVO(damageCheckVos);
	    	if(("uld_def_mod_dmg").equals(maintainDamageReportForm.getStatusFlag())){
	    		
	    		ArrayList<ULDDamageVO> modifiedULDDamageVO = 
					new ArrayList<ULDDamageVO>(maintainDamageReportSession.getULDDamageVOs()) ;
	    		log.log(Log.FINE,
						"\n\nuldDamageVOs before MODIFY OK Command ---> ",
						modifiedULDDamageVO);
	   			if(modifiedULDDamageVO!=null && modifiedULDDamageVO.size()!=0){
	   			int displayIndex =
	   				Integer.parseInt(maintainDamageReportForm.getDmgcurrentPageNum())-1;
	   			
	   			ULDDamageVO uLDDamageVO = modifiedULDDamageVO.get(displayIndex);
	   			updateULDDamageVO(uLDDamageVO, maintainDamageReportForm,logonAttributes,maintainDamageReportSession);
	   			Collection<ErrorVO> errorsmod = null;
	   			errorsmod=validateMandatory(uLDDamageVO);
	   			if(errorsmod != null && errorsmod.size() > 0) {
	   				log.log(Log.FINE, "exception");
	   				maintainDamageReportForm.setDmgdisplayPage(maintainDamageReportForm.getDmgcurrentPageNum());
	   				invocationContext.addAllError(errorsmod);			
	   				invocationContext.target = ACTION_SUCCESS;
	   				return;
	   			}
	   			log.log(Log.FINE,
						"\n\nuldDamageVOs after MODIFY OK Command ---> ",
						modifiedULDDamageVO);
		    	maintainDamageReportSession.setULDDamageVOs(modifiedULDDamageVO);
		    	
	   			int displayPage = Integer.parseInt(maintainDamageReportForm.getDmgdisplayPage())-1;
	   			ULDDamageVO uldDmgVO = 
	   				modifiedULDDamageVO.get(displayPage);
	   			populateDmg(uldDmgVO, maintainDamageReportForm);
	   			maintainDamageReportForm.setDmgcurrentPageNum(maintainDamageReportForm.getDmgdisplayPage());
				
	   			}		        
			    for(ULDDamageVO modULDDamageVO : modifiedULDDamageVO) {
					for(ULDDamageVO sessionULDDamageVO : uldDamageVO) {
						if(sessionULDDamageVO.getSequenceNumber() ==
							modULDDamageVO.getSequenceNumber()) {
							sessionULDDamageVO = modULDDamageVO;
				}
				}}
				log.log(Log.FINE,
						"\n\nuldDamageVO in SESSION AFTER MODIFY---> ",
						maintainDamageReportSession
								.getULDDamageVO());
	    		/*String selectedRows=null ;
	            selectedRows = maintainDamageReportForm.getFlag();
	            log.log(Log.FINE,"\n\nselectedRows...."+selectedRows);
	            if(uldDamageVO!=null){
	               if (selectedRows != null) {
	                  int index = 0;
	                  for (ULDDamageVO uldDamageVOtmp :uldDamageVO) {
	                     if (index == Integer.parseInt(selectedRows)) {
	    	                	uldDamageVOtmp.setDamageCode
	    	                		(maintainDamageReportForm.getDamageCode());
	    	                	uldDamageVOtmp.setDamageReferenceNumber
	    	                	(Long.parseLong(maintainDamageReportForm
	    	                								.getDmgRefNo()));
	    	                	uldDamageVOtmp.setPosition
	    	                		(maintainDamageReportForm.getPosition());
	    	                	uldDamageVOtmp.setSeverity
	    	                	(maintainDamageReportForm.getSeverity());
	    	                	uldDamageVOtmp.setReportedStation
	    	                	(maintainDamageReportForm.getRepStn()
	    	                								.toUpperCase());
	    	                	if(maintainDamageReportForm.getClosed()!=null 
	    	                		&& maintainDamageReportForm.getClosed()
	    	                						.equals("on")){
	    	                		uldDamageVOtmp.setClosed(true);
	    	                		}
	    	            		else
	    	            		{uldDamageVOtmp.setClosed(false);
	    	            		}
	    	                	uldDamageVOtmp.setRemarks
	    	                	(maintainDamageReportForm.getRemarks());
	    	                	if(uldDamageVOtmp.getOperationFlag()==null)
	    	                	{
	    	                		uldDamageVOtmp.setOperationFlag
	    	                		(AbstractVO.OPERATION_FLAG_UPDATE);
	    	                	}
	    	                	uldDamageVOtmp.setLastUpdateUser
	    	                	(logonAttributes.getUserId());
	    	            }
	    	            index++;
	                 }
	              }
	            } 
	           	log.log(Log.FINE, "\n\n\n\n uldDamageVO ---> " + uldDamageVO);
	           
	            maintainDamageReportSession.getULDDamageVO().setUldDamageVOs
	            											(uldDamageVO);
	            log.log(Log.FINE, "\n\n\n\n maintainDamageReportSession ---> " 
	            		+ maintainDamageReportSession.getULDDamageVO());*/
			           	maintainDamageReportSession.setULDDamageVOs(null);
	   		}else
	    	{
	   			log.log(Log.FINE,"From else part ");
	    		MaintainDamageReportForm actionForm = (MaintainDamageReportForm)
				invocationContext.screenModel;
	   			ArrayList<ULDDamageVO> uldDamageVOs =
	   				(ArrayList<ULDDamageVO>)
	   									maintainDamageReportSession.getULDDamageVOs();
	   			log.log(Log.FINE,
						"\n\nuldDamageVOs before OK Command from else---> ",
						uldDamageVOs);
	   			if(uldDamageVOs!=null && uldDamageVOs.size()!=0){
	   			int displayIndex =
	   				Integer.parseInt(actionForm.getDmgcurrentPageNum())-1;
	   			
	   			ULDDamageVO uLDDamageVO = uldDamageVOs.get(displayIndex);
	   			log.log(Log.FINE, "from collection and from else", uLDDamageVO);
	   			if(uLDDamageVO != null ){
		   			updateULDDamageVO(uLDDamageVO, actionForm,logonAttributes,maintainDamageReportSession);
		   			Collection<ErrorVO> errorsadd = null;
		   			errorsadd=validateMandatory(uLDDamageVO);
		   			if(errorsadd != null && errorsadd.size() > 0) {
		   				log.log(Log.FINE, "exception");
		   				actionForm.setDmgdisplayPage(actionForm.getDmgcurrentPageNum());
		   				invocationContext.addAllError(errorsadd);			
		   				invocationContext.target = ACTION_SUCCESS;
		   				return;
		   			}
		   			//Modified as part of ICRD-276265 starts here
		   			boolean isRepaired= false;
		   			if(uldDamageVO != null && uldDamageVO.size() > 0){
		   		   		for(ULDDamageVO uldDmgVO : uldDamageVO){
		   		   				if(uLDDamageVO.getSection().contains(uldDmgVO.getSection())&&uldDmgVO.getRepairDate()!=null){
		   		   				isRepaired= true;
		   		   				}
		   		   			if(damageCodes.contains(uLDDamageVO.getSection())&&!isRepaired){
				   				
				   				ErrorVO errorVO = new ErrorVO("uld.defaults.maintainDmgRep.msg.err.duplicatedamage");
								invocationContext.addError(errorVO);			
				   				invocationContext.target = ACTION_SUCCESS;
				   				return;
				   			}
		   		   				}
		   					}
		   		//Modified as part of ICRD-276265 ends here
	   			}
	   			log.log(Log.FINE,
						"\n\nuldDamageVOs after OK Command from else ---> ",
						uldDamageVOs);
		    	maintainDamageReportSession.setULDDamageVOs(uldDamageVOs);
		    	log.log(Log.FINE, "The form value", actionForm.getDmgdisplayPage());
	   			int displayPage = Integer.parseInt(actionForm.getDmgdisplayPage())-1;
	   			ULDDamageVO uldDmgvo = 
	   				uldDamageVOs.get(displayPage);
	   			log.log(Log.FINE, "THe ULDDamageVO details after OK command ",
						uldDmgvo);
	   			populateDmg(uldDmgvo, actionForm);
	   			actionForm.setDmgcurrentPageNum(actionForm.getDmgdisplayPage());	   			
	   			ArrayList<ULDDamageVO> uldDamageFromSessionVOs=
	   				(ArrayList<ULDDamageVO>)maintainDamageReportSession.getULDDamageVO().getUldDamageVOs();
	   			log.log(Log.FINE, "\n\nuldDamageVOs from session ---> ",
						uldDamageFromSessionVOs);
	   			if(uldDamageFromSessionVOs==null)
	   			{maintainDamageReportSession.getULDDamageVO().setUldDamageVOs(maintainDamageReportSession.getULDDamageVOs());
	   			}
	   			else
	   			{
	   			uldDamageFromSessionVOs.addAll(maintainDamageReportSession.getULDDamageVOs());
	   			}
	   			log.log(Log.FINE,
						"\n\nuldDamageFromSessionVOs from else after ADD",
						uldDamageFromSessionVOs);
				log.log(Log.FINE,
						"\n\nuldDamageVOs from session after ADD---> ",
						maintainDamageReportSession.getULDDamageVO().getUldDamageVOs());
	   			}
	   			else{
	   			maintainDamageReportSession.setULDDamageVOs(null);
	   			}
	   			/*log.log(Log.FINE, "statusFlag ---> " + 
	    				maintainDamageReportForm.getStatusFlag());
	        	ULDDamageVO	uldDamageVOForm=new ULDDamageVO();
				uldDamageVOForm.setDamageCode
						(maintainDamageReportForm.getDamageCode());
				uldDamageVOForm.setDamageReferenceNumber
					(Long.parseLong(maintainDamageReportForm.getDmgRefNo()));
				uldDamageVOForm.setPosition
							(maintainDamageReportForm.getPosition());
				uldDamageVOForm.setSeverity
							(maintainDamageReportForm.getSeverity());
				uldDamageVOForm.setReportedStation
							(maintainDamageReportForm.getRepStn()
													.toUpperCase());
				log.log(Log.FINE, "\n\n maintainDamageReportForm.Closed() ---> " 
						+ maintainDamageReportForm.getClosed());
				if(maintainDamageReportForm.getClosed().equals("on"))
				{
				uldDamageVOForm.setClosed(true);
				}
				else
				{uldDamageVOForm.setClosed(false);
				}
				uldDamageVOForm.setRemarks
							(maintainDamageReportForm.getRemarks());
				uldDamageVOForm.setOperationFlag
						(AbstractVO.OPERATION_FLAG_INSERT);
				long seq=populateSequence(uldDamageVO);
				uldDamageVOForm.setSequenceNumber(seq);
				uldDamageVOForm.setLastUpdateUser
            	(logonAttributes.getUserId());
				uldDamageVO.add(uldDamageVOForm);
				log.log(Log.FINE, "\n\n\n\n uldDamageVO ---> " + uldDamageVO);
	            maintainDamageReportSession.getULDDamageVO().setUldDamageVOs
	            											(uldDamageVO);
	            log.log(Log.FINE, "\n\n\n\n maintainDamageReportSession ---> " 
	            		+ maintainDamageReportSession.getULDDamageVO());*/
			}
	    	log.log(Log.FINE, "After IF and ELSE");
	    //	log.log(Log.FINE,"The totalPoints are"+Integer.parseInt(totalPoints_form));
	    	String totalpoints=null;
	    	//if(maintainDamageReportSession.getDamageTotalPoint()==0){
	    		
	    	Collection<String> parameterTypes = new ArrayList<String>();
			Map<String, String> systemParameterMap = null;
			
			parameterTypes.add(TOTALPOINTS);

	    	SharedDefaultsDelegate sharedDefaultsDelegate =	new SharedDefaultsDelegate();
	    	
	    	try {
				systemParameterMap= sharedDefaultsDelegate.findSystemParameterByCodes(parameterTypes);
			} catch (BusinessDelegateException e) {
				// To be reviewed Auto-generated catch block
				e.getMessage();
			}
			if(systemParameterMap!=null){
				totalpoints=systemParameterMap.get(TOTALPOINTS);
				
log.log(Log.FINE, "The Total Value frm SystemParameter",
						totalpoints);
				
			}
	    	//}
	    	//log.log(Log.FINE,"The totalPoints frm session "+Integer.parseInt(totalpoints));
	    	//log.log(Log.FINE,"The totalPoints frm session "+maintainDamageReportSession.getDamageTotalPoint());
			/*if(Integer.parseInt(totalPoints_form)<Integer.parseInt(totalpoints)){
				maintainDamageReportForm.setOverStatus("S");
			}
			else{
				maintainDamageReportForm.setOverStatus("U");
			}
			log.log(Log.FINE,"THe Form value of OverStatus is"+maintainDamageReportForm.getOverStatus());*/
	    	ArrayList<ULDDamageVO> uldDamageVOAfter = 
	    		maintainDamageReportSession.getULDDamageVO()
	    											.getUldDamageVOs() != null ?
				new ArrayList<ULDDamageVO>(maintainDamageReportSession
										.getULDDamageVO().getUldDamageVOs()) : 
				new ArrayList<ULDDamageVO>();
				log.log(Log.FINE, "The value of uldDamageVOAfter ",
						uldDamageVOAfter);
				boolean isDmgPresent=false;
				 for(ULDDamageVO uldDamageVOafter : uldDamageVOAfter) {
					 if(uldDamageVOafter.getOperationFlag()==null || (uldDamageVOafter.getOperationFlag()!=null &&
							 uldDamageVOafter.getOperationFlag()!=	(AbstractVO.OPERATION_FLAG_INSERT) )
							 && uldDamageVOafter.getOperationFlag()!=	(AbstractVO.OPERATION_FLAG_DELETE))
					 {
						 isDmgPresent=true;
						 break;
					 }
					 
				 }
				 
				 int totalDamagePoints=0;
				 if(uldDamageVOAfter!=null && uldDamageVOAfter.size()>0){
					 for(ULDDamageVO uldDamageVo:uldDamageVOAfter){
						log.log(Log.FINE, "The Closed operations", uldDamageVo.isClosed());
						 if(!uldDamageVo.isClosed()){
						//if((AbstractVO.OPERATION_FLAG_UPDATE !=(uldDamageVo.getOperationFlag()))&& !uldDamageVo.isClosed())
						 if(uldDamageVo.getDamagePoints()!=null ){
							 totalDamagePoints=totalDamagePoints+Integer.parseInt(uldDamageVo.getDamagePoints());
						 }
						  }
						 else{
							 log.log(Log.FINE,"The close checkbox is checked");
						 }
						  }
					 
				 }
				 log.log(Log.FINE,
						"The damageChecklistVO after main screen details",
						totalDamagePoints);
				 if(totalDamagePoints<Integer.parseInt(totalpoints)){
					 //Modified by A-3415 for ICRD-113953
					 //User can manually set overall status. prevent modification unless damage points satisfied.
					 //maintainDamageReportForm.setOverStatus("O");
					}
					else{
						maintainDamageReportForm.setOverStatus("N");
					}
					log.log(Log.FINE, "THe Form value of OverStatus is",
							maintainDamageReportForm.getOverStatus());
				 if(!isDmgPresent)
					 
				 {
					 maintainDamageReportForm.setScreenStatusValue("DMGNOTPRESENT");
					 //maintainDamageReportForm.setScreenStatusFlag("DMGNOTPRESENT"); 
				 }
				 
				 if(maintainDamageReportSession.getULDDamageVO()!=null){
					 log.log(Log.FINE,
							"The Total VO before going to manin SCreen ",
							maintainDamageReportSession.getULDDamageVO());
					 maintainDamageReportSession.getULDDamageVO().setOverallStatus(maintainDamageReportForm.getOverStatus());
					 log.log(Log.FINE, "The OverStatus frm the session",
							maintainDamageReportSession.getULDDamageVO().getOverallStatus());
				 }
				log.log(Log.FINE, "maintainDamageReportForm.getStatusFlag()",
					maintainDamageReportForm.getStatusFlag());
	    	if(ULD_DEF_ADD_DMG.equals(maintainDamageReportForm.getStatusFlag()))
	    	{
	    		maintainDamageReportForm.setStatusFlag
	    								("uld_def_add_dmg_success");
	    	} else {
	    		maintainDamageReportForm.setStatusFlag
	    								("uld_def_update_dmg_success");
	    	}
	    	maintainDamageReportForm.setScreenStatusFlag(ComponentAttributeConstants.COMPONENT_TYPE_DETAIL);
	    	invocationContext.target = ACTION_SUCCESS;
		}
		
		/**
		 * Method to return a sequence number
		 * @param uldDamageVOs
		 * @return dmgseq
		 */
		 public long populateSequence(ArrayList<ULDDamageVO> uldDamageVOs) {
	    	log.entering("AddDamageDetailsCommand", "populateSequence");
	    	long dmgseq=0;
	    	for(ULDDamageVO uldDamageVO:uldDamageVOs)
	    	{
	    		if(uldDamageVO.getSequenceNumber()>dmgseq)
	    		{
	    			dmgseq=uldDamageVO.getSequenceNumber();
	    		}
	    	}
	    	dmgseq=dmgseq+1;
	    	log.exiting("AddDamageDetailsCommand", "populateSequence");
	    	return dmgseq;
	    }
	
		 /**
		 * @param maintainDamageReportForm
		 * @param companyCode 
		 * @return errors
		 */
		private Collection<ErrorVO> validateForm(MaintainDamageReportForm 
							maintainDamageReportForm, String companyCode){
			log.entering("AddDamageDetailsCommand", "validateForm");
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
			if(maintainDamageReportForm.getRepStn()== null || 
					maintainDamageReportForm.getRepStn().trim().length() == 0){
				 error = new ErrorVO(
						 "uld.defaults.maintainDmgRep.msg.err.repstnmandatory");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				
			}else{
				if(validateAirportCodes(maintainDamageReportForm.getRepStn().toUpperCase(),
			    		logonAttributes)!=null){
					errors.add(new ErrorVO(
							"uld.defaults.maintainDmgRep.msg.err.stationinvalid",
							null));
					}
			}
			//Added by Tarun on 19Mar08 For AirNZ418 
		/*	if(maintainDamageReportForm.getFacilityType() == null ||
					maintainDamageReportForm.getFacilityType().trim().length() == 0){
				 error = new ErrorVO(
				 	"uld.defaults.maintainDmgRep.msg.err.factypemandatory");
				 	error.setErrorDisplayType(ErrorDisplayType.ERROR);
				 	errors.add(error);	
			}else{*/
				if(maintainDamageReportForm.getLocation() != null &&
						maintainDamageReportForm.getLocation().trim().length() > 0){
					Collection<ErrorVO> newerrors = new ArrayList<ErrorVO>();
					newerrors = validatelocation(logonAttributes,
							maintainDamageReportForm.getFacilityType(),
							maintainDamageReportForm.getLocation(),maintainDamageReportForm.getRepStn());
					if(newerrors.size() > 0){
						errors.addAll(newerrors);
					}
				}
				
			log.log(Log.FINE, " \n partytype=", maintainDamageReportForm.getPartyType());
			/*if(maintainDamageReportForm.getPartyType() == null ||
					maintainDamageReportForm.getPartyType().trim().length() == 0){
				 error = new ErrorVO(
				 	"uld.defaults.maintainDmgRep.msg.err.partytypemandatory");
				 	error.setErrorDisplayType(ErrorDisplayType.ERROR);
				 	errors.add(error);		
			}else{*/
				if(("G").equals(maintainDamageReportForm.getPartyType()) &&
					(maintainDamageReportForm.getParty() != null ||
						maintainDamageReportForm.getParty().trim().length() > 0)	
				){
					
					Collection<ErrorVO> newerrors = new ArrayList<ErrorVO>();
					newerrors = validateAgentCode(maintainDamageReportForm.getParty(),
							logonAttributes);
					if(newerrors.size() > 0){
						errors.addAll(newerrors);
					}
					
				}
		if(("A").equals(maintainDamageReportForm.getPartyType()) &&
						(maintainDamageReportForm.getParty() != null ||
								maintainDamageReportForm.getParty().trim().length() > 0)){
					log.log(Log.FINE, " \n inside ailrine validation");
					
					try {
			    		AirlineDelegate delegate = new AirlineDelegate();
						delegate.validateAlphaCode(
								logonAttributes.getCompanyCode(),
								maintainDamageReportForm.getParty());			

			    	} catch (BusinessDelegateException e) {
						e.getMessage();
						 error = new ErrorVO(
							"uld.defaults.maintainDmgRep.msg.err.invalidstation");
					   	 	errors.add(error);
						
					}
					
				
				}
					
		//	}
			log.log(Log.FINE, " \n %$%$");
			/*if(maintainDamageReportForm.getParty() == null ||
					maintainDamageReportForm.getParty().trim().length() == 0){
				log.log(Log.FINE, " \n %$%$5");
				error = new ErrorVO(
				 	"uld.defaults.maintainDmgRep.msg.err.partymandatory");
				 	error.setErrorDisplayType(ErrorDisplayType.ERROR);
				 	errors.add(error);	
			}*/
			
			
			log.exiting("AddDamageDetailsCommand", "validateForm");
			return errors;
		}
			
		
		private Collection<ErrorVO> validateMandatory(ULDDamageVO uLDDamageVO){
			log.entering("AddDamageDetailsCommand", "validateForm");
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
			if(uLDDamageVO.getReportedStation()== null || 
					uLDDamageVO.getReportedStation().trim().length() == 0){
				 error = new ErrorVO(
						 "uld.defaults.maintainDmgRep.msg.err.repstnmandatory");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				
			}else{
				if(validateAirportCodes(uLDDamageVO.getReportedStation().toUpperCase(),
			    		logonAttributes)!=null){
					errors.add(new ErrorVO(
							"uld.defaults.maintainDmgRep.msg.err.stationinvalid",
							null));
					}
			}
			
			

		log.exiting("AddDamageDetailsCommand", "validateForm");
		return errors;
		}
		/**
		 * 
		 * @param uldDamageVO
		 * @param actionForm
		 * @param logonAttributes
		 */
		public void updateULDDamageVO
		(ULDDamageVO uldDamageVO,
				MaintainDamageReportForm actionForm,LogonAttributes logonAttributes,MaintainDamageReportSession maintainDamageReportSession) {
		
		log.entering("AddDamageDetailsCommand", "updateULDDamageVO");
		if(uldDamageVO != null) {
			log.log(Log.FINE, "\n\nuldDamageVO ---> ", uldDamageVO);
			log.log(Log.FINE,
					"\n\nactionForm details from updateULDDamgeVO ---> ",
					actionForm.getSeverity());
			if(uldDamageVO.getOperationFlag()==null ){
			/*	if(!uldDamageVO.getDamageCode().equals(actionForm.getDamageCode()))
				{
					actionForm.setFlag("Updated");
					log.log(Log.FINE ,
			    			"\n\n  DAMAGE CODE CHANGED ---> ");
				}*/
				
				
				
				if(!uldDamageVO.getSeverity().equals(actionForm.getSeverity()))
				{
					actionForm.setFlag("Updated");					
				}
				/*if(!uldDamageVO.getPosition().equals(actionForm.getPosition()))
				{
					actionForm.setFlag("Updated");
					log.log(Log.FINE ,
	    			"\n\n  POSITION CHANGED ---> ");
				}*/
				}
			uldDamageVO.setOverStatus(actionForm.getOverStatus());
			if(actionForm.getDmgRefNo()!=null && actionForm.getDmgRefNo().trim().length()!=0){
			uldDamageVO.setDamageReferenceNumber(Long.parseLong(actionForm.getDmgRefNo()));
			} else {
				uldDamageVO.setDamageReferenceNumber(0);
			}
			uldDamageVO.setDamageCode(actionForm.getDamageCode());
			/*
			log.log(Log.FINE ,"\n\n actionForm.getDmgPic()---> "+actionForm.getDmgPic());
			if(actionForm.getDmgPic()!=null)
			{
				actionForm.setFlag("Updated");
				log.log(Log.FINE ,"\n\n  DAMAGE PIC updated ---> ");
			}			
//			start upload picture
			ULDDamagePictureVO uldDamagePictureVO=new ULDDamagePictureVO();
			FormFile formFile = actionForm.getDmgPic();
			if(actionForm.getDmgPic()!=null && formFile.getFileSize()>0){
			log.log(Log.FINE,"The IMAGE in vo is "+uldDamageVO.getPictureVO());
			log.log(Log.FINE,"The IMAGE in form is "+actionForm.getDmgPic());

				byte[] picDetails = null;
				ImageModel image = null;

				try{
				if(formFile.getFileSize()>0){
					log.log(Log.FINE,"\n\n****************formFile.getFileSize()"+
							formFile.getFileSize());
					log.log(Log.FINE,"\n\n****************formFile.getFileName()"+
							formFile.getFileName());
					log.log(Log.FINE,"\n\n***************formFile.getContentType()"+
							formFile.getContentType());
					log.log(Log.FINE,"\n\n****************formFile.getFileData() "+
							formFile.getFileData());

					image = new ImageModel();
					image.setName(formFile.getFileName());
					image.setContentType(formFile.getContentType());
					image.setData(formFile.getFileData());
				}

				uldDamagePictureVO.setCompanyCode(logonAttributes.getCompanyCode());	
			
				if(!uldDamageVO.isPicturePresent() && uldDamageVO.getPictureVO()==null && uldDamageVO.getOperationFlag()==null){
					uldDamagePictureVO.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
				}
				if(uldDamageVO.isPicturePresent() && uldDamageVO.getOperationFlag()==null){
					uldDamagePictureVO.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
				}
				if((AbstractVO.OPERATION_FLAG_INSERT).equals(uldDamageVO.getOperationFlag())){
					uldDamagePictureVO.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
				}
				
				uldDamagePictureVO.setImage(image);
				uldDamagePictureVO.setSequenceNumber(uldDamageVO.getSequenceNumber());
				uldDamageVO.setPictureVO(uldDamagePictureVO);
				uldDamageVO.setPicturePresent(true);
				log.log(Log.FINE,"The IMAGE in vo is "+uldDamageVO.getPictureVO());

			}
			catch (FileNotFoundException e) {
				// To be reviewed Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// To be reviewed Auto-generated catch block
				e.printStackTrace();
			}

			}
			/*else
			{
				if(uldDamageVO.getpgetPictureVO()==null ||
						uldDamageVO.getPictureVO().getImage()==null){
				uldDamagePictureVO.setPicturePresent(false);
				uldDamagePictureVO.setCompanyCode(logonAttributes.getCompanyCode());				
				uldDamagePictureVO.setImage(null);
				uldDamageVO.setPictureVO(uldDamagePictureVO);
				log.log(Log.FINE,"The IMAGE in vo is (ELSE)"+uldDamageVO.getPictureVO());
				}
			}*/
			// end upload picture*/
			//uldDamageVO.setSection(actionForm.getSection());
			uldDamageVO.setPosition(actionForm.getPosition());
			uldDamageVO.setSeverity(actionForm.getSeverity());
			uldDamageVO.setReportedStation(actionForm.getRepStn().toUpperCase());
			uldDamageVO.setLastUpdateUser(logonAttributes.getUserId());
			
			//Added by Tarun for CRQ AirNZ418
			uldDamageVO.setFacilityType(actionForm.getFacilityType());
			uldDamageVO.setLocation(actionForm.getLocation());
			uldDamageVO.setPartyType(actionForm.getPartyType());
			uldDamageVO.setParty(actionForm.getParty());
			uldDamageVO.setDamagePoints(actionForm.getTotalPoints());
			uldDamageVO.setDamageChecklistVOs(maintainDamageReportSession.getULDDamageChecklistVO());
			
			ArrayList<ULDDamageChecklistVO> damageCheckVos= null;
	    	  damageCheckVos=(ArrayList<ULDDamageChecklistVO>)maintainDamageReportSession.getULDDamageChecklistVO();
	    	  if(damageCheckVos!=null && damageCheckVos.size()==1){
	    		  log.log(Log.FINE,
						"The SectionValue from AddDamageDetailsCommand",
						actionForm.getSection());
	    		 uldDamageVO.setSection(damageCheckVos.get(0).getSection());
	    		  log.log(Log.FINE,
						"The SectionValue from AddDamageDetailsCommand ",
						uldDamageVO.getSection());
	    	  }
                else if (damageCheckVos != null && damageCheckVos.size() > 1) {
				  if (actionForm.getSections() != null) {
					String sectionCodes[] = actionForm.getSections().split(",");
					for (String code : sectionCodes) {
						for (ULDDamageChecklistVO uldDamageChecklistVO : damageCheckVos) {
							if (uldDamageChecklistVO.getSection().equals(code)) {
								uldDamageVO.setSection(code); 
							}

						}
					}
				}
			}
	    	  
		
			
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
			ArrayList<ULDDamageChecklistVO> damageCheckVos= null;
	    	  damageCheckVos=(ArrayList<ULDDamageChecklistVO>)maintainDamageReportSession.getULDDamageChecklistVO();
	    	  if(damageCheckVos!=null && damageCheckVos.size()>0){
	    	    for (ULDDamageChecklistVO checklistVO : damageCheckVos) {
	    	    	if (oneTimeVOs != null && oneTimeVOs.size() > 0) {
	    	  			for (OneTimeVO oneTimeVo : oneTimeVOs) {
	    	  				if (oneTimeVo.getFieldValue().equals(checklistVO.getSection())) {
				uldDamageVO.setSection(oneTimeVo.getFieldDescription());
				break;
			}
	    	 }
	    	    	}
	    	    }
	    	  }*/
			
			uldDamageVO.setDamageDescription(actionForm.getDamageDescription());
			//uldDamageVO.setSection(maintainDamageReportSession.getULDDamageChecklistVO().)
			//Added by Tarun for INT ULD370
			//Modified by A-5169 for iCRD-71942
			if(actionForm.getReportedDate()!=null) {
			LocalDate repDate  = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);		
			String reportedDate = actionForm.getReportedDate();
			String repdate = null;
			int hour = repDate.get(LocalDate.HOUR_OF_DAY);
			int minute = repDate.get(LocalDate.MINUTE);
			int seconds = repDate.get(LocalDate.SECOND);
			StringBuilder time = new StringBuilder();
			
			time.append(" ");
			time.append(hour);
			time.append(":");
			time.append(minute);
			time.append(":");
			time.append(seconds);
			String tim = time.toString();
			if(reportedDate != null){
			repdate =reportedDate.concat(tim);		
			}
			repDate.setDateAndTime(repdate);  			
			uldDamageVO.setReportedDate(repDate);
		}
			
			//Added by Tarun for INT ULD370
			if(("on").equals(actionForm.getClosed()))
			{
				uldDamageVO.setClosed(true);
			}
			else
			{	
				uldDamageVO.setClosed(false);
			}
			
			uldDamageVO.setRemarks(actionForm.getRemarks());
			log.log(Log.FINE, "\n\nactionForm.getStatusFlag() ---> ",
					actionForm.getStatusFlag());
			log.log(Log.FINE, "\n\nactionForm.getFlag() ---> ", actionForm.getFlag());
			log.log(Log.FINE, "\n\nuldDamageVO.getOperationFlag() ---> ",
					uldDamageVO.getOperationFlag());
			log.log(Log.FINE, "\n\nuldDamageVO ---> ", uldDamageVO);
			if(("Updated").equals(actionForm.getFlag()))
				{if(uldDamageVO.getOperationFlag()==null)
            	{
					uldDamageVO.setOperationFlag
            		(AbstractVO.OPERATION_FLAG_UPDATE);
					actionForm.setStatusFlag("uld_def_mod_dmg");
            	}
			}
		}
		log.exiting("AddDamageDetailsCommand", "updateULDDamageVO");
	}
		/**
		 * 
		 * @param uldDamageVO
		 * @param actionForm
		 */
		public void populateDmg(
				  ULDDamageVO uldDamageVO,
					MaintainDamageReportForm actionForm) {
	    	log.entering("AddDamageDetailsCommand", "populateDmg");
	    	if (uldDamageVO!=null) {
	    		log.log(Log.FINE,
						"The uldDamageVO Details not null in populateDmg",
						uldDamageVO);
		    	actionForm.setDamageCode(uldDamageVO.getDamageCode());
		    	
		   /* 	// for pic
		    	if(uldDamageVO.getPictureVO()==null || uldDamageVO.getPictureVO().getImage()==null){
		    		actionForm.setDmgPic(null);
		    	}else{
		    	
		    	}
		    	
		    	//for pic*/
	    		actionForm.setDmgRefNo(String.valueOf(uldDamageVO.getDamageReferenceNumber()));
	    		actionForm.setPosition(uldDamageVO.getPosition());
	    		actionForm.setSeverity(uldDamageVO.getSeverity());
	    		if(uldDamageVO.getReportedStation()!=null){
	    		actionForm.setRepStn
								(uldDamageVO.getReportedStation().toUpperCase());
	    		} else {
	    			actionForm.setRepStn("");
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
	    	}
	    	log.log(Log.FINE, "The Form details from populateDmg ", actionForm);
	    	log.exiting("AddDamageDetailsCommand", "populateDmg");
	    }
		/**
	     * @param station
	     * @param logonAttributes
	     * @return errors
	     */
	   public Collection<ErrorVO> validateAirportCodes(
	    		String station,
	    		LogonAttributes logonAttributes){
	    	log.entering("Command", "validateAirportCodes");
	    	log.log(Log.FINE, " Station ---> ", station);
	    	Collection<ErrorVO> errors = null;
	    	
	    	try {
	    		AreaDelegate delegate = new AreaDelegate();
				delegate.validateAirportCode(
						logonAttributes.getCompanyCode(),station);			

	    	} catch (BusinessDelegateException e) {
				e.getMessage();
				log.log(Log.FINE, " Error Airport ---> ", e.getMessageVO().getErrorType());
				errors = handleDelegateException(e);
			}
			log.exiting("Command", "validateAirportCodes");
	    	return errors;
	    }
	   
	   public Collection<ErrorVO> validateAgentCode(
	    		String agentCode,
	    		LogonAttributes logonAttributes){
			ErrorVO error = null;
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			AgentVO agentVO = null;
			try {
				
					agentVO = new AgentDelegate().findAgentDetails(logonAttributes.getCompanyCode(),
							agentCode);
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				handleDelegateException(businessDelegateException);
			}
			 if(agentVO == null){
				 error = new ErrorVO(
	 						"uld.defaults.maintainDmgRep.msg.err.invalidagentcode");
				 errors.add(error);

		}
			return errors;
	    }
	   
	   private Collection<ErrorVO> validatelocation(LogonAttributes logonAttributes,
			   String facilitycode,String location,String airportCode){
			
				
		Collection<ULDAirportLocationVO> uldAirportLocationVOs= null;
	   	ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
	   	String station = null;
	   	if(airportCode!=null){
	   		station = airportCode.toUpperCase();
	   	}else{
	   		station = logonAttributes.getStationCode().toUpperCase();
	   	}
	   	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	   	ErrorVO error = null;
	   	try {
	   		uldAirportLocationVOs = uldDefaultsDelegate.listULDAirportLocation
	   		(logonAttributes.getCompanyCode(),station
	   				,facilitycode.toUpperCase());
	   	}catch(BusinessDelegateException businessDelegateException) {
	   		businessDelegateException.getMessage();
	   		errors = handleDelegateException(businessDelegateException);
	   	}
	   	log.log(Log.FINE, " \n Location", location);
	   	boolean isContain = false;
	   	for(ULDAirportLocationVO vos : uldAirportLocationVOs){
	   		if(vos.getFacilityCode().equals(location)){
	   			isContain = true;
	   		}
	   	}
	   	if(!isContain){
	   	 error = new ErrorVO(
			"uld.defaults.maintainDmgRep.msg.err.invalidlocation");
	   	 	errors.add(error);
	   	}
	   
	   		return errors;
	   }
	   
/*	   private Collection<String> getOneTimeParameterTypes() {
	    	log.entering("DamageDetailsScreenLoadCommand",
	    									"getOneTimeParameterTypes");
	    	ArrayList<String> parameterTypes = new ArrayList<String>();
	   
	       	parameterTypes.add(SECTION_ONETIME);
	    	log.exiting("DamageDetailsScreenLoadCommand",
	    											"getOneTimeParameterTypes");
	    	return parameterTypes;    	
	    }*/
	   
}