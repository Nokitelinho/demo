/*
 * ProrateCommand.java Created on Sep, 2008
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listprorationexceptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListProrationExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListMailProrationExceptionsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-3108
 *
 */
public class ProrateCommand extends BaseCommand {
	@Override
	public boolean breakOnInvocationFailure() {

		return true;
	}
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA PRORATION");

	private static final String SCREEN_ID = "mailtracking.mra.defaults.listmailprorationexceptions";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String CLASS_NAME = "ProrateCommand";
	private static final String SUCCESS = "Prorate_Success";
	private static final String FAILURE = "Prorate_Failure";
	private static final String FLIGHT ="FLT";
	private static final String CARRIERCODE_YY ="YY";
	private static final String ERR_NORECORDS ="mra.proration.listexceptions.msg.err.noeligiblerecords";

	/**
	 * Execute method
	 *
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 * Code Modified As Part of ICRD-106032
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");

		ListMailProrationExceptionsForm listExceptionForm = (ListMailProrationExceptionsForm) invocationContext.screenModel;

		ListProrationExceptionsSession listExceptionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);

		HashMap<String,FlightValidationVO>toBeProratedFlightsMap=new HashMap<String,FlightValidationVO>();
		List<ProrationExceptionVO> prorationExceptionVOs = new ArrayList<ProrationExceptionVO>();
		Collection<FlightValidationVO> toBeProratedFlightVOs = new ArrayList<FlightValidationVO>();
		MailTrackingMRADelegate mailTrackingMRADelegate=new MailTrackingMRADelegate();

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String[] selectedRows = listExceptionForm.getSelectedRows().split("-");

		if(selectedRows!=null){
			
			String key=null;
			for (String index : selectedRows) {
				prorationExceptionVOs.add(listExceptionSession.getProrationExceptionVOs().get(
						Integer.parseInt(index)));
			}
			for(ProrationExceptionVO proExceptionVO:prorationExceptionVOs){
					
					log.log(Log.FINE,"Selected exceptionExceptionVO frm VOs>>",proExceptionVO);

					if(CARRIERCODE_YY.equals(proExceptionVO.getCarrierCode())){
						continue;
					}

					FlightValidationVO flightValidationVO= new FlightValidationVO();	
					flightValidationVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
					flightValidationVO.setFlightCarrierId(proExceptionVO.getFlightCarrierIdentifier());
					flightValidationVO.setFlightNumber(proExceptionVO.getFlightNumber());
					flightValidationVO.setFlightSequenceNumber(proExceptionVO.getFlightSequenceNumber());
					
					key=new StringBuilder(FLIGHT).append(proExceptionVO.getFlightCarrierIdentifier()).append(proExceptionVO.getFlightNumber()).append(proExceptionVO.getFlightSequenceNumber()).toString();
					toBeProratedFlightsMap.put(key, flightValidationVO);

			}
			
			
			if(toBeProratedFlightsMap.size()==0 && !prorationExceptionVOs.isEmpty()){
				ErrorVO errorVO = new ErrorVO(ERR_NORECORDS);
				errors.add(errorVO);
				
			}
			
			if(errors.isEmpty()){
				
					for(FlightValidationVO fltValidationVO:toBeProratedFlightsMap.values()){
						toBeProratedFlightVOs.add(fltValidationVO);
					}
					try {

						mailTrackingMRADelegate.prorateExceptionFlights(toBeProratedFlightVOs);
					} catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);

					}
			}		
					
				}
					if (errors != null && errors.size() > 0) {
						invocationContext.addAllError(errors);
						invocationContext.target = FAILURE;
						return;
					}
				
			 
		
		invocationContext.target = SUCCESS;
		log.exiting(CLASS_NAME, "execute");
}

}
