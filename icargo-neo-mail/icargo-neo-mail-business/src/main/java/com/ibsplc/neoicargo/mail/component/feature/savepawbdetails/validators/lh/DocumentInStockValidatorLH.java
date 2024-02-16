package com.ibsplc.neoicargo.mail.component.feature.savepawbdetails.validators.lh;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.mail.component.feature.savepawbdetails.validators.DocumentInStockValidator;
import com.ibsplc.neoicargo.mail.component.proxy.webservices.lh.MailStockRetrievalWSProxy;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.mail.vo.CarditPawbDetailsVO;
import com.ibsplc.neoicargo.mail.vo.CarditVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.masters.ParameterService;
import com.ibsplc.neoicargo.masters.ParameterType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ibsplc.neoicargo.framework.orchestration.Validator;

@Component("LH-mail.operations.feature.savepawbdetails.validators.documentinstockvalidator")
@Slf4j
public class DocumentInStockValidatorLH  extends Validator<CarditVO> {
	@Autowired
	private ParameterService parameterService;
	@Autowired
	private MailStockRetrievalWSProxy mailStockRetrievalWSProxy;
	@Autowired
	private ContextUtil contextUtil;
	private static final String OPERATION_SHIPMENT_STOCK_CHECK = "operations.shipment.stockcheckrequired";
	private static final String DOCUMENT_TYPE = "AWB";
	public static final String MAIL_AWB_PRODUCT = "mail.operations.productCode";
	public static final String MODULE_SUBMODULE = "MAIL OPERATIONS";
//	@Autowired
//	@Qualifier("mailController")
//	private MailController mailController;

	@Override
	public void validate(CarditVO carditVO) throws BusinessException {
		if (carditVO.getCarditPawbDetailsVO() == null) {
			throw new MailOperationsBusinessException(
					MailOperationsBusinessException.REQUIRED_INFO_FOR_CREATING_PAWB_MISSING);
		} else {
			CarditPawbDetailsVO carditPawbDetail = carditVO.getCarditPawbDetailsVO();
			boolean isStockRetrievalRequired = true;
			if (MailConstantsVO.CDT_TYP_CANCEL.equalsIgnoreCase(carditVO.getCarditType())) {
				if (carditPawbDetail.getMasterDocumentNumber() == null
						&& carditPawbDetail.getConsignmentDocumentVO().getConsignmentMasterDocumentNumber() != null) {
					carditPawbDetail.setMasterDocumentNumber(
							carditPawbDetail.getConsignmentDocumentVO().getConsignmentMasterDocumentNumber());
				}
			} else {
				if (!MailConstantsVO.CARDIT_TYPE_ACP.equalsIgnoreCase(carditVO.getCarditType())
						&& carditPawbDetail.getMasterDocumentNumber() == null && carditPawbDetail
								.getExistingMailBagsInConsignment().getConsignmentMasterDocumentNumber() != null) {
					carditPawbDetail.setMasterDocumentNumber(
							carditPawbDetail.getExistingMailBagsInConsignment().getConsignmentMasterDocumentNumber());
				}
			}
			int airlineIdentifier = getAirlineIdentifier(carditVO);
			carditPawbDetail.setOwnerId(airlineIdentifier);
			if (!MailConstantsVO.CARDIT_TYPE_ACP.equalsIgnoreCase(carditVO.getCarditType())) {
				setCarditPAWBDetails(carditVO, carditPawbDetail, isStockRetrievalRequired);
			}
		}
	}

	@SneakyThrows
	private void setCarditPAWBDetails(CarditVO carditVO, CarditPawbDetailsVO carditPawbDetail,
			boolean isStockRetrievalRequired) throws MailOperationsBusinessException {
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(carditVO.getCompanyCode());
		documentFilterVO.setShipmentPrefix(carditPawbDetail.getShipmentPrefix());
		documentFilterVO.setDocumentNumber(carditPawbDetail.getMasterDocumentNumber());
		documentFilterVO.setDocumentType(DOCUMENT_TYPE);
		documentFilterVO.setStockOwner(carditPawbDetail.getAgentCode());
		if (carditPawbDetail.getConsignmentOriginAirport() != null) {
			documentFilterVO.setAwbOrigin(carditPawbDetail.getConsignmentOriginAirport());
		}
		if (carditPawbDetail.getConsignmentDestinationAirport() != null) {
			documentFilterVO.setAwbDestination(carditPawbDetail.getConsignmentDestinationAirport());
		}
		String isStockCheckEnabled = parameterService.getParameter(OPERATION_SHIPMENT_STOCK_CHECK,
				ParameterType.SYSTEM_PARAMETER);
		if (carditPawbDetail.getExistingMailBagsInConsignment() != null
				&& carditPawbDetail.getExistingMailBagsInConsignment().getConsignmentMasterDocumentNumber() != null) {
			isStockCheckEnabled = parameterService.getParameter(OPERATION_SHIPMENT_STOCK_CHECK,
					ParameterType.SYSTEM_PARAMETER);
		}
		if (carditPawbDetail.getExistingMailBagsInConsignment() != null) {
			isStockCheckEnabled = parameterService.getParameter(OPERATION_SHIPMENT_STOCK_CHECK,
					ParameterType.SYSTEM_PARAMETER);
		}
		isStockRetrievalRequired = setShipmentPrefixAndMstDocNumForCarditPAWBDetails(carditVO, carditPawbDetail,
				isStockRetrievalRequired);
		if (isStockCheckEnabled.contentEquals("N") && isStockRetrievalRequired) {
			DocumentFilterVO documentFilterVOResponse = null;
			try {
				documentFilterVOResponse = mailStockRetrievalWSProxy.stockRetrievalForPAWB(documentFilterVO);
			} finally {
			}
			if (documentFilterVOResponse != null) {
				carditPawbDetail.setMasterDocumentNumber(documentFilterVOResponse.getDocumentNumber());
				carditVO.setCarditPawbDetailsVO(carditPawbDetail);
			}
			if (documentFilterVOResponse == null || documentFilterVOResponse.getDocumentNumber() == null) {
				throw new MailOperationsBusinessException(MailOperationsBusinessException.INVALID_PAWB_STOCK);
			}
		}
	}

	private boolean setShipmentPrefixAndMstDocNumForCarditPAWBDetails(CarditVO carditVO,
			CarditPawbDetailsVO carditPawbDetail, boolean isStockRetrievalRequired) {
		if (!MailConstantsVO.CDT_TYP_CANCEL.equalsIgnoreCase(carditVO.getCarditType())
				&& carditPawbDetail.getExistingMailBagsInConsignment() != null
				&& carditPawbDetail.getExistingMailBagsInConsignment().getConsignmentMasterDocumentNumber() != null) {
			carditPawbDetail.setMasterDocumentNumber(
					carditPawbDetail.getExistingMailBagsInConsignment().getConsignmentMasterDocumentNumber());
			carditPawbDetail.setShipmentPrefix(carditPawbDetail.getShipmentPrefix());
			isStockRetrievalRequired = false;
		}
		return isStockRetrievalRequired;
	}

	private int getAirlineIdentifier(CarditVO carditVO) throws BusinessException {
		AirlineValidationVO airlineValidationVO = null;
		CarditPawbDetailsVO carditPawbDetail = carditVO.getCarditPawbDetailsVO();
		int airlineIdentifier = 0;
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		airlineIdentifier = logonAttributes.getOwnAirlineIdentifier();
		carditPawbDetail.setShipmentPrefix(logonAttributes.getOwnAirlineNumericCode());
		carditVO.setCarditPawbDetailsVO(carditPawbDetail);
		return airlineIdentifier;
	}
}
