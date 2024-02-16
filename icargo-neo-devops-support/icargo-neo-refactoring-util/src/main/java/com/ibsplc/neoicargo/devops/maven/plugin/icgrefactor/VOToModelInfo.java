/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor;

import org.eclipse.jdt.core.dom.ParameterizedType;

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
public class VOToModelInfo {

	private String voType;
	
	private String voName;
	
	private String modelType;
	
	private String modelName;
	
	private ParameterizedType voPType;
	
	private ParameterizedType modalPType;
	
	@Override
	public String toString() {
		return this.voName.isEmpty() ? voType + "-" + modelType + voPType : modelType + "-" + voType  + voPType ;
	}
}
