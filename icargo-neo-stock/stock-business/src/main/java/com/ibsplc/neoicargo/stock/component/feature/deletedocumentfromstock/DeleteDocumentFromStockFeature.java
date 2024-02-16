package com.ibsplc.neoicargo.stock.component.feature.deletedocumentfromstock;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_024;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_035;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DOC_TYP_AWB;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_NORMAL;
import static com.ibsplc.neoicargo.stock.util.StockConstant.OPERATION_FLAG_UPDATE;
import static com.ibsplc.neoicargo.stock.util.StockConstant.SUBSTRING_COUNT;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
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
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("deletedocumentfromstockfeature")
@RequiredArgsConstructor
public class DeleteDocumentFromStockFeature {

  private final ContextUtil contextUtil;
  private final StockDao stockDao;
  private final SaveStockUtilisationFeature saveStockUtilisationFeature;
  private final StockUtil stockUtil;

  public void perform(DocumentFilterVO documentFilterVO) throws BusinessException {

    String stockHolderCode = null;
    String companyCode = contextUtil.callerLoginProfile().getCompanyCode();
    String documentNumber = documentFilterVO.getDocumentNumber();
    int airlineId = documentFilterVO.getAirlineIdentifier();

    if (Objects.isNull(airlineId) || airlineId == 0) {
      airlineId = stockUtil.findAirlineIdentifier(documentFilterVO.getPrefix());
      documentFilterVO.setAirlineIdentifier(airlineId);
    }

    if (Objects.isNull(documentFilterVO.getDocumentSubType())) {
      String docSubType = null;
      docSubType =
          stockUtil.findSubTypeForDocument(companyCode, airlineId, DOC_TYP_AWB, documentNumber);
      documentFilterVO.setDocumentSubType(docSubType);
    }

    if (documentFilterVO.getStockOwner() == null) {

      stockHolderCode =
          stockUtil.checkAwbExistsInAnyStock(
              companyCode, airlineId, DOC_TYP_AWB, documentNumber, SUBSTRING_COUNT);
      depleteDocumentFromStock(documentFilterVO, stockHolderCode);
    } else {

      int length = 7;
      String documentOwner = null;

      documentOwner =
          stockUtil.checkAwbExistsInAnyStock(
              companyCode, airlineId, DOC_TYP_AWB, documentNumber, length);

      if (DOC_TYP_AWB.equals(documentFilterVO.getDocumentType())) {
        stockHolderCode =
            stockUtil.findStockHolderCodeForAgent(companyCode, documentFilterVO.getStockOwner());
        if (stockHolderCode == null || ("").equals(stockHolderCode.trim())) {
          throw new StockBusinessException(
              constructErrorVO(
                  NEO_STOCK_024.getErrorCode(),
                  NEO_STOCK_024.getErrorMessage(),
                  ERROR,
                  new String[] {documentNumber}));
        }
      } else {
        stockHolderCode = documentFilterVO.getStockOwner();
      }
      if (documentOwner.equals(stockHolderCode)) {
        depleteDocumentFromStock(documentFilterVO, stockHolderCode);
      } else {
        log.error("Document Owner and StockHolder code NOT matching");
        throw new StockBusinessException(
            constructErrorVO(
                NEO_STOCK_035.getErrorCode(),
                NEO_STOCK_035.getErrorMessage(),
                ERROR,
                new String[] {documentNumber, stockHolderCode}));
      }
    }
    log.info("RETURN");
  }

  protected void depleteDocumentFromStock(DocumentFilterVO documentFilterVO, String stockHolderCode)
      throws StockBusinessException {

    String status = OPERATION_FLAG_UPDATE;
    String companyCode = contextUtil.callerLoginProfile().getCompanyCode();

    List<RangeVO> ranges = new ArrayList<>();
    RangeVO rangeVo = new RangeVO();

    StockAllocationVO stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setCompanyCode(companyCode);
    stockAllocationVO.setAirlineIdentifier(documentFilterVO.getAirlineIdentifier());
    stockAllocationVO.setDocumentNumber(documentFilterVO.getDocumentNumber());
    stockAllocationVO.setStockHolderCode(stockHolderCode);
    stockAllocationVO.setDocumentType(DOC_TYP_AWB);
    stockAllocationVO.setDocumentSubType(documentFilterVO.getDocumentSubType());
    stockAllocationVO.setTransferMode(MODE_NORMAL);

    StockHolderVO stockHolderVo = stockDao.findStockHolderDetails(companyCode, stockHolderCode);

    rangeVo.setLastUpdateTime(stockHolderVo.getLastUpdateTime());
    if (DOC_TYP_AWB.equals(documentFilterVO.getDocumentType())) {
      rangeVo.setStartRange(documentFilterVO.getDocumentNumber().substring(0, 7));
      rangeVo.setEndRange(documentFilterVO.getDocumentNumber().substring(0, 7));
    } else {
      rangeVo.setStartRange(documentFilterVO.getDocumentNumber());
      rangeVo.setEndRange(documentFilterVO.getDocumentNumber());
    }

    RangeVO tempRangeVo =
        stockDao.findRangeDelete(
            companyCode,
            DOC_TYP_AWB,
            documentFilterVO.getDocumentSubType(),
            toLong(rangeVo.getStartRange()));

    stockAllocationVO.setManual(tempRangeVo.isManual());
    ranges.add(rangeVo);
    stockAllocationVO.setRanges(ranges);

    stockAllocationVO.setLastUpdateUser(contextUtil.callerLoginProfile().getUserId());
    stockAllocationVO.setLastUpdateTime(ZonedDateTime.now());
    saveStockUtilisationFeature.perform(stockAllocationVO, status);
  }
}
