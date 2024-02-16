/*
 * EmbargoBI.java Created on Jul 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information
 * of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.admin.user.vo.UserRoleGroupDetailsVO;
import com.ibsplc.icargo.business.products.defaults.InvalidProductException;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoJobSchedulerVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoSearchVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ExceptionEmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ExceptionEmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.RECORefreshJobScheduleVO;
import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.business.shared.embargo.EmbargoBusinessException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.audit.AuditException;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * Business Interface for embargo module. This interface is realized by the
 * service layer
 *
 * @author A-1358
 *
 */
public interface EmbargoRulesBI {

	/**
	 * This method is used to create/modify embargo details. For create
	 * operationFlag is set as I and for modify, operationFlag is set as 'U'
	 *
	 * @param embargoVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	String saveEmbargoDetails(EmbargoRulesVO embargoVO) throws RemoteException,
			SystemException, EmbargoRulesBusinessException;

	/**
	 * This method is used to cancel an embargo.
	 *
	 * @param embargoDetailsVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	void cancelEmbargo(Collection<EmbargoDetailsVO> embargoDetailsVOs)
			throws RemoteException, SystemException, EmbargoRulesBusinessException;

	/**
	 * Used to list the details of a selected embargo
	 *
	 * @param companyCode
	 * @param embargoReferenceNumber
	 * @return EmbargoVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	EmbargoRulesVO findEmbargoDetails(EmbargoFilterVO embargoFilterVO) throws RemoteException,
			SystemException;

	/**
	 * This method finds embargos which meet the filter
	 *
	 * @param filterVO
	 * @param pageNumber
	 *            To be reviewed
	 * @return Collection
	 * @throws RemoteException
	 * @throws SystemException
	 *             Page<EmbargoDetailsVO>
	 */
	Page findEmbargos(EmbargoFilterVO filterVO, int pageNumber)
			throws RemoteException, SystemException;

	/**
	 * This method will implement the audit method in Auditor.
	 * It will calls the audit method of the audit controller.
	 * @param auditVo
	 * @throws AuditException
	 * @throws RemoteException
	 * @return
	 */
    void audit(Collection<AuditVO> auditVo) throws AuditException, RemoteException;
    
	/**
	 * This method identifies all embargos associated with the given shipment
	 *
	 * @param shipmentVO
	 * @return Collection<EmbargoDetailsVO>
	 * @throws RemoteException
	 * @throws SystemException
	 *             To be reviewed
	 */
	Collection<EmbargoDetailsVO> checkForEmbargo(Collection<ShipmentDetailsVO> shipmentDetailsVO)
			throws RemoteException, SystemException, EmbargoRulesBusinessException;

	
	/**
	 * Alert embargo expiry.
	 *
	 * @param embargoJobSchedulerVO the embargo job scheduler vo
	 * @throws SystemException the system exception
	 * @throws RemoteException the remote exception
	 * @author A-5160
	 */
	public void alertEmbargoExpiry(EmbargoJobSchedulerVO embargoJobSchedulerVO)throws SystemException,RemoteException;
	/**
	 * @param eventAsyncHelperVO
	 * @throws RemoteException
	 */
	public void handleEvents(com.ibsplc.xibase.server.framework.event.vo.EventAsyncHelperVO eventAsyncHelperVO) throws RemoteException;
	/**
	 * 
	 * @param adviceAsyncHelperVO
	 * @throws RemoteException
	 */
	public void handleAdvice(com.ibsplc.xibase.server.framework.interceptor.vo.AsyncAdviceHelperVO adviceAsyncHelperVO) throws RemoteException;
	
	void approveEmbargo(Collection<EmbargoDetailsVO> embargoDetailsVOs)
	throws RemoteException, SystemException, EmbargoRulesBusinessException, EmbargoBusinessException;
	
	void rejectEmbargo(Collection<EmbargoDetailsVO> embargoDetailsVOs)
	throws RemoteException, SystemException, EmbargoRulesBusinessException;

	
	/** Added by A-5867 for ICRD-68630 starts **/
	/**
	 * Search Embargos .
	 * @author A-5867
	 * @param EmbargoFilterVO
	 * @return the EmbargoSearchVO
	 * @throws SystemException the system exception
	 * @throws RemoteException the remote exception
	 */
	public EmbargoSearchVO  searchEmbargos(EmbargoFilterVO filterVO) throws RemoteException, SystemException;
	/**
	 * find All Regulatory Messages.
	 * @author A-5867
	 * @param RegulatoryMessageFilterVO
	 * @return List<RegulatoryMessageVO>
	 * @throws SystemException the system exception
	 * @throws RemoteException the remote exception
	 */
	 List<RegulatoryMessageVO> findAllRegulatoryMessages(RegulatoryMessageFilterVO regulatoryMessageFilter)  
			throws SystemException,RemoteException;
	
	/**
	 * find Regulatory Messages.
	 * @author A-5867
	 * @param RegulatoryMessageFilterVO
	 * @return the page
	 * @throws SystemException the system exception
	 * @throws RemoteException the remote exception
	 */
	Page<RegulatoryMessageVO> findRegulatoryMessages(RegulatoryMessageFilterVO regulatoryMessageFilter)  
			throws SystemException,RemoteException;

	/**
	 * save Regulatory Messages
	 * @author A-5867
	 * @param Collection<RegulatoryMessageVO>
	 * @throws SystemException the system exception
	 * @throws RemoteException the remote exception
	 */
	public void saveRegulatoryMessages(Collection<RegulatoryMessageVO> regulatoryMessageVOs) throws 
			SystemException,RemoteException;
	
	/**
	 * @author A-5867
	 * @param roleGroupCodes
	 * @param companyCode
	 * @return Collection<UserRoleGroupDetailsVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<UserRoleGroupDetailsVO> validateRoleGroup(Collection<String> roleGroupCodes, String companyCode)
			throws RemoteException, SystemException;
	/** Added by A-5867 for ICRD-68630 ends **/
	
	/**
	 * @author A-5867
	 * @param EmbargoFilterVO
	 * @return Collection<EmbargoDetailsVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<EmbargoDetailsVO> findDuplicateEmbargos(EmbargoFilterVO filterVO)
			throws RemoteException, SystemException;
	
	/**
     * This method finds embargos/regulatory compliance which meet the filter 
     * @param filterVO
     * @param pageNumber To be reviewed
     * @return Collection
     * @throws RemoteException
     * @throws SystemException
     * Page<EmbargoDetailsVO>
     */
	public Page<EmbargoDetailsVO> findRegulatoryComplianceRules(EmbargoFilterVO filterVO, int pageNumber)
			throws RemoteException, SystemException;
	
	/**
	 * 
	 * @param companyCode
	 * @param productName
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public String validateProduct(String companyCode, String productName, LocalDate startDate, LocalDate endDate) throws SystemException, RemoteException;
	/** Added by A-6843 for ICRD-69906 starts **/
	/**
	 * Find exception embargo details.
	 *
	 * @param exceptionEmbargoFilterVO the exception embargo filter vo
	 * @return the page
	 * @throws SystemException the system exception
	 * @throws RemoteException the remote exception
	 */
	public Page<ExceptionEmbargoDetailsVO> findExceptionEmbargoDetails(ExceptionEmbargoFilterVO exceptionEmbargoFilterVO)throws SystemException,RemoteException;

	/**
	 * Save exception embargo details.
	 *
	 * @param exceptionEmbargoVOs the exception embargo v os
	 * @throws SystemException the system exception
	 * @throws RemoteException the remote exception
	 */
	public void saveExceptionEmbargoDetails(Collection<ExceptionEmbargoDetailsVO> exceptionEmbargoVOs) throws 
	SystemException,RemoteException;

	/** Added by A-6843 for ICRD-69906 ends **/
	/**
	 * 
	 * @param jobScheduleVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void updateEmbargoView(RECORefreshJobScheduleVO jobScheduleVO)throws RemoteException,SystemException ;

	/**
     * This method checks any embargo exists in system
	 * @param embargoFilterVO
	 * @return boolean
	 * @throws SystemException
	 */
	public boolean checkAnyEmbargoExists(EmbargoFilterVO embargoFilterVO)
			throws RemoteException, SystemException;
	/**
	 * This method returns exception awbs
	 * @param exceptionEmbargoFilterVOs
	 * @return Collection<String>
	 * @throws SystemException
	 */
	public Collection<String> findExceptionEmbargos(
			Collection<ExceptionEmbargoFilterVO> exceptionEmbargoFilterVOs)
			throws RemoteException, SystemException;
	/**
	 * 
	 * 	Method		:	EmbargoRulesBI.getEmbargoEnquiryDetails
	 *	Added by 	:	A-5153 on Feb 16, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	List<EmbargoDetailsVO>
	 */
	public List<EmbargoDetailsVO> getEmbargoEnquiryDetails(
			EmbargoFilterVO filterVO) throws RemoteException, SystemException;
	/**
	 * 
	 * @param embargoFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<EmbargoDetailsVO> parameterSelectiveEmbargoSearch(EmbargoFilterVO embargoFilterVO)
			throws RemoteException,SystemException;
	public Map<String,ProductVO> validateProductNames(
			String companyCode, Collection<String> productNames) throws SystemException,
			InvalidProductException, RemoteException;
}
