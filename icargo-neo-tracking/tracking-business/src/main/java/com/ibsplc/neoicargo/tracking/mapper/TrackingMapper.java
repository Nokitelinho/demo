package com.ibsplc.neoicargo.tracking.mapper;

import com.ibsplc.neoicargo.awb.dao.entity.AWBUserNotification;
import com.ibsplc.neoicargo.awb.vo.AwbVO;
import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.common.converters.DateConverter;
import com.ibsplc.neoicargo.common.mapper.BaseMapper;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import com.ibsplc.neoicargo.notif.model.EmailRequestData;
import com.ibsplc.neoicargo.tracking.model.MilestoneMasterModel;
import com.ibsplc.neoicargo.tracking.model.ActualFlightDataModel;
import com.ibsplc.neoicargo.tracking.model.MilestoneModel;
import com.ibsplc.neoicargo.tracking.model.ShipmentActivityModel;
import com.ibsplc.neoicargo.tracking.model.ShipmentDetailsModel;
import com.ibsplc.neoicargo.tracking.model.SplitDetailsItemModel;
import com.ibsplc.neoicargo.tracking.model.SplitModel;
import com.ibsplc.neoicargo.tracking.vo.ActualFlightDataVO;
import com.ibsplc.neoicargo.tracking.vo.EmailMilestoneNotificationVO;
import com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneMasterVO;
import com.ibsplc.neoicargo.tracking.vo.MilestoneVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentActivityVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestoneEventVO;
import com.ibsplc.neoicargo.tracking.vo.SplitDetailsItemVO;
import com.ibsplc.neoicargo.tracking.vo.SplitVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring", config = CentralConfig.class, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {QuantityMapper.class}, imports = {QuantityMapper.class, Objects.class, DateConverter.class, DateTimeFormatter.class, Optional.class, MilestoneCodeEnum.class})
public interface TrackingMapper extends BaseMapper {

    @Mapping(target = "awbNumber", expression = "java(shipmentDetailsVO.getShipmentPrefix() + shipmentDetailsVO.getMasterDocumentNumber())")
    @Mapping(target = "statedWeight", expression = "java(Objects.nonNull(shipmentDetailsVO.getWeight()) ? shipmentDetailsVO.getWeight().getRoundedDisplayValue().doubleValue() : 0.0)")
    @Mapping(target = "statedVolume", expression = "java(Objects.nonNull(shipmentDetailsVO.getVolume()) ? shipmentDetailsVO.getVolume().getRoundedDisplayValue().doubleValue() : 0.0)")
    @Mapping(target = "unitsOfMeasure.weight", expression = "java(shipmentDetailsVO.getWeight() != null ? shipmentDetailsVO.getWeight().getDisplayUnit().getName() : null)")
    @Mapping(target = "unitsOfMeasure.volume", expression = "java(shipmentDetailsVO.getVolume() != null ? shipmentDetailsVO.getVolume().getDisplayUnit().getName() : null)")
    @Mapping(source = "departureTime", target = "departureTime", qualifiedByName = "shipmentTimeToLocalDateTime")
    @Mapping(source = "arrivalTime", target = "arrivalTime", qualifiedByName = "shipmentTimeToLocalDateTime")
    @Mapping(source = "shipmentDetailsVO.departureTimePostfix.label", target = "departureTimePostfix")
    @Mapping(source = "shipmentDetailsVO.arrivalTimePostfix.label", target = "arrivalTimePostfix")
    ShipmentDetailsModel constructShipmentDetailsModel(ShipmentDetailsVO shipmentDetailsVO);

    List<ShipmentDetailsModel> constructShipmentDetailsModels(List<ShipmentDetailsVO> shipments);

    @Mapping(source = "milestoneTime", target = "milestoneTime", qualifiedByName = "shipmentTimeToLocalDateTime")
    SplitDetailsItemModel constructSplitDetailsItem(SplitDetailsItemVO detailsItemVO);

    @Mapping(source = "milestoneTime", target = "milestoneTime", qualifiedByName = "shipmentTimeToLocalDateTime")
    ActualFlightDataModel constructSplitDetailsItem(ActualFlightDataVO actualFlightDataVO);

    List<SplitModel> constructSplits(List<SplitVO> shipments);

    @Mapping(target = "weight", source = "statedWeight")
    @Mapping(target = "volume", source = "statedVolume")
    @Mapping(target = "pieces", source = "statedPieces")
    @Mapping(target = "destinationAirportCode", source = "destination")
    @Mapping(target = "originAirportCode", source = "origin")
    ShipmentDetailsVO constructShipmentDetailsVO(AwbVO awbDetailVO);

    @Mapping(source = "milestoneVO.status.label", target = "status")
    MilestoneModel constructMilestoneModel(MilestoneVO milestoneVO);

    List<ShipmentActivityModel> constructShipmentActivities(List<ShipmentActivityVO> activities);

    @Mapping(source = "eventTime", target = "eventTime", qualifiedByName = "shipmentActivityTimeToLocalDateTime")
    @Mapping(source = "eventTimeUTC", target = "eventTimeUTC", qualifiedByName = "shipmentActivityTimeToLocalDateTime")
    @Mapping(target = "weight", expression = "java(Objects.nonNull(activity.getWeight()) ? activity.getWeight().getRoundedDisplayValue().doubleValue() : 0.0)")
    @Mapping(target = "weightUnit", expression = "java(activity.getWeight() != null ? activity.getWeight().getDisplayUnit().getName() : null)")
    ShipmentActivityModel constructShipmentActivity(ShipmentActivityVO activity);

    @Mapping(target = "toAddress", source = "to")
    @Mapping(target = "fromAddress", source = "from")
    @Mapping(target = "subject", source = "subject")
    @Mapping(target = "body", source = "body")
    EmailRequestData constructEmailRequestData(String from, List<String> to, String subject, String body);

    @Mapping(target = "pieces", source = "pieces")
    @Mapping(target = "milestoneTime", source = "event.milestoneTime", qualifiedByName = "shipmentTimeToLocalDateTime")
    EmailMilestoneNotificationVO constructEmailMilestoneNotificationVO(ShipmentMilestoneEventVO event, List<AWBUserNotification> notifications, Integer pieces);

    @Mapping(source = "milestoneTime", target = "eventTime")
    @Mapping(source = "milestoneTimeUTC", target = "eventTimeUTC")
    ShipmentActivityVO constructShipmentActivityVO(ShipmentMilestoneEventVO event);

	List<MilestoneMasterModel> constructMilestoneMasterModels(List<MilestoneMasterVO> milestoneVOs);
	
	MilestoneMasterModel constructMilestoneMasterModel(MilestoneMasterVO milestoneVO);
	
	

}
