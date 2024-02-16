/*
 * ListUPURateCardSessionImpl.java Created on Jan 19, 2007
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateCardSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-1556
 * @author A-2521
 */
public class ListUPURateCardSessionImpl extends AbstractScreenSession
implements ListUPURateCardSession {

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.listuparatecard";

	private static final String KEY_RATECARDS = "ratecardvos";

	private static final String KEY_STATUS_ONETIME = "retecardstatus";

	private static final String KEY_SELEC_RATECARDS = "selectedratecardvos";

	private static final String KEY_FILTER = "ratecardfilter";

	private static final String KEY_ERRORS = "errors";
	
	private static final String KEY_TOTALRECORDS = "totalrecords";
	
	private static final String KEY_SELECTEDRATECARD ="selectedratecard";
	/**
     *
     */
    public ListUPURateCardSessionImpl() {
        super();

    }

    /**
     *
     */
   	@Override
	public String getScreenID() {
		return SCREENID;
	}

   	/**
   	 *
   	 */
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}
	/**
	 * returns RateCardVOs
	 * 
	 * @return 
	 */
	public Page<RateCardVO> getRateCardVOs() {

		return getAttribute( KEY_RATECARDS );
	}

	/**
	 * sets rateCardVOs
	 * 
	 * @param rateCardVOs
	 */
	public void setRateCardVOs(Page<RateCardVO> rateCardVOs) {
		setAttribute(KEY_RATECARDS, rateCardVOs);

	}

	/**
	 * removes StatusOneTime
	 */
	public void removeRateCardVOs() {
		removeAttribute(KEY_RATECARDS);

	}

	/**
	 *  returns StatusOneTime
	 *  
	 *  @return statusOneTimeVOs
	 */
	public HashMap<String, Collection<OneTimeVO>> getStatusOneTime(){
		return getAttribute( KEY_STATUS_ONETIME );
	}

	/**
	 * sets StatusOneTimeVOs
	 * 
	 * @param statusOneTimeVOs
	 */
	public void setStatusOneTime(HashMap<String, Collection<OneTimeVO>> statusOneTimeVOs){
		setAttribute(KEY_STATUS_ONETIME, statusOneTimeVOs);
	}

	/**
	 *
	 * removes StatusOneTime
	 */
	public void removeStatusOneTime(){
		removeAttribute(KEY_STATUS_ONETIME);
	}

	/**
	 *  returns SelectedRateCardVOs
	 *  
	 *  @return rateCardVOs
	 */
	public Page<RateCardVO> getSelectedRateCardVOs() {

		return getAttribute( KEY_SELEC_RATECARDS );
	}

	/**
	 * sets rateCardVOs
	 * 
	 * @param rateCardVOs
	 */
	public void setSelectedRateCardVOs(Page<RateCardVO> rateCardVOs) {
		setAttribute(KEY_SELEC_RATECARDS, rateCardVOs);

	}

	/**
	 * removes SelectedRateCardVOs
	 */
	public void removeSelectedRateCardVOs() {
		removeAttribute(KEY_SELEC_RATECARDS);

	}

	/**
	 * sets rateCardFilterVO
	 * 
	 * @param rateCardFilterVO
	 */
	public void setRateCardFilterVO(RateCardFilterVO rateCardFilterVO) {
		setAttribute(KEY_FILTER, rateCardFilterVO);

	}

	/**
	 * gets rateCardFilterVO
	 *
	 * @return rateCardFilterVO
	 */
	public RateCardFilterVO getRateCardFilterVO() {
		return getAttribute( KEY_FILTER );
	}

	/**
	 * removes rateCardFilterVO
	 */
	public void removeRateCardFilterVO() {
		removeAttribute(KEY_FILTER);

	}

	/**
	 * returns RateCardVOs
	 * 
	 * @return ErrorVOs
	 */
	public Collection<ErrorVO> getErrorVOs() {

		return (ArrayList<ErrorVO>)getAttribute(KEY_ERRORS );
	}

	/**
	 * sets ErrorVOs
	 * 
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

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateCardSession#getTotalRecords()
	 *	Added by 			: A-5175 on 16-Oct-2012
	 * 	Used for 	: ICRD-21098
	 *	Parameters	:	@return 
	 */
	
	public Integer getTotalRecords() {
			return getAttribute(KEY_TOTALRECORDS);
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateCardSession#setTotalRecords(int)
	 *	Added by 			: A-5175 on 16-Oct-2012
	 * 	Used for 	: ICRD-21098
	 *	Parameters	:	@param totalRecords 
	 */

	public void setTotalRecords(int totalRecords) {
		setAttribute(KEY_TOTALRECORDS, totalRecords);
	}
	
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateCardSession#getSelectedRateCardVO()
	 *	Added by 			: A-5166 on 07-Feb-2013
	 * 	Used for 	: ICRD-17262
	 *	Parameters	:	@return 
	 */
	public RateCardVO getSelectedRateCardVO() {

		return getAttribute( KEY_SELECTEDRATECARD );
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateCardSession#setSelectedRateCardVO(RateCardVO)
	 *	Added by 			: A-5166 on 07-Feb-2013
	 * 	Used for 	: ICRD-17262
	 *	Parameters	:	@param rateCardVO 
	 */

	public void setSelectedRateCardVO(RateCardVO rateCardVO) {
		setAttribute(KEY_SELECTEDRATECARD, rateCardVO);

	}

}
