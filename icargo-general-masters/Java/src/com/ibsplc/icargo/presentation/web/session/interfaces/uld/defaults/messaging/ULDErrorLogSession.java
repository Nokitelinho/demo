/*
 * ULDErrorLogSession.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1862
 *
 */
public interface ULDErrorLogSession extends ScreenSession {
	    
 /**
  * 
  * @return
  */
  public String getPageURL();
/**
 * 
 * @param pageurl
 */
  public void setPageURL(String pageurl);
  /**
   * 
   * @return
   */
  public String getForPic();
	/**
	 * 
	 * @param pageurl
	 */
  public void setForPic(String pageurl);
  /**
   * 
   * @return
   */
  public Page<ULDFlightMessageReconcileDetailsVO> getULDFlightMessageReconcileDetailsVOs();
/**
 * 
 * @param paramCode
 */
  public void setULDFlightMessageReconcileDetailsVOs(Page<ULDFlightMessageReconcileDetailsVO> paramCode);
  /**
   * 
   * @return
   */
  public FlightFilterMessageVO getFlightFilterMessageVOSession();
/**
 * 
 * @param flightFilterMessageVO
 */
  public void setFlightFilterMessageVOSession(FlightFilterMessageVO flightFilterMessageVO);
  /**
   * 
   * @return
   */
  public ULDFlightMessageReconcileDetailsVO getULDFlightMessageReconcileDetailsVO();
/**
 * 
 * @param uldFlightMessageReconcileDetailsVO
 */
  public void setULDFlightMessageReconcileDetailsVO(ULDFlightMessageReconcileDetailsVO uldFlightMessageReconcileDetailsVO);
//newly added starts
  /**
	 * @param ucmNos
	 */
	public void setUcmNumberValues(Collection<String> ucmNos);
	
	/**
	 * @return
	 */
	public ArrayList<String> getUcmNumberValues();
	
	/**
	 * 
	 */
	public void removeUcmNumberValues();
	
	/**
	 * @param ucmNos
	 */
	public void setPouValues(Collection<String> ucmNos);
	
	/**
	 * @return
	 */
	public ArrayList<String> getPouValues();
	
	/**
	 * 
	 */
	public void removePouValues();
	
	/**
	 * @return
	 */
	public FlightValidationVO getFlightValidationVO();
	/**
	 * @param flightValidationVO
	 */
	public void setFlightValidationVO(FlightValidationVO flightValidationVO);
	/**
	 * 
	 */
	public void removeFlightValidationVO();
  //newly added endds
	
	 
    public ArrayList<OneTimeVO> getContent();
    
    public void setContent(ArrayList<OneTimeVO> content);
    
}
