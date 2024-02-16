package com.ibsplc.icargo.presentation.web.model.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.spring.model.ScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.CarditFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;

public class CarditEnquiryModel implements ScreenModel{

	/*
	 * The constant variable for product mail
	 */
	private static final String PRODUCT = "mail";
	/*
	 * The constant for sub product operations
	 */
	private static final String SUBPRODUCT = "operations";
	/*
	 * The constant for screen id
	 */
	private static final String SCREENID = "mail.operations.ux.carditenquiry";
	  private CarditFilter carditFilter;
	
      private String mailbagId;
	  private String flightNumber;
	  private String flightDate;
	  private String carrierCode;
	  private String ooe;
	  private String doe;
	  private String mailCategoryCode;
	  private String mailSubclass;
	  private String year;
	  private String despatchSerialNumber;
	  private String receptacleSerialNumber;
	  private String conDocNo;
	  private String fromDate;
	  private String toDate;
	  private String paCode;
	  private String uldNumber;
	  private String originAirport;
	  private String destAirport;
	  private String mailStatus;
	  private String airportCode;
	  private String mailOrigin;
	  private String mailDestination;
	  private Map<String, Collection<OneTime>> oneTimeValues;
	//  private Collection<Mailbag> mailbags;
	  private Collection<Mailbag> selectedMailbags;
	  private PageResult<Mailbag> mailbags;
	  private Collection<MailbagVO> mailbagsVO;
	  private String displayPage;
	  private int totalPieces;
	  private Measure totalWeight;
	private List<String> selectedResdits=Collections.emptyList();
	  private String selectedResditVersion;
@Override
public String getProduct() {
	return PRODUCT;
}

@Override
public String getScreenId() {
	return SCREENID;
}

public String getMailbagId() {
	return mailbagId;
}

public void setMailbagId(String mailbagId) {
	this.mailbagId = mailbagId;
}

@Override
public String getSubProduct() {
	return SUBPRODUCT;
}

public String getFlightNumber() {
	return flightNumber;
}

public void setFlightNumber(String flightNumber) {
	this.flightNumber = flightNumber;
}

public String getFlightDate() {
	return flightDate;
}

public void setFlightDate(String flightDate) {
	this.flightDate = flightDate;
}

public String getOoe() {
	return ooe;
}

public void setOoe(String ooe) {
	this.ooe = ooe;
}

public String getDoe() {
	return doe;
}

public void setDoe(String doe) {
	this.doe = doe;
}

public String getMailCategoryCode() {
	return mailCategoryCode;
}

public void setMailCategoryCode(String mailCategoryCode) {
	this.mailCategoryCode = mailCategoryCode;
}

public String getMailSubclass() {
	return mailSubclass;
}

public void setMailSubclass(String mailSubclass) {
	this.mailSubclass = mailSubclass;
}

public String getYear() {
	return year;
}

public void setYear(String year) {
	this.year = year;
}

public String getDespatchSerialNumber() {
	return despatchSerialNumber;
}

public void setDespatchSerialNumber(String despatchSerialNumber) {
	this.despatchSerialNumber = despatchSerialNumber;
}

public String getReceptacleSerialNumber() {
	return receptacleSerialNumber;
}

public void setReceptacleSerialNumber(String receptacleSerialNumber) {
	this.receptacleSerialNumber = receptacleSerialNumber;
}

public String getConDocNo() {
	return conDocNo;
}

public void setConDocNo(String conDocNo) {
	this.conDocNo = conDocNo;
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

public String getPaCode() {
	return paCode;
}

public void setPaCode(String paCode) {
	this.paCode = paCode;
}

public String getUldNumber() {
	return uldNumber;
}

public void setUldNumber(String uldNumber) {
	this.uldNumber = uldNumber;
}

public String getOriginAirport() {
	return originAirport;
}

public void setOriginAirport(String originAirport) {
	this.originAirport = originAirport;
}

public String getDestAirport() {
	return destAirport;
}

public void setDestAirport(String destAirport) {
	this.destAirport = destAirport;
}

public String getMailStatus() {
	return mailStatus;
}

public void setMailStatus(String mailStatus) {
	this.mailStatus = mailStatus;
}

public Map<String, Collection<OneTime>> getOneTimeValues() {
	return oneTimeValues;
}

public void setOneTimeValues(Map<String, Collection<OneTime>> oneTimeValues) {
	this.oneTimeValues = oneTimeValues;
}

public String getAirportCode() {
	return airportCode;
}

public void setAirportCode(String airportCode) {
	this.airportCode = airportCode;
}



public String getCarrierCode() {
	return carrierCode;
}

public void setCarrierCode(String carrierCode) {
	this.carrierCode = carrierCode;
}

public Collection<MailbagVO> getMailbagsVO() {
	return mailbagsVO;
}

public void setMailbagsVO(Collection<MailbagVO> mailbagsVO) {
	this.mailbagsVO = mailbagsVO;
}

public PageResult<Mailbag> getMailbags() {
	return mailbags;
}

public void setMailbags(PageResult<Mailbag> mailbags) {
	this.mailbags = mailbags;
}

public Collection<Mailbag> getSelectedMailbags() {
	return selectedMailbags;
}
public void setSelectedMailbags(Collection<Mailbag> selectedMailbags) {
	this.selectedMailbags = selectedMailbags;
}
public String getDisplayPage() {
	return displayPage;
}

public void setDisplayPage(String displayPage) {
	this.displayPage = displayPage;
}

public CarditFilter getCarditFilter() {
	return carditFilter;
}

public void setCarditFilter(CarditFilter carditFilter) {
	this.carditFilter = carditFilter;
}

public String getMailOrigin() {
	return mailOrigin;
}

public void setMailOrigin(String mailOrigin) {
	this.mailOrigin = mailOrigin;
}

public String getMailDestination() {
	return mailDestination;
}

public void setMailDestination(String mailDestination) {
	this.mailDestination = mailDestination;
}

public int getTotalPieces() {
	return totalPieces;
}

public void setTotalPieces(int totalPieces) {
	this.totalPieces = totalPieces;
}

public Measure getTotalWeight() {
	return totalWeight;
}

public void setTotalWeight(Measure totalWeight) {
	this.totalWeight = totalWeight;
}
	public String getSelectedResditVersion() {
		return selectedResditVersion;
	}
	public void setSelectedResditVersion(String selectedResditVersion) {
		this.selectedResditVersion = selectedResditVersion;
}
public List<String> getSelectedResdits() {
		return new ArrayList<>(selectedResdits);
}
public void setSelectedResdits(List<String> selectedResdits) {
		selectedResdits = new ArrayList<>(selectedResdits);
		this.selectedResdits = Collections.unmodifiableList(selectedResdits);
}



}
