/*
 * MaintainLoyaltySessionImpl.java Created on Apr 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.customermanagement.defaults.loyalty;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyAttributeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyParameterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ParameterDescriptionVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.MaintainLoyaltySession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;



/**
 * @author A-1862
 *
 */

public class MaintainLoyaltySessionImpl extends AbstractScreenSession
		implements MaintainLoyaltySession {

	private static final String MODULE = "customermanagement.defaults";

	private static final String SCREENID =
		"customermanagement.defaults.maintainloyalty";
	private static final String KEY_LOYALTYPGM = "loyaltyProgrammeVO";
	
	
	private static final String KEY_SCREENID = "screenid";
	
	
	private static final String KEY_ONETIMEVALUES
		= "customermanagement_defaults_maintainloyalty_onetimevalues";
	
	
	private static final String KEY_ATTRIBUTEVALUES
		= "customermanagement_defaults_maintainloyalty_attributeonetimevalues";
	private static final String KEY_LOYALTYNAME="loyaltyNames";
	private static final String KEY_PAGEURL="pageurl";
	
	private static final String KEY_ATTRVALUE="attribute_value";
	private static final String KEY_UNITVALUE="unit_value";
	private static final String KEY_AMTVAL="amount_value";
	private static final String KEY_POINTVAL="point_value";
	
	private static final String COLL_LOY_PRG_LOV="LoyaltyProgrammeLovVOs";
	
	private static final String KEY_LOYALTYLIST = "runningLoyaltyProgrammeLovVOs";
	
	private static final String COLL_LTY_PRM_VO="LoyaltyParameterVOs";
	private static final String COL_LTY_PRM_VOS="LoyaltyOfParameterVOs";
	private static final String KEY_ATTRIBUTESTRING
	= "customermanagement_defaults_maintainloyalty_attributestringvalues";
	
	private static final String KEY_PARAMETER
	= "parameter_value";
	
	/**
	 * 
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * 
	 */
	public String getModuleName() {
		return MODULE;
	}
	/**
	 * @return LoyaltyProgrammeVO
	 */
	public LoyaltyProgrammeVO getLoyaltyProgrammeVO() {
		return (LoyaltyProgrammeVO)getAttribute(KEY_LOYALTYPGM);
	}
	
/**
 * @param loyaltyProgrammeVO
 */
	public void setLoyaltyProgrammeVO(LoyaltyProgrammeVO loyaltyProgrammeVO) {
		 setAttribute(KEY_LOYALTYPGM,loyaltyProgrammeVO);
	}
	
	 /**
	  * @param screenId
	  */
	public void setParentScreenId(String screenId) {
		setAttribute(KEY_SCREENID, screenId);
	}
	/**
	 * 
	 */
	public String getParentScreenId() {
		return getAttribute(KEY_SCREENID);
	}
	/**
	 * 
	 */
	public void removeParentScreenId() {
		removeAttribute(KEY_SCREENID);
	}
	
 /**
  * @return 
  */
	public ArrayList<String> getLoyaltyNames(){
		return getAttribute(KEY_LOYALTYNAME);
	}
	/**
	 * @param loyaltyNames
	 */
	public void setLoyaltyNames(ArrayList<String> loyaltyNames){
		setAttribute(KEY_LOYALTYNAME,loyaltyNames);
	}
	/**
	 * 
	 */
	public String getPageURL(){
		return getAttribute(KEY_PAGEURL);
	}
	/**
	 * @param pageUrl
	 */
	public void setPageURL(String pageUrl){
		setAttribute(KEY_PAGEURL,pageUrl);
	}
	/**
	 * 
	 */
    public HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
        return getAttribute(KEY_ONETIMEVALUES);
    }

/**
 * @param oneTimeValues
 */
   
    public void setOneTimeValues(
    		HashMap<String, Collection<OneTimeVO>> oneTimeValues) {
    	setAttribute(KEY_ONETIMEVALUES, oneTimeValues);
    }
    
   /**
    * @return 
    */
    public HashMap<String, Collection<LoyaltyAttributeVO>> getAttributeValues() {
        return getAttribute(KEY_ATTRIBUTEVALUES);
    }
/**
 * @param loyaltyAttributeVOs
 */

        public void setAttributeValues(
    		HashMap<String, Collection<LoyaltyAttributeVO>> loyaltyAttributeVOs) {
    	setAttribute(KEY_ATTRIBUTEVALUES, loyaltyAttributeVOs);
    }
    /**
     * 
     */
    public HashMap<String, Collection<String>> getAttributeString() {
        return getAttribute(KEY_ATTRIBUTESTRING);
    }

/**
 * @param attributes
 */
        public void setAttributeString(
    		HashMap<String, Collection<String>> attributes) {
    	setAttribute(KEY_ATTRIBUTESTRING, attributes);
    }
    /**
     * 
     */
	public Page<LoyaltyProgrammeVO> getLoyaltyProgrammeLovVOs(){
		return getAttribute(COLL_LOY_PRG_LOV);
	}
	/**
	 * @param loyaltyProgrammeVOs
	 */
	public void setLoyaltyProgrammeLovVOs(Page<LoyaltyProgrammeVO> loyaltyProgrammeVOs){
		setAttribute(COLL_LOY_PRG_LOV,loyaltyProgrammeVOs);
	}
	
	/**
	 * @return Page<LoyaltyProgrammeVO>
	 */
    public Page<LoyaltyProgrammeVO> getRunningLoyaltyProgrammeLovVOs() {
		return (Page<LoyaltyProgrammeVO>) 
									getAttribute(KEY_LOYALTYLIST);
	}
    
    /**
	 * @param runningLoyaltyProgrammeLovVOs
	 */
    public void setRunningLoyaltyProgrammeLovVOs(Page<LoyaltyProgrammeVO> 
    							runningLoyaltyProgrammeLovVOs) {
				   setAttribute(KEY_LOYALTYLIST,
		           (Page<LoyaltyProgrammeVO>) runningLoyaltyProgrammeLovVOs);
	}
 /**
  * 
  */
	public String getAttributeValue(){
		return getAttribute(KEY_ATTRVALUE);
	}
	/**
	 * @param attrVal
	 */
	public void setAttributeValue(String attrVal){
		setAttribute(KEY_ATTRVALUE,attrVal);
	}
	/**
	 * 
	 */
	public String getUnitValue(){
		return getAttribute(KEY_UNITVALUE);
	}
	/**
	 * @param unitVal
	 */
	public void setUnitValue(String unitVal){
		setAttribute(KEY_UNITVALUE,unitVal);
	}
	/**
	 * 
	 */
	public String getAmountValue(){
		return getAttribute(KEY_AMTVAL);
	}
	/**
	 * @param amountValue
	 */
	public void setAmountValue(String amountValue){
		setAttribute(KEY_AMTVAL,amountValue);
	}
	/**
	 * 
	 */
	public String getPointsValue(){
		return getAttribute(KEY_POINTVAL);
	}
	/**
	 * @param pointVal
	 */
	public void setPointsValue(String pointVal){
		setAttribute(KEY_POINTVAL,pointVal);
	}
	/**
	 * 
	 */
	public ArrayList<LoyaltyParameterVO> getParameterVOsForDisplay(){
		return getAttribute(COLL_LTY_PRM_VO);
	}
	/**
	 * @param loyaltyParameterVOs
	 */
	public void setParameterVOsForDisplay(ArrayList<LoyaltyParameterVO> loyaltyParameterVOs){
		setAttribute(COLL_LTY_PRM_VO,loyaltyParameterVOs);
	}
	/**
	 * 
	 */
	public ArrayList<LoyaltyParameterVO> getParameterVOsForLOV(){
		return getAttribute(COL_LTY_PRM_VOS);
	}
	/**
	 * @param loyaltyParameterVOs
	 */
	public void setParameterVOsForLOV(ArrayList<LoyaltyParameterVO> loyaltyParameterVOs){
		setAttribute(COL_LTY_PRM_VOS,loyaltyParameterVOs);
	}
	/**
	 * 
	 */
	public ArrayList<ParameterDescriptionVO> getParameter(){
		return getAttribute(KEY_PARAMETER);
	}
	/***
	 * @param param
	 */
	public void setParameter(ArrayList<ParameterDescriptionVO> param){
		setAttribute(KEY_PARAMETER,param);	
	}
	
}
