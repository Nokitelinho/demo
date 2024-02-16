/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.neoservices;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author A-1759
 *
 */
@Getter
@Setter
@EqualsAndHashCode
public class MapperInfo {

	String mapperType;
	
	String mapperFullyQualifiedName;
}
