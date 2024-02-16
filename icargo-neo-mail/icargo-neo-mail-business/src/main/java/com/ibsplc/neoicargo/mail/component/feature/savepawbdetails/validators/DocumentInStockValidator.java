package com.ibsplc.neoicargo.mail.component.feature.savepawbdetails.validators;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.AWBDocumentValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.component.proxy.OperationsShipmentProxy;
import com.ibsplc.neoicargo.mail.component.proxy.ProductDefaultsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.StockcontrolDefaultsProxy;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.mail.exception.StockcontrolDefaultsProxyException;
import com.ibsplc.neoicargo.mail.vo.CarditPawbDetailsVO;
import com.ibsplc.neoicargo.mail.vo.CarditVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.masters.ParameterService;
import com.ibsplc.neoicargo.masters.ParameterType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

@Component("documentinstockvalidator")
@Slf4j
public class DocumentInStockValidator extends Validator<CarditVO> {
	@Autowired
	private ParameterService parameterService;
	@Autowired
	private OperationsShipmentProxy operationsShipmentProxy;
	@Autowired
	private ProductDefaultsProxy productDefaultsProxy;
	@Autowired
	private StockcontrolDefaultsProxy stockcontrolDefaultsProxy;
	@Autowired
	private ContextUtil contextUtil;
	private static final String OPERATION_SHIPMENT_STOCK_CHECK = "operations.shipment.stockcheckrequired";
	private static final String DOCUMENT_TYPE = "AWB";
	public static final String MAIL_AWB_PRODUCT = "mail.operations.productCode";

//	@Qualifier("mailController")
//	private MailController mailController;

	@SneakyThrows
	@Override
	public void validate(CarditVO carditVO)  throws BusinessException{
		if (carditVO.getCarditPawbDetailsVO() == null) {
			throw new MailOperationsBusinessException(
					MailOperationsBusinessException.REQUIRED_INFO_FOR_CREATING_PAWB_MISSING);
		} else {
			boolean isStockValidationRequired = true;
			CarditPawbDetailsVO carditPawbDetail = carditVO.getCarditPawbDetailsVO();
			if (MailConstantsVO.CDT_TYP_CANCEL.equalsIgnoreCase(carditVO.getCarditType())) {
				if (carditPawbDetail.getMasterDocumentNumber() == null
						&& carditPawbDetail.getConsignmentDocumentVO().getConsignmentMasterDocumentNumber() != null) {
					carditPawbDetail.setMasterDocumentNumber(
							carditPawbDetail.getConsignmentDocumentVO().getConsignmentMasterDocumentNumber());
				}
			} else {
				if (carditPawbDetail.getMasterDocumentNumber() == null && carditPawbDetail
						.getExistingMailBagsInConsignment().getConsignmentMasterDocumentNumber() != null) {
					carditPawbDetail.setMasterDocumentNumber(
							carditPawbDetail.getExistingMailBagsInConsignment().getConsignmentMasterDocumentNumber());
				}
			}
			LoginProfile logonAttributes = contextUtil.callerLoginProfile();
			int airlineIdentifier = getAirlineIdentifier(carditVO, logonAttributes);
			if (airlineIdentifier != logonAttributes.getOwnAirlineIdentifier()) {
				isStockValidationRequired = false;
			}
			DocumentFilterVO documentFilterVO = new DocumentFilterVO();
			documentFilterVO.setCompanyCode(carditVO.getCompanyCode());
			documentFilterVO.setAirlineIdentifier(airlineIdentifier);
			documentFilterVO.setDocumentNumber(carditPawbDetail.getMasterDocumentNumber());
			documentFilterVO.setDocumentType(DOCUMENT_TYPE);
			carditPawbDetail.setOwnerId(airlineIdentifier);
			Collection<StockAgentVO> stockAgentVOs = null;
			StockAgentFilterVO stockAgentFilterVO = new StockAgentFilterVO();
			stockAgentFilterVO.setCompanyCode(carditVO.getCompanyCode());
			stockAgentFilterVO.setAgentCode(carditPawbDetail.getAgentCode());
			stockAgentFilterVO.setPageNumber(1);
			stockAgentVOs = stockcontrolDefaultsProxy.findStockAgentMappings(stockAgentFilterVO);
			StockAgentVO stockAgentVO = new StockAgentVO();
			if (Objects.nonNull(stockAgentVOs) && !stockAgentVOs.isEmpty()) {
				stockAgentVO = stockAgentVOs.stream().findFirst().get();
			}
			documentFilterVO.setStockOwner(stockAgentVO.getStockHolderCode() != null ? stockAgentVO.getStockHolderCode()
					: carditPawbDetail.getAgentCode());
			if (carditPawbDetail.getConsignmentOriginAirport() != null) {
				documentFilterVO.setAwbOrigin(carditPawbDetail.getConsignmentOriginAirport());
			}
			if (carditPawbDetail.getConsignmentDestinationAirport() != null) {
				documentFilterVO.setAwbDestination(carditPawbDetail.getConsignmentDestinationAirport());
			}
			String subType = null;
			subType = stockcontrolDefaultsProxy.findAutoPopulateSubtype(documentFilterVO);
			if (subType != null && subType.trim().length() > 0) {
				documentFilterVO.setDocumentSubType(subType);
			} else {
				String product =new MailController().findSystemParameterValue(MAIL_AWB_PRODUCT);
				//String product = parameterService.getParameter(MAIL_AWB_PRODUCT, ParameterType.SYSTEM_PARAMETER);
				Collection<ProductValidationVO> productVOs = null;
				ProductVO productVO;
				ProductValidationVO productValidationVO = null;
				String companyCode = carditVO.getCompanyCode();
				productVOs = productDefaultsProxy.findProductsByName(companyCode, product);
				if (productVOs != null) {
					productValidationVO = productVOs.iterator().next();
					productVO = productDefaultsProxy.findProductDetails(companyCode,
							productValidationVO.getProductCode());
					documentFilterVO.setDocumentSubType(productVO.getDocumentSubType());
					subType = productVO.getDocumentSubType();
				}
			}
			carditPawbDetail.setSubType(subType);
			carditVO.setCarditPawbDetailsVO(carditPawbDetail);
			String isStockCheckEnabled = new MailController().findSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK);
//					parameterService.getParameter(OPERATION_SHIPMENT_STOCK_CHECK,
//					ParameterType.SYSTEM_PARAMETER);
			if (isStockCheckEnabled.contentEquals("Y")) {
				DocumentValidationVO documentValidationVO = null;
				if (carditPawbDetail.getMasterDocumentNumber() != null
						&& !carditPawbDetail.getMasterDocumentNumber().isEmpty()) {
					if (MailConstantsVO.CDT_TYP_CANCEL.equalsIgnoreCase(carditVO.getCarditType())
							|| (!MailConstantsVO.CDT_TYP_CANCEL.equalsIgnoreCase(carditVO.getCarditType())
							&& carditPawbDetail.getExistingMailBagsInConsignment()
							.getConsignmentMasterDocumentNumber() != null
							&& carditPawbDetail.getExistingMailBagsInConsignment()
							.getConsignmentMasterDocumentNumber()
							.equals(carditPawbDetail.getMasterDocumentNumber()))) {
						isStockValidationRequired = false;
					}
					if (isStockValidationRequired) {

						documentValidationVO = stockcontrolDefaultsProxy
								.validateDocumentInStock(documentFilterVO);

					}else {
						documentValidationVO = new AWBDocumentValidationVO();
					}
				} else {
					try {
						documentValidationVO =  stockcontrolDefaultsProxy
								.findNextDocumentNumber(documentFilterVO);
						if (documentValidationVO != null) {
							carditPawbDetail.setMasterDocumentNumber(documentValidationVO.getDocumentNumber());
							carditVO.setCarditPawbDetailsVO(carditPawbDetail);
						}
					} catch (StockcontrolDefaultsProxyException e) {
						documentValidationVO = null;
						log.error("Exception :", e);
					}
				}
				if (documentValidationVO == null) {
					throw new BusinessException(MailOperationsBusinessException.INVALID_PAWB_STOCK,MailOperationsBusinessException.INVALID_PAWB_STOCK);
				}
			}
		}
	}

	private int getAirlineIdentifier(CarditVO carditVO, LoginProfile logonAttributes) throws BusinessException {
		AirlineValidationVO airlineValidationVO = null;
		CarditPawbDetailsVO carditPawbDetail = carditVO.getCarditPawbDetailsVO();
		if (carditPawbDetail.getShipmentPrefix() != null) {
			airlineValidationVO = operationsShipmentProxy.validateNumericCode(carditVO.getCompanyCode(),
					carditPawbDetail.getShipmentPrefix());
		}
		int airlineIdentifier = 0;
		if (airlineValidationVO != null) {
			airlineIdentifier = airlineValidationVO.getAirlineIdentifier();
		} else {
			airlineIdentifier = logonAttributes.getOwnAirlineIdentifier();
			carditPawbDetail.setShipmentPrefix(logonAttributes.getOwnAirlineNumericCode());
			carditVO.setCarditPawbDetailsVO(carditPawbDetail);
		}
		return airlineIdentifier;
	}
}
