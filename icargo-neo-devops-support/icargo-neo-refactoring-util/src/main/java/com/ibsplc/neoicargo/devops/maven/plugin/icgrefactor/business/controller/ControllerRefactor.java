/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.controller;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Assignment.Operator;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.springframework.stereotype.Component;

import com.ibsplc.icargo.framework.util.currency.helper.EBLMoneyMapper;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ClassInstanceInvocation;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ConsoleLogger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ConstructorInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ExceptionInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.FieldInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.RefactorConfig;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Source_Type;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.VariableInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.ASTNodeBuilder;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.GetterSetterUtil;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.LocalDateMapper;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.MeasureMapper;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.icgsupport.persistence.EntityManager;
import com.ibsplc.neoicargo.framework.util.currency.Money;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.masters.ParameterService;
import com.ibsplc.neoicargo.masters.ParameterType;

import lombok.Setter;
import lombok.SneakyThrows;

/**
 * @author A-1759
 *
 */
public class ControllerRefactor extends AbstractRefactor {

	private static final String ENTITY_MANAGER = "entityManager";
	private static final String CONTEXT_UTIL = "contextUtil";
	private static final List<String> expListToRemove = Arrays.asList("SystemException", "RemoteException",
			"CurrencyException");

	private static final List<String> annotationsToRemove = Arrays.asList("Audit", "Advice", "Raise");
	private boolean isController;
	protected boolean isBean;
	protected List<String> removedImports = new ArrayList<>();
	@Setter
	private boolean isEntity;

	public ControllerRefactor(Logger logger) {
		super(logger);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor#
	 * setASTVisitor()
	 */
	@Override
	public void setASTVisitor() {
		this.visitor = new ControllerASTVisitor(this.logger);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor#
	 * refactor()
	 */
	@Override
	public void refactor() {
		refactorPackage(cu, ast, visitor);
		removedImports = refactorIcgImports(cu, ast, visitor);
		isController = getParentType(cu).getName().toString().endsWith("Controller") || isBean;
		removeClassAnnotations();
		refactorLoggers();
		populateSourceType();
		// removeMethodAnnotations();
		handleLogonAttrbiutes();
		handleLocalDate();
		removeIcgExceptions();
		refactorProxyInstantiation();

		// handleEntityManager();
		// handleQueryDaoInvocations();
		handleCacheCalls();
		handleQuantitySystemValue();
		handleMesaure();
		handleKeyUtils();
		handlegetBean();
		handleSetErrorDisplayType();
		if (isController) {
			addComponentAnnotation();
		} else {
			addZoneDateTimeSetter();
		}
		handleTriggerPoint();
		handleSystemExceptionCreations();
		handleBeanConversions();
		deleteStatements();
		handleMoney();
		refactorLoggerSuspects();
		handleParameterUtil();
		if (isEntity) {
			addLombokAnnotations();
			removePKsandChilds();
		}
		handleSetMethods();
	}

	private void handleSetMethods() {
		if (!((ControllerASTVisitor) visitor).setMethods.isEmpty()) {
			for (MethodInvocation method : ((ControllerASTVisitor) visitor).setMethods) {
				Source_Type sourceSourceType = null;
				if (method.getExpression() instanceof SimpleName) {
					if (method.arguments().get(0) instanceof MethodInvocation) {
						MethodInvocation fromMethod = (MethodInvocation) method.arguments().get(0);
						if (fromMethod.getExpression() instanceof SimpleName) {
							VariableInfo sourceVariableInfo = ((ControllerASTVisitor) visitor).allVariables
									.get(fromMethod.getExpression().toString());
							if (Objects.nonNull(sourceVariableInfo)) {
								sourceSourceType = sourceVariableInfo.getSourceType();
							}
						}
					} else if (method.arguments().get(0) instanceof SimpleName) {
						sourceSourceType = Source_Type.SOURCE;
					}
					VariableInfo targetVariableInfo = ((ControllerASTVisitor) visitor).allVariables
							.get(method.getExpression().toString());
					if (Objects.nonNull(sourceSourceType) && Objects.nonNull(targetVariableInfo)) {
						String methodName = method.getName().toString();
						if (methodName.endsWith("Date")) {
							handleDateSetter(method, sourceSourceType, targetVariableInfo.getSourceType());
						} else if (methodName.endsWith("Weight") || methodName.endsWith("Volume")
								|| methodName.endsWith("Height") || methodName.endsWith("Length")
								|| methodName.endsWith("Width")) {
							handleUnitSetter(method, sourceSourceType, targetVariableInfo.getSourceType());
						} else if (methodName.endsWith("Charge") || methodName.endsWith("Amount")) {
							handleMoneySetter(method, sourceSourceType, targetVariableInfo.getSourceType());
						}
					}
				}

			}
		}

	}

	private void handleDateSetter(MethodInvocation method, Source_Type sourceSourceType, Source_Type targetSourceType) {
		if (sourceSourceType == Source_Type.UNKOWN || targetSourceType == Source_Type.UNKOWN
				|| sourceSourceType == targetSourceType) {
			return;
		}
		addImport(cu, ast, com.ibsplc.icargo.framework.util.time.LocalDateMapper.class.getName());

		if (sourceSourceType == Source_Type.OTHER) {
			MethodInvocation localDateMapperMethod = ast.newMethodInvocation();
			localDateMapperMethod.setName(ast.newSimpleName("toZonedDateTime"));
			localDateMapperMethod.setExpression(
					ast.newSimpleName(com.ibsplc.icargo.framework.util.time.LocalDateMapper.class.getSimpleName()));
			localDateMapperMethod.arguments().add(ASTNodeBuilder.getNewNode(ast, (ASTNode) method.arguments().get(0)));
			method.arguments().clear();
			method.arguments().add(localDateMapperMethod);
		}
	}

	private void handleUnitSetter(MethodInvocation method, Source_Type sourceSourceType, Source_Type targetSourceType) {
		if (sourceSourceType == Source_Type.UNKOWN || targetSourceType == Source_Type.UNKOWN
				|| sourceSourceType == targetSourceType) {
			return;
		}
		addImport(cu, ast, com.ibsplc.icargo.framework.util.unit.MeasureMapper.class.getName());
		MethodInvocation measureMapperMethod = ast.newMethodInvocation();
		if (sourceSourceType == Source_Type.OTHER) {
			measureMapperMethod.setName(ast.newSimpleName("toQuantity"));
		} else {
			measureMapperMethod.setName(ast.newSimpleName("toMeasure"));
		}
		if (isController) {
			autowireField(com.ibsplc.icargo.framework.util.unit.MeasureMapper.class.getSimpleName(), "measureMapper");
		} else {
			addFieldWithBeanUtil(com.ibsplc.icargo.framework.util.unit.MeasureMapper.class.getSimpleName(),
					"measureMapper", method);
		}
		measureMapperMethod.setExpression(ast.newSimpleName("measureMapper"));
		measureMapperMethod.arguments().add(ASTNodeBuilder.getNewNode(ast, (ASTNode) method.arguments().get(0)));
		method.arguments().clear();
		method.arguments().add(measureMapperMethod);
	}

	private void handleMoneySetter(MethodInvocation method, Source_Type sourceSourceType,
			Source_Type targetSourceType) {
		if (sourceSourceType == Source_Type.UNKOWN || targetSourceType == Source_Type.UNKOWN
				|| sourceSourceType == targetSourceType) {
			return;
		}
		addImport(cu, ast, EBLMoneyMapper.class.getName());
		MethodInvocation measureMapperMethod = ast.newMethodInvocation();
		if (sourceSourceType == Source_Type.OTHER) {
			measureMapperMethod.setName(ast.newSimpleName("toNeoMoney"));
		} else {
			measureMapperMethod.setName(ast.newSimpleName("toClassicMoney"));
		}
		if (isController) {
			autowireField(EBLMoneyMapper.class.getSimpleName(), "eblMoneyMapper");
		} else {
			addFieldWithBeanUtil(EBLMoneyMapper.class.getSimpleName(), "eblMoneyMapper", method);
		}
		measureMapperMethod.setExpression(ast.newSimpleName("eblMoneyMapper"));
		measureMapperMethod.arguments().add(ASTNodeBuilder.getNewNode(ast, (ASTNode) method.arguments().get(0)));
		method.arguments().clear();
		method.arguments().add(measureMapperMethod);
	}

	private void removePKsandChilds() {
		if (visitor.getConstructorList().isEmpty()) {
			List<FieldInfo> toBeRemoved = visitor.getFields().stream()
					.filter(f -> f.getFieldTypeSimpleName().endsWith("PK")
							|| f.getFieldDeclaration().getType() instanceof ParameterizedType
									&& (f.getFieldTypeSimpleName().contains("Set")
											|| f.getFieldTypeSimpleName().contains("Collection")))
					.collect(Collectors.toList());
			List<String> methods = new ArrayList<>();
			visitor.getMethodDeclarationList().forEach(md -> {
				methods.addAll(getTypes(md));
			});
			toBeRemoved.stream().filter(f -> !methods.contains(f.getFieldTypeSimpleName()))
					.forEach(f -> f.getFieldDeclaration().delete());
		}

	}

	private List<String> getTypes(MethodDeclaration md) {
		List<String> types = new ArrayList<>();
		List<SingleVariableDeclaration> parameters = md.parameters();
		types.addAll(parameters.stream().map(s -> s.getType().toString()).collect(Collectors.toList()));
		types.add(md.getReturnType2().toString());
		return types;
	}

	private void addLombokAnnotations() {
		addImport(cu, ast, lombok.Getter.class.getName());
		addImport(cu, ast, lombok.Setter.class.getName());
		MarkerAnnotation annotation = ast.newMarkerAnnotation();
		annotation.setTypeName(ast.newSimpleName(lombok.Getter.class.getSimpleName()));
		getParentType(cu).modifiers().add(0, annotation);
		annotation = ast.newMarkerAnnotation();
		annotation.setTypeName(ast.newSimpleName(lombok.Setter.class.getSimpleName()));
		getParentType(cu).modifiers().add(0, annotation);

	}

	private void handleParameterUtil() {
		if (!((ControllerASTVisitor) visitor).getMethodInvocMap().get("getSystemParameterValue").isEmpty()) {
			Set<MethodDeclaration> methodsToAnnotate = new HashSet<>();
			addImport(cu, ast, ParameterService.class.getName());
			addImport(cu, ast, ParameterType.class.getName());

			if (isController) {
				autowireField(ParameterService.class.getSimpleName(), "parameterService");

			} else {
				addImport(cu, ast, ContextUtil.class.getName());

			}
			for (MethodInvocation method : ((ControllerASTVisitor) visitor).getMethodInvocMap()
					.get("getSystemParameterValue")) {
				if (method.getExpression() instanceof MethodInvocation && ((MethodInvocation) method.getExpression())
						.getExpression().toString().equals("ParameterUtil")) {
					Expression parameterService = null;
					if (isController) {
						parameterService = ast.newSimpleName("parameterService");
					} else {
						parameterService = addFieldWithBeanUtil(ParameterService.class.getSimpleName(), null, method);
					}
					MethodInvocation getParameter = ast.newMethodInvocation();
					getParameter.setName(ast.newSimpleName("getParameter"));
					getParameter.arguments().add(ASTNodeBuilder.getNewNode(ast, (ASTNode) method.arguments().get(0)));
					getParameter.arguments().add(ast.newQualifiedName(ast.newSimpleName("ParameterType"),
							ast.newSimpleName("SYSTEM_PARAMETER")));
					getParameter.setExpression(parameterService);
					assignExpression(method.getParent(), getParameter, "parameterService", String.class.getSimpleName(),
							method);
					methodsToAnnotate.add(ASTNodeBuilder.getParentNode(getParameter, MethodDeclaration.class));
				}
			}
			addImport(cu, ast, SneakyThrows.class.getName());
			for (MethodDeclaration md : methodsToAnnotate) {
				MarkerAnnotation annotation = ast.newMarkerAnnotation();
				annotation.setTypeName(ast.newSimpleName(SneakyThrows.class.getSimpleName()));
				md.modifiers().add(0, annotation);
			}
		}

	}

	private void handleMoney() {
		// TODO Auto-generated method stub
		if (!((ControllerASTVisitor) visitor).getMethodInvocMap().get("getMoney").isEmpty()) {
			addImport(cu, ast, Money.class.getName());
			List<MethodInvocation> getMoneys = ((ControllerASTVisitor) visitor).getMethodInvocMap().get("getMoney");
			for (MethodInvocation getMoney : getMoneys) {
				if (Objects.nonNull(getMoney.getExpression())
						&& getMoney.getExpression().toString().equals("CurrencyHelper")) {
					getMoney.setExpression(ast.newSimpleName("Money"));
					getMoney.setName(ast.newSimpleName("of"));
				}
			}
		}
		if (!((ControllerASTVisitor) visitor).getMethodInvocMap().get("getDouble").isEmpty()) {
			addImport(cu, ast, Money.class.getName());
			List<MethodInvocation> getDoubles = ((ControllerASTVisitor) visitor).getMethodInvocMap().get("getDouble");
			for (MethodInvocation getDouble : getDoubles) {
				VariableInfo varOp = ((ControllerASTVisitor) visitor).getAllVariables()
						.get(getDouble.getExpression().toString());
				if (Objects.nonNull(varOp) && varOp.getVariableType().toString().equals("ResultSet")) {
					if (getDouble.getParent() instanceof MethodInvocation
							&& ((MethodInvocation) getDouble.getParent()).getName().toString().equals("setAmount")) {
						getDouble.setName(ast.newSimpleName("getBigDecimal"));
					}
				}
			}
		}
		if (!((ControllerASTVisitor) visitor).getMethodInvocMap().get("setAmount").isEmpty()) {
			addImport(cu, ast, Money.class.getName());
			addImport(cu, ast, BigDecimal.class.getName());
			List<MethodInvocation> setAmounts = ((ControllerASTVisitor) visitor).getMethodInvocMap().get("setAmount");
			for (MethodInvocation setAmount : setAmounts) {
				VariableInfo varOp = ((ControllerASTVisitor) visitor).getAllVariables()
						.get(setAmount.getExpression().toString());
				if (Objects.nonNull(varOp) && varOp.getVariableType().toString().equals("Money")) {
					if (setAmount.arguments().size() == 1
							&& !setAmount.arguments().get(0).toString().contains("BigDecimal")) {
						MethodInvocation valueOf = ast.newMethodInvocation();
						valueOf.setName(ast.newSimpleName("valueOf"));
						valueOf.setExpression(ast.newSimpleName(BigDecimal.class.getSimpleName()));
						valueOf.arguments().add(ASTNodeBuilder.getNewNode(ast, (ASTNode) setAmount.arguments().get(0)));
						setAmount.arguments().clear();
						setAmount.arguments().add(valueOf);
					}
				}
			}
		}
	}

	private void refactorLoggerSuspects() {
		if (visitor.getLoggerSuspects().size() > 0) {
			List<MethodInvocation> superLoggers = new ArrayList<>();
			for (MethodInvocation methodInvocation : visitor.getLoggerSuspects()) {
				if (methodInvocation.getExpression() instanceof SimpleName
						&& Objects.isNull(((ControllerASTVisitor) visitor).getAllVariables()
								.get(methodInvocation.getExpression().toString()))) {
					superLoggers.add(methodInvocation);
				}
			}
			refactorLoggers(superLoggers);
		}
	}

	private void deleteStatements() {
		((ControllerASTVisitor) visitor).statementsToDelete.forEach(VariableDeclarationStatement::delete);
	}

	private void handleBeanConversions() {
		if (!((ControllerASTVisitor) visitor).methodInvocMap.get("convert").isEmpty()) {
			List<MethodInvocation> converts = ((ControllerASTVisitor) visitor).methodInvocMap.get("convert");
			for (MethodInvocation convert : converts) {
				if (convert.arguments().size() == 3 && convert.arguments().get(2) instanceof TypeLiteral) {
					String fromObject = convert.arguments().get(0).toString();
					VariableInfo varOp = ((ControllerASTVisitor) visitor).getAllVariables().get(fromObject);
					if (Objects.nonNull(varOp)) {
						String converterClazz = varOp.getVariableType() + "Converter";
						addImport(cu, ast, this.refactorConfig.getTargetPackage() + ".vo.converter." + converterClazz);
						String methodName = "convertTo"
								+ ((TypeLiteral) convert.arguments().get(2)).getType().toString();
						if (!((ControllerASTVisitor) visitor).methodInvocMap.get("setGroup").isEmpty()) {
							MethodInvocation setGroup = ((ControllerASTVisitor) visitor).methodInvocMap.get("setGroup")
									.get(0);
							if (setGroup.arguments().size() == 1
									&& setGroup.arguments().get(0) instanceof StringLiteral) {
								methodName = methodName + "For"
										+ ((StringLiteral) setGroup.arguments().get(0)).getLiteralValue();
							}
							if (setGroup.getParent() instanceof ExpressionStatement) {
								setGroup.delete();
							}
						}
						MethodInvocation convertMethod = ast.newMethodInvocation();
						convertMethod.setName(ast.newSimpleName(methodName));
						convertMethod.setExpression(ast.newSimpleName(converterClazz));
						convertMethod.arguments().add(ast.newSimpleName(fromObject));
						Expression parentExp = (Expression) getBeanHelperExpression(convert);
						assignExpression(parentExp.getParent(), convertMethod, null, (Type) ASTNodeBuilder
								.getNewNode(ast, ((TypeLiteral) convert.arguments().get(2)).getType()), parentExp);
					}
				}
			}
			List<MethodInvocation> setFroms = ((ControllerASTVisitor) visitor).methodInvocMap.get("setFrom");
			for (MethodInvocation setFrom : setFroms) {
				if (setFrom.arguments().size() == 1 && setFrom.arguments().get(0) instanceof TypeLiteral
						&& setFrom.getParent() instanceof ExpressionStatement) {
					setFrom.getParent().delete();
				}
			}
		}

	}

	private ASTNode getBeanHelperExpression(MethodInvocation convert) {
		if (convert.getParent() instanceof SimpleName) {
			return convert.getParent();
		} else if (convert.toString().contains("BeanConversionHelper")) {
			return convert;
		}
		return null;
	}

	private void handleSystemExceptionCreations() {
		for (ExceptionInfo exceptionInfo : ((ControllerASTVisitor) visitor).systemExceptionCreations) {
			if (exceptionInfo.getExceptionInstance().arguments().size() == 2) {
				if (exceptionInfo.getCatchVariableName()
						.equals(exceptionInfo.getExceptionInstance().arguments().get(1).toString())) {
					MethodInvocation getMessage = ast.newMethodInvocation();
					getMessage.setName(ast.newSimpleName("getMessage"));
					getMessage.setExpression(ast.newSimpleName(exceptionInfo.getCatchVariableName()));
					exceptionInfo.getExceptionInstance().arguments().add(1, getMessage);
				}
			}
		}

	}

	private void handleTriggerPoint() {
		if (!((ControllerASTVisitor) visitor).methodInvocMap.get("getRequestContext").isEmpty()) {
			List<MethodInvocation> getRequestContexts = ((ControllerASTVisitor) visitor).methodInvocMap
					.get("getRequestContext");
			List<MethodInvocation> triggerPointgetParams = ((ControllerASTVisitor) visitor).methodInvocMap
					.get("triggerPointgetParam");
			if (Objects.nonNull(triggerPointgetParams)) {
				for (MethodInvocation getRequestContext : getRequestContexts) {
					if (triggerPointgetParams.contains(getRequestContext.getParent())) {
						Expression exp = null;
						if (isController) {
							autowireField(ContextUtil.class.getSimpleName(), "contextUtil");
							exp = ast.newSimpleName("contextUtil");
						} else {
							MethodInvocation getInstnace = ast.newMethodInvocation();
							getInstnace.setName(ast.newSimpleName("getInstance"));
							getInstnace.setExpression(ast.newSimpleName(ContextUtil.class.getSimpleName()));
							exp = getInstnace;

						}
						MethodInvocation getTxContext = ast.newMethodInvocation();
						getTxContext.setExpression(exp);
						getTxContext.setName(ast.newSimpleName("getTxContext"));
						QualifiedName qName = ast.newQualifiedName(ast.newSimpleName(ContextUtil.class.getSimpleName()),
								ast.newSimpleName("TRIGGER_POINT"));
						getTxContext.arguments().add(qName);
						MethodInvocation getParameter = (MethodInvocation) getRequestContext.getParent();
						assignExpression(getParameter.getParent(), getTxContext, "", String.class.getSimpleName(),
								getParameter);
					}
				}
			}
		}
	}

	protected void removeClassAnnotations() {
		List<ASTNode> modifiers = getParentType(cu).modifiers();
		List<ASTNode> classAnnotations = modifiers.stream().filter(node -> {
			return node instanceof Annotation && (removedImports.stream()
					.anyMatch(i -> i.endsWith(((Annotation) node).getTypeName().toString())));
		}).collect(Collectors.toList());
		modifiers.removeAll(classAnnotations);
	}

	protected void addComponentAnnotation() {
		addImport(cu, ast, Component.class.getName());
		MarkerAnnotation annotation = ast.newMarkerAnnotation();
		annotation.setTypeName(ast.newSimpleName(Component.class.getSimpleName()));
		getParentType(cu).modifiers().add(0, annotation);

	}

	protected void handleLogonAttrbiutes() {
		if (!((ControllerASTVisitor) visitor).methodInvocMap.get("getLogonAttributesVO").isEmpty()) {
			if (isController) {
				autowireField(ContextUtil.class.getSimpleName(), CONTEXT_UTIL);
			}
		}
		List<MethodInvocation> contextUtils = ((ControllerASTVisitor) visitor).methodInvocMap.get("getSecurityContext");
		for (MethodInvocation invocation : contextUtils) {
			invocation = ASTNodeBuilder.oldToNewMap.getOrDefault(invocation, invocation);
			if (!(invocation.getParent() instanceof MethodInvocation)) {
				invocation.delete();
			}
		}
		List<MethodInvocation> logonAttributes = ((ControllerASTVisitor) visitor).methodInvocMap
				.get("getLogonAttributesVO");
		for (MethodInvocation invocation : logonAttributes) {
			invocation = ASTNodeBuilder.oldToNewMap.getOrDefault(invocation, invocation);
			ASTNode parentNode = invocation.getParent();
			Expression logon = null;
			if (isController) {
				logon = ast.newSimpleName(CONTEXT_UTIL);
			} else {
				logon = addFieldWithBeanUtil(ContextUtil.class.getSimpleName(), null, invocation);
			}
			assignExpression(parentNode, callerLoginProfile(logon), CONTEXT_UTIL, LoginProfile.class.getSimpleName(),
					invocation);
		}
	}

	private void handleEntityManager() {

		if (!((ControllerASTVisitor) visitor).methodInvocMap.get("getEntityManager").isEmpty()) {
			addImport(cu, ast, EntityManager.class.getName());
			if (isController) {
				autowireField(EntityManager.class.getSimpleName(), ENTITY_MANAGER);
			}
		}
		List<MethodInvocation> entityManagers = ((ControllerASTVisitor) visitor).methodInvocMap.get("getEntityManager");
		for (MethodInvocation invocation : entityManagers) {
			Expression entityManager = null;
			if (isController) {
				entityManager = ast.newSimpleName(ENTITY_MANAGER);
			} else {
				entityManager = addFieldWithBeanUtil(EntityManager.class.getSimpleName(), "EntityManager", invocation);
			}
			ASTNode parentNode = invocation.getParent();
			assignExpression(parentNode, entityManager, ENTITY_MANAGER, EntityManager.class.getSimpleName(),
					invocation);
		}
	}

	private void handleQueryDaoInvocations() {

		List<MethodInvocation> queryDaos = ((ControllerASTVisitor) visitor).methodInvocMap.get("getQueryDAO");
		queryDaos.addAll(((ControllerASTVisitor) visitor).methodInvocMap.get("getObjectQueryDAO"));
		Map<String, String> autoWiredProxyMap = new HashMap<>();

		for (MethodInvocation invocation : queryDaos) {
			DaoInvocInfo info = getValidParentNodeForDao(invocation);
			String daoType = info.fieldType.replace("SQL", "").replace("Sql", "");
			String autoWireField = autoWiredProxyMap.get(daoType);
			Expression exp = null;
			if (Objects.isNull(autoWireField)) {
				autoWireField = daoType.substring(0, 1).toLowerCase() + daoType.substring(1);
				autoWiredProxyMap.put(daoType, autoWireField);
				if (isController) {
					autowireField(daoType, autoWireField);
				}
			}

			if (isController) {
				exp = ast.newSimpleName(autoWireField);
			} else {
				exp = addFieldWithBeanUtil(daoType, autoWireField, invocation);
			}
			ASTNode parentNode = info.parentNode;
			assignExpression(parentNode, exp, autoWireField, daoType, invocation);
		}
		for (MethodDeclaration md : visitor.getMethodDeclarationList()) {
			if (md.getReturnType2().toString().toUpperCase().indexOf("SQLDAO") >= 0) {
				String returnType = md.getReturnType2().toString().replace("SQL", "").replace("Sql", "");
				md.setReturnType2(ast.newSimpleType(ast.newSimpleName(returnType)));
			}
		}
		for (Type type : visitor.getTypes()) {
			if (type.toString().toUpperCase().indexOf("SQLDAO") >= 0) {
				String name = type.toString().replace("SQL", "").replace("Sql", "");
				((SimpleType) type).setName(ast.newSimpleName(name));
			}
		}
	}

	private DaoInvocInfo getValidParentNodeForDao(ASTNode daoCall) {
		if (daoCall instanceof MethodInvocation && "cast".equals(((MethodInvocation) daoCall).getName().toString())) {
			DaoInvocInfo info = new DaoInvocInfo();
			info.parentNode = daoCall.getParent() instanceof CastExpression ? daoCall.getParent().getParent()
					: daoCall.getParent();
			info.fieldType = ((TypeLiteral) ((MethodInvocation) daoCall).getExpression()).getType().toString();
			return info;
		} else if (daoCall instanceof CastExpression
				&& ((CastExpression) daoCall).getType().toString().endsWith("DAO")) {
			DaoInvocInfo info = new DaoInvocInfo();
			info.parentNode = daoCall.getParent();
			info.fieldType = ((CastExpression) daoCall).getType().toString();
			return info;
		} else if (daoCall instanceof ReturnStatement) {
			DaoInvocInfo info = new DaoInvocInfo();
			info.parentNode = daoCall;
			info.fieldType = ((MethodDeclaration) daoCall.getParent().getParent()).getReturnType2().toString();
			return info;
		} else if (daoCall instanceof VariableDeclarationFragment) {
			DaoInvocInfo info = new DaoInvocInfo();
			info.parentNode = daoCall;
			info.fieldType = ((VariableDeclarationStatement) daoCall.getParent()).getType().toString();
			return info;
		} else if (daoCall instanceof Assignment) {
			DaoInvocInfo info = new DaoInvocInfo();
			info.parentNode = daoCall;
			String varName = ((Assignment) daoCall).getLeftHandSide().toString();
			Block block = ASTNodeBuilder.getParentNode(daoCall, Block.class);
			info.fieldType = findVarTypeFromBlocks(block, varName);
			return info;
		}

		return getValidParentNodeForDao(daoCall.getParent());
	}

	private String findVarTypeFromBlocks(Block block, String varName) {
		List<ASTNode> statements = block.statements();
		Optional<ASTNode> result = statements.stream().filter(statement -> {
			if (statement instanceof VariableDeclarationStatement) {
				VariableDeclarationStatement vds = (VariableDeclarationStatement) statement;
				return ((VariableDeclarationFragment) vds.fragments().get(0)).getName().toString().equals(varName);
			}
			return false;
		}).findFirst();
		return result.isPresent() ? ((VariableDeclarationStatement) result.get()).getType().toString()
				: findVarTypeFromBlocks(ASTNodeBuilder.getParentNode(block, Block.class), varName);

	}

	private MethodInvocation callerLoginProfile(Expression exp) {
		MethodInvocation callerLoginProfile = ast.newMethodInvocation();
		callerLoginProfile.setName(ast.newSimpleName("callerLoginProfile"));
		callerLoginProfile.setExpression(exp);
		return callerLoginProfile;
	}

	private void handleLocalDate() {
		if (!((ControllerASTVisitor) visitor).localDateCreations.isEmpty()) {
			addImport(cu, ast, LocalDate.class.getName());
			if (isController) {
				autowireField(LocalDate.class.getSimpleName(), "localDateUtil");
			} else {
				addImport(cu, ast, ContextUtil.class.getName());
			}
		}
		for (ClassInstanceCreation localDateInstance : ((ControllerASTVisitor) visitor).localDateCreations) {
			MethodInvocation parent = getValidParentNodeForSourceCheck(localDateInstance);
			if (Objects.isNull(parent) || isSourcePackageObject(parent)) {
				MethodInvocation neoLocalDateMethod = LocalDateMapper.getNeoLocalDate(ast,
						localDateInstance.arguments(), isController ? ast.newSimpleName("localDateUtil")
								: addFieldWithBeanUtil("LocalDate", "localDateUtil", localDateInstance));
				ASTNode parentNode = localDateInstance.getParent();
				assignExpression(parentNode, neoLocalDateMethod, null, ZonedDateTime.class.getSimpleName(),
						localDateInstance);
			} else {
				addImport(cu, ast, Location.class.getName());
				localDateInstance.setType(ast.newSimpleType(ast
						.newName(dotPattern.split(com.ibsplc.icargo.framework.util.time.LocalDate.class.getName()))));
			}
		}
		for (MethodInvocation method : ((ControllerASTVisitor) visitor).methodInvocMap.get("toCalendar")) {
			method = ASTNodeBuilder.oldToNewMap.getOrDefault(method, method);
			MethodInvocation neu = ast.newMethodInvocation();
			neu.setName(ast.newSimpleName("toLocalDateTime"));
			neu.setExpression((Expression) ASTNodeBuilder.getNewNode(ast, method.getExpression()));
			ASTNode parentNode = method.getParent();
			assignExpression(parentNode, neu, "localDateTime", LocalDateTime.class.getSimpleName(), method);
		}

		if (!((ControllerASTVisitor) visitor).methodInvocMap.get("toDisplayDateOnlyFormat").isEmpty()) {
			addImport(cu, ast, LocalDate.class.getName());
			addImport(cu, ast, DateTimeFormatter.class.getName());
			for (MethodInvocation method : ((ControllerASTVisitor) visitor).methodInvocMap
					.get("toDisplayDateOnlyFormat")) {
				method = ASTNodeBuilder.oldToNewMap.getOrDefault(method, method);
				MethodInvocation parent = getValidParentNodeForSourceCheck(method);
				if (Objects.isNull(parent) || isSourcePackageObject(parent)) {
					LocalDateMapper.refactorDisplayFormat(ast, method, "toDisplayDateOnlyFormat");
				}
			}
		}
		if (!((ControllerASTVisitor) visitor).methodInvocMap.get("toDisplayTimeOnlyFormat").isEmpty()) {
			addImport(cu, ast, LocalDate.class.getName());
			addImport(cu, ast, DateTimeFormatter.class.getName());
			for (MethodInvocation method : ((ControllerASTVisitor) visitor).methodInvocMap
					.get("toDisplayTimeOnlyFormat")) {
				method = ASTNodeBuilder.oldToNewMap.getOrDefault(method, method);
				MethodInvocation parent = getValidParentNodeForSourceCheck(method);
				if (Objects.isNull(parent) || isSourcePackageObject(parent)) {
					LocalDateMapper.refactorDisplayFormat(ast, method, "toDisplayTimeOnlyFormat");
				}
			}
		}
		if (!((ControllerASTVisitor) visitor).methodInvocMap.get("toDisplayFormat").isEmpty()) {
			addImport(cu, ast, LocalDate.class.getName());
			addImport(cu, ast, DateTimeFormatter.class.getName());
			for (MethodInvocation method : ((ControllerASTVisitor) visitor).methodInvocMap.get("toDisplayFormat")) {
				method = ASTNodeBuilder.oldToNewMap.getOrDefault(method, method);
				if (method.arguments().size() == 0) {
					MethodInvocation parent = getValidParentNodeForSourceCheck(method);
					if (Objects.isNull(parent) || isSourcePackageObject(parent)) {
						LocalDateMapper.refactorDisplayFormat(ast, method, "toDisplayFormat");
					}
				}
			}
		}
		if (!((ControllerASTVisitor) visitor).methodInvocMap.get("setDate").isEmpty()) {
			addImport(cu, ast, LocalDate.class.getName());
			for (MethodInvocation method : ((ControllerASTVisitor) visitor).methodInvocMap.get("setDate")) {
				method = ASTNodeBuilder.oldToNewMap.getOrDefault(method, method);
				if (method.getExpression() instanceof SimpleName && method.getParent() instanceof ExpressionStatement) {
					VariableInfo info = ((ControllerASTVisitor) visitor).allVariables
							.get(method.getExpression().toString());
					if (Objects.nonNull(info) && (info.getVariableType().toString().equals("ZonedDateTime")
							|| info.getVariableType().toString().equals("LocalDate"))) {
						LocalDateMapper.refactorSetDateMethods(ast, method);
					}
				}
			}
		}
		if (!((ControllerASTVisitor) visitor).methodInvocMap.get("setTime").isEmpty()) {
			addImport(cu, ast, LocalDate.class.getName());
			for (MethodInvocation method : ((ControllerASTVisitor) visitor).methodInvocMap.get("setTime")) {
				method = ASTNodeBuilder.oldToNewMap.getOrDefault(method, method);
				if (method.getExpression() instanceof SimpleName && method.getParent() instanceof ExpressionStatement) {
					VariableInfo info = ((ControllerASTVisitor) visitor).allVariables
							.get(method.getExpression().toString());
					if (Objects.nonNull(info) && (info.getVariableType().toString().equals("ZonedDateTime")
							|| info.getVariableType().toString().equals("LocalDate"))) {
						LocalDateMapper.refactorSetDateMethods(ast, method);
					}
				}
			}
		}
		if (!((ControllerASTVisitor) visitor).methodInvocMap.get("setDateAndTime").isEmpty()) {
			addImport(cu, ast, LocalDate.class.getName());
			for (MethodInvocation method : ((ControllerASTVisitor) visitor).methodInvocMap.get("setDateAndTime")) {
				method = ASTNodeBuilder.oldToNewMap.getOrDefault(method, method);
				if (method.getExpression() instanceof SimpleName && method.getParent() instanceof ExpressionStatement) {
					VariableInfo info = ((ControllerASTVisitor) visitor).allVariables
							.get(method.getExpression().toString());
					if (Objects.nonNull(info) && (info.getVariableType().toString().equals("ZonedDateTime")
							|| info.getVariableType().toString().equals("LocalDate"))) {
						LocalDateMapper.refactorSetDateMethods(ast, method);
					}
				}
			}
		}

		handleCompareMethods(((ControllerASTVisitor) visitor).methodInvocMap.get("isLesserThan"), "isBefore");

		handleCompareMethods(((ControllerASTVisitor) visitor).methodInvocMap.get("isGreaterThan"), "isAfter");

		if (!((ControllerASTVisitor) visitor).methodInvocMap.get("toGMTDate").isEmpty()) {
			addImport(cu, ast, LocalDate.class.getName());
			for (MethodInvocation method : ((ControllerASTVisitor) visitor).methodInvocMap.get("toGMTDate")) {
				method = ASTNodeBuilder.oldToNewMap.getOrDefault(method, method);
				if (method.getExpression() instanceof SimpleName || (method.getExpression() instanceof MethodInvocation
						&& (isSourcePackageObject((MethodInvocation) method.getExpression())))) {
					addImport(cu, ast, LocalDate.class.getName());
					if (isController) {
						autowireField(LocalDate.class.getSimpleName(), "localDateUtil");
					} else {
						addFieldWithBeanUtil(LocalDate.class.getSimpleName(), "localDateUtil", method);
					}
					MethodInvocation toUTCTime = ast.newMethodInvocation();
					toUTCTime.setName(ast.newSimpleName("toUTCTime"));
					toUTCTime.setExpression(ast.newSimpleName("localDateUtil"));
					toUTCTime.arguments().add(ASTNodeBuilder.getNewNode(ast, method.getExpression()));
					assignExpression(method.getParent(), toUTCTime, "utcTime", ZonedDateTime.class.getSimpleName(),
							method);
				}
			}
		}
	}

	private void populateSourceType() {
		Map<String, Source_Type> importMap = new HashMap<>();
		visitor.getIcgOtherImports().stream().map(i -> i.getName().toString()).forEach(i -> {
			importMap.put(i.substring(i.lastIndexOf(".") + 1),
					i.contains(refactorConfig.getSourcePackage()) ? Source_Type.SOURCE : Source_Type.OTHER);
			((ControllerASTVisitor) visitor).allVariables.values().forEach(variableInfo -> {
				if (Objects.nonNull(variableInfo.getVariableType())
						&& importMap.containsKey(variableInfo.getVariableType().toString())) {
					variableInfo.setSourceType(importMap.get(variableInfo.getVariableType().toString()));
				} else {
					variableInfo.setSourceType(Source_Type.UNKOWN);
				}
			});
		});
	}

	private boolean isSourcePackageObject(MethodInvocation method) {
		if (method.getExpression() instanceof SimpleName) {
			VariableInfo info = ((ControllerASTVisitor) visitor).allVariables.get(method.getExpression().toString());
			if (Objects.nonNull(info)) {
				return Source_Type.SOURCE == info.getSourceType();
			}
		}
		return true;

	}

	private boolean isOtherPackageObject(MethodInvocation method) {
		if (method.getExpression() instanceof SimpleName) {
			VariableInfo info = ((ControllerASTVisitor) visitor).allVariables.get(method.getExpression().toString());
			if (Objects.nonNull(info)) {
				return Source_Type.OTHER == info.getSourceType();
			}
		}
		return true;

	}

	private MethodInvocation getValidParentNodeForSourceCheck(ASTNode astNode) {
		if (astNode instanceof ClassInstanceCreation) {
			return astNode.getParent() instanceof MethodInvocation ? (MethodInvocation) astNode.getParent() : null;
		} else if (astNode instanceof MethodInvocation) {
			return ((MethodInvocation) astNode).getExpression() instanceof MethodInvocation
					? (MethodInvocation) ((MethodInvocation) astNode).getExpression()
					: null;
		}
		return null;
	}

	private void handleCompareMethods(List<MethodInvocation> methods, String methodName) {
		for (MethodInvocation method : methods) {
			if (method.getExpression() instanceof SimpleName) {
				VariableInfo info = ((ControllerASTVisitor) visitor).allVariables
						.get(method.getExpression().toString());
				if (Objects.nonNull(info) && (info.getVariableType().toString().equals("LocalDate"))) {
					method.setName(ast.newSimpleName(methodName));
				}
			} else if (method.arguments().size() == 1 && method.arguments().get(0) instanceof SimpleName) {
				VariableInfo info = ((ControllerASTVisitor) visitor).allVariables
						.get(method.arguments().get(0).toString());
				if (Objects.nonNull(info) && (info.getVariableType().toString().equals("LocalDate"))) {
					method.setName(ast.newSimpleName(methodName));
				}
			}
		}
	}

	private void handleMesaure() {
		if (!((ControllerASTVisitor) visitor).measureCreations.isEmpty()) {
			addImport(cu, ast, Quantities.class.getName());
			addImport(cu, ast, BigDecimal.class.getName());
			if (isController) {
				autowireField(Quantities.class.getSimpleName(), "quantities");
			} else {
				addImport(cu, ast, ContextUtil.class.getName());
			}
		}
		for (ClassInstanceCreation measureInstance : ((ControllerASTVisitor) visitor).measureCreations) {
			MethodInvocation quantityMethod = MeasureMapper.getQuantity(cu, ast,
					isController ? ast.newSimpleName("quantities")
							: addFieldWithBeanUtil(Quantities.class.getSimpleName(), null, measureInstance),
					measureInstance.arguments());
			if (quantityMethod.toString().contains("BigDecimal")) {
				addImport(cu, ast, BigDecimal.class.getName());
			}
			ASTNode parentNode = measureInstance.getParent();
			if (parentNode instanceof MethodInvocation && isOtherPackageObject((MethodInvocation) parentNode)) {
				addImport(cu, ast, com.ibsplc.icargo.framework.util.unit.MeasureMapper.class.getName());
				if (isController) {
					autowireField("MeasureMapper", "measureMapper");
				} else {
					addFieldWithBeanUtil("MeasureMapper", "measureMapper", measureInstance);
				}
				MethodInvocation toMeasure = ast.newMethodInvocation();
				toMeasure.setName(ast.newSimpleName("toMeasure"));
				toMeasure.setExpression(ast.newSimpleName("measureMapper"));
				toMeasure.arguments().add(quantityMethod);
				quantityMethod = toMeasure;
			}
			assignExpression(parentNode, quantityMethod, null, Quantity.class.getSimpleName(), measureInstance);

		}
		for (MethodInvocation addMeasureValues : ((ControllerASTVisitor) visitor).methodInvocMap
				.get("addMeasureValues")) {
			addMeasureValues = ASTNodeBuilder.oldToNewMap.getOrDefault(addMeasureValues, addMeasureValues);
			MethodInvocation method = MeasureMapper.handleMathOps(ast, addMeasureValues);
			assignExpression(addMeasureValues.getParent(), method, null, Quantity.class.getSimpleName(),
					addMeasureValues);
		}
		for (MethodInvocation addMeasureValues : ((ControllerASTVisitor) visitor).methodInvocMap
				.get("subtractMeasureValues")) {
			addMeasureValues = ASTNodeBuilder.oldToNewMap.getOrDefault(addMeasureValues, addMeasureValues);
			MethodInvocation method = MeasureMapper.handleMathOps(ast, addMeasureValues);
			assignExpression(addMeasureValues.getParent(), method, null, Quantity.class.getSimpleName(),
					addMeasureValues);
		}
	}

	private void removeIcgExceptions() {
		List<Type> validExceptions = new ArrayList<>();
		for (MethodDeclaration med : visitor.getMethodDeclarationList()) {
			List<SimpleType> throwsList = med.thrownExceptionTypes();
			if (Objects.nonNull(throwsList)) {
				List<SimpleType> tobeRemovedList = new ArrayList<>();
				for (SimpleType throwsType : throwsList) {
					if (expListToRemove.contains(throwsType.getName().toString())
							|| removedImports.stream().anyMatch(i -> i.endsWith(throwsType.getName().toString()))) {
						tobeRemovedList.add(throwsType);
					}
				}
				throwsList.removeAll(tobeRemovedList);
				Optional<SimpleType> proxyOp = throwsList.stream().filter(t -> t.toString().equals("ProxyException"))
						.findFirst();
				if (proxyOp.isPresent()) {
					addImport(cu, ast, BusinessException.class.getName());
					proxyOp.get().setName(ast.newName(BusinessException.class.getSimpleName()));
				}
				validExceptions.addAll(throwsList);
			}

			List modifiers = med.modifiers();
			List<Object> removeAnnotations = new ArrayList<>();
			for (Object modifier : modifiers) {
				if (modifier instanceof Annotation
						&& annotationsToRemove.stream().anyMatch(a -> modifier.toString().indexOf(a) >= 0)) {
					removeAnnotations.add(modifier);
				}
			}
			modifiers.removeAll(removeAnnotations);

		}
		for (ConstructorInfo constructorInfo : visitor.getConstructorList()) {
			List<SimpleType> throwsList = constructorInfo.getConstructorDecl().thrownExceptionTypes();
			if (Objects.nonNull(throwsList)) {
				List<SimpleType> tobeRemovedList = new ArrayList<>();
				for (SimpleType throwsType : throwsList) {
					if (expListToRemove.contains(throwsType.getName().toString())
							|| removedImports.stream().anyMatch(i -> i.endsWith(throwsType.getName().toString()))) {
						tobeRemovedList.add(throwsType);
					}
				}
				throwsList.removeAll(tobeRemovedList);
				validExceptions.addAll(throwsList);
			}
		}
		Set<TryStatement> trySet = new HashSet<>();
		for (CatchClause catchClause : ((ControllerASTVisitor) visitor).catchClauseList) {
			org.eclipse.jdt.core.dom.Type type = catchClause.getException().getType();
			if (type instanceof UnionType) {
				UnionType unionType = (UnionType) type;
				List<Type> toBeRemoved = (List<Type>) unionType.types().stream()
						.filter(t -> expListToRemove.contains(t.toString())
								|| removedImports.stream().anyMatch(i -> i.endsWith(t.toString())))
						.collect(Collectors.toList());
				unionType.types().removeAll(toBeRemoved);
				if (unionType.types().size() == 0) {
					trySet.add((TryStatement) catchClause.getParent());
					catchClause.delete();
				} else {
					validExceptions.addAll(unionType.types());
				}
			} else {
				String typeName = type instanceof QualifiedType ? ((QualifiedType) type).getName().toString()
						: type.toString();
				if (expListToRemove.contains(typeName) || removedImports.stream().anyMatch(i -> i.endsWith(typeName))) {
					trySet.add((TryStatement) catchClause.getParent());
					catchClause.delete();
				} else {
					validExceptions.add(type);
				}
			}
		}

		for (TryStatement tryStatement : trySet) {
			if (tryStatement.catchClauses().isEmpty()) {
				tryStatement.setFinally(ast.newBlock());
			}
		}

		for (Type validType : validExceptions) {
			if (validType instanceof SimpleType && cu.imports().stream()
					.noneMatch(i -> ((ImportDeclaration) i).getName().toString().endsWith(validType.toString()))) {
				addImport(cu, ast, this.packageToCreate.replace("component", "exception") + "." + validType.toString());
			}
		}
	}

	private void refactorProxyInstantiation() {
		Map<String, String> autoWiredProxyMap = new HashMap<>();
		List<ClassInstanceInvocation> proxyInstances = ((ControllerASTVisitor) visitor).proxyInstanceCreations;
		for (ClassInstanceInvocation controllerInstance : proxyInstances) {
			String controllerType = controllerInstance.getType();
			ASTNode parentNode = controllerInstance.getControllerNode();
			assignProxyClass(autoWiredProxyMap, controllerType, parentNode);
		}
		List<MethodInvocation> getMethods = ((ControllerASTVisitor) visitor).methodInvocMap.get("get");
		for (MethodInvocation getMethod : getMethods) {
			getMethod = ASTNodeBuilder.oldToNewMap.getOrDefault(getMethod, getMethod);
			if (getMethod.arguments().size() == 1 && getMethod.arguments().get(0) instanceof TypeLiteral) {
				TypeLiteral typeL = (TypeLiteral) getMethod.arguments().get(0);
				if (typeL.getType().toString().endsWith("Proxy")) {
					assignProxyClass(autoWiredProxyMap, typeL.getType().toString(), getMethod.getParent());
				}
			} else if (getMethod.arguments().size() == 1 && getMethod.arguments().get(0) instanceof QualifiedName) {
				QualifiedName qName = (QualifiedName) getMethod.arguments().get(0);
				if (qName.getQualifier().toString().equals("ZonedDateTime")
						|| qName.getQualifier().toString().equals("LocalDateTime")) {
					String method = LocalDateMapper.getZonedDateTimeMethod(qName.getName().toString());
					getMethod.setName(ast.newSimpleName(method));
					getMethod.arguments().clear();
				}
			}
		}
		Optional<FieldInfo> o = this.visitor.getFields().stream()
				.filter(f -> f.getFieldTypeSimpleName().equals("Proxy")).findFirst();
		if (o.isPresent()) {
			o.get().getFieldDeclaration().delete();
		}
	}

	private void assignProxyClass(Map<String, String> autoWiredProxyMap, String controllerType, ASTNode parentNode) {
		Expression neuNode = null;
		String autoWireField = null;
		if (isController) {
			autoWireField = autoWiredProxyMap.get(controllerType);
			if (Objects.isNull(autoWireField)) {
				autoWireField = controllerType.substring(0, 1).toLowerCase() + controllerType.substring(1);
				autoWiredProxyMap.put(controllerType, autoWireField);
				autowireField(controllerType, autoWireField);
			}
			neuNode = ast.newSimpleName(autoWireField);
		} else {
			neuNode = addFieldWithBeanUtil(controllerType, autoWireField, parentNode);
		}
		assignExpression(parentNode, neuNode, autoWireField, controllerType, null);
	}

	private void addZoneDateTimeSetter() {
		List<String> calendarFields = new ArrayList<>();
		for (FieldInfo fieldInfo : visitor.getFields()) {
			if (fieldInfo.getFieldTypeSimpleName().equals("Calendar")) {
				calendarFields.add(fieldInfo.getFieldName());
				addImport(cu, ast, ZonedDateTime.class.getName());
				String setterName = GetterSetterUtil.setterName(fieldInfo.getFieldName());
				MethodDeclaration md = ast.newMethodDeclaration();
				md.setName(ast.newSimpleName(setterName));
				SingleVariableDeclaration var = ast.newSingleVariableDeclaration();
				var.setType(ast.newSimpleType(ast.newSimpleName(ZonedDateTime.class.getSimpleName())));
				var.setName(ast.newSimpleName(fieldInfo.getFieldName()));
				md.parameters().add(var);
				Block body = ast.newBlock();
				IfStatement ifStatement = ast.newIfStatement();
				addImport(cu, ast, Objects.class.getName());
				MethodInvocation objects = ast.newMethodInvocation();
				objects.setName(ast.newSimpleName("nonNull"));
				objects.setExpression(ast.newSimpleName(Objects.class.getSimpleName()));
				objects.arguments().add(ast.newSimpleName(fieldInfo.getFieldName()));
				ifStatement.setExpression(objects);
				Block thenBlock = ast.newBlock();
				ifStatement.setThenStatement(thenBlock);
				Assignment assignment = ast.newAssignment();
				ExpressionStatement expSt = ast.newExpressionStatement(assignment);
				FieldAccess field = ast.newFieldAccess();
				field.setName(ast.newSimpleName(fieldInfo.getFieldName()));
				field.setExpression(ast.newThisExpression());
				assignment.setLeftHandSide(field);
				assignment.setOperator(Operator.ASSIGN);
				MethodInvocation toLocalDateTime = ast.newMethodInvocation();
				toLocalDateTime.setName(ast.newSimpleName("toLocalDateTime"));
				toLocalDateTime.setExpression(ast.newSimpleName(fieldInfo.getFieldName()));
				assignment.setRightHandSide(toLocalDateTime);
				thenBlock.statements().add(expSt);
				body.statements().add(ifStatement);
				md.setBody(body);
				md.modifiers().add(ast.newModifier(ModifierKeyword.PUBLIC_KEYWORD));
				md.setReturnType2(ast.newPrimitiveType(PrimitiveType.VOID));
				getParentType(cu).bodyDeclarations().add(md);
			}
		}
		for (String field : calendarFields) {
			List<FieldAccess> fieldAccessList = ((ControllerASTVisitor) visitor).calendarFieldAccess.stream()
					.filter(fieldAccess -> fieldAccess.getName().toString().equals(field)).collect(Collectors.toList());
			for (FieldAccess fieldAccess : fieldAccessList) {
				MethodDeclaration md = ASTNodeBuilder.getParentNode(fieldAccess, MethodDeclaration.class);
				if (md.getName().toString().equals(GetterSetterUtil.setterName(field))) {
					continue;
				}
				MethodInvocation setterMethod = ast.newMethodInvocation();
				setterMethod.setName(ast.newSimpleName(GetterSetterUtil.setterName(field)));
				ExpressionStatement statement = ast.newExpressionStatement(setterMethod);
				Assignment assignment = (Assignment) fieldAccess.getParent();
				setterMethod.arguments().add(ASTNodeBuilder.getNewNode(ast, assignment.getRightHandSide()));
				Statement parentStatement = ASTNodeBuilder.getParentNode(assignment, Statement.class);
				Block block = (Block) parentStatement.getParent();
				block.statements().add(block.statements().indexOf(parentStatement), statement);
				parentStatement.delete();
			}
		}
	}

	private void handleCacheCalls() {
		((ControllerASTVisitor) visitor).cacheFactories.forEach(v -> v.delete());
		List<MethodInvocation> cacheCalls = ((ControllerASTVisitor) visitor).methodInvocMap.get("getCache");
		Map<String, String> autoWiredCacheMap = new HashMap<>();
		for (MethodInvocation cacheCall : cacheCalls) {
			String cacheType = null;
			if (cacheCall.arguments().get(0) instanceof QualifiedName) {
				QualifiedName qName = (QualifiedName) cacheCall.arguments().get(0);
				cacheType = ASTNodeBuilder.getTypeName(qName.getQualifier());
			} else if (cacheCall.getParent().getParent() instanceof VariableDeclarationStatement) {
				VariableDeclarationStatement st = (VariableDeclarationStatement) cacheCall.getParent().getParent();
				cacheType = ASTNodeBuilder.getTypeName(st.getType());
			} else {
				continue;
			}
			String autoWireField = autoWiredCacheMap.get(cacheType);
			Expression exp = null;
			if (Objects.isNull(autoWireField)) {
				autoWireField = cacheType.substring(0, 1).toLowerCase() + cacheType.substring(1);
				autoWiredCacheMap.put(cacheType, autoWireField);
				if (isController) {
					autowireField(cacheType, autoWireField);
				}
			}

			if (isController) {
				exp = ast.newSimpleName(autoWireField);
			} else {
				exp = addFieldWithBeanUtil(cacheType, autoWireField, cacheCall);
			}
			ASTNode parentNode = ASTNodeBuilder.getParentNode(cacheCall, VariableDeclarationFragment.class,
					MethodInvocation.class, ReturnStatement.class);
			if (parentNode instanceof VariableDeclarationFragment) {
				String varName = ((VariableDeclarationFragment) parentNode).getName().toString();
				if (isController && varName.equals(autoWireField)) {
					parentNode.getParent().delete();
				} else {
					((VariableDeclarationFragment) parentNode).setInitializer(exp);
				}
				VariableDeclarationStatement statement = (VariableDeclarationStatement) parentNode.getParent();
				statement.setType(ast.newSimpleType(ast.newSimpleName(cacheType)));
			} else if (parentNode instanceof MethodInvocation) {
				((MethodInvocation) parentNode).setExpression(exp);
			} else if (parentNode instanceof ReturnStatement) {
				((ReturnStatement) parentNode).setExpression(exp);
			}
		}

	}

	private void assignExpression(ASTNode parentNode, Expression exp, String autoWireField, String fieldType,
			ASTNode currentNode) {
		Type type = null;
		if (Objects.nonNull(Primitive_Types.get(fieldType))) {
			type = ast.newPrimitiveType(Primitive_Types.get(fieldType));
		} else {
			type = ast.newSimpleType(ast.newSimpleName(fieldType));
		}

		assignExpression(parentNode, exp, autoWireField, type, currentNode);
	}

	private void assignExpression(ASTNode parentNode, Expression exp, String autoWireField, Type fieldType,
			ASTNode currentNode) {
		if (parentNode instanceof VariableDeclarationFragment) {
			String varName = ((VariableDeclarationFragment) parentNode).getName().toString();
			if (isController && varName.equals(autoWireField)) {
				parentNode.getParent().delete();
			} else {
				((VariableDeclarationFragment) parentNode).setInitializer(exp);
			}
			VariableDeclarationStatement statement = (VariableDeclarationStatement) parentNode.getParent();
			statement.setType(fieldType);

		} else if (parentNode instanceof MethodInvocation) {
			MethodInvocation pNode = (MethodInvocation) parentNode;
			if (pNode.arguments().contains(currentNode)) {
				pNode.arguments().add(pNode.arguments().indexOf(currentNode), exp);
				currentNode.delete();
			} else {
				pNode.setExpression(exp);
			}
		} else if (parentNode instanceof ReturnStatement) {
			((ReturnStatement) parentNode).setExpression(exp);
		} else if (parentNode instanceof Assignment) {
			String varName = ((Assignment) parentNode).getLeftHandSide().toString();
			if (isController && varName.equals(autoWireField)) {
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
			} else if (pNode.getRightOperand().equals(currentNode)) {
				pNode.setRightOperand(exp);
			} else if (pNode.extendedOperands().contains(currentNode)) {
				pNode.extendedOperands().add(pNode.extendedOperands().indexOf(currentNode), exp);
				pNode.extendedOperands().remove(currentNode);
			}
		} else if (parentNode instanceof ParenthesizedExpression) {
			((ParenthesizedExpression) parentNode).setExpression(exp);
		}
	}

	private void removeMethodAnnotations() {
		for (MethodDeclaration md : visitor.getMethodDeclarationList()) {
			md.modifiers().removeIf(m -> {
				return m instanceof Annotation && (removedImports.stream()
						.anyMatch(i -> i.endsWith(((Annotation) m).getTypeName().toString())));
			});
		}
	}

	private void handleQuantitySystemValue() {
		for (MethodInvocation inv : ((ControllerASTVisitor) visitor).methodInvocMap.get("getSystemValue")) {
			inv = ASTNodeBuilder.oldToNewMap.getOrDefault(inv, inv);
			if (!(inv.getExpression() instanceof MethodInvocation)
					|| isSourcePackageObject((MethodInvocation) inv.getExpression())) {
				handleQuantityValues(inv, "double", "getValue", "doubleValue");
			}
		}
		for (MethodInvocation inv : ((ControllerASTVisitor) visitor).methodInvocMap.get("getSystemUnit")) {
			inv = ASTNodeBuilder.oldToNewMap.getOrDefault(inv, inv);
			if (!(inv.getExpression() instanceof MethodInvocation)
					|| isSourcePackageObject((MethodInvocation) inv.getExpression())) {
				handleQuantityValues(inv, "String", "getUnit", "getName");
			}
		}
		for (MethodInvocation inv : ((ControllerASTVisitor) visitor).methodInvocMap.get("getRoundedSystemValue")) {
			inv = ASTNodeBuilder.oldToNewMap.getOrDefault(inv, inv);
			if (!(inv.getExpression() instanceof MethodInvocation)
					|| isSourcePackageObject((MethodInvocation) inv.getExpression())) {
				handleQuantityValues(inv, "double", "getRoundedValue", "doubleValue");
			}
		}
		for (MethodInvocation inv : ((ControllerASTVisitor) visitor).methodInvocMap.get("getRoundedDisplayValue")) {
			inv = ASTNodeBuilder.oldToNewMap.getOrDefault(inv, inv);
			if (!(inv.getExpression() instanceof MethodInvocation)
					|| isSourcePackageObject((MethodInvocation) inv.getExpression())) {
				handleQuantityValues(inv, "double", "getRoundedDisplayValue", "doubleValue");
			}
		}
		for (MethodInvocation inv : ((ControllerASTVisitor) visitor).methodInvocMap.get("getDisplayValue")) {
			inv = ASTNodeBuilder.oldToNewMap.getOrDefault(inv, inv);
			if (!(inv.getExpression() instanceof MethodInvocation)
					|| isSourcePackageObject((MethodInvocation) inv.getExpression())) {
				handleQuantityValues(inv, "double", "getDisplayValue", "doubleValue");
			}
		}
		for (MethodInvocation inv : ((ControllerASTVisitor) visitor).methodInvocMap.get("getDisplayUnit")) {
			inv = ASTNodeBuilder.oldToNewMap.getOrDefault(inv, inv);
			if (!(inv.getExpression() instanceof MethodInvocation)
					|| isSourcePackageObject((MethodInvocation) inv.getExpression())) {
				handleQuantityValues(inv, "String", "getDisplayUnit", "getName");
			}
		}
	}

	private void handleQuantityValues(MethodInvocation currentNode, String type, String... methods) {
		if (Objects.isNull(currentNode.getExpression())) {
			return;
		}
		Expression exp = (Expression) ASTNodeBuilder.getNewNode(ast, currentNode.getExpression());
		for (String method : methods) {
			MethodInvocation newNode = ast.newMethodInvocation();
			newNode.setName(ast.newSimpleName(method));
			newNode.setExpression(exp);
			exp = newNode;
		}
		assignExpression(currentNode.getParent(), exp, null, type, currentNode);
	}

	private void handleKeyUtils() {
		for (MethodInvocation md : ((ControllerASTVisitor) visitor).methodInvocMap.get("getCriterion")) {
			ASTNode parent = md.getParent();
			if (parent instanceof VariableDeclarationFragment || parent instanceof ReturnStatement) {
				parent.getParent().delete();
			} else if (parent instanceof Assignment) {
				parent.delete();
			}
		}
		for (MethodInvocation md : ((ControllerASTVisitor) visitor).methodInvocMap.get("getKey")) {
			md.arguments().clear();
		}
		for (MethodInvocation md : ((ControllerASTVisitor) visitor).methodInvocMap.get("getSequenceKey")) {
			md.setName(ast.newSimpleName("getKey"));
			md.arguments().clear();
		}
	}

	private void handlegetBean() {
		for (MethodInvocation md : ((ControllerASTVisitor) visitor).methodInvocMap.get("getBean")) {
			md = ASTNodeBuilder.oldToNewMap.getOrDefault(md, md);
			try {
				CastExpression exp = ASTNodeBuilder.getParentNode(md, CastExpression.class);
				if (Objects.nonNull(exp)) {
					Object arg = md.arguments().get(0);
					Expression beanExp = addFieldWithBeanUtil((Type) ASTNodeBuilder.getNewNode(ast, exp.getType()),
							null);
					assignExpression(exp.getParent(), beanExp, null,
							(Type) ASTNodeBuilder.getNewNode(ast, exp.getType()), exp);
				}
			} catch (ClassCastException e) {
				e.printStackTrace();
			}
		}
	}

	private void handleSetErrorDisplayType() {
		for (MethodInvocation md : ((ControllerASTVisitor) visitor).methodInvocMap.get("setErrorDisplayType")) {
			md = ASTNodeBuilder.oldToNewMap.getOrDefault(md, md);
			md.setName(ast.newSimpleName("setErrorType"));
		}
	}

	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 2) {
			System.out.println("provide the source and destination locations.");
			System.exit(-1);
		}
		ControllerRefactor en = new ControllerRefactor(new ConsoleLogger());
		File src = new File(args[0]);
		File dst = new File(args[1]);
		en.refactor(src, dst, new RefactorConfig() {
			{
				setSourcePackage("operations.shipment");
				setTargetPackage("com.ibsplc.neoicargo.awb");
			}
		});
	}

}
