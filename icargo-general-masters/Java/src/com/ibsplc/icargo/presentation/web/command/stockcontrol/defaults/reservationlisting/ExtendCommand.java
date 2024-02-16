/*
 * ExtendCommand.java Created on Jan 9, 2006
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
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReservationListingSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReservationListingForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-1619
 *
 */
public class ExtendCommand extends BaseCommand {

	private static final String MODULE_NAME = "stockcontrol.defaults";

	private static final String SCREEN_ID = "stockcontrol.defaults.reservationlisting";

	private static final String SCREENLOAD_SUCCESS= "extend_reservation_success";

	private Log log = LogFactory.getLogger("ScreenLoadReservationCommand");

	/**
	 * execute method for handling the extend action
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

			ReservationListingSession reservationListingSession
								   = getScreenSession(MODULE_NAME, SCREEN_ID);
			LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
				ReservationListingForm form =
								  (ReservationListingForm)invocationContext.screenModel;
				Collection<ReservationVO> reservationVOs = new ArrayList<ReservationVO>();
				 Collection<ReservationVO> extendVOs = new ArrayList<ReservationVO>();
				 String rowValue = form.getPopupRowId();
			     String[] selectedRows= rowValue.split(",");
				//log.log(Log.FINE, "selectedRows**********"+selectedRows);
				reservationVOs=reservationListingSession.getReservationVO();
                form.setPopupRowId(null);
				form.setRowId(null);
					if (selectedRows != null) {
						log.log(Log.FINE, "selectedRows**********",
								selectedRows);
						int size = selectedRows.length;
						log.log(Log.FINE, "size**********", size);
						if (reservationVOs != null && reservationVOs.size() > 0) {
							log.log(Log.FINE, "reservationVOs.size() > 0**********");
							int index =0;

							for (ReservationVO reservationVO :reservationVOs) {
								for (int j = 0; j < size; j++) {
									log.log(Log.FINE, "for**********", index);
							if (index == Integer.parseInt(selectedRows[j])) {

                                    extendVOs.add(reservationVO);
									}

								}

								index++;
							}


						}
			}
			log.log(Log.FINE, "extendVOs**********", extendVOs);
				ArrayList<ReservationVO> listExtend = new ArrayList<ReservationVO>(extendVOs);
					ReservationVO reservation = listExtend.get(0);
					log.log(Log.INFO,
							"First ReservationVO set in the form ---------->",
							reservation);
					form.setPopDocumentnumber(reservation.getDocumentNumber());
					form.setPopShipmentPrefix(reservation.getShipmentPrefix());
					//form.setExtendexpiryDate(reservation.getExpiryDate());
					LocalDate localDate = reservation.getExpiryDate();
					log.log(Log.INFO,
							"reservation.getExpiryDate() ---------->",
							reservation.getExpiryDate());
					String date = localDate.toDisplayDateOnlyFormat();							
				log.log(Log.INFO, "date ---------->", date);
					form.setExtendexpiryDate(date);
					form.setCancelRemarks(reservation.getReservationRemarks());

					form.setDisplayPage("1");
					form.setLastPageNum(String.valueOf(listExtend.size()));
					form.setTotalRecords(String.valueOf(listExtend.size()));
					form.setCurrentPageNum("1");

					if(extendVOs != null) {
						reservationListingSession.setExtendReservationVO(extendVOs);
						log.log(Log.INFO,
								"Collection set in the session---------->",
								extendVOs);
		}
				invocationContext.target=SCREENLOAD_SUCCESS;
			}

    }


