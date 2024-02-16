/**
 * 
 */
package com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.validators;


import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.OfficeOfExchange;
import com.ibsplc.icargo.business.mail.operations.OfficeOfExchangePK;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.SavePAWBDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.preinvokers.SavePostalShipmentInvoker;
import com.ibsplc.icargo.business.mail.operations.vo.CarditPawbDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import org.junit.Test;

/**
 * @author A-10647
 *
 */
public class MandatoryFieldsInPawbValidatorTest extends AbstractFeatureTest {
	private MandatoryFieldsInPawbValidator validator;
	private static final String DESTN = "FRPART";
	private static final String ORIGIN = "AEDXBT";
	private static final String MSTDOCNUM = "12345678";
	private static final String AGENTCODE="HQMAIL"; 
	private MailController mAilcontroller;
	private MailTrackingDefaultsDAO dao;





	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		mAilcontroller= mockBean("mAilcontroller", MailController.class);
		dao = mock(MailTrackingDefaultsDAO.class);
		validator =  (MandatoryFieldsInPawbValidator) ICargoSproutAdapter
				.getBean(SavePAWBDetailsFeatureConstants.MANDATORY_FIELDS_IN_PAWB_VALIDATOR);

	}
	@Test(expected = MailTrackingBusinessException.class)
	public void mandtoryFieldsCheckValidtaor() throws FinderException, SystemException, BusinessException {
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange1.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setShipperCode("FRA");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(any(String.class), any(String.class));
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		validator.validate(carditVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void mandtoryFieldsCheckValidtaor_withoutAllFields() throws FinderException, SystemException, BusinessException {
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode(null);
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange1.setAirportCode(null);
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setShipperCode("FRA");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		doReturn(null).when(mAilcontroller).findAgentFromUpucode(any(String.class), any(String.class));
//		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getShipperCode());
		doReturn(null).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		validator.validate(carditVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void mandtoryFieldsCheckValidtaor_withoutOriginDestination() throws FinderException, SystemException, BusinessException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setAgentCode("HQMAIL");
		carditPawbDetailsVO.setConsigneeAgentCode("HQMAIL");
		carditPawbDetailsVO.setShipperCode("FRA");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		validator.validate(carditVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void mandtoryFieldsCheckValidtaor_withoutOriginDestinationAgent() throws FinderException, SystemException, BusinessException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setConsignmentOriginAirport(null);
		carditPawbDetailsVO.setConsignmentDestinationAirport(null);
		carditPawbDetailsVO.setShipperCode("FRA");
		carditPawbDetailsVO.setConsigneeAgentCode("HQMAIL");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		
		validator.validate(carditVO);
	}
	
	@Test(expected = MailTrackingBusinessException.class)
	public void mandtoryFieldsCheckValidtaor_withoutOriginDestinationConsignee() throws FinderException, SystemException, BusinessException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setShipperCode("FRA");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		carditPawbDetailsVO.setAgentCode("HQMAIL");
		validator.validate(carditVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void mandtoryFieldsCheckValidtaor_withoutOriginAgentConsignee() throws FinderException, SystemException, BusinessException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode(null);
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentDestinationAirport("DFW");
		carditPawbDetailsVO.setAgentCode("");
		carditPawbDetailsVO.setConsigneeAgentCode("");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		validator.validate(carditVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void mandtoryFieldsCheckValidtaor_withoutDestinationAgentConsignee() throws FinderException, SystemException, BusinessException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode(null);
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestination(null);
		carditPawbDetailsVO.setConsignmentOriginAirport("FRA");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		validator.validate(carditVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void mandtoryFieldsCheckValidtaor_withoutDestinationAgent() throws FinderException, SystemException, BusinessException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("456");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentOriginAirport("FRA");
		carditPawbDetailsVO.setShipperCode("134");
		carditPawbDetailsVO.setConsigneeAgentCode(AGENTCODE);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		doReturn("").when(mAilcontroller).findAgentFromUpucode(any(String.class), any(String.class));
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		validator.validate(carditVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void mandtoryFieldsCheckValidtaor_withoutDestinationConsignee() throws FinderException, SystemException, BusinessException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("456");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentOriginAirport("FRA");
		carditPawbDetailsVO.setShipperCode("134");
		carditPawbDetailsVO.setAgentCode(AGENTCODE);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		validator.validate(carditVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void mandtoryFieldsCheckValidtaor_withoutOriginAgent() throws FinderException, SystemException, BusinessException {
		
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("456");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestinationAirport("DFW");
		carditPawbDetailsVO.setShipperCode("134");
		carditPawbDetailsVO.setConsigneeAgentCode(AGENTCODE);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		doReturn("").when(mAilcontroller).findAgentFromUpucode(any(String.class), any(String.class));
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		validator.validate(carditVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void mandtoryFieldsCheckValidtaor_withoutOriginConsignee() throws FinderException, SystemException, BusinessException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("456");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestinationAirport("DFW");
		carditPawbDetailsVO.setShipperCode("134");
		carditPawbDetailsVO.setAgentCode(AGENTCODE);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		validator.validate(carditVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void mandtoryFieldsCheckValidtaor_withoutAgentConsignee() throws FinderException, SystemException, BusinessException {
		
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("456");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestinationAirport("DFW");
		carditPawbDetailsVO.setConsignmentOriginAirport("FRA");
		carditPawbDetailsVO.setShipperCode("134");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		validator.validate(carditVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void mandtoryFieldsCheckValidtaor_withoutOrigin() throws FinderException, SystemException, BusinessException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("456");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestinationAirport("DFW");
		carditPawbDetailsVO.setShipperCode("134");
		carditPawbDetailsVO.setAgentCode(AGENTCODE);
		carditPawbDetailsVO.setConsigneeAgentCode(AGENTCODE);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		validator.validate(carditVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void mandtoryFieldsCheckValidtaor_withoutDestination() throws FinderException, SystemException, BusinessException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("456");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentOriginAirport("FRA");
		carditPawbDetailsVO.setAgentCode(AGENTCODE);
		carditPawbDetailsVO.setConsigneeAgentCode(AGENTCODE);
		carditPawbDetailsVO.setShipperCode("134");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		validator.validate(carditVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void mandtoryFieldsCheckValidtaor_withoutAgent() throws FinderException, SystemException, BusinessException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("456");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestinationAirport("DFW");
		carditPawbDetailsVO.setConsignmentOriginAirport("FRA");
		carditPawbDetailsVO.setShipperCode("134");
		carditPawbDetailsVO.setConsigneeAgentCode(AGENTCODE);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		validator.validate(carditVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void mandtoryFieldsCheckValidtaor_withoutConsignee() throws FinderException, SystemException, BusinessException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("456");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestinationAirport("DFW");
		carditPawbDetailsVO.setConsignmentOriginAirport("FRA");
		carditPawbDetailsVO.setAgentCode(AGENTCODE);
		carditPawbDetailsVO.setShipperCode("134");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(any(String.class), any(String.class));
		doReturn("").when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		validator.validate(carditVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void validator_WithoutCarditPawbDetailVO() throws BusinessException, SystemException {
		CarditVO carditVO = new CarditVO();
		validator.validate(carditVO);
	}
}
