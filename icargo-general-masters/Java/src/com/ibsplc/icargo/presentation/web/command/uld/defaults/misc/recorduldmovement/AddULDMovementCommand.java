/*
 * AddULDMovementCommand.java Created on jan 29, 2005
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
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
  * @author A-1936 Karthick .V
  *
  */
public class AddULDMovementCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
    
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		 log.entering("AddRowCommand","execute");
		 RecordULDMovementForm  recordULDMovementForm = (RecordULDMovementForm) invocationContext.screenModel;
		 RecordUldMovementSession session = (RecordUldMovementSession)
							getScreenSession( "uld.defaults","uld.defaults.misc.recorduldmovement");
		 
         
		 String[] carrierCode = recordULDMovementForm.getCarrierCode();
		 String[] content = recordULDMovementForm.getContent();
		 String[]  flightDate =recordULDMovementForm.getFlightDateString();
	     String[]  flightNumber=recordULDMovementForm.getFlightNumber();
	     String[] pointOfLading = recordULDMovementForm.getPointOfLading();
	     String[] pointOfUnLading=recordULDMovementForm.getPointOfUnLading();
	     String[] chkValues = recordULDMovementForm.getDummyMovement();
	     Collection<String> checkValuesColl = new ArrayList<String>();
	     
	      if(chkValues!=null){
	          for ( int i=0;i<chkValues.length;i++){
	    	  checkValuesColl.add(chkValues[i]);
	       }
	      }
	    int count=0;

		 ULDMovementVO uldMovementVO =new ULDMovementVO();
         
		 uldMovementVO.setCarrierCode("");
		 uldMovementVO.setContent("");
		 uldMovementVO.setPointOfLading("");
		 uldMovementVO.setPointOfUnLading("");
		 uldMovementVO.setFlightDateString("");
		 uldMovementVO.setFlightNumber("");
	     uldMovementVO.setDummyMovement(false);
		 //LocalDate fltDate = new LocalDate(getApplicationSession().getLogonVO().getStationCode(), false);
		 

		if(session.getULDMovementVOs()==null){

					log.log(Log.FINE,"...........There are no entries.......");
					Collection<ULDMovementVO> uldVOs = new ArrayList<ULDMovementVO>();
					uldVOs.add(uldMovementVO);
					session.setULDMovementVOs(uldVOs);

		}

		else{

					log.log(Log.FINE,"...........Else part.......");
					//ArrayList<ULDMovementVO> uldVOs = new ArrayList<ULDMovementVO>(session.getULDMovementVOs());
					count=0;
					log.log(Log.FINE,"...........Else part.......");
					for(ULDMovementVO uldMovment:session.getULDMovementVOs()){
						 uldMovment.setContent(content[count]);
					     uldMovment.setPointOfLading(pointOfLading[count]);
						 uldMovment.setPointOfUnLading(pointOfUnLading[count]);
						 log.log(Log.FINE, "cgfvfcgfgfgxfgf", flightDate, count);
						log.log(Log.FINE, "cgfvfcgfgfgxfgf", flightDate, count);
						if(flightDate[count]!=null && !"".equalsIgnoreCase(flightDate[count])){
							 //uldMovment.setFlightDate( fltDate.setDate(flightDate[count],"dd-MMM-yyyy"));	 
							 uldMovment.setFlightDateString(flightDate[count]);
						 }
						  uldMovment.setFlightNumber(flightNumber[count].toUpperCase());
						  uldMovment.setCarrierCode(carrierCode[count]);
						  
						 if(checkValuesColl.contains(String.valueOf(count))){
							 uldMovment.setDummyMovement(true);
						 }else{
							 uldMovment.setDummyMovement(false);
						 }
						  /*if("CHECKED".equals(chkValues[count])){
							  uldMovment.setDummyMovement(true);
						  }else{
							  uldMovment.setDummyMovement(false);
						  }*/
					     count++;
					 }
					 //session.setULDMovementVOs(uldVOs);
					
					session.getULDMovementVOs().add(uldMovementVO);
		}
		
	   log.exiting("AddRowCommand","execute");
	   invocationContext.target = "save_success";
	}
}
