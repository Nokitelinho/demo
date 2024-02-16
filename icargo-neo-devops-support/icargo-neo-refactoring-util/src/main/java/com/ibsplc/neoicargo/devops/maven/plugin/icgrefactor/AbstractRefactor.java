/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.extractor.RemoveCommentASTVisitor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.neo.NeoNoOpRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.ASTNodeBuilder;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.ImportRefactorUtil;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.LoggerRefactorUtil;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.UtilityFrameworkSupport;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author A-1759
 *
 */
public abstract class AbstractRefactor {

	private static final String LINSEP = System.getProperty("line.separator");
	protected final Pattern dotPattern = Pattern.compile(Pattern.quote("."));
	protected Logger logger;
	protected RefactorConfig refactorConfig;
	protected String packageToCreate;
	private boolean isAutowiredImported = false;
	private boolean isBeanUtilImported = false;

	List<String> addedImports = new ArrayList<>();
	List<String> autoWiredFields = new ArrayList<>();
	Map<MethodDeclaration, List<String>> methodBeanMap = new HashMap<>();

	public AbstractRefactor(Logger logger) {
		this.logger = logger;
	}

	protected static Map<String, PrimitiveType.Code> Primitive_Types = new HashMap<String, PrimitiveType.Code>() {
		{
			put("double", PrimitiveType.DOUBLE);
			put("boolean", PrimitiveType.BOOLEAN);
			put("byte", PrimitiveType.BYTE);
			put("char", PrimitiveType.CHAR);
			put("float", PrimitiveType.FLOAT);
			put("int", PrimitiveType.INT);
			put("long", PrimitiveType.LONG);
			put("short", PrimitiveType.SHORT);
			put("void", PrimitiveType.VOID);
		}
	};

	protected static Map<PrimitiveType.Code, String> Primitive_SimpleTypes = new HashMap<PrimitiveType.Code, String>() {
		{
			put(PrimitiveType.DOUBLE, "Double");
			put(PrimitiveType.BOOLEAN, "Boolean");
			put(PrimitiveType.BYTE, "Byte");
			put(PrimitiveType.CHAR, "Character");
			put(PrimitiveType.FLOAT, "Float");
			put(PrimitiveType.INT, "Integer");
			put(PrimitiveType.LONG, "Long");
			put(PrimitiveType.SHORT, "Short");
			put(PrimitiveType.VOID, "Void");
		}
	};

	Set<String> icgUtilFields = new HashSet<>();
	protected BaseASTVisitor visitor;
	protected CompilationUnit cu;
	protected AST ast;
	protected File destDir;
	protected boolean isSourceChanged = true;
	protected Document doc;

	public abstract void setASTVisitor();

	public abstract void refactor();

	public void refactor(File source, File destDir, RefactorConfig refactorConfig) throws Exception {
		setASTVisitor();
		this.refactorConfig = refactorConfig;
		this.destDir = destDir;
		doc = new Document(getFile(source));
		cu = createCompilationUnit(doc);
		cu.recordModifications();
		ast = cu.getAST();
		cu.accept(visitor);
		refactor();
		String parent = getParentType(cu).getName().toString();
		if (isSourceChanged) {
			filterNewMethods(refactorConfig.getNeoSrcDir());
			writeModifiedFile(doc, cu, destDir, parent, packageToCreate);
		}
	}

	private void filterNewMethods(String neoSrcDir) throws Exception {
		if (Objects.nonNull(neoSrcDir)) {
			String srcFile = neoSrcDir + File.separator + packageToCreate.replace('.', File.separatorChar)
					+ File.separator + getParentType(cu).getName().toString() + ".java";
			File sourceFile = new File(srcFile);
			if (sourceFile.exists()) {
				NeoNoOpRefactor noOpRefactor = new NeoNoOpRefactor(logger);
				noOpRefactor.refactor(sourceFile, null, null);
				Arrays.stream(getParentType(cu).getMethods()).filter(noOpRefactor::hasMethod)
						.forEach(MethodDeclaration::delete);
				Arrays.stream(getParentType(cu).getFields()).filter(noOpRefactor::hasField)
						.forEach(FieldDeclaration::delete);
			}
		}
	}

	protected String getFile(File f) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			String line = null;
			StringBuilder cbuf = new StringBuilder(1000);
			while ((line = br.readLine()) != null) {
				cbuf.append(line);
				cbuf.append(LINSEP);
			}
			return cbuf.toString();
		}
	}

	protected CompilationUnit createCompilationUnit(File source) throws IOException {
		Document doc = new Document(getFile(source));
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		Map options = JavaCore.getOptions(); // New!
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_5, options); // New!
		parser.setCompilerOptions(options); // New!
		parser.setSource(doc.get().toCharArray());
		return (CompilationUnit) parser.createAST(null);
	}

	protected CompilationUnit createCompilationUnit(Document doc) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		Map options = JavaCore.getOptions(); // New!
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_5, options); // New!
		parser.setCompilerOptions(options); // New!
		parser.setSource(doc.get().toCharArray());
		return (CompilationUnit) parser.createAST(null);
	}

	protected void writeModifiedFile(Document doc, CompilationUnit cu, File dest, String fileName, String pkg)
			throws Exception {
		List<Comment> comments = cu.getCommentList();
		if (comments != null) {
			for (Comment comment : comments) {

				comment.accept(new RemoveCommentASTVisitor(doc));
			}
		}
		final Document document = new Document(cu.toString());
		cu.rewrite(doc, null).apply(doc);
		CodeFormatter codeFormatter = ToolFactory.createCodeFormatter(null);
		TextEdit textEdit = codeFormatter.format(CodeFormatter.K_COMPILATION_UNIT, document.get(), 0,
				document.getLength(), 0, null);
		textEdit.apply(document);

		File target = createTargetFile(pkg, fileName, dest.getAbsolutePath());
		logger.info("Writing file -->" + target.getAbsolutePath());
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(target))) {
			bw.write(document.get());
			bw.flush();
		}
	}

	protected File createTargetFile(String packageName, String simpleClassName, String destDir) throws IOException {
		StringBuilder sbul = new StringBuilder(simpleClassName).append(".java");
		mkdir(packageName, destDir);
		if (!destDir.endsWith(File.separator))
			destDir = destDir + File.separator;
		String baseDir = destDir + packageName.replace('.', File.separatorChar);
		File dir = new File(baseDir);
		dir.mkdirs();
		File outFile = new File(dir, sbul.toString());
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		return outFile;
	}

	protected void mkdir(String pkg, String base) {
		String[] dirs = pkg.split("\\Q.\\E");
		for (String dir : dirs) {
			File folder = new File(base + File.separator + dir);
			base = folder.getAbsolutePath();
			if (!folder.exists())
				folder.mkdir();
		}
	}

	protected TypeDeclaration getParentType(CompilationUnit cu) {
		if (cu.types() == null || cu.types().size() == 0) {
			logger.warn("Type does not refer to a valid class");
			return null;
		}
		TypeDeclaration td = TypeDeclaration.class.cast(cu.types().get(0));
		return td;
	}

	protected ImportDeclaration addImport(CompilationUnit cu, AST ast, String className) {
		return addImport(cu, ast, className, false);
	}

	protected ImportDeclaration addImport(CompilationUnit cu, AST ast, String className, boolean onDemand) {
		return addImport(cu, ast, className, false, false);
	}

	protected ImportDeclaration addImport(CompilationUnit cu, AST ast, String className, boolean onDemand,
			boolean isStatic) {
		if (addedImports.contains(className) || cu.imports().stream()
				.anyMatch(i -> ((ImportDeclaration) i).getName().toString().equals(className))) {
			return null;
		}
		String[] splts = dotPattern.split(className);
		ImportDeclaration id = ast.newImportDeclaration();
		id.setName(ast.newName(splts));
		id.setOnDemand(onDemand);
		id.setStatic(isStatic);
		cu.imports().add(id);
		addedImports.add(className);
		return id;
	}

	protected void removeParentClass(CompilationUnit cu, boolean removeInterfaces) {
		if (getParentType(cu).isInterface() || removeInterfaces) {
			getParentType(cu).superInterfaceTypes().clear();
		} else if (!getParentType(cu).isInterface()) {
			getParentType(cu).setSuperclassType(null);
		}

	}

	protected void removeParentClass(CompilationUnit cu) {
		removeParentClass(cu, false);
	}

	protected List<String> refactorIcgImports(CompilationUnit cu, AST ast, BaseASTVisitor visitor) {
		List<String> removedImports = new ArrayList<>();

		for (ImportDeclaration importDeclaration : visitor.getIcgfrwkImports()) {
			String importDecl = ((QualifiedName) importDeclaration.getName()).getName().toString();
			cu.imports().remove(importDeclaration);
			if (UtilityFrameworkSupport.getUtilityclassmap().containsKey(importDecl)) {
				Class clazz = UtilityFrameworkSupport.getUtilityclassmap().get(importDecl).getNeoClazz();
				if (icgUtilFields.add(importDecl)) {
					addImport(cu, ast, clazz.getName());
				}
			} else {
				removedImports.add(importDecl);
			}
		}
		List<String> allTypes = visitor.getTypes().stream().map(t -> t.toString()).collect(Collectors.toList());
		allTypes.addAll(
				visitor.qualifiedNames.stream().map(q -> q.getQualifier().toString()).collect(Collectors.toSet()));
		allTypes.addAll(visitor.simpleNames);
		for (ImportDeclaration importDeclaration : visitor.getIcgOtherImports()) {
			String importDecl = ((QualifiedName) importDeclaration.getName()).getName().toString();
			if (importDeclaration.isStatic() || importDeclaration.isOnDemand() || allTypes.contains(importDecl)) {
				String otherImport = ImportRefactorUtil.refactorDevImport(refactorConfig.getSourcePackage(),
						refactorConfig.getTargetPackage(), importDeclaration.getName().toString());
				cu.imports().remove(importDeclaration);
				addImport(cu, ast, otherImport, importDeclaration.isOnDemand(), importDeclaration.isStatic());
			} else {
				cu.imports().remove(importDeclaration);
				removedImports.add(importDecl);
			}
		}
		for (FieldInfo fieldInfo : visitor.getFields()) {
			Type neoType = getNeoTypeForIcg(fieldInfo.getFieldDeclaration().getType());
			if (Objects.nonNull(neoType)) {
				fieldInfo.getFieldDeclaration().setType(neoType);
			}

		}
		for (MethodDeclaration md : visitor.getMethodDeclarationList()) {
			List<SingleVariableDeclaration> params = md.parameters();
			if (Objects.nonNull(params)) {
				for (SingleVariableDeclaration param : params) {
					Type neoType = getNeoTypeForIcg(param.getType());
					if (Objects.nonNull(neoType)) {
						param.setType(neoType);
					}
				}
			}
			Type returnType = md.getReturnType2();
			Type neoType = getNeoTypeForIcg(returnType);
			if (Objects.nonNull(neoType)) {
				md.setReturnType2(neoType);
			}
		}
		for (Type type : visitor.types) {
			Type neoType = getNeoTypeForIcg(type);
			if (Objects.nonNull(neoType)) {
				ASTNodeBuilder.setNewType(type, neoType);
			}
		}
		for (QualifiedName type : visitor.qualifiedNames) {
			if (UtilityFrameworkSupport.getUtilityclassmap().containsKey(type.getQualifier().toString())) {
				Class neoClazz = UtilityFrameworkSupport.getUtilityclassmap().get(type.getQualifier().toString())
						.getNeoClazz();
				type.setQualifier(ast.newSimpleName(neoClazz.getSimpleName()));
			}
		}
		return removedImports;
	}

	private Type getNeoTypeForIcg(Type type) {
		Type newType = null;
		if (type instanceof ParameterizedType
				|| UtilityFrameworkSupport.getUtilityclassmap().containsKey(type.toString())) {
			newType = (Type) ASTNodeBuilder.getNewNode(ast, type);

		}
		return newType;

	}

	protected void refactorPackage(CompilationUnit cu, AST ast, BaseASTVisitor visitor) {
		String appendClazz = "." + getParentType(cu).getName().toString();
		packageToCreate = ImportRefactorUtil.refactorDevImport(refactorConfig.getSourcePackage(),
				refactorConfig.getTargetPackage(), visitor.getPackageName() + appendClazz);
		packageToCreate = packageToCreate.substring(0, packageToCreate.indexOf(appendClazz));
		PackageDeclaration pkgDecl = ast.newPackageDeclaration();
		pkgDecl.setName(ast.newName(packageToCreate));
		cu.setPackage(pkgDecl);
	}

	protected void autowireField(Type fieldType, String fieldName) {
		if (!isAutowiredImported) {
			addImport(cu, ast, Autowired.class.getName());
			isAutowiredImported = true;
		}
		if (autoWiredFields.contains(fieldType + "-" + fieldName)) {
			return;
		}
		autoWiredFields.add(fieldType + "-" + fieldName);
		VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
		fragment.setName(ast.newSimpleName(fieldName));
		FieldDeclaration field = ast.newFieldDeclaration(fragment);
		field.setType(fieldType);
		MarkerAnnotation annotation = ast.newMarkerAnnotation();
		annotation.setTypeName(ast.newSimpleName(Autowired.class.getSimpleName()));
		field.modifiers().add(annotation);
		field.modifiers().add(ast.newModifier(ModifierKeyword.PRIVATE_KEYWORD));
		getParentType(cu).bodyDeclarations().add(0, field);
	}

	protected void autowireField(String fieldType, String fieldName) {
		autowireField(ast.newSimpleType(ast.newSimpleName(fieldType)), fieldName);
	}

	protected Expression addFieldWithBeanUtil(String fieldType, String beanName, ASTNode currentNode) {
		MethodDeclaration md = ASTNodeBuilder.getParentNode(currentNode, MethodDeclaration.class);
		String fieldName = Objects.nonNull(beanName) ? beanName
				: fieldType.substring(0, 1).toLowerCase() + fieldType.substring(1);
		if (Objects.nonNull(md) && Objects.nonNull(methodBeanMap.get(md))
				&& methodBeanMap.get(md).contains(fieldName)) {
			return ast.newSimpleName(fieldName);
		}
		ASTNode astNode = null;

		Expression beanUtil = addFieldWithBeanUtil(ast.newSimpleType(ast.newSimpleName(fieldType)), astNode);
		if (Objects.nonNull(md)) {
			VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
			fragment.setName(ast.newSimpleName(fieldName));
			fragment.setInitializer(beanUtil);
			VariableDeclarationStatement statement = ast.newVariableDeclarationStatement(fragment);
			statement.setType(ast.newSimpleType(ast.newSimpleName(fieldType)));
			md.getBody().statements().add(0, statement);
			methodBeanMap.putIfAbsent(md, new ArrayList<>());
			methodBeanMap.get(md).add(fieldName);
			return ast.newSimpleName(fieldName);
		}
		return beanUtil;
	}

	protected Expression addFieldWithBeanUtil(Type fieldType, ASTNode beanName) {
		if (!isBeanUtilImported) {
			addImport(cu, ast, ContextUtil.class.getName());
			isBeanUtilImported = true;
		}
		MethodInvocation getInstnace = ast.newMethodInvocation();
		getInstnace.setName(ast.newSimpleName("getInstance"));
		getInstnace.setExpression(ast.newSimpleName(ContextUtil.class.getSimpleName()));
		if (fieldType.toString().equals("ContextUtil")) {
			return getInstnace;
		}
		MethodInvocation beanUtil = ast.newMethodInvocation();
		beanUtil.setName(ast.newSimpleName("getBean"));
		if (Objects.nonNull(beanName)) {
			beanUtil.arguments().add(beanName);
		}
		TypeLiteral typeL = ast.newTypeLiteral();
		typeL.setType(fieldType);
		beanUtil.arguments().add(typeL);
		beanUtil.setExpression(getInstnace);
		return beanUtil;
	}

	protected void assignExpression(ASTNode parentNode, Expression exp, ASTNode currentNode) {
		if (parentNode instanceof ExpressionStatement) {
			((ExpressionStatement) parentNode).setExpression(exp);
			currentNode.delete();
		} else {
			logger.error("Unhandled Parent Node " + parentNode.getClass() + " " + parentNode);
		}
	}

	protected void refactorLoggers(List<MethodInvocation> loggerMethods) {
		if (loggerMethods.size() > 0) {
			addImport(cu, ast, Slf4j.class.getName());
			MarkerAnnotation annotation = ast.newMarkerAnnotation();
			annotation.setTypeName(ast.newSimpleName(Slf4j.class.getSimpleName()));
			getParentType(cu).modifiers().add(0, annotation);
			for (MethodInvocation loggerMethod : loggerMethods) {
				MethodInvocation newLoggerMethod = LoggerRefactorUtil.refactorLoggers(loggerMethod, ast);
				assignExpression(loggerMethod.getParent(), newLoggerMethod, loggerMethod);
			}

		}
	}

	protected void refactorLoggers() {
		refactorLoggers(visitor.getLoggerMethods());
		for (FieldInfo fieldInfo : visitor.loggerFields) {
			if (Objects.nonNull(fieldInfo.fieldDeclaration)) {
				fieldInfo.fieldDeclaration.delete();
			}

		}
		for (VariableDeclarationStatement loggerStatements : visitor.loogerVariables) {
			loggerStatements.delete();
		}
	}

}
