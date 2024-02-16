package com.ibsplc.icargo.business.mail.operations.vo;
import java.util.Collection;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
public class CarditPawbDetailsVO extends AbstractVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String masterDocumentNumber;
	private String shipmentPrefix;
	private String consignmentOrigin;
	private String consignmentDestination;
	private String shipperCode;
	private String consigneeCode;
	private ShipmentValidationVO shipmentValidationVO;
	private Collection<MailInConsignmentVO> mailInConsignmentVOs;
	private ConsignmentDocumentVO consignmentDocumentVO;
	private ShipmentDetailVO shipmentDetailVO;
	private int ownerId;
	private String consignmentOriginAirport;
	private String consignmentDestinationAirport;
	private String agentCode;
	private String consigneeAgentCode;
	private String subType;
	private Collection<ShipmentDetailVO> shipmentDetailVOs;
	private Collection<ShipmentValidationVO>updatedshipmentValidationVOs;
	private ConsignmentDocumentVO existingMailBagsInConsignment;
	private Collection<RoutingInConsignmentVO> consignmentRoutingVOs;
	private Collection<ConsignmentScreeningVO> consignmentScreeningVOs;
	private String sourceIndicator;
    private boolean isAwbExistsForConsignment;
    private int totalPieces;
    private Measure totalWeight;
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	public String getConsignmentOrigin() {
		return consignmentOrigin;
	}
	public void setConsignmentOrigin(String consignmentOrigin) {
		this.consignmentOrigin = consignmentOrigin;
	}
	public String getConsignmentDestination() {
		return consignmentDestination;
	}
	public void setConsignmentDestination(String consignmentDestination) {
		this.consignmentDestination = consignmentDestination;
	}
	public String getShipperCode() {
		return shipperCode;
	}
	public void setShipperCode(String shipperCode) {
		this.shipperCode = shipperCode;
	}
	public String getConsigneeCode() {
		return consigneeCode;
	}
	public void setConsigneeCode(String consigneeCode) {
		this.consigneeCode = consigneeCode;
	}
	public ShipmentValidationVO getShipmentValidationVO() {
		return shipmentValidationVO;
	}
	public void setShipmentValidationVO(ShipmentValidationVO shipmentValidationVO) {
		this.shipmentValidationVO = shipmentValidationVO;
	}
	public Collection<MailInConsignmentVO> getMailInConsignmentVOs() {
		return mailInConsignmentVOs;
	}
	public void setMailInConsignmentVOs(Collection<MailInConsignmentVO> mailInConsignmentVOs) {
		this.mailInConsignmentVOs = mailInConsignmentVOs;
	}
	public ConsignmentDocumentVO getConsignmentDocumentVO() {
		return consignmentDocumentVO;
	}
	public void setConsignmentDocumentVO(ConsignmentDocumentVO consignmentDocumentVO) {
		this.consignmentDocumentVO = consignmentDocumentVO;
	}
	public ShipmentDetailVO getShipmentDetailVO() {
		return shipmentDetailVO;
	}
	public void setShipmentDetailVO(ShipmentDetailVO shipmentDetailVO) {
		this.shipmentDetailVO = shipmentDetailVO;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public String getConsignmentOriginAirport() {
		return consignmentOriginAirport;
	}
	public void setConsignmentOriginAirport(String consignmentOriginAirport) {
		this.consignmentOriginAirport = consignmentOriginAirport;
	}
	public String getConsignmentDestinationAirport() {
		return consignmentDestinationAirport;
	}
	public void setConsignmentDestinationAirport(String consignmentDestinationAirport) {
		this.consignmentDestinationAirport = consignmentDestinationAirport;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getConsigneeAgentCode() {
		return consigneeAgentCode;
	}
	public void setConsigneeAgentCode(String consigneeAgentCode) {
		this.consigneeAgentCode = consigneeAgentCode;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
public Collection<ShipmentDetailVO> getShipmentDetailVOs() {
		return shipmentDetailVOs;
	}
	public void setShipmentDetailVOs(Collection<ShipmentDetailVO> shipmentDetailVOs) {
		this.shipmentDetailVOs = shipmentDetailVOs;
	}
	public Collection<ShipmentValidationVO> getUpdatedshipmentValidationVOs() {
		return updatedshipmentValidationVOs;
	}
	public void setUpdatedshipmentValidationVOs(Collection<ShipmentValidationVO> updatedshipmentValidationVOs) {
		this.updatedshipmentValidationVOs = updatedshipmentValidationVOs;
	}
	
	public ConsignmentDocumentVO getExistingMailBagsInConsignment() {
		return existingMailBagsInConsignment;
	}
	public void setExistingMailBagsInConsignment(ConsignmentDocumentVO existingMailBagsInConsignment) {
		this.existingMailBagsInConsignment = existingMailBagsInConsignment;
	}
	public Collection<RoutingInConsignmentVO> getConsignmentRoutingVOs() {
		return consignmentRoutingVOs;
	}
	public void setConsignmentRoutingVOs(Collection<RoutingInConsignmentVO> consignmentRoutingVOs) {
		this.consignmentRoutingVOs = consignmentRoutingVOs;
	}
	public Collection<ConsignmentScreeningVO> getConsignmentScreeningVOs() {
		return consignmentScreeningVOs;
	}
	public void setConsignmentScreeningVOs(Collection<ConsignmentScreeningVO> consignmentScreeningVOs) {
		this.consignmentScreeningVOs = consignmentScreeningVOs;
	}
	public String getSourceIndicator() {
		return sourceIndicator;
	}
	public void setSourceIndicator(String sourceIndicator) {
		this.sourceIndicator = sourceIndicator;
	}
	public boolean isAwbExistsForConsignment() {
		return isAwbExistsForConsignment;
	}
	public void setAwbExistsForConsignment(boolean isAwbExistsForConsignment) {
		this.isAwbExistsForConsignment = isAwbExistsForConsignment;
	}
	public int getTotalPieces() {
		return totalPieces;
	}
	public void setTotalPieces(int totalPieces) {
		this.totalPieces = totalPieces;
	}
	public Measure getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Measure totalWeight) {
		this.totalWeight = totalWeight;
	}  
	
}
