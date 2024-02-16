/*
 * SCMReconcileSession.java Created on Aug 01, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * 
 * @author A-1496
 * 
 */
public interface SCMReconcileSession extends ScreenSession {

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
	public Page<ULDSCMReconcileVO> getSCMReconcileVOs();
/**
 * 
 * @param paramCode
 */
	public void setSCMReconcileVOs(Page<ULDSCMReconcileVO> paramCode);
/**
 * 
 * @return
 */
	public HashMap<String, String> getIndexMap();
/**
 * 
 * @param indexMap
 */
	public void setIndexMap(HashMap<String, String> indexMap);
/**
 * 
 * @return
 */
	public String getPageUrl();
/**
 * 
 * @param pageUrl
 */
	public void setPageUrl(String pageUrl);
	/**
	 * 
	 * @return
	 */
	public Page<ULDSCMReconcileVO> getSCMReconcileLovVOs();
/**
 * 
 * @param paramCode
 */
	public void setSCMReconcileLovVOs(Page<ULDSCMReconcileVO> paramCode);
	/**
	 * 
	 * @param filterVO
	 */
	public void setMessageFilterVO(SCMMessageFilterVO filterVO);
/**
 * 
 * @return
 */
	public SCMMessageFilterVO getMessageFilterVO();
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<OneTimeVO> getMessageStatus();

	/**
	 * 
	 * @param messageStatus
	 */
	public void setMessageStatus(ArrayList<OneTimeVO> messageStatus);

	/**
	 * method is used to find the Display page
	 * 
	 */
	public String getDisplayPageNumber();
	public void setDisplayPageNumber(String displayPage);
    public Integer getTotalRecordsCount();
 	public void setTotalRecordsCount(int totalRecordsCount);
 	public void removeTotalRecordsCount();
}
