package com.ibsplc.neoicargo.tracking.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ibsplc.neoicargo.awb.mapper.AwbMapper;
import com.ibsplc.neoicargo.awb.model.AwbUserNotificationModel;
import com.ibsplc.neoicargo.awb.model.ShipperConsigneeDetailsModel;
import com.ibsplc.neoicargo.awb.service.AwbService;
import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.framework.core.security.spring.oauth2.AuthorizedService;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.tracking.TrackingAPI;
import com.ibsplc.neoicargo.tracking.mapper.TrackingMapper;
import com.ibsplc.neoicargo.tracking.model.MilestoneMasterModel;
import com.ibsplc.neoicargo.tracking.model.ShipmentActivityModel;
import com.ibsplc.neoicargo.tracking.model.ShipmentDetailsModel;
import com.ibsplc.neoicargo.tracking.model.SplitModel;
import com.ibsplc.neoicargo.tracking.service.TrackingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrackingController implements TrackingAPI {

    private final TrackingService trackingService;
    private final AwbService awbService;
    private final TrackingMapper trackingMapper;
    private final AwbMapper awbMapper;
    private final ContextUtil contextUtil;
    private final AuthorizedService authService;
    

    @Override
    public List<ShipmentDetailsModel> getShipmentDetails(List<String> awbs) {
        var requestVOS = awbs.stream()
                .map(AwbRequestVO::new)
                .collect(Collectors.toList());

        return trackingMapper.constructShipmentDetailsModels(trackingService.getShipments(requestVOS));
    }

    @Override
    public List<SplitModel> getShipmentSplits(String awb) {
        return trackingMapper.constructSplits(trackingService.getShipmentSplits(new AwbRequestVO(awb)));
    }

    @Override
    public ShipperConsigneeDetailsModel getShipperConsigneeDetails(String awb) {

        var awbContactDetailsVO = awbService.getAwbContactDetails(new AwbRequestVO(awb));

        return awbMapper.constructAwbContactDetailModel(awbContactDetailsVO);
    }

    @Override
    public List<ShipmentActivityModel> getShipmentActivities(String awb) {

        var activities = trackingService.getShipmentActivities(new AwbRequestVO(awb));

        return trackingMapper.constructShipmentActivities(activities);
    }


    @Override
    public AwbUserNotificationModel getUserAwbNotifications(String awb) {
        var notificationVO = awbService.getAwbUserNotifications(new AwbRequestVO(awb));
        var milestonVOs = trackingService.findAllMilestones();
        return awbMapper.constructAwbUserNotificationModel(notificationVO,milestonVOs);
    }

    @Override
    public AwbUserNotificationModel saveUserAwbNotifications(String awb, AwbUserNotificationModel notificationModel) {
    	 var milestonVOs = trackingService.findAllMilestones();
    	 var awbUserNotificationVO = awbMapper.constructAwbUserNotificationVO(awb, notificationModel, milestonVOs,contextUtil);
        awbService.saveAwbUserNotifications(awbUserNotificationVO);
        return awbMapper.constructAwbUserNotificationModel(awbUserNotificationVO,milestonVOs);
    }

    @Override
    public void deleteUserAwbNotifications(Long trackingAwbSerialNumber) {
        var awbUserNotificationKeyVO = awbMapper.constructAwbUserNotificationKeyVO(trackingAwbSerialNumber, contextUtil);
        awbService.deleteAwbUserNotifications(awbUserNotificationKeyVO);
    }
    
    @Override
    public List<MilestoneMasterModel> findAllMilestones(){
    	authService.authorizeFor(contextUtil.getTenant());
    	return trackingMapper.constructMilestoneMasterModels(trackingService.findAllMilestones());
    	
    }

}
