package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Calendar;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingIndexVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.framework.util.unit.vo.UnitConversionNewVO;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.UnitException;

public class PopulateDomesticMailbagIdCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	private Map<String, String> exchangeOfficeMap;
	private static final String DEST_FOR_CDT_MISSING_DOM_MAL="mail.operation.destinationforcarditmissingdomesticmailbag";
	private static final String DOMESTIC_WARNING = "mail.operation.domesticmaildoesnotexistwarning";  
	private static final String DOMESTIC_WARNING_ONSAVE = "mail.operation.domesticmaildoesnotexistwarningonsave";
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		log.entering("PopulateDomesticMailbagIdCommand", "execute");
		List<ErrorVO> errorVOs = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		OutboundModel outboundModel = (OutboundModel) actionContext.getScreenModel();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companycode = logonAttributes.getCompanyCode();
		List<Mailbag> mailbags = outboundModel.getMailbags();
		if (mailbags.isEmpty())
		{
			actionContext.addError(new ErrorVO("Please enter MailBag ID"));
			return;	
		}
		else
		{
		Mailbag mailbag = mailbags.get(0);
		MailbagVO mailbagVO=null;
		List<Mailbag> excelmailbags= new ArrayList<Mailbag>();
		String defDestForCdtMissingMailbag=null;
		try {
			defDestForCdtMissingMailbag=findSystemParameterValue(DEST_FOR_CDT_MISSING_DOM_MAL);
		} catch (SystemException e1) {
			log.log(Log.INFO, e1);
		}	
		if(!outboundModel.getAddMailbagMode().equalsIgnoreCase("EXCEL_VIEW")){
			try{mailbagVO =new MailTrackingDefaultsDelegate().findMailDetailsForMailTag(
				companycode,mailbag.getMailbagId().toUpperCase()) ;
		}catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			}
		if(mailbagVO!=null&&mailbagVO.getMailbagId()!=null&&
				!mailbagVO.getMailbagId().isEmpty()){
			if(mailbagVO.getOoe()!=null)
			mailbag.setOoe(mailbagVO.getOoe());
			if(mailbagVO.getDoe()!=null)
			mailbag.setDoe(mailbagVO.getDoe());
			if(mailbagVO.getMailCategoryCode()!=null)
			mailbag.setMailCategoryCode(mailbagVO.getMailCategoryCode());
			if(mailbagVO.getMailSubclass()!=null)
			mailbag.setMailSubclass(mailbagVO.getMailSubclass());
			mailbag.setYear(mailbagVO.getYear());
			if(mailbagVO.getDespatchSerialNumber()!=null)
			mailbag.setDespatchSerialNumber(mailbagVO.getDespatchSerialNumber());
			if(mailbagVO.getReceptacleSerialNumber()!=null)
			mailbag.setReceptacleSerialNumber(mailbagVO.getReceptacleSerialNumber());
			if(mailbagVO.getHighestNumberedReceptacle()!=null)
			mailbag.setHighestNumberedReceptacle(mailbagVO.getHighestNumberedReceptacle());
			if(mailbagVO.getRegisteredOrInsuredIndicator()!=null)
			mailbag.setRegisteredOrInsuredIndicator(mailbagVO.getRegisteredOrInsuredIndicator());
			if(mailbagVO.getWeight()!=null)
			mailbag.setMailbagWeight(String.valueOf(mailbagVO.getWeight()));
			
			if(mailbagVO.getOrigin()!=null){
			    mailbag.setMailorigin(mailbagVO.getOrigin());	
			}
			if(mailbagVO.getDestination()!=null){
				mailbag.setMailDestination(mailbagVO.getDestination());	
			}
			
			}
		else{
		String routIndex = mailbag.getMailbagId().substring(4, 8);
		String org = null;
		String dest = null;
		Collection<RoutingIndexVO> routingIndexVOs = new ArrayList<RoutingIndexVO>();
		RoutingIndexVO routingIndexFilterVO = new RoutingIndexVO();
		routingIndexFilterVO.setRoutingIndex(routIndex);
		routingIndexFilterVO.setCompanyCode(companycode);
		LocalDate scannedDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		if(mailbag.getScannedDate()!=null && mailbag.getScannedDate().trim().length()>0) {
			scannedDate.setDate(mailbag.getScannedDate());
		}
		routingIndexFilterVO.setScannedDate(scannedDate);
		try {
			routingIndexVOs = new MailTrackingDefaultsDelegate().findRoutingIndex(routingIndexFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}
		if(routingIndexVOs.isEmpty()&&defDestForCdtMissingMailbag!=null &&!"NA".equals(defDestForCdtMissingMailbag)){
			Map<String, String> existigWarningMap = outboundModel.getWarningMessagesStatus();   
	    	String domesticWarningStatus = "N";
	    	if(existigWarningMap != null && existigWarningMap.size()>0 && existigWarningMap.containsKey(DOMESTIC_WARNING)) {
	    		domesticWarningStatus=existigWarningMap.get(DOMESTIC_WARNING);
	    	}
	    	if("N".equals(domesticWarningStatus)){     
			List<ErrorVO> warningErrors = new ArrayList<>();
		    ErrorVO warningError = new ErrorVO(DOMESTIC_WARNING);
  		    warningError.setErrorDisplayType(ErrorDisplayType.WARNING);
  		    warningErrors.add(warningError);
  		    actionContext.addAllError(warningErrors); 
		    return;  
	    	}
			RoutingIndexVO routingIndexVO=new RoutingIndexVO();
			routingIndexVO.setOrigin(logonAttributes.getAirportCode());
			routingIndexVO.setDestination(defDestForCdtMissingMailbag);	
			routingIndexVO.setRoutingIndex("XXXX");	      
			routingIndexVOs.add(routingIndexVO);
		}
		exchangeOfficeMap = new HashMap<>();
		mailbagIdValidation(actionContext, companycode, mailbag, routingIndexVOs);


			





		}
		}
		else{
			for(Mailbag excelMailbag:outboundModel.getMailbags()) {
				if (excelMailbag.getMailbagId().length() == 12) {
					try{
						mailbagVO =new MailTrackingDefaultsDelegate().findMailDetailsForMailTag(
								logonAttributes.getCompanyCode(),excelMailbag.getMailbagId().toUpperCase()) ;
			  		}catch (BusinessDelegateException businessDelegateException) {
		  				handleDelegateException(businessDelegateException);
		  			}
					mailbag = mailbagValidations(actionContext, outboundModel, logonAttributes, mailbag,
							mailbagVO, defDestForCdtMissingMailbag, excelMailbag);
		}
				else if (excelMailbag.getMailbagId().length() == 29) {
					mailbagVO= new MailbagVO();
					mailbagVO.setMailbagId(excelMailbag.getMailbagId());
					mailbagVO.setOoe(excelMailbag.getMailbagId().substring(0, 6));
					mailbagVO.setDoe(excelMailbag.getMailbagId().substring(6, 12));
					mailbagVO.setMailCategoryCode(excelMailbag.getMailbagId().substring(12, 13));
					mailbagVO.setMailSubclass(excelMailbag.getMailbagId().substring(13, 15));
					mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
					mailbagVO.setYear(Integer.parseInt(excelMailbag.getMailbagId().substring(15, 16)));
					mailbagVO.setDespatchSerialNumber(excelMailbag.getMailbagId().substring(16, 20));
					mailbagVO.setReceptacleSerialNumber(excelMailbag.getMailbagId().substring(20, 23));
					mailbagVO.setHighestNumberedReceptacle(excelMailbag.getMailbagId().substring(23, 24));
					mailbagVO.setRegisteredOrInsuredIndicator(excelMailbag.getMailbagId().substring(24, 25));
					mailbagVO.setTransferFromCarrier(mailbag.getCarrier());
					mailbagVO.setMailRemarks(mailbag.getMailRemarks());
					mailbag = MailOutboundModelConverter.populateMailDetails(mailbagVO);
					if(null!=excelMailbag.getAcceptancePostalContainerNumber())
					{
						mailbag.setAcceptancePostalContainerNumber(excelMailbag.getAcceptancePostalContainerNumber());
					}
					double convertedWeight = unitConvertion(UnitConstants.MAIL_WGT, "L", excelMailbag.getDisplayUnit(),
							Double.parseDouble(excelMailbag.getMailbagWeight()));
					double conDisplayWeight = 0;
					if (UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(excelMailbag.getDisplayUnit())) {
						conDisplayWeight = round(convertedWeight, 1);
					} else {
						conDisplayWeight = round(convertedWeight, 0);
					}
					mailbag.setDisplayUnit(excelMailbag.getDisplayUnit());
					mailbag.setMailbagWeight(excelMailbag.getMailbagWeight());
					double weightForVol=unitConvertion(UnitConstants.MAIL_WGT,"L",UnitConstants.WEIGHT_UNIT_KILOGRAM,conDisplayWeight);
					double actVol=(weightForVol/0.5);
					mailbag.setVolume(new Measure(UnitConstants.VOLUME,
							actVol));
					mailbag.setMailbagVolume(mailbag.getVolume().getFormattedDisplayValue());
				}
				else{
					actionContext.addError(new ErrorVO("Invalid Mail bag"));
						return;
				}
				excelmailbags.add(mailbag);
	}
			outboundModel.setMailbags(excelmailbags);
		}
		}
		ResponseVO responseVO = new ResponseVO();
	    List<OutboundModel> results = new ArrayList<>();
	    results.add(outboundModel);
	    responseVO.setResults(results);
	    responseVO.setStatus("success");
	    actionContext.setResponseVO(responseVO);
	}
	private Mailbag mailbagValidations(ActionContext actionContext, OutboundModel outboundModel,
			LogonAttributes logonAttributes, Mailbag mailbag, MailbagVO mailbagVO,
			String defDestForCdtMissingMailbag, Mailbag excelMailbag) {
		String companycode=logonAttributes.getCompanyCode();
		if(null==mailbagVO){
			actionContext.addError(new ErrorVO("Mailbag Not Found"));
					}	
					else if (mailbagVO!=null&&mailbagVO.getMailbagId()!=null&&!mailbagVO.getMailbagId().isEmpty()){
						mailbag=	MailOutboundModelConverter.populateMailDetails(mailbagVO);
						double convertedWeight = unitConvertion(UnitConstants.MAIL_WGT, "L", excelMailbag.getDisplayUnit(),
								Double.parseDouble(excelMailbag.getMailbagId().substring(10, 12)));
						double conDisplayWeight = 0;
						if (UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(excelMailbag.getDisplayUnit())) {
							conDisplayWeight = round(convertedWeight, 1);
						} else {
							conDisplayWeight = round(convertedWeight, 0);
						}
						mailbag.setMailbagWeight(String.valueOf(conDisplayWeight));
						double weightForVol=unitConvertion(UnitConstants.MAIL_WGT,"L",UnitConstants.WEIGHT_UNIT_KILOGRAM,conDisplayWeight);
						double actVol=(weightForVol/0.5);
						mailbag.setVolume(new Measure(UnitConstants.VOLUME,
								actVol));
						mailbag.setMailbagVolume(mailbag.getVolume().getFormattedDisplayValue());
					}
					else if (excelMailbag.getMailbagId().length() == 12) {
						String routIndex = excelMailbag.getMailbagId().substring(4, 8);
						Collection<RoutingIndexVO> routingIndexVOs = new ArrayList<>();
						RoutingIndexVO routingIndexFilterVO = new RoutingIndexVO();
						routingIndexFilterVO.setRoutingIndex(routIndex);
						routingIndexFilterVO.setCompanyCode(companycode);
						LocalDate scannedDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,false);
						if(excelMailbag.getScannedDate()!=null && excelMailbag.getScannedDate().trim().length()>0) {
							scannedDate.setDate(excelMailbag.getScannedDate());
						}
						routingIndexFilterVO.setScannedDate(scannedDate);
						try {
							routingIndexVOs = new MailTrackingDefaultsDelegate().findRoutingIndex(routingIndexFilterVO);
						} catch (BusinessDelegateException businessDelegateException) {
							handleDelegateException(businessDelegateException);
						}
						if(routingIndexVOs.isEmpty()&&defDestForCdtMissingMailbag!=null &&!"NA".equals(defDestForCdtMissingMailbag)){
							Map<String, String> existigWarningMap = outboundModel.getWarningMessagesStatus();
							String domesticWarningStatus = "N"; 
					    	domesticWarningStatus = existigWarningMapCheck(existigWarningMap, domesticWarningStatus);
					    	domesticWarningStatusN(actionContext, domesticWarningStatus);
							RoutingIndexVO routingIndexVO=new RoutingIndexVO(); 
							routingIndexVO.setOrigin(logonAttributes.getAirportCode());
							routingIndexVO.setDestination(defDestForCdtMissingMailbag);	
							routingIndexVO.setRoutingIndex("XXXX");	      
							routingIndexVOs.add(routingIndexVO);
						}
						exchangeOfficeMap=new HashMap<>();
						if(!routingIndexVOs.isEmpty()){
							for(RoutingIndexVO routingIndexVO:routingIndexVOs){
								mailbag = routingIndexVoNullCheck(companycode, mailbag, mailbagVO, excelMailbag,
										routingIndexVO);
							}
			}else{	
										
				actionContext.addError(new ErrorVO("Invalid Mail bag"));

}
		}
		return mailbag;
	}

				

	private void mailbagIdValidation(ActionContext actionContext, String companycode, Mailbag mailbag,
			Collection<RoutingIndexVO> routingIndexVOs) {
		String org;
		String dest;
		if (!routingIndexVOs.isEmpty()) {
			for (RoutingIndexVO routingIndexVO : routingIndexVOs) {
				if (routingIndexVO != null && routingIndexVO.getRoutingIndex() != null) {
					org = routingIndexVO.getOrigin();
					dest = routingIndexVO.getDestination();
					try {
						exchangeOfficeMap = new MailTrackingDefaultsDelegate().findOfficeOfExchangeForPA(companycode,
								findSystemParameterValue(USPS_DOMESTIC_PA));
					} catch (BusinessDelegateException businessDelegateException) {
						handleDelegateException(businessDelegateException);
					} catch (SystemException e) {
						log.log(Log.SEVERE, e.getMessage());
					}
					exchangeOfficeCheck(mailbag, org, dest);
					mailbag.setMailCategoryCode("B");
					String mailClass = mailbag.getMailbagId().substring(3, 4);
					mailbag.setMailSubclass(mailClass + "X");
					int lastTwoDigits = Calendar.getInstance().get(Calendar.YEAR) % 100;
					mailbag.setYear(lastTwoDigits % 10);
					mailbagValueSet(mailbag, org, dest);
					mailbagLengthCheck(mailbag);
				} else {
					actionContext.addError(new ErrorVO("Invalid mail id"));
			  		return;
				}
			}
		} else {
			actionContext.addError(new ErrorVO("Invalid mail id"));
	  		
		}
	}
	private void mailbagValueSet(Mailbag mailbag, String org, String dest) {
		mailbag.setDespatchSerialNumber(MailConstantsVO.DOM_MAILBAG_DEF_DSNVAL);  
		mailbag.setReceptacleSerialNumber(MailConstantsVO.DOM_MAILBAG_DEF_RSNVAL);
		mailbag.setHighestNumberedReceptacle("9");
		mailbag.setRegisteredOrInsuredIndicator("9");
		if(org!=null){
		    mailbag.setMailorigin(org);	
		}
		if(dest!=null){
			mailbag.setMailDestination(dest);	
		}
	}
	private String existigWarningMapCheck(Map<String, String> existigWarningMap, String domesticWarningStatus) {
		if(existigWarningMap != null && existigWarningMap.size()>0 && existigWarningMap.containsKey(DOMESTIC_WARNING_ONSAVE)) {
			domesticWarningStatus=existigWarningMap.get(DOMESTIC_WARNING_ONSAVE);  
		}
		return domesticWarningStatus;
	}
	private Mailbag routingIndexVoNullCheck(String companycode, Mailbag mailbag, MailbagVO mailbagVO,
			Mailbag excelMailbag, RoutingIndexVO routingIndexVO) {
		if(routingIndexVO!=null&&routingIndexVO.getRoutingIndex()!=null){
			 findOfficeOfExchangeDest(companycode, excelMailbag, routingIndexVO);
			 excelMailbag.setMailCategoryCode("B");
				String mailClass = excelMailbag.getMailbagId().substring(3, 4);
				excelMailbag.setMailSubclass(mailClass + "X");
				int lastTwoDigits = Calendar.getInstance().get(Calendar.YEAR) % 100;
				excelMailbag.setYear((lastTwoDigits % 10));
				excelMailbag.setDespatchSerialNumber(MailConstantsVO.DOM_MAILBAG_DEF_DSNVAL);  
				excelMailbag.setReceptacleSerialNumber(MailConstantsVO.DOM_MAILBAG_DEF_RSNVAL);
				excelMailbag.setHighestNumberedReceptacle("9");
				excelMailbag.setRegisteredOrInsuredIndicator("9");
				mailbagVO.setMailbagId(excelMailbag.getMailbagId());
				mailbagVO.setOoe(mailbag.getOoe());
				mailbagVO.setDoe(mailbag.getDoe());
				mailbagVO.setMailCategoryCode(mailbag.getMailCategoryCode());
				mailbagVO.setMailSubclass(mailbag.getMailSubclass());
				mailbagVO.setCompanyCode(mailbag.getCompanyCode());
				mailbagVO.setYear(mailbag.getYear());
				mailbagVO.setDespatchSerialNumber(mailbag.getDespatchSerialNumber());
				mailbagVO.setReceptacleSerialNumber(mailbag.getReceptacleSerialNumber());
				mailbagVO.setHighestNumberedReceptacle(mailbag.getHighestNumberedReceptacle());
				mailbagVO.setRegisteredOrInsuredIndicator(mailbag.getRegisteredOrInsuredIndicator());
				mailbagVO.setTransferFromCarrier(mailbag.getCarrier());
				mailbagVO.setMailRemarks(mailbag.getMailRemarks());
				mailbag = MailOutboundModelConverter.populateMailDetails(mailbagVO);
				double convertedWeight = unitConvertion(UnitConstants.MAIL_WGT, "L", excelMailbag.getDisplayUnit(),
						Double.parseDouble(excelMailbag.getMailbagId().substring(10, 12)));
				double conDisplayWeight = 0;
				if (UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(excelMailbag.getDisplayUnit())) {
					conDisplayWeight = round(convertedWeight, 1);
				} else {
					conDisplayWeight = round(convertedWeight, 0);
				}
				mailbag.setMailbagWeight(String.valueOf(conDisplayWeight));
				double weightForVol=unitConvertion(UnitConstants.MAIL_WGT,"L",UnitConstants.WEIGHT_UNIT_KILOGRAM,conDisplayWeight);
				double actVol=(weightForVol/0.5);
				mailbag.setVolume(new Measure(UnitConstants.VOLUME,
						actVol));
				mailbag.setMailbagVolume(mailbag.getVolume().getFormattedDisplayValue());
		}
		return mailbag;
	}
	private void findOfficeOfExchangeDest(String companycode, Mailbag excelMailbag, RoutingIndexVO routingIndexVO) {
		String org;
		String dest;
		org=routingIndexVO.getOrigin();
		 dest=routingIndexVO.getDestination();
		 try {
				exchangeOfficeMap=new MailTrackingDefaultsDelegate().findOfficeOfExchangeForPA(companycode,findSystemParameterValue(USPS_DOMESTIC_PA));
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			} catch (SystemException e) {
				log.log(Log.INFO, e);			} 
		 exchangeOfficeCheck(excelMailbag, org, dest);
		 if(org!=null){
			 excelMailbag.setMailorigin(org);	
			}
			if(dest!=null){
				excelMailbag.setMailDestination(dest);	
			}
	}
	private void domesticWarningStatusN(ActionContext actionContext, String domesticWarningStatus) {
		if("N".equals(domesticWarningStatus)){     
		List<ErrorVO> warningErrors = new ArrayList<>();
		ErrorVO warningError = new ErrorVO(DOMESTIC_WARNING_ONSAVE);
		warningError.setErrorDisplayType(ErrorDisplayType.WARNING);
		warningErrors.add(warningError);
		actionContext.addAllError(warningErrors); 
		}
	}

	private void exchangeOfficeCheck(Mailbag mailbag, String org, String dest) {
		if (exchangeOfficeMap != null && !exchangeOfficeMap.isEmpty()) {
			if (exchangeOfficeMap.containsKey(org)) {
				mailbag.setOoe(exchangeOfficeMap.get(org));
			}
			if (exchangeOfficeMap.containsKey(dest)) {
				mailbag.setDoe(exchangeOfficeMap.get(dest));
			}
		}
	}
	private void mailbagLengthCheck(Mailbag mailbag) {
		if (mailbag.getMailbagId().length() == 12) {
			double convertedWeight = unitConvertion(UnitConstants.MAIL_WGT, "L", mailbag.getDisplayUnit(),
					Double.parseDouble(mailbag.getMailbagId().substring(11, 12)));
			double conDisplayWeight = 0;
			if (UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(mailbag.getDisplayUnit())) {
				conDisplayWeight = round(convertedWeight, 1);
			} else {
				conDisplayWeight = round(convertedWeight, 0);
			}
			mailbag.setMailbagWeight(String.valueOf(conDisplayWeight));
		}
	}

	private String findSystemParameterValue(String syspar) throws SystemException, BusinessDelegateException {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = new SharedDefaultsDelegate()
				.findSystemParameterByCodes(systemParameters);
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

	private double round(double val, int places) {
		long factor = (long) Math.pow(10, places);
		val = val * factor;
		long tmp = Math.round(val);
		return (double) tmp / factor;
	}

	private double unitConvertion(String unitType, String fromUnit, String toUnit, double fromValue) {
		UnitConversionNewVO unitConversionVO = null;
		try {
			unitConversionVO = UnitFormatter.getUnitConversionForToUnit(unitType, fromUnit, toUnit, fromValue);
		} catch (UnitException e) {
			log.log(Log.SEVERE, e.getMessage());
		}
		if(null!=unitConversionVO)
		{
		return unitConversionVO.getToValue();
		}
		else
		{
			return 0;
		}

	}

}
