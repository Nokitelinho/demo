/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.neoservices;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;

/**
 * @author A-1759
 *
 */
public class ValidatorRefactor extends ComponentRefactor {

	public ValidatorRefactor(Logger logger) {
		super(logger);
	}

	@Override
	public void setASTVisitor() {
		this.visitor = new ValidatorVisitor(logger);
	}

	@Override
	public void refactor() {
		isSourceChanged=true;
		String voType = ((ParameterizedType) getParentType(cu).getSuperclassType()).typeArguments().get(0).toString();
		ValidatorVisitor validatorVisitor = (ValidatorVisitor) this.visitor;
		List<CastExpression> castExpressionsOp = validatorVisitor.castExpressions.stream()
				.filter(c -> c.getType().toString().equals(voType)).collect(Collectors.toList());
		String voName = voType.substring(0,1).toLowerCase()+voType.substring(1);
		validatorVisitor.validateMethod.parameters().clear();
		SingleVariableDeclaration parameter = ast.newSingleVariableDeclaration();
		parameter.setType(ast.newSimpleType(ast.newSimpleName(voType)));
		parameter.setName(ast.newSimpleName(voName));
		validatorVisitor.validateMethod.parameters().add(parameter);
		for(CastExpression castExpression : castExpressionsOp){
			SimpleName simpleName = ast.newSimpleName(voName);
			assignExpression(castExpression.getParent(),simpleName , voName, voType, castExpression);
		}
		super.refactor();
	}

	private void assignExpression(ASTNode parentNode, Expression exp, String autoWireField, String fieldType,
			ASTNode currentNode) {
		if (parentNode instanceof VariableDeclarationFragment) {
			String varName = ((VariableDeclarationFragment) parentNode).getName().toString();
			if (varName.equals(autoWireField)) {
				parentNode.getParent().delete();
			} else {
				((VariableDeclarationFragment) parentNode).setInitializer(exp);
			}
			VariableDeclarationStatement statement = (VariableDeclarationStatement) parentNode.getParent();
			statement.setType(ast.newSimpleType(ast.newSimpleName(fieldType)));
		} else if (parentNode instanceof MethodInvocation) {
			MethodInvocation pNode = (MethodInvocation) parentNode;
			if (pNode.arguments().contains(currentNode)) {
				pNode.arguments().add(pNode.arguments().indexOf(currentNode), exp);
				currentNode.delete();
			} else {
				pNode.setExpression(exp);
			}
		}else if(parentNode instanceof ParenthesizedExpression){
			assignExpression(parentNode.getParent(),exp, autoWireField, fieldType, currentNode);
		} else if (parentNode instanceof ReturnStatement) {
			((ReturnStatement) parentNode).setExpression(exp);
		} else if (parentNode instanceof Assignment) {
			String varName = ((Assignment) parentNode).getLeftHandSide().toString();
			if (varName.equals(autoWireField)) {
				parentNode.getParent().delete();
			} else {
				((Assignment) parentNode).setRightHandSide(exp);
			}
		} else if (parentNode instanceof SuperMethodInvocation) {
			SuperMethodInvocation pNode = (SuperMethodInvocation) parentNode;
			pNode.arguments().add(pNode.arguments().indexOf(currentNode), exp);
			currentNode.delete();
		} else if (parentNode instanceof ConditionalExpression) {
			ConditionalExpression pNode = (ConditionalExpression) parentNode;
			if (pNode.getThenExpression().equals(currentNode)) {
				pNode.setThenExpression(exp);
			} else {
				pNode.setElseExpression(exp);
			}
		} else if (parentNode instanceof InfixExpression) {
			InfixExpression pNode = (InfixExpression) parentNode;
			if (pNode.getLeftOperand().equals(currentNode)) {
				pNode.setLeftOperand(exp);
			} else {
				pNode.setRightOperand(exp);
			}
		}
	}

}
