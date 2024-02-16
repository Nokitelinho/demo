/*
 * ListUPURateCardSession.java Created on Jan 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-1556
 * @author A-2521
 */
public interface ListUPURateCardSession extends ScreenSession {

	/**
	 * @return rateCardVOs
	 */
	public Page<RateCardVO> getRateCardVOs();

	/**
	 * sets rateCardVOs
	 */
	public void setRateCardVOs(Page<RateCardVO> rateCardVOs);

	/**
	 * 
	 * removes rateCardVOs
	 */
	public void removeRateCardVOs();

	/**
	 * @return StatusOneTimeVOs
	 */
	public HashMap<String, Collection<OneTimeVO>> getStatusOneTime();

	/**
	 * sets StatusOneTimeVOs
	 */
	public void setStatusOneTime(
			HashMap<String, Collection<OneTimeVO>> statusOneTimeVOs);

	/**
	 * 
	 * removes StatusOneTimeVOs
	 */
	public void removeStatusOneTime();

	/**
	 * @return SelectedRateCardVOs
	 */
	public Page<RateCardVO> getSelectedRateCardVOs();

	/**
	 * sets rateCardVOs
	 */
	public void setSelectedRateCardVOs(Page<RateCardVO> rateCardVOs);

	/**
	 * 
	 * removes rateCardVOs
	 */
	public void removeSelectedRateCardVOs();

	/**
	 * sets rateCardFilterVO
	 * 
	 * @param rateCardFilterVO
	 */
	public void setRateCardFilterVO(RateCardFilterVO rateCardFilterVO);

	/**
	 * gets rateCardFilterVO
	 * 
	 * @return rateCardFilterVO
	 */
	public RateCardFilterVO getRateCardFilterVO();

	/**
	 * 
	 * removes rateCardFilterVO from session
	 */
	public void removeRateCardFilterVO();

	/**
	 * returns errorVOs
	 * 
	 * @return errorVOs
	 */
	public Collection<ErrorVO> getErrorVOs();

	/**
	 * sets errorVOs
	 */
	public void setErrorVOs(ArrayList<ErrorVO> errorVOs);

	/**
	 * removes errorVOs
	 */
	public void removeErrorVOs();

	// added by A-5175 for QF CR icrd-21098 starts
	public Integer getTotalRecords();

	public void setTotalRecords(int totalRecords);
	// added by A-5175 for QF CR icrd-21098 ends

	//Added by A-5166 for ICRD-17262 Starts
	/**
	 * @param rateCardVO
	 */
	public void setSelectedRateCardVO(RateCardVO rateCardVO);
	
	/**
	 * @return rateCradVO
	 */
	public RateCardVO getSelectedRateCardVO();
	//Added by A-5166 for ICRD-17262 Ends

}
