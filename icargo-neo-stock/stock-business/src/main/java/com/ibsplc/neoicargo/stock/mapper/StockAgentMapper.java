package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.dao.entity.StockAgent;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.mapper.common.EntityAbstractMapper;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.mapper.common.PageViewAbstractMapper;
import com.ibsplc.neoicargo.stock.model.StockAgentModel;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    uses = {BaseMapper.class})
public interface StockAgentMapper
    extends EntityAbstractMapper<StockAgent, StockAgentVO>,
        ModelAbstractMapper<StockAgentModel, StockAgentVO>,
        PageViewAbstractMapper<StockAgentModel> {

  @Mapping(target = "lastUpdatedUser", source = "lastUpdateUser")
  @Mapping(
      target = "lastUpdatedTime",
      source = "lastUpdateTime",
      qualifiedByName = "convertToTimestamp")
  StockAgent mapVoToEntity(StockAgentVO vo);

  @Mapping(target = "lastUpdateUser", source = "lastUpdatedUser")
  @Mapping(
      target = "lastUpdateTime",
      source = "lastUpdatedTime",
      qualifiedByName = "convertToZonedDateTime")
  StockAgentVO mapEntityToVo(StockAgent entity);

  List<StockAgentVO> mapModelToVo(List<StockAgentModel> stockAgentModels);

  List<StockAgentModel> mapVoToModel(List<StockAgentVO> stockAgentVOs);

  @Mapping(target = "operationFlag", defaultValue = "U")
  StockAgentModel mapVoToModel(StockAgentVO vo);
}
