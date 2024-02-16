
package com.ibsplc.neoicargo.lh.mail.component.feature.searchcontainerfilterquery.enrichers;



import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.lh.mail.dao.LHSearchContainerFilterQueryBuilder;
import com.ibsplc.neoicargo.mail.vo.SearchContainerFilterVO;
import org.springframework.stereotype.Component;


@Component("searchContainerFilterQueryEnricherLH")
public class SearchContainerFilterQueryEnricherLH extends Enricher<SearchContainerFilterVO> {




	@Override
	public void enrich(SearchContainerFilterVO searchContainerFilterVO) throws BusinessException {

		LHSearchContainerFilterQueryBuilder lhSearchContainerFilterQuery = new LHSearchContainerFilterQueryBuilder();
		searchContainerFilterVO.setPgqry(( lhSearchContainerFilterQuery.withBaseQuery(searchContainerFilterVO.getBaseQuery()).withIsOracleDataSource(searchContainerFilterVO.isOracleDataSource())
				.withSearchContainerFilterVO(searchContainerFilterVO).build()));
	}
}