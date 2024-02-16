/*
 * ListMailbagsCommand.java Created on Jun 08, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagenquiry;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

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
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailbagFilter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * Revision History Revision Date Author Description 0.1 Jun 07, 2018 A-2257
 * First draft
 */

public class ListMailbagsCommand extends AbstractCommand {
	

	private static final String CONST_MAILBAGENQUIRY = "MAILBAGENQUIRY";
	private Log log = LogFactory.getLogger("ListMailbagsCommand");
	private static final String STNPAR_DEFUNIT_VOL = "station.defaults.unit.volume";

	/**
	 * 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException {
		log.entering("ListMailbagsCommand", "execute");	
		
		 
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		
		MailbagEnquiryModel mailbagEnquiryModel = (MailbagEnquiryModel) actionContext.getScreenModel();
		
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();

		ResponseVO responseVO = new ResponseVO();
		
		ArrayList results = new ArrayList();	
		Page<MailbagVO> mailbagVOPage = null;		

		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = null;
		MailbagFilter mailbagFilter = null;
		String actualWeightUnit=null;
		

		// CREATING FILTER
		log.log(Log.FINE,"mailbagEnquiryModel.getFlightCarrierCode()"+mailbagEnquiryModel.getFlightCarrierCode());
		
		log.log(Log.FINE,"mailbagEnquiryModel.getFlightCarrierCode()"+mailbagEnquiryModel);	
		
		
		if( mailbagEnquiryModel !=null && mailbagEnquiryModel.getMailbagFilter()!=null){
			
			log.log(Log.FINE,"mailbagFilter not null");
			
			mailbagFilter = mailbagEnquiryModel.getMailbagFilter();		
			String fromSreen = mailbagEnquiryModel.getFromScreen();
			Collection<ErrorVO> errors = validateForm(mailbagFilter,fromSreen);
			if (errors != null && errors.size() > 0) {
				actionContext.addAllError((ArrayList)errors);
				return;
			}

			mailbagEnquiryFilterVO = constructMailBagFilterVO(mailbagFilter,logonAttributes);		
			
			log.log(Log.FINE, "MailbagEnquiryFilterVO --------->>", mailbagEnquiryFilterVO);
			log.log(Log.FINE, "PageNumber --------->>", mailbagEnquiryModel.getDisplayPage());
			
			mailbagVOPage = mailTrackingDefaultsDelegate.findMailbags(mailbagEnquiryFilterVO, mailbagEnquiryFilterVO.getPageNumber());
			
			
		}
	

//		if (MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED.equals(mailbagEnquiryFilterVO.getCurrentStatus())
//				&& (carrier == null || (carrier != null && carrier.trim().length() == 0))) {
//			mailbagEnquiryFilterVO.setCarrierId(logonAttributes.getOwnAirlineIdentifier());
//			mailbagEnquiryFilterVO.setCarrierCode(upper(logonAttributes.getOwnAirlineCode()));
//		}
		

		try {
			actualWeightUnit=findSystemParameterValue("mail.operations.defaultcaptureunit");
		} catch (SystemException e2) {
			e2.printStackTrace();
		}
			
			if ((mailbagVOPage == null || mailbagVOPage.getActualPageSize() == 0)) {
				 actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.nomailbags"));
				 return	;
			} else {
				for (MailbagVO mailbagVO : mailbagVOPage) {
					String maibagid = mailbagVO.getMailbagId();
					mailbagVO.setActualWeightUnit(actualWeightUnit);
					if (mailbagVO.getWeight() != null) {
						if (maibagid.length() == 29 && mailbagVO.getWeight().getSystemValue() == 0.0) {
							double weight = Double.parseDouble(maibagid.substring(25, 29)) / 10;
							Measure wgt = new Measure(UnitConstants.WEIGHT, weight);
							mailbagVO.setWeight(wgt);// added by A-7371
						}
					}
				}
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
		for(MailbagVO mailbagVO: mailbagVOPage){
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
	        if(unitConversionVO != null)
	        	convertedValue = Math.round(unitConversionVO.getToValue() * 100.0) / 100.0;
	        		
	        mailbagVO.setVol(convertedValue);
		}
		ArrayList mailbagList = MailOperationsModelConverter.constructMailbags(mailbagVOPage);

		PageResult pageList = new PageResult(mailbagVOPage, mailbagList);
		mailbagEnquiryModel.setMailbags(pageList);

		results.add(mailbagEnquiryModel);
		responseVO.setResults(results);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		log.exiting("ListMailbagsCommand","execute");

	}

	private MailbagEnquiryFilterVO constructMailBagFilterVO(MailbagFilter mailbagFilter,LogonAttributes logonAttributes){
		
		log.entering("ListMailbagsCommand","exconstructMailBagFilterVOecute");
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = new MailbagEnquiryFilterVO();	
		
		
		log.log(Log.FINE,"mailbagEnquiryModel.getfilter"+ mailbagFilter.getMailbagId());
		
		mailbagEnquiryFilterVO.setFromScreen(CONST_MAILBAGENQUIRY);
		mailbagEnquiryFilterVO.setCompanyCode(logonAttributes.getCompanyCode());    		
		
		if(mailbagFilter.getDisplayPage() !=null &&  mailbagFilter.getDisplayPage().trim().length()>0){
			
			mailbagEnquiryFilterVO.setPageNumber(Integer.valueOf(mailbagFilter.getDisplayPage()));
		}else{
			mailbagEnquiryFilterVO.setPageNumber(1);
		}
		
		mailbagEnquiryFilterVO.setMailbagId(upper(mailbagFilter.getMailbagId()));
		mailbagEnquiryFilterVO.setDoe(upper(mailbagFilter.getDoe()));
		mailbagEnquiryFilterVO.setOoe(upper(mailbagFilter.getOoe()));
		
		mailbagEnquiryFilterVO.setOriginAirportCode(upper(mailbagFilter.getMailOrigin()));
		mailbagEnquiryFilterVO.setDestinationAirportCode(upper(mailbagFilter.getMailDestination()));
		
		mailbagEnquiryFilterVO.setMailCategoryCode(mailbagFilter.getMailCategoryCode());
		mailbagEnquiryFilterVO.setMailSubclass(upper(mailbagFilter.getMailSubclass()));
		if (!"".equals(mailbagFilter.getYear())) {
			mailbagEnquiryFilterVO.setYear(mailbagFilter.getYear());
		}
		mailbagEnquiryFilterVO.setDespatchSerialNumber(upper(mailbagFilter.getDespatchSerialNumber()));
		mailbagEnquiryFilterVO.setReceptacleSerialNumber(upper(mailbagFilter.getReceptacleSerialNumber()));
		mailbagEnquiryFilterVO.setCurrentStatus(mailbagFilter.getOperationalStatus());
		mailbagEnquiryFilterVO.setScanPort(upper(mailbagFilter.getScanPort()));
		
		if ((!("").equals(mailbagFilter.getFromDate())) && mailbagFilter.getFromDate() != null) {
			LocalDate fromdate = new LocalDate(logonAttributes.getAirportCode(), ARP, true);
			String scanFromDT = new StringBuilder(mailbagFilter.getFromDate()).append(" ").append("00:00:00")
					.toString();
			mailbagEnquiryFilterVO.setScanFromDate(fromdate.setDateAndTime(scanFromDT, false));
		}
		if ((!("").equals(mailbagFilter.getToDate())) && mailbagFilter.getToDate() != null) {
			LocalDate todate = new LocalDate(logonAttributes.getAirportCode(), ARP, true);
			String scanToDT = new StringBuilder(mailbagFilter.getToDate()).append(" ").append("23:59:59")
					.toString();
			mailbagEnquiryFilterVO.setScanToDate(todate.setDateAndTime(scanToDT, false));
		}
		mailbagEnquiryFilterVO.setScanUser(upper(mailbagFilter.getUserID()));
		
	  if (mailbagFilter.getDamageFlag()!=null && mailbagFilter.getDamageFlag().equalsIgnoreCase("true")) {
			mailbagEnquiryFilterVO.setDamageFlag(MailbagEnquiryFilterVO.FLAG_YES);
		} else {
			mailbagEnquiryFilterVO.setDamageFlag(MailbagEnquiryFilterVO.FLAG_NO);
		}
	  if (mailbagFilter.getTransitFlag()!=null && mailbagFilter.getTransitFlag().equalsIgnoreCase("true")) {
		  	mailbagEnquiryFilterVO.setTransitFlag(MailbagEnquiryFilterVO.FLAG_YES);
		} else {
			mailbagEnquiryFilterVO.setTransitFlag(MailbagEnquiryFilterVO.FLAG_NO);
		}
			
		if (mailbagFilter.getRdtDate() != null
				&& mailbagFilter.getRdtDate().trim().length() > 0) {
			LocalDate rqdDlvTim = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			StringBuilder reqDeliveryTime = new StringBuilder(mailbagFilter.getRdtDate());
			if (mailbagFilter.getReqDeliveryTime() != null
					&& mailbagFilter.getReqDeliveryTime().trim().length() > 0) {
				reqDeliveryTime.append(" ").append(mailbagFilter.getReqDeliveryTime()).append(":00");
				rqdDlvTim.setDateAndTime(reqDeliveryTime.toString());
			} else {
				rqdDlvTim.setDate(reqDeliveryTime.toString());
			}
			mailbagEnquiryFilterVO.setReqDeliveryTime(rqdDlvTim);

		}
		if (mailbagFilter.getTransportServWindowDate() != null
				&& mailbagFilter.getTransportServWindowDate().trim().length() > 0) {
			LocalDate transportServWindow = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			StringBuilder transportServWindowTime = new StringBuilder(mailbagFilter.getTransportServWindowDate());
			if (mailbagFilter.getTransportServWindowTime() != null
					&& mailbagFilter.getTransportServWindowTime().trim().length() > 0) {
				transportServWindowTime.append(" ").append(mailbagFilter.getTransportServWindowTime()).append(":00");
				transportServWindow.setDateAndTime(transportServWindowTime.toString());
			} else {
				transportServWindow.setDate(transportServWindowTime.toString());
			}
			mailbagEnquiryFilterVO.setTransportServWindow(transportServWindow);
		}
		//mailbagEnquiryFilterVO.setContainerNumber(upper(mailbagFilter.getUldNumber()));
		if (mailbagFilter.getFlightDate() != null && mailbagFilter.getFlightDate().length() > 0 && !"null".equals(mailbagFilter.getFlightDate())) {
			LocalDate fltdate = new LocalDate(logonAttributes.getAirportCode(), ARP, false);
			mailbagEnquiryFilterVO.setFlightDate(fltdate.setDate(mailbagFilter.getFlightDate()));
		}
		if (mailbagFilter.getFlightNumber() != null && !"".equals(mailbagFilter.getFlightNumber())) {
			String fltnum = (mailbagFilter.getFlightNumber());
			mailbagEnquiryFilterVO.setFlightNumber(upper(fltnum));
		}
		
		mailbagEnquiryFilterVO.setCarrierCode(upper(mailbagFilter.getCarrierCode()));
		
		//mailbagEnquiryFilterVO.setConsigmentNumber(upper(mailbagFilter.getConsigmentNumber()));
		mailbagEnquiryFilterVO.setUpuCode(upper(mailbagFilter.getUpuCode()));
		
		if (MailbagEnquiryFilterVO.FLAG_YES.equals(mailbagFilter.getCarditStatus())) {
			mailbagEnquiryFilterVO.setCarditPresent(MailConstantsVO.FLAG_YES);
		}
		if (MailbagEnquiryFilterVO.FLAG_NO.equals(mailbagFilter.getCarditStatus())) {
			mailbagEnquiryFilterVO.setCarditPresent(MailConstantsVO.FLAG_NO);
		}
		if (("").equals(mailbagFilter.getCarditStatus())) {
			mailbagEnquiryFilterVO.setCarditPresent("ALL");
		}
		if((mailbagFilter.getPageSize()!=null) && ((mailbagFilter.getPageSize().trim().length()) > 0)){
			mailbagEnquiryFilterVO.setPageSize(Integer.parseInt(mailbagFilter.getPageSize()));//Added by A-8353 for ICRD-324698
		}
		mailbagEnquiryFilterVO.setConsigmentNumber(mailbagFilter.getConsignmentNo());
		mailbagEnquiryFilterVO.setContainerNumber(mailbagFilter.getContainerNo());
		mailbagEnquiryFilterVO.setServiceLevel(mailbagFilter.getServiceLevel());
		mailbagEnquiryFilterVO.setOnTimeDelivery(mailbagFilter.getOnTimeDelivery());
		if(mailbagFilter.getMailBagsToList()!=null)//Added by A-8164 for mailinbound
			if(mailbagFilter.getMailBagsToList().size()>0){
				mailbagEnquiryFilterVO.setMailBagsToList(mailbagFilter.getMailBagsToList());
			}
			return mailbagEnquiryFilterVO;
	}
	
	private Collection<ErrorVO> validateForm(MailbagFilter mailbagFilter, String fromSreen) {
		boolean isDateMandatory = true;
		List<ErrorVO> formErrors = new ArrayList<ErrorVO>();
		boolean errorValidations=true;	
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();

		//added by A-7815 as part of IASCB-30811
		if(mailbagFilter.getRdtDate()!=null  && mailbagFilter.getRdtDate().trim().length()> 0)	{
			isDateMandatory = false;
		}
		if(mailbagFilter.getFlightNumber()!=null  && mailbagFilter.getFlightNumber().trim().length()> 0
				&& mailbagFilter.getFlightDate()!=null  && mailbagFilter.getFlightDate().trim().length()> 0)	{
			isDateMandatory = false;
		}
		if(MailConstantsVO.MAILOUTBOUND.equals(fromSreen) && (!StringUtils.isBlank(mailbagFilter.getFlightNumber())
				&& !StringUtils.isBlank(mailbagFilter.getContainerNo()))){
			errorValidations=false;
		}
		if (StringUtils.isBlank(mailbagFilter.getFromDate()) && "mail.operations.ux.containerenquiry".equals(fromSreen)
				&& (StringUtils.isBlank(mailbagFilter.getFlightNumber()))
				&& !StringUtils.isBlank(mailbagFilter.getContainerNo())) {
			mailbagFilter.setFromDate(mailbagFilter.getContainerAssignedOn().substring(0, 11));
			LocalDate todate = new LocalDate(logonAttributes.getAirportCode(), ARP, true);
			mailbagFilter.setToDate(todate.toDisplayFormat().substring(0, 11));
			//container current port using as scan port
			mailbagFilter.setScanPort(mailbagFilter.getAirportCode());
		}

		
		if (errorValidations) {  
			if ((mailbagFilter.getScanPort() == null || mailbagFilter.getScanPort().trim().length() == 0)
					&& (mailbagFilter.getDespatchSerialNumber() == null
							|| mailbagFilter.getDespatchSerialNumber().trim().length() == 0)
					&& (mailbagFilter.getCarrierCode() == null || mailbagFilter.getCarrierCode().trim().length() == 0
							|| mailbagFilter.getFlightNumber() == null
							|| mailbagFilter.getFlightNumber().trim().length() == 0
							|| mailbagFilter.getFlightDate() == null
							|| mailbagFilter.getFlightDate().trim().length() == 0) &&
					(mailbagFilter.getMailbagId() == null || mailbagFilter.getMailbagId().trim().length() == 0)
					&& (mailbagFilter.getConsignmentNo() == null || mailbagFilter.getConsignmentNo().trim().length() == 0) && (mailbagFilter.getOoe() == null || mailbagFilter.getOoe().trim().length() == 0) && (mailbagFilter.getDoe() == null || mailbagFilter.getDoe().trim().length() == 0)) {//Modified by A-8527 for IASCB-52766
				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailbagenquiry.portordsnorflightdetailsormailidorconsnumbermandatory");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}
			
			if (isDateMandatory && (mailbagFilter.getFromDate() == null || (mailbagFilter.getFromDate().trim().length()==0))) {
				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.noFromDate");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}

			if (isDateMandatory && (mailbagFilter.getToDate() == null || (mailbagFilter.getToDate().trim().length()==0))) {
				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.noToDate");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}
			}
			if(mailbagFilter.getFromDate() != null && (mailbagFilter.getFromDate().trim().length()>0)
					&& mailbagFilter.getToDate() != null && (mailbagFilter.getToDate().trim().length()>0)){
				
			if (!(mailbagFilter.getFromDate().equalsIgnoreCase(mailbagFilter.getToDate())) && (!DateUtilities
					.isLessThan(mailbagFilter.getFromDate(), mailbagFilter.getToDate(), "dd-MMM-yyyy"))) {
				    	
				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.fromgrtrtodat");
				    	formErrors.add(errorVO);
				    }
				  
			}

		String status = mailbagFilter.getMailStatus();
		if ("ACP".equals(status)) {
			if (mailbagFilter.getFlightNumber()==null || mailbagFilter.getFlightNumber().trim().length()>0
					|| mailbagFilter.getFlightDate()==null || mailbagFilter.getFlightDate().trim().length()>0){				

					ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.noFlightDetails");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					formErrors.add(errorVO);

				
			}
		}

		return formErrors;
	}
/**
 * This method is used to convert a string to upper case if
 * it is not null
 * @param input
 * @return String
 */
private String upper(String input){//to convert sting to uppercase

	if(input!=null){
		return input.trim().toUpperCase();
	}else{
		return "";
	}
}

private String findSystemParameterValue(String syspar) throws SystemException, BusinessDelegateException {
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
	
}
