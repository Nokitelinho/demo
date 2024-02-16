/*
 * OperationsFltHandlingProxy.java Created on Jul 8, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.proxy;


import java.util.Collection;

import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalULDAuditFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalULDAuditVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalULDVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.UldManifestVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-1763
 *
 */
@Module("operations")
@SubModule("flthandling")

public class OperationsFltHandlingProxy extends ProductProxy {
	private Log log = LogFactory.getLogger("ULD");
	
	/**
	 * 
	 * A-1950
	 * @param uldValidationVOs
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public void checkULDInOperartions(Collection<ULDValidationVO> uldValidationVOs)
	throws SystemException , ProxyException{
		log.entering("OperationsFltHandlingProxy", "checkULDInOperartions");
		despatchRequest("checkULDInOperartions",uldValidationVOs);
	}
	
	/**
	 * 
	 * A-3278
	 * For CR QF1022 on 18Jun08
	 * @param uldValidationVOs
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Page<OperationalULDAuditVO> findOprULDAuditDetails(OperationalULDAuditFilterVO
			operationalULDAuditFilterVO) throws SystemException ,ProxyException{
		return despatchRequest("findOprULDAuditDetails",operationalULDAuditFilterVO);
		
	}
	/**
	 * 
	 * 	Method		:	OperationsFltHandlingProxy.cargoFlightStatusImport
	 *	Added by 	:	A-6991 on 04-May-2017
	 * 	Used for 	:   ICRD-77772
	 *	Parameters	:	@param fltVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	boolean
	 */
	public boolean cargoFlightStatus(OperationalFlightVO fltVO)throws SystemException, ProxyException{
 		boolean flightStatus=false;
 		try{
 			flightStatus = despatchRequest("cargoFlightStatus", fltVO);
 		}catch(ProxyException proxyException){
 			log.log(Log.FINE,"ProxyException-->OperationsFltHandlingProxy.updateCargoFigures", 
 					proxyException.getMessage());
 		}
 		return flightStatus;
 	}	
	/**
	 * 
	 * 	Method		:	OperationsFltHandlingProxy.cargoFlightStatusImport
	 *	Added by 	:	A-6991 on 04-May-2017
	 * 	Used for 	:   ICRD-77772
	 *	Parameters	:	@param fltVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	boolean
	 */
	public boolean cargoFlightStatusImport(OperationalFlightVO fltVO)throws SystemException, ProxyException{
 		boolean flightStatus=false;
 		try{
 			flightStatus = despatchRequest("cargoFlightStatusImport", fltVO);
 		}catch(ProxyException proxyException){
 			log.log(Log.FINE,"ProxyException-->OperationsFltHandlingProxy.updateCargoFigures", 
 					proxyException.getMessage());
 		}
 		return flightStatus;
 	}
	/**
	 * 
	 * 	Method		:	OperationsFltHandlingProxy.findUldsForFlight
	 *	Added by 	:	A-6991 on 17-Nov-2017
	 * 	Used for 	:   ICRD-77772
	 *	Parameters	:	@param opfltvo
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	Collection<OperationalULDVO>
	 */
	public Collection<OperationalULDVO> findUldsForFlight(OperationalFlightVO opfltvo) throws SystemException, ProxyException{
		Collection<OperationalULDVO> operationalUlds = null;
		try {
			operationalUlds = despatchRequest("findUldsForFlight", opfltvo);
		} catch (ProxyException proxyException) {
						log.log(Log.FINE,"ProxyException-->OperationsFltHandlingProxy.findUldsForFlight", 
 					proxyException.getMessage());
		}
		return operationalUlds;
	}
	
	 /**
	  * 
	  * Method		:	OperationsFltHandlingProxy.findManifestedUlds
	  *	Added by 	:	A-7359 on 28-Aug-2017
	  * Used for 	:   ICRD-192413
	  *	Parameters	:	@param operationalFlightVO
	  *	Parameters	:	@return
	  *	Parameters	:	@throws SystemException
	  *	Parameters	:	@throws ProxyException 
	  *	Return type	: 	Collection<UldManifestVO>
	  */
	public Collection<UldManifestVO> findManifestedUlds(OperationalFlightVO operationalFlightVO) 
		    throws SystemException , ProxyException
		{ 
			log.log(Log.FINE,"OperationsFlthandlingProxy , findManifestedUlds");
			return despatchRequest("findManifestedUlds",operationalFlightVO); 
	}
}