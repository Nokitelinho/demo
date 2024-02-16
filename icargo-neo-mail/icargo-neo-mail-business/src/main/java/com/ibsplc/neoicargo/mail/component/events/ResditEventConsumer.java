package com.ibsplc.neoicargo.mail.component.events;

import com.ibsplc.neoicargo.mail.events.*;
import com.ibsplc.neoicargo.mail.service.MailOperationsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(MailEventsSink.class)
@Slf4j
@AllArgsConstructor
public class ResditEventConsumer {

    private MailOperationsService mailOperationsService;


    @StreamListener(target = MailEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='ResditAcceptedEvent' && headers['Event-Source']=='neo-mail-business'")
    public void flagResditForAcceptance(@Payload ResditAcceptedEvent resditAcceptedEvent) {
        try {
            log.info("Entering -> ResditEventConsumer.flagResditForAcceptance");
            if (resditAcceptedEvent != null) {
                mailOperationsService.flagResditsForAcceptance(resditAcceptedEvent.getMailAcceptanceModel(),
                        resditAcceptedEvent.isHasFlightDeparted(), resditAcceptedEvent.getAcceptedMailbags(),
                        resditAcceptedEvent.getAcceptedUlds());
            }
        } catch (Exception e) {
            System.out.println("Exception" + " " + e.getMessage());
        }
        log.info("Exiting -> ResditEventConsumer.flagResditForAcceptance");
    }

    @StreamListener(target = MailEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='MailbagResditEvent' && headers['Event-Source']=='neo-mail-business'")
    public void flagResditForMailbags(@Payload MailbagResditEvent mailbagResditEvent) {
        try {
            log.info("Entering -> ResditEventConsumer.flagResditForMailbags");
            if (mailbagResditEvent != null) {
                mailOperationsService.flagResditForMailbags(mailbagResditEvent.getMailBags(), mailbagResditEvent.getPol(),
                        mailbagResditEvent.getEventCode());
            }
        } catch (Exception e) {
            System.out.println("Exception" + " " + e.getMessage());
        }
        log.info("Exiting -> ResditEventConsumer.flagResditForMailbags");
    }

    @StreamListener(target = MailEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='MailbagTransferResditEvent' && headers['Event-Source']=='neo-mail-business'")
    public void flagResditsForMailbagTransfer(@Payload MailbagTransferResditEvent mailbagTransferResditEvent) {
        try {
            log.info("Entering -> ResditEventConsumer.flagResditsForMailbagTransfer");
            if (mailbagTransferResditEvent != null) {
                mailOperationsService.flagResditsForMailbagTransfer(mailbagTransferResditEvent.getTransferredMails(),
                        mailbagTransferResditEvent.getContainer());
            }
        } catch (Exception e) {
            System.out.println("Exception" + " " + e.getMessage());
        }
        log.info("Exiting -> ResditEventConsumer.flagResditsForMailbagTransfer");
    }

    @StreamListener(target = MailEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='MailbagReassignResditEvent' && headers['Event-Source']=='neo-mail-business'")
    public void flagResditsForMailbagReassign(@Payload MailbagReassignResditEvent mailbagReassignResditEvent) {
        try {
            log.info("Entering -> ResditEventConsumer.flagResditsForMailbagReassign");
            if (mailbagReassignResditEvent != null) {
                mailOperationsService.flagResditsForMailbagReassign(mailbagReassignResditEvent.getTransferredMails(),
                        mailbagReassignResditEvent.getContainer());
            }
        } catch (Exception e) {
            System.out.println("Exception" + " " + e.getMessage());
        }
        log.info("Exiting -> ResditEventConsumer.flagResditsForMailbagReassign");
    }
    @StreamListener(target = MailEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='MailbagsFromReassignResditEvent' && headers['Event-Source']=='neo-mail-business'")
    public void flagResditForMailbagsFromReassign(@Payload MailbagsFromReassignResditEvent mailbagsFromReassignResditEvent) {
        try {
            log.info("Entering -> ResditEventConsumer.flagResditForMailbagsFromReassign");
            if (mailbagsFromReassignResditEvent != null) {
                mailOperationsService.flagResditForMailbagsFromReassign(mailbagsFromReassignResditEvent.getMailBags(),
                        mailbagsFromReassignResditEvent.getPol(),mailbagsFromReassignResditEvent.getEventCode());
            }
        } catch (Exception e) {
            System.out.println("Exception" + " " + e.getMessage());
        }
        log.info("Exiting -> ResditEventConsumer.flagResditsForMailbagTransfer");
    }
    @StreamListener(target = MailEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='ContainerTransferResditEvent' && headers['Event-Source']=='neo-mail-business'")
    public void flagResditsForContainerTransfer(@Payload ContainerTransferResditEvent containerTransferResditEvent) {
        try {
            log.info("Entering -> ResditEventConsumer.flagResditsForContainerTransfer");
            if (containerTransferResditEvent != null) {
                mailOperationsService.flagResditsForContainerTransfer(containerTransferResditEvent.getTransferredMails(),
                        containerTransferResditEvent.getContainers(),containerTransferResditEvent.getOperationalFlight());
            }
        } catch (Exception e) {
            System.out.println("Exception" + " " + e.getMessage());
        }
        log.info("Exiting -> ResditEventConsumer.flagResditsForContainerTransfer");
    }
    @StreamListener(target = MailEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='ContainerReassignResditEvent' && headers['Event-Source']=='neo-mail-business'")
    public void flagResditForContainerReassign(@Payload  ContainerReassignResditEvent containerReassignResditEvent) {
        try {
            log.info("Entering -> ResditEventConsumer.flagResditForContainerReassign");
            if (containerReassignResditEvent != null) {
                mailOperationsService.flagResditForContainerReassign(containerReassignResditEvent.getMailbags(),
                        containerReassignResditEvent.getContainerDetails(),containerReassignResditEvent.getToFlightVO(),
                        containerReassignResditEvent.isHasFlightDeparted());
            }
        } catch (Exception e) {
            System.out.println("Exception" + " " + e.getMessage());
        }
        log.info("Exiting -> ResditEventConsumer.flagResditForContainerReassign");
    }
    @StreamListener(target = MailEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='ArrivalResditEvent' && headers['Event-Source']=='neo-mail-business'")
    public void flagResditsForArrival(@Payload  ArrivalResditEvent arrivalResditEvent) {
        try {
            log.info("Entering -> ResditEventConsumer.flagResditsForArrival");
            if (arrivalResditEvent != null) {
                mailOperationsService.flagResditsForArrival(arrivalResditEvent.getMailArrivalDetails(),
                        arrivalResditEvent.getArrivedMailbags(),arrivalResditEvent.getArrivedContainers());
            }
        } catch (Exception e) {
            System.out.println("Exception" + " " + e.getMessage());
        }
        log.info("Exiting -> ResditEventConsumer.flagResditsForArrival");
    }
    @StreamListener(target = MailEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='FlightDepartureResditEvent' && headers['Event-Source']=='neo-mail-business'")
    public void  flagResditsForFlightDeparture(@Payload FlightDepartureResditEvent flightDepartureResditEvent) {
        try {
            log.info("Entering -> ResditEventConsumer.flagResditsForFlightDeparture");

            if (flightDepartureResditEvent != null) {
                mailOperationsService.flagResditsForFlightDeparture(flightDepartureResditEvent.getCompanyCode(), flightDepartureResditEvent.getCarrierId(),
                        flightDepartureResditEvent.getMailBags(), flightDepartureResditEvent.getContainerDetails(), flightDepartureResditEvent.getPol());
            }
        } catch (Exception e) {
            System.out.println("Exception" + " " + e.getMessage());
        }
        log.info("Exiting -> ResditEventConsumer.flagResditsForFlightDeparture");

    }

    @StreamListener(target = MailEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='TransportCompletedResditEvent' && headers['Event-Source']=='neo-mail-business'")
    public void  flagResditsForTransportCompleted(@Payload TransportCompletedResditEvent transportCompletedEvent){
        try{
            log.info("Entering -> ResditEventConsumer.flagResditsForTransportCompleted");

            if(transportCompletedEvent!=null){
                mailOperationsService.flagResditsForTransportCompleted(transportCompletedEvent.getCompanyCode(),transportCompletedEvent.getCarrierId(),
                        transportCompletedEvent.getMailBags(),transportCompletedEvent.getContainerDetails(),transportCompletedEvent.getEventPort(),
                        transportCompletedEvent.getFlightArrivedPort());
            }
        } catch (Exception e) {
            System.out.println("Exception" + " " + e.getMessage());
        }
        log.info("Exiting -> ResditEventConsumer.flagResditsForFlightDeparture");

    }
    @StreamListener(target = MailEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='FoundArrivalResditEvent' && headers['Event-Source']=='neo-mail-business'")
    public void flagResditsForFoundArrival(@Payload FoundArrivalResditEvent arrivalEvent){
        try{
            log.info("Entering -> ResditEventConsumer.flagResditsForFoundArrival");

            if(arrivalEvent!=null){
                mailOperationsService.flagResditsForFoundArrival(arrivalEvent.getMailArrivalDetails());
            }
        } catch (Exception e) {
            System.out.println("Exception" + " " + e.getMessage());
        }
        log.info("Exiting -> ResditEventConsumer.flagResditsForFoundArrival");
    }
    @StreamListener(target = MailEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='LostResditEvent' && headers['Event-Source']=='neo-mail-business'")
    public void flagLostResditsForMailbags(@Payload LostResditEvent lostResditEvent){
        try{
            log.info("Entering -> ResditEventConsumer.flagResditsForFoundArrival");

            if(lostResditEvent!=null){
                mailOperationsService.flagLostResditsForMailbags(lostResditEvent.getOperationalFlight());
            }
        } catch (Exception e) {
            System.out.println("Exception" + " " + e.getMessage());
        }
        log.info("Exiting -> ResditEventConsumer.flagResditsForFoundArrival");
    }
}
