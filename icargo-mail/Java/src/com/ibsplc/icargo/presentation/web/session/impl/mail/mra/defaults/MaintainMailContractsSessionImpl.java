/*
 * MaintainMailContractsSessionImpl.java Created on Apr 02, 2007
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMailContractsSession;


/**
 * @author A-2408
 *
 */
public class MaintainMailContractsSessionImpl extends AbstractScreenSession implements MaintainMailContractsSession {

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.maintainmailcontracts";

	private static final String KEY_MAILCONTRACTVO="mailcontractvo";

	private static final String KEY_ONETIME_VOS="onetimevalues";
	
	private static final String KEY_MAILCONTRACTVO_DETAILS="contractvodetails";

	
    /**
     *
     */
    public MaintainMailContractsSessionImpl() {
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
 	public MailContractVO getMailContractVO(){
 		return (MailContractVO)getAttribute(KEY_MAILCONTRACTVO);
 	}
 	/**
 	 * @param vo
 	 */
 	public void setMailContractVO(MailContractVO vo){
 		setAttribute(KEY_MAILCONTRACTVO,(MailContractVO)vo);
 	}
 	/**
 	 * 
 	 */
 	public void removeMailContractVO(){
 		
 		removeAttribute(KEY_MAILCONTRACTVO);
 	}
 	/**
	 * @return
	 */
	public Collection<MailContractDetailsVO> getMailContractDetails(){
		return (ArrayList<MailContractDetailsVO>)getAttribute(KEY_MAILCONTRACTVO_DETAILS);
	}
	/**
	 * @param vos
	 */
	public void setMailContractDetails(Collection<MailContractDetailsVO> vos){
		setAttribute(KEY_MAILCONTRACTVO_DETAILS,(ArrayList<MailContractDetailsVO>)vos);
	}
	/**
	 * 
	 */
	public void removeMailContractDetails(){
		removeAttribute(KEY_MAILCONTRACTVO_DETAILS);
	}

}
