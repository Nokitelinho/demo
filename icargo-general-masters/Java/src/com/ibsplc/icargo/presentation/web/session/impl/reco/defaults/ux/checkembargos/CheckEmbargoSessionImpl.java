/*
 * CheckEmbargoSessionImpl.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.reco.defaults.ux.checkembargos;

import java.util.Collection;
import java.util.ArrayList;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;

import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.ux.checkembargos.CheckEmbargoSession;


/**
 * @author A-1747
 *SessionImplementation for ListEmbargo
 */

public class CheckEmbargoSessionImpl extends AbstractScreenSession
	implements CheckEmbargoSession {

	private static final String EMBARGOS="embargos";
	private static final String TARGET="targetaction";
	private static final String SCREENMODE = "screenmode";

	/**
     * This method returns the SCREEN ID for the Maintain Product screen
     * @return null
     */
    public String getScreenID(){
        return "reco.defaults.ux.checkembargo";
    }

    /**
     * This method returns the MODULE name for the Maintain Product screen
     * @return null
     */
    public String getModuleName(){
        return "reco.defaults";
    }

    /**
     * Method to return embargo details
	 * @param
	 * @return Collection<EmbargoDetailsVO>
	 */
    public Collection<EmbargoDetailsVO> getEmbargos(){
    	return (Collection<EmbargoDetailsVO>)getAttribute(EMBARGOS);
    }

    /**
     * Method to store embargo details
	 * @param Collection<EmbargoDetailsVO>
	 */
    public void setEmbargos(Collection<EmbargoDetailsVO> embargos){
		setAttribute(EMBARGOS, (ArrayList<EmbargoDetailsVO>)embargos);
    }

    /**
     * Method to return the target action
	 * @param
	 * @return String
	 */
    public String getContinueAction(){
    	return (String)getAttribute(TARGET);
    }

    /**
     * Method to store the target action
	 * @param target
	 */
    public void setContinueAction(String target){
		setAttribute(TARGET, target);
    }

	@Override
	public String getScreenMode() {
		
		return (String)getAttribute(SCREENMODE);
	}

	@Override
	public void setScreenMode(String screenMode) {
		setAttribute(SCREENMODE, screenMode);
		
	}

}
