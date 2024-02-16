/*
* @(#) ObjectQuery.java 1.0 Mar 29, 2005 
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This Software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to License terms.
*
*/
package com.ibsplc.xibase.server.framework.persistence.query.object;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
* @author A-1456
* QbjectQuery.java
* 
* Base Class for all Object Queries. A place-holder at present
*/

/*
* Revision History
* Revision      Date                Author          Description
* 1.0           Mar 29, 2005        Binu K          First draft
*/


public abstract class ObjectQuery implements Query {
    
	@Override
	public int getFirstResult() {
		throw new UnsupportedOperationException();
	}

	@Override
	public FlushModeType getFlushMode() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> getHints() {
		throw new UnsupportedOperationException();
	}

	@Override
	public LockModeType getLockMode() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMaxResults() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Parameter<?> getParameter(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Parameter<?> getParameter(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> Parameter<T> getParameter(String arg0, Class<T> arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> Parameter<T> getParameter(int arg0, Class<T> arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T getParameterValue(Parameter<T> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getParameterValue(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getParameterValue(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Parameter<?>> getParameters() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isBound(Parameter<?> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Query setLockMode(LockModeType arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> Query setParameter(Parameter<T> arg0, T arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Query setParameter(Parameter<Calendar> arg0, Calendar arg1, TemporalType arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Query setParameter(Parameter<Date> arg0, Date arg1, TemporalType arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(Class<T> arg0) {
		throw new UnsupportedOperationException();
	}

	public ObjectQuery setParameter(String arg0, Calendar arg1,
			TemporalType arg2) {
		throw new UnsupportedOperationException();
	}

	public ObjectQuery setParameter(String arg0, Date arg1, TemporalType arg2) {
		throw new UnsupportedOperationException();
	}

	public ObjectQuery setParameter(String arg0, Object arg1) {
		throw new UnsupportedOperationException();
	}
	
	public ObjectQuery setFlushMode(FlushModeType arg0) {
		throw new UnsupportedOperationException();
	}
}
