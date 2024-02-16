/**
 * 
 */
package com.ibsplc.neoicargo.framework.icgsupport.persistence;

/**
 * @author A-1759
 *
 */
public interface EntityManager {

	void persist(Object paramObject);

	<T> T find(Class<T> paramClass, Object paramObject) throws FinderException;

	void remove(Object paramObject);

	void flush();

	void refresh(Object paramObject);

	void clear();

}
