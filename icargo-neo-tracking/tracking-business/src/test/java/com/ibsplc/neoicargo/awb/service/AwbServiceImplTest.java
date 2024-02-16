package com.ibsplc.neoicargo.awb.service;

import com.ibsplc.neoicargo.awb.component.AwbComponent;
import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationKeyVO;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationVO;
import com.ibsplc.neoicargo.awb.vo.AwbVO;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(JUnitPlatform.class)
public class AwbServiceImplTest {

    @InjectMocks
    private AwbServiceImpl awbService;

    @Mock
    private AwbComponent awbComponent;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSaveAwb() {
        // given
        doNothing().when(awbComponent).saveAwb(any(AwbVO.class));

        // then
        Assertions.assertDoesNotThrow(() -> awbService.saveAwb(any(AwbVO.class)));
    }

    @Test
    public void shouldReopenAwb() {
        // given
        doNothing().when(awbComponent).reopenAwb(any(AwbValidationVO.class));

        // then
        Assertions.assertDoesNotThrow(() -> awbService.reopenAwb(any(AwbValidationVO.class)));
    }

    @Test
    public void shouldDeleteAwb() {
        // given
        doNothing().when(awbComponent).deleteAwb(any(AwbValidationVO.class));

        // then
        Assertions.assertDoesNotThrow(() -> awbService.deleteAwb(any(AwbValidationVO.class)));
    }

    @Test
    public void shouldExecuteAwb() {
        // given
        doNothing().when(awbComponent).deleteAwb(any(AwbValidationVO.class));

        // then
        Assertions.assertDoesNotThrow(() -> awbService.executeAwb(any(AwbValidationVO.class)));
    }

    @Test
    void shouldGetAwbContactDetails(){
        var awbRequestVO = new AwbRequestVO();
        awbService.getAwbContactDetails(awbRequestVO);
        verify(awbComponent).getAwbContactDetails(awbRequestVO);
    }

    @Test
    void shouldGetAwbUserNotifications(){
        var awbRequestVO = new AwbRequestVO();
        awbService.getAwbUserNotifications(awbRequestVO);
        verify(awbComponent).getAwbUserNotifications(awbRequestVO);
    }

    @Test
    void shouldSaveAwbUserNotifications(){
        var notificationVO = new AwbUserNotificationVO();
        awbService.saveAwbUserNotifications(notificationVO);
        verify(awbComponent).saveAwbUserNotifications(notificationVO);
    }

    @Test
    void shouldDeleteAwbUserNotifications(){
        var notificationKeyVO = new AwbUserNotificationKeyVO();
        awbService.deleteAwbUserNotifications(notificationKeyVO);
        verify(awbComponent).deleteAwbUserNotifications(notificationKeyVO);
    }
}