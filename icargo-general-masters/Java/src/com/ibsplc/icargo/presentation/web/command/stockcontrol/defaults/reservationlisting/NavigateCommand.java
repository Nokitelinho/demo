/*
 * NavigateCommand.java Created on Jan 17, 2006
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
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReservationListingSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReservationListingForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 *
 */
public class NavigateCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ScreenLoadReservationCommand");
    private static final String MODULE_NAME = "stockcontrol.defaults";
	private static final String SCREEN_ID = "stockcontrol.defaults.reservationlisting";
	private static final String SCREENLOAD_SUCCESS= "navigation_reservation_success";
	private static final String GET_NEXT_FAILURE = "navigation_reservation_failure";

	//Collection<StockVO> fullStockVOs = new ArrayList<StockVO>();

	/**
	 * execute method for handling the close action
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

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();


		Collection<ErrorVO> errors = null;
		if(errors!=null && errors.size() > 0 ) {
			invocationContext.addAllError(errors);
			invocationContext.target = GET_NEXT_FAILURE;
			return;
		}
		Collection<ReservationVO> origreservation = session.getReservationVO();
		//log.log(Log.FINE, "stockAllocationVO from session ----> "+stockAllocationVO);
		//ArrayList<StockVO> listStockVO = new ArrayList<StockVO>(
		Collection<ReservationVO> collReservationVO = session.getCancelReservationVO();
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

		//setting the stockAllocationVO to session
		session.setReservationVO(origreservation);
		//session.setCollForRemoval();
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


    	log.log(Log.FINE, "<---Entering in updateVOinCollection  ----> ");
    	log.log(Log.FINE, "reservationVO  ----> ", reservationVO);
		reservationVO.setDocumentNumber(form.getPopDocumentnumber());
    	reservationVO.setShipmentPrefix(form.getPopShipmentPrefix());
    	reservationVO.setReservationRemarks(form.getCancelRemarks());

    	log.log(Log.FINE, "<---Exiting from updateVOinCollection  ----> ");
    }




}

