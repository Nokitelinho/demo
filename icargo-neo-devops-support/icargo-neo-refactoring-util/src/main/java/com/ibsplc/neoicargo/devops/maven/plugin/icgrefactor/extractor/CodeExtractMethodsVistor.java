/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.extractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.BaseASTVisitor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;

import lombok.Getter;

/**
 * @author A-1759
 *
 */
@Getter
public class CodeExtractMethodsVistor extends BaseASTVisitor {

	public CodeExtractMethodsVistor(Logger logger, String srcPckg) {
		super(logger);
		this.srcPckg = srcPckg;
		// TODO Auto-generated constructor stub
	}

	private String srcPckg;

	private Map<String, String> srcImports = new HashMap<>();
	private Map<String, String> allImports = new HashMap<>();

	private Map<String, Set<String>> srcVariableMap = new HashMap<>();

	private String currentMethod;

	private Map<String, List<MethodInvocation>> allMethodInvocations = new HashMap<>();
	private Map<String, List<ClassInstanceCreation>> allClassIntanceCreations = new HashMap<>();
	private Map<String, List<String>> selfInvocations = new HashMap<>();
	private Map<String, String> flowDefinitions = new HashMap<>();

	private List<String> wildImports = new ArrayList<>();

	@Override
	public boolean visit(FieldDeclaration node) {
		String type = node.getType() instanceof ParameterizedType
				? ((ParameterizedType) node.getType()).getType().toString()
				: node.getType().toString();
		if (node.getType() instanceof PrimitiveType || type.startsWith("String") || type.startsWith("Object")) {
			return super.visit(node);
		}
		if (node.fragments().get(0) instanceof VariableDeclarationFragment) {
			VariableDeclarationFragment fragment = (VariableDeclarationFragment) node.fragments().get(0);
			String variable = fragment.getName().toString();
			addToSrcVariableMap(type, variable);
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		currentMethod = node.isConstructor() ? "Constructor" : node.getName().toString();
		allMethodInvocations.putIfAbsent(currentMethod, new ArrayList<>());
		allClassIntanceCreations.putIfAbsent(currentMethod, new ArrayList<>());
		if (node.parameters().size() > 0) {
			for (Object arg : node.parameters()) {
				SingleVariableDeclaration argV = (SingleVariableDeclaration) arg;
				String type = argV.getType() instanceof ParameterizedType
						? ((ParameterizedType) argV.getType()).getType().toString()
						: argV.getType().toString();
				if (argV.getType() instanceof PrimitiveType || type.startsWith("String") || type.startsWith("Object")) {
					continue;
				}
				addToSrcVariableMap(type, argV.getName().toString());
			}
		}
		Optional<NormalAnnotation> annotOp = node.modifiers().stream()
				.filter(m -> m.toString().contains("@FlowDescriptor")).findFirst();
		if (annotOp.isPresent()) {
			MemberValuePair pair = (MemberValuePair) annotOp.get().values().get(0);
			flowDefinitions.put(currentMethod, ((StringLiteral) pair.getValue()).getLiteralValue());
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodInvocation node) {
		if (Objects.isNull(node.getExpression()) || "this".equals(node.getExpression().toString())) {
			selfInvocations.putIfAbsent(node.getName().toString(), new ArrayList<>());
			selfInvocations.get(node.getName().toString()).add(currentMethod);
		}
		if (Objects.nonNull(currentMethod)) {
			allMethodInvocations.get(currentMethod).add(node);
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(ImportDeclaration node) {
		String imp = node.getName().toString();
		if (imp.contains(srcPckg)) {
			if (node.isOnDemand()) {
				wildImports.add(imp);
			}
			srcImports.put(imp.substring(imp.lastIndexOf('.') + 1), imp);
		} else {
			allImports.put(imp.substring(imp.lastIndexOf('.') + 1), imp);
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(ClassInstanceCreation node) {
		if (Objects.nonNull(currentMethod)) {
			allClassIntanceCreations.get(currentMethod).add(node);
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		String type = node.getType() instanceof ParameterizedType
				? ((ParameterizedType) node.getType()).getType().toString()
				: node.getType().toString();
		if (node.getType() instanceof PrimitiveType || type.startsWith("String") || type.startsWith("Object")) {
			return super.visit(node);
		}
		if (node.fragments().get(0) instanceof VariableDeclarationFragment) {
			VariableDeclarationFragment fragment = (VariableDeclarationFragment) node.fragments().get(0);
			String variable = fragment.getName().toString();
			addToSrcVariableMap(type, variable);
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(SingleVariableDeclaration node) {
		String type = node.getType() instanceof ParameterizedType
				? ((ParameterizedType) node.getType()).getType().toString()
				: node.getType().toString();
		if (node.getType() instanceof PrimitiveType || type.startsWith("String") || type.startsWith("Object")) {
			return super.visit(node);
		}
		addToSrcVariableMap(type, node.getName().toString());
		return super.visit(node);
	}

	private void addToSrcVariableMap(String type, String variable) {
		srcImports.values().stream().filter(i -> i.endsWith("." + type)).findFirst().ifPresentOrElse(i -> {
			srcVariableMap.putIfAbsent(variable, new HashSet<>());
			srcVariableMap.get(variable).add(i);
		}, () -> {
			if (allImports.values().stream().noneMatch(i -> i.endsWith(type))) {
				// Same package variable
				String typeF = this.getPackageName() + "." + type;
				srcVariableMap.putIfAbsent(variable, new HashSet<>());
				srcVariableMap.get(variable).add(typeF);
				for (String wildImport : wildImports) {
					typeF = wildImport + "." + type;
					srcVariableMap.get(variable).add(typeF);
				}
			}
		});

	}
}
