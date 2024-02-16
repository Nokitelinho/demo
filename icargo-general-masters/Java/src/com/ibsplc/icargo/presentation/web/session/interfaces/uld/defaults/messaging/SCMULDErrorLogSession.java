/*
 * SCMULDErrorLogSession.java Created on Aug 01, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * 
 * @author A-1496
 * 
 */
public interface SCMULDErrorLogSession extends ScreenSession {
	/**
	 * 
	 * @return screenID
	 */
	public String getScreenID();

	/**
	 * 
	 * @return modulename
	 */
	public String getModuleName();
/**
 * 
 * @return
 */
	public Page<ULDSCMReconcileDetailsVO> getSCMReconcileDetailVOs();
/**
 * 
 * @param paramCode
 */
	public void setSCMReconcileDetailVOs(
			Page<ULDSCMReconcileDetailsVO> paramCode);
/**
 * 
 * @return
 */
	public SCMMessageFilterVO getSCMULDFilterVO();
/**
 * 
 * @param filterVO
 */
	public void setSCMULDFilterVO(SCMMessageFilterVO filterVO);
/**
 * 
 * @param pageUrl
 */
	public void setPageUrl(String pageUrl);
/**
 * 
 * @return
 */
	public String getPageUrl();
	
	/**
	 * 
	 * @param pageUrl
	 */
		public void setParentPageUrl(String parentPageUrl);
	/**
	 * 
	 * @return
	 */
		public String getParentPageUrl();	
	
	/**
	 * 
	 * @param indexMap
	 */
	public void setIndexMap(
			HashMap<String, String> indexMap);
    /**
     * 
     * @return indexMap
     */
    public HashMap<String, String> getIndexMap();

    /**
     * 
     * @return
     */
    public Collection<OneTimeVO> getErrorDescriptions();
    
    /**
     * 
     * @param errorDescs
     */
    public void setErrorDescriptions(Collection<OneTimeVO> errorDescs);
    public Integer getTotalRecordsCount();
 	public void setTotalRecordsCount(int totalRecordsCount);
 	public void removeTotalRecordsCount();
} 
