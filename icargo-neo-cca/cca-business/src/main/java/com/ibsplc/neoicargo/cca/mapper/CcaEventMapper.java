package com.ibsplc.neoicargo.cca.mapper;

import com.ibsplc.neoicargo.cca.constants.CustomerType;
import com.ibsplc.neoicargo.cca.dao.entity.CcaMaster;
import com.ibsplc.neoicargo.cca.events.CCAApprovedEvent;
import com.ibsplc.neoicargo.cca.events.CCACreateEvent;
import com.ibsplc.neoicargo.cca.events.CCADeleteEvent;
import com.ibsplc.neoicargo.cca.events.CCAUpdateEvent;
import com.ibsplc.neoicargo.cca.events.CcaInvoicedEvent;
import com.ibsplc.neoicargo.cca.modal.CCAAWBDetailData;
import com.ibsplc.neoicargo.cca.modal.CcaCustomerDetailData;
import com.ibsplc.neoicargo.cca.modal.ReasonCodeModel;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaCustomerDetailVO;
import com.ibsplc.neoicargo.cca.vo.filter.CCAFilterVO;
import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.util.currency.mapper.MoneyMapper;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = CentralConfig.class,
        imports = {
                StringUtils.class, Arrays.class, Optional.class, ReasonCodeModel.class, Collectors.class
        },
        uses = {
                QuantityMapper.class, MoneyMapper.class, CcaAwbMapper.class
        })
public interface CcaEventMapper {

    CCAFilterVO constructCCAFilterVO(CcaInvoicedEvent ccaInvoicedEvent);

    @Mapping(target = "ccaIssueDate", source = "ccaIssueDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "billingPeriodTo", source = "billingPeriodTo", dateFormat = "dd-MMM-yyyy")
    @Mapping(target = "billingPeriodFrom", source = "billingPeriodFrom", dateFormat = "dd-MMM-yyyy")
    @Mapping(target = "originalAWBData", source = "originalShipmentVO")
    @Mapping(target = "revisedAWBData", source = "revisedShipmentVO")
    @Mapping(target = "originalAWBData.displayTaxDetails", ignore = true)
    @Mapping(target = "revisedAWBData.displayTaxDetails", ignore = true)
    @Mapping(target = "originalAWBData.shippingDate", source = "originalShipmentVO.shippingDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "revisedAWBData.shippingDate", source = "revisedShipmentVO.shippingDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "originalAWBData.executionDate", source = "originalShipmentVO.executionDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "revisedAWBData.executionDate", source = "revisedShipmentVO.executionDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "originalAWBData.payType", expression = "java((StringUtils.isNotBlank(ccaAwbVO.getPayType()) && StringUtils.isNotBlank(ccaAwbVO.getAwbOtherChargePaymentType())) " +
            "? ccaAwbVO.getPayType().substring(0, 1).concat(ccaAwbVO.getAwbOtherChargePaymentType().substring(0, 1)) " +
            ": null)")
    @Mapping(target = "revisedAWBData.payType", expression = "java((StringUtils.isNotBlank(ccaAwbVO.getPayType()) && StringUtils.isNotBlank(ccaAwbVO.getAwbOtherChargePaymentType())) " +
            "? ccaAwbVO.getPayType().substring(0, 1).concat(ccaAwbVO.getAwbOtherChargePaymentType().substring(0, 1)) " +
            ": null)")
    @Mapping(target = "originalAWBData.inboundCustomerDetails", expression="java(ccaAwbVO.getCcaCustomerDetails() !=null ? ccaCustomerDetailVOToCcaCustomerDetailData(getCustomerDetails(ccaAwbVO.getCcaCustomerDetails(),com.ibsplc.neoicargo.cca.constants.CustomerType.I)) :null )" )
    @Mapping(target = "originalAWBData.outboundCustomerDetails", expression="java(ccaAwbVO.getCcaCustomerDetails() !=null ? ccaCustomerDetailVOToCcaCustomerDetailData(getCustomerDetails(ccaAwbVO.getCcaCustomerDetails(),com.ibsplc.neoicargo.cca.constants.CustomerType.O)) :null )" )
    @Mapping(target = "revisedAWBData.inboundCustomerDetails", expression="java(ccaAwbVO.getCcaCustomerDetails() !=null ? ccaCustomerDetailVOToCcaCustomerDetailData(getCustomerDetails(ccaAwbVO.getCcaCustomerDetails(),com.ibsplc.neoicargo.cca.constants.CustomerType.I)) :null )" )
    @Mapping(target = "revisedAWBData.outboundCustomerDetails", expression="java(ccaAwbVO.getCcaCustomerDetails() !=null ? ccaCustomerDetailVOToCcaCustomerDetailData(getCustomerDetails(ccaAwbVO.getCcaCustomerDetails(),com.ibsplc.neoicargo.cca.constants.CustomerType.O)) :null )" )
    @Mapping(target = "revisedAWBData.totalNonAWBCharge", source = "revisedShipmentVO.awbQualityAuditDetail.totalNonAWBCharge")
    @Mapping(target = "originalAWBData.totalNonAWBCharge", source = "originalShipmentVO.awbQualityAuditDetail.totalNonAWBCharge")
    @Mapping(target = "documentOwnerId", ignore = true)
    @Mapping(target = "reasonCodes", expression = "java(Arrays.stream(Optional.ofNullable(ccaMasterVO.getCcaReason()).orElse(\"\").split(\",\")).map(ReasonCodeModel::new).collect(Collectors.toList()))")
    @Mapping(target = "outboundBillingStatus", source = "exportBillingStatus")
    @Mapping(target = "inboundBillingStatus", source = "importBillingStatus")
    @Mapping(target = "unitOfMeasure", expression = "java(Optional.ofNullable(ccaMasterVO.getOriginalShipmentVO()).map(CcaAwbVO::getUnitOfMeasure).orElse(null))")
    CCACreateEvent constructCCACreateEventFromCCAMasterVO(CCAMasterVO ccaMasterVO, @Context Units units);

    @Mapping(target = "ccaIssueDate", source = "ccaIssueDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "billingPeriodTo", source = "billingPeriodTo", dateFormat = "dd-MMM-yyyy")
    @Mapping(target = "billingPeriodFrom", source = "billingPeriodFrom", dateFormat = "dd-MMM-yyyy")
    @Mapping(target = "originalAWBData", source = "originalShipmentVO")
    @Mapping(target = "revisedAWBData", source = "revisedShipmentVO")
    @Mapping(target = "originalAWBData.displayTaxDetails", ignore = true)
    @Mapping(target = "revisedAWBData.displayTaxDetails", ignore = true)
    @Mapping(target = "originalAWBData.shippingDate", source = "originalShipmentVO.shippingDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "revisedAWBData.shippingDate", source = "revisedShipmentVO.shippingDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "originalAWBData.executionDate", source = "originalShipmentVO.executionDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "revisedAWBData.executionDate", source = "revisedShipmentVO.executionDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "originalAWBData.payType", expression = "java((StringUtils.isNotBlank(ccaAwbVO.getPayType()) && StringUtils.isNotBlank(ccaAwbVO.getAwbOtherChargePaymentType())) " +
            "? ccaAwbVO.getPayType().substring(0, 1).concat(ccaAwbVO.getAwbOtherChargePaymentType().substring(0, 1)) " +
            ": null)")
    @Mapping(target = "revisedAWBData.payType", expression = "java((StringUtils.isNotBlank(ccaAwbVO.getPayType()) && StringUtils.isNotBlank(ccaAwbVO.getAwbOtherChargePaymentType())) " +
            "? ccaAwbVO.getPayType().substring(0, 1).concat(ccaAwbVO.getAwbOtherChargePaymentType().substring(0, 1)) " +
            ": null)")
    @Mapping(target = "originalAWBData.inboundCustomerDetails", expression="java(ccaAwbVO.getCcaCustomerDetails() !=null ? ccaCustomerDetailVOToCcaCustomerDetailData(getCustomerDetails(ccaAwbVO.getCcaCustomerDetails(),com.ibsplc.neoicargo.cca.constants.CustomerType.I)) :null )" )
    @Mapping(target = "originalAWBData.outboundCustomerDetails", expression="java(ccaAwbVO.getCcaCustomerDetails() !=null ? ccaCustomerDetailVOToCcaCustomerDetailData(getCustomerDetails(ccaAwbVO.getCcaCustomerDetails(),com.ibsplc.neoicargo.cca.constants.CustomerType.O)) :null )" )
    @Mapping(target = "revisedAWBData.inboundCustomerDetails", expression="java(ccaAwbVO.getCcaCustomerDetails() !=null ? ccaCustomerDetailVOToCcaCustomerDetailData(getCustomerDetails(ccaAwbVO.getCcaCustomerDetails(),com.ibsplc.neoicargo.cca.constants.CustomerType.I)) :null )" )
    @Mapping(target = "revisedAWBData.outboundCustomerDetails", expression="java(ccaAwbVO.getCcaCustomerDetails() !=null ? ccaCustomerDetailVOToCcaCustomerDetailData(getCustomerDetails(ccaAwbVO.getCcaCustomerDetails(),com.ibsplc.neoicargo.cca.constants.CustomerType.O)) :null )" )
    @Mapping(target = "revisedAWBData.totalNonAWBCharge", source = "revisedShipmentVO.awbQualityAuditDetail.totalNonAWBCharge")
    @Mapping(target = "originalAWBData.totalNonAWBCharge", source = "originalShipmentVO.awbQualityAuditDetail.totalNonAWBCharge")
    @Mapping(target = "documentOwnerId", ignore = true)
    @Mapping(target = "reasonCodes", expression = "java(Arrays.stream(Optional.ofNullable(ccaMasterVO.getCcaReason()).orElse(\"\").split(\",\")).map(ReasonCodeModel::new).collect(Collectors.toList()))")
    @Mapping(target = "outboundBillingStatus", source = "exportBillingStatus")
    @Mapping(target = "inboundBillingStatus", source = "importBillingStatus")
    @Mapping(target = "unitOfMeasure", expression = "java(Optional.ofNullable(ccaMasterVO.getOriginalShipmentVO()).map(CcaAwbVO::getUnitOfMeasure).orElse(null))")
    CCAUpdateEvent constructCCAUpdateEventFromCCAMasterVO(CCAMasterVO ccaMasterVO, @Context Units units);

    @Mapping(target = "ccaIssueDate", source = "ccaIssueDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "billingPeriodTo", source = "billingPeriodTo", dateFormat = "dd-MMM-yyyy")
    @Mapping(target = "billingPeriodFrom", source = "billingPeriodFrom", dateFormat = "dd-MMM-yyyy")
    @Mapping(target = "originalAWBData", source = "originalShipmentVO")
    @Mapping(target = "revisedAWBData", source = "revisedShipmentVO")
    @Mapping(target = "originalAWBData.displayTaxDetails", ignore = true)
    @Mapping(target = "revisedAWBData.displayTaxDetails", source = "revisedShipmentVO.displayTaxDetails", qualifiedByName = "mapDisplayTaxDetails")
    @Mapping(target = "originalAWBData.shippingDate", source = "originalShipmentVO.shippingDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "revisedAWBData.shippingDate", source = "revisedShipmentVO.shippingDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "originalAWBData.executionDate", source = "originalShipmentVO.executionDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "revisedAWBData.executionDate", source = "revisedShipmentVO.executionDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "originalAWBData.payType", expression = "java((StringUtils.isNotBlank(ccaAwbVO.getPayType()) && StringUtils.isNotBlank(ccaAwbVO.getAwbOtherChargePaymentType())) " +
            "? ccaAwbVO.getPayType().substring(0, 1).concat(ccaAwbVO.getAwbOtherChargePaymentType().substring(0, 1)) " +
            ": null)")
    @Mapping(target = "revisedAWBData.payType", expression = "java((StringUtils.isNotBlank(ccaAwbVO.getPayType()) && StringUtils.isNotBlank(ccaAwbVO.getAwbOtherChargePaymentType())) " +
            "? ccaAwbVO.getPayType().substring(0, 1).concat(ccaAwbVO.getAwbOtherChargePaymentType().substring(0, 1)) " +
            ": null)")
    @Mapping(target = "originalAWBData.inboundCustomerDetails", expression="java(ccaAwbVO.getCcaCustomerDetails() !=null ? ccaCustomerDetailVOToCcaCustomerDetailData(getCustomerDetails(ccaAwbVO.getCcaCustomerDetails(),com.ibsplc.neoicargo.cca.constants.CustomerType.I)) :null )" )
    @Mapping(target = "originalAWBData.outboundCustomerDetails", expression="java(ccaAwbVO.getCcaCustomerDetails() !=null ? ccaCustomerDetailVOToCcaCustomerDetailData(getCustomerDetails(ccaAwbVO.getCcaCustomerDetails(),com.ibsplc.neoicargo.cca.constants.CustomerType.O)) :null )" )
    @Mapping(target = "revisedAWBData.inboundCustomerDetails", expression="java(ccaAwbVO.getCcaCustomerDetails() !=null ? ccaCustomerDetailVOToCcaCustomerDetailData(getCustomerDetails(ccaAwbVO.getCcaCustomerDetails(),com.ibsplc.neoicargo.cca.constants.CustomerType.I)) :null )" )
    @Mapping(target = "revisedAWBData.outboundCustomerDetails", expression="java(ccaAwbVO.getCcaCustomerDetails() !=null ? ccaCustomerDetailVOToCcaCustomerDetailData(getCustomerDetails(ccaAwbVO.getCcaCustomerDetails(),com.ibsplc.neoicargo.cca.constants.CustomerType.O)) :null )" )
    @Mapping(target = "revisedAWBData.totalNonAWBCharge", source = "revisedShipmentVO.awbQualityAuditDetail.totalNonAWBCharge")
    @Mapping(target = "originalAWBData.totalNonAWBCharge", source = "originalShipmentVO.awbQualityAuditDetail.totalNonAWBCharge")
    @Mapping(target = "documentOwnerId", ignore = true)
    @Mapping(target = "reasonCodes", expression = "java(Arrays.stream(Optional.ofNullable(ccaMasterVO.getCcaReason()).orElse(\"\").split(\",\")).map(ReasonCodeModel::new).collect(Collectors.toList()))")
    @Mapping(target = "outboundBillingStatus", source = "exportBillingStatus")
    @Mapping(target = "inboundBillingStatus", source = "importBillingStatus")
    @Mapping(target = "unitOfMeasure", expression = "java(Optional.ofNullable(ccaMasterVO.getOriginalShipmentVO()).map(CcaAwbVO::getUnitOfMeasure).orElse(null))")
    CCAApprovedEvent constructCCAApprovedEventFromCCAMasterVO(CCAMasterVO ccaMasterVO, @Context Units units);

    @Mapping(target = "ccaIssueDate", source = "ccaIssueDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "reasonCodes", expression = "java(Arrays.stream(Optional.ofNullable(ccaMaster.getCcaReason()).orElse(\"\").split(\",\")).map(ReasonCodeModel::new).collect(Collectors.toList()))")
    @Mapping(target = "outboundBillingStatus", source = "exportBillingStatus")
    @Mapping(target = "inboundBillingStatus", source = "importBillingStatus")
    @Mapping(target = "assignee", source = "ccaAssignee")
    CCADeleteEvent constructCCADeleteEventFromCcaMaster(CcaMaster ccaMaster);

    @Mapping(target = "ccaIssueDate", source = "ccaIssueDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "reasonCodes", expression = "java(Arrays.stream(Optional.ofNullable(ccaMaster.getCcaReason()).orElse(\"\").split(\",\")).map(ReasonCodeModel::new).collect(Collectors.toList()))")
    @Mapping(target = "outboundBillingStatus", source = "exportBillingStatus")
    @Mapping(target = "inboundBillingStatus", source = "importBillingStatus")
    @Mapping(target = "assignee", source = "ccaAssignee")
    CCAApprovedEvent constructCCAApprovedEventFromCcaMaster(CcaMaster ccaMaster);



    public default CcaCustomerDetailVO  getCustomerDetails(Set<CcaCustomerDetailVO> ccaCustomerDetailVO, CustomerType customerType){
        return  ccaCustomerDetailVO.stream().filter(details-> customerType.equals(details.getCustomerType())).findFirst().orElse(null);

    }
    CcaCustomerDetailData ccaCustomerDetailVOToCcaCustomerDetailData(CcaCustomerDetailVO ccaCustomerDetailVO);

}
