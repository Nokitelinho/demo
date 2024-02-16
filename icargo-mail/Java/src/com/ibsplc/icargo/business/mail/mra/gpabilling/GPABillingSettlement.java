/**
 * GPABillingSettlement.java Created on Mar 30, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.mra.MailTrackingMRABusinessException;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.BillingSummaryDetailsFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.POMailSummaryDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SettlementDetailsVO;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedCurrencyProxy;
import com.ibsplc.icargo.business.shared.currency.vo.CurrencyConvertorVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author a-4823
 *
 */
@Entity
@Table(name = "MALMRAGPASTL")
public class GPABillingSettlement {

	private static final String MODULE_NAME = "mail.mra.gpabilling";
	private GPABillingSettlementPK gpaBillingSettlementPK;
	private Calendar settlementDate;
	private String settlementCurrency;
	private String lastUpdatedUser;
	private Calendar lastUpdateTime;
	private Set<GPABillingSettlementDetails>  gpaBillingSettlementDetails; 

	//default constructor
	public GPABillingSettlement(){

	}
	/**
	 * 
	 * @param gpaSettlementVO
	 * @throws SystemException
	 */
	public GPABillingSettlement(GPASettlementVO gpaSettlementVO)throws SystemException {		
		populatePK(gpaSettlementVO);
		populateAttribute(gpaSettlementVO);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException createException) {
			// TODO Auto-generated catch block
			createException.getErrorCode();
			throw new SystemException(createException.getErrorCode());
		}
		gpaSettlementVO.setSettlementSequenceNumber(this.gpaBillingSettlementPK.getSettlementSequenceNumber());
		populateChildTable(gpaSettlementVO);
		//saveSettlementDetails(gpaSettlementVO);		


	}
	/**
	 * to populate seqnum in GPABillingSettlementDetails
	 * @param gpaSettlementVO
	 * @throws SystemException
	 */
	private void populateChildTable(GPASettlementVO gpaSettlementVO) throws SystemException {

		Collection<SettlementDetailsVO> settlementDetailsVOs = gpaSettlementVO.getSettlementDetailsVOs();
		int serNum=0;
		if(settlementDetailsVOs!=null && settlementDetailsVOs.size()>0){

			Set<GPABillingSettlementDetails> newGpaBillingSettlementDetails = new HashSet<GPABillingSettlementDetails>();
			GPABillingSettlementDetails gpaBillingSettlementDetails=null;
			for(SettlementDetailsVO settlementDetailsVO : settlementDetailsVOs){
				
				settlementDetailsVO.setSettlementId(gpaSettlementVO.getSettlementId());
				settlementDetailsVO.setSettlementSequenceNumber(gpaSettlementVO.getSettlementSequenceNumber());
				
				if("EXCELUPLOAD".equals(gpaSettlementVO.getFrmScreen())){//added for ICRD-351167
				settlementDetailsVO.setSerialNumber(serNum);
				if(!"0000".equals(settlementDetailsVO.getChequeNumber())){
				 gpaBillingSettlementDetails = new GPABillingSettlementDetails(settlementDetailsVO);
				//Added by A-7794 as part of ICRD-194277
				if(gpaSettlementVO.getLastUpdatedTime() != null){
				gpaBillingSettlementDetails.setLastUpdatedTime(gpaSettlementVO.getLastUpdatedTime().toCalendar());
				}
				gpaBillingSettlementDetails.setLastUpdatedUser(gpaSettlementVO.getLastUpdatedUser());
				serNum=gpaBillingSettlementDetails.getGpaBillingSettlementDetailsPK().getSerialNumber();
				newGpaBillingSettlementDetails.add(gpaBillingSettlementDetails);
				}else if (gpaBillingSettlementDetails!=null && settlementDetailsVO.getChequeAmount()!=null){
					gpaBillingSettlementDetails.setSettlementAmount(gpaBillingSettlementDetails.getSettlementAmount()+settlementDetailsVO.getChequeAmount().getAmount());
				}
				}else{
					 gpaBillingSettlementDetails = new GPABillingSettlementDetails(settlementDetailsVO);
					//Added by A-7794 as part of ICRD-194277
					if(gpaSettlementVO.getLastUpdatedTime() != null){
					gpaBillingSettlementDetails.setLastUpdatedTime(gpaSettlementVO.getLastUpdatedTime().toCalendar());
					}
					gpaBillingSettlementDetails.setLastUpdatedUser(gpaSettlementVO.getLastUpdatedUser());
					newGpaBillingSettlementDetails.add(gpaBillingSettlementDetails);
				}
				
			}
			this.setGpaBillingSettlementDetails(newGpaBillingSettlementDetails);
		}
		
	}

	/**
	 * 
	 * @param gpaSettlementVO
	 * @throws SystemException 
	 */
	private void saveSettlementDetails(GPASettlementVO gpaSettlementVO) throws SystemException {
		Integer serNum=null;
		Collection <SettlementDetailsVO> settlementDetailsVOs = gpaSettlementVO.getSettlementDetailsVOs();
		if(settlementDetailsVOs!= null){
			for(SettlementDetailsVO settlementDetailsVO : settlementDetailsVOs){
				settlementDetailsVO.setSettlementId(gpaSettlementVO.getSettlementId());
				GPABillingSettlementDetails gpaBillingSettlementDetails=new GPABillingSettlementDetails(settlementDetailsVO);
				serNum=Integer.valueOf(gpaBillingSettlementDetails.getGpaBillingSettlementDetailsPK().getSerialNumber());	
				settlementDetailsVO.setSerialNumber(serNum.intValue());
			}	


		}
	}
	/**
	 * 
	 * @param gpaSettlementVO
	 */
	private void populateAttribute(GPASettlementVO gpaSettlementVO) {
		this.settlementCurrency= gpaSettlementVO.getSettlementCurrency();
		if(gpaSettlementVO.getSettlementDate()!= null){
			this.settlementDate= gpaSettlementVO.getSettlementDate().toCalendar();
		}		
		this.setLastUpdatedUser(gpaSettlementVO.getLastUpdatedUser());
		if(gpaSettlementVO.getLastUpdatedTime()!= null){
			this.setLastUpdateTime(gpaSettlementVO.getLastUpdatedTime().toCalendar());	
		}


	}
	/**
	 * 
	 * @param gpaSettlementVO
	 */
	private void populatePK(GPASettlementVO gpaSettlementVO) {
		GPABillingSettlementPK gpaBillingSettlementPK = new GPABillingSettlementPK();
		gpaBillingSettlementPK.setCompanyCode(gpaSettlementVO.getCompanyCode());
		gpaBillingSettlementPK.setGpaCode(gpaSettlementVO.getGpaCode());
		gpaBillingSettlementPK.setSettlementReferenceNumber(gpaSettlementVO.getSettlementId());		
		this.gpaBillingSettlementPK = gpaBillingSettlementPK;


	}
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "gpaCode", column = @Column(name = "GPACOD")),
		@AttributeOverride(name = "settlementReferenceNumber", column = @Column(name = "STLREFNUM")),
		@AttributeOverride(name = "settlementSequenceNumber", column = @Column(name = "SEQNUM"))})
		public GPABillingSettlementPK getGpaBillingSettlementPK() {
		return gpaBillingSettlementPK;
	}
	/**
	 * @param gpaBillingSettlementPK the gpaBillingSettlementPK to set
	 */
	public void setGpaBillingSettlementPK(
			GPABillingSettlementPK gpaBillingSettlementPK) {
		this.gpaBillingSettlementPK = gpaBillingSettlementPK;
	}

	@Column(name = "STLCUR") 
	public String getSettlementCurrency() {
		return settlementCurrency;
	}
	/**
	 * @param settlementCurrency the settlementCurrency to set
	 */
	public void setSettlementCurrency(String settlementCurrency) {
		this.settlementCurrency = settlementCurrency;
	}

	@Column(name = "STLDAT")
	@Temporal(TemporalType.TIMESTAMP)

	public Calendar getSettlementDate() {
		return settlementDate;
	}
	/**
	 * @param settlementDate the settlementDate to set
	 */
	public void setSettlementDate(Calendar settlementDate) {
		this.settlementDate = settlementDate;
	}
	@Column (name="LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * method for calling dao of the submodule
	 * @return queryDAO
	 * @throws SystemException
	 */
	private static MRAGPABillingDAO constructDAO()
	throws SystemException {
		MRAGPABillingDAO queryDAO =null;
		try {
			queryDAO = (MRAGPABillingDAO)PersistenceController
			.getEntityManager()
			.getQueryDAO(MODULE_NAME);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(),e);
		}

		return queryDAO;
	}

	/**
	 * 
	 * @param invoiceSettlementFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<GPASettlementVO> findSettlementDetails(
			InvoiceSettlementFilterVO invoiceSettlementFilterVO,int displayPage)throws SystemException{

		try {
			return constructDAO().findSettlementDetails(invoiceSettlementFilterVO,displayPage);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage());

		} 	

	}

	public void update(GPASettlementVO gpaSettlementVO) throws SystemException, FinderException {
		populateAttribute(gpaSettlementVO);
		updateGPABillingSettlementDetails(gpaSettlementVO.getSettlementDetailsVOs());

	}

	private void updateGPABillingSettlementDetails(
			Collection<SettlementDetailsVO> settlementDetailsVOs) throws SystemException, FinderException {
		if(settlementDetailsVOs!=null){
			for(SettlementDetailsVO settlementDetailsVO:settlementDetailsVOs){

				if (SettlementDetailsVO.OPERATION_FLAG_DELETE.equals(settlementDetailsVO
						.getOperationFlag())) {

					GPABillingSettlementDetails gpaBillingSettlementDetails = GPABillingSettlementDetails.find(settlementDetailsVO
							.getCompanyCode(), settlementDetailsVO.getGpaCode(),
							settlementDetailsVO.getSettlementId(), settlementDetailsVO.getSerialNumber(),settlementDetailsVO.getSettlementSequenceNumber());
					if(gpaBillingSettlementDetails!=null){
						try {

							gpaBillingSettlementDetails.remove();
						} catch(RemoveException ex) {
							throw new SystemException(ex.getMessage(), ex);
						}
					}
				}
			}
			for(SettlementDetailsVO settlementDetailsVO:settlementDetailsVOs){
				if (SettlementDetailsVO.OPERATION_FLAG_INSERT.equals(settlementDetailsVO
						.getOperationFlag())) {
					new GPABillingSettlementDetails(settlementDetailsVO);
				}
				if (SettlementDetailsVO.OPERATION_FLAG_UPDATE.equals(settlementDetailsVO
						.getOperationFlag())) {

					GPABillingSettlementDetails gpaBillingSettlementDetails = GPABillingSettlementDetails.find(settlementDetailsVO
							.getCompanyCode(), settlementDetailsVO.getGpaCode(),
							settlementDetailsVO.getSettlementId(), settlementDetailsVO.getSerialNumber(),settlementDetailsVO.getSettlementSequenceNumber());
					gpaBillingSettlementDetails.update(settlementDetailsVO);
				}
			}
		}
	}

	/**
	 * 
	 * @param companyCode
	 * @param gpaCode
	 * @param settlementId
	 * @param settlementSequenceNumber
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static GPABillingSettlement find(String companyCode, String gpaCode,
			String settlementId, int settlementSequenceNumber) throws SystemException, FinderException {
		GPABillingSettlementPK gpaBillingSettlementPK = new GPABillingSettlementPK();
		gpaBillingSettlementPK.setCompanyCode(   companyCode);
		gpaBillingSettlementPK.setGpaCode(gpaCode);
		gpaBillingSettlementPK.setSettlementReferenceNumber(settlementId);
		gpaBillingSettlementPK.setSettlementSequenceNumber(settlementSequenceNumber);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(GPABillingSettlement.class, gpaBillingSettlementPK);
		}

	public void remove() throws SystemException, RemoveException{

		PersistenceController.getEntityManager().remove(this);

	}

	public static Collection<GPASettlementVO> findUnSettledInvoicesForGPA(
			GPASettlementVO gpaSettlementVO) throws SystemException {
		try {
			return constructDAO().findUnSettledInvoicesForGPA(gpaSettlementVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage());

		} 
	}

	/**
	 * 
	 * @param netAmount
	 * @param billingCurrencyCode
	 * @param sysParamExgratbas 
	 * @return 
	 * @throws SystemException 
	 * @throws ProxyException 
	 * @throws MailTrackingMRABusinessException 
	 */
	public static Double findExchangeRate(String companyCode,String toCurrency,
			String fromCurrency, String exchangeRateBasis,LocalDate date) throws SystemException, MailTrackingMRABusinessException {
		Money currAmt=null;
		Double rate=0.0;
		Double correctRate = 0.0;

		if(toCurrency.equals(fromCurrency)){
			correctRate=1.0;
		}
		try{
			CurrencyConvertorVO currencyConvertorVO = new CurrencyConvertorVO();

			currencyConvertorVO.setCompanyCode(companyCode);
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			currencyConvertorVO.setAirlineIdentifier(logonAttributes.getOwnAirlineIdentifier());
			currencyConvertorVO.setFromCurrencyCode(fromCurrency);
			currencyConvertorVO.setToCurrencyCode(toCurrency);
			currencyConvertorVO.setRatingBasisType(exchangeRateBasis);
			/*currencyConvertorVO.setValidityStartDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
			currencyConvertorVO.setValidityEndDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));*/
			currencyConvertorVO.setRatePickUpDate(date);
			rate = new SharedCurrencyProxy().findConversionRate(currencyConvertorVO);
			if(rate!=0){
				correctRate=(rate);
			}		
		}
		catch(ProxyException e){
			ErrorVO errorVo = new ErrorVO(
					MailTrackingMRABusinessException.MTK_MRA_DEFAULTS_NO_EXCHANGE_RATE);
			errorVo.setErrorDisplayType(ErrorDisplayType.ERROR);
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			mailTrackingMRABusinessException.addError(errorVo);
			throw mailTrackingMRABusinessException;
		}
		return correctRate;

	}

	/**
	 * 
	 * @param settlementDetailsVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<GPASettlementVO> findSettledInvoicesForGPA(
			SettlementDetailsVO settlementDetailsVO) throws SystemException {

		try {
			return constructDAO().findSettledInvoicesForGPA(settlementDetailsVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage());

		} 
	}


	/**
	 * 
	 * @param companyCode
	 * @param paCode
	 * @return
	 * @throws SystemException
	 */
	public static String findSettlementCurrency(String companyCode,String paCode)throws SystemException{
		try {
			return constructDAO().findSettlementCurrency(companyCode,paCode);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage());

		} 
	}

	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
		@JoinColumn(name = "GPACOD", referencedColumnName = "GPACOD", insertable = false, updatable = false),
		@JoinColumn(name = "STLREFNUM", referencedColumnName = "STLREFNUM", insertable = false, updatable = false),
		@JoinColumn(name = "SEQNUM", referencedColumnName = "SEQNUM", insertable = false, updatable = false) })
		public Set<GPABillingSettlementDetails> getGpaBillingSettlementDetails() {
		return gpaBillingSettlementDetails;
	}

	/**
	 * @param gpaBillingSettlementDetails the gpaBillingSettlementDetails to set
	 */
	public void setGpaBillingSettlementDetails(
			Set<GPABillingSettlementDetails> gpaBillingSettlementDetails) {
		this.gpaBillingSettlementDetails = gpaBillingSettlementDetails;
	}
	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException 
	 */
	public static Collection<GPASettlementVO> generateSettlementDetails(
			InvoiceSettlementFilterVO filterVO) throws SystemException {
		try {
			return constructDAO().generateSettlementDetails(filterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage());

		} 
	}
	/**
	 * 
	 * @param gpaSettlementVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<GPASettlementVO> findUnSettledInvoicesForGPAForSettlementCapture(
			GPASettlementVO gpaSettlementVO) throws SystemException {
		try {
			return constructDAO().findUnSettledInvoicesForGPAForSettlementCapture(gpaSettlementVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage());
		} 
	}
	/**
	 * 
	 * @param billingSummaryDetailsFilterVO
	 * @return
	 * @throws SystemException 
	 */
	public static Collection<POMailSummaryDetailsVO> findPOMailSummaryDetails(
			BillingSummaryDetailsFilterVO billingSummaryDetailsFilterVO) throws SystemException {
		try {
			return constructDAO().findPOMailSummaryDetails(billingSummaryDetailsFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage());

		} 
	}
	public static Collection<SettlementDetailsVO> findOutStandingChequesForGPA(
			GPASettlementVO gpaSettlementVO) throws SystemException  {

		try {
			return constructDAO().findOutStandingChequesForGPA(gpaSettlementVO);

		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage());

		} 
	}	
	/**
	 * Added by A-7794 as part of ICRD-194277
	 * @param invoiceNumber
	 *  @param billingPeriodTodate
	 *  @param accountNumber
	 * @return CN51SummaryVO
	 * @throws SystemException 
	 */
	public static CN51SummaryVO findGPADtlsForSAPSettlemnt(String invoiceNumber,LocalDate billingPeriodTodate,String accountNumber)throws SystemException {
		CN51SummaryVO summaryVo = new CN51SummaryVO();
		try {
			return constructDAO().findGPADtlsForSAPSettlemntMail(invoiceNumber, billingPeriodTodate, accountNumber);


		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage());

		} 
	}
	/**
	 * Added by A-7794 as part of ICRD-194277
	 * @param comapnyCode
	 *  @param invoice
	 *  @param gpaCode
	 * @return CN51SummaryVO
	 * @throws SystemException 
	 */
	public static InvoiceSettlementVO findLatestSettlementForInvoice(String comapnyCode,String invoice,String gpaCode)throws SystemException {
		InvoiceSettlementVO invSettlVO = new InvoiceSettlementVO();
		try {
			return constructDAO().findLatestSettlementForInvoice(comapnyCode, invoice, gpaCode);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage());

		} 
	}

	/**
	 * Added by A-7794 on 23-Jan-2018 as part of ICRD-194277
	 * @param gpaCode
	 * @param settlementRef
	 * @return
	 */
	public static GPASettlementVO findSettlementSeqNum (String gpaCode, String settlementRef){
		GPASettlementVO stlmnt = new GPASettlementVO();
		try {
			stlmnt = constructDAO().findSettlementSeqNum(gpaCode,settlementRef);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stlmnt;
	}

	/**
	 * Added by A-7871 on 06-Jun-2018 as part of ICRD-235799
	 * @param filterVO

	 * @return Collection<InvoiceSettlementVO>
	 * @throws SystemException 
	 */
	public  static  Collection<InvoiceSettlementVO> findUnsettledMailbagDetails (InvoiceSettlementFilterVO filterVO) throws SystemException{
		Collection <InvoiceSettlementVO> invoiceSettlementVO =null; 
		
			try {
				invoiceSettlementVO = constructDAO().findUnsettledMailbagDetails(filterVO);
			} catch (PersistenceException e) {
				// TODO Auto-generated catch block
				throw new SystemException(e.getMessage());
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				throw new SystemException(e.getMessage());
			}
		
		return invoiceSettlementVO;
	}

	
	
	/**
	 * Added by A-7871 on 08-Jun-2018 as part of ICRD-235799
	 * @param filterVO
	 * @return Collection<InvoiceSettlementVO>
	 * @throws SystemException 
	 */
	public  static  Collection<InvoiceSettlementVO> findSettledmailbags (InvoiceSettlementFilterVO filterVO) throws SystemException{
		Collection <InvoiceSettlementVO> invoiceSettlementVO =null; 
		
			try {
				invoiceSettlementVO = constructDAO().findSettledMailbagDetails(filterVO);
			} catch (PersistenceException e) {
				// TODO Auto-generated catch block
				throw new SystemException(e.getMessage());
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				throw new SystemException(e.getMessage());
			}
		
		return invoiceSettlementVO;
	}
	/**
	 * 	Method		:	GPABillingSettlement.fetchDataForUpload
	 *	Added by 	:	A-7531 on 14-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param fileType
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<InvoiceSettlementVO>
	 */
	public static Collection<InvoiceSettlementVO> fetchDataForUpload(String companyCode, String fileType) throws SystemException{
		try{
			return constructDAO().fetchDataForUpload(companyCode,fileType);
	    		}catch(PersistenceException persistenceException){
	    			throw new SystemException(persistenceException.getErrorCode());
	    		}
	    	}
	/**
	 * 	Method		:	GPABillingSettlement.removeDataFromTempTable
	 *	Added by 	:	A-7531 on 14-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param fileUploadFilterVO 
	 *	Return type	: 	void
	 */
	public static void removeDataFromTempTable(FileUploadFilterVO fileUploadFilterVO) throws SystemException{
		try {
			constructDAO().removeDataFromTempTable(fileUploadFilterVO);
		} catch (PersistenceException e) {
			 throw new SystemException(e.getMessage(), e);}
	}
	/**
	 * 	Method		:	GPABillingSettlement.filenameValidation
	 *	Added by 	:	A-7531 on 21-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param invoiceVO
	 *	Parameters	:	@return 
	 *	Return type	: 	int
	 */
	public static int filenameValidation(InvoiceSettlementVO invoiceVO) throws SystemException{
		// TODO Auto-generated method stub
		try{
			return constructDAO().filenameValidation(invoiceVO);
	    		}catch(PersistenceException persistenceException){
	    			throw new SystemException(persistenceException.getErrorCode());
	    		}
	}

}
