/*
 * MailTrackingDefaultsServiceImpl.java Created on 20-03-2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.services.mail.operations.webservices.internal;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import com.ibsplc.icargo.business.mail.operations.types.internal.MailErrorType;
import com.ibsplc.icargo.business.mail.operations.types.internal.MailScannedType;
import com.ibsplc.icargo.business.mail.operations.types.internal.ValidateContainerRequestType;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailScanDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.types.internal.ErrorDetailType;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.WebServiceEndPoint;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.exception.WSBusinessException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.NodeUtil;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;

@javax.jws.WebService(serviceName = "MailOperationsService", portName = "MailOperationsService", targetNamespace = "http://www.ibsplc.com/icargo/services/internal/MailOperationsService/2014/03/20_01", wsdlLocation = "file:./wsdl/mail/operations/internal/MailTrackingDefaultsIntService.wsdl", endpointInterface = "com.ibsplc.icargo.services.mail.operations.webservices.internal.MailOperationsService")
@Module("mail")
@SubModule("operations")
public class MailTrackingDefaultsServiceImpl extends WebServiceEndPoint
		implements MailOperationsService {

	private final static String RUNTIME_EXCEPTION = "Runtime exception, Contact system administrator with business scenario";
	private final static String ANDROID_FLAG = "FromAndroid";

    /**
     * @author A-5183
     * @param ScannedMailType
     * @return String
	 * @see com.ibsplc.icargo.services.mail.operations.webservices.internal.MailTrackingDefaultsService#saveMailDetails(com.ibsplc.icargo.business.mail.operations.types.internal.ScannedMailType
	 *      scannedMailType )*
     */

	public MailTrackingDefaultsServiceImpl(){
		this.retryCount=3;
	}
	
	@Override
	public ErrorDetailType saveMailDetails(MailScannedType scannedMailType) {

		Collection<MailScanDetailVO> mailScanDetailVOs=null;
		/*
		 * LogonAttributes logonAttributes = null; try { logonAttributes =
		 * ContextUtils.getSecurityContext() .getLogonAttributesVO(); } catch
		 * (SystemException e) { e.printStackTrace(); }
		 */
		ErrorDetailType errorDetailType = null;
		String realTimeuploadrequired = "Y";
		//Added as part of bug ICRD-135866 by A-5526 starts
		errorDetailType = new ErrorDetailType();
		errorDetailType.setErrorCode("ok");
		errorDetailType.setErrorDescription("noerror");
		//Added as part of bug ICRD-135866 by A-5526 ends
    	  try {
			// Code to find the system parameter value of
			// "mail.operations.scanmailupload.isrealtimeuploadrequired".

			realTimeuploadrequired = despatchRequest(
					"findRealTimeuploadrequired",
					MailConstantsVO.REALTIME_UPLOAD_REQUIRED);
			if(!"Y".equals(realTimeuploadrequired)){
				boolean isTrue = checkForOnlineDevice(scannedMailType);
				if(isTrue)
					realTimeuploadrequired = "Y";
			}

			// If realTimeuploadrequired is Y or N.ScannedData should be saved
			// to the table MTKMALSNDTL starts.
			mailScanDetailVOs = createMailScanDetailVOs(scannedMailType,
					realTimeuploadrequired);
			if(mailScanDetailVOs!=null && mailScanDetailVOs.size()>0){
 		  despatchRequest("saveMailScannedDetails", mailScanDetailVOs);
			}
		} catch (WSBusinessException e1) {
			log.log(Log.FINE, "Scanned Mails",mailScanDetailVOs);
			//e1.printStackTrace();
		} catch (SystemException e1) {
			log.log(Log.FINE, "Scanned Mails",mailScanDetailVOs);
				//e1.printStackTrace();
		}

		if (MailConstantsVO.FLAG_YES.equals(realTimeuploadrequired)) {

			Collection<MailUploadVO> mailBagVOs = null;
			List<String> mailScannedString = scannedMailType.getMaildetails();
			log.log(Log.FINE, "sacnning port",
					scannedMailType.getScanningPort());

			String[] mailArray = mailScannedString
					.toArray(new String[mailScannedString.size()]);
			mailBagVOs = constructMailUploadVO(mailArray);

			log.log(Log.FINE, "MailUploadVO", mailBagVOs);
			for (MailUploadVO mail : mailBagVOs) {

				ScannedMailDetailsVO scannedMailDetailsVO = null;
				Collection<MailUploadVO> mails = new ArrayList<MailUploadVO>();
				mails.add(mail);

				try {
					scannedMailDetailsVO = despatchRequest(
							"saveMailUploadDetails", mails,scannedMailType.getScanningPort());
					log.log(Log.INFO, "ScannedMailDetailsVO*****",
							scannedMailDetailsVO);

				} catch (WSBusinessException e) {
					errorDetailType = populateErrorDetailType(errorDetailType, e);
					log.log(Log.FINE, "WSBusinessException Caught");
				} catch (SystemException e) {
					log.log(Log.FINE, "SystemException Caught");
				}
			}

  		}

		/*
		 * try { despatchRequest(
		 * "performUploadCorrection",logonAttributes.getCompanyCode()); } catch
		 * (WSBusinessException e) { log.log(Log.SEVERE,
		 * "WSBusinessException",e.getMessage()); } catch (SystemException e) {
		 * log.log(Log.SEVERE, "SystemException",e.getMessage()); }
		 */
		return errorDetailType;
	}

	/**
	 * Method to split and save scanned informations
	 *
	 * @param scannedMailType
	 * @param realTimeuploadrequired
	 * @throws SystemException
	 */
	public Collection<MailScanDetailVO> createMailScanDetailVOs(
			MailScannedType scannedMailType, String realTimeuploadrequired) {
		log.entering("MailTrackingDefaultsServiceImpl",
				"saveMailScannedDetails");
		String deviceId="";
		String deviceIpAddress="";
		String fileSequenceNumber="";
		MailScanDetailVO mailScanDetailVO=null;
		Collection<MailScanDetailVO> mailScanDetailVOs=new ArrayList<MailScanDetailVO>();
		log.log(Log.INFO, "HHTFiles", scannedMailType);
		List<String> mailScannedString = scannedMailType.getMaildetails();

		log.log(Log.FINE, "sacnning port", scannedMailType.getScanningPort());
		String[] mailArray = mailScannedString
				.toArray(new String[mailScannedString.size()]);
		String scannedString = null;

		for (int i = 0; i <= mailArray.length - 1; i++) {
			scannedString = mailArray[i];
			log.log(Log.INFO, "Splited is*****", scannedString);
			// The below code is used to split the values of
			// FilesequenceNumber,device ID and device Ip Address
			if(scannedString.startsWith("mailtracking.defaults") || scannedString.startsWith("mail.operations")){
				String[] splitHeaderData = null;
				splitHeaderData = scannedString.split(";");
				fileSequenceNumber=splitHeaderData[6];
				deviceId=splitHeaderData[7];
				deviceIpAddress=splitHeaderData[8];
			}
			if (!scannedString.startsWith("mailtracking.defaults") && !scannedString.startsWith("mail.operations")) {
				mailScanDetailVO = saveMailScanDetailVO(scannedString,
						scannedMailType, fileSequenceNumber, deviceId,
						deviceIpAddress);
				mailScanDetailVO.setUploadStatus(realTimeuploadrequired);

			}
			if(mailScanDetailVO!=null){
			mailScanDetailVOs.add(mailScanDetailVO);
			}
		}
		log.log(Log.INFO, "HHT each scan details", mailArray);
return mailScanDetailVOs;
	}

	/**
	 * Method to savemailScanDetailVos
	 *
	 * @param scannedString
	 * @param scannedMailType
	 * @param deviceIpAddress
	 * @param deviceId
	 * @param fileSequenceNumber
	 * @throws SystemException
	 */
	private MailScanDetailVO saveMailScanDetailVO(String scannedString,
			MailScannedType scannedMailType, String fileSequenceNumber,
			String deviceId, String deviceIpAddress) {
		log.entering("MailTrackingDefaultsServiceImpl", "saveMailScanDetailVO");
		LogonAttributes logonAttributes = null;

		try {
			logonAttributes = (LogonAttributes) getLogonAttributesVO();
		} catch (SystemException e) {

			log.log(Log.FINE, "SystemException Caught");

		}

			MailScanDetailVO mailScanDetailVO = new MailScanDetailVO();
		String[] splitData = null;
            String mailSequenceNumber="";
			splitData = scannedString.split(";");

		mailScanDetailVO.setCompanyCode(logonAttributes.getCompanyCode());
			mailScanDetailVO.setDeviceId(deviceId);
			mailScanDetailVO.setDeviceIpAddress(deviceIpAddress);
			mailScanDetailVO.setFileSequence(Integer.parseInt(fileSequenceNumber));
		mailScanDetailVO.setLastUpdateTime(new LocalDate(logonAttributes
				.getAirportCode(), ARP, true));
			mailScanDetailVO.setLastUpdateUser(logonAttributes.getUserId());
			if(splitData[13]!=null && splitData[13].trim().length()>0 ){
			mailScanDetailVO.setMailBagId(splitData[13]);
		} else {
				mailScanDetailVO.setMailBagId(splitData[6]);
			}
		mailScanDetailVO.setAirport(scannedMailType.getScanningPort());
			mailScanDetailVO.setScanData(scannedString);
			mailScanDetailVO.setScannedUser(logonAttributes.getUserId());
			//Added for BUG ICRD-145494 by A-5526 starts
			String nodeName=NodeUtil.getInstance().getNodeName();
			if(nodeName!=null && nodeName.trim().length()>0){
				mailScanDetailVO.setNodeName(nodeName);
			}
			LocalDate scanDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			scanDate.setDateAndTime(splitData[14]);
			mailScanDetailVO.setScanDate(scanDate);
			//Added for BUG ICRD-145494 by A-5526 ends
		// mailScanDetailVO.setUploadStatus(MailConstantsVO.FLAG_NO);
			//Added as part of bug ICRD-153402 by A-5526 starts (Added as per Gino)
			if (splitData[4] != null && splitData[4].trim().length() > 0) {
				LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				flightDate.setDate(splitData[4]);

			}
			//Added as part of bug ICRD-153402 by A-5526 ends
			if (splitData[44].length() > 2) {
				if (splitData[44].indexOf("*") > 0) {
					mailSequenceNumber = splitData[44].substring(0,
							splitData[44].indexOf("*"));
				mailScanDetailVO.setMailSequenceNumber(Integer
						.parseInt(mailSequenceNumber));
				}

			}
			log.log(Log.INFO, "mailScanDetailVO", mailScanDetailVO);
			//new MailScanDetail(mailScanDetailVO);

			log.exiting("MailTrackingDefaultsServiceImpl", "saveMailScanDetailVO");

		return mailScanDetailVO;

	}




	private Collection<MailUploadVO> constructMailUploadVO(String[] uploadData) {

		Collection<MailUploadVO> mailBagVOs = null;
		String fileData = null;

		MailUploadVO mailUploadVO = null;

		for (int i = 0; i <= uploadData.length - 1; i++) {
			mailUploadVO = new MailUploadVO();
			fileData = uploadData[i];
			log.log(Log.INFO, "Splited is*****", fileData);

			if (!fileData.startsWith("mailtracking.defaults") && !fileData.startsWith("mail.operations")) {

			mailUploadVO = splitData(fileData);

    		if (mailUploadVO != null) {
				if (mailBagVOs == null) {
					mailBagVOs = new ArrayList<MailUploadVO>();
				}
				log.log(Log.INFO, "MailUploadVO is*****", mailUploadVO);

				mailBagVOs.add(mailUploadVO);
				}

			}

		}

		for (MailUploadVO mailbag : mailBagVOs) {
			log.log(Log.INFO, "Mailbags is*****", mailbag);
		}

		return mailBagVOs;

	}

	/**
	 * Method for Split MailBag details from File
	 *
	 * @param data
	 * @return
	 */

	private MailUploadVO splitData(String data) {
		String[] splitData = null;
LogonAttributes logonAttributes = null;
		MailUploadVO mailUploadVO = null;
		try {
			logonAttributes = (LogonAttributes) getLogonAttributesVO();
		} catch (SystemException e) {

			log.log(Log.FINE, "SystemException Caught");

		}
		splitData = data.split(";",-1);
		for (int k = 0; k < splitData.length - 1; k++) {
			log.log(Log.INFO, "Splited data");
			log.log(Log.INFO, " ", splitData[k]);
		}
		mailUploadVO = new MailUploadVO();

		mailUploadVO.setScanType(splitData[0]);
		mailUploadVO.setMailCompanyCode(splitData[1]);
		mailUploadVO.setCompanyCode(logonAttributes.getCompanyCode());
		if(splitData[2] != null && !"null".equals(splitData[2])
				&& splitData[2].trim().length() > 0) {
		mailUploadVO.setCarrierCode(splitData[2]);
		}
		mailUploadVO.setFlightNumber(splitData[3]);
		if (splitData[4] != null && splitData[4].trim().length() > 0) {
		LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false);
		flightDate.setDate(splitData[4]);
		mailUploadVO.setFlightDate(flightDate);
		}

		if (splitData[5] != null && !"null".equals(splitData[5]) && splitData[5].trim().length() > 0 ){
			mailUploadVO.setContainerPOU(splitData[5]); 
			mailUploadVO.setToPOU(splitData[5]);
		}
		if (splitData[9] != null && !"null".equals(splitData[9]) && splitData[9].trim().length() > 0 ){
			mailUploadVO.setToDestination(splitData[9]);
		}
		
		if ((splitData[5] == null || "null".equals(splitData[5])||   splitData[5].trim().length() ==0) && (splitData[9] != null && !"null".equals(splitData[9]) && splitData[9].trim().length() > 0)  ){
			mailUploadVO.setContainerPOU(splitData[9]); 
			mailUploadVO.setToPOU(splitData[9]);
		}
		if ((splitData[9] == null || "null".equals(splitData[9 ])|| splitData[9].trim().length() ==0) && (splitData[5] != null && !"null".equals(splitData[5]) && splitData[5].trim().length() > 0)  ){
			mailUploadVO.setToDestination(splitData[5]);
		}
		
		if(splitData[3] == null || "null".equals(splitData[3]) || splitData[3].trim().length() == 0 )
		{
			mailUploadVO.setContainerPOU(mailUploadVO.getToDestination()); 
			mailUploadVO.setToPOU(mailUploadVO.getToDestination());
		}
		
				
	
		if(splitData[6] != null && !"null".equals(splitData[6])
				&& splitData[6].trim().length() > 0) {
		mailUploadVO.setContainerNumber(splitData[6]);
		}
		mailUploadVO.setContainerType(splitData[7]);

			//mailUploadVO.setCompanyCode(splitData[8]);
        boolean androidFlagForInbound=true; 
		if(splitData.length>48){
			if( splitData[49] !=null && splitData[49].trim().length() > 0 && ANDROID_FLAG.equals(splitData[49])){
				androidFlagForInbound=false;
				mailUploadVO.setAndroidFlag(MailConstantsVO.FLAG_YES);
			}
			
		}
		if(splitData[9] != null && !"null".equals(splitData[9])
				&& splitData[9].trim().length() > 0) {  
		mailUploadVO.setDestination(splitData[9]);
		}
		if (splitData[11] != null && splitData[11].trim().length() > 0) {
			mailUploadVO.setContainerPol(splitData[11]);
			} 
		
		mailUploadVO.setRemarks(splitData[12]);
		mailUploadVO.setMailTag(splitData[13]);	  
        //Added as part of IASCB-36005
		LocalDate scanDate = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
		mailUploadVO.setDateTime(scanDate.toDisplayFormat());
		mailUploadVO.setDamageCode(splitData[15]);
		mailUploadVO.setDamageRemarks(splitData[16]);
		mailUploadVO.setOffloadReason(splitData[17]);
		if (splitData[18] != null && splitData[18].trim().length() > 0) {
		mailUploadVO.setReturnCode(splitData[18]);
		}
		if (mailUploadVO.getScanType().equals(
				MailConstantsVO.MAIL_STATUS_RETURNED)
				&& "N".equals(splitData[18])) {

			mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_DAMAGED);

		}

		mailUploadVO.setToContainer(splitData[19]);

		mailUploadVO.setToCarrierCode(splitData[20]);
		mailUploadVO.setToFlightNumber(splitData[21]);
		if (splitData[22] != null && splitData[22].trim().length() > 0) {
		LocalDate toFlightDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false);
		toFlightDate.setDate(splitData[22]);

		mailUploadVO.setToFlightDate(toFlightDate);
		}
		if (splitData[5] != null && splitData[5].trim().length() > 0) {
		mailUploadVO.setToPOU(splitData[5]);
		mailUploadVO.setToDestination(splitData[5]);
		} else if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailUploadVO
				.getScanType())) {
			mailUploadVO.setToPOU(splitData[23]);
			mailUploadVO.setToDestination(splitData[24]);

		} else {
			if (splitData[13].trim().length() == 29) {
			mailUploadVO.setToPOU(splitData[13].substring(8, 11));
			mailUploadVO.setToDestination(splitData[13].substring(8, 11));
		}
		}
		if (mailUploadVO.getMailTag().trim().length() == 29) {
		mailUploadVO.setOrginOE(mailUploadVO.getMailTag().substring(0, 6));
			mailUploadVO.setDestinationOE(mailUploadVO.getMailTag().substring(
					6, 12));
		mailUploadVO.setCategory(splitData[13].substring(12, 13));
		mailUploadVO.setSubClass(splitData[13].substring(13, 15));

			mailUploadVO.setYear(Integer.parseInt(splitData[13].substring(15,
					16)));
		}

		if (splitData[30] != null && splitData[30].trim().length() > 0) {
		mailUploadVO.setDsn(splitData[30]);
		}
		mailUploadVO.setConsignmentDocumentNumber(splitData[31]);
		mailUploadVO.setPaCode(splitData[32]);
		if (splitData[33] != null && splitData[33].trim().length() > 0) {
		mailUploadVO.setTotalBag(Integer.parseInt(splitData[33]));
		}
		if (splitData[34] != null && splitData[34].trim().length() > 0) {
		//mailUploadVO.setTotalWeight(Double.parseDouble(splitData[34]));
			mailUploadVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(splitData[34])));//added by A-7371
		}
		if (splitData[35] != null && splitData[35].trim().length() > 0) {
		mailUploadVO.setBags(Integer.parseInt(splitData[35]));
		}

		if (splitData[36] != null && splitData[36].trim().length() > 0) {
		//mailUploadVO.setWeight(Double.parseDouble(splitData[36]));
			mailUploadVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(splitData[36])));//added by A-7371
		}
		if (splitData[37] != null && splitData[37].trim().length() > 0) {
		mailUploadVO.setIntact(Boolean.parseBoolean(splitData[37]));
		}
		if (splitData[38] != null && splitData[38].trim().length() > 0) {
		mailUploadVO.setSerialNumber(Integer.parseInt(splitData[38]));
		}
		mailUploadVO.setCirCode(splitData[39]);
		if (splitData[40] != null && splitData[40].trim().length() > 0) {
			if ("Y".equals(splitData[40])
					|| MailConstantsVO.MAIL_STATUS_DELIVERED
							.equals(splitData[0])) {
		   mailUploadVO.setDeliverd(true);
			} else {
			mailUploadVO.setDeliverd(false);
		   }
		}
		//Added by A-7929 as part of IASCB-35577
		
		if (splitData[41] != null && splitData[41].trim().length() > 0) {
		   mailUploadVO.setScanUser(splitData[41]);	
		}
		
		// Added as part of Bug ICRD-94100 starts
		if (splitData[43].length() > 2) {
			if (splitData[43].indexOf("*") > 0) {
				String transferCarrierCode = splitData[43].substring(0,
						splitData[43].indexOf("*"));
				mailUploadVO.setFromCarrierCode(transferCarrierCode);
			}
			else {
				mailUploadVO.setFromCarrierCode(splitData[43]);
			}

		} else {
			mailUploadVO.setFromCarrierCode(splitData[43]);
		}
		if( splitData.length>47)
		{
			if(splitData[46] !=null && splitData[46].trim().length() > 0)
			mailUploadVO.setOverrideValidation(splitData[46]);
		
		if(splitData[47] !=null && splitData[48].trim().length() > 0)
			mailUploadVO.setTransferFrmFlightNum(splitData[47]);//Added by a-7871 for ICRD-240184
		if(splitData[48] !=null && splitData[48].trim().length() > 0)
		{
			LocalDate transferFlightDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			transferFlightDate.setDate(splitData[48]);
			mailUploadVO.setTransferFrmFlightDate(transferFlightDate);
		}
		}
		if(splitData.length>45 && splitData[45] != null && splitData[45].trim().length() >= 3){
			mailUploadVO.setFromPol(splitData[45].substring(0,3));
		}
		// Added as part of Bug ICRD-94100 ends
		
		if(splitData.length>50 && splitData[50] != null){
			mailUploadVO.setMailSource(MailConstantsVO.SCAN+":"+splitData[50]);
		}else{
			mailUploadVO.setMailSource(MailConstantsVO.SCAN);
		}
		if(mailUploadVO.getMailSource().contains("UTIL")&& splitData[14]!=null){
			scanDate.setDateAndTime(splitData[14],false);
			mailUploadVO.setDateTime(scanDate.toDisplayFormat());
		}
		LocalDate deviceDate = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
		
		if(splitData[14]!=null &&splitData[14].trim().length() > 0){
			deviceDate.setDateAndTime(splitData[14],false);
			mailUploadVO.setDeviceDateAndTime(deviceDate);
		}
		return mailUploadVO;
	}
	/**
	 * @author A-5525
	 * @param businessException
	 * @return ErrorDetailType
	 */
	private ErrorDetailType createErrorDetailType(WSBusinessException wsBusinessException){
		wsBusinessException.getMessage();
		return constructErrorDetailType(wsBusinessException.getErrors().iterator().next());
	}
	/**
	 * @author A-5525
	 * @param systemException
	 * @return ErrorDetailType
	 */

	private ErrorDetailType createErrorDetailType(SystemException systemException) {
		systemException.getMessage();
		return constructErrorDetailType(systemException.getErrors().iterator().next());
	}
	/**
	 * @author A-5525
	 * @param errorVO
	 * @return ErrorDetailType
	 */
	private ErrorDetailType constructErrorDetailType(ErrorVO errorVO) {
		ErrorDetailType errorDetailType = new ErrorDetailType();
		if (errorVO.getErrorCode() != null && errorVO.getErrorCode().length() > 0) {
			errorDetailType.setErrorCode(errorVO.getErrorCode());
			if(errorVO.getErrorDescription()==null){
				errorDetailType.setErrorDescription("");
			}else{
				errorDetailType.setErrorDescription(errorVO.getErrorDescription());
			}

			if (errorVO.getErrorData() != null && errorVO.getErrorData().length > 0
					&& errorVO.getErrorData()[0] != null) {
				errorDetailType.setErrorData(errorVO.getErrorData()[0].toString());
			}
		} else {
			errorDetailType.setErrorCode(RUNTIME_EXCEPTION);
		}
		return errorDetailType;
	}
	/**
	 *
	 * @param scannedMailType
	 * @return
	 */
	public boolean checkForOnlineDevice(MailScannedType scannedMailType){
		boolean isTrue = false;
		String booleanString = "";
		List<String> mailScannedString = scannedMailType.getMaildetails();
		String[] mailArray = mailScannedString
		.toArray(new String[mailScannedString.size()]);
		String scannedString = null;
		for (int i = 0; i <= mailArray.length - 1; i++) {
			scannedString = mailArray[i];
			if(scannedString.startsWith("mailtracking.defaults") || scannedString.startsWith("mail.operations")){
				String[] splitHeaderData = null;
				splitHeaderData = scannedString.split(";");
				booleanString = splitHeaderData[9];
				if("TRUE".equalsIgnoreCase(booleanString))
					isTrue = true;
					}
			}
		return isTrue;
	}

	/**
	 * @param errorDetailType
	 * @param e
	 * @return
	 */
	private ErrorDetailType populateErrorDetailType(final ErrorDetailType errorDetailType, final WSBusinessException e) {
		ArrayList<ErrorVO> errorVos = (ArrayList<ErrorVO>) e.getErrors();
		for(ErrorVO errorVo : errorVos){
			if(errorVo != null){
				errorDetailType.setErrorCode(errorVo.getErrorCode());
				errorDetailType.setErrorDescription(errorVo.getErrorDescription());
			}
		}
		return errorDetailType;
	}
	
	private List<MailErrorType> populateMailErrorType(final WSBusinessException e, MailUploadVO mail) {
		ErrorDetailType errorDetailType = new ErrorDetailType();
		MailErrorType mailErrorType = new MailErrorType();
		List<MailErrorType> mailErrorTypes = new ArrayList<MailErrorType>();
		ArrayList<ErrorVO> errorVos = (ArrayList<ErrorVO>) e.getErrors();
		for(ErrorVO errorVo : errorVos){
			if(errorVo != null){
				errorDetailType.setErrorCode(errorVo.getErrorCode());
				errorDetailType.setErrorDescription(errorVo.getConsoleMessage());
				errorDetailType.setErrorData(mail.getMailTag());
				if(errorVo.getErrorDisplayType() != null){
				errorDetailType.setErrorType(errorVo.getErrorDisplayType().toString());
				}
				else
				{
					errorDetailType.setErrorType("Error");
				}
			}
		}
		mailErrorType.setErrorDetails(errorDetailType);
		mailErrorTypes.add(mailErrorType);
		return mailErrorTypes;
	}
	

	
	private List<MailErrorType> populateMailErrorType(final WSBusinessException e, ScannedMailDetailsVO scannedMailDetailsVO) {
		ErrorDetailType errorDetailType = new ErrorDetailType();
		ArrayList<ErrorVO> errorVos = (ArrayList<ErrorVO>) e.getErrors();
		MailErrorType mailErrorType = new MailErrorType();
		List<MailErrorType> mailErrorTypes = new ArrayList<MailErrorType>();
		String errString = null;
		for(ErrorVO errorVo : errorVos){
			if(errorVo != null){
				errorDetailType.setErrorCode(errorVo.getErrorCode());
				errorDetailType.setErrorDescription(errorVo.getErrorDescription());
				Object[] errorData= errorVo.getErrorData();
				mailErrorType.setMalidr((String) errorData[0]);
				errorDetailType.setErrorType(errorVo.getErrorDisplayType().toString());
			}
		}
		
		if (scannedMailDetailsVO != null) {
			mailErrorType.setFlightDate(scannedMailDetailsVO.getFlightDate().toString());
			mailErrorType.setConnum(scannedMailDetailsVO.getContainerNumber());
			mailErrorType.setFltnum(scannedMailDetailsVO.getFlightNumber());
			mailErrorType.setMalidr(getMailIdr(scannedMailDetailsVO));
			mailErrorType.setUploadStatus(scannedMailDetailsVO.getStatus());
		}
		errorDetailType.setErrorDescription(errString);
		mailErrorType.setErrorDetails(errorDetailType);
		mailErrorTypes.add(mailErrorType);
		return mailErrorTypes;
	}
	
	/**
	 * @param scannedMailDetailsVO
	 * @return 
	 */
	private List<MailErrorType> populateMailErrorType(ScannedMailDetailsVO scannedMailDetailsVO) {
	//	MailErrorType mailErrorType = new MailErrorType();
		List<MailErrorType> mailErrorTypes = new ArrayList<MailErrorType>();
		List<ErrorDetailType> errorDetailTypes = new ArrayList<ErrorDetailType>();
		ArrayList<MailbagVO> errorVo = (ArrayList<MailbagVO>) scannedMailDetailsVO.getErrorMailDetails();
	/*	if(errorVo.size() > 0 && errorVo.get(0) != null){
			errorDetailType.setErrorCode(errorVo.get(0).getErrorCode());
		}*/
		if(null!=scannedMailDetailsVO.getErrorMailDetails()
				&&scannedMailDetailsVO.getErrorMailDetails().size()>0){
		for(MailbagVO voError : scannedMailDetailsVO.getErrorMailDetails()){
			MailErrorType mailErrorType = new MailErrorType();
			ErrorDetailType errorDetailType = new ErrorDetailType();
			errorDetailType.setErrorDescription(voError.getErrorDescription());
			errorDetailType.setErrorType(voError.getErrorType());
			errorDetailType.setErrorData("");
			if("E".equals(voError.getErrorType()) || "Error".equals(voError.getErrorType())){
				errorDetailType.setErrorCode(voError.getErrorCode());
			}else{
			errorDetailType.setErrorCode(populateOverrideWarningFlag(voError.getErrorCode()));
			}
			errorDetailTypes.add(errorDetailType);
		
		
		if (scannedMailDetailsVO != null) { //Changed by A-8164 ICRD-315657
			String pols="";
			String pous="";
			//mailErrorType.setFlightDate(scannedMailDetailsVO.getFlightDate().toString());
			mailErrorType.setErrorDetails(errorDetailType);
			mailErrorType.setFltnum(scannedMailDetailsVO.getFlightNumber());
			if(null!=scannedMailDetailsVO.getMailDetails())
				mailErrorType.setMalidr(getMailIdr(scannedMailDetailsVO));
			if(null!=scannedMailDetailsVO.getFlightDate())
				mailErrorType.setFlightDate(scannedMailDetailsVO.getFlightDate().toDisplayDateOnlyFormat());
			mailErrorType.setFltcarcod(scannedMailDetailsVO.getCarrierCode());
			mailErrorType.setDestination(scannedMailDetailsVO.getDestination());
			if(scannedMailDetailsVO.getPols()!=null
					&&scannedMailDetailsVO.getPols().size()>0){
					for(String pol:scannedMailDetailsVO.getPols()){
						pols=new StringBuffer().append(pols).append(pol).append("-").toString();
					}
					mailErrorType.setPols(pols);
			}
			if(scannedMailDetailsVO.getPous()!=null
					&&scannedMailDetailsVO.getPous().size()>0){
					for(String pou:scannedMailDetailsVO.getPous()){
						pous=new StringBuffer().append(pous).append(pou).append("-").toString(); 
					}
					mailErrorType.setPous(pous);
			}
			mailErrorType.setConnum(scannedMailDetailsVO.getContainerNumber());
			mailErrorType.setPou(scannedMailDetailsVO.getPou());
			mailErrorType.setPol(scannedMailDetailsVO.getPol());
			//Added as part of ICRD-340690 starts
			mailErrorType.setTotalMailbagCount(String.valueOf(scannedMailDetailsVO.getTotalMailbagCount()));
			mailErrorType.setTotalMailbagWeight(
					scannedMailDetailsVO.getTotalMailbagWeight() != null ?
							String.valueOf(scannedMailDetailsVO.getTotalMailbagWeight().getRoundedDisplayValue()) : "0.0");
			//Added as part of ICRD-340690 ends
			/*mailErrorType.setConnum(scannedMailDetailsVO.getContainerNumber());
			mailErrorType.setFltnum(scannedMailDetailsVO.getFlightNumber());
			mailErrorType.setMalidr(getMailIdr(scannedMailDetailsVO));
			mailErrorType.setUploadStatus(scannedMailDetailsVO.getStatus());*/
		}
		mailErrorTypes.add(mailErrorType); 
		}
		}else{ //Added by A-8164 for ICRD-315657
			MailErrorType mailErrorType = new MailErrorType(); 
		if (scannedMailDetailsVO != null) {
				String pols="";
				String pous="";
			//mailErrorType.setFlightDate(scannedMailDetailsVO.getFlightDate().toString());
			mailErrorType.setFltnum(scannedMailDetailsVO.getFlightNumber());

				mailErrorType.setFlightDate(scannedMailDetailsVO.getFlightDate()!=null?
						scannedMailDetailsVO.getFlightDate().toDisplayDateOnlyFormat():null);
				mailErrorType.setFltcarcod(scannedMailDetailsVO.getCarrierCode());
				mailErrorType.setDestination(scannedMailDetailsVO.getDestination());
				if(scannedMailDetailsVO.getPols()!=null
						&&scannedMailDetailsVO.getPols().size()>0){
						for(String pol:scannedMailDetailsVO.getPols()){
							pols=new StringBuffer().append(pols).append(pol).append("-").toString();
						}
						mailErrorType.setPols(pols);
				}
				if(scannedMailDetailsVO.getPous()!=null
						&&scannedMailDetailsVO.getPous().size()>0){
						for(String pou:scannedMailDetailsVO.getPous()){
							pous=new StringBuffer().append(pous).append(pou).append("-").toString();
						}
						mailErrorType.setPous(pous);
				}
				mailErrorType.setConnum(scannedMailDetailsVO.getContainerNumber());
				mailErrorType.setPou(scannedMailDetailsVO.getPou());
				mailErrorType.setPol(scannedMailDetailsVO.getPol());
				//Added as part of ICRD-340690 starts
				mailErrorType.setTotalMailbagCount(String.valueOf(scannedMailDetailsVO.getTotalMailbagCount()));
				mailErrorType.setTotalMailbagWeight(
					scannedMailDetailsVO.getTotalMailbagWeight() != null ?
							String.valueOf(scannedMailDetailsVO.getTotalMailbagWeight().getRoundedDisplayValue()) : "0.0");
				//Added as part of ICRD-340690 ends
				mailErrorType.setPoaFlag(scannedMailDetailsVO.getIsContainerPabuilt());
				mailErrorType.setContainerType(scannedMailDetailsVO.getContainerType());
				ErrorDetailType errorDetailType = new ErrorDetailType();
				errorDetailType.setErrorCode("SUCCESS");
				mailErrorType.setErrorDetails(errorDetailType); 
			/*mailErrorType.setConnum(scannedMailDetailsVO.getContainerNumber());
			mailErrorType.setFltnum(scannedMailDetailsVO.getFlightNumber());
			mailErrorType.setMalidr(getMailIdr(scannedMailDetailsVO));
			mailErrorType.setUploadStatus(scannedMailDetailsVO.getStatus());*/
		}
		
		
		mailErrorTypes.add(mailErrorType);
		}
		
		return mailErrorTypes;
	}
	
	/**
	 * @param scannedMailDetailsVO
	 * @return
	 */
	private String getMailIdr(ScannedMailDetailsVO scannedMailDetailsVO) {
		String mailId = null ;
		//for validate mailbag only 1 mailbag will exist in a single request
		for (MailbagVO mailbagVO: scannedMailDetailsVO.getMailDetails()){
			mailId= mailbagVO.getMailbagId();
		}
		return mailId;
	}

	/**
	 * @author A-8236
	 * @param scannedMailType
	 * @return
	 */
	public List<MailErrorType> validateMailBagDetails(MailScannedType scannedMailType) {
		Collection<MailUploadVO> mailuploadVos = null;
		List<MailErrorType> mailErrorTypes = new ArrayList<MailErrorType>();
		List<String> mailScannedString = scannedMailType.getMaildetails();
		ErrorDetailType errorDetailType = new ErrorDetailType();
		MailErrorType mailErrorType = new MailErrorType();
		errorDetailType.setErrorCode("ok");
		mailErrorType.setErrorDetails(errorDetailType);
		mailErrorTypes.add(mailErrorType);
		log.log(Log.FINE, "sacnning port",
				scannedMailType.getScanningPort());

		String[] mailArray = mailScannedString
				.toArray(new String[mailScannedString.size()]);
		mailuploadVos = constructMailUploadVO(mailArray);
		if(mailuploadVos.size()>1)//offline case
		{
			errorDetailType.setErrorCode("ok");
			errorDetailType.setErrorDescription("Offline");
			mailErrorType.setErrorDetails(errorDetailType);
			mailErrorTypes.add(mailErrorType);
		}
		else
		{
			
		for (MailUploadVO mail : mailuploadVos) {
			mail.setScannedPort(scannedMailType.getScanningPort());
			ScannedMailDetailsVO scannedMailDetailsVO = null;
			try {
				scannedMailDetailsVO = despatchRequest(
						"validateMailBagDetails", mail);
				log.log(Log.INFO, "ScannedMailDetailsVO*****",
						scannedMailDetailsVO);
				if(scannedMailDetailsVO.getErrorMailDetails().size()>0 && scannedMailDetailsVO.getErrorMailDetails()!=null)
				{
					
					mailErrorTypes=	populateMailErrorType(scannedMailDetailsVO);
					
				}
					/*else
					{
						errorDetailType.setErrorCode("ok");
						mailErrorType.setErrorDetails(errorDetailType);//no errors/warnings
						mailErrorTypes.add(mailErrorType);
					}*/
				//mailErrorTypes = populateMailErrorType(scannedMailDetailsVO);
			} catch (WSBusinessException e) {
				mailErrorTypes = populateMailErrorType(e,mail);
				log.log(Log.FINE, "WSBusinessException Caught");
			} catch (SystemException e) {
				log.log(Log.FINE, "SystemException Caught");
			}
		}
			}
		
		return mailErrorTypes;
		
	}
	

	

	/**
	 * @author A-8236
	 * @param scannedMailType
	 * @return
	 */
	public List<MailErrorType> saveMailUploadDetailsForAndroid(MailScannedType scannedMailType) {

		Collection<MailUploadVO> mailuploadVos = null;
		List<String> mailScannedString = scannedMailType.getMaildetails();
		log.log(Log.FINE, "sacnning port", 
				scannedMailType.getScanningPort());
		Collection<MailScanDetailVO> mailScanDetailVOs=null;
		/*
		 * LogonAttributes logonAttributes = null; try { logonAttributes =
		 * ContextUtils.getSecurityContext() .getLogonAttributesVO(); } catch
		 * (SystemException e) { e.printStackTrace(); }
		 */
		String[] mailArray = mailScannedString
				.toArray(new String[mailScannedString.size()]);
		mailuploadVos = constructMailUploadVO(mailArray);
		List<MailErrorType> mailErrorTypes = new ArrayList<MailErrorType>();
		//List<ScannedMailDetailsVO> scannedMailDetailsVOs = new ArrayList<ScannedMailDetailsVO>();
		ScannedMailDetailsVO scannedMailDetailsVO = null;
		for (MailUploadVO mail : mailuploadVos) {

			try {
				scannedMailDetailsVO = despatchRequest(
						"saveMailUploadDetailsForAndroid", mail,scannedMailType.getScanningPort());
				log.log(Log.INFO, "ScannedMailDetailsVO*****",
						scannedMailDetailsVO);
				/*if(scannedMailDetailsVO.getErrorMailDetails().size()>0 && scannedMailDetailsVO.getErrorMailDetails()!=null)
				{
					
					mailErrorTypes=	populateMailErrorType(scannedMailDetailsVO);
					
				}
				else
				{*/ //warnings in save req occurs only in offline
				
				//Added by A-8164 for ICRD-330543 for stamping on to
				//error handling screen with ARR as scan type.(Autoarrival case without manifest details)
				if(scannedMailDetailsVO.isArrivalException()||scannedMailDetailsVO.isForceAccepted()){  
					MailbagVO mailbagVO=null;
					if(scannedMailDetailsVO.getMailDetails()!=null&&scannedMailDetailsVO.getMailDetails().size()>0){
						mailbagVO=scannedMailDetailsVO.getMailDetails().iterator().next();
					}
				  if(scannedMailDetailsVO.isForceAccepted()){
						mail.setForceAccepted(true);
						mail.setErrorDescription(scannedMailDetailsVO.getErrorDescription());
						mail.setErrorCode(scannedMailDetailsVO.getErrorCode());	
					} 
				  else{
					  mail.setArrivalException(true);
					if(mailbagVO.isDeliveryNeeded()){
						mail.setProcessPointBeforeArrival(MailConstantsVO.MAIL_STATUS_DELIVERED);
						mail.setDeliverd(false);
						mail.setDeliverFlag("N");
					}
					else
						mail.setProcessPointBeforeArrival(scannedMailDetailsVO.getProcessPoint());
					mail.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);
					mail.setScanType(MailConstantsVO.MAIL_STATUS_ARRIVED); 
				  }
					scannedMailDetailsVO = despatchRequest(
							"saveMailUploadDetailsForAndroid", mail,scannedMailType.getScanningPort());
				}
				
				MailErrorType mailErrorType = new MailErrorType();
				ErrorDetailType errorDetailType = new ErrorDetailType();
				errorDetailType.setErrorCode("ok");
				errorDetailType.setErrorDescription("noerror");
				errorDetailType.setErrorData(mail.getMailTag());
				mailErrorType.setErrorDetails(errorDetailType);
				mailErrorTypes.add(mailErrorType); 
				//}
				if(scannedMailDetailsVO.getErrorMailDetails()!=null &&!scannedMailDetailsVO.getErrorMailDetails().isEmpty())
				{					
					mailErrorTypes=	populateMailErrorType(scannedMailDetailsVO);		
				}
			} catch (WSBusinessException e) {
				mailErrorTypes = populateMailErrorType(e, mail); 
				log.log(Log.FINE, "WSBusinessException Caught");
			} catch (SystemException e) {
				log.log(Log.FINE, "SystemException Caught");
			}
			 try {
					mailScanDetailVOs = createMailScanDetailVOs(scannedMailType,
							"Y");
					if(mailScanDetailVOs!=null && mailScanDetailVOs.size()>0){
		 		  despatchRequest("saveMailScannedDetails", mailScanDetailVOs);
					}
				} catch (WSBusinessException e1) {
					log.log(Log.FINE, "Scanned Mails",mailScanDetailVOs);
					//e1.printStackTrace();
				} catch (SystemException e1) {
					log.log(Log.FINE, "Scanned Mails",mailScanDetailVOs);
						//e1.printStackTrace();
				}
		
	}
		return mailErrorTypes;

}
/**
 * 
 * 	Method		:	MailTrackingDefaultsServiceImpl.populateOverrideWarningFlag
 *	Added by 	:	A-6245 on 15-Oct-2018
 * 	Used for 	:	Populate warning flag with error codes to override them in
 *					android screen.Only the first warning will be displayed for 
 *					each container,flight,destination.Keyset defined in client side
 *	Parameters	:	@param errorCode
 *	Parameters	:	@return 
 *	Return type	: 	String
 */
	private String populateOverrideWarningFlag(String errorCode) {
		if (errorCode != null) {
			StringBuilder sbul = new StringBuilder(errorCode);
			sbul.append(MailConstantsVO.ARPULD_KEYSEP);
			switch (errorCode) {
			case MailConstantsVO.CONTAINER_POU_DIFFERENT_FROM_DEST:
				errorCode = sbul.append(MailConstantsVO.FLAG_YES).toString();
				break;
			case MailConstantsVO.EMBARGO_VALIDATION:
				errorCode = sbul.append(MailConstantsVO.FLAG_YES).toString();
				break;
			case MailConstantsVO.LAT_VIOLATED_WAR:
				errorCode = sbul.append(MailConstantsVO.FLAG_YES).toString();
				break;
			case MailConstantsVO.NOT_COTERMINUS_AIRPORT:
				errorCode = sbul.append(MailConstantsVO.FLAG_YES).toString();
				break;
			case MailConstantsVO.INVALID_ACCEPTANCE_AIRPORT:
				errorCode = sbul.append(MailConstantsVO.FLAG_YES).toString();
				break;				
			default:
				errorCode = sbul.append(MailConstantsVO.FLAG_NO).toString();
				break;
			}
		}
		return errorCode;
	}
	/**
	 * 
	 * 	Method		:	MailTrackingDefaultsServiceImpl.validateFlightAndContainer
	 *	Added by 	:	A-8164 on 19-Feb-2019
	 * 	Used for 	:	validating flight and container
	 *	Parameters	:	@param validateContainerRequestType
	 *	Parameters	:	@return 
	 *	Return type	: 	MailErrorType
	 */
	public MailErrorType validateFlightAndContainer(ValidateContainerRequestType validateContainerRequestType){
		MailUploadVO mailUploadVO=null;
		ScannedMailDetailsVO scannedMailDetailsVO=null;    
		MailErrorType mailErrorType=new MailErrorType();
		List<MailErrorType> mailErrorTypes = new ArrayList<MailErrorType>(); 
		mailUploadVO=populateMailUploadVo(validateContainerRequestType);
		try {
			scannedMailDetailsVO  = despatchRequest(
					"validateFlightAndContainer", mailUploadVO);
			log.log(Log.INFO, "ScannedMailDetailsVO*****",
					scannedMailDetailsVO);
			if(scannedMailDetailsVO!=null){	 
				mailErrorTypes=	populateMailErrorType(scannedMailDetailsVO);	
			}
		} catch (WSBusinessException exception) {
			mailErrorTypes = populateMailErrorType(exception,mailUploadVO);
			log.log(Log.FINE, "WSBusinessException Caught");
		} catch (SystemException e) {
			log.log(Log.FINE, "SystemException Caught");
		}
		if(mailErrorTypes.size()>0){
			mailErrorType=mailErrorTypes.get(0);
		}
		return mailErrorType;
	}
	/**
	 * 
	 * 	Method		:	MailTrackingDefaultsServiceImpl.populateMailUploadVo
	 *	Added by 	:	A-8164 on 19-Feb-2019
	 * 	Used for 	:	Populating MailUploadVO
	 *	Parameters	:	@param validateContainerRequestType
	 *	Parameters	:	@return 
	 *	Return type	: 	MailUploadVO
	 */
	private MailUploadVO populateMailUploadVo(ValidateContainerRequestType validateContainerRequestType) {
		MailUploadVO mailUploadVO=new MailUploadVO();
		if(validateContainerRequestType.getCompanyCode()!=null)
				mailUploadVO.setCompanyCode(validateContainerRequestType.getCompanyCode()); 
		if(validateContainerRequestType.getContainerNumber()!=null
				&&validateContainerRequestType.getContainerNumber().length()>=1) 
			mailUploadVO.setContainerNumber(validateContainerRequestType.getContainerNumber());
		if(validateContainerRequestType.getContainerType()!=null)
			mailUploadVO.setContainerType(validateContainerRequestType.getContainerType());
		if(validateContainerRequestType.getFlightCarrierCode()!=null)
			mailUploadVO.setCarrierCode(validateContainerRequestType.getFlightCarrierCode());
		if(validateContainerRequestType.getFlightDate()!=null)
			mailUploadVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false).setDate(validateContainerRequestType.getFlightDate().split(" ")[0]));
		if(validateContainerRequestType.getFlightNumber()!=null&&validateContainerRequestType.getFlightNumber().length()>1)
			mailUploadVO.setFlightNumber(validateContainerRequestType.getFlightNumber());
		if(validateContainerRequestType.getOperationType()!=null)
			mailUploadVO.setOperationType(validateContainerRequestType.getOperationType());
		if(validateContainerRequestType.getHasWarningShown()!=null)
			mailUploadVO.setWarningFlag(validateContainerRequestType.getHasWarningShown());
		if(validateContainerRequestType.getAirportCode()!=null)
			mailUploadVO.setScannedPort(validateContainerRequestType.getAirportCode());
		mailUploadVO.setOverrideULDVal(validateContainerRequestType.getOverrideULDVal());
		return mailUploadVO;
	}
}
