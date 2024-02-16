package com.ibsplc.icargo.persistence.dao.xaddons.lh.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.ContainerMapperForDestination;
import com.ibsplc.icargo.persistence.dao.mail.operations.SearchContainerFilterQueryBuilder;
import com.ibsplc.icargo.persistence.dao.mail.operations.SearchContainerMultiMapper;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

public class LHSearchContainerFilterQueryBuilder extends SearchContainerFilterQueryBuilder {
	
	private static final String SEARCHMODE_DEST = "DESTN";

	public LHSearchContainerFilterQueryBuilder() {
		// Default constructor
	}
	
	private String baseQuery;
	private SearchContainerFilterVO searchContainerFilterVO;
	private boolean isOracleDataSource;
	
	
	public LHSearchContainerFilterQueryBuilder withBaseQuery(String baseQuery){
		this.baseQuery = baseQuery;
		return this;
	}
	
	
	public LHSearchContainerFilterQueryBuilder withSearchContainerFilterVO(SearchContainerFilterVO searchContainerFilterVO){
		this.searchContainerFilterVO = searchContainerFilterVO;
		return this;
	}
	
	public LHSearchContainerFilterQueryBuilder withIsOracleDataSource(boolean isOracleDataSource){
		this.isOracleDataSource = isOracleDataSource;
		return this;
	}
	
	public LHSearchContainerFilterQuery build() throws SystemException {
		if (SEARCHMODE_DEST.equals(this.searchContainerFilterVO.getSearchMode())) {
			if(searchContainerFilterVO.getPageSize() == 0){
				return new LHSearchContainerFilterQuery(this.baseQuery,this.searchContainerFilterVO,new ContainerMapperForDestination(),this.isOracleDataSource);
			
			}
			else{
				return new LHSearchContainerFilterQuery(this.baseQuery,this.searchContainerFilterVO,new ContainerMapperForDestination(),this.isOracleDataSource, "MTK058");
				
			}
		} else {
			if(searchContainerFilterVO.getPageSize() == 0){
				return new LHSearchContainerFilterQuery(this.baseQuery,this.searchContainerFilterVO,new SearchContainerMultiMapper(),this.isOracleDataSource);
			
		}
			else{
				return new LHSearchContainerFilterQuery(this.baseQuery,this.searchContainerFilterVO,new SearchContainerMultiMapper(),this.isOracleDataSource,"MTK058");
			}
		}
	}
	
	

}
