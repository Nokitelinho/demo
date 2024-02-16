package com.ibsplc.neoicargo.mail.component.proxy;

import java.util.Collection;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.MailMRAAsyncEProxy;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import com.ibsplc.neoicargo.mail.vo.MailScanDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.MailMRAEProxy;

@Component
public class MailMRAProxy {

	@Autowired
	private MailMRAEProxy mailMRAEProxy;
	@Autowired
	private MailMRAAsyncEProxy mailMRAAsyncEProxy;

	public void importMRAData(Collection<RateAuditVO> rateAuditVOs) throws BusinessException {

		try {
			mailMRAAsyncEProxy.importMRAData(rateAuditVOs);
		}catch(ServiceException serviceException){
			throw new SystemException(serviceException.getMessage());
		}

	}

	public void importConsignmentDataToMra(ConsignmentDocumentVO consignmentDocumentVO)  {

		try {
			mailMRAAsyncEProxy.importConsignmentDataToMra(consignmentDocumentVO);
		}catch(ServiceException serviceException){
			throw new SystemException(serviceException.getMessage());
		}
	}

	/**
	 * Method		:	MailtrackingMRAProxy.isMailbagInMRA Added by 	:	A-5219 on 07-May-2020 Used for 	: Parameters	:	@param companyCode Parameters	:	@param mailSeq Parameters	:	@return Parameters	:	@throws SystemException Parameters	:	@throws ProxyException  Return type	: 	boolean
	 */
	public boolean isMailbagInMRA(String companyCode, long mailSeq) throws BusinessException {
		boolean isMailbagPresent = false;
		try {
			isMailbagPresent = mailMRAEProxy.isMailbagInMRA(companyCode, mailSeq);
		} catch (ServiceException se) {
			throw new BusinessException(se);
		}
		return isMailbagPresent;
	}

	public void importMailProvisionalRateData(Collection<RateAuditVO> provisionalRateAuditVOs)
			throws BusinessException {
		var mailMRAEProxy = ContextUtil.getInstance().getBean(MailMRAEProxy.class);
		try {
			mailMRAAsyncEProxy.importMailProvisionalRateData(provisionalRateAuditVOs);
		}catch(ServiceException serviceException){
			throw new SystemException(serviceException.getMessage());
		}
	}
	public void importResditDataToMRA(MailScanDetailVO mailScanDetailVO) throws BusinessException{
		try {
			mailMRAAsyncEProxy.importResditDataToMRA(mailScanDetailVO);
		}catch(ServiceException serviceException){
			throw new SystemException(serviceException.getMessage());
		}
	}
}
