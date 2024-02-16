/*
 * SelectNextRepCommand.java Created on Feb 08,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
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
import com.ibsplc.xibase.util.time.TimeConvertor;


/**
 * @author A-1862
 *
 */
public class SelectNextRepCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Maintain Damage Report");
	
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintaindamagereport";
	/**
	 * Target constants
	 */
	private static final String OPR_ACTION_SUCCESS
		= "uld.defaults.maintainrep";
		
	/**
	 * The execute method for SelectNextRepCommand
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
		ArrayList<ULDRepairVO> uldRepairVOs =
			(ArrayList<ULDRepairVO>)
								maintainDamageReportSession.getULDRepairVOs();
		ArrayList<String> refNos =
			(ArrayList<String>)
			maintainDamageReportSession.getRefNo();
		log.log(Log.FINE, "\n\nuldDamageVOs before select next ---> ",
				uldRepairVOs);
		log.log(Log.FINE, "\n\nrefNos before select next ---> ", refNos);
		int displayIndex =
			Integer.parseInt(actionForm.getRepcurrentPageNum())-1;
		ULDRepairVO uldRepairVO = 
            							uldRepairVOs.get(displayIndex);
		String refno = refNos.get(displayIndex);
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>(); 
		
		
		updateULDRepairVO
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
			
		refNos.set(displayIndex,refno);
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
			invocationContext.addAllError(errors);	
							
			int displayPageNum = Integer.parseInt
										(actionForm.getRepdisplayPage());	
			log.log(Log.FINE, "\n\n\n**DisplayPage val-->", displayPageNum);
			actionForm.setRepdisplayPage(actionForm.getRepcurrentPageNum());
						
			invocationContext.target = OPR_ACTION_SUCCESS;
			return;
		}
	
		log.log(Log.FINE, "\n\nuldDamageVOs after select next ---> ",
				uldRepairVOs);
		log.log(Log.FINE, "\n\nrefNos after select next ---> ", refNos);
		maintainDamageReportSession.setULDRepairVOs(uldRepairVOs);
		maintainDamageReportSession.setRefNo(refNos);

		int displayPage = Integer.parseInt(actionForm.getRepdisplayPage())-1;
		ULDRepairVO uLDRepairVO = uldRepairVOs.get(displayPage);
		String refNo = refNos.get(displayPage);
		populateRep(uLDRepairVO,refNo,actionForm);

		actionForm.setRepcurrentPageNum(actionForm.getRepdisplayPage());
		invocationContext.target = OPR_ACTION_SUCCESS;
	}

	
/**
 * 
 * @param uldRepairVO
 * @param actionForm
 * @param logonAttributes
 * @param refno
 */
	public void updateULDRepairVO
			(ULDRepairVO uldRepairVO,
		MaintainDamageReportForm actionForm,LogonAttributes logonAttributes,String refno) {
		log.entering("SelectNextCommand", "updateULDRepairVO");
		refno = (refno != null) ? refno : "";
		if(uldRepairVO != null) {
			uldRepairVO.setRepairHead
        	(actionForm.getRepHead());
			uldRepairVO.setRepairStation
        	(actionForm.getRepairStn().toUpperCase());
			LocalDate localDate = null;
			if(uldRepairVO.getRepairStation()!= null && 
					uldRepairVO.getRepairStation().trim().length() != 0 
					&& validateAirportCodes(
						uldRepairVO.getRepairStation(),logonAttributes) == null	){
				localDate = new LocalDate(uldRepairVO.getRepairStation(),Location.ARP,false);
			}
			else {
				localDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
			}
        	if(actionForm.getRepairDate()!=null && 
        			actionForm.getRepairDate().trim().length()!=0){
        	uldRepairVO.setRepairDate(localDate.setDate
        		(actionForm.getRepairDate()));
        	} else {
				uldRepairVO.setRepairDate(null);
			}
        	if(actionForm.getDmgRepairRefNo()!=null && 
        			actionForm.getDmgRepairRefNo().trim().length()!=0)
			{
        		refno=actionForm.getDmgRepairRefNo();
			} 
        	
        	if(actionForm.getAmount()!=null && 
        			actionForm.getAmount().trim().length()!=0)
			{
        	uldRepairVO.setDisplayAmount
        	(Double.parseDouble(actionForm
        								.getAmount()));
			} else {
				uldRepairVO.setDisplayAmount(0.0);
			}
        	
        	uldRepairVO.setCurrency
        			(actionForm.getCurrency());
        	uldRepairVO.setRemarks
        			(actionForm.getRepRemarks());
        	uldRepairVO.setLastUpdateUser
        	(logonAttributes.getUserId());
			
		}
		log.exiting("SelectNextCommand", "updateULDRepairVO");
	}
	
	
/**
 * 
 * @param uLDRepairVO
 * @param refno
 * @param actionForm
 */
	  public void populateRep(
			  ULDRepairVO uLDRepairVO,String refno,
				MaintainDamageReportForm actionForm) {
    	log.entering("SelectNextCommand", "populateRep");

    	if (uLDRepairVO!=null) {
	    	
    		actionForm.setRepHead(uLDRepairVO.getRepairHead());
    		actionForm.setRepairStn(uLDRepairVO.getRepairStation());
    		if(uLDRepairVO.getRepairDate()!=null){
    		String date = TimeConvertor.toStringFormat
    						(uLDRepairVO.getRepairDate().toCalendar(),
    				TimeConvertor.CALENDAR_DATE_FORMAT);
    		actionForm.setRepairDate(date);
    		} else {
				actionForm.setRepairDate("");
			}
    		actionForm.setDmgRepairRefNo
    				(refno);
    		actionForm.setAmount(String.valueOf
    				                      (uLDRepairVO.getDisplayAmount()));
    		actionForm.setCurrency(uLDRepairVO.getCurrency());
    		actionForm.setRepRemarks(uLDRepairVO.getRemarks());
    		
    	}
    	log.exiting("SelectNextCommand", "ULDRepairVO");
    }
	 
	  
	  private Collection<ErrorVO> validateMandatory(ULDRepairVO uLDRepairVO){
			log.entering("SelectNextCommand", "validateForm");
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
			boolean isValidAirport = false;
			if(uLDRepairVO.getRepairStation()== null || 
					uLDRepairVO.getRepairStation().trim().length() == 0){
				 error = new ErrorVO(
						 "uld.defaults.maintainDmgRep.msg.err.repstnmandatory");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				
			}else{
				if(validateAirportCodes(uLDRepairVO.getRepairStation()
						.toUpperCase(),logonAttributes)!=null){
					errors.add(new ErrorVO(
						"uld.defaults.maintainDmgRep.msg.err.stationinvalid",
							null));
				}
				else {
					isValidAirport = true;
				}
			}
			
			if(uLDRepairVO.getRepairDate()== null || 
					String.valueOf(uLDRepairVO.getRepairDate())
												.trim().length() == 0){
					 error = new ErrorVO(
					 "uld.defaults.maintainDmgRep.msg.err.repdatemandatory");
					 error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}else
				{
					if(isValidAirport) {
							LocalDate currentdate = new LocalDate(
									logonAttributes.getAirportCode(),Location.ARP,false);
					    	if(currentdate.before(uLDRepairVO.getRepairDate())) {
					    		errors.add(new ErrorVO(
					    			"uld.defaults.maintainDmgRep.msg.err.repairdategreaterthancurrent"));
					    	}
					}
					
				}
				/*if(String.valueOf(uLDRepairVO.getDamageReferenceNumber())
						                 .trim().length() == 0){
					 error = new ErrorVO(
					 "uld.defaults.maintainDmgRep.msg.err.dmgrefnomandatory");
					 error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}*/
				if(uLDRepairVO.getDisplayAmount()== 0.0 || 
						String.valueOf(uLDRepairVO.getDisplayAmount())
						                           .trim().length() == 0){
					 error = new ErrorVO(
						 "uld.defaults.maintainDmgRep.msg.err.amountmandatory");
					 error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}

		log.exiting("SelectNextCommand", "validateForm");
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
