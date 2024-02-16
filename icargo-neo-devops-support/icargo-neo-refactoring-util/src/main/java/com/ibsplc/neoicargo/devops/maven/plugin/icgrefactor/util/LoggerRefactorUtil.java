/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.StringLiteral;

/**
 * @author A-1759
 *
 */
public class LoggerRefactorUtil {

	private static final List<String> LOGGER_METHODS = Arrays.asList("entering", "exiting", "log");

	public static MethodInvocation refactorLoggers(MethodInvocation loggerMethod, AST ast) {
		String method = loggerMethod.getName().toString();
		if (LOGGER_METHODS.contains(method)) {
			switch (method) {
			case "entering": {
				return refactorEnterExist(loggerMethod, " Entering", ast);
			}
			case "exiting": {
				return refactorEnterExist(loggerMethod, " Exiting", ast);
			}
			case "log": {
				return refactorLog(loggerMethod, ast);
			}
			}
		}
		return null;
	}

	private static MethodInvocation refactorLog(MethodInvocation loggerMethod, AST ast) {
		MethodInvocation newMethodInvoc = ast.newMethodInvocation();
		newMethodInvoc.setExpression(ast.newSimpleName("log"));
		String level = loggerMethod.arguments().get(0).toString().toUpperCase();
		String methodName;
		switch (level) {
		case "LOG.FINEST":
		case "LOG.FINER":
		case "LOG.FINE":
		case "LOG.CONFIG": {
			methodName = "debug";
			break;
		}
		case "LOG.INFO": {
			methodName = "info";
			break;
		}
		case "LOG.WARNING": {
			methodName = "warn";
			break;
		}
		default: {
			methodName = "error";
			break;
		}

		}
		newMethodInvoc.setName(ast.newSimpleName(methodName));
		List<ASTNode> arguments = loggerMethod.arguments();
		ASTNode arg2 = ASTNodeBuilder.getNewNode(ast, arguments.get(1));
		if (loggerMethod.arguments().size() == 2) {
			CatchClause catchClause = ASTNodeBuilder.getParentNode(loggerMethod, CatchClause.class);
			if (Objects.nonNull(catchClause)
					&& catchClause.getException().getName().toString().equals(arg2.toString())) {
				StringLiteral sl = ast.newStringLiteral();
				sl.setLiteralValue("Exception :");
				newMethodInvoc.arguments().add(sl);
			}
			newMethodInvoc.arguments().add(arg2);
		} else {
			InfixExpression arg = ast.newInfixExpression();
			newMethodInvoc.arguments().add(arg);
			StringLiteral emptyString = ast.newStringLiteral();
			emptyString.setLiteralValue("");
			arg.setLeftOperand(emptyString);
			arg.setOperator(Operator.PLUS);
			arg.setRightOperand((Expression) arg2);
			for (int index = 2; index < arguments.size(); index++) {
				StringLiteral blankString = ast.newStringLiteral();
				blankString.setLiteralValue(" ");
				arg.extendedOperands().add(blankString);
				arg.extendedOperands().add(ASTNodeBuilder.getNewNode(ast, arguments.get(index)));
			}
		}
		return newMethodInvoc;
	}

	private static MethodInvocation refactorEnterExist(MethodInvocation loggerMethod, String suffix, AST ast) {
		MethodInvocation newMethodInvoc = ast.newMethodInvocation();
		newMethodInvoc.setExpression(ast.newSimpleName("log"));
		newMethodInvoc.setName(ast.newSimpleName("debug"));
		ASTNode arg1 = ASTNodeBuilder.getNewNode(ast, (ASTNode) loggerMethod.arguments().get(0));
		ASTNode arg2 = ASTNodeBuilder.getNewNode(ast, (ASTNode) loggerMethod.arguments().get(1));
		InfixExpression arg = ast.newInfixExpression();
		newMethodInvoc.arguments().add(arg);
		arg.setLeftOperand((Expression) arg1);
		arg.setOperator(Operator.PLUS);
		StringLiteral colonSeperator = ast.newStringLiteral();
		colonSeperator.setLiteralValue(" : ");
		arg.setRightOperand(colonSeperator);
		arg.extendedOperands().add(arg2);
		StringLiteral suffixL = ast.newStringLiteral();
		suffixL.setLiteralValue(suffix);
		arg.extendedOperands().add(suffixL);
		return newMethodInvoc;
	}

}
