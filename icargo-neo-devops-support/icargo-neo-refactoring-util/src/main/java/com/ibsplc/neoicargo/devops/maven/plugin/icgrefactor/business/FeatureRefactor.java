/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.springframework.stereotype.Component;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ConsoleLogger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.RefactorConfig;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.controller.ControllerASTVisitor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.controller.ControllerRefactor;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.framework.orchestration.FeatureContextUtilThreadArray;

/**
 * @author A-1759
 *
 */
public class FeatureRefactor extends ControllerRefactor {

	@Override
	public void refactor() {
		String componentName = getComponentName();
		isBean = true;
		super.refactor();
		if (Objects.nonNull(componentName)) {
			addComponentAnnotation(componentName);
		}
		if (Objects.nonNull(getParentType(cu).getSuperclassType())
				&& getParentType(cu).getSuperclassType().toString().contains("AbstractFeature")) {
			refactorFeature();
		}
		handleFeatureContext();
		handleBEVMethods();
	}

	private void handleBEVMethods() {
		for (MethodDeclaration md : this.visitor.getMethodDeclarationList()) {
			if (md.getReturnType2().toString().equals("FeatureConfigVO")) {
				md.delete();
			}
		}
	}

	private void handleFeatureContext() {
		if (!((ControllerASTVisitor) visitor).getMethodInvocMap().get("storeTxBusinessParameter").isEmpty()) {
			List<MethodInvocation> storeTxMethods = ((ControllerASTVisitor) visitor).getMethodInvocMap()
					.get("storeTxBusinessParameter");
			for (MethodInvocation storeTxMethod : storeTxMethods) {
				storeTxMethod.setName(ast.newSimpleName("addtoFeatureContext"));
				storeTxMethod.setExpression(null);
			}
		}
		if (!((ControllerASTVisitor) visitor).getMethodInvocMap().get("addToContext").isEmpty()) {
			List<MethodInvocation> storeTxMethods = ((ControllerASTVisitor) visitor).getMethodInvocMap()
					.get("addToContext");
			for (MethodInvocation storeTxMethod : storeTxMethods) {
				if (Objects.isNull(storeTxMethod.getExpression())) {
					storeTxMethod.setName(ast.newSimpleName("addtoFeatureContext"));
				}
			}
		}

		if (!((ControllerASTVisitor) visitor).getMethodInvocMap().get("getTxBusinessParameter").isEmpty()) {
			List<MethodInvocation> storeTxMethods = ((ControllerASTVisitor) visitor).getMethodInvocMap()
					.get("getTxBusinessParameter");
			for (MethodInvocation storeTxMethod : storeTxMethods) {
				storeTxMethod.setName(ast.newSimpleName("getContextObject"));
				storeTxMethod.setExpression(null);
			}
		}
		if (!((ControllerASTVisitor) visitor).getMethodInvocMap().get("getInstance").isEmpty()) {
			List<MethodInvocation> getInstances = ((ControllerASTVisitor) visitor).getMethodInvocMap()
					.get("getInstance");
			for (MethodInvocation getInstance : getInstances) {
				if (getInstance.getExpression().toString().equals("FeatureContextUtil")) {
					getInstance.setExpression(ast.newSimpleName(FeatureContextUtilThreadArray.class.getSimpleName()));
				}
			}
		}
		if (!((ControllerASTVisitor) visitor).getMethodInvocMap().get("setToContext").isEmpty()) {
			List<MethodInvocation> storeTxMethods = ((ControllerASTVisitor) visitor).getMethodInvocMap()
					.get("setToContext");
			addImport(cu, ast, FeatureContextUtilThreadArray.class.getName());
			for (MethodInvocation storeTxMethod : storeTxMethods) {
				if (Objects.isNull(storeTxMethod.getExpression())) {
					storeTxMethod.setName(ast.newSimpleName("put"));
					MethodInvocation getContextMap = ast.newMethodInvocation();
					getContextMap.setName(ast.newSimpleName("getContextMap"));
					storeTxMethod.setExpression(getContextMap);
					MethodInvocation getFeatureContext = ast.newMethodInvocation();
					getFeatureContext.setName(ast.newSimpleName("getFeatureContext"));
					getContextMap.setExpression(getFeatureContext);
					MethodInvocation getInstance = ast.newMethodInvocation();
					getInstance.setName(ast.newSimpleName("getInstance"));
					getFeatureContext.setExpression(getInstance);
					getInstance.setExpression(ast.newSimpleName(FeatureContextUtilThreadArray.class.getSimpleName()));
				}
			}
		}
	}

	protected String getComponentName() {
		List<ASTNode> modifiers = getParentType(cu).modifiers();
		Annotation featureAnnotation = (Annotation) modifiers.stream().filter(node -> {
			return node instanceof Annotation && node.toString().contains("FeatureComponent");
		}).findFirst().orElseGet(() -> null);
		if (featureAnnotation != null) {
			if (featureAnnotation instanceof SingleMemberAnnotation) {
				SingleMemberAnnotation sAnnot = (SingleMemberAnnotation) featureAnnotation;
				return ((StringLiteral) sAnnot.getValue()).getEscapedValue();
			}else {
				NormalAnnotation nAnnot = (NormalAnnotation) featureAnnotation;
				StringLiteral sl = (StringLiteral) ((MemberValuePair)nAnnot.values().get(0)).getValue();
				return sl.getEscapedValue();
			}
		}
		return null;
	}

	protected void addComponentAnnotation(String componentName) {
		Optional<Object> m = getParentType(cu).modifiers().stream()
				.filter(a -> (a instanceof MarkerAnnotation && a.toString().equals("@Component"))).findFirst();
		if (m.isPresent()) {
			getParentType(cu).modifiers().remove(m.get());
		}
		addImport(cu, ast, Component.class.getName());
		SingleMemberAnnotation annotation = ast.newSingleMemberAnnotation();
		annotation.setTypeName(ast.newSimpleName(Component.class.getSimpleName()));
		StringLiteral l = ast.newStringLiteral();
		l.setEscapedValue(componentName);
		annotation.setValue(l);
		getParentType(cu).modifiers().add(0, annotation);

	}

	private void refactorFeature() {
		addImport(cu, ast, FeatureConfigSource.class.getName());
		SingleMemberAnnotation annotation = ast.newSingleMemberAnnotation();
		annotation.setTypeName(ast.newSimpleName(FeatureConfigSource.class.getSimpleName()));
		String className = getParentType(cu).getName().toString();
		String configsource = className.toLowerCase().substring(0, className.indexOf("Feature"));
		StringLiteral sl = ast.newStringLiteral();
		sl.setLiteralValue(this.refactorConfig.getModule() + "/" + configsource);
		annotation.setValue(sl);
		getParentType(cu).modifiers().add(1, annotation);

	}

	public FeatureRefactor(Logger logger) {
		super(logger);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 2) {
			System.out.println("provide the source and destination locations.");
			System.exit(-1);
		}
		FeatureRefactor en = new FeatureRefactor(new ConsoleLogger());
		File src = new File(args[0]);
		File dst = new File(args[1]);
		en.refactor(src, dst, new RefactorConfig() {
			{
				setSourcePackage("operations.shipment");
				setTargetPackage("com.ibsplc.neoicargo.awb");
			}
		});
	}

}
