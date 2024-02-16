/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.BaseASTVisitor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ClassInstanceInvocation;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ExceptionInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.VariableInfo;

import lombok.Getter;

/**
 * @author A-1759
 *
 */
public class ControllerASTVisitor extends BaseASTVisitor {

	List<ClassInstanceCreation> localDateCreations = new ArrayList<>();
	List<ClassInstanceCreation> measureCreations = new ArrayList<>();
	List<CatchClause> catchClauseList = new ArrayList<>();
	List<ClassInstanceInvocation> proxyInstanceCreations = new ArrayList<>();
	List<ExceptionInfo> systemExceptionCreations = new ArrayList<>();
	private boolean isCatchClause = false;
	private String exceptionVariable;
	private VariableDeclarationStatement currentStatement;

	@Getter
	protected Map<String, VariableInfo> allVariables = new HashMap<>();
	@Getter

	protected Map<String, List<MethodInvocation>> methodInvocMap = new HashMap<>();
	private static final List<String> watchMethodList = Arrays.asList("getSecurityContext", "getLogonAttributesVO",
			"getEntityManager", "getQueryDAO", "getObjectQueryDAO", "toCalendar", "getCache", "getDisplayValue",
			"getDisplayUnit", "getSystemValue", "getSystemUnit", "getRoundedSystemValue", "getRoundedDisplayValue",
			"getCriterion", "getKey", "getSequenceKey", "getBean", "setErrorDisplayType", "getRequestContext", "get",
			"convert", "setFrom", "setGroup", "addToContext", "storeTxBusinessParameter", "getTxBusinessParameter",
			"getMoney", "getDouble", "getInstance", "toDisplayDateOnlyFormat", "toDisplayTimeOnlyFormat",
			"toDisplayFormat", "setDate", "setTime", "setDateAndTime", "addMeasureValues", "subtractMeasureValues",
			"getSystemParameterValue", "setToContext", "setAmount", "isLesserThan", "isGreaterThan", "toGMTDate");

	List<MethodInvocation> setMethods = new ArrayList<>();
	List<VariableDeclarationStatement> cacheFactories = new ArrayList<>();
	List<FieldAccess> calendarFieldAccess = new ArrayList<>();
	List<VariableDeclarationStatement> statementsToDelete = new ArrayList<>();

	public ControllerASTVisitor(Logger logger) {
		super(logger);
		initializemethodInvocMap();
	}

	private void initializemethodInvocMap() {
		for (String method : watchMethodList) {
			methodInvocMap.put(method, new ArrayList<>());
		}
	}

	@Override
	public boolean visit(FieldAccess node) {
		if (node.getParent() instanceof Assignment) {
			calendarFieldAccess.add(node);
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		if ("CacheFactory".equals(node.getType().toString())) {
			cacheFactories.add(node);
		}
		if ("BeanConversionConfigVO".equals(node.getType().toString())) {
			statementsToDelete.add(node);
		}
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

	@Override
	public boolean visit(MethodInvocation node) {
		String methodName = node.getName().toString();
		if (watchMethodList.contains(methodName)) {
			methodInvocMap.get(methodName).add(node);
		} else if (node.getName().toString().startsWith("set") && node.arguments().size() == 1) {
			setMethods.add(node);
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(ClassInstanceCreation node) {
		if ("LocalDate".equals(node.getType().toString())
				|| "com.ibsplc.icargo.framework.util.time.LocalDate".equals(node.getType().toString())) {
			localDateCreations.add(node);
		} else if (node.getType().toString().endsWith("Proxy")) {
			ClassInstanceInvocation invocations = new ClassInstanceInvocation();
			invocations.setType(node.getType().toString());
			invocations.setControllerNode(node.getParent());
			proxyInstanceCreations.add(invocations);
		} else if ("Measure".equals(node.getType().toString())) {
			measureCreations.add(node);
		} else if ("SystemException".equals(node.getType().toString())) {
			if (isCatchClause) {
				ExceptionInfo info = new ExceptionInfo();
				info.setExceptionInstance(node);
				info.setCatchVariableName(exceptionVariable);
				systemExceptionCreations.add(info);
			}

		}
		return super.visit(node);
	}

	@Override
	public boolean visit(CatchClause node) {
		catchClauseList.add(node);
		isCatchClause = true;
		exceptionVariable = node.getException().getName().toString();
		return super.visit(node);
	}

	@Override
	public void endVisit(CatchClause node) {
		isCatchClause = false;
		super.endVisit(node);
	}

	@Override
	public boolean visit(QualifiedName node) {
		if (node.getQualifier().toString().equals("AbstractControl")
				&& node.getName().toString().equals("REQ_TRIGGER_POINT")) {
			if (node.getParent() instanceof MethodInvocation) {
				methodInvocMap.putIfAbsent("triggerPointgetParam", new ArrayList<>());
				methodInvocMap.get("triggerPointgetParam").add((MethodInvocation) node.getParent());
			}
		}
		return super.visit(node);
	}
	


	@Override
	public boolean visit(StringLiteral node) {
		if (node.getLiteralValue().equals("REQ_TRIGGERPOINT") &&  node.getParent() instanceof MethodInvocation) {
			methodInvocMap.putIfAbsent("triggerPointgetParam", new ArrayList<>());
			methodInvocMap.get("triggerPointgetParam").add((MethodInvocation) node.getParent());
		}
		return super.visit(node);
	}

}
