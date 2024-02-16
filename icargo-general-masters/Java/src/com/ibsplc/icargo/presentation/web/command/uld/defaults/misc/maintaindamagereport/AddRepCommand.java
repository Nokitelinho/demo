/*
 * AddRepCommand.java Created on Feb 09,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.
											misc.maintaindamagereport;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc
											.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc
											.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1862
 *
 */
public class AddRepCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Maintain Damage Report");
	
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintaindamagereport";
	/*
	 * The constant for actions - assign success
	 */
	private static final String ADDREP_SUCCESS = "addrep_success";
		
	/**
	 * The execute method for AddRepCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute
	 * (com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		MaintainDamageReportForm actionForm = (MaintainDamageReportForm)
												invocationContext.screenModel;
		MaintainDamageReportSession maintainDamageReportSession =
			(MaintainDamageReportSession)getScreenSession(MODULE,
					SCREENID);
		
		int displayIndex = Integer.parseInt(
				actionForm.getRepcurrentPageNum())-1;
				
		
		
		ArrayList<ULDRepairVO> uldRepairVOs =
			(ArrayList<ULDRepairVO>)
								maintainDamageReportSession.getULDRepairVOs();
		ArrayList<String> refnos =
			(ArrayList<String>)
								maintainDamageReportSession.getRefNo();
		log.log(Log.FINE, "\n\nuldDamageVOs before add ---> ", uldRepairVOs);
		log.log(Log.FINE, "\n\nrefnos before add ---> ", refnos);
		SelectNextRepCommand command = new SelectNextRepCommand();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>(); 
		if(uldRepairVOs != null && uldRepairVOs.size() > 0){
			ULDRepairVO uldRepairVO = 
				                  uldRepairVOs.get(displayIndex);
			String refno = refnos.get(displayIndex);
			command.updateULDRepairVO
						(uldRepairVO, actionForm,logonAttributes,refno);
			if(actionForm.getDmgRepairRefNo()!=null && 
        			actionForm.getDmgRepairRefNo().trim().length()!=0)
			{
        		refno=actionForm.getDmgRepairRefNo();
        		log.log(Log.FINE, "\n\nactionForm.getDmgRepairRefNo()---> ",
						actionForm.getDmgRepairRefNo());
			} else {
				refno="";
			}
   			
			refnos.set(displayIndex,refno);
   			log.log(Log.FINE, "\n\nrefno ---> ", refno);
			ErrorVO error = null;
			
   			
			errors=validateMandatory(uldRepairVO);
			if(refno.trim().length() == 0){
				error = new ErrorVO(
				"uld.defaults.maintainDmgRep.msg.err.dmgrefnomandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				}
			ArrayList<ULDDamageVO> uldDamageVOs=new ArrayList<ULDDamageVO>
			(maintainDamageReportSession.getULDDamageVO().getUldDamageVOs());
			if(uldDamageVOs!=null && uldDamageVOs.size()>0	)
	    	{
	    		 boolean isDatePresent=true;
	    		
	    		
		   				
		   				String[] selectedRefNo = refno.split(",");
		   				for(int i=0;i < selectedRefNo.length; i++) {
		   					ULDRepairVO uldRepairVOtmp= new ULDRepairVO();
		   					try{
		   					BeanHelper.copyProperties(uldRepairVOtmp,uldRepairVOs.get(displayIndex));
			   				} catch (SystemException systemException) {
			   					systemException.getMessage();
			   				} 
				for(ULDDamageVO uldDamageVO:uldDamageVOs){
					if((uldDamageVO.getOperationFlag()!=null && 
							!uldDamageVO.getOperationFlag().equals
							(AbstractVO.OPERATION_FLAG_DELETE) &&
							!uldDamageVO.getOperationFlag().equals
							(AbstractVO.OPERATION_FLAG_INSERT))|| 
							(uldDamageVO.getOperationFlag()==null)){
						
							if(selectedRefNo[i].trim().length() != 0 && Long.parseLong(selectedRefNo[i])==
								uldDamageVO.getDamageReferenceNumber())
							{
								
						    	if(uldRepairVOtmp.getRepairDate()!=null && 
						    			uldRepairVOtmp.getRepairDate().isLesserThan
						    			(uldDamageVO.getReportedDate())){
						    		errors.add(new ErrorVO(
			"uld.defaults.maintainDmgRep.msg.err.damagedategreaterthanrepair"));
						    		isDatePresent=false;
						    		break;
						    	}
							}												
					}
				
	    		}
				if(!isDatePresent)
				{break;
				}
				}
	    		log.log(Log.FINE, "\n\n\n\n present", isDatePresent);
	    	}
		if(errors != null && errors.size() > 0) {
			log.log(Log.FINE, "exception");
			int displayPageNum = Integer.parseInt
										(actionForm.getRepdisplayPage());	
			log.log(Log.FINE, "\n\n\n**DisplayPage val-->", displayPageNum);
			actionForm.setRepdisplayPage(actionForm.getRepcurrentPageNum());
			invocationContext.addAllError(errors);	
			
			invocationContext.target = ADDREP_SUCCESS;
			return;
		}
		}
		ULDRepairVO newULDRepairVO = new ULDRepairVO();
		String newString="";
		if(uldRepairVOs != null) {
			newULDRepairVO.setOperationFlag
			(AbstractVO.OPERATION_FLAG_INSERT);
			log.log(Log.FINE,
					"\n\n logonAttributes.getAirlineIdentifier()--> ",
					logonAttributes.getOwnAirlineIdentifier());
			newULDRepairVO.setAirlineIdentifier(logonAttributes.getOwnAirlineIdentifier());
			//newULDRepairVO.setAirlineIdentifier(1083);
			newULDRepairVO.setRepairSequenceNumber(
			(populateSequence(uldRepairVOs)));
			newULDRepairVO.setLastUpdateUser(logonAttributes.getUserId());
			uldRepairVOs.add(newULDRepairVO);
			refnos.add(newString);
			
		} 
		log.log(Log.FINE, "\n\nuldDamageVOs after add ---> ", uldRepairVOs);
		log.log(Log.FINE, "\n\nrefnos after add ---> ", refnos);
		maintainDamageReportSession.setULDRepairVOs(uldRepairVOs);
		maintainDamageReportSession.setRefNo(refnos);
		command.populateRep(newULDRepairVO,newString,actionForm);

		int totalRecords = 0;
		if(refnos != null) {
	    	for(String ref : refnos) {
	    		totalRecords++;
	    		}
	    }
     	actionForm.setReptotalRecords(String.valueOf(totalRecords));
    	actionForm.setReplastPageNum(actionForm.getReptotalRecords());
    	actionForm.setRepdisplayPage(actionForm.getReptotalRecords());
    	actionForm.setRepcurrentPageNum(actionForm.getRepdisplayPage());
     	
		invocationContext.target = ADDREP_SUCCESS;
	}
	/**
	 * 
	 * @param uldRepairVOs
	 * @return
	 */
	 public long populateSequence(ArrayList<ULDRepairVO> 
	 										uldRepairVOs) {
	    	log.entering("AddRepCommand", "populateSequence");
	    	long seq=0;
	    	for(ULDRepairVO uldRepairVO:uldRepairVOs)
	    	{
	    		if(uldRepairVO.getRepairSequenceNumber()>seq)
	    		{
	    			seq=uldRepairVO.getRepairSequenceNumber();
	    		}
	    	}
	    	seq=seq+1;
	    	log.exiting("AddRepCommand", "populateSequence");
    	return seq;
    	
    }
	 
	 
	/**
	 * 
	 * @param uLDRepairVO
	 * @return
	 */
	 private Collection<ErrorVO> validateMandatory(ULDRepairVO uLDRepairVO){
			log.entering("AddRepCommand", "validateMandatory");
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
			if(uLDRepairVO.getRepairStation()== null || 
					uLDRepairVO.getRepairStation().trim().length() == 0){
				 error = new ErrorVO(
						 "uld.defaults.maintainDmgRep.msg.err.repstnmandatory");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				
			}else{
		if(validateAirportCodes(uLDRepairVO.getRepairStation().toUpperCase(),
			    		logonAttributes)!=null){
					errors.add(new ErrorVO(
						"uld.defaults.maintainDmgRep.msg.err.stationinvalid",
							null));
		}}
			if(uLDRepairVO.getRepairDate()== null || 
			String.valueOf(uLDRepairVO.getRepairDate()).trim().length() == 0){
					 error = new ErrorVO(
					 "uld.defaults.maintainDmgRep.msg.err.repdatemandatory");
					 error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}else
				{
					
					LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
			    	if(currentdate.isLesserThan(uLDRepairVO.getRepairDate())){
			    		errors.add(new ErrorVO(
		"uld.defaults.maintainDmgRep.msg.err.repairdategreaterthancurrent"));
			    	}
					
				}
				
				if(uLDRepairVO.getDisplayAmount()== 0.0 || 
						String.valueOf(uLDRepairVO.getDisplayAmount())
													.trim().length() == 0){
					 error = new ErrorVO(
						 "uld.defaults.maintainDmgRep.msg.err.amountmandatory");
					 error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}

		log.exiting("AddRepCommand", "validateMandatory");
		return errors;
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
}
