package com.ibsplc.icargo.business.xaddons.ke.mail.operations.event.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO;
import com.ibsplc.icargo.business.operations.shipment.cto.delivery.vo.ShipmentDeliveryVO;
import com.ibsplc.icargo.business.operations.shipment.cto.delivery.vo.ULDDeliveryVO;
import com.ibsplc.icargo.business.operations.shipment.vo.SaveCollectionListDeliveryFeatureVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentSummaryVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.xaddons.ke.mail.operations.proxy.OperationsShipmentProxy;
import com.ibsplc.icargo.business.xaddons.ke.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.xaddons.ke.mail.operations.vo.MailSummaryDeatilsVO;
import com.ibsplc.icargo.framework.bean.BeanConversion;
import com.ibsplc.icargo.framework.bean.BeanConversionConfigVO;
import com.ibsplc.icargo.framework.bean.BeanConversionHelper;
import com.ibsplc.icargo.framework.bean.BeanConverterRegistry;
import com.ibsplc.icargo.framework.context.ContextAware;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@BeanConverterRegistry("ke.mail.operations.MailAWBTransactionsMapper")
public class MailAWBTransactionsMapper  implements ContextAware {
	
	private Log log = LogFactory.getLogger(this.getClass().getCanonicalName());
	@BeanConversion(from = SaveCollectionListDeliveryFeatureVO.class, to = MailSummaryDeatilsVO.class, group = "OPERATIONS_SHIPMENT_SAVECOLLECTIONLISTDELIVERY_EVENT")
	public  MailSummaryDeatilsVO  performMailAWBTransactions(SaveCollectionListDeliveryFeatureVO savecollectionListdeliveryVO) throws SystemException, ProxyException {
		log.entering(MailAWBTransactionsMapper.class.getCanonicalName(),  "performMailAWBTransactions");
		MailFlightSummaryVO mailFlightSummaryVO= null;
		MailSummaryDeatilsVO mailSummaryVO = new MailSummaryDeatilsVO();
		Collection<ULDDeliveryVO> uldDeliverVOs =savecollectionListdeliveryVO.getUldDeliveryDetails();
		if (Objects.nonNull(uldDeliverVOs) && !uldDeliverVOs.isEmpty()) {
			for (ULDDeliveryVO deliveryVO : uldDeliverVOs) {
				if(Objects.nonNull(deliveryVO.getShipmentDeliveryDetails())&& !deliveryVO.getShipmentDeliveryDetails().isEmpty()){
					for(ShipmentDeliveryVO shipmentDeliveryVO : deliveryVO.getShipmentDeliveryDetails()){
						mailFlightSummaryVO = populateMailFlightSummaryVO(shipmentDeliveryVO,deliveryVO);
					}
			}
			}
		}
		mailSummaryVO.setMailFlightSummaryVO(mailFlightSummaryVO);
		mailSummaryVO.setEventCode("DLV");
		log.exiting(MailAWBTransactionsMapper.class.getCanonicalName(), "performMailAWBTransactions");
		return mailSummaryVO;
	}
	private Collection<OneTimeVO> populateOneTime() throws SystemException{
		Collection<String> parameterTypes = new ArrayList<>();
		parameterTypes.add("mailtracking.defaults.mailscccode");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Map<String, Collection<OneTimeVO>> oneTimeMap = null;
		Collection<OneTimeVO> oneTimeValues = null;
		oneTimeMap = Proxy.getInstance().get(SharedDefaultsProxy.class).findOneTimeValues(logonAttributes.getCompanyCode(), parameterTypes);
		if (oneTimeMap != null) {
			oneTimeValues = oneTimeMap.get("mailtracking.defaults.mailscccode");
		}
		return oneTimeValues;
	}
	private MailFlightSummaryVO populateMailFlightSummaryVO(ShipmentDeliveryVO shipmentDeliveryVO,ULDDeliveryVO deliveryVO) throws SystemException, ProxyException{
		MailFlightSummaryVO mailFlightSummaryVO = convertToMailFlightSummaryVO(deliveryVO,shipmentDeliveryVO);
		Collection<OneTimeVO> oneTimeValues = populateOneTime();
		boolean isShipmentSummaryVOAdded = false;
		Collection<ShipmentSummaryVO> shipmentSummaryVOsToMail = new ArrayList<>();
		for (ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()) {
			String[] sccs = null;
			if (Objects.nonNull(shipmentSummaryVO.getScc()) && shipmentSummaryVO.getScc().trim().length() > 0) {
				sccs = shipmentSummaryVO.getScc().split(",");
			}
			isShipmentSummaryVOAdded = shipmentSummaryVOsToMail(shipmentSummaryVOsToMail,sccs,oneTimeValues,shipmentSummaryVO,isShipmentSummaryVOAdded);
			if (isShipmentSummaryVOAdded) {
				break;
			}
		}
		mailFlightSummaryVO.setShipmentSummaryVOs(shipmentSummaryVOsToMail);
		return mailFlightSummaryVO;
	}
	private boolean shipmentSummaryVOsToMail(Collection<ShipmentSummaryVO> shipmentSummaryVOsToMail,String[] sccs,Collection<OneTimeVO> oneTimeValues,ShipmentSummaryVO shipmentSummaryVO,boolean isShipmentSummaryVOAdded){
		if ((Objects.nonNull(sccs) && sccs.length > 0) && Objects.nonNull(oneTimeValues)) {
				for (OneTimeVO oneTimeVO : oneTimeValues) {
						for (String scc : sccs) {
							if (Objects.nonNull(oneTimeVO.getFieldValue()) && oneTimeVO.getFieldValue().equals(scc)) {
								shipmentSummaryVOsToMail.add(shipmentSummaryVO);
								isShipmentSummaryVOAdded = true;
								break;
							}
						}
					if (isShipmentSummaryVOAdded) {
						break;
					}
				}
		}
		return isShipmentSummaryVOAdded;
	}
	private MailFlightSummaryVO convertToMailFlightSummaryVO(ULDDeliveryVO deliveryVO,ShipmentDeliveryVO shipmentDeliveryVO) throws ProxyException, SystemException{
		ShipmentFilterVO shipmentFilterVO = new ShipmentFilterVO();
		shipmentFilterVO.setCompanyCode(deliveryVO.getCompanyCode());
		shipmentFilterVO.setMasterDocumentNumber(shipmentDeliveryVO	.getMasterDocumentNumber());
		shipmentFilterVO.setDocumentNumber(shipmentDeliveryVO.getMasterDocumentNumber());
		shipmentFilterVO.setOwnerId(shipmentDeliveryVO.getOwnerId());
		Collection<ShipmentVO>  shipmentVOs = Proxy.getInstance().get(OperationsShipmentProxy.class).findShipments(shipmentFilterVO);
		BeanConversionConfigVO configVO = new BeanConversionConfigVO();
		configVO.setFrom(ShipmentDeliveryVO.class);
		ShipmentSummaryVO shipmentSummaryVO = BeanConversionHelper.getInstance().convert(shipmentDeliveryVO,configVO,ShipmentSummaryVO.class);
		Collection<ShipmentSummaryVO> shipmentSummaryVOs = new ArrayList<>();
		if(!shipmentVOs.isEmpty())
			shipmentSummaryVO.setScc(((ArrayList<ShipmentVO>) shipmentVOs).get(0).getScc());
		shipmentSummaryVOs.add(shipmentSummaryVO);
		MailFlightSummaryVO mailFlightSummaryVO = new MailFlightSummaryVO();
		mailFlightSummaryVO.setCompanyCode(shipmentDeliveryVO.getCompanyCode());
		mailFlightSummaryVO.setAirportCode(shipmentDeliveryVO.getAirportCode());
		mailFlightSummaryVO.setShipmentSummaryVOs(shipmentSummaryVOs);
		return mailFlightSummaryVO;
	}

}
