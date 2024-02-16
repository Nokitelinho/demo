package com.ibsplc.neoicargo.tracking.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import com.ibsplc.neoicargo.tracking.dao.entity.Milestone;
import com.ibsplc.neoicargo.tracking.dao.entity.ShipmentMilestoneEvent;
import com.ibsplc.neoicargo.tracking.dao.entity.ShipmentMilestonePlan;
import com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneMasterVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestoneEventVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring", config = CentralConfig.class, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {QuantityMapper.class}, imports = {Objects.class, MilestoneCodeEnum.class, QuantityMapper.class, Optional.class})
public interface TrackingEntityMapper {

    ShipmentMilestonePlanVO constructShipmentMilestonePlanVO(ShipmentMilestonePlan shipmentMilestonePlan);

    List<ShipmentMilestonePlanVO> constructShipmentMilestonePlansVO(List<ShipmentMilestonePlan> shipments);

    @Mapping(target = "weight", expression = "java(quantityMapper.getQuantity(\"WGT\", Optional.ofNullable(event.getWeight()).orElse(0.0), event.getEUnit()==null? \"K\": event.getEUnit().getWeight()))")
    ShipmentMilestoneEventVO constructShipmentMilestoneEventVO(ShipmentMilestoneEvent event);

    List<ShipmentMilestoneEventVO> constructShipmentMilestoneEventsVO(List<ShipmentMilestoneEvent> shipments);

    List<ShipmentMilestonePlan> constructShipmentMilestonePlans(List<ShipmentMilestonePlanVO> planVOs);

    @Mapping(target = "EUnit.weight", expression = "java(shipmentMilestoneEventVO.getWeight()==null?\"K\":shipmentMilestoneEventVO.getWeight().getDisplayUnit().getName())")
    ShipmentMilestoneEvent constructShipmentMilestoneEvent(ShipmentMilestoneEventVO event);
    
    List<MilestoneMasterVO> constructMilestoneVOs(List<Milestone> milestone);
    
    MilestoneMasterVO constructMilestoneVO(Milestone milestone);
}
