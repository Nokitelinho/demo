/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor;

import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import lombok.Getter;
import lombok.Setter;

/**
 * @author A-1759
 *
 */
@Getter
@Setter
public class VariableInfo {

	private VariableDeclarationFragment variableFragment;
	
	private String variableName;
	
	private Type variableType;
	
	private Source_Type sourceType;
}
