/*
 * UpdateRecordULDMovementCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.recorduldmovement;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.RecordUldMovementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.RecordULDMovementForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 *
 * @author A-1936 Karthick.V
 *
 */
public class UpdateRecordULDMovementCommand extends BaseCommand {

	 private Log log=LogFactory.getLogger("ULD_MANAGEMENT");

	 /**
	  * @param invocationContext
	  * @throws CommandInvocationException
	  */
	 public void execute(InvocationContext invocationContext)
	   throws CommandInvocationException {
         log.entering("UPDATE","RECORD ULD MOVEMENT FORM");
         log.entering("UPDATE","RECORD ULD MOVEMENT FORM");
         RecordULDMovementForm  recordULDMovementForm = (RecordULDMovementForm) invocationContext.screenModel;
         RecordUldMovementSession session = (RecordUldMovementSession)getScreenSession( "uld.defaults","uld.defaults.misc.recorduldmovement");


           String[] carrierCode=recordULDMovementForm.getCarrierCode();
		   String[] content = recordULDMovementForm.getContent();
		   String[] flightDate =recordULDMovementForm.getFlightDateString();
		   String[]  flightNumber=recordULDMovementForm.getFlightNumber();
		   String[] pointOfLading = recordULDMovementForm.getPointOfLading();
		   String[] pointOfUnLading=recordULDMovementForm.getPointOfUnLading();
		   String[] chkValues = recordULDMovementForm.getDummyMovement();
		   Collection<String> checkValuesColl = new ArrayList<String>();
		   Collection<ULDMovementVO> uldMovementVos= null;
		   Collection<String> uldNos= null;
		   String[] uldNumbers= recordULDMovementForm.getUldNumber();
		   String[] opFlag = recordULDMovementForm.getHiddenOpFlagForULD();
		   String[] opFlagFlight = recordULDMovementForm.getHiddenOpFlag();

		     if(chkValues!=null){
		           for ( int i=0;i<chkValues.length;i++){
		    	   checkValuesColl.add(chkValues[i]);
		        }
		      }

		     if(carrierCode!=null){
			     uldMovementVos =new ArrayList<ULDMovementVO>();
			         for(int i=0;i<carrierCode.length;i++){
						if(!"NOOP".equals(opFlagFlight[i])){
								 ULDMovementVO vo = new ULDMovementVO();
								 vo.setCarrierCode(carrierCode[i]);
								 vo.setContent(content[i]);
								 if(checkValuesColl.contains(String.valueOf(i))){
									  vo.setDummyMovement(true);
								 }else{
									 vo.setDummyMovement(false);
								 }
								vo.setFlightDateString(flightDate[i]);
								vo.setFlightNumber(flightNumber[i].toUpperCase());
								vo.setPointOfLading(pointOfLading[i]);
								vo.setPointOfUnLading(pointOfUnLading[i]);
								vo.setUpdateCurrentStation(recordULDMovementForm.getUpdateCurrentStation());
								log.log(Log.FINE, "UPDATE CURRENT STATION",
										recordULDMovementForm.getUpdateCurrentStation());
								vo.setCurrentStation(recordULDMovementForm.getCurrentStation());
								vo.setRemark(recordULDMovementForm.getRemarks());
								log.log(Log.FINE, "UPDATE REMARKS REMARKS",
										recordULDMovementForm.getRemarks());
								if ("ulddiscrepancy".equals(recordULDMovementForm
										.getDiscrepancyStatus())) {
									log.log(Log.FINE,
											"the DISCREPANCY STATUS IS ", vo.isDiscrepancyToBeSolved());
									log
											.log(
													Log.FINE,
													"the DISCREPANCY STATUS IS collect6ion in session",
													session.getULDMovementVOs());
									//added by nisha for bugfix starts
									if(session.getULDMovementVOs()!=null && session.getULDMovementVOs().size()>0){
										vo.setDiscrepancyCode(session.getULDMovementVOs().iterator().next().getDiscrepancyCode());
									}
									//ends
								}
								uldMovementVos.add(vo);
					   }
		   			}
			   session.setULDMovementVOs(uldMovementVos);
		   }

		       /**
			     * This method is used to retain the values in the form for Collection<UldNos>
			     */
			   if(uldNumbers!=null){
				    uldNos= new ArrayList<String>();
				    log.entering("INSIDE THE MMAINTAIN SESSION"," FOR ULDNUMBERS");
				    for(int i=0;i<uldNumbers.length;i++){
						if(!"NOOP".equals(opFlag[i])){
					    	uldNos.add(uldNumbers[i]);
						}
				  }
				   session.setULDNumbers(uldNos);
			  }

	 }



}
