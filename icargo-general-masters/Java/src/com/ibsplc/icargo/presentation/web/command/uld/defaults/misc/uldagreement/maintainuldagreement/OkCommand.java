/*
 * OkCommand.java Created onFeb 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldagreement.maintainuldagreement;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainULDAgreementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AddULDAgreementForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

/**
 *
 * @author A-2046
 *
 */
public class OkCommand extends AddAgreementDetailsCommand {

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String SCREENLOAD_FAILURE = "screenload_failure";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String BLANK = "";

	private static final String SCREEN_ID = "uld.defaults.maintainuldagreement";

	private static final String MODIFY = "screen_mode_modify";

	private static final String CREATE = "screen_mode_create";

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
     */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Log log = LogFactory.getLogger("ULD_MANAGEMENT");
		log.entering("OkCommand", "-------uldmnagement");
		Collection<ErrorVO> errors = null;
		MaintainULDAgreementSession session = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		ArrayList<ULDAgreementDetailsVO> detailsVOs = null;
		log.log(Log.FINE, "values retrieved from session"
				+ session.getUldAgreementVOs());
		log.log(Log.FINE, "main collection"
				+ session.getUldAgreementDetails().getUldAgreementDetailVOs());
		if (session.getUldAgreementVOs() != null) {
			detailsVOs = new ArrayList<ULDAgreementDetailsVO>(session
					.getUldAgreementVOs());
		} else {
			log.log(Log.FINE, "inside new arraylist-------------");
			detailsVOs = new ArrayList<ULDAgreementDetailsVO>();

		}
		AddULDAgreementForm form = (AddULDAgreementForm) invocationContext.screenModel;
		//LocalDate startDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
		//LocalDate endDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
		errors = validateCreatedVO(form);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			int displayPageNum = Integer.parseInt(form.getDisplayPage());
			log.log(Log.FINE, "\n\n\n**DisplayPage val-->" + displayPageNum);
			form.setDisplayPage(form.getCurrentPage());
			log.log(Log.FINE, "\n\n\n**Current Page val-->"
					+ form.getCurrentPage());
			form.setCanClose("cannotClose");
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		log
				.log(Log.FINE, "\n\n\n**Current Page val-->"
						+ form.getCurrentPage());
		/*
		 * GET THE CURRENT CHILD VO
		 *
		 */
		ULDAgreementDetailsVO currentULDAgreementDetailsVO = detailsVOs
				.get(Integer.parseInt(form.getCurrentPage()) - 1);
		/*
		 * GET THE MAIN VO FROM SESSION
		 */
		ULDAgreementVO agreementVO = session.getUldAgreementDetails();
		//Added by A-8445
		Page<ULDAgreementDetailsVO> pageULDAgreementDetailsVO = session.getUldAgreementPageDetails();
		/*
		 * CREATE VALIDATION VOS FROM MAIN VO IN SESSIO FOR DATE RANGE
		 * VALIDATIONS
		 */
		Collection<ULDAgreementDetailsVO> validationVOs = new ArrayList<ULDAgreementDetailsVO>();
		if (session.getUldAgreementDetails().getUldAgreementDetailVOs() != null) {
			validationVOs = (ArrayList<ULDAgreementDetailsVO>) ((ArrayList<ULDAgreementDetailsVO>) session
					.getUldAgreementDetails().getUldAgreementDetailVOs())
					.clone();
		}
		if (CREATE.equals(form.getActionStatus())) {
			if (validationVOs != null && validationVOs.size() > 0) {
				log.log(Log.FINE,
						"----------create mode---- validationVOs != null-----");
				validationVOs.addAll((ArrayList<ULDAgreementDetailsVO>) detailsVOs.clone());
			} else {
				log.log(Log.FINE,
						"----------create mode  validationVOs =null---------");
				validationVOs = new ArrayList<ULDAgreementDetailsVO>();
				validationVOs.addAll((ArrayList<ULDAgreementDetailsVO>) detailsVOs.clone());
			}
			validationVOs.remove(currentULDAgreementDetailsVO);
		}
		log.log(Log.FINE, "-validationVOs before validate method--!!!!!!!!!!!"
				+ validationVOs);
		boolean isValid = true; 
		if (CREATE.equals(form.getActionStatus())) {
			isValid = isDetailsDateValid(validationVOs, form);
		}
		if (MODIFY.equals(form.getActionStatus())) {
			isValid = isModifyDetailsDateValid(validationVOs, form);
		}
		if (isValid) {
			if (isEndDatesValid(form)) {
				log.log(Log.FINE,"form.getStation()"+form.getStation());
				if(!BLANK.equals(form.getStation())){
				errors = validateAirportCodes(form.getStation().toUpperCase(),
						logonAttributes);
				if (errors != null && errors.size() > 0) {
					int displayPageNum = Integer
							.parseInt(form.getDisplayPage());
					log.log(Log.FINE, "\n\n\n**DisplayPage val-->"
							+ displayPageNum);
					form.setDisplayPage(form.getCurrentPage());
					form.setCanClose("cannotclose");
					invocationContext.addAllError(errors);
					invocationContext.target = SCREENLOAD_FAILURE;
					return;
				}
				}
				errors = validateUldType(form.getUldType().toUpperCase(),
						logonAttributes);
				if (errors != null && errors.size() > 0) {
					int displayPageNum = Integer
							.parseInt(form.getDisplayPage());
					log.log(Log.FINE, "\n\n\n**DisplayPage val-->"
							+ displayPageNum);
					form.setDisplayPage(form.getCurrentPage());
					invocationContext.addAllError(errors);
					invocationContext.target = SCREENLOAD_FAILURE;
					return;
				}
				
				if(form.getCurrencyCode()!=null&&form.getCurrencyCode().trim().length()!=0){
					log.log(Log.INFO, "currency code"+form.getCurrencyCode());
					errors=validateCurrency(getApplicationSession().getLogonVO().getCompanyCode(),
							form);
					if (errors != null && errors.size() > 0) {
						log.log(Log.FINE, "exception");
						int displayPageNum = Integer.parseInt(form.getDisplayPage());
						log
								.log(Log.FINE, "\n\n\n**DisplayPage val-->"
										+ displayPageNum);
						form.setDisplayPage(form.getCurrentPage());
						invocationContext.addAllError(errors);
						invocationContext.target = SCREENLOAD_FAILURE;
						return;
					}
					
				}else{
					
					ErrorVO errorVO = new ErrorVO("shared.currency.invalidcurrency");
					
					if(errors==null){
						errors = new ArrayList<ErrorVO>();
					}
					errors.add(errorVO);
					
					invocationContext.addAllError(errors);
					invocationContext.target = SCREENLOAD_FAILURE;
					return;
				}
				
				/*
				 * CHECK FOR MODIFICATIONS FOR SETTING THE TRANSACTION FLAG FOR
				 * MAIN VO
				 *
				 */

				SelectNextAgreementDetailCommand command = new SelectNextAgreementDetailCommand();
				if (command.setTransactionFlag(currentULDAgreementDetailsVO,
						form)) {
					log.log(Log.FINE, "\n\n\n\nsetting the transaction flag");
					session.getUldAgreementDetails().setValidateFlag(true);
				}
				updateULDAgreementDetailsVO(currentULDAgreementDetailsVO, form,
						applicationSession);
				if (!AbstractVO.OPERATION_FLAG_INSERT.equals(agreementVO
						.getOperationFlag())) {
					agreementVO
							.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
				}
				Collection<ULDAgreementDetailsVO> detailsVOsInSession = session
						.getUldAgreementDetails().getUldAgreementDetailVOs();
				log.log(Log.FINE, "main collection--------------->"
						+ detailsVOsInSession);
				if (MODIFY.equals(form.getActionStatus())) {
					for (ULDAgreementDetailsVO popupVO : detailsVOs) {
						boolean isFound = false;
						log.log(Log.FINE, "inside modify------------- ");
						for (ULDAgreementDetailsVO detailsVOInSession : detailsVOsInSession) {
							if (popupVO.getSequenceNumber() == detailsVOInSession
									.getSequenceNumber()) {
								if (!BLANK.equals(popupVO.getUldTypeCode())) {
									detailsVOInSession.setUldTypeCode(popupVO
											.getUldTypeCode().toUpperCase());
								}								
								if(popupVO.getStation() != null){
								if (!BLANK.equals(popupVO.getStation())) {
									detailsVOInSession.setStation(popupVO
											.getStation().toUpperCase());
								}
								}
								detailsVOInSession.setRemark(popupVO
										.getRemark());
								detailsVOInSession.setFreeLoanPeriod(popupVO
										.getFreeLoanPeriod());
								detailsVOInSession
										.setDemurrageFrequency(popupVO
												.getDemurrageFrequency());
								detailsVOInSession.setDemurrageRate(popupVO
										.getDemurrageRate());
								detailsVOInSession.setCurrency(popupVO
										.getCurrency());
								detailsVOInSession.setTax(popupVO.getTax());
								if (popupVO.getAgreementToDate() != null) {
									detailsVOInSession
											.setAgreementToDate(popupVO
													.getAgreementToDate());
								}
								detailsVOInSession.setAgreementFromDate(popupVO
										.getAgreementFromDate());
								detailsVOInSession.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
								isFound = true;
							}
						}
						if (!isFound) {
							log.log(Log.FINE, "adding new vo-------");
							detailsVOsInSession.add(popupVO);
						}
					}
					//Added by A-8445
				} else if (CREATE.equals(form.getActionStatus())) {
					log.log(Log.FINE, "----------create mode---------");
					if (detailsVOsInSession != null) {
						log
								.log(Log.FINE,
										"----------adding to existing collection---------");
						log.log(Log.FINE,
								"----------collection to add---------"
										+ detailsVOs);
						log.log(Log.FINE,
								"----------original collection---------"
										+ detailsVOsInSession);
						detailsVOsInSession.addAll(detailsVOs);
						agreementVO
								.setUldAgreementDetailVOs(detailsVOsInSession);
						//Added by A-8445
						if(pageULDAgreementDetailsVO==null){
							Page<ULDAgreementDetailsVO> pageULDAgreementDetailsVO1 = new Page<ULDAgreementDetailsVO>(detailsVOs, 0, 0, 0, 0, 0, false);
							session.setUldAgreementPageDetails(pageULDAgreementDetailsVO1);
						} else {
							pageULDAgreementDetailsVO.addAll(detailsVOs);
							session.setUldAgreementPageDetails(pageULDAgreementDetailsVO);
						}
					} else {
						log.log(Log.FINE,
								"----------setting new collection---------");
						session.getUldAgreementDetails()
								.setUldAgreementDetailVOs(detailsVOs);
						//Added by A-8445
						if(session.getUldAgreementPageDetails()!=null){
							session.getUldAgreementPageDetails().addAll(detailsVOs);
						} else {
							if(pageULDAgreementDetailsVO==null){
								Page<ULDAgreementDetailsVO> pageULDAgreementDetailsVO1 = new Page<ULDAgreementDetailsVO>(detailsVOs, 0, 0, 0, 0, 0, false);
								session.setUldAgreementPageDetails(pageULDAgreementDetailsVO1);
								
							} else {
								pageULDAgreementDetailsVO.addAll(detailsVOs);
								session.setUldAgreementPageDetails(pageULDAgreementDetailsVO);
							}
							
						}
					}
				}
			} else {
				form.setCanClose("cannotclose");
				ErrorVO errorVO = new ErrorVO("uld.defaults.invalidenddates");
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;

			}

		} else {
			log.log(Log.FINE, "exception");
			int displayPageNum = Integer.parseInt(form.getDisplayPage());
			log.log(Log.FINE, "\n\n\n**DisplayPage val-->" + displayPageNum);
			form.setDisplayPage(form.getCurrentPage());
			ErrorVO error = new ErrorVO("uld.defaults.agreementexists");
			invocationContext.addError(error);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		log.log(Log.FINE, "MainVO------------------>" + agreementVO);
		form.setCanClose("close");
		invocationContext.target = SCREENLOAD_SUCCESS;

	}

	/**
	 *
	 * @param form
	 * @return
	 */
	private boolean isEndDatesValid(AddULDAgreementForm form) {
		if (DateUtilities
				.isValidDate(form.getValidFrom(), CALENDAR_DATE_FORMAT)
				&& DateUtilities.isValidDate(form.getValidTo(),
						CALENDAR_DATE_FORMAT)) {
			LocalDate startingDate = new LocalDate(getApplicationSession()
					.getLogonVO().getAirportCode(),Location.ARP, false);
			startingDate.setDate(form.getValidFrom());
			LocalDate endingDate = new LocalDate(getApplicationSession()
					.getLogonVO().getAirportCode(),Location.ARP, false);
			endingDate.setDate(form.getValidTo());
			if (startingDate.isGreaterThan(endingDate)) {
				return false;
			}
		}
		return true;
	}

	/**
	 *
	 * @param detailVOs
	 * @param form
	 * @return
	 */
	private boolean isModifyDetailsDateValid(
			Collection<ULDAgreementDetailsVO> detailVOs,
			AddULDAgreementForm form) {

		LocalDate startingDate = new LocalDate(getApplicationSession()
				.getLogonVO().getAirportCode(),Location.ARP, false);
		if (DateUtilities
				.isValidDate(form.getValidFrom(), CALENDAR_DATE_FORMAT)) {
			startingDate.setDate(form.getValidFrom());
		}
		LocalDate endingDate = new LocalDate(getApplicationSession()
				.getLogonVO().getAirportCode(),Location.ARP, false);
		if (DateUtilities.isValidDate(form.getValidTo(), CALENDAR_DATE_FORMAT)) {
			endingDate.setDate(form.getValidTo());
		} else {
			endingDate = null;
		}

		for (ULDAgreementDetailsVO detailsVO : detailVOs) {

			if (!AbstractVO.OPERATION_FLAG_DELETE.equals(detailsVO
					.getOperationFlag())) {

				if ((form.getStation().toUpperCase().equals(
						detailsVO.getStation())|| (form.getStation().isEmpty() && detailsVO.getStation() == null ))
						&& detailsVO.getUldTypeCode().equals(
								form.getUldType().toUpperCase())) { 
				/*if (detailsVO.getUldTypeCode().equals(
								form.getUldType().toUpperCase())) {*/
					if (detailsVO.getSequenceNumber() == form
							.getSequenceNumber()) {
						continue;
					}
					if (detailsVO.getAgreementToDate() == null
							&& endingDate == null) {
						return false;
					}
					if (detailsVO.getAgreementToDate() != null
							&& endingDate != null) {
						if (detailsVO.getAgreementFromDate().equals(
								startingDate)
								|| detailsVO.getAgreementToDate().equals(
										endingDate)
								|| detailsVO.getAgreementFromDate().equals(
										endingDate)
								|| detailsVO.getAgreementToDate().equals(
										startingDate)) {
							return false;
						}
						if ((detailsVO.getAgreementToDate().isGreaterThan(
								startingDate) && detailsVO.getAgreementToDate()
								.isLesserThan(endingDate))) {
							return false;
						}
						if (detailsVO.getAgreementFromDate().isGreaterThan(
								startingDate)
								&& detailsVO.getAgreementFromDate()
										.isLesserThan(endingDate)) {
							return false;
						}
						if (startingDate.isGreaterThan(detailsVO
								.getAgreementFromDate())
								&& startingDate.isLesserThan(detailsVO
										.getAgreementToDate())) {
							return false;
						}
						if (endingDate.isGreaterThan(detailsVO
										.getAgreementFromDate())
								&& endingDate.isLesserThan(detailsVO
										.getAgreementToDate())) {
							return false;
						} else if (endingDate == null
								&& startingDate.isLesserThan(detailsVO
									.getAgreementToDate())) {
								return false;
						}

					} else if (endingDate == null
							&& detailsVO.getAgreementToDate() != null) {
						if (startingDate.isLesserThan(detailsVO
								.getAgreementToDate())) {
							return false;
						} else if(startingDate.equals(detailsVO
								.getAgreementToDate())) {
							return false; //Added by A-5265 for ICRD-52232
						}
					} else if (endingDate != null
							&& detailsVO.getAgreementToDate() == null) {
						if (endingDate.isGreaterThan(detailsVO
								.getAgreementFromDate())) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Checks if is details date valid.
	 *
	 * @param detailVOs the detail v os
	 * @param form the form
	 * @return true, if is details date valid
	 */
	private boolean isDetailsDateValid(
			Collection<ULDAgreementDetailsVO> detailVOs,
			AddULDAgreementForm form) {

		LocalDate startingDate = new LocalDate(getApplicationSession()
				.getLogonVO().getAirportCode(),Location.ARP, false);
		if (DateUtilities
				.isValidDate(form.getValidFrom(), CALENDAR_DATE_FORMAT)) {
			startingDate.setDate(form.getValidFrom());
		}
		LocalDate endingDate = new LocalDate(getApplicationSession()
				.getLogonVO().getAirportCode(),Location.ARP, false);
		if (DateUtilities.isValidDate(form.getValidTo(), CALENDAR_DATE_FORMAT)) {
			endingDate.setDate(form.getValidTo());
		} else {
			endingDate = null;
		}

		for (ULDAgreementDetailsVO detailsVO : detailVOs) {

			if (!AbstractVO.OPERATION_FLAG_DELETE.equals(detailsVO
					.getOperationFlag())) {
				if ((form.getStation().toUpperCase().equals(
						detailsVO.getStation())|| (form.getStation().isEmpty() && detailsVO.getStation() == null ))
						&& detailsVO.getUldTypeCode().equals(
								form.getUldType().toUpperCase())) {
				/*if (detailsVO.getUldTypeCode().equals(
								form.getUldType().toUpperCase())) {*/

					if (detailsVO.getAgreementToDate() == null
							&& endingDate == null) {
						return false;
					}
					if (detailsVO.getAgreementToDate() != null
							&& endingDate != null) {
						if (detailsVO.getAgreementFromDate().equals(
								startingDate)
								|| detailsVO.getAgreementToDate().equals(
										endingDate)
								|| detailsVO.getAgreementFromDate().equals(
										endingDate)
								|| detailsVO.getAgreementToDate().equals(
										startingDate)) {
							return false;
						}
						if ((detailsVO.getAgreementToDate().isGreaterThan(
								startingDate) && detailsVO.getAgreementToDate()
								.isLesserThan(endingDate))) {
							return false;
						}
						if (detailsVO.getAgreementFromDate().isGreaterThan(
								startingDate)
								&& detailsVO.getAgreementFromDate()
										.isLesserThan(endingDate)) {
							return false;
						}
						if (startingDate.isGreaterThan(detailsVO
								.getAgreementFromDate())
								&& startingDate.isLesserThan(detailsVO
										.getAgreementToDate())) {
							return false;
						}
						if (endingDate.isGreaterThan(detailsVO
										.getAgreementFromDate())
								&& endingDate.isLesserThan(detailsVO
										.getAgreementToDate())) {
							return false;
						} else if (endingDate == null
								&& startingDate.isLesserThan(detailsVO
									.getAgreementToDate())) {
								return false;
							}

					} else if (endingDate == null
							&& detailsVO.getAgreementToDate() != null) {
						if (startingDate.isLesserThan(detailsVO
								.getAgreementToDate())) {
							return false;//Added by A-5237 for ICRD-48368
						} else if(startingDate.equals(detailsVO
								.getAgreementToDate())) {
							return false; //Added by A-5265 for ICRD-52232
						}
					} else if (endingDate != null
							&& detailsVO.getAgreementToDate() == null) {
						if (endingDate.isGreaterThan(detailsVO
								.getAgreementFromDate())) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

}
