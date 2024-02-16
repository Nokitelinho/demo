/*
 * PageAwareMultiMapper.java Created on Jul 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.xibase.server.framework.persistence.query.sql;

import com.ibsplc.xibase.server.framework.persistence.query.QueryException;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A MultiMapper that is aware of paging needs. Used in situations where
 * a multimapper is needed with paging needs. 
 * Typically,
 <code>
 public class XXMultiMapper extends PageAwareMultiMapper{
 
 public List<ParentVO> map(ResultSet rs){
 boolean isRowComplete = false;
 ParentVO parentVO = null;
 ChildVo childVO = null;
 String prevParentId = null;
 List<ParentVO> parents = new ArrayList<ParentVO>();
 String parentId = null,childId=null,childName=null;
 while(<b>hasNext()</b>) {
 parentId = rs.get.....();
 childId = rs.get.....();
 childName = rs.get.....();		
 if(parentId != prevParentId) {
 parents.add(parentVO);
 parentVO = new ParentVO();
 parentVO.setParentId(parentId);
 .........
 <b>increment();</b>
 }
 childVO = new ChildVO();
 childVO.setChildId(childId);
 childVO.setChildName(childName);
 parentVO.addChild(childVO);
 }
 return parents;
 }
 }
 </code>
 * 
 * @author Binu K
 */

/*
 * Revision History
 * Revision 	Date      	   		   Author			Description
 * 0.1			Jul 28, 2005	  	   Binu K			First draft
 */
public abstract class PageAwareMultiMapper<T extends Serializable> implements
		MultiMapper<T> {

	private int maxResults;

	//Intialize index to 1 as we always count to the page size
	private int index = 1;

	private ResultSet resultSet;

	//The absolute index into the resultset
	private int absoluteIndex;

	private int peekCount;
	

	/**
	 * Sets the max Results
	 * @param maxResults
	 */
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	/**
	 * Set a result Set instance for this mapper
	 * @param resultSet
	 */
	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	/**
	 * Gets the absoluteIndex
	 * @return Returns the absoluteIndex.
	 */
	public int getAbsoluteIndex() {
		return absoluteIndex;
	}

	/**
	 * Sets the absolute index 
	 * @param absoluteIndex
	 */
	public void setAbsoluteIndex(int absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}

	/**
	 * Increment the row count held by this mapper.
	 * This method should be called only when a VO that is to
	 * be
	 * 
	 * @throws QueryException
	 */
	public void increment() throws QueryException {
		++index;

	}

	/**
	 * Checks if the underlying resultset has more rows and
	 * the page size is not exceeded.
	 * 
	 * @return
	 */
	public boolean hasNext() {
		try {
			boolean hasNext = (index <= maxResults) && (resultSet.next());
			//Increment absolute index only upto the page size
			//The index need not be incremented for the extra rows
			//that will be retrieved, but removed
			if (index < maxResults) {
				++absoluteIndex;
			}
			return hasNext;
		} catch (SQLException e) {
			throw new QueryException(e);
		}

	}

	public boolean peekNext() {
		try {
			boolean hasNext = (index <= maxResults) && (resultSet.next());
			if (hasNext) {
				peekCount++;
			}
			return hasNext;
		} catch (SQLException e) {
			throw new QueryException(e);
		}

	}

	public int endPeek() {
		try {
			for (int i = 0; i < peekCount; i++) {
				resultSet.previous();
			}
			peekCount = 0;
			return peekCount;
		} catch (SQLException e) {
			throw new QueryException(e);
		}

	}

}
