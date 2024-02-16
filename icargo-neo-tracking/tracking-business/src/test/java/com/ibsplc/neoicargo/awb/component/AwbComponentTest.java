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
import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationKeyVO;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationVO;
import com.ibsplc.neoicargo.awb.vo.AwbVO;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AwbComponentTest {

    @InjectMocks
    private AwbComponent awbComponent;
    @Mock
    private SaveAwbFeature saveAwbFeature;
    @Mock
    private ReopenAwbFeature reopenAwbFeature;
    @Mock
    private DeleteAwbFeature deleteAwbFeature;
    @Mock
    private ExecuteAwbFeature executeAwbFeature;
    @Mock
    private GetAwbListFeature getAwbListFeature;
    @Mock
    private GetAwbContactDetailsFeature getAwbContactDetailsFeature;
    @Mock
    private GetAwbUserNotificationFeature getAwbUserNotificationFeature;
    @Mock
    private SaveAwbUserNotificationFeature saveAwbUserNotificationFeature;
    @Mock
    private DeleteAwbUserNotificationFeature deleteAwbUserNotificationFeature;

    @Test
    public void shouldReturnAwbList() {

        var awbRequestVO1 = new AwbRequestVO("020-6666666");

        // given
        var trackingAWBMasterVO = new AwbVO();
        trackingAWBMasterVO.setShipmentPrefix("020");
        trackingAWBMasterVO.setMasterDocumentNumber("6666666");
        doReturn(List.of(trackingAWBMasterVO)).when(getAwbListFeature).perform(anyList());

        // when
        var awbList = awbComponent.getAwbList(List.of(awbRequestVO1));

        // then
        Assertions.assertNotNull(awbList);
        Assertions.assertEquals(1, awbList.size());
        Assertions.assertEquals("020-6666666", awbList.get(0).getBusinessId());
    }

    @Test
    public void shouldSaveAwb() {
        // given
        var awbVO = new AwbVO();
        // when
        awbComponent.saveAwb(awbVO);
        // then
        verify(saveAwbFeature).perform(awbVO);
    }

    @Test
    public void shouldReopenAwb() {
        // given
        var awbValidationVO = new AwbValidationVO();
        // when
        awbComponent.reopenAwb(awbValidationVO);
        //then
        verify(reopenAwbFeature).perform(awbValidationVO);

    }

    @Test
    public void shouldDeleteAwb() {
        // given
        var awbValidationVO = new AwbValidationVO();
        //
        awbComponent.deleteAwb(awbValidationVO);
        // then
        verify(deleteAwbFeature).execute(awbValidationVO);
    }

    @Test
    public void shouldExecuteAwb() {
        // given
        var awbValidationVO = new AwbValidationVO();
        //when
        awbComponent.executeAwb(awbValidationVO);
        // then
        verify(executeAwbFeature).perform(awbValidationVO);
    }

    @Test
    void shouldGetAwbContactDetails() {
        var awbRequestVO = new AwbRequestVO();
        awbComponent.getAwbContactDetails(awbRequestVO);

        verify(getAwbContactDetailsFeature).perform(awbRequestVO);
    }

    @Test
    void shouldGetAwbUserNotifications() {
        var awbRequestVO = new AwbRequestVO();
        awbComponent.getAwbUserNotifications(awbRequestVO);

        verify(getAwbUserNotificationFeature).perform(awbRequestVO);
    }

    @Test
    void shouldSaveAwbUserNotifications() {
        var userNotificationVO = new AwbUserNotificationVO();
        awbComponent.saveAwbUserNotifications(userNotificationVO);

        verify(saveAwbUserNotificationFeature).execute(userNotificationVO);
    }

    @Test
    void shouldDeleteAwbUserNotifications() {
        var userNotificationKeyVO = new AwbUserNotificationKeyVO();
        awbComponent.deleteAwbUserNotifications(userNotificationKeyVO);

        verify(deleteAwbUserNotificationFeature).perform(userNotificationKeyVO);
    }

}