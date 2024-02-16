/**
 * 
 */
package com.ibsplc.neoicargo.framework.icgsupport.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

/**
 * @author A-1759
 *
 */
public class BaseCacheImpl implements Invalidator {

	@Autowired
	private CacheManager manager;
	

	
	@Override
	public void invalidateForGroup(String groupName) {
		manager.getCache(groupName).clear();
	}

	@Override
	public void invalidateForKey(String groupName, Object... keys) {
		int hashCode = 0;
		for(Object obj : keys){
			hashCode +=obj.hashCode();
		}
		manager.getCache(groupName).evictIfPresent(hashCode);
	}

	
}
