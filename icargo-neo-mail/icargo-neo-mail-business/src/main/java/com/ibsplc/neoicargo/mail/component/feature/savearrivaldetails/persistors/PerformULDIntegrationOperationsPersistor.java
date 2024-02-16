package com.ibsplc.neoicargo.mail.component.feature.savearrivaldetails.persistors;

import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDInFlightVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.mail.component.proxy.SharedDefaultsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.ULDDefaultsProxy;
import com.ibsplc.neoicargo.mail.exception.ULDDefaultsProxyException;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.ContainerVO;
import com.ibsplc.neoicargo.mail.vo.MailArrivalVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Slf4j
@Component("performULDIntegrationOperationsPersistor")
public class PerformULDIntegrationOperationsPersistor {
    @Autowired
    private ULDDefaultsProxy uLDDefaultsProxy;
    @Autowired
    private SharedDefaultsProxy sharedDefaultsProxy;

    public void persist(MailArrivalVO mailArrivalVO) throws ULDDefaultsProxyException {
        log.debug(getClass().getSimpleName() + " : " + "persist" + " Entering");
        boolean isUldIntegrationEnbled = isULDIntegrationEnabled();
        if (isUldIntegrationEnbled) {
            performULDIntegrationOperations(mailArrivalVO);
        }
        log.debug(getClass().getSimpleName() + " : " + "persist" + " Exiting");
    }

    private boolean isULDIntegrationEnabled() {
        boolean isULDIntegrationEnabled = false;
        ArrayList<String> systemParameters = new ArrayList<String>();
        systemParameters.add(MailConstantsVO.ULD_INTEGRATION_ENABLED);
        Map<String, String> systemParameterMap = null;
        NeoMastersServiceUtils neoMastersServiceUtils = ContextUtil.getInstance()
                .getBean(NeoMastersServiceUtils.class);
        try {
            systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
        } catch (BusinessException e) {
            log.error(e.getMessage());
        }
        log.debug("" + " systemParameterMap " + " " + systemParameterMap);
        if (systemParameterMap != null
                && ContainerVO.FLAG_YES.equals(systemParameterMap.get(MailConstantsVO.ULD_INTEGRATION_ENABLED))) {
            isULDIntegrationEnabled = true;
        }
        log.debug("" + " isULDIntegrationEnabled :" + " " + isULDIntegrationEnabled);
        return isULDIntegrationEnabled;
    }

    private void performULDIntegrationOperations(MailArrivalVO mailArrivalVO) throws ULDDefaultsProxyException {
        ULDInFlightVO uldInFlightVO = null;
        Collection<ULDInFlightVO> uldInFlightVOs = null;
        FlightDetailsVO flightDetailsVO = null;
        boolean offlineJobWithAlreadyArrivedMailBag = false;
        uldInFlightVOs = new ArrayList<>();
        flightDetailsVO = new FlightDetailsVO();
        flightDetailsVO.setCompanyCode(mailArrivalVO.getCompanyCode());
        flightDetailsVO.setFlightCarrierIdentifier(mailArrivalVO.getCarrierId());
        flightDetailsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
        flightDetailsVO.setFlightNumber(mailArrivalVO.getFlightNumber());
        if (mailArrivalVO.getArrivalDate() != null) {
            flightDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(mailArrivalVO.getArrivalDate()));
        } else {
            flightDetailsVO.setFlightDate(LocalDateMapper.toLocalDate(mailArrivalVO.getScanDate()));
        }
        flightDetailsVO.setFlightSequenceNumber(mailArrivalVO.getFlightSequenceNumber());
        flightDetailsVO.setDirection(MailConstantsVO.IMPORT);
        Collection<ContainerDetailsVO> containerDetailsVOs = mailArrivalVO.getContainerDetails();
        if (containerDetailsVOs != null && !containerDetailsVOs.isEmpty()) {
            for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
                if (containerDetailsVO.getOperationFlag() != null
                        && !(MailConstantsVO.BULK_TYPE.equals(containerDetailsVO.getContainerType()))) {
                    if (MailConstantsVO.FLAG_YES.equals(containerDetailsVO.getArrivedStatus())
                            && mailArrivalVO.isOfflineJob()) {
                        offlineJobWithAlreadyArrivedMailBag = true;
                        break;
                    }
                    uldInFlightVO = new ULDInFlightVO();
                    uldInFlightVO.setUldNumber(containerDetailsVO.getContainerNumber());
                    uldInFlightVO.setPointOfLading(containerDetailsVO.getPol());
                    uldInFlightVO.setPointOfUnLading(containerDetailsVO.getPou());
                    uldInFlightVO.setRemark(MailConstantsVO.MAIL_ULD_ARRIVED);
                    uldInFlightVO.setContent(MailConstantsVO.UCM_ULD_SOURCE_MAIL);
                    uldInFlightVOs.add(uldInFlightVO);
                }
            }
            flightDetailsVO.setAction(FlightDetailsVO.ARRIVAL);
            flightDetailsVO.setRemark(MailConstantsVO.MAIL_ULD_ARRIVED);
            flightDetailsVO.setSubSystem(MailConstantsVO.MAIL_CONST);
            flightDetailsVO.setUldInFlightVOs(uldInFlightVOs);
            if (!offlineJobWithAlreadyArrivedMailBag) {
                ULDDefaultsProxy uLDDefaultsProxy = ContextUtil.getInstance().getBean(ULDDefaultsProxy.class);
                uLDDefaultsProxy.updateULDForOperations(flightDetailsVO);
            }
        }
    }
}
