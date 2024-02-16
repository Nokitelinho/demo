package com.ibsplc.neoicargo.mail.mapper;

import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.mail.events.*;
import com.ibsplc.neoicargo.mail.model.*;
import com.ibsplc.neoicargo.mail.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component("mailEventMapper")
public class MailEventsMapper {
    @Autowired
    private MailOperationsMapper mailOperationsMapper;
    public ResditAcceptedEvent constructResditAcceptedEvent(MailAcceptanceModel mailAcceptanceModel, boolean hasFlightDeparted, Collection<MailbagModel> acceptedMailbags,
                                                            Collection<ContainerDetailsModel> acceptedUlds){
        ResditAcceptedEvent resditAcceptedEvent = new ResditAcceptedEvent();
        resditAcceptedEvent.setMailAcceptanceModel(mailAcceptanceModel);
        resditAcceptedEvent.setAcceptedMailbags(acceptedMailbags);
        resditAcceptedEvent.setAcceptedUlds(acceptedUlds);
        resditAcceptedEvent.setHasFlightDeparted(hasFlightDeparted);
        return resditAcceptedEvent;
    }

    public MailbagResditEvent constructMailbagResditEvent( Collection<MailbagModel> mailbags,String eventAirport, String eventCode){
        MailbagResditEvent mailbagResditEvent= new MailbagResditEvent();
        mailbagResditEvent.setMailBags(mailbags);
        mailbagResditEvent.setPol(eventAirport);
        mailbagResditEvent.setEventCode(eventCode);
        return mailbagResditEvent;
    }
    public MailbagTransferResditEvent constructMailbagTransferResditEvent(Collection<MailbagModel> mailbags, ContainerModel containerModel){
        MailbagTransferResditEvent mailbagTransferResditEvent = new MailbagTransferResditEvent();
        mailbagTransferResditEvent.setTransferredMails(mailbags);
        mailbagTransferResditEvent.setContainer(containerModel);
        return  mailbagTransferResditEvent;
    }
    public MailbagReassignResditEvent constructMailbagReassignResditEvent(Collection<MailbagModel> mailbags, ContainerModel containerModel){
        MailbagReassignResditEvent mailbagReassignResditEvent = new MailbagReassignResditEvent();
        mailbagReassignResditEvent.setTransferredMails(mailbags);
        mailbagReassignResditEvent.setContainer(containerModel);
        return  mailbagReassignResditEvent;
    }
    public MailbagsFromReassignResditEvent constructMailbagsFromReassignResditEvent( Collection<MailbagModel> mailbags,String eventAirport, String eventCode){
        MailbagsFromReassignResditEvent MailbagsFromReassignResditEvent= new MailbagsFromReassignResditEvent();
        MailbagsFromReassignResditEvent.setMailBags(mailbags);
        MailbagsFromReassignResditEvent.setPol(eventAirport);
        MailbagsFromReassignResditEvent.setEventCode(eventCode);
        return MailbagsFromReassignResditEvent;
    }
    public ContainerTransferResditEvent constructContainerTransferResditEvent(Collection<MailbagModel> transferredMails,
                                                                              Collection<ContainerModel> containers, OperationalFlightModel operationalFlight){
        ContainerTransferResditEvent containerTransferResditEvent = new ContainerTransferResditEvent();
        containerTransferResditEvent.setTransferredMails(transferredMails);
        containerTransferResditEvent.setContainers(containers);
        containerTransferResditEvent.setOperationalFlight(operationalFlight);
        return containerTransferResditEvent;
    }
    public ContainerReassignResditEvent constructContainerReassignResditEvent(Collection<MailbagModel>mailbags,
                                                                              Collection<ContainerDetailsModel>containerDetails, OperationalFlightModel flight,boolean hasFlightDeparted){
        ContainerReassignResditEvent containerReassignResditEvent = new ContainerReassignResditEvent();
        containerReassignResditEvent.setMailbags(mailbags);
        containerReassignResditEvent.setContainerDetails(containerDetails);
        containerReassignResditEvent.setToFlightVO(flight);
        containerReassignResditEvent.setHasFlightDeparted(hasFlightDeparted);
        return containerReassignResditEvent;
    }
    public ArrivalResditEvent constructArrivalResditEvent(MailArrivalModel arrivalModel,Collection<MailbagModel>mailbagModels,
                                                          Collection<ContainerDetailsModel> arrivedContainers){
        ArrivalResditEvent arrivalResditEvent = new ArrivalResditEvent();
        arrivalResditEvent.setMailArrivalDetails(arrivalModel);
        arrivalResditEvent.setArrivedMailbags(mailbagModels);
        arrivalResditEvent.setArrivedContainers(arrivedContainers);
        return arrivalResditEvent;
    }
    public MailUpliftEvent constructMailUpliftEvent(MailManifestDetailsVO manifestDetailsVO) {
        MailUpliftEvent mailUpliftEvent = new MailUpliftEvent();
        mailUpliftEvent.setOperationalFlightVO(mailOperationsMapper.operationalFlightVOToOperationalFlightModel(manifestDetailsVO.getOperationalFlightVO()));
        mailUpliftEvent.setContainerDetailsVOs(mailOperationsMapper.containerDetailsVOsToContainerDetailsModel(manifestDetailsVO.getContainerDetailsVOs()));
        mailUpliftEvent.setProductVO(manifestDetailsVO.getProductVO());
        mailUpliftEvent.setDocumentFilterVO(manifestDetailsVO.getDocumentFilterVO());
        mailUpliftEvent.setDocumentValidationVO(manifestDetailsVO.getDocumentValidationVO());
        mailUpliftEvent.setCheckAWBAttached(manifestDetailsVO.isCheckAWBAttached());
        mailUpliftEvent.setMailbagVOs(mailOperationsMapper.mailbagVOsToMailbagModels(manifestDetailsVO.getMailbagVOs()));
        mailUpliftEvent.setAirlineValidationVO(manifestDetailsVO.getAirlineValidationVO());
        mailUpliftEvent.setShipmentValidationVO(manifestDetailsVO.getShipmentValidationVO());
        mailUpliftEvent.setShipmentDetailVO(manifestDetailsVO.getShipmentDetailVO());
        mailUpliftEvent.setFlightDate(LocalDateMapper.toLocalDate(manifestDetailsVO.getFlightDate()));
        mailUpliftEvent.setMailbagsMap(mailOperationsMapper.mailbagVOMapToMailbagModelMap(manifestDetailsVO.getMailbagsMap()));
        mailUpliftEvent.setCarrierId(manifestDetailsVO.getCarrierId());
        mailUpliftEvent.setLegSerialNumber(manifestDetailsVO.getLegSerialNumber());
        mailUpliftEvent.setFlightSequenceNumber(manifestDetailsVO.getFlightSequenceNumber());
        mailUpliftEvent.setFlightValidationVO(manifestDetailsVO.getFlightValidationVO());
        mailUpliftEvent.setCarrierCode(manifestDetailsVO.getCarrierCode());
        mailUpliftEvent.setFromAttachAWB(manifestDetailsVO.isFromAttachAWB());
        mailUpliftEvent.setFromAttachContainerVOs(mailOperationsMapper.containerDetailsVOsToContainerDetailsModel(manifestDetailsVO.getFromAttachContainerVOs()));
        mailUpliftEvent.setOperationFlag(manifestDetailsVO.getOperation());
        return mailUpliftEvent;
    }
    public FlightDepartureResditEvent constructFlightDepartureResditEvent(String companyCode, int carrierId, Collection<MailbagModel> mailbags,
                                                                          Collection<ContainerDetailsModel> containerDetails, String eventPort) {
        FlightDepartureResditEvent flightDepartureResditEvent = new FlightDepartureResditEvent();
        flightDepartureResditEvent.setCompanyCode(companyCode);
        flightDepartureResditEvent.setCarrierId(carrierId);
        flightDepartureResditEvent.setMailBags(mailbags);
        flightDepartureResditEvent.setContainerDetails(containerDetails);
        flightDepartureResditEvent.setPol(eventPort);
        return flightDepartureResditEvent;
    }

    public TransportCompletedResditEvent constructTransportCompletedResditEvent(String companyCode, int carrierId, Collection<MailbagModel> mailbags,
                                                                                Collection<ContainerDetailsModel> containerDetails, String eventPort, String flightArrivedPort) {
        TransportCompletedResditEvent transportCompletedResditEvent = new TransportCompletedResditEvent();
        transportCompletedResditEvent.setCompanyCode(companyCode);
        transportCompletedResditEvent.setCarrierId(carrierId);
        transportCompletedResditEvent.setMailBags(mailbags);
        transportCompletedResditEvent.setContainerDetails(containerDetails);
        transportCompletedResditEvent.setEventPort(eventPort);
        transportCompletedResditEvent.setFlightArrivedPort(flightArrivedPort);
        return  transportCompletedResditEvent;

    }

    public LostResditEvent constructLostResditEvent(OperationalFlightModel operationalFlight){
        LostResditEvent lostResditEvent = new LostResditEvent();
        lostResditEvent.setOperationalFlight(operationalFlight);
        return  lostResditEvent;
    }

public FoundArrivalResditEvent constructFoundArrivalResditEvent(MailArrivalModel arrivalModel){
    FoundArrivalResditEvent arrivalEvent = new FoundArrivalResditEvent();
        arrivalEvent.setMailArrivalDetails(arrivalModel);
        return arrivalEvent;
}
}
