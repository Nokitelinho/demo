/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.ConsoleLogger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.RefactorConfig;
import com.sandeep.flowxmlparser.parser.FlowBeanXMLParser;

/**
 * @author A-1759
 *
 */
public class ClassicCodeExtractor {

	private Logger logger;

	private ExtractConfig extractConfig;

	public ClassicCodeExtractor(Logger logger) {
		this.logger = logger;
	}

	private void readConfig(String configFile) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		InputStream is = new FileInputStream(configFile);
		extractConfig = mapper.readValue(is, ExtractConfig.class);
	}

	public void extractCode(String configFile) throws Exception {
		readConfig(configFile);
		File destFile = new File(extractConfig.getDestDir());
		SourceModel sourceModel = extractConfig.getSourceFile();
		File sourceFile = new File(extractConfig.getSrcDir() + File.separator + sourceModel.clazz);
		Map<String, String> implMap = extractConfig.getImplMap();
		Map<String, SourceModel> dependentSources = new HashMap<>();
		Set<String> voAndExceptions = new HashSet<>();
		Map<String, String> beanToClassMap = null;
		if (Objects.nonNull(extractConfig.getFlowBeanFile())) {
			beanToClassMap = new FlowBeanXMLParser().parse(new File(extractConfig.getFlowBeanFile()));
		}
		List<String> exclusionList = new ArrayList<>();
		if(Objects.nonNull(extractConfig.getExclusionListFile())){
			File exclusionListFile = new File(extractConfig.getExclusionListFile());
			try(BufferedReader reader = new BufferedReader(new FileReader(exclusionListFile))){
				String line = reader.readLine();
				while(Objects.nonNull(line)) {
					exclusionList.add(line.replace('\\', '.').replace('/', '.').replaceAll(".java", ""));
					line = reader.readLine();
				}
			}
		}
		CodeExtractorRefactor refactor = new CodeExtractorRefactor(logger, sourceModel, extractConfig.getSrcPckg(),
				dependentSources, voAndExceptions, extractConfig.getFlowDir());
		refactor.setBeanToClassMap(beanToClassMap);
		refactor.setExclusionList(exclusionList);
		sourceModel.refactor = refactor;
		RefactorConfig config = new RefactorConfig();
		config.setSrcDir(extractConfig.getSrcDir());
		config.setDstDir(extractConfig.getDestDir());
		refactor.refactor(sourceFile, destFile, config);
		boolean repeat = true;
		while (repeat) {
			repeat = false;
			List<SourceModel> depSourceModels = new ArrayList<>(dependentSources.values());
			for (SourceModel depSourceModel : depSourceModels) {
				if (depSourceModel.methods.stream().anyMatch(m -> !depSourceModel.processedMethods.contains(m))) {
					if (Objects.nonNull(implMap) && Objects.nonNull(implMap.get(depSourceModel.clazz))) {
						SourceModel implSourceModel = dependentSources.get(implMap.get(depSourceModel.clazz));
						if (Objects.isNull(implSourceModel)) {
							implSourceModel = new SourceModel();
							implSourceModel.setClazz(implMap.get(depSourceModel.clazz));
							implSourceModel.setMethods(new ArrayList<>());
							dependentSources.put(implMap.get(depSourceModel.clazz), implSourceModel);
						}
						implSourceModel.getMethods().addAll(depSourceModel.getMethods());
					}
					String srcFile = depSourceModel.clazz.replace('.', '/') + ".java";
					File depSrcFile = new File(extractConfig.getSrcDir() , srcFile);
					if (!depSrcFile.exists()) {
						continue;
					}
					repeat = true;
					if (Objects.isNull(depSourceModel.refactor)) {
						depSourceModel.refactor = new CodeExtractorRefactor(logger, depSourceModel,
								extractConfig.getSrcPckg(), dependentSources, voAndExceptions,
								extractConfig.getFlowDir());
						depSourceModel.refactor.setBeanToClassMap(beanToClassMap);
						depSourceModel.refactor.setExclusionList(exclusionList);
					}
					System.out.println("Refactoring " + depSrcFile);
					depSourceModel.refactor.refactor(depSrcFile, destFile, config);
					if (depSourceModel.clazz.contains("feature") && depSourceModel.clazz.endsWith("Feature")) {
						List<File> featureComponents = getFiles(depSrcFile.getParentFile(), depSrcFile);
						for (File featureComponent : featureComponents) {
							System.out.println("Refactoring " + featureComponent);
							SourceModel sourceModelC = new SourceModel();
							sourceModelC.setClazz(
									featureComponent.getPath().substring(featureComponent.getPath().indexOf("com"))
											.replace(File.separatorChar, '.').replace(".java", ""));
							sourceModelC.setMethods(new ArrayList<>());
							dependentSources.put(sourceModelC.getClazz(), sourceModelC);
							CodeExtractorRefactor featureRefactor= new CodeExtractorRefactor(logger, sourceModelC, extractConfig.getSrcPckg(),
									dependentSources, voAndExceptions, extractConfig.getFlowDir());
							featureRefactor.setExclusionList(exclusionList);
							featureRefactor.refactor(featureComponent, destFile, config);
							
						}
					}
				}
			}
		}
		for (SourceModel depSourceModel : dependentSources.values()) {
			if (Objects.nonNull(depSourceModel.refactor)) {
				depSourceModel.refactor.removeMethods();
			}
		}
		Set<String> voAndExceptionsCopy = new HashSet<>();
		Set<String> completedVos = new HashSet<>();
		do {
			voAndExceptionsCopy.clear();
			voAndExceptionsCopy.addAll(voAndExceptions);
			for (String voAndException : voAndExceptionsCopy) {
				String srcFile = voAndException.replace('.','/').replace('\\', '/') + ".java";
				File depSrcFile = new File(extractConfig.getSrcDir() , srcFile);
				if (!depSrcFile.exists()) {
					continue;
				}

				new VODependencyExtractor(logger, extractConfig.getSrcPckg(), voAndExceptions).refactor(depSrcFile,
						destFile, config);
			}
			completedVos.addAll(voAndExceptionsCopy);
			voAndExceptions.removeAll(completedVos);
		} while (!voAndExceptions.isEmpty());
	}

	protected File createTargetFile(String srcFile, String destDir) throws IOException {
		String baseDir = destDir + "/" + srcFile;
		File dir = new File(baseDir.substring(0, baseDir.lastIndexOf("/")));
		dir.mkdirs();
		File outFile = new File(baseDir);
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		return outFile;
	}

	protected CompilationUnit createCompilationUnit(Document doc) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(doc.get().toCharArray());
		return (CompilationUnit) parser.createAST(null);
	}

	private List<File> getFiles(File dir, File excludeFile) {
		List<File> files = new ArrayList<>();
		File[] fileArr = dir.listFiles(file -> (file.isDirectory() && !file.getName().equals("micro"))
				|| file.getName().toLowerCase().endsWith(".java"));
		for (File file : fileArr) {
			if (file.isFile()) {
				if (!file.getName().equals(excludeFile.getName())) {
					files.add(file);
				}
			} else {
				files.addAll(getFiles(file, excludeFile));
			}
		}
		return files;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		new ClassicCodeExtractor(new ConsoleLogger()).extractCode(args[0]);
	}

}
