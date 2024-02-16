/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.vo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractGenerator;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.FieldInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.ASTNodeBuilder;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.util.currency.Money;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;

/**
 * @author A-1759
 *
 */
public class ModelGenerator extends AbstractGenerator {

//	private static final List<String> UNIT_FIELD_TYPES = Arrays.asList("Quantity", "Money");

	private static final List<String> EXCLUDE_IMPORTS = Arrays.asList(AbstractVO.class.getName(),
			Quantity.class.getName(), Money.class.getName());

	List<FieldInfo> fields;
//	private boolean hasUnitFields = false;

	private List<String> voSrcs;

	public ModelGenerator(Logger logger, List<FieldInfo> fields, List<String> voSrcs) {
		super(logger);
		this.fields = fields;
		this.voSrcs = voSrcs;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void refactor() {
		setPackage();
		addImports();
		addLombokAnnotations();
		addFields();
		setSuperClass();

	}

	protected void addImports() {
		for (ImportDeclaration importDecl : implInfo.getImports()) {
			if (EXCLUDE_IMPORTS.contains(importDecl.getName().toString())) {
				continue;
			}
			String importD = voSrcs.contains(importDecl.getName().toString())
					? importDecl.getName().toString().replaceAll("VO", "Model").replace("vo", "model")
					: importDecl.getName().toString();
			addImport(cu, ast, importD);
		}
	}

	private void setSuperClass() {
		addImport(cu, ast, BaseModel.class.getName());
		getParentType(cu).setSuperclassType(ast.newSimpleType(ast.newSimpleName(BaseModel.class.getSimpleName())));
	}

	private void addUnitOfMeasureField() {
		addImport(cu, ast, Units.class.getName());
		VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
		fragment.setName(ast.newSimpleName("unitOfMeasure"));
		FieldDeclaration field = ast.newFieldDeclaration(fragment);
		field.setType(ast.newSimpleType(ast.newSimpleName(Units.class.getSimpleName())));
		field.modifiers().add(ast.newModifier(ModifierKeyword.PRIVATE_KEYWORD));
		getParentType(cu).bodyDeclarations().add(field);

	}

	private void addFields() {
		List<String> excludeList = implInfo.getImports().stream()
				.filter(i -> !voSrcs.contains(((QualifiedName) i.getName()).toString()))
				.map(i -> ((QualifiedName) i.getName()).getName().toString()).collect(Collectors.toList());
		for (FieldInfo fieldInfo : fields) {
			List modifiers = fieldInfo.getFieldDeclaration().modifiers();
			if (modifiers.stream().anyMatch(m -> !(m instanceof Modifier) || ((Modifier) m).isStatic())) {
				continue;
			}
			VariableDeclarationFragment fragment = ast.newVariableDeclarationFragment();
			String fieldName = fieldInfo.getFieldName();
			fragment.setName(ast.newSimpleName(fieldName));
			FieldDeclaration field = ast.newFieldDeclaration(fragment);
			String fieldTypeName = ASTNodeBuilder.getTypeName(fieldInfo.getFieldDeclaration().getType());
			field.setType(ASTNodeBuilder.getNewType(ast, fieldInfo.getFieldDeclaration().getType(), "VO", "Model",
					excludeList));
			field.modifiers().add(ast.newModifier(ModifierKeyword.PRIVATE_KEYWORD));
			getParentType(cu).bodyDeclarations().add(field);
		}
	}

	private void addLombokAnnotations() {
		MarkerAnnotation annotation = ast.newMarkerAnnotation();
		annotation.setTypeName(ast.newSimpleName(lombok.Getter.class.getSimpleName()));
		getParentType(cu).modifiers().add(0, annotation);
		annotation = ast.newMarkerAnnotation();
		annotation.setTypeName(ast.newSimpleName(lombok.Setter.class.getSimpleName()));
		getParentType(cu).modifiers().add(0, annotation);

	}

}
