package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor;

import java.util.List;
import java.util.Objects;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConstructorInfo {

	List<SingleVariableDeclaration> params;
	
	MethodDeclaration constructorDecl;
	
	boolean isNoArgsConstructor;
	
	
	public boolean isNoArgsConstructor(){
		return Objects.isNull(params) || params.size()==0;
	}
}
