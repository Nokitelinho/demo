/**
 * 
 */
package com.ibsplc.xibase.server.framework.persistence.query;

import java.io.Serializable;
import java.util.List;

/**
 * @author A-1759
 *
 */
public class ResultSetFilter implements Serializable{
	
	public static final String CONTEXT_KEY="RESULTSET_FILTER_CONTEXT";
	
	public enum FILTER_TYPE { INCLUDE, EXCLUDE }
	
	private String filterKey;
	
	private FILTER_TYPE filterType;
	
	private List<String> filterDataKeys;

	public ResultSetFilter(String filterKey, FILTER_TYPE filterType, List<String> filterDataKeys) {
		this.filterKey = filterKey;
		this.filterType = filterType;
		this.filterDataKeys = filterDataKeys;
	}

	/**
	 * @return the filterType
	 */
	public FILTER_TYPE getFilterType() {
		return filterType;
	}

	/**
	 * @return the filterDataKeys
	 */
	public List<String> getFilterDataKeys() {
		return filterDataKeys;
	}

	/**
	 * @return the filterKey
	 */
	public String getFilterKey() {
		return filterKey;
	}
	
}
