package com.ibsplc.neoicargo.awb.dao.impl;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.dao.impl.repositories.AwbRepository;
import com.ibsplc.neoicargo.awb.dao.impl.repositories.AwbUserNotificationRepository;
import com.ibsplc.neoicargo.framework.core.context.tenant.TenantContext;
import com.ibsplc.neoicargo.framework.tests.core.context.tenant.BootstrapTestContextFor;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(JUnitPlatform.class)
@ActiveProfiles("test")
@BootstrapTestContextFor(tenant = "AV")
@ExtendWith(SpringExtension.class)
@AutoConfigureEmbeddedDatabase(beanName = "defaultDataSource")
public class AwbDAOImplTest {
    @Autowired
    private TenantContext tenantContext;
    private AwbDAO awbDAO;
    private Quantities quantities;
    private AwbRepository awbRepository;
    private AwbUserNotificationRepository awbUserNotificationRepository;
    private final String shipmentKey1 = "134-78999255";
    private final String shipmentKey2 = "134-78999256";

    @BeforeEach
    public void setup() {
        quantities = MockQuantity.performInitialisation(null, null, "TRV", null);
        awbRepository = tenantContext.getBean(AwbRepository.class);
        awbUserNotificationRepository = tenantContext.getBean(AwbUserNotificationRepository.class);
        awbDAO = new AwbDAOImpl(awbRepository, awbUserNotificationRepository);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnAwbByShipmentKeyAfterSave() {

        //given
        var shipmentKey = new ShipmentKey("134", "789040");
        var expected = MockDataHelper.constructAwbEntity(null, shipmentKey, quantities);
        awbRepository.save(expected);

        //when
        var actual = awbDAO.findAwbByShipmentKey(shipmentKey);

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getShipmentKey(), actual.get().getShipmentKey());
    }

    @Test
    public void shouldReturnAwbByShipmentKey() {

        //given
        var shipmentKey = new ShipmentKey(shipmentKey1.substring(0, 3), shipmentKey1.substring(4));

        //when
        var actual = awbDAO.findAwbByShipmentKeys(List.of(shipmentKey));

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(shipmentKey, actual.get(0).getShipmentKey());
    }

    @Test
    public void shouldReturnAwbBySingleShipmentKey() {

        //given
        var shipmentKey = new ShipmentKey(shipmentKey1.substring(0, 3), shipmentKey1.substring(4));

        //when
        var actual = awbDAO.findAwbByShipmentKey(shipmentKey);

        //then
        Assertions.assertNotNull(actual);
        assertTrue(actual.isPresent());
        Assertions.assertEquals(shipmentKey, actual.get().getShipmentKey());
    }

    @Test
    public void shouldReturnSeveralAwbsByShipmentKey() {

        //given
        var shipmentKey_1 = new ShipmentKey(shipmentKey1.substring(0, 3), shipmentKey1.substring(4));
        var shipmentKey_2 = new ShipmentKey(shipmentKey2.substring(0, 3), shipmentKey2.substring(4));

        //when
        var actual = awbDAO.findAwbByShipmentKeys(List.of(shipmentKey_1, shipmentKey_2));

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals(shipmentKey_1, actual.get(0).getShipmentKey());
        Assertions.assertEquals(shipmentKey_2, actual.get(1).getShipmentKey());
    }

    @Test
    public void shouldSaveAwb() {
        //given
        var shipmentPrefix = "135";
        var masterDocumentNumber = "789040";
        var shipmentKey = new ShipmentKey(shipmentPrefix, masterDocumentNumber);
        var expected = MockDataHelper.constructAwbEntity(null, shipmentKey, quantities);

        //when
        awbDAO.saveAwb(expected);
        var actual = awbDAO.findAwbByShipmentKeys(List.of(shipmentKey)).get(0);

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getShipmentKey().getShipmentPrefix(), actual.getShipmentKey().getShipmentPrefix());
        Assertions.assertEquals(expected.getShipmentKey().getMasterDocumentNumber(), actual.getShipmentKey().getMasterDocumentNumber());
    }

    @Test
    public void shouldSaveAwbWithoutShipperAndConsignee() {
        //given
        var shipmentPrefix = "135";
        var masterDocumentNumber = "789040";
        var shipmentKey = new ShipmentKey(shipmentPrefix, masterDocumentNumber);
        var expected = MockDataHelper.constructAwbEntity(null, shipmentKey, quantities);
        expected.getAwbContactDetails().setConsigneeCode(null);
        expected.getAwbContactDetails().setShipperCode(null);

        //when
        awbDAO.saveAwb(expected);
        var actual = awbDAO.findAwbByShipmentKeys(List.of(shipmentKey)).get(0);

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getShipmentKey().getShipmentPrefix(), actual.getShipmentKey().getShipmentPrefix());
        Assertions.assertEquals(expected.getShipmentKey().getMasterDocumentNumber(), actual.getShipmentKey().getMasterDocumentNumber());
    }

    @Test
    public void shouldSaveAwbWithShipperAndConsigneeNull() {
        //given
        var shipmentPrefix = "135";
        var masterDocumentNumber = "789040";
        var shipmentKey = new ShipmentKey(shipmentPrefix, masterDocumentNumber);
        var expected = MockDataHelper.constructAwbEntity(null, shipmentKey, quantities);
        expected.setAwbContactDetails(null);

        //when
        awbDAO.saveAwb(expected);
        var actual = awbDAO.findAwbByShipmentKeys(List.of(shipmentKey)).get(0);

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getShipmentKey().getShipmentPrefix(), actual.getShipmentKey().getShipmentPrefix());
        Assertions.assertEquals(expected.getShipmentKey().getMasterDocumentNumber(), actual.getShipmentKey().getMasterDocumentNumber());
    }

    @Test
    public void shouldDeleteAwb() {
        //given
        var shipmentPrefix = "137";
        var masterDocumentNumber = "789040";
        var shipmentKey = new ShipmentKey(shipmentPrefix, masterDocumentNumber);
        var expected = MockDataHelper.constructAwbEntity(null, shipmentKey, quantities);

        //when
        awbDAO.saveAwb(expected);
        var actual = awbDAO.findAwbByShipmentKey(shipmentKey);

        //then
        assertTrue(actual.isPresent());
        var trackingAWBMaster = actual.get();
        Assertions.assertEquals(expected.getShipmentKey().getShipmentPrefix(), trackingAWBMaster.getShipmentKey().getShipmentPrefix());
        Assertions.assertEquals(expected.getShipmentKey().getMasterDocumentNumber(), trackingAWBMaster.getShipmentKey().getMasterDocumentNumber());

        awbDAO.deleteAwb(trackingAWBMaster);
        actual = awbDAO.findAwbByShipmentKey(shipmentKey);
        assertTrue(actual.isEmpty());
    }

    public void shouldReturnTrackingAWBMasterByShipmentKeyAfterSave() {

        //given
        var shipmentKey = new ShipmentKey("134", "789040");
        var expected = MockDataHelper.constructAwbEntity(shipmentKey, quantities);
        awbRepository.save(expected);

        //when
        var actual = awbDAO.findAwbByShipmentKey(shipmentKey);

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getShipmentKey(), actual.get().getShipmentKey());
    }

    @Test
    public void shouldReturnTrackingAWBMasterByShipmentKey() {

        //given
        var shipmentKey = new ShipmentKey(shipmentKey1.substring(0, 3), shipmentKey1.substring(4));

        //when
        var actual = awbDAO.findAwbByShipmentKeys(List.of(shipmentKey));

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(shipmentKey, actual.get(0).getShipmentKey());
    }

    @Test
    public void shouldReturnTrackingAWBMasterBySingleShipmentKey() {

        //given
        var shipmentKey = new ShipmentKey(shipmentKey1.substring(0, 3), shipmentKey1.substring(4));

        //when
        var actual = awbDAO.findAwbByShipmentKey(shipmentKey);

        //then
        Assertions.assertNotNull(actual);
        assertTrue(actual.isPresent());
        Assertions.assertEquals(shipmentKey, actual.get().getShipmentKey());
    }

    @Test
    public void shouldReturnSeveralTrackingAWBMasterByShipmentKey() {

        //given
        var shipmentKey_1 = new ShipmentKey(shipmentKey1.substring(0, 3), shipmentKey1.substring(4));
        var shipmentKey_2 = new ShipmentKey(shipmentKey2.substring(0, 3), shipmentKey2.substring(4));

        //when
        var actual = awbDAO.findAwbByShipmentKeys(List.of(shipmentKey_1, shipmentKey_2));

        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals(shipmentKey_1, actual.get(0).getShipmentKey());
        Assertions.assertEquals(shipmentKey_2, actual.get(1).getShipmentKey());
    }

    @Test
    void shouldFindAwbPersonalDataById() {
        var shipmentKey = new ShipmentKey();
        shipmentKey.setShipmentPrefix("134");
        shipmentKey.setMasterDocumentNumber("78999256");
        var awb = awbDAO.findAwbByShipmentKey(shipmentKey).get();
        var personalInfo = awb.getAwbContactDetails();

        assertEquals("AV", personalInfo.getCompanyCode());
        assertEquals("DHLABRAJ", personalInfo.getShipperCode());
        assertEquals("CCBEY", personalInfo.getConsigneeCode());
        assertNotNull(personalInfo.getConsigneeDetails());
        assertNotNull(personalInfo.getShipperDetails());
    }

    @Test
    void crudAwbUserNotification() {
        var notification = MockDataHelper.constructAwbUserNotification();
        awbDAO.saveAwbUserNotification(notification);

        var savedNotification = awbDAO.findAwbUserNotificationByKey(notification.getNotificationsKey()).get();

        assertEquals(notification.getNotificationMilestones(), savedNotification.getNotificationMilestones());
        assertEquals(notification.getEmails(), savedNotification.getEmails());

        awbDAO.deleteAwbUserNotification(notification);
        assertTrue(awbDAO.findAwbUserNotificationByKey(notification.getNotificationsKey()).isEmpty());
    }
}