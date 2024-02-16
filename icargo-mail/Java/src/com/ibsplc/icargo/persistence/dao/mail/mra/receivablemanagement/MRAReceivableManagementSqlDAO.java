/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.MRAReceivableManagementSqlDAO.java
 *
 *	Created by	:	A-4809
 *	Created on	:	12-Nov-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MRABatchUploadedVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MailSettlementBatchFilterVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchFilterVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchSettlementDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.AbstractQueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.Procedure;
import com.ibsplc.xibase.server.framework.persistence.query.Query;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.SqlType;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.MRAReceivableManagementSqlDAO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	12-Nov-2021	:	Draft
 */
public class MRAReceivableManagementSqlDAO extends AbstractQueryDAO implements MRAReceivableManagementDAO{

	private static final String CLASS_NAME="MRAReceivableManagementSqlDAOs";
	private static final Log LOG = LogFactory.getLogger("MAIL MRA ReceivableManagementSqlDAO ");
	private static final String MRA_RCVMNG_ROWNUM_RANK_QUERY="SELECT RESULT_TABLE.* ,ROW_NUMBER () OVER (ORDER BY NULL) AS RANK FROM(";
	private static final String MRA_RCVMNG_SUFFIX_QUERY = " )RESULT_TABLE";
	private static final String MRA_RCVMNG_LISTPAYBTHDTL="mailtracking.mra.receivablemanagement.listPaymentBatchDetails";
	private static final String FIND_BATCH_UPLOAD_DETAILS="mail.mra.receivablemanagement.findbatchuploaddetails";
	private static final String MRA_RCVBLEMANAGMENT_TRIGGERACCOUNTING = "mail.mra.receivablemanagement.triggeraccounting";
	private static final String FIND_BATCHSEQUENCE_NUMBER_FROM_BATCHIDR = "mail.mra.receivablemanagement.findbatchsequencenumber";
	private static final String FIND_MAILSEQUENCE_NUMBER = "mail.mra.receivablemanagement.findmailsequencenumber";
	
	private static final String FIND_FILE_COUNT="mail.mra.receivablemanagement.validateFileName";
	private static final String MRA_RCVMNG_LISTSTLBTHDTL="mailtracking.mra.receivablemanagement.listSettlementBatchDetails";
	private static final String MRA_SETTLEMENT_FILEUPLOAD = "mail.mra.receivablemanagement.loadMRASettlementBatchUploadIntoTable";
	private static final String MRA_RCVMNG_DELETEATTACHMENT="mail.mra.receivablemanagement.deleteattachment";
	private static final String MRA_RCVBLEMANAGMENT_TRIGGER_REVERSE_ACCOUNTING = "mail.mra.receivablemanagement.triggerreverseaccounting";
	
	private static final String TRIGGERREVERSALACCOUNTINGATBATCHLEVEL = "triggerReverseAccountingAtBatchLevel";
	private static final String MRA_RCVBLEMANAGMENT_UPDATE_SETTLEMENTDETAILS = "mail.mra.receivablemanagement.updatesettlementdetails";
	private static final String UPDATESETTLEMENTDETAILS = "UPDATESETTLEMENTDETAILS";
	private static final String FIND_BATCH_SETTLMENT_DETAILS= "mail.mra.receivablemanagement.findSettlementBatchDetails";
	private static final String FIND_UN_MATCHED_BATCH_SETTLMENT_DETAILS= "mail.mra.receivablemanagement.findUnmatchedSettlementBatchDetails";
	private static final String TRIGGERUNAPPLIEDCASHCLEARANCEACCOUNTING ="triggerUnappliedCashClearanceAccounting";
	private static final String MRA_RCVBLEMANAGMENT_TRIGGER_CASHCLEARANCE_ACCOUNTING="mail.mra.receivablemanagement.triggercashclearanceaccounting";
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.MRAReceivableManagementDAO#listPaymentBatchDetails(com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchFilterVO)
	 *	Added by 			: A-4809 on 12-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param paymentBatchFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	@Override
	public Page<PaymentBatchDetailsVO> listPaymentBatchDetails(PaymentBatchFilterVO paymentBatchFilterVO)
			throws SystemException, PersistenceException {
		StringBuilder rankQuery = new StringBuilder(MRA_RCVMNG_ROWNUM_RANK_QUERY);
		String baseQuery = getQueryManager().getNamedNativeQueryString(MRA_RCVMNG_LISTPAYBTHDTL);
		String fullQuery=rankQuery.append(baseQuery).toString();
		PageableNativeQuery<PaymentBatchDetailsVO> pgqry=new PaymentBatchFilterQuery(paymentBatchFilterVO.getDefaultPageSize(),paymentBatchFilterVO.getTotalRecordCount(),new PaymentBatchDetailsMapper(), fullQuery, paymentBatchFilterVO);
		pgqry.append(MRA_RCVMNG_SUFFIX_QUERY);
		return pgqry.getPage(paymentBatchFilterVO.getPageNumber());
	}


	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.MRAReceivableManagementDAO#findBatchUploadDetails(com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MRABatchSplitFilterVO)
	 *	Added by 			: A-5219 on 12-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 */
	public Collection<MRABatchUploadedVO> findBatchUploadDetails(PaymentBatchFilterVO filterVO)
			throws SystemException {
		LOG.entering(CLASS_NAME,"findBatchUploadDetails");
		int index = 0;
		Query query=getQueryManager().createNamedNativeQuery(FIND_BATCH_UPLOAD_DETAILS);
		query.setParameter(++index,filterVO.getProcessIdentifier());
		query.setParameter(++index, filterVO.getCompanyCode());
		return query.getResultList(new MRAExcelUploadMultiMapper());
	}


	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.MRAReceivableManagementDAO#validateFileName(com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MRABatchSplitFilterVO)
	 *	Added by 			: A-5219 on 12-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 */
	public int validateFileName(PaymentBatchFilterVO filterVO) throws SystemException {
		LOG.entering(CLASS_NAME, "validateFileName");
		int index = 0;
		Query query=getQueryManager().createNamedNativeQuery(FIND_FILE_COUNT);
		query.setParameter(++index, filterVO.getCompanyCode());
		query.setParameter(++index, filterVO.getFileName());
		Mapper<Integer> integerMapper = getIntMapper("FILCOUNT");
		return query.getSingleResult(integerMapper).intValue();
	}
	
	/**
	 * Overriding Method : @see
	 * com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.MRAReceivableManagementDAO#findSettlementBatches(com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchFilterVO)
	 * Added by : A-3429 on 18-Nov-2021 Used for : Parameters : 
	 * @param paymentBatchFilterVO 
	 * Parameters : @return Parameters : @throws
	 * SystemException Parameters : @throws PersistenceException
	 */

	@Override
	public Collection<PaymentBatchDetailsVO> findSettlementBatches(PaymentBatchFilterVO paymentBatchFilterVO)
			throws SystemException, PersistenceException {
		Collection<PaymentBatchDetailsVO> mailSettlementBatchVOs = null;
		Query query = getQueryManager().createNamedNativeQuery(MRA_RCVMNG_LISTPAYBTHDTL);
		int idx = 0;
		query.setParameter(++idx, paymentBatchFilterVO.getCompanyCode());
		if (paymentBatchFilterVO.getBatchFrom() != null && paymentBatchFilterVO.getBatchTo() != null) {
			if(isOracleDataSource()) {
			query.append("AND TO_NUMBER(TO_CHAR(STL.STLDAT,'YYYYMMDD')) BETWEEN ? AND ?");
			query.setParameter(++idx, paymentBatchFilterVO.getBatchFrom().toSqlDate().toString().replace("-", ""));
			query.setParameter(++idx, paymentBatchFilterVO.getBatchTo().toSqlDate().toString().replace("-", ""));
			}else {
			query.append("AND TO_NUMBER(TO_CHAR(STL.STLDAT,'YYYYMMDD')) BETWEEN ?  :: numeric AND ?  :: numeric ");
			query.setParameter(++idx, paymentBatchFilterVO.getBatchFrom().toSqlDate().toString().replace("-", ""));
			query.setParameter(++idx, paymentBatchFilterVO.getBatchTo().toSqlDate().toString().replace("-", ""));
			}
		}
		if (paymentBatchFilterVO.getBatchStatus() != null&&!paymentBatchFilterVO.getBatchStatus().isEmpty()) {
			query.append("AND STL.BTHSTA = ?");
			query.setParameter(++idx, paymentBatchFilterVO.getBatchStatus());
		}
		if (paymentBatchFilterVO.getBatchId() != null&&!paymentBatchFilterVO.getBatchId().isEmpty()) {
			query.append("AND STL.BTHSTLIDR = ?");
			query.setParameter(++idx, paymentBatchFilterVO.getBatchId());
		}
		mailSettlementBatchVOs = query.getResultList(new PaymentBatchDetailsMapper());

		return mailSettlementBatchVOs;
	}

	/**
	 * Overriding Method : @see
	 * com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.MRAReceivableManagementDAO#findSettlementBatchDetails(com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MailSettlementBatchFilterVO)
	 * Added by : A-3429 on 18-Nov-2021 Used for : 
	 * Parameters : @param MailSettlementBatchFilterVO 
	 * Parameters : @return Parameters : @throws
	 * SystemException Parameters : @throws PersistenceException
	 */
	public Page<PaymentBatchSettlementDetailsVO> findSettlementBatchDetails(
			MailSettlementBatchFilterVO mailSettlementBatchFilterVO) throws SystemException, PersistenceException {

		int index = 0;
		int defaultPageSize = 0;
		if (mailSettlementBatchFilterVO.getPageSize() != 0) {
			defaultPageSize = mailSettlementBatchFilterVO.getPageSize();
		}
		int totalRecords = mailSettlementBatchFilterVO.getTotalRecordCount();

		String baseQry = getQueryManager().getNamedNativeQueryString(MRA_RCVMNG_LISTSTLBTHDTL);

		StringBuilder rankQuery = new StringBuilder();

		rankQuery.append(MRA_RCVMNG_ROWNUM_RANK_QUERY);

		rankQuery.append(baseQry);

		PageableNativeQuery<PaymentBatchSettlementDetailsVO> qry = new PageableNativeQuery<>(
				defaultPageSize, totalRecords, rankQuery.toString(), new PaymentBatchSettlementDetailsMapper());
		qry.setParameter(++index, mailSettlementBatchFilterVO.getCompanyCode());
		qry.setParameter(++index, mailSettlementBatchFilterVO.getPaCode()); 
		qry.setParameter(++index, mailSettlementBatchFilterVO.getBatchSequenceNum());
		qry.setParameter(++index, mailSettlementBatchFilterVO.getBatchID());
		qry.append(MRA_RCVMNG_SUFFIX_QUERY);
		return qry.getPage(mailSettlementBatchFilterVO.getPageNumber());

	}
	
	
	@Override
	public String loadMRASettlementBatchUploadDetails(FileUploadFilterVO filterVO)
			throws PersistenceException, SystemException {
		LOG.entering(CLASS_NAME, "loadMRASettlementBatchUploadDetails");
		String processStatus = null;
		if (filterVO != null) {
			int index = 0;
			Procedure procedure = getQueryManager().createNamedNativeProcedure(MRA_SETTLEMENT_FILEUPLOAD);
			procedure.setSensitivity(true);
			procedure.setParameter(++index, filterVO.getCompanyCode());
			procedure.setParameter(++index, filterVO.getFileType());
			procedure.setParameter(++index, "F");
			procedure.setParameter(++index, filterVO.getProcessIdentifier());
			procedure.setParameter(++index, MRAConstantsVO.FLAG_NO);
			procedure.setOutParameter(++index, SqlType.STRING);
			procedure.setOutParameter(++index, SqlType.STRING);
			procedure.execute();
			processStatus = (String)procedure.getParameter(index);
		}
		LOG.log(Log.FINE, "ProcessStatus after executed Procedure -----> " + processStatus);
		LOG.exiting(CLASS_NAME, "loadMRASettlementBatchUploadIntoTable");
		return processStatus;
	}
/***
 * @author A-8331
 * Parameters : @param PaymentBatchDetailsVO 
 * Parameters : @return Parameters : @throws
 * SystemException Parameters : @throws PersistenceException
 */
public void triggerAccountingAtBatchLevel(PaymentBatchDetailsVO advancePaymentV) 
			throws PersistenceException, SystemException {
		LOG.entering(CLASS_NAME, "triggerAccountingAtBatchLevel");
				
				Procedure procedure = getQueryManager().createNamedNativeProcedure(
						MRA_RCVBLEMANAGMENT_TRIGGERACCOUNTING);
				procedure.setSensitivity(true);
				int index = 0;		
				procedure.setParameter(++index, advancePaymentV.getCompanyCode());
				procedure.setParameter(++index, advancePaymentV.getBatchID());
				procedure.setParameter(++index, advancePaymentV.getBatchSequenceNum());
				
				procedure.setParameter(++index, advancePaymentV.getPaCode());
				procedure.setParameter(++index, MRAConstantsVO.MRA_FUNPNT_UPC);
				procedure.setParameter(++index, MRAConstantsVO.SUBSYSTEM);
				procedure.setParameter(++index, advancePaymentV.getLstUpdatedUser());
				procedure.setParameter(++index, advancePaymentV.getLstUpdatedTime());
				procedure.setParameter(++index, advancePaymentV.getAccSource());
				procedure.setOutParameter(++index, SqlType.STRING);    
				procedure.execute();
				
			}
	
	
	public long findBatchSequenceNumber(String batchId, String companyCode) throws SystemException {
		LOG.entering(CLASS_NAME, "findBatchSequenceNumber");
	
		long batchSeqNumber = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_BATCHSEQUENCE_NUMBER_FROM_BATCHIDR);
		int indx = 0;
		
		query.setParameter(++indx, companyCode);
		query.setParameter(++indx, batchId);
		

		String seqNum = query.getSingleResult(getStringMapper("BTHSTLSEQNUM"));
		if (seqNum != null) {
			batchSeqNumber = Long.parseLong(seqNum);
		}
		
		return batchSeqNumber;


	}
	
/**
 * @author A-8331
 * Parameters : @param PaymentBatchDetailsVO 
 * Parameters : @return Parameters : @throws
 * SystemException Parameters : @throws PersistenceException
 */
	
public void triggerReverseAccountingAtBatchLevel(PaymentBatchDetailsVO advancePaymentV) throws SystemException {
		LOG.entering(CLASS_NAME, TRIGGERREVERSALACCOUNTINGATBATCHLEVEL);
				
				Procedure procedure = getQueryManager().createNamedNativeProcedure(
						MRA_RCVBLEMANAGMENT_TRIGGER_REVERSE_ACCOUNTING);
				procedure.setSensitivity(true);
				int index = 0;		
				procedure.setParameter(++index, advancePaymentV.getCompanyCode());
				procedure.setParameter(++index, advancePaymentV.getAccTxnIdr());
				procedure.setParameter(++index, MRAConstantsVO.MRA_FUNPNT_UPC);
				procedure.setParameter(++index, MRAConstantsVO.SUBSYSTEM);
				procedure.setParameter(++index, advancePaymentV.getLstUpdatedUser());
				procedure.setParameter(++index, advancePaymentV.getLstUpdatedTime());
				procedure.setOutParameter(++index, SqlType.STRING);    
				procedure.execute();
				LOG.exiting(CLASS_NAME, TRIGGERREVERSALACCOUNTINGATBATCHLEVEL);			
			}

/**
 * @author A-4809
 * Parameters : @param PaymentBatchDetailsVO 
 * Parameters : @return Parameters : @throws
 * SystemException Parameters : @throws PersistenceException
 */
	@Override
	public void deletePaymentBatchAttachment(PaymentBatchDetailsVO batchDetailsVO)
			throws SystemException, PersistenceException {
		LOG.entering(CLASS_NAME, "deletePaymentBatchAttachment");
		Procedure procedure = getQueryManager().createNamedNativeProcedure(MRA_RCVMNG_DELETEATTACHMENT);
		procedure.setSensitivity(true);
		int index = 0;	
		procedure.setParameter(++index, batchDetailsVO.getCompanyCode());
		procedure.setParameter(++index, batchDetailsVO.getBatchID());
		procedure.setParameter(++index, batchDetailsVO.getPaCode());  
		procedure.setParameter(++index, "MRABTHSTL");
		if(batchDetailsVO.getFileName()!=null && !batchDetailsVO.getFileName().isEmpty()){
		procedure.setParameter(++index, batchDetailsVO.getFileName()); 
		}else{
		procedure.setParameter(++index, ""); 	 
		}
		procedure.setParameter(++index, batchDetailsVO.getProcessID());
		procedure.setOutParameter(++index, SqlType.STRING);    
		procedure.execute();
	}  




	@Override
	public void updateSettlementdetails(PaymentBatchDetailsVO advancePaymentVO) throws SystemException {
		LOG.entering(CLASS_NAME, UPDATESETTLEMENTDETAILS);
		
		Procedure procedure = getQueryManager().createNamedNativeProcedure(
				MRA_RCVBLEMANAGMENT_UPDATE_SETTLEMENTDETAILS);
		procedure.setSensitivity(true);
		int index = 0;		
		procedure.setParameter(++index, advancePaymentVO.getCompanyCode());
		procedure.setParameter(++index, advancePaymentVO.getBatchID());
		procedure.setParameter(++index, advancePaymentVO.getPaCode());
		
		procedure.setParameter(++index, advancePaymentVO.getLstUpdatedUser());
		procedure.setParameter(++index, advancePaymentVO.getLstUpdatedTime());
		procedure.setOutParameter(++index, SqlType.STRING);    
		procedure.execute();
	}
	
	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.MRAReceivableManagementDAO#findSettlementBatchDetails(java.lang.String)
	 *	Added by 			: A-5219 on 09-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 */
	public Collection<InvoiceSettlementVO> findSettlementBatchDetails(String companyCode)
			throws SystemException{
		Collection<InvoiceSettlementVO > mailSettlementVOs = null;
		 String qryString = getQueryManager().getNamedNativeQueryString(
				FIND_BATCH_SETTLMENT_DETAILS);
		 String modifiedStr = null;
		 if(isOracleDataSource()) {
			 modifiedStr = "AND INVDAT > add_months(sysdate,-1*coalesce(to_number(par.parval),0))"	 ;
		 }else {
			 modifiedStr ="AND INVDAT > current_date-COALESCE(to_number(par.parval), 0) * INTERVAL '1 MONTH'";
		 }
		 qryString = String.format(qryString, modifiedStr);
	 	Query query = getQueryManager().createNativeQuery(qryString);
		int idx = 0;
		query.setParameter(++idx, companyCode);
		mailSettlementVOs = query
				.getResultList(new MRAGpaBatchSettlmentMapper());
		return mailSettlementVOs;
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.MRAReceivableManagementDAO#findUnMatchedBatchSettlements(java.lang.String)
	 *	Added by 			: A-5219 on 12-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 */
	public Collection<InvoiceSettlementVO> findUnMatchedBatchSettlements(String companyCode) 
			throws SystemException{
		LOG.entering(CLASS_NAME, "findUnMatchedBatchSettlements");
		int index = 0;
		Query query=getQueryManager().createNamedNativeQuery(FIND_UN_MATCHED_BATCH_SETTLMENT_DETAILS);
		query.setParameter(++index, companyCode);
		return query.getResultList(new MRABatchHeaderMapper());
	}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.MRAReceivableManagementDAO#triggerUnappliedCashClearanceAccounting(com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO)
	 *	Added by 			: A-10647 on 31-Jan-2022
	 * 	Used for 	:
	 *	Parameters	:	@param batchVO
	 *	Parameters	:	@throws SystemException
	 */
	public void triggerUnappliedCashClearanceAccounting(PaymentBatchSettlementDetailsVO detailVO) throws SystemException {
		LOG.entering(CLASS_NAME, TRIGGERUNAPPLIEDCASHCLEARANCEACCOUNTING);
				Procedure procedure = getQueryManager().createNamedNativeProcedure(
						MRA_RCVBLEMANAGMENT_TRIGGER_CASHCLEARANCE_ACCOUNTING);
				procedure.setSensitivity(true);
				int index = 0;		
				procedure.setParameter(++index, detailVO.getCompanyCode());
				procedure.setParameter(++index, detailVO.getBatchID());
				procedure.setParameter(++index, detailVO.getBatchSequenceNum());
				procedure.setParameter(++index, detailVO.getPaCode());
				procedure.setParameter(++index, detailVO.getMailSeqNum());
				procedure.setParameter(++index, MRAConstantsVO.MRA_FUNPNT_MCB);
				procedure.setParameter(++index, MRAConstantsVO.SUBSYSTEM);
				procedure.setParameter(++index, detailVO.getLstUpdatedUser());
				procedure.setParameter(++index, detailVO.getLstUpdatedTime()); 
				procedure.setOutParameter(++index, SqlType.STRING);    
				procedure.execute();
				LOG.exiting(CLASS_NAME, TRIGGERUNAPPLIEDCASHCLEARANCEACCOUNTING);			
	}


	@Override
	public long findMailBagSequenceNumberFromMailIdr(String mailIdr, String companyCode) throws SystemException , PersistenceException {
	LOG.entering(CLASS_NAME, "findBatchSequenceNumber");
			
			long sequenceNumber = 0;
			Query query = getQueryManager().createNamedNativeQuery(
					FIND_MAILSEQUENCE_NUMBER);
			int indx = 0;
			
			query.setParameter(++indx, mailIdr);
			query.setParameter(++indx, companyCode);
			

			String seqNum = query.getSingleResult(getStringMapper("malseqnum"));
			if (seqNum != null) {
				sequenceNumber = Long.parseLong(seqNum);
			}
			
			return sequenceNumber;
		}
}
