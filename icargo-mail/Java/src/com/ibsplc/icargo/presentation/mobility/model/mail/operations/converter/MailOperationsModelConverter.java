/**
 *	Java file	: 	com.ibsplc.icargo.presentation.mobility.model.mail.operations.mailbagenquiry.MailbagEnquiryModelConverter.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8464	:	25-Mar-2019		:	Draft
 */

package com.ibsplc.icargo.presentation.mobility.model.mail.operations.converter;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.presentation.mobility.model.mail.operations.common.Mailbag;
import com.ibsplc.icargo.presentation.mobility.model.mail.operations.mailbagenquiry.MailbagEnquiryModel;

public class MailOperationsModelConverter {
	
	public static final String RESPONSE_STATUS_SUCCESS = "SUCCESS";
	public static final String RESPONSE_STATUS_NO_DATA = "NO_DATA";
	public static final String RESPONSE_STATUS_ERROR = "ERROR";
	

	public static MailbagEnquiryModel populateMailbagDetailsToMailbagEnquiryModel(MailbagEnquiryModel mailbagEnquiryModel,
			MailbagVO resultMailbagVo) {
			Mailbag mailbag = new Mailbag();
			mailbag.setMailbagId(mailbagEnquiryModel.getMailbagEnquiryFilter().getMailbagId());
			
			mailbag.setMailSequenceNumber(resultMailbagVo.getMailSequenceNumber());
			mailbag.setScannedDate(resultMailbagVo.getScannedDate().toDisplayFormat("dd-MM-yyyy"));
			mailbag.setLatestStatus(resultMailbagVo.getLatestStatus());
			mailbag.setScannedPort(resultMailbagVo.getScannedPort());
			if(resultMailbagVo.getFlightNumber()!=null && !resultMailbagVo.getFlightNumber().equals("-1"))   
			mailbag.setFlightNumber(resultMailbagVo.getFlightNumber());
			if(resultMailbagVo.getFlightDate()!=null)
			{
				mailbag.setFlightDate(resultMailbagVo.getFlightDate().toDisplayFormat("dd-MM-yyyy"));
			}
			mailbag.setCarrierCode(resultMailbagVo.getCarrierCode());
			mailbag.setContainerNumber(resultMailbagVo.getContainerNumber());
			mailbag.setPou(resultMailbagVo.getPou());
			mailbag.setConsignmentNumber(resultMailbagVo.getConsignmentNumber());
			mailbag.setConsignmentSequenceNumber(resultMailbagVo.getConsignmentSequenceNumber());
			mailbag.setWeight(resultMailbagVo.getWeight());
			mailbag.setMailorigin(resultMailbagVo.getMailOrigin());
			mailbag.setMailDestination(resultMailbagVo.getMailDestination());
			if(resultMailbagVo.getActualWeight()!=null){
			mailbag.setActualWeight(resultMailbagVo.getActualWeight().getRoundedDisplayValue());
            }
			mailbag.setLastUpdateUser(resultMailbagVo.getLastUpdateUser());
			if(resultMailbagVo.getReqDeliveryTime()!=null)
			{   String reqDlvTime =null; // added by A-8353 for ICRD-333808
			    reqDlvTime=resultMailbagVo.getReqDeliveryTime().toDisplayFormat("dd-MMM-yyyy HH:mm");
				mailbag.setReqDeliveryTime(reqDlvTime);   
			}
			if(resultMailbagVo.getConsignmentDate()!=null)
			{
				mailbag.setConsignmentDate(resultMailbagVo.getConsignmentDate().toString());
			}
			mailbag.setRoutingInfo(resultMailbagVo.getRoutingInfo());
			mailbag.setMailRemarks(resultMailbagVo.getMailRemarks());
			
			mailbagEnquiryModel.setMailbag(mailbag);
			return mailbagEnquiryModel;
		
	}
	
}
