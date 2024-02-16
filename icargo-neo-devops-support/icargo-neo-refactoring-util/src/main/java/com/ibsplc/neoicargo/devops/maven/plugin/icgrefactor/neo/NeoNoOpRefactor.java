/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.neo;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.BaseASTVisitor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.FieldInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;

/**
 * @author A-1759
 *
 */
public class NeoNoOpRefactor extends AbstractRefactor {

	public NeoNoOpRefactor(Logger logger) {
		super(logger);
	}

	@Override
	public void setASTVisitor() {
		this.visitor = new BaseASTVisitor(logger);

	}

	@Override
	public void refactor() {
		this.isSourceChanged = false;
	}


	public boolean hasMethod(MethodDeclaration md) {
		if (md.isConstructor()) {
			return this.visitor.getConstructorList().stream()
					.anyMatch(constroctorInfo -> (constroctorInfo.isNoArgsConstructor() && md.parameters().isEmpty())
							|| constroctorInfo.getParams().toString().equals(md.parameters().toString()));
		} else {
			return this.visitor.getMethodDeclarationList().stream()
					.anyMatch(methodD -> methodD.getName().toString().equals(md.getName().toString())
							&& methodD.parameters().toString().equals(md.parameters().toString()));
		}

	}

	public boolean hasField(FieldDeclaration fd) {
		VariableDeclarationFragment fragment = (VariableDeclarationFragment) fd.fragments().get(0);
		return this.visitor.getFields().stream().anyMatch(f -> fragment.getName().toString().equals(f.getFieldName()));
	}
}
