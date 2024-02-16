/*
 * ProcessCommand.java Created on Feb 15, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.processmanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;


import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ProcessManagerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAProcessManagerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * 
 * @author A-2338
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * Jun 20, 2007 A-2270 Created
 */
public class ProcessCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ProcessCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.processmanager";

	private static final String ACTION_SUCCESS = "action_success";

	private static final String IMPORT_DATA_TO_MRA = "I";

	private static final String IMPORT_RECONCILE = "IR";

	private static final String RECONCILE = "R";

	private static final String CLAIM_GENERATION = "C";

	private static final String RERATE_MAILS = "RR";
	
	private static final String TRGPNT_PROCMGR = "P";
	
	private static final String REPRORATE_MAILS = "RP";//Added for icrd-132487 by a-7531
	
	private static final String DATEANDTIMEFORMAT = "dd-MMM-yyyy";

	private static final String MANDATORY_CHECK = "mailtracking.mra.defaults.msg.err.mandatoryCheck";

	private static final String INVALID_CARRIERCODE = "mailtracking.mra.defaults.msg.err.invalidcarriercode";

	private static final String VALIDATE_FAILURE = "action_failure";

	// private static final String VALIDATE_SUCCESS = "action_success";

	private static final String INVALID_FLIGHT = "mailtracking.mra.defaults.msg.err.invalidflight";

	private static final String MESSAGE_PROCESS_RUN_FAILED = "mailtracking.mra.defaults.processManager.processRunFailed";

	private static final String MESSAGE_PROCESS_RUN_SUCCESS = "mailtracking.mra.defaults.processManager.processRunSucess";

	private static final String MESSAGE_PROCESS_RUN_SUCCESS_REMAINING = "mailtracking.mra.defaults.processManager.reProrateRemaining";

	

	private static final String BLANK = "";

	private static final String CHECK_FILTER="mailtracking.mra.defaults.processManager.msg.err.checkfilter";
	private static final String CHECK_FILTER_ORGDST="mailtracking.mra.defaults.processManager.msg.err.orgdstcheckfilter";
	private static final String STATUS="Re-rating of mailbags is initiated";
	private static final String STATUS_REPRORATE="Re-prorating of mailbags is initiated";//Added for icrd-132487 by a-7531
	private static final String STATUS_IMPORTCARDITDATA="Process is initiated";
	private static final String SCREENID="MRA040";
	private static final String CHECK_FILTER_EXCEPTION="mailtracking.mra.defaults.processManager.msg.err.checkfilterexception";
	private static final String CHECK_FILTER_DATE="mailtracking.mra.defaults.processManager.msg.err.checkfilterdate";
	private static final String MAILBAG_EXIST_ERROR="mailtracking.mra.defaults.processManager.msg.err.mailbagexistinmra";
	private static final String SISMAIL_FINALIZE = "S";
	
	private static final String FROMDATE_MUST_NOT_BE_GREATER="mailtracking.mra.processManager.msg.err.fromdatenotgreater";
	private static final String FROMDATE_MUST_NOT_BE_NULL="mailtracking.mra.processManager.msg.err.fromdatenotnull";
	private static final String TODATE_MUST_NOT_BE_NULL="mailtracking.mra.processManager.msg.err.todatenotnull";

	private static final String MAXRECORD_FOR_BULK_REPRORATE="mailtracking.mra.maxrecordsforbulkrerateandproratefromMRA040";
	private static final String MAXRECORD_EXCEED = "mailtracking.mra.processManager.msg.err.bulkproratecountexceed";
	

	private static final String IS_FILE_GENERATED = "cra.sisbilling.isfilenotgeneratedbeforefinalize.errororwarning";
	private static final String MAIL_OB_FINALIZE_INITIATED = "Mail outward Invoice/s finalization initiated for clearance perios ";
	private static final String SIS_FIL_NOT_GEN_WARN = "mailtracking.mra.defaults.processManager.msg.err.outwardinvoicenotparticipatedwarn";
	private static final String SIS_FIL_NOT_GEN_ERR = "mailtracking.mra.defaults.processManager.msg.err.outwardinvoicenotparticipatederr";
	private static final String NO_REC_FOUND = "No Records found for Clearance period ";
	private static final String NO_DATA = "mailtracking.mra.defaults.processManager.msg.err.nodata"; 

	
	/**
	 * TODO Purpose Jun 20, 2007, A-2270
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");

		MRAProcessManagerForm processManagerForm = (MRAProcessManagerForm) invocationContext.screenModel;

		// String flightNumber = processManagerForm.getFlightNumber();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		// String userId = logonAttributes.getUserId();
		AirlineValidationVO airlineValidationVO = null;

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		FlightValidationVO flightValidationVO = null;
		// flightValidationVO.setCompanyCode(companyCode);
		Collection<FlownMailSegmentVO> flownMailSegmentVOs = new ArrayList<FlownMailSegmentVO>();

		
		int mailBagCount=0;
		int maxRecordForReprorate=5000;
		
		HashMap<String, String> systemParameterValues = null;
		try {
			
			systemParameterValues=(HashMap<String, String>)new SharedDefaultsDelegate().findSystemParameterByCodes(getSystemParameterTypes());
		} catch (BusinessDelegateException e) {
    		e.getMessage();
			errors=handleDelegateException( e );
		}
		
		if(systemParameterValues!=null && !systemParameterValues.isEmpty()){
			maxRecordForReprorate= Integer.parseInt(systemParameterValues.get(MAXRECORD_FOR_BULK_REPRORATE));
		}

		// LocalDate date = new LocalDate(NO_STATION,NONE,true);
		String dateString = DateUtilities.getCurrentDate(DATEANDTIMEFORMAT);
		log.log(Log.FINE, "Current Date: ", dateString);
		String process = processManagerForm.getProcessOneTime();
		Collection<ErrorVO> processErrors = null;
		ErrorVO displayMessageErrorVO = null;
		Boolean checkFlag = true;
		log.log(Log.INFO, "process onetime---->", process);
		if (IMPORT_DATA_TO_MRA.equals(process)) {
			if (!BLANK.equals(processManagerForm.getFlightDate())
					|| !BLANK.equals(processManagerForm.getFlightNumber())
					|| !BLANK.equals(processManagerForm.getCarrierCode())) {
				checkFlag = validateForm(processManagerForm);
				log.log(Log.INFO, "checkFlag------->", checkFlag);
				if (!checkFlag) {
					log.log(1, "------->inside errors");
					ErrorVO errorVO = new ErrorVO(MANDATORY_CHECK);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					invocationContext.addAllError(errors);
					invocationContext.target = ACTION_SUCCESS;
					return;
				}


				log.log(Log.INFO, "airlineValidationVO------->",
						processManagerForm.getCarrierCode());
				log.log(Log.INFO, "airlineValidationVO------->",
						processManagerForm.getFlightNumber());
				log.log(Log.INFO, "airlineValidationVO------->",
						processManagerForm.getFlightDate());
				try {
					airlineValidationVO = new AirlineDelegate()
							.validateAlphaCode(companyCode, processManagerForm
									.getCarrierCode());
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				if (airlineValidationVO == null) {
					ErrorVO errorVO = new ErrorVO(INVALID_CARRIERCODE);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					invocationContext.addAllError(errors);
					invocationContext.target = VALIDATE_FAILURE;
					return;
				} else {
					log.log(Log.INFO, "airlineValidationVO------->",
							airlineValidationVO);
					flightFilterVO.setCompanyCode(companyCode);
					flightFilterVO.setCarrierCode(processManagerForm
							.getCarrierCode());
					flightFilterVO.setFlightNumber(processManagerForm
							.getFlightNumber());

					LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,
							Location.NONE, false);
					flightDate.setDate(processManagerForm.getFlightDate());
					flightFilterVO.setLoadPlanFlightDate(flightDate);
					/*
					 * flightFilterVO.setFlightDate(flightDate);
					 * flightFilterVO.setStationCode(getApplicationSession().getLogonVO().getStationCode());
					 */
					flightFilterVO.setFlightCarrierId(airlineValidationVO
							.getAirlineIdentifier());
					flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
					MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
					Collection<FlightValidationVO> flightValidationVOs = new ArrayList<FlightValidationVO>();
					try {
						flightValidationVOs = delegate
								.validateFlight(flightFilterVO);
					} catch (BusinessDelegateException businessDelegateException) {
						log.log(Log.FINE,
								"inside updateAirline caught busDelegateExc");
						handleDelegateException(businessDelegateException);
					}

					log
							.log(
									Log.INFO,
									"flightValidationVOs from ValidateFlightCommand--->",
									flightValidationVOs);
					if (flightValidationVOs == null
							|| flightValidationVOs.size() <= 0) {
						log.log(Log.FINE, "flightValidationVOs is NULL");
						ErrorVO errorVO = new ErrorVO(INVALID_FLIGHT);
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(errorVO);
						invocationContext.addAllError(errors);
						invocationContext.target = VALIDATE_FAILURE;
						return;
					} else if (flightValidationVOs.size() == 1) {
						log.log(Log.FINE, "flightValidationVOs has one VO");

						flightValidationVO = ((ArrayList<FlightValidationVO>) flightValidationVOs)
								.get(0);

						// flightValidationVO.get

						log
								.log(
										Log.FINE,
										"flightValidationVO in validateflight command class ===",
										flightValidationVO);

						// invocationContext.target =VALIDATE_SUCCESS;
					}
					// for getting seg serial number
					if(flightValidationVO.getCarrierCode()==null){
						flightValidationVO.setCarrierCode(processManagerForm.getCarrierCode());
					}
					if(flightValidationVO.getFlightDate()==null){
						flightValidationVO.setFlightDate(flightDate);
					}
					FlownMailFilterVO filterVO = new FlownMailFilterVO();
					filterVO.setFlightCarrierCode(processManagerForm
							.getCarrierCode());
					filterVO.setFlightNumber(processManagerForm
							.getFlightNumber());
					// LocalDate flightFormDate = new
					// LocalDate(LocalDate.NO_STATION,Location.NONE,false);
					// flightDate.setDate(processManagerForm.getFlightDate());
					filterVO.setFlightDate(flightDate);

					filterVO.setFlightCarrierId(flightValidationVO
							.getFlightCarrierId());
					filterVO.setFlightSequenceNumber((int) flightValidationVO
							.getFlightSequenceNumber());
					filterVO
							.setCompanyCode(flightValidationVO.getCompanyCode());

					try {
						flownMailSegmentVOs = new MailTrackingMRADelegate()
								.findFlightDetails(filterVO);
					} catch (BusinessDelegateException businessDelegateException) {
						handleDelegateException(businessDelegateException);

					}
					log.log(Log.FINE, "*****flownMailSegmentVOs",
							flownMailSegmentVOs);
				}

			} else if (processManagerForm.getFlightDate().equals(BLANK)
					&& processManagerForm.getFlightNumber().equals(BLANK)
					&& processManagerForm.getFlightDate().equals(BLANK)
					&& (flownMailSegmentVOs == null || flownMailSegmentVOs
							.size() == 0)) {

				flightValidationVO = new FlightValidationVO();
				flightValidationVO.setCompanyCode(companyCode);
			}
		}

		// ends the validation part

		// //////
		// if(flownMailSegmentVOs==null||flownMailSegmentVOs.size()==0){
		// flightValidationVO.setCompanyCode(companyCode);
		// }
		if(flownMailSegmentVOs!=null &&flownMailSegmentVOs.size()>0){
			for(FlownMailSegmentVO flownMailSegmentVO:flownMailSegmentVOs){
				flownMailSegmentVO.setTriggerPoint("P");
			}
		}
		if (IMPORT_DATA_TO_MRA.equals(process)) {
            LocalDate fromDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
   	        LocalDate toDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
   	        if(processManagerForm.getFromDateImportmail()!=null && !processManagerForm.getFromDateImportmail().isEmpty() && processManagerForm.getToDateImportmail()!=null && !processManagerForm.getToDateImportmail().isEmpty()){
   	        fromDate.setDate(processManagerForm.getFromDateImportmail()) ;
   	        toDate.setDate(processManagerForm.getToDateImportmail());
   	        	if(fromDate.isGreaterThan(toDate)){
   	        		ErrorVO	error=new ErrorVO(FROMDATE_MUST_NOT_BE_GREATER);
	   		   		errors.add(error);
	   				invocationContext.addAllError(errors);
					invocationContext.target = ACTION_SUCCESS;
					return;
   	        	}
           
   	        }
           
			DocumentBillingDetailsVO documentBillingVO=new DocumentBillingDetailsVO();
			documentBillingVO.setScreenID(SCREENID);
			documentBillingVO.setTriggerPoint(TRGPNT_PROCMGR);
			documentBillingVO.setCompanyCode(companyCode); 
			documentBillingVO.setBillingBasis(processManagerForm.getMailbag());
			documentBillingVO.setFromDate(processManagerForm.getFromDateImportmail());
			documentBillingVO.setToDate(processManagerForm.getToDateImportmail());
			if(documentBillingVO.getFromDate()!=null &&!documentBillingVO.getFromDate().isEmpty() && documentBillingVO.getToDate()!=null && !documentBillingVO.getToDate().isEmpty()){
				documentBillingVO.setFilterMode(processManagerForm.getFilterMode());
			}
			

			InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
			invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
			invoiceTransactionLogVO.setInvoiceType(IMPORT_DATA_TO_MRA);
			invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
			invoiceTransactionLogVO.setInvoiceGenerationStatus(InvoiceTransactionLogVO.OPERATION_FLAG_INSERT);
			invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
			invoiceTransactionLogVO.setRemarks(STATUS_IMPORTCARDITDATA);
			invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
			invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
			invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
			invoiceTransactionLogVO.setPeriodFrom(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
			invoiceTransactionLogVO.setPeriodTo(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
			invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
			invoiceTransactionLogVO.getSerialNumber();
			invoiceTransactionLogVO.getTransactionCode();

			 try{

			   		invoiceTransactionLogVO =  new MailTrackingMRADelegate().initiateTransactionLogForInvoiceGeneration( invoiceTransactionLogVO );
				       }catch(BusinessDelegateException ex){
					errors = this.handleDelegateException(ex);
				    }
			String txnlogInfo = new StringBuilder(invoiceTransactionLogVO.getTransactionCode()).append("-").append(invoiceTransactionLogVO.getSerialNumber()).toString();

			try {
				new MailTrackingMRADelegate().importFlownMails(

						flightValidationVO, flownMailSegmentVOs,documentBillingVO,txnlogInfo);

			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.FINE, "in the exception");
				businessDelegateException.getMessage();
				processErrors = handleDelegateException(businessDelegateException);
			}
		} else if (IMPORT_RECONCILE.equals(process)) {
			try {
				new MailTrackingMRADelegate().importToReconcile(companyCode);
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.FINE, "*****in the exception");
				businessDelegateException.getMessage();
				processErrors = handleDelegateException(businessDelegateException);
			}
		} else if (RECONCILE.equals(process)) {
			try {
				new MailTrackingMRADelegate().reconcileProcess(companyCode);
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.FINE, "*****in the exception");
				businessDelegateException.getMessage();
				processErrors = handleDelegateException(businessDelegateException);
			}
		} else if (CLAIM_GENERATION.equals(process)) {
			try {
				new MailTrackingMRADelegate()
						.generateInvoicClaimFile(companyCode);
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.FINE, "*****in the exception");
				businessDelegateException.getMessage();
				processErrors = handleDelegateException(businessDelegateException);
			}
			//Added by A-7531 as part of ICRD-132508 starts
		} else if (RERATE_MAILS.equals(process)) {
			
			DocumentBillingDetailsVO documentBillingVO=new DocumentBillingDetailsVO();

			documentBillingVO.setScreenID(SCREENID);
			
			documentBillingVO.setOrigin(processManagerForm.getOrigin());
			documentBillingVO.setDestination(processManagerForm.getDestination());
			documentBillingVO.setCategory(processManagerForm.getMailCategory());
			documentBillingVO.setSubClass(processManagerForm.getMailSubclass());
			if(processManagerForm.getGpaCode()!=null){
			documentBillingVO.setGpaCode(processManagerForm.getGpaCode().toUpperCase());
			}
			if(processManagerForm.getFromAirlineCode()!=null){
			documentBillingVO.setAirlineCode(processManagerForm.getFromAirlineCode().toUpperCase());
			}
			if(processManagerForm.getToAirlineCode()!=null){
			documentBillingVO.setAirlineCode(processManagerForm.getToAirlineCode().toUpperCase());
			}
			if(processManagerForm.getUpliftAirportReRate()!=null){
			documentBillingVO.setUpliftAirport(processManagerForm.getUpliftAirportReRate().toUpperCase());
			}
			if(processManagerForm.getDischargeAirportReRate()!=null){
			documentBillingVO.setDischargeAirport(processManagerForm.getDischargeAirportReRate().toUpperCase());
			}
			if(processManagerForm.getTransferAirlineReRate()!=null){
			documentBillingVO.setTransferAirline(processManagerForm.getTransferAirlineReRate().toUpperCase());
			}
			if(processManagerForm.getTransferPAReRate()!=null){
			documentBillingVO.setTransferPA(processManagerForm.getTransferPAReRate().toUpperCase());
			}
			if(processManagerForm.getOriginOEReRate()!=null){
			documentBillingVO.setOriginOE(processManagerForm.getOriginOEReRate().toUpperCase());
			}
			if(processManagerForm.getDestinationOEReRate()!=null){
			documentBillingVO.setDestinationOE(processManagerForm.getDestinationOEReRate().toUpperCase());
			}
			
			
			//Modified for icrd-254820
			String fromDate=null;
			String toDate=null;
			String OOE=null;
			String DOE=null;
			
			if((!BLANK.equals(processManagerForm.getFromDate())) && (!BLANK.equals(processManagerForm.getToDate()))){
			documentBillingVO.setFromDate(processManagerForm.getFromDate());
				fromDate=documentBillingVO.getFromDate();
			documentBillingVO.setToDate(processManagerForm.getToDate());
			   toDate=documentBillingVO.getToDate();
			}
			if((!BLANK.equals(processManagerForm.getOriginOfficeOfExchange())) && (!BLANK.equals(processManagerForm.getDestinationOfficeOfExchange()))){
				documentBillingVO.setOrgOfficeOfExchange(processManagerForm.getOriginOfficeOfExchange().toUpperCase());
				OOE=documentBillingVO.getOrgOfficeOfExchange();
				documentBillingVO.setDestOfficeOfExchange(processManagerForm.getDestinationOfficeOfExchange().toUpperCase());
				DOE=documentBillingVO.getDestOfficeOfExchange();
			}
		
			
			if(((documentBillingVO.getOrigin()==null||OOE==null)||(documentBillingVO.getDestination()==null||DOE==null))&&(fromDate==null||toDate==null))
			{
				ErrorVO error=new ErrorVO(CHECK_FILTER);
	 			errors.add(error);
	 			error.setErrorDisplayType(ErrorDisplayType.ERROR);
	 			invocationContext.addAllError(errors);
				invocationContext.target = VALIDATE_FAILURE;
				return;
	 			}
			if((((documentBillingVO.getOrigin()!=null&& !("".equals(documentBillingVO.getOrigin())))&&OOE==null)||((documentBillingVO.getDestination()!=null&& !("".equals(documentBillingVO.getDestination()))))&&DOE==null))
			{
				ErrorVO error=new ErrorVO(CHECK_FILTER_ORGDST);
	 			errors.add(error);
	 			error.setErrorDisplayType(ErrorDisplayType.ERROR);
	 			invocationContext.addAllError(errors);
				invocationContext.target = VALIDATE_FAILURE;
				return;
	 			}
			
					Collection<DocumentBillingDetailsVO> seldocumentBillingDetailsVOs= null;
			try {
				if((documentBillingVO.getGpaCode()!=null&&documentBillingVO.getGpaCode().trim().length()>0)&&((documentBillingVO.getAirlineCode()==null)))
				{
					
					seldocumentBillingDetailsVOs = new MailTrackingMRADelegate()
						.findRerateBillableMails(documentBillingVO,companyCode);
				
					for(DocumentBillingDetailsVO seldocumentBillingDetailsVO:seldocumentBillingDetailsVOs){//added by A-7371 as part of ICRD-253017
						seldocumentBillingDetailsVO.setScreenID(SCREENID);
					}
				}else if((!BLANK.equals(documentBillingVO.getAirlineCode()))&&(BLANK.equals(documentBillingVO.getGpaCode())))
				{
					
					seldocumentBillingDetailsVOs =new MailTrackingMRADelegate()//Modified for icrd-254820
					.findRerateInterlineBillableMails(documentBillingVO,companyCode);
					for(DocumentBillingDetailsVO seldocumentBillingDetailsVO:seldocumentBillingDetailsVOs){
						seldocumentBillingDetailsVO.setScreenID(SCREENID);
					}
				}
				else if(BLANK.equals(documentBillingVO.getAirlineCode())&& BLANK.equals(documentBillingVO.getGpaCode())){//Added for icrd-254820
					seldocumentBillingDetailsVOs =new MailTrackingMRADelegate()
					.findRerateBillableMails(documentBillingVO,companyCode);
					Collection<DocumentBillingDetailsVO> interlineBillingDetailsVOs= null;
					
					interlineBillingDetailsVOs=new MailTrackingMRADelegate()
					.findRerateInterlineBillableMails(documentBillingVO,companyCode);
					if(interlineBillingDetailsVOs!=null){
						seldocumentBillingDetailsVOs.addAll(interlineBillingDetailsVOs);
						for(DocumentBillingDetailsVO seldocumentBillingDetailsVO:seldocumentBillingDetailsVOs){
							seldocumentBillingDetailsVO.setScreenID(SCREENID);
						}
					}
				
				}
				
				
				if(seldocumentBillingDetailsVOs.size() > maxRecordForReprorate){
					ErrorVO error=new ErrorVO(MAXRECORD_EXCEED);
		 			errors.add(error);
		 			error.setErrorDisplayType(ErrorDisplayType.ERROR);
		 			invocationContext.addAllError(errors);
					invocationContext.target = VALIDATE_FAILURE;
					return;
					
				}
				
				
				InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
				invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
				invoiceTransactionLogVO.setInvoiceType(MRAConstantsVO.TOBERERATED);
				invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
				invoiceTransactionLogVO.setInvoiceGenerationStatus(InvoiceTransactionLogVO.OPERATION_FLAG_INSERT);
				invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
				invoiceTransactionLogVO.setRemarks(STATUS);
				invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
				invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
				invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
				invoiceTransactionLogVO.setPeriodFrom(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
				invoiceTransactionLogVO.setPeriodTo(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
				invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
				invoiceTransactionLogVO.getSerialNumber();
				invoiceTransactionLogVO.getTransactionCode();
			    try{
			   		invoiceTransactionLogVO =  new MailTrackingMRADelegate().initiateTransactionLogForInvoiceGeneration( invoiceTransactionLogVO );
				       }catch(BusinessDelegateException ex){
					errors = this.handleDelegateException(ex);
				    }
			    String txnlogInfo = new StringBuilder(invoiceTransactionLogVO.getTransactionCode()).append("-").append(invoiceTransactionLogVO.getSerialNumber()).toString();
		     
				try {
					new MailTrackingMRADelegate().reRateMailbags(seldocumentBillingDetailsVOs,txnlogInfo);
				} catch (BusinessDelegateException e) {
					
					log.log(Log.SEVERE, "execption",e.getMessage());
				}
				
				
				
				
				/*InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
				invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
				invoiceTransactionLogVO.setInvoiceType(MRAConstantsVO.TOBERERATED);
				invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
				invoiceTransactionLogVO.setInvoiceGenerationStatus(InvoiceTransactionLogVO.OPERATION_FLAG_INSERT);
				invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
				invoiceTransactionLogVO.setRemarks(STATUS);
				invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
				invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
				invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
				invoiceTransactionLogVO.setPeriodFrom(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
				invoiceTransactionLogVO.setPeriodTo(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
				invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
				invoiceTransactionLogVO.getSerialNumber();
				invoiceTransactionLogVO.getTransactionCode();
			    
			    try{
			   		invoiceTransactionLogVO =  new MailTrackingMRADelegate().initiateTransactionLogForInvoiceGeneration( invoiceTransactionLogVO );
				       }catch(BusinessDelegateException ex){
					errors = this.handleDelegateException(ex);
				    }
			    String txnlogInfo = new StringBuilder(invoiceTransactionLogVO.getTransactionCode()).append("-").append(invoiceTransactionLogVO.getSerialNumber()).toString();
		     
				try {
					new MailTrackingMRADelegate().reRateMailbags(seldocumentBillingDetailsVOs,txnlogInfo);
				} catch (BusinessDelegateException e) {
					
					log.log(Log.SEVERE, "execption",e.getMessage());
				}*/
				

			} catch (BusinessDelegateException e) {
				log.log(Log.SEVERE, "execption",e.getMessage());
				processErrors = handleDelegateException(e);
			}
		}//Added by A-7531 for ICRD-132508 ends
		
		//Added by a-7531 for icrd-132487 starts
		else if(REPRORATE_MAILS.equals(process)){
			
			
       		if(processManagerForm.getFromDateforProrate()==null || processManagerForm.getFromDateforProrate().trim().length()==0){
       
       			ErrorVO  error=new ErrorVO(FROMDATE_MUST_NOT_BE_NULL);
       			errors.add(error);
       			invocationContext.addAllError(errors);
				invocationContext.target = ACTION_SUCCESS;
				return;
       			}
        
       		 if(processManagerForm.getToDateforProrate() == null || processManagerForm.getToDateforProrate().trim().length()==0){
       			ErrorVO	error=new ErrorVO(TODATE_MUST_NOT_BE_NULL);
       			errors.add(error);
       			invocationContext.addAllError(errors);
				invocationContext.target = ACTION_SUCCESS;
				return;
       		 }
       		  if ((processManagerForm.getFromDateforProrate() != null) && (!("").equals(processManagerForm.getFromDateforProrate().toString()))&&
       				processManagerForm.getToDateforProrate() != null && ((!("").equals(processManagerForm.getToDateforProrate().toString())))){
       			  
       		  	LocalDate fromDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
       	        LocalDate toDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
       	        fromDate.setDate(processManagerForm.getFromDateforProrate()) ;
       	        toDate.setDate(processManagerForm.getToDateforProrate());
               if(fromDate.isGreaterThan(toDate)){
            	   ErrorVO	error=new ErrorVO(FROMDATE_MUST_NOT_BE_GREATER);
       		   		errors.add(error);
       				invocationContext.addAllError(errors);
					invocationContext.target = ACTION_SUCCESS;
					return;
                }
               
             }

			DocumentBillingDetailsVO documentBillingVO=new DocumentBillingDetailsVO();
			
			documentBillingVO.setCompanyCode(logonAttributes.getCompanyCode());
			documentBillingVO.setScreenID(SCREENID);
			if(processManagerForm.getOriginforProrate()!=null){
			documentBillingVO.setOrgOfficeOfExchange(processManagerForm.getOriginforProrate().toUpperCase());
			}
			if(processManagerForm.getDestinationforProrate()!=null){
			documentBillingVO.setDestOfficeOfExchange(processManagerForm.getDestinationforProrate().toUpperCase());
			}
			documentBillingVO.setCategory(processManagerForm.getMailCategoryforProrate());
			documentBillingVO.setSubClass(processManagerForm.getMailSubclassforProrate());
			if(processManagerForm.getTransferAirlineException()!=null){
			documentBillingVO.setTransferAirline(processManagerForm.getTransferAirlineException().toUpperCase());
			}
			if(processManagerForm.getTransferPAException()!=null){
			documentBillingVO.setTransferPA(processManagerForm.getTransferPAException().toUpperCase());
			}
			if(processManagerForm.getUpliftAirportException()!=null){
			documentBillingVO.setUpliftAirport(processManagerForm.getUpliftAirportException().toUpperCase());
			}
			if(processManagerForm.getDischargeAirportException()!=null){
			documentBillingVO.setDischargeAirport(processManagerForm.getDischargeAirportException().toUpperCase());
			}
			if(processManagerForm.getOriginAirportException()!=null){
			documentBillingVO.setOriginAirport(processManagerForm.getOriginAirportException().toUpperCase());
			}
			if(processManagerForm.getDestinationAirportException()!=null){
			documentBillingVO.setDestinationAirport(processManagerForm.getDestinationAirportException().toUpperCase());
			}
			
			
			if(((processManagerForm.getFromDateforProrate()!=null||!BLANK.equals(processManagerForm.getFromDateforProrate()))) && (processManagerForm.getToDateforProrate()!=null ||(!BLANK.equals(processManagerForm.getToDateforProrate())))){
			documentBillingVO.setFromDate(processManagerForm.getFromDateforProrate());
			documentBillingVO.setToDate(processManagerForm.getToDateforProrate());
			}
			else{
				ErrorVO error=new ErrorVO(CHECK_FILTER_DATE);
	 			errors.add(error);
	 			error.setErrorDisplayType(ErrorDisplayType.ERROR);
	 			invocationContext.addAllError(errors);
				invocationContext.target = VALIDATE_FAILURE;
	 			}
			if(!BLANK.equals(processManagerForm.getProrateException())||processManagerForm.getProrateException()!=null)
					{
			documentBillingVO.setProrateException(processManagerForm.getProrateException());
					}
			else{
				ErrorVO error=new ErrorVO(CHECK_FILTER_EXCEPTION);
	 			errors.add(error);
	 			error.setErrorDisplayType(ErrorDisplayType.ERROR);
	 			invocationContext.addAllError(errors);
				invocationContext.target = VALIDATE_FAILURE;
	 			}
			try {
				Collection<DocumentBillingDetailsVO> selectedDocumentBillingDetailsVOs= null;
				selectedDocumentBillingDetailsVOs=new MailTrackingMRADelegate().findReproarteMails(documentBillingVO);
			
			if(selectedDocumentBillingDetailsVOs!=null&&!selectedDocumentBillingDetailsVOs.isEmpty()){
				mailBagCount=selectedDocumentBillingDetailsVOs.size();
			}
			
			
			if(selectedDocumentBillingDetailsVOs.size() > maxRecordForReprorate){
				ErrorVO error=new ErrorVO(MAXRECORD_EXCEED);
	 			errors.add(error);
	 			error.setErrorDisplayType(ErrorDisplayType.ERROR);
	 			invocationContext.addAllError(errors);
				invocationContext.target = VALIDATE_FAILURE;
				return;
				
			}

			InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
			invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
			invoiceTransactionLogVO.setInvoiceType(MRAConstantsVO.TOBEREPORATED);
			invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
			invoiceTransactionLogVO.setInvoiceGenerationStatus(InvoiceTransactionLogVO.OPERATION_FLAG_INSERT);
			invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
			invoiceTransactionLogVO.setRemarks(STATUS_REPRORATE);
			invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
			invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
			invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
			invoiceTransactionLogVO.setPeriodFrom(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
			invoiceTransactionLogVO.setPeriodTo(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
			invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
			invoiceTransactionLogVO.getSerialNumber();
			invoiceTransactionLogVO.getTransactionCode();
		    
			 try{
			   		invoiceTransactionLogVO =  new MailTrackingMRADelegate().initiateTransactionLogForInvoiceGeneration( invoiceTransactionLogVO );
				       }catch(BusinessDelegateException ex){
					errors = this.handleDelegateException(ex);
				    }
			    String txnlogInfo = new StringBuilder(invoiceTransactionLogVO.getTransactionCode()).append("-").append(invoiceTransactionLogVO.getSerialNumber()).toString();
		     
				try {
					new MailTrackingMRADelegate().reProrateExceptionMails(selectedDocumentBillingDetailsVOs,txnlogInfo);
				} catch (BusinessDelegateException e) {
					
					log.log(Log.SEVERE, "execption",e.getMessage());
				}
				}

			catch (BusinessDelegateException e) {
				log.log(Log.SEVERE, "execption",e.getMessage());
				processErrors = handleDelegateException(e);
			}
		}else if (SISMAIL_FINALIZE.equals(process)) {
			Collection<String> codes = new ArrayList<String>();
			String errorLevel="";
			codes.add(IS_FILE_GENERATED);
			Map<String, String> results = new HashMap<String, String>();
			try {
				results = new SharedDefaultsDelegate()
						.findSystemParameterByCodes(codes);
				if(results != null && !results.isEmpty()){
					errorLevel = results.get(IS_FILE_GENERATED);
				}
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			}
			Collection<AirlineCN51SummaryVO> summaryVOs = new ArrayList<AirlineCN51SummaryVO>();
			AirlineCN51FilterVO filterVo = new AirlineCN51FilterVO();
			filterVo.setCompanyCode(companyCode);
			filterVo.setIataClearancePeriod(processManagerForm.getClearancePeriod());
			filterVo.setInterlineBillingType("O");
			filterVo.setInvoiceStatus("N");
			boolean isFilNotGen = false;
			boolean isFileGen = false;
			try {
				summaryVOs = new MailTrackingMRADelegate().getAirlineSummaryDetails(filterVo);
				if(summaryVOs != null && !summaryVOs.isEmpty()){
					for(AirlineCN51SummaryVO vo : summaryVOs){
						if(vo.getFileName() == null){
							isFilNotGen = true;
						}else{
							isFileGen = true;
						}
					}
				}
				if(isFileGen) {
					if(isFilNotGen && "E".equals(errorLevel)){
						ErrorVO error=new ErrorVO(SIS_FIL_NOT_GEN_ERR);
			 			errors.add(error);
			 			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			 			invocationContext.addAllError(errors);
						invocationContext.target = VALIDATE_FAILURE;
						return;
					}else if (isFilNotGen && "W".equals(errorLevel) && !"Y".equals(processManagerForm.getByPassWarning())){
						ErrorVO error=new ErrorVO(SIS_FIL_NOT_GEN_WARN);
			 			errors.add(error);
			 			error.setErrorDisplayType(ErrorDisplayType.WARNING);
			 			invocationContext.addAllError(errors);
						invocationContext.target = VALIDATE_FAILURE;
						return;
					}
					InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
					invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
					invoiceTransactionLogVO.setInvoiceType(MRAConstantsVO.MRA_OB_FINALIZE);
					invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
					invoiceTransactionLogVO.setInvoiceGenerationStatus(InvoiceTransactionLogVO.OPERATION_FLAG_INSERT);
					invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
					invoiceTransactionLogVO.setRemarks((new StringBuilder(MAIL_OB_FINALIZE_INITIATED).append(processManagerForm.getClearancePeriod())).toString());
					invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
					invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
					invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
					invoiceTransactionLogVO.setClearancePeriod(processManagerForm.getClearancePeriod());
					invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
					 try{
					   		invoiceTransactionLogVO =  new MailTrackingMRADelegate().initiateTransactionLogForInvoiceGeneration( invoiceTransactionLogVO );
						       }catch(BusinessDelegateException ex){
							errors = this.handleDelegateException(ex);
						    }
					String txnlogInfo = new StringBuilder(invoiceTransactionLogVO.getTransactionCode()).append("-")
							.append(invoiceTransactionLogVO.getSerialNumber()).append("-")
									.append(processManagerForm.getClearancePeriod()).toString();	
				new MailTrackingMRADelegate().finalizeInvoice(summaryVOs,txnlogInfo);
				}else{
					ErrorVO error=new ErrorVO(NO_DATA);
		 			errors.add(error);
		 			error.setErrorDisplayType(ErrorDisplayType.ERROR);
		 			invocationContext.addAllError(errors);
					invocationContext.target = VALIDATE_FAILURE;
					return;
//					InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
//					invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
//					invoiceTransactionLogVO.setInvoiceType(MRAConstantsVO.MRA_OB_FINALIZE);
//					invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
//					invoiceTransactionLogVO.setInvoiceGenerationStatus(MRAConstantsVO.FAILD);
//					invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
//					invoiceTransactionLogVO.setRemarks((new StringBuilder(NO_REC_FOUND).append(processManagerForm.getClearancePeriod())).toString());
//					invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
//					invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
//					invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
//					invoiceTransactionLogVO.setClearancePeriod(processManagerForm.getClearancePeriod());
//					invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
//					 try{
//					   		  new MailTrackingMRADelegate().initiateTransactionLogForInvoiceGeneration( invoiceTransactionLogVO);
//						       }catch(BusinessDelegateException ex){
//							errors = this.handleDelegateException(ex);
//						    }
				}
			} catch (BusinessDelegateException e) {
				log.log(Log.SEVERE, "execption",e.getMessage());
			}
	}else if(MRAConstantsVO.IMPORT_DATA_TO_MRA_CARDIT.equals(process)){
		//Added by A-4809 for CR ICRD-232299 Starts......................
		String mailbagPresent = null;
		DocumentBillingDetailsVO documentBillingVO=new DocumentBillingDetailsVO();
		documentBillingVO.setScreenID(SCREENID);
		documentBillingVO.setCompanyCode(companyCode); 
		documentBillingVO.setFlightNumber(processManagerForm.getFlightNumber());
		LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		if(processManagerForm.getFlightDate()!=null && !processManagerForm.getFlightDate().isEmpty()){
		flightDate.setDate(processManagerForm.getFlightDate());
		documentBillingVO.setFlightDate(flightDate);
		}  
		documentBillingVO.setPoaCode(processManagerForm.getGpaCode());
		documentBillingVO.setBillingBasis(processManagerForm.getMailbag());
		documentBillingVO.setFromDate(processManagerForm.getMailFromDate());
		documentBillingVO.setToDate(processManagerForm.getMailToDate());
		//Added By A-7794 for ICRD-324755
		if((processManagerForm.getMailFromDate() == null || ("").equals(processManagerForm.getMailFromDate())) &&
				(processManagerForm.getMailToDate() == null || ("").equals(processManagerForm.getMailToDate()))){
			ErrorVO error=new ErrorVO(CHECK_FILTER_DATE);
 			errors.add(error);
 			error.setErrorDisplayType(ErrorDisplayType.ERROR);
 			invocationContext.addAllError(errors);
			invocationContext.target = VALIDATE_FAILURE;
			return;
		}else if(processManagerForm.getMailFromDate() == null || ("").equals(processManagerForm.getMailFromDate())){
			ErrorVO  error=new ErrorVO(FROMDATE_MUST_NOT_BE_NULL);
   			errors.add(error);
   			invocationContext.addAllError(errors);
			invocationContext.target = ACTION_SUCCESS;
			return;
		}else if(processManagerForm.getMailToDate() == null || ("").equals(processManagerForm.getMailToDate())){
			ErrorVO	error=new ErrorVO(TODATE_MUST_NOT_BE_NULL);
   			errors.add(error);
   			invocationContext.addAllError(errors);
			invocationContext.target = ACTION_SUCCESS;
			return;
		}else{
			LocalDate fromDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
   	        LocalDate toDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
   	        fromDate.setDate(processManagerForm.getMailFromDate()) ;
   	        toDate.setDate(processManagerForm.getMailToDate());
           if(fromDate.isGreaterThan(toDate)){
        	   ErrorVO	error=new ErrorVO(FROMDATE_MUST_NOT_BE_GREATER);
   		   		errors.add(error);
   				invocationContext.addAllError(errors);
				invocationContext.target = ACTION_SUCCESS;
				return;
            }
		}
		//Added By A-7794 for ICRD-324755
		if(processManagerForm.getMailbag()!=null && !processManagerForm.getMailbag().isEmpty()){
			try {
				mailbagPresent = new MailTrackingMRADelegate().findMailbagExistInMRA(documentBillingVO);
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.FINE, "*****in the exception");
				businessDelegateException.getMessage();
				processErrors = handleDelegateException(businessDelegateException);
			}
		}
		if(mailbagPresent!=null && mailbagPresent.trim().length()>0){
			ErrorVO error=new ErrorVO(MAILBAG_EXIST_ERROR);
 			errors.add(error);
 			error.setErrorDisplayType(ErrorDisplayType.ERROR);
 			invocationContext.addAllError(errors);
			invocationContext.target = VALIDATE_FAILURE;
			return;   
		}
		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
		invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
		invoiceTransactionLogVO.setInvoiceType(MRAConstantsVO.IMPORT_DATA_TO_MRA_CARDIT);
		invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		invoiceTransactionLogVO.setInvoiceGenerationStatus(InvoiceTransactionLogVO.OPERATION_FLAG_INSERT);
		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
		invoiceTransactionLogVO.setRemarks(STATUS_IMPORTCARDITDATA);
		invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
		invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		invoiceTransactionLogVO.setPeriodFrom(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
		invoiceTransactionLogVO.setPeriodTo(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
		invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
		invoiceTransactionLogVO.getSerialNumber();
		invoiceTransactionLogVO.getTransactionCode();
		 try{
		   		invoiceTransactionLogVO =  new MailTrackingMRADelegate().initiateTransactionLogForInvoiceGeneration( invoiceTransactionLogVO );
			       }catch(BusinessDelegateException ex){
				errors = this.handleDelegateException(ex);
			    }
		    String txnlogInfo = new StringBuilder(invoiceTransactionLogVO.getTransactionCode()).append("-").append(invoiceTransactionLogVO.getSerialNumber()).toString();
			try {
				new MailTrackingMRADelegate().importMailsFromCarditData(documentBillingVO,txnlogInfo);
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.FINE, "*****in the exception");
				businessDelegateException.getMessage();
				processErrors = handleDelegateException(businessDelegateException);
			}
			//Added by A-4809 for CR ICRD-232299 Ends......................
	}
		
		//Added by a-7531 for icrd-132487 ends
		
       

		if (processErrors != null && !processErrors.isEmpty()) {
			log.log(Log.SEVERE, " @@@@@@@@ processErrors are there @@@@@@@@ ");
			displayMessageErrorVO = new ErrorVO(MESSAGE_PROCESS_RUN_FAILED);
			displayMessageErrorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			displayMessageErrorVO
					.setErrorData(new String[] { showProcessDescription(process) });
			invocationContext.addError(displayMessageErrorVO);
		} /*Added by A-7531 for ICRD-132508*/
		else if(errors.isEmpty()){
			log.log(Log.FINE, " @@@@@@@@ process Run Success @@@@@@@@ ");
			if(REPRORATE_MAILS.equals(process)&& mailBagCount > 29000){
			displayMessageErrorVO = new ErrorVO(MESSAGE_PROCESS_RUN_SUCCESS_REMAINING);
			}else{
			displayMessageErrorVO = new ErrorVO(MESSAGE_PROCESS_RUN_SUCCESS);
			}
			displayMessageErrorVO.setErrorDisplayType(ErrorDisplayType.INFO);
			displayMessageErrorVO
					.setErrorData(new String[] { showProcessDescription(process) });
			invocationContext.addError(displayMessageErrorVO);
		}

		//Added by A-7531 for ICRD-132508 starts
		processManagerForm.setOrigin(BLANK);
		processManagerForm.setDestination(BLANK);
		processManagerForm.setOriginOfficeOfExchange(BLANK);
		processManagerForm.setDestinationOfficeOfExchange(BLANK);
		processManagerForm.setMailCategory(BLANK);
		processManagerForm.setMailSubclass(BLANK);
		processManagerForm.setGpaCode(BLANK);
		processManagerForm.setToAirlineCode(BLANK);
		processManagerForm.setFromAirlineCode(BLANK);
		processManagerForm.setFromDate(BLANK);
		processManagerForm.setToDate(BLANK);
		processManagerForm.setMailFromDate(BLANK);
		processManagerForm.setMailToDate(BLANK);
		//Added by A-7531 for ICRD-132508 ends
		
		//added by a-7531 for icrd-132487 starts
		processManagerForm.setProrateException(BLANK);
		processManagerForm.setFromDateforProrate(BLANK);
		processManagerForm.setToDateforProrate(BLANK);
		processManagerForm.setMailCategoryforProrate(BLANK);
		processManagerForm.setMailSubclassforProrate(BLANK);
		processManagerForm.setOriginforProrate(BLANK);
		processManagerForm.setDestinationforProrate(BLANK);
		//added by a-7531 for icrd-132487 ends
		
		
		processManagerForm.setUpliftAirportReRate(BLANK);
		processManagerForm.setDischargeAirportReRate(BLANK);
		processManagerForm.setTransferAirlineReRate(BLANK);
		processManagerForm.setTransferPAReRate(BLANK);
		processManagerForm.setOriginOEReRate(BLANK);
		processManagerForm.setDestinationOEReRate(BLANK);
		
		processManagerForm.setUpliftAirportException(BLANK);
		processManagerForm.setDischargeAirportException(BLANK);
		processManagerForm.setTransferAirlineException(BLANK);
		processManagerForm.setTransferPAException(BLANK);
		processManagerForm.setOriginAirportException(BLANK);
		processManagerForm.setDestinationAirportException(BLANK);
		
		
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
		
	}

	/**
	 * @author a-2270
	 * @param processType
	 * @return
	 */
	private String showProcessDescription(String processType) {

		ProcessManagerSession processManagerSession = (ProcessManagerSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		String processDescription = "";

		if (processManagerSession.getProcesses() != null
				&& processManagerSession.getProcesses().size() > 0) {

			for (OneTimeVO processOneTimeVO : processManagerSession
					.getProcesses()) {

				if (processOneTimeVO.getFieldValue().equals(processType)) {
					processDescription = processOneTimeVO.getFieldDescription();
					break;
				}
			}

		}
		log.log(Log.INFO, " The Process Description>>>>> ", processDescription);
		return processDescription;
	}

	private boolean validateForm(MRAProcessManagerForm form) {
		boolean canProceed = true;
		if (!BLANK.equals(form.getFlightNumber())) {
			if (BLANK.equals(form.getCarrierCode())
					|| BLANK.equals(form.getFlightDate())) {
				canProceed = false;
			}
		} else if (!BLANK.equals(form.getFlightDate())) {
			if (BLANK.equals(form.getCarrierCode())
					|| BLANK.equals(form.getFlightNumber())) {
				canProceed = false;
			}
		} else if (!BLANK.equals(form.getCarrierCode())) {
			if (BLANK.equals(form.getFlightDate())
					|| BLANK.equals(form.getFlightNumber())) {
				canProceed = false;
			}
		}
		return canProceed;

	}
	/**
	 * 
	 * 	Method		:	ProcessCommand.getSystemParameterTypes
	 *	Added by 	:	A-8061 on 19-Feb-2021
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<String>
	 */
	 private Collection<String> getSystemParameterTypes(){
	    	log.entering("ProcessCommand", "getSystemParameterTypes");
	    	ArrayList<String> systemparameterTypes = new ArrayList<String>();
	    	systemparameterTypes.add(MAXRECORD_FOR_BULK_REPRORATE);
	    	log.exiting("ProcessCommand", "getSystemParameterTypes");
	    	return systemparameterTypes;
	    }
	 
	 
	 

}
