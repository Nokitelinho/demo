package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.model.StockHolderPriorityModel;
import com.ibsplc.neoicargo.stock.vo.StockHolderPriorityVO;
import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    uses = {BaseMapper.class})
public interface StockHolderPriorityMapper
    extends ModelAbstractMapper<StockHolderPriorityModel, StockHolderPriorityVO> {

  List<StockHolderPriorityModel> mapVoToModel(List<StockHolderPriorityVO> vos);
}
