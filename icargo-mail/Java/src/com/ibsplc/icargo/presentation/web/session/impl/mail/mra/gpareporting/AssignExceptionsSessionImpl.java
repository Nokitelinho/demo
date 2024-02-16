/*
 * AssignExceptionsSessionImpl.java Created on Feb 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpareporting;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.AssignExceptionsSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * 
 * @author A-2245
 *
 */
public class AssignExceptionsSessionImpl extends AbstractScreenSession implements AssignExceptionsSession {

	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String SCREENID = "mailtracking.mra.gpareporting.assignexceptions";

	private static final String KEY_GPAREPORTINGFILTERVO="gpaReportingFilterVO";
	
	private static final String KEY_GPAREPORTINGDETAILVOS="gpaReportingDetailVOs";

	private static final String KEY_ONETIMEVOS="oneTimeValues";
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
	 * method to set GPAReportingFilterVO to session
	 * @param gpaReportingFilterVO
	 */
    public void setGpaReportingFilterVO(GPAReportingFilterVO gpaReportingFilterVO){
     	setAttribute(KEY_GPAREPORTINGFILTERVO,gpaReportingFilterVO);
    }
    /**
      * method to get GPAReportingFilterVO from session
      * @return GPAReportingFilterVO
      */
    public GPAReportingFilterVO getGpaReportingFilterVO(){
     	return getAttribute(KEY_GPAREPORTINGFILTERVO);
    }
    /**
      * method to remove GPAReportingFilterVO from session
      */
    public void removeGpaReportingFilterVO(){
     	removeAttribute(KEY_GPAREPORTINGFILTERVO);
    }
    /**
     * method to set gpaReportingDetailVOs to session
     * @param gpaReportingDetailVOs
     */
	public void setGpaReportingDetailVOs(Page<GPAReportingClaimDetailsVO> gpaReportingDetailVOs){
    	setAttribute(KEY_GPAREPORTINGDETAILVOS,gpaReportingDetailVOs);
	}
	/**
	 * method to get Page<GPAReportingClaimDetailsVO> from session 
	 * @return Page<GPAReportingClaimDetailsVO>
	 */
	public Page<GPAReportingClaimDetailsVO> getGpaReportingDetailVOs(){
    	return getAttribute(KEY_GPAREPORTINGDETAILVOS);
	}
	/**
	 * method to remove Page<GPAReportingClaimDetailsVO> from session
	 */
	public void removeGpaReportingDetailVOs(){
    	removeAttribute(KEY_GPAREPORTINGDETAILVOS);
	}
    /**
     * method to get HashMap<String, Collection<OneTimeVO>> from session
     * @return HashMap<String, Collection<OneTimeVO>>
     */
     public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){
    	 return getAttribute(KEY_ONETIMEVOS);
     }
     /**
      * method to set oneTimeVOs to session
      * @param oneTimeVOs
      */
     public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){
     setAttribute(KEY_ONETIMEVOS, oneTimeVOs);
     }
     /**
      * method to remove oneTimeVOs from session
      */
     public void removeOneTimeVOs() {
     removeAttribute(KEY_ONETIMEVOS);
     }
}
