/*
 * MaintainDOTRateSessionImpl.java Created on Aug 03, 2007
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailDOTRateVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainDOTRateSession;

/**
 * @author A-2408
 *
 */
public class MaintainDOTRateSessionImpl extends AbstractScreenSession implements MaintainDOTRateSession {

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.maintaindotrate";
	
	private static final String KEY_ONETIME_VOS="onetimevalues";
	
	private static final String KEY_DOT_VOS="dotratevos";
	
	/**
    *
    */
   public MaintainDOTRateSessionImpl() {
       super();

   }
   /**
    * @return
    */
   @Override
   public String getScreenID() {

       return SCREENID;
   }

   /**
    * @return
    */
   @Override
   public String getModuleName() {
       return MODULE_NAME;
   }

  
   /**

    *

    * @return

    */

    public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){

    return getAttribute(KEY_ONETIME_VOS);

    }
    /**

    *

    * @param oneTimeVOs

    */

    public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){

    setAttribute(KEY_ONETIME_VOS, oneTimeVOs);

    }
    /**

    *

    *remove onetimes

    */

    public void removeOneTimeVOs() {

    removeAttribute(KEY_ONETIME_VOS);

    }
    /**
	 * @return
	 */
	public ArrayList<MailDOTRateVO> getMailDOTRateVOs(){
		return getAttribute(KEY_DOT_VOS);
	}
	
	/**
	 * @param mailDOTRateVOs
	 */
	public void setMailDOTRateVOs(ArrayList<MailDOTRateVO> mailDOTRateVOs){
		setAttribute(KEY_DOT_VOS,mailDOTRateVOs);
	}
	
	/**
	 * 
	 */
	public void removeMailDOTRateVOs(){
		removeAttribute(KEY_DOT_VOS);
	}

}
