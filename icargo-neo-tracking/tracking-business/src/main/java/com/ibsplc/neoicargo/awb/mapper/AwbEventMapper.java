package com.ibsplc.neoicargo.awb.mapper;

import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.events.AWBCreatedEvent;
import com.ibsplc.neoicargo.awb.events.AWBDeletedEvent;
import com.ibsplc.neoicargo.awb.events.AWBExecutedEvent;
import com.ibsplc.neoicargo.awb.events.AWBReopenedEvent;
import com.ibsplc.neoicargo.awb.events.AWBUpdatedEvent;
import com.ibsplc.neoicargo.awb.events.AWBVoidedEvent;
import com.ibsplc.neoicargo.awb.vo.AwbContactDetailsVO;
import com.ibsplc.neoicargo.awb.vo.AwbVO;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.common.converters.DateConverter;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring", config = CentralConfig.class, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {QuantityMapper.class}, imports = {QuantityMapper.class, Objects.class, DateConverter.class, DateTimeFormatter.class, Optional.class, ShipmentKey.class})
public interface AwbEventMapper {

    // Created Event

    @Mapping(target = "statedWeight", expression = "java(quantityMapper.getQuantity(\"WGT\", Optional.ofNullable(event.getStatedWeight()).orElse(0.0), event.getUnitsOfMeasure().getWeight()))")
    @Mapping(target = "statedVolume", expression = "java(quantityMapper.getQuantity(\"VOL\", Optional.ofNullable(event.getStatedVolume()).orElse(0.0), event.getUnitsOfMeasure().getVolume()))")
    @Mapping(target = "lastUpdatedTime", expression = "java(java.util.Objects.nonNull(event.getLastUpdatedTime()) ? "
            + "java.time.LocalDateTime.parse(event.getLastUpdatedTime(), java.time.format.DateTimeFormatter.ofPattern(\"dd-MM-yyyy HH:mm:ss.SSS\")) : null)")
    @Mapping(target = "shipmentSequenceNumber", expression = "java(java.util.Objects.nonNull(event.getShipmentSequenceNumber()) ? "
            + "event.getShipmentSequenceNumber() : 0)")
    @Mapping(target = "companyCode", expression = "java(java.util.Objects.nonNull(contextUtil.callerLoginProfile().getCompanyCode()) ? "
            + "contextUtil.callerLoginProfile().getCompanyCode() : null)")
    @Mapping(target = "awbContactDetailsVO", expression = "java(java.util.Objects.isNull(event.getShipperCode()) && java.util.Objects.isNull(event.getConsigneeCode())? null : constructAwbContactDetails(event, contextUtil))")
    AwbVO constructAwbVO(AWBCreatedEvent event, @Context ContextUtil contextUtil);

    @Mapping(target = "companyCode", expression = "java(java.util.Objects.nonNull(contextUtil.callerLoginProfile().getCompanyCode()) ? "
            + "contextUtil.callerLoginProfile().getCompanyCode() : null)")
    @Mapping(target = "lastUpdatedTime", expression = "java(java.util.Objects.nonNull(event.getLastUpdatedTime()) ? "
            + "java.time.LocalDateTime.parse(event.getLastUpdatedTime(), java.time.format.DateTimeFormatter.ofPattern(\"dd-MM-yyyy HH:mm:ss.SSS\")) : null)")
    @Mapping(target = "shipperDetails", expression = "java(event.getAwbContacts().stream().filter(contact->\"SHP\".equals(contact.getContactType())).findFirst().orElse(null))")
    @Mapping(target = "consigneeDetails", expression = "java(event.getAwbContacts().stream().filter(contact->\"CNS\".equals(contact.getContactType())).findFirst().orElse(null))")
    AwbContactDetailsVO constructAwbContactDetails(AWBCreatedEvent event, @Context ContextUtil contextUtil);

    // Updated event

    @Mapping(target = "statedWeight", expression = "java(quantityMapper.getQuantity(\"WGT\", Optional.ofNullable(event.getStatedWeight()).orElse(0.0), event.getUnitsOfMeasure().getWeight()))")
    @Mapping(target = "statedVolume", expression = "java(quantityMapper.getQuantity(\"VOL\", Optional.ofNullable(event.getStatedVolume()).orElse(0.0), event.getUnitsOfMeasure().getVolume()))")
    @Mapping(target = "lastUpdatedTime", expression = "java(java.util.Objects.nonNull(event.getLastUpdatedTime()) ? "
            + "java.time.LocalDateTime.parse(event.getLastUpdatedTime(), java.time.format.DateTimeFormatter.ofPattern(\"dd-MM-yyyy HH:mm:ss.SSS\")) : null)")
    @Mapping(target = "shipmentSequenceNumber", expression = "java(java.util.Objects.nonNull(event.getShipmentSequenceNumber()) ? "
            + "event.getShipmentSequenceNumber() : 0)")
    @Mapping(target = "companyCode", expression = "java(java.util.Objects.nonNull(contextUtil.callerLoginProfile().getCompanyCode()) ? "
            + "contextUtil.callerLoginProfile().getCompanyCode() : null)")
    @Mapping(target = "awbContactDetailsVO", expression = "java(java.util.Objects.isNull(event.getShipperCode()) && java.util.Objects.isNull(event.getConsigneeCode())? null : constructAwbUpdatedContactDetails(event, contextUtil))")
    AwbVO constructAwbVO(AWBUpdatedEvent event, @Context ContextUtil contextUtil);

    @Mapping(target = "companyCode", expression = "java(java.util.Objects.nonNull(contextUtil.callerLoginProfile().getCompanyCode()) ? "
            + "contextUtil.callerLoginProfile().getCompanyCode() : null)")
    @Mapping(target = "lastUpdatedTime", expression = "java(java.util.Objects.nonNull(event.getLastUpdatedTime()) ? "
            + "java.time.LocalDateTime.parse(event.getLastUpdatedTime(), java.time.format.DateTimeFormatter.ofPattern(\"dd-MM-yyyy HH:mm:ss.SSS\")) : null)")
    @Mapping(target = "shipperDetails", expression = "java(event.getAwbContacts().stream().filter(contact->\"SHP\".equals(contact.getContactType())).findFirst().orElse(null))")
    @Mapping(target = "consigneeDetails", expression = "java(event.getAwbContacts().stream().filter(contact->\"CNS\".equals(contact.getContactType())).findFirst().orElse(null))")
    AwbContactDetailsVO constructAwbUpdatedContactDetails(AWBUpdatedEvent event, @Context ContextUtil contextUtil);

    AWBCreatedEvent constructAwbCreatedEvent(AwbVO awbVO);

    // validation VO

    AwbValidationVO constructAwbValidationVo(AWBVoidedEvent event);

    AwbValidationVO constructAwbValidationVo(AWBReopenedEvent event);

    AwbValidationVO constructAwbValidationVo(AWBExecutedEvent event);

    AwbValidationVO constructAwbValidationVo(AWBDeletedEvent event);

}
