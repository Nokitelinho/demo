package com.ibsplc.icargo.business.mail.mra.gpareporting.vo;


import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
/**
 * @author A-8527
 *
 */
public class InvoicVO extends AbstractVO{
	
    private String invoicRefId;
    private Double totalamount;
    private String invoicStatus;
    private String invoicStatusCode;
    private String remarks;
    private String lastupdatedUser;
    private String companyCode;     
    private String poaCode; 
	private LocalDate fromDate;
	private LocalDate toDate;
	private LocalDate invoiceDate;
	private long SeqNumber;
	private String txnCode;
	private int txnSerialNum;
	private String fileName;
	private String processingType;
	private boolean noDbJobRequired;
	private String payType;
	private String serNums;
	private long numOfMailbags;
	private long startBatchnum;
	private long endBatchnum;
	private String actionCode;
	private int jobIdx;
	
	
	public int getJobIdx() {
		return jobIdx;
	}
	public void setJobIdx(int jobIdx) {
		this.jobIdx = jobIdx;
	}
	public String getInvoicRefId() {
		return invoicRefId;
	}
	public void setInvoicRefId(String invoicRefId) {
		this.invoicRefId = invoicRefId;
	}
	public Double getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(Double totalamount) {
		this.totalamount = totalamount;
	}
	public String getInvoicStatus() {
		return invoicStatus;
	}
	public void setInvoicStatus(String invoicStatus) {
		this.invoicStatus = invoicStatus;
	}
	public String getInvoicStatusCode() {
		return invoicStatusCode;
	}
	public void setInvoicStatusCode(String invoicStatusCode) {
		this.invoicStatusCode = invoicStatusCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getPoaCode() {
		return poaCode;
	}
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getLastupdatedUser() {
		return lastupdatedUser;
	}
	public void setLastupdatedUser(String lastupdatedUser) {
		this.lastupdatedUser = lastupdatedUser;
	}
	public long getSeqNumber() {
		return SeqNumber;
	}
	public void setSeqNumber(long seqNumber) {
		SeqNumber = seqNumber;
	}
	/**
	 * @return the txnCode
	 */
	public String getTxnCode() {
		return txnCode;
	}
	/**
	 * @param txnCode the txnCode to set
	 */
	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}
	/**
	 * @return the txnSerialNum
	 */
	public int getTxnSerialNum() {
		return txnSerialNum;
	}
	/**
	 * @param txnSerialNum the txnSerialNum to set
	 */
	public void setTxnSerialNum(int txnSerialNum) {
		this.txnSerialNum = txnSerialNum;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFileName(){
		return fileName;
	}
	
	/**
	 * 
	 * @param fileName
	 */
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	/**
	 * @return the processingType
	 */
	public String getProcessingType() {
		return processingType;
	}
	/**
	 * @param processingType the processingType to set
	 */
	public void setProcessingType(String processingType) {
		this.processingType = processingType;
	}
	
	/**
	 * @return the noDbJobRequired
	 */
	public boolean isNoDbJobRequired() {
		return noDbJobRequired;
	}
	/**
	 * @param noDbJobRequired the noDbJobRequired to set
	 */
	public void setNoDbJobRequired(boolean noDbJobRequired) {
		this.noDbJobRequired = noDbJobRequired;
	}
	/**
	 * @return the payType
	 */
	public String getPayType() {
		return payType;
	}
	/**
	 * @param payType the payType to set
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}
	/**
	 * 	Getter for serNums 
	 *	Added by : A-5219 on 23-Apr-2020
	 * 	Used for :
	 */
	public String getSerNums() {
		return serNums;
	}
	/**
	 *  @param serNums the serNums to set
	 * 	Setter for serNums 
	 *	Added by : A-5219 on 23-Apr-2020
	 * 	Used for :
	 */
	public void setSerNums(String serNums) {
		this.serNums = serNums;
	}
	/**
	 * 	Getter for numOfMailbags 
	 *	Added by : A-5219 on 23-Apr-2020
	 * 	Used for :
	 */
	public long getNumOfMailbags() {
		return numOfMailbags;
	}
	/**
	 *  @param numOfMailbags the numOfMailbags to set
	 * 	Setter for numOfMailbags 
	 *	Added by : A-5219 on 23-Apr-2020
	 * 	Used for :
	 */
	public void setNumOfMailbags(long numOfMailbags) {
		this.numOfMailbags = numOfMailbags;
	}
	public long getStartBatchnum() {
		return startBatchnum;
	}
	public void setStartBatchnum(long startBatchnum) {
		this.startBatchnum = startBatchnum;
	}
	public long getEndBatchnum() {
		return endBatchnum;
	}
	public void setEndBatchnum(long endBatchnum) {
		this.endBatchnum = endBatchnum;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	
    
}
