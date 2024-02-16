/*
 * RevokeCommand.java Created on Sep 22, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.revokeblacklistedstock;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListBlackListedStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.RevokeBlackListedStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1927
 *
 */
public class RevokeCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	private static final String SUCCESS = "Y";
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {


    		log.entering("RevokeCommand","execute---->");
			RevokeBlackListedStockForm revokeBlackListedStockForm = (RevokeBlackListedStockForm) invocationContext.screenModel;
	 		ListBlackListedStockSession session = (ListBlackListedStockSession)
										getScreenSession("stockcontrol.defaults","stockcontrol.defaults.listblacklistedstock");

			BlacklistStockVO revokeBlacklistStockVO=new BlacklistStockVO();
			BlacklistStockVO blacklistStockVO=(BlacklistStockVO)session.getBlacklistStockVO();

			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
			//Calendar lastUpdateDate =Calendar.getInstance(logonAttributes.getTimeZone());
			Collection<ErrorVO> errors = null;
			errors = validateForm(revokeBlackListedStockForm,session);

			if (errors != null && errors.size() > 0) {

					invocationContext.addAllError(errors);
					invocationContext.target = "screenload_failure";
					return;
			}

			String companyCode=logonAttributes.getCompanyCode();
			String documentType=revokeBlackListedStockForm.getDocType();
			String documentSubType=revokeBlackListedStockForm.getSubType();
			String rangeFrom=blacklistStockVO.getRangeFrom();
			String rangeTo=blacklistStockVO.getRangeTo();
			String newRangeFrom=revokeBlackListedStockForm.getRangeFrom();
			String newRangeTo=revokeBlackListedStockForm.getRangeTo();

			revokeBlacklistStockVO.setCompanyCode(companyCode);
			revokeBlacklistStockVO.setRemarks(revokeBlackListedStockForm.getRemarks());
			revokeBlacklistStockVO.setDocumentType(documentType);
			revokeBlacklistStockVO.setDocumentSubType(documentSubType);
			revokeBlacklistStockVO.setRangeFrom(rangeFrom);
			revokeBlacklistStockVO.setRangeTo(rangeTo);
			revokeBlacklistStockVO.setNewRangeFrom(newRangeFrom);
			revokeBlacklistStockVO.setNewRangeTo(newRangeTo);
			revokeBlacklistStockVO.setStationCode(getApplicationSession().getLogonVO().getStationCode());
			revokeBlacklistStockVO.setBlacklistDate(blacklistStockVO.getBlacklistDate());
			revokeBlacklistStockVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
			revokeBlacklistStockVO.setLastUpdateUser(logonAttributes.getUserId());
			//Bug ID : ICRD-12191 - Jeena - Getting airlineIdentifier from BlacklistStockVO 
			//Changed for IASCB-104245
			revokeBlacklistStockVO.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
			//revokeBlacklistStockVO.setAirlineIdentifier(getApplicationSession().getLogonVO().getOwnAirlineIdentifier());
			//End of Bug ID : ICRD-12191 - Jeena - Getting airlineIdentifier from BlacklistStockVO 
			Collection<ErrorVO> errorVos= null;
			try{
					new StockControlDefaultsDelegate().revokeBlacklistStock(revokeBlacklistStockVO);
				}
			catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
				errorVos = handleDelegateException(businessDelegateException);
			}
			if(errorVos==null || errorVos.size()==0){
				revokeBlackListedStockForm.setRevokeSuccessful(SUCCESS);
				revokeBlackListedStockForm.setDocType("");
				revokeBlackListedStockForm.setRangeFrom("");
				revokeBlackListedStockForm.setRangeTo("");
				revokeBlackListedStockForm.setRemarks("");
				revokeBlackListedStockForm.setSubType("");
				ErrorVO error = null;
				Object[] obj = { "Revoke" };
				error = new ErrorVO("stockcontrol.defaults.revokesuccessful", obj);// Revoke Successfully
				error.setErrorDisplayType(ErrorDisplayType.INFO);
				errors.add(error);
				invocationContext.addAllError(errors); //uncommented by A-7924 as part of ICRD-317566 
			}
			log.exiting("RevokeCommand","execute");
			invocationContext.target = "screenload_success";


    }

	/** To get the numeric value of the string
	 *
	 * @param range
	 * @return Numeric value
	 */
	private  long findLong(String range){
			//System.out.println("---------------Entering ascii convertion----->>>>");
			char[] sArray=range.toCharArray();
			long base=1;
			long sNumber=0;
			for(int i=sArray.length-1;i>=0;i--){
				sNumber+=base*calculateBase(sArray[i]);
				int count=sArray[i];
				if (count>57) {
					base*=26;
				} else {
					base*=10;
				}
			}
			return sNumber;
	}

	private  long calculateBase(char str){
			long formatStr=str;
			long base=0;
			try{
				base=Integer.parseInt(""+str);
			}catch(NumberFormatException numberFormatException){
				base=formatStr-65;
			}
			return base;
	}

	/**
	 * Validate form method
	 * @param form
	 * @param session
	 * @return
	 */
    private Collection<ErrorVO> validateForm(RevokeBlackListedStockForm form,ListBlackListedStockSession session) {


			BlacklistStockVO blacklistStockVO=(BlacklistStockVO)session.getBlacklistStockVO();
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;

			if (((findLong(blacklistStockVO.getRangeFrom())>findLong(form.getRangeFrom())) && (findLong(form.getRangeFrom())<findLong(blacklistStockVO.getRangeTo())))
						|| ((findLong(blacklistStockVO.getRangeTo())<findLong(form.getRangeTo())) && (findLong(form.getRangeTo())>findLong(blacklistStockVO.getRangeFrom())))){

						Object[] obj = { "Range" };
						error = new ErrorVO("stockcontrol.defaults.selectedrngnotwithinstock", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
				}


			if ("".equals(form.getRangeFrom())||form.getRangeFrom().length()==0){

						Object[] obj = { "Range From" };
						error = new ErrorVO("stockcontrol.defaults.plsenterrangefrmval", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
			}


			if ("".equals(form.getRangeTo())||form.getRangeTo().length()==0){

						Object[] obj = { "Range To" };
						error = new ErrorVO("stockcontrol.defaults.plzenterrangetoval", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
				}

			if (findLong(form.getRangeFrom())>findLong(form.getRangeTo())){

						Object[] obj = { "RangeFrom exceeds" };
						error = new ErrorVO("SKCM_099", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
			}


		return errors;
	}


}
