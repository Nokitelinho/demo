/*
 * SCMReconcileSessionImpl.java Created on Aug 01, 2006
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
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMReconcileSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * 
 * @author A-1496
 * 
 */
public class SCMReconcileSessionImpl extends AbstractScreenSession implements
		SCMReconcileSession {

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.scmreconcile";

	private static final String LIST_DETAILS = "scmReconcileDetailsVO";

	private static final String KEY_INDEXMAP = "indexmap";

	private static final String PAGE_URL = "pageurl";

	private static final String LOV_DETAILS = "lov_details";

	private static final String KEY_MSGFILTERVO = "messageFilterVO";
	
	private static final String KEY_STATUS="messageStatus";

	private static final String DISPLAY_PAGE_NUMBER="displaypagenumber";
	private static final String TOTAL_RECORDS_COUNT = "totalRecordsCount";
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
	public Page<ULDSCMReconcileVO> getSCMReconcileVOs() {
		return (Page<ULDSCMReconcileVO>) getAttribute(LIST_DETAILS);
	}
	/**
	 * @param paramCode
	 */
	public void setSCMReconcileVOs(Page<ULDSCMReconcileVO> paramCode) {
		setAttribute(LIST_DETAILS, (Page<ULDSCMReconcileVO>) paramCode);
	}
	/**
	 * @return
	 */
	public Page<ULDSCMReconcileVO> getSCMReconcileLovVOs() {
		return (Page<ULDSCMReconcileVO>) getAttribute(LOV_DETAILS);
	}
	/**
	 * @param paramCode
	 */
	public void setSCMReconcileLovVOs(Page<ULDSCMReconcileVO> paramCode) {
		setAttribute(LOV_DETAILS, (Page<ULDSCMReconcileVO>) paramCode);
	}
	/**
	 * @return
	 */
	public HashMap<String, String> getIndexMap() {
		return getAttribute(KEY_INDEXMAP);
	}
	/**
	 * @param indexMap
	 */
	public void setIndexMap(HashMap<String, String> indexMap) {
		setAttribute(KEY_INDEXMAP, indexMap);
	}
	/**
	 * @return
	 */
	public String getPageUrl() {
		return getAttribute(PAGE_URL);
	}
	/**
	 * @param pageUrl
	 */
	public void setPageUrl(String pageUrl) {
		setAttribute(PAGE_URL, pageUrl);
	}
/**
 * @param filterVO
 */
	public void setMessageFilterVO(SCMMessageFilterVO filterVO) {
		setAttribute(KEY_MSGFILTERVO, filterVO);
	}
	/**
	 * @return
	 */
	public SCMMessageFilterVO getMessageFilterVO() {
		return getAttribute(KEY_MSGFILTERVO);
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
	public String getDisplayPageNumber() {
		// TODO Auto-generated method stub
		return getAttribute(DISPLAY_PAGE_NUMBER);
	}
	public void setDisplayPageNumber(String displayPage) {
		setAttribute(DISPLAY_PAGE_NUMBER, displayPage);
	}

	public Integer getTotalRecordsCount() {
		return getAttribute(TOTAL_RECORDS_COUNT);
	}
	public void setTotalRecordsCount(int totalRecordsCount) {
		setAttribute(TOTAL_RECORDS_COUNT, totalRecordsCount);
	}
	public void removeTotalRecordsCount(){
		removeAttribute(TOTAL_RECORDS_COUNT);
    }
}
