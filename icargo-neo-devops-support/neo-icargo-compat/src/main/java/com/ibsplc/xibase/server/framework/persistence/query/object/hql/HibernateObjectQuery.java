/*
 * @(#) HibernateObjectQuery.java 1.0 Mar 29, 2005
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This Software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to License terms.
 *
 */
package com.ibsplc.xibase.server.framework.persistence.query.object.hql;

import com.ibsplc.xibase.server.framework.persistence.query.object.ObjectQuery;
import com.ibsplc.xibase.util.time.XCalendar;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.NonUniqueResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.Query;
import javax.persistence.*;
import java.util.*;

/**
 * Represents a Hibernate Object Query (in HQL).<p/>
 *
 * @author A-1456
 */
/*
 * Revision History
 * Revision      Date                Author          Description
 * 1.0           Mar 29, 2005        Binu K          First draft
 */
public class HibernateObjectQuery extends ObjectQuery {

    private Query query;
    private String queryString;

    private Boolean isPositional = null;
    static boolean postgresCheck = true;

    public HibernateObjectQuery(Query query, String queryString) {
        this.query = query;
        this.queryString = queryString;
    }


    public int executeUpdate() {
        throw new UnsupportedOperationException();
    }

    public List getResultList() {
        return query.getResultList();
    }

    public Object getSingleResult() {
        List result = getResultList();
        if (result.size() == 0) {
            throwPersistenceException(new NoResultException("No entity found for query"));
        } else if (result.size() > 1) {
            Set uniqueResult = new HashSet(result);
            if (uniqueResult.size() > 1) {
                throwPersistenceException(new NonUniqueResultException("result returns " + uniqueResult.size() + " elements"));
            } else {
                return uniqueResult.iterator().next();
            }
        } else {
            return result.get(0);
        }
        return null;
    }

    public ObjectQuery setMaxResults(int maxResult) {
        if (maxResult < 0) {
            throw new IllegalArgumentException("Negative (" + maxResult + ") parameter passed in to setMaxResults");
        }
        query.setMaxResults(maxResult);
        return this;
    }

    public ObjectQuery setFirstResult(int firstResult) {
        if (firstResult < 0) {
            throw new IllegalArgumentException("Negative (" + firstResult + ") parameter passed in to setFirstResult");
        }
        query.setFirstResult(firstResult);
        return this;
    }

    public ObjectQuery setHint(String hintName, Object value) {
        query.setHint(hintName, value);
        return this;
    }

    private ObjectQuery setParameter0(String name, Object value) {
        try {
            if (value instanceof Collection) {
                query.setParameter(name, (Collection) value);
            } else {
                query.setParameter(name, value);
            }
        } catch (HibernateException e) {
            throwPersistenceException(e);
        }
        return this;
    }

    private ObjectQuery setParameter0(String name, Date value, TemporalType temporalType) {
        query.setParameter(name, value, temporalType);
        return this;
    }

    private ObjectQuery setParameter0(String name, Calendar value, TemporalType temporalType) {
        query.setParameter(name, value, temporalType);
        return this;
    }

    public ObjectQuery setParameter(int position, Object value) {
        try {
            if (value instanceof XCalendar) {
                XCalendar xcal = (XCalendar) value;
                if (xcal.isTimePresent()) {
                    this.query.setParameter(position, (Calendar) value, TemporalType.TIMESTAMP);
                } else {
                    this.query.setParameter(position, (Calendar) value, TemporalType.DATE);
                }
            } else {
                this.query.setParameter(position, value);
            }
        } catch (HibernateException e) {
            throwPersistenceException(e);
        }
        return this;
    }

    private boolean isPositionalParameter() {
        if (isPositional == null) {
            //compute it
            int index = queryString.indexOf('?');
            //there is a ? and the following char is a digit
            if (index == -1) {
                //no ?
                isPositional = true;
            } else if (index == queryString.length() - 1) {
                // "... ?"
                isPositional = false;
            } else {
                isPositional = Character.isDigit(queryString.charAt(index + 1));
            }
        }
        return isPositional;
    }

    public ObjectQuery setParameter(int position, Date value, TemporalType temporalType) {
        try {
            this.query.setParameter(position, value, temporalType);
        } catch (HibernateException e) {
            throwPersistenceException(e);
        }
        return this;

    }

    public ObjectQuery setParameter(int position, Calendar value, TemporalType temporalType) {
        try {
            this.query.setParameter(position, value, temporalType);
        } catch (HibernateException e) {
            throwPersistenceException(e);
        }
        return this;
    }

    /**
     * @param cal
     * @return
     */
    public static Date getInJVMTimezone(Calendar cal) {
        Calendar jvmCal = Calendar.getInstance();
        jvmCal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        jvmCal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        jvmCal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        jvmCal.set(Calendar.HOUR, cal.get(Calendar.HOUR));
        jvmCal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
        jvmCal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
        jvmCal.set(Calendar.SECOND, cal.get(Calendar.SECOND));
        jvmCal.set(Calendar.MILLISECOND, cal.get(Calendar.MILLISECOND));
        jvmCal.set(Calendar.AM_PM, cal.get(Calendar.AM_PM));
        return jvmCal.getTime();

    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#listResults()
     */
    public List listResults() {
        return query.getResultList();
    }

    public void throwPersistenceException(PersistenceException e) {
        throw e;
    }

    public void throwPersistenceException(HibernateException e) {
        /**
         * Added stale date error handling from TIACT for IACT
         *
         * */
        if (e instanceof StaleObjectStateException) {
            throw new PersistenceException("STLDTA", e);
        } else if (e instanceof StaleStateException) {
            throwPersistenceException(new OptimisticLockException(e));
        } else if (e instanceof ConstraintViolationException) {
            //FIXME this is bad cause ConstraintViolationException happens in other circumstances
            throwPersistenceException(new EntityExistsException(e));
        } else if (e instanceof org.hibernate.ObjectNotFoundException) {
            throwPersistenceException(new EntityNotFoundException(e.getMessage()));
        } else if (e instanceof org.hibernate.NonUniqueResultException) {
            throwPersistenceException(new NonUniqueResultException(e.getMessage()));
        } else if (e instanceof UnresolvableObjectException) {
            throwPersistenceException(new EntityNotFoundException(e.getMessage()));
        } else if (e instanceof QueryException) {
            throw new IllegalArgumentException(e);
        } else {
            throwPersistenceException(new PersistenceException(e));
        }
    }

}
