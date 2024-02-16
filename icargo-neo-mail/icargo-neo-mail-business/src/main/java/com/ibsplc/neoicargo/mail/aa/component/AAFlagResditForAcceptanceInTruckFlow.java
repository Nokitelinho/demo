package com.ibsplc.neoicargo.mail.aa.component;

import com.ibsplc.neoicargo.mail.aa.component.MailUploadController;

public class AAFlagResditForAcceptanceInTruckFlow {
	//TODO: Neo to correct
	//public ShipmentDetailVO scannedMailDetailsVO;
	private MailUploadController mailUploadControllerAA;

	public void flagResditForAcceptanceInTruck()  {
		System.out.println("flagResditForAcceptanceInTruck");
		//TODO: Neo to correct
		// mailUploadControllerAA.flagResditForAcceptanceInTruck(scannedMailDetailsVO);
		this.end();
	}

	public void end() {
	}
}
