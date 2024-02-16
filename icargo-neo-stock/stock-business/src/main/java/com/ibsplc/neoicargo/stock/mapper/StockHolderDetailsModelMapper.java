package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.mapper.common.PageViewAbstractMapper;
import com.ibsplc.neoicargo.stock.model.StockHolderDetailsModel;
import com.ibsplc.neoicargo.stock.vo.StockHolderDetailsVO;
import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    uses = {BaseMapper.class})
public interface StockHolderDetailsModelMapper
    extends ModelAbstractMapper<StockHolderDetailsModel, StockHolderDetailsVO>,
        PageViewAbstractMapper<StockHolderDetailsModel> {
  List<StockHolderDetailsModel> mapVoToModel(List<StockHolderDetailsVO> vos);
}
