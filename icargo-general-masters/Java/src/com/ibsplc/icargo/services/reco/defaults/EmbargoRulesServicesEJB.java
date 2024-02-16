/*
 * EmbargoServicesEJB.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary
 * information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.services.reco.defaults;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.admin.user.vo.UserRoleGroupDetailsVO;
import com.ibsplc.icargo.business.products.defaults.InvalidProductException;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.reco.defaults.EmbargoRulesBI;
import com.ibsplc.icargo.business.reco.defaults.EmbargoRulesBusinessException;
import com.ibsplc.icargo.business.reco.defaults.EmbargoRulesController;
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
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.ejb.AbstractFacadeEJB;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 * @author A-1358
 *  @ejb.bean description="EmbargoServices"
 *           display-name="EmbargoServicesEJB"
 *           jndi-name="com.ibsplc.icargo.services.reco.defaults.EmbargoServicesHome"
 *           name="EmbargoServices"
 *           type="Stateless"
 *           view-type="remote"
 *           remote-business-interface="com.ibsplc.icargo.business.reco.defaults.EmbargoBI"
 *
 * @ejb.transaction type="Supports"
 *
 */
public class EmbargoRulesServicesEJB extends AbstractFacadeEJB implements EmbargoRulesBI {



	/**
	 * This method is used to create/modify embargo details. For create
	 * operationFlag is set as I and for modify, operationFlag is set as 'U'
	 *
	 * @param embargoVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @return String
	 */
	public String saveEmbargoDetails(EmbargoRulesVO embargoVO)
			throws RemoteException, SystemException, EmbargoRulesBusinessException {

		//return new EmbargoRulesController().saveEmbargoDetails(embargoVO);
		EmbargoRulesController embargoRulesController= (EmbargoRulesController)SpringAdapter.getInstance().getBean("recodefaultsController");
		return embargoRulesController.saveEmbargoDetails(embargoVO);

	}

	/**
	 * This method is used to cancel an embargo.
	 *
	 * @param embargoDetailsVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void cancelEmbargo(Collection<EmbargoDetailsVO> embargoDetailsVOs)
			throws RemoteException, SystemException, EmbargoRulesBusinessException {

		//new EmbargoController().cancelEmbargo(embargoDetailsVO);
		EmbargoRulesController embargoRulesController= (EmbargoRulesController)SpringAdapter.getInstance().getBean("recodefaultsController");
		embargoRulesController.cancelEmbargo(embargoDetailsVOs);
	}

	/**
	 * Used to list the details of a selected embargo
	 *
	 * @param companyCode
	 * @param embargoReferenceNumber
	 * @return EmbargoVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public EmbargoRulesVO findEmbargoDetails(EmbargoFilterVO embargoFilterVO) throws RemoteException,
			SystemException {
		return new EmbargoRulesController().findEmbargoDetails(embargoFilterVO);
	}

	/**
	 * This method finds embargos which meet the filter
	 *
	 * @param filterVO
	 * @param pageNumber
	 * @return Page<EmbargoDetailsVO>
	 * @throws RemoteException
	 * @throws SystemException
	 *
	 */
	public Page findEmbargos(EmbargoFilterVO filterVO, int pageNumber)
			throws RemoteException, SystemException {
		return new EmbargoRulesController().findEmbargos(filterVO, pageNumber);
	}
	
	/**
	 * This method identifies all embargos associated with the given shipment
	 *
	 * @param shipmentVO
	 * @return Collection<EmbargoDetailsVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	
	public Collection<EmbargoDetailsVO> checkForEmbargo(
			Collection<ShipmentDetailsVO> shipmentDetailsVO) throws RemoteException,
			SystemException, EmbargoRulesBusinessException {
		return new EmbargoRulesController().checkForEmbargo(shipmentDetailsVO);
	}
	
	/**
	 * This method identifies all embargos for expiry.
	 *
	 * @param embargoJobSchedulerVO the embargo job scheduler vo
	 * @throws SystemException the system exception
	 * @throws RemoteException the remote exception
	 */
	public void alertEmbargoExpiry(EmbargoJobSchedulerVO embargoJobSchedulerVO)throws SystemException,RemoteException{
		 new EmbargoRulesController().alertEmbargoExpiry(embargoJobSchedulerVO);
	}
	
	public void approveEmbargo(Collection<EmbargoDetailsVO> embargoDetailsVOs)
	throws RemoteException, SystemException, EmbargoRulesBusinessException, EmbargoBusinessException {

		EmbargoRulesController embargoRulesController= (EmbargoRulesController)SpringAdapter.getInstance().getBean("recodefaultsController");
		embargoRulesController.approveEmbargo(embargoDetailsVOs);
		
	}
	
	public void rejectEmbargo(Collection<EmbargoDetailsVO> embargoDetailsVOs)
	throws RemoteException, SystemException, EmbargoRulesBusinessException {

		EmbargoRulesController embargoRulesController= (EmbargoRulesController)SpringAdapter.getInstance().getBean("recodefaultsController");
		embargoRulesController.rejectEmbargo(embargoDetailsVOs);
	}

	/** Added by A-5867 for ICRD-68630 starts**/
	/**
	 * This method searches all embargos that matches the filter
	 *
	 * @param EmbargoFilterVO
	 * @return EmbargoSearchVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public EmbargoSearchVO searchEmbargos(EmbargoFilterVO filterVO)
		throws RemoteException, SystemException {
	return new EmbargoRulesController().searchEmbargos(filterVO);
	}

	/**
	 * This method fetches all Regulatory messages that matches the filter
	 *
	 * @param RegulatoryMessageFilterVO
	 * @return List<RegulatoryMessageVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public  List<RegulatoryMessageVO> findAllRegulatoryMessages(RegulatoryMessageFilterVO regulatoryMessageFilter)
			throws SystemException, RemoteException {
		 return new EmbargoRulesController().findAllRegulatoryMessages(regulatoryMessageFilter);
	}
	
	/**
	 * This method fetches Regulatory messages that matches the filter
	 *
	 * @param RegulatoryMessageFilterVO
	 * @return Page<RegulatoryMessageVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page<RegulatoryMessageVO> findRegulatoryMessages(RegulatoryMessageFilterVO regulatoryMessageFilter)
			throws SystemException, RemoteException {
		 return new EmbargoRulesController().findRegulatoryMessages(regulatoryMessageFilter);
	}

	/**
	 * This method save Regulatory messages
	 *
	 * @param Collection<RegulatoryMessageVO>
	 * @return 
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void saveRegulatoryMessages(Collection<RegulatoryMessageVO> regulatoryMessageVOs)
			throws SystemException,RemoteException{
		new EmbargoRulesController().saveRegulatoryMessages(regulatoryMessageVOs);
		
	}
	/**
	 * @param roleGroupCodes
	 * @param companyCode
	 * @return Collection<UserRoleGroupDetailsVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<UserRoleGroupDetailsVO> validateRoleGroup(
			Collection<String> roleGroupCodes, String companyCode)
			throws RemoteException, SystemException{
		return new EmbargoRulesController().validateRoleGroup(roleGroupCodes,companyCode);
	}
	
	/**
	 * @param EmbargoFilterVO
	 * @return Collection<EmbargoDetailsVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<EmbargoDetailsVO> findDuplicateEmbargos(EmbargoFilterVO filterVO)
			throws RemoteException, SystemException{
		return new EmbargoRulesController().findDuplicateEmbargos(filterVO);
	}
	
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
    	throws RemoteException,SystemException{
		return new EmbargoRulesController().findRegulatoryComplianceRules(filterVO, pageNumber);
	}
	
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
	public String validateProduct(String companyCode, String productName, LocalDate startDate, LocalDate endDate)
	throws SystemException, RemoteException
  {
    return new EmbargoRulesController().validateProduct(companyCode,productName,startDate,endDate);
	}
	
	/**
	 * Added by A-6843 for ICRD-69906 starts *.
	 *
	 * @param exceptionEmbargoFilterVO the exception embargo filter vo
	 * @return the page
	 * @throws RemoteException the remote exception
	 * @throws SystemException the system exception
	 */
	
	public Page<ExceptionEmbargoDetailsVO> findExceptionEmbargoDetails(ExceptionEmbargoFilterVO exceptionEmbargoFilterVO)
	throws RemoteException,SystemException{
		return new EmbargoRulesController().findExceptionEmbargoDetails(exceptionEmbargoFilterVO);

	}
	/**
	 * 
	 * @param exceptionEmbargoVOs the exception embargo v os
	 * @throws SystemException the system exception
	 * @throws RemoteException the remote exception
	 */	
		
	public void saveExceptionEmbargoDetails(Collection<ExceptionEmbargoDetailsVO> exceptionEmbargoVOs)
	throws RemoteException,SystemException{
		new EmbargoRulesController().saveExceptionEmbargoDetails(exceptionEmbargoVOs);
	}
	/** Added by A-6843 for ICRD-69906 ends **/
	/**
	 * 
	 * @param jobScheduleVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void updateEmbargoView(RECORefreshJobScheduleVO jobScheduleVO)throws RemoteException,SystemException {
		new EmbargoRulesController().updateEmbargoView();
	}
	/**
	 *This method checks any embargo exists in system
	 * @param embargoFilterVO
	 * @return boolean
	 * @throws SystemException
	 */
	public boolean checkAnyEmbargoExists(EmbargoFilterVO embargoFilterVO)
			throws RemoteException,
			SystemException {
		return new EmbargoRulesController().checkAnyEmbargoExists(embargoFilterVO);
	}
	/**
	 * This method returns exception awbs
	 * @param exceptionEmbargoFilterVOs
	 * @return Collection<String>
	 * @throws SystemException
	 */
	public Collection<String> findExceptionEmbargos(
			Collection<ExceptionEmbargoFilterVO> exceptionEmbargoFilterVOs)
			throws RemoteException,	SystemException {
		return new EmbargoRulesController().findExceptionEmbargos(
				exceptionEmbargoFilterVOs);
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.reco.defaults.EmbargoRulesBI#getEmbargoEnquiryDetails(com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO)
	 *	Added by 			: A-5153 on Feb 16, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException
	 */
	public List<EmbargoDetailsVO> getEmbargoEnquiryDetails(
			EmbargoFilterVO filterVO) throws RemoteException, SystemException {
		return new EmbargoRulesController().getEmbargoEnquiryDetails(filterVO);
	}
	/**
	 * 
	 * @return Collection<EmbargoDetailsVO>
	 * @throws RemoteException	 *
	 * @throws SystemException 
	 */
	public Collection<EmbargoDetailsVO> parameterSelectiveEmbargoSearch(EmbargoFilterVO embargoFilterVO)
			throws RemoteException,SystemException {
		return new EmbargoRulesController().parameterSelectiveEmbargoSearch(embargoFilterVO);
	}
	public Map<String,ProductVO> validateProductNames(
			String companyCode, Collection<String> productNames) throws SystemException,
			InvalidProductException, RemoteException {
		 return new EmbargoRulesController().validateProductNames(companyCode,productNames);
	}
}
