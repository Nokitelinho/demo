/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.utils.io.FileUtils;

/**
 * @author A-1759
 *
 */
public abstract class BaseMojo extends AbstractMojo {

	/* Class members */
	@Parameter(property = "project", required = true, readonly = false)
	protected MavenProject project;

	@Parameter(required = true)
	protected List<Resource> resources;
	
	public List<File> resolveCandidateFiles() throws IOException{
		List<File> answer = new ArrayList<File>();
		for(Resource r : resources) {
			String includes = joinWithComma(r.getIncludes());
			String excludes = joinWithComma(r.getExcludes());
			File sourceDir = new File(r.getDirectory());
			answer = FileUtils.getFiles(sourceDir, includes, excludes, false);
		}
		return answer;
	}
	
	static String joinWithComma(List<String> arr) {
		if (arr == null)
			return "";
		StringBuilder sbul = new StringBuilder();
		for (String s : arr) {
			if (sbul.length() > 0)
				sbul.append(',');
			sbul.append(s);
		}
		return sbul.toString();
	}	
	public MavenProject getProject() {
		return project;
	}

	public void setProject(MavenProject project) {
		this.project = project;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}



}
