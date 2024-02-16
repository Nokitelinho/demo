/*
 * MUCTrackingSessionImpl.java Created on Aug 07, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.messaging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDConfigAuditVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.MUCTrackingSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author a-3045
 *
 */
public class MUCTrackingSessionImpl extends AbstractScreenSession
implements MUCTrackingSession{
	
	private static final String MODULE = "uld.defaults";
	
	private static final String SCREENID ="uld.defaults.messaging.muctracking";
	
	private static final String KEY_ONETIMEVALUES = "oneTimeValues";
	
	private static final String LISTCOLL = "listDisplayPage";
	
	private static final String AUDITCOLL = "listAudit";
	
	private static final String LISTFILTER = "listFilterVO";
	
	private static final String KEY_LOVVO = "lovVO";
	
	private static final String KEY_CONDITIONCODES = "conditionCodes";
	
	private static final String  LISTSTATUS = "listStatus";

	
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
    * @return Returns the ULDListVO.
    */
    public TransactionListVO getListDisplayColl() {
        return getAttribute(LISTCOLL);
    }
    /**
     * @param uldListVOs The uldListVOs to set.
     */
    public void setListDisplayColl(TransactionListVO uldListVOs) {
    	setAttribute(LISTCOLL, uldListVOs);
    }

	/**
	 * @return Returns the listFilterVO.
	 */
	public TransactionFilterVO getListFilterVO() {
		return getAttribute(LISTFILTER);
	}

	/**
	 * @param listFilterVO The listFilterVO to set.
	 */
	public void setListFilterVO(TransactionFilterVO listFilterVO) {
		setAttribute(LISTFILTER,listFilterVO);
	}
	
	/**
	 * @return Returns the lovVO.
	 */
	public Page<String> getLovVO() {
		return getAttribute(KEY_LOVVO);
	}

	/**
	 * @param lovVO The lovVO to set.
	 */
	public void setLovVO(Page<String> lovVO) {
		setAttribute(KEY_LOVVO,lovVO);
	}
	
	public Collection<OneTimeVO>  getConditionCodes(){
		return (Collection<OneTimeVO>)getAttribute(KEY_CONDITIONCODES);
	}
	public void setConditionCodes(Collection<OneTimeVO> conditionCodes){
		setAttribute(KEY_CONDITIONCODES, (ArrayList<OneTimeVO>)conditionCodes);
	}
	
	/**
	    * @return Returns the ULDConfigAuditVO.
	    */
	    public Collection<ULDConfigAuditVO> getConfigAuditColl() {
	        return (Collection<ULDConfigAuditVO>)getAttribute(AUDITCOLL);
	    }
	    /**
	     * @param auditVOs The auditVOs to set.
	     */
	    public void setConfigAuditColl(Collection<ULDConfigAuditVO> auditVOs) {
	    	setAttribute(AUDITCOLL, (ArrayList<ULDConfigAuditVO>)auditVOs);
	    }
		/**
		 * @return Returns the listStatus.
		 */
		public String getListStatus() {
			return getAttribute(LISTSTATUS);
		}

		/**
		 * @param listStatus The listStatus to set.
		 */
		public void setListStatus(String listStatus) {
			setAttribute(LISTSTATUS,listStatus);
		}
	
}
