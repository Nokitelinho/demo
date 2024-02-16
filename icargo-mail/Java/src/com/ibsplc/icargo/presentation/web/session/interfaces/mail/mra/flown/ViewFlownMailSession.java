/*
 * ViewFlownMailSession.java Created on Feb 14, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author a-2449
 *
 */
public interface ViewFlownMailSession extends ScreenSession{
	
	/**
	 * @return HashMap<String, Collection<OneTimeVO>>
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();
	
	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);
	
	
	/**
	 * @param flownMailSegmentVO
	 */
	public void setFlownMailSegmentVO(FlownMailSegmentVO flownMailSegmentVO);
	
	
	/**
	 * @return FlownMailSegmentVO
	 */
	public FlownMailSegmentVO getFlownMailSegmentVO();
	
	
	/**
	 *  remove FlownMailSegmentVO
	 */
	public void removeFlownMailSegmentVO();
	
	
	/**
	 * @param flownMailFilterVO
	 */
	public void setFilter(FlownMailFilterVO flownMailFilterVO);
	
	
	/**
	 * @return FlownMailFilterVO
	 */
	public FlownMailFilterVO getFilter();
	
	/**
	 * @return FlightValidationVO
	 */
	public FlightValidationVO getFlightDetails();
	
	
	/**
	 * @param flightValidationVO
	 */
	public void setFlightDetails(FlightValidationVO flightValidationVO);
	
	/**
	 *  remove FlightValidationVO
	 */
	public void removeFlightDetails();
	
	/**
	 * @return Collection<FlownMailSegmentVO>
	 */
	
	public Collection<FlownMailSegmentVO> getSegmentDetails();
	
	/**
	 * @param Collection<FlownMailSegmentVO> flownMailSegmentVO
	 */
	public void setSegmentDetails(Collection<FlownMailSegmentVO> flownMailSegmentVOs);
	
	/**
	 * remove Collection<FlownMailSEgmentVO> 
	 */
	public void removeSegmentDetails();	
	
	/**
	 * @return FlownMailFilterVO
	 */
	public FlownMailFilterVO getListFilterDetails();
	
	/**
	 * @param flownMailFilterVO
	 */
	public void setListFilterDetails(FlownMailFilterVO flownMailFilterVO);
	
	/**
	 * remove FlownMailFilterVO
	 */
	public void removeListFilterDetails();
	
}
