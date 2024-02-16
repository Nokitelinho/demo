package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.mail.component.MailUploadController;

public class ValidateScannedMailDtlsForAndroidFlow {
	private MailUploadController mailUploadcontroller;
	private java.lang.Boolean scannedMailDetailsVO = false;

	public void validateMailBagDetails()  {
		System.out.println("validateMailBagDetails");
		//TODO: Neo to correct
		//mailUploadcontroller.validateMailBagDetails(mailUploadVO);
		//this.scannedMailDetailsVO = (mailUploadcontroller.validateMailBagDetails(mailUploadVO));
		this.end();
	}

	public void end() {
	}
}
