/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor;

import org.eclipse.jdt.core.dom.FieldDeclaration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author A-1759
 *
 */
@Getter
@Setter
public class FieldInfo {

	String fieldName;
	
	FieldDeclaration fieldDeclaration;
	
	String fieldTypeSimpleName;
}
