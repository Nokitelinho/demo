/*
 * ListDamageRepairDetailsCommand.java Created on  10 June,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * 
 * Use is subject to license terms.
 * 
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldmovementhistory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.UldDmgRprFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author a-3093
 * 
 * This command class is for listing of damage and repair details of ULDs
 * 
 */
public class ListDamageRepairDetailsCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ListDamageReportCommand");

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String SCREENID_DAMAGE = "uld.defaults.maintaindamagereport";
	
	private static final String MODULE_NAME = "uld.defaults";
	  private static final String SYSPAR_BASE="system.defaults.unit.currency";
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		ULDMovementFilterVO uldMovementFilterVO = new ULDMovementFilterVO();
		ListULDMovementForm form = (ListULDMovementForm) invocationContext.screenModel;
		MaintainDamageReportSession session = getScreenSession(MODULE_NAME,
				SCREENID_DAMAGE);
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		UldDmgRprFilterVO filterVO = new UldDmgRprFilterVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ULDValidationVO uldValidationVO = new ULDValidationVO();
		filterVO.setCompanyCode(companyCode);
		filterVO.setUldNumber(form.getUldNumber().toUpperCase());
		
		//errors = validateForm(form,session,companyCode);
		updateFilterVO(form, uldMovementFilterVO,companyCode);
		updateDateRangeInDamageFilter(uldMovementFilterVO, filterVO);
		log.log(Log.INFO, "filterVO returned ---->", filterVO);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			session.setULDDamageRepairDetailsVOs(null);
			invocationContext.target =LIST_FAILURE;
			return;
		}
		
		Collection<String> systemparameterCodes = new  ArrayList<String>();
		Map<String,String> map = new HashMap<String,String>();
		systemparameterCodes.add(SYSPAR_BASE);
		
		/**
		 * Implementing Exchange Rates according to CRINT1122
		 */	
		
		SharedDefaultsDelegate sharedDefaultsDelegate = 
			new SharedDefaultsDelegate();
		/*
		 * Getting system parameters for Exchange Rates and
		 * base Currency
		 */
		try {
			map = sharedDefaultsDelegate.findSystemParameterByCodes(systemparameterCodes);
		}catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
			businessDelegateException.getMessage();
			
		}
		String sysparBaseCurrency = map.get(SYSPAR_BASE);
		if(sysparBaseCurrency!=null)
		{
			form.setSystemCurrency(sysparBaseCurrency);
		}
		session.setUldDmgRprFilterVO(filterVO);
		Page<ULDRepairDetailsListVO> repairDetailVOs = new Page<ULDRepairDetailsListVO>(
				new ArrayList<ULDRepairDetailsListVO>(), 0, 0, 0, 0, 0, false);

		String displayPage = null;

		displayPage = form.getDisplayPage();
		if ("1".equals(form.getDisplayPageFlag())) {
			displayPage = "1";
			form.setDisplayPageFlag("");
		}
		log.log(Log.INFO, "form.getDisplayPage()--------- ", form.getDisplayPage());
		int pageNumber = Integer.parseInt(displayPage);
		
		try {
			repairDetailVOs = new ULDDefaultsDelegate()
					.listDamageRepairDetails(filterVO, pageNumber);
				
		   } catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		   if(repairDetailVOs == null 
					|| (repairDetailVOs != null && 
							repairDetailVOs.size() == 0)){
				log.log(Log.FINE,"!!!inside resultList== null");
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.nouldmovementrepairdetailsexists");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				session.setULDDamageRepairDetailsVOs(null);
				invocationContext.target = LIST_FAILURE;
			  	}
		session.setULDDamageRepairDetailsVOs(repairDetailVOs);
		log.log(Log.INFO, "session returned ---->", session.getULDDamageRepairDetailsVOs());
		invocationContext.target = LIST_SUCCESS;
	}
	private void updateDateRangeInDamageFilter(ULDMovementFilterVO uldMovementFilterVO, UldDmgRprFilterVO filterVO) {
		if(Objects.nonNull(uldMovementFilterVO.getFromDate())){
			filterVO.setFromDate(uldMovementFilterVO.getFromDate().toDisplayFormat());
		}
		if(Objects.nonNull(uldMovementFilterVO.getToDate())){
			filterVO.setToDate(uldMovementFilterVO.getToDate().toDisplayFormat());
		}
	}
	 private void updateFilterVO(ListULDMovementForm listULDMovementForm,
			 ULDMovementFilterVO uldMovementFilterVO,String companyCode){
	    	log.entering("ListULDMovementHistoryCommand","updateFilterVO");
	    	uldMovementFilterVO.setCompanyCode(companyCode);
	    		String uldNumber =listULDMovementForm.
	    								getUldNumber().toUpperCase();    
	    		if(uldNumber != null && 
	    				uldNumber.trim().length() > 0){
	    		uldMovementFilterVO.setUldNumber(uldNumber);
	    	}
	    	else {
				uldMovementFilterVO.setUldNumber("");
			}
	    	
	    	if(listULDMovementForm.getFromDate() != null && 
	    			listULDMovementForm.getFromDate().trim().length() > 0) {
	    		if(DateUtilities.isValidDate(listULDMovementForm.getFromDate(),
	    											"dd-MMM-yyyy")) {
					LocalDate frmDate = new LocalDate(getApplicationSession().
											getLogonVO().getAirportCode(),Location.ARP,false);
				   frmDate.setDate(listULDMovementForm.getFromDate());
					uldMovementFilterVO.setFromDate(frmDate);
				}   		
	    		
	    	}
	    	else {
	    			uldMovementFilterVO.setFromDate(null);
	    		}
	    	if(listULDMovementForm.getToDate() != null && 
	    			listULDMovementForm.getToDate().trim().length() > 0) {
	    		if(DateUtilities.isValidDate(listULDMovementForm.getToDate(),
	    													"dd-MMM-yyyy")) {
					LocalDate toDate = new LocalDate(getApplicationSession().
											getLogonVO().getAirportCode(),Location.ARP,false);
					toDate.setDate(listULDMovementForm.getToDate());
					uldMovementFilterVO.setToDate(toDate);
				}   		
	    		
	    	}
	    	else{
	    		uldMovementFilterVO.setToDate(null);
	    	}
	    	
	    	log.log(Log.FINE, "uldMovementFilterVO-->", uldMovementFilterVO);
			log.exiting("ListULDMovementHistoryCommand","updateFilterVO");
	   }
	   
	    
	 
  
    	
    	    	
}
