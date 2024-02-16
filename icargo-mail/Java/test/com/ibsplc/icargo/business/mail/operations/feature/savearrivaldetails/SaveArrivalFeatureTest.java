package com.ibsplc.icargo.business.mail.operations.feature.savearrivaldetails;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.ULDDefaultsProxyException;
import com.ibsplc.icargo.business.mail.operations.feature.savearrivaldetails.PerformSaveArrival;
import com.ibsplc.icargo.business.mail.operations.feature.savearrivaldetails.SaveArrivalFeature;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;

public class SaveArrivalFeatureTest  extends AbstractFeatureTest{
	
	private SaveArrivalFeature spy;
	private MailArrivalVO mailArrivalVO;
	private PerformSaveArrival performSaveArrival;
	
	
	
	@Override
	public void setup() throws Exception {
		spy = spy((SaveArrivalFeature) ICargoSproutAdapter.getBean("mail.operations.savearrivalfeature"));
		performSaveArrival = mockBean("mail.operations.performsavearrival",PerformSaveArrival.class);
		mailArrivalVO = setUpMailArrivalVO();
	}
		
		private MailArrivalVO setUpMailArrivalVO() {
			MailArrivalVO mailArrivalVO = new MailArrivalVO();
			mailArrivalVO.setCompanyCode(getCompanyCode());
			mailArrivalVO.setFlightNumber("1234");
			mailArrivalVO.setAirportCode("FRA");
			mailArrivalVO.setFlightCarrierCode(getCompanyCode());
			mailArrivalVO.setFlightSequenceNumber(1);
			mailArrivalVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
			return mailArrivalVO;
		}
		@Test()
		public void performSaveArrival_When_Invoked() throws Exception {
			spy.execute(mailArrivalVO);
			verify(performSaveArrival, times(1)).perform(mailArrivalVO);
		}
		
		
		@Test(expected = MailTrackingBusinessException.class)
		public void shouldThrowBusinessException_When_PerformSaveArrival() throws Exception {
			doThrow(ULDDefaultsProxyException.class).when(performSaveArrival).perform(mailArrivalVO);
			spy.execute(mailArrivalVO);
		}
		

	}




	


	



