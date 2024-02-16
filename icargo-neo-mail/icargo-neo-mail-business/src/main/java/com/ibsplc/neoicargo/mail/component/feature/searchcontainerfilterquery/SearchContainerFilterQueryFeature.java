package com.ibsplc.neoicargo.mail.component.feature.searchcontainerfilterquery;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.mail.component.ConsignmentScreeningDetails;
import com.ibsplc.neoicargo.mail.component.feature.savesecuritydetails.invoker.EditScreeningDetailsInvoker;
import com.ibsplc.neoicargo.mail.component.feature.savesecuritydetails.persistors.SecurityDetailPersistor;
import com.ibsplc.neoicargo.mail.dao.SearchContainerFilterQueryBuilder;
import com.ibsplc.neoicargo.mail.vo.ConsignmentScreeningVO;
import com.ibsplc.neoicargo.mail.vo.ContainerVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.SearchContainerFilterVO;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component("mail.operations.searchContainerFilterQueryFeature")
@FeatureConfigSource("feature/searchcontainerfilterquery")
public class SearchContainerFilterQueryFeature extends AbstractFeature<SearchContainerFilterVO> {
    @Override
    public  SearchContainerFilterVO  perform(SearchContainerFilterVO searchContainerFilterVO) throws BusinessException {
       log.debug("SearchContainerFilterQueryFeature");
       return searchContainerFilterVO;
    }
}
