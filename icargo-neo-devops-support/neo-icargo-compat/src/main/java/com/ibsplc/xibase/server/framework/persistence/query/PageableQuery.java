/*
 * @(#) PageableQuery.java 1.0 Apr 22, 2005
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This Software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to License terms.
 *
 */
package com.ibsplc.xibase.server.framework.persistence.query;

import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.PageAwareMultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.io.Serializable;
import java.util.List;

/**
 * A pageable wrapper for a <i>Query></i>. A query instance can be made
 * pageable by constructing a PageableQuery with the instance of the query.
 * <p>
 * The page information once obtained is cached. <b>Hence using the same
 * instance of a pageablequery with diffferent values for pageNumber and/or page
 * size will not return different pages.</b>
 * <p>
 * Typically, Within an instance of QueryDAO
 * <p>
 * <code>
 * public class  EmployeeQueryDAO extends AbstractQueryDAO{
 * public Page&lt;EmployeeVO&gt; getEmployees(int pageNumer){
 * QueryManager qm = getQueryManager();
 * Query query = qm.createNamedNativeQuery(&quot;EMPLOYEE_LIST&quot;);
 * PageableQuery&lt;EmployeeVO&gt; pq = new PageableQuery&lt;EmployeeVO&gt;(query,new EmployeeMapper());
 * return pq.getPage(pageNumer,10);//10 is the page size
 * }
 * </code>
 * <p>
 * where EmployeeMapper is declared as
 * <p>
 * <code>
 * public class EmployeeMapper implements Mapper&lt;EmployeeVO&gt; {
 * public EmployeeVO map(ResultSet rs) throws SQLException {
 * EmployeeVO vo = new EmployeeVO();
 * vo.setDesignation(rs.getString(&quot;EMP_DSG&quot;));
 * vo.setFirstName(rs.getString(&quot;EMP_FST&quot;));
 * vo.setLastName(rs.getString(&quot;EMP_LST&quot;));
 * vo.setVersion(rs.getInt(&quot;VERSION&quot;));
 * return vo;
 * }
 * }
 * </code>
 *
 * @author Binu K
 */

/*
 * Revision History Revision Date Author Description 0.1 Apr 22, 2005 Binu K
 * First draft
 */
public class PageableQuery<T extends Serializable> {

    private Query query;

    private int startPos;

    private int endPos;

    private Mapper<T> mapper;

    private MultiMapper<T> multiMapper;

    private Log log = LogFactory.getLogger("XIBASE");

    private Page<T> pagedData;

    private int pageSize;

    /**
     * Construct an instance of PageableQuery using the supplied instance of
     * Query and the Mapper implementation
     *
     * @param query
     * @param mapper
     */
    public PageableQuery(Query query, Mapper<T> mapper) {
        this(PageableNativeQuery.DEFAULT_PAGE_SIZE, query, mapper, (MultiMapper<T>) null);
    }

    /**
     * Construct an instance of PageableQuery using the supplied instance of
     * Query and the MultiMapper implementation
     *
     * @param query
     */
    public PageableQuery(Query query, MultiMapper<T> multiMapper) {
        this(PageableNativeQuery.DEFAULT_PAGE_SIZE, query, (Mapper<T>) null, multiMapper);
    }

    /**
     * Construct an instance of PageableQuery using the supplied instance of
     * Query and the Mapper implementation
     *
     * @param query
     * @param mapper
     * @param pageSize
     */
    public PageableQuery(Query query, Mapper<T> mapper, int pageSize) {
        this(pageSize, query, mapper, null);
    }

    /**
     * Construct an instance of PageableQuery using the supplied instance of
     * Query and the MultiMapper implementation
     *
     * @param query
     * @param pageSize
     */
    public PageableQuery(Query query, MultiMapper<T> multiMapper, int pageSize) {
        this(pageSize, query, (Mapper<T>) null, multiMapper);
    }

    private PageableQuery(int pageSize, Query query, Mapper<T> mapper, MultiMapper<T> multiMapper) {
        this.query = query;
        this.multiMapper = multiMapper;
        this.mapper = mapper;
        this.pageSize = pageSize;
        resolveSetPageSize();
    }

    private void resolveSetPageSize() {
        String pageSizeOverride = null;
        // check if the value is override in the current context
        if (pageSizeOverride == null || pageSizeOverride.trim().length() == 0)
            return;
        this.pageSize = Integer.parseInt(pageSizeOverride);
    }

    /**
     * Get the Page at the given page number
     * <p>
     * <b> Calling this method on the same instance with different page numbers
     * will not give different results. <b>
     *
     * @param pageNumber -
     *                   the page Number
     * @return a Page
     * @throws IllegalArgumentException -
     *                                  if the page number is invalid
     */
    public Page<T> getPage(int pageNumber) {
        log.entering("PageableQuery", "getPage");
        if (pageNumber < 1) {
            log.log(Log.SEVERE, "PAGE NUMBER INCORRECT");
            throw new IllegalArgumentException("PAGE NUMBER INCORRECT");
        }
        log.exiting("PageableQuery", "getPage");
        return getPageInformation(pageNumber, getPageSize());
    }

    /**
     * Get the Page using the given absolute index to the underlying resultset
     * Page Number is required in addition to the absolute index to calculate
     * the displayed start and end indices.
     * <p>
     * <b> This page method should only be used in conjunction with a
     * PageAwareMultiMapper. Also, calling this method on the same instance of a
     * PageableQuery with different arguments will not yield different results.
     * <b>
     *
     * @param pageNumber    -
     *                      the pageNumber
     * @param absoluteIndex -
     *                      the actual index to the resultset
     * @return
     */
    Page<T> getPageAbsolute(int pageNumber, int absoluteIndex, int pageSize) {
        log.entering("PageableQuery", "getPageAbsolute");
        if (!((pageNumber > 0) && (absoluteIndex > 0))) {
            log.log(Log.SEVERE, "PAGE NUMBER AND/OR ABSOLUTE INDEX INCORRECT");
            throw new IllegalArgumentException(
                    "PAGE NUMBER AND/OR ABSOLUTE INDEX INCORRECT");
        }
        return getAbsolutePageInformation(pageNumber, pageSize, absoluteIndex);
    }

    public Page<T> getPageAbsolute(int pageNumber, int absoluteIndex) {
        log.entering("PageableQuery", "getPageAbsolute");
        if (!((pageNumber > 0) && (absoluteIndex > 0))) {
            log.log(Log.SEVERE, "PAGE NUMBER AND/OR ABSOLUTE INDEX INCORRECT");
            throw new IllegalArgumentException(
                    "PAGE NUMBER AND/OR ABSOLUTE INDEX INCORRECT");
        }
        return getAbsolutePageInformation(pageNumber, getPageSize(),
                absoluteIndex);
    }

    private Page<T> getAbsolutePageInformation(int pageNumber, int pageSize,
                                               int absoluteIndex) {
        log.entering("PageableQuery", "getAbsolutePageInformation");
        if (pagedData == null) {
            startPos = absoluteIndex;
            // End position is set one more to check next page availability
            // End Position is not related to the absoluteIndex as we are not
            // counting the actual rows in the resultset
            endPos = pageSize + 1;
            query.setFirstResult(startPos);
            query.setMaxResults(endPos);
            List<T> results = null;
            results = query.getResultList(multiMapper);
            if (results != null) {
                PageAwareMultiMapper<T> pamm = (PageAwareMultiMapper<T>) multiMapper;

                int size = results.size();
                // Has next if result has more than page size
                boolean hasNext = (size > pageSize);
                if (hasNext) {
                    int index = size;
                    for (int i = pageSize; i < size; i++) {
                        index = index - 1;
                        // Remove unwanted extras
                        results.remove(index);

                    }
                    size = pageSize;
                }
                // Recalculate startPos and endPos for relative numbers
                // used for display
                startPos = (pageNumber - 1) * pageSize + 1;
                endPos = pageNumber * pageSize;

                pagedData = new Page<T>(results, pageNumber, pageSize, size,
                        startPos, endPos, pamm.getAbsoluteIndex(), hasNext);
            }
        }
        log.exiting("PageableQuery", "getAbsolutePageInformation");
        return pagedData;

    }

    /**
     * Gets the actual page of information
     *
     * @param pageNamber
     * @param pageSize
     * @return
     */
    private Page<T> getPageInformation(int pageNamber, int pageSize) {
        if (pagedData == null) {
            startPos = (pageNamber - 1) * pageSize + 1;
            // End position is set one more to check next page availability
            endPos = pageNamber * pageSize + 1;
            query.setFirstResult(startPos);
            query.setMaxResults(endPos);
            List<T> results = null;
            if (mapper != null) {
                results = query.getResultList(mapper);
            } else {
                results = query.getResultList(multiMapper);
            }
            int size = results.size();
            // Has next if result has one more
            boolean hasNext = size == (pageSize + 1);
            if (hasNext) {
                size -= 1;
                // Remove unwanted extra last
                results.remove(size);
            }
            pagedData = new Page<T>(results, pageNamber, pageSize, size, startPos, --endPos, hasNext);
        }

        return pagedData;

    }

    /**
     * Return the page size
     */
    private int getPageSize() {

        if (pageSize == 0) {
            return 25;
        } else {
            return pageSize;
        }
    }
}
