

package com.ibsplc.neoicargo.lh.mail.dao;

import com.ibsplc.neoicargo.mail.dao.ContainerMapperForDestination;
import com.ibsplc.neoicargo.mail.dao.SearchContainerFilterQuery;
import com.ibsplc.neoicargo.mail.dao.SearchContainerMultiMapper;
import com.ibsplc.neoicargo.mail.vo.SearchContainerFilterVO;

public class LHSearchContainerFilterQuery extends SearchContainerFilterQuery {
	
	
	public LHSearchContainerFilterQuery(String baseQuery, SearchContainerFilterVO searchContainerFilterVO,
                                        ContainerMapperForDestination mapper, boolean isOracleDataSource, String fromScreen)
			{
		super(baseQuery, searchContainerFilterVO, mapper, isOracleDataSource, fromScreen);
		
	}
	
	public LHSearchContainerFilterQuery(String baseQuery, SearchContainerFilterVO searchContainerFilterVO,
			ContainerMapperForDestination mapper, boolean isOracleDataSource)
			 {
		super(baseQuery, searchContainerFilterVO, mapper, isOracleDataSource);
		
	}
 
	public LHSearchContainerFilterQuery(String baseQuery, SearchContainerFilterVO searchContainerFilterVO,
                                        SearchContainerMultiMapper mapper, boolean isOracleDataSource, String fromScreen)  {
		super(baseQuery,searchContainerFilterVO, mapper, isOracleDataSource, fromScreen);
	}
	
	public LHSearchContainerFilterQuery(String baseQuery, SearchContainerFilterVO searchContainerFilterVO,
			SearchContainerMultiMapper mapper,boolean isOracleDataSource) {
		super(baseQuery,searchContainerFilterVO, mapper, isOracleDataSource);
	}

		
@Override	
protected void appendWhereClause(StringBuilder builder) {
	         if(searchContainerFilterVO.getHbaMarkedFlag()!=null && "Y".equals(searchContainerFilterVO.getHbaMarkedFlag())) {
					builder.append("AND HBADTL.ULDREFNUM IS NOT NULL ");
				} 
				if(searchContainerFilterVO.getHbaMarkedFlag()!=null && "N".equals(searchContainerFilterVO.getHbaMarkedFlag())) {
					builder.append("AND HBADTL.ULDREFNUM IS NULL ");
				} 
	
}

@Override	
protected void appendSelectQuery(StringBuilder query) {
	query.append(",  CASE WHEN MIN(HBADTL.ULDREFNUM) IS NULL THEN 'N'  ELSE 'Y' END HBAMARKED");
	
}

@Override	
protected void appendJoinQuery(StringBuilder query) {
	query.append("  LEFT OUTER JOIN MALFLTCONHBADTL HBADTL  ")
	.append(" ON ")
	.append(" MST.CMPCOD=HBADTL.CMPCOD  AND ")
	.append(" MST.ULDREFNUM=HBADTL.ULDREFNUM   ");
	
}
}


