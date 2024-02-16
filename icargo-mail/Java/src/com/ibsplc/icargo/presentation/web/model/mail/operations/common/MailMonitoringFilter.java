package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

public class MailMonitoringFilter {
  private String fromDate;
  private String toDate;
  private String station;
  private String paCode;
  private String serviceLevel;
  private int displayPage;
  private String type;
  private int pageSize = 10;
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
public String getStation() {
	return station;
}
public void setStation(String station) {
	this.station = station;
}
public String getPaCode() {
	return paCode;
}
public void setPaCode(String paCode) {
	this.paCode = paCode;
}
public String getServiceLevel() {
	return serviceLevel;
}
public void setServiceLevel(String serviceLevel) {
	this.serviceLevel = serviceLevel;
}
public int getDisplayPage() {
	return displayPage;
}
public void setDisplayPage(int displayPage) {
	this.displayPage = displayPage;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
/**
 * @return the pageSize
 */
public int getPageSize() {
	return pageSize;
}
/**
 * @param pageSize the pageSize to set
 */
public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
}
  
}
