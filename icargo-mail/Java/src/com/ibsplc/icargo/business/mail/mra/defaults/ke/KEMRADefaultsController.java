/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.ke.KEMRADefaultsController.java
 *
 *	Created by	:	a-7929
 *	Created on	:	May 10, 2018
 *
 *  Copyright 2014 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.defaults.ke;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.proxy.CRAAccountingProxy;
import com.ibsplc.icargo.business.cra.accounting.extinterface.vo.SAPInterfaceFileLogVO;
import com.ibsplc.icargo.business.cra.accounting.extinterface.vo.SAPInterfaceFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.MRABillingDetailsHistory;
import com.ibsplc.icargo.business.mail.mra.defaults.MRAInterfacedDetails;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.FlightRevenueInterfaceVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.business.mail.mra.proxy.MsgBrokerMessageProxy;
import com.ibsplc.icargo.business.mail.mra.wsproxy.webservices.ke.MailtrackingMRAWSProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.services.jaxws.proxy.exception.WebServiceException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ke.xsd.mra.revenueinterface.Response;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.ke.KEMRADefaultsController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7929	:	10-May-2018	:	Draft
 */



@Module("mail")
@SubModule("mra")
public class KEMRADefaultsController {
	
	private Log log = LogFactory.getLogger("MRA:DEFAULTS:KE");
	private static final String CLASS_NAME  = "KEMRADefaultsController";
	private static final String MODULE_NAME = "mail.mra.defaults";
	private static final String NO_RECORDS_FOUND = "NO RECORDS";
	private static final String KEERP = "KEERP";
	private static final String IFCSTD = "IFCSTD";
	private static final String FLAG_YES = "Y";
	private static final String FLAG_FAIL = "F";
	private static final String FLAG_NO = "N";
	private static final String MCA_REVRESE_AGAINST_ORIGINAL = "MCAO";
	private static final String MCA_REVICED_AMOUNT = "MCAR";
	private static final String TRIGGERPOINT_MCA = "CA";
	private static final String SUCCESS = "S";
	private static final String MAL_BILLINGINTERFACE_JOBID = "953";
	private static final String MALFLTREVJOB = "MALFLTREVJOB";
	private static final String MAIL_BILLING = "MALBLG";
	private static final String SUBSYS_MRA = "M";
	
	
	
	
	
	
	private static final String MAIL_FLIGHT_REVENUE_INTERFACE_ROWCOUNT = "mail.mra.flightrevenueinterfacerowcount";
	
	
	
	/**
	 * 
	 * 	Method		:	KEMRADefaultsController.constructDAO
	 *	Added by 	:	A-7929 on 10-May-2018
	 * 	Used for 	:   ICRD-245605
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	MailTrackingDefaultsDAO
	 */
	private static MRADefaultsDAO constructDAO()throws SystemException {
	    	
	    		try {
	    			EntityManager entityManager = PersistenceController.getEntityManager();
	    			return MRADefaultsDAO.class.cast(entityManager.getQueryDAO(MODULE_NAME));
	    		}
	    		catch(PersistenceException persistenceException) {
	    			throw new SystemException(persistenceException.getErrorCode());
	    		}
	    	}

	
	
	/**
	 * 
	 * 	Method		:	KEMRADefaultsController.generateMailBillingInterfaceFile
	 *	Added by 	:	A-7929 on 10-May-2018
	 * 	Used for 	:   ICRD-245605
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 * @throws FinderException 
	 */
	public String generateMailBillingInterfaceFile(String companyCode,String regenerateFlag,String fileNameToRegenerate,LocalDate fromDate,LocalDate toDate) throws SystemException, FinderException {
		log.entering(CLASS_NAME, "generateMailBillingInterfaceFile");
		
		String triggerPoint="";
		SAPInterfaceFilterVO sapInterfaceFilterVO = new SAPInterfaceFilterVO();
		SAPInterfaceFileLogVO sapInterfaceFileLogVO = new SAPInterfaceFileLogVO();		
		String status = null;	
		
		//ICRD-320855
		
		if("MALBLGINTJOB".equals(fileNameToRegenerate)){//Method triggered from JOB
			triggerPoint="JOB";
		}else{//triggered from CRA122 Screen 
			triggerPoint="CRA";
		}
		
		//if the trigger point is job then log the transaction in CRAINTFILLOG table 
		if("JOB".equals(triggerPoint)){
		sapInterfaceFilterVO.setCompanyCode(companyCode);
		sapInterfaceFilterVO.setFileType(MAIL_BILLING);
		sapInterfaceFilterVO.setFromDate(fromDate);
		sapInterfaceFilterVO.setToDate(toDate);
		sapInterfaceFilterVO.setRegenerateFlag(FLAG_NO);
		sapInterfaceFilterVO.setSubsystem(SUBSYS_MRA);

		/**
		 * For saving in CRAINTFILLOG table
		 */
		 try {
			 sapInterfaceFileLogVO  = new CRAAccountingProxy().saveSAPInterfaceFileLogs(sapInterfaceFilterVO);
			 
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage());			
		}
		 
		}

		String fileName = null ;
		if("MALBLGINT".equals(fileNameToRegenerate)||"MALBLGINTJOB".equals(fileNameToRegenerate)) {
			fileNameToRegenerate="";
		}
		fileName = constructDAO().generateMailBillingInterfaceFile(regenerateFlag,fileNameToRegenerate,fromDate,toDate);
		
		if("JOB".equals(triggerPoint)){
		if (!NO_RECORDS_FOUND.equals(fileName)) {
			sapInterfaceFileLogVO.setRemarks(fileName);
		    sapInterfaceFileLogVO.setInterfaceFilename(fileName);
		    sapInterfaceFileLogVO.setGenerationStatus(SAPInterfaceFileLogVO.FILE_GENERATION_STATUS_COMPLETED);
		    status=SAPInterfaceFileLogVO.FILE_GENERATION_STATUS_COMPLETED;
			}
			else{
				sapInterfaceFileLogVO.setRemarks(fileName);
			    sapInterfaceFileLogVO.setGenerationStatus(SAPInterfaceFileLogVO.FILE_GENERATION_STATUS_FAILED);
			    status=SAPInterfaceFileLogVO.FILE_GENERATION_STATUS_FAILED;
		}
		
		try {
			new CRAAccountingProxy().updateInterfaceLogDetails(sapInterfaceFileLogVO , status);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage());
		}
		
		}

		if (!NO_RECORDS_FOUND.equals(fileName)) {
			/*if(!FLAG_YES.equals(regenerateFlag)){
			fileName = updateMailBillingInterfaceFLag(fileName);
			}*/
			fileName = triggerERPGeneration(fileName);
		}
		log.exiting(CLASS_NAME, "generateMailBillingInterfaceFile");
		return fileName;
		
	}

   

	/**
	 * 
	 * 	Method		:	KEMRADefaultsController.triggerERPGeneration
	 *	Added by 	:	A-7929 on 08-Jun-2018
	 * 	Used for 	:   ICRD-245605
	 *	Parameters	:	@param sapInterfaceFilterVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public String triggerERPGeneration(String fileName)
			throws SystemException {
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		MsgBrokerMessageProxy msgbrokerMessageProxy = new MsgBrokerMessageProxy();
		MessageVO messageVO = new MessageVO();
		messageVO.setCompanyCode(logonAttributes.getCompanyCode());
		messageVO.setStationCode(logonAttributes.getStationCode());
		messageVO.setFileName(fileName);
		messageVO.setMessageType(KEERP);
		messageVO.setMessageVersion("1");
		messageVO.setMessageStandard(IFCSTD);
		messageVO.setOriginalMessage(fileName);
		messageVO.setReceiptOrSentDate(new LocalDate(logonAttributes
				.getStationCode(), Location.STN, true));
		messageVO.setRawMessage(fileName);
		messageVO.setRawMessageBlob(fileName);
		MessageDespatchDetailsVO messageDespatchDetailsVO = new MessageDespatchDetailsVO();
		messageDespatchDetailsVO.setPartyType("AR");
		messageDespatchDetailsVO.setParty(logonAttributes.getCompanyCode());
		messageDespatchDetailsVO.setStation(messageVO.getStationCode());
		ArrayList<MessageDespatchDetailsVO> messageDespatchDetails = new ArrayList<MessageDespatchDetailsVO>();
		messageDespatchDetails.add(messageDespatchDetailsVO);
		messageVO.setDespatchDetails(messageDespatchDetails);
		log.log(Log.FINE, "*****messageVO******" + messageVO);
		try {
			msgbrokerMessageProxy.sendMessageText(messageVO);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		return fileName;
	}

	/**
	 * 
	 * 	Method		:	KEMRADefaultsController.doInterfaceFlightRevenueDtls
	 *	Added by 	:	a-8061 on 02-Jul-2018
	 * 	Used for 	:	ICRD-245594
	 *	Parameters	:	@param companycode
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void doInterfaceFlightRevenueDtls(String companycode, boolean isFromRetrigger) throws SystemException {
		  
		log.entering(CLASS_NAME, "doInterfaceFlightRevenueDtls");
		Collection<FlightRevenueInterfaceVO> flightRevenueInterfaceVOsTemp=null;
		List<FlightRevenueInterfaceVO> flightRevenueInterfaceVOs = (List) new MRABillingDetailsHistory()
				.findFlightrevenueDetails(companycode, isFromRetrigger);
		List<FlightRevenueInterfaceVO> flightRevenueInterfaceVOsToInterface = new ArrayList<FlightRevenueInterfaceVO>();
		List<FlightRevenueInterfaceVO> flightRevenueInterfaceVOsBlocked = new ArrayList<FlightRevenueInterfaceVO>();
		Map<String,String> mcaAdjRecords= new HashMap<String,String>();
		MaintainCCAFilterVO maintainCCAFilterVO=null;
		Map<String,String> deltaCheckMap= new HashMap<String,String>();
		String deltaCheckValTmp=null;
		StringBuilder tmpValue= null;
		ArrayList<String> blockedMailBags= new ArrayList<String>(); 
		
		// to identify MCA adjusted records and update adj code : ICRD-274973
		// begin
		if(flightRevenueInterfaceVOs!=null && !flightRevenueInterfaceVOs.isEmpty()){			
			for(FlightRevenueInterfaceVO flightRevenueInterfaceVO : flightRevenueInterfaceVOs){				
				
				if(flightRevenueInterfaceVO.getAdjustCode()!=null){
					tmpValue = new StringBuilder().append(flightRevenueInterfaceVO.getRegionCode())
							.append(flightRevenueInterfaceVO.getBranchCode())
							.append(flightRevenueInterfaceVO.getSettlement())
							.append(flightRevenueInterfaceVO.getMailOrigin ())
							.append(flightRevenueInterfaceVO.getMailDestination())
							.append(flightRevenueInterfaceVO.getCurrency())
							.append(flightRevenueInterfaceVO.getFlightLineCode())
							.append(flightRevenueInterfaceVO.getBillingBranch())
							.append(flightRevenueInterfaceVO.getCarrTypeC ())
							.append(flightRevenueInterfaceVO.getFlightNumber ())
							.append(flightRevenueInterfaceVO.getFlightDate())
							.append(flightRevenueInterfaceVO.getFlightOrigin())
							.append(flightRevenueInterfaceVO.getFlightDestination());
							if("Y".equals(flightRevenueInterfaceVO.getInterfaceFlag())){
								tmpValue.append(flightRevenueInterfaceVO.getBlockCheckMailWeight())
								.append(flightRevenueInterfaceVO.getBlockCheckRateAmount())
								.append(flightRevenueInterfaceVO.getBlockCheckRateAmountInUSD());
							}else{
								tmpValue.append(flightRevenueInterfaceVO.getMailWeight())
								.append(flightRevenueInterfaceVO.getRateAmount())
								.append(flightRevenueInterfaceVO.getRateAmountInUSD());
							}
							
					deltaCheckValTmp=tmpValue.toString();
					if (!deltaCheckMap
							.containsKey(flightRevenueInterfaceVO.getrSN() + flightRevenueInterfaceVO.getSerNumber())) {
						deltaCheckMap.put(flightRevenueInterfaceVO.getrSN() + flightRevenueInterfaceVO.getSerNumber(),
								deltaCheckValTmp);
					} else if (deltaCheckMap
							.containsKey(flightRevenueInterfaceVO.getrSN() + flightRevenueInterfaceVO.getSerNumber())
							&& deltaCheckMap
									.get(flightRevenueInterfaceVO.getrSN() + flightRevenueInterfaceVO.getSerNumber())
									.equals(deltaCheckValTmp)) {
						blockedMailBags
								.add(flightRevenueInterfaceVO.getrSN() + flightRevenueInterfaceVO.getSerNumber());
					}

				}

				if (TRIGGERPOINT_MCA.equals(flightRevenueInterfaceVO.getTriggerPoint())
						&& (FLAG_NO.equals(flightRevenueInterfaceVO.getInterfaceFlag())
								|| FLAG_FAIL.equals(flightRevenueInterfaceVO.getInterfaceFlag()))) {
					if(!mcaAdjRecords.containsKey(flightRevenueInterfaceVO.getrSN())){
						mcaAdjRecords.put(flightRevenueInterfaceVO.getrSN(), flightRevenueInterfaceVO.getrSN());
					}
				}
			}
		}
		if(flightRevenueInterfaceVOs!=null && !flightRevenueInterfaceVOs.isEmpty()){			
			for(FlightRevenueInterfaceVO flightRevenueInterfaceVO : flightRevenueInterfaceVOs){	
				if(mcaAdjRecords.containsKey(flightRevenueInterfaceVO.getrSN())){
					//A-8061 Added for ICRD-289296 begin
					 maintainCCAFilterVO = new MaintainCCAFilterVO();
					 maintainCCAFilterVO.setBillingBasis(flightRevenueInterfaceVO.getrSN());
					 maintainCCAFilterVO.setCompanyCode(flightRevenueInterfaceVO.getCompanyCode());
					 flightRevenueInterfaceVO.setBillingBranch(flightRevenueInterfaceVO.getSettlement());	 
					if (FLAG_NO.equals(flightRevenueInterfaceVO.getInterfaceFlag())
							|| FLAG_FAIL.equals(flightRevenueInterfaceVO.getInterfaceFlag())) {
						flightRevenueInterfaceVO.setAdjustCode(MCA_REVICED_AMOUNT);
					}else if (FLAG_YES.equals(flightRevenueInterfaceVO.getInterfaceFlag())){
						flightRevenueInterfaceVO.setAdjustCode(MCA_REVRESE_AGAINST_ORIGINAL);
					}
				}
				if (!blockedMailBags
						.contains(flightRevenueInterfaceVO.getrSN() + flightRevenueInterfaceVO.getSerNumber())) {
					flightRevenueInterfaceVOsToInterface.add(flightRevenueInterfaceVO);
				}else{
					flightRevenueInterfaceVOsBlocked.add(flightRevenueInterfaceVO);
				}
				
			}
		}
		//ICRD-274973 end 
		
		//Modified as part of bug ICRD-331531 by A-5526 
		
		if(flightRevenueInterfaceVOsBlocked!=null && !flightRevenueInterfaceVOsBlocked.isEmpty()){
			new MRABillingDetailsHistory().updateInterfaceStatus(flightRevenueInterfaceVOsBlocked,FLAG_YES);
		}
		if(flightRevenueInterfaceVOsToInterface!=null && !flightRevenueInterfaceVOsToInterface.isEmpty()){

			new MRABillingDetailsHistory().updateInterfaceStatus(flightRevenueInterfaceVOsToInterface,FLAG_YES);
			
			// save data into table MALMRAINTFCDDTL

			saveFlightRevenueInterfacedDetails(flightRevenueInterfaceVOsToInterface);

		}

	}
	
	
	/**
	 * 
	 * 	Method		:	KEMRADefaultsController.findSystemParameterValue
	 *	Added by 	:	a-8061 on 11-Jul-2018
	 * 	Used for 	:
	 *	Parameters	:	@param syspar
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	String
	 */
	public String findSystemParameterValue(String syspar)
			throws SystemException {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap=null;
		try {
			systemParameterMap = new SharedDefaultsProxy()
					.findSystemParameterByCodes(systemParameters);
		} catch (ProxyException e) {
			sysparValue="100";
		}
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}
	
	
	
	/**
	 * 
	 * 	Method		:	KEMRADefaultsController.generateFlightRevenueInterfaceDtls
	 *	Added by 	:	a-8061 on 02-Jul-2018
	 * 	Used for 	:	ICRD-245594
	 *	Parameters	:	@param flightRevenueInterfaceVOs
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void generateFlightRevenueInterfaceDtls(Collection<FlightRevenueInterfaceVO> flightRevenueInterfaceVOs )throws SystemException{
 
		if(flightRevenueInterfaceVOs!=null && !flightRevenueInterfaceVOs.isEmpty()){
			try {
				MailtrackingMRAWSProxy mailtrackingMRAWSProxy = new MailtrackingMRAWSProxy();
				Response response=	mailtrackingMRAWSProxy.doInterfaceFlightRevenueDtls(flightRevenueInterfaceVOs);
				if(response != null && SUCCESS.equals(response.getResult())){
				new MRAInterfacedDetails().updateInterfaceStatus(flightRevenueInterfaceVOs,FLAG_YES);
				}else{
					//Added for monitoring the duplication on interfacing for bug ICRD-338009 by A-5526.Temporary change
					new MRAInterfacedDetails().updateInterfaceStatus(flightRevenueInterfaceVOs,"X");
					//new MRABillingDetailsHistory().updateInterfaceStatus(flightRevenueInterfaceVOs,"F");
				}
			} catch (WebServiceException webServiceException) {
				//Added for monitoring the duplication on interfacing for bug ICRD-338009 by A-5526.Temporary change
				new MRAInterfacedDetails().updateInterfaceStatus(flightRevenueInterfaceVOs,"W");
				//new MRABillingDetailsHistory().updateInterfaceStatus(flightRevenueInterfaceVOs,"F");
			} 
			catch(Exception systemException){
				//Added for monitoring the duplication on interfacing for bug ICRD-338009 by A-5526.Temporary change
				new MRAInterfacedDetails().updateInterfaceStatus(flightRevenueInterfaceVOs,"S");
				//new MRABillingDetailsHistory().updateInterfaceStatus(flightRevenueInterfaceVOs,"F");
			}
		
		}
	}
	
	
	/**
	 * Method to save the interface details into table MALMRAINTFCDDTL
	 * @author A-5526
	 * @param flightRevenueInterfaceVOsToInterface
	 * @throws SystemException
	 */
	private void saveFlightRevenueInterfacedDetails(
			List<FlightRevenueInterfaceVO> flightRevenueInterfaceVOsToInterface) throws SystemException{
		
		for(FlightRevenueInterfaceVO flightRevenueInterfaceVO:flightRevenueInterfaceVOsToInterface){
			new MRAInterfacedDetails(flightRevenueInterfaceVO);
		}
	}


	/**
	 * Method to trigger interfacing
	 * @author A-5526
	 * @param companycode
	 * @param isFromRetrigger
	 * @throws SystemException
	 */
	public void doInterface(String companycode, int maxRecord, boolean isFromRetrigger) throws SystemException {
		String rowCount = null;
		int size = 100;
		Collection<FlightRevenueInterfaceVO> flightRevenueInterfaceVOsTemp = null;
		rowCount = findSystemParameterValue(MAIL_FLIGHT_REVENUE_INTERFACE_ROWCOUNT);
		if (rowCount != null) {
			size = Integer.parseInt(rowCount);
		}

		if (isFromRetrigger) {
			size = maxRecord;
		}

		List<FlightRevenueInterfaceVO> flightRevenueInterfaceVOs = (List) new MRAInterfacedDetails()
				.findInterfaceDetails(companycode, isFromRetrigger);

		for (int start = 0; start < flightRevenueInterfaceVOs.size(); start += size) {
			int end = Math.min(start + size, flightRevenueInterfaceVOs.size());
			flightRevenueInterfaceVOsTemp = flightRevenueInterfaceVOs.subList(start, end);
			generateFlightRevenueInterfaceDtls(flightRevenueInterfaceVOsTemp);
		}

	}

/**
 * 
 * 	Method		:	KEMRADefaultsController.saveVoidedInterfaceDetails
 *	Added by 	:	A-8061 on 15-Oct-2019
 * 	Used for 	:	ICRD-336689
 *	Parameters	:	@param VOs
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */
	public void saveVoidedInterfaceDetails(Collection<DocumentBillingDetailsVO> VOs)throws SystemException{
		
		if(VOs!=null && !VOs.isEmpty()){
			for(DocumentBillingDetailsVO documentBillingDetailsVO:VOs){
				List<FlightRevenueInterfaceVO> voidedRevenueInterfaceVOs = (List) new MRAInterfacedDetails().findVoidedInterfaceDetails(documentBillingDetailsVO);
				if(voidedRevenueInterfaceVOs!=null && !voidedRevenueInterfaceVOs.isEmpty()){
					for(FlightRevenueInterfaceVO flightRevenueInterfaceVO:voidedRevenueInterfaceVOs){
						new MRAInterfacedDetails(flightRevenueInterfaceVO);
					}
				}
			}
		}
	}

}
