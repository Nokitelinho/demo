/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.proxy;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.Type;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractGenerator;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.VariableInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.ASTNodeBuilder;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

/**
 * @author A-1759
 *
 */
public class EProxyGenerator extends AbstractGenerator {

	private ProxyInfo proxyInfo;

	public EProxyGenerator(Logger logger, ProxyInfo proxyInfo) {
		super(logger);
		this.proxyInfo = proxyInfo;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void refactor() {
		setPackage();
		addImports();
		addEProxyAnnotation();
		addMethods();
	}

	private void addMethods() {
		List<String> addedMethods =new ArrayList<>();
		List<MethodInfo> methods = proxyInfo.getMethods();
		for(MethodInfo methodInfo : methods) {
			if(addedMethods.contains(methodInfo.getMethodName())) {
				continue;
			}
			MethodDeclaration md = ast.newMethodDeclaration();
			md.setName(ast.newSimpleName(methodInfo.getMethodName()));
			md.setReturnType2((Type)ASTNodeBuilder.getNewNode(ast, methodInfo.getRetrunType()));
			for(VariableInfo variableInfo : methodInfo.getParameters()) {
				SingleVariableDeclaration svd = ast.newSingleVariableDeclaration();
				svd.setType( (Type)ASTNodeBuilder.getNewNode(ast, variableInfo.getVariableType()));
				svd.setName(ast.newSimpleName(variableInfo.getVariableName()));
				md.parameters().add(svd);
			}
			getParentType(cu).bodyDeclarations().add(md);
			addedMethods.add(methodInfo.getMethodName());
		}
		
	}

	private void addEProxyAnnotation() {
		addImport(cu, ast, EProductProxy.class.getName());
		NormalAnnotation eProxyAnnotation = ast.newNormalAnnotation();
		eProxyAnnotation.setTypeName(ast.newSimpleName(EProductProxy.class.getSimpleName()));
		MemberValuePair mvp = ast.newMemberValuePair();
		mvp.setName(ast.newSimpleName("module"));
		StringLiteral sl = ast.newStringLiteral();
		sl.setLiteralValue(proxyInfo.getModule());
		mvp.setValue(sl);
		eProxyAnnotation.values().add(mvp);
		mvp = ast.newMemberValuePair();
		mvp.setName(ast.newSimpleName("submodule"));
		sl = ast.newStringLiteral();
		sl.setLiteralValue(proxyInfo.getSubModule());
		mvp.setValue(sl);
		eProxyAnnotation.values().add(mvp);
		mvp = ast.newMemberValuePair();
		mvp.setName(ast.newSimpleName("name"));
		sl = ast.newStringLiteral();
		String className = getParentType(cu).getName().toString();
		sl.setLiteralValue(className.substring(0,1).toLowerCase()+className.substring(1));
		mvp.setValue(sl);
		eProxyAnnotation.values().add(mvp);
		getParentType(cu).modifiers().add(0, eProxyAnnotation);
	}

}
