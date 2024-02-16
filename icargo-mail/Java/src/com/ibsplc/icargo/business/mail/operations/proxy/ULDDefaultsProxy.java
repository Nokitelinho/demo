/*
 * ULDDefaultsProxy.java Created on Mar 8, 2007 
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.ULDDefaultsProxyException;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 * RevisionHistory
 * -------------------------------------------------------------------------
 * Version Date Author Description
 * ------------------------------------------------------------------------ 0.1
 * Mar 8, 2007 A-1883 Created
 */

@Module("uld")
@SubModule("defaults")
public class ULDDefaultsProxy extends ProductProxy {
	private Log log = LogFactory.getLogger("MAIL TRACKING");

	private static final String ULD_DOES_NOT_EXISTS = "uld.defaults.ulddoesnot.exists";

	private static final String ULD_IS_NOT_IN_AIRPORT = "uld.defaults.uldisnotinairport";

	private static final String ULD_IS_NOT_IN_THE_SYSTEM = "uld.defaults.uldisnotinthesystem";

	private static final String ULD_IS_NOT_OPERATIONAL = "uld.defaults.uldisnotoperational";

	private static final String ULD_DOES_NOT_EXISTS_MAIL = "mailtracking.defaults.warn.ulddoesnot.exists";

	private static final String ULD_IS_NOT_IN_AIRPORT_MAIL = "mailtracking.defaults.warn.uldisnotinairport";

	private static final String ULD_IS_NOT_IN_THE_SYSTEM_MAIL = "mailtracking.defaults.warn.uldisnotinthesystem";

	private static final String ULD_IS_NOT_OPERATIONAL_MAIL = "mailtracking.defaults.warn.uldisnotoperational";

	
	 /**
	  * ERROR_ULDISNOTINAIRPORT
	  */
	 private static final String ULD_DEFAULTS_ERROR_ULDISNOTINAIRPORT = "uld.defaults.error.uldisnotinairport";
   
	 /**
	  * WARNING_ULDISNOTINAIRPORT
	  */
	  private static final String ULD_DEFAULTS_WARNING_ULDISNOTINAIRPORT = "uld.defaults.warning.uldisnotinairport";

	 /**
	  * 
	  * ERROR_ULDISNOTINTHESYSTEM
	  * 
	  */
	  private static final String ULD_DEFAULTS_ERROR_ULDISNOTINTHESYSTEM = "uld.defaults.error.uldisnotinthesystem";

	 /**
	  * WARNING_ULDISNOTINTHESYSTEM
	  */
	  private static final String ULD_DEFAULTS_WARNING_ULDISNOTINTHESYSTEM = "uld.defaults.warning.uldisnotinthesystem";

	 /**
	  * ERROR_ULDISNOTOPERATIONAL
	  */
	  private static final String ULD_DEFAULTS_ERROR_ULDISNOTOPERATIONAL = "uld.defaults.error.uldisnotoperational";

	/**
	 * WARNING_ULDISNOTOPERATIONAL
	 */
	private static final String ULD_DEFAULTS_WARNING_ULDISNOTOPERATIONAL = "uld.defaults.warning.uldisnotoperational";

	/**
	 * ERROR_ULDISLOST
	 */
	private static final String ULD_DEFAULTS_ERROR_ULDISLOST = "uld.defaults.error.uldislost";

	/**
	 * WARNING_ULDISLOST
	 */
	private static final String ULD_DEFAULTS_WARNING_ULDISLOST = "uld.defaults.warning.uldislost";

	/**
	 * ERROR_ULDNOTINAIRLINESTOCK
	 */
	private static final String ULD_DEFAULTS_ERROR_ULDNOTINAIRLINESTOCK = "uld.defaults.error.uldnotinairlinestock";

	/**
	 * WARNING_ULDNOTINAIRLINESTOCK
	 */
	private static final String ULD_DEFAULTS_WARNING_ULDNOTINAIRLINESTOCK = "uld.defaults.warning.uldnotinairlinestock";


	/**
	 * ERROR_ULDISNOTINAIRPORT
	 */
	private static final String MAIL_DEFAULTS_ERROR_ULDISNOTINAIRPORT = "mail.defaults.error.uldisnotinairport";

	/**
	 * WARNING_ULDISNOTINAIRPORT
	 */
	private static final String MAIL_DEFAULTS_WARNING_ULDISNOTINAIRPORT = "mail.defaults.warning.uldisnotinairport";

	/**
	 * 
	 * ERROR_ULDISNOTINTHESYSTEM
	 * 
	 */
	private static final String MAIL_DEFAULTS_ERROR_ULDISNOTINTHESYSTEM = "mail.defaults.error.uldisnotinthesystem";

	/**
	 * WARNING_ULDISNOTINTHESYSTEM
	 */
	private static final String MAIL_DEFAULTS_WARNING_ULDISNOTINTHESYSTEM = "mail.defaults.warning.uldisnotinthesystem";

	/**
	 * ERROR_ULDISNOTOPERATIONAL
	 */
	private static final String MAIL_DEFAULTS_ERROR_ULDISNOTOPERATIONAL = "mail.defaults.error.uldisnotoperational";

	/**
	 * WARNING_ULDISNOTOPERATIONAL
	 */
	private static final String MAIL_DEFAULTS_WARNING_ULDISNOTOPERATIONAL = "mail.defaults.warning.uldisnotoperational";

	/**
	 * ERROR_ULDISLOST
	 */
	private static final String MAIL_DEFAULTS_ERROR_ULDISLOST = "mail.defaults.error.uldislost";

	/**
	 * WARNING_ULDISLOST
	 */
	private static final String MAIL_DEFAULTS_WARNING_ULDISLOST = "mail.defaults.warning.uldislost";

	/**
	 * ERROR_ULDNOTINAIRLINESTOCK
	 */
	private static final String MAIL_DEFAULTS_ERROR_ULDNOTINAIRLINESTOCK = "mail.defaults.error.uldnotinairlinestock";

	/**
	 * WARNING_ULDNOTINAIRLINESTOCK
	 */
	private static final String MAIL_DEFAULTS_WARNING_ULDNOTINAIRLINESTOCK = "mail.defaults.warning.uldnotinairlinestock";

	/**
	 * @param companyCode
	 * @param airportCode
	 * @param uldNumbers
	 * @return
	 * @throws SystemException
	 * @throws ULDDefaultsProxyException
	 */
	public void validateULDs(String companyCode, String airportCode,
			Collection<String> uldNumbers) throws SystemException,
			ULDDefaultsProxyException {
		log.entering("ULDDefaultsProxy", "validateULDs");
		try {
			despatchRequest("validateULDs", companyCode, airportCode,
					uldNumbers);
		} catch (ProxyException proxyException) {
			Collection<ErrorVO> errors = proxyException.getErrors();
			for (ErrorVO errorVO : errors) {
				if (ULD_DOES_NOT_EXISTS.equals(errorVO.getErrorCode())) {
					throw new ULDDefaultsProxyException(
							ULD_DOES_NOT_EXISTS_MAIL);
				} else if (ULD_IS_NOT_IN_AIRPORT.equals(errorVO.getErrorCode())) {
					throw new ULDDefaultsProxyException(
							ULD_IS_NOT_IN_AIRPORT_MAIL);
				} else if (ULD_IS_NOT_IN_THE_SYSTEM.equals(errorVO
						.getErrorCode())) {
					throw new ULDDefaultsProxyException(
							ULD_IS_NOT_IN_THE_SYSTEM_MAIL);
				} else if (ULD_IS_NOT_OPERATIONAL
						.equals(errorVO.getErrorCode())) {
					throw new ULDDefaultsProxyException(
							ULD_IS_NOT_OPERATIONAL_MAIL);
				} else if (ULD_DEFAULTS_ERROR_ULDISNOTINTHESYSTEM
						.equals(errorVO.getErrorCode())) {
					throw new ULDDefaultsProxyException(MAIL_DEFAULTS_ERROR_ULDISNOTINTHESYSTEM);
				} else if (ULD_DEFAULTS_WARNING_ULDISNOTINTHESYSTEM
						.equals(errorVO.getErrorCode())) {
					throw new ULDDefaultsProxyException(MAIL_DEFAULTS_WARNING_ULDISNOTINTHESYSTEM);
				} else if (ULD_DEFAULTS_ERROR_ULDISNOTINAIRPORT
						.equals(errorVO.getErrorCode())) {
					throw new ULDDefaultsProxyException(MAIL_DEFAULTS_ERROR_ULDISNOTINAIRPORT);					
                }  else if (ULD_DEFAULTS_WARNING_ULDISNOTINAIRPORT
						.equals(errorVO.getErrorCode())) {
					throw new ULDDefaultsProxyException(MAIL_DEFAULTS_WARNING_ULDISNOTINAIRPORT);
				} else if (ULD_DEFAULTS_ERROR_ULDISLOST
						.equals(errorVO.getErrorCode())) {
					throw new ULDDefaultsProxyException(MAIL_DEFAULTS_ERROR_ULDISLOST);
				} else if (ULD_DEFAULTS_WARNING_ULDISLOST
						.equals(errorVO.getErrorCode())) {
					throw new ULDDefaultsProxyException(MAIL_DEFAULTS_WARNING_ULDISLOST);
				} else if (ULD_DEFAULTS_ERROR_ULDISNOTOPERATIONAL
						.equals(errorVO.getErrorCode())) {
					throw new ULDDefaultsProxyException(MAIL_DEFAULTS_ERROR_ULDISNOTOPERATIONAL);
				} else if (ULD_DEFAULTS_WARNING_ULDISNOTOPERATIONAL
						.equals(errorVO.getErrorCode())) {
					throw new ULDDefaultsProxyException(MAIL_DEFAULTS_WARNING_ULDISNOTOPERATIONAL);
				} else if (ULD_DEFAULTS_ERROR_ULDNOTINAIRLINESTOCK
						.equals(errorVO.getErrorCode())) {
					throw new ULDDefaultsProxyException(MAIL_DEFAULTS_ERROR_ULDNOTINAIRLINESTOCK);
				} else if (ULD_DEFAULTS_WARNING_ULDNOTINAIRLINESTOCK
						.equals(errorVO.getErrorCode())) {
					throw new ULDDefaultsProxyException(MAIL_DEFAULTS_WARNING_ULDNOTINAIRLINESTOCK);
				} else {
					throw new ULDDefaultsProxyException(proxyException);
				}
			}
		}
	}

	/**
	 * @param companyCode
	 * @param airportCode
	 * @param uldNumbers
	 * @return
	 * @throws SystemException
	 * @throws ULDDefaultsProxyException
	 */
	public void validateULDsForOperation(FlightDetailsVO flightDetailsVo)
			throws SystemException, ULDDefaultsProxyException {
		log.entering("ULDDefaultsProxy", "validateULDsForOperation");
		log.log(Log.FINE, "The Flight Details Vo", flightDetailsVo);
		try {
			despatchRequest("validateULDsForOperation", flightDetailsVo);
		} catch (ProxyException proxyException) {
			Collection<ErrorVO> errors = proxyException.getErrors();
			Collection<ErrorVO> errorsColl = new ArrayList<ErrorVO>();
			ErrorVO error  =null;
			for (ErrorVO errorVO : errors) {
				if (ULD_DEFAULTS_ERROR_ULDISNOTINAIRPORT.equals(errorVO
						.getErrorCode())) {
					error= new ErrorVO(MAIL_DEFAULTS_ERROR_ULDISNOTINAIRPORT);
					error.setErrorDisplayType(errorVO.getErrorDisplayType());
					errorsColl.add(error);
                } else if (ULD_DEFAULTS_WARNING_ULDISNOTINAIRPORT
						.equals(errorVO.getErrorCode())) {
					error= new ErrorVO(MAIL_DEFAULTS_WARNING_ULDISNOTINAIRPORT);
					error.setErrorDisplayType(errorVO.getErrorDisplayType());
					errorsColl.add(error);
				} else if (ULD_DEFAULTS_ERROR_ULDISLOST.equals(errorVO
						.getErrorCode())) {
					error= new ErrorVO(MAIL_DEFAULTS_ERROR_ULDISLOST);
					error.setErrorDisplayType(errorVO.getErrorDisplayType());
					errorsColl.add(error);
				} else if (ULD_DEFAULTS_WARNING_ULDISLOST.equals(errorVO
						.getErrorCode())) {
					error= new ErrorVO(MAIL_DEFAULTS_WARNING_ULDISLOST);
					error.setErrorDisplayType(errorVO.getErrorDisplayType());
					errorsColl.add(error);
				} else if (ULD_DEFAULTS_ERROR_ULDISNOTOPERATIONAL
						.equals(errorVO.getErrorCode())) {
					error= new ErrorVO(MAIL_DEFAULTS_ERROR_ULDISNOTOPERATIONAL);
					error.setErrorDisplayType(errorVO.getErrorDisplayType());
					errorsColl.add(error);
				} else if (ULD_DEFAULTS_WARNING_ULDISNOTOPERATIONAL
						.equals(errorVO.getErrorCode())) {
					error= new ErrorVO(MAIL_DEFAULTS_WARNING_ULDISNOTOPERATIONAL);
					error.setErrorDisplayType(errorVO.getErrorDisplayType());
					errorsColl.add(error);
				} else if (ULD_DEFAULTS_ERROR_ULDISNOTINTHESYSTEM
						.equals(errorVO.getErrorCode())) {
					error= new ErrorVO(MAIL_DEFAULTS_ERROR_ULDISNOTINTHESYSTEM);
					error.setErrorDisplayType(errorVO.getErrorDisplayType());
					errorsColl.add(error);
				} else if (ULD_DEFAULTS_WARNING_ULDISNOTINTHESYSTEM
						.equals(errorVO.getErrorCode())) {
					error= new ErrorVO(MAIL_DEFAULTS_WARNING_ULDISNOTINTHESYSTEM);
					error.setErrorDisplayType(errorVO.getErrorDisplayType());
					errorsColl.add(error);
				} else if (ULD_DEFAULTS_ERROR_ULDNOTINAIRLINESTOCK
						.equals(errorVO.getErrorCode())) {
					error= new ErrorVO(MAIL_DEFAULTS_ERROR_ULDNOTINAIRLINESTOCK);
					error.setErrorDisplayType(errorVO.getErrorDisplayType());
					errorsColl.add(error);
				} else if (ULD_DEFAULTS_WARNING_ULDNOTINAIRLINESTOCK
						.equals(errorVO.getErrorCode())) {
					error= new ErrorVO(MAIL_DEFAULTS_WARNING_ULDNOTINAIRLINESTOCK);
					error.setErrorDisplayType(errorVO.getErrorDisplayType());
					errorsColl.add(error);
				} else {
					throw new ULDDefaultsProxyException(proxyException);
				}
			}
			
			if(errorsColl!=null  && errorsColl.size()>0){
				log.log(Log.FINE, "THE errors Collected finally is  ",
						errorsColl);
				ErrorVO errorFromUld = (ErrorVO) ((List)errorsColl).get(0);
				ULDDefaultsProxyException uldDefaultsProxyException = new ULDDefaultsProxyException(errorFromUld.getErrorCode());
				 throw uldDefaultsProxyException;

//				for(ErrorVO errorFromUld : errorsColl){
//					log.log(Log.FINE,"THE  Error  Vo is "+errorFromUld);
//				 }
//				 ULDDefaultsProxyException uldDefaultsProxyException = new ULDDefaultsProxyException();
//				 uldDefaultsProxyException.addErrors(errorsColl);
//				 throw uldDefaultsProxyException;
			}
			
			
		}
	}

	/**
	 * @param flightDetailsVO
	 * @throws SystemException
	 * @throws ULDDefaultsProxyException
	 * @throws ProxyException
	 */
	public void updateULDForFlightFinalisation(FlightDetailsVO flightDetailsVO)
			throws SystemException, ULDDefaultsProxyException {
		log.log(Log.FINE, "\n\n\nflightDetailsVO in Proxy-------->",
				flightDetailsVO);
		try {
			despatchRequest("updateULDForFlightFinalisation", flightDetailsVO);
		} catch (ProxyException ex) {
			throw new ULDDefaultsProxyException(ex);
		}
	}

	/**
	 * @param flightDetailsVO
	 * @throws SystemException
	 * @throws ULDDefaultsProxyException
	 * @throws ProxyException
	 */
	public void updateULDForOperations(FlightDetailsVO flightDetailsVO)
			throws SystemException, ULDDefaultsProxyException {
		log.log(Log.FINE, "\n\n\nflightDetailsVO in Proxy-------->",
				flightDetailsVO);
		try {
			//despatchRequest("updateULDForOperations", flightDetailsVO);
			dispatchAsyncRequest("updateULDForOperations",false,flightDetailsVO);
		} catch (ProxyException ex) {
			throw new ULDDefaultsProxyException(ex);
		}

	}

	/**
	 * @param flightDetailsVO
	 * @throws SystemException
	 * @throws ProxyException
	 * @throws ULDDefaultsProxyException
	 */
	public void updateULDForFlightClosure(FlightDetailsVO flightDetailsVO)
			throws SystemException, ULDDefaultsProxyException {
		log.log(Log.FINE, "\n\n\nflightDetailsVO in Proxy-------->",
				flightDetailsVO);
		try {
			despatchRequest("updateULDForFlightClosure", flightDetailsVO);
		} catch (ProxyException ex) {
			throw new ULDDefaultsProxyException(ex);
		}
	}
	/**
	 * @param flightDetailsVO
	 * @throws SystemException
	 * @throws ProxyException
	 * @throws ULDDefaultsProxyException
	 */
	public void updateULDForMailFlightClosure(FlightDetailsVO flightDetailsVO)
			throws SystemException, ULDDefaultsProxyException {
		log.log(Log.FINE, "\n\n\n flightDetailsVO in Proxy-------->",
				flightDetailsVO);
		try {
			despatchRequest("updateULDForFlightClosure", flightDetailsVO);
		} catch (ProxyException ex) {
			throw new ULDDefaultsProxyException(ex);
		}
	}

	/**
	 * @param flightDetailsVO
	 * @throws SystemException
	 * @throws ProxyException
	 * @throws ULDDefaultsProxyException
	 */
	public void updateULDForOffload(FlightDetailsVO flightDetailsVO)
			throws SystemException, ULDDefaultsProxyException {
		log.log(Log.FINE, "\n\n\nflightDetailsVO in Proxy-------->",
				flightDetailsVO);
		try {
			despatchRequest("updateULDForOffload", flightDetailsVO);
		} catch (ProxyException ex) {
			throw new ULDDefaultsProxyException(ex);
		}
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 * @throws ULDDefaultsProxyException
	 */
	 public ULDVO findULDDetails(String companyCode, String uldNumber)
     throws SystemException,ULDDefaultsProxyException {
		
		try {
			return (ULDVO)despatchRequest("findULDDetails", new Object[] {
		            companyCode, uldNumber});
		} catch (ProxyException ex) {
			throw new ULDDefaultsProxyException(ex);
		}
	 }
}
