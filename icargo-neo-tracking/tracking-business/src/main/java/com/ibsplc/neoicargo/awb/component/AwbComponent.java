package com.ibsplc.neoicargo.awb.component;

import com.ibsplc.neoicargo.awb.component.feature.deleteawb.DeleteAwbFeature;
import com.ibsplc.neoicargo.awb.component.feature.deleteawbusernotification.DeleteAwbUserNotificationFeature;
import com.ibsplc.neoicargo.awb.component.feature.executeawb.ExecuteAwbFeature;
import com.ibsplc.neoicargo.awb.component.feature.getawbcontactdetails.GetAwbContactDetailsFeature;
import com.ibsplc.neoicargo.awb.component.feature.getawblist.GetAwbListFeature;
import com.ibsplc.neoicargo.awb.component.feature.getawbusernotification.GetAwbUserNotificationFeature;
import com.ibsplc.neoicargo.awb.component.feature.reopenawb.ReopenAwbFeature;
import com.ibsplc.neoicargo.awb.component.feature.saveawb.SaveAwbFeature;
import com.ibsplc.neoicargo.awb.component.feature.saveawbusernotification.SaveAwbUserNotificationFeature;
import com.ibsplc.neoicargo.awb.vo.AwbContactDetailsVO;
import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationKeyVO;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationVO;
import com.ibsplc.neoicargo.awb.vo.AwbVO;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("awbComponent")
@RequiredArgsConstructor
public class AwbComponent {

    private final SaveAwbFeature saveAwbFeature;
    private final DeleteAwbFeature deleteAwbFeature;
    private final ReopenAwbFeature reopenAwbFeature;
    private final GetAwbListFeature getAwbListFeature;
    private final ExecuteAwbFeature executeAwbFeature;
    private final GetAwbContactDetailsFeature getAwbContactDetailsFeature;
    private final GetAwbUserNotificationFeature getAwbUserNotificationFeature;
    private final SaveAwbUserNotificationFeature saveAwbUserNotificationFeature;
    private final DeleteAwbUserNotificationFeature deleteAwbUserNotificationFeature;

    public List<AwbVO> getAwbList(List<AwbRequestVO> awbs) {
        log.info("Entering get AWB List");
        return getAwbListFeature.perform(awbs);
    }

    public void saveAwb(AwbVO awbVO) {
        log.info("Entering save tracking awb master");
        saveAwbFeature.perform(awbVO);
    }

    public void reopenAwb(AwbValidationVO awbValidationVO) {
        log.info("Entering reopen tracking awb master");
        reopenAwbFeature.perform(awbValidationVO);
    }

    public void deleteAwb(AwbValidationVO awbValidationVO) {
        log.info("Entering delete tracking awb master");
        deleteAwbFeature.execute(awbValidationVO);
    }

    public void executeAwb(AwbValidationVO awbValidationVO) {
        log.info("Entering execute tracking awb master");
        executeAwbFeature.perform(awbValidationVO);
    }

    public AwbContactDetailsVO getAwbContactDetails(AwbRequestVO requestVO) {
        log.info("Entering get AWB contact details");
        return getAwbContactDetailsFeature.perform(requestVO);
    }

    public AwbUserNotificationVO getAwbUserNotifications(AwbRequestVO requestVO) {
        log.info("Entering delete AWB user notifications");
        return getAwbUserNotificationFeature.perform(requestVO);
    }

    public void saveAwbUserNotifications(AwbUserNotificationVO awbUserNotificationVO) {
        log.info("Entering save AWB user notifications");
        saveAwbUserNotificationFeature.execute(awbUserNotificationVO);
    }

    public void deleteAwbUserNotifications(AwbUserNotificationKeyVO awbUserNotificationKeyVO) {
        log.info("Entering delete AWB user notifications");
        deleteAwbUserNotificationFeature.perform(awbUserNotificationKeyVO);
    }
}
