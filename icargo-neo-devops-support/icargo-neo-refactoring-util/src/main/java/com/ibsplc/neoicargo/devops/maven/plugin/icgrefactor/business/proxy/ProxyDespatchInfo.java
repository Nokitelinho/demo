/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.proxy;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import lombok.Getter;
import lombok.Setter;

/**
 * @author A-1759
 *
 */
@Getter
@Setter
public class ProxyDespatchInfo {

	private MethodDeclaration currentMethod;
	
	private MethodInvocation despatchRequest;
	
	private MethodInvocation getService;
}
