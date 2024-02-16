/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.persistence.dao;

import java.io.File;
import java.util.List;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractGenerator;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ConsoleLogger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.MethodInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.RefactorConfig;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.ASTNodeBuilder;

/**
 * @author A-1759
 *
 */
public class DaoImplGenerator extends AbstractGenerator {

	public DaoImplGenerator(Logger logger) {
		super(logger);
	}

	

	/* (non-Javadoc)
	 * @see com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor#refactor()
	 */
	@Override
	public void refactor() {
		setPackage();
		addImports();
		addComponentAnnotation();
		addImplements();
		addMissingBeanAnnotation();
		for (MethodInfo methodInfo : this.implInfo.getMethods()) {
			MethodDeclaration methodDeclaration = methodInfo.getMethodDeclaration();
			MethodDeclaration method = ast.newMethodDeclaration();
			method.modifiers().add(ast.newModifier(ModifierKeyword.PUBLIC_KEYWORD));
			method.setReturnType2((Type)ASTNodeBuilder.getNewNode(ast,methodDeclaration.getReturnType2()));
			method.setName(ast.newSimpleName(methodDeclaration.getName().toString()));
			List<SingleVariableDeclaration> parameters = methodDeclaration.parameters();
			for (SingleVariableDeclaration variable : parameters) {
				SingleVariableDeclaration newParam = ast.newSingleVariableDeclaration();
				newParam.setType((Type)ASTNodeBuilder.getNewNode(ast,variable.getType()));
				newParam.setName(ast.newSimpleName(variable.getName().toString()));
				method.parameters().add(newParam);
			}
			Block block = ast.newBlock();
			ThrowStatement throwStatement = ast.newThrowStatement();
			ClassInstanceCreation unsupportedExp = ast.newClassInstanceCreation();
			unsupportedExp.setType(ast.newSimpleType(ast.newSimpleName(UnsupportedOperationException.class.getSimpleName())));
			throwStatement.setExpression(unsupportedExp);
			block.statements().add(throwStatement);
			method.setBody(block);
			getParentType(cu).bodyDeclarations().add(method);
		}

	}

	private void addMissingBeanAnnotation() {
		addImport(cu, ast, ConditionalOnMissingBean.class.getName());
		SingleMemberAnnotation annotation = ast.newSingleMemberAnnotation();
		annotation.setTypeName(ast.newSimpleName(ConditionalOnMissingBean.class.getSimpleName()));
		TypeLiteral typeL =  ast.newTypeLiteral();
		typeL.setType(ast.newSimpleType(ast.newSimpleName(this.implInfo.getClazzName())));
		annotation.setValue(typeL);
		getParentType(cu).modifiers().add(0, annotation);
	}
	
	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 2) {
			System.out.println("provide the source and destination locations.");
			System.exit(-1);
		}
		DAORefactor en = new DAORefactor(new ConsoleLogger());
		File src = new File(args[0]);
		File dst = new File(args[1]);
		en.refactor(src, dst, new RefactorConfig() {
			{
				setSourcePackage("com.ibsplc.icargo.business.shared");
				setTargetPackage("com.ibsplc.neoicargo.masters");
				setModule("masters");
			}
		});
	}
}
