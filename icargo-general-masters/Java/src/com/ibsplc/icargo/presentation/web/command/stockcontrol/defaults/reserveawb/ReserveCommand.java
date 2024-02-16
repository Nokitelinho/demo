/*
 * ReserveCommand.java Created on Jan 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.reserveawb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerValidationVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReserveAWBVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.customer.CustomerDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReserveAWBSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReserveAWBForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 *
 */
public class ReserveCommand extends BaseCommand {

	/**
	 * execute method
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */

	private static final String NOOP = "NOOP";
	private Log log = LogFactory.getLogger("TRACING MESSAGE PARAMETERS");
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ReserveAWBForm form = (ReserveAWBForm) invocationContext.screenModel;
		ReserveAWBSession session = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.cto.reservestock");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> errs = new ArrayList<ErrorVO>();
		StockControlDefaultsDelegate delegate = new StockControlDefaultsDelegate();
		ReserveAWBVO reserveAWBVO = new ReserveAWBVO();
		ReserveAWBVO vo = new ReserveAWBVO();
		errors = getVoToSave(form, reserveAWBVO, logonAttributes, session
						.getDocumentVO());
		session.setReserveAWBVO(reserveAWBVO);

		if(logonAttributes.getOwnAirlineCode().equalsIgnoreCase(
						form.getAirline())) {
					invocationContext.addError(
						new ErrorVO("stockcontrol.defaults.cannotmanageforownairline"));
					invocationContext.target = "reserve_failure";
					return;
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = "reserve_failure";
		} else {
				errors = validateForm(form);
				session.setAirline(form.getAirline());
				session.setDocType(form.getAwbType());

			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = "reserve_failure";
			} else {
				try {
					vo = delegate.reserveDocument(reserveAWBVO);

				} catch (BusinessDelegateException e) {
					errs = handleDelegateException(e);
				}
				if (errs != null && errs.size() > 0) {
					invocationContext.addAllError(errs); 
					log.log(Log.FINE, "ERRORS in COMMAND", errs);
					/*for(ErrorVO err : errs){
						log.log(Log.FINE, "ERRORS in COMMAND value"+err.getErrorCode());
						if(("stockcontrol.defaults.rangenotfound".equalsIgnoreCase(err.getErrorCode()))
						|| ("stockcontrol.defaults.stocknotexists".equalsIgnoreCase(err.getErrorCode()))){
							ErrorVO errvo = new ErrorVO("stockcontrol.defaults.rangenotfoundatclient");
							errvo.setErrorDisplayType(ErrorDisplayType.ERROR);
							invocationContext.addError(errvo);
						}
					}*/
					invocationContext.target = "reserve_failure";
					return; 
				} else {
					if (vo != null && vo.getCompanyCode() != null) {
						session.setReservedDoc(null);
						session.setRejectedDoc(null);
						int docnumFromServer = 0;
						int specificdocnumToServer = 0;
						int gendocnumToServer = 0;

						if (vo != null && vo.getDocumentNumbers() != null) {
							docnumFromServer = vo.getDocumentNumbers().size();
						}
						if (vo != null && vo.getSpecificDocNumbers() != null) {
							specificdocnumToServer = vo.getSpecificDocNumbers()
									.size();
						}
						if (vo != null && vo.getNumberOfDocuments() > 0) {
							gendocnumToServer = vo.getNumberOfDocuments();
						}
						if (docnumFromServer < (specificdocnumToServer + gendocnumToServer)) {
							session.setReservedDoc(String
									.valueOf(docnumFromServer));
							session
									.setRejectedDoc(String
											.valueOf((specificdocnumToServer + gendocnumToServer)
													- docnumFromServer));
						}
						if (vo.isHasMinReorderLevel()) {
							session.setReorderLevel("Y");
						}


						session.setReserveAWBVOs(vo);
						session.setAWBs(vo.getDocumentNumbers());
						form.setAfterReserve("Y");
						errors = new ArrayList<ErrorVO>();
						ErrorVO error = null;
						error = new ErrorVO("stockcontrol.defaults.cto.reservation.savesuccess");
						error.setErrorDisplayType(ErrorDisplayType.STATUS);
						errors.add(error);
						invocationContext.addAllError(errors);
						invocationContext.target = "reserve_success";

					} else {
						Collection<ErrorVO> errorvos = new ArrayList<ErrorVO>();
						ErrorVO error = new ErrorVO(
								"stockcontrol.defaults.stockcannotbeallocated");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errorvos.add(error);
						invocationContext.addAllError(errorvos);
						invocationContext.target = "reserve_failure";
					}

				}

			}
		}
	}
	/**
	 *
	 * @param form
	 * @return
	 */
	private Collection<ErrorVO> validateForm(ReserveAWBForm form) {
		Collection<ErrorVO> errVo = new ArrayList<ErrorVO>();

		if (form.getAirline() == null || form.getAirline().trim().length() == 0) {
			ErrorVO error = new ErrorVO(
					"stockcontrol.defaults.cto.airlineempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errVo.add(error);
		}
		if (form.getAwbType() == null || form.getAwbType().trim().length() == 0) {
			ErrorVO error = new ErrorVO(
					"stockcontrol.defaults.cto.awbtypeempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errVo.add(error);
		}
		if (form.getCustCode() == null
				|| form.getCustCode().trim().length() == 0) {
			ErrorVO error = new ErrorVO(
					"stockcontrol.defaults.cto.custcodeempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errVo.add(error);
		}
		if (form.getRemarks() == null || form.getRemarks().trim().length() == 0) {
			ErrorVO error = new ErrorVO(
					"stockcontrol.defaults.cto.remarksempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errVo.add(error);
		}
		if (form.getGeneral() == null && form.getSpecific() == null) {
			ErrorVO error = new ErrorVO(
					"stockcontrol.defaults.cto.generalorspecific");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errVo.add(error);
		}
		if (form.getGeneral() != null && form.getGeneral().trim().length() > 0) {
			if (form.getTotAwb() == null
					|| form.getTotAwb().trim().length() == 0) {
				ErrorVO error = new ErrorVO(
						"stockcontrol.defaults.cto.noofdocempty");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errVo.add(error);
			}
		}
		if (form.getSpecific() != null
				&& form.getSpecific().trim().length() > 0) {
			if (form.getAwbNumber() == null || form.getAwbNumber().length == 0) {
				ErrorVO error = new ErrorVO(
						"stockcontrol.defaults.cto.awbnumbersempty");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errVo.add(error);
			}
		}
		return errVo;
	}
	/**
	 *
	 * @param form
	 * @param reserveAWBVO
	 * @param logonAttributes
	 * @param docvocol
	 * @return
	 */
	private Collection<ErrorVO> getVoToSave(ReserveAWBForm form,
			ReserveAWBVO reserveAWBVO, LogonAttributes logonAttributes,
			Collection<DocumentVO> docvocol) {
		Collection<ErrorVO> errs = new ArrayList<ErrorVO>();
		AirlineDelegate airlineDelegate = new AirlineDelegate();

		reserveAWBVO.setReservationDate(new LocalDate(logonAttributes
				.getAirportCode(), Location.ARP, false));
		reserveAWBVO.setCompanyCode(logonAttributes.getCompanyCode());
		reserveAWBVO.setAirportCode(logonAttributes.getAirportCode());
		reserveAWBVO.setLastUpdateUser(logonAttributes.getUserId());
		reserveAWBVO.setLastUpdateTime(new LocalDate(logonAttributes
				.getAirportCode(), Location.ARP, true));
		if (form.getAwbType() != null && form.getAwbType().trim().length() > 0) {
			reserveAWBVO.setDocumentType("AWB");
			for (DocumentVO docvo : docvocol) {
				if (docvo.getDocumentSubTypeDes().trim().equals(
						form.getAwbType().trim())) {
					reserveAWBVO.setDocumentSubType(docvo.getDocumentSubType());
				}
			}
		}

		if (form.getExpiryDate() != null
				&& form.getExpiryDate().trim().length() > 0) {
			LocalDate localdate = new LocalDate(logonAttributes
					.getAirportCode(), Location.ARP, false);
			reserveAWBVO.setExpiryDate(localdate.setDate(form.getExpiryDate()));
		}
		if (form.getRemarks() != null && form.getRemarks().trim().length() > 0) {
			reserveAWBVO.setRemarks(form.getRemarks());
		}
		if (form.getGeneral() != null && form.getGeneral().trim().length() > 0) {
				if (form.getTotAwb() == null
						|| form.getTotAwb().trim().length() == 0) {
					ErrorVO error = new ErrorVO(
							"stockcontrol.defaults.cto.noofdocempty");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errs.add(error);
				}else{
					reserveAWBVO.setGeneralMode(true);
					reserveAWBVO
							.setNumberOfDocuments(Integer.valueOf(form.getTotAwb()));
				}
		}
		if (form.getSpecific() != null
				&& form.getSpecific().trim().length() > 0) {
			String[] awbnumbers = form.getAwbNumber();
			String[] opflag = form.getReservationOperationFlag();
			if(awbnumbers==null || awbnumbers.length==0){
				ErrorVO error = new ErrorVO(
				"stockcontrol.defaults.cto.awbnumbersempty");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errs.add(error);
			}else{
				reserveAWBVO.setSpecificMode(true);
				Collection<String> awbNum = new ArrayList<String>();
				if (awbnumbers != null && awbnumbers.length > 0) {
					int i = 0;
					for (String awb : awbnumbers) {
						if (awb != null && awb.trim().length() > 0 &&
								!NOOP.equals(opflag[i]) &&
					!"D".equals(opflag[i])) {
							log.log(Log.INFO, "opflag[i]", opflag, i);
							awbNum.add(awb);
						}
						i++;
					}
					if (awbNum != null && awbNum.size() > 0) {
						reserveAWBVO.setSpecificDocNumbers(awbNum);
					} else {
						reserveAWBVO.setSpecificDocNumbers(null);
					}
				} else {
					reserveAWBVO.setSpecificDocNumbers(null);

				}
			}
		}

		if (form.getAirline() != null && form.getAirline().trim().length() > 0) {
				try {
					AirlineValidationVO airlineVO = airlineDelegate
							.validateAlphaCode(logonAttributes.getCompanyCode(),
									form.getAirline().toUpperCase());
					reserveAWBVO.setShipmentPrefix(airlineVO.getNumericCode());
					reserveAWBVO.setAirlineIdentifier(airlineVO
							.getAirlineIdentifier());
					reserveAWBVO.setAirlineCode(form.getAirline().trim()
							.toUpperCase());
					form.setShpPrefix(airlineVO.getNumericCode());
				} catch (BusinessDelegateException e) {
					errs = handleDelegateException(e);
				}
		}

		if (form.getCustCode() != null
						&& form.getCustCode().trim().length() > 0) {
				CustomerDelegate customerDelegate = new CustomerDelegate();
				Map<String, CustomerValidationVO> map = new HashMap<String, CustomerValidationVO>();
				Collection<String> customer = new ArrayList<String>();
				customer.add(form.getCustCode().trim().toUpperCase());
				try {
					map = customerDelegate.validateCustomers(logonAttributes
							.getCompanyCode(), customer);
					CustomerValidationVO customerValidationVO = map.get(form
							.getCustCode().trim().toUpperCase());
					reserveAWBVO.setCustomerCode(customerValidationVO
							.getCustomerCode());
				} catch (BusinessDelegateException e) {
					errs = handleDelegateException(e);
				}
				reserveAWBVO.setCustomerCode(form.getCustCode().toUpperCase());
		}
		return errs;
	}

}
