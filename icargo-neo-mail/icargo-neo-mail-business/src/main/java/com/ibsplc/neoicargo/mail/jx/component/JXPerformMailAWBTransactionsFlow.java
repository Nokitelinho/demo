package com.ibsplc.neoicargo.mail.jx.component;

import com.ibsplc.neoicargo.mail.component.MailController;

public class JXPerformMailAWBTransactionsFlow {

	private MailController mAilcontroller;

	public void performMailAWBTransactionsFlow()  {
		System.out.println("performMailAWBTransactionsFlow");
		//TODO: Neo to correct
		//starluxMailcontroller.performMailAWBTransactionsFlow(mailFlightSummaryVO, eventCode);
		this.performMailAWBTransactions();
	}

	public void performMailAWBTransactions() {
		System.out.println("performMailAWBTransactions");
		//TODO: Neo to correct
		//mAilcontroller.performMailAWBTransactions(mailFlightSummaryVO, eventCode);
		this.end();
	}

	public void end() {
	}
}
