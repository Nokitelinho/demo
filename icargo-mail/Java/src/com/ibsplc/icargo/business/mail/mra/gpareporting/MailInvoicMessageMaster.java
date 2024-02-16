package com.ibsplc.icargo.business.mail.mra.gpareporting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.MailInvoicMessageDetailVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.MailInvoicMessageVO;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.MRAGPAReportingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 *
 * @author A-7371
 *
 */
@Entity
@Staleable
@Table(name = "MALMRAGPAINCMSGMST")
public class MailInvoicMessageMaster {


		private static  Log log = LogFactory.getLogger("MRA GPAREPORTING MailGPAInvoicMaster");

		private MailInvoicMessageMasterPK invoicMessageMasterPK;

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

		private String lastUpdatedUser;

		private Calendar lastUpdatedTime;

		private String remark;

		private String processStatus;

		private String fileName;

		private int splitCount;

		private int totalsplitCount;

		private String assocAssignCode;

		private String contractNumber;

		private double totalAdjustmentAmount;


		@Column(name="CTRNUM")
		public String getContractNumber() {
			return contractNumber;
		}
		public void setContractNumber(String contractNumber) {
			this.contractNumber = contractNumber;
		}
		@Column(name="ASCASGCOD")
		public String getAssocAssignCode() {
			return assocAssignCode;
		}

		public void setAssocAssignCode(String assocAssignCode) {
			this.assocAssignCode = assocAssignCode;
		}

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

		/**
		 * @return the processStatus
		 */
		@Column(name = "PRCSTA")
		public String getProcessStatus() {
			return processStatus;
		}

		/**
		 * @param processStatus the processStatus to set
		 */
		public void setProcessStatus(String processStatus) {
			this.processStatus = processStatus;
		}
		@Column(name = "SPLCNT")
		public int getSplitCount() {
			return splitCount;
		}

		public void setSplitCount(int splitCount) {
			this.splitCount = splitCount;
		}
		@Column(name = "TOTSPLCNT")
		public int getTotalsplitCount() {
			return totalsplitCount;
		}

		public void setTotalsplitCount(int totalsplitCount) {
			this.totalsplitCount = totalsplitCount;
		}


		@Column(name="TOTADJAMT")
		public double getTotalAdjustmentAmount() {
			return totalAdjustmentAmount;
		}
		public void setTotalAdjustmentAmount(double totalAdjustmentAmount) {
			this.totalAdjustmentAmount = totalAdjustmentAmount;
		}
		@EmbeddedId
		@AttributeOverrides( {
				@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
				@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM"))})
		 public MailInvoicMessageMasterPK getInvoicMessageMasterPK() {
			return invoicMessageMasterPK;
		}


		public void setInvoicMessageMasterPK(MailInvoicMessageMasterPK invoicMessageMasterPK) {
			this.invoicMessageMasterPK = invoicMessageMasterPK;
		}


		public MailInvoicMessageMaster(){

		}

		public MailInvoicMessageMaster(MailInvoicMessageVO mailInvoicMessage)
				throws SystemException {
			log.entering("MailInvoicMessageMaster", "MailInvoicMessageMaster");

			populatePK(mailInvoicMessage);
			populateAttributes(mailInvoicMessage);

			 try {
	            PersistenceController.getEntityManager().persist(this);
	        } catch(CreateException createException) {
	            throw new SystemException(createException.getMessage(),
	                    createException);
	        }
			 if(mailInvoicMessage.getMailInvoiceMessageDetailVOs()!=null
					 && mailInvoicMessage.getMailInvoiceMessageDetailVOs().size()>0
					 )
			 populateChild(getInvoicMessageMasterPK(),mailInvoicMessage.getMailInvoiceMessageDetailVOs());

			 log.exiting("MailInvoicMessageMaster", "MailInvoicMessageMaster");
			}




		private void populateChild(MailInvoicMessageMasterPK invoicMessageMasterPK,
				Collection<MailInvoicMessageDetailVO> mailInvoiceMessageDetailVOs) throws SystemException {
			for(MailInvoicMessageDetailVO mailInvoicMessageDetailVO:mailInvoiceMessageDetailVOs){
				new MailGPAInvoicMessageDetail(invoicMessageMasterPK,mailInvoicMessageDetailVO);
			}

		}


		private void populatePK(MailInvoicMessageVO mailInvoicMessage)
				throws SystemException {
			log.entering("MailGPAInvoicMaster", "populatePK");
			invoicMessageMasterPK= new MailInvoicMessageMasterPK();
			invoicMessageMasterPK.setCompanyCode(mailInvoicMessage.getCompanyCode());
			this.setInvoicMessageMasterPK(invoicMessageMasterPK);

			log.exiting("InvoicMessageMaster", "populatePK");
		}



		private void populateAttributes(MailInvoicMessageVO mailInvoicMessage) {

			log.entering("InvoicMessageMaster", "--populateAttributes---");

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
			if(mailInvoicMessage.getTotalAdjustmentAmount()!=null){
				this.setTotalAdjustmentAmount(mailInvoicMessage.getTotalAdjustmentAmount().getAmount());
			}
			this.setLastUpdatedUser(mailInvoicMessage.getLastUpdatedUser());
			this.setLastUpdatedTime(mailInvoicMessage.getLastUpdatedTime());
			this.setRemark(mailInvoicMessage.getRemark());
			this.setFileName(mailInvoicMessage.getFileName());
	        this.setSplitCount(mailInvoicMessage.getSplitCount());
	        this.setTotalsplitCount(mailInvoicMessage.getTotalsplitCount());
	        this.setAssocAssignCode(mailInvoicMessage.getAssocAssignCode());
	        this.setContractNumber(mailInvoicMessage.getContractNumber());
			log.exiting("InvoicMessageMaster", "populateAttributes");
		}


		public static MailGPAInvoicMaster find(MailGPAInvoicMasterPK mailGPAInvoicMasterPK)
		      throws FinderException, SystemException {
		      return PersistenceController.getEntityManager().find(MailGPAInvoicMaster.class, mailGPAInvoicMasterPK);
		  }

		public static MRAGPAReportingDAO constructDAO() throws SystemException {
			try {
				return MRAGPAReportingDAO.class.cast(PersistenceController
						.getEntityManager().getQueryDAO(
								"mail.mra.gpareporting"));
			} catch (PersistenceException e) {
				throw new SystemException(e.getErrorCode(), e);
			}
		}


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
		 * @author A-7371
		 * @param mailInvoicMessageVO
		 * @return
		 * @throws SystemException
		 */
		 public static String checkAutoProcessing(Collection<MailInvoicMessageVO> mailInvoicMessage) throws SystemException{
		    	return constructDAO().checkAutoProcessing(mailInvoicMessage);

		    }
		 /**
		  * @author A-7371
		  * @param mailInvoicMessage
		  * @throws SystemException
		  */
		 public static String  updateInvoicStatus(Collection<MailInvoicMessageVO> mailInvoicMessage) throws SystemException{
			 return constructDAO().updateInvoicStatus(mailInvoicMessage);
		 }
	/**
	 * A-8176
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static void updateInvoicReject(Collection<InvoicVO> invoicVOs) throws SystemException, PersistenceException {
		MailInvoicMessageMaster gpaInvoicMessageMaster = null;
		String levelOfProcessing = findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		String invoicStatus="";
		if (invoicVOs != null && !invoicVOs.isEmpty()) {
			MailInvoicMessageMasterPK  pk = null;
			if(levelOfProcessing != null && "F".equals(levelOfProcessing)){
				InvoicVO invoicVO = invoicVOs.iterator().next();
				invoicStatus = invoicVO.getInvoicStatusCode();
				List<Long> invoics = constructDAO().findInvoicsByFileName(invoicVO.getCompanyCode(),invoicVO.getFileName());
				if(invoics != null && !invoics.isEmpty()){
					for(Long sernum : invoics){
						pk = new MailInvoicMessageMasterPK();
						pk.setCompanyCode(invoicVO.getCompanyCode());
						pk.setSerialNumber(sernum);
						try{
							gpaInvoicMessageMaster = MailInvoicMessageMaster.find(pk);
							gpaInvoicMessageMaster.setProcessStatus("RJ");
							gpaInvoicMessageMaster.setRemark(invoicVO.getRemarks());
							PersistenceController.getEntityManager().flush();
				            PersistenceController.getEntityManager().clear();
						}catch(FinderException e){
							e.getErrorCode();
						}
					}
				}
				if("PR".equals(invoicStatus) || "PE".equals(invoicStatus)){
					try{
						invoicVO.setProcessingType("J");
						invoicVO.setInvoicRefId(" ");
		    		    invoicVO.setPayType(" ");
						String batchUpdStatus = new MailGPAInvoicMaster().updateBatchNumForInvoic(invoicVO);
						if("OK".equals(batchUpdStatus)){
							new MailGPAInvoicMaster().processInvoic(invoicVO);
						}
					}catch(SystemException e){
						log.log(Log.FINEST, "Exception  " , e);
					}
				}
			}
			else{
				for (InvoicVO invoicVO : invoicVOs) {
				try {
					String[] seq = null;
					if(invoicVO.getSerNums() != null){
						seq = invoicVO.getSerNums().split(",");
						for(String s : seq){
							long serNum = Long.parseLong(s);
							pk = new MailInvoicMessageMasterPK();
							pk.setCompanyCode(invoicVO.getCompanyCode());
							pk.setSerialNumber(serNum);
							gpaInvoicMessageMaster = MailInvoicMessageMaster.find(pk);
							gpaInvoicMessageMaster.setProcessStatus("RJ");
							PersistenceController.getEntityManager().flush();
				            PersistenceController.getEntityManager().clear();
						}

					}

				} catch (FinderException e) {
					e.getErrorCode();
				}
				}
			}
		}
	}
	/**
	 * A-8176
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private static MailInvoicMessageMasterPK constructGPAInvoicMessageMasterPK(InvoicVO invoicVO)
			throws SystemException {
		MailInvoicMessageMasterPK gpaInvoicMessageMasterPK = new MailInvoicMessageMasterPK();
		gpaInvoicMessageMasterPK.setCompanyCode(invoicVO.getCompanyCode());
		gpaInvoicMessageMasterPK.setSerialNumber(invoicVO.getSeqNumber());
		return gpaInvoicMessageMasterPK;
	}
	/**
	 * A-8176
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static MailInvoicMessageMaster find(MailInvoicMessageMasterPK gpaInvoicMessageMasterPK)
			throws FinderException, SystemException {
		return PersistenceController.getEntityManager().find(MailInvoicMessageMaster.class, gpaInvoicMessageMasterPK);
		 }


	/**
	 *
	 * 	Method		:	MailInvoicMessageMaster.isInitiatedInvoic
	 *	Added by 	:	A-5219 on 22-Apr-2020
	 * 	Used for 	:
	 *	Parameters	:	@param invoicVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	boolean
	 */
	public static boolean isInitiatedInvoic(InvoicVO invoicVO)
			throws SystemException {
		boolean isInitiatedInvoic = false;
		String[] seq = null;
		long serNum = 0;
		if(invoicVO.getSerNums() != null){
			seq = invoicVO.getSerNums().split(",");
			if(seq != null && seq.length > 0){
				serNum = Long.parseLong(seq[0]);
			}
		}
		if(serNum > 0){
			MailInvoicMessageMasterPK pk = new MailInvoicMessageMasterPK();
			pk.setCompanyCode(invoicVO.getCompanyCode());
			pk.setSerialNumber(serNum);
			try{
				MailInvoicMessageMaster inviocMaster = MailInvoicMessageMaster.find(pk);
			if(inviocMaster != null){
				isInitiatedInvoic = ("IN".equals(inviocMaster.getProcessStatus()) || "PR".equals(inviocMaster.getProcessStatus())
						|| "PE".equals(inviocMaster.getProcessStatus()))? true : false;
			}
			}catch(FinderException exception){
				isInitiatedInvoic = false;
			}
		}
		return isInitiatedInvoic;
	}


	/**
	 *
	 * 	Method		:	MailInvoicMessageMaster.findSystemParameterValue
	 *	Added by 	:	A-5219 on 17-Jun-2020
	 * 	Used for 	:
	 *	Parameters	:	@param syspar
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException
	 *	Return type	: 	String
	 */
	private static String findSystemParameterValue(String syspar)
			throws SystemException {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = null;
		try{
		 systemParameterMap = new SharedDefaultsProxy()
				.findSystemParameterByCodes(systemParameters);
		//log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		}catch(ProxyException ex){
			systemParameterMap = null;
		}catch(Exception ex){
			systemParameterMap = null;
		}
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}
}
