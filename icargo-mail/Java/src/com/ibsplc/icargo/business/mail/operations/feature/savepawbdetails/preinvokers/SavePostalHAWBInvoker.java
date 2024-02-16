package com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.preinvokers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.OfficeOfExchange;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.SavePAWBDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.proxy.OperationsShipmentProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.operations.shipment.vo.HAWBSummaryVO;
import com.ibsplc.icargo.business.operations.shipment.vo.HAWBVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Invoker;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent(SavePAWBDetailsFeatureConstants.SAVE_POSTAL_HAWB_INVOKER)
public class SavePostalHAWBInvoker extends Invoker<CarditVO> {

	private static final Log LOGGER = LogFactory.getLogger(SavePAWBDetailsFeatureConstants.MODULE_SUBMODULE);
	private static final String MAIL_CLASS ="mailtracking.defaults.mailclass";
	private static final String MAIL_CONTROLLER_BEAN = "mAilcontroller";    
	private static final String SOURCE_INDICATOR = "ACP";
	@Autowired
	private Proxy proxyInstance;
	@Override
	public void invoke(CarditVO carditVO) throws BusinessException, SystemException {
		if (carditVO.getCarditPawbDetailsVO().getMailInConsignmentVOs() != null
				&& !carditVO.getCarditPawbDetailsVO().getMailInConsignmentVOs().isEmpty()
				&& carditVO.getCarditPawbDetailsVO().getShipmentValidationVO() != null) {
			
			Collection<MailInConsignmentVO> mailInConsignmentVOs = carditVO.getCarditPawbDetailsVO()
					.getMailInConsignmentVOs();
			ShipmentValidationVO shipmentValidationVO = carditVO.getCarditPawbDetailsVO().getShipmentValidationVO();
			ShipmentDetailVO shipmentDetailVO = carditVO.getCarditPawbDetailsVO().getShipmentDetailVO();
			Collection<HAWBVO> hawbs = null;
			Collection<HAWBVO> existingHawbs = findAndConvertToHouseAwbVo(shipmentDetailVO);
			if (existingHawbs.isEmpty()) {
				hawbs = constructHAWBVOs(mailInConsignmentVOs, shipmentValidationVO,
						carditVO.getCarditPawbDetailsVO().getShipmentDetailVO(), AbstractVO.OPERATION_FLAG_INSERT);
			} else {
				// Find already saved HAWBS that in
				// MailInConsignmentVOS
				List<MailInConsignmentVO> mailInConsignmentVOInHawbs = mailInConsignmentVOs.stream().filter(
						mail -> existingHawbs.stream().anyMatch(hawb -> hawb.getHawbNumber().equals(mail.getMailId())))
						.collect(Collectors.toList());
				List<HAWBVO> hawbTobeUpdated = existingHawbs.stream().filter(
						hawb -> mailInConsignmentVOs.stream().anyMatch(mail -> mail.getMailId().equals(hawb.getHawbNumber())))
						.collect(Collectors.toList());
				hawbs = constructHouseAwbToBeUpdated(mailInConsignmentVOInHawbs,hawbTobeUpdated, shipmentValidationVO,
					 AbstractVO.OPERATION_FLAG_UPDATE);
				// Find already saved HAWBS that not in MailInConsignmentVOS
				if(carditVO.getCarditType()!= null  && !carditVO.getCarditType().equalsIgnoreCase(SOURCE_INDICATOR)){
				List<HAWBVO> result = existingHawbs.stream()
						.filter(hawb -> mailInConsignmentVOs.stream()
								.noneMatch(mal -> mal.getMailId().equals(hawb.getHawbNumber())))
						.collect(Collectors.toList());
				if (!result.isEmpty()) {
					result.stream().forEach(hawb ->hawb.setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE)
					);
					hawbs.addAll(result);
				}
				}
				// Find new hawbs in the MailInConsignmentVOS
				List<MailInConsignmentVO> mailInConsignmentVONotInHawbs = mailInConsignmentVOs.stream().filter(
						mail -> existingHawbs.stream().noneMatch(hawb -> hawb.getHawbNumber().equals(mail.getMailId())))
						.collect(Collectors.toList());
				hawbs.addAll(constructHAWBVOs(mailInConsignmentVONotInHawbs, shipmentValidationVO,
						carditVO.getCarditPawbDetailsVO().getShipmentDetailVO(), AbstractVO.OPERATION_FLAG_INSERT));
			}
			Collection<ShipmentValidationVO> shipmentValidationVOs = proxyInstance.get(OperationsShipmentProxy.class)
					.saveHAWBDetails(shipmentValidationVO, hawbs, true);
			addToContext("ShipmentValidationVOs", shipmentValidationVOs);
			
			//find hawbs for delete 
			Collection<HAWBVO> hawbsTobedeleted = new ArrayList<>();
			Collection<HAWBVO> eachHawbs = new ArrayList<>();

			if (carditVO.getCarditPawbDetailsVO().getShipmentDetailVOs()!=null && !carditVO.getCarditPawbDetailsVO().getShipmentDetailVOs().isEmpty()) {
				Collection<HAWBVO> existingHawbsForDel = new ArrayList<>();
				carditVO.getCarditPawbDetailsVO().getShipmentDetailVOs().forEach(shipmDet -> 
					existingHawbsForDel.addAll(findAndConvertToHouseAwbVo(shipmDet))
				);
				
				List<HAWBVO> result = existingHawbsForDel.stream()
						.filter(hawb -> mailInConsignmentVOs.stream()
								.anyMatch(mal -> mal.getMailId().equals(hawb.getHawbNumber())))
						.collect(Collectors.toList());
				if (!result.isEmpty()) {
					result.stream().forEach(hawb -> 
						hawb.setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE)			
					);
					hawbsTobedeleted.addAll(result);
				}
				
				for(ShipmentValidationVO validation:carditVO.getCarditPawbDetailsVO().getUpdatedshipmentValidationVOs()) {
					hawbsTobedeleted.forEach(hawb->{
						if(validation.getMasterDocumentNumber().contentEquals(hawb.getDocumentNumber())) {
							eachHawbs.add(hawb)	;
						}
					});
							proxyInstance.get(OperationsShipmentProxy.class)
								.saveHAWBDetails(validation, eachHawbs, true);	
				}
			}

		
		}

	}


	private Collection<HAWBVO> constructHAWBVOs(Collection<MailInConsignmentVO> mailInConsignmentVOs,
			ShipmentValidationVO shipmentValidationVO, ShipmentDetailVO shipmentDetailVO, String operationFlag) throws SystemException {
		Collection<HAWBVO> hawbs = new ArrayList<>();
		HashMap <String,String> mailClassValues = oneTimeValues(shipmentDetailVO.getCompanyCode());
		for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
			HAWBVO hawbVO = new HAWBVO();
			hawbVO.setCompanyCode(shipmentValidationVO.getCompanyCode());
			hawbVO.setDocumentNumber(shipmentValidationVO.getMasterDocumentNumber());
			hawbVO.setDuplicateNumber(shipmentValidationVO.getDuplicateNumber());
			hawbVO.setHawbNumber(mailInConsignmentVO.getMailId());
			hawbVO.setOwnerId(shipmentValidationVO.getOwnerId());
			hawbVO.setOwnerCode(shipmentValidationVO.getOwnerCode());
			hawbVO.setSequenceNumber(shipmentValidationVO.getSequenceNumber());
			hawbVO.setOperationFlag(operationFlag);
			String mailClassValue = mailClassValues.get(mailInConsignmentVO.getMailClass()).toString();
			hawbVO.setShipmentDescription(SavePAWBDetailsFeatureConstants.POSTAL_MAIL+" "+mailClassValue);
			MailController mailController = (MailController) SpringAdapter.getInstance().getBean(MAIL_CONTROLLER_BEAN); 
			try {
				OfficeOfExchange origin = OfficeOfExchange.find(shipmentValidationVO.getCompanyCode(),
						mailInConsignmentVO.getOriginExchangeOffice());
				OfficeOfExchange destination = OfficeOfExchange.find(shipmentValidationVO.getCompanyCode(),
						mailInConsignmentVO.getDestinationExchangeOffice());
				if(origin.getAirportCode()!=null) {                                                                                                                                                                                  
				hawbVO.setOrigin(origin.getAirportCode());
				 }else {                                                                                                                                                                                                                   
					 hawbVO.setOrigin(mailController.findNearestAirportOfCity(shipmentValidationVO.getCompanyCode(), mailInConsignmentVO.getOriginExchangeOffice()));                                                              
				}  
				if(destination.getAirportCode()!=null) {                                                                                                                                                                                  
				hawbVO.setDestination(destination.getAirportCode());
				}else {                                                                                                                                                                                                                   
					 hawbVO.setDestination(mailController.findNearestAirportOfCity(shipmentValidationVO.getCompanyCode(), mailInConsignmentVO.getDestinationExchangeOffice()));                                                              
				}   
			} catch (FinderException ex) {
				LOGGER.log(Log.INFO, ex);
			}
			hawbVO.setStatedPieces(1);
			hawbVO.setStatedWeight(mailInConsignmentVO.getStatedWeight());
			setShipperDetails(hawbVO, shipmentDetailVO);
			setConsigneeDetails(hawbVO, shipmentDetailVO);
			hawbs.add(hawbVO);
		}
		return hawbs;
	}

	private void setShipperDetails(HAWBVO hawbVO, ShipmentDetailVO shipmentDetailVO) {
		hawbVO.setShipperCode(shipmentDetailVO.getShipperCode());
		hawbVO.setShipperName(shipmentDetailVO.getShipperName());
		hawbVO.setShipperAddress(shipmentDetailVO.getShipperAddress1());
		hawbVO.setShipperCity(shipmentDetailVO.getShipperCity());
		hawbVO.setShipperCountry(shipmentDetailVO.getShipperCountry());
		hawbVO.setShipperAccountNumber(shipmentDetailVO.getShipperAccountNumber());
		hawbVO.setShipperTelephoneNumber(shipmentDetailVO.getShipperTelephoneNumber());
		hawbVO.setShipperEmailId(shipmentDetailVO.getShipperEmailId());
		hawbVO.setShipperState(shipmentDetailVO.getShipperState());
		hawbVO.setShipperPostalCode(shipmentDetailVO.getShipperPostalCode());
	}

	private void setConsigneeDetails(HAWBVO hawbVO, ShipmentDetailVO shipmentDetailVO) {

		hawbVO.setConsigneeCode(shipmentDetailVO.getConsigneeCode());
		hawbVO.setConsigneeName(shipmentDetailVO.getConsigneeName());
		hawbVO.setConsigneeAddress(shipmentDetailVO.getConsigneeAddress1());
		hawbVO.setConsigneeCity(shipmentDetailVO.getConsigneeCity());
		hawbVO.setConsigneeCountry(shipmentDetailVO.getConsigneeCountry());
		hawbVO.setConsigneeAccountNumber(shipmentDetailVO.getConsigneeAccountNumber());
		hawbVO.setConsigneeTelephoneNumber(shipmentDetailVO.getConsigneeTelephoneNumber());
		hawbVO.setConsigneeEmailId(shipmentDetailVO.getConsigneeEmailId());
		hawbVO.setConsigneeState(shipmentDetailVO.getConsigneeState());
		hawbVO.setConsigneePostalCode(shipmentDetailVO.getConsigneePostalCode());
	}
	private Collection<HAWBVO> findAndConvertToHouseAwbVo(ShipmentDetailVO shipmentDetailVO) {
		Collection<HAWBVO> hawbvos = new ArrayList<>();
		if (shipmentDetailVO!=null && shipmentDetailVO.getHouses()!=null&&!shipmentDetailVO.getHouses().isEmpty()) {
			for (HAWBSummaryVO hawbSummaryVO : shipmentDetailVO.getHouses()) {
				HAWBVO hawbVO = new HAWBVO();
				hawbVO.setCompanyCode(hawbSummaryVO.getCompanyCode());
				hawbVO.setDocumentNumber(hawbSummaryVO.getMasterDocumentNumber());
				hawbVO.setDuplicateNumber(hawbSummaryVO.getDuplicateNumber());
				hawbVO.setHawbNumber(hawbSummaryVO.getDocumentNumber());
				hawbVO.setOwnerId(hawbSummaryVO.getOwnerId());
				hawbVO.setSequenceNumber(hawbSummaryVO.getSequenceNumber());
				hawbVO.setStatedPieces(hawbSummaryVO.getStatedPieces());
				hawbVO.setStatedWeight(hawbSummaryVO.getStatedWeight());
				setShipperDetails(hawbVO,shipmentDetailVO);
				setConsigneeDetails(hawbVO,shipmentDetailVO);
				hawbvos.add(hawbVO);
			}
		}
		return hawbvos;
	}
	private Collection<HAWBVO> constructHouseAwbToBeUpdated(Collection<MailInConsignmentVO> mailInConsignmentVOs,
			Collection<HAWBVO> hawbTobeUpdated,ShipmentValidationVO shipmentValidationVO, String operationFlag) throws SystemException {
		Collection<HAWBVO> hawbvos = new ArrayList<>();
		if (hawbTobeUpdated!=null &&!hawbTobeUpdated.isEmpty()) {
			HashMap <String,String>mailClassValues = oneTimeValues(shipmentValidationVO.getCompanyCode());
			for (HAWBVO hawbVO : hawbTobeUpdated) {
				MailInConsignmentVO mailInConsignmentVO = mailInConsignmentVOs.stream()                      
		                .filter(mail -> mail.getMailId().equals(hawbVO.getHawbNumber()))       
		                .findAny()                                    
		                .orElse(null);
				if(mailInConsignmentVO!=null) {
				try {
					OfficeOfExchange origin = OfficeOfExchange.find(shipmentValidationVO.getCompanyCode(),
							mailInConsignmentVO.getOriginExchangeOffice());
					OfficeOfExchange destination = OfficeOfExchange.find(shipmentValidationVO.getCompanyCode(),
							mailInConsignmentVO.getDestinationExchangeOffice());
					hawbVO.setOrigin(origin.getAirportCode());
					hawbVO.setDestination(destination.getAirportCode());
					String mailClassValue = mailClassValues.get(mailInConsignmentVO.getMailClass()).toString();
					hawbVO.setShipmentDescription(SavePAWBDetailsFeatureConstants.POSTAL_MAIL+" "+mailClassValue);
									}
				catch (FinderException ex) {
					LOGGER.log(Log.INFO, ex);
				} 
				}
				hawbVO.setOperationFlag(operationFlag);
				hawbvos.add(hawbVO);
			}
		}
		return hawbvos;
	}
	
	private HashMap<String,String> oneTimeValues(String companyCode) throws SystemException {
		Map<String, Collection<OneTimeVO>> mailclassMap = null;
		HashMap<String,String>mailclassDescription = new HashMap<>();

		Collection<String> oneTimeList = new ArrayList<>();

		oneTimeList.add(MAIL_CLASS);
		try {
			mailclassMap = Proxy.getInstance().get(SharedDefaultsProxy.class).findOneTimeValues(companyCode,
					oneTimeList);
			 Collection<OneTimeVO> mailClassDetails;

			 mailClassDetails = mailclassMap.get(MAIL_CLASS);
	
			 mailClassDetails.forEach(mailClassDetail -> 
				 mailclassDescription.put(mailClassDetail.getFieldValue(), mailClassDetail.getFieldDescription())
	              );
		} catch (ProxyException proxyException) {
			LOGGER.log(Log.INFO, proxyException);
			throw new SystemException(proxyException.getMessage());
		}
	return mailclassDescription;
	}

}
