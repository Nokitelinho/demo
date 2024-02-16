/*
 * ListULDDetailsCommand.java Created on JAN 24, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.maintainuld;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * This command class is used to list the details of the specified ULD
 * 
 * @author A-1347
 */
public class ListULDDetailsCommand extends BaseCommand {

	@Override
	public boolean breakOnInvocationFailure() {		
		return true;
	}
	
	
	private Log log = LogFactory.getLogger("LIST MAINTAIN ULD");

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.maintainuld";

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String WEIGHTUNIT_ONETIME = "shared.defaults.weightUnitCodes";

	private static final String DIMENTIONUNIT_ONETIME = "shared.defaults.dimensionUnitCodes";

	private static final String OPERATSTATUS_ONETIME = "uld.defaults.operationalStatus";
	
	private static final String ULDNATURE_ONETIME = "uld.defaults.uldnature";

	private static final String CLEANSTATUS_ONETIME = "uld.defaults.cleanlinessStatus";

	private static final String DAMAGESTATUS_ONETIME = "uld.defaults.damageStatus";

	private static final String CONTOUR_ONETIME = "operations.flthandling.uldcontour";

	private static final String FACILITYTYPE_ONETIME = "uld.defaults.facilitytypes";

	private static final String LISTULD_SUCCESS = "listuld_success";

	private static final String LISTULD_FAILURE = "listuld_failure";

	// ADDED FOR SCM RECONCILE
	private static final String PAGE_URL = "fromScmUldReconcile";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		MaintainULDForm maintainULDForm = (MaintainULDForm) invocationContext.screenModel;

		MaintainULDSession maintainULDSession = (MaintainULDSession) getScreenSession(
				MODULE, SCREENID);
		if (maintainULDSession.getPageURL() != null
				&& ("fromulderrorlog").equals(maintainULDSession.getPageURL())) {
			ULDFlightMessageReconcileDetailsVO uldFlightMessageReconcileDetailsVO = maintainULDSession
					.getULDFlightMessageReconcileDetailsVO();
			maintainULDForm.setUldNumber(uldFlightMessageReconcileDetailsVO
					.getUldNumber());
			maintainULDForm.setScreenloadstatus("fromulderrorlog");
			maintainULDForm.setOverallStatus("O");
			maintainULDForm.setDamageStatus("N");
			maintainULDForm.setCleanlinessStatus("C");
			HashMap<String, Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
			maintainULDSession.setOneTimeValues(oneTimeValues);
	    	//Added by A-3415 for ICRD-113953 Starts
			Collection<String> systemparameterCodes = new  ArrayList<String>();
			Map<String,String> map = new HashMap<String,String>();
			systemparameterCodes.add(ULDVO.NON_OPERATIONAL_DAMAGE_STATUS);
			try {
				map = new SharedDefaultsDelegate().findSystemParameterByCodes(systemparameterCodes);
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.SEVERE, "System Parameter for Damage Status not found ");
			} 
			if(map!=null){
				maintainULDSession.setNonOperationalDamageCodes(map.get(ULDVO.NON_OPERATIONAL_DAMAGE_STATUS));
			}
			//Added by A-3415 for ICRD-113953 Ends
		}

		// ADDED FOR SCM RECONCILE
		else if (maintainULDSession.getPageURL() != null
				&& PAGE_URL.equals(maintainULDSession.getPageURL())) {
			ULDSCMReconcileDetailsVO scmReconcileVO = maintainULDSession
					.getSCMReconcileDetailsVO();
			maintainULDForm.setUldNumber(scmReconcileVO.getUldNumber());
			maintainULDForm.setScreenloadstatus(PAGE_URL);
			maintainULDForm.setOverallStatus("O");
			maintainULDForm.setDamageStatus("N");
			maintainULDForm.setCleanlinessStatus("C");
			HashMap<String, Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
			maintainULDSession.setOneTimeValues(oneTimeValues);
	    	//Added by A-3415 for ICRD-113953 Starts
			Collection<String> systemparameterCodes = new  ArrayList<String>();
			Map<String,String> map = new HashMap<String,String>();
			systemparameterCodes.add(ULDVO.NON_OPERATIONAL_DAMAGE_STATUS);
			try {
				map = new SharedDefaultsDelegate().findSystemParameterByCodes(systemparameterCodes);
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.SEVERE, "System Parameter for Damage Status not found ");
			} 
			if(map!=null){
				maintainULDSession.setNonOperationalDamageCodes(map.get(ULDVO.NON_OPERATIONAL_DAMAGE_STATUS));
			}
			//Added by A-3415 for ICRD-113953 Ends
			//Added by A-7530 for ICRD-213974 starts
		} else if ("qmsserviceRecovery".equals(maintainULDForm.getFromScreen())) {
			Collection<String> systemparameterCodes = new  ArrayList<String>();
			Map<String,String> map = new HashMap<String,String>();
			systemparameterCodes.add(ULDVO.NON_OPERATIONAL_DAMAGE_STATUS);
			try {
				map = new SharedDefaultsDelegate().findSystemParameterByCodes(systemparameterCodes);
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.SEVERE, "System Parameter for Damage Status not found ");
			} 
			if(map!=null){
				maintainULDSession.setNonOperationalDamageCodes(map.get(ULDVO.NON_OPERATIONAL_DAMAGE_STATUS));
			}
		}
		//Added by A-7530 for ICRD-213974 ends

		/*
		 * Validate for client errors
		 */
		Collection<ErrorVO> errors = null;
		errors = validateForm(maintainULDForm, logonAttributes.getCompanyCode());
		/*
		 * if(errors == null || errors.size() == 0) { ErrorVO error =
		 * splitUldNumber(maintainULDForm); if(error != null) {
		 * errors.add(error); } }
		 */
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			maintainULDForm
					.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = LISTULD_FAILURE;
			return;
		}
		ULDVO uldVO = null;

		try {
			
			maintainULDForm.setFlagVar(maintainULDForm.getUldNumber()
					.toUpperCase());
			uldVO = new ULDDefaultsDelegate().findULDDetails(logonAttributes
					.getCompanyCode(), maintainULDForm.getUldNumber()
					.toUpperCase());
			
			
			
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}

		if (errors != null && errors.size() > 0) {
			// added by nisha starts on 04Jan2008 ....commented by nisha ...this check not required
			/*for(ErrorVO error:errors){
				if("uld.defaults.nosuchuldexists".equals(error.getErrorCode())){
					String uldOwnerAirlineCode="";
					String arlDtl="";
					try{
			    	 arlDtl=new ULDDefaultsDelegate().findOwnerCode(logonAttributes.getCompanyCode() ,
			    			maintainULDForm.getUldNumber().
			    			substring(maintainULDForm.getUldNumber().length()-2),maintainULDForm.getUldNumber().
			    			substring(maintainULDForm.getUldNumber().length()-3));
					}catch(BusinessDelegateException businessDelegateException) {
						 handleDelegateException(businessDelegateException);
					}
			    	log.log(Log.INFO,"airlineDetails****"+arlDtl);
			    	String arldtlArray[] = arlDtl.split("~");
			    	boolean isTwoAlpha=false;
			    	
						if ("2".equals(arldtlArray[0])) {
							isTwoAlpha=true;
						} else {
							isTwoAlpha=false;
						}
				    	if(isTwoAlpha){
				    		uldOwnerAirlineCode=maintainULDForm.getUldNumber().substring(maintainULDForm.getUldNumber().length()-2);
				    	}else{
				    		uldOwnerAirlineCode=maintainULDForm.getUldNumber().substring(maintainULDForm.getUldNumber().length()-3);
				    	}
				    	log.log(Log.INFO,"inside ListULD validate"+maintainULDForm.getOperationalAirlineCode());
				    	if("".equals(maintainULDForm.getOperationalAirlineCode())){
				    		
				    	}else{
				    		if(!(uldOwnerAirlineCode.equals(maintainULDForm.getOperationalAirlineCode()))){
							log.log(Log.INFO,"uldairlinecode"+uldOwnerAirlineCode);
							error =new ErrorVO("uld.defaults.maintainuld.err.cannotcreateuld");
							error.setErrorData(new Object[]{uldOwnerAirlineCode});
							maintainULDForm
							.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
					
							invocationContext.target = LISTULD_FAILURE;
							invocationContext.addError(error);
							return;
						}
				    	}
				    		
				}
			}*/
			// added by nisha ends on 04Jan2008
			//to add a newly created uld for pessimistic locking 
			
				ULDVO uldVONull = new ULDVO();
				
				uldVONull.setUldNumber(maintainULDForm.getFlagVar());
				uldVONull.setOperationalFlag(ULDVO.OPERATION_FLAG_INSERT);
				
				maintainULDSession.setULDVO(uldVONull);
				
				
				
				
			invocationContext.addAllError(errors);
			maintainULDForm
					.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			
			invocationContext.target = LISTULD_FAILURE;
			return;
		}
		
		
		if(uldVO == null){
			ULDVO uldVONull = new ULDVO();
			uldVONull.setUldNumber(maintainULDForm.getFlagVar());
			maintainULDSession.setULDVO(uldVONull);
			log.log(Log.FINE, "!!!!!added to session null condition ");
			
			}else
			{

		loadMaintainULDForm(uldVO, maintainULDForm);
		// uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_UPDATE);
		log.log(Log.FINE, "!!!!!added to sessio old ULD ");
		maintainULDSession.setULDVO(uldVO);
			}

		maintainULDForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		maintainULDForm.setStatusFlag("list");
		invocationContext.target = LISTULD_SUCCESS;
	}

	/**
	 * @param maintainULDForm
	 * @param companyCode
	 * @return errors
	 */

	private Collection<ErrorVO> validateForm(MaintainULDForm maintainULDForm,
			String companyCode) {
		log.entering("ListULDDetailsCommand", "validateForm");

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if (maintainULDForm.getUldNumber() == null
				|| maintainULDForm.getUldNumber().trim().length() == 0) {
			error = new ErrorVO("uld.defaults.uldnumbermandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		} else {
			if (maintainULDForm.getUldNumber() != null
					&& maintainULDForm.getUldNumber().trim().length() > 0) {
				try {
					// new ULDDelegate().validateULD(companyCode,
					// maintainULDForm.getUldNumber());
					new ULDDefaultsDelegate().validateULDFormat(companyCode,
							maintainULDForm.getUldNumber().toUpperCase());
					/*
					 * error = splitUldNumber(maintainULDForm); if(error !=
					 * null) { errors.add(error); }
					 */
				
					
				} catch (BusinessDelegateException businessDelegateException) {
					/*
					 * error = new ErrorVO("uld.defaults.invaliduldformat", new
					 * Object[]{maintainUldForm.getUldNumber()});
					 * errors.add(error);
					 */
					errors = handleDelegateException(businessDelegateException);
				}
			}
		}
		log.exiting("ListULDDetailsCommand", "validateForm");
		return errors;
	}

	/**
	 * @param uldVO
	 * @param maintainULDForm
	 * @return
	 */

	private void loadMaintainULDForm(ULDVO uldVO,
			MaintainULDForm maintainULDForm) {

		if (uldVO.getUldContour() != null) {
			maintainULDForm.setUldContour(uldVO.getUldContour());
		}

		if(uldVO.getTareWeight() != null) {
			maintainULDForm.setDisplayTareWeightUnit(uldVO.getTareWeight().getDisplayUnit());
		}
		
		maintainULDForm.setDisplayTareWeight(Double.toString(uldVO.getTareWeight()!=null?uldVO.getTareWeight().getRoundedDisplayValue():0.0));
		maintainULDForm.setTareWtMeasure(uldVO.getTareWeight());
		/*if(uldTypeVO.getTareWtUnit() != null) {
			maintainULDForm.setDisplayTareWeightUnit(
										uldTypeVO.getTareWtUnit());
		}*/
		maintainULDForm.setDisplayStructuralWeight(Double.toString(uldVO.getStructuralWeight()!=null?uldVO.getStructuralWeight().getRoundedDisplayValue():0.0));
		maintainULDForm.setStructWeightMeasure(uldVO.getStructuralWeight());
		if(uldVO.getStructuralWeight() != null) {
			maintainULDForm.setDisplayStructuralWeightUnit(uldVO.getStructuralWeight().getDisplayUnit());
		}else {
			maintainULDForm.setDisplayStructuralWeightUnit("");
		}
		
		maintainULDForm.setBaseLengthMeasure(uldVO.getBaseLength());
		maintainULDForm.setDisplayBaseLength(Double.toString(uldVO.getBaseLength()!=null?uldVO.getBaseLength().getRoundedDisplayValue():0.0));
		maintainULDForm.setBaseWidthMeasure(uldVO.getBaseWidth());
		maintainULDForm.setDisplayBaseWidth(Double.toString(uldVO.getBaseWidth()!=null?uldVO.getBaseWidth().getRoundedDisplayValue():0.0));
		maintainULDForm.setBaseHeightMeasure(uldVO.getBaseHeight());
		maintainULDForm.setDisplayBaseHeight(Double.toString(uldVO.getBaseHeight()!=null?uldVO.getBaseHeight().getRoundedDisplayValue():0.0));
		
		maintainULDForm.setDisplayDimensionUnit(uldVO.getBaseLength()!=null?uldVO.getBaseLength().getDisplayUnit() : "");

		if (uldVO.getOperationalAirlineCode() != null) {
			maintainULDForm.setOperationalAirlineCode(uldVO
					.getOperationalAirlineCode());
		}
		if (uldVO.getOperationalOwnerAirlineCode() != null) {
			maintainULDForm.setOperationalOwnerAirlineCode(uldVO
					.getOperationalOwnerAirlineCode());
		}
		if (uldVO.getCurrentStation() != null) {
			maintainULDForm.setCurrentStation(uldVO.getCurrentStation());
		}
		if (uldVO.getOverallStatus() != null) {
			maintainULDForm.setOverallStatus(uldVO.getOverallStatus());
		}
		if (uldVO.getCleanlinessStatus() != null) {
			maintainULDForm.setCleanlinessStatus(uldVO.getCleanlinessStatus());
		}
		if (uldVO.getOwnerStation() != null) {
			maintainULDForm.setOwnerStation(uldVO.getOwnerStation());
		}
		if (uldVO.getLocation() != null) {
			maintainULDForm.setLocation(uldVO.getLocation());
		}
		if (uldVO.getUldNature() != null) {
			maintainULDForm.setUldNature(uldVO.getUldNature());
		}
		if (uldVO.getFacilityType() != null) {
			maintainULDForm.setFacilityType(uldVO.getFacilityType());
		}
		if (uldVO.getDamageStatus() != null) {
			maintainULDForm.setDamageStatus(uldVO.getDamageStatus());
		}
		if (uldVO.getVendor() != null) {
			maintainULDForm.setVendor(uldVO.getVendor());
		}
		maintainULDForm.setTransitStatus(uldVO.getTransitStatus());
		if (uldVO.getManufacturer() != null) {
			maintainULDForm.setManufacturer(uldVO.getManufacturer());
		}
		if (uldVO.getUldSerialNumber() != null) {
			maintainULDForm.setUldSerialNumber(uldVO.getUldSerialNumber());
		}
		if (uldVO.getPurchaseDate() != null) {
			maintainULDForm.setPurchaseDate(TimeConvertor.toStringFormat(uldVO
					.getPurchaseDate().toCalendar(),
					TimeConvertor.CALENDAR_DATE_FORMAT));
		}
		if (uldVO.getPurchaseInvoiceNumber() != null) {
			maintainULDForm.setPurchaseInvoiceNumber(uldVO
					.getPurchaseInvoiceNumber());
		}

        log.log(Log.FINE, "uldVO.getDisplayUldPrice()---", uldVO.getDisplayUldPrice());
		//BigDecimal price=new BigDecimal(uldVO.getDisplayUldPrice()).setScale(2,RoundingMode.UP);
		//log.log(Log.FINE,"price------"+price);
		maintainULDForm.setUldPrice(String.valueOf(uldVO.getDisplayUldPrice()));

		if (uldVO.getUldPriceUnit() != null) {
			maintainULDForm.setUldPriceUnit(uldVO.getUldPriceUnit());
		}

		/*maintainULDForm.setIataReplacementCost(Double.toString(uldVO
				.getDisplayIataReplacementCost()));*/

		
		log.log(Log.FINE, "uldVO.getDisplayIataReplacementCost()---", uldVO.getDisplayIataReplacementCost());
		//BigDecimal cost=new BigDecimal(uldVO.getDisplayIataReplacementCost()).setScale(2,RoundingMode.UP);
		//log.log(Log.FINE,"cost------"+cost);
		maintainULDForm.setIataReplacementCost(String.valueOf(uldVO.getDisplayIataReplacementCost()));

		if (uldVO.getDisplayIataReplacementCostUnit() != null) {
			maintainULDForm.setIataReplacementCostUnit(uldVO
					.getDisplayIataReplacementCostUnit());
		}

		log
				.log(Log.FINE, "uldVO.getCurrentValue()---", uldVO.getCurrentValue());
		//BigDecimal value=new BigDecimal(uldVO.getCurrentValue()).setScale(2,RoundingMode.UP);
		//log.log(Log.FINE,"value------"+value);
		maintainULDForm.setCurrentValue(String.valueOf(uldVO.getDisplayCurrentValue())); // modified by a-5505 for the bug ICRD-126614
		/*maintainULDForm.setCurrentValue(Double.toString(uldVO
				.getDisplayCurrentValue()));*/

		if (uldVO.getCurrentValueUnit() != null) {
			maintainULDForm.setCurrentValueUnit(uldVO.getCurrentValueUnit());
		}
        // Added by Preet for bug ULD 220 on 26 th Feb --ends
		
		if (uldVO.getRemarks() != null) {
			maintainULDForm.setRemarks(uldVO.getRemarks());
		}
		
		if (uldVO.getManufactureDate() != null) {
			maintainULDForm.setManufactureDate(TimeConvertor.toStringFormat(uldVO
					.getManufactureDate().toCalendar(),
					TimeConvertor.CALENDAR_DATE_FORMAT));
		}
		maintainULDForm.setLifeSpan(uldVO.getLifeSpan()+"");
		if(uldVO.getTsoNumber()!=null){
			maintainULDForm.setTsoNumber(uldVO.getTsoNumber());
		}

	}

	/**
	 * Method to populate the collection of onetime parameters to be obtained
	 * 
	 * @return parameterTypes
	 */
	private Collection<String> getOneTimeParameterTypes() {
		log.entering("ScreenLoadCommand", "getOneTimeParameterTypes");
		ArrayList<String> parameterTypes = new ArrayList<String>();

		parameterTypes.add(WEIGHTUNIT_ONETIME);
		parameterTypes.add(DIMENTIONUNIT_ONETIME);
		parameterTypes.add(OPERATSTATUS_ONETIME);
		parameterTypes.add(ULDNATURE_ONETIME);		
		parameterTypes.add(CLEANSTATUS_ONETIME);
		parameterTypes.add(DAMAGESTATUS_ONETIME);
		parameterTypes.add(CONTOUR_ONETIME);
		parameterTypes.add(FACILITYTYPE_ONETIME);

		log.exiting("ScreenLoadCommand", "getOneTimeParameterTypes");
		return parameterTypes;
	}

	/**
	 * The method to obtain the onetime values. The method will call the
	 * sharedDefaults delegate and returns the map of requested onetimes
	 * 
	 * @return oneTimeValues
	 */
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
		log.entering("ScreenLoadCommand", "getOneTimeValues");
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			log.log(Log.FINE, "****inside try**************************",
					getOneTimeParameterTypes());
			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(),
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "*****in the exception");			
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ScreenLoadCommand", "getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>) oneTimeValues;
	}

}
