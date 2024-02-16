package com.ibsplc.icargo.business.xaddons.oz.mail.operations.vo;

import com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class MailSummaryDeatilsVO  extends AbstractVO  {
	 private MailFlightSummaryVO mailFlightSummaryVO;
	 private String eventCode;
	public MailFlightSummaryVO getMailFlightSummaryVO() {
		return mailFlightSummaryVO;
	}
	public void setMailFlightSummaryVO(MailFlightSummaryVO mailFlightSummaryVO) {
		this.mailFlightSummaryVO = mailFlightSummaryVO;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
}
