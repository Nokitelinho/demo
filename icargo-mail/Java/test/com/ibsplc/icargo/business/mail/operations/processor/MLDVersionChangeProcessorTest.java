package com.ibsplc.icargo.business.mail.operations.processor;

import static org.mockito.Mockito.spy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.ibsplc.icargo.framework.eai.base.vo.CommunicationVO;
import com.ibsplc.icargo.framework.eai.spi.listener.CommunicationException;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

public class MLDVersionChangeProcessorTest extends AbstractFeatureTest {

	private MLDVersionChangeProcessor spy;

	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		spy = spy(new MLDVersionChangeProcessor());

	}

	@Test
	public void processTest() throws SystemException, PersistenceException, IOException, CommunicationException {

		Properties props = new Properties();
		props.setProperty("message-version1", "MLD1");
		props.setProperty("message-version2", "MLD0");

		MLDVersionChangeProcessor ref = new MLDVersionChangeProcessor(props);
		List<CommunicationVO> communication = new ArrayList<>();

		CommunicationVO com = new CommunicationVO();
		com.setMessageString("MLDdata");
		communication.add(com);

		ref.process(communication);
	}

	@Test
	public void processEmptyTest() throws SystemException, PersistenceException, IOException, CommunicationException {

		List<CommunicationVO> communication = null;
		spy.process(communication);
	}

}
