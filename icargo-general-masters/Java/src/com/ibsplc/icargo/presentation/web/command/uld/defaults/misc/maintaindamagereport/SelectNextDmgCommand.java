/*
 * SelectNextDmgCommand.java Created on Dec 23, 2005
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
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
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
public class SelectNextDmgCommand extends BaseCommand {

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
		= "uld.defaults.maintaindmg";
		
	/**
	 * The execute method for SelectNextDmgCommand
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
				
		ArrayList<ULDDamageVO> uldDamageVOs =
			(ArrayList<ULDDamageVO>)
								maintainDamageReportSession.getULDDamageVOs();
		
		log.log(Log.FINE, "\n\nuldDamageVOs before select next ---> ",
				uldDamageVOs);
		int displayIndex =
			Integer.parseInt(actionForm.getDmgcurrentPageNum())-1;
		ULDDamageVO uldDamageVO = 
            							uldDamageVOs.get(displayIndex);
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>(); 
		
		
		updateULDDamageVO(uldDamageVO, actionForm,logonAttributes);
		
		errors=validateMandatory(uldDamageVO);
		if(errors != null && errors.size() > 0) {
			log.log(Log.FINE, "exception");
			invocationContext.addAllError(errors);	
							
			int displayPageNum = Integer.parseInt
									(actionForm.getDmgdisplayPage());	
			log.log(Log.FINE, "\n\n\n**DisplayPage val-->", displayPageNum);
			actionForm.setDmgdisplayPage(actionForm.getDmgcurrentPageNum());
						
			invocationContext.target = OPR_ACTION_SUCCESS;
			return;
		}
	
		log.log(Log.FINE, "\n\nuldDamageVOs after select next ---> ",
				uldDamageVOs);
		maintainDamageReportSession.setULDDamageVOs(uldDamageVOs);

		int displayPage = Integer.parseInt(actionForm.getDmgdisplayPage())-1;
		ULDDamageVO uLDDamageVO = uldDamageVOs.get(displayPage);
		populateDmg(uLDDamageVO, actionForm);

		actionForm.setDmgcurrentPageNum(actionForm.getDmgdisplayPage());
		invocationContext.target = OPR_ACTION_SUCCESS;
	}

	/**
	 * Method to update the selected ULDDamageVO
	 * with the values set in form
	 * @param uldDamageVO
	 * @param actionForm
	 * @param logonAttributes
	 */
	public void updateULDDamageVO
			(ULDDamageVO uldDamageVO,
					MaintainDamageReportForm actionForm,
										LogonAttributes logonAttributes) {
		log.entering("SelectNextCommand", "updateULDDamageVO");
		if(uldDamageVO != null) {
						
			if(uldDamageVO.getOperationFlag()==null ){
				if(!uldDamageVO.getDamageCode().equals
											(actionForm.getDamageCode()))
				{
					actionForm.setFlag("Updated");
					log.log(Log.FINE ,
			    			"\n\n  DAMAGE CODE CHANGED ---> ");
				}
				if(!uldDamageVO.getSeverity().equals(actionForm.getSeverity()))
				{
					actionForm.setFlag("Updated");
					log.log(Log.FINE ,
	    			"\n\n  SEVERITY CHANGED ---> ");
				}
				if(!uldDamageVO.getPosition().equals(actionForm.getPosition()))
				{
					actionForm.setFlag("Updated");
					log.log(Log.FINE ,
	    			"\n\n  POSITION CHANGED ---> ");
				}}
			
			if(actionForm.getDmgRefNo()!=null && 
							actionForm.getDmgRefNo().trim().length()!=0){
				uldDamageVO.setDamageReferenceNumber
									(Long.parseLong(actionForm.getDmgRefNo()));
				} else {
				uldDamageVO.setDamageReferenceNumber(0);
			}
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
				if((AbstractVO.OPERATION_FLAG_INSERT).equals(uldDamageVO.getOperationFlag())){
					uldDamagePictureVO.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
				}
				if(uldDamageVO.isPicturePresent() && uldDamageVO.getOperationFlag()==null){
					uldDamagePictureVO.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
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
			*/
			//Added by Tarun for CRQ AirNZ418
			uldDamageVO.setFacilityType(actionForm.getFacilityType());
			uldDamageVO.setLocation(actionForm.getLocation());
			uldDamageVO.setPartyType(actionForm.getPartyType());
			uldDamageVO.setParty(actionForm.getParty());
			//Added by Tarun for CRQ AirNZ418 ends
			
			//Added by Tarun for INT ULD370
			LocalDate fromDate = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
    		
			uldDamageVO.setReportedDate(fromDate.setDate(actionForm.getReportedDate()));
			//Added by Tarun for INT ULD370 ends
			
			uldDamageVO.setDamageCode(actionForm.getDamageCode());
			uldDamageVO.setPosition(actionForm.getPosition());
			uldDamageVO.setSeverity(actionForm.getSeverity());
			uldDamageVO.setReportedStation
									(actionForm.getRepStn().toUpperCase());
			uldDamageVO.setLastUpdateUser(logonAttributes.getUserId());
			if(("on").equals(actionForm.getClosed()))
			{
				uldDamageVO.setClosed(true);
			}
			else
			{	
				uldDamageVO.setClosed(false);
			}
			
			uldDamageVO.setRemarks(actionForm.getRemarks());
			if(("Updated").equals(actionForm.getFlag()))
				{if(uldDamageVO.getOperationFlag()==null)
            	{
					uldDamageVO.setOperationFlag
            		(AbstractVO.OPERATION_FLAG_UPDATE);
					actionForm.setStatusFlag("uld_def_mod_dmg");
            	}
			}
			
		}
		log.exiting("SelectNextCommand", "updateULDDamageVO");
	}
	/**
	 * Method to update the selected ULDDamageVO
	 * with the values set in form
	 * @param uLDDamageVO
	 * @param actionForm
	 * @return
	 */
	  public void populateDmg(
			  ULDDamageVO uLDDamageVO,
				MaintainDamageReportForm actionForm) {
    	log.entering("SelectNextCommand", "populateDmg");

    	if (uLDDamageVO!=null) {
	    	
    		actionForm.setDamageCode
			(uLDDamageVO.getDamageCode());
    		actionForm.setDmgRefNo
			(String.valueOf
					(uLDDamageVO.getDamageReferenceNumber()));
    		actionForm.setPosition
								(uLDDamageVO.getPosition());
    		actionForm.setSeverity
							(uLDDamageVO.getSeverity());
    		if(uLDDamageVO.getReportedStation()!=null){
    		actionForm.setRepStn
							(uLDDamageVO.getReportedStation().toUpperCase());
    		} else {
				actionForm.setRepStn("");
			}
    		
//    		Added by Manaf for INT ULD489
    			actionForm.setLocation("");
    			actionForm.setParty("");
    			
			if(uLDDamageVO.isClosed()){
				actionForm.setClosed("on");
			}
			else
			{
				actionForm.setClosed(null);
			}
			actionForm.setRemarks
								(uLDDamageVO.getRemarks());
    		
    	}
    	log.exiting("SelectNextCommand", "populateDmg");
    }
	  private Collection<ErrorVO> validateMandatory(ULDDamageVO uLDDamageVO){
			log.entering("SelectNextDmgCommand", "validateMandatory");
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
				if(validateAirportCodes
							(uLDDamageVO.getReportedStation().toUpperCase(),
			    		logonAttributes)!=null){
					errors.add(new ErrorVO(
						"uld.defaults.maintainDmgRep.msg.err.stationinvalid",
							null));
		}}

		log.exiting("SelectNextDmgCommand", "validateMandatory");
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
