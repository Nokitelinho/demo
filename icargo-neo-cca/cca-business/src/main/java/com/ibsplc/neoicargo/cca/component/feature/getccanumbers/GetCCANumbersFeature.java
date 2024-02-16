package com.ibsplc.neoicargo.cca.component.feature.getccanumbers;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CcaNumbersEdge;
import com.ibsplc.neoicargo.cca.modal.CcaSelectFilter;
import com.ibsplc.neoicargo.cca.modal.CcaNumbersPage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class GetCCANumbersFeature {

    private final CcaDao ccaDao;
    private final CcaMasterMapper ccaMasterMapper;

    public CcaNumbersPage perform(final CcaSelectFilter ccaSelectFilter, final String companyCode) {
        return new CcaNumbersPage(
                ccaMasterMapper.constructCcaNumbersData(
                        ccaDao.getCCANumbers(ccaSelectFilter, companyCode)
                )
                        .stream()
                        .map(CcaNumbersEdge::new)
                        .collect(Collectors.toList())
        );
    }
}
