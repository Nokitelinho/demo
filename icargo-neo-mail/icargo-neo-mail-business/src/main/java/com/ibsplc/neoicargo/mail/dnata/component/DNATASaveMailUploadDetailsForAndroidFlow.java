package com.ibsplc.neoicargo.mail.dnata.component;

import com.ibsplc.neoicargo.mail.component.MailUploadController;

public class DNATASaveMailUploadDetailsForAndroidFlow {
	private MailUploadController mailUploadcontroller;
	private java.lang.Boolean scannedMailDetailsVO = false;

	public void saveMailUploadDetailsForAndroidFlow() {
		System.out.println("saveMailUploadDetailsForAndroidFlow");
//		mailUploadcontroller.saveMailUploadDetailsForAndroid(mailBagVO, scanningPort);
//		this.scannedMailDetailsVO = (mailUploadcontroller.saveMailUploadDetailsForAndroid(mailBagVO, scanningPort));
		this.end();
	}

	public void end() {
	}
}
