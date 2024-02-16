package com.ibsplc.neoicargo.mail.events;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.neoicargo.framework.core.lang.event.DomainEvent;
import com.ibsplc.neoicargo.mail.model.ContainerDetailsModel;
import com.ibsplc.neoicargo.mail.model.MailSummaryModel;
import com.ibsplc.neoicargo.mail.model.MailbagModel;
import com.ibsplc.neoicargo.mail.model.OperationalFlightModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class MailUpliftEvent implements DomainEvent, Serializable {
      OperationalFlightModel operationalFlightVO;
      Collection<ContainerDetailsModel> containerDetailsVOs;
      ProductVO productVO;
      DocumentFilterVO documentFilterVO;
      DocumentValidationVO documentValidationVO;
      boolean checkAWBAttached;
      Collection<MailbagModel> mailbagVOs;
      AirlineValidationVO airlineValidationVO;
      ShipmentValidationVO shipmentValidationVO;
      ShipmentDetailVO shipmentDetailVO;
      String flightNumber;
      LocalDate flightDate;
      Map<String, Collection<MailbagModel>> mailbagsMap;
      int carrierId;
      int legSerialNumber;
      long flightSequenceNumber;
      FlightValidationVO flightValidationVO;
      String carrierCode;
      boolean fromAttachAWB;
      Collection<ContainerDetailsModel> fromAttachContainerVOs;
      String operationFlag;
      String triggerPoint;
      boolean ignoreWarnings;
}
