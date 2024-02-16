/*
 * ListCommand.java Created on Mar 26, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoicesettlement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.InvoiceSettlementSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2408
 *
 */
public class ListCommand extends BaseCommand {
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREEN_ID ="mailtracking.mra.gpabilling.invoicesettlement";

	private static final String SCREENLOAD_SUCCESS ="screenload_success";

	private static final String SCREENLOAD_FAILURE="screenload_failure";

	private static final String CLASS_NAME = "ListCommand";

	private static final String FILTER_MANDATORY="mailtracking.mra.gpabilling.msg.err.filtermandatory";

	private static final String BLANK="";

	private static final String NO_DATA="mailtracking.mra.gpabilling.msg.err.noresultsfound";

	private static final String LIST_STATUS="list";
	private static final String AVAIL_POPUP="avail_popup";
	private static final String LIST_ACCOUNTING="from_listaccounting";
	private static final String INVOICE_DETAIL="from_invoicedetail";

	private static final String SCREENSTATUS_SCREENLOAD="screenload";


	private static final String DATERANGEEXCEEDS = "mailtracking.mra.gpabilling.err.daterangeexceeds";
	private static final String FROMDATE_MUST_NOT_BE_GREATER="mailtracking.mra.gpabilling.msg.err.fromdatenotgreater";
	private static final String YES= "Y";
	private static final String INVOICE_ENQUIRY="invoice_enquiry";
	private static final String SETTLEMENT_NOTEXIST="mailtracking.mra.gpabilling.msg.err.settlementdoesnotexist";

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException{

		Log log = LogFactory.getLogger("MRA_GPABILLING");
		log.entering(CLASS_NAME, "execute");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		ErrorVO errorVO=null;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		InvoiceSettlementSession session=(InvoiceSettlementSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		InvoiceSettlementForm form=(InvoiceSettlementForm)invocationContext.screenModel;
		Page<GPASettlementVO> gpaSettlementVOs=null;
		InvoiceSettlementFilterVO filterVO=new InvoiceSettlementFilterVO();
		
		filterVO.setCompanyCode(companyCode);
		GPASettlementVO gpaSettlementFilterVO=null;
		
		if(AVAIL_POPUP.equals(form.getFrmPopUp()) ){
					errorVO=new ErrorVO(NO_DATA);
					if(!AVAIL_POPUP.equals(form.getFrmPopUp()) ){ //if loop added by a-5133 as part of ICRD-23808
					errors.add(errorVO);
					}
			Collection<GPASettlementVO> gpaVo= session.getSelectedGPASettlementVOs();
//			for(GPASettlementVO gpaSettlementVO : gpaVo){
//				form.setGpaCodeFilter(gpaSettlementVO.getGpaCode());
//				
//				
//			}
			// If user is redirected to Settlement Capture screen from CREATE btn of AvailableSettlement Popup
			//Continue as a fresh settlemnt capture for that PA
			if(YES.equals(form.getCreateFlag())){
				//form.setCreateFlag("");
				 gpaSettlementFilterVO = new GPASettlementVO();
				Collection <GPASettlementVO> gpaSettlementVOsInSession = session.getGPASettlementVOs();
		
				for(GPASettlementVO gpaSettlementVO: gpaSettlementVOsInSession){
					gpaSettlementFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
					gpaSettlementFilterVO.setGpaCode(gpaSettlementVO.getGpaCode());
					gpaSettlementFilterVO.setDisplayPage(Integer.parseInt(form.getDisplayPage()));
				}
				try {
					gpaSettlementVOs =  new MailTrackingMRADelegate().findUnSettledInvoicesForGPAForSettlementCapture(gpaSettlementFilterVO);
				} catch (BusinessDelegateException e) {
					log.log(Log.FINE,  "BusinessDelegateException");
					
				}
				LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
				form.setSettlementDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
				//Added for clearing the settlement reference no: for create.
				form.setSettlementReferenceNumber("");
				form.setScreenStatus(SCREENSTATUS_SCREENLOAD);
				form.setCheckFlag("ENABLE");
				session.setGPASettlementVO(gpaSettlementVOs);
				session.setInvoiceSettlementDetailVOs(gpaSettlementVOs.iterator().next().getInvoiceSettlementVOsPage());
				//session.setSelectedGPASettlementVOs(gpaSettlementVOs);
			}
			//Else details associated to the selected settlement will be displayed
			else{
				
				filterVO=session.getInvoiceSettlementFilterVO();
				try {
					gpaSettlementVOs=new MailTrackingMRADelegate().findSettlementDetails(filterVO ,Integer.parseInt(form.getDisplayPage()));
				} catch (BusinessDelegateException | NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
				session.setGPASettlementVO(gpaSettlementVOs);
				//Collection<GPASettlementVO> gpaVos= session.getGPASettlementVOs(); 
				session.setInvoiceSettlementDetailVOs(gpaSettlementVOs.iterator().next().getInvoiceSettlementVOsPage());
				for(GPASettlementVO gpaSettlementVO : gpaSettlementVOs){
					form.setGpaCodeFilter(gpaSettlementVO.getGpaCode());
					
					
					
					form.setSettleCurrency(gpaSettlementVO.getSettlementCurrency());
					if(gpaSettlementVO.getSettlementDate()!= null && gpaSettlementVO.getSettlementId() != null){
						form.setSettlementReferenceNumber(gpaSettlementVO.getSettlementId());
						form.setSettlementDate(gpaSettlementVO.getSettlementDate().toDisplayDateOnlyFormat().toString());
					}
					else{
						LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
						form.setSettlementDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
					}
				}
			}
		//Added by A-5116 for ICRD-18551
		}else if (LIST_ACCOUNTING.equals(form.getFrmPopUp()) || INVOICE_DETAIL.equals(form.getFrmPopUp())) {
			filterVO=session.getInvoiceSettlementFilterVO();
			Page<GPASettlementVO> gpaVo= session.getSelectedGPASettlementVOs();
			if(gpaVo != null){
				for(GPASettlementVO gpaSettlementVO : gpaVo){
					if(gpaSettlementVO != null){
						filterVO.setSettlementReferenceNumber(gpaSettlementVO.getSettlementId());
						form.setSettlementReferenceNumber(gpaSettlementVO.getSettlementId());
						form.setSettleCurrency(gpaSettlementVO.getSettlementCurrency());
						if(gpaSettlementVO.getSettlementDate()!= null){
							form.setSettlementDate(gpaSettlementVO.getSettlementDate().toDisplayDateOnlyFormat().toString());
						}
						else{
							LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
							form.setSettlementDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
						}
					}
					break;
				}			
			}	
				try{
					gpaSettlementVOs=new MailTrackingMRADelegate().findSettlementDetails(filterVO ,Integer.parseInt(form.getDisplayPage()));
				}
				catch (BusinessDelegateException businessDelegateException) {
					errors=handleDelegateException(businessDelegateException);
				}
				if(gpaSettlementVOs!=null && gpaSettlementVOs.size()>0){
					if (gpaSettlementVOs.iterator().next().getSettlementDetailsVOs()!=null && 
							gpaSettlementVOs.iterator().next().getSettlementDetailsVOs().size()>0){
						form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
						session.setGPASettlementVO(gpaSettlementVOs);
						session.setGPASettlementVOs(gpaSettlementVOs);					
						form.setScreenStatus(SCREENSTATUS_SCREENLOAD);					
					}
					//if no settlement exists for the PA ,all unsettled invoices will be listed
					else{
						//For finding the unsettled invoices for a GPA while listing
						Collection <GPASettlementVO> unsettledInvoices=new ArrayList<GPASettlementVO>();
						gpaSettlementFilterVO=populateGPASettlementVO(form, session,logonAttributes);
						if(filterVO.getInvoiceNumber()==null && (filterVO.getSettlementStatus()==null || "F".equals(filterVO.getSettlementStatus()) || "D".equals(filterVO.getSettlementStatus())) && 
									filterVO.getFromDate()==null && filterVO.getToDate()==null){
							try{
								unsettledInvoices=new MailTrackingMRADelegate().findUnSettledInvoicesForGPA(gpaSettlementFilterVO);
							}
							catch (BusinessDelegateException businessDelegateException) {
								errors=handleDelegateException(businessDelegateException);
							}
						}
						LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
						form.setSettlementDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
						if(unsettledInvoices!=null && unsettledInvoices.size()>0){
							//Unsettled Invoices needs to be set only if there are no associated invoices for that settlement
//							if(gpaSettlementVOs.iterator().next().getInvoiceSettlementVOs()== null || 
//									gpaSettlementVOs.iterator().next().getInvoiceSettlementVOs().size()<=0){
								gpaSettlementVOs.iterator().next().setInvoiceSettlementVOs
								(unsettledInvoices.iterator().next().getInvoiceSettlementVOs());
//							}
						}
						/*
						 * String settlementCurrency="";
						 * try {
							settlementCurrency= new MailTrackingMRADelegate().findSettlementCurrency(logonAttributes.getCompanyCode(), form.getGpaCodeFilter());
						} catch (BusinessDelegateException e) {
							errors=handleDelegateException(e);					
						}
						if(settlementCurrency ==null || settlementCurrency.trim().length()<=0){
							errorVO=new ErrorVO("mailtracking.mra.gpabilling.msg.err.settlementcurrencymandatory");
							errors.add(errorVO);					
						}
						form.setSettleCurrency(settlementCurrency);
						 */
						session.setGPASettlementVO(gpaSettlementVOs);						
						form.setScreenStatus(SCREENSTATUS_SCREENLOAD);
						form.setCheckFlag("ENABLE");
						form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
						//errorVO=new ErrorVO(NO_DATA);
						//errors.add(errorVO);
					}
				}
				else{
					Page<GPASettlementVO> gpaSettlemntVOsToList = new Page<GPASettlementVO>(new ArrayList<GPASettlementVO>(),0,0,0,0,0,false);
					form.setScreenStatus(SCREENSTATUS_SCREENLOAD);
					Collection <GPASettlementVO> newInvoices=new ArrayList<GPASettlementVO>();
					//if( "F".equals(filterVO.getSettlementStatus()) || "D".equals(filterVO.getSettlementStatus())){
						gpaSettlementFilterVO=populateGPASettlementVO(form, session,logonAttributes);
						try{
							newInvoices=new MailTrackingMRADelegate().findUnSettledInvoicesForGPA(gpaSettlementFilterVO);
						}
						catch (BusinessDelegateException businessDelegateException) {
							errors=handleDelegateException(businessDelegateException);
						}
						if(newInvoices.size() > 0){
						GPASettlementVO gPASettlementVO=new GPASettlementVO();
						Collection<InvoiceSettlementVO> invoiceSettlementVOs = newInvoices.iterator().next().getInvoiceSettlementVOs();
						Collection<InvoiceSettlementVO> invoiceSettlementVOFinalised = new ArrayList<InvoiceSettlementVO>();
						Collection<InvoiceSettlementVO> invoiceSettlementVODiffernce = new ArrayList<InvoiceSettlementVO>();
						Collection <GPASettlementVO> newInvoicestoDisplay=new ArrayList<GPASettlementVO>();
						if(invoiceSettlementVOs!=null && invoiceSettlementVOs.size()>0){
							if("F".equals(form.getInvoiceStatusFilter())){
								for(InvoiceSettlementVO invoiceSettlementVO:invoiceSettlementVOs){
									if("F".equals(invoiceSettlementVO.getSettlementStatus())){
										invoiceSettlementVOFinalised.add(invoiceSettlementVO);
									}
								}
								gPASettlementVO.setInvoiceSettlementVOs(invoiceSettlementVOFinalised);
								newInvoicestoDisplay.add(gPASettlementVO);
							}else if("D".equals(form.getInvoiceStatusFilter())){
								for(InvoiceSettlementVO invoiceSettlementVO:invoiceSettlementVOs){
									if("D".equals(invoiceSettlementVO.getSettlementStatus())){
										invoiceSettlementVODiffernce.add(invoiceSettlementVO);
									}
								}
								gPASettlementVO.setInvoiceSettlementVOs(invoiceSettlementVODiffernce);
								newInvoicestoDisplay.add(gPASettlementVO);
							}
							else{
								newInvoicestoDisplay.addAll(newInvoices); 
							}
						}
						gpaSettlemntVOsToList.addAll(newInvoicestoDisplay);
							session.setGPASettlementVO(gpaSettlemntVOsToList);	
							LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
							form.setSettlementDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
						}
						else{
					errorVO=new ErrorVO(NO_DATA);
					errors.add(errorVO); 
					LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
							form.setSettlementDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
						}
						form.setCheckFlag("ENABLE");
						form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		}
		}else{
			

			String gpaCode=form.getGpaCodeFilter();
			String invNumber=form.getInvRefNumberFilter();
			String fromDate=form.getFromDate();
			String toDate=form.getToDate();
			String settlementStatus=form.getSettlementStatusFilter();
			//Changed for CR ICRD-7316
			String gpaName = form.getGpaNameFilter();
			String invoiceStatus = form.getInvoiceStatusFilter();
			String chequeNo= form.getChequeNumberFilter();

			if(session.getInvoiceSettlementFilterVO()==null){
				if(gpaCode!=null && gpaCode.trim().length()>0){
					filterVO.setGpaCode(gpaCode.trim().toUpperCase());
					/*
					 * Validate the GpaCode  and obtain the GpaCode
					 */

					errors = validateGpaCode(form,
							logonAttributes);
					log.log(Log.FINE,"errors"+errors);
				}
				if(errors != null && errors.size() > 0 ){
					log.log(Log.INFO," ######### SCREENLOAD_FAILURE ####");
					invocationContext.addAllError(errors);
					form.setScreenStatus(SCREENSTATUS_SCREENLOAD);
					invocationContext.target = SCREENLOAD_FAILURE;
					return;
				}


				if(invNumber!=null && invNumber.trim().length()>0){
					filterVO.setInvoiceNumber(invNumber.trim().toUpperCase());
				}
				if(fromDate!=null && fromDate.trim().length()>0){
					filterVO.setFromDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(fromDate));
				}
				if(toDate!=null && toDate.trim().length()>0){
					filterVO.setToDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(toDate));

				}
				if(invoiceStatus != null && invoiceStatus.trim().length()>0){
					filterVO.setSettlementStatus(invoiceStatus.trim().toUpperCase());
				}    	
				if(gpaName != null && gpaName.trim().length()>0){
					filterVO.setGpaName(gpaName);
				}
				if(chequeNo != null && chequeNo.trim().length()>0){
					filterVO.setChequeNumber(chequeNo);	

				}
				log.log(log.INFO,"filterVO "+filterVO);
				
				
				if(!AVAIL_POPUP.equals(form.getScreenStatus())){
					errors=validateForm(form);
					if(errors!=null && errors.size()>0){
						log.log(Log.FINE,"!!!inside errors!= null");
						invocationContext.addAllError(errors);
						session.removeInvoiceSettlementVOs();
						form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
						invocationContext.target=SCREENLOAD_FAILURE;
						return;
					}	
				}
				session.setInvoiceSettlementFilterVO(filterVO);
				
			}
			else{
				
				filterVO=session.getInvoiceSettlementFilterVO();
				filterVO.setSettlementReferenceNumber(null);
			}
			
			



			//find Settlement details for a PA

			try{
				gpaSettlementVOs=new MailTrackingMRADelegate().findSettlementDetails(filterVO ,Integer.parseInt(form.getDisplayPage()));
			}
			catch (BusinessDelegateException businessDelegateException) {
				errors=handleDelegateException(businessDelegateException);

			}
			if(gpaSettlementVOs!=null && gpaSettlementVOs.size()>0){
				
				if (gpaSettlementVOs.iterator().next().getSettlementDetailsVOs()!=null && 
						gpaSettlementVOs.iterator().next().getSettlementDetailsVOs().size()>0){
					form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
					
					session.setGPASettlementVOs(gpaSettlementVOs);					
					form.setScreenStatus(AVAIL_POPUP);					
				}
				//if no settlement exists for the PA ,all unsettled invoices will be listed
				else{
					//For finding the unsettled invoices for a GPA while listing
					Collection <GPASettlementVO> unsettledInvoices=new ArrayList<GPASettlementVO>();
					
					gpaSettlementFilterVO=populateGPASettlementVO(form, session,logonAttributes);
					if(filterVO.getInvoiceNumber()==null && (filterVO.getSettlementStatus()==null || "F".equals(filterVO.getSettlementStatus()) || "D".equals(filterVO.getSettlementStatus())) && 
								filterVO.getFromDate()==null && filterVO.getToDate()==null){
						try{
							gpaSettlementVOs=new MailTrackingMRADelegate().findUnSettledInvoicesForGPAForSettlementCapture(gpaSettlementFilterVO);
						}
						catch (BusinessDelegateException businessDelegateException) {
							errors=handleDelegateException(businessDelegateException);
						}
					}
					LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
					form.setSettlementDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
					if(unsettledInvoices!=null && unsettledInvoices.size()>0){
						//Unsettled Invoices needs to be set only if there are no associated invoices for that settlement
//						if(gpaSettlementVOs.iterator().next().getInvoiceSettlementVOs()== null || 
//								gpaSettlementVOs.iterator().next().getInvoiceSettlementVOs().size()<=0){
							gpaSettlementVOs.iterator().next().setInvoiceSettlementVOs
							(unsettledInvoices.iterator().next().getInvoiceSettlementVOs());

//						}

					}
					/*
					 * String settlementCurrency="";
					 * try {
						settlementCurrency= new MailTrackingMRADelegate().findSettlementCurrency(logonAttributes.getCompanyCode(), form.getGpaCodeFilter());
					} catch (BusinessDelegateException e) {
						errors=handleDelegateException(e);					
					}
					if(settlementCurrency ==null || settlementCurrency.trim().length()<=0){
						errorVO=new ErrorVO("mailtracking.mra.gpabilling.msg.err.settlementcurrencymandatory");
						errors.add(errorVO);					
					}
					form.setSettleCurrency(settlementCurrency);
					 */
					session.setGPASettlementVO(gpaSettlementVOs);
					session.setInvoiceSettlementDetailVOs(gpaSettlementVOs.iterator().next().getInvoiceSettlementVOsPage());
					form.setScreenStatus(SCREENSTATUS_SCREENLOAD);
					form.setCheckFlag("ENABLE");
					form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
					ErrorVO errorVOs = new ErrorVO(
							SETTLEMENT_NOTEXIST);
						
						
			   			errorVOs.setErrorDisplayType(ErrorDisplayType.WARNING);
			   			errors = new ArrayList<ErrorVO>();
			   			errors.add(errorVOs);
					//errorVOs.setErrorDisplayType(ErrorDisplayType.WARNING);
			         // errors.add(errorVO);
					//errorVOs=new ErrorVO(SETTLEMENT_NOTEXIST);
					//errors.add(errorVO);
				}
			}
			else{
				Page<GPASettlementVO> gpaSettlemntVOsToList = new Page<GPASettlementVO>(new ArrayList<GPASettlementVO>(),0,0,0,0,0,false);
				form.setScreenStatus(SCREENSTATUS_SCREENLOAD);
				Collection <GPASettlementVO> newInvoices=new ArrayList<GPASettlementVO>();
				int recordCount=0;
				//if( "F".equals(filterVO.getSettlementStatus()) || "D".equals(filterVO.getSettlementStatus())){
				
					gpaSettlementFilterVO=populateGPASettlementVO(form, session,logonAttributes);
					try{
						//newInvoices=new MailTrackingMRADelegate().findUnSettledInvoicesForGPA(gpaSettlementFilterVO);
						gpaSettlemntVOsToList=new MailTrackingMRADelegate().findUnSettledInvoicesForGPAForSettlementCapture(gpaSettlementFilterVO);
					}
					catch (BusinessDelegateException businessDelegateException) {
						errors=handleDelegateException(businessDelegateException);
					}
					if(newInvoices.size() > 0){    
					GPASettlementVO gPASettlementVO=new GPASettlementVO();
					Collection<InvoiceSettlementVO> invoiceSettlementVOs = newInvoices.iterator().next().getInvoiceSettlementVOs();
					Collection<InvoiceSettlementVO> invoiceSettlementVOFinalised = new ArrayList<InvoiceSettlementVO>();
					Collection<InvoiceSettlementVO> invoiceSettlementVODiffernce = new ArrayList<InvoiceSettlementVO>();
					Collection <GPASettlementVO> newInvoicestoDisplay=new ArrayList<GPASettlementVO>();
					if(invoiceSettlementVOs!=null && invoiceSettlementVOs.size()>0){
						if("F".equals(form.getInvoiceStatusFilter())){
							for(InvoiceSettlementVO invoiceSettlementVO:invoiceSettlementVOs){
								if("F".equals(invoiceSettlementVO.getSettlementStatus())){
									invoiceSettlementVOFinalised.add(invoiceSettlementVO);
								}
							}
							gPASettlementVO.setInvoiceSettlementVOs(invoiceSettlementVOFinalised);
							newInvoicestoDisplay.add(gPASettlementVO);
						}else if("D".equals(form.getInvoiceStatusFilter())){
							for(InvoiceSettlementVO invoiceSettlementVO:invoiceSettlementVOs){
								if("D".equals(invoiceSettlementVO.getSettlementStatus())){
									recordCount++;
									invoiceSettlementVODiffernce.add(invoiceSettlementVO);
								}
							}
							gPASettlementVO.setInvoiceSettlementVOs(invoiceSettlementVODiffernce);
							newInvoicestoDisplay.add(gPASettlementVO);
						}
						else{
							newInvoicestoDisplay.addAll(newInvoices); 
						}
					}
					//gpaSettlemntVOsToList.addAll(newInvoicestoDisplay);
						session.setGPASettlementVO(gpaSettlemntVOsToList);	
						session.setInvoiceSettlementDetailVOs(gpaSettlemntVOsToList.iterator().next().getInvoiceSettlementVOsPage());
						LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
						form.setSettlementDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
					}
					else{
				errorVO=new ErrorVO(NO_DATA);
				errors.add(errorVO); 
				LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
						form.setSettlementDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
					}
					if("D".equals(form.getInvoiceStatusFilter()) && recordCount==0){
						errorVO=new ErrorVO(NO_DATA);      
						errors.add(errorVO); 
						LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
								form.setSettlementDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
					}
					form.setCheckFlag("ENABLE");
					form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
				//}
				/*	else{  
					 LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
						form.setSettlementDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
					form.setCheckFlag("ENABLE");
					errorVO=new ErrorVO(NO_DATA);
					form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
					errors.add(errorVO);
				}*/
			}
		
			

			
		
			
		}

		if(errors!=null && errors.size()>0){
			log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
			session.removeInvoiceSettlementVOs();

			invocationContext.target=SCREENLOAD_FAILURE;
			return;
		}
		invocationContext.target = SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	/**
	 * 
	 * @param session 
	 * @return gpaSettlementVO
	 */
	private GPASettlementVO populateGPASettlementVO(InvoiceSettlementForm form ,InvoiceSettlementSession session, LogonAttributes logonAttributes) {
		GPASettlementVO gpaSettlementVO=new GPASettlementVO();

		gpaSettlementVO.setCompanyCode(logonAttributes.getCompanyCode());
		InvoiceSettlementVO invoiceSettlementVO = new InvoiceSettlementVO();
		Collection<InvoiceSettlementVO> invoiceSettlementVOs = new ArrayList<InvoiceSettlementVO>();
		String invNumber = form.getInvRefNumberFilter();
		//added by a-5133 as part of ICRD-23808 starts
		String fromDate=form.getFromDate();
		String toDate=form.getToDate();
		if(fromDate!=null && fromDate.trim().length()>0){
			gpaSettlementVO.setFromDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(fromDate));
		}
		if(toDate!=null && toDate.trim().length()>0){
			gpaSettlementVO.setToDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(toDate));

		}
		//added by a-5133 as part of ICRD-23808 ends
		if (LIST_ACCOUNTING.equals(form.getFrmPopUp()) || INVOICE_DETAIL.equals(form.getFrmPopUp())) {
			if(session.getInvoiceSettlementFilterVO()!=null && session.getInvoiceSettlementFilterVO().getInvoiceNumber()!=null){
				invNumber=session.getInvoiceSettlementFilterVO().getInvoiceNumber();
				invoiceSettlementVO.setInvoiceNumber(invNumber);
			}
		}
		else if(invNumber !=null && invNumber.trim().length()>0){
			invoiceSettlementVO.setInvoiceNumber(invNumber);
		}
		invoiceSettlementVOs.add(invoiceSettlementVO);
		if(session.getInvoiceSettlementFilterVO()!=null){
			gpaSettlementVO.setGpaCode(session.getInvoiceSettlementFilterVO().getGpaCode());
		}
		else{
			gpaSettlementVO.setGpaCode(form.getGpaCodeFilter());
		}
		gpaSettlementVO.setInvoiceSettlementVOs(invoiceSettlementVOs); 
		gpaSettlementVO.setDisplayPage(Integer.parseInt(form.getDisplayPage()));
		return gpaSettlementVO;
	}

	/**
	 * 
	 * @param form
	 * @return
	 */
	private Collection<ErrorVO> validateForm(InvoiceSettlementForm form) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(form.getGpaCodeFilter()==null||form.getGpaCodeFilter().trim().length()<=0) {
			errors.add(new ErrorVO("mailtracking.mra.gpabilling.msg.err.gpanamemandatory"));
		}
		/* Commented By T-1925 for ICRD-9704
		if((form.getFromDate()!=null && form.getFromDate().trim().length()>0) && 
				(form.getToDate()!=null && form.getToDate().trim().length()>0)){
			if(DateUtilities.getDifferenceInMonths(form.getFromDate(),form.getToDate())> 6){
				errors.add(new ErrorVO(DATERANGEEXCEEDS));
			}
			
		}*/
		return errors;
	}
	/**
	 * Method to validate GpaCode
	 * @param GenerateGPABillingInvoiceForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateGpaCode(InvoiceSettlementForm form,
			LogonAttributes logonAttributes) {

		String gpaCode=form.getGpaCodeFilter();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(gpaCode != null || ("".equals(gpaCode.trim()))){

			//    	validate PA code

			try {
				PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
				postalAdministrationVO  = new MailTrackingMRADelegate().findPACode(
						logonAttributes.getCompanyCode(),gpaCode.toUpperCase());

				if(postalAdministrationVO == null) {
					Object[] obj = {gpaCode.toUpperCase()};
					errors.add(new ErrorVO("mailtracking.mra.gpabilling.gpacode.invalid",obj));
				}
				else{
					HashMap<String,Collection<PostalAdministrationDetailsVO> >postalAdministrationDetailsVOs=postalAdministrationVO.getPostalAdministrationDetailsVOs();
					if(postalAdministrationDetailsVOs!= null && postalAdministrationDetailsVOs.size()>0){

						Collection<PostalAdministrationDetailsVO> paDetails=postalAdministrationDetailsVOs.get("STLINFO");
						if(paDetails!=null && paDetails.size()>0 ){
							form.setSettleCurrency(paDetails.iterator().next().getSettlementCurrencyCode() );
							form.setGpaNameFilter(postalAdministrationVO.getPaName());
						
						}
						else{							
							errors.add(new ErrorVO(("mailtracking.mra.gpabilling.msg.err.settlementcurrencymandatory")));
						}

					}
				}

			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		return errors;
	}


}