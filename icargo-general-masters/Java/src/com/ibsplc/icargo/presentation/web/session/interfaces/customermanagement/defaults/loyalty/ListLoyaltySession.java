/*
 * ListLoyaltySession.java Created on Apr 23, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/*
 * @author A-1862
 *
 */
public interface ListLoyaltySession extends ScreenSession {
	
	 //added by a-5203
	
	Integer getTotalRecords();
	void setTotalRecords(int totalRecords);
	    
	//end
	public Page<LoyaltyProgrammeVO> 
	getLoyaltyDetails();

	
	public void setLoyaltyDetails
	(Page<LoyaltyProgrammeVO> loyaltyDetails);


	public LoyaltyProgrammeFilterVO getFilterVO(); 
	
	public void setFilterVO(LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO);
 
    
	public void setParentScreenId(String parentScreenId);
	
	public String getParentScreenId();
	
	public void removeParentScreenId();
	
	
    
     public String getPageURL();
 
     public void setPageURL(String pageurl);     

     public HashMap<String, Collection<OneTimeVO>> getOneTimeValues();
     
     public void setOneTimeValues(
     		HashMap<String, Collection<OneTimeVO>> oneTimeValues);
   
}
