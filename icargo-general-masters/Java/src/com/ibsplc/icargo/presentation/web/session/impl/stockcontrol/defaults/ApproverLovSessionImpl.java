/*
 * ApproverLovSessionImpl.java Created on Aug 30, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.stockcontrol.defaults;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ApproverLovSession;


/**
 * @author kirupakaran
 *
 */
public class ApproverLovSessionImpl extends AbstractScreenSession
        implements ApproverLovSession{

	public static final String KEY_OneTimeVO = "oneTimeVO";
	public static final String KEY_SCREEN_ID = "UISKC021";
	public static final String KEY_MODULE_NAME = "stockcontrol.defaults";


    /**
     * This method returns the SCREEN ID for the Maintain stock holder screen
     */
    public String getScreenID(){
        return KEY_SCREEN_ID;
    }

    /**
     * This method returns the MODULE name for the Maintain stock  holder screen
     */
    public String getModuleName(){
        return KEY_MODULE_NAME;
    }

   /**
	 * Thie method is used to get the OneTimeVO for child screen
	 * from session
	 * @return OneTimeVO
	 */
	public Collection<OneTimeVO> getOneTimeStock() {
		return (Collection<OneTimeVO>)getAttribute(KEY_OneTimeVO);
	}

	/**
	 * This method is used to set the OneTimeVO in session for child
	 * @param OneTimeVO
	 */
	public void setOneTimeStock(Collection<OneTimeVO> collOneTimeStockVO) {
		setAttribute(KEY_OneTimeVO,(ArrayList<OneTimeVO>)collOneTimeStockVO);
	}


}
