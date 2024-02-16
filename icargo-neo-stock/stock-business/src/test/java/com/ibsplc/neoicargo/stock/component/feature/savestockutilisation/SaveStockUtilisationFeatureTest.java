package com.ibsplc.neoicargo.stock.component.feature.savestockutilisation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditUtils;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditVO;
import com.ibsplc.neoicargo.framework.tenant.audit.builder.AuditConfig;
import com.ibsplc.neoicargo.stock.component.feature.savestockutilisation.persistor.StockUtilisationPersistor;
import com.ibsplc.neoicargo.stock.dao.entity.StockRangeUtilisation;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class SaveStockUtilisationFeatureTest {
  @InjectMocks private SaveStockUtilisationFeature saveStockUtilisationFeature;

  @Mock private ContextUtil contextUtil;

  @Mock private StockUtilisationPersistor stockUtilisationPersistor;

  @Mock private AuditUtils<AuditVO> auditUtils;

  private LoginProfile loginProfile;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    loginProfile = new LoginProfile();
    loginProfile.setCompanyCode("CO");
    loginProfile.setAirportCode("FRA");
    loginProfile.setStationCode("TRV");
  }

  @Test
  void shouldSaveStockUtilisation() {
    // Given
    var status = "A";

    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1000000");
    rangeVO.setEndRange("2000000");
    rangeVO.setMasterDocumentNumbers(new ArrayList<>());

    List<RangeVO> ranges = List.of(rangeVO);

    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setRanges(ranges);

    // When
    doReturn(new StockRangeUtilisation())
        .when(stockUtilisationPersistor)
        .find(any(StockAllocationVO.class), anyString());
    doReturn(loginProfile).when(contextUtil).callerLoginProfile();
    doNothing().when(auditUtils).performAudit(any(AuditConfig.class));

    // Then
    saveStockUtilisationFeature.perform(stockAllocationVO, status);
  }

  @Test
  void shouldNotSaveStockUtilisation() {
    // Given
    var status = "A";
    var documentNumber = "1234567";
    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1000000");
    rangeVO.setEndRange("2000000");
    rangeVO.setMasterDocumentNumbers(new ArrayList<>());

    List<RangeVO> ranges = List.of(rangeVO);

    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setRanges(ranges);
    stockAllocationVO.setCompanyCode("IBS");
    stockAllocationVO.setStockHolderCode("HQ");
    stockAllocationVO.setDocumentType("AWB");
    stockAllocationVO.setDocumentSubType("S");
    stockAllocationVO.setAirlineIdentifier(1777);

    // When
    doReturn(loginProfile).when(contextUtil).callerLoginProfile();
    doNothing().when(auditUtils).performAudit(any(AuditConfig.class));
    doReturn(null).when(stockUtilisationPersistor).find(any(StockAllocationVO.class), anyString());

    // Then
    saveStockUtilisationFeature.perform(stockAllocationVO, status);
  }

  @Test
  void shouldUpdateStockUtilisationFeature() {
    // Given
    var status = "B";

    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1000000");
    rangeVO.setEndRange("2000000");
    rangeVO.setMasterDocumentNumbers(new ArrayList<>());

    List<RangeVO> ranges = List.of(rangeVO);

    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setRanges(ranges);

    doReturn(loginProfile).when(contextUtil).callerLoginProfile();
    doNothing().when(auditUtils).performAudit(any(AuditConfig.class));

    // Then
    saveStockUtilisationFeature.perform(stockAllocationVO, status);
  }
}
