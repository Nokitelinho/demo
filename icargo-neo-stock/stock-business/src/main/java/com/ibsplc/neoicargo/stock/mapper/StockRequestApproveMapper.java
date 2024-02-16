package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.mapper.common.ModelAbstractMapper;
import com.ibsplc.neoicargo.stock.model.StockRequestApproveModel;
import com.ibsplc.neoicargo.stock.vo.StockRequestApproveVO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    uses = {BaseMapper.class})
public interface StockRequestApproveMapper
    extends ModelAbstractMapper<StockRequestApproveModel, StockRequestApproveVO> {}
