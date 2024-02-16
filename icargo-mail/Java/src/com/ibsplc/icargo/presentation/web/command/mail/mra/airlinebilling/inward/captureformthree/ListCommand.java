/*
 * ListCommand.java Created on Aug 06, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.captureformthree;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineForBillingVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InterlineFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureFormThreeSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormThreeForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-3108
 *
 */
public class ListCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("CaptureForm3 ScreenloadCommand");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.captureformthree";

	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "list_success";
	private static final String ACTION_FAILURE = "list_failure";
	private static final String CLEARANCE_PERIOD_MANDATORY="mailtracking.mra.airlinebilling.inward.msg.err.clearanceperiodmandatory";
	private static final String NO_DATA_FOUND="mailtracking.mra.airlinebilling.inward.msg.err.nodatafound";
	private static final String CLRPRD_INVALID= "mailtracking.mra.airlinebilling.inward.msg.err.clrprdinvalid";
	private static final String STATUS_ONETIME = "mra.airlinebilling.formThreeStatus";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {

    	log.entering(CLASS_NAME, "execute");
    	CaptureFormThreeSession captureFormThreeSession = (CaptureFormThreeSession)
    	getScreenSession(MODULE_NAME, SCREEN_ID);
    	CaptureMailFormThreeForm captureFormThreeForm=(CaptureMailFormThreeForm)invocationContext.screenModel;


    	LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
    	Collection<AirlineForBillingVO> airlineForBillingVOS = new ArrayList<AirlineForBillingVO>();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	InterlineFilterVO interlineFilterVO = new InterlineFilterVO();
    	SharedDefaultsDelegate sharedDefaultsDelegate = new  SharedDefaultsDelegate();
    	interlineFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
    	if(captureFormThreeSession.getInterlineFilterVO()==null){
    		if(captureFormThreeForm.getClearancePeriod()==null || captureFormThreeForm.getClearancePeriod().length()<=0){
    			errors.add(new ErrorVO(CLEARANCE_PERIOD_MANDATORY));
    		}
    		if(errors != null && errors.size() > 0){
    			invocationContext.addAllError(errors);
    			captureFormThreeForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
    			captureFormThreeForm.setLinkDisable("Y");
    			invocationContext.target = ACTION_FAILURE;
    			return;
    		}
    		else{

    			IATACalendarVO   iatacalendarvo = null;
    			IATACalendarFilterVO iatacalendarfiltervo = new IATACalendarFilterVO();
    			iatacalendarfiltervo.setCompanyCode(logonAttributes.getCompanyCode());
    			iatacalendarfiltervo.setClearancePeriod(captureFormThreeForm.getClearancePeriod());

    			try{

    				iatacalendarvo = new MailTrackingMRADelegate().validateClearancePeriod(iatacalendarfiltervo);
    				log
							.log(Log.INFO, "iatacalendarvo obtained",
									iatacalendarvo);

    			}catch(BusinessDelegateException businessDelegateException){
    				errors = handleDelegateException(businessDelegateException);
    			}


    			if(iatacalendarvo!=null  ){
    				log.log(Log.INFO, "iatacalendarvo not null ",
							iatacalendarvo);

    			}
    			else{
    				log.log(log.FINE,"iatacalendarvo null");
    				ErrorVO err=new ErrorVO(CLRPRD_INVALID);
    				err.setErrorDisplayType(ErrorDisplayType.ERROR);
    				errors.add(err);
    				captureFormThreeForm.setLinkDisable("Y");
    				captureFormThreeForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
    			}
    		}
    		if(errors != null && errors.size() > 0){
    			invocationContext.addAllError(errors);
    			captureFormThreeForm.setLinkDisable("Y");
    			invocationContext.target = ACTION_FAILURE;
    			return;
    		}
    		interlineFilterVO.setClearancePeriod(
    				captureFormThreeForm.getClearancePeriod());

    		log
					.log(Log.INFO, "Filter VO To Server>>>>>>>>>",
							interlineFilterVO);
    	}
    	else{
    		interlineFilterVO=captureFormThreeSession.getInterlineFilterVO();
    		Map<String, Collection<OneTimeVO>>
    		oneTimeValues = null;
    		try {

    			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
    					logonAttributes.getCompanyCode(), getOneTimeParameterTypes());
    			if(oneTimeValues!=null){
    				captureFormThreeSession.setOneTimeValues(new HashMap<String, Collection<OneTimeVO>>(oneTimeValues));
    			}
    		} catch (BusinessDelegateException businessDelegateException) {
    			log.log(Log.FINE, "*****in the exception");
    			businessDelegateException.getMessageVO().getErrors();
    			handleDelegateException(businessDelegateException);
    		}
    		log.log(Log.INFO, "Filter VO To Server from session",
					interlineFilterVO);
    	}
    	try{

    		airlineForBillingVOS = (ArrayList<AirlineForBillingVO>)new MailTrackingMRADelegate().
    		findFormThreeDetails(interlineFilterVO);
    	}catch(BusinessDelegateException businessDelegateException){
    		errors = handleDelegateException(businessDelegateException);
    	}
    	if(errors != null && errors.size() > 0){
    		captureFormThreeForm.setLinkDisable("Y");
    		invocationContext.addAllError(errors);
    		captureFormThreeForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
    		invocationContext.target = ACTION_FAILURE;
    		return;
    	}
    	log.log(Log.INFO, "airlineForBillingVOS", airlineForBillingVOS);
		if(airlineForBillingVOS==null || airlineForBillingVOS.size()==0){
    		captureFormThreeForm.setLinkDisable("N");
    		errors.add(new ErrorVO(NO_DATA_FOUND));
    		//captureFormThreeForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
    	}
    	if(errors != null && errors.size() > 0){
    		invocationContext.addAllError(errors);
    		//captureFormThreeForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
    		invocationContext.target = ACTION_FAILURE;
    		return;
    	}
    	Money creditMoney=null;
    	Money miscMoney =null;
    	Money totMoney=null;
    	Money netMoney=null;
    	try{
    		creditMoney = CurrencyHelper.getMoney("USD");
    		creditMoney.setAmount(0.0D);
    		miscMoney = CurrencyHelper.getMoney("USD");
    		miscMoney.setAmount(0.0D);
    		totMoney = CurrencyHelper.getMoney("USD");
    		totMoney.setAmount(0.0D);
    		netMoney = CurrencyHelper.getMoney("USD");
    		netMoney.setAmount(0.0D);
    	}
    	catch(CurrencyException e){
    		e.getErrorCode();
    	}

    	for(AirlineForBillingVO airlineForBillingVO:airlineForBillingVOS) {
    		airlineForBillingVO.setOperationFlag("N");
    		if(airlineForBillingVO.getCreditAmountInBilling()!=null){
    			creditMoney.plusEquals(airlineForBillingVO.getCreditAmountInBilling());
    		}
    		if(airlineForBillingVO.getMiscAmountInBilling()!=null){
    			miscMoney.plusEquals(airlineForBillingVO.getMiscAmountInBilling());
    		}
    		if(airlineForBillingVO.getTotalAmountInBilling()!=null){
    			totMoney.plusEquals(airlineForBillingVO.getTotalAmountInBilling())	;
    		}
    		if(airlineForBillingVO.getNetValueInBilling()!=null){
    			netMoney.plusEquals(airlineForBillingVO.getNetValueInBilling());
    		}

    	}
    	captureFormThreeForm.setCreditTotalAmountInBillingMoney(creditMoney);
    	captureFormThreeForm.setMiscTotalAmountInBillingMoney(miscMoney);
    	captureFormThreeForm.setGrossTotalAmountInBillingMoney(totMoney);
    	captureFormThreeForm.setNetTotalValueInBillingMoney(netMoney);




    	log.log(Log.INFO, "airlineForBillingVOS", airlineForBillingVOS);
		captureFormThreeSession.setAirlineForBillingVOs(new ArrayList<AirlineForBillingVO>(airlineForBillingVOS));
    	captureFormThreeForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
    	captureFormThreeForm.setLinkDisable("N");
    	invocationContext.target = ACTION_SUCCESS;
    	log.exiting(CLASS_NAME, "execute");
    }
    /**
     * @author A-3447
     * @return String
     */

    private Collection<String> getOneTimeParameterTypes() {

		ArrayList<String> parameterTypes = new ArrayList<String>();

		parameterTypes.add(STATUS_ONETIME);
		return parameterTypes;
	}

}
