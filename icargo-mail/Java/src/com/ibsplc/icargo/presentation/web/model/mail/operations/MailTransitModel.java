package com.ibsplc.icargo.presentation.web.model.mail.operations;


import java.util.List;
import java.util.Map;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailTransit;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailTransitFilter;



public class MailTransitModel extends AbstractScreenModel{
	
	private String flightNumber;
	private String airportCode;
	private String fromDate;
	private String toDate;
	private MailTransitFilter mailTransitFilter;
	private PageResult<MailTransit> mailTransits;
	List<MailTransit> mailTransitList;
	Map<String, MailTransit> mailTansitCapMap;
	
	public PageResult<MailTransit> getMailTransits() {
		return mailTransits;
	}

	public void setMailTransits(PageResult<MailTransit> mailTransits) {
		this.mailTransits = mailTransits;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public MailTransitFilter getMailTransitFilter() {
		return mailTransitFilter;
	}

	public void setMailTransitFilter(MailTransitFilter mailTransitFilter) {
		this.mailTransitFilter = mailTransitFilter;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	

	@Override
	public String getProduct() {
		return null;
	}

	@Override
	public String getScreenId() {
		return null;
	}

	@Override
	public String getSubProduct() {
		return null;
	}
	public List<MailTransit> getMailTransitList() {
		return mailTransitList;
	}
	public void setMailTransitList(List<MailTransit> mailTransitList) {
		this.mailTransitList = mailTransitList;
	}
	public Map<String, MailTransit> getMailTansitCapMap() {
		return mailTansitCapMap;
	}
	public void setMailTansitCapMap(Map<String, MailTransit> mailTansitCapMap) {
		this.mailTansitCapMap = mailTansitCapMap;
	}

}
