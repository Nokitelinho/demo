/*
 * DisplayPrdDetailsCommand.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductParamterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductPriorityVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCommodityVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCustomerGroupVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionPaymentTermsVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;

import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

/**
 *
 * @author A-1754
 *
 */
public class DisplayPrdDetailsCommand extends BaseCommand {


	private static final String RESTRICT_FLAG = "Restrict";

	private static final String SAVE_MODE = "save";

	private static final String MODIFY_MODE = "modify";

	private static final String CREATE_MODE = "saveas";

	private static final String PRIORITY_ONETIME="products.defaults.priority";
	
	private static final String ZERO = "0";
	
	private static final String TIME_SEPERATOR = ":";

	private static final String EMPTY = "";
	
	private static final String OVERRIDE_CAPACITY= "on";
	
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
	
	/**
	 * The execute method in BaseCommand
	 *
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		MaintainProductForm maintainProductForm = (MaintainProductForm) invocationContext.screenModel;

		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");

		if (CREATE_MODE.equals(maintainProductForm.getMode())) {
			maintainProductForm.setProductStatus("New");
			ProductVO productVO = findProductDetails(
					getApplicationSession().getLogonVO().getCompanyCode(),
					maintainProductForm.getProductCode());
			session.setProductVO(productVO);
			//Added by A-1953 to set the parentScreenId
			session.setButtonStatusFlag(maintainProductForm.getFromListProduct());
			handleGetScreenLoadDetails(session);
			setProductDisplayDetails(productVO, session, maintainProductForm);

		} else {
			Collection<ErrorVO> validationErrors =null;
			handleGetScreenLoadDetails(session);
			validationErrors = validateForm(maintainProductForm);
			if(validationErrors!=null && validationErrors.size()>0){
				invocationContext.addAllError(validationErrors);
				invocationContext.target = "screenload_success";
				return;
			}
			Collection<ErrorVO> dateValidationErrors = null;
			dateValidationErrors = validateDate(maintainProductForm);
			if(dateValidationErrors!=null && dateValidationErrors.size()>0){
				invocationContext.addAllError(dateValidationErrors);
				invocationContext.target = "screenload_success";
				return;
			}
			
			if ("".equals(maintainProductForm.getStartDate())
					|| "".equals(maintainProductForm.getEndDate())) {

				Collection<ProductValidationVO> validationVOs = null;
				try {
					validationVOs = new ProductDefaultsDelegate()
							.findProductsByName(getApplicationSession().getLogonVO().getCompanyCode(),
									maintainProductForm.getProductName());
				} catch (BusinessDelegateException businessDelegateException) {
					log.log(Log.FINE,"<-----------------BusinessDelegateException------------->");
					Collection<ErrorVO> expErrors = handleDelegateException(businessDelegateException);
				}

				if (validationVOs != null && validationVOs.size() > 0) {
					if (validationVOs.size() == 1) {
						ArrayList<ProductValidationVO> poductValidationVOList = (ArrayList<ProductValidationVO>) validationVOs;
						ProductValidationVO poductValidationVO = (ProductValidationVO) poductValidationVOList
								.get(0);
						ProductVO productVO = findProductDetails(getApplicationSession().getLogonVO().getCompanyCode(),
								poductValidationVO.getProductCode());
						session.setProductVO(productVO);
						session.setProductParameterVOs(productVO.getProductParamters());
						setProductDisplayDetails(productVO, session,
								maintainProductForm);
						maintainProductForm.setProductCode(poductValidationVO
								.getProductCode());
						maintainProductForm.setMode(MODIFY_MODE);
					} else {
						session.setProductValidationVos(validationVOs);
					}
				} else {
					Collection<ProductParamterVO> ProductParamterVOs=findAllProductParametersDetails(getApplicationSession().getLogonVO().getCompanyCode());
					session.setProductParameterVOs(ProductParamterVOs);
					Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
					ErrorVO error = null;
					Object[] obj = { "Product" };
					error = new ErrorVO("products.defaults.prdnotexists", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					invocationContext.addAllError(errors);
					handleClearAction(session, maintainProductForm);
					maintainProductForm.setProductStatus("New");
					maintainProductForm.setMode(SAVE_MODE);
				}

			} else {
				LocalDate localStartDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
				LocalDate localEndDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
				String productCode = validateProductName(getApplicationSession().getLogonVO().getCompanyCode(),
						maintainProductForm.getProductName(), 
								localStartDate.setDate(maintainProductForm.getStartDate()), 
								localEndDate.setDate(maintainProductForm.getEndDate()));

				if (productCode != null) {
					ProductVO productVO = findProductDetails(getApplicationSession().getLogonVO().getCompanyCode(),
							productCode);
					session.setProductVO(productVO);
					// sessionModel.setProductVO(maintainProductModel.getProductVO());
					setProductDisplayDetails(productVO, session,
							maintainProductForm);
					maintainProductForm.setProductCode(productCode);
					maintainProductForm.setMode(MODIFY_MODE);
				} else {

					Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
					ErrorVO error = null;
					Object[] obj = { "Product" };
					// This SNULL01 errorcode is mapped in errortags.xml as
					// servercode with corresponding clientcode.
					// This client code have message entry in message resources
					// property file.
					error = new ErrorVO("products.defaults.prdnotexists", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					invocationContext.addAllError(errors);
					handleClearAction(session, maintainProductForm);
					maintainProductForm.setProductStatus("New");
					maintainProductForm.setMode(SAVE_MODE);
				}
			}
		}

		invocationContext.target = "screenload_success";

	}

	/**
	 * Used to fetch the details of a particular product
	 *
	 * @param companyCode
	 * @param productCode
	 * @return productVO
	 */

	public ProductVO findProductDetails(String companyCode, String productCode) {
		ProductVO productVO = null;
		try {
			productVO = new ProductDefaultsDelegate().findProductDetails(
					companyCode, productCode);
			log.log(Log.FINE,"<-----------------Inside Controller productVO------------->");

		} catch (BusinessDelegateException e) {
			log.log(Log.FINE,"<-----------------BusinessDelegateException------------->");

		}
		return productVO;

	}

	/**
	 * The method to get the onetime values
	 *
	 * @param session
	 */

	private void handleGetScreenLoadDetails(
			MaintainProductSessionInterface session) {
		Map<String, Collection<OneTimeVO>> oneTimes = getScreenLoadDetails(
										getApplicationSession().getLogonVO().getCompanyCode());
		if (oneTimes != null) {
			Collection<OneTimeVO> restrictedTerms = oneTimes.get("products.defaults.paymentterms");
			Collection<OneTimeVO> weightUnit = oneTimes
					.get("shared.defaults.weightUnitCodes");
			Collection<OneTimeVO> volumeUnit = oneTimes
					.get("shared.defaults.volumeUnitCodes");
			Collection<OneTimeVO> statusOnetime = oneTimes.get("products.defaults.status");
			Collection<OneTimeVO> prtyOnetime = oneTimes.get(PRIORITY_ONETIME);

			Collection<RestrictionPaymentTermsVO> paymentTermsVOs = getAllPaymentRestrictions(restrictedTerms);
			session.setWeightCode(weightUnit);
			session.setVolumeCode(volumeUnit);
			session.setStatusOneTime(statusOnetime);
			session.setRestrictionPaymentTerms(paymentTermsVOs);
			session.setPriorityOneTIme(prtyOnetime);
			//Added for ICRD-164890 starts
			String stationCode = getApplicationSession().getLogonVO().getStationCode();
			UnitRoundingVO unitRoundingVO = null;
			UnitRoundingVO unitRoundingVolVO = null;
			try {
				log.log(Log.FINE,"\n\nUnitcode----------->");
				unitRoundingVO = UnitFormatter.getStationDefaultUnit(
						stationCode, UnitConstants.WEIGHT);
				unitRoundingVolVO = UnitFormatter.getStationDefaultUnit(
						stationCode, UnitConstants.VOLUME);
				} catch(UnitException unitException) {
				unitException.getErrorCode();
			}
				session.setWeightVO(unitRoundingVO);
				session.setVolumeVO(unitRoundingVolVO);
				//Added for ICRD-164890 ends			
		}
		//added by A-1944 - to obtain the values of the dynamic doctypes
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		 final LinkedHashMap<String,Collection<String>> documentListNew = new LinkedHashMap<String,Collection<String>>();//added by A-8130 for ICRD-307952
		 HashMap<String,Collection<String>> documentList = null;
		 try{
		 documentListNew.put("",null);
		  documentList =new HashMap<String,Collection<String>>(
				  new DocumentTypeDelegate().findAllDocuments(companyCode));
		  //documentList.put("",null); 
		  documentListNew.putAll(documentList);
		 }catch(BusinessDelegateException businessDelegateException){
		 	businessDelegateException.getMessage();
		 	log.log(Log.FINE,"\n\n Exception while finding the doctypes");
		 }
		 
		 log.log(Log.FINE, "\n\n the doctypes --------------", documentList);
		session.setDynamicDocType(documentListNew);

	}

	/**
	 * Method to set the product display details.
	 * @param productVO
	 * @param session
	 * @param mintainProductForm
	 */

	private void setProductDisplayDetails(ProductVO productVO,
			MaintainProductSessionInterface session,
			MaintainProductForm mintainProductForm) {

		if (productVO.getTransportMode() != null) {
			if (productVO.getTransportMode().size() == 0) {
				session.setProductTransportModeVOs(null);
			} else {
				session
						.setProductTransportModeVOs(productVO
								.getTransportMode());
			}
		}
		if (productVO.getProductScc() != null) {
			if (productVO.getProductScc().size() == 0) {
				session.setProductSccVOs(null);
			} else {
				session.setProductSccVOs(productVO.getProductScc());
			}
		}
		if (productVO.getPriority() != null) {
			if (productVO.getPriority().size() == 0) {
				session.setProductPriorityVOs(null);
			} else {
				populatePrtyDisplay(productVO.getPriority(),session);
				session.setProductPriorityVOs(productVO.getPriority());
			}
		}
		if (productVO.getServices() != null) {
			if (productVO.getServices().size() == 0) {
				session.setProductServiceVOs(null);
			} else {
				session.setProductServiceVOs(productVO.getServices());
			}
		}

		if (productVO.getRestrictionCommodity() != null) {
			if (productVO.getRestrictionCommodity().size() == 0) {
				session.setProductCommodityVOs(null);
			} else {
				session.setProductCommodityVOs(productVO
						.getRestrictionCommodity());
				setCommodityRestriction(mintainProductForm,session);
			}
		}

		if (productVO.getRestrictionCustomerGroup() != null) {
			if (productVO.getRestrictionCustomerGroup().size() == 0) {
				session.setProductCustGrpVOs(null);
			} else {
				session.setProductCustGrpVOs(productVO
						.getRestrictionCustomerGroup());
				setCustomerGroupRestriction(mintainProductForm,session);
			}
		}

		if (productVO.getRestrictionSegment() != null) {
			if (productVO.getRestrictionSegment().size() == 0) {
				session.setProductSegmentVOs(null);
			} else {
				session.setProductSegmentVOs(productVO.getRestrictionSegment());
				session.setSegmentAfterListing(productVO
						.getRestrictionSegment());
				setSegmentRestriction(mintainProductForm,session);
			}
		}
		if (productVO.getRestrictionStation() != null) {
			if (productVO.getRestrictionStation().size() == 0) {
				session.setProductStationVOs(null);
			} else {
				session.setProductStationVOs(productVO.getRestrictionStation());
				setStationRestriction(mintainProductForm,session);
			}
		}

		if (productVO.getProductEvents() != null) {
			if (productVO.getProductEvents().size() == 0) {
				session.setProductEventVOs(null);
			} else {
				Collection<ProductEventVO> productEventVos=productVO.getProductEvents();
				Collection<ProductEventVO> eventsVOs=settingTimeChanger(productEventVos);
				session.setProductEventVOs(eventsVOs);
			}
		}

		if (productVO.getRestrictionPaymentTerms() != null) {
			if (productVO.getRestrictionPaymentTerms().size() == 0) {
				session.setSelectedRestrictedPaymentTerms(null);
			} else {
				session.setSelectedRestrictedPaymentTerms(productVO
						.getRestrictionPaymentTerms());
			}
		}
		session.setBooleanForProductIcon("N");
		if (productVO.isProductIconPresent()) {
			session.setBooleanForProductIcon("Y");
				log.log(Log.FINE, "inside setting session", session.getBooleanForProductIcon());
		}
		
		mintainProductForm.setBookingMand(productVO.isBookingMandatory());
		mintainProductForm.setProductName(productVO.getProductName());
		mintainProductForm.setProductCode(productVO.getProductCode());
		mintainProductForm.setRateDefined(productVO.getIsRateDefined());
		// Added as part of CR ICRD-237928
		if(ProductVO.FLAG_YES.equals(productVO.getOverrideCapacity())){
			mintainProductForm.setOverrideCapacity(OVERRIDE_CAPACITY);
		}
		mintainProductForm.setCoolProduct(productVO.isCoolProduct());
		mintainProductForm.setDisplayInPortal(productVO.getIsDisplayInPortal());//Added for ICRD-352832
		//Added for ICRD-352832
		if(productVO.getPrdPriority()!=null && !"0".equals(productVO.getPrdPriority())){
			mintainProductForm.setProductPriority(productVO.getPrdPriority());
		}else
			mintainProductForm.setProductPriority("");  
		String fromDateString = productVO.getStartDate().toDisplayFormat(CALENDAR_DATE_FORMAT);
		String endDateString = productVO.getEndDate().toDisplayFormat(CALENDAR_DATE_FORMAT);
		mintainProductForm.setStartDate(fromDateString);
		mintainProductForm.setEndDate(endDateString);
		mintainProductForm
				.setRestrictedTermsCheck(getCheckedPaymentRestrictions(productVO
						.getRestrictionPaymentTerms()));
		mintainProductForm.setProductDesc(productVO.getDescription());
		mintainProductForm.setAddRestriction(productVO
				.getAdditionalRestrictions());

		mintainProductForm.setDetailDesc(productVO.getDetailedDescription());

		mintainProductForm.setHandlingInfo(productVO.getHandlingInfo());

		mintainProductForm.setRemarks(productVO.getRemarks());

		mintainProductForm.setMaxVolume(Double.toString(productVO.getMaximumVolumeDisplay()));
		mintainProductForm.setMaxWeight(Double.toString(productVO.getMaximumWeightDisplay()));
		mintainProductForm.setMinVolume(Double.toString(productVO.getMinimumVolumeDisplay()));
		mintainProductForm.setMinWeight(Double.toString(productVO.getMinimumWeightDisplay()));
		mintainProductForm.setWeightUnit(productVO.getDisplayWeightCode());
		mintainProductForm.setVolumeUnit(productVO.getDisplayVolumeCode());
		//Added as part of ICRD-232462 begins
		mintainProductForm.setMinDimension(Double.toString(productVO.getMinimumDimensionDisplay()));
		mintainProductForm.setMaxDimension(Double.toString(productVO.getMaximumDimensionDisplay()));
		mintainProductForm.setDimensionUnit(productVO.getDisplayDimensionCode());
		//Added as part of ICRD-232462 ends
		mintainProductForm.setProductStatus(findOneTimeDescription(session
				.getStatusOneTime(), productVO.getStatus()));
		
		mintainProductForm.setProductCategory(productVO.getProductCategory());
		//to set the doctype and docsubtype from the product vo
		if(productVO.getDocumentType() != null 
				&& productVO.getDocumentType().trim().length()>0
				&& productVO.getDocumentSubType() != null
				&& productVO.getDocumentSubType().trim().length()>0){
    		mintainProductForm.setDocType(productVO.getDocumentType());
    		mintainProductForm.setSubType(productVO.getDocumentSubType());
		}else{
    		mintainProductForm.setDocType("");
    		mintainProductForm.setSubType("");
		}
log.log(Log.FINE, "productVO.getProactiveMilestoneEnabled()", productVO.getProactiveMilestoneEnabled());
		if(ProductVO.FLAG_YES.equalsIgnoreCase(productVO.getProactiveMilestoneEnabled())){
			mintainProductForm.setProactiveMilestoneEnabled(true);
		}else{
			mintainProductForm.setProactiveMilestoneEnabled(false);
		}
	}

	/**
	 * Function to Clear the screen
	 * @param session
	 * @param form
	 */
	private void handleClearAction(MaintainProductSessionInterface session,
			MaintainProductForm form) {
		session.setProductTransportModeVOs(null);
		session.setProductPriorityVOs(null);
		session.setProductSccVOs(null);
		session.setProductServiceVOs(null);
		session.setProductEventVOs(null);
		session.setProductCommodityVOs(null);
		session.setProductSegmentVOs(null);
		session.setProductStationVOs(null);
		session.setProductCustGrpVOs(null);
		session.setProductVO(null);
		form.setProductStatus("");
		form.setProductCode("");
		form.setHandlingInfo("");
		form.setDetailDesc("");
		form.setProductDesc("");
		//form.setIcon("");
		form.setProductStatus("");
		form.setRemarks("");
		form.setRestrictedTermsCheck(null);
		form.setCommodityStatus(RESTRICT_FLAG);
		form.setSegmentStatus(RESTRICT_FLAG);
		form.setOriginStatus(RESTRICT_FLAG);
		form.setDestinationStatus(RESTRICT_FLAG);
		form.setCustGroupStatus(RESTRICT_FLAG);
		form.setOverrideCapacity("");
		form.setRateDefined(false);
		form.setCoolProduct(false);
		form.setDisplayInPortal(true);//Added for ICRD-352832
		form.setProductPriority("");
		form.setMinVolume("0.0");
		form.setMaxVolume("0.0");
		form.setMinWeight("0.0");
		form.setMaxWeight("0.0");
		form.setAddRestriction("");
		form.setMode(SAVE_MODE);
	}

	/**
	 * used to validate product name
	 * @param companyCode
	 * @param productName
	 * @param startDate
	 * @param endDate
	 * @return
	 */

	public String validateProductName(String companyCode, String productName,
			LocalDate startDate, LocalDate endDate) {
		String productCode = null;
		try {
			productCode = new ProductDefaultsDelegate().validateProductName(
					companyCode, productName, startDate, endDate);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"<-----------BusinessDelegateException-------------->");
		}
		return productCode;
	}

	/**
	 * This method will the dstatus escription corresponding to the value from
	 * onetime
	 *
	 * @param oneTimeVOs
	 * @param status
	 * @return String
	 */
	private String findOneTimeDescription(Collection<OneTimeVO> oneTimeVOs,
			String status) {
		for (OneTimeVO oneTimeVO : oneTimeVOs) {
			if (status.equals(oneTimeVO.getFieldValue())) {
				return oneTimeVO.getFieldDescription();
			}
		}
		return null;
	}

	/**
	 * Method to get the Payment Terms This is done to keep the payment terms
	 * that are selected checked The String returned will be set to the
	 * paymentTermCheck of the form
	 * @param paymentTerms
	 * @return selectedPaymentTerms
	 */
	private String[] getCheckedPaymentRestrictions(
			Collection<RestrictionPaymentTermsVO> paymentTerms) {
		String[] selectedPaymentTerms = null;
		if (paymentTerms != null) {
			selectedPaymentTerms = new String[paymentTerms.size()];
			int count = 0;
			for (RestrictionPaymentTermsVO vo : paymentTerms) {
				selectedPaymentTerms[count] = vo.getPaymentTerm();

				count++;
			}
		}
		return selectedPaymentTerms;
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

			Collection<String> fieldTypes = new ArrayList<String>();
			fieldTypes.add("products.defaults.paymentterms");
			fieldTypes.add("shared.defaults.weightUnitCodes");
			fieldTypes.add("shared.defaults.volumeUnitCodes");
			fieldTypes.add("products.defaults.status");
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
	 * @return paymentTerms
	 */
	private Collection<RestrictionPaymentTermsVO> getAllPaymentRestrictions(
			Collection<OneTimeVO> restrictedTerms) {

		Collection<RestrictionPaymentTermsVO> paymentTerms = new ArrayList<RestrictionPaymentTermsVO>(); // /Till
																											// here
		for (OneTimeVO oneTime : restrictedTerms) {
			RestrictionPaymentTermsVO termsVO = new RestrictionPaymentTermsVO();
			termsVO.setPaymentTerm(oneTime.getFieldValue());
			termsVO.setIsRestricted(true);
			paymentTerms.add(termsVO);
		}
		return paymentTerms;
	}

	private void setCommodityRestriction(MaintainProductForm form,MaintainProductSessionInterface session){
		if(session.getProductCommodityVOs()!=null){
			RestrictionCommodityVO commodityVo = (RestrictionCommodityVO)((ArrayList<RestrictionCommodityVO>)session.getProductCommodityVOs()).get(0);
			boolean isRestricted = commodityVo.getIsRestricted();
			log
					.log(
							Log.FINE,
							"\n\n\n\n***********isRestricted*****from commodity Vo*********",
							isRestricted);
			if(isRestricted){
				form.setCommodityStatus("Restrict");				
			}else{
				form.setCommodityStatus("Allow");
			}
			log.log(Log.FINE,
					"\n\n\n*************isRestricted**set in form************",
					form.getCommodityStatus());

		}

	}

	private void setSegmentRestriction(MaintainProductForm form,MaintainProductSessionInterface session){
		if(session.getProductSegmentVOs()!=null){
			RestrictionSegmentVO vo = (RestrictionSegmentVO)((ArrayList<RestrictionSegmentVO>)session.getProductSegmentVOs()).get(0);
			boolean isRestricted = vo.getIsRestricted();
			if(isRestricted){
				form.setSegmentStatus("Restrict");
			}else{
				form.setSegmentStatus("Allow");
			}

		}

	}

	private void setStationRestriction(MaintainProductForm form,MaintainProductSessionInterface session){
		if(session.getProductStationVOs()!=null){

			for(RestrictionStationVO vo : session.getProductStationVOs()){
				if(vo.getIsOrigin()){
					if(vo.getIsRestricted()){
						form.setOriginStatus("Restrict");
					}else{
						form.setOriginStatus("Allow");
					}

				}else{
					if(vo.getIsRestricted()){
						form.setDestinationStatus("Restrict");
					}else{
						form.setDestinationStatus("Allow");
					}
				}
			}

		}

	}
	/**
	 *
	 * @param form
	 * @param session
	 */
	private void setCustomerGroupRestriction(MaintainProductForm form,MaintainProductSessionInterface session){
		if(session.getProductCustGrpVOs()!=null){
			RestrictionCustomerGroupVO vo = (RestrictionCustomerGroupVO)((ArrayList<RestrictionCustomerGroupVO>)session.getProductCustGrpVOs()).get(0);
			boolean isRestricted = vo.getIsRestricted();
			if(isRestricted){
				form.setCustGroupStatus("Restrict");
			}else{
				form.setCustGroupStatus("Allow");
			}

		}

	}

	/**
	 * Function to return the display value set
	 * @param list
	 * @param session
	 */
	private void populatePrtyDisplay(Collection<ProductPriorityVO> list,
			MaintainProductSessionInterface session){
		if(list!=null){
			for(ProductPriorityVO vo : list){
				vo.setPriorityDisplay(findOneTimeDescription(session.getPriorityOneTIme(),vo.getPriority()));
			}
		}
	}
	
	/**
	 *
	 * @param eventVos
	 * @return Collection<ProductEventVO>
	 */
	private Collection<ProductEventVO> settingTimeChanger(Collection<ProductEventVO> eventVos){
		log
				.log(
						Log.FINE,
						"\n\n\n**********************eventVos after timechange******************",
						eventVos);
		for(ProductEventVO eventVo:eventVos){
			eventVo.setMaximumTimeStr(findTimeString((int)eventVo.getMaximumTime()));
			eventVo.setMinimumTimeStr(findTimeString((int)eventVo.getMinimumTime()));
			
			/*if(eventVo.getMinimumTime()>60){
			minTime=String.valueOf((eventVo.getMinimumTime())/60);
			}else{
			minTime=String.valueOf((eventVo.getMinimumTime())/100);
			}
			if(eventVo.getMaximumTime()>60){
			maxTime=String.valueOf((eventVo.getMaximumTime())/60);
			}else{
			maxTime=String.valueOf((eventVo.getMaximumTime())/100);
			}

			if(minTime.indexOf('.')!=-1 && (minTime.length() - minTime.indexOf('.')) > 2){
				minTime=minTime.substring(0,minTime.indexOf('.')+3);
               }
           if(maxTime.indexOf('.')!=-1 && (maxTime.length() - maxTime.indexOf('.')) > 2){
			 maxTime=maxTime.substring(0,maxTime.indexOf('.')+3);
          }

			
           minimumTime=minTime.replace('.',':');
			maximumTime=maxTime.replace('.',':');*/
		}
		log
				.log(
						Log.FINE,
						"\n\n\n**********************eventVos after timechange******************",
						eventVos);
		return  eventVos;
	}
	
	/**
	 * Method to return time string getting number of minutes as input
	 * @param minutes
	 * @return
	 */
	private String findTimeString(int minutes){
		StringBuilder timeString = new StringBuilder();
		int hours = 0;
		int mins = 0;
		if(minutes>60){
			hours = minutes/60;
			mins = minutes%60;
			if(hours<10){
				timeString.append(ZERO);
			}
			timeString.append(String.valueOf(hours));
			timeString.append(TIME_SEPERATOR);
			if(mins<10){
				timeString.append(ZERO);
			}
			timeString.append(String.valueOf(mins));				
		}else{
			timeString.append(ZERO).append(ZERO);
			timeString.append(TIME_SEPERATOR);
			if(minutes<10){
				timeString.append(ZERO);
			}
			timeString.append(String.valueOf(minutes));
		}
		return timeString.toString();
	}

	
	private ArrayList<ErrorVO> validateForm(MaintainProductForm form){
		ArrayList<ErrorVO> errors = null;
		if("".equals(form.getProductName())){
			log.log(Log.FINE,"***eneterd validateForm********");
			errors = new ArrayList<ErrorVO>();
			ErrorVO error = new ErrorVO("products.defaults.enterproductname");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		return errors;
	}
	
	private ArrayList<ErrorVO> validateDate(MaintainProductForm form){
		ArrayList<ErrorVO> errors = null;
		//ErrorVO error = null;   
		String fromDate = "";
		String toDate = "";	
		fromDate = form.getStartDate().trim();
		toDate =   form.getEndDate().trim();
		if(!(EMPTY.equals(fromDate)) && !(EMPTY.equals(toDate))) {
			log.log(Log.FINE, "***fromDate****", fromDate);
			log.log(Log.FINE, "***toDate****", toDate);
			if(!fromDate.equals(toDate)){
				if (!DateUtilities.isLessThan(fromDate, toDate,"dd-MMM-yyyy")) {
					errors = new ArrayList<ErrorVO>();
					ErrorVO error = new ErrorVO("products.defaults.fromdatlatrthanenddat");
					log
							.log(Log.FINE, "***invalid date$$$$$$$$$********",
									error);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
			}
		}
		return errors;
	}
	
	private Collection<ProductParamterVO> findAllProductParametersDetails(String companyCode){
		Collection<ProductParamterVO> ProductParamterVOs=null;
		try {
			ProductParamterVOs= new ProductDefaultsDelegate().findAllProductParameters(companyCode);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"<-----------------BusinessDelegateException------------->");
		}
		return ProductParamterVOs;
	}
}
