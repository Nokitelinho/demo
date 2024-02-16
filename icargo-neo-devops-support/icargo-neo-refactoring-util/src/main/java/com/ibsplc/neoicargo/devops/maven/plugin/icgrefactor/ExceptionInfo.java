/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor;

import org.eclipse.jdt.core.dom.ClassInstanceCreation;

import lombok.Getter;
import lombok.Setter;

/**
 * @author A-1759
 *
 */
@Getter
@Setter
public class ExceptionInfo {

	private ClassInstanceCreation exceptionInstance;
	
	private String catchVariableName;
}
