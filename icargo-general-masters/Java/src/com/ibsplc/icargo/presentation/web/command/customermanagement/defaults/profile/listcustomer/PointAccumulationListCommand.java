/*
 * PointAccumulationListCommand.java Created on Jan 1, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AirWayBillLoyaltyProgramFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class PointAccumulationListCommand extends BaseCommand {

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.customerlisting";

	private static final String BLANK = "";

	private Log log = LogFactory.getLogger("CUTOMER_LISTING");

	private static final String MODULE_AWB = "operations.shipment";

	/**
	 * The ScreenID for Duplicate AWB screen
	 */

	private static final String SHIPMENT_BLACKLISTED = "B";
	

/**
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("customer",
				"************PointAccumulationListCommand**********");
		ListCustomerForm form = (ListCustomerForm) invocationContext.screenModel;
		ListCustomerSession session = getScreenSession(MODULENAME, SCREENID);
		session.setLoyaltyPrograms(null);
		session.setAwbLoyaltyVos(null);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ShipmentVO shipmentVO = null;
		
    	ShipmentVO shipmentVOFromSession = session.getFromScreenSessionMap(ShipmentVO.KEY_SHIPMENTVO);
		Page<CustomerVO> customerVOs = session.getCustomerVOs();
		String customerCode = session.getCustomerCode();

		log.log(Log.FINE, "customer code iss--->", session.getCustomerCode());
		if (session.getShipmentVOs() == null) {
			log.log(Log.FINE, "\n\nshipment vos from session is null");

			errors = validateForm(form);

			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;
			}
			
             // Added by A-5218	as part of the CR ICRD-21184 to start
			 if (shipmentVOFromSession != null) {
	    			shipmentVO = (ShipmentVO)shipmentVOFromSession;
	    			this.log.log(3, "shipmentVO------------------ ---> " + shipmentVO);
			 }// Added by A-5218	as part of the CR ICRD-21184 to end
			 if(shipmentVO==null){
				 ErrorVO error = null;
					errors = new ArrayList<ErrorVO>();
					if (form.getMasterDocumentNumber() != null
							&& form.getMasterDocumentNumber().trim().length() > 0) {
						error = new ErrorVO(
								"customermanagement.defaults.customerlisting.msg.err.awbdoesnotexist");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					} else if (form.getDocumentNumber() != null
							&& form.getDocumentNumber().trim().length() > 0) {
						error = new ErrorVO(
								"customermanagement.defaults.customerlisting.msg.err.houseawbdoesnotexist");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					}

					invocationContext.addAllError(errors);
					invocationContext.target = LIST_FAILURE;
					return;
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
			invocationContext.addError(errorVO);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		if (shipmentVO != null) {
			log.log(Log.FINE, "\n\n\n\n FOR LISTING LOYALTY PROGRAMS");
			CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
			AirWayBillLoyaltyProgramFilterVO awbFilterVO = new AirWayBillLoyaltyProgramFilterVO();
			awbFilterVO.setCompanyCode(getApplicationSession().getLogonVO()
					.getCompanyCode());
			awbFilterVO.setCustomerCode(customerCode);
			awbFilterVO.setSequenceNumber(shipmentVO.getSequenceNumber());
			if (shipmentVO.getDocumentNumber() != null) {
				awbFilterVO.setAwbNumber(shipmentVO.getDocumentNumber());
			}
			awbFilterVO.setDuplicateNumber(shipmentVO.getDuplicateNumber());
			if (shipmentVO.getMasterDocumentNumber() != null) {
				awbFilterVO.setMasterAwbNumber(shipmentVO
						.getMasterDocumentNumber());
			}
			awbFilterVO.setOwnerId(shipmentVO.getOwnerId());
			session.setAwbFilterVO(awbFilterVO);
			log.log(Log.FINE, "filter vo to server---->", awbFilterVO);
			ArrayList<String> loyaltyPrograms = null;
			try {
				loyaltyPrograms = (ArrayList<String>) delegate
						.listPointAccumulated(awbFilterVO);
			} catch (BusinessDelegateException ex) {
//printStackTrrace()();
				handleDelegateException(ex);
			}
			log.log(Log.FINE, "loyalty progrmas from server---->",
					loyaltyPrograms);
			if (loyaltyPrograms == null || loyaltyPrograms.size() == 0) {
				errorVO = new ErrorVO(
						"customermanagement.defaults.custlisting.msg.err.norecords");
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;
			}
			session.setLoyaltyPrograms(loyaltyPrograms);
			form.setCanEnableShowPoints("Y");
		}
		form.setDuplicateAWBStatus(BLANK);
		session.setShipmentVOs(null);
		invocationContext.target = LIST_SUCCESS;

	}

	/**
	 * 
	 * @param form
	 * @return
	 */
	private Collection<ErrorVO> validateForm(ListCustomerForm form) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;

		if ((form.getOwnerId() == null || form.getOwnerId().trim().length() == 0)
				&& (form.getMasterDocumentNumber() == null || form
						.getMasterDocumentNumber().trim().length() == 0)) {
			error = new ErrorVO(
			"customermanagement.defaults.customerlisting.msg.err.enterawbno");
	error.setErrorDisplayType(ErrorDisplayType.ERROR);
	errors.add(error);

		}
		if ((form.getOwnerId() == null || form.getOwnerId().trim().length() == 0)
				&& (form.getMasterDocumentNumber() != null && form
						.getMasterDocumentNumber().trim().length() > 0)) {
			error = new ErrorVO(
					"customermanagement.defaults.customerlisting.msg.err.nonstandardawb");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);

		}
		if ((form.getOwnerId() != null && form.getOwnerId().trim().length() > 0)
				&& (form.getMasterDocumentNumber() == null || form
						.getMasterDocumentNumber().trim().length() == 0)) {
			error = new ErrorVO(
					"customermanagement.defaults.customerlisting.msg.err.mastermandatoryforprefix");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if ((form.getDocumentNumber() != null && form.getDocumentNumber()
				.trim().length() > 0)
				&& ((form.getDocumentNumber() == null || form
						.getDocumentNumber().trim().length() == 0) || (form
						.getOwnerId() == null || form.getOwnerId().trim()
						.length() == 0))) {
			error = new ErrorVO(
					"customermanagement.defaults.customerlisting.msg.err.mastermandatoryforhawb");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

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
					"customermanagement.defaults.customerlisting.msg.err.awb.blacklisted",
					new String[] { awbNumber.toString() });
		}
		/**
		 * If the shipment status is "Purged" then display an error message
		 */
		else if (shipmentVO.isPurged()) {

			errorVO = new ErrorVO(
					"customermanagement.defaults.customerlisting.msg.err.awb.purged",
					new String[] { awbNumber.toString() });
		}

		log.exiting("ListCommand", "validateShipmentVO");
		return errorVO;
	}

	

}
