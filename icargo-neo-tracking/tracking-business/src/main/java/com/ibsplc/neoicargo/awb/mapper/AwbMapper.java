package com.ibsplc.neoicargo.awb.mapper;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.model.AwbUserNotificationModel;
import com.ibsplc.neoicargo.awb.model.ContactDetailsModel;
import com.ibsplc.neoicargo.awb.model.ShipperConsigneeDetailsModel;
import com.ibsplc.neoicargo.awb.vo.AwbContactDetailsVO;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationKeyVO;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationVO;
import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.common.mapper.BaseMapper;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.tracking.vo.MilestoneMasterVO;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring", config = CentralConfig.class, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, imports = {ShipmentKey.class})
public interface AwbMapper extends BaseMapper {

    @Mapping(target = "shipperDetails", expression = "java(objectToContactDetailsModel(awbContactDetails.getShipperDetails()))")
    @Mapping(target = "consigneeDetails", expression = "java(objectToContactDetailsModel(awbContactDetails.getConsigneeDetails()))")
    ShipperConsigneeDetailsModel constructAwbContactDetailModel(AwbContactDetailsVO awbContactDetails);


    @AfterMapping
    default void setCodes(AwbContactDetailsVO awbContactDetails, @MappingTarget ShipperConsigneeDetailsModel model) {
        model.getConsigneeDetails().setCode(awbContactDetails.getConsigneeCode());
        model.getShipperDetails().setCode(awbContactDetails.getShipperCode());
    }

    default ContactDetailsModel objectToContactDetailsModel(Object details) {
        var model = new ContactDetailsModel();

        if (Objects.nonNull(details)) {
            var mapDetails = (Map) details;
            var city = mapDetails.get("city");
            var country = mapDetails.get("country");
            var state = mapDetails.get("state");
            var zipCode = mapDetails.get("zip_code");
            var name = mapDetails.get("name");
            var address = mapDetails.get("address");

            model.setCity(Objects.nonNull(city) ? city.toString() : "");
            model.setAddress(Objects.nonNull(address) ? address.toString() : "");
            model.setCountry(Objects.nonNull(country) ? country.toString() : "");
            model.setState(Objects.nonNull(state) ? state.toString() : "");
            model.setName(Objects.nonNull(name) ? name.toString() : "");
            model.setZipCode(Objects.nonNull(zipCode) ? zipCode.toString() : "");

        }

        return model;
    }

    @Mapping(target = "trackingAwbSerialNumber", source = "awbUserNotificationVO.notificationsKey.trackingAwbSerialNumber")
    @BeanMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
    AwbUserNotificationModel constructAwbUserNotificationModel(AwbUserNotificationVO awbUserNotificationVO,List<MilestoneMasterVO> milestonVOs);

    @AfterMapping
    default void setNotifications(AwbUserNotificationVO awbUserNotificationVO,List<MilestoneMasterVO> milestonVOs, @MappingTarget AwbUserNotificationModel model) {
     var notifications= milestonVOs.stream().filter(MilestoneMasterVO::isEmailNotificationFlag).collect(Collectors.toMap(MilestoneMasterVO::getMilestoneCode, enabled -> Boolean.FALSE));
     if (Objects.nonNull(awbUserNotificationVO)) {
            List<String> enabled = awbUserNotificationVO.getNotificationMilestones();
            milestonVOs.stream().filter(MilestoneMasterVO::isEmailNotificationFlag).filter(n -> enabled.contains(n.getMilestoneCode())).map(MilestoneMasterVO::getMilestoneCode).forEach(label -> notifications.put(label, Boolean.TRUE));
        } else {
            model.setEmails(List.of());
        }

        model.setNotifications(notifications);
    }

    @Mapping(target = "shipmentKey", expression = "java(new ShipmentKey(awb.split(\"-\")[0], awb.split(\"-\")[1]))")
    @Mapping(target = "notificationMilestones", expression = "java(notificationModel.getNotifications().entrySet().stream().filter(java.util.Map.Entry::getValue).map(v-> v.getKey()).collect(java.util.stream.Collectors.toList()))")
    @Mapping(target = "companyCode", expression = "java(toCompanyCode(contextUtil))")
    AwbUserNotificationVO constructAwbUserNotificationVO(String awb, AwbUserNotificationModel notificationModel,List<MilestoneMasterVO> milestonVOs, @Context ContextUtil contextUtil);

    @Mapping(target = "trackingAwbSerialNumber", source = "trackingAwbSerialNumber")
    @Mapping(target = "userId", expression = "java(contextUtil.callerLoginProfile().getUserId())")
    AwbUserNotificationKeyVO constructAwbUserNotificationKeyVO(Long trackingAwbSerialNumber, ContextUtil contextUtil);
}
