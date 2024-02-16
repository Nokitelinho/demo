/*
 * DsnEnquirySession.java Created on July 05, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DSNEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1861
 *
 */
public interface DsnEnquirySession extends ScreenSession {
	
	//added by A-5203
	Integer getTotalRecords();
	
	 void setTotalRecords(int totalRecords);
	 
	 //end

	/**
     * This method is used to set onetime values to the session
     * @param operationTypes - Collection<OneTimeVO>
     */
	public void setOperationTypes(Collection<OneTimeVO> operationTypes);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OPERATIONTYPES - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOperationTypes();
	
	/**
     * This method is used to set onetime values to the session
     * @param containerTypes - Collection<OneTimeVO>
     */
	public void setContainerTypes(Collection<OneTimeVO> containerTypes);

	/**
     * This method returns the onetime vos
     * @return ONETIME_CONTAINERTYPES - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getContainerTypes();
	
	/**
     * This method is used to set onetime values to the session
     * @param mailCategory - Collection<OneTimeVO>
     */
	public void setMailCategory(Collection<OneTimeVO> mailCategory);

	/**
     * This method returns the onetime vos
     * @return ONETIME_MAILCATEGORY - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getMailCategory();
	
	/**
     * This method is used to set onetime values to the session
     * @param mailClass - Collection<OneTimeVO>
     */
	public void setMailClass(Collection<OneTimeVO> mailClass);

	/**
     * This method returns the onetime vos
     * @return ONETIME_MAILCLASS - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getMailClass();
	
	/**
	 * This method is used to set DespatchDetailsVOs to the session
	 * @param despatchDetailsVOs - Page<DespatchDetailsVO>
	 */
	public void setDespatchDetailsVOs(Page<DespatchDetailsVO> despatchDetailsVOs);

	/**
	 * This method returns the DespatchDetailsVOs
	 * @return DESPATCHDETAILVOS - Page<DespatchDetailsVO>
	 */
	public Page<DespatchDetailsVO> getDespatchDetailsVOs();
	
	/**
	 * This method is used to set Collection<DespatchDetailsVO> to the session
	 * @param despatchDetailsVOs - Collection<DespatchDetailsVO>
	 */
	public void setSelectedDespatchDetailsVOs(Collection<DespatchDetailsVO> despatchDetailsVOs);

	/**
	 * This method returns the Collection<DespatchDetailsVO>
	 * @return SELECTED_DESPATCHDETAILVOS - Collection<DespatchDetailsVO>
	 */
	public Collection<DespatchDetailsVO> getSelectedDespatchDetailsVOs();
	
	/**
	 * This method is used to set DsnEnquiryFilterVO to the session
	 * @param dsnEnquiryFilterVO - DsnEnquiryFilterVO
	 */
	public void setDsnEnquiryFilterVO(DSNEnquiryFilterVO dsnEnquiryFilterVO);

	/**
	 * This method returns the DsnEnquiryFilterVO
	 * @return DSNENQUIRYFILTERVO - DsnEnquiryFilterVO
	 */
	public DSNEnquiryFilterVO getDsnEnquiryFilterVO();
	
}

