/*
 * MailOutboundActionController.java Created on Apr 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.mobility.controller.mail.operations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.mobility.controller.AbstractController;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.exception.WSBusinessException;
import com.ibsplc.icargo.framework.web.json.vo.ErrorData;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.presentation.mobility.model.mail.operations.converter.MailOutboundModelConverter;
import com.ibsplc.icargo.presentation.mobility.model.mail.operations.mailoutbound.MailOutboundModel;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Created by A-9498 on 17-03-2020.
 */
@Controller
@Module("mail")
@SubModule("operations")
@RequestMapping({ "mail/operations/mailoutbound" })
public class MailOutboundActionController extends AbstractController {
	private Log log = LogFactory.getLogger("MAIL OPERATIONS MAILOUTBOUND");
	private static final String CLASS_NAME = MailOutboundActionController.class.getName();
	public static final String SUCCESS = "success";
	public static final String FAILED = "failed";
	public static final String ERROR = "error";
	public static final String NO_DATA = "nodata";
	public static final String FETCHCONTAINERDETAILS_METHOD = "fetchContainerDetails";
	

	@RequestMapping(value = "/saveoutboundDetails", method = RequestMethod.POST)
	
	public @ResponseBody ResponseVO saveoutboundDetails(@RequestBody MailOutboundModel mailOutboundModel)
			throws SystemException, WSBusinessException {
		log.entering(CLASS_NAME, "saveOutboundDetails");
		ResponseVO responseVO = new ResponseVO();
		responseVO.setStatus(NO_DATA);
		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO = MailOutboundModelConverter.populateMailUploadVO(mailOutboundModel, mailUploadVO);
		String scanningPort = mailOutboundModel.getAirportCode();
		try {
			despatchRequest("saveMailUploadDetailsForAndroid", mailUploadVO, scanningPort);
			responseVO.setStatus(SUCCESS);
		} catch (WSBusinessException e) {
			handleWSBusinessException(responseVO, e);
		} catch (SystemException systemException) {
			handleSystemException(responseVO, systemException);
		}
		log.exiting(CLASS_NAME, "saveOutboundDetails");
		return responseVO;
	}

	@RequestMapping({ "/fetchContainerDetails" })
	public @ResponseBody ResponseVO fetchContainerDetails(@RequestBody MailOutboundModel mailOutboundModel)
			throws SystemException, WSBusinessException {
		log.entering(CLASS_NAME, FETCHCONTAINERDETAILS_METHOD);
		ResponseVO responseVO = new ResponseVO();
		responseVO.setStatus(NO_DATA);
		MailUploadVO mailUploadVO = new MailUploadVO();
		ScannedMailDetailsVO resultMailbagVo = new ScannedMailDetailsVO();
		mailUploadVO = MailOutboundModelConverter.populateMailUploadVOForContainer(mailOutboundModel, mailUploadVO);
		try {
			resultMailbagVo = despatchRequest("validateFlightAndContainer", mailUploadVO);
			responseVO.setStatus(SUCCESS);
		} catch (WSBusinessException e) {
			handleWSBusinessException(responseVO, e);
		} catch (SystemException systemException) {
			handleSystemException(responseVO, systemException);
		}
		if (resultMailbagVo == null) {
			log.exiting(CLASS_NAME, FETCHCONTAINERDETAILS_METHOD);
		}
		if (null != resultMailbagVo) {
			responseVO.setResults(Arrays.asList(resultMailbagVo));
		}

		log.exiting(CLASS_NAME, FETCHCONTAINERDETAILS_METHOD);
		return responseVO;

	}

	private void handleWSBusinessException(ResponseVO responseVO, WSBusinessException wsBusinessException) {
		log.entering(CLASS_NAME, "handleWSBusinessException");
		if ((wsBusinessException.getErrors() != null) && (!wsBusinessException.getErrors().isEmpty())) {
			if (responseVO == null) {
				responseVO = new ResponseVO();
			}
			Map<String, List<ErrorData>> errorWarnings = new HashMap<>();
			errorWarnings.put(ErrorDisplayType.ERROR.toString(), new ArrayList<ErrorData>());
			errorWarnings.put(ErrorDisplayType.WARNING.toString(), new ArrayList<ErrorData>());
			ErrorData errorData = null;
			for (ErrorVO errorVO : wsBusinessException.getErrors()) {
				errorData = new ErrorData();
				errorData.setCode(errorVO.getErrorCode());
				if (ErrorDisplayType.WARNING.equals(errorVO.getErrorDisplayType()))
					errorWarnings.get(ErrorDisplayType.WARNING.toString()).add(errorData);
				else {
					errorWarnings.get(ErrorDisplayType.ERROR.toString()).add(errorData);
				}
			}
			responseVO.setStatus(ERROR);
			responseVO.setErrors(errorWarnings);
		}
		log.exiting(CLASS_NAME, "handleWSBusinessException");
	}

	private void handleSystemException(ResponseVO responseVO, SystemException systemException) {
		log.entering(CLASS_NAME, "handleSystemException");
		if ((systemException.getErrors() != null) && (!systemException.getErrors().isEmpty())) {
			Map<String, List<ErrorData>> errorWarnings = new HashMap<>();
			errorWarnings.put(ErrorDisplayType.ERROR.toString(), new ArrayList<ErrorData>());
			errorWarnings.put(ErrorDisplayType.WARNING.toString(), new ArrayList<ErrorData>());
			ErrorData errorData = null;
			for (ErrorVO errorVO : systemException.getErrors()) {
				errorData = new ErrorData();
				errorData.setCode(errorVO.getErrorCode());
				if (ErrorDisplayType.WARNING.equals(errorVO.getErrorDisplayType()))
					errorWarnings.get(ErrorDisplayType.WARNING.toString()).add(errorData);
				else {
					errorWarnings.get(ErrorDisplayType.ERROR.toString()).add(errorData);
				}
			}
			responseVO.setStatus(ERROR);
			responseVO.setErrors(errorWarnings);
		}
		log.exiting(CLASS_NAME, "handleSystemException");
	}

}
