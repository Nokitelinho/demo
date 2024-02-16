package com.ibsplc.icargo.presentation.web.command.mail.operations.mldconfigurations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MLDConfigurationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MLDConfigurationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;

import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MLDConfigurationSession;

import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MLDConfigurationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-5526
 * 
 */
public class ListCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILTRACKING");
	private static final String TARGET_SUCCESS = "list_success";
	private static final String TARGET_FAILURE = "list_failure";

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mldconfiguration";
	private static final String INVALID_CARRIER = "mailtracking.defaults.invalidcarrier";
	private static final String INVALID_AIRPORT = "mailtracking.defaults.invalidairport";
	private static final String NO_DATA_FOUND = "mailtracking.defaults.mldconfiguration.msg.err.nodata";
	
	private static final String ONETIME_MLDVERSION = "mailtracking.defaults.mldversions";
	
	private static final String MLDVERSION_MANDATORY_FOR_LIST ="mailtracking.defaults.mldconfiguration.msg.err.mldversionmandatory";
	private static final String INVALID_MLDVERSION ="mailtracking.defaults.mldconfiguration.msg.err.invalidmldversion";
	
	

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ListCommand", "execute");

		MLDConfigurationForm mLDConfigurationForm = (MLDConfigurationForm) invocationContext.screenModel;
		MLDConfigurationSession mLDConfigurationSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;

		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();

		MLDConfigurationFilterVO mLDConfigurationFilterVO = new MLDConfigurationFilterVO();
		Collection<MLDConfigurationVO> mLDConfigurationVOs = new ArrayList<MLDConfigurationVO>();

		// VALIDATING FLIGHT CARRIER
		String carrier = mLDConfigurationForm.getCarrier();
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		AirlineValidationVO airlineValidationVO = null;
		errors = null;
		if (carrier != null && !"".equals(carrier)) {

			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						logonAttributes.getCompanyCode(), carrier.trim()
								.toUpperCase());

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				log.log(Log.FINE, " errors.size() --------->>", errors.size());
				for (ErrorVO error : errors) {
					error.setErrorCode(INVALID_CARRIER);
				}
				invocationContext.addAllError(errors);
				invocationContext.target = TARGET_FAILURE;
				return;
			}
		}

		// VALIDATING PORT
		String port = mLDConfigurationForm.getAirport();
		errors = null;
		if (port != null && !"".equals(port)) {

			try {
				new AreaDelegate().validateAirportCode(
						logonAttributes.getCompanyCode(), port.toUpperCase());
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				errors = new ArrayList<ErrorVO>();
				log.log(Log.FINE, " errors.size() --------->>", errors.size());
				errors.add(new ErrorVO(INVALID_AIRPORT,
						new Object[] { port.toUpperCase() }));
				invocationContext.addAllError(errors);
				invocationContext.target = TARGET_FAILURE;
				return;
			}
		}

		mLDConfigurationFilterVO.setAirportCode(mLDConfigurationForm
				.getAirport());
		mLDConfigurationFilterVO.setCarrierCode(mLDConfigurationForm
				.getCarrier());
		mLDConfigurationFilterVO.setCompanyCode(logonAttributes
				.getCompanyCode());
		
		//Added for CRQ ICRD-135130 by A-8061 starts
		//validate 

		errors=null;
		if (mLDConfigurationForm.getMldversion()==null || "".equals(mLDConfigurationForm.getMldversion())) {
			
			errors = new ArrayList<ErrorVO>();
			ErrorVO error = new ErrorVO(MLDVERSION_MANDATORY_FOR_LIST);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		
		Map<String, Collection<OneTimeVO>> oneTimesMap=getOneTimeValues();
		Collection<OneTimeVO> mldVersionsOneTimeVOs=oneTimesMap.get(ONETIME_MLDVERSION);
		boolean isValidMLDVersion=false;
		for(OneTimeVO oneTimeVO: mldVersionsOneTimeVOs){
			if(mLDConfigurationForm.getMldversion().equals(oneTimeVO.getFieldDescription())){
				isValidMLDVersion=true;
			}
		}
		
		if(!isValidMLDVersion){
			
			errors = new ArrayList<ErrorVO>();
			ErrorVO error = new ErrorVO(INVALID_MLDVERSION);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}

		mLDConfigurationFilterVO.setMldversion(mLDConfigurationForm.getMldversion());
		
		//Added for CRQ ICRD-135130 by A-8061 end
		
		// mLDConfigurationSession.setDsnEnquiryFilterVO(dsnEnquiryFilterVO);

		try {
			mLDConfigurationVOs = mailTrackingDefaultsDelegate
					.findMLDCongfigurations(mLDConfigurationFilterVO);

		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}

		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}

		
		
		mLDConfigurationForm.setListFlag("Y");//Added for CRQ ICRD-135130 by A-8061 
		
		
		if (mLDConfigurationVOs != null && mLDConfigurationVOs.size() > 0) {
			log.log(Log.FINE, "Collection of mldConfigVOS --------->>",
					mLDConfigurationVOs);
			mLDConfigurationSession.setMLDConfigurationVOs(mLDConfigurationVOs);
		} else {
			ErrorVO errorVO = new ErrorVO(
					NO_DATA_FOUND);
			//Added as part of Bug ICRD-143797 by A-5526
			mLDConfigurationSession.setMLDConfigurationVOs(null);
			errors = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}

		mLDConfigurationForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);

		invocationContext.target = TARGET_SUCCESS;

		log.exiting("ListCommand", "execute");

	}
	


	/**
	 * @author A-8061
	 * @return
	 */
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
		log.entering("ListCommand","getOneTimeValues");
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate = 
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		
		try {
			log.log(Log.FINE, "****inside try**************************",
					getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
			handleDelegateException(businessDelegateException);
		}
		//updateMilitaryClass(oneTimeValues.get(ONETIME_CLASS));
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ScreenLoadCommand","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}
	 
	/**
	 * @author A-8061
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	parameterTypes.add(ONETIME_MLDVERSION);
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }

}
