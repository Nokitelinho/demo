/*
 * SaveCommand.java
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.errorhandling;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
//import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailTrackingErrorHandlingSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ErrorHandlingPopUpForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
/**
 * @author A-5991
 *
 */
public class SaveCommand extends BaseCommand {

	/**
	 * Logger
	 */
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");

	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "mail.operations";

	/**
	 * The ScreenID
	 */
	private static final String SCREEN_ID = "mailtracking.defaults.errorhandligpopup";
	/**
	 * Target mappings for succes
	 */
	private static final String SAVE_SUCCESS = "save_success";

	private static final String DISPLAY_DATE_FORMAT = "dd-MMM-yyyy HH:mm:ss";

	/**
	 * This method is used to execute the list requested construct rates details
	 * command
	 *
	 * @param invocationContext
	 *            - InvocationContext
    * @throws CommandInvocationException
    */
   public void execute(InvocationContext invocationContext)
	                         throws CommandInvocationException {

	   log.entering("SaveCommand", "execute");
	   ErrorHandlingPopUpForm errorHandlingPopUpForm = (ErrorHandlingPopUpForm) invocationContext.screenModel;
	   MailTrackingErrorHandlingSession mailTrackingErrorHandlingSession= (MailTrackingErrorHandlingSession) getScreenSession(
				MODULE_NAME, SCREEN_ID)	;
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		ScannedMailDetailsVO scannedMailDetailsVO = null;
		String txnId=mailTrackingErrorHandlingSession.getTxnid();
		log.log(Log.FINE, "***txnid inside save command*******",txnId);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		

		MailUploadVO mailUploadVO = mailTrackingErrorHandlingSession
				.getScannedDetails();
		String[] txnIds = mailTrackingErrorHandlingSession.getTxnids();

		LinkedHashMap<String, Collection<MailUploadVO>> mailUploadMap = fetchSelectedErrorVOs(
				companyCode, txnIds);

		Collection<MailUploadVO> finalMailUploadVOsForSave = validateAndUpdateMailUploadVOs(
				mailUploadMap, errorHandlingPopUpForm);

		if (finalMailUploadVOsForSave != null) {
			for (MailUploadVO mailUploadVOForSave : finalMailUploadVOsForSave) {
				Collection<MailUploadVO> mailUploadVOs = new ArrayList<MailUploadVO>();
				mailUploadVOs.add(mailUploadVOForSave);

        try {

				scannedMailDetailsVO = mailTrackingDefaultsDelegate
						.saveMailUploadDetails(mailUploadVOs,
								mailUploadVO.getScannedPort());
		//if("Y".equals(errorHandlingPopUpForm.getBulk())){
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}
			if (scannedMailDetailsVO != null) {

				try {
			  mailTrackingDefaultsDelegate
							.saveAndProcessMailBags(scannedMailDetailsVO);
				} catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
				}

			}

			log.log(Log.FINE, "***values to be saved*******", mailUploadVOs);

			}
		}

		if (txnIds != null && txnIds.length > 0)
			{
			for (String txnID : txnIds) {
		try{
						mailTrackingDefaultsDelegate.resolveTransaction(companyCode, txnID, null);
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
		  }
		}
			}
		errorHandlingPopUpForm.setCloseStatus("CLOSE");
		log.log(Log.FINE, "***save success*******");

        invocationContext.target=SAVE_SUCCESS;

	}

	private Collection<MailUploadVO> validateAndUpdateMailUploadVOs(
			LinkedHashMap<String, Collection<MailUploadVO>> mailUploadMap,
			ErrorHandlingPopUpForm errorHandlingPopUpForm) {

		Collection<MailUploadVO> oldMailUploadVOs = null;
		MailUploadVO oldMailUploadVO = null;
		if (errorHandlingPopUpForm.getSelectedtxnid() != null
				&& errorHandlingPopUpForm.getSelectedtxnid().trim().length() > 0) {
			if (mailUploadMap.containsKey(errorHandlingPopUpForm
					.getSelectedtxnid())) {
				oldMailUploadVOs = mailUploadMap.get(errorHandlingPopUpForm
						.getSelectedtxnid());

			}
		}

		if (oldMailUploadVOs != null && !oldMailUploadVOs.isEmpty()) {
			oldMailUploadVO = oldMailUploadVOs.iterator().next();
		}

		identifyAndUpdateNewChangesOfErrorVO(errorHandlingPopUpForm,
				oldMailUploadVO);
		mailUploadMap.remove(errorHandlingPopUpForm.getSelectedtxnid());
		Collection<MailUploadVO> finalMailUploadVOsForSave = new ArrayList<MailUploadVO>();
		finalMailUploadVOsForSave.add(oldMailUploadVO);
		updateAllSelectedErrorVOs(mailUploadMap, oldMailUploadVO,
				errorHandlingPopUpForm, finalMailUploadVOsForSave);

		return finalMailUploadVOsForSave;
	}

	

	

	private void identifyAndUpdateNewChangesOfErrorVO(
			ErrorHandlingPopUpForm errorHandlingPopUpForm,
			MailUploadVO mailUploadVO) {

		if ((mailUploadVO.getCarrierCode() != null && !mailUploadVO
				.getCarrierCode().equals(
						errorHandlingPopUpForm.getFlightCarrierCode()
								.toUpperCase()))
				|| (mailUploadVO.getCarrierCode() == null
						&& errorHandlingPopUpForm.getFlightCarrierCode() != null && errorHandlingPopUpForm
						.getFlightCarrierCode().trim().length() > 0)) {

			errorHandlingPopUpForm
					.setIsFlightCarrierCodeChanged(MailConstantsVO.FLAG_YES);
		}
		if (mailUploadVO.getFlightNumber() != null
				&& !mailUploadVO.getFlightNumber().equals(
						errorHandlingPopUpForm.getFlightNumber().toUpperCase())
				|| (mailUploadVO.getFlightNumber() == null
						&& errorHandlingPopUpForm.getFlightNumber() != null && errorHandlingPopUpForm
						.getFlightNumber().trim().length() > 0)) {

			errorHandlingPopUpForm
					.setIsFlightNumberChanged(MailConstantsVO.FLAG_YES);
		}

		if (mailUploadVO.getFlightDate() != null
				&& !mailUploadVO
						.getFlightDate()
						.toDisplayDateOnlyFormat()
						.toUpperCase()
						.equals(errorHandlingPopUpForm.getFlightDate()
								.toUpperCase())
				|| (mailUploadVO.getFlightDate() == null
						&& errorHandlingPopUpForm.getFlightDate() != null && errorHandlingPopUpForm
						.getFlightDate().trim().length() > 0)) {
			if (errorHandlingPopUpForm.getFlightDate() != null
					&& errorHandlingPopUpForm.getFlightDate().trim().length() > 0) {

				errorHandlingPopUpForm
						.setIsFlightDateChanged(MailConstantsVO.FLAG_YES);
			}
		}
		String containerType = "";
		if ("Y".equals(errorHandlingPopUpForm.getBulk())) {
			containerType = "B";
		} else
			{
			containerType = "U";
			}

		if (mailUploadVO.getContainerType() != null
				&& !mailUploadVO.getContainerType().equals(containerType)
				|| (mailUploadVO.getContainerType() == null
						&& containerType != null && containerType.trim()
						.length() > 0)) {

			errorHandlingPopUpForm
					.setIsContainerTypeChanged(MailConstantsVO.FLAG_YES);

		}
		if (mailUploadVO.getContainerNumber() != null
				&& !mailUploadVO.getContainerNumber().equals(
						errorHandlingPopUpForm.getContainer().toUpperCase())
				|| (mailUploadVO.getContainerNumber() == null
						&& errorHandlingPopUpForm.getContainer() != null && errorHandlingPopUpForm
						.getContainer().trim().length() > 0)) {

			errorHandlingPopUpForm
					.setIsContainerNumberChanged(MailConstantsVO.FLAG_YES);
		}
		if (mailUploadVO.getDestination() != null
				&& !mailUploadVO.getDestination().equals(
						errorHandlingPopUpForm.getDestination().toUpperCase())
				|| (mailUploadVO.getDestination() == null
						&& errorHandlingPopUpForm.getDestination() != null && errorHandlingPopUpForm
						.getDestination().trim().length() > 0)) {

			errorHandlingPopUpForm
					.setIsDestinationChanged(MailConstantsVO.FLAG_YES);

		}
		if (mailUploadVO.getMailCompanyCode() != null &&  (errorHandlingPopUpForm.getMailCompanyCode() != null)
				&& !mailUploadVO.getMailCompanyCode().equals(
						errorHandlingPopUpForm.getMailCompanyCode()
								.toUpperCase())
				|| (mailUploadVO.getMailCompanyCode() == null
						&& errorHandlingPopUpForm.getMailCompanyCode() != null && errorHandlingPopUpForm
						.getMailCompanyCode().trim().length() > 0)) {
			errorHandlingPopUpForm
					.setIsMailCompanyCodeChanged(MailConstantsVO.FLAG_YES);
		}

		if (mailUploadVO.getToPOU() != null
				&& !mailUploadVO.getToPOU().equals(
						errorHandlingPopUpForm.getPou().toUpperCase())
				|| (mailUploadVO.getToPOU() == null
						&& errorHandlingPopUpForm.getPou() != null && errorHandlingPopUpForm
						.getPou().trim().length() > 0)) {
			errorHandlingPopUpForm.setIsPouChanged(MailConstantsVO.FLAG_YES);
		}

		if (mailUploadVO.getFromPol() != null
				&& !mailUploadVO.getFromPol().equals(
						errorHandlingPopUpForm.getPol().toUpperCase())
				|| (mailUploadVO.getFromPol() == null
						&& errorHandlingPopUpForm.getPol() != null && errorHandlingPopUpForm
						.getPol().trim().length() > 0)) {
			errorHandlingPopUpForm.setIsPolChanged(MailConstantsVO.FLAG_YES);
		}

		String mailbagID = mailUploadVO.getMailTag();
		if (mailbagID != null && mailbagID.trim().length() == 29) {
			mailUploadVO.setOrginOE(mailbagID.substring(0, 6));
			mailUploadVO.setDestinationOE(mailbagID.substring(6, 12));
			mailUploadVO.setCategory(mailbagID.substring(12, 13));
			mailUploadVO.setSubClass(mailbagID.substring(13, 15));
			mailUploadVO.setYear(Integer.parseInt(mailbagID.substring(15, 16)));
			mailUploadVO.setDsn(mailbagID.substring(16, 20));
			mailUploadVO.setRsn(mailbagID.substring(20, 23));
			mailUploadVO.setHighestnumberIndicator(mailbagID.substring(23, 24));
			mailUploadVO.setRegisteredIndicator(mailbagID.substring(24, 25));
			//mailUploadVO.setWeight(Double.parseDouble(mailbagID.substring(25,29)));

			mailUploadVO.setWeight(new Measure(UnitConstants.MAIL_WGT, Double.parseDouble(mailbagID.substring(25,29)))); //Added by a-7550
			if (mailUploadVO.getOrginOE() != null
					&& !mailUploadVO.getOrginOE().equals(
							errorHandlingPopUpForm.getOoe().toUpperCase())) {
				errorHandlingPopUpForm
						.setIsOOEChanged(MailConstantsVO.FLAG_YES);

			}
			if (mailUploadVO.getDestinationOE() != null
					&& !mailUploadVO.getDestinationOE().equals(
							errorHandlingPopUpForm.getDoe().toUpperCase())) {
				errorHandlingPopUpForm
						.setIsDOEChanged(MailConstantsVO.FLAG_YES);

			}

			if (mailUploadVO.getCategory() != null
					&& !mailUploadVO.getCategory().equals(
							errorHandlingPopUpForm.getCategory().toUpperCase())) {
				errorHandlingPopUpForm
						.setIsCategoryChanged(MailConstantsVO.FLAG_YES);

			}

			if (mailUploadVO.getSubClass() != null
					&& !mailUploadVO.getSubClass().equals(
							errorHandlingPopUpForm.getSubclass().toUpperCase())) {
				errorHandlingPopUpForm
						.setIsSubClassChanged(MailConstantsVO.FLAG_YES);

			}
			if ((mailUploadVO.getYear() != Integer
					.parseInt(errorHandlingPopUpForm.getYear()))) {
				errorHandlingPopUpForm
						.setIsYearChanged(MailConstantsVO.FLAG_YES);

			}

			if (mailUploadVO.getDsn() != null
					&& !mailUploadVO.getDsn().equals(
							errorHandlingPopUpForm.getDsn().toUpperCase())) {
				errorHandlingPopUpForm
						.setIsDsnChanged(MailConstantsVO.FLAG_YES);

			}
			if (mailUploadVO.getRsn() != null
					&& !mailUploadVO.getRsn().equals(
							errorHandlingPopUpForm.getRsn().toUpperCase())) {
				errorHandlingPopUpForm
						.setIsRsnChanged(MailConstantsVO.FLAG_YES);

			}
			if (mailUploadVO.getHighestnumberIndicator() != null
					&& !mailUploadVO.getHighestnumberIndicator().equals(
							errorHandlingPopUpForm.getHni().toUpperCase())) {
				errorHandlingPopUpForm
						.setIsHniChanged(MailConstantsVO.FLAG_YES);

			}
			if (mailUploadVO.getRegisteredIndicator() != null
					&& !mailUploadVO.getRegisteredIndicator().equals(
							errorHandlingPopUpForm.getRi().toUpperCase())) {
				errorHandlingPopUpForm.setIsRiChanged(MailConstantsVO.FLAG_YES);

			}
			/*if ((mailUploadVO.getWeight() != Double
					.parseDouble(errorHandlingPopUpForm.getWeight))) {*/
			
			try {
				if ((Measure.compareTo(mailUploadVO.getWeight(),errorHandlingPopUpForm.getWeightMeasure())!=0)) {
					errorHandlingPopUpForm
							.setIsWeightChanged(MailConstantsVO.FLAG_YES);

				}
			} catch (UnitException e) {
				// TODO Auto-generated catch block
				log.log(Log.SEVERE, "UnitException",e.getMessage());
			}
		}
		if (mailUploadVO.getFromCarrierCode() != null
				&& !mailUploadVO.getFromCarrierCode().equals(
						errorHandlingPopUpForm.getTransferCarrier()
								.toUpperCase())
				|| (mailUploadVO.getFromCarrierCode() == null
						&& errorHandlingPopUpForm.getTransferCarrier() != null && errorHandlingPopUpForm
						.getTransferCarrier().trim().length() > 0)) {
			errorHandlingPopUpForm
					.setIsTransferCarrierChanged(MailConstantsVO.FLAG_YES);

		}

		updateSelectedmailUploadVO(mailUploadVO, errorHandlingPopUpForm);
	}
	
	private void updateSelectedmailUploadVO(MailUploadVO mailUploadVO,
			ErrorHandlingPopUpForm errorHandlingPopUpForm) {
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

		if (mailUploadVO != null) {

			//Modified as part of code quality work by A-7531 starts
			if ((mailUploadVO.getCarrierCode() != null && mailUploadVO
					.getCarrierCode().trim().length() > 0)
					&& (mailUploadVO.getFlightNumber() != null && mailUploadVO
							.getFlightNumber().trim().length() > 0)
					&& mailUploadVO.getFlightDate() != null) {
				
				//Modified as part of code quality work by A-7531 ends
				
				//Modified as part of bug ICRD-205448 by A-5526 starts
				if ("Y".equals(errorHandlingPopUpForm.getBulk())) {
					mailUploadVO.setContainerType("B");
				} else
					{
					mailUploadVO.setContainerType("U");
					}
				if (errorHandlingPopUpForm.getContainer() != null)
					{
						mailUploadVO.setContainerNumber(errorHandlingPopUpForm.getContainer().toUpperCase());
					}
				//Modified as part of bug ICRD-205448 by A-5526 ends
				mailUploadVO.setCarrierCode(errorHandlingPopUpForm
						.getFlightCarrierCode().toUpperCase());
				if (errorHandlingPopUpForm.getFlightNumber() != null)
					{
						mailUploadVO.setFlightNumber(errorHandlingPopUpForm.getFlightNumber().toUpperCase());
					}
				if (errorHandlingPopUpForm.getFlightDate() != null
						&& errorHandlingPopUpForm.getFlightDate().trim()
								.length() > 0) {

				mailUploadVO.setFlightDate(new LocalDate(logonAttributes
						.getAirportCode(), ARP, true)
						.setDate(errorHandlingPopUpForm.getFlightDate()));
			}

			if ("Y".equals(errorHandlingPopUpForm.getBulk())) {
				mailUploadVO.setContainerType("B");
			} else
				{
				mailUploadVO.setContainerType("U");
				}
				if (errorHandlingPopUpForm.getContainer() != null)
			mailUploadVO.setContainerNumber(errorHandlingPopUpForm
					.getContainer().toUpperCase());
				if (errorHandlingPopUpForm.getDestination() != null)
					{
						mailUploadVO.setDestination(errorHandlingPopUpForm.getDestination().toUpperCase());
					}
				if (errorHandlingPopUpForm.getPou() != null) {
					mailUploadVO.setToPOU(errorHandlingPopUpForm.getPou()
							.toUpperCase());
					mailUploadVO.setContainerPOU(errorHandlingPopUpForm
							.getPou().toUpperCase());
				}
				if (errorHandlingPopUpForm.getDoe() != null
						&& errorHandlingPopUpForm.getDoe().trim().length() > 0
						&& !mailUploadVO.getScanType().equals(
								MailConstantsVO.MAIL_STATUS_ACCEPTED)) {
					mailUploadVO.setToPOU(errorHandlingPopUpForm.getDoe()
							.substring(2, 5).toUpperCase());

					mailUploadVO.setContainerPOU(errorHandlingPopUpForm
							.getDoe().substring(2, 5).toUpperCase());
				}
			}
			// Added as part of ICRD-124715 starts
			if (errorHandlingPopUpForm.getMailCompanyCode() != null
					&& errorHandlingPopUpForm.getMailCompanyCode().trim()
							.length() != 0) {
				mailUploadVO.setMailCompanyCode(errorHandlingPopUpForm
						.getMailCompanyCode().toUpperCase());
			}
			// Added as part of ICRD-124715 ends

			if (errorHandlingPopUpForm.getPol() != null
					&& errorHandlingPopUpForm.getPol().trim().length() > 0)
				{
					mailUploadVO.setFromPol(errorHandlingPopUpForm.getPol().toUpperCase());
				}
			// Added by A-5945 for ICRD-96162 starts

			// Added by A-5945 for ICRD-96162 ends

			if (errorHandlingPopUpForm.getPol() != null
					&& errorHandlingPopUpForm.getPol().trim().length() > 0)
				{
					mailUploadVO.setContainerPol(errorHandlingPopUpForm.getPol().toUpperCase());
				}
			else if (errorHandlingPopUpForm.getOoe() != null
					&& errorHandlingPopUpForm.getOoe().trim().length() > 0)
				{
					mailUploadVO.setContainerPol(errorHandlingPopUpForm.getOoe().substring(2, 5).toUpperCase());
				}
		}

		if (mailUploadVO.getMailTag() != null
				&& mailUploadVO.getMailTag().trim().length() == 29) {
			if (errorHandlingPopUpForm.getOoe() != null
					&& errorHandlingPopUpForm.getOoe().trim().length() != 0) {
				mailUploadVO.setOrginOE(errorHandlingPopUpForm.getOoe()
						.toUpperCase());

				if (errorHandlingPopUpForm.getDoe() != null
						&& errorHandlingPopUpForm.getDoe().trim().length() != 0) {
					mailUploadVO.setDestinationOE(errorHandlingPopUpForm
							.getDoe().toUpperCase());
				}
				if (errorHandlingPopUpForm.getCategory() != null
						&& errorHandlingPopUpForm.getCategory().trim().length() != 0) {
					mailUploadVO.setCategory(errorHandlingPopUpForm
							.getCategory().toUpperCase());
				}
				if (errorHandlingPopUpForm.getSubclass() != null
						&& errorHandlingPopUpForm.getSubclass().trim().length() != 0) {
					mailUploadVO.setSubClass(errorHandlingPopUpForm
							.getSubclass().toUpperCase());
				}
				if (errorHandlingPopUpForm.getYear() != null
						&& errorHandlingPopUpForm.getYear().trim().length() != 0) {
					mailUploadVO.setYear(Integer
							.parseInt(errorHandlingPopUpForm.getYear()));
				}
				if (errorHandlingPopUpForm.getDsn() != null
						&& errorHandlingPopUpForm.getDsn().trim().length() != 0) {
			mailUploadVO.setDsn(errorHandlingPopUpForm.getDsn());
			}
				if (errorHandlingPopUpForm.getRsn() != null
						&& errorHandlingPopUpForm.getRsn().trim().length() != 0) {
			mailUploadVO.setRsn(errorHandlingPopUpForm.getRsn());
			}
				if (errorHandlingPopUpForm.getHni() != null
						&& errorHandlingPopUpForm.getHni().trim().length() != 0) {
					mailUploadVO
							.setHighestnumberIndicator(errorHandlingPopUpForm
									.getHni());
				}
				if (errorHandlingPopUpForm.getRi() != null
						&& errorHandlingPopUpForm.getRi().trim().length() != 0) {
					mailUploadVO.setRegisteredIndicator(errorHandlingPopUpForm
							.getRi());
				}
				if (errorHandlingPopUpForm.getWeight() != null
						&& errorHandlingPopUpForm.getWeight().trim().length() != 0) {
					String weight = errorHandlingPopUpForm.getWeight();
					// Added by A-5945 for ICRD-107696 starts
					// For turkish locale the weight always come as decimal
					// value
					if (weight.contains(".")) {
						int len = weight.length();
						int i = weight.indexOf(".");
						String wgt = weight.substring(i + 1, len);
						weight = wgt;
					}
					// Added by A-5945 for ICRD-107696 ends
					if (weight.length() == 3) {
						weight = new StringBuilder("0").append(weight)
								.toString();
					}
					if (weight.length() == 2) {
						weight = new StringBuilder("00").append(weight)
								.toString();
					}
					if (weight.length() == 1) {
						weight = new StringBuilder("000").append(weight)
								.toString();
					}
					errorHandlingPopUpForm.setWeight(weight);
					//mailUploadVO.setWeight(Double.parseDouble(errorHandlingPopUpForm.getWeight()));
					mailUploadVO.setWeight(new Measure(UnitConstants.MAIL_WGT, Double.parseDouble(errorHandlingPopUpForm.getWeight()))); //Added by A-7550
				}
			}
			// Added by A-5945 for updating scandate at the time of resolving
			// each transactions
			if (mailUploadVO.getDateTime() != null
					&& mailUploadVO.getDateTime().trim().length() > 0) {  
                  //Commented as part of IASCB-36005
				/*LocalDate scanDat = new LocalDate(
						logonAttributes.getAirportCode(), Location.ARP, true);
			    mailUploadVO.setDateTime(scanDat.toDisplayFormat());*/
				// SimpleDateFormat sdf = new
				// SimpleDateFormat(DISPLAY_DATE_FORMAT);
				// return sdf.format(scanDat);
			}
			// Added by A-5945 for ICRD-113473 starts
			if (errorHandlingPopUpForm.getTransferCarrier() != null
					&& errorHandlingPopUpForm.getTransferCarrier().trim()
							.length() > 0) {
				// Modified as part of Bug ICRD-143487
				mailUploadVO.setFromCarrierCode(errorHandlingPopUpForm
						.getTransferCarrier().toUpperCase());
			}else{
				mailUploadVO.setFromCarrierCode(errorHandlingPopUpForm.getTransferCarrier());  
			}
			// Added by A-5945 for ICRD-113473 ends
			String mailbagId = mailUploadVO.getMailTag();

			StringBuilder mailTag = new StringBuilder();
			mailTag.append(errorHandlingPopUpForm.getOoe())
					.append(errorHandlingPopUpForm.getDoe())
					.append(errorHandlingPopUpForm.getCategory())
					.append((errorHandlingPopUpForm.getSubclass())
							.toUpperCase())
					.append(errorHandlingPopUpForm.getYear())
					.append(errorHandlingPopUpForm.getDsn())
					.append(errorHandlingPopUpForm.getRsn())
					.append(errorHandlingPopUpForm.getHni())
					.append(errorHandlingPopUpForm.getRi())
					.append(errorHandlingPopUpForm.getWeight());
			String mailBagId = mailTag.toString().toUpperCase();
			if (mailBagId != null && mailBagId.trim().length() == 29) {
				mailUploadVO.setMailTag(mailBagId);
			} else if (mailUploadVO.getContainerNumber() == null
					&& mailbagId != null && mailbagId.trim().length() > 0) {
				mailUploadVO.setMailTag(mailUploadVO.getContainerNumber());
			} else {
				mailUploadVO.setMailTag("");
			}

		}

	}

	private void updateAllSelectedErrorVOs(
			LinkedHashMap<String, Collection<MailUploadVO>> mailUploadMap,
			MailUploadVO updatedMailUploadVO,
			ErrorHandlingPopUpForm errorHandlingPopUpForm,
			Collection<MailUploadVO> finalMailUploadVOsForSave) {
		Collection<Collection<MailUploadVO>> mailuploadVOsCol = (Collection<Collection<MailUploadVO>>) mailUploadMap
				.values();
		log.log(Log.INFO, "mailuploadvos", mailuploadVOsCol);
		for (Collection<MailUploadVO> mailuploadvos : mailuploadVOsCol) {
			if (mailuploadvos != null && mailuploadvos.size() > 0) {

				MailUploadVO currentMailUploadVO = mailuploadvos.iterator()
						.next();

				updateMailUploadVO(currentMailUploadVO, updatedMailUploadVO,
						errorHandlingPopUpForm);
				finalMailUploadVOsForSave.add(currentMailUploadVO);

			}
		}

	}

	private void updateMailUploadVO(MailUploadVO currentMailUploadVO,
			MailUploadVO updatedMailUploadVO,
			ErrorHandlingPopUpForm errorHandlingPopUpForm) {
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String mailbagID = currentMailUploadVO.getMailTag();
		if (mailbagID != null && mailbagID.trim().length() == 29) {
			currentMailUploadVO.setOrginOE(mailbagID.substring(0, 6));
			currentMailUploadVO.setDestinationOE(mailbagID.substring(6, 12));
			currentMailUploadVO.setCategory(mailbagID.substring(12, 13));
			currentMailUploadVO.setSubClass(mailbagID.substring(13, 15));
			currentMailUploadVO.setYear(Integer.parseInt(mailbagID.substring(
					15, 16)));
			currentMailUploadVO.setDsn(mailbagID.substring(16, 20));
			currentMailUploadVO.setRsn(mailbagID.substring(20, 23));
			currentMailUploadVO.setHighestnumberIndicator(mailbagID.substring(
					23, 24));
			currentMailUploadVO.setRegisteredIndicator(mailbagID.substring(24,
					25));
			//currentMailUploadVO.setWeight(Double.parseDouble(mailbagID
			//		.substring(25, 29)));
			currentMailUploadVO.setWeight(new Measure(UnitConstants.MAIL_WGT, Double.parseDouble(mailbagID.substring(25, 29)))); //Added by A-7550
		} else {
			currentMailUploadVO.setOrginOE("");
			currentMailUploadVO.setDestinationOE("");
			currentMailUploadVO.setCategory("");
			currentMailUploadVO.setSubClass("");
			currentMailUploadVO.setYear(0);
			currentMailUploadVO.setDsn("");
			currentMailUploadVO.setRsn("");
			currentMailUploadVO.setHighestnumberIndicator("");
			currentMailUploadVO.setRegisteredIndicator("");
			//currentMailUploadVO.setWeight(0);
			currentMailUploadVO.setWeight(new Measure(UnitConstants.MAIL_WGT, 0)); //Added by A-7550
		}
		//Modified as part of code quality work by A-7531 starts
		if ((currentMailUploadVO.getCarrierCode() != null && currentMailUploadVO
				.getCarrierCode().trim().length() > 0)
				&& (currentMailUploadVO.getFlightNumber() != null && currentMailUploadVO
						.getFlightNumber().trim().length() > 0)
				&& currentMailUploadVO.getFlightDate() != null) {
			//Modified as part of code quality work by A-7531 ends
			
			
			if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
					.getIsFlightCarrierCodeChanged())) {
				currentMailUploadVO.setCarrierCode(errorHandlingPopUpForm
						.getFlightCarrierCode().toUpperCase());
			}
			if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
					.getIsFlightNumberChanged())) {
				currentMailUploadVO.setFlightNumber(errorHandlingPopUpForm
						.getFlightNumber().toUpperCase());
			}
			if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
					.getIsFlightDateChanged())) {
				if (errorHandlingPopUpForm.getFlightDate() != null
						&& errorHandlingPopUpForm.getFlightDate().trim()
								.length() > 0) {

					currentMailUploadVO.setFlightDate(new LocalDate(
							logonAttributes.getAirportCode(), ARP, true)
							.setDate(errorHandlingPopUpForm.getFlightDate()));
				}

			}
			if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
					.getIsContainerTypeChanged())) {
				if ("Y".equals(errorHandlingPopUpForm.getBulk())) {
					currentMailUploadVO.setContainerType("B");
				} else
					{
					currentMailUploadVO.setContainerType("U");
					}
			}
			if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
					.getIsContainerNumberChanged())) {
				currentMailUploadVO.setContainerNumber(errorHandlingPopUpForm
						.getContainer().toUpperCase());
			}
			if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
					.getIsDestinationChanged())) {
				currentMailUploadVO.setDestination(errorHandlingPopUpForm
						.getDestination().toUpperCase());
			}
			if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
					.getIsPolChanged())) {
				currentMailUploadVO.setFromPol(errorHandlingPopUpForm.getPol()
						.toUpperCase());
				currentMailUploadVO.setContainerPol(errorHandlingPopUpForm
						.getPol().toUpperCase());
			}
			if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
					.getIsPouChanged())) {
				currentMailUploadVO.setToPOU(errorHandlingPopUpForm.getPou()
						.toUpperCase());
				currentMailUploadVO.setContainerPOU(errorHandlingPopUpForm
						.getPou().toUpperCase());
			}

		}
		if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
				.getIsMailCompanyCodeChanged())) {
			currentMailUploadVO.setMailCompanyCode(errorHandlingPopUpForm
					.getMailCompanyCode().toUpperCase());
		}
		if (currentMailUploadVO.getMailTag() != null
				&& currentMailUploadVO.getMailTag().trim().length() == 29) {
			if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
					.getIsOOEChanged())) {
				currentMailUploadVO.setOrginOE(errorHandlingPopUpForm.getOoe()
						.toUpperCase());
				errorHandlingPopUpForm
						.setIsMailTagChanged(MailConstantsVO.FLAG_YES);
			}
			if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
					.getIsDOEChanged())) {
				currentMailUploadVO.setDestinationOE(errorHandlingPopUpForm
						.getDoe().toUpperCase());
				errorHandlingPopUpForm
						.setIsMailTagChanged(MailConstantsVO.FLAG_YES);
			}
			if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
					.getIsCategoryChanged())) {
				currentMailUploadVO.setCategory(errorHandlingPopUpForm
						.getCategory().toUpperCase());
				errorHandlingPopUpForm
						.setIsMailTagChanged(MailConstantsVO.FLAG_YES);
			}
			if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
					.getIsSubClassChanged())) {
				currentMailUploadVO.setSubClass(updatedMailUploadVO
						.getSubClass().toUpperCase());
				errorHandlingPopUpForm
						.setIsMailTagChanged(MailConstantsVO.FLAG_YES);
			}
			if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
					.getIsYearChanged())) {
				currentMailUploadVO.setYear(Integer
						.parseInt(errorHandlingPopUpForm.getYear()));
				errorHandlingPopUpForm
						.setIsMailTagChanged(MailConstantsVO.FLAG_YES);
			}
			if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
					.getIsDsnChanged())) {
				currentMailUploadVO.setDsn(errorHandlingPopUpForm.getDsn());
				errorHandlingPopUpForm
						.setIsMailTagChanged(MailConstantsVO.FLAG_YES);
			}
			if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
					.getIsRsnChanged())) {
				currentMailUploadVO.setRsn(errorHandlingPopUpForm.getRsn());
				errorHandlingPopUpForm
						.setIsMailTagChanged(MailConstantsVO.FLAG_YES);
			}
			if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
					.getIsHniChanged())) {
				currentMailUploadVO
						.setHighestnumberIndicator(errorHandlingPopUpForm
								.getHni());
				errorHandlingPopUpForm
						.setIsMailTagChanged(MailConstantsVO.FLAG_YES);
			}
			if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
					.getIsRiChanged())) {
				currentMailUploadVO
						.setRegisteredIndicator(errorHandlingPopUpForm.getRi());
				errorHandlingPopUpForm
						.setIsMailTagChanged(MailConstantsVO.FLAG_YES);
			}
			if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
					.getIsWeightChanged())) {
				if (errorHandlingPopUpForm.getWeight() != null
						&& errorHandlingPopUpForm.getWeight().trim().length() != 0) {
					String weight = errorHandlingPopUpForm.getWeight();
					// Added by A-5945 for ICRD-107696 starts
					// For turkish locale the weight always come as decimal
					// value
					if (weight.contains(".")) {
						int len = weight.length();
						int i = weight.indexOf(".");
						String wgt = weight.substring(i + 1, len);
						weight = wgt;
					}
					// Added by A-5945 for ICRD-107696 ends
					if (weight.length() == 3) {
						weight = new StringBuilder("0").append(weight)
								.toString();
					}
					if (weight.length() == 2) {
						weight = new StringBuilder("00").append(weight)
								.toString();
					}
					if (weight.length() == 1) {
						weight = new StringBuilder("000").append(weight)
								.toString();
					}
					errorHandlingPopUpForm.setWeight(weight);
					//currentMailUploadVO.setWeight(Double
					//		.parseDouble(errorHandlingPopUpForm.getWeight()));
					currentMailUploadVO.setWeight(new Measure(UnitConstants.MAIL_WGT, Double
									.parseDouble(errorHandlingPopUpForm.getWeight()))); //Added by A-7550
				}
				errorHandlingPopUpForm
						.setIsMailTagChanged(MailConstantsVO.FLAG_YES);
			}
			if (currentMailUploadVO.getMailTag() != null
					&& currentMailUploadVO.getMailTag().trim().length() > 0
					&& currentMailUploadVO.getMailTag().trim().length() < 29) {
				currentMailUploadVO.setMailTag(errorHandlingPopUpForm
						.getContainer());
			}
		}
		if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
				.getIsTransferCarrierChanged())) {
			currentMailUploadVO.setFromCarrierCode(errorHandlingPopUpForm
					.getTransferCarrier().toUpperCase());
		}
		currentMailUploadVO.setDateTime(updatedMailUploadVO.getDateTime());

		if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm
				.getIsMailTagChanged())) {

			StringBuilder mailTag = new StringBuilder();
			String weight = String.valueOf(errorHandlingPopUpForm.getWeight());
			if (weight.contains(".")) {

				int i = weight.indexOf(".");
				String wgt = weight.substring(0, i);
				weight = wgt;
			}
			// Added by A-5945 for ICRD-107696 ends
			if (weight.length() == 3) {
				weight = new StringBuilder("0").append(weight).toString();
			}
			if (weight.length() == 2) {
				weight = new StringBuilder("00").append(weight).toString();
			}
			if (weight.length() == 1) {
				weight = new StringBuilder("000").append(weight).toString();
			}
			mailTag.append(currentMailUploadVO.getOrginOE())
					.append(currentMailUploadVO.getDestinationOE())
					.append(currentMailUploadVO.getCategory())
					.append((currentMailUploadVO.getSubClass()).toUpperCase())
					.append(currentMailUploadVO.getYear())
					.append(currentMailUploadVO.getDsn())
					.append(currentMailUploadVO.getRsn())
					.append(currentMailUploadVO.getHighestnumberIndicator())
					.append(currentMailUploadVO.getRegisteredIndicator())
					.append(weight);
			String mailBagId = mailTag.toString().toUpperCase();
			if (mailBagId != null && mailBagId.trim().length() == 29) {
				currentMailUploadVO.setMailTag(mailBagId);
			} else if (currentMailUploadVO.getContainerNumber() == null
					&& mailBagId != null && mailBagId.trim().length() > 0) {
				currentMailUploadVO.setMailTag(errorHandlingPopUpForm
						.getContainer());
			} else {
				currentMailUploadVO.setMailTag("");
			}
		}

		log.log(Log.FINE, " in side if looop",
				currentMailUploadVO.getProcessPoint());

		if (currentMailUploadVO.getCarrierCode() == null
				&& currentMailUploadVO.getFlightNumber() == null
				&& currentMailUploadVO.getFlightDate() == null) {
			currentMailUploadVO.setCarrierCode(null);
			currentMailUploadVO.setFlightNumber(null);
			currentMailUploadVO.setDestination(null);
			currentMailUploadVO.setFlightDate(null);
			currentMailUploadVO.setContainerNumber(null);
			currentMailUploadVO.setToPOU(null);
			currentMailUploadVO.setContainerPOU(null);
		}

	}

	private LinkedHashMap<String, Collection<MailUploadVO>> fetchSelectedErrorVOs(
			String companyCode, String[] txnIds) {

		LinkedHashMap<String, Collection<MailUploadVO>> mailUploadVOmap = new LinkedHashMap<String, Collection<MailUploadVO>>();
		for (String txnID : txnIds) {
			Object[] txndetail = getTxnParameters(companyCode, txnID);
			for (Object obj : txndetail) {
				ArrayList<MailUploadVO> mailBagVOs = null;
				if (obj instanceof Collection) {
					mailBagVOs = (ArrayList<MailUploadVO>) obj;
					log.log(Log.FINE, "object mails", mailBagVOs);
					if (mailBagVOs != null && !mailBagVOs.isEmpty()) {
						mailUploadVOmap.put(txnID, mailBagVOs);
					}
				}

			}

		}

		return mailUploadVOmap;

	}

	private Object[] getTxnParameters(String companyCode, String txnId) {

		Object[] txndetails1 = null;
		try {

			MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
			txndetails1 = mailTrackingDefaultsDelegate.getTxnParameters(
					companyCode, txnId);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
		}
		return txndetails1;
	}

}
