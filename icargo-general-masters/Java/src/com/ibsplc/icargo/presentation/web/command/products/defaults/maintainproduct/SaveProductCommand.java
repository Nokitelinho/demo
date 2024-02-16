/*
 * SaveProductCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Collection;
import java.util.StringTokenizer;

import org.apache.struts.upload.FormFile;

import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductParamterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCommodityVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCustomerGroupVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionPaymentTermsVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;




import com.ibsplc.icargo.framework.model.ImageModel;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;

import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 *
 * @author A-1754
 *
 */
public class SaveProductCommand extends BaseCommand {

	//private static final String COMPANY_CODE = "AV";

	private static final String OPERATION_FLAG_INSERT = "I";

	private static final String OPERATION_FLAG_UPDATE = "U";

	private static final String OPERATION_FLAG_DELETE = "D";

	private static final String ALLOW_FLAG = "Allow";

	private static final String RESTRICT_FLAG = "Restrict";

	private static final String SAVE_MODE = "save";

	private static final String MODIFY_MODE = "modify";

	private static final String CREATE_MODE = "saveas";

	private static final String SUCCESS_MODE = "success";

	private static final String RESTRICT = "Restrict";

	private static final String ALLOW = "Allow";
	
	private static final String OVERRIDE_CAPACITY= "on";

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	//private static final String NULL_STRING = "";

	/**
	 * The execute method in BaseCommand
	 *
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("SaveProductCommand","execute");
		MaintainProductForm maintainProductForm = (MaintainProductForm) invocationContext.screenModel;

		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");

		ProductVO productVO = new ProductVO();
		Collection<ErrorVO> errors = null;
		Collection<SubProductVO> subProductVOs = null;
		errors = validateForm(maintainProductForm,session);
		if (errors != null && errors.size() > 0) { 
			
			
			invocationContext.addAllError(errors);
			invocationContext.target = "screenload_failure";
			return;
		}
		else if (!validateSegments(session)) {
			
			maintainProductForm.setCanSave("N");
			ErrorVO error = null;
			Object[] obj = { "Segment" };
			error = new ErrorVO("products.defaults.segcontainsinvalidstns", obj); // Invalid Segment
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = "screenload_failure";
		} else if (isDuplictaeSegmet(session)) {
			log.log(Log.FINE,"\n\n\n\n\n\n\n\n\n\n**********************!isDuplictaeSegmet(sessione-------------");
			maintainProductForm.setCanSave("N");
			ErrorVO error = null;
			Object[] obj = { "Segment" };
			error = new ErrorVO("products.defaults.duplicatesegment", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = "screenload_failure";
		} else {
			
			productVO = getAllProductDetails(maintainProductForm, session);
			log
					.log(
							Log.FINE,
							"\n\n\n\n\n\n\n\n\n\n**********************fproductVO from client to save-------------",
							productVO);
			try {
				subProductVOs = saveProductDetails(productVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				maintainProductForm.setCanSave("N");
				invocationContext.addAllError(errors);
				invocationContext.target = "screenload_failure";
			} else {
				for(SubProductVO vo: subProductVOs){
					vo.setProductPriority(findOneTimeDescription(session.getPriorityOneTIme(),vo.getProductPriority()));
				}
				if(subProductVOs!=null && subProductVOs.size()==0)	{
					subProductVOs = null;
				}
				session.setSubProductVOs(subProductVOs);

				ErrorVO error = null;
				Object[] obj = { "Save" };
				error = new ErrorVO("products.defaults.productsaved", obj);// Saved Successfully
				error.setErrorDisplayType(ErrorDisplayType.INFO);
				errors.add(error);
				invocationContext.addAllError(errors);
				maintainProductForm.setMode(SUCCESS_MODE);
				maintainProductForm.setCanSave("N");
				maintainProductForm.setProductName("");
				String fromDateString = DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT);
				maintainProductForm.setStartDate(fromDateString);
				maintainProductForm.setEndDate("");
				handleClearAction(session, maintainProductForm);
				invocationContext.target = "screenload_success";
			}
			
		}
		
	}

	/**
	 * This method validates if the segments enterd by the user contains invalid
	 * stations
	 * @param session
	 * @return boolean
	 */
	private boolean validateSegments(MaintainProductSessionInterface session) {
		if (session.getProductSegmentVOs() != null) {
			//Changed by Dinoop
			Map<String,AirportValidationVO> validStations = null;
			Collection<RestrictionSegmentVO> segments = session
					.getProductSegmentVOs();
			HashSet<String> stations = new HashSet<String>();

			for (RestrictionSegmentVO vo : segments) {
				if(!ProductVO.OPERATION_FLAG_DELETE.equals(vo.getOperationFlag())){
					if (!stations.contains(vo.getOrigin())) {
						stations.add(upper(vo.getOrigin()));
					}
					if (!stations.contains(vo.getDestination())) {
						stations.add(upper(vo.getDestination()));
					}
				}
			}
			try {

				if(stations!=null && stations.size()>0){
					validStations = new AreaDelegate().validateAirportCodes(
						getApplicationSession().getLogonVO().getCompanyCode(), stations);
				}
			} catch (BusinessDelegateException businessDelegateException) {
				log.entering("validateSegments","<------------------------------ Inside Business Delegate Exception--------------------------->");
				return false;
			}

		}
		return true;
	}

	/**
	 * This method validates if the user has enterd duplicate segments
	 * @param session
	 * @return
	 */
	private boolean isDuplictaeSegmet(MaintainProductSessionInterface session) {
		if (session.getProductSegmentVOs() != null) {

			ArrayList<RestrictionSegmentVO> segments  = new ArrayList<RestrictionSegmentVO>();
			for(RestrictionSegmentVO vo : session.getProductSegmentVOs()){
				if(!ProductVO.OPERATION_FLAG_DELETE.equals(vo.getOperationFlag())){
					segments.add(vo);
				}
			}

			ArrayList<RestrictionSegmentVO> segmentList = new ArrayList<RestrictionSegmentVO>(segments);
			int segSize = segments.size();
			int segListSize = segmentList.size();
			for (int i = 0; i < segSize; i++) {
				RestrictionSegmentVO firstVO = (RestrictionSegmentVO) segments
						.get(i);
				for (int j = i + 1; j < segListSize; j++) {
					RestrictionSegmentVO secondVO = (RestrictionSegmentVO) segmentList
							.get(j);
					if (firstVO.getOrigin().equalsIgnoreCase(secondVO.getOrigin())
								&& firstVO.getDestination().equals(
										secondVO.getDestination())) {
							return true;
						}

				}
			}

		}
		return false;
	}

	/**
	 * The function to set the details of the product VO for saving/modifying
	 * @param form
	 * @param session
	 * @return
	 */
	private ProductVO getAllProductDetails(MaintainProductForm form,
			MaintainProductSessionInterface session) {

		ProductVO productVO = new ProductVO();
		Collection<ProductParamterVO> productParamters=new ArrayList<>();
		ProductVO productVOFromSession = session.getProductVO();
		productVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());

		productVO.setProductCode(form.getProductCode());
		productVO.setProductName(form.getProductName().toUpperCase());
		productVO.setDescription(form.getProductDesc());
		// Added as part of CR ICRD-237928 by A-8154
		if(OVERRIDE_CAPACITY.equalsIgnoreCase(form.getOverrideCapacity())){
			productVO.setOverrideCapacity(ProductVO.FLAG_YES);
		}else{
			productVO.setOverrideCapacity(ProductVO.FLAG_NO);
		}
		//Added by A-5353 for ICRD-85791
		productVO.setIsRateDefined(form.isRateDefined());
		productVO.setCoolProduct(form.isCoolProduct());
		productVO.setDisplayInPortal(form.isDisplayInPortal());//Added for ICRD-352832
		if(form.getProductPriority()!=null && !"0".equals(form.getProductPriority())){
			productVO.setPrdPriority(form.getProductPriority());
		}else{
			productVO.setPrdPriority("");    
		}
		productVO.setBookingMandatory(form.isBookingMand());
		productVO.setProductCategory(form.getProductCategory());

		if(("Y").equals(session.getBooleanForProductIcon())){
			productVO.setProductIconPresent(true);
		}
		else{
			productVO.setProductIconPresent(false);
		}

		// Convert Start date and End Date to Local date nad set
		LocalDate localStartDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
		productVO.setStartDate(localStartDate.setDate(form.getStartDate()));
		LocalDate localEndDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
		productVO.setEndDate(localEndDate.setDate(form.getEndDate()));
		//productVO.setProductIcon(form.getIcon());
//		log.log(Log.FINE,"The formfile is data is "+productVO.getIconForProduct());

		FormFile formFile = form.getIcon();
		log.log(Log.FINE, "The formFile data is ", formFile);
		log.log(Log.FINE, "The formfile is data is ", formFile.getFileSize());
			byte[] picDetails = null;
			/*BufferedReader br =
						new BufferedReader(new FileReader(uploadFilePath));
		    String str = null ;
			StringBuffer buffer = new StringBuffer() ;
			while((str = br.readLine())!=null){
			    buffer.append(str) ;
			}
			ruleXmlDetails = buffer.toString().getBytes();*/

			ImageModel image = null;

			try{
			if(formFile.getFileSize()>0){
				image = new ImageModel();
				log.log(Log.FINE, "inside getfilesize", formFile.getFileSize());
				image.setName(formFile.getFileName());
				log.log(Log.FINE, "\n\n****************formFile.getFileName()",
						formFile.getFileName());
				image.setContentType(formFile.getContentType());
				log.log(Log.FINE,
						"\n\n***************formFile.getContentType()",
						formFile.getContentType());
				image.setData(formFile.getFileData());
/*				log.log(Log.FINE,"\n\n****************formFile.getFileData() "+
						formFile.getFileData());*/

			}
			productVO.setImage(image);
			log.log(Log.FINE, "The formfile is data is ", productVO.getImage());

		}
		catch (FileNotFoundException e) {
			// To be reviewed Auto-generated catch block
			e.getMessage();
		} catch (IOException e) {
			// To be reviewed Auto-generated catch block
			e.getMessage();
		}

		//Calendar lastUpdateDate =Calendar.getInstance(getApplicationSession().getLogonVO().getTimeZone());
		productVO.setDetailedDescription(form.getDetailDesc());
		productVO.setHandlingInfo(form.getHandlingInfo());
		productVO.setRemarks(form.getRemarks());

		if (!"".equals(form.getMaxVolume())) {
			double maxVolume = Double.parseDouble(form.getMaxVolume());
			productVO.setMaximumVolumeDisplay(maxVolume);
			productVO.setMaximumVolume(maxVolume);
		}
		if (!"".equals(form.getMinVolume())) {
			double minVolume = Double.parseDouble(form.getMinVolume());
			productVO.setMinimumVolumeDisplay(minVolume);
			productVO.setMinimumVolume(minVolume);
		}
		if (!"".equals(form.getMaxWeight())) {
			double maxWeight = Double.parseDouble(form.getMaxWeight());
			productVO.setMaximumWeightDisplay(maxWeight);
			productVO.setMaximumWeight(maxWeight);
		}
		if (!"".equals(form.getMinWeight())) {
			double minWeight = Double.parseDouble(form.getMinWeight());
			productVO.setMinimumWeightDisplay(minWeight);
			productVO.setMinimumWeight(minWeight);
		}
		//Added as part of ICRD-232462 begins
		if (!"".equals(form.getMaxDimension())) {
			double maxDimension = Double.parseDouble(form.getMaxDimension());
			productVO.setMaximumDimensionDisplay(maxDimension);
			productVO.setMaximumDimension(maxDimension);
		}
		if (!"".equals(form.getMinDimension())) {
			double minDimension = Double.parseDouble(form.getMinDimension());
			productVO.setMinimumDimensionDisplay(minDimension);
			productVO.setMinimumDimension(minDimension);
		}
		//Added as part of ICRD-232462 ends
		
		productVO.setAdditionalRestrictions(form.getAddRestriction());
		productVO.setDisplayVolumeCode(form.getVolumeUnit());
		productVO.setDisplayWeightCode(form.getWeightUnit());
		productVO.setDisplayDimensionCode(form.getDimensionUnit());
		if (form.getMode().equals(SAVE_MODE)
				|| form.getMode().equals(CREATE_MODE)) {
			productVO.setOperationFlag(OPERATION_FLAG_INSERT);
			productVO.setLastUpdateDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
			productVO.setLastUpdateUser(getApplicationSession().getLogonVO().getUserId());
			//productparameterVo.setOperationalFlag(OPERATION_FLAG_INSERT);
		} else {
			productVO.setStatus(findOneTimeCode(session.getStatusOneTime(),
					form.getProductStatus())); // change
			productVO.setOperationFlag(OPERATION_FLAG_UPDATE);
			productVO.setLastUpdateDate(productVOFromSession.getLastUpdateDate());
			productVO.setLastUpdateUser(getApplicationSession().getLogonVO().getUserId());
			//productparameterVo.setOperationalFlag(OPERATION_FLAG_UPDATE);
		}
		productVO.setTransportMode(session.getProductTransportModeVOs());
		productVO.setPriority(session.getProductPriorityVOs());
		productVO
				.setRestrictionCommodity(getAllSelectedCommodity(form, session));
		productVO.setProductScc(session.getProductSccVOs());
		productVO.setRestrictionPaymentTerms(getAllRestrictedPaymentTerms(form,
				session));
		productVO.setRestrictionSegment(getAllSelectedSegments(form, session));
		productVO.setRestrictionCustomerGroup(getAllSelectedCustomerGroup(form,
				session));
		productVO.setRestrictionStation(getAllRestrictedStation(form, session));
		productVO.setProductEvents(session.getProductEventVOs());
		productVO.setServices(session.getProductServiceVOs());
		productVO.setProductParamters(session.getProductParameterVOs());
		
		
		//to update the docType and subtype details
		productVO.setDocumentType(form.getDocType());
		productVO.setDocumentSubType(form.getSubType());

		//to update the Enable Proactive Milestone Management
		//@author a-1944
		if(form.isProactiveMilestoneEnabled())
		{
			productVO.setProactiveMilestoneEnabled(ProductVO.FLAG_YES);
		}
		else
		{
			productVO.setProactiveMilestoneEnabled(ProductVO.FLAG_NO);
		}
		//Added by A-8377 for ICRD-311816
		/*if(form.getParamCode()!=null)  {  
			int size = form.getParamCode().length;
                for (int index = 0; index < size; index++){
        //ProductParamterVO productparameterVo = new ProductParamterVO();      	
        productparameterVo.setParameterCode(form.getParamCode()[index]);*/
		//Added as part of bug ICRD-301596 by A-5526 -null check
		if(form.getParamCode()!=null)  {  
			int size = form.getParamCode().length;
                for (int index = 0; index < size; index++){
        ProductParamterVO productparameterVo = new ProductParamterVO();      	
        productparameterVo.setParameterCode(form.getParamCode()[index]);
		productparameterVo.setProductCode(form.getProductCode());
        productparameterVo.setParameterValue(form.getParamValue()[index]);
        if (form.getMode().equals(SAVE_MODE)
				|| form.getMode().equals(CREATE_MODE)) {
        	productparameterVo.setOperationalFlag(OPERATION_FLAG_INSERT);
        }
        else{
        	productparameterVo.setOperationalFlag(OPERATION_FLAG_UPDATE);
        }
		productParamters.add(productparameterVo);		
        }
		productVO.setProductParamters(productParamters);
		}
		return productVO;
	}

	/**
	 * The function to return the selected commodities
	 * @param form
	 * @param session
	 * @return
	 */

	private Collection<RestrictionCommodityVO> getAllSelectedCommodity(
			MaintainProductForm form, MaintainProductSessionInterface session) {
		Collection<RestrictionCommodityVO> oldList = session
				.getProductCommodityVOs();
		Collection<RestrictionCommodityVO> newList = new ArrayList<RestrictionCommodityVO>();
		boolean isRestricted = true;
		if (oldList != null) {
			String status = form.getCommodityStatus();
			if (status.equals(ALLOW_FLAG)) {
				isRestricted = false;
			}
			for (RestrictionCommodityVO vo : oldList) {
				if ((vo.getOperationFlag() == null)
						&& (vo.getIsRestricted() != isRestricted)) {
					vo.setOperationFlag(OPERATION_FLAG_DELETE);
					RestrictionCommodityVO newVO = new RestrictionCommodityVO();
					newVO.setCommodity(vo.getCommodity());
					newVO.setIsRestricted(isRestricted);
					newVO.setOperationFlag(OPERATION_FLAG_INSERT);
					newList.add(newVO);
				} else {
					vo.setIsRestricted(isRestricted);
				}
				newList.add(vo);
			}
		}
	/*	ArrayList<RestrictionCommodityVO> newSetOfList = new ArrayList<RestrictionCommodityVO>();
		ArrayList<RestrictionCommodityVO> firstList = (ArrayList<RestrictionCommodityVO>)newList;
		ArrayList<RestrictionCommodityVO> secondList = new ArrayList<RestrictionCommodityVO>(newList);*/

		/*for(RestrictionCommodityVO newVO : firstList){
			boolean isPresent = false;
			for(RestrictionCommodityVO oldVO : secondList){
				if(oldVO.getCommodity().equals(newVO.getCommodity())){
					isPresent = true;
				}

			}
			if(isPresent){
				newVO.setOperationFlag(null);
			}
			newSetOfList.add(newVO);
		}*/
	/*	RestrictionCommodityVO firstVO = null;
		for (int i = 0; i < firstList.size(); i++) {
			 firstVO = (RestrictionCommodityVO) firstList.get(i);

			boolean isPresent = false;
			inner:
			for (int j = i + 1; j < secondList.size(); j++) {
				RestrictionCommodityVO secondVO = (RestrictionCommodityVO) secondList.get(j);
				if (firstVO.getCommodity().equals(secondVO.getCommodity())
						&& firstVO.getIsRestricted()== secondVO.getIsRestricted()){
					isPresent = true;
					break inner;
					}

			}
			if(isPresent){
				firstVO.setOperationFlag(null);
			}
			newSetOfList.add(firstVO);
		}*/
	
	//	return newSetOfList;
		return newList;

	}

	/**
	 * The function to get all the selected restricted/allowed cuatomer group
	 * @param form
	 * @param session
	 * @return
	 */
	private Collection<RestrictionCustomerGroupVO> getAllSelectedCustomerGroup(
			MaintainProductForm form, MaintainProductSessionInterface session) {

		Collection<RestrictionCustomerGroupVO> oldList = session
				.getProductCustGrpVOs();
		Collection<RestrictionCustomerGroupVO> newList = new ArrayList<RestrictionCustomerGroupVO>();
		boolean isRestricted = true;
		if (oldList != null) {
			String status = form.getCustGroupStatus();
			if (status.equals(ALLOW_FLAG)) {
				isRestricted = false;
			}
			for (RestrictionCustomerGroupVO vo : oldList) {
				if ((vo.getOperationFlag() == null)
						&& (vo.getIsRestricted() != isRestricted)) {
					vo.setOperationFlag(OPERATION_FLAG_DELETE);
					RestrictionCustomerGroupVO newVO = new RestrictionCustomerGroupVO();
					newVO.setCustomerGroup(vo.getCustomerGroup());
					newVO.setIsRestricted(isRestricted);
					newVO.setOperationFlag(OPERATION_FLAG_INSERT);
					newList.add(newVO);
				} else {
					vo.setIsRestricted(isRestricted);
				}
				newList.add(vo);
			}
		}
		return newList;

	}

	/**
	 * The function to return the restricted/allowed segments
	 * @param form
	 * @param session
	 * @return
	 */
	private Collection<RestrictionSegmentVO> getAllSelectedSegments(
			MaintainProductForm form, MaintainProductSessionInterface session) {
		Collection<RestrictionSegmentVO> oldList = session
				.getProductSegmentVOs();
		Collection<RestrictionSegmentVO> newList = new ArrayList<RestrictionSegmentVO>();

		boolean isRestricted = true;
		int count = 0;
		String segmentFromForm = "";
		if (oldList != null) {
			String status = form.getSegmentStatus();
			if (status.equals(ALLOW_FLAG)) {
				isRestricted = false;
			}
			log
					.log(
							Log.FINE,
							"\n\n\n\n\n\n\n\n\n\n**************UPDATED VO TO DELEGATE-------------",
							oldList);
			for (RestrictionSegmentVO vo : oldList) {
				if (OPERATION_FLAG_UPDATE.equals(vo.getOperationFlag())) {
					segmentFromForm = form.getSegment()[count];
					if (segmentFromForm.equalsIgnoreCase(vo.getOrigin()+"-"+vo.getDestination()) && (vo.getIsRestricted()==isRestricted)) {
						vo.setOperationFlag(null);	
					} else {
					vo.setOperationFlag(OPERATION_FLAG_UPDATE);
					}
					vo.setIsRestricted(isRestricted);
					
				} else if ((vo.getOperationFlag() == null)
						&& (vo.getIsRestricted() != isRestricted)) {
					vo.setOperationFlag(OPERATION_FLAG_DELETE);
					RestrictionSegmentVO newVO = new RestrictionSegmentVO();
					newVO.setOrigin(upper(vo.getOrigin()));
					newVO.setDestination(upper(vo.getDestination()));
					//newVO.setOrigin(vo.getOrigin());
					newVO.setIsRestricted(isRestricted);
					newVO.setOperationFlag(OPERATION_FLAG_INSERT);
					newList.add(newVO);
					log
							.log(
									Log.FINE,
									"\n\n\n\n\n\n\n\n\n\n**************newVO-------------",
									newVO);
				} else {
					vo.setIsRestricted(isRestricted);
				}
				newList.add(vo);
				log
						.log(
								Log.FINE,
								"\n\n\n\n\n\n\n\n\n\n**************UPDATED VO TO DELEGATE-------------",
								newList);
				count++;
			}
			Collection<RestrictionSegmentVO> afterListingList = session
					.getSegmentAfterListing();
			if (afterListingList != null) {
				for (RestrictionSegmentVO oldVO : afterListingList) {

					if (OPERATION_FLAG_DELETE.equals(oldVO.getOperationFlag())) {
						newList.add(oldVO);
					}
				}
			}

		}
		return newList;

	}

	/**
	 * The function to get the selected restricted/allowed stations
	 * @param form
	 * @param session
	 * @return
	 */
	private Collection<RestrictionStationVO> getAllRestrictedStation(
			MaintainProductForm form, MaintainProductSessionInterface session) {
		Collection<RestrictionStationVO> restrictedStations = session
				.getProductStationVOs();
		Collection<RestrictionStationVO> newStations = new ArrayList<RestrictionStationVO>();
		// For Origin
		boolean isOriginRestricted = true;

		String status = form.getOriginStatus();
		if (status.equals(ALLOW_FLAG)) {
			isOriginRestricted = false;
		}

		// for destination

		boolean isDestnRestricted = true;

		String destStatus = form.getDestinationStatus();
		if (destStatus.equals(ALLOW_FLAG)) {
			isDestnRestricted = false;
		}
		if (restrictedStations != null) {
			for (RestrictionStationVO stationVo : restrictedStations) {
				if (stationVo.getIsOrigin()) {
					if ((stationVo.getOperationFlag() == null)
							&& (stationVo.getIsRestricted() != isOriginRestricted)) {
						stationVo.setOperationFlag(OPERATION_FLAG_DELETE);
						RestrictionStationVO newVO = new RestrictionStationVO();
						newVO.setStation(stationVo.getStation());
						newVO.setIsOrigin(stationVo.getIsOrigin());
						newVO.setIsRestricted(isOriginRestricted);
						newVO.setOperationFlag(OPERATION_FLAG_INSERT);
						newStations.add(newVO);
					} else {
						stationVo.setIsRestricted(isOriginRestricted);
					}
				} else {
					if ((stationVo.getOperationFlag() == null)
							&& (stationVo.getIsRestricted() != isDestnRestricted)) {
						stationVo.setOperationFlag(OPERATION_FLAG_DELETE);
						RestrictionStationVO newVO = new RestrictionStationVO();
						newVO.setStation(stationVo.getStation());
						newVO.setIsOrigin(stationVo.getIsOrigin());
						newVO.setIsRestricted(isDestnRestricted);
						newVO.setOperationFlag(OPERATION_FLAG_INSERT);
						newStations.add(newVO);
					} else {
						stationVo.setIsRestricted(isDestnRestricted);
					}
				}
				newStations.add(stationVo);
			}
		}
		return newStations;

	}

	/**
	 * The function to get the selected restricted/allowed payment terms
	 * @param form
	 * @param session
	 * @return
	 */
	private Collection<RestrictionPaymentTermsVO> getAllRestrictedPaymentTerms(
			MaintainProductForm form, MaintainProductSessionInterface session) {
		Collection<RestrictionPaymentTermsVO> oldList = session
				.getSelectedRestrictedPaymentTerms();
		Collection<RestrictionPaymentTermsVO> newList = new ArrayList<RestrictionPaymentTermsVO>();
		String[] selectedRestriction = form.getRestrictedTermsCheck();
		if (selectedRestriction != null) {
			boolean isPresent = false;
			RestrictionPaymentTermsVO newVO = null;
			if (oldList != null) {
				for (RestrictionPaymentTermsVO vo : oldList) {
					isPresent = false;
					for (int i = 0; i < selectedRestriction.length; i++) {
						if (vo.getPaymentTerm().equals(selectedRestriction[i])) {
							isPresent = true;
						}
					}
					if (!isPresent) {
						vo.setOperationFlag(OPERATION_FLAG_DELETE);
						newList.add(vo);
					}
					if (isPresent) {
						newList.add(vo);
					}
				}

				for (int i = 0; i < selectedRestriction.length; i++) {
					isPresent = false;
					for (RestrictionPaymentTermsVO paymentVO : oldList) {
						if (paymentVO.getPaymentTerm().equals(
								selectedRestriction[i])) {
							isPresent = true;
						}
					}
					if (!isPresent) {
						newVO = new RestrictionPaymentTermsVO();
						newVO.setPaymentTerm(selectedRestriction[i]);
						newVO.setOperationFlag(OPERATION_FLAG_INSERT);
						newVO.setIsRestricted(true);
						newList.add(newVO);
					}
				}
			} else {
				newList = getRestrictedPaymentTerms(selectedRestriction);
			}

		} else {

			if (oldList != null) {
				for (RestrictionPaymentTermsVO paymentVO : oldList) {
					paymentVO.setOperationFlag(OPERATION_FLAG_DELETE);
					newList.add(paymentVO);
				}
			}
		}
		return newList;

	}

	/**
	 * This method will the dstatus escription corresponding to the value from
	 * onetime
	 *
	 * @param oneTimeVOs
	 * @param status
	 * @return String
	 */
	private String findOneTimeCode(Collection<OneTimeVO> oneTimeVOs,
			String status) {
		for (OneTimeVO oneTimeVO : oneTimeVOs) {
			if (status.equals(oneTimeVO.getFieldDescription())) {
				return oneTimeVO.getFieldValue();
			}
		}
		return null;
	}

	/**
	 * Function to convert the selcted payment terms in the form to
	 * RestrictionPaymentTermsVO
	 *
	 * @param paymentTerms
	 * @return Collection<RestrictionPaymentTermsVO> restrictionPaymentTermsVOs
	 */
	private Collection<RestrictionPaymentTermsVO> getRestrictedPaymentTerms(
			String[] paymentTerms) {
		Collection<RestrictionPaymentTermsVO> restrictionTermsVOs = new ArrayList<RestrictionPaymentTermsVO>();
		for (int i = 0; i < paymentTerms.length; i++) {
			RestrictionPaymentTermsVO restrictionPaymentTermsVO = new RestrictionPaymentTermsVO();

			restrictionPaymentTermsVO.setPaymentTerm(paymentTerms[i]);
			restrictionPaymentTermsVO.setOperationFlag(OPERATION_FLAG_INSERT);
			restrictionPaymentTermsVO.setIsRestricted(true);
			restrictionTermsVOs.add(restrictionPaymentTermsVO);

		}
		return restrictionTermsVOs;

	}

	/**
	 * used for creating or modifying a product
	 * @param productVo
	 * @return
	 * @throws BusinessDelegateException
	 */

	public Collection<SubProductVO> saveProductDetails(ProductVO productVo)
			throws BusinessDelegateException {
		Collection<SubProductVO> subProductVOs = null;
		return new ProductDefaultsDelegate().saveProductDetails(productVo);

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
		//Added by A-5277 for the BUG - 28316
		session.setSegmentAfterListing(null);
		session.setSelectedRestrictedPaymentTerms(null);
		session.setProductParameterVOs(null);
		session.setProductVO(null);
		form.setProductStatus("");
		form.setProductCode("");
		form.setHandlingInfo("");
		form.setDetailDesc("");
		form.setProductDesc("");
		//form.setIcon(null);
		form.setProductStatus("");
		form.setRemarks("");
		form.setRestrictedTermsCheck(null);
		form.setCommodityStatus(RESTRICT_FLAG);
		form.setSegmentStatus(RESTRICT_FLAG);
		form.setOriginStatus(RESTRICT_FLAG);
		form.setDestinationStatus(RESTRICT_FLAG);
		form.setCustGroupStatus(RESTRICT_FLAG);
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
		form.setDocType("");
		form.setSubType("");
		form.setBookingMand(false);
		form.setProactiveMilestoneEnabled(false);
		form.setOverrideCapacity("");
		form.setMinDimension("0.0");
		form.setMaxDimension("0.0");
	}

	private Collection<ErrorVO> validateForm(MaintainProductForm form,
			MaintainProductSessionInterface session) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		boolean isValid = true;
		boolean isExportValid = true;
		boolean isImportValid = true;
		boolean isExternalInternal = true;
		boolean isNotDuplicate=true;
		if ("".equals(form.getProductName())) {
			isValid = false;
			error = new ErrorVO("products.defaults.enterproductname");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if ("".equals(form.getStartDate())) {
			isValid = false;
			error = new ErrorVO("products.defaults.inavlidstartdate");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		else{
			if (!DateUtilities.isValidDate(form.getStartDate(),
			"dd-MMM-yyyy")) {
			log.log(Log.FINE,"inside isValidDate from*****************************************");
			error = new ErrorVO("products.defaults.fromdatenotvalid");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			}
		}
		if (form.getEndDate().trim().length()==0) {
			isValid = false;
			error = new ErrorVO("products.defaults.inavlidenddate");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}else{

			if (!DateUtilities.isValidDate(form.getEndDate(),
			"dd-MMM-yyyy")) {
				log.log(Log.FINE,"\ninside DateUtilities.isValidDate *****************");
				error = new ErrorVO("products.defaults.todatenotvalid");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}


		if("".equals(form.getProductDesc())){
			error = new ErrorVO("products.defaults.invaliddescription");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
//		log.log(Log.FINE,"\nform.getTransportMode()****************" +form.getTransportMode());
		if (form.getTransportMode() == null) {
			isValid = false;
			error = new ErrorVO("products.defaults.selecttramode");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if (form.getPriority() == null) {
			isValid = false;
			error = new ErrorVO("products.defaults.selectpriority");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if (form.getSccCode() == null) {
			isValid = false;
			error = new ErrorVO("products.defaults.selectscc");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		/*if (form.getProductServices() == null) {
			isValid = false;
			error = new ErrorVO("products.defaults.selectservice");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}*/

		if(!form.getStartDate().equals(form.getEndDate()) && isValid){
			log.log(Log.FINE,"\ninside Going to check if the date is before date**********");
			if (!DateUtilities.isLessThan(form.getStartDate(), form.getEndDate(),
					"dd-MMM-yyyy")) {
				isValid = false;
				error = new ErrorVO("products.defaults.startdategreaterthancurrentdate");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}
		if (form.getSegment() != null) {
		 /*	if (RESTRICT.equals(form.getSegmentStatus())) {
				if (ALLOW.equals(form.getOriginStatus())) {
					if (form.getOrigin() != null) {
						for (int j = 0; j < form.getOrigin().length; j++) {
							for (int i = 0; i < form.getSegment().length; i++) {
								StringTokenizer tok = new StringTokenizer(form
										.getSegment()[i], "-");
								int count = 0;
								String[] seperateToken = new String[2];
								seperateToken[0] = "";
								seperateToken[1] = "";
								while (tok.hasMoreTokens()) {
									seperateToken[count] = tok.nextToken();
									System.out
											.println("Seperating hidden Fields External::"
													+ count
													+ "---->"
													+ seperateToken[count]);
									count++;
								}
								if(!ProductVO.OPERATION_FLAG_DELETE.equals(form.getSegmentOperationFlag()[i])){
								if (form.getOrigin()[j]
										.equals(seperateToken[0])) {
									isValid = false;
									Object[] obj = { "Origin" };
									error = new ErrorVO("products.defaults.segviolorgallow", obj);
									error.setErrorDisplayType(ErrorDisplayType.ERROR);
									errors.add(error);
									break;
								}
								}
							}
						}
					}
				}

				if (ALLOW.equals(form.getDestinationStatus())) {
					if (form.getDestination() != null) {
						for (int j = 0; j < form.getDestination().length; j++) {
							for (int i = 0; i < form.getSegment().length; i++) {
								StringTokenizer tok = new StringTokenizer(form
										.getSegment()[i], "-");
								int count = 0;
								String[] seperateToken = new String[2];
								seperateToken[0] = "";
								seperateToken[1] = "";
								while (tok.hasMoreTokens()) {
									seperateToken[count] = tok.nextToken();
									System.out
											.println("Seperating hidden Fields External::"
													+ count
													+ "---->"
													+ seperateToken[count]);
									count++;
								}
								if(!ProductVO.OPERATION_FLAG_DELETE.equals(form.getSegmentOperationFlag()[i])){
								if (form.getDestination()[j]
										.equals(seperateToken[1])) {
									isValid = false;
									Object[] obj = { "Destination" };
									error = new ErrorVO("products.defaults.segvioldstallow", obj);
									error.setErrorDisplayType(ErrorDisplayType.ERROR);
									errors.add(error);
									break;
								}
								}
							}
						}
					}
				}

			} else*/
			if (ALLOW.equals(form.getSegmentStatus())) {
				if (RESTRICT.equals(form.getOriginStatus())) {
					if (form.getOrigin() != null) {
						int orgLen=form.getOrigin().length;
						int segLen=form.getSegment().length;
						outer:
						for (int j = 0; j < orgLen ; j++) {
							for (int i = 0; i < segLen; i++) {
								StringTokenizer tok = new StringTokenizer(form
										.getSegment()[i], "-");
								int count = 0;
								String[] seperateToken = new String[2];
								seperateToken[0] = "";
								seperateToken[1] = "";
								while (tok.hasMoreTokens()) {
									seperateToken[count] = tok.nextToken();
									count++;
								}
								if(!ProductVO.OPERATION_FLAG_DELETE.equals(form.getSegmentOperationFlag()[i])){
								if (form.getOrigin()[j]
										.equalsIgnoreCase(seperateToken[0])) {
									isValid = false;
									error = new ErrorVO("products.defaults.segallowvilolorgrst");
									error.setErrorDisplayType(ErrorDisplayType.ERROR);
									errors.add(error);
									break outer;
								}
								}
							}
						}
					}
				}

				if (RESTRICT.equals(form.getDestinationStatus())) {
					if (form.getDestination() != null) {
						int destnLen=form.getDestination().length;
						int segmentLen= form.getSegment().length;
						outer:
						for (int j = 0; j < destnLen; j++) {
							for (int i = 0; i < segmentLen; i++) {
								StringTokenizer tok = new StringTokenizer(form
										.getSegment()[i], "-");
								int count = 0;
								String[] seperateToken = new String[2];
								seperateToken[0] = "";
								seperateToken[1] = "";
								while (tok.hasMoreTokens()) {
									seperateToken[count] = tok.nextToken();
									count++;
								}
								if(!ProductVO.OPERATION_FLAG_DELETE.equals(form.getSegmentOperationFlag()[i])){
								if (form.getDestination()[j]
										.equalsIgnoreCase(seperateToken[1])) {
									isValid = false;
									error = new ErrorVO("products.defaults.segallowviloldstrst");
									error.setErrorDisplayType(ErrorDisplayType.ERROR);
									errors.add(error);
									break outer;
								}
								}
							}
						}
					}
				}

			}

		}
		if (!"".equals(form.getMinWeight().trim())
				&& !"".equals(form.getMaxWeight().trim())) {
			if (Double.parseDouble(form.getMinWeight()) > Double
					.parseDouble(form.getMaxWeight())) {
				isValid = false;
				error = new ErrorVO("products.defaults.minweightexceedsmaxweight");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}

		}
		if (!"".equals(form.getMinVolume()) && !"".equals(form.getMaxVolume())) {
			if (Double.parseDouble(form.getMinVolume()) > Double
					.parseDouble(form.getMaxVolume())) {
				isValid = false;
				error = new ErrorVO("products.defaults.minvolexceedsmaxvol");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}

		}
		//Added as part of ICRD-232462
		if (!"".equals(form.getMinDimension()) && !"".equals(form.getMaxDimension())) {
			if (Double.parseDouble(form.getMinDimension()) > Double
					.parseDouble(form.getMaxDimension())) {
				isValid = false;
				error = new ErrorVO("products.defaults.mindimexceedsmaxdim");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}

		}
		if (form.getMinTime() != null && form.getMaxTime() != null) {
			ArrayList<ProductEventVO> eventVOs =(ArrayList<ProductEventVO>)session.getProductEventVOs();
			int minTimLen = form.getMinTime().length;
			for (int i = 0; i < minTimLen ; i++) {
				ProductEventVO eventVO = eventVOs.get(i);
				if(!ProductVO.OPERATION_FLAG_DELETE.equals(eventVO.getOperationFlag())){
					if ("".equals(form.getMinTime()[i].trim()) && "".equals(form.getMaxTime()[i].trim())  ) {
						isValid = false;
						error = new ErrorVO("products.defaults.enterminormaxtim");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					} /*else if ("".equals(form.getMinTime()[i].trim())) {
						isValid = false;
						Object[] obj = { "EndDate" };
						error = new ErrorVO("ERR_1024", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					} */else {
						log
								.log(
										Log.FINE,
										"\n\n\n**********************form.getMinTime()[i]-------------",
										form.getMinTime(), i);
						log
								.log(
										Log.FINE,
										"\n\n\n**********************form.getMaxTime()[i]-------------",
										form.getMaxTime(), i);
						log
								.log(
										Log.FINE,
										"\n\n\n**********************form.getMinTime()[i]).replace(':','.')-------------",
										(form.getMinTime()[i]).replace(':','.'));
						log
								.log(
										Log.FINE,
										"\n\n\n**********************form.getMaxTime()[i]).replace(':','.')-------------",
										(form.getMaxTime()[i]).replace(':','.'));
						if(!"".equals(form.getMinTime()[i].trim()) && !"".equals(form.getMaxTime()[i].trim())){
						if("Export".toUpperCase().equals(eventVO.getEventType().toUpperCase())){
							if ( (Double.parseDouble((form.getMinTime()[i]).replace(':','.'))< Double
									.parseDouble((form.getMaxTime()[i]).replace(':','.'))) && isExportValid ) {
								isValid = false;
								Object[] obj = { "EndDate" };
								error = new ErrorVO("products.defaults.mintimimpentlessmintime", obj);
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
								isExportValid = false;
							}
						}
						if("Import".toUpperCase().equals(eventVO.getEventType().toUpperCase())){
							if ((Double.parseDouble((form.getMinTime()[i]).replace(':','.')) > Double
									.parseDouble((form.getMaxTime()[i]).replace(':','.'))) && isImportValid) {
								isValid = false;
								Object[] obj = { "EndDate" };
								error = new ErrorVO("products.defaults.maxtimimpentlessmintime", obj);
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
								isImportValid = false;
							}

						}
					}
					}
			}
			if(!eventVO.isExternal() && !eventVO.isInternal() && isExternalInternal){
				error = new ErrorVO("products.defaults.internalorexternal");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				isExternalInternal= false;
			}
			}
		}
		if (isValid
				&& DateUtilities.isLessThan(form.getStartDate(), DateUtilities
						.getCurrentDate("dd-MMM-yyyy"), "dd-MMM-yyyy")) {
			if (!MODIFY_MODE.equals(form.getMode())) {
				if ("N".equals(form.getCanSave())) {
					Object[] obj = { "EndDate" };
					error = new ErrorVO("products.defaults.prdstagrtthancurrentdate", obj);
					error.setErrorDisplayType(ErrorDisplayType.WARNING);
					errors.add(error);
				}
			}
		}

		return errors;
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
	 * method to convert to upper case
	 *
	 * @param input
	 *
	 */
	private String upper(String input) {

		if (input != null) {
			return input.trim().toUpperCase();
		} else {
			return "";
		}
	}
}
