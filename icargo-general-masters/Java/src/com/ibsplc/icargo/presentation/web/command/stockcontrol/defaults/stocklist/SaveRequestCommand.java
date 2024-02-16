/*
 * SaveRequestCommand.java Created on Jan 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.stocklist;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestForOALVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockListSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.CreateStockRequestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 * 
 */
public class SaveRequestCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	private static final String ERROR_FWD = "screenload_failure";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		CreateStockRequestForm form = (CreateStockRequestForm) invocationContext.screenModel;
		StockControlDefaultsDelegate delegate = new StockControlDefaultsDelegate();
		StockListSession session = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.cto.stocklist");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		StockRequestForOALVO stockRequestForOALVO = new StockRequestForOALVO();
		errors = validateForm(form);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = ERROR_FWD;
		} else {
			errorVOs = getStockRequestForOALVOForSave(form, logonAttributes,
					stockRequestForOALVO, session.getDocumentVO());
			if (errorVOs != null && errorVOs.size() > 0) {
				invocationContext.addAllError(errorVOs);
				invocationContext.target = ERROR_FWD;
			} else {
				try {
					delegate.saveStockRequestForOAL(stockRequestForOALVO);
				} catch (BusinessDelegateException e) {
//printStackTrrace()();
					handleDelegateException(e);
				}
				form.setAfterSave("Y");
				invocationContext.target = "screenload_success";
			}
		}

	}

	private Collection<ErrorVO> getStockRequestForOALVOForSave(
			CreateStockRequestForm frm, LogonAttributes attributes,
			StockRequestForOALVO vo, Collection<DocumentVO> docVOCOL) {
		Collection<ErrorVO> errorvos = new ArrayList<ErrorVO>();

		if (frm.getAirline() != null && frm.getAirline().trim().length() > 0) {
			AirlineDelegate airlineDelegate = new AirlineDelegate();
			try {
				AirlineValidationVO airlineVO = airlineDelegate
						.validateAlphaCode(attributes.getCompanyCode(), frm
								.getAirline().toUpperCase());
				vo.setAirlineId(airlineVO.getAirlineIdentifier());
			} catch (BusinessDelegateException e) {
//printStackTrrace()();
				errorvos = handleDelegateException(e);
			}
		}
		if (frm.getDocumentType() != null
				&& frm.getDocumentType().trim().length() > 0) {
			vo.setDocumentType(frm.getDocumentType());
		}

		vo.setRequestedDate(new LocalDate(attributes.getAirportCode(),
				Location.ARP, false));
		if (frm.getDocSubType() != null
				&& frm.getDocSubType().trim().length() > 0) {
			//commented by A-7364 as part of ICRD-320783
			/*for (DocumentVO docvo : docVOCOL) {
				if (docvo.getDocumentSubTypeDes().trim().equals(
						frm.getDocSubType().trim())) {
					vo.setDocumentSubType(docvo.getDocumentSubType());
				}
			}*/
			//added by A-7364 as part of ICRD-320783
			vo.setDocumentSubType(frm.getDocSubType());
		}
		if (frm.getModeOfCommunication() != null
				&& frm.getModeOfCommunication().trim().length() > 0) {
			vo.setModeOfCommunication(frm.getModeOfCommunication());
		}
		if (frm.getContent() != null && frm.getContent().trim().length() > 0) {
			vo.setContent(frm.getContent());
		}
		if (frm.getAddress() != null && frm.getAddress().trim().length() > 0) {
			vo.setAddress(frm.getAddress());
		}
		vo.setCompanyCode(attributes.getCompanyCode());
		vo.setAirportCode(attributes.getAirportCode());
		vo.setOperationFlag("I");
		return errorvos;
	}

	private Collection<ErrorVO> validateForm(CreateStockRequestForm form) {

		Collection<ErrorVO> errVos = new ArrayList<ErrorVO>();
		if (form.getAirline() == null || form.getAirline().trim().length() == 0) {
			ErrorVO error = new ErrorVO("createstockrequest.airlineempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errVos.add(error);
		}
		if (form.getDocumentType() == null
				|| form.getDocumentType().trim().length() == 0) {
			ErrorVO error = new ErrorVO("createstockrequest.doctypeempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errVos.add(error);
		}
		if (form.getDocSubType() == null
				|| form.getDocSubType().trim().length() == 0) {
			ErrorVO error = new ErrorVO("createstockrequest.docsubtypeempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errVos.add(error);
		}
		if (form.getModeOfCommunication() != null
				&& form.getModeOfCommunication().trim().length() > 0) {
			if (form.getAddress() == null
					|| form.getAddress().trim().length() == 0) {
				ErrorVO error = new ErrorVO(
						"createstockrequest.addresstypeempty");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errVos.add(error);
			}
		}
		return errVos;
	}

}
