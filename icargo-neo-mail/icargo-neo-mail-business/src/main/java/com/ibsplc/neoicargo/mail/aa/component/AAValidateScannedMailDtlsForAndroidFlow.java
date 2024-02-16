package com.ibsplc.neoicargo.mail.aa.component;

import com.ibsplc.neoicargo.mail.component.MailUploadController;
//TODO: Neo to correct flow
public class AAValidateScannedMailDtlsForAndroidFlow {
	private MailUploadController mailUploadcontroller;
	private MailUploadController mailUploadControllerAA;
	private java.lang.Boolean scannedMailDetailsVO = false;

	public void validateMailBagDetails()  {
		System.out.println("validateMailBagDetails");
		//mailUploadcontroller.validateMailBagDetails(mailUploadVO);
		//this.scannedMailDetailsVO = (mailUploadcontroller.validateMailBagDetails(mailUploadVO));
		this.doSpecificValidations();
	}

	public void doSpecificValidations()  {
		System.out.println("doSpecificValidations");
//		mailUploadControllerAA.doSpecificValidations(scannedMailDetailsVO);
//		this.scannedMailDetailsVO = (mailUploadControllerAA.doSpecificValidations(scannedMailDetailsVO));
		this.end();
	}

	public void end() {
	}
}
