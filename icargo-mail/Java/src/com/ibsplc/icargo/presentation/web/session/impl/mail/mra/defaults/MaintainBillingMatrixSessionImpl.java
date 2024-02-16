/*
 * MaintainBillingMatrixSessionImpl.java Created on Feb 28, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineDetailVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;



/**
 * @author A-2398
 *
 */
public class MaintainBillingMatrixSessionImpl 
	extends AbstractScreenSession implements MaintainBillingMatrixSession {
    private static final String MODULE_NAME = "mailtracking.mra.defaults";

    private static final String SCREENID = 
    	"mailtracking.mra.defaults.maintainbillingmatrix";
    
    private static final String KEY_BLG_MATRIX_DETAILS=
    	"billingMatrixDetails";

    private static final String KEY_BLG_LINE_DETAILS = 
    	"billingLineDetailsPage";
    
    private static final String KEY_BLG_LINE_CHARGE_DETAILS = 
        	"billingLineChargeDetailsPage";
    private static final String KEY_WGTBRK_DETAILS_SURCHARGE = 
        	"weightbreakdetailsforsurcharge";
    private static final String KEY_WGTBRK_DETAILS_MAILCHARGE = 
        	"weightbreakdetailsformailcharge";
    // Added for ICRD-76551
    public static final String KEY_SELECTED_BLG_LINE= "selectedBillingLineVO";
    private static final String KEY_ONETIME_VOs = "onetimevos";	
    private static final String KEY_RATINGBASIS ="ratingBasis";
    private static final String KEY_PARENTSCREENID="parentscreenid";
    private static final String KEY_SYSPARAMETERS="systemParameterByCodes";//added by a-7871 for ICRD-214766
    /**
     * indexMap
     */
    public static final String KEY_INDEXMAP = "indexMap";

    public static final String KEY_BLG_MATRIX_FILTER= "filterVo";

    /**
     * 
     */
    public MaintainBillingMatrixSessionImpl() {
        super();

    }

    
    /**
     * @param blgMtxVO
     */
    public void setBillingMatrixVO(BillingMatrixVO blgMtxVO){
    	setAttribute(KEY_BLG_MATRIX_DETAILS,(BillingMatrixVO)blgMtxVO);
    }
    
    public BillingMatrixVO getBillingMatrixVO(){
    	return (BillingMatrixVO)getAttribute(KEY_BLG_MATRIX_DETAILS);
    }
   

    /**
     * 
     */
    public void removeBillingMatrixVO(){
    	removeAttribute(KEY_BLG_MATRIX_DETAILS);
    }
    /**
     * This method is used for PageAwareMultiMapper to get the Index Map
     * @return  HashMap<String,String>
     */
    public HashMap<String,String>getIndexMap(){
    	 return (HashMap<String,String>)getAttribute(KEY_INDEXMAP);
    }

    /**Sets the hashmap for Absolute index of page
     * @param indexMap
     */
    public void setIndexMap(HashMap<String,String>indexMap){
    	 setAttribute(KEY_INDEXMAP, (HashMap<String,String>)indexMap);
    }
    /**
     * Removes the hashmap for Absolute index of page
     *
     */
    public void removeIndexMap(){
    	removeAttribute(KEY_INDEXMAP);
    }
    /**
     * This method is used for getBillingLineChargeDetails to get the  BillingLineChargeDetails
     * @return  HashMap<String,BillingLineDetailVO>
     */
    public HashMap<String,BillingLineDetailVO> getBillingLineChargeDetails(){
    	 return (HashMap<String,BillingLineDetailVO>)getAttribute(KEY_BLG_LINE_CHARGE_DETAILS);
    }

    /**Sets the hashmap for Absolute index of page
     * @param indexMap
     */
    public void setBillingLineChargeDetails(HashMap<String,BillingLineDetailVO>indexMap){
    	 setAttribute(KEY_BLG_LINE_CHARGE_DETAILS, (HashMap<String,BillingLineDetailVO>)indexMap);
    }
    /**
     * Removes the hashmap for Absolute index of page
     *
     */
    public void removeBillingLineChargeDetails(){
    	removeAttribute(KEY_BLG_LINE_CHARGE_DETAILS);
    }
    
    /**
     * @param billingLineDetailsPage
     */
    public void setBillingLineDetails(Page<BillingLineVO> billingLineDetailsPage){
    	setAttribute(KEY_BLG_LINE_DETAILS,(Page<BillingLineVO>)billingLineDetailsPage);
    }
    
    /**
     * @return
     */
    public Page<BillingLineVO> getBillingLineDetails(){
    	return (Page<BillingLineVO>)getAttribute(KEY_BLG_LINE_DETAILS);
    }
    
    /**
     * 
     */
    public void removeBillingLneDetails(){
    	removeAttribute(KEY_BLG_LINE_DETAILS);
    }
    /**
	 * 
	 * @return
	 */	 

	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){

		return getAttribute(KEY_ONETIME_VOs);

	}

	
	/**
	 * 
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){

		setAttribute(KEY_ONETIME_VOs, oneTimeVOs);

	}
	/**
     * @param blgMtxVO
     */
    public void setBillingMatrixFilterVO(BillingMatrixFilterVO blgMtxFilterVO){
    	setAttribute(KEY_BLG_MATRIX_FILTER,(BillingMatrixFilterVO)blgMtxFilterVO);
    }
    
    public BillingMatrixFilterVO getBillingMatrixFilterVO(){
    	return (BillingMatrixFilterVO)getAttribute(KEY_BLG_MATRIX_FILTER);
    }
   

    /**
     * 
     */
    public void removeBillingMatrixFilterVO(){
    	removeAttribute(KEY_BLG_MATRIX_FILTER);
    }
/**@author A-2398
 * 
 *
 */
	public void removeOneTimeVOs() {

		removeAttribute(KEY_ONETIME_VOs);

	}
    
    /**
     * @return String
     */
    @Override
    public String getModuleName() {
        return MODULE_NAME;
    } 
    /**
	 * @return
	 */
    @Override
    public String getScreenID() {

        return SCREENID;
    }


	/**
	  * @author A-5255
	  * @return
	  * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession#getBillingLineWeightBreakDetails()
	  */
	
	@Override
	public ArrayList<BillingLineChargeVO> getBillingLineSurWeightBreakDetails() {
		return getAttribute(KEY_WGTBRK_DETAILS_SURCHARGE);
	}


	/**
	  * @author A-5255
	  * @param billingLineChargeVOs
	  * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession#setBillingLineWeightBreakDetails(java.util.ArrayList)
	  */
	
	@Override
	public void setBillingLineSurWeightBreakDetails(
			ArrayList<BillingLineChargeVO> billingLineChargeVOs) {
		setAttribute(KEY_WGTBRK_DETAILS_SURCHARGE,billingLineChargeVOs);
		
	}


	/**
	  * @author A-5255
	  * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession#removeBillingLineWeightBreakDetails()
	  */
	
	@Override
	public void removeBillingLineSurWeightBreakDetails() {
		removeAttribute(KEY_WGTBRK_DETAILS_SURCHARGE);
	}
	/**
	  * @author A-5255
	  * @return
	  * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession#getBillingLineWeightBreakDetails()
	  */
	
	@Override
	public ArrayList<BillingLineChargeVO> getBillingLineMailWeightBreakDetails() {
		return getAttribute(KEY_WGTBRK_DETAILS_MAILCHARGE);
	}


	/**
	  * @author A-5255
	  * @param billingLineChargeVOs
	  * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession#setBillingLineWeightBreakDetails(java.util.ArrayList)
	  */
	
	@Override
	public void setBillingLineMailWeightBreakDetails(
			ArrayList<BillingLineChargeVO> billingLineChargeVOs) {
		setAttribute(KEY_WGTBRK_DETAILS_MAILCHARGE,billingLineChargeVOs);
		
	}


	/**
	  * @author A-5255
	  * @see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession#removeBillingLineWeightBreakDetails()
	  */
	
	@Override
	public void removeBillingLineMailWeightBreakDetails() {
		removeAttribute(KEY_WGTBRK_DETAILS_MAILCHARGE);
	}
    /**
	 * 
	 * @param selectedBillingLine
	 */
	public void setSelectedBillingLine(BillingLineVO selectedBillingLine){
    	setAttribute(KEY_SELECTED_BLG_LINE,selectedBillingLine);
    }
	/**
	 * 
	 * @return
	 */
	public BillingLineVO getSelectedBillingLine(){
		return (BillingLineVO)getAttribute(KEY_SELECTED_BLG_LINE);
	}
	@Override
	public void setRatingBasis(Collection<OneTimeVO> ratingBasis) {
		// TODO Auto-generated method stub
		setAttribute(KEY_RATINGBASIS, (ArrayList)ratingBasis);
	}
	@Override
	public Collection<OneTimeVO> getRatingBasis() {
		// TODO Auto-generated method stub
		return (Collection)getAttribute(KEY_RATINGBASIS);
	}
   
	public String getParentScreenId() {
		return getAttribute(KEY_PARENTSCREENID);
	}
	public void setParentScreenId(String parentScreenId) {
		setAttribute(KEY_PARENTSCREENID,parentScreenId);
	}
	public void removeParentScreenId() {
		removeAttribute(KEY_PARENTSCREENID);
	}
	public HashMap<String, String> getSystemparametres()//added by a-7871 for ICRD-214766
	{
		return getAttribute(KEY_SYSPARAMETERS);
	}
	public void setSystemparametres(HashMap<String, String> sysparameters)
	{
		setAttribute(KEY_SYSPARAMETERS, sysparameters);
	}
    
}
