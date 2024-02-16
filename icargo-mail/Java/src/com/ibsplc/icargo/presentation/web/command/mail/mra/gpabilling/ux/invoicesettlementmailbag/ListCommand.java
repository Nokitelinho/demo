/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.ListCommand.java
 *
 *	Created by	:	A-7531
 *	Created on	:	24-Apr-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceLovVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SettlementDetailsVO;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ux.InvoiceSettlementMailbagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.InvoiceSettlementMailbagForm;

import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.ListCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	24-Apr-2018	:	Draft
 */
public class ListCommand extends BaseCommand {
	
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREEN_ID ="mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel";

	private static final String LIST_SUCCESS ="list_success";

	private static final String LST_FAILURE="list_failure";

	private static final String CLASS_NAME = "ListCommand";
	private static final String INVOICENUMBER_MANDATORY = "mailtracking.mra.gpabilling.msg.err.invoicenumbermandatory";
	private static final String NO_DATA="mailtracking.mra.gpabilling.msg.err.noresultsfound";
	 private static final String KEY_PAYMENT_STATUS_ONETIME =  "mailtracking.mra.gpabilling.paymentstatus";
	 private static final String KEY_CHEQUE_DETAILS =  "mailtracking.mra.gpabilling.ChequeDetailsMandatory";
	 private static final String NO_UNSETTLED_MAILBAG = "mailtracking.mra.gpabilling.msg.err.allmailbagssettled";
	 private static final String PROFORMA_INVOICE_DETAILS = "mailtracking.mra.gpabilling.msg.err.proforma";
	
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException{
		
		Log log = LogFactory.getLogger("MRA_GPABILLING");
		log.entering(CLASS_NAME, "execute");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		ErrorVO errorVO=null;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		InvoiceSettlementMailbagSession session=(InvoiceSettlementMailbagSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		InvoiceSettlementMailbagForm form=(InvoiceSettlementMailbagForm)invocationContext.screenModel;
		Collection<GPASettlementVO> gpaSettlementVOs=null; 
		
		InvoiceSettlementFilterVO filterVO=new InvoiceSettlementFilterVO();
		
		
		
		String displayPage = form.getDisplayPage();
	    int displayPageCount = Integer.parseInt(displayPage);
	    String defaultSize =form.getDefaultPageSize();
	    int defaultPageSize = Integer.parseInt(defaultSize);
	    
		if("LIST_SETTLE".equals(form.getActionFlag())){
			errorVO=new ErrorVO(NO_DATA);
			form.setFlagFilter("Y");
			
		}else if("LIST_UNSETTLE".equals(form.getActionFlag()) ){
			form.setFlagFilter("N");
			errors.add(errorVO);
		}	else{
	
		
		if("CREATE".equals(form.getActionFlag())){
InvoiceSettlementFilterVO filter=session.getInvoiceSettlementFilterVO();

filter.setPageNumber(displayPageCount);
filter.setDefaultPageSize(defaultPageSize);
filter.setTotalRecords(-1);
			try {
				gpaSettlementVOs=new MailTrackingMRADelegate().findUnsettledMailbags(filter);
				} catch (BusinessDelegateException e) {
					
					log.log(Log.SEVERE, "execption",e.getMessage());
				}
			if(gpaSettlementVOs.iterator().next().getInvoiceSettlementVO()!=null){
			session.setTotalRecords(gpaSettlementVOs.iterator().next().getInvoiceSettlementVO().size());
			}
			else{
				errors.add(new ErrorVO(NO_UNSETTLED_MAILBAG));
				form.setPopupFlag(false);
				if(errors != null && errors.size() > 0 ){
					log.log(Log.INFO," ######### LST_FAILURE ####");
					invocationContext.addAllError(errors);
				
					invocationContext.target = LST_FAILURE;
					return;
				}
			}
			session.setSelectedGPASettlementVO(gpaSettlementVOs);
			//filter=populateFilter(gpaSettlementVOs);
			session.setInvoiceSettlementFilterVO(filter);
			form.setPopupFlag(false);
		}else if("OK".equals(form.getActionFlag())){
			
			InvoiceSettlementFilterVO filter=session.getInvoiceSettlementFilterVO();
			filter.setPageNumber(displayPageCount);
			filter.setDefaultPageSize(defaultPageSize);
			filter.setTotalRecords(-1);
			try {
				gpaSettlementVOs=new MailTrackingMRADelegate().findSettledMailbags(filter);
			} catch (BusinessDelegateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	    	session.setSelectedGPASettlementVO(gpaSettlementVOs);
	    	if(gpaSettlementVOs.iterator().next().getInvoiceSettlementVO()!=null ||gpaSettlementVOs.iterator().next().getInvoiceSettlementVO().size()>0){
	    	session.setTotalRecords(gpaSettlementVOs.iterator().next().getInvoiceSettlementVO().size());
	    	}
	    	//filter=populateFilter(gpaSettlementVOs);
			session.setSelectedGPASettlementVO(gpaSettlementVOs);
			session.setInvoiceSettlementFilterVO(filter);
			form.setPopupFlag(false);
		}
		else if("SHOWOK".equals(form.getActionFlag())){
			InvoiceSettlementFilterVO filter=session.getInvoiceSettlementFilterVO();
			filter.setPageNumber(displayPageCount);
			filter.setDefaultPageSize(defaultPageSize);
			filterVO.setTotalRecords(session.getTotalRecords());
			try {
				gpaSettlementVOs=new MailTrackingMRADelegate().findSettledMailbags(filter);
			} catch (BusinessDelegateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	    	session.setSelectedGPASettlementVO(gpaSettlementVOs);
	    	if(gpaSettlementVOs.iterator().next().getInvoiceSettlementVO()!=null||gpaSettlementVOs.iterator().next().getInvoiceSettlementVO().size()>0){
	    	session.setTotalRecords(gpaSettlementVOs.iterator().next().getInvoiceSettlementVO().size());
	    	}
	    	//filter=populateFilter(gpaSettlementVOs);
			session.setSelectedGPASettlementVO(gpaSettlementVOs);
			//session.setInvoiceSettlementFilterVO(filter);
			form.setPopupFlag(false);
			form.setActionFlag("SHOWOK");
			
		}else if("SHOWCREATRE".equals(form.getActionFlag())){
			InvoiceSettlementFilterVO filter=session.getInvoiceSettlementFilterVO();
			filter.setPageNumber(displayPageCount);
			filter.setDefaultPageSize(defaultPageSize);
			filterVO.setTotalRecords(session.getTotalRecords());
			try {
				gpaSettlementVOs=new MailTrackingMRADelegate().findUnsettledMailbags(filter);
			} catch (BusinessDelegateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	    	session.setSelectedGPASettlementVO(gpaSettlementVOs);
	    	if(gpaSettlementVOs.iterator().next().getInvoiceSettlementVO()!=null||gpaSettlementVOs.iterator().next().getInvoiceSettlementVO().size()>0){
	    	session.setTotalRecords(gpaSettlementVOs.iterator().next().getInvoiceSettlementVO().size());
	    	}
	    	//filter=populateFilter(gpaSettlementVOs);
			session.setSelectedGPASettlementVO(gpaSettlementVOs);
			//session.setInvoiceSettlementFilterVO(filter);
			form.setPopupFlag(false);
			form.setActionFlag("SHOWCREATRE");
			
		}
		
		else{
			String invNumber = form.getInvoiceNumber();
			
			errors= validateForm(form);
			
			if(errors != null && errors.size() > 0 ){
				log.log(Log.INFO," ######### LST_FAILURE ####");
				invocationContext.addAllError(errors);
			
				invocationContext.target = LST_FAILURE;
				return;
			}
			
			filterVO.setCompanyCode(companyCode);
			if(invNumber!=null && invNumber.trim().length()>0){
			filterVO.setInvoiceNumber(invNumber.trim().toUpperCase());
			}
			filterVO.setPageNumber(displayPageCount);
			filterVO.setDefaultPageSize(defaultPageSize);
			/*if("SHOWOK".equals(form.getActionFlag())){
				filterVO.setTotalRecords(session.getTotalRecords());
			}else{*/
			filterVO.setTotalRecords(-1);
			//}
		if(filterVO!=null){
		try {
			gpaSettlementVOs=new MailTrackingMRADelegate().findSettledMailbags(filterVO);
		} catch (BusinessDelegateException e) {
			
			log.log(Log.SEVERE, "execption",e.getMessage());
		}
		Collection<SettlementDetailsVO> availableSettlementVOs= new ArrayList<SettlementDetailsVO>() ;
		SettlementDetailsVO settlementDetailsVO=null;
		if(gpaSettlementVOs.iterator().next().getSettlementDetailsVOs()!=null)
		{
			
			for(SettlementDetailsVO settlDetailsVO:gpaSettlementVOs.iterator().next().getSettlementDetailsVOs())
			{
				
				if(settlementDetailsVO!=null && settlDetailsVO.getSettlementId().equals(settlementDetailsVO.getSettlementId()))
					settlementDetailsVO.setChequeAmount(settlementDetailsVO.getChequeAmount().plusEquals(settlDetailsVO.getChequeAmount()));
				else
				{
				 settlementDetailsVO=new SettlementDetailsVO();
				settlementDetailsVO=settlDetailsVO;
				availableSettlementVOs.add(settlementDetailsVO);
				}
				
			}
			
		}
		gpaSettlementVOs.iterator().next().setSettlementDetailsVOs(availableSettlementVOs);
		Map<String, InvoiceSettlementVO> invoiceDetailsMap =  new HashMap<String, InvoiceSettlementVO>();
		String key="";
		Page<InvoiceSettlementVO> invoiceSettlementVOs = new Page<InvoiceSettlementVO>(new ArrayList<InvoiceSettlementVO>(),0,0,0,0,0,false);
		
	if(gpaSettlementVOs.iterator().next().getInvoiceSettlementVO()!=null && gpaSettlementVOs.iterator().next().getInvoiceSettlementVO().size()>0) {
		if(gpaSettlementVOs.iterator().next().getInvoiceSettlementVO() != null && gpaSettlementVOs.iterator().next().getInvoiceSettlementVO().size() > 0) {
		
		for(InvoiceSettlementVO invoiceVO:gpaSettlementVOs.iterator().next().getInvoiceSettlementVO()){
	    key = new StringBuilder().append(invoiceVO.getMailbagID()).toString();
	    if(!invoiceDetailsMap.containsKey(key)){
	    invoiceSettlementVOs.add(invoiceVO)	;		
	    gpaSettlementVOs.iterator().next().setInvoiceSettlementVO(invoiceSettlementVOs);
	    invoiceDetailsMap.put(key, invoiceVO);
	    }
		}
		}
		session.setSelectedGPASettlementVO(gpaSettlementVOs);
		/*if("SHOWCREATRE".equals(form.getActionFlag())){
			form.setPopupFlag(false);
			form.setActionFlag("SHOWCREATRE");
			}else{*/
		form.setPopupFlag(true);
		form.setActionFlag("LIST_SETTLE");
			//}
		form.setFlagFilter("Y");
		if(gpaSettlementVOs.iterator().next().getInvoiceSettlementVO()!=null && gpaSettlementVOs.iterator().next().getInvoiceSettlementVO().size()>0){
		session.setTotalRecords(gpaSettlementVOs.iterator().next().getInvoiceSettlementVO().size());
		}
	}
		
	else{
		try {
		gpaSettlementVOs=new MailTrackingMRADelegate().findUnsettledMailbags(filterVO);
		} catch (BusinessDelegateException e) {
			
			log.log(Log.SEVERE, "execption",e.getMessage());
		}
		
		if(gpaSettlementVOs!=null && gpaSettlementVOs.size()>0) {
			
			if(gpaSettlementVOs.iterator().next().getInvoiceSettlementVO() != null && gpaSettlementVOs.iterator().next().getInvoiceSettlementVO().size() > 0) {
			
			for(InvoiceSettlementVO invoiceVO:gpaSettlementVOs.iterator().next().getInvoiceSettlementVO()){
		    key = new StringBuilder().append(invoiceVO.getMailbagID()).toString();
		    if(!invoiceDetailsMap.containsKey(key)){
		    invoiceSettlementVOs.add(invoiceVO)	;		
		    gpaSettlementVOs.iterator().next().setInvoiceSettlementVO(invoiceSettlementVOs);
		    invoiceDetailsMap.put(key, invoiceVO);
		    }
		    form.setCreateButtonFlag(false);
			}
			}
			session.setSelectedGPASettlementVO(gpaSettlementVOs);
			/*if("SHOW".equals(form.getActionFlag())){
				form.setPopupFlag(false);
				form.setActionFlag("SHOW");
				}else{*/
					form.setPopupFlag(true);
					form.setActionFlag("LIST_UNSETTLE");
				//}
			
			form.setFlagFilter("N");
			if(gpaSettlementVOs.iterator().next().getInvoiceSettlementVO()!=null && gpaSettlementVOs.iterator().next().getInvoiceSettlementVO().size()>0){
			
			session.setTotalRecords(gpaSettlementVOs.iterator().next().getInvoiceSettlementVO().size());
			}else{
				form.setCreateButtonFlag(true);
				errors.add(new ErrorVO(NO_UNSETTLED_MAILBAG));
				if(errors != null && errors.size() > 0 ){
					log.log(Log.INFO," ######### LST_FAILURE ####");
					invocationContext.addAllError(errors);
				
					invocationContext.target = LST_FAILURE;
					return;
				}
			}
		}
	}
	for(GPASettlementVO gpaSettlementVO:gpaSettlementVOs){
	filterVO.setGpaCode(gpaSettlementVO.getGpaCode());
	//filterVO.setSettlementCurrency(gpaSettlementVO.getSettlementCurrency());
	if(gpaSettlementVO.getInvoiceSettlementVO()!=null){
	for(InvoiceSettlementVO invoiceVO:gpaSettlementVO.getInvoiceSettlementVO()){
		filterVO.setSettlementReferenceNumber(invoiceVO.getSettlementId());
		if(invoiceVO.getSettlementDate()!=null){
		filterVO.setSettlementDate(invoiceVO.getSettlementDate().toDisplayFormat());
		}
		if(filterVO.getGpaCode() == null && invoiceVO.getGpaCode() != null)
			filterVO.setGpaCode(invoiceVO.getGpaCode());
		filterVO.setGpaName(invoiceVO.getGpaName());
		filterVO.setSettlementStatus(invoiceVO.getInvoiceStatus());
		filterVO.setBillingPeriod((invoiceVO.getBillingPeriod()));
		filterVO.setSettlementCurrency(invoiceVO.getSettlementCurrencyCode());
		String[] date=invoiceVO.getBillingPeriod().split("to");
		//String[] todate=;
		filterVO.setBillingPeriodfrom(date[0]);
		filterVO.setBillingPeriodto(date[1]);
		filterVO.setTotalBlgamt(String.valueOf(invoiceVO.getTotalBlgamt().getAmount()));
		filterVO.setTotalSettledAmt(String.valueOf(invoiceVO.getTotalSettledamt().getAmount()));
		filterVO.setMailbagID(invoiceVO.getMailbagID());
		filterVO.setMailsequenceNum(invoiceVO.getMailsequenceNum());
		
		
	}
	}
	
	}
	session.setInvoiceSettlementFilterVO(filterVO);
	
	}
		}	
		}
		if(!"N".equals(session.getSystemparametres().get(KEY_CHEQUE_DETAILS)) )
		{
			form.setChequeFlag(true);
		}
		else{
			form.setChequeFlag(false);
			}
		invocationContext.target = LIST_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	
	}

	private Collection<ErrorVO> validateForm(InvoiceSettlementMailbagForm form) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		String displayPage = form.getDisplayPage();
	    int displayPageCount = Integer.parseInt(displayPage);
		if(form.getInvoiceNumber()==null||form.getInvoiceNumber().trim().length()<=0) {
			errors.add(new ErrorVO(INVOICENUMBER_MANDATORY));
		}else{
		InvoiceLovVO invoiceLovVo=null;
		InvoiceLovVO invoiceLovVO=new InvoiceLovVO();
		invoiceLovVO.setCompanyCode(companyCode);
		invoiceLovVO.setInvoiceNumber(form.getInvoiceNumber());
		invoiceLovVO.setPageNumber(displayPageCount);
		MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
		try{
			invoiceLovVo=delegate.findInvoiceNumber(invoiceLovVO);
		}
		catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			
		}
		if(invoiceLovVo==null){
			
			errors.add(new ErrorVO(NO_DATA));
		}
		else if(invoiceLovVo!=null && "P".equals(invoiceLovVo.getInvoiceStatus())){
					
					errors.add(new ErrorVO(PROFORMA_INVOICE_DETAILS));
		}
	
	}
		return errors;
	}

}
