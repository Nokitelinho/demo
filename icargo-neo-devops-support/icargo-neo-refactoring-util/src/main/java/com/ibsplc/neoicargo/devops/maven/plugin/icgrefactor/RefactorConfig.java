package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefactorConfig {

	private String srcDir;
	
	private String dstDir;
	
	private String sourcePackage;
	
	private String targetPackage;

	private String module;
	
	private String neoSrcDir;
}

