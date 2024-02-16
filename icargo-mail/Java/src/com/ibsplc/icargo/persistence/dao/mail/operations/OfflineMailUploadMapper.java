/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.OfflineMailUploadMapper.java
 *
 *	Created by	:	A-5526
 *	Created on	:	May 3, 2017
 *
 *  Copyright 2016 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import org.apache.poi.ss.usermodel.DateUtil;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.server.framework.util.ContextUtils;

/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.OfflineMailUploadMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5526	:	May 3, 2017	:	Draft
 */
public class OfflineMailUploadMapper implements Mapper<MailUploadVO> {

	private static final String BLANKSPACE = "";
	public MailUploadVO map(ResultSet rs) throws SQLException {
		MailUploadVO mailUploadVO = new MailUploadVO();
		 String EMPTY_STRING="";
			LogonAttributes logonAttributes = null;
			try {
				logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			} catch (SystemException e1) {
				e1.getMessage();
			}
			mailUploadVO.setScannedPort(logonAttributes.getAirportCode());  
		mailUploadVO.setCompanyCode(rs.getString("CMPCOD"));
		String uldType = rs.getString("REF001");
		if(uldType != null){
		if("ULD".equalsIgnoreCase(uldType.toUpperCase()))// Modified By A-7604 for ICRD-290084
			{
			mailUploadVO.setContainerType(MailConstantsVO.ULD_TYPE)	;
			}
		else
			{
			mailUploadVO.setContainerType(MailConstantsVO.BULK_TYPE)	;
			}
		}
		mailUploadVO.setContainerNumber(rs.getString("REF002"));
		mailUploadVO.setMailTag(rs.getString("REF003"));
		mailUploadVO.setContainerPol(rs.getString("REF004"));
		mailUploadVO.setContainerPOU(rs.getString("REF005"));
		
		//Modified as part of ICRD-266213 by A-7540
		String flightNumberWithCarrierCode="";
		String date="";
		
		String fileType = rs.getString("FILTYP");
			
			 
		if("MALINBOUND".equals(fileType)){
			flightNumberWithCarrierCode=rs.getString("REF006");
			 date=rs.getString("REF007");
			 String deliverFlag = rs.getString("REF008");
			 
			if(deliverFlag!=null){
			   if("N".equalsIgnoreCase(deliverFlag)){
				mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_ARRIVED);
				mailUploadVO.setDeliverFlag(deliverFlag);
			   }
			   else{
				   mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_ARRIVED);
				   mailUploadVO.setDeliverd(true); 
				   mailUploadVO.setDeliverFlag(deliverFlag);
			   }
		   }
			else{
				
				mailUploadVO.setDeliverFlag(deliverFlag);
			}
		}
		
		else{
		mailUploadVO.setDestination(rs.getString("REF006"));
	        mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			 flightNumberWithCarrierCode=rs.getString("REF007");
			 date=rs.getString("REF008");
		  }
		//mailUploadVO.setMailCompanyCode(EMPTY_STRING);  Commented and added by A-6991 for ICRD-213953
		//mailUploadVO.setMailCompanyCode(rs.getString("REF009")!=null?rs.getString("REF009"):EMPTY_STRING);
		mailUploadVO.setMailCompanyCode(rs.getString("REF009"));
		
		/* ==================IASCB-104412====================================*/
		

		if (rs.getString("REF010") != null && MailConstantsVO.YES.equals(rs.getString("REF010"))) {
			mailUploadVO.setPaCode(MailConstantsVO.FLAG_YES);
		} else {

			mailUploadVO.setPaCode(MailConstantsVO.FLAG_NO);
		}
	/*	 ==================END=========================================*/
		if (rs.getString("REF005") != null && rs.getString("REF005").trim().length() > 0) {
			mailUploadVO.setContainerPOU(rs.getString("REF005"));
		}
		/*else {
			if (rs.getString("REF003").trim().length() == 29) {
				mailUploadVO.setContainerPOU(rs.getString("REF003").substring(8, 11));
			}
		}*/
		//Modified BY A-7604 for 290115
		String[] splitData = null;
		if(flightNumberWithCarrierCode!=null && flightNumberWithCarrierCode.contains("-")){
				splitData=flightNumberWithCarrierCode.split("-");
			String flightNo="";
			mailUploadVO.setFromCarrierCode(splitData[0].toUpperCase());
			try {
				if(splitData[1].toString()!=null && !BLANKSPACE.equalsIgnoreCase(splitData[1].toString()))
				{
					flightNo=validateFlightNumberFormat(splitData[1].toString());
				}
			} catch (SystemException e) {
				
				e.getMessage();
			}
			mailUploadVO.setCarrierCode(splitData[0].toUpperCase());
			mailUploadVO.setFlightNumber(flightNo);
			}
		//Added BY A-7604 for 290115
		 else if(flightNumberWithCarrierCode!=null)
		 {
			 mailUploadVO.setFromCarrierCode(flightNumberWithCarrierCode.substring(0,2).toUpperCase());
			 mailUploadVO.setCarrierCode(flightNumberWithCarrierCode.substring(0,2).toUpperCase());
			 String flightNumber=flightNumberWithCarrierCode.substring(2,flightNumberWithCarrierCode.length());
			 String flightNo="";
				try {
					if(flightNumber!=null && !BLANKSPACE.equalsIgnoreCase(flightNumber))
					{
						flightNo=validateFlightNumberFormat(flightNumber);
					}
				} catch (SystemException e) {
					e.getMessage();
				}
				mailUploadVO.setFlightNumber(flightNo);
		 }
		 // End Here For 290115
	 else{
			mailUploadVO.setFromCarrierCode(flightNumberWithCarrierCode);
			mailUploadVO.setFlightNumber("");
		}
		
		/**
		mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_ACCEPTED);     
	
		//mailUploadVO.setMailCompanyCode(EMPTY_STRING);  Commented and added by A-6991 for ICRD-213953
		mailUploadVO.setMailCompanyCode(rs.getString("REF009"));
		
		if (rs.getString("REF005") != null && rs.getString("REF005").trim().length() > 0) {
			mailUploadVO.setContainerPOU(rs.getString("REF005"));
		} else {
			if (rs.getString("REF003").trim().length() == 29) {
				mailUploadVO.setContainerPOU(rs.getString("REF003").substring(8, 11));
			}
		}
		
		**/

		if(mailUploadVO.getDestination()==null && mailUploadVO.getContainerPOU()!=null){
			mailUploadVO.setDestination(mailUploadVO.getContainerPOU())	;
		}
/*		if(mailUploadVO.getContainerPol()==null){
			mailUploadVO.setContainerPol(logonAttributes.getAirportCode());
		}*/
		
		mailUploadVO.setRemarks(EMPTY_STRING);     
		mailUploadVO.setDamageCode(EMPTY_STRING);
		mailUploadVO.setDamageRemarks(EMPTY_STRING);
		mailUploadVO.setOffloadReason(EMPTY_STRING);
		mailUploadVO.setReturnCode(EMPTY_STRING);
		mailUploadVO.setToContainer(EMPTY_STRING);
		mailUploadVO.setToCarrierCode(EMPTY_STRING);
		mailUploadVO.setToFlightNumber(EMPTY_STRING);
		
		if (mailUploadVO.getContainerPOU()!=null) {
			mailUploadVO.setToPOU(mailUploadVO.getContainerPOU());
			mailUploadVO.setToDestination(mailUploadVO.getContainerPOU());
		}else {
			if (mailUploadVO.getMailTag()!=null && mailUploadVO.getMailTag().trim().length() == 29) {
				mailUploadVO.setToPOU(mailUploadVO.getMailTag().substring(8, 11));
				mailUploadVO.setToDestination(mailUploadVO.getMailTag().substring(8, 11));
			}
		}
		if (mailUploadVO.getMailTag()!=null && mailUploadVO.getMailTag().trim().length() == 29) {
			mailUploadVO.setOrginOE(mailUploadVO.getMailTag().substring(0, 6));
			mailUploadVO.setDestinationOE(mailUploadVO.getMailTag().substring(
					6, 12));
			mailUploadVO.setCategory(mailUploadVO.getMailTag().substring(12, 13));
			mailUploadVO.setSubClass(mailUploadVO.getMailTag().substring(13, 15));

			mailUploadVO.setYear(Integer.parseInt(mailUploadVO.getMailTag().substring(15,
					16)));     
		}

		
		mailUploadVO.setScanUser("EXCELUPL");
		mailUploadVO.setConsignmentDocumentNumber(EMPTY_STRING);
		mailUploadVO.setCirCode(EMPTY_STRING);      
		mailUploadVO.setFromCarrierCode(EMPTY_STRING);  
		mailUploadVO.setMailSource("EXCELUPL");
		LocalDate dateTime = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		mailUploadVO.setDateTime(dateTime.toDisplayFormat());                                    
		if (date != null) {  
			
			
			LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			  
			 java.text.DateFormat  formatter = new java.text.SimpleDateFormat("dd-MMM-yyyy");
		       Date parseDate=null;
			try {
				parseDate = formatter.parse(date);           
			} catch (ParseException e) {
				parseDate = DateUtil.getJavaDate(Double.parseDouble(date));
			}   
		       flightDate.setTime(parseDate);

		mailUploadVO.setFlightDate(flightDate);
		}
		return mailUploadVO;
	}
	
	/**
	 * @@author A-7604 for 290115
	 * validateFlightNumberFormat
	 * @param flight
	 * @return String
	 * @throws SystemException
	 */
	public String validateFlightNumberFormat(String flight) throws SystemException
	  {
		StringBuilder flightnumber= new StringBuilder();
		if(flight!=null && flight.length()==1){			
			flightnumber = flightnumber.append("000").append(flight);
			}
			else if(flight.length()==2){
				flightnumber = flightnumber.append("00").append(flight);
				}
			else if(flight.length()==3){
				flightnumber = flightnumber.append("0").append(flight);
			}
			else{
				return flight;
			}
		return flightnumber.toString();
		
	  }

}
