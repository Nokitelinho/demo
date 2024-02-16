/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.services;

import java.util.Objects;
import java.util.Set;

import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.ibsplc.icargo.framework.util.currency.helper.EBLMoneyMapper;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractGenerator;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.VOToModelInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.ASTNodeBuilder;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;

import lombok.Getter;
import lombok.Setter;

/**
 * @author A-1759
 *
 */
public class MapperGenerator extends AbstractGenerator {

	private Set<VOToModelInfo> votoModelSet;

	public MapperGenerator(Logger logger, Set<VOToModelInfo> votoModelSet) {
		super(logger);
		this.votoModelSet = votoModelSet;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void refactor() {
		setPackage();
		addImports();
		
		addMapperAnnotation();
		addMapperMethods();
	}

	protected void addImports() {
		super.addImports();
		addImport(cu, ast, Getter.class.getName());
		addImport(cu, ast, Setter.class.getName());
	}
	private void addMapperMethods() {
		addImport(cu, ast, Units.class.getName());
		for (ImportDeclaration importDeclaration : this.implInfo.getImports()) {
			String importS = importDeclaration.getName().getFullyQualifiedName();
			String mapperImport = importS.replace("VO", "Model").replace("vo", "model");
			addImport(cu, ast, mapperImport);
		}
		for (VOToModelInfo voToModelInfo : votoModelSet) {
			String voType = voToModelInfo.getVoType();
			String mapperType = voType.replace("VO", "Model");
			String paramType = null;
			String returnType = null;
			Type paramTypeObject = null;
			Type returnTypeObject = null;
			if(voToModelInfo.getVoName().isEmpty()) {
				paramType = voType;
				returnType = mapperType;
				if(Objects.nonNull(voToModelInfo.getModalPType())) {
					paramTypeObject = (Type) ASTNodeBuilder.getNewNode(ast, voToModelInfo.getVoPType());
					returnTypeObject = (Type) ASTNodeBuilder.getNewNode(ast, voToModelInfo.getModalPType());
				}else {
					paramTypeObject = ast.newSimpleType(ast.newSimpleName(paramType));
					returnTypeObject = ast.newSimpleType(ast.newSimpleName(returnType));
				}
			}else {
				paramType = mapperType;
				returnType = voType;
				if(Objects.nonNull(voToModelInfo.getModalPType())) {
					paramTypeObject = (Type) ASTNodeBuilder.getNewNode(ast, voToModelInfo.getModalPType());
					returnTypeObject = (Type) ASTNodeBuilder.getNewNode(ast, voToModelInfo.getVoPType());
				}else {
					paramTypeObject = ast.newSimpleType(ast.newSimpleName(paramType));
					returnTypeObject = ast.newSimpleType(ast.newSimpleName(returnType));
				}
			}
			String paramName = paramType.substring(0, 1).toLowerCase() + paramType.substring(1);
			MethodDeclaration method = ast.newMethodDeclaration();
			method.modifiers().add(ast.newModifier(ModifierKeyword.PUBLIC_KEYWORD));
			method.setReturnType2(returnTypeObject);
			String methodName = paramType + ( Objects.nonNull(voToModelInfo.getModalPType()) ? "sTo" : "To") + returnType;
			methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
			method.setName(ast.newSimpleName(methodName));
			SingleVariableDeclaration newParam = ast.newSingleVariableDeclaration();
			newParam.setType(paramTypeObject);
			newParam.setName(ast.newSimpleName(paramName));
			method.parameters().add(newParam);
			getParentType(cu).bodyDeclarations().add(method);
		}

	}

	private void addMapperAnnotation() {
		addImport(cu, ast, Mapper.class.getName());
		addImport(cu, ast, ReportingPolicy.class.getName());
		addImport(cu, ast, NullValueMappingStrategy.class.getName());
		addImport(cu, ast, EBLMoneyMapper.class.getName());
		addImport(cu, ast, LocalDateMapper.class.getName());
		addImport(cu, ast, MeasureMapper.class.getName());
		NormalAnnotation mapperAnnotation = ast.newNormalAnnotation();
		mapperAnnotation.setTypeName(ast.newSimpleName("Mapper"));
		MemberValuePair pair = ast.newMemberValuePair();
		pair.setName(ast.newSimpleName("unmappedSourcePolicy"));
		pair.setValue(ast.newQualifiedName(ast.newSimpleName("ReportingPolicy"), ast.newSimpleName("IGNORE")));
		mapperAnnotation.values().add(pair);
		pair = ast.newMemberValuePair();
		pair.setName(ast.newSimpleName("componentModel"));
		StringLiteral sl = ast.newStringLiteral();
		sl.setLiteralValue("spring");
		pair.setValue(sl);
		mapperAnnotation.values().add(pair);
		pair = ast.newMemberValuePair();
		pair.setName(ast.newSimpleName("nullValueMappingStrategy"));
		pair.setValue(
				ast.newQualifiedName(ast.newSimpleName("NullValueMappingStrategy"), ast.newSimpleName("RETURN_NULL")));
		mapperAnnotation.values().add(pair);
		pair = ast.newMemberValuePair();
		pair.setName(ast.newSimpleName("uses"));
		ArrayInitializer array = ast.newArrayInitializer();
		TypeLiteral t = ast.newTypeLiteral();
		t.setType(ast.newSimpleType(ast.newSimpleName(MeasureMapper.class.getSimpleName())));
		array.expressions().add(t);
		t = ast.newTypeLiteral();
		t.setType(ast.newSimpleType(ast.newSimpleName(LocalDateMapper.class.getSimpleName())));
		array.expressions().add(t);
		t = ast.newTypeLiteral();
		t.setType(ast.newSimpleType(ast.newSimpleName(EBLMoneyMapper.class.getSimpleName())));
		array.expressions().add(t);
		pair.setValue(array);
		mapperAnnotation.values().add(pair);
		getParentType(cu).modifiers().add(0, mapperAnnotation);
	}

}
