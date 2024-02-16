package com.ibsplc.neoicargo.stock.component.feature.createhistory.enricher;

import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_ALLOCATE;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_ALLOCATE_TRANSIT;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_BLACKLIST;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_CREATE;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_DELETED;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_REOPENED;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_RETURN;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_RETURN_TRANSIT;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_REVOKE;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_TRANSFER;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_TRANSFER_TRANSIT;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_USED;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_VOID;

import com.ibsplc.neoicargo.stock.dao.StockRangeHistoryDao;
import com.ibsplc.neoicargo.stock.dao.entity.StockAgent;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockRangeHistoryEnricher {

  /**
   * {@link Map} collection that contains {@link String} as key and {@link BiConsumer} as value. The
   * key is the status in {@link com.ibsplc.neoicargo.stock.model.StockAllocationStatus} The value
   * is the action we need to take when matching {@link StockAllocationVO} to {@link
   * StockRangeHistoryVO}.
   */
  private Map<String, BiConsumer<StockAllocationVO, StockRangeHistoryVO>> actionMap;

  private final StockRangeHistoryDao stockRangeHistoryDao;

  @PostConstruct
  public void init() {
    actionMap = new HashMap<>();
    for (String status :
        List.of(MODE_BLACKLIST, MODE_CREATE, MODE_REVOKE, MODE_VOID, MODE_DELETED)) {
      actionMap.put(
          status,
          (stockAllocationVO, stockRangeHistoryVO) ->
              stockRangeHistoryVO.setFromStockHolderCode(stockAllocationVO.getStockHolderCode()));
    }

    for (String status :
        List.of(
            MODE_ALLOCATE,
            MODE_TRANSFER,
            MODE_RETURN,
            MODE_RETURN_TRANSIT,
            MODE_TRANSFER_TRANSIT,
            MODE_ALLOCATE_TRANSIT)) {
      actionMap.put(
          status,
          (stockAllocationVO, stockRangeHistoryVO) -> {
            stockRangeHistoryVO.setToStockHolderCode(stockAllocationVO.getStockHolderCode());
            stockRangeHistoryVO.setFromStockHolderCode(stockAllocationVO.getStockControlFor());
          });
    }

    actionMap.put(
        MODE_USED,
        (stockAllocationVO, stockRangeHistoryVO) -> {
          if (stockAllocationVO.getStockControlFor() != null) {
            stockRangeHistoryVO.setFromStockHolderCode(stockAllocationVO.getStockControlFor());
          }
        });

    actionMap.put(
        MODE_REOPENED,
        (stockAllocationVO, stockRangeHistoryVO) -> {
          if (stockAllocationVO.getStockControlFor() != null) {
            String stockHolderCode;
            StockAgent stockAgent =
                stockRangeHistoryDao.findByCompanyCodeAndAgentCode(
                    stockAllocationVO.getCompanyCode(), stockAllocationVO.getStockControlFor());

            if (stockAgent != null) {
              stockHolderCode = stockAgent.getStockHolderCode();
              stockRangeHistoryVO.setToStockHolderCode(stockHolderCode);
              stockRangeHistoryVO.setFromStockHolderCode(stockHolderCode);
            }
          }
        });
  }

  public void enrich(
      String status, StockAllocationVO stockAllocationVO, StockRangeHistoryVO stockRangeHistoryVO) {
    BiConsumer<StockAllocationVO, StockRangeHistoryVO> action = actionMap.get(status);
    if (action == null) {
      log.warn("Invalid status value");
    } else {
      action.accept(stockAllocationVO, stockRangeHistoryVO);
    }
  }
}
