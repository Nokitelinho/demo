/*
 * ListCustomerPointsCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.customerpoints;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ListCustomerPointsVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ListPointsAccumulatedFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
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
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.ListCustomerPointsSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.ListCustomerPointsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * 
 * @author A-2046
 * 
 */
public class ListCustomerPointsCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("ListCustomerPointsCommand");

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.listcustomerpoints";

//	private static final String MODULE_AWB = "operations.shipment";

	/**
	 * The ScreenID for Duplicate AWB screen
	 */
//	private static final String SCREEN_ID_DAWB = "operations.shipment.duplicateawb";
//
//	private static final String DUPAWB_SUCCESS = "dupawb_success";

	private static final String SHIPMENT_BLACKLISTED = "B";

/**
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ListCustomerPointsCommand", "Enter");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		ListCustomerPointsForm form = (ListCustomerPointsForm) invocationContext.screenModel;
		ListCustomerPointsSession session = getScreenSession(MODULENAME,SCREENID);
		CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ListPointsAccumulatedFilterVO filterVO = new ListPointsAccumulatedFilterVO();
		//Added by A-5220 for ICRD-46939
		UnitRoundingVO unitRoundingWgtVO = null;
		UnitRoundingVO unitRoundingVolVO = null;
		try {
			log.log(Log.FINE, "\n\nUnitcode----------->");
			unitRoundingWgtVO = UnitFormatter.getSystemDefaultUnitVo(UnitConstants.WEIGHT);
			unitRoundingVolVO = UnitFormatter.getSystemDefaultUnitVo(UnitConstants.VOLUME);
			session.setWeightVO(unitRoundingWgtVO);	
			session.setVolumeVO(unitRoundingVolVO);
		} catch (UnitException unitException) {
			log.log(Log.FINE, unitException.getErrorCode());
		}
	//	AirlineValidationVO airlineValidationVO = null;
		ShipmentVO shipmentVO = null;
		/*form.setFromDate
		(new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP, false).
										toDisplayDateOnlyFormat());
		form.setToDate
		(new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP, false).
										toDisplayDateOnlyFormat());
*/
		// for AWB Validation
		if (session.getShipmentVOs() == null) {
			errors = validateFormAWB(form);
			if (errors != null && errors.size() > 0) {
				session.removePage();
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;
			}

			else if (form.getDocumentNumber() != null
					&& form.getDocumentNumber().trim().length() > 0) {
				/* A-5273 Removed for ICRD-21184
				if (form.getAwbPrefix() != null
						&& form.getAwbPrefix().trim().length() > 0) {

					log.log(Log.FINE, "AWB prefix  length() > 0__________");
					String shipmentPrefix = form.getAwbPrefix();
					try {
						airlineValidationVO = validateShipmentPrefix(
								shipmentPrefix, logonAttributes
										.getCompanyCode());
					} catch (BusinessDelegateException e) {
						errors = handleDelegateException(e);
					}

					if (errors != null && errors.size() > 0) {
						session.removePage();
						invocationContext.addAllError(errors);
						invocationContext.target = LIST_FAILURE;
						return;
					}
				}

				ShipmentFilterVO shipmentFilterVO = populateShipmentFilterVO(
						airlineValidationVO, form, logonAttributes
								.getCompanyCode(), logonAttributes
								.getAirportCode());
				ArrayList<ShipmentVO> shipmentVOs = null;
				
				log
						.log(Log.FINE, "shipmentFilterVO________"
								+ shipmentFilterVO);
												
				try {
					shipmentVOs = (ArrayList<ShipmentVO>) delegate
							.findShipments(shipmentFilterVO);
				} catch (BusinessDelegateException e) {
					errors = handleDelegateException(e);
				}
				if (errors != null && errors.size() > 0) {
					session.removePage();
					invocationContext.addAllError(errors);
					invocationContext.target = LIST_FAILURE;
					return;
				}
				if (shipmentVOs == null || shipmentVOs.size() == 0) {
					ErrorVO error = null;
					errors = new ArrayList<ErrorVO>();
					if (form.getHawbNumber() == null
							|| form.getHawbNumber().trim().length() == 0) {
						error = new ErrorVO(
								"customermanagement.defaults.listcustomerpoints.msg.err.awbdoesnotexist");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					} else {
						error = new ErrorVO(
								"customermanagement.defaults.listcustomerpoints.msg.err.houseawbdoesnotexist");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					}
					session.removePage();
					invocationContext.addAllError(errors);
					invocationContext.target = LIST_FAILURE;
					return;
				}

				else if (shipmentVOs.size() > 1) {
					// log.log(Log.FINE,"_IS_DUPLICATE_TRUE_________"+IS_DUPLICATE_TRUE);
					// deliveryNoteRegisterForm.setStatusFlag(IS_DUPLICATE_TRUE);
					DuplicateAWBSession duplicateAWBSession = (DuplicateAWBSession) getScreenSession(
							MODULE_AWB, SCREEN_ID_DAWB);
					duplicateAWBSession.setShipmentVOs(shipmentVOs);
					duplicateAWBSession.setParentScreenId(SCREENID);
					form.setDuplicateAWBStatus("duplicate");
					invocationContext.target = DUPAWB_SUCCESS;
					return;
				} else {
					log.log(Log.FINE, "No duplicate___________________");
					shipmentVO = shipmentVOs.get(0);
				}*/
				// A-5273 Added for ICRD-21184 	
				  shipmentVO = session.getFromScreenSessionMap(ShipmentVO.KEY_SHIPMENTVO);
			 	 log.log(Log.FINE, "shipmentVO from FWK session-->", shipmentVO);
				if (shipmentVO == null ) {
					ErrorVO error = null;
					errors = new ArrayList<ErrorVO>();
					if (form.getHawbNumber() == null
							|| form.getHawbNumber().trim().length() == 0) {
						error = new ErrorVO(
								"customermanagement.defaults.listcustomerpoints.msg.err.awbdoesnotexist");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					} else {
						error = new ErrorVO(
								"customermanagement.defaults.listcustomerpoints.msg.err.houseawbdoesnotexist");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					}
					session.removePage();
					invocationContext.addAllError(errors);
					invocationContext.target = LIST_FAILURE;
					return;
				}
			}
		} else {
			// form.setConfirmStatus("");
			log.log(Log.FINE, "Inside duplicate AWB session true");
			shipmentVO = ((ArrayList<ShipmentVO>) session.getShipmentVOs())
					.get(0);

		}
		/*
		 * If there is a single shipmentVO then validate the shipmentVO for
		 * blacklisted and purged AWB
		 */
		ErrorVO errorVO = null;
		if (shipmentVO != null) {
			errorVO = validateShipmentVO(shipmentVO);
		}

		if (errorVO != null) {
			session.setShipmentVOs(null);
			session.removePage();
			invocationContext.addError(errorVO);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		log.log(Log.FINE, "shipmentVO------->>>", shipmentVO);
		if (shipmentVO != null) {
			filterVO.setAwbDuplicateNumber(shipmentVO.getDuplicateNumber());
			filterVO.setAwbSequenceNumber(shipmentVO.getSequenceNumber());
			session.setShipmentVOs(null);
			// invocationContext.target = LIST_SUCCESS;
		}

		// AWB Validation ends

		errors = validateForm(form);
		if (errors != null && errors.size() > 0) {
			session.removePage();
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		/* A-5273 Removed for ICRD-21184
		log.log(Log.FINE, "form.getAwbPrefix()----->>>" + form.getAwbPrefix());
		if (!form.getAwbPrefix().equals("")
				&& form.getAwbPrefix().trim().length() > 0) {
			String prefix = form.getAwbPrefix();
			
			try {
				log.log(Log.FINE, "before validateShipmentPrefix--------->>>>");
				airlineValidationVO = validateShipmentPrefix(prefix,
						companyCode);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (airlineValidationVO == null) {
				log.log(Log.FINE, "airlineValidationVO == null-------->>>>");
				errors = new ArrayList<ErrorVO>();
				ErrorVO error = null;
				Object[] obj = { prefix };
				error = new ErrorVO(
						"customermanagement.defaults.listcustomerpoints.msg.err.invalidawbprefix",
						obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			} else {
				filterVO.setMasterOwnerIdentifier(airlineValidationVO
						.getAirlineIdentifier());
				log.log(Log.FINE,
						"filterVO.getMasterOwnerIdentifier()------->>>>"
								+ filterVO.getMasterOwnerIdentifier());
			}
			/**
			 * If errorVOs are set then the target is returned as failure for
			 * List Action
			 
			if (errors != null && errors.size() > 0) {
				log.log(Log.FINE, "errors!=null && errors.size()>0------->>>>");
				session.removePage();
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;
			}*/
		if(shipmentVO!=null){
			filterVO.setMasterOwnerIdentifier(shipmentVO.getOwnerId());
		} else {
			log
					.log(Log.FINE,
							"filterVO.setMasterOwnerIdentifier(-1) && filterVO.(-1);------>>>>");
			filterVO.setMasterOwnerIdentifier(-1);
			filterVO.setAwbDuplicateNumber(-1);
			filterVO.setAwbSequenceNumber(-1);
		}
		if (errors != null && errors.size() > 0) {
			log.log(Log.FINE,
					"errors!=null && errors.size()>022222222------->>>>");
			session.removePage();
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
		} else {
			log.log(Log.FINE, "HashMap indexMap = null;------->>>>");
			//Modified by A-5220 for ICRD-20902 starts
			HashMap<String, String> indexMap = getIndexMap(session.getIndexMap(), invocationContext); 
			//Modified by A-5220 for ICRD-20902 ends
			HashMap finalMap = null;
			if (session.getIndexMap() != null) {
				indexMap = session.getIndexMap();
			}
			if (indexMap == null) {
				log.log(Log.FINE, "INDEX MAP IS NULL");
				indexMap = new HashMap();
				indexMap.put("1", "1");
			}
			int nAbsoluteIndex = 0;
			String toDisplayPage = form.getDisplayPage();
			String strAbsoluteIndex = (String) indexMap.get(toDisplayPage);
			if (strAbsoluteIndex != null) {
				nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
			}
			filterVO.setAbsoluteIndex(nAbsoluteIndex);
			int pageNumber = Integer.parseInt(form.getDisplayPage());
			//Added by A-5220 for ICRD-20902 starts
			filterVO.setPageNumber(pageNumber);
			log.log(Log.FINE, "pageNumber------->>>>", pageNumber);
			log.log(Log.FINE, "getFormDetails------->>>>");
			filterVO = getFormDetails(form, logonAttributes, session, filterVO);
			
			//Added by A-5175 on 24-Oct-2012 for icrd-22065 starts
			
			if(ListCustomerPointsForm.PAGINATION_MODE_FROM_FILTER.equals(form.getNavigationMode())) {
				filterVO.setTotalRecordCount(-1);
				//Commented by A-5220 for ICRD-20902 starts
				//filterVO.setPageNumber(1);
				//Commented by A-5220 for ICRD-20902 ends
				
			}else if(ListCustomerPointsForm.PAGINATION_MODE_FROM_NAVIGATION.equals(form.getNavigationMode())) {
				filterVO.setTotalRecordCount(session.getTotalRecords());
				filterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));
			}else {
				filterVO.setTotalRecordCount(-1);
				//Commented by A-5220 for ICRD-20902 starts
				//filterVO.setPageNumber(1);
				//Commented by A-5220 for ICRD-20902 ends
			}
			//Added by A-5175 on 24-Oct-2012 for icrd-22065 ends
			session.setFilterDetailsOnReturn(filterVO);
			try {
				log.log(Log.FINE, "filterVO before setting to delegate",
						filterVO);
				Page<ListCustomerPointsVO> pg = delegate
						.listLoyaltyPointsForAwb(filterVO, pageNumber);
				log.log(Log.FINE, "colln getting from delegate", pg);
				if (pg != null ) {
					//Added by A-5175 on 24-Oct-2012 for icrd-22065 starts
					if(pg.size()>0) {
						log.log(Log.FINE,
								" the total records in the    list:>", pg.getTotalRecordCount());
						log.log(Log.FINE, " caching in screen session ");
						session.setTotalRecords(pg.getTotalRecordCount()); 
					}
					//Added by A-5175 on 24-Oct-2012 for icrd-22065 ends
					if (pg.getActualPageSize() != 0) {
						session.setPage(pg);
						log.log(Log.FINE, "session.getPage()", session.getPage());
				    }
				}
				if ((pg == null) || (pg.getActualPageSize() == 0)) {
					session.removePage();
					log.log(Log.INFO,"\n\n-----------------------------Page size is zero");
					ErrorVO error = new ErrorVO(
							"customermanagement.defaults.listcustomerpoints.msg.err.nomorerecordsfound");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
			} catch (BusinessDelegateException e) {
				// To be reviewed Auto-generated catch block
//printStackTrrace()();
				handleDelegateException(e);
			}

			if (errors == null || errors.size() == 0) {
				finalMap = indexMap;
				log.log(Log.FINE, "session.getPage()---->>>>", session.getPage());
				if (session.getPage() != null) {
					finalMap = buildIndexMap(indexMap, session.getPage());
				}
				//Modified by A-5220 for ICRD-20902 starts
				session.setIndexMap((HashMap<String,String>)super.setIndexMap(finalMap, invocationContext));
				//Modified by A-5220 for ICRD-20902 ends
				invocationContext.target = LIST_SUCCESS;
			} else {
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
			}
		}
		log.exiting("ListCustomerPointsCommand", "Exit");
	}

	/**
	 * 
	 * @param form
	 * @return
	 */
	public Collection<ErrorVO> validateForm(ListCustomerPointsForm form) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if (form.getFromDate() != null && form.getToDate() != null) {
			LocalDate frmDate = new LocalDate(getApplicationSession().getLogonVO().getStationCode(),Location.STN,false);
			LocalDate toDate = new LocalDate(getApplicationSession().getLogonVO().getStationCode(),Location.STN,false);

			if (DateUtilities.isValidDate(form.getFromDate(),
					CALENDAR_DATE_FORMAT)
					&& DateUtilities.isValidDate(form.getToDate(),
							CALENDAR_DATE_FORMAT)) {
				frmDate.setDate(form.getFromDate());
				toDate.setDate(form.getToDate());
				if (toDate.isLesserThan(frmDate)) {
					error = new ErrorVO(
							"customermanagement.defaults.listcustomerpoints.msg.err.FromDateGreaterThanToDate");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
			}
		}
		return errors;
	}
/**
 * 
 * @param form
 * @param logonAttributes
 * @param session
 * @param filterVO
 * @return
 */
	public ListPointsAccumulatedFilterVO getFormDetails(
			ListCustomerPointsForm form, LogonAttributes logonAttributes,
			ListCustomerPointsSession session,
			ListPointsAccumulatedFilterVO filterVO) {
		// ListPointsAccumulatedFilterVO filterVO = new
		// ListPointsAccumulatedFilterVO ();

		filterVO.setCompanyCode(logonAttributes.getCompanyCode());

		filterVO.setAwbNumber(form.getDocumentNumber());
		filterVO.setHouseAwbNumber(form.getHawbNumber());
		filterVO.setCustomerCode(form.getCustomerCode().toUpperCase());
		// filterVO.setAwbSequenceNumber(1);
		LocalDate from = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
		if (form.getFromDate() != null
				&& form.getFromDate().trim().length() != 0) {
			filterVO.setFromDate(from.setDate(form.getFromDate()));
		}
		LocalDate to = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
		if (form.getToDate() != null && form.getToDate().trim().length() != 0) {
			filterVO.setToDate(to.setDate(form.getToDate()));
		}
		log.log(Log.FINE, "filterVO---------->", filterVO);
		return filterVO;
	}

	/**
	 * @param existingMap
	 * @param page
	 * @return HashMap
	 */	
	private HashMap buildIndexMap(HashMap existingMap, Page page) {
		HashMap finalMap = existingMap;
//		String currentPage = String.valueOf(page.getPageNumber());
//		String currentAbsoluteIndex = String.valueOf(page.getAbsoluteIndex());
		String indexPage = String.valueOf((page.getPageNumber() + 1));

		boolean isPageExits = false;
		Set<Map.Entry<String, String>> set = existingMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExits = true;
			}
		}

		if (!isPageExits) {
			finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
		}
		return finalMap;
	}

	/**
	 * Method to return the airline validation vo
	 * 
	 * @param shipmentPrefix
	 * @param compCode
	 * @return
	 * @throws BusinessDelegateException
	 */
	/* A-5273 Removed for ICRD-21184
	private AirlineValidationVO validateShipmentPrefix(String shipmentPrefix,
			String compCode) throws BusinessDelegateException {
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		AirlineValidationVO airlineValidationVO = airlineDelegate
				.validateNumericCode(compCode, shipmentPrefix);
		return airlineValidationVO;
	}*/

	/**
	 * 
	 * @param form
	 * @return
	 */
	private Collection<ErrorVO> validateFormAWB(ListCustomerPointsForm form) {
		log.entering("validateFormAWB", "ENTRY");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		/*
		 * if("".equals(form.getDocumentNumber())){ error = new
		 * ErrorVO("customermanagement.defaults.listcustomerpoints.msg.err.entermasterdocno");
		 * error.setErrorDisplayType(ErrorDisplayType.ERROR); errors.add(error); }
		 */
		if ((form.getAwbPrefix() == null || form.getAwbPrefix().trim().length() == 0)
				&& (form.getDocumentNumber() != null && form
						.getDocumentNumber().trim().length() > 0)) {
			error = new ErrorVO(
					"customermanagement.defaults.listcustomerpoints.msg.err.nonstandardawb");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);

		}
		if ((form.getAwbPrefix() != null && form.getAwbPrefix().trim().length() > 0)
				&& (form.getDocumentNumber() == null || form
						.getDocumentNumber().trim().length() == 0)) {
			error = new ErrorVO(
					"customermanagement.defaults.listcustomerpoints.msg.err.mastermandatoryforprefix");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if ((form.getHawbNumber() != null && form.getHawbNumber().trim()
				.length() > 0)
				&& ((form.getDocumentNumber() == null || form
						.getDocumentNumber().trim().length() == 0) || (form
						.getAwbPrefix() == null || form.getAwbPrefix().trim()
						.length() == 0))) {
			error = new ErrorVO(
					"customermanagement.defaults.listcustomerpoints.msg.err.mastermandatoryforhawb");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		log.exiting("validateFormAWB--->>>", "EXIT");
		return errors;
	}

	/**
	 * The method to validate the shipmentVO. The method returns the errorVO, if
	 * the shipment is blacklisted or purged.
	 * 
	 * @param shipmentVO
	 * @return errorVO;
	 */
	public ErrorVO validateShipmentVO(ShipmentVO shipmentVO) {
		log.entering("ListCommand", "validateShipmentVO");

		ErrorVO errorVO = null;
		StringBuffer awbNumber = new StringBuffer();
		awbNumber.append(shipmentVO.getShipmentPrefix()).append("-").append(
				shipmentVO.getDocumentNumber());
		/*
		 * If the shipment status is "Blacklisted" then display an error
		 * message.
		 */
		if (SHIPMENT_BLACKLISTED.equals(shipmentVO.getShipmentStatus())) {

			errorVO = new ErrorVO(
					"customermanagement.defaults.listcustomerpoints.msg.err.awb.blacklisted",
					new String[] { awbNumber.toString() });
		}
		/*
		 * If the shipment status is "Purged" then display an error message
		 */
		else if (shipmentVO.isPurged()) {

			errorVO = new ErrorVO(
					"customermanagement.defaults.listcustomerpoints.msg.err.awb.purged",
					new String[] { awbNumber.toString() });
		}

		log.exiting("ListCommand", "validateShipmentVO");
		return errorVO;
	}

	/**
	 * 
	 * @param airlineValidationVO
	 * @param form
	 * @param compCode
	 * @param airportCode
	 * @return
	 */
	/* A-5273 Removed for ICRD-21184
	private ShipmentFilterVO populateShipmentFilterVO(
			AirlineValidationVO airlineValidationVO,
			ListCustomerPointsForm form, String compCode, String airportCode) {
		log.entering("ListCommand", "populateShipmentFilterVO");
		ShipmentFilterVO shipmentFilterVO = new ShipmentFilterVO();
		shipmentFilterVO.setMasterDocumentNumber(form.getDocumentNumber()
				.length() == 0 ? null : form.getDocumentNumber());
		shipmentFilterVO
				.setDocumentNumber(form.getHawbNumber().length() > 0 ? form
						.getHawbNumber()
						: (form.getDocumentNumber().length() == 0 ? null : form
								.getDocumentNumber()));
		shipmentFilterVO.setCompanyCode(compCode);
		shipmentFilterVO.setShipmentPrefix(form.getAwbPrefix());
		shipmentFilterVO.setHouse(form.getHawbNumber().length() > 0);
		shipmentFilterVO.setNonStandard(form.getHawbNumber().length() > 0
				|| form.getAwbPrefix().length() == 0);

		// AWB prefix entered
		if (form.getAwbPrefix().length() > 0) {
			log.log(Log.FINE,
					"_airlineValidationVO.getAirlineIdentifier_________"
							+ airlineValidationVO.getAirlineIdentifier());
			shipmentFilterVO.setOwnerId(airlineValidationVO
					.getAirlineIdentifier());
		} else {
			shipmentFilterVO.setNonStandard(true);
		}
		log.exiting("ListCommand", "populateShipmentFilterVO");
		return shipmentFilterVO;

	}*/

}
