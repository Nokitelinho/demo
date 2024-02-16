/*
 * EmbargoVOConverter.java Created on Sep 04, 2013
 *
 * Copyright 2013 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.reco.defaults.vo.converter;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.msgbroker.message.vo.reco.defaults.EmbargoGeographicLevelMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.reco.defaults.EmbargoMasterMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.reco.defaults.EmbargoMasterPrevDetailsMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.reco.defaults.EmbargoParameterMasterMessageVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGeographicLevelVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoPublishVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.util.time.TimeConvertor;
/**
 * author A-5219
 * The Class EmbargoVOConverter.
 */

public class EmbargoVOConverter {

	/** The Constant VIA. */
	public static final String VIA="VIA";
	/** The Constant AT_ORIGIN. */
	public static final String AT_ORIGIN="O";
	/** The Constant ORIGIN. */
	public static final String ORIGIN="ORG";
	/** The Constant DESTINATION. */
	public static final String DESTINATION="DST";
	/** The Constant AT_DESTINATION. */
	public static final String AT_DESTINATION="D";
	/** The Constant COUNTRY. */
	public static final String COUNTRY="C";
	/** The Constant AT_COUNTRY. */
	public static final String AT_COUNTRY="C";
	/** The Constant STATION. */
	public static final String STATION="S";
	/** The Constant AIRPORT. */
	public static final String AIRPORT="A";
	/** The Constant YES. */
	public static final String YES="Y";
	/** The Constant NO. */
	public static final String NO="N";
	/** The Constant FLTNUM. */
	public static final String FLTNUM="FLTNUM";
	/** The Constant FLT. */
	public static final String FLT="FLT";
	/** The Constant PAYTYP. */
	public static final String PAYTYP="PAYTYP";
	/** The Constant PAY. */
	public static final String PAY="PAY";
	/** The Constant UPDATE. */
	public static final String UPDATE="U";
	/** The Constant DELETE. */
	public static final String DELETE="D";
	/** The Constant AT_VIA. */
	public static final String AT_VIA="V";
	/** The Constant ALL. */
	public static final String ALL = "All";
	/** The Constant ALLDAYS. */
	public static final String ALLDAYS = "1234567";
	/** The Constant MONDAY. */
	public static final String MONDAY = "1";
	/** The Constant MON. */
	public static final String MON = "Mon";
	/** The Constant TUESDAY. */
	public static final String TUESDAY = "2";
	/** The Constant TUE. */
	public static final String TUE = "Tue";
	/** The Constant WEDNESDAY. */
	public static final String WEDNESDAY = "3";
	/** The Constant WED. */
	public static final String WED = "Wed";
	/** The Constant THURSDAY. */
	public static final String THURSDAY = "4";
	/** The Constant THU. */
	public static final String THU = "Thu";
	/** The Constant FRIDAY. */
	public static final String FRIDAY = "5";
	/** The Constant FRI. */
	public static final String FRI = "Fri";
	/** The Constant SATURDAY. */
	public static final String SATURDAY = "6";
	/** The Constant SAT. */
	public static final String SAT = "Sat";
	/** The Constant SUNDAY. */
	public static final String SUNDAY = "7";
	/** The Constant SUN. */
	public static final String SUN = "Sun";
	/** The Constant CANCELLED. */
	public static final String CANCELLED = "CANCELLED";
	/** The Constant CANCEL. */
	public static final String INACTIVE = "I";
	/** The Constant ERROR. */
	public static final String ERROR = "Error";
	/** The Constant WARNING. */
	public static final String WARNING = "Warning";
	/** The Constant INFO. */
	public static final String INFO = "Info";
	/** The Constant TO_INFO. */
	public static final String TO_INFO = "I";
	/** The Constant TO_WARNING. */
	public static final String TO_WARNING = "W";
	/** The Constant TO_ERROR. */
	public static final String TO_ERROR = "E";
	public static final String APPROVED = "A";
	public static final String SUSPENDED = "S";
	public static final String AT_SEG_ORIGIN = "L"; //added by A-7924 as part of CR ICRD-299901
	public static final String AT_SEG_DESTINATION = "U";//added by A-7924 as part of CR ICRD-299901
	/**
	 * 
	 * @param embargoVO
	 * @return
	 */
	public static EmbargoMasterMessageVO convertToEmbargoMasterMessageVO(EmbargoRulesVO embargoVO){
		EmbargoMasterMessageVO embargoMasterMessageVO = new EmbargoMasterMessageVO(); 
		embargoMasterMessageVO.setCompanyCode(embargoVO.getCompanyCode());
		embargoMasterMessageVO.setDaysOfOperation(embargoVO.getDaysOfOperation());
		if(AT_ORIGIN.equalsIgnoreCase(embargoVO.getDaysOfOperationApplicableOn()))
			{
			embargoMasterMessageVO.setDaysOfOperationApplicableOn(AT_ORIGIN);
			}
		else if(AT_DESTINATION.equalsIgnoreCase(embargoVO.getDaysOfOperationApplicableOn()))
			{
			embargoMasterMessageVO.setDaysOfOperationApplicableOn(AT_DESTINATION);
			}
		else if(AT_VIA.equalsIgnoreCase(embargoVO.getDaysOfOperationApplicableOn()))
			{
			embargoMasterMessageVO.setDaysOfOperationApplicableOn(AT_VIA);   
            }   
		//added by A-7924 as part of CR ICRD-299901 starts
		else if(AT_SEG_ORIGIN.equalsIgnoreCase(embargoVO.getDaysOfOperationApplicableOn()))
			embargoMasterMessageVO.setDaysOfOperationApplicableOn(AT_SEG_ORIGIN);
		else if(AT_SEG_DESTINATION.equalsIgnoreCase(embargoVO.getDaysOfOperationApplicableOn()))
			embargoMasterMessageVO.setDaysOfOperationApplicableOn(AT_SEG_DESTINATION);
		//added by A-7924 as part of CR ICRD-299901 ends
		embargoMasterMessageVO.setDestination(embargoVO.getDestination());
		embargoMasterMessageVO.setDestinationType(embargoVO.getDestinationType());
		embargoMasterMessageVO.setEmbargoDescription(embargoVO.getEmbargoDescription());
		embargoMasterMessageVO.setEmbargoLevel(embargoVO.getEmbargoLevel());
		embargoMasterMessageVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
		embargoMasterMessageVO.setEndDate(embargoVO.getEndDate().toDisplayDateOnlyFormat()); 
		embargoMasterMessageVO.setOperationalFlag(embargoVO.getOperationalFlag());
		embargoMasterMessageVO.setOrigin(embargoVO.getOrigin());
		embargoMasterMessageVO.setOriginType(embargoVO.getOriginType());
		embargoMasterMessageVO.setRemarks(embargoVO.getRemarks());
		embargoMasterMessageVO.setStartDate(embargoVO.getStartDate().toDisplayDateOnlyFormat());
		embargoMasterMessageVO.setStatus(embargoVO.getStatus());
		embargoMasterMessageVO.setApplicableTransactions(embargoVO.getApplicableTransactions());
		embargoMasterMessageVO.setCategory(embargoVO.getCategory());
		embargoMasterMessageVO.setComplianceType(embargoVO.getComplianceType());
		if(!embargoVO.getIsSuspended())
			{
			embargoMasterMessageVO.setSuspended(NO);
			}
		else
			{
			embargoMasterMessageVO.setSuspended(YES);
			}
		embargoMasterMessageVO.setViaPoint(embargoVO.getViaPoint());
		embargoMasterMessageVO.setViaPointType(embargoVO.getViaPointType());
		Collection<EmbargoParameterVO> emargoParameterVO = embargoVO.getParameters();
		Collection<EmbargoGeographicLevelVO> geographicLevels = embargoVO.getGeographicLevels();
		//embargoMasterMessageVO.setSernumber(embargoVO.getSernumber());
		//embargoVO.setSernumber(0);
		if(embargoVO.getEmbargoPublishVO()!=null){
			EmbargoMasterPrevDetailsMessageVO embargoMasterPrevDetailsMessageVO=EmbargoVOConverter.convertToEmbargoMasterPrevDetailsMessageVO(embargoVO);
			if(embargoMasterPrevDetailsMessageVO!=null)
				{
				embargoMasterMessageVO.setPrevEmbargoDetailsForPublish(embargoMasterPrevDetailsMessageVO);
		}
		}
		if(emargoParameterVO!=null && emargoParameterVO.size()>0)
			{
			embargoMasterMessageVO.setParameters(EmbargoVOConverter.convertToEmbargoParameterMasterMessageVO(emargoParameterVO));		
			}		
		if(geographicLevels != null && geographicLevels.size()>0)
			{
			embargoMasterMessageVO.setGeographicLevels(EmbargoVOConverter.convertToEmbargoGeographicLevelMessageVO(geographicLevels));
			}
		return embargoMasterMessageVO;
	}
	/**
	 * 
	 * @param geographicLevels
	 * @return
	 */
	private static Collection<EmbargoGeographicLevelMessageVO> convertToEmbargoGeographicLevelMessageVO(
			Collection<EmbargoGeographicLevelVO> geographicLevels) {
		Collection<EmbargoGeographicLevelMessageVO> embargoGeographicLevelMessageVOs = new ArrayList<EmbargoGeographicLevelMessageVO>();
		EmbargoGeographicLevelMessageVO embargoGeographicLevelMessageVO = null;
		for(EmbargoGeographicLevelVO embargoGeographicLevelVO : geographicLevels){
			if(!EmbargoGeographicLevelVO.OPERATION_FLAG_DELETE.equals(embargoGeographicLevelVO.getOperationFlag())){
			embargoGeographicLevelMessageVO = new EmbargoGeographicLevelMessageVO();
			embargoGeographicLevelMessageVO.setCompanyCode(embargoGeographicLevelVO.getCompanyCode());
			embargoGeographicLevelMessageVO
			.setEmbargoReferenceNumber(embargoGeographicLevelVO.getEmbargoReferenceNumber());
			embargoGeographicLevelMessageVO.setEmbargoVersion(embargoGeographicLevelVO.getEmbargoVersion());
			embargoGeographicLevelMessageVO.setGeographicLevel(embargoGeographicLevelVO.getGeographicLevel());
			embargoGeographicLevelMessageVO.setGeographicLevelType(embargoGeographicLevelVO.getGeographicLevelType());
			embargoGeographicLevelMessageVO.setGeographicLevelApplicableOn(embargoGeographicLevelVO.getGeographicLevelApplicableOn());
			embargoGeographicLevelMessageVO.setGeographicLevelValues(embargoGeographicLevelVO.getGeographicLevelValues());
			embargoGeographicLevelMessageVOs.add(embargoGeographicLevelMessageVO);
			}
		}
		return embargoGeographicLevelMessageVOs;
	}
	/**
	 * 
	 * @param embargoVO
	 * @return
	 */
	public static EmbargoMasterPrevDetailsMessageVO convertToEmbargoMasterPrevDetailsMessageVO(EmbargoRulesVO embargoVO){
		EmbargoMasterPrevDetailsMessageVO embargoMasterPrevDetailsMessageVO = new EmbargoMasterPrevDetailsMessageVO();
		EmbargoPublishVO embargoOldVO = embargoVO.getEmbargoPublishVO();
		embargoMasterPrevDetailsMessageVO.setCompanyCode(embargoOldVO.getCompanyCode());
		embargoMasterPrevDetailsMessageVO.setDaysOfOperation(embargoOldVO.getDaysOfOperation());
		if(AT_ORIGIN.equals(embargoOldVO.getDaysOfOperationApplicableOn()))
			{
			embargoMasterPrevDetailsMessageVO.setDaysOfOperationApplicableOn(AT_ORIGIN);
			}
		else if(AT_DESTINATION.equals(embargoOldVO.getDaysOfOperationApplicableOn()))
			{
			embargoMasterPrevDetailsMessageVO.setDaysOfOperationApplicableOn(AT_DESTINATION);
			}
		else if(AT_VIA.equalsIgnoreCase(embargoOldVO.getDaysOfOperationApplicableOn()))
			{
			embargoMasterPrevDetailsMessageVO.setDaysOfOperationApplicableOn(AT_VIA);
			}
		embargoMasterPrevDetailsMessageVO.setDestination(embargoOldVO.getDestination());
		embargoMasterPrevDetailsMessageVO.setDestinationType(embargoOldVO.getDestinationType());
		embargoMasterPrevDetailsMessageVO.setEmbargoDescription(embargoOldVO.getEmbargoDescription());
		embargoMasterPrevDetailsMessageVO.setEmbargoLevel(embargoOldVO.getEmbargoLevel());
		embargoMasterPrevDetailsMessageVO.setEmbargoReferenceNumber(embargoOldVO.getEmbargoReferenceNumber());
		embargoMasterPrevDetailsMessageVO.setEndDate(embargoOldVO.getEndDate().toDisplayDateOnlyFormat());
		embargoMasterPrevDetailsMessageVO.setOperationalFlag(embargoOldVO.getOperationalFlag());
		embargoMasterPrevDetailsMessageVO.setOrigin(embargoOldVO.getOrigin());
		embargoMasterPrevDetailsMessageVO.setOriginType(embargoOldVO.getOriginType());
		embargoMasterPrevDetailsMessageVO.setRemarks(embargoOldVO.getRemarks());
		embargoMasterPrevDetailsMessageVO.setStartDate(embargoOldVO.getStartDate().toDisplayDateOnlyFormat());
		embargoMasterPrevDetailsMessageVO.setStatus(APPROVED);
		embargoMasterPrevDetailsMessageVO.setApplicableTransactions(embargoOldVO.getApplicableTransactions());
		embargoMasterPrevDetailsMessageVO.setCategory(embargoOldVO.getCategory());
		embargoMasterPrevDetailsMessageVO.setComplianceType(embargoOldVO.getComplianceType());
		if(!embargoOldVO.getIsSuspended())
			{
			embargoMasterPrevDetailsMessageVO.setSuspended(NO);
			}
		else{
			embargoMasterPrevDetailsMessageVO.setSuspended(YES);
			embargoMasterPrevDetailsMessageVO.setStatus(SUSPENDED);
		}
		embargoMasterPrevDetailsMessageVO.setViaPoint(embargoOldVO.getViaPoint());
		embargoMasterPrevDetailsMessageVO.setViaPointType(embargoOldVO.getViaPointType());
		Collection<EmbargoParameterVO> emargoParameterVO = embargoOldVO.getParameters();
		Collection<EmbargoGeographicLevelVO> geographicLevels = embargoOldVO.getGeographicLevels();
		if(emargoParameterVO!=null)
			{
			embargoMasterPrevDetailsMessageVO.setParameters(EmbargoVOConverter.convertToEmbargoParameterMasterMessageVO(emargoParameterVO));
			}
		if(geographicLevels != null && geographicLevels.size()>0)
			{
			embargoMasterPrevDetailsMessageVO.setGeographicLevels(EmbargoVOConverter.convertToEmbargoGeographicLevelMessageVO(geographicLevels));
			}
		return embargoMasterPrevDetailsMessageVO;
	}
	/**
	 * 
	 * @param emargoParameterVOs
	 * @return
	 */
	public static Collection<EmbargoParameterMasterMessageVO> convertToEmbargoParameterMasterMessageVO(Collection<EmbargoParameterVO> emargoParameterVOs){
		Collection<EmbargoParameterMasterMessageVO> embargoParameterMasterMessageVOS = new ArrayList<EmbargoParameterMasterMessageVO>();
		for(EmbargoParameterVO emargoParameterVO :emargoParameterVOs){
			if(!EmbargoGeographicLevelVO.OPERATION_FLAG_DELETE.equals(emargoParameterVO.getOperationalFlag())){
			EmbargoParameterMasterMessageVO embargoParameterMasterMessageVO = new EmbargoParameterMasterMessageVO();
			
				embargoParameterMasterMessageVO.setParameterCode(emargoParameterVO.getParameterCode());
			if(FLTNUM.equalsIgnoreCase(emargoParameterVO.getParameterCode())){
				String fltNum=emargoParameterVO.getParameterValues();
				if(fltNum.contains("~"))
					{
					fltNum=fltNum.replace('~', ' ');
					}
				embargoParameterMasterMessageVO.setParameterValue(fltNum);
			}
			else
				{
				embargoParameterMasterMessageVO.setParameterValue(emargoParameterVO.getParameterValues());
				}
			if(NO.equals(emargoParameterVO.getAplFlg()) || "EX".equals(emargoParameterVO.getApplicable()) ||  "EXIF".equals(emargoParameterVO.getApplicable()))
				{
				embargoParameterMasterMessageVO.setIncluded(NO);
				}
			else if(YES.equals(emargoParameterVO.getAplFlg()) || "IN".equals(emargoParameterVO.getApplicable()))
				{
				embargoParameterMasterMessageVO.setIncluded(YES);
				}
			else{
				embargoParameterMasterMessageVO.setParameterLevel(emargoParameterVO.getApplicable());
			}
			embargoParameterMasterMessageVO.setApplicableLevel(emargoParameterVO.getApplicableLevel()); //Added by A-7924 as part of CR ICRD-299901 
			embargoParameterMasterMessageVOS.add(embargoParameterMasterMessageVO);
			}
		}
		return embargoParameterMasterMessageVOS;
	}
	/**
	 * 
	 * @param embargoDetailsVO
	 * @return
	 */
	public static EmbargoRulesVO convertToEmbargoVO(EmbargoDetailsVO embargoDetailsVO){
		EmbargoRulesVO embargoVO=new EmbargoRulesVO();
		embargoVO.setCompanyCode(embargoDetailsVO.getCompanyCode());
		if(embargoDetailsVO.getDaysOfOperation()!=null && embargoDetailsVO.getDaysOfOperation().trim().length()>0){
			String daysOfOperation[]=embargoDetailsVO.getDaysOfOperation().split(",");
			StringBuilder addDays=new StringBuilder();
			for(String days: daysOfOperation){
				if(ALL.equals(days))
					{
					addDays.append(ALLDAYS);
					}
				else if(MON.equals(days))
					{
					addDays.append(MONDAY);
					}
				else if(TUE.equals(days))
					{
					addDays.append(TUESDAY);
					}
				else if(WED.equals(days))
					{
					addDays.append(WEDNESDAY);
					}
				else if(THU.equals(days))
					{
					addDays.append(THURSDAY);
					}
				else if(FRI.equals(days))
					{
					addDays.append(FRIDAY);
					}
				else if(SAT.equals(days))
					{
					addDays.append(SATURDAY);
					}
				else 
					{
					addDays.append(SUNDAY);
			}
			}
			embargoVO.setDaysOfOperation(addDays.toString());
		}
		embargoVO.setDaysOfOperationApplicableOn(embargoDetailsVO.getDaysOfOperationApplicableOn());
		embargoVO.setDestination(embargoDetailsVO.getDestination());
		embargoVO.setDestinationType(embargoDetailsVO.getDestinationType());
		embargoVO.setEmbargoDescription(embargoDetailsVO.getEmbargoDescription());
		embargoVO.setRemarks(embargoDetailsVO.getRemarks());
		embargoVO.setApplicableTransactions(embargoDetailsVO.getApplicableTransactions());
		embargoVO.setCategory(embargoDetailsVO.getCategory());
		embargoVO.setComplianceType(embargoDetailsVO.getComplianceType());
		if(ERROR.equals(embargoDetailsVO.getEmbargoLevel()) ||TO_ERROR.equals(embargoDetailsVO.getEmbargoLevel()) )
			{
			embargoVO.setEmbargoLevel(TO_ERROR);
			}
		else if(WARNING.equals(embargoDetailsVO.getEmbargoLevel()) ||TO_WARNING.equals(embargoDetailsVO.getEmbargoLevel()))
			{
			embargoVO.setEmbargoLevel(TO_WARNING);
			}
		else
			{
			embargoVO.setEmbargoLevel(TO_INFO);
			}
		embargoVO.setEmbargoReferenceNumber(embargoDetailsVO.getEmbargoReferenceNumber());
		LocalDate endDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		embargoVO.setEndDate(endDate.setDate(TimeConvertor.toStringFormat(embargoDetailsVO.getEndDate(),TimeConvertor.CALENDAR_DATE_FORMAT)));
		embargoVO.setIsSuspended(embargoDetailsVO.getIsSuspended());
		embargoVO.setOrigin(embargoDetailsVO.getOrigin());
		embargoVO.setOriginType(embargoDetailsVO.getOriginType());
		Collection<EmbargoParameterVO> parameterVOs =  new ArrayList<EmbargoParameterVO>();
		if(embargoDetailsVO.getParams()!=null && embargoDetailsVO.getParams().size()>0){
			parameterVOs.addAll(embargoDetailsVO.getParams());
			embargoVO.setParameters(parameterVOs);
		}
		embargoVO.setRemarks(embargoDetailsVO.getRemarks());
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		embargoVO.setStartDate(fromDate.setDate(TimeConvertor.toStringFormat(embargoDetailsVO.getStartDate(),TimeConvertor.CALENDAR_DATE_FORMAT)));
		if(CANCELLED.equals(embargoDetailsVO.getStatus()))
			{
			embargoVO.setStatus(INACTIVE);
			}
		else
			{
			embargoVO.setStatus(embargoDetailsVO.getStatus());
			}
		embargoVO.setViaPoint(embargoDetailsVO.getViaPoint());
		embargoVO.setViaPointType(embargoDetailsVO.getViaPointType());
		embargoVO.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_UPDATE);
		Collection<EmbargoGeographicLevelVO> embargoGeographicLevelVOs =  new ArrayList<EmbargoGeographicLevelVO>();
		if( embargoDetailsVO.getGeographicLevels()!=null &&  embargoDetailsVO.getGeographicLevels().size()>0){
			embargoGeographicLevelVOs.addAll(embargoDetailsVO.getGeographicLevels());
			embargoVO.setGeographicLevels(embargoGeographicLevelVOs);
		}
		return embargoVO;
	}
}
