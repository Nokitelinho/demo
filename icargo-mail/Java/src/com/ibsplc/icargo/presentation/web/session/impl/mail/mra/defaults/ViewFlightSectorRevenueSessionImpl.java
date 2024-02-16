/*
 * ViewFlightSectorRevenueSessionImpl.java Created on Aug 19, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.FlightSectorRevenueFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SectorRevenueDetailsVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewFlightSectorRevenueSession;

/**
 * @author A-3429
 * 
 */
public class ViewFlightSectorRevenueSessionImpl extends AbstractScreenSession
		implements ViewFlightSectorRevenueSession {

	private static final String KEY_SEGMENTDETAILS = "sectorvos";

	private static final String KEY_REVENUEDETAILS = "revenueDetailvos";

	/*
	 * The module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/*
	 * The screen id
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.viewflightsectorrevenue";

	/*
	 * KEY FLIGHT DETAILS
	 */
	private static final String KEY_FLIGHTDETAILS = "flightvalidationvo";
	/*
	 * KEY LIST_STATUS
	 */
	private static final String LISTSTATUS = "listStatus";
	/*
	 * KEY SECTOR_DETAILS_FILTERVO
	 */
	private static final String KEY_SECTORDETAILSFILTERVO = "sectordetailsfiltervo";
	
	private static final String KEY_REVENUEDETAILSFILTERVO = "revenuedetailsfiltervo";
	
	

	/**
	 * @return SCREEN_ID
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
	 * @param sectorVOs
	 */
	public void setFlightSectorRevenueDetails(
			Collection<SectorRevenueDetailsVO> sectorVOs) {
		setAttribute(KEY_REVENUEDETAILS,
				(ArrayList<SectorRevenueDetailsVO>) sectorVOs);
	}

	/**
	 * @return Collection<SectorRevenueDetailsVO>
	 */
	public Collection<SectorRevenueDetailsVO> getFlightSectorRevenueDetails() {
		return (Collection<SectorRevenueDetailsVO>) getAttribute(KEY_REVENUEDETAILS);
	}

	/**
	 * @param sectorVOs
	 */
	public void setSectorDetails(Collection<SectorRevenueDetailsVO> sectorVOs) {
		setAttribute(KEY_SEGMENTDETAILS,
				(ArrayList<SectorRevenueDetailsVO>) sectorVOs);
	}

	/**
	 * @return Collection<SectorRevenueDetailsVO>
	 */
	public Collection<SectorRevenueDetailsVO> getSectorDetails() {
		return (Collection<SectorRevenueDetailsVO>) getAttribute(KEY_SEGMENTDETAILS);
	}

	/**
	 * @param flightValidationVO
	 */
	public void setFlightDetails(FlightValidationVO flightValidationVO) {
		setAttribute(KEY_FLIGHTDETAILS, flightValidationVO);
	}

	/**
	 * @return FlightValidationVO
	 */
	public FlightValidationVO getFlightDetails() {
		return getAttribute(KEY_FLIGHTDETAILS);
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
	 * @param FlightSectorRevenueFilterVO
	 */
	public void setSectorDetailsFilterVO(FlightSectorRevenueFilterVO sectorrevenueDetailsVO) {
		setAttribute(KEY_SECTORDETAILSFILTERVO, sectorrevenueDetailsVO);
	}

	/**
	 * @return FlightSectorRevenueFilterVO
	 */
	public FlightSectorRevenueFilterVO getSectorDetailsFilterVO() {
		return getAttribute(KEY_SECTORDETAILSFILTERVO);
	}
	/**
	 * @param FlightSectorRevenueFilterVO
	 */
	public void setRevenueDetailsFilterVO(FlightSectorRevenueFilterVO sectorrevenueDetailsVO) {
		setAttribute(KEY_REVENUEDETAILSFILTERVO, sectorrevenueDetailsVO);
	}

	/**
	 * @return FlightSectorRevenueFilterVO
	 */
	public FlightSectorRevenueFilterVO getRevenueDetailsFilterVO() {
		return getAttribute(KEY_REVENUEDETAILSFILTERVO);
	}
}
