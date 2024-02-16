/*
 * PrintCN46Command.java Created on Jun 30 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.substitutedeliverybill.reports;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SubstituteDeliveryBillGenSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SubstituteDeliveryBillGenForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3217
 *
 */
public class PrintCN46Command extends AbstractPrintCommand {

	private Log log = LogFactory.getLogger("PrintCN46 Report");
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.substitutedeliverybill";
	private static final String OUTBOUND = "O";
	
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
	
	log.entering("GenerateReportComand", "starts");
	ApplicationSessionImpl applicationSession = getApplicationSession();
	LogonAttributes logonAttributes = applicationSession.getLogonVO();
	SubstituteDeliveryBillGenForm substituteDeliveryBillGenForm = (SubstituteDeliveryBillGenForm) invocationContext.screenModel;
	SubstituteDeliveryBillGenSession substituteDeliveryBillGenSession = getScreenSession(MODULE_NAME, SCREEN_ID);
	Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();
	String[] selectedMails = substituteDeliveryBillGenForm.getMailCheck();
	String[] selectedConsignment = substituteDeliveryBillGenForm.getConsignmentCheck();
	
	if(selectedMails == null && selectedConsignment == null){
		ErrorVO errorVO = new ErrorVO(
		"mailtracking.defaults.deliverybill.msg.err.noSelection");
		formErrors.add(errorVO);
		invocationContext.addAllError(formErrors);
		invocationContext.target = "substitutelist_failure";
		return;
	}
	
	
	List<ConsignmentDocumentVO> reportConsignmentList = constructConsignmentList( (List<ConsignmentDocumentVO> )substituteDeliveryBillGenSession
																								.getConsignmentDocumentVOs(),
																				substituteDeliveryBillGenForm);
	
	for(ConsignmentDocumentVO vo:reportConsignmentList){
		vo.setAirportCode(logonAttributes.getAirportCode());
		vo.setFlight(new StringBuilder(substituteDeliveryBillGenForm.getCarrierCode()).
				append(substituteDeliveryBillGenForm.getFlightNumber()).toString());
		if(OUTBOUND.equals(substituteDeliveryBillGenForm.getRadioInboundOutbound())){
			vo.setDateOfDept(substituteDeliveryBillGenForm.getDepartureDate());
		}else{
			vo.setDateOfDept(substituteDeliveryBillGenForm.getArrivalDate());
		}
	}
	
	try {
		new MailTrackingDefaultsDelegate().print46Report(reportConsignmentList);
	} catch (BusinessDelegateException businessDelegateException) {
		errors = handleDelegateException(businessDelegateException);
	}
	invocationContext.target = "substitutelist_success";
}
	
	private List<ConsignmentDocumentVO> constructConsignmentList(List<ConsignmentDocumentVO> sessionConsignmentList,
			SubstituteDeliveryBillGenForm substituteDeliveryBillGenForm){
		
		HashMap<String, ConsignmentDocumentVO> mainMap = new HashMap<String, ConsignmentDocumentVO>();
		String[] selectedMails = substituteDeliveryBillGenForm.getMailCheck();
		String[] selectedConsignment = substituteDeliveryBillGenForm.getConsignmentCheck();
		int mailCount = 0;
		List<String> mailList = null;
		ConsignmentDocumentVO newvo = null;
		List<MailInConsignmentVO> mailInConsignmentList = null;
		
		if(selectedMails != null){
			mailList = new ArrayList<String>(Arrays.asList(selectedMails));
			mailCount = mailList.size();
		}
				
		if(selectedConsignment != null){
			for(String consignId : selectedConsignment){
				if((mainMap.get(consignId) == null)){
					mainMap.put(consignId,sessionConsignmentList.get(Integer.parseInt(consignId)));
					for(int i=0;i<mailCount;i++){
						String mail = mailList.get(i);
						if(consignId.equals(mail.split("-")[0])){
							mailList.remove(i);
							mailCount = mailList.size();
							i--;
						}
					}
				}
			}
		}
		
		if(mailCount >0){
			for(String mailId : mailList){
				if(mainMap.get(mailId.split("-")[0]) != null){
					newvo.getMailInConsignment().add(mailInConsignmentList.get(Integer.parseInt(mailId.split("-")[1])));
				}else{
					newvo = reconstructConsignmentDocumentVO(sessionConsignmentList.get(Integer.parseInt(mailId.split("-")[0])));
					mailInConsignmentList = new ArrayList<MailInConsignmentVO>(newvo.getMailInConsignment());
					newvo.setMailInConsignment(new ArrayList<MailInConsignmentVO>());
					newvo.getMailInConsignment().add(mailInConsignmentList.get(Integer.parseInt(mailId.split("-")[1])));
					mainMap.put(mailId.split("-")[0],newvo);
				}
			}
		}
			
		return( new ArrayList<ConsignmentDocumentVO>(mainMap.values()));
	 }
	

	private ConsignmentDocumentVO reconstructConsignmentDocumentVO(ConsignmentDocumentVO consignmentDocumentVO){
		ConsignmentDocumentVO newVO = new ConsignmentDocumentVO();
		newVO.setAirportCode(consignmentDocumentVO.getAirportCode());
		newVO.setDateOfDept(consignmentDocumentVO.getDateOfDept());
		newVO.setFlight(consignmentDocumentVO.getFlight());
		newVO.setPou(consignmentDocumentVO.getPou());
		newVO.setOrginCity(consignmentDocumentVO.getOrginCity());
		newVO.setDestinationCity(consignmentDocumentVO.getDestinationCity());
		newVO.setSubsequentFlight(consignmentDocumentVO.getSubsequentFlight());
		newVO.setDespatchDate(consignmentDocumentVO.getDespatchDate());
		newVO.setMailInConsignment(consignmentDocumentVO.getMailInConsignment());
		return newVO;
		
	}
}
