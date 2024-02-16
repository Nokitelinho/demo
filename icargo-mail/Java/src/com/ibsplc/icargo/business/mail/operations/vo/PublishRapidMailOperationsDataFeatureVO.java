package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class PublishRapidMailOperationsDataFeatureVO extends AbstractVO {

	private static final long serialVersionUID = 1L;
	private MailOperationDataFilterVO mailOperationDataFilterVO;
	
	public MailOperationDataFilterVO getMailOperationDataFilterVO() {
		return mailOperationDataFilterVO;
	}
	public void setMailOperationDataFilterVO(MailOperationDataFilterVO mailOperationDataFilterVO) {
		this.mailOperationDataFilterVO = mailOperationDataFilterVO;
	}
	
	


}
