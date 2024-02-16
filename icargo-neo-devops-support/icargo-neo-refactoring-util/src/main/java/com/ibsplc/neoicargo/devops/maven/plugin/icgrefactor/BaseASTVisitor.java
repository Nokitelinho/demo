/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.UtilityFrameworkSupport;

import lombok.Getter;

/**
 * @author A-1759
 *
 */
public class BaseASTVisitor extends ASTVisitor {
	protected Logger logger;
	private List<String> methodsToWatch;

	public BaseASTVisitor(Logger logger) {
		this.logger = logger;
	}
	
	public BaseASTVisitor(Logger logger, List<String> methodsToWatch) {
		this.logger = logger;
		this.methodsToWatch = methodsToWatch;
		initializemethodInvocMap();
	}
	
	
	private void initializemethodInvocMap() {
		for (String method : methodsToWatch) {
			watchMethodMap.put(method, new ArrayList<>());
		}
	}
	
	private static final List<String> imports_to_retain = Arrays.asList("com.ibsplc.icargo.framework.model.ImageModel","com.ibsplc.icargo.framework.report.vo.ReportSpec");
	private static final List<String> logger_methods = Arrays.asList("log","entering","exiting");

	private static final List<String> imports_to_remove = Arrays.asList(
			"com.ibsplc.xibase.server.framework.persistence.entity.Staleable",
			"com.ibsplc.xibase.server.framework.persistence.entity.DefaultValue");
	private String packageName;
	protected boolean isField;
	List<VariableDeclarationStatement> loogerVariables = new ArrayList<>();
	private List<MethodDeclaration> methodDeclarationList = new ArrayList<>();
	private List<ImportDeclaration> icgfrwkImports = new ArrayList<>();
	private List<ImportDeclaration> icgOtherImports = new ArrayList<>();
	private List<FieldInfo> fields = new ArrayList<>();
	private List<ConstructorInfo> constructorList = new ArrayList<>();
	FieldInfo fieldInfo;
	private boolean isInterface;
	List<QualifiedName> qualifiedNames = new ArrayList<>();
	List<String> simpleNames = new ArrayList<>();
	@Getter
	List<Type> types = new ArrayList<>();
	public boolean neo;
	List<FieldInfo> loggerFields = new ArrayList<>();
	private boolean isLoggerField;
	@Getter
	private List<MethodInvocation> loggerMethods = new ArrayList<>();
	@Getter
	private List<MethodInvocation> loggerSuspects = new ArrayList<>();

	@Getter
	private Map<String, List<MethodInvocation>> watchMethodMap = new HashMap<>();
	
	@Override
	public boolean visit(PackageDeclaration node) {
		if (packageName == null) {
			packageName = node.getName().toString();
			packageName = packageName.trim();
			packageName = packageName.replace("\n", "");
			packageName = packageName.replace("\r", "");
			if (packageName.endsWith(";"))
				packageName = packageName.substring(0, packageName.length() - 1);
			if (packageName.startsWith("package"))
				packageName = packageName.substring(7);
			packageName = packageName.trim();
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(QualifiedName node) {
		qualifiedNames.add(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(SimpleType node) {
		types.add(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ParameterizedType node) {
		types.add(node);
		return super.visit(node);
	}
	
	@Override
	public boolean visit(UnionType node) {
		node.types().forEach( t ->  types.add((Type)t));
		return super.visit(node);
	}
	
	@Override
	public boolean visit(ArrayType node) {
		types.add(node.getElementType());
		return super.visit(node);
	}

	@Override
	public boolean visit(FieldDeclaration node) {
		isField = true;
		fieldInfo = new FieldInfo();
		fieldInfo.setFieldDeclaration(node);
		String fieldType = String.valueOf(node.getType());
		fieldInfo.setFieldTypeSimpleName(fieldType);
		fields.add(fieldInfo);
		if ("Log".equals(fieldType)) {
			isLoggerField = true;
		}
		return super.visit(node);
	}

	@Override
	public void endVisit(FieldDeclaration node) {
		isField = false;
		isLoggerField = false;
		super.endVisit(node);
	}

	@Override
	public boolean visit(MethodInvocation node) {
		if (Objects.nonNull(node.getExpression())
				&& loggerFields.stream().anyMatch(f -> f.getFieldName().equals(node.getExpression().toString()))) {
			loggerMethods.add(node);
		} else if ("getLogger".equals(node.getName().toString()) && "LogFactory".equals(node.getExpression().toString())
				&& node.getParent() instanceof MethodInvocation) {
			loggerMethods.add((MethodInvocation) node.getParent());
		}else if(logger_methods.contains(node.getName().toString())) {
			// when the log is defined in super class. 
			loggerSuspects.add(node);
		}
		if(Objects.nonNull(methodsToWatch) && methodsToWatch.contains(node.getName().toString())) {
			watchMethodMap.putIfAbsent(node.getName().toString(), new ArrayList<>());
			watchMethodMap.get(node.getName().toString()).add(node);
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		if ("Log".equals(node.getType().toString())) {
			isLoggerField = true;
			loogerVariables.add(node);
		}
		return super.visit(node);
	}

	@Override
	public void endVisit(VariableDeclarationStatement node) {
		isLoggerField = false;
		super.endVisit(node);
	}

	@Override
	public boolean visit(VariableDeclarationFragment node) {
		if (isField) {
			fieldInfo.setFieldName(node.getName().getIdentifier());
			if (isLoggerField) {
				loggerFields.add(fieldInfo);
			}
		} else if (isLoggerField) {
			FieldInfo loggerInfo = new FieldInfo();
			loggerInfo.setFieldName(node.getName().toString());
			loggerFields.add(loggerInfo);
		}

		return super.visit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		if (node.isConstructor()) {
			ConstructorInfo info = new ConstructorInfo();
			info.setConstructorDecl(node);
			info.setParams(node.parameters());
			constructorList.add(info);
		} else {
			methodDeclarationList.add(node);
		}
		return super.visit(node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 * TypeDeclaration)
	 */
	@Override
	public boolean visit(TypeDeclaration node) {
		isInterface = node.isInterface();
		return super.visit(node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.
	 * TypeDeclaration)
	 */
	@Override
	public boolean visit(ImportDeclaration node) {
		if (neo) {
			return super.visit(node);
		}
		String importCandidate = node.getName().toString();
		if (importCandidate.startsWith("com.ibsplc.icargo.framework") || importCandidate.startsWith("com.ibsplc.xibase")
				|| importCandidate.startsWith("com.ibsplc.ibase") || UtilityFrameworkSupport.getUtilityclassmap()
						.values().stream().anyMatch(s -> importCandidate.equals(s.getIcgClazz()))) {
			if (!imports_to_retain.contains(importCandidate) && (imports_to_remove.contains(importCandidate)
					|| !importCandidate.startsWith("com.ibsplc.xibase.server.framework.persistence"))) {
				icgfrwkImports.add(node);
			}

		} else if (importCandidate.startsWith("com.ibsplc.icargo")) {
			icgOtherImports.add(node);
		}
		return super.visit(node);
	}

	public List<MethodDeclaration> getMethodDeclarationList() {
		return methodDeclarationList;
	}

	@Override
	public boolean visit(SimpleName node) {
		if(Character.isUpperCase(node.toString().charAt(0))) {
			simpleNames.add(node.toString());
		}
		return super.visit(node);
	}
	public List<MethodDeclaration> getPublicMethods() {
		return isInterface ? methodDeclarationList
				: methodDeclarationList.stream().filter(md -> (md.getModifiers() & Modifier.PUBLIC) != 0)
						.collect(Collectors.toList());
	}

	public List<FieldInfo> getFields() {
		return fields;
	}

	public List<ImportDeclaration> getIcgfrwkImports() {
		return icgfrwkImports;
	}

	public String getPackageName() {
		return packageName;
	}

	public List<ImportDeclaration> getIcgOtherImports() {
		return icgOtherImports;
	}

	public List<ConstructorInfo> getConstructorList() {
		return constructorList;
	}

}
