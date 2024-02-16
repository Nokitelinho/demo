package com.ibsplc.icargo.business.mail.operations;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Matchers.any;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;

public class ContainerAuditTest extends AbstractFeatureTest {
	
	private ContainerAudit containerAuditSpy;
	
	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		ContainerAuditVO containerAuditVO = new ContainerAuditVO(
				ContainerVO.MODULE, ContainerVO.SUBMODULE, ContainerVO.ENTITY);
		containerAuditVO.setCompanyCode(getCompanyCode());
		containerAuditSpy = new ContainerAudit(containerAuditVO);
	}

	@Test(expected=SystemException.class)
	public void ContainerAudit_ExceptionHandling() throws SystemException, CreateException{
		ContainerAuditVO containerAuditVO = new ContainerAuditVO(
				ContainerVO.MODULE, ContainerVO.SUBMODULE, ContainerVO.ENTITY);
		CreateException createException= new CreateException();
		doThrow(createException).when(PersistenceController.getEntityManager()).persist(any(ContainerAudit.class));
		containerAuditSpy = new ContainerAudit(containerAuditVO);
	}
}
