/*
 * GenerateReportCommand.java Created on Jun 30 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.substitutedeliverybill.reports;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SubstituteDeliveryBillGenSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SubstituteDeliveryBillGenForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-3217
 *
 */
public class GenerateReportCommand extends AbstractPrintCommand {

	private Log log = LogFactory.getLogger("PrintCN46 Report");
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String ACTION = "generateCN46Report";
	private static final String BUNDLE = "substituteDeliveryBillResources";
	private static final String MODULE_NAME = "mail.operations";
	private static final String REPORT_ID = "04MTK003";
	/*Screen Id was different, Screen id corrected for Bug 102366 by A-2391*/
	//private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";
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
		List<ConsignmentDocumentVO> reportConsignmentList = null;
		
//		if(substituteDeliveryBillGenForm.getMailMutlireports()!=null && substituteDeliveryBillGenForm.getMailMutlireports().length() >0 ){
//			selectedMails = substituteDeliveryBillGenForm.getMailMutlireports().split(",");
//		}
//		if(substituteDeliveryBillGenForm.getConsignmentMutlireports()!=null && substituteDeliveryBillGenForm.getConsignmentMutlireports().length() >0){
//			selectedConsignment = substituteDeliveryBillGenForm.getConsignmentMutlireports().split(",");
//		}
			if(substituteDeliveryBillGenForm.getConsignmentCheck()!=null || substituteDeliveryBillGenForm.getMailCheck()!=null){
		reportConsignmentList = constructConsignmentList( (List<ConsignmentDocumentVO> )substituteDeliveryBillGenSession
					.getConsignmentDocumentVOs(),substituteDeliveryBillGenForm);
			}else{
				reportConsignmentList = (List<ConsignmentDocumentVO> )substituteDeliveryBillGenSession
				.getConsignmentDocumentVOs();
			}
			for(ConsignmentDocumentVO vo:reportConsignmentList){
				vo.setAirportCode(logonAttributes.getAirportCode());
				vo.setFlight(new StringBuilder(substituteDeliveryBillGenForm.getCarrierCode()).
						append(" ").append(substituteDeliveryBillGenForm.getFlightNumber()).toString());
				if(OUTBOUND.equals(substituteDeliveryBillGenForm.getRadioInboundOutbound())) {
					vo.setDateOfDept(substituteDeliveryBillGenForm.getDepartureDate());
				} else {
					vo.setDateOfDept(substituteDeliveryBillGenForm.getArrivalDate());
				}
			}
		
		
		
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setProductCode(PRODUCTCODE);
		reportSpec.setSubProductCode(SUBPRODUCTCODE);
		
		reportSpec.setReportId(REPORT_ID);
		log.log(Log.FINE, "status--->", substituteDeliveryBillGenForm.getStatus());
		if("VIEW".equals(substituteDeliveryBillGenForm.getStatus())){
		reportSpec.setPreview(true);
		}else if("PRINT".equals(substituteDeliveryBillGenForm.getStatus())){
			reportSpec.setPreview(false);
		}
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.setAction(ACTION);
		reportSpec.setResourceBundle(BUNDLE);
		reportSpec.setData(reportConsignmentList);
	    generateReport();
	    
	    invocationContext.target = getTargetPage();

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
	/**
	 * 
	 * @param consignmentDocumentVO
	 * @return
	 */
	
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
