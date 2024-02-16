package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

public class SearchContainerFilterQueryBuilder {

	private static final String SEARCHMODE_DEST = "DESTN";
	public SearchContainerFilterQueryBuilder() {
		// Default constructor
	}
	
	private String baseQuery;
	private SearchContainerFilterVO searchContainerFilterVO;
	private boolean isOracleDataSource;
	
	
	public SearchContainerFilterQueryBuilder withBaseQuery(String baseQuery){
		this.baseQuery = baseQuery;
		return this;
	}
	
	public SearchContainerFilterQueryBuilder withSearchContainerFilterVO(SearchContainerFilterVO searchContainerFilterVO){
		this.searchContainerFilterVO = searchContainerFilterVO;
		return this;
	}
	
	public SearchContainerFilterQueryBuilder withIsOracleDataSource(boolean isOracleDataSource){
		this.isOracleDataSource = isOracleDataSource;
		return this;
	}
	
	public SearchContainerFilterQuery build() throws SystemException {
		if (SEARCHMODE_DEST.equals(this.searchContainerFilterVO.getSearchMode())) {
			if(searchContainerFilterVO.getPageSize() == 0){
				return new SearchContainerFilterQuery(this.baseQuery,this.searchContainerFilterVO,new ContainerMapperForDestination(),this.isOracleDataSource);
			
			}
			else{
				return new SearchContainerFilterQuery(this.baseQuery,this.searchContainerFilterVO,new ContainerMapperForDestination(),this.isOracleDataSource, "MTK058");
				
			}
		} else {
			if(searchContainerFilterVO.getPageSize() == 0){
				return new SearchContainerFilterQuery(this.baseQuery,this.searchContainerFilterVO,new SearchContainerMultiMapper(),this.isOracleDataSource);
			
		}
			else{
				return new SearchContainerFilterQuery(this.baseQuery,this.searchContainerFilterVO,new SearchContainerMultiMapper(),this.isOracleDataSource,"MTK058");
			}
		}
	}
	
	
	
	
	

}
