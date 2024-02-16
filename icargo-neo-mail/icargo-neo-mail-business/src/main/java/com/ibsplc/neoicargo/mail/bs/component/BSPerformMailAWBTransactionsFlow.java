package com.ibsplc.neoicargo.mail.bs.component;

import com.ibsplc.neoicargo.mail.component.MailController;

public class BSPerformMailAWBTransactionsFlow {
	//TODO: TO correct in NEO
//	public ShipmentDetailVO mailFlightSummaryVO;
//	public ShipmentDetailVO eventCode;
	private MailController mAilcontroller;

	public void performMailAWBTransactionsFlow(){
		System.out.println("performMailAWBTransactionsFlow");
		//TODO: TO correct in NEO
		//baseMailcontroller.performMailAWBTransactionsFlow(mailFlightSummaryVO, eventCode);
		this.performMailAWBTransactions();
	}

	public void performMailAWBTransactions()  {
		System.out.println("performMailAWBTransactions");
		//TODO: TO correct in NEO
		//mAilcontroller.performMailAWBTransactions(mailFlightSummaryVO, eventCode);
		this.end();
	}

	public void end() {
	}
}
