/*
 * ListCommand.java Created on July 15, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.listformone;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward.ListFormOneSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ListMailFormOneForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 *
 */
public class ListCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA_AIRLINEBILLING");

	private static final String MODULE = "mra.airlinebilling";

	private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.listform1";

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String ERROR_KEY_NORESUTLS_FOUND=
		"mra.airlinebilling.outward.listformone.msg.err.norecordfound";

	private static final String CLASS_NAME = "ListCommand";

	private static final String INVALID_AIRLINE_CODE=
		"mra.airlinebilling.outward.listformone.msg.err.invalidairlinecode";

	private static final String INVALID_NUMERIC_CODE=
		"mra.airlinebilling.outward.listformone.msg.err.invalidnumericcode";
	private static final String CLRPRD_INVALID= "mailtracking.mra.airlinebilling.outward.clrprdinvalid";
	private static final String CLRPRD_MANDATORY= "mailtracking.mra.airlinebilling.outward.clrprdmandatory";
	private static final String BLANK= "";





	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		ListMailFormOneForm listFormOneForm = (ListMailFormOneForm)invocationContext.screenModel;

		ListFormOneSession  listFormOneSession =
			(ListFormOneSession)getScreenSession(MODULE, SCREENID);

		listFormOneSession.removeFormOneVOs();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String airlineNumber=BLANK;
		String airlineCode=BLANK;
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		if(listFormOneForm.getAirlineNumber()!=null){
			airlineNumber = listFormOneForm.getAirlineNumber().toUpperCase();
		}
		if(listFormOneForm.getAirlineCodeFilterField()!=null){
			airlineCode = listFormOneForm.getAirlineCodeFilterField().toUpperCase();
		}
		String displayPage = listFormOneForm.getDisplayPage();

		ErrorVO error = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>(); 
		if(("").equals(listFormOneForm.getClearancePeriod()) &&(listFormOneForm.getClearancePeriod().trim().length()==0)){

			errors.add(new ErrorVO(CLRPRD_MANDATORY));
			invocationContext.addAllError(errors);

			invocationContext.target = LIST_FAILURE;
			return;
		}
		else{
			log.log(log.INFO,"inside ClearancePeriod");

			IATACalendarFilterVO iatacalendarfiltervo = new IATACalendarFilterVO();
			iatacalendarfiltervo.setCompanyCode(logonAttributes.getCompanyCode());
			iatacalendarfiltervo.setClearancePeriod(listFormOneForm.getClearancePeriod());
			IATACalendarVO  iatacalendarvo = null;
			try{

				iatacalendarvo = mailTrackingMRADelegate.validateClearancePeriod(iatacalendarfiltervo);
				log.log(Log.INFO, "iatacalendarvo obtained", iatacalendarvo);
			}
			catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);

			}

			if(iatacalendarvo == null ){
				log.log(log.INFO,"iatacalendarvo null");

				errors.add(new ErrorVO(CLRPRD_INVALID));
				invocationContext.addAllError(errors);

				invocationContext.target = LIST_FAILURE;
				return;
			}
			else{
				log.log(log.INFO,"iatacalendarvo!=null");
			}
		}
		FormOneFilterVO listFormOneFilterVo = new FormOneFilterVO();
		Page<FormOneVO> formOneVos = null;
		AirlineValidationVO airlineValidationVO = null;


		listFormOneFilterVo.setCompanyCode(companyCode);
		listFormOneFilterVo.setClearancePeriod(listFormOneForm.getClearancePeriod());		
		listFormOneFilterVo.setPageNumber(Integer.parseInt(displayPage));

		if(airlineCode != null && airlineCode.trim().length()>0){
			try {
				airlineValidationVO = new AirlineDelegate().validateAlphaCode(
						companyCode, airlineCode);
			}catch (BusinessDelegateException e) {
				if(airlineValidationVO == null ){

					errors.add(new ErrorVO(INVALID_AIRLINE_CODE));
					invocationContext.addAllError(errors);

					invocationContext.target = LIST_FAILURE;
					return;
				}
				else{
					listFormOneFilterVo.setAirlineId
					(airlineValidationVO.getAirlineIdentifier());
				}
			}	

		}

		if (airlineNumber != null && airlineNumber.trim().length()>0) {
			//listFormOneFilterVo.setAirlineNumber(airlineNumber);	
			airlineValidationVO 
			= validateNumericCode(companyCode,airlineNumber);
			if(airlineValidationVO == null ){

				errors.add(new ErrorVO(INVALID_NUMERIC_CODE));
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;

			}	else{
				listFormOneFilterVo.setAirlineId
				(airlineValidationVO.getAirlineIdentifier());
			}

		}				

		try{
			formOneVos = mailTrackingMRADelegate.findFormOnes(listFormOneFilterVo);
		}
		catch(BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);

		}
		if(formOneVos == null || formOneVos.size() == 0 ){

			errors.add(new ErrorVO(ERROR_KEY_NORESUTLS_FOUND));
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}


		log.log(Log.FINE, "returned page collection SIZE:---> ", formOneVos.size());
		log.log(Log.FINE, "returned page collection : ", formOneVos);
		listFormOneSession.setFormOneVOs(formOneVos);
		log.exiting(CLASS_NAME,"execute");
		listFormOneForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);	
		invocationContext.target=LIST_SUCCESS;

	}



	/**
	 * @author a-3434
	 * @param companyCode
	 * @param airlineNumber
	 * @return
	 */
	private AirlineValidationVO validateNumericCode(String companyCode,String airlineNumber){
		AirlineDelegate airlineDelegate = new AirlineDelegate(); 
		AirlineValidationVO validationVO = null;
		try{
			validationVO 
			= airlineDelegate.validateNumericCode(companyCode,airlineNumber);
		}
		catch(BusinessDelegateException businessDelegateException) {
			log.log(Log.SEVERE," invalid Airline Exception from server ");
		}
		return validationVO;
	}

}
