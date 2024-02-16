/**
 * MailbagTest.java Created on Aug 21, 2021
 * @author A-8353
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import org.junit.Test;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstance;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstanceMock;

public class ContainerTest extends AbstractFeatureTest {
	private Container containerSpy;
	private KeyUtilInstance keyUtils;
	private KeyUtilInstance keyUtilsInstance;
	@Override
	public void setup() throws Exception {
		containerSpy = spy(new Container());
		KeyUtilInstanceMock.mockKeyUtilInstance();
		keyUtils = KeyUtilInstance.getInstance();
		keyUtilsInstance =  KeyUtilInstance.getInstance();
	}
	@Test
	public void  populateAttributesOfContainer() throws SystemException{
		ContainerVO containerVO= new ContainerVO();
		containerVO.setAssignedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		containerVO.setActualWeight(new Measure(UnitConstants.MAIL_WGT,0));
		containerVO.setPaBuiltFlag("");
		containerVO.setAssignedPort("CDG");
		containerVO.setMailbagPresent(true);
		containerVO.setUldReferenceNo(0);
		containerVO.setCompanyCode("IBS");
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		containerSpy.update(containerVO);   
		
	}
	@Test
	public void  populateAttributesOfContainerFirstAsgDateNotNull() throws SystemException{
		ContainerVO containerVO= new ContainerVO();
		containerVO.setAssignedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		containerVO.setFirstAssignDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		containerVO.setTransferFlag(MailConstantsVO.FLAG_YES);
		containerVO.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
		containerVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
		containerVO.setOffloadFlag(MailConstantsVO.FLAG_YES);
		containerVO.setArrivedStatus(MailConstantsVO.FLAG_YES);
		containerVO.setPaBuiltOpenedFlag(MailConstantsVO.FLAG_YES);
		containerVO.setIntact(MailConstantsVO.FLAG_YES);
		containerVO.setTransitFlag(MailConstantsVO.FLAG_YES);
		containerVO.setAssignedPort("CDG");
		containerVO.setMailbagPresent(true);
		containerVO.setUldReferenceNo(0);
		containerVO.setCompanyCode("IBS");
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		containerSpy.update(containerVO);   
		
	}
	@Test
	public void  populateAttributesOfContainerAsgDateAndFirstAsgDateNull() throws SystemException{
		ContainerVO containerVO= new ContainerVO();
		containerVO.setActualWeight(new Measure(UnitConstants.MAIL_WGT,0));
		containerVO.setAssignedPort("CDG");
		containerVO.setMailbagPresent(true);
		containerVO.setUldReferenceNo(0);
		containerVO.setCompanyCode("IBS");
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		containerSpy.update(containerVO);   
		
	}
	@Test
	public void  populateAttributesOfContainerFirstAsgDateStampedNotNull() throws SystemException{
		ContainerVO containerVO= new ContainerVO();
		containerVO.setActualWeight(new Measure(UnitConstants.MAIL_WGT,0));
		containerVO.setActWgtSta("F");
		containerVO.setAssignedPort("CDG");
		containerVO.setMailbagPresent(true);
		doReturn(new LocalDate(LocalDate.NO_STATION,Location.NONE,true).toCalendar()).when(containerSpy).getFirstMalbagAsgDat();
		containerVO.setUldReferenceNo(0);
		containerVO.setCompanyCode("IBS");
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		containerSpy.update(containerVO);   
		
	}
	@Test
	public void  populateAttributesOfContainerFirstAsgDateStampedNotNullAndMailNotPresent() throws SystemException{
		ContainerVO containerVO= new ContainerVO();
		containerVO.setActualWeight(new Measure(UnitConstants.MAIL_WGT,0));
		containerVO.setActWgtSta("F");
		containerVO.setAssignedPort("CDG");
		containerVO.setMailbagPresent(false);
		doReturn(new LocalDate(LocalDate.NO_STATION,Location.NONE,true).toCalendar()).when(containerSpy).getFirstMalbagAsgDat();
		
		containerVO.setUldReferenceNo(0);
		containerVO.setCompanyCode("IBS");
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		containerSpy.update(containerVO);   
		
	}
	@Test
	public void  populateAttributesOfContainerFirstAsgDateStampedNotNul() throws SystemException{
		ContainerVO containerVO= new ContainerVO();
		containerVO.setActualWeight(new Measure(UnitConstants.MAIL_WGT,0));
		containerVO.setActWgtSta("F");
		containerVO.setAssignedPort("CDG");
		containerVO.setMailbagPresent(false);
		containerVO.setUldReferenceNo(0);
		containerVO.setCompanyCode("IBS");
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		containerSpy.update(containerVO);   
		
	}
	
	@Test
	public void  populateAttributesOfContainerNull() throws SystemException{
		ContainerVO containerVO= new ContainerVO();
		containerVO.setAssignedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		containerVO.setActualWeight(new Measure(UnitConstants.MAIL_WGT,0));
		containerVO.setActWgtSta("F");
		containerVO.setPaBuiltFlag("");
		containerVO.setAssignedPort("CDG");
		containerVO.setMailbagPresent(true);
		containerVO.setUldReferenceNo(1);
		containerVO.setCompanyCode("IBS");
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		containerSpy.update(containerVO);   
		
	}
	
	@Test
	public void  getActWgtSta_Test() throws SystemException{
		containerSpy.getActWgtSta();   
		
	}
}
