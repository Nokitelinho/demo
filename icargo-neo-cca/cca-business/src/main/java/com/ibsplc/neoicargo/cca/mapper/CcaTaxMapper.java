package com.ibsplc.neoicargo.cca.mapper;

import com.ibsplc.neoicargo.cca.dao.entity.CcaAwbTaxDetails;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaTaxDetailsVO;
import com.ibsplc.neoicargo.cca.vo.TaxFilterVO;
import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CARGOTYPE_CARGO;
import static java.util.Objects.nonNull;

@Mapper(config = CentralConfig.class)
public interface CcaTaxMapper {

    @Mapping(target = "taxConfigurationDetails", ignore = true)
    @Mapping(target = "parameterMap", ignore = true)
    @Mapping(target = "chargeDetailsMap", ignore = true)
    @Mapping(target = "countryCode", ignore = true)
    @Mapping(target = "companyCode", expression = "java(contextUtil.callerLoginProfile().getCompanyCode())")
    @Mapping(target = "cargoType", constant = CARGOTYPE_CARGO)
    @Mapping(target = "dateOfJourney", expression = "java(getDateOfJourney(ccaAwbVO))")
    @Mapping(target = "currencyCode", source = "currency")
    TaxFilterVO fromCcaAwbVOToTaxFilterVO(CcaAwbVO ccaAwbVO, @Context ContextUtil contextUtil);

    default String getDateOfJourney(CcaAwbVO ccaAwbVO) {
        var formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        if (nonNull(ccaAwbVO.getExecutionDate())) {
            return ccaAwbVO.getExecutionDate().format(formatter);
        } else {
            if (nonNull(ccaAwbVO.getCaptureDate())) {
                return ccaAwbVO.getCaptureDate().format(formatter);
            } else {
                return LocalDateTime.now().format(formatter);
            }
        }
    }

    CcaAwbTaxDetails fromCcaAwbVOToCcaAwbTaxDetails(CcaTaxDetailsVO ccaTaxDetailsVO);
}