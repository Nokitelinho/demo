package com.ibsplc.icargo.presentation.mobility.controller.uld.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.framework.mobility.controller.AbstractController;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.exception.WSBusinessException;
import com.ibsplc.icargo.framework.web.json.vo.ErrorData;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.presentation.mobility.model.uld.defaults.UldAirportLocationFilterModel;
import com.ibsplc.icargo.presentation.mobility.model.uld.defaults.UldAirportLocationModel;
import com.ibsplc.icargo.presentation.mobility.model.uld.defaults.UldDamageChecklistFilterModel;
import com.ibsplc.icargo.presentation.mobility.model.uld.defaults.UldDamageChecklistModel;
import com.ibsplc.icargo.presentation.mobility.model.uld.defaults.UldDamageModel;
import com.ibsplc.icargo.presentation.mobility.model.uld.defaults.converter.UldDefaultsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.mobility.controller.uld.defaults.UldMasterDataController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8761	:	26-Mar-2019	:	Draft
 */

@Controller
@Module("uld")
@SubModule("defaults")
@RequestMapping({ "uld/defaults" })
public class UldMasterDataController extends AbstractController {

	private Log log = LogFactory.getLogger("ULD DEFAULTS");
	private static final String CLASS_NAME = "UldMasterDataController";
	private static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "error";
	public static final String NO_DATA = "nodata";

	@RequestMapping({ "/findULDDamageChecklist" })
	public @ResponseBody ResponseVO findULDDamageChecklist(
			@RequestBody UldDamageChecklistFilterModel uldDamageChecklistFilterModel) throws SystemException {
		log.entering(CLASS_NAME, "findULDDamageChecklist");
		ResponseVO responseVO = new ResponseVO();
		responseVO.setStatus(NO_DATA);
		Collection<ULDDamageChecklistVO> uldDamageChecklistVOs = new ArrayList<ULDDamageChecklistVO>();
		Collection<UldDamageChecklistModel> uldDamageChecklistModels = new ArrayList<UldDamageChecklistModel>();
		try {
			uldDamageChecklistVOs = (Collection) despatchRequest("listULDDamageChecklistMaster", new Object[] {
					uldDamageChecklistFilterModel.getCompanyCode(), uldDamageChecklistFilterModel.getSection() });
			if ((uldDamageChecklistVOs != null) && (!uldDamageChecklistVOs.isEmpty()))
				uldDamageChecklistModels = UldDefaultsModelConverter
						.populateUldDamageChecklistModel(uldDamageChecklistVOs);
			responseVO.setResults(uldDamageChecklistModels);
			responseVO.setStatus(SUCCESS);
		} catch (WSBusinessException businessException) {
			handleWSBusinessException(responseVO, businessException);
		}

		log.exiting(CLASS_NAME, "findULDDamageChecklist");
		return responseVO;
	}

	@RequestMapping({ "/findULDAirportLocations" })
	public @ResponseBody ResponseVO findULDAirportLocations(
			@RequestBody UldAirportLocationFilterModel uldAirportLocationFilterModel) throws SystemException {

		log.entering(CLASS_NAME, "findULDAirportLocations");
		ResponseVO responseVO = new ResponseVO();
		responseVO.setStatus(NO_DATA);
		List<UldAirportLocationModel> uldAirportLocationModels = new ArrayList<UldAirportLocationModel>();
		try {
			Collection<ULDAirportLocationVO> uldAirportLocationVOs = (Collection<ULDAirportLocationVO>) despatchRequest(
					"listULDAirportLocation",
					new Object[] { uldAirportLocationFilterModel.getCompanyCode(),
							uldAirportLocationFilterModel.getAirportCode(),
							uldAirportLocationFilterModel.getFacilitytype() });

			if ((uldAirportLocationVOs != null) && (uldAirportLocationVOs.size() > 0)) {
				uldAirportLocationModels = UldDefaultsModelConverter
						.populateUldAirportLocationModels(uldAirportLocationVOs);
			}
			responseVO.setResults(uldAirportLocationModels);
			responseVO.setStatus(SUCCESS);
		} catch (WSBusinessException businessException) {
			handleWSBusinessException(responseVO, businessException);
		}

		log.exiting(CLASS_NAME, "findULDAirportLocations");
		return responseVO;
	}

	@RequestMapping({ "/saveULDDamageDetails" })
	public @ResponseBody ResponseVO saveULDDamageDetails(@RequestBody UldDamageModel uldDamageModel)
			throws SystemException {

		log.entering(CLASS_NAME, "saveULDDamageDetails");
		ResponseVO responseVO = new ResponseVO();
		responseVO.setStatus(NO_DATA);

		ULDDamageRepairDetailsVO uldDamageRepairDetailsVO = UldDefaultsModelConverter
				.populateULDDamageRepairDetailsVO(uldDamageModel);
		try {
			despatchRequest("saveULDDamage", new Object[] { uldDamageRepairDetailsVO });
			responseVO.setStatus(SUCCESS);
		} catch (WSBusinessException businessException) {
			handleWSBusinessException(responseVO, businessException);
		} catch (SystemException systemException) {
			handleSystemException(responseVO, systemException);
		}

		log.exiting(CLASS_NAME, "saveULDDamageDetails");
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
