/**
 * MailbagTest.java Created on Aug 21, 2021
 * @author A-8353
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import static org.mockito.Mockito.spy;

import org.junit.Test;

public class CN51SummaryTest extends AbstractFeatureTest {
	private CN51Summary cn51Summary;
	InvoiceSettlementVO invoiceSettlementVO;

	@Override
	public void setup() throws Exception {
		cn51Summary = spy(new CN51Summary());
		invoiceSettlementVO = new InvoiceSettlementVO();
		invoiceSettlementVO.setSettlementLevel("V");
		invoiceSettlementVO.setTolerancePercentage(0);
		invoiceSettlementVO.setSettlementValue(0.1);
		invoiceSettlementVO.setSettlementMaxValue(3);
		cn51Summary.setTotalAmountInBillingCurr(15.0195);

	}

	@Test
	public void updateSettlementStatus() {
		cn51Summary.setTotalAmountInBillingCurr(15.0195);
		cn51Summary.updateSettlementStatus(15, invoiceSettlementVO, 2);
	}

	@Test
	public void updateSettlementStatusSettlementLevelIsNotV() {
		cn51Summary.setTotalAmountInBillingCurr(15.0195);
		invoiceSettlementVO.setSettlementLevel("M");
		cn51Summary.updateSettlementStatus(15, invoiceSettlementVO, 2);
	}

	@Test
	public void updateSettlementStatusSettlementPercentageIsnotZero() {
		cn51Summary.setTotalAmountInBillingCurr(15.0195);
		invoiceSettlementVO.setTolerancePercentage(20);
		cn51Summary.updateSettlementStatus(15, invoiceSettlementVO, 2);
	}

	@Test
	public void updateSettlementStatusSettlementMaxValueIsNull() {
		cn51Summary.setTotalAmountInBillingCurr(15.0195);
		invoiceSettlementVO.setSettlementMaxValue(0);
		cn51Summary.updateSettlementStatus(15, invoiceSettlementVO, 2);
	}

	@Test
	public void updateSettlementStatusWithSettledAmntZero() {
		cn51Summary.setTotalAmountInBillingCurr(15.0195);
		cn51Summary.updateSettlementStatus(0, invoiceSettlementVO, 2);
	}

	@Test
	public void updateSettlementStatusWithSettledAmnt() {
		cn51Summary.setTotalAmountInBillingCurr(15.0195);
		cn51Summary.updateSettlementStatus(14, invoiceSettlementVO, 2);
	}

	@Test
	public void updateSettlementStatusWithGreaterSettledAmnt() {
		cn51Summary.setTotalAmountInBillingCurr(15.0195);
		cn51Summary.updateSettlementStatus(17, invoiceSettlementVO, 2);
	}

	@Test
	public void updateSettlementStatusWithSameSettledAmnt() {
		cn51Summary.setTotalAmountInBillingCurr(15.0195);
		cn51Summary.updateSettlementStatus(15.0195, invoiceSettlementVO, 2);
	}

	@Test
	public void updateSettlementStatusWithInToleranceLimit() {
		cn51Summary.setTotalAmountInBillingCurr(15.0195);
		cn51Summary.updateSettlementStatus(15, invoiceSettlementVO, 2);
	}

	@Test
	public void updateSettlementStatusValueWithInToleranceLimit() {
		invoiceSettlementVO.setSettlementValue(3);
		cn51Summary.setTotalAmountInBillingCurr(15.0195);
		cn51Summary.updateSettlementStatus(18, invoiceSettlementVO, 2);
	}
	@Test
	public void updateSettlementStatusSettlemntAmntWithToleranceAndBillingCurrencyAreSame() {
		invoiceSettlementVO.setSettlementValue(0.1);
		cn51Summary.setTotalAmountInBillingCurr(13.1);
		cn51Summary.updateSettlementStatus(13, invoiceSettlementVO, 2);
	}
	
}
