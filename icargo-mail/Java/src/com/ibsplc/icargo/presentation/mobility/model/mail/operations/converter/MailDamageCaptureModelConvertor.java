package com.ibsplc.icargo.presentation.mobility.model.mail.operations.converter;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.presentation.mobility.model.mail.operations.maildamagecapture.MailDamageCaptureModel;

public class MailDamageCaptureModelConvertor {
	
	public static final String RESPONSE_STATUS_SUCCESS = "SUCCESS";
	public static final String RESPONSE_STATUS_NO_DATA = "NO_DATA";
	public static final String RESPONSE_STATUS_ERROR = "ERROR";
	
	public static MailUploadVO populateMailUploadVO(MailDamageCaptureModel mailDamageCaptureModel,
			MailUploadVO mailUploadVO) {
		if(mailDamageCaptureModel.getIsReturn().equals("Y") || mailDamageCaptureModel.getIsReturn() == "Y") {
			mailUploadVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_RETURNED);
			mailUploadVO.setReturnCode(mailDamageCaptureModel.getDamageCode());
		}
		else
			mailUploadVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_DAMAGED);
		mailUploadVO.setMailSource(MailConstantsVO.SCAN+":HHT044");//A-9619: as part of IASCB-51619, mail source from react-native screen 
		mailUploadVO.setCompanyCode(mailDamageCaptureModel.getCompanyCode());
		mailUploadVO.setCarrierCode("");
		mailUploadVO.setFlightNumber("");
		mailUploadVO.setDestination("");
		mailUploadVO.setDamageCode(mailDamageCaptureModel.getDamageCode());
		mailUploadVO.setDamageRemarks(mailDamageCaptureModel.getRemarks());
		mailUploadVO.setMailKeyforDisplay(mailDamageCaptureModel.getMailBagId());
		mailUploadVO.setMailTag(mailDamageCaptureModel.getMailBagId());
		mailUploadVO.setDestinationOE(mailDamageCaptureModel.getMailBagId().substring(0,6));
		mailUploadVO.setOrginOE(mailDamageCaptureModel.getMailBagId().substring(6,12));
		mailUploadVO.setCategory(mailDamageCaptureModel.getMailBagId().substring(12,13));
		mailUploadVO.setSubClass(mailDamageCaptureModel.getMailBagId().substring(13,15));
		mailUploadVO.setScreeningUser(mailDamageCaptureModel.getScreeningStaff());//A-9619: IASCB-44583
		return mailUploadVO;
	}

}
