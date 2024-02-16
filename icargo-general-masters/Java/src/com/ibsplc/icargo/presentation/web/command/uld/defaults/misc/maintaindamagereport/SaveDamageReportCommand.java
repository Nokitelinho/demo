/*
 * SaveDamageReportCommand.java Created on Feb 07,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.
														maintaindamagereport;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * This command class is used to save the details
 *
 * @author A-1347
 */
public class SaveDamageReportCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Maintain Damage Report");

	private static final String SAVE_SUCCESS = "save_success";
	private static final String SAVE_FAILURE = "save_failure";
    /**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintaindamagereport";

	private static final String SCREENID_ULDERRORLOG ="uld.defaults.ulderrorlog";
	private static final String SAVE_ULDERRORLOG = "save_ulderrorlog";

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

		MaintainDamageReportForm maintainDamageReportForm =
			(MaintainDamageReportForm) invocationContext.screenModel;
		MaintainDamageReportSession maintainDamageReportSession =
				(MaintainDamageReportSession)getScreenSession(MODULE,SCREENID);

		ULDDamageRepairDetailsVO uldDamageRepairDetailsVO =
    		maintainDamageReportSession.getULDDamageVO() != null ?
			maintainDamageReportSession.getULDDamageVO() :
			new ULDDamageRepairDetailsVO();
		Collection<ErrorVO> errors = null;

		if(!("continue").equals(maintainDamageReportForm.getSaveStatus()))
		{
		/**
		 * Validate for client errors
		 */





		if(maintainDamageReportSession.getULDDamageVO().getUldRepairVOs()
					!=null &&
				maintainDamageReportSession.getULDDamageVO().getUldDamageVOs()
					!=null	)
		{
			ArrayList<ULDRepairVO> uldRepairVOs=new ArrayList<ULDRepairVO>
			(maintainDamageReportSession.getULDDamageVO().getUldRepairVOs());
			ArrayList<ULDDamageVO> uldDamageVOs=new ArrayList<ULDDamageVO>
			(maintainDamageReportSession.getULDDamageVO().getUldDamageVOs());

			if(uldDamageVOs!=null && uldDamageVOs.size()>0 )
			{boolean isPresent=false;
				for(ULDDamageVO uldDamageVO:uldDamageVOs){
					
					
					if((uldDamageVO.getOperationFlag()!=null &&
							!uldDamageVO.getOperationFlag().equals
							(AbstractVO.OPERATION_FLAG_DELETE) &&
							!uldDamageVO.getOperationFlag().equals
							(AbstractVO.OPERATION_FLAG_INSERT))||
							(uldDamageVO.getOperationFlag()==null)){
						isPresent=true;
					}
				}
				if(!isPresent){
				if(uldRepairVOs!=null && uldRepairVOs.size()!=0 )
				{
			 ErrorVO error = null;
	    	error = new ErrorVO
	    	("uld.defaults.maintainDmgRep.msg.err.damagedetailsnotpresent"
	    							,null);
	    	error.setErrorDisplayType(ErrorDisplayType.ERROR);
	    	invocationContext.addError(error);
	    	invocationContext.target = SAVE_FAILURE;
			return;
				}}
			}Collection<ErrorVO> errorsVal = new ArrayList<ErrorVO>();
			if(uldRepairVOs!=null && uldRepairVOs.size()>0 &&
					uldDamageVOs!=null && uldDamageVOs.size()>0	)
	    	{
	    		 boolean isPresent=false;
	    		 long dmgRefNo=0;
	    		 for(ULDRepairVO uldRepairVO:uldRepairVOs){
				for(ULDDamageVO uldDamageVO:uldDamageVOs){
					if((uldDamageVO.getOperationFlag()!=null &&
							!uldDamageVO.getOperationFlag().equals
							(AbstractVO.OPERATION_FLAG_DELETE) &&
							!uldDamageVO.getOperationFlag().equals
							(AbstractVO.OPERATION_FLAG_INSERT))||
							(uldDamageVO.getOperationFlag()==null)){
						   if(uldRepairVO.getDamageReferenceNumber()==
							   uldDamageVO.getDamageReferenceNumber())
							{
								if(uldRepairVO.getRepairDate().isLesserThan
										(uldDamageVO.getReportedDate())){
									errorsVal.add(new ErrorVO(
			"uld.defaults.maintainDmgRep.msg.err.damagedategreaterthanrepair"));
						    	}
								isPresent=true;
								dmgRefNo=0;
								break;
							}else
							{
								isPresent=false;
								dmgRefNo=uldRepairVO.getDamageReferenceNumber();
							}

					}

				}
				if(!isPresent)
				{
					break;

				}
	    		 }
	    		 if(!isPresent)
					{

	    			 errorsVal.add( new ErrorVO
	    		("uld.defaults.maintainDmgRep.msg.err.damagerefnotpresent",
	    									new Object[] {dmgRefNo}));

	    	    }
	    		 if(errorsVal != null && errorsVal.size() > 0) {
	    			 invocationContext.addAllError(errorsVal);
	    		    	invocationContext.target = SAVE_FAILURE;
	    				return;
	    		}
				log.log(Log.FINE, "\n\n\n\n dmgRefNo", dmgRefNo);
				log.log(Log.FINE, "\n\n\n\n present", isPresent);
	    	}

			if(uldRepairVOs.size()>0){
				Collection<ErrorVO> errorsDup = new ArrayList<ErrorVO>();
				boolean isDupPresent=false;
				String dupRepairHead="";
				String dupRepairStation="";
				long dupDamageReferenceNumber=0;
				int size=uldRepairVOs.size();
				for(int i=0;i<size;i++){
					int index=0;
					for(ULDRepairVO uldRepairVO:uldRepairVOs){

						if(index!=i && uldRepairVO.getRepairHead().equals(uldRepairVOs.get(i).getRepairHead()) &&
								uldRepairVO.getRepairStation().equals(uldRepairVOs.get(i).getRepairStation()) &&
								uldRepairVO.getDamageReferenceNumber()==(uldRepairVOs.get(i).getDamageReferenceNumber()) &&
								uldRepairVO.getRepairDate().equals(uldRepairVOs.get(i).getRepairDate())
								)
						{
							dupRepairHead=uldRepairVO.getRepairHead();
							dupRepairStation=uldRepairVO.getRepairStation();
							dupDamageReferenceNumber=uldRepairVO.getDamageReferenceNumber();
							isDupPresent=true;
							errorsDup.add(new ErrorVO(
							"uld.defaults.maintainDmgRep.msg.err.duplicateddata",
							new Object[] {dupRepairHead,dupRepairStation,dupDamageReferenceNumber}));
							break;

						}index++;
					}
					if(isDupPresent)
					{
						break;
					}
				}
				if(errorsDup != null && errorsDup.size() > 0) {
	    			 invocationContext.addAllError(errorsDup);
	    		    	invocationContext.target = SAVE_FAILURE;
	    				return;
	    		}

				errors = validateForm
				(maintainDamageReportForm,logonAttributes.getCompanyCode());

				if(errors!=null && errors.size() > 0 ) {
					invocationContext.addAllError(errors);
					invocationContext.target = SAVE_FAILURE;
					return;
				}
				}


			boolean isDmgrefnotClosed=false;
			if(uldRepairVOs!=null && uldRepairVOs.size()>0 )
			{
				for(ULDRepairVO uldRepairVO:uldRepairVOs){




					for(ULDDamageVO uldDamageVO:uldDamageVOs){
						if(uldRepairVO.getDamageReferenceNumber()==uldDamageVO.getDamageReferenceNumber()&&
								!uldDamageVO.isClosed())
						{
							isDmgrefnotClosed=true;
							break;
						}
					}
					if(isDmgrefnotClosed)
					{
						break;

					}
				}
				if(isDmgrefnotClosed)
				{
				maintainDamageReportForm.setSaveStatus("whethertoclose");
				ErrorVO error  = new ErrorVO("uld.defaults.maintainDmgRep.msg.err.dmgrefnonotclosed");
  				error.setErrorDisplayType(ErrorDisplayType.WARNING);
      			invocationContext.addError(error);
    	    	invocationContext.target = SAVE_FAILURE;
    			return;
				}
			}






		}


    }







		if (uldDamageRepairDetailsVO != null ){
			errors = new ArrayList<ErrorVO>();
			try{
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

				if(uldDamageRepairDetailsVO.getUldDamageVOs() != null && uldDamageRepairDetailsVO.getUldDamageVOs().size() > 0){
					for(ULDDamageVO uldDamageVO:uldDamageRepairDetailsVO.getUldDamageVOs()){
						if(uldDamageVO.getPictureVO()!=null && uldDamageVO.getPictureVO().getImage()!=null){
							uldDamageVO.getPictureVO().setUldNumber(uldDamageRepairDetailsVO.getUldNumber());
						}
					}
				}

				//added by A-2883 for CR ANA1476. Modified by A-3415 for ICRD-113953 
				if(uldDamageRepairDetailsVO.getUldDamageVOs()!= null && 
						uldDamageRepairDetailsVO.getUldDamageVOs().size()>0){
					boolean checkclosed=true;
					
					for(ULDDamageVO uldDamageVO:uldDamageRepairDetailsVO.getUldDamageVOs()){
						if(!uldDamageVO.isClosed()){
							checkclosed=false;
						}
					}
					
				if (checkclosed){
					uldDamageRepairDetailsVO.setRepairStatus("R");
					//added by nisha for bugfix on 11Jul08 stars
						//uldDamageRepairDetailsVO.setDamageStatus("N");
					//ends
				}else{
					uldDamageRepairDetailsVO.setRepairStatus("D");
					
log
							.log(
									Log.FINE,
									"\n\n\n\n uldDamageRepairDetailsVO.getDamageStatus(); ",
									uldDamageRepairDetailsVO.getDamageStatus());
					}
				}
				//A-2883 ends
				
				log.log(Log.FINE,
						"\n\n\n\n uldDamageRepairDetailsVO for SAVE> ",
						uldDamageRepairDetailsVO);
				saveDamageRepairDetails(uldDamageRepairDetailsVO);
	    		maintainDamageReportSession.setSavedULDDamageVO(uldDamageRepairDetailsVO);
	    		log.log(Log.FINE, "\n\n\n\n AFTER SAVE");


	      	}catch(BusinessDelegateException businessDelegateException){
	    		businessDelegateException.getMessage();
				businessDelegateException.getMessage();
				errors = handleDelegateException(businessDelegateException);
			}
	      	if(errors!=null && errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}
		}

		if( maintainDamageReportForm.getPageURL()!=null
		&& ("fromulderrorlog").equals(maintainDamageReportForm.getPageURL())) {
    		ULDErrorLogSession uldErrorLogSession =
    				(ULDErrorLogSession)getScreenSession(MODULE,SCREENID_ULDERRORLOG);
 			log
					.log(
							Log.FINE,
							"\n \n maintainDamageReportSession.getULDFlightMessageReconcileDetailsVO()",
							maintainDamageReportSession.getULDFlightMessageReconcileDetailsVO());
			Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
 			try {
 				log.log(Log.FINE, "\n reconcile  delegate " );
 				new ULDDefaultsDelegate().reconcileUCMULDError(maintainDamageReportSession.getULDFlightMessageReconcileDetailsVO());
 			}
 			catch(BusinessDelegateException businessDelegateException) {
 				businessDelegateException.getMessage();
 				exception = handleDelegateException(businessDelegateException);
    		}
 			if(exception!=null && exception.size() > 0 ) {
				invocationContext.addAllError(exception);
				invocationContext.target = SAVE_FAILURE;
				return;
			}
 			
 			uldErrorLogSession.setPageURL("frommaintaindmgrep");
 			maintainDamageReportSession.removeAllAttributes();
 			invocationContext.target = SAVE_ULDERRORLOG;
 			return;

 		}
		
		 ErrorVO error = new ErrorVO("uld.defaults.maintaindamagereport.savedsuccessfully");
	     error.setErrorDisplayType(ErrorDisplayType.STATUS);
	     errors = new ArrayList<ErrorVO>();
	     errors.add(error);
	     invocationContext.addAllError(errors);
		invocationContext.target = SAVE_SUCCESS;
    }
	/**
	 * @param maintainDamageReportForm
	 * @param companyCode
	 * @return errors
	 */

	private Collection<ErrorVO> validateForm
	(MaintainDamageReportForm maintainDamageReportForm, String companyCode){
		log.entering("SaveDamageReportCommand", "validateForm");

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if(maintainDamageReportForm.getSupervisor()== null ||
				maintainDamageReportForm.getSupervisor().trim().length() == 0){
			 error = new ErrorVO(
					 "uld.defaults.maintainDmgRep.msg.err.supervisormandatory");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if(maintainDamageReportForm.getInvRep()== null ||
				maintainDamageReportForm.getInvRep().trim().length() == 0){
			 error = new ErrorVO(
					 "uld.defaults.maintainDmgRep.msg.err.invrepmandatory");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		log.exiting("SaveDamageReportCommand", "validateForm");
		return errors;
	}
	/**
     * Method to save the save uldDamageRepairDetailsVO
     * @param uldDamageRepairDetailsVO
     * @return void
     * @throws BusinessDelegateException
     */
    private void saveDamageRepairDetails (ULDDamageRepairDetailsVO
    										uldDamageRepairDetailsVO)
    						throws BusinessDelegateException{
    	log.entering("SaveCommand","saveDamageRepairDetails");
    	ULDDefaultsDelegate uldDefaultsDelegate =
									new ULDDefaultsDelegate();
    	uldDefaultsDelegate.saveULDDamage(uldDamageRepairDetailsVO);
    	log.exiting("SaveCommand","saveDamageRepairDetails");
    	return ;
    }
}
