package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;

@Setter
@Getter
public class CarditPawbDetailsVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	/** 
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
	private Collection<ShipmentValidationVO> updatedshipmentValidationVOs;
	private ConsignmentDocumentVO existingMailBagsInConsignment;
	private Collection<RoutingInConsignmentVO> consignmentRoutingVOs;
	private Collection<ConsignmentScreeningVO> consignmentScreeningVOs;
	private String sourceIndicator;
	private boolean isAwbExistsForConsignment;
	private int totalPieces;
	private Quantity totalWeight;

	public void setAwbExistsForConsignment(boolean isAwbExistsForConsignment) {
		this.isAwbExistsForConsignment = isAwbExistsForConsignment;
	}
}
