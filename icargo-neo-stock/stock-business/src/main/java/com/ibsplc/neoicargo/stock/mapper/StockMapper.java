package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.dao.entity.Stock;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.mapper.common.EntityAbstractMapper;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.model.StockModel;
import com.ibsplc.neoicargo.stock.util.CalculationUtil;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import org.mapstruct.Builder;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    imports = {CalculationUtil.class},
    uses = {RangeMapper.class, BaseMapper.class})
public interface StockMapper
    extends ModelAbstractMapper<StockModel, StockVO>, EntityAbstractMapper<Stock, StockVO> {

  @Mapping(target = "ranges", ignore = true)
  StockModel mapVoToModel(StockVO vo);

  @Named(value = "skipRange")
  @Mapping(target = "ranges", ignore = true)
  @Mapping(target = "lastUpdateUser", source = "lastUpdatedUser")
  @Mapping(
      target = "lastUpdateTime",
      source = "lastUpdatedTime",
      qualifiedByName = "convertToZonedDateTime")
  @Mapping(
      target = "reorderAlertFlag",
      source = "reorderAlertFlag",
      qualifiedByName = "convertToBoolean")
  @Mapping(
      target = "autoRequestFlag",
      source = "autoRequestFlag",
      qualifiedByName = "convertToBoolean")
  @Mapping(
      target = "autoPopulateFlag",
      source = "autoPopulateFlag",
      qualifiedByName = "convertToBoolean")
  StockVO mapEntityToVo(Stock entity);

  @Mapping(target = "lastUpdateUser", source = "lastUpdatedUser")
  @Mapping(
      target = "lastUpdateTime",
      source = "lastUpdatedTime",
      qualifiedByName = "convertToZonedDateTime")
  @Mapping(
      target = "reorderAlertFlag",
      source = "reorderAlertFlag",
      qualifiedByName = "convertToBoolean")
  @Mapping(
      target = "autoRequestFlag",
      source = "autoRequestFlag",
      qualifiedByName = "convertToBoolean")
  @Mapping(
      target = "autoPopulateFlag",
      source = "autoPopulateFlag",
      qualifiedByName = "convertToBoolean")
  StockVO mapEntityToVoWithRanges(Stock entity);

  @Mapping(target = "lastUpdatedUser", source = "lastUpdateUser")
  @Mapping(target = "stockApproverCompany", source = "companyCode")
  @Mapping(
      target = "lastUpdatedTime",
      source = "lastUpdateTime",
      qualifiedByName = "convertToTimestamp")
  @Mapping(
      target = "reorderAlertFlag",
      source = "reorderAlertFlag",
      qualifiedByName = "convertToFlag")
  @Mapping(
      target = "autoRequestFlag",
      source = "autoRequestFlag",
      qualifiedByName = "convertToFlag")
  @Mapping(
      target = "autoPopulateFlag",
      source = "autoPopulateFlag",
      qualifiedByName = "convertToFlag")
  Stock mapVoToEntity(StockVO vo);

  @Mapping(
      target = "reorderAlertFlag",
      source = "reorderAlertFlag",
      qualifiedByName = "convertToFlag")
  @Mapping(
      target = "autoRequestFlag",
      source = "autoRequestFlag",
      qualifiedByName = "convertToFlag")
  @Mapping(
      target = "autoPopulateFlag",
      source = "autoPopulateFlag",
      qualifiedByName = "convertToFlag")
  @Mapping(target = "stockSerialNumber", ignore = true)
  @Mapping(target = "ranges", ignore = true)
  void update(StockVO stockVO, @MappingTarget Stock stock);
}
