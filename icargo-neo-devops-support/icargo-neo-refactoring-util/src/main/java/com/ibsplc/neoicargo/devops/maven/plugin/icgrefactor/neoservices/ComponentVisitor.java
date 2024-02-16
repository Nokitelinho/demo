package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.neoservices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.BaseASTVisitor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;

public class ComponentVisitor extends BaseASTVisitor {

	List<QualifiedName> mapperInstances = new ArrayList<>();
	List<ImportDeclaration> mapperImports = new ArrayList<>();
	List<MethodInvocation> getQuantityMethods = new ArrayList<>();

	private static List<String> utils = Arrays.asList("QuantityMapper", "Quantities", "LocalDate");

	@Override
	public boolean visit(QualifiedName node) {
		if (node.getName().toString().equals("INSTANCE") && node.getQualifier().toString().endsWith("Mapper")) {
			mapperInstances.add(node);
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(ImportDeclaration node) {
		if (node.getName().toString().endsWith("Mapper")) {
			mapperImports.add(node);
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodInvocation node) {
		if (Objects.nonNull(node.getExpression()) && utils.contains(node.getExpression().toString())) {
			getQuantityMethods.add(node);
		}
		return super.visit(node);
	}

	public ComponentVisitor(Logger logger) {
		super(logger);
		neo = true;
	}

}
