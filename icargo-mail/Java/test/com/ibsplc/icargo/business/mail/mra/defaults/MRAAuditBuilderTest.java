package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.Test;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ibsplc.icargo.business.mail.mra.builder.MRAAuditBuilder;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MLDMasterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;




public class MRAAuditBuilderTest  extends AbstractFeatureTest{

   private MRAAuditBuilder mraauditBuilder;
	
	@Override
	public void setup() throws Exception {
		mraauditBuilder = spy(new MRAAuditBuilder());
	}
	
	@Test
	public void changeEnddateTest() throws SystemException{
	Collection<BillingLineVO> billingLineVos = new ArrayList<BillingLineVO>();
	BillingLineVO billingLineVO =new BillingLineVO();
	billingLineVO.setBillingMatrixId("test");
	billingLineVO.setCompanyCode("AV");
	billingLineVos.add(billingLineVO);
	String changedate = "date";
	mraauditBuilder.changeEnddate(billingLineVos, changedate);
	}
	
	@Test
	public void changeBillingMatrixStatusUpdateTest() throws SystemException, ProxyException{
	
	BillingMatrixFilterVO billingMatrixFilterVO=new BillingMatrixFilterVO();
	billingMatrixFilterVO.setBillingMatrixId("test");
	billingMatrixFilterVO.setCompanyCode("AV");
	String statusandTranx="A";
	mraauditBuilder.changeBillingMatrixStatusUpdate(billingMatrixFilterVO, statusandTranx);
	}
	
	

	@Test
	public void changeBillingMatrixStatusUpdateOInTest() throws SystemException, ProxyException{
	
	BillingMatrixFilterVO billingMatrixFilterVO=new BillingMatrixFilterVO();
	billingMatrixFilterVO.setBillingMatrixId("test");
	billingMatrixFilterVO.setCompanyCode("AV");
	String statusandTranx="I";
	mraauditBuilder.changeBillingMatrixStatusUpdate(billingMatrixFilterVO, statusandTranx);
	}
	
	@Test
	public void changeBillingMatrixStatusUpdateCaTest() throws SystemException, ProxyException{
	
	BillingMatrixFilterVO billingMatrixFilterVO=new BillingMatrixFilterVO();
	billingMatrixFilterVO.setBillingMatrixId("test");
	billingMatrixFilterVO.setCompanyCode("AV");
	String statusandTranx="C";
	mraauditBuilder.changeBillingMatrixStatusUpdate(billingMatrixFilterVO, statusandTranx);
	}
	
	@Test
	public void changeBillingMatrixStatusUpdateExTest() throws SystemException, ProxyException{
	
	BillingMatrixFilterVO billingMatrixFilterVO=new BillingMatrixFilterVO();
	billingMatrixFilterVO.setBillingMatrixId("test");
	billingMatrixFilterVO.setCompanyCode("AV");
	String statusandTranx="E";
	mraauditBuilder.changeBillingMatrixStatusUpdate(billingMatrixFilterVO, statusandTranx);
	}
	
	@Test
	public void changeBillingMatrixStatusUpdateOthTest() throws SystemException, ProxyException{
	
	BillingMatrixFilterVO billingMatrixFilterVO=new BillingMatrixFilterVO();
	billingMatrixFilterVO.setBillingMatrixId("test");
	billingMatrixFilterVO.setCompanyCode("AV");
	String statusandTranx="N";
	mraauditBuilder.changeBillingMatrixStatusUpdate(billingMatrixFilterVO, statusandTranx);
	}
}
	