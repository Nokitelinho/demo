package com.ibsplc.neoicargo.mail.oz.component;

import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.neoicargo.mail.component.MailController;

public class OZPerformMailAWBTransactionsFlow {
	public ShipmentDetailVO mailFlightSummaryVO;
	public ShipmentDetailVO eventCode;
	private MailController mAilcontroller;

	public void performMailAWBTransactionsFlow()  {

		//TODO: Neo to correct
		System.out.println("performMailAWBTransactionsFlow");
		//asianaMailcontroller.performMailAWBTransactionsFlow(mailFlightSummaryVO, eventCode);
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
