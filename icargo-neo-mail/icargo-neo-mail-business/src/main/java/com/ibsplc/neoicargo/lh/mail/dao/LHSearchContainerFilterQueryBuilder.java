package com.ibsplc.neoicargo.lh.mail.dao;


import com.ibsplc.neoicargo.mail.dao.ContainerMapperForDestination;
import com.ibsplc.neoicargo.mail.dao.SearchContainerFilterQueryBuilder;
import com.ibsplc.neoicargo.mail.dao.SearchContainerMultiMapper;
import com.ibsplc.neoicargo.mail.vo.SearchContainerFilterVO;

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
	
	public LHSearchContainerFilterQuery build()  {
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
