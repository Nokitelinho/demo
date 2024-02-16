/*
 * NavigateMaintainULDCommand.java Created on Aug 1, 2005
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

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * This command class is invoked for viewing the detailed information of
 * selected ulds
 *
 * @author A-2001
 */
public class NavigateMaintainULDCommand extends SaveULDCommand {
	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Maintain Uld Discripency");


	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.maintainuld";
	private static final String NAVIGATION_SUCCESS = "navigation_success";
	private static final String NAVIGATION_FAILURE = "navigation_failure";
	private static final String MANUFACTURER_ONETIME = "uld.defaults.manufacturer";

    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

    	MaintainULDSession maintainULDSession =
    		(MaintainULDSession)getScreenSession(MODULE,SCREENID);
    	MaintainULDForm maintainULDForm = (MaintainULDForm) invocationContext.screenModel;

    	int displayPage = Integer.parseInt(maintainULDForm.getDisplayPage());
    	int currentPage = Integer.parseInt(maintainULDForm.getCurrentPage());
    	if(maintainULDSession.getUldNosForNavigation() != null &&
    			maintainULDSession.getUldNosForNavigation().size() > 0) {
	    	String currentUldNumber = maintainULDSession.getUldNosForNavigation().get(currentPage-1);
	    	ULDVO uldVO = maintainULDSession.getULDMultipleVOs().get(currentUldNumber);
	    	Collection<ErrorVO> errors =
				validateForm(maintainULDForm,logonAttributes.getCompanyCode());
	    	if(errors != null &&
					errors.size() > 0 ) {
	    		maintainULDForm.setDisplayPage(Integer.toString(currentPage));
				invocationContext.addAllError(errors);
				invocationContext.target = NAVIGATION_FAILURE;
				return;
			}
//			validate manufacturer
			if(!("").equals(maintainULDForm.getManufacturer()) &&
					maintainULDForm.getManufacturer()!=null &&
					maintainULDForm.getManufacturer().trim().length()> 0){

				HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
				Collection<String> manufacturers = new ArrayList<String>();
				int val = -1;
				boolean isPresent = true;
				if (oneTimeValues.keySet() != null && oneTimeValues.keySet().size() > 0) {
				      for(String manufacturer:oneTimeValues.keySet()) {
				    	  Collection<OneTimeVO> col = oneTimeValues.get(manufacturer);

				    	  for(OneTimeVO vo:col){
				    		  if(!("").equals(vo.getFieldValue())){
				    			  val++;

				    			  log
										.log(
												Log.FINE,
												"\n maintainULDForm.getManufacturer() ",
												maintainULDForm.getManufacturer());
								log.log(Log.FINE, "\n vo.getFieldValue() ", vo.getFieldValue());
								manufacturers.add(vo.getFieldValue());
				    			 if((maintainULDForm.getManufacturer()).equals(vo.getFieldValue())){
				    				 isPresent = true;

				    				 break;
				    			 }else{
				    				 isPresent = false;

				    			 }
				    		  }
				    	  }if(isPresent){
				    		  break;
				    	  }
				      }if(!isPresent){

		    			ErrorVO err=new ErrorVO(
				                    "uld.defaults.invalidmanufacturer",null);
				        errors.add(err);

			    	  }
				}

			}
//			for checking the location with facility type
			boolean isValidFacilityCode=true;
			if(maintainULDForm.getLocation()!=null && maintainULDForm.getLocation().length()>0){
				if(!(ULDVO.NO_LOCATION.equals(maintainULDForm.getLocation())&& ULDVO.NO_LOCATION.equals(maintainULDForm.getFacilityType()))){
						isValidFacilityCode=false;
					Collection<ULDAirportLocationVO> uldAirportLocationVOs= null;
					ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
					Collection<ErrorVO> error = new ArrayList<ErrorVO>();
					try {
						uldAirportLocationVOs = uldDefaultsDelegate.listULDAirportLocation
						(logonAttributes.getCompanyCode(),maintainULDForm.getCurrentStation().toUpperCase()
								,maintainULDForm.getFacilityType().toUpperCase());
					}  catch(BusinessDelegateException businessDelegateException)
					{						
						error = handleDelegateException(businessDelegateException);
						if(error != null && error.size() > 0){
							maintainULDForm.setDisplayPage(Integer.toString(currentPage));
			        		invocationContext.addAllError(error);
			        		invocationContext.target =NAVIGATION_FAILURE;
			        		return;
			        	}
					}
					for(ULDAirportLocationVO uldAirportLocationVO:uldAirportLocationVOs){
						if(uldAirportLocationVO.getFacilityCode()!=null &&
								uldAirportLocationVO.getFacilityCode().equals(maintainULDForm.getLocation().toUpperCase())){
							isValidFacilityCode=true;
							break;
						}
					}
				}
			}
			if(!isValidFacilityCode){
				ErrorVO err=new ErrorVO(
		                    "uld.defaults.maintainuld.msg.err.invalidfacilitycode",null);
		        errors.add(err);
				}


			AirlineValidationVO ownerVO = null;
			AirlineValidationVO operationVO = null;
			boolean hasOwnerBeCreared = false;
			if(maintainULDForm.getOwnerAirlineCode() == null
    				|| maintainULDForm.getOwnerAirlineCode().trim().length() == 0) {
				maintainULDForm.setOwnerAirlineCode(uldVO.getOwnerAirlineCode());
				hasOwnerBeCreared = true;
			}
			if(maintainULDForm.getOwnerAirlineCode() != null
					&& maintainULDForm.getOwnerAirlineCode().trim().length() > 0) {

						Collection<ErrorVO> errorsOwnerAirline = null;
						try {
							ownerVO = new AirlineDelegate().validateAlphaCode(
									logonAttributes.getCompanyCode(),
									maintainULDForm.getOwnerAirlineCode());
						}
						catch(BusinessDelegateException businessDelegateException) {

							errorsOwnerAirline =
								handleDelegateException(businessDelegateException);
		       			}
						if(errorsOwnerAirline != null &&
								errorsOwnerAirline.size() > 0) {
							for(ErrorVO errorAirline : (Collection<ErrorVO>)errorsOwnerAirline) {
								errorAirline.setErrorData(new Object[]{
										maintainULDForm.getOwnerAirlineCode()
								});
							}
							errors.addAll(errorsOwnerAirline);
							maintainULDForm.setDisplayPage(Integer.toString(currentPage));
							invocationContext.addAllError(errors);
							invocationContext.target = NAVIGATION_FAILURE;
							return;
						}

			}
			if(maintainULDForm.getOperationalAirlineCode() != null
					&& maintainULDForm.getOperationalAirlineCode().trim().length() > 0) {

						Collection<ErrorVO> errorsOperationalAirline = null;
						try {
							operationVO = new AirlineDelegate().validateAlphaCode(
									logonAttributes.getCompanyCode(),
									maintainULDForm.getOperationalAirlineCode());
						}
						catch(BusinessDelegateException businessDelegateException) {
							/*ErrorVO error = new ErrorVO("uld.defaults.invalidairline",
									new Object[]{maintainULDForm.getOperationalAirlineCode()});
							errors.add(error);*/
							errorsOperationalAirline =
								handleDelegateException(businessDelegateException);
		       			}
						if(errorsOperationalAirline != null &&
								errorsOperationalAirline.size() > 0) {
							for(ErrorVO errorAirline :
								(Collection<ErrorVO>)errorsOperationalAirline) {
								errorAirline.setErrorData(new Object[]{
										maintainULDForm.getOperationalAirlineCode()
								});
							}
							errors.addAll(errorsOperationalAirline);
						}

			}
			if(hasOwnerBeCreared) {
				maintainULDForm.setOwnerAirlineCode("");
			}
			if(errors != null &&
				errors.size() > 0 ) {
				maintainULDForm.setDisplayPage(Integer.toString(currentPage));
				invocationContext.addAllError(errors);
				invocationContext.target = NAVIGATION_FAILURE;
				return;
			}

			if(isUldUpdated(uldVO,maintainULDForm)) {
				uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_UPDATE);
			}
			loadUldFromForm(uldVO,maintainULDForm,logonAttributes.getCompanyCode());
			uldVO.setLastUpdateUser(logonAttributes.getUserId());
			uldVO.setLastUpdateTime(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
			if(ownerVO != null) {
				int ownerAirline = ownerVO.getAirlineIdentifier();
				uldVO.setOwnerAirlineIdentifier(ownerAirline);
			}

			if(operationVO != null) {
				int operationAirline = operationVO.getAirlineIdentifier();
				uldVO.setOperationalAirlineIdentifier(operationAirline);
			}

	    	String displayUldNumber = maintainULDSession.getUldNosForNavigation().get(displayPage-1);
	    	log.log(Log.FINE, "*************************CURRENT_PAGE========",
					currentPage);
			log.log(Log.FINE, "*************************CURRENT_ULD========",
					currentUldNumber);
			log.log(Log.FINE, "*************************DISPLAY_PAGE========",
					displayPage);
			log.log(Log.FINE, "*************************DISPLAY_ULD========",
					displayUldNumber);
			ULDVO newUldVO = null;
	    	if(maintainULDSession.getULDMultipleVOs().get(displayUldNumber) == null) {
	    		try {
	    			newUldVO = new ULDDefaultsDelegate().findULDDetails(
	    					 logonAttributes.getCompanyCode(),
	    					 displayUldNumber) ;
	    		}
	    		catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    		}

	    	}
	    	else {
	    		newUldVO = maintainULDSession.getULDMultipleVOs().get(displayUldNumber);
	    	}
	    	log.log(Log.FINE, "*************************NEWULD========",
					newUldVO);
			if(errors != null &&
					errors.size() > 0 ) {
	    		maintainULDForm.setDisplayPage(Integer.toString(currentPage));
				invocationContext.addAllError(errors);
				invocationContext.target = NAVIGATION_FAILURE;
				return;
			}
	    	newUldVO.setUldNumber(displayUldNumber);
	    	loadMaintainULDForm(newUldVO,maintainULDForm);
	    	HashMap<String,ULDVO> uldMultipleVOs = maintainULDSession.getULDMultipleVOs();
	    	newUldVO.setLastUpdateUser(logonAttributes.getUserId());
	    	newUldVO.setLastUpdateTime(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));

	    	//newUldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_UPDATE);
	    	uldMultipleVOs.put(displayUldNumber,newUldVO);
	    	maintainULDForm.setCurrentPage(Integer.toString(displayPage));
    	}
      	maintainULDForm.setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	invocationContext.target = NAVIGATION_SUCCESS;

    }

    private void loadMaintainULDForm(ULDVO uldVO, MaintainULDForm maintainULDForm) {

		maintainULDForm.setUldNumber(uldVO.getUldNumber());
		if(uldVO.getUldContour() != null) {
			maintainULDForm.setUldContour(uldVO.getUldContour());
		}
		else {
			maintainULDForm.setUldContour("");
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

		if(uldVO.getBaseHeight() != null) {
			maintainULDForm.setDisplayDimensionUnit(uldVO.getBaseHeight().getDisplayUnit());
		}else {
			maintainULDForm.setDisplayDimensionUnit("");
		}

		if(uldVO.getOperationalAirlineCode() != null) {
			maintainULDForm.setOperationalAirlineCode(uldVO.getOperationalAirlineCode());
		}
		else {
			maintainULDForm.setOperationalAirlineCode("");
		}

		if(uldVO.getCurrentStation() != null) {
			maintainULDForm.setCurrentStation(uldVO.getCurrentStation());
		}
		else {
			maintainULDForm.setCurrentStation("");
		}

		if(uldVO.getOverallStatus() != null) {
			maintainULDForm.setOverallStatus(uldVO.getOverallStatus());
		}
		else {
			maintainULDForm.setOverallStatus("O");
		}

		if(uldVO.getCleanlinessStatus() != null) {
			maintainULDForm.setCleanlinessStatus(uldVO.getCleanlinessStatus());
		}
		else {
			maintainULDForm.setCleanlinessStatus("C");
		}

		if(uldVO.getOwnerStation() != null) {
			maintainULDForm.setOwnerStation(uldVO.getOwnerStation());
		}
		else {
			maintainULDForm.setOwnerStation("");
		}
		if(uldVO.getLocation() != null) {
			maintainULDForm.setLocation(uldVO.getLocation());
		}
		else {
			maintainULDForm.setLocation("");
		}
		if(uldVO.getUldNature() != null) {
			maintainULDForm.setUldNature(uldVO.getUldNature());
		}
		else {
			maintainULDForm.setUldNature("");
		}

		if(uldVO.getFacilityType() != null) {
			maintainULDForm.setFacilityType(uldVO.getFacilityType());
		}
		else {
			maintainULDForm.setFacilityType("");
		}

		if(uldVO.getDamageStatus() != null) {
			maintainULDForm.setDamageStatus(uldVO.getDamageStatus());
		}
		else {
			maintainULDForm.setDamageStatus("N");
		}

		if(uldVO.getVendor() != null) {
			maintainULDForm.setVendor(uldVO.getVendor());
		}
		else {
			maintainULDForm.setVendor("");
		}
		maintainULDForm.setTransitStatus(uldVO.getTransitStatus());
		if(uldVO.getManufacturer() != null) {
			maintainULDForm.setManufacturer(uldVO.getManufacturer());
		}
		else {
			maintainULDForm.setManufacturer("");
		}

		if(uldVO.getUldSerialNumber() != null) {
			maintainULDForm.setUldSerialNumber(uldVO.getUldSerialNumber());
		}
		else {
			maintainULDForm.setUldSerialNumber("");
		}

		if(uldVO.getPurchaseDate() != null) {
			maintainULDForm.setPurchaseDate(
				TimeConvertor.toStringFormat(
				uldVO.getPurchaseDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT));
		}
		else {
			maintainULDForm.setPurchaseDate("");
		}

		if(uldVO.getPurchaseInvoiceNumber() != null) {
			maintainULDForm.setPurchaseInvoiceNumber(uldVO.getPurchaseInvoiceNumber());
		}
		else {
			maintainULDForm.setPurchaseInvoiceNumber("");
		}

		maintainULDForm.setUldPrice(Double.toString(uldVO.getDisplayUldPrice()));

		if(uldVO.getUldPriceUnit() != null) {
			maintainULDForm.setUldPriceUnit(uldVO.getUldPriceUnit());
		}
		else {
			maintainULDForm.setUldPriceUnit("");
		}

		maintainULDForm.setIataReplacementCost(
							Double.toString(uldVO.getDisplayIataReplacementCost()));

		if(uldVO.getDisplayIataReplacementCostUnit() != null) {
			maintainULDForm.setIataReplacementCostUnit(uldVO.getDisplayIataReplacementCostUnit());
		}
		else {
			maintainULDForm.setIataReplacementCostUnit("");
		}
		maintainULDForm.setCurrentValue(Double.toString(uldVO.getDisplayCurrentValue()));


		if(uldVO.getCurrentValueUnit() != null) {
			maintainULDForm.setCurrentValueUnit(uldVO.getCurrentValueUnit());
		}
		else {
			maintainULDForm.setCurrentValueUnit("");
		}
		if(uldVO.getRemarks() != null) {
			maintainULDForm.setRemarks(uldVO.getRemarks());
		}
		else {
			maintainULDForm.setRemarks("");
		}
	}

    private Collection<ErrorVO> validateForm(
    					MaintainULDForm maintainULDForm, String companyCode) {
		ErrorVO error = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();


		if(maintainULDForm.getDisplayTareWeight()!= null
				&& maintainULDForm.getDisplayTareWeight().trim().length() > 0) {
			if(Double.parseDouble(maintainULDForm.getDisplayTareWeight()) <= 0) {
				error = new ErrorVO("uld.defaults.tareweightlessthanzero");
				errors.add(error);
			}
		}

		if(maintainULDForm.getDisplayStructuralWeight()!= null
				&& maintainULDForm.getDisplayStructuralWeight().trim().length() > 0) {
			if(Double.parseDouble(maintainULDForm.getDisplayStructuralWeight()) <= 0) {
				error = new ErrorVO("uld.defaults.structuralweightlessthanzero");
				errors.add(error);
			}
		}

		if(maintainULDForm.getDisplayBaseLength() != null &&
				maintainULDForm.getDisplayBaseLength().trim().length() > 0) {
			if(Double.parseDouble(maintainULDForm.getDisplayBaseLength()) <= 0) {
				error = new ErrorVO("uld.defaults.baselengthzero");
				errors.add(error);
			}
		}

		if(maintainULDForm.getDisplayBaseWidth() != null
				&& maintainULDForm.getDisplayBaseWidth().trim().length() > 0) {
			if(Double.parseDouble(maintainULDForm.getDisplayBaseWidth()) <= 0) {
				error = new ErrorVO("uld.defaults.basewidthzero");
				errors.add(error);
			}
		}

		if( maintainULDForm.getDisplayBaseHeight() != null &&
				maintainULDForm.getDisplayBaseHeight().trim().length() > 0) {
			if(Double.parseDouble(maintainULDForm.getDisplayBaseHeight()) <= 0) {
				error = new ErrorVO("uld.defaults.baseheightzero");
				errors.add(error);
			}
		}

		if(maintainULDForm.getDisplayTareWeight() != null
				&& maintainULDForm.getDisplayTareWeight().trim().length() > 0) {
			if(Double.parseDouble(maintainULDForm.getDisplayTareWeight()) >
					Double.parseDouble(maintainULDForm.getDisplayStructuralWeight())) {
				error = new ErrorVO("uld.defaults.tarewtgreaterthanstructural");
				errors.add(error);

			}
		}
		if(maintainULDForm.getPurchaseDate() != null &&
				maintainULDForm.getPurchaseDate().trim().length() > 0) {
			if(DateUtilities.isValidDate(maintainULDForm.getPurchaseDate(),"dd-MMM-yyyy")) {
				LocalDate purchaseDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
				purchaseDate.setDate(maintainULDForm.getPurchaseDate());
				LocalDate currentdate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
				if(purchaseDate.isGreaterThan(currentdate)) {
					error = new ErrorVO("uld.defaults.purchasedategreaterthancurrent");
					errors.add(error);
				}
			}
			else {
				error = new ErrorVO("uld.defaults.invaliddateformat",
						new Object[]{maintainULDForm.getPurchaseDate()});
				errors.add(error);
			}
		}


		if(maintainULDForm.getCurrentStation() != null
			&& maintainULDForm.getCurrentStation().trim().length() > 0) {
			Collection<ErrorVO> errorsCurrentStation = null;
			try {
					new AreaDelegate().validateAirportCode(
							companyCode,maintainULDForm.getCurrentStation());
			}
			catch(BusinessDelegateException businessDelegateException) {
				/*error = new ErrorVO("uld.defaults.invalidairport",
						new Object[]{maintainULDForm.getCurrentStation()});
				errors.add(error);*/
				errorsCurrentStation = handleDelegateException(businessDelegateException);
   			}
			if(errorsCurrentStation != null &&
					errorsCurrentStation.size() > 0) {
				for(ErrorVO errorStation : (Collection<ErrorVO>)errorsCurrentStation) {
					errorStation.setErrorData(new Object[]{
							maintainULDForm.getCurrentStation()
					});
				}
				errors.addAll(errorsCurrentStation);
			}
		}

		if(maintainULDForm.getOwnerStation() != null
			&& maintainULDForm.getOwnerStation().trim().length() > 0) {
			Collection<ErrorVO> errorsOwnerStation = null;
			try {
				new AreaDelegate().validateAirportCode(
						companyCode,maintainULDForm.getOwnerStation());
			}
			catch(BusinessDelegateException businessDelegateException) {

				errorsOwnerStation = handleDelegateException(businessDelegateException);
   			}
			if(errorsOwnerStation != null &&
					errorsOwnerStation.size() > 0) {
				for(ErrorVO errorStation : (Collection<ErrorVO>)errorsOwnerStation) {
					errorStation.setErrorData(new Object[]{
							maintainULDForm.getOwnerStation()
					});
				}
				errors.addAll(errorsOwnerStation);
			}
		}
		else {
			error = new ErrorVO("uld.defaults.maintainuld.currentairportmandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if((maintainULDForm.getManufacturer() ==null ||
				maintainULDForm.getManufacturer().trim().length() == 0)
				&&(maintainULDForm.getUldSerialNumber() != null &&
						maintainULDForm.getUldSerialNumber().trim().length() > 0)) {
			error = new ErrorVO("uld.defaults.manufacturermandatory");
			errors.add(error);
		}
		return errors;
	}

    private void loadUldFromForm(ULDVO uldVO,MaintainULDForm maintainULDForm,String companyCode) {

    	uldVO.setUldNumber(maintainULDForm.getUldNumber().toUpperCase());
    	uldVO.setCompanyCode(companyCode);
		uldVO.setCleanlinessStatus(maintainULDForm.getCleanlinessStatus());
		uldVO.setCurrentStation(maintainULDForm.getCurrentStation().toUpperCase());
		if(maintainULDForm.getCurrentValue()!= null
				&& maintainULDForm.getCurrentValue().trim().length() > 0) {
			uldVO.setDisplayCurrentValue(Double.parseDouble(
											maintainULDForm.getCurrentValue()));
		}
		uldVO.setCurrentValueUnit(maintainULDForm.getCurrentValueUnit());
		uldVO.setDamageStatus(maintainULDForm.getDamageStatus());
		/*if(maintainULDForm.getDisplayBaseHeight()!= null
				&& maintainULDForm.getDisplayBaseHeight().trim().length() > 0) {*/
		uldVO.setBaseHeight(maintainULDForm.getBaseHeightMeasure());
    	//}
		/*if(maintainULDForm.getDisplayBaseLength()!= null
				&& maintainULDForm.getDisplayBaseLength().trim().length() > 0) {*/
			uldVO.setBaseLength(maintainULDForm.getBaseLengthMeasure());
		//}
		/*if(maintainULDForm.getDisplayBaseWidth()!= null
				&& maintainULDForm.getDisplayBaseWidth().trim().length() > 0) {*/
			uldVO.setBaseWidth(maintainULDForm.getBaseWidthMeasure());
		//}
		//uldVO.setDisplayDimensionUnit(maintainULDForm.getDisplayDimensionUnit());
		/*if(maintainULDForm.getDisplayStructuralWeight()!= null
				&& maintainULDForm.getDisplayStructuralWeight().trim().length() > 0) {*/
			uldVO.setStructuralWeight(maintainULDForm.getStructWeightMeasure());
		//}
		//uldVO.setDisplayStructuralWeightUnit( maintainULDForm.getDisplayStructuralWeightUnit());
		if(maintainULDForm.getTareWtMeasure()!= null) {
			uldVO.setTareWeight(maintainULDForm.getTareWtMeasure());
		}
		//uldVO.setDisplayTareWeightUnit(maintainULDForm.getDisplayStructuralWeightUnit());
		if(maintainULDForm.getIataReplacementCost()!= null
				&& maintainULDForm.getIataReplacementCost().trim().length() > 0) {
			uldVO.setDisplayIataReplacementCost(Double.parseDouble(
									maintainULDForm.getIataReplacementCost()));
		}
		uldVO.setDisplayIataReplacementCostUnit(maintainULDForm.getIataReplacementCostUnit());
		uldVO.setLocation(maintainULDForm.getLocation().toUpperCase());
		uldVO.setUldNature(maintainULDForm.getUldNature().toUpperCase());
		uldVO.setFacilityType(maintainULDForm.getFacilityType().toUpperCase());
		uldVO.setManufacturer(maintainULDForm.getManufacturer());
		uldVO.setOverallStatus(maintainULDForm.getOverallStatus());
		uldVO.setOwnerStation(maintainULDForm.getOwnerStation().toUpperCase());
		if(maintainULDForm.getPurchaseDate()!= null &&
				maintainULDForm.getPurchaseDate().trim().length() > 0) {
			LocalDate purchaseDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
			uldVO.setPurchaseDate(purchaseDate.setDate(maintainULDForm.getPurchaseDate()));
		}
		uldVO.setPurchaseInvoiceNumber(
					maintainULDForm.getPurchaseInvoiceNumber().toUpperCase());
		uldVO.setUldContour(maintainULDForm.getUldContour().toUpperCase());
		if(maintainULDForm.getUldPrice()!= null
				&& maintainULDForm.getUldPrice().trim().length() > 0) {
		   uldVO.setDisplayUldPrice(Double.parseDouble(maintainULDForm.getUldPrice()));
		}
		uldVO.setUldPriceUnit(maintainULDForm.getUldPriceUnit());
		uldVO.setUldSerialNumber(
						maintainULDForm.getUldSerialNumber().toUpperCase());
		uldVO.setVendor(maintainULDForm.getVendor().toUpperCase());
		uldVO.setOperationalAirlineCode(
				maintainULDForm.getOperationalAirlineCode().toUpperCase());
		uldVO.setUldNumber(maintainULDForm.getUldNumber().toUpperCase());
		uldVO.setRemarks(maintainULDForm.getRemarks());


	}

   /**
	 * The method to obtain the onetime values.
	 * The method will call the sharedDefaults delegate
	 * and returns the map of requested onetimes
	 * @return oneTimeValues
	 */
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
		log.entering("FindManufacturerLovCommand","getOneTimeValues");
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
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(),
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}


		log.exiting("FindManufacturerLovCommand","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}

	/**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("FindManufacturerLovCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	parameterTypes.add(MANUFACTURER_ONETIME);
    	log.exiting("FindManufacturerLovCommand","getOneTimeParameterTypes");
    	return parameterTypes;
    }

}
