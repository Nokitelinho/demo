package com.ibsplc.icargo.business.mail.operations.feature.stampresdit.validators;

import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.ResditController;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtil;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

public class StampResditValidatorTest extends AbstractFeatureTest {

	private MailResditVO mailResditVO;
	private StampResditValidator stampResditValidator;
	private ResditController resditController;

	@Override
	public void setup() throws Exception {
		mailResditVO = setUpMailResditVO();
		stampResditValidator  = spy(StampResditValidator.class);
		resditController = mockBean("resditController", ResditController.class);
	}

	private MailResditVO setUpMailResditVO() {
		MailResditVO mailResditVO = new MailResditVO();
		mailResditVO.setCompanyCode(getCompanyCode());
		mailResditVO.setMailId("GBLHRADEFRAAACA12000200002000");
		mailResditVO.setMailSequenceNumber(1000);
		mailResditVO.setEventAirport("FRA");
		mailResditVO.setEventCode("49");
		mailResditVO.setCarrierId(1001);
		mailResditVO.setFlightNumber("1234");
		mailResditVO.setFlightSequenceNumber(1234);
		mailResditVO.setSegmentSerialNumber(1);
		mailResditVO.setPaOrCarrierCode("AV");
		mailResditVO.setResditSentFlag(MailConstantsVO.FLAG_NO);
		mailResditVO.setProcessedStatus(MailConstantsVO.FLAG_NO);
		mailResditVO.setUldNumber("AKE1234AV");
		mailResditVO.setEventDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		mailResditVO.setMailboxID("US101");
		return mailResditVO;
	}
	
	@Test(expected = BusinessException.class)
	public void shouldThrowBusinessException_WhenSystemParameterIsNull() throws Exception{
		doReturn(null).when(ParameterUtil.getInstance()).findSystemParameterByCodes(anyCollectionOf(String.class));
		stampResditValidator.validate(mailResditVO);
	}	
	@Test
	public void shouldSkipValidation_WhenPrecheckEnabledIsNull() throws Exception{
		Map<String, String> parameterMap= new HashMap<>();
		parameterMap.put(MailConstantsVO.RESDIT_STAMPING_PRECHECK_ENABLED,null);
		stampResditValidator.validate(mailResditVO);
	}		
	@Test
	public void shouldSkipValidation_WhenStampingIsEnabled() throws Exception{
		Map<String, String> parameterMap= new HashMap<>();
		parameterMap.put(MailConstantsVO.RESDIT_STAMPING_PRECHECK_ENABLED, MailConstantsVO.FLAG_YES);
		doReturn(parameterMap).when(ParameterUtil.getInstance()).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(true).when(resditController).canStampResdits(mailResditVO);
		stampResditValidator.validate(mailResditVO);
	}
	@Test(expected = BusinessException.class)
	public void shouldThrowBusinessException_WhenStampResditIsDisabled() throws Exception{
		Map<String, String> parameterMap= new HashMap<>();
		parameterMap.put(MailConstantsVO.RESDIT_STAMPING_PRECHECK_ENABLED, MailConstantsVO.FLAG_YES);
		doReturn(parameterMap).when(ParameterUtil.getInstance()).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(false).when(resditController).canStampResdits(mailResditVO);
		stampResditValidator.validate(mailResditVO);
	}	
	@Test()
	public void shouldSkipValidation_WhenPrecheckIsDisabled() throws Exception{
		Map<String, String> parameterMap= new HashMap<>();
		parameterMap.put(MailConstantsVO.RESDIT_STAMPING_PRECHECK_ENABLED, MailConstantsVO.FLAG_NO);
		doReturn(parameterMap).when(ParameterUtil.getInstance()).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(true).when(resditController).canStampResdits(mailResditVO);
		stampResditValidator.validate(mailResditVO);
	}		
}
