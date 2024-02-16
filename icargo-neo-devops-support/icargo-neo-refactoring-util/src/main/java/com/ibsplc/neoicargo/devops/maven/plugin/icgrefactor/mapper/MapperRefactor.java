/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeLiteral;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.FieldInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.GetterSetterUtil;

/**
 * @author A-1759
 *
 */
public class MapperRefactor extends AbstractRefactor {

	public MapperRefactor(Logger logger) {
		super(logger);
	}

	private List<ASTNode> toDelete = new ArrayList<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor#
	 * setASTVisitor()
	 */
	@Override
	public void setASTVisitor() {
		this.visitor = new MapperVisitor(logger);

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
		packageToCreate = cu.getPackage().getName().toString();
		Optional<ImportDeclaration> importUnits = cu.imports().stream()
				.filter(i -> i.toString().contains("com.ibsplc.neoicargo.booking.modal.Units")).findFirst();
		if (importUnits.isPresent()) {
			importUnits.get().delete();
			addImport(cu, ast, "com.ibsplc.neoicargo.framework.core.lang.modal.Units");
		}
		for (FieldInfo field : visitor.getFields()) {
			if (field.getFieldName().equals("INSTANCE")) {
				field.getFieldDeclaration().delete();
			}
		}
		for (MethodDeclaration md : visitor.getPublicMethods()) {
			List modifiers = md.modifiers();
			for (Object modifier : modifiers) {
				if (modifier instanceof NormalAnnotation) {
					NormalAnnotation annot = (NormalAnnotation) modifier;
					if (annot.getTypeName().toString().equals("Mapping")) {
						Optional<MemberValuePair> op = annot.values().stream()
								.filter(m -> (((MemberValuePair) m).getName().toString().equals("expression")))
								.findAny();
						if (op.isPresent()) {

							MemberValuePair expression = op.get();
							String exp = expression.getValue().toString();
							if (exp.contains("QuantityMapper")) {
								Optional<MemberValuePair> opTarget = annot.values().stream()
										.filter(m -> (((MemberValuePair) m).getName().toString().equals("target")))
										.findAny();
								StringLiteral target = (StringLiteral) opTarget.get().getValue();
								if (exp.contains("constructEQuantity")) {
									mapForConstructEQuantity(annot, expression,
											(SingleVariableDeclaration) md.parameters().get(0),
											target.getLiteralValue());
								}else 
								if (exp.contains("getQuantity") && exp.contains("units")) {
									mapForGetQuantity("getQuantity",annot, expression,
											(SingleVariableDeclaration) md.parameters().get(0),
											target.getLiteralValue());
								}else if(exp.contains("constructQuantity") && md.parameters().size()==1){
									mapForGetQuantity("constructQuantity",annot, expression,
											(SingleVariableDeclaration) md.parameters().get(0),
											target.getLiteralValue());
								}
								else{
									StringLiteral l = ast.newStringLiteral();
									l.setEscapedValue(exp.replace("QuantityMapper", "quantityMapper"));
									expression.setValue(l);
								}
							}
						}
					}
				}
			}
		}
		toDelete.forEach(ASTNode::delete);
		removeInstanceFieldDecl();
	}

	private void removeInstanceFieldDecl() {
		for (FieldInfo fieldInfo : visitor.getFields()) {
			if (getParentType(cu).getName().toString().equals(fieldInfo.getFieldDeclaration().getType().toString())) {
				fieldInfo.getFieldDeclaration().delete();
			}
		}
	}

	private void mapForGetQuantity(String method, NormalAnnotation annot, MemberValuePair expression, SingleVariableDeclaration svd,
			String target) {

		String exp = expression.getValue().toString();
		String methodR = exp.substring(exp.indexOf(method) + method.length());
		String qMapperMethodArg = methodR.substring(1, methodR.indexOf(getMethodData(methodR, 0)));
		String[] args = qMapperMethodArg.split(",");
		//String typeAnnot = args[0].trim().substring(2, 5);
		//addImport(cu, ast, "com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper." + typeAnnot);
		String sourceMethodArg = args[1].trim();
		//String targetMethod = svd.getName().toString() + "." + GetterSetterUtil.getterName(target, false) + "()";
		if (sourceMethodArg.substring(0, sourceMethodArg.length() - 2).indexOf("()") == -1) {
			String getter = sourceMethodArg.substring(svd.getName().toString().length() + 1,
					sourceMethodArg.length() - 2);
			String source = GetterSetterUtil.getFieldName(getter, false);
			MemberValuePair sourceMem = ast.newMemberValuePair();
			sourceMem.setName(ast.newSimpleName("source"));
			StringLiteral sourceSl = ast.newStringLiteral();
			sourceSl.setLiteralValue(source);
			sourceMem.setValue(sourceSl);
			annot.values().add(sourceMem);
			toDelete.add(expression);
			/*MemberValuePair qualifiedBy = ast.newMemberValuePair();
			qualifiedBy.setName(ast.newSimpleName("qualifiedBy"));
			TypeLiteral typeL = ast.newTypeLiteral();
			typeL.setType(ast.newSimpleType(ast.newSimpleName(typeAnnot)));
			qualifiedBy.setValue(typeL);
			annot.values().add(qualifiedBy);*/
		}
	}

	private void mapForConstructEQuantity(NormalAnnotation annot, MemberValuePair expression,
			SingleVariableDeclaration svd, String target) {
		String exp = expression.getValue().toString();
		String methodR = exp.substring(exp.indexOf("constructEQuantity") + 18);
		String qMapperMethodArg = methodR.substring(1, methodR.indexOf(getMethodData(methodR, 0)));
		String targetMethod = svd.getName().toString() + "." + GetterSetterUtil.getterName(target, false) + "()";
		if (qMapperMethodArg.equals(targetMethod)) {
			toDelete.add(annot);
		} else if (qMapperMethodArg.substring(0, qMapperMethodArg.length() - 2).indexOf("()") == -1) {
			String getter = qMapperMethodArg.substring(svd.getName().toString().length() + 1,
					qMapperMethodArg.length() - 2);
			String source = GetterSetterUtil.getFieldName(getter, false);
			MemberValuePair sourceMem = ast.newMemberValuePair();
			sourceMem.setName(ast.newSimpleName("source"));
			StringLiteral sourceSl = ast.newStringLiteral();
			sourceSl.setLiteralValue(source);
			sourceMem.setValue(sourceSl);
			annot.values().add(sourceMem);
			toDelete.add(expression);
		}
	}

	private String getMethodData(String x, int count) {
		char c = x.charAt(0);
		if (c == '(') {
			count++;
		} else if (c == ')') {
			count--;
		}
		if (count == 0) {
			return x;
		} else {
			return getMethodData(x.substring(1), count);
		}
	}

}
