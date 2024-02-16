package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Calendar;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingIndexVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationVO;
import com.ibsplc.icargo.framework.util.unit.vo.UnitConversionNewVO;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;

public class AcceptMailCommand extends AbstractCommand{
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	ScannedMailDetailsVO scannedMailVO=new ScannedMailDetailsVO();
	private static final String HYPHEN = "-";
	private static final String ERROR_DMG_REASON_MANDATORY = "mailtracking.defaults.err.reasonfordamagemandatory";
	private static final String WARN_COTERMINUS="mailtracking.defaults.war.coterminus";
	private static final String COTERMINUS_STATUS="coterminus";
	private static final String LATVALIDATION_STATUS="latvalidation";
	private static final String RETURNED_MAILBAG = "mailtracking.defaults.err.returnedmailbag";
	private Map<String, String> exchangeOfficeMap;
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	private static final String DEST_FOR_CDT_MISSING_DOM_MAL="mail.operation.destinationforcarditmissingdomesticmailbag";
	private static final String SECURITY_SCREENING_WARNING="mail.operations.securityscreeningwarning";
	private static final String SECURITY_SCREENING_ERROR="mail.operations.securityscreeningerror";
	private static final String APPLICABLE_REGULATION_ERROR="mail.operations.applicableregulationerror";
	public static final String LAT_VIOLATED_ERR = "mailtracking.defaults.err.latvalidation";
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
	CommandInvocationException {
	log.entering("AcceptMailCommand","execute");
	LogonAttributes logonAttributes =  getLogonAttribute();
	ContainerDetailsVO containerDetailsVO = (ContainerDetailsVO)actionContext.getAttribute("containerDetails");
//Added as part of ICRD-356586
	List<ErrorVO> existingerrors = actionContext.getErrors();
    if ((existingerrors != null) && (existingerrors.size() > 0)) {
          return; 
    }
	String flightCarrierFlag = (String)actionContext.getAttribute("flightCarrierFlag");
			List<ErrorVO> errors = new ArrayList<ErrorVO>();
	OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
	
	String warningShow=outboundModel.getShowWarning();
	if(outboundModel.getMailAcceptance()!=null && outboundModel.getMailAcceptance().isFromDeviationList()) {
		warningShow = "N";
		outboundModel.setShowWarning("N");
	}
	 Map<String, String> warningMap = outboundModel.getWarningMessagesStatus();
	 if ((warningMap == null) || (warningMap.isEmpty()))
	    {
	      warningMap = new HashMap();
	      outboundModel.setWarningMessagesStatus(warningMap);
	    }
	if("F".equals(flightCarrierFlag) && outboundModel.getNewContainerInfo()!=null){
		if((outboundModel.getNewContainerInfo().getPou() == null || 
				outboundModel.getNewContainerInfo().getPou().trim().length() == 0)){ 
			actionContext.addError(new ErrorVO("mailtracking.defaults.pou.empty"));
			//actionContext.target = TARGET;
	  		return;
		}
		if("U".equals(outboundModel.getNewContainerInfo().getType()) && (outboundModel.getNewContainerInfo().getDestination())==null ||
				(outboundModel.getNewContainerInfo().getDestination().trim().length()==0)){
					actionContext.addError(new ErrorVO("mailtracking.defaults.destn.empty"));
			//invocationContext.target = TARGET;
	  		return;
		}
		if("B".equals(outboundModel.getNewContainerInfo().getType())){
			if(! (outboundModel.getNewContainerInfo().getPou().equals(outboundModel.getNewContainerInfo().getDestination()))){
				actionContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.destnandpouisnotsame"));
				//invocationContext.target = TARGET;
		  		return;
			}
		}
	}				
		
	MailAcceptanceVO mailAcceptanceVO = (MailAcceptanceVO)actionContext.getAttribute("mailAcceptanceDetails");
	Collection<MailbagVO> newMailbagVOs =  containerDetailsVO.getMailDetails();
	Collection<MailbagVO> updatedMailbagVOs = new ArrayList<MailbagVO>(); 
	
	if(newMailbagVOs != null && newMailbagVOs.size() > 0) {
		  for(MailbagVO newMailbagVO:newMailbagVOs) {
              //Added by A-5945 for ICRD-93738 starts
			  /*if(newMailbagVO.getMailbagId().length()==12 && outboundModel.getTab()!=null && outboundModel.getTab().equals("EXCEL_VIEW")){  
				  CreateDomesticMailbag(newMailbagVO, logonAttributes);
			  }*/
			  String wgt = String.valueOf(newMailbagVO.getStrWeight().getDisplayValue()); //modified by a-7371
			  log.log(Log.FINE, "wt ...in command", wgt);
			 	String stdwgt = wgt;
				if(wgt.length() == 3){
					stdwgt = new StringBuilder("0").append(wgt).toString();  
				}
				if(wgt.length() == 2){
					stdwgt = new StringBuilder("00").append(wgt).toString();
				}
				if(wgt.length() == 1){
					stdwgt = new StringBuilder("000").append(wgt).toString();
				}  
             //Added by A-5945 for ICRD-93738 ends 
		    /*String mailId = new StringBuilder()
		            .append(newMailbagVO.getOoe())
		            .append(newMailbagVO.getDoe())
					.append(newMailbagVO.getMailCategoryCode())
					.append(newMailbagVO.getMailSubclass())
					.append(newMailbagVO.getYear())
					.append(newMailbagVO.getDespatchSerialNumber())
					.append(newMailbagVO.getReceptacleSerialNumber())
					.append(newMailbagVO.getHighestNumberedReceptacle())
					.append(newMailbagVO.getRegisteredOrInsuredIndicator())
					.append(stdwgt).toString();*/
		    String dsnId = new StringBuilder()
            .append(newMailbagVO.getOoe())
            .append(newMailbagVO.getDoe())
			.append(newMailbagVO.getMailCategoryCode())
			.append(newMailbagVO.getMailSubclass())
			.append(newMailbagVO.getYear())
			.append(newMailbagVO.getDespatchSerialNumber()).toString();			    
		    newMailbagVO.setMailbagId(newMailbagVO.getMailbagId());//Modified for ICRD-205027
		    newMailbagVO.setDespatchId(dsnId);
		     newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
		     newMailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		    	newMailbagVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
		    	newMailbagVO.setFlightDate(mailAcceptanceVO.getFlightDate());
		    	newMailbagVO.setScannedPort(newMailbagVO.getScannedPort());
		    	newMailbagVO.setMailCompanyCode(newMailbagVO.getMailCompanyCode());
		    	newMailbagVO.setMailSequenceNumber(newMailbagVO.getMailSequenceNumber());
		    	if(MailConstantsVO.FLAG_YES.equals(newMailbagVO.getArrivedFlag())){
		    		//No need to update  
		    	}else{
		    	newMailbagVO.setArrivedFlag("N");
		    	}
		    	newMailbagVO.setDeliveredFlag("N");
		    	newMailbagVO.setScannedUser(newMailbagVO.getScannedUser());
		    	newMailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		    	newMailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
		    	newMailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
		    	newMailbagVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());			    	
		    	newMailbagVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());       			    	
		    	newMailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
		    	newMailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
		    	newMailbagVO.setContainerType(containerDetailsVO.getContainerType());
		    	newMailbagVO.setPou(containerDetailsVO.getPou());
		  }
		}
	//added for icrd-85496 by a-4810 begins
	if(/*MailbagVO.FLAG_YES.equals(mailAcceptancevo.getModify()) && */containerDetailsVO.getDeletedMailDetails() != null &&
			!containerDetailsVO.getDeletedMailDetails().isEmpty()){
		
    		for(MailbagVO mailVO : newMailbagVOs){
    			if(!MailbagVO.OPERATION_FLAG_DELETE.equals(mailVO.getOperationalFlag())){
    				updatedMailbagVOs.add(mailVO);
    			}
    		}
    		for(MailbagVO delmailVO : containerDetailsVO.getDeletedMailDetails()){
    			for(MailbagVO mailVO : newMailbagVOs){
    			if(MailbagVO.OPERATION_FLAG_DELETE.equals(delmailVO.getOperationalFlag()) && MailbagVO.OPERATION_FLAG_INSERT.equals(mailVO.getOperationalFlag()) &&
    					delmailVO.getMailbagId() != null && delmailVO.getMailbagId().equals(mailVO.getNewMailId())){
    				delmailVO.setNewMailId(mailVO.getMailbagId());
    				if(delmailVO.getContainerNumber() == null){
    					delmailVO.setContainerNumber(containerDetailsVO.getContainerNumber());
    					delmailVO.setContainerType(containerDetailsVO.getContainerType());
        			}
    			}
    		}
		}
		newMailbagVOs = updatedMailbagVOs;
	}
	/*if(newMailbagVOs != null && newMailbagVOs.size() > 0) {
	  for(MailbagVO newMailbagVO:newMailbagVOs) {
    	 if(newMailbagVO.getDamageFlag() != null && MailbagVO.FLAG_YES.equals(newMailbagVO.getDamageFlag())
	    			 && newMailbagVO.getOperationalFlag()!=null && (MailbagVO.OPERATION_FLAG_INSERT.equals(newMailbagVO.getOperationalFlag())
	    			 || MailbagVO.OPERATION_FLAG_UPDATE.equals(newMailbagVO.getOperationalFlag()))) {
			 if(newMailbagVO.getDamagedMailbags() == null ||  newMailbagVO.getDamagedMailbags().size() == 0){
				 actionContext.addError(new ErrorVO(RETURNED_MAILBAG)); 
						 break;
			 }
		 }
	  }
	}*/
	
	    containerDetailsVO.setMailDetails(newMailbagVOs);
	    
	    
	    
	    
    	
    			    


		  			
    	
    	

    	String invalidPACode = "";
    	Collection<DespatchDetailsVO> despatchDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
	  	log.log(Log.FINE, "Going To validate PA code ...in command");
	  	if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0){
	  		try {
		  		for(DespatchDetailsVO despatchVO:despatchDetailsVOs){
		  			String paCode = despatchVO.getPaCode().toUpperCase();
		  			PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();

	  				postalAdministrationVO  = new MailTrackingDefaultsDelegate().findPACode(
	  								logonAttributes.getCompanyCode(),paCode);

		  			if (postalAdministrationVO == null) {
		  				if("".equals(invalidPACode)){
		  					invalidPACode = paCode;
		  				}else{
		  					invalidPACode = new StringBuilder(invalidPACode).append(",").append(paCode).toString();
		  				}
		  			}
		  		}
	  		}catch (BusinessDelegateException businessDelegateException) {
  				errors = handleDelegateException(businessDelegateException);
  			}
	  	}
	  	if(!"".equals(invalidPACode)){
			actionContext.addError(new ErrorVO("mailtracking.defaults.invalidpacode",
	   				new Object[]{invalidPACode}));
			//mailAcceptanceForm.setPopupCloseFlag("N");
	  		//invocationContext.target = TARGET;
	  		return;
		}   
	    
	    
  
	  	/*
	  	 * Validating Consignment Date and Stated Weight for a Despatch 
	  	 */
	  	log.log(Log.FINE, "Going To validate Consignment Date and Stated Weight for a Despatch...in command");
	  	if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0){
	  		Collection<DespatchDetailsVO> despatchDetails = new ArrayList<DespatchDetailsVO>();
	  		Collection<DespatchDetailsVO> despatchesWithWrongCsgDtls = null;
	  		try { 
	  			for(DespatchDetailsVO despatchDtl : despatchDetailsVOs) {
	  				if(despatchDtl.getConsignmentNumber() != null && 
	  						despatchDtl.getConsignmentNumber().trim().length() > 0) {
	  					despatchDetails.add(despatchDtl);
	  				}
	  			}
	  			if(despatchDetails.size() > 0) {
	  				despatchesWithWrongCsgDtls = new MailTrackingDefaultsDelegate().validateConsignmentDetails(logonAttributes.getCompanyCode(),despatchDetails);
	  			}
	  			if(despatchesWithWrongCsgDtls != null && despatchesWithWrongCsgDtls.size() > 0) {
	  				List<ErrorVO> despatchCSGDetailErrors = new ArrayList<ErrorVO>();
	  				StringBuilder dsns = new StringBuilder();
	  				boolean first = true;
	  				for(DespatchDetailsVO wrongDespatchCsgDtl : despatchesWithWrongCsgDtls) {
	  					if(first) {
		  					dsns.append(wrongDespatchCsgDtl.getDsn());
	  						first = false;
	  					}else {
		  					dsns.append(",").append(wrongDespatchCsgDtl.getDsn());
	  					}
	  				}
	  				if(dsns.length() > 0) {
	  					despatchCSGDetailErrors.add(new ErrorVO("mailtracking.defaults.acceptmail.wrongconsignmentDetails",
  							new Object[] {dsns.toString()}));
	  				}
	  				if (despatchCSGDetailErrors != null && despatchCSGDetailErrors.size() > 0) {
	  			  		actionContext.addAllError(despatchCSGDetailErrors);
	  			  		//mailAcceptanceForm.setPopupCloseFlag("N");
	  			  		//invocationContext.target = TARGET;
	  			  		return;
	  			  	}
	  			}
			} catch (BusinessDelegateException  businessDelegateException) {
  				errors = handleDelegateException(businessDelegateException);
  			}
	  	}
//    	validate class and subclass
    	String invalidSubClass = "";
    	Collection<DespatchDetailsVO> desDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
	  	log.log(Log.FINE, "Going To validate class and subclass ...in command");
	  	if(desDetailsVOs != null && desDetailsVOs.size() > 0){
		  		for(DespatchDetailsVO despatchVO:desDetailsVOs){
		  			if(despatchVO.getMailClass()!=null && despatchVO.getMailClass().trim().length()>0
		  					&& despatchVO.getMailSubclass()!=null && despatchVO.getMailSubclass().trim().length()>0){
		  			String mailclass = despatchVO.getMailClass().toUpperCase();
		  			String mailsubclass = despatchVO.getMailSubclass().toUpperCase();
		  			if(!mailsubclass.startsWith(mailclass)){
		  				invalidSubClass=mailsubclass;
		  			}}
		  		}
	  	}
	  	if(!"".equals(invalidSubClass)){
	  		actionContext.addError(new ErrorVO("mailtracking.defaults.invalidsubclass"));
			//mailAcceptanceForm.setPopupCloseFlag("N");
	  		//invocationContext.target = TARGET;
	  		return;
		}


    	//validate Mail bags
    	log.log(Log.FINE, "Going To validate Mail bags ...in command");

    	Collection<MailbagVO> mailbgVOs = containerDetailsVO.getMailDetails();
    	Collection<MailbagVO> newMailbgVOs = new ArrayList<MailbagVO>();
    	if(mailbgVOs != null && mailbgVOs.size() > 0){
	  		for(MailbagVO mailbagVO:mailbgVOs){
	  			if("I".equals(mailbagVO.getOperationalFlag())
	  				|| "U".equals(mailbagVO.getOperationalFlag())){
	  				newMailbgVOs.add(mailbagVO);
	  			}
	  		}
	  	}
		  try {
		    new MailTrackingDefaultsDelegate().validateMailBags(newMailbgVOs);
        }catch (BusinessDelegateException businessDelegateException) {
  			errors = handleDelegateException(businessDelegateException);
	  	  }
	  	  if (errors != null && errors.size() > 0) {
	  		actionContext.addAllError(errors);
	  		//mailAcceptanceForm.setPopupCloseFlag("N");
	  		//invocationContext.target = TARGET;
	  		return;
	  	  }

	  	if(newMailbgVOs!=null && !newMailbgVOs.isEmpty()&& "CARDIT".equals(newMailbgVOs.iterator().next().getFromPanel())){
	  	mailAcceptanceVO.setFromCarditList(true);
	  	}else{
	  	mailAcceptanceVO.setFromCarditList(false);	
	  	  }

	  	mailAcceptanceVO.setFromOutboundScreen(true);	 
	  	boolean isScanned=false;
	  	if("Y".equals(warningShow)){
	  	try {
	  		//added as part IASCB-52133 as confirmed by BA starts
	  		String fromPanel = "";
	  		for(MailbagVO newMailbag : newMailbgVOs){
	  			fromPanel = newMailbag.getFromPanel(); 
	  		}
	  		if(fromPanel!=null && !"LYINGLIST".equals(fromPanel)){
	  		//added as part IASCB-52133 as confirmed by BA ends
	  			scannedMailVO = new MailTrackingDefaultsDelegate().doLATValidation(newMailbgVOs,isScanned);
	  		}
        }catch (BusinessDelegateException businessDelegateException) {
  		errors = handleDelegateException(businessDelegateException);
  		for(ErrorVO vo : errors) {
  			if("mailtracking.defaults.war.latvalidation".equals(vo.getErrorCode())){  				
  				vo.setErrorDisplayType(ErrorDisplayType.WARNING);	    	
  			}
  		}
	 	  }
	  

	  		if((scannedMailVO.getErrorDescription()!=null && scannedMailVO.getErrorDescription().equals(LAT_VIOLATED_ERR)) || (errors != null && errors.size() > 0) )
			{
					errors.add(new ErrorVO(LAT_VIOLATED_ERR));
	  		actionContext.addAllError(errors);
	  		return;
	  	}
		}
  	//added by A-7371 as part of ICRD-273840 starts
  	String isCoterminusConfigured=null;
  	try {
		 isCoterminusConfigured = findSystemParameterValue(MailConstantsVO.IS_COTERMINUS_CONFIGURED);
	} catch (BusinessDelegateException businessDelegateException) {
		// TODO Auto-generated catch block
		errors = handleDelegateException(businessDelegateException);
	}
		if (("ADD_MAILBAG".equals(outboundModel.getActionType()) || "CARDIT".equals(outboundModel.getActionType()))) {
  	Collection<MailbagVO> mailbagVOsForValidation = containerDetailsVO.getMailDetails();
  	if(mailbagVOsForValidation != null && mailbagVOsForValidation.size() > 0){
  		for(MailbagVO mailbagVO:mailbagVOsForValidation){
  			String originAirportCode=null;
			 String pACode=null;
			 boolean isCoterminus=false;
			 OfficeOfExchangeVO officeOfExchangeVO=new OfficeOfExchangeVO();
			 if(mailbagVO.getOrigin()!=null && !mailbagVO.getOrigin().isEmpty()){
				 originAirportCode=mailbagVO.getOrigin();
			 }
			 if(mailbagVO.getPaCode()!=null && !mailbagVO.getPaCode().isEmpty()){
				 pACode= mailbagVO.getPaCode();
			 }
			 if(originAirportCode==null ||originAirportCode.isEmpty() ||pACode==null ||pACode.isEmpty()){
			String ooe = mailbagVO.getOoe();
				 
					Page<OfficeOfExchangeVO> orginAirport = null;
			try {
						orginAirport = new MailTrackingDefaultsDelegate()
								.findOfficeOfExchange(mailbagVO.getCompanyCode(), ooe, 1);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			
			if(orginAirport!=null && !orginAirport.isEmpty()){
				officeOfExchangeVO = orginAirport.iterator().next();
			}
			if(originAirportCode==null||originAirportCode.isEmpty()){
				originAirportCode=officeOfExchangeVO.getAirportCode();
			}
			if(pACode==null||pACode.isEmpty()){
				pACode=officeOfExchangeVO.getPoaCode();
			}
			
			// added by A-8353 for ICRD-325826 starts
			if (originAirportCode == null || originAirportCode.isEmpty()) {
				Collection<ArrayList<String>> oECityArpCodes = null;
				Collection<String> gpaCode = new ArrayList<>();
				String airportCode = null;
				gpaCode.add(officeOfExchangeVO.getCode());
				try {
					oECityArpCodes = new MailTrackingDefaultsDelegate()
							.findCityAndAirportForOE(logonAttributes.getCompanyCode(), gpaCode);

				} catch (BusinessDelegateException businessDelegateException) {
					log.log(Log.INFO, "ERROR--SERVER------findCityAndAirportForOE---->>");
				}
				if (oECityArpCodes != null && !oECityArpCodes.isEmpty()) {
					for (ArrayList<String> cityAndArpForOE : oECityArpCodes) {
						airportCode = cityAndArpForOE.get(2);
					}
				}
				originAirportCode=airportCode;
			} // added by A-8353 for ICRD-325826 ends
			
			}	
             if(mailbagVO.getOrigin()==null||mailbagVO.getOrigin().trim().length()==0){
				mailbagVO.setOrigin(originAirportCode); 
				}
				if((mailbagVO.getPaCode()==null||mailbagVO.getPaCode().trim().length()==0)
				    &&officeOfExchangeVO.getPoaCode()!=null){
				mailbagVO.setPaCode(officeOfExchangeVO.getPoaCode());  
				}
				String doe = mailbagVO.getDoe();
				Page<OfficeOfExchangeVO> exchDstAirport = null;
				try {
					exchDstAirport = new MailTrackingDefaultsDelegate()
									.findOfficeOfExchange(mailbagVO.getCompanyCode(), doe, 1);
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				OfficeOfExchangeVO dstOfficeOfExchangeVO=new OfficeOfExchangeVO();
				if(exchDstAirport!=null && !exchDstAirport.isEmpty()){
					dstOfficeOfExchangeVO = exchDstAirport.iterator().next();
				}
				if (dstOfficeOfExchangeVO.getAirportCode() == null) {
					Collection<ArrayList<String>> dECityArpCodes = null;
					Collection<String> gpaCode = new ArrayList<String>();
					String dstAirportCode = null;
					gpaCode.add(dstOfficeOfExchangeVO.getCode());
					try {
						dECityArpCodes = new MailTrackingDefaultsDelegate()
								.findCityAndAirportForOE(logonAttributes.getCompanyCode(), gpaCode);
					} catch (BusinessDelegateException businessDelegateException) {
						Collection<ErrorVO> err = handleDelegateException(businessDelegateException);
						log.log(Log.INFO, "ERROR--SERVER------findCityAndAirportForOE---->>");
					}
					if (dECityArpCodes != null && dECityArpCodes.size() > 0) {
						for (ArrayList<String> cityAndArpForOE : dECityArpCodes) {
							dstAirportCode = cityAndArpForOE.get(2);
						}
					}
					dstOfficeOfExchangeVO.setAirportCode(dstAirportCode);
				} 
				if(mailbagVO.getDestination()==null||mailbagVO.getDestination().trim().length()==0){
					mailbagVO.setDestination(dstOfficeOfExchangeVO.getAirportCode()); 
				}
				if(MailConstantsVO.FLAG_YES.equals(isCoterminusConfigured)){
  			
  			
  				//IASCB-40218 begins
		  		
					 boolean isCoterminusDLV=false;
						
				
				
				
				isCoterminusDLV = new MailTrackingDefaultsDelegate().validateCoterminusairports(
						dstOfficeOfExchangeVO.getAirportCode(), logonAttributes.getAirportCode(),
						MailConstantsVO.RESDIT_DELIVERED, dstOfficeOfExchangeVO.getPoaCode(),mailbagVO.getConsignmentDate());
				if (logonAttributes.getAirportCode().equals(dstOfficeOfExchangeVO.getAirportCode()) ||isCoterminusDLV ) {
					//check whether PACode is applicable to by bass destination check while acceptance, IASCB-55964
					boolean canIgnoreDestCheck = false;
					String paCodeForDestinationByPass = findSystemParameterValue("mail.operations.pacodeforvalidationbypass");
					if(paCodeForDestinationByPass!=null && paCodeForDestinationByPass.trim().length()>0) {
						if(dstOfficeOfExchangeVO.getPoaCode()!=null && paCodeForDestinationByPass.contains(dstOfficeOfExchangeVO.getPoaCode())) {
							canIgnoreDestCheck = true;
						}     
						else {  
							String destForCdtMissingDomMail=null;
							 destForCdtMissingDomMail=findSystemParameterValue(DEST_FOR_CDT_MISSING_DOM_MAL);
							if(destForCdtMissingDomMail!=null &&!"NA".equals(destForCdtMissingDomMail) && destForCdtMissingDomMail.equals(dstOfficeOfExchangeVO.getAirportCode())){
									canIgnoreDestCheck=true;        
								}
						}
					}
					if(!canIgnoreDestCheck) {
			  		actionContext.addError(new ErrorVO("mailtracking.defaults.err.obscannotallowedatdest"));
			  		return;
					}
			  		

				}


					if ((mailbagVO.getTransferFromCarrier()==null) && (!logonAttributes.getAirportCode().equals(originAirportCode))) {
						try {
							isCoterminus = new MailTrackingDefaultsDelegate().validateCoterminusairports(
									originAirportCode, logonAttributes.getAirportCode(),
									MailConstantsVO.RESDIT_RECEIVED, pACode,mailbagVO.getConsignmentDate());
			   Map<String, String> existigWarningMap = outboundModel.getWarningMessagesStatus();
	    	   String coterMinusWarningStatus = "N";
	    	   if(existigWarningMap != null && existigWarningMap.size()>0 && existigWarningMap.containsKey(WARN_COTERMINUS)) {
	    		   coterMinusWarningStatus=existigWarningMap.get(WARN_COTERMINUS);
	    	   }
				   if(isCoterminus){ 
			    		continue;
			    	}else{
			    		 if("N".equals(coterMinusWarningStatus)) {
			    		  warningMap.put(WARN_COTERMINUS, "N");
			    		  outboundModel.setWarningMessagesStatus(warningMap);
			    		  ErrorVO warningError = new ErrorVO(WARN_COTERMINUS);
			    		  warningError.setErrorDisplayType(ErrorDisplayType.WARNING);
			    		  errors.add(warningError);
			    		break;
			    		 }
			    	}
		  		//}
	        }catch (BusinessDelegateException businessDelegateException) {
	  			errors = handleDelegateException(businessDelegateException);
		  	  }
					}
		    	  }
		if( errors.isEmpty()){
			doSecurityScreeningValidations(mailbagVO,mailAcceptanceVO,flightCarrierFlag,logonAttributes,actionContext, warningMap,outboundModel);
		}
  	}
		       }
  	}
  	if (errors != null && errors.size() > 0) {
  		actionContext.addAllError(errors);
  		return;
  	}
		int sameOE = 0;
		/*	Collection<DespatchDetailsVO> despatchVOs = containerDetailsVO.getDesptachDetailsVOs();
		if(despatchVOs != null && despatchVOs.size() > 0){
	  		for(DespatchDetailsVO despatchVO:despatchVOs){
  				String ooe = despatchVO.getOriginOfficeOfExchange();
		    	String doe = despatchVO.getDestinationOfficeOfExchange();
		    	if(ooe.trim().length() == 6){
			      if(doe.trim().length() == 6){
			        if (ooe.equals(doe)) {
		        	   sameOE = 1;
		        	   String pk = new StringBuilder(despatchVO.getOriginOfficeOfExchange())
        							.append(despatchVO.getDestinationOfficeOfExchange())
        							//.append(despatchVO.getMailClass())
        							//added by anitha for change in pk
        							.append(despatchVO.getMailCategoryCode())
        							.append(despatchVO.getMailSubclass())
        							.append(despatchVO.getYear())
        							.append(despatchVO.getDsn()).toString();
		        			 invocationContext.addError(new ErrorVO("mailtracking.defaults.sameoe",new Object[]{pk}));
		        	}
			      }
			    }
	  		}
	  	}
		if(sameOE == 1){
			mailAcceptanceForm.setPopupCloseFlag("N");
			invocationContext.target = TARGET;
	  		return;
		}
*/
//  	Check for same OOE and DOE for mail bags
	sameOE = 0;
	Collection<MailbagVO> mailbagVOs = containerDetailsVO.getMailDetails();
	if(mailbagVOs != null && mailbagVOs.size() > 0){
  		for(MailbagVO mailbagVO:mailbagVOs){
				String ooe = mailbagVO.getOoe();
	    	String doe = mailbagVO.getDoe();
	    	if(ooe.trim().length() == 6){
		       if(doe.trim().length() == 6){
		    	  if (ooe.equals(doe)) {
	        		 sameOE = 1;
	        		 actionContext.addError(new ErrorVO("mailtracking.defaults.sameoe",new Object[]{mailbagVO.getMailbagId()}));
		    	  }
		       }
		    }
  		}
  	}
	if(sameOE == 1){
	//	mailAcceptanceForm.setPopupCloseFlag("N");
	//	invocationContext.target = TARGET;
  		return;
	}


//Duplicate check for Mail bags
//  	Collection<MailbagVO> firstMailbagVOs = containerDetailsVO.getMailDetails();
//  	Collection<MailbagVO> secMailbagVOs = containerDetailsVO.getMailDetails();
//  	
//  	if(firstMailbagVOs != null && firstMailbagVOs.size() > 0){
//  		int count = 0;
//  		for(MailbagVO fstMailbagVO:firstMailbagVOs){
//  			for(MailbagVO secMailbagVO:secMailbagVOs){
//  				if(fstMailbagVO.getMailbagId().equals(secMailbagVO.getMailbagId()) &&
//  						!"D".equals(secMailbagVO.getOperationalFlag())){             
//  					count++;
//  				}
//  				if(fstMailbagVO.getMailbagId().equals(secMailbagVO.getMailbagId()) &&
//  						"D".equals(secMailbagVO.getOperationalFlag())&&
//  						"I".equals(fstMailbagVO.getOperationalFlag())){                
//  					
//  					fstMailbagVO.setOperationalFlag("U"); 
//  					secMailbagVO.setAcknowledge("D");          
//  					
//  				}
//  				
//  			}
//  			if(count > 1){
//  				duplicate = 1;
//  				
//  				break;
//  			}
//  			count = 0;
//  		}
//  	}
  	Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
  	Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();
  	containers.add(containerDetailsVO);
  	int duplicate = 0;
  	try{
		containerDetailsVOs=new MailTrackingDefaultsDelegate().findMailbagsInContainer(containers);
	}catch(BusinessDelegateException businessDelegateException){
		errors = handleDelegateException(businessDelegateException);
		actionContext.addAllError(errors);
	}
  	boolean modify_flag =false;
		modify_flag=containerDetailsVO.isMailUpdateFlag();
  	for(ContainerDetailsVO containerDetailsVO1:containerDetailsVOs){
  		if(containerDetailsVO1.getMailDetails()!= null && containerDetailsVO1.getMailDetails().size()>0){
  			int count = 0;
  			for(MailbagVO mailbagVO:containerDetailsVO1.getMailDetails()){
  				
  				if(containerDetailsVO.getMailDetails()!=null && containerDetailsVO.getMailDetails().size()>0){
  					for(MailbagVO mailbagVO1:containerDetailsVO.getMailDetails()){
  						
  						if(!modify_flag&&(mailbagVO1.getMailSequenceNumber()!=mailbagVO.getMailSequenceNumber()) ) {
  						if(mailbagVO.getMailbagId().equals(mailbagVO1.getMailbagId()) &&
  								(!"U".equals(mailbagVO1.getOperationalFlag()) && !"D".equals(mailbagVO1.getOperationalFlag()))) {  
  		  					count++;
  		  				  }
  						}
  						else{
  							Collection<MailbagVO> secMailbagVOs = containerDetailsVO.getMailDetails();
  							for(MailbagVO secMailbagVO:secMailbagVOs){
  			  				//if(mailbagVO1.getMailbagId().equals(secMailbagVO.getMailbagId()) &&
  			  					//	!"D".equals(secMailbagVO.getOperationalFlag())){             
  			  				//	count++;
  			  				//}
  			  				if(mailbagVO1.getMailbagId().equals(secMailbagVO.getMailbagId()) &&
  			  					mailbagVO.getMailbagId().equals(mailbagVO1.getMailbagId())&&
  			  						"D".equals(secMailbagVO.getOperationalFlag())&&
  			  						"I".equals(mailbagVO1.getOperationalFlag())){                
  			  					
  			  				mailbagVO1.setOperationalFlag("U"); 
  			  				secMailbagVO.setAcknowledge("D");          
  			  				}
  			  			if(mailbagVO1.getMailbagId().equals(secMailbagVO.getMailbagId()) &&
  			  					mailbagVO.getMailbagId().equals(mailbagVO1.getMailbagId())&&
  			  						"U".equals(mailbagVO1.getOperationalFlag())&&
  			  						mailbagVO1.getTransferFromCarrier()!=null&& mailbagVO1.getTransferFromCarrier().trim().length()>0
  			  						&&(mailbagVO.getTransferFromCarrier()==null||mailbagVO.getTransferFromCarrier().trim().length()==0
  			  						||!mailbagVO.getTransferFromCarrier().equals(mailbagVO1.getTransferFromCarrier()))){     
  			  		                errors = validateTransferCarrierCode( logonAttributes, errors,
											outboundModel, mailAcceptanceVO, mailbagVO1);
  			  		            if (errors != null &&! errors.isEmpty()) {            			
  			  		    		Object[] obj = {mailbagVO1.getTransferFromCarrier()};
  			  		    		actionContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
  			  		    		return;
  			  		    		}   
  			  				}
  			  				
  			  			}
  						}
  		  					
  		  					
  					}
  					if(count >= 1){
  		  				duplicate = 1;
  		  				
  		  				break;
  		  			}
  		  			count = 0;
  				}
  			}
  			
  		}
  	}
  	if(!mailAcceptanceVO.isModifyAfterExportOpr()&&outboundModel.isDisableForModify()){
  		for(MailbagVO mailbagVO1:containerDetailsVO.getMailDetails()){
  			if(mailbagVO1.getTransferFromCarrier()!=null&& mailbagVO1.getTransferFromCarrier().trim().length()>0){
  				errors = validateTransferCarrierCode( logonAttributes, errors,
						outboundModel, mailAcceptanceVO, mailbagVO1);
	            if (errors != null &&! errors.isEmpty()) {              		   	
	    		Object[] obj = {mailbagVO1.getTransferFromCarrier()};
	    		actionContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
	    		return;
	    		}   
     
  			}
  		}
  		
  	}
  	containerDetailsVO.setMailModifyflag(false);
  	if(duplicate == 1){
  		actionContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.duplicatemailbag"));
  		//mailAcceptanceForm.setPopupCloseFlag("N");
  		//actionContext.target = TARGET;
  		return;
  	}

//	 checking duplicate mailbags across containers
  	int duplicateAcross = 0;
  	String container = "";
  	String mailId = "";
  //	MailAcceptanceVO mailAcceptVO = outboundModel.getMailAcceptance();
 // 	log.log(Log.FINE, "**********mailAcceptVO***********:::", mailAcceptVO);
  	Collection<ContainerDetailsVO> allContainerDtlVOs = mailAcceptanceVO.getContainerDetails();
  	log.log(Log.FINE, "**********mailAcceptVO -all containers***********:::", allContainerDtlVOs);
	//ADDED FOR BUG 87046 STARTS
	Collection<ContainerDetailsVO> containerDtlVOs =  null;
//	containerDtlVOs.add(containerDetailsVO);
log.log(Log.FINE, "*********containerDtlVOs*********:::",
			containerDtlVOs);
if(allContainerDtlVOs!=null && allContainerDtlVOs.size()>0){
	if(containerDtlVOs != null && containerDtlVOs.size() > 0){
	for(ContainerDetailsVO cntVO :allContainerDtlVOs){
		Collection<MailbagVO> mailBagsinCont = cntVO.getMailDetails();
		if(mailBagsinCont != null && mailBagsinCont.size() > 0){
			for(MailbagVO fstMailbagVO:mailBagsinCont){
				for(ContainerDetailsVO popupVO:containerDtlVOs){
					if(!fstMailbagVO.getContainerNumber().equals(popupVO.getContainerNumber())){
						Collection<MailbagVO> secMailVOs = popupVO.getMailDetails();
						if(secMailVOs != null && secMailVOs.size() > 0){
							for(MailbagVO secMailbagVO:secMailVOs){
								if(fstMailbagVO.getMailbagId().equals(secMailbagVO.getMailbagId())){
									duplicateAcross = 1;
									container = cntVO.getContainerNumber();
									mailId = fstMailbagVO.getMailbagId();
								}
								
								
							}
							
								}
							}
						}
					}
				}
			}
		}
	}

//Added for bug ICRD-100112 by A-5526 starts
//checking duplicate mailbags within a session itself
if(containerDtlVOs != null && containerDtlVOs.size() > 0){
for(ContainerDetailsVO cntVO :containerDtlVOs){
	Collection<MailbagVO> mailBagsinCont = cntVO.getMailDetails();
	if(mailBagsinCont != null && mailBagsinCont.size() > 0){
		for(MailbagVO fstMailbagVO:mailBagsinCont){
			
				if(!fstMailbagVO.getContainerNumber().equals(containerDetailsVO.getContainerNumber())){
					Collection<MailbagVO> secMailbagVOsFromSession = containerDetailsVO.getMailDetails();
					if(secMailbagVOsFromSession != null && secMailbagVOsFromSession.size() > 0){
						for(MailbagVO secMailbagVO:secMailbagVOsFromSession){
							if(fstMailbagVO.getMailbagId().equals(secMailbagVO.getMailbagId())){
								duplicateAcross = 1;      
								container = cntVO.getContainerNumber();  
								mailId = fstMailbagVO.getMailbagId();
							}
							
							
						}
						
							}
						}
					
				}
			}
		}
	}
// Added for bug ICRD-100112 by A-5526 ends

	if(duplicateAcross == 1){
		actionContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.duplicatemailbagacross",new Object[]{container,mailId}));
  		//mailAcceptanceForm.setPopupCloseFlag("N");
  		///invocationContext.target = TARGET;
  		return;
  	}
	   
	// Validate Transfer From Carrier
	AirlineDelegate airlineDelegate = new AirlineDelegate();
	if(containerDetailsVO.getTransferFromCarrier() != null
		&& !"".equals(containerDetailsVO.getTransferFromCarrier())){
    	try {        			
    		AirlineValidationVO airlineValidationVO = airlineDelegate.validateAlphaCode(
					logonAttributes.getCompanyCode(),containerDetailsVO.getTransferFromCarrier());

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {            			
			Object[] obj = {containerDetailsVO.getTransferFromCarrier()};
			actionContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
			//invocationContext.target = TARGET;
			return;
		}
	}
	
	// Validate Destination
	/*AreaDelegate areaDelegate = new AreaDelegate();
	AirportValidationVO airportValidationVO = null;
	String destination = mailAcceptanceForm.getDestn();        	
	if (destination != null && !"".equals(destination)) {        		
		try {        			
			airportValidationVO = areaDelegate.validateAirportCode(
					logonAttributes.getCompanyCode(),destination.toUpperCase());
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {            			
			Object[] obj = {destination.toUpperCase()};
			actionContext.addError(new ErrorVO("mailtracking.defaults.invalidairport",obj));
			//invocationContext.target = TARGET;
			return;
		}
	}*/
	
	
//	Validating Mailbag & Despatches for similar key
	/*
	 * This code will take any newly inserted Mailbag or Despatch and construct a key from that.
	 * The key comprises {1.OOE 2.DOE 3.MAL CAT CODE 4.Subclass 5.DSN 6.YEAR}
	 * and check of any similar key in any of the Mail or Despatch already present.
	 * The case may have two ways.
	 * 1.Containers already present with Mailbags or Despatches
	 * 2.Containers created newly with Mailbags or Despatches.
	 * In First case, the Container Details can be taken from ContainerDetailsVO
	 * and other details(reg. Mailbag & Despatch) can be taken from DSNVO
	 * In Second case, the container details and all other details can be taken from MailAcceptanceVO
	 *  
	 */
	Collection<MailbagVO> mailVOs=containerDetailsVO.getMailDetails();
	
//	Collection<DSNVO> despatchVOS= new MailTrackingDefaultsDelegate().getDSNsForContainer(containerDetailsVO);
	Collection<ContainerDetailsVO> contDtlsVOs=mailAcceptanceVO.getContainerDetails();
	log.log(Log.FINE, "*********contDtlsVOs********", contDtlsVOs);
	String key="";
	String connum="";
	String errFlg=MailConstantsVO.FLAG_NO;
	Integer errVal=0;
	/*
	 * This Commented Code can by used in case, any performance issues
	 * comes with the present code written below.
	 * Before that, make sure all the changes are done in this code too as per the present code.
	 */
//	HashSet<String> hashKeySet=new HashSet<String>();
//	if((mailVOs!=null && mailVOs.size()>0)   
//			&&(despatchVOS!=null && despatchVOS.size()>0)){
//		for(MailbagVO mailbagVO:mailVOs){
//			hashKeySet.add(mailbagVO.getOoe()+HYPHEN
//					+mailbagVO.getDoe()+HYPHEN
//					+mailbagVO.getMailCategoryCode()+HYPHEN
//					+mailbagVO.getMailSubclass()+HYPHEN
//					+mailbagVO.getDespatchSerialNumber()+HYPHEN
//					+mailbagVO.getYear());
//		}
//		if(!hashKeySet.isEmpty()){
//			for(DespatchDetailsVO despatchVO:despatchVOS){
//				if(hashKeySet.contains(despatchVO.getOriginOfficeOfExchange()+HYPHEN
//						+despatchVO.getDestinationOfficeOfExchange()+HYPHEN
//						+despatchVO.getMailCategoryCode()+HYPHEN
//						+despatchVO.getMailSubclass()+HYPHEN
//						+despatchVO.getDsn()+HYPHEN
//						+despatchVO.getYear())){
//					errFlg=MailConstantsVO.FLAG_YES;
//					key=despatchVO.getOriginOfficeOfExchange()+HYPHEN
//					+despatchVO.getDestinationOfficeOfExchange()+HYPHEN
//					+despatchVO.getMailSubclass()+HYPHEN
//					+despatchVO.getDsn()+HYPHEN
//					+despatchVO.getYear();
//					break;
//				}
//			}
//		}
//	}
	if((mailVOs!=null && mailVOs.size()>0)){/*    	
		for(MailbagVO mailbagVO:mailVOs){
			if("I".equals(mailbagVO.getOperationalFlag())){
				key=mailbagVO.getOoe()+HYPHEN
				+mailbagVO.getDoe()+HYPHEN
				+mailbagVO.getMailCategoryCode()+HYPHEN
				+mailbagVO.getMailSubclass()+HYPHEN
				+mailbagVO.getDespatchSerialNumber()+HYPHEN
				+mailbagVO.getYear();    				
				for(DSNVO despatchVO:despatchVOS){    		
					if(key.equals(despatchVO.getOriginExchangeOffice()+HYPHEN
							+despatchVO.getDestinationExchangeOffice()+HYPHEN
		    				+despatchVO.getMailCategoryCode()+HYPHEN
							+despatchVO.getMailSubclass()+HYPHEN
							+despatchVO.getDsn()+HYPHEN
							+despatchVO.getYear())){
						errFlg=MailConstantsVO.FLAG_YES;
						errVal=1;
						connum=despatchVO.getContainerNumber();
						break;
					}
				}
				if((MailConstantsVO.FLAG_NO.equals(errFlg))
						&& (contDtlsVOs!=null && contDtlsVOs.size()>0)){
					for(ContainerDetailsVO containerDetailVO : contDtlsVOs){
						if(containerDetailVO.getDesptachDetailsVOs() != null 
								&& containerDetailVO.getDesptachDetailsVOs().size()>0){
							for(DespatchDetailsVO despatchVO:containerDetailVO.getDesptachDetailsVOs()){    		
								if(key.equals(despatchVO.getOriginOfficeOfExchange()+HYPHEN
										+despatchVO.getDestinationOfficeOfExchange()+HYPHEN
					    				+despatchVO.getMailCategoryCode()+HYPHEN
										+despatchVO.getMailSubclass()+HYPHEN
										+despatchVO.getDsn()+HYPHEN
										+despatchVO.getYear())){
									errFlg=MailConstantsVO.FLAG_YES;
									errVal=2;
									connum=despatchVO.getContainerNumber();
									break;
								}
							} 
						}
						if((MailConstantsVO.FLAG_NO.equals(errFlg))
								&&(containerDetailVO.getDsnVOs() != null 
								&& containerDetailVO.getDsnVOs().size()>0)){
							for(DSNVO dsnVO:containerDetailVO.getDsnVOs()){    		
								if(key.equals(dsnVO.getOriginExchangeOffice()+HYPHEN
										+dsnVO.getDestinationExchangeOffice()+HYPHEN
					    				+dsnVO.getMailCategoryCode()+HYPHEN
										+dsnVO.getMailSubclass()+HYPHEN
										+dsnVO.getDsn()+HYPHEN
										+dsnVO.getYear())){
									if(MailConstantsVO.FLAG_NO.equals(dsnVO.getPltEnableFlag())){
										errFlg=MailConstantsVO.FLAG_YES;
										errVal=2;
										connum=containerDetailVO.getContainerNumber();
										break;
									}
								}
							}
						}
						if(MailConstantsVO.FLAG_YES.equals(errFlg)){
							break;    					
						}
					}
				}
				if(MailConstantsVO.FLAG_YES.equals(errFlg)){
					break;    					
				}
			}
		}
	*/}
	/*if((despatchVOS!=null && despatchVOS.size()>0) 
			&& (MailConstantsVO.FLAG_NO.equals(errFlg))){
		for(DSNVO despatchVO:despatchVOS){
			if("I".equals(despatchVO.getOperationFlag())){
				key=despatchVO.getOriginExchangeOffice()+HYPHEN
				+despatchVO.getDestinationExchangeOffice()+HYPHEN
				+despatchVO.getMailCategoryCode()+HYPHEN
				+despatchVO.getMailSubclass()+HYPHEN
				+despatchVO.getDsn()+HYPHEN
				+despatchVO.getYear();
				for(MailbagVO mailbagVO:mailVOs){    		
					if(key.equals(mailbagVO.getOoe()+HYPHEN
							+mailbagVO.getDoe()+HYPHEN
		    				+mailbagVO.getMailCategoryCode()+HYPHEN
							+mailbagVO.getMailSubclass()+HYPHEN
							+mailbagVO.getDespatchSerialNumber()+HYPHEN
							+mailbagVO.getYear())){
						errFlg=MailConstantsVO.FLAG_YES;
						errVal=1;
						connum=mailbagVO.getContainerNumber();
						break;
					}
				}
				if((MailConstantsVO.FLAG_NO.equals(errFlg))
						&& (contDtlsVOs!=null && contDtlsVOs.size()>0)){
					for(ContainerDetailsVO containerDetailVO : contDtlsVOs){
						if(containerDetailVO.getMailDetails()!=null 
								&& containerDetailVO.getMailDetails().size()>0){
							for(MailbagVO mailbagVO:containerDetailVO.getMailDetails()){    		
								if(key.equals(mailbagVO.getOoe()+HYPHEN
										+mailbagVO.getDoe()+HYPHEN
					    				+mailbagVO.getMailCategoryCode()+HYPHEN
										+mailbagVO.getMailSubclass()+HYPHEN
										+mailbagVO.getDespatchSerialNumber()+HYPHEN
										+mailbagVO.getYear())){
									errFlg=MailConstantsVO.FLAG_YES;
									errVal=3;
									connum=mailbagVO.getContainerNumber();
									break;
								}
							}
						}
						if((MailConstantsVO.FLAG_NO.equals(errFlg)) 
								&& (containerDetailVO.getDsnVOs() != null 
								&& containerDetailVO.getDsnVOs().size()>0)){
							for(DSNVO dsnVO:containerDetailVO.getDsnVOs()){    		
								if(key.equals(dsnVO.getOriginExchangeOffice()+HYPHEN
										+dsnVO.getDestinationExchangeOffice()+HYPHEN
					    				+dsnVO.getMailCategoryCode()+HYPHEN
										+dsnVO.getMailSubclass()+HYPHEN
										+dsnVO.getDsn()+HYPHEN
										+dsnVO.getYear())){
									if(MailConstantsVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())){
										errFlg=MailConstantsVO.FLAG_YES;
										errVal=3;
										connum=containerDetailVO.getContainerNumber();
										break;
									}
								}
							}
						}
						if(MailConstantsVO.FLAG_YES.equals(errFlg)){
							break;    					
						}
					}
				}
				if(MailConstantsVO.FLAG_YES.equals(errFlg)){
					break;    					
				}
			}
		}
	}*/
	/*if(errFlg.equals(MailConstantsVO.FLAG_YES)){
		if(errVal==1){
			Object[] obj={key.toUpperCase()};
			log.log(Log.FINE, "SIMILAR-MAILBAG-DSN-KEY...--->", key.toUpperCase());
			actionContext.addError(new ErrorVO("mailtracking.defaults.acceptmail.similarmaildsnkey",obj));
			//actionContext.target = TARGET;
			return;
		}else if(errVal==2){
			Object[] obj={key.toUpperCase(),connum.toUpperCase()};
			log.log(Log.FINE, "SIMILAR-DSN-KEY...--->", key.toUpperCase());
			actionContext.addError(new ErrorVO("mailtracking.defaults.acceptmail.similardsnkey",obj));
			//actionContext.target = TARGET;
			return;    			
		}else if(errVal==3){
			Object[] obj={key.toUpperCase(),connum.toUpperCase()};
			log.log(Log.FINE, "SIMILAR-MAILBAG-KEY...--->", key.toUpperCase());
			actionContext.addError(new ErrorVO("mailtracking.defaults.acceptmail.similarmailkey",obj));
			//invocationContext.target = TARGET;
			return;    		
			
		}
	*/

	//Updating Session
	
	//Collection<ContainerDetailsVO> containerDetailsVOs =  null;
	/*log.log(Log.FINE, "containerDetailsVOs ...in command",
			containerDetailsVOs);
	Collection<ContainerDetailsVO> newContainerDetailsVOs = new ArrayList<ContainerDetailsVO>();
	if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
		for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
				if(containerDtlsVO.getContainerNumber().equals(containerDetailsVO.getContainerNumber())){
					newContainerDetailsVOs.add(containerDetailsVO);
				}else{
					newContainerDetailsVOs.add(containerDtlsVO);
    			}
		}
	}
	//mailAcceptanceSession.setContainerDetailsVOs(newContainerDetailsVOs);
	log.log(Log.FINE, "newContainerDetailsVOs ...in command",
			newContainerDetailsVOs);
	// making Summary DSN VOs
	newContainerDetailsVOs = makeDSNVOs(newContainerDetailsVOs,logonAttributes);
	//n
*/	//mailAcceptanceSession.setContainerDetailsVOs(newContainerDetailsVOs);

//	validate DSN VOs
	log.log(Log.FINE, "Going To validate DSN VOs ...in command");
	  try {
	    new MailTrackingDefaultsDelegate().validateDSNs(containerDetailsVO.getDsnVOs());
    }catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
  	  }
  	  if (errors != null && errors.size() > 0) {
  		actionContext.addAllError(errors);
  		//mailAcceptanceForm.setPopupCloseFlag("N");
  		//invocationContext.target = TARGET;
  		return;
  	  }

	//updating in main screen VOs
  	  //Commented by A-4810 for icrd-90664
  	  //This is now handled in embargovalidationcommand and closemailacceptancecommand for various scenarios
	/*Collection<ContainerDetailsVO> contDetailsVOs = mailAcceptanceVO.getContainerDetails();
	log.log(Log.FINE, "contDetailsVOs ...in mainscreen", contDetailsVOs);
	Collection<ContainerDetailsVO> newVOs = new ArrayList<ContainerDetailsVO>();
	int flag = 0;
	if(contDetailsVOs != null && contDetailsVOs.size() > 0){
	  for(ContainerDetailsVO mainscreenVO:contDetailsVOs){
		  if(newContainerDetailsVOs != null && newContainerDetailsVOs.size() > 0){
			for(ContainerDetailsVO popupVO:newContainerDetailsVOs){
			  if(mainscreenVO.getContainerNumber().equals(popupVO.getContainerNumber())){
				  newVOs.add(popupVO);
				  flag = 1;
			  }
	        }
		  }
		  if(flag == 1){
			  flag = 0;
		  }else{
			  newVOs.add(mainscreenVO);
		  }
	  }
	}
	
	log.log(Log.FINE, "newVOs ...in first", newVOs);
	flag = 0;
	if(newContainerDetailsVOs != null && newContainerDetailsVOs.size() > 0){
	for(ContainerDetailsVO popupVO:newContainerDetailsVOs){
	      if(contDetailsVOs != null && contDetailsVOs.size() > 0){
		  for(ContainerDetailsVO mainscreenVO:contDetailsVOs){
			   if(mainscreenVO.getContainerNumber().equals(popupVO.getContainerNumber())){
					  flag = 1;
			   }
		    }
		  }
		  if(flag == 0){
			  newVOs.add(popupVO);
		  }else{
			  flag = 0;
		  }
	  }
	}
	
}*/
  	 actionContext.setAttribute("flightCarrierFlag", outboundModel.getFlightCarrierflag());
	 actionContext.setAttribute("containerDetails", containerDetailsVO);
	 actionContext.setAttribute("mailAcceptanceDetails", mailAcceptanceVO);
			
  	//outboundModel.setSelectedContainer(selectedContainer);
	 ResponseVO responseVO = new ResponseVO();
	    List<OutboundModel> results = new ArrayList();
	    results.add(outboundModel);
	    responseVO.setResults(results);
	    actionContext.setResponseVO(responseVO);
}

/**
 * Mehtod to update DSN Summary VOs
 * 
 * @param newContainerDetailsVOs
 * @param logonAttributes
 * @return Collection<ContainerDetailsVO>
 */
public Collection<ContainerDetailsVO> makeDSNVOs(
		Collection<ContainerDetailsVO> newContainerDetailsVOs,
		LogonAttributes logonAttributes) {

	if (newContainerDetailsVOs != null && newContainerDetailsVOs.size() > 0) {
		for (ContainerDetailsVO popupVO : newContainerDetailsVOs) {
			HashMap<String, DSNVO> dsnMap = new HashMap<String, DSNVO>();
			Collection<DespatchDetailsVO> despatchDetailsVOs = popupVO
					.getDesptachDetailsVOs();
			if (despatchDetailsVOs != null && despatchDetailsVOs.size() > 0) {
				for (DespatchDetailsVO despatchVO : despatchDetailsVOs) {
					int numBags = 0;
					double bagWgt = 0;
					double accptbagvol = 0;
					int stdNumBags = 0;
					double stdBagWgt = 0;
					double stdBagVol = 0;
					String outerpk = despatchVO.getOriginOfficeOfExchange()
							+ despatchVO.getDestinationOfficeOfExchange()
							// +despatchVO.getMailClass()
							// added by anitha for change in pk
							+ despatchVO.getMailCategoryCode()
							+ despatchVO.getMailSubclass()
							+ despatchVO.getDsn() + despatchVO.getYear();
					if (dsnMap.get(outerpk) == null) {
						DSNVO dsnVO = new DSNVO();
						dsnVO.setCompanyCode(logonAttributes
								.getCompanyCode());
						dsnVO.setDsn(despatchVO.getDsn());
						dsnVO.setOriginExchangeOffice(despatchVO
								.getOriginOfficeOfExchange());
						dsnVO.setDestinationExchangeOffice(despatchVO
								.getDestinationOfficeOfExchange());
						dsnVO.setMailClass(despatchVO.getMailClass());
						// added by anitha for change in pk
						dsnVO.setMailCategoryCode(despatchVO
								.getMailCategoryCode());
						dsnVO.setMailSubclass(despatchVO.getMailSubclass());
						dsnVO.setYear(despatchVO.getYear());
						dsnVO.setPltEnableFlag("N");
						for (DespatchDetailsVO innerVO : despatchDetailsVOs) {
							String innerpk = innerVO
									.getOriginOfficeOfExchange()
									+ innerVO
											.getDestinationOfficeOfExchange()
									// +innerVO.getMailClass()
									// added by anitha for change in pk
									+ innerVO.getMailCategoryCode()
									+ innerVO.getMailSubclass()
									+ innerVO.getDsn() + innerVO.getYear();
							if (outerpk.equals(innerpk)) {
								numBags = numBags
										+ innerVO.getAcceptedBags();
								/*bagWgt = bagWgt
										+ innerVO.getAcceptedWeight();*/
								bagWgt = bagWgt
										+ innerVO.getAcceptedWeight().getRoundedSystemValue(); //added by A-7550
								//accptbagvol = accptbagvol
								//		+ innerVO.getAcceptedVolume();
								accptbagvol = accptbagvol
										+ innerVO.getAcceptedVolume().getRoundedSystemValue(); //Added by A-7550 
								stdNumBags = stdNumBags
										+ innerVO.getStatedBags();
								/*stdBagWgt = stdBagWgt
										+ innerVO.getStatedWeight();*/
								stdBagWgt = stdBagWgt
										+ innerVO.getStatedWeight().getRoundedSystemValue();//added by A-7550
								//stdBagVol = stdBagVol
									//	+ innerVO.getStatedVolume();
								stdBagVol = stdBagVol
											+ innerVO.getStatedVolume().getRoundedSystemValue(); //added by A-7550
							}
						}
						dsnVO.setBags(numBags);
						//dsnVO.setWeight(doubleFormatter(bagWgt, 2));
					//	dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT, doubleFormatter(bagWgt, 2))); //Added by A-7550
						dsnVO.setStatedBags(stdNumBags);
						//dsnVO
						//		.setStatedWeight(doubleFormatter(stdBagWgt,
						//				2));
					//	dsnVO
					//	.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,doubleFormatter(stdBagWgt,
					//			2)));        //Added by A-7550
						//dsnVO.setAcceptedVolume(doubleFormatter(
						//		accptbagvol, 2));
					//	dsnVO.setAcceptedVolume(new Measure(UnitConstants.VOLUME,doubleFormatter(
					//			accptbagvol, 2)));     //Added by A-7550
						//dsnVO
							//	.setStatedVolume(doubleFormatter(stdBagVol,
							//			2));
					//	dsnVO
					//	.setStatedVolume(new Measure(UnitConstants.VOLUME,doubleFormatter(stdBagVol,
					//			2)));                          //Added by A-7550
						dsnMap.put(outerpk, dsnVO);
						numBags = 0;
						bagWgt = 0;
						accptbagvol = 0;
						stdNumBags = 0;
						stdBagWgt = 0;
						stdBagVol = 0;
					}
				}
			}

			Collection<MailbagVO> mailbagVOs = popupVO.getMailDetails();
			if (mailbagVOs != null && mailbagVOs.size() > 0) {
				for (MailbagVO mailbagVO : mailbagVOs) {
					    int dsnCount=0;
					    int newDsnCount=0;
					    double deletedBagWgt = 0;
					    
					int numBags = 0;
					double bagWgt = 0;
					double bagVol = 0;
					String outerpk = mailbagVO.getOoe()
							+ mailbagVO.getDoe()
							+ (mailbagVO.getMailSubclass())
							+ mailbagVO.getMailCategoryCode()
							+ mailbagVO.getDespatchSerialNumber()
							+ mailbagVO.getYear();
					if (dsnMap.get(outerpk) == null) {
						DSNVO dsnVO = new DSNVO();
						dsnVO.setCompanyCode(logonAttributes
								.getCompanyCode());
						dsnVO.setDsn(mailbagVO.getDespatchSerialNumber());
						dsnVO.setOriginExchangeOffice(mailbagVO.getOoe());
						dsnVO.setDestinationExchangeOffice(mailbagVO
								.getDoe());
						dsnVO.setMailClass(mailbagVO.getMailSubclass()
								.substring(0, 1));
						dsnVO.setMailSubclass(mailbagVO.getMailSubclass());
						dsnVO.setMailCategoryCode(mailbagVO
								.getMailCategoryCode());
						dsnVO.setYear(mailbagVO.getYear());
						dsnVO.setPltEnableFlag("Y");
						if("D".equals(mailbagVO.getOperationalFlag())){
							dsnVO.setOperationFlag("D");    
														}      
						for (MailbagVO innerVO : mailbagVOs) {
							    if("D".equals(innerVO.getAcknowledge())){     
							    	break;    
							    }
							    
							    if("I".equals(innerVO.getOperationalFlag())){
							    	newDsnCount++; 
							    	
							    }
							String innerpk = innerVO.getOoe()
									+ innerVO.getDoe()
									+ (innerVO.getMailSubclass())
									+ innerVO.getMailCategoryCode()
									+ innerVO.getDespatchSerialNumber()
									+ innerVO.getYear();
							
							if (outerpk.equals(innerpk)) {
								if("D".equals(innerVO.getOperationalFlag())){
							    	dsnVO.setOperationFlag("D");
							    	deletedBagWgt=deletedBagWgt+innerVO.getWeight().getRoundedSystemValue();//modified by A-7550
							    	if( "B".equals(popupVO.getContainerType())){      
							    		innerVO.setAcknowledge("U");        
							    	}            
							    }
								dsnCount++;    
									if(!"D".equals(innerVO.getOperationalFlag())) {
								numBags = numBags + 1;
								if(innerVO.getWeight()!=null){
								bagWgt = bagWgt + innerVO.getWeight().getRoundedSystemValue();//modified by A-7550
								}
								//bagVol = bagVol + innerVO.getVolume();
								bagVol = bagVol + innerVO.getVolume().getRoundedSystemValue(); //Added by A-7550
								
							}
							}
							
							    
						}
						dsnVO.setStatedBags(dsnCount);  
						dsnVO.setShipmentCount(newDsnCount);
						//dsnVO.setShipmentWeight(deletedBagWgt);   
						dsnVO.setShipmentWeight(new Measure(UnitConstants.MAIL_WGT, deletedBagWgt));  //Added by A-7550							
						dsnVO.setBags(numBags);
						//dsnVO.setWeight(doubleFormatter(bagWgt, 2));
						/*
						 * A-5526 Added For ICRD-286407 Begin
						 * Now weight conversion from hg to kg is not happening.
						 * Remove the code block after releasing Measure changes CR ICRD-211400 . 
						  */
						if(!"AA".equals(logonAttributes.getCompanyCode())){
							bagWgt=bagWgt/10; 
			}
						//dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,doubleFormatter(bagWgt, 2))); //Added by A-7550
						//dsnVO.setAcceptedVolume(doubleFormatter(bagVol, 2));
					//	dsnVO.setAcceptedVolume(new Measure(UnitConstants.VOLUME,doubleFormatter(bagVol, 2))); //Added by A-7550
						//dsnVO.setStatedVolume(0); // Since mailbag has only
													// one vol and that is
													// kept inside accepted
													// vol
						dsnVO.setStatedVolume(new Measure(UnitConstants.VOLUME,0));
						dsnMap.put(outerpk, dsnVO);
						numBags = 0;
						bagWgt = 0;
						bagVol = 0;
					}
				
				}
			}

			Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
			for (String key : dsnMap.keySet()) {
				DSNVO dsnVO = dsnMap.get(key);
				newDSNVOs.add(dsnVO);
			}
			Collection<DSNVO> oldDSNVOs = popupVO.getDsnVOs();
			int numBags = 0;
			double wgtBags = 0;
			if (newDSNVOs != null && newDSNVOs.size() > 0) {
				for (DSNVO dsnVO : newDSNVOs) {
					String outerpk = dsnVO.getOriginExchangeOffice()
							+ dsnVO.getDestinationExchangeOffice()
							// +dsnVO1.getMailClass()
							// added by anitha for change in pk
							+ dsnVO.getMailCategoryCode()
							+ dsnVO.getMailSubclass() + dsnVO.getDsn()
							+ dsnVO.getYear();
					int flag = 0;
					if (oldDSNVOs != null && oldDSNVOs.size() > 0) {
						for (DSNVO oldDsnVO : oldDSNVOs) {
							String innerpk = oldDsnVO
									.getOriginExchangeOffice()
									+ oldDsnVO.getDestinationExchangeOffice()
									// +oldDsnVO.getMailClass()
									// added by anitha for change in pk
									+ oldDsnVO.getMailCategoryCode()
									+ oldDsnVO.getMailSubclass()
									+ oldDsnVO.getDsn() + oldDsnVO.getYear();
							if (outerpk.equals(innerpk)) {
								if (!"I".equals(oldDsnVO.getOperationFlag())) {
									if( !"D".equals(dsnVO.getOperationFlag())){
									dsnVO.setOperationFlag("U");
									} 
									
									dsnVO.setPrevBagCount(oldDsnVO
											.getPrevBagCount());
									dsnVO.setPrevBagWeight(oldDsnVO
											.getPrevBagWeight());
									dsnVO.setPrevStatedBags(oldDsnVO
											.getPrevStatedBags());
									dsnVO.setPrevStatedWeight(oldDsnVO
											.getPrevStatedWeight());
									 
									/*
									 * Added For AirNZ CR : Mail Allocation
									 */
									dsnVO.setUbrNumber(oldDsnVO
											.getUbrNumber());
									dsnVO.setCurrencyCode(oldDsnVO
											.getCurrencyCode());
									dsnVO
											.setMailrate(oldDsnVO
													.getMailrate());
									dsnVO.setBookingLastUpdateTime(oldDsnVO
											.getBookingLastUpdateTime());
									dsnVO
											.setBookingFlightDetailLastUpdTime(oldDsnVO
													.getBookingFlightDetailLastUpdTime());
									// END CR : Mail Allocation

									// For ANZ BUG 37127
									dsnVO.setRoutingAvl(oldDsnVO
											.getRoutingAvl());
								}
								flag = 1;
							}
						}
					}
					if (flag == 1) {
						if (!"U".equals(dsnVO.getOperationFlag()) && !"D".equals(dsnVO.getOperationFlag())) {  
							dsnVO.setOperationFlag("I");
						}
						flag = 0;
					} else {
						dsnVO.setOperationFlag("I");
					}
					numBags = numBags + dsnVO.getBags();
					//wgtBags = wgtBags + dsnVO.getWeight();
					wgtBags = wgtBags + dsnVO.getWeight().getRoundedSystemValue(); //Added by A-7550
					if("D".equals(dsnVO.getOperationFlag()) &&
							dsnVO.getStatedBags()==1 && numBags>0){            
					numBags = numBags - dsnVO.getBags();                
					//wgtBags = wgtBags - dsnVO.getWeight();  
					wgtBags = wgtBags - dsnVO.getWeight().getRoundedSystemValue();   //Added by A-7550
					}    
					if("D".equals(dsnVO.getOperationFlag()) &&
							dsnVO.getStatedBags()>1&&
							dsnVO.getShipmentCount()>0){
						dsnVO.setOperationFlag("U"); 
						dsnVO.setBags(dsnVO.getStatedBags());                  
						//dsnVO.setWeight(dsnVO.getWeight()+dsnVO.getShipmentWeight());  
						try {
							dsnVO.setWeight(Measure.addMeasureValues(dsnVO.getWeight(), dsnVO.getShipmentWeight()));
						} catch (UnitException e) {
							// TODO Auto-generated catch block
							log.log(Log.SEVERE,"UnitException",e.getMessage());
						}  
					}  
					dsnVO.setStatedBags(0);              
					dsnVO.setShipmentCount(0);
					//dsnVO.setShipmentWeight(0);      
					dsnVO.setShipmentWeight(new Measure(UnitConstants.MAIL_WGT, 0)); //Added by A-7550
				}
			}
			popupVO.setTotalBags(numBags);
		//	popupVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,doubleFormatter(wgtBags, 2)));
			popupVO.setDsnVOs(newDSNVOs);
		}
	}

	return newContainerDetailsVOs;
	}

public String findSystemParameterValue(String syspar) throws BusinessDelegateException
{
String sysparValue = null;
ArrayList<String> systemParameters = new ArrayList<String>();
systemParameters.add(syspar);
Map<String, String> systemParameterMap = new SharedDefaultsDelegate()
	.findSystemParameterByCodes(systemParameters);
log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
if (systemParameterMap != null) {
sysparValue = systemParameterMap.get(syspar);
}
return sysparValue;
}
public void CreateDomesticMailbag(MailbagVO mailbag, LogonAttributes logonAttributes){
	String companycode = logonAttributes.getCompanyCode();
	String routIndex = mailbag.getMailbagId().substring(4, 8);
	String org = null;
	String dest = null;
	Collection<RoutingIndexVO> routingIndexVOs = new ArrayList<RoutingIndexVO>();
	RoutingIndexVO routingIndexFilterVO = new RoutingIndexVO();
	routingIndexFilterVO.setRoutingIndex(routIndex);
	routingIndexFilterVO.setCompanyCode(companycode);
	routingIndexFilterVO.setScannedDate(mailbag.getScannedDate());
	try {
		routingIndexVOs = new MailTrackingDefaultsDelegate().findRoutingIndex(routingIndexFilterVO);
	} catch (BusinessDelegateException businessDelegateException) {
		// TODO Auto-generated catch block
		handleDelegateException(businessDelegateException);
	}
	exchangeOfficeMap = new HashMap<String, String>();
	if (routingIndexVOs.size() > 0) {
		for (RoutingIndexVO routingIndexVO : routingIndexVOs) {
			if (routingIndexVO != null && routingIndexVO.getRoutingIndex() != null) {
				org = routingIndexVO.getOrigin();
				dest = routingIndexVO.getDestination();
				try {
					exchangeOfficeMap = new MailTrackingDefaultsDelegate().findOfficeOfExchangeForPA(companycode,
							findSystemParameterValue(USPS_DOMESTIC_PA));
				} catch (BusinessDelegateException businessDelegateException) {
					// TODO Auto-generated catch block
					handleDelegateException(businessDelegateException);
				}
				if (exchangeOfficeMap != null && !exchangeOfficeMap.isEmpty()) {
					if (exchangeOfficeMap.containsKey(org)) {
						mailbag.setOoe(exchangeOfficeMap.get(org));
					}
					if (exchangeOfficeMap.containsKey(dest)) {
						mailbag.setDoe(exchangeOfficeMap.get(dest));
					}
				}
				mailbag.setMailCategoryCode("B");
				String mailClass = mailbag.getMailbagId().substring(3, 4);
				mailbag.setMailSubclass(mailClass + "X");
				int lastTwoDigits = Calendar.getInstance().get(Calendar.YEAR) % 100;
				// String lastDigitOfYear =
				// String.valueOf(lastTwoDigits).substring(1,2);
				mailbag.setYear(lastTwoDigits % 10);
				// String despacthNumber=null;
				MailbagVO newMailbagVO = new MailbagVO();
				newMailbagVO.setCompanyCode(companycode);
				newMailbagVO.setYear(lastTwoDigits % 10);
				try {
					newMailbagVO = new MailTrackingDefaultsDelegate().findDsnAndRsnForMailbag(newMailbagVO);
				} catch (BusinessDelegateException businessDelegateException) {
					// TODO Auto-generated catch block
					handleDelegateException(businessDelegateException);
				}
				if (newMailbagVO.getDespatchSerialNumber() != null) {
					mailbag.setDespatchSerialNumber(newMailbagVO.getDespatchSerialNumber());
				}
				if (newMailbagVO.getReceptacleSerialNumber() != null) {
					mailbag.setReceptacleSerialNumber(newMailbagVO.getReceptacleSerialNumber());
				}
				mailbag.setHighestNumberedReceptacle("9");
				mailbag.setRegisteredOrInsuredIndicator("9");
				if (mailbag.getMailbagId().length() == 12) {
					Measure weight = new Measure(UnitConstants.MAIL_WGT,
							Double.parseDouble(mailbag.getMailbagId().substring(11, 12)));
					double convertedWeight = unitConvertion(UnitConstants.MAIL_WGT, "L", "K",
							Double.parseDouble(mailbag.getMailbagId().substring(11, 12)));
					double conDisplayWeight = 0;
					if (UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(mailbag.getDisplayUnit())) {
						conDisplayWeight = round(convertedWeight, 1);
					} else {
						conDisplayWeight = round(convertedWeight, 0);
					}
					mailbag.setStrWeight(new
							Measure(UnitConstants.MAIL_WGT,0.0,conDisplayWeight,"L"));
					mailbag.setWeight(mailbag.getStrWeight());
					// mailWt[index]=new
					// Measure(UnitConstants.MAIL_WGT,0.0,conDisplayWeight,mailbag.getDisplayUnit());
					//mailbag.setMailbagWeight(String.valueOf(conDisplayWeight));
					double weightForVol=unitConvertion(UnitConstants.MAIL_WGT,"L",UnitConstants.WEIGHT_UNIT_KILOGRAM,mailbag.getStrWeight().getDisplayValue());
					double ActVol=(weightForVol/0.5);
					mailbag.setVolume(new Measure(UnitConstants.VOLUME,
							ActVol));
				}
				LocalDate sd = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
				mailbag.setScannedDate(sd);
			} else {
				//actionContext.addError(new ErrorVO("Invalid mail id"));
		  		//return;
			}
		}
	} else {
		//actionContext.addError(new ErrorVO("Invalid mail id"));
  		//return;
	}
}
private double unitConvertion(String unitType, String fromUnit, String toUnit, double fromValue) {
	UnitConversionNewVO unitConversionVO = null;
	try {
		unitConversionVO = UnitFormatter.getUnitConversionForToUnit(unitType, fromUnit, toUnit, fromValue);
	} catch (UnitException e) {
		// TODO Auto-generated catch block
		e.getMessage();
	}
	double convertedValue = unitConversionVO.getToValue();
	return convertedValue;
}
private double round(double val, int places) {
	long factor = (long) Math.pow(10, places);
	val = val * factor;
	long tmp = Math.round(val);
	return (double) tmp / factor;
}
/**
 * @author A-8353
 * @param actionContext
 * @param logonAttributes
 * @param errors
 * @param outboundModel
 * @param mailAcceptanceVO
 * @param mailbagVO1
 * @return
 */
private List<ErrorVO> validateTransferCarrierCode( LogonAttributes logonAttributes,
		List<ErrorVO> errors, OutboundModel outboundModel, MailAcceptanceVO mailAcceptanceVO,
		MailbagVO mailbagVO1) {
	AirlineDelegate airlineDelegate = new AirlineDelegate();
	  AirlineValidationVO airlineValidationVO=null;
	if(mailbagVO1.getTransferFromCarrier() != null
	 &&  mailbagVO1.getTransferFromCarrier().trim().length()>0){
	try {        			
		 airlineValidationVO = airlineDelegate.validateAlphaCode(
	logonAttributes.getCompanyCode(),mailbagVO1.getTransferFromCarrier());
	}catch (BusinessDelegateException businessDelegateException) {
	errors = handleDelegateException(businessDelegateException);
	}         
	if(airlineValidationVO!=null){
	mailbagVO1.setFromCarrierId(airlineValidationVO.getAirlineIdentifier());
	}
    }
	mailAcceptanceVO.setTransferOnModify(true);	   
	if(outboundModel.isDisableForModify()){
	mailAcceptanceVO.setModifyAfterExportOpr(true);
	}
	return errors;
}
/**
 * @author A-8353
 * @param mailbagVO
 * @param mailAcceptanceVO
 * @param flightCarrierFlag
 * @param logonAttributes 
 * @param actionContext 
 * @param errors 
 * @param outboundModel 
 * @param warningMap 
 * @throws BusinessDelegateException 
 */
private void doSecurityScreeningValidations(MailbagVO mailbagVO, MailAcceptanceVO mailAcceptanceVO, String flightCarrierFlag, LogonAttributes logonAttributes, ActionContext actionContext, Map<String, String> warningMap, OutboundModel outboundModel) throws BusinessDelegateException {
	SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO= new SecurityScreeningValidationFilterVO();

	Map<String, String> existigWarningMap = outboundModel.getWarningMessagesStatus();
	String securityWarningStatus = "N";
	if(existigWarningMap != null && existigWarningMap.size()>0 && existigWarningMap.containsKey(SECURITY_SCREENING_WARNING)) {
		securityWarningStatus=existigWarningMap.get(SECURITY_SCREENING_WARNING);
	}
	if("N".equals(securityWarningStatus)) {
		Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs=null;
		securityScreeningValidationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());

		if("F".equals(flightCarrierFlag)){
			securityScreeningValidationFilterVO.setApplicableTransaction("ASG");
			securityScreeningValidationFilterVO.setFlightType(mailAcceptanceVO.getFlightType());
			securityScreeningValidationFilterVO.setTransistAirport(mailbagVO.getPou());
		}
		else{
			securityScreeningValidationFilterVO.setApplicableTransaction("ACP");
		}
		securityScreeningValidationFilterVO.setTransactionAirport(logonAttributes.getAirportCode());
		securityScreeningValidationFilterVO.setOriginAirport(mailbagVO.getOrigin());
		securityScreeningValidationFilterVO.setDestinationAirport(mailbagVO.getDestination());
		securityScreeningValidationFilterVO.setMailbagId(mailbagVO.getMailbagId());
		if(!mailbagVO.isImportMailbag()){
		securityScreeningValidationFilterVO.setAppRegValReq(true);
		}
		securityScreeningValidationFilterVO.setSubClass(mailbagVO.getMailSubclass());
		if(mailbagVO.getTransferFromCarrier()!=null){
		securityScreeningValidationFilterVO.setTransferInTxn(true);	
		}
		securityScreeningValidationVOs= new MailTrackingDefaultsDelegate().findSecurityScreeningValidations(securityScreeningValidationFilterVO);
		if (securityScreeningValidationVOs!=null &&! securityScreeningValidationVOs.isEmpty()){
			for( SecurityScreeningValidationVO securityScreeningValidationVO:securityScreeningValidationVOs){
				if ("W".equals(securityScreeningValidationVO
						.getErrorType())) {
					List<ErrorVO> errors = new ArrayList<>();
					warningMap.put(SECURITY_SCREENING_WARNING, "N");
					outboundModel.setWarningMessagesStatus(warningMap);
					ErrorVO warningError = new ErrorVO(SECURITY_SCREENING_WARNING,
							new Object[]{mailbagVO.getMailbagId()});
					warningError.setErrorDisplayType(ErrorDisplayType.WARNING);
					errors.add(warningError);
					actionContext.addAllError(errors);
					return;
				}
				if ("E".equals(securityScreeningValidationVO
						.getErrorType())) {
					if ("AR".equals(securityScreeningValidationVO
							.getValidationType())){
						actionContext.addError(new ErrorVO(APPLICABLE_REGULATION_ERROR));
					}
					else{
					actionContext.addError(new ErrorVO(SECURITY_SCREENING_ERROR,
							new Object[]{mailbagVO.getMailbagId()}));
					}
					return;
				}
			}
		}
	}
}

}
