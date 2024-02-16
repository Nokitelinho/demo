/* ScreenLoadCommand.java Created on Feb 2, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.presentation.web.command.mail.operations.national.transfer;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ArriveDispatchSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.DispatchEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.TransferDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.TransferDispatchForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-4810
 */

public class ScreenLoadCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILTRACKING");

	/**
	 * TARGET
	 */
	private static final String TARGET = "screenload_success";

	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.national.transfermail";
	private static final String MODULE_NAME1 = "mail.operations";	
	private static final String ARR_MAIL_BAG = "ARR_MAIL_BAG";
	private static final String DSN_ENQUIRY = "DSN_ENQUIRY";
	private static final String SCREEN_ID1 = "mailtracking.defaults.national.mailarrival";
	private static final String SCREEN_ID_DISPATCH = "mailtracking.defaults.national.dispatchEnquiry";

	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering("ScreenloadCommand","execute");

		TransferDispatchForm transferDispatchForm = (TransferDispatchForm)invocationContext.screenModel;
		TransferDispatchSession transferDispatchSession = getScreenSession(MODULE_NAME,SCREEN_ID);
		ArriveDispatchSession arriveDispatchSession = getScreenSession(MODULE_NAME1,SCREEN_ID1);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(transferDispatchForm.getFromScreen().equals(ARR_MAIL_BAG)){
		MailArrivalVO mailArrivalVO = arriveDispatchSession.getMailArrivalVO();
		Collection<ContainerDetailsVO> containerDetailsVOs =  mailArrivalVO.getContainerDetails();
		ArrayList<ContainerDetailsVO> collectionContainerDetailsVOs = (ArrayList<ContainerDetailsVO>) containerDetailsVOs;

		//
		String selectedRow = transferDispatchForm.getSelectedcont();
		 String dsnIndex = "";
         String contIndex = "";
		String[] containerDsns = selectedRow.split("~");
		   contIndex = containerDsns[0];
		   dsnIndex = containerDsns[1];
		   if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){	
			   ContainerDetailsVO containerDtlsVO = collectionContainerDetailsVOs.get(Integer.parseInt(contIndex));
			   ArrayList<DSNVO> dSNVOs = (ArrayList<DSNVO>)containerDtlsVO.getDsnVOs();
				DSNVO dSNVO = dSNVOs.get(Integer.parseInt(dsnIndex));
				
				 // The code is commented by a-4810 for icrd -20140
				   //The code is modified to accomodate transfer of despatches form more than one container		
		//
		/*if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){		
			for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
				ArrayList<DSNVO> dSNVOs = (ArrayList<DSNVO>)containerDtlsVO.getDsnVOs();
				DSNVO dSNVO = dSNVOs.get(Integer.parseInt(transferDispatchForm.getSelectedcont()));*/
				//transferDispatchForm.setRemarks(dSNVO.getRemarks());
				transferDispatchForm.setPieces(String.valueOf(dSNVO.getReceivedBags()-dSNVO.getTransferredPieces()));
				//transferDispatchForm.setWeight(String.valueOf(dSNVO.getReceivedWeight()-dSNVO.getTransferredWeight()));
				try {
					transferDispatchForm.setWeightMeasure(Measure.subtractMeasureValues(dSNVO.getReceivedWeight(), dSNVO.getTransferredWeight()));
				} catch (UnitException e1) {
					// TODO Auto-generated catch block
					log.log(Log.SEVERE, "UnitException",e1.getMessage());
				}//added by A-7371
				transferDispatchForm.setPieceavailable(transferDispatchForm.getPieces()) ;
				transferDispatchForm.setWeightavailable(transferDispatchForm.getWeight());  
				/**
				 * ICRD-26699_A-4816 
				 */
				transferDispatchForm.setRemainingPcs(String.valueOf(dSNVO.getBags()-dSNVO.getTransferredPieces())) ;   
				//transferDispatchForm.setRemainingWt(String.valueOf(dSNVO.getWeight()-dSNVO.getTransferredWeight())) ; 
				try {
					transferDispatchForm.setRemainingWtMeasure(Measure.subtractMeasureValues(dSNVO.getWeight(), dSNVO.getTransferredWeight())) ;
				} catch (UnitException e) {
					// TODO Auto-generated catch block
					log.log(Log.SEVERE, "UnitException",e.getMessage());
				}  //added by A-7371
				if(Integer.parseInt(transferDispatchForm.getPieces())<=0 || Double.parseDouble(transferDispatchForm.getWeight())<=0){
					errors.add(new ErrorVO("mailtracking.defaults.national.cannottransfer"));
				}
			}
				
		}else if(transferDispatchForm.getFromScreen().equals(DSN_ENQUIRY)){	

			DispatchEnquirySession dispatchEnquirySession = getScreenSession(MODULE_NAME, SCREEN_ID_DISPATCH);
			String status =dispatchEnquirySession.getDSNEnquiryFilterVO().getStatus();		
				     for( DespatchDetailsVO despatchDetailsVO : (ArrayList<DespatchDetailsVO>) transferDispatchSession.getDespatchDetailsVOs()) {
				    	    //transferDispatchForm.setRemarks(despatchDetailsVO.getRemarks());
				    		if(MailConstantsVO.MAIL_STATUS_ARRIVED.equalsIgnoreCase(status)) {
				    			transferDispatchForm.setPieces(String.valueOf(despatchDetailsVO.getReceivedBags()- despatchDetailsVO.getTransferredPieces()));
								//transferDispatchForm.setWeight(String.valueOf(despatchDetailsVO.getReceivedWeight()-despatchDetailsVO.getTransferredWeight()));	
				    			try {
									transferDispatchForm.setWeightMeasure(Measure.subtractMeasureValues(despatchDetailsVO.getReceivedWeight(), despatchDetailsVO.getTransferredWeight()));
								} catch (UnitException e) {
									// TODO Auto-generated catch block
									log.log(Log.SEVERE, "UnitException",e.getMessage());
								}//added by A-7371	
							}else {
								transferDispatchForm.setPieces(String.valueOf(despatchDetailsVO.getAcceptedBags()- despatchDetailsVO.getTransferredPieces()));
								//transferDispatchForm.setWeight(String.valueOf(despatchDetailsVO.getAcceptedWeight()-despatchDetailsVO.getTransferredWeight()));
								try {
									transferDispatchForm.setWeightMeasure(Measure.subtractMeasureValues(despatchDetailsVO.getAcceptedWeight(), despatchDetailsVO.getTransferredWeight()));
								} catch (UnitException e) {
									// TODO Auto-generated catch block
									log.log(Log.SEVERE, "UnitException",e.getMessage());
								}//added by A-7371
							}
							transferDispatchForm.setPieceavailable(transferDispatchForm.getPieces()) ;
							transferDispatchForm.setWeightavailable(transferDispatchForm.getWeight());
							if(Integer.parseInt(transferDispatchForm.getPieces())<=0 || Double.parseDouble(transferDispatchForm.getWeight())<=0
							 ||!(MailConstantsVO.MAIL_STATUS_ARRIVED.equalsIgnoreCase(status) || MailConstantsVO.MAIL_STATUS_NOTARRIVED.equalsIgnoreCase(status))){
								errors.add(new ErrorVO("mailtracking.defaults.national.cannottransfer"));
							}				    	 
				     }
			}
		
		   if (errors != null && errors.size() > 0) {
		    	invocationContext.addAllError(errors);
		    	invocationContext.target = TARGET;
		    	return;
		     }
			
		
		invocationContext.target = TARGET;

		log.exiting("ScreenloadCommand","execute");

	}    
       
}