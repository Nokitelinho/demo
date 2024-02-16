/*
 * DsnEnquirySessionImpl.java Created on July 05, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DSNEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DsnEnquirySession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1861
 *
 */
public class DsnEnquirySessionImpl extends AbstractScreenSession
        implements DsnEnquirySession {

	private static final String SCREEN_ID = "mailtracking.defaults.dsnEnquiry";
	private static final String MODULE_NAME = "mail.operations";

	private static final String ONETIME_CONTAINERTYPES = "onetime_containertypes";
	private static final String ONETIME_MAILCATEGORY = "onetime_mailcategory";
	private static final String ONETIME_MAILCLASS = "onetime_mailclass";
	private static final String ONETIME_OPERATIONTYPES = "operationTypes";
	private static final String DESPATCHDETAILVOS = "page_despatchdetailvo";
	private static final String SELECTED_DESPATCHDETAILVOS = "selectedvos";
	private static final String DSNENQUIRYFILTERVO = "dsnEnquiryFilterVO";
	private static final String TOTALRECORDS = "totalRecords";   //added by A-5203
	//added by A-5203
	
	 public Integer getTotalRecords()
	    {
	        return (Integer)getAttribute(TOTALRECORDS);
	    }

	 public void setTotalRecords(int totalRecords)
	    {
	        setAttribute(TOTALRECORDS, Integer.valueOf(totalRecords));
	    }
	
	    //end
	/**
     * This method is used to set onetime values to the session
     * @param operationTypes - Collection<OneTimeVO>
     */
	public void setOperationTypes(Collection<OneTimeVO> operationTypes) {
		setAttribute(ONETIME_OPERATIONTYPES,(ArrayList<OneTimeVO>)operationTypes);
	}

	/**
     * This method returns the onetime vos
     * @return ONETIME_OPERATIONTYPES - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOperationTypes() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_OPERATIONTYPES);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param containerTypes - Collection<OneTimeVO>
     */
	public void setContainerTypes(Collection<OneTimeVO> containerTypes) {
		setAttribute(ONETIME_CONTAINERTYPES,(ArrayList<OneTimeVO>)containerTypes);
	}
	/**
     * This method returns the onetime vos
     * @return ONETIME_CONTAINERTYPES - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getContainerTypes() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_CONTAINERTYPES);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param mailCategory - Collection<OneTimeVO>
     */
	public void setMailCategory(Collection<OneTimeVO> mailCategory) {
		setAttribute(ONETIME_MAILCATEGORY,(ArrayList<OneTimeVO>)mailCategory);
	}

	/**
     * This method returns the onetime vos
     * @return ONETIME_MAILCATEGORY - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getMailCategory() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_MAILCATEGORY);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param mailClass - Collection<OneTimeVO>
     */
	public void setMailClass(Collection<OneTimeVO> mailClass) {
		setAttribute(ONETIME_MAILCLASS,(ArrayList<OneTimeVO>)mailClass);
	}

	/**
     * This method returns the onetime vos
     * @return ONETIME_MAILCLASS - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getMailClass() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_MAILCLASS);
	}
	
	/**
	 * This method is used to set DespatchDetailsVOs to the session
	 * @param despatchDetailsVOs - Page<DespatchDetailsVO>
	 */
	public void setDespatchDetailsVOs(Page<DespatchDetailsVO> despatchDetailsVOs) {
		setAttribute(DESPATCHDETAILVOS,despatchDetailsVOs);
	}

	/**
	 * This method returns the DespatchDetailsVOs
	 * @return DESPATCHDETAILVOS - Page<DespatchDetailsVO>
	 */
	public Page<DespatchDetailsVO> getDespatchDetailsVOs() {
		return getAttribute(DESPATCHDETAILVOS);
	}
	
	/**
	 * This method is used to set Collection<DespatchDetailsVO> to the session
	 * @param despatchDetailsVOs - Collection<DespatchDetailsVO>
	 */
	public void setSelectedDespatchDetailsVOs(Collection<DespatchDetailsVO> despatchDetailsVOs) {
		setAttribute(SELECTED_DESPATCHDETAILVOS,(ArrayList<DespatchDetailsVO>)despatchDetailsVOs);
	}

	/**
	 * This method returns the Collection<DespatchDetailsVO>
	 * @return SELECTED_DESPATCHDETAILVOS - Collection<DespatchDetailsVO>
	 */
	public Collection<DespatchDetailsVO> getSelectedDespatchDetailsVOs() {
		return (Collection<DespatchDetailsVO>)getAttribute(SELECTED_DESPATCHDETAILVOS);
	}
	
	/**
	 * This method is used to set DsnEnquiryFilterVO to the session
	 * @param dsnEnquiryFilterVO - DsnEnquiryFilterVO
	 */
	public void setDsnEnquiryFilterVO(DSNEnquiryFilterVO dsnEnquiryFilterVO) {
		setAttribute(DSNENQUIRYFILTERVO,dsnEnquiryFilterVO);
	}

	/**
	 * This method returns the DsnEnquiryFilterVO
	 * @return DSNENQUIRYFILTERVO - DsnEnquiryFilterVO
	 */
	public DSNEnquiryFilterVO getDsnEnquiryFilterVO() {
		return getAttribute(DSNENQUIRYFILTERVO);
	}
	
    /**
     * @return SCREEN_ID - String
     */
	@Override
	public String getScreenID() {
		return SCREEN_ID;
	}

	/**
     * @return MODULE_NAME - String
     */
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}


}
