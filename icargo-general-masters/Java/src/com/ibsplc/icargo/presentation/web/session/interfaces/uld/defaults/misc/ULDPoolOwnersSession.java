/*
 * ULDPoolOwnersSession.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolSegmentExceptionsVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import java.util.Collection;
/**
 * 
 * @author A-2046
 *
 */
public interface ULDPoolOwnersSession extends ScreenSession {
	/**
	 * 
	 * @return screenID
	 */
	public String getScreenID();

	/**
	 * 
	 * @return modulename
	 */
	public String getModuleName();

	/**
	 * 
	 * @return FlightValidationVO
	 */
	public FlightValidationVO getFlightValidationVOSession();

	/**
	 * 
	 * @param flightValidationVO
	 */
	public void setFlightValidationVOSession(
			FlightValidationVO flightValidationVO);
	/**
	 * 
	 * @return
	 */
	public Collection<ULDPoolOwnerVO> getUldPoolOwnerVO();
	/**
	 * 
	 * @param poolDetails
	 */
	public void setUldPoolOwnerVO(Collection<ULDPoolOwnerVO> poolOwnerVO);
	
	/**
	 * 
	 * @return
	 */
	public Collection<ULDPoolOwnerVO> getUldPoolOwnerVOs();
	/**
	 * 
	 * @param poolDetails
	 */
	public void setUldPoolOwnerVOs(Collection<ULDPoolOwnerVO> poolOwnerVO);
	
	
	
	 /* @return
	 */
	public Collection<ULDPoolSegmentExceptionsVO> getUldPoolSegmentVos();
	/**
	 * 
	 * @param poolDetails
	 */
	public void setUldPoolSegmentVos(Collection<ULDPoolSegmentExceptionsVO> SegmentExceptionsVO);
	
	/*
	 * 
	 */
	public void removeUldPoolSegmentVos();
	/*
	 * 
	 */
	public String getSelectedRow();
	/*
	 * 
	 */
	public void setSelectedRow(String selectedRow);
	
}
