/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.text.Document;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.apimodal.ApiModalRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.mapper.MapperRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.neoservices.ComponentRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.neoservices.ValidatorRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.test.TestRefactor;

/**
 * @author A-1759
 *
 */
public class NeoRefactorUtil {

	private static final String LINSEP = System.getProperty("line.separator");
	private Logger logger;

	List<String> annotList = Arrays.asList("Component", "Service");

	public NeoRefactorUtil(Logger logger) {
		this.logger = logger;
	}

	public void refactor(File src, File dst) throws Exception {
		List<File> sourceFiles = getFiles(src);
		for (File source : sourceFiles) {
			Document doc = new Document(getFile(source));
			CompilationUnit cu = createCompilationUnit(doc);
			logger.info("Source : " + source.toString());
			if (Objects.isNull(getParentType(cu))) {
				continue;
			}
			String name = getParentType(cu).getName().toString();
			if (name.endsWith("Mapper") && !name.endsWith("QueryMapper") && getParentType(cu).isInterface()) {
				new MapperRefactor(logger).refactor(source, dst, null);
			} else if (name.endsWith("Test")) {
				new TestRefactor(logger).refactor(source, dst, null);
			} else if (cu.getPackage().getName().toString().contains("model")
					|| (name.endsWith("Event") && cu.getPackage().getName().toString().contains("events")
							&& !getParentType(cu).modifiers().toString().contains("Component")
							|| name.endsWith("VO") && cu.getPackage().getName().toString().contains("vo"))) {
				new ApiModalRefactor(logger).refactor(source, dst, null);
			} else if (getParentType(cu).modifiers().stream().anyMatch(
					f -> f instanceof Annotation && annotList.contains(((Annotation) f).getTypeName().toString()))) {
				if (getParentType(cu).getName().toString().endsWith("Validator")
						&& getParentType(cu).getSuperclassType().toString().contains("Validator")) {
					new ValidatorRefactor(logger).refactor(source, dst, null);
				} else {
					new ComponentRefactor(logger).refactor(source, dst, null);
				}

			}

		}

	}

	private List<File> getFiles(File dir) {
		List<File> files = new ArrayList<>();
		File[] fileArr = dir.listFiles(file -> (file.isDirectory() && !file.getName().equals("micro"))
				|| file.getName().toLowerCase().endsWith(".java"));
		for (File file : fileArr) {
			if (file.isFile()) {
				files.add(file);
			} else {
				files.addAll(getFiles(file));
			}
		}
		return files;
	}

	private String getFile(File f) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			String line = null;
			StringBuilder cbuf = new StringBuilder(1000);
			while ((line = br.readLine()) != null) {
				cbuf.append(line);
				cbuf.append(LINSEP);
			}
			return cbuf.toString();
		}
	}

	protected CompilationUnit createCompilationUnit(Document doc) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(doc.get().toCharArray());
		return (CompilationUnit) parser.createAST(null);
	}

	protected TypeDeclaration getParentType(CompilationUnit cu) {
		if (cu.types() == null || cu.types().size() == 0) {
			logger.warn("Type does not refer to a valid class");
			return null;
		}
		TypeDeclaration td = TypeDeclaration.class.cast(cu.types().get(0));
		return td;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		if (args == null || args.length != 2) {
			System.out.println("provide the source and destination locations.");
			System.exit(-1);
		}

		File src = new File(args[0]);
		File dst = new File(args[1]);
		new NeoRefactorUtil(new ConsoleLogger()).refactor(src, dst);
	}

}
