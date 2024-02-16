package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.vo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.BaseASTVisitor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.converter.ConverterASTVisitor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.GetterSetterUtil;

public class VOASTVisitor extends ConverterASTVisitor {
	
	

	public VOASTVisitor(Logger logger) {
		super(logger);
	}
	
	
	
	private boolean isBooleanField;
	private List<String> getterStterList = new ArrayList<>();
	
	

	@Override
	public boolean visit(FieldDeclaration node) {
		isBooleanField = node.getType().toString().equals("boolean");
		return super.visit(node);	
	}
	
	@Override
	public void endVisit(FieldDeclaration node) {
		isBooleanField = false;
		super.endVisit(node);
	}
	
	@Override
	public boolean visit(VariableDeclarationFragment node) {
		if(isField){
			getterStterList.add(GetterSetterUtil.getterName(node.getName().toString(), isBooleanField));
			getterStterList.add(GetterSetterUtil.setterName(node.getName().toString()));
		}
		return super.visit(node);
	}
	
	
	
	public List<String> getGetterStterList() {
		return getterStterList;
	}
	
	
}
