package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.dao.entity.StockHolder;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.mapper.common.EntityAbstractMapper;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.model.StockHolderModel;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import org.mapstruct.Builder;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    uses = {StockMapper.class, BaseMapper.class})
public interface StockHolderMapper
    extends ModelAbstractMapper<StockHolderModel, StockHolderVO>,
        EntityAbstractMapper<StockHolder, StockHolderVO> {

  @Mapping(target = "stock", source = "stock", qualifiedByName = "skipRange")
  @Mapping(target = "lastUpdateUser", source = "lastUpdatedUser")
  @Mapping(
      target = "lastUpdateTime",
      source = "lastUpdatedTime",
      qualifiedByName = "convertToZonedDateTime")
  StockHolderVO mapEntityToVo(StockHolder entity);

  @Mapping(target = "lastUpdatedUser", source = "lastUpdateUser")
  @Mapping(
      target = "lastUpdatedTime",
      source = "lastUpdateTime",
      qualifiedByName = "convertToTimestamp")
  StockHolder mapVoToEntity(StockHolderVO vo);
}
