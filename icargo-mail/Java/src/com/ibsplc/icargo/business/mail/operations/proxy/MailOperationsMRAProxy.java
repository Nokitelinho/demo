/*
 * MailOperationsMRAProxy.java Created on DEC 12, 2017
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;

 
import java.util.Collection;
 
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailScanDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

 
/*
*
* Revision History
* Version	 	Date      		    Author			Description
* 0.1			Dec 12, 2017 	  	A-5526			Initial draft
*
*/
@Module("mail")
@SubModule("mra")
public class MailOperationsMRAProxy extends ProductProxy{
	private Log log = LogFactory.getLogger("MailtrackingMRAProxy");

     

	public void importMRAData(Collection<RateAuditVO> rateAuditVOs) throws SystemException,ProxyException{
		try {
			dispatchAsyncRequest("importMRAData", false,rateAuditVOs);
		} catch(ProxyException ex) {
			throw new SystemException(ex.getMessage(),ex);
		}
		
		
	}
	/**
	 * 
	 * 	Method		:	MailOperationsMRAProxy.createJobforFlightRevenueInterface
	 *	Added by 	:	a-8061 on 28-Jun-2018
	 * 	Used for 	:	ICRD-245594
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	void
	 */
	public void createJobforFlightRevenueInterface() throws SystemException,ProxyException{
		log.entering("MessageBuilder", "createJobforFlightRevenueInterface");
		
		LogonAttributes logonAttributes = (LogonAttributes) ContextUtils
				.getSecurityContext().getLogonAttributesVO();
		
		try {
			dispatchAsyncRequest("createJobforFlightRevenueInterface", false,logonAttributes.getCompanyCode());
		} catch (ProxyException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
		
		log.exiting("MessageBuilder", "createJobforFlightRevenueInterface");

	}
	/**
	 * 
	 * 	Method		:	MailOperationsMRAProxy.saveVoidedInterfaceDetails
	 *	Added by 	:	A-8061 on 15-Oct-2019
	 * 	Used for 	:	ICRD-336689
	 *	Parameters	:	@param VOs
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	void
	 */
	public void saveVoidedInterfaceDetails(Collection<DocumentBillingDetailsVO> VOs)throws SystemException,ProxyException{
		try {
			dispatchAsyncRequest("saveVoidedInterfaceDetails", false,VOs);
		} catch (ProxyException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
		

	}
	/***
	 * @author A-7794
	 * @param mailScanDetailVO
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public void importResditDataToMRA(MailScanDetailVO mailScanDetailVO)throws SystemException,ProxyException{
		try {
			dispatchAsyncRequest("importResditDataToMRA", false,mailScanDetailVO);
		} catch(ProxyException ex) {
			throw new SystemException(ex.getMessage(),ex);
		}
	}
	public void recalculateDisincentiveData(Collection<RateAuditDetailsVO> rateAuditVos)throws SystemException,ProxyException{
		try {
			 despatchRequest("recalculateDisincentiveData", new Object[] { rateAuditVos });
		} catch(ProxyException ex) {
			throw new SystemException(ex.getMessage(),ex);
		}
	}

	public void reImportPABuiltMailbagsToMRA(MailbagVO mailbagVO) throws SystemException {
		try {
			dispatchAsyncRequest("reImportPABuiltMailbagsToMRA", false, mailbagVO);
		} catch (ProxyException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}

	public Collection<MailbagVO> findMailbagsForPABuiltUpdate(MailbagVO mailbagVO)
			throws SystemException {
		try {
			return despatchRequest("findMailbagsForPABuiltUpdate", mailbagVO);
		} catch (ProxyException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}
	public void importMailProvisionalRateData(Collection<RateAuditVO> provisionalRateAuditVOs) throws SystemException,ProxyException{
		try {
			dispatchAsyncRequest("importMailProvisionalRateData", false,provisionalRateAuditVOs);
		} catch(ProxyException ex) {
			throw new SystemException(ex.getMessage(),ex);
		}
		
		
	}
}
