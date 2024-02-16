/*
 * MailtrackingDefaultsProxy.java Created on 18-DEC-08
 *
 * Copyright 2008 IBS Software Services (P) Ltd.
 * All Rights Reserved. This software is the
 * proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-3227 RENO K ABRAHAM
 *
 */
@Module("mail")
@SubModule("operations")
public class MailtrackingDefaultsProxy extends ProductProxy {

	private static final String MODULE_NAME = "mail.operations";

	private static final String CLASS_NAME = "MailtrackingDefaultsProxy";

	private Log log = LogFactory.getLogger(MODULE_NAME);

	/**
	 * @author A-3227 RENO K ABRAHAM,  Added ON 18-DEC-08
	 * @param companyCode
	 * @param currentSerialNumber
	 * @throws SystemException
	 */
	public void clearDSNAcceptanceTemp(String companyCode, int currentSerialNumber)
			throws SystemException {
		log.entering(CLASS_NAME, "clearDSNAcceptanceTemp");
		log.log(Log.FINE,"clearDSNAcceptanceTemp--Operating Async");
		try {
			dispatchAsyncRequest("clearDSNAcceptanceTemp",false,companyCode,currentSerialNumber);
		} catch (ProxyException e) {
			e.getMessage();
		}
		log.exiting(CLASS_NAME, "clearDSNAcceptanceTemp");
	}
	
	
	public void saveMailDetailsFromJob(Collection<MailUploadVO> mailBagVOs, String scanningPort)
	throws SystemException{
		log.entering(CLASS_NAME, "saveMailDetailsFromJob");	
		try{
			despatchRequest(true,"saveMailDetailsFromJob",mailBagVOs,scanningPort);     
		}catch (ProxyException e) {
			e.getMessage();
		}
		log.exiting(CLASS_NAME, "saveMailDetailsFromJob");      
	}
	public void saveAllValidMailBags(Collection<ScannedMailDetailsVO> validScannedMailVOs)
	throws SystemException {
		log.entering(CLASS_NAME, "saveAllValidMailBags");
		try {
			despatchRequest("saveAllValidMailBags", validScannedMailVOs);
		} catch(ProxyException ex) {
			throw new SystemException(ex.getMessage(),ex);
		}
		log.exiting(CLASS_NAME, "saveAllValidMailBags");
		
	}
	
	/**
	 * 
	 * 	Method		:	MailtrackingDefaultsProxy.findMailAWBDetails
	 *	Added by 	:	A-6991 on 03-May-2017
	 * 	Used for 	:
	 *	Parameters	:	@param operationalFlightVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	MailManifestVO
	 */
	public MailManifestVO findMailAWBDetails(OperationalFlightVO operationalFlightVO)
			 throws SystemException,ProxyException{
				 return despatchRequest("findMailAWBDetails",operationalFlightVO);
			 }
	
	/**
	 * 
	 * 	Method		:	MailtrackingDefaultsProxy.undoArriveContainer
	 *	Added by 	:	Added by A-7794 as part of ICRD-224613 on 17-October-2017
	 *	Parameters	:	@param mailArrivalVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws MailTrackingBusinessException 
	 *	Parameters	:	@throws ProxyException 
	 */
	public void undoArriveContainer(MailArrivalVO mailArrivalVO) throws SystemException,MailTrackingBusinessException,ProxyException{
		 despatchRequest("undoArriveContainer",mailArrivalVO);
			 }
	
	public void closeInboundFlightAfterULDAcquitalForProxy(OperationalFlightVO operationalFlightVO)
			throws SystemException,MailTrackingBusinessException,ProxyException{
		despatchRequest("closeInboundFlightAfterULDAcquital",operationalFlightVO);
	}
			public void releasingMailsForULDAcquittance(MailArrivalVO mailArrivalVO,
					OperationalFlightVO operationalFlightVO)
					throws SystemException,MailTrackingBusinessException,ProxyException{
				despatchRequest("releasingMailsForULDAcquittance",mailArrivalVO,operationalFlightVO);
			}

			/**
			 * Added for ICRD-239331
			 * @param containerDetailsVO
			 * @return Collection<ContainerDetailsVO>
			 * @throws ProxyException
			 * @throws SystemException
			 */
			public Collection<ContainerDetailsVO> findMailbagsInContainer(
					Collection<ContainerDetailsVO> containerDetailsVO) throws ProxyException, SystemException {
				return despatchRequest("findMailbagsInContainer",containerDetailsVO);
			}
			/**
			 * @author A-8353
			 * @param containerDetailsVO
			 * @return Collection<ContainerDetailsVO>
			 * @throws ProxyException
			 * @throws SystemException
			 */
			public MailManifestVO findContainersInFlightForManifest(
					OperationalFlightVO operationalFlightVo) throws ProxyException, SystemException {
				return despatchRequest("findContainersInFlightForManifest",operationalFlightVo);
			}

	/**
	 * @author-U-1439
	 * for IASCB-37022
	 * @param resditEvents
	 * @throws ProxyException
	 * @throws SystemException
	 */
			public void buildResditProxy(
					Collection<ResditEventVO> resditEvents)throws ProxyException, SystemException {
				dispatchProductAsyncRequest("buildResditProxy",true,resditEvents);
			}

	}
