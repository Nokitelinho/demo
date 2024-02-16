/*
 * SaveSubProductCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainsubproduct;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Collection;
import java.util.StringTokenizer;


import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCommodityVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCustomerGroupVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionPaymentTermsVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;

import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainSubProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 *
 * @author A-1870
 *
 */
public class SaveSubProductCommand extends BaseCommand {
	private static final String RESTRICT = "Restrict";

	private static final String ALLOW = "Allow";

	private static final String OPERATION_FLAG_INSERT = "I";

	private static final String OPERATION_FLAG_UPDATE = "U";

	private static final String OPERATION_FLAG_DELETE = "D";

	private static final String ALLOW_FLAG = "Allow";
	/**
     * Log
     */
    private Log log = LogFactory.getLogger("PRODUCTS.DEFAULTS");

/**
 * @param invocationContext
 * @throws CommandInvocationException
 *
 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		MaintainSubProductForm maintainSubProductForm= (MaintainSubProductForm)invocationContext.screenModel;
		MaintainSubProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainsubproducts");
		SubProductVO  subProductVO=new SubProductVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		int chkflg =0;
		errors = validateForm(maintainSubProductForm,session);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = "save_failure";
			return;
		}
		else if (!validateSegments(session)) {

			ErrorVO error = null;
			Object[] obj = { "Segment" };
			error = new ErrorVO("products.defaults.segcontainsinvalidstns", obj); // Invalid Segment
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = "save_failure";
			return;
		} else if (isDuplictaeSegmet(session)) {
			ErrorVO error = null;
			Object[] obj = { "Segment" };
			error = new ErrorVO("products.defaults.duplicatesegment", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = "save_failure";
			return;
		} else {
			subProductVO = getAllProductDetails(maintainSubProductForm,session);
			log.log(Log.FINE,
					"\n\n\n\n**********************subProductVO-------------",
					subProductVO);
			try {

				ProductDefaultsDelegate productDefaultsDelegate = new ProductDefaultsDelegate();

				Collection<SubProductVO> subProductvo=new ArrayList <SubProductVO>();
				subProductvo.add(subProductVO);
				productDefaultsDelegate.saveSubProductDetails(subProductvo);

				//maintainSubProductForm.setSaveSuccessful(FLAG_YES);
				

			} catch (BusinessDelegateException businessDelegateException) {
				
				businessDelegateException.getMessage();
				errors = handleDelegateException(businessDelegateException);
				invocationContext.target = "save_failure";
				chkflg =1;
				return;
				
			}

		}
		
		if(chkflg != 1){
			Collection<ErrorVO> errors1 = new ArrayList<ErrorVO>();
			ErrorVO error1 = null;
			Object[] obj = { "Segment" };
			error1 = new ErrorVO("products.defaults.subprdsavesuccess", obj); //saved successfully
			//error1.setErrorDisplayType(ErrorDisplayType.INFO);
			errors1.add(error1);
			invocationContext.addAllError(errors1);
			invocationContext.target = "save_success";
			return;
			

		}
		 invocationContext.target = "save_success";

	}
	/**
	 *
	 * @param form
	 * @param session
	 * @return Collection<ErrorVO>
	 */
		private Collection<ErrorVO> validateForm(MaintainSubProductForm form,
				MaintainSubProductSessionInterface session) {
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			log.log(Log.FINE,"\n\n\n\n**********************Inside validate form-------------");
			ErrorVO error = null;
			boolean isValid = true;
			boolean isExportValid = true;
			boolean isImportValid = true;
			boolean isExternalInternal = true;
//			log.log(Log.FINE,"\n\n\n\n***********form.getSegment()-- " + form.getSegment());

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

				log.log(Log.FINE,"\n\n\n\n***************going to validate segment-----------");
				log.log(Log.FINE,
						"\n\n\n\n*********--form.getSegmentStatus()--------",
						form.getSegmentStatus());
				log
						.log(
								Log.FINE,
								"\n\n\n\n*********--form.getDestinationStatus()--------",
								form.getDestinationStatus());
				log.log(Log.FINE,
						"\n\n\n\n*********--form.getOriginStatus()--------",
						form.getOriginStatus());
				if (ALLOW.equals(form.getSegmentStatus())) {
					if (RESTRICT.equals(form.getOriginStatus())) {
//							log.log(Log.FINE,"\n\n\n\n*********--form.getOrigin()--"+form.getOrigin());
						if (form.getOrigin() != null) {
							int orgLen = form.getOrigin().length;
							int segLen = form.getSegment().length;
							outer :
							for (int j = 0; j < orgLen; j++) {
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
									log
											.log(
													Log.FINE,
													"\n\n\n\n******form.getOrigin()[j]-",
													form.getOrigin(), j);
									log.log(Log.FINE,
											"\n\n\n\n******seperateToken[0]-",
											seperateToken);
									if(!ProductVO.OPERATION_FLAG_DELETE.equals(form.getSegmentOperationFlag()[i])){
									if (form.getOrigin()[j]
											.equalsIgnoreCase(seperateToken[0])) {
										isValid = false;
										Object[] obj = { "Origin" };
										log.log(Log.FINE,"\n\n\n\n******ERROR.... segallowvilolorgrst"  );
										error = new ErrorVO("products.defaults.segallowvilolorgrst", obj);
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
//						log.log(Log.FINE,"\n\n\n\n*********--form.getDestination()--"+form.getDestination());
						if (form.getDestination() != null) {
							int destnLen = form.getDestination().length;
							int segLen = form.getSegment().length;
							outer:
							for (int j = 0; j < destnLen; j++) {
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
									log
											.log(
													Log.FINE,
													"\n\n\n\n******form.getDestination()[j]-",
													form.getDestination(), j);
									log.log(Log.FINE,
											"\n\n\n\n******seperateToken[1]-",
											seperateToken);
									if(!ProductVO.OPERATION_FLAG_DELETE.equals(form.getSegmentOperationFlag()[i])){
									if (form.getDestination()[j]
											.equalsIgnoreCase(seperateToken[1])) {
										isValid = false;
										Object[] obj = { "Destination" };
										log.log(Log.FINE,"\n\n\n\n******ERROR.... segallowviloldstrst"  );
										error = new ErrorVO("products.defaults.segallowviloldstrst", obj);
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
					Object[] obj = { "Weight" };
					error = new ErrorVO("products.defaults.minweightexceedsmaxweight", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}

			}
			if (!"".equals(form.getMinVolume()) && !"".equals(form.getMaxVolume())) {
				if (Double.parseDouble(form.getMinVolume()) > Double
						.parseDouble(form.getMaxVolume())) {
					isValid = false;
					Object[] obj = { "Volume" };
					error = new ErrorVO("products.defaults.minvolexceedsmaxvol", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}

			}
			if (form.getMinTime() != null && form.getMaxTime() != null) {
				ArrayList<ProductEventVO> eventVOs =(ArrayList<ProductEventVO>)session.getProductEventVOs();
				int minTimLen = form.getMinTime().length;
				for (int i = 0; i < minTimLen; i++) {
					ProductEventVO eventVO = eventVOs.get(i);
					if(!ProductVO.OPERATION_FLAG_DELETE.equals(eventVO.getOperationFlag())){
						if ("".equals(form.getMinTime()[i].trim()) && "".equals(form.getMaxTime()[i].trim())) {
							isValid = false;
							Object[] obj = { "EndDate" };
							error = new ErrorVO("products.defaults.enterminormaxtim", obj);
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
						} else {
							if(!"".equals(form.getMinTime()[i].trim()) && !"".equals(form.getMaxTime()[i].trim())){
							if("Export".toUpperCase().equals(eventVO.getEventType().toUpperCase())){
								if ( (Double.parseDouble((form.getMinTime()[i]).replace(':','.')) < Double
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


			return errors;
		}
		/**
		 *
		 * @param session
		 * @return boolean
		 */
		private boolean validateSegments(MaintainSubProductSessionInterface session) {
			if (session.getSegmentVOs() != null) {
				Map<String,AirportValidationVO> validStations = null;
				Collection<RestrictionSegmentVO> segments = session
						.getSegmentVOs();
				HashSet<String> stations = new HashSet<String>();

				for (RestrictionSegmentVO vo : segments) {
					if (!stations.contains(vo.getOrigin())) {
						stations.add(upper(vo.getOrigin()));
					}
					if (!stations.contains(vo.getDestination())) {
						stations.add(upper(vo.getDestination()));
					}
				}
				try {
					validStations = new AreaDelegate().validateAirportCodes(
							getApplicationSession().getLogonVO().getCompanyCode(), stations);
				} catch (BusinessDelegateException e) {
							return false;
				}

			}
			return true;
		}	/**
		 * This method validates if the user has enterd duplicate segments
		 *
		 * @param session
		 * @return boolean
		 */
		private boolean isDuplictaeSegmet(MaintainSubProductSessionInterface session) {
			if (session.getSegmentVOs() != null) {

				ArrayList<RestrictionSegmentVO> segments  = new ArrayList<RestrictionSegmentVO>();
				for(RestrictionSegmentVO vo : session.getSegmentVOs()){
					if(!ProductVO.OPERATION_FLAG_DELETE.equals(vo.getOperationFlag())){
						segments.add(vo);
					}
				}

				ArrayList<RestrictionSegmentVO> segmentList = new ArrayList<RestrictionSegmentVO>(segments);
				int segmentSize=segments.size();
				int segmentListSize=0;
				for (int i = 0; i < segmentSize; i++) {
					RestrictionSegmentVO firstVO = (RestrictionSegmentVO) segments
							.get(i);
					segmentListSize=segmentList.size();
					for (int j = i + 1; j <segmentListSize ; j++) {
						RestrictionSegmentVO secondVO = (RestrictionSegmentVO) segmentList
								.get(j);
						if (firstVO.getOrigin().equals(secondVO.getOrigin())
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
 *
 * @param form
 * @param session
 * @return SubProductVO
 */

		private SubProductVO getAllProductDetails(MaintainSubProductForm form, MaintainSubProductSessionInterface session){

			SubProductVO subProductVO = new SubProductVO();

			//setting the flag as manually modified added by salish
			subProductVO.setAlreadyModifed(true);
			if (!"".equals(form.getMaxVolume())) {
			subProductVO.setMaximumVolumeDisplay(Double.parseDouble(form.getMaxVolume()));
			}
			if (!"".equals(form.getMinVolume())) {
			subProductVO.setMinimumVolumeDisplay(Double.parseDouble(form.getMinVolume()));
			}
			if (!"".equals(form.getMaxWeight())) {
			subProductVO.setMaximumWeightDisplay(Double.parseDouble(form.getMaxWeight()));
			}
			if (!"".equals(form.getMinWeight())) {
			subProductVO.setMinimumWeightDisplay(Double.parseDouble(form.getMinWeight()));
			}
			subProductVO.setWeightUnit(form.getWeightUnit());
			subProductVO.setVolumeUnit(form.getVolumeUnit());
			subProductVO.setProductPriority(form.getHiddenPriority());
			subProductVO.setProductScc(form.getProductScc());
			subProductVO.setProductTransportMode(form.getTransportMode());
			subProductVO.setStatus(form.getHiddenStatus());
			subProductVO.setProductCode(form.getProductCode());
			subProductVO.setHandlingInfo(form.getHandlingInfo());
			subProductVO.setRemarks(form.getRemarks());
			subProductVO.setClientCall(true);
			try{
				subProductVO.setVersionNumber(Integer.parseInt(form.getVersionNumber()));
			}catch(NumberFormatException exception){
				exception.getMessage();
			}
			subProductVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
			subProductVO.setSubProductCode(form.getSubProductCode());

			subProductVO.setAdditionalRestrictions(form.getAddRestriction());

			subProductVO.setOperationFlag(OPERATION_FLAG_UPDATE);
			subProductVO.setRestrictionCommodity(getAllSelectedCommodity(form,session));
			subProductVO.setRestrictionPaymentTerms(getAllRestrictedPaymentTerms(form,session));
			subProductVO.setRestrictionSegment(getAllSelectedSegments(form,session));
			subProductVO.setRestrictionCustomerGroup(getAllSelectedCustomerGroup(form,session));
			subProductVO.setRestrictionStation(getAllRestrictedStation(form,session));
			subProductVO.setEvents(session.getProductEventVOs());
			subProductVO.setServices(session.getProductService());
			subProductVO.setLastUpdateDate(session.getSubProductVO().getLastUpdateDate());
			return subProductVO;

		}
		private Collection<RestrictionCommodityVO> getAllSelectedCommodity(
				MaintainSubProductForm form, MaintainSubProductSessionInterface session) {
			Collection<RestrictionCommodityVO> oldList = session
					.getCommodityVOs();
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
			return newList;

		}
		/**
		 *
		 * @param form
		 * @param session
		 * @return Collection<RestrictionPaymentTermsVO>
		 */

		private Collection<RestrictionPaymentTermsVO> getAllRestrictedPaymentTerms(
				MaintainSubProductForm form, MaintainSubProductSessionInterface session){
			Collection<RestrictionPaymentTermsVO> oldList = session.getSelectedRestrictedPaymentTerms();
			Collection<RestrictionPaymentTermsVO> newList = new ArrayList<RestrictionPaymentTermsVO>();
			String[]  selectedRestriction = form.getRestrictedTermsCheck();
			if(selectedRestriction!=null){
				boolean isPresent = false;
				RestrictionPaymentTermsVO newVO = null;
				if(oldList!=null){
					for(RestrictionPaymentTermsVO vo : oldList){
						isPresent = false;
						for(int i=0;i<selectedRestriction.length;i++){
							if(vo.getPaymentTerm().equals(selectedRestriction[i])){
								isPresent = true;
							}
						}
						if(!isPresent){
							vo.setOperationFlag(OPERATION_FLAG_DELETE);
							newList.add(vo);
						}if(isPresent){
							newList.add(vo);
						}
					}

					for(int i=0;i<selectedRestriction.length;i++){
					
						isPresent = false;
						for(RestrictionPaymentTermsVO paymentVO : oldList)	{

							if(paymentVO.getPaymentTerm().equals(selectedRestriction[i])){
									isPresent = true;
								}
						}
							if(!isPresent){

								newVO = new RestrictionPaymentTermsVO();
								newVO.setPaymentTerm(selectedRestriction[i]);
								newVO.setOperationFlag(OPERATION_FLAG_INSERT);
								newVO.setIsRestricted(true);
								newList.add(newVO);
							}
					}
				}else{
					newList = getRestrictedPaymentTerms(selectedRestriction);
				}

			}else{

				if(oldList!=null){
					for(RestrictionPaymentTermsVO paymentVO : oldList){
						paymentVO.setOperationFlag(OPERATION_FLAG_DELETE);
						newList.add(paymentVO);
					}
				}
			}
			return newList;

		}
/**
 *
 * @param paymentTerms
 * @return Collection<RestrictionPaymentTermsVO>
 */
		private Collection<RestrictionPaymentTermsVO> getRestrictedPaymentTerms(String[] paymentTerms){

			Collection<RestrictionPaymentTermsVO> restrictionPaymentTermsVOs = new ArrayList<RestrictionPaymentTermsVO>();
			if(paymentTerms!=null){
				for(int i=0;i<paymentTerms.length;i++){
					RestrictionPaymentTermsVO restrictionPaymentTermsVO = new RestrictionPaymentTermsVO();

					restrictionPaymentTermsVO.setPaymentTerm(paymentTerms[i]);
					restrictionPaymentTermsVO.setIsRestricted(true);
					restrictionPaymentTermsVO.setOperationFlag(OPERATION_FLAG_INSERT);
					restrictionPaymentTermsVOs.add(restrictionPaymentTermsVO);

				}
			}

			return restrictionPaymentTermsVOs;

		}
		/**
		 *
		 * @param form
		 * @param session
		 * @return Collection<RestrictionSegmentVO>
		 */
		private Collection<RestrictionSegmentVO> getAllSelectedSegments(
				MaintainSubProductForm form, MaintainSubProductSessionInterface session) {
			Collection<RestrictionSegmentVO> oldList = session
					.getSegmentVOs();
			Collection<RestrictionSegmentVO> newList = new ArrayList<RestrictionSegmentVO>();

			boolean isRestricted = true;
			if (oldList != null) {
				String status = form.getSegmentStatus();
				if (status.equals(ALLOW_FLAG)) {
					isRestricted = false;
				}
				for (RestrictionSegmentVO vo : oldList) {
					if (OPERATION_FLAG_UPDATE.equals(vo.getOperationFlag())) {
						vo.setOperationFlag(OPERATION_FLAG_INSERT);
						vo.setIsRestricted(isRestricted);
					} else if ((vo.getOperationFlag() == null)
							&& (vo.getIsRestricted() != isRestricted)) {
						vo.setOperationFlag(OPERATION_FLAG_DELETE);
						RestrictionSegmentVO newVO = new RestrictionSegmentVO();
						newVO.setOrigin(vo.getOrigin());
						newVO.setDestination(vo.getDestination());
						newVO.setOrigin(vo.getOrigin());
						newVO.setIsRestricted(isRestricted);
						newVO.setOperationFlag(OPERATION_FLAG_INSERT);
						newList.add(newVO);
					} else {
						vo.setIsRestricted(isRestricted);
					}
					newList.add(vo);
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
		 * The function to get all the selected restricted/allowed cuatomer group
		 *
		 * @param form
		 * @param session
		 * @return Collection<RestrictionCustomerGroupVO> newList
		 */
		private Collection<RestrictionCustomerGroupVO> getAllSelectedCustomerGroup(
				MaintainSubProductForm form, MaintainSubProductSessionInterface session) {

			Collection<RestrictionCustomerGroupVO> oldList = session.getCustGroupVOs();

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
		 * The function to get the selected restricted/allowed stations
		 *
		 * @param form
		 * @param session
		 * @return Collection<RestrictionStationVO>
		 */
		private Collection<RestrictionStationVO> getAllRestrictedStation(
				MaintainSubProductForm form, MaintainSubProductSessionInterface session) {
			Collection<RestrictionStationVO> restrictedStations = session
					.getStationVOs();
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
