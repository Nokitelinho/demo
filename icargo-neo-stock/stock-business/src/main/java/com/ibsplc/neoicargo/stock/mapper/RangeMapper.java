package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.dao.entity.Range;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.mapper.common.EntityAbstractMapper;
import com.ibsplc.neoicargo.stock.mapper.common.FilterAbstractMapper;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.mapper.common.PageViewAbstractMapper;
import com.ibsplc.neoicargo.stock.model.RangeModel;
import com.ibsplc.neoicargo.stock.util.CalculationUtil;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.filter.RangeFilterVO;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    imports = {CalculationUtil.class, LocalDateTime.class, Timestamp.class},
    uses = {BaseMapper.class})
public interface RangeMapper
    extends ModelAbstractMapper<RangeModel, RangeVO>,
        EntityAbstractMapper<Range, RangeVO>,
        FilterAbstractMapper<RangeModel, RangeVO, RangeFilterVO>,
        PageViewAbstractMapper<RangeModel> {

  @Mapping(target = "txnDat", expression = "java(Timestamp.valueOf(LocalDateTime.now()))")
  @Mapping(
      target = "asciiStartRange",
      expression = "java(CalculationUtil.toLong(vo.getStartRange()))")
  @Mapping(target = "asciiEndRange", expression = "java(CalculationUtil.toLong(vo.getEndRange()))")
  RangeFilterVO mapVoToFilter(RangeVO vo);

  @Mapping(target = "isManual", source = "manual", qualifiedByName = "convertToFlag")
  @Mapping(target = "lastUpdatedUser", source = "lastUpdateUser")
  @Mapping(
      target = "lastUpdatedTime",
      source = "lastUpdateTime",
      qualifiedByName = "convertToTimestamp")
  Range mapVoToEntity(RangeVO vo);

  @Mapping(target = "lastUpdateUser", source = "lastUpdatedUser")
  @Mapping(target = "manual", source = "isManual", qualifiedByName = "convertToBoolean")
  @Mapping(
      target = "lastUpdateTime",
      source = "lastUpdatedTime",
      qualifiedByName = "convertToZonedDateTime")
  RangeVO mapEntityToVo(Range entity);

  List<RangeVO> mapModelToVo(List<RangeModel> models);

  List<RangeModel> mapVoToModel(List<RangeVO> vos);

  Collection<RangeModel> mapModelToVo(Collection<RangeVO> rangeVOs);
}
