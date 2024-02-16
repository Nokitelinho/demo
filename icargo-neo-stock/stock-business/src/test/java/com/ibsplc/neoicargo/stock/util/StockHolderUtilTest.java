package com.ibsplc.neoicargo.stock.util;

import static com.ibsplc.neoicargo.stock.util.StockConstant.DOC_TYP_AWB;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.stock.dao.entity.Range;
import com.ibsplc.neoicargo.stock.dao.entity.Stock;
import com.ibsplc.neoicargo.stock.dao.entity.StockHolder;
import com.ibsplc.neoicargo.stock.dao.repository.StockHolderRepository;
import com.ibsplc.neoicargo.stock.mapper.RangeMapper;
import com.ibsplc.neoicargo.stock.mapper.StockHolderMapper;
import com.ibsplc.neoicargo.stock.mapper.StockMapper;
import com.ibsplc.neoicargo.stock.model.enums.StockHolderType;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(JUnitPlatform.class)
public class StockHolderUtilTest {

  @InjectMocks private StockHolderUtil stockHolderUtil;
  @Mock private StockHolderRepository repository;
  @Spy private StockHolderMapper stockHolderMapper = Mappers.getMapper(StockHolderMapper.class);
  @Spy private StockMapper stockMapper = Mappers.getMapper(StockMapper.class);
  @Spy private RangeMapper rangeMapper = Mappers.getMapper(RangeMapper.class);

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    ReflectionTestUtils.setField(stockMapper, "rangeMapper", rangeMapper);
    ReflectionTestUtils.setField(stockHolderMapper, "stockMapper", stockMapper);
  }

  @Test
  void shouldFindStockHolderDetails() {
    StockHolder stockHolder = new StockHolder();
    Stock stock = new Stock();
    Range range = new Range();
    range.setAsciiEndRange(4554548);
    range.setAsciiStartRange(4554543);
    stock.setDocumentType(DOC_TYP_AWB);
    stock.setAirlineIdentifier(1134);
    stockHolder.setStockHolderType(StockHolderType.A);
    stockHolder.setStockHolderCode("HQ");
    stockHolder.setCompanyCode("IBS");
    stockHolder.setLastUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
    stock.setLastUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
    range.setLastUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
    stock.setRanges(new HashSet<>());
    stock.getRanges().add(range);
    stockHolder.setStock(new HashSet<>());
    stockHolder.getStock().add(stock);

    doReturn(Optional.ofNullable(stockHolder))
        .when(repository)
        .findByCompanyCodeAndStockHolderCode(any(String.class), any(String.class));
    assertDoesNotThrow(() -> stockHolderUtil.findStockHolderDetails("IBS", "HQ"));
  }

  @Test
  void shouldNotFindStockHolderDetails() {
    doReturn(Optional.ofNullable(null))
        .when(repository)
        .findByCompanyCodeAndStockHolderCode(any(String.class), any(String.class));
    assertDoesNotThrow(() -> stockHolderUtil.findStockHolderDetails("IBS", "HQ"));
  }
}
