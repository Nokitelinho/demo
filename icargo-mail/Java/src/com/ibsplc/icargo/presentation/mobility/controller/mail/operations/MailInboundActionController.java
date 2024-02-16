/*
 * MailInboundActionController.java Created on Apr 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.mobility.controller.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.mobility.controller.AbstractController;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.exception.WSBusinessException;
import com.ibsplc.icargo.framework.web.json.vo.ErrorData;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.presentation.mobility.model.mail.operations.converter.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.mobility.model.mail.operations.mailinbound.MailInboundModel;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;

/**
 * Created by A-9529 on 01-04-2020.
 */
@Controller
@Module("mail")
@SubModule("operations")
@RequestMapping({"mail/operations/mailinbound"})
public class MailInboundActionController extends AbstractController{

    private Log log = LogFactory.getLogger("MAIL OPERATIONS MAIL INBOUND");
    private static final String CLASS_NAME = MailInboundActionController.class.getName();
    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    public static final String ERROR = "error";
    public static final String NO_DATA = "nodata";
    public static final String FETCHCONTAINERDETAILS_METHOD = "fetchContainerDetails";

    @RequestMapping({ "/savemailinbounddetails" })
    public @ResponseBody ResponseVO saveinboundDetails(@RequestBody MailInboundModel mailInboundModel)
            throws SystemException, WSBusinessException {
        log.entering(CLASS_NAME, "saveInBoundDetails");
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(NO_DATA);
        MailUploadVO mailUploadVO =new MailUploadVO();
        mailUploadVO = MailInboundModelConverter.populateMailUploadVO(mailInboundModel, mailUploadVO);

        String scanningPort=mailInboundModel.getAirportCode();
        try {
            despatchRequest("saveMailUploadDetailsForAndroid", mailUploadVO, scanningPort);
            responseVO.setStatus(SUCCESS);

        }  catch (WSBusinessException e) {
            handleWSBusinessException(responseVO, e);
        } catch (SystemException systemException) {
            handleSystemException(responseVO, systemException);
        }

        log.exiting(CLASS_NAME, "saveInBoundDetails");
        return responseVO;

    }

    @RequestMapping({ "/fetchContainerDetails" })
    public @ResponseBody ResponseVO fetchContainerDetails(@RequestBody MailInboundModel mailInboundModel)
            throws SystemException, WSBusinessException {
        log.entering(CLASS_NAME, FETCHCONTAINERDETAILS_METHOD);
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(NO_DATA);
        MailUploadVO mailUploadVO =new MailUploadVO();
        ScannedMailDetailsVO resultMailbagVo = new ScannedMailDetailsVO();
        mailUploadVO = MailInboundModelConverter.populateContainerMailUploadVO(mailInboundModel, mailUploadVO);
        try {
            resultMailbagVo=despatchRequest("validateFlightAndContainer", mailUploadVO);
            responseVO.setStatus(SUCCESS);
        }  catch (WSBusinessException e) {
            handleWSBusinessException(responseVO, e);
        } catch (SystemException systemException) {
            handleSystemException(responseVO, systemException);
        }
        if (resultMailbagVo == null) {
            log.exiting(CLASS_NAME, FETCHCONTAINERDETAILS_METHOD);
            return responseVO;
        }
        responseVO.setResults(Collections.singletonList(resultMailbagVo));
        log.exiting(CLASS_NAME, FETCHCONTAINERDETAILS_METHOD);
        return responseVO;

    }


    private void handleWSBusinessException(ResponseVO responseVO, WSBusinessException wsBusinessException)
    {
        log.entering(CLASS_NAME, "handleWSBusinessException");
        if ((wsBusinessException.getErrors() != null) && (!wsBusinessException.getErrors().isEmpty())) {
            if (responseVO == null) {
                responseVO = new ResponseVO();
            }
            Map<String, List<ErrorData>> errorWarnings = new HashMap<>();
            errorWarnings.put(ErrorDisplayType.ERROR.toString(), new ArrayList<>());
            errorWarnings.put(ErrorDisplayType.WARNING.toString(), new ArrayList<>());
            for (ErrorVO errorVO : wsBusinessException.getErrors()) {
                ErrorData errorData = new ErrorData();
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
            errorWarnings.put(ErrorDisplayType.ERROR.toString(), new ArrayList<>());
            errorWarnings.put(ErrorDisplayType.WARNING.toString(), new ArrayList<>());
            for (ErrorVO errorVO : systemException.getErrors()) {
                ErrorData errorData = new ErrorData();
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
