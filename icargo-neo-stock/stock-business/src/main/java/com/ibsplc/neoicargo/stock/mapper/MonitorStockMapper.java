package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.mapper.common.PageViewAbstractMapper;
import com.ibsplc.neoicargo.stock.model.MonitorStockModel;
import com.ibsplc.neoicargo.stock.vo.MonitorStockVO;
import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    uses = {BaseMapper.class})
public interface MonitorStockMapper
    extends ModelAbstractMapper<MonitorStockModel, MonitorStockVO>,
        PageViewAbstractMapper<MonitorStockModel> {

  List<MonitorStockModel> mapVoToModel(List<MonitorStockVO> vos);
}
