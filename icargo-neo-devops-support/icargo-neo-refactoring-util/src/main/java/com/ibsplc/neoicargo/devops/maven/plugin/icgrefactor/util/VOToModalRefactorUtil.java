/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.VOToModelInfo;

/**
 * @author A-1759
 *
 */
public class VOToModalRefactorUtil {

	public static Map<String, ImportDeclaration> refactorImports(AST ast, List<ImportDeclaration> imports) {
		Map<String, ImportDeclaration> voToModelImports = new HashMap<>();
		for (ImportDeclaration imp : imports) {
			if (imp.getName().toString().endsWith("VO")) {
				voToModelImports.put(imp.getName().toString().replace("VO", "Model").replace("vo", "model"), imp);
			}
		}
		return voToModelImports;
	}

	public static Map<MethodDeclaration, List<VOToModelInfo>> refactorMethodDeclarations(AST ast, List<MethodDeclaration> methods, List<String> otherImportVOs) {
		 Map<MethodDeclaration, List<VOToModelInfo>> updatedVOMap = new HashMap<>();
		for (MethodDeclaration md : methods) {
			List<SingleVariableDeclaration> parameters = md.parameters();
			for (SingleVariableDeclaration svd : parameters) {
				if(updateTypetoModal(ast,svd.getType(),svd.getName().toString(),md,updatedVOMap,otherImportVOs)) {
					svd.setName(ast.newSimpleName(svd.getName().toString().replace("VO", "")+"Model"));
				}
			}
			Type returnType = md.getReturnType2();
			updateTypetoModal(ast,returnType, "", md, updatedVOMap,otherImportVOs);
			
		}
		return updatedVOMap;
	}
	
	private static boolean updateTypetoModal(AST ast,Type type, String voName, MethodDeclaration md, Map<MethodDeclaration, List<VOToModelInfo>> updatedVOMap, List<String> otherImportVOs) {
		boolean hasUpdated = false;
		if (type instanceof SimpleType) {
			SimpleName name = getSimpleNameFromSimpleType((SimpleType)type);
			if (name.getIdentifier().endsWith("VO") && !otherImportVOs.contains(name.getIdentifier())) {
				hasUpdated = true;
				updatedVOMap.putIfAbsent(md, new ArrayList<>());
				VOToModelInfo info = new VOToModelInfo();
				info.setVoType(name.getIdentifier());
				info.setVoName(voName);
				info.setModelType(name.getIdentifier().replace("VO", "Model"));
				info.setModelName(voName.replace("VO", "")+"Model");
				name.setIdentifier(info.getModelType());
			//	svd.setName(ast.newSimpleName(info.getModelName()));
				updatedVOMap.get(md).add(info);
				
			}
		}else if(type instanceof ParameterizedType) {
			List<SimpleName> simpleNames = new ArrayList<>();
			 getSimpleNameFromParameterizedType((ParameterizedType)type,simpleNames);
			 for(SimpleName name : simpleNames){
				 if (name.getIdentifier().endsWith("VO") && !otherImportVOs.contains(name.getIdentifier())) {
					 hasUpdated = true;
					 updatedVOMap.putIfAbsent(md, new ArrayList<>());
						VOToModelInfo info = new VOToModelInfo();
						info.setVoType(name.getIdentifier());
						info.setVoName(voName);
						info.setModelType(name.getIdentifier().replace("VO", "Model"));
						info.setModelName(voName.replace("VO", "")+"Model");
						info.setVoPType((ParameterizedType) ASTNodeBuilder.getNewNode(ast, type));
						name.setIdentifier(info.getModelType());
						info.setModalPType((ParameterizedType)type);
						//svd.setName(ast.newSimpleName(info.getModelName()));
						updatedVOMap.get(md).add(info);
					}
			 }
		}
		return hasUpdated;
	}
	
	private static void getSimpleNameFromParameterizedType(ParameterizedType type, List<SimpleName> simpleNames) {
		List<Type> parameterTypes = type.typeArguments();
		for(Type parameterType : parameterTypes) {
			if(parameterType instanceof SimpleType) {
				simpleNames.add(getSimpleNameFromSimpleType((SimpleType)parameterType));
			}else if(parameterType instanceof ParameterizedType){
				getSimpleNameFromParameterizedType((ParameterizedType)parameterType, simpleNames);
			}
		}
		
	}

  private static SimpleName getSimpleNameFromSimpleType(SimpleType type) {

    return type.getName() instanceof QualifiedName
        ? ((QualifiedName) type.getName()).getName()
        : (SimpleName) type.getName();
  }
}
