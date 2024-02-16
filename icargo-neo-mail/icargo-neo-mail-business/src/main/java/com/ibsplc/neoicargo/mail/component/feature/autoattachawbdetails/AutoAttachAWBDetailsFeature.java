package com.ibsplc.neoicargo.mail.component.feature.autoattachawbdetails;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.mail.component.events.MailEventsProducer;
import com.ibsplc.neoicargo.mail.component.feature.autoattachawbdetails.persistors.AutoAttachAWBDetailsPersistor;
import com.ibsplc.neoicargo.mail.events.MailUpliftEvent;
import com.ibsplc.neoicargo.mail.mapper.MailEventsMapper;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailManifestDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;


@Slf4j
@Component(AutoAttachAWBDetailsFeatureConstants.AUTO_ATTACH_AWB_FEATURE)
@FeatureConfigSource("mail/autoattachawbdetails")
public class AutoAttachAWBDetailsFeature extends AbstractFeature<MailManifestDetailsVO> {
	@Autowired
	private MailEventsProducer mailEventsProducer;
	@Autowired
	private MailEventsMapper mailEventsMapper;

	@Override
	protected MailManifestDetailsVO perform(MailManifestDetailsVO mailManifestDetailsVO) {
		Collection<ContainerDetailsVO> containerDetailsVOs = mailManifestDetailsVO.getContainerDetailsVOs();
		String flightNumber = containerDetailsVOs.iterator().next().getFlightNumber();
		ZonedDateTime flightDate = containerDetailsVOs.iterator().next().getFlightDate();
		int carrierId = containerDetailsVOs.iterator().next().getCarrierId();
		int legSerialNumber = containerDetailsVOs.iterator().next().getLegSerialNumber();
		long flightSequenceNumber = containerDetailsVOs.iterator().next().getFlightSequenceNumber();
		
		mailManifestDetailsVO.setFlightNumber(flightNumber);
		mailManifestDetailsVO.setFlightDate(flightDate);
		mailManifestDetailsVO.setCarrierId(carrierId);
		mailManifestDetailsVO.setLegSerialNumber(legSerialNumber);
		mailManifestDetailsVO.setFlightSequenceNumber(flightSequenceNumber);
		try {
			new AutoAttachAWBDetailsPersistor().persist(mailManifestDetailsVO);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		updateContainerInfoForPublish(mailManifestDetailsVO,containerDetailsVOs);
		log.debug("---mailManifestDetailsVO--- {}", mailManifestDetailsVO);
		FlightValidationVO flightValidationVO =mailManifestDetailsVO.getFlightValidationVO();
		publishMailManifest(mailManifestDetailsVO);
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
 private void publishMailManifest(MailManifestDetailsVO mailManifestDetailsVO){
	 MailUpliftEvent mailUpliftEvent = mailEventsMapper.constructMailUpliftEvent(mailManifestDetailsVO);
	 String key = String.format("%s-%s",mailManifestDetailsVO.getFlightNumber(),String.valueOf(mailManifestDetailsVO.getFlightSequenceNumber()),
			 String.valueOf(mailManifestDetailsVO.getCarrierId()));
	 log.debug("---mailUpliftEvent--- {}", mailUpliftEvent);
	 mailEventsProducer.publishEvent(key,mailUpliftEvent);
 }

}
