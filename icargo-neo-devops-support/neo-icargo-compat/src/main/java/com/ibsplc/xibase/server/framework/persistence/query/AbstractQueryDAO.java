/*
 * @(#) AbstractQueryDAO.java 1.0 Apr 22, 2005
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This Software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to License terms.
 *
 */
package com.ibsplc.xibase.server.framework.persistence.query;

import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.query.QueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.QueryManager;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * An abstract implementaion of <i>QueryDAO</i>. Concrtete implementations
 * of this class should be provided for all QueryDAOs. An instance of
 * this implementaion is obtained through the {@link EntityManager} which acts
 * as a factory for QueryDAOs. Typically,
 * <p>
 * <pre>
 * 	EntityManager em = PersistenceController.getEntityManager();
 * 	EmployeeQueryDAO eqd = (EmployeeQueryDAO)em.getQueryDAO("EMPLOYEE");
 * 	</pre>
 * <p>
 * where EmployeeQueryDAO is declared as
 * <pre>
 * 	public class  EmployeeQueryDAO extends AbstractQueryDAO{
 * 		public Collection<EmployeeVO> getAllEmployees(){
 * 			QueryManager qm = getQueryManager();
 * 			Query query = qm.createNamedNativeQuery("EMPLOYEE_LIST");
 * 			return query.getResultList(new EmployeeMapper());
 *            }
 *        }
 * 	</pre>
 * <p>
 * where EmployeeMapper is declared as
 * <pre>
 * public class EmployeeMapper implements Mapper<EmployeeVO> {
 * public EmployeeVO map(ResultSet rs) throws SQLException {
 * EmployeeVO vo = new EmployeeVO();
 * vo.setDesignation(rs.getString("EMP_DSG"));
 * vo.setFirstName(rs.getString("EMP_FST"));
 * vo.setLastName(rs.getString("EMP_LST"));
 * vo.setVersion(rs.getInt("VERSION"));
 * return vo;
 * }
 * }
 * </pre>
 * <p>
 * where "EMPLOYEE_LIST" is the named query
 * <pre>
 * 	SELECT EMP_DSG,EMP_FST,EMP_LST,VERSION FROM EMPLOYEES;
 * <pre>
 */
/*
 * Revision History
 * Revision      Date                Author          Description
 * 1.0           Apr 22, 2005        Binu K          First draft
 */
public abstract class AbstractQueryDAO implements QueryDAO {


    private QueryManager queryManager;

    public final QueryManager getQueryManager() {
        return queryManager;
    }

    public final void setQueryManager(QueryManager qm) {
        if (queryManager == null) {
            queryManager = qm;
        }

    }

    public Mapper<String> getStringMapper(final String column) {
        return new Mapper<String>() {
            public String map(ResultSet rs) throws SQLException {
                return rs.getString(column);
            }
        };
    }

    public Mapper<Integer> getIntMapper(final String column) {
        return new Mapper<Integer>() {
            public Integer map(ResultSet rs) throws SQLException {
                return rs.getInt(column);
            }
        };
    }

    public Mapper<Long> getLongMapper(final String column) {
        return new Mapper<Long>() {
            public Long map(ResultSet rs) throws SQLException {
                return rs.getLong(column);
            }
        };
    }

    public Mapper<Timestamp> getTimestampMapper(final String column) {
        return new Mapper<Timestamp>() {
            public Timestamp map(ResultSet rs) throws SQLException {
                return rs.getTimestamp(column);
            }
        };
    }

    public boolean isOracleDataSource() {
        return false;

    }

}
