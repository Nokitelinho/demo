package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineParametersVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidityDetailsVO;
import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import com.ibsplc.neoicargo.stock.mapper.common.BaseMapper;
import com.ibsplc.neoicargo.stock.model.AirlineParametersModel;
import com.ibsplc.neoicargo.stock.model.AirlineValidationModel;
import com.ibsplc.neoicargo.stock.model.AirlineValidityDetailsModel;
import com.ibsplc.neoicargo.stock.util.CalculationUtil;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = CentralConfig.class,
    builder = @Builder(disableBuilder = true),
    imports = {CalculationUtil.class, LocalDateTime.class, Timestamp.class},
    uses = {BaseMapper.class})
public interface AirlineMapper {

  @Mapping(target = "airlineParameters", source = "airlineParameterVOs")
  @Mapping(target = "airlineValidations", source = "airlineValidationVOs")
  @Mapping(target = "airlineValidityDetails", source = "airlineValidityDetailsVOs")
  AirlineValidationModel mapVoToModel(AirlineValidationVO vo);

  Collection<AirlineValidationModel> mapVoToModelCollection(Collection<AirlineValidationVO> vos);

  AirlineValidityDetailsModel mapVoToModel(AirlineValidityDetailsVO vo);

  Set<AirlineValidityDetailsModel> mapVoToModel(Set<AirlineValidityDetailsVO> vos);

  AirlineParametersModel mapVoToModel(AirlineParametersVO vo);

  Collection<AirlineParametersModel> mapVoToModel(Collection<AirlineParametersVO> vos);

  AirlineValidationModel mapVoToModel(AirlineModel airlineModel);

  @Mapping(target = "validFromDate", source = "validFrom")
  @Mapping(target = "validTillDate", source = "validTo")
  AirlineValidityDetailsModel mapVoToModel(
      com.ibsplc.neoicargo.masters.airline.modal.AirlineValidityDetailsModel vlddtls);
}
