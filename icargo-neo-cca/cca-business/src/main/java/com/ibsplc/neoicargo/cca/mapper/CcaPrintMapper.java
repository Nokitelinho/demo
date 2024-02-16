package com.ibsplc.neoicargo.cca.mapper;

import com.ibsplc.neoicargo.cca.modal.CCAPrintFilterModel;
import com.ibsplc.neoicargo.cca.modal.CCAPrintModel;
import com.ibsplc.neoicargo.cca.vo.CCAPrintVO;
import com.ibsplc.neoicargo.cca.vo.filter.CCAPrintFilterVO;
import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.framework.util.currency.mapper.MoneyMapper;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import org.mapstruct.Mapper;

@Mapper(config = CentralConfig.class, uses = {QuantityMapper.class, MoneyMapper.class})
public interface CcaPrintMapper {

    CCAPrintFilterVO constructCCAPrintVO(CCAPrintFilterModel reportFilterModel);

    CCAPrintModel constructCCAPrintModel(CCAPrintVO reportVO);

}
