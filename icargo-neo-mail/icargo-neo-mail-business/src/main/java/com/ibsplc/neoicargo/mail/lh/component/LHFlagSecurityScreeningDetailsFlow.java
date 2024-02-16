package com.ibsplc.neoicargo.mail.lh.component;

import com.ibsplc.neoicargo.mail.lh.component.LHMailUploadController;

public class LHFlagSecurityScreeningDetailsFlow {
	private LHMailUploadController mailUploadControllerLH;

	public void saveScreeningDetails()  {
		System.out.println("saveScreeningDetails");
		//TODO: Neo to correct
		//mailUploadControllerLH.saveScreeningDetails(scannedMailDetailsVO);
		this.end();
	}

	public void end() {
	}
}
