/*
 * CaptureCN66Session.java Created on Feb 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2408
 *
 */
public interface CaptureCN66Session extends ScreenSession {

	
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
	 * @return
	 */
	public ArrayList<AirlineCN66DetailsVO> getCn66Details();
	/**
	 * @param cn66Details
	 */
	public void setCn66Details(ArrayList<AirlineCN66DetailsVO> cn66Details);
	/**
	 * remove
	 */
	public void removeCn66Details();
	/**
	 * @return
	 */
	public HashMap<String, Collection<AirlineCN66DetailsVO>> getCn66DetailsMap();
	/**
	 * @param cn66details
	 */
	public void setCn66DetailsMap(HashMap<String, Collection<AirlineCN66DetailsVO>> cn66details);
	/**
	 * 
	 */
	public void removeCn66DetailsMap();
	/**
	 * @return
	 */
	public HashMap<String, Collection<AirlineCN66DetailsVO>> getCn66DetailsModifiedMap();
	/**
	 * @param cn66details
	 */
	public void setCn66DetailsModifiedMap(HashMap<String, Collection<AirlineCN66DetailsVO>> cn66details);
	/**
	 * 
	 */
	public void removeCn66DetailsModifiedMap();
	/**
	 * @return
	 */
	public ArrayList<AirlineCN66DetailsVO> getPreviousCn66Details();
	/**
	 * @param cn66Details
	 */
	public void setPreviousCn66Details(ArrayList<AirlineCN66DetailsVO> cn66Details);
	/**
	 * remove
	 */
	public void removePreviousCn66Details();
	/**
	 * @return
	 */
	public ArrayList<AirlineCN66DetailsVO> getModifiedCn66Details();
	/**
	 * @param cn66Details
	 */
	public void setModifiedCn66Details(ArrayList<AirlineCN66DetailsVO> cn66Details);
	/**
	 * remove
	 */
	public void removemodifiedCn66Details();
	/**
	 * @return
	 */
	public ArrayList<String> getKeyValues();
	/**
	 * @param keyValues
	 */
	public void setKeyValues(ArrayList<String> keyValues);
	/**
	 * 
	 */
	public void removeKeyValues();
	
	/**
	 * @return
	 */
	public String getParentId();
	/**
	 * @param parentId
	 */
	public void setParentId(String parentId);
	/**
	 * 
	 */
	public void removeParentId();
	/**
	 * @return
	 */
	public AirlineCN66DetailsFilterVO getCn66FilterVO();
	/**
	 * @param filterVO
	 */
	public void setCn66FilterVO(AirlineCN66DetailsFilterVO filterVO);
	/**
	 * 
	 */
	public void removeCn66FilterVO();
	/**
	 * @return
	 */
	public String getPresentScreenStatus();
	/**
	 * @param status
	 */
	public void setPresentScreenStatus(String status);
	/**
	 * 
	 */
	public void removePresentScreenStatus();
	/**
	 * @return
	 */
	public AirlineCN66DetailsVO getAirlineCN66DetailsVO();
	/**
	 * @param AirlineCN66DetailsVO
	 */
	public void setAirlineCN66DetailsVO(AirlineCN66DetailsVO airlineCN66DetailsVO);
	/**
	 * 
	 */
	public void removeAirlineCN66DetailsVO();
	/**
	 * 
	 * @return AirlineCN66DetailsVOs
	 */
	public Page<AirlineCN66DetailsVO> getAirlineCN66DetailsVOs();
	
	/**
	 * 
	 * @param Collection<AirlineCN66DetailsVO>
	 */
	public void setAirlineCN66DetailsVOs(Page<AirlineCN66DetailsVO> airlineCN66DetailsVOs);
	
	/**
	 * remove AirlineCN66DetailsVOs
	 */
	public void removeAirlineCN66DetailsVOs();
	/**
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
	 * @param oneTimeRI
	 */
	public void setOneTimeRI(Collection<OneTimeVO> oneTimeRI);
	/**
	 * 
	 * @return oneTimeRI
	 */
	public Collection<OneTimeVO> getOneTimeRI();
	/**
	 * 
	 * @param oneTimeHNI
	 */
	public void setOneTimeHNI(Collection<OneTimeVO> oneTimeHNI);
	/**
	 * 
	 * @return oneTimeHNI
	 */
	public Collection<OneTimeVO> getOneTimeHNI();
	/**
	 * 
	 * @param unitRoundingVO
	 */
	public void setVolumeRoundingVO(UnitRoundingVO unitRoundingVO);
    /**
     * 
     * @return unitRoundingVO
     */
    public UnitRoundingVO getVolumeRoundingVO();
    /**
     * Remove unitRoundingVO
     */
   public void removeVolumeRoundingVO();
  /**
   * 
   * @param unitRoundingVO
   */
   public void setWeightRoundingVO(UnitRoundingVO unitRoundingVO);
  /**
   * 
   * @return unitRoundingVO
   */
   public UnitRoundingVO getWeightRoundingVO();
/**
 * Remove unitRoundingVO
 */
   public void removeWeightRoundingVO();
}

