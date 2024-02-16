package com.ibsplc.neoicargo.cca.mapper;

import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaRateDetailsVO;
import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.util.currency.mapper.MoneyMapper;
import com.ibsplc.neoicargo.pricing.modal.CalculateSpotRateResponse;
import com.ibsplc.neoicargo.pricing.modal.SpotRateRequest;
import com.ibsplc.neoicargo.pricing.modal.SpotRateShipmentData;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Arrays;
import java.util.List;

@Mapper(config = CentralConfig.class, uses = {MoneyMapper.class})
public interface CcaRateDetailsMapper {

    @Mapping(target = "charge", source = "response.charge")
    @Mapping(target = "rate", source = "response.spotValue")
    @BeanMapping(ignoreByDefault = true)
    CcaRateDetailsVO updateSpotRateFromPricing(CalculateSpotRateResponse response, @MappingTarget CcaRateDetailsVO awbRateDetailsVO, @Context Units units);

    @Mapping(target = "displayUnits", source = "shipmentDetailVO.unitOfMeasure")
    @Mapping(target = "chargeableWeight", expression = "java(java.util.Objects.nonNull(shipmentDetailVO.getChargeableWeight()) ? shipmentDetailVO.getChargeableWeight().getRoundedDisplayValue().doubleValue() : 0d)")
    @Mapping(target = "shipmentChargeableWeight", expression = "java(java.util.Objects.nonNull(shipmentDetailVO.getChargeableWeight()) ? shipmentDetailVO.getChargeableWeight().getRoundedDisplayValue().doubleValue() : 0d)")
    @Mapping(target = "spotRateShipmentDataList", expression = "java(insertSpotRateShipmentDataFromShipmentDetailVO(shipmentDetailVO))")
    SpotRateRequest constructSpotRateRequest(CcaAwbVO shipmentDetailVO);

    default List<SpotRateShipmentData> insertSpotRateShipmentDataFromShipmentDetailVO(CcaAwbVO shipmentDetailVO) {
        return Arrays.asList(constructSpotRateShipmentData(shipmentDetailVO));
    }

    @Mapping(target = "spotRateAppliedFlag", expression = "java(\"N\")")
    @Mapping(target = "chargeableWeight", expression = "java(java.util.Objects.nonNull(shipmentDetailVO.getChargeableWeight()) ? shipmentDetailVO.getChargeableWeight().getRoundedDisplayValue().doubleValue() : 0d)")
    @Mapping(target = "shipmentStatus", ignore = true)
    SpotRateShipmentData constructSpotRateShipmentData(CcaAwbVO shipmentDetailVO);

}