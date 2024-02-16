/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ImportDeclaration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author A-1759
 *
 */
@Getter
@Setter
public class GenImplInfo {

	boolean isInterface;
	
	String clazzName;
	
	String packageDecl;
	
	String suffix="";
	
	List<MethodInfo> methods = new ArrayList<>();
	
	List<ImportDeclaration> imports = new ArrayList<>();
}
