package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.model.viewfilter.RangeFilterModel;
import com.ibsplc.neoicargo.stock.vo.filter.RangeFilterVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    uses = {BaseMapper.class})
public interface RangeFilterMapper extends ModelAbstractMapper<RangeFilterModel, RangeFilterVO> {

  @Mapping(target = "manualFlag", source = "manual", qualifiedByName = "convertToFlag")
  RangeFilterVO mapModelToVo(RangeFilterModel model);
}
