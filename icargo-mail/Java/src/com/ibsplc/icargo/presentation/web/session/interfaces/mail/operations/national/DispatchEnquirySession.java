/**
 * DispatchEnquirySession Created on February 17, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author a-4823
 *
 */
public interface DispatchEnquirySession extends ScreenSession{
	/**
	 * 
	 * @return DespatchDetailsVO
	 */
	public Page<DespatchDetailsVO> getDespatchDetailsVO();
	/**
	 * 
	 * @param despatchDetailsVOPage
	 */
	public void setDespatchDetailsVO(Page<DespatchDetailsVO> despatchDetailsVOPage);
	/**
	 * @return Returns the oneTimeVOs.
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();
	/**
	 * @param oneTimeVOs The oneTimeVOs to set.
	 */
	public void setOneTimeVOs(
			HashMap<String, Collection<OneTimeVO>> oneTimeVOs);
	public DSNEnquiryFilterVO getDSNEnquiryFilterVO();
	public void setDSNEnquiryFilterVO(DSNEnquiryFilterVO dsnEnquiryFilterVO);
	/**
	 * 
	 * @param flightValidationVO
	 */
	public void setFlightValidationVO(FlightValidationVO flightValidationVO);
	/**
	 * 
	 * @return FlightValidationVO
	 */
	public FlightValidationVO getFlightValidationVO();
	
    void setTotalRecords(int totalRecords);//Added by A-5214 as part from the ICRD-21098 starts
	
	Integer getTotalRecords();//Added by A-5214 as part from the ICRD-21098 starts
	
}
