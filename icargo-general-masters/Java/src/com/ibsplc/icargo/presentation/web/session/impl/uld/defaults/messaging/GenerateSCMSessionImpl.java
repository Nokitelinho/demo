/*
 * GenerateSCMSessionImpl.java Created on Aug 01, 2006
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
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.GenerateSCMSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1862
 * 
 */
public class GenerateSCMSessionImpl extends AbstractScreenSession implements
		GenerateSCMSession {

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.generatescm";

	private static final String KEY_PAGEURL = "pageurl";

	private static final String KEY_EXTRAULD = "ExtraUld";

	private static final String KEY_MISSINGULD = "MissingUld";

	private static final String KEY_SYSSTOCK = "SystemStock";
	
	private static final String KEY_CHANGED_SYSSTOCK = "ChangedSystemStock";
 
	private static final String KEY_MSGFILTERVO = "messageFilterVO";
	
	private static final String KEY_INDEXMAP = "indexMap";
	
	private static final String KEY_FACILITY_TYPE = "facilityType";
	private static final String KEY_SCM_ULD_STATUS = "uldStatusList";
	private static final String KEY_ULDFILTERVO = "uldFilterVO";

	private static final String  KEY_TOTAL_RECORDS = "uld.defaults.generateScm.totalrecords";
	
	private static final String  KEY_UPDATED_STOCK = "uld.defaults.generateScm.updatedstock";
	
	private static final String KEY_NEW_SYSSTOCK = "NewSystemStock";

	private static final String  KEY_LISTED_DYNAMIC_QUERY = "uld.defaults.generateScm.listeddynamicquery";
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
	 * @return the keyListedDynamicQuery
	 */
	public String getKeyListedDynamicQuery() {
		return getAttribute(KEY_LISTED_DYNAMIC_QUERY);
	}
	public void setKeyListedDynamicQuery(String keyListedDynamicQuery) {
		setAttribute(KEY_LISTED_DYNAMIC_QUERY, keyListedDynamicQuery);
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
	public ArrayList<String> getExtraUld() {
		return (ArrayList<String>) getAttribute(KEY_EXTRAULD);
	}
	/**
	 * @param extraulds
	 */
	public void setExtraUld(ArrayList<String> extraulds) {
		setAttribute(KEY_EXTRAULD, (ArrayList<String>) extraulds);
	}
	/**
	 * @return
	 */
	public ArrayList<String> getMissingUld() {
		return (ArrayList<String>) getAttribute(KEY_MISSINGULD);
	}
	/**
	 * @param missingulds
	 */
	public void setMissingUld(ArrayList<String> missingulds) {
		setAttribute(KEY_MISSINGULD, (ArrayList<String>) missingulds);
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
	 * @return
	 */
	public Page<ULDVO> getSystemStock() {
		return (Page<ULDVO>) getAttribute(KEY_SYSSTOCK);
	}
	/**
	 * @param uldVOs
	 */
	public void setSystemStock(Page<ULDVO> uldVOs) {
		setAttribute(KEY_SYSSTOCK, uldVOs);
	}
	public Collection<ULDVO> getChangedSystemStock() {
		return (Collection<ULDVO>)getAttribute(KEY_CHANGED_SYSSTOCK);
	}

	public void setChangedSystemStock(Collection<ULDVO> uldNumbers) {
		setAttribute(KEY_CHANGED_SYSSTOCK,(ArrayList<ULDVO>)uldNumbers);
		
	}

	public Collection<OneTimeVO> getFacilityType() { 
		return (ArrayList<OneTimeVO>)getAttribute(KEY_FACILITY_TYPE);
	}

	public void setFacilityType(Collection<OneTimeVO> facilityType) {
		setAttribute(KEY_FACILITY_TYPE,(ArrayList<OneTimeVO>)facilityType);
		
	}
	//added by a-3045 for bug 24468 starts
	/**
	 * @param filterVO
	 */
	public void setULDListFilterVO(ULDListFilterVO filterVO) {
		setAttribute(KEY_ULDFILTERVO, filterVO);
	}
	/**
	 * @return
	 */
	public ULDListFilterVO getULDListFilterVO() {
		return getAttribute(KEY_ULDFILTERVO);
	}
	
	//added by a-3045 for bug 24468 ends
	
	/**
	 * 
	 * @return
	 */
	public Integer getTotalRecords(){
		return getAttribute(KEY_TOTAL_RECORDS);
	}
	
	/**
	 * 
	 * @param totalRecords
	 */
	public void setTotalRecords(Integer totalRecords){
		setAttribute(KEY_TOTAL_RECORDS, totalRecords);
	}

	public void setUpdatedULDStocks(HashMap<String, ULDVO> updatedULDs) {
		setAttribute(KEY_UPDATED_STOCK, updatedULDs);
		
	}

	public HashMap<String, ULDVO> getUpdatedULDStocks() {
		return getAttribute(KEY_UPDATED_STOCK);
	}
	
	/**
	 * 
	 */
	public Collection<ULDVO> getNewSystemStock() {
		return (Collection<ULDVO>)getAttribute(KEY_NEW_SYSSTOCK);
	}

	/**
	 * 
	 */
	public void setNewSystemStock(Collection<ULDVO> newuldNumbers) {
		setAttribute(KEY_NEW_SYSSTOCK,(ArrayList<ULDVO>)newuldNumbers);
		
	}

	@Override
	public Collection<OneTimeVO> getUldStatusList() {
		return (ArrayList<OneTimeVO>)getAttribute(KEY_SCM_ULD_STATUS);
	}

	@Override
	public void setUldStatusList(Collection<OneTimeVO> uldStatusList) {
		setAttribute(KEY_SCM_ULD_STATUS,(ArrayList<OneTimeVO>)uldStatusList);
		
	}
}
