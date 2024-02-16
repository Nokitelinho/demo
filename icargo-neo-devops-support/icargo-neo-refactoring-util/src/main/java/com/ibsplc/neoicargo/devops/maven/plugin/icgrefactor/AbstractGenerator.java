/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor;

import java.io.File;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jface.text.Document;
import org.springframework.stereotype.Component;

/**
 * @author A-1759
 *
 */

public abstract class AbstractGenerator extends AbstractRefactor {

	protected GenImplInfo implInfo;

	private static final String TEMPLATE = String.join("\n", "public class CLASS_NAME {",

			"}");

	public AbstractGenerator(Logger logger) {
		super(logger);
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
		throw new UnsupportedOperationException();
	}

	public void generate(GenImplInfo implInfo, File destDir, RefactorConfig refactorConfig) throws Exception {
		String source = TEMPLATE.replace("CLASS_NAME", implInfo.getClazzName()+implInfo.suffix);
		if(implInfo.isInterface) {
			source = source.replace("class", "interface");
		}
		Document doc = new Document(source);
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		this.refactorConfig = refactorConfig;
		this.implInfo = implInfo;
		cu = (CompilationUnit) parser.createAST(null);
		cu.recordModifications();
		ast = cu.getAST();
		refactor();
		writeModifiedFile(doc, cu, destDir, implInfo.getClazzName()+implInfo.suffix, implInfo.getPackageDecl());
	}
	
	protected void setPackage(){
		PackageDeclaration pkgDecl = ast.newPackageDeclaration();
		pkgDecl.setName(ast.newName(implInfo.getPackageDecl()));
		cu.setPackage(pkgDecl);	
	}

	protected void addImports() {
		for (ImportDeclaration importDecl : implInfo.getImports()) {
			addImport(cu, ast, importDecl.getName().toString());
		}
	}
	

	protected void addComponentAnnotation() {
		addImport(cu, ast, Component.class.getName());
		MarkerAnnotation annotation = ast.newMarkerAnnotation();
		annotation.setTypeName(ast.newSimpleName(Component.class.getSimpleName()));
		getParentType(cu).modifiers().add(0, annotation);

	}
	
	protected void addImplements(){
		getParentType(cu).superInterfaceTypes()
		.add(ast.newSimpleType(ast.newSimpleName(this.implInfo.getClazzName())));
	}
	
	
}
