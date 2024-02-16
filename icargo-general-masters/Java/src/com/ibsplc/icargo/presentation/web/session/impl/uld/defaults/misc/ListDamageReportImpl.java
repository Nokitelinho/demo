/*
 * ListDamageReportImpl.java Created on Jan 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.framework.model.UploadFileModel;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListDamageReportSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * @author A-1617
 *
 */
public class ListDamageReportImpl extends AbstractScreenSession
        implements ListDamageReportSession {
	
	private static final String LIST_DETAILS="listdamagereport";
	
	private static final String KEY_INDEXMAP="index";
	
	private static final String DAMAGE_STATUS = "uld.defaults.damageStatus";
    
	private static final String ULD_STATUS = "uld.defaults.overallStatus";
    
	private static final String REPAIR_STATUS = "uld.defaults.repairStatus";
    
    private static final String SCREENID = "uld.defaults.listdamagereport";
	
	private static final String MODULE_NAME = "uld.defaults";
	
	private static final String KEY_DAMAGEFILTERVO = "ULDDamageFilterVO";
	
	private static final String KEY_DAMAGEPICVO = "ULDDamagePictureVO";
	
	private static final String KEY_SCREENID = "screenid";
	
	private static final String KEY_FACILITYTYPE = "facilityType";
	
	//Added by A-7636 as part of ICRD-245031
	private static final String DMG_IMG_MAP = "damageImageMap";
	
	private static final String KEY_PARTYTYPE = "partyType";
	//added by A-5223 for ICRD-22824 starts
	private static final String  TOTALRECORDS = "totalRecords";
	//added by A-5223 for ICRD-22824 ends
    /**
     * @return
     */
	public HashMap<String, ArrayList<UploadFileModel>> getDamageImageMap() {
        return getAttribute(DMG_IMG_MAP);
    }
	
	public void setDamageImageMap(
    		HashMap<String, ArrayList<UploadFileModel>> damageImageMap) {
    	setAttribute(DMG_IMG_MAP, damageImageMap);
    }
    public String getScreenID() {
        return SCREENID;
    }

    /**
     * @return
     */
    public String getModuleName() {
        return MODULE_NAME;
    }

    /**
     * This method returns the ULDDamageDetailsListVO in session
     * @return Page<ClearanceListingVO>
     */

	public Page<ULDDamageDetailsListVO> getULDDamageRepairDetailsVOs(){
    	return (Page<ULDDamageDetailsListVO>)getAttribute(LIST_DETAILS);
    }
	/**
     * This method sets the ULDDamageDetailsListVO in session
     * @param paramCode
     */
	public void setULDDamageRepairDetailsVOs(Page<ULDDamageDetailsListVO> paramCode){
		setAttribute(LIST_DETAILS, (Page<ULDDamageDetailsListVO>)paramCode);
	}
	/**
     * This method removes the ULDDamageDetailsListVO in session
     */
	public void removeULDDamageRepairDetailsVOs(){
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
     * @return
     */
	public Collection<OneTimeVO> getDamageStatus(){
		return (Collection<OneTimeVO>) getAttribute(DAMAGE_STATUS);
	}
	/**
	 * @param damageStatus
	 */
	public void setDamageStatus(Collection<OneTimeVO> damageStatus){
		setAttribute(DAMAGE_STATUS,(ArrayList<OneTimeVO>) damageStatus);
	}
	/**
	 * Methods for removing status
	 */
	public void removeDamageStatus(){
		removeAttribute(DAMAGE_STATUS);
	}
	
	/**
     * @return
     */
	public Collection<OneTimeVO> getUldStatus(){
		return (Collection<OneTimeVO>) getAttribute(ULD_STATUS);
	}
	/**
	 * @param uldStatus
	 */
	public void setUldStatus(Collection<OneTimeVO> uldStatus){
		setAttribute(ULD_STATUS,(ArrayList<OneTimeVO>) uldStatus);
	}
	/**
	 * Methods for removing status
	 */
	public void removeUldStatus(){
		removeAttribute(ULD_STATUS);
	}
	
	/**
	 * @return
	 */
	public Collection<OneTimeVO> getRepairStatus(){
		return (Collection<OneTimeVO>) getAttribute(REPAIR_STATUS);
	}
	/**
	 * @param repairStatus
	 */
	public void setRepairStatus(Collection<OneTimeVO> repairStatus){
		setAttribute(REPAIR_STATUS,(ArrayList<OneTimeVO>) repairStatus);
	}
	/**
	 * Methods for removing status
	 */
	public void removeRepairStatus(){
		removeAttribute(REPAIR_STATUS);
	}
	
	/**
	 * @param uldDamageFilterVO
	 */
	public void setULDDamageFilterVO(ULDDamageFilterVO uldDamageFilterVO) {
		setAttribute(KEY_DAMAGEFILTERVO, uldDamageFilterVO);
	}
	/**
	 * @return
	 */
	public ULDDamageFilterVO getULDDamageFilterVO() {
		return getAttribute(KEY_DAMAGEFILTERVO);
	}
	/**
	 * 
	 */
	public void removeULDDamageFilterVO() {
		removeAttribute(KEY_DAMAGEFILTERVO);
	}
	/**
	 * @param uldDamagePictureVO
	 */
	public void setULDDamagePictureVO(ULDDamagePictureVO uldDamagePictureVO) {
		setAttribute(KEY_DAMAGEPICVO, uldDamagePictureVO);
	}
	/**
	 * @return
	 */
	public ULDDamagePictureVO getULDDamagePictureVO() {
		return getAttribute(KEY_DAMAGEPICVO);
	}
	/**
	 * 
	 */
	public void removeULDDamagePictureVO() {
		removeAttribute(KEY_DAMAGEPICVO);
	}
	
	/**
	 * The setter for PARENT_SCREENIDR
	 * @param screenId
	 */
	public void setScreenId(String screenId) {
		setAttribute(KEY_SCREENID, screenId);
	}
	/**
	 * The getter for PARENT_SCREENIDR
	 * @return string parentScreenId
	 */
	public String getScreenId() {
		return getAttribute(KEY_SCREENID);
	}
	/**
	 * Remove method for parentScreenId
	 */
	public void removeScreenId() {
		removeAttribute(KEY_SCREENID);
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
	public Collection<OneTimeVO> getPartyType() {
		return (Collection<OneTimeVO>) getAttribute(KEY_PARTYTYPE);
	}
	
	/**
	 * @param partyType
	 */
	public void setPartyType(Collection<OneTimeVO> partyType) {
		setAttribute(KEY_PARTYTYPE, (ArrayList<OneTimeVO>)partyType);
		
	}
    /**
     * 
     *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListDamageReportSession#getTotalRecords()
     *	Added by 			: a-5223 on 08-Nov-2012
     * 	Used for 	: getting total records
     *	Parameters	:	@return
     */
	@Override
	public Integer getTotalRecords() {
		return getAttribute(TOTALRECORDS);
	}
    /**
     * 
     *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListDamageReportSession#setTotalRecords(int)
     *	Added by 			: a-5223 on 08-Nov-2012
     * 	Used for 	: setting total records
     *	Parameters	:	@param totalRecords
     */
	@Override
	public void setTotalRecords(int totalRecords) {
		setAttribute(TOTALRECORDS,totalRecords);
		
	}

	
	
	
	
	
}
