/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.vo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.PrimitiveType.Code;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ConsoleLogger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.FieldInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.GenImplInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.RefactorConfig;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.converter.ConverterRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.StringUtil;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author A-1759
 *
 */
public class VORefactor extends ConverterRefactor {

	private List<String> removedImports;
	private List<String> voSrcs;

	public VORefactor(Logger logger, List<String> voSrcs) {
		super(logger);
		this.logger = logger;
		this.voSrcs = voSrcs;
	}

	public void refactor() {
		refactorPackage(cu, ast, visitor);
		removeGetterSetters();
		addLombokAnnotations();
		addCommonFields();
		// addSwaggerDocumentation();
		generateModel();
		removedImports = refactorIcgImports(cu, ast, visitor);
		Type superClass = getParentType(cu).getSuperclassType();
		if (Objects.nonNull(superClass)
				&& (superClass.isQualifiedType() && superClass.toString().startsWith("com.ibsplc.icargo.framework")
						|| (removedImports.stream().anyMatch(i -> i.endsWith(superClass.toString()))))) {
			addImport(cu, ast, AbstractVO.class.getName());
			getParentType(cu).setSuperclassType(ast.newSimpleType(ast.newSimpleName(AbstractVO.class.getSimpleName())));
		}
		handleLogonAttrbiutes();
		handleLocalDate();
		handleMeasure();
		handleParameterUtil();

	}

	private void addCommonFields() {
		List<FieldInfo> commonFields = getCommonFields();
		for (FieldInfo fieldInfo : commonFields) {
			if (this.visitor.getFields().stream().anyMatch(f -> f.getFieldName().equals(fieldInfo.getFieldName())
					&& f.getFieldTypeSimpleName().equals(fieldInfo.getFieldTypeSimpleName()))) {
				continue;
			}
			getParentType(cu).bodyDeclarations().add(0,fieldInfo.getFieldDeclaration());
			this.visitor.getFields().add(fieldInfo);
		}
	}

	private List<FieldInfo> getCommonFields() {
		List<FieldInfo> fieldInfos = new ArrayList<>();
		FieldInfo fieldInfo = new FieldInfo();
		fieldInfo.setFieldName("operationFlag");
		fieldInfo.setFieldTypeSimpleName("String");
		VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
		FieldDeclaration fd = ast.newFieldDeclaration(fragment);
		fragment.setName(ast.newSimpleName("operationFlag"));
		fd.setType(ast.newSimpleType(ast.newSimpleName("String")));
		fd.modifiers().add(ast.newModifier(ModifierKeyword.PRIVATE_KEYWORD));
		fieldInfo.setFieldDeclaration(fd);
		fieldInfos.add(fieldInfo);
		fieldInfo = new FieldInfo();
		fieldInfo.setFieldName("triggerPoint");
		fieldInfo.setFieldTypeSimpleName("String");	
		fragment = ast.newVariableDeclarationFragment();
		fd = ast.newFieldDeclaration(fragment);
		fragment.setName(ast.newSimpleName("triggerPoint"));
		fd.setType(ast.newSimpleType(ast.newSimpleName("String")));
		fd.modifiers().add(ast.newModifier(ModifierKeyword.PRIVATE_KEYWORD));
		fieldInfo.setFieldDeclaration(fd);
		fieldInfos.add(fieldInfo);
		fieldInfo = new FieldInfo();
		fieldInfo.setFieldName("ignoreWarnings");
		fieldInfo.setFieldTypeSimpleName("boolean");
		fragment = ast.newVariableDeclarationFragment();
		fd = ast.newFieldDeclaration(fragment);
		fragment.setName(ast.newSimpleName("ignoreWarnings"));
		fd.setType(ast.newPrimitiveType(PrimitiveType.BOOLEAN));
		fd.modifiers().add(ast.newModifier(ModifierKeyword.PRIVATE_KEYWORD));
		fieldInfo.setFieldDeclaration(fd);
		fieldInfos.add(fieldInfo);
		return fieldInfos;
	}

	private void generateModel() {
		GenImplInfo modelInfo = new GenImplInfo();
		modelInfo.setPackageDecl(this.packageToCreate.replace("vo", "model"));
		modelInfo.setClazzName(getParentType(cu).getName().toString().replace("VO", ""));
		modelInfo.setSuffix("Model");
		modelInfo.setImports(cu.imports());
		try {
			new ModelGenerator(logger, this.visitor.getFields(), voSrcs).generate(modelInfo,
					new File(destDir.getParent(), "api"), refactorConfig);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void removeGetterSetters() {
		List<MethodDeclaration> methodList = visitor.getPublicMethods();
		List<String> getterSetterList = ((VOASTVisitor) visitor).getGetterStterList();
		for (MethodDeclaration md : methodList) {
			if (getterSetterList.contains(md.getName().toString())) {
				getParentType(cu).bodyDeclarations().remove(md);
			}
		}
	}

	private void addLombokAnnotations() {
		addImport(cu, ast, lombok.Getter.class.getName());
		addImport(cu, ast, lombok.Setter.class.getName());
		MarkerAnnotation annotation = ast.newMarkerAnnotation();
		annotation.setTypeName(ast.newSimpleName(lombok.Getter.class.getSimpleName()));
		getParentType(cu).modifiers().add(0, annotation);
		annotation = ast.newMarkerAnnotation();
		annotation.setTypeName(ast.newSimpleName(lombok.Setter.class.getSimpleName()));
		getParentType(cu).modifiers().add(0, annotation);

	}

	private void addSwaggerDocumentation() {
		addImport(cu, ast, ApiModelProperty.class.getName());
		List<FieldInfo> fields = visitor.getFields();
		for (FieldInfo field : fields) {
			List modifiers = field.getFieldDeclaration().modifiers();
			if (modifiers.stream().anyMatch(m -> !(m instanceof Modifier) || ((Modifier) m).isStatic())) {
				continue;
			}
			String desc = StringUtil.getSentenceFromCamelCaseString(field.getFieldName());
			NormalAnnotation annotation = ast.newNormalAnnotation();
			annotation.setTypeName(ast.newSimpleName(ApiModelProperty.class.getSimpleName()));
			MemberValuePair pair = ast.newMemberValuePair();
			pair.setName(ast.newSimpleName("value"));
			StringLiteral stringLiteral = ast.newStringLiteral();
			stringLiteral.setLiteralValue(desc);
			pair.setValue(stringLiteral);
			annotation.values().add(pair);
			field.getFieldDeclaration().modifiers().add(0, annotation);
		}
	}

	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 2) {
			System.out.println("provide the source and destination locations.");
			System.exit(-1);
		}
		VORefactor en = new VORefactor(new ConsoleLogger(), new ArrayList<String>());
		File src = new File(args[0]);
		File dst = new File(args[1]);
		en.refactor(src, dst, new RefactorConfig() {
			{
				setSourcePackage("operations.shipment");
				setTargetPackage("com.ibsplc.neoicargo.awb");
			}
		});
	}

	@Override
	public void setASTVisitor() {
		this.visitor = new VOASTVisitor(this.logger);
	}
}
