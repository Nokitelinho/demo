/*
 * ExtendNavigateCommand.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.reservationlisting;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReservationListingSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReservationListingForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 *
 */
public class ExtendNavigateCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ScreenLoadReservationCommand");
    private static final String MODULE_NAME = "stockcontrol.defaults";
	private static final String SCREEN_ID = "stockcontrol.defaults.reservationlisting";
	private static final String SCREENLOAD_SUCCESS= "extend_navigation_reservation_success";
	private static final String GET_NEXT_FAILURE = "extend_navigation_reservation_failure";



	/**
	 * execute method for handling the navigate action
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

		log.entering("GetNextStockDetailsCommand", "execute");
		ReservationListingSession session
								   = getScreenSession(MODULE_NAME, SCREEN_ID);

		ReservationListingForm form =
						  (ReservationListingForm)invocationContext.screenModel;		
		Collection<ErrorVO> errors = null;
		errors = validateForm(form,session);
		if(errors!=null && errors.size() > 0 ) {
			invocationContext.addAllError(errors);
			invocationContext.target = GET_NEXT_FAILURE;
			return;
		}
		Collection<ReservationVO> origreservation = session.getReservationVO();
		Collection<ReservationVO> collReservationVO = session.getExtendReservationVO();
		log
				.log(Log.FINE, "rangeVOs from the session ----> ",
						collReservationVO);
		ArrayList<ReservationVO> listReservationVO = new ArrayList<ReservationVO>(collReservationVO);

		ReservationVO reservationVO = listReservationVO.get(Integer.parseInt(
				form.getCurrentPageNum())-1);

		log.log(Log.FINE, "reservationVO updated in the Collection ----> ",
				reservationVO);
		updateVOinCollection(form,reservationVO);


		ReservationVO newrangeVO = listReservationVO.get(Integer.parseInt(
													form.getDisplayPage())-1);
		log
				.log(Log.FINE, "newrangeVO populated in the form ----> ",
						newrangeVO);
		populateForm(form,newrangeVO);

       	//setting the page nos.
		form.setCurrentPageNum(form.getDisplayPage());
		form.setLastPageNum(String.valueOf(listReservationVO.size()));
		form.setTotalRecords(String.valueOf(listReservationVO.size()));

		
		session.setReservationVO(origreservation);
		
		invocationContext.target = SCREENLOAD_SUCCESS;

		log.exiting("GetNextStockDetailsCommand", "execute");
    }


    /**
     * @param form
     * @param reservationVO
     */
    public void populateForm(ReservationListingForm form, ReservationVO reservationVO) {    	
    	log.log(Log.FINE, "<---Entering in populateForm  ----> ");
    	log.log(Log.FINE, "reservationVO  ----> ", reservationVO);
		form.setPopDocumentnumber(reservationVO.getDocumentNumber());
    	form.setPopShipmentPrefix(reservationVO.getShipmentPrefix());
		LocalDate localDate = reservationVO.getExpiryDate();
		String date = localDate.toDisplayDateOnlyFormat();			
			//TimeConvertor.toStringFormat(
				//localDate.toCalendar(), "dd-MMM-yyyy");
				
				//	localDate.toCalendar(),logonAttributes.getDateFormat());
		form.setExtendexpiryDate(date);
		form.setCancelRemarks(reservationVO.getReservationRemarks());
    	log.log(Log.FINE, "<---Exiting from populateForm  ----> ");

    }

	/**
     * Update the VO from the form to Collection
     * @param form
     * @param reservationVO
     */
    public void updateVOinCollection(ReservationListingForm form,
             ReservationVO reservationVO) {
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	String stationCode=logonAttributes.getStationCode();
    	log.log(Log.FINE, "<---Entering in updateVOinCollection  ----> ");
    	log.log(Log.FINE, "reservationVO  ----> ", reservationVO);
		reservationVO.setDocumentNumber(form.getPopDocumentnumber());
    	reservationVO.setShipmentPrefix(form.getPopShipmentPrefix());
    	LocalDate localFromDate = new LocalDate(
    			logonAttributes.getAirportCode(),Location.ARP,true);
    	reservationVO.setExpiryDate((localFromDate.setDate
											(form.getExtendexpiryDate())));
    	reservationVO.setReservationRemarks(form.getCancelRemarks());

    	log.log(Log.FINE, "<---Exiting from updateVOinCollection  ----> ",
				reservationVO);
    }
  
    private Collection<ErrorVO> validateForm(ReservationListingForm form,
            ReservationListingSession session) {
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String stationCode=logonAttributes.getStationCode();
		log.entering("AddOkCommand", "validateForm");
		log.log(Log.INFO, "session.getExpiryDate()--------->", session.getExpiryDate());
		int date=Integer.parseInt(session.getExpiryDate());
		LocalDate currentdate=new LocalDate(
				logonAttributes.getAirportCode(),Location.ARP,false);
		log.log(Log.INFO, "currentdate---------->", currentdate);
		LocalDate maxextend=currentdate.addDays(date);
		log.log(Log.INFO, "maxextend---------->", maxextend);
		//log.log(Log.INFO,"maxdate---------->"+maxdate);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		/*
		* Validate whether date From entered is blank
		*/
		if(("").equals(form.getExtendexpiryDate())) {
			log.log(Log.INFO,"no date---------->");
			error = new ErrorVO("stockcontrol.defaults.mandatory",
					 new Object[] {"Range From"});
			errors.add(error);
		}
		LocalDate localFromDate = new LocalDate(
				logonAttributes.getAirportCode(),Location.ARP,true);
		if(!("").equals(form.getExtendexpiryDate())) {
		log.log(Log.INFO, "form.getExtendexpiryDate()---------->", form.getExtendexpiryDate());
		LocalDate formdate=localFromDate.setDate(form.getExtendexpiryDate());
		log.log(Log.INFO, "formdate---------->", formdate);
		log.log(Log.INFO, "maxextend---------->", maxextend);
		if(formdate.compareTo(maxextend)>0){
		log.log(Log.INFO,"error greater---------->");
		  	 error = new ErrorVO("stockcontrol.defaults.greaterthanmax",
				 new Object[] {"Range From"});
			    errors.add(error);
		}
		}
		if(!("").equals(form.getExtendexpiryDate())) {
			log.log(Log.INFO, "form.getExtendexpiryDate()---------->", form.getExtendexpiryDate());
			LocalDate formdate=localFromDate.setDate(form.getExtendexpiryDate());
            log.log(Log.INFO, "formdate---------->", formdate);
			log.log(Log.INFO, "maxextend---------->", maxextend);
			LocalDate current=new LocalDate(
            		logonAttributes.getAirportCode(),Location.ARP,false);
		  if(formdate.compareTo(current)<0){
			log.log(Log.INFO,"error greater---------->");
		        	 error = new ErrorVO("stockcontrol.defaults.greaterthancurrentdate",
					 new Object[] {"Range From"});
				    errors.add(error);
		}
      }
		return errors;
				}
		


}

