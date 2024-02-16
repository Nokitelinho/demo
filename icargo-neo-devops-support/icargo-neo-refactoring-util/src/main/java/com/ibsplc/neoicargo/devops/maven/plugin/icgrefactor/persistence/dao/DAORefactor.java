/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.persistence.dao;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleType;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.BaseASTVisitor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ConsoleLogger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.GenImplInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.MethodInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.RefactorConfig;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.VariableInfo;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.controller.ControllerASTVisitor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.controller.ControllerRefactor;
import com.ibsplc.neoicargo.framework.util.currency.Money;

/**
 * @author A-1759
 *
 */
public class DAORefactor extends ControllerRefactor {

	private static final List<String> expListToRemove = Arrays.asList("SystemException", "PersistenceException");
	private GenImplInfo daoInfo;
	private List<String> removedImports;

	public DAORefactor(Logger logger) {
		super(logger);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor#
	 * refactor()
	 */
	@Override
	public void refactor() {
		refactorPackage(cu, ast, visitor);
		removedImports = refactorIcgImports(cu, ast, visitor);
		refactorLoggers();
		handleLogonAttrbiutes();
		// removeParentClass(cu);
		/*
		 * if (getParentType(cu).isInterface() &&
		 * getParentType(cu).getName().toString().toUpperCase().endsWith("DAO")) {
		 * this.daoInfo = new GenImplInfo();
		 * daoInfo.setPackageDecl(this.packageToCreate);
		 * daoInfo.setClazzName(getParentType(cu).getName().toString());
		 * daoInfo.setSuffix("Impl"); daoInfo.setImports(cu.imports());
		 * removeThrownExceptions(); try { new
		 * DaoImplGenerator(logger).generate(daoInfo, destDir, refactorConfig); } catch
		 * (Exception e) { logger.error(e.toString()); e.printStackTrace(); } }
		 */
	}


	private void removeThrownExceptions() {
		for (MethodDeclaration md : visitor.getPublicMethods()) {
			List<SimpleType> expList = md.thrownExceptionTypes();
			List<SimpleType> toRemove = expList.stream()
					.filter(e -> expListToRemove.contains(e.getName().toString())
							|| removedImports.stream().anyMatch(i -> i.endsWith(e.getName().toString())))
					.collect(Collectors.toList());
			expList.removeAll(toRemove);
			MethodInfo methodInfo = new MethodInfo();
			methodInfo.setMethodDeclaration(md);
			daoInfo.getMethods().add(methodInfo);
		}
	}

	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 2) {
			System.out.println("provide the source and destination locations.");
			System.exit(-1);
		}
		DAORefactor en = new DAORefactor(new ConsoleLogger());
		File src = new File(args[0]);
		File dst = new File(args[1]);
		en.refactor(src, dst, new RefactorConfig() {
			{
				setSourcePackage("operations.shipment");
				setTargetPackage("com.ibsplc.neoicargo.awb");
			}
		});
	}
}
