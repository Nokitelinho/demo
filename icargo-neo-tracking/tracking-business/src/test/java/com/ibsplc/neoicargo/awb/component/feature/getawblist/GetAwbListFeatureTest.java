package com.ibsplc.neoicargo.awb.component.feature.getawblist;

import com.ibsplc.neoicargo.awb.component.feature.getawblist.validator.AwbListValidator;
import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.mapper.AwbEntityMapper;
import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.framework.core.security.spring.oauth2.AuthorizedService;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(JUnitPlatform.class)
public class GetAwbListFeatureTest {

    @InjectMocks
    private GetAwbListFeature getAwbListFeature;
    @Mock
    private AwbDAO awbDAO;
    @Mock
    private AwbListValidator awbListValidator;
    @Mock
    private AuthorizedService authService;
    @Mock
    private ContextUtil contextUtil;

    private AwbEntityMapper awbEntityMapper;
    private Quantities quantities;
    private QuantityMapper quantityMapper;

    @BeforeEach
    public void setup() {
        quantities = MockQuantity.performInitialisation(null, null, "TRV", null);
        quantityMapper = MockQuantity.injectMapper(quantities, QuantityMapper.class);
        awbEntityMapper = MockQuantity.injectMapper(quantities, AwbEntityMapper.class);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnAwbList() {

        //given
        var shipmentKeyString = "020-222222";
        var request = new AwbRequestVO(shipmentKeyString);
        var shipmentKey = new ShipmentKey("020", "222222");
        var trackingAWBMasterEntity = MockDataHelper.constructAwbEntity(shipmentKey, quantities);

        doReturn(List.of(trackingAWBMasterEntity)).when(awbDAO).findAwbByShipmentKeys(List.of(shipmentKey));
        doReturn("AV").when(contextUtil).getTenant();
        doNothing().when(authService).authorizeFor("AV");

        //when
        var details = getAwbListFeature.perform(List.of(request));

        //then
        Assertions.assertNotNull(details);
        Assertions.assertEquals("020", details.get(0).getShipmentPrefix());
        Assertions.assertEquals("222222", details.get(0).getMasterDocumentNumber());

        verify(awbListValidator, times(1)).validate(anyList());
        verify(awbDAO).findAwbByShipmentKeys(anyList());
    }
}