/*
 * StockAllocationVO.java Created on Sep 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestForOALVO;
/**
 * @author A-1366
 *
 */
public class StockAllocationVO extends AbstractVO {

	/**
	 * Constant for audit product name
	 */
	public static final String ALLOCATERANGE_AUDIT_PRODUCTNAME = "stockcontrol";

	/**
	 * Constant for audit module name
	 */
	public static final String ALLOCATERANGE_AUDIT_MODULENAME = "defaults";

	/**
	 * Constant for audit entity name
	 */
	public static final String ALLOCATERANGE_AUDIT_ENTITYNAME = "com.ibsplc.icargo.business.stockcontrol.defaults.Stock";

	/**
	 * Mode of transfer return
	 */
	public static final String MODE_RETURN = "R";

	/**
	 * Mode of transfer blacklist
	 */
	public static final String MODE_BLACKLIST = "B";

	/**
	 * Mode of transfer allocate
	 */
	public static final String MODE_ALLOCATE = "A";

	/**
	 * Mode of transfer create
	 */
	public static final String MODE_CREATE = "C";

	/**
	 * Mode of transfer used
	 */
	public static final String MODE_USED = "U";

	/**
	 * Mode of transfer unused
	 */
	public static final String MODE_UNUSED = "F";

	/**
	 * Mode of stock manual
	 */
	public static final String MODE_MANUAL = "M";

	/**
	 * Mode of stock neutral
	 */
	public static final String MODE_NEUTRAL = "N";


	/**
	 * mode of tranfer transfer
	 */
	public static final String MODE_TRANSFER = "T";

	public static final String MODE_BLACKLISTREVOKE = "BR";
	public static final String MODE_UTILIZED_RETURNED = "UR";
	/**
	 * mode of reopen
	 */
	public static final String MODE_REOPENED = "P";


	/**
	 * mode of deleted stock
	 */
	public static final String MODE_DELETED = "D";


	public static final String MODE_REVOKE = "V";

	/**
	 * Mode of tranfer normal
	 */
	public static final String MODE_NORMAL = "N";

	/**
	 * Mode of Executed
	 */
	public static final String MODE_EXECUTE = "E";

	/**
	 * Mode when moved to transit as part of allocation
	 */
	public static final String MODE_ALLOCATE_TRANSIT="L";
	/**
	 * Mode when moved to transit as part of return
	 */
	public static final String MODE_RETURN_TRANSIT="N";
	/**
	 * Mode when moved to transfer as part of return
	 */
	public static final String MODE_TRANSFER_TRANSIT="E";

	/**
	 * Added by A-2881 for ICRD-3082
	 */
	public static final String MODE_VOID="O";
	/**
	 * Added by A-2881 for ICRD-3082
	 */
	public static final String RECEIVED_ALLOCATION="G";
	/**
	 * Added by A-2881 for ICRD-3082
	 */
	public static final String RECEIVED_TRANSFER="H";
	/**
	 * Added by A-2881 for ICRD-3082
	 */
	public static final String RECEIVED_RETURN="I";
	
	public static final String INVOICE ="INVOICE";


	/**
	 * Range length
	 */
	public static final int RANGELENGTH = 7;

	/**
	 * Company Code
	 */
	private String companyCode;

	/**
	 * Stock Holder Code
	 */
	private String stockHolderCode;

	private int airlineIdentifier;
	private String awbPrefix;
	private int noOfReqDocuments;

	private String airlineCode;

	/**
	 * Stock Control for (approver)
	 */
	private String stockControlFor;

	/**
	 * Document Number
	 */
	private String documentNumber;

	/**
	 * Document Type
	 */
	private String documentType;

	/**
	 * Document Sub Type
	 */
	private String documentSubType;

	/**
	 * new Stock Flag
	 */
	private boolean isNewStockFlag;

	/**
	 * is manual flag
	 */
	private boolean isManual;

	private boolean enableStockHistory;
	
	//added by Chippy as part of CR1878
	private GMTDate captureDateUTC;
	
	public GMTDate getCaptureDateUTC() {
		return captureDateUTC;
	}

	public void setCaptureDateUTC(GMTDate captureDateUTC) {
		this.captureDateUTC = captureDateUTC;
	}

	public boolean isEnableStockHistory() {
		return enableStockHistory;
	}

	public void setEnableStockHistory(boolean enableStockHistory) {
		this.enableStockHistory = enableStockHistory;
	}

	/**
	 * The validation of ranges(whether any awb is reserved within that range)
	 * is to be done using this flag.
	 */
	private boolean isConfirmationRequired;
	/**
	 * Flag for indicating call is from allocate stock screen
	 */
	private boolean isAllocate;
	/**
	 * Flag for indicating awb is executed
	 */
	private boolean isExecuted;
	/**
	 * Flag for indicating awb is returnedd
	 */
	private boolean isReturned;

	/**
	 * Flag for indicating awb is reopened
	 */
	private boolean isReopened;
	/**
	 * Flag for indicating confirm to return from allocateStock method
	 */
	private boolean isConfirm;

	/**
	 * Execution Date
	 */
	private LocalDate executionDate;

	/**
	 * last update time
	 */
	private LocalDate lastUpdateTime;

	/**
	 * last update time for stock request
	 */
	private LocalDate lastUpdateTimeForStockReq;
	/**
	 * last update user
	 */
	private String lastUpdateUser;

	/**
	 * remarks
	 */
	private String remarks;

	/**
	 * Stock Request reference number
	 */
	private String requestRefNumber;

	/**
	 * Quantity of allocated stock
	 */
	private long allocatedStock;

	/**
	 * Collection<RangeVO>
	 */
	private Collection<RangeVO> ranges;

	/**
	 * Stock request for other air lines
	 */
	private Collection<StockRequestForOALVO> stockForOtherAirlines;

	/**
	 * Specifies the transfer mode
	 */
	private String transferMode;

	/**
	 * specifies whether blacklist
	 */
	private boolean isBlacklist;

	/**
	 * specifies whether approver is deleted
	 */
	private boolean isApproverDeleted;

	private String operationFlag;

	/**
	 * to get the rejected documents which is set by the server as per
	 * the validation
	 */
	private Collection<String> rejectedDocuments;
	/**
	 * this is to get the number of already processed documents within a range
	 */
	private int numberOfProcessedDocs;

	//This is to get the stockList details for Validate againstthe re-order level

	private String airportCode;

	//to validate the re-order level

	private boolean hasMinReorderLevel;
	/**
	 * @return Returns the noOfReqDocuments.
	 */

	
	private boolean isAllocatedforCreate;

	private boolean isFromConfirmStock;
	
	//Added by A-2881 for ICRD-3082
	private String transactionCode;
	
	private double voidingCharge;
	
	private String currencyCode;
	
	private String autoAllocated;
	
	public StockAllocationVO() {

	}
	
	/**
	 * Constructor Method for copying
	 * @param stockAllocationVO
	 */
	public StockAllocationVO(StockAllocationVO stockAllocationVO) {
		
		Collection<RangeVO> copiedRanges=null;
		Collection<String>  copiedRejectedDocuments=null;
		Collection<StockRequestForOALVO> copiedStockForOtherAirlines=null;
		
		this.companyCode = stockAllocationVO.getCompanyCode();
		this.stockHolderCode = stockAllocationVO.getStockHolderCode();
        this.airlineIdentifier = stockAllocationVO.getAirlineIdentifier();
        this.awbPrefix=stockAllocationVO.getAwbPrefix();
		this.noOfReqDocuments = stockAllocationVO.getNoOfReqDocuments();
		this.airlineCode = stockAllocationVO.getAirlineCode();
		this.stockControlFor = stockAllocationVO.getStockControlFor();
		this.documentType = stockAllocationVO.getDocumentType();
		this.documentSubType = stockAllocationVO.getDocumentSubType();
		this.isNewStockFlag = stockAllocationVO.isNewStockFlag();
        this.isManual = stockAllocationVO.isManual();
        this.enableStockHistory = stockAllocationVO.isEnableStockHistory();
		this.captureDateUTC = stockAllocationVO.getCaptureDateUTC();
		this.isConfirmationRequired = stockAllocationVO
				.isConfirmationRequired();
        this.isAllocate = stockAllocationVO.isAllocate();

		this.isExecuted = stockAllocationVO.isExecuted();

		this.isReturned = stockAllocationVO.isReturned();

		this.isReopened = stockAllocationVO.isReopened();
		this.isConfirm = stockAllocationVO.isConfirm();
		this.executionDate = stockAllocationVO.getExecutionDate();
		this.lastUpdateTime = stockAllocationVO.getLastUpdateTime();
		this.lastUpdateTimeForStockReq = stockAllocationVO
				.getLastUpdateTimeForStockReq();
		this.lastUpdateUser = stockAllocationVO.getLastUpdateUser();
		this.remarks = stockAllocationVO.getRemarks();
		this.requestRefNumber = stockAllocationVO.getRequestRefNumber();
		this.allocatedStock = stockAllocationVO.getAllocatedStock();
		this.stockForOtherAirlines = stockAllocationVO
				.getStockForOtherAirlines();
		this.transferMode = stockAllocationVO.getTransferMode();
		this.isBlacklist = stockAllocationVO.isBlacklist();
		this.isApproverDeleted = stockAllocationVO.isApproverDeleted();
		this.operationFlag = stockAllocationVO.getOperationFlag();
		this.rejectedDocuments = stockAllocationVO.getRejectedDocuments();
		this.numberOfProcessedDocs = stockAllocationVO
				.getNumberOfProcessedDocs();
		this.airportCode = stockAllocationVO.getAirportCode();
		this.hasMinReorderLevel = stockAllocationVO.isHasMinReorderLevel();
		this.isAllocatedforCreate = stockAllocationVO.isAllocatedforCreate();
		this.isFromConfirmStock = stockAllocationVO.isFromConfirmStock();
	
		
		//Setting RangeVOs
		if(stockAllocationVO.getRanges()!=null && stockAllocationVO.getRanges().size()>0){
			if(copiedRanges==null) {
				copiedRanges=new ArrayList<RangeVO>();
			}
			for(RangeVO rangeVO:stockAllocationVO.getRanges()){
				RangeVO copyVO=new RangeVO(rangeVO);
				copiedRanges.add(copyVO);
				
			}
		}
		this.ranges=copiedRanges;
		
		//setting rejected documents
		if(stockAllocationVO.getRejectedDocuments()!=null){
			if(copiedRejectedDocuments==null) {
				copiedRejectedDocuments=new ArrayList<String>();
			}
			for(String str:stockAllocationVO.getRejectedDocuments()){
				copiedRejectedDocuments.add(str);
			}
		}
		this.rejectedDocuments=copiedRejectedDocuments;
		
        //Setting StockForOALVOs
		if (stockAllocationVO.getStockForOtherAirlines() != null
				&& stockAllocationVO.getStockForOtherAirlines().size() > 0) {
			if (copiedStockForOtherAirlines == null) {
				copiedStockForOtherAirlines = new ArrayList<StockRequestForOALVO>();
			}
			for (StockRequestForOALVO stockRequestOALVO : stockAllocationVO
					.getStockForOtherAirlines()) {
				StockRequestForOALVO copyVO = new StockRequestForOALVO(
						stockRequestOALVO);
				copiedStockForOtherAirlines.add(copyVO);
			}
		}
		this.stockForOtherAirlines=copiedStockForOtherAirlines;
		this.setTransactionCode(stockAllocationVO.getTransactionCode());
		this.setVoidingCharge(stockAllocationVO.getVoidingCharge());
		this.setCurrencyCode(stockAllocationVO.getCurrencyCode());
		this.autoAllocated =(stockAllocationVO.getAutoAllocated());

	}

	public String getAwbPrefix() {
		return awbPrefix;
	}

	public void setAwbPrefix(String awbPrefix) {
		this.awbPrefix = awbPrefix;
	}

	public boolean isAllocatedforCreate() {
		return isAllocatedforCreate;
	}

	public void setAllocatedforCreate(boolean isAllocatedforCreate) {
		this.isAllocatedforCreate = isAllocatedforCreate;
	}



	public int getNoOfReqDocuments() {
		return this.noOfReqDocuments;
	}

	/**
	 * @param noOfReqDocuments The noOfReqDocuments to set.
	 */
	public void setNoOfReqDocuments(int noOfReqDocuments) {
		this.noOfReqDocuments = noOfReqDocuments;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @author A-1885
	 * @return isApproverDeleted
	 */
	public boolean isApproverDeleted() {
		return isApproverDeleted;
	}

	/**
	 * @author A-1885
	 * @param isApproverDeleted
	 */
	public void setApproverDeleted(boolean isApproverDeleted) {
		this.isApproverDeleted = isApproverDeleted;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the documentSubType.
	 */
	public String getDocumentSubType() {
		return documentSubType;
	}

	/**
	 * @param documentSubType
	 *            The documentSubType to set.
	 */
	public void setDocumentSubType(String documentSubType) {
		this.documentSubType = documentSubType;
	}

	/**
	 * @return Returns the documentType.
	 */
	public String getDocumentType() {
		return documentType;
	}

	/**
	 * @param documentType
	 *            The documentType to set.
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	/**
	 * @return Returns the ranges.
	 */
	public Collection<RangeVO> getRanges() {
		return ranges;
	}

	/**
	 * @param ranges
	 *            The ranges to set.
	 */
	public void setRanges(Collection<RangeVO> ranges) {
		this.ranges = ranges;
	}

	/**
	 * @return Returns the stockForOtherAirlines.
	 */
	public Collection<StockRequestForOALVO> getStockForOtherAirlines() {
		return stockForOtherAirlines;
	}

	/**
	 * @param stockForOtherAirlines The stockForOtherAirlines to set.
	 */
	public void setStockForOtherAirlines(
			Collection<StockRequestForOALVO> stockForOtherAirlines) {
		this.stockForOtherAirlines = stockForOtherAirlines;
	}

	/**
	 * @return Returns the stockControlFor.
	 */
	public String getStockControlFor() {
		return stockControlFor;
	}

	/**
	 * @param stockControlFor
	 *            The stockControlFor to set.
	 */
	public void setStockControlFor(String stockControlFor) {
		this.stockControlFor = stockControlFor;
	}

	/**
	 * @return Returns the stockHolderCode.
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}

	/**
	 * @param stockHolderCode
	 *            The stockHolderCode to set.
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode
	 *            The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier
	 *            The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}

	/**
	 * Method for getting the isNewStockFlag
	 *
	 * @return isNewStockFlag
	 */
	public boolean isNewStockFlag() {
		return isNewStockFlag;
	}

	/**
	 * Method for setting newStockFlag
	 *
	 * @param isNewStockFlag
	 */
	public void setNewStockFlag(boolean isNewStockFlag) {
		this.isNewStockFlag = isNewStockFlag;
	}

	/**
	 * Method for getting last update time
	 *
	 * @return last update time
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * Method for setting last update time
	 *
	 * @param lastUpdateTime
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}


	/**
	 * @return Returns the lastUpdateTimeForStockReq.
	 */
	public LocalDate getLastUpdateTimeForStockReq() {
		return lastUpdateTimeForStockReq;
	}

	/**
	 * @param lastUpdateTimeForStockReq The lastUpdateTimeForStockReq to set.
	 */
	public void setLastUpdateTimeForStockReq(LocalDate lastUpdateTimeForStockReq) {
		this.lastUpdateTimeForStockReq = lastUpdateTimeForStockReq;
	}

	/**
	 * Method for getting last update user
	 *
	 * @return lastUpdateUser
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * Method for setting last update user
	 *
	 * @param lastUpdateUser
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * Method for getting is manual flag
	 *
	 * @return isManual
	 */
	public boolean isManual() {
		return isManual;
	}

	/**
	 * Method for setting isManual flag
	 *
	 * @param isManual
	 */
	public void setManual(boolean isManual) {
		this.isManual = isManual;
	}

	/**
	 * @return Returns the isConfirmationRequired.
	 */
	public boolean isConfirmationRequired() {
		return isConfirmationRequired;
	}

	/**
	 * @param isConfirmationRequired The isConfirmationRequired to set.
	 */
	public void setConfirmationRequired(boolean isConfirmationRequired) {
		this.isConfirmationRequired = isConfirmationRequired;
	}

	/**
	 * Method for getting is manual flag
	 *
	 * @return
	 */
	public boolean isAllocate() {
		return isAllocate;
	}

	/**
	 *
	 * @param isAllocate
	 */
	public void setAllocate(boolean isAllocate) {
		this.isAllocate = isAllocate;
	}

	/**
	 * Method for setting remarks
	 *
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Method for getting remarks
	 *
	 * @return remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @return Returns the requestRefNumber.
	 */
	public String getRequestRefNumber() {
		return requestRefNumber;
	}

	/**
	 * @param requestRefNumber
	 *            The requestRefNumber to set.
	 */
	public void setRequestRefNumber(String requestRefNumber) {
		this.requestRefNumber = requestRefNumber;
	}

	/**
	 * @return Returns the allocatedStock.
	 */
	public long getAllocatedStock() {
		return allocatedStock;
	}

	/**
	 * @param allocatedStock
	 *            The allocatedStock to set.
	 */
	public void setAllocatedStock(long allocatedStock) {
		this.allocatedStock = allocatedStock;
	}

	/**
	 * @return Returns the transferMode.
	 */
	public String getTransferMode() {
		return transferMode;
	}

	/**
	 * @param transferMode
	 *            The transferMode to set.
	 */
	public void setTransferMode(String transferMode) {
		this.transferMode = transferMode;
	}

	/**
	 * @return
	 */
	public boolean isBlacklist() {
		return isBlacklist;
	}

	/**
	 * @param isBlacklist
	 */
	public void setBlacklist(boolean isBlacklist) {
		this.isBlacklist = isBlacklist;
	}

	/**
	 * @return Returns the rejectedDocuments.
	 */
	public Collection<String> getRejectedDocuments() {
		return rejectedDocuments;
	}

	/**
	 * @param rejectedDocuments The rejectedDocuments to set.
	 */
	public void setRejectedDocuments(Collection<String> rejectedDocuments) {
		this.rejectedDocuments = rejectedDocuments;
	}

	/**
	 * @return Returns the numberOfProcessedDocs.
	 */
	public int getNumberOfProcessedDocs() {
		return numberOfProcessedDocs;
	}

	/**
	 * @param numberOfProcessedDocs The numberOfProcessedDocs to set.
	 */
	public void setNumberOfProcessedDocs(int numberOfProcessedDocs) {
		this.numberOfProcessedDocs = numberOfProcessedDocs;
	}

	/**
	 * @return Returns the airportCode.
	 */
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * @return Returns the hasMinReorderLevel.
	 */
	public boolean isHasMinReorderLevel() {
		return hasMinReorderLevel;
	}

	/**
	 * @param hasMinReorderLevel The hasMinReorderLevel to set.
	 */
	public void setHasMinReorderLevel(boolean hasMinReorderLevel) {
		this.hasMinReorderLevel = hasMinReorderLevel;
	}

	public boolean isExecuted() {
		return isExecuted;
	}

	public void setExecuted(boolean isExecuted) {
		this.isExecuted = isExecuted;
	}

	public boolean isReturned() {
		return isReturned;
	}

	public void setReturned(boolean isReturned) {
		this.isReturned = isReturned;
	}

	public boolean isConfirm() {
		return isConfirm;
	}

	public void setConfirm(boolean isConfirm) {
		this.isConfirm = isConfirm;
	}

	public LocalDate getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(LocalDate executionDate) {
		this.executionDate = executionDate;
	}

	public boolean isReopened() {
		return isReopened;
	}

	public void setReopened(boolean isReopened) {
		this.isReopened = isReopened;
	}

	/**
	 * @return the isFromConfirmStock
	 */
	public boolean isFromConfirmStock() {
		return isFromConfirmStock;
	}

	/**
	 * @param isFromConfirmStock the isFromConfirmStock to set
	 */
	public void setFromConfirmStock(boolean isFromConfirmStock) {
		this.isFromConfirmStock = isFromConfirmStock;
	}

	/**
	 * @return the transactionCode
	 */
	public String getTransactionCode() {
		return transactionCode;
	}

	/**
	 * @param transactionCode the transactionCode to set
	 */
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the voidingCharge
	 */
	public double getVoidingCharge() {
		return voidingCharge;
	}

	/**
	 * @param voidingCharge the voidingCharge to set
	 */
	public void setVoidingCharge(double voidingCharge) {
		this.voidingCharge = voidingCharge;
	}

	/**
	 * @return the documentNumber
	 */
	public String getDocumentNumber() {
		return documentNumber;
	}

	/**
	 * @param documentNumber the documentNumber to set
	 */
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	
	/**
	 * @return the autoAllocated
	 */
	public String getAutoAllocated() {
		return autoAllocated;
	}
	
	/**
	 * @param autoAllocated the autoAllocated to set
	 */

	public void setAutoAllocated(String autoAllocated) {
		this.autoAllocated = autoAllocated;
	}



}
