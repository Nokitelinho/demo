package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.dao.entity.StockRequestOAL;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.mapper.common.EntityAbstractMapper;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.model.StockRequestOALModel;
import com.ibsplc.neoicargo.stock.vo.StockRequestOALVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    uses = {BaseMapper.class})
public interface StockRequestOALMapper
    extends EntityAbstractMapper<StockRequestOAL, StockRequestOALVO>,
        ModelAbstractMapper<StockRequestOALModel, StockRequestOALVO> {

  @Mapping(
      target = "isRequestCompleted",
      source = "requestCompleted",
      qualifiedByName = "convertToFlag")
  @Mapping(target = "lastUpdatedUser", source = "lastUpdateUser")
  @Mapping(
      target = "lastUpdatedTime",
      source = "lastUpdateTime",
      qualifiedByName = "convertToTimestamp")
  StockRequestOAL mapVoToEntity(StockRequestOALVO vo);

  @Mapping(
      target = "requestCompleted",
      source = "isRequestCompleted",
      qualifiedByName = "convertToBoolean")
  @Mapping(target = "lastUpdateUser", source = "lastUpdatedUser")
  @Mapping(
      target = "lastUpdateTime",
      source = "lastUpdatedTime",
      qualifiedByName = "convertToZonedDateTime")
  StockRequestOALVO mapEntityToVo(StockRequestOAL entity);

  @Mapping(target = "airlineIdentifier", source = "airlineId")
  StockRequestOALVO mapModelToVo(StockRequestOALModel model);
}
