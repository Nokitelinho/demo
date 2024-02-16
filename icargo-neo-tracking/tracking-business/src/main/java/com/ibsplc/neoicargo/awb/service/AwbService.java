package com.ibsplc.neoicargo.awb.service;

import com.ibsplc.neoicargo.awb.vo.AwbContactDetailsVO;
import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationKeyVO;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationVO;
import com.ibsplc.neoicargo.awb.vo.AwbVO;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;

public interface AwbService {
    void saveAwb(AwbVO awbVO);

    void reopenAwb(AwbValidationVO awbValidationVO);

    void deleteAwb(AwbValidationVO awbValidationVO);

    void executeAwb(AwbValidationVO awbValidationVO);

    AwbContactDetailsVO getAwbContactDetails(AwbRequestVO awb);

    AwbUserNotificationVO getAwbUserNotifications(AwbRequestVO awbVO);

    void saveAwbUserNotifications(AwbUserNotificationVO awbUserNotificationVO);

    void deleteAwbUserNotifications(AwbUserNotificationKeyVO awbUserNotificationKeyVO);
}
