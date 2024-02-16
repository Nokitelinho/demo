/*
 * DSNPopUpSessionImpl.java Created on AUG 28,2008
 * 
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved. 
 * 
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2391
 * 
 */
public class DSNPopUpSessionImpl extends AbstractScreenSession implements
	DSNPopUpSession {

	private static final String SCREENID = "mailtracking.mra.defaults.dsnselectpopup";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String DESPATCH = "despatch";
	
	private static final String DESPATCH_SEL = "despatch_sel";
	
	private static final String POPUP_FILTERVO = "popup_filtervo";
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getScreenID()
	 */
	/**
	 * @return screenId
	 */
	public String getScreenID() {

		return SCREENID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getModuleName()
	 */
	/**
	 * @return moduleName
	 */
	public String getModuleName() {
		return MODULE_NAME;
	}

	/**
	 * 
	 * @return Collection<OneTimeVO>
	 */
	public Page<DSNPopUpVO> getDespatchDetails() {
		return (Page<DSNPopUpVO>) getAttribute(DESPATCH);
	}

	/**
	 * 
	 * @param dSNPopUpVOs
	 */
	public void setDespatchDetails(Page<DSNPopUpVO> dSNPopUpVOs) {
		setAttribute(DESPATCH, (Page<DSNPopUpVO>) dSNPopUpVOs);
	}

	/**
	 * 
	 * 
	 */
	public void removeDespatchDetails() {
		removeAttribute(DESPATCH);
	}
	/**
	 * 
	 * @return Collection<OneTimeVO>
	 */
	public DSNPopUpVO getSelectedDespatchDetails() {
		return (DSNPopUpVO) getAttribute(DESPATCH_SEL);
	}

	/**
	 * 
	 * @param dSNPopUpVO
	 */
	public void setSelectedDespatchDetails(DSNPopUpVO dSNPopUpVO) {
		setAttribute(DESPATCH_SEL, (DSNPopUpVO) dSNPopUpVO);
	}

	/**
	 * 
	 * 
	 */
	public void removeSelectedDespatchDetails() {
		removeAttribute(DESPATCH_SEL);
	}
	

	
	/**
	 * 
	 * @return DSNPopUpFilterVO
	 */
	public DSNPopUpFilterVO getDsnPopUpFilterDetails() {
		return (DSNPopUpFilterVO) getAttribute(POPUP_FILTERVO);
	}

	/**
	 * 
	 * @param dsnPopUpFilterVOs
	 */
	public void setDsnPopUpFilterDetails(DSNPopUpFilterVO dsnPopUpFilterVO) {
		setAttribute(POPUP_FILTERVO, (DSNPopUpFilterVO) dsnPopUpFilterVO);
		
	}

	/**
	 * 
	 * 
	 */
	public void removeDsnPopUpFilterDetails() {
		removeAttribute(POPUP_FILTERVO);
		
	}

}