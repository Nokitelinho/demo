package com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails.persistors.AutoAttachAWBDetailsPersistor;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.event.annotations.ExtEventPublish;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


@FeatureComponent(AutoAttachAWBDetailsFeatureConstants.AUTO_ATTACH_AWB_FEATURE)
@Feature(exception = BusinessException.class, event = "MAIL_MANIFEST")
@ExtEventPublish
public class AutoAttachAWBDetailsFeature extends AbstractFeature<MailManifestDetailsVO> {

	private Log logger = LogFactory.getLogger(AutoAttachAWBDetailsFeatureConstants.MODULE_SUBMODULE);

	@Override
	protected FeatureConfigVO fetchFeatureConfig(MailManifestDetailsVO mailManifestDetailsVO) {
		
		
					return getBECConfigurationDetails();
				
	}

	private FeatureConfigVO getBECConfigurationDetails() {
		FeatureConfigVO featureConfigVO = new FeatureConfigVO();
		List<String> enricherIds = new ArrayList<>();
		List<String> validatorIds = new ArrayList<>();
		validatorIds.add(AutoAttachAWBDetailsFeatureConstants.AGENT_CODE_VALIDATOR);
		validatorIds.add(AutoAttachAWBDetailsFeatureConstants.PA_CODE_VALIDATOR);
		enricherIds.add(AutoAttachAWBDetailsFeatureConstants.PRODUCT_ENRICHER);
		enricherIds.add(AutoAttachAWBDetailsFeatureConstants.DOCUMENT_FILETR_ENRICHER);
		enricherIds.add(AutoAttachAWBDetailsFeatureConstants.SHIPMENT_DETAIL_ENRICHER);
		featureConfigVO.setValidatorIds(validatorIds);
		featureConfigVO.setEnricherId(enricherIds);
		return featureConfigVO;
	}

	@Override
	protected MailManifestDetailsVO perform(MailManifestDetailsVO mailManifestDetailsVO)
			throws SystemException, BusinessException {
		Collection<ContainerDetailsVO> containerDetailsVOs = mailManifestDetailsVO.getContainerDetailsVOs();
		String flightNumber = containerDetailsVOs.iterator().next().getFlightNumber();
		LocalDate flightDate = containerDetailsVOs.iterator().next().getFlightDate();
		int carrierId = containerDetailsVOs.iterator().next().getCarrierId();
		int legSerialNumber = containerDetailsVOs.iterator().next().getLegSerialNumber();
		long flightSequenceNumber = containerDetailsVOs.iterator().next().getFlightSequenceNumber();
		
		mailManifestDetailsVO.setFlightNumber(flightNumber);
		mailManifestDetailsVO.setFlightDate(flightDate);
		mailManifestDetailsVO.setCarrierId(carrierId);
		mailManifestDetailsVO.setLegSerialNumber(legSerialNumber);
		mailManifestDetailsVO.setFlightSequenceNumber(flightSequenceNumber);
		new AutoAttachAWBDetailsPersistor().persist(mailManifestDetailsVO);		
		updateContainerInfoForPublish(mailManifestDetailsVO,containerDetailsVOs);
		logger.log(Log.FINE, "---mailManifestDetailsVO---", mailManifestDetailsVO); 
		FlightValidationVO flightValidationVO =mailManifestDetailsVO.getFlightValidationVO();
		if(flightValidationVO!=null && flightValidationVO.getAtd()!=null){
		return mailManifestDetailsVO;
		}else{
			return new MailManifestDetailsVO();
		}
	}
	/**
	 * @author A-4809
	 * @param mailManifestDetailsVO
	 * @param containerDetailsVOs
	 * @return
	 */
	private MailManifestDetailsVO updateContainerInfoForPublish(MailManifestDetailsVO mailManifestDetailsVO,
			Collection<ContainerDetailsVO> containerDetailsVOs){
		if(mailManifestDetailsVO.isFromAttachAWB()){
			Collection<MailbagVO> mailDetails = new ArrayList<>();
			ContainerDetailsVO containerVO =  containerDetailsVOs.iterator().next();
			String carrierCode = containerVO.getCarrierCode();
			for(ContainerDetailsVO contVO : mailManifestDetailsVO.getFromAttachContainerVOs()){
				if(containerVO.getContainerNumber().equals(contVO.getContainerNumber())){
					for(MailbagVO mailVO : contVO.getMailDetails()){
						for(MailbagVO mailDetailVO : containerVO.getMailDetails()){
						if(mailVO.getMailbagId().equals(mailDetailVO.getMailbagId())){
							mailDetails.add(mailDetailVO);
						}else{
							mailDetails.add(mailVO);
						}
					  }
					}
					contVO.setMailDetails(mailDetails);
				}
			}
		
			mailManifestDetailsVO.setCarrierCode(carrierCode);
			mailManifestDetailsVO.setContainerDetailsVOs(mailManifestDetailsVO.getFromAttachContainerVOs());
		}
		
		return mailManifestDetailsVO;
	}

}
