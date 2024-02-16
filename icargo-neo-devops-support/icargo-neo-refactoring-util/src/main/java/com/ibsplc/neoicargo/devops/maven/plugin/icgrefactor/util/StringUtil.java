/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util;

/**
 * @author A-1759
 *
 */
public class StringUtil {

	/**
	 * 
	 * @param fieldName
	 * @return
	 */
	public static String getSentenceFromCamelCaseString(String fieldName){
		String[] words = fieldName.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
		StringBuilder builder = new StringBuilder();
		
		for (String w : words) {
			builder.append(w).append(" ");
	    }
		builder.deleteCharAt(builder.length()-1);
		return builder.toString();
	}
}
