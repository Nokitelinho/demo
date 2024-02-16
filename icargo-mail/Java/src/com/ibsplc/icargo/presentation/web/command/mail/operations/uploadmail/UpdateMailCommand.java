/*
 * UpdateMailCommand.java Created on Feb 09, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmail;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1876
 *
 */
public class UpdateMailCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAIL");
	
   /**
    * TARGET
    */
   private static final String TARGET = "update_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.uploadmail";	
   private static final String HYPHEN = "-";	
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("UpdateMailCommand","execute");
    	  
    	UploadMailForm uploadMailForm 
				= (UploadMailForm)invocationContext.screenModel;
    	UploadMailSession uploadMailSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
		/* For Accept Mail Tab */
		String[] acceptOOE = uploadMailForm.getAcceptOoe();
		String[] acceptDOE = uploadMailForm.getAcceptDoe();
		String[] acceptCat = uploadMailForm.getAcceptCat();
		String[] acceptSC = uploadMailForm.getAcceptSc();
		String[] acceptYr = uploadMailForm.getAcceptYr();
		String[] acceptDSN = uploadMailForm.getAcceptDsn();
		String[] acceptRSN = uploadMailForm.getAcceptRsn();
		String[] acceptHNI = uploadMailForm.getAcceptHni();
		String[] acceptRI = uploadMailForm.getAcceptRi();
		String[] acceptWt = uploadMailForm.getAcceptWt();
		String[] acceptScanDate = uploadMailForm.getAcceptScanDate();
		String[] acceptScanTime = uploadMailForm.getAcceptScanTime();
		
		/* For Accept Mail Tab */
		String[] arriveOOE = uploadMailForm.getArriveOoe();
		String[] arriveDOE = uploadMailForm.getArriveDoe();
		String[] arriveCat = uploadMailForm.getArriveCat();
		String[] arriveSC = uploadMailForm.getArriveSc();
		String[] arriveYr = uploadMailForm.getArriveYr();
		String[] arriveDSN = uploadMailForm.getArriveDsn();
		String[] arriveRSN = uploadMailForm.getArriveRsn();
		String[] arriveHNI = uploadMailForm.getArriveHni();
		String[] arriveRI = uploadMailForm.getArriveRi();
		String[] arriveWt = uploadMailForm.getArriveWt();
		String[] arriveScanDate = uploadMailForm.getArriveScanDate();
		String[] arriveScanTime = uploadMailForm.getArriveScanTime();
		
		/* For Accept Mail Tab */
		String[] returnOOE = uploadMailForm.getReturnOoe();
		String[] returnDOE = uploadMailForm.getReturnDoe();
		String[] returnCat = uploadMailForm.getReturnCat();
		String[] returnSC = uploadMailForm.getReturnSc();
		String[] returnYr = uploadMailForm.getReturnYr();
		String[] returnDSN = uploadMailForm.getReturnDsn();
		String[] returnRSN = uploadMailForm.getReturnRsn();
		String[] returnHNI = uploadMailForm.getReturnHni();
		String[] returnRI = uploadMailForm.getReturnRi();
		String[] returnWt = uploadMailForm.getReturnWt();
		String[] returnScanDate = uploadMailForm.getReturnScanDate();
		String[] returnScanTime = uploadMailForm.getReturnScanTime();
		
		/* For Offload Mail Tab */
		String[] offloadOOE = uploadMailForm.getOffloadOoe();
		String[] offloadDOE = uploadMailForm.getOffloadDoe();
		String[] offloadCat = uploadMailForm.getOffloadCat();
		String[] offloadSC = uploadMailForm.getOffloadSc();
		String[] offloadYr = uploadMailForm.getOffloadYr();
		String[] offloadDSN = uploadMailForm.getOffloadDsn();
		String[] offloadRSN = uploadMailForm.getOffloadRsn();
		String[] offloadHNI = uploadMailForm.getOffloadHni();
		String[] offloadRI = uploadMailForm.getOffloadRi();
		String[] offloadWt = uploadMailForm.getOffloadWt();
		String[] offloadScanDate = uploadMailForm.getOffloadScanDate();
		String[] offloadScanTime = uploadMailForm.getOffloadScanTime();
		
		ScannedDetailsVO scannedVO = uploadMailSession.getScannedDetailsVO();
		Collection<ScannedMailDetailsVO> scannedOutboundVOs = scannedVO.getOutboundMails();
		Collection<ScannedMailDetailsVO> scannedArrivedVOs = scannedVO.getArrivedMails();
		Collection<ScannedMailDetailsVO> scannedReturnVOs = scannedVO.getReturnedMails();
		Collection<ScannedMailDetailsVO> scannedOffloadVOs = scannedVO.getOffloadMails();
		
		int count = 0;
		if(scannedOutboundVOs != null && scannedOutboundVOs.size() > 0) {
		for(ScannedMailDetailsVO scannedMailVO:scannedOutboundVOs) {
			Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
			mailbagVOs = scannedMailVO.getMailDetails();
			if(mailbagVOs != null && mailbagVOs.size() > 0) {
			  for(MailbagVO mailbagVO:mailbagVOs) {
				int modifyFlag = 0;
				if(acceptOOE != null) {
					if(acceptOOE[count] != null && !("".equals(acceptOOE[count]))) {
						if(!(acceptOOE[count].toUpperCase()).equals(mailbagVO.getOoe())) {
							 modifyFlag = 1;
						}
						mailbagVO.setOoe(acceptOOE[count].toUpperCase());
					}
				}
				if(acceptDOE != null) {
					if(acceptDOE[count] != null && !("".equals(acceptDOE[count]))) {
						if(!(acceptDOE[count].toUpperCase()).equals(mailbagVO.getDoe())) {
							 modifyFlag = 1;
						}
						mailbagVO.setDoe(acceptDOE[count].toUpperCase());
					}
				}
				if(acceptCat != null) {
					if(acceptCat[count] != null && !("".equals(acceptCat[count]))) {
						if(!(acceptCat[count].toUpperCase()).equals(mailbagVO.getMailCategoryCode())) {
							 modifyFlag = 1;
						}
						mailbagVO.setMailCategoryCode(acceptCat[count]);
					}
				}
				if(acceptSC != null) {
					if(acceptSC[count] != null && !("".equals(acceptSC[count]))) {
						if(!(acceptSC[count].toUpperCase()).equals(mailbagVO.getMailSubclass())) {
							 modifyFlag = 1;
						}
						mailbagVO.setMailSubclass(acceptSC[count].toUpperCase());
						mailbagVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
					}
				}
				if(acceptYr != null) {
					if(acceptYr[count] != null && !("".equals(acceptYr[count]))) {
						if(!(acceptYr[count].toUpperCase()).equals(String.valueOf(mailbagVO.getYear()))) {
							 modifyFlag = 1;
						}
						mailbagVO.setYear(Integer.parseInt(acceptYr[count]));
					}
				}
				if(acceptDSN != null) {
					if(acceptDSN[count] != null && !("".equals(acceptDSN[count]))) {
						if(!(acceptDSN[count].toUpperCase()).equals(mailbagVO.getDespatchSerialNumber())) {
							 modifyFlag = 1;
						}
						mailbagVO.setDespatchSerialNumber(acceptDSN[count].toUpperCase());
					}
				}
				if(acceptRSN != null) {
					if(acceptRSN[count] != null && !("".equals(acceptRSN[count]))) {
						if(!(acceptRSN[count].toUpperCase()).equals(mailbagVO.getReceptacleSerialNumber())) {
							 modifyFlag = 1;
						}
						mailbagVO.setReceptacleSerialNumber(acceptRSN[count].toUpperCase());
					}
				}
				if(acceptHNI != null) {
					if(acceptHNI[count] != null && !("".equals(acceptHNI[count]))) {
						if(!(acceptHNI[count].toUpperCase()).equals(mailbagVO.getHighestNumberedReceptacle())) {
							 modifyFlag = 1;
						}
						mailbagVO.setHighestNumberedReceptacle(acceptHNI[count]);
					}
				}
				if(acceptRI != null) {
					if(acceptRI[count] != null && !("".equals(acceptRI[count]))) {
						if(!(acceptRI[count].toUpperCase()).equals(mailbagVO.getRegisteredOrInsuredIndicator())) {
							 modifyFlag = 1;
						}
						mailbagVO.setRegisteredOrInsuredIndicator(acceptRI[count]);
					}
				}
				if(acceptWt != null) {
					if(acceptWt[count] != null && !("".equals(acceptWt[count]))) {
						if(!(acceptWt[count].toUpperCase()).equals(mailbagVO.getStrWeight())) {
							 modifyFlag = 1;
						}
						/*mailbagVO.setWeight((Double.parseDouble(acceptWt[count]))/10);
						mailbagVO.setStrWeight(acceptWt[count]);*/
						mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,(Double.parseDouble(acceptWt[count]))/10));//added by A-7371
						mailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT,(Double.parseDouble(acceptWt[count]))/10));//added by A-7371
						
					}
				}
				
				if(acceptScanDate != null && acceptScanTime != null) {
					if((acceptScanDate[count] != null && !("".equals(acceptScanDate[count])))
						&& (acceptScanTime[count] != null && !("".equals(acceptScanTime[count])))) {
						LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
						String scanDT = new StringBuilder(acceptScanDate[count]).append(" ")
						        .append(acceptScanTime[count]).append(":00").toString();
						mailbagVO.setScannedDate(sd.setDateAndTime(scanDT,false));
					}
				}
				 String mailId = new StringBuilder()
		            .append(mailbagVO.getOoe())
		            .append(mailbagVO.getDoe())
					.append(mailbagVO.getMailCategoryCode())
					.append(mailbagVO.getMailSubclass())
					.append(mailbagVO.getYear())
					.append(mailbagVO.getDespatchSerialNumber())
					.append(mailbagVO.getReceptacleSerialNumber())
					.append(mailbagVO.getHighestNumberedReceptacle())
					.append(mailbagVO.getRegisteredOrInsuredIndicator())
					.append(mailbagVO.getStrWeight()).toString();
				 mailbagVO.setMailbagId(mailId);
				count++;
				
				if(modifyFlag == 1){
					mailbagVO.setCarrierCode(null);
					mailbagVO.setCarrierId(0);
					mailbagVO.setFlightNumber(null);
					mailbagVO.setFlightDate(null);
					mailbagVO.setFlightSequenceNumber(0);
					mailbagVO.setLegSerialNumber(0);
					mailbagVO.setSegmentSerialNumber(0);
					mailbagVO.setPol(null);
					mailbagVO.setPou(null);
					mailbagVO.setContainerNumber(null);
					mailbagVO.setContainerType(null);
					mailbagVO.setErrorDescription(null);
					mailbagVO.setErrorType(null);
					modifyFlag = 0;
				}
			  }
			}
			 scannedMailVO.setMailDetails(mailbagVOs);
		  }
		}
		count = 0;
		if(scannedArrivedVOs != null && scannedArrivedVOs.size() > 0) {
		for(ScannedMailDetailsVO scannedMailVO:scannedArrivedVOs) {
			Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
			mailbagVOs = scannedMailVO.getMailDetails();
			if(mailbagVOs != null && mailbagVOs.size() > 0) {
			  for(MailbagVO mailbagVO:mailbagVOs) {
				int modifyFlag = 0;
				if(arriveOOE != null) {
					if(arriveOOE[count] != null && !("".equals(arriveOOE[count]))) {
						if(!(arriveOOE[count].toUpperCase()).equals(mailbagVO.getOoe())) {
							 modifyFlag = 1;
						}
						mailbagVO.setOoe(arriveOOE[count].toUpperCase());
					}
				}
				if(arriveDOE != null) {
					if(arriveDOE[count] != null && !("".equals(arriveDOE[count]))) {
						if(!(arriveDOE[count].toUpperCase()).equals(mailbagVO.getDoe())) {
							 modifyFlag = 1;
						}
						mailbagVO.setDoe(arriveDOE[count].toUpperCase());
					}
				}
				if(arriveCat != null) {
					if(arriveCat[count] != null && !("".equals(arriveCat[count]))) {
						if(!(arriveCat[count].toUpperCase()).equals(mailbagVO.getMailCategoryCode())) {
							 modifyFlag = 1;
						}
						mailbagVO.setMailCategoryCode(arriveCat[count]);
					}
				}
				if(arriveSC != null) {
					if(arriveSC[count] != null && !("".equals(arriveSC[count]))) {
						if(!(arriveSC[count].toUpperCase()).equals(mailbagVO.getMailSubclass())) {
							 modifyFlag = 1;
						}
						mailbagVO.setMailSubclass(arriveSC[count].toUpperCase());
						mailbagVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
					}
				}
				if(arriveYr != null) {
					if(arriveYr[count] != null && !("".equals(arriveYr[count]))) {
						if(!(arriveYr[count].toUpperCase()).equals(String.valueOf(mailbagVO.getYear()))) {
							 modifyFlag = 1;
						}
						mailbagVO.setYear(Integer.parseInt(arriveYr[count]));
					}
				}
				if(arriveDSN != null) {
					if(arriveDSN[count] != null && !("".equals(arriveDSN[count]))) {
						if(!(arriveDSN[count].toUpperCase()).equals(mailbagVO.getDespatchSerialNumber())) {
							 modifyFlag = 1;
						}
						mailbagVO.setDespatchSerialNumber(arriveDSN[count].toUpperCase());
					}
				}
				if(arriveRSN != null) {
					if(arriveRSN[count] != null && !("".equals(arriveRSN[count]))) {
						if(!(arriveRSN[count].toUpperCase()).equals(mailbagVO.getReceptacleSerialNumber())) {
							 modifyFlag = 1;
						}
						mailbagVO.setReceptacleSerialNumber(arriveRSN[count].toUpperCase());
					}
				}
				if(arriveHNI != null) {
					if(arriveHNI[count] != null && !("".equals(arriveHNI[count]))) {
						if(!(arriveHNI[count].toUpperCase()).equals(mailbagVO.getHighestNumberedReceptacle())) {
							 modifyFlag = 1;
						}
						mailbagVO.setHighestNumberedReceptacle(arriveHNI[count]);
					}
				}
				if(acceptRI != null) {
					if(arriveRI[count] != null && !("".equals(arriveRI[count]))) {
						if(!(arriveRI[count].toUpperCase()).equals(mailbagVO.getRegisteredOrInsuredIndicator())) {
							 modifyFlag = 1;
						}
						mailbagVO.setRegisteredOrInsuredIndicator(arriveRI[count]);
					}
				}
				if(arriveWt != null) {
					if(arriveWt[count] != null && !("".equals(arriveWt[count]))) {
						if(!(arriveWt[count].toUpperCase()).equals(mailbagVO.getStrWeight())) {
							 modifyFlag = 1;
						}
						/*mailbagVO.setWeight((Double.parseDouble(arriveWt[count]))/10);
						mailbagVO.setStrWeight(arriveWt[count]);*/
						mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(arriveWt[count])/10));
						mailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(arriveWt[count])/10));
					}
				}
				
				if(arriveScanDate != null && arriveScanTime != null) {
					if((arriveScanDate[count] != null && !("".equals(arriveScanDate[count])))
						&& (arriveScanTime[count] != null && !("".equals(arriveScanTime[count])))) {
						LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
						String scanDT = new StringBuilder(arriveScanDate[count]).append(" ")
						        .append(arriveScanTime[count]).append(":00").toString();
						mailbagVO.setScannedDate(sd.setDateAndTime(scanDT,false));
					}
				}
				String mailId = new StringBuilder()
	            .append(mailbagVO.getOoe())
	            .append(mailbagVO.getDoe())
				.append(mailbagVO.getMailCategoryCode())
				.append(mailbagVO.getMailSubclass())
				.append(mailbagVO.getYear())
				.append(mailbagVO.getDespatchSerialNumber())
				.append(mailbagVO.getReceptacleSerialNumber())
				.append(mailbagVO.getHighestNumberedReceptacle())
				.append(mailbagVO.getRegisteredOrInsuredIndicator())
				.append(mailbagVO.getStrWeight()).toString();
			    mailbagVO.setMailbagId(mailId);
				count++;
				if(modifyFlag == 1){
					mailbagVO.setCarrierCode(null);
					mailbagVO.setCarrierId(0);
					mailbagVO.setFlightNumber(null);
					mailbagVO.setFlightDate(null);
					mailbagVO.setFlightSequenceNumber(0);
					mailbagVO.setLegSerialNumber(0);
					mailbagVO.setSegmentSerialNumber(0);
					mailbagVO.setPol(null);
					mailbagVO.setPou(null);
					mailbagVO.setContainerNumber(null);
					mailbagVO.setContainerType(null);
					mailbagVO.setErrorDescription(null);
					mailbagVO.setErrorType(null);
					modifyFlag = 0;
				}
			  }
			}
			 scannedMailVO.setMailDetails(mailbagVOs);
		  }
		}
		
		count = 0;
		if(scannedReturnVOs != null && scannedReturnVOs.size() > 0) {
		for(ScannedMailDetailsVO scannedMailVO:scannedReturnVOs) {
			Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
			mailbagVOs = scannedMailVO.getMailDetails();
			if(mailbagVOs != null && mailbagVOs.size() > 0) {
			  for(MailbagVO mailbagVO:mailbagVOs) {
				int modifyFlag = 0;
				if(returnOOE != null) {
					if(returnOOE[count] != null && !("".equals(returnOOE[count]))) {
						if(!(returnOOE[count].toUpperCase()).equals(mailbagVO.getOoe())) {
							 modifyFlag = 1;
						}
						mailbagVO.setOoe(returnOOE[count].toUpperCase());
					}
				}
				if(returnDOE != null) {
					if(returnDOE[count] != null && !("".equals(returnDOE[count]))) {
						if(!(returnDOE[count].toUpperCase()).equals(mailbagVO.getDoe())) {
							 modifyFlag = 1;
						}
						mailbagVO.setDoe(returnDOE[count].toUpperCase());
					}
				}
				if(returnCat != null) {
					if(returnCat[count] != null && !("".equals(returnCat[count]))) {
						if(!(returnCat[count].toUpperCase()).equals(mailbagVO.getMailCategoryCode())) {
							 modifyFlag = 1;
						}
						mailbagVO.setMailCategoryCode(returnCat[count]);
					}
				}
				if(returnSC != null) {
					if(returnSC[count] != null && !("".equals(returnSC[count]))) {
						if(!(returnSC[count].toUpperCase()).equals(mailbagVO.getMailSubclass())) {
							 modifyFlag = 1;
						}
						mailbagVO.setMailSubclass(returnSC[count].toUpperCase());
						mailbagVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
					}
				}
				if(returnYr != null) {
					if(returnYr[count] != null && !("".equals(returnYr[count]))) {
						if(!(returnYr[count].toUpperCase()).equals(String.valueOf(mailbagVO.getYear()))) {
							 modifyFlag = 1;
						}
						mailbagVO.setYear(Integer.parseInt(returnYr[count]));
					}
				}
				if(returnDSN != null) {
					if(returnDSN[count] != null && !("".equals(returnDSN[count]))) {
						if(!(returnDSN[count].toUpperCase()).equals(mailbagVO.getDespatchSerialNumber())) {
							 modifyFlag = 1;
						}
						mailbagVO.setDespatchSerialNumber(returnDSN[count].toUpperCase());
					}
				}
				if(returnRSN != null) {
					if(returnRSN[count] != null && !("".equals(returnRSN[count]))) {
						if(!(returnRSN[count].toUpperCase()).equals(mailbagVO.getReceptacleSerialNumber())) {
							 modifyFlag = 1;
						}
						mailbagVO.setReceptacleSerialNumber(returnRSN[count].toUpperCase());
					}
				}
				if(returnHNI != null) {
					if(returnHNI[count] != null && !("".equals(returnHNI[count]))) {
						if(!(returnHNI[count].toUpperCase()).equals(mailbagVO.getHighestNumberedReceptacle())) {
							 modifyFlag = 1;
						}
						mailbagVO.setHighestNumberedReceptacle(returnHNI[count]);
					}
				}
				if(returnRI != null) {
					if(returnRI[count] != null && !("".equals(returnRI[count]))) {
						if(!(returnRI[count].toUpperCase()).equals(mailbagVO.getRegisteredOrInsuredIndicator())) {
							 modifyFlag = 1;
						}
						mailbagVO.setRegisteredOrInsuredIndicator(returnRI[count]);
					}
				}
				if(returnWt != null) {
					if(returnWt[count] != null && !("".equals(returnWt[count]))) {
						if(!(returnWt[count].toUpperCase()).equals(mailbagVO.getStrWeight())) {
							 modifyFlag = 1;
						}
						/*mailbagVO.setWeight(Double.parseDouble(returnWt[count]))/10);
						mailbagVO.setStrWeight(returnWt[count]);*/
						mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(returnWt[count])/10));
						mailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(returnWt[count])/10));
					}
				}
				
				if(returnScanDate != null && returnScanTime != null) {
					if((returnScanDate[count] != null && !("".equals(returnScanDate[count])))
						&& (returnScanTime[count] != null && !("".equals(returnScanTime[count])))) {
						LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
						String scanDT = new StringBuilder(returnScanDate[count]).append(" ")
						        .append(returnScanTime[count]).append(":00").toString();
						mailbagVO.setScannedDate(sd.setDateAndTime(scanDT,false));
					}
				}
				String mailId = new StringBuilder()
	            .append(mailbagVO.getOoe())
	            .append(mailbagVO.getDoe())
				.append(mailbagVO.getMailCategoryCode())
				.append(mailbagVO.getMailSubclass())
				.append(mailbagVO.getYear())
				.append(mailbagVO.getDespatchSerialNumber())
				.append(mailbagVO.getReceptacleSerialNumber())
				.append(mailbagVO.getHighestNumberedReceptacle())
				.append(mailbagVO.getRegisteredOrInsuredIndicator())
				.append(mailbagVO.getStrWeight()).toString();
			    mailbagVO.setMailbagId(mailId);
				count++;
				
				if(modifyFlag == 1){
					mailbagVO.setCarrierCode(null);
					mailbagVO.setCarrierId(0);
					mailbagVO.setFlightNumber(null);
					mailbagVO.setFlightDate(null);
					mailbagVO.setFlightSequenceNumber(0);
					mailbagVO.setLegSerialNumber(0);
					mailbagVO.setSegmentSerialNumber(0);
					mailbagVO.setPol(null);
					mailbagVO.setPou(null);
					mailbagVO.setContainerNumber(null);
					mailbagVO.setContainerType(null);
					mailbagVO.setErrorDescription(null);
					mailbagVO.setErrorType(null);
					modifyFlag = 0;
				}
			  }
			}
			 scannedMailVO.setMailDetails(mailbagVOs);
		  }
		}
		count = 0;
		
		if(scannedOffloadVOs != null && scannedOffloadVOs.size() > 0) {
			for(ScannedMailDetailsVO scannedMailVO:scannedOffloadVOs) {
				Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
				mailbagVOs = scannedMailVO.getMailDetails();
				if(mailbagVOs != null && mailbagVOs.size() > 0) {
				  for(MailbagVO mailbagVO:mailbagVOs) {
					int modifyFlag = 0;
					if(offloadOOE != null) {
						if(offloadOOE[count] != null && !("".equals(offloadOOE[count]))) {
							mailbagVO.setOoe(offloadOOE[count].toUpperCase());
						}
					}
					if(offloadDOE != null) {
						if(offloadDOE[count] != null && !("".equals(offloadDOE[count]))) {
							mailbagVO.setDoe(offloadDOE[count].toUpperCase());
						}
					}
					if(offloadCat != null) {
						if(offloadCat[count] != null && !("".equals(offloadCat[count]))) {
							mailbagVO.setMailCategoryCode(offloadCat[count]);
						}
					}
					if(offloadSC != null) {
						if(offloadSC[count] != null && !("".equals(offloadSC[count]))) {
							mailbagVO.setMailSubclass(offloadSC[count].toUpperCase());
							mailbagVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
						}
					}
					if(offloadYr != null) {
						if(offloadYr[count] != null && !("".equals(offloadYr[count]))) {
							mailbagVO.setYear(Integer.parseInt(offloadYr[count]));
						}
					}
					if(offloadDSN != null) {
						if(offloadDSN[count] != null && !("".equals(offloadDSN[count]))) {
							mailbagVO.setDespatchSerialNumber(offloadDSN[count].toUpperCase());
						}
					}
					if(offloadRSN != null) {
						if(offloadRSN[count] != null && !("".equals(offloadRSN[count]))) {
							mailbagVO.setReceptacleSerialNumber(offloadRSN[count].toUpperCase());
						}
					}
					if(offloadHNI != null) {
						if(offloadHNI[count] != null && !("".equals(offloadHNI[count]))) {
							mailbagVO.setHighestNumberedReceptacle(offloadHNI[count]);
						}
					}
					if(offloadRI != null) {
						if(offloadRI[count] != null && !("".equals(offloadRI[count]))) {
							mailbagVO.setRegisteredOrInsuredIndicator(offloadRI[count]);
						}
					}
					if(offloadWt != null) {
						if(offloadWt[count] != null && !("".equals(offloadWt[count]))) {
							
							/*mailbagVO.setWeight((Double.parseDouble(offloadWt[count])/10);
							mailbagVO.setStrWeight(offloadWt[count]);*/
							mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(offloadWt[count])/10));
							mailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(offloadWt[count])/10));//added by A-7371
						}
					}
					
					if(offloadScanDate != null && offloadScanTime != null) {
						if((offloadScanDate[count] != null && !("".equals(offloadScanDate[count])))
							&& (offloadScanTime[count] != null && !("".equals(offloadScanTime[count])))) {
							LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
							String scanDT = new StringBuilder(offloadScanDate[count]).append(" ")
							        .append(offloadScanTime[count]).append(":00").toString();
							mailbagVO.setScannedDate(sd.setDateAndTime(scanDT,false));
						}
					}
					String mailId = new StringBuilder()
		            .append(mailbagVO.getOoe())
		            .append(mailbagVO.getDoe())
					.append(mailbagVO.getMailCategoryCode())
					.append(mailbagVO.getMailSubclass())
					.append(mailbagVO.getYear())
					.append(mailbagVO.getDespatchSerialNumber())
					.append(mailbagVO.getReceptacleSerialNumber())
					.append(mailbagVO.getHighestNumberedReceptacle())
					.append(mailbagVO.getRegisteredOrInsuredIndicator())
					.append(mailbagVO.getStrWeight()).toString();
				    mailbagVO.setMailbagId(mailId);
					count++;
					
				  }
				}
				 scannedMailVO.setMailDetails(mailbagVOs);
			  }
			}
    	
		scannedVO.setOutboundMails(scannedOutboundVOs);
		scannedVO.setArrivedMails(scannedArrivedVOs);
		scannedVO.setReturnedMails(scannedReturnVOs);
		scannedVO.setOffloadMails(scannedOffloadVOs);
		
		uploadMailSession.setScannedDetailsVO(scannedVO);
		/**
		 * Updating Summary Details
		 * @author A-3227
		 */
		ScannedDetailsVO summaryVO = uploadMailSession.getScannedSummaryVO();
		Collection<ScannedMailDetailsVO> outboundSummaryVOs = summaryVO.getOutboundMails();
		Collection<ScannedMailDetailsVO> arrivedSummaryVOs = summaryVO.getArrivedMails();
		Collection<ScannedMailDetailsVO> returnedSummaryVOs = summaryVO.getReturnedMails();
		Collection<ScannedMailDetailsVO> offloadedSummaryVOs = summaryVO.getOffloadMails();
		Collection<ScannedMailDetailsVO> newArvdSummaryVOs = new ArrayList<ScannedMailDetailsVO>();
		Collection<ScannedMailDetailsVO> newAcpSummarVOs = new ArrayList<ScannedMailDetailsVO>();
		Collection<ScannedMailDetailsVO> newRetSummarVOs = new ArrayList<ScannedMailDetailsVO>();
		
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		if (scannedOutboundVOs != null && scannedOutboundVOs.size() > 0) {
			for (ScannedMailDetailsVO acceptMailVO : scannedOutboundVOs) {
				mailbagVOs = new ArrayList<MailbagVO>();
				mailbagVOs = acceptMailVO.getMailDetails();
				if (mailbagVOs != null && mailbagVOs.size() > 0) {
					acceptMailVO.setScannedBags(mailbagVOs.size());
				}
				newAcpSummarVOs.add(acceptMailVO);
			}
		}
		if (scannedReturnVOs != null && scannedReturnVOs.size() > 0) {
			for (ScannedMailDetailsVO returnedMailVO : scannedReturnVOs) {
				mailbagVOs = new ArrayList<MailbagVO>();
				mailbagVOs = returnedMailVO.getMailDetails();
				if (mailbagVOs != null && mailbagVOs.size() > 0) {
					returnedMailVO.setScannedBags(mailbagVOs.size());
				}
				newRetSummarVOs.add(returnedMailVO);
			}
		}

		
		summaryVO.setOutboundMails(newAcpSummarVOs);
		summaryVO.setArrivedMails(arrivedSummaryVOs);
		summaryVO.setReturnedMails(newRetSummarVOs);


		uploadMailSession.setScannedSummaryVO(summaryVO);
		
    	invocationContext.target = TARGET;
       	log.exiting("UpdateMailCommand","execute");
    	
    }
       
}
