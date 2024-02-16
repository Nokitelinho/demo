package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.commodity.CommodityDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.FlightNumber;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.ScreenLoadCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	20-Sep-2018		:	Draft
 */
public class ScreenLoadCommand extends AbstractCommand{
	
	private Log log = LogFactory.getLogger("OPERATIONS MAILINBOUND");

	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private static final String MAILSTATUS = "mailtracking.defaults.mailarrivalstatus";
	private static final String CONTAINERTYPES = "mailtracking.defaults.containertype";
	private static final String MAIL_COMMODITY_SYS = "mailtracking.defaults.booking.commodity";
	  private static final String SYSPAR_DEFUNIT_WEIGHT = "mail.operations.defaultcaptureunit";
	private static final String STNPAR_DEFUNIT_VOL = "station.defaults.unit.volume";//added by A-8353 for ICRD-274933   
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
			CommandInvocationException {
		
		log.entering("ScreenloadMailinboundCommand","execute");
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute();
		MailinboundModel mailinboundModel =  
				(MailinboundModel) actionContext.getScreenModel();
		MailinboundFilter mailinboundFilter = 
				(MailinboundFilter)mailinboundModel.getMailinboundFilter();
		ResponseVO responseVO = new ResponseVO();
		ArrayList results = new ArrayList();
		
		mailinboundFilter.setPort(logonAttributes.getAirportCode());
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
		mailinboundFilter.setFromDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		mailinboundFilter.setToDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		mailinboundFilter.setFromTime("00:00");
		mailinboundFilter.setToTime("23:59");
		mailinboundFilter.setPa("");  
        FlightNumber flighnumber = new FlightNumber();
        if(logonAttributes.isGHAUser() && !logonAttributes.isOwnSalesAgent()) {
        	flighnumber.setCarrierCode(null);
        }
        else {
        	flighnumber.setCarrierCode(logonAttributes.getOwnAirlineCode());
        }
        
        mailinboundFilter.setFlightnumber(flighnumber);
		
		Map<String, Collection<OneTimeVO>> oneTimes = 
				findOneTimeDescription(logonAttributes.getCompanyCode());
		
		if(oneTimes!=null){
		    mailinboundFilter.setOneTimeValues((HashMap<String, Collection<OneTimeVO>>) oneTimes);   
	    }
		
		Collection<String> codes = new ArrayList<String>();
		codes.add(MAIL_COMMODITY_SYS);
		codes.add(SYSPAR_DEFUNIT_WEIGHT);
		codes.add("mail.operations.defaultoperatingcarriers");
		codes.add("operations.flthandling.enableatdcapturevalildationforimporthandling");
		codes.add("mail.operations.ignoretobeactionedflightvalidation");
		Map<String, String> sysResults = null;
		try {
			sysResults = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);  
		}
		if(sysResults != null && sysResults.size() > 0) {
    		 String commodityCode = ((String)sysResults.get(MAIL_COMMODITY_SYS));
    		 String defaultSysUnit = sysResults.get(SYSPAR_DEFUNIT_WEIGHT);
    		 String defaultOperatingCarrier = sysResults.get("mail.operations.defaultoperatingcarriers");
    		 String valildationforimporthandling=sysResults.get("operations.flthandling.enableatdcapturevalildationforimporthandling");
    		 String validationforTBA=sysResults.get("mail.operations.ignoretobeactionedflightvalidation");
    	        Collection<String> commodites = new ArrayList<String>();
    	    	if(commodityCode!=null && commodityCode.trim().length()>0) {
    				commodites.add(commodityCode);
    				Map<String,CommodityValidationVO> densityMap = null;
    				CommodityDelegate  commodityDelegate = new CommodityDelegate();
    				try {
    					densityMap = commodityDelegate.validateCommodityCodes(logonAttributes.getCompanyCode(), commodites);
    				} catch (BusinessDelegateException e) {
    				}
    				if(densityMap !=null && densityMap.size()>0){
    					CommodityValidationVO commodityValidationVO = densityMap.get(commodityCode);
    					mailinboundFilter.setDensity(String.valueOf(commodityValidationVO.getDensityFactor()));
    				}
    			}

    	    	if(validationforTBA!=null){
    	    		mailinboundFilter.setValidationforTBA(validationforTBA);
    			}
    	    	if(defaultSysUnit!=null){
    	    		mailinboundFilter.setDefWeightUnit(defaultSysUnit);
    			}
    	    	if(defaultOperatingCarrier!=null) {
    	    		// X means applicable for ALL airlines, so need to set value
	    			if(!"X".equals(defaultOperatingCarrier)) {
	    				mailinboundFilter.setOperatingReference(defaultOperatingCarrier);
	    			}	
    	    	}
    	    	if(valildationforimporthandling!=null) {
    	    		mailinboundFilter.setValildationforimporthandling(valildationforimporthandling);
    	    	}
    	}
		if(logonAttributes.getCompanyCode().equals("AA")){
			mailinboundFilter.setReadyforDeliveryRequired("Y");
		}
		else{
			mailinboundFilter.setReadyforDeliveryRequired("N");
		}
		AreaDelegate areaDelegate = new AreaDelegate();    
        Map stationParameters = null; 
		String stationCode = logonAttributes.getStationCode();	
		Collection<String> parameterCodes = new ArrayList<String>();
		parameterCodes.add(STNPAR_DEFUNIT_VOL);
		try {
			stationParameters = areaDelegate.findStationParametersByCode(logonAttributes.getCompanyCode(), stationCode, parameterCodes);
		} catch (BusinessDelegateException e1) {
			e1.getMessage();
		} 
		String stationVolumeUnit = (String)stationParameters.get(STNPAR_DEFUNIT_VOL);  
		 if(stationVolumeUnit!=null){
	    		mailinboundFilter.setStationVolUnt(stationVolumeUnit);   
			}
		results.add(mailinboundFilter);
		responseVO.setResults(results);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		log.exiting("ScreenloadMailinboundCommand","execute");
			
	}
	
	/**
	 * 
	 * 	Method		:	ScreenLoadCommand.findOneTimeDescription
	 *	Added by 	:	A-8164 on 20-Sep-2018
	 * 	Used for 	:   Finding the one time descriptions
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return 
	 *	Return type	: 	Map<String,Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add(MAILSTATUS);
			fieldValues.add("mailtracking.defaults.registeredorinsuredcode");
			fieldValues.add("mailtracking.defaults.mailcategory");
			fieldValues.add("mailtracking.defaults.highestnumbermail");
			fieldValues.add("mailtracking.defaults.mailclass"); 
			fieldValues.add("mail.operations.mailservicelevels"); 
			fieldValues.add("flight.operation.flightstatus"); 
			fieldValues.add("mail.operations.contentid"); 
			fieldValues.add("mailtracking.defaults.mailstatus"); 
			fieldValues.add("mailtracking.defaults.offload.reasoncode"); 
			fieldValues.add("mailtracking.defaults.companycode"); 
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}

}
