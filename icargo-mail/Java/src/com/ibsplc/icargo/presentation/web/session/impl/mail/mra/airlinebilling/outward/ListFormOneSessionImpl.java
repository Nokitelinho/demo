/*
 * ListFormOneSessionImpl.java Created on july 15, 2008 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. 
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.airlinebilling.outward;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward.ListFormOneSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-3434
 *
 */

public class ListFormOneSessionImpl extends AbstractScreenSession 
									implements ListFormOneSession {
	
	private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.listform1";
	
	private static final String MODULE_NAME = "mra.airlinebilling";
		
	private static final String KEY_FORMONE_VOS = "formone";	
	
	/*
     * (non-Javadoc)
     *
     * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getScreenID()
     */
	/**
	 * @return String
	 */
    public String getScreenID() {

        return SCREENID;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getModuleName()
     */
     /**
      * @return String
      */
    public String getModuleName() {
        return MODULE_NAME;
    }

    /**
     * @return Page<FormOneVO>
     */
	public Page<FormOneVO> getFormOneVOs() {
		
		return getAttribute(KEY_FORMONE_VOS);
	}

    /**
     * @param formOneVOs
     */
	public void setFormOneVOs(Page<FormOneVO> formOneVOs) {
		
		setAttribute(KEY_FORMONE_VOS,	formOneVOs);
		
	}

    /**
     * 
     */
	public void removeFormOneVOs() {

		removeAttribute(KEY_FORMONE_VOS);
		
	}


  }