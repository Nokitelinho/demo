package com.ibsplc.neoicargo.stock.component.feature.findranges;

import static com.ibsplc.neoicargo.stock.util.CalculationUtil.findNumberOfDocuments;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.subtract;
import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DOCUMENT_TYPE;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.filter.RangeFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("findRangesFeature")
@RequiredArgsConstructor
public class FindRangesFeature {
  private final RangeDao rangeDao;

  public List<RangeVO> perform(RangeFilterVO rangeFilterVO) {
    var rangeVOs = rangeDao.findRanges(rangeFilterVO);

    return findRanges(rangeVOs, rangeFilterVO);
  }

  public List<RangeVO> findRanges(List<RangeVO> rangeVOs, RangeFilterVO rangeFilterVO) {

    enrichRanges(rangeVOs, rangeFilterVO);

    rangeVOs = enrichNumberDocuments(rangeVOs, rangeFilterVO);

    enrichMasterDocumentNumber(rangeVOs, rangeFilterVO);

    return rangeVOs;
  }

  private void enrichMasterDocumentNumber(List<RangeVO> rangeVOs, RangeFilterVO rangeFilterVO) {
    var availableRanges = getStockRanges(rangeFilterVO);
    for (RangeVO rangeVO : rangeVOs) {
      for (RangeVO range : availableRanges) {
        if (DOCUMENT_TYPE.equals(rangeFilterVO.getDocumentType())) {
          if ((range.getStartRange().substring(0, 7)).equals(rangeVO.getStartRange())) {
            rangeVO.setMasterDocumentNumbers(range.getMasterDocumentNumbers());
          }
        } else {
          if ((range.getStartRange()).equals(rangeVO.getStartRange())) {
            rangeVO.setMasterDocumentNumbers(range.getMasterDocumentNumbers());
          }
        }
      }
    }
  }

  private List<RangeVO> getStockRanges(RangeFilterVO rangeFilterVO) {
    var stockFilterVO =
        StockFilterVO.builder()
            .companyCode(rangeFilterVO.getCompanyCode())
            .airlineIdentifier(rangeFilterVO.getAirlineIdentifier())
            .documentType(rangeFilterVO.getDocumentType())
            .documentSubType(rangeFilterVO.getDocumentSubType())
            .stockHolderCode(rangeFilterVO.getStockHolderCode())
            .manualFlag(rangeFilterVO.getManualFlag())
            .build();

    return rangeDao.findRangesForViewRange(stockFilterVO);
  }

  private static List<RangeVO> enrichNumberDocuments(
      List<RangeVO> rangeVOs, RangeFilterVO rangeFilterVO) {
    long sum = 0;
    if (isNotEmpty(rangeFilterVO.getNumberOfDocuments())) {
      long noOfDocs = toLong(rangeFilterVO.getNumberOfDocuments());
      List<RangeVO> newRangeVOs = new ArrayList<>();
      for (RangeVO rangeVO : rangeVOs) {
        if (sum == noOfDocs) {
          break;
        }
        sum += rangeVO.getNumberOfDocuments();
        if (sum > noOfDocs) {
          rangeVO.setEndRange(subtract(rangeVO.getEndRange(), sum - noOfDocs));
          rangeVO.setNumberOfDocuments(
              findNumberOfDocuments(rangeVO.getEndRange(), rangeVO.getStartRange()));
          newRangeVOs.add(rangeVO);
          break;
        }
        newRangeVOs.add(rangeVO);
      }
      rangeVOs = newRangeVOs;
    }
    return rangeVOs;
  }

  private static void enrichRanges(List<RangeVO> rangeVOs, RangeFilterVO rangeFilterVO) {
    if (isNotEmpty(rangeFilterVO.getStartRange())) {
      long startRange = toLong(rangeFilterVO.getStartRange());
      for (RangeVO rangeVO : rangeVOs) {
        if (startRange > toLong(rangeVO.getStartRange())) {
          rangeVO.setStartRange(rangeFilterVO.getStartRange());
          rangeVO.setNumberOfDocuments(
              findNumberOfDocuments(rangeVO.getEndRange(), rangeVO.getStartRange()));
        }
        if (isNotEmpty(rangeFilterVO.getEndRange())) {
          long endRange = toLong(rangeFilterVO.getEndRange());
          if (endRange < toLong(rangeVO.getEndRange())) {
            rangeVO.setEndRange(rangeFilterVO.getEndRange());
            rangeVO.setNumberOfDocuments(
                findNumberOfDocuments(rangeVO.getEndRange(), rangeVO.getStartRange()));
          }
        }
      }
    }
  }
}
