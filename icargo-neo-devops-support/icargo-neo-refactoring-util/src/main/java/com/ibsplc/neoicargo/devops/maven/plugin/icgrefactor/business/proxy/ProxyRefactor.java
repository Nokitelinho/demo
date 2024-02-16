/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.proxy;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.springframework.stereotype.Component;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ConsoleLogger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.GenImplInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.RefactorConfig;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.VariableInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.ASTNodeBuilder;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;

/**
 * @author A-1759
 *
 */
public class ProxyRefactor extends AbstractRefactor {

	private static final List<String> expListToRemove = Arrays.asList("SystemException",
			"ServiceNotAccessibleException", "RemoteException");

	String module, subModule;

	String eProxyInstance;

	ProxyInfo proxyInfo = new ProxyInfo();

	private String eProxyType;

	List<String> allTypes = new ArrayList<String>();

	public ProxyRefactor(Logger logger) {
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
		this.visitor = new ProxyASTVisitor(this.logger);
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
		refactorIcgImports(cu, ast, visitor);
		String parentClass = getParentType(cu).getSuperclassType().toString();
		removeClassAnnotations();
		refactorLoggers();
		addComponentAnnotation();
		replaceParentClass();
		removeIcgExceptions();
		autoWireEProxy();
		if (parentClass.equals("ProductProxy")) {
			handleDespatchRequest();
		} else if (parentClass.equals("SubSystemProxy")) {
			handleSubSystemProxyCall();
		}
		if (!proxyInfo.getMethods().isEmpty()) {
			autowireField(eProxyType, eProxyInstance);
			generateEProxy();
		}
	}

	private void generateEProxy() {
		if (Objects.isNull(module) || Objects.isNull(subModule)) {
			return;
		}
		proxyInfo.setModule(module);
		proxyInfo.setSubModule(subModule);
		List<ImportDeclaration> importsToAdd = cu.imports();
		importsToAdd = importsToAdd.stream().filter(
				i -> allTypes.contains(i.getName().toString().substring(i.getName().toString().lastIndexOf(".") + 1)))
				.collect(Collectors.toList());
		GenImplInfo eProxyInfo = new GenImplInfo();
		eProxyInfo.setPackageDecl(this.packageToCreate + ".eproxy");
		eProxyInfo.setClazzName(eProxyType);
		eProxyInfo.setImports(importsToAdd);
		eProxyInfo.setInterface(true);
		try {
			new EProxyGenerator(logger, proxyInfo).generate(eProxyInfo, destDir, refactorConfig);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void handleSubSystemProxyCall() {
		for (ProxyDespatchInfo proxyDespatchInfo : ((ProxyASTVisitor) visitor).proxyDespatches) {
			MethodInvocation despatchRequest = proxyDespatchInfo.getDespatchRequest();
			MethodInvocation getSericeMethod = proxyDespatchInfo.getGetService();
			ASTNode arg = (ASTNode) getSericeMethod.arguments().get(0);
			String argS = null;
			if (arg instanceof StringLiteral) {
				argS = ((StringLiteral) arg).getLiteralValue();

			} else if (arg instanceof SimpleName) {
				VariableInfo f = ((ProxyASTVisitor) visitor).allVariables.stream()
						.filter(v -> arg.toString().equals(v.getVariableName().toString())).findFirst().get();
				argS = ((StringLiteral) f.getVariableFragment().getInitializer()).getLiteralValue();
			}
			String[] pathArr = argS.toLowerCase().split("_");
			module = pathArr[0];
			subModule = pathArr[1];
			String action = despatchRequest.getName().toString();
			refactorToServiceProxy(action, despatchRequest, proxyDespatchInfo.getCurrentMethod());
			((ProxyASTVisitor) visitor).toBeDeleted.forEach(VariableDeclarationStatement::delete);
		}
		List<ImportDeclaration> allImports = cu.imports();
		List<ImportDeclaration> toRemove = allImports.stream().filter(i -> i.getName().toString().endsWith("BI"))
				.collect(Collectors.toList());
		cu.imports().removeAll(toRemove);
	}

	private void refactorToServiceProxy(String action, MethodInvocation despatchRequest,
			MethodDeclaration currentMethod) {
		MethodInfo methodInfo = new MethodInfo();
		methodInfo.setMethodName(action);
		MethodInvocation serviceProxyDispatch = ast.newMethodInvocation();
		serviceProxyDispatch.setName(ast.newSimpleName(action));
		serviceProxyDispatch.setExpression(ast.newSimpleName(eProxyInstance));
		despatchRequest.arguments().forEach(a -> {
			serviceProxyDispatch.arguments().add(ASTNodeBuilder.getNewNode(ast, (ASTNode) a));
			VariableInfo variableInfo = ((ProxyASTVisitor) visitor).allVariables.stream()
					.filter(v -> v.getVariableName().equals(a.toString())).findFirst().orElseGet(() -> {
						VariableInfo v = new VariableInfo();
						v.setVariableName("object");
						v.setVariableType(ast.newSimpleType(ast.newSimpleName("Object")));
						return v;
					});
			methodInfo.getParameters().add(variableInfo);
			addTypes(variableInfo.getVariableType());
		});
		ASTNode parentNode = despatchRequest.getParent();
		Type typeArg = null;
		if (parentNode instanceof CastExpression) {
			parentNode = parentNode.getParent();
		}
		if (parentNode instanceof ReturnStatement) {
			typeArg = (Type) ASTNodeBuilder.getNewNode(ast, currentMethod.getReturnType2());
			((ReturnStatement) parentNode).setExpression(serviceProxyDispatch);
		} else if (parentNode instanceof ExpressionStatement) {
			typeArg = ast.newSimpleType(ast.newSimpleName("Void"));
			((ExpressionStatement) parentNode).setExpression(serviceProxyDispatch);
		} else if (parentNode instanceof Assignment) {
			Assignment assignment = (Assignment) parentNode;
			VariableInfo variableInfo = ((ProxyASTVisitor) visitor).allVariables.stream()
					.filter(a -> a.getVariableName().equals(assignment.getLeftHandSide().toString())).findFirst().get();
			typeArg = (Type) ASTNodeBuilder.getNewNode(ast, variableInfo.getVariableType());
			assignment.setRightHandSide(serviceProxyDispatch);
		} else if (parentNode instanceof VariableDeclarationFragment) {
			VariableDeclarationFragment fragment = (VariableDeclarationFragment) parentNode;
			VariableInfo variableInfo = ((ProxyASTVisitor) visitor).allVariables.stream()
					.filter(a -> a.getVariableName().equals(fragment.getName().toString())).findFirst().get();
			typeArg = (Type) ASTNodeBuilder.getNewNode(ast, variableInfo.getVariableType());
			fragment.setInitializer(serviceProxyDispatch);
		} else {

			throw new IllegalArgumentException("Unknown Type " + parentNode.getClass());
		}
		methodInfo.setRetrunType(typeArg);
		addTypes(typeArg);
		proxyInfo.getMethods().add(methodInfo);
	}

	private void addTypes(Type type) {
		if (type instanceof SimpleType) {
			allTypes.add(type.toString());
		} else if (type instanceof ParameterizedType) {
			ParameterizedType pType = (ParameterizedType) type;
			addTypes(pType.getType());
			pType.typeArguments().forEach(t -> addTypes((Type) t));
		}
	}

	private void handleDespatchRequest() {
		for (ProxyDespatchInfo proxyDespatchInfo : ((ProxyASTVisitor) visitor).proxyDespatches) {
			MethodInvocation despatchRequest = proxyDespatchInfo.getDespatchRequest();
			String actionURL = ((StringLiteral) despatchRequest.arguments().get(0)).getLiteralValue();
			despatchRequest.arguments().remove(0);
			refactorToServiceProxy(actionURL, despatchRequest, proxyDespatchInfo.getCurrentMethod());
		}
	}

	private void autoWireEProxy() {
		eProxyType = getParentType(cu).getName().toString().replace("Proxy", "EProxy");
		addImport(cu, ast, this.packageToCreate + ".eproxy." + eProxyType);
		eProxyInstance = eProxyType.substring(0, 1).toLowerCase() + eProxyType.substring(1);
		
	}

	private void removeClassAnnotations() {
		List<ASTNode> modifiers = getParentType(cu).modifiers();
		List<ASTNode> classAnnotations = modifiers.stream().filter(node -> {
			return node instanceof Annotation;
		}).collect(Collectors.toList());
		for (ASTNode annot : classAnnotations) {
			if (annot instanceof SingleMemberAnnotation) {
				SingleMemberAnnotation annotS = (SingleMemberAnnotation) annot;
				String typeName = annotS.getTypeName().toString();
				if (typeName.equals("Module")) {
					module = ((StringLiteral) annotS.getValue()).getLiteralValue();
				} else if (typeName.equals("SubModule")) {
					subModule = ((StringLiteral) annotS.getValue()).getLiteralValue();
				}
			}
		}
		modifiers.removeAll(classAnnotations);
	}

	private void addComponentAnnotation() {
		addImport(cu, ast, Component.class.getName());
		MarkerAnnotation annotation = ast.newMarkerAnnotation();
		annotation.setTypeName(ast.newSimpleName(Component.class.getSimpleName()));
		getParentType(cu).modifiers().add(0, annotation);

	}

	private void replaceParentClass() {
		getParentType(cu).setSuperclassType(null);
	}

	private void removeIcgExceptions() {
		for (MethodDeclaration med : visitor.getMethodDeclarationList()) {
			List<SimpleType> throwsList = med.thrownExceptionTypes();
			if (Objects.nonNull(throwsList)) {
				List<SimpleType> tobeRemovedList = throwsList.stream()
						.filter(throwsType -> expListToRemove.contains(throwsType.getName().toString()))
						.collect(Collectors.toList());
				throwsList.removeAll(tobeRemovedList);
				if (!throwsList.isEmpty()) {
					Optional<SimpleType> peOp = throwsList.stream().filter(t -> t.toString().equals("ProxyException"))
							.findFirst();
					if (peOp.isPresent()) {
						throwsList.remove(peOp.get());
					}
					if (throwsList.isEmpty()) {
						addImport(cu, ast, BusinessException.class.getName());
						throwsList.add(ast.newSimpleType(ast.newSimpleName("BusinessException")));
					}
				}
			}
		}
		Set<TryStatement> trySet = new HashSet<>();
		for (CatchClause catchClause : ((ProxyASTVisitor) visitor).catchClauseList) {
			if (expListToRemove.contains(catchClause.getException().getType().toString())) {
				trySet.add((TryStatement) catchClause.getParent());
				catchClause.delete();
			}
		}
		/*
		 * if(!trySet.isEmpty()){ addImport(cu, ast, ProxyException.class.getName()); }
		 */
		for (TryStatement tryStatement : trySet) {
			if (tryStatement.catchClauses().isEmpty()) {
				MethodDeclaration md = ASTNodeBuilder.getParentNode(tryStatement, MethodDeclaration.class);
				if (Objects.isNull(md) || md.thrownExceptionTypes().isEmpty()) {
					tryStatement.setFinally(ast.newBlock());
				} else {
					addImport(cu, ast, ServiceException.class.getName());
					CatchClause catchClause = ast.newCatchClause();
					SingleVariableDeclaration svd = ast.newSingleVariableDeclaration();
					svd.setName(ast.newSimpleName("se"));
					svd.setType(ast.newSimpleType(ast.newSimpleName("ServiceException")));
					catchClause.setException(svd);
					Block block = ast.newBlock();
					catchClause.setBody(block);
					ThrowStatement throwStatement = ast.newThrowStatement();
					ClassInstanceCreation excpCl = ast.newClassInstanceCreation();
					excpCl.setType((Type) ASTNodeBuilder.getNewNode(ast, (Type) md.thrownExceptionTypes().get(0)));
					excpCl.arguments().add(ast.newSimpleName("se"));
					throwStatement.setExpression(excpCl);
					block.statements().add(throwStatement);
					tryStatement.catchClauses().add(catchClause);
				}
			} else {
				addImport(cu, ast, ServiceException.class.getName());
				CatchClause catchClause = (CatchClause) tryStatement.catchClauses().get(0);
				catchClause.getException().setType(ast.newSimpleType(ast.newSimpleName("ServiceException")));
			}
		}
		if (!((ProxyASTVisitor) visitor).proxyExceptionCreations.isEmpty()) {
			addImport(cu, ast, BusinessException.class.getName());
			for (ClassInstanceCreation cl : ((ProxyASTVisitor) visitor).proxyExceptionCreations) {
				cl.setType(ast.newSimpleType(ast.newSimpleName("BusinessException")));
			}
		}
	}

	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 2) {
			System.out.println("provide the source and destination locations.");
			System.exit(-1);
		}
		ProxyRefactor en = new ProxyRefactor(new ConsoleLogger());
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
