package com.ibsplc.icargo.presentation.mobility.controller.mail.operations;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.mobility.controller.AbstractController;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.exception.WSBusinessException;

import com.ibsplc.icargo.framework.web.json.vo.ErrorData;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.presentation.mobility.model.mail.operations.converter.MailOffloadModelConverter;
import com.ibsplc.icargo.presentation.mobility.model.mail.operations.mailoffload.MailOffloadModel;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@Controller
@Module("mail")
@SubModule("operations")
@RequestMapping({"mail/operations/mailoffload"})
public class MailOffloadActionController extends AbstractController {
	private Log log = LogFactory.getLogger("MAIL OPERATIONS MAILOFFLOAD");
	private static final String CLASS_NAME = MailOffloadActionController.class.getName();
	public static final String SUCCESS = "success";
	public static final String FAILED = "ailed";
	public static final String ERROR = "error";
	public static final String NO_DATA = "nodata";
	
    @RequestMapping({ "/saveoffloadDetails" })
	public @ResponseBody ResponseVO saveoffloadDetails(@RequestBody MailOffloadModel mailOffloadModel)
			throws SystemException, WSBusinessException {
			log.entering(CLASS_NAME, "saveoffloadDetails");
			ResponseVO responseVO = new ResponseVO();
			responseVO.setStatus(NO_DATA);
			ScannedMailDetailsVO shipmentScreeningVOs =new ScannedMailDetailsVO();
			MailUploadVO mailUploadVO =new MailUploadVO();
			mailUploadVO = MailOffloadModelConverter.populateMailUploadVO(mailOffloadModel, mailUploadVO);
			
			String scanningPort=mailOffloadModel.getAirportCode();
        	try {
				despatchRequest("saveMailUploadDetailsForAndroid", mailUploadVO,scanningPort);
				responseVO.setStatus(SUCCESS);
				
			}  catch (WSBusinessException e) {
				handleWSBusinessException(responseVO, e);
			} catch (SystemException systemException) {
				handleSystemException(responseVO, systemException);
			}
			
			log.exiting(CLASS_NAME, "saveScreeningDetails");
			return responseVO;
			
			}
    
	
	private void handleWSBusinessException(ResponseVO responseVO, WSBusinessException wsBusinessException)
			throws SystemException {
		log.entering(CLASS_NAME, "handleWSBusinessException");
		if ((wsBusinessException.getErrors() != null) && (!wsBusinessException.getErrors().isEmpty())) {
			if (responseVO == null) {
				responseVO = new ResponseVO();
			}
			Map<String, List<ErrorData>> errorWarnings = new HashMap<String, List<ErrorData>>();
			errorWarnings.put(ErrorDisplayType.ERROR.toString(), new ArrayList<ErrorData>());
			errorWarnings.put(ErrorDisplayType.WARNING.toString(), new ArrayList<ErrorData>());
			ErrorData errorData = null;
			for (ErrorVO errorVO : wsBusinessException.getErrors()) {
				errorData = new ErrorData();
				errorData.setCode(errorVO.getErrorCode());
				errorData.setDescription(errorVO.getConsoleMessage());
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
			Map<String, List<ErrorData>> errorWarnings = new HashMap<String, List<ErrorData>>();
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
