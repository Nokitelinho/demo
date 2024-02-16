package com.ibsplc.neoicargo.mail.lh.component;

import com.ibsplc.neoicargo.mail.component.MailUploadController;

public class LHValidateScannedMailDtlsForAndroidFlow {
	private MailUploadController mailUploadcontroller;
	private java.lang.Boolean scannedMailDetailsVO = false;

	public void validateMailBagDetails()  {
		System.out.println("validateMailBagDetails");
		//mailUploadcontroller.validateMailBagDetails(mailUploadVO);
		//this.scannedMailDetailsVO = (mailUploadcontroller.validateMailBagDetails(mailUploadVO));
		this.end();
	}

	public void end() {
	}
}
