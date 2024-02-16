/*
 * ViewFlightSectorRevenueSession.java Created on Aug 19, 2008
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.Collection;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.FlightSectorRevenueFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SectorRevenueDetailsVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-3429
 * 
 */
public interface ViewFlightSectorRevenueSession extends ScreenSession {

	/**
	 * @return Collection<SectorRevenueDetailsVO>
	 */

	public Collection<SectorRevenueDetailsVO> getFlightSectorRevenueDetails();

	/**
	 * @param Collection
	 *            <SectorRevenueDetailsVO> sectorVOs
	 */
	public void setFlightSectorRevenueDetails(
			Collection<SectorRevenueDetailsVO> sectorVOs);

	/**
	 * @return Collection<SectorRevenueDetailsVO>
	 */

	public Collection<SectorRevenueDetailsVO> getSectorDetails();

	/**
	 * @param Collection
	 *            <SectorRevenueDetailsVO> sectorVOs
	 */
	public void setSectorDetails(Collection<SectorRevenueDetailsVO> sectorVOs);

	/**
	 * @return FlightValidationVO
	 */
	public FlightValidationVO getFlightDetails();

	/**
	 * @param flightValidationVO
	 */
	public void setFlightDetails(FlightValidationVO flightValidationVO);
	
	/**
	 * @return Returns the listStatus.
	 */
	String getListStatus();

	/**
	 * @param listStatus
	 * The listStatus to set.
	 */
	void setListStatus(String listStatus);
	
	/**
	 * @param Collection
	 *            <SectorRevenueDetailsVO> sectorVOs
	 */
	public void setSectorDetailsFilterVO(
			FlightSectorRevenueFilterVO sectordetailsFilterVO);

	/**
	 * @return Collection<SectorRevenueDetailsVO>
	 */

	public FlightSectorRevenueFilterVO getSectorDetailsFilterVO();
	/**
	 * @param Collection
	 *            <SectorRevenueDetailsVO> sectorVOs
	 */
	public void setRevenueDetailsFilterVO(
			FlightSectorRevenueFilterVO sectordetailsFilterVO);

	/**
	 * @return Collection<SectorRevenueDetailsVO>
	 */

	public FlightSectorRevenueFilterVO getRevenueDetailsFilterVO();

}
