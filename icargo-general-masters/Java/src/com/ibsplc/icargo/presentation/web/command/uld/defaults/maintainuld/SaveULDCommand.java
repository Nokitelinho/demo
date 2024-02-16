/*
 * SaveULDCommand.java Created on Aug 1, 2005
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
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListULDSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainULDSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListDamageReportSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListRepairReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * This command class is used to save the details of the specified ULD
 *
 * @author A-1347
 */
public class SaveULDCommand extends BaseCommand {
	
	
	@Override
	public boolean breakOnInvocationFailure() {		
		return true;
	}

	private Log log = LogFactory.getLogger("uld.defaults");
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.maintainuld";

	private static final String MODULE_LISTULD = "uld.defaults";

	private static final String SCREENID_LISTULD =
		"uld.defaults.listuld";

	private static final String MODULE_DAMAGE = "uld.defaults";

	private static final String SCREENID_DAMAGE =
		"uld.defaults.listdamagereport";

	private static final String MODULE_LISTREPAIR = "uld.defaults";

	private static final String SCREENID_LISTREPAIR =
		"uld.defaults.listrepairreport";

	private static final String SCREENID_ULDERRORLOG =
		"uld.defaults.ulderrorlog";

	private static final String SCREENID_SCMERRORLOG =
		"uld.defaults.scmulderrorlog";
	//added for scm reconcile
	private static final String PAGE_URL = "fromScmUldReconcile";

	private static final String SAVE_SCMERRORLOG="save_scmerrorlog";

	private static final String SAVEULD_SUCCESS = "saveuld_success";
	private static final String SAVEULD_FAILURE = "saveuld_failure";
	private static final String SAVE_LISTCREATE = "save_listcreate";
	private static final String SAVE_LISTDETAIL = "save_listdetail";
	private static final String SAVE_LISTREPAIR = "save_listrepair";
	private static final String SAVE_LISTDAMAGE = "save_listdamage";
	private static final String SAVE_LOANBORROW = "save_loanborrow";
	private static final String SAVE_ULDERRORLOG = "save_ulderrorlog";
	private static final String MANUFACTURER_ONETIME = "uld.defaults.manufacturer";
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
   public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	MaintainULDForm maintainULDForm = (MaintainULDForm)invocationContext.screenModel;
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MaintainULDSession maintainULDSession =
			(MaintainULDSession)getScreenSession(MODULE,SCREENID);
		ListULDSession listULDSession =
	      	  (ListULDSession)getScreenSession(MODULE_LISTULD,SCREENID_LISTULD);
    	ListRepairReportSession listRepairReportSession =
      	  (ListRepairReportSession)getScreenSession(MODULE_LISTREPAIR,SCREENID_LISTREPAIR);
      	ListDamageReportSession listDamageReportSession =
        	  (ListDamageReportSession)getScreenSession(MODULE_DAMAGE,SCREENID_DAMAGE);
      	ULDErrorLogSession uldErrorLogSession =
			(ULDErrorLogSession)getScreenSession(MODULE,SCREENID_ULDERRORLOG);
      	SCMULDErrorLogSession scmSession=getScreenSession(MODULE,SCREENID_SCMERRORLOG);

       if(maintainULDSession.getULDMultipleVOs()!=null || maintainULDSession.getULDVO()!=null){
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		boolean hasOwnerBeCreared = false;
    	if((maintainULDForm.getUldNumber() != null
    		&& maintainULDForm.getUldNumber().trim().length() > 0)
    		|| (maintainULDSession.getUldNumbersSaved() != null
					&& maintainULDSession.getUldNumbersSaved().size() > 0)) {
	    		ULDVO uldVO = null;
				if(maintainULDSession.getULDMultipleVOs() != null) {
					int currentPage = Integer.parseInt(maintainULDForm.getCurrentPage());
	    	    	String currentUldNumber = maintainULDSession.getUldNosForNavigation().get(currentPage-1);
	    	    	uldVO = maintainULDSession.getULDMultipleVOs().get(currentUldNumber);
				}
				else {
					uldVO = maintainULDSession.getULDVO();
				}

				if(maintainULDForm.getOwnerAirlineCode() == null
	    				|| maintainULDForm.getOwnerAirlineCode().trim().length() == 0) {
					maintainULDForm.setOwnerAirlineCode(uldVO.getOwnerAirlineCode());
					hasOwnerBeCreared = true;
				}
				errors = validateForm(maintainULDForm,logonAttributes.getCompanyCode());
    			log.log(Log.FINE, "\n errors ", errors);
				Collection<String> airlineCodes = new ArrayList<String>();
    			Map<String,AirlineValidationVO> airlineMap = null;
    			if(maintainULDForm.getOwnerAirlineCode() != null
    				&& maintainULDForm.getOwnerAirlineCode().trim().length() > 0) {
    				airlineCodes.add(maintainULDForm.getOwnerAirlineCode().toUpperCase());
    			}
    			if(maintainULDForm.getOperationalAirlineCode() != null
    					&& maintainULDForm.getOperationalAirlineCode().trim().length() > 0) {
    				if(!airlineCodes.contains(maintainULDForm.getOperationalAirlineCode().toUpperCase())) {
    					airlineCodes.add(maintainULDForm.getOperationalAirlineCode().toUpperCase());
    				}
    			}
    			if(maintainULDForm.getOperationalOwnerAirlineCode() != null
    					&& maintainULDForm.getOperationalOwnerAirlineCode().trim().length() > 0) {
    				if(!airlineCodes.contains(maintainULDForm.getOperationalOwnerAirlineCode().toUpperCase())) {
    					airlineCodes.add(maintainULDForm.getOperationalOwnerAirlineCode().toUpperCase());
    				}
    			}
    			if(airlineCodes.size() > 0) {
    				Collection<ErrorVO> errorsAirline = null;
					try {

						airlineMap = new AirlineDelegate().validateAlphaCodes(
								logonAttributes.getCompanyCode(),
								airlineCodes);
					}
					catch(BusinessDelegateException businessDelegateException) {

						errorsAirline = handleDelegateException(businessDelegateException);
	       			}
					if(errorsAirline != null &&
							errorsAirline.size() > 0) {


						errors.addAll(errorsAirline);
					}
					else {

						if(maintainULDForm.getOperationalOwnerAirlineCode() != null
			    				&& maintainULDForm.getOperationalOwnerAirlineCode().trim().length() > 0) {
							AirlineValidationVO airlineVO = airlineMap.get(
								maintainULDForm.getOperationalOwnerAirlineCode().toUpperCase());
							uldVO.setOwnerAirlineIdentifier(airlineVO.getAirlineIdentifier());
						}

						if(maintainULDForm.getOperationalAirlineCode() != null
			    				&& maintainULDForm.getOperationalAirlineCode().trim().length() > 0) {
							AirlineValidationVO airlineVO = airlineMap.get(
								maintainULDForm.getOperationalAirlineCode().toUpperCase());
							uldVO.setOperationalAirlineIdentifier(airlineVO.getAirlineIdentifier());
						}

					}

    			}
    			Collection<ErrorVO> currEerrors = null;
    			if(maintainULDForm.getUldPriceUnit()!=null && maintainULDForm.getUldPriceUnit().trim().length()> 0 ){
	  				
    				currEerrors=validateCurrency(getApplicationSession().getLogonVO().getCompanyCode(),
    						maintainULDForm);    				   				    				
    			}
    			 
    			if(maintainULDForm.getIataReplacementCostUnit()!=null && maintainULDForm.getIataReplacementCostUnit().trim().length()> 0 ){
    				
    				currEerrors=validateCurrency(getApplicationSession().getLogonVO().getCompanyCode(),
    						maintainULDForm);
       			}
    			if(maintainULDForm.getStatusFlag() != null &&
    					!"createNewUld".equals(maintainULDForm.getStatusFlag())){
    				if(maintainULDForm.getCurrentValueUnit()!= null && !("").equals(maintainULDForm.getCurrentValueUnit())
        					&& (maintainULDForm.getCurrentValueUnit().trim().length()> 0 )){
        				currEerrors=validateCurrency(getApplicationSession().getLogonVO().getCompanyCode(),
        						maintainULDForm);
        			}
    			}
    			
    			if(hasOwnerBeCreared) {

    				maintainULDForm.setOwnerAirlineCode("");
    			}
    			if(currEerrors!=null && currEerrors.size()>0){
    				errors.addAll(currEerrors);
    			}
    			if(errors != null &&
    				errors.size() > 0 ) {
    				log.log(Log.FINE, "\n Airline delegate4 " );
    				invocationContext.addAllError(errors);
    				invocationContext.target = SAVEULD_FAILURE;
    				return;
    			}

    			/*
    			 * Obtain the logonAttributes
    			 */
    			if(!(ULDVO.OPERATION_FLAG_INSERT.equals(uldVO.getOperationalFlag()))) {
	    			if(isUldUpdated(uldVO,maintainULDForm)) {
						uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_UPDATE);
					}
    			}
    			loadUldFromForm(uldVO,maintainULDForm,logonAttributes.getCompanyCode());
    			uldVO.setLastUpdateUser(logonAttributes.getUserId());
    			
    			//uldVO.setLastUpdateTime(new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,true));
    			
    			ArrayList<String> uldNumbers = null;
    			log.log(Log.FINE, "\n Airline delegate5 " );

//    			validate manufacturer
    			if(!("").equals(uldVO.getManufacturer()) &&
    					uldVO.getManufacturer()!=null &&
    					uldVO.getManufacturer().trim().length()> 0){

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

    				    			  log.log(Log.FINE,
											"\n uldVO.getManufacturer() ",
											uldVO.getManufacturer());
									log.log(Log.FINE, "\n vo.getFieldValue() ",
											vo.getFieldValue());
									manufacturers.add(vo.getFieldValue());
    				    			 if((uldVO.getManufacturer()).equalsIgnoreCase((vo.getFieldValue()))){
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
    			    		  invocationContext.addError(new ErrorVO(
    				                    "uld.defaults.invalidmanufacturer",null));
    							invocationContext.target = SAVEULD_FAILURE;
    		    				return;

    			    	  }
    				}

    			}

//    			for checking the location with facility type
				boolean isValidFacilityCode=true;
				if(uldVO.getLocation()!=null && uldVO.getLocation().length()>0){
				if(!(ULDVO.NO_LOCATION.equals(uldVO.getLocation())&& ULDVO.NO_LOCATION.equals(uldVO.getFacilityType()))){
					isValidFacilityCode=false;
				Collection<ULDAirportLocationVO> uldAirportLocationVOs= null;
		    	ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
		    	Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		    	try {
		    		uldAirportLocationVOs = uldDefaultsDelegate.listULDAirportLocation
		    		(logonAttributes.getCompanyCode(),uldVO.getCurrentStation().toUpperCase()
		    				,uldVO.getFacilityType().toUpperCase());
		    	}  catch(BusinessDelegateException businessDelegateException) {
		    		businessDelegateException.getMessage();
		    		error = handleDelegateException(businessDelegateException);
		    	}
		    	for(ULDAirportLocationVO uldAirportLocationVO:uldAirportLocationVOs){
		    		if(uldAirportLocationVO.getFacilityCode()!=null &&
		    				uldAirportLocationVO.getFacilityCode().equals(uldVO.getLocation())){
		    			isValidFacilityCode=true;
		    			break;
		    		}
		    	}
				}
				}
				if(isValidFacilityCode){
    			//code for finding whether uld is updated or not...
    			if(maintainULDSession.getULDMultipleVOs() != null) {

    				try {
    					log.log(Log.FINE, "\n uld  delegate 1" );
	    				new ULDDefaultsDelegate().saveULDs(new ArrayList<ULDVO>(maintainULDSession.getULDMultipleVOs().values()));
	    			}
	    			catch(BusinessDelegateException businessDelegateException) {
	    				log.log(Log.FINE, "\n \n **********error.getErrorCode()1" );
	    				errors = handleDelegateException(businessDelegateException);
	       			}
	    			if(errors != null &&
	        				errors.size() > 0 ) {
	        				for(ErrorVO error:errors){

	        				log
									.log(
											Log.FINE,
											"\n \n **********error.getErrorCode()****1",
											error.getErrorCode());

	        				}
	        				invocationContext.addAllError(errors);
	        				invocationContext.target = SAVEULD_FAILURE;
	        				return;
	        			}
    			}
    			else if(maintainULDForm.getUldNumber()!= null &&
    					maintainULDForm.getUldNumber().trim().length() >0){

    				if(ULDVO.OPERATION_FLAG_INSERT.equals(uldVO.getOperationalFlag())) {
    					uldVO.setTransitStatus("N");
    					uldVO.setOccupiedULDFlag("N");
    				}

    				if(ULDVO.OPERATION_FLAG_UPDATE.equals(uldVO.getOperationalFlag()) ||
    						ULDVO.OPERATION_FLAG_INSERT.equals(uldVO.getOperationalFlag())) {
	    				try {
	    					log.log(Log.FINE, "\n uld  delegate 2" );
	    					new ULDDefaultsDelegate().saveULD(uldVO);
		    			}
		    			catch(BusinessDelegateException businessDelegateException) {
		    				log.log(Log.FINE, "\n \n **********error.getErrorCode()2" );
		    				errors = handleDelegateException(businessDelegateException);
		    				log.log(Log.INFO, "errors", errors);
							log.log(Log.INFO, "Size(errors)", errors.size());
		       			}
    				}


    				if(errors != null &&
    	    				errors.size() > 0 ) {
    	    				for(ErrorVO error:errors){

    	    				log.log(Log.FINE, "\n \n error.getErrorCode()2",
									error.getErrorCode());

    	    				}
    	    				invocationContext.addAllError(errors);
    	    				invocationContext.target = SAVEULD_FAILURE;
    	    				return;
    	    			}
    			}
    			else if(maintainULDSession.getUldNumbersSaved() != null
    					&& maintainULDSession.getUldNumbersSaved().size() > 0) {
    				uldVO.setUldType(maintainULDForm.getUldType().toUpperCase());
    				uldVO.setTransitStatus("N");
    				log.log(Log.FINE, "maintainULDSession.getUldGroupCode()",
							maintainULDSession.getUldGroupCode());
					uldVO.setUldGroupCode(maintainULDSession.getUldGroupCode());
    				/* A-2412 for ULD group code ends */
	    			try {
	    				log.log(Log.FINE,
								"\n uld  delegate 3--!!!-@@@-isss---", uldVO.getOperationalFlag());
						//added by a-3278 for ULD917 on 02Mar09 
	    				//the OperationalFlag for Multipe ULDs are coming as null ,later these are setting as U.. hence the issue
	    				uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_INSERT);	 
	    				//a-3278 ends
	    				uldNumbers = (ArrayList<String>)new ULDDefaultsDelegate().createMultipleULDs(uldVO,
	    						maintainULDSession.getUldNumbersSaved());
	    			}
	    			catch(BusinessDelegateException businessDelegateException) {
	    				errors = handleDelegateException(businessDelegateException);
	       			}
    			}}
				else{
					invocationContext.addError(new ErrorVO(
		                    "uld.defaults.maintainuld.msg.err.invalidfacilitycode",null));
					invocationContext.target = SAVEULD_FAILURE;
    				return;
				}
    			if(uldNumbers != null) {
    				int size = uldNumbers.size();
    				StringBuffer alreadyExistingUlds = new StringBuffer("");
    			   	for(int i=0; i< size; i++) {

	    				if(("").equals(alreadyExistingUlds.toString())) {
	            			//alreadyGenerated = uldNo.toUpperCase();
	    					alreadyExistingUlds.append(uldNumbers.get(i).toUpperCase());
	            		}
	            		else {
	            			alreadyExistingUlds.append(" , ");
	            			alreadyExistingUlds.append(uldNumbers.get(i).toUpperCase());
	            		}

    				}
    			   	if(errors == null) {
    					errors = new ArrayList<ErrorVO>();
    				}
    			    if(!("").equals(alreadyExistingUlds.toString()) ) {
    			    	maintainULDSession.setUldNumbers(null);
    			    	 maintainULDSession.setUldNumbersSaved(null);  
	    			   	ErrorVO error = new ErrorVO("uld.defaults.uldalreadyexists"
	    						,new Object[]{
	    						logonAttributes.getCompanyCode(),alreadyExistingUlds.toString()});
	    				errors.add(error);

    			    }
    				 
    			}
    			if(errors != null &&
    				errors.size() > 0 ) {
    				for(ErrorVO error:errors){

    				log.log(Log.FINE,
							"\n \n **********error.getErrorCode()3****", error.getErrorCode());

    				}
    				invocationContext.addAllError(errors);
    				invocationContext.target = SAVEULD_FAILURE;
    				/* Added by A-2412 */
    				//maintainULDSession.setULDVO(null);
        			//maintainULDSession.setUldNumbersSaved(null);
        			//maintainULDSession.setULDMultipleVOs(null);
        	    	//maintainULDSession.setUldNosForNavigation(null);
        	    	// Addition ends 
    				return;
    			}
    			maintainULDSession.setULDVO(null);
    			maintainULDSession.setUldNumbersSaved(null);
    			maintainULDSession.setULDMultipleVOs(null);
    	    	maintainULDSession.setUldNosForNavigation(null);
    			clearForm(maintainULDForm);
    			maintainULDForm.setScreenStatusFlag(
    						ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    			 maintainULDForm.setStatusFlag("save");



    	}
    	else {
    		errors = new ArrayList<ErrorVO>();
    		ErrorVO error = new ErrorVO("uld.defaults.nouldspecified");
    		errors.add(error);
    		invocationContext.addAllError(errors);
    		invocationContext.target = SAVEULD_FAILURE;
    		return;
    	}
    	if(("listcreate").equals(maintainULDForm.getScreenloadstatus())) {
			listULDSession.setListStatus("noListForm");
			invocationContext.target = SAVE_LISTCREATE;
		}
		else if(("listdetail").equals(maintainULDForm.getScreenloadstatus())) {
			listULDSession.setListStatus("noListForm");
			invocationContext.target = SAVE_LISTDETAIL;
		}
		else if(("ListRepairReport").equals(maintainULDForm.getScreenloadstatus())) {
			listRepairReportSession.setScreenId("LISTREPAIR");
			invocationContext.target = SAVE_LISTREPAIR;
		}
		else if(("ListDamageReport").equals(maintainULDForm.getScreenloadstatus())) {
			listDamageReportSession.setScreenId("LISTDAMAGE");
			invocationContext.target = SAVE_LISTDAMAGE;
		}
		else if(("LoanBorrow").equals(maintainULDForm.getScreenloadstatus())) {

			invocationContext.target = SAVE_LOANBORROW;
		}
		else if(("fromulderrorlog").equals(maintainULDForm.getScreenloadstatus())) {
			Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			log
					.log(
							Log.FINE,
							"\n \n maintainULDSession.getULDFlightMessageReconcileDetailsVO()",
							maintainULDSession.getULDFlightMessageReconcileDetailsVO());
			try {
				log.log(Log.FINE, "\n reconcile  delegate " );
				new ULDDefaultsDelegate().reconcileUCMULDError(maintainULDSession.getULDFlightMessageReconcileDetailsVO());
			}
			catch(BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				error = handleDelegateException(businessDelegateException);
   			}
			uldErrorLogSession.setPageURL("frommaintainuld");
			 //Altered by A-7978 for ICRD-238863
			//maintainULDSession.removeAllAttributes();
			invocationContext.target = SAVE_ULDERRORLOG;
		}else if(PAGE_URL.equals(maintainULDForm.getScreenloadstatus())){
			Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			ULDSCMReconcileDetailsVO detailsVO=maintainULDSession.getSCMReconcileDetailsVO();
			Collection<ULDSCMReconcileDetailsVO> scmUldDetails=new ArrayList<ULDSCMReconcileDetailsVO>();
			scmUldDetails.add(detailsVO);
			log.log(Log.FINE, "Details VO for removing error---->", detailsVO);
			try{
				new ULDDefaultsDelegate().removeErrorCodeForULDsInSCM(scmUldDetails);
			}catch(BusinessDelegateException exception){
				exception.getMessage();
				error = handleDelegateException(exception);
			}
			maintainULDSession.removeAllAttributes();
			scmSession.setPageUrl("frommaintainuld");
			invocationContext.target = SAVE_SCMERRORLOG;
			return;

		}

		else {

	     	invocationContext.addError(new ErrorVO("uld.defaults.maintainuld.savedsuccessfully"));
	    	invocationContext.target = SAVEULD_SUCCESS;
//return    pls check this is needed or not?
	    	return;
		}
}
      /* 
       for(ErrorVO err:invocationContext.getErrors()){
    	   log.log(Log.FINE,"Second"+err.getErrorCode());
       }*/
        invocationContext.target = SAVEULD_SUCCESS;
    }

    /**
     *
     * @param uldVO
     * @param maintainULDForm
     * @param companyCode
     */
    private void loadUldFromForm(ULDVO uldVO,MaintainULDForm maintainULDForm, String companyCode) {

    	uldVO.setUldNumber(maintainULDForm.getUldNumber().toUpperCase());
		uldVO.setCompanyCode(companyCode);
		uldVO.setCleanlinessStatus(maintainULDForm.getCleanlinessStatus());
		uldVO.setCurrentStation(maintainULDForm.getCurrentStation().toUpperCase());
		if(maintainULDForm.getCurrentValue()!= null
				&& maintainULDForm.getCurrentValue().trim().length() > 0) {
			uldVO.setDisplayCurrentValue(Double.parseDouble(maintainULDForm.getCurrentValue()));
		}
		uldVO.setCurrentValueUnit(maintainULDForm.getCurrentValueUnit());
		uldVO.setDamageStatus(maintainULDForm.getDamageStatus());
		if(maintainULDForm.getDisplayBaseHeight()!= null
				&& maintainULDForm.getDisplayBaseHeight().trim().length() > 0) {
		uldVO.setBaseHeight(maintainULDForm.getBaseHeightMeasure());
    	}
		if(maintainULDForm.getDisplayBaseLength()!= null
				&& maintainULDForm.getDisplayBaseLength().trim().length() > 0) {
			uldVO.setBaseLength(maintainULDForm.getBaseLengthMeasure());
		}
		if(maintainULDForm.getDisplayBaseWidth()!= null
				&& maintainULDForm.getDisplayBaseWidth().trim().length() > 0) {
			uldVO.setBaseWidth(maintainULDForm.getBaseWidthMeasure());
		}
		//uldVO.setDisDimensionUnit(maintainULDForm.getDisplayDimensionUnit());
		if(maintainULDForm.getDisplayStructuralWeight()!= null
				&& maintainULDForm.getDisplayStructuralWeight().trim().length() > 0) {
			uldVO.setStructuralWeight(maintainULDForm.getStructWeightMeasure());
		}
		/*uldVO.setDisplayStructuralWeightUnit(
						maintainULDForm.getDisplayStructuralWeightUnit());*/
		if(maintainULDForm.getDisplayTareWeight()!= null
				&& maintainULDForm.getDisplayTareWeight().trim().length() > 0) {
			uldVO.setTareWeight(maintainULDForm.getTareWtMeasure());
		}
		//uldVO.setDisplayTareWeightUnit(maintainULDForm.getDisplayStructuralWeightUnit());
		if(maintainULDForm.getIataReplacementCost()!= null
				&& maintainULDForm.getIataReplacementCost().trim().length() > 0) {
			uldVO.setDisplayIataReplacementCost(
				Double.parseDouble(maintainULDForm.getIataReplacementCost()));
		}
		else{
			uldVO.setDisplayIataReplacementCost(0.0);
		}
		uldVO.setDisplayIataReplacementCostUnit(  
							maintainULDForm.getIataReplacementCostUnit());
		uldVO.setLocation(maintainULDForm.getLocation().toUpperCase());
		uldVO.setUldNature(maintainULDForm.getUldNature().toUpperCase());
		if(maintainULDForm.getFacilityType() != null && 
				maintainULDForm.getFacilityType().trim().length() > 0){
			uldVO.setFacilityType(maintainULDForm.getFacilityType().toUpperCase());
		}else if("".equals(maintainULDForm.getFacilityType())){  //Added by A-5202 as part of ICRD-35018
			uldVO.setFacilityType(maintainULDForm.getFacilityType());
		}
		uldVO.setManufacturer(maintainULDForm.getManufacturer());
		uldVO.setOverallStatus(maintainULDForm.getOverallStatus());
		uldVO.setOwnerStation(maintainULDForm.getOwnerStation().toUpperCase());
		if(maintainULDForm.getPurchaseDate()!= null &&
				maintainULDForm.getPurchaseDate().trim().length() > 0) {
			LocalDate purchaseDate =
				new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
			uldVO.setPurchaseDate(purchaseDate.setDate(
					maintainULDForm.getPurchaseDate()));
		}
		uldVO.setPurchaseInvoiceNumber(
				maintainULDForm.getPurchaseInvoiceNumber().toUpperCase());
		uldVO.setUldContour(maintainULDForm.getUldContour().toUpperCase());
		if(maintainULDForm.getUldPrice()!= null
				&& maintainULDForm.getUldPrice().trim().length() > 0) {
			uldVO.setDisplayUldPrice(Double.parseDouble(maintainULDForm.getUldPrice()));
		}else{
			uldVO.setDisplayUldPrice(0.00);
		}
		uldVO.setUldPriceUnit(maintainULDForm.getUldPriceUnit());
		uldVO.setUldSerialNumber(maintainULDForm.getUldSerialNumber().toUpperCase());
		uldVO.setVendor(maintainULDForm.getVendor().toUpperCase());
		uldVO.setOperationalAirlineCode(
				maintainULDForm.getOperationalAirlineCode().toUpperCase());
		uldVO.setOperationalOwnerAirlineCode(maintainULDForm.getOperationalOwnerAirlineCode().toUpperCase());
		uldVO.setUldNumber(maintainULDForm.getUldNumber().toUpperCase());
		uldVO.setRemarks(maintainULDForm.getRemarks());
		// Added by Preet for Air NZ 447 --starts
		if(maintainULDForm.getManufactureDate()!= null &&
				maintainULDForm.getManufactureDate().trim().length() > 0) {
			LocalDate manDate =
				new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
			uldVO.setManufactureDate(manDate.setDate(
					maintainULDForm.getManufactureDate()));			
		}
		// else part commented since QF ppl has asked not to default the
		// Manufacture date(as current date),if it is not explicitly given by
		// the user(bug 62497)
		/*
		 * else{ LocalDate currentdate = new
		 * LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
		 * uldVO.setManufactureDate(currentdate); }
		 */
		uldVO.setTsoNumber(maintainULDForm.getTsoNumber());		
		if(maintainULDForm.getLifeSpan()!=null && maintainULDForm.getLifeSpan().trim().length()>0){				
			uldVO.setLifeSpan(Integer.parseInt(maintainULDForm.getLifeSpan()));
		}else{			
			uldVO.setLifeSpan(0);			
		}
		// Added by Preet for Air NZ 447 --ends
	}

    /**
     *
     * @param maintainULDForm
     * @param companyCode
     * @return
     */
	private Collection<ErrorVO> validateForm(MaintainULDForm maintainULDForm, String companyCode) {
		ErrorVO error = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MaintainULDSession maintainULDSession =
			(MaintainULDSession)getScreenSession(MODULE,SCREENID);
		if(maintainULDSession.getUldNosForNavigation() == null) {
			if(maintainULDForm.getUldNumber()!= null &&
					maintainULDForm.getUldNumber().trim().length() >0) {
				try {

					new ULDDefaultsDelegate().validateULDFormat(companyCode,
									maintainULDForm.getUldNumber().toUpperCase());
				} catch (BusinessDelegateException businessDelegateException) {

					errors = handleDelegateException(businessDelegateException);
					businessDelegateException.getMessage();
				}
			}


		}
		if(maintainULDForm.getDisplayTareWeight()!= null
				&& maintainULDForm.getDisplayTareWeight().trim().length() > 0) {
			if(Double.parseDouble(maintainULDForm.getDisplayTareWeight()) <= 0) {

				error = new ErrorVO("uld.defaults.tareweightlessthanzero");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}
		
		if(("").equals(maintainULDForm.getUldPriceUnit()) || ("").equals(maintainULDForm.getIataReplacementCostUnit())){
			log.log(Log.FINE, "\n currency Blank" );
		 error = new ErrorVO("uld.defaults.currencyblank");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}else{
		if(maintainULDForm.getStatusFlag() != null &&
				!("createNewUld").equals(maintainULDForm.getStatusFlag())){
			if(("").equals(maintainULDForm.getCurrentValueUnit())) {
				error = new ErrorVO("uld.defaults.currencyblank");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			 }
		   }
		}
		if(("").equals(maintainULDForm.getDisplayTareWeight())){
			error = new ErrorVO("uld.defaults.tareweightblank");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		if(maintainULDForm.getDisplayStructuralWeight()!= null
				&& maintainULDForm.getDisplayStructuralWeight().trim().length() > 0) {
			if(Double.parseDouble(maintainULDForm.getDisplayStructuralWeight()) <= 0) {

				error = new ErrorVO("uld.defaults.structuralweightlessthanzero");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}
		if(("").equals(maintainULDForm.getDisplayStructuralWeight())){
			error = new ErrorVO("uld.defaults.structuralweightblank");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		
		if(maintainULDForm.getDisplayBaseLength()!= null
				&& maintainULDForm.getDisplayBaseLength().trim().length() > 0) {
			if(Double.parseDouble(maintainULDForm.getDisplayBaseLength()) <= 0) {

				error = new ErrorVO("uld.defaults.baselengthlessthanzero");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}
		// commented by nisha
		/* if(maintainULDForm.getDisplayStructuralWeight()!= null
					&& !(maintainULDForm.getDisplayStructuralWeight().equals("")))
					{
			 	    if(Double.parseDouble(maintainULDForm.getDisplayStructuralWeight()) <= 0) {
					 
						
					error = new ErrorVO("uld.defaults.structuralweightlessthanzero");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
			}*/
		 
		if(maintainULDForm.getDisplayStructuralWeight()!= null
				&& ("").equals(maintainULDForm.getDisplayStructuralWeight())) {
				error = new ErrorVO("uld.defaults.structuralweightisempty");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			
		}

		if(maintainULDForm.getDisplayTareWeight()!= null
				&& ("").equals(maintainULDForm.getDisplayTareWeight())) {
				error = new ErrorVO("uld.defaults.tareweightisempty");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			
		}
		if(maintainULDForm.getDisplayBaseLength()!= null
				&& ("").equals(maintainULDForm.getDisplayBaseLength())) {
				error = new ErrorVO("uld.defaults.baselengthisempty");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			
		}
		if(maintainULDForm.getDisplayBaseHeight()!= null
				&& ("").equals(maintainULDForm.getDisplayBaseHeight())) {
				error = new ErrorVO("uld.defaults.baseheightisempty");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			
		}
		if(maintainULDForm.getDisplayBaseWidth()!= null
				&& ("").equals(maintainULDForm.getDisplayBaseWidth())) {
				error = new ErrorVO("uld.defaults.basewidthisempty");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			
		}
		if(("").equals(maintainULDForm.getDisplayBaseLength())){
			error = new ErrorVO("uld.defaults.baselengthblank");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		if(maintainULDForm.getDisplayBaseWidth() != null
				&& maintainULDForm.getDisplayBaseWidth().trim().length() > 0) {
			if(Double.parseDouble(maintainULDForm.getDisplayBaseWidth()) <= 0) {

				error = new ErrorVO("uld.defaults.basewidthzero");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}
		if(("").equals(maintainULDForm.getDisplayBaseWidth())){
			error = new ErrorVO("uld.defaults.basewidthblank");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		if( maintainULDForm.getDisplayBaseHeight() != null &&
				maintainULDForm.getDisplayBaseHeight().trim().length() > 0) {
			if(Double.parseDouble(maintainULDForm.getDisplayBaseHeight()) <= 0) {

				error = new ErrorVO("uld.defaults.baseheightzero");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}
		if(("").equals(maintainULDForm.getDisplayBaseHeight())){
			error = new ErrorVO("uld.defaults.baseheightblank");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		if(maintainULDForm.getDisplayTareWeight() != null
				&& maintainULDForm.getDisplayTareWeight().trim().length() > 0
				&& (maintainULDForm.getDisplayStructuralWeight()!=null)
				&& !(("").equals(maintainULDForm.getDisplayStructuralWeight())))
		{
			if(Double.parseDouble(maintainULDForm.getDisplayTareWeight()) >
					Double.parseDouble(maintainULDForm.getDisplayStructuralWeight())) {

				error = new ErrorVO("uld.defaults.tarewtgreaterthanstructural");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
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
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
			}
			else {

				error = new ErrorVO("uld.defaults.invaliddateformat",
						new Object[]{maintainULDForm.getPurchaseDate()});
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}else{
			//Added by A-7359 for ICRD-233082 
			error = new ErrorVO("uld.defaults.purchasedatemandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			
		}
		Collection<String> airportCodes = new ArrayList<String>();

		if(maintainULDForm.getCurrentStation() != null
			&& maintainULDForm.getCurrentStation().trim().length() > 0) {
			airportCodes.add(maintainULDForm.getCurrentStation().toUpperCase());

		}
		else {
			error = new ErrorVO("uld.defaults.maintainuld.currentairportmandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		if(maintainULDForm.getOwnerStation() != null
			&& maintainULDForm.getOwnerStation().trim().length() > 0) {
			if(!airportCodes.contains(maintainULDForm.getOwnerStation().toUpperCase())) {
				airportCodes.add(maintainULDForm.getOwnerStation().toUpperCase());
			}

		}
		if(airportCodes.size() > 0) {
			Collection<ErrorVO> errorsOwnerStation = null;
			try {

				new AreaDelegate().validateAirportCodes(
						companyCode,airportCodes);
			}
			catch(BusinessDelegateException businessDelegateException) {

				errorsOwnerStation = handleDelegateException(businessDelegateException);
				businessDelegateException.getMessage();
   			}
			if(errorsOwnerStation != null &&
					errorsOwnerStation.size() > 0) {

				errors.addAll(errorsOwnerStation);
			}
		}

		if((("").equals(maintainULDForm.getManufacturer()) ||
				maintainULDForm.getManufacturer() ==null ||
				maintainULDForm.getManufacturer().trim().length() == 0)
				&&(maintainULDForm.getUldSerialNumber() != null &&
						maintainULDForm.getUldSerialNumber().trim().length() > 0)) {

			error = new ErrorVO("uld.defaults.manufacturermandatory");
			errors.add(error);
		}
		//Added by A-7359 for ICRD-233082 starts here
		if(("").equals(maintainULDForm.getUldPrice())||maintainULDForm.getUldPrice() ==null ||
				maintainULDForm.getUldPrice().trim().length() == 0){
			log.log(Log.FINE, "\n UldPrice Blank" );
		    error = new ErrorVO("uld.defaults.uldpricemandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		
		}
		//Added by A-7359 for ICRD-233082 ends here

		return errors;
	}

	/**
	 *
	 * @param maintainULDForm
	 */
	private void clearForm(MaintainULDForm maintainULDForm) { 
		maintainULDForm.setUldNumber("");
		maintainULDForm.setUldType("");
		maintainULDForm.setOperationalAirlineCode("");
		maintainULDForm.setUldContour("");
		maintainULDForm.setDisplayTareWeight("");
	//	maintainULDForm.setDisplayTareWeightUnit("");
		maintainULDForm.setDisplayStructuralWeight("");
		maintainULDForm.setDisplayStructuralWeightUnit("");
		maintainULDForm.setDisplayBaseLength("");
		maintainULDForm.setDisplayBaseWidth("");
		maintainULDForm.setDisplayBaseHeight("");
		maintainULDForm.setDisplayDimensionUnit("");
		maintainULDForm.setOwnerAirlineCode("");
		maintainULDForm.setCurrentStation("");
		maintainULDForm.setOwnerStation("");
		maintainULDForm.setLocation("");
		maintainULDForm.setUldNature("");
		maintainULDForm.setFacilityType("");
		maintainULDForm.setVendor("");
		maintainULDForm.setTransitStatus("");
		maintainULDForm.setManufacturer("");
		maintainULDForm.setUldSerialNumber("");
		maintainULDForm.setPurchaseDate("");
		maintainULDForm.setPurchaseInvoiceNumber("");
		maintainULDForm.setUldPrice("");
		maintainULDForm.setUldPriceUnit("");
		maintainULDForm.setIataReplacementCost("");
		maintainULDForm.setIataReplacementCostUnit("");
		maintainULDForm.setCurrentValue("");
		maintainULDForm.setCurrentValueUnit("");
		maintainULDForm.setTotalNoofUlds("");
		maintainULDForm.setRemarks("");
		maintainULDForm.setStructuralFlag("");
		maintainULDForm.setOverallStatus("O");
    	maintainULDForm.setDamageStatus("N");
    	maintainULDForm.setCleanlinessStatus("C");
    	maintainULDForm.setLifeSpan("");
    	maintainULDForm.setManufactureDate("");
    	maintainULDForm.setTsoNumber("");
		maintainULDForm.setCreateMultiple(false);

	}

	 public boolean isUldUpdated(ULDVO uldVO,
				MaintainULDForm maintainULDForm) {
 	  boolean isUpdated = false;
		if(uldVO.getUldContour() != null) {
			if(!uldVO.getUldContour().equals(maintainULDForm.getUldContour())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getUldContour() != null &&
				maintainULDForm.getUldContour().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}
		if(maintainULDForm.getDisplayTareWeight() != null &&
				maintainULDForm.getDisplayTareWeight().trim().length() > 0) {
			if(getDoubleValue(uldVO.getTareWeight(), ULDVO.DIS_VAL) != Double.parseDouble(maintainULDForm.getDisplayTareWeight())) {
				isUpdated = true;
				//return isUpdated;
			}

		}
		else {
			isUpdated = true;
			//return isUpdated;
		}

		/*if(maintainULDForm.getDisplayTareWeight() != null &&
				maintainULDForm.getDisplayTareWeight().trim().length() > 0) {
			if(uldVO.getDisplayTareWeight() != Double.parseDouble(maintainULDForm.getDisplayTareWeight())) {
				isUpdated = true;
				//return isUpdated;
			}

		}
		else {
			isUpdated = true;
			//return isUpdated;
		}*/

	/*	if(uldVO.getDisplayTareWeightUnit() != null) {
			if(!uldVO.getDisplayTareWeightUnit().equals(maintainULDForm.getDisplayTareWeightUnit())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getDisplayTareWeightUnit() != null &&
				maintainULDForm.getDisplayTareWeightUnit().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}*/

		if(maintainULDForm.getDisplayStructuralWeight() != null &&
				maintainULDForm.getDisplayStructuralWeight().trim().length() > 0) {
			if(getDoubleValue(uldVO.getStructuralWeight(), ULDVO.DIS_VAL) != Double.parseDouble(maintainULDForm.getDisplayStructuralWeight())) {
				isUpdated = true;
				//return isUpdated;
			}

		}
		else {
			isUpdated = true;
			//return isUpdated;
		}

		if(getUnitValue(uldVO.getStructuralWeight(), ULDVO.DIS_VAL) != null) {
			if(!getUnitValue(uldVO.getStructuralWeight(), ULDVO.DIS_VAL).equals(maintainULDForm.getDisplayStructuralWeightUnit())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getDisplayStructuralWeightUnit() != null &&
				maintainULDForm.getDisplayStructuralWeightUnit().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}

		if(maintainULDForm.getDisplayBaseLength() != null &&
				maintainULDForm.getDisplayBaseLength().trim().length() > 0) {
			if(getDoubleValue(uldVO.getBaseLength(), ULDVO.DIS_VAL) != Double.parseDouble(maintainULDForm.getDisplayBaseLength())) {
				isUpdated = true;
				//return isUpdated;
			}

		}
		else {
			isUpdated = true;
			//return isUpdated;
		}

		if(maintainULDForm.getDisplayBaseWidth() != null &&
				maintainULDForm.getDisplayBaseWidth().trim().length() > 0) {
			if(getDoubleValue(uldVO.getBaseWidth(), ULDVO.DIS_VAL) != Double.parseDouble(maintainULDForm.getDisplayBaseWidth())) {
				isUpdated = true;
				//return isUpdated;
			}

		}
		else {
			isUpdated = true;
			//return isUpdated;
		}

		if(maintainULDForm.getDisplayBaseHeight() != null &&
				maintainULDForm.getDisplayBaseHeight().trim().length() > 0) {
			if(getDoubleValue(uldVO.getBaseHeight(), ULDVO.DIS_VAL) != Double.parseDouble(maintainULDForm.getDisplayBaseHeight())) {
				isUpdated = true;
				//return isUpdated;
			}

		}
		else {
			isUpdated = true;
			//return isUpdated;
		}
		if(getUnitValue(uldVO.getBaseHeight(), ULDVO.DIS_VAL) != null) {
			if(!getUnitValue(uldVO.getBaseHeight(), ULDVO.DIS_VAL).equals(maintainULDForm.getDisplayDimensionUnit())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getDisplayDimensionUnit() != null &&
				maintainULDForm.getDisplayDimensionUnit().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}

		if(uldVO.getOperationalAirlineCode() != null) {
			if(!uldVO.getOperationalAirlineCode().equals(maintainULDForm.getOperationalAirlineCode())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getOperationalAirlineCode() != null &&
				maintainULDForm.getOperationalAirlineCode().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}

		if(uldVO.getCurrentStation() != null) {
			if(!uldVO.getCurrentStation().equals(maintainULDForm.getCurrentStation())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getCurrentStation() != null &&
				maintainULDForm.getCurrentStation().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}

		if(uldVO.getOverallStatus() != null) {
			if(!uldVO.getOverallStatus().equals(maintainULDForm.getOverallStatus())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getOverallStatus() != null &&
				maintainULDForm.getOverallStatus().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}
		if(uldVO.getCleanlinessStatus() != null) {
			if(!uldVO.getCleanlinessStatus().equals(maintainULDForm.getCleanlinessStatus())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getCleanlinessStatus() != null &&
				maintainULDForm.getCleanlinessStatus().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}

		if(uldVO.getOwnerStation() != null) {
			if(!uldVO.getOwnerStation().equals(maintainULDForm.getOwnerStation())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getOwnerStation() != null &&
				maintainULDForm.getOwnerStation().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}

		if(uldVO.getLocation() != null) {
			if(!uldVO.getLocation().equals(maintainULDForm.getLocation())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getLocation() != null &&
				maintainULDForm.getLocation().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}
		if(uldVO.getUldNature() != null) {
			if(!uldVO.getUldNature().equals(maintainULDForm.getUldNature())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getUldNature() != null &&
				maintainULDForm.getUldNature().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}
		if(uldVO.getFacilityType() != null) {
			if(!uldVO.getFacilityType().equals(maintainULDForm.getFacilityType())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getFacilityType() != null &&
				maintainULDForm.getFacilityType().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}

		if(uldVO.getDamageStatus() != null) {
			if(!uldVO.getDamageStatus().equals(maintainULDForm.getDamageStatus())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getDamageStatus() != null &&
				maintainULDForm.getDamageStatus().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}
		if(uldVO.getVendor() != null) {
			if(!uldVO.getVendor().equals(maintainULDForm.getVendor())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getVendor() != null &&
				maintainULDForm.getVendor().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}

		if(uldVO.getManufacturer() != null) {
			if(!uldVO.getManufacturer().equals(maintainULDForm.getManufacturer())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getManufacturer() != null &&
				maintainULDForm.getManufacturer().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}

		if(uldVO.getUldSerialNumber() != null) {
			if(!uldVO.getUldSerialNumber().equals(maintainULDForm.getUldSerialNumber())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getUldSerialNumber() != null &&
				maintainULDForm.getUldSerialNumber().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}

		if(uldVO.getPurchaseDate() != null) {
			if(!(TimeConvertor.toStringFormat(
					uldVO.getPurchaseDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT).
					equals(maintainULDForm.getPurchaseDate()))) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getPurchaseDate() != null &&
				maintainULDForm.getPurchaseDate().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}

		if(uldVO.getPurchaseInvoiceNumber() != null) {
			if(!uldVO.getPurchaseInvoiceNumber().equals(maintainULDForm.getPurchaseInvoiceNumber())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getPurchaseInvoiceNumber() != null &&
				maintainULDForm.getPurchaseInvoiceNumber().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}

		if(maintainULDForm.getUldPrice() != null &&
				maintainULDForm.getUldPrice().trim().length() > 0) {
			if(uldVO.getDisplayUldPrice() != Double.parseDouble(maintainULDForm.getUldPrice())) {
				isUpdated = true;
				//return isUpdated;
			}

		}
		else {
			isUpdated = true;
			//return isUpdated;
		}

		if(uldVO.getUldPriceUnit() != null) {
			if(!uldVO.getUldPriceUnit().equals(maintainULDForm.getUldPriceUnit())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getUldPriceUnit() != null &&
				maintainULDForm.getUldPriceUnit().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}

		if(maintainULDForm.getIataReplacementCost() != null &&
				maintainULDForm.getIataReplacementCost().trim().length() > 0) {
			if(uldVO.getDisplayIataReplacementCost() != Double.parseDouble(maintainULDForm.getIataReplacementCost())) {
				isUpdated = true;
				//return isUpdated;
			}

		}
		else {
			isUpdated = true;
			//return isUpdated;
		}

		if(uldVO.getDisplayIataReplacementCostUnit() != null) {
			if(!uldVO.getDisplayIataReplacementCostUnit().equals(maintainULDForm.getIataReplacementCostUnit())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getIataReplacementCostUnit() != null &&
				maintainULDForm.getIataReplacementCostUnit().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}

		if(maintainULDForm.getCurrentValue() != null &&
				maintainULDForm.getCurrentValue().trim().length() > 0) {
			if(uldVO.getCurrentValue() != Double.parseDouble(maintainULDForm.getCurrentValue())) {
				isUpdated = true;
				//return isUpdated;
			}

		}
		else {
			isUpdated = true;
			//return isUpdated;
		}

		if(uldVO.getCurrentValueUnit() != null) {
			if(!uldVO.getCurrentValueUnit().equals(maintainULDForm.getCurrentValueUnit())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getCurrentValueUnit() != null &&
				maintainULDForm.getCurrentValueUnit().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}

		if(uldVO.getRemarks() != null) {
			if(!uldVO.getRemarks().equals(maintainULDForm.getRemarks())) {
				isUpdated = true;
				//return isUpdated;
			}
		}
		else if(maintainULDForm.getRemarks() != null &&
				maintainULDForm.getRemarks().trim().length() > 0 ) {
			isUpdated = true;
			//return isUpdated;
		}
		
		if(uldVO.getTsoNumber() != null) {
			if(!uldVO.getTsoNumber().equals(maintainULDForm.getTsoNumber())) {
				isUpdated = true;				
			}
		}
		else if(maintainULDForm.getTsoNumber() != null &&
				maintainULDForm.getTsoNumber().trim().length() > 0 ) {
			isUpdated = true;			
		}
		if(maintainULDForm.getLifeSpan() != null) 
		{

		if(!maintainULDForm.getLifeSpan().equals(maintainULDForm.getLifeSpan())) {
			isUpdated = true;			
		}
		}
		if(uldVO.getManufactureDate() != null) {
			if(!(TimeConvertor.toStringFormat(
					uldVO.getManufactureDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT).
					equals(maintainULDForm.getManufactureDate()))) {
				isUpdated = true;
			}
		}
		else if(maintainULDForm.getManufactureDate() != null &&
				maintainULDForm.getManufactureDate().trim().length() > 0 ) {
			isUpdated = true;
		}
		
		return isUpdated;

	}
	 
	 public Collection<ErrorVO> validateCurrency(
				String companyCode,MaintainULDForm form ) {
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			if(form.getUldPriceUnit()!=null && form.getUldPriceUnit().trim().length()> 0){
			try {
				
				new CurrencyDelegate().validateCurrency(
						companyCode,form.getUldPriceUnit().toUpperCase());
				
			} catch (BusinessDelegateException e) {
				errors=	handleDelegateException(e);
			}
			}
			if(form.getIataReplacementCostUnit()!=null && form.getIataReplacementCostUnit().trim().length()> 0){
			try {
				new CurrencyDelegate().validateCurrency(
						companyCode,form.getIataReplacementCostUnit().toUpperCase());
			} catch (BusinessDelegateException e) {
				errors=	handleDelegateException(e);
			}
			}
			if(form.getCurrentValueUnit()!=null && form.getCurrentValueUnit().trim().length()> 0){
			try {
				new CurrencyDelegate().validateCurrency(
						companyCode,form.getCurrentValueUnit().toUpperCase());
			} catch (BusinessDelegateException e) {
				errors=	handleDelegateException(e);
			}
			}
			return errors;
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
	    /**
	     * 
	     * @param measureObj
	     * @param unitType
	     * @return
	     */
	    public double getDoubleValue(Measure measureObj, String unitType){
	        Double d = 0.0;
	        if(measureObj !=null)
	       	{
	       	if("S".equalsIgnoreCase(unitType)){
	       		d=measureObj.getRoundedSystemValue();
	       	}else{
	       		d=measureObj.getRoundedDisplayValue();
	       	}
	       	}
	       	return d;
	       }
	    /**
	     * 
	     * @param measureObj
	     * @param unitType
	     * @return
	     */
       public String getUnitValue(Measure measureObj, String unitType){
           String s = null;
           if(measureObj !=null)
          	{
          	if("S".equalsIgnoreCase(unitType)){
          		s=measureObj.getSystemUnit();
          	}else{
          		s=measureObj.getDisplayUnit();
          					}
          	}
          	return s;
        }

}
