package com.ibsplc.icargo.presentation.mobility.model.mail.operations.converter;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.presentation.mobility.model.mail.operations.mailoffload.MailOffloadModel;


public class MailOffloadModelConverter {
	
	public static final String RESPONSE_STATUS_SUCCESS = "SUCCESS";
	public static final String RESPONSE_STATUS_NO_DATA = "NO_DATA";
	public static final String RESPONSE_STATUS_ERROR = "ERROR";
	

	public static MailUploadVO populateMailUploadVO(MailOffloadModel mailOffloadModel,
			MailUploadVO mailUploadVO) {
		mailUploadVO =new MailUploadVO();
		mailUploadVO.setProcessPoint("OFL");
		mailUploadVO.setMailSource(MailConstantsVO.SCAN+":HHT019"); 
		mailUploadVO.setCompanyCode(mailOffloadModel.getCompanyCode());
		mailUploadVO.setCarrierCode("");
		mailUploadVO.setFlightNumber("");
		mailUploadVO.setDestination("");
		mailUploadVO.setOffloadReason(mailOffloadModel.getOffloadReason());
		mailUploadVO.setRemarks(mailOffloadModel.getOffloadReason());
		mailUploadVO.setMailKeyforDisplay(mailOffloadModel.getMailBagId());
		if(mailOffloadModel.getIsMailBag().equals("Y")||mailOffloadModel.getIsMailBag()=="Y"){
			mailUploadVO.setMailTag(mailOffloadModel.getMailBagId());
			mailUploadVO.setDestinationOE(mailOffloadModel.getMailBagId().substring(0,6));
			mailUploadVO.setOrginOE(mailOffloadModel.getMailBagId().substring(6,12));
			mailUploadVO.setCategory(mailOffloadModel.getMailBagId().substring(12,13));
			mailUploadVO.setSubClass(mailOffloadModel.getMailBagId().substring(13,15));
		}
		else if(mailOffloadModel.getIsMailBag().equals("N")||mailOffloadModel.getIsMailBag()=="N"){
			mailUploadVO.setContainerNumber(mailOffloadModel.getMailBagId());
			mailUploadVO.setContainerType("U");
			mailUploadVO.setMailTag(mailOffloadModel.getMailBagId());
		}
		return mailUploadVO;
		}
	}