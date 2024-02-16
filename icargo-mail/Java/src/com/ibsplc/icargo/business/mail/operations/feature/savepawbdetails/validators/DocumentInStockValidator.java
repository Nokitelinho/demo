package com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.validators;

import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.StockcontrolDefaultsProxyException;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.SavePAWBDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.proxy.OperationsShipmentProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.ProductDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.CarditPawbDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.mail.operations.proxy.StockcontrolDefaultsProxy;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.AWBDocumentValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentVO;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.feature.Validator;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtil;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent(SavePAWBDetailsFeatureConstants.DOCUMENT_IN_STOCK_VALIDATOR)
public class DocumentInStockValidator extends Validator<CarditVO> {	
	private static final Log LOGGER = LogFactory.getLogger(SavePAWBDetailsFeatureConstants.MODULE_SUBMODULE);
	private static final String OPERATION_SHIPMENT_STOCK_CHECK = "operations.shipment.stockcheckrequired";
	private static final String DOCUMENT_TYPE = "AWB";
	public static final String MAIL_AWB_PRODUCT = "mail.operations.productCode";
	@Autowired
	private Proxy proxyInstance;

	@Autowired
	@Qualifier("mAilcontroller")
	private MailController mailController;

	@Override
	public void validate(CarditVO carditVO) throws BusinessException, SystemException {
		if (carditVO.getCarditPawbDetailsVO() == null) {
			throw new MailTrackingBusinessException(MailTrackingBusinessException.REQUIRED_INFO_FOR_CREATING_PAWB_MISSING);
		}else {
			boolean isStockValidationRequired  =true;
			CarditPawbDetailsVO carditPawbDetail = carditVO.getCarditPawbDetailsVO();
				if (MailConstantsVO.CDT_TYP_CANCEL.equalsIgnoreCase(carditVO.getCarditType())) {
					if(carditPawbDetail.getMasterDocumentNumber()==null && carditPawbDetail.getConsignmentDocumentVO().getConsignmentMasterDocumentNumber()!=null) {
						carditPawbDetail.setMasterDocumentNumber(carditPawbDetail.getConsignmentDocumentVO().getConsignmentMasterDocumentNumber());
				}
				}else {
					if(carditPawbDetail.getMasterDocumentNumber()==null && carditPawbDetail.getExistingMailBagsInConsignment().getConsignmentMasterDocumentNumber()!=null) {
						carditPawbDetail.setMasterDocumentNumber(carditPawbDetail.getExistingMailBagsInConsignment().getConsignmentMasterDocumentNumber());
					}
				}
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			int airlineIdentifier = getAirlineIdentifier(carditVO,logonAttributes);
			if(airlineIdentifier!=logonAttributes.getOwnAirlineIdentifier()) {
				 isStockValidationRequired  =false;
			
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
			stockAgentVOs = proxyInstance.get(StockcontrolDefaultsProxy.class)
					.findStockAgentMappings(stockAgentFilterVO);
			StockAgentVO stockAgentVO = new StockAgentVO();
			if(Objects.nonNull(stockAgentVOs) && !stockAgentVOs.isEmpty()) {
			stockAgentVO =stockAgentVOs.stream().findFirst().get();
			}
			
			documentFilterVO.setStockOwner(stockAgentVO.getStockHolderCode()!=null?stockAgentVO.getStockHolderCode():
				carditPawbDetail.getAgentCode());
			if(carditPawbDetail.getConsignmentOriginAirport()!=null) {
			documentFilterVO.setAwbOrigin(carditPawbDetail.getConsignmentOriginAirport());
			}
			if(carditPawbDetail.getConsignmentDestinationAirport()!=null) {
			documentFilterVO.setAwbDestination(carditPawbDetail.getConsignmentDestinationAirport());
			}
			String subType = null;
			subType = proxyInstance.get(StockcontrolDefaultsProxy.class).findAutoPopulateSubtype(documentFilterVO);
			
			if (subType != null && subType.trim().length() > 0) {
				documentFilterVO.setDocumentSubType(subType);
			} else {
				String product = ParameterUtil.getInstance().getSystemParameterValue(MAIL_AWB_PRODUCT);
				Collection<ProductValidationVO> productVOs = null;
				ProductVO productVO;
				ProductValidationVO productValidationVO = null;
				
				String companyCode = carditVO.getCompanyCode();
				productVOs = proxyInstance
						.get(ProductDefaultsProxy.class).findProductsByName(companyCode, product);

				if (productVOs != null) {

					productValidationVO = productVOs.iterator().next();

					productVO = proxyInstance
							.get(ProductDefaultsProxy.class).findProductDetails(companyCode,
							productValidationVO.getProductCode());
					
				documentFilterVO.setDocumentSubType(productVO.getDocumentSubType());
				subType = productVO.getDocumentSubType();
			}
				}
			carditPawbDetail.setSubType(subType);
			carditVO.setCarditPawbDetailsVO(carditPawbDetail);
			
		String isStockCheckEnabled = ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK);
		if (isStockCheckEnabled.contentEquals("Y")) {
			AWBDocumentValidationVO documentValidationVO = null;
			
				
				if(carditPawbDetail.getMasterDocumentNumber()!=null && !carditPawbDetail.getMasterDocumentNumber().isEmpty()) {
					
					if(MailConstantsVO.CDT_TYP_CANCEL.equalsIgnoreCase(carditVO.getCarditType())||
							(!MailConstantsVO.CDT_TYP_CANCEL.equalsIgnoreCase(carditVO.getCarditType()) &&
							carditPawbDetail.getExistingMailBagsInConsignment().getConsignmentMasterDocumentNumber()!=null &&
					carditPawbDetail.getExistingMailBagsInConsignment().getConsignmentMasterDocumentNumber().equals(carditPawbDetail.getMasterDocumentNumber()))) {
						isStockValidationRequired = false;
					}
					if(isStockValidationRequired) {
				documentValidationVO = (AWBDocumentValidationVO) proxyInstance
						.get(StockcontrolDefaultsProxy.class).validateDocumentInStock(documentFilterVO);
					}else {
						documentValidationVO = new 	AWBDocumentValidationVO();
					}
				}else {
					
					
					try {
						
					 documentValidationVO =  (AWBDocumentValidationVO) proxyInstance.get(StockcontrolDefaultsProxy.class)
							.findNextDocumentNumber(documentFilterVO);
					 if(documentValidationVO!=null) {
					 carditPawbDetail.setMasterDocumentNumber(documentValidationVO.getDocumentNumber());
					 carditVO.setCarditPawbDetailsVO(carditPawbDetail);
					 }
					 
					}catch(StockcontrolDefaultsProxyException e) {
						documentValidationVO = null;
						LOGGER.log(LOGGER.FINE, e);

					}
					catch(SystemException e) {
						documentValidationVO = null;
						LOGGER.log(LOGGER.FINE, e);
					}
				}
				if(documentValidationVO==null) {
					throw new MailTrackingBusinessException(MailTrackingBusinessException.INVALID_PAWB_STOCK);
				}
			}
		}

	}
	private int getAirlineIdentifier(CarditVO carditVO,LogonAttributes logonAttributes) throws ProxyException, SystemException {
		AirlineValidationVO airlineValidationVO = null;
		CarditPawbDetailsVO carditPawbDetail = carditVO.getCarditPawbDetailsVO();
		if(carditPawbDetail.getShipmentPrefix()!=null) {
		 airlineValidationVO = proxyInstance.get(OperationsShipmentProxy.class)
				.validateNumericCode(carditVO.getCompanyCode(), carditPawbDetail.getShipmentPrefix());
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
