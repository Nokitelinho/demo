/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.bi;

import java.util.List;

import org.eclipse.jdt.core.dom.ImportDeclaration;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.BaseASTVisitor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;

/**
 * @author A-1759
 *
 */
public class BIASTVisitor extends BaseASTVisitor {
	
	private ImportDeclaration remoteImport;

	public BIASTVisitor(Logger logger) {
		super(logger);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.TypeDeclaration)
	 */
	@Override
	public boolean visit(ImportDeclaration node) {
		String importCandidate=node.getName().toString();
		if(importCandidate.equals("java.rmi.RemoteException")){
			remoteImport = node;
		}
		return super.visit(node);
	}

	public ImportDeclaration getRemoteImport() {
		return remoteImport;
	}
	
	
	
}
