package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;

import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitConversionNewVO;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailbagFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.DSNDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailBagDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.ListMailbagDetailsCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	28-Dec-2018		:	Draft
 */
public class ListMailbagDetailsCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private static final String STNPAR_DEFUNIT_VOL = "station.defaults.unit.volume";//added by A-8353 for ICRD-274933
	private static final String EMBARGO_EXISTS = "Embargo Exists";
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS ListMailbagDetailsCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		this.log.entering("ListMailbagDetailsCommand", "execute");
		LogonAttributes logonAttributes =   
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		ResponseVO responseVO = new ResponseVO();
		ContainerDetails containerDetails = 
				(ContainerDetails)mailinboundModel.getContainerDetail();
		MailArrivalFilterVO mailArrivalFilterVO=
				new MailArrivalFilterVO();
		Page<MailbagVO> mailbagVOs=
				new Page<MailbagVO>();
		Page<DSNVO> dsnvos=
				new Page<DSNVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		if(containerDetails.getCarrierId()==null){ 
			ArrayList<MailinboundModel> result=new ArrayList<MailinboundModel>();
			result.add(mailinboundModel);
			responseVO.setResults(result);
			actionContext.setResponseVO(responseVO);
			return;
		}   
		mailArrivalFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailArrivalFilterVO.setCarrierId(Integer.parseInt(containerDetails.getCarrierId()));
		mailArrivalFilterVO.setFlightNumber(containerDetails.getFlightNumber());
		mailArrivalFilterVO.setFlightSequenceNumber(Integer.parseInt(containerDetails.getFlightSeqNumber()));
		mailArrivalFilterVO.setPou(containerDetails.getPou());
		mailArrivalFilterVO.setContainerNumber(containerDetails.getContainerNumberWithBulk());
		mailArrivalFilterVO.setContainerType(containerDetails.getContainerType());
		
		if(containerDetails.getPageNumber()==null|| 
				("".equals(containerDetails.getPageNumber().trim()))){
			mailArrivalFilterVO.setPageNumber(1);
		}
		else{
			mailArrivalFilterVO.setPageNumber(Integer.parseInt(containerDetails.getPageNumber()));
		}
		if(containerDetails.getPageSize()==null|| 
				("".equals(containerDetails.getPageSize().trim()))){
			mailArrivalFilterVO.setDefaultPageSize(10);    
		}
		else{
			mailArrivalFilterVO.setDefaultPageSize(Integer.parseInt(containerDetails.getPageSize()));
		}
		
		if(mailinboundModel.getMailbagFilter()!=null) {
			populateAdditionalFilter(mailArrivalFilterVO,mailinboundModel.getMailbagFilter());
		}
		if(containerDetails.getPol()!=null && containerDetails.getPol()!=null) {
			mailArrivalFilterVO.setPol(containerDetails.getPol());
		}
		try {
			mailbagVOs = 
					new MailTrackingDefaultsDelegate().findArrivedMailbagsForInbound(mailArrivalFilterVO);  
		}catch (BusinessDelegateException businessDelegateException) { 
			errors=handleDelegateException(businessDelegateException); 
		}
		if(mailinboundModel.getDsnFilter()!=null) {
			populateAdditionalFilter(mailArrivalFilterVO,mailinboundModel.getDsnFilter());
		}
		try {
			dsnvos = 
					new MailTrackingDefaultsDelegate().findArrivedDsnsForInbound(mailArrivalFilterVO);
		}catch (BusinessDelegateException businessDelegateException) {
			errors=handleDelegateException(businessDelegateException);
		}
		
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError((List<ErrorVO>) errors); 
    	    return;
    	}
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
			if (mailbagVOs!=null){
			for(MailbagVO mailbagVO: mailbagVOs){
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
		if(null!=mailbagVOs){
			mailbagVOs=changeFlightOperationalVos(mailbagVOs,logonAttributes);
			PageResult<MailBagDetails> mailbagDetailsPageList = 
					MailInboundModelConverter.convertMailBagDetailsToModel(mailbagVOs);
			mailinboundModel.setMailBagDetailsCollectionPage(mailbagDetailsPageList);
		}
		if(null!=dsnvos){
			PageResult<DSNDetails> dsnDetailsPageList = 
					MailInboundModelConverter.convertDsnDetailsToModel(dsnvos);
			mailinboundModel.setDsnDetailsCollectionPage(dsnDetailsPageList);
		}
		
		 Collection<ErrorVO> embargoErrors = new ArrayList<>();
		 if(mailinboundModel.isEmbargoInfo()){
			 ErrorVO displayErrorVO = new ErrorVO(EMBARGO_EXISTS);
	  		 displayErrorVO.setErrorDisplayType(ErrorDisplayType.INFO);
	  		 embargoErrors.add(displayErrorVO);
		 }
		
		ArrayList<MailinboundModel> result=new ArrayList<MailinboundModel>();
		result.add(mailinboundModel);
		responseVO.setResults(result);
		responseVO.setStatus("success");
		 if (CollectionUtils.isNotEmpty(embargoErrors)) {
		    	for(ErrorVO error: embargoErrors){
	    		actionContext.addError(error);
	    	}
		  }
		actionContext.setResponseVO(responseVO);
		log.exiting("ListMailbagDetailsCommand", "execute");
	}

	private void populateAdditionalFilter(MailArrivalFilterVO mailArrivalFilterVO, MailbagFilter mailbagFilter) {
		MailbagEnquiryFilterVO additionalFilter = new MailbagEnquiryFilterVO();
		if(mailbagFilter.getMailbagId()!=null && mailbagFilter.getMailbagId().trim().length()>0) {
			additionalFilter.setMailbagId(mailbagFilter.getMailbagId());
		}
		if(mailbagFilter.getMailOrigin()!=null && mailbagFilter.getMailOrigin().trim().length()>0) {
			additionalFilter.setOriginAirportCode(mailbagFilter.getMailOrigin());
		}
		if(mailbagFilter.getMailDestination()!=null && mailbagFilter.getMailDestination().trim().length()>0) {
			additionalFilter.setDestinationAirportCode(mailbagFilter.getMailDestination());
		}
		if(mailbagFilter.getOoe()!=null && mailbagFilter.getOoe().trim().length()>0) {
			additionalFilter.setOoe(mailbagFilter.getOoe());
		}
		if(mailbagFilter.getDoe()!=null && mailbagFilter.getDoe().trim().length()>0) {
			additionalFilter.setDoe(mailbagFilter.getDoe());
		}
		if(mailbagFilter.getMailCategoryCode()!=null && mailbagFilter.getMailCategoryCode().trim().length()>0) {
			additionalFilter.setMailCategoryCode(mailbagFilter.getMailCategoryCode());
		}
		if(mailbagFilter.getMailSubclass()!=null && mailbagFilter.getMailSubclass().trim().length()>0) {
			additionalFilter.setMailSubclass(mailbagFilter.getMailSubclass());
		}
		if(mailbagFilter.getDespatchSerialNumber()!=null && mailbagFilter.getDespatchSerialNumber().trim().length()>0) {
			additionalFilter.setDespatchSerialNumber(mailbagFilter.getDespatchSerialNumber());
		}
		if(mailbagFilter.getReceptacleSerialNumber()!=null && mailbagFilter.getReceptacleSerialNumber().trim().length()>0) {
			additionalFilter.setReceptacleSerialNumber(mailbagFilter.getReceptacleSerialNumber());
		}
		if(mailbagFilter.getConsigmentNumber()!=null && mailbagFilter.getConsigmentNumber().trim().length()>0) {
			additionalFilter.setConsigmentNumber(mailbagFilter.getConsigmentNumber());
		}
		if(mailbagFilter.getMailStatus()!=null && mailbagFilter.getMailStatus().trim().length()>0) {
			additionalFilter.setCurrentStatus(mailbagFilter.getMailStatus());
		}
		if(mailbagFilter.getPaCode()!=null && mailbagFilter.getPaCode().trim().length()>0) {
			additionalFilter.setPacode(mailbagFilter.getPaCode());
		}
		if(mailbagFilter.getShipmentPrefix()!=null && mailbagFilter.getShipmentPrefix().trim().length()>0) {
			additionalFilter.setShipmentPrefix(mailbagFilter.getShipmentPrefix());
		}
		if(mailbagFilter.getMasterDocumentNumber()!=null && mailbagFilter.getMasterDocumentNumber().trim().length()>0) {
			additionalFilter.setMasterDocumentNumber(mailbagFilter.getMasterDocumentNumber());
		}
		if(mailbagFilter.isCarditAvailable()) {
			additionalFilter.setCarditPresent("Y");
		}
		if(mailbagFilter.getRdtDate()!=null && mailbagFilter.getRdtDate().trim().length()>0) {
			LocalDate rdtDate = new LocalDate("***", Location.NONE, false);
			rdtDate.setDate(mailbagFilter.getRdtDate());
			additionalFilter.setReqDeliveryTime(rdtDate);
		}
		mailArrivalFilterVO.setAdditionalFilter(additionalFilter);
		
	}

	private Page<MailbagVO> changeFlightOperationalVos(Page<MailbagVO> mailbagVOs, LogonAttributes logonAttributes) 
			throws BusinessDelegateException {
		Map<String, Collection<OneTimeVO>> oneTimes = 
				 findOneTimeDescription(logonAttributes.getCompanyCode());
		 
		 if(oneTimes!=null){
			   Collection<OneTimeVO> typeVOs = oneTimes.get("mailtracking.defaults.mailstatus");
			   for(MailbagVO mailbagVO:mailbagVOs){
					 for(OneTimeVO oneTimeVO:typeVOs){   
						 if(oneTimeVO.getFieldValue().equals(mailbagVO.getMailStatus())){
							 if(mailbagVO.getMailStatus().equals("DMG")){
								 Collection<DamagedMailbagVO> damagedMailbagVOs=null;
								 damagedMailbagVOs=new MailTrackingDefaultsDelegate()
								 		.findMailbagDamages(logonAttributes.getCompanyCode(),mailbagVO.getMailbagId());
								 mailbagVO.setDamagedMailbags(damagedMailbagVOs);   
							 }
							 mailbagVO.setMailStatus(oneTimeVO.getFieldDescription());
						 } 
					 } 
				 }
		 }
		 
		return mailbagVOs;         
	}
	
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			
			fieldValues.add("mailtracking.defaults.mailstatus");
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}

}
