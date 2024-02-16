package com.ibsplc.neoicargo.stock.component.feature.savestockrequestdetails.persistor;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_003;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_007;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_011;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.component.feature.savestockrequestdetails.invoker.AllocateStockInvoker;
import com.ibsplc.neoicargo.stock.component.feature.savestockrequestdetails.invoker.ApproveStockRequestInvoker;
import com.ibsplc.neoicargo.stock.component.feature.savestockrequestdetails.invoker.FindRangesInvoker;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.dao.entity.Stock;
import com.ibsplc.neoicargo.stock.dao.entity.StockRequest;
import com.ibsplc.neoicargo.stock.dao.repository.StockRepository;
import com.ibsplc.neoicargo.stock.dao.repository.StockRequestRepository;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.mapper.StockRequestMapper;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("stockRequestDetailsPersistor")
@RequiredArgsConstructor
public class StockRequestDetailsPersistor {

  private final StockDao stockDao;
  private final StockRepository stockRepository;
  private final FindRangesInvoker findRangesInvoker;
  private final StockRequestMapper stockRequestMapper;
  private final AllocateStockInvoker allocateStockInvoker;
  private final StockRequestRepository stockRequestRepository;
  private final ApproveStockRequestInvoker approveStockRequestInvoker;

  public String save(StockRequestVO stockRequestVO) throws BusinessException {
    var stock =
        findStock(
            stockRequestVO.getCompanyCode(),
            stockRequestVO.getStockHolderCode(),
            Integer.parseInt(stockRequestVO.getAirlineIdentifier()),
            stockRequestVO.getDocumentType(),
            stockRequestVO.getDocumentSubType());

    if (stockRequestVO.getRequestRefNumber() == null
        || stockRequestVO.getRequestRefNumber().trim().isEmpty()) {
      stockRequestVO.setRequestRefNumber(
          stockRequestVO.getStockHolderCode() + stockRequestRepository.getNextValOfReqRefNumber());
    } else {
      stockRequestVO.setRequestRefNumber(
          stockRequestVO.getStockHolderCode() + stockRequestVO.getRequestRefNumber());
    }

    var stockRequest = stockRequestMapper.mapVoToEntity(stockRequestVO);
    stockRequest.setStock(stock);
    stockRequestRepository.save(stockRequest);
    if (stockDao.findAutoProcessingQuantityAvailable(
            stockRequestVO.getCompanyCode(), stockRequestVO.getStockHolderCode(),
            stockRequestVO.getDocumentType(), stockRequestVO.getDocumentSubType())
        != null) {
      autoProcessingAllocateStock(stockRequestVO);
    }
    log.info(
        "StockRequest saved by companyCode:{}, requestRefNumber:{}, airlineIdentifier:{}",
        stockRequestVO.getCompanyCode(),
        stockRequestVO.getRequestRefNumber(),
        stockRequestVO.getAirlineIdentifier());
    return stockRequest.getRequestRefNumber();
  }

  public String update(StockRequestVO stockRequestVO) throws BusinessException {
    var stockRequest =
        findStockRequest(stockRequestVO.getCompanyCode(), stockRequestVO.getRequestRefNumber());
    stockRequestMapper.update(stockRequestVO, stockRequest);
    if (stockRequestVO.getStockHolderType() != null
        || stockRequestVO.getStockHolderCode() != null) {
      var stock =
          findStock(
              stockRequest.getCompanyCode(),
              stockRequest.getStockHolderCode(),
              stockRequest.getAirlineIdentifier(),
              stockRequest.getDocumentType(),
              stockRequest.getDocumentSubType());
      stockRequest.setStock(stock);
    }
    log.info(
        "StockRequest updated by companyCode:{}, requestRefNumber:{}, airlineIdentifier:{}",
        stockRequestVO.getCompanyCode(),
        stockRequestVO.getRequestRefNumber(),
        stockRequestVO.getAirlineIdentifier());
    return stockRequest.getRequestRefNumber();
  }

  private void autoProcessingAllocateStock(StockRequestVO stockRequestVO) throws BusinessException {
    log.info("autoProcessingAllocateStock");
    var stockHolderDetails =
        findStockHolder(stockRequestVO.getCompanyCode(), stockRequestVO.getStockHolderCode());
    for (StockVO stockVO : stockHolderDetails.getStock()) {
      if (isExists(stockRequestVO, stockVO)) {
        approveStockRequestInvoker.invoke(stockRequestVO, stockVO);
        allocateStock(stockRequestVO, stockVO);
      }
    }
  }

  private boolean isExists(StockRequestVO stockRequestVO, StockVO stockVO) {
    return (stockRequestVO.getDocumentType().equalsIgnoreCase(stockVO.getDocumentType())
            && (stockRequestVO.getDocumentSubType().equalsIgnoreCase(stockVO.getDocumentSubType())))
        && (stockVO.getAutoprocessQuantity() > 0);
  }

  private void allocateStock(StockRequestVO stockRequestVO, StockVO stockVO)
      throws BusinessException {
    var rangeVOS = findRangesInvoker.invoke(stockRequestVO, stockVO);
    if (!rangeVOS.isEmpty()) {
      allocateStockInvoker.invoke(stockRequestVO, stockVO, rangeVOS);
    }
  }

  private StockRequest findStockRequest(String companyCode, String requestRefNumber)
      throws BusinessException {
    final var stockRequest =
        stockRequestRepository
            .findByCompanyCodeAndRequestRefNumber(companyCode, requestRefNumber)
            .orElse(null);
    if (stockRequest == null) {
      throw new StockBusinessException(constructErrorVO(NEO_STOCK_007, ERROR));
    }
    return stockRequest;
  }

  private StockHolderVO findStockHolder(String companyCode, String stockHolderCode)
      throws StockBusinessException {
    var stockHolderDetails = stockDao.findStockHolderDetails(companyCode, stockHolderCode);
    if (stockHolderDetails == null) {
      throw new StockBusinessException(constructErrorVO(NEO_STOCK_011, ERROR));
    }
    return stockHolderDetails;
  }

  private Stock findStock(
      String companyCode,
      String stockHolderCode,
      Integer airlineId,
      String docType,
      String docSubType)
      throws StockBusinessException {
    var possibleStock =
        stockRepository
            .findByCompanyCodeAndStockHolderCodeAndAirlineIdentifierAndDocumentTypeAndDocumentSubType(
                companyCode, stockHolderCode, airlineId, docType, docSubType);
    if (possibleStock.isPresent()) {
      return possibleStock.get();
    }
    throw new StockBusinessException(
        constructErrorVO(NEO_STOCK_003.getErrorCode(), NEO_STOCK_003.getErrorMessage(), ERROR));
  }
}
