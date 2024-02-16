package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util;

import java.math.BigDecimal;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.TypeLiteral;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;

public class MeasureMapper {

	public static MethodInvocation getQuantity(CompilationUnit cu, AST ast, Expression exp,
			List<ASTNode> parameters) {
		MethodInvocation quantiesMethod = ast.newMethodInvocation();
		quantiesMethod.setExpression(exp);
		quantiesMethod.setName(ast.newSimpleName("getQuantity"));
		for (int index = 0; index < parameters.size(); index++) {
			ASTNode newNode = ASTNodeBuilder.getNewNode(ast, parameters.get(index));
			if (newNode instanceof QualifiedName
					&& ((QualifiedName) newNode).getQualifier().toString().equals("UnitConstants")) {
				((QualifiedName) newNode).setQualifier(ast.newSimpleName(Quantities.class.getSimpleName()));
			}

			if (index == 1 || index == 2) {
				newNode = getBigDWrap(ast, (Expression) newNode);
			}
			quantiesMethod.arguments().add(newNode);
		}
		return quantiesMethod;
	}

	public static MethodInvocation handleMathOps(AST ast, MethodInvocation method) {
		String methodName = null;
		switch (method.getName().toString()) {
			case "addMeasureValues": {
				methodName = "add";
				break;
			}
			case "subtractMeasureValues": {
				methodName = "subtract";
				break;
			}
		}
		MethodInvocation ops = ast.newMethodInvocation();
		ops.setName(ast.newSimpleName(methodName));
		ops.setExpression((Expression) ASTNodeBuilder.getNewNode(ast, (ASTNode) method.arguments().get(0)));
		ops.arguments().add(ASTNodeBuilder.getNewNode(ast, (ASTNode) method.arguments().get(1)));
		return ops;
	}

	private static MethodInvocation getBigDWrap(AST ast, Expression exp) {
		MethodInvocation bigDecimal = ast.newMethodInvocation();
		bigDecimal.setName(ast.newSimpleName("valueOf"));
		bigDecimal.arguments().add(exp);
		bigDecimal.setExpression(ast.newSimpleName(BigDecimal.class.getSimpleName()));
		return bigDecimal;
	}
}
