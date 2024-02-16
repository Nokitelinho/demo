/*
 * GenerateCSVCommand.java Created on Jul 1 2016
 *
 * Copyright 2010 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbagreconciliation;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailReconciliationFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailbagReconciliationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailbagReconciliationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
/**
 * 
 * @author A-5991
 *
 */
public class GenerateCSVCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAIL MRA DEFAULTS");

	private static final String CLASS_NAME = "GenerateCommand";
	
	private static final String MODULE_NAME = "mail.operations";
	
	private static final String SCREENID = "mailtracking.defaults.MailbagReconciliation";

	private static final String GENERATE_SUCCESS = "generate_success";
	private static final String GENERATE_FAILURE = "generate_failure";
	
	private static final String ERROR_FROM_DATE_MANDATORY ="mailtracking.defaults.MailbagReconciliation.fromdatemandatory";
	private static final String ERROR_TO_DATE_MANDATORY ="mailtracking.defaults.MailbagReconciliation.todatemandatory";
	private static final String ERROR_TO_DATE_LESS_THAN_FROM_DATE ="mailtracking.defaults.MailbagReconciliation.todatelessthanfromdate";
	private static final String ERROR_NO_DATA_FOUND ="mailtracking.defaults.MailbagReconciliation.report.error.nodatafound";
	private static final int seconds = 3600;
	private static final int hours = 24;
	private static final int millis = 1000 * seconds * hours;
	private static final long month = 30;

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 * @return void
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		log.entering(CLASS_NAME, "execute");
		
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		MailbagReconciliationSession mailbagReconciliationSession = getScreenSession(
				MODULE_NAME, SCREENID);

		MailbagReconciliationForm form = (MailbagReconciliationForm) invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailReconciliationFilterVO mailReconciliationFilterVO=new MailReconciliationFilterVO();
		
		 Collection<String> eventCodes=new ArrayList<String>();
	   	 Collection<String> msgMissing=new ArrayList<String>();
	   	 errors=validateForm(form);
	     if(errors!=null && errors.size()>0){
	     	invocationContext.addAllError(errors);
	     	invocationContext.target=GENERATE_FAILURE;
				return;
	     }

		/*
		 * Setting the form values into the VO fields.
		 */
		mailReconciliationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailReconciliationFilterVO.setOriginOE(form.getOrginOE());
		 mailReconciliationFilterVO.setDestinationOE(form.getDestinationOE());
		 mailReconciliationFilterVO.setCategory(form.getCategory());
		 mailReconciliationFilterVO.setSubclass(form.getSubClass());
		 if(form.getYear()!=null && form.getYear().trim().length()>0){
		 mailReconciliationFilterVO.setYear(Integer.parseInt(form.getYear()));
		 }
		 mailReconciliationFilterVO.setDSN(form.getDsn());
		 mailReconciliationFilterVO.setRSN(form.getRsn());
		 if(form.getWeight()!=null && form.getWeight().trim().length()>0){
		// mailReconciliationFilterVO.setWeight(Double.parseDouble(form.getWeight()));
			 mailReconciliationFilterVO.setWeight(form.getWeightMeasure());//added by A-7371
		 }
		 mailReconciliationFilterVO.setConsignmentDocumentNumber(form.getConsignmentId());
		 mailReconciliationFilterVO.setPaCode(form.getPaCode());
		 mailReconciliationFilterVO.setCarditOrigin(form.getCarditOrgin());
		 mailReconciliationFilterVO.setCarditDestination(form.getCarditDestination()); 
		 if(form.getResidit()!=null && form.getResidit().trim().length()>0){
			 String[] resditEvent=form.getResidit().split(",");
			 for(String resdit: resditEvent){
			 eventCodes.add(resdit);
			 }
			 mailReconciliationFilterVO.setEventCodes(eventCodes);
		 }
		 mailReconciliationFilterVO.setEventPortCode(form.getAirport());
		 mailReconciliationFilterVO.setControlReferenceNumber(form.getControlReferenceNumber());
		 if (!("").equals(form.getFromDate())) {
				LocalDate fromdate = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
				String scanFromDT = new StringBuilder(form.getFromDate())
																		.append(" ")
																		.append("00:00:00").toString();
				mailReconciliationFilterVO.setOperationFromDate(fromdate.setDateAndTime(scanFromDT,false));
			}
			if (!("").equals(form.getToDate())) {
				LocalDate todate = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
				String scanToDT = new StringBuilder(form.getToDate())
																	  .append(" ")
																	  .append("23:59:59").toString();
				mailReconciliationFilterVO.setOperationToDate(todate.setDateAndTime(scanToDT,false)); 
			}
		 if(form.getMessageMissing()!=null && form.getMessageMissing().trim().length()>0){
			 String[] messageMissing=form.getMessageMissing().split(",");
			 for(String msgMisg: messageMissing){
				 msgMissing.add(msgMisg);
			 }
			 mailReconciliationFilterVO.setMsgMissing(msgMissing);
		 }
		mailReconciliationFilterVO.setDelayPeriodRequired(form.isDelayPeriod());
		
		mailReconciliationFilterVO.setCarditStatus(form.getCarditStatus());
		
		log.log(Log.FINE, "After setting mailReconciliationFilterVO --->\n",
				mailReconciliationFilterVO);
		/*		 * Calling method to get the formatted data for file.
		 */
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		
		String fileData = null;
		try {
			fileData = mailTrackingDefaultsDelegate.generateMailExceptionReport(mailReconciliationFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			invocationContext.addAllError(errors);
			invocationContext.target = GENERATE_FAILURE;
			return;
		}
		
		if(fileData == null || "".equals(fileData)){
			log.log(Log.FINE,"No data found");
			ErrorVO errorVo = new ErrorVO(ERROR_NO_DATA_FOUND);
			errors.add(errorVo);
		}
		
		if(errors!=null&& errors.size()>0){
			log.log(Log.FINE,"Going to return control");
			invocationContext.addAllError(errors);
			invocationContext.target = GENERATE_FAILURE;
			return;
		}
		
		mailbagReconciliationSession.setData(fileData);
		
		log.exiting(CLASS_NAME, "execute");
		
		invocationContext.target=GENERATE_SUCCESS;
	}
	
	/**
	 * Method for validating the screenlevel requirements
	 * @param form
	 * @param companyCode
	 * @return  Collection<ErrorVO>
	 */
		  public Collection<ErrorVO> validateForm(MailbagReconciliationForm form){
			  
			  Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
				ErrorVO error = null;
				if(form.getFromDate()== null || "".equals(form.getFromDate())){
					error = new ErrorVO(ERROR_FROM_DATE_MANDATORY);
					errors.add(error);
				}
				if(form.getToDate()== null || "".equals(form.getToDate())){
					error = new ErrorVO(ERROR_TO_DATE_MANDATORY);
					errors.add(error);
				}
				if (form.getFromDate()!= null
						&& form.getToDate()!= null
						&& !"".equals(form.getFromDate().trim())
						&& !"".equals(form.getToDate().trim())) {
					if (!form.getFromDate().equals(
							form.getToDate())) {
						if (DateUtilities.isLessThan(form
								.getToDate(), form.getFromDate(),
								"dd-MMM-yyyy")) {
							error = new ErrorVO(ERROR_TO_DATE_LESS_THAN_FROM_DATE);
							errors.add(error);
						}
					}
				}
				if (form.getFromDate()!= null
						&& form.getToDate()!= null
						&& !"".equals(form.getFromDate().trim())
						&& !"".equals(form.getToDate().trim())) {
					LocalDate fromdate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
					LocalDate todate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
					fromdate.setDate(form.getFromDate());
					todate.setDate(form.getToDate());
					long diff = (todate.getTimeInMillis() - fromdate.getTimeInMillis())/ millis;

					if(diff > month){
						error = new ErrorVO("mailtracking.defaults.MailbagReconciliation.daterangegreaterthanmonth");
						errors.add(error);
					}
				}
				return errors;
		  }
		
}

