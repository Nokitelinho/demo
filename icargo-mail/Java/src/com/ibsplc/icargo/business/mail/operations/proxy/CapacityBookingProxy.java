/*
 * CapacityBookingProxy.java Created on Jan 25, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.capacity.booking.vo.BookingSummaryVO;
import com.ibsplc.icargo.business.capacity.booking.vo.BookingVO;
import com.ibsplc.icargo.business.capacity.booking.vo.FlightMailBookingFilterVO;
import com.ibsplc.icargo.business.capacity.booking.vo.MailBookingVO;
import com.ibsplc.icargo.business.mail.operations.CapacityBookingProxyException;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-1303
 */
@Module("capacity")
@SubModule("booking")
public class CapacityBookingProxy extends ProductProxy {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
    /**
     * @author a-1883
     * @param flightMailBookingFilterVO
     * @return
     * @throws SystemException
     */
    public   Collection<MailBookingVO> findBookingDetailsForMail(
  			 FlightMailBookingFilterVO flightMailBookingFilterVO)
  			 throws CapacityBookingProxyException,SystemException {
     log.entering("CapacityBookingProxy","findBookingDetailsForMail");
     try {
		  return despatchRequest("findBookingDetailsForMail", 
					flightMailBookingFilterVO);
		  }catch (ProxyException ex) {
			throw new CapacityBookingProxyException(ex.getMessage(),ex);
	   	  }
    }
    
    /**
     * This method updates Mail Booking details 
	 * @author a-1883
	 * @param mailBookingVO
	 * @param mailBookingVOs
	 * @throws SystemException
	 */
	 public void updateBookingDetailsForMail(MailBookingVO mailBookingVO,
			 Collection<MailBookingVO> mailBookingVOs)
	    throws CapacityBookingProxyException,SystemException {
		 log.entering("CapacityBookingProxy","updateBookingDetailsForMail");
	    	try {
				 despatchRequest("updateBookingDetailsForMail",mailBookingVO,
						 mailBookingVOs);
			} catch (ProxyException ex) {
				throw new CapacityBookingProxyException(ex.getMessage(),ex);
		   	  }
			 log.exiting("CapacityBookingProxy","updateBookingDetailsForMail");
	 }
	 
	 
	/**
	 * @author A-1936
	 * This  method is used to save the Booking Details  
	 * Added By Karthick V as the part of the NCA Mail Tracking CR
	 * @param bookingVO
	 * @return
	 * @throws SystemException
	 * @throws CapacityBookingProxyException
	 */
	 public BookingSummaryVO  saveBookingDetails(BookingVO bookingVO)
		throws  SystemException,CapacityBookingProxyException {
	    log.log(Log.FINE, "Inside Delegate");
	    try{
	      return despatchRequest("saveBookingDetails", bookingVO);
        }catch (ProxyException ex) {
			throw new CapacityBookingProxyException(ex);
	   	  }
	 
	 }
	 
	 /**
	  * @author A-3227 RENO K ABRAHAM,  Added ON 26/09/08
	  * This Method will Update the Booking STATUS
	  * @param bookingVOs
	  * @throws SystemException
	  * @throws CapacityBookingProxyException
	  */
	 public void updateBookingStatus(Collection<BookingVO> bookingVOs)
	 throws  SystemException,CapacityBookingProxyException {
		 log.log(Log.FINE, "Inside Delegate");
		 try{
			 despatchRequest("updateBookingStatus", bookingVOs);
		 }catch (ProxyException ex) {
			 throw new CapacityBookingProxyException(ex);
		 }
	 }
	 
	 
	 
	 
}
