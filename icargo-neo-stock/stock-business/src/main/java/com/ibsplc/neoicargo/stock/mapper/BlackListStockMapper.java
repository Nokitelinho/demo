package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.dao.entity.BlackListStock;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.mapper.common.EntityAbstractMapper;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.mapper.common.PageViewAbstractMapper;
import com.ibsplc.neoicargo.stock.model.BlacklistStockModel;
import com.ibsplc.neoicargo.stock.util.CalculationUtil;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    uses = {BaseMapper.class, TransitStockMapper.class},
    imports = {CalculationUtil.class})
public interface BlackListStockMapper
    extends ModelAbstractMapper<BlacklistStockModel, BlacklistStockVO>,
        EntityAbstractMapper<BlackListStock, BlacklistStockVO>,
        PageViewAbstractMapper<BlacklistStockModel> {

  @Mapping(target = "firstLevelStockHolderCode", source = "firstLevelStockHolder")
  @Mapping(target = "lastUpdatedUser", source = "lastUpdateUser")
  @Mapping(
      target = "lastUpdatedTime",
      source = "lastUpdateTime",
      qualifiedByName = "convertToTimestamp")
  @Mapping(target = "isManual", source = "manual", qualifiedByName = "convertToFlag")
  @Mapping(
      target = "asciiStartRange",
      expression = "java(CalculationUtil.toLong(vo.getRangeFrom()))")
  @Mapping(target = "asciiEndRange", expression = "java(CalculationUtil.toLong(vo.getRangeTo()))")
  @Mapping(target = "startRange", source = "rangeFrom")
  @Mapping(target = "endRange", source = "rangeTo")
  BlackListStock mapVoToEntity(BlacklistStockVO vo);

  BlacklistStockVO clone(BlacklistStockVO vo);

  BlacklistStockModel mapVoToModel(BlacklistStockVO vo);

  List<BlacklistStockModel> mapVoToModel(List<BlacklistStockVO> vos);
}
