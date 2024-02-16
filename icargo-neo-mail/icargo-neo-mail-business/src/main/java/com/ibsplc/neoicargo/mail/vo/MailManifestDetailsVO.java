package com.ibsplc.neoicargo.mail.vo;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Map;

/**
 * @author a8893 for implementing Feature
 *
 */
@Setter
@Getter
public class MailManifestDetailsVO extends AbstractVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private OperationalFlightVO operationalFlightVO;
	private Collection<ContainerDetailsVO> containerDetailsVOs;
	private ProductVO productVO;
	private DocumentFilterVO documentFilterVO;
	private DocumentValidationVO documentValidationVO;
	private boolean checkAWBAttached;
	private Collection<MailbagVO> mailbagVOs;
	private AirlineValidationVO airlineValidationVO;
	private ShipmentValidationVO shipmentValidationVO;
	private ShipmentDetailVO shipmentDetailVO;
	private String flightNumber;
	private ZonedDateTime flightDate;
	private Map<String, Collection<MailbagVO>> mailbagsMap;
	private int carrierId;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private FlightValidationVO flightValidationVO;
	private String carrierCode;
	private boolean fromAttachAWB;
	private Collection<ContainerDetailsVO> fromAttachContainerVOs;


}