/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.text.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.FeatureRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.bi.BIRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.cache.CacheRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.controller.ControllerRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.converter.ConverterRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.exception.ExceptionRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.proxy.ProxyRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.vo.VORefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.persistence.dao.DAORefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.services.ServiceEJBRefactor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util.ImportRefactorUtil;

/**
 * @author A-1759
 *
 */
public class ICGRefactorUtil {

	private static final String LINSEP = System.getProperty("line.separator");
	private static final List<String> packagesToExcl = Arrays.asList("builder", "event", "evaluator", "interceptor",
			"webservices", "worker", "wsproxy", "handler");
	private Logger logger;
	RefactorConfig refactorConfig;

	public ICGRefactorUtil(Logger logger) {
		this.logger = logger;
	}

	private void readConfig(String configFile) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		InputStream is = new FileInputStream(configFile);
		refactorConfig = mapper.readValue(is, RefactorConfig.class);
	}

	public void refactor(File src, File dst) throws Exception {
		List<File> sourceFiles = getFiles(src);
		List<String> entityAnnotations = Arrays.asList("@Entity", "@Embeddable", "@MappedSuperclass");
		List<String> voSrcs = sourceFiles.stream().map(f -> f.getPath())
				.filter(f -> f.contains(File.separator + "vo" + File.separator))
				.map(f -> f.substring(refactorConfig.getSrcDir().length() + 1,
						f.indexOf(".java")).replace(File.separator, "."))
				.collect(Collectors.toList());
		voSrcs = voSrcs.stream().map( s -> ImportRefactorUtil.refactorDevImport(refactorConfig.getSourcePackage(),
				refactorConfig.getTargetPackage(), s)).collect(Collectors.toList());
		File apiDir = new File(dst,"api");
		File businessDir = new File(dst,"business");
		for (File source : sourceFiles) {
			Document doc = new Document(getFile(source));
			CompilationUnit cu = createCompilationUnit(doc);
			logger.info("Source : " + source.toString());
			if (Objects.isNull(getParentType(cu))) {
				continue;
			}
			if (getParentType(cu).modifiers().stream()
					.anyMatch(m -> (m instanceof Annotation && entityAnnotations.contains(m.toString())))) {
				ControllerRefactor controllerRefactor = new ControllerRefactor(logger);	
				controllerRefactor.setEntity(true);
				controllerRefactor.refactor(source, businessDir, refactorConfig);
				continue;
			}
			String fullyQualityName = cu.getPackage().getName().toString() + "."
					+ getParentType(cu).getName().toString();
			ImportRefactorUtil.TYPE clazzType = ImportRefactorUtil.getType(fullyQualityName);
			switch (clazzType) {
			case CONVERTER: {
				new ConverterRefactor(logger).refactor(source, businessDir, refactorConfig);
				break;
			}case VO: {
				new VORefactor(logger,voSrcs).refactor(source, businessDir, refactorConfig);
				break;
			}
			case EXCEPTION: {
				new ExceptionRefactor(logger).refactor(source, apiDir, refactorConfig);
				break;
			}
			case BI: {
				new BIRefactor(logger,voSrcs).refactor(source, apiDir, refactorConfig);
				break;
			}
			case SERVICES: {
				new ServiceEJBRefactor(logger,voSrcs).refactor(source, businessDir, refactorConfig);
				break;
			}
			case PROXY: {
				new ProxyRefactor(logger).refactor(source, businessDir, refactorConfig);
				break;
			}
			case CACHE: {
				new CacheRefactor(logger).refactor(source, businessDir, refactorConfig);
				break;
			}
			case FEATURE: {
				new FeatureRefactor(logger).refactor(source, businessDir, refactorConfig);
				break;
			}
			case DAO:
			case CONTROLLER: {
				new ControllerRefactor(logger).refactor(source, businessDir, refactorConfig);
				break;
			}
			case UNKNWON: {
				if (getParentType(cu).getName().toString().endsWith("PK")) {
					new ControllerRefactor(logger).refactor(source, businessDir, refactorConfig);
				} else if (!getParentType(cu).superInterfaceTypes().isEmpty()
						&& getParentType(cu).superInterfaceTypes().get(0).toString().equals("QueryDAO")) {
					new DAORefactor(logger).refactor(source, businessDir, refactorConfig);
				} else if (cu.getPackage().toString().indexOf("business") >= 0
						&& !getParentType(cu).getName().toString().endsWith("DAO")
						&& packagesToExcl.stream().noneMatch(p -> cu.getPackage().toString().indexOf(p) >= 0)
						&& !getParentType(cu).getName().toString().endsWith("MDB")) {
					new ControllerRefactor(logger).refactor(source, businessDir, refactorConfig);
				}

			}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		if (args == null || args.length != 1) {
			System.out.println("provide the refactor config file location.");
			System.exit(-1);
		}
		ICGRefactorUtil refactorUtil = new ICGRefactorUtil(new ConsoleLogger());
		refactorUtil.readConfig(args[0]);
		File src = new File(refactorUtil.refactorConfig.getSrcDir());
		File dst = new File(refactorUtil.refactorConfig.getDstDir());
		refactorUtil.refactor(src, dst);
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
}
