/*
 * MaintainLoyaltySession.java Created on Apr 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyParameterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyAttributeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ParameterDescriptionVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1862
 *
 */
public interface MaintainLoyaltySession extends ScreenSession {
	    
	
    void setLoyaltyProgrammeVO(LoyaltyProgrammeVO loyaltyProgrammeVO);	
	
    LoyaltyProgrammeVO getLoyaltyProgrammeVO();
 
    
	public void setParentScreenId(String parentScreenId);
	
	public String getParentScreenId();
	
	public void removeParentScreenId();
	
	public ArrayList<ParameterDescriptionVO> getParameter();
    
    public void setParameter(ArrayList<ParameterDescriptionVO> param);
     
    
     
     public ArrayList<String> getLoyaltyNames();
    
     public void setLoyaltyNames(ArrayList<String> loyaltyNames);
    
     public String getPageURL();
 
     public void setPageURL(String pageurl);    
     
    HashMap<String, Collection<OneTimeVO>> getOneTimeValues();

  
  	void setOneTimeValues(HashMap<String, Collection<OneTimeVO>> oneTimeValues);
  	
  	
  	HashMap<String, Collection<LoyaltyAttributeVO>> getAttributeValues();
  	
  	void setAttributeValues(HashMap<String, Collection<LoyaltyAttributeVO>> loyaltyAttributeVOs);
  	
  	HashMap<String, Collection<String>> getAttributeString();
  	
  	void setAttributeString(HashMap<String, Collection<String>> attributes);
      
      
  	public Page<LoyaltyProgrammeVO> getLoyaltyProgrammeLovVOs();
    
    public void setLoyaltyProgrammeLovVOs(Page<LoyaltyProgrammeVO> loyaltyProgrammeVOs);
    
    public Page<LoyaltyProgrammeVO> getRunningLoyaltyProgrammeLovVOs();
    
    public void setRunningLoyaltyProgrammeLovVOs(Page<LoyaltyProgrammeVO> 
												runningLoyaltyProgrammeLovVOs);
   
    public String getAttributeValue();
    
    public void setAttributeValue(String attribute); 
    public String getUnitValue();
    
    public void setUnitValue(String unit); 
    public String getAmountValue();
    
    public void setAmountValue(String amount); 
    public String getPointsValue();
    
    public void setPointsValue(String points); 
 	
    public ArrayList<LoyaltyParameterVO> getParameterVOsForDisplay();
    
    public void setParameterVOsForDisplay(ArrayList<LoyaltyParameterVO> loyaltyParameterVOs);
    
  public ArrayList<LoyaltyParameterVO> getParameterVOsForLOV();
    
    public void setParameterVOsForLOV(ArrayList<LoyaltyParameterVO> loyaltyParameterVOs);
}
