package com.ibsplc.neoicargo.stock.dao.impl;

import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;

import com.ibsplc.neoicargo.stock.dao.UtilisationDao;
import com.ibsplc.neoicargo.stock.dao.mybatis.UtilisationQueryMapper;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.filter.RangeFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.UtilisationFilterVO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("utilisationDao")
@RequiredArgsConstructor
public class UtilisationDaoImpl implements UtilisationDao {

  private final UtilisationQueryMapper queryMapper;

  @Override
  public long findStockUtilisationForRange(UtilisationFilterVO utilisationFilterVO) {
    return queryMapper.findStockUtilisationForRange(utilisationFilterVO);
  }

  @Override
  public List<Long> validateStockPeriod(
      StockAllocationVO stockAllocationVO, int stockIntroductionPeriod) {
    final var currentDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

    final var rangeFilterVO =
        stockAllocationVO.getRanges().stream()
            .map(
                rangeVO ->
                    RangeFilterVO.builder()
                        .asciiStartRange(toLong(rangeVO.getStartRange()))
                        .asciiEndRange(toLong(rangeVO.getEndRange()))
                        .build())
            .collect(Collectors.toList());

    final var utilisationFilter =
        UtilisationFilterVO.builder()
            .companyCode(stockAllocationVO.getCompanyCode())
            .airlineIdentifier(stockAllocationVO.getAirlineIdentifier())
            .documentType(stockAllocationVO.getDocumentType())
            .stockIntroductionPeriod(stockIntroductionPeriod)
            .currentDate(currentDate)
            .ranges(rangeFilterVO)
            .build();

    return queryMapper.validateStockPeriod(utilisationFilter);
  }
}
