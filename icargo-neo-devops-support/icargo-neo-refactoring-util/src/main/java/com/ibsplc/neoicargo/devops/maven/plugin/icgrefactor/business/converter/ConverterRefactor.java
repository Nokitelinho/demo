/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.converter;

import java.io.File;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
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
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import com.ibsplc.icargo.framework.util.currency.helper.EBLMoneyMapper;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ConsoleLogger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.RefactorConfig;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Source_Type;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.VariableInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.ASTNodeBuilder;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.LocalDateMapper;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.MeasureMapper;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.masters.ParameterService;
import com.ibsplc.neoicargo.masters.ParameterType;

import lombok.SneakyThrows;

/**
 * @author A-1759
 *
 */
public class ConverterRefactor extends AbstractRefactor {

	public ConverterRefactor(Logger logger) {
		super(logger);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setASTVisitor() {
		this.visitor = new ConverterASTVisitor(logger);
	}

	@Override
	public void refactor() {
		refactorPackage(cu, ast, visitor);
		refactorIcgImports(cu, ast, visitor);
		populateSourceType();
		removeClassAnnotation();
		refactorLoggers();
		refactorMethods();
		handleLogonAttrbiutes();
		handleLocalDate();
		handleMeasure();
		handleParameterUtil();
		handleSetMethods();
	}

	protected void handleParameterUtil() {
		if (!((ConverterASTVisitor) visitor).getWatchMethodMap().get("getSystemParameterValue").isEmpty()) {
			Set<MethodDeclaration> methodsToAnnotate = new HashSet<>();
			addImport(cu, ast, ParameterService.class.getName());
			addImport(cu, ast, ParameterType.class.getName());
			addImport(cu, ast, ContextUtil.class.getName());
			for (MethodInvocation method : ((ConverterASTVisitor) visitor).getWatchMethodMap()
					.get("getSystemParameterValue")) {
				if (method.getExpression() instanceof MethodInvocation && ((MethodInvocation) method.getExpression())
						.getExpression().toString().equals("ParameterUtil")) {
					Expression parameterService = null;
					parameterService = addFieldWithBeanUtil(ParameterService.class.getSimpleName(), null, method);
					MethodInvocation getParameter = ast.newMethodInvocation();
					getParameter.setName(ast.newSimpleName("getParameter"));
					getParameter.arguments().add(ASTNodeBuilder.getNewNode(ast, (ASTNode) method.arguments().get(0)));
					getParameter.arguments().add(ast.newQualifiedName(ast.newSimpleName("ParameterType"),
							ast.newSimpleName("SYSTEM_PARAMETER")));
					getParameter.setExpression(parameterService);
					assignExpression(method.getParent(), getParameter, method);
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

	protected void handleLocalDate() {
		if (!((ConverterASTVisitor) visitor).localDateCreations.isEmpty()) {
			addImport(cu, ast, LocalDate.class.getName());
			addImport(cu, ast, ContextUtil.class.getName());

			for (ClassInstanceCreation localDateInstance : ((ConverterASTVisitor) visitor).localDateCreations) {
				MethodInvocation neoLocalDateMethod = LocalDateMapper.getNeoLocalDate(ast,
						localDateInstance.arguments(), addFieldWithBeanUtil("LocalDate", null, localDateInstance));
				ASTNode parentNode = localDateInstance.getParent();
				assignExpression(parentNode, neoLocalDateMethod, localDateInstance);

			}
		}
	}

	protected void handleMeasure() {
		if (!((ConverterASTVisitor) visitor).measureCreations.isEmpty()) {
			addImport(cu, ast, Quantities.class.getName());
			addImport(cu, ast, BigDecimal.class.getName());
			addImport(cu, ast, ContextUtil.class.getName());

			for (ClassInstanceCreation measureInstance : ((ConverterASTVisitor) visitor).measureCreations) {
				MethodInvocation quantityMethod = MeasureMapper.getQuantity(cu, ast,
						addFieldWithBeanUtil(Quantities.class.getSimpleName(), null, measureInstance),
						measureInstance.arguments());
				ASTNode parentNode = measureInstance.getParent();
				assignExpression(parentNode, quantityMethod, measureInstance);
			}
		}
	}

	private void populateSourceType() {
		Map<String, Source_Type> importMap = new HashMap<>();
		visitor.getIcgOtherImports().stream().map(i -> i.getName().toString()).forEach(i -> {
			importMap.put(i.substring(i.lastIndexOf(".") + 1),
					i.contains(refactorConfig.getSourcePackage()) ? Source_Type.SOURCE : Source_Type.OTHER);
			((ConverterASTVisitor) visitor).allVariables.values().forEach(variableInfo -> {
				if (Objects.nonNull(variableInfo.getVariableType())
						&& importMap.containsKey(variableInfo.getVariableType().toString())) {
					variableInfo.setSourceType(importMap.get(variableInfo.getVariableType().toString()));
				} else {
					variableInfo.setSourceType(Source_Type.UNKOWN);
				}
			});
		});
	}

	private void handleSetMethods() {
		if (!((ConverterASTVisitor) visitor).setMethods.isEmpty()) {
			for (MethodInvocation method : ((ConverterASTVisitor) visitor).setMethods) {
				Source_Type sourceSourceType = null;
				if (method.getExpression() instanceof SimpleName) {
					if (method.arguments().get(0) instanceof MethodInvocation) {
						MethodInvocation fromMethod = (MethodInvocation) method.arguments().get(0);
						if (fromMethod.getExpression() instanceof SimpleName) {
							VariableInfo sourceVariableInfo = ((ConverterASTVisitor) visitor).allVariables
									.get(fromMethod.getExpression().toString());
							if (Objects.nonNull(sourceVariableInfo)) {
								sourceSourceType = sourceVariableInfo.getSourceType();
							}
						}
					} else if (method.arguments().get(0) instanceof SimpleName) {
						sourceSourceType = Source_Type.SOURCE;
					} 
					VariableInfo targetVariableInfo = ((ConverterASTVisitor) visitor).allVariables
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
		addFieldWithBeanUtil(com.ibsplc.icargo.framework.util.unit.MeasureMapper.class.getSimpleName(), "measureMapper",
				method);
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
		addFieldWithBeanUtil(EBLMoneyMapper.class.getSimpleName(), "eblMoneyMapper",method);

		measureMapperMethod.setExpression(ast.newSimpleName("eblMoneyMapper"));
		measureMapperMethod.arguments().add(ASTNodeBuilder.getNewNode(ast, (ASTNode) method.arguments().get(0)));
		method.arguments().clear();
		method.arguments().add(measureMapperMethod);
	}

	private void assignExpression(ASTNode parentNode, Expression newNode, Expression oldNode) {
		if (parentNode instanceof VariableDeclarationFragment) {
			((VariableDeclarationFragment) parentNode).setInitializer(newNode);
			if (((VariableDeclarationFragment) parentNode).getParent() instanceof VariableDeclarationStatement) {
				VariableDeclarationStatement statement = (VariableDeclarationStatement) ((VariableDeclarationFragment) parentNode)
						.getParent();
				statement.setType(ast.newSimpleType(ast.newSimpleName(ZonedDateTime.class.getSimpleName())));
			} else {
				FieldDeclaration statement = (FieldDeclaration) ((VariableDeclarationFragment) parentNode).getParent();
				statement.setType(ast.newSimpleType(ast.newSimpleName(ZonedDateTime.class.getSimpleName())));
			}
		} else if (parentNode instanceof MethodInvocation) {
			int pos = ((MethodInvocation) parentNode).arguments().indexOf(oldNode);
			if (pos != -1) {
				oldNode.delete();
				((MethodInvocation) parentNode).arguments().add(pos, newNode);
			} else {
				((MethodInvocation) parentNode).setExpression(newNode);
			}
		} else if (parentNode instanceof Assignment) {
			((Assignment) parentNode).setRightHandSide(newNode);
		}

	}

	private void refactorMethods() {
		for (MethodDeclaration md : visitor.getMethodDeclarationList()) {
			if ((md.getModifiers() & Modifier.PUBLIC) != 0) {
				Optional<Object> modifierOp = md.modifiers().stream()
						.filter(node -> node instanceof NormalAnnotation && node.toString().contains("BeanConversion"))
						.findFirst();
				if (modifierOp.isPresent()) {
					if ((md.getModifiers() & Modifier.STATIC) == 0) {
						md.modifiers().add(ast.newModifier(ModifierKeyword.STATIC_KEYWORD));
					}
					NormalAnnotation beanConversionAnnot = (NormalAnnotation) modifierOp.get();
					String to = null, group = null;
					List<MemberValuePair> members = beanConversionAnnot.values();
					for (MemberValuePair mvp : members) {
						if (mvp.getName().toString().equals("to")) {
							to = ((TypeLiteral) mvp.getValue()).getType().toString();
						} else if (mvp.getName().toString().equals("group")) {
							group = ((StringLiteral) mvp.getValue()).getLiteralValue();
						}
					}
					String methodName = "convertTo" + to;
					if (Objects.nonNull(group)) {
						methodName = methodName + "For" + group;
					}
					md.setName(ast.newSimpleName(methodName));
					md.modifiers().remove(beanConversionAnnot);
				}
			}
		}
	}

	protected void handleLogonAttrbiutes() {
		List<MethodInvocation> contextUtils = visitor.getWatchMethodMap().get("getSecurityContext");
		if (Objects.nonNull(contextUtils)) {
			for (MethodInvocation invocation : contextUtils) {
				if (!(invocation.getParent() instanceof MethodInvocation)) {
					invocation.delete();
				}
			}
			List<MethodInvocation> logonAttributes = visitor.getWatchMethodMap().get("getLogonAttributesVO");
			if (Objects.nonNull(logonAttributes)) {
				for (MethodInvocation invocation : logonAttributes) {
					ASTNode parentNode = invocation.getParent();
					Expression logon = addFieldWithBeanUtil(ContextUtil.class.getSimpleName(), null, invocation);
					assignExpression(parentNode, callerLoginProfile(logon),
							ast.newSimpleType(ast.newSimpleName(LoginProfile.class.getSimpleName())), invocation);
				}
			}
		}
	}

	private MethodInvocation callerLoginProfile(Expression exp) {
		MethodInvocation callerLoginProfile = ast.newMethodInvocation();
		callerLoginProfile.setName(ast.newSimpleName("callerLoginProfile"));
		callerLoginProfile.setExpression(exp);
		return callerLoginProfile;
	}

	private void assignExpression(ASTNode parentNode, Expression exp, Type fieldType, ASTNode currentNode) {
		if (parentNode instanceof VariableDeclarationFragment) {
			String varName = ((VariableDeclarationFragment) parentNode).getName().toString();
			((VariableDeclarationFragment) parentNode).setInitializer(exp);
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
			((Assignment) parentNode).setRightHandSide(exp);

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

	private void removeClassAnnotation() {
		List<ASTNode> modifiers = getParentType(cu).modifiers();
		List<ASTNode> classAnnotations = modifiers.stream().filter(node -> {
			return node instanceof Annotation;
		}).collect(Collectors.toList());
		modifiers.removeAll(classAnnotations);
	}

	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 2) {
			System.out.println("provide the source and destination locations.");
			System.exit(-1);
		}
		ConverterRefactor en = new ConverterRefactor(new ConsoleLogger());
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
