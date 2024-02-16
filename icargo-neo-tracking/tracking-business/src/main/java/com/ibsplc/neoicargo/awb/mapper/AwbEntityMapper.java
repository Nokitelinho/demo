package com.ibsplc.neoicargo.awb.mapper;

import com.ibsplc.neoicargo.awb.dao.entity.AWBContactDetails;
import com.ibsplc.neoicargo.awb.dao.entity.AWBUserNotification;
import com.ibsplc.neoicargo.awb.dao.entity.Awb;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.vo.AwbContactDetailsVO;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationVO;
import com.ibsplc.neoicargo.awb.vo.AwbVO;
import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.common.converters.DateConverter;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring", config = CentralConfig.class, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {QuantityMapper.class}, imports = {QuantityMapper.class, Objects.class, DateConverter.class, DateTimeFormatter.class, Optional.class, ShipmentKey.class})
public interface AwbEntityMapper {

    @Mapping(target = "lastUpdatedTime", expression = "java(java.util.Objects.nonNull(awbVO.getLastUpdatedTime())?"
            + "java.sql.Timestamp.valueOf(awbVO.getLastUpdatedTime().now()):null)")
    @Mapping(target = "shipmentKey", expression = "java(new ShipmentKey(awbVO.getShipmentPrefix(),awbVO.getMasterDocumentNumber()))")
    @Mapping(target = "awbContactDetails", expression = "java(constructAwbContactDetails(awbVO.getAwbContactDetailsVO()))")
    @Mapping(target = "EUnit.weight", source = "awbVO.unitsOfMeasure.weight")
    @Mapping(target = "EUnit.volume", source = "awbVO.unitsOfMeasure.volume")
    Awb constructAwb(AwbVO awbVO);

    @Mapping(target = "lastUpdatedTime", expression = "java(java.util.Objects.nonNull(awbContactDetailsVO.getLastUpdatedTime())?"
            + "java.sql.Timestamp.valueOf(awbContactDetailsVO.getLastUpdatedTime().now()):null)")
    AWBContactDetails constructAwbContactDetails(AwbContactDetailsVO awbContactDetailsVO);

    @Mapping(target = "lastUpdatedTime", expression = "java(java.util.Objects.nonNull(awbContactDetails.getLastUpdatedTime())?"
            + "(awbContactDetails.getLastUpdatedTime().toLocalDateTime()):null)")
    AwbContactDetailsVO constructAwbContactDetailsVO(AWBContactDetails awbContactDetails);

    @Mapping(target = "lastUpdatedTime", expression = "java(java.util.Objects.nonNull(awb.getLastUpdatedTime())?"
            + "awb.getLastUpdatedTime().toLocalDateTime():null)")
    @Mapping(target = "shipmentPrefix", expression = "java(awb.getShipmentKey().getShipmentPrefix())")
    @Mapping(target = "masterDocumentNumber", expression = "java(awb.getShipmentKey().getMasterDocumentNumber())")
    @Mapping(target = "statedVolume", expression = "java(quantityMapper.getQuantity(\"VOL\", Optional.ofNullable(awb.getStatedVolume()).orElse(0.0), awb.getEUnit().getVolume()))")
    @Mapping(target = "statedWeight", expression = "java(quantityMapper.getQuantity(\"WGT\", Optional.ofNullable(awb.getStatedWeight()).orElse(0.0), awb.getEUnit().getWeight()))")
    AwbVO constructAwbVo(Awb awb);

    @Mapping(target = "lastUpdatedTime", expression = "java(java.util.Objects.nonNull(awbVO.getLastUpdatedTime())?"
            + "java.sql.Timestamp.valueOf(awbVO.getLastUpdatedTime().now()):null)")
    @Mapping(target = "shipmentKey", expression = "java(new ShipmentKey(awbVO.getShipmentPrefix(),awbVO.getMasterDocumentNumber()))")
    @Mapping(target = "awbContactDetails", expression = "java(java.util.Objects.isNull(awbVO.getAwbContactDetailsVO())? null : java.util.Objects.nonNull(entity.getAwbContactDetails())?"
            + "mergeAwbContactDetails(awbVO.getAwbContactDetailsVO(), entity.getAwbContactDetails()) : constructContactDetails(awbVO.getAwbContactDetailsVO()))")
    Awb mergeAwb(AwbVO awbVO, @MappingTarget Awb entity);

    @Mapping(target = "lastUpdatedTime", expression = "java(java.util.Objects.nonNull(awbContactDetailsVO.getLastUpdatedTime())?"
            + "java.sql.Timestamp.valueOf(awbContactDetailsVO.getLastUpdatedTime().now()):null)")
    AWBContactDetails mergeAwbContactDetails(AwbContactDetailsVO awbContactDetailsVO, @MappingTarget AWBContactDetails entity);

    @Mapping(target = "lastUpdatedTime", expression = "java(java.util.Objects.nonNull(awbContactDetailsVO.getLastUpdatedTime())?"
            + "java.sql.Timestamp.valueOf(awbContactDetailsVO.getLastUpdatedTime().now()):null)")
    AWBContactDetails constructContactDetails(AwbContactDetailsVO awbContactDetailsVO);

    AwbUserNotificationVO constructAwbUserNotificationVO(AWBUserNotification notification);

    AWBUserNotification constructAwbUserNotification(AwbUserNotificationVO notification);
}
