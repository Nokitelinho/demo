package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.model.MissingStockModel;
import com.ibsplc.neoicargo.stock.vo.MissingStockVO;
import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    uses = {BaseMapper.class})
public interface MissingStockMapper extends ModelAbstractMapper<MissingStockModel, MissingStockVO> {
  MissingStockVO clone(MissingStockVO vo);

  List<MissingStockVO> clone(List<MissingStockVO> vos);
}
