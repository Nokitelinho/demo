/*
 * MRAGPAReportingDAO.java Created on Feb 22, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting;

import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
//import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicSummaryVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.MailInvoicMessageVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO;

/**
 * @author A-1945
 *
 */

/*
 *
 * Revision History
 * Version	 	Date      		    Author			Description
 * 0.1			Feb 22, 2007 	  	A-1945			First draft
 * 0.2          Feb 22, 2007        A-2280          Added method findClaimsDetails
 * 0.3          Mar 6 , 2007        A-2245          Added method printExceptionsReportAssigneeDetails
 * 0.4          Mar 19, 2007        A-2245          Added method printExceptionsReportAssigneeSummary
 * 0.5          Mar 20, 2007        A-2245          Added method printExceptionsReportDetails
 * 0.6          Mar 20, 2007        A-2245          Added method printExceptionsReportSummary
 * 0.7			Nov 20,2018			A-8464			Added method listInvoicDetails
 *
 */

public interface MRAGPAReportingDAO {

	/**
	 *
	 * @param gpaReportFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Page<GPAReportingDetailsVO> findGPAReportingDetails(
			GPAReportingFilterVO gpaReportFilterVO) throws SystemException,
			PersistenceException;
	/**
	 * @author A-2280
	 * @param gpaReportingFilterVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	Page<GPAReportingClaimDetailsVO> findClaimDetails(GPAReportingFilterVO gpaReportingFilterVO)
    throws PersistenceException,SystemException;

	/**
	 * @author a-2270
	 * @param gpaReportingFilterVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String processGpaReport(GPAReportingFilterVO gpaReportingFilterVO)
	          throws PersistenceException,SystemException;


	/**
	 * @author A-2245
	 * @param gpaReportingFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<GPAReportingClaimDetailsVO> printExceptionsReportAssigneeDetails(
				GPAReportingFilterVO gpaReportingFilterVO)
				throws PersistenceException,SystemException;

	/**
	 * @author A-2245
	 * @param gpaReportingFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<GPAReportingClaimDetailsVO> printExceptionsReportAssigneeSummary(
				GPAReportingFilterVO gpaReportingFilterVO)
				throws PersistenceException,SystemException;

	/**
	 * @author A-2245
	 * @param gpaReportingFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<GPAReportingClaimDetailsVO> printExceptionsReportDetails(
				GPAReportingFilterVO gpaReportingFilterVO)
				throws PersistenceException,SystemException;

	/**
	 * @author A-2245
	 * @param gpaReportingFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<GPAReportingClaimDetailsVO> printExceptionsReportSummary(
				GPAReportingFilterVO gpaReportingFilterVO)
				throws PersistenceException,SystemException;

	/**
	 * @author A-8464
	 * @param invoicFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<InvoicDetailsVO> listInvoicDetails(InvoicFilterVO invoicFilterVO)
				throws PersistenceException,SystemException;

	/**
	 * @author A-8464
	 */
	public void saveGroupRemarkDetails(InvoicFilterVO invoicFilterVO,String groupRemarksToSave)
			throws PersistenceException,SystemException;

	/**
	 * @author A-8464
	 */
	// Commenting as part of ICRD-319850
/*	public Page<InvoicSummaryVO> findInvoicLov(
			InvoicFilterVO invoicFilterVO) throws SystemException,
			PersistenceException;	*/
/**
	 * @author A-8527
	 * @param invoicFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<InvoicVO> listInvoic(InvoicFilterVO invoicFilterVO,int pageNumber)
				throws PersistenceException,SystemException;
	/**
	 * @author A-8527
	 * @param invoicFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<ClaimDetailsVO> listClaimDetails(InvoicFilterVO invoicFilterVO,int pageNumber)
				throws PersistenceException,SystemException;
	/**
	 * @author A-8527
	 * @param invoicFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Page<ClaimDetailsVO> listGenerateClaimDetails(InvoicFilterVO invoicFilterVO,int pageNumber)
				throws PersistenceException,SystemException;
	/**
	 * @author A-7371
	 * @param invoiceNumber
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public long findSerialNumberfromInvoic(String invoiceNumber, String companyCode)throws SystemException;

/**
	 *
	 * @param invoicVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public String processInvoic(InvoicVO invoicVO) throws PersistenceException,SystemException;

	/**
	 *
	 * @param invoicDetailsVOs
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public String reprocessInvoicMails(Collection<InvoicDetailsVO> invoicDetailsVOs) throws PersistenceException,SystemException;

	/**
	 *
	 * @param companyCode
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public int checkForProcessCount(String companyCode) throws PersistenceException,SystemException;
	/**
	 * @author A-7371
	 * @param mailInvoicMessageVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public String checkAutoProcessing(Collection<MailInvoicMessageVO> mailInvoicMessage )throws SystemException;
	
	/**
	 *
	 * @param invoicVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public String updateBatchNumForInvoic(InvoicVO invoicVO) throws PersistenceException,SystemException;
	/**
	 * @author A-7371
	 * @param mailInvoicMessage
	 * @throws SystemException
	 */
	public String  updateInvoicStatus(Collection<MailInvoicMessageVO> mailInvoicMessage)throws SystemException;
	
	/**
	 * 
	 * @param companyCode
	 * @param mailSeqnum
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<InvoicDetailsVO> findInvoicAndClaimDetails(String companyCode, long mailSeqnum)throws PersistenceException,SystemException; 

	/**
	 * 	Method		:	MRAGPAReportingDAO.saveClaimDetails
	 *	Added by 	:	A-4809 on May 28, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public String saveClaimDetails(InvoicFilterVO filterVO) throws PersistenceException,SystemException;

	/**
	 * 	Method		:	MRAGPAReportingDAO.findMailbagsForClaim
	 *	Added by 	:	A-4809 on Jun 3, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ClaimVO
	 */
	public ClaimVO findMailbagsForClaim(InvoicFilterVO filterVO) throws PersistenceException,SystemException;

   /**
	 * @author A-7371
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public Collection<ClaimDetailsVO> findMailBagsForMessageGeneration(String companyCode) throws SystemException;
/**
	 * 	Method		:	MRAGPAReportingDAO.findGeneratedResditMessages
	 *	Added by 	:	A-4809 on Jun 6, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ResditEventVO>
	 */
	public Collection<ResditEventVO> findGeneratedResditMessages(InvoicFilterVO filterVO) throws PersistenceException,SystemException;

	/**
	 * 
	 * 	Method		:	MRAGPAReportingDAO.findClaimMasterDetails
	 *	Added by 	:	A-8061 on 20-Jun-2019
	 * 	Used for 	:	ICRD-262451
	 *	Parameters	:	@param invoicFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Boolean
	 */
	  public String findClaimMasterDetails(InvoicFilterVO invoicFilterVO) throws PersistenceException,SystemException;

  /**
	 * Trigger Invoic accounting.
	 * @author A-7794
	 * @param detailsVO the details vo
	 * @return the string
	 * @throws PersistenceException the persistence exception
	 * @throws SystemException the system exception
	 */
	public String triggerInvoicAccounting(InvoicDetailsVO detailsVO)throws PersistenceException, SystemException;
	/**
	 * 
	 * 	Method		:	MRAGPAReportingDAO.findClaimReferenceNumber
	 *	Added by 	:	A-8061 on 27-Jun-2019
	 * 	Used for 	:
	 *	Parameters	:	@param invoicFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	String
	 */
	public String findClaimReferenceNumber(InvoicFilterVO invoicFilterVO)throws PersistenceException,SystemException;
	  
	public Collection<ClaimDetailsVO> listClaimDetails(InvoicFilterVO invoicFilterVO)
			throws PersistenceException,SystemException;
	
	/**
	 * 
	 * 	Method		:	MRAGPAReportingDAO.findInvoicsByFileName
	 *	Added by 	:	A-5219 on 17-Jun-2020
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param fileName
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	long[]
	 */
	public List<Long> findInvoicsByFileName(String companyCode, String fileName)
			throws PersistenceException,SystemException;

	public Collection<ClaimVO> findMailbagsForClaimForInternational(InvoicFilterVO filterVO) throws PersistenceException, SystemException ;
	 public int checkForRejectionMailbags(String companyCode,InvoicVO invoicVO) throws SystemException;
	public long findBatchNo(InvoicVO invoicVO) throws PersistenceException,SystemException;
	public String processInvoicFileFromJob(InvoicVO invoicVO) throws PersistenceException,SystemException;
	public String updateInvoicProcessingStatusFromJob(String companyCode) throws PersistenceException,SystemException;
}
