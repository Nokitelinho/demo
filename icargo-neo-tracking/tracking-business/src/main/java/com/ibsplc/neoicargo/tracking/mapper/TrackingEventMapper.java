package com.ibsplc.neoicargo.tracking.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.common.converters.DateConverter;
import com.ibsplc.neoicargo.common.mapper.BaseMapper;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import com.ibsplc.neoicargo.qms.events.ShipmentMilestonePlanCreatedEvent;
import com.ibsplc.neoicargo.qms.events.ShipmentMilestonePlanDeletedEvent;
import com.ibsplc.neoicargo.qms.events.ShipmentMilestonePlanDetailsEvent;
import com.ibsplc.neoicargo.tracking.events.ShipmentHistoryEvent;
import com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestoneEventVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanCreatedVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanDeletedVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanVO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring", config = CentralConfig.class, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {QuantityMapper.class}, imports = {QuantityMapper.class, Objects.class, DateConverter.class, DateTimeFormatter.class, Optional.class, MilestoneCodeEnum.class})
public interface TrackingEventMapper extends BaseMapper {

    @Mapping(target = "planVOs", expression = "java(event.getMilestoneDetails().stream().map(milestone -> constructShipmentMilestonePlanVO(milestone, event, contextUtil)).collect(java.util.stream.Collectors.toList()))")
    ShipmentMilestonePlanCreatedVO constructShipmentMilestonePlanCreatedVO(ShipmentMilestonePlanCreatedEvent event, @Context ContextUtil contextUtil);

    @Mapping(source = "event.shipmentKey", target = "shipmentKey")
    @Mapping(source = "event.shipmentType", target = "shipmentType")
    @Mapping(source = "event.sourceOfPlan", target = "source")
    @Mapping(source = "details.plannedPieces", target = "pieces")
    @Mapping(source = "details.plannedFlightNumber", target = "flightNumber")
    @Mapping(source = "details.plannedFlightCarrier", target = "flightCarrierCode")
    @Mapping(source = "details.plannedFlightDate", target = "flightDate", qualifiedByName = "planDateToLocalDate")
    @Mapping(source = "details.plannedTimeInUTC", target = "milestoneTimeUTC", qualifiedByName = "planTimeToLocalDateTime")
    @Mapping(source = "details.plannedTime", target = "milestoneTime", qualifiedByName = "planTimeToLocalDateTime")
    @Mapping(target = "companyCode", expression = "java(toCompanyCode(contextUtil))")
    ShipmentMilestonePlanVO constructShipmentMilestonePlanVO(ShipmentMilestonePlanDetailsEvent details, ShipmentMilestonePlanCreatedEvent event, @Context ContextUtil contextUtil);

    @Mapping(target = "planVOs", expression = "java(event.getMilestoneDetails().stream().map(milestone -> constructDeletedShipmentMilestonePlanVO(milestone, event, contextUtil)).collect(java.util.stream.Collectors.toList()))")
    ShipmentMilestonePlanDeletedVO constructShipmentMilestonePlanDeletedVO(ShipmentMilestonePlanDeletedEvent event,@Context ContextUtil contextUtil);
    
    @Mapping(source = "event.shipmentKey", target = "shipmentKey")
    @Mapping(source = "event.shipmentType", target = "shipmentType")
    @Mapping( ignore = true, target = "pieces")
    @Mapping(source = "details.plannedFlightNumber", target = "flightNumber")
    @Mapping(source = "details.plannedFlightCarrier", target = "flightCarrierCode")
    @Mapping(source = "details.plannedFlightDate", target = "flightDate", qualifiedByName = "planDateToLocalDate")
    @Mapping( ignore = true, target = "milestoneTimeUTC")
    @Mapping( ignore = true, target = "milestoneTime")
    @Mapping(target = "companyCode", expression = "java(toCompanyCode(contextUtil))")
    ShipmentMilestonePlanVO constructDeletedShipmentMilestonePlanVO(ShipmentMilestonePlanDetailsEvent details, ShipmentMilestonePlanDeletedEvent event, @Context ContextUtil contextUtil);

    @Mapping(source = "transactionPieces", target = "pieces")
    @Mapping(source = "transactionTime", target = "milestoneTime", qualifiedByName = "eventTimeToLocalDateTime")
    @Mapping(source = "transactionTime", target = "milestoneTimeUTC", qualifiedByName = "eventTimeToUtcLocalDateTime")
    @Mapping(source = "lastUpdatedUser", target = "lastUpdateUser")
    @Mapping(target = "companyCode", expression = "java(toCompanyCode(contextUtil))")
    @Mapping(target = "weight", expression = "java(quantityMapper.getQuantity(\"WGT\", Optional.ofNullable(event.getTransactionWeight()).orElse(0.0), Objects.nonNull(event.getUnitsOfMeasure())?event.getUnitsOfMeasure().getWeight():\"K\"))")
    ShipmentMilestoneEventVO constructShipmentMilestoneEventVO(ShipmentHistoryEvent event, @Context ContextUtil contextUtil);

    @Mapping(source = "weight", target = "transactionWeight")
    ShipmentHistoryEvent constructShipmentMilestoneEventVO(ShipmentMilestoneEventVO event);
}
