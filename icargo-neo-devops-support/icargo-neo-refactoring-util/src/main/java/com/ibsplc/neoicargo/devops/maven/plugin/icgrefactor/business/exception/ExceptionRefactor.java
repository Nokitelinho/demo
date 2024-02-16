/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.exception;

import java.io.File;
import java.util.List;
import java.util.Objects;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.BaseASTVisitor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ConsoleLogger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ConstructorInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.RefactorConfig;
import com.ibsplc.neoicargo.framework.core.lang.DomainException;
import com.ibsplc.neoicargo.framework.core.lang.DomainException.Type;
import com.ibsplc.neoicargo.framework.icgsupport.lang.BusDomainException;

/**
 * @author A-1759
 *
 */
public class ExceptionRefactor extends AbstractRefactor {
	
	private List<String> removedImports;

	public ExceptionRefactor(Logger logger) {
		super(logger);
	}

	@Override
	public void setASTVisitor() {
		this.visitor = new BaseASTVisitor(this.logger);
	}

	@Override
	public void refactor() {
		refactorPackage(cu, ast, visitor);
		removedImports=refactorIcgImports(cu, ast, visitor);
		//updateParentClass();
		updateConstrutors();
	}



	private void updateParentClass() {
		removeParentClass(cu);
		addImport(cu, ast, BusDomainException.class.getName());
		getParentType(cu)
				.setSuperclassType(ast.newSimpleType(ast.newSimpleName(BusDomainException.class.getSimpleName())));
	}

	private void updateConstrutors() {
		for (ConstructorInfo constructorInfo : visitor.getConstructorList()) {
			String errorCode = null;
			String message = null;
			if (!constructorInfo.isNoArgsConstructor()) {
				SingleVariableDeclaration first = constructorInfo.getParams().get(0);
				if(first.getType() instanceof SimpleType){
					String type = ((SimpleType) first.getType()).getName().toString();
					if ("String".equals(type)) {
						errorCode = first.getName().toString();
					}else if(removedImports.contains(type) &&  type.endsWith("Exception")) {
						// unknown type
						first.setType(ast.newSimpleType(ast.newSimpleName(BusDomainException.class.getSimpleName())));
						addConstructorForDomainException(constructorInfo.getConstructorDecl(),first.getName().toString());
						continue;
					}
				}
				if (constructorInfo.getParams().size() > 1) {
					SingleVariableDeclaration second = constructorInfo.getParams().get(0);
					if ("String".equals(((SimpleType) second.getType()).getName().toString())) {
						message = second.getName().toString();
					}
				}
				
			}
			addConstructorBody(constructorInfo.getConstructorDecl(),errorCode,message);
		}

	}
	
	private void addConstructorForDomainException(MethodDeclaration constructorDecl,String parameter){
		SuperConstructorInvocation invocation = ast.newSuperConstructorInvocation();
		MethodInvocation domainExpMethodInvocation = ast.newMethodInvocation();
		domainExpMethodInvocation.setName(ast.newSimpleName("getErrorCode"));
		domainExpMethodInvocation.setExpression(ast.newSimpleName(parameter));
		invocation.arguments().add(0,domainExpMethodInvocation);
		invocation.arguments().add(1,getArguement(null, "Not Found"));
		List statements = constructorDecl.getBody().statements();
		if(!statements.isEmpty()){
			statements.remove(0);
		}
		statements.add(invocation);
	}

	private void addConstructorBody(MethodDeclaration constructorDecl, String errorCode, String message) {
		SuperConstructorInvocation invocation = ast.newSuperConstructorInvocation();
		invocation.arguments().add(0, getArguement(errorCode, "NOT_FOUND") );
		invocation.arguments().add(1, getArguement(message, "Not Found"));
		List statements = constructorDecl.getBody().statements();
		if(!statements.isEmpty()){
			statements.remove(0);
		}
		statements.add(invocation);
		
	}
	
	private Object getArguement(String name,String defaultValue){
		
		if(Objects.isNull(name)){
			StringLiteral exp = ast.newStringLiteral();
			exp.setLiteralValue(defaultValue);
			return exp;
		}else{
			return ast.newSimpleName(name);
		}
	}
	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 2) {
			System.out.println("provide the source and destination locations.");
			System.exit(-1);
		}
		ExceptionRefactor en = new ExceptionRefactor(new ConsoleLogger());
		File src = new File(args[0]);
		File dst = new File(args[1]);
		en.refactor(src, dst, new RefactorConfig() {
			{
				setSourcePackage("com.ibsplc.icargo.business.shared");
				setTargetPackage("com.ibsplc.neoicargo.masters");
			}
		});
	}
}
