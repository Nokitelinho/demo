/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author A-1759
 *
 */
@Getter
@Setter
public class MethodInfo {
	
	String strategyClassName;
	
	MethodDeclaration methodDeclaration;
	
	String groupName;

}
