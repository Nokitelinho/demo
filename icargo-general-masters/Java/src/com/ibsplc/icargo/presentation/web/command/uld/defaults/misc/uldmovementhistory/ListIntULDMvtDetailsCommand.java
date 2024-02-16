/*
 * ListIntULDMvtDetailsCommand.java Created on  May 07, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 * 
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldmovementhistory;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtDetailVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtHistoryFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDNumberVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDIntMvtHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-3093
 * This method is called on List click from the Internal ULD Movement Screen
 * 
 */
public class ListIntULDMvtDetailsCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("ULD_MOVEMENT_HISTORY");

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID_INTMVT = "uld.defaults.misc.uldintmvthistory";
	
	private static final String EMPTY ="";
	
	private static final String LISTED="Listed";

	/**
	 * @param invocationContext	
	 * @return
	 * @throws CommandInvocationException 
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.INFO, "ENTERED LIST CMD");
		ULDIntMvtHistoryFilterVO uldIntMvtFilterVO = new ULDIntMvtHistoryFilterVO();
		log.log(Log.INFO, "ENTERED LIST CMD", uldIntMvtFilterVO);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ListULDMovementForm form = (ListULDMovementForm) invocationContext.screenModel;
		ULDIntMvtHistorySession session = getScreenSession(MODULE,
				SCREENID_INTMVT);
		log.log(Log.INFO, "ENTERED LIST CMD GOT SESSION ");

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		log.log(Log.INFO, "ENTERED LIST CMD GOT company code ", companyCode);
		session.setULDIntMvtHistoryFilterVO(uldIntMvtFilterVO);
		// Validate ULD Number format and From ,To Date
		//errors = validateForm(form, session, companyCode);
		updateFilterVO(form, uldIntMvtFilterVO, companyCode);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			form.setAfterList(EMPTY);
			session.setIntULDMvtDetails(null);
			invocationContext.target = LIST_FAILURE;
			return;
		}

		Page<ULDIntMvtDetailVO> uldIntMvtDetailVOs = new Page<ULDIntMvtDetailVO>(
				new ArrayList<ULDIntMvtDetailVO>(), 0, 0, 0, 0, 0, false);
		String displayPage= null;
		
    	displayPage=form.getDisplayPage();
    	if("1".equals(form.getDisplayPageFlag())){
    		displayPage="1";
    		form.setDisplayPageFlag("");
    	}
		log.log(Log.INFO, "form.getDisplayPage()--------- ", form.getDisplayPage());
		int pageNumber = Integer.parseInt(displayPage);
		
		// Check if ULD exists in system
		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
		ULDValidationVO uldValidationVO = new ULDValidationVO();
		String uldNumber = form.getUldNumber().toUpperCase();

		try {
			uldValidationVO = uldDefaultsDelegate.validateULD(companyCode,
					uldNumber);
			log
					.log(Log.INFO, "uldValidationVO returned ---->",
							uldValidationVO);

			} catch (BusinessDelegateException businessDelegateException) {
					log.log(Log.FINE, "inside try...caught businessDelegateException");
					businessDelegateException.getMessage();
					errors = handleDelegateException(businessDelegateException);
					form.setAfterList(EMPTY);
					session.setIntULDMvtDetails(null);
					
			}

		if (uldValidationVO == null) {
			ErrorVO err = new ErrorVO(
					"uld.defaults.uldintmvthistory.msg.err.norecords");
			errors.add(err);
			invocationContext.addAllError(errors);
			form.setAfterList(EMPTY);
			session.setIntULDMvtDetails(null);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		// Find the movement details
		try {
			
			uldIntMvtDetailVOs = new ULDDefaultsDelegate()
					.findIntULDMovementHistory(uldIntMvtFilterVO, pageNumber);

			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				errors = handleDelegateException(businessDelegateException);
				form.setAfterList(EMPTY);
				session.setIntULDMvtDetails(null);
				
			}

		session.setIntULDMvtDetails(uldIntMvtDetailVOs);
		log.log(Log.INFO, "uldIntMvtDetailVOs returned ---->",
				uldIntMvtDetailVOs);
		if (uldIntMvtDetailVOs == null || uldIntMvtDetailVOs.size() == 0) {
			
			ErrorVO err = new ErrorVO("uld.defaults.uldintmvthistory.msg.err.norecords");
			errors.add(err);
			invocationContext.addAllError(errors);
			form.setAfterList(EMPTY);
			session.setIntULDMvtDetails(null);
			invocationContext.target = LIST_FAILURE;
			return;
			}
			log.log(Log.INFO, "uldValidationVO.getLocation()--->",
					uldValidationVO);
			ULDNumberVO uldNumberVO = new ULDNumberVO();
	        try
	        {
	        	ULDMovementFilterVO uldMovementFilterVO = new ULDMovementFilterVO();
	        		uldMovementFilterVO.setCompanyCode(companyCode);
	        		uldMovementFilterVO.setUldNumber(form.getUldNumber().toUpperCase());
	        		log.log(Log.FINE, "uldNumberVOuldNumberVOuldNumberVO11-->",
							uldMovementFilterVO);
				uldNumberVO = new ULDDefaultsDelegate().findULDHistoryCounts(uldMovementFilterVO);
	            log.log(Log.FINE, "uldNumberVOuldNumberVOuldNumberVO-->",
						uldNumberVO);
	           
	        }
	        catch(BusinessDelegateException businessDelegateException)
	        {
	        	errors = handleDelegateException(businessDelegateException);
				form.setAfterList(EMPTY);
				session.setIntULDMvtDetails(null);
				if(errors != null && errors.size() > 0){
	        		invocationContext.addAllError(errors);
	        		invocationContext.target = LIST_FAILURE;
	        		return;
	        	}
	        }
	        form.setNoOfLoanTxns(uldNumberVO.getNoOfLoanTxns());
	        form.setNoOfMovements(uldNumberVO.getNoOfMovements());
	        form.setNoOfTimesDmged(uldNumberVO.getNoOfTimesDmged());
	        form.setNoOfTimesRepaired(uldNumberVO.getNoOfTimesRepaired());
	           

		form.setAfterList(LISTED);
		invocationContext.target = LIST_SUCCESS;
	}
	/**
	 * @param form
	 * @param uldIntMvtFilterVO
	 * @param companyCode
	 */

	private void updateFilterVO(ListULDMovementForm form,
			ULDIntMvtHistoryFilterVO uldIntMvtFilterVO, String companyCode) {
		log.entering("ListULDIntMvtCommand", "updateFilterVO");
		uldIntMvtFilterVO.setCompanyCode(companyCode);
		String uldNumber = form.getUldNumber().toUpperCase();
		if (uldNumber != null && uldNumber.trim().length() > 0) {
			
			uldIntMvtFilterVO.setUldNumber(uldNumber.trim());
		} else {
			uldIntMvtFilterVO.setUldNumber(EMPTY);
		}

		if (form.getFromDate() != null
				&& form.getFromDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(form.getFromDate(), "dd-MMM-yyyy")) {
				LocalDate frmDate = new LocalDate(getApplicationSession()
						.getLogonVO().getAirportCode(), Location.ARP, false);
				frmDate.setDate(form.getFromDate().trim());
				uldIntMvtFilterVO.setFromDate(frmDate);
			}

		} else {
			uldIntMvtFilterVO.setFromDate(null);
		}
		if (form.getToDate() != null && form.getToDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(form.getToDate(), "dd-MMM-yyyy")) {
				LocalDate toDate = new LocalDate(getApplicationSession()
						.getLogonVO().getAirportCode(), Location.ARP, false);
				toDate.setDate(form.getToDate().trim());
				uldIntMvtFilterVO.setToDate(toDate);
			}

		} else {
			uldIntMvtFilterVO.setToDate(null);
		}
		log.exiting("List Internal ULDMovementHistoryCommand",
				"uldIntMvtFilterVO");
	}

	/**
	 * This method is used to do form validations
	 * @param form
	 * @return
	 */
	

}
