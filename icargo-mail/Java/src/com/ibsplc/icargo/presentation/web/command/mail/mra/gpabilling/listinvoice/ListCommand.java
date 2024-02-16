/*
 * ListCommand.java Created on Jun 30, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import java.util.ArrayList;
import java.util.Collection;

import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListGPABillingInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListGPABillingInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-3434
 *
 */
public class ListCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA GPABILLING");

	private static final String CLASS_NAME = "ListCommand";
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREENID = "mailtracking.mra.gpabilling.listgpabillinginvoice";
	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_failure";
	private static final String KEY_NO_RESULTS_FOUND = "mailtracking.mra.gpabilling.noresultsfound";
	private static final String ERROR_KEY_DATE ="mailtracking.mra.gpabilling.listcn51.nodatefields";
	private static final String ERROR_FROM_DATE_MANDATORY  ="mailtracking.mra.gpabilling.listcn51.fromdatemandatory";
	private static final String ERROR_TO_DATE_MANDATORY  ="mailtracking.mra.gpabilling.listcn51.todatemandatory";
	private static final String ERROR_KEY_NO_INVALID_DATE ="mailtracking.mra.gpabilling.listcn51.notvaliddate";
	private static final String BLANK = "";
	private static final String COMMA = ",";
	private static final String NEW_LINE = "\n";

	/**
	 * Method to implement the List operation
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering(CLASS_NAME,"execute");

		ListGPABillingInvoiceForm   listGPABillingInvoiceForm  = ( ListGPABillingInvoiceForm )invocationContext.screenModel;

	ListGPABillingInvoiceSession listGPABillingInvoiceSession =
			(ListGPABillingInvoiceSession)getScreenSession(MODULE_NAME, SCREENID);

		Page<CN51SummaryVO> cn51SummaryVOs = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		CN51SummaryFilterVO cn51SummaryFilterVO=new CN51SummaryFilterVO	();
		//listGPABillingInvoiceSession.removeCN51SummaryVOs();

		Map<String, Collection<OneTimeVO>> oneTimeMap = null;



		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		
		if("listinvoice".equals(listGPABillingInvoiceForm.getFromScreen())){  
			
			cn51SummaryFilterVO.setCompanyCode(companyCode);
				if(listGPABillingInvoiceForm.getFromDate()!=null && listGPABillingInvoiceForm.getFromDate().trim().length()>0){
					cn51SummaryFilterVO.setFromDate	(convertToDate(listGPABillingInvoiceForm.getFromDate()));
				}
				if(listGPABillingInvoiceForm.getToDate()!=null && listGPABillingInvoiceForm.getToDate().trim().length()>0){
					cn51SummaryFilterVO.setToDate(convertToDate(listGPABillingInvoiceForm.getToDate()));
				}
			//if(listGPABillingInvoiceSession.getFromScreen()!=null && !"listGPAInvoices".equals(listGPABillingInvoiceSession.getFromScreen())){
			if(listGPABillingInvoiceForm.getInvoiceNo()!=null && listGPABillingInvoiceForm.getInvoiceNo().trim().length()>0){
					cn51SummaryFilterVO.setInvoiceNumber(listGPABillingInvoiceForm.getInvoiceNo());
			}
			if(listGPABillingInvoiceForm.getGpacode()!=null && listGPABillingInvoiceForm.getGpacode().trim().length()>0){
					cn51SummaryFilterVO.setGpaCode(listGPABillingInvoiceForm.getGpacode());
			}	
			if(listGPABillingInvoiceForm.getInvoiceStatus()!=null && listGPABillingInvoiceForm.getInvoiceStatus().trim().length()>0){
				cn51SummaryFilterVO.setInvoiceStatus(listGPABillingInvoiceForm.getInvoiceStatus());
			}
			if(Objects.nonNull(listGPABillingInvoiceForm.getPassFileName())&&!listGPABillingInvoiceForm.getPassFileName().isEmpty()){
				cn51SummaryFilterVO.setPassFileName(listGPABillingInvoiceForm.getPassFileName());
			}
			if(Objects.nonNull(listGPABillingInvoiceForm.getPeriodNumber())&&!listGPABillingInvoiceForm.getPeriodNumber().isEmpty()){
				cn51SummaryFilterVO.setPeriodNumber(listGPABillingInvoiceForm.getPeriodNumber());
			}
			if("on".equals(listGPABillingInvoiceForm.getCheckPASS())){
				cn51SummaryFilterVO.setPASS(true);
			}
		}
		else{

			       
				cn51SummaryFilterVO=listGPABillingInvoiceSession.getCN51SummaryFilterVO();
				   if(cn51SummaryFilterVO!=null){
				if(cn51SummaryFilterVO.getFromDate()!=null){
					listGPABillingInvoiceForm.setFromDate(cn51SummaryFilterVO.getFromDate().toDisplayDateOnlyFormat());
					}
					if(cn51SummaryFilterVO.getToDate()!=null){
					listGPABillingInvoiceForm.setToDate(cn51SummaryFilterVO.getToDate().toDisplayDateOnlyFormat());
					}
					if(cn51SummaryFilterVO.getInvoiceNumber()!=null && cn51SummaryFilterVO.getInvoiceNumber().trim().length()>0){
					listGPABillingInvoiceForm.setInvoiceNo(cn51SummaryFilterVO.getInvoiceNumber());
					}
					if(cn51SummaryFilterVO.getGpaCode()!=null && cn51SummaryFilterVO.getGpaCode().trim().length()>0){			
					listGPABillingInvoiceForm.setGpacode(cn51SummaryFilterVO.getGpaCode());         
					}
					if(cn51SummaryFilterVO.getInvoiceStatus()!=null && cn51SummaryFilterVO.getInvoiceStatus().trim().length()>0) {
						listGPABillingInvoiceForm.setInvoiceStatus(cn51SummaryFilterVO.getInvoiceStatus());
					}
				
				   }       
				
		}
		
		
		
		
				
			
		            
		
		listGPABillingInvoiceSession.setCN51SummaryFilterVO(cn51SummaryFilterVO);
			

			log.log(Log.FINE, "cn51SummaryFilterVO--null-->",
					listGPABillingInvoiceSession.getCN51SummaryFilterVO());
		
		errors = validateForm(listGPABillingInvoiceForm, errors);

		if(errors != null && errors.size() > 0){
			
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		      
		
		cn51SummaryFilterVO.setPageNumber(Integer.parseInt(listGPABillingInvoiceForm.getDisplayPage()));
		
		log.log(Log.FINE, "cn51SummaryFilterVO--null-->",cn51SummaryFilterVO);
		try {
			cn51SummaryVOs = new MailTrackingMRADelegate().findAllInvoices(cn51SummaryFilterVO);

		} catch (BusinessDelegateException e) {
			errors.addAll(handleDelegateException(e));
			
		}
		
		oneTimeMap=	listGPABillingInvoiceSession.getOneTimeMap();
		Collection<OneTimeVO> statusCollection = oneTimeMap.get("mra.gpabilling.invoicestatus");
		//statusCollection.addAll(oneTimeMap.get("mailtracking.mra.gpabilling.paymentstatus"));
		
		log.log(Log.FINE, "Invoice Status-->", statusCollection);
		if(cn51SummaryVOs != null && cn51SummaryVOs.size() > 0){

			for(CN51SummaryVO cN51SummaryVO:cn51SummaryVOs){
				for(OneTimeVO status :statusCollection){
					if(cN51SummaryVO.getInvoiceStatus()!=null){
						if(cN51SummaryVO.getInvoiceStatus().equals(status.getFieldValue()) ){
							cN51SummaryVO.setInvoiceStatusDisplay(status.getFieldDescription());
							String date=cN51SummaryVO.getBilledDate().toString();
							//cN51SummaryVO.setBilledDate(date);

							LocalDate retdat = new LocalDate(logonAttributes.getAirportCode(),Location.ARP, true);

							retdat.setDate(cN51SummaryVO.getBilledDate().toDisplayDateOnlyFormat());

							log.log(Log.FINE, "Invoice Status1-->",
									cN51SummaryVO.getInvoiceStatus());
							
						
						}
					}
					//Added By A-8527 for ICRD-234294 Starts
					if(cN51SummaryVO.getRebillInvoiceDetails()!=null && cN51SummaryVO.getRebillInvoiceDetails().size()>0 ){
						for(CN51SummaryVO rebillDetailsVO:cN51SummaryVO.getRebillInvoiceDetails()){
							if(!(null==rebillDetailsVO.getInvoiceStatus()))
							{
							if(status.getFieldValue().equals(rebillDetailsVO.getInvoiceStatus()) ){
								rebillDetailsVO.setInvoiceStatusDisplay(status.getFieldDescription());
						}	}
						}
					}
					//Added By A-8527 for ICRD-234294 Ends
				}


				updatePASSFileName(cN51SummaryVO);

			}
			if((listGPABillingInvoiceSession.getCN51SummaryFilterVO()!=null)
					&&!"Y".equals(listGPABillingInvoiceForm.getFailureFlag())){
				log.log(Log.FINE, "from enquiry---->", cn51SummaryFilterVO);
				if(cn51SummaryFilterVO.getFromDate().toDisplayDateOnlyFormat()!=null){
					cn51SummaryFilterVO = listGPABillingInvoiceSession.getCN51SummaryFilterVO();
				}

				if(cn51SummaryFilterVO.getFromDate().toDisplayDateOnlyFormat()!=null){
					listGPABillingInvoiceForm.setFromDate(cn51SummaryFilterVO.getFromDate().toDisplayDateOnlyFormat());
				}
				listGPABillingInvoiceForm.setToDate(cn51SummaryFilterVO.getToDate().toDisplayDateOnlyFormat());
				if(cn51SummaryFilterVO.getInvoiceNumber()!=null){
					listGPABillingInvoiceForm.setInvoiceNo(cn51SummaryFilterVO.getInvoiceNumber());
				}
				else{
					listGPABillingInvoiceForm.setInvoiceNo(BLANK);
				}
				if(cn51SummaryFilterVO.getGpaCode()!=null){
					listGPABillingInvoiceForm.setGpacode(cn51SummaryFilterVO.getGpaCode());
				}
				if(cn51SummaryFilterVO.getInvoiceStatus()!=null){
					listGPABillingInvoiceForm.setInvoiceStatus(cn51SummaryFilterVO.getInvoiceStatus());
				}
				if(Objects.nonNull(cn51SummaryFilterVO.getPassFileName()) && !cn51SummaryFilterVO.getPassFileName().isEmpty()){
					listGPABillingInvoiceForm.setPassFileName(cn51SummaryFilterVO.getPassFileName());
				}
				if(Objects.nonNull(cn51SummaryFilterVO.getPeriodNumber()) && !cn51SummaryFilterVO.getPeriodNumber().isEmpty()){
					listGPABillingInvoiceForm.setPeriodNumber(cn51SummaryFilterVO.getPeriodNumber());
				}
				if (cn51SummaryFilterVO.isPASS()) {
					listGPABillingInvoiceForm.setCheckPASS("on");
				}
				log.log(Log.FINE, "cn51SummaryFilterVO--->",
						cn51SummaryFilterVO);	
			}
			
			listGPABillingInvoiceSession.setCN51SummaryVOs(cn51SummaryVOs);
			listGPABillingInvoiceSession.setFromScreen(null);
			listGPABillingInvoiceForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
			invocationContext.target = LIST_SUCCESS;


		}else{
			if(cn51SummaryVOs == null){
				
				listGPABillingInvoiceSession.setCN51SummaryVOs(cn51SummaryVOs);
				listGPABillingInvoiceSession.setTotalRecords(0);
				
			}
			errors.add(new ErrorVO(KEY_NO_RESULTS_FOUND));
			invocationContext.addAllError(errors);
			listGPABillingInvoiceForm.setFailureFlag("Y");
			listGPABillingInvoiceForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = LIST_FAILURE;


		}


	}


	/**
	 *
	 * @param date
	 * @return LocalDate
	 */
	private LocalDate convertToDate(String date){

		if(date!=null && !date.equals(BLANK)){

			return(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate( date ));
		}
		return null;
	}

	/**
	 *
	 * @param form
	 * @param errors
	 * @return
	 */
	private  Collection<ErrorVO> validateForm( ListGPABillingInvoiceForm form,
			Collection<ErrorVO> errors){


		if(BLANK.equals(form.getFromDate()) && BLANK.equals(form.getToDate())){

			errors.add(new ErrorVO(ERROR_KEY_DATE));

		}else{
			if(BLANK.equals(form.getFromDate())){

				errors.add(new ErrorVO(ERROR_FROM_DATE_MANDATORY));
			}
			else if(BLANK.equals(form.getToDate())){

				errors.add(new ErrorVO(ERROR_TO_DATE_MANDATORY));
			}
			else if(!validateDate(form.getFromDate(), form.getToDate())){

				errors.add(new ErrorVO(ERROR_KEY_NO_INVALID_DATE));
			}
		}
		return errors;
	}

	/**
	 * validating fromdate and todate
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	private boolean validateDate( String fromDate, String toDate ){

		if( ((toDate != null)&&(toDate.trim().length()>0)) &&
				((fromDate != null) &&(fromDate.trim().length()>0))&&!(toDate.equals(fromDate)) ) {

			return DateUtilities.isLessThan( fromDate, toDate, LocalDate.CALENDAR_DATE_FORMAT );

		}else{

			return true;
		}
	}
	private void updatePASSFileName(CN51SummaryVO cN51SummaryVO) {
		if(isNotNullAndEmpty(cN51SummaryVO.getPassFileName())){
			String passFileName = cN51SummaryVO.getPassFileName().replace(COMMA, COMMA+NEW_LINE);
			cN51SummaryVO.setPassFileName(passFileName);
		}
		
	}


	private static boolean isNotNullAndEmpty(String s) {
		return Objects.nonNull(s)&&!s.trim().isEmpty();
	}

}
