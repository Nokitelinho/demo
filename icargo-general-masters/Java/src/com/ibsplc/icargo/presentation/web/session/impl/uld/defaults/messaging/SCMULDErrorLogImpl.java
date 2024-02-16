/*
 * SCMULDErrorLogImpl.java Created on Aug 01, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.messaging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMULDErrorLogSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * 
 * @author A-1496
 * 
 * 
 */
public class SCMULDErrorLogImpl extends AbstractScreenSession implements
		SCMULDErrorLogSession {

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.scmulderrorlog";

	private static final String LIST_DETAILS = "scmReconcileDetailsVO";

	private static final String FILTER_DETAILS = "scmMessageFilterVO";

	private static final String PAGE_URL = "pageurl";

	private static final String KEY_INDEXMAP = "indexMap";
	
	private static final String PARENTPAGE_URL = "parentpageurl";
	
	private static final String ERROR_DESCS = "error_descs";
	
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
	public Page<ULDSCMReconcileDetailsVO> getSCMReconcileDetailVOs() {
		return (Page<ULDSCMReconcileDetailsVO>) getAttribute(LIST_DETAILS);
	}
	/**
	 * @param paramCode
	 */
	public void setSCMReconcileDetailVOs(
			Page<ULDSCMReconcileDetailsVO> paramCode) {
		setAttribute(LIST_DETAILS, (Page<ULDSCMReconcileDetailsVO>) paramCode);
	}
	/**
	 * @return
	 */
	public SCMMessageFilterVO getSCMULDFilterVO() {
		return getAttribute(FILTER_DETAILS);
	}
	/**
	 * @param filterVO
	 */
	public void setSCMULDFilterVO(SCMMessageFilterVO filterVO) {
		setAttribute(FILTER_DETAILS, filterVO);
	}
/**
 * @param pageUrl
 */
	public void setPageUrl(String pageUrl) {
		setAttribute(PAGE_URL, pageUrl);

	}
	/**
	 * @return
	 */
	public String getPageUrl() {
		return getAttribute(PAGE_URL);
	}

	/**
	 * This method returns the indexMap
	 * @return indexMap - HashMap<String, String>
	 */
	public HashMap<String, String> getIndexMap(){
		return (HashMap<String, String>)getAttribute(KEY_INDEXMAP);
	}
	
	/**
	 * This method is used to set indexMap to the session
	 * @param indexMap - HashMap<String, String>
	 */
	public void setIndexMap(HashMap<String, String> indexMap){
		setAttribute(KEY_INDEXMAP,indexMap);
	}

	/**
	 * @param pageUrl
	 */
	public String getParentPageUrl() {
		return getAttribute(PARENTPAGE_URL);
	}
	
	/**
	 *  @return
	 */
	public void setParentPageUrl(String parentPageUrl) {
		setAttribute(PARENTPAGE_URL, parentPageUrl);
		
	}

	public Collection<OneTimeVO> getErrorDescriptions() {
		return (Collection<OneTimeVO>)getAttribute(ERROR_DESCS);
	}

	public void setErrorDescriptions(Collection<OneTimeVO> errorDescs) {
		setAttribute(ERROR_DESCS,(ArrayList<OneTimeVO>) errorDescs);
		
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
