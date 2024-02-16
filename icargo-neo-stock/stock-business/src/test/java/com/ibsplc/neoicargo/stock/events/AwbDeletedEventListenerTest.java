package com.ibsplc.neoicargo.stock.events;

import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.stock.service.StockService;
import com.ibsplc.neoicargo.stock.util.StockUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class AwbDeletedEventListenerTest {

  @InjectMocks private AwbDeletedEventListener awbDeletedEventListener;
  @Mock StockUtil stockUtil;
  @Mock ContextUtil contextUtil;
  @Mock private LoginProfile loginProfile;
  @Mock StockService stockService;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void shouldHandleAwbDeletedEvent() throws BusinessException {
    var awbData = new AWBDeletedEvent();
    awbData.setShipmentPrefix("777");
    awbData.setMasterDocumentNumber("12345678");

    doReturn(loginProfile).when(contextUtil).callerLoginProfile();
    //    doReturn(1767).when(stockUtil).findAirlineIdentifier(any(String.class));
    //    doReturn("S")
    //        .when(stockUtil)
    //        .findSubTypeForDocument(
    //            any(String.class), any(Integer.class), any(String.class), any(String.class));
    //    doReturn("STKHLD")
    //        .when(stockUtil)
    //        .checkAwbExistsInAnyStock(
    //            any(String.class),
    //            any(Integer.class),
    //            any(String.class),
    //            any(String.class),
    //            any(Integer.class));
    //    doNothing().when(stockService).returnDocumentToStock(any(StockAllocationModel.class));
    Assertions.assertDoesNotThrow(() -> awbDeletedEventListener.handleAwbEvent(awbData));
  }
}
