/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.BaseASTVisitor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ClassInstanceInvocation;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;

/**
 * @author A-1759
 *
 */
public class ServiceEJBASTVisitor extends BaseASTVisitor {

	private ImportDeclaration remoteImport;
	private List<ClassInstanceInvocation> controllerInstances = new ArrayList<>();

	List<CatchClause> catchClauseList = new ArrayList<>();
	private String currentMethod;
	
	Map<String, ReturnStatement> mdToReturnMap = new HashMap<>();
	public ServiceEJBASTVisitor(Logger logger) {
		super(logger);
		initializemethodInvocMap();

	}

	Map<String, List<MethodInvocation>> methodInvocMap = new HashMap<>();
	private static final List<String> watchMethodList = Arrays.asList( "getBean");


	private void initializemethodInvocMap() {
		for (String method : watchMethodList) {
			methodInvocMap.put(method, new ArrayList<>());
		}
	}
	
	@Override
	public boolean visit(CatchClause node) {
		catchClauseList.add(node);
		return super.visit(node);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 * TypeDeclaration)
	 */
	@Override
	public boolean visit(ImportDeclaration node) {
		String importCandidate = node.getName().toString();
		if (importCandidate.equals("java.rmi.RemoteException")) {
			remoteImport = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(ClassInstanceCreation node) {
		if (node.getType().toString().endsWith("Controller")) {
			ClassInstanceInvocation invocations = new ClassInstanceInvocation();
			invocations.setType(node.getType().toString());
			invocations.setControllerNode(node.getParent());
			controllerInstances.add(invocations);
		}
		return super.visit(node);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.BaseASTVisitor#visit
	 * (org.eclipse.jdt.core.dom.VariableDeclarationFragment)
	 */
	public boolean visit(VariableDeclarationFragment node) {
		ASTNode parent = node.getParent();
		if (parent instanceof VariableDeclarationStatement
				&& ((VariableDeclarationStatement) parent).getType().toString().endsWith("Controller")) {
			ClassInstanceInvocation invocations = new ClassInstanceInvocation();
			invocations.setType(((VariableDeclarationStatement) parent).getType().toString());
			invocations.setControllerNode(node);
			controllerInstances.add(invocations);

		}
		return super.visit(node);
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
		this.currentMethod = node.getName().toString();
		return super.visit(node);
	}
	
	@Override
	public boolean visit(ReturnStatement node) {
		mdToReturnMap.put(currentMethod, node);
		return super.visit(node);
	}
	

	@Override
	public boolean visit(MethodInvocation node) {
		String methodName = node.getName().toString();
		if (watchMethodList.contains(methodName)) {
			methodInvocMap.get(methodName).add(node);
		}
		return super.visit(node);
	}

	public ImportDeclaration getRemoteImport() {
		return remoteImport;
	}

	public List<ClassInstanceInvocation> getControllerInstances() {
		return controllerInstances;
	}

}
