package com.ibsplc.neoicargo.mail.lh.component;

import com.ibsplc.neoicargo.mail.lh.component.LHMailUploadController;


public class LHFlagScreeningConsginorDetailsFlow {
	//TODO: Neo correction
	//public ShipmentDetailVO contTransferMap;
	private LHMailUploadController mailUploadControllerLH;

	public void saveScreeningConsginorDetails()  {
		System.out.println("saveScreeningConsginorDetails");
		//mailUploadControllerLH.saveScreeningConsginorDetails(contTransferMap);
		this.end();
	}

	public void end() {
	}
}
