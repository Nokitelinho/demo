/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.bi;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.springframework.stereotype.Service;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ConsoleLogger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.GenImplInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.RefactorConfig;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.StringUtil;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.VOToModalRefactorUtil;
import com.ibsplc.neoicargo.framework.core.lang.notation.apis.PrivateAPI;

/**
 * @author A-1759
 *
 */
public class BIRefactor extends AbstractRefactor {

	private List<String> removedImports;

	private List<String> voSrcs;

	public BIRefactor(Logger logger, List<String> voSrcs) {
		super(logger);
		this.voSrcs = voSrcs;
	}

	private static final List<String> expListToRemove = Arrays.asList("SystemException", "RemoteException");

	private static final List<String> methodsToRemove = Arrays.asList("doFlow", "audit", "handleEvents",
			"handleAdvice");

	public void refactor() {
		refactorPackage(cu, ast, visitor);
		removeParentClass(cu);
		addPrivateAPIAnnotation();
		refactorIcgImports();
		addJaxRsAnnotations();
		reafactorVOToModals();
		generateModel();
	}

	private void generateModel() {
		GenImplInfo controllerInfo = new GenImplInfo();
		controllerInfo.setPackageDecl(this.packageToCreate + ".controller");
		controllerInfo.setClazzName(getParentType(cu).getName().toString().replace("BI", "Controller"));
		List<ImportDeclaration> imports = cu.imports();
		imports = imports.stream().filter(i -> i.toString().contains("model")).collect(Collectors.toList());
		controllerInfo.setImports(imports);
		try {
			new ControllerGenerator(logger, this.visitor.getMethodDeclarationList()).generate(controllerInfo, new File(destDir.getParent(),"business"),
					refactorConfig);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void reafactorVOToModals() {
		List<ImportDeclaration> allImports = cu.imports();
		List<ImportDeclaration> otherImports = allImports.stream()
				.filter(i -> voSrcs.contains(i.getName().toString()))
				.collect(Collectors.toList());
		List<String> otherImportVOs = allImports.stream().filter(i -> !voSrcs.contains(i.getName().toString()))
				.map(i -> ((QualifiedName) i.getName()).getName().toString()).collect(Collectors.toList());
		Map<String, ImportDeclaration> voToModelMap = VOToModalRefactorUtil.refactorImports(ast, otherImports);
		for (String modal : voToModelMap.keySet()) {;
			addImport(cu, ast, modal);
			cu.imports().remove(voToModelMap.get(modal));
		}
		VOToModalRefactorUtil.refactorMethodDeclarations(ast, visitor.getMethodDeclarationList(),otherImportVOs);
	}

	private void refactorIcgImports() {
		removedImports = refactorIcgImports(cu, ast, visitor);
		cu.imports().remove(((BIASTVisitor) visitor).getRemoteImport());
	}

	private void addJaxRsAnnotations() {
		addImport(cu, ast, Path.class.getName());
		addImport(cu, ast, POST.class.getName());
		addImport(cu, ast, GET.class.getName());
		addImport(cu, ast, Consumes.class.getName());
		addImport(cu, ast, Produces.class.getName());
		// addImport(cu, ast, ApiOperation.class.getName());
		for (MethodDeclaration med : visitor.getPublicMethods()) {
			if (methodsToRemove.contains(med.getName().toString())) {
				med.delete();
				continue;
			}
			med.thrownExceptionTypes().clear();
			String methodName = med.getName().toString();
			List modifiers = med.modifiers();
			List<Object> removeAnnotations = new ArrayList<>();
			for (Object modifier : modifiers) {
				if (modifier instanceof Annotation) {
					removeAnnotations.add(modifier);
				}
			}
			modifiers.removeAll(removeAnnotations);
			SingleMemberAnnotation pathAnnotation = ast.newSingleMemberAnnotation();
			StringLiteral stringLiteral = ast.newStringLiteral();
			String path = "/v1/" + refactorConfig.getModule().toLowerCase() + "/" + methodName.toLowerCase();
			stringLiteral.setLiteralValue(path);
			pathAnnotation.setValue(stringLiteral);
			pathAnnotation.setTypeName(ast.newSimpleName(Path.class.getSimpleName()));
			med.modifiers().add(0, pathAnnotation);
			String fieldNameDesc = StringUtil.getSentenceFromCamelCaseString(methodName);
			String serviceTypeAnnotation = fieldNameDesc.startsWith("find") || fieldNameDesc.startsWith("get")
					? GET.class.getSimpleName()
					: POST.class.getSimpleName();
			MarkerAnnotation annotation = ast.newMarkerAnnotation();
			annotation.setTypeName(ast.newSimpleName(serviceTypeAnnotation));
			med.modifiers().add(1, annotation);
			SingleMemberAnnotation consumerAnnotation = ast.newSingleMemberAnnotation();
			stringLiteral = ast.newStringLiteral();
			stringLiteral.setLiteralValue("application/json");
			consumerAnnotation.setValue(stringLiteral);
			consumerAnnotation.setTypeName(ast.newSimpleName(Consumes.class.getSimpleName()));
			med.modifiers().add(2, consumerAnnotation);
			SingleMemberAnnotation producerAnnotation = ast.newSingleMemberAnnotation();
			stringLiteral = ast.newStringLiteral();
			stringLiteral.setLiteralValue("application/json");
			producerAnnotation.setValue(stringLiteral);
			producerAnnotation.setTypeName(ast.newSimpleName(Produces.class.getSimpleName()));
			med.modifiers().add(3, producerAnnotation);
			List<SimpleType> throwsList = med.thrownExceptionTypes();
			if (Objects.nonNull(throwsList)) {
				List<SimpleType> tobeRemovedList = new ArrayList<>();
				for (SimpleType throwsType : throwsList) {
					if (expListToRemove.contains(throwsType.getName().toString())
							|| removedImports.stream().anyMatch(i -> i.endsWith(throwsType.getName().toString()))) {
						tobeRemovedList.add(throwsType);
					}
				}
				throwsList.removeAll(tobeRemovedList);
			}
		}
	}

	private void addPrivateAPIAnnotation() {
		addImport(cu, ast, Service.class.getName());
		MarkerAnnotation annotation = ast.newMarkerAnnotation();
		annotation.setTypeName(ast.newSimpleName(PrivateAPI.class.getSimpleName()));
		getParentType(cu).modifiers().add(0, annotation);
	}

	@Override
	public void setASTVisitor() {
		this.visitor = new BIASTVisitor(this.logger);
	}

	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 2) {
			System.out.println("provide the source and destination locations.");
			System.exit(-1);
		}
		BIRefactor en = new BIRefactor(new ConsoleLogger(),new ArrayList<String>());
		File src = new File(args[0]);
		File dst = new File(args[1]);
		en.refactor(src, dst, new RefactorConfig() {
			{
				setSourcePackage("operations.shipment");
				setTargetPackage("com.ibsplc.neoicargo.awb");
				setModule("awb");
			}
		});
	}
}
