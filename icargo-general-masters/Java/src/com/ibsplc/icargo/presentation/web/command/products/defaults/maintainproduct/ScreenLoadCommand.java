/*
 * ScreenLoadCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ibsplc.icargo.business.products.defaults.vo.RestrictionPaymentTermsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 *
 * @author A-1754
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	//private static final String COMPANY_CODE = "AV";

	private static final String SAVE_MODE = "save";
	
	private static final String PRIORITY_ONETIME="products.defaults.priority";
	
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
	private static final String  PRD_CATG_ONETIME= "products.defaults.category";//Added for ICRD-166985 by A-5117

	/**
	 * The execute method in BaseCommand 
	 *
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		final MaintainProductForm maintainProductForm = (MaintainProductForm) invocationContext.screenModel;
		final MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		session.removeAllAttributes();
		session.setButtonStatusFlag(maintainProductForm.getFromListProduct());
		maintainProductForm.setMode(SAVE_MODE);
		maintainProductForm.setProductName("");
		maintainProductForm.setEndDate("");
		maintainProductForm.setStartDate(DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT));
		maintainProductForm.setMinVolume("0.0");
		maintainProductForm.setMaxVolume("0.0");
		maintainProductForm.setMinWeight("0.0");
		maintainProductForm.setMaxWeight("0.0");
		maintainProductForm.setMinDimension("0.0");
		maintainProductForm.setMaxDimension("0.0");
		maintainProductForm.setProductStatus("New");
		maintainProductForm.setProductCategory("");
		maintainProductForm.setDisplayInPortal(true); 
		maintainProductForm.setDocType("");
		maintainProductForm.setSubType("");
		final Map<String, Collection<OneTimeVO>> oneTimes = getScreenLoadDetails(getApplicationSession().getLogonVO().getCompanyCode());
		if (oneTimes != null) {
			final Collection<OneTimeVO> restrictedTerms = oneTimes
					.get("products.defaults.paymentterms");
			final Collection<OneTimeVO> weightUnit = oneTimes
					.get("shared.defaults.weightUnitCodes");
			final Collection<OneTimeVO> volumeUnit = oneTimes
					.get("shared.defaults.volumeUnitCodes");
			final Collection<OneTimeVO> dimensionUnit = oneTimes
					.get("shared.defaults.dimensionUnitCodes");
			final Collection<OneTimeVO> statusOnetime = oneTimes
					.get("products.defaults.status");
			final Collection<OneTimeVO> prtyOnetime = oneTimes.get(PRIORITY_ONETIME);
			final Collection<OneTimeVO> productCategories = oneTimes.get(PRD_CATG_ONETIME);//Added for ICRD-166985 by A-5117

			final Collection<RestrictionPaymentTermsVO> paymentTermsVOs = getAllPaymentRestrictions(restrictedTerms);
			session.setWeightCode(weightUnit);
			session.setVolumeCode(volumeUnit);
			session.setDimensionCode(dimensionUnit);
			session.setStatusOneTime(statusOnetime);
			session.setRestrictionPaymentTerms(paymentTermsVOs);
			session.setPriorityOneTIme(prtyOnetime);
			session.setProductCategories(productCategories);//Added for ICRD-166985 by A-5117

		}
		 
		//added by A-1944 - to obtain the values of the dynamic doctypes
    	final ApplicationSessionImpl applicationSession = getApplicationSession();
		final LogonAttributes logonAttributes = applicationSession.getLogonVO();
		final String companyCode = logonAttributes.getCompanyCode();

		 LinkedHashMap<String,Collection<String>> documentList = null;
		 final LinkedHashMap<String,Collection<String>> documentListNew = new LinkedHashMap<String,Collection<String>>();
		 try{
			 documentListNew.put("",null);
		     documentList =new LinkedHashMap<String,Collection<String>>(
				     new DocumentTypeDelegate().findAllDocuments(companyCode));
		  //documentList.put("",null);
		  documentListNew.putAll(documentList);
		 }catch(BusinessDelegateException businessDelegateException){
		 	businessDelegateException.getMessage();
		 	log.log(Log.FINE,"\n\n Exception while finding the doctypes");
		 } 

		 log.log(Log.FINE, "\n\n the doctypes --------------", documentList);
		session.setDynamicDocType(documentListNew);
		//ends
		String stationCode = logonAttributes.getStationCode();
		
		UnitRoundingVO unitRoundingVO = null;
		UnitRoundingVO unitRoundingVolVO = null;
		UnitRoundingVO unitRoundingDimensionsVO = null; //added as part of CR-ICRD-232462
		try {
			log.log(Log.FINE,"\n\nUnitcode----------->");
			unitRoundingVO = UnitFormatter.getStationDefaultUnit(
					stationCode, UnitConstants.WEIGHT);
			unitRoundingVolVO = UnitFormatter.getStationDefaultUnit(
					stationCode, UnitConstants.VOLUME);
			unitRoundingDimensionsVO=UnitFormatter.getStationDefaultUnit(
					stationCode, UnitConstants.DIMENSION);
			} catch(UnitException unitException) {
			unitException.getErrorCode();
		}
			session.setWeightVO(unitRoundingVO);
			session.setVolumeVO(unitRoundingVolVO);
			session.setDimensionVO(unitRoundingDimensionsVO);
			
		invocationContext.target = "screenload_success";

	}

	/**
	 * This method will be invoked at the time of screen load
	 *
	 * @param companyCode
	 * @return
	 */
	public Map<String, Collection<OneTimeVO>> getScreenLoadDetails(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		try {

			final Collection<String> fieldTypes = new ArrayList<String>();
			fieldTypes.add("products.defaults.paymentterms");
			fieldTypes.add("shared.defaults.weightUnitCodes");
			fieldTypes.add("shared.defaults.volumeUnitCodes");
			fieldTypes.add("shared.defaults.dimensionUnitCodes");
			fieldTypes.add("products.defaults.status");
			fieldTypes.add(PRD_CATG_ONETIME);//Added for ICRD-166985 by A-5117
			fieldTypes.add(PRIORITY_ONETIME);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(
					getApplicationSession().getLogonVO().getCompanyCode(), fieldTypes);

		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"<-----------------BusinessDelegateException------------->");
		}
		return oneTimes;
	}

	/**
	 * This function returns the payment terms to be displayed this shud be
	 * obtained form the shared System Written of to test function call.
	 * @param restrictedTerms
	 * @return
	 */
	private Collection<RestrictionPaymentTermsVO> getAllPaymentRestrictions(
			Collection<OneTimeVO> restrictedTerms) {

		final Collection<RestrictionPaymentTermsVO> paymentTerms = new ArrayList<RestrictionPaymentTermsVO>(); // /Till
																											// here
		if (restrictedTerms != null) {
			for (OneTimeVO oneTime : restrictedTerms) {
				RestrictionPaymentTermsVO termsVO = new RestrictionPaymentTermsVO();
				termsVO.setPaymentTerm(oneTime.getFieldValue());
				termsVO.setIsRestricted(true);
				paymentTerms.add(termsVO);
			}
		}
		return paymentTerms;
	}
}
