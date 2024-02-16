/*
 * EmbargoDAO.java Created on May 2, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary 
 * information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.reco.defaults;

import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGlobalParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoSearchVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ExceptionEmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ExceptionEmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1358
 *
 * This interface defines the database queries that are required by 
 * the embargo module
 */
public interface EmbargoRulesDAO {
    
    /**
     * Used to list the details of a selected embargo
     * @param companyCode
     * @param embargoReferenceNumber
     * @return EmbargoVO
     * @throws PersistenceException
     * @throws SystemException
     */
	EmbargoRulesVO findEmbargoDetails(EmbargoFilterVO embargoFilterVO)
    	throws PersistenceException, SystemException;
    
    /**
     * This method finds embargos which meet the filter 
     * @param filterVO
     * @param pageNumber To be reviewed
     * @return Collection
     * @throws PersistenceException
     * @throws SystemException
     * Page<EmbargoDetailsVO>
     */
    Page findEmbargos(EmbargoFilterVO filterVO, int pageNumber)
    	throws PersistenceException,SystemException;
    
    /**
     * This method checks whether a duplicate embargo exists in the system
     * @param embargoVO
     * @return boolean
     * @throws PersistenceException
     * @throws SystemException
     */
    boolean checkDuplicateEmbargo(EmbargoRulesVO embargoVO)
    	throws PersistenceException, SystemException;
    
    /**
     * This method fetches all global parameters present in the system
     * @param companyCode
     * @return Collection<EmbargoGlobalParameterVO>
     * @throws PersistenceException
     * @throws SystemException
     */
    Collection<EmbargoGlobalParameterVO> findGlobalParameterCodes(String companyCode)
    	throws PersistenceException, SystemException;

    /**
     * This method identifies all embargos associated with the given
     * shipment 
     * @param shipmentVO
     * @return Collection<EmbargoDetailsVO>
     * @throws PersistenceException
     * @throws SystemException
     */
    Collection<EmbargoDetailsVO> checkForEmbargo(Collection<ShipmentDetailsVO> shipmentVO)
    	throws PersistenceException,SystemException; 
    /**
     * This method identifies all embargos associated with the given
     * shipment 
     * @param shipmentVO
     * @return Collection<EmbargoDetailsVO>
     * @throws PersistenceException
     * @throws SystemException
     */
    Collection<EmbargoDetailsVO> checkForCoolFrozenEmbargo(Collection<ShipmentDetailsVO> shipmentDetailsVOs)
	throws PersistenceException,SystemException;
    
    /**
     * This method identifies all embargos for expiry.
     *
     * @param embargoFilterVO the embargo filter vo
     * @return Collection<EmbargoDetailsVO>
     * @throws SystemException the system exception
     * @author a-5160
     */
    Collection<EmbargoDetailsVO> findEmbargoCandidatesForExpiry(EmbargoFilterVO embargoFilterVO) throws SystemException;

    /** Added by A-5867 for ICRD-68630 starts **/
    /**
     * This method finds embargos which meet the filter 
     * @param filterVO
     * @return EmbargoSearchVO
     * @throws PersistenceException
     * @throws SystemException
     */
    EmbargoSearchVO searchEmbargos(EmbargoFilterVO filterVO)throws PersistenceException,SystemException;
    
    /**
     * This method finds embargos which meet the filter 
     * @param filterVO
     * @return  Collection<EmbargoDetailsVO>
     * @throws PersistenceException
     * @throws SystemException
     */
    Collection<EmbargoDetailsVO> findDuplicateEmbargos(EmbargoFilterVO filterVO)throws PersistenceException,SystemException;
    
    /**
     * This method All finds embargos which meet the filter 
     * @param filterVO
     * @param pageNumber To be reviewed
     * @return Collection
     * @throws PersistenceException
     * @throws SystemException
     * List<RegulatoryMessageVO>
     */
    List<RegulatoryMessageVO> findAllRegulatoryMessages(RegulatoryMessageFilterVO regulatoryMessageFilter)throws PersistenceException,SystemException;

    
    /**
     * This method finds embargos which meet the filter 
     * @param filterVO
     * @param pageNumber To be reviewed
     * @return Collection
     * @throws PersistenceException
     * @throws SystemException
     * Page<RegulatoryMessageVO>
     */
    Page<RegulatoryMessageVO> findRegulatoryMessages(RegulatoryMessageFilterVO regulatoryMessageFilter)throws PersistenceException,SystemException;

    /**
     * Method to call procedure to update embargo view
     * @throws SystemException
     */
    void updateEmbargoView()throws SystemException;
    
    /**
     * This method finds embargos/regulatory compliance which meet the filter 
     * @param filterVO
     * @param pageNumber To be reviewed
     * @return Page<EmbargoDetailsVO>
     * @throws PersistenceException
     * @throws SystemException
     */
    Page<EmbargoDetailsVO> findRegulatoryComplianceRules(EmbargoFilterVO filterVO, int pageNumber)
    	throws PersistenceException,SystemException;
    
    /** Added by A-5867 for ICRD-68630 ends **/
    /** Added by A-6843 for ICRD-69906 starts **/
    /**
     * @param filterVO
     * @return
     * @throws SystemException
     */
    Page<ExceptionEmbargoDetailsVO> findExceptionEmbargoDetails(ExceptionEmbargoFilterVO filterVO) throws SystemException;
	/** Added by A-6843 for ICRD-69906 ends **/
    /**
     * This method checks any embargo exists in system
	 * @param embargoFilterVO
	 * @return boolean
	 * @throws PersistenceException
	 * @throws SystemException
     */
    boolean checkAnyEmbargoExists(EmbargoFilterVO embargoFilterVO)
    	throws PersistenceException,SystemException;
	/**
	 * This method returns exception awbs
	 * @param exceptionEmbargoFilterVOs
	 * @return Collection<String>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	Collection<String> findExceptionEmbargos(
			Collection<ExceptionEmbargoFilterVO> exceptionEmbargoFilterVOs)
	throws PersistenceException, SystemException;
	
	/**
	 * 
	 * 	Method		:	EmbargoRulesDAO.getEmbargoEnquiryDetails
	 *	Added by 	:	A-5153 on Feb 16, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	List<EmbargoDetailsVO>
	 */
	List<EmbargoDetailsVO> getEmbargoEnquiryDetails(EmbargoFilterVO filterVO)
			throws PersistenceException, SystemException;
	/**
	 * this method does a specific search for OD and product configured embargoes
	 * @param shipmentVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	Collection<EmbargoDetailsVO> parameterSelectiveEmbargoSearch(EmbargoFilterVO filterVO)
	    	throws PersistenceException,SystemException; 
}
