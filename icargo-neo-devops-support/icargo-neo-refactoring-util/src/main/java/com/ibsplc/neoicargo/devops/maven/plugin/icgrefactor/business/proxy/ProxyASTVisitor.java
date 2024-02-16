/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.BaseASTVisitor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.VariableInfo;

/**
 * @author A-1759
 *
 */
public class ProxyASTVisitor extends BaseASTVisitor {

	MethodDeclaration currentMethod;

	List<ProxyDespatchInfo> proxyDespatches = new ArrayList<>();

	List<CatchClause> catchClauseList = new ArrayList<>();

	private String currentVariable;

	List<VariableInfo> allVariables = new ArrayList<>();

	private MethodInvocation getServiceMethod;

	private String biVariable;

	private VariableDeclarationStatement currentStatement;

	List<VariableDeclarationStatement> toBeDeleted = new ArrayList<>();

	List<ClassInstanceCreation> proxyExceptionCreations = new ArrayList<>();

	public ProxyASTVisitor(Logger logger) {
		super(logger);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean visit(SingleVariableDeclaration node) {
		VariableInfo info = new VariableInfo();
		info.setVariableName(node.getName().toString());
		info.setVariableType(node.getType());
		allVariables.add(info);
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		currentMethod = node;
		return super.visit(node);
	}

	@Override
	public boolean visit(CatchClause node) {
		catchClauseList.add(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ClassInstanceCreation node) {
		if (node.getType().toString().equals("ProxyException")) {
			proxyExceptionCreations.add(node);
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationFragment node) {
		this.currentVariable = node.getName().toString();
		VariableInfo info = new VariableInfo();
		info.setVariableFragment(node);
		info.setVariableName(node.getName().toString());
		if (Objects.nonNull(this.currentStatement)) {
			info.setVariableType(this.currentStatement.getType());
		}
		allVariables.add(info);
		return super.visit(node);
	}
	
	@Override
	public boolean visit(Assignment node) {
		if(node.getLeftHandSide() instanceof SimpleName) {
			this.currentVariable = node.getLeftHandSide().toString();
		}
		return super.visit(node);
	}

	@Override
	public void endVisit(Assignment node) {
		this.currentVariable = null;
		super.endVisit(node);
	}
	
	@Override
	public void endVisit(VariableDeclarationFragment node) {
		this.currentVariable = null;
		super.endVisit(node);
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		this.currentStatement = node;
		return super.visit(node);
	}

	@Override
	public void endVisit(VariableDeclarationStatement node) {
		this.currentStatement = null;
		super.endVisit(node);
	}

	@Override
	public boolean visit(MethodInvocation node) {
		if (node.getName().toString().equals("despatchRequest")) {
			ProxyDespatchInfo info = new ProxyDespatchInfo();
			info.setCurrentMethod(currentMethod);
			info.setDespatchRequest(node);
			proxyDespatches.add(info);
		} else if (node.getName().toString().equals("getService")) {
			this.getServiceMethod = node;
			this.biVariable = currentVariable;
			if (Objects.nonNull(currentStatement)) {
				toBeDeleted.add(currentStatement);
			}
		} else if (Objects.nonNull(node.getExpression()) && (node.getExpression().toString().equals(biVariable)
				|| node.getExpression().toString().contains("getService"))) {
			ProxyDespatchInfo proxyDespatchInfo = new ProxyDespatchInfo();
			proxyDespatchInfo.setCurrentMethod(currentMethod);
			proxyDespatchInfo.setGetService(getServiceMethod);
			proxyDespatchInfo.setDespatchRequest(node);
			proxyDespatches.add(proxyDespatchInfo);
		}

		return super.visit(node);
	}
}
