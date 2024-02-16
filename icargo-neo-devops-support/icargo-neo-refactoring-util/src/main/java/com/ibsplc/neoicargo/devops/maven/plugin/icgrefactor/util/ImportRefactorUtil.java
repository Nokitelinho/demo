/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.util;

/**
 * @author A-1759
 *
 */
public class ImportRefactorUtil {

	private static final String BUSINESS = ".business.";

	public enum TYPE {
		VO, EXCEPTION, SERVICES, CONTROLLER, BI, PROXY, UNKNWON, DAO, CACHE, FEATURE, CONVERTER
	}

	public static String refactorDevImport(String base, String target, String input) {
		if(input.indexOf(base)==-1) {
			return input;
		}
		StringBuilder builder = new StringBuilder(target);
		builder.append(".");
		
		String suffix = input.substring(input.indexOf(base) + base.length()+1);
		TYPE type = getType(input);
		switch (type) {
		case CONVERTER: {
			converter(builder, suffix);
			break;
		}case VO: {
			vo(builder, suffix);
			break;
		}
		case SERVICES: {
			services(builder, suffix);
			break;
		}
		case DAO: {
			dao(builder, suffix);
			break;
		}
		case BI:{
			api(builder,suffix);
			break;
		}
		case EXCEPTION: {
			exceptions(builder,suffix);
			break;
		}
		case FEATURE:{
			feature(builder,suffix);
			break;
		}
		case PROXY:{
			proxy(builder,suffix);
			break;
		}
		
		default: {
			component(builder, suffix);
		}

		}
		return builder.toString();
	}

	private static void converter(StringBuilder builder, String suffix) {
		// TODO Auto-generated method stub
		builder.append(suffix);
	}

	private static void proxy(StringBuilder builder, String input) {
		if(input.indexOf('.')!=-1) {
			builder.append(input.substring(0, input.indexOf("proxy")));
		}
		builder.append("component.proxy");	
		if(input.indexOf('.')!=-1) {
			builder.append(input.substring(input.indexOf('.')));
		}else {
			builder.append("."+input);
		}
	}

	private static void component(StringBuilder builder, String input) {
		if(input.indexOf('.')!=-1) {
			builder.append(input.substring(0, input.indexOf('.')+1));
		}
		builder.append("component");
		if(input.indexOf('.')!=-1) {
			builder.append(input.substring(input.indexOf('.')));
		}else {
			builder.append("."+input);
		}
		
	}
	
	private static void feature(StringBuilder builder, String input) {
		if(input.indexOf('.')!=-1) {
			builder.append(input.substring(0, input.indexOf("feature")));
		}
		builder.append("component");
		if(input.indexOf('.')!=-1) {
			builder.append("."+input.substring(input.indexOf("feature")));
		}else {
			builder.append("."+input);
		}
		
	}


	private static void dao(StringBuilder builder, String input) {
		if(input.indexOf('.')!=-1) {
			builder.append(input.substring(0, input.indexOf('.')+1));
		}
		builder.append("dao");
		//input = input.replace("DAO", "Dao").replace("SQL", "Sql");
		if(input.indexOf('.')!=-1) {
			builder.append(input.substring(input.indexOf('.')));
		}else {
			builder.append("."+input);
		}
		

	}

	private static void services(StringBuilder builder, String input) {
		if(input.indexOf('.')!=-1) {
			builder.append(input.substring(0, input.indexOf('.')+1));
		}
		builder.append("service");
		if(input.indexOf('.')!=-1) {
			builder.append(input.substring(input.indexOf('.')));
		}else {
			builder.append("."+input);
		}
		

	}

	private static void vo(StringBuilder builder, String input) {
			builder.append(input);
	}
	
	private static void exceptions(StringBuilder builder, String input) {
		input = input.replace("exception.", "").replace("..", ".");
		if(input.indexOf('.')!=-1) {
			builder.append(input.substring(0, input.indexOf('.')+1));
		}
		builder.append("exception");
		if(input.indexOf('.')!=-1) {
			builder.append(input.substring(input.indexOf('.')));
		}else {
			builder.append("."+input);
		}
		
	}
	
	
	private static void api(StringBuilder builder, String input) {
			builder.append(input);
	}

	public static TYPE getType(String input) {
		return getType(input, input.indexOf('.') >= 0);
	}

	public static TYPE getType(String input, boolean hasPackage) {
		if (input.indexOf(".vo") >= 0 && input.indexOf(".converter")>0) {
			return TYPE.CONVERTER;
		}
		if (input.indexOf(".vo") >= 0) {
			return TYPE.VO;
		}
		if (input.indexOf(".services.") >= 0 && input.endsWith("EJB")) {
			return TYPE.SERVICES;
		}
		if (input.indexOf(BUSINESS) >= 0 && input.indexOf("feature")>=0) {
			return TYPE.FEATURE;
		}
		if (input.indexOf(BUSINESS) >= 0 && input.endsWith("Controller")) {
			return TYPE.CONTROLLER;
		}
		if (input.indexOf(BUSINESS) >= 0 && input.endsWith("Exception")) {
			return TYPE.EXCEPTION;
		}
		if (input.indexOf(BUSINESS) >= 0
				&& (input.endsWith("Cache") || input.endsWith("Strategy") && input.indexOf("cache") >= 0)) {
			return TYPE.CACHE;
		}
		if (input.endsWith("BI") && (!hasPackage || input.indexOf(BUSINESS) >= 0)) {
			return TYPE.BI;
		}
		if (input.indexOf(BUSINESS) >= 0 && input.endsWith("Proxy")) {
			return TYPE.PROXY;
		}
		if (input.indexOf(".persistence.dao.") >= 0) {
			return TYPE.DAO;
		}
		return TYPE.UNKNWON;
	}
}
