package com.ibsplc.neoicargo.cca.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibsplc.neoicargo.cca.constants.CustomerType;
import com.ibsplc.neoicargo.cca.dao.entity.CCAAwb;
import com.ibsplc.neoicargo.cca.dao.entity.CCAAwbDetail;
import com.ibsplc.neoicargo.cca.dao.entity.CCAAwbPk;
import com.ibsplc.neoicargo.cca.dao.entity.CcaMaster;
import com.ibsplc.neoicargo.cca.events.CCAApprovedEvent;
import com.ibsplc.neoicargo.cca.events.CCADeleteEvent;
import com.ibsplc.neoicargo.cca.modal.CCAAWBDetailData;
import com.ibsplc.neoicargo.cca.modal.CcaCustomerDetailData;
import com.ibsplc.neoicargo.cca.modal.CcaDetailData;
import com.ibsplc.neoicargo.cca.modal.CcaTaxValueData;
import com.ibsplc.neoicargo.cca.modal.ReasonCodeModel;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaRateDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaRatingDetailVO;
import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.util.currency.mapper.MoneyMapper;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import com.ibsplc.neoicargo.pricing.modal.RateDetail;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_REVISED;
import static com.ibsplc.neoicargo.cca.util.CcaUtil.isNullOrEmpty;

@Mapper(config = CentralConfig.class,
        uses = {QuantityMapper.class, MoneyMapper.class, CcaAwbDetailMapper.class, UnitQuantityMapper.class, CcaTaxMapper.class},
        imports = {DateTimeFormatter.class, Objects.class, Collectors.class, LinkedHashSet.class, ReasonCodeModel.class, Optional.class})
public interface CcaAwbMapper {

    CcaCustomerDetailMapper CCA_CUSTOMER_DETAIL_MAPPER = Mappers.getMapper(CcaCustomerDetailMapper.class);

    @Mapping(target = "shippingDate", expression = "java(Objects.nonNull(ccaAwbVO.getShippingDate()) ? ccaAwbVO.getShippingDate().format(DateTimeFormatter.ofPattern(\"dd-MM-yyyy\")) : null)")
    @Mapping(target = "executionDate", expression = "java(Objects.nonNull(ccaAwbVO.getExecutionDate()) ? ccaAwbVO.getExecutionDate().atStartOfDay().format(DateTimeFormatter.ofPattern(\"dd-MM-yyyy HH:mm:ss\")) : null)")
    @Mapping(target = "firstFlightDate", expression = "java((Objects.nonNull(ccaAwbVO.getAwbQualityAuditDetail()) && Objects.nonNull(ccaAwbVO.getAwbQualityAuditDetail().getFirstFlightDate())) ? ccaAwbVO.getAwbQualityAuditDetail().getFirstFlightDate().format(DateTimeFormatter.ofPattern(\"dd-MM-yyyy\")) : null)")
    @Mapping(target = "chargeableWeight", expression = "java(Objects.nonNull(ccaAwbVO.getChargeableWeight()) ? ccaAwbVO.getChargeableWeight().getDisplayValue().doubleValue() : 0.0)")
    @Mapping(target = "volumetricWeight", expression = "java(Objects.nonNull(ccaAwbVO.getVolumetricWeight()) ? ccaAwbVO.getVolumetricWeight().getDisplayValue().doubleValue() : 0.0)")
    @Mapping(target = "adjustedWeight", expression = "java(Objects.nonNull(ccaAwbVO.getAdjustedWeight()) ? ccaAwbVO.getAdjustedWeight().getDisplayValue().doubleValue() : 0.0)")
    @Mapping(target = "weight", expression = "java(Objects.nonNull(ccaAwbVO.getWeight()) ? ccaAwbVO.getWeight().getDisplayValue().doubleValue() : 0.0)")
    @Mapping(target = "volume", expression = "java(Objects.nonNull(ccaAwbVO.getVolume()) ? ccaAwbVO.getVolume().getDisplayValue().doubleValue() : 0.0)")
    @Mapping(target = "inboundCustomerDetails", source = "ccaAwbVO", qualifiedByName = "inboundCustomerDetails")
    @Mapping(target = "outboundCustomerDetails", source = "ccaAwbVO", qualifiedByName = "outboundCustomerDetails")
    @Mapping(target = "displayTaxDetails", source = "ccaAwbVO.displayTaxDetails", qualifiedByName = "mapDisplayTaxDetails")
    CCAAWBDetailData ccaAwbVOToCCAAWBData(CcaAwbVO ccaAwbVO);

    @Mapping(target = "key", expression = "java(ccaRatingDetailVO.businessKey())")
    @Mapping(target = "weightOfShipment", expression = "java(java.util.Objects.nonNull(ccaRatingDetailVO.getWeightOfShipment()) ? ccaRatingDetailVO.getWeightOfShipment().getRoundedDisplayValue().doubleValue() : 0.0)")
    @Mapping(target = "volumeOfShipment", expression = "java(java.util.Objects.nonNull(ccaRatingDetailVO.getVolumeOfShipment()) ? ccaRatingDetailVO.getVolumeOfShipment().getRoundedDisplayValue().doubleValue() : 0.0)")
    @Mapping(target = "chargeableWeight", expression = "java(java.util.Objects.nonNull(ccaRatingDetailVO.getChargeableWeight()) ? ccaRatingDetailVO.getChargeableWeight().getRoundedDisplayValue().doubleValue() : 0.0)")
    @Mapping(target = "volumetricWeight", expression = "java(java.util.Objects.nonNull(ccaRatingDetailVO.getVolumetricWeight()) ? ccaRatingDetailVO.getVolumetricWeight().getRoundedDisplayValue().doubleValue() : 0.0)")
    @Mapping(target = "adjustedWeight", expression = "java(java.util.Objects.nonNull(ccaRatingDetailVO.getAdjustedWeight()) ? ccaRatingDetailVO.getAdjustedWeight().getRoundedDisplayValue().doubleValue() : 0.0)")
    @Mapping(target = "displayWeightUnit", expression = "java(java.util.Objects.nonNull(ccaRatingDetailVO.getWeightOfShipment()) ? ccaRatingDetailVO.getWeightOfShipment().getDisplayUnit().getSymbol() : null)")
    @Mapping(target = "displayVolumeUnit", expression = "java(java.util.Objects.nonNull(ccaRatingDetailVO.getVolumeOfShipment()) ? ccaRatingDetailVO.getVolumeOfShipment().getDisplayUnit().getSymbol(): null)")
    CcaDetailData constructCcaDetailData(CcaRatingDetailVO ccaRatingDetailVO);

    @Mapping(target = "chargeableWeight", source = "chargeableWeight", qualifiedByName = "getValue")
    @Mapping(target = "displayChargeableWeight", source = "chargeableWeight", qualifiedByName = "getDisplayValue")
    @Mapping(target = "volumetricWeight", source = "volumetricWeight", qualifiedByName = "getValue")
    @Mapping(target = "displayVolumetricWeight", source = "volumetricWeight", qualifiedByName = "getDisplayValue")
    @Mapping(target = "adjustedWeight", source = "adjustedWeight", qualifiedByName = "getValue")
    @Mapping(target = "displayAdjustedWeight", source = "adjustedWeight", qualifiedByName = "getDisplayValue")
    @Mapping(target = "weight", source = "weight", qualifiedByName = "getValue")
    @Mapping(target = "displayWeight", source = "weight", qualifiedByName = "getDisplayValue")
    @Mapping(target = "volume", source = "volume", qualifiedByName = "getValue")
    @Mapping(target = "displayVolume", source = "volume", qualifiedByName = "getDisplayValue")
    @Mapping(target = "unitOfQuantity", source = "unitOfMeasure")
    @Mapping(target = "awbDetails", source = "ratingDetails")
    @Mapping(target = "lastUpdatedTime", expression = "java(java.util.Objects.nonNull(ccaAwbVO.getLastUpdatedTime()) ? java.sql.Timestamp.valueOf(ccaAwbVO.getLastUpdatedTime()) : null)")
    @Mapping(target = "totalTaxAmount", source = "taxAmount")
    @Mapping(target = "netValueExport", expression = "java(java.util.Objects.nonNull(ccaAwbVO.getNetValueExport()) ? ccaAwbVO.getNetValueExport().getAmount().doubleValue() : null)")
    @Mapping(target = "netValueImport", expression = "java(java.util.Objects.nonNull(ccaAwbVO.getNetValueImport()) ? ccaAwbVO.getNetValueImport().getAmount().doubleValue() : null)")
    CCAAwb constructCcaAwb(CcaAwbVO ccaAwbVO);

    @Mapping(target = "weightOfShipment", source = "weightOfShipment", qualifiedByName = "getValue")
    @Mapping(target = "displayWeightOfShipment", source = "weightOfShipment", qualifiedByName = "getDisplayValue")
    @Mapping(target = "volumeOfShipment", source = "volumeOfShipment", qualifiedByName = "getValue")
    @Mapping(target = "displayVolumeOfShipment", source = "volumeOfShipment", qualifiedByName = "getDisplayValue")
    @Mapping(target = "chargeableWeight", source = "chargeableWeight", qualifiedByName = "getValue")
    @Mapping(target = "displayChargeableWeight", source = "chargeableWeight", qualifiedByName = "getDisplayValue")
    @Mapping(target = "volumetricWeight", source = "volumetricWeight", qualifiedByName = "getValue")
    @Mapping(target = "displayVolumetricWeight", source = "volumetricWeight", qualifiedByName = "getDisplayValue")
    @Mapping(target = "adjustedWeight", source = "adjustedWeight", qualifiedByName = "getValue")
    @Mapping(target = "displayAdjustedWeight", source = "adjustedWeight", qualifiedByName = "getDisplayValue")
    @Mapping(target = "unitOfQuantity", source = "unitOfMeasure")
    @Mapping(target = "commodityDescription", source = "shipmentDescription")
    @Mapping(target = "dimensions", source = "dimensions")
    CCAAwbDetail constructAwbDetail(CcaRatingDetailVO ratingDetailVO);

    @Mapping(target = "chargeableWeight", expression = "java(Objects.nonNull(rateDetail.getRatedChargeableWeight()) ? rateDetail.getRatedChargeableWeight().doubleValue() : 0.0)")
    @Mapping(target = "displayChargeableWeight", expression = "java(Objects.nonNull(rateDetail.getRatedChargeableWeight()) ? rateDetail.getRatedChargeableWeight().doubleValue() : 0.0)")
    @Mapping(target = "charge", source = "rateDetail.charge")
    @Mapping(target = "rate", source = "rateDetail.rate")
    @Mapping(target = "rateDetails", source = "rateDetail.rateLineDetail")
    @Mapping(target = "allInAttribute", expression = "java(java.util.Objects.nonNull(rateDetail.getRateLineDetail().getAllInAttributes()) ? String.join(\",\",rateDetail.getRateLineDetail().getAllInAttributes()) : \"\")")
    @Mapping(target = "minimumChargeAppliedFlag", source = "rateDetail.minimumChargeApplied")
    @BeanMapping(ignoreByDefault = true)
    CcaRateDetailsVO constructRatesFromPricing(RateDetail rateDetail, @Context Units units);

    @Named("inboundCustomerDetails")
    default CcaCustomerDetailData getInboundCustomerDetails(CcaAwbVO ccaAwbVO) {
        return Optional.ofNullable(ccaAwbVO.getCcaCustomerDetails())
                .stream()
                .flatMap(Collection::stream)
                .filter(details -> CustomerType.I.equals(details.getCustomerType()))
                .map(CCA_CUSTOMER_DETAIL_MAPPER::constructCcaCustomerDetailData)
                .findFirst()
                .orElse(null);
    }

    @Named("outboundCustomerDetails")
    default CcaCustomerDetailData getOutboundCustomerDetails(CcaAwbVO ccaAwbVO) {
        return Optional.ofNullable(ccaAwbVO.getCcaCustomerDetails())
                .stream()
                .flatMap(Collection::stream)
                .filter(details -> CustomerType.O.equals(details.getCustomerType()))
                .map(CCA_CUSTOMER_DETAIL_MAPPER::constructCcaCustomerDetailData)
                .findFirst()
                .orElse(null);
    }

    @NotNull
    @SuppressWarnings("unchecked")
    @Named("mapDisplayTaxDetails")
    default List<CcaTaxValueData> mapDisplayTaxDetails(Object taxDetails) {
        try {
            var mapper = new ObjectMapper();
            LinkedHashMap<String, String> taxDetailsAsMap = mapper.convertValue(taxDetails, LinkedHashMap.class);
            return isNullOrEmpty(taxDetailsAsMap)
                    ? new ArrayList<>()
                    : taxDetailsAsMap.entrySet()
                    .stream()
                    .map(entry -> CcaTaxValueData.builder()
                            .taxName(entry.getKey())
                            .taxValue(String.valueOf(entry.getValue()))
                            .build())
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            LoggerFactory.getLogger(CcaAwbMapper.class).warn("Cannot process Display Tax Details", e);
            return new ArrayList<>();
        }
    }

    @AfterMapping
    default CCAAwb constructCcaAwb(CcaAwbVO shipmentDetailVO, @MappingTarget CCAAwb cCAAwb) {
        final var ccaAwbPk = new CCAAwbPk();
        ccaAwbPk.setRecordType(shipmentDetailVO.getRecordType());
        cCAAwb.setCcaAwbPk(ccaAwbPk);
        if (cCAAwb.getAwbDetails() != null) {
            cCAAwb.getAwbDetails().forEach(awbDetail -> {
                awbDetail.setCcaawb(cCAAwb);
                if (awbDetail.getDimensions() != null) {
                    awbDetail.getDimensions().forEach(dimension -> dimension.setCcaAwbDetail(awbDetail));
                }
                if (awbDetail.getUlds() != null) {
                    awbDetail.getUlds().forEach(uld -> uld.setCcaAwbDetail(awbDetail));
                }
            });
        }
        if (cCAAwb.getAwbRates() != null) {
            cCAAwb.getAwbRates().forEach(awbRateDetail -> awbRateDetail.setCcaawb(cCAAwb));
        }

        cCAAwb.getAwbCharges().forEach(awbChargeDetail -> awbChargeDetail.setCcaawb(cCAAwb));

        if (cCAAwb.getCcaAwbRoutingDetails() != null) {
            cCAAwb.getCcaAwbRoutingDetails().forEach(ccaAwbRoutingDetails -> ccaAwbRoutingDetails.setCcaawb(cCAAwb));
        }

        if (cCAAwb.getCcaCustomerDetails() != null) {
            cCAAwb.getCcaCustomerDetails().forEach(ccaCustomerDetail -> ccaCustomerDetail.setCcaawb(cCAAwb));
        }

        if (cCAAwb.getAwbTaxes() != null) {
            cCAAwb.getAwbTaxes().forEach(ccaAwbTaxDetails -> {
                ccaAwbTaxDetails.setCcaawb(cCAAwb);
                ccaAwbTaxDetails.setCcaMaster(cCAAwb.getCcaMaster());
                ccaAwbTaxDetails.setRecordType(cCAAwb.getCcaAwbPk().getRecordType());
            });
        }

        return cCAAwb;
    }

    CcaAwbVO constructCcaAwbVO(CCAAwb ccaAwb, @Context Units units);

    @AfterMapping
    default void updateAwbForCcaDeleteEvent(CcaMaster ccaMaster, @MappingTarget CCADeleteEvent ccaDeleteEvent) {
        ccaMaster.getCcaAwb().forEach(awb -> {
            final var units = new Units();
            units.weight(awb.getUnitOfQuantity().getWeight());
            units.volume(awb.getUnitOfQuantity().getVolume());
            units.setCurrencyCode(awb.getCurrency());
            awb.getAwbDetails().stream()
                    .findAny()
                    .ifPresent(ccaAwbDetail -> {
                        final var dimensions = ccaAwbDetail.getDimensions();
                        if (dimensions != null) {
                            dimensions.stream()
                                    .findAny()
                                    .ifPresent(dim -> units.setLength(dim.getUnitOfMeasure().getLength()));
                        }
                    });
            ccaDeleteEvent.setUnitOfMeasure(units);
            final var ccaawbDetailData = ccaAwbVOToCCAAWBData(constructCcaAwbVO(awb, units));
            if (CCA_RECORD_TYPE_REVISED.equals(awb.getCcaAwbPk().getRecordType())) {
                ccaDeleteEvent.setRevisedAWBData(ccaawbDetailData);
            } else {
                ccaDeleteEvent.setOriginalAWBData(ccaawbDetailData);
            }
        });
    }

    @AfterMapping
    default void updateAwbForCCAApprovedEvent(CcaMaster ccaMaster, @MappingTarget CCAApprovedEvent ccaApprovedEvent) {
        ccaMaster.getCcaAwb().forEach(awb -> {
            final var units = new Units();
            units.weight(awb.getUnitOfQuantity().getWeight());
            units.volume(awb.getUnitOfQuantity().getVolume());
            units.setCurrencyCode(awb.getCurrency());
            awb.getAwbDetails().stream()
                    .findAny()
                    .ifPresent(ccaAwbDetail -> {
                        final var dimensions = ccaAwbDetail.getDimensions();
                        if (dimensions != null) {
                            dimensions.stream()
                                    .findAny()
                                    .ifPresent(dim -> units.setLength(dim.getUnitOfMeasure().getLength()));
                        }
                    });
            ccaApprovedEvent.setUnitOfMeasure(units);
            final var ccaawbDetailData = ccaAwbVOToCCAAWBData(constructCcaAwbVO(awb, units));
            if (CCA_RECORD_TYPE_REVISED.equals(awb.getCcaAwbPk().getRecordType())) {
                ccaApprovedEvent.setRevisedAWBData(ccaawbDetailData);
            } else {
                ccaApprovedEvent.setOriginalAWBData(ccaawbDetailData);
            }
        });
    }
}