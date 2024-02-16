package com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.DamagedMailbag;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailbagFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundFilter;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.DespatchDetails;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	27-Sep-2018		:	Draft
 */
public class MailinboundModel extends AbstractScreenModel{

	
	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "operations";

	private static final String SCREENID = "mail.operations.ux.mailinbound";
	
	private MailinboundFilter mailinboundFilter;
	
	private MailinboundDetails mailinboundDetails;
	
	private DSNDetails dsnDetails;
	
	private MailBagDetails mailBagDetails;
	
	private FlightValidationVO flightValidationVO;
	
	private MailArrivalVO mailArrivalVO;
	
	private AddContainer addContainer;
	
	private OperationalFlightVO operationalFlightVO;
	
	private ArrayList<FlightValidationVO> flightValidationVOs;
	
	private PageResult<MailinboundDetails> mailinboundDetailsCollectionPage;
	
	private ArrayList<MailinboundDetails> mailinboundDetailsCollection;
	
	private Page<MailArrivalVO> mailArrivalVOs;
	
	private ArrayList<ContainerDetails> containerDetailsCollection;
	
	private PageResult<ContainerDetails> containerDetailsCollectionPage;
	
	private PageResult<DSNDetails> dsnDetailsCollectionPage;
	
	private PageResult<MailBagDetails> mailBagDetailsCollectionPage;
	
	private Collection<ContainerDetailsVO> containerDetailsVos;
	
	private ArrayList<AddMailbag> addMailbags;
	
	private ArrayList<DiscrepancyDetails> DiscrepancyDetailsCollection;
	
	private ContainerDetails containerDetail;
	
	private int carrierId;
	
	private long flightSeqNumber;
	
	private int legSerialNumber;
	
	private String messageStatus; 
	
	private String arrivedFlag;
	
	private String arrivalDate;
	
	private String currentDate;
	
	private String currentTime;
	
	private TransferDetails transferDetails;
	
	private AttachAwbDetails attachAwbDetails;
	
	private DeliverMailDetails deliverMailDetails;
	
	private ChangeContainerDetails changeContainerDetails;
	
	private ArrayList<ContainerVO> containerVOs;

	private HashMap<String, Collection<DSNVO>> consignmentMap;
	
	private String operationLevel;
	
	private ArrayList <DamagedMailbag> damagedMailBagCollection;
	
	private AttachRoutingDetails attachRoutingDetails;
	
	private ConsignmentDocumentVO consignmentDocumentVO;
	
	private Collection<MailInConsignmentVO> createMailInConsignmentVOs;
	
	private HashMap<String, Collection<OneTimeVO>> oneTimeValues;

	private TransferManifestVO transferManifestVO; //Added as part of ICRD-343290 
	
	private String showWarning;

	List<com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails> containerList;
	//Added as part of IASCB-34119
	private String ownAirlineCode; 
	private Collection<String> partnerCarriers;
	
	private MailbagFilter mailbagFilter;
	
	private MailbagFilter dsnFilter;
	
	private String addMailbagMode;
	
	private String removeReason;
	private String removeRemarks;
	private String removeType;
	private String activeTab;
	
	private String popupAction;
	private boolean embargoInfo;
	private String embargoFlag;
	private String transactionLevel;
	private boolean screenWarning;	

    private List<DespatchDetails>despatchDetailsList;
public List<DespatchDetails> getDespatchDetailsList() {
		return despatchDetailsList;
	}
	public void setDespatchDetailsList(List<DespatchDetails> despatchDetailsList) {
		this.despatchDetailsList = despatchDetailsList;
	}
	public String getEmbargoFlag() {
		return embargoFlag;
	}
	public void setEmbargoFlag(String embargoFlag) {
		this.embargoFlag = embargoFlag;
	}
    public TransferManifestVO getTransferManifestVO() {
		return transferManifestVO;
	}

	public void setTransferManifestVO(TransferManifestVO transferManifestVO) {
		this.transferManifestVO = transferManifestVO;
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

	public MailinboundFilter getMailinboundFilter() {
		return mailinboundFilter;
	}

	public void setMailinboundFilter(MailinboundFilter mailinboundFilter) {
		this.mailinboundFilter = mailinboundFilter;
	}

	public MailinboundDetails getMailinboundDetails() {
		return mailinboundDetails;
	}

	public void setMailinboundDetails(MailinboundDetails mailinboundDetails) {
		this.mailinboundDetails = mailinboundDetails;
	}

	public static String getSubproduct() {
		return SUBPRODUCT;
	}

	public static String getScreenid() {
		return SCREENID;
	}

	public FlightValidationVO getFlightValidationVO() {
		return flightValidationVO;
	}

	public void setFlightValidationVO(FlightValidationVO flightValidationVO) {
		this.flightValidationVO = flightValidationVO;
	}

	public ArrayList<FlightValidationVO> getFlightValidationVOs() {
		return flightValidationVOs;
	}

	public void setFlightValidationVOs(ArrayList<FlightValidationVO> flightValidationVOs) {
		this.flightValidationVOs = flightValidationVOs;
	}

	public MailArrivalVO getMailArrivalVO() {
		return mailArrivalVO;
	}

	public void setMailArrivalVO(MailArrivalVO mailArrivalVO) {
		this.mailArrivalVO = mailArrivalVO;
	}

	public int getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	public long getFlightSeqNumber() {
		return flightSeqNumber;
	}

	public void setFlightSeqNumber(long flightSeqNumber) {
		this.flightSeqNumber = flightSeqNumber;
	}

	public int getLegSerialNumber() {
		return legSerialNumber;
	}

	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	public HashMap<String, Collection<DSNVO>> getConsignmentMap() {
		return consignmentMap;
	}

	public void setConsignmentMap(HashMap<String, Collection<DSNVO>> consignmentMap) {
		this.consignmentMap = consignmentMap;
	}

	public OperationalFlightVO getOperationalFlightVO() {
		return operationalFlightVO;
	}

	public void setOperationalFlightVO(OperationalFlightVO operationalFlightVO) {
		this.operationalFlightVO = operationalFlightVO;
	}

	public Page<MailArrivalVO> getMailArrivalVOs() {
		return mailArrivalVOs;
	}

	public void setMailArrivalVOs(Page<MailArrivalVO> mailArrivalVOs) {
		this.mailArrivalVOs = mailArrivalVOs;
	}

	public ArrayList<ContainerDetails> getContainerDetailsCollection() {
		return containerDetailsCollection;
	}

	public void setContainerDetailsCollection(ArrayList<ContainerDetails> containerDetailsCollection) {
		this.containerDetailsCollection = containerDetailsCollection;
	}

	public AddContainer getAddContainer() {
		return addContainer;
	}

	public void setAddContainer(AddContainer addContainer) {
		this.addContainer = addContainer;
	}

	public Collection<ContainerDetailsVO> getContainerDetailsVos() {
		return containerDetailsVos;
	}

	public void setContainerDetailsVos(Collection<ContainerDetailsVO> containerDetailsVos) {
		this.containerDetailsVos = containerDetailsVos;
	}

	public String getArrivedFlag() {
		return arrivedFlag;
	}

	public void setArrivedFlag(String arrivedFlag) {
		this.arrivedFlag = arrivedFlag;
	}

	public ContainerDetails getContainerDetail() {
		return containerDetail;
	}

	public void setContainerDetail(ContainerDetails containerDetail) {
		this.containerDetail = containerDetail;
	}

	public ArrayList<AddMailbag> getAddMailbags() {
		return addMailbags;
	}

	public void setAddMailbags(ArrayList<AddMailbag> addMailbags) {
		this.addMailbags = addMailbags;
	}

	public TransferDetails getTransferDetails() {
		return transferDetails;
	}

	public void setTransferDetails(TransferDetails transferDetails) {
		this.transferDetails = transferDetails;
	}

	public ArrayList<DiscrepancyDetails> getDiscrepancyDetailsCollection() {
		return DiscrepancyDetailsCollection;
	}

	public void setDiscrepancyDetailsCollection(ArrayList<DiscrepancyDetails> discrepancyDetailsCollection) {
		DiscrepancyDetailsCollection = discrepancyDetailsCollection;
	}

	public AttachAwbDetails getAttachAwbDetails() {
		return attachAwbDetails;
	}

	public void setAttachAwbDetails(AttachAwbDetails attachAwbDetails) {
		this.attachAwbDetails = attachAwbDetails;
	}

	public String getOperationLevel() {
		return operationLevel;
	}

	public void setOperationLevel(String operationLevel) {
		this.operationLevel = operationLevel;
	}

	public DeliverMailDetails getDeliverMailDetails() {
		return deliverMailDetails;
	}

	public void setDeliverMailDetails(DeliverMailDetails deliverMailDetails) {
		this.deliverMailDetails = deliverMailDetails;
	}

	public ChangeContainerDetails getChangeContainerDetails() {
		return changeContainerDetails;
	}

	public void setChangeContainerDetails(ChangeContainerDetails changeContainerDetails) {
		this.changeContainerDetails = changeContainerDetails;
	}

	public ArrayList<ContainerVO> getContainerVOs() {
		return containerVOs;
	}

	public void setContainerVOs(ArrayList<ContainerVO> containerVOs) {
		this.containerVOs = containerVOs;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public ArrayList<DamagedMailbag> getDamagedMailBagCollection() {
		return damagedMailBagCollection;
	}

	public void setDamagedMailBagCollection(ArrayList<DamagedMailbag> damagedMailBagCollection) {
		this.damagedMailBagCollection = damagedMailBagCollection;
	}

	public AttachRoutingDetails getAttachRoutingDetails() {
		return attachRoutingDetails;
	}

	public void setAttachRoutingDetails(AttachRoutingDetails attachRoutingDetails) {
		this.attachRoutingDetails = attachRoutingDetails;
	}

	public ConsignmentDocumentVO getConsignmentDocumentVO() {
		return consignmentDocumentVO;
	}

	public void setConsignmentDocumentVO(ConsignmentDocumentVO consignmentDocumentVO) {
		this.consignmentDocumentVO = consignmentDocumentVO;
	}

	public Collection<MailInConsignmentVO> getCreateMailInConsignmentVOs() {
		return createMailInConsignmentVOs;
	}

	public void setCreateMailInConsignmentVOs(Collection<MailInConsignmentVO> createMailInConsignmentVOs) {
		this.createMailInConsignmentVOs = createMailInConsignmentVOs;
	}

	public PageResult<MailinboundDetails> getMailinboundDetailsCollectionPage() {
		return mailinboundDetailsCollectionPage;
	}

	public void setMailinboundDetailsCollectionPage(PageResult<MailinboundDetails> mailinboundDetailsCollectionPage) {
		this.mailinboundDetailsCollectionPage = mailinboundDetailsCollectionPage;
	}

	public ArrayList<MailinboundDetails> getMailinboundDetailsCollection() {
		return mailinboundDetailsCollection;
	}

	public void setMailinboundDetailsCollection(ArrayList<MailinboundDetails> mailinboundDetailsCollection) {
		this.mailinboundDetailsCollection = mailinboundDetailsCollection;
	}

	public PageResult<ContainerDetails> getContainerDetailsCollectionPage() {
		return containerDetailsCollectionPage;
	}

	public void setContainerDetailsCollectionPage(PageResult<ContainerDetails> containerDetailsCollectionPage) {
		this.containerDetailsCollectionPage = containerDetailsCollectionPage;
	}

	public PageResult<DSNDetails> getDsnDetailsCollectionPage() {
		return dsnDetailsCollectionPage;
	}

	public void setDsnDetailsCollectionPage(PageResult<DSNDetails> dsnDetailsCollectionPage) {
		this.dsnDetailsCollectionPage = dsnDetailsCollectionPage;
	}

	public PageResult<MailBagDetails> getMailBagDetailsCollectionPage() {
		return mailBagDetailsCollectionPage;
	}

	public void setMailBagDetailsCollectionPage(PageResult<MailBagDetails> mailBagDetailsCollectionPage) {
		this.mailBagDetailsCollectionPage = mailBagDetailsCollectionPage;
	}

	public DSNDetails getDsnDetails() {
		return dsnDetails;
	}

	public void setDsnDetails(DSNDetails dsnDetails) {
		this.dsnDetails = dsnDetails;
	}

	public MailBagDetails getMailBagDetails() {
		return mailBagDetails;
	}

	public void setMailBagDetails(MailBagDetails mailBagDetails) {
		this.mailBagDetails = mailBagDetails;
	}

	public HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(HashMap<String, Collection<OneTimeVO>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
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
	public String getShowWarning() {
		return showWarning;
	}
	public void setShowWarning(String showWarning) {
		this.showWarning = showWarning;
	}
	
	/**
	 * @return the containerList
	 */
	public List<com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails> getContainerList() {
		return containerList;
	}
	/**
	 * @param containerList the containerList to set
	 */
	public void setContainerList(
			List<com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails> containerList) {
		this.containerList = containerList;
	}
	public String getAddMailbagMode() {
		return addMailbagMode;
	}
	public void setAddMailbagMode(String addMailbagMode) {
		this.addMailbagMode = addMailbagMode;
	}

	public MailbagFilter getMailbagFilter() {
		return mailbagFilter;
	}

	public void setMailbagFilter(MailbagFilter mailbagFilter) {
		this.mailbagFilter = mailbagFilter;
	}

	public MailbagFilter getDsnFilter() {
		return dsnFilter;
	}

	public void setDsnFilter(MailbagFilter dsnFilter) {
		this.dsnFilter = dsnFilter;
	}

	public String getRemoveReason() {
		return removeReason;
	}

	public void setRemoveReason(String removeReason) {
		this.removeReason = removeReason;
	}

	public String getRemoveRemarks() {
		return removeRemarks;
	}

	public void setRemoveRemarks(String removeRemarks) {
		this.removeRemarks = removeRemarks;
	}

	public String getRemoveType() {
		return removeType;
	}

	public void setRemoveType(String removeType) {
		this.removeType = removeType;
	}

	public String getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}

	public String getPopupAction() {
		return popupAction;
	}

	public void setPopupAction(String popupAction) {
		this.popupAction = popupAction;
	}

	public boolean isEmbargoInfo() {
		return embargoInfo;
	}

	public void setEmbargoInfo(boolean embargoInfo) {
		this.embargoInfo = embargoInfo;
	}
	public String getTransactionLevel() {
		return transactionLevel;
	}
	public void setTransactionLevel(String transactionLevel) {
		this.transactionLevel = transactionLevel;
	}	
    public boolean isScreenWarning() {
		return screenWarning;
	}

	public void setScreenWarning(boolean screenWarning) {
		this.screenWarning = screenWarning;
	}
	
}
