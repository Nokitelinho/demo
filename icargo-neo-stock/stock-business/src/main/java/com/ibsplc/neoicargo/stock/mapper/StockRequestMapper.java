package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.dao.entity.StockRequest;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.mapper.common.EntityAbstractMapper;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.mapper.common.PageViewAbstractMapper;
import com.ibsplc.neoicargo.stock.model.StockRequestModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockRequestFilterModel;
import com.ibsplc.neoicargo.stock.vo.StockRequestFilterVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    uses = {RangeMapper.class, BaseMapper.class})
public interface StockRequestMapper
    extends ModelAbstractMapper<StockRequestModel, StockRequestVO>,
        PageViewAbstractMapper<StockRequestModel>,
        EntityAbstractMapper<StockRequest, StockRequestVO> {

  @Mapping(target = "isManual", source = "manual", qualifiedByName = "convertToFlag")
  @Mapping(target = "statuses", source = "status", qualifiedByName = "toArray")
  @Mapping(target = "levelValues", source = "privilegeLevelValue", qualifiedByName = "toArray")
  StockRequestFilterVO mapModelToVo(StockRequestFilterModel model);

  List<StockRequestModel> mapVoToModel(List<StockRequestVO> vos);

  @Mapping(target = "manual", source = "isManual", qualifiedByName = "convertToBoolean")
  StockRequestModel mapVoToModel(StockRequestVO vo);

  List<StockRequestVO> mapEntityToVo(List<StockRequest> entities);

  List<StockRequestModel> mapVoToModel(Page<StockRequestVO> stockRequestVO);

  List<StockRequestVO> mapModelsToVos(List<StockRequestModel> model);

  @Mapping(target = "isManual", source = "manual", qualifiedByName = "convertToFlag")
  StockRequestVO mapModelToVo(StockRequestModel model);

  @Mapping(target = "lastUpdatedUser", source = "lastUpdateUser")
  @Mapping(
      target = "lastUpdatedTime",
      source = "lastUpdateDate",
      qualifiedByName = "convertToTimestamp")
  StockRequest mapVoToEntity(StockRequestVO vo);

  @Mapping(target = "lastUpdatedUser", source = "lastUpdateUser")
  @Mapping(target = "lastUpdatedTime", ignore = true)
  @Mapping(target = "stockRequestSerialNumber", ignore = true)
  void update(StockRequestVO stockRequestVO, @MappingTarget StockRequest stockRequest);
}
