/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractGenerator;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.MethodInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.ASTNodeBuilder;
import com.ibsplc.neoicargo.framework.icgsupport.cache.BaseCacheImpl;
import com.ibsplc.neoicargo.framework.icgsupport.cache.CacheException;

/**
 * @author A-1759
 *
 */
public class CacheImpleGenerator extends AbstractGenerator {

	

	private Map<String, String> autoWiredStrategies = new HashMap<>();

	public CacheImpleGenerator(Logger logger) {
		super(logger);
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
		setPackage();
		addImports();
		addComponentAnnotation();
		addImplements();
		setSuperClass();
		for (MethodInfo cacheMethodinfo : this.implInfo.getMethods()) {
			MethodDeclaration methodDeclaration = cacheMethodinfo.getMethodDeclaration();
			MethodDeclaration method = ast.newMethodDeclaration();
			method.modifiers().add(ast.newModifier(ModifierKeyword.PUBLIC_KEYWORD));
			method.setReturnType2((Type)ASTNodeBuilder.getNewNode(ast, methodDeclaration.getReturnType2()));
			method.setName(ast.newSimpleName(methodDeclaration.getName().toString()));
			List<SingleVariableDeclaration> parameters = methodDeclaration.parameters();
			List<String> variables = new ArrayList<>();
			for (SingleVariableDeclaration variable : parameters) {
				SingleVariableDeclaration newParam = ast.newSingleVariableDeclaration();
				newParam.setType((Type)ASTNodeBuilder.getNewNode(ast,variable.getType()));
				newParam.setName(ast.newSimpleName(variable.getName().toString()));
				variables.add(variable.getName().toString());
				method.parameters().add(newParam);
			}
			method.thrownExceptionTypes()
					.add(ast.newSimpleType(ast.newSimpleName(CacheException.class.getSimpleName())));
			Block block = ast.newBlock();
			ReturnStatement returnStatement = ast.newReturnStatement();
			MethodInvocation strategyInvocation = ast.newMethodInvocation();
			strategyInvocation.setName(ast.newSimpleName("retrieve"));
			autowireStrategy(cacheMethodinfo.getStrategyClassName());
			strategyInvocation
					.setExpression(ast.newSimpleName(autoWiredStrategies.get(cacheMethodinfo.getStrategyClassName())));
			for (String variable : variables) {
				strategyInvocation.arguments().add(ast.newSimpleName(variable));
			}
			CastExpression castExpression = ast.newCastExpression();
			castExpression.setExpression(strategyInvocation);
			castExpression.setType((Type)ASTNodeBuilder.getNewNode(ast, methodDeclaration.getReturnType2()));
			returnStatement.setExpression(castExpression);
			block.statements().add(returnStatement);
			method.setBody(block);
			getParentType(cu).bodyDeclarations().add(method);
		}
	}


	private void setSuperClass(){
		addImport(cu, ast, BaseCacheImpl.class.getName());
		getParentType(cu).setSuperclassType(ast.newSimpleType(ast.newSimpleName(BaseCacheImpl.class.getSimpleName())));
	}

	private void autowireStrategy(String strategy) {
		if (autoWiredStrategies.isEmpty()) {
			addImport(cu, ast, Autowired.class.getName());
		}
		if (!autoWiredStrategies.containsKey(strategy)) {
			String autoWireField = strategy.substring(0, 1).toLowerCase() + strategy.substring(1);
			autoWiredStrategies.put(strategy, autoWireField);
			VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
			fragment.setName(ast.newSimpleName(autoWireField));
			FieldDeclaration field = ast.newFieldDeclaration(fragment);
			field.setType(ast.newSimpleType(ast.newSimpleName(strategy)));
			MarkerAnnotation annotation = ast.newMarkerAnnotation();
			annotation.setTypeName(ast.newSimpleName(Autowired.class.getSimpleName()));
			field.modifiers().add(annotation);
			field.modifiers().add(ast.newModifier(ModifierKeyword.PRIVATE_KEYWORD));
			getParentType(cu).bodyDeclarations().add(0, field);
		}
	}

	
}
