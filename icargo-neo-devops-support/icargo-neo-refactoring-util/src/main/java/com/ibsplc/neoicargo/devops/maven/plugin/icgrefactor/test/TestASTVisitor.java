/**
 * 
 */
package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.internal.core.search.matching.MatchLocatorParser.MethodButNoClassDeclarationVisitor;

import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.BaseASTVisitor;
import com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.Logger;

/**
 * @author A-1759
 *
 */
public class TestASTVisitor extends BaseASTVisitor {

	List<MethodInvocation> mockQuantityInvocList = new ArrayList<>();
	List<MethodInvocation> getQuantityInvocList = new ArrayList<>();
	List<MethodInvocation> constructEquantityList = new ArrayList<>();
	List<MethodInvocation> timeZoneHelperList = new ArrayList<>();
	List<MethodInvocation> getlocalDateList = new ArrayList<>();
	List<MethodInvocation> setApplicationContextList = new ArrayList<>();
	MethodInvocation initMock;
	MethodInvocation stationDefault;

	private static final List<String> methodList = Arrays.asList("getBean", "getTimeZone");

	public TestASTVisitor(Logger logger) {
		super(logger);
		neo = true;
	}

	@Override
	public boolean visit(MethodInvocation node) {
		String methodName = node.getName().toString();
		if ("performInitialisation".equals(methodName) && "MockQuantity".equals(node.getExpression().toString())) {
			mockQuantityInvocList.add(node);
		} else if ("getQuantity".equals(methodName) && "Quantities".equals(node.getExpression().toString())) {
			getQuantityInvocList.add(node);
		} else if ("constructEQuantity".equals(methodName)
				&& "QuantityMapper".equals(node.getExpression().toString())) {
			constructEquantityList.add(node);
		} else if (methodList.contains(methodName)) {
			timeZoneHelperList.add(node);
			if ("setApplicationContext".equals(methodName)) {
				setApplicationContextList.add(node);
			}
		} else if ("initMocks".equals(methodName)) {
			initMock = node;
		} else if ("getStationDefaultUnit".equals(methodName)) {
			stationDefault = node;
		} else if ("getLocalDate".equals(methodName)) {
			getlocalDateList.add(node);
		}
		return super.visit(node);
	}

}
