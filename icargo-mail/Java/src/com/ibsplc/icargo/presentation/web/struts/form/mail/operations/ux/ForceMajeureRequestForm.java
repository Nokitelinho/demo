package com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux;

import antlr.collections.List;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;
import org.apache.struts.upload.FormFile;


/**
 * @author A-8527
 *
 */
public class ForceMajeureRequestForm extends ScreenModel{
	
	private static final String SCREEN_ID = "mail.operations.ux.forcemajeure";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "ForceMajeureResources";
	private String origin_airport;
	private String destination;
	private String viaPoint;
	private String affectedAirport;
	private String pacode;
	private String flightNumber;
	private String flightDateStr;
	private String frmDate;
	private String toDate;
	private String source;
	private String lastPageNum;
	private String totalRecords; 
	private String displayPage;
	private String CarrierCode;
	private String btnNewList;
	private String btnNewClear;
	private String newTabRemarks;
	private String reqTabRemarks;
	private String defaultPageSize = "100";
	private String btnsave;
	private String btnClose;
	private String actionFlag;
	private String forceid;
	private String displaytype="1";
	private String[]checkSel;
	private String[] checkall;
	private List forceids;
	private String lovaction;
	private String selectedValues="";
	private String formCount;
	private String lovTxtFieldName;
	private String lovDescriptionTxtFieldName;
	private String[] selectCheckBox;
	private int index;
	private String multiselect;
	private String reqStatus;
	private String sortOrder;
	private String sortingField;
	private String frmTime;
	private String toTime;
	private String scanType;
	private String userId;
	
	private String airportFilter;
	private String carrierFilter;
	private String flightNumberFilter;
	private String flightDateFilter;
	private String consignmentNo;
	private String mailbagId;
	private FormFile file;
	private String fileType;
	private String uploadStatus;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFrmTime() {
		return frmTime;
	}

	public void setFrmTime(String frmTime) {
		this.frmTime = frmTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}

	/**
	 * @return screenId
	 */
	public String getScreenId() {
		return SCREEN_ID;
	}

	/**
	 * @return product
	 */
	public String getProduct() {
		return PRODUCT_NAME;
	}

	/**
	 * @return subProduct
	 */
	public String getSubProduct() {
		return SUBPRODUCT_NAME;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @param origin_airport The origin airport to set and get.
	 */
	public String getOrigin_airport() {
		return origin_airport;
	}
	
	public void setOrigin_airport(String origin_airport) {
		this.origin_airport = origin_airport;
	}
	/**
	 * @param destination The destination airport to set and get.
	 */
	public String getDestination() {
		return destination;
	}

	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @param viaPoint The viaPoint  to set and get.
	 */
	public String getViaPoint() {
		return viaPoint;
	}

	
	public void setViaPoint(String viaPoint) {
		this.viaPoint = viaPoint;
	}
	/**
	 * @param origin_airport The origin airport to set and get.
	 */
	public String getAffectedAirport() {
		return affectedAirport;
	}

	
	public void setAffectedAirport(String affectedAirport) {
		this.affectedAirport = affectedAirport;
	}
	
	/**
	 * @param viaPoint The viaPoint  to set.
	 */
	public String getPacode() {
		return pacode;
	}

	
	public void setPacode(String pacode) {
		this.pacode = pacode;
	}
	
	/**
	 * @param viaPoint The viaPoint  to set.
	 */
	
	
	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @param viaPoint The viaPoint  to set.
	 */
	public String getFlightDateStr() {
		return flightDateStr;
	}

	
	public void setFlightDateStr(String flightDateStr) {
		this.flightDateStr = flightDateStr;
	}
	
	/**
	 * @param frmDate The frmDate  to set.
	 */
	
	@DateFieldId(id="forceMajeureDateRange",fieldType="from")  //Added by A-7929 for IASCB-27457
	public String getFrmDate() {
		return frmDate;
	}

	
	public void setFrmDate(String frmDate) {
		this.frmDate = frmDate;
	}
	/**
	 * @param viaPoint The viaPoint  to set.
	 */
	@DateFieldId(id="forceMajeureDateRange",fieldType="to")  //Added by A-7929 for IASCB-27457
	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @param source The source  to set.
	 */
	public String getSource() {
		return source;
	}

	
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @param lastPageNum The lastPageNum  to set.
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}

	
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
	/**
	 * @param displayPage The displayPage  to set.
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	
	public String getCarrierCode() {
		return CarrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		CarrierCode = carrierCode;
	}

	public String getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getBtnNewList() {
		return btnNewList;
	}

	public void setBtnNewList(String btnNewList) {
		this.btnNewList = btnNewList;
	}

	public String getBtnNewClear() {
		return btnNewClear;
	}

	public void setBtnNewClear(String btnNewClear) {
		this.btnNewClear = btnNewClear;
	}



	public String getDefaultPageSize() {
		return defaultPageSize;
	}

	public void setDefaultPageSize(String defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}

	public String getBtnClose() {
		return btnClose;
	}

	public void setBtnClose(String btnClose) {
		this.btnClose = btnClose;
	}
	public String getBtnsave() {
		return btnsave;
	}

	public void setBtnsave(String btnsave) {
		this.btnsave = btnsave;
	}

	public String getActionFlag() {
		return actionFlag;
	}

	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}
	public String getForceid() {
		return forceid;
	}
	public void setForceid(String forceid) {
		this.forceid = forceid;
	}
	public String[] getCheckSel() {
		return checkSel;
	}
	public void setCheckSel(String[] checkSel) {
		this.checkSel = checkSel;
	}
	public String getDisplaytype() {
		return displaytype;
	}
	public void setDisplaytype(String displaytype) {
		this.displaytype = displaytype;
	}
	public List getForceids() {
		return forceids;
	}
	public String getLovaction() {
		return lovaction;
	}
	public void setLovaction(String lovaction) {
		this.lovaction = lovaction;
	}
	public String getSelectedValues() {
		return selectedValues;
	}
	public void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
	}
	public String getFormCount() {
		return formCount;
	}
	public void setFormCount(String formCount) {
		this.formCount = formCount;
	}
	public String getLovTxtFieldName() {
		return lovTxtFieldName;
	}
	public void setLovTxtFieldName(String lovTxtFieldName) {
		this.lovTxtFieldName = lovTxtFieldName;
	}
	public String getLovDescriptionTxtFieldName() {
		return lovDescriptionTxtFieldName;
	}
	public void setLovDescriptionTxtFieldName(String lovDescriptionTxtFieldName) {
		this.lovDescriptionTxtFieldName = lovDescriptionTxtFieldName;
	}
	public void setForceids(List forceids) {
		this.forceids = forceids;
	}
	public String[] getCheckall() {
		return checkall;
	}
	public void setCheckall(String[] checkall) {
		this.checkall = checkall;
	}
	public String[] getSelectCheckBox() {
		return selectCheckBox;
	}
	public void setSelectCheckBox(String[] selectCheckBox) {
		this.selectCheckBox = selectCheckBox;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getMultiselect() {
		return multiselect;
	}
	public void setMultiselect(String multiselect) {
		this.multiselect = multiselect;
	}
	public String getReqStatus() {
		return reqStatus;
	}
	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getSortingField() {
		return sortingField;
	}
	public void setSortingField(String sortingField) {
		this.sortingField = sortingField;
	}
	public String getNewTabRemarks() {
		return newTabRemarks;
	}
	public void setNewTabRemarks(String newTabRemarks) {
		this.newTabRemarks = newTabRemarks;
	}
	public String getReqTabRemarks() {
		return reqTabRemarks;
	}
	public void setReqTabRemarks(String reqTabRemarks) {
		this.reqTabRemarks = reqTabRemarks;
	}

	public String getAirportFilter() {
		return airportFilter;
	}

	public void setAirportFilter(String airportFilter) {
		this.airportFilter = airportFilter;
	}

	public String getCarrierFilter() {
		return carrierFilter;
	}

	public void setCarrierFilter(String carrierFilter) {
		this.carrierFilter = carrierFilter;
	}

	public String getFlightNumberFilter() {
		return flightNumberFilter;
	}

	public void setFlightNumberFilter(String flightNumberFilter) {
		this.flightNumberFilter = flightNumberFilter;
	}

	public String getFlightDateFilter() {
		return flightDateFilter;
	}

	public void setFlightDateFilter(String flightDateFilter) {
		this.flightDateFilter = flightDateFilter;
	}

	public String getConsignmentNo() {
		return consignmentNo;
	}

	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}

	public String getMailbagId() {
		return mailbagId;
	}

	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}
	
    public FormFile getFile() {
        return file;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }

    public String getFileType() {
        return fileType;
    }
	
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
	public String getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	
}
