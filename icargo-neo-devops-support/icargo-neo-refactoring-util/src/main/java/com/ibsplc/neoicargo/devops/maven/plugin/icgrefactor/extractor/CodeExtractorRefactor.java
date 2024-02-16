/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.extractor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ConsoleLogger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.sandeep.flowxmlparser.classcreator.ClassCreator;
import com.sandeep.flowxmlparser.parser.FlowXMLParser;
import com.sandeep.flowxmlparser.vo.FlowObject;

import lombok.Setter;

/**
 * @author A-1759
 *
 */
public class CodeExtractorRefactor extends AbstractRefactor {

	private Logger logger;
	private SourceModel sourceModel;
	private Map<String, SourceModel> dependentSources;
	private String srcPckg;
	List<String> methodList;
	private CodeExtractMethodsVistor codeExtractMethodsVistor;
	private Set<String> voAndExceptions;
	private boolean isFeature = false;
	private String flowDir;
	@Setter
	private Map<String, String> beanToClassMap;
	@Setter
	private List<String> exclusionList;

	public CodeExtractorRefactor(Logger logger, SourceModel sourceModel, String srcPckg,
			Map<String, SourceModel> dependentSources, Set<String> voAndExceptions, String flowDir) {
		super(logger);
		this.logger = logger;
		this.sourceModel = sourceModel;
		this.dependentSources = dependentSources;
		this.srcPckg = srcPckg;
		this.voAndExceptions = voAndExceptions;
		this.flowDir = flowDir;
		sourceModel.refactor = this;
	}

	@Override
	public void setASTVisitor() {
		this.visitor = new CodeExtractMethodsVistor(logger, srcPckg);
		this.codeExtractMethodsVistor = (CodeExtractMethodsVistor) visitor;
	}

	@Override
	public void refactor() {
		isSourceChanged = false;
		this.packageToCreate = visitor.getPackageName();
		dependentSources.putIfAbsent(this.packageToCreate + "." + getParentType(cu).getName().toString(),
				this.sourceModel);
		methodList = sourceModel.getMethods();
		isFeature = this.packageToCreate.contains("feature");
		if (isFeature) {
			// If feature all methods to be moved.
			methodList.addAll(this.visitor.getMethodDeclarationList().stream().map(m -> m.getName().toString())
					.collect(Collectors.toList()));
		} else {
			Map<String, List<String>> selfInvocationMap = codeExtractMethodsVistor.getSelfInvocations();
			Set<String> selfMethods = selfInvocationMap.keySet();
			boolean repeat = true;
			while (repeat) {
				repeat = false;
				for (String selfMethod : selfMethods) {
					// If already in the method List go to next
					if (methodList.contains(selfMethod)) {
						continue;
					}
					List<String> invokedMethods = selfInvocationMap.get(selfMethod);
					// any of the invokedMethods is from methodList add the selfMethod to methodList
					if (methodList.stream().anyMatch(invokedMethods::contains)) {
						methodList.add(selfMethod);
						repeat = true;
					}
				}
			}
			Type superType = getParentType(cu).getSuperclassType();
			if (Objects.nonNull(superType)
					&& codeExtractMethodsVistor.getSrcImports().containsKey(superType.toString())) {
				String qualifiedSuperType = codeExtractMethodsVistor.getSrcImports().get(superType.toString());
				if (Objects.nonNull(qualifiedSuperType) && dependentSources.containsKey(qualifiedSuperType)) {
					SourceModel superSourceModel = dependentSources.get(qualifiedSuperType);
					superSourceModel.methods.forEach(m -> {
						if (!methodList.contains(m)) {
							methodList.add(m);
						}
					});
				}
			}
		}
		findDependentMethodCalls();
	}

	public void findDependentMethodCalls() {
		Map<String, List<MethodInvocation>> allMethodCalls = codeExtractMethodsVistor.getAllMethodInvocations();
		Map<String, List<ClassInstanceCreation>> allClassIstanceCreations = codeExtractMethodsVistor
				.getAllClassIntanceCreations();
		for (int i = 0; i < methodList.size(); i++) {
			String method = methodList.get(i);
			if (sourceModel.getProcessedMethods().contains(method)) {
				continue;
			}
			List<Type> interfaceTypes = getParentType(cu).superInterfaceTypes();
			for (Type interfaceType : interfaceTypes) {
				addDepedencyIfValidType(interfaceType.toString(), method);
			}
			if (Objects.nonNull(getParentType(cu).getSuperclassType())) {
				addDepedencyIfValidType(getParentType(cu).getSuperclassType().toString(), method);
			}
			List<MethodInvocation> methodInvocations = allMethodCalls.get(method);
			if (Objects.nonNull(methodInvocations)) {
				for (MethodInvocation methodInvocation : methodInvocations) {
					ASTNode methodExpression = getValidExpression(methodInvocation.getExpression());
					if (methodExpression instanceof SimpleName) {
						if (Objects.nonNull(
								codeExtractMethodsVistor.getSrcVariableMap().get(methodExpression.toString()))) {
							codeExtractMethodsVistor.getSrcVariableMap().get(methodExpression.toString())
									.forEach(depSrc -> {
										addDepedencySrcModel(depSrc, methodInvocation.getName().toString());
									});
						}
					}
					if (methodExpression instanceof SimpleType || (methodExpression instanceof SimpleName
							&& Character.isUpperCase(methodExpression.toString().codePointAt(0)))) {
						addDepedencyIfValidType(methodExpression.toString(), methodInvocation.getName().toString());
					}

				}
			}
			List<ClassInstanceCreation> classInstanceCreations = allClassIstanceCreations.get(method);
			if (Objects.nonNull(classInstanceCreations)) {
				for (ClassInstanceCreation classInstanceCreation : classInstanceCreations) {
					addDepedencyIfValidType(classInstanceCreation.getType().toString(), "Constructor");
				}
			}
			Optional<MethodDeclaration> mdOp = this.codeExtractMethodsVistor.getMethodDeclarationList().stream()
					.filter(m -> m.getName().toString().equals(method)).findFirst();
			if (mdOp.isPresent()) {
				MethodDeclaration md = mdOp.get();
				List<SingleVariableDeclaration> params = md.parameters();
				for (SingleVariableDeclaration svd : params) {
					Type type = svd.getType();
					addMethodDeclVOs(type, method);
				}
				addMethodDeclVOs(md.getReturnType2(), method);
			}
			String flowDef = this.codeExtractMethodsVistor.getFlowDefinitions().get(method);
			if (Objects.nonNull(flowDef)) {
				handleFlowDefintion(flowDef);
			}
			sourceModel.getProcessedMethods().add(method);
		}

	}

	private void handleFlowDefintion(String flowDef) {
		File flowDefDir = new File(this.flowDir);
		List<File> flowDefList = new ArrayList<>();
		getAllMatchingFlowDefs(flowDefDir, flowDef, flowDefList);
		for (File flowDefFile : flowDefList) {
			try {
				FlowObject flowObject = new FlowXMLParser().parse(flowDefFile, beanToClassMap);
				String clazz = new ClassCreator().createClass(flowObject);
				File destFile = createTargetFile(flowObject.filePackage, flowObject.className,
						this.refactorConfig.getSrcDir());
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(destFile))) {
					bw.write(clazz);
					bw.flush();
				}
				String depSrc = flowObject.filePackage + "." + flowObject.className;
				flowObject.stateFunctions
						.forEach(stateFunction -> addDepedencySrcModel(depSrc, stateFunction.nameOfMethod));
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	private void getAllMatchingFlowDefs(File dir, String flowDef, List<File> flowDefList) {
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				getAllMatchingFlowDefs(file, flowDef, flowDefList);
			} else if (file.getName().contains(flowDef)) {
				flowDefList.add(file);
			}
		}
	}

	private void addMethodDeclVOs(Type type, String method) {
		if (type.toString().contains("VO")) {
			if (type instanceof SimpleType) {
				addDepedencyIfValidType(getSimpleNameFromSimpleType((SimpleType) type).toString(), method);
			} else if (type instanceof ParameterizedType) {
				List<SimpleName> simpleNames = new ArrayList<SimpleName>();
				getSimpleNameFromParameterizedType((ParameterizedType) type, simpleNames);
				simpleNames.forEach(s -> addDepedencyIfValidType(s.toString(), method));
			}
		}
	}

	private void getSimpleNameFromParameterizedType(ParameterizedType type, List<SimpleName> simpleNames) {
		List<Type> parameterTypes = type.typeArguments();
		for (Type parameterType : parameterTypes) {
			if (parameterType instanceof SimpleType) {
				simpleNames.add(getSimpleNameFromSimpleType((SimpleType) parameterType));
			} else if (parameterType instanceof ParameterizedType) {
				getSimpleNameFromParameterizedType((ParameterizedType) parameterType, simpleNames);
			}
		}

	}

	private SimpleName getSimpleNameFromSimpleType(SimpleType type) {

		return type.getName() instanceof QualifiedName ? ((QualifiedName) type.getName()).getName()
				: (SimpleName) type.getName();
	}

	private void addDepedencyIfValidType(String type, String methodName) {
		String typeFullyQualified = null;

		if (Objects.nonNull(codeExtractMethodsVistor.getSrcImports().get(type))) {
			typeFullyQualified = codeExtractMethodsVistor.getSrcImports().get(type);
		} else if (Objects.isNull(codeExtractMethodsVistor.getAllImports().get(type))) {
			typeFullyQualified = this.packageToCreate + "." + type;
		}
		if (Objects.nonNull(typeFullyQualified)) {
			if(type.endsWith("PrecheckDetailsVO")) {
				System.out.println(typeFullyQualified);
			}
			if ( (type.endsWith("VO") || type.endsWith("Exception") || type.endsWith("Mapper"))) {
				voAndExceptions.add(typeFullyQualified);
				
			} else {
				addDepedencySrcModel(typeFullyQualified, methodName);
			}
		}
	}

	private ASTNode getValidExpression(Expression parent) {
		if (parent instanceof SimpleName) {
			return parent;
		}
		if (parent instanceof CastExpression) {
			return ((CastExpression) parent).getType();
		}
		if (parent instanceof ParenthesizedExpression) {
			return getValidExpression(((ParenthesizedExpression) parent).getExpression());
		}
		if (parent instanceof ClassInstanceCreation) {
			return ((ClassInstanceCreation) parent).getType();
		}
		if (parent instanceof MethodInvocation) {
			MethodInvocation parentExp = (MethodInvocation) parent;
			if ((parentExp.getName().getFullyQualifiedName().equals("get")
					|| parentExp.getName().getFullyQualifiedName().equals("cast")) && parentExp.arguments().size() == 1
					&& parentExp.arguments().get(0) instanceof TypeLiteral) {
				TypeLiteral proxyClass = (TypeLiteral) parentExp.arguments().get(0);
				return proxyClass.getType();
			}
			Optional<MethodDeclaration> mdOp = this.codeExtractMethodsVistor.getMethodDeclarationList().stream()
					.filter(m -> m.getName().toString().equals(parentExp.getName().toString().replace("this.", "")))
					.findFirst();
			if (mdOp.isPresent()) {
				return mdOp.get().getReturnType2();
			}
		}
		return null;
	}

	private void addDepedencySrcModel(String depSrc, String method) {
		if (exclusionList.contains(depSrc)) {
			return;
		}
		if (depSrc.contains(srcPckg)
				&& (depSrc.endsWith("VO") || depSrc.endsWith("Exception") || depSrc.endsWith("Mapper"))) {
			voAndExceptions.add(depSrc);
			return;
		}
		SourceModel sourceModel = dependentSources.get(depSrc);
		if (Objects.isNull(sourceModel)) {
			sourceModel = new SourceModel();
			sourceModel.clazz = depSrc;
			sourceModel.methods = new ArrayList<>();
			dependentSources.put(depSrc, sourceModel);
		}
		if (!sourceModel.methods.contains(method)) {
			sourceModel.methods.add(method);
		}
	}

	public void removeMethods() throws Exception {
		if (!isFeature) {
			List<MethodDeclaration> toBeRemovedMethods = this.visitor.getMethodDeclarationList().stream()
					.filter(md -> !methodList.contains(md.getName().toString())).collect(Collectors.toList());
			toBeRemovedMethods.forEach(MethodDeclaration::delete);
			if (!methodList.contains("Constructor")) {
				this.visitor.getConstructorList()
						.forEach(constructorInfo -> constructorInfo.getConstructorDecl().delete());
			}
		}
		writeModifiedFile(doc, cu, destDir, getParentType(cu).getName().toString(), this.packageToCreate);
	}

	public static void main(String[] args) throws Exception {
		Map<String, SourceModel> dependentSources = new HashMap<>();
		Set<String> voAndExceptions = new HashSet<>();
		SourceModel sourceModel = new SourceModel();
		sourceModel.clazz = "com/ibsplc/icargo/business/operations/shipment/feature/saveshipment/enrichers/lh/AgentDetailsforOALEnricher.java";
		sourceModel.methods = new ArrayList<String>(Arrays.asList("find"));
		File sourceFile = new File("D:/Git/icargo-ops/Java/src" + File.separator + sourceModel.clazz);
		File destFile = new File("D:/eclipse-workspace-devops");
		CodeExtractorRefactor refactor = new CodeExtractorRefactor(new ConsoleLogger(), sourceModel,
				"operations.shipment", dependentSources, voAndExceptions,
				"D:\\Git\\icargo-ops\\Java\\server\\config\\sprout\\com\\ibsplc\\icargo\\operations\\shipment");
		sourceModel.refactor = refactor;
		refactor.refactor(sourceFile, destFile, null);
		refactor.removeMethods();
	}

}
