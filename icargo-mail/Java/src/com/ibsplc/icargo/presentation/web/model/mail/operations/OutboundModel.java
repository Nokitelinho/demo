package com.ibsplc.icargo.presentation.web.model.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.AttachAwbDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.AttachRoutingDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.CarditFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ConsignmentDocument;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.DespatchDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.FlightCarrierFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.FlightSegmentCapacitySummary;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.FlightSegmentVolumeSummary;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailAcceptance;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailInConsignment;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailbagFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.PostalAdministrationModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Preadvice;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Warehouse;
import com.ibsplc.icargo.presentation.web.model.operations.flthandling.common.FlightValidation;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
public class OutboundModel extends AbstractScreenModel{
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
	private static final String SCREENID = "mail.operations.ux.outbound";
	 private String flightNumber;
	 private LocalDate flightDate;
	 private String mailStatus;
	 private String fromDate;
	 private String toDate;
	 private String paCode;
	 private String airportCode;
	 private String carrierCode;
	 private String mailDestination;
	 private String departurePort;
	 private MailAcceptance mailAcceptance;
	 private FlightCarrierFilter flightCarrierFilter;
	 private PageResult<MailAcceptance> mailflightspage;
	 private PageResult<MailAcceptance> mailcarrierspage;
	 private List<MailAcceptance> mailAcceptanceList;
	 private FlightValidation flightValidation;
	 private ContainerDetails containerInfo;
	 private String flightPK;
	 //this is to get the pagination display page for flght level actions
	 private String displayPage;
	 private HashMap<String, Page<MailbagVO>> containerMap;
	 //Cardit list ang gropu view
	 private CarditFilter carditFilter;
	 private PageResult<Mailbag> carditMailbags;
	 private PageResult<Mailbag> carditGroupMailbags;
	 private Mailbag carditSummary;
	 private Map<String, Collection<OneTime>> oneTimeValues;
	 //Add/modify container
	 private String density;
	 //to identify if carrier/flight in listcontainers in flight
	 private String flightCarrierflag;
	 private int containerDisplayPage;
	 //pous list in add container
	 private Collection<String> pouList;
	 private ContainerDetails newContainerInfo;
	 private Collection<ContainerDetails> newContainersDetails;
	 //Lying list section
	 private MailbagFilter mailbagFilter;
	 private PageResult<Mailbag> lyinglistMailbags;
	 private PageResult<Mailbag> lyinglistGroupedMailbags;
	 private List<Mailbag>mailbags;
	 private Collection<Warehouse> wareHouses;
	 private Preadvice preadvice;
	 private ContainerDetails selectedContainer;
	 private String warningOverride;
	 //AttachRouting changes
	 private String selectedIndex;
	 private ArrayList<ContainerDetails> ContainerDetailsCollection;
	 private AttachAwbDetails attachAwbDetails;
	//Attach Routing
	 private ConsignmentDocument consignmentDocument;
	 private AttachRoutingDetails attachRoutingDetails;
	 private String csgDocNumForRouting;
	 private String paCodeForRouting;
	 private String RoutingDirection;
	 private String newRoutingFlag;
	 private Collection<MailInConsignment> mailsInConsignment;
	 private int mailbagsDisplayPage;
	 private int mailbagsDSNDisplayPage;
	 private ContainerDetails importFromContainer;
	 private Collection<Mailbag> importedmailbags;
	 private String currentDate;
	 private String currentTime;
	 private String defWeightUnit;
	 private Measure totalWeight;
	 private ArrayList<PostalAdministrationModel> postalAdministrations;
	 private HashMap<String, Collection<FlightSegmentCapacitySummary>> flightCapacityDetails;
	 private Map<String, String> warningMessagesStatus;
	 private String actionType;
	 private ArrayList<DespatchDetails>despatchDetailsList;
	 private Collection<MailInConsignmentVO> createMailInConsignmentVOs;
	// private Collection<ExistingMailbag> existingMailbags;
	 private Collection<Mailbag> existingMailbags;
	 private String[] reassignFlag;
	 private String existingMailbagFlag;
	 private String  stationVolUnt;//added by A-8353 for ICRD-274933
	 private String tab;//added by A-8353 for ICRD-274933
	 private String isCarrierDefault; //Added by A-7540 for IASCB-23663
	 private int defaultPageSize ;
	 private boolean weightScaleAvailable;//added by A-8353 for IASCB-33559
	 private String showWarning;
	 private String stationWeightUnit;				
     private String uldToBarrowAllow;
	 //added by A-7815 as part of IASCB-36551
	 private String defaultOperatingReference;
     private String ownAirlineCode; 
	 private Collection<String> partnerCarriers;

	private boolean enableDeviationListTab;
	 //added as part of IASCB-48444
	 private boolean mandateScanTime;
	 private String addMailbagMode;
	 private boolean disableForModify;
	 private boolean embargoInfo;
	 private String activeTab;
	 private String containerJnyID;
	 private boolean fromContainerTab;
	 private Map<String, MailAcceptanceVO> flightPreAdviceDetails;
	 private boolean containerTransferCheck;
	 private Map<String, Collection<FlightSegmentVolumeSummary>> flightVolumeDetails;
	 /**
	 * @return the isCarrierDefault
	 */
	public String getIsCarrierDefault() {
		return isCarrierDefault;
	}
	/**
	 * @param isCarrierDefault the isCarrierDefault to set
	 */
	public void setIsCarrierDefault(String isCarrierDefault) {
		this.isCarrierDefault = isCarrierDefault;
	}
	 public Measure getTotalWeight() {
		return totalWeight;
	 }
	 public void setTotalWeight(Measure totalWeight) {
		this.totalWeight = totalWeight;
	 }
	 
	 @Override
	public String getProduct() {
		return PRODUCT;
	}

	@Override
	public String getScreenId() {
		return SCREENID;
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

	public String getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
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

	public String getMailDestination() {
		return mailDestination;
	}

	public void setMailDestination(String mailDestination) {
		this.mailDestination = mailDestination;
	}

	public FlightCarrierFilter getFlightCarrierFilter() {
		return flightCarrierFilter;
	}

	public void setFlightCarrierFilter(FlightCarrierFilter flightCarrierFilter) {
		this.flightCarrierFilter = flightCarrierFilter;
	}


	public PageResult<MailAcceptance> getMailflightspage() {
		return mailflightspage;
	}

	public void setMailflightspage(PageResult<MailAcceptance> mailflightspage) {
		this.mailflightspage = mailflightspage;
	}

	

	public FlightValidation getFlightValidation() {
		return flightValidation;
	}

	public void setFlightValidation(FlightValidation flightValidation) {
		this.flightValidation = flightValidation;
	}

	public String getDeparturePort() {
		return departurePort;
	}

	public void setDeparturePort(String departurePort) {
		this.departurePort = departurePort;
	}

	public LocalDate getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	public MailAcceptance getMailAcceptance() {
		return mailAcceptance;
	}

	public void setMailAcceptance(MailAcceptance mailAcceptance) {
		this.mailAcceptance = mailAcceptance;
	}

	public ContainerDetails getContainerInfo() {
		return containerInfo;
	}

	public void setContainerInfo(ContainerDetails containerInfo) {
		this.containerInfo = containerInfo;
	}

	public String getFlightPK() {
		return flightPK;
	}

	public void setFlightPK(String flightPK) {
		this.flightPK = flightPK;
	}

	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	public Preadvice getPreadvice() {
		return preadvice;
	}

	public void setPreadvice(Preadvice preadvice) {
		this.preadvice = preadvice;
	}

	public HashMap<String, Page<MailbagVO>> getContainerMap() {
		return containerMap;
	}

	public void setContainerMap(HashMap<String, Page<MailbagVO>> containerMap) {
		this.containerMap = containerMap;
	}

	public CarditFilter getCarditFilter() {
		return carditFilter;
	}

	public void setCarditFilter(CarditFilter carditFilter) {
		this.carditFilter = carditFilter;
	}

    public PageResult<Mailbag> getCarditMailbags() {
		return carditMailbags;
	}

	public void setCarditMailbags(PageResult<Mailbag> carditMailbags) {
		this.carditMailbags = carditMailbags;
	}

	public PageResult<Mailbag> getCarditGroupMailbags() {
		return carditGroupMailbags;
	}

	public void setCarditGroupMailbags(PageResult<Mailbag> carditGroupMailbags) {
		this.carditGroupMailbags = carditGroupMailbags;
	}

	public Mailbag getCarditSummary() {
		return carditSummary;
	}

	public void setCarditSummary(Mailbag carditSummary) {
		this.carditSummary = carditSummary;
	}

	public Map<String, Collection<OneTime>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(Map<String, Collection<OneTime>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}

	public PageResult<MailAcceptance> getMailcarrierspage() {
		return mailcarrierspage;
	}

	public void setMailcarrierspage(PageResult<MailAcceptance> mailcarrierspage) {
		this.mailcarrierspage = mailcarrierspage;
	}

	public String getDensity() {
		return density;
	}

	public void setDensity(String density) {
		this.density = density;
	}

	public String getFlightCarrierflag() {
		return flightCarrierflag;
	}

	public void setFlightCarrierflag(String flightCarrierflag) {
		this.flightCarrierflag = flightCarrierflag;
	}

	public Collection<String> getPouList() {
		return pouList;
	}

	public void setPouList(Collection<String> pouList) {
		this.pouList = pouList;
	}

	public ContainerDetails getNewContainerInfo() {
		return newContainerInfo;
	}

	public void setNewContainerInfo(ContainerDetails newContainerInfo) {
		this.newContainerInfo = newContainerInfo;
	}

	public Collection<ContainerDetails> getNewContainersDetails() {
		return newContainersDetails;
	}

	public void setNewContainersDetails(
			Collection<ContainerDetails> newContainersDetails) {
		this.newContainersDetails = newContainersDetails;
	}

	public MailbagFilter getMailbagFilter() {
		return mailbagFilter;
	}

	public void setMailbagFilter(MailbagFilter mailbagFilter) {
		this.mailbagFilter = mailbagFilter;
	}

	public PageResult<Mailbag> getLyinglistMailbags() {
		return lyinglistMailbags;
	}

	public void setLyinglistMailbags(PageResult<Mailbag> lyinglistMailbags) {
		this.lyinglistMailbags = lyinglistMailbags;
	}

	public PageResult<Mailbag> getLyinglistGroupedMailbags() {
		return lyinglistGroupedMailbags;
	}

	public void setLyinglistGroupedMailbags(
			PageResult<Mailbag> lyinglistGroupedMailbags) {
		this.lyinglistGroupedMailbags = lyinglistGroupedMailbags;
	}

	
	public List<Mailbag> getMailbags() {
		return mailbags;
	}

	public void setMailbags(List<Mailbag> mailbags) {
		this.mailbags = mailbags;
	}

	public Collection<Warehouse> getWareHouses() {
		return wareHouses;
	}

	public void setWareHouses(Collection<Warehouse> wareHouses) {
		this.wareHouses = wareHouses;
	}

	public List<MailAcceptance> getMailAcceptanceList() {
		return mailAcceptanceList;
	}

	public void setMailAcceptanceList(List<MailAcceptance> mailAcceptanceList) {
		this.mailAcceptanceList = mailAcceptanceList;
	}

	public ContainerDetails getSelectedContainer() {
		return selectedContainer;
	}

	public void setSelectedContainer(ContainerDetails selectedContainer) {
		this.selectedContainer = selectedContainer;
	}

	public String getWarningOverride() {
		return warningOverride;
	}

	public void setWarningOverride(String warningOverride) {
		this.warningOverride = warningOverride;
	}
	public ArrayList<ContainerDetails> getContainerDetailsCollection() {
		return ContainerDetailsCollection;
	}
	public void setContainerDetailsCollection(
			ArrayList<ContainerDetails> containerDetailsCollection) {
		ContainerDetailsCollection = containerDetailsCollection;
	}
	public AttachAwbDetails getAttachAwbDetails() {
		return attachAwbDetails;
	}
	public void setAttachAwbDetails(AttachAwbDetails attachAwbDetails) {
		this.attachAwbDetails = attachAwbDetails;
	}
	public String getSelectedIndex() {
		return selectedIndex;
	}
	public void setSelectedIndex(String selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
	public AttachRoutingDetails getAttachRoutingDetails() {
		return attachRoutingDetails;
	}
	public void setAttachRoutingDetails(AttachRoutingDetails attachRoutingDetails) {
		this.attachRoutingDetails = attachRoutingDetails;
	}
	public String getCsgDocNumForRouting() {
		return csgDocNumForRouting;
	}
	public void setCsgDocNumForRouting(String csgDocNumForRouting) {
		this.csgDocNumForRouting = csgDocNumForRouting;
	}
	public String getPaCodeForRouting() {
		return paCodeForRouting;
	}
	public void setPaCodeForRouting(String paCodeForRouting) {
		this.paCodeForRouting = paCodeForRouting;
	}
	public Collection<MailInConsignment> getMailsInConsignment() {
		return mailsInConsignment;
	}
	public void setMailsInConsignment(
			Collection<MailInConsignment> mailsInConsignment) {
		this.mailsInConsignment = mailsInConsignment;
	}
	public String getRoutingDirection() {
		return RoutingDirection;
	}
	public void setRoutingDirection(String routingDirection) {
		RoutingDirection = routingDirection;
	}
	public String getNewRoutingFlag() {
		return newRoutingFlag;
	}
	public void setNewRoutingFlag(String newRoutingFlag) {
		this.newRoutingFlag = newRoutingFlag;
	}
	public ConsignmentDocument getConsignmentDocument() {
		return consignmentDocument;
	}
	public void setConsignmentDocument(ConsignmentDocument consignmentDocument) {
		this.consignmentDocument = consignmentDocument;
	}
	public int getContainerDisplayPage() {
		return containerDisplayPage;
	}
	public void setContainerDisplayPage(int containerDisplayPage) {
		this.containerDisplayPage = containerDisplayPage;
	}
	public int getMailbagsDisplayPage() {
		return mailbagsDisplayPage;
	}
	public void setMailbagsDisplayPage(int mailbagsDisplayPage) {
		this.mailbagsDisplayPage = mailbagsDisplayPage;
	}
	public int getMailbagsDSNDisplayPage() {
		return mailbagsDSNDisplayPage;
	}
	public void setMailbagsDSNDisplayPage(int mailbagsDSNDisplayPage) {
		this.mailbagsDSNDisplayPage = mailbagsDSNDisplayPage;
	}
	public ContainerDetails getImportFromContainer() {
		return importFromContainer;
	}
	public void setImportFromContainer(ContainerDetails importFromContainer) {
		this.importFromContainer = importFromContainer;
	}
	public Collection<Mailbag> getImportedmailbags() {
		return importedmailbags;
	}
	public void setImportedmailbags(Collection<Mailbag> importedmailbags) {
		this.importedmailbags = importedmailbags;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	public ArrayList<PostalAdministrationModel> getPostalAdministrations() {
		return postalAdministrations;
	}
	public void setPostalAdministrations(
			ArrayList<PostalAdministrationModel> postalAdministrations) {
		this.postalAdministrations = postalAdministrations;
	}
	public HashMap<String, Collection<FlightSegmentCapacitySummary>> getFlightCapacityDetails() {
		return flightCapacityDetails;
	}
	public void setFlightCapacityDetails(
			HashMap<String, Collection<FlightSegmentCapacitySummary>> flightCapacityDetails) {
		this.flightCapacityDetails = flightCapacityDetails;
	}

	public String getDefWeightUnit() {
		return defWeightUnit;  
	}

	public void setDefWeightUnit(String defWeightUnit) {
		this.defWeightUnit = defWeightUnit;
	}
	public Map<String, String> getWarningMessagesStatus() {
		return warningMessagesStatus;
	}
	public void setWarningMessagesStatus(Map<String, String> warningMessagesStatus) {
		this.warningMessagesStatus = warningMessagesStatus;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public ArrayList<DespatchDetails> getDespatchDetailsList() {
		return despatchDetailsList;
	}
	public void setDespatchDetailsList(ArrayList<DespatchDetails> despatchDetailsList) {
		this.despatchDetailsList = despatchDetailsList;
	}
	public Collection<MailInConsignmentVO> getCreateMailInConsignmentVOs() {
		return createMailInConsignmentVOs;
	}
	public void setCreateMailInConsignmentVOs(Collection<MailInConsignmentVO> createMailInConsignmentVOs) {
		this.createMailInConsignmentVOs = createMailInConsignmentVOs;
	}
/*	public Collection<ExistingMailbag> getExistingMailbags() {
		return existingMailbags;
	}
	public void setExistingMailbags(Collection<ExistingMailbag> existingMailbags) {
		this.existingMailbags = existingMailbags;
	}*/
	public String getExistingMailbagFlag() {
		return existingMailbagFlag;
	}
	public Collection<Mailbag> getExistingMailbags() {
		return existingMailbags;
	}
	public void setExistingMailbags(Collection<Mailbag> existingMailbags) {
		this.existingMailbags = existingMailbags;
	}
	public void setExistingMailbagFlag(String existingMailbagFlag) {
		this.existingMailbagFlag = existingMailbagFlag;
	}
	public String[] getReassignFlag() {
		return reassignFlag;
	}
	public void setReassignFlag(String[] reassignFlag) {
		this.reassignFlag = reassignFlag;
	}
	public String getStationVolUnt() {
		return stationVolUnt;
	}
	public void setStationVolUnt(String stationVolUnt) {
		this.stationVolUnt = stationVolUnt;
	}
	public String getTab() {
		return tab;
	}
	public void setTab(String tab) {
		this.tab = tab;
    }
	public int getDefaultPageSize() {
		return defaultPageSize;
	}
	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}
	public boolean isWeightScaleAvailable() {
		return weightScaleAvailable;
	}
	public void setWeightScaleAvailable(boolean weightScaleAvailable) {
		this.weightScaleAvailable = weightScaleAvailable;
	}

public boolean isEnableDeviationListTab() {
		return enableDeviationListTab;
	}
	public void setEnableDeviationListTab(boolean enableDeviationListTab) {
		this.enableDeviationListTab = enableDeviationListTab;
	}

	public String getShowWarning() {
		return showWarning;
	}
	public void setShowWarning(String showWarning) {
		this.showWarning = showWarning;
	}
	/**
	 * @return the stationWeightUnit
	 */
	public String getStationWeightUnit() {
		return stationWeightUnit;
	}
	/**
	 * @param stationWeightUnit the stationWeightUnit to set
	 */
	public void setStationWeightUnit(String stationWeightUnit) {
		this.stationWeightUnit = stationWeightUnit;
	}						  
	public String getUldToBarrowAllow() {
		return uldToBarrowAllow;
	}
	public void setUldToBarrowAllow(String uldToBarrowAllow) {
		this.uldToBarrowAllow = uldToBarrowAllow;
	}
	public String getDefaultOperatingReference() {
		return defaultOperatingReference;
	}
	public void setDefaultOperatingReference(String defaultOperatingReference) {
		this.defaultOperatingReference = defaultOperatingReference;
	}
	public boolean isMandateScanTime() {
		return mandateScanTime;
	}
	public void setMandateScanTime(boolean mandateScanTime) {
		this.mandateScanTime = mandateScanTime;
	}
	public String getAddMailbagMode() {
		return addMailbagMode;
	}
	public void setAddMailbagMode(String addMailbagMode) {
		this.addMailbagMode = addMailbagMode;
	}
	public String getOwnAirlineCode() {
		return ownAirlineCode;
	}
	public void setOwnAirlineCode(String ownAirlineCode) {
		this.ownAirlineCode = ownAirlineCode;
	}
	public Collection<String> getPartnerCarriers() {
		return partnerCarriers;
	}
	public void setPartnerCarriers(Collection<String> partnerCarriers) {
		this.partnerCarriers = partnerCarriers;
	}
	public boolean isDisableForModify() {
		return disableForModify;
	}
	public void setDisableForModify(boolean disableForModify) {
		this.disableForModify = disableForModify;
	}
	public boolean isEmbargoInfo() {
		return embargoInfo;
	}
	public void setEmbargoInfo(boolean embargoInfo) {
		this.embargoInfo = embargoInfo;
	}
	public String getActiveTab() {
		return activeTab;
	}
	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}
	public String getContainerJnyID() {
		return containerJnyID;
	}
	public void setContainerJnyID(String containerJnyID) {
		this.containerJnyID = containerJnyID;
	}
	public boolean isFromContainerTab() {
		return fromContainerTab;
	}
	public void setFromContainerTab(boolean fromContainerTab) {
		this.fromContainerTab = fromContainerTab;
	}
	public Map<String, MailAcceptanceVO> getFlightPreAdviceDetails() {
		return flightPreAdviceDetails;
	}
	public void setFlightPreAdviceDetails(Map<String, MailAcceptanceVO> flightPreAdviceDetails) {
		this.flightPreAdviceDetails = flightPreAdviceDetails;
	}
	public boolean isContainerTransferCheck() {
		return containerTransferCheck;
	}
	public void setContainerTransferCheck(boolean containerTransferCheck) {
		this.containerTransferCheck = containerTransferCheck;
	}
	public Map<String, Collection<FlightSegmentVolumeSummary>> getFlightVolumeDetails() {
		return flightVolumeDetails;
	}
	public void setFlightVolumeDetails(Map<String, Collection<FlightSegmentVolumeSummary>> flightVolumeDetails) {
		this.flightVolumeDetails = flightVolumeDetails;
	}
}
