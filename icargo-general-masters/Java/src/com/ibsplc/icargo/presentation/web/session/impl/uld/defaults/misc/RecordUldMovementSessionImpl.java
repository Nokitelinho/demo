/*
 * RecordUldMovementSessionImpl.java Created on Oct 10, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.misc;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.RecordUldMovementSession;

/**
 * @author A-1496
 *
 */
public class  RecordUldMovementSessionImpl extends AbstractScreenSession
		implements RecordUldMovementSession {

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.misc.recorduldmovement";
	
	private static final String KEY_ONETIMEVO="oneTimeVO";
	
	private static final String KEY_ULDMOVEMENT_VO="uldmovementvo";
	
	private static final String KEY_ULDNUMBER_VO="uldnumbervo";
	
	private static final String KEY_RECONCILEVO = "ReconcileVO";
	
	private static final String KEY_PAGEURL="pageurl";	

	/**
	 *
	 * /** Method to get ScreenID
	 *
	 * @return ScreenID
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * Method to get ProductName
	 *
	 * @return ProductName
	 */
	public String getModuleName() {
		return MODULE;
	}
   
	/**
	 * This method is used to fetch the uldcontentTypes stored in
	 * session. UldcontentTypes types are fetched from onetime during
	 * screenload
	 * @return Collection<String>
	 */
	public Collection<OneTimeVO> getOneTimeContent(){
	   return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEVO);
	}

	/**
	 * This method is used to set the uldcontentTypes into the session
	 * @param contentOneTimeValues
	 * Collection<String>
	 */
	public void setOneTimeContent(Collection<OneTimeVO> contentOneTimeValues){
		setAttribute(KEY_ONETIMEVO,(ArrayList<OneTimeVO>)contentOneTimeValues);
	}
    /**
     * This method is used to set the uldMovementVos in the Session
     * @param uldMovementVOs
     */
	public void setULDMovementVOs(Collection<ULDMovementVO> uldMovementVOs){
		setAttribute(KEY_ULDMOVEMENT_VO,(ArrayList<ULDMovementVO>)uldMovementVOs);
	}
	
	/**
	 * This method is used to get the uldMovementVos from the session
	 * @return 
	 */
	public Collection<ULDMovementVO> getULDMovementVOs(){
		   return (Collection<ULDMovementVO>)getAttribute(KEY_ULDMOVEMENT_VO);
		} 
	 /**
	  * 
	  * This method is used to get the ULDNumbers from the Session
	  * @return collection<String>
	  */
	 public Collection<String> getULDNumbers(){
		 return (Collection<String>)getAttribute(KEY_ULDNUMBER_VO);
	 }
	  
	/**
	 * This method is used to set the uldnumbers in the session.
	 * @param uldNumbers
	 */ 
	public void setULDNumbers(Collection<String> uldNumbers){
	    	setAttribute(KEY_ULDNUMBER_VO,(ArrayList<String>)uldNumbers);
	}
	/**
	 * @return
	 */
	public ULDFlightMessageReconcileDetailsVO getULDFlightMessageReconcileDetailsVO() {
		 return getAttribute(KEY_RECONCILEVO);
	}
/**
 * @param uldFlightMessageReconcileDetailsVO
 */
	public void setULDFlightMessageReconcileDetailsVO(ULDFlightMessageReconcileDetailsVO uldFlightMessageReconcileDetailsVO) {
		setAttribute(KEY_RECONCILEVO,uldFlightMessageReconcileDetailsVO);
	}
	/**
	 * @return
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
   
}
