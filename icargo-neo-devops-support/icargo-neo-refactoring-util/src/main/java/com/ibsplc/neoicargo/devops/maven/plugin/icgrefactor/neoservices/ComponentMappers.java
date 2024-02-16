/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.neoservices;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author A-1759
 *
 */
public class ComponentMappers {

	private static Map<String, Set<MapperInfo>> componentMapperList = new HashMap<>();
	
	private static Map<String, MapperInfo> mapperInfo = new HashMap<>();
	
	public static void addMapperInfo(String mapper, String fullyQualifiedName){
		if(!mapperInfo.containsKey(mapper)){
			MapperInfo info = new MapperInfo();
			info.mapperFullyQualifiedName=fullyQualifiedName;
			info.mapperType=mapper;
			mapperInfo.put(mapper, info);
		}
	}
		
	
	public static void addMapper(String component, String mapper){
		Set<MapperInfo> mapperSet = componentMapperList.get(component);
		if(Objects.isNull(mapperSet)){
			mapperSet = new HashSet<>();
			componentMapperList.put(component, mapperSet);
		}
		mapperSet.add(mapperInfo.get(mapper));
	}
	
	public static Set<MapperInfo> getMappers(String component){
		return componentMapperList.get(component);
	}
	
}
