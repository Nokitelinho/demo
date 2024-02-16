package com.ibsplc.neoicargo.awb.component.feature.getawblist;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ibsplc.neoicargo.awb.component.feature.getawblist.validator.AwbListValidator;
import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.mapper.AwbEntityMapper;
import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.awb.vo.AwbVO;
import com.ibsplc.neoicargo.framework.core.security.spring.oauth2.AuthorizedService;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetAwbListFeature {

    private final AwbDAO awbDAO;
    private final AwbEntityMapper awbEntityMapper;
    private final AwbListValidator awbListValidator;
    private final AuthorizedService authService;
    private final ContextUtil contextUtil;

    public List<AwbVO> perform(List<AwbRequestVO> awbs) {
        awbListValidator.validate(awbs);

        var keys = awbs.stream().map(awb -> new ShipmentKey(awb.getShipmentPrefix(), awb.getMasterDocumentNumber()))
                .collect(Collectors.toList());

        var awbList = awbDAO.findAwbByShipmentKeys(keys);
        
        authService.authorizeFor(contextUtil.getTenant());
        return awbList.stream()
                .map(awbEntityMapper::constructAwbVo)
                .collect(Collectors.toList());
    }
}
