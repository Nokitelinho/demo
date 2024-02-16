/*
 * MaintainBillingMatrixSession.java Created on Feb 28, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineDetailVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 *  @author A-2398
 */

public interface MaintainBillingMatrixSession extends ScreenSession {

	
	/**
	 * @return BillingMatrixVO
	 */
	public BillingMatrixVO getBillingMatrixVO();

	/**
	 * sets BillingMatrixVO
	 */
	public void setBillingMatrixVO(BillingMatrixVO blgMtxVO) ;

	/**
	 *
	 * removes BillingMatrixVO
	 */
	public void removeBillingMatrixVO();
	
	/**
     * This method is used for PageAwareMultiMapper to get the Index Map
     * @return  HashMap<String,String>
     */
    public HashMap<String,String>getIndexMap();

    /**Sets the hashmap for Absolute index of page
     * @param indexMap
     */
    public void setIndexMap(HashMap<String,String>indexMap);
    
    /**
     * Removes the hashmap for Absolute index of page
     *
     */
    public void removeIndexMap();
    
    /**
     * @param billingLineDetailsPage
     */
    public void setBillingLineDetails(Page<BillingLineVO> billingLineDetailsPage);
    
    /**
     * @return
     */
    public Page<BillingLineVO> getBillingLineDetails();
    
    /**
     * 
     */
    public void removeBillingLneDetails();
    
    /**
     * @return
     */
    public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();
	
	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);
	
	/**
	 * 
	 */
	public void removeOneTimeVOs();

	/**
	 * @return BillingMatrixFilterVO
	 */
	public BillingMatrixFilterVO getBillingMatrixFilterVO();

	/**
	 * sets BillingMatrixFilterVO
	 */
	public void setBillingMatrixFilterVO(BillingMatrixFilterVO blgMtxFilterVO) ;

	/**
	 *
	 * removes BillingMatrixVO
	 */
	public void removeBillingMatrixFilterVO();
	/**
	 * 
	 * @author A-5255
	 * @return
	 */
	public HashMap<String,BillingLineDetailVO> getBillingLineChargeDetails();
	/**
	 * 
	 * @author A-5255
	 * @param indexMap
	 */
	public void setBillingLineChargeDetails(HashMap<String,BillingLineDetailVO>indexMap);
	/**
	 * 
	 * @author A-5255
	 */
	public void removeBillingLineChargeDetails();
	/**
	 * 
	 * @author A-5255
	 * @return
	 */
	public ArrayList<BillingLineChargeVO> getBillingLineSurWeightBreakDetails();
	/**
	 * 
	 * @author A-5255
	 * @param indexMap
	 */
	public void setBillingLineSurWeightBreakDetails(ArrayList<BillingLineChargeVO> billingLineChargeVOs);
	/**
	 * 
	 * @author A-5255
	 */
	public void removeBillingLineSurWeightBreakDetails();
	/**
	 * 
	 * @author A-5255
	 * @return
	 */
	public ArrayList<BillingLineChargeVO> getBillingLineMailWeightBreakDetails();
	/**
	 * 
	 * @author A-5255
	 * @param indexMap
	 */
	public void setBillingLineMailWeightBreakDetails(ArrayList<BillingLineChargeVO> billingLineChargeVOs);
	/**
	 * 
	 * @author A-5255
	 */
	public void removeBillingLineMailWeightBreakDetails();
	
	/**
	 * 
	 * @param selectedBillingLine
	 */
	public void setSelectedBillingLine(BillingLineVO selectedBillingLine);
	/**
	 * 
	 * @return
	 */
	public BillingLineVO getSelectedBillingLine();
	public void setRatingBasis(Collection<OneTimeVO> ratingBasis);
	public Collection<OneTimeVO> getRatingBasis();
	/**
	 * 
	 * @return
	 */
	public String getParentScreenId();
	/**
	 * 
	 * @param parentScreenId
	 */
	public void setParentScreenId(String parentScreenId);
	/**
	 * 
	 *
	 */
	public void removeParentScreenId();
	/**
	 * @return HashMap<String, String>
	 */
	public HashMap<String, String> getSystemparametres();//added by a-7871 for ICRD-214766
	/**
	 * 
	 * @param sysparameters sysparameters
	 */
	public void setSystemparametres(HashMap<String, String> sysparameters);
}
