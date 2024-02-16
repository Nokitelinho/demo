/*
 * PrintCommand.java Created on Jan 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.reservationlisting;

import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReservationListingForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1747
 *
 */


public class PrintCommand extends AbstractPrintCommand {
	
	private Log log = LogFactory.getLogger("ReserveAWB PrintCommand");
	private static final String REPORT_ID="RPRSTK002";
	private static final String ACTION_SL = "generateReservationListReport";

	/**
	 * execute method for handling the close action
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ReservationListingForm form = (ReservationListingForm)invocationContext.screenModel;
    	ReservationFilterVO reservationFilterVO = new ReservationFilterVO();
    	ReservationFilterVO reservationFilterVOForReport = new ReservationFilterVO();
    	reservationFilterVO = getFilterForPrint(form);
 
	  //	filterVO for listing
	  reservationFilterVO = getFilterForPrint(form);
	  getReportSpec().addFilterValue(reservationFilterVO);
	// filterVO for populating
	reservationFilterVOForReport = getFilterForReport(form);
	getReportSpec().addFilterValue(reservationFilterVOForReport);
	  getReportSpec().setReportId(REPORT_ID);
	  getReportSpec().setResourceBundle(form.getBundle());
	  getReportSpec().setProductCode("stockcontrol");
	  getReportSpec().setSubProductCode("defaults");
	  getReportSpec().setAction(ACTION_SL);
	  if("true".equalsIgnoreCase(form.getPreview())){
		  getReportSpec().setPreview(true);
	  }else {
		  getReportSpec().setPreview(false);
	  }	 
		generateReport(); 			
	log.log(Log.FINE, "\n\n\n----------AFTER GENERATE REPORT----");
	invocationContext.target = getTargetPage();    	
    }
    
    /**
     * @param reservationListingForm
     * @return
     */
    private ReservationFilterVO getFilterForPrint(ReservationListingForm reservationListingForm) {
    	ReservationFilterVO reservationFilterVO = new ReservationFilterVO();
 		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		 if(reservationListingForm.getDocumentFilterType()!=null && 
				 	reservationListingForm.getDocumentFilterType().trim().length()>0) {
				reservationFilterVO.setDocumentType(reservationListingForm.getDocumentFilterType());
		 }
		 reservationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		 reservationFilterVO.setAirportCode(logonAttributes.getAirportCode());
		 if(reservationListingForm.getCustomerFilterCode()!=null 
				 && reservationListingForm.getCustomerFilterCode().trim().length()>0) {
				reservationFilterVO.setCustomerCode(
						reservationListingForm.getCustomerFilterCode().toUpperCase());
    	}
    	if(reservationListingForm.getAirlineFilterCode()!=null 
    			&& reservationListingForm.getAirlineFilterCode().trim().length()>0) {

    		reservationFilterVO.setAirlineCode(
    				reservationListingForm.getAirlineFilterCode().toUpperCase());
    	}
	    	LocalDate reserveLocalFromDate = new LocalDate(
	    			logonAttributes.getAirportCode(),Location.ARP,true);
	    	LocalDate reserveLocalToDate = new LocalDate(logonAttributes.getAirportCode(),
	    			Location.ARP,true);
			LocalDate expiryLocalFromDate = new LocalDate(logonAttributes.getAirportCode(),
					Location.ARP,true);
			LocalDate expiryLocalToDate = new LocalDate(logonAttributes.getAirportCode(),
					Location.ARP,true);

			if(reservationListingForm.getReservationFilterFromDate()!= null && 
					reservationListingForm.getReservationFilterFromDate().trim().length()>0){
				reservationFilterVO.setReservationFromDate(reserveLocalFromDate.setDate
								(reservationListingForm.getReservationFilterFromDate()));
			}
			if(reservationListingForm.getReservationFilterToDate()!=null &&
					reservationListingForm.getReservationFilterToDate().trim().length()>0){
				reservationFilterVO.setReservationToDate(reserveLocalToDate.setDate
								(reservationListingForm.getReservationFilterToDate()));
			}
			if(reservationListingForm.getExpiryFilterFromDate()!=null && 
					reservationListingForm.getExpiryFilterFromDate().trim().length()>0 ){
				reservationFilterVO.setExpiryFromDate(expiryLocalFromDate.setDate
								(reservationListingForm.getExpiryFilterFromDate()));
			}
			if(reservationListingForm.getExpiryFilterToDate()!= null && 
					reservationListingForm.getExpiryFilterToDate().trim().length()>0){
				reservationFilterVO.setExpiryToDate(expiryLocalToDate.setDate
								(reservationListingForm.getExpiryFilterToDate()));
			}	   	
    	return reservationFilterVO;     	
    }
    /**
     * @param reservationListingForm
     * @return
     */
    private ReservationFilterVO getFilterForReport(ReservationListingForm reservationListingForm) {
    	
    	ReservationFilterVO filterVO = new ReservationFilterVO();
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	
    	 if(reservationListingForm.getDocumentFilterType()!=null && 
				 	reservationListingForm.getDocumentFilterType().trim().length()>0) {				
    		 filterVO.setDocumentType(reservationListingForm.getDocumentFilterType());				
			}
		 if(reservationListingForm.getCustomerFilterCode()!=null 
				 && reservationListingForm.getCustomerFilterCode().trim().length()>0) {
			 filterVO.setCustomerCode(reservationListingForm.getCustomerFilterCode().toUpperCase());
	    }
	    	if(reservationListingForm.getAirlineFilterCode()!=null 
	    			&& reservationListingForm.getAirlineFilterCode().trim().length()>0) {
	    		filterVO.setAirlineCode(reservationListingForm.getAirlineFilterCode().trim());				
	    	}
	    	LocalDate reserveLocalFromDate = new LocalDate(logonAttributes.getStationCode(),Location.ARP,true);
	    	LocalDate reserveLocalToDate = new LocalDate(logonAttributes.getStationCode(),Location.ARP,true);
			LocalDate expiryLocalFromDate = new LocalDate(logonAttributes.getStationCode(),Location.ARP,true);
			LocalDate expiryLocalToDate = new LocalDate(logonAttributes.getStationCode(),Location.ARP,true);

			if(reservationListingForm.getReservationFilterFromDate()!= null && 
					reservationListingForm.getReservationFilterFromDate().trim().length()>0){
				filterVO.setReservationFromDate(reserveLocalFromDate.setDate
								(reservationListingForm.getReservationFilterFromDate()));
			}
			if(reservationListingForm.getReservationFilterToDate()!=null &&
					reservationListingForm.getReservationFilterToDate().trim().length()>0){
				filterVO.setReservationToDate(reserveLocalToDate.setDate
								(reservationListingForm.getReservationFilterToDate()));
			}
			if(reservationListingForm.getExpiryFilterFromDate()!=null && 
					reservationListingForm.getExpiryFilterFromDate().trim().length()>0 ){
				filterVO.setExpiryFromDate(expiryLocalFromDate.setDate
								(reservationListingForm.getExpiryFilterFromDate()));
			}
			if(reservationListingForm.getExpiryFilterToDate()!= null && 
					reservationListingForm.getExpiryFilterToDate().trim().length()>0){
				filterVO.setExpiryToDate(expiryLocalToDate.setDate
								(reservationListingForm.getExpiryFilterToDate()));
			}				
			return filterVO; 	
    }
	
	
	
}