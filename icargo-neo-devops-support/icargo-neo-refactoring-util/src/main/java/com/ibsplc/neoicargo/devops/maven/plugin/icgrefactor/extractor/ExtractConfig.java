/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * @author A-1759
 *
 */
@Getter
@Setter
public class ExtractConfig {

	private String srcDir;
	
	private String destDir;
	
	private String srcPckg;
	
	private Map<String,String> implMap;
	
	private SourceModel sourceFile;
	
	private String flowDir;
	
	private String flowBeanFile;
	
	private String exclusionListFile;
}

@Getter
@Setter	
class SourceModel{
	
	String clazz;
	
	List<String> methods;
	
	List<String> processedMethods = new ArrayList<>();
	
	CodeExtractorRefactor refactor;
}
