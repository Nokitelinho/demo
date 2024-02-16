package com.ibsplc.neoicargo.cca.component.feature.getccalistview;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CcaListViewPageInfo;
import com.ibsplc.neoicargo.cca.vo.CCAListViewFilterVO;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
@FeatureConfigSource("cca/getccalistview")
public class GetCCAListViewFeature extends AbstractFeature<CCAListViewFilterVO> {

    private final CcaDao ccaDao;
    private final CcaMasterMapper ccaMasterMapper;

    public CcaListViewPageInfo perform(CCAListViewFilterVO ccaListViewFilterData) {
        log.info("Invoked GetCCAListView feature");
        final var pageable = PageRequest.of(ccaListViewFilterData.getPage() - 1, ccaListViewFilterData.getSize());
        final var maxResult = pageable.getPageSize();
        final var firstResult = pageable.getPageNumber() * pageable.getPageSize();

        final var ccaListViewVO = ccaDao.findCcaListViewVO(ccaListViewFilterData, pageable, maxResult, firstResult);
        final var totalPages = ccaListViewVO.getTotalPages();
        final var totalElements = ccaListViewVO.getTotalElements();
        final var ccaListViewModals = ccaMasterMapper.mapCcaListViewVOToModal(ccaListViewVO, totalPages, totalElements);
        return ccaMasterMapper.mapCcaListViewVOToPageInfo(ccaListViewModals, totalPages, totalElements);
    }

}