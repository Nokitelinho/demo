/**
 *DispatchEnquirySessionImpl Created on February 17, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations.national;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.DispatchEnquirySession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author a-4823
 *
 */
public class DispatchEnquirySessionImpl extends AbstractScreenSession implements DispatchEnquirySession{
	private static final String SCREEN_ID = "mailtracking.defaults.national.dispatchEnquiry";
	private static final String MODULE_NAME = "mail.operations";
	private static final String KEY_DESPATCHDETAILSVO = "despatchdetailsvo";
	private static final String KEY_DSNENQUIRYFILTERVO = "dsnenquiryfiltervo";
	private static final String KEY_ONETIME_VO = "oneTimeVOs";
	private static final String KEY_FLIGHTVALIDATIONVO = "flightvalidationvo";
	private static final String KEY_TOTAL_RECORDS = "totalRecords";//Added by A-5214 as part from the ICRD-21098
	
	/**
	 * This method returns the despatchDetailsVO
	 * @return KEY_DESPATCHDETAILSVO - DespatchDetailsVO
	 */
	public Page<DespatchDetailsVO> getDespatchDetailsVO() {
		return getAttribute(KEY_DESPATCHDETAILSVO);
	}

	/**
	 * This method is used to set despatchDetailsVO  to the session
	 * @param despatchDetailsVO - DespatchDetailsVO
	 */
	public void setDespatchDetailsVO(
			Page<DespatchDetailsVO> despatchDetailsVOPage) {
		setAttribute (KEY_DESPATCHDETAILSVO,despatchDetailsVOPage);

	}
	public void setFlightValidationVO(FlightValidationVO flightValidationVO) {

		setAttribute(KEY_FLIGHTVALIDATIONVO,flightValidationVO);	
	}


	public FlightValidationVO getFlightValidationVO() {

		return getAttribute(KEY_FLIGHTVALIDATIONVO);
	}


	/**
	 * This method returns the onetime vos
	 * @return KEY_ONETIME_VO - Collection<OneTimeVO>
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs() {

		return (HashMap<String, Collection<OneTimeVO>>)
		getAttribute(KEY_ONETIME_VO);
	}

	/**
	 * This method is used to set onetime values to the session
	 * @param oneTimeVOs - Collection<OneTimeVO>
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs) {
		setAttribute(KEY_ONETIME_VO,(
				HashMap<String, Collection<OneTimeVO>>)oneTimeVOs);

	}
	public String getModuleName() {

		return MODULE_NAME;
	}


	public String getScreenID() {

		return SCREEN_ID;
	}

	public DSNEnquiryFilterVO getDSNEnquiryFilterVO() {

		return getAttribute(KEY_DSNENQUIRYFILTERVO);
	}

	public void setDSNEnquiryFilterVO(DSNEnquiryFilterVO dsnEnquiryFilterVO) {
		setAttribute (KEY_DSNENQUIRYFILTERVO,dsnEnquiryFilterVO);

	}
	
	/**
	  * Added by A-5214 as part from the ICRD-21098
	  * This method is used to set total records values in session
	  * @param int
	*/
	public void setTotalRecords(int totalRecords){
     setAttribute(KEY_TOTAL_RECORDS, Integer.valueOf(totalRecords));
  }
	
	/**
	  * Added by A-5214 as part from the ICRD-21098
	  * This method is used to get total records values from session
	  * from session
	  * @return Integer
	*/
	
	public Integer getTotalRecords() {
		return (Integer)getAttribute(KEY_TOTAL_RECORDS);
	}


}
