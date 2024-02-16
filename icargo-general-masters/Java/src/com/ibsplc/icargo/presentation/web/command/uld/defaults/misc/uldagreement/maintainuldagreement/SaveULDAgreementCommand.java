/*
 * SaveULDAgreementCommand.java.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldagreement.maintainuldagreement;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.ibase.util.time.TimeConvertor;
import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainULDAgreementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainULDAgreementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-1496
 * 
 */
public class SaveULDAgreementCommand extends BaseCommand {

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.maintainuldagreement";

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String SCREENLOAD_FAILURE = "screenload_failure";

	private static final String SCREENLOAD_LIST = "screenload_list";

	private static final String BLANK_STRING = "";

	private static final String ACTIVE = "A";

	private static final String AGENT = "G";	
	
	private static final String AIRLINE = "A";	

	private static final String DELETED = "D";

	private static final String CANCLEAR = "canClearTrue";
	
   // Added by Preet for AirNZ 517 -starts
	private static final String AIRLINE_ALL = "ALL AIRLINES";
	
	private static final String AGENT_ALL = "ALL AGENTS";
	// Added by Preet for AirNZ 517 -ends

	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("SaveCommand", "ULDManagement");
		MaintainULDAgreementForm form = (MaintainULDAgreementForm) invocationContext.screenModel;
		MaintainULDAgreementSession session = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		ULDAgreementVO agreementVO = session.getUldAgreementDetails();
		//Added by A-8445
		Page<ULDAgreementDetailsVO> pageULDAgreementDetailsVO = session.getUldAgreementPageDetails();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		LocalDate agreementDate = new LocalDate(logonAttributes
				.getAirportCode(), Location.ARP,false);
		LocalDate startDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,
				false);
		LocalDate endDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,
				false);
		errors = validateForm(form);
		if (errors != null && errors.size() > 0) {			
			log.log(log.FINE,"errors not null*******");	
			invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		
		
		
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		AirlineValidationVO validationVO = null;
		AgentDelegate agentDelegate = new AgentDelegate();
		AgentVO agentVO = null;
		String partyCode = "";
	
		if (ACTIVE.equals(form.getPartyType())) {
			if (form.getPartyCode() != null
					&& form.getPartyCode().trim().length() > 0) {
				partyCode = form.getPartyCode().toUpperCase();
			}
			try {
				// Added by Preet on 3 rd Apr for AirNZ 517--starts
				// No validation needed for ALL AIRLINE agreements 
				if(!AIRLINE_ALL.equals(partyCode)){
					validationVO = airlineDelegate.validateAlphaCode(companyCode,
							partyCode);
				}
				// Added by Preet on 3 rd Apr for AirNZ 517--ends
			} catch (BusinessDelegateException exception) {
				errors = handleDelegateException(exception);
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}
		}
		if (AGENT.equals(form.getPartyType())) {
			if (form.getPartyCode() != null
					&& form.getPartyCode().trim().length() > 0) {
				partyCode = form.getPartyCode().toUpperCase();
			}
			Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			try {
				// Added by Preet on 3 rd Apr for AirNZ 517--starts
				// No validation needed for ALL AGENT agreements 
				if(!AGENT_ALL.equals(partyCode)){
					agentVO = agentDelegate
						.findAgentDetails(companyCode, partyCode);
				}
				// Added by Preet on 3 rd Apr for AirNZ 517--ends
			} catch (BusinessDelegateException exception) {
				log.log(Log.FINE, "*****in the exception");
				exception.getMessage();
				error = handleDelegateException(exception);
			}
			if (agentVO == null && (!AGENT_ALL.equals(partyCode))) {
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.maintainuldagreement.invalidagentcode");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
			}
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}
		}
		if(form.getCurrencyCode()!=null&&form.getCurrencyCode().trim().length()!=0){
			errors=validateCurrency(getApplicationSession().getLogonVO().getCompanyCode(),
					form);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}
		}else{
			ErrorVO errorVO = new ErrorVO("shared.currency.invalidcurrency");
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		if (!OPERATION_FLAG_INSERT.equals(agreementVO.getOperationFlag())) {
			if ((hasValueChanged(agreementVO.getAgreementFromDate()
					.toDisplayDateOnlyFormat(), form.getFromDate())
					|| hasToDateChanged(form, agreementVO)
					|| hasValueChanged(agreementVO.getAgreementStatus(), form
							.getAgreementStatus().toUpperCase())
					|| hasAgreementDateChanged(form, agreementVO)
					|| hasValueChanged(agreementVO.getCurrency(), form
							.getCurrencyCode().toUpperCase())
					|| hasValueChanged(agreementVO.getDemurrageFrequency(),
							form.getDemurrageFrequency().toUpperCase())
					|| hasValueChanged(agreementVO.getDemurrageRate(), form
							.getDemurrageRate())
					|| hasValueChanged(agreementVO.getRemark(), form
							.getRemarks())
					|| hasValueChanged(agreementVO.getFreeLoanPeriod(), form
							.getFreeLoanPeriod())
					|| hasValueChanged(agreementVO.getTax(), form.getTaxes())
					|| hasValueChanged(agreementVO.getPartyCode(), form
							.getPartyCode().toUpperCase())
					|| hasValueChanged(agreementVO.getPartyType(), form
							.getPartyType().toUpperCase())
					|| hasValueChanged(agreementVO.getPartyName(), form
							.getPartyName().toUpperCase()) ||
							 hasValueChanged(agreementVO.getFromPartyCode(), form
							.getPartyCode().toUpperCase())
					|| hasValueChanged(agreementVO.getFromPartyType(), form
							.getPartyType().toUpperCase())
					|| hasValueChanged(agreementVO.getFromPartyName(), form
							.getPartyName().toUpperCase()) || hasValueChanged(
					agreementVO.getTxnType(), form.getTransactionType()))) {
				agreementVO.setOperationFlag(OPERATION_FLAG_UPDATE);

				if (ACTIVE.equals(agreementVO.getAgreementStatus())
						&& DELETED.equals(form.getAgreementStatus())) {
					ErrorVO error = new ErrorVO("uld.defaults.cannotdelete");
					errors.add(error);
					invocationContext.addAllError(errors);
					form.setAgreementStatus(ACTIVE);
					invocationContext.target = SCREENLOAD_FAILURE;
					return;
				}
				log.log(Log.FINE, "OPERATION FLAG------->UPDATE");
			}
			if ((hasValueChanged(agreementVO.getAgreementFromDate()
					.toDisplayDateOnlyFormat().toUpperCase(), form.getFromDate())
					|| hasToDateChanged(form, agreementVO)
					|| hasValueChanged(agreementVO.getAgreementStatus(), form
							.getAgreementStatus())
					|| hasAgreementDateChanged(form, agreementVO)
					|| hasValueChanged(agreementVO.getCurrency(), form
							.getCurrencyCode())
					|| hasValueChanged(agreementVO.getDemurrageFrequency(),
							form.getDemurrageFrequency())
					|| hasValueChanged(agreementVO.getDemurrageRate(), form
							.getDemurrageRate())
					|| hasValueChanged(agreementVO.getFreeLoanPeriod(), form
							.getFreeLoanPeriod())
					|| hasValueChanged(agreementVO.getTax(), form.getTaxes())
					|| hasValueChanged(agreementVO.getPartyCode(), form
							.getPartyCode().toUpperCase())
					|| hasValueChanged(agreementVO.getPartyType(), form
							.getPartyType().toUpperCase()) ||
							 hasValueChanged(agreementVO.getFromPartyCode(), form
							.getPartyCode().toUpperCase())
					|| hasValueChanged(agreementVO.getFromPartyType(), form
							.getPartyType().toUpperCase())
				|| hasValueChanged(
					agreementVO.getTxnType(), form.getTransactionType()))) {
				log.log(Log.FINE, "\n\n\n\nsetting the transaction flag");
				agreementVO.setValidateFlag(true);
			}

		}

		agreementVO.setCompanyCode(logonAttributes.getCompanyCode());
		if (DateUtilities.isValidDate(form.getAgreementDate(),
				CALENDAR_DATE_FORMAT)) {
			agreementVO.setAgreementDate(agreementDate.setDate(form
					.getAgreementDate()));
		} else {
			agreementVO.setAgreementDate(null);
		}
		if (DateUtilities.isValidDate(form.getFromDate(), CALENDAR_DATE_FORMAT)) {
			agreementVO.setAgreementFromDate(startDate.setDate(form
					.getFromDate()));
		}
		if (DateUtilities.isValidDate(form.getToDate(), CALENDAR_DATE_FORMAT)) {
			agreementVO.setAgreementToDate(endDate.setDate(form.getToDate()));
		} else {
			agreementVO.setAgreementToDate(null);
		}
		agreementVO.setAgreementStatus(form.getAgreementStatus());
		agreementVO.setCompanyCode(logonAttributes.getCompanyCode());
		agreementVO.setCurrency(form.getCurrencyCode());
		agreementVO.setDemurrageFrequency(form.getDemurrageFrequency());
		agreementVO.setDemurrageRate(form.getDemurrageRate());
		agreementVO.setRemark(form.getRemarks());
		agreementVO.setFreeLoanPeriod(form.getFreeLoanPeriod());
		agreementVO.setPartyCode(form.getPartyCode().toUpperCase());
		agreementVO.setPartyType(form.getPartyType().toUpperCase());
		agreementVO.setPartyName(form.getPartyName().toUpperCase());
		//added as part of ICRD-232684 by A-4393 starts
		agreementVO.setFromPartyCode(form.getFromPartyCode().toUpperCase());
		agreementVO.setFromPartyType(form.getFromPartyType().toUpperCase());
		agreementVO.setFromPartyName(form.getFromPartyName().toUpperCase());
		//added as part of ICRD-232684 by A-4393 ends 
		agreementVO.setTxnType(form.getTransactionType());
		agreementVO.setTax(form.getTaxes());
		/*Collection<ULDAgreementDetailsVO> details = agreementVO
				.getUldAgreementDetailVOs();*/
		LocalDate detailToDate = new LocalDate(
				logonAttributes.getAirportCode(),Location.ARP, false);
		if (pageULDAgreementDetailsVO != null && pageULDAgreementDetailsVO.size() > 0) {
			for (ULDAgreementDetailsVO detailsvo : pageULDAgreementDetailsVO) {
				log.log(Log.INFO, "----------inside for loop-----------");
				if (detailsvo.getAgreementToDate() == null
						&& DateUtilities.isValidDate(form.getToDate(),
								CALENDAR_DATE_FORMAT)) {
					log
							.log(
									Log.INFO,
									"\n\n----------inside for loop not null-----------",
									detailsvo.getAgreementToDate());
					detailsvo.setAgreementToDate(detailToDate.setDate(form
							.getToDate()));
					if (!OPERATION_FLAG_INSERT.equals(detailsvo
							.getOperationFlag())) {
						detailsvo.setOperationFlag(OPERATION_FLAG_UPDATE);
					}
				} else if (!OPERATION_FLAG_INSERT.equals(detailsvo
							.getOperationFlag()) && !OPERATION_FLAG_DELETE.equals(detailsvo
									.getOperationFlag()) && "U".equals(agreementVO.getOperationFlag())) {
						detailsvo.setOperationFlag(OPERATION_FLAG_UPDATE);
				}
			}
		}
		log.log(Log.INFO, "VO To Server--------------", agreementVO);
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();

		if (isDateValid(agreementVO)) {
			if (isEndDatesValid(agreementVO)) {
				if (isAgreementDateValid(agreementVO)) {
					String agreementNumber = BLANK_STRING;
					try {
						agreementNumber = delegate
								.createULDAgreement(agreementVO);
					} catch (BusinessDelegateException ex) {
						ex.getMessage();
						errors = handleDelegateException(ex);
						invocationContext.addAllError(errors);
						invocationContext.target = SCREENLOAD_SUCCESS;
					}
					if (!BLANK_STRING.equals(agreementNumber)
							&& agreementNumber != null) {
						//form.setAgreementDate(BLANK_STRING);
						LocalDate date = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,true);
						String agreementDateString = TimeConvertor.toStringFormat(date.toCalendar(),
						"dd-MMM-yyyy");
						form.setAgreementDate(agreementDateString);
						
						form.setAgreementNumber(BLANK_STRING);
						form.setDemurrageRate(0.0);
						form.setFreeLoanPeriod(0);
						form.setPartyName(BLANK_STRING);
						form.setPartyCode(BLANK_STRING);
						form.setPartyType(AIRLINE);
						//added as part of ICRD-232684 by A-4393 starts 
						form.setFromPartyName(BLANK_STRING);
						form.setFromPartyCode(BLANK_STRING);
						form.setFromPartyType(AIRLINE);
						//added as part of ICRD-232684 by A-4393 ends 
						form.setRemarks(BLANK_STRING);
						form.setTaxes(0.0);
						form.setFromDate(BLANK_STRING);
						form.setToDate(BLANK_STRING);
						form.setOnload(BLANK_STRING);
						ULDAgreementVO newAgreementVO = new ULDAgreementVO();
						newAgreementVO.setOperationFlag(OPERATION_FLAG_INSERT);
						session.setUldAgreementDetails(newAgreementVO);
						//Added by A-8445
						form.setLastPageNumStr("0");
						form.setDisplayPageNumStr("1");
						form.setUldTypeFilter(BLANK_STRING);
						session.setUldAgreementPageDetails(null);
						session.setTotalRecordsCount(0);
						Object[] number = new Object[] { agreementNumber };
						ErrorVO errorVO = new ErrorVO(
								"uld.defaults.savedagreementnumber", number);
						errorVO.setErrorDisplayType(ErrorDisplayType.INFO);
						errors.add(errorVO);
						invocationContext.addAllError(errors);
						invocationContext.target = SCREENLOAD_SUCCESS;
					}

					if (agreementNumber == null) {
						if (CANCLEAR.equals(form.getCanClear())) {
							invocationContext.target = SCREENLOAD_LIST;
							return;
						}
						form.setAgreementDate(BLANK_STRING);
						form.setAgreementNumber(BLANK_STRING);
						form.setDemurrageRate(0.0);
						form.setFreeLoanPeriod(0);
						form.setPartyName(BLANK_STRING);
						form.setPartyCode(BLANK_STRING);
						form.setPartyType(AIRLINE);
						//added as part of ICRD-232684 by A-4393 ends 
						form.setFromPartyName(BLANK_STRING);
						form.setFromPartyCode(BLANK_STRING);
						form.setFromPartyType(AIRLINE);
						//added as part of ICRD-232684 by A-4393 ends 
						form.setRemarks(BLANK_STRING);
						form.setTaxes(0.0);
						form.setFromDate(BLANK_STRING);
						form.setToDate(BLANK_STRING);
						form.setOnload(BLANK_STRING);
						ULDAgreementVO newAgreementVO = new ULDAgreementVO();
						newAgreementVO.setOperationFlag(OPERATION_FLAG_INSERT);
						session.setUldAgreementDetails(newAgreementVO);
						//Added by A-8445
						form.setLastPageNumStr("0");
						form.setDisplayPageNumStr("1");
						form.setUldTypeFilter(BLANK_STRING);
						session.setUldAgreementPageDetails(null);
						session.setTotalRecordsCount(0);
						
						ErrorVO error = new ErrorVO("uld.defaults.maintainuldagreement.savedsuccessfully");
					    error.setErrorDisplayType(ErrorDisplayType.STATUS);
					    errors = new ArrayList<ErrorVO>();
					    errors.add(error);
					    invocationContext.addAllError(errors);
						invocationContext.target = SCREENLOAD_SUCCESS;
					}
				} else {
					ErrorVO errorVO = new ErrorVO("uld.defaults.invalidagrdate");
					errors.add(errorVO);
					invocationContext.addAllError(errors);
					invocationContext.target = SCREENLOAD_FAILURE;
					return;
				}
			} else {
				ErrorVO errorVO = new ErrorVO("uld.defaults.invalidenddates");
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;

			}
		}

		else {

			ErrorVO errorVO = new ErrorVO("uld.defaults.invalidagreementdate");
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
	}

	/**
	 * 
	 * @param originalValue
	 * @param formValue
	 * @return boolean
	 */
	public boolean hasValueChanged(String originalValue, String formValue) {
		if (originalValue == null && formValue != null) {
			return true;
		}
		if (originalValue.equals(formValue)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @param originalValue
	 * @param formValue
	 * @return
	 */
	public boolean hasValueChanged(double originalValue, double formValue) {
		if (originalValue != formValue) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param originalValue
	 * @param formValue
	 * @return boolean
	 */
	public boolean hasValueChanged(int originalValue, int formValue) {
		if (originalValue != formValue) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param agreementVO
	 * @return boolean
	 */
	public boolean isDateValid(ULDAgreementVO agreementVO) {
		Collection<ULDAgreementDetailsVO> agreementDetails = agreementVO
				.getUldAgreementDetailVOs();
		if (agreementDetails != null && agreementDetails.size() > 0) {
			for (ULDAgreementDetailsVO detailsVO : agreementDetails) {
				//for adding station to details date
				LocalDate detailsFromDate = new LocalDate(getApplicationSession().
						getLogonVO().getAirportCode(),Location.ARP,
						false);
				detailsFromDate.setDate(detailsVO.getAgreementFromDate().
						toDisplayDateOnlyFormat());
				
				if (detailsFromDate.isLesserThan(
						agreementVO.getAgreementFromDate())) {
					return false;
				}
				if (detailsVO.getAgreementToDate() != null
						&& agreementVO.getAgreementToDate() != null) {
					LocalDate detailsToDate = new LocalDate(getApplicationSession().
							getLogonVO().getAirportCode(),Location.ARP,
							false);
					detailsToDate.setDate(detailsVO.getAgreementToDate().toDisplayDateOnlyFormat());
					if (detailsFromDate.isGreaterThan(
							agreementVO.getAgreementToDate())
							|| detailsToDate.isGreaterThan(
									agreementVO.getAgreementToDate())) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/***************************************************************************
	 * 
	 * @param agreementVO
	 * @return boolean
	 */
	public boolean isAgreementDateValid(ULDAgreementVO agreementVO) {
		LocalDate currentDate = new LocalDate(getApplicationSession()
				.getLogonVO().getAirportCode(),Location.ARP, true);
		if (agreementVO.getAgreementDate() != null) {
			if (agreementVO.getAgreementDate().isGreaterThan(currentDate)) {
				return false;
			} else {
				return true;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param agreementVO
	 * @return boolean
	 */
	public boolean isEndDatesValid(ULDAgreementVO agreementVO) {
		if (agreementVO.getAgreementFromDate() != null
				&& agreementVO.getAgreementToDate() != null) {
			if (agreementVO.getAgreementFromDate().after(
					agreementVO.getAgreementToDate())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param form
	 * @return
	 */
	private Collection<ErrorVO> validateForm(MaintainULDAgreementForm form) {
		
		log.log(log.FINE,"inside validateForm");
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if (form.getPartyCode() == null
				|| (form.getPartyCode().trim().length() == 0)) {
			ErrorVO error = new ErrorVO("uld.defaults.enterpartycode");
			errors.add(error);
		}
		String airlineCode=getApplicationSession().getLogonVO().getOwnAirlineCode();
		
		log.log(Log.INFO, "own airline code", airlineCode);
		//added as part of ICRD-232684 by A-4393 starts 
		if (form.getPartyCode() != null
				&& form.getPartyCode().trim().length() > 0&&form.getFromPartyCode()!=null&&!form.getFromPartyCode().isEmpty()) {
			if ((form.getPartyCode().trim().toUpperCase()).equals(form.getFromPartyCode())) {
				if(!AIRLINE_ALL.equals(form.getPartyCode())&&!AGENT_ALL.equals(form.getPartyCode())&&!"ALL".equals(form.getPartyCode())){
				ErrorVO error = new ErrorVO(
						"uld.defaults.fromandtopartycannotbesame");
				errors.add(error);
				}
			}
		}
		//added as part of ICRD-232684 by A-4393 ends 
		//commented as part of ICRD-232684 by A-4393 ends 
		/*if(form.getPartyCode()!=null && form.getPartyCode().trim().length()>0){
			if((form.getPartyCode().trim().toUpperCase()).equals(airlineCode)){
				ErrorVO error = new ErrorVO("uld.defaults.partycannotbeownairline");
				errors.add(error);
			}
		}*/  
		if (form.getFromDate() == null
				|| (form.getFromDate().trim().length() == 0)) {
			ErrorVO error = new ErrorVO("uld.defaults.enterfromdate");
			errors.add(error);
		}
	
		if (form.getAgreementDate() == null
				|| (form.getAgreementDate().trim().length() == 0)) {	
			ErrorVO error = new ErrorVO("uld.defaults.enteragreementDate");
			errors.add(error);
		}
		
		
		return errors;
	}

	/**
	 * 
	 * @param form
	 * @param agreementVO
	 * @return
	 */
	public boolean hasToDateChanged(MaintainULDAgreementForm form,
			ULDAgreementVO agreementVO) {
		if (agreementVO.getAgreementToDate() != null) {

			if (agreementVO.getAgreementToDate().toDisplayDateOnlyFormat()
					.equalsIgnoreCase(form.getToDate())) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param form
	 * @param agreementVO
	 * @return boolean
	 */
	public boolean hasAgreementDateChanged(MaintainULDAgreementForm form,
			ULDAgreementVO agreementVO) {
		if (agreementVO.getAgreementDate() != null) {
			if (agreementVO.getAgreementDate().toDisplayDateOnlyFormat()
					.equalsIgnoreCase(form.getAgreementDate())) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	
	public Collection<ErrorVO> validateCurrency(
			String companyCode,MaintainULDAgreementForm form ) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			
			new CurrencyDelegate().validateCurrency(
					companyCode,form.getCurrencyCode().toUpperCase());
									
		} catch (BusinessDelegateException e) {
			errors=	handleDelegateException(e);
		}
		
		return errors;
	} 
}
