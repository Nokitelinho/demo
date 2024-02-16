package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.dispatchroutingcarrierconfig;

/**
 * ListCommand
 * 
 * @author A-4452
 * 
 */

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RoutingCarrierFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RoutingCarrierVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRARoutingCarrierSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRARoutingCarrierForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;




public class ListCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");
	
	
	private static final String CLASS_NAME = "ListCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.dispatchroutingcarrierconfig";
	
	private static final String SCREENID_DSNPOPUP = "mailtracking.mra.defaults.dsnselectpopup";
	
	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";
	
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		MRARoutingCarrierSession  dsnRoutingSession = 
			(MRARoutingCarrierSession)getScreenSession(MODULE_NAME, SCREEN_ID);
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		MRARoutingCarrierForm mraRoutingCarrierForm=(MRARoutingCarrierForm)invocationContext.screenModel;
		
		Collection<RoutingCarrierVO> routingCarrierVOs=new ArrayList<RoutingCarrierVO>();
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		RoutingCarrierFilterVO routingCarrierFilterVO=new RoutingCarrierFilterVO();		
		routingCarrierFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		//Added by A-4809 for Bug ICMN-3309
		errors = validateForm(mraRoutingCarrierForm, errors);
		if(errors !=null && errors.size()>0){
    		invocationContext.addAllError(errors);
    		invocationContext.target = LIST_FAILURE;
    		return;  
    	}
		if(!("").equals(mraRoutingCarrierForm.getOriginCity())){			
			routingCarrierFilterVO.setOriginCity(mraRoutingCarrierForm.getOriginCity());
		}
		if(!("").equals(mraRoutingCarrierForm.getDestCity())){
			routingCarrierFilterVO.setDestCity(mraRoutingCarrierForm.getDestCity());
		}
		if(!("").equals(mraRoutingCarrierForm.getCarrier())){
			routingCarrierFilterVO.setCarrier(mraRoutingCarrierForm.getCarrier());
		}
		if(!("").equals(mraRoutingCarrierForm.getValidFromDate())){
			routingCarrierFilterVO.setValidFromDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(mraRoutingCarrierForm.getValidFromDate()));
		}
		if(!("").equals(mraRoutingCarrierForm.getValidToDate())){
			routingCarrierFilterVO.setValidFromTo(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(mraRoutingCarrierForm.getValidToDate()));
		}
		 try{
			 routingCarrierVOs=mailTrackingMRADelegate.findRoutingCarrierDetails(routingCarrierFilterVO);
		 }catch(BusinessDelegateException businessDelegateException){
	    		log.log(Log.FINE,"inside try...caught businessDelegateException");
	        	//businessDelegateException.printStackTrace();
	        	handleDelegateException(businessDelegateException);
		}  
		 log.log(Log.FINE, "routingCarrierVOs....................",
				routingCarrierVOs);
		if(routingCarrierVOs!= null && routingCarrierVOs.size()> 0 ){	
			 
			 dsnRoutingSession.setRoutingCarrierVOs(routingCarrierVOs);
			 dsnRoutingSession.setRoutingCarrierFilterVO(routingCarrierFilterVO);			
					 
		 }else{
			 mraRoutingCarrierForm.setCheckResult("Y");
		 }
		
		
		 invocationContext.target = LIST_SUCCESS;
		
		log.exiting(CLASS_NAME, "execute");
	}
      

	/**
	 * @author A-4809
	 * to validate origin and destination
	 * @param mraRoutingCarrierForm
	 * @param errors
	 * @return
	 */
	private Collection<ErrorVO> validateForm(MRARoutingCarrierForm mraRoutingCarrierForm,
			Collection<ErrorVO> errors) {
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		Collection<ErrorVO> errs=null;
		ErrorVO errorVO = null;
		if(!("").equals(mraRoutingCarrierForm.getOriginCity())){
			Collection<String> originColl = new ArrayList<String>();
			originColl.add(mraRoutingCarrierForm.getOriginCity());
			errs = validateCityCodes(logonAttributes.getCompanyCode(),originColl);
				if(errs !=null && errs.size()>0){
					errorVO= new ErrorVO("mailtracking.mra.defaults.despatchrouting.err.invalidorigin");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errorVOs.add(errorVO);
				}
		}		
		if(!("").equals(mraRoutingCarrierForm.getDestCity())){
			Collection<String> destinationColl = new ArrayList<String>();
			destinationColl.add(mraRoutingCarrierForm.getDestCity());
			errs = validateCityCodes(logonAttributes.getCompanyCode(),destinationColl);
			if(errs !=null && errs.size()>0){
				errorVO= new ErrorVO("mailtracking.mra.defaults.despatchrouting.err.invaliddestination");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errorVOs.add(errorVO);
			}
		}
		if(!("").equals(mraRoutingCarrierForm.getCarrier())){
			Collection<String> carrierColl = new ArrayList<String>();
			carrierColl.add(mraRoutingCarrierForm.getCarrier());
			errs = validateAirportCodes(logonAttributes.getCompanyCode(),carrierColl);
			if(errs !=null && errs.size()>0){
				errorVO= new ErrorVO("mailtracking.mra.defaults.despatchrouting.err.invalidcarrier");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errorVOs.add(errorVO);
			}
		}  
		
		return errorVOs;     
		
	}
	/**
	 * @author A-4809
	 * to find valid city codes
	 * @param companyCode
	 * @param cityCode
	 * @return
	 */
	private Collection<ErrorVO> validateCityCodes(String companyCode,
			Collection<String> cityCode) {
		Collection<ErrorVO> errorVO = null;
			try {

				AreaDelegate areaDelegate = new AreaDelegate();	
				 areaDelegate.validateCityCodes(companyCode, cityCode);
			} catch (BusinessDelegateException businessDelegateException) {
				errorVO = handleDelegateException(businessDelegateException);
			}  
		return errorVO;
	}
	
	/**
	 * @author A-4809
	 * to validate carrier code
	 * @param companyCode
	 * @param ownSectorfrm
	 * @return
	 */
	private Collection<ErrorVO> validateAirportCodes(String companyCode,
			Collection<String> carrier) {
		Collection<ErrorVO> errorVO = null;
		for(String carrierCode : carrier){  
			try {

				AirlineDelegate delegate = new AirlineDelegate();	
				delegate.validateAlphaCode(companyCode, carrierCode);
			} catch (BusinessDelegateException businessDelegateException) {
				errorVO = handleDelegateException(businessDelegateException);				
			}
		}
		return errorVO;
	}
	
}
