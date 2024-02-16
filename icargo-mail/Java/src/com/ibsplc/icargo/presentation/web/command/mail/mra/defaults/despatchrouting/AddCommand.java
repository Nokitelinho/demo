/*
 *
 * AddCommand.java Created on JUL 21, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchrouting;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.cra.defaults.vo.CRAAWBRoutingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNRoutingSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchRoutingForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-3429
 * 
 * 
 * 
 */
/**
 * Adds a row to table
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 JUL 17, 2009 A-3429 Initial draft 0.2 JUL 17, 2009 A-3429 Second draft
 */

public class AddCommand extends BaseCommand {

	private static final String CLASS_NAME = "AddCommand";

	/*
	 * The module name
	 */

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/*
	 * The screen id
	 */

	private static final String SCREENID = "mailtracking.mra.defaults.despatchrouting";

	/**
	 * Target Action
	 */

	private static final String ADDDETAILS_SUCCESS = "adddetails_success";

	private static final String ADDDETAILS_FAILURE = "adddetails_failure";

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	private static final String BLANK = "";

	/**
	 * String constant
	 */
	private static final String ERROR_KEY_NOAWB_SELECTED = "cra.defaults.awbroutingdetails.noawblisted";

	/**
	 * 
	 * execute method
	 * 
	 * @param invocationContext
	 * 
	 * @throws CommandInvocationException
	 * 
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");

		int index = 0;
		/**
		 * Obtaining form
		 */
		DespatchRoutingForm despatchRoutingForm = (DespatchRoutingForm) invocationContext.screenModel;
		/**
		 * Obtaining session
		 */
		DSNRoutingSession dsnRoutingSession = (DSNRoutingSession) getScreenSession(
				MODULE_NAME, SCREENID);
		Collection<DSNRoutingVO> newDSNRoutingVOs = new ArrayList<DSNRoutingVO>();
		Collection<DSNRoutingVO> dsnRoutingVOs = dsnRoutingSession
				.getDSNRoutingVOs();

		/** if no collection of VO create new one and one empty VO to it */

		if (dsnRoutingVOs != null && dsnRoutingVOs.size() > 0) {

			/** getting values from form */
			
			String[] hiddenOpFlag = despatchRoutingForm.getHiddenOpFlag();

			String[] carrierCode = despatchRoutingForm.getFlightCarrierCode();
			String[] flightNumber = despatchRoutingForm.getFlightNumber();
			String[] departDate = despatchRoutingForm.getDepartureDate();
			String[] pol = despatchRoutingForm.getPol();
			String[] pou = despatchRoutingForm.getPou();
			String[] nopieces = despatchRoutingForm.getNopieces();
			String[] weight = despatchRoutingForm.getWeight();
			String[] rowId = despatchRoutingForm.getCheckBoxForFlight();
			String[] source = despatchRoutingForm.getSource();
			// log.entering(CLASS_NAME, "\n\n in if loop
			// 1\n\n"+segmentOriginCode);

			log.log(Log.FINE, "dsnRoutingVOsin add command==>>:\n\n ",
					dsnRoutingVOs);
			for (DSNRoutingVO dSNRoutingVO : dsnRoutingVOs) {

				// craAWBRoutingDetailsVO.setCompanyCode(companyCode);
				if (!"D".equalsIgnoreCase(hiddenOpFlag[index])) {

					if (!carrierCode[index].equals(BLANK)) {
						dSNRoutingVO.setFlightCarrierCode(carrierCode[index]);
					} else {
						dSNRoutingVO.setFlightCarrierCode(BLANK);
					}

					if (!flightNumber[index].equals(BLANK)) {
						dSNRoutingVO.setFlightNumber(flightNumber[index]);
					} else {
						dSNRoutingVO.setFlightNumber(BLANK);
					}

					if (!departDate[index].equals(BLANK)) {
						dSNRoutingVO.setDepartureDate(new LocalDate(
								LocalDate.NO_STATION, Location.NONE, false)
								.setDate(departDate[index]));
					}

					if (!pol[index].equals(BLANK)) {
						dSNRoutingVO.setPol(pol[index]);
					} else {
						dSNRoutingVO.setPol(BLANK);
					}

					if (!pou[index].equals(BLANK)) {
						dSNRoutingVO.setPou(pou[index]);
					} else {
						dSNRoutingVO.setPou(BLANK);
					}

					if (!nopieces[index].equals(BLANK)) {
						dSNRoutingVO.setNopieces(Integer
								.parseInt(nopieces[index]));
					} else {
						dSNRoutingVO.setNopieces(0);
					}

					if (!weight[index].equals(BLANK)) {
						dSNRoutingVO.setWeight(Double
								.parseDouble(weight[index]));
					} else {
						dSNRoutingVO.setWeight(0);
					}
					dSNRoutingVO.setSource("MN");
					if ("I".equalsIgnoreCase(hiddenOpFlag[index])) {
						dSNRoutingVO.setAcctualnopieces(Integer
								.parseInt(despatchRoutingForm.getOalPcs()));
						dSNRoutingVO.setAcctualweight(Double
								.parseDouble(despatchRoutingForm.getOalwgt()));
					}
					}
				++index;
			}

			log.log(Log.FINE, "dsnRoutingVOs==>>:\n\n ", dsnRoutingVOs);
			/** Adding an row to selected row of collection */
			if (rowId != null && rowId.length == 1) {

				int rowCount = Integer.parseInt(rowId[0]);
				index = 0;
				// log.log(Log.FINE, "\n\nSelected row is: " + rowCount );

				for (DSNRoutingVO dSNRoutingVO : dsnRoutingVOs) {

					if (index == (rowCount + 1)) {
						log.log(Log.FINE, "\n\nSelected row inserted: ",
								rowCount);
						newDSNRoutingVOs.add(createEmptyDetailsVO(
								despatchRoutingForm, dsnRoutingVOs));
					}
					newDSNRoutingVOs.add(dSNRoutingVO);
					++index;
				}
				/** for adding row as last row */
				if (rowCount == dsnRoutingVOs.size() - 1) {
					newDSNRoutingVOs.add(createEmptyDetailsVO(
							despatchRoutingForm, dsnRoutingVOs));
				}
			} else if (rowId == null && dsnRoutingVOs != null) {
				for (DSNRoutingVO dSNRoutingVO : dsnRoutingVOs) {
					newDSNRoutingVOs.add(dSNRoutingVO);
				}
				newDSNRoutingVOs.add(createEmptyDetailsVO(despatchRoutingForm,
						dsnRoutingVOs));
			}
			log.log(Log.FINE, "newDSNRoutingVOs==>>:\n\n ", newDSNRoutingVOs);
			dsnRoutingSession.setDSNRoutingVOs(newDSNRoutingVOs);
			invocationContext.target = ADDDETAILS_SUCCESS; // sets target

		} else {
			newDSNRoutingVOs.add(createEmptyDetailsVO(despatchRoutingForm,
					dsnRoutingVOs));
			dsnRoutingSession.setDSNRoutingVOs(newDSNRoutingVOs);
			invocationContext.target = ADDDETAILS_SUCCESS; // sets target

		}

		log.exiting(CLASS_NAME, "execute");
		return;
	}

	/**
	 * creates an emplty CRAAWBRoutingDetailsVO
	 * 
	 * @param craAWBRoutingVO
	 * @return
	 */
	private DSNRoutingVO createEmptyDetailsVO(
			DespatchRoutingForm despatchRoutingForm,
			Collection<DSNRoutingVO> dsnRoutingVOs) {
		int serialNumber = 1;
		long mailseqnum = 0;
		String csgdocnum = "";
		String blgbas = "";
		String cmpcod = "";
		String poacod = "";
		for (DSNRoutingVO proVO : dsnRoutingVOs) {
			cmpcod = proVO.getCompanyCode();
			//csgseqnum = proVO.getCsgSequenceNumber();
			//csgdocnum = proVO.getCsgDocumentNumber();
			//blgbas = proVO.getBillingBasis();
			//poacod = proVO.getPoaCode();
			serialNumber = serialNumber + 1;
			mailseqnum=proVO.getMailSequenceNumber();
		}
		log.entering(CLASS_NAME, "createEmptyDetailsVO");
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		DSNRoutingVO dSNRoutingVO = new DSNRoutingVO();
		dSNRoutingVO.setCompanyCode(logonAttributes.getCompanyCode());
		dSNRoutingVO.setFlightCarrierCode(BLANK);
		dSNRoutingVO.setFlightNumber(BLANK);
		dSNRoutingVO.setDepartureDate(null);
		dSNRoutingVO.setPol(BLANK);
		dSNRoutingVO.setPou(BLANK);
		dSNRoutingVO.setNopieces(0);
		dSNRoutingVO.setWeight(0);
		dSNRoutingVO.setMailSequenceNumber(mailseqnum);
		dSNRoutingVO.setSource("MN");
		//dSNRoutingVO.setBillingBasis(blgbas);
		//dSNRoutingVO.setCsgDocumentNumber(csgdocnum);
		//dSNRoutingVO.setCsgSequenceNumber(csgseqnum);
		//dSNRoutingVO.setPoaCode(poacod);
		//dSNRoutingVO.setRoutingSerialNumber(serialNumber);
		dSNRoutingVO.setOwnairlinecode(logonAttributes.getOwnAirlineCode());
		dSNRoutingVO.setLegsernum(1);
		dSNRoutingVO
				.setOperationFlag(CRAAWBRoutingDetailsVO.OPERATION_FLAG_INSERT);
		log.exiting(CLASS_NAME, "createEmptyDetailsVO");
		return dSNRoutingVO;
	}
}
