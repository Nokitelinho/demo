package com.ibsplc.neoicargo.stock.util;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_003;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_023;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DOC_TYP_AWB;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STRING_START;
import static com.ibsplc.neoicargo.stock.util.StockConstant.SUBSTRING_COUNT;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.dao.entity.StockAgent;
import com.ibsplc.neoicargo.stock.dao.repository.StockAgentRepository;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockUtil {

  private final StockDao stockDao;
  private final StockAgentRepository stockAgentRepository;
  private final AirlineWebComponent airlineWebComponent;

  public String findSubTypeForDocument(
      String companyCode, int airlineId, String documentType, String documentNumber) {
    long mstDocNumber = 0;
    if (DOC_TYP_AWB.equals(documentType)) {
      mstDocNumber = toLong(documentNumber.substring(STRING_START, SUBSTRING_COUNT));
    } else {
      mstDocNumber = toLong(documentNumber);
    }
    RangeVO rangeVO =
        stockDao.findStockRangeDetails(companyCode, airlineId, documentType, mstDocNumber);
    return Objects.nonNull(rangeVO) ? rangeVO.getDocumentSubType() : null;
  }

  public String checkAwbExistsInAnyStock(
      String companyCode,
      int airlineId,
      String documentType,
      String documentNumber,
      int lengthofFormat)
      throws StockBusinessException {
    long mstDocNumber = 0;
    if (DOC_TYP_AWB.equals(documentType) && documentNumber.length() >= lengthofFormat) {
      mstDocNumber = toLong(documentNumber.substring(STRING_START, lengthofFormat));
    } else if ("ORDINO".equals(documentType) && documentNumber.length() >= lengthofFormat) {
      mstDocNumber = toLong(documentNumber.substring(STRING_START, lengthofFormat));
    } else {
      mstDocNumber = toLong(documentNumber);
    }
    String stockHolderCode =
        checkDocumentExistsInAnyStock(companyCode, airlineId, documentType, mstDocNumber);
    if (StringUtils.isBlank(stockHolderCode)) {
      if (DOC_TYP_AWB.equals(documentType)) {
        throw new StockBusinessException(
            constructErrorVO(
                NEO_STOCK_023.getErrorCode(),
                NEO_STOCK_023.getErrorMessage(),
                ERROR,
                new String[] {documentNumber}));
      } else {
        throw new StockBusinessException(
            constructErrorVO(
                NEO_STOCK_003.getErrorCode(),
                NEO_STOCK_003.getErrorMessage(),
                ERROR,
                new String[] {documentNumber}));
      }
    }
    return stockHolderCode;
  }

  protected String checkDocumentExistsInAnyStock(
      String companyCode, int airlineId, String documentType, long mstDocNumber) {
    RangeVO rangeVO =
        stockDao.findStockRangeDetails(companyCode, airlineId, documentType, mstDocNumber);
    return Objects.nonNull(rangeVO) ? rangeVO.getStockHolderCode() : null;
  }

  public String findStockHolderCodeForAgent(String companyCode, String agentCode) {
    Optional<StockAgent> stkAgentMapping =
        stockAgentRepository.findByCompanyCodeAndAgentCode(companyCode, agentCode);
    if (stkAgentMapping.isPresent()) {
      return stkAgentMapping.get().getStockHolderCode();
    }
    return null;
  }

  public int findAirlineIdentifier(String prefix) throws BusinessException {
    var airlineModel = airlineWebComponent.validateNumericCode(prefix);
    if (Objects.nonNull(airlineModel)) {
      return airlineModel.getAirlineIdentifier();
    }
    return 0;
  }
}
