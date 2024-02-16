/*
 * SCMValidationSessionImpl.java Created on Jan 2, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.messaging;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMValidationVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMValidationSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-3459
 *
 */
public class SCMValidationSessionImpl  extends AbstractScreenSession implements SCMValidationSession {
	
	private static final String KEY_MODULE_NAME = "uld.defaults";
    private static final String KEY_SCREEN_ID = "uld.defaults.scmvalidation";
    private static final String KEY_FACILITYTYPE = "facilityType";
    private static final String KEY_SCMSTATUS = "scmStatus";
    private static final String KEY_SCMVAL = "SCMValidationVOs";
    
    
    /**
	 * 
	 * /** Method to get ScreenID
	 * 
	 * @return ScreenID
	 */
	public String getScreenID() {
		return KEY_SCREEN_ID;
	}

	/**
	 * Method to get ProductName
	 * 
	 * @return ProductName
	 */
	public String getModuleName() {
		return KEY_MODULE_NAME;
	}
	
	/**
	 * @return
	 */
	public Collection<OneTimeVO> getFacilityType() {
		return (Collection<OneTimeVO>) getAttribute(KEY_FACILITYTYPE);
	}
	/**
	 * @param facilityType
	 */
	public void setFacilityType(Collection<OneTimeVO> facilityType) {
		setAttribute(KEY_FACILITYTYPE, (ArrayList<OneTimeVO>)facilityType);
		
	}
	
	/**
	 * @return
	 */
	public Collection<OneTimeVO> getScmStatus() {
		return (Collection<OneTimeVO>) getAttribute(KEY_SCMSTATUS);
	}
	/**
	 * @param facilityType
	 */
	public void setScmStatus(Collection<OneTimeVO> scmStatus) {
		setAttribute(KEY_SCMSTATUS, (ArrayList<OneTimeVO>)scmStatus);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Page<SCMValidationVO> getSCMValidationVOs(){
	   return (Page<SCMValidationVO>) 
	   		getAttribute(KEY_SCMVAL);
	}
	/**
	 * @param scmValidationVO
	 *            The SCMValidationVO to set.
	 */
	public void setSCMValidationVOs(Page<SCMValidationVO> scmValidationVO){
	   setAttribute(
			   KEY_SCMVAL, (Page<SCMValidationVO>)scmValidationVO);
	}
	
	/**
	 * 
	 *
	 */
	public void removeSCMValidationVOs() {
		removeAttribute(KEY_SCMVAL);	
	}
}
