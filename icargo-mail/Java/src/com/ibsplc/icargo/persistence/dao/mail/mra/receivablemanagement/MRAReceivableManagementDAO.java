/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.MRAReceivableManagementDAO.java
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

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MRABatchUploadedVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MailSettlementBatchFilterVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchFilterVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchSettlementDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.MRAReceivableManagementDAO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	12-Nov-2021	:	Draft
 */
public interface MRAReceivableManagementDAO {

	public Page<PaymentBatchDetailsVO> listPaymentBatchDetails(PaymentBatchFilterVO paymentBatchFilterVO)
			throws SystemException, PersistenceException;


	/**
	 *
	 * 	Method		:	MRAReceivableManagementDAO.findBatchUploadDetails
	 *	Added by 	:	A-5219 on 12-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	Collection<MRABatchUploadedVO>
	 */
	public Collection<MRABatchUploadedVO> findBatchUploadDetails(PaymentBatchFilterVO filterVO) throws SystemException;

	/**
	 *
	 * 	Method		:	MRAReceivableManagementDAO.validateFileName
	 *	Added by 	:	A-5219 on 12-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	int
	 */
	public int validateFileName(PaymentBatchFilterVO filterVO)
			throws SystemException;
			
	public Collection<PaymentBatchDetailsVO> findSettlementBatches(PaymentBatchFilterVO paymentBatchFilterVO)
			throws SystemException, PersistenceException;
	
	public Page<PaymentBatchSettlementDetailsVO> findSettlementBatchDetails(MailSettlementBatchFilterVO mailSettlementBatchFilterVO)
			throws SystemException, PersistenceException;

	public String loadMRASettlementBatchUploadDetails(FileUploadFilterVO filterVO)throws PersistenceException,SystemException;	
	
 public void triggerAccountingAtBatchLevel(PaymentBatchDetailsVO advancePaymentVO) throws PersistenceException, SystemException;

	

	public long findBatchSequenceNumber(String batchID, String companyCode) throws SystemException;

	/**
	 * 	Method		:	MRAReceivableManagementDAO.deletePaymentBatchAttachment
	 *	Added by 	:	A-4809 on 02-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param batchDetailsVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	void
	 */
	public void deletePaymentBatchAttachment(PaymentBatchDetailsVO batchDetailsVO)
			throws SystemException, PersistenceException;
 public void triggerReverseAccountingAtBatchLevel(PaymentBatchDetailsVO advancePaymentVO) throws SystemException;


	public void updateSettlementdetails(PaymentBatchDetailsVO advancePaymentVO) throws SystemException;
	
	/**
	 *
	 * 	Method		:	MRAReceivableManagementDAO.findSettlementBatchDetails
	 *	Added by 	:	A-5219 on 09-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 *	Return type	: 	Collection<InvoiceSettlementVO>
	 */
	public Collection<InvoiceSettlementVO> findSettlementBatchDetails(String companyCode)
		throws SystemException, PersistenceException;

	
	/**
	 * 
	 * 	Method		:	MRAReceivableManagementDAO.updateReasonForUnMatchedBatchSettlements
	 *	Added by 	:	A-5219 on 12-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	void
	 */
	public Collection<InvoiceSettlementVO> findUnMatchedBatchSettlements(String companyCode) throws SystemException, PersistenceException;
	/**
	 * 
	 * 	Method		:	MRAReceivableManagementDAO.triggerUnappliedCashClearanceAccounting
	 *	Added by 	:	A-10647 on 27-Jan-2022
	 * 	Used for 	:
	 *	Parameters	:	@param detailVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */

	public void triggerUnappliedCashClearanceAccounting(PaymentBatchSettlementDetailsVO detailVO) throws SystemException;


	public long findMailBagSequenceNumberFromMailIdr(String mailIdr, String companyCode) throws SystemException,PersistenceException;
}
