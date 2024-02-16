/*
 * ULDPoolOwnersImpl.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.misc;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolSegmentExceptionsVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDPoolOwnersSession;

/**
 * 
 * @author A-2046
 * 
 */
public class ULDPoolOwnersImpl extends AbstractScreenSession implements
		ULDPoolOwnersSession {

	private static final String SCREENID = "uld.defaults.uldpoolowners";

	private static final String MODULE = "uld.defaults";

	private static final String KEY_FLIGHTDETAILS = "flightDetails";

	private static final String KEY_POOLDETAILS = "pooldetails";
	
	private static final String KEY_POOLOWNER= "poolOwnerVO";
	
	private static final String KEY_POOLOWNERS= "poolOwnerVOs";
	
	private static final String KEY_POOLSEGMENT="ULDPoolSegmentExceptionsVO";
	
	private static final String SELECTEDROW= "selectedrow";
	
	/**
	 * 
	 * /** Method to get ScreenID
	 * 
	 * @return ScreenID
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * Method to get ProductName
	 * 
	 * @return ProductName
	 */
	public String getModuleName() {
		return MODULE;
	}
/**
 * @return
 */
	public FlightValidationVO getFlightValidationVOSession() {
		return getAttribute(KEY_FLIGHTDETAILS);
	}
	/**
	 * @param flightValidationVO
	 */
	public void setFlightValidationVOSession(
			FlightValidationVO flightValidationVO) {
		setAttribute(KEY_FLIGHTDETAILS, flightValidationVO);
	}
	
	/**
	 * @return
	 */
	public Collection<ULDPoolOwnerVO> getUldPoolOwnerVO() {
		return (Collection<ULDPoolOwnerVO>) getAttribute(KEY_POOLOWNER);

	}
/**
 * @param 
 */
	public void setUldPoolOwnerVO(Collection<ULDPoolOwnerVO> pooldetails) {
		setAttribute(KEY_POOLOWNER, (ArrayList<ULDPoolOwnerVO>) pooldetails);
	}
	/**
	 * @return
	 */
	public Collection<ULDPoolOwnerVO> getUldPoolOwnerVOs() {
		return (Collection<ULDPoolOwnerVO>) getAttribute(KEY_POOLOWNERS);

	}
/**
 * @param 
 */
	public void setUldPoolOwnerVOs(Collection<ULDPoolOwnerVO> pooldetails) {
		setAttribute(KEY_POOLOWNERS, (ArrayList<ULDPoolOwnerVO>) pooldetails);
	}

	/**
	 * @return
	 */
	public Collection<ULDPoolSegmentExceptionsVO> getUldPoolSegmentVos() {
		return (Collection<ULDPoolSegmentExceptionsVO>) getAttribute(KEY_POOLSEGMENT);

	}
/**
 * @param 
 */
	public void setUldPoolSegmentVos(Collection<ULDPoolSegmentExceptionsVO> segmentExceptionsVOS) {
		setAttribute(KEY_POOLSEGMENT, (ArrayList<ULDPoolSegmentExceptionsVO>) segmentExceptionsVOS);
	}
	/*
	 * 
	 */
		public void removeUldPoolSegmentVos() {
		// To be reviewed Auto-generated method stub
		removeAttribute(KEY_POOLSEGMENT);
	}
		
		/**
		 * @param airportCode
		 */
		public void setSelectedRow(String selectedRow) {
			setAttribute(SELECTEDROW,selectedRow);
		}
		 /**
		 * @return Returns the listStatus.
		 */
		public String getSelectedRow() {
			return getAttribute(SELECTEDROW);
		}
		


}
