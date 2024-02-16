/*
 * DamageRefNoLovmpl.java Created on Jan 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.misc;

import java.util.HashMap;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageReferenceNumberLovVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.DamageRefNoLovSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * @author A-1617
 *
 */
public class DamageRefNoLovmpl extends AbstractScreenSession
        implements DamageRefNoLovSession {
	
	
	private static final String KEY_INDEXMAP="index";
    
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of maintain damage report screen
	 */
	
	private static final String LIST_DETAILS="listdamagerefnolov";
	
	private static final String SCREENID =
		"uld.defaults.damagerefnolov";
	
	/*
	 * Key for Parent Screen Id 
	 */
	private static final String KEY_PARENTSCREENID = "parentscreenid";
	
    /**
     * @return
     */
    public String getScreenID() {
        return SCREENID;
    }

    /**
     * @return
     */
    public String getModuleName() {
        return MODULE;
    }

    /**
     * This method returns the ULDDamageRepairDetailsVO in session
     * @return Page<ClearanceListingVO>
     */

	public Page<ULDDamageReferenceNumberLovVO> getULDDamageReferenceNumberLovVOs(){
    	return (Page<ULDDamageReferenceNumberLovVO>)getAttribute(LIST_DETAILS);
    }
	/**
     * This method sets the ULDDamageRepairDetailsVO in session
     * @param paramCode
     */
	public void setULDDamageReferenceNumberLovVOs(Page<ULDDamageReferenceNumberLovVO> paramCode){
		setAttribute(LIST_DETAILS, (Page<ULDDamageReferenceNumberLovVO>)paramCode);
	}
	/**
     * This method removes the ULDDamageRepairDetailsVO in session
     */
	public void removeULDDamageReferenceNumberLovVOs(){
		removeAttribute(LIST_DETAILS);
	}
	
	 /**
     * Method used to get indexMap
     * @return KEY_INDEXMAP - HashMap<String,String>
     */
	public HashMap<String,String>  getIndexMap(){
	    return (HashMap<String,String>)getAttribute(KEY_INDEXMAP);
	}

	/**
	 * Method used to set indexMap to session
	 * @param indexmap - HashMap<String,String>
	 */
	public void setIndexMap(HashMap<String,String>  indexmap) {
	    setAttribute(KEY_INDEXMAP, (HashMap<String,String>)indexmap);
	}
	/**
     * This method removes the indexMAp in session
     */
	public void removeIndexMap(){
		removeAttribute(KEY_INDEXMAP);
	}

	/**
	 * The setter for PARENT_SCREENIDR
	 * @param parentScreenId
	 */
	public void setParentScreenId(String parentScreenId) {
		setAttribute(KEY_PARENTSCREENID, parentScreenId);
	}
	/**
	 * The getter for PARENT_SCREENIDR
	 * @return string parentScreenId
	 */
	public String getParentScreenId() {
		return getAttribute(KEY_PARENTSCREENID);
	}
	/**
	 * Remove method for parentScreenId
	 */
	public void removeParentScreenId() {
		removeAttribute(KEY_PARENTSCREENID);
	}

}
