/*
 * OperationsFltHandlingProxy.java Created on Aug 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalULDWeighingDetailsVO;
import com.ibsplc.icargo.business.operations.shipment.vo.UldInFlightVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-1739
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Aug 13, 2007			A-1739		Created
 */

@Module("operations")
@SubModule("flthandling")
public class OperationsFltHandlingProxy extends ProductProxy {
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	
	
	 public void  saveOperationalULDsInFlight(
				Collection<UldInFlightVO> uldInFlightVOs)
		throws SystemException {
		 log.entering("OperationsFltHandlingProxy", "saveOperationalULDsInFlight");
		 log.log(Log.FINEST, " uldinflts for save ", uldInFlightVOs);
		try {
			despatchRequest("saveOperationalULDsInFlight", uldInFlightVOs);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(), e);
		}
		 log.exiting("OperationsFltHandlingProxy", "saveOperationalULDsInFlight");
	 }
	 
	 public UldInFlightVO validateOperationalULD(
		 UldInFlightVO uldInFlightVO) 
		 throws SystemException {
		 log.entering("OperationsFltHandlingProxy", "validateOperationalULD");		 
		 try {
			return despatchRequest("validateOperationalULD", uldInFlightVO);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(), e);
		}
	 }
	 
	 public void updateOperationalFlightStatus(MailArrivalVO mailArrivalVO)
	 	throws SystemException, CreateException, FinderException{
			 log.entering("OperationsFltHandlingProxy", "updateOperationalFlightStatus");	
			 OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
			 operationalFlightVO =  creatingOperationalFlightVO(operationalFlightVO,mailArrivalVO);
			 try {
				 despatchRequest("updateOperationalFlightStatus", operationalFlightVO);
			} catch (ProxyException e) {
				throw new SystemException(e.getMessage(), e);
			}
		 }
	 
	 
	 private OperationalFlightVO creatingOperationalFlightVO(OperationalFlightVO operationalFlightVO,MailArrivalVO mailArrivalVO){
		 		 
		 if(operationalFlightVO==null){
				operationalFlightVO = new OperationalFlightVO();
				operationalFlightVO.setCompanyCode(mailArrivalVO.getCompanyCode());
				operationalFlightVO.setAirportCode(mailArrivalVO.getAirportCode());
				operationalFlightVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
				operationalFlightVO.setFlightNumber(mailArrivalVO.getFlightNumber());
				operationalFlightVO.setFlightDate(mailArrivalVO.getArrivalDate());
				operationalFlightVO.setFlightSequenceNumber(mailArrivalVO.getFlightSequenceNumber());
				operationalFlightVO.setCarrierId(mailArrivalVO.getCarrierId());
				operationalFlightVO.setLegSerialNumber(mailArrivalVO.getLegSerialNumber());
				operationalFlightVO.setDirection(OperationalFlightVO.INBOUND);
log.log(Log.FINE, "OperationalFlightVO --------->",
							operationalFlightVO);
		}
		
		 
		 
		return operationalFlightVO;
	 }
	 public OperationalULDWeighingDetailsVO saveULDWeighingDetails(OperationalULDWeighingDetailsVO operationalULDWeighingDetailsVO) throws SystemException {
		 OperationalULDWeighingDetailsVO operationalULDWeighingDetailsVOResult =null;
		 try {
			  operationalULDWeighingDetailsVOResult =despatchRequest("saveULDWeighingDetails", operationalULDWeighingDetailsVO);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(), e);
		}
		 return operationalULDWeighingDetailsVOResult;
	 }
	 
}
