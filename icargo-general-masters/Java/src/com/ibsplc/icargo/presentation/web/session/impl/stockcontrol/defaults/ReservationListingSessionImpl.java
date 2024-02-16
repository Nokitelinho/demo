/*
 * ReservationListingSessionImpl.java Created on Jan 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.stockcontrol.defaults;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReservationListingSession;

/**
 * @author A-1619
 *
 */
public class ReservationListingSessionImpl extends AbstractScreenSession
  implements ReservationListingSession {

	 /**
	 * String KEY_SCREEN_ID
	 */
	private static final String KEY_SCREEN_ID = "stockcontrol.defaults";
	/**
	 * String KEY_MODULE_NAME
	 */

   private static final String KEY_MODULE_NAME = "stockcontrol.defaults.reservationlisting";
   /**
	 * String RESERVATION_VO
	 */

  private static final String RESERVATION_VO ="ReservationVO";
  /**
	 * String DOCUMENT_VO
	 */

	private static final String DOCUMENT_VO ="DocumentType";
	/**
	 * String SYS_EXPIRY
	 */

  private static final String SYS_EXPIRY ="ExpiryDate";
  /**
  	 * String CANCEL_RESERVATION_VO
  	 */

  private static final String CANCEL_RESERVATION_VO ="CancelReservationVO";
   /**
    	 * String EXTEND_RESERVATION_VO
    	 */

  private static final String EXTEND_RESERVATION_VO ="ExtendReservationVO";
  private static final String SYS_ONETIM ="onetimes";


    /**
	 * This method returns the SCREEN ID for the Reservationlisting screen
	 */
  /**
	 * @return void
	 * @param
	 */
	public String getScreenID(){
		return KEY_SCREEN_ID;
	}

	/**
	 * This method returns the MODULE name for the Reservationlisting screen
	 */
	/**
	 * @return String
	 * @param
	 */
	public String getModuleName(){
		return KEY_MODULE_NAME;
	}

	/**
	 * @return HashMap
	 * @param
	 */
    public HashMap getOneTimeValues() {
        return null;
    }

    /**
	 * @return void
	 * @param oneTimevalues
	 */
    public void setOneTimeValues(HashMap oneTimevalues) {
    }

    /**
	 * @return Collection
	 * @param
	 */
    public Collection getReservationDetails() {
        return null;
    }

    /**
	 * @return void
	 * @param reservationDetails
	 */
    public void setReservationDetails(Collection reservationDetails) {
    }

    

    /**
	 * This method is used to get the ReservationVO from the session
	 * @param
	 * @return Collection<ReservationVO>
	 */
	public Collection<ReservationVO> getReservationVO(){
		return  (Collection<ReservationVO>) getAttribute(RESERVATION_VO);

	}

	/**This method sets the ReservationVO in the session
	 * @param reservationVO
	 * @return
	 */
	public void setReservationVO(Collection<ReservationVO> reservationVO){
		setAttribute(RESERVATION_VO,(ArrayList<ReservationVO>)reservationVO);
	}
	/**
	 * @return void
	 */
	public void removeReservationVO(){
		removeAttribute(RESERVATION_VO);
}
	/**
	 * @return Collection<ReservationVO>
	 * @param
	 */
   public Collection<ReservationVO> getCancelReservationVO(){
		return  (Collection<ReservationVO>) getAttribute(CANCEL_RESERVATION_VO);

	}

	/**This method sets the ReservationVO in the session
	 * @param cancelReservationVO
	 * @return
	 */
	public void setCancelReservationVO(Collection<ReservationVO> cancelReservationVO){
		setAttribute(CANCEL_RESERVATION_VO,(ArrayList<ReservationVO>) cancelReservationVO);
	}
	/**
	 * @return void
	 */
	public void removeCancelReservationVO(){
		removeAttribute(CANCEL_RESERVATION_VO);
   }
	/**
	 * @return Collection<ReservationVO>
	 * @param
	 */
	public Collection<ReservationVO> getExtendReservationVO(){
		return  (Collection<ReservationVO>) getAttribute(EXTEND_RESERVATION_VO);

	}

	/**This method sets the ReservationVO in the session
	 * @param extendReservationVO
	 * @return
	 */
	public void setExtendReservationVO(Collection<ReservationVO> extendReservationVO){
		setAttribute(EXTEND_RESERVATION_VO,(ArrayList<ReservationVO>) extendReservationVO);
	}
	/**
	 * @return void
	 */
	public void removeExtendReservationVO(){
		removeAttribute(EXTEND_RESERVATION_VO);
   }
	 /**
	 * This method is used to get the ReservationVO from the session
	 * @param
	 * @return Collection<DocumentVO>
	 */
	public Collection<DocumentVO> getDocumentType(){
		return  (Collection<DocumentVO>) getAttribute(DOCUMENT_VO);

	}

	/**This method sets the ReservationVO in the session
	 * @param documentType
	 * @return
	 */
	public void setDocumentType(Collection<DocumentVO> documentType){
		setAttribute(DOCUMENT_VO,(ArrayList<DocumentVO>)documentType);
	}
	/**
	 * @return void
	 */
	public void removeDocumentType(){
		removeAttribute(DOCUMENT_VO);
	}
	/**
	 * @return String
	 * @param
	 */
	public String getExpiryDate(){
		return (String) getAttribute(SYS_EXPIRY);
	}
	/**
	 * @return void
	 * @param expiryDate
	 */
	public void setExpiryDate(String expiryDate){
		setAttribute(SYS_EXPIRY,(String) expiryDate);
	}
	/**
	 * Methods for removing ExpiryDate
	 */
	/**
	 * @return void
	 * @param
	 */
	public void removeExpiryDate(){
		removeAttribute(SYS_EXPIRY);
	}
	/**
	 * @return Collection<OneTimeVO>
	 * @param
	 */
	public Collection<OneTimeVO> getOnetime(){
		return (Collection<OneTimeVO>) getAttribute(SYS_ONETIM);
	}
	/**
	 * Methods for setting ExpiryDate
	 */
	/**
	 * @return void
	 * @param expiryDate
	 */
	public void setOnetime(Collection<OneTimeVO> expiryDate){
		setAttribute(SYS_ONETIM,(ArrayList<OneTimeVO>) expiryDate);
	}
	/**
	 * Methods for removing ExpiryDate
	 */
	/**
	 * @return void
	 * @param
	 */
	public void removeOnetime(){
		removeAttribute(SYS_ONETIM);
	}
}
