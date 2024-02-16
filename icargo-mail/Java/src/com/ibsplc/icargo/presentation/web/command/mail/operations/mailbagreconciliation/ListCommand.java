/*
 * ListCommand.java Created on Jul 1 2016
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

import com.ibsplc.icargo.business.mail.operations.vo.MailReconciliationDetailsVO;
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
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
/**
 * 
 * @author A-5991
 *
 */

public class ListCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ListCommand");

	private static final String LIST_SUCCESS="list_success";

	private static final String LIST_FAILURE="list_failure";

	private static final String MODULENAME = "mail.operations";

	private static final String SCREENID = "mailtracking.defaults.MailbagReconciliation";

	private static final int seconds = 3600;
	private static final int hours = 24;
	private static final int millis = 1000 * seconds * hours;
	private static final long month = 30;
	
	/**
	 * Method to execute the command
	 * @param invocationContext
	 * @exception  CommandInvocationException
	 */	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		String companyCode=null; 
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO(); 
		companyCode = logonAttributes.getCompanyCode();
		MailbagReconciliationForm form = 
			(MailbagReconciliationForm)invocationContext.screenModel;
		MailbagReconciliationSession session = 
			getScreenSession(MODULENAME, SCREENID);
		MailReconciliationFilterVO mailReconciliationFilterVO=null;
		if(form.getFromScreen()!=null && ("resditgeneration").equals(form.getFromScreen())){
			
			if(session!=null && session.getMailReconciliationFilterVO()!=null){
				mailReconciliationFilterVO=session.getMailReconciliationFilterVO();
				populateMailbagReconciliationForm(form,mailReconciliationFilterVO);
			}
			
		}else{
			mailReconciliationFilterVO=new MailReconciliationFilterVO();
		}
		Page<MailReconciliationDetailsVO>  mailReconciliationDetailsVOs=null;
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		Collection<String> eventCodes=new ArrayList<String>();
		Collection<String> msgMissing=new ArrayList<String>();
		errors=validateForm(form,logonAttributes);
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target=LIST_FAILURE;
			return;
		}  
		
		/*if(session.getMailReconciliationFilterVO() != null ){
			mailReconciliationFilterVO =  session.getMailReconciliationFilterVO();
			form.setFromDate(mailReconciliationFilterVO.getOperationFromDate().toDisplayDateOnlyFormat());
			form.setToDate(mailReconciliationFilterVO.getOperationToDate().toDisplayDateOnlyFormat());
		}else{*/
			mailReconciliationFilterVO.setCompanyCode(companyCode);
			int pageNumber=Integer.parseInt(form.getDisplayPage());
			mailReconciliationFilterVO.setPageNumber(pageNumber);
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
				//mailReconciliationFilterVO.setWeight(Double.parseDouble(form.getWeight()));
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
			if(form.getMessageMissing()!=null && form.getMessageMissing().trim().length()>0){
				String[] messageMissing=form.getMessageMissing().split(",");
				for(String msgMisg: messageMissing){
					msgMissing.add(msgMisg);
				}
				mailReconciliationFilterVO.setMsgMissing(msgMissing);
			}
			mailReconciliationFilterVO.setDelayPeriodRequired(form.isDelayPeriod());
			mailReconciliationFilterVO.setCarditStatus(form.getCarditStatus());
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
			mailReconciliationFilterVO.setDelayPeriodValue(session.getDelayPeriod());
			log.log(Log.FINE, "session cnt", session.getTotalRecords());
			if("Y".equals(form.getCountFlag()) &&  session.getTotalRecords() != 0){
				mailReconciliationFilterVO.setTotalRecords(session.getTotalRecords());
			}else{
				mailReconciliationFilterVO.setTotalRecords(-1);
			} 
		//}

		session.setMailReconciliationFilterVO(mailReconciliationFilterVO);
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
			new MailTrackingDefaultsDelegate();
		try{
			mailReconciliationDetailsVOs=mailTrackingDefaultsDelegate.listReconciliationDetails(mailReconciliationFilterVO);
		}catch (BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);

		}
		if(mailReconciliationDetailsVOs!=null && mailReconciliationDetailsVOs.size()>0){
			session.setTotalRecords(mailReconciliationDetailsVOs.getTotalRecordCount());  
			log.log(Log.FINE, "\n\n\n TotalRecordCount------>", session.getTotalRecords());
			session.setMailReconciliationDetailsVOs(mailReconciliationDetailsVOs);
			invocationContext.target=LIST_SUCCESS; 

		}
		else{
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.MailbagReconciliation.noDetails");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);

			session.removeMailReconciliationDetailsVOs();
			invocationContext.target = LIST_FAILURE;
			return;
		}
		

	}

	/**
	 * Method for validating the screenlevel requirements
	 * @param form
	 * @param companyCode
	 * @return  Collection<ErrorVO>
	 */
	public Collection<ErrorVO> validateForm(MailbagReconciliationForm form,LogonAttributes logonAttributes){

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;

		if(form.getFromDate()== null || "".equals(form.getFromDate())){
			error = new ErrorVO("mailtracking.defaults.MailbagReconciliation.fromdatemandatory");
			errors.add(error);
		}
		if(form.getToDate()== null || "".equals(form.getToDate())){
			error = new ErrorVO("mailtracking.defaults.MailbagReconciliation.todatemandatory");
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
					error = new ErrorVO("mailtracking.defaults.MailbagReconciliation.todatelessthanfromdate");
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
	public void populateMailbagReconciliationForm(MailbagReconciliationForm form,MailReconciliationFilterVO mailReconciliationFilterVO){
		
		form.setDisplayPage("1");
		form.setLastPageNum("0");		
		
		if(mailReconciliationFilterVO.getOriginOE()!=null){
			form.setOrginOE(mailReconciliationFilterVO.getOriginOE());
		}else{
			form.setOrginOE("");
		}
		if(mailReconciliationFilterVO.getDestinationOE()!=null){
			form.setDestinationOE(mailReconciliationFilterVO.getDestinationOE());
		}else{
			form.setDestinationOE("");
		}
		if(mailReconciliationFilterVO.getCategory()!=null){
			form.setCategory(mailReconciliationFilterVO.getCategory());
		}else{
			form.setCategory("");
		}	
		if(mailReconciliationFilterVO.getSubclass()!=null){
			form.setSubClass(mailReconciliationFilterVO.getSubclass());
		}else{
			form.setSubClass("");
		}
		if(mailReconciliationFilterVO.getYear()>0){
			form.setYear(String.valueOf(mailReconciliationFilterVO.getYear()));
		}else{
			form.setYear("");
		}
		if(mailReconciliationFilterVO.getDSN()!=null){
			form.setDsn(mailReconciliationFilterVO.getDSN());
		}else{
			form.setDsn("");
		}
		if(mailReconciliationFilterVO.getRSN()!=null){
			form.setRsn(mailReconciliationFilterVO.getRSN());
		}else{
			form.setRsn("");
		}
		//if(mailReconciliationFilterVO.getWeight()>0){
		if(mailReconciliationFilterVO.getWeight().getRoundedSystemValue()>0){//added by A-7371
			form.setWeight(String.valueOf(mailReconciliationFilterVO.getWeight()));
		}else{
			form.setWeight("");
		}
		
		if(mailReconciliationFilterVO.getConsignmentDocumentNumber()!=null ){
			form.setConsignmentId(mailReconciliationFilterVO.getConsignmentDocumentNumber());
		}else{
			form.setConsignmentId("");
		}	
		if(mailReconciliationFilterVO.getPaCode()!=null ){
			form.setPaCode(mailReconciliationFilterVO.getPaCode());
		}else{
			form.setPaCode("");
		}	
		if(mailReconciliationFilterVO.getCarditOrigin()!=null ){
			form.setCarditOrgin(mailReconciliationFilterVO.getCarditOrigin());
		}else{
			form.setCarditOrgin("");
		}	
		if(mailReconciliationFilterVO.getCarditDestination()!=null ){
			form.setCarditDestination(mailReconciliationFilterVO.getCarditDestination());
		}else{
			form.setCarditDestination("");
		}			
		if(mailReconciliationFilterVO.getEventCodes()!=null && mailReconciliationFilterVO.getEventCodes().size()>0){
			Collection<String> eventCodes=mailReconciliationFilterVO.getEventCodes();
			StringBuilder resdit=null;
			int count=0;
			for(String event: eventCodes){
				if(count==0){
					resdit=new StringBuilder(event);
					
				}else{
					resdit=resdit.append(",").append(event);
				}
				
				count++;
			
			}
			form.setResidit(resdit.toString());
		}else{
			form.setResidit("");
		}
		
		
		if(mailReconciliationFilterVO.getEventPortCode()!=null ){
			form.setAirport(mailReconciliationFilterVO.getEventPortCode());
		}else{
			form.setAirport("");
		}	
		if(mailReconciliationFilterVO.getControlReferenceNumber()!=null ){
			form.setControlReferenceNumber(mailReconciliationFilterVO.getControlReferenceNumber());
		}else{
			form.setControlReferenceNumber("");
		}
		if(mailReconciliationFilterVO.getMsgMissing()!=null && mailReconciliationFilterVO.getMsgMissing().size()>0){
			Collection<String> messageMissings=mailReconciliationFilterVO.getMsgMissing();
			StringBuilder messageMissing=null;
			int count=0;
			for(String msgMissing: messageMissings){
				if(count==0){
					messageMissing=new StringBuilder(msgMissing);
					
				}else{
					messageMissing=messageMissing.append(",").append(msgMissing);
				}
				
				count++;
			
			}
			form.setMessageMissing(messageMissing.toString());
		}else{
			form.setMessageMissing("");
		}
		
		
		if(mailReconciliationFilterVO.isDelayPeriodRequired()){
			form.setDelayPeriod(true);
		}else{
			form.setDelayPeriod(false);
		}
		if(mailReconciliationFilterVO.getCarditStatus()!=null ){
			form.setCarditStatus(mailReconciliationFilterVO.getCarditStatus());
		}else{
			form.setCarditStatus("");
		}
		if(mailReconciliationFilterVO.getOperationFromDate()!=null){
			form.setFromDate(mailReconciliationFilterVO.getOperationFromDate().toDisplayDateOnlyFormat());
		}else{
			form.setFromDate("");
		}
		if(mailReconciliationFilterVO.getOperationToDate()!=null){
			form.setToDate(mailReconciliationFilterVO.getOperationToDate().toDisplayDateOnlyFormat());
		}else{
			form.setToDate("");
		}
		
		form.setFromScreen("");
		
		
	}
}
