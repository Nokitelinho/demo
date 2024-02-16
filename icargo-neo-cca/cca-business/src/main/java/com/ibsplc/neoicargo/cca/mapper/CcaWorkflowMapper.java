package com.ibsplc.neoicargo.cca.mapper;

import com.ibsplc.neoicargo.cca.modal.CCAWorkflowData;
import com.ibsplc.neoicargo.cca.vo.CCAWorkflowVO;
import com.ibsplc.neoicargo.common.converters.CentralConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CentralConfig.class)
public interface CcaWorkflowMapper {

    @Mapping(target = "requestedDate", source = "requestedDate", dateFormat = "dd-MM-yyyy HH:mm:ss")
    CCAWorkflowData constructCCAWorkflowVO(CCAWorkflowVO cCAWorkflowVO);
}
