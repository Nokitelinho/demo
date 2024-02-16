package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.dao.entity.StockDetails;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.mapper.common.EntityAbstractMapper;
import com.ibsplc.neoicargo.stock.vo.StockDetailsVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    uses = {BaseMapper.class})
public interface StockDetailsMapper extends EntityAbstractMapper<StockDetails, StockDetailsVO> {

  @Mapping(target = "lastUpdatedUser", source = "lastUpdateUser")
  @Mapping(
      target = "txnDateString",
      source = "transactionDate",
      qualifiedByName = "convertZonedDateTimeToInt")
  @Mapping(
      target = "lastUpdatedTime",
      source = "lastUpdateTime",
      qualifiedByName = "convertToTimestamp")
  StockDetails mapVoToEntity(StockDetailsVO vo);

  @Mapping(target = "lastUpdateUser", source = "lastUpdatedUser")
  @Mapping(
      target = "transactionDate",
      source = "txnDateString",
      qualifiedByName = "convertIntToZonedDateTime")
  @Mapping(
      target = "lastUpdateTime",
      source = "lastUpdatedTime",
      qualifiedByName = "convertToZonedDateTime")
  StockDetailsVO mapEntityToVo(StockDetails entity);
}
