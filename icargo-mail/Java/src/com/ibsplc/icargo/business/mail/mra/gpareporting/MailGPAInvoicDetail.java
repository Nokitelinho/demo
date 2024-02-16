/*
 * MailGPAInvoicDetail.java Created on Nov 20, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

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

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.MailGPAInvoicDetailVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.MRAGPAReportingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-8464
 *
 */
@Entity
@Table(name = "MALMRAGPAINCDTL")
public class MailGPAInvoicDetail {

	private static  Log log = LogFactory.getLogger("MRA GPAREPORTING MailInvoicDetail");

	private MailInvoicDetailPK mailInvoicDetailPK;

    private String mailIdr;
    private String invoicRefNum;
    private Calendar receivedDate;
    private String mailClass;
    private double weight;
    private double appliedRate;
    private double invoicAmount;
    //private double dueAmount;
    private double claimAmount;
    private double updClaimAmount;
    private String processStatus;
    private String invoicPaymentStatus;
    private String claimStatus;
    private String remark;
    private String lastUpdatedUser;
	private Calendar lastUpdatedTime;
	//private double previousSettlementAmount;Commented by Manish as part of IASCB-38452
	private double totalSettlementAmount;
	private String claimType;
	//ADDED poaCode AND versionNumber AS PART OF IASCB-38452 BY MANISH
	private String poaCode;
	private long versionNumber;
	private String claimRefNumber;

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "mailSequenceNumber", column = @Column(name = "MALSEQNUM")) })
	public MailInvoicDetailPK getMailInvoicDetailPK() {
		return mailInvoicDetailPK;
	}
	public void setMailInvoicDetailPK(MailInvoicDetailPK mailInvoicDetailPK) {
		this.mailInvoicDetailPK = mailInvoicDetailPK;
	}



	@Column(name = "MALIDR")
	public String getMailIdr() {
		return mailIdr;
	}
	public void setMailIdr(String mailIdr) {
		this.mailIdr = mailIdr;
	}

	@Column(name = "INVREFNUM")
	public String getInvoicRefNum() {
		return invoicRefNum;
	}
	public void setInvoicRefNum(String invoicRefNum) {
		this.invoicRefNum = invoicRefNum;
	}

	@Column(name = "RCVDAT")
	public Calendar getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Calendar receivedDate) {
		this.receivedDate = receivedDate;
	}

	@Column(name = "MALCLS")
	public String getMailClass() {
		return mailClass;
	}
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	@Column(name = "GRSWGT")
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Column(name = "APLRAT")
	public double getAppliedRate() {
		return appliedRate;
	}
	public void setAppliedRate(double appliedRate) {
		this.appliedRate = appliedRate;
	}

	@Column(name = "INVAMT")
	public double getInvoicAmount() {
		return invoicAmount;
	}
	public void setInvoicAmount(double invoicAmount) {
		this.invoicAmount = invoicAmount;
	}
	/*
	@Column(name = "DUEAMT")
	public double getDueAmount() {
		return dueAmount;
	}
	public void setDueAmount(double dueAmount) {
		this.dueAmount = dueAmount;
	}
	*/
	@Column(name = "CLMAMT")
	public double getClaimAmount() {
		return claimAmount;
	}
	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	@Column(name = "PROSTA")
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	@Column(name = "INVPAYSTA")
	public String getInvoicPaymentStatus() {
		return invoicPaymentStatus;
	}
	public void setInvoicPaymentStatus(String invoicPaymentStatus) {
		this.invoicPaymentStatus = invoicPaymentStatus;
	}

	@Column(name = "CLMSTA")
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	@Column(name = "RMK")
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



	/**
	 * @return the actualClaimAmount
	 */
	@Column(name = "UPDCLMAMT")
	public double getUpdClaimAmount() {
		return updClaimAmount;
	}
	/**
	 * @param actualClaimAmount the actualClaimAmount to set
	 */
	public void setUpdClaimAmount(double updClaimAmount) {
		this.updClaimAmount = updClaimAmount;
	}

	/**
	 *
	 * @return
	 */
	/*Commented by Manish as part of IASCB-38452
	 * @Column(name = "PRVSTLAMT")
	public double getPreviousSettlementAmount(){
		return previousSettlementAmount;
	}*/

	/**
	 *
	 * @param settlementAmount
	 */
	/*public void setPreviousSettlementAmount(double previousSettlementAmount){
		this.previousSettlementAmount = previousSettlementAmount;
	}*/


	/**
	 *
	 * @return
	 */
	@Column(name = "TOTSTLAMT")
	public double getTotalSettlementAmount(){
		return totalSettlementAmount;
	}

	/**
	 *
	 * @param settlementAmount
	 */
	public void setTotalSettlementAmount(double totalSettlementAmount){
		this.totalSettlementAmount = totalSettlementAmount;
	}

	@Column(name = "CLMTYP")
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	/**
	 *
	 * 	Method		:	MailGPAInvoicDetail.setPoaCode
	 *	Added by 	:	A-5219 on 13-Feb-2020
	 * 	Used for 	:
	 *	Parameters	:	@param poaCode
	 *	Return type	: 	void
	 */
	public void setPoaCode(java.lang.String poaCode) {
		this.poaCode=poaCode;
	}
	/**
	 *
	 * 	Method		:	MailGPAInvoicDetail.getPoaCode
	 *	Added by 	:	A-5219 on 13-Feb-2020
	 * 	Used for 	:
	 *	Parameters	:	@return
	 *	Return type	: 	java.lang.String
	 */
	@Column(name =  "POACOD")
	public java.lang.String getPoaCode() {
		return this.poaCode;
	}
	/**
	 *
	 * 	Method		:	MailGPAInvoicDetail.setVersionNumber
	 *	Added by 	:	A-5219 on 13-Feb-2020
	 * 	Used for 	:
	 *	Parameters	:	@param versionNumber
	 *	Return type	: 	void
	 */
	public void setVersionNumber(long versionNumber) {
		this.versionNumber = versionNumber;
	}
	/**
	 *
	 * 	Method		:	MailGPAInvoicDetail.getVersionNumber
	 *	Added by 	:	A-5219 on 13-Feb-2020
	 * 	Used for 	:
	 *	Parameters	:	@return
	 *	Return type	: 	long
	 */
	@Column(name = "VERNUM")
	public long getVersionNumber() {
		return versionNumber;
	}
	public MailGPAInvoicDetail(){

	}

	public MailGPAInvoicDetail(MailGPAInvoicDetailVO mailGpaInvoicDetailsVO)
			throws SystemException {
		log.entering("MailInvoicDetail", "MailInvoicDetail");

		populatePK(mailGpaInvoicDetailsVO);
		populateAttributes(mailGpaInvoicDetailsVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode(),
					createException);
		}
		log.exiting("MailInvoicDetail", "MailInvoicDetail");
	}


	private void populatePK(MailGPAInvoicDetailVO mailGpaInvoicDetailsVO)
			throws SystemException {
		log.entering("MailInvoicDetail", "populatePK");
		this.setMailInvoicDetailPK(new MailInvoicDetailPK(
				mailGpaInvoicDetailsVO.getCompanyCode(),
				mailGpaInvoicDetailsVO.getMailSequenceNumber()));
		log.exiting("MailInvoicDetail", "populatePK");
	}

	public void update(MailGPAInvoicDetailVO mailGpaInvoicDetailsVO)
			throws SystemException {
		log.entering("MailInvoicDetail", "update");
		populateAttributes(mailGpaInvoicDetailsVO);
		log.exiting("MailInvoicDetail", "update");
	}

	private void populateAttributes(MailGPAInvoicDetailVO mailGpaInvoicDetailsVO) {

		log.entering("MailInvoicDetail", "--populateAttributes---");
		this.setAppliedRate(mailGpaInvoicDetailsVO.getAppliedRate());
		this.setClaimAmount(mailGpaInvoicDetailsVO.getClaimAmount());
		this.setClaimStatus(mailGpaInvoicDetailsVO.getClaimStatus());
		//this.setDueAmount(mailGpaInvoicDetailsVO.getDueAmount());
		this.setInvoicAmount(mailGpaInvoicDetailsVO.getInvoicAmount());
		this.setInvoicPaymentStatus(mailGpaInvoicDetailsVO.getInvoicPaymentStatus());
		this.setInvoicRefNum(mailGpaInvoicDetailsVO.getInvoicRefNum());
		this.setLastUpdatedTime(mailGpaInvoicDetailsVO.getLastUpdatedTime());
		this.setLastUpdatedUser(mailGpaInvoicDetailsVO.getLastUpdatedUser());
		this.setMailClass(mailGpaInvoicDetailsVO.getMailClass());
		this.setMailIdr(mailGpaInvoicDetailsVO.getMailIdr());
		this.setProcessStatus(mailGpaInvoicDetailsVO.getProcessStatus());
		this.setReceivedDate(mailGpaInvoicDetailsVO.getReceivedDate());
		this.setWeight(mailGpaInvoicDetailsVO.getWeight());
		log.exiting("MailInvoicDetail", "populateAttributes");
	}


	  public static MailGPAInvoicDetail find(MailInvoicDetailPK mailInvoicDetailPK)
	      throws FinderException, SystemException {
	      return PersistenceController.getEntityManager().find(MailGPAInvoicDetail.class, mailInvoicDetailPK);
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

	public static Page<InvoicDetailsVO> listInvoicDetails(InvoicFilterVO invoicFilterVO) throws SystemException {
		try {
			return constructDAO().listInvoicDetails(invoicFilterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	public static void saveRemarkDetails(InvoicDetailsVO invoicDetailsVO) throws SystemException{
		MailGPAInvoicDetail mailGPAInvoicDetail = null;
		try{
		mailGPAInvoicDetail = MailGPAInvoicDetail.find(constructMailGPAInvoicDetailPK(invoicDetailsVO));
		mailGPAInvoicDetail.setRemark(invoicDetailsVO.getRemarks());
		} catch (FinderException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}

	public static boolean saveClaimDetails(Collection<InvoicDetailsVO> invoicDetailsVOs) throws SystemException{
		MailGPAInvoicDetail mailGPAInvoicDetail = null;
		
		boolean changeInClaimAmount=true;
		InvoicSettlementDetail stlDetail = null;
		if(invoicDetailsVOs!=null && !invoicDetailsVOs.isEmpty()){
			for(InvoicDetailsVO invoicDetailsVO:invoicDetailsVOs){
				try {
					mailGPAInvoicDetail = MailGPAInvoicDetail.find(constructMailGPAInvoicDetailPK(invoicDetailsVO));
					stlDetail = InvoicSettlementDetail.find(constructSettlementDetailPK(invoicDetailsVO));
					Money orgClaimAmount = null ;
					try {
						orgClaimAmount = CurrencyHelper.getMoney(invoicDetailsVO.getCurrencyCode());
					} catch (CurrencyException e) {
						e.getErrorCode();
					}
					if(stlDetail != null && invoicDetailsVO.getClaimamount()!= null && invoicDetailsVO.getClaimamount().getAmount() > 0){
						stlDetail.setUpdClaimAmount(invoicDetailsVO.getClaimamount().getAmount());
						stlDetail.setLastUpdatedTime( new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
					}
					orgClaimAmount.setAmount(mailGPAInvoicDetail.getClaimAmount());
					if(orgClaimAmount!=null && orgClaimAmount.getAmount()==0
							&& invoicDetailsVO.getClaimamount()!=null&& invoicDetailsVO.getClaimamount().getAmount()>0
							&&(mailGPAInvoicDetail.getClaimType()==null ||"".equals(mailGPAInvoicDetail.getClaimType()))){
						mailGPAInvoicDetail.setClaimType("OTH");//as part of ICRD-343117 - Changed code corrected as part of ICRD-343424
					}
					invoicDetailsVO.setOrgClaimAmount(orgClaimAmount);   // for audit purpose
					//mailGPAInvoicDetail.setClaimAmount(invoicDetailsVO.getClaimamount().getAmount());
					if(mailGPAInvoicDetail.getUpdClaimAmount()==invoicDetailsVO.getClaimamount().getAmount()){
						changeInClaimAmount=false;
					}
					mailGPAInvoicDetail.setUpdClaimAmount(invoicDetailsVO.getClaimamount().getAmount());
					mailGPAInvoicDetail.setLastUpdatedTime( new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
					/*MailGPAInvoicDetailVO vo = new MailGPAInvoicDetailVO();
					vo.setSerialNumber(invoicDetailsVO.getSerialNumber());
					constructMailGPAInvoicDetailVO(mailGPAInvoicDetail,vo);
					try{
						new InvoicSettlementDetail(vo);
					}catch(SystemException exception){

					}*/
				} catch (FinderException e) {
					e.getErrorCode();
				}
			}
		}

return changeInClaimAmount;
	}

	//MoveTo action will update the process status
	public static void updateProcessStatus(Collection<InvoicDetailsVO> invoicDetailsVOs, String processStatus)throws SystemException{
		MailGPAInvoicDetail mailGPAInvoicDetail = null;
		if(invoicDetailsVOs!=null && !invoicDetailsVOs.isEmpty()){
			for(InvoicDetailsVO invoicDetailsVO:invoicDetailsVOs){
				try {
					mailGPAInvoicDetail = MailGPAInvoicDetail.find(constructMailGPAInvoicDetailPK(invoicDetailsVO));
					mailGPAInvoicDetail.setProcessStatus(processStatus);
					if("CLMZROPAY".equals(processStatus))
						mailGPAInvoicDetail.setClaimType("NPR");
					else if("CLMNOTINV".equals(processStatus))
						mailGPAInvoicDetail.setClaimType("NPR");
					else if("CLMNOINC".equals(processStatus))
						mailGPAInvoicDetail.setClaimType("OTH");
					else if("CLMRATDIF".equals(processStatus))
						mailGPAInvoicDetail.setClaimType("RVX");
					else if("CLMWGTDIF".equals(processStatus)){
						if(mailGPAInvoicDetail.getMailIdr() != null && mailGPAInvoicDetail.getMailIdr().length() == 29)
							mailGPAInvoicDetail.setClaimType("WXX");
						else
							mailGPAInvoicDetail.setClaimType("WX".concat(String.valueOf((int)mailGPAInvoicDetail.getWeight())));
					}
					else
						if(mailGPAInvoicDetail.getClaimType()==null)
							mailGPAInvoicDetail.setClaimType("OTH");
				} catch (FinderException e) {
					e.getErrorCode();
				}
			}
		}
	}

	public static void saveGroupRemarkDetails(InvoicFilterVO invoicFilterVO,String groupRemarksToSave)throws SystemException{
		try {
			constructDAO().saveGroupRemarkDetails(invoicFilterVO, groupRemarksToSave);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}


	private static MailInvoicDetailPK constructMailGPAInvoicDetailPK(InvoicDetailsVO invoicDetailsVO) throws SystemException{

		MailInvoicDetailPK mailInvoicDetailPK = new MailInvoicDetailPK();
		mailInvoicDetailPK.setCompanyCode(invoicDetailsVO.getCompanyCode());
		mailInvoicDetailPK.setMailSequenceNumber(invoicDetailsVO.getMailSequenceNumber());
		return mailInvoicDetailPK;
	}
	private static InvoicSettlementDetailPK constructSettlementDetailPK(InvoicDetailsVO invoicDetailsVO) throws SystemException{
		InvoicSettlementDetailPK settlementDetailPK = new InvoicSettlementDetailPK();
		settlementDetailPK.setCompanyCode(invoicDetailsVO.getCompanyCode());
		settlementDetailPK.setMailSequenceNumber(invoicDetailsVO.getMailSequenceNumber());
		settlementDetailPK.setSerialNumber(invoicDetailsVO.getSerialNumber());
		return settlementDetailPK;
	}
	/**
	 * 	Method		:	MailGPAInvoicDetail.updateMailStatus
	 *	Added by 	:	A-7929 on Jan 10, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param invoicDetailsVO,RaiseClaimFlag
	 *	Return type	:
	 * @throws PersistenceException
	 */
	public static void updateMailStatus(Collection<InvoicDetailsVO> invoicDetailsVOs, String raiseClaimFlag)
			throws SystemException, PersistenceException {
		MailGPAInvoicDetail mailGPAInvoicDetail = null;
		if (invoicDetailsVOs != null && !invoicDetailsVOs.isEmpty()) {
			for (InvoicDetailsVO invoicDetailsVO : invoicDetailsVOs) {
				try {
					mailGPAInvoicDetail = MailGPAInvoicDetail.find(constructMailGPAInvoicDetailPK(invoicDetailsVO));
					if ("Y".equals(raiseClaimFlag)) {
						mailGPAInvoicDetail.setClaimStatus("GEN");
						/*if(invoicDetailsVO.getPreviousSettlementAmount() != null && invoicDetailsVO.getTotalSettlementAmount() != null){
								mailGPAInvoicDetail.setTotalSettlementAmount(invoicDetailsVO.getPreviousSettlementAmount().getAmount()+
										invoicDetailsVO.getInvoicamount().getAmount());
						}else{
							mailGPAInvoicDetail.setTotalSettlementAmount(0);
						}*/
					}
					else{
						/*if(invoicDetailsVO.getPreviousSettlementAmount() != null && invoicDetailsVO.getTotalSettlementAmount() != null){
							mailGPAInvoicDetail.setTotalSettlementAmount(invoicDetailsVO.getPreviousSettlementAmount().getAmount()+
									invoicDetailsVO.getInvoicamount().getAmount());
					}else{
							mailGPAInvoicDetail.setTotalSettlementAmount(0);
						}*/
						if("R".equals(raiseClaimFlag)){
							mailGPAInvoicDetail.setInvoicPaymentStatus("ROP");
							mailGPAInvoicDetail.setProcessStatus("OVRPAYREJ");
						}else{
							if("U".equals(raiseClaimFlag)){
							invoicDetailsVO.setInvoicPayStatus("CAU");   	
							invoicDetailsVO.setInvoicID(mailGPAInvoicDetail.getInvoicRefNum());
						}
							
							else if(mailGPAInvoicDetail.getProcessStatus()!=null && "AMOTOBEACT".equals(mailGPAInvoicDetail.getProcessStatus())) {
							mailGPAInvoicDetail.setProcessStatus("AMOTACT");
						}
							else  if((invoicDetailsVO.getInvoicPayStatus() != null &&
								invoicDetailsVO.getInvoicPayStatus().contains("SP")) ||(mailGPAInvoicDetail!=null && "SP".equals(mailGPAInvoicDetail.getInvoicPaymentStatus()))){
						mailGPAInvoicDetail.setInvoicPaymentStatus("ASP");
						mailGPAInvoicDetail.setProcessStatus("SHRPAYACP");
						mailGPAInvoicDetail.setClaimStatus(null);
						//Triggering Invoic Accounting; Added by A-7794 as part of ICRD-235899
						invoicDetailsVO.setInvoicID(mailGPAInvoicDetail.getInvoicRefNum());
						//constructDAO().triggerInvoicAccounting(invoicDetailsVO) ;
						}
						else if (mailGPAInvoicDetail!=null && "OP".equals(invoicDetailsVO.getInvoicPayStatus())){
						mailGPAInvoicDetail.setInvoicPaymentStatus("AOP");
						mailGPAInvoicDetail.setProcessStatus("OVRPAYACP");
						invoicDetailsVO.setInvoicID(mailGPAInvoicDetail.getInvoicRefNum());
						}
						else{
							//No change
						}
						if(mailGPAInvoicDetail!=null && mailGPAInvoicDetail.getInvoicAmount()  != 0 && !("AMOTOBEACT".equals(invoicDetailsVO.getMailbagInvoicProcessingStatus()))){
						constructDAO().triggerInvoicAccounting(invoicDetailsVO) ;
						}
						}
					}
					if(mailGPAInvoicDetail!=null){
					mailGPAInvoicDetail.setLastUpdatedTime( new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
					}
				} catch (FinderException e) {
					log.log(Log.FINEST, "Exception  " , e);
					throw new SystemException("mail.mra.gpareporting.acceptfailure.dummytobereportedtags");
				}
			}
		}

	}

	/**
	 *
	 * @param mailGPAInvoicDetail
	 * @param vo
	 */
	private static void constructMailGPAInvoicDetailVO(MailGPAInvoicDetail mailGPAInvoicDetail, MailGPAInvoicDetailVO vo){
		vo.setCompanyCode(mailGPAInvoicDetail.getMailInvoicDetailPK().getCompanyCode());
		vo.setAppliedRate(mailGPAInvoicDetail.getAppliedRate());
		vo.setClaimAmount(mailGPAInvoicDetail.getClaimAmount());
		vo.setClaimStatus(mailGPAInvoicDetail.getClaimStatus());
		//vo.setDueAmount(mailGPAInvoicDetail.getDueAmount());
		vo.setInvoicAmount(mailGPAInvoicDetail.getInvoicAmount());
		vo.setInvoicPaymentStatus(mailGPAInvoicDetail.getInvoicPaymentStatus());
		vo.setInvoicRefNum(mailGPAInvoicDetail.getInvoicRefNum());
		vo.setLastUpdatedTime(mailGPAInvoicDetail.getLastUpdatedTime());
		vo.setLastUpdatedUser(mailGPAInvoicDetail.getLastUpdatedUser());
		vo.setMailClass(mailGPAInvoicDetail.getMailClass());
		vo.setMailIdr(mailGPAInvoicDetail.getMailIdr());
		vo.setMailSequenceNumber(mailGPAInvoicDetail.getMailInvoicDetailPK().getMailSequenceNumber());
		vo.setPoaCode(mailGPAInvoicDetail.getPoaCode());
		vo.setProcessStatus(mailGPAInvoicDetail.getProcessStatus());
		vo.setReceivedDate(mailGPAInvoicDetail.getReceivedDate());
		vo.setRemark(mailGPAInvoicDetail.getRemark());
		vo.setVersionNumber(mailGPAInvoicDetail.getVersionNumber()+1);
		vo.setWeight(mailGPAInvoicDetail.getWeight());

	}


	/**
	 *
	 * @param companyCode
	 * @param mailSeqnum
	 * @return
	 * @throws SystemException
	 */
	public static Collection<InvoicDetailsVO> findInvoicAndClaimDetails(String companyCode, long mailSeqnum)throws SystemException {
		try {
			return constructDAO().findInvoicAndClaimDetails(companyCode, mailSeqnum) ;
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
	/**
	 * 	Getter for claimRefNumber
	 *	Added by : A-5219 on 17-Feb-2020
	 * 	Used for :
	 */
	@Column(name = "CLMREFNUM")
	public String getClaimRefNumber() {
		return claimRefNumber;
	}
	/**
	 *  @param claimRefNumber the claimRefNumber to set
	 * 	Setter for claimRefNumber
	 *	Added by : A-5219 on 17-Feb-2020
	 * 	Used for :
	 */
	public void setClaimRefNumber(String claimRefNumber) {
		this.claimRefNumber = claimRefNumber;
	}
	public static int checkForRejectionMailbags(String companyCode, InvoicVO invoicVO) throws SystemException {
		int count = 0;
		try {
			count = constructDAO().checkForRejectionMailbags(companyCode, invoicVO);
		} catch (SystemException e) {
			log.log(Log.FINEST, "Exception  " , e);
		}
		return count;
	}

}
