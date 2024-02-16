/*
 * AddLoyaltyProgrammeCommand.java Created on Aug 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AttachLoyaltyProgrammeVO;
import com.ibsplc.icargo.framework.session.ApplicationSession;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 * 
 * @author A-2046
 * 
 */
public class AddLoyaltyProgrammeCommand extends BaseCommand {

	private static final String ADD_SUCCESS = "add_success";

	private static final String ADD_FAILURE = "add_failure";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.customerlisting";

	private static final String BLANK = "";

	private Log log = LogFactory.getLogger("customerlisting");

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("customerlisting");
		log.entering("customerlisting", "ADD COMMAND");
		ListCustomerForm form = (ListCustomerForm) invocationContext.screenModel;
		ListCustomerSession session = getScreenSession(MODULENAME, SCREENID);
		ApplicationSession appSession = getApplicationSession();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String[] programme = form.getLoyaltyProgramme();
		String[] prgmFromDate = form.getProgramFromDate();
		String[] prgmToDate = form.getProgramToDate();
		String[] attachFromDate = form.getAttachFromDate();
		String[] attachToDate = form.getAttachToDate();
		ArrayList<AttachLoyaltyProgrammeVO> loyaltyVOs = session
				.getLoyaltyVOs();
		if (loyaltyVOs == null) {
			loyaltyVOs = new ArrayList<AttachLoyaltyProgrammeVO>();
		}
		if (loyaltyVOs != null && loyaltyVOs.size() > 0) {
			int index = 0;
			for (AttachLoyaltyProgrammeVO programVO : loyaltyVOs) {
				if (!OPERATION_FLAG_DELETE.equals(programVO.getOperationFlag())
						&& !OPERATION_FLAG_INSERT.equals(programVO
								.getOperationFlag())) {
					if (!programVO.getFromDate().toDisplayDateOnlyFormat()
							.equalsIgnoreCase(attachFromDate[index])
							|| !programVO.getToDate().toDisplayDateOnlyFormat()
									.equalsIgnoreCase(attachToDate[index])) {
						programVO.setOperationFlag(OPERATION_FLAG_UPDATE);
					}

				}

				programVO.setLoyaltyProgrammeCode(programme[index]);
				if (!BLANK.equals(prgmFromDate[index])) {
					LocalDate pgmFromDate = new LocalDate(getApplicationSession().getLogonVO().getStationCode(),Location.STN, false);
					pgmFromDate.setDate(prgmFromDate[index]);
					programVO.setLoyaltyFromDate(pgmFromDate);
				}
				if (!BLANK.equals(prgmToDate[index])) {
					LocalDate pgmToDate = new LocalDate(getApplicationSession().getLogonVO().getStationCode(),Location.STN, false);
					pgmToDate.setDate(prgmToDate[index]);
					programVO.setLoyaltyToDate(pgmToDate);
				}
				if (!BLANK.equals(attachFromDate[index])) {
					LocalDate attachFrmDate = new LocalDate(getApplicationSession().getLogonVO().getStationCode(),Location.STN, false);
					attachFrmDate.setDate(attachFromDate[index]);
					programVO.setFromDate(attachFrmDate);
				}

				if (!BLANK.equals(attachToDate[index])) {
					LocalDate attachedToDate = new LocalDate(getApplicationSession().getLogonVO().getStationCode(),Location.STN, false);
					attachedToDate.setDate(attachToDate[index]);
					programVO.setToDate(attachedToDate);
				}
				index++;

			}
			/*
			 * ArrayList<AttachLoyaltyProgrammeVO> validationVOs = new
			 * ArrayList<AttachLoyaltyProgrammeVO>(); for
			 * (AttachLoyaltyProgrammeVO prgVO : loyaltyVOs) { if
			 * (!OPERATION_FLAG_DELETE.equals(prgVO.getOperationFlag())) {
			 * validationVOs.add(prgVO); } }
			 */
			
			errors = validateEndDates(loyaltyVOs);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = ADD_FAILURE;
				return;
			}

			errors = validateDateFields(loyaltyVOs);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = ADD_FAILURE;
				return;
			}
			
			errors = validateLoyaltyDates(loyaltyVOs, form,appSession);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = ADD_FAILURE;
				return;
			}
		}
		AttachLoyaltyProgrammeVO newProgramVO = new AttachLoyaltyProgrammeVO();
		newProgramVO.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());
		newProgramVO.setOperationFlag(OPERATION_FLAG_INSERT);
		newProgramVO.setCustomerCode(form.getCustomerCodeChild().toUpperCase());
		newProgramVO.setLoyaltyFromDate(null);
		newProgramVO.setLoyaltyToDate(null);
		newProgramVO.setFromDate(null);
		newProgramVO.setToDate(null);
		newProgramVO.setLoyaltyProgrammeCode(null);
		newProgramVO.setGroupFlag("N");
		loyaltyVOs.add(newProgramVO);
		session.setLoyaltyVOs(loyaltyVOs);
		invocationContext.target = ADD_SUCCESS;

	}

	/**
	 * method for validating if attach dates are insideloyalty program dates
	 * 
	 * @param loyaltyVOs
	 */
	public Collection<ErrorVO> validateDateFields(
			Collection<AttachLoyaltyProgrammeVO> loyaltyVOs) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if (loyaltyVOs != null && loyaltyVOs.size() > 0) {
			for (AttachLoyaltyProgrammeVO loyaltyVO : loyaltyVOs) {
				if (!OPERATION_FLAG_DELETE.equals(loyaltyVO.getOperationFlag())) {
					if (loyaltyVO.getFromDate() != null
							&& loyaltyVO.getToDate() != null) {
						if (loyaltyVO.getFromDate().isGreaterThan(
								loyaltyVO.getLoyaltyToDate())
								|| loyaltyVO.getToDate().isLesserThan(
										loyaltyVO.getLoyaltyFromDate())
								|| loyaltyVO.getToDate().isGreaterThan(
										loyaltyVO.getLoyaltyToDate())
								|| loyaltyVO.getFromDate().isLesserThan(
										loyaltyVO.getLoyaltyFromDate())) {
							error = new ErrorVO(
									"customermanagement.defaults.custlisting.ms.err.daterangeincorrect");
							errors.add(error);
						}
					}
				}
			}
		}
		return errors;

	}

/**
 * 
 * @param loyaltyVOs
 * @param form
 * @param appSession
 * @return
 */
public Collection<ErrorVO> validateLoyaltyDates(
			Collection<AttachLoyaltyProgrammeVO> loyaltyVOs,
			ListCustomerForm form,ApplicationSession appSession) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if (loyaltyVOs != null && loyaltyVOs.size() > 0) {
			String[] code = form.getLoyaltyProgramme();
			String[] attachFrom = form.getAttachFromDate();
			String[] attachTo = form.getAttachToDate();
			String[] opFlag = form.getOperationFlag();
			log.log(Log.FINE, "operation flag---------->", opFlag);
			for (int i = 0; i < code.length; i++) {
				int index = 0;

				for (AttachLoyaltyProgrammeVO programVO : loyaltyVOs) {
					if (index != i) {
						log.log(Log.FINE, "current program vo------>",
								programVO);
						LocalDate fromDateForm = (new LocalDate(getApplicationSession().getLogonVO().getStationCode(),Location.STN, false))
								.setDate(attachFrom[i]);
						LocalDate toDateForm = (new LocalDate(getApplicationSession().getLogonVO().getStationCode(),Location.STN, false))
								.setDate(attachTo[i]);
						log.log(Log.FINE, "fromDateForm------------------>",
								fromDateForm);
						log.log(Log.FINE, "toDateForm-------------->",
								toDateForm);
						log.log(Log.FINE,
								"currrent operation flag--------------->",
								opFlag, i);
						if (!OPERATION_FLAG_DELETE.equals(opFlag[i])) {
							if((programVO.getOperationFlag()!=null &&
						    		!OPERATION_FLAG_DELETE.equals(programVO.getOperationFlag()))    		    					
						    			|| programVO.getOperationFlag()==null){ 
							
							if (code[i].equals(programVO
									.getLoyaltyProgrammeCode())
									&& attachFrom[i].equals(programVO
											.getFromDate().toDisplayFormat(true))//Modified By A-5374
									&& attachTo[i]
											.equals(programVO.getToDate().toDisplayFormat(true))) {//Modified By A-5374
								error = new ErrorVO(
										"customermanagement.defaults.custlisting.msg.err.loyaltyprgmexists");
								errors.add(error);
								return errors;
							}
							log.log(Log.FINE, "loyalty program code------->",
									code, i);
							log.log(Log.FINE, "loyalty code from vo------>",
									programVO.getLoyaltyProgrammeCode());
							log.log(Log.FINE,
									"program vo to date------------------>",
									programVO.getToDate());
							if (code[i].equals(programVO
									.getLoyaltyProgrammeCode())) {
								if (!programVO.getFromDate().isGreaterThan(
										toDateForm)
										&& !programVO.getToDate().isLesserThan(
												fromDateForm)) {
									error = new ErrorVO(
											"customermanagement.defaults.custlisting.msg.err.loyaltyprgmexists");
									errors.add(error);
									return errors;
								}
							}
						}
						}
					}

					index++;
				}
			}
		}
		return errors;
	}
	/**
	 * 
	 * @param loyaltyVOs
	 * @return
	 */
	public Collection<ErrorVO> validateEndDates(
			Collection<AttachLoyaltyProgrammeVO> loyaltyVOs) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if (loyaltyVOs != null && loyaltyVOs.size() > 0) {
			for (AttachLoyaltyProgrammeVO loyaltyVO : loyaltyVOs) {
				if (loyaltyVO.getFromDate()
						.isGreaterThan(loyaltyVO.getToDate())) {
					error = new ErrorVO(
							"customermanagement.defaults.custlisting.msg.err.invalidendadates");
					errors.add(error);
				}
			}
		}
		return errors;
	}
}
