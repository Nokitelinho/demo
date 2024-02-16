package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.mail.component.MailController;

public class PerformMailAWBTransactionsFlow {
	//TODO: Neo correction
//	public ShipmentDetailVO mailFlightSummaryVO;
//	public ShipmentDetailVO eventCode;
	private MailController mAilcontroller;

	public void performMailAWBTransactionsEnd()  {
		System.out.println("performMailAWBTransactionsEnd");
		//mAilcontroller.performMailAWBTransactions(mailFlightSummaryVO, eventCode);
		this.end();
	}

	public void end() {
	}
}
