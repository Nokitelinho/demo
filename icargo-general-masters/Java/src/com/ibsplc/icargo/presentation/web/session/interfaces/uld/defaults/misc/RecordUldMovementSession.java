/*
 * RecordUldMovementSession.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc;

import java.util.Collection;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1347
 *
 */
public interface RecordUldMovementSession extends ScreenSession {
	
	/**
	 * 
	 * @param contentOneTimeValues
	 */
	public void setOneTimeContent(Collection<OneTimeVO> contentOneTimeValues);
	/**
	 * 
	 * @return
	 */
	public Collection<OneTimeVO> getOneTimeContent();
	    /**
	     * 
	     * @param uldMovementVOs
	     */
    public void setULDMovementVOs(Collection<ULDMovementVO> uldMovementVOs);
    /**
     * 
     * @return
     */
    public Collection<ULDMovementVO> getULDMovementVOs(); 
    /**
     * 
     * @return
     */
    
    public Collection<String> getULDNumbers();
    /**
     * 
     * @param uldNumbers
     */
    public void setULDNumbers(Collection<String> uldNumbers);
    /**
     * 
     * @return
     */
    public ULDFlightMessageReconcileDetailsVO getULDFlightMessageReconcileDetailsVO();
/**
 * 
 * @param uldFlightMessageReconcileDetailsVO
 */
	public void setULDFlightMessageReconcileDetailsVO(ULDFlightMessageReconcileDetailsVO uldFlightMessageReconcileDetailsVO);
	/**
	 * 
	 * @return
	 */
	public String getPageURL();
/**
 * 
 * @param pageurl
 */
	public void setPageURL(String pageurl);
    
    
}
