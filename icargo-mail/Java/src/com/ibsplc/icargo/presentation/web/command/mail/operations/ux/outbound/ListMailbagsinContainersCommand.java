package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitConversionNewVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.DespatchDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailbagFilter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
public class ListMailbagsinContainersCommand extends AbstractCommand {
	   private static final String OUTBOUND = "O";
		private static final String STNPAR_DEFUNIT_VOL = "station.defaults.unit.volume";//added by A-8353 for ICRD-274933
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
	CommandInvocationException {
OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
FlightFilterVO flightFilterVO = null;
flightFilterVO = new FlightFilterVO();
List<ErrorVO> errors = new ArrayList<>();
LogonAttributes logonAttributes = getLogonAttribute();
ResponseVO responseVO = new ResponseVO();
List<OutboundModel> results = new ArrayList();
ContainerDetails containerDetails=null;
if(outboundModel.getContainerInfo()!=null) {
	containerDetails=outboundModel.getContainerInfo();
}
ContainerDetailsVO containervo =MailOutboundModelConverter.constructContainerDetailVO(containerDetails);
if(outboundModel.getMailbagFilter()!=null) {
	containervo.setAdditionalFilters(constructAdditionalMailbagFilter(outboundModel.getMailbagFilter()));
}
	int mailbagDisplayPage=outboundModel.getMailbagsDisplayPage();
	int mailbagDSNDisplayPage = outboundModel.getMailbagsDSNDisplayPage();
	Page<MailbagVO> mailbagPage =null;
	Page<DSNVO> mailbagPagedsnview =null;
	PageResult<Mailbag> mailPageResult =null;
	PageResult<DespatchDetails> mailPageResultdsnview =null;
	if(outboundModel.getFlightCarrierFilter().getAssignTo()!=null || outboundModel.getFlightCarrierFilter().getAssignTo().trim().length()>0) { 
        if(outboundModel.getFlightCarrierFilter().getAssignTo().equals("F")) {
        	if( containervo!=null
        	           &&containervo.getContainerNumber()!=null&&containervo.getContainerNumber().trim().length()>0){
	        try {
		        mailbagPage = new MailTrackingDefaultsDelegate().getMailbagsinContainer(containervo, mailbagDisplayPage);
             }catch (BusinessDelegateException businessDelegateException) {
			    errors = handleDelegateException(businessDelegateException);
	      }
        }   
	
	 }
        
        else {
        	if( containervo!=null
     	           &&containervo.getContainerNumber()!=null&&containervo.getContainerNumber().trim().length()>0){
          	 try {
    		       mailbagPage = new MailTrackingDefaultsDelegate().getMailbagsinCarrierContainer(containervo, mailbagDisplayPage);
                }catch (BusinessDelegateException businessDelegateException) {
    			 errors = handleDelegateException(businessDelegateException);
    	     }
          }
    
        }
      //added by A-8353 for ICRD-274933 starts
        if (mailbagPage!=null){
        AreaDelegate areaDelegate = new AreaDelegate();
		//String stationVolumeUnit = (String)stationParameters.get(STNPAR_DEFUNIT_VOL); 
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
		for(MailbagVO mailbagVO: mailbagPage){
			UnitConversionNewVO unitConversionVO= null;
			String fromUnit = stationVolumeUnit;
			if(mailbagVO.getVolUnit()!=null){
				fromUnit = mailbagVO.getVolUnit();
			}
	        try {
	               unitConversionVO=UnitFormatter.getUnitConversionForToUnit(UnitConstants.VOLUME, fromUnit, stationVolumeUnit, mailbagVO.getVol());
	        } catch (UnitException e) {
	              // TODO Auto-generated catch block
	              e.getMessage();
	        }
			double convertedValue = 0.0;
			if(Objects.nonNull(unitConversionVO)) {
				convertedValue = Math.round(unitConversionVO.getToValue() * 10000.0) / 10000.0;
			}
	        if(mailbagVO.getVolUnit()!=null && mailbagVO.getVol()!=0){
	        	mailbagVO.setVolume(new Measure(UnitConstants.VOLUME,0.0,convertedValue,stationVolumeUnit));
	        }
		} 
	}
		//added by A-8353 for ICRD-274933ends
        if(outboundModel.isEmbargoInfo()){
            ErrorVO displayErrorVO = new ErrorVO("mailtracking.defaults.embargoexists");
			displayErrorVO.setErrorDisplayType(ErrorDisplayType.INFO);
			errors.add(displayErrorVO);
			actionContext.addAllError(errors);
			return;
	 }
     if(outboundModel.isContainerTransferCheck() && mailbagPage!=null) {
    	 for(MailbagVO mailbagVO: mailbagPage) {
    		 if ( !mailbagVO.getScannedPort().equals(logonAttributes.getAirportCode()) ||
    				 MailConstantsVO.MAIL_STATUS_DELIVERED.contentEquals(mailbagVO.getLatestStatus()) || 
    				 MailConstantsVO.MAIL_STATUS_ARRIVED.contentEquals(mailbagVO.getLatestStatus())) {
    			 ErrorVO displayErrorVO = new ErrorVO("mail.operations.mailbagnotavailableinairport");
    				actionContext.addError(displayErrorVO);
    				return;
    		 }
    	 }
	 }
    if(mailbagPage!=null) {
     List<Mailbag> mailbagList = MailOutboundModelConverter.constructMailbagDetails(mailbagPage,containervo);
   	 mailPageResult= new PageResult<Mailbag>(mailbagPage, mailbagList);
   	 outboundModel.getContainerInfo().setMailbagpagelist(mailPageResult);
    }
 
	  
	
			results.add(outboundModel);
	        responseVO.setResults(results);
	        responseVO.setStatus("success");
	        actionContext.setResponseVO(responseVO);
	        
	
	 }
	}
	private MailbagEnquiryFilterVO constructAdditionalMailbagFilter(MailbagFilter mailbagFilter) {
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = new MailbagEnquiryFilterVO();
		mailbagEnquiryFilterVO.setMailbagId(mailbagFilter.getMailbagId());
		mailbagEnquiryFilterVO.setOriginAirportCode(mailbagFilter.getMailOrigin());
		mailbagEnquiryFilterVO.setDestinationAirportCode(mailbagFilter.getMailDestination());
		mailbagEnquiryFilterVO.setMailCategoryCode(mailbagFilter.getMailCategoryCode());
		mailbagEnquiryFilterVO.setMailSubclass(mailbagFilter.getMailSubclass());
		mailbagEnquiryFilterVO.setDespatchSerialNumber(mailbagFilter.getDespatchSerialNumber());
		mailbagEnquiryFilterVO.setReceptacleSerialNumber(mailbagFilter.getReceptacleSerialNumber());
		mailbagEnquiryFilterVO.setPacode(mailbagFilter.getPaCode());
		mailbagEnquiryFilterVO.setConsigmentNumber(mailbagFilter.getConsigmentNumber());
		if(mailbagFilter.getConsignmentDate()!=null && mailbagFilter.getConsignmentDate().trim().length()>0) {
			LocalDate consignmentDate = new LocalDate("***", Location.NONE, false);
			consignmentDate.setDate(mailbagFilter.getConsignmentDate());
		     mailbagEnquiryFilterVO.setConsignmentDate(consignmentDate);
		}
		if(mailbagFilter.getRdtDate()!=null && mailbagFilter.getRdtDate().trim().length()>0) {
			LocalDate rdtDate = new LocalDate("***", Location.NONE, false);
			rdtDate.setDate(mailbagFilter.getRdtDate());
		     mailbagEnquiryFilterVO.setReqDeliveryTime(rdtDate);
		}
		mailbagEnquiryFilterVO.setTransferFromCarrier(mailbagFilter.getTransferFromCarrier());
		mailbagEnquiryFilterVO.setShipmentPrefix(mailbagFilter.getShipmentPrefix());
		mailbagEnquiryFilterVO.setMasterDocumentNumber(mailbagFilter.getMasterDocumentNumber());
		mailbagEnquiryFilterVO.setCarditPresent(mailbagFilter.isCarditAvailable()? "Y":null);
		mailbagEnquiryFilterVO.setDamageFlag(mailbagFilter.isDamaged()? "Y":null);
		mailbagEnquiryFilterVO.setCurrentStatus(mailbagFilter.getMailStatus());
		return mailbagEnquiryFilterVO;
	}
}
