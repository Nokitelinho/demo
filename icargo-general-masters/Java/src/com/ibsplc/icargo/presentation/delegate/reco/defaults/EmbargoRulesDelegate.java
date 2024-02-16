/*
 * EmbargoDelegate.java Created on Aug 9, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information 
 * of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.delegate.reco.defaults;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.admin.user.vo.UserRoleGroupDetailsVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGlobalParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoSearchVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ExceptionEmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ExceptionEmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.RECORefreshJobScheduleVO;
import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.business.rules.defaults.vo.RuleDefinitionVO;
import com.ibsplc.icargo.business.rules.defaults.vo.RuleFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.client.framework.delegate.Action;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * This class is used to maintain and check embargos against shipments
 * 
 * @author A-1358
 */

@Module("reco")
@SubModule("defaults")
public class EmbargoRulesDelegate extends BusinessDelegate {

	/**
	 * This method is used to create/modify embargo details. For create
	 * operationFlag is set as I and for modify, operationFlag is set as 'U'
	 * 
	 * @param embargoVO
	 * @throws BusinessDelegateException
	 */
	@Action("saveEmbargoDetails")
	public String saveEmbargoDetails(EmbargoRulesVO embargoVO)
			throws BusinessDelegateException {
		return despatchRequest("saveEmbargoDetails", embargoVO);
	}

	/**
	 * This method is used to cancel an embargo.
	 * 
	 * @param embargoDetailsVO
	 * @throws BusinessDelegateException
	 */
	@Action("cancelEmbargo")
	public void cancelEmbargo(Collection<EmbargoDetailsVO> embargoDetailsVOs)
			throws BusinessDelegateException {
		despatchRequest("cancelEmbargo",embargoDetailsVOs);
	}

	/**
	 * Used to list the details of a selected embargo
	 * 
	 * @param companyCode
	 * @param embargoReferenceNumber
	 * @return EmbargoVO
	 * @throws BusinessDelegateException
	 */
	@Action("findEmbargoDetails")
	public EmbargoRulesVO findEmbargoDetails(EmbargoFilterVO embargoFilterVO) throws BusinessDelegateException {
		return despatchRequest("findEmbargoDetails",
				embargoFilterVO);
	}

	/**
	 * This method finds all embargos matching the filter criteria
	 * 
	 * @param filterVO
	 * @param pageNumber
	 * @return Collection <EmbargoDetailsVO>
	 * @throws BusinessDelegateException
	 */
	@Action("findEmbargos")
	public Page findEmbargos(EmbargoFilterVO filterVO,int pageNumber)
			throws BusinessDelegateException {
		return despatchRequest("findEmbargos",filterVO,pageNumber);
	}

	/**
	 * This method fetches all global parameter codes
	 * 
	 * @param companyCode
	 * @return Collection<EmbargoGlobalParameterVO>
	 * @throws BusinessDelegateException
	 */
	@Action("findGlobalParameterCodes")
	public Collection<EmbargoGlobalParameterVO> findGlobalParameterCodes(
			String companyCode)
			throws BusinessDelegateException {
		return despatchRequest("findGlobalParameterCodes",companyCode);
	}
	
	/**
	 * This method identifies all embargos associated with the given shipment
	 * 
	 * @param shipmentVO
	 * @return Collection<EmbargoDetailsVO>
	 * @throws BusinessDelegateException
	 */
	@Action("checkForEmbargo")
	public Collection<EmbargoDetailsVO> checkForEmbargo(
			Collection<ShipmentDetailsVO> shipmentVO)
			throws BusinessDelegateException {
		
		return despatchRequest("checkForEmbargo",shipmentVO);
	}
	/**
	 * This method identifies all embargos associated with the given shipment
	 * 
	 * @param Collection<EmbargoDetailsVO>
	 * @return Collection<EmbargoDetailsVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<EmbargoDetailsVO> checkForEmbargoForBooking(
			Collection<ShipmentDetailsVO> shipmentDetailsVOs)
			throws BusinessDelegateException {		
		return despatchRequest("checkForEmbargoForBooking",shipmentDetailsVOs);
	}
	
	/**
	 * @author A-5183
	 * @param filterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<RuleDefinitionVO> findRuleDetails(RuleFilterVO filterVO) throws BusinessDelegateException{
		log.entering("RulesDefaultsDelegate", "findRuleDetails");

		return despatchRequest("findRuleDetails", filterVO);
	}
	
	/**
	 * Approve embargo.
	 * @author a-5160
	 * @param embargoDetailsVOs the embargo details v os
	 * @throws BusinessDelegateException the business delegate exception
	 */
	public void approveEmbargo(Collection<EmbargoDetailsVO> embargoDetailsVOs)
	throws BusinessDelegateException {
		despatchRequest("approveEmbargo",embargoDetailsVOs);
	}
	
	/**
	 * Reject embargo.
	 * @author a-5160
	 * @param embargoDetailsVOs the embargo details v os
	 * @throws BusinessDelegateException the business delegate exception
	 */
	public void rejectEmbargo(Collection<EmbargoDetailsVO> embargoDetailsVOs)
	throws BusinessDelegateException {
		despatchRequest("rejectEmbargo",embargoDetailsVOs);
	}

	/**
	 * Find duplicate embargos.
	 * @author a-5160
	 * @param filterVO the filter vo
	 * @return the embargo search vo
	 * @throws BusinessDelegateException the business delegate exception
	 */
	public Collection<EmbargoDetailsVO> findDuplicateEmbargos(EmbargoFilterVO filterVO)
	throws BusinessDelegateException {
	return despatchRequest("findDuplicateEmbargos",filterVO);
	}
	/** Added by A-5867 for ICRD-68630 starts **/
	/**
	 * @author A-5867
	 * @param EmbargoFilterVO
	 * @return EmbargoSearchVO
	 * @throws BusinessDelegateException
	 */
	public EmbargoSearchVO searchEmbargos(EmbargoFilterVO filterVO)
			throws BusinessDelegateException {
		return despatchRequest("searchEmbargos",filterVO);
	}
	/**
	 * @author A-5867
	 * @param RegulatoryMessageFilterVO
	 * @return List<RegulatoryMessageVO>
	 * @throws BusinessDelegateException
	 */
	public List<RegulatoryMessageVO> findAllRegulatoryMessages(RegulatoryMessageFilterVO filter)
	throws BusinessDelegateException {
		return despatchRequest("findAllRegulatoryMessages",filter);
	}
	/**
	 * @author A-5867
	 * @param RegulatoryMessageFilterVO
	 * @return Page<RegulatoryMessageVO>
	 * @throws BusinessDelegateException
	 */
	public Page<RegulatoryMessageVO> findRegulatoryMessages(RegulatoryMessageFilterVO filter)
	throws BusinessDelegateException {
		return despatchRequest("findRegulatoryMessages",filter);
	}
	/**
	 * @author A-5867
	 * @param Collection<RegulatoryMessageVO>
	 * @return 
	 * @throws BusinessDelegateException
	 */
	public void saveRegulatoryMessages(Collection<RegulatoryMessageVO> regulatoryMessageVOs)
	throws BusinessDelegateException {
		despatchRequest("saveRegulatoryMessages",regulatoryMessageVOs);
	}
	
	/**
	 * @author A-5867
	 * @param Collection<String> roleGroupCodes
	 * @param companyCode
	 * @return Collection<UserRoleGroupDetailsVO>
	 * @throws BusinessDelegateException
	 */
	
	public Collection<UserRoleGroupDetailsVO> validateRoleGroup( 
			Collection<String> roleGroupCodes, String companyCode)
			throws BusinessDelegateException {
		return despatchRequest("validateRoleGroup", roleGroupCodes, companyCode);
	}
	
	/**
     * This method finds embargos/regulatory compliance which meet the filter 
     * @param filterVO
     * @param pageNumber To be reviewed
     * @return Page<EmbargoDetailsVO>
     * @throws BusinessDelegateException
     */
	public Page<EmbargoDetailsVO> findRegulatoryComplianceRules(EmbargoFilterVO filterVO,int pageNumber)
			throws BusinessDelegateException {
		return despatchRequest("findRegulatoryComplianceRules", filterVO,pageNumber);
	}
	
	/** Added by A-5867 for ICRD-68630 ends **/
	/**
	 * @param companyCode
	 * @param productName
	 * @param startDate
	 * @param endDate
	 * @return 
	 * @throws BusinessDelegateException
	 */
	public String validateProduct(String companyCode, String productName, LocalDate startDate, LocalDate endDate)
    throws BusinessDelegateException
	{
	    return (String)despatchRequest("validateProduct", new Object[] {
	    		companyCode, productName, startDate, endDate});
	}
	
	/** Added by A-6843 for ICRD-69906 **/
	/**
	 * Find exception embargo details.
	 *
	 * @param exceptionEmbargoFilterVO the exception embargo filter vo
	 * @return the page
	 * @throws BusinessDelegateException the business delegate exception
	 */
	public Page<ExceptionEmbargoDetailsVO> findExceptionEmbargoDetails(ExceptionEmbargoFilterVO exceptionEmbargoFilterVO)
			throws BusinessDelegateException{
		return despatchRequest("findExceptionEmbargoDetails",exceptionEmbargoFilterVO);
		
	}
	
	/**
	 * Save exception embargo details.
	 *
	 * @param exceptionEmbargoVOs the exception embargo v os
	 * @throws BusinessDelegateException the business delegate exception
	 */
	public void saveExceptionEmbargoDetails(Collection<ExceptionEmbargoDetailsVO> exceptionEmbargoVOs)
		throws BusinessDelegateException {
			despatchRequest("saveExceptionEmbargoDetails",exceptionEmbargoVOs);
	}
	
	/** Added by A-6843 for ICRD-69906 ends **/
	
	/**
	 * 
	 * @param jobScheduleVO
	 * @throws BusinessDelegateException
	 */
	public void updateEmbargoView(RECORefreshJobScheduleVO jobScheduleVO)throws BusinessDelegateException {
		despatchRequest("updateEmbargoView",jobScheduleVO);
	}
	/**Added by A-5984 for ICRD-215196
	 * @param companyCode
	 * @param Collection<String> productNames
	 * @return 
	 * @throws BusinessDelegateException
	 */
	public Map<String,ProductVO> validateProductNames(
			String companyCode, Collection<String> productNames) throws BusinessDelegateException {
		 log.entering("EmbargoRulesDelegate", "validateProductNames");
		 return despatchRequest("validateProductNames",companyCode, productNames);
	}
}
