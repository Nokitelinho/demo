package com.ibsplc.icargo.business.mail.operations.event.evaluator;

import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultProductProxy;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.DWSMasterVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class MailActualWeightSyncFromDWSEvaluatorTest extends AbstractFeatureTest {
	MailActualWeightSyncFromDWSEvaluator mailActualWeightSyncFromDWSEvaluator;
	SharedDefaultProductProxy sharedDefaultsProxy ;
	DWSMasterVO dWSMasterVO;
	HashMap<String, String> systemParameterMap;
	String sysparValue;
	@Override
	public void setup() throws Exception {
		mailActualWeightSyncFromDWSEvaluator = spy(MailActualWeightSyncFromDWSEvaluator.class);
		sharedDefaultsProxy=mock(SharedDefaultProductProxy.class);
		sharedDefaultsProxy=mockProxy(SharedDefaultProductProxy.class);
		systemParameterMap = new HashMap<String, String>();
		sysparValue = null;
	}
	
	@Test
	public void evaluate_N() throws SystemException, ProxyException {
		dWSMasterVO = new DWSMasterVO();
		//systemParameterMap = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		mailActualWeightSyncFromDWSEvaluator.evaluate(dWSMasterVO);
	}
	
	@Test
	public void evaluate_N_exception() throws SystemException, ProxyException {
		dWSMasterVO = new DWSMasterVO();
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doThrow(new SystemException("error",
				"error")).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		mailActualWeightSyncFromDWSEvaluator.evaluate(dWSMasterVO);
	}
	
	@Test
	public void evaluate_Y() throws SystemException, ProxyException {
		dWSMasterVO = new DWSMasterVO();
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add("mail.operations.modeofmailsyncfordws");
		systemParameterMap.put("mail.operations.modeofmailsyncfordws", "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		sysparValue = systemParameterMap.get("mail.operations.modeofmailsyncfordws");
		mailActualWeightSyncFromDWSEvaluator.evaluate(dWSMasterVO);
	}
	
	@Test
	public void evaluate_N_Proxyexception() throws SystemException, ProxyException {
		dWSMasterVO = new DWSMasterVO();
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doThrow(new ProxyException(new SystemException("error","error"))).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		mailActualWeightSyncFromDWSEvaluator.evaluate(dWSMasterVO);
	}

}
