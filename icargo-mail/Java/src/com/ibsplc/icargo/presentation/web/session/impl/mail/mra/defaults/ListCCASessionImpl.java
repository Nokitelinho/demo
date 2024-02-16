/*
 * ListCCASessionImpl.java Created on Jul 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ListCCAFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListCCASession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2270
 *
 */
public class ListCCASessionImpl extends AbstractScreenSession implements
		ListCCASession {

	/*
	 * The module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/*
	 * The screen id
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.listcca";

	private static final String KEY_ONETIME_VOS = "onetimevalues";

	private static final String KEY_CCALIST = "CCAdetailsVO";

	private static final String KEY_CCAFILTER = "CCAfilterVO";

	private static final String LISTSTATUS = "listStatus";
	
	private static final String CLOSE_STATUS = "closeStatus";
	
	private static final String TOTAL_RECORDS = "totalRecords";//added by A-5201 for CR ICRD-21098

	 private static final String KEY_SYSPARAMETERS="systemParameterByCodes"; //added by A-6991 for CR ICRD-208114

	/**
	 * @return
	 */
	public String getScreenID() {

		return SCREEN_ID;
	}

	/**
	 * @return MODULE_NAME
	 */
	public String getModuleName() {
		return MODULE_NAME;
	}

	/**
	 * 
	 *
	 */
	public ListCCASessionImpl() {
		super();
	}

	/**

	 *

	 * @return

	 */

	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs() {

		return getAttribute(KEY_ONETIME_VOS);

	}

	/**

	 *

	 * @param oneTimeVOs

	 */

	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs) {

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
	 * 
	 * @return
	 */
	public Page<CCAdetailsVO> getCCADetailsVOs() {
		return (Page<CCAdetailsVO>) getAttribute(KEY_CCALIST);
	}

	/**
	 * @param ccaDetailsVO
	 *  The ccaDetailsVO to set.
	 */
	public void setCCADetailsVOs(Page<CCAdetailsVO> ccaDetailsVO) {
		setAttribute(KEY_CCALIST, (Page<CCAdetailsVO>) ccaDetailsVO);
	}

	/**
	 * 
	 * @return
	 */
	public ListCCAFilterVO getCCAFilterVO() {
		return getAttribute(KEY_CCAFILTER);
	}

	/**
	 * @param listCCAFilterVO
	 * The listCCAFilterVO to set.
	 */
	public void setCCAFilterVO(ListCCAFilterVO listCCAFilterVO) {
		setAttribute(KEY_CCAFILTER, listCCAFilterVO);
	}

	/**
	 * @return Returns the listStatus.
	 */
	public String getListStatus() {
		return getAttribute(LISTSTATUS);
	}

	/**
	 * @param listStatus 
	 * The listStatus to set.
	 */
	public void setListStatus(String listStatus) {
		setAttribute(LISTSTATUS, listStatus);
	}
	
	/**
	 * @return Returns the listStatus.
	 */
	public String getCloseStatus() {
		return getAttribute(CLOSE_STATUS);
	}

	/**
	 * @param listStatus 
	 * The listStatus to set.
	 */
	public void setCloseStatus(String closeStatus) {
		setAttribute(CLOSE_STATUS, closeStatus);
	}
	
	//added by A-5201 for CR ICRD-21098 starts
	/**	 
	  * This method is used to get total records values from session
	  * from session
	  * @return Integer
	*/
	  public Integer getTotalRecords()
	    {
	        return (Integer)getAttribute(TOTAL_RECORDS);
	    }
	  
	  /**	
		  * This method is used to set total records values in session
		  * @param int
		*/
	    public void setTotalRecords(int totalRecords)
	    {
	        setAttribute(TOTAL_RECORDS, Integer.valueOf(totalRecords));
	    }
	    
	  //added by A-5201 for CR ICRD-21098 end
	    /**
	     * 
	     *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListCCASession#getSystemparametres()
	     *	Added by 			: A-6991 on 12-Jun-2017
	     * 	Used for 			: ICRD-208114
	     *	Parameters			:	@return
	     */
	    public HashMap<String, String> getSystemparametres(){
			return getAttribute(KEY_SYSPARAMETERS);
		}
		public void setSystemparametres(HashMap<String, String> sysparameters){
			setAttribute(KEY_SYSPARAMETERS, sysparameters);
		}
}
