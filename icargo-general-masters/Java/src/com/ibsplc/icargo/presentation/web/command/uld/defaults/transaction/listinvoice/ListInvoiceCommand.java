/*
 * ListInvoiceCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listinvoice;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ChargingInvoiceFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDChargingInvoiceVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-1496
 *
 */
public class ListInvoiceCommand  extends BaseCommand {
	private Log log = LogFactory.getLogger("List Invoice ULD");
	
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.listinvoice";
	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_failure";
    
	private static final String LIST_STATUS = "fromrepair";
    private static final String SELECT_ALL = "ALL"; 
    	

    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ListInvoiceSession listInvoiceSession = (ListInvoiceSession)getScreenSession(MODULE,SCREENID);
		
		ListInvoiceForm listInvoiceForm = (ListInvoiceForm) invocationContext.screenModel;
		ChargingInvoiceFilterVO chargingInvoiceFilterVO = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(!LIST_STATUS.equals(listInvoiceSession.getListStatus())) {
			chargingInvoiceFilterVO = new ChargingInvoiceFilterVO();
			errors = loadInvoiceFilterFromForm(listInvoiceForm,chargingInvoiceFilterVO);
			listInvoiceSession.setChargingInvoiceFilterVO(chargingInvoiceFilterVO);
			if(errors != null &&
					errors.size() > 0 ) {
					invocationContext.addAllError(errors);
					invocationContext.target = LIST_FAILURE;
					return;
			}else{
				//Added by A-5214 as part from the ICRD-20959 starts
				if(!"YES".equals(listInvoiceForm.getCountTotalFlag()) && listInvoiceSession.getTotalRecords().intValue() != 0){
					chargingInvoiceFilterVO.setTotalRecords(listInvoiceSession.getTotalRecords().intValue());
			    }else{
			    	chargingInvoiceFilterVO.setTotalRecords(-1);
			    }
				//Added by A-5214 as part from the ICRD-20959 end
			}
			
		}
		else {
			chargingInvoiceFilterVO = listInvoiceSession.getChargingInvoiceFilterVO();
			listInvoiceSession.setListStatus("");
			//listInvoiceForm.setDisplayPage("1");
			//listInvoiceForm.setLastPageNum("0");
		}
		chargingInvoiceFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		Page<ULDChargingInvoiceVO> uldChargingInvoiceVOs = new Page<ULDChargingInvoiceVO>(
				new ArrayList<ULDChargingInvoiceVO>(), 0, 0, 0, 0, 0, false);
		
		try {
			uldChargingInvoiceVOs = new ULDDefaultsDelegate().listULDChargingInvoice(
					chargingInvoiceFilterVO,Integer.parseInt(listInvoiceForm.getDisplayPage())) ;
		}
		catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		if(errors != null &&
				errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;
		}
		if(uldChargingInvoiceVOs != null && uldChargingInvoiceVOs.size()> 0) {
			listInvoiceSession.setULDChargingInvoiceVO(uldChargingInvoiceVOs);
			listInvoiceSession.setTotalRecords(uldChargingInvoiceVOs.getTotalRecordCount());
		}
		else {
			listInvoiceSession.setULDChargingInvoiceVO(null);
			ErrorVO error = new ErrorVO("uld.defaults.listuld.norecordsfound");
	     	error.setErrorDisplayType(ErrorDisplayType.ERROR);
	     	errors = new ArrayList<ErrorVO>();
	     	errors.add(error);
	     	invocationContext.addAllError(errors);
		}
		listInvoiceForm.setScreenStatusFlag(
				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		
		invocationContext.target = LIST_SUCCESS;
    }
    
    private Collection<ErrorVO> loadInvoiceFilterFromForm(ListInvoiceForm listInvoiceForm,
    		ChargingInvoiceFilterVO chargingInvoiceFilterVO) {
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	
    	if((listInvoiceForm.getInvoiceRefNumber() != null &&
    			listInvoiceForm.getInvoiceRefNumber().trim().length() > 0)) {
    		chargingInvoiceFilterVO.setInvoiceRefNumber(listInvoiceForm.getInvoiceRefNumber().toUpperCase());
    		
    	}
    	
    	if((listInvoiceForm.getTransactionType() != null &&
				listInvoiceForm.getTransactionType().trim().length() > 0)) {
    		chargingInvoiceFilterVO.setTransactionType(listInvoiceForm.getTransactionType().toUpperCase());
    		
    	}
    	
    	if((listInvoiceForm.getInvoicedToCode() != null &&
				listInvoiceForm.getInvoicedToCode().trim().length() > 0)) {
    		chargingInvoiceFilterVO.setInvoicedToCode(listInvoiceForm.getInvoicedToCode().toUpperCase());
    	
    	}
    	    	
    	if((listInvoiceForm.getInvoicedDateFrom() != null &&
				listInvoiceForm.getInvoicedDateFrom().trim().length() > 0)) {
    		LocalDate invoiceDateFrom = null;
			if(DateUtilities.isValidDate(listInvoiceForm.getInvoicedDateFrom(),"dd-MMM-yyyy")) {
				invoiceDateFrom = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
				invoiceDateFrom.setDate(listInvoiceForm.getInvoicedDateFrom());
				
			}
			else {
				ErrorVO error = new ErrorVO("uld.defaults.listinvoice.invaliddate",
						new Object[]{listInvoiceForm.getInvoicedDateFrom()});
				errors.add(error);
			}
			chargingInvoiceFilterVO.setInvoicedDateFrom(invoiceDateFrom);
    		    	
    	}
    	
    	if((listInvoiceForm.getInvoicedDateTo() != null &&
				listInvoiceForm.getInvoicedDateTo().trim().length() > 0)) {
    		LocalDate invoiceDateTo = null;
			if(DateUtilities.isValidDate(listInvoiceForm.getInvoicedDateTo(),"dd-MMM-yyyy")) {
				invoiceDateTo = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
				invoiceDateTo.setDate(listInvoiceForm.getInvoicedDateTo());
				
			}
			else {
				ErrorVO error = new ErrorVO("uld.defaults.listinvoice.invaliddate",
						new Object[]{listInvoiceForm.getInvoicedDateTo()});
				errors.add(error);
			}
			chargingInvoiceFilterVO.setInvoicedDateTo(invoiceDateTo);
    		
    	}
    	
    	//	Modified By Tarun INT_ULD410
    	if((listInvoiceForm.getPartyType() != null &&
				listInvoiceForm.getPartyType().trim().length() > 0)) {
    		if(SELECT_ALL.equals(listInvoiceForm.getPartyType())){
    			chargingInvoiceFilterVO.setPartyType(null);
    		}else{
    			chargingInvoiceFilterVO.setPartyType(listInvoiceForm.getPartyType().toUpperCase());
    		}
    		
    	log.log(Log.FINE, "partytype", chargingInvoiceFilterVO.getPartyType());
    	}
    	//Added by nisha for bugfix on 30Jul08 starts
    	if (chargingInvoiceFilterVO.getInvoicedDateFrom() != null
				&& chargingInvoiceFilterVO.getInvoicedDateTo() != null) {
			if (chargingInvoiceFilterVO.getInvoicedDateFrom().isGreaterThan(
					chargingInvoiceFilterVO.getInvoicedDateTo())) {
				ErrorVO error = new ErrorVO(
						"uld.defaults.listinvoice.fromdatecannotbegreaterthantodate",
						new Object[] { listInvoiceForm.getInvoicedDateTo() });
				errors.add(error);
			}
		}
    	//ends
    	return errors;
    	
    	
   		
	}


}
