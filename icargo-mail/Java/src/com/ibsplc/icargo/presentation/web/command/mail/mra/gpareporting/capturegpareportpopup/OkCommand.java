/*
 * OkCommand.java Created on Feb 24, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.capturegpareportpopup;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFlightDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.CaptureGPAReportForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-1739
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Feb 24, 2007			a-2257		Created
 */
public class OkCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Mailtracking MRA");
	
	private static final String CLASS_NAME = "OkCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra";
	
	private static final String SCREENID = "mailtracking.mra.gpareporting.capturegpareport";
	/**
	 * constant for invoking popup screen
	 */
	
	
	private static final String ADD = "ADD";
	
	private static final String BLANK = "";
	
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "action_success";
	
	/**
	 * 
	 * TODO Purpose
	 * Mar 11, 2007, a-2257
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("OkCommand", "execute");
		
		CaptureGPAReportSession session = 
			(CaptureGPAReportSession)getScreenSession(
													MODULE_NAME, SCREENID);			
		CaptureGPAReportForm form = 
			(CaptureGPAReportForm)invocationContext.screenModel;
			
		//Collection<GPAReportingDetailsVO> gpaReportingDetailsVOs = session.getGPAReportingDetailsVOs();
		
		Collection<GPAReportingDetailsVO> gpaReportingDetailsVOs = session.getGPAReportingDetailsPage();
		
		Collection<GPAReportingDetailsVO> newGpaReportingDetailsVOs = session.getModifiedGPAReportingDetailsVOs();
		
		if (invocationContext.getErrors() != null
				&& invocationContext.getErrors().size() > 0) {	
			log.log(Log.FINE, "Inside errors");
			invocationContext.target = ACTION_SUCCESS;
			log.exiting(CLASS_NAME, "execute");
			return;
		} 
		
		if(newGpaReportingDetailsVOs!=null && newGpaReportingDetailsVOs.size()>0){
			for(GPAReportingDetailsVO gpaReportingDetailsVO : newGpaReportingDetailsVOs){
				
				StringBuffer flightDetails = new StringBuffer("") ;
				
				if(gpaReportingDetailsVO.getGpaReportingFlightDetailsVOs()!=null 
						&& gpaReportingDetailsVO.getGpaReportingFlightDetailsVOs().size()>0){
					
					for(GPAReportingFlightDetailsVO fltDetailsVO : gpaReportingDetailsVO.getGpaReportingFlightDetailsVOs()){
						log.log(Log.FINE, "updating the fltDetailsVO");								
					
						if(!(BLANK).equals(flightDetails.toString()) && flightDetails.length()>0) {
							log.log(Log.FINE, "not first time");
							
							flightDetails.append(",");	
							flightDetails.append("\n");	
						}
							log.log(Log.FINE, "not first time", fltDetailsVO.getCarriageFrom());
								if(fltDetailsVO.getCarriageFrom() != null && fltDetailsVO.getCarriageFrom().trim().length()>0){
									flightDetails.append(fltDetailsVO.getCarriageFrom());
								}else{
														
									log.log(Log.FINE, "no getCarriageFrom ");
									flightDetails.append("");						
								}
								flightDetails.append("-");
								
								if(fltDetailsVO.getCarriageTo() != null && fltDetailsVO.getCarriageTo().trim().length()>0){
									flightDetails.append(fltDetailsVO.getCarriageTo());
								}else{
														
									log.log(Log.FINE, "no getCarriageTo ");
									flightDetails.append("");						
								}
								flightDetails.append(":");
								
								if(fltDetailsVO.getFlightCarrierCode() != null && fltDetailsVO.getFlightCarrierCode().trim().length()>0){
									flightDetails.append(fltDetailsVO.getFlightCarrierCode());
								}else{
														
									log.log(Log.FINE, "no getFlightCarrierCode ");
									flightDetails.append("");						
								}
								flightDetails.append(" ");	
								if(fltDetailsVO.getFlightNumber() != null && fltDetailsVO.getFlightNumber().trim().length()>0){
									flightDetails.append(fltDetailsVO.getFlightNumber());
								}else{
														
									log.log(Log.FINE, "no getFlightNumber ");
									flightDetails.append("");						
								}																
						
					}
					gpaReportingDetailsVO.setFlightDetails(flightDetails.toString());	
				}
				
				
			}
		}
		
		if(form.getPopUpStatusFlag()!= null && 
				form.getPopUpStatusFlag().trim().length() > 0){
			log.log(Log.FINE, "Inside first if");
			if(ADD.equals(form.getPopUpStatusFlag())){
				log.log(Log.FINE, "Inside sec if");
				if(gpaReportingDetailsVOs==null || (gpaReportingDetailsVOs.size()<=0)){
					log.log(Log.FINE, "Inside third if");
					//session.setGPAReportingDetailsVOs(newGpaReportingDetailsVOs);
					
					Page<GPAReportingDetailsVO> pageGPA = 
						new Page<GPAReportingDetailsVO>( (ArrayList<GPAReportingDetailsVO>)newGpaReportingDetailsVOs , 0, 0, 0, 0, 0, false);
					
					session.setGPAReportingDetailsPage(pageGPA);
					
				}else{
					log.log(Log.FINE, "Inside else if");
					gpaReportingDetailsVOs.addAll(newGpaReportingDetailsVOs);
					log.log(Log.FINE, "going to make page");
					//session.setGPAReportingDetailsVOs(gpaReportingDetailsVOs);
//					Page<GPAReportingDetailsVO> pageGPA = 
//						new Page<GPAReportingDetailsVO>( (ArrayList<GPAReportingDetailsVO>)gpaReportingDetailsVOs , 0, 0, 0, 0, 0, false);
					
					session.setGPAReportingDetailsPage((Page<GPAReportingDetailsVO>)gpaReportingDetailsVOs);
				}
				if(session.getGPAReportingDetailsPage()!=null
						&& session.getGPAReportingDetailsPage().size()>0){
					
					log.log(Log.FINE, "after adding to the main ",
							gpaReportingDetailsVOs);
				}
				
				form.setPopUpStatusFlag("");
			}
	    }
		
		form.setScreenFlag("mainScreen");
		invocationContext.target =ACTION_SUCCESS;  	
		log.exiting("OkCommand", "execute");

	}

}
