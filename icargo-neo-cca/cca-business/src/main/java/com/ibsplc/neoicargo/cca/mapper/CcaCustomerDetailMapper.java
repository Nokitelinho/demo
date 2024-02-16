package com.ibsplc.neoicargo.cca.mapper;

import com.ibsplc.neoicargo.cca.constants.CustomerType;
import com.ibsplc.neoicargo.cca.dao.entity.CCACustomerDetail;
import com.ibsplc.neoicargo.cca.modal.CcaCustomerDetailData;
import com.ibsplc.neoicargo.cca.vo.CcaCustomerDetailVO;
import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.masters.customer.modal.CustomerModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CentralConfig.class, imports = {CustomerType.class})
public interface CcaCustomerDetailMapper {

    @Mapping(target = "cassIndicator", expression = "java(ccaCustomerDetailVO.getCustomerType().equals(CustomerType.O) ? ccaCustomerDetailVO.getCassIndicator() : null)")
    CcaCustomerDetailData constructCcaCustomerDetailData(CcaCustomerDetailVO ccaCustomerDetailVO);

    @Mapping(target = "cassIndicator", expression = "java(ccaCustomerDetailVO.getCustomerType().equals(CustomerType.O) ? ccaCustomerDetailVO.getCassIndicator() : null)")
    CCACustomerDetail constructCcaCustomerDetail(CcaCustomerDetailVO ccaCustomerDetailVO);

    @Mapping(target = "customerType", ignore = true)
    @Mapping(target = "customerTypeCode", source = "customerType")
    @Mapping(target = "cassIndicator", source = "cassIdentifier")
    CcaCustomerDetailVO constructCcaCustomerDetailVo(CustomerModel customerModel);

}
