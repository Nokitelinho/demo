package com.ibsplc.icargo.business.mail.operations.feature.savemailbaghistorynotes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.MailHistoryRemarks;
import com.ibsplc.icargo.business.mail.operations.MailHistoryRemarksPK;
import com.ibsplc.icargo.business.mail.operations.feature.savemailbaghistorynotes.persistors.SaveMailbagHistoryPersistor;

import com.ibsplc.icargo.business.mail.operations.vo.MailHistoryRemarksVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.pojo.PojoGetSetTester;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;

public class SaveMailbagHistoryNotesTest extends AbstractFeatureTest{
	
	private SaveMailbagHistoryNotesFeature spy;
	private MailHistoryRemarksVO mailHistoryRemarksVO;
	private SaveMailbagHistoryPersistor saveMailbagHistoryPersistor;
	private MailHistoryRemarks mailHistoryRemarks;
	private MailHistoryRemarksPK mailHistoryRemarksPKSpy;
	@Override
	public void setup() throws Exception {
		spy = spy((SaveMailbagHistoryNotesFeature) ICargoSproutAdapter.getBean("mail.operations.savemailbagnotesfeature"));
		mailHistoryRemarksVO = setUpMailHistoryRemarksVO();
		EntityManagerMock.mockEntityManager();
		saveMailbagHistoryPersistor =mockBean(SaveMailbagHistoryNotesConstants.HISTORY_NOTES_PERSISTOR, SaveMailbagHistoryPersistor.class);
		mailHistoryRemarks = mock(MailHistoryRemarks.class);
		mailHistoryRemarksPKSpy = spy(new MailHistoryRemarksPK());
	}
	
	private MailHistoryRemarksVO setUpMailHistoryRemarksVO() {
		MailHistoryRemarksVO mailHistoryRemarksVOObj = new MailHistoryRemarksVO();
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		mailHistoryRemarksVOObj.setCompanyCode("LH");
		mailHistoryRemarksVOObj.setMailSequenceNumber(12);
		mailHistoryRemarksVOObj.setRemark("Remark");
		mailHistoryRemarksVOObj.setRemarkSerialNumber(1);
		mailHistoryRemarksVOObj.setUserName("ACF");
		mailHistoryRemarksVOObj.setRemarkDate(currentDate);
		return mailHistoryRemarksVOObj;
	}
	
	@Test
	public void saveMailHistoryRemarWhenInvoked() throws Exception {
		mailHistoryRemarksVO =setUpMailHistoryRemarksVO();
		doNothing().when(saveMailbagHistoryPersistor).persist(mailHistoryRemarksVO);
		spy.execute(mailHistoryRemarksVO);
		verify(spy, times(1)).perform(any(MailHistoryRemarksVO.class));
	}
	
	 @Test(expected=SystemException.class)
		public void saveMailHistoryRemark_ThrowsException() throws Exception {
			mailHistoryRemarksVO =setUpMailHistoryRemarksVO();
			doThrow(CreateException.class).when(PersistenceController.getEntityManager()).persist(any(MailHistoryRemarks.class));
			spy.execute(mailHistoryRemarksVO);

		}
	 @Test
	    public void verifyContainerHBADetailsGettersSetter() throws Exception{
	          assertTrue(new PojoGetSetTester().testGettersAndSetters(MailHistoryRemarks.class));
	    }
	 @Test
	    public void verifyFContainerHBADetailsAdditionalGettersSetter() throws Exception{
		 assertTrue(new PojoGetSetTester().testGettersAndSetters(MailHistoryRemarksPK.class));
	    }
	  @Test
			public void callingTheConstructorContainerHBADetailsPK() throws CreateException, SystemException {
				 new MailHistoryRemarksPK();
			}
	  @Test
	  public void ContainerHBADetails_isInvoked() {
		new MailHistoryRemarks();
	  }
	  @Test
		public void testPK() throws FinderException, SystemException{
			MailHistoryRemarksPK mailHistoryRemarksPK= new MailHistoryRemarksPK();
			mailHistoryRemarksPK.setCompanyCode("CC");
			mailHistoryRemarksPK.setSerialNumber(1);
			MailHistoryRemarksPK mailHistoryRemarksPK1= new MailHistoryRemarksPK();
			mailHistoryRemarksPK1.setCompanyCode("CC");
			mailHistoryRemarksPK1.setSerialNumber(1);
			mailHistoryRemarksPKSpy.setCompanyCode("CC");
			mailHistoryRemarksPKSpy.setSerialNumber(1);
			assertNotNull(mailHistoryRemarksPKSpy.toString());
			assertTrue(mailHistoryRemarksPK.equals(mailHistoryRemarksPK1));
			assertFalse(mailHistoryRemarksPK.equals(mailHistoryRemarksPKSpy));
			assertFalse(mailHistoryRemarksPK.equals(null));
		}

}
