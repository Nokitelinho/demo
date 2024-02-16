/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingsite.SaveCommand.java
 *
 *	Created by	:	A-5219
 *	Created on	:	15-Nov-2013
 *
 *  Copyright 2013 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.billingsite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteBankDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteGPACountriesVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteVO;
import com.ibsplc.icargo.business.shared.area.country.vo.CountryVO;
import com.ibsplc.icargo.business.shared.currency.vo.CurrencyValidationVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.BillingSiteSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingSiteMasterForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingsite.SaveCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	15-Nov-2013	:	Draft
 */
public class SaveCommand extends BaseCommand{
	/** The log. */
	private Log log = LogFactory.getLogger("Billing Site Master SaveCommand");
	
	/** The Constant MODULE_NAME. */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/** The Constant SCREEN_ID. */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.billingsitemaster";
	
	/** The Constant ACTION_SUCCESS. */
	private static final String ACTION_SUCCESS = "screenload_success";
	
	/** The Constant INSERT. */
	private static final String INSERT = "I";
	
	/** The Constant DELETE. */
	private static final String DELETE = "D";
	
	/** The Constant DELETE_FLAG. */
	private static final String DELETE_FLAG = "Delete";
	
	/** The Constant SAVE_FLAG. */
	private static final String SAVE_FLAG = "Save";
	
	/** The Constant UPDATE. */
	private static final String UPDATE = "U";
	
	/** The Constant OPFLAG_NOOP. */
	private static final String OPFLAG_NOOP = "NOOP";

	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * Added by 			: A-5219 on 17-Nov-2013
	 * Used for 	:
	 * Parameters	:	@param invocationContext
	 * Parameters	:	@throws CommandInvocationException
	 *
	 * @param invocationContext the invocation context
	 * @throws CommandInvocationException the command invocation exception
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
	
		log.entering("SaveCommand", "execute");
		BillingSiteMasterForm billingSiteMasterForm = (BillingSiteMasterForm) invocationContext.screenModel;
		BillingSiteSession billingSiteSession=getScreenSession(MODULE_NAME,SCREEN_ID);
		BillingSiteVO billingSiteVO =null;
		BillingSiteVO billingSiteVOFromSession =null;
		if(billingSiteSession.getBillingSiteVO()!=null){
			billingSiteVOFromSession=billingSiteSession.getBillingSiteVO();
		}
		else
			{
			billingSiteVOFromSession=billingSiteSession.getBillingSiteBackUpVO();
			}
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO errorVO=null;
		if(DELETE_FLAG.equals(billingSiteMasterForm.getCheckFlag())){
			billingSiteVO = new BillingSiteVO();
			billingSiteVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
			billingSiteVO.setBillingSiteCode(billingSiteMasterForm.getBillingSiteCode());
			billingSiteVO.setSerialNumber(billingSiteVOFromSession.getSerialNumber());
			billingSiteVO.setOperationFlag(DELETE);
		}
		else{
			billingSiteSession=sessionUpdateWithForm(billingSiteSession,billingSiteMasterForm);
			billingSiteVO=billingSiteSession.getBillingSiteBackUpVO();
			errors= validateForm(billingSiteMasterForm,errors,billingSiteVO);
			if(errors!=null && errors.size()>0){
				if("YES".equals(billingSiteMasterForm.getSiteExpired()) && UPDATE.equals(billingSiteVO.getOperationFlag())){
					billingSiteMasterForm.setCheckFlag("List");
				}
				if(billingSiteSession.getBillingSiteBackUpVO()!=null){
					billingSiteSession.setBillingSiteVO(billingSiteSession.getBillingSiteBackUpVO());
					billingSiteSession.setBillingSiteGPACountiresVOs(billingSiteSession.getBillingSiteBackUpVO().getBillingSiteGPACountriesVO());
					billingSiteSession.setBillingSiteBankDetailsVOs(billingSiteSession.getBillingSiteBackUpVO().getBillingSiteBankDetailsVO());
				}
				if(INSERT.equals(billingSiteVO.getOperationFlag()))
					{
					billingSiteMasterForm.setStatus("UnSave");
					}
				invocationContext.addAllError(errors);
				invocationContext.target=ACTION_SUCCESS;
				return;
			}
		}
		try{
			BillingSiteVO billingSiteUpdatedVO=new MailTrackingMRADelegate().saveBillingSiteDetails(billingSiteVO);
			if(billingSiteUpdatedVO.isOverlapping()){
				String[] errordata= new String[1];
				if(billingSiteUpdatedVO.getGpaCountry()!=null && billingSiteUpdatedVO.getGpaCountry().trim().length()>0){
					errordata[0]=billingSiteUpdatedVO.getGpaCountry();
					Object[] obj=errordata;
					errorVO=new ErrorVO("mailtracking.mra.defaults.billingsite.countrydateoverlap",obj);
				}
				else{
					errorVO=new ErrorVO("mailtracking.mra.defaults.billingsite.countrynodateoverlap");
				}
				errors.add(errorVO);
			}
			if(billingSiteUpdatedVO.isDuplicate()){
				String[] errordata= new String[1];
				errordata[0]=billingSiteUpdatedVO.getBillingSiteCode();
				Object[] obj=errordata;
				errorVO=new ErrorVO("mailtracking.mra.defaults.billingsite.alreadyexists",obj);
				errors.add(errorVO);
			}
			if(errors!=null && errors.size()>0){
				if(INSERT.equals(billingSiteVO.getOperationFlag()))
					{
					billingSiteMasterForm.setStatus("UnSave");
					}
					billingSiteSession.setBillingSiteVO(billingSiteSession.getBillingSiteBackUpVO());
					billingSiteSession.setBillingSiteGPACountiresVOs(billingSiteSession.getBillingSiteBackUpVO().getBillingSiteGPACountriesVO());
					billingSiteSession.setBillingSiteBankDetailsVOs(billingSiteSession.getBillingSiteBackUpVO().getBillingSiteBankDetailsVO());
					invocationContext.addAllError(errors);
		   			invocationContext.target=ACTION_SUCCESS;
					return;
			}
			billingSiteMasterForm.reset();
			billingSiteSession.setBillingSiteVO(null);
			billingSiteSession.setBillingSiteBankDetailsVOs(null);
			billingSiteSession.setBillingSiteGPACountiresVOs(null);
			billingSiteSession.setBillingSiteBackUpVO(null);
			Object[] obj = {billingSiteUpdatedVO.getBillingSiteCode()};
			if(!DELETE.equals(billingSiteVO.getOperationFlag()))
				{
				errorVO=new ErrorVO("mailtracking.mra.defaults.billingsite.savesuccess",obj);
				}
			else
				{
				errorVO=new ErrorVO("mailtracking.mra.defaults.billingsite.deletesuccess",obj);
				}
			errors.add(errorVO);
			invocationContext.addAllError(errors);
		}
		catch(BusinessDelegateException exception){
			log.log(Log.INFO, "ERRORS>>>>>>>", exception.getMessage());	
		}
		billingSiteMasterForm.setStatus("Screenload");
		invocationContext.target = ACTION_SUCCESS;
		log.exiting("ScreenLoadCommand", "execute");
		}
			
	/**
	 * Validate currency.
	 *
	 * @param companyCode the company code
	 * @param billingSiteMasterForm the billing site master form
	 * @param errors the errors
	 * @return the collection
	 */
	private Collection<ErrorVO> validateCurrency(Collection<ErrorVO> errors,
			String[] currencies) {
		Collection<String> currencyCodes = new ArrayList<String>();
		Map<String,CurrencyValidationVO> validCurrencies= new HashMap<String,CurrencyValidationVO>();
		for(String currency:currencies){
			if(currency!=null && currency.trim().length()>0) 
					{
					currencyCodes.add(currency);
		}
		}
		if(currencyCodes.size() > 0) {
			try {
				validCurrencies = new CurrencyDelegate().validateCurrencyCodes(getApplicationSession().getLogonVO().getCompanyCode(),currencyCodes);
			} catch (BusinessDelegateException exception) {
				Collection<ErrorVO> otherErrors = handleDelegateException(exception);
				Object[] obj = new Object[otherErrors.size()];
				StringBuffer data = new StringBuffer("");
				if(otherErrors.iterator().hasNext()){
					if(("shared.currency.invalidcurrency").equals(otherErrors.iterator().next().getErrorCode())){
						   Object sb[]=otherErrors.iterator().next().getErrorData();
					   	   for(Object errorData: sb){
					   		   if(errorData!=null ){
					   			   data.append(errorData+", ");
					   			   obj[0]=data.substring(0, data.length()-2).toString();
					   		   }
					   	   }
					   }
				}
				if(obj[0]!=null){
					ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.billingsite.invalidCurrency",obj);
					errors.add(errorVO);
				}
				log.log(Log.INFO, "ERRORS>>>>>>>", errors);
			}		
		}
		log.log(Log.INFO, "valid Currencies>>>>>>>", validCurrencies);	
		return errors;
	}
	
	/**
	 * Validate country.
	 *
	 * @param companyCode the company code
	 * @param billingSiteMasterForm the billing site master form
	 * @param errors the errors
	 * @return the collection
	 */
	private Collection<ErrorVO> validateCountry(Collection<ErrorVO> errors,
			String[] countries) {
		Collection<String> countryCodes = new ArrayList<String>();
		Map<String,CountryVO> validCountries= new HashMap<String,CountryVO>();
		for(String country:countries){
			if(country!=null && country.trim().length()>0){
				countryCodes.add(country);
			}
		}
		if(countryCodes.size() > 0) {
		   try {
	        	validCountries = new AreaDelegate().validateCountryCodes(getApplicationSession().getLogonVO().getCompanyCode(), countryCodes);
		   } catch (BusinessDelegateException exception) {
			   Collection<ErrorVO> otherErrors = handleDelegateException(exception);
			   Object[] obj = new Object[countryCodes.size()];
			   StringBuffer data = new StringBuffer("");
			   if(otherErrors.iterator().hasNext()){
				   if(("shared.area.invalidcountry").equals(otherErrors.iterator().next().getErrorCode())){
					   Object sb[]=otherErrors.iterator().next().getErrorData();
				   	   for(Object errorData: sb){
				   		   if(errorData!=null ){
				   			   data.append(errorData+", ");
				   			   obj[0]=data.substring(0, data.length()-2).toString();
				   		   }
				   	   }
				   }
			   }
			   if(obj[0]!=null){
				   ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.billingsite.invalidCountry",obj);
				   errors.add(errorVO);
			   }
			   log.log(Log.INFO, "ERRORS>>>>>>>", errors);
		   }
		}
		log.log(Log.INFO, "valid Countries>>>>>>>", validCountries);	
		return errors;
	}
	
	/**
	 * Find duplicate countries.
	 *
	 * @param errors the errors
	 * @param countries the countries
	 * @return the collection
	 */
	private Collection<ErrorVO> findDuplicateCountries(Collection<ErrorVO> errors, String[] countries) {

	    Set<String> duplicatesForCountres = new HashSet<String>();
	    Set<String> uniquesForCountries = new HashSet<String>();
	     for(String s : countries) {
	    	 if(s!=null &&s.trim().length()>0){
	    		 String[] temp=s.split(",");
	    		 for(String temp1:temp){
	    			 if(temp1!=null && temp1.trim().length()>0){
	    				 if(!uniquesForCountries.add(temp1)) 
	    					 {
	    					 duplicatesForCountres.add(temp1);
	    			 }
	    		 }
	    	 }
	    }
	    }
	    if(duplicatesForCountres.size()>0){
	    	ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.billingsite.duplicatecountriesexist");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
		 }
	    return errors;
	}
	
	/**
	 * Find duplicate currencies.
	 *
	 * @param errors the errors
	 * @param currencies the currencies
	 * @return the collection
	 */
	private Collection<ErrorVO> findDuplicateCurrencies(Collection<ErrorVO> errors, String[] currencies) {
	      Set<String> duplicatesForCurrencies = new HashSet<String>();
	      Set<String> uniquesForCurrencies = new HashSet<String>();
	      for(String s : currencies) {
	    	  if(s!=null && s.trim().length()>0){
	    		  if(!uniquesForCurrencies.add(s)) 
	    			{
	    			duplicatesForCurrencies.add(s);
	    	  }
	      }
	      }
	      if(duplicatesForCurrencies.size()>0){
	    	  ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.billingsite.duplicatecurrenciesexist");
	    	  errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
	    	  errors.add(errorVO);
	      }
	      return errors;
	 }
	
	/**
	 * Validate form.
	 *
	 * @param billingSiteMasterForm the billing site master form
	 * @param billingSiteVO the billing site vo
	 * @param errors the errors
	 * @return the collection
	 */
	private Collection<ErrorVO> validateForm(BillingSiteMasterForm billingSiteMasterForm,Collection<ErrorVO> errors,BillingSiteVO billingSiteVO){
		ErrorVO errorVO=null;
		if (billingSiteMasterForm.getFromDate() != null && 
				(billingSiteMasterForm.getFromDate().trim().length()>0) && (billingSiteMasterForm.getToDate()!=null)
				&& (billingSiteMasterForm.getToDate().trim().length()>0)) {
			if (DateUtilities.isLessThan(billingSiteMasterForm
					.getToDate(), billingSiteMasterForm
					.getFromDate(), "dd-MMM-yyyy")) {
				billingSiteMasterForm.setSiteExpired("YES");
				errorVO = new ErrorVO("mailtracking.mra.defaults.billingsite.StartDateGreaterThanEndDate");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
			}
			else if (DateUtilities.isLessThan(billingSiteMasterForm.getToDate(), DateUtilities.getCurrentDate("dd-MMM-yyyy"), "dd-MMM-yyyy")
					&& INSERT.equals(billingSiteVO.getOperationFlag())){
					billingSiteMasterForm.setSiteExpired("YES");
					errorVO = new ErrorVO("mailtracking.mra.defaults.billingsite.todatelessthancurrentdate");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
				}
			else if(DateUtilities.isLessThan(billingSiteMasterForm.getToDate(), DateUtilities.getCurrentDate("dd-MMM-yyyy"), "dd-MMM-yyyy")) {
				billingSiteMasterForm.setSiteExpired("YES");
				errorVO = new ErrorVO("mailtracking.mra.defaults.billingsite.cannotmodify");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
			}
			else{
				billingSiteMasterForm.setSiteExpired("NO");
			}
		}
		else{
			errorVO = new ErrorVO("mailtracking.mra.defaults.billingsite.datesaremandatory");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
		}
		if(billingSiteMasterForm.getBillingSite()==null || billingSiteMasterForm.getBillingSite().trim().length()==0 ){
			errorVO = new ErrorVO("mailtracking.mra.defaults.billingsite.invalidbillingsitecode");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
		}
		if(billingSiteMasterForm.getGpaCountries()!=null && billingSiteMasterForm.getGpaCountries().length>0){
			String countries[]=new String[billingSiteMasterForm.getGpaCountries().length*7];
			int i=0,j=0;
			for(String country:billingSiteMasterForm.getGpaCountries()){
				if(country.length()>20){
					errorVO = new ErrorVO("mailtracking.mra.defaults.billingsite.invalidcountrylength");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
				}
				else{
					if(!DELETE.equals(billingSiteMasterForm.getHiddenOpFlagForCountry()[j])&& !OPFLAG_NOOP.equals(billingSiteMasterForm.getHiddenOpFlagForCountry()[j])){
						String s[]=country.split(",");
						for(String temp:s){
							if(temp!=null && temp.trim().length()>0)
								{
								countries[i++]=temp;
						}
						}
						j++;
					}
					else
						{
						j++;
						}
					}
			}
			boolean empty = true;
			for (int k=0; k<countries.length; k++) {
				if (countries[k] != null) {
					empty = false;
					break;
				}
			}
			if(!empty){
				Collection<ErrorVO> errorsFromCountryVOs=new ArrayList<ErrorVO>();
				errorsFromCountryVOs=findDuplicateCountries(errorsFromCountryVOs,countries);
				errorsFromCountryVOs=validateCountry(errorsFromCountryVOs,countries);
				if(errorsFromCountryVOs!=null && errorsFromCountryVOs.size()>0)
					{
					errors.addAll(errorsFromCountryVOs);
			}
		}
		}
		if(billingSiteMasterForm.getCurrencies()!=null && billingSiteMasterForm.getCurrencies().length>1){
			String currencies[]=new String[billingSiteMasterForm.getCurrencies().length];
			int i=0,l=0;
			boolean isSpace=false;
			for(String currency:billingSiteMasterForm.getCurrencies()){
				if(!DELETE.equals(billingSiteMasterForm.getHiddenOpFlagForBank()[i]) && !OPFLAG_NOOP.equals(billingSiteMasterForm.getHiddenOpFlagForBank()[i])){
					if(currency!=null && currency.trim().length()!=0)
						{
						currencies[l++]=currency;
						}
					else
						{
						isSpace=true;
						}
				}
				i++;
			}
			boolean empty = true;
			for (int j=0; j<currencies.length; j++) {
				if(currencies[j] != null){
					if (currencies[j].trim().length()!=0) {
			    empty = false;
			    break;
			  }
					else
						{
						isSpace=true;
						}
				}
			}
			if(isSpace){
				errorVO = new ErrorVO("mailtracking.mra.defaults.billingsite.currencyismandatory");
		        errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
		        errors.add(errorVO);
			}
			if(!empty){
				Collection<ErrorVO> errorsFromBankVOs=new ArrayList<ErrorVO>();
				errorsFromBankVOs=findDuplicateCurrencies(errorsFromBankVOs,currencies);
				errorsFromBankVOs=validateCurrency(errorsFromBankVOs,currencies);
				if(errorsFromBankVOs!=null && errorsFromBankVOs.size()>0)
					{
					errors.addAll(errorsFromBankVOs);
			}
		}
		}
		else{
			errorVO = new ErrorVO("mailtracking.mra.defaults.billingsite.currencyismandatory");
		        errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
		        errors.add(errorVO);
		}
		return errors;
	}
	
	
	/**
	 * Session update with form.
	 *
	 * @param billingSiteSession the billing site session
	 * @param billingSiteMasterForm the billing site master form
	 * @return the billing site session
	 */
	public BillingSiteSession sessionUpdateWithForm(BillingSiteSession billingSiteSession, BillingSiteMasterForm billingSiteMasterForm){
		BillingSiteVO billingSiteVO=new BillingSiteVO();
		if(SAVE_FLAG.equals(billingSiteMasterForm.getCheckFlag()) && billingSiteSession.getBillingSiteVO()!=null){
			if(INSERT.equals(billingSiteSession.getBillingSiteVO().getOperationFlag()))
				{
				billingSiteVO.setOperationFlag(INSERT);
				}
			else
				{
				billingSiteVO.setOperationFlag(UPDATE);
		}
		}
		else
			{
			billingSiteVO.setOperationFlag(INSERT);
			}
		billingSiteVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		if(billingSiteMasterForm.getBillingSiteCode()!=null && billingSiteMasterForm.getBillingSiteCode().trim().length()>0)
			{
			billingSiteVO.setBillingSiteCode(billingSiteMasterForm.getBillingSiteCode());
			}
		else
			{
			billingSiteVO.setAutoGenerate(true);
			}
		billingSiteVO.setBillingSite(billingSiteMasterForm.getBillingSite());
		billingSiteVO.setAirlineAddress(billingSiteMasterForm.getAirlineAddress());
		billingSiteVO.setCorrespondenceAddress(billingSiteMasterForm.getCorrespondenceAddress());
		billingSiteVO.setDesignator1(billingSiteMasterForm.getDesignation1());
		billingSiteVO.setDesignator2(billingSiteMasterForm.getDesignation2());
		billingSiteVO.setSignator1(billingSiteMasterForm.getSignator1());
		billingSiteVO.setSignator2(billingSiteMasterForm.getSignator2());
		billingSiteVO.setFreeText(billingSiteMasterForm.getFreeText());
		billingSiteVO.setLastUpdatedUser(getApplicationSession().getLogonVO().getUserId());
		billingSiteVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		if(billingSiteMasterForm.getFromDate()!=null && billingSiteMasterForm.getFromDate().trim().length()>0)
			{
			billingSiteVO.setFromDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false).setDate(billingSiteMasterForm.getFromDate()));
			}
		if(billingSiteMasterForm.getToDate()!=null && billingSiteMasterForm.getToDate().trim().length()>0)
			{
			billingSiteVO.setToDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false).setDate(billingSiteMasterForm.getToDate()));
			}
		ArrayList<BillingSiteBankDetailsVO> billingSiteBankDetailsVOs = new ArrayList<BillingSiteBankDetailsVO>();
		ArrayList<BillingSiteGPACountriesVO> billingSiteGPACountriesVOs = new ArrayList<BillingSiteGPACountriesVO>();
		if(billingSiteMasterForm.getHiddenOpFlagForCountry()!=null){
		int sizeOfCountries=billingSiteMasterForm.getHiddenOpFlagForCountry().length;
		String[] gpaCountries = billingSiteMasterForm.getGpaCountries();
		String[] operationFlagForCountries=billingSiteMasterForm.getHiddenOpFlagForCountry();
		int count=0;
		while(sizeOfCountries>0){
			if(!OPFLAG_NOOP.equals(operationFlagForCountries[count])){
				if(gpaCountries[count]!=null && (gpaCountries[count]).trim().length()>0){
					BillingSiteGPACountriesVO billingSiteGPACountriesVO = new BillingSiteGPACountriesVO();
					if(billingSiteMasterForm.getBillingSiteCode()!=null && billingSiteMasterForm.getBillingSiteCode().trim().length()>0)
						{
						billingSiteGPACountriesVO.setBillingSiteCode(billingSiteMasterForm.getBillingSiteCode());
						}
					billingSiteGPACountriesVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
					billingSiteGPACountriesVO.setGpaCountry(gpaCountries[count]);
					if(!INSERT.equals(billingSiteVO.getOperationFlag())){
						if(!INSERT.equals(operationFlagForCountries[count]) && !OPFLAG_NOOP.equals(operationFlagForCountries[count])){
							if(billingSiteSession.getBillingSiteBackUpVO()==null)
								{
								billingSiteGPACountriesVO.setSerialNumber(billingSiteSession.getBillingSiteVO().getBillingSiteGPACountriesVO().get(count).getSerialNumber());
								}
							else
								billingSiteGPACountriesVO.setSerialNumber(billingSiteSession.getBillingSiteBackUpVO().getBillingSiteGPACountriesVO().get(count).getSerialNumber());
						}
					}
					billingSiteGPACountriesVO.setOperationalFlag(operationFlagForCountries[count]);
					billingSiteGPACountriesVO.setLastUpdatedUser(getApplicationSession().getLogonVO().getUserId());
					billingSiteGPACountriesVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
					billingSiteGPACountriesVOs.add(billingSiteGPACountriesVO);
				}
			}
			count++;
			sizeOfCountries--;
		}
		billingSiteVO.setBillingSiteGPACountriesVO(billingSiteGPACountriesVOs);
		}
		if(billingSiteMasterForm.getHiddenOpFlagForBank()!=null){
		int sizeOfBank=billingSiteMasterForm.getHiddenOpFlagForBank().length;
		String[] currency=billingSiteMasterForm.getCurrencies();
		String[] bankName=billingSiteMasterForm.getBankName();
		String[] branch=billingSiteMasterForm.getBranch();
		String[] accNo=billingSiteMasterForm.getAccno();
		String[] city=billingSiteMasterForm.getCity();
		String[] country=billingSiteMasterForm.getCountry();
		String[] swiftCode=billingSiteMasterForm.getSwiftCode();
		String[] ibanNo=billingSiteMasterForm.getIbanNo();
		String[] operationFlagForBank=billingSiteMasterForm.getHiddenOpFlagForBank();
		int cnt=0;
		while(sizeOfBank>0){
			if(!OPFLAG_NOOP.equals(operationFlagForBank[cnt])){
				if(currency[cnt]!=null){
					BillingSiteBankDetailsVO billingSiteBankDetailsVO = new BillingSiteBankDetailsVO();
					billingSiteBankDetailsVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
					billingSiteBankDetailsVO.setCurrency(currency[cnt]);
					billingSiteBankDetailsVO.setBankName(bankName[cnt]);
					billingSiteBankDetailsVO.setBranch(branch[cnt]);
					billingSiteBankDetailsVO.setAccNo(accNo[cnt]);
					billingSiteBankDetailsVO.setCity(city[cnt]);
					billingSiteBankDetailsVO.setCountry(country[cnt]);
					billingSiteBankDetailsVO.setIbanNo(ibanNo[cnt]);
					billingSiteBankDetailsVO.setSwiftCode(swiftCode[cnt]);
					billingSiteBankDetailsVO.setOperationalFlag(operationFlagForBank[cnt]);
					if(billingSiteMasterForm.getBillingSiteCode()!=null && billingSiteMasterForm.getBillingSiteCode().trim().length()>0)
						{
						billingSiteBankDetailsVO.setBillingSiteCode(billingSiteMasterForm.getBillingSiteCode());
						}
					if(!INSERT.equals(billingSiteVO.getOperationFlag())){
						if(!INSERT.equals(operationFlagForBank[cnt]) && !OPFLAG_NOOP.equals(operationFlagForBank[cnt])){
							if(billingSiteSession.getBillingSiteBackUpVO()!=null)
								{
								billingSiteBankDetailsVO.setSerialNumber(billingSiteSession.getBillingSiteBackUpVO().getBillingSiteBankDetailsVO().get(cnt).getSerialNumber());
								}
							else
								billingSiteBankDetailsVO.setSerialNumber(billingSiteSession.getBillingSiteVO().getBillingSiteBankDetailsVO().get(cnt).getSerialNumber());
						}
					}	
					billingSiteBankDetailsVO.setLastUpdatedUser(getApplicationSession().getLogonVO().getUserId());
					billingSiteBankDetailsVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
					billingSiteBankDetailsVOs.add(billingSiteBankDetailsVO);
				}
			}
			cnt++;
			sizeOfBank--;
		}	
		billingSiteVO.setBillingSiteBankDetailsVO(billingSiteBankDetailsVOs);
		}
		billingSiteSession.setBillingSiteBackUpVO(billingSiteVO);
		return billingSiteSession;
	}
}

