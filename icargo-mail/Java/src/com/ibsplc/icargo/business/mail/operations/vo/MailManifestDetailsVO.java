package com.ibsplc.icargo.business.mail.operations.vo;

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

/**
 * @author a8893 for implementing Feature
 *
 */
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
	private LocalDate flightDate;
	private Map<String, Collection<MailbagVO>> mailbagsMap;
	private int carrierId;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private FlightValidationVO flightValidationVO;
	private String carrierCode;
	private boolean fromAttachAWB;
	private Collection<ContainerDetailsVO> fromAttachContainerVOs;

	public OperationalFlightVO getOperationalFlightVO() {
		return operationalFlightVO;
	}

	public void setOperationalFlightVO(OperationalFlightVO operationalFlightVO) {
		this.operationalFlightVO = operationalFlightVO;
	}

	public Collection<ContainerDetailsVO> getContainerDetailsVOs() {
		return containerDetailsVOs;
	}

	public void setContainerDetailsVOs(Collection<ContainerDetailsVO> containerDetailsVOs) {
		this.containerDetailsVOs = containerDetailsVOs;
	}

	public ProductVO getProductVO() {
		return productVO;
	}

	public void setProductVO(ProductVO productVO) {
		this.productVO = productVO;
	}

	public DocumentFilterVO getDocumentFilterVO() {
		return documentFilterVO;
	}

	public void setDocumentFilterVO(DocumentFilterVO documentFilterVO) {
		this.documentFilterVO = documentFilterVO;
	}

	public DocumentValidationVO getDocumentValidationVO() {
		return documentValidationVO;
	}

	public void setDocumentValidationVO(DocumentValidationVO documentValidationVO) {
		this.documentValidationVO = documentValidationVO;
	}

	public boolean isCheckAWBAttached() {
		return checkAWBAttached;
	}

	public void setCheckAWBAttached(boolean checkAWBAttached) {
		this.checkAWBAttached = checkAWBAttached;
	}

	public Collection<MailbagVO> getMailbagVOs() {
		return mailbagVOs;
	}

	public void setMailbagVOs(Collection<MailbagVO> mailbagVOs) {
		this.mailbagVOs = mailbagVOs;
	}

	public AirlineValidationVO getAirlineValidationVO() {
		return airlineValidationVO;
	}

	public void setAirlineValidationVO(AirlineValidationVO airlineValidationVO) {
		this.airlineValidationVO = airlineValidationVO;
	}

	public ShipmentValidationVO getShipmentValidationVO() {
		return shipmentValidationVO;
	}

	public void setShipmentValidationVO(ShipmentValidationVO shipmentValidationVO) {
		this.shipmentValidationVO = shipmentValidationVO;
	}

	public ShipmentDetailVO getShipmentDetailVO() {
		return shipmentDetailVO;
	}

	public void setShipmentDetailVO(ShipmentDetailVO shipmentDetailVO) {
		this.shipmentDetailVO = shipmentDetailVO;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public LocalDate getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	public Map<String, Collection<MailbagVO>> getMailbagsMap() {
		return mailbagsMap;
	}

	public void setMailbagsMap(Map<String, Collection<MailbagVO>> mailbagsMap) {
		this.mailbagsMap = mailbagsMap;
	}

	public int getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	public int getLegSerialNumber() {
		return legSerialNumber;
	}

	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	public FlightValidationVO getFlightValidationVO() {
		return flightValidationVO;
	}
	public void setFlightValidationVO(FlightValidationVO flightValidationVO) {
		this.flightValidationVO = flightValidationVO;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public boolean isFromAttachAWB() {
		return fromAttachAWB;
	}
	public void setFromAttachAWB(boolean fromAttachAWB) {
		this.fromAttachAWB = fromAttachAWB;
	}
	public Collection<ContainerDetailsVO> getFromAttachContainerVOs() {
		return fromAttachContainerVOs;
	}
	public void setFromAttachContainerVOs(Collection<ContainerDetailsVO> fromAttachContainerVOs) {
		this.fromAttachContainerVOs = fromAttachContainerVOs;
	}
	
	

}