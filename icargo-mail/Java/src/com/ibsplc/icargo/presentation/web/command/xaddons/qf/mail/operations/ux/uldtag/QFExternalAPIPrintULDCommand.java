package com.ibsplc.icargo.presentation.web.command.xaddons.qf.mail.operations.ux.uldtag;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtil;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractPrintCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ListContainerModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.xaddons.qf.mail.operations.ux.uldtag.QFExternalAPIPrintULDCommand
 *	Version		:	Name	:	Date			:	
 * ---------------------------------------------------
 *		0.1		:	204569	:	17-May-2023	:	Draft
 */
public class QFExternalAPIPrintULDCommand extends AbstractPrintCommand {
	private static final Log LOGGER = LogFactory.getLogger("OPERATIONS PrintMailtagCommand");
	  private static final String PRINTER_OFFICE_CODE = "admin.user.printerofficecode";
	  private static final String REDIRECT_URL = "operations.flighthandling.cto.dwsexternalprinturl";

		
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.spring.controller.ActionContext)
	 *	Added by 			: 204569 on 17-May-2023
	 * 	Used for 	:	Used to initiate API print from Deadload screen
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	
	
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		LOGGER.entering("PrintMailtagCommand","execute");
  	  
    	
		ListContainerModel listContainerModel = (ListContainerModel) actionContext.getScreenModel();
		Collection<ContainerDetails> selectedContainerData = listContainerModel.getSelectedContainerData();
		
		String redirectUrl = null;
	    try {
	      redirectUrl = ParameterUtil.getInstance().getSystemParameterValue(REDIRECT_URL);
	    } catch (SystemException systemException) {
	    	LOGGER.log(Log.FINE, "Exception Occured in find getSystemParameterValue: " + systemException);
	    } 
		
		
		String uldLabelDetails = constructULDLabelDetails(selectedContainerData);
		
		List<String> results = new ArrayList<>();
		results.add(uldLabelDetails);
		results.add(redirectUrl);
		ResponseVO responseVO = new ResponseVO();	  
		
		responseVO.setResults(results);
		actionContext.setResponseVO(responseVO);    
		
		LOGGER.exiting(" QFExternalAPIPrintCommand", "execute");
	}

	private String constructULDLabelDetails(Collection<ContainerDetails> selectedContainerData) {
		LogonAttributes logonAttributes = getLogonAttribute();
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
	    String eventTimeString = date.toInstant().atZone(date.getTimeZone().toZoneId()).truncatedTo(ChronoUnit.SECONDS)
	      .toOffsetDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	    String token = logonAttributes.getUserId() + "," + 
	      logonAttributes.getUserName() + "," + eventTimeString;
		
		ContainerVO containerVO ;
		ContainerDetails containerDetails = selectedContainerData.iterator().next();
		containerVO = MailOperationsModelConverter.constructContainerVO(containerDetails, logonAttributes);
		containerVO.setAssignedUser(containerDetails.getAssignedUser());
		containerVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
		containerVO.setTransactionCode(containerDetails.getTransactionCode());
		String labelType = isContainerTypeBarrow(containerVO.getType())?"BARROW" : "ULD";
				
		String containertype = isContainerTypeBarrow(containerVO.getType())?"&barrowNumber=%s" : "&uldNumber=%s";
	    String uldNumber = containerVO.getContainerNumber();
	    String carrierCode = containerVO.getCarrierCode();
	    String flightNumber = containerVO.getFlightNumber();
	    String departurePort = containerDetails.getAirportCode();
	    String flightDate = containerVO.getFlightDate().toDisplayFormat("dd-MMM-YYYY");
	    String officeCode = "";
	    if (Objects.nonNull(logonAttributes.getUserParameterMap()) &&
				logonAttributes.getUserParameterMap().containsKey(PRINTER_OFFICE_CODE)) {
			officeCode = logonAttributes.getUserParameterMap().get(PRINTER_OFFICE_CODE);
		}
		return String.format(
				"labelType=%s" + containertype + "&carrierCode=%s" + "&flightNumber=%s" + "&departurePort=%s"
						+ "&flightDate=%s" + "&officeCode=%s" + "&port=%s" + "&encryptedtoken=%s",
				labelType, uldNumber, carrierCode, flightNumber, departurePort, flightDate, officeCode,
				logonAttributes.getAirportCode(), encrypt(token));
	}
	
	private boolean isContainerTypeBarrow(String type) {
		return type.contains("B");
	}
	/**
	 * 
	 * 	Method		:	QFExternalAPIPrintULDCommand.encrypt
	 *	Added on 	:	03-Aug-2023
	 * 	Used for 	:
	 *	Parameters	:	@param message
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	private String encrypt(String message) {
		String encryptedToken = null;
		byte[] key = null;
		byte[] iv = null;
		key = "45dbcb6c214a11ee".getBytes(StandardCharsets.UTF_8);
		iv = "5183666c72eec9e4".getBytes(StandardCharsets.UTF_8);
		
		try {
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
			GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, parameterSpec);
			encryptedToken=  Base64.getEncoder() .encodeToString(cipher.doFinal(message.getBytes(StandardCharsets.UTF_8)));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			LOGGER.log(Log.FINE, "Token Encryption Exception - > " + e);
		}
		try {
			encryptedToken =  URLEncoder.encode(encryptedToken, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			LOGGER.log(Log.FINE, "Token Encryption UnsupportedEncodingException - > " + e);
		}
		LOGGER.log(Log.FINE, "Encrypted Token - > " + encryptedToken);
		return encryptedToken; 
	}
    	
	}
		
	

