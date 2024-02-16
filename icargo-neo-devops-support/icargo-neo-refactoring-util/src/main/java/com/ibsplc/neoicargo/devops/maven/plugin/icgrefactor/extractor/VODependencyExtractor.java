/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.extractor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.AbstractRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.BaseASTVisitor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ConsoleLogger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.RefactorConfig;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.vo.VORefactor;

/**
 * @author A-1759
 *
 */
public class VODependencyExtractor extends AbstractRefactor {

	private Set<String> voAndExceptions;
	private String srcPckgs;
	private List<ImportDeclaration> srcImports;

	public VODependencyExtractor(Logger logger, String srcPckgs, Set<String> voAndExceptions) {
		super(logger);
		this.voAndExceptions = voAndExceptions;
		this.srcPckgs = srcPckgs;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setASTVisitor() {
		this.visitor = new BaseASTVisitor(logger);

	}

	@Override
	public void refactor() {
		this.packageToCreate = visitor.getPackageName();
		this.srcImports = this.visitor.getIcgOtherImports().stream().filter( i -> i.getName().toString().contains(srcPckgs)).collect(Collectors.toList());
		List<MethodDeclaration> methods = this.visitor.getMethodDeclarationList();
		for (MethodDeclaration md : methods) {
			for (Object param : md.parameters()) {
				String typeS = getType(((SingleVariableDeclaration) param).getType());
				if (Objects.nonNull(typeS)) {
					addToVOSet(typeS);
				}

			}
			String typeS = getType(md.getReturnType2());
			if (Objects.nonNull(typeS)) {
				addToVOSet(typeS);
			}
		}

	}

	private String getType(Type type) {
		String typeS = type instanceof ParameterizedType ? ((ParameterizedType) type).typeArguments().get(0).toString()
				: type.toString();
		if (type instanceof PrimitiveType || typeS.startsWith("String") || typeS.startsWith("Object")) {
			return null;
		}
		return typeS;
	}

	private void addToVOSet(String type) {
		srcImports.stream().filter(i -> i.getName().toString().endsWith("." + type)).findFirst()
				.ifPresentOrElse(i -> {
					voAndExceptions.add(i.getName().toString());
				}, () -> {
					List<ImportDeclaration> allImports = cu.imports();
					if (allImports.stream().noneMatch(i -> i.getName().toString().endsWith(type))) {
						// Same package variable
						String typeF = this.packageToCreate + "." + type;
						voAndExceptions.add(typeF);
					}
				});

	}
	
	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 2) {
			System.out.println("provide the source and destination locations.");
			System.exit(-1);
		}
		VODependencyExtractor en = new VODependencyExtractor(new ConsoleLogger(),"operations.shipment", new HashSet<String>());
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
