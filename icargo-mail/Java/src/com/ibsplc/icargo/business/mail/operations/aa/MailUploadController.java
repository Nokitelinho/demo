/**
 *MailUploadController.java Created on July 06, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */

package com.ibsplc.icargo.business.mail.operations.aa;

import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.DESTN_FLT;

import com.ibsplc.icargo.business.mail.operations.ForceAcceptanceException;
import com.ibsplc.icargo.business.mail.operations.MailAcceptance;

/**
 * @author A-7871
 *
 */




import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.MailMLDBusniessException;
import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.MailbagPK;
//import com.ibsplc.icargo.business.mail.operations.OfficeOfExchange;
import com.ibsplc.icargo.business.mail.operations.errorhandling.MailHHTBusniessException;
//import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class MailUploadController extends
com.ibsplc.icargo.business.mail.operations.MailUploadController {
private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");




/**
 * @author A-7871
 * @param mailUploadVOs
 * @return
 * @throws MailMLDBusniessException
 * @throws MailHHTBusniessException
 * @throws SystemException 
 */
public ScannedMailDetailsVO doSpecificValidations(ScannedMailDetailsVO scannedMailDetailsVO)
		throws MailMLDBusniessException, MailHHTBusniessException, SystemException {
	
	OfficeOfExchangeVO originOfficeOfExchangeVO;
	OfficeOfExchangeVO destOfficeOfExchangeVO;
	boolean coTerminusDelivery;
	String poaCode=null;
	String destinationPort=null; 
	 String airportCode = null;
	String isCoterminusConfigured = findSystemParameterValue(MailConstantsVO.IS_COTERMINUS_CONFIGURED);//Added by a-7871 for ICRD-290687
	log.entering("MailUploadController", "doSpecificValidations");
	Mailbag mailBag = null;
	LogonAttributes logonAttributes = null;
	if (scannedMailDetailsVO.getMailDetails() != null
			&& scannedMailDetailsVO.getMailDetails().size() > 0) {
		try {
			logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		} catch (SystemException e) {
			log.log(Log.SEVERE, "System Exception Caught");
		}
		for (MailbagVO scannedMailbagVO : scannedMailDetailsVO.getMailDetails()) { 
			  
			
			LocalDate dspDate = new LocalDate(scannedMailbagVO.getScannedPort(),Location.ARP,true);			
							
			MailbagPK mailbagPk = new MailbagPK();
			mailbagPk.setCompanyCode(scannedMailbagVO.getCompanyCode());
			mailbagPk.setMailSequenceNumber(Mailbag.findMailBagSequenceNumberFromMailIdr(scannedMailbagVO.getMailbagId(), scannedMailbagVO.getCompanyCode()));
			try {
				mailBag = Mailbag.find(mailbagPk); 
			} catch (SystemException e) {
				log.log(Log.FINE, "SystemException Caught");
				//constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
			} catch (FinderException e) {
				log.log(Log.SEVERE, "Finder Exception Caught");
			}
			catch(Exception e){
				log.log(Log.FINE, e.getMessage());
				//constructAndRaiseException(e.getMessage(), e.getMessage(), scannedMailDetailsVO);
			}
		
			 
	
			
			//Modified by U-1532 Exchange Office validation
				if (mailBag != null) {
					dspDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,mailBag.getDespatchDate(),false);
					
					if (mailBag.getPaCode() != null) {
						poaCode = mailBag.getPaCode();
					}
					if (scannedMailbagVO.getDestination() == null || scannedMailbagVO.getDestination().isEmpty()) {
						scannedMailbagVO.setDestination(mailBag.getDestination());
				}
			} else if (mailBag == null || mailBag.getOrigin() == null || mailBag.getOrigin().isEmpty()
					|| mailBag.getPaCode() == null || mailBag.getPaCode().isEmpty()
					|| mailBag.getDestination() == null || mailBag.getDestination().isEmpty()) {

				originOfficeOfExchangeVO = new MailController()
						.validateOfficeOfExchange(scannedMailbagVO.getCompanyCode(), scannedMailbagVO.getOoe());
				if (originOfficeOfExchangeVO != null) {
					poaCode = originOfficeOfExchangeVO.getPoaCode();
					if (originOfficeOfExchangeVO != null && originOfficeOfExchangeVO.getAirportCode() == null) {
						String orginOfficeExchange = null;
						orginOfficeExchange = originOfficeOfExchangeVO.getCode();
						airportCode = findNearestAirportOfCity(scannedMailbagVO.getCompanyCode(),
								orginOfficeExchange);
						originOfficeOfExchangeVO.setAirportCode(airportCode);
					}

				}                	
				if (scannedMailbagVO.getDestination() == null || scannedMailbagVO.getDestination().isEmpty()) {
					destOfficeOfExchangeVO = new MailController()
							.validateOfficeOfExchange(scannedMailbagVO.getCompanyCode(), scannedMailbagVO.getDoe());
					if (destOfficeOfExchangeVO != null && destOfficeOfExchangeVO.getAirportCode() == null) {
						String destOfficeOfExchange = destOfficeOfExchangeVO.getCode();
						destinationPort = findNearestAirportOfCity(scannedMailbagVO.getCompanyCode(),
								destOfficeOfExchange);
						scannedMailbagVO.setDestination(destinationPort);
						destOfficeOfExchangeVO.setAirportCode(destinationPort);
						}
					}
				}
				else{
			//No change
		}
			
			
			
			 //uncommented as part of IASCB-57929 by A-8353 starts
			if(("DLV".equals(scannedMailDetailsVO.getProcessPoint()) || "Y".equals(scannedMailbagVO.getDeliveredFlag())) &&scannedMailbagVO.getDestination()!=null&&scannedMailDetailsVO.getAirportCode()!=null&& (!(scannedMailbagVO.getDestination().equals(scannedMailDetailsVO.getAirportCode()))) && isCoterminusConfigured!=null && "Y".equals(isCoterminusConfigured) )
			{   
				
				coTerminusDelivery= new MailController().validateCoterminusairports(scannedMailbagVO.getDestination(), scannedMailDetailsVO.getAirportCode(),MailConstantsVO.RESDIT_DELIVERED, poaCode,dspDate);
				if(!(coTerminusDelivery    
						||isCommonCityForAirport(scannedMailDetailsVO.getCompanyCode(),scannedMailDetailsVO.getAirportCode(),scannedMailbagVO.getDestination())))//23 or 21 resdit modes
				{  
					scannedMailDetailsVO=constructAndroidException(MailConstantsVO.INVALID_DELIVERY_PORT, MailHHTBusniessException.INVALID_DELIVERY_EXCEPTION, scannedMailDetailsVO);     

				}
					
			}
			
		
	}
	}
	log.exiting("MailUploadController", "doSpecificValidations");
	return scannedMailDetailsVO;
}

/**
 * @param errorCode
 * @param errorDescriptionForHHT
 * @param scannedMailDetailsVO
 * @throws MailMLDBusniessException
 * @throws MailHHTBusniessException
 */
public ScannedMailDetailsVO constructAndroidException(String errorCode, String errorDescriptionForHHT,
		ScannedMailDetailsVO scannedMailDetailsVO) throws MailMLDBusniessException,
		MailHHTBusniessException {
	
	if (errorDescriptionForHHT == null) { 
		errorDescriptionForHHT = "Exception";
	}
	//Added as part of Bug ICRD-153992 by A-5526 ends
	if (scannedMailDetailsVO != null) {
	MailbagVO mailbagVO = new MailbagVO();
	mailbagVO.setErrorCode(errorCode);
	mailbagVO.setErrorType("Warning");
	if(MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerAsSuchArrOrDlvFlag())){
		mailbagVO.setErrorType("E");
	}else if(MailHHTBusniessException.INVALID_OFFICEOFEXCHANGE.equals(errorCode)){ 
		mailbagVO.setErrorType("Error"); 
	}else if(MailConstantsVO.INVALID_DELIVERY_PORT.equals(errorCode)){
		mailbagVO.setErrorType("Error"); 
	}
	mailbagVO.setErrorDescription(errorDescriptionForHHT);
	scannedMailDetailsVO.getErrorMailDetails().add(mailbagVO);
	}
	return scannedMailDetailsVO;
}
	/**
	*@author A-7779
	* @param errorCode
	* @param errorDescriptionForHHT
	* @param scannedMailDetailsVO
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	*/
public void constructAndRaiseException(String errorCode, String errorDescriptionForHHT,
		ScannedMailDetailsVO scannedMailDetailsVO) throws MailMLDBusniessException,
		MailHHTBusniessException {
	if (errorDescriptionForHHT!=null && errorDescriptionForHHT.length() > 90) { 
		errorDescriptionForHHT = errorDescriptionForHHT.substring(0, 90);
	}
	//Added as part of Bug ICRD-153992 by A-5526 starts
	else if (errorDescriptionForHHT == null || errorDescriptionForHHT.isEmpty()) {
		errorDescriptionForHHT = "Exception";
	}
	//Added as part of Bug ICRD-153992 by A-5526 ends
	if (scannedMailDetailsVO != null) {
	scannedMailDetailsVO.setErrorDescription(errorDescriptionForHHT);
	}
	throw new MailHHTBusniessException(errorDescriptionForHHT);
}
/**
 * @author A-8353 for IASCB-45360
 * @param scannedMailDetailsVO
 * @param logonAttributes
 * @throws SystemException 
 * @throws MailHHTBusniessException 
 * @throws MailMLDBusniessException 
 * @throws ForceAcceptanceException 
 */
public void flagResditForAcceptanceInTruck(ScannedMailDetailsVO scannedMailDetailsVO) throws MailMLDBusniessException, MailHHTBusniessException, SystemException, ForceAcceptanceException {
	LogonAttributes logonAttributes = null;
		try {
			logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		} catch (SystemException e) {
			log.log(Log.SEVERE, "System Exception Caught");
		}
	scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
	scannedMailDetailsVO.getMailDetails().iterator().next().setIsFromTruck(null);
	MailAcceptanceVO mailAcceptanceVO = null;
	mailAcceptanceVO = makeMailAcceptanceVO(scannedMailDetailsVO, logonAttributes);
	boolean hasFlightDeparted = false;

	if (mailAcceptanceVO.getFlightSequenceNumber() != DESTN_FLT) {
		//no need to do closed flight validation for deviation list
		if (mailAcceptanceVO.getFlightStatus() == null) {
			hasFlightDeparted = new MailAcceptance().checkForDepartedFlight(mailAcceptanceVO);
		} else if (MailConstantsVO.FLIGHT_STATUS_DEPARTED.equals(mailAcceptanceVO.getFlightStatus())) {
			hasFlightDeparted = true;
		}
	}
	
	 new MailAcceptance().flagResditsForAcceptance(mailAcceptanceVO, scannedMailDetailsVO.getMailDetails(), hasFlightDeparted);
}
}
