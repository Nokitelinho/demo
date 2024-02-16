/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.neoservices;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;

/**
 * @author A-1759
 *
 */
public class ComponentRefactor extends AbstractRefactor {

	public ComponentRefactor(Logger logger) {
		super(logger);
		// TODO Auto-generated constructor stub
		isSourceChanged=false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor#
	 * setASTVisitor()
	 */
	@Override
	public void setASTVisitor() {
		visitor = new ComponentVisitor(logger);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor#
	 * refactor()
	 */
	@Override
	public void refactor() {
		List<String> handledTypes = new ArrayList<>();
		ComponentVisitor compVisitor = ((ComponentVisitor) visitor);
		isSourceChanged = isSourceChanged || !compVisitor.mapperInstances.isEmpty();
		packageToCreate = cu.getPackage().getName().toString();
		for (ImportDeclaration imp : compVisitor.mapperImports) {
			ComponentMappers.addMapperInfo(((QualifiedName) imp.getName()).getName().toString(),
					imp.getName().toString());
		}
		for (QualifiedName qName : compVisitor.mapperInstances) {
			String mapperType = qName.getQualifier().toString();
			ComponentMappers.addMapper(getParentType(cu).getName().toString(), mapperType);
			String autoWireField = mapperType.substring(0, 1).toLowerCase() + mapperType.substring(1);
			if (!handledTypes.contains(mapperType)) {
				autowireField(mapperType, autoWireField);
				handledTypes.add(mapperType);
			}
			assignExpression(qName.getParent(), ast.newSimpleName(autoWireField), qName, mapperType);
		}
		if (!compVisitor.getQuantityMethods.isEmpty()) {
			isSourceChanged = true;
			List<String> autoWiredFields = new ArrayList<>();
			for (MethodInvocation getQuantity : compVisitor.getQuantityMethods) {
				String type  = getQuantity.getExpression().toString();
				String varName = type.substring(0,1).toLowerCase()+type.substring(1);
				
				if(!autoWiredFields.contains(type)){
					autowireField(type,varName);
					autoWiredFields.add(type);
				}
				getQuantity.setExpression(ast.newSimpleName(varName));
			}
		}

	}

	private void assignExpression(ASTNode parentNode, Expression exp, ASTNode currentNode, String fieldType) {
		if (parentNode instanceof VariableDeclarationFragment) {

			((VariableDeclarationFragment) parentNode).setInitializer(exp);

			VariableDeclarationStatement statement = (VariableDeclarationStatement) parentNode.getParent();
			statement.setType(ast.newSimpleType(ast.newSimpleName(fieldType)));
		} else if (parentNode instanceof MethodInvocation) {
			MethodInvocation pNode = (MethodInvocation) parentNode;
			if (pNode.arguments().contains(currentNode)) {
				pNode.arguments().add(pNode.arguments().indexOf(currentNode), exp);
				currentNode.delete();
			} else {
				pNode.setExpression(exp);
			}
		} else if (parentNode instanceof Assignment) {
			String varName = ((Assignment) parentNode).getLeftHandSide().toString();
			((Assignment) parentNode).setRightHandSide(exp);

		}
	}
}
