/*
 * GenerateInvoiceSessionImpl.java Created on Feb 18, 2008
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpabilling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GenerateInvoiceSession;
/**
 * @author a-1863
 *
 */
public class GenerateInvoiceSessionImpl extends AbstractScreenSession implements
		GenerateInvoiceSession {

	private static final String FROM_DATES="fromdates";
	private static final String TO_DATES="fromdates";
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREENID = "mailtracking.mra.gpabilling.generateinvoice";
	private static final String KEY_ONETIME_VOS="onetimevalues";

	 public void setGenerateInvoiceVOs(Collection<GenerateInvoiceVO> generateInvoiceVOs) {
	   		setAttribute(FROM_DATES, (ArrayList<GenerateInvoiceVO>)generateInvoiceVOs);
		}

	/**
     * Method to get ScreenId
     * @return screenId
     **/
    public String getScreenID() {
        return SCREENID;
    }

    /**
     * Method to get ModuleName
     * @return ModuleName
     **/
    public String getModuleName() {
        return MODULE_NAME;
    }

    public void setToDate(Collection<String> todates) {
   		setAttribute(TO_DATES, (ArrayList<String>)todates);
	}

    public ArrayList<GenerateInvoiceVO> getGenerateInvoiceVOs(){
    	return getAttribute(FROM_DATES);
    }
    public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){

        return getAttribute(KEY_ONETIME_VOS);

        }

        public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){

        setAttribute(KEY_ONETIME_VOS, oneTimeVOs);

        }
        public void removeOneTimeVOs() {

        removeAttribute(KEY_ONETIME_VOS);

        }

}
