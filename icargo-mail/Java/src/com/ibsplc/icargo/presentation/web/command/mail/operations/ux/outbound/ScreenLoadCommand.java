package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.SecurityAgent;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ScreenLoadCommand extends AbstractCommand{
	   private static final String CONST_FLIGHT = "FLIGHT";
	   private static final String DEFAULT_CARRIER ="mail.operations.defaultcarrierinmailoutbound";
	   private static final String  WEIGHT_SCALE_AVAILABLE="mail.operation.weighscaleformailavailable";
	   private static final String  STNPAR_DEFUNIT_WGT="station.defaults.unit.weight";
	   private static final String SYSPAR_ULDTOBARROW_ALLOW="mail.operations.allowuldasbarrow";
	private Log log = LogFactory.getLogger("OPERATIONS MAIL OUTBOUND NEO");
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
			CommandInvocationException {
		this.log.entering("ScreenLoadCommand", "execute");
		LogonAttributes logonAttributes = getLogonAttribute();
		//DefaultScreenSessionImpl screenSession= getScreenSession();
		
    	
		OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
	    SharedDefaultsDelegate sharedDefaultsDelegate = 
	    	      new SharedDefaultsDelegate();
	     Map<String, Collection<OneTimeVO>> oneTimeValues = null;
	     try
	    	 {
	    	      oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
	    	        logonAttributes.getCompanyCode(), getOneTimeParameterTypes());
	    	     // systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterTypes);
	    	    }
	    	    catch (BusinessDelegateException e)
	    	    {
	    	      actionContext.addAllError(handleDelegateException(e));
	    	    }
	     this.log.log(5, new Object[] { "oneTimeValues ---> ", oneTimeValues });
	     this.log.log(5, new Object[] { "LoginAirport ---> ", logonAttributes.getAirportCode() });
         //Added by A-7540 for IASCB-23663 starts
	     String sysparValue = null;
	     String allowuldasbarrow=null;
	     ArrayList<String> systemParameters = new ArrayList<String>();
	     systemParameters.add(DEFAULT_CARRIER);
	     systemParameters.add("mail.operations.mandatescantime");
	     systemParameters.add("mail.operations.defaultoperatingcarriers");
		 systemParameters.add(SYSPAR_ULDTOBARROW_ALLOW);
	     Map<String, String> systemParameterMap = new SharedDefaultsDelegate()
	     	.findSystemParameterByCodes(systemParameters);
	     log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
	     if (systemParameterMap != null) {
	     sysparValue = systemParameterMap.get(DEFAULT_CARRIER);
	     allowuldasbarrow=systemParameterMap.get(SYSPAR_ULDTOBARROW_ALLOW);
	     if(sysparValue!=null && MailConstantsVO.FLAG_YES.contentEquals(sysparValue) && !logonAttributes.isGHAUser()){
	     outboundModel.setIsCarrierDefault(sysparValue);
	     }
	     else{
	    	 outboundModel.setIsCarrierDefault(null);
	         }
	     }
	    /* instead of system parameter logic changed to privilege based on  the CR docs
		if (systemParameterMap != null && systemParameterMap.containsKey("mail.operations.enabledeviationtab")) {
			outboundModel.setEnableDeviationListTab(MailConstantsVO.FLAG_YES
					.equals(systemParameterMap.get("mail.operations.enabledeviationtab")));
		} */
		boolean hasPrivilegeToViewDeviationList = false;
		try {
			hasPrivilegeToViewDeviationList = SecurityAgent.getInstance()
					.checkBusinessPrivilege("mail.operations.viewdeviationtab");
		} catch (SystemException systemException) {
			systemException.getMessage();
		}
		outboundModel.setEnableDeviationListTab(hasPrivilegeToViewDeviationList);
		//added by A-7815 as part of IASCB-36551
		if (systemParameterMap != null && systemParameterMap.containsKey("mail.operations.defaultoperatingcarriers")) {
			// X means applicable for ALL airlines, so need to set value
			if(!"X".equals(systemParameterMap.get("mail.operations.defaultoperatingcarriers"))) {
				outboundModel.setDefaultOperatingReference(systemParameterMap.get("mail.operations.defaultoperatingcarriers"));
			}		
		}
		//added as part of IASCB-48444
		if (systemParameterMap != null && systemParameterMap.containsKey("mail.operations.mandatescantime")) {
			outboundModel.setMandateScanTime("Y".equals(systemParameterMap.get("mail.operations.mandatescantime")));
		}
	     //Added by A-7540 for IASCB-23663 ends
	     outboundModel.setOneTimeValues(MailOutboundModelConverter.constructOneTimeValues(oneTimeValues));
	
	     outboundModel.setAirportCode(logonAttributes.getAirportCode());

	     if(logonAttributes.isGHAUser() && !logonAttributes.isOwnSalesAgent()) {
	    	 outboundModel.setCarrierCode(null);
	     }else {
	    	 outboundModel.setCarrierCode(logonAttributes.getOwnAirlineCode());
	     }	
	     
	     LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);		 	
	     outboundModel.setFromDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
	     outboundModel.setToDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		//added by A-7815 as part of IASCB-29597
	     outboundModel.setDefaultPageSize(20); 
	     Map airportParameters = null; 
	     AreaDelegate areaDelegate =   new AreaDelegate();
	    	 try {
				airportParameters = areaDelegate.findAirportParametersByCode(logonAttributes.getCompanyCode(), logonAttributes.getAirportCode(), getAirportParameterCodes());
			} catch(BusinessDelegateException e) {
				e.getMessage();
			}//added by A-8353 for ICRD-274933
		if(airportParameters!=null &&airportParameters.size()>0 &&"Y".equals((String)airportParameters.get(WEIGHT_SCALE_AVAILABLE))){
	              outboundModel.setWeightScaleAvailable(true);
			}
		else{
				outboundModel.setWeightScaleAvailable(false);
			}
		Map stationParameters = null;
        String stationCode = logonAttributes.getStationCode();
       String companyCode=logonAttributes.getCompanyCode();
       try {
           stationParameters = areaDelegate.findStationParametersByCode(companyCode, stationCode, getStationParameterCodes());
       } catch (BusinessDelegateException e1) {
           e1.getMessage();
       }
       if(stationParameters!=null &&stationParameters.size()>0 ){
    	   String stationWeightUnit = (String)stationParameters.get(STNPAR_DEFUNIT_WGT);
    	   if(stationWeightUnit.equals("L")){
    		   outboundModel.setStationWeightUnit("lbs");
    	   }else if(stationWeightUnit.equals("K")){
    		   outboundModel.setStationWeightUnit("Kg");
    	   }else{
    		   outboundModel.setStationWeightUnit(stationWeightUnit);
    	   }
		}
       outboundModel.setUldToBarrowAllow(allowuldasbarrow);
       
	     ResponseVO responseVO = new ResponseVO();
	     List<OutboundModel> results = new ArrayList();
	     results.add(outboundModel);
	     responseVO.setResults(results);
	     actionContext.setResponseVO(responseVO);
	     this.log.exiting("ScreenLoadCommand", "execute");
	     
	     
		
	}
	
	private Collection<String> getOneTimeParameterTypes()
	  {
	    Collection<String> parameterTypes = new ArrayList();
	    parameterTypes.add("flight.operation.flightstatus");
	    parameterTypes.add("mailtracking.defaults.flightstatus");
	    parameterTypes.add("mailtracking.defaults.return.reasoncode");
	    parameterTypes.add("mailtracking.defaults.mailcategory");
	    parameterTypes.add("mailtracking.defaults.mailclass");
	    parameterTypes.add("mailtracking.defaults.mailstatus");
	    parameterTypes.add("mailtracking.defaults.carditenquiry.flighttype");
        parameterTypes.add("mailtracking.defaults.consignmentdocument.type");
        parameterTypes.add("mailtracking.defaults.return.reasoncode");
        parameterTypes.add("mail.operations.mailservicelevels");
        parameterTypes.add("mailtracking.defaults.registeredorinsuredcode");
	    parameterTypes.add("mailtracking.defaults.mailcategory");
	    parameterTypes.add("mailtracking.defaults.highestnumbermail");
	    parameterTypes.add("mailtracking.defaults.mailclass");
	    parameterTypes.add("mail.operations.contentid");
	    parameterTypes.add("operations.flthandling.dws.dcsreportingstatus");
	    parameterTypes.add("mail.operations.deviationlist.status");
		return parameterTypes;
	  }
	 private Collection<String> getAirportParameterCodes(){
		 Collection<String> parameterTypes = new ArrayList();
		  parameterTypes.add(WEIGHT_SCALE_AVAILABLE);
          return parameterTypes;
	  }
	 private Collection<String> getStationParameterCodes()
     {
       Collection stationParameterCodes = new ArrayList();
       stationParameterCodes.add(STNPAR_DEFUNIT_WGT);
       return stationParameterCodes;
	  }

}
