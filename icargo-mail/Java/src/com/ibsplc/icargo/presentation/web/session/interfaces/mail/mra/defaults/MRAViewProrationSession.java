/*
 * MRAViewProrationSession.java Created on Jul 17, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.AWMProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SurchargeProrationDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-3251
 *
 */
public interface MRAViewProrationSession extends ScreenSession {
	
	
	 /**
    * 
    * @return prorationFilterVO
    */
   
	public ProrationFilterVO getProrationFilterVO();
	
	/**
	 * @param prorationFilterVO The  prorationFilterVO to set.
	 */
	public void setProrationFilterVO(ProrationFilterVO prorationFilterVO);
	
	/**
	 * @author A-2122
	 */	
	
	public void removeProrationFilterVO();
	
	
	
	/**
	 * @param prorationVOs 
	 */
	public void setProrationVOs(ArrayList<ProrationDetailsVO> prorationVOs);
	
	
	/**
	 * @return ArrayList<ProrationDetailsVO>
	 */
	public ArrayList<ProrationDetailsVO> getProrationVOs();
	
	/**
	 * @author A-2122
	 *
	 */
	public void removeProrationVOs();
		
	
	
	
	/**
	 * @return Returns the category
	 */
	public ArrayList<OneTimeVO> getCategory();
	
	
	/**
	 * @param category The  category to set.
	 */
	public void setCategory(ArrayList<OneTimeVO> category);
	
	
	
	/**
	 * @return Returns the subClass
	 */
	public ArrayList<OneTimeVO> getSubClass();
	
	
	
	/**
	 * @param subClass The  subClass to set.
	 */
	public void setSubClass(ArrayList<OneTimeVO> subClass);
	
	
	/**
	 * @return Returns the prorationType
	 */
	public ArrayList<OneTimeVO> getProrationType();
	
	/**
	 * @param prorationType The  prorationType to set.
	 */
	public void setProrationType(ArrayList<OneTimeVO> prorationType);
	
	
	/**
	 * @return Returns the prorationPayType
	 */
	public ArrayList<OneTimeVO> getProrationPayType();
	
	/**
	 * @param prorationPayType The  prorationPayType to set.
	 */
	public void setProrationPayType(ArrayList<OneTimeVO> prorationPayType);
	
	//added for bug 23617
	/**
	 * @param primaryProrationVOs 
	 */
	public void setPrimaryProrationVOs(Collection<ProrationDetailsVO> primaryProrationVOs);
	
	
	/**
	 * @return Collection<ProrationDetailsVO>
	 */
	public Collection<ProrationDetailsVO> getPrimaryProrationVOs();
	
	/**
	 * method to remove primaryProrationVOs
	 *
	 */
	public void removePrimaryProrationVOs();
		
	
	/**
	 * @param secondaryProrationVOs 
	 */
	public void setSecondaryProrationVOs(Collection<ProrationDetailsVO> secondaryProrationVOs);
	
	
	/**
	 * @return Collection<ProrationDetailsVO>
	 */
	public Collection<ProrationDetailsVO> getSecondaryProrationVOs();
	
	/**
	 * method to remove secondaryProrationVOs
	 *
	 */
	public void removeSecondaryProrationVOs();
	
	/**
	 * @return
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

	/**
	 * removes removeOneTimeVOs
	 */
	public void removeOneTimeVOs();
	

	public void setParentScreenID(String parentScreenID);
		    	
    public String getParentScreenID();

    public void removeParentScreenID();
    /**
     * 
     * @author A-5255
     * @return
     */
	public  ArrayList<SurchargeProrationDetailsVO> getSurchargeProrationVOs();
	/**
	 * 
	 * @author A-5255
	 * @param surchargeProrationVOs
	 */
	public  void setSurchargeProrationVOs(ArrayList<SurchargeProrationDetailsVO> surchargeProrationVOs);
	/**
	 * 
	 * @author A-5255
	 * @param sectorDetails
	 */
	public void setSectorDetails(HashMap<String, String> sectorDetails);
	/**
	 * 
	 * @author A-5255
	 * @return
	 */
	public HashMap<String, String>  getSectorDetails();
	/**
	 * 
	 * @author A-5255
	 */
	public void removeSectorDetails();	
	/**
	 * @author A-6991
	 * @return HashMap<String, String>
	 */
	public HashMap<String, String> getSystemparametres();
	/**
	 * 
	 * @param sysparameters sysparameters
	 */
	public void setSystemparametres(HashMap<String, String> sysparameters);
	/**
	 * 
	 * @param awmProrationVOs
	 */
	public void setAWMProrationVOs(Collection<AWMProrationDetailsVO> awmProrationVOs);		
	/**
	 * @author A-7371
	 * @return Collection<AWMProrationDetailsVO>
	 */
	public Collection<AWMProrationDetailsVO> getAWMProrationVOs();

}

