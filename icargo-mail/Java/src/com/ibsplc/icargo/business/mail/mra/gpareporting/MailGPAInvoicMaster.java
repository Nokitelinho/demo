package com.ibsplc.icargo.business.mail.mra.gpareporting;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO;
//import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
//import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicSummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.MailInvoicMessageVO;
//import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.MailGPAInvoicMasterVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.MRAGPAReportingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
//import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-8464,A-7371
 *
 */
@Entity
@Table(name = "MALMRAGPAINCMST")
public class MailGPAInvoicMaster {

	private  Log log = LogFactory.getLogger("MRA GPAREPORTING MailGPAInvoicMaster");

	private MailGPAInvoicMasterPK mailGPAInvoicMasterPK;


	//private int tagidx;

private String poaCode;

	private Calendar reportingPeriodFrom;

	private Calendar reportingPeriodTo;

	private String invoiceReferenceNumber;

	private int numberOfMailbags;

	private String messageReferenceNumber;

	private Calendar messageDate;

	private Calendar invoiceDate;

	private String payeeCode;

	private String payerCode;

	private String assignedCarrier;

	private String invoiceAdvieType;

	private double totalInvoiceAmount;

	private String invoiceStatus;

	private String lastUpdatedUser;

	private Calendar lastUpdatedTime;

	private String remark;

	private String fileName;


	@Column(name="POACOD")
	public String getPoaCode() {
		return poaCode;
	}

	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	@Column(name="RPTPRDFRM")
	public Calendar getReportingPeriodFrom() {
		return reportingPeriodFrom;
	}

	public void setReportingPeriodFrom(Calendar reportingPeriodFrom) {
		this.reportingPeriodFrom = reportingPeriodFrom;
	}
	@Column(name="RPTPRDTOO")
	public Calendar getReportingPeriodTo() {
		return reportingPeriodTo;
	}

	public void setReportingPeriodTo(Calendar reportingPeriodTo) {
		this.reportingPeriodTo = reportingPeriodTo;
	}
	@Column(name="INVREFNUM")
	public String getInvoiceReferenceNumber() {
		return invoiceReferenceNumber;
	}

	public void setInvoiceReferenceNumber(String invoiceReferenceNumber) {
		this.invoiceReferenceNumber = invoiceReferenceNumber;
	}
	@Column(name="NUMMALBAG")
	public int getNumberOfMailbags() {
		return numberOfMailbags;
	}

	public void setNumberOfMailbags(int numberOfMailbags) {
		this.numberOfMailbags = numberOfMailbags;
	}
	@Column(name="INVMSGREFNUM")
	public String getMessageReferenceNumber() {
		return messageReferenceNumber;
	}

	public void setMessageReferenceNumber(String messageReferenceNumber) {
		this.messageReferenceNumber = messageReferenceNumber;
	}
	@Column(name="MSGDAT")
	public Calendar getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Calendar messageDate) {
		this.messageDate = messageDate;
	}
	@Column(name="INVDAT")
	public Calendar getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Calendar invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	@Column(name="PYECOD")
	public String getPayeeCode() {
		return payeeCode;
	}

	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}
	@Column(name="PYRCOD")
	public String getPayerCode() {
		return payerCode;
	}

	public void setPayerCode(String payerCode) {
		this.payerCode = payerCode;
	}
	@Column(name="ASDCAR")
	public String getAssignedCarrier() {
		return assignedCarrier;
	}

	public void setAssignedCarrier(String assignedCarrier) {
		this.assignedCarrier = assignedCarrier;
	}
	@Column(name="PAYTYP")
	public String getInvoiceAdvieType() {
		return invoiceAdvieType;
	}

	public void setInvoiceAdvieType(String invoiceAdvieType) {
		this.invoiceAdvieType = invoiceAdvieType;
	}
	@Column(name="TOTINVAMT")
	public double getTotalInvoiceAmount() {
		return totalInvoiceAmount;
	}

	public void setTotalInvoiceAmount(double totalInvoiceAmount) {
		this.totalInvoiceAmount = totalInvoiceAmount;
	}
	@Column(name="INVSTA")
	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	@Column(name="RMK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	 @Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}


	@Column(name = "FILNAM")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	//default constructor
	public MailGPAInvoicMaster(){

	}



	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM"))})
	 public MailGPAInvoicMasterPK getMailGPAInvoicMasterPK() {
		return mailGPAInvoicMasterPK;
	}

	public void setMailGPAInvoicMasterPK(MailGPAInvoicMasterPK mailGPAInvoicMasterPK) {
		this.mailGPAInvoicMasterPK = mailGPAInvoicMasterPK;
	}




	public MailGPAInvoicMaster(MailInvoicMessageVO mailInvoicMessage)
			throws SystemException {
		log.entering("MailGPAInvoicMaster", "MailGPAInvoicMaster");

		long serialNumber =findSerialNumberfromInvoic(mailInvoicMessage.getInvoiceReferenceNumber(), mailInvoicMessage.getCompanyCode());
		if(serialNumber!=0){
			mailGPAInvoicMasterPK= new MailGPAInvoicMasterPK();
			mailGPAInvoicMasterPK.setCompanyCode(mailInvoicMessage.getCompanyCode());
			mailGPAInvoicMasterPK.setSerialNumber(serialNumber);
			this.setMailGPAInvoicMasterPK(mailGPAInvoicMasterPK);
			//populateChild(getMailGPAInvoicMasterPK(),mailInvoicMessage.getMailInvoiceMessageDetailVOs());
		}else{
		populatePK(mailInvoicMessage);
		 populateAttributes(mailInvoicMessage);

		 try {
            PersistenceController.getEntityManager().persist(this);
        } catch(CreateException createException) {
            throw new SystemException(createException.getMessage(),
                    createException);
        }
		 /*if(mailInvoicMessage.getMailInvoiceMessageDetailVOs()!=null
				 && mailInvoicMessage.getMailInvoiceMessageDetailVOs().size()>0
				 )
		 populateChild(getMailGPAInvoicMasterPK(),mailInvoicMessage.getMailInvoiceMessageDetailVOs());*/

		}

		log.exiting("MailGPAInvoicMaster", "MailGPAInvoicMaster");
	}

	/*private void populateChild(MailGPAInvoicMasterPK mailGPAInvoicMasterPK,
			Collection<MailInvoicMessageDetailVO> mailInvoiceMessageDetailVOs) throws SystemException {
		for(MailInvoicMessageDetailVO mailInvoicMessageDetailVO:mailInvoiceMessageDetailVOs){
			new MailGPAInvoicMessageDetail(mailGPAInvoicMasterPK,mailInvoicMessageDetailVO);
		}

	}*/


	private void populatePK(MailInvoicMessageVO mailInvoicMessage)
			throws SystemException {
		log.entering("MailGPAInvoicMaster", "populatePK");
		mailGPAInvoicMasterPK= new MailGPAInvoicMasterPK();
		mailGPAInvoicMasterPK.setCompanyCode(mailInvoicMessage.getCompanyCode());
		this.setMailGPAInvoicMasterPK(mailGPAInvoicMasterPK);

		log.exiting("MailGPAInvoicMaster", "populatePK");
	}



	private void populateAttributes(MailInvoicMessageVO mailInvoicMessage) {

		log.entering("MailGPAInvoicMaster", "--populateAttributes---");

		this.setPoaCode(mailInvoicMessage.getPoaCode());
		this.setReportingPeriodFrom(mailInvoicMessage.getReportingPeriodFrom());
		this.setReportingPeriodTo(mailInvoicMessage.getReportingPeriodTo());
		this.setInvoiceReferenceNumber(mailInvoicMessage.getInvoiceReferenceNumber());
		this.setNumberOfMailbags(mailInvoicMessage.getNumberOfMailbags());
		this.setMessageReferenceNumber(mailInvoicMessage.getMessageReferenceNumber());
		this.setMessageDate(mailInvoicMessage.getMessageDate());
		this.setInvoiceDate(mailInvoicMessage.getInvoiceDate());
		this.setPayeeCode(mailInvoicMessage.getPayeeCode());
		this.setPayerCode(mailInvoicMessage.getPayerCode());
		this.setAssignedCarrier(mailInvoicMessage.getAssignedCarrier());
		this.setInvoiceAdvieType(mailInvoicMessage.getInvoiceAdvieType());
		if(mailInvoicMessage.getTotalInvoiceAmount()!=null){
		this.setTotalInvoiceAmount(mailInvoicMessage.getTotalInvoiceAmount().getAmount());
		}
		this.setInvoiceStatus(mailInvoicMessage.getInvoiceStatus());
		this.setLastUpdatedUser(mailInvoicMessage.getLastUpdatedUser());
		this.setLastUpdatedTime(mailInvoicMessage.getLastUpdatedTime());
		this.setRemark(mailInvoicMessage.getRemark());
		this.setFileName(mailInvoicMessage.getFileName());
		log.exiting("MailGPAInvoicMaster", "populateAttributes");
	}


	public static MailGPAInvoicMaster find(MailGPAInvoicMasterPK mailGPAInvoicMasterPK)
	      throws FinderException, SystemException {
	      return PersistenceController.getEntityManager().find(MailGPAInvoicMaster.class, mailGPAInvoicMasterPK);
	  }

	private static MRAGPAReportingDAO constructDAO() throws SystemException {
		try {
			return MRAGPAReportingDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO(
							"mail.mra.gpareporting"));
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	// Commenting as part of ICRD-319850
/*	public static Page<InvoicSummaryVO> findInvoicLovDetails(
			InvoicFilterVO invoicFilterVO) throws SystemException {
		try {
			return constructDAO().findInvoicLov(invoicFilterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}*/


	public void update(MailInvoicMessageVO mailInvoicMessage)
			throws SystemException {
		log.entering("MailGPAInvoicMaster", "update");
		populateAttributes(mailInvoicMessage);
		log.exiting("MailGPAInvoicMaster", "update");
	}

	  public static long findSerialNumberfromInvoic(String invoiceNumber,String companyCode) throws SystemException{
	    	return constructDAO().findSerialNumberfromInvoic(invoiceNumber, companyCode);

	    }  
 
      /**
	 *
	 * @param invoicVO
	 * @return
	 * @throws SystemException
	 */
	public String processInvoic(InvoicVO invoicVO)throws SystemException {
		try{
			return constructDAO().processInvoic(invoicVO);
		}catch(PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 *
	 * @param invoicDetailsVOs
	 * @return
	 * @throws SystemException
	 */
	public String reprocessInvoicMails(Collection<InvoicDetailsVO> invoicDetailsVOs)throws SystemException {
		try{
			return constructDAO().reprocessInvoicMails(invoicDetailsVOs);
		}catch(PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}


	/**
	 *
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public int checkForProcessCount(String companyCode) throws SystemException {
		try{
			return constructDAO().checkForProcessCount(companyCode);
		}catch(PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
	
	/**
	 * 
	 * @param invoicVO
	 * @return
	 * @throws SystemException
	 */
	public String updateBatchNumForInvoic(InvoicVO invoicVO)throws SystemException {
		try{
			return constructDAO().updateBatchNumForInvoic(invoicVO);
		}catch(PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
	public long findBatchNo(InvoicVO invoicVO)throws SystemException {
		try{
			return constructDAO().findBatchNo(invoicVO);
		}catch(PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	} 
	public String processInvoicFileFromJob(InvoicVO invoicVO) throws SystemException {
		try{
			return constructDAO().processInvoicFileFromJob(invoicVO);
		}catch(PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
	public String updateInvoicProcessingStatusFromJob(String companyCode) throws SystemException {
		try{
			return constructDAO().updateInvoicProcessingStatusFromJob(companyCode);
		}catch(PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
}
