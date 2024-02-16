/*
 * CancelCommand.java Created on Jan 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.reservationlisting;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.
														ReservationVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.
									defaults.ReservationListingSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.
													ReservationListingForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 *
 */
public class CancelCommand extends BaseCommand {

	private static final String MODULE_NAME = "stockcontrol.defaults";

	private static final String SCREEN_ID = "stockcontrol.defaults.reservationlisting";

	private static final String SCREENLOAD_SUCCESS= "cancel_reservation_success";
	private static final String CANCELLED= "Cancelled";

	private Log log = LogFactory.getLogger("ScreenLoadReservationCommand");

	/**
	 * execute method for handling the cancel action
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.log(Log.FINE, "inside cancel**********");
		ReservationListingSession reservationListingSession
						   = getScreenSession(MODULE_NAME, SCREEN_ID);

		ReservationListingForm form =
						  (ReservationListingForm)invocationContext.screenModel;
        Collection<ReservationVO> reservationVOs =
        	new ArrayList<ReservationVO>();
        Collection<ReservationVO> cancelVOs = new ArrayList<ReservationVO>();
        String rowValue = form.getPopupRowId();
        String[] selectedRows= rowValue.split(",");
       // log.log(Log.FINE, "selectedRows**********"+selectedRows);
        reservationVOs=reservationListingSession.getReservationVO();
        form.setPopupRowId(null);
		form.setRowId(null);
		Collection<ErrorVO> errVo = new ArrayList<ErrorVO>();
			if (selectedRows != null) {
				log.log(Log.FINE, "selectedRows**********", selectedRows);
				int size = selectedRows.length;
				log.log(Log.FINE, "size**********", size);
				if (reservationVOs != null && reservationVOs.size() > 0) {
					log.log(Log.FINE, "reservationVOs.size() > 0**********");
					int index =0;
					boolean isCancelled = false;
					for (ReservationVO reservationVO :reservationVOs) {
						for (int j = 0; j < size; j++) {
					if (index == Integer.parseInt(selectedRows[j])) {
						if( reservationVO.getDocumentStatus()!= null &&
								reservationVO.getDocumentStatus().
								trim().length()>0 ) {					
							if(CANCELLED.equalsIgnoreCase(
									reservationVO.getDocumentStatus().trim())) {
								log.log(Log.FINE, "index**********", index);
								isCancelled = true;							
							}
						}
						log.log(Log.FINE, "index**********", index);
							//form.setPopDocumentnumber(reservationVO.getDocumentNumber());
							//form.setPopShipmentPrefix(reservationVO.getShipmentPrefix());
                           // form.setCancelRemarks(reservationVO.getReservationRemarks());
                            cancelVOs.add(reservationVO);
							}
						}
						index++;
					}					
					if(isCancelled) {
						log.log(Log.FINE, "index**********", index);
						ErrorVO error = new ErrorVO("stockcontrol.reservation.reservationlisting.cancelled");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errVo.add(error);					
					}
				}
	}
			if(errVo!=null && errVo.size()>0) {
				log.log(Log.FINE, "index**********", errVo.size());
				invocationContext.addAllError(errVo);
    			invocationContext.target = "cancel_failure";  			
			}else {
	log.log(Log.FINE, "cancelVOs**********", cancelVOs);
	ArrayList<ReservationVO> listCancel = new ArrayList<ReservationVO>(cancelVOs);
		ReservationVO reservation = listCancel.get(0);
		log.log(Log.INFO, "First ReservationVO set in the form ---------->",
				reservation);
		form.setPopDocumentnumber(reservation.getDocumentNumber());
		form.setPopShipmentPrefix(reservation.getShipmentPrefix());
		form.setCancelRemarks(reservation.getReservationRemarks());

		form.setDisplayPage("1");
		form.setLastPageNum(String.valueOf(listCancel.size()));
		form.setTotalRecords(String.valueOf(listCancel.size()));
		form.setCurrentPageNum("1");

		if(cancelVOs != null) {
			reservationListingSession.setCancelReservationVO(cancelVOs);
			log.log(Log.INFO, "Collection set in the session---------->",
					cancelVOs);
		}

		invocationContext.target=SCREENLOAD_SUCCESS;
    }
    }

}
