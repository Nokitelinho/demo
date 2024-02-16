/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.neoservices;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;

/**
 * @author A-1759
 *
 */
public class ValidatorVisitor extends ComponentVisitor {
	
	private boolean isValidatorMethod;
	List<CastExpression> castExpressions = new ArrayList<>();
	MethodDeclaration validateMethod;

	public ValidatorVisitor(Logger logger) {
		super(logger);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		isValidatorMethod = "validate".equals(node.getName().toString());
		if(isValidatorMethod){
			validateMethod = node;
		}
		return super.visit(node);
	}
	
	@Override
	public boolean visit(CastExpression node) {
		if(isValidatorMethod){
			castExpressions.add(node);
		}
		return super.visit(node);
	}
}
