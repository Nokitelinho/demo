/*
 * AddAgreementDetailsCommand.java Created on Dec 19, 2005
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
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSession;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainULDAgreementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AddULDAgreementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
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
public class AddAgreementDetailsCommand extends BaseCommand {
	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.maintainuldagreement";

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String SCREENLOAD_FAILURE = "screenload_failure";

	private static final String BLANK = "";
	
	private Log log = LogFactory.getLogger("ULD");

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		//Log log = LogFactory.getLogger("ULD_AGREEMENT");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MaintainULDAgreementSession session = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		AddULDAgreementForm form = (AddULDAgreementForm) invocationContext.screenModel;
		ArrayList<ULDAgreementDetailsVO> detailsVOs = session
				.getUldAgreementVOs() != null
				&& session.getUldAgreementVOs().size() > 0 ? new ArrayList<ULDAgreementDetailsVO>(
				session.getUldAgreementVOs())
				: new ArrayList<ULDAgreementDetailsVO>();

		if (detailsVOs.size() > 0) {
			/*
			 * The currently displayed VO
			 */
			ULDAgreementDetailsVO currentULDAgreementDetailsVO = detailsVOs
					.get(Integer.parseInt(form.getCurrentPage()) - 1);

			/*
			 * update the currently selected VO with the values from the form
			 */
			updateULDAgreementDetailsVO(currentULDAgreementDetailsVO, form,
					applicationSession);
			log.log(Log.FINE, "currentULDAgreementDetailsVO ---> ",
					currentULDAgreementDetailsVO);
			/*
			 * validate
			 */
			Collection<ErrorVO> errors = null;
			errors = validateCreatedVO(form);

			if (errors != null && errors.size() > 0) {
				log.log(Log.FINE, "exception");
				int displayPageNum = Integer.parseInt(form.getDisplayPage());
				log.log(Log.FINE, "\n\n\n**DisplayPage val-->", displayPageNum);
				form.setDisplayPage(form.getCurrentPage());
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}
			/*
			 * validate with existing collection
			 */
			ArrayList<ULDAgreementDetailsVO> validationVOs = new ArrayList<ULDAgreementDetailsVO>();
			validationVOs = (ArrayList<ULDAgreementDetailsVO>) detailsVOs.clone();
			if (session.getUldAgreementDetails().getUldAgreementDetailVOs() != null) {
				validationVOs.addAll(session.getUldAgreementDetails()
						.getUldAgreementDetailVOs());
			}
			validationVOs.remove(currentULDAgreementDetailsVO);
			//log.log(Log.INFO,"%%%%%%%%%%%%validationVOs"+validationVOs);
			
			ErrorVO error = validateDetailsDate(validationVOs, form);
			if (error != null) {
				log.log(Log.FINE, "exception");
				int displayPageNum = Integer.parseInt(form.getDisplayPage());
				log.log(Log.FINE, "\n\n\n**DisplayPage val-->", displayPageNum);
				form.setDisplayPage(form.getCurrentPage());
				invocationContext.addError(error);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}

			errors = validateAirportCodes(form.getStation().toUpperCase(),
					logonAttributes);
			if (errors != null && errors.size() > 0) {
				log.log(Log.FINE, "exception");
				int displayPageNum = Integer.parseInt(form.getDisplayPage());
				log.log(Log.FINE, "\n\n\n**DisplayPage val-->", displayPageNum);
				form.setDisplayPage(form.getCurrentPage());
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}

			errors = validateUldType(form.getUldType().toUpperCase(),
					logonAttributes);
			if (errors != null && errors.size() > 0) {
				log.log(Log.FINE, "exception");
				int displayPageNum = Integer.parseInt(form.getDisplayPage());
				log.log(Log.FINE, "\n\n\n**DisplayPage val-->", displayPageNum);
				form.setDisplayPage(form.getCurrentPage());
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}
			
			log.log(Log.INFO, "currency code", form.getCurrencyCode());
			if(form.getCurrencyCode()!=null&&form.getCurrencyCode().trim().length()!=0){
				log.log(Log.INFO, "currency code", form.getCurrencyCode());
				errors=validateCurrency(getApplicationSession().getLogonVO().getCompanyCode(),
						form);
				if (errors != null && errors.size() > 0) {
					log.log(Log.FINE, "exception");
					int displayPageNum = Integer.parseInt(form.getDisplayPage());
					log.log(Log.FINE, "\n\n\n**DisplayPage val-->",
							displayPageNum);
					form.setDisplayPage(form.getCurrentPage());
					invocationContext.addAllError(errors);
					invocationContext.target = SCREENLOAD_FAILURE;
					return;
				}
				
			}
		}

		/*
		 * The newly added VO is added to the collection
		 */
		ULDAgreementDetailsVO newULDAgreementDetailsVO = new ULDAgreementDetailsVO();
		newULDAgreementDetailsVO
				.setOperationFlag(ULDAgreementDetailsVO.OPERATION_FLAG_INSERT);

		newULDAgreementDetailsVO
				.setSequenceNumber(populateSequence(detailsVOs));
		detailsVOs.add(newULDAgreementDetailsVO);
		session.setUldAgreementVOs(detailsVOs);
		log.log(Log.FINE, "maincollection------------>", session.getUldAgreementDetails().getUldAgreementDetailVOs());
		/*
		 * populate the form with the new VO
		 */
		populateNewDetails(newULDAgreementDetailsVO, form);

		/*
		 * update the form variables for navigation
		 */
		form.setDisplayPage(String.valueOf(detailsVOs.size()));
		form.setCurrentPage(form.getDisplayPage());
		form.setTotalRecords(String.valueOf(detailsVOs.size()));
		form.setLastPageNumber(form.getTotalRecords());
		AreaDelegate areaDelegate = new AreaDelegate();
    	String defCur = "";
		try {
			defCur = areaDelegate.defaultCurrencyForStation(logonAttributes
					.getCompanyCode(), logonAttributes.getStationCode());
		} catch (BusinessDelegateException e) {
			e.getMessage();
		}
		form.setCurrencyCode(defCur);
		invocationContext.target = SCREENLOAD_SUCCESS;
	}

/**
 * 
 * @param detailsVO
 * @param form
 * @param session
 */
	public void updateULDAgreementDetailsVO(ULDAgreementDetailsVO detailsVO,
			AddULDAgreementForm form, ApplicationSession session) {
		LocalDate detailsFromDate = new LocalDate(session.getLogonVO().getAirportCode(),Location.ARP
				, false);
		
		LocalDate detailsToDate = new LocalDate(session.getLogonVO().getAirportCode(),Location.ARP, false);

		if (!BLANK.equals(form.getUldType().toUpperCase())) {
			detailsVO.setUldTypeCode(form.getUldType().toUpperCase());
		}
			detailsVO.setStation(form.getStation().toUpperCase());
		
		if (DateUtilities
				.isValidDate(form.getValidFrom(), CALENDAR_DATE_FORMAT)) {
			
			
			detailsVO.setAgreementFromDate(detailsFromDate.setDate(form
					.getValidFrom()));
			
		}
		if (DateUtilities.isValidDate(form.getValidTo(), CALENDAR_DATE_FORMAT)) {
			
			detailsVO.setAgreementToDate(detailsToDate.setDate(form
					.getValidTo()));
			
			
			
		} else {
			if (DateUtilities.isValidDate(form.getToDate(),
					CALENDAR_DATE_FORMAT)) {
				detailsVO.setAgreementToDate(detailsToDate.setDate(form
						.getToDate()));
			} else {
				detailsVO.setAgreementToDate(null);
			}
		}
		
		detailsVO.setFreeLoanPeriod(form.getFreeLoanPeriod());
		detailsVO.setDemurrageFrequency(form.getDemurrageFrequency());
		detailsVO.setDemurrageRate(form.getDemurrageRate());
		detailsVO.setRemark(form.getRemarks());
		detailsVO.setCurrency(form.getCurrencyCode());
		detailsVO.setTax(form.getTaxes());

		if (!ULDAgreementDetailsVO.OPERATION_FLAG_INSERT.equals(detailsVO
				.getOperationFlag())) {
			detailsVO
					.setOperationFlag(ULDAgreementDetailsVO.OPERATION_FLAG_UPDATE);
		}
	}

	/**
	 * validate mandatory fields
	 * 
	 * @param agreementDetailsVO
	 * @param form
	 * @return
	 */
	public Collection<ErrorVO> validateCreatedVO(AddULDAgreementForm form) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if (BLANK.equals(form.getUldType())) {
			form.setCanClose("cannotclose");
			ErrorVO errorVO = new ErrorVO("uld.defaults.enteruldcode");
			errors.add(errorVO);
		}
		
		// Added by Preet ofr AirNZ 517--starts
		// Airport is mandaotry for Agreements
		/**if (BLANK.equals(form.getStation())) {
			form.setCanClose("cannotclose");
			ErrorVO errorVO = new ErrorVO("uld.defaults.enterstation");
			errors.add(errorVO);

		}**/
		// Added by Preet ofr AirNZ 517--ends
		if (BLANK.equals(form.getValidFrom())) {
			form.setCanClose("cannotclose");
			ErrorVO errorVO = new ErrorVO("uld.defaults.enterfromdate");
			errors.add(errorVO);
		}

		LocalDate startingDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP, false);
		LocalDate endingDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP, false);
		if (DateUtilities
				.isValidDate(form.getValidFrom(), CALENDAR_DATE_FORMAT)
				&& DateUtilities.isValidDate(form.getValidTo(),
						CALENDAR_DATE_FORMAT)) {

			startingDate.setDate(form.getValidFrom());

			endingDate.setDate(form.getValidTo());
			if (startingDate.isGreaterThan(endingDate)) {
				form.setCanClose("cannotclose");
				ErrorVO errorVO = new ErrorVO("uld.defaults.invalidenddates");
				errors.add(errorVO);
			}
		}

		LocalDate fromDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP, false);
		LocalDate toDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP, false);
		boolean isFlag = true;
		if (DateUtilities.isValidDate(form.getFromDate(), CALENDAR_DATE_FORMAT)) {
			fromDate.setDate(form.getFromDate());
			if (DateUtilities.isValidDate(form.getValidFrom(),
					CALENDAR_DATE_FORMAT)) {
				startingDate.setDate(form.getValidFrom());
				if (startingDate.isGreaterThan(fromDate)
						|| startingDate.equals(fromDate)) {
					isFlag = true;
				} else {
					isFlag = false;
				}

			}
		}

		if (!isFlag) {
			ErrorVO errorVO = new ErrorVO("uld.defaults.invalidfromdate");
			errors.add(errorVO);
			return errors;
		}
		if (DateUtilities.isValidDate(form.getToDate(), CALENDAR_DATE_FORMAT)) {
			toDate.setDate(form.getToDate());
			if (DateUtilities.isValidDate(form.getValidTo(),
					CALENDAR_DATE_FORMAT)) {
				endingDate.setDate(form.getValidTo());
				if (endingDate.isLesserThan(toDate)
						|| endingDate.equals(toDate)) {
					isFlag = true;
				} else {
					isFlag = false;
				}
			}
		}

		if (!isFlag) {
			ErrorVO errorVO = new ErrorVO("uld.defaults.invalidfromdate");
			errors.add(errorVO);
		}

		return errors;
	}

/**
 * 
 * @param newULDAgreementDetailsVO
 * @param form
 */
	public void populateNewDetails(
			ULDAgreementDetailsVO newULDAgreementDetailsVO,
			AddULDAgreementForm form) {
		form.setDemurrageRate(newULDAgreementDetailsVO.getDemurrageRate());
		form.setFreeLoanPeriod(newULDAgreementDetailsVO.getFreeLoanPeriod());
		form.setRemarks(newULDAgreementDetailsVO.getRemark());
		if (newULDAgreementDetailsVO.getAgreementFromDate() != null) {
			form.setValidFrom(newULDAgreementDetailsVO.getAgreementFromDate()
					.toDisplayDateOnlyFormat());
		} else {
			form.setValidFrom(BLANK);
		}
		if (newULDAgreementDetailsVO.getAgreementToDate() != null) {
			form.setValidTo(newULDAgreementDetailsVO.getAgreementToDate()
					.toDisplayDateOnlyFormat());
		} else {
			form.setValidTo(BLANK);
		}
		form.setStation(newULDAgreementDetailsVO.getStation());
		form.setUldType(newULDAgreementDetailsVO.getUldTypeCode());
		form.setTaxes(newULDAgreementDetailsVO.getTax());
		form.setSequenceNumber(newULDAgreementDetailsVO.getSequenceNumber());
		form.setAgreementNumber(newULDAgreementDetailsVO.getAgreementNumber());
        // Added by Preet on 28th Feb for bug 6492 --starts
		form.setCurrencyCode(newULDAgreementDetailsVO.getCurrency());
		form.setDemurrageFrequency(newULDAgreementDetailsVO.getDemurrageFrequency());		
		// Added by Preet on 28th Feb for bug 6492 --ends
	}

	/**
	 * validate child dates
	 * 
	 * @param detailsVOs
	 * @param form
	 * @return
	 */
	public ErrorVO validateDetailsDate(
			Collection<ULDAgreementDetailsVO> detailsVOs,
			AddULDAgreementForm form) {
		
		log.log(Log.INFO, "%%%%%%%%%detailsVOs ", detailsVOs);
		ErrorVO errorVO = null;
		LocalDate startingDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP, false);
		if (DateUtilities
				.isValidDate(form.getValidFrom(), CALENDAR_DATE_FORMAT)) {
			startingDate.setDate(form.getValidFrom());
		}
		LocalDate endingDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP, false);
		if (DateUtilities.isValidDate(form.getValidTo(), CALENDAR_DATE_FORMAT)) {
			endingDate.setDate(form.getValidTo());
		} else {
			endingDate = null;
		}
		if (detailsVOs != null && detailsVOs.size() > 0) {
			for (ULDAgreementDetailsVO detailsVO : detailsVOs) {
				log.log(Log.INFO, "%%%%%%%%%detailsVO ", detailsVO);
				if (!AbstractVO.OPERATION_FLAG_DELETE.equals(detailsVO
						.getOperationFlag())) {
					// Commented by A-2412
					/*if (detailsVO.getStation().equals(
							form.getStation().toUpperCase())
							&& detailsVO.getUldTypeCode().equals(
									form.getUldType().toUpperCase())) {*/
					// Added by A-2412
					if (detailsVO.getUldTypeCode().equals(
									form.getUldType().toUpperCase())) {
					//ends
						if (detailsVO.getAgreementToDate() == null
								&& endingDate == null) {
							return new ErrorVO("uld.defaults.agreementexists");
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
								return new ErrorVO("uld.defaults.agreementexists");
							}
							log.log(Log.INFO, "detailsVO.getAgreementToDate",
									detailsVO.getAgreementToDate());
							log.log(Log.INFO, "startingDate", startingDate);
							log.log(Log.INFO, "%%%%%endingDate", endingDate);
							if ((detailsVO.getAgreementToDate().isGreaterThan(
									startingDate) && detailsVO
									.getAgreementToDate().isLesserThan(
											endingDate))) {
								return new ErrorVO("uld.defaults.agreementexists");

							}
							log.log(Log.INFO, "detailsVO.getAgreementFromDate",
									detailsVO.getAgreementFromDate());
							if (detailsVO.getAgreementFromDate().isGreaterThan(
									startingDate)
									&& detailsVO.getAgreementFromDate()
											.isLesserThan(endingDate)) {
								return new ErrorVO("uld.defaults.agreementexists");

							}
							log.log(Log.INFO, "ok up to here", detailsVO.getAgreementFromDate());
							if (startingDate.isGreaterThan(detailsVO
									.getAgreementFromDate())
									&& startingDate.isLesserThan(detailsVO
											.getAgreementToDate())) {
								return new ErrorVO("uld.defaults.agreementexists");

							}
							if (endingDate != null
									&& endingDate.isGreaterThan(detailsVO
											.getAgreementFromDate())
									&& endingDate.isLesserThan(detailsVO
											.getAgreementToDate())) {
								return new ErrorVO("uld.defaults.agreementexists");

							} else if (endingDate == null) {
								if (startingDate.isLesserThan(detailsVO
										.getAgreementToDate())) {
									return new ErrorVO("uld.defaults.agreementexists");

								}
							}

						} else if (endingDate == null
								&& detailsVO.getAgreementToDate() != null) {
							if (startingDate.isLesserThan(detailsVO
									.getAgreementToDate())) {
								return new ErrorVO("uld.defaults.agreementexists");
							}
						} else if (endingDate != null
								&& detailsVO.getAgreementToDate() == null) {
							return new ErrorVO("uld.defaults.agreementexists");

						}
					}

				}

			}
		}
		return errorVO;
	}

	/**
	 * @param station
	 * @param logonAttributes
	 * @return errors
	 */
	public Collection<ErrorVO> validateAirportCodes(String station,
			LogonAttributes logonAttributes) {

		Collection<ErrorVO> errors = null;

		try {
			AreaDelegate delegate = new AreaDelegate();
			delegate.validateAirportCode(logonAttributes.getCompanyCode(),station);

		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors = handleDelegateException(e);
		}
		return errors;
	}

	/**
	 * 
	 * @param uldType
	 * @param logonAttributes
	 * @return
	 */
	public Collection<ErrorVO> validateUldType(String uldType,
			LogonAttributes logonAttributes) {
		Collection<ErrorVO> errors = null;
		ULDDelegate delegate = new ULDDelegate();
		Collection<String> uldTypes = new ArrayList<String>();
		uldTypes.add(uldType);
		try {
			delegate.validateULDTypeCodes(logonAttributes.getCompanyCode(),
					uldTypes);
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
		return errors;
	}

	
	public Collection<ErrorVO> validateCurrency(
			String companyCode,AddULDAgreementForm form ) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			
			new CurrencyDelegate().validateCurrency(
					companyCode,form.getCurrencyCode().toUpperCase());
									
		} catch (BusinessDelegateException e) {
			errors=	handleDelegateException(e);
		}
		
		return errors;
	} 
	
	/**
	 * 
	 * @param uldAgreementDetailsVOs
	 * @return
	 */
	public int populateSequence(
			Collection<ULDAgreementDetailsVO> uldAgreementDetailsVOs) {
		int resolutionseq = 0;
		for (ULDAgreementDetailsVO detailsVO : uldAgreementDetailsVOs) {
			if (detailsVO.getSequenceNumber() > resolutionseq) {
				resolutionseq = detailsVO.getSequenceNumber();
			}
		}
		return resolutionseq + 1;

	}
}
