/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.apimodal;

import java.util.Optional;

import org.eclipse.jdt.core.dom.ImportDeclaration;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.BaseASTVisitor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.sun.xml.bind.v2.schemagen.xmlschema.Import;

/**
 * @author A-1759
 *
 */
public class ApiModalRefactor extends AbstractRefactor {

	public ApiModalRefactor(Logger logger) {
		super(logger);
		isSourceChanged = false;
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
		this.visitor = new BaseASTVisitor(logger);
		this.visitor.neo = true;

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
		if (this.visitor.getFields().stream()
				.anyMatch(fieldInfo -> fieldInfo.getFieldDeclaration().getType().toString().equals("Units"))) {
			isSourceChanged = true;
			packageToCreate = "api.java." + cu.getPackage().getName().toString();
			addImport(cu, ast, "com.ibsplc.neoicargo.framework.core.lang.modal.Units");
			Optional<ImportDeclaration> impOp = cu.imports().stream()
					.filter(i -> ((ImportDeclaration) i).getName().toString().equals("com.ibsplc.neoicargo.booking.modal.Units"))
					.findFirst();
			if(impOp.isPresent()){
				impOp.get().delete();
			}
		}

	}

}
