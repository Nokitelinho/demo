/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.mailbooking.BookedFlightsPopupOkCommand.java
 *
 *	Created by	:	A-7531
 *	Created on	:	08-Sep-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.mailbooking;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.xaddons.bs.mail.operations.BaseMailOperationsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.xaddons.bs.mail.operations.MailbookingPopupForm;


import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.mailbooking.BookedFlightsPopupOkCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	08-Sep-2017	:	Draft
 */
public class BookedFlightsPopupOkCommand extends BaseCommand{

	
	private Log log = LogFactory.getLogger("BookedFlightsPopupOkCommand");
	 private static final String MODULE_NAME = "bsmail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.searchconsignment";
	   private static final String TARGET = "listbooking_success";
	   private static final String STATUS_SAME_ROUTE="xaddons.bs.mail.operations.awb.status.sameroute";
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-7531 on 08-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param invocationcontext
	 *	Parameters	:	@throws CommandInvocationException 
	 */

	public void execute(InvocationContext invocationcontext)
			throws CommandInvocationException {
		// TODO Auto-generated method stub
		MailbookingPopupForm mailpopForm = 
				(MailbookingPopupForm)invocationcontext.screenModel;
	    	SearchConsignmentSession consignmentSession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID);
	    	Collection<MailbagVO> mailbagVOs = consignmentSession.getMailbagVOsCollection();
	    	BaseMailOperationsDelegate delegate=new BaseMailOperationsDelegate();
	    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    	Collection<MailbagVO> selectedMailBagVO=new ArrayList<MailbagVO>();
	    	MailBookingDetailVO mailBookingDetailVO =null;
	    	
	    	
	    	Collection<MailBookingDetailVO> selectedMailBookingDetailVOs = new ArrayList<MailBookingDetailVO>();

	    	//added by A-8061 as part of ICRD-229572 begin
	    	selectedMailBagVO =consignmentSession.getselectedMailbagVOs();
	    	Collection<MailBookingDetailVO>mailBookingDetailVOs =new ArrayList<MailBookingDetailVO>();
	    	mailBookingDetailVOs = consignmentSession.getMailBookingDetailsVO();
	    	
	    	//String selectedFlightNo=mailpopForm.getBookingFlightNumber();
	    	
	    	
	    	//ICRD-252055 Starts 
		  	// Getting the selected FLights
	    	String[] selectedRows =mailpopForm.getBookingFlightNumber().split(",");
	    	int size = selectedRows.length;
	    	int row = 0;
	    	for (MailBookingDetailVO mailBookingDetailVOTemp : mailBookingDetailVOs) {
	    		for (int j = 0; j < size; j++) {
	    			if (row == Integer.parseInt(selectedRows[j])) {
	    				selectedMailBookingDetailVOs.add(mailBookingDetailVOTemp);
	    			}    			
				}
	    		row++;
	    	}
	    	
	    	
	    	
	    	//ICRD-252055 Ends
	    	
	    	
	    	/*String[] selectedFlights=null;
	    	if(mailpopForm.getBookingFlightNumber()!=null&&!"".equals(mailpopForm.getBookingFlightNumber())){
	    	selectedFlights=mailpopForm.getBookingFlightNumber().split(",");
	    	}

	    	
	    	if(selectedFlights!=null){
		    	for(int i=0;i<selectedFlights.length;i++){
			    	for(MailBookingDetailVO mailBookingDetailVOTmp:mailBookingDetailVOs){
			    		
			    		String[] selectedFlightDetails=selectedFlights[i].split(" ");
			    		int bookingFlightSequenceNumber=Integer.parseInt(selectedFlightDetails[2]);
			    		if(mailBookingDetailVOTmp.getBookingFlightNumber()!=null && mailBookingDetailVOTmp.getBookingFlightNumber().equals(selectedFlightDetails[0]) && mailBookingDetailVOTmp.getBookingCarrierCode().equals(selectedFlightDetails[1])&& bookingFlightSequenceNumber== mailBookingDetailVOTmp.getBookingFlightSequenceNumber()){
			    			mailBookingDetailVO =new MailBookingDetailVO();
			    			mailBookingDetailVO.setOrigin(mailBookingDetailVOTmp.getOrigin());
							mailBookingDetailVO.setDestination(mailBookingDetailVOTmp.getDestination());
							mailBookingDetailVO.setBookingFlightNumber(mailBookingDetailVOTmp.getBookingFlightNumber());
							mailBookingDetailVO.setSegementserialNumber(mailBookingDetailVOTmp.getSegementserialNumber());
							mailBookingDetailVO.setBookingFlightSequenceNumber(mailBookingDetailVOTmp.getBookingFlightSequenceNumber());
							mailBookingDetailVO.setBookingFlightCarrierid(mailBookingDetailVOTmp.getBookingFlightCarrierid());
							selectedMailBookingDetailVOs.add(mailBookingDetailVO);
			    		}
			    	}
		    	}
	    	}
	    	*/
	    	//added by A-8061 as part of ICRD-229572 end
	    	

	    	
	    	/*if(selectedMailIds != null && mailbagVOs != null){
	    		
					
						
						for(int i=0; i < selectedMailIds.length; i++){
							String selectedId = selectedMailIds[i];
							for(MailbagVO mailbagVO:mailbagVOs){
						
						if(mailbagVO.getMailbagId().equals(selectedId)){
							selectedMailBagVO.add(mailbagVO);
							//mailBookingDetailVO.setOrigin(mailpopForm.getOrginOfBooking());
							//mailBookingDetailVO.setDestination(mailpopForm.getDestinationOfBooking());
							mailBookingDetailVO.setBookingFlightNumber(mailpopForm.get)
						}
					}
				}
					
				
	    	}*/
	    	// a-8061 for ICRD-237844 begin
			String tmpOrigin="";
			String tmpDestination="";
	    	for(MailBookingDetailVO mailBookingDetailVOtoValidate :selectedMailBookingDetailVOs ){
	    		if("".equals(tmpOrigin)){
	    			tmpOrigin=mailBookingDetailVOtoValidate.getOrigin();
	    			tmpDestination=mailBookingDetailVOtoValidate.getDestination();
	    		}
	    		else{
	    			if(tmpOrigin.equals(mailBookingDetailVOtoValidate.getOrigin())||tmpDestination.equals(mailBookingDetailVOtoValidate.getDestination())){
	    				ErrorVO errorVO = new ErrorVO(STATUS_SAME_ROUTE);
	    				errors.add(errorVO);
	    				invocationcontext.addAllError(errors);   
	    				invocationcontext.target = TARGET;
	    				mailpopForm.setPopUpFlag("Y");
	    				return;
	    				
	    			}
	    			else{
	    				tmpOrigin=mailBookingDetailVOtoValidate.getOrigin();
		    			tmpDestination=mailBookingDetailVOtoValidate.getDestination();
	    			}
	    		}
	    		
	    	}
	    	// a-8061 for ICRD-237844 end
	    	
	    	try {
				delegate.saveMailBookingFlightDetails(selectedMailBagVO,selectedMailBookingDetailVOs);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				businessDelegateException.getMessage();
			}

	    	 mailpopForm.setPopUpFlag("N");
	    	if(errors!=null&&errors.size()>0)
			{
	    		invocationcontext.addAllError(errors);   
				invocationcontext.target = TARGET;
				mailpopForm.setPopUpFlag("Y");
			}
	    	

		invocationcontext.target = TARGET;
    	log.exiting("BookedFlightsPopupOkCommand","execute");
		
	}

}
