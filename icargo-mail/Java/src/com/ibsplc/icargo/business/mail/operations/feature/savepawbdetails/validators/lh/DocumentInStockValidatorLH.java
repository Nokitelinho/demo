package com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.validators.lh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.SavePAWBDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.proxy.OperationsShipmentProxy;
import com.ibsplc.icargo.business.mail.operations.vo.CarditPawbDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.proxy.webservices.lh.MailStockRetrievalWSProxy;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.services.jaxws.proxy.exception.WebServiceException;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtil;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.validators.DocumentInStockValidator;

@FeatureComponent(SavePAWBDetailsFeatureConstants.DOCUMENT_IN_STOCK_VALIDATORLH)
public class DocumentInStockValidatorLH extends DocumentInStockValidator{	
	private static final String OPERATION_SHIPMENT_STOCK_CHECK = "operations.shipment.stockcheckrequired";
	private static final String DOCUMENT_TYPE = "AWB";
	public static final String MAIL_AWB_PRODUCT = "mail.operations.productCode";
	public static final String MODULE_SUBMODULE = "MAIL OPERATIONS";
	private static final Log LOGGER = LogFactory.getLogger(MODULE_SUBMODULE);
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
			CarditPawbDetailsVO carditPawbDetail = carditVO.getCarditPawbDetailsVO();
			boolean isStockRetrievalRequired  =true;
				if (MailConstantsVO.CDT_TYP_CANCEL.equalsIgnoreCase(carditVO.getCarditType())) {
					if(carditPawbDetail.getMasterDocumentNumber()==null && carditPawbDetail.getConsignmentDocumentVO().getConsignmentMasterDocumentNumber()!=null) {
						carditPawbDetail.setMasterDocumentNumber(carditPawbDetail.getConsignmentDocumentVO().getConsignmentMasterDocumentNumber());
				}
				}else {
					if(!MailConstantsVO.CARDIT_TYPE_ACP.equalsIgnoreCase(carditVO.getCarditType()) && carditPawbDetail.getMasterDocumentNumber()==null && carditPawbDetail.getExistingMailBagsInConsignment().getConsignmentMasterDocumentNumber()!=null) {
						carditPawbDetail.setMasterDocumentNumber(carditPawbDetail.getExistingMailBagsInConsignment().getConsignmentMasterDocumentNumber());
					}
				}
			
			int airlineIdentifier = getAirlineIdentifier(carditVO);
			carditPawbDetail.setOwnerId(airlineIdentifier);
			
			if(!MailConstantsVO.CARDIT_TYPE_ACP.equalsIgnoreCase(carditVO.getCarditType())){
			setCarditPAWBDetails(carditVO, carditPawbDetail, isStockRetrievalRequired);
		}
		}
	}
	private void setCarditPAWBDetails(CarditVO carditVO, CarditPawbDetailsVO carditPawbDetail,
			boolean isStockRetrievalRequired) throws SystemException, MailTrackingBusinessException {
			DocumentFilterVO documentFilterVO = new DocumentFilterVO();
			documentFilterVO.setCompanyCode(carditVO.getCompanyCode());
			documentFilterVO.setShipmentPrefix(carditPawbDetail.getShipmentPrefix());
			documentFilterVO.setDocumentNumber(carditPawbDetail.getMasterDocumentNumber());
			documentFilterVO.setDocumentType(DOCUMENT_TYPE);
			documentFilterVO.setStockOwner(carditPawbDetail.getAgentCode());

			
			
			if(carditPawbDetail.getConsignmentOriginAirport()!=null) {
			documentFilterVO.setAwbOrigin(carditPawbDetail.getConsignmentOriginAirport());
			}
			if(carditPawbDetail.getConsignmentDestinationAirport()!=null) {
			documentFilterVO.setAwbDestination(carditPawbDetail.getConsignmentDestinationAirport());
			}
		  String isStockCheckEnabled = ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK);
		    if(carditPawbDetail.getExistingMailBagsInConsignment()!=null && carditPawbDetail.getExistingMailBagsInConsignment().getConsignmentMasterDocumentNumber()!=null)	{
				isStockCheckEnabled = ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK);
			}
		    if(carditPawbDetail.getExistingMailBagsInConsignment()!=null) {
				isStockCheckEnabled = ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK);
			}
			
			
		isStockRetrievalRequired = setShipmentPrefixAndMstDocNumForCarditPAWBDetails(carditVO, carditPawbDetail,
				isStockRetrievalRequired);
		
		if (isStockCheckEnabled.contentEquals("N") && isStockRetrievalRequired) {
			
			DocumentFilterVO documentFilterVOResponse=null;
			try {
						 documentFilterVOResponse = proxyInstance
								.get(MailStockRetrievalWSProxy.class).stockRetrievalForPAWB(documentFilterVO);
   }catch(SystemException | WebServiceException ex) { 
	   LOGGER.log(Log.FINE,ex); 
       throw new MailTrackingBusinessException(MailTrackingBusinessException.INVALID_PAWB_STOCK);
   }
						if(documentFilterVOResponse!=null) {
					 carditPawbDetail.setMasterDocumentNumber(documentFilterVOResponse.getDocumentNumber());
					 carditVO.setCarditPawbDetailsVO(carditPawbDetail);
					 }
					if(documentFilterVOResponse==null || documentFilterVOResponse.getDocumentNumber()==null ||documentFilterVOResponse.getDocumentNumber().isEmpty()) {
						throw new MailTrackingBusinessException(MailTrackingBusinessException.INVALID_PAWB_STOCK);
					}
				
			}
		}
	private boolean setShipmentPrefixAndMstDocNumForCarditPAWBDetails(CarditVO carditVO,
			CarditPawbDetailsVO carditPawbDetail, boolean isStockRetrievalRequired) {
		if(!MailConstantsVO.CDT_TYP_CANCEL.equalsIgnoreCase(carditVO.getCarditType()) && carditPawbDetail.getExistingMailBagsInConsignment() != null &&
				carditPawbDetail.getExistingMailBagsInConsignment().getConsignmentMasterDocumentNumber()!=null) {
			carditPawbDetail.setMasterDocumentNumber(carditPawbDetail.getExistingMailBagsInConsignment().getConsignmentMasterDocumentNumber());
			carditPawbDetail.setShipmentPrefix(carditPawbDetail.getShipmentPrefix());
			isStockRetrievalRequired = false;
		}
		return isStockRetrievalRequired;
	}
	private int getAirlineIdentifier(CarditVO carditVO) throws ProxyException, SystemException {
		AirlineValidationVO airlineValidationVO = null;
		CarditPawbDetailsVO carditPawbDetail = carditVO.getCarditPawbDetailsVO();
		int airlineIdentifier = 0;
		/**if(carditPawbDetail.getShipmentPrefix()!=null) {
		 airlineValidationVO = proxyInstance.get(OperationsShipmentProxy.class)
				.validateNumericCode(carditVO.getCompanyCode(), carditPawbDetail.getShipmentPrefix());
		}

		
		if (airlineValidationVO != null) {
			airlineIdentifier = airlineValidationVO.getAirlineIdentifier();
		} else {*/
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			airlineIdentifier = logonAttributes.getOwnAirlineIdentifier();
			carditPawbDetail.setShipmentPrefix(logonAttributes.getOwnAirlineNumericCode());
			carditVO.setCarditPawbDetailsVO(carditPawbDetail);
		return airlineIdentifier;
		
	}
}
