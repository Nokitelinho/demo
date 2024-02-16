package com.ibsplc.icargo.presentation.web.model.mail.operations.common;
import java.util.Collection;
import java.util.List;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.web.json.PageResult;
public class MailAcceptance {
   private String companyCode;
   private int carrierId;
   private String flightCarrierCode;
   private String carrierCode;
   private String flightNumber;
   private long flightSequenceNumber;
   private String flightRoute;
   private String flightStatus;
   private String flightType;
   private String flightDate ;  
   private String flightTime;
   private String upliftAirport;
   private String destination;
   private PageResult<ContainerDetails> containerPageInfo;
  // private Page<ContainerDetail> containerPageInfo;
   private List<Mailbag>mailbags;
   private String FlightOperationalStatus;
   private int totalULDs;
   private int totalBulks;
   private String pol;
   private String pou;
   private String flightOrigin;
   private String flightDestination;
   private int legSerialNumber;
   private int segmentSerialNumber;
   private String aircraftType;
   private String flightDateDesc;
   private MailAcceptancePK flightpk;
   private MailAcceptancePK carrierpk;
   //Newly added container
   private ContainerDetails container;
   private Collection<ContainerDetails> containerDetails;
   private int totalCount;
   private Measure totalWeight;
   private String preassignFlag;
   private Preadvice preadvice;
   // To populate pou in add container popup
   private Collection<String> pouList;
   // To show total count and weight in the filght manifest details
   private int totalContainerCount;
   private Measure totalContainerWeight;
   //Added by A-8176 as part of ICRD-212021
   private String departureGate;
   //Added by A-8672 as part of ICRD-335856 starts
   private String manifestInfo;
   private String capacityInfo;
   private String DCSinfo;
   private String DCSregectionReason;
   private boolean fromDeviationList;	 
   private String containerNumber;
   
   //added by A-7815 as part of IASCB-47327
   private  String flightDepartureDate;
   
   private String rdtDate;
   private String reqDeliveryTime;
   private String popupAction;
   private String showWarning;
   private double  provosionalCharge;
   private String baseCurrency;
   private String rateAvailforallMailbags;
   private boolean estimatedChargePrivilage;	
   private String std;
   
public boolean isEstimatedChargePrivilage() {
	return estimatedChargePrivilage;
}
public void setEstimatedChargePrivilage(boolean estimatedChargePrivilage) {
	this.estimatedChargePrivilage = estimatedChargePrivilage;
}
public double getProvosionalCharge() {
	return provosionalCharge;
}
public void setProvosionalCharge(double provosionalCharge) {
	this.provosionalCharge = provosionalCharge;
}
public String getBaseCurrency() {
	return baseCurrency;
}
public void setBaseCurrency(String baseCurrency) {
	this.baseCurrency = baseCurrency;
}
public String getRateAvailforallMailbags() {
	return rateAvailforallMailbags;
}
public void setRateAvailforallMailbags(String rateAvailforallMailbags) {
	this.rateAvailforallMailbags = rateAvailforallMailbags;
}
//Added by A-8672 as part of ICRD-335856 ends
public String getCompanyCode() {
	return companyCode;
}
public void setCompanyCode(String companyCode) {
	this.companyCode = companyCode;
}
public String getFlightNumber() {
	return flightNumber;
}

public String getFlightCarrierCode() {
	return flightCarrierCode;
}
public void setFlightCarrierCode(String flightCarrierCode) {
	flightCarrierCode = flightCarrierCode;
}
public long getFlightSequenceNumber() {
	return flightSequenceNumber;
}
public void setFlightSequenceNumber(long flightSequenceNumber) {
	this.flightSequenceNumber = flightSequenceNumber;
}
public void setFlightNumber(String flightNumber) {
	this.flightNumber = flightNumber;
}
public String getFlightRoute() {
	return flightRoute;
}
public void setFlightRoute(String flightRoute) {
	this.flightRoute = flightRoute;
}
public String getFlightStatus() {
	return flightStatus;
}
public void setFlightStatus(String flightStatus) {
	this.flightStatus = flightStatus;
}
public String getFlightType() {
	return flightType;
}
public void setFlightType(String flightType) {
	this.flightType = flightType;
}
public String getUpliftAirport() {
	return upliftAirport;
}
public void setUpliftAirport(String upliftAirport) {
	this.upliftAirport = upliftAirport;
}

public List<Mailbag> getMailbags() {
	return mailbags;
}
public void setMailbags(List<Mailbag> mailbags) {
	this.mailbags = mailbags;
}
public String getFlightOperationalStatus() {
	return FlightOperationalStatus;
}
public void setFlightOperationalStatus(String flightOperationalStatus) {
	FlightOperationalStatus = flightOperationalStatus;
}
public int getTotalULDs() {
	return totalULDs;
}
public void setTotalULDs(int totalULDs) {
	this.totalULDs = totalULDs;
}
public int getTotalBulks() {
	return totalBulks;
}
public void setTotalBulks(int totalBulks) {
	this.totalBulks = totalBulks;
}
public String getFlightDate() {
	return flightDate;
}
public void setFlightDate(String flightDate) {
	this.flightDate = flightDate;
}
public int getCarrierId() {
	return carrierId;
}
public void setCarrierId(int carrierId) {
	this.carrierId = carrierId;
}
public String getPol() {
	return pol;
}
public void setPol(String pol) {
	this.pol = pol;
}
public String getPou() {
	return pou;
}
public void setPou(String pou) {
	this.pou = pou;
}
public String getFlightOrigin() {
	return flightOrigin;
}
public void setFlightOrigin(String flightOrigin) {
	this.flightOrigin = flightOrigin;
}
public String getFlightDestination() {
	return flightDestination;
}
public void setFlightDestination(String flightDestination) {
	this.flightDestination = flightDestination;
}
public String getCarrierCode() {
	return carrierCode;
}
public void setCarrierCode(String carrierCode) {
	this.carrierCode = carrierCode;
}
public int getLegSerialNumber() {
	return legSerialNumber;
}
public void setLegSerialNumber(int legSerialNumber) {
	this.legSerialNumber = legSerialNumber;
}
public int getSegmentSerialNumber() {
	return segmentSerialNumber;
}
public void setSegmentSerialNumber(int segmentSerialNumber) {
	this.segmentSerialNumber = segmentSerialNumber;
}
public ContainerDetails getContainer() {
	return container;
}
public void setContainer(ContainerDetails container) {
	this.container = container;
}



public PageResult<ContainerDetails> getContainerPageInfo() {
	return containerPageInfo;
}
public void setContainerPageInfo(PageResult<ContainerDetails> containerPageInfo) {
	this.containerPageInfo = containerPageInfo;
}
public Collection<ContainerDetails> getContainerDetails() {
	return containerDetails;
}
public void setContainerDetails(Collection<ContainerDetails> containerDetails) {
	this.containerDetails = containerDetails;
}
public MailAcceptancePK getFlightpk() {
	return flightpk;
}
public void setFlightpk(MailAcceptancePK flightpk) {
	this.flightpk = flightpk;
}
public String getAircraftType() {
	return aircraftType;
}
public void setAircraftType(String aircraftType) {
	this.aircraftType = aircraftType;
}
public String getFlightDateDesc() {
	return flightDateDesc;
}
public void setFlightDateDesc(String flightDateDesc) {
	this.flightDateDesc = flightDateDesc;
}
public int getTotalCount() {
	return totalCount;
}
public void setTotalCount(int totalCount) {
	this.totalCount = totalCount;
}
public Measure getTotalWeight() {
	return totalWeight;
}
public void setTotalWeight(Measure totalWeight) {
	this.totalWeight = totalWeight;
}
public String getDestination() {
	return destination;
}
public void setDestination(String destination) {
	this.destination = destination;
}
public MailAcceptancePK getCarrierpk() {
	return carrierpk;
}
public void setCarrierpk(MailAcceptancePK carrierpk) {
	this.carrierpk = carrierpk;
}
public String getPreassignFlag() {
	return preassignFlag;
}
public void setPreassignFlag(String preassignFlag) {
	this.preassignFlag = preassignFlag;
}
public Collection<String> getPouList() {
	return pouList;
}
public void setPouList(Collection<String> pouList) {
	this.pouList = pouList;
}
public Preadvice getPreadvice() {
	return preadvice;
}
public void setPreadvice(Preadvice preadvice) {
	this.preadvice = preadvice;
}
public int getTotalContainerCount() {
	return totalContainerCount;
}
public void setTotalContainerCount(int totalContainerCount) {
	this.totalContainerCount = totalContainerCount;
}
public Measure getTotalContainerWeight() {
	return totalContainerWeight;
}
public void setTotalContainerWeight(Measure totalContainerWeight) {
	this.totalContainerWeight = totalContainerWeight;
}
public String getDepartureGate() {
	return departureGate;
}
public void setDepartureGate(String departureGate) {
	this.departureGate = departureGate;
}
public String getFlightTime() {
	return flightTime;
}
public void setFlightTime(String flightTime) {
	this.flightTime = flightTime;
}
//Added by A-8672 as part of ICRD-335856 starts
/**
 * @return the manifestInfo
 */
public String getManifestInfo() {
	return manifestInfo;
}
/**
 * @param manifestInfo the manifestInfo to set
 */
public void setManifestInfo(String manifestInfo) {
	this.manifestInfo = manifestInfo;
}
/**
 * @return the capacityInfo
 */
public String getCapacityInfo() {
	return capacityInfo;
}
/**
 * @param capacityInfo the capacityInfo to set
 */
public void setCapacityInfo(String capacityInfo) {
	this.capacityInfo = capacityInfo;
}
public String getDCSinfo() {
	return DCSinfo;
}
public void setDCSinfo(String dCSinfo) {
	DCSinfo = dCSinfo;
}
public String getDCSregectionReason() {
	return DCSregectionReason;
}
public void setDCSregectionReason(String dCSregectionReason) {
	DCSregectionReason = dCSregectionReason;
}
public boolean isFromDeviationList() {
	return fromDeviationList;
}
public void setFromDeviationList(boolean fromDeviationList) {
	this.fromDeviationList = fromDeviationList;
}   
public String getContainerNumber() {
	return containerNumber;
}
public void setContainerNumber(String containerNumber) {
	this.containerNumber = containerNumber;
}
public String getFlightDepartureDate() {
	return flightDepartureDate;
}
public void setFlightDepartureDate(String flightDepartureDate) {
	this.flightDepartureDate = flightDepartureDate;
}
public String getRdtDate() {
	return rdtDate;
}
public void setRdtDate(String rdtDate) {
	this.rdtDate = rdtDate;
}
public String getReqDeliveryTime() {
	return reqDeliveryTime;
}
public void setReqDeliveryTime(String reqDeliveryTime) {
	this.reqDeliveryTime = reqDeliveryTime;
}
public String getPopupAction() {
	return popupAction;
}
public void setPopupAction(String popupAction) {
	this.popupAction = popupAction;
}
 public String getShowWarning() {
	return showWarning;
}
public void setShowWarning(String showWarning) {
	this.showWarning = showWarning;
}
public String getStd() {
	return std;
}
public void setStd(String std) {
	this.std = std;
}

//Added by A-8672 as part of ICRD-335856 ends
   
   
   
}
