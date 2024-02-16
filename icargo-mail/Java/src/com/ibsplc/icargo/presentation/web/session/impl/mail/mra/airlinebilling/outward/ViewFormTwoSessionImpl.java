/*
 * ViewFormTwoSessionImpl.java Created on July 22,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.airlinebilling.outward;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineForBillingVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward.ViewFormTwoSession;

/**
 *  
 * @author A-3434
 * 
 */

public class ViewFormTwoSessionImpl extends
	AbstractScreenSession implements ViewFormTwoSession{
	
	private static final String SCREENID ="mailtracking.mra.airlinebilling.outward.viewform2";
	private static final String MODULE_NAME = "mra.airlinebilling";
	private static final String AGENTFORBILLINGVOS = "AgentForBillingVos";
	
	/*
     * (non-Javadoc)
     *
     * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getScreenID()
     */
	/**
	 * @return screenId
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
     * @return moduleName
     */
    public String getModuleName() {
        return MODULE_NAME;
    }	
    
    /**
	 * 
	 * @return Collection<AirlineForBillingVO>
	 */
	public Collection<AirlineForBillingVO> getAirlineForBillingVOs(){
		return (Collection<AirlineForBillingVO>)getAttribute(AGENTFORBILLINGVOS);
	}
	
	/**
	 * 
	 * @param airlineForBillingVOs
	 */
	public void setAirlineForBillingVOs(Collection<AirlineForBillingVO> airlineForBillingVOs){
		setAttribute(AGENTFORBILLINGVOS, (ArrayList<AirlineForBillingVO>)airlineForBillingVOs);
	}
	
	/**
	 * 
	 *
	 */
	public void removeAirlineForBillingVOs(){
		removeAttribute(AGENTFORBILLINGVOS);
	}
}
