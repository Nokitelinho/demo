package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class CarditPawbDetailsModel extends BaseModel {
	private String masterDocumentNumber;
	private String shipmentPrefix;
	private String consignmentOrigin;
	private String consignmentDestination;
	private String shipperCode;
	private String consigneeCode;
	private ShipmentValidationVO shipmentValidationVO;
	private Collection<MailInConsignmentModel> mailInConsignmentVOs;
	private ConsignmentDocumentModel consignmentDocumentVO;
	private ShipmentDetailVO shipmentDetailVO;
	private int ownerId;
	private String consignmentOriginAirport;
	private String consignmentDestinationAirport;
	private String agentCode;
	private String consigneeAgentCode;
	private String subType;
	private Collection<ShipmentDetailVO> shipmentDetailVOs;
	private Collection<ShipmentValidationVO> updatedshipmentValidationVOs;
	private ConsignmentDocumentModel existingMailBagsInConsignment;
	private Collection<RoutingInConsignmentModel> consignmentRoutingVOs;
	private Collection<ConsignmentScreeningModel> consignmentScreeningVOs;
	private String sourceIndicator;
	private boolean isAwbExistsForConsignment;
	private int totalPieces;
	private Measure totalWeight;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
