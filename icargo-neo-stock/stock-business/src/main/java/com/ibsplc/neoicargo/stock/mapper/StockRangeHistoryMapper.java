package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.dao.entity.StockRangeHistory;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.mapper.common.EntityAbstractMapper;
import com.ibsplc.neoicargo.stock.mapper.common.PageViewAbstractMapper;
import com.ibsplc.neoicargo.stock.model.StockRangeHistoryModel;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    uses = {StockMapper.class, BaseMapper.class})
public interface StockRangeHistoryMapper
    extends EntityAbstractMapper<StockRangeHistory, StockRangeHistoryVO>,
        PageViewAbstractMapper<StockRangeHistoryModel> {

  @Mapping(target = "lastUpdateUser", source = "lastUpdatedUser")
  @Mapping(
      target = "lastUpdateTime",
      source = "lastUpdatedTime",
      qualifiedByName = "convertToZonedDateTime")
  StockRangeHistoryVO mapEntityToVo(StockRangeHistory entity);

  @Mapping(target = "lastUpdatedUser", source = "lastUpdateUser")
  @Mapping(
      target = "lastUpdatedTime",
      source = "lastUpdateTime",
      qualifiedByName = "convertToTimestamp")
  StockRangeHistory mapVoToEntity(StockRangeHistoryVO vo);

  List<StockRangeHistoryModel> mapVoToModel(List<StockRangeHistoryVO> stockRangeHistoryVOs);

  @Mapping(
      target = "transactionDate",
      source = "transactionDate",
      qualifiedByName = "convertZonedDateTimeToCalendar")
  @Mapping(
      target = "startDate",
      source = "startDate",
      qualifiedByName = "convertZonedDateTimeToCalendar")
  @Mapping(
      target = "endDate",
      source = "endDate",
      qualifiedByName = "convertZonedDateTimeToCalendar")
  StockRangeHistoryModel mapVoToModel(StockRangeHistoryVO stockRangeHistoryVO);
}
