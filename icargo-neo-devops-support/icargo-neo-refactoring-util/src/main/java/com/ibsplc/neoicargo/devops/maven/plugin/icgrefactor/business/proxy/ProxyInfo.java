/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.proxy;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Type;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.VariableInfo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author A-1759
 *
 */
@Getter
@Setter
public class ProxyInfo {

	private String module;

	private String subModule;

	private List<MethodInfo> methods = new ArrayList<>();
}

@Getter
@Setter
class MethodInfo {
	
	private String methodName;
	
	private Type retrunType;
	
	private List<VariableInfo> parameters = new ArrayList<>();
}