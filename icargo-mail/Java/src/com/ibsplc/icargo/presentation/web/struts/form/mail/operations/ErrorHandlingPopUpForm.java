package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.MeasureAnnotation;

public class ErrorHandlingPopUpForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.errorhandligpopup";
	private static final String PRODUCT = "admin";
	private static final String SUBPRODUCT = "monitoring";
	private static final String BUNDLE = "errorhandlingresources";

	private String mailBag;
	 private String container;
	 private String transactionType;
	 private String checkbox;
	 private String remarks;
	 private String flightNumber;
	 private String flightDate;
	 private String fromTime;
	 private String toTime;
	 private String status;
	 private String selectedIndex;

		 private String closeStatus;

		 private String bulk;
		 private String pou;
		 private String	 destination;
		 private String	 subclass;
		 private String dsn;
		 private String  ooe;
		 private String  doe;
		 private String category;
		 private String rsn;
		 private String  year;
		 private String hni;
		 private String ri;
		 @MeasureAnnotation(mappedValue="weightMeasure",unitType="MWT")//added by A-7371
		 private String weight;
		 private Measure weightMeasure;
		private String selectedtxnid;
		 private String flightCarrierCode;
		 private String functionType;
		 private String currentDialogOption;
		  private String currentDialogId;
		  private String mailCompanyCode;
		  private boolean barrowCheck;//Added as part of ICRD-130510
		  //Added by A-5945 for ICRD-113473
		  private String transferCarrier;
		  private String pol;
		  private String transactionName;

		  private String displayPage = "1";
		  private String lastPageNum = "0";

		  private String displayPopupPage = "1";
		  private String lastPopupPageNum = "0";
		  private String totalViewRecords = "0";
		  
		  
		  private String isFlightCarrierCodeChanged;
		  private String isFlightNumberChanged;
		  private String isFlightDateChanged;
		  private String isContainerTypeChanged;
		  private String isContainerNumberChanged;
		  private String isDestinationChanged;
		  private String isMailCompanyCodeChanged;
		  private String isPouChanged;
		  private String  operationFlag;
		  //ADDED BY A-7531
		  private String orgin;
	      private String destn;
		  private String gpaCode;
		  private String isOriginChanged;
		  //Added by A-7540
		  private boolean invalidMailbagId;
		public boolean isInvalidMailbagId() {
			return invalidMailbagId;
		}
		public void setInvalidMailbagId(boolean invalidMailbagId) {
			this.invalidMailbagId = invalidMailbagId;
		}
		  /**
		 * 	Getter for isOriginChanged 
		 *	Added by : A-7531 on 12-Nov-2018
		 * 	Used for :
		 */
		public String getIsOriginChanged() {
			return isOriginChanged;
		}

		/**
		 *  @param isOriginChanged the isOriginChanged to set
		 * 	Setter for isOriginChanged 
		 *	Added by : A-7531 on 12-Nov-2018
		 * 	Used for :
		 */
		public void setIsOriginChanged(String isOriginChanged) {
			this.isOriginChanged = isOriginChanged;
		}

		/**
		 * 	Getter for isDestnChanged 
		 *	Added by : A-7531 on 12-Nov-2018
		 * 	Used for :
		 */
		public String getIsDestnChanged() {
			return isDestnChanged;
		}

		/**
		 *  @param isDestnChanged the isDestnChanged to set
		 * 	Setter for isDestnChanged 
		 *	Added by : A-7531 on 12-Nov-2018
		 * 	Used for :
		 */
		public void setIsDestnChanged(String isDestnChanged) {
			this.isDestnChanged = isDestnChanged;
		}

		/**
		 * 	Getter for isGpaChanged 
		 *	Added by : A-7531 on 12-Nov-2018
		 * 	Used for :
		 */
		public String getIsGpaChanged() {
			return isGpaChanged;
		}

		/**
		 *  @param isGpaChanged the isGpaChanged to set
		 * 	Setter for isGpaChanged 
		 *	Added by : A-7531 on 12-Nov-2018
		 * 	Used for :
		 */
		public void setIsGpaChanged(String isGpaChanged) {
			this.isGpaChanged = isGpaChanged;
		}



		private String isDestnChanged;
		  private String isGpaChanged;
		 public Measure getWeightMeasure() {
				return weightMeasure;
		 }

		public void setWeightMeasure(Measure weightMeasure) {
				this.weightMeasure = weightMeasure;
		}

		  
		  public String getOperationFlag() {
			return operationFlag;
		}

		public void setOperationFlag(String operationFlag) {
			this.operationFlag = operationFlag;
		}

		
		
		public String getIsFlightCarrierCodeChanged() {
			return isFlightCarrierCodeChanged;
		}

		public void setIsFlightCarrierCodeChanged(String isFlightCarrierCodeChanged) {
			this.isFlightCarrierCodeChanged = isFlightCarrierCodeChanged;
		}

		public String getIsFlightNumberChanged() {
			return isFlightNumberChanged;
		}

		public void setIsFlightNumberChanged(String isFlightNumberChanged) {
			this.isFlightNumberChanged = isFlightNumberChanged;
		}

		public String getIsFlightDateChanged() {
			return isFlightDateChanged;
		}

		public void setIsFlightDateChanged(String isFlightDateChanged) {
			this.isFlightDateChanged = isFlightDateChanged;
		}

		public String getIsContainerTypeChanged() {
			return isContainerTypeChanged;
		}

		public void setIsContainerTypeChanged(String isContainerTypeChanged) {
			this.isContainerTypeChanged = isContainerTypeChanged;
		}

		public String getIsContainerNumberChanged() {
			return isContainerNumberChanged;
		}

		public void setIsContainerNumberChanged(String isContainerNumberChanged) {
			this.isContainerNumberChanged = isContainerNumberChanged;
		}

		public String getIsDestinationChanged() {
			return isDestinationChanged;
		}

		public void setIsDestinationChanged(String isDestinationChanged) {
			this.isDestinationChanged = isDestinationChanged;
		}

		public String getIsMailCompanyCodeChanged() {
			return isMailCompanyCodeChanged;
		}

		public void setIsMailCompanyCodeChanged(String isMailCompanyCodeChanged) {
			this.isMailCompanyCodeChanged = isMailCompanyCodeChanged;
		}

		public String getIsPouChanged() {
			return isPouChanged;
		}

		public void setIsPouChanged(String isPouChanged) {
			this.isPouChanged = isPouChanged;
		}

		public String getIsPolChanged() {
			return isPolChanged;
		}

		public void setIsPolChanged(String isPolChanged) {
			this.isPolChanged = isPolChanged;
		}

		public String getIsOOEChanged() {
			return isOOEChanged;
		}

		public void setIsOOEChanged(String isOOEChanged) {
			this.isOOEChanged = isOOEChanged;
		}

		public String getIsDOEChanged() {
			return isDOEChanged;
		}

		public void setIsDOEChanged(String isDOEChanged) {
			this.isDOEChanged = isDOEChanged;
		}

		public String getIsCategoryChanged() {
			return isCategoryChanged;
		}

		public void setIsCategoryChanged(String isCategoryChanged) {
			this.isCategoryChanged = isCategoryChanged;
		}

		public String getIsSubClassChanged() {
			return isSubClassChanged;
		}

		public void setIsSubClassChanged(String isSubClassChanged) {
			this.isSubClassChanged = isSubClassChanged;
		}

		public String getIsYearChanged() {
			return isYearChanged;
		}

		public void setIsYearChanged(String isYearChanged) {
			this.isYearChanged = isYearChanged;
		}

		public String getIsDsnChanged() {
			return isDsnChanged;
		}

		public void setIsDsnChanged(String isDsnChanged) {
			this.isDsnChanged = isDsnChanged;
		}

		public String getIsRsnChanged() {
			return isRsnChanged;
		}

		public void setIsRsnChanged(String isRsnChanged) {
			this.isRsnChanged = isRsnChanged;
		}

		public String getIsHniChanged() {
			return isHniChanged;
		}

		public void setIsHniChanged(String isHniChanged) {
			this.isHniChanged = isHniChanged;
		}

		public String getIsRiChanged() {
			return isRiChanged;
		}

		public void setIsRiChanged(String isRiChanged) {
			this.isRiChanged = isRiChanged;
		}

		public String getIsWeightChanged() {
			return isWeightChanged;
		}

		public void setIsWeightChanged(String isWeightChanged) {
			this.isWeightChanged = isWeightChanged;
		}

		public String getIsMailTagChanged() {
			return isMailTagChanged;
		}

		public void setIsMailTagChanged(String isMailTagChanged) {
			this.isMailTagChanged = isMailTagChanged;
		}

		public String getIsTransferCarrierChanged() {
			return isTransferCarrierChanged;
		}

		public void setIsTransferCarrierChanged(String isTransferCarrierChanged) {
			this.isTransferCarrierChanged = isTransferCarrierChanged;
		}



		private String isPolChanged;
		  private String isOOEChanged;
		  private String isDOEChanged;
		  private String isCategoryChanged;
		  private String isSubClassChanged;
		  private String isYearChanged;
		  private String isDsnChanged;
		  private String isRsnChanged;
		  private String isHniChanged;
		  private String isRiChanged;
		  private String isWeightChanged;
		  private String isMailTagChanged;
		 
		private String isTransferCarrierChanged;
		 
		  
			 


	 public String getDisplayPage() {
			return displayPage;
		}

		public void setDisplayPage(String displayPage) {
			this.displayPage = displayPage;
		}

		public String getLastPageNum() {
			return lastPageNum;
		}

		public void setLastPageNum(String lastPageNum) {
			this.lastPageNum = lastPageNum;
		}

		public String getDisplayPopupPage() {
			return displayPopupPage;
		}

		public void setDisplayPopupPage(String displayPopupPage) {
			this.displayPopupPage = displayPopupPage;
		}

		public String getLastPopupPageNum() {
			return lastPopupPageNum;
		}

		public void setLastPopupPageNum(String lastPopupPageNum) {
			this.lastPopupPageNum = lastPopupPageNum;
		}

		public String getTotalViewRecords() {
			return totalViewRecords;
		}

		public void setTotalViewRecords(String totalViewRecords) {
			this.totalViewRecords = totalViewRecords;
		}

	 public String getTransactionName() {
			return transactionName;
		}

		public void setTransactionName(String transactionName) {
			this.transactionName = transactionName;
		}

	 /**
		 * @return Returns the ScreenId.
		 */
		public String getScreenId() {
			return SCREEN_ID;
		}

		/**
		 * @return Returns the Product.
		 */
		public String getProduct() {
			return PRODUCT;
		}

		/**
		 * @return Returns the SubProduct.
		 */
		public String getSubProduct() {
			return SUBPRODUCT;
		}

		/**
		 * @return Returns the bundle.
		 */
		public String getBundle() {
			return BUNDLE;
		}


	 /**
		 * @return the mailBag
		 */
		public String getMailBag() {
			return mailBag;
		}
		/**
		 * @param mailBag the mailBag to set
		 */
		public void setMailBag(String mailBag) {
			this.mailBag = mailBag;
		}
		/**
		 * @return the transactionType
		 */
		public String getTransactionType() {
			return transactionType;
		}
		/**
		 * @param transactionType the transactionType to set
		 */
		public void setTransactionType(String transactionType) {
			this.transactionType = transactionType;
		}
		/**
		 * @return the checkbox
		 */
		public String getCheckbox() {
			return checkbox;
		}
		/**
		 * @param checkbox the checkbox to set
		 */
		public void setCheckbox(String checkbox) {
			this.checkbox = checkbox;
		}
		public String getCurrentDialogOption() {
			return currentDialogOption;
		}

		public void setCurrentDialogOption(String currentDialogOption) {
			this.currentDialogOption = currentDialogOption;
		}

		public String getCurrentDialogId() {
			return currentDialogId;
		}

		public void setCurrentDialogId(String currentDialogId) {
			this.currentDialogId = currentDialogId;
		}

		/**
		 * @return the container
		 */
		public String getContainer() {
			return container;
		}
		/**
		 * @param container the container to set
		 */
		public void setContainer(String container) {
			this.container = container;
		}
		/**
		 * @return the remarks
		 */
		public String getRemarks() {
			return remarks;
		}
		/**
		 * @param remarks the remarks to set
		 */
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		/**
		 * @return the flightNumber
		 */
		public String getFlightNumber() {
			return flightNumber;
		}
		/**
		 * @param flightNumber the flightNumber to set
		 */
		public void setFlightNumber(String flightNumber) {
			this.flightNumber = flightNumber;
		}
		/**
		 * @return the flightDate
		 */
		public String getFlightDate() {
			return flightDate;
		}
		/**
		 * @param flightDate the flightDate to set
		 */
		public void setFlightDate(String flightDate) {
			this.flightDate = flightDate;
		}
		/**
		 * @return the fromTime
		 */
		public String getFromTime() {
			return fromTime;
		}
		/**
		 * @param fromTime the fromTime to set
		 */
		public void setFromTime(String fromTime) {
			this.fromTime = fromTime;
		}
		/**
		 * @return the toTime
		 */
		public String getToTime() {
			return toTime;
		}
		/**
		 * @param toTime the toTime to set
		 */
		public void setToTime(String toTime) {
			this.toTime = toTime;
		}
		/**
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}
		/**
		 * @param status the status to set
		 */
		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * @return the closeStatus
		 */
		public String getCloseStatus() {
			return closeStatus;
		}
		/**
		 * @param closeStatus the closeStatus to set
		 */
		public void setCloseStatus(String closeStatus) {
			this.closeStatus = closeStatus;
		}


		public String getBulk() {
			return bulk;
		}

		public void setBulk(String bulk) {
			this.bulk = bulk;
		}

		public String getPou() {
			return pou;
		}

		public void setPou(String pou) {
			this.pou = pou;
		}

		public String getDestination() {
			return destination;
		}

		public void setDestination(String destination) {
			this.destination = destination;
		}

		public String getSubclass() {
			return subclass;
		}

		public void setSubclass(String subclass) {
			this.subclass = subclass;
		}

		public String getDsn() {
			return dsn;
		}

		public void setDsn(String dsn) {
			this.dsn = dsn;
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

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getRsn() {
			return rsn;
		}

		public void setRsn(String rsn) {
			this.rsn = rsn;
		}

		public String getYear() {
			return year;
		}

		public void setYear(String year) {
			this.year = year;
		}

		public String getHni() {
			return hni;
		}

		public void setHni(String hni) {
			this.hni = hni;
		}

		public String getRi() {
			return ri;
		}

		public void setRi(String ri) {
			this.ri = ri;
		}

		public String getWeight() {
			return weight;
		}

		public void setWeight(String weight) {
			this.weight = weight;
		}

		public void setSelectedtxnid(String selectedtxnid) {
			this.selectedtxnid = selectedtxnid;
		}

		public String getSelectedtxnid() {
			return selectedtxnid;
		}

		public void setFlightCarrierCode(String flightCarrierCode) {
			this.flightCarrierCode = flightCarrierCode;
		}

		public String getFlightCarrierCode() {
			return flightCarrierCode;
		}

		public void setFunctionType(String functionType) {
			this.functionType = functionType;
		}

		public String getFunctionType() {
			return functionType;
		}
		public String getSelectedIndex() {
			return selectedIndex;
		}
		public void setSelectedIndex(String selectedIndex) {
			this.selectedIndex = selectedIndex;
		}

	public String getMailCompanyCode() {
		return mailCompanyCode;
	}

	public void setMailCompanyCode(String mailCompanyCode) {
		this.mailCompanyCode = mailCompanyCode;
	}
	/**
	 * @return the barrowCheck
	 */
	public boolean isBarrowCheck() {
		return barrowCheck;
	}
	/**
	 * @param barrowCheck the barrowCheck to set
	 */
	public void setBarrowCheck(boolean barrowCheck) {
		this.barrowCheck = barrowCheck;
	}
//Added by A-5945 for ICRD-113473 starts
	/**
	 * @return the transferCarrier
	 */
	public String getTransferCarrier() {
		return transferCarrier;
	}
	/**
	 * @param transferCarrier the transferCarrier to set
	 */
	public void setTransferCarrier(String transferCarrier) {
		this.transferCarrier = transferCarrier;
	}
	//Added by A-5945 for ICRD-113473 ends

	public String getPol() {
		return pol;
	}
	public void setPol(String pol) {
		this.pol = pol;
	}

	/**
	 * 	Getter for orgin 
	 *	Added by : A-7531 on 01-Nov-2018
	 * 	Used for :
	 */
	public String getOrgin() {
		return orgin;
	}

	/**
	 *  @param orgin the orgin to set
	 * 	Setter for orgin 
	 *	Added by : A-7531 on 01-Nov-2018
	 * 	Used for :
	 */
	public void setOrgin(String orgin) {
		this.orgin = orgin;
	}

	/**
	 * 	Getter for destn 
	 *	Added by : A-7531 on 01-Nov-2018
	 * 	Used for :
	 */
	public String getDestn() {
		return destn;
	}

	/**
	 *  @param destn the destn to set
	 * 	Setter for destn 
	 *	Added by : A-7531 on 01-Nov-2018
	 * 	Used for :
	 */
	public void setDestn(String destn) {
		this.destn = destn;
	}

	/**
	 * 	Getter for gpaCode 
	 *	Added by : A-7531 on 01-Nov-2018
	 * 	Used for :
	 */
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 *  @param gpaCode the gpaCode to set
	 * 	Setter for gpaCode 
	 *	Added by : A-7531 on 01-Nov-2018
	 * 	Used for :
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
}
