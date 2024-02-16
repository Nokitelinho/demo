package com.ibsplc.icargo.presentation.mobility.controller.customermanagement.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.mobility.controller.AbstractController;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.exception.WSBusinessException;
import com.ibsplc.icargo.framework.web.json.vo.ErrorData;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.presentation.mobility.model.customermanagement.defaults.CustomerDetailsModel;
import com.ibsplc.icargo.presentation.mobility.model.customermanagement.defaults.CustomerRequestFilterModel;
import com.ibsplc.icargo.presentation.mobility.model.customermanagement.defaults.converter.CustomerDefaultsConverter;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.mobility.controller.customermanagement.defaults.CustomerController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8761	:	27-Mar-2019	:	Draft
 */
@Controller
@Module("customermanagement")
@SubModule("defaults")
@RequestMapping({ "customermanagement/defaults" })
public class CustomerController extends AbstractController {

	private Log log = LogFactory.getLogger("CUSTOMERMANAGEMENT DEFAULTS");
	private static final String CLASS_NAME = "CustomerController";
	private static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "error";
	public static final String NO_DATA = "nodata";

	/**
	 * 
	 * 	Method		:	CustomerController.findAllCustomers
	 *	Added by 	:	A-8761 on 27-Mar-2019
	 * 	Used for 	:
	 *	Parameters	:	@param customerRequestFilterModel
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ResponseVO
	 */
	
	@RequestMapping({ "/findAllCustomers" })
	public @ResponseBody ResponseVO findAllCustomers(@RequestBody CustomerRequestFilterModel customerRequestFilterModel)
			throws SystemException {
		log.entering(CLASS_NAME, "findAllCustomers");
		ResponseVO responseVO = new ResponseVO();
		responseVO.setStatus(NO_DATA);
		CustomerFilterVO customerFilterVO = CustomerDefaultsConverter
				.populateCustomerFilterVO(customerRequestFilterModel);
		List<CustomerDetailsModel> response = new ArrayList<CustomerDetailsModel>();

		try {

			Collection<CustomerVO> customerVOs = (Collection) despatchRequest("getAllCustomers",
					new Object[] { customerFilterVO });

			if (customerVOs != null && !customerVOs.isEmpty()) {
				CustomerDetailsModel customerResponseModel = CustomerDefaultsConverter
						.populateCustomerResponseModel(customerVOs);
				response.add(customerResponseModel);
			}

			responseVO.setStatus(SUCCESS);
			responseVO.setResults(response);

		} catch (WSBusinessException wsBusinessException) {
			handleWSBusinessException(responseVO, wsBusinessException);
		} catch (SystemException systemException) {
			handleSystemException(responseVO, systemException);
		}
		log.exiting(CLASS_NAME, "findAllCustomers");
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
