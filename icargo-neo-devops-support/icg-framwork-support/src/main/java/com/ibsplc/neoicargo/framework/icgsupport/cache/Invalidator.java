/**
 * 
 */
package com.ibsplc.neoicargo.framework.icgsupport.cache;

/**
 * @author A-1759
 *
 */
public interface Invalidator {
	void invalidateForGroup(String groupName);
	
	void invalidateForKey(String groupName, Object... keys);
}
