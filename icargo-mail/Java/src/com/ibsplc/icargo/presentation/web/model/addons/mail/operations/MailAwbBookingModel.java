package com.ibsplc.icargo.presentation.web.model.addons.mail.operations;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.addons.mail.operations.common.AwbFilter;
import com.ibsplc.icargo.presentation.web.model.addons.mail.operations.common.MailBookingDetail;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.CarditFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;

public class MailAwbBookingModel extends AbstractScreenModel  {
	
	private static final String PRODUCT = "addonsmail";
	private static final String SUBPRODUCT = "operations";
	private static final String SCREENID = "addons.mail.operations.mailawbbooking";
	
	private String displayPage;
	private String pageSize;
	private Collection<MailbagVO> selectedMailBagVOs;
    private String warningFlag;
    private Map<String, Collection<OneTimeVO>> oneTimeValues;
    private AwbFilter awbFilter;
    private PageResult<MailBookingDetail> mailBookingDetailsCollectionPage;
    private CarditFilter carditFilter;
    private List<MailBookingDetail> mailBookingDetailsCollection;
    private List<Mailbag> selectedMailBags;
	private Collection<MailBookingDetailVO> mailBookingDetailVOs;
	private String shipmentPrefix;
	private String masterDocumentNumber;
	private String bookingFlightNumber;
	private AwbFilter loadPlanFilter;
	private PageResult<MailBookingDetail> loadPlanDetailsCollectionPage;
	private List<MailBookingDetail> loadPlanDetailsCollection;
	private String fligtTab;
    private AwbFilter manifestFilter;
	private PageResult<MailBookingDetail> manifestDetailsCollectionPage;
	private List<MailBookingDetail> manifestDetailsCollection;
  

	public String getProduct() {
		return PRODUCT;
	}

	public String getScreenId() {
		return SCREENID;
	}
	
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public Collection<MailbagVO> getSelectedMailBagVOs() {
		return selectedMailBagVOs;
	}

	public void setSelectedMailBagVOs(Collection<MailbagVO> selectedMailBagVOs) {
		this.selectedMailBagVOs = selectedMailBagVOs;
	}
	public String getWarningFlag() {
		return warningFlag;
	}
	public void setWarningFlag(String warningFlag) {
		this.warningFlag = warningFlag;
	}

	public Map<String, Collection<OneTimeVO>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(Map<String, Collection<OneTimeVO>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}

	public AwbFilter getAwbFilter() {
		return awbFilter;
	}

	public void setAwbFilter(AwbFilter awbFilter) {
		this.awbFilter = awbFilter;
	}

	public PageResult<MailBookingDetail> getMailBookingDetailsCollectionPage() {
		return mailBookingDetailsCollectionPage;
	}

	public void setMailBookingDetailsCollectionPage(PageResult<MailBookingDetail> mailBookingDetailsCollectionPage) {
		this.mailBookingDetailsCollectionPage = mailBookingDetailsCollectionPage;
	}

	public CarditFilter getCarditFilter() {
		return carditFilter;
	}

	public void setCarditFilter(CarditFilter carditFilter) {
		this.carditFilter = carditFilter;
	}

	public List<MailBookingDetail> getMailBookingDetailsCollection() {
		return mailBookingDetailsCollection;
	}

	public void setMailBookingDetailsCollection(List<MailBookingDetail> mailBookingDetailsCollection) {
		this.mailBookingDetailsCollection = mailBookingDetailsCollection;
	}

	public List<Mailbag> getSelectedMailBags() {
		return selectedMailBags;
	}

	public void setSelectedMailBags(List<Mailbag> selectedMailBags) {
		this.selectedMailBags = selectedMailBags;
	}
	 public Collection<MailBookingDetailVO> getMailBookingDetailVOs() {
		return mailBookingDetailVOs;
	}
	public void setMailBookingDetailVOs(Collection<MailBookingDetailVO> mailBookingDetailVOs) {
		this.mailBookingDetailVOs = mailBookingDetailVOs;
	}
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	public String getBookingFlightNumber() {
		return bookingFlightNumber;
	}
	public void setBookingFlightNumber(String bookingFlightNumber) {
		this.bookingFlightNumber = bookingFlightNumber;
	}

	public AwbFilter getLoadPlanFilter() {
		return loadPlanFilter;
	}

	public void setLoadPlanFilter(AwbFilter loadPlanFilter) {
		this.loadPlanFilter = loadPlanFilter;
	}

	public PageResult<MailBookingDetail> getLoadPlanDetailsCollectionPage() {
		return loadPlanDetailsCollectionPage;
	}

	public void setLoadPlanDetailsCollectionPage(PageResult<MailBookingDetail> loadPlanDetailsCollectionPage) {
		this.loadPlanDetailsCollectionPage = loadPlanDetailsCollectionPage;
	}

	public List<MailBookingDetail> getLoadPlanDetailsCollection() {
		return loadPlanDetailsCollection;
	}

	public void setLoadPlanDetailsCollection(List<MailBookingDetail> loadPlanDetailsCollection) {
		this.loadPlanDetailsCollection = loadPlanDetailsCollection;
	}

	public String getFligtTab() {
		return fligtTab;
	}

	public void setFligtTab(String fligtTab) {
		this.fligtTab = fligtTab;
	}  
    public AwbFilter getManifestFilter() {
		return manifestFilter;
	}
	public void setManifestFilter(AwbFilter manifestFilter) {
		this.manifestFilter = manifestFilter;
	}
	public PageResult<MailBookingDetail> getManifestDetailsCollectionPage() {
		return manifestDetailsCollectionPage;
	}
	public void setManifestDetailsCollectionPage(PageResult<MailBookingDetail> manifestDetailsCollectionPage) {
		this.manifestDetailsCollectionPage = manifestDetailsCollectionPage;
	}
	public List<MailBookingDetail> getManifestDetailsCollection() {
		return manifestDetailsCollection;
	}
	public void setManifestDetailsCollection(List<MailBookingDetail> manifestDetailsCollection) {
		this.manifestDetailsCollection = manifestDetailsCollection;
	}
	
	
}
