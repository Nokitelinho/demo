/*
 * ListRepairReportSessionImpl.java Created on Jan 18, 2006
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
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.UldDmgRprFilterVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListRepairReportSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * @author A-2046
 *
 */
public class ListRepairReportSessionImpl extends AbstractScreenSession
        implements ListRepairReportSession {
	
	private static final String LIST_DETAILS="listdamagereport";
	
	private static final String KEY_INDEXMAP="index";
	
	private static final String DAMAGE_STATUS = "damage_status";
    
	private static final String ULD_STATUS = "uld_status";
    
	private static final String REPAIR_HEAD = "repair_head";
    /*
	 * Key for Parent Screen Id 
	 */
	private static final String KEY_SCREENID = "screenid";
	
	private static final String SCREENID= "uld.defaults.listrepairreport";
	
	private static final String KEY_REPAIRFILTERVO = "ULDRepairFilterVO";
	
	private static final String KEY_DAMAGEREPAIRFILTERVO = "UldDmgRprFilterVO";
	private static final String  KEY_TOTAL_RECORDS = "totalRecords";
	
	/**
     * @return
     */
    public String getScreenID() {
        return null;
    }

    /**
     * @return
     */
    public String getModuleName() {
        return null;
    }

    /**
     * @return
     */

	public Page<ULDRepairDetailsListVO> getULDDamageRepairDetailsVOs(){
    	return (Page<ULDRepairDetailsListVO>)getAttribute(LIST_DETAILS);
    }
	/**
     * This method sets the ULDRepairDetailsListVO in session
     * @param paramCode
     */
	public void setULDDamageRepairDetailsVOs(Page<ULDRepairDetailsListVO> paramCode){
		setAttribute(LIST_DETAILS, (Page<ULDRepairDetailsListVO>)paramCode);
	}
	/**
     * This method removes the ULDRepairDetailsListVO in session
     */
	public void removeULDDamageRepairDetailsVOs(){
		removeAttribute(LIST_DETAILS);
	}
	
	/**
     * @return
     */
	public HashMap<String,String>  getIndexMap(){
	    return (HashMap<String,String>)getAttribute(KEY_INDEXMAP);
	}

	/**
	 * Method used to set indexMap to session
	 * @param indexmap
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
	public Collection<OneTimeVO> getRepairStatus(){
		return (Collection<OneTimeVO>) getAttribute(DAMAGE_STATUS);
	}
	/**
	 * @param repairStatus
	 */
	public void setRepairStatus(Collection<OneTimeVO> repairStatus){
		setAttribute(DAMAGE_STATUS,(ArrayList<OneTimeVO>) repairStatus);
	}
	/**
	 * Methods for removing status
	 */
	public void removeRepairStatus(){
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
	public Collection<OneTimeVO> getRepairHead(){
		return (Collection<OneTimeVO>) getAttribute(REPAIR_HEAD);
	}
	/**
	 * @param repairHead
	 */
	public void setRepairHead(Collection<OneTimeVO> repairHead){
		setAttribute(REPAIR_HEAD,(ArrayList<OneTimeVO>) repairHead);
	}
	/**
	 * Methods for removing status
	 */
	public void removeRepairHead(){
		removeAttribute(REPAIR_HEAD);
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
	 * @param uldRepairFilterVO
	 */
	public void setULDRepairFilterVO(ULDRepairFilterVO uldRepairFilterVO) {
		setAttribute(KEY_REPAIRFILTERVO, uldRepairFilterVO);
	}
	/**
     * @return
     */
	public ULDRepairFilterVO getULDRepairFilterVO() {
		return getAttribute(KEY_REPAIRFILTERVO);
	}
	/**
	 * 
	 */
	public void removeULDRepairFilterVO() {
		removeAttribute(KEY_REPAIRFILTERVO);
	}
	/**
	 * @author a-3093
	 * @return Returns the kEY_DAMAGEREPAIRFILTERVO.
	 */
	public UldDmgRprFilterVO getUldDmgRprFilterVO() {
		return getAttribute(KEY_DAMAGEREPAIRFILTERVO);
				
	}
	/**
	 * @author a-3093
     * @return
     */

	public void setUldDmgRprFilterVO(UldDmgRprFilterVO uldRepairFilterVO) {
		setAttribute(KEY_DAMAGEREPAIRFILTERVO, uldRepairFilterVO);
		// To be reviewed Auto-generated method stub		
	}
	/**
	  * Added by A-5214 as part from the ICRD-22824
	  * This method is used to set total records values in session
	  * @param int
	*/
	public void setTotalRecords(int totalRecords){
    setAttribute(KEY_TOTAL_RECORDS, Integer.valueOf(totalRecords));
 }
	
	/**
	  * Added by A-5214 as part from the ICRD-22824
	  * This method is used to get total records values from session
	  * from session
	  * @return Integer
	*/
	
	public Integer getTotalRecords() {
		return (Integer)getAttribute(KEY_TOTAL_RECORDS);
	}
}
