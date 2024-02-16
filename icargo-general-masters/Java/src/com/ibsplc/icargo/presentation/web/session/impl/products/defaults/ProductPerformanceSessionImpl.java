/*
 * MaintainPrivilegeSessionImpl.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.products.defaults;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ProductPerformanceSession;


/**
 * @author A-1747
 *
 */
public class ProductPerformanceSessionImpl extends AbstractScreenSession
        implements ProductPerformanceSession {
	
	private static final String KEY_PRIORITY="priority";
	private static final String KEY_TRANSPORTMODE="transportMode";
	private static final String KEY_SCREEN_ID = "products.defaults.productperformance";
	private static final String KEY_MODULE_NAME = "products.defaults";
	


	/**
     * This method returns the SCREEN ID for the List Product screen
     */


    public String getScreenID(){
        return KEY_SCREEN_ID;
    }

    /**
     * This method returns the MODULE name for the List Product screen
     */
    public String getModuleName(){
        return KEY_MODULE_NAME;
    }   
	/**
     * This method is used to get the priority from the session
     * @return OneTimeVO
     */
	public Collection<OneTimeVO>  getPriority(){
	    return (Collection<OneTimeVO>)getAttribute(KEY_PRIORITY);
	}

	/**
	 * This method is used to set the priority in session
	 * @param priority
	 */
	public void setPriority(Collection<OneTimeVO>  priority) {
	    setAttribute(KEY_PRIORITY, (ArrayList<OneTimeVO>)priority);
	}
	/**
	 * @return void
	 */
	public void removePriority(){
		removeAttribute(KEY_PRIORITY);
	}
	/**
     * This method is used to get the transportMode from the session
     * @return OneTimeVO
     */
	public Collection<OneTimeVO>  getTransportMode(){
	    return (Collection<OneTimeVO>)getAttribute(KEY_TRANSPORTMODE);
	}

	/**
	 * This method is used to set the transportMode in session
	 * @param transportMode
	 */
	public void setTransportMode(Collection<OneTimeVO>  transportMode) {
	    setAttribute(KEY_TRANSPORTMODE, (ArrayList<OneTimeVO>)transportMode);
	}
	/**
	 * @return void
	 */
	public void removeTransportMode(){
		removeAttribute(KEY_TRANSPORTMODE);
	}

}
