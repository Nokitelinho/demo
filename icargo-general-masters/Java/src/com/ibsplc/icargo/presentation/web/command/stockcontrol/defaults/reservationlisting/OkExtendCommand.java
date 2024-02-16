/*
 * OkExtendCommand.java Created on Jan 9, 2006
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
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReservationListingSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReservationListingForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 *
 */
public class OkExtendCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("RESERVATION LISTING");

	private static final String EXTEND_SUCCESS =
		"Extend_reservation_success";

	private static final String MODULE_NAME = "stockcontrol.defaults";

	private static final String SCREEN_ID =
				"stockcontrol.defaults.reservationlisting";

    private static final String EXTEND_FAILURE = "Extend_reservation_failure";



    /**
	 * execute method for handling the close action
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

	ReservationListingForm form = (ReservationListingForm)invocationContext.screenModel;

     ReservationListingSession session =
			              getScreenSession(MODULE_NAME, SCREEN_ID);
     LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
     String companyCode=logonAttributes.getCompanyCode();
     String stationCode=logonAttributes.getStationCode();
		StockControlDefaultsDelegate stockControlDefaultsDelegate =
			new StockControlDefaultsDelegate();



		     ArrayList<ReservationVO> extendVOs = (ArrayList<ReservationVO>)
                                          session.getExtendReservationVO();
            int pageIndex=Integer.parseInt(form.getCurrentPageNum())-1;
	  		ReservationVO reservationVO = extendVOs.get(pageIndex);

/*****************************/

//Added

		   Collection<ErrorVO> errors = null;
			errors = validateForm(form,session,reservationVO);
			if(errors!=null && errors.size() > 0 ) {
				form.setErrorStatus("Y");
				invocationContext.addAllError(errors);
				invocationContext.target = EXTEND_FAILURE;
				return;
			 }

/*****************************/

	  		updateVOinCollection(form,reservationVO);
         	extendVOs.set(Integer.parseInt(form.getCurrentPageNum())-1,reservationVO);
		   // Collection<ReservationVO> extendVOs =session.getExtendReservationVO();
		    Collection<ReservationVO> extendsaveVOs = new ArrayList<ReservationVO>();


		    ReservationVO reservVO=new ReservationVO ();
		    log.log(Log.FINE, "extendVOs ---> ", extendVOs);
			if(extendVOs.size()>1){
		    	 for(ReservationVO reservationVOs:extendVOs){
						reservationVOs.setCompanyCode(companyCode);
						reservationVOs.setOperationFlag("U");
						extendsaveVOs.add(reservationVOs);
						reservationVOs=new ReservationVO();
					   }
		    }else if(extendVOs.size()==1){
		    	for(ReservationVO reservationVOs:extendVOs){
		    		reservationVOs.setCompanyCode(companyCode);
		    		reservationVOs.setOperationFlag("U");
		    		reservationVOs.setShipmentPrefix(form.getPopShipmentPrefix());
		    		reservationVOs.setDocumentNumber(form.getPopDocumentnumber());
		    	    LocalDate localFromDate = new LocalDate(
		    	    		logonAttributes.getAirportCode(),Location.ARP,true);
		    	     reservationVO.setExpiryDate((localFromDate.setDate
							(form.getExtendexpiryDate())));
		    	   reservationVOs.setReservationRemarks(form.getCancelRemarks());
		    	  extendsaveVOs.add(reservationVOs);
		    	  reservationVOs=new ReservationVO();
				   }
		       }
		    log.log(Log.FINE, "extendsaveVOs ---> ", extendsaveVOs);
		// Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		try {

				stockControlDefaultsDelegate.extendReservation(extendsaveVOs);

				} catch (BusinessDelegateException businessDelegateException) {
					errors=handleDelegateException(businessDelegateException);
						if (errors != null && errors.size() > 0) {
							form.setErrorStatus("Y");
							invocationContext.addAllError(errors);
							invocationContext.target = EXTEND_FAILURE;
							return;
						}
		}
		form.setErrorStatus("N");
    invocationContext.target = EXTEND_SUCCESS;
    }
     /**
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
    		              ReservationListingSession session,
    		              ReservationVO reservationVO) {
    	 LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	 String stationCode=logonAttributes.getStationCode();
			log.entering("AddOkCommand", "validateForm");
			log.log(Log.INFO, "session.getExpiryDate()--------->", session.getExpiryDate());
			int date=Integer.parseInt(session.getExpiryDate());
			log.log(Log.INFO, "\n@@getExpiryDate---------->", reservationVO.getExpiryDate());
			LocalDate maxextend=reservationVO.getExpiryDate().addDays(date);
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
			LocalDate current=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
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
