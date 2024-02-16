package com.ibsplc.neoicargo.awb.mapper;

import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
public class AwbEventMapperTest {

    @Mock
    private ContextUtil contextUtil;
    @Mock
    private LoginProfile loginProfile;
    private AwbEventMapper awbEventMapper;
    private Quantities quantities;
    private QuantityMapper quantityMapper;

    @BeforeEach
    public void setup() {
        quantities = MockQuantity.performInitialisation(null, null, null, null);
        quantityMapper = MockQuantity.injectMapper(quantities, QuantityMapper.class);
        awbEventMapper = MockQuantity.injectMapper(quantities, AwbEventMapper.class);

        MockitoAnnotations.initMocks(this);
        doReturn(loginProfile).when(contextUtil).callerLoginProfile();
        doReturn("CODE").when(loginProfile).getCompanyCode();
    }

    @Test
    void shouldConstructAwbVo() {
        // given
        var awbCreatedEvent = MockDataHelper.constructAWBCreatedEvent("001", "15615600");

        // when
        var actual = awbEventMapper.constructAwbVO(awbCreatedEvent, contextUtil);

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals("001", actual.getShipmentPrefix());
        Assertions.assertEquals("15615600", actual.getMasterDocumentNumber());
        Assertions.assertEquals("CODE", actual.getCompanyCode());
        Assertions.assertEquals("DFW", actual.getOrigin());
        Assertions.assertEquals("LHR", actual.getDestination());
        Assertions.assertEquals(10, actual.getStatedPieces());
        Assertions.assertEquals("DHL", actual.getAwbContactDetailsVO().getShipperCode());
        Assertions.assertEquals("DHLLHR", actual.getAwbContactDetailsVO().getConsigneeCode());
        Assertions.assertEquals(100.0, actual.getStatedWeight().getDisplayValue().doubleValue());
        Assertions.assertEquals("K", actual.getStatedWeight().getDisplayUnit().getName());
        Assertions.assertEquals(10.0, actual.getStatedVolume().getDisplayValue().doubleValue());
        Assertions.assertEquals("F", actual.getStatedVolume().getDisplayUnit().getName());
        Assertions.assertEquals("K", actual.getUnitsOfMeasure().getWeight());
        Assertions.assertEquals("F", actual.getUnitsOfMeasure().getVolume());
    }

    @Test
    void shouldConstructAwbVoFromUpdatedEvent() {
        // given
        var awbUpdatedEvent = MockDataHelper.constructAWBUpdatedEvent("001", "15615600");

        // when
        var actual = awbEventMapper.constructAwbVO(awbUpdatedEvent, contextUtil);

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals("001", actual.getShipmentPrefix());
        Assertions.assertEquals("15615600", actual.getMasterDocumentNumber());
        Assertions.assertEquals("DHL", actual.getAwbContactDetailsVO().getShipperCode());
        Assertions.assertEquals("DHLLHR", actual.getAwbContactDetailsVO().getConsigneeCode());
        Assertions.assertEquals(100.0, actual.getStatedWeight().getDisplayValue().doubleValue());
        Assertions.assertEquals("K", actual.getStatedWeight().getDisplayUnit().getName());
        Assertions.assertEquals(10.0, actual.getStatedVolume().getDisplayValue().doubleValue());
        Assertions.assertEquals("F", actual.getStatedVolume().getDisplayUnit().getName());
        Assertions.assertEquals("K", actual.getUnitsOfMeasure().getWeight());
        Assertions.assertEquals("F", actual.getUnitsOfMeasure().getVolume());
    }

    @Test
    void shouldConstructAwbVoWithoutContactDetails() {
        // given
        var awbCreatedEvent = MockDataHelper.constructAWBCreatedEvent("001", "15615600");
        awbCreatedEvent.setShipperCode(null);
        awbCreatedEvent.setConsigneeCode(null);

        // when
        var actual = awbEventMapper.constructAwbVO(awbCreatedEvent, contextUtil);

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals("001", actual.getShipmentPrefix());
        Assertions.assertEquals("15615600", actual.getMasterDocumentNumber());
        Assertions.assertNull(actual.getAwbContactDetailsVO());
    }

    @Test
    void shouldConstructAwbUpdateVoWithoutContactDetails() {
        // given
        var awbUpdatedEvent = MockDataHelper.constructAWBUpdatedEvent("001", "15615600");
        awbUpdatedEvent.setShipperCode(null);
        awbUpdatedEvent.setConsigneeCode(null);

        // when
        var actual = awbEventMapper.constructAwbVO(awbUpdatedEvent, contextUtil);

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals("001", actual.getShipmentPrefix());
        Assertions.assertEquals("15615600", actual.getMasterDocumentNumber());
        Assertions.assertNull(actual.getAwbContactDetailsVO());
    }
}
