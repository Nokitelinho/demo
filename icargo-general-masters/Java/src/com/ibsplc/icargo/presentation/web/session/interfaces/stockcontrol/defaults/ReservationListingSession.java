/*
 * ReservationListingSession.java Created on Jan 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1619
 *
 */
public interface ReservationListingSession extends ScreenSession {

    /**
     * Methos used to get Payment type onetime VOs from Session
     * @return
     */
    //Todo HashMap( OnetimeConst ,Collection<oneTimeVos>
    HashMap getOneTimeValues();

    /**
     * Method used to set the onetimeVOs to session
     * @param oneTimevalues
     */
    //Todo HashMap( OnetimeConst ,Collection<oneTimeVos>
    void setOneTimeValues(HashMap oneTimevalues);

    /**
     *
     * @return
     */
    Collection getReservationDetails();


    /**
     *
     * @param reservationDetails
     */
    void setReservationDetails(Collection reservationDetails);
    /**
	 *
	 * @return ReservationVO
	 */

	public Collection<ReservationVO> getReservationVO();

	 /**
	  * This method is used to set the  ReservationVO
	  * into the session reservationVO
	  */
	public void setReservationVO(Collection<ReservationVO> reservationVO);

	/**
	 * Method for removing ReservationVO from session
	 *
	 */
	public void removeReservationVO();
	/**
	 * @param 
	 * @return Collection<ReservationVO>
	 * */
	public Collection<ReservationVO> getCancelReservationVO();

	 /**
	  * This method is used to set the  ReservationVO
	  * into the session Collection<ReservationVO>
	  */
	public void setCancelReservationVO(Collection<ReservationVO> cancelReservationVO);

	/**
	 * Method for removing ReservationVO from session
	 *
	 */
	public void removeCancelReservationVO();
	/**
	 * @param 
	 * @return Collection<ReservationVO>
	 * */
	public Collection<ReservationVO> getExtendReservationVO();

	 /**
	  * This method is used to set the  ReservationVO
	  * into the session Collection<ReservationVO>
	  */
	public void setExtendReservationVO(Collection<ReservationVO> extendReservationVO);

	/**
	 * Method for removing ReservationVO from session
	 *
	 */
	public void removeExtendReservationVO();
	/**
	 * @param 
	 * @return Collection<DocumentVO>
	 * */
	public Collection<DocumentVO> getDocumentType();

	 /**
	  * This method is used to set the  ReservationVO
	  * into the session Collection<ReservationVO>
	  */
	public void setDocumentType(Collection<DocumentVO> documentType);

	/**
	 * Method for removing ReservationVO from session
	 *
	 */
	public void removeDocumentType();
	 /**
	 * Methods for getting ExpiryDate
	 */
	public String getExpiryDate();

	/**
	 * Methods for setting ExpiryDate
	 */
	public void setExpiryDate(String expiryDate);

	/**
	 * Methods for removing ExpiryDate
	 */
	public void removeExpiryDate();
	/**
	 * @param 
	 * @return Collection<OneTimeVO>
	 * */
	public Collection<OneTimeVO> getOnetime();

	/**
	 * Methods for setting ExpiryDate
	 */
	public void setOnetime(Collection<OneTimeVO> expiryDate);

	/**
	 * Methods for removing ExpiryDate
	 */
	public void removeOnetime();
}
