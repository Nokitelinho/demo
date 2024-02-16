/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.worker.ProcessTempCarditMessagesWorker.java
 *
 *	Created by	:	A-6287
 *	Created on	:	01-Mar-2020
 *
 *  Copyright 2020 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.worker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.CarditTempDetails;
import com.ibsplc.icargo.business.mail.operations.CarditTempDetailsPK;
import com.ibsplc.icargo.business.mail.operations.MailBoxId;
import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.SharedProxyException;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.CarditContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditHandoverInformationVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditReceptacleVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditReferenceInformationVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditTotalVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditTransportationVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.business.mail.operations.vo.EDIInterchangeVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailRdtMasterVO;
import com.ibsplc.icargo.business.mail.operations.vo.RdtMasterFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditProcessJobScheduleVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.CarditMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.CarditTempMsgVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.ContainerInformationMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.EDIMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.HandoverInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.ReceptacleInformationMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.ReceptacleMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.ReferenceInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.TotalsInformationMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.TransportInformationMessageVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.session.GenerationFailedException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;
import com.ibsplc.xibase.server.framework.cache.CacheFactory;
import com.ibsplc.icargo.business.mail.operations.cache.OfficeOfExchangeCache;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.worker.ProcessTempCarditMessagesWorker.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-6287	:	01-Mar-2020	:	Draft
 */
@Module("mail")
@SubModule("operations")
public class ProcessTempCarditMessagesWorker  extends RequestWorker {

	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	public static final String INVALID_AIRLINE_CODE ="shared.airline.invalidairline";
	private static final int FLTNUM_LEN = 5;
	private static final String NO_OF_CHAR_ALLOWED_FOR_MAILTAG = "mailtracking.defaults.noofcharsallowedformailtag";
	private HashMap<String,String> systemParameters = null;
	private static final String PAD_DGT = "0";
	private EDIMessageVO ediMsgeVO=null;
	private Map<String,String> exchangeOfficeMap;
	private static final String DOMESTIC_PACODE="mailtracking.domesticmra.usps";
	private static final String INVALID_MAILTAG = "Invalid Mailtag";
	private static final String INITIAL_TIME = "00:00:00";


	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#execute(com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext)
	 *	Added by 			: A-6287 on 01-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param workercontext
	 *	Parameters	:	@throws SystemException
	 */
	@Override
	public void execute(WorkerContext workercontext) throws SystemException {
		log.entering("ProcessTempCarditMessagesWorker", "execute");
		Collection<CarditTempMsgVO> carditTmpMsgVOs =null;
		HashMap<String,EDIMessageVO> ediMessageMap = null;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		CarditProcessJobScheduleVO carditProcessJobScheduleVO = (CarditProcessJobScheduleVO)workercontext.getJobScheduleVO();
		carditTmpMsgVOs = getTempCarditMessages(logonAttributes,carditProcessJobScheduleVO
				);
		if(carditTmpMsgVOs!=null && carditTmpMsgVOs.size()>0){
			ediMessageMap = constructCarditMsgVOs(carditTmpMsgVOs);
		}
		if(ediMessageMap!=null){
			saveCarditMessages(ediMessageMap);
		}

		log.exiting("ProcessTempCarditMessagesWorker", "execute");
	}

	/**
	 * 	Method		:	ProcessTempCarditMessagesWorker.saveCarditMessages
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param ediMessageVOs
	 *	Return type	: 	void
	 * @throws SystemException
	 * @throws FinderException
	 */
	private void saveCarditMessages(HashMap<String,EDIMessageVO> ediMessageMap) throws SystemException{
		log.entering("ProcessTempCarditMessagesWorker", "saveCarditMessages");
		EDIInterchangeVO ediInterchangeVO = null;
		EDIInterchangeVO newediInterchangeVO = null;
		CarditTempDetailsPK carditTempDetailsPK =null;
		CarditTempDetails carditTempDetails=null;

		Collection<String> failedConsignments=new ArrayList<>();
	//	for(EDIMessageVO ediMsgVO :ediMessageVOs){
		for (Map.Entry<String, EDIMessageVO> entry : ediMessageMap.entrySet()) {
try {
	ediInterchangeVO = getEDIInterchangeVO(entry.getValue());
}catch(Exception e){
try {
	carditTempDetailsPK = getPK(entry.getKey());
	carditTempDetails = CarditTempDetails.find(carditTempDetailsPK);
	carditTempDetails.setIsProcessed("Y");
	carditTempDetails.setMessageStatus("F");
	carditTempDetails.setErrorCodes(e.getMessage());
}catch(Exception ex){
	log.log(Log.SEVERE,ex.getMessage());
}
continue;
}

			newediInterchangeVO = convertMailoperationVOs(ediInterchangeVO);
			try{
				String errorCode = null;
				carditTempDetailsPK =  getPK(entry.getKey());
				carditTempDetails = CarditTempDetails.find(carditTempDetailsPK);

				if(!failedConsignments.contains(carditTempDetailsPK.getConsignmentIdentifier())){
					Collection<ErrorVO> errorVOs =despatchRequest("saveCarditMsgs",newediInterchangeVO);
					if(Objects.nonNull(errorVOs) && !errorVOs.isEmpty()) {
						errorCode= errorVOs.stream()
								  .filter(errorVO-> (errorVO!=null && errorVO.getErrorDisplayType().equals(ErrorDisplayType.ERROR)))
								  .collect(Collectors.toCollection(ArrayList::new)).iterator().next().getErrorCode();
					}

				}else{
					errorCode ="previous record failed";
				}
				if(errorCode !=null && "CARDITDOM".equalsIgnoreCase(carditTempDetails.getMessageTypeIdentifier())){
					failedConsignments.add(carditTempDetailsPK.getConsignmentIdentifier());
				}

				carditTempDetails.setIsProcessed(errorCode!=null&& !errorCode.startsWith("BSE_")?"N":"Y");
				carditTempDetails.setMessageStatus(errorCode==null?"S":"F");
				carditTempDetails.setErrorCodes(errorCode!=null?errorCode:null);

				//Added by A-7540
				//Find call to CarditTempDetails removed
			    if(ediInterchangeVO.getErrorCodes() != null && !ediInterchangeVO.getErrorCodes().isEmpty()){
					if(carditTempDetails != null){
			         	for(String error :ediInterchangeVO.getErrorCodes() ){
			    	        if(INVALID_MAILTAG.equals(error))   {
								    carditTempDetails.setIsProcessed("Y");
								    carditTempDetails.setMessageStatus("W");
								    carditTempDetails.setErrorCodes(error);
			    	   }
			    	}
			    }
			    }


			}catch (Exception e){
				if(e instanceof MailTrackingBusinessException){
					carditTempDetails.setIsProcessed("Y");
					carditTempDetails.setMessageStatus("F");
				}else{
					carditTempDetails.setIsProcessed("N");
					carditTempDetails.setMessageStatus("F");
				}
			}
			/*try {
				new MailOperationsProxy().saveCarditMsgs(newediInterchangeVO);
			} catch (ProxyException e) {

			}*/
		}
		log.exiting("ProcessTempCarditMessagesWorker", "saveCarditMessages");
	}

	/**
	 * 	Method		:	ProcessTempCarditMessagesWorker.getPK
	 *	Added by 	:	A-6287 on 03-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param newediInterchangeVO
	 *	Return type	: 	void
	 */
	private  CarditTempDetailsPK getPK(String key) {
		String[] keys = key.split("\\|");
		CarditTempDetailsPK carditTempDetailsPK =new CarditTempDetailsPK();
		carditTempDetailsPK.setCompanyCode(keys[0]);
		carditTempDetailsPK.setSequenceNumber(Long.parseLong(keys[2]));
	//	Collection<CarditVO> carditMessages = newediInterchangeVO.getCarditMessages();
	//	for(CarditVO caditVO : carditMessages){
			carditTempDetailsPK.setConsignmentIdentifier(keys[1]);
	//	}
		return carditTempDetailsPK;
	}

	/**
	 * 	Method		:	ProcessTempCarditMessagesWorker.constructCarditMsgVOs
	 *	Added by 	:	A-6287 on 01-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param carditTmpMsgVOs
	 *	Parameters	:	@return
	 *	Return type	: 	Collection<EDIMessageVO>
	 */
	private HashMap<String,EDIMessageVO> constructCarditMsgVOs(Collection<CarditTempMsgVO> carditTmpMsgVOs) {
		log.entering("ProcessTempCarditMessagesWorker", "constructCarditMsgVOs");
		HashMap<String,EDIMessageVO> ediMessagesMap = new HashMap<>();
		String key=null;
		EDIMessageVO ediMessageVO;
		CarditMessageVO carditMsgVO=null;
		boolean isChild =false;
		for(CarditTempMsgVO carditTmpMsgVO : carditTmpMsgVOs){
			//key = carditTmpMsgVO.getCompanyCode().concat(carditTmpMsgVO.getConsignmentIdentifier()).concat(carditTmpMsgVO.getSequenceNumber());
			key = new StringBuilder(carditTmpMsgVO.getCompanyCode())
					.append("|").append(carditTmpMsgVO.getConsignmentIdentifier())
					.append("|").append(carditTmpMsgVO.getSequenceNumber()).toString();
			isChild =false;
			if(ediMessagesMap.containsKey(key)){
				ediMessageVO =ediMessagesMap.get(key);
				carditMsgVO =((ArrayList<CarditMessageVO>)ediMessageVO.getCarditMessages()).get(0);
				isChild =true;
			}else{
			ediMessageVO = new EDIMessageVO();
				ediMessagesMap.put(key,ediMessageVO);
				carditMsgVO = new CarditMessageVO();
				ediMessageVO.setCarditMessages(new ArrayList<CarditMessageVO>());
				ediMessageVO.getCarditMessages().add(carditMsgVO);
			}
			//ediMessageVO = new EDIMessageVO();
			if(!isChild) {
			ediMessageVO.setCompanyCode(carditTmpMsgVO.getCompanyCode());
			ediMessageVO.setSyntaxID(carditTmpMsgVO.getSyntaxID());
			ediMessageVO.setSyntaxVersion(Integer.parseInt(carditTmpMsgVO.getSyntaxVersion()));
			ediMessageVO.setSenderID(carditTmpMsgVO.getSenderID());

			ediMessageVO.setProcessingFailed(Boolean.parseBoolean(carditTmpMsgVO.getProcessingFailed()));
			ediMessageVO.setSenderPartnerIDQualifier(carditTmpMsgVO.getSenderPartnerIDQualifier());
			ediMessageVO.setRecipientID(carditTmpMsgVO.getRecipientID());
			ediMessageVO.setRecipientPartnerIDQualifier(carditTmpMsgVO.getRecipientPartnerIDQualifier());
			ediMessageVO.setPreparationDate(getDate(carditTmpMsgVO.getPreparationDate()));
			ediMessageVO.setInterchangeControlReference(carditTmpMsgVO.getInterchangeControlReference());
			ediMessageVO.setTestIndicator(carditTmpMsgVO.getTestIndicator());
			ediMessageVO
					.setInterchangeControlCount(Integer.parseInt(carditTmpMsgVO.getInterchangeControlCount()));
			ediMessageVO.setTrailerInterchangeControlReference(
					carditTmpMsgVO.getTrailerInterchangeControlReference());

				//carditMsgVO = new CarditMessageVO();
			carditMsgVO.setControllingAgency(carditTmpMsgVO.getControllingAgency());
			carditMsgVO.setMessageReferenceNumber(carditTmpMsgVO.getMessageReferenceNumber());
			carditMsgVO.setMessageTypeIdentifier(carditTmpMsgVO.getMessageTypeIdentifier());
			carditMsgVO.setMessageVersionNumber(carditTmpMsgVO.getMessageVersionNumber());
			carditMsgVO.setMessageReleaseNumber(carditTmpMsgVO.getMessageReleaseNumber());
			carditMsgVO.setAssociationAssignedCode(carditTmpMsgVO.getAssociationAssignedCode());
			carditMsgVO.setConsignmentIdentifier(carditTmpMsgVO.getConsignmentIdentifier());
			carditMsgVO.setMessageFunction(carditTmpMsgVO.getMessageFunction());
			carditMsgVO.setDateTimePeriodQualifier(carditTmpMsgVO.getDateTimePeriodQualifier());
			carditMsgVO.setDateTimeFormatQualifier(carditTmpMsgVO.getDateTimeFormatQualifier());
			carditMsgVO.setTrailerMessageReferenceNumber(carditTmpMsgVO.getTrailerMessageReferenceNumber());
			carditMsgVO.setNumberOfSegments(Integer.parseInt(carditTmpMsgVO.getNumberOfSegments()));
			carditMsgVO.setErrorCardit(Boolean.parseBoolean(carditTmpMsgVO.getErrorCardit()));
			carditMsgVO.setMailCategoryCodeIndicator(carditTmpMsgVO.getMailCategoryCodeIndicator());
			carditMsgVO.setConsignmentCompletionDate(getDate(carditTmpMsgVO.getConsignmentCompletionDate()));
			carditMsgVO.setReqDeliveryTime(getDate(carditTmpMsgVO.getReqDeliveryTime()));
			carditMsgVO.setMailCategoryCode(carditTmpMsgVO.getMailCategoryCode());

			Collection<TransportInformationMessageVO> transportInfos =new ArrayList<TransportInformationMessageVO>();
			transportInfos = getTransportInfos(carditTmpMsgVO);
			carditMsgVO.setTransportInformation(transportInfos);
			/*
			Collection<ReceptacleInformationMessageVO> receptacleVOs =null;
			receptacleVOs = getReceptacleVO(carditTmpMsgVO);
			carditMsgVO.setReceptacleInformation(receptacleVOs);*/

				carditMsgVO.setReceptacleInformation(new ArrayList<ReceptacleInformationMessageVO>());
				carditMsgVO.getReceptacleInformation().addAll(getReceptacleVO(carditTmpMsgVO));

			Collection<TotalsInformationMessageVO> totalsInfos =null;
			totalsInfos = getTotalsInfoVO(carditTmpMsgVO);
			carditMsgVO.setTotalsInformation(totalsInfos);

			Collection<ReferenceInformationVO> refInfos =null;
			refInfos=getRefInfoVOs(carditTmpMsgVO);
			carditMsgVO.setReferenceInformation(refInfos);

			Collection<HandoverInformationVO> handoverInfos =null;
			handoverInfos = getHandoverInfos(carditTmpMsgVO);
			carditMsgVO.setHandoverInformation(handoverInfos);

			Collection<ContainerInformationMessageVO> containerInfos =null;
			containerInfos = getContainerInfoVO(carditTmpMsgVO);
			carditMsgVO.setContainerInformation(containerInfos);

			}else{
				//Collection<ReceptacleInformationMessageVO> receptacleVOs =null;
				//receptacleVOs = getReceptacleVO(carditTmpMsgVO);
				carditMsgVO.getReceptacleInformation().addAll(getReceptacleVO(carditTmpMsgVO));
			}
/*			if(carditMsgVOs==null){
				carditMsgVOs = new ArrayList<CarditMessageVO>();
				carditMsgVOs.add(carditMsgVO);
			}else{
				carditMsgVOs.add(carditMsgVO);
			}
			ediMessageVO.setCarditMessages(carditMsgVOs);

			if(ediMessageVOs==null){
				ediMessageVOs = new ArrayList<EDIMessageVO>();
				ediMessageVOs.add(ediMessageVO);
			}else{
				ediMessageVOs.add(ediMessageVO);
			}*/
			//ediMessageVOs=ediMessagesMap.values();
		}
		log.exiting("ProcessTempCarditMessagesWorker", "constructCarditMsgVOs");
		return ediMessagesMap;
	}

	/**
	 * 	Method		:	ProcessTempCarditMessagesWorker.getTransportInfos
	 *	Added by 	:	A-6287 on 04-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param carditTmpMsgVO
	 *	Parameters	:	@return
	 *	Return type	: 	Collection<TransportInformationMessageVO>
	 */
	private Collection<TransportInformationMessageVO> getTransportInfos(CarditTempMsgVO carditTmpMsgVO) {
		log.entering("ProcessTempCarditMessagesWorker", "getTransportInfos");
		TransportInformationMessageVO transportInfo =null;
		Collection<TransportInformationMessageVO> transportInfos = null;
		ArrayList<Integer> sizelist = new ArrayList<Integer>();
		int maxSize=0;

		String[] trpStgQfr =null;
		String[] cnyRef =null;
		String[] trpIdr =null;
		String[] carIdr =null;
		String[] depLoc =null;
		String[] depPlc =null;
		String[] arrLoc =null;
		String[] arrPlc =null;
		String[] modTrp =null;
		String[] carNam =null;
		String[] codLstQdr =null;
		String[] agyCarCod =null;
		String[] trpLegRat =null;
		String[] cntRef =null;
		String[] depPlcNam =null;
		String[] depCodLst =null;
		String[] depCodLstAgy =null;
		String[] arrPlcNam =null;
		String[] arrCodQfr =null;
		String[] arrCodAgy =null;
		String[] depDat =null;
		String[] arrDat =null;

		if(carditTmpMsgVO.getTransportStageQualifier()!=null && carditTmpMsgVO.getTransportStageQualifier().trim().length() >0){
			if(carditTmpMsgVO.getTransportStageQualifier().contains(";")){
				trpStgQfr = carditTmpMsgVO.getTransportStageQualifier().split(";");
			}else{
				trpStgQfr =new String[1];
				trpStgQfr[0] = carditTmpMsgVO.getTransportStageQualifier();
			}
			if(trpStgQfr!=null){
				sizelist.add(trpStgQfr.length);
			}
		}
		if(carditTmpMsgVO.getConveyanceReference()!=null && carditTmpMsgVO.getConveyanceReference().trim().length() >0){
			if(carditTmpMsgVO.getConveyanceReference().contains(";")){
				cnyRef = carditTmpMsgVO.getConveyanceReference().split(";");
			}else{
				cnyRef =new String[1];
				cnyRef[0] = carditTmpMsgVO.getConveyanceReference();
			}
			if(cnyRef!=null){
				sizelist.add(cnyRef.length);
			}
		}
		if(carditTmpMsgVO.getTransportIdentification()!=null && carditTmpMsgVO.getTransportIdentification().trim().length() >0){
			if(carditTmpMsgVO.getTransportIdentification().contains(";")){
				trpIdr = carditTmpMsgVO.getTransportIdentification().split(";");
			}else{
				trpIdr =new String[1];
				trpIdr[0] = carditTmpMsgVO.getTransportIdentification();
			}
			if(trpIdr!=null){
				sizelist.add(trpIdr.length);
			}
		}
		if(carditTmpMsgVO.getCarrierID()!=null && carditTmpMsgVO.getCarrierID().trim().length() >0){
			if(carditTmpMsgVO.getCarrierID().contains(";")){
				carIdr = carditTmpMsgVO.getCarrierID().split(";");
			}else{
				carIdr =new String[1];
				carIdr[0] = carditTmpMsgVO.getCarrierID();
			}
			if(carIdr!=null){
				sizelist.add(carIdr.length);
			}
		}
		if(carditTmpMsgVO.getDepartureLocationQualifier()!=null && carditTmpMsgVO.getDepartureLocationQualifier().trim().length() >0){
			if(carditTmpMsgVO.getDepartureLocationQualifier().contains(";")){
				depLoc = carditTmpMsgVO.getDepartureLocationQualifier().split(";");
			}else{
				depLoc =new String[1];
				depLoc[0] = carditTmpMsgVO.getDepartureLocationQualifier();
			}
			if(depLoc!=null){
				sizelist.add(depLoc.length);
			}
		}
		if(carditTmpMsgVO.getDepartureLocationQualifier()!=null && carditTmpMsgVO.getDepartureLocationQualifier().trim().length() >0){
			if(carditTmpMsgVO.getDeparturePlace().contains(";")){
				depPlc = carditTmpMsgVO.getDeparturePlace().split(";");
			}else{
				depPlc =new String[1];
				depPlc[0] = carditTmpMsgVO.getDeparturePlace();
			}
			if(depPlc!=null){
				sizelist.add(depPlc.length);
			}
		}
		if(carditTmpMsgVO.getArrivalLocationQualifier()!=null && carditTmpMsgVO.getArrivalLocationQualifier().trim().length() >0){
			if(carditTmpMsgVO.getArrivalLocationQualifier().contains(";")){
				arrLoc = carditTmpMsgVO.getArrivalLocationQualifier().split(";");
			}else{
				arrLoc =new String[1];
				arrLoc[0] = carditTmpMsgVO.getArrivalLocationQualifier();
			}
			if(arrLoc!=null){
				sizelist.add(arrLoc.length);
			}
		}
		if(carditTmpMsgVO.getArrivalPlace()!=null && carditTmpMsgVO.getArrivalPlace().trim().length() >0){
			if(carditTmpMsgVO.getArrivalPlace().contains(";")){
				arrPlc = carditTmpMsgVO.getArrivalPlace().split(";");
			}else{
				arrPlc =new String[1];
				arrPlc[0] = carditTmpMsgVO.getArrivalPlace();
			}
			if(arrPlc!=null){
				sizelist.add(arrPlc.length);
			}
		}
		if(carditTmpMsgVO.getModeOfTransport()!=null && carditTmpMsgVO.getModeOfTransport().trim().length() >0){
			if(carditTmpMsgVO.getModeOfTransport().contains(";")){
				modTrp = carditTmpMsgVO.getModeOfTransport().split(";");
			}else{
				modTrp =new String[1];
				modTrp[0] = carditTmpMsgVO.getModeOfTransport();
			}
			if(modTrp!=null){
				sizelist.add(modTrp.length);
			}
		}
		if(carditTmpMsgVO.getCarrierName()!=null && carditTmpMsgVO.getCarrierName().trim().length() >0){
			if(carditTmpMsgVO.getCarrierName().contains(";")){
				carNam = carditTmpMsgVO.getCarrierName().split(";");
			}else{
				carNam =new String[1];
				carNam[0] = carditTmpMsgVO.getCarrierName();
			}
			if(carNam!=null){
				sizelist.add(carNam.length);
			}
		}
		if(carditTmpMsgVO.getCodeListQualifier()!=null  && carditTmpMsgVO.getCodeListQualifier().trim().length() >0){
			if(carditTmpMsgVO.getCodeListQualifier().contains(";")){
				codLstQdr = carditTmpMsgVO.getCodeListQualifier().split(";");
			}else{
				codLstQdr =new String[1];
				codLstQdr[0] = carditTmpMsgVO.getCodeListQualifier();
			}
			if(codLstQdr!=null){
				sizelist.add(codLstQdr.length);
			}
		}
		if(carditTmpMsgVO.getAgencyForCarrierCode()!=null && carditTmpMsgVO.getAgencyForCarrierCode().trim().length() >0){
			if(carditTmpMsgVO.getAgencyForCarrierCode().contains(";")){
				agyCarCod = carditTmpMsgVO.getAgencyForCarrierCode().split(";");
			}else{
				agyCarCod =new String[1];
				agyCarCod[0] = carditTmpMsgVO.getAgencyForCarrierCode();
			}
			if(agyCarCod!=null){
				sizelist.add(agyCarCod.length);
			}
		}
		if(carditTmpMsgVO.getTransportLegRate()!=null && carditTmpMsgVO.getTransportLegRate().trim().length() >0){
			if(carditTmpMsgVO.getTransportLegRate().contains(";")){
				trpLegRat = carditTmpMsgVO.getTransportLegRate().split(";");
			}else{
				trpLegRat =new String[1];
				trpLegRat[0] = carditTmpMsgVO.getTransportLegRate();
			}
			if(trpLegRat!=null){
				sizelist.add(trpLegRat.length);
			}
		}
		if(carditTmpMsgVO.getContractReference()!=null && carditTmpMsgVO.getContractReference().trim().length() >0){
			if(carditTmpMsgVO.getContractReference().contains(";")){
				cntRef = carditTmpMsgVO.getContractReference().split(";");
			}else{
				cntRef =new String[1];
				cntRef[0] = carditTmpMsgVO.getContractReference();
			}
			if(cntRef!=null){
				sizelist.add(cntRef.length);
			}
		}
		if(carditTmpMsgVO.getDeparturePlaceName()!=null && carditTmpMsgVO.getDeparturePlaceName().trim().length() >0){
			if(carditTmpMsgVO.getDeparturePlaceName().contains(";")){
				depPlcNam = carditTmpMsgVO.getDeparturePlaceName().split(";");
			}else{
				depPlcNam =new String[1];
				depPlcNam[0] = carditTmpMsgVO.getDeparturePlaceName();
			}
			if(depPlcNam!=null){
				sizelist.add(depPlcNam.length);
			}
		}
		if(carditTmpMsgVO.getDepartureCodeListQualifier()!=null && carditTmpMsgVO.getDepartureCodeListQualifier().trim().length() >0){
			if(carditTmpMsgVO.getDepartureCodeListQualifier().contains(";")){
				depCodLst = carditTmpMsgVO.getDepartureCodeListQualifier().split(";");
			}else{
				depCodLst =new String[1];
				depCodLst[0] = carditTmpMsgVO.getDepartureCodeListQualifier();
			}
			if(depCodLst!=null){
				sizelist.add(depCodLst.length);
			}
		}
		if(carditTmpMsgVO.getDepartureCodeListAgency()!=null && carditTmpMsgVO.getDepartureCodeListAgency().trim().length() >0){
			if(carditTmpMsgVO.getDepartureCodeListAgency().contains(";")){
				depCodLstAgy = carditTmpMsgVO.getDepartureCodeListAgency().split(";");
			}else{
				depCodLstAgy =new String[1];
				depCodLstAgy[0] = carditTmpMsgVO.getDepartureCodeListAgency();
			}
			if(depCodLstAgy!=null){
				sizelist.add(depCodLstAgy.length);
			}
		}
		if(carditTmpMsgVO.getArrivalPlaceName()!=null && carditTmpMsgVO.getArrivalPlaceName().trim().length() >0){
			if(carditTmpMsgVO.getArrivalPlaceName().contains(";")){
				arrPlcNam = carditTmpMsgVO.getArrivalPlaceName().split(";");
			}else{
				arrPlcNam =new String[1];
				arrPlcNam[0] = carditTmpMsgVO.getArrivalPlaceName();
			}
			if(arrPlcNam!=null){
				sizelist.add(arrPlcNam.length);
			}
		}
		if(carditTmpMsgVO.getArrivalCodeListQualifier()!=null && carditTmpMsgVO.getArrivalCodeListQualifier().trim().length() >0){
			if(carditTmpMsgVO.getArrivalCodeListQualifier().contains(";")){
				arrCodQfr = carditTmpMsgVO.getArrivalCodeListQualifier().split(";");
			}else{
				arrCodQfr =new String[1];
				arrCodQfr[0] = carditTmpMsgVO.getArrivalCodeListQualifier();
			}
			if(arrCodQfr!=null){
				sizelist.add(arrCodQfr.length);
			}
		}
		if(carditTmpMsgVO.getArrivalCodeListAgency()!=null && carditTmpMsgVO.getArrivalCodeListAgency().trim().length() >0){
			if(carditTmpMsgVO.getArrivalCodeListAgency().contains(";")){
				arrCodAgy = carditTmpMsgVO.getArrivalCodeListAgency().split(";");
			}else{
				arrCodAgy =new String[1];
				arrCodAgy[0] = carditTmpMsgVO.getArrivalCodeListAgency();
			}
			if(arrCodAgy!=null){
				sizelist.add(arrCodAgy.length);
			}
		}
		if(carditTmpMsgVO.getDepartureDate()!=null && carditTmpMsgVO.getDepartureDate().trim().length() >0){
			if(carditTmpMsgVO.getDepartureDate().contains(";")){
				depDat = carditTmpMsgVO.getDepartureDate().split(";");
			}else{
				depDat =new String[1];
				depDat[0] = carditTmpMsgVO.getDepartureDate();
			}
			if(depDat!=null){
				sizelist.add(depDat.length);
			}
		}
		if(carditTmpMsgVO.getArrivalDate()!=null && carditTmpMsgVO.getArrivalDate().trim().length() >0){
			if(carditTmpMsgVO.getArrivalDate().contains(";")){
				arrDat = carditTmpMsgVO.getArrivalDate().split(";");
			}else{
				arrDat =new String[1];
				arrDat[0] = carditTmpMsgVO.getArrivalDate();
			}
			if(arrDat!=null){
				sizelist.add(arrDat.length);
			}
		}


		if(sizelist!=null && !sizelist.isEmpty()){
			maxSize = Collections.max(sizelist);
			for(int i =0;i<maxSize;i++){
				transportInfo =new TransportInformationMessageVO();
				if(trpStgQfr!=null && i<trpStgQfr.length){
					transportInfo.setTransportStageQualifier(trpStgQfr[i]);
				}
				if(cnyRef!=null && i<cnyRef.length){
					transportInfo.setConveyanceReference(cnyRef[i]);
				}
				if(trpIdr!=null && i<trpIdr.length){
					transportInfo.setTransportIdentification(trpIdr[i]);
				}
				if(carIdr!=null && i<carIdr.length){
					transportInfo.setCarrierID(carIdr[i]);
				}
				if(depLoc!=null && i<depLoc.length){
					transportInfo.setDepartureLocationQualifier(depLoc[i]);
				}
				if(depPlc!=null && i<depPlc.length){
					transportInfo.setDeparturePlace(depPlc[i]);
				}
				if(arrLoc!=null && i<arrLoc.length){
					transportInfo.setArrivalLocationQualifier(arrLoc[i]);
				}
				if(arrPlc!=null && i<arrPlc.length){
					transportInfo.setArrivalPlace(arrPlc[i]);
				}
				if(modTrp!=null && i<modTrp.length){
					transportInfo.setModeOfTransport(modTrp[i]);
				}
				if(carNam!=null && i<carNam.length){
					transportInfo.setCarrierName(carNam[i]);
				}
				if(codLstQdr!=null && i<codLstQdr.length){
					transportInfo.setCodeListQualifier(codLstQdr[i]);
				}
				if(agyCarCod!=null && i<agyCarCod.length){
					transportInfo.setAgencyForCarrierCode(agyCarCod[i]);
				}
				if(trpLegRat!=null && i<trpLegRat.length){
					transportInfo.setTransportLegRate(trpLegRat[i]);
				}
				if(cntRef!=null && i<cntRef.length){
					transportInfo.setContractReference(cntRef[i]);
				}
				if(depPlcNam!=null && i<depPlcNam.length){
					transportInfo.setDeparturePlaceName(depPlcNam[i]);
				}
				if(depCodLst!=null && i<depCodLst.length){
					transportInfo.setDepartureCodeListQualifier(depCodLst[i]);
				}
				if(depCodLstAgy!=null && i<depCodLstAgy.length){
					transportInfo.setDepartureCodeListAgency(depCodLstAgy[i]);
				}
				if(arrPlcNam!=null && i<arrPlcNam.length){
					transportInfo.setArrivalPlaceName(arrPlcNam[i]);
				}
				if(arrCodQfr!=null && i<arrCodQfr.length){
					transportInfo.setArrivalCodeListQualifier(arrCodQfr[i]);
				}
				if(arrCodAgy!=null && i<arrCodAgy.length){
					transportInfo.setArrivalCodeListAgency(arrCodAgy[i]);
				}
				if(depDat!=null && i<depDat.length){
					transportInfo.setDepartureDate(getDate(depDat[i]));
				}
				if(arrDat!=null && i<arrDat.length){
					transportInfo.setArrivalDate(getDate(arrDat[i]));
				}

				if(transportInfos==null){
					transportInfos = new ArrayList<TransportInformationMessageVO>();
					transportInfos.add(transportInfo);
				}else{
					transportInfos.add(transportInfo);
				}
			}
		}
		log.exiting("ProcessTempCarditMessagesWorker", "getTransportInfos");
		return transportInfos;
	}

	/**
	 * 	Method		:	ProcessTempCarditMessagesWorker.getContainerInfoVO
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param carditTmpMsgVO
	 *	Parameters	:	@return
	 *	Return type	: 	Collection<ContainerInformationMessageVO>
	 */
	private Collection<ContainerInformationMessageVO> getContainerInfoVO(CarditTempMsgVO carditTmpMsgVO) {
		log.entering("ProcessTempCarditMessagesWorker", "getContainerInfoVO");
		Collection<ContainerInformationMessageVO> containerInfos = null;
		ContainerInformationMessageVO containerInfo = null;
		ArrayList<Integer> sizelist = new ArrayList<Integer>();
		int maxSize=0;
		String[] eqpQfr =null;
		String[] cntNum =null;
		String[] codLstAgy =null;
		String[] cntTyp =null;
		String[] codLstQfr =null;
		String[] typCodLstAgy =null;
		String[] cntJrnyTyp =null;
		String[] msrApp =null;
		String[] msrDim =null;
		String[] msrUnt =null;
		String[] cntWgt =null;
		String[] selNum =null;

		if(carditTmpMsgVO.getEquipmentQualifier()!=null && carditTmpMsgVO.getEquipmentQualifier().trim().length()>0){
			if(carditTmpMsgVO.getEquipmentQualifier().contains(";")){
				eqpQfr = carditTmpMsgVO.getEquipmentQualifier().split(";");
			}else{
				eqpQfr =new String[1];
				eqpQfr[0] = carditTmpMsgVO.getEquipmentQualifier();
			}
			if(eqpQfr!=null){
				sizelist.add(eqpQfr.length);
			}
		}
		if(carditTmpMsgVO.getContainerNumber()!=null && carditTmpMsgVO.getContainerNumber().trim().length()>0){
			if(carditTmpMsgVO.getContainerNumber().contains(";")){
				cntNum = carditTmpMsgVO.getContainerNumber().split(";");
			}else{
				cntNum =new String[1];
				cntNum[0] = carditTmpMsgVO.getContainerNumber();
			}
			if(cntNum!=null){
				sizelist.add(cntNum.length);
			}
		}
		if(carditTmpMsgVO.getCodeListResponsibleAgency()!=null && carditTmpMsgVO.getCodeListResponsibleAgency().trim().length()>0){
			if(carditTmpMsgVO.getCodeListResponsibleAgency().contains(";")){
				codLstAgy = carditTmpMsgVO.getCodeListResponsibleAgency().split(";");
			}else{
				codLstAgy =new String[1];
				codLstAgy[0] = carditTmpMsgVO.getCodeListResponsibleAgency();
			}
			if(codLstAgy!=null){
				sizelist.add(codLstAgy.length);
			}
		}
		if(carditTmpMsgVO.getContainerType()!=null && carditTmpMsgVO.getContainerType().trim().length()>0){
			if(carditTmpMsgVO.getContainerType().contains(";")){
				cntTyp = carditTmpMsgVO.getContainerType().split(";");
			}else{
				cntTyp =new String[1];
				cntTyp[0] = carditTmpMsgVO.getContainerType();
			}
			if(cntTyp!=null){
				sizelist.add(cntTyp.length);
			}
		}
		if(carditTmpMsgVO.getCodeListQualifier()!=null && carditTmpMsgVO.getCodeListQualifier().trim().length()>0){
			if(carditTmpMsgVO.getCodeListQualifier().contains(";")){
				codLstQfr = carditTmpMsgVO.getCodeListQualifier().split(";");
			}else{
				codLstQfr =new String[1];
				codLstQfr[0] = carditTmpMsgVO.getCodeListQualifier();
			}
			if(codLstQfr!=null){
				sizelist.add(codLstQfr.length);
			}
		}
		if(carditTmpMsgVO.getTypeCodeListResponsibleAgency()!=null && carditTmpMsgVO.getTypeCodeListResponsibleAgency().trim().length()>0){
			if(carditTmpMsgVO.getTypeCodeListResponsibleAgency().contains(";")){
				typCodLstAgy = carditTmpMsgVO.getTypeCodeListResponsibleAgency().split(";");
			}else{
				typCodLstAgy =new String[1];
				typCodLstAgy[0] = carditTmpMsgVO.getTypeCodeListResponsibleAgency();
			}
			if(typCodLstAgy!=null){
				sizelist.add(typCodLstAgy.length);
			}
		}
		if(carditTmpMsgVO.getContainerJourneyIdentifier()!=null && carditTmpMsgVO.getContainerJourneyIdentifier().trim().length()>0){
			if(carditTmpMsgVO.getContainerJourneyIdentifier().contains(";")){
				cntJrnyTyp = carditTmpMsgVO.getContainerJourneyIdentifier().split(";");
			}else{
				cntJrnyTyp =new String[1];
				cntJrnyTyp[0] = carditTmpMsgVO.getContainerJourneyIdentifier();
			}
			if(cntJrnyTyp!=null){
				sizelist.add(cntJrnyTyp.length);
			}
		}
		if(carditTmpMsgVO.getMeasurementApplication()!=null && carditTmpMsgVO.getMeasurementApplication().trim().length()>0){
			if(carditTmpMsgVO.getMeasurementApplication().contains(";")){
				msrApp = carditTmpMsgVO.getMeasurementApplication().split(";");
			}else{
				msrApp =new String[1];
				msrApp[0] = carditTmpMsgVO.getMeasurementApplication();
			}
			if(msrApp!=null){
				sizelist.add(msrApp.length);
			}
		}
		if(carditTmpMsgVO.getMeasurementDimension()!=null && carditTmpMsgVO.getMeasurementDimension().trim().length()>0){
			if(carditTmpMsgVO.getMeasurementDimension().contains(";")){
				msrDim = carditTmpMsgVO.getMeasurementDimension().split(";");
			}else{
				msrDim =new String[1];
				msrDim[0] = carditTmpMsgVO.getMeasurementDimension();
			}
			if(msrDim!=null){
				sizelist.add(msrDim.length);
			}
		}
		if(carditTmpMsgVO.getMeasureUnitQualifier()!=null && carditTmpMsgVO.getMeasureUnitQualifier().trim().length()>0){
			if(carditTmpMsgVO.getMeasureUnitQualifier().contains(";")){
				msrUnt = carditTmpMsgVO.getMeasureUnitQualifier().split(";");
			}else{
				msrUnt =new String[1];
				msrUnt[0] = carditTmpMsgVO.getMeasureUnitQualifier();
			}
			if(msrUnt!=null){
				sizelist.add(msrUnt.length);
			}
		}
		if(carditTmpMsgVO.getContainerWeight()!=null && carditTmpMsgVO.getContainerWeight().trim().length()>0){
			if(carditTmpMsgVO.getContainerWeight().contains(";")){
				cntWgt = carditTmpMsgVO.getContainerWeight().split(";");
			}else{
				cntWgt =new String[1];
				cntWgt[0] = carditTmpMsgVO.getContainerWeight();
			}
			if(cntWgt!=null){
				sizelist.add(cntWgt.length);
			}
		}
		if(carditTmpMsgVO.getSealNumber()!=null && carditTmpMsgVO.getSealNumber().trim().length()>0){
			if(carditTmpMsgVO.getSealNumber().contains(";")){
				selNum = carditTmpMsgVO.getSealNumber().split(";");
			}else{
				selNum =new String[1];
				selNum[0] = carditTmpMsgVO.getSealNumber();
			}
			if(selNum!=null){
				sizelist.add(selNum.length);
			}
		}

		if(sizelist!=null && !sizelist.isEmpty()){
			maxSize = Collections.max(sizelist);
			for(int i =0;i<maxSize;i++){
				containerInfo = new ContainerInformationMessageVO();
				if(eqpQfr!=null && i<eqpQfr.length){
					containerInfo.setEquipmentQualifier(eqpQfr[i]);
				}
				if(cntNum!=null && i<cntNum.length){
					containerInfo.setContainerNumber(cntNum[i]);
				}
				if(codLstAgy!=null && i<codLstAgy.length){
					containerInfo.setCodeListResponsibleAgency(codLstAgy[i]);
				}
				if(cntTyp!=null && i<cntTyp.length){
					containerInfo.setContainerType(cntTyp[i]);
				}
				if(codLstQfr!=null && i<codLstQfr.length){
					containerInfo.setCodeListQualifier(codLstQfr[i]);
				}
				if(typCodLstAgy!=null && i<typCodLstAgy.length){
					containerInfo.setTypeCodeListResponsibleAgency(typCodLstAgy[i]);
				}
				if(cntJrnyTyp!=null && i<cntJrnyTyp.length){
					containerInfo.setContainerJourneyIdentifier(cntJrnyTyp[i]);
				}
				if(msrApp!=null && i<msrApp.length){
					containerInfo.setMeasurementApplication(msrApp[i]);
				}
				if(msrDim!=null && i<msrDim.length){
					containerInfo.setMeasurementDimension(msrDim[i]);
				}
				if(msrUnt!=null && i<msrUnt.length){
					containerInfo.setMeasureUnitQualifier(msrUnt[i]);
				}
				if(cntWgt!=null && i<cntWgt.length){
					containerInfo.setContainerWeight(cntWgt[i]);
				}
				if(selNum!=null && i<selNum.length){
					containerInfo.setSealNumber(selNum[i]);
				}

				if(containerInfos==null){
					containerInfos = new ArrayList<ContainerInformationMessageVO>();
					containerInfos.add(containerInfo);
				}else{
					containerInfos.add(containerInfo);
				}
			}
		}
		log.exiting("ProcessTempCarditMessagesWorker", "getContainerInfoVO");
		return containerInfos;
	}

	/**
	 * 	Method		:	ProcessTempCarditMessagesWorker.getHandoverInfos
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param carditTmpMsgVO
	 *	Parameters	:	@return
	 *	Return type	: 	Collection<HandoverInformationVO>
	 */
	private Collection<HandoverInformationVO> getHandoverInfos(CarditTempMsgVO carditTmpMsgVO) {
		log.entering("ProcessTempCarditMessagesWorker", "getHandoverInfos");
		Collection<HandoverInformationVO> handoverInfos =null;
		HandoverInformationVO handoverInfo=null;
		ArrayList<Integer> sizelist = new ArrayList<Integer>();
		int maxSize=0;
		String[] trpStgQfr =null;
		String[] orgLocQfr =null;
		String[] orgLocIdr =null;
		String[] orgLocNam =null;
		String[] orgCodLst =null;
		String[] orgCodLstAgy =null;
		String[] dstLocQfr =null;
		String[] dstLocIdr =null;
		String[] dstLocNam =null;
		String[] dstCodLst =null;
		String[] dstCodLstAgy =null;
		String[] dtmPrdQfr =null;
		String[] dtmFmtQfr =null;
		String[] orgCutoff =null;
		String[] dstCutoff =null;

		if(carditTmpMsgVO.getRefInfoTransportStageQualifier()!=null && carditTmpMsgVO.getRefInfoTransportStageQualifier().trim().length()>0){
			if(carditTmpMsgVO.getRefInfoTransportStageQualifier().contains(";")){
				trpStgQfr = carditTmpMsgVO.getRefInfoTransportStageQualifier().split(";");
			}else{
				trpStgQfr =new String[1];
				trpStgQfr[0] = carditTmpMsgVO.getRefInfoTransportStageQualifier();
			}
			if(trpStgQfr!=null){
				sizelist.add(trpStgQfr.length);
			}
		}
		if(carditTmpMsgVO.getHandoverOriginLocationQualifier()!=null && carditTmpMsgVO.getHandoverOriginLocationQualifier().trim().length() >0){
			if(carditTmpMsgVO.getHandoverOriginLocationQualifier().contains(";")){
				orgLocQfr = carditTmpMsgVO.getHandoverOriginLocationQualifier().split(";");
			}else{
				orgLocQfr =new String[1];
				orgLocQfr[0] = carditTmpMsgVO.getHandoverOriginLocationQualifier();
			}
			if(orgLocQfr!=null){
				sizelist.add(orgLocQfr.length);
			}
		}
		if(carditTmpMsgVO.getHandoverOriginLocationIdentifier()!=null && carditTmpMsgVO.getHandoverOriginLocationIdentifier().trim().length() >0){
			if(carditTmpMsgVO.getHandoverOriginLocationIdentifier().contains(";")){
				orgLocIdr = carditTmpMsgVO.getHandoverOriginLocationIdentifier().split(";");
			}else{
				orgLocIdr =new String[1];
				orgLocIdr[0] = carditTmpMsgVO.getHandoverOriginLocationIdentifier();
			}
			if(orgLocIdr!=null){
				sizelist.add(orgLocIdr.length);
			}
		}
		if(carditTmpMsgVO.getHandoverOriginLocationName()!=null && carditTmpMsgVO.getHandoverOriginLocationName().trim().length() >0){
			if(carditTmpMsgVO.getHandoverOriginLocationName().contains(";")){
				orgLocNam = carditTmpMsgVO.getHandoverOriginLocationName().split(";");
			}else{
				orgLocNam =new String[1];
				orgLocNam[0] = carditTmpMsgVO.getHandoverOriginLocationName();
			}
			if(orgLocNam!=null){
				sizelist.add(orgLocNam.length);
			}
		}
		if(carditTmpMsgVO.getHandoverOriginCodeListQualifier()!=null && carditTmpMsgVO.getHandoverOriginCodeListQualifier().trim().length() >0){
			if(carditTmpMsgVO.getHandoverOriginCodeListQualifier().contains(";")){
				orgCodLst = carditTmpMsgVO.getHandoverOriginCodeListQualifier().split(";");
			}else{
				orgCodLst =new String[1];
				orgCodLst[0] = carditTmpMsgVO.getHandoverOriginCodeListQualifier();
			}
			if(orgCodLst!=null){
				sizelist.add(orgCodLst.length);
			}
		}
		if(carditTmpMsgVO.getHandoverOriginCodeListAgency()!=null && carditTmpMsgVO.getHandoverOriginCodeListAgency().trim().length() >0){
			if(carditTmpMsgVO.getHandoverOriginCodeListAgency().contains(";")){
				orgCodLstAgy = carditTmpMsgVO.getHandoverOriginCodeListAgency().split(";");
			}else{
				orgCodLstAgy =new String[1];
				orgCodLstAgy[0] = carditTmpMsgVO.getHandoverOriginCodeListAgency();
			}
			if(orgCodLstAgy!=null){
				sizelist.add(orgCodLstAgy.length);
			}
		}
		if(carditTmpMsgVO.getHandoverDestnLocationQualifier()!=null && carditTmpMsgVO.getHandoverDestnLocationQualifier().trim().length() >0){
			if(carditTmpMsgVO.getHandoverDestnLocationQualifier().contains(";")){
				dstLocQfr = carditTmpMsgVO.getHandoverDestnLocationQualifier().split(";");
			}else{
				dstLocQfr =new String[1];
				dstLocQfr[0] = carditTmpMsgVO.getHandoverDestnLocationQualifier();
			}
			if(dstLocQfr!=null){
				sizelist.add(dstLocQfr.length);
			}
		}
		if(carditTmpMsgVO.getHandoverDestnLocationIdentifier()!=null && carditTmpMsgVO.getHandoverDestnLocationIdentifier().trim().length() >0){
			if(carditTmpMsgVO.getHandoverDestnLocationIdentifier().contains(";")){
				dstLocIdr = carditTmpMsgVO.getHandoverDestnLocationIdentifier().split(";");
			}else{
				dstLocIdr =new String[1];
				dstLocIdr[0] = carditTmpMsgVO.getHandoverDestnLocationIdentifier();
			}
			if(dstLocIdr!=null){
				sizelist.add(dstLocIdr.length);
			}
		}
		if(carditTmpMsgVO.getHandoverDestnLocationName()!=null && carditTmpMsgVO.getHandoverDestnLocationName().trim().length() >0){
			if(carditTmpMsgVO.getHandoverDestnLocationName().contains(";")){
				dstLocNam = carditTmpMsgVO.getHandoverDestnLocationName().split(";");
			}else{
				dstLocNam =new String[1];
				dstLocNam[0] = carditTmpMsgVO.getHandoverDestnLocationName();
			}
			if(dstLocNam!=null){
				sizelist.add(dstLocNam.length);
			}
		}
		if(carditTmpMsgVO.getHandoverDestnCodeListQualifier()!=null && carditTmpMsgVO.getHandoverDestnCodeListQualifier().trim().length() >0){
			if(carditTmpMsgVO.getHandoverDestnCodeListQualifier().contains(";")){
				dstCodLst = carditTmpMsgVO.getHandoverDestnCodeListQualifier().split(";");
			}else{
				dstCodLst =new String[1];
				dstCodLst[0] = carditTmpMsgVO.getHandoverDestnCodeListQualifier();
			}
			if(dstCodLst!=null){
				sizelist.add(dstCodLst.length);
			}
		}
		if(carditTmpMsgVO.getHandoverDestnCodeListAgency()!=null && carditTmpMsgVO.getHandoverDestnCodeListAgency().trim().length() >0){
			if(carditTmpMsgVO.getHandoverDestnCodeListAgency().contains(";")){
				dstCodLstAgy = carditTmpMsgVO.getHandoverDestnCodeListAgency().split(";");
			}else{
				dstCodLstAgy =new String[1];
				dstCodLstAgy[0] = carditTmpMsgVO.getHandoverDestnCodeListAgency();
			}
			if(dstCodLstAgy!=null){
				sizelist.add(dstCodLstAgy.length);
			}
		}
		if(carditTmpMsgVO.getDateTimePeriodQualifier()!=null && carditTmpMsgVO.getDateTimePeriodQualifier().trim().length() >0){
			if(carditTmpMsgVO.getDateTimePeriodQualifier().contains(";")){
				dtmPrdQfr = carditTmpMsgVO.getDateTimePeriodQualifier().split(";");
			}else{
				dtmPrdQfr =new String[1];
				dtmPrdQfr[0] = carditTmpMsgVO.getDateTimePeriodQualifier();
			}
			if(dtmPrdQfr!=null){
				sizelist.add(dtmPrdQfr.length);
			}
		}
		if(carditTmpMsgVO.getDateTimeFormatQualifier()!=null && carditTmpMsgVO.getDateTimeFormatQualifier().trim().length() >0){
			if(carditTmpMsgVO.getDateTimeFormatQualifier().contains(";")){
				dtmFmtQfr = carditTmpMsgVO.getDateTimeFormatQualifier().split(";");
			}else{
				dtmFmtQfr =new String[1];
				dtmFmtQfr[0] = carditTmpMsgVO.getDateTimeFormatQualifier();
			}
			if(dtmFmtQfr!=null){
				sizelist.add(dtmFmtQfr.length);
			}
		}
		if(carditTmpMsgVO.getOriginCutOffPeriod()!=null && carditTmpMsgVO.getOriginCutOffPeriod().trim().length() >0){
			if(carditTmpMsgVO.getOriginCutOffPeriod().contains(";")){
				orgCutoff = carditTmpMsgVO.getOriginCutOffPeriod().split(";");
			}else{
				orgCutoff =new String[1];
				orgCutoff[0] = carditTmpMsgVO.getOriginCutOffPeriod();
			}
			if(orgCutoff!=null){
				sizelist.add(orgCutoff.length);
			}
		}
		if(carditTmpMsgVO.getDestinationCutOffPeriod()!=null && carditTmpMsgVO.getDestinationCutOffPeriod().trim().length() >0){
			if(carditTmpMsgVO.getDestinationCutOffPeriod().contains(";")){
				dstCutoff = carditTmpMsgVO.getDestinationCutOffPeriod().split(";");
			}else{
				dstCutoff =new String[1];
				dstCutoff[0] = carditTmpMsgVO.getDestinationCutOffPeriod();
			}
			if(dstCutoff!=null){
				sizelist.add(dstCutoff.length);
			}
		}
		if(sizelist!=null && !sizelist.isEmpty()){
			maxSize = Collections.max(sizelist);
			for(int i=0;i<maxSize;i++){
				handoverInfo = new HandoverInformationVO();
				if(trpStgQfr!=null && i<trpStgQfr.length){
					handoverInfo.setTransportStageQualifier(trpStgQfr[i]);
				}
				if(orgLocQfr!=null && i<orgLocQfr.length){
					handoverInfo.setHandoverOriginLocationQualifier(orgLocQfr[i]);
				}
				if(orgLocIdr!=null && i<orgLocIdr.length){
					handoverInfo.setHandoverOriginLocationIdentifier(orgLocIdr[i]);
				}
				if(orgLocNam!=null && i<orgLocNam.length){
					handoverInfo.setHandoverOriginLocationName(orgLocNam[i]);
				}
				if(orgCodLst!=null && i<orgCodLst.length){
					handoverInfo.setHandoverOriginCodeListQualifier(orgCodLst[i]);
				}
				if(orgCodLstAgy!=null && i<orgCodLstAgy.length){
					handoverInfo.setHandoverOriginCodeListAgency(orgCodLstAgy[i]);
				}
				if(dstLocQfr!=null && i<dstLocQfr.length){
					handoverInfo.setHandoverDestnLocationQualifier(dstLocQfr[i]);
				}
				if(dstLocIdr!=null && i<dstLocIdr.length){
					handoverInfo.setHandoverDestnLocationIdentifier(dstLocIdr[i]);
				}
				if(dstLocNam!=null && i<dstLocNam.length){
					handoverInfo.setHandoverDestnLocationName(dstLocNam[i]);
				}
				if(dstCodLst!=null && i<dstCodLst.length){
					handoverInfo.setHandoverDestnCodeListQualifier(dstCodLst[i]);
				}
				if(dstCodLstAgy!=null && i<dstCodLstAgy.length){
					handoverInfo.setHandoverDestnCodeListAgency(dstCodLstAgy[i]);
				}
				if(dtmPrdQfr!=null && i<dtmPrdQfr.length){
					handoverInfo.setDateTimePeriodQualifier(dtmPrdQfr[i]);
				}
				if(dtmFmtQfr!=null && i<dtmFmtQfr.length){
					handoverInfo.setDateTimeFormatQualifier(dtmFmtQfr[i]);
				}
				if(dtmFmtQfr!=null && i<dtmFmtQfr.length){
					handoverInfo.setOriginCutOffPeriod(getDate(orgCutoff!=null?orgCutoff[i]:null));
				}
				if(dtmFmtQfr!=null && i<dtmFmtQfr.length){
				handoverInfo.setDestinationCutOffPeriod(getDate(dstCutoff!=null?dstCutoff[i]:null));
				}
				if(handoverInfos==null){
					handoverInfos=new ArrayList<HandoverInformationVO>();
					handoverInfos.add(handoverInfo);
				}else{
					handoverInfos.add(handoverInfo);
				}
			}
		}
		log.exiting("ProcessTempCarditMessagesWorker", "getHandoverInfos");
		return handoverInfos;
	}

	/**
	 * 	Method		:	ProcessTempCarditMessagesWorker.getRefInfoVOs
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param carditTmpMsgVO
	 *	Parameters	:	@return
	 *	Return type	: 	Collection<ReferenceInformationVO>
	 */
	private Collection<ReferenceInformationVO> getRefInfoVOs(CarditTempMsgVO carditTmpMsgVO) {
		log.entering("ProcessTempCarditMessagesWorker", "getRefInfoVOs");
		Collection<ReferenceInformationVO> refInfos = null;
		ReferenceInformationVO refInfo = null;
		ArrayList<Integer> sizelist = new ArrayList<Integer>();
		int maxSize=0;
		String[] cnsRefnum =null;
		String[] ctrRefQfr =null;

		if(carditTmpMsgVO.getConsignmentContractReferenceNumber()!=null && carditTmpMsgVO.getConsignmentContractReferenceNumber().trim().length()>0){
			if(carditTmpMsgVO.getConsignmentContractReferenceNumber().contains(";")){
				cnsRefnum = carditTmpMsgVO.getConsignmentContractReferenceNumber().split(";");
			}else{
				cnsRefnum =new String[1];
				cnsRefnum[0] = carditTmpMsgVO.getConsignmentContractReferenceNumber();
			}
			if(cnsRefnum!=null){
				sizelist.add(cnsRefnum.length);
			}
		}
		if(carditTmpMsgVO.getTransportContractReferenceQualifier()!=null && carditTmpMsgVO.getTransportContractReferenceQualifier().trim().length()>0){
			if(carditTmpMsgVO.getTransportContractReferenceQualifier().contains(";")){
				ctrRefQfr = carditTmpMsgVO.getTransportContractReferenceQualifier().split(";");
			}else{
				ctrRefQfr =new String[1];
				ctrRefQfr[0] = carditTmpMsgVO.getTransportContractReferenceQualifier();
			}
			if(ctrRefQfr!=null){
				sizelist.add(ctrRefQfr.length);
			}
		}
		if(sizelist!=null && !sizelist.isEmpty()){
			maxSize=Collections.max(sizelist);
			for(int i=0;i<maxSize;i++){
				refInfo = new ReferenceInformationVO();
				if(cnsRefnum!=null && i<cnsRefnum.length){
					refInfo.setConsignmentContractReferenceNumber(cnsRefnum[i]);
				}
				if(ctrRefQfr!=null && i<ctrRefQfr.length){
					refInfo.setTransportContractReferenceQualifier(ctrRefQfr[i]);
				}
				if(refInfos==null){
					refInfos = new ArrayList<ReferenceInformationVO>();
					refInfos.add(refInfo);
				}else{
					refInfos.add(refInfo);
				}
			}
		}
		log.exiting("ProcessTempCarditMessagesWorker", "getRefInfoVOs");
		return refInfos;
	}

	/**
	 * 	Method		:	ProcessTempCarditMessagesWorker.getTotalsInfoVO
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param carditTmpMsgVO
	 *	Parameters	:	@return
	 *	Return type	: 	Collection<TotalsInformationMessageVO>
	 */
	private Collection<TotalsInformationMessageVO> getTotalsInfoVO(CarditTempMsgVO carditTmpMsgVO) {
		log.entering("ProcessTempCarditMessagesWorker", "getTotalsInfoVO");
		Collection<TotalsInformationMessageVO> totalsInfos =null;
		TotalsInformationMessageVO totalsInfo =null;
		ArrayList<Integer> sizelist = new ArrayList<Integer>();
		int maxSize=0;
		String[] malClscod =null;
		String[] cntVal =null;
		String[] totPcsQfr =null;
		String[] numRcps =null;
		String[] msrUntQfr =null;
		String[] wgtCtrQfr =null;
		String[] wgtRcps =null;
		String[] wgtMsrQfr =null;

		if(carditTmpMsgVO.getMailClassCode()!=null && carditTmpMsgVO.getMailClassCode().trim().length()>0){
			if(carditTmpMsgVO.getMailClassCode().contains(";")){
				malClscod = carditTmpMsgVO.getMailClassCode().split(";");
			}else{
				malClscod =new String[1];
				malClscod[0] = carditTmpMsgVO.getMailClassCode();
			}
			if(malClscod!=null){
				sizelist.add(malClscod.length);
			}
		}
		if(carditTmpMsgVO.getControlValue()!=null && carditTmpMsgVO.getControlValue().trim().length()>0){
			if(carditTmpMsgVO.getControlValue().contains(";")){
				cntVal = carditTmpMsgVO.getControlValue().split(";");
			}else{
				cntVal =new String[1];
				cntVal[0] = carditTmpMsgVO.getControlValue();
			}
			if(cntVal!=null){
				sizelist.add(cntVal.length);
			}
		}
		if(carditTmpMsgVO.getTotalPiecesControlQualifier()!=null && carditTmpMsgVO.getTotalPiecesControlQualifier().trim().length()>0){
			if(carditTmpMsgVO.getTotalPiecesControlQualifier().contains(";")){
				totPcsQfr = carditTmpMsgVO.getTotalPiecesControlQualifier().split(";");
			}else{
				totPcsQfr =new String[1];
				totPcsQfr[0] = carditTmpMsgVO.getTotalPiecesControlQualifier();
			}
			if(totPcsQfr!=null){
				sizelist.add(totPcsQfr.length);
			}
		}
		if(carditTmpMsgVO.getNumberOfReceptacles()!=null && carditTmpMsgVO.getNumberOfReceptacles().trim().length()>0){
			if(carditTmpMsgVO.getNumberOfReceptacles().contains(";")){
				numRcps = carditTmpMsgVO.getNumberOfReceptacles().split(";");
			}else{
				numRcps =new String[1];
				numRcps[0] = carditTmpMsgVO.getNumberOfReceptacles();
			}
			if(numRcps!=null){
				sizelist.add(numRcps.length);
			}
		}
		if(carditTmpMsgVO.getTotalPiecesMeasureUnitQualifier()!=null && carditTmpMsgVO.getTotalPiecesMeasureUnitQualifier().trim().length()>0){
			if(carditTmpMsgVO.getTotalPiecesMeasureUnitQualifier().contains(";")){
				msrUntQfr = carditTmpMsgVO.getTotalPiecesMeasureUnitQualifier().split(";");
			}else{
				msrUntQfr =new String[1];
				msrUntQfr[0] = carditTmpMsgVO.getTotalPiecesMeasureUnitQualifier();
			}
			if(msrUntQfr!=null){
				sizelist.add(msrUntQfr.length);
			}
		}
		if(carditTmpMsgVO.getTotalWeightControlQualifier()!=null && carditTmpMsgVO.getTotalWeightControlQualifier().trim().length()>0){
			if(carditTmpMsgVO.getTotalWeightControlQualifier().contains(";")){
				wgtCtrQfr = carditTmpMsgVO.getTotalWeightControlQualifier().split(";");
			}else{
				wgtCtrQfr =new String[1];
				wgtCtrQfr[0] = carditTmpMsgVO.getTotalWeightControlQualifier();
			}
			if(wgtCtrQfr!=null){
				sizelist.add(wgtCtrQfr.length);
			}
		}
		if(carditTmpMsgVO.getWeightOfReceptacles()!=null && carditTmpMsgVO.getWeightOfReceptacles().trim().length()>0){
			if(carditTmpMsgVO.getWeightOfReceptacles().contains(";")){
				wgtRcps = carditTmpMsgVO.getWeightOfReceptacles().split(";");
			}else{
				wgtRcps =new String[1];
				wgtRcps[0] = carditTmpMsgVO.getWeightOfReceptacles();
			}
			if(wgtRcps!=null){
				sizelist.add(wgtRcps.length);
			}
		}
		if(carditTmpMsgVO.getTotalWeightMeasureUnitQualifier()!=null && carditTmpMsgVO.getTotalWeightMeasureUnitQualifier().trim().length()>0){
			if(carditTmpMsgVO.getTotalWeightMeasureUnitQualifier().contains(";")){
				wgtMsrQfr = carditTmpMsgVO.getTotalWeightMeasureUnitQualifier().split(";");
			}else{
				wgtMsrQfr =new String[1];
				wgtMsrQfr[0] = carditTmpMsgVO.getTotalWeightMeasureUnitQualifier();
			}
			if(wgtMsrQfr!=null){
				sizelist.add(wgtMsrQfr.length);
			}
		}

		if(sizelist!=null && !sizelist.isEmpty()){
			maxSize= Collections.max(sizelist);
			for(int i=0;i<maxSize;i++){
				totalsInfo = new TotalsInformationMessageVO();
				if(malClscod!=null && i<malClscod.length){
					totalsInfo.setMailClassCode(malClscod[i]);
				}
				if(cntVal!=null && i<cntVal.length){
					totalsInfo.setControlValue(cntVal[i]);
				}
				if(totPcsQfr!=null && i<totPcsQfr.length){
					totalsInfo.setTotalPiecesControlQualifier(totPcsQfr[i]);
				}
				if(numRcps!=null && i<numRcps.length){
					totalsInfo.setNumberOfReceptacles(numRcps[i]);
				}
				if(msrUntQfr!=null && i<msrUntQfr.length){
					totalsInfo.setTotalPiecesMeasureUnitQualifier(msrUntQfr[i]);
				}
				if(wgtCtrQfr!=null && i<wgtCtrQfr.length){
					totalsInfo.setTotalWeightControlQualifier(wgtCtrQfr[i]);
				}
				if(wgtRcps!=null && i<wgtRcps.length){
					totalsInfo.setWeightOfReceptacles(wgtRcps[i]);
				}
				if(wgtMsrQfr!=null && i<wgtMsrQfr.length){
					totalsInfo.setTotalWeightMeasureUnitQualifier(wgtMsrQfr[i]);
				}
				if(totalsInfos ==null){
					totalsInfos = new ArrayList<TotalsInformationMessageVO>();
					totalsInfos.add(totalsInfo);
				}else{
					totalsInfos.add(totalsInfo);
				}
			}
		}
		log.exiting("ProcessTempCarditMessagesWorker", "getTotalsInfoVO");
		return totalsInfos;
	}

	/**
	 * 	Method		:	ProcessTempCarditMessagesWorker.getReceptacleVO
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param carditTmpMsgVO
	 *	Parameters	:	@return
	 *	Return type	: 	ReceptacleInformationMessageVO
	 */
	private Collection<ReceptacleInformationMessageVO> getReceptacleVO(CarditTempMsgVO carditTmpMsgVO) {
		log.entering("ProcessTempCarditMessagesWorker", "getReceptacleVO");
		Collection<ReceptacleInformationMessageVO>  rcpInfoMsgVOs = null;
		ReceptacleInformationMessageVO receptacleVO = null;
		ReceptacleMessageVO rcpMsgVO =null;
		if(carditTmpMsgVO.getMailbagVOs() ==null ||carditTmpMsgVO.getMailbagVOs().isEmpty()){
			return new ArrayList<>();
		}

		for(ReceptacleInformationMessageVO rcpInfVO : carditTmpMsgVO.getMailbagVOs()){
			receptacleVO = new ReceptacleInformationMessageVO();
			receptacleVO.setNumberOfPackages(rcpInfVO.getNumberOfPackages());
			receptacleVO.setReceptacleType(rcpInfVO.getReceptacleType());
			receptacleVO.setPkgType(rcpInfVO.getPkgType());
			receptacleVO.setDRTagNo(rcpInfVO.getDRTagNo());
			receptacleVO.setReferenceQualifier(rcpInfVO.getReferenceQualifier());
			receptacleVO.setReceptacleID(rcpInfVO.getReceptacleID());
			receptacleVO.setDangerousGoodsIndicator(rcpInfVO.getDangerousGoodsIndicator());
			receptacleVO.setInsuranceIndicator(rcpInfVO.getInsuranceIndicator());
			receptacleVO.setHandlingClass(rcpInfVO.getHandlingClass());
			receptacleVO.setCodeListResponsibleAgency(rcpInfVO.getCodeListResponsibleAgency());
			receptacleVO.setMeasurementApplicationQualifier(rcpInfVO.getMeasurementApplicationQualifier());
			receptacleVO.setReceptacleWeightType(rcpInfVO.getReceptacleWeightType());
			receptacleVO.setMeasureUnitQualifier(rcpInfVO.getMeasureUnitQualifier());
			receptacleVO.setDocumentOrMessageNameCode(rcpInfVO.getDocumentOrMessageNameCode());
			receptacleVO.setDespatchIdentification(rcpInfVO.getDespatchIdentification());
			receptacleVO.setSealNumber(rcpInfVO.getSealNumber());
			rcpMsgVO = new ReceptacleMessageVO();
			if(rcpInfVO.getReceptacleVO()!=null){
				rcpMsgVO.setOriginExchangeOffice(rcpInfVO.getReceptacleVO().getOriginExchangeOffice());
				rcpMsgVO.setDestinationExchangeOffice(rcpInfVO.getReceptacleVO().getDestinationExchangeOffice());
				rcpMsgVO.setMailCategoryCode(rcpInfVO.getReceptacleVO().getMailCategoryCode());
				rcpMsgVO.setMailSubClassCode(rcpInfVO.getReceptacleVO().getMailSubClassCode());
				rcpMsgVO.setLastDigitOfYear(rcpInfVO.getReceptacleVO().getLastDigitOfYear());
				rcpMsgVO.setDespatchNumber(rcpInfVO.getReceptacleVO().getDespatchNumber());
				rcpMsgVO.setReceptacleSerialNumber(rcpInfVO.getReceptacleVO().getReceptacleSerialNumber());
				rcpMsgVO.setHighestNumberReceptacleIndicator(rcpInfVO.getReceptacleVO().getHighestNumberReceptacleIndicator());
				rcpMsgVO.setRegdOrInsuredIndicator(rcpInfVO.getReceptacleVO().getRegdOrInsuredIndicator());
				rcpMsgVO.setReceptacleWeight(rcpInfVO.getReceptacleVO().getReceptacleWeight());
				receptacleVO.setReceptacleVO(rcpMsgVO);
			}
			if(rcpInfoMsgVOs==null){
				rcpInfoMsgVOs = new ArrayList<ReceptacleInformationMessageVO>();
				rcpInfoMsgVOs.add(receptacleVO);
			}else{
				rcpInfoMsgVOs.add(receptacleVO);
			}

		}
		log.exiting("ProcessTempCarditMessagesWorker", "getReceptacleVO");
		return rcpInfoMsgVOs;
	}

	/**
	 * 	Method		:	ProcessTempCarditMessagesWorker.getDate
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param reqDeliveryTime
	 *	Parameters	:	@return
	 *	Return type	: 	LocalDate
	 */
	private LocalDate getDate(String dateStr) {
		LocalDate date= null;
		LocalDate dateOfJourney = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		if(dateStr!=null){
			Date doj = null;
			Calendar calenderDate = null;
			try {
				calenderDate = TimeConvertor.toCalendar(dateStr, "dd-MMM-yy HH:mm:ss");
				doj = new SimpleDateFormat("dd-MMM-yy").parse(dateStr);
			} catch (ParseException e) {
				log.log(Log.SEVERE, "PreparationDate is null");
			}
			String dtOfJrny = new SimpleDateFormat("dd-MMM-yyyy").format(doj);
			dateOfJourney.setDate(dtOfJrny);
			date = new LocalDate(LocalDate.NO_STATION, Location.NONE, calenderDate,true);
		}
		return date;
	}

	/**
	 * 	Method		:	ProcessTempCarditMessagesWorker.getTempCarditMessages
	 *	Added by 	:	A-6287 on 01-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return
	 *	Return type	: 	CarditTempMsgVO
	 */
	private Collection<CarditTempMsgVO> getTempCarditMessages(LogonAttributes logonAttributes, CarditProcessJobScheduleVO carditProcessJobScheduleVO) {
		log.entering("ProcessTempCarditMessagesWorker", "getTempCarditMessages");
		return despatchRequest("getTempCarditMessages",logonAttributes.getCompanyCode(),
				carditProcessJobScheduleVO.getIncludeMailBoxIdr(),
				carditProcessJobScheduleVO.getExcludeMailBoxIdr(),
				carditProcessJobScheduleVO.getIncludedOrigins(),
				carditProcessJobScheduleVO.getExcludedOrigins(),
				carditProcessJobScheduleVO.getPageSize(),
				carditProcessJobScheduleVO.getNoOfDays());
	}

	/**
	 *
	 * 	Method		:	ProcessTempCarditMessagesWorker.getEDIInterchangeVO
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	EDIInterchangeVO
	 * @param ediMessageVO
	 */
	 	EDIInterchangeVO getEDIInterchangeVO(EDIMessageVO ediMessageVO)
			throws SystemException {
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		EDIInterchangeVO interchangeVO = new EDIInterchangeVO();
		ediMsgeVO = ediMessageVO;
		interchangeVO.setCompanyCode(ediMessageVO.getCompanyCode());
		interchangeVO.setInterchangeSyntaxId(ediMessageVO.getSyntaxID());
		interchangeVO.setInterchangeSyntaxVer(ediMessageVO.getSyntaxVersion());
		interchangeVO.setSenderId(ediMessageVO.getSenderID());
		interchangeVO.setRecipientId(ediMessageVO.getRecipientID());
		interchangeVO.setInterchangeCtrlReference(ediMessageVO.getInterchangeControlReference());
		interchangeVO.setInterchangeControlCnt(ediMessageVO.getInterchangeControlCount());
		interchangeVO.setPreparationDate(ediMessageVO.getPreparationDate());
		interchangeVO.setMessageSequence(ediMessageVO.getMessageSequence());
		interchangeVO.setStationCode(logonAttributes.getStationCode());
		String rcpOrg = null;
		String rcpDest = null;
		String rcpClass = null;
		Collection<CarditVO> cardits = new ArrayList<CarditVO>();
		Collection<CarditMessageVO> carditMessages = ediMessageVO.getCarditMessages();
		for (CarditMessageVO carditMessageVO : carditMessages) {
			boolean isvalid = validateCarditDomesticMailbagDetails(carditMessageVO);
			if (!carditMessageVO.isErrorCardit()) {
				CarditVO carditVO = new CarditVO();

				carditVO.setInterchangeSyntaxId(interchangeVO.getInterchangeSyntaxId());
				carditVO.setInterchangeSyntaxVer(interchangeVO.getInterchangeSyntaxVer());
				carditVO.setSenderId(interchangeVO.getSenderId());
				carditVO.setActualSenderId(interchangeVO.getSenderId());
				carditVO.setRecipientId(interchangeVO.getRecipientId());
				carditVO.setPreparationDate(interchangeVO.getPreparationDate());
				carditVO.setInterchangeCtrlReference(interchangeVO.getInterchangeCtrlReference());
				carditVO.setInterchangeControlCnt(interchangeVO.getInterchangeControlCnt());
				carditVO.setCompanyCode(interchangeVO.getCompanyCode());
				carditVO.setMsgSeqNum(interchangeVO.getMessageSequence());
				carditVO.setStationCode(interchangeVO.getStationCode());

				carditVO.setCarditReceivedDate(new LocalDate(logonAttributes.getStationCode(), Location.ARP, true));
				carditVO.setMessageRefNum(carditMessageVO.getMessageReferenceNumber());
				carditVO.setMessageTypeId(carditMessageVO.getMessageTypeIdentifier());
				carditVO.setMessageVersion(carditMessageVO.getMessageVersionNumber());
				carditVO.setMessageReleaseNum(carditMessageVO.getMessageReleaseNumber());
				carditVO.setControlAgency(carditMessageVO.getControllingAgency());
				carditVO.setConsignmentNumber(carditMessageVO.getConsignmentIdentifier());
				carditVO.setConsignmentDate(carditMessageVO.getConsignmentCompletionDate());
				carditVO.setMailCategoryCode(carditMessageVO.getMailCategoryCode());
				carditVO.setMessageSegmentNum(carditMessageVO.getNumberOfSegments());

				carditVO.setAssociationAssignedCode(carditMessageVO.getAssociationAssignedCode());
				carditVO.setCarditType(carditMessageVO.getMessageFunction());

				Collection<CarditTransportationVO> carditTransportInfo = new ArrayList<CarditTransportationVO>();
				Collection<TransportInformationMessageVO> transportInfo = carditMessageVO.getTransportInformation();
				int noOfLegs = 1;
				for (TransportInformationMessageVO transportMessageVO : transportInfo) {
					CarditTransportationVO carditTDT = new CarditTransportationVO();

					carditTDT.setTransportStageQualifier(transportMessageVO.getTransportStageQualifier());
					carditTDT.setModeOfTransport(transportMessageVO.getModeOfTransport());
					carditTDT.setCarrierCode(transportMessageVO.getCarrierID());
					carditTDT.setDeparturePort(transportMessageVO.getDeparturePlace());
					if (noOfLegs == 1) {
						rcpOrg = transportMessageVO.getDeparturePlace();
					}
					carditTDT.setDepartureCodeListAgency(transportMessageVO.getDepartureCodeListAgency());
					carditTDT.setArrivalPort(transportMessageVO.getArrivalPlace());
					if (transportInfo.size() == noOfLegs) {
						rcpDest = transportMessageVO.getArrivalPlace();
					}
					carditTDT.setArrivalCodeListAgency(transportMessageVO.getArrivalCodeListAgency());
					carditTDT.setDepartureTime(transportMessageVO.getDepartureDate());

					carditTDT.setArrivalPlaceName(transportMessageVO.getArrivalPlaceName());
					carditTDT.setDeparturePlaceName(transportMessageVO.getDeparturePlaceName());
					carditTDT.setArrivalDate(transportMessageVO.getArrivalDate());
					carditTDT.setTransportLegRate(transportMessageVO.getTransportLegRate());

					carditTDT.setContractReference(transportMessageVO.getContractReference());

					if (transportMessageVO.getConveyanceReference() != null
							|| transportMessageVO.getTransportIdentification() != null) {
						carditTDT.setConveyanceReference(transportMessageVO.getConveyanceReference());

						populateFlightValidationDtls(transportMessageVO, carditTDT);
					} else {

						if (CarditMessageVO.CARRCOD_IDENT.equals(transportMessageVO.getCodeListQualifier())
								&& CarditMessageVO.IATA_ID.equals(transportMessageVO.getAgencyForCarrierCode())) {
							AirlineValidationVO airlineVO = validateAirlineByAlphaCode(ediMessageVO.getCompanyCode(),
									transportMessageVO.getCarrierID());
							if (airlineVO != null) {
								carditTDT.setCarrierID(airlineVO.getAirlineIdentifier());
								carditTDT.setFlightNumber("-1");
							}
						}
					}

					noOfLegs++;
					carditTransportInfo.add(carditTDT);
				}
				carditVO.setTransportInformation(carditTransportInfo);

				Collection<CarditReceptacleVO> receptacleInfo = new ArrayList<CarditReceptacleVO>();
				Collection<ReceptacleInformationMessageVO> receptacleMessages = carditMessageVO
						.getReceptacleInformation();
				LocalDate reqDeliveryDate = null;
				if (receptacleMessages != null && receptacleMessages.size() > 0) {
					for (ReceptacleInformationMessageVO receptacleMessageVO : receptacleMessages) {
						CarditReceptacleVO carditReceptacleVO = new CarditReceptacleVO();
						carditReceptacleVO.setCarditType(carditVO.getCarditType());
						carditReceptacleVO.setReceptacleId(receptacleMessageVO.getReceptacleID());
						carditReceptacleVO.setReceptacleType(receptacleMessageVO.getReceptacleType());
						carditReceptacleVO.setDangerousGoodsIndicator(receptacleMessageVO.getDangerousGoodsIndicator());
						carditReceptacleVO.setHandlingClass(receptacleMessageVO.getHandlingClass());
						carditReceptacleVO.setReferenceQualifier(receptacleMessageVO.getReferenceQualifier());
						carditReceptacleVO
								.setCodeListResponsibleAgency(receptacleMessageVO.getCodeListResponsibleAgency());
						carditReceptacleVO.setMeasurementApplicationQualifier(
								receptacleMessageVO.getMeasurementApplicationQualifier());
						carditReceptacleVO.setReceptacleWeightType(receptacleMessageVO.getReceptacleWeightType());
						carditReceptacleVO.setMeasureUnitQualifier(receptacleMessageVO.getMeasureUnitQualifier());
						carditReceptacleVO
								.setDocumentOrMessageNameCode(receptacleMessageVO.getDocumentOrMessageNameCode());
						carditReceptacleVO.setDespatchIdentification(receptacleMessageVO.getDespatchIdentification());

						ReceptacleMessageVO receptacleVO = receptacleMessageVO.getReceptacleVO();
						if (receptacleVO != null) {
							if (receptacleMessageVO.getDRTagNo() != null) {

								String mailbagId = receptacleMessageVO.getDRTagNo();
								if (Double.toString(receptacleMessageVO.getReceptacleVO().getReceptacleWeight()).trim()
										.indexOf(".") == 0) {
									mailbagId = mailbagId.concat("00");
								} else if (Double.toString(receptacleMessageVO.getReceptacleVO().getReceptacleWeight())
										.trim().indexOf(".") == 1) {
									mailbagId = mailbagId.concat("0").concat(
											Double.toString(receptacleMessageVO.getReceptacleVO().getReceptacleWeight())
													.trim().substring(0, 1));
								} else if (Double.toString(receptacleMessageVO.getReceptacleVO().getReceptacleWeight())
										.trim().indexOf(".") == 2) {
									if(receptacleMessageVO.getReceptacleVO().getReceptacleWeight()<0){
										mailbagId=mailbagId.concat("0").concat(Double.toString(receptacleMessageVO.getReceptacleVO().getReceptacleWeight()).trim().substring(1,2));
									}else {
										mailbagId = mailbagId.concat(Double.toString(receptacleMessageVO.getReceptacleVO().getReceptacleWeight()).trim().substring(0, 2));
									}
								} else if (receptacleMessageVO.getReceptacleVO().getReceptacleWeight()<0 &&Double.toString(receptacleMessageVO.getReceptacleVO().getReceptacleWeight())
										.trim().indexOf(".") >= 3) {
									mailbagId = mailbagId.concat(
											Double.toString(receptacleMessageVO.getReceptacleVO().getReceptacleWeight())
													.trim().substring(1, 3));
								} else {
									mailbagId = mailbagId.concat("99");
								}
								if (!isValidMailtag(mailbagId != null ? mailbagId.trim().length() : 0)) {
									Collection<String> errorCodes = new ArrayList<String>();
									errorCodes.add(INVALID_MAILTAG);
									interchangeVO.setErrorCodes(errorCodes);
									continue;
								//	ediMessageVO.getMessageVO().setStatus(MessageVO.STATUS_PROCESSED_WITH_WARNING);

								}
								if (receptacleMessageVO.getDRTagNo().length() > 10) {

									String mailBagId = receptacleMessageVO.getDRTagNo().substring(0, 10);
									if (Double.toString(receptacleMessageVO.getReceptacleVO().getReceptacleWeight())
											.trim().indexOf(".") == 0) {
										mailBagId = mailBagId.concat("00");
									} else if (Double
											.toString(receptacleMessageVO.getReceptacleVO().getReceptacleWeight())
											.trim().indexOf(".") == 1) {
										mailBagId = mailBagId.concat("0").concat(Double
												.toString(receptacleMessageVO.getReceptacleVO().getReceptacleWeight())
												.trim().substring(0, 1));
									} else if (Double
											.toString(receptacleMessageVO.getReceptacleVO().getReceptacleWeight())
											.trim().indexOf(".") == 2) {
										if(receptacleMessageVO.getReceptacleVO().getReceptacleWeight()<0){
											mailbagId=mailbagId.concat("0").concat(Double.toString(receptacleMessageVO.getReceptacleVO().getReceptacleWeight()).trim().substring(1,2));
										}else {
											mailbagId = mailbagId.concat(Double.toString(receptacleMessageVO.getReceptacleVO().getReceptacleWeight()).trim().substring(0, 2));
										}
									} else if (receptacleMessageVO.getReceptacleVO().getReceptacleWeight()<0 &&Double
											.toString(receptacleMessageVO.getReceptacleVO().getReceptacleWeight())
											.trim().indexOf(".") >= 3) {
										mailBagId = mailBagId.concat(Double
												.toString(receptacleMessageVO.getReceptacleVO().getReceptacleWeight())
												.trim().substring(1, 3));
									} else {
										mailBagId = mailBagId.concat("99");
									}

									carditReceptacleVO.setMailBagId(mailbagId);



									rcpClass = carditReceptacleVO.getMailBagId().substring(3, 4);
									carditReceptacleVO.setHandlingClass(CarditMessageVO.DOM_HNDCLS);
								} else {
									carditReceptacleVO.setSealNumber(receptacleMessageVO.getSealNumber());
									carditReceptacleVO.setMailBagId(mailbagId);
									carditReceptacleVO.setMailCategoryCode(CarditMessageVO.DOM_MALCTGCOD);
									carditReceptacleVO.setHandlingClass(CarditMessageVO.DOM_HNDCLS);
									carditVO.setMailCategoryCode(CarditMessageVO.DOM_MALCTGCOD);
									rcpClass = receptacleMessageVO.getDRTagNo().substring(3, 4);
								}
								if (receptacleMessageVO.getReceptacleID() == null) {
									carditReceptacleVO.setReceptacleId(mailbagId);
								}
								populatePCIDetailsforUSPS(receptacleVO, rcpOrg, rcpDest, rcpClass);
							}

							carditReceptacleVO.setOriginExchangeOffice(receptacleVO.getOriginExchangeOffice());
							carditReceptacleVO
									.setDestinationExchangeOffice(receptacleVO.getDestinationExchangeOffice());
							carditReceptacleVO.setMailCategoryCode(receptacleVO.getMailCategoryCode());
							carditReceptacleVO.setMailSubClassCode(receptacleVO.getMailSubClassCode());
							carditReceptacleVO.setLastDigitOfYear(receptacleVO.getLastDigitOfYear());
							carditReceptacleVO.setDespatchNumber(receptacleVO.getDespatchNumber());
							carditReceptacleVO.setReceptacleSerialNumber(receptacleVO.getReceptacleSerialNumber());
							carditReceptacleVO.setRegdOrInsuredIndicator(receptacleVO.getRegdOrInsuredIndicator());
							carditReceptacleVO.setHighestNumberReceptacleIndicator(
									receptacleVO.getHighestNumberReceptacleIndicator());
							String displayUnit = "";
							if (MailConstantsVO.MEASUREUNITQUALIFIER_POUND
									.equals(receptacleMessageVO.getMeasureUnitQualifier())) {
								displayUnit = MailConstantsVO.WEIGHTCODE_POUND;

							} else if (MailConstantsVO.MEASUREUNITQUALIFIER_KILO
									.equals(receptacleMessageVO.getMeasureUnitQualifier())) {
								displayUnit = MailConstantsVO.WEIGHTCODE_KILO;
							}
							carditReceptacleVO.setReceptacleWeight(new Measure(UnitConstants.MAIL_WGT, 0.0,
									receptacleVO.getReceptacleWeight(), displayUnit));

							carditReceptacleVO.setReqDeliveryTime(carditMessageVO.getConsignmentCompletionDate());

							carditReceptacleVO.setMailOrigin(receptacleVO.getOrigin());
							carditReceptacleVO.setMailDestination(receptacleVO.getDestination());


							String paCode=null;
					        try {
							MailBoxId mailBoxId = MailBoxId.find(ediMessageVO.getCompanyCode(), interchangeVO.getSenderId());
								if("ACTIVE".equals(mailBoxId.getMailboxStatus()) &&  "PA".equals(mailBoxId.getMailboxOwner())){
									paCode=mailBoxId.getOwnerCode();
					        	}
					        }catch(FinderException finderException){
					                log.log(Log.FINE, "Exception finding Mailbox ID as PA");
							}
					        if (paCode == null) {
					    	if(interchangeVO.getSenderId()!=null){
					    		paCode = findPAForMailboxID(ediMessageVO.getCompanyCode(), interchangeVO.getSenderId(), receptacleVO.getOriginExchangeOffice());
					    	}
					    	else{
								paCode = findPAForOfficeOfExchange(ediMessageVO.getCompanyCode(), receptacleVO.getOriginExchangeOffice());
					    	}
					     }

							if (carditMessageVO.getConsignmentCompletionDate() != null) {
								rDTCalculationWhenConsignmentCompletionDateNotNull(rcpOrg, rcpDest, rcpClass, carditMessageVO,
										carditVO, carditReceptacleVO, paCode);
									


							} else {
								carditReceptacleVO.setReqDeliveryTime(null);
							}

							receptacleInfo.add(carditReceptacleVO);
						}

					}

				}

				boolean duplicate = false;
				MessageDetailsVO duplicateData = null;
				if (receptacleInfo != null && receptacleInfo.size() > 0) {
					HashMap<String, String> mailbagsDuplicate = new HashMap<String, String>();

					Iterator<CarditReceptacleVO> receptaclesVO = receptacleInfo.iterator();
					while (receptaclesVO.hasNext()) {
						CarditReceptacleVO receptacle = receptaclesVO.next();
						if (mailbagsDuplicate.containsKey(receptacle.getMailBagId())) {
							duplicateData = carditMessageVO.getMailbagMessageMap().get(receptacle.getMailBagId());
							duplicate = true;
							receptaclesVO.remove();
						} else {
							mailbagsDuplicate.put(receptacle.getMailBagId(), receptacle.getMailBagId());
						}
					}
				}

				carditVO.setReceptacleInformation(receptacleInfo);

				Collection<ContainerInformationMessageVO> containerMessageInfo = carditMessageVO
						.getContainerInformation();
				if (containerMessageInfo != null && containerMessageInfo.size() > 0) {
					Collection<CarditContainerVO> carditContainerInfo = new ArrayList<CarditContainerVO>();
					for (ContainerInformationMessageVO containerMessageVO : containerMessageInfo) {
						CarditContainerVO carditContainerVO = new CarditContainerVO();
						carditContainerVO.setCarditType(carditVO.getCarditType());
						carditContainerVO.setEquipmentQualifier(containerMessageVO.getEquipmentQualifier());
						carditContainerVO.setContainerNumber(containerMessageVO.getContainerNumber());
						carditContainerVO
								.setCodeListResponsibleAgency(containerMessageVO.getCodeListResponsibleAgency());
						carditContainerVO.setContainerType(containerMessageVO.getContainerType());
						carditContainerVO.setTypeCodeListResponsibleAgency(
								containerMessageVO.getTypeCodeListResponsibleAgency());
						carditContainerVO.setMeasurementDimension(containerMessageVO.getMeasurementDimension());
						carditContainerVO.setContainerWeight(new Measure(UnitConstants.MAIL_WGT,
								Double.parseDouble(containerMessageVO.getContainerWeight()!=null?containerMessageVO.getContainerWeight():"0")));
						carditContainerVO.setSealNumber(containerMessageVO.getSealNumber());

						carditContainerVO
								.setContainerJourneyIdentifier(containerMessageVO.getContainerJourneyIdentifier());

						if (MailConstantsVO.CDT_BLY_CART_ID.equals(carditContainerVO.getEquipmentQualifier())
								&& (carditContainerVO.getContainerNumber() == null
										|| carditContainerVO.getContainerNumber().length() == 0)) {
							String containerNumber;
							try {
								containerNumber = getBellyCartId(carditVO.getCompanyCode(),
										carditVO.getConsignmentNumber(), MailConstantsVO.CDT_BLY_CART_ID);
								carditContainerVO.setContainerNumber(containerNumber);
							} catch (GenerationFailedException e) {

								log.log(Log.WARNING, "BELLY CART ID GENERATION FAILED");
							}
						}

						log.log(Log.FINE, "carditContainerVO-----" + carditContainerVO);
						carditContainerInfo.add(carditContainerVO);
					}
					carditVO.setContainerInformation(carditContainerInfo);
				}

				Collection<HandoverInformationVO> handoverMessageInfo = carditMessageVO.getHandoverInformation();
				if (handoverMessageInfo != null && handoverMessageInfo.size() > 0) {
					Collection<CarditHandoverInformationVO> carditHandoverInfo = new ArrayList<CarditHandoverInformationVO>();
					for (HandoverInformationVO handoverInformationVO : handoverMessageInfo) {
						CarditHandoverInformationVO carditHandoverInfoVO = new CarditHandoverInformationVO();

						carditHandoverInfoVO.setCarditKey(carditVO.getConsignmentNumber());

						carditHandoverInfoVO
								.setDateTimeFormatQualifier(handoverInformationVO.getDateTimeFormatQualifier());
						carditHandoverInfoVO
								.setDateTimePeriodQualifier(handoverInformationVO.getDateTimePeriodQualifier());

						carditHandoverInfoVO
								.setDestinationCutOffPeriod(handoverInformationVO.getDestinationCutOffPeriod());
						carditHandoverInfoVO.setOriginCutOffPeriod(handoverInformationVO.getOriginCutOffPeriod());

						carditHandoverInfoVO
								.setHandoverDestnCodeListAgency(handoverInformationVO.getHandoverDestnCodeListAgency());
						carditHandoverInfoVO.setHandoverDestnCodeListQualifier(
								handoverInformationVO.getHandoverDestnCodeListQualifier());
						carditHandoverInfoVO.setHandoverDestnLocationIdentifier(
								handoverInformationVO.getHandoverDestnLocationIdentifier());
						carditHandoverInfoVO
								.setHandoverDestnLocationName(handoverInformationVO.getHandoverDestnLocationName());
						carditHandoverInfoVO.setHandoverDestnLocationQualifier(
								handoverInformationVO.getHandoverDestnLocationQualifier());

						carditHandoverInfoVO.setHandoverOriginCodeListAgency(
								handoverInformationVO.getHandoverOriginCodeListAgency());
						carditHandoverInfoVO.setHandoverOriginCodeListQualifier(
								handoverInformationVO.getHandoverOriginCodeListQualifier());
						carditHandoverInfoVO.setHandoverOriginLocationIdentifier(
								handoverInformationVO.getHandoverOriginLocationIdentifier());
						carditHandoverInfoVO
								.setHandoverOriginLocationName(handoverInformationVO.getHandoverOriginLocationName());
						carditHandoverInfoVO.setHandoverOriginLocationQualifier(
								handoverInformationVO.getHandoverOriginLocationQualifier());

						carditHandoverInfoVO
								.setTransportStageQualifier(handoverInformationVO.getTransportStageQualifier());

						carditHandoverInfoVO.setCarditType(carditVO.getCarditType());

						carditHandoverInfo.add(carditHandoverInfoVO);
					}
					carditVO.setHandoverInformation(carditHandoverInfo);
				}

				Collection<ReferenceInformationVO> referenceMessageInfo = carditMessageVO.getReferenceInformation();
				if (referenceMessageInfo != null && referenceMessageInfo.size() > 0) {
					Collection<CarditReferenceInformationVO> carditReferenceInfo = new ArrayList<CarditReferenceInformationVO>();
					for (ReferenceInformationVO referenceInformationVO : referenceMessageInfo) {
						CarditReferenceInformationVO carditReferenceInfoVO = new CarditReferenceInformationVO();

						carditReferenceInfoVO.setConsignmentContractReferenceNumber(
								referenceInformationVO.getConsignmentContractReferenceNumber());
						carditReferenceInfoVO.setTransportContractReferenceQualifier(
								referenceInformationVO.getTransportContractReferenceQualifier());

						carditReferenceInfoVO.setCarditType(carditVO.getCarditType());

						carditReferenceInfo.add(carditReferenceInfoVO);
					}
					carditVO.setReferenceInformation(carditReferenceInfo);
				}

				Collection<CarditTotalVO> carditTotals = new ArrayList<CarditTotalVO>();
				Collection<TotalsInformationMessageVO> totalsMessages = carditMessageVO.getTotalsInformation();
				for (TotalsInformationMessageVO totalMessageVO : totalsMessages) {
					CarditTotalVO carditTotalVO = new CarditTotalVO();
					carditTotalVO.setMailClassCode(totalMessageVO.getMailClassCode());
					carditTotalVO.setNumberOfReceptacles(totalMessageVO.getNumberOfReceptacles());
					if (totalMessageVO.getWeightOfReceptacles() != null) {
						carditTotalVO.setWeightOfReceptacles(new Measure(UnitConstants.MAIL_WGT,
								Double.parseDouble(totalMessageVO.getWeightOfReceptacles())));
					}
					carditTotals.add(carditTotalVO);
				}
				carditVO.setTotalsInformation(carditTotals);

				cardits.add(carditVO);
			}
		}
		interchangeVO.setCarditMessages(cardits);

		return interchangeVO;
	}

	/**
	 *
	 * 	Method		:	ProcessTempCarditMessagesWorker.convertMailoperationVOs
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param ediInterchangeVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	com.ibsplc.icargo.business.mail.operations.vo.EDIInterchangeVO
	 */
	private com.ibsplc.icargo.business.mail.operations.vo.EDIInterchangeVO convertMailoperationVOs( EDIInterchangeVO ediInterchangeVO) throws SystemException {
   	 com.ibsplc.icargo.business.mail.operations.vo.EDIInterchangeVO newediInterchangeVO = new com.ibsplc.icargo.business.mail.operations.vo.EDIInterchangeVO();
    	Collection<com.ibsplc.icargo.business.mail.operations.vo.CarditVO> carditMessages=new ArrayList<com.ibsplc.icargo.business.mail.operations.vo.CarditVO>();
    	Collection<com.ibsplc.icargo.business.mail.operations.vo.CarditTransportationVO> carditTransportationVOs=new ArrayList<com.ibsplc.icargo.business.mail.operations.vo.CarditTransportationVO>();
    	Collection<com.ibsplc.icargo.business.mail.operations.vo.CarditContainerVO> carditContainerVOs=new ArrayList<com.ibsplc.icargo.business.mail.operations.vo.CarditContainerVO>();
    	Collection<com.ibsplc.icargo.business.mail.operations.vo.CarditHandoverInformationVO> carditHandoverInformationVOs=new ArrayList<com.ibsplc.icargo.business.mail.operations.vo.CarditHandoverInformationVO>();
    	Collection<com.ibsplc.icargo.business.mail.operations.vo.CarditReceptacleVO> carditReceptacleVOs=new ArrayList<com.ibsplc.icargo.business.mail.operations.vo.CarditReceptacleVO>();
    	Collection<com.ibsplc.icargo.business.mail.operations.vo.CarditReferenceInformationVO> carditReferenceInformationVOs=new ArrayList<com.ibsplc.icargo.business.mail.operations.vo.CarditReferenceInformationVO>();
    	Collection<com.ibsplc.icargo.business.mail.operations.vo.CarditTotalVO> carditTotalVOs=new ArrayList<com.ibsplc.icargo.business.mail.operations.vo.CarditTotalVO>();
    	for(CarditVO oldCarditVO:ediInterchangeVO.getCarditMessages()){
    		com.ibsplc.icargo.business.mail.operations.vo.CarditVO newCarditVO=new com.ibsplc.icargo.business.mail.operations.vo.CarditVO();
    		BeanHelper.copyProperties(newCarditVO, oldCarditVO);
    		if(oldCarditVO.getTransportInformation()!=null)
    		{
    		for(CarditTransportationVO oldCarditTransportationVO:oldCarditVO.getTransportInformation()){
    			com.ibsplc.icargo.business.mail.operations.vo.CarditTransportationVO newCarditTransportationVO=new com.ibsplc.icargo.business.mail.operations.vo.CarditTransportationVO();
        		BeanHelper.copyProperties(newCarditTransportationVO, oldCarditTransportationVO);
        		carditTransportationVOs.add(newCarditTransportationVO);
    		}
    		}
    		if(oldCarditVO.getContainerInformation()!=null)
    		{
    		for(CarditContainerVO oldCarditContainerVO:oldCarditVO.getContainerInformation()){
    			com.ibsplc.icargo.business.mail.operations.vo.CarditContainerVO newCarditContainerVO=new com.ibsplc.icargo.business.mail.operations.vo.CarditContainerVO();
        		BeanHelper.copyProperties(newCarditContainerVO, oldCarditContainerVO);
        		carditContainerVOs.add(newCarditContainerVO);
    		}
    		}
    		if(oldCarditVO.getHandoverInformation()!=null)
    		{
    		for(CarditHandoverInformationVO oldHandoverInformationVO:oldCarditVO.getHandoverInformation()){
    			com.ibsplc.icargo.business.mail.operations.vo.CarditHandoverInformationVO newCarditHandoverInformationVO=new com.ibsplc.icargo.business.mail.operations.vo.CarditHandoverInformationVO();
        		BeanHelper.copyProperties(newCarditHandoverInformationVO, oldHandoverInformationVO);
        		carditHandoverInformationVOs.add(newCarditHandoverInformationVO);
    		}
    		}
    		if(oldCarditVO.getReceptacleInformation()!=null)
    		{
    		for(CarditReceptacleVO oldCarditReceptacleVO:oldCarditVO.getReceptacleInformation()){
    			com.ibsplc.icargo.business.mail.operations.vo.CarditReceptacleVO newCarditReceptacleVO=new com.ibsplc.icargo.business.mail.operations.vo.CarditReceptacleVO();
        		BeanHelper.copyProperties(newCarditReceptacleVO, oldCarditReceptacleVO);
        		carditReceptacleVOs.add(newCarditReceptacleVO);
    		}
    		}
    		if(oldCarditVO.getReferenceInformation()!=null)
    		{
    		for(CarditReferenceInformationVO oldCarditReferenceInformationVO:oldCarditVO.getReferenceInformation()){
    			com.ibsplc.icargo.business.mail.operations.vo.CarditReferenceInformationVO newCarditReferenceInformationVO=new com.ibsplc.icargo.business.mail.operations.vo.CarditReferenceInformationVO();
        		BeanHelper.copyProperties(newCarditReferenceInformationVO, oldCarditReferenceInformationVO);
        		carditReferenceInformationVOs.add(newCarditReferenceInformationVO);
    		}
    		}
    		if(oldCarditVO.getTotalsInformation()!=null)
    		{
    		for(CarditTotalVO oldCarditTotalVO:oldCarditVO.getTotalsInformation()){
    			com.ibsplc.icargo.business.mail.operations.vo.CarditTotalVO newCarditTotalVO=new com.ibsplc.icargo.business.mail.operations.vo.CarditTotalVO();
        		BeanHelper.copyProperties(newCarditTotalVO, oldCarditTotalVO);
        		carditTotalVOs.add(newCarditTotalVO);
    			}
    		}
    		newCarditVO.setTransportInformation(carditTransportationVOs);
    		newCarditVO.setContainerInformation(carditContainerVOs);
    		newCarditVO.setHandoverInformation(carditHandoverInformationVOs);
    		newCarditVO.setReceptacleInformation(carditReceptacleVOs);
    		newCarditVO.setReferenceInformation(carditReferenceInformationVOs);
    		newCarditVO.setTotalsInformation(carditTotalVOs);
    		carditMessages.add(newCarditVO);
    	}

    	BeanHelper.copyProperties(newediInterchangeVO, ediInterchangeVO);
    	newediInterchangeVO.setCarditMessages(carditMessages);

		return newediInterchangeVO;
	}
	/**
	 *
	 * 	Method		:	DomesticCarditMessageProcessor.getBellyCartId
	 *	Added by 	:	A-6245 on 21-Jul-2017
	 * 	Used for 	:	Generate Belly Cart Id from sequence
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param carditkey
	 *	Parameters	:	@param cartId
	 *	Parameters	:	@return
	 *	Parameters	:	@throws GenerationFailedException
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	String
	 */
	private String getBellyCartId(String companyCode, String carditkey, String cartId)
			throws GenerationFailedException, SystemException {
		Criterion keyCriterion = KeyUtils.getCriterion(companyCode, carditkey, cartId);
		String bellyCartId = new StringBuilder(cartId).append(checkLength(KeyUtils.getKey(keyCriterion), 10))
				.toString();
		log.log(Log.FINEST, "****** bellyCartId Generated ---- " + bellyCartId);
		return bellyCartId;
	}
	/**
	 *
	 * 	Method		:	DomesticCarditMessageProcessor.checkLength
	 *	Added by 	:	A-6245 on 21-Jul-2017
	 * 	Used for 	:	This method will generate a key
	 * 					by appending "0" infront of, the first param
	 * 					and is restricted to a length, which is passed as the second param
	 *	Parameters	:	@param key
	 *	Parameters	:	@param maxLength
	 *	Parameters	:	@return
	 *	Return type	: 	String
	 */
	private String checkLength(String key, int maxLength) {
		String modifiedKey = null;
		StringBuilder buildKey = new StringBuilder();
		modifiedKey = new StringBuilder().append(key).toString();
		int keyLength = modifiedKey.length();
		if (modifiedKey.length() < maxLength) {
			int diff = maxLength - keyLength;
			String val = null;
			for (int i = 0; i < diff; i++) {
				val = buildKey.append("0").toString();
			}
			modifiedKey = new StringBuilder().append(val).append(key).toString();
		}
		return modifiedKey;
	}
	/**
	 *
	 * 	Method		:	ProcessTempCarditMessagesWorker.populatePCIDetailsforUSPS
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param receptacleVO
	 *	Parameters	:	@param rcpOrg
	 *	Parameters	:	@param rcpDest
	 *	Parameters	:	@param rcpClass
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
	private void populatePCIDetailsforUSPS(ReceptacleMessageVO receptacleVO,
			String rcpOrg,String rcpDest,String rcpClass) throws SystemException{
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			receptacleVO.setMailSubClassCode(rcpClass+CarditMessageVO.DOM_MALSUBCLS);
	    	String lastDigitOfYear = new LocalDate(logonAttributes.getStationCode(),
	    			Location.ARP,false).toDisplayFormat(CarditMessageVO.YEAR_FORMAT).substring(3,4);
	    	receptacleVO.setLastDigitOfYear(Integer.parseInt(lastDigitOfYear));
	    	if(exchangeOfficeMap!=null && !exchangeOfficeMap.isEmpty()){
	    		if(exchangeOfficeMap.containsKey(rcpOrg)&&
	    				exchangeOfficeMap.containsKey(rcpDest)){
		    	receptacleVO.setOriginExchangeOffice(exchangeOfficeMap.get(rcpOrg));
		    	receptacleVO.setDestinationExchangeOffice(exchangeOfficeMap.get(rcpDest));
			} else {
				receptacleVO.setOriginExchangeOffice(constructExchangeOfficeForUSPS(rcpOrg));
				receptacleVO.setDestinationExchangeOffice(constructExchangeOfficeForUSPS(rcpDest));
	    		}
		} else {
			receptacleVO.setOriginExchangeOffice(constructExchangeOfficeForUSPS(rcpOrg));
			receptacleVO.setDestinationExchangeOffice(constructExchangeOfficeForUSPS(rcpDest));
	    	}
	    	receptacleVO.setMailCategoryCode(CarditMessageVO.DOM_MALCTGCOD);


	    	/*receptacleVO.setDespatchNumber(generateDespatchSerialNumber(MailConstantsVO.FLAG_YES));
	    	if(receptacleVO.getReceptacleSerialNumber()==null||
	    			receptacleVO.getReceptacleSerialNumber().trim().isEmpty()){
	    	String rsn=generateReceptacleSerialNumber(receptacleVO.getDespatchNumber());
	    	if(rsn.length()>3){
	    		generateDespatchSerialNumber(MailConstantsVO.FLAG_NO);
	    		receptacleVO.setDespatchNumber(generateDespatchSerialNumber(MailConstantsVO.FLAG_YES));
	    		receptacleVO.setReceptacleSerialNumber(generateReceptacleSerialNumber(
	    			receptacleVO.getDespatchNumber()));
	    	}else{
	    		receptacleVO.setReceptacleSerialNumber(rsn);
	    	}
	    	}*/
	    	receptacleVO.setDespatchNumber(MailConstantsVO.DOM_MAILBAG_DEF_DSNVAL);
	    	receptacleVO.setReceptacleSerialNumber(MailConstantsVO.DOM_MAILBAG_DEF_RSNVAL);

	    	receptacleVO.setHighestNumberReceptacleIndicator(CarditMessageVO.DOM_HNI);
	    	receptacleVO.setRegdOrInsuredIndicator(CarditMessageVO.DOM_RI);
	    	//Added for ICRD-255189
	    	receptacleVO.setOrigin(rcpOrg);
	    	receptacleVO.setDestination(rcpDest);
	}
	/**
	 *
	 * 	Method		:	ProcessTempCarditMessagesWorker.populateFlightValidationDtls
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param transportInformationVO
	 *	Parameters	:	@param carditTDT
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
	private void populateFlightValidationDtls(
            TransportInformationMessageVO transportInformationVO,
            CarditTransportationVO carditTDT)
    throws SystemException {

            FlightFilterVO flightFilterVO = new FlightFilterVO();
            flightFilterVO.setCompanyCode(ediMsgeVO.getCompanyCode());
            String carrierCode=null;
            String flightNumber=null;
				 if(transportInformationVO.getTransportIdentification()!=null&&
							!transportInformationVO.getTransportIdentification().trim().isEmpty()){
					 flightNumber=transportInformationVO.getTransportIdentification();
			            if(flightNumber!= null && flightNumber.trim().length() > 0 &&
			            		flightNumber.length()< FLTNUM_LEN) {
			            	flightNumber = formatFlightNumber(flightNumber);
			            }else if (flightNumber == null|| flightNumber.trim().length() == 0){
			            	flightNumber = "-1";
			            }

					}
				 if(transportInformationVO.getCarrierID()!=null&&
						 !transportInformationVO.getCarrierID().trim().isEmpty()){
					 carrierCode=transportInformationVO.getCarrierID();
				 }
			//Added as part of ICRD-212903 ends
            carditTDT.setCarrierCode(carrierCode);
            carditTDT.setCarrierName(transportInformationVO.getCarrierName());
        	carditTDT.setFlightNumber(flightNumber);

            AirlineValidationVO airlineVO =
            	validateAirlineByAlphaCode(ediMsgeVO.getCompanyCode(),
            			carrierCode);
            if(airlineVO != null) {
            	flightFilterVO.setFlightCarrierId(airlineVO.getAirlineIdentifier());
                //Added by A-7540 to set the carrier id if the flight does not exist in the system
            	  carditTDT.setCarrierID(airlineVO.getAirlineIdentifier());
            }

            flightFilterVO.setFlightNumber(flightNumber);
            if(transportInformationVO.getDeparturePlace() != null) {
                flightFilterVO.setSegmentOrigin(
                		transportInformationVO.getDeparturePlace());
                flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
                flightFilterVO.setStation(
                		transportInformationVO.getDeparturePlace());
            }
            flightFilterVO.setSegmentDestination(transportInformationVO.getArrivalPlace());
            flightFilterVO.setFlightDate(transportInformationVO.getDepartureDate());

            log.log(Log.FINEST, "flightFilterVO " + flightFilterVO);

            FlightValidationVO flightValidationVO = validateFlight(flightFilterVO);
            log.log(Log.FINEST, "flightValidationVO " + flightValidationVO);
            if(flightValidationVO != null) {
	            carditTDT.setCarrierID(flightValidationVO.getFlightCarrierId());
	            carditTDT.setFlightSequenceNumber(
	                    flightValidationVO.getFlightSequenceNumber());
	            carditTDT.setLegSerialNumber(flightValidationVO.getLegSerialNumber());

	            FlightDetailsVO flightDetailsVO = new FlightDetailsVO();
	            flightDetailsVO.setCompanyCode(
	            		flightValidationVO.getCompanyCode());
	            flightDetailsVO.setCarrierId(
	            		flightValidationVO.getFlightCarrierId());
        		flightDetailsVO.setFlightNumber(
        				flightValidationVO.getFlightNumber());
        		flightDetailsVO.setFlightSequenceNumber(
        				flightValidationVO.getFlightSequenceNumber());

        		Collection<FlightSegmentSummaryVO> segmentSummaries = null;
				try {
                    log.log(Log.FINEST, "flightDetailsVO " + flightDetailsVO);
					segmentSummaries = new FlightOperationsProxy().findFlightSegments(flightDetailsVO.getCompanyCode(),flightDetailsVO.getCarrierId()
		            		,flightDetailsVO.getFlightNumber(),flightDetailsVO.getFlightSequenceNumber());
                    log.log(Log.FINEST, "segmentSummaries " + segmentSummaries);
				} catch (SystemException exp) {
                    log.log(Log.FINEST, "FlightOperationsProxy - findFlightSegments -  SystemException" + exp.getMessage());
				}
        		if(segmentSummaries != null && segmentSummaries.size() >0) {
        			for(FlightSegmentSummaryVO segmentSumVO : segmentSummaries) {
        				if(segmentSumVO.getSegmentOrigin().equals(
        						transportInformationVO.getDeparturePlace()) &&
        						segmentSumVO.getSegmentDestination().equals(
                						transportInformationVO.getArrivalPlace())) {
                            log.log(Log.FINEST, "Segment--found---origin--- " + segmentSumVO.getSegmentOrigin());
                            log.log(Log.FINEST, "Segment--found---Destination--- " + segmentSumVO.getSegmentDestination());
                            log.log(Log.FINEST, "Segment--found---serial number--- " + segmentSumVO.getSegmentSerialNumber());
        					carditTDT.setSegmentSerialNum(
        							segmentSumVO.getSegmentSerialNumber());
        				}
        			}
        		}
            }
    }
	/**
	 *
	 * 	Method		:	ProcessTempCarditMessagesWorker.formatFlightNumber
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param flightNumber
	 *	Parameters	:	@return
	 *	Return type	: 	String
	 */
	private String formatFlightNumber(String flightNumber) {
		int lastCharInAscii = (int) flightNumber.charAt(flightNumber.length() - 1);
		int flightNumberLength = FLTNUM_LEN;
		if (!((lastCharInAscii >= 65) && (lastCharInAscii <= 90))) {
			flightNumberLength = flightNumberLength - 1;
		}
		int iterIdx = flightNumberLength - flightNumber.length();
		while (iterIdx != 0) {
			flightNumber = PAD_DGT + flightNumber;
			iterIdx--;
		}
		log.log(Log.FINEST, "formatter fltnum " + flightNumber);
		return flightNumber;
	}
	/**
	 *
	 * 	Method		:	ProcessTempCarditMessagesWorker.validateFlight
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param flightFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	FlightValidationVO
	 */
	private FlightValidationVO validateFlight(FlightFilterVO flightFilterVO) throws SystemException {
		log.entering("Processor", "validateFlight");
		FlightValidationVO flightValidationVO = null;
		try {
			Collection<FlightValidationVO> flightValidationVOs = new FlightOperationsProxy()
					.validateFlight(flightFilterVO);
			if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
				flightValidationVO = flightValidationVOs.iterator().next();
			}
		} catch (ProxyException ex) {
			if (ex.getErrors() != null && ex.getErrors().size() != 0) {
				ErrorVO errorVO = ((ArrayList<ErrorVO>) ex.getErrors()).get(0);
				log.log(Log.INFO, "error code :", errorVO.getErrorCode());
				if (errorVO.getErrorCode().equals(INVALID_AIRLINE_CODE)) {
					return null;
				}
			}
			// throw new SystemException(ex.getMessage());
		}

		log.exiting("Processor", "validateFlight");
		return flightValidationVO;
	}
	/**
	 *
	 * 	Method		:	ProcessTempCarditMessagesWorker.validateAirlineByAlphaCode
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param carrierCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	AirlineValidationVO
	 */
	public AirlineValidationVO validateAirlineByAlphaCode(String companyCode, String carrierCode)
			throws SystemException {
		log.entering("Processor", "validateAirlineByAlphaCode");
		try {
			return new SharedAirlineProxy().validateAlphaCode(companyCode, carrierCode);
		} catch (SharedProxyException ex) {
			if (ex.getErrors() != null && ex.getErrors().size() != 0) {
				ErrorVO errorVO = ((ArrayList<ErrorVO>) ex.getErrors()).get(0);
				log.log(Log.INFO, "error code :", errorVO.getErrorCode());
				if (errorVO.getErrorCode().equals(INVALID_AIRLINE_CODE)) {
					return null;
				}
			}
		}
		return null;
	}
	/**
	 *
	 * 	Method		:	ProcessTempCarditMessagesWorker.isValidMailtag
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param mailtagLength
	 *	Parameters	:	@return
	 *	Return type	: 	boolean
	 */
	private boolean isValidMailtag(int mailtagLength) {
		boolean valid = false;
		String systemParameterValue = null;
		try {
			systemParameterValue = findSystemParameter(NO_OF_CHAR_ALLOWED_FOR_MAILTAG);
		} catch (SystemException e) {
			log.log(Log.SEVERE, "System Exception Caught");
		}
		if (systemParameterValue != null && !systemParameterValue.equals("NA")) {
			String[] systemParameterVal = systemParameterValue.split(",");
			for (String a : systemParameterVal) {
				if (Integer.valueOf(a) == mailtagLength) {
					valid = true;
					break;
				}
			}
		}
		if(systemParameterValue != null && "NA".equals(systemParameterValue)){
			valid = true;
		}
		return valid;
	}

	/**
	 *
	 * 	Method		:	ProcessTempCarditMessagesWorker.findSystemParameter
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param paramterCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	String
	 */
	private String findSystemParameter(String paramterCode) throws SystemException {

		log.entering("Processor", "findSystemParameter");
		Collection<String> paramterCodes = new ArrayList<String>();
		paramterCodes.add(paramterCode);
		if (systemParameters == null) {
			systemParameters = new HashMap<String, String>();
		}
		if (!systemParameters.containsKey(paramterCode)) {
			HashMap<String, String> parameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(paramterCodes);
			systemParameters.put(paramterCode, (String) (parameterMap.get(paramterCode)));
		}
		log.exiting("Processor", "findSystemParameter");
		return systemParameters.get(paramterCode);
	}
	/**
	 *
	 * 	Method		:	ProcessTempCarditMessagesWorker.validateCarditDomesticMailbagDetails
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param carditMessageVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	boolean
	 */
	 boolean validateCarditDomesticMailbagDetails(CarditMessageVO carditMessageVO) throws SystemException {

		boolean isValid = true;
		String error = CarditMessageVO.INVALID_EXCHANGE_OFFICE;

		Collection<TransportInformationMessageVO> transportInformations = carditMessageVO.getTransportInformation();
		try {
			exchangeOfficeMap = new MailController().findOfficeOfExchangeForPA(ediMsgeVO.getCompanyCode(),
					findSystemParameter(DOMESTIC_PACODE));
		} catch (SystemException exp) {
			log.log(Log.INFO, "SystemException");
		}
		for (TransportInformationMessageVO transportInformation : transportInformations) {
			// isValid = false;
			if (transportInformation.getDeparturePlace() != null
					&& !transportInformation.getDeparturePlace().trim().isEmpty()
					&& transportInformation.getArrivalPlace() != null
					&& !transportInformation.getArrivalPlace().trim().isEmpty()) {
				if (exchangeOfficeMap != null && !exchangeOfficeMap.isEmpty()) {
					if (!(exchangeOfficeMap.containsKey(transportInformation.getDeparturePlace())
							&& exchangeOfficeMap.containsKey(transportInformation.getArrivalPlace()))) {
						isValid = false;
						break;
					}
				}
			}
		}
		return isValid;
	}
	/**
	 *
	 * 	Method		:	ProcessTempCarditMessagesWorker.constructExchangeOfficeForUSPS
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param airport
	 *	Parameters	:	@return
	 *	Return type	: 	String
	 */
	private String constructExchangeOfficeForUSPS(String airport) {
		return "US"+airport+"X";
	}
	/**
	 *
	 * 	Method		:	ProcessTempCarditMessagesWorker.generateDespatchSerialNumber
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param currentKey
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	String
	 */
	private String generateDespatchSerialNumber(String currentKey) throws SystemException {
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		String key = null;
		StringBuilder keyCondition = new StringBuilder();
		keyCondition.append(new LocalDate(logonAttributes.getStationCode(), Location.ARP, false)
				.toDisplayFormat(CarditMessageVO.YEAR_FORMAT));
		Criterion criterion = KeyUtils.getCriterion(ediMsgeVO.getCompanyCode(), CarditMessageVO.DOM_USPS_DSN_KEY,
				keyCondition.toString());
		key = KeyUtils.getKey(criterion);
		if (MailConstantsVO.FLAG_YES.equals(currentKey) && key.length() > 4) {
			key = "9999";
			KeyUtils.resetKey(criterion, "0");
		} else if (MailConstantsVO.FLAG_YES.equals(currentKey) && !"1".equals(key)) {
			key = String.valueOf(Long.parseLong(key) - 1);
			KeyUtils.resetKey(criterion, key);
		}
		return checkLength(key, 4);
	}
	/**
	 *
	 * 	Method		:	ProcessTempCarditMessagesWorker.generateReceptacleSerialNumber
	 *	Added by 	:	A-6287 on 02-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param dsn
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	String
	 */
	private String generateReceptacleSerialNumber(String dsn) throws SystemException {
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		String key = null;
		StringBuilder keyCondition = new StringBuilder();
		keyCondition.append(new LocalDate(logonAttributes.getStationCode(), Location.ARP, false)
				.toDisplayFormat(CarditMessageVO.YEAR_FORMAT)).append(dsn);
		Criterion criterion = KeyUtils.getCriterion(ediMsgeVO.getCompanyCode(), CarditMessageVO.DOM_USPS_RSN_KEY,
				keyCondition.toString());
		key = KeyUtils.getKey(criterion);
		if (key.length() > 3) {
			key = "999";
			KeyUtils.resetKey(criterion, "0");
		}
		String rsn = checkLength(key, 3);
		log.log(Log.FINEST, "***** rsn Generated ---- " + rsn);
		return rsn;
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#instantiateJobScheduleVO()
	 *	Added by 			: A-6287 on 01-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@return
	 */
	@Override
	public JobScheduleVO instantiateJobScheduleVO() {
		return new CarditProcessJobScheduleVO();
	}

	  private String findPAForMailboxID(String companyCode, String mailboxId, String originOE) throws SystemException {
          CacheFactory factory = CacheFactory.getInstance();
           OfficeOfExchangeCache cache = factory
           .getCache(OfficeOfExchangeCache.ENTITY_NAME);   
           return cache.findPAForMailboxID(companyCode,mailboxId,originOE);
			}
	  public String findPAForOfficeOfExchange(String companyCode,
				String officeOfExchange) throws SystemException {
			String paCode=null;
			Page<OfficeOfExchangeVO> officeOfExchangePage = null;
			CacheFactory factory = CacheFactory.getInstance();
			OfficeOfExchangeCache cache = factory  
				.getCache(OfficeOfExchangeCache.ENTITY_NAME);
			officeOfExchangePage= cache.findOfficeOfExchange(companyCode,officeOfExchange,1);
			 if(officeOfExchangePage!=null && !officeOfExchangePage.isEmpty()
					 &&officeOfExchangePage.iterator().next().getPoaCode()!=null
					 &&officeOfExchangePage.iterator().next().isActive()){
				 paCode =officeOfExchangePage.iterator().next().getPoaCode() ;
			 }
			 return paCode;
		}
	  private void rDTCalculationWhenConsignmentCompletionDateNotNull(String rcpOrg, String rcpDest, String rcpClass,
				CarditMessageVO carditMessageVO, CarditVO carditVO, CarditReceptacleVO carditReceptacleVO, String paCode)
				throws SystemException {
			LocalDate reqDeliveryDate;
			RdtMasterFilterVO filterVO = new RdtMasterFilterVO();
			filterVO.setAirportCodes(rcpDest);
			filterVO.setOriginAirportCode(rcpOrg);
			filterVO.setCompanyCode(carditVO.getCompanyCode());  
			filterVO.setMailClass(rcpClass); 
			filterVO.setGpaCode(paCode);
			Collection<MailRdtMasterVO> mailRdtMasterVOs = null;
			mailRdtMasterVOs = new MailController().findRdtMasterDetails(filterVO); 
			if (mailRdtMasterVOs != null && !mailRdtMasterVOs.isEmpty()) {
				 int rdtOffset = 0;
				 int rdtDay = 0;
				 int priority = 0;
				for ( MailRdtMasterVO mailRdtMasterVO : mailRdtMasterVOs) {
					 if (rcpOrg != null && mailRdtMasterVO.getOriginAirportCodes()!=null && mailRdtMasterVO.getOriginAirportCodes().equals(rcpOrg)
							&& rcpDest != null && mailRdtMasterVO.getAirportCodes()!=null && mailRdtMasterVO.getAirportCodes().equals(rcpDest)){
						 rdtOffset = mailRdtMasterVO.getRdtOffset();
						 rdtDay = mailRdtMasterVO.getRdtDay() - 1;
						 break;
						}
						if (rcpOrg != null  && mailRdtMasterVO.getOriginAirportCodes()!=null && mailRdtMasterVO.getOriginAirportCodes().equals(rcpOrg)
								&& mailRdtMasterVO.getAirportCodes() == null){	
							 rdtOffset = mailRdtMasterVO.getRdtOffset();
							 rdtDay = mailRdtMasterVO.getRdtDay() - 1;
							 priority = 1;
					 } 
					 else { if( rcpDest != null && mailRdtMasterVO.getAirportCodes()!=null && mailRdtMasterVO.getAirportCodes().equals(rcpDest)
							 && mailRdtMasterVO.getOriginAirportCodes()==null && priority==0) {
						 rdtOffset = mailRdtMasterVO.getRdtOffset();
						 rdtDay = mailRdtMasterVO.getRdtDay() - 1;
					 	}
					 } 
				}
				 
			      if (rcpOrg != null) {
			        reqDeliveryDate = new LocalDate(rcpOrg, Location.ARP, true);
			      } else {
			        reqDeliveryDate = new LocalDate(rcpDest, Location.ARP, true);
			      } 
				  BeanHelper.copyProperties(reqDeliveryDate, carditMessageVO.getConsignmentCompletionDate());
				  
				if (reqDeliveryDate != null && (rdtDay > 0 || rdtOffset > 0)) {
					reqDeliveryDate.setTime(INITIAL_TIME);
					reqDeliveryDate.addDays(rdtDay);
					reqDeliveryDate.addMinutes(rdtOffset);
							}
				carditReceptacleVO.setReqDeliveryTime(reqDeliveryDate);
			} else {
				carditReceptacleVO.setReqDeliveryTime(carditMessageVO.getConsignmentCompletionDate());
			}
		}
}
