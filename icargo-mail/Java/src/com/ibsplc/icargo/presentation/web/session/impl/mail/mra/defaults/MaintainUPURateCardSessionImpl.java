/*
 * MaintainUPURateCardSessionImpl.java Created on Jan 19, 2007
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainUPURateCardSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-1556
 *
 */
public class MaintainUPURateCardSessionImpl extends AbstractScreenSession implements
        MaintainUPURateCardSession
   {
    private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.upuratecard.maintainupuratecard";

	private static final String KEY_UPURATECARDDETAILS="upuratecarddetails";

	private static final String KEY_UPURATELINEDETAILS="upuratelinedetails";
	private static final String KEY_NEWUPURATELINEDETAILS="newupuratelinedetails";
	private static final String KEY_ONETIME_VOS="onetimevalues";
	private static final String KEY_ERRORS = "errors";
	private static final String KEY_FROMPAGE="frompage";

    /**
     *
     */


    public MaintainUPURateCardSessionImpl() {
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
     * @return String
     */
    @Override
    public String getModuleName() {
        return MODULE_NAME;
    }
    /**
	 * @param ratecardVO
	 */
    public void setRateCardDetails(RateCardVO ratecardVO){
    	setAttribute(KEY_UPURATECARDDETAILS,(RateCardVO)ratecardVO);
    }
    /**
     * @return
     */
    public RateCardVO getRateCardDetails(){
    	return (RateCardVO)getAttribute(KEY_UPURATECARDDETAILS);
    }
    /**

    *

    *remove RateCardDetails

    */

    public void removeRateCardDetails(){
    	removeAttribute(KEY_UPURATECARDDETAILS);
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
 	 * @param ratelineVOs
 	 */
 	public void setRateLineDetails(Page<RateLineVO> ratelineVOs)
 	{
 		setAttribute(KEY_UPURATELINEDETAILS,(Page<RateLineVO>) ratelineVOs);
 	}
 	/**
 	 * @return
 	 */
 	public Page<RateLineVO> getRateLineDetails()
 	{
 		return (Page<RateLineVO>)getAttribute(KEY_UPURATELINEDETAILS);
 	}

 	/**
 	 * to remove RateLine details
 	 */
 	public void removeRateLineDetails()
 	{
 		removeAttribute(KEY_UPURATELINEDETAILS);
 	}

 	  /**
 	 * @param ratelineVOs
 	 */
 	public void setNewRateLineDetails(Page<RateLineVO> ratelineVOs)
 	{
 		setAttribute(KEY_NEWUPURATELINEDETAILS,(Page<RateLineVO>) ratelineVOs);
 	}
 	/**
 	 * @return
 	 */
 	public Page<RateLineVO> getNewRateLineDetails()
 	{
 		return (Page<RateLineVO>)getAttribute(KEY_NEWUPURATELINEDETAILS);
 	}

 	/**
 	 * to remove RateLine details
 	 */
 	public void removeNewRateLineDetails()
 	{
 		removeAttribute(KEY_NEWUPURATELINEDETAILS);
 	}
 	/**
 	 * @param fromPage
 	 */
 	public void setFromPage(String fromPage)
 	{
 		setAttribute(KEY_FROMPAGE,(String) fromPage);
 	}
 	/**
 	 * @return
 	 */
 	public String getFromPage()
 	{
 		return (String)getAttribute(KEY_FROMPAGE);
 	}
 	/**
 	 * to remove fromPage
 	 */
 	public void removeFromPage()
 	{
 		removeAttribute(KEY_FROMPAGE);
 	}
 	/**
	 * @return Collection<ErrorVO>
	 */
	public Collection<ErrorVO> getErrorVOs() {

		return (ArrayList<ErrorVO>)getAttribute(KEY_ERRORS );
	}

	  /**
 	 * @param errorVOs
 	 */
	public void setErrorVOs(ArrayList<ErrorVO> errorVOs) {
		setAttribute(KEY_ERRORS, errorVOs);

	}

	/**
	 * removes StatusOneTime
	 */
	public void removeErrorVOs() {
		removeAttribute(KEY_ERRORS);

	}
   

}
