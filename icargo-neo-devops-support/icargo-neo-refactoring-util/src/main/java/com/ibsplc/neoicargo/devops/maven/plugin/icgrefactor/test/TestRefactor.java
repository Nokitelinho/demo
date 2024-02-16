/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.test;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Assignment.Operator;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.FieldInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.neoservices.ComponentMappers;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.neoservices.MapperInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.ASTNodeBuilder;

/**
 * @author A-1759
 *
 */
public class TestRefactor extends AbstractRefactor {

	public TestRefactor(Logger logger) {
		super(logger);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor#
	 * setASTVisitor()
	 */
	@Override
	public void setASTVisitor() {
		this.visitor = new TestASTVisitor(logger);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor#
	 * refactor()
	 */
	@Override
	public void refactor() {
		isSourceChanged = false;
		packageToCreate = "test.java." + cu.getPackage().getName().toString();
		TestASTVisitor testASTVisitor = (TestASTVisitor) this.visitor;
		Optional<ImportDeclaration> importUnits = cu.imports().stream()
				.filter(i -> i.toString().contains("com.ibsplc.neoicargo.booking.modal.Units")).findFirst();
		if (importUnits.isPresent()) {
			isSourceChanged = true;
			importUnits.get().delete();
			addImport(cu, ast, "com.ibsplc.neoicargo.framework.core.lang.modal.Units");
		}
		String componentForTest = getParentType(cu).getName().toString();
		componentForTest = componentForTest.substring(0, componentForTest.length() - 4);
		Set<MapperInfo> mappers = ComponentMappers.getMappers(componentForTest);
		
		if (!testASTVisitor.mockQuantityInvocList.isEmpty() || !testASTVisitor.getQuantityInvocList.isEmpty() || Objects.nonNull(mappers)) {
			isSourceChanged = true;
			addImport(cu,ast, "com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities");
			VariableDeclarationFragment quantitesFieldVar = ast.newVariableDeclarationFragment();
			quantitesFieldVar.setName(ast.newSimpleName("quantities"));
			FieldDeclaration quantiesFieldDecl = ast.newFieldDeclaration(quantitesFieldVar);
			quantiesFieldDecl.setType(ast.newSimpleType(ast.newSimpleName("Quantities")));
			quantiesFieldDecl.modifiers().add(ast.newModifier(ModifierKeyword.PRIVATE_KEYWORD));
			getParentType(cu).bodyDeclarations().add(testASTVisitor.getFields().size(), quantiesFieldDecl);
			if (Objects.nonNull(mappers)) {
				for (MapperInfo mapperInfo : mappers) {
					String mapper = mapperInfo.getMapperType();
					String varName = mapper.substring(0, 1).toLowerCase() + mapper.substring(1);
					Optional<FieldInfo> fieldOp = this.visitor.getFields().stream()
							.filter(f -> f.getFieldName().equals(varName)).findFirst();
					if (fieldOp.isPresent()) {
						Optional<Annotation> annotOp = fieldOp.get().getFieldDeclaration().modifiers().stream()
								.filter(f -> f instanceof Annotation
										&& ((Annotation) f).getTypeName().toString().equals("Mock"))
								.findFirst();
						if (annotOp.isPresent()) {
							annotOp.get().delete();
						}
						continue;
					}
					VariableDeclarationFragment mapperFieldVar = ast.newVariableDeclarationFragment();
					mapperFieldVar.setName(ast.newSimpleName(varName));
					FieldDeclaration mapperFieldDecl = ast.newFieldDeclaration(mapperFieldVar);
					mapperFieldDecl.setType(ast.newSimpleType(ast.newSimpleName(mapper)));
					mapperFieldDecl.modifiers().add(ast.newModifier(ModifierKeyword.PRIVATE_KEYWORD));
					getParentType(cu).bodyDeclarations().add(testASTVisitor.getFields().size(), mapperFieldDecl);

				}
			}
			for (MethodInvocation methodInvocation : testASTVisitor.mockQuantityInvocList) {
				int args = methodInvocation.arguments().size();
				((ASTNode) methodInvocation.arguments().get(0)).delete();
				if (args == 4) {
					methodInvocation.arguments().add(ast.newNullLiteral());
				}
				Assignment assignment = ast.newAssignment();
				assignment.setLeftHandSide(ast.newSimpleName("quantities"));
				assignment.setOperator(Operator.ASSIGN);
				assignment.setRightHandSide((MethodInvocation) ASTNodeBuilder.getNewNode(ast, methodInvocation));
				ExpressionStatement ex = ast.newExpressionStatement(assignment);

				Block block = (Block) methodInvocation.getParent().getParent();
				block.statements().remove(methodInvocation.getParent());
				block.statements().add(0, ex);
				if (Objects.nonNull(mappers)) {
					for (MapperInfo mapperInfo : mappers) {
						addImport(cu, ast, mapperInfo.getMapperFullyQualifiedName());
						String mapper = mapperInfo.getMapperType();
						String varName = mapper.substring(0, 1).toLowerCase() + mapper.substring(1);
						assignment = ast.newAssignment();
						assignment.setLeftHandSide(ast.newSimpleName(varName));
						assignment.setOperator(Operator.ASSIGN);
						MethodInvocation mapperMethodInvoc = ast.newMethodInvocation();
						mapperMethodInvoc.setName(ast.newSimpleName("injectMapper"));
						mapperMethodInvoc.setExpression(ast.newSimpleName("MockQuantity"));
						mapperMethodInvoc.arguments().add(ast.newSimpleName("quantities"));
						TypeLiteral type = ast.newTypeLiteral();
						type.setType(ast.newSimpleType(ast.newSimpleName(mapper)));
						mapperMethodInvoc.arguments().add(type);
						assignment.setRightHandSide(mapperMethodInvoc);
						ex = ast.newExpressionStatement(assignment);
						block.statements().add(1, ex);
					}
				}
			}
			if (testASTVisitor.mockQuantityInvocList.isEmpty()) {
				addImport(cu, ast, "com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity");
				Assignment assignment = ast.newAssignment();
				assignment.setLeftHandSide(ast.newSimpleName("quantities"));
				assignment.setOperator(Operator.ASSIGN);
				MethodInvocation invoc = ast.newMethodInvocation();
				invoc.setName(ast.newSimpleName("performInitialisation"));
				invoc.setExpression(ast.newSimpleName("MockQuantity"));
				invoc.arguments().add(ast.newNullLiteral());
				invoc.arguments().add(ast.newNullLiteral());
				ASTNode node = null;
				if (Objects.isNull(testASTVisitor.stationDefault)) {
					node = ast.newStringLiteral();
					((StringLiteral) node).setLiteralValue("TRV");
					;
				} else {
					node = ASTNodeBuilder.getNewNode(ast, (ASTNode) testASTVisitor.stationDefault.arguments().get(0));
				}
				invoc.arguments().add(node);
				invoc.arguments().add(ast.newNullLiteral());
				assignment.setRightHandSide(invoc);
				ExpressionStatement ex = ast.newExpressionStatement(assignment);
				Block block = (Block) testASTVisitor.initMock.getParent().getParent();
				block.statements().add(0, ex);
				if (Objects.nonNull(mappers)) {
					for (MapperInfo mapperInfo : mappers) {
						addImport(cu, ast, mapperInfo.getMapperFullyQualifiedName());
						String mapper = mapperInfo.getMapperType();
						String varName = mapper.substring(0, 1).toLowerCase() + mapper.substring(1);
						assignment = ast.newAssignment();
						assignment.setLeftHandSide(ast.newSimpleName(varName));
						assignment.setOperator(Operator.ASSIGN);
						MethodInvocation mapperMethodInvoc = ast.newMethodInvocation();
						mapperMethodInvoc.setName(ast.newSimpleName("injectMapper"));
						mapperMethodInvoc.setExpression(ast.newSimpleName("MockQuantity"));
						mapperMethodInvoc.arguments().add(ast.newSimpleName("quantities"));
						TypeLiteral type = ast.newTypeLiteral();
						type.setType(ast.newSimpleType(ast.newSimpleName(mapper)));
						mapperMethodInvoc.arguments().add(type);
						assignment.setRightHandSide(mapperMethodInvoc);
						ex = ast.newExpressionStatement(assignment);
						block.statements().add(1, ex);
					}
				}
			}
		}
		for(MethodInvocation setApplicationContext : testASTVisitor.setApplicationContextList){
			ExpressionStatement statement  = ASTNodeBuilder.getParentNode(setApplicationContext, ExpressionStatement.class);
			statement.delete();
		}
		for (MethodInvocation getQuantityInvoc : testASTVisitor.getQuantityInvocList) {
			isSourceChanged = true;
			ASTNode parentNode = getQuantityInvoc.getParent();
			MethodInvocation newInvoc = (MethodInvocation) ASTNodeBuilder.getNewNode(ast, getQuantityInvoc, true);
			newInvoc.setExpression(ast.newSimpleName("quantities"));
			assignExpression(parentNode, newInvoc, getQuantityInvoc, "Quantities");
		}
		if (!testASTVisitor.constructEquantityList.isEmpty()) {
			isSourceChanged = true;
			addImport(cu, ast, "com.ibsplc.neoicargo.framework.util.unit.EQuantity");
			for (MethodInvocation constructEqauntity : testASTVisitor.constructEquantityList) {
				ASTNode parentNode = constructEqauntity.getParent();
				Expression newExp = (Expression) ASTNodeBuilder.getNewNode(ast,
						(Expression) constructEqauntity.arguments().get(0), true);
				ClassInstanceCreation eQInstance = ast.newClassInstanceCreation();
				eQInstance.setType(ast.newSimpleType(ast.newSimpleName("EQuantity")));
				eQInstance.arguments().add(newExp);
				assignExpression(parentNode, eQInstance, constructEqauntity, "EQuantity");
			}
		}
		List<MethodInvocation> getTimeZoneList = testASTVisitor.timeZoneHelperList.stream()
				.filter(m -> m.getName().toString().equals("getTimeZone")).collect(Collectors.toList());
		if (!getTimeZoneList.isEmpty()) {
			isSourceChanged = true;
			addImport(cu, ast, "com.ibsplc.neoicargo.framework.util.time.LocalDate");
			addImport(cu, ast, "com.ibsplc.neoicargo.framework.tests.security.utils.MockLocalDate");
			VariableDeclarationFragment localDateFieldVar = ast.newVariableDeclarationFragment();
			localDateFieldVar.setName(ast.newSimpleName("localDate"));
			FieldDeclaration localDateField = ast.newFieldDeclaration(localDateFieldVar);
			localDateField.setType(ast.newSimpleType(ast.newSimpleName("LocalDate")));
			localDateField.modifiers().add(ast.newModifier(ModifierKeyword.PRIVATE_KEYWORD));
			getParentType(cu).bodyDeclarations().add(testASTVisitor.getFields().size(), localDateField);
			getTimeZoneList.forEach(m -> {
				ASTNode arg = (ASTNode) m.arguments().get(0);
				Assignment assignment = ast.newAssignment();
				assignment.setLeftHandSide(ast.newSimpleName("localDate"));
				assignment.setOperator(Operator.ASSIGN);
				MethodInvocation mockLocalDateInvoc = ast.newMethodInvocation();
				mockLocalDateInvoc.setName(ast.newSimpleName("performInitialisationLocalDate"));
				mockLocalDateInvoc.setExpression(ast.newSimpleName("MockLocalDate"));
				mockLocalDateInvoc.arguments().add(ASTNodeBuilder.getNewNode(ast, arg));
				assignment.setRightHandSide(mockLocalDateInvoc);
				ExpressionStatement ex = ast.newExpressionStatement(assignment);
				ASTNode parentStatement = getParentStatement(m);
				Block block = (Block) parentStatement.getParent();
				block.statements().add(0, ex);
			});
			testASTVisitor.timeZoneHelperList.forEach(m -> {
				ASTNode parentStatement = getParentStatement(m);
				Block block = (Block) parentStatement.getParent();
				block.statements().remove(parentStatement);
			});
		}
		for (MethodInvocation localDateIn : testASTVisitor.getlocalDateList) {
			ASTNode parentNode = localDateIn.getParent();
			MethodInvocation newInvoc = (MethodInvocation) ASTNodeBuilder.getNewNode(ast, localDateIn, true);
			newInvoc.setExpression(ast.newSimpleName("localDate"));
			assignExpression(parentNode, newInvoc, localDateIn, "LocalDate");
		}

	}

	private ASTNode getParentStatement(ASTNode astNode) {
		while (!(astNode.getParent() instanceof Block)) {
			astNode = astNode.getParent();
		}
		return astNode;
	}

	private void assignExpression(ASTNode parentNode, Expression exp, ASTNode currentNode, String fieldType) {
		if (parentNode instanceof VariableDeclarationFragment) {
			String varName = ((VariableDeclarationFragment) parentNode).getName().toString();

			((VariableDeclarationFragment) parentNode).setInitializer(exp);

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
		} else if (parentNode instanceof Assignment) {
			String varName = ((Assignment) parentNode).getLeftHandSide().toString();
			((Assignment) parentNode).setRightHandSide(exp);

		}
	}
}
