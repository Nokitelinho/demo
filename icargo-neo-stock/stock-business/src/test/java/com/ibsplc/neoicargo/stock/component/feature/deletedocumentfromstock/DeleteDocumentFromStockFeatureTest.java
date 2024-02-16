package com.ibsplc.neoicargo.stock.component.feature.deletedocumentfromstock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.stock.component.feature.savestockutilisation.SaveStockUtilisationFeature;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.util.StockUtil;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

@RunWith(JUnitPlatform.class)
public class DeleteDocumentFromStockFeatureTest {

  @InjectMocks @Spy DeleteDocumentFromStockFeature deleteDocumentFromStockFeature;

  @Mock private ContextUtil contextUtil;
  @Mock private LoginProfile loginProfile;
  @Mock private StockDao stockDao;
  @Mock private StockUtil stockUtil;
  @Mock private SaveStockUtilisationFeature saveStockUtilisationFeature;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    doReturn(loginProfile).when(contextUtil).callerLoginProfile();
    doReturn("IBS").when(loginProfile).getCompanyCode();
    doReturn("ICOADMIN").when(loginProfile).getUserId();
  }

  @Test
  public void findAirlineIdentifierIfNotPresent() throws BusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentNumber("docno");
    documentFilterVO.setDocumentSubType("S");
    documentFilterVO.setDocumentType("AWB");
    documentFilterVO.setPrefix("pre");
    when(stockUtil.findAirlineIdentifier(Mockito.anyString())).thenReturn(12345);
    when(stockUtil.checkAwbExistsInAnyStock(
            Mockito.anyString(),
            Mockito.anyInt(),
            Mockito.anyString(),
            Mockito.anyString(),
            Mockito.anyInt()))
        .thenReturn("HQ");
    doNothing()
        .when(deleteDocumentFromStockFeature)
        .depleteDocumentFromStock(Mockito.any(DocumentFilterVO.class), Mockito.anyString());
    deleteDocumentFromStockFeature.perform(documentFilterVO);
    Mockito.verify(stockUtil, times(1)).findAirlineIdentifier(Mockito.anyString());
  }

  @Test
  public void findDocumentSubtypeIfNotPresent() throws BusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentNumber("docno");
    documentFilterVO.setAirlineIdentifier(12345);
    documentFilterVO.setDocumentType("AWB");
    documentFilterVO.setPrefix("pre");
    when(stockUtil.findSubTypeForDocument(
            Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString()))
        .thenReturn("S");
    when(stockUtil.checkAwbExistsInAnyStock(
            Mockito.anyString(),
            Mockito.anyInt(),
            Mockito.anyString(),
            Mockito.anyString(),
            Mockito.anyInt()))
        .thenReturn("HQ");
    doNothing()
        .when(deleteDocumentFromStockFeature)
        .depleteDocumentFromStock(Mockito.any(DocumentFilterVO.class), Mockito.anyString());
    deleteDocumentFromStockFeature.perform(documentFilterVO);
    Mockito.verify(stockUtil, times(1))
        .findSubTypeForDocument(
            Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString());
  }

  @Test
  public void findStockHolderCodeIfStockOwnerIsNull() throws BusinessException {
    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setDocumentNumber("docno");
    documentFilterVO.setAirlineIdentifier(12345);
    documentFilterVO.setDocumentSubType("S");
    documentFilterVO.setDocumentType("XYZ");

    when(stockUtil.checkAwbExistsInAnyStock(
            Mockito.anyString(),
            Mockito.anyInt(),
            Mockito.anyString(),
            Mockito.anyString(),
            Mockito.anyInt()))
        .thenReturn("HQ");
    doNothing()
        .when(deleteDocumentFromStockFeature)
        .depleteDocumentFromStock(Mockito.any(DocumentFilterVO.class), Mockito.anyString());
    deleteDocumentFromStockFeature.perform(documentFilterVO);

    Mockito.verify(stockUtil, times(1))
        .checkAwbExistsInAnyStock(
            Mockito.anyString(),
            Mockito.anyInt(),
            Mockito.anyString(),
            Mockito.anyString(),
            Mockito.anyInt());
  }

  @Test
  public void findStockHolderCodeIfStockOwnerIsPresent() throws BusinessException {

    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setAirlineIdentifier(12345);
    documentFilterVO.setDocumentSubType("S");
    documentFilterVO.setStockOwner("HQ");
    documentFilterVO.setDocumentNumber("docno");
    documentFilterVO.setDocumentType("XYZ");

    when(stockUtil.checkAwbExistsInAnyStock(
            Mockito.anyString(),
            Mockito.anyInt(),
            Mockito.anyString(),
            Mockito.anyString(),
            Mockito.anyInt()))
        .thenReturn("HQ");

    doNothing()
        .when(deleteDocumentFromStockFeature)
        .depleteDocumentFromStock(Mockito.any(DocumentFilterVO.class), Mockito.anyString());
    deleteDocumentFromStockFeature.perform(documentFilterVO);
    Mockito.verify(stockUtil, times(1))
        .checkAwbExistsInAnyStock(
            Mockito.anyString(),
            Mockito.anyInt(),
            Mockito.anyString(),
            Mockito.anyString(),
            Mockito.anyInt());
  }

  @Test
  public void findStockHolderCodeForAgentWhenDocumentTypeIsAWB() throws BusinessException {

    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setAirlineIdentifier(12345);
    documentFilterVO.setDocumentSubType("S");
    documentFilterVO.setStockOwner("HQ");
    documentFilterVO.setDocumentNumber("docno");
    documentFilterVO.setDocumentType("AWB");

    when(stockUtil.checkAwbExistsInAnyStock(
            Mockito.anyString(),
            Mockito.anyInt(),
            Mockito.anyString(),
            Mockito.anyString(),
            Mockito.anyInt()))
        .thenReturn("HQ");

    when(stockUtil.findStockHolderCodeForAgent(Mockito.anyString(), Mockito.anyString()))
        .thenReturn("HQ");

    doNothing()
        .when(deleteDocumentFromStockFeature)
        .depleteDocumentFromStock(Mockito.any(DocumentFilterVO.class), Mockito.anyString());
    deleteDocumentFromStockFeature.perform(documentFilterVO);
    Mockito.verify(stockUtil, times(1))
        .findStockHolderCodeForAgent(Mockito.anyString(), Mockito.anyString());
  }

  @Test
  public void throwStockBusinessExceptionWhenFindStockHolderCodeForAgentIsNull()
      throws BusinessException {

    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setAirlineIdentifier(12345);
    documentFilterVO.setDocumentSubType("S");
    documentFilterVO.setStockOwner("HQ");
    documentFilterVO.setDocumentNumber("docno");
    documentFilterVO.setDocumentType("AWB");

    when(stockUtil.checkAwbExistsInAnyStock(
            Mockito.anyString(),
            Mockito.anyInt(),
            Mockito.anyString(),
            Mockito.anyString(),
            Mockito.anyInt()))
        .thenReturn("HQ");

    when(stockUtil.findStockHolderCodeForAgent(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(null);

    assertThrows(
        StockBusinessException.class,
        () -> deleteDocumentFromStockFeature.perform(documentFilterVO));
  }

  @Test
  public void throwStockBusinessExceptionWhenFindStockHolderCodeForAgentIsEmpty()
      throws BusinessException {

    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setAirlineIdentifier(12345);
    documentFilterVO.setDocumentSubType("S");
    documentFilterVO.setStockOwner("HQ");
    documentFilterVO.setDocumentNumber("docno");
    documentFilterVO.setDocumentType("AWB");

    when(stockUtil.checkAwbExistsInAnyStock(
            Mockito.anyString(),
            Mockito.anyInt(),
            Mockito.anyString(),
            Mockito.anyString(),
            Mockito.anyInt()))
        .thenReturn("HQ");

    when(stockUtil.findStockHolderCodeForAgent(Mockito.anyString(), Mockito.anyString()))
        .thenReturn("");

    assertThrows(
        StockBusinessException.class,
        () -> deleteDocumentFromStockFeature.perform(documentFilterVO));
  }

  @Test
  public void throwStockBusinessExceptionWhenDocumentNumberAndStockHolderIsDifferent()
      throws BusinessException {

    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setAirlineIdentifier(12345);
    documentFilterVO.setDocumentSubType("S");
    documentFilterVO.setStockOwner("HQ");
    documentFilterVO.setDocumentNumber("docno");
    documentFilterVO.setDocumentType("AWB");

    when(stockUtil.checkAwbExistsInAnyStock(
            Mockito.anyString(),
            Mockito.anyInt(),
            Mockito.anyString(),
            Mockito.anyString(),
            Mockito.anyInt()))
        .thenReturn("HQ");

    when(stockUtil.findStockHolderCodeForAgent(Mockito.anyString(), Mockito.anyString()))
        .thenReturn("PQ");

    assertThrows(
        StockBusinessException.class,
        () -> deleteDocumentFromStockFeature.perform(documentFilterVO));
  }

  @Test
  public void shouldDepleteDocumentFromStockSuccess() throws BusinessException {

    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setAirlineIdentifier(12345);
    documentFilterVO.setDocumentNumber("1234567");
    documentFilterVO.setDocumentType("AWB");
    documentFilterVO.setDocumentSubType("S");

    List<RangeVO> ranges = new ArrayList<>();
    RangeVO rangeVo = new RangeVO();
    rangeVo.setStartRange("1234567");

    StockHolderVO stockHolderVO = new StockHolderVO();
    stockHolderVO.setLastUpdateTime(ZonedDateTime.now());

    when(stockDao.findStockHolderDetails(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(stockHolderVO);

    RangeVO tempRangeVo = new RangeVO();
    when(stockDao.findRangeDelete(
            Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong()))
        .thenReturn(tempRangeVo);
    tempRangeVo.setManual(true);

    doNothing()
        .when(saveStockUtilisationFeature)
        .perform(Mockito.any(StockAllocationVO.class), Mockito.anyString());

    deleteDocumentFromStockFeature.depleteDocumentFromStock(documentFilterVO, "HQ");
    Mockito.verify(saveStockUtilisationFeature, times(1))
        .perform(Mockito.any(StockAllocationVO.class), Mockito.anyString());
  }

  @Test
  public void shouldDepleteDocumentFromStockSuccessWhenDocumentTypeIsNotAWB()
      throws BusinessException {

    DocumentFilterVO documentFilterVO = new DocumentFilterVO();
    documentFilterVO.setAirlineIdentifier(12345);
    documentFilterVO.setDocumentNumber("1234567");
    documentFilterVO.setDocumentType("XYZ");
    documentFilterVO.setDocumentSubType("S");

    List<RangeVO> ranges = new ArrayList<>();
    RangeVO rangeVo = new RangeVO();
    rangeVo.setStartRange("1234567");

    StockHolderVO stockHolderVO = new StockHolderVO();
    stockHolderVO.setLastUpdateTime(ZonedDateTime.now());

    when(stockDao.findStockHolderDetails(Mockito.anyString(), Mockito.anyString()))
        .thenReturn(stockHolderVO);

    RangeVO tempRangeVo = new RangeVO();
    when(stockDao.findRangeDelete(
            Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong()))
        .thenReturn(tempRangeVo);
    tempRangeVo.setManual(true);

    doNothing()
        .when(saveStockUtilisationFeature)
        .perform(Mockito.any(StockAllocationVO.class), Mockito.anyString());

    deleteDocumentFromStockFeature.depleteDocumentFromStock(documentFilterVO, "HQ");
    Mockito.verify(saveStockUtilisationFeature, times(1))
        .perform(Mockito.any(StockAllocationVO.class), Mockito.anyString());
  }
}
