package com.ibsplc.neoicargo.stock.component.feature.findcustomerstockdetailsfeature;

import static com.ibsplc.neoicargo.framework.core.lang.error.ErrorType.ERROR;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_004;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;

import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import com.ibsplc.neoicargo.masters.customer.CustomerBusinessException;
import com.ibsplc.neoicargo.masters.customer.CustomerComponent;
import com.ibsplc.neoicargo.masters.customer.modal.CustomerModel;
import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import com.ibsplc.neoicargo.stock.vo.StockDetailsVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockDetailsFilterVO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component("findCustomerStockDetailsFeature")
@Slf4j
@RequiredArgsConstructor
public class FindCustomerStockDetailsFeature {

  private final StockDao stockDao;
  private final RangeDao rangeDao;
  private final AirlineWebComponent airlineWebComponent;
  private final CustomerComponent customerComponent;

  public StockDetailsVO perform(StockDetailsFilterVO stockDetailsFilterVO)
      throws StockBusinessException {
    String stockHolderCode =
        findStockHolderForCustomer(
            stockDetailsFilterVO.getCompanyCode(), stockDetailsFilterVO.getCustomerCode());
    if (stockHolderCode == null || ("").equals(stockHolderCode.trim())) {
      log.info("---------INVALID_STOCKHOLDERFORAGENTDETAILS - STOCKHOLDERCODE IS NULL-------");
      throw new StockBusinessException(constructErrorVO(NEO_STOCK_004, ERROR));
    }
    stockDetailsFilterVO.setStockHolderCode(stockHolderCode);

    StockHolderVO stockHolderVO =
        stockDao.findStockHolderDetails(
            stockDetailsFilterVO.getCompanyCode(), stockDetailsFilterVO.getStockHolderCode());
    if (Objects.isNull(stockHolderVO)) {
      log.info("---------STOCKHOLDER IS NULL---------");
      throw new StockBusinessException(constructErrorVO(NEO_STOCK_004, ERROR));
    }
    return findCustomerStockDetails(stockDetailsFilterVO);
  }

  public String findStockHolderForCustomer(String companyCode, String customerCode)
      throws StockBusinessException {
    StockAgentVO stockAgentVO = null;
    var customerModel = new CustomerModel();
    customerModel.setCustomerCode(customerCode);

    // calling validateAgent from CustomerWebApi from shared-masters module

    CustomerModel cusValues;
    try {
      cusValues = customerComponent.validateAgent(customerModel);
    } catch (CustomerBusinessException e) {
      log.error("CustomerBusinessException", e);
      return null;
    }

    if (Objects.nonNull(cusValues)) {
      stockAgentVO = stockDao.findStockAgent(companyCode, cusValues.getAgentCode());
    }
    return Objects.nonNull(stockAgentVO) ? stockAgentVO.getStockHolderCode() : null;
  }

  public StockDetailsVO findCustomerStockDetails(StockDetailsFilterVO stockDetailsFilterVO) {
    var stockDetailsVO = stockDao.findCustomerStockDetails(stockDetailsFilterVO);

    if (stockDetailsVO != null) {

      List<RangeVO> availableRngs = rangeDao.findAvailableRangesForCustomer(stockDetailsFilterVO);
      List<RangeVO> allocatedRngs = rangeDao.findAllocatedRangesForCustomer(stockDetailsFilterVO);
      List<RangeVO> usedRngs = rangeDao.findUsedRangesForCustomer(stockDetailsFilterVO);

      setThreeNumCod(availableRngs);
      setThreeNumCod(allocatedRngs);
      setThreeNumCod(usedRngs);

      List<RangeVO> newUsedRngs = checkTxnDat(usedRngs);

      int availableSize = availableRngs.size();
      int allocatedSize = allocatedRngs.size();
      int usedSize = newUsedRngs.size();

      int result =
          (availableSize >= allocatedSize && availableSize >= usedSize)
              ? 0
              : (allocatedSize >= availableSize && allocatedSize >= usedSize)
                  ? 1
                  : (usedSize >= availableSize && usedSize >= allocatedSize) ? 2 : 100;

      Map<Integer, List<RangeVO>> rangeMap = new HashMap<Integer, List<RangeVO>>();

      rangeMap.put(0, availableRngs);
      rangeMap.put(1, allocatedRngs);
      rangeMap.put(2, newUsedRngs);

      stockDetailsVO.setCustomerRanges(rangeMap.get(result));
      List<RangeVO> customerRangeVOs = (List<RangeVO>) stockDetailsVO.getCustomerRanges();
      if (customerRangeVOs != null) {
        int i = 0, count = 0;
        while (i <= 2) {
          if (i != result) {
            for (RangeVO range : rangeMap.get(i)) {
              RangeVO finalRange = customerRangeVOs.get(count);
              if (i == 0) {
                finalRange.setAvlStartRange(range.getAvlStartRange());
                finalRange.setAvlEndRange(range.getAvlEndRange());
                finalRange.setAvlNumberOfDocuments(range.getAvlNumberOfDocuments());
                finalRange.setAvailableRange(range.getAvailableRange());
              }
              if (i == 1) {
                finalRange.setAllocStartRange(range.getAllocStartRange());
                finalRange.setAllocEndRange(range.getAllocEndRange());
                finalRange.setAllocNumberOfDocuments(range.getAllocNumberOfDocuments());
                finalRange.setFromStockHolderCode(range.getFromStockHolderCode());
                finalRange.setAllocatedRange(range.getAllocatedRange());
              }
              if (i == 2) {
                finalRange.setUsedStartRange(range.getUsedStartRange());
                finalRange.setUsedEndRange(range.getUsedEndRange());
                finalRange.setUsedNumberOfDocuments(range.getUsedNumberOfDocuments());
                finalRange.setUsedRange(range.getUsedRange());
              }
              count++;
            }
          }
          i++;
        }
        stockDetailsVO.setCustomerRanges(customerRangeVOs);
      }
    }
    return stockDetailsVO;
  }

  public Map<Integer, String> constructMap(List<RangeVO> rangeVOs) {
    List<AirlineModel> airlineModelList = findAirlineModels(rangeVOs);
    return airlineModelList.stream()
        .collect(
            Collectors.toMap(
                AirlineModel::getAirlineIdentifier,
                k -> k.getAirlineValidityDetails().iterator().next().getThreeNumberCode()));
  }

  public List<AirlineModel> findAirlineModels(List<RangeVO> rangeVOs) {
    var arlModelsFilter =
        rangeVOs.stream()
            .distinct()
            .map(
                rangeVO -> {
                  var arlMdl = new AirlineModel();
                  arlMdl.setAirlineIdentifier(rangeVO.getAirlineIdentifier());
                  return arlMdl;
                })
            .collect(Collectors.toList());
    if (CollectionUtils.isNotEmpty(arlModelsFilter)) {
      return airlineWebComponent.findAirlineValidityDetails(arlModelsFilter);
    }
    return Collections.emptyList();
  }

  public void setThreeNumCod(List<RangeVO> rangeVOs) {
    if (CollectionUtils.isNotEmpty(rangeVOs)) {
      Map<Integer, String> airlineDetailsMap = constructMap(rangeVOs);
      rangeVOs.stream()
          .forEach(
              rangeVO -> {
                var thrnumcod = airlineDetailsMap.get(rangeVO.getAirlineIdentifier());
                if (StringUtils.isNotBlank(thrnumcod)) {
                  rangeVO.setAwbPrefix(thrnumcod);
                }
              });
    }
  }

  public List<RangeVO> checkTxnDat(List<RangeVO> usedRngs) {
    List<RangeVO> newUsedRngsList = new ArrayList<RangeVO>();
    if (CollectionUtils.isNotEmpty(usedRngs)) {
      List<AirlineModel> airlineModelList = findAirlineModels(usedRngs);
      usedRngs.stream()
          .forEach(
              rangeVO -> {
                Optional<AirlineModel> airline =
                    airlineModelList.stream()
                        .filter(
                            airlineModel ->
                                (rangeVO.getAirlineIdentifier()
                                        == airlineModel.getAirlineIdentifier()
                                    && rangeVO
                                        .getTransactionDate()
                                        .isAfter(
                                            LocalDate.parse(
                                                airlineModel
                                                    .getAirlineValidityDetails()
                                                    .iterator()
                                                    .next()
                                                    .getValidFrom()))
                                    && rangeVO
                                        .getTransactionDate()
                                        .isBefore(
                                            LocalDate.parse(
                                                airlineModel
                                                    .getAirlineValidityDetails()
                                                    .iterator()
                                                    .next()
                                                    .getValidTo()))))
                        .findFirst();
                if (airline.isPresent()) {
                  newUsedRngsList.add(rangeVO);
                }
              });
    }
    return newUsedRngsList;
  }
}
