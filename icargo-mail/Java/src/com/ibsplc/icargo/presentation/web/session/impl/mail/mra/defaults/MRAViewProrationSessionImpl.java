/*
 * MRAViewProrationSessionImpl.java Created on Jul 17, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.AWMProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SurchargeProrationDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRAViewProrationSession;


/**
 * @author A-3251
 *
 */
public class MRAViewProrationSessionImpl extends AbstractScreenSession implements MRAViewProrationSession{

	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.viewproration";
	
	/**
	 * Constant for the session variable memoFilterVO
	 */
	private static final String KEY_LISTFILTER = "prorationFilterVO";
	
	/**
	 * Constant for the session variable memoInInvoiceVOs
	 */
	private static final String KEY_PRORATIONVOS = "prorationVOs";
	
	
	/**
	 * Constant for the session variable category
	 */
	private static final String KEY_CATEGORY = "category";
	
	
	private static final String KEY_ONETIME_VOS = "onetimevalues";
	
	/**
	 * Constant for the session variable subClass
	 */
	private static final String KEY_SUBCLASS = "subClass";
	
	
	/**
	 * Constant for the session variable prorationType
	 */
	private static final String KEY_PRORATIONTYPE = "prorationType";
	
	
	/**
	 * Constant for the session variable prorationPayType
	 */
	private static final String KEY_PRORATIONPAYTYPE = "prorationPayType";
	
	/**
	 * Constant for the session variable primaryProrationVOs
	 */
	private static final String KEY_PRIMARYPRORATIONVOS = "primaryProrationVOs";
	private static final String KEY_SURCHARGEPRORATION="surchargeproration";
	
	/**
	 * Constant for the session variable secondaryProrationVOs
	 */
	private static final String KEY_SECONDARYPRORATIONVOS = "secondaryProrationVOs";
	private static final  String KEY_PARENTSCREENID = "parentscreenID";
	private static final  String KEY_SECTORDETAILS = "sectordetails";
	/**
	 *  @Author A-6991 
	 */
	private static final String KEY_SYSPARAMETERS="systemParameterByCodes";
	private static final String KEY_AWMPRORATIONVOS="awmProrationVOs";

	/*
     * (non-Javadoc)
     *
     * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getScreenID()
     */
	/**
	 * @return screenID 
	 */
    public String getScreenID() {

        return SCREEN_ID;
    }

    /**
     * @return moduleName
     */
    /*
     * (non-Javadoc)
     *
     * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getModuleName()
     */
    public String getModuleName() {
        return MODULE_NAME;
    }
    
    
    
    /**
     * 
     * @return prorationFilterVO
     */
    
	public ProrationFilterVO getProrationFilterVO() {
		return getAttribute(KEY_LISTFILTER);
	}
	/**
	 * @param prorationFilterVO The  prorationFilterVO to set.
	 */
	public void setProrationFilterVO(ProrationFilterVO prorationFilterVO) {
		setAttribute(KEY_LISTFILTER,prorationFilterVO);
	}
	
	/**
	 * @author A-2122
	 */	
	
	public void removeProrationFilterVO() {
		// TODO Auto-generated method stub
		removeAttribute(KEY_LISTFILTER);
	}
   
	/**
	 * @param prorationVOs 
	 */
	public void setProrationVOs(ArrayList<ProrationDetailsVO> prorationVOs){
		setAttribute(KEY_PRORATIONVOS,prorationVOs);
	}
	/**
	 * @return ArrayList<ProrationDetailsVO>
	 */
	public ArrayList<ProrationDetailsVO> getProrationVOs(){
		return getAttribute(KEY_PRORATIONVOS);
	}
	
	/**
	 * @author A-2122
	 *
	 */
	public void removeProrationVOs(){
		
		removeAttribute(KEY_PRORATIONVOS);
		
	}
	
	
	/**
	 * @return Returns the category
	 */
	public ArrayList<OneTimeVO> getCategory() {
		return getAttribute(KEY_CATEGORY);
	}
	
	/**
	 * @param category The  category to set.
	 */
	public void setCategory(ArrayList<OneTimeVO> category) {
		
		// TODO Auto-generated method stub
		setAttribute(KEY_CATEGORY,category);
		
	}
	
	
	/**
	 * @return Returns the subClass
	 */
	public ArrayList<OneTimeVO> getSubClass() {
		return getAttribute(KEY_SUBCLASS);
	}
	
	/**
	 * @param subClass The  subClass to set.
	 */
	public void setSubClass(ArrayList<OneTimeVO> subClass) {
		
		// TODO Auto-generated method stub
		setAttribute(KEY_SUBCLASS,subClass);
		
	}
	
	
	
	/**
	 * @return Returns the prorationType
	 */
	public ArrayList<OneTimeVO> getProrationType() {
		return getAttribute(KEY_PRORATIONTYPE);
	}
	
	/**
	 * @param prorationType The  prorationType to set.
	 */
	public void setProrationType(ArrayList<OneTimeVO> prorationType) {
		
		// TODO Auto-generated method stub
		setAttribute(KEY_PRORATIONTYPE,prorationType);
		
	}

	/**
	 * @return Returns the prorationPayType
	 */
	public ArrayList<OneTimeVO> getProrationPayType() {	
		return getAttribute(KEY_PRORATIONPAYTYPE);
	}
	
	/**
	 * @param prorationPayType The  prorationPayType to set.
	 */
	public void setProrationPayType(ArrayList<OneTimeVO> prorationPayType) {
		
		// TODO Auto-generated method stub
		setAttribute(KEY_PRORATIONPAYTYPE,prorationPayType);
		
	}
	/**
	 * @param primaryProrationVOs 
	 */
	public void setPrimaryProrationVOs(Collection<ProrationDetailsVO> primaryProrationVOs){
		setAttribute(KEY_PRIMARYPRORATIONVOS,(ArrayList<ProrationDetailsVO>)primaryProrationVOs);
	}
	/**
	 * @return Collection<ProrationDetailsVO>
	 */
	public Collection<ProrationDetailsVO> getPrimaryProrationVOs(){
		return (Collection<ProrationDetailsVO>)getAttribute(KEY_PRIMARYPRORATIONVOS);
	}
	
	/**
	 * method to remove primaryProrationVOs
	 *
	 */
	public void removePrimaryProrationVOs(){
		
		removeAttribute(KEY_PRIMARYPRORATIONVOS);
		
	}
	
	/**
	 * @param secondaryProrationVOs 
	 */
	public void setSecondaryProrationVOs(Collection<ProrationDetailsVO> secondaryProrationVOs){
		setAttribute(KEY_SECONDARYPRORATIONVOS,(ArrayList<ProrationDetailsVO>)secondaryProrationVOs);
	}
	/**
	 * @return Collection<ProrationDetailsVO>
	 */
	public Collection<ProrationDetailsVO> getSecondaryProrationVOs(){
		return (Collection<ProrationDetailsVO>)getAttribute(KEY_SECONDARYPRORATIONVOS);
	}
	
	/**
	 * method to remove secondaryProrationVOs
	 *
	 */
	public void removeSecondaryProrationVOs(){
		
		removeAttribute(KEY_SECONDARYPRORATIONVOS);
		
	}
	
	
	/**
	 * @author A-3447 for getting one time into the session
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs() {

		return getAttribute(KEY_ONETIME_VOS);

	}

	/**
	 * @param OneTimeVO
	 *            for setting one times
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs) {

		setAttribute(KEY_ONETIME_VOS, oneTimeVOs);

	}

	/**
	 * for removing one times
	 */
	public void removeOneTimeVOs() {

		removeAttribute(KEY_ONETIME_VOS);

	}
	
	 public void setParentScreenID(String parentScreenID) {
	    	setAttribute(KEY_PARENTSCREENID, parentScreenID);
	 }
	
     public String getParentScreenID() {
    	return getAttribute(KEY_PARENTSCREENID);
     }
	
	 public void removeParentScreenID() {
	    	removeAttribute(KEY_PARENTSCREENID);
	    	
	 } 
    /**
     * 
     * @author A-5255
     * @return
     */
	public  ArrayList<SurchargeProrationDetailsVO> getSurchargeProrationVOs(){
		return getAttribute(KEY_SURCHARGEPRORATION);
	}
	/**
	 * 
	 * @author A-5255
	 * @param surchargeProrationVOs
	 */
	public  void setSurchargeProrationVOs(ArrayList<SurchargeProrationDetailsVO> surchargeProrationVOs){
		setAttribute(KEY_SURCHARGEPRORATION, surchargeProrationVOs);
	}
	/*
	 * 
	 */
	public void removSurchargeProrationVOs() {
    	removeAttribute(KEY_SURCHARGEPRORATION);
    	
 }

	/**
	  * @author A-5255
	  * @param sectorDetails
	  * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRAViewProrationSession#setSectorDetails(java.util.HashMap)
	  */
	public void setSectorDetails(HashMap<String, String> sectorDetails) {
		
		setAttribute(KEY_SECTORDETAILS, sectorDetails);
	}

	/**
	  * @author A-5255
	  * @return
	  * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRAViewProrationSession#getSectorDetails()
	  */
	public HashMap<String, String> getSectorDetails() {
		return getAttribute(KEY_SECTORDETAILS);
	}

	/**
	  * @author A-5255
	  * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRAViewProrationSession#removeSectorDetails()
	  */
	public void removeSectorDetails() {
		removeAttribute(KEY_SECTORDETAILS);
	    	
	 } 
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRAViewProrationSession#getSystemparametres()
	 *	Added by 			: A-6991 on 08-Jun-2017
	 * 	Used for 	:
	 *	Parameters	:	@return
	 */
	public HashMap<String, String> getSystemparametres(){
		return getAttribute(KEY_SYSPARAMETERS);
	}
	public void setSystemparametres(HashMap<String, String> sysparameters){
		setAttribute(KEY_SYSPARAMETERS, sysparameters);
	}				
	/**
	 * @author A-7371
	 * Parameters	:	@return
	 */
	public Collection<AWMProrationDetailsVO> getAWMProrationVOs(){
		return (Collection<AWMProrationDetailsVO>)getAttribute(KEY_AWMPRORATIONVOS);
	}
/**
 * 
 * @param awmProrationVOs
 */
	public void setAWMProrationVOs(Collection<AWMProrationDetailsVO> awmProrationVOs){		
		setAttribute(KEY_AWMPRORATIONVOS,(ArrayList<AWMProrationDetailsVO>) awmProrationVOs);
	}

}
