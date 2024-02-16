/*
 * BookingTimeVO.java Created on Jun 30, 2016
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-5991
 *
 */
public class BookingTimeVO  extends AbstractVO {
	
	
	public static final String MODULE = "mail";
	public static final String SUBMODULE = "operations";

    private String ubrNumber;
    private LocalDate bookingLastUpdateTime;
    private LocalDate bookingFlightDetailLastUpdTime;
    
    
	/**
	 * @return the bookingFlightDetailLastUpdTime
	 */
	public LocalDate getBookingFlightDetailLastUpdTime() {
		return bookingFlightDetailLastUpdTime;
	}
	/**
	 * @param bookingFlightDetailLastUpdTime the bookingFlightDetailLastUpdTime to set
	 */
	public void setBookingFlightDetailLastUpdTime(
			LocalDate bookingFlightDetailLastUpdTime) {
		this.bookingFlightDetailLastUpdTime = bookingFlightDetailLastUpdTime;
	}
	/**
	 * @return the bookingLastUpdateTime
	 */
	public LocalDate getBookingLastUpdateTime() {
		return bookingLastUpdateTime;
	}
	/**
	 * @param bookingLastUpdateTime the bookingLastUpdateTime to set
	 */
	public void setBookingLastUpdateTime(LocalDate bookingLastUpdateTime) {
		this.bookingLastUpdateTime = bookingLastUpdateTime;
	}
	/**
	 * @return the ubrNumber
	 */
	public String getUbrNumber() {
		return ubrNumber;
	}
	/**
	 * @param ubrNumber the ubrNumber to set
	 */
	public void setUbrNumber(String ubrNumber) {
		this.ubrNumber = ubrNumber;
	}

}
