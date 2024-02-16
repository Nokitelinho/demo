/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.receivablemanagement.ReceivableManagementController.java
 *
 *	Created by	:	A-4809
 *	Created on	:	12-Nov-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.receivablemanagement;


import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.mra.MailTrackingMRABusinessException;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.GPABillingController;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.business.mail.mra.proxy.CRADefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.FrameworkLockProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MailTrackingDefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedCurrencyProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MRABatchUploadedVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MRASettlementBatchLockVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MailSettlementBatchFilterVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchFilterVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchSettlementDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.currency.vo.CurrencyValidationVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadErrorLogVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.MRAReceivableManagementDAO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.Advice;
import com.ibsplc.xibase.server.framework.interceptor.Phase;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.tx.Transaction;
import com.ibsplc.xibase.server.framework.persistence.tx.TransactionProvider;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.receivablemanagement.ReceivableManagementController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	12-Nov-2021	:	Draft
 */
@Module("mail")
@SubModule("mra")
public class ReceivableManagementController {


	private static final String MODULE_NAME = "mail.mra.receivablemanagement";

	private static final String CLASS_NAME = "ReceivableManagementController";

	/** The log. */
	private static final Log LOG = LogFactory.getLogger("MRA ReceivableManagement");

	private static final String DUPLICATE_EXISTS = "Duplicate Exists";
	
	private static final String MRA_BATCH_UPLOAD="MRABTCHUPL";
	private static final String MRA_BATCH_UPLOAD_ACTION="MRABTCHUPLACT";
	private static final String MRA_BATCH_UPLOAD_EXCEL="MRA BATCH UPLOAD EXCEL";
	private static final String FROM_CLIENT="FROM CLIENT";
	private static final String SCREEN_ID="mail.mra.defaults.ux.attachfile";
	private static final String OK="OK";
	private static final String FILE_NOT_UPLOADED_ERROR_CODE="Please Upload valid data File upload not successful";
	private static final String FILE_NOT_UPLOADED="File Upload not successful";
	private static final String COMPLETED="C";
	private static final String FAILED="F";
	private static final String MAIL_STATUS_ACC="ACC";
	private static final String MAIL_SOURCE_BTH="BTH";
	private static final String MRABTHSTL = "MRABTHSTL";
	private static final String PROCESSING_COMPLETED="PC";
	private static final String PROCESSED_WITH_ERROR="PE";
	private static final String BATCH_SETTLEMENT_SUCCESS="MRA Batch Settlement File Processed Successfully on ";
	private static final String BATCH_SETTLEMENT_FAILED="MRA Batch Settlement File Processing failed on ";
	private static final String AT=" at ";
	private static final String FOR_FILE=" for file: ";
	private static final String PAY_DATE_NOT_SAME="Payment date is not same as Settlement date";
	private static final String INVALID_MAILBAG="Invalid Mailbag/Mailbag is mandatory";
	private static final String CURRENCY_MANDATORY="Currency is mandatory";
	private static final String CURRENCY_INVALID="Invalid Currency";
	private static final String PA_CODE_INVALID="Invalid PA Code";
	private static final String MAILDATE_INVALID="Invalid Mail Date";
	private static final String PAYDATE_INVALID="Invalid Pay Date";
	private static final String PA_CODE_MANDATORY="PA Code Currency";
	private static final String BATCH_CURRENCY_NOT_SAME="Batch currency is not same as Uploaded settlement currency";
	private static final String BATCH_AMOUNT_NOT_MATCHING="Batch Amount doesn't match with the Settlement amounts uploaded";
	private static final String GPA_NOT_SAME="Settlement GPA Code is not same as uploaded Mailbag's GPA Code";
	private static final String PA_MISMATCH ="PA Code mentioned in Batch upload is not matching with Invoiced PA Code";
	private static final String INVOICE_NOT_PRESENT ="Invoice not present for the mailbag mentioned in batch upload";
	private static final String PARTIALLY_APPLIED = "PA";
	private static final String APPLIED = "AP";
	private static final String CREATEBATCH="CRTBTH";
	private static final String EDITBATCH="EDTBTH";
	private static final String BTHSTL_SAVE ="BTHSTL_SAVE";
	private static final String BTHSTL_UPDATE ="BTHSTL_UPDATE";
	private static final String BTHSTL_DELETE ="BTHSTL_DELETE";
	private static final String BTHSTL_EDIT ="BTHSTL_EDIT";
	private static final String CONTROLLER ="receivableManagementController";
	private static final String INVOICES ="invoices";
	private static final String CURRENCY ="currency";
	private static final String AMOUNT ="amount";
	private static final String CLEAR_BATCH ="Batch cleared and unapplied cash written off";
	private static final String BTHSTL_CLEARED ="BTHSTL_CLEAR";
	private static final String OVERRIDE_ROUNDING_VALUE="mailtracking.mra.overrideroundingvalue";
	
	/**
	 * 	Method		:	ReceivableManagementController.constructDAO
	 *	Added by 	:	A-4809 on 12-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	MRADefaultsDAO
	 */
	 private static MRAReceivableManagementDAO constructDAO()
			 throws SystemException,PersistenceException{

				EntityManager entityManager = PersistenceController.getEntityManager();
				return MRAReceivableManagementDAO.class.cast(entityManager.getQueryDAO(MODULE_NAME));

		}

	/**
	 *
	 * 	Method		:	ReceivableManagementController.listPaymentBatchDetails
	 *	Added by 	:	A-4809 on 12-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param paymentBatchFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	Page<PaymentBatchDetailsVO>
	 * @throws PersistenceException
	 */
	public Page<PaymentBatchDetailsVO> listPaymentBatchDetails(PaymentBatchFilterVO paymentBatchFilterVO)
			throws SystemException, PersistenceException{

		return constructDAO().listPaymentBatchDetails(paymentBatchFilterVO);

	}


    /**
	 *
	 * 	Method		:	ReceivableManagementController.uploadSettlementAsyc
	 *	Added by 	:	A-5219 on 09-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param batchSplitFilterVO
	 *	Parameters	:	@param invoiceTransactionLogVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 *	Return type	: 	void
	 */
	public void uploadPaymentBatchDetail(PaymentBatchFilterVO batchFilterVO, InvoiceTransactionLogVO invoiceTransactionLogVO)
			throws SystemException{

		try {
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			Collection<LockVO> lockvos = new ArrayList<>();
			MRASettlementBatchLockVO txLockVO = new MRASettlementBatchLockVO(MRA_BATCH_UPLOAD);
			txLockVO.setAction(MRA_BATCH_UPLOAD_ACTION);
			txLockVO.setCompanyCode(logonAttributes.getCompanyCode());
			txLockVO.setClientType(ClientType.APPLICATION);
			txLockVO.setStationCode(logonAttributes.getStationCode());
			txLockVO.setDescription(MRA_BATCH_UPLOAD_EXCEL);
			txLockVO.setRemarks(FROM_CLIENT);

			txLockVO.setScreenId(SCREEN_ID);
			lockvos.add(txLockVO);
			new FrameworkLockProxy().addLocks(lockvos);
			FileUploadFilterVO filterVO = new FileUploadFilterVO();
			filterVO.setCompanyCode(batchFilterVO.getCompanyCode());
			filterVO.setFileType(batchFilterVO.getFileType());
			filterVO.setProcessIdentifier(batchFilterVO.getProcessIdentifier());
			String status= new MRAGPABatchSettlement().loadMRASettlementBatchUploadDetails(filterVO);
			if(status != null && OK.equals(status)){
				mraBatchUploadDetails(batchFilterVO, invoiceTransactionLogVO);
			}else{
				MRABatchUploadedVO uploadedVO = new MRABatchUploadedVO();
				uploadedVO.setCompanyCode(batchFilterVO.getCompanyCode());
				Collection<MRABatchUploadedVO> vos = new  ArrayList<>();
				vos.add(uploadedVO);
				uploadedVO.setErrorCode(FILE_NOT_UPLOADED_ERROR_CODE);
				saveFileUploadError(vos,batchFilterVO.getProcessIdentifier());
				mraBatchSettlementTxnLog(FAILED, FILE_NOT_UPLOADED, invoiceTransactionLogVO,
						batchFilterVO.getFileName(),batchFilterVO.getProcessIdentifier());
			}
			releaseLocks(lockvos);
			
		} catch (SystemException | ProxyException ex) {
			LOG.log(Log.FINE, ex);
		}


	}
	
	/**
	 * 
	 * 	Method		:	ReceivableManagementController.releaseLocks
	 *	Added by 	:	A-5219 on 17-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param lockvos
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	private void releaseLocks(Collection<LockVO> lockvos) throws SystemException{
		try {
			new FrameworkLockProxy().releaseLocks(lockvos);
		} catch (ProxyException e) {
			LOG.log(Log.FINE, "***ProxyException releaseLocks***"+e);
		}
	}


	/**
	 *
	 * 	Method		:	ReceivableManagementController.mraBatchUploadDetails
	 *	Added by 	:	A-5219 on 15-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param batchSplitFilterVO
	 *	Parameters	:	@param invoiceTransactionLogVO
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
	public void mraBatchUploadDetails(PaymentBatchFilterVO batchFilterVO, InvoiceTransactionLogVO invoiceTransactionLogVO)
			throws SystemException {
		LOG.entering(CLASS_NAME, "mraBatchUploadDetails");
		try {
			PaymentBatchDetailsVO batchDetailsVO = getBatchHeaderAndUploadedData(batchFilterVO);
			boolean errorsExist = validateBatchUploadData(batchDetailsVO);
			if(!errorsExist){
				constructPaymentBatchDetailsVOS(batchDetailsVO);
				saveBatchSettlementDetails(batchDetailsVO,batchFilterVO);
				mraBatchSettlementTxnLog(COMPLETED, null, invoiceTransactionLogVO,
						batchFilterVO.getFileName(),batchFilterVO.getProcessIdentifier());
			}else{
				mraBatchSettlementTxnLog(FAILED, null, invoiceTransactionLogVO,
						batchFilterVO.getFileName(),batchFilterVO.getProcessIdentifier());
			}
		}catch(SystemException e){
			LOG.log(Log.FINE,e);
			TransactionProvider tm = PersistenceController.getTransactionProvider();
			Transaction tx = tm.getNewTransaction(true);
			mraBatchSettlementTxnLog(FAILED, e.getMessage(), invoiceTransactionLogVO,
					batchFilterVO.getFileName(),batchFilterVO.getProcessIdentifier());
			tx.commit();
		}
		LOG.exiting(CLASS_NAME, "mraBatchUploadDetails");
	}



	/**
	 *
	 * 	Method		:	ReceivableManagementController.getBatchHeaderVO
	 *	Added by 	:	A-5219 on 15-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param batchSplitFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	MRASettlementBatchHeaderVO
	 */
	private PaymentBatchDetailsVO getBatchHeaderAndUploadedData(PaymentBatchFilterVO batchFilterVO)
			throws SystemException{
		Page<PaymentBatchDetailsVO> headerVOs = null;
		PaymentBatchDetailsVO headerVO = null;
		try{
			headerVOs = constructDAO().listPaymentBatchDetails(batchFilterVO);
		}catch(PersistenceException e){
			LOG.log(Log.SEVERE, e);
		}
		if(headerVOs != null && !headerVOs.isEmpty() && headerVOs.iterator().next() != null){
			headerVO = headerVOs.iterator().next();
			Collection<MRABatchUploadedVO> uploadedVOs = new MRAGPABatchSettlement().findBatchUploadDetails(batchFilterVO);
			headerVO.setProcessID(batchFilterVO.getProcessIdentifier());
			headerVO.setFileName(batchFilterVO.getFileName());
			headerVO.setBatchUploadedVOs(uploadedVOs);
		}
		return headerVO;
	}

	/**
	 *
	 * 	Method		:	ReceivableManagementController.validateBatchUploadData
	 *	Added by 	:	A-5219 on 12-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param vo
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	boolean
	 */
	public boolean validateBatchUploadData(PaymentBatchDetailsVO vo)
			throws SystemException{
		boolean isErrorExist = false;
		Money sumamount = null;
		if(vo.getBatchUploadedVOs() != null && !vo.getBatchUploadedVOs().isEmpty()){
			if(vo.getBatchCurrency()!=null){
				try {
					sumamount = CurrencyHelper.getMoney(vo.getBatchCurrency());
				} catch (CurrencyException e) {
					LOG.log(Log.SEVERE, e);
				}
			}
			for(MRABatchUploadedVO uploadedVO : vo.getBatchUploadedVOs()){
				if(uploadedVO.getCurrencyCode() == null || uploadedVO.getCurrencyCode().trim().length() != 3){
					uploadedVO.setErrorCode(CURRENCY_MANDATORY);
				}else{
					CurrencyValidationVO validationVO = null;
					try{
					validationVO = Proxy.getInstance().get(SharedCurrencyProxy.class).validateCurrency(vo.getCompanyCode(), uploadedVO.getCurrencyCode());
					 if(validationVO ==null){
							uploadedVO.setErrorCode(CURRENCY_INVALID);
					
					 }else if(validationVO.getCurrencyCode().equals(vo.getBatchCurrency())){
						Money payamount = null;
						payamount = CurrencyHelper.getMoney(vo.getBatchCurrency());
						payamount.setAmount(uploadedVO.getPayAmount());
						sumamount.setAmount(sumamount.getAmount()+payamount.getAmount());
					 }else if(validationVO != null && !validationVO.getCurrencyCode().equals(vo.getBatchCurrency())){
						uploadedVO.setErrorCode(BATCH_CURRENCY_NOT_SAME);
					 }
					}catch (CurrencyException | ProxyException e) {
						LOG.log(Log.SEVERE, e);
					}
				}
				if(uploadedVO.getPayDate() != null && vo.getBatchDate() != null
						&& !uploadedVO.getPayDate().toDisplayDateOnlyFormat().equals(vo.getBatchDate().toDisplayDateOnlyFormat())){
					uploadedVO.setErrorCode(PAY_DATE_NOT_SAME);
				}
				if(uploadedVO.getMailIdr() == null || uploadedVO.getMailIdr().length() != 29){
					uploadedVO.setErrorCode(INVALID_MAILBAG);
				}else{
					Collection<MailbagVO> vos = new ArrayList<>();
					MailbagVO mailVO = constructMailbagVO(uploadedVO.getMailIdr());
					mailVO.setCompanyCode(uploadedVO.getCompanyCode());
					vos.add(mailVO);
					boolean isValidMailbag = false;
					try {
						isValidMailbag = new MailTrackingDefaultsProxy().validateMailbag(vos);
					} catch (RemoteException | ServiceNotAccessibleException e) {
						LOG.log(Log.SEVERE, e);
					}
					if(!isValidMailbag){
						uploadedVO.setErrorCode(INVALID_MAILBAG);
					}
				}
				if(uploadedVO.getMailDate() == null){
					uploadedVO.setErrorCode(MAILDATE_INVALID);
				}
				if(uploadedVO.getPaCode() == null){
					uploadedVO.setErrorCode(PA_CODE_MANDATORY);
				}else{
					PostalAdministrationVO adminVO = null;
					try{
						adminVO = new MailTrackingDefaultsProxy().findPACode(vo.getCompanyCode(), uploadedVO.getPaCode());
					}catch(SystemException | ProxyException  e){
						LOG.log(Log.FINE, e);
					}
					if(adminVO == null){
						uploadedVO.setErrorCode(PA_CODE_INVALID);
					}
				}
				if(!vo.getPaCode().equals(uploadedVO.getPaCode())){
					uploadedVO.setErrorCode(GPA_NOT_SAME);
				}
				if(uploadedVO.getPayDate() == null){
					uploadedVO.setErrorCode(PAYDATE_INVALID);
				}
				if(uploadedVO.getErrorCode() != null && uploadedVO.getErrorCode().trim().length() >0 && uploadedVO.isErrorsExist()){
					uploadedVO.setErrorsExist(true);
					isErrorExist = true;
				}
			}
			int roundingValue=roundingSysParVal();	
			if(sumamount!=null){
				double sumAmountDecimal = new GPABillingController().getScaledValue(sumamount.getAmount(), roundingValue);
				double batchAmountDecimal=new GPABillingController().getScaledValue(vo.getBatchAmount().getAmount(), roundingValue);
		       if(sumAmountDecimal!=batchAmountDecimal){
				MRABatchUploadedVO uploadVO = new MRABatchUploadedVO();
				uploadVO.setCompanyCode(vo.getCompanyCode());
				uploadVO.setFileName(vo.getFileName());
				uploadVO.setErrorCode(BATCH_AMOUNT_NOT_MATCHING);
				vo.getBatchUploadedVOs().add(uploadVO);
				isErrorExist = true;
			}
		}
		}
		if(isErrorExist){
			saveFileUploadError(vo.getBatchUploadedVOs(),vo.getProcessID()); 
		}
		return isErrorExist;
	}

		private int roundingSysParVal() throws SystemException {
			Collection<String> systemParameterCodes = new ArrayList<>();
			systemParameterCodes.add(OVERRIDE_ROUNDING_VALUE);
			Map<String, String> systemParamcodeCheck = null;
			try {
				systemParamcodeCheck = Proxy.getInstance().get(SharedDefaultsProxy.class).findSystemParameterByCodes(systemParameterCodes);
						
			} catch (ProxyException e) {
				LOG.log(Log.FINE, e);
			}
			int roundingValue = 0;
			if (systemParamcodeCheck != null) {
				String systemParamcode = systemParamcodeCheck.get(OVERRIDE_ROUNDING_VALUE);
				roundingValue = Integer.parseInt(systemParamcode);
			}
			return roundingValue;
		}


	/**
	 *
	 * 	Method		:	ReceivableManagementController.validateExcelFileName
	 *	Added by 	:	A-5219 on 14-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@throws MailTrackingMRABusinessException
	 *	Return type	: 	void
	 */
	public int validateExcelFileName(PaymentBatchFilterVO filterVO)
			throws SystemException {
		int count = 0;
		try {
			count = new MRAGPABatchSettlement().validateFileName(filterVO);
		} catch (SystemException e) {
			LOG.log(Log.FINE,e);
		}
		return count;
	}

	/**
	 * 
	 * 	Method		:	ReceivableManagementController.mraBatchSettlementTxnLog
	 *	Added by 	:	A-5219 on 07-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param status
	 *	Parameters	:	@param exception
	 *	Parameters	:	@param invoiceTransactionLogVO
	 *	Parameters	:	@param fileName
	 *	Parameters	:	@param processIdr
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void mraBatchSettlementTxnLog(String status, String exception,
			InvoiceTransactionLogVO invoiceTransactionLogVO, String fileName, String processIdr) throws SystemException {
		FileUploadFilterVO fileUploadFilterVO = new FileUploadFilterVO();
		fileUploadFilterVO.setCompanyCode(invoiceTransactionLogVO.getCompanyCode());
		fileUploadFilterVO.setProcessIdentifier(processIdr);
		if (COMPLETED.equals(status)) {
			StringBuilder remarks = new StringBuilder(BATCH_SETTLEMENT_SUCCESS);
			remarks.append(invoiceTransactionLogVO.getTransactionDate().toDisplayDateOnlyFormat());
			remarks.append(AT);
			remarks.append(invoiceTransactionLogVO.getTransactionTime().toDisplayTimeOnlyFormat());
			remarks.append(FOR_FILE);
			remarks.append(fileName);
			invoiceTransactionLogVO.setRemarks(remarks.toString());
			invoiceTransactionLogVO.setInvoiceGenerationStatus(COMPLETED);
			fileUploadFilterVO.setStatus(PROCESSING_COMPLETED);
		} else {
			StringBuilder remarks = new StringBuilder(BATCH_SETTLEMENT_FAILED);
			remarks.append(invoiceTransactionLogVO.getTransactionDate().toDisplayDateOnlyFormat());
			remarks.append(AT);
			remarks.append(invoiceTransactionLogVO.getTransactionTime().toDisplayTimeOnlyFormat());
			remarks.append(FOR_FILE);
			remarks.append(fileName);
			invoiceTransactionLogVO.setRemarks(remarks.toString());
			invoiceTransactionLogVO.setInvoiceGenerationStatus(FAILED);
			fileUploadFilterVO.setStatus(PROCESSED_WITH_ERROR);
		}
		try {
			new CRADefaultsProxy().updateTransactionandRemarks(invoiceTransactionLogVO);
			new SharedDefaultsProxy().updateSettlementFileUploadStatus(fileUploadFilterVO);
		} catch (ProxyException e) {
			LOG.log(Log.FINE, e);
		}
	}


	/**
	 * 
	 * 	Method		:	ReceivableManagementController.saveFileUploadError
	 *	Added by 	:	A-5219 on 07-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param uploadVOs
	 *	Parameters	:	@param processID
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	private void saveFileUploadError(Collection<MRABatchUploadedVO> uploadVOs, String processID) throws SystemException {
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();

		Collection<FileUploadErrorLogVO> errorLogVOs=new ArrayList<>();
		long i = 0;
		LocalDate date = null;
		for(MRABatchUploadedVO uploadVO : uploadVOs ){
		if(uploadVO.getErrorCode() != null && uploadVO.getErrorCode().trim().length() > 0){
		FileUploadErrorLogVO errorLogVO = new FileUploadErrorLogVO();
		errorLogVO.setCompanyCode(uploadVO.getCompanyCode());
		errorLogVO.setProcessIdentifier(processID);
		errorLogVO.setFileName(uploadVO.getFileName());
		errorLogVO.setFileType(MRABTHSTL);
		errorLogVO.setLineNumber(i+2);
		errorLogVO.setErrorCode(uploadVO.getErrorCode());
		errorLogVO.setUpdatedUser(logonAttributes.getUserId());
		date = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		errorLogVO.setUpdatedTime(date);
		errorLogVOs.add(errorLogVO);
		i++;
		}
		}

		if (!errorLogVOs.isEmpty()) {
			new SharedDefaultsProxy().saveFileUploadExceptions(errorLogVOs);
		}

	}
	
	/**
	 * 
	 * 	Method		:	ReceivableManagementController.findSettlementBatches
	 *	Added by 	:	A-3429 on 18-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param paymentBatchFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<PaymentBatchDetailsVO>
	 * @throws PersistenceException 
	 */
	public Collection<PaymentBatchDetailsVO> findSettlementBatches(PaymentBatchFilterVO paymentBatchFilterVO)
			throws SystemException, PersistenceException{
		
		return constructDAO().findSettlementBatches(paymentBatchFilterVO);
		
	}
	
	/**
	 * 
	 * 	Method		:	ReceivableManagementController.findSettlementBatchDetails
	 *	Added by 	:	A-3429 on 18-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param MailSettlementBatchFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Page<PaymentBatchSettlementDetailsVO>
	 * @throws PersistenceException 
	 */
	public Page<PaymentBatchSettlementDetailsVO> findSettlementBatchDetails(MailSettlementBatchFilterVO mailSettlementBatchFilterVO)
			throws SystemException, PersistenceException{
		
		return constructDAO().findSettlementBatchDetails(mailSettlementBatchFilterVO);
		
	}   

/**
 * @author A-8331
 * @param advancePaymentVO
 * @throws SystemException
 * @throws FinderException
 */
	@Advice(name = "mail.mra.triggerAccountingAtBatchLevel" , phase=Phase.POST_INVOKE)
	public void saveAdvancePaymentDetails(PaymentBatchDetailsVO advancePaymentVO) throws SystemException, FinderException {
		
        MRAGPABatchSettlement mraGPABatchSettlement= null;
		StringBuilder str = new StringBuilder();
		StringBuilder dup = new StringBuilder();
		
	String key = 	str.append(advancePaymentVO.getBatchID()).append(advancePaymentVO.getPaCode()).append(advancePaymentVO.getBatchDate().toDisplayDateOnlyFormat()).toString();
		mraGPABatchSettlement= MRAGPABatchSettlement.find(advancePaymentVO.getCompanyCode(),
					advancePaymentVO.getPaCode(), advancePaymentVO.getBatchID()
					);
		if(mraGPABatchSettlement!=null){
		String dupKey = dup.append(mraGPABatchSettlement.getmRAGPABatchSettlementPk().getBatchId()).append(mraGPABatchSettlement.getmRAGPABatchSettlementPk().getPoaCode()).
				append(  new SimpleDateFormat("dd-MMM-yyyy").format(mraGPABatchSettlement.getSettlementDate().getTime()) ).toString();
		
			if (dupKey.equals(key)){
			throw new SystemException(DUPLICATE_EXISTS);
			
		}
		
		}
		
			new MRAGPABatchSettlement(advancePaymentVO);
			advancePaymentVO.setAccSource(CREATEBATCH);
			ReceivableManagementController receivableManagementController = (ReceivableManagementController)SpringAdapter.getInstance().getBean(CONTROLLER);
			receivableManagementController.auditBatchSettlement(advancePaymentVO,BTHSTL_SAVE);
	
		}
		
	
	/**
	 *
	 * 	Method		:	ReceivableManagementController.saveBatchSettlementDetails
	 *	Added by 	:	A-5219 on 15-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param batchDetailsVO
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
	public void saveBatchSettlementDetails(PaymentBatchDetailsVO batchDetailsVO,PaymentBatchFilterVO filterVO) throws SystemException{
		MRAGPABatchSettlementPK pk = new MRAGPABatchSettlementPK();
		pk.setCompanyCode(batchDetailsVO.getCompanyCode());
		pk.setBatchId(batchDetailsVO.getBatchID());
		pk.setBatchSequenceNumber(batchDetailsVO.getBatchSequenceNum());
		pk.setPoaCode(batchDetailsVO.getPaCode());
		MRAGPABatchSettlement batchSettlement = null;
		try{
			batchSettlement = MRAGPABatchSettlement.find(pk);
			batchSettlement.setProcessId(filterVO.getProcessIdentifier());
			batchSettlement.setFileName(filterVO.getFileName());
			batchSettlement.setFileType(filterVO.getFileType());
			batchSettlement.setRecordCount(batchDetailsVO.getPaymentBatchSettlementDetails().size());
			for(PaymentBatchSettlementDetailsVO vo : batchDetailsVO.getPaymentBatchSettlementDetails()){
				new MRAGPABatchSettlementDetail(vo);
			}
		}catch(FinderException | SystemException e){
			LOG.log(Log.SEVERE, e);
		}
	}

	/**
	 * 
	 * 	Method		:	ReceivableManagementController.constructPaymentBatchDetailsVOS
	 *	Added by 	:	A-5219 on 07-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param batchDetailsVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void constructPaymentBatchDetailsVOS(PaymentBatchDetailsVO batchDetailsVO)
		throws SystemException{
		Collection<PaymentBatchSettlementDetailsVO> vos = new ArrayList<>();
		for(MRABatchUploadedVO vo : batchDetailsVO.getBatchUploadedVOs()){
			PaymentBatchSettlementDetailsVO detailVO = new PaymentBatchSettlementDetailsVO();
			detailVO.setCompanyCode(batchDetailsVO.getCompanyCode());
			detailVO.setBatchID(batchDetailsVO.getBatchID());
			detailVO.setBatchSequenceNum(batchDetailsVO.getBatchSequenceNum());
			detailVO.setPaCode(vo.getPaCode()); 
			try {
				detailVO.setMailSeqNum(getMailBagSequenceNumber(vo,batchDetailsVO));
			} catch (PersistenceException e1 ) {
				LOG.log(Log.FINE, e1);
			}
			detailVO.setMailbagId(vo.getMailIdr());
			detailVO.setPayDate(vo.getPayDate());
			detailVO.setMailbagDate(vo.getMailDate());
			detailVO.setConsignmentDocNum(vo.getConsignmentDocNum());
			detailVO.setCurrencyCode(vo.getCurrencyCode());
			detailVO.setSettlementDate(batchDetailsVO.getBatchDate());
			PostalAdministrationVO adminVO = null;
			try{
				adminVO = new MailTrackingDefaultsProxy().findPACode(vo.getCompanyCode(), vo.getPaCode());
			}catch(SystemException | ProxyException  e){
				LOG.log(Log.FINE, e);
			} 
			Money payamount = null;
			try {
				payamount = CurrencyHelper.getMoney(vo.getCurrencyCode());
				payamount.setAmount(vo.getPayAmount());
			} catch (CurrencyException e) {
				LOG.log(Log.SEVERE, e);
			}
			detailVO.setPaidAmount(payamount);
			detailVO.setUnappliedAmount(payamount);
			if(adminVO!=null){
			detailVO.setAccountNumber(adminVO.getAccNum());
			}
			vos.add(detailVO);
		}
		batchDetailsVO.setPaymentBatchSettlementDetails(vos);
	}

	private long getMailBagSequenceNumber(MRABatchUploadedVO vo, PaymentBatchDetailsVO paymentBatchDetailsVO)	throws SystemException, PersistenceException{
	long mailSeqNum = 0;
	mailSeqNum= constructDAO().findMailBagSequenceNumberFromMailIdr(vo.getMailIdr(),  paymentBatchDetailsVO.getCompanyCode());
	if(mailSeqNum == 0){
		MailbagVO mailbagVO = constructMailbagVO(vo.getMailIdr());
		mailbagVO.setScannedDate(paymentBatchDetailsVO.getBatchDate());
		mailbagVO.setCompanyCode(paymentBatchDetailsVO.getCompanyCode());
		try {
		mailSeqNum = Proxy.getInstance().get(MailTrackingDefaultsProxy.class).insertMailbagAndHistory(mailbagVO);
		}catch(RemoteException | ServiceNotAccessibleException |SystemException e){
			LOG.log(Log.SEVERE, e);
		}
	}
	return mailSeqNum;
	}

	public void triggerAccountingAtBatchLevel(PaymentBatchDetailsVO advancePaymentVO) throws SystemException {
		MRAReceivableManagementDAO mraReceivableManagementDAO = null;
		
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraReceivableManagementDAO = MRAReceivableManagementDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			long btchseqnum= mraReceivableManagementDAO.findBatchSequenceNumber(advancePaymentVO.getBatchID(),advancePaymentVO.getCompanyCode());
			advancePaymentVO.setBatchSequenceNum(btchseqnum);
			mraReceivableManagementDAO
			.triggerAccountingAtBatchLevel(advancePaymentVO);
		} catch (PersistenceException persistenceException) {
			LOG.log(Log.FINE, persistenceException);
			
		}
		
	}
	
	/**
	 * 
	 * 	Method		:	ReceivableManagementController.constructMailbagVO
	 *	Added by 	:	A-5219 on 07-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param mailBagId
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	MailbagVO
	 */
	public MailbagVO constructMailbagVO(String mailBagId) throws SystemException{
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();

		MailbagVO mailVO = new MailbagVO();
		mailVO.setVolume(new Measure(UnitConstants.VOLUME,0.0,0,UnitConstants.VOLUME_UNIT_CUBIC_FOOT));
		mailVO.setScannedPort(logonAttributes.getStationCode());
		mailVO.setMailbagId(mailBagId); 
		mailVO.setDespatchId(mailBagId.substring(0, 20));
		mailVO.setOoe(mailBagId.substring(0, 6));
		mailVO.setDoe(mailBagId.substring(6, 12));
		mailVO.setMailCategoryCode(mailBagId.substring(12, 13));
		mailVO.setMailSubclass(mailBagId.substring(13, 15));
		mailVO.setMailClass(mailVO.getMailSubclass().substring(0, 1));
		mailVO.setYear(Integer.parseInt(mailBagId.substring(15, 16)));
		mailVO.setDespatchSerialNumber(mailBagId.substring(16, 20));
		mailVO.setReceptacleSerialNumber(mailBagId.substring(20, 23));
		mailVO.setHighestNumberedReceptacle(mailBagId.substring(23, 24));
		mailVO.setRegisteredOrInsuredIndicator(mailBagId.substring(24,
				25));
		double displayStrWt = Double.parseDouble(mailBagId.substring(25, 29)) / 10;//added by A-7371
		Measure strWt = new Measure(UnitConstants.MAIL_WGT, displayStrWt);
		mailVO.setWeight(strWt); 
		mailVO.setStrWeight(strWt);
		mailVO.setLatestStatus(MAIL_STATUS_ACC);
		mailVO.setMailStatus(MAIL_STATUS_ACC);
		mailVO.setMailSource(MAIL_SOURCE_BTH);
		mailVO.setScannedUser(logonAttributes.getUserId());
		mailVO.setConsignmentSequenceNumber(-1);
		return mailVO;
	}
	
	/**
	 * 	Method		:	ReceivableManagementController.deletePaymentBatchAttachment
	 *	Added by 	:	A-4809 on 02-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param batchDetailsVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 * @throws PersistenceException 
	 */
	public void deletePaymentBatchAttachment(PaymentBatchDetailsVO batchDetailsVO) 
			throws SystemException, PersistenceException{
		LOG.entering(CLASS_NAME, "deletePaymentBatchAttachment");
		constructDAO().deletePaymentBatchAttachment(batchDetailsVO);
		LOG.exiting(CLASS_NAME, "deletePaymentBatchAttachment");
	}
	
	
	/** 
	 * 
	 * 	Method		:	ReceivableManagementController.getMailSequenceNumber
	 *	Added by 	:	A-5219 on 07-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param vo
	 *	Parameters	:	@param paymentBatchDetailsVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	long
	 */
	private long getMailSequenceNumber(MRABatchUploadedVO vo, PaymentBatchDetailsVO paymentBatchDetailsVO)
		throws SystemException{
		long mailSeqNum = 0;
		try{
			mailSeqNum = new MailTrackingDefaultsProxy().findMailBagSequenceNumberFromMailIdr(vo.getMailIdr(), paymentBatchDetailsVO.getCompanyCode());
			if(mailSeqNum == 0){
				MailbagVO mailbagVO = constructMailbagVO(vo.getMailIdr());
				mailbagVO.setScannedDate(paymentBatchDetailsVO.getBatchDate());
				mailbagVO.setCompanyCode(paymentBatchDetailsVO.getCompanyCode());
				mailSeqNum = new MailTrackingDefaultsProxy().insertMailbagAndHistory(mailbagVO);
			} 
		}catch(RemoteException | ServiceNotAccessibleException |ProxyException | SystemException e){
			LOG.log(Log.SEVERE, e);
		}
		return mailSeqNum;
	}

    /**
	 * @author A-8331
	 * @param advancePaymentVO
	 * @throws SystemException
	 * @throws CreateException
	 * @throws FinderException
	 */
	
	@Advice(name = "mail.mra.triggerReverseAccountingAtBatchLevel" , phase=Phase.POST_INVOKE)
	public void deletePaymentBatch(PaymentBatchDetailsVO advancePaymentVO)
	throws SystemException, FinderException
	 {
		try{

			 MRAGPABatchSettlement mraGPABatchSettlement= new MRAGPABatchSettlement();
			mraGPABatchSettlement.updateBatchStatus(advancePaymentVO);
			if(MRAConstantsVO.BATCH_STATUS_PARTIALAPP.equals(advancePaymentVO.getBatchStatus()) || 
					MRAConstantsVO.BATCH_STATUS_APPLIED.equals(advancePaymentVO.getBatchStatus())	)
			{
			updateSettlementdetails(advancePaymentVO);
			}
			ReceivableManagementController receivableManagementController = (ReceivableManagementController)SpringAdapter.getInstance().getBean(CONTROLLER);
			receivableManagementController.auditBatchSettlement(advancePaymentVO,BTHSTL_DELETE);
           
		
	} catch (SystemException e) {
		 LOG.log(Log.SEVERE, e);
		
	}
	
	
	
	
	
	
	 }

	/**
	 * @author A-8331
	 * @param advancePaymentVO
	 * @throws SystemException
	 */
	public void updateSettlementdetails(PaymentBatchDetailsVO advancePaymentVO) throws SystemException {
		MRAReceivableManagementDAO mraReceivableManagementDAO = null;
		
		   try 
		    {
			EntityManager em = PersistenceController.getEntityManager();
			mraReceivableManagementDAO = MRAReceivableManagementDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			
			mraReceivableManagementDAO
			.updateSettlementdetails(advancePaymentVO);
		    } 
		    catch (PersistenceException persistenceException) {
			LOG.log(Log.FINE, persistenceException);
			
		}
		   
	}
/** 
 * @author A-8331  
 * @param advancePaymentVO
 * @throws SystemException
 */
	public void triggerReverseAccountingAtBatchLevel(PaymentBatchDetailsVO advancePaymentVO) throws SystemException {
      MRAReceivableManagementDAO mraReceivableManagementDAO = null;
		
		   try 
		    {
			EntityManager em = PersistenceController.getEntityManager();
			mraReceivableManagementDAO = MRAReceivableManagementDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			
			mraReceivableManagementDAO
			.triggerReverseAccountingAtBatchLevel(advancePaymentVO);
		    } 
		    catch (PersistenceException persistenceException) {
			LOG.log(Log.FINE, persistenceException);
			
		}
		
	}
	
	/**
	 *
	 * 	Method		:	ReceivableManagementController.mraBatchSettlementJob
	 *	Added by 	:	A-5219 on 07-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
	public void mraBatchSettlementJob(String companyCode)
			throws SystemException{
		LOG.entering(CLASS_NAME, "mraBatchSettlementJob");
		String status = "";
		Collection<InvoiceSettlementVO> mailSettlementVOs = null;
		Map<String,Object> auditData = new HashMap();
		try{
			mailSettlementVOs = constructDAO().findSettlementBatchDetails(companyCode);
			updateReasonForUnMatchedBatchSettlements(companyCode);
			if(mailSettlementVOs != null && !mailSettlementVOs.isEmpty()){
				status = new GPABillingController().processSettlementBatchDetails(mailSettlementVOs);
				if(MailConstantsVO.OK_STATUS.equals(status)){
					for(InvoiceSettlementVO vo: mailSettlementVOs){
						auditData = prepareAuditData(mailSettlementVOs);
						updateBatchSettlementDetails(vo);
						updateBatchSettlement(vo);
						
					}
			
					mraBatchSettlementJobAudit(auditData,companyCode);
					
					
				}
			}
		}catch(SystemException | PersistenceException |  MailTrackingMRABusinessException e){
			LOG.log(Log.SEVERE, e);
		}


	}


	/**
	 *
	 * 	Method		:	ReceivableManagementController.updateBatchSettlementDetails
	 *	Added by 	:	A-5219 on 10-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param vo
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
	private void updateBatchSettlementDetails(InvoiceSettlementVO vo)
		throws SystemException{
		MRAGPABatchSettlementDetailPK pk = null;
		try{
			pk = new MRAGPABatchSettlementDetailPK();
			pk.setCompanyCode(vo.getCompanyCode());
			pk.setBatchId(vo.getSettlementId());
			pk.setGpaCode(vo.getGpaCode());
			pk.setBatchSequenceNumber(vo.getBatchSeqNumber());
			pk.setMailSeqNum(vo.getMailsequenceNum());
			MRAGPABatchSettlementDetail details = MRAGPABatchSettlementDetail.find(pk);
			if(details != null){
				if(!vo.isGpaMismatch() && !vo.isInvoiceNotPresent()){
					details.setInvoiceNumber(vo.getInvoiceNumber());
					details.setAppliedAmount(details.getUnAppliedAmount());
					details.setUnAppliedAmount(0);
					details.setRsn(null);
				}else{
					if(vo.isInvoiceNotPresent()){
						details.setRsn(INVOICE_NOT_PRESENT); 
					}else if(vo.isGpaMismatch()){
						details.setRsn(PA_MISMATCH);
					}
				}
			}
		}catch(FinderException | SystemException e){
			LOG.log(Log.SEVERE, e);
		}
	}


	/**
	 *
	 * 	Method		:	ReceivableManagementController.updateBatchSettlement
	 *	Added by 	:	A-5219 on 10-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param vo
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
	private void updateBatchSettlement(InvoiceSettlementVO vo)
			throws SystemException{

		MRAGPABatchSettlementPK pk = null;
		try{
			pk = new MRAGPABatchSettlementPK();
			pk.setCompanyCode(vo.getCompanyCode());
			pk.setPoaCode(vo.getGpaCode());
			pk.setBatchId(vo.getSettlementId());
			pk.setBatchSequenceNumber(vo.getBatchSeqNumber());
			MRAGPABatchSettlement settlement = MRAGPABatchSettlement.find(pk);
				if(settlement != null &&settlement.getmRAGPABatchSettlementPk().getBatchId().equals(vo.getSettlementId())) {
				settlement.setAppliedAmount(vo.getTotalBatchAmountApplied());
				settlement.setUnAppliedAmount(settlement.getTotalBatchAmount()-vo.getTotalBatchAmountApplied());
				if(settlement.getTotalBatchAmount() ==settlement.getAppliedAmount()){
					settlement.setBatchStatus(APPLIED);
				}else{
					settlement.setBatchStatus(PARTIALLY_APPLIED);
				}
			}
		}catch(FinderException | SystemException e){
			LOG.log(Log.SEVERE, e);
		}

	}
	
	/**
	 * 
	 * 	Method		:	ReceivableManagementController.updateReasonForUnMatchedBatchSettlements
	 *	Added by 	:	A-5219 on 12-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	private void updateReasonForUnMatchedBatchSettlements(String companyCode)
			throws SystemException{
		try{
			Collection<InvoiceSettlementVO> vos= constructDAO().findUnMatchedBatchSettlements(companyCode);
			if(vos != null && !vos.isEmpty()){
				for(InvoiceSettlementVO vo: vos){
					if(vo.isGpaMismatch() || vo.isInvoiceNotPresent()){
					updateBatchSettlementDetails(vo);
					}
				}
			}
		}catch(SystemException | PersistenceException e){
			LOG.log(Log.SEVERE, e);
		}
		
	}  	


		
	/**
	 * 	Method		:	ReceivableManagementController.updateBatchAmount
	 *	Added by 	:	A-4809 on 11-Jan-2022
	 * 	Used for 	:
	 *	Parameters	:	@param batchVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 * @throws PersistenceException 
	 */
	@Advice(name = "mail.mra.triggerAccountingAtBatchLevel" , phase=Phase.POST_INVOKE)
	public void updateBatchAmount(PaymentBatchDetailsVO batchVO) 
			throws SystemException{
		MRAGPABatchSettlementPK pk = null;
		try {
			long btchseqnum= constructDAO().findBatchSequenceNumber(batchVO.getBatchID(),batchVO.getCompanyCode());
		pk = new MRAGPABatchSettlementPK();
		pk.setCompanyCode(batchVO.getCompanyCode());
		pk.setPoaCode(batchVO.getPaCode());
		pk.setBatchId(batchVO.getBatchID());
		pk.setBatchSequenceNumber(btchseqnum);
		batchVO.setBatchSequenceNum(btchseqnum); 
			MRAGPABatchSettlement batch = MRAGPABatchSettlement.find(pk);
			batchVO.setCurrentBatchAmount(batch.getTotalBatchAmount());
			batch.setTotalBatchAmount(batchVO.getBatchAmount().getAmount());
			batch.setSettlementCurrencyCode(batchVO.getBatchCurrency());
			batch.setUnAppliedAmount(batchVO.getBatchAmount().getAmount()); 
			batch.setRemarks(batchVO.getRemarks()); 
		} catch (FinderException |PersistenceException e) {
			LOG.log(Log.SEVERE, e); 
		}
		batchVO.setAccSource(EDITBATCH);
		ReceivableManagementController receivableManagementController = (ReceivableManagementController)SpringAdapter.getInstance().getBean(CONTROLLER);
		receivableManagementController.auditBatchSettlement(batchVO,BTHSTL_EDIT);
	}
	
	/**
	 * Method ReceivableManagementController.auditBatchSettlement
	 * @author A-10647
	 * @param detailVO
	 * @param actionCode
	 * @throws SystemException
	 */

	@Advice(name = "mail.mra.auditBatchSettlement", phase = Phase.POST_INVOKE)
	public void auditBatchSettlement(PaymentBatchDetailsVO detailVO,String actionCode){
		LOG.entering(CLASS_NAME, "auditBatchSettlement");
		LOG.log(Log.FINE, "Auditing Batch settlement-", detailVO); 
		LOG.exiting(CLASS_NAME, "auditBatchSettlement"); 
		}
	/**
	 * 
	 * 	Method		:	ReceivableManagementController.mraBatchSettlementJobAudit
	 *	Added by 	:	A-10647 on 14-Jan-2022
	 * 	Used for 	:
	 *	Parameters	:	@param auditData
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void mraBatchSettlementJobAudit(Map <String,Object>auditData,String companyCode) throws SystemException{
		for (Map.Entry<String,Object> set : auditData.entrySet()) {
		    Map<String,Object> eachAuditData = (Map) set.getValue();
		    PaymentBatchDetailsVO detailVO = new PaymentBatchDetailsVO();
		    detailVO.setBatchID(set.getKey());
		    detailVO.setProcessedInvoice(eachAuditData.get(INVOICES).toString());
			detailVO.setAuditAmount((Double)eachAuditData.get(AMOUNT));
		    detailVO.setBatchCurrency(eachAuditData.get(CURRENCY).toString());
		    detailVO.setCompanyCode(companyCode);
		    detailVO.setSource("JOB"); 
		     
		    ReceivableManagementController receivableManagementController = (ReceivableManagementController)SpringAdapter.getInstance().getBean(CONTROLLER);
			receivableManagementController.auditBatchSettlement(detailVO,BTHSTL_UPDATE);  
		}
	}

		/**
		 * 	Method		:	ReceivableManagementController.prepareAuditData
		 *	Added by 	:	A-10647 on 14-Jan-2022
		 * 	Used for 	:
		 *	Parameters	:	@param mailSettlementVOs
		 *	Parameters	:	@return 
		 *	Return type	: 	Map<String,Object>
		 */
		private Map<String, Object> prepareAuditData(Collection<InvoiceSettlementVO> mailSettlementVOs) {
			Map<String,Object> auditData = new HashMap <>();
			for(InvoiceSettlementVO vo: mailSettlementVOs){
				String settlementId =vo.getSettlementId();
				String invoiceNumber = vo.getInvoiceNumber();
			 if(!auditData.containsKey(settlementId)){
				 HashMap <String,Object>auditValues = new HashMap<>();
					auditValues.put(INVOICES,invoiceNumber);
					auditValues.put(AMOUNT,vo.getTotalBatchAmountApplied());
					auditValues.put(CURRENCY, vo.getSettlementCurrencyCode()); 
					auditData.put(settlementId,auditValues);
			 }else{
				 HashMap  <String,Object>auditValue = (HashMap) auditData.get(settlementId);
				 String existingInvoice = auditValue.get(INVOICES).toString();
				 if(!existingInvoice.contains(invoiceNumber)){
				 auditValue.put(INVOICES, existingInvoice+" ,"+invoiceNumber);
				 }
				 auditData.put(settlementId, auditValue);
			 }
			}
			return auditData;
		}
		
		/**
		 * 
		 * 	Method		:	ReceivableManagementController.clearBatchDetails
		 *	Added by 	:	A-10647 on 27-Jan-2022
		 * 	Used for 	:
		 *	Parameters	:	@param batchVO
		 *	Parameters	:	@throws SystemException 
		 *	Return type	: 	void
		 */
		public void clearBatchDetails(PaymentBatchDetailsVO batchVO) 
				throws SystemException{
			MRAGPABatchSettlementPK pk = null;
			try {
			pk = new MRAGPABatchSettlementPK();
			pk.setCompanyCode(batchVO.getCompanyCode());
			pk.setPoaCode(batchVO.getPaCode());
			pk.setBatchId(batchVO.getBatchID());
			pk.setBatchSequenceNumber(batchVO.getBatchSequenceNum());
				MRAGPABatchSettlement batch = MRAGPABatchSettlement.find(pk);
				 double batchAmount = batch.getUnAppliedAmount();
					if(batch.getAppliedAmount()!=0){
						batch.setAppliedAmount(batch.getAppliedAmount()+batchVO.getAmountTobeApplied());
					}else{
					batch.setAppliedAmount(batchVO.getAmountTobeApplied());
					}
					double unAppliedAmount = batchAmount-batchVO.getAmountTobeApplied();
					batch.setUnAppliedAmount(unAppliedAmount);
					batch.setBatchStatus(MRAConstantsVO.BATCH_STATUS_CLEARED);
//					
			} catch (FinderException e) {
				LOG.log(Log.SEVERE, e); 
			}
			Collection<PaymentBatchSettlementDetailsVO> settlementDetail = batchVO.getPaymentBatchSettlementDetails();
			for(PaymentBatchSettlementDetailsVO detailVO:settlementDetail){
			MRAGPABatchSettlementDetailPK settlementPK =null;
			try{
				settlementPK = new MRAGPABatchSettlementDetailPK();	
			settlementPK.setBatchId(detailVO.getBatchID());
			settlementPK.setBatchSequenceNumber(detailVO.getBatchSequenceNum());
			settlementPK.setCompanyCode(detailVO.getCompanyCode());
			settlementPK.setGpaCode(detailVO.getPaCode());
			settlementPK.setMailSeqNum(detailVO.getMailSeqNum());
			MRAGPABatchSettlementDetail batchDetail = MRAGPABatchSettlementDetail.find(settlementPK);
			if(batchDetail!=null){
				batchDetail.setAppliedAmount(detailVO.getAppliedAmount().getAmount()+detailVO.getUnappliedAmount().getAmount());
				batchDetail.setUnAppliedAmount(0);
				batchDetail.setRemarks(CLEAR_BATCH);
			}
			 StringBuilder auditRemark = new StringBuilder();
			 auditRemark.append(" Cleared batch ").append(detailVO.getBatchID()).append(" for the date ").append(batchVO.getBatchDate().toDisplayDateOnlyFormat())
			 .append(" for mailbag ").append(detailVO.getMailbagId()) .append(" for  amount ").append(detailVO.getUnappliedAmount().getAmount());
			 batchVO.setAuditRemark(auditRemark.toString());
			} catch (FinderException e) {
				LOG.log(Log.SEVERE, e); 
			}
			ReceivableManagementController receivableManagementController = (ReceivableManagementController)SpringAdapter.getInstance().getBean(CONTROLLER);
			receivableManagementController.auditBatchSettlement(batchVO,BTHSTL_CLEARED);
			}
			triggerUnappliedCashClearanceAccounting(batchVO);
				}
		/**
		 * 
		 * 	Method		:	ReceivableManagementController.triggerUnappliedCashClearanceAccounting
		 *	Added by 	:	A-10647 on 31-Jan-2022
		 * 	Used for 	:
		 *	Parameters	:	@param batchVO 
		 *	Return type	: 	void
		 * @throws SystemException 
		 */
		public void triggerUnappliedCashClearanceAccounting(PaymentBatchDetailsVO batchVO) throws SystemException{
			MRAReceivableManagementDAO mraReceivableManagementDAO = null;
			Collection<PaymentBatchSettlementDetailsVO> settlementDetail = batchVO.getPaymentBatchSettlementDetails();
			for(PaymentBatchSettlementDetailsVO detailVO:settlementDetail){
			try {
				EntityManager em = PersistenceController.getEntityManager();
				mraReceivableManagementDAO = MRAReceivableManagementDAO.class.cast(em
						.getQueryDAO(MODULE_NAME));
				mraReceivableManagementDAO
				.triggerUnappliedCashClearanceAccounting(detailVO);			
			} catch (PersistenceException persistenceException) {
				LOG.log(Log.FINE, persistenceException);
			}
		}
		} 
		
}