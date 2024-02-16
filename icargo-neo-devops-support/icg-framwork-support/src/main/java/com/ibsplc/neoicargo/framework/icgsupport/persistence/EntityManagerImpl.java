/**
 * 
 */
package com.ibsplc.neoicargo.framework.icgsupport.persistence;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author A-1759
 *
 */
@Component("EntityManager")
public class EntityManagerImpl implements EntityManager {
	
	@Autowired
	private javax.persistence.EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.ibsplc.neoicargo.framework.icgsupport.persistence.EntityManager#persist(java.lang.Object)
	 */
	@Override
	public void persist(Object paramObject) {
		entityManager.persist(paramObject);

	}

	/* (non-Javadoc)
	 * @see com.ibsplc.neoicargo.framework.icgsupport.persistence.EntityManager#find(java.lang.Class, java.lang.Object)
	 */
	@Override
	public <T> T find(Class<T> paramClass, Object paramObject) throws FinderException {
		T result = entityManager.find(paramClass, paramObject);
		if(Objects.isNull(result)){
			throw new FinderException("No persisted object found for " + paramObject.toString());
		}
		return result;
	}


	/* (non-Javadoc)
	 * @see com.ibsplc.neoicargo.framework.icgsupport.persistence.EntityManager#remove(java.lang.Object)
	 */
	@Override
	public void remove(Object paramObject) {
		entityManager.remove(paramObject);
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.neoicargo.framework.icgsupport.persistence.EntityManager#flush()
	 */
	@Override
	public void flush() {
		entityManager.flush();

	}

	/* (non-Javadoc)
	 * @see com.ibsplc.neoicargo.framework.icgsupport.persistence.EntityManager#refresh(java.lang.Object)
	 */
	@Override
	public void refresh(Object paramObject) {
		entityManager.refresh(paramObject);
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.neoicargo.framework.icgsupport.persistence.EntityManager#clear()
	 */
	@Override
	public void clear() {
		entityManager.clear();
	}

}
