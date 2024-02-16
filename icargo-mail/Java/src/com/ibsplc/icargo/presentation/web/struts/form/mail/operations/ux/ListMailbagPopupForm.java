/*
 * ListMailbagPopupForm.java Created on Oct 22, 2018 by A-7929 
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-7929
 *
 */
public class ListMailbagPopupForm extends ScreenModel {


	private static final String SCREEN_ID = "mail.operations.ux.listmailbagpopup";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "listMailbagPopupResources";

	
	private String flightCarrierCode ;
    private String flightNumber;
    private String  flightDate;
    private String originAirportCode;  //A-7929 
	private String filterType;  //A-7929
	private String mailbagId;
    private String ooe;
    private String doe;
    private String mailCategoryCode;
	private String mailSubclass;
	private String year;
    private String despatchSerialNumber;
    private String receptacleSerialNumber;
    private String consignmentNumber;	
    private String fromDate;
    private String toDate;
    private String paCode;
    private String upliftAirport;
    private String uldNumber;
    private String destinationAirportCode;
    private String status;   //Accepted drop down
    

	private String[] selectedRows;
   	private String[] selectMail;
    
    //PAGINATION
   	private String displayPage;  //Cardit
    private int pageNumber;  //Cardit
    private String lastPageNum; //Cardit
    private String displayPageForLyingList;
    private int pageNumberForLyingList;
    private String lastPageNumForLylingList;
    private String paginationFlag;
   
    
	private String filterFlag;
	private String defaultPageSize = "10";
	private int totalRecords;
	//added by A-8215 for ICRD_241437 
	private String refreshParent; 
	private String okForScreenClose="";
	private String[] allCarditChecked;
	private String[] carditRowId;
	private String[] allLyingListChecked;
	private String[] lyingListRowId;	
	//added by A-8215 for ICRD_241437 ends
	 public String getPaginationFlag() {
			return paginationFlag;
		}

		public void setPaginationFlag(String paginationFlag) {
			this.paginationFlag = paginationFlag;
		}

		public String getFilterFlag() {
			return filterFlag;
		}

		public void setFilterFlag(String filterFlag) {
			this.filterFlag = filterFlag;
		}

	public String getLastPageNumForLylingList() {
		return lastPageNumForLylingList;
	}

	public void setLastPageNumForLylingList(String lastPageNumForLylingList) {
		this.lastPageNumForLylingList = lastPageNumForLylingList;
	}
	public String getDisplayPageForLyingList() {
		return displayPageForLyingList;
	}

	public void setDisplayPageForLyingList(String displayPageForLyingList) {
		this.displayPageForLyingList = displayPageForLyingList;
	}

	public int getPageNumberForLyingList() {
		return pageNumberForLyingList;
	}

	public void setPageNumberForLyingList(int pageNumberForLyingList) {
		this.pageNumberForLyingList = pageNumberForLyingList;
	}
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getLastPageNum() {
		return lastPageNum;
	}

	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	public String getDefaultPageSize() {
		return defaultPageSize;
	}

	public void setDefaultPageSize(String defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	private String[] checkBox;
	
	
	

	public String[] getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(String[] checkBox) {
		this.checkBox = checkBox;
	}

	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	public String getOriginAirportCode() {
			return originAirportCode;
		}

		public void setOriginAirportCode(String originAirportCode) {
			this.originAirportCode = originAirportCode;
		}

		public String getConsignmentNumber() {
			return consignmentNumber;
		}

		public void setConsignmentNumber(String consignmentNumber) {
			this.consignmentNumber = consignmentNumber;
		}

		public String getUpliftAirport() {
			return upliftAirport;
		}

		public void setUpliftAirport(String upliftAirport) {
			this.upliftAirport = upliftAirport;
		}

		public String getUldNumber() {
			return uldNumber;
		}

		public void setUldNumber(String uldNumber) {
			this.uldNumber = uldNumber;
		}

		public String getDestinationAirportCode() {
			return destinationAirportCode;
		}

		public void setDestinationAirportCode(String destinationAirportCode) {
			this.destinationAirportCode = destinationAirportCode;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

	
		public String getFilterType() {
			return filterType;
		}
	
		public void setFilterType(String filterType) {
			this.filterType = filterType;
		}
		public String getFlightCarrierCode() {
			return flightCarrierCode;
		}
	
		public void setFlightCarrierCode(String flightCarrierCode) {
			this.flightCarrierCode = flightCarrierCode;
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
	
	
		public String getMailbagId() {
			return mailbagId;
		}
	
		public void setMailbagId(String mailbagId) {
			this.mailbagId = mailbagId;
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
	
		public String[] getSelectedRows() {
			return selectedRows;
		}
	
		public void setSelectedRows(String[] selectedRows) {
			this.selectedRows = selectedRows;
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
	
		
	
		public String[] getSelectMail() {
			return selectMail;
		}
	
		public void setSelectMail(String[] selectMail) {
			this.selectMail = selectMail;
		}
	
		
		public String getPaCode() {
			return paCode;
		}
	
		public void setPaCode(String paCode) {
			this.paCode = paCode;
		}
	
		
	
		/**
	     * @return SCREEN_ID - String
	     */
	    public String getScreenId() {
	        return SCREEN_ID;
	    }
	
	    /**
	     * @return PRODUCT_NAME - String
	     */
	    public String getProduct() {
	        return PRODUCT_NAME;
	    }
	
	    /**
	     * @return SUBPRODUCT_NAME - String
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
		 * @return the refreshParent
		 */
		public String getRefreshParent() {
			return refreshParent;
		}

		/**
		 * @param refreshParent the refreshParent to set
		 */
		public void setRefreshParent(String refreshParent) {
			this.refreshParent = refreshParent;
		}

		/**
		 * @return the okForScreenClose
		 */
		public String getOkForScreenClose() {
			return okForScreenClose;
		}

		/**
		 * @param okForScreenClose the okForScreenClose to set
		 */
		public void setOkForScreenClose(String okForScreenClose) {
			this.okForScreenClose = okForScreenClose;
		}

		/**
		 * @return the allCarditChecked
		 */
		public String[] getAllCarditChecked() {
			return allCarditChecked;
		}

		/**
		 * @param allCarditChecked the allCarditChecked to set
		 */
		public void setAllCarditChecked(String[] allCarditChecked) {
			this.allCarditChecked = allCarditChecked;
		}

		/**
		 * @return the carditRowId
		 */
		public String[] getCarditRowId() {
			return carditRowId;
		}

		/**
		 * @param carditRowId the carditRowId to set
		 */
		public void setCarditRowId(String[] carditRowId) {
			this.carditRowId = carditRowId;
		}

		/**
		 * @return the allLyingListChecked
		 */
		public String[] getAllLyingListChecked() {
			return allLyingListChecked;
		}

		/**
		 * @param allLyingListChecked the allLyingListChecked to set
		 */
		public void setAllLyingListChecked(String[] allLyingListChecked) {
			this.allLyingListChecked = allLyingListChecked;
		}

		/**
		 * @return the lyingListRowId
		 */
		public String[] getLyingListRowId() {
			return lyingListRowId;
		}

		/**
		 * @param lyingListRowId the lyingListRowId to set
		 */
		public void setLyingListRowId(String[] lyingListRowId) {
			this.lyingListRowId = lyingListRowId;
		}

		

	
	
	

	


	





}
