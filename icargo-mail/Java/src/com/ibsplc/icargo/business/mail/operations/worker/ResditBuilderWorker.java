/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.worker.ResditBuilderWorker.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Sep 2, 2016
 *
 *  Copyright 2016 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.worker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.worker.ResditBuilderWorker.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Sep 2, 2016	:	Draft
 */
@Module("mail")
@SubModule("operations")
public class ResditBuilderWorker extends RequestWorker{
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#execute(com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext)
	 *	Added by 			: A-4809 on Sep 2, 2016
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws SystemException 
	 */
	@Override
	public void execute(WorkerContext arg0) throws SystemException {
		log.entering("ResditBuilderWorker", "execute");
/*		ResditBuilderJobScheduleVO jobScheduleVO =
			(ResditBuilderJobScheduleVO)workerContext.getJobScheduleVO();*/
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		log.entering("invokeResditReceiver", "execute"); 
		despatchRequest("invokeResditReceiver", logonAttributes.getCompanyCode());
		log.exiting("invokeResditReceiver", "execute");
		Collection<ResditEventVO> resditEvents =despatchcheckForResditEventsRequest(logonAttributes);


		log.log(Log.FINEST, "flagged events ", resditEvents);
		if(resditEvents != null && !resditEvents.isEmpty()) {
			buildFlaggedResdits(resditEvents);
		}

		log.exiting("ResditBuilderWorker", "execute");
	}
	public Collection<ResditEventVO> despatchcheckForResditEventsRequest(
			LogonAttributes logonAttributes) {
		return despatchRequest("checkForResditEvents", logonAttributes.getCompanyCode());
	}
	
	private void buildFlaggedResdits(Collection<ResditEventVO> resditEvents) throws SystemException{
		log.entering("ResditBuilderWorker", "buildFlaggedResdits");
		Map<String, Collection<ResditEventVO>> resditsMapForPA = groupResditsForPA(resditEvents);
		Map<String, Collection<ResditEventVO>> resditsMapForPA_V1_0 = new HashMap<String, Collection<ResditEventVO>>();
		Map<String, Collection<ResditEventVO>> resditsMapForPA_V1_1 = new HashMap<String, Collection<ResditEventVO>>();
		Map<String, Collection<ResditEventVO>> resditsMapForPA_V1_2 = new HashMap<String, Collection<ResditEventVO>>();
		Map<String, Collection<ResditEventVO>> resditsMap = new HashMap<String, Collection<ResditEventVO>>();
		
		Collection<ResditEventVO> resditsArrayListForPA_V1_0 = null;
		Collection<ResditEventVO> resditsArrayListForPA_V1_1 = null;
		Collection<ResditEventVO> resditsArrayListForPA_V1_2 = null;
		

		if(resditsMapForPA!=null){

			for(Map.Entry<String, Collection<ResditEventVO>> paResdits : resditsMapForPA.entrySet()) {

				resditsArrayListForPA_V1_0 = new ArrayList<ResditEventVO>();
				resditsArrayListForPA_V1_1 = new ArrayList<ResditEventVO>();
				resditsArrayListForPA_V1_2 = new ArrayList<ResditEventVO>();

				if(paResdits != null) {
					ArrayList<ResditEventVO> paResditEvents =  (ArrayList<ResditEventVO>)paResdits.getValue();
					if(paResditEvents != null) {								
						for(ResditEventVO resditEventVO : paResditEvents){								
							if(("1.0").equals(resditEventVO.getResditVersion())){
								resditsArrayListForPA_V1_0.add(resditEventVO);
							}else if(("1.1").equals(resditEventVO.getResditVersion()) || (MailConstantsVO.M49_1_1).equals(resditEventVO.getResditVersion()) ){
								resditsArrayListForPA_V1_1.add(resditEventVO);
							}else if(("DOM").equals(resditEventVO.getResditVersion())){
								resditsArrayListForPA_V1_2.add(resditEventVO);
							}
						}

						if(resditsArrayListForPA_V1_0!=null){
							resditsMapForPA_V1_0.put(paResdits.getKey(), resditsArrayListForPA_V1_0);
						}
						if(resditsArrayListForPA_V1_0!=null){
							resditsMapForPA_V1_1.put(paResdits.getKey(), resditsArrayListForPA_V1_1);
						}
						if(resditsArrayListForPA_V1_2!=null){
							resditsMapForPA_V1_2.put(paResdits.getKey(), resditsArrayListForPA_V1_2);
						}
					}
				}
			}
		}

		if(resditsMapForPA_V1_0 != null && resditsMapForPA_V1_0.size() > 0){
			groupAndSendResdits(resditsMapForPA_V1_0);
		}

		if(resditsMapForPA_V1_1 != null && resditsMapForPA_V1_1.size() > 0){
			groupAndSendResdits(resditsMapForPA_V1_1);
		}
		if(resditsMapForPA_V1_2 != null && resditsMapForPA_V1_2.size() > 0){
			groupAndSendResdits(resditsMapForPA_V1_2);
		}


		log.exiting("ResditBuilderWorker", "buildFlaggedResdits");
	}

	private Map<String, Collection<ResditEventVO>> groupResditsForPA(Collection<ResditEventVO> resditEvents) throws SystemException{
		log.entering("ResditBuilderWorker", "groupResditsForPA");
		Map<String, Collection<ResditEventVO>> paResditMap = new HashMap<String, Collection<ResditEventVO>>();
		for(ResditEventVO resditEventVO : resditEvents) {
				String pAandStation = getResditGroupingKey( resditEventVO);
				Collection<ResditEventVO> paResdits = paResditMap.get(pAandStation);
				if(paResdits == null) {
					paResdits = new ArrayList<ResditEventVO>();
					paResditMap.put(pAandStation, paResdits);
				}
				paResdits.add(resditEventVO);
			}	
		log.exiting("ResditBuilderWorker", "groupResditsForPA");
		return paResditMap;
	}

	private String getResditGroupingKey(ResditEventVO resditEventVO) {
		String groupingParameters = null;
		String groupingKey=null;
		try {
			groupingParameters = findSystemParameterValue(MailConstantsVO.MAIL_OPERATION_RESDIT_GROUPING_PARAMETERS);
		} catch (SystemException e) {
			log.log(Log.FINE, "System Exception", e);
		}
		if (Objects.nonNull(groupingParameters)) {
			StringBuilder sb = new StringBuilder();
			if (groupingParameters.contains(MailConstantsVO.STATION)) {
					groupingKey=sb.append(resditEventVO.getEventPort()).toString();
			}
			if (groupingParameters.contains(MailConstantsVO.RDTEVT)) {
					groupingKey = sb.append(resditEventVO.getResditEventCode()).toString();
			}
			if (groupingParameters.contains(MailConstantsVO.HDLCAR)) {
					groupingKey = sb.append(resditEventVO.getCarrierCode()).toString();
			}
			if (groupingParameters.contains(MailConstantsVO.POSTAL_AUTHORITY_CODE)) {
	                groupingKey=sb.append(resditEventVO.getPaCode()).toString();
            }
			if (groupingParameters.contains(MailConstantsVO.RECIEVER_ADDRESS)
					&&MailConstantsVO.FLAG_YES.equalsIgnoreCase(resditEventVO.getCarditExist())
					&&resditEventVO.getReciever()!=null && resditEventVO.getReciever().trim().length()>0) {
				groupingKey=sb.append(resditEventVO.getReciever()).toString();

			}
		}
		return groupingKey;
	}
	private Map<String, Collection<ResditEventVO>> groupResditsForActualSender(Collection<ResditEventVO> resditEvents) {
		log.entering("ResditBuilderWorker", "groupResditsForActualSender");
		Map<String, Collection<ResditEventVO>> actualSenderResditMap = new HashMap<String, Collection<ResditEventVO>>();
		for(ResditEventVO resditEventVO : resditEvents) {
			if(resditEventVO.getActualSenderId() != null && 
					resditEventVO.getActualSenderId().trim().length() > 0) {
				Collection<ResditEventVO> actualSenderResdits = actualSenderResditMap.get(resditEventVO.getActualSenderId());
				if(actualSenderResdits == null) {
					actualSenderResdits = new ArrayList<ResditEventVO>();
					actualSenderResditMap.put(resditEventVO.getActualSenderId(), actualSenderResdits);
				}
				actualSenderResdits.add(resditEventVO);
			}
		}
		log.exiting("ResditBuilderWorker", "groupResditsForActualSender");
		return actualSenderResditMap;
	}
	
	private void groupAndSendResdits(Map<String, Collection<ResditEventVO>> resditsMap) {
		log.entering("ResditBuilderWorker", "groupAndSendResdits");
		Collection<ResditEventVO> resditsForCarditArrayList = null;
		Collection<ResditEventVO> resditsWithoutCarditArrayList = null;
		for(Map.Entry<String, Collection<ResditEventVO>> paResdit : resditsMap.entrySet()) {
			resditsWithoutCarditArrayList = new ArrayList<ResditEventVO>();
			resditsForCarditArrayList = new ArrayList<ResditEventVO>();
			for(ResditEventVO resditEventVO : paResdit.getValue()) {
				if(MailConstantsVO.M49_1_1.equals(resditEventVO.getResditVersion()))
				{
				resditEventVO.setM49Resdit(true);
				resditEventVO.setResditVersion("1.1");
				}
				if(MailConstantsVO.FLAG_NO.equalsIgnoreCase(resditEventVO.getCarditExist())) {
					if(!(resditEventVO.isM49Resdit() && MailConstantsVO.RESDIT_HANDOVER_ONLINE.equals(resditEventVO.getResditEventCode())))
					{
					resditsWithoutCarditArrayList.add(resditEventVO);
					}
				}else {
					if(!(resditEventVO.isM49Resdit() && MailConstantsVO.RESDIT_HANDOVER_ONLINE.equals(resditEventVO.getResditEventCode())))
					{
					resditsForCarditArrayList.add(resditEventVO);
					}
				}					
			}
			/*
			 * Till Now : 
			 * 1. Grouped For PA
			 * 2. Grouped For Version
			 * 3. Categorized with CARDIT Existance
			 */
			if(resditsWithoutCarditArrayList != null && resditsWithoutCarditArrayList.size() > 0) {
				despatchRequest("buildResdit", resditsWithoutCarditArrayList);
			}
			if(resditsForCarditArrayList != null && resditsForCarditArrayList.size() > 0) {
				Map<String, Collection<ResditEventVO>> resditsMapForActualSender = 
					groupResditsForActualSender(resditsForCarditArrayList);
				/*
				 * Grouped For Actual Sender Identification
				 */
				if(resditsMapForActualSender != null && resditsMapForActualSender.size() > 0) {
					for(Map.Entry<String, Collection<ResditEventVO>> resditVOs : resditsMapForActualSender.entrySet()) {
						despatchRequest("buildResdit", resditVOs.getValue());
					}							
				}
			}
		}
		log.exiting("ResditBuilderWorker", "groupAndSendResdits");	
	}
	
	//Added by A-5945 for ICRd-129920
	private String findSystemParameterValue(String syspar)
			throws SystemException {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		HashMap<String, String> systemParameterMap = Proxy.getInstance().get(SharedDefaultsProxy.class)
				.findSystemParameterByCodes(systemParameters);
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#instantiateJobScheduleVO()
	 *	Added by 			: A-4809 on Sep 2, 2016
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	@Override
	public JobScheduleVO instantiateJobScheduleVO() {
		return new JobScheduleVO(){

			@Override
			public int getPropertyCount() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getIndex(String arg0) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String getValue(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setValue(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}

}
