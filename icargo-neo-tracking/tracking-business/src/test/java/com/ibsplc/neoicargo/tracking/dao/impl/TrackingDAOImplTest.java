package com.ibsplc.neoicargo.tracking.dao.impl;

import com.ibsplc.neoicargo.framework.core.context.tenant.TenantContext;
import com.ibsplc.neoicargo.framework.tests.core.context.tenant.BootstrapTestContextFor;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.testframework.dao.PostgresDaoTest;
import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.dao.entity.ShipmentMilestonePlan;
import com.ibsplc.neoicargo.tracking.dao.impl.repositories.MilestoneRepository;
import com.ibsplc.neoicargo.tracking.dao.impl.repositories.ShipmentMilestoneEventRepository;
import com.ibsplc.neoicargo.tracking.dao.impl.repositories.ShipmentMilestonePlanRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(JUnitPlatform.class)
@ActiveProfiles("test")
@BootstrapTestContextFor(tenant = "AV")
@ExtendWith(SpringExtension.class)
@AutoConfigureEmbeddedDatabase(beanName = "defaultDataSource")
@Transactional
public class TrackingDAOImplTest extends PostgresDaoTest {
    @Autowired
    private TenantContext tenantContext;
    private TrackingDAO trackingDAO;
    private Quantities quantities;
    private ShipmentMilestonePlanRepository shipmentMilestonePlanRepository;
    private ShipmentMilestoneEventRepository shipmentMilestoneEventRepository;
    private MilestoneRepository milestoneRepository;
    private final String shipmentKey1 = "134-78999255";
    private final String shipmentKey2 = "134-78999256";

    @BeforeEach
    public void setup() {
        quantities = MockQuantity.performInitialisation(null, null, "TRV", null);
        shipmentMilestonePlanRepository = tenantContext.getBean(ShipmentMilestonePlanRepository.class);
        shipmentMilestoneEventRepository = tenantContext.getBean(ShipmentMilestoneEventRepository.class);
        milestoneRepository = tenantContext.getBean(MilestoneRepository.class);
        trackingDAO = new TrackingDAOImpl(shipmentMilestonePlanRepository, shipmentMilestoneEventRepository, milestoneRepository);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnTrackingShipmentMilestonePlansByShipmentKey() {

        //when
        var actual = trackingDAO.findPlansByShipmentKeys(List.of(shipmentKey1));

        //then
        assertEquals(actual.size(), 6);
        assertTrue(actual.stream().allMatch(plan -> plan.getShipmentKey().equals(shipmentKey1)));
    }

    @Test
    public void shouldReturnTrackingShipmentMilestonePlansBySeveralShipmentKey() {

        //when
        var actual = trackingDAO.findPlansByShipmentKeys(List.of(shipmentKey1, shipmentKey2));

        //then
        assertEquals(10, actual.size());
        assertTrue(actual.stream().allMatch(plan -> plan.getShipmentKey().equals(shipmentKey1) || plan.getShipmentKey().equals(shipmentKey2)));
    }

    @Test
    public void shouldReturnTrackingShipmentMilestoneEventByShipmentKeyAndType() {

        //given
        var shipmentKey = "134-78999255";

        //when
        var actual = trackingDAO.findEventsByShipmentKeysAndType(List.of(shipmentKey), "A");

        //then
        assertEquals(actual.size(), 4);
        assertTrue(actual.stream().allMatch(event -> event.getShipmentKey().equals(shipmentKey)));
        var actualEvent = actual.get(1);
        assertEquals("BHM", actualEvent.getAirportCode());
        assertEquals("DEP", actualEvent.getMilestoneCode());
        assertEquals(100, actualEvent.getPieces());
        assertEquals(100, actualEvent.getPieces());
        assertEquals(1.3, actualEvent.getWeight());
        assertEquals("K", actualEvent.getEUnit().getWeight());

        assertEquals("carrier_1", ((LinkedHashMap) actual.get(0).getTransactionDetails()).get("fromCarrier"));
        assertEquals("carrier_2", ((LinkedHashMap) actual.get(0).getTransactionDetails()).get("flightCarrierCode"));
    }

    @Test
    public void shouldDeletePlanByShipmentKey() {

        //given
        var shipmentKey = "020-88888888";

        //then
        Assertions.assertDoesNotThrow(() -> trackingDAO.deletePlanByShipmentKey(shipmentKey));
    }

    @Test
    public void shouldDeleteEventByShipmentKey() {

        //given
        var shipmentKey = "020-88888888";

        //when

        //then
        Assertions.assertDoesNotThrow(() -> trackingDAO.deleteEventByShipmentKey(shipmentKey));
    }

    @Test
    void shouldSaveShipmentMilestonePlan() {
        //given
        var expected = MockDataHelper.constructTrackingShipmentMilestonePlanEntities();
        var shipmentKeys = expected.stream().map(ShipmentMilestonePlan::getShipmentKey).distinct().collect(Collectors.toList());
        //when
        trackingDAO.saveShipmentMilestonePlans(expected);
        var actual = trackingDAO.findPlansByShipmentKeys(shipmentKeys);
        //then
        assertEquals(expected.size(), actual.size());
        IntStream.range(0, expected.size()).forEach(index -> assertPlans(expected.get(index), actual.get(index)));
    }

    @Test
    void shouldDeleteShipmentMilestonePlan() {
        ;

        //given
        var actual = trackingDAO.findPlansByShipmentKeys(List.of(shipmentKey1));
        assertEquals(actual.size(), 6);

        //when
        trackingDAO.deletePlanByShipmentKey(shipmentKey1);

        //then
        var result = trackingDAO.findPlansByShipmentKeys(List.of(shipmentKey1));
        assertEquals(0, result.size());
    }

    @Test
    void shouldDeleteShipmentMilestoneEvent() {
        ;

        //given
        var shipmentKey = "134-78999255";
        var actual = trackingDAO.findEventsByShipmentKeysAndType(List.of(shipmentKey), "A");
        assertEquals(actual.size(), 4);

        //when
        trackingDAO.deleteEventByShipmentKey(shipmentKey);

        //then
        var result = trackingDAO.findEventsByShipmentKeysAndType(List.of(shipmentKey), "A");
        assertEquals(0, result.size());
    }

    private void assertPlans(ShipmentMilestonePlan actual, ShipmentMilestonePlan expected) {
        assertEquals(expected.getAirportCode(), actual.getAirportCode());
        assertEquals(expected.getShipmentKey(), actual.getShipmentKey());
        assertEquals(expected.getFlightNumber(), actual.getFlightNumber());
        assertEquals(expected.getFlightDate(), actual.getFlightDate());
        assertEquals(expected.getMilestoneCode(), actual.getMilestoneCode());
        assertEquals(expected.getCompanyCode(), actual.getCompanyCode());
        assertEquals(expected.getShipmentSequenceNumber(), actual.getShipmentSequenceNumber());
        assertNotNull(actual.getSerialNumber());
    }
    

    @Test
    void shouldFindMilestons() {
        var actual = trackingDAO.findAllMilestones();
        assertNotNull(actual);       
    }

}
