package com.ibsplc.xibase.server.framework.persistence.query;

import com.ibsplc.xibase.server.framework.persistence.query.sql.*;
import com.ibsplc.xibase.server.framework.persistence.query.sql.ResultSetWrapper.RankKeyFilterAwareResultSetWrapper;
import org.hibernate.Session;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;



/**
 * A native query that supports pagination using a ranking function.
 *
 * @author A-1640
 *
 * @param <T>
 */
public class PageableNativeQuery<T extends Serializable> extends NativeQuery {

	private MultiMapper<T> multiMapper;
	
	// the mapper instance passed on by the developer
	private Mapper<T> mapper;
	private Page<T> pagedData;
	private int pageSize;
	private int eagerLoadSize;
	private int recordCount;
	private int startRank;
	private int endRank;
	private int queryHash;
	private boolean isCacheable = true;	
	private boolean isReportPaging =false;
	private boolean isOverrideRankQuery = false;
	
	private static final String RECCNT_WRAPPERQRY_PFX = "select last_value(rank) over (partition by 1) "+RECCNT_ALIAS+",xallrows.* from ( ";
	private static final String RECCNT_WRAPPERQRY_SFX = " ) xallrows ";
	private static final String PAGE_QUERY_PREFIX = "SELECT ALLROWS.* FROM ( ";
	
	//Added By Kiran For Fixing a Performance Bug .. Query should be parameterized
	//private static final String PAGE_QUERY_SUFFIX = ")ALLROWS WHERE RANK BETWEEN <START_RANK> AND <END_RANK>";
	private static final String PAGE_QUERY_SUFFIX = " ) ALLROWS WHERE RANK BETWEEN ? AND ? ";
	private static final String PAGE_RANKLIST_SUFFIX = " ) ALLROWS WHERE XIBASE_RECKEY IN ( ";
	private static final String PAGE_RANKKEY_QUERY_PREFIX = "SELECT DISTINCT ALLROWS.XIBASE_RECKEY FROM ( ";
	private static final String PAGE_RANKKEY_QUERY_SUFFIX = " ) ALLROWS ORDER BY ALLROWS.XIBASE_RECKEY";
	
	public static final int DEFAULT_PAGE_SIZE = 25 ;	
	private static final int PRINT_PAGE_SIZE = 25;
	
	private List<String> rankKeyListToFetch;

	/**
	 * Construct an instance of PageableQuery using the supplied instance of
	 * Query and the MultiMapper implementation , the pageSize used will be the size passed by the caller
	 *
	 */
	public PageableNativeQuery(int pageSize, int recordCount, MultiMapper<T> multiMapper, Session session) {
		this(pageSize, recordCount, (Mapper)null, multiMapper, session);
	}

	/**
	 * Construct an instance of PageableQuery using the supplied instance of
	 * Query and the MultiMapper implementation , the pageSize used will be 25
	 *
	 */
	public PageableNativeQuery(int recordCount, MultiMapper<T> multiMapper, Session session) {
		this(DEFAULT_PAGE_SIZE, recordCount, multiMapper, session);
	}

	/**
	 * Construct an instance of PageableQuery using the supplied instance of
	 * Query and the Mapper implementation , the pageSize used will be the size passed by the caller
	 */
	public PageableNativeQuery(int pageSize, int recordCount, Mapper<T> mapper, Session session) {
		this(pageSize, recordCount, mapper, null, session);
	}

	/**
	 * Construct an instance of PageableQuery using the supplied instance of
	 * Query and the Mapper implementation , the pageSize used will be 25
	 */
	public PageableNativeQuery(int recordCount, Mapper<T> mapper, Session session) {
		this(DEFAULT_PAGE_SIZE, recordCount, mapper, null, session);
	}

	/**
	 * Internal constructor;
	 * @param pageSize
	 * @param recordCount
	 * @param mapper
	 * @param multiMapper
	 */
	private PageableNativeQuery(int pageSize,int recordCount,Mapper<T> mapper, MultiMapper<T> multiMapper, Session session) {
		super(session);
		this.pageSize = pageSize;
		this.recordCount = recordCount;
		this.mapper = mapper;
		this.multiMapper = multiMapper;
		resolveSetPageSize();
	}
		

	private String finalQuery;

	/**
	 *
	 * @param recordCount
	 * @param finalQuery - the query read from Query xml
	 * @param multiMapper
	 */
	public PageableNativeQuery(int recordCount, String finalQuery , MultiMapper<T> multiMapper, Session session) {
		this(DEFAULT_PAGE_SIZE, recordCount, (Mapper<T>)null, multiMapper, session);
		this.finalQuery = finalQuery;
	}

	/**
	 *
	 * @param recordCount
	 * @param finalQuery - the query read from Query xml
	 * @param mapper
	 */
	public PageableNativeQuery(int recordCount, String finalQuery , Mapper<T> mapper, Session session) {
		this(DEFAULT_PAGE_SIZE, recordCount, mapper, (MultiMapper<T>)null, session);
		this.finalQuery = finalQuery;
	}

	/**
	 *
	 * @param pageSize
	 * @param recordCount
	 * @param finalQuery - the query read from Query xml
	 * @param multiMapper
	 */
	public PageableNativeQuery(int pageSize, int recordCount, String finalQuery , MultiMapper<T> multiMapper, Session session) {
		this(pageSize, recordCount, (Mapper<T>)null, multiMapper, session);
		this.finalQuery = finalQuery;
	}

	/**
	 *
	 * @param pageSize
	 * @param recordCount
	 * @param finalQuery - the query read from Query xml
	 * @param mapper
	 */
	public PageableNativeQuery(int pageSize, int recordCount, String finalQuery , Mapper<T> mapper, Session session) {
		this(pageSize, recordCount, mapper, (MultiMapper<T>)null, session);
		this.finalQuery = finalQuery;
	}

	private void resolveSetPageSize() {
		String pageSizeOverride = null;
		// check if the value is override in the current context
		if(pageSizeOverride == null || pageSizeOverride.trim().length() == 0)
			return;
		this.pageSize = Integer.parseInt(pageSizeOverride);
	}
	
	/**
	 * Developer Needs to override this method if he needs to append the query based on
	 * filter criteria .
	 */
	@Override
	public String getNativeQuery() {
		if( finalQuery != null ) {
			return finalQuery ;
		}
		return null;
	}

	/* ****************************************************************************************** */

	/**
	 * @author A-2394
	 * method to rewrite the final query with the function to get the record count.
	 */
	private void primeQuery(){
		String query = super.getFinalQuery(true);
		if(!this.isOverrideRankQuery && !isRankKeyCacheable()){
		query = new StringBuilder(query.length() + 100)
					.append(RECCNT_WRAPPERQRY_PFX)
					.append(query)
					.append(RECCNT_WRAPPERQRY_SFX).toString();
		}
		this.finalisedQuery = query;
		computeHash();
	}
	
	/**
	 * Method to obtain the page corresponding to the specified pageNumber
	 *
	 * @param pageNumber
	 * @return data corresponding to a page
	 */
	public Page<T> getPage(int pageNumber) {
		if ( pageNumber <= 0 ) {
			throw new IllegalArgumentException("PAGE NUMBER CANNOT BE LESS THAN OR EQUAL TO ZERO");
		}
		primeQuery();
		Page<T> page = getPageFromDatabase(pageNumber);
		return page;
	}

	/**
	 * Method to retrieve the page from database.
	 * @author A-2394
	 * @param pageNumber
	 * @return
	 */
	Page<T> getPageFromDatabase(int pageNumber){
		List<T> results = null;
		if(eagerLoadSize == 0)
			eagerLoadSize = pageSize;
		startRank = (pageNumber - 1) * pageSize + 1;
		endRank = startRank + eagerLoadSize - 1;
		// Obtain the result using either multimapper or using a mapper
		if (pagedData == null) {
			if(multiMapper != null ) {
				results = getResultList(multiMapper);
			}else if( mapper != null ) {
				results = getResultList(mapper);
			}
		}

		boolean hasNextPage = (endRank < recordCount);
		int actualPageSize = 0 ;
		if(results != null && results.size() > 0 ){
			actualPageSize = results.size();
			pagedData = new Page<T>(results, pageNumber, pageSize, results.size(),
					startRank, startRank + actualPageSize, hasNextPage, recordCount);
		}
		return pagedData;
	}
	
	List<String> getAllRankKeysFromDatabase(){
		String query = new StringBuilder(this.finalisedQuery.length() + 100).append(PAGE_RANKKEY_QUERY_PREFIX)
				.append(this.finalisedQuery).append(PAGE_RANKKEY_QUERY_SUFFIX).toString();
		return this.session.doReturningWork(connection -> {
			List<String> rankList = new ArrayList<>();
			try {
				preparedStatement = connection.prepareStatement(query);
				setAllParameters();
				try(ResultSet rs = preparedStatement.executeQuery()){
					Mapper<String> rankMapper = getRankMapper();
					RankKeyFilterAwareResultSetWrapper rsw =
							new RankKeyFilterAwareResultSetWrapper(rs, filterKey,this.lowerCaseColumns);
					while (rsw.next()) {
						rankList.add(rankMapper.map(rs));
					}
					return rankList;
				}
			} catch (SQLException e) {
				throw new QueryException(e);
			}
		});
	}
	
	public Mapper<String> getRankMapper(){
		return new Mapper<String>(){
			 public String map(ResultSet rs) throws SQLException{
				 return rs.getString("XIBASE_RECKEY");
			 }
		};
	}
	
	/**
	 * Method to obtain the total record count fetched by the query
	 * @return record count
	 * @deprecated : use last_value function
	 */
	private int getRecordCount() {
		return this.session.doReturningWork(conn -> {
			ResultSet resultSet = null;
			int count = 0;
			String query = super.getFinalQuery(true);
			if(!this.isOverrideRankQuery){
				query = new StringBuilder(RECCNT_WRAPPERQRY_PFX).append(query).append(
						RECCNT_WRAPPERQRY_SFX).toString();
				//query = query.replaceAll(RANK_CLAUSE, "");
			}
			try {
				preparedStatement = conn.prepareStatement(query);
				setAllParameters();
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					count = resultSet.getInt("RECCNT");
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new QueryException(e);

			} finally {
				if (resultSet != null) {
					try {
						resultSet.close();
					} catch (SQLException e) {
					}
				}
				close();
			}
			return count;
		});
	}

	/**
	 * Method to execute the query and obtain the result
	 *
	 * @see NativeQuery#getResultList(MultiMapper)
	 */
	public <T extends Serializable> List<T> getResultList(
			MultiMapper<T> multiMapper) {

		/*
		 * Added By Kiran on 05-Jan-2010
		 * Throw IllegalArgumentException when the multiMapper supplied is 
		 * an instanceof PageAwareMultiMapper 
		 */
		if( multiMapper instanceof PageAwareMultiMapper ) {
			throw new IllegalArgumentException(" PageAwareMultiMapper Implementation is Incompatible with PageableNativeQuery ... Use MultiMapper instead ");
		}
		return this.session.doReturningWork(connection -> {
			if (resultList == null) {
				preparedStatement = null;
				prepareQuery(connection);
				ResultSet resultSet = null;
				try {
					resultSet = preparedStatement.executeQuery();
					final ResultSet rs = resultSet;
					if(Objects.isNull(rankKeyListToFetch)){
						ResultSetWrapper.CountAwareResultSetWrapper rsw = new ResultSetWrapper.CountAwareResultSetWrapper(rs, this.lowerCaseColumns);
						resultList = multiMapper.map(rsw);
						this.setRecordCount(rsw.getTotalRecordCount());
					}else{
						ResultSetWrapper rsw = new ResultSetWrapper(resultSet, lowerCaseColumns);
						resultList = multiMapper.map(rsw);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					throw new QueryException(e);
				} finally {
					if (resultSet != null) {
						try {
							resultSet.close();
						} catch (SQLException e) {
							// Ignored
						}
					}
					close();
				}
			}
			return resultList;
		});
	}

	/**
	 * Method to obtain the final query
	 *
	 * Change Added by Kiran - to Support Normal List return using PageableNativeQuery
	 *
	 * @see NativeQuery#getFinalQuery(boolean)
	 */
	@Override
	protected String getFinalQuery(boolean isFinal) {
		String finalPageQuery = super.getFinalQuery(isFinal);
		
		if(startRank == 0 && endRank == 0 && Objects.isNull(rankKeyListToFetch)) {
			return finalPageQuery;
		}
		Integer lastPosition = Integer.valueOf(0);
		if( parameters != null ) {
			List<Integer> sortedIndexes = new ArrayList<>(parameters.keySet());
			lastPosition = Collections.max(sortedIndexes);					
		}		
		if(Objects.nonNull(rankKeyListToFetch)){
			StringBuilder queryBuilder = new StringBuilder(PAGE_QUERY_PREFIX).append(finalPageQuery)
					.append(PAGE_RANKLIST_SUFFIX);
			for(String rankKey : rankKeyListToFetch){
				queryBuilder.append(" ?,");
				setParameter(++lastPosition, rankKey);
			}
			queryBuilder.deleteCharAt(queryBuilder.length()-1);
			queryBuilder.append(") ");
			finalPageQuery = queryBuilder.toString();
		}else{
	        finalPageQuery = new StringBuilder(PAGE_QUERY_PREFIX).append(finalPageQuery)
					.append(PAGE_QUERY_SUFFIX).toString();
	
			setParameter(++lastPosition, Integer.valueOf(startRank));
			setParameter(++lastPosition, Integer.valueOf(endRank));
		}
		return finalPageQuery;
	}

	/**
	 * computes a unique hashcode to identify the query sans rank filter.
	 */
	private void computeHash(){
		String finalQuery = super.getFinalQuery(true);
		int parHash = this.parameters == null ? 0 : this.parameters.hashCode();
		this.queryHash = finalQuery.hashCode() + parHash;
	}
	
	/**
	 * Return the page size
	 *
	 * @return the page size
	 */
	public int getPageSize() {
		if (pageSize == 0) {
			return 25;
		} else {
			return pageSize;
		}
	}

	public <T extends Serializable> List<T> getResultList(Mapper<T> mapper) {
		return this.session.doReturningWork(connection -> {
			if (resultList == null) {
				preparedStatement = null;
				prepareQuery(connection);
				populateWithQueryResult(mapper);
			}
			return resultList;
		});
	}

	/**
	 * Populate a non-pageable query
	 * @param mapper - a mapper implementation
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected <T extends Serializable> void populateWithQueryResult(Mapper<T> mapper) {
		ResultSet rs = null;
		ResultSetWrapper rsw = null;		
		
		try {
			rs = preparedStatement.executeQuery();
			rsw = Objects.isNull(this.rankKeyListToFetch) ? new ResultSetWrapper.CountAwareResultSetWrapper(rs, this.lowerCaseColumns) :  new ResultSetWrapper(rs, this.lowerCaseColumns);
			resultList = new ArrayList<T>();
			while (rsw.next()) {
				resultList.add(mapper.map(rsw));
			}
			if(Objects.isNull(this.rankKeyListToFetch)){
				this.setRecordCount(((ResultSetWrapper.CountAwareResultSetWrapper)rsw).getTotalRecordCount());
			}
		} catch (SQLException e) {
			throw new QueryException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					//Ignored
				}
			}
			close();
		}
	}

	

	/* (non-Javadoc)
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery#setRecordCount(int)
	 */
	@Override
	protected void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	/**
	 * @author A-2394
	 * @return
	 */
	int getQueryHash(){
		return this.queryHash;
	}

	/**
	 * @return the isCacheable
	 */
	public boolean isCacheable() {
		return isCacheable;
	}

	/**
	 * @param isCacheable the isCacheable to set
	 */
	public void setCacheable(boolean isCacheable) {
		this.isCacheable = isCacheable;
	}

	void setEagerLoadSize(int eagerLoadSize) {
		this.eagerLoadSize = eagerLoadSize;
	}

	/**
	 * @return the isReportPaging
	 */
	public boolean isReportPaging() {
		return isReportPaging;
	}

	/**
	 * @param isReportPaging the isReportPaging to set
	 */
	public void setReportPaging(boolean isReportPaging) {
		this.isReportPaging = isReportPaging;
		if(isReportPaging){
			setCacheable(false);	
			if(PRINT_PAGE_SIZE>0)
				this.pageSize=PRINT_PAGE_SIZE;
		}
	}
	
	/**
	 * @return the isOverrideRankQuery
	 */
	public boolean isOverrideRankQuery() {
		return isOverrideRankQuery;
	}
	
	/**
	 * @param isOverrideRankQuery the isOverrideRankQuery to set
	 */
	public void setOverrideRankQuery(boolean isOverrideRankQuery) {
		this.isOverrideRankQuery = isOverrideRankQuery;
	}

	private boolean isRankKeyCacheable(){
		return this.isCacheable && isFilteredResult();
	}
}
