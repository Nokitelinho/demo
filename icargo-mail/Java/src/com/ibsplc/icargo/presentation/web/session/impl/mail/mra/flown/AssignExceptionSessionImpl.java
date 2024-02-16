/*
 * AssignExceptionSessionImpl.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.flown;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailExceptionVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.AssignExceptionSession;

/**
 * @author A-2401
 *
 */
public class AssignExceptionSessionImpl extends AbstractScreenSession
										implements AssignExceptionSession{
	
	/*
	 * String for SCREENID
	 */
	private static final String SCREENID = "mailtracking.mra.flown.assignexceptions";
	/*
	 * String for MODULE_NAME
	 */
	private static final String MODULE_NAME = "mailtracking.mra.flown";
	private static final String KEY_FOWNMAIL_FILTERVO="FlownMailFIlterVO";
	private static final String KEY_EXCEPTIONVOS="AWBExceptionVOs";
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
	 * @param exceptionFilterVO
	 */
    public void setFilterDetails(FlownMailFilterVO flownMailFilterVO) {
    	setAttribute(KEY_FOWNMAIL_FILTERVO,
    			(FlownMailFilterVO)flownMailFilterVO);
    }

    /**
	 * @return Returns the ExceptionFilterVO.
	 */
    public FlownMailFilterVO getFilterDetails() {
    	return ((FlownMailFilterVO) getAttribute(KEY_FOWNMAIL_FILTERVO));
    }

    /**
     * @param key
     */
    public void removeFilterDetails(String key) {
    }
    
    /**
	 * @param awbExceptionVos
	 */
    public void setExceptions(ArrayList<FlownMailExceptionVO> flownMailExceptionVOs) {
    	setAttribute(KEY_EXCEPTIONVOS,
    			(ArrayList<FlownMailExceptionVO>)flownMailExceptionVOs);
    }

    /**
	 * @return Collection<AWBExceptionVO>
	 */
    public ArrayList<FlownMailExceptionVO> getExceptions() {
    	return (ArrayList<FlownMailExceptionVO>) getAttribute(KEY_EXCEPTIONVOS);
    }

    /**
	 * @param key
	 */
    public void removeExceptions(String key) {
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



}
