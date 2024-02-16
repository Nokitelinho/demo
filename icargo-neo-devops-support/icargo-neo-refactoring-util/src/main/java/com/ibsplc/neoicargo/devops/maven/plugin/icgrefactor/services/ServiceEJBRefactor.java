/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.services;

import java.io.File;
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
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ClassInstanceInvocation;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ConsoleLogger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.GenImplInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.RefactorConfig;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.VOToModelInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.ASTNodeBuilder;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.ImportRefactorUtil;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.VOToModalRefactorUtil;
import com.ibsplc.neoicargo.framework.core.lang.notation.BusinessService;

/**
 * @author A-1759
 *
 */
public class ServiceEJBRefactor extends AbstractRefactor {

	private static final List<String> expListToRemove = Arrays.asList("SystemException", "RemoteException");
	private List<String> removedImports;
	private List<String> voSrcs;

	public ServiceEJBRefactor(Logger logger, List<String> voSrcs) {
		super(logger);
		this.voSrcs = voSrcs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor#
	 * setASTVisitor()
	 */
	@Override
	public void setASTVisitor() {
		this.visitor = new ServiceEJBASTVisitor(this.logger);

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
		removeParentClass(cu);
		removeParentClass(cu, true);
		refactorLoggers();
		refactorIcgImports();
		addServiceAnnotation();
		refactorMethodSignature();
		refactorControllerInstantiation();
		handlegetBean();
		removeCatchClauses();
		addModelToVOMapper();
	}

	private void addServiceAnnotation() {
		addImport(cu, ast, BusinessService.class.getName());
		MarkerAnnotation annotation = ast.newMarkerAnnotation();
		annotation.setTypeName(ast.newSimpleName(BusinessService.class.getSimpleName()));
		getParentType(cu).modifiers().add(0, annotation);

	}

	private void refactorIcgImports() {
		removedImports = refactorIcgImports(cu, ast, visitor);
		cu.imports().remove(((ServiceEJBASTVisitor) visitor).getRemoteImport());
	}

	private void refactorMethodSignature() {
		for (MethodDeclaration med : visitor.getMethodDeclarationList()) {
			if (med.getName().toString().equals("doFlow")) {
				med.delete();
				continue;
			}
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
			}
		}
	}

	private void addModelToVOMapper() {
		List<ImportDeclaration> allImports = cu.imports();
		List<ImportDeclaration> otherImports = allImports.stream().filter(i -> voSrcs.contains(i.getName().toString()))
				.collect(Collectors.toList());
		List<String> otherImportVOs = allImports.stream().filter(i -> !voSrcs.contains(i.getName().toString()))
				.map(i -> ((QualifiedName) i.getName()).getName().toString()).collect(Collectors.toList());
		Map<String, ImportDeclaration> voToModelMap = VOToModalRefactorUtil.refactorImports(ast, otherImports);
		for (String modal : voToModelMap.keySet()) {
			otherImports.add(addImport(cu, ast, modal));
		}
		// List<ImportDeclaration> allOtherImports = new
		// ArrayList<ImportDeclaration>(allImports);
		// allOtherImports.removeAll(otherImports);
		Map<MethodDeclaration, List<VOToModelInfo>> voModelMap = VOToModalRefactorUtil.refactorMethodDeclarations(ast,
				visitor.getMethodDeclarationList(), otherImportVOs);
		Set<VOToModelInfo> votoModelSet = new HashSet<>();
		voModelMap.values().stream().forEach(l -> votoModelSet.addAll(l));
		Map<String, VOToModelInfo> voToModalMap = new HashMap<>();
		votoModelSet.forEach(v -> voToModalMap.put(v.toString(), v));
		votoModelSet.clear();
		votoModelSet.addAll(voToModalMap.values());
		List<ImportDeclaration> importsToAdd = otherImports.stream()
				.filter(i -> votoModelSet.stream()
						.anyMatch(vo -> i.getName().toString().endsWith("." + vo.getVoType())
								|| i.getName().toString().endsWith("." + vo.getModelType())))
				.collect(Collectors.toList());
		String mapperTypeName = getParentType(cu).getName().toString().replace("ServicesEJB", "Mapper");
		String mapperName = mapperTypeName.substring(0, 1).toLowerCase() + mapperTypeName.substring(1);
		GenImplInfo mapperInfo = new GenImplInfo();
		mapperInfo.setPackageDecl(this.packageToCreate.replace("service", "mapper"));
		mapperInfo.setClazzName(mapperTypeName);
		mapperInfo.setImports(importsToAdd);
		mapperInfo.setInterface(true);
		try {
			new MapperGenerator(logger, votoModelSet).generate(mapperInfo, destDir, refactorConfig);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		addImport(cu, ast, mapperInfo.getPackageDecl() + "." + mapperInfo.getClazzName());
		autowireField(mapperTypeName, mapperName);
		for (MethodDeclaration md : voModelMap.keySet()) {
			List<VOToModelInfo> voToModelInfos = voModelMap.get(md);
			for (VOToModelInfo voToModelInfo : voToModelInfos) {
				if (voToModelInfo.getVoName().isEmpty()) {
					MethodInvocation mapperMethod = ast.newMethodInvocation();
					String methodName = voToModelInfo.getVoType()
							+ (Objects.nonNull(voToModelInfo.getModalPType()) ? "sTo" : "To")
							+ voToModelInfo.getModelType();
					methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
					mapperMethod.setName(ast.newSimpleName(methodName));
					mapperMethod.setExpression(ast.newSimpleName(mapperName));
					ReturnStatement rs = ((ServiceEJBASTVisitor) visitor).mdToReturnMap.get(md.getName().toString());
					mapperMethod.arguments().add(ASTNodeBuilder.getNewNode(ast, rs.getExpression()));
					rs.setExpression(mapperMethod);
					continue;
				}
				VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
				fragment.setName(ast.newSimpleName(voToModelInfo.getVoName()));
				MethodInvocation mapperMethod = ast.newMethodInvocation();
				String methodName = voToModelInfo.getModelType()
						+ (Objects.nonNull(voToModelInfo.getModalPType()) ? "sTo" : "To") + voToModelInfo.getVoType();
				methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
				mapperMethod.setName(ast.newSimpleName(methodName));
				mapperMethod.setExpression(ast.newSimpleName(mapperName));
				mapperMethod.arguments().add(ast.newSimpleName(voToModelInfo.getModelName()));
				fragment.setInitializer(mapperMethod);
				VariableDeclarationStatement mapperStatement = ast.newVariableDeclarationStatement(fragment);
				Type voTypeObject = Objects.nonNull(voToModelInfo.getModalPType())
						? (Type) ASTNodeBuilder.getNewNode(ast, voToModelInfo.getVoPType())
						: ast.newSimpleType(ast.newSimpleName(voToModelInfo.getVoType()));
				mapperStatement.setType(voTypeObject);
				md.getBody().statements().add(0, mapperStatement);
			}
		}
	}

	private void removeCatchClauses() {
		Set<TryStatement> trySet = new HashSet<>();
		for (CatchClause catchClause : ((ServiceEJBASTVisitor) visitor).catchClauseList) {
			if (expListToRemove.contains(catchClause.getException().getType().toString()) || removedImports.stream()
					.anyMatch(i -> i.endsWith(catchClause.getException().getType().toString()))) {
				trySet.add((TryStatement) catchClause.getParent());
				catchClause.delete();
			}
		}

		for (TryStatement tryStatement : trySet) {
			if (tryStatement.catchClauses().isEmpty()) {
				tryStatement.setFinally(ast.newBlock());
			}
		}
	}

	private void refactorControllerInstantiation() {
		Map<String, String> autoWiredControllers = new HashMap<>();
		List<ClassInstanceInvocation> controllerInstances = ((ServiceEJBASTVisitor) visitor).getControllerInstances();
		if (!controllerInstances.isEmpty()) {
			addImport(cu, ast, Autowired.class.getName());
		}
		for (ClassInstanceInvocation controllerInstance : controllerInstances) {
			String controllerType = controllerInstance.getType();
			String autoWireField = controllerType.substring(0, 1).toLowerCase() + controllerType.substring(1);
			autowireField(controllerType, autoWireField);
			ASTNode parentNode = controllerInstance.getControllerNode();
			if (parentNode instanceof MethodInvocation) {
				((MethodInvocation) parentNode).setExpression(ast.newSimpleName(autoWireField));
			} else if (parentNode instanceof VariableDeclarationFragment) {
				String varName = ((VariableDeclarationFragment) parentNode).getName().toString();
				if (varName.equals(autoWireField)) {
					parentNode.getParent().delete();
				} else {
					((VariableDeclarationFragment) parentNode).setInitializer(ast.newSimpleName(autoWireField));
				}
			}
		}
	}

	private void handlegetBean() {
		for (MethodInvocation md : ((ServiceEJBASTVisitor) visitor).methodInvocMap.get("getBean")) {
			try {
				CastExpression exp = ASTNodeBuilder.getParentNode(md, CastExpression.class);
				Object arg = md.arguments().get(0);
				String type = exp.getType().toString();
				type = type.substring(0, 1).toLowerCase() + type.substring(1);
				autowireField((Type) ASTNodeBuilder.getNewNode(ast, exp.getType()), type);
				Expression beanExp = ast.newSimpleName(type);
				assignExpression(exp.getParent(), beanExp, null, (Type) ASTNodeBuilder.getNewNode(ast, exp.getType()),
						exp);
			} catch (ClassCastException e) {
				e.printStackTrace();
			}
		}
	}

	private void assignExpression(ASTNode parentNode, Expression exp, String autoWireField, Type fieldType,
			ASTNode currentNode) {
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

	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 2) {
			System.out.println("provide the source and destination locations.");
			System.exit(-1);
		}
		ServiceEJBRefactor en = new ServiceEJBRefactor(new ConsoleLogger(), new ArrayList<String>());
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
