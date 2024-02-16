/*
 * UCMErrorLogSessionImpl.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.messaging;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMErrorLogSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1862
 * 
 */
public class UCMErrorLogSessionImpl extends AbstractScreenSession implements
		UCMErrorLogSession {

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.ucmerrorlog";

	private static final String KEY_PAGEURL = "pageurl";

	private static final String KEY_FORPIC = "pictureURL";

	private static final String LIST_DETAILS = "ULDFlightMessageReconcileVO";

	private static final String HASHMAP_INDEXMAP = "indexMap";

	private static final String KEY_FILTERDETAILS = "filterDetails";

	private static final String KEY_RECONCILEVOONE = "reconcilevo1";

	private static final String KEY_RECONCILEVOTWO = "reconcilevo2";

	private static final String KEY_STATUS = "messageStatus";

	/**
	 * @return ScreenID
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * Method to get ProductName
	 * 
	 * @return ProductName
	 */
	public String getModuleName() {
		return MODULE;
	}

	/**
	 * @return
	 */
	public String getPageURL() {
		return getAttribute(KEY_PAGEURL);
	}

	/**
	 * @param pageUrl
	 */
	public void setPageURL(String pageUrl) {
		setAttribute(KEY_PAGEURL, pageUrl);
	}

	/**
	 * @return
	 */
	public String getForPic() {
		return getAttribute(KEY_FORPIC);
	}

	/**
	 * @param pic
	 */
	public void setForPic(String pic) {
		setAttribute(KEY_FORPIC, pic);
	}

	/**
	 * @return
	 */
	public Page<ULDFlightMessageReconcileVO> getULDFlightMessageReconcileVOs() {
		return (Page<ULDFlightMessageReconcileVO>) getAttribute(LIST_DETAILS);
	}

	/**
	 * @param paramCode
	 */
	public void setULDFlightMessageReconcileVOs(
			Page<ULDFlightMessageReconcileVO> paramCode) {
		setAttribute(LIST_DETAILS,
				(Page<ULDFlightMessageReconcileVO>) paramCode);
	}

	/**
	 * 
	 * 
	 */
	public void removeULDFlightMessageReconcileVOs() {
		removeAttribute(LIST_DETAILS);
	}

	/**
	 * @return
	 */
	public HashMap<String, String> getIndexMap() {
		return getAttribute(HASHMAP_INDEXMAP);
	}

	/**
	 * @param indexMap
	 */
	public void setIndexMap(HashMap<String, String> indexMap) {
		setAttribute(HASHMAP_INDEXMAP, indexMap);
	}

	/**
	 * @return
	 */
	public FlightFilterMessageVO getFlightFilterMessageVOSession() {
		return getAttribute(KEY_FILTERDETAILS);
	}

	/**
	 * @param flightFilterMessageVO
	 */
	public void setFlightFilterMessageVOSession(
			FlightFilterMessageVO flightFilterMessageVO) {
		setAttribute(KEY_FILTERDETAILS, flightFilterMessageVO);
	}

	/**
	 * @param reconcileVO1
	 */
	public void setUCM1ReconcileVO(ULDFlightMessageReconcileVO reconcileVO1) {
		setAttribute(KEY_RECONCILEVOONE, reconcileVO1);
	}

	/**
	 * @param reconcileVO2
	 */
	public void setUCM2ReconcileVO(ULDFlightMessageReconcileVO reconcileVO2) {
		setAttribute(KEY_RECONCILEVOTWO, reconcileVO2);
	}

	/**
	 * @return
	 */
	public ULDFlightMessageReconcileVO getUCM1ReconcileVO() {
		return getAttribute(KEY_RECONCILEVOONE);
	}

	/**
	 * @return
	 */
	public ULDFlightMessageReconcileVO getUCM2ReconcileVO() {
		return getAttribute(KEY_RECONCILEVOTWO);
	}

	/**
	 * @return
	 */
	public ArrayList<OneTimeVO> getMessageStatus() {
		return getAttribute(KEY_STATUS);
	}

	/**
	 * @param messageStatus
	 */
	public void setMessageStatus(ArrayList<OneTimeVO> messageStatus) {
		setAttribute(KEY_STATUS, messageStatus);
	}

}
