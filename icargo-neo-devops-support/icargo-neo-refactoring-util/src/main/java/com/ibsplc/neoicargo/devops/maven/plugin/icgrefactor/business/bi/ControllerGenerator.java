/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.bi;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.springframework.stereotype.Service;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractGenerator;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.ASTNodeBuilder;

/**
 * @author A-1759
 *
 */
public class ControllerGenerator extends AbstractGenerator {

	List<MethodDeclaration> methodList;
	private String serviceName;

	public ControllerGenerator(Logger logger, List<MethodDeclaration> list) {
		super(logger);
		methodList = list;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void refactor() {
		this.packageToCreate = implInfo.getPackageDecl();
		setPackage();
		addImports();
		addImplements();
		addServiceAnnotation();
		autoWireServices();
		addMethods();
	}

	private void autoWireServices() {
		String ejbName = getParentType(cu).getName().toString().replace("Controller", "ServicesEJB");
		String ejbPackage = this.packageToCreate.replace("controller", "service");
		serviceName = ejbName.replace("ServicesEJB", "Service").substring(0, 1).toLowerCase() + ejbName.substring(1);
		addImport(cu, ast, ejbPackage + "." + ejbName);
		autowireField(ejbName, serviceName);
	}

	private void addMethods() {
		for (MethodDeclaration methodDeclaration : this.methodList) {
			MethodDeclaration method = ast.newMethodDeclaration();
			method.modifiers().add(ast.newModifier(ModifierKeyword.PUBLIC_KEYWORD));
			method.setReturnType2((Type) ASTNodeBuilder.getNewNode(ast, methodDeclaration.getReturnType2()));
			method.setName(ast.newSimpleName(methodDeclaration.getName().toString()));
			List<SingleVariableDeclaration> parameters = methodDeclaration.parameters();
			for (SingleVariableDeclaration variable : parameters) {
				SingleVariableDeclaration newParam = ast.newSingleVariableDeclaration();
				newParam.setType((Type) ASTNodeBuilder.getNewNode(ast, variable.getType()));
				newParam.setName(ast.newSimpleName(variable.getName().toString()));
				method.parameters().add(newParam);
			}
			if (methodDeclaration.thrownExceptionTypes().size() > 0) {
				methodDeclaration.thrownExceptionTypes()
						.forEach(t -> method.thrownExceptionTypes().add((Type) ASTNodeBuilder.getNewNode(ast, (ASTNode)t)));
			}
			Block block = ast.newBlock();
			ReturnStatement returnStatement = ast.newReturnStatement();
			MethodInvocation serviceMethodInvoc = ast.newMethodInvocation();
			serviceMethodInvoc.setName(ast.newSimpleName(methodDeclaration.getName().toString()));
			serviceMethodInvoc.setExpression(ast.newSimpleName(serviceName));
			for (SingleVariableDeclaration variable : parameters) {
				serviceMethodInvoc.arguments().add(ast.newSimpleName(variable.getName().toString()));
			}
			returnStatement.setExpression(serviceMethodInvoc);
			block.statements().add(returnStatement);
			method.setBody(block);
			getParentType(cu).bodyDeclarations().add(method);
		}

	}

	protected void addImplements() {
		String biClassName = getParentType(cu).getName().toString().replace("Controller", "BI");
		addImport(cu, ast, this.packageToCreate.replace("controller", "") + biClassName);
		getParentType(cu).superInterfaceTypes().add(ast.newSimpleType(ast.newSimpleName(biClassName)));
	}

	private void addServiceAnnotation() {
		addImport(cu, ast, Service.class.getName());
		MarkerAnnotation annotation = ast.newMarkerAnnotation();
		annotation.setTypeName(ast.newSimpleName(Service.class.getSimpleName()));
		getParentType(cu).modifiers().add(0, annotation);
	}
}
