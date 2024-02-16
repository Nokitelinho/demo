/*
 * AddDmgCommand.java Created on Feb 08, 2006
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
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
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
public class AddDmgCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("LIST Damage Report");
	
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintaindamagereport";
	/*
	 * The constant for actions - assign success
	 */
	private static final String ADDDMG_SUCCESS = "adddmg_success";
		
	/**
	 * The execute method for AddDmgCommand
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
				actionForm.getDmgcurrentPageNum())-1;		
		
		ArrayList<ULDDamageVO> uldDamageVOs =
			(ArrayList<ULDDamageVO>)
								maintainDamageReportSession.getULDDamageVOs();
		log.log(Log.FINE, "\n\nuldDamageVOs before add ---> ", uldDamageVOs);
		SelectNextDmgCommand command = new SelectNextDmgCommand();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>(); 
		if(uldDamageVOs != null && uldDamageVOs.size() > 0){
			ULDDamageVO uldDamageVO = 
				                  uldDamageVOs.get(displayIndex);
			command.updateULDDamageVO
						(uldDamageVO, actionForm,logonAttributes);
			
			errors=validateMandatory(uldDamageVO);
		if(errors != null && errors.size() > 0) {
			log.log(Log.FINE, "exception");
			int displayPageNum = Integer.parseInt(actionForm.getDmgdisplayPage());	
			log.log(Log.FINE, "\n\n\n**DisplayPage val-->", displayPageNum);
			actionForm.setDmgdisplayPage(actionForm.getDmgcurrentPageNum());
			invocationContext.addAllError(errors);	
			
			invocationContext.target = ADDDMG_SUCCESS;
			return;
		}
		}
		ULDDamageVO newULDDamageVO = new ULDDamageVO();
		if(uldDamageVOs != null) {
			//LocalDate ldate=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
			newULDDamageVO.setOperationFlag
			(AbstractVO.OPERATION_FLAG_INSERT);
			newULDDamageVO.setSequenceNumber(
			(populateSequence(uldDamageVOs)));
			//newULDDamageVO.setReportedDate(ldate);
			newULDDamageVO.setLastUpdateUser
        	(logonAttributes.getUserId());
			uldDamageVOs.add(newULDDamageVO);
			
		} 
		log.log(Log.FINE, "\n\nuldDamageVOs after add ---> ", uldDamageVOs);
		maintainDamageReportSession.setULDDamageVOs(uldDamageVOs);
		
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
     	
		invocationContext.target = ADDDMG_SUCCESS;
	}
	/**
	 * Method to return a sequence number
	 * @param uldDamageVOs
	 * @return dmgseq
	 */
	 public long populateSequence(ArrayList<ULDDamageVO> 
	 										uldDamageVOs) {
	    	log.entering("AddDmgCommand", "populateSequence");
	    	long dmgseq=0;
	    	for(ULDDamageVO uldDamageVO:uldDamageVOs)
	    	{
	    		if(uldDamageVO.getSequenceNumber()>dmgseq)
	    		{
	    			dmgseq=uldDamageVO.getSequenceNumber();
	    		}
	    	}
	    	dmgseq=dmgseq+1;
	    	log.exiting("AddDmgCommand", "populateSequence");
    	return dmgseq;
    	
    }
		/**
		 * Method to validate 
		 * @param uLDDamageVO
		 * @return errors
		 */
	 private Collection<ErrorVO> validateMandatory(ULDDamageVO uLDDamageVO){
			log.entering("AddDmgCommand", "validateMandatory");
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
		}}

		log.exiting("AddDamageDetailsCommand", "validateForm");
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
				log.log(Log.FINE, " Error Airport ---> ", e.getMessageVO().getErrorType());
				errors = handleDelegateException(e);
			}
			log.exiting("Command", "validateAirportCodes");
	    	return errors;
	    }
}
