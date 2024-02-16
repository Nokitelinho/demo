/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.cache;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.BaseASTVisitor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ConsoleLogger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.FieldInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.GenImplInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.MethodInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.RefactorConfig;

/**
 * @author A-1759
 *
 */
public class CacheRefactor extends AbstractRefactor {

	private GenImplInfo cacheInfo;

	public CacheRefactor(Logger logger) {
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
		this.visitor = new BaseASTVisitor(this.logger);
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
		refactorPackage(cu, ast, visitor);
		refactorIcgImports(cu, ast, visitor);

		this.cacheInfo = new GenImplInfo();
		cacheInfo.setPackageDecl(this.packageToCreate);
		cacheInfo.setClazzName(getParentType(cu).getName().toString());
		cacheInfo.setSuffix("Impl");
		cacheInfo.setImports(cu.imports());
		if (getParentType(cu).isInterface()) {
			List<ASTNode> nodes = getParentType(cu).superInterfaceTypes();
			List<ASTNode> removeList = nodes.stream().filter(i -> !"Invalidator".equals(i.toString()))
					.collect(Collectors.toList());
			nodes.removeAll(removeList);
			handleCacheMethods();
			addCacheableAnnotation();
			try {
				new CacheImpleGenerator(this.logger).generate(cacheInfo, destDir, refactorConfig);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			getParentType(cu).superInterfaceTypes().clear();
			addComponentAnnotation();
			removeOverrides();
		}

	}

	private void removeOverrides() {
		for (MethodDeclaration md : visitor.getMethodDeclarationList()) {
			md.modifiers().removeIf(m -> m instanceof MarkerAnnotation
					&& ((MarkerAnnotation) m).getTypeName().toString().equals("Override"));
		}
	}

	private void addComponentAnnotation() {
		addImport(cu, ast, Component.class.getName());
		MarkerAnnotation annotation = ast.newMarkerAnnotation();
		annotation.setTypeName(ast.newSimpleName(Component.class.getSimpleName()));
		getParentType(cu).modifiers().add(0, annotation);

	}

	private void handleCacheMethods() {

		for (MethodDeclaration med : visitor.getPublicMethods()) {
			String methodName = med.getName().toString();
			List modifiers = med.modifiers();
			List<Object> removeAnnotations = new ArrayList<>();
			String strategyClass = null;
			String groupName = null;
			for (Object modifier : modifiers) {
				if (modifier instanceof Annotation) {
					SingleMemberAnnotation annotation = (SingleMemberAnnotation) modifier;
					if ("Strategy".equals(annotation.getTypeName().toString())) {
						strategyClass = ((StringLiteral) annotation.getValue()).getLiteralValue();
						logger.info(strategyClass);
					} else if ("Group".equals(annotation.getTypeName().toString())) {
						if (annotation.getValue() instanceof StringLiteral) {
							groupName = ((StringLiteral) annotation.getValue()).getLiteralValue();
						} else {
							FieldInfo fieldInfo = visitor.getFields().stream()
									.filter(f -> f.getFieldName().equals(annotation.getValue().toString())).findFirst()
									.get();
							VariableDeclarationFragment fragment = (VariableDeclarationFragment) fieldInfo
									.getFieldDeclaration().fragments().get(0);
							groupName = fragment.getInitializer().toString();
						}
					}
					removeAnnotations.add(modifier);
				}
			}
			modifiers.removeAll(removeAnnotations);
			MethodInfo methodInfo = new MethodInfo();
			methodInfo.setMethodDeclaration(med);
			methodInfo.setStrategyClassName(strategyClass.substring(strategyClass.lastIndexOf(".") + 1));
			methodInfo.setGroupName(groupName);
			cacheInfo.getMethods().add(methodInfo);
		}
	}

	private void addCacheableAnnotation() {
		addImport(cu, ast, Cacheable.class.getName());
		for (MethodInfo methodInfo : cacheInfo.getMethods()) {
			NormalAnnotation cacheableAnnotation = ast.newNormalAnnotation();
			cacheableAnnotation.setTypeName(ast.newSimpleName(Cacheable.class.getSimpleName()));
			MemberValuePair pair = ast.newMemberValuePair();
			pair.setName(ast.newSimpleName("value"));
			StringLiteral literal = ast.newStringLiteral();
			literal.setLiteralValue(methodInfo.getGroupName());
			pair.setValue(literal);
			cacheableAnnotation.values().add(pair);
			methodInfo.getMethodDeclaration().modifiers().add(0, cacheableAnnotation);
		}
	}

	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 2) {
			System.out.println("provide the source and destination locations.");
			System.exit(-1);
		}
		CacheRefactor en = new CacheRefactor(new ConsoleLogger());
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
