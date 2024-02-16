/*
 * GPABillingAAController.java Created on Feb 12, 2019
 *
 * Copyright 2014 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.aa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.mra.defaults.MRABillingMaster;
import com.ibsplc.icargo.business.mail.mra.gpabilling.CN51Summary;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceDetailsReportVO;
import com.ibsplc.icargo.business.mail.mra.proxy.CRADefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.FrameworkLockProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MailTrackingDefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MailTrackingMRAProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MessageBrokerConfigProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.msgbroker.config.handling.vo.MessageAddressDetailVO;
import com.ibsplc.icargo.business.msgbroker.config.handling.vo.MessageAddressFilterVO;
import com.ibsplc.icargo.business.msgbroker.config.handling.vo.MessageAddressVO;
import com.ibsplc.icargo.business.msgbroker.config.mode.vo.MessageModeParameterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.eai.base.vo.CommunicationVO;
import com.ibsplc.icargo.framework.event.annotations.Raise;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.message.vo.EmailVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.lock.ObjectNotLockedException;
import com.ibsplc.xibase.server.framework.persistence.lock.TransactionLockVO;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

@Module("mail")
@SubModule("mra")
public class GPABillingAAController {

	private Log log = LogFactory.getLogger("MRA:GPABILLING");
	private static final String BLANK = "";
	private static final String OK = "OK";
	private static String isSuccessFlag = "N";
	private static final String COMMA = ",";
	private static final String GENINVMRA = "GENINVMRA";
	private static final String LOCKSCREENID = "MRA009";
	private static final String LOCKREMARK = "MANUAL LOCK";
	private static final String LOCkDESC = "GENERATE INVOICE LOCK";
	private static final String GENINV = "GENINV";
	private static final String MSGBROKER_EMAIL_INTERFACE_PARAMETER = "msgbroker.message.emailinterfacesystem";
	private static final String MSGBROKER_EMAIL_INTERFACE = "EMAIL";
	private static final String OVERRIDEROUNDING="mailtracking.mra.overrideroundingvalue";
	/**
	 * Method : GPABillingAAController.generateInvoiceAA Added by : A-5526 on
	 * 12-Feb-2019 Used for : ICRD-235779 generateInvoice specific for AA
	 * Parameters : @param generateInvoiceFilterVO Parameters : @throws
	 * SystemException Return type : void
	 * 
	 * @throws SystemException
	 */

	public void generateInvoiceAA(GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException {
		log.entering("GPABillingAAController", "generateInvoiceTK");

		try {
			Collection<PostalAdministrationVO> postalAdministrationVOs = findAllPACodes(generateInvoiceFilterVO);

			sendEmailsForPA(postalAdministrationVOs, generateInvoiceFilterVO);
		} catch (SystemException se) {
			log.log(Log.SEVERE, "Exception", se);

		}
	}

	private void sendEmailsForPA(Collection<PostalAdministrationVO> postalAdministrationVOs,
			GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException {

		for (PostalAdministrationVO postalAdministrationVO : postalAdministrationVOs) {
			try {
				Collection<PostalAdministrationVO> palist = new ArrayList<PostalAdministrationVO>(1);
				palist.add(postalAdministrationVO);
				new MailTrackingMRAProxy().sendEmailforAA(palist, generateInvoiceFilterVO);
			} catch (ProxyException e) {
				throw new SystemException(e.getMessage());
			}
		}
	}

	/**
	 * Method : GPABillingAAController.findAllPACodes Added by : A-5526 on
	 * 12-Feb-2019 Used for : ICRD-235779 finding All PAs Parameters : @param
	 * generateInvoiceFilterVO Parameters : @return Parameters : @throws
	 * SystemException Return type : Collection<PostalAdministrationVO>
	 */
	public Collection<PostalAdministrationVO> findAllPACodes(GenerateInvoiceFilterVO generateInvoiceFilterVO)
			throws SystemException {
		log.entering("GPABillingControlle", "findAllPACodes");
		Collection<PostalAdministrationVO> postalAdministrationVOs = null;
		try {
			postalAdministrationVOs = new MailTrackingDefaultsProxy().findAllPACodes(generateInvoiceFilterVO);
		} catch (ProxyException e) {
			log.log(Log.SEVERE, "ExceptionCaught", e);
			throw new SystemException(e.getErrors());
		}
		log.exiting("GPABillingControlle", "findAllPACodes");
		return postalAdministrationVOs;
	}

	/**
	 * Method : GPABillingAAController.sendEmailforAA Added by : A-5526 on
	 * 12-Feb-2019 Used for : ICRD-235779 sendEmailforAA Parameters : @param
	 * postalAdministrationVOs,generateInvoiceFilterVO Parameters : @return
	 * Parameters : @throws SystemException Return type : void
	 */

	public void sendEmailforAA(Collection<PostalAdministrationVO> postalAdministrationVOs,
			GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException {
		String outParameter[] = null;
		String invNumber = null;
		StringBuilder remarks = new StringBuilder();
		StringBuilder finalRemarks = new StringBuilder();
		String txnSta = BLANK;
		String failureFlag = null;
		String successFlag = null;
		String Syspar = "System Parameter Not found";
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Collection<String> invoiceNumbers = new ArrayList<String>();
		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
		//Added by A-8527 for ICRD-324512 starts
				Map<String,String> systemParameterMap =null;
				String overrideRounding =null;
				Collection<String> systemParCodes = new ArrayList<String>();
				systemParCodes.add(OVERRIDEROUNDING);
				try {
					systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
				} catch (ProxyException proxyException) {
					throw new SystemException(proxyException.getMessage());
				}
				if(systemParameterMap !=null && systemParameterMap.size()>0){
					overrideRounding = systemParameterMap.get(OVERRIDEROUNDING);
				}
				//Added by A-8527 for ICRD-324512 Ends
		if (postalAdministrationVOs != null && postalAdministrationVOs.size() > 0) {
			for (PostalAdministrationVO postalAdministrationVO : postalAdministrationVOs) {

				boolean success = false;
				GenerateInvoiceFilterVO invoiceFilterVO = new GenerateInvoiceFilterVO();
				invoiceFilterVO.setCompanyCode(postalAdministrationVO.getCompanyCode());
				invoiceFilterVO.setBillingFrequency(postalAdministrationVO.getBillingFrequency());
				invoiceFilterVO.setBillingPeriodFrom(generateInvoiceFilterVO.getBillingPeriodFrom());
				invoiceFilterVO.setBillingPeriodTo(generateInvoiceFilterVO.getBillingPeriodTo());
				invoiceFilterVO.setCountryCode(postalAdministrationVO.getCountryCode());
				invoiceFilterVO.setGpaCode(postalAdministrationVO.getPaCode());
				invoiceFilterVO.setGpaName(postalAdministrationVO.getPaName());
				invoiceFilterVO.setInvoiceType(generateInvoiceFilterVO.getInvoiceType());// Added
																							// for
																							// ICRD-211662
				invoiceFilterVO.setAddNew(generateInvoiceFilterVO.isAddNew());
				LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
				invoiceFilterVO.setCurrentDate(currentDate);
				outParameter = MRABillingMaster.generateInvoiceTK(invoiceFilterVO);
				if (outParameter != null) {

					log.log(Log.FINE, "outParameter[0]", outParameter[0]);
					log.log(Log.FINE, "outParameter[1]", outParameter[1]);
					log.log(Log.FINE, "outParameter[2]", outParameter[2]);

					String[] parameter = outParameter[0].split("#");

					log.log(Log.FINE, "Parameters :>>>>>>>>>>>>>>>>", parameter);
					if (parameter != null && parameter.length > 0) {

						if (OK.equals(parameter[0])) {

							CN51CN66FilterVO cN51CN66FilterVO = null;
							Collection<CN66DetailsVO> cn66DetailsVos = null;
							Collection<CN51DetailsVO> cn51DetailsVos = null;
							if (parameter.length > 1 && parameter[1] != null && parameter[1].trim().length() > 0) {
								int len = parameter.length - 1;
								for (int i = 1; i <= len; i++) {
									cN51CN66FilterVO = new CN51CN66FilterVO();
									cN51CN66FilterVO.setCompanyCode(postalAdministrationVO.getCompanyCode());
									cN51CN66FilterVO.setInvoiceNumber(parameter[i]);
									cN51CN66FilterVO.setGpaCode(postalAdministrationVO.getPaCode());
									cn66DetailsVos = CN51Summary.generateCN66Report(cN51CN66FilterVO);
									cn51DetailsVos = CN51Summary.generateCN51Report(cN51CN66FilterVO);
									//added by A-8527 for ICRD-324283 starts
									for(CN66DetailsVO cn66DetailsVo:cn66DetailsVos){
										cn66DetailsVo.setSettlementCurrencyCode(postalAdministrationVO.getSettlementCurrencyCode());
										cn66DetailsVo.setOverrideRounding(overrideRounding);
									}
									//added by A-8527 for ICRD-324283 Ends
									invoiceNumbers.add(parameter[i]);
									invNumber = parameter[i];
									log.log(Log.FINE, "Invoice number", invNumber);

									if (PostalAdministrationVO.FLAG_YES
											.equals(postalAdministrationVO.getAutoEmailReqd())) {
										if (cn66DetailsVos != null && cn66DetailsVos.size() > 0) {
											InvoiceDetailsReportVO invoiceDetailsReportVO = new InvoiceDetailsReportVO();
											invoiceDetailsReportVO.setPaName(postalAdministrationVO.getPaName());
											invoiceDetailsReportVO.setAddress(postalAdministrationVO.getAddress());
											invoiceDetailsReportVO.setCity(postalAdministrationVO.getCity());
											invoiceDetailsReportVO.setState(postalAdministrationVO.getState());
											invoiceDetailsReportVO.setCountry(postalAdministrationVO.getCountry());
											invoiceDetailsReportVO.setPhone1(postalAdministrationVO.getPhone1());
											invoiceDetailsReportVO.setPhone2(postalAdministrationVO.getPhone2());
											invoiceDetailsReportVO.setFax(postalAdministrationVO.getFax());
											invoiceDetailsReportVO.setFromBillingPeriod(
													generateInvoiceFilterVO.getBillingPeriodFrom());
											invoiceDetailsReportVO
													.setToBillingPeriod(generateInvoiceFilterVO.getBillingPeriodTo());

											log.log(Log.FINE, "Details send to sendEmail cn66DetailsVos:-",
													cn66DetailsVos);
											log.log(Log.FINE, "Details send to sendEmail cn51DetailsVos:-",
													cn51DetailsVos);
											log.log(Log.FINE, "Details send to sendEmail invoiceDetailsReportVO:-",
													invoiceDetailsReportVO);
											log.log(Log.FINE, "Details send to sendEmail cN51CN66FilterVO:-",
													cN51CN66FilterVO);
											log.log(Log.FINE, "Details send to sendEmail emailID:-",
													postalAdministrationVO.getEmail());
											StringBuilder emailIds = new StringBuilder();
											if (postalAdministrationVO.getEmail() != null) {
												emailIds.append(postalAdministrationVO.getEmail());
												if (postalAdministrationVO.getSecondaryEmail1() != null
														&& !postalAdministrationVO.getSecondaryEmail1().isEmpty()) {
													emailIds.append(",")
															.append(postalAdministrationVO.getSecondaryEmail1());
												}
												if (postalAdministrationVO.getSecondaryEmail2() != null
														&& !postalAdministrationVO.getSecondaryEmail2().isEmpty()) {
													emailIds.append(",")
															.append(postalAdministrationVO.getSecondaryEmail2());
												}
												invoiceDetailsReportVO.setEmail(emailIds.toString());
											}
											try {
												sendEmail(cn66DetailsVos, cn51DetailsVos, invoiceDetailsReportVO,
														cN51CN66FilterVO, emailIds.toString());
											} catch (SystemException exception) {
												log.log(Log.FINE, "Issue in sending email invoice for the ",
														postalAdministrationVO.getPaCode());
											}
										}
									}
								}
							}

							success = true;
							successFlag = "Y";
							isSuccessFlag = "Y";
							log.log(Log.FINE, "status", success);
						} else {
							success = false;
							failureFlag = "Y";
							// Added by A-3434 for CR ICRD-114599 on 29SEP2015
							// begins..For Invoice log screen

							log.log(Log.FINE, "outParameter[1] before setting rmk111", outParameter[1]);

							// if ( outParameter[1].trim().length() > 0) {

							remarks.append(outParameter[1]);

							log.log(Log.FINE, "remarks inside", remarks.toString());
							// }

							if (Syspar.equals(outParameter[0])) {

								break;

							}

							if (postalAdministrationVOs.size() > 1) {
								remarks.append(COMMA);
							}

						}
					}

				} // Release Locks for Each PA
				Collection<LockVO> lockvos = new ArrayList<LockVO>();
				TransactionLockVO generateInvoiceLockVO = new TransactionLockVO(GENINVMRA);
				generateInvoiceLockVO.setAction(GENINV);
				generateInvoiceLockVO.setClientType(ClientType.APPLICATION);
				generateInvoiceLockVO.setCompanyCode(logonAttributes.getCompanyCode());
				generateInvoiceLockVO.setDescription(LOCkDESC);
				generateInvoiceLockVO.setRemarks(LOCKREMARK);
				generateInvoiceLockVO.setStationCode(logonAttributes.getStationCode());
				generateInvoiceLockVO.setScreenId(LOCKSCREENID);
				lockvos.add(generateInvoiceLockVO);
				releaseLocks(lockvos);
				isSuccessFlag = "N";
			}
		}

		if (generateInvoiceFilterVO.getCountryCode() != null
				&& generateInvoiceFilterVO.getCountryCode().trim().length() > 0) {
			finalRemarks.append("Country:").append(generateInvoiceFilterVO.getCountryCode()).append(COMMA);
		}
		if (generateInvoiceFilterVO.getGpaCode() != null && generateInvoiceFilterVO.getGpaCode().trim().length() > 0) {
			finalRemarks.append("GpaCod:").append(generateInvoiceFilterVO.getGpaCode()).append(COMMA);
		}

		finalRemarks.append("From Date:")
				.append(generateInvoiceFilterVO.getBillingPeriodFrom().toDisplayFormat("dd/MM/yyyy")).append(COMMA)
				.append("To Date:").append(generateInvoiceFilterVO.getBillingPeriodTo().toDisplayFormat("dd/MM/yyyy"));

		if ("Y".equals(failureFlag) && "Y".equals(isSuccessFlag)) {
			txnSta = "P";
			finalRemarks.append("\n").append("Failure Reason:");
		} else if ("Y".equals(failureFlag)) {
			txnSta = "F";
			finalRemarks.append("\n").append("Failure Reason:");
		} else if ("Y".equals(successFlag)) {
			txnSta = "C";
			remarks.append(COMMA);
			if ("P".equals(generateInvoiceFilterVO.getInvoiceType())) {
				if (outParameter[0] != null && outParameter[0].split("#").length >= 2) {
					remarks.append("Proforma Invoice: ").append(outParameter[0].split("#")[1])
							.append(" generated Successfully");
				}

			} else {
				remarks.append(outParameter[1]);
			}

		}

		if (remarks != null) {

			log.log(Log.FINE, "remarks last", remarks.toString());
			finalRemarks.append(remarks.toString());

		}
		invoiceTransactionLogVO.setCompanyCode(generateInvoiceFilterVO.getCompanyCode());
		invoiceTransactionLogVO.setInvoiceType("GB");
		invoiceTransactionLogVO.setTransactionCode(generateInvoiceFilterVO.getTransactionCode());
		invoiceTransactionLogVO.setSerialNumber(generateInvoiceFilterVO.getInvoiceLogSerialNumber());

		invoiceTransactionLogVO.setInvoiceGenerationStatus(txnSta);

		if (finalRemarks != null && finalRemarks.length() > 0) {
			invoiceTransactionLogVO.setRemarks(finalRemarks.toString());
		} else {
			invoiceTransactionLogVO.setRemarks(BLANK);
		}

		try {
			new CRADefaultsProxy().updateTransactionandRemarks(invoiceTransactionLogVO);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage());
		}

		// Added by A-3434 for CR ICRD-114599 on 29SEP2015 ends

	}

	/**
	 * Method : GPABillingAAController.sendEmail Added by : A-5526 on
	 * 12-Feb-2019 Used for : ICRD-235779 sendEmail to address configured
	 * Parameters : @param
	 * cN51CN66VO,cn51DetailsVos,invoiceDetailsReportVO,cn51CN66FilterVO,email
	 * Parameters : @throws SystemException Return type : void
	 */

	public void sendEmail(Collection<CN66DetailsVO> cn66DetailsVos, Collection<CN51DetailsVO> cn51DetailsVos,
			InvoiceDetailsReportVO invoiceDetailsReportVO, CN51CN66FilterVO cn51CN66FilterVO, String email)
			throws SystemException {
		log.entering("GPABillingControlle", "sendEmail");

		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs = new ArrayList<InvoiceDetailsReportVO>();
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		InvoiceDetailsReportVO invoiceDetailsReportVO1 = new InvoiceDetailsReportVO();
		invoiceDetailsReportVO1 = CN51Summary.generateInvoiceReportTK(cn51CN66FilterVO);
		InvoiceDetailsReportVO invoiceReportVO = CN51Summary.generateInvoiceReport(cn51CN66FilterVO);
		if (invoiceReportVO != null) {
			invoiceDetailsReportVO1.setAddress(invoiceReportVO.getAddress());
			invoiceDetailsReportVO1.setBilledDateString(invoiceReportVO.getBilledDateString());
			invoiceDetailsReportVO1.setTotalAmountinBillingCurrency(invoiceReportVO.getTotalAmountinBillingCurrency());
			invoiceDetailsReportVO1.setCurrency(invoiceReportVO.getBillingCurrencyCode());
			invoiceDetailsReportVO1.setPaName(invoiceReportVO.getPaName());
			invoiceDetailsReportVO1.setPhone1(invoiceReportVO.getPhone1());
			invoiceDetailsReportVO1.setPhone2(invoiceReportVO.getPhone2());
			invoiceDetailsReportVO1.setAddress(invoiceReportVO.getAddress());
			invoiceDetailsReportVO1.setCity(invoiceReportVO.getCity());
			invoiceDetailsReportVO1.setState(invoiceReportVO.getState());
			invoiceDetailsReportVO1.setCountry(invoiceReportVO.getCountry());
			invoiceDetailsReportVO1.setFax(invoiceReportVO.getFax());
			invoiceDetailsReportVO1.setInvoiceNumber(invoiceReportVO.getInvoiceNumber());
			invoiceDetailsReportVO1.setFreeText(invoiceReportVO.getFreeText());
		}
		invoiceDetailsReportVOs.add(invoiceDetailsReportVO1);

		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
		try {
			oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(cn51CN66FilterVO.getCompanyCode(),
					oneTimeActiveStatusList);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		Collection<OneTimeVO> mailCategory = new ArrayList<OneTimeVO>();
		if (oneTimeHashMap != null) {
			mailCategory = oneTimeHashMap.get("mailtracking.defaults.mailcategory");
		}

		Collection<CN51DetailsVO> cn51DetailsVoS = new ArrayList<CN51DetailsVO>();
		if (cn51DetailsVos != null && cn51DetailsVos.size() > 0) {
			for (CN51DetailsVO cn51VO : cn51DetailsVos) {
				for (OneTimeVO oneTimeVO : mailCategory) {
					if (cn51VO.getMailCategoryCode().equals(oneTimeVO.getFieldValue())) {
						cn51VO.setMailCategoryCode(oneTimeVO.getFieldDescription());
					}
				}
				cn51VO.setMonthFlag("C");
				cn51VO.setSector(cn51VO.getOrigin().concat("-").concat(cn51VO.getDestination()));
				cn51VO.setAirlineCode(logonAttributes.getOwnAirlineCode());
				cn51DetailsVoS.add(cn51VO);
			}
		}
		// Added for CRQ ICRD-235779 by A-5526 starts
		String fromDate = "";
		String toDate = "";
		if (invoiceDetailsReportVO.getFromBillingPeriod() != null) {
			fromDate = TimeConvertor.toStringFormat(invoiceDetailsReportVO.getFromBillingPeriod().toCalendar(),
					"dd MMM yyyy");
		}
		if (invoiceDetailsReportVO.getToBillingPeriod() != null) {
			toDate = TimeConvertor.toStringFormat(invoiceDetailsReportVO.getToBillingPeriod().toCalendar(),
					"dd MMM yyyy");
		}
		if(fromDate!=null){    
			fromDate=fromDate.substring(0, 2);
		}
		invoiceDetailsReportVO.setFromDateString(fromDate);
		invoiceDetailsReportVO.setToDateString(toDate);
		// Added for CRQ ICRD-235779 by A-5526 ends
		String attachmentName = "Invoice report";
		StringBuilder messageBody = new StringBuilder();
		String messageType = "MTKINVRPT";
		EmailVO emailVO = populateEmailVO(logonAttributes.getCompanyCode(), attachmentName, ReportConstants.FORMAT_PDF,
				messageBody.toString(), messageType);
		log.log(Log.FINE, "Email VOs formed", emailVO);
		emailVO.setToEmailIDs(email);

		GPABillingAAController gpaBillingAAController = (GPABillingAAController) SpringAdapter.getInstance()
				.getBean("mRAGpaBillingAAcontroller");
		gpaBillingAAController.sendInvoiceEmailForAA(cn51CN66FilterVO, invoiceDetailsReportVO, invoiceDetailsReportVOs,
				cn51DetailsVoS, cn66DetailsVos);

		log.exiting("GPABillingControlle", "sendEmail");
	}

	/**
	 * Method : GPABillingAAController.sendInvoiceEmailForAA Added by : A-5526
	 * on 12-Feb-2019 Used for : ICRD-235779 sendInvoiceEmailForAA Parameters
	 * : @param
	 * cn51CN66FilterVO,invoiceDetailsReportVO,invoiceDetailsReportVOs,cn51DetailsVoS,cn66DetailsVos,emailVO
	 * Parameters : @throws SystemException Return type : void
	 */
	@Raise(module = "mail", submodule = "mra", event = "MRA_INVOICEGENERATE_EVENT", methodId = "mail.mra.sendInvoiceEmailForAA")
	public void sendInvoiceEmailForAA(CN51CN66FilterVO cn51CN66FilterVO, InvoiceDetailsReportVO invoiceDetailsReportVO,
			Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs, Collection<CN51DetailsVO> cn51DetailsVoS,
			Collection<CN66DetailsVO> cn66DetailsVos) throws SystemException {
		log.entering("GPABillingAAControlle", "sendInvoiceEmailForAA");

	}

	/**
	 * Method : GPABillingAAController.releaseLocks Added by : A-5526 on
	 * 12-Feb-2019 Used for : ICRD-235779 Parameters : @param lockVOs Parameters
	 * : @throws ObjectNotLockedException Parameters : @throws SystemException
	 * Return type : void
	 */
	private void releaseLocks(Collection<LockVO> lockVOs) throws ObjectNotLockedException, SystemException {
		try {
			new FrameworkLockProxy().releaseLocks(lockVOs);
		} catch (ProxyException ex) {
			throw new SystemException(ex.getMessage(), ex);
		} catch (SystemException ex) {
			log.log(Log.SEVERE, "System Exception");
			boolean isFound = false;
			if (ex.getErrors() != null && ex.getErrors().size() > 0) {
				for (ErrorVO errvo : ex.getErrors()) {
					if (ObjectNotLockedException.OBJECT_NOT_LOCKED.equals(errvo.getErrorCode())) {
						isFound = true;
						break;
					}
				}
			}
			if (isFound) {
				throw new ObjectNotLockedException(ex);
			}
			throw new SystemException(ex.getErrors());
		}
	}

	/**
	 * Method : GPABillingAAController.populateEmailVO Added by : A-5526 on
	 * 12-Feb-2019 Used for : ICRD-235779 to populateEmailVO Parameters
	 * : @return Return type : EmailVO Parameters : @throws SystemException
	 */
	private EmailVO populateEmailVO(String companyCode, String attachmentName, String attachmentType,
			String printerName, String messageType) throws SystemException {
		EmailVO emailVO = new EmailVO();
		try {
			emailVO.setAttachementName(attachmentName);
			emailVO.setAttachementType(attachmentType);
			emailVO.setEmailBody("");
			emailVO.setSubject(printerName);
			ArrayList<MessageAddressDetailVO> addressDetails = null;
			ArrayList<String> systemParameters = new ArrayList<String>();
			MessageAddressDetailVO addressDetailVO = new MessageAddressDetailVO();
			MessageAddressVO addressVO = new MessageAddressVO();
			ArrayList<MessageAddressVO> addresses = null;
			systemParameters.add(MSGBROKER_EMAIL_INTERFACE_PARAMETER);
			String emailInterface = null;
			HashMap<String, String> systemParameterMap = null;
			try {
				systemParameterMap = (HashMap<String, String>) new SharedDefaultsProxy()
						.findSystemParameterByCodes(systemParameters);
			} catch (ProxyException proxyException) {
				throw new SystemException(proxyException.getMessage());
			}
			if (systemParameterMap != null) {
				emailInterface = systemParameterMap.get(MSGBROKER_EMAIL_INTERFACE_PARAMETER);
			}
			if (emailInterface == null) {
				emailInterface = MSGBROKER_EMAIL_INTERFACE;
			}
			MessageAddressFilterVO filterVO = new MessageAddressFilterVO();
			filterVO.setCompanyCode(companyCode);
			filterVO.setMessageType(messageType);
			// Added as part of BUG ICRD-108425 by A-5526 starts
			filterVO.setProfileStatus(MessageAddressVO.STATUS_ACTIVE);
			// Added as part of BUG ICRD-108425 by A-5526 ends
			addresses = (ArrayList<MessageAddressVO>) new MessageBrokerConfigProxy()
					.findMessageAddressDetails(filterVO);
			log.log(Log.FINE, "Address obtained", addresses);
			if (addresses != null) {
				for (int j = 0; j < addresses.size(); j++) {
					addressVO = addresses.get(j);
					addressDetails = (ArrayList<MessageAddressDetailVO>) addressVO.getMessageAddressDetails();
					for (int i = 0; i < addressDetails.size(); i++) {
						addressDetailVO = addressDetails.get(i);
						MessageBrokerConfigProxy messageBrokerConfigProxy = new MessageBrokerConfigProxy();
						Collection<MessageModeParameterVO> addDtls = new ArrayList<MessageModeParameterVO>();
						addDtls = messageBrokerConfigProxy.getSplitedAddress(companyCode, CommunicationVO.MODE_EMAIL,
								addressDetailVO.getModeAddress());
						for (MessageModeParameterVO messageModeParameterVO : addDtls) {
							if (MessageModeParameterVO.EMAIL_FROM_ADDRESS
									.equals(messageModeParameterVO.getParameterCode())) {
								emailVO.setFromAddress(messageModeParameterVO.getParameterValue());
							}
							if (MessageModeParameterVO.EMAIL_CC_ADDRESS
									.equals(messageModeParameterVO.getParameterCode())) {
								emailVO.setCcEmailIDs(messageModeParameterVO.getParameterValue());
							}
							if (MessageModeParameterVO.EMAIL_BCC_ADDRESS
									.equals(messageModeParameterVO.getParameterCode())) {
								emailVO.setBccEmailIDs(messageModeParameterVO.getParameterValue());
							}
							if (MessageModeParameterVO.EMAILADDRESS.equals(messageModeParameterVO.getParameterCode())) {
								emailVO.setToEmailIDs(messageModeParameterVO.getParameterValue());
							}
							if (emailVO.getSubject() == null && MessageModeParameterVO.EMAIL_SUBJECT
									.equals(messageModeParameterVO.getParameterCode())) {
								emailVO.setSubject(messageModeParameterVO.getParameterValue());
							}
						}
					}
				}
				log.log(Log.FINE, "Email VO constructed", emailVO);
			}
		} catch (ProxyException pe) {
			log.log(Log.SEVERE, "ProxyException", pe);
		}
		return emailVO;
	}
}