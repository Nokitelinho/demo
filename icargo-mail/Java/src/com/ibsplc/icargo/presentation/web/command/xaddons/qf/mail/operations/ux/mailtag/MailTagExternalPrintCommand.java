/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.xaddons.qf.mail.operations.ux.mailtag.MailTagExternalPrintCommand.java
 *
 *	Created by	:	A-9090
 *	Created on	:	14-Aug-2023
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.xaddons.qf.mail.operations.ux.mailtag;

import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailTagExternalPrintForm;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtil;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.xaddons.qf.mail.operations.ux.mailtag.MailTagExternalPrintCommand.java
 *	This class is used for
 */
public class MailTagExternalPrintCommand extends AbstractPrintCommand{
	private static final Log LOGGER = LogFactory.getLogger("QFOPERATIONS SHIPMENT");
	private static final String SUCCESS = "success";
	private static final String REDIRECT_URL = "operations.flighthandling.cto.dwsexternalprinturl";
	private static final String PRINTER_OFFICE_CODE = "admin.user.printerofficecode";
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added on 			: 09-Aug-2023
	 * 	Used for 	:
	 *	Parameters	:	@param invocationContext
	 *	Parameters	:	@throws CommandInvocationException
	 */
	@Override
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		LogonAttributes logonAttributes = null; 
		try {
			logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		} catch (SystemException e) {
			LOGGER.log(Log.FINE, "Exception Occured in find LogonAttributes : " + e);
		}
		MailTagExternalPrintForm mailTagExternalPrintForm = (MailTagExternalPrintForm) invocationContext.screenModel;
		String redirectUrl = null;
		try {
			redirectUrl = ParameterUtil.getInstance().getSystemParameterValue(REDIRECT_URL);
		} catch (SystemException systemException) {
			LOGGER.log(Log.FINE, "Exception Occured in find getSystemParameterValue: " + systemException);
		}
		mailTagExternalPrintForm.setScreenMode("MAIL_TAG_LABEL_PRINT_GATEWAY");
		mailTagExternalPrintForm.setRedirectURL(redirectUrl);
		mailTagExternalPrintForm.setMailTagLabelDetails(constructMailTagLabelDetails(mailTagExternalPrintForm,logonAttributes));
		

		invocationContext.target = SUCCESS;
		
	}

	private String constructMailTagLabelDetails(MailTagExternalPrintForm mailTagExternalPrintForm,
			LogonAttributes logonAttributes) {
		String labelType = "";
		
		String officeCode = "";
		String port = "";
		String token = "";
		String mailbagIdIntegrated = "";

		if (Objects.nonNull(logonAttributes)) {
			if (Objects.nonNull(logonAttributes.getUserParameterMap())
					&& logonAttributes.getUserParameterMap().containsKey(PRINTER_OFFICE_CODE)) {
				officeCode = logonAttributes.getUserParameterMap().get(PRINTER_OFFICE_CODE);
			}
			port = logonAttributes.getAirportCode();
			LocalDate date = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
			String eventTimeString = date.toInstant().atZone(date.getTimeZone().toZoneId())
					.truncatedTo(ChronoUnit.SECONDS).toOffsetDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
			token = logonAttributes.getUserId() + "," + logonAttributes.getUserName() + "," + eventTimeString;
		}
		String mailbagId = mailTagExternalPrintForm.getSelectedMailBagId();

		
		int[] space = { 6, 12, 13, 15, 16, 20, 23, 24, 25 };

		labelType = "MAILBAG";
		mailbagIdIntegrated = spaceintegration(mailbagId, space);
		

		return String.format("labelType=%s" + "&mailBagId=%s" + "&officeCode=%s" + "&port=%s" + "&encryptedtoken=%s",
				labelType, mailbagIdIntegrated, officeCode, port, encrypt(token));
	}
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
	static String spaceintegration(String s, int []sp)
    {
      int mailBagLength = s.length();
      int spaceLength = sp.length;
      int l = 0;
      int r = 0;
      String res = newstr(mailBagLength + spaceLength, ' ');
   
      
      for (int i = 0; i < mailBagLength + spaceLength; i++) {
   
        if (l < spaceLength && i == sp[l] + l){
          l++;
        }
        else{
          res = res.substring(0,i)+s.charAt(r++)+res.substring(i+1);
      }
      }
   
     
      return res;
    }
 
 static String newstr(int i, char c) {
	   
	    StringBuilder stringBuilder = new StringBuilder("");
	    for (int j = 0; j < i; j++) {
	      
	    	stringBuilder.append(c);
	    }
	    return stringBuilder.toString();
	  }
    

}
