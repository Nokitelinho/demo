/*
 * OkCancelCommand.java Created on Jan 9, 2006
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
public class OkCancelCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("RESERVATION LISTING");

	private static final String CANCEL_SUCCESS =
		"Cancel_reservation_success";

	private static final String MODULE_NAME = "stockcontrol.defaults";

	private static final String SCREEN_ID =
				"stockcontrol.defaults.reservationlisting";

    private static final String CANCEL_FAILURE = "Cancel_reservation_failure";



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

		StockControlDefaultsDelegate stockControlDefaultsDelegate =
			new StockControlDefaultsDelegate();
		 LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	     String companyCode=logonAttributes.getCompanyCode();


      ArrayList<ReservationVO> cancelVOs = (ArrayList<ReservationVO>)
                                          session.getCancelReservationVO();
      int pageIndex=Integer.parseInt(form.getCurrentPageNum())-1;
	  		ReservationVO reservationVO = cancelVOs.get(pageIndex);
	     updateVOinCollection(form,reservationVO);
	   cancelVOs.set(Integer.parseInt(form.getCurrentPageNum())-1,reservationVO);
	   Collection<ReservationVO> extendsaveVOs = new ArrayList<ReservationVO>();
	   ReservationVO reservVO=new ReservationVO ();

	   log.log(Log.FINE, "cancelVOs ---> ", cancelVOs);
		if(cancelVOs.size()>1){
		    	 for(ReservationVO reservationVOs:cancelVOs){
						reservationVOs.setCompanyCode(companyCode);
						reservationVOs.setOperationFlag("U");
						extendsaveVOs.add(reservationVOs);
						 log.log(Log.FINE, "cancelVOs.size()>1---> ",
								extendsaveVOs);
						reservationVOs=new ReservationVO();
					   }
		    }else if(cancelVOs.size()==1){
		    	 for(ReservationVO reservationVOs:cancelVOs){
		    		 log.log(Log.FINE, "cancelVOs.size()==1---> ",
							reservationVOs);
					reservationVOs.setCompanyCode(companyCode);
		    		 reservationVOs.setOperationFlag("U");
		    		 reservationVOs.setShipmentPrefix(form.getPopShipmentPrefix());
		    		 reservationVOs.setDocumentNumber(form.getPopDocumentnumber());
		    		 reservationVOs.setReservationRemarks(form.getCancelRemarks());
		    	extendsaveVOs.add(reservationVOs);
		    	reservationVOs=new ReservationVO();
		    }
		    }
		  log.log(Log.FINE, "extendsaveVOs ---> ", extendsaveVOs);
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
				try {
						stockControlDefaultsDelegate.cancelReservation(extendsaveVOs);

						} catch (BusinessDelegateException businessDelegateException) {
							errors=handleDelegateException(businessDelegateException);
								if (errors != null && errors.size() > 0) {
									form.setErrorStatus("Y");
									invocationContext.addAllError(errors);
									invocationContext.target = CANCEL_FAILURE;
				}
		}
						form.setErrorStatus("N");
    invocationContext.target = CANCEL_SUCCESS;
    }

    /**
     * @param form
     * @param reservationVO
     */
    public void updateVOinCollection(ReservationListingForm form,
	             ReservationVO reservationVO) {


	    	log.log(Log.FINE, "<---Entering in updateVOinCollection  ----> ");
	    	log.log(Log.FINE, "reservationVO  ----> ", reservationVO);
			reservationVO.setDocumentNumber(form.getPopDocumentnumber());
	    	reservationVO.setShipmentPrefix(form.getPopShipmentPrefix());
	    	reservationVO.setReservationRemarks(form.getCancelRemarks());
	    	log.log(Log.FINE, "reservationVO  ----> ", reservationVO);
			log.log(Log.FINE, "<---Exiting from updateVOinCollection  ----> ");
    }
}
