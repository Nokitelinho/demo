package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import java.util.Map;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailManifestDetailsModel extends BaseModel {
	private OperationalFlightModel operationalFlightVO;
	private Collection<ContainerDetailsModel> containerDetailsVOs;
	private ProductVO productVO;
	private DocumentFilterVO documentFilterVO;
	private DocumentValidationVO documentValidationVO;
	private boolean checkAWBAttached;
	private Collection<MailbagModel> mailbagVOs;
	private AirlineValidationVO airlineValidationVO;
	private ShipmentValidationVO shipmentValidationVO;
	private ShipmentDetailVO shipmentDetailVO;
	private String flightNumber;
	private LocalDate flightDate;
	private Map<String, Collection<MailbagModel>> mailbagsMap;
	private int carrierId;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private FlightValidationVO flightValidationVO;
	private String carrierCode;
	private boolean fromAttachAWB;
	private Collection<ContainerDetailsModel> fromAttachContainerVOs;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
