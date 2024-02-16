/*
 * FlownController.java Created on Dec 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.flown;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.admin.user.vo.ValidUsersVO;
import com.ibsplc.icargo.business.cra.defaults.vo.CRAFlightFinaliseVO;
import com.ibsplc.icargo.business.cra.miscbilling.blockspace.flight.utilization.vo.BlockSpaceFlightSegmentVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.MailTrackingMRABusinessException;
import com.ibsplc.icargo.business.mail.mra.defaults.MRADefaultsController;
import com.ibsplc.icargo.business.mail.mra.flown.proxy.AdminUserProxy;
import com.ibsplc.icargo.business.mail.mra.flown.proxy.FlightOperationProxy;
import com.ibsplc.icargo.business.mail.mra.flown.proxy.MsgBrokerMessageProxy;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailExceptionVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailRevenueVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO;
import com.ibsplc.icargo.business.mail.mra.proxy.CRAMiscbillingProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.agent.ReportAgent;
import com.ibsplc.icargo.framework.report.vo.ReportMetaData;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.mra.flown.MRAFlownDAO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.Advice;
import com.ibsplc.xibase.server.framework.interceptor.Phase;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;

import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 * @generated "UML to Java
 *            (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
@Module("mail")
@SubModule("mra")
public class FlownController {

	private static final String FLIGHT_STATUS = "flight.operation.flightstatus";

	private static final String KEY_FLOWNSEGMENTSTATUS = "mailtracking.mra.flownSegmentStatus";

	private static final String CLASS_NAME = "FlownController";
	private Log log = LogFactory.getLogger("MAIL MRA");

	/**
	 * This method is for findFlownMails
	 *
	 * @param flownMailFilterVO
	 * @return
	 * @throws SystemException
	 */
	public FlownMailSegmentVO findFlownMails(FlownMailFilterVO flownMailFilterVO) {
		return FlownSegment.findFlownMails(flownMailFilterVO);
	}

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findFlownMailExceptionsforprint(
			ReportSpec reportSpec) throws SystemException,
			MailTrackingMRABusinessException {
		FlownMailFilterVO flownMailFilterVO = (FlownMailFilterVO) reportSpec
				.getFilterValues().get(0);
		Collection<FlownMailExceptionVO> flownMailExceptionVOs = FlownSegment
				.findFlownMailExceptionsforprint(flownMailFilterVO);
		if (flownMailExceptionVOs == null || flownMailExceptionVOs.size() == 0) {
			MailTrackingMRABusinessException mailTrackingMraBusinessException = new MailTrackingMRABusinessException(
					MailTrackingMRABusinessException.MAILTACKING_MRA_EXCEPTION_NOREPORTDATA);
			throw mailTrackingMraBusinessException;

		}
		log.log(Log.FINE, "VOS to the report", flownMailExceptionVOs);
		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "FLTDAT", "ARLCOD",
				"FLTNUM", "SEG", "EXP", "DSN", "ASGCOD" });
		reportMetaData.setFieldNames(new String[] { "flightDate",
				"airlineCode", "flightNumber", "flightSegment",
				"exceptionCode", "dsnNumber", "assigneeCode" });
		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(flownMailExceptionVOs);
		return ReportAgent.generateReport(reportSpec);

	}

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findFlownMailExceptionsforprintDetails(
			ReportSpec reportSpec) throws SystemException,
			MailTrackingMRABusinessException {
		FlownMailFilterVO flownMailFilterVO = (FlownMailFilterVO) reportSpec
				.getFilterValues().get(0);
		Collection<FlownMailExceptionVO> flownMailExceptionVOs = FlownSegment
				.findFlownMailExceptionsforprintDetails(flownMailFilterVO);
		if (flownMailExceptionVOs == null || flownMailExceptionVOs.size() == 0) {
			MailTrackingMRABusinessException mailTrackingMraBusinessException = new MailTrackingMRABusinessException(
					MailTrackingMRABusinessException.MAILTACKING_MRA_EXCEPTION_NOREPORTDATA);
			throw mailTrackingMraBusinessException;

		}
		log.log(Log.FINE, "VOS to the report", flownMailExceptionVOs);
		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "FLTDAT", "ARLCOD",
				"FLTNUM", "SEG", "EXP", "COUNT", "ASGCOD" });
		reportMetaData.setFieldNames(new String[] { "flightDate",
				"airlineCode", "flightNumber", "flightSegment",
				"exceptionCode", "totalNoOfExceptions", "assigneeCode" });
		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(flownMailExceptionVOs);
		return ReportAgent.generateReport(reportSpec);

	}

	/**
	 * @param flownMailFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<FlownMailExceptionVO> findFlownMailExceptions(
			FlownMailFilterVO flownMailFilterVO) throws SystemException {
		return FlownSegment.findFlownMailExceptions(flownMailFilterVO);
	}

	/**
	 * @param flownMailExceptionVOs
	 * @throws SystemException
	 */
	public void assignFlownMailExceptions(
			Collection<FlownMailExceptionVO> flownMailExceptionVOs)
			throws SystemException {
		new FlownSegment().assignFlownMailExceptions(flownMailExceptionVOs);

	}

	/**
	 * This method is for closeFlight
	 *
	 * @param flownMailFilterVO
	 * @return
	 * @throws SystemException
	 */
	/* Commented the method as part of ICRD-153078
	public void closeFlight(FlownMailFilterVO flownMailFilterVO)
			throws SystemException {
		try {
			FlownSegment flownSegment = FlownSegment.find(flownMailFilterVO
					.getCompanyCode(), flownMailFilterVO.getFlightCarrierId(),
					flownMailFilterVO.getFlightNumber(), flownMailFilterVO
							.getFlightSequenceNumber(), flownMailFilterVO
							.getSegmentSerialNumber());
			flownSegment.setSegmentStatus("CL");
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.getErrorCode();
		}

	}*/

	/**
	 * @param flownMailFilterVO
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public void processFlight(FlownMailFilterVO flownMailFilterVO)
			throws SystemException, MailTrackingMRABusinessException {

		FlownSegment.processFlight(flownMailFilterVO);

	}

	/**
	 * This method is for findFlownMails
	 *
	 * @param flownMailFilterVO
	 * @return
	 */
	public Collection<FlownMailSegmentVO> findFlightDetails(
			FlownMailFilterVO flownMailFilterVO) {
		return FlownSegment.findFlightDetails(flownMailFilterVO);
	}

	/**
	 * This method is for validateFlight
	 *
	 * @param flightFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<FlightValidationVO> validateFlight(
			FlightFilterVO flightFilterVO) throws SystemException {
		Collection<FlightValidationVO> flightValidationVOs = null;
		// try {
		FlightValidationFilterVO flightValidationFilterVO = new FlightValidationFilterVO();
		flightValidationFilterVO
				.setCompanyCode(flightFilterVO.getCompanyCode());
		flightValidationFilterVO.setFlightNumber(flightFilterVO
				.getFlightNumber());
		flightValidationFilterVO.setFlightDate(flightFilterVO
				.getLoadPlanFlightDate());
		flightValidationFilterVO.setFlightCarrierId(flightFilterVO
				.getFlightCarrierId());
		// } catch(ProxyException e) {
		// throw new SystemException(e.getMessage(), e);
		// }

		return new FlightOperationProxy().validateFlight(flightValidationFilterVO);
	}

	/**
	 * @param userCodes
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public Collection<ValidUsersVO> validateUsers(Collection<String> userCodes,
			String companyCode) throws SystemException {
		log.entering("Flown Controller", "validateUsers");
		try {
			return new AdminUserProxy().validateUsersWithoutRoleGroup(
					userCodes, companyCode);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(), e);
		}
	}

	/**
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findListOfFlightsForReport(ReportSpec reportSpec)
			throws SystemException, MailTrackingMRABusinessException {

		log.entering("FlownController", "findListOfFlightsForReport");
		ReportMetaData reportMetaData = new ReportMetaData();
		FlownMailFilterVO flownMailFilterVO = (FlownMailFilterVO) reportSpec
				.getFilterValues().get(0);
		Map<String, Collection<OneTimeVO>> hashMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(FLIGHT_STATUS);
		oneTimeList.add(KEY_FLOWNSEGMENTSTATUS);
		Collection<FlownMailSegmentVO> flownMailSegmentVOs = FlownSegment
				.findListOfFlightsForReport(flownMailFilterVO);
		// log.log(1,"flownMailSegmentVOs from
		// controller------->"+flownMailSegmentVOs);
		if (flownMailSegmentVOs == null || flownMailSegmentVOs.size() == 0) {
			MailTrackingMRABusinessException businessException = new MailTrackingMRABusinessException();
			businessException.addError(new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA));
			throw businessException;
		}
		try {
			hashMap = new SharedDefaultsProxy().findOneTimeValues(
					flownMailFilterVO.getCompanyCode(), oneTimeList);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		log.log(Log.INFO, "onetimes from controller------->", hashMap);
		reportSpec.addExtraInfo(hashMap);

		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "flightCarrierCode",
				"flightNumber", "fromDate", "toDate" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(flownMailFilterVO);

		reportMetaData.setColumnNames(new String[] { "FLTNUM", "FLTDAT",
				"FLTSTA", "FLTSEQNUM", "FLTCARIDR", "FLTORG", "FLTDST",
				"SEGSERNUM", "SEGORG", "SEGDST", "SEGSTA", "TWOAPHCOD" });

		reportMetaData.setFieldNames(new String[] { "flightNumber",
				"stringFlightDate", "flightStatus", "flightSequenceNumber",
				"flightCarrierId", "flightOrigin", "flightDestination",
				"segmentSerialNumber", "segmentOrigin", "segmentDestination",
				"segmentStatus", "flightCarrierCode" });

		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(flownMailSegmentVOs);

		log.exiting("FlownController", "findListOfFlightsForReport");
		return ReportAgent.generateReport(reportSpec);

	}

	/**
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public Map<String, Object> findListOfFlownMailsForReport(
			ReportSpec reportSpec) throws SystemException,
			MailTrackingMRABusinessException {

		log.entering("FlownController", "findListOfFlownMailsForReport");
		ReportMetaData reportMetaData = new ReportMetaData();
		FlownMailFilterVO flownMailFilterVO = (FlownMailFilterVO) reportSpec
				.getFilterValues().get(0);

		Collection<FlownMailSegmentVO> flownMailSegmentVOs = FlownSegment
				.findListOfFlownMailsForReport(flownMailFilterVO);
		log.log(Log.INFO, "flownMailSegmentVOs from controller------->",
				flownMailSegmentVOs);
		if (flownMailSegmentVOs == null || flownMailSegmentVOs.size() == 0) {
			MailTrackingMRABusinessException businessException = new MailTrackingMRABusinessException();
			ErrorVO error = new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA);
			businessException.addError(error);
			throw businessException;
		}

		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "flightCarrierCode",
				"flightNumber", "fromDate", "toDate", "flightOrigin",
				"flightDestination", "mailOrigin", "mailDestination" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(flownMailFilterVO);

		reportMetaData.setColumnNames(new String[] { "FLTNUM", "FLTDAT",
				"FLTSEQNUM", "FLTORG", "FLTDST", "FLTCARIDR", "SEGSERNUM",
				"SEGORG", "SEGDST", "DSNORG", "DSNDST", "DSN", "DSNBAG",
				"DSNWGT", "TWOAPHCOD" ,"WGTUNT"});

		reportMetaData.setFieldNames(new String[] { "flightNumber",
				"stringFlightDate", "flightSequenceNumber", "flightOrigin",
				"flightDestination", "flightCarrierId", "segmentSerialNumber",
				"segmentOrigin", "segmentDestination", "mailOrigin",
				"mailDestination", "despatch", "bagCount", "bagWeight",
				"flightCarrierCode","bagWeightUnit"});

		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(flownMailSegmentVOs);

		log.exiting("FlownController", "findListOfFlownMailsForReport");
		return ReportAgent.generateReport(reportSpec);

	}

	public void printMailRevenueReport(FlownMailFilterVO flownMailFilterVO)
			throws SystemException,FlownException {
		log.entering("FlownController", "printMailRevenueReport");
		log.log(Log.INFO, "FlownMailFilterVOr------->", flownMailFilterVO);
		Map<String, String> baseCurrencyMap = null;
		Collection<String> baseCurrencyCodes = new ArrayList<String>();
		baseCurrencyCodes.add("shared.airline.basecurrency");
		try {
			baseCurrencyMap = new SharedDefaultsProxy()
					.findSystemParameterByCodes(baseCurrencyCodes);
		} catch (ProxyException proxyException) {
			log.log(Log.FINE,  "ProxyException");
		}
		if (baseCurrencyMap != null && baseCurrencyMap.size() > 0) {
			if (baseCurrencyMap.get("shared.airline.basecurrency") != null
					&& baseCurrencyMap.get("shared.airline.basecurrency")
							.trim().length() > 0) {
				flownMailFilterVO.setBaseCurrencyCode(baseCurrencyMap
						.get("shared.airline.basecurrency"));
				Collection<FlownMailRevenueVO> flownMailRevenueVOs = constructDAO().generateMailRevenueReportDetails(flownMailFilterVO);

				if(flownMailRevenueVOs!=null && flownMailRevenueVOs.size()>0){

						String constructedString = generateMailRevenueDetailsCSV(flownMailRevenueVOs);
						String fileName = new StringBuilder("MailRevenue_Details_")
								.append(flownMailFilterVO.getAccountingMonth()).append(
										flownMailFilterVO.getAccountingYear())
								.toString();
						sendRevenueReport(constructedString, fileName);
				}else{

 					FlownException flownException = new FlownException();
 					ErrorVO reporterror = new ErrorVO(FlownException.NO_DATA_FOUND_FOR_MAIL_REVENUE_DETAILS);
 					flownException.addError(reporterror);
 					throw flownException;


				}
			}
		}
	}

	private String generateMailRevenueDetailsCSV(
			Collection<FlownMailRevenueVO> flownMailRevenueVOs) {

		StringBuilder mailRevenueString = new StringBuilder();
		mailRevenueString.append("Orig Country");
		mailRevenueString.append(",");
		mailRevenueString.append("AWB Orig");
		mailRevenueString.append(",");
		mailRevenueString.append("Dest Country");
		mailRevenueString.append(",");
		mailRevenueString.append("AWB Dest");
		mailRevenueString.append(",");
		mailRevenueString.append("Despatch No.");
		mailRevenueString.append(",");
		mailRevenueString.append("Date");
		mailRevenueString.append(",");
		mailRevenueString.append("Flight No.");
		mailRevenueString.append(",");
		mailRevenueString.append("Orig");
		mailRevenueString.append(",");
		mailRevenueString.append("Dest");
		mailRevenueString.append(",");
		mailRevenueString.append("Type");
		mailRevenueString.append(",");
		mailRevenueString.append("Acft");
		mailRevenueString.append(",");
		mailRevenueString.append("RDI");
		mailRevenueString.append(",");
		mailRevenueString.append("Weight");
		mailRevenueString.append(",");
		mailRevenueString.append("Station");
		mailRevenueString.append(",");
		mailRevenueString.append("Amount(ZAR)");
		mailRevenueString.append(",");
		mailRevenueString.append("Account");
		mailRevenueString.append(",");
		mailRevenueString.append("Cargo");

		mailRevenueString.append("\r\n");
		for (FlownMailRevenueVO flownMailRevenueVO : flownMailRevenueVOs) {

			if (flownMailRevenueVO.getOriginCountry() != null) {
				mailRevenueString.append(flownMailRevenueVO.getOriginCountry());
				mailRevenueString.append(",");
			}
			if (flownMailRevenueVO.getOriginCity() != null) {
				mailRevenueString.append(flownMailRevenueVO.getOriginCity());
				mailRevenueString.append(",");
			}
			if (flownMailRevenueVO.getDestinationCountry() != null) {
				mailRevenueString.append(flownMailRevenueVO
						.getDestinationCountry());
				mailRevenueString.append(",");
			}
			if (flownMailRevenueVO.getDestinationCity() != null) {
				mailRevenueString.append(flownMailRevenueVO
						.getDestinationCity());
				mailRevenueString.append(",");
			}
			if (flownMailRevenueVO.getDsnNumber() != null) {
				mailRevenueString.append(flownMailRevenueVO.getDsnNumber());
				mailRevenueString.append(",");
			}
			if (flownMailRevenueVO.getFlightDate() != null) {
				mailRevenueString.append(flownMailRevenueVO.getFlightDate().toDisplayFormat("dd-MMM-yyyy"));
				mailRevenueString.append(",");
			}
			if (flownMailRevenueVO.getFlightNumber() != null) {
				mailRevenueString.append(flownMailRevenueVO.getFlightNumber());
				mailRevenueString.append(",");
			}
			if (flownMailRevenueVO.getSectorFrom() != null) {
				mailRevenueString.append(flownMailRevenueVO.getSectorFrom());
				mailRevenueString.append(",");
			}
			if (flownMailRevenueVO.getSectorTo() != null) {
				mailRevenueString.append(flownMailRevenueVO.getSectorTo());
				mailRevenueString.append(",");
			}
			if (flownMailRevenueVO.getSubClass() != null) {
				mailRevenueString.append(flownMailRevenueVO.getSubClass());
				mailRevenueString.append(",");
			}
			if (flownMailRevenueVO.getAirCraftType() != null) {
				mailRevenueString.append(flownMailRevenueVO.getAirCraftType());
				mailRevenueString.append(",");
			}
			if (flownMailRevenueVO.getRegionType() != null) {
				mailRevenueString.append(flownMailRevenueVO.getRegionType());
				mailRevenueString.append(",");
			}
			if (flownMailRevenueVO.getWeight() != 0) {
				mailRevenueString.append(flownMailRevenueVO.getWeight());
				mailRevenueString.append(",");
			}
			if (flownMailRevenueVO.getStation() != null) {
				mailRevenueString.append(flownMailRevenueVO.getStation());
				mailRevenueString.append(",");
			}
			if (flownMailRevenueVO.getAmountInZAR() != null) {
				mailRevenueString.append(String.valueOf(flownMailRevenueVO.getAmountInZAR().getRoundedAmount()));
				mailRevenueString.append(",");
			}
			if (flownMailRevenueVO.getAccountCode() != null) {
				mailRevenueString.append(flownMailRevenueVO.getAccountCode());
				mailRevenueString.append(",");
			}
			mailRevenueString.append("N");
			mailRevenueString.append("\r\n");
		}
		return mailRevenueString.toString();
	}

	private void sendRevenueReport(String fileContent, String fileName)
			throws SystemException {
		log.entering("FlownController", "sendRevenueReport");

		MsgBrokerMessageProxy msgbrokerMessageProxy = new MsgBrokerMessageProxy();
		MessageVO messageVO = new MessageVO();
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		messageVO.setCompanyCode(logonAttributes.getCompanyCode());
		messageVO.setStationCode(logonAttributes.getStationCode());
		messageVO.setOriginalMessage(fileContent);
		messageVO.setRawMessage(messageVO.getOriginalMessage());
		messageVO.setReceiptOrSentDate(new LocalDate(logonAttributes
				.getStationCode(), Location.STN, true));
		messageVO.setFileName(fileName);
		Collection<MessageDespatchDetailsVO> despatchDetails = new ArrayList<MessageDespatchDetailsVO>();
		messageVO.setDespatchDetails(despatchDetails);
		MessageDespatchDetailsVO despatchDetailsVO = new MessageDespatchDetailsVO();
		despatchDetails.add(despatchDetailsVO);
		messageVO.setMessageType(FlownMailRevenueVO.REVENUE_REPORT_MSGTYP);
		messageVO.setMessageStandard(FlownMailRevenueVO.MSG_STD);
		messageVO.setMessageVersion(FlownMailRevenueVO.MSG_VERSION);
		despatchDetailsVO.setPartyType(BaseMessageVO.PARTY_TYPE);
		despatchDetailsVO.setParty(logonAttributes.getCompanyCode());
		log.log(Log.INFO, "Sending message ==> \r\n", messageVO.getOriginalMessage());
		try {
			msgbrokerMessageProxy.sendMessageText(messageVO);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getErrors());
		}
		log.exiting("FlownController", "sendRevenueReport");

	}

	/**
	 * @return MRAFlownDAO
	 * @throws SystemException
	 */
	private static MRAFlownDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MRAFlownDAO.class.cast(em
					.getQueryDAO("mail.mra.flown"));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}

	}
/**
 * 
 * @param flightFilterVO
 * @return
 * @throws SystemException
 */
	public Collection<FlightValidationVO> validateFlightForAirport(
			FlightFilterVO flightFilterVO) throws SystemException {
		Collection<FlightValidationVO> flightValidationVOs = null;
	
		return new FlightOperationProxy().validateFlightForAirport(flightFilterVO);
	}

	/**
	 * 	Method		:	FlownController.importArrivedMailstoMRA
	 *	Added by 	:	A-4809 on Oct 12, 2015
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	@Advice(name = "mail.operations.importArrivedMailstoMRA" , phase=Phase.POST_INVOKE)//A-8061 Added For ICRD-245594
	public void importArrivedMailstoMRA(String companyCode)throws SystemException{
		log.entering(CLASS_NAME, "importArrivedMailstoMRA");
		 FlownSegment.importArrivedMailstoMRA(companyCode);
		 boolean isTaxrequired=new MRADefaultsController().isTaxRequired();
		 if(isTaxrequired){
				
				
			 new MRADefaultsController().updateTaxForMRA(companyCode);
					
				
			}
		 //A-8061 Added for Mail BSA begin
		 Collection <CRAFlightFinaliseVO> blockspaceFlights = null;
		 BlockSpaceFlightSegmentVO blockSpaceFlightSegmentVO=null;
		 
		 blockspaceFlights = new MRADefaultsController().findBlockSpaceFlights(companyCode);
		 if(blockspaceFlights!=null && !blockspaceFlights.isEmpty()){
			 for(CRAFlightFinaliseVO cRAFlightFinaliseVO: blockspaceFlights){
				 try {
					 
					new CRAMiscbillingProxy().saveBlockSpaceAgreementDetails(cRAFlightFinaliseVO);
					
					blockSpaceFlightSegmentVO=new BlockSpaceFlightSegmentVO();
					blockSpaceFlightSegmentVO.setFlightCarrierId(cRAFlightFinaliseVO.getFlightCarrierIdr());
					blockSpaceFlightSegmentVO.setFlightNumber(cRAFlightFinaliseVO.getFlightNumber());
					blockSpaceFlightSegmentVO.setFlightSequenceNumber((int)cRAFlightFinaliseVO.getFlightSeqNumber());
					blockSpaceFlightSegmentVO.setCompanyCode(companyCode);
					
					new MRADefaultsController().updateMailBSAInterfacedDetails(blockSpaceFlightSegmentVO);
					 
					
				} catch (ProxyException proxyException) {
					log.log(Log.INFO,"Exception ");
				}
			 }
		 }
		 
		 
		 //A-8061 Added for Mail BSA end
		 
		log.exiting(CLASS_NAME, "importArrivedMailstoMRA");
	}
	/**
	 * @author A-7371
	 * @param companyCode
	 * @param airlineId
	 * @param flightNumber
	 * @param flightSequenceNumber
	 * @return
	 * @throws SystemException
	 */
	public Collection<FlightSegmentSummaryVO> findFlightSegments(String companyCode, int airlineId, String flightNumber,
			long flightSequenceNumber) throws SystemException{
		
		Collection<FlightSegmentSummaryVO> segmentSummaryVos = null;
		log.log(Log.INFO, "Validate the Container For the Flight");
		return segmentSummaryVos = new FlightOperationProxy().findFlightSegments(
				companyCode, airlineId,flightNumber,flightSequenceNumber);

		
	}
	
	/**
	 * @author A-7794 as part of ICRD-232299
	 * @param companyCode
	 * @param startDate
	 * @param endDate
	 * @throws SystemException
	 */
	public void forceImportScannedMailbags(String companyCode,String startDate,String endDate) throws SystemException{
		log.entering(CLASS_NAME, "forceImportScannedMailbags");
		FlownSegment.forceImportScannedMailbags(companyCode,startDate,endDate);
		log.exiting(CLASS_NAME, "forceImportScannedMailbags");
	}
}
