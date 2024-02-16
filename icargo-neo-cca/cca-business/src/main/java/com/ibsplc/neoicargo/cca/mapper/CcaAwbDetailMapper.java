package com.ibsplc.neoicargo.cca.mapper;

import com.ibsplc.neoicargo.cca.dao.entity.CCAAwbChargeDetail;
import com.ibsplc.neoicargo.cca.dao.entity.CCAAwbRateDetail;
import com.ibsplc.neoicargo.cca.dao.entity.CcaAwbDetailDimensions;
import com.ibsplc.neoicargo.cca.dao.entity.CcaAwbRoutingDetails;
import com.ibsplc.neoicargo.cca.dao.entity.CcaAwbUldDetail;
import com.ibsplc.neoicargo.cca.modal.CcaChargeDetailData;
import com.ibsplc.neoicargo.cca.modal.CcaDimension;
import com.ibsplc.neoicargo.cca.modal.CcaRateDetailData;
import com.ibsplc.neoicargo.cca.modal.CcaRatingUldModal;
import com.ibsplc.neoicargo.cca.modal.CcaRoutingData;
import com.ibsplc.neoicargo.cca.vo.CcaAwbRoutingDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaChargeDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaRateDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaRatingUldVO;
import com.ibsplc.neoicargo.cca.vo.CcaRoutingVO;
import com.ibsplc.neoicargo.cca.vo.DimensionsVO;
import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.util.currency.mapper.MoneyMapper;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import com.ibsplc.neoicargo.pricing.modal.Flight;
import com.ibsplc.neoicargo.pricing.modal.OtherChargeDetail;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(config = CentralConfig.class,
        uses = {QuantityMapper.class, MoneyMapper.class, UnitQuantityMapper.class})
public interface CcaAwbDetailMapper {

    @Mapping(target = "weight", expression = "java(ccaAwbRoutingDetailsVO.getWeight() != null " +
            "? ccaAwbRoutingDetailsVO.getWeight().getRoundedDisplayValue().doubleValue() : 0.0)")
    CcaRoutingData constructCcaAwbRoutingDetailData(CcaAwbRoutingDetailsVO ccaAwbRoutingDetailsVO);

    @Mapping(target = "weight", source = "weight", qualifiedByName = "getValue")
    @Mapping(target = "displayWeight", source = "weight", qualifiedByName = "getDisplayValue")
    @Mapping(target = "displayWeightUnit", source = "weight", qualifiedByName = "getDisplayUnit")
    @Mapping(target = "serialNumber", ignore = true)
    CcaAwbRoutingDetails constructCcaAwbRoutingDetails(CcaAwbRoutingDetailsVO ccaAwbRoutingDetailsVO);

    Set<CcaAwbRoutingDetails> constructCcaAwbRoutingDetails(Set<CcaAwbRoutingDetailsVO> ccaAwbRoutingDetailsVOS);

    List<Flight> fromRoutingDetailsToFlight(List<CcaRoutingVO> bookingRoute);

    @Mapping(target = "carrierCode", source = "flightCarrierCode")
    @Mapping(target = "departureAirport", source = "segOrgCod")
    @Mapping(target = "arrivalAirport", source = "segDstCod")
    @Mapping(target = "departureDate", source = "flightDate")
    @Mapping(target = "aircraftType", source = "flightType")
    @Mapping(target = "weight", expression = "java(java.util.Objects.nonNull(routingDetails.getWeight()) " +
            "? Double.valueOf(routingDetails.getWeight().getDisplayValue().toString()) : 0.0)")
    @Mapping(target = "volume", expression = "java(java.util.Objects.nonNull(routingDetails.getVolume()) " +
            "? Double.valueOf(routingDetails.getVolume().getRoundedDisplayValue().toString()) : 0.0)")
    Flight iterateRoutingDetailVO(CcaRoutingVO routingDetails);

    List<CcaRoutingVO> constructBookingRoutes(List<com.ibsplc.neoicargo.booking.modal.Flight> flights);

    @Mapping(target = "flightCarrierCode", source = "flightCarrier")
    @Mapping(target = "flightDate", source = "departureDate")
    @Mapping(target = "flightNumber", source = "flightNumber")
    @Mapping(target = "pieces", source = "pieces")
    @Mapping(target = "wgt", source = "weight")
    @Mapping(target = "vol", source = "volume")
    @Mapping(target = "segOrgCod", source = "departureAirport")
    @Mapping(target = "segDstCod", source = "arrivalAirport")
    @Mapping(target = "flightType", expression = "java(\"Y\".equals(flights.getIsTruckFlight())?\"Y\":null)")
    @BeanMapping(ignoreByDefault = true)
    CcaRoutingVO iterateBookingRoutesVO(com.ibsplc.neoicargo.booking.modal.Flight flights);

    @Mapping(target = "netCharge", source = "charge")
    CcaRateDetailData constructCcaRateDetailData(CcaRateDetailsVO ccaRateDetailsVO);

    @Mapping(target = "charge", source = "netCharge")
    CcaRateDetailsVO constructCcaRateDetailVO(CcaRateDetailData ccaRateDetailsVO, @Context Units units);

    @Mapping(target = "update", ignore = true)
    CCAAwbRateDetail constructAwbRate(CcaRateDetailsVO awbRateDetailsVO);

    @Mapping(target = "key", expression = "java(ccaChargeDetailsVO.businessKey())")
    CcaChargeDetailData constructCcaChargeDetailData(CcaChargeDetailsVO ccaChargeDetailsVO);

    @Mapping(target = "serialNumber", ignore = true)
    @Mapping(target = "dueCarrier", expression = "java(awbChargeDetailsVO.isDueCarrier() ? \"Y\" : \"N\")")
    @Mapping(target = "dueAgent", expression = "java(awbChargeDetailsVO.isDueAgent() ? \"Y\" : \"N\")")
    CCAAwbChargeDetail constructAwbChargeDetail(CcaChargeDetailsVO awbChargeDetailsVO);

    @Mapping(target = "chargeHeadCode", source = "chargeHeadCode")
    @Mapping(target = "chargeHead", source = "chargeHeadName")
    @Mapping(target = "charge", source = "otherCharge.charge")
    @Mapping(target = "dueCarrier", expression = "java(\"DC\".equals(otherCharge.getChargeType())?true:false)")
    @Mapping(target = "dueAgent", expression = "java(\"DA\".equals(otherCharge.getChargeType())?true:false)")
    @BeanMapping(ignoreByDefault = true)
    CcaChargeDetailsVO constructChargeDetailsFromPricing(OtherChargeDetail otherCharge, @Context Units units);


    @Mapping(target = "weight", source = "weight", qualifiedByName = "getValue")
    @Mapping(target = "displayWeight", source = "weight", qualifiedByName = "getDisplayValue")
    @Mapping(target = "volume", source = "volume", qualifiedByName = "getValue")
    @Mapping(target = "displayVolume", source = "volume", qualifiedByName = "getDisplayValue")
    @Mapping(target = "length", source = "length", qualifiedByName = "getValue")
    @Mapping(target = "displayLength", source = "length", qualifiedByName = "getDisplayValue")
    @Mapping(target = "width", source = "width", qualifiedByName = "getValue")
    @Mapping(target = "displayWidth", source = "width", qualifiedByName = "getDisplayValue")
    @Mapping(target = "height", source = "height", qualifiedByName = "getValue")
    @Mapping(target = "displayHeight", source = "height", qualifiedByName = "getDisplayValue")
    @Mapping(target = "serialNumber", ignore = true)
    CcaAwbDetailDimensions constructCCAAwbDetailDimensions(DimensionsVO dimensionsVO);

    Set<CcaAwbDetailDimensions> constructCCAAwbDetailDimensions(Set<DimensionsVO> dimensionsVO);

    @Mapping(target = "weight", expression = "java(dimensionsVO.getWeight() != null " +
            "? dimensionsVO.getWeight().getRoundedDisplayValue().doubleValue() : 0.0)")
    @Mapping(target = "length", expression = "java(dimensionsVO.getLength() != null " +
            "? dimensionsVO.getLength().getRoundedDisplayValue().doubleValue() : 0.0)")
    @Mapping(target = "width", expression = "java(dimensionsVO.getWidth() != null " +
            "? dimensionsVO.getWidth().getRoundedDisplayValue().doubleValue() : 0.0)")
    @Mapping(target = "height", expression = "java(dimensionsVO.getHeight() != null " +
            "? dimensionsVO.getHeight().getRoundedDisplayValue().doubleValue() : 0.0)")
    @Mapping(target = "volume", expression = "java(dimensionsVO.getVolume() != null " +
            "? dimensionsVO.getVolume().getRoundedDisplayValue().doubleValue() : 0.0)")
    CcaDimension constructCCAAwbDetailDimensionsData(DimensionsVO dimensionsVO);

    @Mapping(target = "weight", source = "weight", qualifiedByName = "getValue")
    @Mapping(target = "displayWeight", source = "weight", qualifiedByName = "getDisplayValue")
    @Mapping(target = "volume", source = "volume", qualifiedByName = "getValue")
    @Mapping(target = "displayVolume", source = "volume", qualifiedByName = "getDisplayValue")
    @Mapping(target = "serialNumber", ignore = true)
    CcaAwbUldDetail constructCcaAwbDetailUld(CcaRatingUldVO uldVO);

    Set<CcaAwbUldDetail> constructCcaAwbUldDetails(Set<CcaRatingUldVO> uldVO);

    @Mapping(target = "weight", expression = "java(uldVO.getWeight() != null ? uldVO.getWeight().getRoundedDisplayValue().doubleValue() : 0.0)")
    @Mapping(target = "volume", expression = "java(uldVO.getVolume() != null ? uldVO.getVolume().getRoundedDisplayValue().doubleValue() : 0.0)")
    CcaRatingUldModal constructCcaRatingUldModal(CcaRatingUldVO uldVO);
}
