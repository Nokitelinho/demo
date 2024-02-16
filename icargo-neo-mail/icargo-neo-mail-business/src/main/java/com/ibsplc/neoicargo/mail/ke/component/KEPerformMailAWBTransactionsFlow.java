package com.ibsplc.neoicargo.mail.ke.component;

import com.ibsplc.neoicargo.mail.component.MailController;

public class KEPerformMailAWBTransactionsFlow {
	private MailController mAilcontroller;

	public void performMailAWBTransactionsFlow() {
		System.out.println("performMailAWBTransactionsFlow");
		//TODO: NEO to correct flwo
		//koreanMailcontroller.performMailAWBTransactionsFlow(mailFlightSummaryVO, eventCode);
		this.performMailAWBTransactions();
	}

	public void performMailAWBTransactions()  {
		System.out.println("performMailAWBTransactions");
		//mAilcontroller.performMailAWBTransactions(mailFlightSummaryVO, eventCode);
		this.end();
	}

	public void end() {
	}
}
