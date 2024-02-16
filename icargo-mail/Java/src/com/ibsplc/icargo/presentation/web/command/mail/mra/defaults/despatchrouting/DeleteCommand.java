/*
 * 
 * DeleteCommand.java Created on Oct 13, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchrouting;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNRoutingSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchRoutingForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2521
 * 
 * Deletes selected rows from display table
 */
public class DeleteCommand extends BaseCommand {

	private static final String CLASS_NAME = "DeleteCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.despatchrouting";

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	private static final String BLANK = "";

	/*
	 * 
	 * For setting the Target action. If the system successfully saves the
	 * 
	 * data, then SAVE_SUCCESS target action is set to invocation context
	 * 
	 */

	private static final String DELETEDETAILS_SUCCESS = "deletedetails_success";

	private static final String DELETEDETAILS_FAILURE = "deletedetails_failure";

	private static final String ERROR_KEY_NOROWSELECTED = "mail.mra.defaults.despatchrouting.err.selectrow";

	/**
	 * executes delete function
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");

		ErrorVO error = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<DSNRoutingVO> newCRAAWBRoutingDetailsVOs = new ArrayList<DSNRoutingVO>();
		Collection<DSNRoutingVO> dSNRoutingVOstoBeRemoved = new ArrayList<DSNRoutingVO>();
		Collection<DSNRoutingVO> dSNRoutingVOs = null;
		/** getting values from form */
		DespatchRoutingForm despatchRoutingForm = (DespatchRoutingForm) invocationContext.screenModel;
		DSNRoutingSession dsnRoutingSession = (DSNRoutingSession) getScreenSession(
				MODULE_NAME, SCREENID);
		dSNRoutingVOs = dsnRoutingSession.getDSNRoutingVOs();

		// String companyCode =
		// getApplicationSession().getLogonVO().getCompanyCode();

		String[] rowId = despatchRoutingForm.getCheckBoxForFlight();

		// log.log(Log.FINE, "VO in newCRAAWBRoutingDetailsVOs is:before
		// operation " + craAWBRoutingDetailsVOs );

		/**
		 * if no rows in table is selected creates an error VO with suitable
		 * message if has rows selected , updates VOs, Changes VO's operational
		 * flag of selected rows appropriately and adds it to VO collection
		 */
		if (rowId == null || dSNRoutingVOs == null) {

			log.log(Log.FINE, "No row selected");
			error = new ErrorVO(ERROR_KEY_NOROWSELECTED);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = DELETEDETAILS_FAILURE;
			return;

		} else {

			int index = 0;

			String[] hiddenOpFlag = despatchRoutingForm.getHiddenOpFlag();

			String[] carrierCode = despatchRoutingForm.getFlightCarrierCode();
			String[] flightNumber = despatchRoutingForm.getFlightNumber();
			String[] departDate = despatchRoutingForm.getDepartureDate();
			String[] pol = despatchRoutingForm.getPol();
			String[] pou = despatchRoutingForm.getPou();
			String[] nopieces = despatchRoutingForm.getNopieces();
			String[] weight = despatchRoutingForm.getWeight();
			String[] agreementType = despatchRoutingForm.getAgreementType();
			String[] source = despatchRoutingForm.getSource();


			log.log(Log.FINE, "VO in operationalFlag before delete is:\n\n ",
					hiddenOpFlag.length);
			for (DSNRoutingVO dsnRoutingVO : dSNRoutingVOs) {

				// craAWBRoutingDetailsVO.setCompanyCode(companyCode);
				if (!"D".equalsIgnoreCase(hiddenOpFlag[index])) {

					if (!carrierCode[index].equals(BLANK)) {
						dsnRoutingVO.setFlightCarrierCode(carrierCode[index]);
					} else {
						dsnRoutingVO.setFlightCarrierCode(BLANK);
					}

					if (!flightNumber[index].equals(BLANK)) {
						dsnRoutingVO.setFlightNumber(flightNumber[index]);
					} else {
						dsnRoutingVO.setFlightNumber(BLANK);
					}

					if (!departDate[index].equals(BLANK)) {
						dsnRoutingVO.setDepartureDate(new LocalDate(
								LocalDate.NO_STATION, Location.NONE, false)
								.setDate(departDate[index]));
					}

					if (!pol[index].equals(BLANK)) {
						dsnRoutingVO.setPol(pol[index]);
					} else {
						dsnRoutingVO.setPol(BLANK);
					}

					if (!pou[index].equals(BLANK)) {
						dsnRoutingVO.setPou(pou[index]);
					} else {
						dsnRoutingVO.setPou(BLANK);
					}

					if (!nopieces[index].equals(BLANK)) {
						dsnRoutingVO.setNopieces(Integer
								.parseInt(nopieces[index]));
					} else {
						dsnRoutingVO.setNopieces(0);
					}

					if (!weight[index].equals(BLANK)) {
						dsnRoutingVO.setWeight(Double
								.parseDouble(weight[index]));
					} else {
						dsnRoutingVO.setWeight(0);
					}
					if (!agreementType[index].equals(BLANK)) {//added by A-7371 for ICRD-265301
						dsnRoutingVO.setAgreementType(agreementType[index]);
					} else {
						dsnRoutingVO.setAgreementType(BLANK);
					}
					if(!source[index].equals(BLANK)){
						dsnRoutingVO.setSource(source[index]);
					}else{
						dsnRoutingVO.setSource(BLANK);
					}
					if ("I".equalsIgnoreCase(hiddenOpFlag[index])) {
						dsnRoutingVO.setAcctualnopieces(Integer
								.parseInt(despatchRoutingForm.getOalPcs()));
						dsnRoutingVO.setAcctualweight(Double
								.parseDouble(despatchRoutingForm.getOalwgt()));
					}
					++index;
				}
			}
			log.log(Log.FINE,
					"VO in newCRAAWBRoutingDetailsVOs before delete is:\n\n ",
					newCRAAWBRoutingDetailsVOs);
			/** Changing flag values for selected rows */
			for (String rowCount : rowId) {

				index = 0;
				log.log(Log.FINE, "Selected row is: ", rowCount);
				for (DSNRoutingVO dSNRoutingVo : dSNRoutingVOs) {

					if (index == Integer.valueOf(rowCount)
							&& "I".equals(dSNRoutingVo.getOperationFlag())) {
						dSNRoutingVOstoBeRemoved.add(dSNRoutingVo);

					} else if (index == Integer.parseInt(rowCount)) {
						dSNRoutingVo.setOperationFlag("D");
					}
					++index;
				}
			}
			dSNRoutingVOs.removeAll(dSNRoutingVOstoBeRemoved);
			dsnRoutingSession.setDSNRoutingVOs(dSNRoutingVOs);
			invocationContext.target = DELETEDETAILS_SUCCESS;

		}

		log.exiting(CLASS_NAME, "execute");

	}
}
