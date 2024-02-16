/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.Assignment.Operator;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.VariableInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.controller.ControllerASTVisitor;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;

/**
 * @author A-1759
 *
 */
public class LocalDateMapper {

	private static final String LOCAL_DATE = "LocalDate";
	private static final String GET_LOCAL_DATE = "getLocalDate";

	private static final List<String> qTypes = Arrays.asList(LOCAL_DATE, "ZonedDateTime");

	public static MethodInvocation getNeoLocalDate(AST ast, List<ASTNode> parameters, Expression exp) {
		MethodInvocation neoMethod;
		
		switch (parameters.size()) {
		case 2: {
			neoMethod = handle2ArgConstructors(ast, parameters);
			break;
		}
		case 3: {
			neoMethod = handle3ArgConstructors(ast, parameters);
			break;
		}
		case 4: {
			neoMethod = handle4ArgConstructors(ast, parameters);
			break;
		}
		default: {
			neoMethod = handle6ArgConstructors(ast, parameters);
			break;
		}
		}
		neoMethod.setExpression(exp);
		return neoMethod;
	}

	private static MethodInvocation handle6ArgConstructors(AST ast, List<ASTNode> parameters) {
		MethodInvocation neoLocalDateMethod = ast.newMethodInvocation();
		neoLocalDateMethod.setName(ast.newSimpleName(GET_LOCAL_DATE));
		//neoLocalDateMethod.setExpression(ast.newSimpleName(LOCAL_DATE));
		neoLocalDateMethod.arguments().add(getNewNode(parameters.get(2), ast));
		neoLocalDateMethod.arguments().add(getNewNode(parameters.get(0), ast));
		return neoLocalDateMethod;
	}

	private static MethodInvocation handle4ArgConstructors(AST ast, List<ASTNode> parameters) {
		MethodInvocation neoLocalDateMethod = ast.newMethodInvocation();
		neoLocalDateMethod.setName(ast.newSimpleName("getLocalDateTime"));
		//neoLocalDateMethod.setExpression(ast.newSimpleName(LOCAL_DATE));
		neoLocalDateMethod.arguments().add(getNewNode(parameters.get(2), ast));
		neoLocalDateMethod.arguments().add(getNewNode(parameters.get(0), ast));
		return neoLocalDateMethod;
	}

	private static MethodInvocation handle3ArgConstructors(AST ast, List<ASTNode> parameters) {
		if (isLocationParam(parameters.get(2))) {
			return handleGMTDateConstrucor(ast, parameters);
		}
		return getLocalDateMethod(ast, parameters.get(0), parameters.get(2));
	}

	private static MethodInvocation handleGMTDateConstrucor(AST ast, List<ASTNode> parameters) {
		MethodInvocation neoLocalDateMethod = ast.newMethodInvocation();
		neoLocalDateMethod.setName(ast.newSimpleName("convertFromUTCTime"));
		//neoLocalDateMethod.setExpression(ast.newSimpleName(LOCAL_DATE));
		neoLocalDateMethod.arguments().add(getNewNode(parameters.get(0), ast));
		neoLocalDateMethod.arguments().add(getNewNode(parameters.get(1), ast));
		return neoLocalDateMethod;
	}

	private static boolean isLocationParam(ASTNode node) {
		return node instanceof QualifiedName && "Location".equals(((QualifiedName) node).getQualifier().toString());
	}

	private static MethodInvocation getLocalDateMethod(AST ast, ASTNode param0, ASTNode param1) {
		MethodInvocation neoLocalDateMethod = ast.newMethodInvocation();
		neoLocalDateMethod.setName(ast.newSimpleName(GET_LOCAL_DATE));
		//neoLocalDateMethod.setExpression(ast.newSimpleName(LOCAL_DATE));
		neoLocalDateMethod.arguments().add(getNewNode(param0, ast));
		neoLocalDateMethod.arguments().add(getNewNode(param1, ast));
		return neoLocalDateMethod;
	}

	
	public static String getZonedDateTimeMethod(String field) {
		switch(field) {
			case "DATE" : 
				return "getDayOfMonth";
			case "MONTH" :
				return "getMonthValue";
			case "DAY_OF_MONTH" :
				return "getDayOfMonth";
			case "DAY_OF_WEEK" :
				return "getDayOfWeek" ;
			case "DAY_OF_YEAR" :
				return "getDayOfYear" ;
			case "HOUR" :
				return "getHour" ;
			case "MINUTE" :
				return "getMinute" ;
			case "SECOND" :
				return "getSecond" ;
			case "YEAR" :
				return "getYear";
		}
		return null;
	}
	
	private static ASTNode getNewNode(ASTNode param, AST ast) {
		if ((param instanceof QualifiedName && qTypes.contains(((QualifiedName) param).getQualifier().toString()))
				|| param instanceof StringLiteral && ((StringLiteral)param).getLiteralValue().equals("***")) {
			return ast.newNullLiteral();
		} else {
			return ASTNodeBuilder.getNewNode(ast, param);
		}
	}

	private static MethodInvocation handle2ArgConstructors(AST ast, List<ASTNode> parameters) {
		MethodInvocation neoLocalDateMethod = ast.newMethodInvocation();
		neoLocalDateMethod.setName(ast.newSimpleName(GET_LOCAL_DATE));
		//neoLocalDateMethod.setExpression(ast.newSimpleName(LOCAL_DATE));
		neoLocalDateMethod.arguments().add(getNewNode(parameters.get(0), ast));
		return neoLocalDateMethod;
	}

	public static void refactorDisplayFormat(AST ast, MethodInvocation method, String format) {
		String formatType = null;
		switch(format){
			case "toDisplayDateOnlyFormat" :
				formatType = "DATE_FORMAT";
				break;
			case "toDisplayTimeOnlyFormat" :
				formatType = "TIME_FORMAT";
				break;
			case "toDisplayFormat" :
				formatType = "DATE_TIME_FORMAT";
				break;	
		}
		method.setName(ast.newSimpleName("format"));
		MethodInvocation ofPattern = ast.newMethodInvocation();
		ofPattern.setName(ast.newSimpleName("ofPattern"));
		ofPattern.setExpression(ast.newSimpleName("DateTimeFormatter"));
		method.arguments().add(ofPattern);
		ofPattern.arguments().add(ast.newQualifiedName(ast.newSimpleName(LocalDate.class.getSimpleName()),
				ast.newSimpleName(formatType)));	
	}
	
	public static void refactorSetDateMethods(AST ast, MethodInvocation method) {
		String variableName = method.getExpression().toString();
		String methodName = method.getName().toString().replace("set", "with");
		MethodInvocation withMethod = ast.newMethodInvocation();
		withMethod.setName(ast.newSimpleName(methodName));
		withMethod.setExpression(ast.newSimpleName("LocalDate"));
		withMethod.arguments().add(ast.newSimpleName(variableName));
		withMethod.arguments().add(ASTNodeBuilder.getNewNode(ast, (ASTNode)method.arguments().get(0)));
		Assignment assignment = ast.newAssignment();
		assignment.setLeftHandSide(ast.newSimpleName(variableName));
		assignment.setOperator(Operator.ASSIGN);
		assignment.setRightHandSide(withMethod);
		((ExpressionStatement)method.getParent()).setExpression(assignment);
	}
	
	
}
