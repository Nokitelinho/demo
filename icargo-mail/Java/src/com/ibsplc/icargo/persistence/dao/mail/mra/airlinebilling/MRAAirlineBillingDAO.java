/*
 * MRAAirlineBillingDAO.java created on Feb 16, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.MRAArlAuditFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineForBillingVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineInvoiceLovVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineInvoiceReportVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ArlInvoiceDetailsReportVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MiscFileFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InterlineFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceLovFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoInInvoiceVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoLovVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SisSupportingDocumentDetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SupportingDocumentFilterVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.sis.SISMessageVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2521
 * 
 */
public interface MRAAirlineBillingDAO {

	/**
	 * @param upuCalendarFilterVO
	 * @return Collection<UPUCalendarVO>
	 * @throws SystemException
	 */
	public Collection<UPUCalendarVO> displayUPUCalendarDetails(
			UPUCalendarFilterVO upuCalendarFilterVO) throws SystemException;

	/**
	 * Method to list CN66 details
	 * 
	 * @param cn66FilterVo
	 * @return Page<AirlineCN66DetailsVO>
	 * @throws SystemException
	 */
	public Page<AirlineCN66DetailsVO> findCN66Details(
			AirlineCN66DetailsFilterVO cn66FilterVo) throws SystemException;
	/**@author A-3434
	 * Method to list CN66 details
	 * 
	 * @param cn66FilterVo
	 * @return Collection<AirlineCN66DetailsVO>
	 * @throws SystemException
	 */
	public Collection<AirlineCN66DetailsVO> findCN66DetailsVOCollection(
			AirlineCN66DetailsFilterVO cn66FilterVo) throws SystemException;
	/**
	 * Method to list AirlineExceptionInInvoices details
	 * 
	 * @param exceptionInInvoiceFilterVO
	 * @return  Collection<ExceptionInInvoiceVO>
	 * @throws SystemException
	 */
	public Page<ExceptionInInvoiceVO> findAirlineExceptionInInvoices(
			ExceptionInInvoiceFilterVO exceptionInInvoiceFilterVO) throws SystemException,PersistenceException;
	/**
	 * Method to Print AirlineExceptionInInvoices details
	 * 
	 * @param exceptionInInvoiceFilterVO
	 * @return  Collection<ExceptionInInvoiceVO>
	 * @throws SystemException
	 */
	public Collection<ExceptionInInvoiceVO> findAirlineExceptionInInvoicesForReport(
			ExceptionInInvoiceFilterVO exceptionInInvoiceFilterVO) throws SystemException,PersistenceException;
	/** added by A-2407
	 * Method to list AirlineException details
	 * 
	 * @param airlineExceptionsFilterVO
	 * @return  Collection<AirlineExceptionsVO>
	 * @throws SystemException
	 */
	public  Page<AirlineExceptionsVO> displayAirlineExceptions
						(AirlineExceptionsFilterVO airlineExceptionsFilterVO)throws SystemException;
	/** added by A-2391
	 * Method to list AirlineException details from ExceptionInInvoice
	 * 
	 * @param airlineExceptionsFilterVO
	 * @return  Collection<AirlineExceptionsVO>
	 * @throws SystemException
	 */
	public  Collection<AirlineExceptionsVO> findAirlineExceptions
						(AirlineExceptionsFilterVO airlineExceptionsFilterVO)throws SystemException;
	
	/**
	 * 
	 * @param cn51FilterVO
	 * @return
	 * @throws SystemException
	 */
	public AirlineCN51SummaryVO findCN51Details
									(AirlineCN51FilterVO cn51FilterVO)
									throws SystemException ,PersistenceException;
	
	/**
	 * 
	 * @param cn51FilterVO
	 * @return
	 * @throws SystemException
	 */
	public AirlineCN51SummaryVO findCN51DetailColection
									(AirlineCN51FilterVO cn51FilterVO)
									throws SystemException ,PersistenceException;
	
	/**
	 * 
	 * @param invoiceLovFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Page<AirlineInvoiceLovVO> displayInvoiceLOV(
			InvoiceLovFilterVO invoiceLovFilterVO) throws SystemException;

	/**
	 * 
	 * @param companyCode
	 * @param memoCode
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public Page<MemoLovVO> displayMemoLOV(
			String companyCode, String memoCode, int pageNumber) throws SystemException;
	
	/**
	 * 
	 * @param memoFilterVo
	 * @return
	 * @throws SystemException
	 */
	public Collection<MemoInInvoiceVO> findMemoDetails(MemoFilterVO memoFilterVo)
	throws SystemException;
	/**
	 * @author A-2408
	 * @param cn66FilterVo
	 * @throws SystemException
	 */
	public String processMail(AirlineCN66DetailsFilterVO cn66FilterVo)
	throws SystemException;
	
	/*Added by A-2391*/
	 /**
	    *
	    * @param airlineExceptionsFilterVO
	    * @return  Collection<AirlineExceptionsVO>
	    * @throws SystemException
	    * @throws PersistenceException
	    */
	   Collection<AirlineExceptionsVO> printExceptionReportDetail(
			   AirlineExceptionsFilterVO airlineExceptionsFilterVO)
	           throws SystemException, PersistenceException;
	   
	   /**
	    * @author A-2521
	    * method to generate invoice for outward airline billing 
	    * @param invoiceFilterVO
	    * @return
	    * @throws SystemException
	    */
	   public String generateOutwardBillingInvoice(InvoiceLovFilterVO invoiceFilterVO)
	   throws SystemException;
	   /**
	    * @author a-2270
	    * @param airlineCN51FilterVO
	    * @return
	    * @throws PersistenceException
	    * @throws SystemException
	    */
	   public  Collection<AirlineInvoiceReportVO> findInvoiceDetailsForReport(AirlineCN51FilterVO airlineCN51FilterVO)
		throws PersistenceException,SystemException;
	   
	   /**
	    * @author A-2521
	    * @param filterVO
	    * @return
	    * @throws SystemException	    
	    */
	   public Collection<AirlineCN51SummaryVO> generateInvoiceReports(AirlineCN51FilterVO filterVO)
	   throws SystemException;
	   
	   /**
	    * 
	    * @param cn51filterVO
	    * @return
	    * @throws PersistenceException
	    * @throws SystemException
	    */
	   public Page<AirlineCN51SummaryVO> 
						findCN51s(AirlineCN51FilterVO cn51filterVO) 
							throws PersistenceException,SystemException;

	   /**
	    * A-2458 
	    * @param airlineCN51FilterVO
	    * @return Collection<AirlineCN51DetailsVO> 
	    * @throws SystemException
	    */	   
	   public Collection<AirlineCN51DetailsVO> findInwardInvoicesCollection(
				AirlineCN51FilterVO airlineCN51FilterVO) throws SystemException;
	   
	   
	   /**
		 * 
		 * @param memoFilterVo
		 * @return Collection<MemoInInvoiceVO>
		 * @throws SystemException
		 */
		public Collection<MemoInInvoiceVO> findOutwardRejectionMemo(MemoFilterVO memoFilterVo)
		throws SystemException;
		
		 /**
		 * @author A-2391
		 * @param filterVo
		 * @return RejectionMemoVO
		 * @throws SystemException
		 */
		public RejectionMemoVO findRejectionMemo(RejectionMemoFilterVO filterVo)
		throws SystemException;
		
		/**
		 * @author A-2391
		 * @param rejectionMemoVO
		 * @return String
		 * @throws SystemException
		 */
		public String findBlgCurCode(RejectionMemoVO rejectionMemoVO)
		throws SystemException;
		

		 /**
		 * @author A-2391
		 * @param rejectionMemoVO
		 * @return double
		 * @throws SystemException
		 */
		public double findExgRate(RejectionMemoVO rejectionMemoVO)
		throws SystemException;
		
		/**
		 * @author A-2391
		 * @param rejectionMemoFilterVO
		 * @throws SystemException
		 */
		public String findAcctTxnIdr(RejectionMemoFilterVO rejectionMemoFilterVO,
				                  String user)
		throws SystemException;
		
		/**
		 * @author a-2391 This method is used to list the Audit details
		 * @param mailAuditFilterVO
		 * @return Collection<AuditDetailsVO>
		 * @throws SystemException
		 * @throws PersistenceException
		 */
		Collection<AuditDetailsVO> findArlAuditDetails(
				MRAArlAuditFilterVO mailAuditFilterVO) throws SystemException,
				PersistenceException;
		
		/**
		 * @author A-3434
		 * @param formOneFilterVo
		 * @return Page<FormOneVO>
		 * @throws SystemException
		 */
		public Page<FormOneVO> findFormOnes(FormOneFilterVO formOneFilterVo)
			throws SystemException;
		/**
		 * @author A-3434
		 * @param interlineFilterVo
		 * @return Collection<AirlineForBillingVO>
		 * @throws SystemException
		 * @throws PersistenceException
		 */
		public Collection<AirlineForBillingVO> findAirlineDetails(InterlineFilterVO interlineFilterVo)
		throws PersistenceException,SystemException;
		
		/**
		 * 
		 * @param formOneVO
		 * @return
		 * @throws SystemException
		 */
		public FormOneVO listFormOneDetails(FormOneVO formOneVO)
		throws SystemException;
		/**
		 * @author a-3456 This method is used to list the View Form1 details
		 * @param InterlineFilterVO
		 * @return FormOneVO
		 * @throws SystemException
		 */
		
		public FormOneVO findFormOneDetails(InterlineFilterVO interlineFilterVo)
		throws SystemException;
		
		/**
		 * @author A-3108
		 * @param  interlineFilterVO
		 * @return Collection<AirlineForBillingVO>
		 * @throws SystemException
		 */
		public Collection<AirlineForBillingVO> findFormThreeDetails(InterlineFilterVO interlineFilterVO)
			throws SystemException;
		/**
	    * @author A-3108
	    * @return
	    * @throws SystemException
	    */

	   public int findMaxSerialNumber()throws SystemException;
	   /** @author A-3108
	     * Method save FormThreeDetails
	     * @param lstupdusr
	     * @param lstupddat
	     * @param serialNumber
	     * @param companyCode
	     * @return String
	     * @throws SystemException
	     */
	    public String saveFormThree(String lstupdusr, LocalDate lstupddat, int serialNumber,String companyCode)
	    throws SystemException;
	    
	    
	    
	    /**
		 * @author a-3447
		 * @param AirlineCN51FilterVO
		 * @return AirlineCN51SummaryVO
		 * @throws SystemException
		 */
		
		public AirlineCN51SummaryVO findCaptureInvoiceDetails(AirlineCN51FilterVO airlineCN51FilterVO)
		throws SystemException,PersistenceException;
		
		/**
		 * to trigger Rejection Memo Accounting at despatch level
		 * @author Meenu A-2565
		 * @param rejectionVO
		 * @throws SystemException
		 * @throws PersistenceException
		 */
		public void triggerRejectionMemoAccounting(RejectionMemoVO rejectionVO)
		throws SystemException,PersistenceException;
		  /**
	     * 
	     * @param filterVO
	     * @return
	     * @throws PersistenceException
	     * @throws SystemException
	     */
	    public  ArlInvoiceDetailsReportVO generateCN66InvoiceReport(AirlineCN66DetailsFilterVO filterVO)
		throws PersistenceException,SystemException;
	    /**
		 * @author a-2391
		 * @param companyCode
		 * @param airlineIdentifier
		 * @return
		 * For fetching Airline Details
		 */
		public AirlineVO findAirlineAddresss(String companyCode, int airlineIdentifier) throws PersistenceException,SystemException;
		/**
		 * to trigger Accept Button Accounting at despatch level
		 * @author A-3429
		 * @param rejectionVO
		 * @throws SystemException
		 * @throws PersistenceException
		 */
		public void triggerAcceptDSNAccounting(AirlineExceptionsVO airlineExceptionVO)
		throws SystemException,PersistenceException;
        
		/**
		 * 
		 * 	Method		:	MRADefaultsDAO.generateIsFile
		 *	Added by 	:	a-7794 on 24-July-2018
		 *	Author		:	@author A-7794
		 * 	Used for 	:	ICRD-265471
		 *	Parameters	:	@param fileFilterVO
		 *	Parameters	:	@throws SystemException
		 *	Parameters	:	@throws PersistenceException 
		 *	Return type	: 	Collection<SISMessageVO>
		 */
		public Collection<SISMessageVO> generateIsFile(MiscFileFilterVO fileFilterVO) throws SystemException, PersistenceException;

	    	/**
		    * 
			 * 	Method		:	withdrawMailBags
			 *	Added by 	:	A-8061 
			 * 	Used for 	:
			 *	Parameters	:	@return 
			 *	Return type	: 	void
			 * @param airlineCN66DetailsVOs 
			 * @throws BusinessDelegateException 
			 */
		public void withdrawMailBag(AirlineCN66DetailsVO airlineCN66DetailsVO)throws SystemException;


		/**
		 * 
		 * 	Method		:	MRAAirlineBillingDAO.downloadAttachment
		 *	Added by 	:	a-8061 on 29-Oct-2018
		 * 	Used for 	:	ICRD-265471
		 *	Parameters	:	@param paramSupportingDocumentFilterVO
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException
		 *	Parameters	:	@throws PersistenceException 
		 *	Return type	: 	SisSupportingDocumentDetailsVO
		 */
		  public  SisSupportingDocumentDetailsVO downloadAttachment(SupportingDocumentFilterVO paramSupportingDocumentFilterVO)
				    throws SystemException, PersistenceException;
		  /**
		   * 
		   * 	Method		:	MRAAirlineBillingDAO.findSupportingDocumentSerialNumber
		   *	Added by 	:	a-8061 on 29-Oct-2018
		   * 	Used for 	:	ICRD-265471
		   *	Parameters	:	@param sisSupportingDocumentDetailsVO
		   *	Parameters	:	@return
		   *	Parameters	:	@throws SystemException
		   *	Parameters	:	@throws PersistenceException 
		   *	Return type	: 	int
		   */
			public int findSupportingDocumentSerialNumber(
					SisSupportingDocumentDetailsVO sisSupportingDocumentDetailsVO)
					throws SystemException, PersistenceException;
		/**@author A-5526
		 * Method to list CN66 details
		 * @param mailSequenceNumber 
		 * 
		 * @param cn66FilterVo
		 * @return Collection<AirlineCN66DetailsVO>
		 * @throws SystemException
		 */
		public Collection<AirlineCN66DetailsVO> findCN66DetailsVOsForStatusChange(
				AirlineCN66DetailsFilterVO airlineCN66DetailsFilterVO, long mailSequenceNumber) throws SystemException;

		
}
