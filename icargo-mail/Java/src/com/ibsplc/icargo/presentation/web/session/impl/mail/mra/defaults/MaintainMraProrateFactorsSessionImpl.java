/*
 * MaintainMraProrateFactorsSessionImpl.java Created on Mar 20, 2007
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFactorFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFactorVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMraProrateFactorsSession;


/**
 * @author Rani Rose John
 * Session implementation for assign exceptions screen.
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Mar 20, 2007   Rani Rose John 		Initial draft
 */
public class MaintainMraProrateFactorsSessionImpl extends AbstractScreenSession
									implements MaintainMraProrateFactorsSession{
	/*
	 * String for SCREENID
	 */
	private static final String SCREENID = "mailtracking.mra.defaults.maintainproratefactors";
	/*
	 * String for MODULE_NAME
	 */
	private static final String MODULE_NAME = "mra.defaults";

	private static final String KEY_PRORATEFILTERVO="ProrationFactorFilterVO";
	private static final String KEY_PRORATEFACTORS="ProrationFactorVO";
	private static final String KEY_ONETIMEVALUES = "oneTimeValues";

	/**
	 * @return Returns the SCREENID.
	 */
    public String getScreenID() {
        return SCREENID;
    }

    /**
	 * @return Returns the MODULE_NAME.
	 */
    public String getModuleName() {
        return MODULE_NAME;
    }
   
    /**
	 * @return Returns the oneTimeValues.
	 */
	public HashMap<String,Collection<OneTimeVO>> getOneTimeValues() {
		return getAttribute(KEY_ONETIMEVALUES);
	}

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */
	public void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues) {
		setAttribute(KEY_ONETIMEVALUES,oneTimeValues);
	}
	/**
	 * @param craProrateFactorFilterVO
	 */
    public void setFilterDetails(ProrationFactorFilterVO prorationFactorFilterVO) {
    	setAttribute(KEY_PRORATEFILTERVO,
    			(ProrationFactorFilterVO)prorationFactorFilterVO);
    }

    /**
	 * @return Returns the CraProrateFactorFilterVO.
	 */
    public ProrationFactorFilterVO getFilterDetails() {
    	return ((ProrationFactorFilterVO) getAttribute(KEY_PRORATEFILTERVO));
    }

    /**
     * @param key
     */
    public void removeFilterDetails() {
    	removeAttribute(KEY_PRORATEFILTERVO);
    }
    /**
	 * Methods for getting Page<InvoiceForAgentVO>
	 * @return invoiceForAgentVO 
	 */
	public ArrayList<ProrationFactorVO> getFactors(){
		return (ArrayList<ProrationFactorVO>) getAttribute(KEY_PRORATEFACTORS);
	}
	
	/**
	 * Methods for setting Page<ProrateFactorVO>
	 * @param prorateFactorVOs
	 */
	public void setFactors(ArrayList<ProrationFactorVO> prorationFactorVOs){
		setAttribute(KEY_PRORATEFACTORS,(ArrayList<ProrationFactorVO>)prorationFactorVOs);
	}
	
	/**
	 * Methods for removing Page<InvoiceForAgentVO>
	 */
	public void removeFactors(){
		removeAttribute(KEY_PRORATEFACTORS);
	}
}