package com.ibsplc.neoicargo.mail.component.feature.savearrivaldetails;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MonitorMailSLAVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.component.Mailbag;
import com.ibsplc.neoicargo.mail.component.MailbagPK;
import com.ibsplc.neoicargo.mail.component.feature.savearrivaldetails.persistors.MailbagInULDForSegmentPersistor;
import com.ibsplc.neoicargo.mail.component.feature.savearrivaldetails.persistors.PerformULDIntegrationOperationsPersistor;
import com.ibsplc.neoicargo.mail.component.feature.savearrivaldetails.persistors.SaveArrivalDetailsPersistor;
import com.ibsplc.neoicargo.mail.component.feature.savearrivaldetails.persistors.UpdateDamageDetailsPersistor;
import com.ibsplc.neoicargo.mail.component.feature.savearrivaldetails.persistors.UpdateDespatchDocumentDetailsForImportPersistor;
import com.ibsplc.neoicargo.mail.component.proxy.MailMRAProxy;
import com.ibsplc.neoicargo.mail.exception.CapacityBookingProxyException;
import com.ibsplc.neoicargo.mail.exception.ContainerAssignmentException;
import com.ibsplc.neoicargo.mail.exception.DuplicateDSNException;
import com.ibsplc.neoicargo.mail.exception.DuplicateMailBagsException;
import com.ibsplc.neoicargo.mail.exception.FlightClosedException;
import com.ibsplc.neoicargo.mail.exception.InvalidFlightSegmentException;
import com.ibsplc.neoicargo.mail.exception.InventoryForArrivalFailedException;
import com.ibsplc.neoicargo.mail.exception.MailBookingException;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.mail.exception.MailbagIncorrectlyDeliveredException;
import com.ibsplc.neoicargo.mail.exception.ULDDefaultsProxyException;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.DamagedMailbagVO;
import com.ibsplc.neoicargo.mail.vo.MailArrivalVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailInConsignmentVO;
import com.ibsplc.neoicargo.mail.vo.MailbagHistoryVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mailmasters.MailTrackingDefaultsBI;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Component(SaveArrivalDetailsFeatureConstants.SAVE_ARRIVAL_FEATURE)
@FeatureConfigSource("feature/savearrivaldetails")
public class SaveArrivalDetailsFeature extends AbstractFeature<MailArrivalVO> {
    @Autowired
    private LocalDate localDateUtil;
    @Autowired
    MailTrackingDefaultsBI mailMasterApi;
    @Autowired
    private MailMRAProxy mailOperationsMRAProxy;
    @Autowired
    private MailController mailController;

    protected Void perform(MailArrivalVO mailArrivalVO)
            throws MailOperationsBusinessException {
        log.debug(getClass().getSimpleName() + " : " + "perform" + " Entering");
        Collection<ContainerDetailsVO> arrivedContainers = mailArrivalVO.getContainerDetails();
        String POST_DATA_CAPTURE = "PDC";
        if (CollectionUtils.isNotEmpty(arrivedContainers)) {
            for (ContainerDetailsVO containerDetailsVO : arrivedContainers) {
                Collection<MailbagVO> mailbags = containerDetailsVO.getMailDetails();
                if (CollectionUtils.isNotEmpty(mailbags)) {
                    for (MailbagVO mailbagVO : mailbags) {
                        if ("U".equals(mailbagVO.getOperationalFlag())) {
                            if (isPostDataCaptureOfMailbag(mailbagVO)) {
                                mailbagVO.setOperationalFlag(null);
                                mailbagVO.setActionMode(POST_DATA_CAPTURE);
                                try {
                                    new UpdateDamageDetailsPersistor().persist(mailbagVO);
                                    new MailbagInULDForSegmentPersistor().persist(mailbagVO);

                                } catch (ULDDefaultsProxyException | DuplicateMailBagsException |
                                         ContainerAssignmentException
                                         | MailbagIncorrectlyDeliveredException | InvalidFlightSegmentException |
                                         FlightClosedException
                                         | InventoryForArrivalFailedException | DuplicateDSNException |
                                         CapacityBookingProxyException
                                         | MailBookingException e) {
                                    throw new MailOperationsBusinessException(e);
                                }
                            }
                        }
                    }
                }
            }
        }
        Collection<MailbagVO> arrivedMailBagsForMonitorSLA = null;
        Collection<MailbagVO> deliveredMailBagsForMonitorSLA = null;
        ZonedDateTime deliveryDate = null;
        //Commented for performanace improvement as this logic has no relevance now..
        //boolean isMonitorSLAEnabled = isMonitorSLAEnabled();
        boolean isMonitorSLAEnabled = false;
        boolean undoArriveNeeded = false;
        if (isMonitorSLAEnabled) {
            arrivedMailBagsForMonitorSLA = new ArrayList<>();
            deliveredMailBagsForMonitorSLA = new ArrayList<>();
        }
        mailArrivalVO.setDeliveryCheckNeeded(true);
        //updatebulkDetails(arrivedContainers);
        //checkIfDSNinBothMailbagAndDespatchMode(arrivedContainers);
        try {
            new PerformULDIntegrationOperationsPersistor().persist(mailArrivalVO);
            new UpdateDespatchDocumentDetailsForImportPersistor().persist(mailArrivalVO);
            updateMailbagDocumentDetailsForImport(mailArrivalVO);
            updateMailbagSource(mailArrivalVO, undoArriveNeeded);
            new SaveArrivalDetailsPersistor().persist(mailArrivalVO, arrivedMailBagsForMonitorSLA, deliveredMailBagsForMonitorSLA);
        } catch (ULDDefaultsProxyException | DuplicateMailBagsException | ContainerAssignmentException
                 | MailbagIncorrectlyDeliveredException | InvalidFlightSegmentException | FlightClosedException
                 | InventoryForArrivalFailedException | DuplicateDSNException | CapacityBookingProxyException
                 | MailBookingException e) {
            throw new MailOperationsBusinessException(e);
        }
        afterSavingPerform(mailArrivalVO, isMonitorSLAEnabled, deliveryDate, undoArriveNeeded,
                arrivedMailBagsForMonitorSLA, deliveredMailBagsForMonitorSLA);
        log.debug(getClass().getSimpleName() + " : " + "perform" + " Exiting");
        return null;
    }

    private boolean isPostDataCaptureOfMailbag(MailbagVO mailbagVO) {
        log.debug(SaveArrivalDetailsFeatureConstants.CLASS + " : " + "findCurrentStatusOfMailbag" + " Entering");
        MailbagPK mailbagPk = new MailbagPK();
        Mailbag mailbag = null;
        boolean postDataCapture = false;
        String mailbagId = mailbagVO.getMailbagId();
        mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
        mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() > 0 ? mailbagVO.getMailSequenceNumber()
                : mailController.findMailSequenceNumber(mailbagId, mailbagVO.getCompanyCode()));
        try {
            mailbag = Mailbag.find(mailbagPk);
        } catch (FinderException e) {
            log.error("Finder Exception Caught");
        }
        if (mailbag != null) {
            if (!mailbag.getScannedPort().equals(mailbagVO.getScannedPort())) {
                if ("O".equals(mailbag.getOperationalStatus())
                        && (mailbag.getFlightNumber() != null
                        && mailbag.getFlightNumber().equals(mailbagVO.getFlightNumber()))
                        && (mailbag.getFlightSequenceNumber() == mailbagVO.getFlightSequenceNumber())) {
                } else {
                    Collection<MailbagHistoryVO> mailhistories = new ArrayList<MailbagHistoryVO>();
                    mailhistories = (mailbagVO.getMailbagHistories() != null
                            && !mailbagVO.getMailbagHistories().isEmpty()) ? mailbagVO.getMailbagHistories()
                            : (ArrayList<MailbagHistoryVO>) Mailbag.findMailbagHistories(
                            mailbagVO.getCompanyCode(), mailbagVO.getMailbagId(), 0L);
                    if (!mailhistories.isEmpty()) {
                        for (MailbagHistoryVO mailbaghistoryvo : mailhistories) {
                            if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbaghistoryvo.getMailStatus())
                                    && mailbaghistoryvo.getScannedPort().equals(mailbagVO.getScannedPort())) {
                                if ((mailbaghistoryvo.getFlightNumber() != null
                                        && mailbaghistoryvo.getFlightNumber().equals(mailbagVO.getFlightNumber()))
                                        || mailbaghistoryvo.getFlightSequenceNumber() == mailbagVO
                                        .getFlightSequenceNumber()) {
                                    postDataCapture = true;
                                }
                            }
                        }
                    }
                }
            } else {
                if (!mailbagVO.getFlightNumber().equals(mailbag.getFlightNumber())
                        || mailbagVO.getFlightSequenceNumber() != mailbag.getFlightSequenceNumber()) {
                    postDataCapture = true;
                }
            }
        }
        return postDataCapture;
    }

    private void updateMailbagSource(MailArrivalVO mailArrivalVO, boolean undoArriveNeeded) {
        log.debug(getClass().getSimpleName() + " : " + "updateMailbagSource" + " Entering");

        Collection<ContainerDetailsVO> containerDetailsVOs = mailArrivalVO.getContainerDetails();
        if (CollectionUtils.isNotEmpty(containerDetailsVOs)) {
            for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
                Collection<MailbagVO> mailBagVOs = containerDetailsVO.getMailDetails();
                if (CollectionUtils.isNotEmpty(mailBagVOs)) {
                    for (MailbagVO mailbagVO : mailBagVOs) {
                        mailbagVO.setMailSource(mailArrivalVO.getMailSource());
                        mailbagVO.setMailbagDataSource(mailArrivalVO.getMailDataSource());
                        mailbagVO.setMessageVersion(mailArrivalVO.getMessageVersion());
                        if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getUndoArrivalFlag())) {
                            undoArriveNeeded = true;
                            if (mailbagVO.getScannedDate() == null) {
                                mailbagVO.setScannedDate(localDateUtil.getLocalDate(
                                        mailbagVO.getScannedPort(), true));
                            }
                        }
                        Collection<DamagedMailbagVO> damagedMailBags = mailbagVO.getDamagedMailbags();
                        if (CollectionUtils.isNotEmpty(damagedMailBags)) {
                            for (DamagedMailbagVO damagedMailbagVO : damagedMailBags) {
                                if (damagedMailbagVO.getPaCode() == null) {
                                    if (mailbagVO.getOoe() != null && !mailbagVO.getOoe().trim().isEmpty()) {
                                        String paCode = mailController.findPAForOfficeOfExchange(mailbagVO.getCompanyCode(),
                                                mailbagVO.getOoe());
                                        if (paCode != null && !paCode.isEmpty()) {
                                            damagedMailbagVO.setPaCode(paCode);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        log.debug(getClass().getSimpleName() + " : " + "updateMailbagSource" + " Exiting");
    }

    private void updateMailbagDocumentDetailsForImport(MailArrivalVO mailArrivalVO) {
        log.debug(getClass().getSimpleName() + " : " + "updateMailbagDocumentDetailsForImport" + " Entering");
        Collection<ContainerDetailsVO> containerDetails = mailArrivalVO.getContainerDetails();
        if (CollectionUtils.isNotEmpty(containerDetails)) {
            for (ContainerDetailsVO containerDetailsVO : containerDetails) {
                if (containerDetailsVO.getOperationFlag() != null) {
                    Collection<MailbagVO> mailbags = containerDetailsVO.getMailDetails();
                    if (CollectionUtils.isNotEmpty(mailbags )) {
                        MailInConsignmentVO mailInConsignmentVO = null;
                        for (MailbagVO mailbagVO : mailbags) {
                            if (mailbagVO.getOperationalFlag() != null) {
                                mailInConsignmentVO = mailbagVO.getMailConsignmentVO();
                                if (mailInConsignmentVO != null) {
                                    mailbagVO.setConsignmentNumber(mailInConsignmentVO.getConsignmentNumber());
                                    mailbagVO.setConsignmentSequenceNumber(
                                            mailInConsignmentVO.getConsignmentSequenceNumber());
                                    if (!"MTK064".equalsIgnoreCase(mailArrivalVO.getMailSource())) {
                                        mailbagVO.setPaCode(mailInConsignmentVO.getPaCode());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        log.debug(getClass().getSimpleName() + " : " + "updateMailbagDocumentDetailsForImport" + " Exiting");
    }

    private void afterSavingPerform(MailArrivalVO mailArrivalVO, boolean isMonitorSLAEnabled, ZonedDateTime deliveryDate,
                                    boolean undoArriveNeeded, Collection<MailbagVO> arrivedMailBagsForMonitorSLA,
                                    Collection<MailbagVO> deliveredMailBagsForMonitorSLA) throws MailOperationsBusinessException {

        if (isMonitorSLAEnabled) {
            deliveryDate = LocalDateMapper.toZonedDateTime(new com.ibsplc.icargo.framework.util.time.LocalDate(mailController.getLogonAirport(), Location.ARP, true));
            if (arrivedMailBagsForMonitorSLA != null && !arrivedMailBagsForMonitorSLA.isEmpty()) {
                mailController.monitorMailSLAActivity(createMonitorSLAVosForInboundOperations(arrivedMailBagsForMonitorSLA, mailArrivalVO, MonitorMailSLAVO.MAILSTATUS_ARRIVED, null));
            }
            if (deliveredMailBagsForMonitorSLA != null && !deliveredMailBagsForMonitorSLA.isEmpty()) {
                mailController.monitorMailSLAActivity(createMonitorSLAVosForInboundOperations(deliveredMailBagsForMonitorSLA, mailArrivalVO, MonitorMailSLAVO.MAILSTATUS_DELIVERED, deliveryDate));
            }
        }
        if (undoArriveNeeded) {
            log.debug("" + "Going To undo arrive ..." + " " + mailArrivalVO);
            mailController.undoArriveContainer(mailArrivalVO);
        }
        boolean provisionalRateImport = false;
        Collection<RateAuditVO> rateAuditVOs = mailController.createRateAuditVOs(mailArrivalVO.getContainerDetails(),
                MailConstantsVO.MAIL_STATUS_ARRIVED, provisionalRateImport);
        log.debug("" + "RateAuditVO-->" + " " + rateAuditVOs);
        if (CollectionUtils.isNotEmpty(rateAuditVOs)) {
            String importEnabled = mailController.findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
            if (importEnabled != null && importEnabled.contains("A")) {
                try {
                    mailOperationsMRAProxy.importMRAData(rateAuditVOs);
                } catch (BusinessException e) {
                    throw new SystemException(e.getMessage(), e.getMessage(), e);
                }
            }
        }
        String provisionalRateimportEnabled = mailController.findSystemParameterValue(
                MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
        if (MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)) {
            provisionalRateImport = true;
            Collection<RateAuditVO> provisionalRateAuditVOs = mailController.createRateAuditVOs(
                    mailArrivalVO.getContainerDetails(), MailConstantsVO.MAIL_STATUS_ARRIVED, provisionalRateImport);
            if (provisionalRateAuditVOs != null && !provisionalRateAuditVOs.isEmpty()) {
                try {
                    mailOperationsMRAProxy.importMailProvisionalRateData(provisionalRateAuditVOs);
                } catch (BusinessException e) {
                    throw new SystemException(e.getMessage(), e.getMessage(), e);
                }
            }
        }
    }

    private Collection<com.ibsplc.neoicargo.mail.vo.MonitorMailSLAVO> createMonitorSLAVosForInboundOperations(Collection<MailbagVO> mailbags,
                                                                                                              MailArrivalVO mailArrivalVo, String activity, ZonedDateTime deliveryDate) {
        log.debug(getClass().getSimpleName() + " : " + "createMonitorSLAVosForInboundOperations" + " Entering");
        com.ibsplc.neoicargo.mail.vo.MonitorMailSLAVO monitorSLAVo = null;
        Collection<com.ibsplc.neoicargo.mail.vo.MonitorMailSLAVO> monitorSLAVos = new ArrayList<com.ibsplc.neoicargo.mail.vo.MonitorMailSLAVO>();
        for (MailbagVO mailBag : mailbags) {
            monitorSLAVo = new com.ibsplc.neoicargo.mail.vo.MonitorMailSLAVO();
            monitorSLAVo.setCompanyCode(mailArrivalVo.getCompanyCode());
            monitorSLAVo.setAirlineCode(mailArrivalVo.getOwnAirlineCode());
            monitorSLAVo.setAirlineIdentifier(mailArrivalVo.getOwnAirlineId());
            monitorSLAVo.setFlightCarrierCode(mailArrivalVo.getFlightCarrierCode());
            monitorSLAVo.setFlightCarrierIdentifier(mailArrivalVo.getCarrierId());
            monitorSLAVo.setFlightNumber(mailArrivalVo.getFlightNumber());
            monitorSLAVo.setActivity(activity);
            monitorSLAVo.setMailBagNumber(mailBag.getMailbagId());
            if (com.ibsplc.neoicargo.mail.vo.MonitorMailSLAVO.MAILSTATUS_ARRIVED.equals(activity)) {
                monitorSLAVo.setScanTime(mailBag.getScannedDate());
            } else {
                monitorSLAVo.setScanTime(deliveryDate);
            }
            if (com.ibsplc.neoicargo.mail.vo.MonitorMailSLAVO.MAILSTATUS_ARRIVED.equals(activity)) {
                monitorSLAVo.setOperationFlag(mailBag.getOperationalFlag());
            } else {
                monitorSLAVo.setOperationFlag(com.ibsplc.neoicargo.mail.vo.MonitorMailSLAVO.OPERATION_FLAG_UPDATE);
            }
            monitorSLAVos.add(monitorSLAVo);
        }
        log.debug(getClass().getSimpleName() + " : " + "createMonitorSLAVosForInboundOperations" + " Exiting");
        return monitorSLAVos;
    }
}
