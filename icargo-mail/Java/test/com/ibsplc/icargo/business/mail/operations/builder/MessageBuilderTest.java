package com.ibsplc.icargo.business.mail.operations.builder;

import java.util.ArrayList;
import java.util.Collection;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.Test;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MLDMasterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;




public class MessageBuilderTest  extends AbstractFeatureTest{

   private MessageBuilder messageBuilder;
	
	@Override
	public void setup() throws Exception {
		messageBuilder = spy(new MessageBuilder());
	}
	
	@Test
	public void flagMLDForMailReassignOperations_bulk() throws SystemException{
	ContainerVO toContainerVO= new ContainerVO();
	Collection<MailbagVO> mailbagVOs=null;
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO masterVO=new MLDMasterVO();
	masterVO.setCompanyCode("LH");
	mldMasterVOs.add(masterVO);
	String mode="STG";
	toContainerVO.setCompanyCode("LH");
	toContainerVO.setType("B");
	messageBuilder.flagMLDForMailReassignOperations(mailbagVOs, toContainerVO, mode);
	}
	
	@Test
	public void flagMLDForMailReassignOperations_uld() throws SystemException{
	ContainerVO toContainerVO= new ContainerVO();
	Collection<MailbagVO> mailbagVOs=null;
	Collection<MLDMasterVO> mldMasterVOs=new ArrayList<>();
	MLDMasterVO masterVO=new MLDMasterVO();
	masterVO.setCompanyCode("LH");
	mldMasterVOs.add(masterVO);
	String mode="STG";
	toContainerVO.setCompanyCode("LH");
	toContainerVO.setType("U");
	messageBuilder.flagMLDForMailReassignOperations(mailbagVOs, toContainerVO, mode);
	}
}
	