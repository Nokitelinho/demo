package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.stock.events.AWBCreatedEvent;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.model.viewfilter.DocumentFilterModel;
import com.ibsplc.neoicargo.stock.util.CalculationUtil;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    imports = {CalculationUtil.class, LocalDateTime.class, Timestamp.class},
    uses = {BaseMapper.class})
public interface AwbEventMapper {

  @Mapping(target = "documentNumber", source = "masterDocumentNumber")
  DocumentFilterModel constructAwbCreatedEvent(AWBCreatedEvent awbCreatedEvent);
}
