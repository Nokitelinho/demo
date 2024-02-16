package com.ibsplc.neoicargo.stock.util;

import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import java.util.Collection;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class LoggerUtil {

  public static final String RANGE_FORMAT =
      "\n"
          + "companyCode: %s,\n"
          + " stockHolderCode: %s,\n"
          + " airlineIdentifier: %d,\n"
          + " documentType: %s,\n"
          + " documentSubtype: %s,\n"
          + " startRange: %s\n"
          + " endRange: %s\n"
          + " asciiStartRange: %d\n"
          + " asciiEndRange: %d";
  public static final String STOCK_FORMAT =
      "\n"
          + "companyCode: %s,\n"
          + " stockHolderCode: %s,\n"
          + " airlineIdentifier: %d,\n"
          + " documentType: %s,\n"
          + " documentSubtype: %s";

  public static void logRanges(Collection<RangeVO> rangeVOS) {
    rangeVOS.stream()
        .map(
            rangeVO ->
                String.format(
                    RANGE_FORMAT,
                    rangeVO.getCompanyCode(),
                    rangeVO.getStockHolderCode(),
                    rangeVO.getAirlineIdentifier(),
                    rangeVO.getDocumentType(),
                    rangeVO.getDocumentSubType(),
                    rangeVO.getStartRange(),
                    rangeVO.getEndRange(),
                    rangeVO.getAsciiStartRange(),
                    rangeVO.getAsciiEndRange()))
        .forEach(log::info);
  }

  public static void logStocks(List<StockVO> stockVOS) {
    stockVOS.forEach(
        stockVO -> {
          log.info(
              String.format(
                  STOCK_FORMAT,
                  stockVO.getCompanyCode(),
                  stockVO.getStockHolderCode(),
                  stockVO.getAirlineIdentifier(),
                  stockVO.getDocumentType(),
                  stockVO.getDocumentSubType()));
          logRanges(stockVO.getRanges());
        });
  }
}
