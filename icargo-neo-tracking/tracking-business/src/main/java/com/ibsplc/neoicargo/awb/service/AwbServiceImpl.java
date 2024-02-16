package com.ibsplc.neoicargo.awb.service;

import com.ibsplc.neoicargo.awb.component.AwbComponent;
import com.ibsplc.neoicargo.awb.vo.AwbContactDetailsVO;
import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationKeyVO;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationVO;
import com.ibsplc.neoicargo.awb.vo.AwbVO;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
import com.ibsplc.neoicargo.framework.core.lang.notation.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("awbService")
@BusinessService
@RequiredArgsConstructor
public class AwbServiceImpl implements AwbService {

    private final AwbComponent awbComponent;

    @Override
    public void saveAwb(AwbVO awbVO) {
        awbComponent.saveAwb(awbVO);
    }

    @Override
    public void reopenAwb(AwbValidationVO awbValidationVO) {
        awbComponent.reopenAwb(awbValidationVO);
    }

    @Override
    public void deleteAwb(AwbValidationVO awbValidationVO) {
        awbComponent.deleteAwb(awbValidationVO);
    }

    @Override
    public void executeAwb(AwbValidationVO awbValidationVO) {
        awbComponent.executeAwb(awbValidationVO);
    }

    @Override
    public AwbContactDetailsVO getAwbContactDetails(AwbRequestVO awb) {
      return awbComponent.getAwbContactDetails(awb);
    }

    @Override
    public AwbUserNotificationVO getAwbUserNotifications(AwbRequestVO awbVO) {
        return awbComponent.getAwbUserNotifications(awbVO);
    }

    @Override
    public void saveAwbUserNotifications(AwbUserNotificationVO awbUserNotificationVO) {
        awbComponent.saveAwbUserNotifications(awbUserNotificationVO);
    }

    @Override
    public void deleteAwbUserNotifications(AwbUserNotificationKeyVO awbUserNotificationKeyVO) {
        awbComponent.deleteAwbUserNotifications(awbUserNotificationKeyVO);
    }
}
