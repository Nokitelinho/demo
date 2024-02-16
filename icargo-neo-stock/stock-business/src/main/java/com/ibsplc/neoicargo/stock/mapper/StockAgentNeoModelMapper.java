package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.model.StockAgentNeoModel;
import com.ibsplc.neoicargo.stock.vo.StockAgentVO;
import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    uses = {BaseMapper.class})
public interface StockAgentNeoModelMapper
    extends ModelAbstractMapper<StockAgentNeoModel, StockAgentVO> {

  List<StockAgentNeoModel> mapVoToModel(List<StockAgentVO> stockAgentVOs);

  @Mapping(target = "operationFlag", defaultValue = "U")
  StockAgentNeoModel mapVoToModel(StockAgentVO vo);
}
