/*
 * SharedEmbargoPublishBuilder.java Created on Sep 04 , 2013
 *
 * Copyright 2013 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary
 * information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.msgbroker.message.vo.masterdata.PublishMessageEntityKeyVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.reco.defaults.EmbargoMasterMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.reco.defaults.EmbargoMasterPrevDetailsMessageVO;
import com.ibsplc.icargo.business.reco.defaults.EmbargoRules;
import com.ibsplc.icargo.business.reco.defaults.proxy.MsgBrokerMessageProxy;
import com.ibsplc.icargo.business.reco.defaults.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGeographicLevelVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoPublishVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.business.reco.defaults.vo.converter.EmbargoVOConverter;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.action.AbstractActionBuilder;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;


/**
 * The Class RecoDefaultsPublishBuilder.
 *
 * @author A-5219
 */

public class RecoDefaultsPublishBuilder extends AbstractActionBuilder {
	/** The Constant log. */
	private static final Log log = LogFactory.getLogger("RECO CONFIGURATION PUBLISH");
	/** The Constant UPDATE. */
	public static final String UPDATE = "U";
	/** The Constant DELETE. */
	public static final String DELETE = "D";
	/** The Constant INSERT. */
	public static final String INSERT = "I";
	/** The Constant CANCEL. */
	public static final String INACTIVE = "I";
	/** The Constant CANCELLED. */
	public static final String CANCELLED = "CANCELLED";
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
	public static final String ACTIVE = "A";
	public static final String SUSPEND = "S";
	public static final String DRAFT = "D";
	/** The Constant CANCELLED. */
	public static final String CANCEL= "C";
	private static final String CATEGORY_TYPES= "reco.defaults.category";
	private static final String COMPLIANCE_TYPES= "reco.defaults.compliancetype";
	private static final String APPLICABLE_TRANSACTIONS= "reco.defaults.applicabletransactions";
	private static final String  RECO_MAIN_SCREEN="REC001";
	/**
	 * Method		:	RecoDefaultsPublishBuilder.retainExistingEmbargoDetailsForPublish
	 * Added by 	:	A-4823 on 08-Mar-2016
	 * Used for 	:
	 * Parameters	:	@param embargoVO
	 * Parameters	:	@throws SystemException
	 * Parameters	:	@throws FinderException
	 * Return type	: 	void
	 *
	 * @param embargoVO the embargo vo
	 * @throws SystemException the system exception
	 * @throws FinderException the finder exception
	 */
	public void retainExistingEmbargoDetailsForPublish(EmbargoRulesVO embargoRulesFilterVO) throws SystemException, FinderException {
		log.entering("RecoDefaultsPublishBuilder", "retainExistingEmbargoDetailsForPublish");
		//EmbargoPublishVO embargoOldVO = new EmbargoPublishVO();
		EmbargoRulesVO embargoRulesVo=null;
		EmbargoFilterVO embargoFilterVO = new EmbargoFilterVO();
		embargoFilterVO.setCompanyCode(embargoRulesFilterVO.getCompanyCode());
		embargoFilterVO.setEmbargoRefNumber(embargoRulesFilterVO.getEmbargoReferenceNumber());
		embargoFilterVO.setHighestInactiveVersionFetch(true);
		
		if(embargoRulesFilterVO.getEmbargoReferenceNumber() !=null && embargoRulesFilterVO.getEmbargoReferenceNumber().trim().length() > 0 ){
			try{
				embargoRulesVo= EmbargoRules.findEmbargoDetails(embargoFilterVO);
			}
			catch(Exception e){
				log.entering("RecoDefaultsPublishBuilder", "NO Data Found");
			}
			if(embargoRulesVo!=null) {	
				EmbargoPublishVO embargoOldVO = populateOldEmbargoDetails(embargoRulesVo);
				embargoRulesFilterVO.setEmbargoPublishVO(embargoOldVO);
			}
		}
		else{
			log.entering("RecoDefaultsPublishBuilder", "No old data exists");
		}
	}
	/**
	 * Method		:	RecoDefaultsPublishBuilder.retainExistingEmbargoDetailsForCancelPublish
	 * Added by 	:	A-4823 on 09-Mar-2016
	 * Used for 	:
	 * Parameters	:	@param embargoVO
	 * Parameters	:	@throws SystemException
	 * Parameters	:	@throws FinderException
	 * Return type	: 	void
	 *
	 * @param embargoVO the embargo vo
	 * @throws SystemException the system exception
	 * @throws FinderException the finder exception
	 */
	public void retainExistingEmbargoDetailsForStatusUpdatePublish(Collection<EmbargoDetailsVO> embargoDetailsVOs) throws SystemException, FinderException {
		log.entering("RecoDefaultsPublishBuilder", "retainExistingEmbargoDetailsForStatusUpdatePublish");
		EmbargoRulesVO embargoVo=null;		
		EmbargoFilterVO embargoFilterVO = new EmbargoFilterVO();
		if(embargoDetailsVOs != null ){
			for(EmbargoDetailsVO embargoDetailsVO : embargoDetailsVOs){
				embargoFilterVO.setCompanyCode(embargoDetailsVO.getCompanyCode());
				embargoFilterVO.setEmbargoRefNumber(embargoDetailsVO.getEmbargoReferenceNumber());
				embargoFilterVO.setHighestInactiveVersionFetch(true);
				try{
					embargoVo=EmbargoRules.findEmbargoDetails(embargoFilterVO);
				}
				catch(Exception e){
					log.entering("RecoDefaultsPublishBuilder", "NO Data Found");
				}
				if(embargoVo!=null){	
					EmbargoPublishVO embargoOldVO = populateOldEmbargoDetails(embargoVo);
					embargoDetailsVO.setEmbargoPublishVO(embargoOldVO);	
					}
			}	
		}
	}
	/**
	 * 
	 * @param embargoVo
	 * @return
	 */
	private static EmbargoPublishVO populateOldEmbargoDetails(EmbargoRulesVO embargoVo){
		EmbargoPublishVO embargoOldVO = new EmbargoPublishVO();
		embargoOldVO.setEmbargoVersion(embargoVo.getEmbargoVersion());
		embargoOldVO.setCompanyCode(embargoVo.getCompanyCode());
		embargoOldVO.setDaysOfOperation(embargoVo.getDaysOfOperation());
		embargoOldVO.setDaysOfOperationApplicableOn(embargoVo.getDaysOfOperationApplicableOn());
		embargoOldVO.setDestination(embargoVo.getDestination());
		embargoOldVO.setDestinationType(embargoVo.getDestinationType());
		embargoOldVO.setEmbargoDescription(embargoVo.getEmbargoDescription());
		embargoOldVO.setCategory(embargoVo.getCategory());
		embargoOldVO.setComplianceType(embargoVo.getComplianceType());
		embargoOldVO.setApplicableTransactions(embargoVo.getApplicableTransactions());
		if(ERROR.equals(embargoVo.getEmbargoLevel()))
			{
			embargoOldVO.setEmbargoLevel(TO_ERROR);
			}
		else if(WARNING.equals(embargoVo.getEmbargoLevel()))
			{
			embargoOldVO.setEmbargoLevel(TO_WARNING);
			}
		else if(INFO.equals(embargoVo.getEmbargoLevel()))
			{
			embargoOldVO.setEmbargoLevel(TO_INFO);
			}
		else
			{
			embargoOldVO.setEmbargoLevel(embargoVo.getEmbargoLevel());
			}
		embargoOldVO.setEmbargoReferenceNumber(embargoVo.getEmbargoReferenceNumber());
		LocalDate endDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		embargoOldVO.setEndDate(endDate.setDate(TimeConvertor.toStringFormat(embargoVo.getEndDate(),TimeConvertor.CALENDAR_DATE_FORMAT)));
		embargoOldVO.setIsSuspended(embargoVo.getIsSuspended());
		embargoOldVO.setOrigin(embargoVo.getOrigin());
		embargoOldVO.setOriginType(embargoVo.getOriginType());
		embargoOldVO.setParameters(embargoVo.getParameters());
		embargoOldVO.setGeographicLevels(embargoVo.getGeographicLevels());
		embargoOldVO.setRemarks(embargoVo.getRemarks());
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		embargoOldVO.setStartDate(fromDate.setDate(TimeConvertor.toStringFormat(embargoVo.getStartDate(),TimeConvertor.CALENDAR_DATE_FORMAT)));
		if(CANCEL.equals(embargoVo.getStatus()))
			{
			embargoOldVO.setStatus(INACTIVE);
			}
		else
			{
			embargoOldVO.setStatus(embargoVo.getStatus());
			}
		embargoOldVO.setViaPoint(embargoVo.getViaPoint());
		embargoOldVO.setViaPointType(embargoVo.getViaPointType());
		return embargoOldVO;
	}
	/**
	 * 
	 * @param embargoVO
	 * @throws SystemException
	 * @throws FinderException 
	 */
	public void publishEmbargoDetails(EmbargoRulesVO embargoVO) throws SystemException, FinderException {
		log.entering("RecoDefaultsPublishBuilder", "publishEmbargoDetails");
		if(DRAFT.equals(embargoVO.getStatus())){
			return;
		}
		//fetching the old data for publish . In cancel case already the Old data will be set from Controller 
		//by entity find as version number will not be incremented in that case  
		if(embargoVO.getEmbargoPublishVO()==null ){
			retainExistingEmbargoDetailsForPublish(embargoVO);
		}
		
		if(embargoVO.getEmbargoPublishVO() !=null){
			
			if(!((ACTIVE.equals(embargoVO.getEmbargoPublishVO().getStatus()) && SUSPEND.equals(embargoVO.getStatus())) ||
					   (SUSPEND.equals(embargoVO.getEmbargoPublishVO().getStatus()) && ACTIVE.equals(embargoVO.getStatus()))||
					   (DRAFT.equals(embargoVO.getEmbargoPublishVO().getStatus())&& ACTIVE.equals(embargoVO.getStatus()))||
					(ACTIVE.equals(embargoVO.getEmbargoPublishVO().getStatus()) && CANCEL.equals(embargoVO.getStatus())) ||
					(SUSPEND.equals(embargoVO.getEmbargoPublishVO().getStatus()) && CANCEL.equals(embargoVO.getStatus())) ||
					(INACTIVE.equals(embargoVO.getEmbargoPublishVO().getStatus()) && ACTIVE.equals(embargoVO.getStatus())) ||
					(INACTIVE.equals(embargoVO.getEmbargoPublishVO().getStatus()) && SUSPEND.equals(embargoVO.getStatus()))||
					(INACTIVE.equals(embargoVO.getEmbargoPublishVO().getStatus()) && CANCEL.equals(embargoVO.getStatus()))
					)						
							){
					return;
					}
		}		
		if(embargoVO.getIsSuspended() || INACTIVE.equals(embargoVO.getStatus()))
			{
			embargoVO.setOperationalFlag(DELETE);
			}
		//EmbargoPublish embargoPublish = new EmbargoPublish(embargoVO);
		//embargoVO.setSernumber(embargoPublish.getEmbargoPk().getSerialNumber());
//		if(embargoVO.getEmbargoPublishVO() !=null 
//			&& (DRAFT.equals(embargoVO.getEmbargoPublishVO().getStatus()) && ACTIVE.equals(embargoVO.getStatus()) 
//				|| (INACTIVE.equals(embargoVO.getEmbargoPublishVO().getStatus()) && ACTIVE.equals(embargoVO.getStatus())))
//			&& embargoVO.getEmbargoPublishVO().getEmbargoVersion()==1){
//			embargoVO.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
//			embargoVO.setEmbargoPublishVO(null);
//		}
		if(embargoVO.getEmbargoPublishVO()==null){
			embargoVO.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
		}
		else {
			if(!EmbargoRulesVO.OPERATION_FLAG_DELETE.equals(embargoVO.getOperationalFlag()))		
			{
			embargoVO.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_UPDATE);
		}
		}
		
		Collection<EmbargoParameterVO> embargoParameterVOs= embargoVO.getParameters();
		Collection<EmbargoParameterVO> embargoParameterVOS= new ArrayList<EmbargoParameterVO>();
		if(embargoParameterVOs!=null && embargoParameterVOs.size()>0){
		for(EmbargoParameterVO embargoParameterVO : embargoParameterVOs){
			if(!DELETE.equals(embargoParameterVO.getOperationalFlag())){
				embargoParameterVOS.add(embargoParameterVO);
				}
			}
		}
		embargoVO.setParameters(embargoParameterVOS);
		Collection<EmbargoGeographicLevelVO> embargoGeographicLevelVOs= new ArrayList<EmbargoGeographicLevelVO>();
		if(embargoVO.getGeographicLevels() !=null){
			for(EmbargoGeographicLevelVO embargoGeographicLevelVO : embargoVO.getGeographicLevels()){
				if(!DELETE.equals(embargoGeographicLevelVO.getOperationFlag())){
					embargoGeographicLevelVOs.add(embargoGeographicLevelVO);
				}
			}
		}
		Collection<EmbargoMasterMessageVO> embargoMasterMessageVOs = new ArrayList<EmbargoMasterMessageVO>();
		EmbargoMasterMessageVO embargoMasterMessageVO = populateEmbargoMasterMessageVO(embargoVO,false);
		LogonAttributes logonAttributesVO = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		embargoMasterMessageVO.setStationCode(logonAttributesVO.getStationCode());
		embargoMasterMessageVOs.add(embargoMasterMessageVO);
	     //if(embargoMasterMessageVOs!=null){
		try {
			new MsgBrokerMessageProxy().encodeMessages(embargoMasterMessageVOs);
		} catch (ProxyException proxyException) {
			 log.log(Log.SEVERE, proxyException.getMessage());
		}
	    // }
	}
	/**
	 * 
	 * @param embargoMasterMessageVO
	 */
	private static void updateonetimeCode_Master(EmbargoMasterMessageVO embargoMasterMessageVO) {
		Map hashMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(CATEGORY_TYPES);
		oneTimeList.add(COMPLIANCE_TYPES);
		oneTimeList.add(APPLICABLE_TRANSACTIONS);
		try {
			hashMap= new SharedDefaultsProxy().findOneTimeValues(embargoMasterMessageVO.getCompanyCode(),oneTimeList);
		} catch (ProxyException e) {		
			log.log(Log.SEVERE, "ProxyException!!!!!!!!!!!!");
		} catch (SystemException e) {
			log.log(Log.SEVERE, "SystemException!!!!!!!!!");

		}
		if(hashMap!=null){

			for(OneTimeVO category : (Collection<OneTimeVO>)hashMap.get(CATEGORY_TYPES)){

				if(embargoMasterMessageVO.getCategory().equals(category.getFieldDescription())){
					embargoMasterMessageVO.setCategory(category.getFieldValue());
				}
			}

			for(OneTimeVO complianceType : (Collection<OneTimeVO>)hashMap.get(COMPLIANCE_TYPES)){
				if(embargoMasterMessageVO.getComplianceType().equals(complianceType.getFieldDescription())){
					embargoMasterMessageVO.setComplianceType(complianceType.getFieldValue());
				}
			}
			
			StringBuilder appTransactionsBuilder = new StringBuilder();
			for(OneTimeVO appTransactions : (Collection<OneTimeVO>)hashMap.get(APPLICABLE_TRANSACTIONS)){
				if(embargoMasterMessageVO.getApplicableTransactions().contains(",")){

					String[] applicableTransactions = embargoMasterMessageVO.getApplicableTransactions().split(",");
					for(String appTxn : applicableTransactions){
						if(appTxn.equals(appTransactions.getFieldDescription())){
							appTransactionsBuilder.append(appTransactions.getFieldValue()).append(",");
						}
						else if(appTxn.equals(appTransactions.getFieldValue())) {
							appTransactionsBuilder.append(appTransactions.getFieldValue()).append(",");
						}
					}

				}
				else{
					if(embargoMasterMessageVO.getApplicableTransactions().equals(appTransactions.getFieldDescription())){
						embargoMasterMessageVO.setApplicableTransactions(appTransactions.getFieldValue());
					}
					else if(embargoMasterMessageVO.getApplicableTransactions().equals(appTransactions.getFieldValue())) {
						embargoMasterMessageVO.setApplicableTransactions(appTransactions.getFieldValue());
					}
				}
			
			}
			if(appTransactionsBuilder!=null && appTransactionsBuilder.length() >0){
				embargoMasterMessageVO.setApplicableTransactions(appTransactionsBuilder.toString().substring(0,appTransactionsBuilder.length()-1));
			}
		}


	}
	/**
	 * 
	 * @param embargoVO
	 * @param isJob
	 * @return
	 */
	private static EmbargoMasterMessageVO populateEmbargoMasterMessageVO(EmbargoRulesVO embargoVO,boolean isJob){
		Collection<PublishMessageEntityKeyVO> publishMessageEntityKeyVOs = new ArrayList<PublishMessageEntityKeyVO>();
		EmbargoMasterMessageVO embargoMasterMessageVO = EmbargoVOConverter.convertToEmbargoMasterMessageVO(embargoVO);
		updateonetimeCode_Master(embargoMasterMessageVO);
		if(embargoMasterMessageVO.getPrevEmbargoDetailsForPublish()!=null){
		updateonetimeCode_PreviousData(embargoMasterMessageVO.getPrevEmbargoDetailsForPublish());
		}
		embargoMasterMessageVO.setMessageType(EmbargoMasterMessageVO.MESSAGE_TYPE_EMBMST);
		embargoMasterMessageVO.setMessageStandard(EmbargoMasterMessageVO.MESSAGE_STANDARD_TYPE);
		embargoMasterMessageVO.setPublishID(EmbargoMasterMessageVO.EMBMST_PUBLISID);
		embargoMasterMessageVO.setEntityName(EmbargoMasterMessageVO.KEY_EMBARGO);
		embargoMasterMessageVO.setEntityUpdateTime(new LocalDate(
				LocalDate.NO_STATION, Location.NONE, true));
		embargoMasterMessageVO.setCreationTime(new LocalDate(
				LocalDate.NO_STATION, Location.NONE, true));
		Map<String, String> businessKeys = new HashMap<String, String>();
		PublishMessageEntityKeyVO publishMessageEntityKeyVO = new PublishMessageEntityKeyVO();
		businessKeys.put(
				EmbargoMasterMessageVO.BUSINESSKEY_EMBIDF,
				String.valueOf(embargoVO.getEmbargoReferenceNumber())); 
		publishMessageEntityKeyVO
		.setEntityName(EmbargoMasterMessageVO.KEY_EMBARGO);
		publishMessageEntityKeyVO.setBusinessKeys(businessKeys);
		if(isJob)
			{
			publishMessageEntityKeyVO.setOperationFlag(embargoVO.getOperationalFlag());
			}
		else
		if(embargoVO.getIsSuspended() || INACTIVE.equals(embargoVO.getStatus()) ||CANCEL.equals(embargoVO.getStatus()))
			{
			publishMessageEntityKeyVO.setOperationFlag(DELETE);
			}
		else
			{
			publishMessageEntityKeyVO.setOperationFlag(embargoVO.getOperationalFlag());
			}
		publishMessageEntityKeyVOs.add(publishMessageEntityKeyVO);
		embargoMasterMessageVO.setPublishMessageEntityKeys(publishMessageEntityKeyVOs);
		return embargoMasterMessageVO;
		} 
	/**
	 * 
	 * @param prevEmbargoDetailsForPublish
	 */
	private static void updateonetimeCode_PreviousData(
			EmbargoMasterPrevDetailsMessageVO prevEmbargoDetailsForPublish) {
		Map hashMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(CATEGORY_TYPES);
		oneTimeList.add(COMPLIANCE_TYPES);
		oneTimeList.add(APPLICABLE_TRANSACTIONS);
		try {
			hashMap= new SharedDefaultsProxy().findOneTimeValues(prevEmbargoDetailsForPublish.getCompanyCode(),oneTimeList);
		} catch (ProxyException e) {		
			log.log(Log.SEVERE, "ProxyException!!!!!!!!!!!!");
		} catch (SystemException e) {
			log.log(Log.SEVERE, "SystemException!!!!!!!!!");
		
		}
		for(OneTimeVO category : (Collection<OneTimeVO>)hashMap.get(CATEGORY_TYPES)){
			if(prevEmbargoDetailsForPublish.getCategory().equals(category.getFieldDescription())){
				prevEmbargoDetailsForPublish.setCategory(category.getFieldValue());
			}
		}
		for(OneTimeVO complianceType : (Collection<OneTimeVO>)hashMap.get(COMPLIANCE_TYPES)){
			if(prevEmbargoDetailsForPublish.getComplianceType().equals(complianceType.getFieldDescription())){
				prevEmbargoDetailsForPublish.setComplianceType(complianceType.getFieldValue());
			}
		}
		StringBuilder appTransactionsBuilder = new StringBuilder();
		for(OneTimeVO appTransactions : (Collection<OneTimeVO>)hashMap.get(APPLICABLE_TRANSACTIONS)){
			if(prevEmbargoDetailsForPublish.getApplicableTransactions().contains(",")){

				String[] applicableTransactions = prevEmbargoDetailsForPublish.getApplicableTransactions().split(",");
				for(String appTxn : applicableTransactions){
					if(appTxn.equals(appTransactions.getFieldDescription())){
						appTransactionsBuilder.append(appTransactions.getFieldValue()).append(",");
					}
					else if(appTxn.equals(appTransactions.getFieldValue())) {
						appTransactionsBuilder.append(appTransactions.getFieldValue()).append(",");
					}
				}

			}
			else{
				if(prevEmbargoDetailsForPublish.getApplicableTransactions().equals(appTransactions.getFieldDescription())){
					prevEmbargoDetailsForPublish.setApplicableTransactions(appTransactions.getFieldValue());
				}
				else if(prevEmbargoDetailsForPublish.getApplicableTransactions().equals(appTransactions.getFieldValue())) {
					prevEmbargoDetailsForPublish.setApplicableTransactions(appTransactions.getFieldValue());
				}
				
			}			

		}
		if(appTransactionsBuilder!=null && appTransactionsBuilder.length() >0){
			prevEmbargoDetailsForPublish.setApplicableTransactions(appTransactionsBuilder.toString().substring(0,appTransactionsBuilder.length()-1));
		}
		
	}
	/**
	 * 
	 * @param embargoDetailsVOs
	 * @throws SystemException
	 * @throws FinderException 
	 */
	public void statusUpdatePublish(Collection<EmbargoDetailsVO> embargoDetailsVOs )
			throws SystemException, FinderException {
		log.entering("RecoDefaultsPublishBuilder", "statusUpdatePublish");
		if (embargoDetailsVOs != null) {
			for (EmbargoDetailsVO embargoDetailsVO : embargoDetailsVOs) {
				//source ID will be set from approval from mainscreen approval publish is preventing 
				//because as part of save embargo publish will be triggered
				if (RECO_MAIN_SCREEN.equals(embargoDetailsVO.getSourceId())){
					continue;
				}
				EmbargoRulesVO embargoVO = EmbargoVOConverter.convertToEmbargoVO(embargoDetailsVO);
				embargoVO.setEmbargoPublishVO(embargoDetailsVO.getEmbargoPublishVO());
				publishEmbargoDetails(embargoVO);
			}
		}
	}
}
