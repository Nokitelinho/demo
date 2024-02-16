/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util;

/**
 * @author A-1759
 *
 */
public class GetterSetterUtil {

	public static String getterName(String name, boolean isBool) {
		String prefix = "get";
		if (isBool) {
			prefix = "is";
			if (name.startsWith("is")) {
				String getterName = prefix + name.substring(2, 3).toUpperCase() + name.substring(3);
				if (name.equals(getterName)) {
					return getterName;
				}
			}
		}
		String appender = name.substring(0, 1).toUpperCase() + name.substring(1);
		return prefix + appender;
	}

	public static String setterName(String name){
		return "set"+name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	public static String getFieldName(String name, boolean isBool){
		String prefix = "get";
		if (isBool) {
			prefix = "is";
		}
		int index = prefix.length();
		return name.substring(index, index+1).toLowerCase() + name.substring(index+1);
	}
}
