/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.MethodInvocation;
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
public class ConverterASTVisitor extends BaseASTVisitor {

	public ConverterASTVisitor(Logger logger) {
		super(logger, Arrays.asList("getLogonAttributesVO", "getSecurityContext","getSystemParameterValue"));
		// TODO Auto-generated constructor stub
	}

	List<ClassInstanceCreation> localDateCreations = new ArrayList<>();
	List<ClassInstanceCreation> measureCreations = new ArrayList<>();
	private VariableDeclarationStatement currentStatement;
	Map<String, VariableInfo> allVariables = new HashMap<>();
	List<MethodInvocation> setMethods = new ArrayList<>();
	
	
	public boolean visit(ClassInstanceCreation node) {
		if ("LocalDate".equals(node.getType().toString())) {
			localDateCreations.add(node);
		}else if ("Measure".equals(node.getType().toString())) {
			measureCreations.add(node);
		} 
		return super.visit(node);
	}
	
	@Override
	public boolean visit(MethodInvocation node) {
		if(node.getName().toString().startsWith("set") && node.arguments().size()==1) {
			setMethods.add(node);
		}
		return super.visit(node);
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
	public boolean visit(VariableDeclarationFragment node) {
		VariableInfo info = new VariableInfo();
		info.setVariableFragment(node);
		info.setVariableName(node.getName().toString());
		if (Objects.nonNull(this.currentStatement)) {
			info.setVariableType(this.currentStatement.getType());
		}
		allVariables.put(node.getName().toString(), info);
		return super.visit(node);
	}

	@Override
	public boolean visit(SingleVariableDeclaration node) {
		VariableInfo info = new VariableInfo();
		info.setVariableName(node.getName().toString());
		info.setVariableType(node.getType());
		allVariables.put(node.getName().toString(), info);
		return super.visit(node);
	}
}
