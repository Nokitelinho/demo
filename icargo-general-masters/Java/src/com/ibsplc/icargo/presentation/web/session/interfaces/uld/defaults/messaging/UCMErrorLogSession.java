/*
 * UCMErrorLogSession.java Created on Jul 20, 2006
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
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1862
 * 
 */
public interface UCMErrorLogSession extends ScreenSession {
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
/**
 * 
 * @return
 */
	public String getForPic();
/**
 * 
 * @param pageurl
 */
	public void setForPic(String pageurl);
/**
 * 
 * @return
 */
	public Page<ULDFlightMessageReconcileVO> getULDFlightMessageReconcileVOs();
/**
 * 
 * @param paramCode
 */
	public void setULDFlightMessageReconcileVOs(
			Page<ULDFlightMessageReconcileVO> paramCode);
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
	public FlightFilterMessageVO getFlightFilterMessageVOSession();
/**
 * 
 * @param flightFilterMessageVO
 */
	public void setFlightFilterMessageVOSession(
			FlightFilterMessageVO flightFilterMessageVO);
/**
 * 
 * @param reconcileVO
 */
	public void setUCM1ReconcileVO(ULDFlightMessageReconcileVO reconcileVO);
/**
 * 
 * @return
 */
	public ULDFlightMessageReconcileVO getUCM1ReconcileVO();
/**
 * 
 * @return
 */
	public ULDFlightMessageReconcileVO getUCM2ReconcileVO();
/**
 * 
 * @param reconcileVO
 */
	public void setUCM2ReconcileVO(ULDFlightMessageReconcileVO reconcileVO);
	
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
}
